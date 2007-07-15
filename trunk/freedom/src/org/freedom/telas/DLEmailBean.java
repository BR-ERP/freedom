/**
 * @version 05/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)DLEnviaPedido.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Formul�rio para envio do pedido por e-mail.
 */

package org.freedom.telas;

import javax.swing.JLabel;

import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.EmailBean;

public class DLEmailBean extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelRodape = null;

	private final JTextFieldPad txtHost = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtPort = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtFrom = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JCheckBoxPad cbAutenticaSMTP = new JCheckBoxPad( "Autenticar ?", "S", "N" );
	
	private final JCheckBoxPad cbSSLSMTP = new JCheckBoxPad( "Usa SSL ?", "S", "N" );
	
	private EmailBean mail = null;

	public DLEmailBean( EmailBean mail ) {

		super();
		setTitulo( "Configura��es de envio" );
		setAtribos( 405, 240 );
		setResizable( false );

		montaTela();
		
		this.mail = mail;
		
		if ( mail != null) {
			
			txtHost.setVlrString( mail.getHost() );
			txtPort.setVlrInteger( mail.getPorta() );
			txtFrom.setVlrString( mail.getDe() );
			cbAutenticaSMTP.setVlrString( mail.getAutentica() );
			cbSSLSMTP.setVlrString( mail.getSsl() );
		}
	}

	private void montaTela() {

		adic( new JLabel( "Servidor SMTP:" ), 10, 10, 300, 20 );
		adic( txtHost, 10, 30, 300, 20 );
		adic( new JLabel( "Porta" ), 320, 10, 60, 20 );
		adic( txtPort, 320, 30, 60, 20 );
		adic( new JLabel( "Usuario:" ), 10, 50, 370, 20 );
		adic( txtFrom, 10, 70, 370, 20 );
		adic( cbAutenticaSMTP, 10, 100, 370, 20 );
		adic( cbSSLSMTP, 10, 120, 370, 20 );
	}
	
	public EmailBean getEmailBean() {
		
		return mail;
	}

	@Override
	public void ok() {
		
		if ( mail == null ) {		

			mail = new EmailBean();
		}

		mail.setHost( txtHost.getVlrString() );
		mail.setPorta( txtPort.getVlrInteger() );
		mail.setDe( txtFrom.getVlrString() );
		mail.setUsuario( txtFrom.getVlrString() );
		mail.setAutentica( cbAutenticaSMTP.getVlrString() );
		mail.setSsl( cbSSLSMTP.getVlrString() );
		
		super.ok();
	}

	@Override
	public void cancel() {

		mail = null;
		
		super.cancel();
	}	
}
