/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Carlos Eduardo Caetano David <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FEncaminhador.java <BR>
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

package org.freedom.modulos.atd;
import java.sql.Connection;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FEncaminhador extends FDados implements PostListener { 

	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCodEnc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
	private JTextFieldPad txtNomeEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldPad txtEndEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtNumEnc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtBairEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
	private JTextFieldPad txtComplEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
	private JTextFieldPad txtCidEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
	private JTextFieldPad txtCepEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldPad txtFoneEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
	private JTextFieldPad txtFaxEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldPad txtUFEnc = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	
	public FEncaminhador () {
		super();
		setTitulo("Cadastro de Encaminhador");
		setAtribos( 50, 50, 400, 250);
		
		adicCampo(txtCodEnc, 7, 20, 50, 20, "CodEnc", "C�d.enc.", ListaCampos.DB_PK, true);
		adicCampo(txtNomeEnc, 60, 20, 312, 20, "NomeEnc", "Descri��o do encaminhador", ListaCampos.DB_SI, true);
		adicCampo(txtEndEnc, 7, 60, 260, 20, "EndEnc", "Endere�o",ListaCampos.DB_SI, false);
		adicCampo(txtNumEnc, 270, 60, 50, 20, "NumEnc", "Num.", ListaCampos.DB_SI, false);
		adicCampo(txtComplEnc, 323, 60, 49, 20, "ComplEnc", "Compl.", ListaCampos.DB_SI, false);
		adicCampo(txtBairEnc, 7, 100, 120, 20, "BairEnc", "Bairro", ListaCampos.DB_SI, false);
		adicCampo(txtCidEnc, 130, 100, 120, 20, "CidEnc", "Cidade",  ListaCampos.DB_SI, false);
		adicCampo(txtCepEnc, 253, 100, 80, 20, "CepEnc", "Cep", ListaCampos.DB_SI, false);
		adicCampo(txtUFEnc, 336, 100, 36, 20, "UFEnc", "UF", ListaCampos.DB_SI, false);
		adicCampo(txtFoneEnc, 7, 140, 120, 20, "FoneEnc", "Telefone", ListaCampos.DB_SI, false);
		adicCampo(txtFaxEnc, 130, 140, 120, 20, "FaxEnc", "Fax", ListaCampos.DB_SI, false);

		txtFoneEnc.setMascara(JTextFieldPad.MC_FONEDDD);   
		txtFaxEnc.setMascara(JTextFieldPad.MC_FONE);
		lcCampos.addPostListener(this);
		lcCampos.setQueryInsert(false);    
	}
	
	public void beforePost(PostEvent pevt) {	   
		if (txtUFEnc.getText().trim().length() < 2) {
			pevt.cancela();
			Funcoes.mensagemInforma( this,"Campo UF � requerido! ! !");
			txtUFEnc.requestFocus();
		}
	}
	  
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		setListaCampos( true, "ENCAMINHADOR", "AT");
	}
}
