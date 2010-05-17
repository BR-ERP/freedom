/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FProduto.java <BR>
 * 
 *                   Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                   modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                   na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                   Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                   sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                   Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                   Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                   Cadastro de produtos
 * 
 */

package org.freedom.modulos.gms.view.frame.crud.tabbed;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.object.CustosProd;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.EANFactory;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.Navegador;
import org.freedom.library.swing.component.PainelImagem;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FAndamento;
import org.freedom.library.swing.frame.FTabDados;
import org.freedom.modulos.gms.view.frame.crud.plain.FSecaoProd;
import org.freedom.modulos.gms.view.frame.crud.special.FGrupoProd;
import org.freedom.modulos.lvf.view.frame.crud.detail.FCLFiscal;
import org.freedom.modulos.pcp.view.frame.crud.detail.FEstrutura;
import org.freedom.modulos.std.view.dialog.report.DLRProduto;
import org.freedom.modulos.std.view.frame.crud.detail.FPlanoPag;
import org.freedom.modulos.std.view.frame.crud.plain.FAlmox;
import org.freedom.modulos.std.view.frame.crud.plain.FCaixa;
import org.freedom.modulos.std.view.frame.crud.plain.FClasCli;
import org.freedom.modulos.std.view.frame.crud.plain.FMarca;
import org.freedom.modulos.std.view.frame.crud.plain.FPrazoEnt;
import org.freedom.modulos.std.view.frame.crud.plain.FTabPreco;
import org.freedom.modulos.std.view.frame.crud.plain.FUnidade;
import org.freedom.modulos.std.view.frame.crud.special.FCentroCusto;
import org.freedom.modulos.std.view.frame.crud.special.FPlanejamento;
import org.freedom.modulos.std.view.frame.crud.tabbed.FFornecedor;
import org.freedom.modulos.std.view.frame.crud.tabbed.FMoeda;

public class FProduto extends FTabDados implements CheckBoxListener, EditListener, InsertListener, ChangeListener, ActionListener, CarregaListener, RadioGroupListener, PostListener {

	private static final long serialVersionUID = 1L;

	private int casasDec = Aplicativo.casasDec;

	private int casasDecFin = Aplicativo.casasDecFin;

	private JPanelPad pinGeral = new JPanelPad();

	private JPanelPad pnFatConv = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnProdPlan = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnFor = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCodAltProd = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCodAcess = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnMedidas = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnLote = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnSerie = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnFoto = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnPreco = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtCodUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodAltProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodPA = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );// c�digo de acesso

	private JTextFieldPad txtAnoCCPA = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtCodCCPA = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldPad txtCodCaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescCCPA = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescCaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodMarca = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtCodSecao = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtCodAlmox = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDescAuxProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodBarProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodEANProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtCodFabProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtVlrDens = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtCubagem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtVlrConcent = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtVlrCompri = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtVlrLarg = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtVlrEspess = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtQtdEmbalagem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtComisProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, casasDecFin );

	private JTextFieldPad txtPesoLiqProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtPesoBrutProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtQtdMinProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtQtdMaxProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtLocalProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private JTextFieldPad txtCustoMPMProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtCustoInfoProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtCustoPEPSProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtSldProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtCustoMPMAlmox = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtCustoPEPSAlmox = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtSldAlmox = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtDtUltCpProd = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, casasDec );

	private JTextFieldPad txtSldConsigProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtSldConsigAlmox = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtSldResProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtSldResAlmox = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtSldLiqProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtSldLiqAlmox = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtPrecoBaseProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtUnidFat = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtFatConv = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtDiniLote = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVenctoLote = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtNumSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtDtFabricSerie = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtValidSerie = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtSldLote = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtSldResLote = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtSldConsigLote = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 3 );

	private JTextFieldPad txtSldLiqLote = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtQtdProdLote = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtDiasAvisoLote = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodFotoProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescFotoProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtLargFotoProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtAltFotoProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodPrecoProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodClasCliPreco = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTabPreco = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodPlanoPagPreco = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtPrecoProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );

	private JTextFieldPad txtSeqPP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldPad txtPrazoEnt = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDias = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtDescPrazoEnt = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescPlan = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtAlmox = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtDescUnid = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtCodProdFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 18, 0 );

	private JTextFieldFK txtDescFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescFisc = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescMarca = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescSecao = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescAlmox = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescUnidFat = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescClasCliPreco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescTabPreco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescPlanoPagPreco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private Vector<String> vLabsTipo = new Vector<String>();

	private Vector<String> vValsTipo = new Vector<String>();

	private Vector<String> vLabsCV = new Vector<String>();

	private Vector<String> vValsCV = new Vector<String>();

	private Vector<String> vLabsTF = new Vector<String>();

	private Vector<String> vValsTF = new Vector<String>();

	private Vector<String> vLabsTipoPP = new Vector<String>();

	private Vector<String> vValsTipoPP = new Vector<String>();

	private Vector<String> vValsTipoConv = new Vector<String>();

	private Vector<String> vLabsTipoConv = new Vector<String>();

	private Vector<String> vLabsPA = new Vector<String>();

	private Vector<String> vValsPA = new Vector<String>();

	private Vector<String> vLabsBCusto = new Vector<String>();

	private Vector<String> vValsBCusto = new Vector<String>();

	private JRadioGroup<?, ?> rgPA = null;

	private JRadioGroup<?, ?> rgTipo = null;

	private JRadioGroup<?, ?> rgCV = null;

	private JRadioGroup<?, ?> rgTF = null;

	private JRadioGroup<?, ?> rgTipoPP = null;

	private JRadioGroup<?, ?> rgAbaixCust = null;

	private JCheckBoxPad cbLote = null;

	private JCheckBoxPad cbReceita = null;

	private JCheckBoxPad cbSerie = null;

	private JCheckBoxPad cbGuiaTraf = null;

	private JCheckBoxPad cbAtivo = null;

	private JRadioGroup<?, ?> rgTipoConv = null;

	private JCheckBoxPad cbRMA = null;

	private JCheckBoxPad cbAdicPDV = null;

	private JTablePad tabFatConv = new JTablePad();

	private JScrollPane spnFatConv = new JScrollPane( tabFatConv );

	private JTablePad tabProdPlan = new JTablePad();

	private JScrollPane spnPlan = new JScrollPane( tabProdPlan );

	private JTablePad tabFor = new JTablePad();

	private JTablePad tabCodAltProd = new JTablePad();

	private JTablePad tabCodAcess = new JTablePad();

	private JScrollPane spnFor = new JScrollPane( tabFor );

	private JScrollPane spnCodAltProd = new JScrollPane( tabCodAltProd );

	private JScrollPane spnCodAcess = new JScrollPane( tabCodAcess );

	private JTablePad tabLote = new JTablePad();

	private JTablePad tabSerie = new JTablePad();

	private JScrollPane spnLote = new JScrollPane( tabLote );

	private JScrollPane spnSerie = new JScrollPane( tabSerie );

	private JTablePad tabFoto = new JTablePad();

	private JScrollPane spnFoto = new JScrollPane( tabFoto );

	private JTablePad tabPreco = new JTablePad();

	private JScrollPane spnPreco = new JScrollPane( tabPreco );

	private JPanelPad pinRodFatConv = new JPanelPad( 650, 120 );

	private JPanelPad pinRodProdPlan = new JPanelPad( 650, 120 );

	private JPanelPad pinRodFor = new JPanelPad( 650, 80 );

	private JPanelPad pinRodCodAltProd = new JPanelPad( 650, 80 );

	private JPanelPad pinRodCodAcess = new JPanelPad( 650, 120 );

	private JPanelPad pinMedidas = new JPanelPad( 650, 120 );

	private JPanelPad pinRodLote = new JPanelPad( 650, 120 );

	private JPanelPad pinRodSerie = new JPanelPad( 650, 165 );

	private JPanelPad pinRodFoto = new JPanelPad( 650, 170 );

	private JPanelPad pinRodPreco = new JPanelPad( 650, 120 );

	private JPanelPad pnDesc = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JTextAreaPad txaDescComp = new JTextAreaPad();

	private JTextAreaPad txaObsSerie = new JTextAreaPad();

	private JScrollPane spnDesc = new JScrollPane( txaDescComp );

	private JScrollPane spnObsSerie = new JScrollPane( txaObsSerie );

	private ListaCampos lcMoeda = new ListaCampos( this, "MA" );

	private ListaCampos lcUnid = new ListaCampos( this, "UD" );

	private ListaCampos lcFisc = new ListaCampos( this, "FC" );

	private ListaCampos lcMarca = new ListaCampos( this, "MC" );

	private ListaCampos lcSecao = new ListaCampos( this, "SC" );

	private ListaCampos lcGrup = new ListaCampos( this, "GP" );

	private ListaCampos lcAlmox = new ListaCampos( this, "AX" );

	private ListaCampos lcPrazoEnt = new ListaCampos( this, "PE" );

	private ListaCampos lcFatConv = new ListaCampos( this, "" );

	private ListaCampos lcProdPlan = new ListaCampos( this );

	private ListaCampos lcFor = new ListaCampos( this );

	private ListaCampos lcCodAltProd = new ListaCampos( this, "" );

	private ListaCampos lcProdAcesso = new ListaCampos( this );

	private ListaCampos lcUnidFat = new ListaCampos( this, "" );

	private ListaCampos lcPlan = new ListaCampos( this, "PN" );

	private ListaCampos lcCC = new ListaCampos( this, "CC" );

	private ListaCampos lcCCAcesso = new ListaCampos( this, "CC" );

	private ListaCampos lcCaixa = new ListaCampos( this, "CX" );

	private ListaCampos lcForFK = new ListaCampos( this );

	private ListaCampos lcLote = new ListaCampos( this );

	private ListaCampos lcSerie = new ListaCampos( this );

	private ListaCampos lcFoto = new ListaCampos( this );

	private ListaCampos lcPreco = new ListaCampos( this );

	private ListaCampos lcClasCliPreco = new ListaCampos( this, "CC" );

	private ListaCampos lcTabPreco = new ListaCampos( this, "TB" );

	private ListaCampos lcPlanoPagPreco = new ListaCampos( this, "PG" );

	private Navegador navFatConv = new Navegador( true );

	private Navegador navProdPlan = new Navegador( true );

	private Navegador navFor = new Navegador( true );

	private Navegador navLote = new Navegador( true );

	private Navegador navSerie = new Navegador( true );

	private Navegador navFoto = new Navegador( true );

	private Navegador navPreco = new Navegador( true );

	private Navegador navCodAltProd = new Navegador( true );

	private Navegador navCodAcess = new Navegador( true );

	private JButtonPad btDuplicar = new JButtonPad( "duplicar", Icone.novo( "btDuplicar.png" ) );

	private JButtonPad btCodBar = new JButtonPad( "", Icone.novo( "btCodBar.gif" ) );

	private PainelImagem imFotoProd = new PainelImagem( 65000 );

	private String[] sPrefs = null;

	private JCheckBoxPad cbCpFatConv = new JCheckBoxPad( "", "S", "N" );

	private enum eprefs {
		CODMOEDA, PEPSPROD, TIPOCODBAR, CODEANEMP, CODPAISEMP
	};

	private JLabelPad lbUnidFat = null;

	private JLabelPad lbDescUnidFat = null;

	private JLabelPad lbFatConv = null;

	private JLabelPad lbCpFatConv = null;

	private JLabelPad lbCodProdEst = null;

	private JLabelPad lbDescProdEst = null;

	private JLabelPad lbSeqEst = null;

	private JTextFieldPad txtCodProdEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdEst = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSeqEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private ListaCampos lcProdEstCod = new ListaCampos( this, "ET" );

	private JLabelPad separador1 = new JLabelPad();

	private JLabelPad separador2 = new JLabelPad();

	public FProduto() {

		super();

		setTitulo( "Cadastro de Produtos" );
		setAtribos( 30, 10, 685, 650 );

		lcFatConv.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcFatConv );
		lcFatConv.setTabela( tabFatConv );

		lcProdPlan.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcProdPlan );
		lcProdPlan.setTabela( tabProdPlan );
		lcFor.setMaster( lcCampos );

		lcCodAltProd.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcFor );
		lcCampos.adicDetalhe( lcCodAltProd );
		lcFor.setTabela( tabFor );
		lcCodAltProd.setTabela( tabCodAltProd );

		lcProdAcesso.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcProdAcesso );
		lcProdAcesso.setTabela( tabCodAcess );

		lcLote.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcLote );
		lcLote.setTabela( tabLote );

		lcSerie.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcSerie );
		lcSerie.setTabela( tabSerie );

		lcFoto.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcFoto );
		lcFoto.setTabela( tabFoto );

		lcPreco.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcPreco );
		lcPreco.setTabela( tabPreco );

		lcCampos.addInsertListener( this );
		lcCampos.addCarregaListener( this );
		lcFoto.addEditListener( this );
		lcFoto.addInsertListener( this );
		lcProdAcesso.addInsertListener( this );
		lcProdAcesso.addCarregaListener( this );

		setPainel( pinGeral );
		adicTab( "Geral", pinGeral );

		btDuplicar.setToolTipText( "Duplicar produto" );

		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "C�d. moeda", ListaCampos.DB_PK, true ) );
		lcMoeda.add( new GuardaCampo( txtDescMoeda, "SingMoeda", "Descri��o da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.montaSql( false, "MOEDA", "FN" );
		lcMoeda.setReadOnly( true );
		lcMoeda.setQueryCommit( false );
		txtCodMoeda.setTabelaExterna( lcMoeda, FMoeda.class.getCanonicalName() );

		lcUnid.add( new GuardaCampo( txtCodUnid, "CodUnid", "C�d.unid.", ListaCampos.DB_PK, true ) );
		lcUnid.add( new GuardaCampo( txtDescUnid, "DescUnid", "Descri��o da unidade", ListaCampos.DB_SI, false ) );
		lcUnid.montaSql( false, "UNIDADE", "EQ" );
		lcUnid.setReadOnly( true );
		lcUnid.setQueryCommit( false );
		txtCodUnid.setTabelaExterna( lcUnid, FUnidade.class.getCanonicalName() );

		lcMarca.add( new GuardaCampo( txtCodMarca, "CodMarca", "C�d.marca", ListaCampos.DB_PK, true ) );
		lcMarca.add( new GuardaCampo( txtDescMarca, "DescMarca", "Descri��o da marca", ListaCampos.DB_SI, false ) );
		lcMarca.montaSql( false, "MARCA", "EQ" );
		lcMarca.setReadOnly( true );
		lcMarca.setQueryCommit( false );
		txtCodMarca.setTabelaExterna( lcMarca, FMarca.class.getCanonicalName() );

		lcSecao.add( new GuardaCampo( txtCodSecao, "CodSecao", "C�d.Se��o", ListaCampos.DB_PK, false ) );
		lcSecao.add( new GuardaCampo( txtDescSecao, "DescSecao", "Descri��o da se��o", ListaCampos.DB_SI, false ) );
		lcSecao.montaSql( false, "SECAO", "EQ" );
		lcSecao.setReadOnly( true );
		lcSecao.setQueryCommit( false );
		txtCodSecao.setTabelaExterna( lcSecao, FSecaoProd.class.getCanonicalName() );

		lcFisc.add( new GuardaCampo( txtCodFisc, "CodFisc", "C�d.c.fisc.", ListaCampos.DB_PK, true ) );
		lcFisc.add( new GuardaCampo( txtDescFisc, "DescFisc", "Descri��o da classifica��o fiscal", ListaCampos.DB_SI, false ) );
		lcFisc.montaSql( false, "CLFISCAL", "LF" );
		lcFisc.setReadOnly( true );
		lcFisc.setQueryCommit( false );
		txtCodFisc.setTabelaExterna( lcFisc, FCLFiscal.class.getCanonicalName() );

		lcGrup.add( new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, true ) );
		lcGrup.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false ) );
		lcGrup.montaSql( false, "GRUPO", "EQ" );
		lcGrup.setReadOnly( true );
		lcGrup.setQueryCommit( false );
		txtCodGrup.setTabelaExterna( lcGrup, FGrupoProd.class.getCanonicalName() );

		lcAlmox.add( new GuardaCampo( txtCodAlmox, "CodAlmox", "C�d.almox.", ListaCampos.DB_PK, true ) );
		lcAlmox.add( new GuardaCampo( txtDescAlmox, "DescAlmox", "Descri��o do almoxarifado", ListaCampos.DB_SI, false ) );
		lcAlmox.montaSql( false, "ALMOX", "EQ" );
		lcAlmox.setReadOnly( true );
		lcAlmox.setQueryCommit( false );
		txtCodAlmox.setTabelaExterna( lcAlmox, FAlmox.class.getCanonicalName() );

		lcPrazoEnt.add( new GuardaCampo( txtPrazoEnt, "CodPE", "Prazo para entrega", ListaCampos.DB_PK, false ) );
		lcPrazoEnt.add( new GuardaCampo( txtDescPrazoEnt, "DescPE", "Descri��o do prazo de entrega", ListaCampos.DB_SI, false ) );
		lcPrazoEnt.add( new GuardaCampo( txtDias, "DiasPE", "N� de dias", ListaCampos.DB_SI, false ) );
		lcPrazoEnt.montaSql( false, "PRAZOENT", "VD" );
		lcPrazoEnt.setReadOnly( true );
		lcPrazoEnt.setQueryCommit( false );
		txtPrazoEnt.setTabelaExterna( lcPrazoEnt, FPrazoEnt.class.getCanonicalName() );

		lcProdEstCod.add( new GuardaCampo( txtCodProdEst, "CodProd", "C�d.Prod.", ListaCampos.DB_PK, txtDescProdEst, false ) );
		lcProdEstCod.add( new GuardaCampo( txtSeqEst, "SeqEst", "Seq.Est.", ListaCampos.DB_PK, txtDescProdEst, false ) );
		lcProdEstCod.add( new GuardaCampo( txtDescProdEst, "DescEst", "Descri��o da estrutura", ListaCampos.DB_SI, false ) );

		lcProdEstCod.setWhereAdic( "ATIVOEST='S'" );
		lcProdEstCod.montaSql( false, "ESTRUTURA", "PP" );
		lcProdEstCod.setQueryCommit( false );
		lcProdEstCod.setReadOnly( true );
		txtCodProdEst.setTabelaExterna( lcProdEstCod, FEstrutura.class.getCanonicalName() );
		txtSeqEst.setTabelaExterna( lcProdEstCod, FEstrutura.class.getCanonicalName() );
		txtCodProdEst.setNomeCampo( "codprodet" );

		vValsTipo.addElement( "P" );
		vValsTipo.addElement( "S" );
		vValsTipo.addElement( "E" );
		vValsTipo.addElement( "F" );
		vValsTipo.addElement( "M" );
		vValsTipo.addElement( "O" );
		vValsTipo.addElement( "C" );

		vLabsTipo.addElement( "Com�rcio" );
		vLabsTipo.addElement( "Servi�o" );
		vLabsTipo.addElement( "Equipamento" );
		vLabsTipo.addElement( "Fabrica��o" );
		vLabsTipo.addElement( "Mat.prima" );
		vLabsTipo.addElement( "Patrim�nio" );
		vLabsTipo.addElement( "Consumo" );

		rgTipo = new JRadioGroup<String, String>( 6, 1, vLabsTipo, vValsTipo );
		rgTipo.setVlrString( "P" );

		vValsCV.addElement( "C" );
		vValsCV.addElement( "V" );
		vValsCV.addElement( "A" );
		vLabsCV.addElement( "Compra" );
		vLabsCV.addElement( "Venda" );
		vLabsCV.addElement( "Ambos" );
		rgCV = new JRadioGroup<String, String>( 3, 1, vLabsCV, vValsCV );
		rgCV.setVlrString( "V" );

		vValsTF.addElement( "P" );
		vValsTF.addElement( "M" );
		vValsTF.addElement( "N" );
		vValsTF.addElement( "G" );
		vLabsTF.addElement( "Pequena" );
		vLabsTF.addElement( "M�dia" );
		vLabsTF.addElement( "Natural" );
		vLabsTF.addElement( "Grande" );
		rgTF = new JRadioGroup<String, String>( 1, 4, vLabsTF, vValsTF );
		rgTF.setVlrString( "P" );

		vValsTipoPP.addElement( "R" );
		vValsTipoPP.addElement( "D" );
		vLabsTipoPP.addElement( "Receitas" );
		vLabsTipoPP.addElement( "Despesas" );
		rgTipoPP = new JRadioGroup<String, String>( 1, 2, vLabsTipoPP, vValsTipoPP );
		rgTipoPP.setVlrString( "R" );

		vValsTipoConv.addElement( "U" );
		vValsTipoConv.addElement( "P" );
		vLabsTipoConv.addElement( "Unidade" );
		vLabsTipoConv.addElement( "Produto" );
		rgTipoConv = new JRadioGroup<String, String>( 2, 1, vLabsTipoConv, vValsTipoConv );

		rgTipoConv.addRadioGroupListener( this );

		cbCpFatConv.setVlrString( "N" );

		vValsPA.addElement( "RMA" );
		vValsPA.addElement( "PDV" );
		vLabsPA.addElement( "RMA" );
		vLabsPA.addElement( "PDV" );
		rgPA = new JRadioGroup<String, String>( 1, 2, vLabsPA, vValsPA );
		rgPA.setVlrString( "R" );
		rgPA.addRadioGroupListener( this );

		cbLote = new JCheckBoxPad( "Exige", "S", "N" );
		cbLote.setVlrString( "N" );
		cbLote.addCheckBoxListener( this );

		cbAtivo = new JCheckBoxPad( "Sim", "S", "N" );
		cbAtivo.setVlrString( "S" );

		vValsBCusto.addElement( "N" );
		vValsBCusto.addElement( "S" );
		vValsBCusto.addElement( "L" );
		vLabsBCusto.addElement( "Bloqueado" );
		vLabsBCusto.addElement( "Senha" );
		vLabsBCusto.addElement( "Liberado" );
		rgAbaixCust = new JRadioGroup<String, String>( 1, 3, vLabsBCusto, vValsBCusto );
		rgAbaixCust.setVlrString( "N" );
		rgAbaixCust.addRadioGroupListener( this );

		cbRMA = new JCheckBoxPad( "Permite", "S", "N" );
		cbRMA.setVlrString( "S" );

		cbAdicPDV = new JCheckBoxPad( "Tela adicional", "S", "N" );
		cbAdicPDV.setVlrString( "N" );

		cbReceita = new JCheckBoxPad( "Exige", "S", "N" );
		cbReceita.setVlrString( "N" );

		cbGuiaTraf = new JCheckBoxPad( "Exige", "S", "N" );
		cbGuiaTraf.setVlrString( "N" );

		cbSerie = new JCheckBoxPad( "Exige", "S", "N" );
		cbSerie.setVlrString( "N" );

		txtCustoMPMProd.setSoLeitura( true );
		txtCustoPEPSProd.setSoLeitura( true );
		txtSldProd.setSoLeitura( true );
		txtSldResProd.setSoLeitura( true );
		txtDtUltCpProd.setSoLeitura( true );
		txtSldConsigProd.setSoLeitura( true );
		txtSldLiqProd.setSoLeitura( true );

		txtAlmox.setSoLeitura( true );
		txtCustoMPMAlmox.setSoLeitura( true );
		txtCustoPEPSAlmox.setSoLeitura( true );
		txtSldAlmox.setSoLeitura( true );
		txtSldResAlmox.setSoLeitura( true );
		txtSldConsigAlmox.setSoLeitura( true );
		txtSldLiqAlmox.setSoLeitura( true );

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btCodBar.addActionListener( this );
		tpn.addChangeListener( this );

		lcCampos.addPostListener( this );
		lcFatConv.addPostListener( this );

		setImprimir( true );

	}

	private void habAcesso( boolean hab, int tipo ) {

		if ( tipo == 0 ) {
			txtCodPA.setAtivo( hab );
			rgPA.setAtivo( hab );
		}
		if ( ( tipo == 0 ) || ( tipo == 1 ) ) {
			txtAnoCCPA.setAtivo( hab );
			txtCodCCPA.setAtivo( hab );
		}
		if ( ( tipo == 0 ) || ( tipo == 2 ) ) {
			txtCodCaixa.setAtivo( hab );
		}
	}

	private void montaTela() {

		adicCampo( txtCodProd, 7, 20, 70, 20, "CodProd", "C�d.prod.", ListaCampos.DB_PK, true );

		adicCampo( txtRefProd, 80, 20, 70, 20, "RefProd", "Refer�ncia", ListaCampos.DB_SI, true );

		adicCampo( txtDescProd, 153, 20, 384, 20, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, true );

		adicDB( rgTipo, 540, 20, 200, 140, "TipoProd", "Tipo:", true );

		adicCampo( txtDescAuxProd, 7, 60, 530, 20, "DescAuxProd", "Descri��o auxiliar", ListaCampos.DB_SI, false );

		adicCampo( txtCodBarProd, 7, 100, 220, 20, "CodBarProd", "C�digo de barras", ListaCampos.DB_SI, true );

		adic( btCodBar, 230, 100, 20, 20 );

		adicCampo( txtCodEANProd, 253, 100, 284, 20, "CodEANProd", "C�digo EAN", ListaCampos.DB_SI, false );

		adicCampo( txtCodFabProd, 7, 140, 140, 20, "CodFabProd", "C�d. fabricante", ListaCampos.DB_SI, true );

		adicCampo( txtPrecoBaseProd, 150, 140, 75, 20, "PrecoBaseProd", "Pre�o base", ListaCampos.DB_SI, true );

		adicCampo( txtCustoInfoProd, 228, 140, 75, 20, "CustoInfoProd", "Custo infor.", ListaCampos.DB_SI, false );

		adicCampo( txtComisProd, 306, 140, 75, 20, "ComisProd", "%Comis.", ListaCampos.DB_SI, true );

		adicCampo( txtQtdMinProd, 384, 140, 75, 20, "QtdMinProd", "Qtd.min.", ListaCampos.DB_SI, true );

		adicCampo( txtQtdMaxProd, 462, 140, 75, 20, "QtdMaxProd", "Qtd.m�x.", ListaCampos.DB_SI, true );

		adicCampo( txtLocalProd, 7, 180, 165, 20, "LocalProd", "Local armz.", ListaCampos.DB_SI, false );

		btCodBar.setToolTipText( "Gera c�d. barras" );

		adic( new JLabelPad( "Custo MPM" ), 175, 160, 87, 20 );
		adic( txtCustoMPMProd, 175, 180, 76, 20 );
		adic( new JLabelPad( "Custo PEPS" ), 254, 160, 87, 20 );
		adic( txtCustoPEPSProd, 254, 180, 76, 20 ); // Sem inserir no lista campos
		adicCampo( txtSldProd, 333, 180, 76, 20, "SldProd", "Saldo", ListaCampos.DB_SI, false );
		adicCampo( txtSldResProd, 412, 180, 76, 20, "SldResProd", "Saldo res.", ListaCampos.DB_SI, false );
		adicCampo( txtSldConsigProd, 491, 180, 76, 20, "SldConsigProd", "Saldo consig.", ListaCampos.DB_SI, false );
		adicCampo( txtSldLiqProd, 570, 180, 76, 20, "SldLiqProd", "Saldo liq.", ListaCampos.DB_SI, false );

		adic( new JLabelPad( "Almoxarifado" ), 7, 200, 87, 20 );
		adic( txtAlmox, 7, 220, 76, 20 );
		adicCampo( txtDtUltCpProd, 86, 220, 86, 20, "DtUltCpProd", "Ultima compra", ListaCampos.DB_SI, false );
		adic( new JLabelPad( "Custo MPM" ), 175, 200, 87, 20 );
		adic( txtCustoMPMAlmox, 175, 220, 76, 20 );
		adic( new JLabelPad( "Custo PEPS" ), 254, 200, 87, 20 );
		adic( txtCustoPEPSAlmox, 254, 220, 76, 20 );
		adic( new JLabelPad( "Saldo" ), 333, 200, 87, 20 );
		adic( txtSldAlmox, 333, 220, 76, 20 );
		adic( new JLabelPad( "Saldo res." ), 412, 200, 87, 20 );
		adic( txtSldResAlmox, 412, 220, 76, 20 );
		adic( new JLabelPad( "Saldo consig." ), 491, 200, 87, 20 );
		adic( txtSldConsigAlmox, 491, 220, 76, 20 );
		adic( new JLabelPad( "Saldo liq." ), 570, 200, 87, 20 );
		adic( txtSldLiqAlmox, 570, 220, 76, 20 );

		JPanelPad pnControles = new JPanelPad();
		pnControles.setBorder( BorderFactory.createEtchedBorder() );

		adic( pnControles, 7, 245, 710, 100 );

		separador1.setBorder( BorderFactory.createEtchedBorder() );
		separador2.setBorder( BorderFactory.createEtchedBorder() );

		setPainel( pnControles );

		adicDB( cbAtivo, 10, 23, 50, 20, "AtivoProd", "Ativo", true );

		pnControles.adic( separador1, 60, 6, 2, 37 );

		adicDB( cbLote, 70, 23, 58, 20, "CLoteProd", "Lote", true );

		adicDB( cbSerie, 132, 23, 60, 20, "SerieProd", "Nro. S�rie", true );

		adicDB( cbReceita, 198, 23, 60, 20, "UsaReceitaProd", "Receita", true );

		adicDB( cbGuiaTraf, 255, 23, 90, 20, "GuiaTrafProd", "Guia de tr�fego", true );

		pnControles.adic( separador2, 350, 6, 2, 37 );

		adicDB( rgAbaixCust, 360, 23, 280, 40, "VERIFPROD", "Abaixo custo:", true );

		adicDB( cbRMA, 7, 70, 70, 20, "RMAProd", "RMA", true );

		adicDB( cbAdicPDV, 80, 70, 110, 20, "UsaTelaAdicPDV", "PDV", true );

		setPainel( pinGeral );

		adicCampo( txtCodAlmox, 7, 360, 80, 20, "CodAlmox", "C�d. almox.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescAlmox, 90, 360, 170, 20, "DescAlmox", "Descri��o do almoxarifado" );

		adicCampo( txtCodSecao, 263, 360, 80, 20, "CodSecao", "C�d. se��o", ListaCampos.DB_FK, txtDescSecao, false );
		adicDescFK( txtDescSecao, 346, 360, 190, 20, "DescSecao", "Descri��o da se��o" );

		adicCampo( txtCodGrup, 7, 400, 80, 20, "CodGrup", "C�d. grupo", ListaCampos.DB_FK, txtDescGrup, true );
		adicDescFK( txtDescGrup, 90, 400, 170, 20, "DescGrup", "Descri��o do grupo" );

		adicCampo( txtCodFisc, 263, 400, 80, 20, "CodFisc", "C�d. fiscal", ListaCampos.DB_FK, txtDescFisc, true );
		adicDescFK( txtDescFisc, 346, 400, 190, 20, "DescFisc", "Descri��o da classifica��o fiscal" );

		adicCampo( txtCodUnid, 7, 440, 80, 20, "CodUnid", "C�d. unidade", ListaCampos.DB_FK, txtDescUnid, true );
		adicDescFK( txtDescUnid, 90, 440, 170, 20, "DescUnid", "Descri��o da unidade" );

		adicCampo( txtCodMarca, 263, 440, 80, 20, "CodMarca", "C�d. marca", ListaCampos.DB_FK, txtDescMarca, true );
		adicDescFK( txtDescMarca, 346, 440, 190, 20, "DescMarca", "Descri��o da marca" );

		adicCampo( txtCodMoeda, 7, 480, 80, 20, "CodMoeda", "C�d.moeda", ListaCampos.DB_FK, true );
		adicDescFK( txtDescMoeda, 90, 480, 170, 20, "SingMoeda", "Descri��o da moeda" );

		adicCampo( txtPrazoEnt, 263, 480, 80, 20, "CodPE", "C�d.p/entrega", ListaCampos.DB_FK, txtDescGrup, false );
		adicDescFK( txtDescPrazoEnt, 346, 480, 190, 20, "DescPE", "Descri��o do prazo de entrega" );
		adicDescFK( txtDias, 7, 520, 110, 20, "DiasPE", "Dias p/entrega" );

		adicDB( rgCV, 540, 370, 110, 100, "CVProd", "Cadastro para:", true );

		adic( btDuplicar, 540, 500, 110, 30 );

		// Decri��o completa

		adicTab( "Descri��o completa", pnDesc );
		adicDBLiv( txaDescComp, "DescCompProd", "Descri��o completa", false );
		pnDesc.add( spnDesc );

		setListaCampos( true, "PRODUTO", "EQ" );

		// Medidas

		setPainel( pinMedidas, pnMedidas );
		adicTab( "Medidas", pnMedidas );
		adicCampo( txtPesoBrutProd, 7, 20, 110, 20, "PesoBrutProd", "Peso bruto", ListaCampos.DB_SI, true );
		adicCampo( txtPesoLiqProd, 120, 20, 110, 20, "PesoLiqProd", "Peso l�quido", ListaCampos.DB_SI, true );

		adicCampo( txtVlrDens, 233, 20, 110, 20, "VlrDensidade", "Densidade", ListaCampos.DB_SI, false );
		adicCampo( txtVlrConcent, 346, 20, 110, 20, "VlrConcent", "Concentra��o", ListaCampos.DB_SI, false );

		adicCampo( txtVlrCompri, 7, 60, 110, 20, "Comprimento", "Comprimento(cm)", ListaCampos.DB_SI, false );
		adicCampo( txtVlrLarg, 120, 60, 110, 20, "Largura", "Largura(cm)", ListaCampos.DB_SI, false );
		adicCampo( txtVlrEspess, 235, 60, 110, 20, "Espessura", "Espessura(cm)", ListaCampos.DB_SI, false );

		adicCampo( txtQtdEmbalagem, 7, 100, 110, 20, "QtdEmbalagem", "Qtd. Embalagem", ListaCampos.DB_SI, false );
		adicCampo( txtCubagem, 120, 100, 110, 20, "Cubagem", "Cubagem (m3)", ListaCampos.DB_SI, false );

		setListaCampos( true, "PRODUTO", "EQ" );

		// Pre�o

		setPainel( pinRodPreco, pnPreco );
		adicTab( "Pre�os", pnPreco );
		setListaCampos( lcPreco );
		setNavegador( navPreco );
		pnPreco.add( pinRodPreco, BorderLayout.SOUTH );
		pnPreco.add( spnPreco, BorderLayout.CENTER );

		pinRodPreco.adic( navPreco, 0, 90, 270, 25 );

		lcClasCliPreco.add( new GuardaCampo( txtCodClasCliPreco, "CodClasCli", "C�d.c.cli.", ListaCampos.DB_PK, false ) );
		lcClasCliPreco.add( new GuardaCampo( txtDescClasCliPreco, "DescClasCli", "Descri��o da classifica��o do cliente", ListaCampos.DB_SI, false ) );
		lcClasCliPreco.montaSql( false, "CLASCLI", "VD" );
		lcClasCliPreco.setQueryCommit( false );
		lcClasCliPreco.setReadOnly( true );
		txtDescClasCliPreco.setListaCampos( lcClasCliPreco );
		txtCodClasCliPreco.setTabelaExterna( lcClasCliPreco, FClasCli.class.getCanonicalName() );

		lcTabPreco.add( new GuardaCampo( txtCodTabPreco, "CodTab", "C�d.tab.p�.", ListaCampos.DB_PK, true ) );
		lcTabPreco.add( new GuardaCampo( txtDescTabPreco, "DescTab", "Descri��o da tabela de pre�o", ListaCampos.DB_SI, false ) );
		lcTabPreco.montaSql( false, "TABPRECO", "VD" );
		lcTabPreco.setReadOnly( true );
		lcTabPreco.setQueryCommit( false );
		txtDescTabPreco.setListaCampos( lcTabPreco );
		txtCodTabPreco.setTabelaExterna( lcTabPreco, FTabPreco.class.getCanonicalName() );

		lcPlanoPagPreco.add( new GuardaCampo( txtCodPlanoPagPreco, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_PK, true ) );
		lcPlanoPagPreco.add( new GuardaCampo( txtDescPlanoPagPreco, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPagPreco.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPagPreco.setReadOnly( true );
		lcPlanoPagPreco.setQueryCommit( false );
		txtDescPlanoPagPreco.setListaCampos( lcPlanoPagPreco );
		txtCodPlanoPagPreco.setTabelaExterna( lcPlanoPagPreco, FPlanoPag.class.getCanonicalName() );

		adicCampo( txtCodPrecoProd, 7, 20, 80, 20, "CodPrecoProd", "C�d.p�.prod.", ListaCampos.DB_PK, true );
		adicCampo( txtCodClasCliPreco, 90, 20, 67, 20, "CodClasCli", "C�d.c.cli.", ListaCampos.DB_FK, txtDescClasCliPreco, false );
		adicDescFK( txtDescClasCliPreco, 160, 20, 217, 20, "DescClasCli", "Descri��o da classifica��o do cliente" );
		adicCampo( txtCodTabPreco, 380, 20, 77, 20, "CodTab", "C�d.tab.pc.", ListaCampos.DB_FK, txtDescTabPreco, true );
		adicDescFK( txtDescTabPreco, 460, 20, 190, 20, "DescTab", "Descri��o da tab. de pre�os" );
		adicCampo( txtCodPlanoPagPreco, 7, 60, 80, 20, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_FK, txtDescPlanoPagPreco, true );
		adicDescFK( txtDescPlanoPagPreco, 90, 60, 197, 20, "DescPlanoPag", "Descri��o do plano de pagamento" );
		adicCampo( txtPrecoProd, 290, 60, 110, 20, "PrecoProd", "Pre�o", ListaCampos.DB_SI, true );
		setListaCampos( true, "PRECOPROD", "VD" );
		lcPreco.setOrdem( "CodPrecoProd" );
		lcPreco.setQueryInsert( false );
		lcPreco.setQueryCommit( false );
		lcPreco.montaTab();
		tabPreco.setTamColuna( 65, 0 );
		tabPreco.setTamColuna( 60, 1 );
		tabPreco.setTamColuna( 110, 2 );
		tabPreco.setTamColuna( 60, 3 );
		tabPreco.setTamColuna( 110, 4 );
		tabPreco.setTamColuna( 60, 5 );
		tabPreco.setTamColuna( 110, 6 );
		tabPreco.setTamColuna( 75, 7 );

		/* Fatores de convers�o */

		setPainel( pinRodFatConv, pnFatConv );
		adicTab( "Fatores de convers�o", pnFatConv );
		setListaCampos( lcFatConv );

		setNavegador( navFatConv );
		pnFatConv.add( pinRodFatConv, BorderLayout.SOUTH );
		pnFatConv.add( spnFatConv, BorderLayout.CENTER );

		JPanelPad pnnav = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
		pnnav.add( navFatConv, BorderLayout.WEST );
		pnnav.setBorder( BorderFactory.createEtchedBorder() );

		pinRodFatConv.adic( pnnav, 0, 86, 688, 30 );

		lcUnidFat.add( new GuardaCampo( txtUnidFat, "CodUnid", "C�d.unid.", ListaCampos.DB_PK, true ) );
		lcUnidFat.add( new GuardaCampo( txtDescUnidFat, "DescUnid", "Descri��o da unidade", ListaCampos.DB_SI, false ) );
		lcUnidFat.montaSql( false, "UNIDADE", "EQ" );
		lcUnidFat.setReadOnly( true );
		lcUnidFat.setQueryCommit( false );
		txtDescUnidFat.setListaCampos( lcUnidFat );
		txtUnidFat.setTabelaExterna( lcUnidFat, FUnidade.class.getCanonicalName() );

		JLabelPad sepconv = new JLabelPad();
		sepconv.setBorder( BorderFactory.createEtchedBorder() );
		adic( sepconv, 128, 4, 2, 80 );

		lbUnidFat = adicCampo( txtUnidFat, 140, 20, 80, 20, "CodUnid", "C�d.unidade", ListaCampos.DB_PF, txtDescUnidFat, true );
		lbDescUnidFat = adicDescFK( txtDescUnidFat, 223, 20, 250, 20, "DescUnid", "Descri��o da unidade" );
		lbFatConv = adicCampo( txtFatConv, 140, 60, 80, 20, "FatConv", "Fator", ListaCampos.DB_SI, false );
		lbCpFatConv = adicDB( cbCpFatConv, 476, 20, 120, 20, "CpFatConv", "Padr�o p/compra", true );

		adicDB( rgTipoConv, 7, 20, 110, 60, "tipoconv", "Tipo de convers�o", true );

		lbCodProdEst = adicCampo( txtCodProdEst, 140, 60, 80, 20, "CodProdEt", "C�d.Prod.", ListaCampos.DB_FK, txtDescProdEst, false );
		lbSeqEst = adicCampo( txtSeqEst, 223, 60, 80, 20, "SeqEst", "Seq.Est.", ListaCampos.DB_FK, txtDescProdEst, false );
		lbDescProdEst = adicDescFK( txtDescProdEst, 306, 60, 270, 20, "DescEst", "Descri��o da estrutura" );

		rgTipoConv.setVlrString( "U" );

		setListaCampos( false, "FATCONV", "EQ" );

		lcFatConv.montaTab();
		lcFatConv.setQueryInsert( false );
		lcFatConv.setQueryCommit( false );

		tabFatConv.setTamColuna( 60, 1 );
		tabFatConv.setTamColuna( 50, 5 );
		tabFatConv.setTamColuna( 240, 6 );

		// *****************************************************************************/

		// Planejamento
		// lcPlan.setUsaME(false);
		lcPlan.add( new GuardaCampo( txtCodPlan, "CodPlan", "C�d.plan.", ListaCampos.DB_PK, true ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descri��o do planejamento", ListaCampos.DB_SI, false ) );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlan.setReadOnly( true );
		lcPlan.setQueryCommit( false );
		txtDescPlan.setListaCampos( lcPlan );
		txtCodPlan.setTabelaExterna( lcPlan, FPlanejamento.class.getCanonicalName() );

		// lcCC.setUsaME(false);
		lcCC.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano.cc.", ListaCampos.DB_PK, true ) );
		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "C�d.cc.", ListaCampos.DB_PK, true ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descri��o do centro de custo", ListaCampos.DB_SI, false ) );

		lcCC.montaSql( false, "CC", "FN" );
		lcCC.setReadOnly( true );
		lcCC.setQueryCommit( false );
		txtDescCC.setListaCampos( lcCC );
		txtAnoCC.setTabelaExterna( lcCC, FCentroCusto.class.getCanonicalName() );
		txtCodCC.setTabelaExterna( lcCC, FCentroCusto.class.getCanonicalName() );

		// CC Acesso
		lcCCAcesso.add( new GuardaCampo( txtAnoCCPA, "AnoCC", "Ano.cc.", ListaCampos.DB_PK, true ) );
		lcCCAcesso.add( new GuardaCampo( txtCodCCPA, "CodCC", "C�d.cc.", ListaCampos.DB_PK, true ) );
		lcCCAcesso.add( new GuardaCampo( txtDescCCPA, "DescCC", "Descri��o do C.C", ListaCampos.DB_SI, false ) );
		lcCCAcesso.montaSql( false, "CC", "FN" );
		lcCCAcesso.setReadOnly( true );
		lcCCAcesso.setQueryCommit( false );
		txtDescCCPA.setListaCampos( lcCCAcesso );
		txtAnoCCPA.setTabelaExterna( lcCCAcesso, FCentroCusto.class.getCanonicalName() );
		txtCodCCPA.setTabelaExterna( lcCCAcesso, FCentroCusto.class.getCanonicalName() );

		// Caixa
		lcCaixa.add( new GuardaCampo( txtCodCaixa, "CodCaixa", "C�d.caixa", ListaCampos.DB_PK, false ) );
		lcCaixa.add( new GuardaCampo( txtDescCaixa, "DescCaixa", "Descri��o do Caixa", ListaCampos.DB_SI, false ) );
		lcCaixa.montaSql( false, "CAIXA", "PV" );
		lcCaixa.setReadOnly( true );
		lcCaixa.setQueryCommit( false );
		txtDescCaixa.setListaCampos( lcCaixa );
		txtCodCaixa.setTabelaExterna( lcCaixa, FCaixa.class.getCanonicalName() );

		setPainel( pinRodProdPlan, pnProdPlan );
		adicTab( "Planejamento", pnProdPlan );

		setListaCampos( lcProdPlan );
		setNavegador( navProdPlan );

		pnProdPlan.add( pinRodProdPlan, BorderLayout.SOUTH );
		pnProdPlan.add( spnPlan, BorderLayout.CENTER );

		pinRodProdPlan.adic( navProdPlan, 0, 90, 270, 25 );

		adicCampo( txtSeqPP, 7, 20, 80, 20, "SeqPP", "N.seq.", ListaCampos.DB_PK, true );
		adicCampo( txtCodPlan, 90, 20, 80, 20, "CodPlan", "C�d.plan.", ListaCampos.DB_PF, txtDescPlan, true );
		adicDescFK( txtDescPlan, 173, 20, 250, 20, "DescPlan", "Descri��o do planejamento" );
		adicDB( rgTipoPP, 426, 20, 200, 30, "TipoPP", "Tipo", true );

		adicCampo( txtAnoCC, 7, 60, 80, 20, "AnoCC", "Ano.cc.", ListaCampos.DB_PF, txtDescCC, true );
		adicCampo( txtCodCC, 90, 60, 107, 20, "CodCC", "C�d.cc.", ListaCampos.DB_PF, true );
		adicDescFK( txtDescCC, 200, 60, 250, 20, "DescCC", "Descri��o do centro de custo" );

		setListaCampos( true, "PRODPLAN", "EQ" );
		lcProdPlan.setOrdem( "SeqPP" );
		lcProdPlan.montaTab();
		lcProdPlan.setQueryCommit( false );
		tabProdPlan.setTamColuna( 50, 0 );
		tabProdPlan.setTamColuna( 100, 1 );
		tabProdPlan.setTamColuna( 250, 2 );
		tabProdPlan.setTamColuna( 50, 3 );
		tabProdPlan.setTamColuna( 50, 4 );
		tabProdPlan.setTamColuna( 100, 5 );
		tabProdPlan.setTamColuna( 250, 6 );

		// Fornecedor
		setPainel( pinRodFor, pnFor );
		adicTab( "Fornecedores", pnFor );
		setListaCampos( lcFor );

		navFor.setAtivo( 6, false );

		setNavegador( navFor );
		pnFor.add( pinRodFor, BorderLayout.SOUTH );
		pnFor.add( spnFor, BorderLayout.CENTER );

		pinRodFor.adic( navFor, 0, 50, 270, 25 );

		lcForFK.setUsaME( false );
		lcForFK.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, true ) );
		lcForFK.add( new GuardaCampo( txtDescFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		lcForFK.montaSql( false, "FORNECED", "CP" );
		lcForFK.setReadOnly( true );
		lcForFK.setQueryCommit( false );
		txtCodFor.setListaCampos( lcForFK );
		txtCodFor.setTabelaExterna( lcForFK, FFornecedor.class.getCanonicalName() );

		adicCampo( txtCodFor, 7, 20, 80, 20, "CodFor", "C�d.for.", ListaCampos.DB_PF, txtDescFor, true );
		adicDescFK( txtDescFor, 90, 20, 300, 20, "RazFor", "Raz�o social do fornecedor" );
		adicCampo( txtCodProdFor, 400, 20, 105, 20, "RefProdFor", "C�d.prod.for.", ListaCampos.DB_SI, false );
		setListaCampos( false, "PRODFOR", "CP" );
		lcFor.montaTab();
		lcFor.setQueryInsert( false );
		lcFor.setQueryCommit( false );
		tabFor.setTamColuna( 250, 1 );

		// Lote
		setPainel( pinRodLote, pnLote );
		// adicTab( "Lotes", pnLote );
		setListaCampos( lcLote );
		setNavegador( navLote );
		pnLote.add( pinRodLote, BorderLayout.SOUTH );
		pnLote.add( spnLote, BorderLayout.CENTER );

		pinRodLote.adic( navLote, 0, 90, 270, 25 );

		txtSldLote.setSoLeitura( true );
		txtSldResLote.setSoLeitura( true );
		txtSldConsigLote.setSoLeitura( true );
		txtSldLiqLote.setSoLeitura( true );

		adicCampo( txtCodLote, 7, 20, 110, 20, "CodLote", "C�d.lote", ListaCampos.DB_PK, true );
		adicCampo( txtDiniLote, 120, 20, 100, 20, "DIniLote", "Data inicial", ListaCampos.DB_SI, false );
		adicCampo( txtVenctoLote, 223, 20, 100, 20, "VenctoLote", "Vencimento", ListaCampos.DB_SI, true );
		adicCampo( txtQtdProdLote, 326, 20, 100, 20, "QtdProdLote", "Qtd.Prod.Lote", ListaCampos.DB_SI, false );
		adicCampo( txtDiasAvisoLote, 429, 20, 100, 20, "DiasAvisoLote", "Dias para aviso", ListaCampos.DB_SI, true );
		adicCampo( txtSldLote, 7, 60, 80, 20, "SldLote", "Saldo", ListaCampos.DB_SI, false );
		adicCampo( txtSldResLote, 90, 60, 80, 20, "SldResLote", "Saldo res.", ListaCampos.DB_SI, false );
		adicCampo( txtSldConsigLote, 173, 60, 80, 20, "SldConsigLote", "Saldo consig.", ListaCampos.DB_SI, false );
		adicCampo( txtSldLiqLote, 256, 60, 80, 20, "SldLiqLote", "Saldo liq.", ListaCampos.DB_SI, false );

		setListaCampos( false, "LOTE", "EQ" );
		lcLote.setOrdem( "VenctoLote desc" );
		lcLote.setQueryInsert( false );
		lcLote.setQueryCommit( false );
		lcLote.montaTab();
		lcLote.setDinWhereAdic( "CODLOTE = #N", txtCodProd );
		tabLote.setTamColuna( 110, 0 );
		tabLote.setTamColuna( 100, 1 );
		tabLote.setTamColuna( 100, 2 );

		// Seriex
		setPainel( pinRodSerie, pnSerie );
		adicTab( "S�ries", pnSerie );
		setListaCampos( lcSerie );
		setNavegador( navSerie );
		pnSerie.add( pinRodSerie, BorderLayout.SOUTH );
		pnSerie.add( spnSerie, BorderLayout.CENTER );

		pinRodSerie.adic( navSerie, 0, 135, 270, 25 );

		adicCampo( txtNumSerie, 7, 20, 250, 20, "NumSerie", "Num.S�rie", ListaCampos.DB_PK, true );
		adicCampo( txtDtFabricSerie, 260, 20, 100, 20, "DtFabricSerie", "Data fabrica��o", ListaCampos.DB_SI, false );
		adicCampo( txtDtValidSerie, 363, 20, 100, 20, "DtValidSerie", "Data de validade", ListaCampos.DB_SI, false );
		JLabelPad lbObsSerie = adicDBLiv( txaObsSerie, "ObsSerie", "Observa��es", false );
		adic( lbObsSerie, 7, 40, 200, 20 );
		adic( spnObsSerie, 7, 60, 458, 60 );

		setListaCampos( false, "SERIE", "EQ" );
		lcSerie.setOrdem( "DtValidSerie desc" );
		lcSerie.setQueryInsert( false );
		lcSerie.setQueryCommit( false );
		lcSerie.montaTab();
		lcSerie.setDinWhereAdic( "CODPROD = #N", txtCodProd );
		tabSerie.setTamColuna( 200, 0 );
		tabSerie.setTamColuna( 100, 1 );
		tabSerie.setTamColuna( 100, 2 );
		tabSerie.setTamColuna( 250, 3 );

		// Codigo alternativo

		setPainel( pinRodCodAltProd, pnCodAltProd );
		adicTab( "C�d.altern.", pnCodAltProd );
		setListaCampos( lcCodAltProd );
		setNavegador( navCodAltProd );
		pnCodAltProd.add( pinRodCodAltProd, BorderLayout.SOUTH );
		pnCodAltProd.add( spnCodAltProd, BorderLayout.CENTER );
		pinRodCodAltProd.adic( navCodAltProd, 0, 50, 270, 25 );
		navCodAltProd.setAtivo( 6, false );

		adicCampo( txtCodAltProd, 7, 20, 150, 20, "CodAltProd", "C�digo alternativo", ListaCampos.DB_PK, null, true );
		setListaCampos( false, "CODALTPROD", "EQ" );
		lcCodAltProd.setQueryInsert( false );
		lcCodAltProd.setQueryCommit( false );

		txtCodAltProd.setTabelaExterna( lcCodAltProd, null );
		txtCodAltProd.setEnterSai( false );
		lcCodAltProd.montaTab();
		tabCodAltProd.setTamColuna( 150, 0 );

		// Fotos

		setPainel( pinRodFoto, pnFoto );
		adicTab( "Fotos", pnFoto );
		setListaCampos( lcFoto );
		setNavegador( navFoto );
		pnFoto.add( pinRodFoto, BorderLayout.SOUTH );
		pnFoto.add( spnFoto, BorderLayout.CENTER );

		pinRodFoto.adic( navFoto, 0, 140, 270, 25 );

		txtAltFotoProd.setEnabled( false );
		txtLargFotoProd.setEnabled( false );

		adicCampo( txtCodFotoProd, 7, 20, 70, 20, "CodFotoProd", "N� foto", ListaCampos.DB_PK, true );
		adicCampo( txtDescFotoProd, 80, 20, 250, 20, "DescFotoProd", "Descri��o da foto", ListaCampos.DB_SI, true );
		adicDB( rgTF, 7, 60, 323, 30, "TipoFotoProd", "Tamanho:", true );
		adicCampo( txtLargFotoProd, 7, 110, 80, 20, "LargFotoProd", "Largura", ListaCampos.DB_SI, true );
		adicCampo( txtAltFotoProd, 90, 110, 77, 20, "AltFotoProd", "Altura", ListaCampos.DB_SI, true );
		adicDB( imFotoProd, 350, 20, 150, 140, "FotoProd", "Foto: (m�x. 63K)", true );

		setListaCampos( true, "FOTOPROD", "VD" );
		lcFoto.setOrdem( "CodFotoProd" );
		lcFoto.setQueryInsert( false );
		lcFoto.setQueryCommit( false );
		lcFoto.montaTab();
		tabFoto.setTamColuna( 80, 0 );
		tabFoto.setTamColuna( 250, 1 );
		tabFoto.setTamColuna( 80, 2 );
		tabFoto.setTamColuna( 80, 3 );
		tabFoto.setTamColuna( 80, 4 );

		// Acesso

		setPainel( pinRodCodAcess, pnCodAcess );
		adicTab( "Acesso", pnCodAcess );
		setListaCampos( lcProdAcesso );
		setNavegador( navCodAcess );
		pnCodAcess.add( pinRodCodAcess, BorderLayout.SOUTH );
		pnCodAcess.add( spnCodAcess, BorderLayout.CENTER );
		pinRodCodAcess.adic( navCodAcess, 0, 90, 270, 25 );
		navCodAcess.setAtivo( 6, false );

		adicCampo( txtCodPA, 7, 20, 70, 20, "CodPA", "C�d.acess.", ListaCampos.DB_PK, null, true );
		adicDB( rgPA, 80, 20, 140, 30, "TipoPA", "Tipo", true );

		adicCampo( txtAnoCCPA, 223, 20, 80, 20, "AnoCC", "Ano CC.", ListaCampos.DB_FK, txtDescCCPA, false );
		adicCampo( txtCodCCPA, 306, 20, 150, 20, "CodCC", "C�d. CC.", ListaCampos.DB_FK, false );
		adicDescFK( txtDescCCPA, 459, 20, 180, 20, "DescCC", "Descri��o do C.C." );

		adicCampo( txtCodCaixa, 223, 60, 80, 20, "CodCaixa", "C�d.caixa", ListaCampos.DB_FK, txtDescCaixa, false );
		adicDescFK( txtDescCaixa, 306, 60, 250, 20, "DescCaixa", "Descri��o do caixa" );
		setListaCampos( true, "PRODACESSO", "EQ" );
		lcProdAcesso.setQueryInsert( false );
		lcProdAcesso.setQueryCommit( false );

		txtCodPA.setTabelaExterna( lcProdAcesso, null );
		txtCodPA.setEnterSai( false );
		lcProdAcesso.montaTab();
		tabCodAcess.setTamColuna( 90, 0 );
		tabCodAcess.setTamColuna( 50, 1 );
		tabCodAcess.setTamColuna( 70, 2 );
		tabCodAcess.setTamColuna( 120, 3 );
		tabCodAcess.setTamColuna( 80, 4 );

		txtCodProd.requestFocus();
		btDuplicar.addActionListener( this );

	}

	private void buscaEstoque() {

		ResultSet rs = null;
		String sWhere = "";
		String sSQL = "";
		int iCodAlmox = 0;
		int iParam = 1;

		String sCodProd = null;
		String sFiltro = "";

		try {
			sCodProd = txtCodProd.getVlrString().trim();
			iCodAlmox = txtCodAlmox.getVlrInteger().intValue();

			if ( sCodProd.equals( "" ) ) {
				Funcoes.mensagemInforma( this, "Selecione um produto!" );
				txtCodProd.requestFocus();
				return;
			}

			sFiltro = "P.CODPROD=" + sCodProd;

			if ( iCodAlmox == 0 ) {
				sWhere = "SP.CODEMPAX = P.CODEMPAX AND SP.CODFILIALAX=P.CODFILIALAX AND " + "SP.CODALMOX = P.CODALMOX";
			}
			else {
				sWhere = "SP.CODEMPAX = ? AND SP.CODFILIALAX=? AND SP.CODALMOX = ?";
			}

			sSQL = "SELECT P.CODPROD,P.DESCPROD,P.SLDPROD, P.SLDRESPROD, " + "P.SLDCONSIGPROD,P.SLDLIQPROD,SP.SLDPROD SLDPRODAX, SP.SLDRESPROD SLDRESPRODAX, " + "SP.SLDCONSIGPROD SLDCONSIGPRODAX,SP.SLDLIQPROD SLDLIQPRODAX " + "FROM EQPRODUTO P, EQSALDOPROD SP "
					+ "WHERE SP.CODEMP=P.CODEMP AND SP.CODFILIAL=P.CODFILIAL AND SP.CODPROD = P.CODPROD AND " + "P.CODEMPGP=? AND P.CODFILIALGP=? AND " + sFiltro + " AND " + sWhere + " ORDER BY P.DESCPROD ";

			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( iParam++, Aplicativo.iCodEmp );
			ps.setInt( iParam++, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			if ( iCodAlmox != 0 ) {
				ps.setInt( iParam++, Aplicativo.iCodEmp );
				ps.setInt( iParam++, ListaCampos.getMasterFilial( "EQALMOX" ) );
				ps.setInt( iParam++, iCodAlmox );
			}
			rs = ps.executeQuery();

			if ( rs.next() ) {
				txtSldAlmox.setVlrDouble( new Double( rs.getDouble( iCodAlmox != 0 ? "SLDPRODAX" : "SLDPROD" ) + "" ) );
				txtSldResAlmox.setVlrDouble( new Double( rs.getDouble( iCodAlmox != 0 ? "SLDRESPRODAX" : "SLDRESPROD" ) + "" ) );
				txtSldConsigAlmox.setVlrDouble( new Double( rs.getDouble( iCodAlmox != 0 ? "SLDCONSIGPRODAX" : "SLDCONSIGPROD" ) + "" ) );
				txtSldLiqAlmox.setVlrDouble( new Double( rs.getDouble( iCodAlmox != 0 ? "SLDLIQPRODAX" : "SLDLIQPROD" ) + "" ) );
			}
			else {
				txtSldAlmox.setVlrDouble( new Double( 0 ) );
				txtSldResAlmox.setVlrDouble( new Double( 0 ) );
				txtSldConsigAlmox.setVlrDouble( new Double( 0 ) );
				txtSldLiqAlmox.setVlrDouble( new Double( 0 ) );
			}

			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar saldos por almoxarifado!\n" + err.getMessage() );
		} finally {
			sSQL = null;
		}

	}

	private void duplicar() {

		if ( txtCodProd.getVlrInteger().intValue() == 0 || lcCampos.getStatus() != ListaCampos.LCS_SELECT ) {
			Funcoes.mensagemInforma( this, "Selecione um produto!" );
			return;
		}

		try {

			String sSQL = "SELECT ICOD FROM EQCOPIAPROD(?,?,?)";

			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, txtCodProd.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, lcCampos.getCodFilial() );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				if ( Funcoes.mensagemConfirma( this, "Produto '" + rs.getInt( 1 ) + "' criado com sucesso!\n" + "Gostaria de edita-lo agora?" ) == JOptionPane.OK_OPTION ) {
					txtCodProd.setVlrInteger( new Integer( rs.getInt( 1 ) ) );
					lcCampos.carregaDados();
				}
			}

			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao duplicar o produto!\n" + err.getMessage() );
			err.printStackTrace();
		}

	}

	private String[] getPrefs() {

		String sRetorno[] = new String[ 5 ];
		String sSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sSQL = "SELECT P.CODMOEDA, P.PEPSPROD, P.TIPOCODBAR, E.CODEANEMP, PA.CODEANPAIS  " + "FROM SGPREFERE1 P, SGEMPRESA E, SGFILIAL F, SGPAIS PA " + "WHERE P.CODEMP=? AND P.CODFILIAL=? AND E.CODEMP=P.CODEMP AND " + "F.CODEMP=E.CODEMP AND F.CODFILIAL=? AND " + "PA.CODPAIS=F.CODPAIS";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			ps.setInt( 3, Aplicativo.iCodFilial );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				sRetorno[ eprefs.CODMOEDA.ordinal() ] = rs.getString( "CODMOEDA" );
				sRetorno[ eprefs.PEPSPROD.ordinal() ] = rs.getString( "PEPSPROD" );
				sRetorno[ eprefs.TIPOCODBAR.ordinal() ] = rs.getString( "TIPOCODBAR" );
				sRetorno[ eprefs.CODEANEMP.ordinal() ] = rs.getString( "CODEANEMP" );
				sRetorno[ eprefs.CODPAISEMP.ordinal() ] = rs.getString( "CODEANPAIS" );
			}

			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage() );
			err.printStackTrace();
		} finally {
			rs = null;
			ps = null;
			sSQL = null;
		}

		return sRetorno;
	}

	private void carregaMoeda() {

		if ( sPrefs != null ) {
			txtCodMoeda.setVlrString( sPrefs[ 0 ] );
		}
	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		String[] sValores;
		ImprimeOS imp = new ImprimeOS( "", con );
		FAndamento And = null;
		int linPag = imp.verifLinPag() - 1;
		int iContaReg = 0;

		DLRProduto dl = new DLRProduto( con );
		dl.setVisible( true );
		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}
		sValores = dl.getValores();
		dl.dispose();

		if ( sValores[ 1 ].trim().length() > 0 ) {
			sWhere.append( "DESCPROD >= '" + sValores[ 1 ] + "'" );
			imp.addSubTitulo( "PRODUTOS MAIORES QUE " + sValores[ 1 ].trim() );
		}
		if ( sValores[ 2 ].trim().length() > 0 ) {
			sWhere.append( sWhere.length() > 0 ? " AND " : "" );
			sWhere.append( "DESCPROD <= '" + sValores[ 2 ] + "'" );
			imp.addSubTitulo( "PRODUTOS MENORES QUE " + sValores[ 2 ].trim() );
		}
		if ( sValores[ 11 ].trim().length() > 0 ) {
			sWhere.append( sWhere.length() > 0 ? " AND " : "" );
			sWhere.append( "CODPROD >= '" + sValores[ 11 ] + "'" );
			imp.addSubTitulo( "PRODUTOS MAIORES QUE " + sValores[ 11 ].trim() );
		}
		if ( sValores[ 12 ].trim().length() > 0 ) {
			sWhere.append( sWhere.length() > 0 ? " AND " : "" );
			sWhere.append( "CODPROD <= '" + sValores[ 12 ] + "'" );
			imp.addSubTitulo( "PRODUTOS MENORES QUE " + sValores[ 12 ].trim() );
		}
		if ( sValores[ 3 ].equals( "S" ) ) {
			sWhere.append( sWhere.length() > 0 ? " AND " : "" );
			sWhere.append( "ATIVOPROD='S'" );
			imp.addSubTitulo( "PRODUTOS ATIVOS" );
		}
		if ( sValores[ 4 ].length() > 0 ) {
			sWhere.append( sWhere.length() > 0 ? " AND " : "" );
			sWhere.append( "CODPROD IN (SELECT CODPROD FROM CPPRODFOR WHERE CODFOR = " + sValores[ 4 ] + ")" );
			imp.addSubTitulo( "FORNECEDOR = " + sValores[ 4 ].trim() );
		}
		if ( sValores[ 7 ].length() > 0 ) {
			sWhere.append( sWhere.length() > 0 ? " AND " : "" );
			sWhere.append( "CODALMOX = " + sValores[ 7 ] );
			imp.addSubTitulo( "ALMOXARIFADO = " + sValores[ 8 ] );
		}

		if ( sValores[ 9 ].length() > 0 ) {
			sWhere.append( sWhere.length() > 0 ? " AND " : "" );
			sWhere.append( "CODMARCA = '" + sValores[ 9 ] + "'" );
			imp.addSubTitulo( "MARCA = " + sValores[ 10 ] );
		}

		if ( sValores[ 13 ].length() > 0 && !"T".equals( sValores[ 13 ] ) ) {
			sWhere.append( sWhere.length() > 0 ? " AND " : "" );
			sWhere.append( "TIPOPROD = '" + sValores[ 13 ] + "'" );
		}

		if ( "C".equals( sValores[ 6 ] ) ) {

			sSQL.append( "SELECT CODPROD,REFPROD, CODALMOX, DESCPROD,CODUNID, CODMARCA,TIPOPROD,CODGRUP,CODBARPROD," );
			sSQL.append( "CODFABPROD, COMISPROD, PESOLIQPROD, PESOBRUTPROD, QTDMINPROD, QTDMAXPROD, CLOTEPROD, CUSTOMPMPROD," );
			sSQL.append( "CUSTOPEPSPROD, PRECOBASEPROD, SLDPROD, SLDRESPROD, SLDCONSIGPROD, SLDLIQPROD, DTULTCPPROD, QTDULTCPPROD " );
			sSQL.append( "FROM EQPRODUTO " );
			sSQL.append( sWhere.length() > 0 ? " WHERE " : "" );
			sSQL.append( sWhere );
			sSQL.append( " ORDER BY " + sValores[ 0 ] );

			try {

				ps = con.prepareStatement( "SELECT COUNT(*) FROM EQPRODUTO" + ( sWhere.length() > 0 ? " WHERE " : "" ) + sWhere.toString() );
				rs = ps.executeQuery();
				rs.next();

				And = new FAndamento( "Montando Relat�rio, Aguarde!", 0, rs.getInt( 1 ) - 1 );

				con.commit();

				ps = con.prepareStatement( sSQL.toString() );
				rs = ps.executeQuery();

				imp.limpaPags();
				imp.montaCab();
				imp.setTitulo( "Relat�rio de Produtos" );

				while ( rs.next() ) {

					if ( imp.pRow() >= linPag ) {
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );
						imp.incPags();
						imp.eject();
					}

					if ( imp.pRow() == 0 ) {
						imp.impCab( 136, true );
						imp.say( 0, imp.comprimido() );
						imp.say( 0, "|" );
						imp.say( 135, "|" );
					}

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 2, "C�digo:" );
					imp.say( 12, rs.getString( "CodProd" ) );
					imp.say( 22, "Ref.:" );
					imp.say( 28, rs.getString( "RefProd" ) );
					imp.say( 42, "Descri��o:" );
					imp.say( 53, rs.getString( "DescProd" ) );
					imp.say( 104, "Cod.Bar.:" );
					imp.say( 115, rs.getString( "codBarProd" ) );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 2, "Cod.Fabr.:" );
					imp.say( 13, rs.getString( "CodFabProd" ) );
					imp.say( 27, "Grupo:" );
					imp.say( 34, rs.getString( "Codgrup" ) );
					imp.say( 48, "Custo:" );
					imp.say( 55, rs.getString( "custoMPMprod" ) );
					imp.say( 71, "Pre�o base:" );
					imp.say( 83, rs.getString( "precobaseprod" ) );
					imp.say( 99, "Saldo:" );
					imp.say( 106, rs.getString( "sldprod" ) );
					imp.say( 121, "Un.:" );
					imp.say( 126, rs.getString( "codunid" ) );
					imp.say( 135, "|" );

					And.atualiza( iContaReg++ );

				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );

				imp.eject();
				imp.fechaGravacao();

				rs.close();
				ps.close();

				con.commit();

				dl.dispose();
				And.dispose();

			} catch ( SQLException err ) {
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro consulta tabela de produtos!" + err.getMessage() );
			}

		}
		else if ( "R".equals( sValores[ 6 ] ) ) {

			sSQL.append( "SELECT CODPROD,DESCPROD,CODUNID, SLDLIQPROD, PRECOBASEPROD FROM EQPRODUTO" );
			sSQL.append( sWhere.length() > 0 ? " WHERE " : "" );
			sSQL.append( sWhere );
			sSQL.append( " ORDER BY " + dl.getValores()[ 0 ] );

			try {

				ps = con.prepareStatement( "SELECT COUNT(*) FROM EQPRODUTO" + ( sWhere.length() > 0 ? " WHERE " : "" ) + sWhere.toString() );
				rs = ps.executeQuery();
				rs.next();

				And = new FAndamento( "Montando Relat�rio, Aguarde!", 0, rs.getInt( 1 ) - 1 );

				rs.close();
				ps.close();

				con.commit();

				ps = con.prepareStatement( sSQL.toString() );
				rs = ps.executeQuery();

				imp.limpaPags();
				imp.montaCab();
				imp.setTitulo( "Relat�rio de Produtos" );

				while ( rs.next() ) {

					if ( imp.pRow() >= linPag ) {
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );
						imp.incPags();
						imp.eject();
					}

					if ( imp.pRow() == 0 ) {
						imp.impCab( 136, true );
						imp.say( 0, imp.comprimido() );
						imp.say( 0, "|" );
						imp.say( 135, "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" );
						imp.say( 3, "C�digo:" );
						imp.say( 12, "|" );
						imp.say( 13, "Descri��o:" );
						imp.say( 70, "|" );
						imp.say( 72, "Unidade:" );
						imp.say( 95, "|" );
						imp.say( 97, "Saldo:" );
						imp.say( 117, "|" );
						imp.say( 120, "Pre�o Base:" );
						imp.say( 135, "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );

					}

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 4, rs.getString( "CodProd" ) );
					imp.say( 12, "|" );
					imp.say( 13, rs.getString( "DescProd" ) != null ? rs.getString( "Descprod" ).substring( 0, 50 ) : "" );
					imp.say( 70, "|" );
					imp.say( 72, rs.getString( "codunid" ) );
					imp.say( 95, "|" );
					imp.say( 97, rs.getString( "sldliqprod" ) );
					imp.say( 117, "|" );
					imp.say( 120, rs.getString( "Precobaseprod" ) );
					imp.say( 135, "|" );

					And.atualiza( iContaReg++ );

				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );

				imp.eject();
				imp.fechaGravacao();

				rs.close();
				ps.close();

				con.commit();

				dl.dispose();
				And.dispose();

			} catch ( SQLException err ) {
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro consulta tabela de produtos!" + err.getMessage() );
			}
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}

	}

	public void setCodBar() {

		EANFactory ean = new EANFactory();
		String codbarras = null;

		if ( sPrefs[ eprefs.CODPAISEMP.ordinal() ] == null && sPrefs[ eprefs.CODEANEMP.ordinal() ] == null ) {
			Funcoes.mensagemInforma( this, "Ajuste o cadastro da empresa.\nCodifica��o EAN requerida!" );
		}

		if ( sPrefs[ eprefs.TIPOCODBAR.ordinal() ].equals( "1" ) ) {

			codbarras = ean.novoEAN13( sPrefs[ eprefs.CODPAISEMP.ordinal() ], sPrefs[ eprefs.CODEANEMP.ordinal() ], txtCodProd.getVlrInteger().toString() );
			txtCodBarProd.setVlrString( codbarras );

		}
		else if ( sPrefs[ eprefs.TIPOCODBAR.ordinal() ].equals( "2" ) ) {

			codbarras = txtCodProd.getVlrString();
			txtCodBarProd.setVlrString( codbarras );
		}
	}

	public void exec( int iCodProduto ) {

		txtCodProd.setVlrInteger( new Integer( iCodProduto ) );
		lcCampos.carregaDados();
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		else if ( evt.getSource() == btDuplicar ) {
			duplicar();
		}
		if ( evt.getSource() == btCodBar ) {
			setCodBar();
		}

		super.actionPerformed( evt );
	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( cevt.getSource() == tpn ) {
			if ( tpn.getSelectedIndex() == 0 ) {
				txtCodProd.requestFocus();
			}
			else if ( tpn.getSelectedIndex() == 1 ) {
				txtUnidFat.requestFocus();
			}
			else if ( tpn.getSelectedIndex() == 2 ) {
				txtCodFor.requestFocus();
			}
			else if ( tpn.getSelectedIndex() == 3 ) {
				txtCodLote.requestFocus();
			}
			else if ( tpn.getSelectedIndex() == 4 ) {
				txtCodFotoProd.requestFocus();
			}
			else if ( tpn.getSelectedIndex() == 5 ) {
				txtCodPrecoProd.requestFocus();
			}
		}
	}

	public void edit( EditEvent eevt ) {

	}

	public void valorAlterado( CheckBoxEvent cbevt ) {

		if ( cbLote.getStatus() ) {
			txtCodLote.setEditable( true );
			txtDiniLote.setEditable( true );
			txtVenctoLote.setEditable( true );
			lcLote.setReadOnly( false );
		}
		else {
			txtCodLote.setEditable( false );
			txtDiniLote.setEditable( false );
			txtVenctoLote.setEditable( false );
			lcLote.setReadOnly( true );
		}
	}

	public void valorAlterado( RadioGroupEvent rgevt ) {

		if ( rgevt.getSource() == rgPA ) {
			if ( rgPA.getVlrString().equals( "RMA" ) ) {
				txtAnoCCPA.setAtivo( true );
				txtCodCCPA.setAtivo( true );
			}
			else {
				txtAnoCCPA.setAtivo( false );
				txtCodCCPA.setAtivo( false );
			}

			if ( rgPA.getVlrString().equals( "PDV" ) ) {
				txtCodCaixa.setAtivo( true );
			}
			else {
				txtCodCaixa.setAtivo( false );
			}
		}
		else if ( rgevt.getSource() == rgTipoConv ) {

			if ( "P".equals( rgTipoConv.getVlrString() ) ) {

				lbFatConv.setVisible( false );
				txtFatConv.setVisible( false );

				lbCodProdEst.setVisible( true );
				txtCodProdEst.setVisible( true );
				lbSeqEst.setVisible( true );
				txtSeqEst.setVisible( true );
				lbDescProdEst.setVisible( true );
				txtDescProdEst.setVisible( true );

				txtFatConv.setVlrBigDecimal( new BigDecimal( 1 ) );

				txtFatConv.setRequerido( false );
				txtCodProdEst.setRequerido( true );
				txtSeqEst.setRequerido( true );

			}
			else if ( "U".equals( rgTipoConv.getVlrString() ) ) {

				lbFatConv.setVisible( true );
				txtFatConv.setVisible( true );

				lbCodProdEst.setVisible( false );
				txtCodProdEst.setVisible( false );
				lbSeqEst.setVisible( false );
				txtSeqEst.setVisible( false );
				lbDescProdEst.setVisible( false );
				txtDescProdEst.setVisible( false );

				txtFatConv.setRequerido( true );
				txtCodProdEst.setRequerido( false );
				txtSeqEst.setRequerido( false );

			}
		}

	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

		try {
			if ( cevt.getListaCampos() == lcCampos ) {

				buscaEstoque();
				CustosProd custos = new CustosProd( txtCodAlmox.getVlrInteger(), txtCodProd.getVlrInteger(), con );

				txtCustoPEPSProd.setVlrBigDecimal( custos.getCustoPEPSProd() );
				txtCustoMPMProd.setVlrBigDecimal( custos.getCustoMPMProd() );
				txtCustoPEPSAlmox.setVlrBigDecimal( custos.getCustoPEPSAlmox() );
				txtCustoMPMAlmox.setVlrBigDecimal( custos.getCustoMPMAlmox() );

				// Oculta aba Lote, caso produto n�o o utilize
				if ( "S".equals( cbLote.getVlrString() ) ) {
					adicTab( "Lotes", pnLote );
				}
				else {
					removeTab( "Lotes", pnLote );
				}
				// Oculta aba Serie, caso produto n�o o utilize
				if ( "S".equals( cbSerie.getVlrString() ) ) {
					adicTab( "Series", pnSerie );
				}
				else {
					removeTab( "Series", pnSerie );
				}

			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void beforeEdit( EditEvent eevt ) {

	}

	public void afterEdit( EditEvent eevt ) {

		if ( imFotoProd.foiAlterado() ) {
			txtLargFotoProd.setVlrString( String.valueOf( imFotoProd.getLargura() ) );
			txtAltFotoProd.setVlrString( String.valueOf( imFotoProd.getAltura() ) );
		}
	}

	public void beforeInsert( InsertEvent eevt ) {

	}

	public void afterInsert( InsertEvent ievt ) {

		if ( ievt.getListaCampos() == lcFoto && imFotoProd.foiAlterado() ) {
			txtLargFotoProd.setVlrString( "" + imFotoProd.getLargura() );
			txtAltFotoProd.setVlrString( "" + imFotoProd.getAltura() );
		}
		else if ( ievt.getListaCampos() == lcCampos ) {
			carregaMoeda();
			cbAtivo.setVlrString( "S" );
			txtRefProd.setVlrString( txtCodProd.getVlrString() );
			txtCodBarProd.setVlrString( txtCodProd.getVlrString() );
			txtCodFabProd.setVlrString( txtCodProd.getVlrString() );
			txtPesoBrutProd.setVlrDouble( new Double( 0.0 ) );
			txtPesoLiqProd.setVlrDouble( new Double( 0.0 ) );
		}
		else if ( ievt.getListaCampos() == lcProdAcesso ) {
			habAcesso( true, 0 );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		sPrefs = getPrefs();

		montaTela();

		lcLote.setConexao( cn );
		lcSerie.setConexao( cn );
		lcMoeda.setConexao( cn );
		lcUnid.setConexao( cn );
		lcFisc.setConexao( cn );
		lcMarca.setConexao( cn );
		lcSecao.setConexao( cn );
		lcGrup.setConexao( cn );
		lcAlmox.setConexao( cn );
		lcPrazoEnt.setConexao( cn );
		lcUnidFat.setConexao( cn );
		lcPlan.setConexao( cn );
		lcCC.setConexao( cn );
		lcCCAcesso.setConexao( cn );
		lcCaixa.setConexao( cn );
		lcForFK.setConexao( cn );
		lcFatConv.setConexao( cn );
		lcProdPlan.setConexao( cn );
		lcFor.setConexao( cn );
		lcFoto.setConexao( cn );
		lcPreco.setConexao( cn );
		lcClasCliPreco.setConexao( cn );
		lcTabPreco.setConexao( cn );
		lcPlanoPagPreco.setConexao( cn );
		lcCodAltProd.setConexao( cn );
		lcProdAcesso.setConexao( cn );
		lcProdEstCod.setConexao( cn );
	}

	public void beforePost( PostEvent pevt ) {

		if ( pevt.getListaCampos() == lcCampos ) {
			BigDecimal qtdemb = txtQtdEmbalagem.getVlrBigDecimal();
			if ( ( qtdemb == null ) || ( qtdemb.compareTo( new BigDecimal( 0 ) ) < 1 ) ) {
				txtQtdEmbalagem.setVlrInteger( 1 );
			}
		}
		if ( pevt.getListaCampos() == lcFatConv ) {
			if ( "P".equals( rgTipoConv.getVlrString() ) ) {
				if ( !validaEstrutura() ) {
					Funcoes.mensagemInforma( this, "A estrutura selecionada n�o contem esse produto!\nSelecione outra estrutura para convers�o." );
				}
			}
		}
	}

	private boolean validaEstrutura() {

		boolean ret = false;
		StringBuilder sql = new StringBuilder();

		try {

			sql.append( "select count(*) from ppitestrutura ie " );
			sql.append( "where ie.codemp=? and ie.codfilial=? and ie.codprod=? and ie.seqest=? " );
			sql.append( "and ie.codemppd=? and ie.codfilialpd=? and ie.codprodpd=? " );

			PreparedStatement ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, lcProdEstCod.getCodEmp() );
			ps.setInt( 2, lcProdEstCod.getCodFilial() );
			ps.setInt( 3, txtCodProdEst.getVlrInteger() );
			ps.setInt( 4, txtSeqEst.getVlrInteger() );
			ps.setInt( 5, lcCampos.getCodEmp() );
			ps.setInt( 6, lcCampos.getCodFilial() );
			ps.setInt( 7, txtCodProd.getVlrInteger() );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				ret = ( rs.getInt( 1 ) > 0 );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return ret;
	}

}
