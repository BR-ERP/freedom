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

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
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
public class FPrefere extends FTabDados implements InsertListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelCampanhas = new JPanelPad();
	
	private JPanelPad panelEmail = new JPanelPad();
	
	private JPanelPad panelAtendimentos = new JPanelPad();
	
	private JPanelPad panelPonto = new JPanelPad();

	private JPanelPad pnEmail = new JPanelPad();

	private JTextFieldPad txtSmtpMail = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtUserMail = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtCodAtivTE = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);

	private JTextFieldFK txtDescAtivTE = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtCodAtivCE = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);

	private JTextFieldFK txtDescAtivCE = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtCodEmailNC = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);

	private JTextFieldFK txtDescEmailNC = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodEmailEA = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);

	private JTextFieldFK txtDescEmailEA = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	
	private JTextFieldPad txtCodModel = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
	
	private JTextFieldFK txtDescModAtendo = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	
	private JTextFieldPad txtCodModelME = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
	
	private JTextFieldFK txtDescModAtendoME = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodEmailEC = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);

	private JTextFieldFK txtDescEmailEC = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	
	private JTextFieldPad txtTempoMax = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
	
	private JTextFieldPad txtTolerancia = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
	
	private JPasswordFieldPad txpPassMail = new JPasswordFieldPad(16);

	private JCheckBoxPad cbAutoHorario = new JCheckBoxPad("Data/Hor�rio autom�tico no atendimento?", "S", "N");
	
	private JCheckBoxPad cbMostraCliAtraso = new JCheckBoxPad("Mostra clientes em atraso no painel de controle ?", "S", "N");
	
	private JCheckBoxPad cbBloqueiaCliAtraso = new JCheckBoxPad("Bloquear atendimentos para clientes em atraso ?", "S", "N");
	
	private JCheckBoxPad cbLancaPontoAF = new JCheckBoxPad("Lan�a ponto na abertura e fechamento do sistema ?", "S", "N");

	private ListaCampos lcAtivTE = new ListaCampos(this, "TE");

	private ListaCampos lcAtivCE = new ListaCampos(this, "CE");
	
	private ListaCampos lcEmailNC = new ListaCampos( this, "NC" );
	
	private ListaCampos lcEmailEA = new ListaCampos( this, "EA" );
	
	private ListaCampos lcEmailEC = new ListaCampos( this, "EC" );
	
	private ListaCampos lcModAtendo = new ListaCampos( this, "MI" );
	
	private ListaCampos lcModAtendoME = new ListaCampos( this, "ME" );
	
	public FPrefere() {

		super();
		setTitulo("Prefer�ncias CRM");
		setAtribos(50, 50, 450, 375);

		montaListaCampos();


		/******************
		 * ABA EMAIL
		 *****************/
		
		adicTab("Campanhas", panelCampanhas);

		setPainel(panelCampanhas);

		adicCampo(txtCodAtivCE, 10, 30, 80, 20, "CodAtivCE", "C�d.Ativ.", ListaCampos.DB_FK, txtDescAtivCE, false);
		adicDescFK(txtDescAtivCE, 93, 30, 320, 20, "DescAtiv", "Atividade padr�o para campanha enviada");
		adicCampo(txtCodAtivTE, 10, 70, 80, 20, "CodAtivTE", "C�d.Ativ.", ListaCampos.DB_FK, txtDescAtivTE, false);
		adicDescFK(txtDescAtivTE, 93, 70, 320, 20, "DescAtiv", "Atividade padr�o para tentativa de envio de campanha");

	
		/******************
		 * ABA EMAIL
		 *****************/

		adicTab("Email", panelEmail);
		
		setPainel(panelEmail);
				
		pnEmail.setBorder(BorderFactory.createTitledBorder("Servidor para envio de email"));
		adic(pnEmail, 10, 10, 405, 120);
		setPainel(pnEmail);
		adicCampo(txtSmtpMail, 10, 20, 190, 20, "SmtpMail", "SMTP", ListaCampos.DB_SI, false);
		adicCampo(txtUserMail, 10, 60, 190, 20, "UserMail", "Usuario", ListaCampos.DB_SI, false);
		adicCampo(txpPassMail, 203, 60, 180, 20, "PassMail", "Senha", ListaCampos.DB_SI, false);
		
		/******************
		 * ABA ATENDIMENTOS
		 *****************/

		adicTab("Atendimentos", panelAtendimentos);

		setPainel(panelAtendimentos);
		
		adicDB(cbAutoHorario, 10, 10, 405, 20, "AUTOHORATEND", "", false);
		adicDB(cbMostraCliAtraso, 10, 30, 405, 20, "MOSTRACLIATRASO", "", false);
		adicDB(cbBloqueiaCliAtraso, 10, 50, 405, 20, "BLOQATENDCLIATRASO", "", false);
		
		adicCampo(txtCodEmailNC, 7, 100, 80, 20, "CodEmailNC", "C�d.Email", ListaCampos.DB_FK, txtDescEmailNC, false);
		adicDescFK(txtDescEmailNC, 90, 100, 320, 20, "DescEmail", "Email para notifica��o de chamados ao t�cnico");

		adicCampo(txtCodEmailEA, 7, 140, 80, 20, "CodEmailEA", "C�d.Email", ListaCampos.DB_FK, txtDescEmailEA, false);
		adicDescFK(txtDescEmailEA, 90, 140, 320, 20, "DescEmail", "Email para notifica��o de chamados ao atendente");

		adicCampo(txtCodEmailEC, 7, 180, 80, 20, "CodEmailEC", "C�d.Email", ListaCampos.DB_FK, txtDescEmailEC, false);
		adicDescFK(txtDescEmailEC, 90, 180, 320, 20, "DescEmail", "Email para notifica��o de chamados ao cliente");
		
		adicCampo(txtCodModel, 7, 220, 80, 20, "CodModelMi", "C�d.Model", ListaCampos.DB_FK, txtDescModAtendo, false);
		adicDescFK(txtDescModAtendo, 90, 220, 320, 20, "DescModel", "Descri��o modelo de atendimento para intervalo");
		
			
				
		/******************
		 * ABA PONTO
		 *****************/
		
		adicTab("Ponto", panelPonto);
		
		setPainel(panelPonto);
		
		adicCampo(txtCodModelME, 7, 30, 80, 20, "CodModelMe", "C�d.Model", ListaCampos.DB_FK, txtDescModAtendoME, false);
		adicDescFK(txtDescModAtendoME, 90, 30, 320, 20, "DescModel", "Desc. mod. interv. entre chegada e inic. equip. " );
		adicCampo(txtTempoMax, 7, 70, 140, 20, "TempoMaxInt", "Tempo m�x.int.(min.)", ListaCampos.DB_SI, false); 
		adicCampo(txtTolerancia, 7, 108, 140, 20, "TolRegPonto", "Toler�ncia (min.)", ListaCampos.DB_SI, false); 
		adicDB(cbLancaPontoAF, 7, 130, 340, 20, "LANCAPONTOAF", "", true);
	
	
		
		setListaCampos(false, "PREFERE3", "SG");

		nav.setAtivo(0, false);
		nav.setAtivo(1, false);
	}

	private void montaListaCampos() {

		lcAtivCE.add(new GuardaCampo(txtCodAtivCE, "CodAtiv", "C�d.ativ.", ListaCampos.DB_PK, false));
		lcAtivCE.add(new GuardaCampo(txtDescAtivCE, "DescAtiv", "Descri��o da atividade", ListaCampos.DB_SI, false));
		lcAtivCE.montaSql(false, "ATIVIDADE", "TK");
		lcAtivCE.setReadOnly(true);
		lcAtivCE.setQueryCommit(false);
		txtCodAtivCE.setTabelaExterna(lcAtivCE, null);

		lcAtivTE.add(new GuardaCampo(txtCodAtivTE, "CodAtiv", "C�d.ativ.", ListaCampos.DB_PK, false));
		lcAtivTE.add(new GuardaCampo(txtDescAtivTE, "DescAtiv", "Descri��o da atividade", ListaCampos.DB_SI, false));
		lcAtivTE.montaSql(false, "ATIVIDADE", "TK");
		lcAtivTE.setReadOnly(true);
		lcAtivTE.setQueryCommit(false);
		txtCodAtivTE.setTabelaExterna(lcAtivTE, null);
		
		// Email Notifica��o de T�cnico
		lcEmailNC.add( new GuardaCampo( txtCodEmailNC, "CodEmail", "C�d.Email", ListaCampos.DB_PK, false ) );
		lcEmailNC.add( new GuardaCampo( txtDescEmailNC, "DescEmail", "Descri��o do Email", ListaCampos.DB_SI,  false ) );
		lcEmailNC.montaSql( false, "EMAIL", "TK" );
		lcEmailNC.setQueryCommit( false );
		lcEmailNC.setReadOnly( true );
		txtCodEmailNC.setTabelaExterna(lcEmailNC, null);
		txtCodEmailNC.setNomeCampo( "CodEmail" );
		txtCodEmailNC.setPK( true );
		txtCodEmailNC.setListaCampos( lcEmailNC );
		
		
		// Email Notifica��o de Atendente
		lcEmailEA.add( new GuardaCampo( txtCodEmailEA, "CodEmail", "C�d.Email", ListaCampos.DB_PK, false ) );
		lcEmailEA.add( new GuardaCampo( txtDescEmailEA, "DescEmail", "Descri��o do Email", ListaCampos.DB_SI,  false ) );
		lcEmailEA.montaSql( false, "EMAIL", "TK" );
		lcEmailEA.setQueryCommit( false );
		lcEmailEA.setReadOnly( true );
		txtCodEmailEA.setTabelaExterna(lcEmailEA, null);
		txtCodEmailEA.setNomeCampo( "CodEmail" );
		txtCodEmailEA.setPK( true );
		txtCodEmailEA.setListaCampos( lcEmailEA );
		
		// Email Notifica��o de Cliente
		lcEmailEC.add( new GuardaCampo( txtCodEmailEC, "CodEmail", "C�d.Email", ListaCampos.DB_PK, false ) );
		lcEmailEC.add( new GuardaCampo( txtDescEmailEC, "DescEmail", "Descri��o do Email", ListaCampos.DB_SI,  false ) );
		lcEmailEC.montaSql( false, "EMAIL", "TK" );
		lcEmailEC.setQueryCommit( false );
		lcEmailEC.setReadOnly( true );
		txtCodEmailEC.setTabelaExterna(lcEmailEC, null);
		txtCodEmailEC.setNomeCampo( "CodEmail" );
		txtCodEmailEC.setPK( true );
		txtCodEmailEC.setListaCampos( lcEmailEC );
		
		//Modelo de Atendimento.
		lcModAtendo.add( new GuardaCampo(txtCodModel, "CodModel", "C�d.Model", ListaCampos.DB_PK, false ) );
		lcModAtendo.add( new GuardaCampo(txtDescModAtendo, "DescModel", "Descri��o do Modelo de Atendimento", ListaCampos.DB_SI, false ));
		lcModAtendo.montaSql( false, "MODATENDO", "AT" );
		lcModAtendo.setQueryCommit( false );
		lcModAtendo.setReadOnly( true );
		txtCodModel.setTabelaExterna(lcModAtendo, null);
		txtCodModel.setNomeCampo( "CodModel" );
		txtCodModel.setPK( true );
		txtCodModel.setListaCampos( lcModAtendo );
		
		
		//Modelo de Atendimento - ABA PONTO.
		lcModAtendoME.add( new GuardaCampo(txtCodModelME, "CodModel", "C�d.Model", ListaCampos.DB_PK, false ) );
		lcModAtendoME.add( new GuardaCampo(txtDescModAtendoME, "DescModel", "Descri��o do Modelo de Atendimento", ListaCampos.DB_SI, false ));
		lcModAtendoME.montaSql( false, "MODATENDO", "AT" );
		lcModAtendoME.setQueryCommit( false );
		lcModAtendoME.setReadOnly( true );
		txtCodModelME.setTabelaExterna(lcModAtendoME, null);
		txtCodModelME.setNomeCampo( "CodModel" );
		txtCodModelME.setPK( true );
		txtCodModelME.setListaCampos( lcModAtendoME );
		
	}

	public void setConexao(DbConnection cn) {

		super.setConexao(cn);
		
		lcAtivCE.setConexao(cn);
		lcAtivTE.setConexao(cn);
		lcEmailNC.setConexao(cn);
		lcEmailEA.setConexao(cn);
		lcEmailEC.setConexao(cn);
		lcModAtendo.setConexao(cn);
		lcModAtendoME.setConexao(cn);
		lcCampos.carregaDados();
		
	}
	
	public void afterInsert(InsertEvent ievt) {
		/*
		if (ievt.getListaCampos() == lcCampos) {
			txtTempoMax.setVlrInteger(18);	
		}
		if (ievt.getListaCampos() == lcCampos) {
			txtTolerancia.setVlrInteger(20);	
		}
		*/

	}

	public void beforeInsert(InsertEvent ievt) {
		
	}
}
