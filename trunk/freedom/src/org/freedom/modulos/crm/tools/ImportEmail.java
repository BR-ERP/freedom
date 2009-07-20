/**
 * @version 16/01/2009 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.tools <BR>
 * Classe: @(#)ImportEmail.java <BR>
 * 
 * Este programa � licenciado de acordo com a GPL (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.0. <BR>
 * A GPL deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da GPL n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da GPL <BR> <BR>
 * 
 */

package org.freedom.modulos.crm.tools;

import java.io.File;
import java.io.IOException;
import org.freedom.infra.model.jdbc.DbConnection;

public class ImportEmail {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {

		byte[] password = new byte[20];
		if ( (args==null) || (args.length<3) )  {
			System.out.println("Entre com os parametros URL/JDBC/Freedom, diretorio e extensao de arquivo");
		} else {
			System.out.print( "Entre com a senha para usu�rio sysdba: ");
			try {
			   System.in.read(password);
			   importEmail(args[0],new String( password ).trim() , args[1],args[2]);
			} catch (IOException e) {
				System.out.println("Erro lendo a senha\n"+e.getMessage());
			}
		}
	}
	
	private static void importEmail(String URLJdbc, String password, String dir, String ext) {
		String path = null;
		String[] list = null;
	    System.out.println("Executando a conexao...");
		DbConnection con = getDbConnection( URLJdbc, password );
		if (con!=null) {
			path = getDirectory(dir);
			if (path!=null) {
				System.out.println("Iniciando a importacao em " + path + " ...");
				list = getListDir(path);
			}
			
		} else {
			System.out.println("Nao foi possivel estabelecer a conexao com o banco de dados");
		}
	}

	private static String[] getListDir(String path) {
		File file = new File(path);
		return file.list();
	}
	
	private static String getDirectory(String dir) {
		String pathv = null;
		File file = new File(dir);
		if (file.exists()) {
			pathv = file.getPath();
		} else {
			System.out.println("Diretorio nao encontrado");
		} 
        return pathv; 		
	}
    private static DbConnection getDbConnection(String URLJdbc, String password) {
    	DbConnection con = null;
		
		try {
			con = new DbConnection("org.firebirdsql.jdbc.FBDriver", URLJdbc, "sysdba", password);
			con.setAutoCommit(false);
		} 
		catch (java.sql.SQLException e) {
			if (e.getErrorCode() == 335544472)
				System.out.println("Nome do usu�rio ou senha inv�lidos ! ! !");
			else                                                                             
				System.out.println("N�o foi poss�vel estabelecer conex�o com o banco de dados.\n"+e.getMessage());
			e.printStackTrace();
		}
    	return con;
    }
}
