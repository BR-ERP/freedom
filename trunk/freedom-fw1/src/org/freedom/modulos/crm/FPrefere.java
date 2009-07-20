/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)FPrefere.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.crm;

import org.freedom.infra.model.jdbc.DbConnection;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FTabDados;

public class FPrefere extends FTabDados {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinMail = new JPanelPad();

	private JPanelPad pinSmtp = new JPanelPad();

	private JTextFieldPad txtSmtpMail = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtUserMail = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodAtivTE = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldFK txtDescAtivTE = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodAtivCE = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldFK txtDescAtivCE = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcAtivTE = new  ListaCampos( this, "TE" );
	
	private ListaCampos lcAtivCE = new  ListaCampos( this, "CE" );
	
	private JPasswordFieldPad txpPassMail = new JPasswordFieldPad( 16 );

	public FPrefere() {

		super();
		setTitulo( "Prefer�ncias do Telemarketing" );
		setAtribos( 50, 50, 450, 375 );

		montaListaCampos();
		
		setPainel( pinMail );
		adicTab( "Mail", pinMail );
		JLabelPad lbServer = new JLabelPad( "  Servidor para envio de email" );
		lbServer.setOpaque( true );
		adic( lbServer, 15, 10, 200, 15 );
		adic( pinSmtp, 10, 15, 380, 150 );
		setPainel( pinSmtp );
		adicCampo( txtSmtpMail, 10, 30, 150, 20, "SmtpMail", "SMTP", ListaCampos.DB_SI, false );
		adicCampo( txtUserMail, 10, 70, 150, 20, "UserMail", "Usuario", ListaCampos.DB_SI, false );
		adicCampo( txpPassMail, 10, 110, 150, 20, "PassMail", "Senha", ListaCampos.DB_SI, false );
		
		setPainel( pinMail );		
		adicCampo( txtCodAtivCE, 10, 185, 80, 20, "CodAtivCE", "C�d.Ativ.", ListaCampos.DB_FK, txtDescAtivCE, false );
		adicDescFK( txtDescAtivCE, 93, 185, 320, 20, "DescAtiv", "Atividade padr�o para capanha enviada" );
		adicCampo( txtCodAtivTE, 10, 225, 80, 20, "CodAtivTE", "C�d.Ativ.", ListaCampos.DB_FK, txtDescAtivTE, false );
		adicDescFK( txtDescAtivTE, 93, 225, 320, 20, "DescAtiv", "Atividade padr�o para tentativa de envio de campanha" );		
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
		txtCodAtivCE.setTabelaExterna( lcAtivCE );
		
		lcAtivTE.add( new GuardaCampo( txtCodAtivTE, "CodAtiv", "C�d.ativ.", ListaCampos.DB_PK, false ) );
		lcAtivTE.add( new GuardaCampo( txtDescAtivTE, "DescAtiv", "Descri��o da atividade", ListaCampos.DB_SI, false ) );
		lcAtivTE.montaSql( false, "ATIVIDADE", "TK" );
		lcAtivTE.setReadOnly( true );
		lcAtivTE.setQueryCommit( false );
		txtCodAtivTE.setTabelaExterna( lcAtivTE );
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcAtivCE.setConexao( cn );
		lcAtivTE.setConexao( cn );
		lcCampos.carregaDados();
		
	}
}
