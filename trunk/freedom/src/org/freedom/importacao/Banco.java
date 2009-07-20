/*
 * Created on 03/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.freedom.importacao;

import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author alexandre
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Banco {
    private DbConnection conn = null;

    private Statement stmt = null;

    private boolean hasStatement = false;

    private boolean autoCommit = true;

    /**
     *  
     */
    public Banco(String url, String driver, String user, String pass) {
        super();

        try {
        	conn = new DbConnection(driver, url, user, pass);
            //    		props.put("roleName", "testrole");
//            conn = DriverManager.getDbConnection(url, props);
  //          conn.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            if (e.getErrorCode() == 335544472) {
                System.err.println("usu�rio ou senha errados");
                e.printStackTrace();
            } else
                e.printStackTrace();
            conn = null;
        }
    }

    public Statement getStatement() {
        if (!hasStatement) {
            try {
                stmt = (conn == null ? null : conn.createStatement()); 
                hasStatement = (stmt != null);
            } catch (SQLException e) {
                stmt = null;
            }
        }

        return stmt;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
            }
        }
    }

    public void commit() {
        if (conn != null) {
            try {
                conn.setAutoCommit(autoCommit);
            } catch (SQLException e) {
            }
        }
    }

    public void rollBack() {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
            }
        }
    }

    public DbConnection getDbConnection() {
        return conn;
    }

    public void connect(String url, String driver, String user, String pass) {

/*        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Properties props = new Properties();
*/
        try {
            //props.put("user", user);
            //props.put("password", pass);
            //    		props.put("roleName", "testrole");
        	conn = new DbConnection(driver, url, user, pass);
            //conn = DriverManager.getDbConnection(url, props);
            conn.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            if (e.getErrorCode() == 335544472) {
                System.err.println("usu�rio ou senha errados");
                e.printStackTrace();
            } else
                e.printStackTrace();
            conn = null;
        }
    }
}