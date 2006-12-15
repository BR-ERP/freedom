package org.freedom.servlet;

import java.io.File;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.PropertyConfigurator;
import org.freedom.jdbc.DbConnectionPool;
import org.freedom.util.resource.AbstractResourcePool;

/**
 * Classe de inicializa��o do pool de conex�es <BR>
 * Projeto: freedom-pool <BR>
 * Pacote: org.freedom.servlet <BR>
 * Classe: @(#)SetupServlet.java <BR>
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
 * @see javax.servlet.http#HttpServlet
 */
public class SetupServlet extends HttpServlet {
   /**
    * Pool de conex�es.
    */
   private AbstractResourcePool pool;

   /**
    * @version Vers�o para serializa��o.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Iniciliza��o do servlet.
    * @param config Inst�ncia do web.xml .
    * @throws ServletException Exce��o de servlets.
    * @see org.freedom.servlet.SetupServlet#HttpServlet
    * @see SetupServlet#HttpServlet
    */
   public void init(final ServletConfig config) throws ServletException {
       this.init(config, false);
   }

   /**
    * Iniciliza��o do servlet para utiliza��o de JDBC ou JPA (Toplink).
    * @param config Inst�ncia do web.xml .
    * @param jpa Flague que define se � jpa.
    * @throws ServletException Exce��o de servlets.
    * @see org.freedom.servlet.SetupServlet#HttpServlet
    * @see SetupServlet#HttpServlet
    */
   public void init(final ServletConfig config, boolean jpa) throws ServletException {
       super.init(config);
       //config.getServletContext().
       setupLog(config);
       final ServletContext app = config.getServletContext();
       final String jdbcDriver = config.getInitParameter("jdbcDriver");
       final String jdbcURL = config.getInitParameter("jdbcURL");
       final String jdbcUser = config.getInitParameter("jdbcUser");
       final String jdbcPassword = config.getInitParameter("jdbcPassword");
       final String jpaFactory = config.getInitParameter("jpaFactory");
       final String jdbcInitCons =
          config.getInitParameter("jdbcInitialConnections");
       final String jdbcMaxCons = config.getInitParameter("jdbcMaxConnections");
       int initialCons = 0;
       int maxCons = 0;
       boolean ispool;
       if ((jdbcUser == null) || ("".equals(jdbcUser))) {
          ispool = false;
       } else {
          ispool = true;
          if ((jdbcInitCons != null) && (!"".equals(jdbcInitCons))) {
             initialCons = Integer.parseInt(jdbcInitCons);
          }
          if ((jdbcMaxCons != null) && (!"".equals(jdbcMaxCons))) {
             maxCons = Integer.parseInt(jdbcMaxCons);
          }
       }
       if (jpa) {
          setPool(new org.freedom.jpa.DbConnectionPool(jdbcDriver, jdbcURL, jpaFactory,initialCons, maxCons,
                jdbcUser, jdbcPassword, ispool ));
       } else {
          setPool(new DbConnectionPool(jdbcDriver, jdbcURL, initialCons, maxCons,
                jdbcUser, jdbcPassword, ispool));
       }
       app.setAttribute("db-connection-pool", pool);
   }
   
   /**
    * Ajusta os par�metros do log4java.
    * @param config Inst�ncia do web.xml .
    */
   public void setupLog(final ServletConfig config) throws ServletException {
      /** Diret�rio onde ser� colocado o arquivo de log. **/ 
      final String directory = getInitParameter("log-directory");
     
      /** 
       * Extrai o path onde se encontra o contexo.
       * Assume que o arquivo de configura��o se encontra nesse diret�rio.
       */
      final String prefix =  getServletContext().getRealPath("/");

      /** nome do arquivo de configura��o do Log4J */
      final String file = getInitParameter("log4j-init-file");
      
      /** Par�metro de configura��o que indica se deve revisar
       *  o arquivo para trocar par�metros.
       */
      final String watch = config.getInitParameter("watch");

      /**
       * Extrae o par�metro que indica quanto tempo deve revisar
       * o arquivo de configura��o.
       */
      final String timeWatch = config.getInitParameter("time-watch");
      
      /** Adiciona o par�metro do diret�rio na propriedade do sistema. **/ 
      System.setProperty("log.directory",directory);
      
      if(file == null || file.length() == 0 ||
            !(new File(prefix+file)).isFile()){
            System.err.println(
            "ERROR: N�o foi poss�vel ler o arquivo de configura��o de log.");
            throw new ServletException();
       }

      // Revisa como deve realizar a configura��o de Log4J e define o m�todo adequado
      if (watch != null && watch.equalsIgnoreCase("true")) {
          if (timeWatch != null) {
              PropertyConfigurator.configureAndWatch(prefix+file,Long.parseLong(timeWatch));
          } else {
              PropertyConfigurator.configureAndWatch(prefix+file);
          }
      } else {
          PropertyConfigurator.configure(prefix+file);
      }
      
   }
   
   /**
    * Acessor do Pool.
    * @return Retorna o pool de conex�es.
    */
   public final AbstractResourcePool getPool() {
      return this.pool;
   }

   /**
    * Seta o pool interno.
    * @param poolParam Recebe o Pool de conex�es.
    */
   public final void setPool(final AbstractResourcePool poolParam) {
      this.pool = poolParam;
   }

   /**
    * Finaliza o pool de conex�es.
    */
   public final void destroy() {
       pool.shutdown();
       super.destroy();
   }
   
}
