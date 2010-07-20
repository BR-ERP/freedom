/**
 * @version 03/05/2005 <BR>
 * @author Setpoint Inform�tica Ltda / Alexandre Marcondes.
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.importacao <BR>
 *         Classe:
 * @(#)Banco.java <BR>
 * 
 *                Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                ? <BR>
 * 
 */

package org.freedom.business.component;

import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.SQLException;
import java.sql.Statement;

public class Banco {

	private DbConnection conn = null;

	private Statement stmt = null;

	private boolean hasStatement = false;

	private boolean autoCommit = true;

	/**
     *  
     */
	public Banco( String url, String driver, String user, String pass ) {

		super();

		try {
			conn = new DbConnection( driver, url, user, pass );
			// props.put("roleName", "testrole");
			// conn = DriverManager.getDbConnection(url, props);
			// conn.setAutoCommit(autoCommit);
		} catch ( SQLException e ) {
			if ( e.getErrorCode() == 335544472 ) {
				System.err.println( "usu�rio ou senha errados" );
				e.printStackTrace();
			}
			else
				e.printStackTrace();
			conn = null;
		}
	}

	public Statement getStatement() {

		if ( !hasStatement ) {
			try {
				stmt = ( conn == null ? null : conn.createStatement() );
				hasStatement = ( stmt != null );
			} catch ( SQLException e ) {
				stmt = null;
			}
		}

		return stmt;
	}

	public boolean isAutoCommit() {

		return autoCommit;
	}

	public void setAutoCommit( boolean autoCommit ) {

		this.autoCommit = autoCommit;
		if ( conn != null ) {
			try {
				conn.commit();
			} catch ( SQLException e ) {
			}
		}
	}

	public void commit() {

		if ( conn != null ) {
			try {
				conn.setAutoCommit( autoCommit );
			} catch ( SQLException e ) {
			}
		}
	}

	public void rollBack() {

		if ( conn != null ) {
			try {
				conn.rollback();
			} catch ( SQLException e ) {
			}
		}
	}

	public DbConnection getDbConnection() {

		return conn;
	}

	public void connect( String url, String driver, String user, String pass ) {

		/*
		 * try { Class.forName(driver); } catch (ClassNotFoundException e) { e.printStackTrace(); }
		 * 
		 * Properties props = new Properties();
		 */
		try {
			// props.put("user", user);
			// props.put("password", pass);
			// props.put("roleName", "testrole");
			conn = new DbConnection( driver, url, user, pass );
			// conn = DriverManager.getDbConnection(url, props);
			conn.setAutoCommit( autoCommit );
		} catch ( SQLException e ) {
			if ( e.getErrorCode() == 335544472 ) {
				System.err.println( "usu�rio ou senha errados" );
				e.printStackTrace();
			}
			else
				e.printStackTrace();
			conn = null;
		}
	}
}
