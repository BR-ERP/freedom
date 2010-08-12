/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLNovoRec.java <BR>
 * 
 *                    Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                    modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                    na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                    Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                    sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                    Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                    Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                    de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                    Coment�rios sobre a classe...
 */

package org.freedom.modulos.fnc.view.dialog.utility;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.object.Historico;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.Navegador;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.std.view.dialog.utility.DLFechaParcela;

public class DLNovoRec extends FFDialogo implements CarregaListener, PostListener, FocusListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private final JPanelPad pnRec = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad pinCab = new JPanelPad( 580, 210 );

	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCNPJCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private final JTextFieldFK txtDescCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtParcPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtObrigCart = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodCartCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldFK txtDescCartCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodTipoCobItRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoCobItRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodBancoItRec = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtDescBancoItRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodCartCobItRec = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtDescCartCobItRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtDtPrevItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtCodRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtNParcRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtVlrParcItRec = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtVlrDescItRec = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtDtVencItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtVlrParcRec = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtCodPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldFK txtDescPlan = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtDtEmisRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDocRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtObs = new JTextFieldPad( JTextFieldPad.TP_STRING, 250, 0 );

	private final JTextFieldPad txtStatus = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private final JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private final JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private final JTextFieldFK txtSiglaCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );


	private JTextFieldFK txtDescConta = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTablePad tabRec = new JTablePad();

	private final JScrollPane spnTab = new JScrollPane( tabRec );

	private final ListaCampos lcReceber = new ListaCampos( this );

	private final ListaCampos lcItReceber = new ListaCampos( this );

	private final ListaCampos lcCli = new ListaCampos( this, "CL" );

	private final ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private final ListaCampos lcTipoCob = new ListaCampos( this, "TC" );

	private final ListaCampos lcBanco = new ListaCampos( this, "BO" );

	private final ListaCampos lcCartCob = new ListaCampos( this, "CB" );

	private final ListaCampos lcBancoItRec = new ListaCampos( this, "BO" );

	private final ListaCampos lcCartCobItRec = new ListaCampos( this, "CB" );

	private final ListaCampos lcTipoCobItRec = new ListaCampos( this, "TC" );

	private final Navegador navRec = new Navegador( false );

	private final Navegador navItRec = new Navegador( false );

	private ListaCampos lcConta = new ListaCampos( this, "CA" );

	private Component owner = null;

	public static final String HISTORICO_PADRAO = "RECEBIMENTO REF. AO PED.: <DOCUMENTO>";

	private Map<String, Integer> prefere = null;

	private Historico historico = null;
	
	private final ListaCampos lcPlan = new ListaCampos( this, "PN" );
	
	private final ListaCampos lcCC = new ListaCampos( this, "CC" );

	public DLNovoRec( Component cOrig ) {

		super( cOrig );
		this.owner = cOrig;

		setTitulo( "Novo t�tulo para recebimento" );
		setAtribos( 600, 450 );

		lcItReceber.setMaster( lcReceber );
		lcReceber.adicDetalhe( lcItReceber );
		lcItReceber.setTabela( tabRec );

		navRec.setName( "Receber" );
		lcReceber.setNavegador( navRec );
		navItRec.setName( "itpagar" );
		lcItReceber.setNavegador( navItRec );

		montaListaCampos();

		montaTela();

		lcItReceber.montaTab();

		tabRec.setTamColuna( 40, 0 ); // N�mero da parcela
		tabRec.setTamColuna( 60, 1 ); // Valor total da parcela
		tabRec.setTamColuna( 65, 2 ); // Vencimento
		tabRec.setTamColuna( 65, 3 ); // Data prevista
		tabRec.setTamColuna( 60, 4 ); // Valor do desconto
		tabRec.setTamColuna( 40, 5 ); // C�d. tipo de cobran�a
		tabRec.setTamColuna( 100, 6 ); // Descri��o do tipo de cobran�a.
		tabRec.setTamColuna( 40, 7 ); // C�digo do banco.
		tabRec.setTamColuna( 100, 8 ); // Descri��o do nome do banco
		tabRec.setTamColuna( 40, 9 ); // C�digo da carteira de cobran�a.
		tabRec.setTamColuna( 100, 10 ); // Descri��o da carteira de cobrana�a.

		lcReceber.addPostListener( this );
		lcTipoCob.addCarregaListener( this );

		txtCodTipoCob.addFocusListener( this );
		tabRec.addMouseListener( this );

	}

	private void montaListaCampos() {

		/***************
		 * FNCLIENTE *
		 ***************/
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtDescCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCNPJCli, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli, null );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		/****************
		 * FNPLANOPAG *
		 ****************/
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.add( new GuardaCampo( txtParcPlanoPag, "ParcPlanoPag", "Nro. parcelas", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag, null );
		txtCodPlanoPag.setFK( true );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );

		/***************
		 * FNTIPOCOB *
		 ***************/
		lcTipoCob.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "C�d.Tip.Cob", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTipoCob, "DescTipoCob", "Descri��o do tipo de cobran�a", ListaCampos.DB_SI, false ) );
		lcTipoCob.add( new GuardaCampo( txtObrigCart, "ObrigCartCob", "Obriga cart.cob.", ListaCampos.DB_SI, false ) );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCob.setQueryCommit( false );
		lcTipoCob.setReadOnly( true );
		txtCodTipoCob.setTabelaExterna( lcTipoCob, null );
		txtCodTipoCob.setFK( true );
		txtCodTipoCob.setNomeCampo( "CodTipoCob" );

		/***************
		 * FNCARTCOB *
		 ***************/
		lcCartCob.add( new GuardaCampo( txtCodCartCob, "CodCartCob", "C�d.Cart.Cob.", ListaCampos.DB_PK, false ) );
		// lcCartCob.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PF, false ) );
		lcCartCob.add( new GuardaCampo( txtDescCartCob, "DescCartCob", "Descri��o da carteira de cobran�a", ListaCampos.DB_SI, false ) );
		lcCartCob.montaSql( false, "CARTCOB", "FN" );
		lcCartCob.setQueryCommit( false );
		lcCartCob.setReadOnly( true );
		txtCodCartCob.setTabelaExterna( lcCartCob, null );
		txtCodCartCob.setFK( true );
		txtCodCartCob.setNomeCampo( "CodCartCob" );

		/*************
		 * FNBANCO *
		 *************/
		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco, null );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );

		/**********************
		 * FNCONTA *
		 **********************/
		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "N� Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descri��o da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setQueryCommit( false );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta, null );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );
		txtDescConta.setTabelaExterna( lcConta, null );
		txtDescConta.setLabel( "Descri��o da Conta" );
		
		/************************
		 * FNPLANEJAMENTO       *
		 ************************/
		lcPlan.add( new GuardaCampo( txtCodPlan, "CodPlan", "C�d.plan.", ListaCampos.DB_PK, false ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descri��o do planejamento", ListaCampos.DB_SI, false ) );
		lcPlan.setWhereAdic( "TIPOPLAN = 'R' AND NIVELPLAN = 6" );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlan.setReadOnly( true );
		txtCodPlan.setTabelaExterna( lcPlan, null );
		txtCodPlan.setFK( true );
		txtCodPlan.setNomeCampo( "CodPlan" );
		
		/***************
		 * FNCC        *
		 ***************/
		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "C�d.c.c.", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descri��o", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano-Base", ListaCampos.DB_PK, false ) );
		lcCC.setReadOnly( true );
		lcCC.setQueryCommit( false );
		lcCC.setWhereAdic( "NIVELCC=10" );
		lcCC.montaSql( false, "CC", "FN" );
		txtCodCC.setTabelaExterna( lcCC, null );
		txtCodCC.setFK( true );
		txtCodCC.setNomeCampo( "CodCC" );
		txtAnoCC.setTabelaExterna( lcCC, null );
		txtAnoCC.setFK( true );
		txtAnoCC.setNomeCampo( "AnoCC" );

		lcCC.addCarregaListener( this );

		
		/***************
		 * FNRECEBER *
		 ***************/
		lcReceber.add( new GuardaCampo( txtCodRec, "CodRec", "C�d.rec.", ListaCampos.DB_PK, true ) );
		lcReceber.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_FK, true ) );
		lcReceber.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_FK, true ) );
		lcReceber.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_FK, false ) );
		lcReceber.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "C�d.tp.cob.", ListaCampos.DB_FK, false ) );
		lcReceber.add( new GuardaCampo( txtVlrParcRec, "VlrParcRec", "Valor parc.", ListaCampos.DB_SI, true ) );
		lcReceber.add( new GuardaCampo( txtVlrParcRec, "VlrAPagRec", "Valor a rec.", ListaCampos.DB_SI, true ) );
		lcReceber.add( new GuardaCampo( txtVlrParcRec, "VlrRec", "Valor tot.", ListaCampos.DB_SI, true ) );
		lcReceber.add( new GuardaCampo( txtDtEmisRec, "DataRec", "Dt.emiss�o", ListaCampos.DB_SI, true ) );
		lcReceber.add( new GuardaCampo( txtDocRec, "DocRec", "N.doc.", ListaCampos.DB_SI, true ) );
		lcReceber.add( new GuardaCampo( txtObs, "ObsRec", "Obs.", ListaCampos.DB_SI, false ) );
		lcReceber.add( new GuardaCampo( txtStatus, "StatusRec", "Status", ListaCampos.DB_SI, false ) );
		lcReceber.add( new GuardaCampo( txtCodConta, "NumConta", "C�d.Conta", ListaCampos.DB_FK, txtDescConta, false ) );

		lcReceber.add( new GuardaCampo( txtCodPlan, "CodPlan", "C�d.Plan.", ListaCampos.DB_FK, txtDescPlan, false ) ); 
		lcReceber.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano.C.C.", ListaCampos.DB_SI, txtDescCC, false ) );
		lcReceber.add( new GuardaCampo( txtCodCC, "CodCC", "C�d.C.C.", ListaCampos.DB_FK, txtDescCC, false ) );

		lcReceber.montaSql( true, "RECEBER", "FN" );

		/************************
		 * FNTIPOCOB - DETALHE *
		 ************************/
		lcTipoCobItRec.add( new GuardaCampo( txtCodTipoCobItRec, "CodTipoCob", "C�d.Tip.Cob", ListaCampos.DB_PK, false ) );
		lcTipoCobItRec.add( new GuardaCampo( txtDescTipoCobItRec, "DescTipoCob", "Descri��o tipo de cobran�a", ListaCampos.DB_SI, false ) );
		lcTipoCobItRec.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCobItRec.setQueryCommit( false );
		lcTipoCobItRec.setReadOnly( true );
		txtCodTipoCobItRec.setTabelaExterna( lcTipoCobItRec, null );
		txtCodTipoCobItRec.setFK( true );
		txtCodTipoCobItRec.setNomeCampo( "CodTipoCob" );
		txtDescTipoCobItRec.setTabelaExterna( lcTipoCobItRec, null );
		txtDescTipoCobItRec.setLabel( "Descri��o do tipo de cobran�a" );

		/***************
		 * FNCARTCOB *
		 ***************/
		txtCodCartCobItRec.setNomeCampo( "CodCartCob" );
		lcCartCobItRec.add( new GuardaCampo( txtCodCartCobItRec, "CodCartCob", "C�d.Cart.Cob.", ListaCampos.DB_PK, false ) );
		lcCartCobItRec.add( new GuardaCampo( txtDescCartCobItRec, "DescCartCob", "Descri��o da carteira de cobran�a", ListaCampos.DB_SI, false ) );
		lcCartCobItRec.setWhereAdicSubSel( "CODBANCO=master.CODBANCO" );
		lcCartCobItRec.montaSql( false, "CARTCOB", "FN" );
		lcCartCobItRec.setQueryCommit( false );
		lcCartCobItRec.setReadOnly( true );
		txtCodCartCobItRec.setTabelaExterna( lcCartCobItRec, null );
		txtCodCartCobItRec.setListaCampos( lcCartCobItRec );
		txtDescCartCobItRec.setListaCampos( lcCartCobItRec );
		txtDescCartCobItRec.setLabel( "Descri��o da carteira de cobran�a" );
		txtCodCartCobItRec.setFK( true );

		/**********************
		 * FNBANCO - DETALE *
		 **********************/
		lcBancoItRec.add( new GuardaCampo( txtCodBancoItRec, "CodBanco", "C�d.Tip.Cob", ListaCampos.DB_PK, false ) );
		lcBancoItRec.add( new GuardaCampo( txtDescBancoItRec, "NomeBanco", "Descri��o tipo de cobran�a", ListaCampos.DB_SI, false ) );
		lcBancoItRec.montaSql( false, "BANCO", "FN" );
		lcBancoItRec.setQueryCommit( false );
		lcBancoItRec.setReadOnly( true );
		txtCodBancoItRec.setTabelaExterna( lcBancoItRec, null );
		txtCodBancoItRec.setFK( true );
		txtCodBancoItRec.setNomeCampo( "CodBanco" );
		txtDescBancoItRec.setTabelaExterna( lcBancoItRec, null );
		txtDescBancoItRec.setLabel( "Descri��o do Banco" );

		/*****************
		 * FNITRECEBER *
		 *****************/
		txtNParcRec.setNomeCampo( "NParcRec" );
		lcItReceber.add( new GuardaCampo( txtNParcRec, "NParcItRec", "N.parc.", ListaCampos.DB_PK, false ) );
		lcItReceber.add( new GuardaCampo( txtVlrParcItRec, "VlrParcItRec", "Valor tot.", ListaCampos.DB_SI, false ) );
		lcItReceber.add( new GuardaCampo( txtDtVencItRec, "DtVencItRec", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcItReceber.add( new GuardaCampo( txtDtPrevItRec, "DtPrevItRec", "Dt.Prev.", ListaCampos.DB_SI, false ) );
		lcItReceber.add( new GuardaCampo( txtVlrDescItRec, "VlrDescItRec", "Valor desc.", ListaCampos.DB_SI, false ) );
		lcItReceber.add( new GuardaCampo( txtCodTipoCobItRec, "CodTipoCob", "C�d.Tipo.Cob", ListaCampos.DB_FK, txtDescTipoCobItRec, false ) );
		lcItReceber.add( new GuardaCampo( txtCodBancoItRec, "CodBanco", "C�d.Banco", ListaCampos.DB_FK, txtDescBancoItRec, false ) );
		lcItReceber.add( new GuardaCampo( txtCodCartCobItRec, "CodCartCob", "C�d.Cart.Cob.", ListaCampos.DB_FK, txtDescCartCobItRec, false ) );

		lcItReceber.montaSql( false, "ITRECEBER", "FN" );
		lcItReceber.setQueryCommit( false );
		txtNParcRec.setListaCampos( lcItReceber );
		txtVlrParcItRec.setListaCampos( lcItReceber );
		txtVlrDescItRec.setListaCampos( lcItReceber );
		txtDtVencItRec.setListaCampos( lcItReceber );
		txtCodTipoCobItRec.setListaCampos( lcItReceber );
		txtCodBancoItRec.setListaCampos( lcItReceber );
		txtCodCartCobItRec.setListaCampos( lcItReceber );
		
		
		


	}

	private void montaTela() {

		c.add( pnRec );

		pnRec.add( pinCab, BorderLayout.NORTH );
		pnRec.add( spnTab, BorderLayout.CENTER );

		setPainel( pinCab );
		adic( new JLabelPad( "C�d.cli." ), 7, 0, 250, 20 );
		adic( txtCodCli, 7, 20, 80, 20 );
		adic( new JLabelPad( "Raz�o social do cliente" ), 90, 0, 250, 20 );
		adic( txtDescCli, 90, 20, 197, 20 );
		adic( new JLabelPad( "C�d.p.pag." ), 290, 0, 250, 20 );
		adic( txtCodPlanoPag, 290, 20, 80, 20 );
		adic( new JLabelPad( "Descri��o do plano de pagto." ), 373, 0, 250, 20 );
		adic( txtDescPlanoPag, 373, 20, 200, 20 );

		adic( new JLabelPad( "Cod.Tip.Cob." ), 7, 40, 250, 20 );
		adic( txtCodTipoCob, 7, 60, 80, 20 );
		adic( new JLabelPad( "Descri��o Tipo Cobran�a" ), 90, 40, 200, 20 );
		adic( txtDescTipoCob, 90, 60, 197, 20 );

		adic( new JLabelPad( "Cod.Cart.Cob." ), 290, 40, 250, 20 );
		adic( txtCodCartCob, 290, 60, 80, 20 );
		adic( new JLabelPad( "Descri��o da Carteira de Cob." ), 373, 40, 200, 20 );
		adic( txtDescCartCob, 373, 60, 200, 20 );

		adic( new JLabelPad( "C�d.catg." ), 7, 80, 80, 20 );
		adic( txtCodPlan, 7, 100, 80, 20 );
		adic( new JLabelPad( "Descri��o da categoria" ), 90, 80, 250, 20 );
		adic( txtDescPlan, 90, 100, 197, 20 );

		adic( new JLabelPad( "C�d.c.c." ), 290, 80, 80, 20 );
		adic( txtCodCC, 290, 100, 80, 20 );
		adic( new JLabelPad( "Descri��o do centro de custo" ), 373, 80, 200, 20 );
		adic( txtDescCC, 373, 100, 200, 20 );
		
		adic( new JLabelPad( "C�d.banco" ), 7, 120, 250, 20 );
		adic( txtCodBanco, 7, 140, 80, 20 );
		adic( new JLabelPad( "Descri�ao do banco" ), 90, 120, 200, 20 );
		adic( txtDescBanco, 90, 140, 197, 20 );

		adic( new JLabelPad( "N� Conta" ), 290, 120, 250, 20 );
		adic( txtCodConta, 290, 140, 80, 20 );
		adic( new JLabelPad( "Descri��o da conta" ), 373, 120, 250, 20 );
		adic( txtDescConta, 373, 140, 200, 20 );

		adic( new JLabelPad( "Valor" ), 7, 160, 80, 20 );
		adic( txtVlrParcRec, 7, 180, 80, 20 );

		adic( new JLabelPad( "Dt.Emiss�o" ), 90, 160, 80, 20 );
		adic( txtDtEmisRec, 90, 180, 80, 20 );

		adic( new JLabelPad( "Doc." ), 173, 160, 114, 20 );
		adic( txtDocRec, 173, 180, 114, 20 );

		adic( new JLabelPad( "Observa��es" ), 290, 160, 284, 20 );
		adic( txtObs, 290, 180, 284, 20 );
	}

	private void testaCodRec() { // Traz o verdadeiro n�mero do codrec

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement( "SELECT * FROM SPGERANUM(?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setString( 3, "RC" );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				txtCodRec.setVlrString( rs.getString( 1 ) );
			}

			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao confirmar c�digo da conta a receber!\n" + err.getMessage(), true, con, err );
		}
	}

	private void setCarteira() {

		if ( ( txtCodBanco.getVlrString() != null && txtCodBanco.getVlrString().trim().length() > 0 ) && ( txtCodCartCob.getVlrString() != null && txtCodCartCob.getVlrString().trim().length() > 0 ) ) {

			try {

				String sql = "UPDATE FNITRECEBER SET CODCARTCOB=?, CODEMPCB=?, CODFILIALCB=? WHERE CODEMP=? AND CODFILIAL=? AND CODREC=?";

				PreparedStatement ps = con.prepareStatement( sql );
				ps.setString( 1, txtCodCartCob.getVlrString() );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
				ps.setInt( 4, Aplicativo.iCodEmp );
				ps.setInt( 5, ListaCampos.getMasterFilial( "FNRECEBER" ) );
				ps.setInt( 6, txtCodRec.getVlrInteger() );
				ps.executeUpdate();

				ps.close();

				con.commit();

				lcReceber.carregaDados();
			} catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao definir carteira de cobran�a.\n" + e.getMessage() );
			}
		}
	}

	private void alteraRec() {

		lcItReceber.edit();

		DLFechaParcela dl = new DLFechaParcela( this, con );

		Object[] valores = new Object[] { txtVlrParcItRec.getVlrBigDecimal(), txtDtVencItRec.getVlrDate(), txtVlrDescItRec.getVlrBigDecimal(), txtCodTipoCobItRec.getVlrInteger(), txtCodBancoItRec.getVlrString(), txtCodCartCobItRec.getVlrString(), "N", txtDtPrevItRec.getVlrDate() };

		try {

			dl.setValores( valores );
			dl.setVisible( true );

			if ( dl.OK ) {

				valores = dl.getValores();

				txtVlrParcItRec.setVlrBigDecimal( (BigDecimal) valores[ DLFechaParcela.EFields.VALOR.ordinal() ] );
				txtDtVencItRec.setVlrDate( (Date) valores[ DLFechaParcela.EFields.DATA.ordinal() ] );
				txtDtPrevItRec.setVlrDate( (Date) valores[ DLFechaParcela.EFields.DATAPREV.ordinal() ] );
				txtVlrDescItRec.setVlrBigDecimal( (BigDecimal) valores[ DLFechaParcela.EFields.DESCONTO.ordinal() ] );
				txtCodTipoCobItRec.setVlrString( (String) valores[ DLFechaParcela.EFields.TIPOCOB.ordinal() ] );
				txtCodBancoItRec.setVlrString( (String) dl.getValores()[ DLFechaParcela.EFields.BANCO.ordinal() ] );
				txtCodCartCobItRec.setVlrString( (String) dl.getValores()[ DLFechaParcela.EFields.CARTCOB.ordinal() ] );

				if ( lcItReceber.post() ) {
					// Atualiza lcReceber
					if ( lcReceber.getStatus() == ListaCampos.LCS_EDIT ) {
						lcReceber.post(); // Caso o lcReceber estaja como edit executa o post que atualiza
					}
					else {
						lcReceber.carregaDados(); // Caso n�o, atualiza
					}
				}
				dl.dispose();
			}
			else {
				dl.dispose();
				lcItReceber.cancel( true );
				lcReceber.carregaDados();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao atualizar parcelas.\n" + e.getMessage() );
			lcItReceber.cancel( true );
			lcReceber.cancel( true );
		}
	}

	private boolean isValido() {

		if ( txtCodCli.getVlrString().trim().length() == 0 ) {
			Funcoes.mensagemErro( this, "C�d.cli. � requerido!" );
			txtCodCli.requestFocus();
			return false;
		}

		if ( txtCodPlanoPag.getVlrString().trim().length() == 0 ) {
			Funcoes.mensagemErro( this, "C�d.p.pag. � requerido!" );
			txtCodPlanoPag.requestFocus();
			return false;
		}
		if ( "S".equalsIgnoreCase( txtObrigCart.getVlrString() ) ) {
			if ( txtCodBanco.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "C�d.banco � requerido!" );
				txtCodBanco.requestFocus();
				return false;
			}
			else if ( txtCodCartCob.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "C�d.cart.cob. � requerido!" );
				txtCodCartCob.requestFocus();
				return false;
			}
		}
		if ( txtVlrParcRec.getVlrString().trim().length() == 0 ) {
			Funcoes.mensagemErro( this, "Valor � requerido!" );
			txtVlrParcRec.requestFocus();
			return false;
		}
		if ( txtDtEmisRec.getVlrString().length() < 10 ) {
			Funcoes.mensagemErro( this, "Data de emiss�o � requerido!" );
			txtDtEmisRec.requestFocus();
			return false;
		}
		if ( txtDocRec.getVlrString().trim().length() == 0 ) {
			Funcoes.mensagemErro( this, "Doc. � requerido!" );
			txtDocRec.requestFocus();
			return false;
		}
		return true;
	}

	public void focusGained( FocusEvent e ) {

	}

	public void focusLost( FocusEvent e ) {

		if ( e.getSource() == txtCodTipoCob ) {

			lcTipoCob.carregaDados();

			if ( !"S".equalsIgnoreCase( txtObrigCart.getVlrString() ) ) {
				txtCodBanco.setRequerido( false );
				txtCodCartCob.setRequerido( false );
			}
		}
	}

	public void afterCarrega( CarregaEvent e ) {

		if ( e.getListaCampos() == lcTipoCob && "S".equalsIgnoreCase( txtObrigCart.getVlrString() ) ) {
			txtCodBanco.setRequerido( true );
			txtCodCartCob.setRequerido( true );
		}
		/*
		 * else if (e.getListaCampos() == lcCli ) { mostraRestricao(); }
		 */
	}

	private boolean mostraRestricao() {

		return DLRestrCli.execRestrCli( this.owner, con, txtCodCli.getVlrInteger() );
	}

	public void beforeCarrega( CarregaEvent e ) {

	}

	public void beforePost( PostEvent evt ) {

		if ( ( evt.getListaCampos().equals( lcReceber ) ) & ( lcReceber.getStatus() == ListaCampos.LCS_INSERT ) ) {

			testaCodRec();
			txtStatus.setVlrString( "R1" );

			// Gerando hist�rico din�mico

			Integer codhistrec = null;

			codhistrec = (Integer) prefere.get( "codhistrec" );

			if ( codhistrec != 0 ) {
				historico = new Historico( codhistrec, con );
			}
			else {
				historico = new Historico();
				historico.setHistoricocodificado( HISTORICO_PADRAO );
			}

			historico.setData( txtDtEmisRec.getVlrDate() );
			historico.setDocumento( txtDocRec.getVlrString() );
			historico.setPortador( txtDescCli.getVlrString() );
			historico.setValor( txtVlrParcRec.getVlrBigDecimal() );
			historico.setHistoricoant( txtObs.getVlrString() );

			txtObs.setVlrString( historico.getHistoricodecodificado() );

		}
	}

	public void afterPost( PostEvent evt ) {

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( isValido() ) {
				if ( lcReceber.getStatus() == ListaCampos.LCS_INSERT ) {
					if ( !mostraRestricao() ) {
						return;
					}
					if ( lcReceber.post() ) {
						setCarteira();
					}
				}
				else {
					super.actionPerformed( evt );
				}
			}
		}
		else {
			super.actionPerformed( evt );
		}
	}

	private Map<String, Integer> getPrefere() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer anocc = null;
		Integer codhistrec = null;

		Map<String, Integer> retorno = new HashMap<String, Integer>();

		try {

			ps = con.prepareStatement( "SELECT ANOCENTROCUSTO,CODHISTREC FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				anocc = rs.getInt( "ANOCENTROCUSTO" );
				codhistrec = rs.getInt( "CODHISTREC" );
			}

			retorno.put( "codhistrec", codhistrec );
			retorno.put( "anocc", anocc );

			rs.close();
			ps.close();

			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
		}
		return retorno;
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcReceber.setConexao( cn );
		lcItReceber.setConexao( cn );
		lcTipoCob.setConexao( cn );
		lcBanco.setConexao( cn );
		lcCartCob.setConexao( cn );
		lcTipoCobItRec.setConexao( cn );
		lcBancoItRec.setConexao( cn );
		lcCartCobItRec.setConexao( cn );
		lcConta.setConexao( cn );
		lcPlan.setConexao( cn );
		lcCC.setConexao( cn );
		
		
		lcReceber.insert( true );

		prefere = getPrefere();
	}

	public void mouseClicked( MouseEvent mevt ) {

		if ( mevt.getSource() == tabRec ) {
			if ( ( mevt.getClickCount() == 2 ) & ( tabRec.getLinhaSel() >= 0 ) ) {
				alteraRec();
			}
		}
	}

	public void mouseEntered( MouseEvent arg0 ) {

		// TODO Auto-generated method stub

	}

	public void mouseExited( MouseEvent arg0 ) {

		// TODO Auto-generated method stub

	}

	public void mousePressed( MouseEvent arg0 ) {

		// TODO Auto-generated method stub

	}

	public void mouseReleased( MouseEvent arg0 ) {

		// TODO Auto-generated method stub

	}
}
