/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRPedido.java <BR>
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
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JCheckBox;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;
public class DLRPedido extends FFDialogo {

	private static final long serialVersionUID = 1L;
	private JRadioGroup<?, ?> rgOrdem = null;
	private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
	private JLabelPad lbTipo = new JLabelPad("Tipo:");
	private JCheckBox cbxResumido = new JCheckBox("Relat�rio Resumido"); 
	private Vector<String> vLabs = new Vector<String>();
	private Vector<String> vVals = new Vector<String>();
	private JRadioGroup<?, ?> rgTipo = null;
	
	public DLRPedido(String OrdNota, boolean RelResumido) {
		
		setTitulo("Ordem do Relat�rio");
		setAtribos(355,220);
		vLabs.addElement("C�digo");
		vLabs.addElement("Descri��o");
		vLabs.addElement("Marca");
		vVals.addElement("C");
		vVals.addElement("D");
		vVals.addElement("M");
		
		rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
		rgOrdem.setVlrString(OrdNota);
		
		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();
		
		vLabs.addElement( "Grafico" );
		vLabs.addElement( "Texto" );
		vVals.addElement( "G" );
		vVals.addElement( "T" );
		
		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgTipo.setVlrString( "T" );
		
		if (RelResumido)
		adic(lbTipo,7,0,80,15);
		adic(rgTipo ,7,20,330,30);
		adic(lbOrdem,7,60,80,15);
		adic(rgOrdem,7,75,330,30);
		adic(cbxResumido, 7, 110, 180, 15);
	}
	
	public void setTipo( String sTipo ){
		
		rgTipo.setVlrString( sTipo );
	}
	public String getTipo(){
		
		String tipo = "";
		
		if("G".equals( rgTipo.getVlrString() )){
			
			tipo = "G";
			
		}else{
			tipo = "T";
		}
		
		return tipo;
	}
	  
	public boolean ehResumido() {
		return cbxResumido.isSelected();
	}
	  
	public String getValor() {
		
		String sRetorno = "";
		if (rgOrdem.getVlrString().compareTo("C") == 0 )
			sRetorno = getComRef();
		else if (rgOrdem.getVlrString().compareTo("D") == 0 )
			sRetorno = "DESCPROD";
		else if (rgOrdem.getVlrString().compareTo("M") == 0 )
			sRetorno = "CODMARCA";
		return sRetorno;
	}
	  
	private String getComRef() {
		  
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "";		
		String retorno = "";
				
		try {
					
			sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
				
			if(rs.next())
				retorno = rs.getString(1).trim();
			
			if(retorno.equals("S"))
				return "REFPROD";
				
			con.commit();
				
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	  
		return "CODPROD";
	}
	
	public void setConexao(DbConnection con) {
		this.con = con;
	}
  
}
