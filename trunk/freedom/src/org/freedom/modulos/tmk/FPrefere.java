/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)FPrefere.java <BR>
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
import java.sql.Connection;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FTabDados;

public class FPrefere extends FTabDados {
	private JPanelPad pinMail = new JPanelPad();
	private JPanelPad pinSmtp = new JPanelPad();
	private JTextFieldPad txtSmtpMail = new JTextFieldPad(JTextFieldPad.TP_STRING, 40 , 0);
	private JTextFieldPad txtUserMail = new JTextFieldPad(JTextFieldPad.TP_STRING, 40 , 0);
	private JPasswordFieldPad txpPassMail = new JPasswordFieldPad(16);
	public FPrefere() {
		setTitulo("Prefer�ncias do Telemarketing");
		setAtribos(50, 50, 355, 375);
		
		setPainel(pinMail);
		adicTab("Mail", pinMail);
		JLabelPad lbServer = new JLabelPad("  Servidor para envio de email");
		lbServer.setOpaque(true);
		adic(lbServer,15,10,200,15);
		adic(pinSmtp,10,15,220,160);
		setPainel(pinSmtp);
		adicCampo(txtSmtpMail,10,30,150,20,"SmtpMail","SMTP", ListaCampos.DB_SI, false);
		adicCampo(txtUserMail,10,70,150,20,"UserMail","Usuario", ListaCampos.DB_SI,false);
		adicCampo(txpPassMail,10,110,150,20,"PassMail","Senha",ListaCampos.DB_SI,false);
		setListaCampos(false, "PREFERE3", "SG");
		
		nav.setAtivo(0,false);
		nav.setAtivo(1,false);
	}
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcCampos.carregaDados();
	}
}