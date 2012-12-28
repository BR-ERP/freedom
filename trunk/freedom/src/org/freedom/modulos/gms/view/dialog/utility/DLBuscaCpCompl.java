/**
 * @version 14/07/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gms <BR>
 *         Classe:
 * @(#)DLAdicPedCompra.java <BR>
 * 
 *                          Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                          modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                          na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                          Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                          sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                          Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                          Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                          de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                          Dialog para busca e gera��o de pedido de compra com base em outros pedidos de compra.
 */

package org.freedom.modulos.gms.view.dialog.utility;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.gms.business.object.PrefereGMS;
import org.freedom.modulos.gms.view.frame.crud.detail.FCompra;

public class DLBuscaCpCompl extends FDialogo implements ActionListener, RadioGroupListener, CarregaListener, FocusListener, JComboBoxListener {

	private static final long serialVersionUID = 1L;

	private JTablePad tabitcompra = new JTablePad();

	private JScrollPane spntabitcompra = new JScrollPane( tabitcompra );

	private JTablePad tabcompra = new JTablePad();

	private JScrollPane spntabcompra = new JScrollPane( tabcompra );

	private JPanelPad pinCab = new JPanelPad( 0, 105 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnSubRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinBtSelCp = new JPanelPad( 40, 110 );

	private JPanelPad pinRod = new JPanelPad( 480, 55 );

	private JPanelPad pinSair = new JPanelPad( 120, 45 );

	private JPanelPad pinBtSel = new JPanelPad( 40, 110 );

	private JPanelPad pnFor = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnTabCompra = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnForTab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldPad txtDocCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtDtEntCompra = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDtEmitCompra = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldFK txtStatusCompra = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtVlrProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrDesc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtVlrProdCompra = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtVlrLiqCompra = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtVlrLiq = new JTextFieldFK( JTextFieldFK.TP_DECIMAL, 15, 2 );
	
	private JTextFieldFK txtVlrICMSCompra = new JTextFieldFK( JTextFieldFK.TP_DECIMAL, 15, 2 );
	
	private JTextFieldFK txtVlrIPICompra = new JTextFieldFK( JTextFieldFK.TP_DECIMAL, 15, 2 );
	
	private JTextFieldFK txtVlrPISCompra = new JTextFieldFK( JTextFieldFK.TP_DECIMAL, 15, 2 );
	
	private JTextFieldFK txtVlrCOFINSCompra = new JTextFieldFK( JTextFieldFK.TP_DECIMAL, 15, 2 );
	
	private JTextFieldFK txtVlrIICOMPRA = new JTextFieldFK( JTextFieldFK.TP_DECIMAL, 15, 2 );
	
	private JTextFieldPad txtDataIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDataFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodImp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	//cp.vlricmscompra, cp.vlripicompra, cp.vlrcofinscompra, cp.vlriicompra
	
	private JTextFieldFK txtNroDI = new JTextFieldFK( JTextFieldFK.TP_STRING, 10, 0 );

	private JTextFieldFK txtDtRegDI = new JTextFieldFK( JTextFieldFK.TP_DATE, 10, 0 );

	private JTextFieldFK txtDtDesembDI = new JTextFieldFK( JTextFieldFK.TP_DATE, 10, 0 );

	private JTextFieldFK txtIdentContainer = new JTextFieldFK( JTextFieldFK.TP_STRING, 20, 0 );

	private JButtonPad btBuscar = new JButtonPad( "Buscar", Icone.novo( "btPesquisa.png" ) );

	private JButtonPad btTodosItCompra = new JButtonPad( Icone.novo( "btTudo.png" ) );

	private JButtonPad btNenhumItCompra = new JButtonPad( Icone.novo( "btNada.png" ) );

	private JButtonPad btGerar = new JButtonPad( Icone.novo( "btGerar.png" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.png" ) );
	
	private JButtonPad btExec = new JButtonPad( Icone.novo( "btExecuta.png" ) );

	private ListaCampos lcFor = new ListaCampos( this, "FR" );
	
	private ListaCampos lcCompra = new ListaCampos( this, "CP" );

	private Vector<Object> vValidos = new Vector<Object>();

	private org.freedom.modulos.gms.view.frame.crud.detail.FCompra telacompra = null;

	private PrefereGMS preferegms = PrefereGMS.getInstance();
	
	private Vector<String> vValsTipo = new Vector<String>();

	private Vector<String> vLabsTipo = new Vector<String>();

	private JComboBoxPad cbTipo = null;


	public static enum enum_compra {
		SEL, CODCOMPRA, CODPLANOPAG, CODEMPFR, CODFILIALFR, CODFOR, RAZFOR, NROITENS, VLRLIQCOMPRA
	}

	public static enum enum_itcompra {
		SEL, CODITCOMPRA, CODEMPPD, CODFILIALPD, CODPROD, DESCPROD, QTDITCOMPRA, PRECOITCOMPRA, VLRDESCITCOMPRA, VLRLIQITCOMPRA, TPAGRUP, AGRUP, VLRAGRUP, CODCOMPRA, CODLOTE, APROVPRECO, CODEMPFR, CODFILIALFR, CODFOR, DTENTCOMPRA
	}

	public DLBuscaCpCompl( Object cp ) {

		super();
		
		setTitulo( "Gera nota complementar de compra" );

		telacompra = (FCompra) cp;

		setAtribos( 750, 520 );
		
		montaComboBox();

		montaTela();

		habilitaCampos();

		montaListaCampos();

		montaTabelas();

		adicListeners();

		adicToolTips();

	}
	
	private void montaComboBox(){
		
		vLabsTipo.add( "Importa��o" );
		vLabsTipo.add( "Compra" );
		vValsTipo.add( "IMP" );
		vValsTipo.add( "CP" );
		
		cbTipo = new JComboBoxPad( vLabsTipo, vValsTipo, JTextFieldPad.TP_STRING, 1, 0 );
		cbTipo.setVlrString( "IMP");
	}
	

	private void habilitaCampos() {

		//txtCodFor.setAtivo( false);
		txtVlrProd.setAtivo( false );
		txtVlrDesc.setAtivo( false );
		txtVlrLiq.setAtivo( false );
		txtDataIni.setAtivo( false );
		txtDataFim.setAtivo( false );
		
		btTodosItCompra.setEnabled( false );
		btNenhumItCompra.setEnabled( false );
		btGerar.setEnabled( false );

	}

	private void montaTela() {

		c.setLayout( new BorderLayout() );
		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pnFor, BorderLayout.CENTER );
		c.add( pinCab, BorderLayout.NORTH );

		pinCab.adic( txtCodCompra, 7, 25, 60, 20, "Compra" );

		pinCab.adic( txtCodFor, 70, 25, 50, 20, "C�d.For." );

		pinCab.adic( txtRazFor, 123, 25, 250, 20, "Raz�o social do fornecedor" );
		
		
		pinCab.adic( txtDataIni, 376, 25, 70, 20, "Data Inicial" );

		pinCab.adic( txtDataFim, 449, 25, 70, 20, "Data Final" );

		pinCab.adic( cbTipo, 426, 65, 157, 20, "tipo de nota" );
		
		pinCab.adic( txtNroDI, 7, 65, 85, 20,  "Nro. da DI" );
		
		pinCab.adic( txtDtRegDI, 95, 65, 70, 20, "Dt.reg. DI" );
		
		pinCab.adic( txtDtDesembDI, 168, 65, 70, 20, "Dt.desemb." );
		
		pinCab.adic( txtIdentContainer, 241, 65, 180, 20, "Identifica��o do container" );
		
		pinCab.adic( btBuscar, 632, 20, 100, 30 );

		pnRod.setPreferredSize( new Dimension( 600, 50 ) );

		pnSubRod.setPreferredSize( new Dimension( 600, 50 ) );
		pnRod.add( pnSubRod, BorderLayout.SOUTH );

		pinSair.tiraBorda();
		pinSair.adic( btSair, 10, 10, 100, 30 );
		btSair.setPreferredSize( new Dimension( 120, 30 ) );

		pnSubRod.add( pinSair, BorderLayout.EAST );
		pnSubRod.add( pinRod, BorderLayout.CENTER );

		pinRod.tiraBorda();
	/*	pinRod.adic( new JLabelPad( "Vlr.bruto" ), 7, 0, 100, 20 );
		pinRod.adic( txtVlrProd, 7, 20, 100, 20 );
		pinRod.adic( new JLabelPad( "Vlr.desc." ), 110, 0, 97, 20 );
		pinRod.adic( txtVlrDesc, 110, 20, 97, 20 );
		pinRod.adic( new JLabelPad( "Vlr.liq." ), 210, 0, 97, 20 );
		pinRod.adic( txtVlrLiq, 210, 20, 97, 20 );*/
		
		pinRod.adic( txtVlrProdCompra, 7, 20, 80, 20, "Vlr.Prod." );
		pinRod.adic( txtVlrLiqCompra, 90, 20, 80, 20, "Vlr.Liq." );
		pinRod.adic( txtVlrICMSCompra, 173, 20, 80, 20, "Vl.ICMS" );
		pinRod.adic( txtVlrIPICompra, 256, 20, 80, 20, "Vl.IPI" );
		pinRod.adic( txtVlrPISCompra, 339, 20, 80, 20, "Vl.PIS" );
		pinRod.adic( txtVlrCOFINSCompra, 422, 20, 80, 20, "Vl.COFINS" );
		pinRod.adic( txtVlrIICOMPRA, 505, 20, 80, 20, "Vl.II" );
		
		pnTabCompra.setPreferredSize( new Dimension( 600, 80 ) );

		pnTabCompra.add( spntabcompra, BorderLayout.CENTER );
		pnTabCompra.add( pinBtSelCp, BorderLayout.EAST );
		
		pinBtSelCp.adic( btExec, 3, 3, 30, 30 );
		
		pnForTab.add( spntabitcompra, BorderLayout.CENTER );
		pnForTab.add( pinBtSel, BorderLayout.EAST );

		pinBtSel.adic( btTodosItCompra, 3, 3, 30, 30 );
		pinBtSel.adic( btNenhumItCompra, 3, 34, 30, 30 );
		pinBtSel.adic( btGerar, 3, 65, 30, 30 );
		
		pnFor.add( pnTabCompra, BorderLayout.NORTH );
		pnFor.add( pnForTab, BorderLayout.CENTER );
	
		txtDataIni.setVlrDate( Funcoes.getDataIniMes( Funcoes.getMes( new Date() ), Funcoes.getAno( new Date() ) - 1 ) );
		txtDataFim.setVlrDate( Funcoes.getDataFimMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );

	}

	private void adicToolTips() {

		btTodosItCompra.setToolTipText( "Selecionar tudo" );
		btNenhumItCompra.setToolTipText( "Limpar sele��o" );
		btGerar.setToolTipText( "Gerar compra" );
		btExec.setToolTipText( "Gerar itens da compra" );

	}

	private void adicListeners() {

		tabitcompra.addKeyListener( this );
		tabcompra.addKeyListener( this );

		btBuscar.addKeyListener( this );
		btGerar.addKeyListener( this );

		txtCodCompra.addActionListener( this );
		btSair.addActionListener( this );
		btBuscar.addActionListener( this );
		btGerar.addActionListener( this );
		btExec.addActionListener( this );
		btTodosItCompra.addActionListener( this );
		btNenhumItCompra.addActionListener( this );

		lcCompra.addCarregaListener( this );
		lcFor.addCarregaListener( this );
		txtCodCompra.addFocusListener( this );
		
		
		cbTipo.addComboBoxListener( this );

		addWindowListener( this );

	}

	private void montaTabelas() {

		// Monta as tabelas
		
		tabcompra.adicColuna( "S/N" );
		tabcompra.adicColuna( "C�d.Cp." );
		tabcompra.adicColuna( "Cod.Plan.Pg." );
		tabcompra.adicColuna( "" );
		tabcompra.adicColuna( "" );
		tabcompra.adicColuna( "C�d.For." );
		tabcompra.adicColuna( "Raz�o do fornecedor" );
		tabcompra.adicColuna( "N� itens." );
		tabcompra.adicColuna( "Valor total" );

		tabcompra.setTamColuna( 25, enum_compra.SEL.ordinal() );
		tabcompra.setTamColuna( 60, enum_compra.CODCOMPRA.ordinal() );
		
		tabcompra.setColunaInvisivel( enum_compra.CODEMPFR.ordinal() );
		tabcompra.setColunaInvisivel( enum_compra.CODFILIALFR.ordinal() );
		
		tabcompra.setTamColuna( 60, enum_compra.CODFOR.ordinal() );
		tabcompra.setTamColuna( 280, enum_compra.RAZFOR.ordinal() );
		tabcompra.setTamColuna( 60, enum_compra.NROITENS.ordinal() );
		tabcompra.setTamColuna( 100, enum_compra.VLRLIQCOMPRA.ordinal() );

		tabcompra.setColunaInvisivel( enum_compra.CODPLANOPAG.ordinal() );
		
		tabcompra.setColunaEditavel( enum_compra.SEL.ordinal(), true );

		tabitcompra.adicColuna( "S/N" );
		tabitcompra.adicColuna( "�tem" );
		tabitcompra.adicColuna( "" );
		tabitcompra.adicColuna( "" );
		tabitcompra.adicColuna( "C�d.Pd." );
		tabitcompra.adicColuna( "Descri��o do produto" );
		tabitcompra.adicColuna( "Qtd." );
		tabitcompra.adicColuna( "Pre�o" );
		tabitcompra.adicColuna( "Vlr.desc." );
		tabitcompra.adicColuna( "Vlr.liq." );
		tabitcompra.adicColuna( "Tp.Agr." );
		tabitcompra.adicColuna( "Agr." );
		tabitcompra.adicColuna( "Vlr.Agr." );
		tabitcompra.adicColuna( "Compra" );
		tabitcompra.adicColuna( "Lote" );
		tabitcompra.adicColuna( "" );
		tabitcompra.adicColuna( "" );
		tabitcompra.adicColuna( "" );
		tabitcompra.adicColuna( "" );
		tabitcompra.adicColuna( "" ); // Data de entrada

		tabitcompra.setTamColuna( 25, enum_itcompra.SEL.ordinal() );
		tabitcompra.setTamColuna( 30, enum_itcompra.CODITCOMPRA.ordinal() );
		tabitcompra.setColunaInvisivel( enum_itcompra.CODEMPPD.ordinal() );
		tabitcompra.setColunaInvisivel( enum_itcompra.CODFILIALPD.ordinal() );
		tabitcompra.setTamColuna( 50, enum_itcompra.CODPROD.ordinal() );
		tabitcompra.setTamColuna( 190, enum_itcompra.DESCPROD.ordinal() );
		tabitcompra.setTamColuna( 40, enum_itcompra.QTDITCOMPRA.ordinal() );
		tabitcompra.setTamColuna( 60, enum_itcompra.PRECOITCOMPRA.ordinal() );
		tabitcompra.setTamColuna( 60, enum_itcompra.VLRDESCITCOMPRA.ordinal() );
		tabitcompra.setTamColuna( 60, enum_itcompra.VLRLIQITCOMPRA.ordinal() );
		tabitcompra.setColunaInvisivel( enum_itcompra.TPAGRUP.ordinal() );
		tabitcompra.setColunaInvisivel( enum_itcompra.AGRUP.ordinal() );
		tabitcompra.setTamColuna( 60, enum_itcompra.VLRAGRUP.ordinal() );
		tabitcompra.setTamColuna( 40, enum_itcompra.CODCOMPRA.ordinal() );
		tabitcompra.setTamColuna( 60, enum_itcompra.CODLOTE.ordinal() );
		tabitcompra.setColunaInvisivel( enum_itcompra.CODEMPFR.ordinal() );
		tabitcompra.setColunaInvisivel( enum_itcompra.CODFILIALFR.ordinal() );
		tabitcompra.setColunaInvisivel( enum_itcompra.CODFOR.ordinal() );
		tabitcompra.setColunaInvisivel( enum_itcompra.DTENTCOMPRA.ordinal() );
		
		

	}

	private void montaListaCampos() {

		// Lista campos do pedido de compra

		lcCompra.add( new GuardaCampo( txtCodCompra, "CodCompra", "C�d.Comp.", ListaCampos.DB_PK, null, false ) );
		lcCompra.add( new GuardaCampo( txtDocCompra, "DocCompra", "Doc.", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtSerie, "Serie", "Serie", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtDtEntCompra, "DtEntCompra", "Dt.entrada", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtDtEmitCompra, "DtEmitCompra", "Dt.emiss�o", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtCodFor, "CodFor", "Cod.Forn.", ListaCampos.DB_FK, txtRazFor, false ) );
		lcCompra.add( new GuardaCampo( txtVlrProdCompra, "VlrProdCompra", "Vlr.Prod.", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtVlrLiqCompra, "VlrLiqCompra", "Vlr.Liq.", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtStatusCompra, "StatusCompra", "Status", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtNroDI, "NroDI", "N�mero DI", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtDtRegDI, "DtRegDI", "Dt.reg. DI", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtDtDesembDI, "DtDesembDI", "Dt.desemb.", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtIdentContainer, "IdentContainer", "Identifica��o do container", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtVlrICMSCompra, "VlrICMSCompra", "Valor.ICMS", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtVlrIPICompra, "VlrIPICompra", "Valor IPI", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtVlrPISCompra, "VlrPISCompra", "Valor PIS", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtVlrCOFINSCompra, "VlrCOFINSCompra", "Valor Cofins", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtVlrIICOMPRA, "VlrIICOMPRA", "Valor II", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtCodImp, "CodImp", "C�d.Imp", ListaCampos.DB_SI, null, false ) );
		
		
		txtCodCompra.setTabelaExterna( lcCompra, null );
		txtCodCompra.setNomeCampo( "CodCompra" );
		txtCodCompra.setFK( true );

		lcCompra.setQueryCommit( false );
		lcCompra.setReadOnly( true );

		txtCodCompra.setListaCampos( lcCompra );
		lcCompra.montaSql( false, "COMPRA", "CP" );

		// Lista campos do fornecedor
		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.Forn.", ListaCampos.DB_PK, txtRazFor, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		txtCodFor.setTabelaExterna( lcFor, null );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		lcFor.setReadOnly( true );
		lcFor.montaSql( false, "FORNECED", "CP" );
		
	}

	private void buscaItCompra() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codcompra = null;

		BigDecimal vlrprod = new BigDecimal( 0 );
		BigDecimal vlrdesc = new BigDecimal( 0 );
		BigDecimal vlrliq = new BigDecimal( 0 );

		try {

			tabitcompra.limpa();
			vValidos.clear();

			sql.append( "select " );
			sql.append( "ic.coditcompra, ic.codprod, pd.descprod, ic.qtditcompra, ic.precoitcompra, ic.vlrdescitcompra, " );
			sql.append( "ic.vlrliqitcompra, ic.codcompra, cp.codplanopag, ic.codlote, coalesce(ic.aprovpreco,'N') aprovpreco, " );
			sql.append( "cp.codempfr, cp.codfilialfr, cp.codfor, ic.codemppd, ic.codfilialpd, ic.codprod, cp.dtentcompra " );
			
			sql.append( "from cpcompra cp, cpitcompra ic, eqproduto pd " );

			sql.append( "where ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra " );
			sql.append( "and pd.codemp=ic.codemppd and pd.codfilial=ic.codfilialpd and pd.codprod=ic.codprod " );
			sql.append( "and cp.codemp=? and cp.codfilial=? and ic.codcompra=? " );

			sql.append( "order by ic.codprod " );

			for ( int i = 0; i < tabcompra.getNumLinhas(); i++ ) {

				if ( ! ( (Boolean) tabcompra.getValor( i, enum_compra.SEL.ordinal() ) ).booleanValue() ) {
					continue;
				}

				codcompra = (Integer) tabcompra.getValor( i, enum_compra.CODCOMPRA.ordinal() );

				ps = con.prepareStatement( sql.toString() );

				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
				ps.setInt( 3, codcompra );

				rs = ps.executeQuery();

				int irow = tabitcompra.getNumLinhas();
				
				while ( rs.next() ) {

					tabitcompra.adicLinha();

					
					tabitcompra.setValor( new Boolean( true ), irow, enum_itcompra.SEL.ordinal() );	
					tabitcompra.setValor( rs.getInt( enum_itcompra.CODITCOMPRA.toString() ), irow, enum_itcompra.CODITCOMPRA.ordinal() );
					tabitcompra.setValor( rs.getInt( enum_itcompra.CODEMPPD.toString() ), irow, enum_itcompra.CODEMPPD.ordinal() );
					tabitcompra.setValor( rs.getInt( enum_itcompra.CODFILIALPD.toString() ), irow, enum_itcompra.CODFILIALPD.ordinal() );
					tabitcompra.setValor( rs.getInt( enum_itcompra.CODPROD.toString() ), irow, enum_itcompra.CODPROD.ordinal() );
					tabitcompra.setValor( rs.getString( enum_itcompra.DESCPROD.toString() ), irow, enum_itcompra.DESCPROD.ordinal() );
 
					tabitcompra.setValor( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDec, rs.getString( enum_itcompra.QTDITCOMPRA.toString() ) != null ? rs.getString( enum_itcompra.QTDITCOMPRA.toString() ) : "0" ), irow, enum_itcompra.QTDITCOMPRA.ordinal() );
					tabitcompra.setValor( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDecPre, rs.getString( enum_itcompra.PRECOITCOMPRA.toString() ) != null ? rs.getString( enum_itcompra.PRECOITCOMPRA.toString() ) : "0" ), irow, enum_itcompra.PRECOITCOMPRA.ordinal() );
					tabitcompra.setValor( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDecFin, rs.getString( enum_itcompra.VLRDESCITCOMPRA.toString() ) != null ? rs.getString( enum_itcompra.VLRDESCITCOMPRA.toString() ) : "0" ), irow, enum_itcompra.VLRDESCITCOMPRA.ordinal() );
					tabitcompra.setValor( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDecFin, rs.getString( enum_itcompra.VLRLIQITCOMPRA.toString() ) != null ? rs.getString( enum_itcompra.VLRLIQITCOMPRA.toString() ) : "0" ), irow, enum_itcompra.VLRLIQITCOMPRA.ordinal() );

					tabitcompra.setValor( "", irow, enum_itcompra.TPAGRUP.ordinal() );
					tabitcompra.setValor( "", irow, enum_itcompra.AGRUP.ordinal() );
					tabitcompra.setValor( "0,00", irow, enum_itcompra.VLRAGRUP.ordinal() );

					tabitcompra.setValor( rs.getInt( enum_itcompra.CODCOMPRA.toString() ), irow, enum_itcompra.CODCOMPRA.ordinal() );
					tabitcompra.setValor( rs.getString( enum_itcompra.CODLOTE.toString() ) == null ? "" : rs.getString( enum_itcompra.CODLOTE.toString() ), irow, enum_itcompra.CODLOTE.ordinal() );
					tabitcompra.setValor( rs.getInt( enum_itcompra.CODEMPFR.toString() ), irow, enum_itcompra.CODEMPFR.ordinal() );
					tabitcompra.setValor( rs.getInt( enum_itcompra.CODFILIALFR.toString() ), irow, enum_itcompra.CODFILIALFR.ordinal() );
					tabitcompra.setValor( rs.getInt( enum_itcompra.CODFOR.toString() ), irow, enum_itcompra.CODFOR.ordinal() );
					tabitcompra.setValor( Funcoes.dateToStrDate( Funcoes.sqlDateToDate( rs.getDate( enum_itcompra.DTENTCOMPRA.toString() ))), irow, enum_itcompra.DTENTCOMPRA.ordinal() );
 
					vlrprod.add( rs.getBigDecimal( enum_itcompra.PRECOITCOMPRA.toString() ) );
					vlrdesc.add( rs.getBigDecimal( enum_itcompra.VLRDESCITCOMPRA.toString() ) );
					vlrliq.add( rs.getBigDecimal( enum_itcompra.VLRLIQITCOMPRA.toString() ) );

					vValidos.addElement( new Integer[] { codcompra, rs.getInt( enum_itcompra.CODITCOMPRA.toString() ) } );

					irow++;

				}

				con.commit();

			}

			txtVlrProd.setVlrBigDecimal( vlrprod );
			txtVlrDesc.setVlrBigDecimal( vlrdesc );
			txtVlrLiq.setVlrBigDecimal( vlrliq );

		}

		catch ( Exception e ) {
			e.printStackTrace();
		}

	}
	
	private void buscaCompra() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		Vector<Object> vVals = null;

		try {

			if ( txtCodCompra.getVlrInteger() > 0  || txtCodFor.getVlrInteger() > 0 ) {

				sql.append( "select cp.statuscompra, cp.codcompra, cp.codplanopag, cp.codfor, fr.razfor, " );
				sql.append( "(select count(*) from cpitcompra ic where ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra) nroitens , " );
				sql.append( "cp.vlrliqcompra " );
				sql.append( "from cpcompra cp, cpforneced fr " );
				sql.append( "where " );
				sql.append( "fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor " );
				sql.append( "and cp.statuscompra = 'C3' and cp.impnotacompra='S' " );
				sql.append( "and cp.codemp=? and cp.codfilial=? " );

				if ( txtCodCompra.getVlrInteger() > 0 ) {
					sql.append( " and cp.codcompra=? " );
				}
				
				if ( txtCodFor.getVlrInteger() > 0 && txtCodCompra.getVlrInteger() <= 0  ) { 
					sql.append( "and cp.codempfr=? and cp.codfilialfr=? and cp.codfor=? " );
					sql.append( "and cp.dtemitcompra between ? and ? ");
					
				}
				
				ps = con.prepareStatement( sql.toString() ); 

				int param = 1;
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "CPCOMPRA" ) );

				if ( txtCodCompra.getVlrInteger() > 0 ) {
					ps.setInt( param++, txtCodCompra.getVlrInteger() );
				}
				
				if ( txtCodFor.getVlrInteger() > 0  && txtCodCompra.getVlrInteger() <= 0 ) {  
					ps.setInt( param++, lcFor.getCodEmp() );
					ps.setInt( param++, lcFor.getCodFilial() );
					ps.setInt( param++, txtCodFor.getVlrInteger() );
					ps.setDate( param++, Funcoes.dateToSQLDate( txtDataIni.getVlrDate() ) );
					ps.setDate( param++, Funcoes.dateToSQLDate( txtDataFim.getVlrDate() ) );
				}

				rs = ps.executeQuery();

				tabcompra.limpa();

				int irow = 0;
				boolean sel = true;

				while ( rs.next() ) {

					tabcompra.adicLinha();

					tabcompra.setValor( new Boolean( sel ), irow, enum_compra.SEL.ordinal() );
					tabcompra.setValor( rs.getInt( enum_compra.CODCOMPRA.toString() ), irow, enum_compra.CODCOMPRA.ordinal() );
					tabcompra.setValor( rs.getInt( enum_compra.CODPLANOPAG.toString() ), irow, enum_compra.CODPLANOPAG.ordinal() );
					tabcompra.setValor( rs.getInt( enum_compra.CODFOR.toString() ), irow, enum_compra.CODFOR.ordinal() );
					tabcompra.setValor( rs.getString( enum_compra.RAZFOR.toString() ), irow, enum_compra.RAZFOR.ordinal() );
					tabcompra.setValor( Funcoes.strDecimalToStrCurrencyd( 0, rs.getString( enum_compra.NROITENS.toString() ) ), irow, enum_compra.NROITENS.ordinal() );
					tabcompra.setValor( Funcoes.strDecimalToStrCurrencyd( 2, rs.getString( enum_compra.VLRLIQCOMPRA.toString() ) ), irow, enum_compra.VLRLIQCOMPRA.ordinal() );
					
					irow++;
					sel = false;
				}

				rs.close();
				ps.close();
			}
			else {
				Funcoes.mensagemInforma( this, "Selecione uma compra para busca!" );
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar compras!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			vVals = null;
		}

	}

	private void limpaNaoSelecionados( JTablePad ltab ) {

		int linhas = ltab.getNumLinhas();
		int pos = 0;
		try {
			for ( int i = 0; i < linhas; i++ ) {
				if ( ! ( (Boolean) ltab.getValor( i, 0 ) ).booleanValue() ) {
					ltab.tiraLinha( i );
					vValidos.remove( i );
					i--;
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}


	private void carregaTudo( JTablePad tb ) {

		for ( int i = 0; i < tb.getNumLinhas(); i++ ) {
			tb.setValor( new Boolean( true ), i, 0 );
		}
	}

	private void carregaNada( JTablePad tb ) {

		for ( int i = 0; i < tb.getNumLinhas(); i++ ) {
			tb.setValor( new Boolean( false ), i, 0 );
		}
	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
			if ( kevt.getSource() == btBuscar ) {
				btBuscar.doClick();
				tabcompra.requestFocus();
			}
			else if ( kevt.getSource() == tabcompra ) {
				tabitcompra.requestFocus();
			}
			else if ( kevt.getSource() == tabitcompra ) {
				btGerar.requestFocus();
			}
		}
		// super.keyPressed(kevt);
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btBuscar ) {
			buscaCompra();
		}
		else if ( evt.getSource() == btExec ) {
			//Se for importa��o busca os itens da importa��o;
			if("IMP".equals( cbTipo.getVlrString())){
				
			} else {
				buscaItCompra();
			}
			
		}
		else if ( evt.getSource() == btTodosItCompra ) {
			carregaTudo( tabitcompra );
		}
		else if ( evt.getSource() == btNenhumItCompra ) {
			carregaNada( tabitcompra );
		}
		else if ( evt.getSource() == txtCodCompra ) {
			if ( txtCodCompra.getVlrInteger().intValue() > 0 )
				btBuscar.requestFocus();
		}
	}

	public void beforeCarrega( CarregaEvent e ) {

	}

	public void afterCarrega( CarregaEvent e ) {

		if ( e.getListaCampos() == lcCompra ) {
			txtCodFor.setAtivo( false );
			txtDataIni.setAtivo( false );
			txtDataFim.setAtivo( false );
			if(txtCodImp.getVlrInteger() > 0 ){
				cbTipo.setVlrString( "IMP" );
			}
			
		} 
		else if (e.getListaCampos() == lcFor	){
			txtDataIni.setAtivo( true );
			txtDataFim.setAtivo( true );
		}
	}

	public void valorAlterado( RadioGroupEvent rgevt ) {

		lcCompra.limpaCampos( true );
	}

	public void firstFocus() {

		txtCodCompra.requestFocus();
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcFor.setConexao( cn );
		lcCompra.setConexao( cn );		

		txtCodCompra.setFocusable( true );
		setFirstFocus( txtCodCompra );
	}

	public void focusGained( FocusEvent arg0 ) {

	}

	public void focusLost( FocusEvent evt ) {

		if ( evt.getSource() == txtCodCompra ) {
			if ( txtCodCompra.getVlrInteger() > 0 ) {
				txtCodFor.setAtivo( false );
			}
			else {
				txtCodFor.setAtivo( true );
			}
		}

	}

	public void valorAlterado( JComboBoxEvent evt ) {

		if(evt.getComboBoxPad() == cbTipo){
			//Abilita campos se tipo de gera��o da nota complementar for Diferente de Importa��o
			boolean habilita = !"IMP".equals( cbTipo.getVlrString() );
		
			//tabcompra.setColunaEditavel( enum_compra.SEL.ordinal(), habilita );
			tabitcompra.setColunaEditavel( enum_itcompra.SEL.ordinal(), habilita );
			btTodosItCompra.setEnabled( habilita );
			btNenhumItCompra.setEnabled( habilita );
			/*		
			txtNroDI.setEnabled( habilita );
			txtDtRegDI.setEnabled( habilita );
			txtDtDesembDI.setEnabled( habilita );
			txtIdentContainer.setEnabled( habilita );*/
		}
	}
}
