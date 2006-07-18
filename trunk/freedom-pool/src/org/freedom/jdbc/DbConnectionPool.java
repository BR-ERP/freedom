package org.freedom.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.freedom.util.resource.ResourceException;
import org.freedom.util.resource.ResourceKey;
import org.freedom.util.resource.AbstractResourcePool;

/**
 * Pool de conex�es JDBC. <BR>
 * Projeto: freedom-pool <BR>
 * Pacote: org.freedom.jdbc <BR>
 * Classe: DbConnectionPool <BR>
 * <BR>
 * Este programa � licenciado de acordo com a LGPL (Lesser General Public
 * License), <BR>
 * vers�o 2.1, Fevereiro de 1999 <BR>
 * A LGPL deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LGPL n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <a
 * href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt> Creative
 * Commons</a> <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar de acordo com os termos da LGPL. <BR>
 * <BR>
 * @author Robson Sanchez/Setpoint Inform�tica Ltda. <BR>
 * criada: 05/10/2004. <BR>
 */
public class DbConnectionPool extends AbstractResourcePool {

   /** Caminho para o driver JDBC. **/
   private transient String driver;

   /** url URL para a conex�o com o banco de dados. **/
   private transient String url;

   /** N�mero de conex�es iniciais. **/
   private transient int initialCons;

   /** Flag que indica se o driver j� foi carregado. **/
   private transient boolean driverLoaded = false;

   /** ID do usu�rio para conex�o com banco de dados. **/
   private transient String user;

   /** Senha do usu�rio de banco de dados. **/
   private transient String password;

   /** Usu�rio para o pool de conex�es configurado no web.xml. **/
   private transient String userweb;

   /** Senha para o pool de conex�es configurado no web.xml. **/
   private transient String passwordweb;

   /** ID da sess�o do servidor http. Utilizado como chave para pool. **/
   private transient String sessionID;

   /** Log4j da classe. **/
   private static final Logger LOGGER = createLogger();

   /**
    * Construtor da classe sem as informa��es de usu�rio e senha.
    * @param drivercon Caminho para o driver de conex�o JDBC.
    * @param urlcon URL para a conex�o com o banco de dados.
    * @param nInitialCons N�mero inicial de conex�es.
    * @param nMaxCons N�mero m�ximo de conex�es.
    */
   public DbConnectionPool(final String drivercon, final String urlcon,
      final int nInitialCons, final int nMaxCons) {
      this(drivercon, urlcon, nInitialCons, nMaxCons, null, null);
   }

   /**
    * Construtor da classe com as informa��es de usu�rio e senha.
    * @param drivercon Caminho para o driver de conex�o JDBC.
    * @param urlcon URL para a conex�o com o banco de dados.
    * @param nInitialCons N�mero inicial de conex�es.
    * @param nMaxCons N�mero m�ximo de conex�es.
    * @param usercon ID do usu�rio para conex�o com o banco de dados.
    * @param passwordcon Senha do usu�rio para a conex�o com o banco de dados.
    */
   public DbConnectionPool(final String drivercon, final String urlcon,
         final int nInitialCons, final int nMaxCons,
         final String usercon, final String passwordcon) {
      super();
      setInitialCons(nInitialCons);
      setMaxResources(nMaxCons);
      this.driver = drivercon;
      this.url = urlcon;
      ResourceKey resource;
      if ((usercon != null) || (passwordcon != null)) { // se o usu�rio e senha
         // estiverem definidos no
         // web xml
         this.userweb = usercon;
         this.passwordweb = passwordcon;
         try {
            for (int i = 0; i < initialCons; i++) {
               resource = createResource();
               getAvailableRes().put(resource.getHashKey(), resource);
            }
         } catch (Exception ex) {
            LOGGER.error(ex);
         }
      }
   }

   /**
    * Cria uma inst�ncia de Log4j para a classe.
    * @return Retorna o log inst�nciado.
    */
   private static Logger createLogger() {
      return Logger.getLogger("org.freedom.jdbc.DbConnectionPool");
   }

   /**
    * M�todo que inst�ncia e disponibiliza nova conex�o de banco de dados.
    * @throws ResourceException Exce��o gerada quando n�o for poss�vel
    * inst�nciar um novo recurso.
    * @return ResourceKey
    */

   public final ResourceKey createResource() throws ResourceException {
      Connection connection = null;
      ResourceKey resource = null;
      String key = null;
      String pwd = null;
      try {
         if (!driverLoaded) {
            Class.forName(driver);
            driverLoaded = true;
         }
         if ((user == null) || (password == null)) { // se o username ou a
            // password informada
            // estiverem nulos
            // conectar� com as informa��es de web xml
            connection = DriverManager.getConnection(url, userweb, passwordweb);
            key = userweb;
            pwd = passwordweb;
         } else {
            connection = DriverManager.getConnection(url, user, password);
            key = user;
            pwd = password;
         }
         resource = new ResourceKey(sessionID, key, pwd, connection);
      } catch (Exception ex) {
         // ClassNotFoundException ou SQLException
         LOGGER.error(ex);
         throw new ResourceException(ex.getMessage());
      }
      return resource;
   }

   /**
    * Fecha as conex�es com banco de dados.
    * @param resource Recebe o recurso a ser finalizado.
    */
   public final void closeResource(final ResourceKey resource) {
      java.sql.Connection connection = null;
      try {
         connection = (Connection)
            resource.getResource();
         connection.close();
      } catch (SQLException ex) {
         LOGGER.error(ex);
         // ignora exce��o
      }
   }

   /**
    * Consiste se um recurso � v�lido.
    * @param resource Recebe o recurso para avalia��o.
    * @return Devolve um flag indicando se o recurso � v�lido.
    */
   public final boolean isResourceValid(final ResourceKey resource) {
      boolean valid = false;
      try {
         final Connection connection = (Connection)
            resource.getResource();
         valid = (!connection.isClosed());
      } catch (SQLException ex) {
         valid = false;
         LOGGER.error(ex);
      }
      return valid;
   }

   /**
    * Seta o usu�rio corrente para conex�o.
    * @author Robson Sanchez/Setpoint Inform�tica Ltda.
    * @param usercon ID do usu�rio.
    */
   public final void setUser(final String usercon) {
      this.user = usercon;
   }

   /**
    * Seta senha corrente.
    * @author Robson Sanchez
    * @param passwordcon senha.
    */
   public final void setPassword(final String passwordcon) {
      this.password = passwordcon;
   }

   /**
    * seta a sess�o http corrente.
    * @author Robson Sanchez
    * @param sessionIDcon ID da sess�o corrente.
    */
   public final void setSessionID(final String sessionIDcon) {
      this.sessionID = sessionIDcon;
   }

   /**
    * Seta o n�mero de conex�es iniciais.
    * @author Robson Sanchez
    * @param initial n�mero de conex�es iniciais.
    */
   public final void setInitialCons(final int initial) {
      this.initialCons = initial;
   }

   /**
    * Seta o flag indicando se o driver de conex�o com banco de dados
    * foi carregado.
    * @param driverLoad Verdadeiro se o driver j� foi carregado.
    */
   public final void setDriverLoaded(final boolean driverLoad) {
      this.driverLoaded = driverLoad;
   }
}
