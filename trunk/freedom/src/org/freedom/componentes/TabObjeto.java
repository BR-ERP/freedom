/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)TabObjeto.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Coment�rios da classe.....
 */

package org.freedom.componentes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Vector;

public class TabObjeto {
  public static final int CODOBJ = 0;
  public static final int NOMEOBJ = 1;
  public static final int DESCOBJ = 2;
  public static final int TIPOOBJ = 3;
  public static final int COMENTOBJ = 4;
  public static final int USOMEOBJ = 5;
  
  private Vector vObjetos = null;
  public TabObjeto() {
  	vObjetos = new Vector();
  }
     
  public boolean montaLista(Connection con, int iCodEmp, String sTabela, String sTipoObj) {
  	
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	boolean bRetorno = false;
  	String sSql = "";
  	try {
  	   if ( !con.isClosed() ) {
  	   	  sSql = "SELECT CODOBJ,NOMEOBJ,DESCOBJ,TIPOOBJ,COMENTOBJ,USOMEOBJ FROM "+sTabela+
  	   	    " WHERE CODEMP=? AND TIPOOBJ=?";
  	   	  ps = con.prepareStatement(sSql);
  	   	  ps.setInt(1,iCodEmp);
  	   	  ps.setString(2,sTipoObj);
  	   	  rs = ps.executeQuery();
  	   	  while ( rs.next() ) {
  	   	  	 vObjetos.add(new ObjetoBD(rs.getInt("CODOBJ"), 
 	 				rs.getString("NOMEOBJ"), rs.getString("DESCOBJ"), 
  	 				rs.getString("TIPOOBJ"), rs.getString("COMENTOBJ"), 
  	 				rs.getString("USOMEOBJ") ) );
  	   	  }
  	   	  rs.close();
  	   	  if (!con.getAutoCommit())
  	   		con.commit();
  	   }
  	}
  	catch (SQLException e) {
  	   bRetorno = false;
  	}
  	return bRetorno;
  }
  
  public boolean getUsoMe(String sTabela) {
     boolean bRetorno = false;
     if (vObjetos!=null) {
     	for (int i=0; i<vObjetos.size(); i++) {
     		if ( ((ObjetoBD) vObjetos.elementAt(i)).getNomeObj().
     		        toUpperCase().trim().equals(sTabela.toUpperCase().trim())) {
     		   if ( ((ObjetoBD) vObjetos.elementAt(i)).getUsomeObj().equals("S")) {
     		   	  bRetorno = true;
     		   	  break;
     		   }
     		}
     	}
     }
     
     return bRetorno;	  
  }
  
}
