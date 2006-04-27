/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)FAtividade.java <BR>
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
package org.freedom.modulos.tmk;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FAtividade extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCod= new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDesc= new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
	
	public FAtividade () {
		super();
		setTitulo("Cadastro de atividades");
		setAtribos(50, 50, 350, 125);
		adicCampo(txtCod, 7, 20, 70, 20,"CodAtiv","C�d.ativ.", ListaCampos.DB_PK, true);
		adicCampo(txtDesc, 80, 20, 240, 20,"DescAtiv","Descri��o da atividade", ListaCampos.DB_SI, true);
		setListaCampos( true, "ATIVIDADE", "TK");
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
		ImprimeOS imp = null;
		int linPag = 0;
		
		DLRAtiv dl = new DLRAtiv();
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		
		try {

			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relat�rio de Atividades");
			imp.limpaPags();
			
			sSQL = "SELECT TK.CODATIV,TK.DESCATIV \n"+
				   "FROM TKATIVIDADE TK ORDER BY TK."+dl.getValor();
			
			ps = con.prepareStatement(sSQL);
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow(), 0, imp.normal());
					imp.say(imp.pRow(), 3, "C�digo");
					imp.say(imp.pRow(), 23, "Descri��o");
					imp.say(imp.pRow()+1, 0, imp.normal());
					imp.say(imp.pRow(), 0,Funcoes.replicate("-",79));
				}
				imp.say(imp.pRow()+1, 0, imp.normal());
				imp.say(imp.pRow(), 3,Funcoes.alinhaDir(rs.getInt("CodAtiv"),8));
				imp.say(imp.pRow(), 23,rs.getString("DescAtiv"));
				if (imp.pRow()>=linPag) {
					imp.incPags();
					imp.eject();
				}
			}			  
			imp.say(imp.pRow()+1, 0, imp.normal());
			imp.say(imp.pRow(), 0, Funcoes.replicate("=",79));
			imp.eject();		  
			imp.fechaGravacao();
			if (!con.getAutoCommit())
				con.commit();
			dl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de atividades!"+err.getMessage(),true,con,err);      
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		    
		if (bVisualizar)
			imp.preview(this);
		else
			imp.print();
	}
}
