/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.fnc <BR>
 *         Classe:
 * @(#)FPrefereFNC.java <BR>
 * 
 *                        Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                        modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                        na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                        Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                        sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                        Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                        Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                        de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                        Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.fnc.view.frame.crud.tabbed;

import java.util.Vector;

import javax.swing.BorderFactory;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FTabDados;
import org.freedom.modulos.fnc.library.swing.component.JTextFieldPlan;
import org.freedom.modulos.std.view.frame.crud.detail.FPlanoPag;
import org.freedom.modulos.std.view.frame.crud.detail.FTabJuros;
import org.freedom.modulos.std.view.frame.crud.special.FPlanejamento;
import org.freedom.modulos.std.view.frame.crud.tabbed.FMoeda;

public class FPrefereFNC extends FTabDados implements InsertListener{

	private static final long serialVersionUID = 1L;

	private JPanelPad pinGeral = new JPanelPad( 330, 350 );

	private JPanelPad pinFin = new JPanelPad();
	
	private JPanelPad pinPadroes = new JPanelPad();

	private JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtDescMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private ListaCampos lcMoeda = new ListaCampos( this, "MO" );
	
	private ListaCampos lcHistRec = new ListaCampos(this, "HISTREC");

	private ListaCampos lcHistPag = new ListaCampos(this, "HISTPAG");
	
	private ListaCampos lcHistCNAB = new ListaCampos(this, "HC");
	
	private JTextFieldPad txtCodHistRec = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtDescHistRec = new JTextFieldFK(JTextFieldPad.TP_STRING, 80, 0);

	private JTextFieldPad txtCodHistPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtDescHistCNAB = new JTextFieldFK(JTextFieldPad.TP_STRING, 80, 0);

	private JTextFieldPad txtCodHistCNAB = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtDescHistPag = new JTextFieldFK(JTextFieldPad.TP_STRING, 80, 0);
	
	private JRadioGroup<String, String> rgTipoCred = null;
	
	private JPanelPad pnDefinicoes = new JPanelPad();

	private JPanelPad panelFinContas = new JPanelPad();

	private JLabelPad lbCtbCont = new JLabelPad();

	private JLabelPad lbPrcCont = new JLabelPad();
	
	private JTextFieldPad txtCodTabJuros = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtDescTabJuros = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodPlanoPagSV = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtDescPlanoPagSV = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	
	private JTextFieldPlan txtCodPlanJR = new JTextFieldPlan(JTextFieldPad.TP_STRING, 13, 0);

	private JTextFieldFK txtDescPlanJR = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPlan txtCodPlanJP = new JTextFieldPlan(JTextFieldPad.TP_STRING, 13, 0);

	private JTextFieldFK txtDescPlanJP = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPlan txtCodPlanDC = new JTextFieldPlan(JTextFieldPad.TP_STRING, 13, 0);

	private JTextFieldFK txtDescPlanDC = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPlan txtCodPlanDR = new JTextFieldPlan(JTextFieldPad.TP_STRING, 13, 0);

	private JTextFieldFK txtDescPlanDR = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPlan txtCodPlanPC = new JTextFieldPlan(JTextFieldPad.TP_STRING, 13, 0);

	private JTextFieldFK txtDescPlanPC = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JPanelPad pnOpcoes = new JPanelPad();

	private JPanelPad pnContrato = new JPanelPad();

	private JPanelPad pnFinLibCred = new JPanelPad();
	
	private JPanelPad pnTPNossoNumero = new JPanelPad();
	
	private JPanelPad pnIdentCliBco = new JPanelPad();
	
	private JPanelPad pnPeriodoConsultaCH = new JPanelPad();
	
	private final JCheckBoxPad cbAltItRecImpBol = new JCheckBoxPad("Atualiza parcela na impress�o do boleto.", "S", "N");

	private final JCheckBoxPad cbEstItRecAltDtVenc = new JCheckBoxPad("Estorna parcela na altera��o da data de vencimento.", "S", "N");
	
	private final JCheckBoxPad cbJurosPosCalc = new JCheckBoxPad("Juros p�s-calculado.", "S", "N");
	
	private final JCheckBoxPad cbLiberacaoCreGlobal = new JCheckBoxPad("Libera��o de cr�dito globalizado.", "S", "N");
	
	private final JCheckBoxPad cbAlinhaTelaLanca = new JCheckBoxPad("Alinha tela de lan�amentos.", "S", "N");
	
	private JCheckBoxPad cbGeraPagEmis = new JCheckBoxPad("Gera contas a pagar a partir da data de emiss�o.", "S", "N");

	private JCheckBoxPad cbGeraRecEmis = new JCheckBoxPad("Gera contas a receber a partir da data de emiss�o.", "S", "N");

	private JCheckBoxPad cbFechaCaixa = new JCheckBoxPad("Habilitar bloqueio de caixas.", "S", "N");

	private JCheckBoxPad cbFechaCaixaAuto = new JCheckBoxPad("Efetua bloqueio autom�tico.", "S", "N");
	
	private final JCheckBoxPad cbMatricEmpFn = new JCheckBoxPad("Matr�cula do empregado nos lan�amentos financeiros.", "S", "N");

	private final JCheckBoxPad cbImpDocBol = new JCheckBoxPad("Imprime documento/parcela nos boletos.", "S", "N");
	
	private final JCheckBoxPad cbLancaFinContr = new JCheckBoxPad("Permite lan�amento financeiro em contrato.", "S", "N");
	
	private final JCheckBoxPad cbHabLogPagar = new JCheckBoxPad("Habilita log de opera��es de contas a pagar.", "S", "N");
	
	private final JCheckBoxPad cbHabLogReceber = new JCheckBoxPad("Habilita log de opera��es de contas a receber.", "S", "N");
	
	private JRadioGroup<String, String> rgLibCred = null;
	
	private JRadioGroup<String, String> rgTpNossoNumero = null;
	
	private JRadioGroup<String, String> rgIdentCliBco = null;
	
	private JRadioGroup<String, String> rgPeriodoConsCH = null;
	
	private JTextFieldPad txtNumDigIdentTit = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 2, 0);
	
	private final String opcoes = "Op��es";
	
	private ListaCampos lcTabJuros = new ListaCampos(this, "TJ");
	
	private ListaCampos lcPlanJR = new ListaCampos(this, "JR");
	
	private ListaCampos lcPlanPC = new ListaCampos(this, "PC");

	private ListaCampos lcPlanJP = new ListaCampos(this, "JP");

	private ListaCampos lcPlanDC = new ListaCampos(this, "DC");

	private ListaCampos lcPlanDR = new ListaCampos(this, "DR");
	
	private ListaCampos lcPlanoPagSV = new ListaCampos(this, "SV");


	public FPrefereFNC() {

		setTitulo( "Prefer�ncias Financeiras" );
		setAtribos( 50, 50, 940, 550 );

		// Op��es de libera��o de cr�dito
		
		Vector<String> vLabs3 = new Vector<String>();
		Vector<String> vVals3 = new Vector<String>();

		vLabs3.addElement("N�o vericar");
		vLabs3.addElement("Consulta cr�dito pr�-aprovado");

		vVals3.addElement("N");
		vVals3.addElement("L");

		rgLibCred = new JRadioGroup<String, String>(2, 1, vLabs3, vVals3);
		rgLibCred.setVlrString("N");

		// Op��es de tipo de nosso numero
		
		Vector<String> vLabsTpNossoNumero = new Vector<String>();
		Vector<String> vValsTpNossoNumero = new Vector<String>();
		
		vLabsTpNossoNumero.addElement("Documento");
		vLabsTpNossoNumero.addElement("Cod.Receber");
		vLabsTpNossoNumero.addElement("Sequencial �nico");
		
		vValsTpNossoNumero.addElement("D");
		vValsTpNossoNumero.addElement("R");
		vValsTpNossoNumero.addElement("S");
		
		rgTpNossoNumero = new JRadioGroup<String, String>(1, 3, vLabsTpNossoNumero, vValsTpNossoNumero);
		rgTpNossoNumero.setVlrString("D");
		
		Vector<String> vLabs5 = new Vector<String>();
		Vector<String> vVals5 = new Vector<String>();
		vLabs5.addElement("Fechamento");
		vLabs5.addElement("Item da venda");
		vLabs5.addElement("Ambos");
		vVals5.addElement("FV");
		vVals5.addElement("II");
		vVals5.addElement("AB");

		rgTipoCred = new JRadioGroup<String, String>(2, 2, vLabs5, vVals5);
		rgTipoCred.setVlrString("AB");
		
		
		Vector<String> vLabsIdentCliBco = new Vector<String>();
		Vector<String> vValsIdentCliBco = new Vector<String>();
		
		vLabsIdentCliBco.addElement("Documento. - CPF/CNPJ");
		vLabsIdentCliBco.addElement("C�digo do cliente");
		
		vValsIdentCliBco.addElement("D");
		vValsIdentCliBco.addElement("C");
		
		rgIdentCliBco = new JRadioGroup<String, String>(2, 1, vLabsIdentCliBco, vValsIdentCliBco);
		rgIdentCliBco.setVlrString("D");	
		
		Vector<String> vLabsPeriodoConsCH = new Vector<String>();
		Vector<String> vValsPeriodoConsCH = new Vector<String>();
		vLabsPeriodoConsCH.addElement("m�s");
		vLabsPeriodoConsCH.addElement("ano");

		vValsPeriodoConsCH.addElement("M");	
		vValsPeriodoConsCH.addElement("A");

		
		rgPeriodoConsCH = new JRadioGroup<String, String>(1, 2, vLabsPeriodoConsCH, vValsPeriodoConsCH);
		rgPeriodoConsCH.setVlrString("M");
		
		// ********************************
		
		cbImpDocBol.setVlrString("N");
		cbFechaCaixa.setVlrString("N");
		cbFechaCaixaAuto.setVlrString("N");
		
		lcCampos.setMensInserir( false ); 

		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "C�d.moeda", ListaCampos.DB_PK, true ) );
		lcMoeda.add( new GuardaCampo( txtDescMoeda, "SingMoeda", "Descri��o da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.montaSql( false, "MOEDA", "FN" );
		lcMoeda.setQueryCommit( false );
		lcMoeda.setReadOnly( true );
		txtCodMoeda.setTabelaExterna( lcMoeda, FMoeda.class.getCanonicalName() );
		
		lcHistRec.add(new GuardaCampo(txtCodHistRec, "CodHist", "C�d.Hist.Rec.", ListaCampos.DB_PK, false));
		lcHistRec.add(new GuardaCampo(txtDescHistRec, "DescHist", "Descri��o do hist�rico", ListaCampos.DB_SI, false));
		lcHistRec.montaSql(false, "HISTPAD", "FN");
		lcHistRec.setQueryCommit(false);
		lcHistRec.setReadOnly(true);
		txtCodHistRec.setTabelaExterna(lcHistRec, FHistPad.class.getCanonicalName());

		lcHistPag.add(new GuardaCampo(txtCodHistPag, "CodHist", "C�d.Hist.Pag.", ListaCampos.DB_PK, false));
		lcHistPag.add(new GuardaCampo(txtDescHistPag, "DescHist", "Descri��o do hist�rico", ListaCampos.DB_SI, false));
		lcHistPag.montaSql(false, "HISTPAD", "FN");
		lcHistPag.setQueryCommit(false);
		lcHistPag.setReadOnly(true);
		txtCodHistPag.setTabelaExterna(lcHistPag, FHistPad.class.getCanonicalName());

		lcHistCNAB.add(new GuardaCampo(txtCodHistCNAB, "CodHist", "C�d.Hist.Pag.", ListaCampos.DB_PK, false));
		lcHistCNAB.add(new GuardaCampo(txtDescHistCNAB, "DescHist", "Descri��o do hist�rico", ListaCampos.DB_SI, false));
		lcHistCNAB.montaSql(false, "HISTPAD", "FN");
		lcHistCNAB.setQueryCommit(false);
		lcHistCNAB.setReadOnly(true);
		txtCodHistCNAB.setTabelaExterna(lcHistCNAB, FHistPad.class.getCanonicalName());

		lcTabJuros.add(new GuardaCampo(txtCodTabJuros, "CodTbj", "C�d.tb.jur.", ListaCampos.DB_PK, false));
		lcTabJuros.add(new GuardaCampo(txtDescTabJuros, "DescTbJ", "Descri��o da tabela de juros", ListaCampos.DB_SI, false));
		lcTabJuros.montaSql(false, "TBJUROS", "FN");
		lcTabJuros.setQueryCommit(false);
		lcTabJuros.setReadOnly(true);
		txtCodTabJuros.setTabelaExterna(lcTabJuros, FTabJuros.class.getCanonicalName());
		
		lcPlanJR.add(new GuardaCampo(txtCodPlanJR, "CodPlan", "C�d.Plan.JR.", ListaCampos.DB_PK, false));
		lcPlanJR.add(new GuardaCampo(txtDescPlanJR, "DescPlan", "Descri��o do Planejamento", ListaCampos.DB_SI, false));
		lcPlanJR.setWhereAdic("TIPOPLAN = 'R' AND NIVELPLAN = 6");
		lcPlanJR.montaSql(false, "PLANEJAMENTO", "FN");
		lcPlanJR.setQueryCommit(false);
		lcPlanJR.setReadOnly(true);
		txtCodPlanJR.setTabelaExterna(lcPlanJR, FPlanejamento.class.getCanonicalName());
		
		lcPlanJP.add(new GuardaCampo(txtCodPlanJP, "CodPlan", "C�d.Plan.JP.", ListaCampos.DB_PK, false));
		lcPlanJP.add(new GuardaCampo(txtDescPlanJP, "DescPlan", "Descri��o do Planejamento", ListaCampos.DB_SI, false));
		lcPlanJP.setWhereAdic("TIPOPLAN = 'D' AND NIVELPLAN = 6");
		lcPlanJP.montaSql(false, "PLANEJAMENTO", "FN");
		lcPlanJP.setQueryCommit(false);
		lcPlanJP.setReadOnly(true);
		txtCodPlanJP.setTabelaExterna(lcPlanJP, FPlanejamento.class.getCanonicalName());

		lcPlanDC.add(new GuardaCampo(txtCodPlanDC, "CodPlan", "C�d.Plan.DC.", ListaCampos.DB_PK, false));
		lcPlanDC.add(new GuardaCampo(txtDescPlanDC, "DescPlan", "Descri��o do Planejamento", ListaCampos.DB_SI, false));
		lcPlanDC.setWhereAdic("TIPOPLAN = 'D' AND NIVELPLAN = 6");
		lcPlanDC.montaSql(false, "PLANEJAMENTO", "FN");
		lcPlanDC.setQueryCommit(false);
		lcPlanDC.setReadOnly(true);
		txtCodPlanDC.setTabelaExterna(lcPlanDC, FPlanejamento.class.getCanonicalName());
		
		lcPlanDR.add(new GuardaCampo(txtCodPlanDR, "CodPlan", "C�d.Plan.DR.", ListaCampos.DB_PK, false));
		lcPlanDR.add(new GuardaCampo(txtDescPlanDR, "DescPlan", "Descri��o do Planejamento", ListaCampos.DB_SI, false));
		lcPlanDR.setWhereAdic("TIPOPLAN = 'R' AND NIVELPLAN = 6");
		lcPlanDR.montaSql(false, "PLANEJAMENTO", "FN");
		lcPlanDR.setQueryCommit(false);
		lcPlanDR.setReadOnly(true);
		txtCodPlanDR.setTabelaExterna(lcPlanDR, FPlanejamento.class.getCanonicalName());
		
		lcPlanPC.add(new GuardaCampo(txtCodPlanPC, "CodPlan", "C�d.Plan.JP.", ListaCampos.DB_PK, false));
		lcPlanPC.add(new GuardaCampo(txtDescPlanPC, "DescPlan", "Descri��o do Planejamento", ListaCampos.DB_SI, false));
		lcPlanPC.setWhereAdic("TIPOPLAN = 'D' AND NIVELPLAN = 6");
		lcPlanPC.montaSql(false, "PLANEJAMENTO", "FN");
		lcPlanPC.setQueryCommit(false);
		lcPlanPC.setReadOnly(true);
		txtCodPlanPC.setTabelaExterna(lcPlanPC, FPlanejamento.class.getCanonicalName());
		txtCodPlanPC.setNomeCampo( "codplan" );
		
		txtCodPlanoPagSV.setNomeCampo("CodPlanoPag");
		lcPlanoPagSV.add(new GuardaCampo(txtCodPlanoPagSV, "CodPlanoPag", "C�d.p.pag", ListaCampos.DB_PK, false));
		lcPlanoPagSV.add(new GuardaCampo(txtDescPlanoPagSV, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPagSV.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPagSV.setReadOnly(true);
		txtCodPlanoPagSV.setTabelaExterna(lcPlanoPagSV, FPlanoPag.class.getCanonicalName());
		txtCodPlanoPagSV.setFK(true);
		txtDescPlanoPagSV.setListaCampos(lcPlanoPagSV);
		txtCodPlanoPagSV.setNomeCampo( "codplanopag" );

				
		// Geral

		setPainel( pinGeral );
		
		adicTab( "Geral", pinGeral );
		adicCampo( txtAnoCC			, 7		, 25	, 100	, 20, "AnoCentroCusto", "Ano Base C.C.", ListaCampos.DB_SI, true );
		
		adicCampo(txtCodMoeda		, 110	, 25	, 80	, 20, "CodMoeda", "C�d.moeda", ListaCampos.DB_FK, txtDescMoeda, true);
		adicDescFK(txtDescMoeda		, 193	, 25	, 315	, 20, "SingMoeda", "Descri��o da moeda corrente.");

		// Painel de op��es 
		
		pnOpcoes.setBorder(BorderFactory.createTitledBorder(opcoes));
		adic(pnOpcoes				, 530	, 10	, 370	, 295);

		setPainel( pnOpcoes );
		
		adicDB(cbAltItRecImpBol		, 7		, 0		, 310	, 20, "AtBancoImpBol"		, ""	, false);
		adicDB(cbJurosPosCalc		, 7		, 20	, 310	, 20, "JurosPosCalc"		, ""	, false);
		adicDB(cbEstItRecAltDtVenc	, 7		, 40	, 350	, 20, "EstItRecAltDtVenc"	, ""	, false);
		adicDB(cbLiberacaoCreGlobal	, 7		, 60	, 350	, 20, "LcRedGlobal"			, ""	, false);
		
		adicDB(cbGeraPagEmis		, 7		, 80	, 350	, 20, "GeraPagEmis"			, ""	, true);
		adicDB(cbGeraRecEmis		, 7		, 100	, 350	, 20, "GeraRecEmis"			, ""	, true);
		adicDB(cbImpDocBol			, 7		, 120	, 350	, 20, "ImpDocBol"			, ""	, true);
		adicDB(cbFechaCaixa			, 7		, 140	, 350	, 20, "FechaCaixa"			, ""	, true);
		adicDB(cbFechaCaixaAuto		, 7		, 160	, 350	, 20, "FechaCaixaAuto"		, ""	, true);
		adicDB(cbAlinhaTelaLanca	, 7		, 180	, 350	, 20, "AlinhaTelaLanca"		, ""	, true);
		adicDB(cbHabLogPagar		, 7		, 200	, 350	, 20, "HabLogPagar"			, ""	, true);
		adicDB(cbHabLogReceber		, 7		, 220	, 350	, 20, "HabLogReceber"		, ""	, true);
		adicDB(cbMatricEmpFn		, 7		, 240	, 350	, 20, "MatricEmpFn"		, ""	, true);
		
		setPainel( pinGeral );
		
		// Painel para identifica��o do cliente - SIACC
		
		pnIdentCliBco.setBorder(BorderFactory.createTitledBorder("Identifica��o do cliente - (SIACC)"));
		adic( pnIdentCliBco		, 530		, 303	, 370	, 90 );

		setPainel( pnIdentCliBco );
		
		adicDB(rgIdentCliBco		, 7	, 10	, 350	, 55, "IdentCliBco", "", false);	
		

		setPainel( pinGeral );

		// Painel de op��es de contrato
		
		pnContrato.setBorder(BorderFactory.createTitledBorder("Contratos/Projetos"));
		adic(pnContrato				, 7		, 60	, 500	, 50);
		setPainel( pnContrato );
		
		adicDB(cbLancaFinContr		, 7		, 0		, 350	, 20, "LancaFinContr", "", false);
		
		setPainel( pinGeral );
		
		// Painel Libera��o de cr�dito
		
		pnFinLibCred.setBorder(BorderFactory.createTitledBorder("Libera��o de cr�dito"));
		adic(pnFinLibCred			, 7		, 120	, 500	, 90);

		setPainel( pnFinLibCred );
		
		adicDB(rgLibCred			, 10	, 5		, 220	, 55, "PrefCred", "", true);
		adicDB(rgTipoCred			, 243	, 5		, 235	, 55, "TipoPrefCred", "", true);

		// Painel de tipo do nosso numero
		
		setPainel( pinGeral );
		
		pnTPNossoNumero.setBorder(BorderFactory.createTitledBorder("Tipo do nosso n�mero (boletos/remessa)"));
		adic( pnTPNossoNumero		, 7		, 220	, 500	, 130 );

		setPainel( pnTPNossoNumero );
		
		adicDB(rgTpNossoNumero		, 10	, 15	, 450	, 30, "tpnossonumero", "", true);	
		
		adicCampo(txtNumDigIdentTit	, 10	, 75	, 100	, 20, "NumDigIdentTit", "Nr.Dig.Ident.Tit.", ListaCampos.DB_SI, false);
		

		setPainel( pinGeral ); 
		
		pnPeriodoConsultaCH.setBorder(BorderFactory.createTitledBorder("Per�odo de consulta de cheques, por:"));
		adic( pnPeriodoConsultaCH		, 7		, 353	, 500	, 90 );
		
		setPainel( pnPeriodoConsultaCH );
		adicDB(rgPeriodoConsCH	, 10	, 15	, 450	, 30, "PeriodoConsCH", "", true);	
		

		
		// Padr�es

		setPainel( pinPadroes );
		adicTab( "Padr�es", pinPadroes );

		// Painel Defini��es
		
		pnDefinicoes.setBorder(BorderFactory.createTitledBorder("Defini��es"));
		adic(pnDefinicoes, 7, 10, 465, 240);

		setPainel( pnDefinicoes );
		
		adicCampo(txtCodTabJuros	, 7		, 20	, 80	, 20, "CodTbj"				, "C�d.tab.jr.", ListaCampos.DB_FK, txtDescTabJuros, false);
		adicDescFK(txtDescTabJuros	, 90	, 20	, 350	, 20, "DescTbj"				, "Descri��o da tabela de juros.");

		adicCampo(txtCodHistRec		, 7		, 60	, 80	, 20, "CodHistRec"			, "C�d.Hist.Rec.", ListaCampos.DB_FK, txtDescHistRec, false);
		adicDescFK(txtDescHistRec	, 90	, 60	, 350	, 20, "DescHist"			, "Descri��o do hist�rico padr�o para contas a receber");

		adicCampo(txtCodHistPag		, 7		, 100	, 80	, 20, "CodHistPag"			, "C�d.Hist.Pag.", ListaCampos.DB_FK, txtDescHistPag, false);
		adicDescFK(txtDescHistPag	, 90	, 100	, 350	, 20, "DescHist"			, "Descri��o do hist�rico padr�o para contas a pagar");

		adicCampo(txtCodHistCNAB	, 7		, 140	, 80	, 20, "CodHistCNAB"			, "C�d.Hist.CNAB", ListaCampos.DB_FK, txtDescHistPag, false);
		adicDescFK(txtDescHistCNAB	, 90	, 140	, 350	, 20, "DescHist"			, "Descri��o do hist�rico padr�o para baixa autom�tica CNAB");

		txtCodHistCNAB.setNomeCampo( "codhist" );
		
		adicCampo(txtCodPlanoPagSV	, 7		, 180	, 80	, 20, "CodPlanoPagSV"		, "C�d.Plan.Pag.", ListaCampos.DB_FK, txtDescHistPag, false);
		adicDescFK(txtDescPlanoPagSV, 90	, 180	, 350	, 20, "DescPlanoPag"		, "Descri��o do plano de pagamento padr�o s/valor financeiro");

		txtCodPlanoPagSV.setNomeCampo( "codplanopag" );
		
		setPainel( pinPadroes );
		// Painel contas padr�o
		
		panelFinContas.setBorder(BorderFactory.createTitledBorder("Contas"));
		adic(panelFinContas			, 475	, 10	, 435	, 240);

		setPainel( panelFinContas );
		
		adicCampo(txtCodPlanJR		, 7		, 20	, 100	, 20, "CodPlanJR", "C�d.Plan.JR.", ListaCampos.DB_FK, txtDescPlanJR, false);
		adicDescFK(txtDescPlanJR	, 110	, 20	, 300	, 20, "DescPlan", "Planejamento para juros recebidos");

		txtCodPlanJR.setNomeCampo( "codplan" );
		
		adicCampo(txtCodPlanJP		, 7		, 60	, 100	, 20, "CodPlanJP", "C�d.Plan.JP.", ListaCampos.DB_FK, txtDescPlanJP, false);
		adicDescFK(txtDescPlanJP	, 110	, 60	, 300	, 20, "DescPlan", "Planejamento para juros pagos");

		txtCodPlanJP.setNomeCampo( "codplan" );
		
		adicCampo(txtCodPlanDC		, 7		, 100	, 100	, 20, "CodPlanDC", "C�d.Plan.DC.", ListaCampos.DB_FK, txtDescPlanDC, false);
		adicDescFK(txtDescPlanDC	, 110	, 100	, 300	, 20, "DescPlan", "Planejamento para descontos concedidos");

		txtCodPlanDC.setNomeCampo( "codplan" );
		
		adicCampo(txtCodPlanDR		, 7		, 140	, 100	, 20, "CodPlanDR", "C�d.Plan.DR.", ListaCampos.DB_FK, txtDescPlanDR, false);
		adicDescFK(txtDescPlanDR	, 110	, 140	, 300	, 20, "DescPlan", "Planejamento para descontos obtidos");

		txtCodPlanDR.setNomeCampo( "codplan" );
		
		adicCampo(txtCodPlanPC		, 7		, 180	, 100	, 20, "CodPlanPC", "C�d.Plan.PC.", ListaCampos.DB_FK, txtDescPlanDR, false);
		adicDescFK(txtDescPlanPC	, 110	, 180	, 300	, 20, "DescPlan", "Planejamento p/pagto com cheques");

		setPainel( pinGeral );
		
		nav.setAtivo( 0, false );
		lcCampos.setPodeExc( false );

		setListaCampos( false, "PREFERE1", "SG" );

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		lcMoeda.setConexao( cn );
		lcHistPag.setConexao( cn );
		lcHistRec.setConexao( cn );
		lcHistCNAB.setConexao( cn );
		lcTabJuros.setConexao( cn );
		lcPlanJR.setConexao(cn);
		lcPlanJP.setConexao(cn);
		lcPlanDC.setConexao(cn);
		lcPlanDR.setConexao(cn);
		lcPlanPC.setConexao(cn);
		lcPlanoPagSV.setConexao(cn);


		lcCampos.carregaDados();
		
	}

	public void beforeInsert( InsertEvent ievt ) {
		
	}

	public void afterInsert( InsertEvent ievt ) {

		if(ievt.getListaCampos() == lcCampos){
			cbHabLogPagar.setVlrString( "N" );
			cbHabLogReceber.setVlrString( "N" );
			cbMatricEmpFn.setVlrString( "N" );
		}
		
	}
}
