/**
 * @version 15/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe: @(#)FGravaMoeda.java <BR>
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

package org.freedom.modulos.pdv;
import java.awt.event.ActionEvent;
import java.sql.Connection;

import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.drivers.JBemaFI32;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDialogo;
public class FGravaMoeda extends FDialogo {
	private JTextFieldPad txtCodMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
	private JTextFieldFK txtSingMoeda = new JTextFieldFK();
	private JTextFieldFK txtPlurMoeda = new JTextFieldFK();
	private JBemaFI32 bf = (FreedomPDV.bECFTerm ? new JBemaFI32() : null);
	private ListaCampos lcMoeda = new ListaCampos(this,"");
	public FGravaMoeda() {
		setTitulo("Ajusta moeda na impressora.");
		setAtribos(390,110);
		
		txtCodMoeda.setTipo(JTextFieldPad.TP_STRING,4,0);
		lcMoeda.add(new GuardaCampo( txtCodMoeda, 7, 100, 80, 20, "CodMoeda", "C�digo", true, false, null, JTextFieldPad.TP_STRING,true),"txtCodMoedax");
		lcMoeda.add(new GuardaCampo( txtSingMoeda, 90, 100, 207, 20, "SingMoeda", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescMoedax");
		lcMoeda.add(new GuardaCampo( txtPlurMoeda, 90, 100, 207, 20, "PlurMoeda", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescMoedax");
		lcMoeda.montaSql(false, "MOEDA", "FN");
		lcMoeda.setQueryCommit(false);
		lcMoeda.setReadOnly(true);
		txtCodMoeda.setFK(true);
		txtCodMoeda.setNomeCampo("CodMoeda");
		txtCodMoeda.setTabelaExterna(lcMoeda);
		
		adic(new JLabel("Sigla"),7,5,50,15);
		adic(txtCodMoeda,7,20,50,20);
		adic(new JLabel("Nome Sing."),60,5,147,15);
		adic(txtSingMoeda,60,20,147,20);
		adic(new JLabel("Nome Plur."),210,5,150,15);
		adic(txtPlurMoeda,210,20,150,20);
	}
	private void gravaMoeda() {
	  if (FreedomPDV.bECFTerm && !bf.programaMoeda(Aplicativo.strUsuario,txtSingMoeda.getVlrString(),txtPlurMoeda.getVlrString(),FreedomPDV.bModoDemo)) {
	  	Funcoes.mensagemErro(this,"Erro ao gravar a moeda!!");
	  }
    }
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			gravaMoeda();
		}
		super.actionPerformed(evt);
	}
	public void setConexao(Connection cn) {
		lcMoeda.setConexao(cn);
	}
}
