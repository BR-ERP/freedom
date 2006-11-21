package org.freedom.infra.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Classe de conex�o com o banco de dados <BR>
 * Projeto: freedom-infra <BR>
 * Pacote: org.freedom.infra.db <BR>
 * Classe: DbConnection.java <BR>
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
 * criada: 21/11/2006. <BR>
 */

public class DbConnection {
	
	/** Log da classe. **/
	private static final Logger LOGGER = createLogger();
	
	private Connection conn;
	private boolean connected = false; 
	private String userid;
	private String password;
	private String driver;
	private String urldb;
	
	public DbConnection(String drv, String url, String usrid, String pwd) {
		execConnection(drv, url, usrid, pwd);
	}
	
   /**
    * Cria uma inst�ncia do log4j da classe.
    * @return Retorna a inst�ncia do log da classe.
    */
   private static Logger createLogger() {
      return Logger.getLogger("org.freedom.infra.db.DbConnection");
   }
	
	public PreparedStatement prepareStatement(String sql) {
		PreparedStatement stmt = null;
		if (conn!=null) {
			try {
				stmt = conn.prepareStatement(sql);
			}
			catch (SQLException e) {
				LOGGER.error(e);
			}
		}
		return stmt;
	}
	
	public ResultSet executeQuery(PreparedStatement stmt) {
		ResultSet rs = null;
		if ((conn!=null) && (stmt!=null)) {
			try {
				rs = stmt.executeQuery();
			}
			catch (SQLException e) {
				LOGGER.error(e);
			}
		}
		return rs;
	}
	//Setters
	public void setUserid(String usrid) {
		this.userid = usrid;
	}
	
	public void setPassword(String pwd) {
		this.password = pwd;
	}
	
	public void setUrldb(String url) {
		this.urldb = url;
	}
	
	public void setDriver(String drv) {
		this.driver = drv;
	}
	
	//Getters
	public String getDriver() {
		return driver;
	}

	public String getPassword() {
		return password;
	}

	public String getUrldb() {
		return urldb;
	}

	public String getUserid() {
		return userid;
	}

	public boolean execConnection(String drv, String url, String usrid, String pwd) {
		boolean ret = true;
		setDriver(drv);
		setUrldb(url);
		setUserid(usrid);
		setPassword(pwd);
		try {
        	Class.forName(drv);
        	conn = DriverManager.getConnection(url, usrid, pwd);
		}
		catch (Exception e) {
			LOGGER.error(e);
			ret = false;
			
		}
		connected = ret;
		return ret;
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public boolean isConnected() {
		return connected;
	}
}
