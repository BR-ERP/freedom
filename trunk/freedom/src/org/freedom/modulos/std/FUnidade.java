/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FUnidade.java <BR>
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

public class FUnidade extends FDados implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodUnidade = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
	
	private JTextFieldPad txtDescUnidade = new JTextFieldPad(JTextFieldPad.TP_STRING,60,0);
	
	private JTextFieldPad txtCasasDec = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	
	public FUnidade () {
		super();
		setTitulo("Cadastro de Unidades");
		setAtribos( 50, 50, 450, 160);
		adicCampo(txtCodUnidade, 7, 20, 110, 20,"CodUnid","C�d.unid.",ListaCampos.DB_PK, true);
		adicCampo(txtDescUnidade, 120, 20, 300, 20,"DescUnid","Descri��o da unidade",ListaCampos.DB_SI, true);
		adicCampo(txtCasasDec, 7, 60, 110, 20,"CasasDec","Casas decimais",ListaCampos.DB_SI, true);
		setListaCampos( true, "UNIDADE", "EQ");
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
		DLRUnidade dl = null;
		ImprimeOS imp = null;
		int linPag = 0;
		
		dl = new DLRUnidade();
		dl.setVisible(true);

		if (dl.OK == false) {
			dl.dispose();
			return;
		}

		try {
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relat�rio de Unidades");
			imp.limpaPags();
			
			sSQL = "SELECT CODUNID,DESCUNID " 
				+  "FROM EQUNIDADE " 
				+  "WHERE CODEMP=? AND CODFILIAL=? " 
				+  "ORDER BY "+ dl.getValor();
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("EQUNIDADE"));
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow(), 0, imp.normal());
					imp.say(imp.pRow(), 2, "C�d.unid.");
					imp.say(imp.pRow(), 30, "Descri��o");
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(), 0, Funcoes.replicate("-",79));
				}
				imp.say(imp.pRow() + 1, 0, imp.normal());
				imp.say(imp.pRow(), 2, rs.getString("Codunid"));
				imp.say(imp.pRow(), 30, rs.getString("Descunid"));
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
			Funcoes.mensagemErro(this,"Erro na consulta � tabela de UNIDADES!\n"+err.getMessage(),true,con,err);
			err.printStackTrace();
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
