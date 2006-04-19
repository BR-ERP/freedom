/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTabPreco.java <BR>
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
 * 
 */

package org.freedom.modulos.std;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class FTabPreco extends FDados implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCodTab = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtDescTab= new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
	
	public FTabPreco () {
		super();
		setTitulo("Cadastro de Tabelas de Pre�os");
		setAtribos( 50, 50, 350, 125);
		adicCampo(txtCodTab, 7, 20, 70, 20,"CodTab","C�d.tb.pc.", ListaCampos.DB_PK, true);
		adicCampo(txtDescTab, 80, 20, 250, 20,"DescTab","Descri��o da tabela de pre�o", ListaCampos.DB_SI, true);
		setListaCampos( true, "TABPRECO", "VD");
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcCampos.setQueryInsert(false);  
		setImprimir(true);
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp)
			imprimir(true);
		else if (evt.getSource() == btImp) 
			imprimir(false);
		super.actionPerformed(evt);
	}
	
	private void imprimir(boolean bVisualizar) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		DLRTabPreco dl = null;
		ImprimeOS imp = null;
		int linPag = 0;
		
		dl = new DLRTabPreco();
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		 
		try {
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relat�rio de Tabelas de Pre�os");
			imp.limpaPags();
			
			sSQL = "SELECT CODTAB,DESCTAB " +
				   "FROM VDTABPRECO " +
				   "WHERE CODEMP=? AND CODFILIAL=? " +
				   "ORDER BY "+dl.getValor();
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("VDTABPRECO"));
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow(), 0, imp.normal());
					imp.say(imp.pRow(), 2, "C�digo");
					imp.say(imp.pRow(), 30, "Descri��o");
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(), 0, Funcoes.replicate("-",79));
				}
				imp.say(imp.pRow() + 1, 0, imp.normal());
				imp.say(imp.pRow(), 2, rs.getString("CodTab"));
				imp.say(imp.pRow(), 30, rs.getString("DescTab"));
				if (imp.pRow()>=linPag) {
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(), 0, Funcoes.replicate("-",79));
					imp.incPags();
					imp.eject();
				}
			}
			
			imp.say(imp.pRow() + 1, 0, imp.normal());
			imp.say(imp.pRow(), 0, Funcoes.replicate("=",79));
			imp.eject();
			  
			imp.fechaGravacao();
			if (!con.getAutoCommit())
				con.commit();
			dl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de \"tabelas de pre�os\"!\n"+err.getMessage(),true,con,err);      
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			dl = null;
		}
		    
		if (bVisualizar)
		imp.preview(this);
		else
		imp.print();
	}
}
