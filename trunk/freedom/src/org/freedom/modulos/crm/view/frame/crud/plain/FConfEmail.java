/**
 * @version 20/01/2009 <BR>
 * @author Setpoint Inform�tica Ltda.
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.crm <BR>
 * Classe: @(#)FConfEmail.java <BR>
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
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.plain;

import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JPasswordFieldPad;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FConfEmail extends FDados{

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodConfEmail = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 15, 0 );
	
	private JTextFieldPad txtEmailRemet = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );
	
	private JTextFieldPad txtNomeRemet = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtUsuarioRemet = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JPasswordFieldPad txtSenhaRemet = new JPasswordFieldPad( 40 );
	
	private JTextFieldPad txtEmailResp = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );
	
	private JTextFieldPad txtHostSmpt = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtUsaSSL = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldPad txtUsaAutSMTP = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldPad txtPortaSMTP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 15, 0 );
	
	private final JCheckBoxPad cbUsaSSL = new JCheckBoxPad( "Usa SSL ?", "S", "N" );
	
	private final JCheckBoxPad cbUsaAutSmtp = new JCheckBoxPad( "Autentica ?", "S", "N" );
	
	private JTextAreaPad txaAssinatRemet = new JTextAreaPad( 1000 );
	
	public FConfEmail(){
		
		super();
		setTitulo( "Configura��o de email" );
		setAtribos( 15, 30, 433, 460 );
		montaTela();		
		
	}
	
	private void montaTela(){
		
		adicCampo( txtCodConfEmail, 7, 25, 70, 20, "CodConfEmail", "C�d.Conf.", ListaCampos.DB_PK, true );
		adicCampo( txtNomeRemet, 80, 25, 330, 20, "NomeRemet", "Nome remetente", ListaCampos.DB_SI, false );
		adicCampo( txtEmailRemet, 7, 65, 200, 20, "EmailRemet", "Email remetente", ListaCampos.DB_SI, false );
		adicCampo( txtEmailResp, 210, 65, 200, 20, "EmailResp", "Email resposta", ListaCampos.DB_SI, false );
		adicCampo( txtHostSmpt, 7, 105, 300, 20, "HostSmtp", "Host SMTP", ListaCampos.DB_SI, false );
		adicCampo( txtPortaSMTP, 310, 105, 100, 20, "PortaSmtp", "Porta SMTP", ListaCampos.DB_SI, false );
		adicCampo( txtUsuarioRemet, 7, 145, 100, 20, "UsuarioRemet", "Usu�rio", ListaCampos.DB_SI, false );		
		adicCampo( txtSenhaRemet, 110, 145, 95, 20, "SenhaRemet", "Senha", ListaCampos.DB_SI, false );
		adicDB( txaAssinatRemet, 7, 185, 402, 195, "AssinatRemet", "Assinatura", false );
		
		
		adicDB( cbUsaSSL, 208, 145, 95, 20, "UsaSSL", "", false );
		adicDB( cbUsaAutSmtp, 300, 145, 130, 20, "UsaAutSMTP", "", false );				
		setListaCampos( true, "CONFEMAIL", "TK" );
	}
}
