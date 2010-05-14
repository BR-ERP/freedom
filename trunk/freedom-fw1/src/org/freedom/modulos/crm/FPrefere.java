/*
 * Projeto: Freedom-fw1
 * Pacote: org.freedom.modules.crm
 * Classe: @(#)FPrefere.java
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> 
 */

package org.freedom.modulos.crm;

import javax.swing.BorderFactory;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JPasswordFieldPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FTabDados;

/**
 * 
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues
 * @version 10/10/2009 - Alex Rodrigues
 */
public class FPrefere extends FTabDados {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelGeral = new JPanelPad();

	private JPanelPad panelMail = new JPanelPad();

	private JTextFieldPad txtSmtpMail = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtUserMail = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodAtivTE = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldFK txtDescAtivTE = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodAtivCE = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldFK txtDescAtivCE = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JPasswordFieldPad txpPassMail = new JPasswordFieldPad( 16 );
	
	private JCheckBoxPad cbAutoHorario = new JCheckBoxPad( "Data/Horario autom�tico no atendimento?", "S", "N" ); 

	private ListaCampos lcAtivTE = new  ListaCampos( this, "TE" );
	
	private ListaCampos lcAtivCE = new  ListaCampos( this, "CE" );
	

	public FPrefere() {

		super();
		setTitulo( "Prefer�ncias" );
		setAtribos( 50, 50, 450, 375 );

		montaListaCampos();
		
		adicTab( "Geral", panelGeral );
		
		setPainel( panelGeral );	
		
		adicCampo( txtCodAtivCE, 10, 30, 80, 20, "CodAtivCE", "C�d.Ativ.", ListaCampos.DB_FK, txtDescAtivCE, false );
		adicDescFK( txtDescAtivCE, 93, 30, 320, 20, "DescAtiv", "Atividade padr�o para capanha enviada" );
		adicCampo( txtCodAtivTE, 10, 70, 80, 20, "CodAtivTE", "C�d.Ativ.", ListaCampos.DB_FK, txtDescAtivTE, false );
		adicDescFK( txtDescAtivTE, 93, 70, 320, 20, "DescAtiv", "Atividade padr�o para tentativa de envio de campanha" );	

		adicDB( cbAutoHorario, 10, 100, 405, 20, "AUTOHORATEND", "", false );
		
		panelMail.setBorder( BorderFactory.createTitledBorder( "Servidor para envio de email" ) );
		adic( panelMail, 10, 140, 405, 120 );
		setPainel( panelMail );
		adicCampo( txtSmtpMail, 10, 20, 190, 20, "SmtpMail", "SMTP", ListaCampos.DB_SI, false );
		adicCampo( txtUserMail, 10, 60, 190, 20, "UserMail", "Usuario", ListaCampos.DB_SI, false );
		adicCampo( txpPassMail, 203, 60, 180, 20, "PassMail", "Senha", ListaCampos.DB_SI, false );		
		
		setListaCampos( false, "PREFERE3", "SG" );

		nav.setAtivo( 0, false );
		nav.setAtivo( 1, false );
	}

	private void montaListaCampos(){
		
		lcAtivCE.add( new GuardaCampo( txtCodAtivCE, "CodAtiv", "C�d.ativ.", ListaCampos.DB_PK, false ) );
		lcAtivCE.add( new GuardaCampo( txtDescAtivCE, "DescAtiv", "Descri��o da atividade", ListaCampos.DB_SI, false ) );
		lcAtivCE.montaSql( false, "ATIVIDADE", "TK" );
		lcAtivCE.setReadOnly( true );
		lcAtivCE.setQueryCommit( false );
		txtCodAtivCE.setTabelaExterna( lcAtivCE, null );
		
		lcAtivTE.add( new GuardaCampo( txtCodAtivTE, "CodAtiv", "C�d.ativ.", ListaCampos.DB_PK, false ) );
		lcAtivTE.add( new GuardaCampo( txtDescAtivTE, "DescAtiv", "Descri��o da atividade", ListaCampos.DB_SI, false ) );
		lcAtivTE.montaSql( false, "ATIVIDADE", "TK" );
		lcAtivTE.setReadOnly( true );
		lcAtivTE.setQueryCommit( false );
		txtCodAtivTE.setTabelaExterna( lcAtivTE, null );
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcAtivCE.setConexao( cn );
		lcAtivTE.setConexao( cn );
		lcCampos.carregaDados();
		
	}
}
