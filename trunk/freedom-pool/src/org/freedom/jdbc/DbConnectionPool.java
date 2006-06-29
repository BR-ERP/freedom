package org.freedom.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.freedom.util.resource.ResourceException;
import org.freedom.util.resource.ResourceKey;
import org.freedom.util.resource.AbstractResourcePool;

/**
 * @author Robson Sanchez/Setpoint Inform�tica Ltda.
 *
 * Pool de conex�es JDBC. <BR>
 * criada: 05/10/2004. <BR>
 * Projeto: freedom-pool <BR>
 * Pacote: org.freedom.jdbc <BR>
 * Classe: DbConnectionPool <BR> <BR>
 * Este programa � licenciado de acordo com a LGPL
 * (Lesser General Public License), <BR>
 * vers�o 2.1, Fevereiro de 1999 <BR>
 * A LGPL deve acompanhar todas PUBLICA��ES,
 * DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LGPL n�o esteja dispon�vel
 * junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em:
 * <a href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt>
 * Creative Commons</a> <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou
 * ALTERAR este Programa � preciso estar
 * de acordo com os termos da LGPL <BR> <BR>
 */
public class DbConnectionPool extends AbstractResourcePool {

   /**
    * driver Caminho para o driver JDBC.
    * url URL para a conex�o com o banco de dados.
    */
   private transient String driver, url;

   /**
    * initialCons N�mero de conex�es iniciais.
    */
   private transient int initialCons;

   /**
    * driverLoaded Flag que indica se o driver j� foi carregado.
    */
   private transient boolean driverLoaded = false;

   /**
    * user ID do usu�rio para conex�o com banco de dados.
    * password Senha do usu�rio de banco de dados.
    */
   private transient String user, password;

   /**
    * userweb Usu�rio para o pool de conex�es configurado no web.xml.
    * passwordweb Senha para o pool de conex�es configurado no web.xml.
    */
   private transient String userweb, passwordweb;

   /**
    * sessionID ID da sess�o do servidor http. Utilizado como chave para pool.
    */
   private transient String sessionID;

   /**
    * Construtor da classe sem as informa��es de usu�rio e senha.
    * @param drivercon Caminho para o driver de conex�o JDBC.
    * @param urlcon URL para a conex�o com o banco de dados.
    */
   public DbConnectionPool(final String drivercon, final String urlcon) {
      this(drivercon, urlcon, null, null);
   }

   /**
    * Construtor da classe com as informa��es de usu�rio e senha.
    * @param drivercon Caminho para o driver de conex�o JDBC.
    * @param urlcon URL para a conex�o com o banco de dados.
    * @param usercon ID do usu�rio para conex�o com o banco de dados.
    * @param passwordcon Senha do usu�rio para a conex�o com o banco de dados.
    */
   public DbConnectionPool(final String drivercon, final String urlcon,
         final String usercon, final String passwordcon) {
      super();
      this.initialCons = 0;
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
         }
      }
   }
   

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
         throw new ResourceException(ex.getMessage());
      } finally {
         key = null;
      }

      return resource;
   }

   public final void closeResource(final ResourceKey resource) {
      java.sql.Connection connection = null;
      try {
         connection = (Connection) resource.getResource();
         connection.close();
      } catch (SQLException ex) {
         // ignora exce��o
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.elog.system.util.ResourcePool#isResourceValid(java.lang.Object)
    */
   public boolean isResourceValid(ResourceKey resource) {
      Connection connection = null;
      boolean valid = false;
      try {
         connection = (Connection) resource.getResource();
         valid = !connection.isClosed();
      } catch (SQLException ex) {
         valid = false;
      }
      return valid;
   }

   public final void setUser(final String usercon) {
      this.user = usercon;
   }

   public final void setPassword(final String passwordcon) {
      this.password = passwordcon;
   }

   public final void setSessionID(final String sessionIDcon) {
      this.sessionID = sessionIDcon;
   }

}
