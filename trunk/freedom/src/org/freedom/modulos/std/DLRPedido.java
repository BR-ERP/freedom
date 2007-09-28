/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRPedido.java <BR>
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
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;
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
	private JCheckBox cbxResumido = new JCheckBox("Relat�rio Resumido"); 
	private Vector<String> vLabs = new Vector<String>();
	private Vector<String> vVals = new Vector<String>();
	
	public DLRPedido(String OrdNota, boolean RelResumido) {
		setTitulo("Ordem do Relat�rio");
		setAtribos(350,160);
		vLabs.addElement("C�digo");
		vLabs.addElement("Descri��o");
		vLabs.addElement("Marca");
		vVals.addElement("C");
		vVals.addElement("D");
		vVals.addElement("M");
		rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
		rgOrdem.setVlrString(OrdNota);
		if (RelResumido)
		adic(cbxResumido, 7, 55, 180, 15);
		adic(lbOrdem,7,0,80,15);
		adic(rgOrdem,7,20,330,30);
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
		String sSQL = null;		
		String retorno = null;
				
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
				
			if(!con.getAutoCommit())
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
  
}
