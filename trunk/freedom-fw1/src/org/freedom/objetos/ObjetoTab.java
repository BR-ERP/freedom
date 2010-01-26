/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)TabObjeto.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Coment�rios da classe.....
 */

package org.freedom.objetos;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Vector;

public class ObjetoTab {
  public static final int IDOBJ = 0;
  public static final int DESCOBJ = 2;
  public static final int TIPOOBJ = 3;
  public static final int COMENTOBJ = 4;
  public static final int USOMEOBJ = 5;
  
  private Vector<ObjetoBD> vObjetos = null;
  public ObjetoTab() {
  	vObjetos = new Vector<ObjetoBD>();
  }
     
  public boolean montaLista(DbConnection con, int iCodEmp, String sTabela, String sTipoObj) {
  	
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	boolean bRetorno = false;
  	String sSql = "";
  	try {
  	   if ( !con.isClosed() ) {
  	   	  sSql = "SELECT IDOBJ,DESCOBJ,TIPOOBJ,COMENTOBJ,USOMEOBJ FROM "+sTabela+
  	   	    " WHERE CODEMP=? AND TIPOOBJ=?";
  	   	  ps = con.prepareStatement(sSql);
  	   	  ps.setInt(1,iCodEmp);
  	   	  ps.setString(2,sTipoObj);
  	   	  rs = ps.executeQuery();
  	   	  while ( rs.next() ) {
  	   	  	 vObjetos.add(new ObjetoBD(rs.getString("IDOBJ"), 
 	 				rs.getString("DESCOBJ"), 
  	 				rs.getString("TIPOOBJ"), rs.getString("COMENTOBJ"), 
  	 				rs.getString("USOMEOBJ") ) );
  	   	  }
  	   	  rs.close();
    	  con.commit();
  	   }
  	}
  	catch (SQLException e) {
  	   bRetorno = false;
  	}
  	return bRetorno;
  }
  
  public boolean getUsoMe(String sTabela) {
     boolean bRetorno = true;
     if ( (vObjetos!=null) && (sTabela!=null) ){
     	for (int i=0; i<vObjetos.size(); i++) {
     		if ( vObjetos.elementAt(i).getIDObj().
     		        toUpperCase().trim().equals(sTabela.toUpperCase().trim())) {
     		   if ( !vObjetos.elementAt(i).getUsomeObj().equals("S")) 
     		   	  bRetorno = false;
  		   	   break;
     		}
     	}
     }
     
     return bRetorno;	  
  }
  
}
