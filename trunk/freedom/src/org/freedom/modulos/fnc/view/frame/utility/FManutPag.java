/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FManutPag.java <BR>
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
 *                    Tela de manuten��o de contas a pagar.
 * 
 */

package org.freedom.modulos.fnc.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.library.type.StringDireita;
import org.freedom.modulos.fnc.business.object.Cheque;
import org.freedom.modulos.fnc.view.dialog.report.DLImpReciboPag;
import org.freedom.modulos.fnc.view.dialog.utility.DLBaixaPag;
import org.freedom.modulos.fnc.view.dialog.utility.DLEditaPag;
import org.freedom.modulos.fnc.view.dialog.utility.DLNovoPag;
import org.freedom.modulos.fnc.view.frame.crud.plain.FSinalizadores;
import org.freedom.modulos.std.view.dialog.utility.DLCancItem;

public class FManutPag extends FFilho implements ActionListener, CarregaListener, ChangeListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	// private static final String HISTORICO_PADRAO = "PAGAMENTO REF. A COMPRA: <DOCUMENTO>";

	private ImageIcon imgVencido = Icone.novo( "clVencido.gif" );

	private ImageIcon imgPago = Icone.novo( "clPago.gif" );

	private ImageIcon imgPagoParcial = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgNaoVencido = Icone.novo( "clNaoVencido.gif" );

	private ImageIcon imgCancelado = Icone.novo( "clCancelado.gif" );

	private JPanelPad pnLegenda = new JPanelPad( JPanelPad.TP_JPANEL );

	private JLabelPad lbVencido = new JLabelPad( "Vencido", imgVencido, SwingConstants.LEFT );

	private JLabelPad lbParcial = new JLabelPad( "Pg.Parcial", imgPagoParcial, SwingConstants.LEFT );

	private JLabelPad lbPago = new JLabelPad( "Quitado", imgPago, SwingConstants.LEFT );

	private JLabelPad lbVencer = new JLabelPad( "� vencer", imgNaoVencido, SwingConstants.LEFT );

	private JLabelPad lbCancelado = new JLabelPad( "Cancelado", imgCancelado, SwingConstants.LEFT );

	private JPanelPad pnTabConsulta = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesConsulta = new JPanelPad( 40, 100 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinConsulta = new JPanelPad( 500, 140 );

	private JPanelPad pnConsulta = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTabbedPanePad tpn = new JTabbedPanePad();

	private JTablePad tabConsulta = new JTablePad();

	private JScrollPane spnConsulta = new JScrollPane( tabConsulta );

	private JPanelPad pnTabBaixa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesBaixa = new JPanelPad( 40, 100 );

	private JPanelPad pinBaixa = new JPanelPad( 500, 140 );

	private JPanelPad pnBaixa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTablePad tabBaixa = new JTablePad();

	private JScrollPane spnBaixa = new JScrollPane( tabBaixa );

	private JPanelPad pnTabManut = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinBotoesManut = new JPanelPad( 40, 210 );

	private JPanelPad pinManut = new JPanelPad( 500, 155 );

	private JPanelPad pnManut = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTablePad tabManut = new JTablePad();

	private JScrollPane spnManut = new JScrollPane( tabManut );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCNPJFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodForManut = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCodForManut2 = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCNPJForManut = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtPrimCompr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtUltCompr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrMaxFat = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtDataMaxFat = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrTotCompr = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrTotPago = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrTotAberto = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtDataMaxAcum = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtVlrMaxAcum = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtCodPagBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodPagManut = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDocManut = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtCodCompraBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCompraManut = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtNumCheque = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodForBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCNPJForBaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtDtEmisBaixa = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDtEmisManut = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodBancoBaixa = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtTotPagBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtTotAbertoBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtTotPagoBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtJurosBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtDatainiManut = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafimManut = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtRazForManut = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRazForManut2 = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRazForBaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescBancoBaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtTotalVencido = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, Aplicativo.casasDecFin );

	private JTextFieldPad txtTotalParcial = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, Aplicativo.casasDecFin );

	private JTextFieldPad txtTotalPago = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, Aplicativo.casasDecFin );

	private JTextFieldPad txtTotalVencer = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, Aplicativo.casasDecFin );

	private JTextFieldPad txtTotalCancelado = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, Aplicativo.casasDecFin );

	private JLabelPad lbFiltroStatus = new JLabelPad( "Filtrar por:" );

	private JPanelPad pinLbFiltroStatus = new JPanelPad( 53, 15 );

	private JPanelPad pinFiltroStatus = new JPanelPad( 300, 150 );

	private JButtonPad btBaixaConsulta = new JButtonPad( Icone.novo( "btOk.gif" ) );

	private JButtonPad btBaixaManut = new JButtonPad( Icone.novo( "btOk.gif" ) );

	private JButtonPad btEditManut = new JButtonPad( Icone.novo( "btEditar.gif" ) );

	private JButtonPad btNovoManut = new JButtonPad( Icone.novo( "btNovo.gif" ) );

	private JButtonPad btExcluirManut = new JButtonPad( Icone.novo( "btExcluir.gif" ) );

	private JButtonPad btEstManut = new JButtonPad( Icone.novo( "btCancelar.gif" ) );

	private JButtonPad btCancItem = new JButtonPad( Icone.novo( "btCancItem.png" ) );

	private JButtonPad btImpRec = new JButtonPad( Icone.novo( "btImprimeRec.gif" ) );

	private JButtonPad btExecManut = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private JButtonPad btBaixa = new JButtonPad( Icone.novo( "btOk.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private JCheckBoxPad cbPagas = new JCheckBoxPad( "Pagas", "S", "N" );

	private JCheckBoxPad cbAPagar = new JCheckBoxPad( "� Pagar", "S", "N" );

	private JCheckBoxPad cbPagParcial = new JCheckBoxPad( "Pagamento parcial", "S", "N" );

	private JCheckBoxPad cbCanceladas = new JCheckBoxPad( "Canceladas", "S", "N" );

	private JRadioGroup<?, ?> rgData = null;

	private JRadioGroup<?, ?> rgVenc = null;

	private Vector<String> vCodPag = new Vector<String>();

	private Vector<String> vNParcPag = new Vector<String>();

	private Vector<String> vNParcBaixa = new Vector<String>();

	private Vector<String> vCodPed = new Vector<String>();

	private Vector<String> vNumContas = new Vector<String>();

	private Vector<String> vCodPlans = new Vector<String>();

	private Vector<String> vCodCCs = new Vector<String>();

	private Vector<String> vDtEmiss = new Vector<String>();

	private Vector<String> vValsData = new Vector<String>();

	private Vector<String> vLabsData = new Vector<String>();

	private Vector<String> vValsVenc = new Vector<String>();

	private Vector<String> vLabsVenc = new Vector<String>();

	private ImageIcon imgColuna = null;

	private ListaCampos lcFor = new ListaCampos( this );

	private ListaCampos lcForBaixa = new ListaCampos( this );

	private ListaCampos lcForManut = new ListaCampos( this );

	private ListaCampos lcForManut2 = new ListaCampos( this );

	private ListaCampos lcCompraBaixa = new ListaCampos( this );

	private ListaCampos lcPagBaixa = new ListaCampos( this );

	private ListaCampos lcPagManut = new ListaCampos( this );

	private ListaCampos lcBancoBaixa = new ListaCampos( this );

	private Date dIniManut = null;

	private Date dFimManut = null;

	private int iCodPag = 0;

	private int iNParcPag = 0;

	private int iAnoCC = 0;
	
	private JPopupMenu menuCores = new JPopupMenu();

	private Map<String, Integer> prefere = null;
	
	private JMenuItem menucancelacor = new JMenuItem();
	
	private JMenuItem menucadastracor = new JMenuItem();

	public enum enum_tab_manut { IMGSTATUS, DTVENCITPAG, STATUSITPAG, CODFOR, RAZFOR, OBSITPAG, CODPAG, CHEQUES, NPARCPAG, DOC, DOCCOMPRA, VLRPARCITPAG, 
		DTPAGOITPAG, VLRPAGOITPAG, VLRDESCITPAG, VLRJUROSITPAG, VLRDEVITPAG, VLRADICITPAG, VLRAPAGITPAG, 
		VLRCANCITPAG, NUMCONTA, DESCPLAN, DESCCC, CODTIPOCOB, DESCTIPOCOB, CODCOMPRA, CODPLAN, CODCC, DTITPAG   };

		public FManutPag() {

			super( false );
			setTitulo( "Manuten��o de contas a pagar" );
			setAtribos( 20, 20, 840, 600 );

			cbAPagar.setVlrString( "S" );
			cbPagParcial.setVlrString( "S" );

			Container c = getContentPane();
			c.setLayout( new BorderLayout() );
			c.add( pnRod, BorderLayout.SOUTH );
			c.add( tpn, BorderLayout.CENTER );


			btSair.setPreferredSize( new Dimension( 90, 30 ) );

			pnLegenda.setPreferredSize( new Dimension( 700, 50 ) );
			pnLegenda.setLayout( null );

			lbVencido.setBounds( 5, 0, 90, 17 );
			txtTotalVencido.setBounds( 5, 18, 90, 18 );
			lbParcial.setBounds( 100, 0, 90, 17 );
			txtTotalParcial.setBounds( 100, 18, 90, 18 );
			lbPago.setBounds( 200, 0, 90, 17 );
			txtTotalPago.setBounds( 200, 18, 90, 18 );
			lbVencer.setBounds( 300, 0, 90, 17 );
			txtTotalVencer.setBounds( 300, 18, 90, 18 );
			lbCancelado.setBounds( 400, 0, 90, 17 );
			txtTotalCancelado.setBounds( 400, 18, 90, 18 );

			pnLegenda.add( lbVencido );
			pnLegenda.add( txtTotalVencido );
			pnLegenda.add( lbParcial );
			pnLegenda.add( txtTotalParcial );
			pnLegenda.add( lbPago );
			pnLegenda.add( txtTotalPago );
			pnLegenda.add( lbVencer );
			pnLegenda.add( txtTotalVencer );
			pnLegenda.add( lbCancelado );
			pnLegenda.add( txtTotalCancelado );

			txtTotalVencido.setSoLeitura( true );
			txtTotalParcial.setSoLeitura( true );
			txtTotalPago.setSoLeitura( true );
			txtTotalVencer.setSoLeitura( true );
			txtTotalCancelado.setSoLeitura( true );
			
			txtTotalVencido.setFont( SwingParams.getFontbold() );
			txtTotalParcial.setFont( SwingParams.getFontbold() );
			txtTotalPago.setFont( SwingParams.getFontbold() );
			txtTotalVencer.setFont( SwingParams.getFontbold() );
			txtTotalCancelado.setFont( SwingParams.getFontbold() );
			
			pnRod.setBorder( BorderFactory.createEtchedBorder() );
			pnRod.setPreferredSize( new Dimension( 600, 42 ) );

			pnRod.add( btSair, BorderLayout.EAST );
			pnRod.add( pnLegenda, BorderLayout.WEST );

			btSair.addActionListener( this );
			btImpRec.addActionListener( this );

			// Consulta:

			lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, false ) );
			lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Descri��o do fornecedor", ListaCampos.DB_SI, false ) );
			lcFor.add( new GuardaCampo( txtCNPJFor, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false ) );

			lcFor.montaSql( false, "FORNECED", "CP" );
			lcFor.setQueryCommit( false );
			lcFor.setReadOnly( true );
			txtCodFor.setTabelaExterna( lcFor, null );
			txtCodFor.setFK( true );
			txtCodFor.setNomeCampo( "CodFor" );

			txtPrimCompr.setAtivo( false );
			txtUltCompr.setAtivo( false );
			txtDataMaxFat.setAtivo( false );
			txtVlrMaxFat.setAtivo( false );
			txtVlrTotCompr.setAtivo( false );
			txtVlrTotPago.setAtivo( false );
			txtVlrTotAberto.setAtivo( false );
			txtDataMaxAcum.setAtivo( false );
			txtVlrMaxAcum.setAtivo( false );

			Funcoes.setBordReq( txtCodFor );

			tpn.addTab( "Consulta", pnConsulta );

			btBaixaConsulta.setToolTipText( "Baixa" );

			pnConsulta.add( pinConsulta, BorderLayout.NORTH );
			pnTabConsulta.add( pinBotoesConsulta, BorderLayout.EAST );
			pnTabConsulta.add( spnConsulta, BorderLayout.CENTER );
			pnConsulta.add( pnTabConsulta, BorderLayout.CENTER );

			pinConsulta.adic( new JLabelPad( "C�d.for." ), 7, 0, 250, 20 );
			pinConsulta.adic( txtCodFor, 7, 20, 80, 20 );
			pinConsulta.adic( new JLabelPad( "Descri��o do fornecedor" ), 90, 0, 250, 20 );
			pinConsulta.adic( txtRazFor, 90, 20, 217, 20 );
			pinConsulta.adic( new JLabelPad( "Primeira compra" ), 310, 0, 98, 20 );
			pinConsulta.adic( txtPrimCompr, 310, 20, 98, 20 );
			pinConsulta.adic( new JLabelPad( "�ltima compra" ), 411, 0, 97, 20 );
			pinConsulta.adic( txtUltCompr, 411, 20, 100, 20 );
			pinConsulta.adic( new JLabelPad( "Data" ), 7, 40, 98, 20 );
			pinConsulta.adic( txtDataMaxFat, 7, 60, 98, 20 );
			pinConsulta.adic( new JLabelPad( "Valor da maior fatura" ), 108, 40, 139, 20 );
			pinConsulta.adic( txtVlrMaxFat, 108, 60, 139, 20 );
			pinConsulta.adic( new JLabelPad( "Data" ), 250, 40, 98, 20 );
			pinConsulta.adic( txtDataMaxAcum, 250, 60, 120, 20 );
			pinConsulta.adic( new JLabelPad( "Valor do maior acumulo" ), 373, 40, 139, 20 );
			pinConsulta.adic( txtVlrMaxAcum, 373, 60, 139, 20 );
			pinConsulta.adic( new JLabelPad( "Total de compras" ), 7, 80, 150, 20 );
			pinConsulta.adic( txtVlrTotCompr, 7, 100, 165, 20 );
			pinConsulta.adic( new JLabelPad( "Total pago" ), 175, 80, 97, 20 );
			pinConsulta.adic( txtVlrTotPago, 175, 100, 165, 20 );
			pinConsulta.adic( new JLabelPad( "Total em aberto" ), 343, 80, 117, 20 );
			pinConsulta.adic( txtVlrTotAberto, 343, 100, 167, 20 );
			pinBotoesConsulta.adic( btBaixaConsulta, 5, 10, 30, 30 );

			tabConsulta.adicColuna( "" );
			tabConsulta.adicColuna( "Vencimento" );
			tabConsulta.adicColuna( "S�rie" );
			tabConsulta.adicColuna( "Doc." );
			tabConsulta.adicColuna( "C�d. compra" );
			tabConsulta.adicColuna( "Data da compra" );
			tabConsulta.adicColuna( "Valor" );
			tabConsulta.adicColuna( "Data pagamento" );
			tabConsulta.adicColuna( "Valor pago" );
			tabConsulta.adicColuna( "Atraso" );
			tabConsulta.adicColuna( "Observa��es" );
			tabConsulta.adicColuna( "Banco" );
			tabConsulta.adicColuna( "Valor canc." );

			tabConsulta.setTamColuna( 0, 0 );
			tabConsulta.setTamColuna( 90, 1 );
			tabConsulta.setTamColuna( 50, 2 );
			tabConsulta.setTamColuna( 50, 3 );
			tabConsulta.setTamColuna( 90, 4 );
			tabConsulta.setTamColuna( 110, 5 );
			tabConsulta.setTamColuna( 90, 6 );
			tabConsulta.setTamColuna( 110, 7 );
			tabConsulta.setTamColuna( 100, 8 );
			tabConsulta.setTamColuna( 60, 9 );
			tabConsulta.setTamColuna( 100, 10 );
			tabConsulta.setTamColuna( 80, 11 );
			tabConsulta.setTamColuna( 90, 12 );
			
			tabConsulta.setRowHeight( 20 );
			// Baixa:

			lcCompraBaixa.add( new GuardaCampo( txtCodCompraBaixa, "CodCompra", "C�d.compra", ListaCampos.DB_PK, false ) );
			lcCompraBaixa.add( new GuardaCampo( txtSerie, "Serie", "S�rie", ListaCampos.DB_SI, false ) );
			lcCompraBaixa.montaSql( false, "COMPRA", "CP" );
			lcCompraBaixa.setQueryCommit( false );
			lcCompraBaixa.setReadOnly( true );
			txtCodCompraBaixa.setTabelaExterna( lcCompraBaixa, null );
			txtCodCompraBaixa.setFK( true );
			txtCodCompraBaixa.setNomeCampo( "CodCompra" );

			lcForBaixa.add( new GuardaCampo( txtCodForBaixa, "CodFor", "C�d.for", ListaCampos.DB_PK, false ) );
			lcForBaixa.add( new GuardaCampo( txtRazForBaixa, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
			lcForBaixa.add( new GuardaCampo( txtCNPJForBaixa, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false ) );
			lcForBaixa.montaSql( false, "FORNECED", "CP" );
			lcForBaixa.setQueryCommit( false );
			lcForBaixa.setReadOnly( true );
			txtCodForBaixa.setTabelaExterna( lcForBaixa, null );
			txtCodForBaixa.setFK( true );
			txtCodForBaixa.setNomeCampo( "CodFor" );

			lcBancoBaixa.add( new GuardaCampo( txtCodBancoBaixa, "CodBanco", "C�d.banco", ListaCampos.DB_PK, false ) );
			lcBancoBaixa.add( new GuardaCampo( txtDescBancoBaixa, "NomeBanco", "Nome banco", ListaCampos.DB_SI, false ) );
			lcBancoBaixa.montaSql( false, "BANCO", "FN" );
			lcBancoBaixa.setQueryCommit( false );
			lcBancoBaixa.setReadOnly( true );
			txtCodBancoBaixa.setTabelaExterna( lcBancoBaixa, null );
			txtCodBancoBaixa.setFK( true );
			txtCodBancoBaixa.setNomeCampo( "CodBanco" );

			lcPagBaixa.add( new GuardaCampo( txtCodPagBaixa, "CodPag", "C�d.pag", ListaCampos.DB_PK, false ) );
			lcPagBaixa.add( new GuardaCampo( txtCodCompraBaixa, "CodCompra", "C�d.compra", ListaCampos.DB_FK, false ) );
			lcPagBaixa.add( new GuardaCampo( txtDoc, "DocPag", "Doc.", ListaCampos.DB_SI, false ) );
			lcPagBaixa.add( new GuardaCampo( txtTotPagBaixa, "VlrPag", "Total pag.", ListaCampos.DB_SI, false ) );
			lcPagBaixa.add( new GuardaCampo( txtCodForBaixa, "CodFor", "C�d.for", ListaCampos.DB_FK, false ) );
			lcPagBaixa.add( new GuardaCampo( txtDtEmisBaixa, "DataPag", "Data emis.", ListaCampos.DB_SI, false ) );
			lcPagBaixa.add( new GuardaCampo( txtCodBancoBaixa, "CodBanco", "C�d.banco", ListaCampos.DB_FK, false ) );
			lcPagBaixa.add( new GuardaCampo( txtTotAbertoBaixa, "VlrApagPag", "Total aberto", ListaCampos.DB_SI, false ) );
			lcPagBaixa.add( new GuardaCampo( txtTotPagoBaixa, "VlrPagoPag", "Total pago", ListaCampos.DB_SI, false ) );
			lcPagBaixa.add( new GuardaCampo( txtJurosBaixa, "VlrJurosPag", "Total juros", ListaCampos.DB_SI, false ) );
			lcPagBaixa.montaSql( false, "PAGAR", "FN" );
			lcPagBaixa.setQueryCommit( false );
			lcPagBaixa.setReadOnly( true );

			txtCodPagBaixa.setTabelaExterna( lcPagBaixa, null );
			txtCodPagBaixa.setFK( true );
			txtCodPagBaixa.setNomeCampo( "CodPag" );
			txtDoc.setAtivo( false );
			txtCodCompraBaixa.setAtivo( false );
			txtSerie.setAtivo( false );
			txtCodForBaixa.setAtivo( false );
			txtDtEmisBaixa.setAtivo( false );
			txtCodBancoBaixa.setAtivo( false );
			txtTotPagBaixa.setAtivo( false );
			txtTotAbertoBaixa.setAtivo( false );
			txtTotPagoBaixa.setAtivo( false );
			txtJurosBaixa.setAtivo( false );

			Funcoes.setBordReq( txtCodPagBaixa );

			tpn.addTab( "Baixa", pnBaixa );

			btBaixa.setToolTipText( "Baixa" );

			pnBaixa.add( pinBaixa, BorderLayout.NORTH );
			pnTabBaixa.add( pinBotoesBaixa, BorderLayout.EAST );
			pnTabBaixa.add( spnBaixa, BorderLayout.CENTER );
			pnBaixa.add( pnTabBaixa, BorderLayout.CENTER );

			pinBaixa.adic( new JLabelPad( "C�d.pag" ), 7, 0, 80, 20 );
			pinBaixa.adic( txtCodPagBaixa, 7, 20, 80, 20 );
			pinBaixa.adic( new JLabelPad( "Doc." ), 90, 0, 77, 20 );
			pinBaixa.adic( txtDoc, 90, 20, 77, 20 );
			pinBaixa.adic( new JLabelPad( " -" ), 170, 20, 7, 20 );
			pinBaixa.adic( new JLabelPad( "S�rie" ), 180, 0, 50, 20 );
			pinBaixa.adic( txtSerie, 180, 20, 50, 20 );
			pinBaixa.adic( new JLabelPad( "Pedido" ), 240, 0, 77, 20 );
			pinBaixa.adic( txtCodCompraBaixa, 240, 20, 77, 20 );
			pinBaixa.adic( new JLabelPad( "C�d.for." ), 7, 40, 250, 20 );
			pinBaixa.adic( txtCodForBaixa, 7, 60, 80, 20 );
			pinBaixa.adic( new JLabelPad( "Descri��o do fornecedor" ), 90, 40, 250, 20 );
			pinBaixa.adic( txtRazForBaixa, 90, 60, 197, 20 );
			pinBaixa.adic( new JLabelPad( "C�d.banco" ), 290, 40, 250, 20 );
			pinBaixa.adic( txtCodBancoBaixa, 290, 60, 67, 20 );
			pinBaixa.adic( new JLabelPad( "Descri��o do banco" ), 360, 40, 250, 20 );
			pinBaixa.adic( txtDescBancoBaixa, 360, 60, 150, 20 );
			pinBaixa.adic( new JLabelPad( "Data de emiss�o" ), 7, 80, 100, 20 );
			pinBaixa.adic( txtDtEmisBaixa, 7, 100, 100, 20 );
			pinBaixa.adic( new JLabelPad( "Total a pagar" ), 110, 80, 97, 20 );
			pinBaixa.adic( txtTotPagBaixa, 110, 100, 97, 20 );
			pinBaixa.adic( new JLabelPad( "Total pago" ), 210, 80, 97, 20 );
			pinBaixa.adic( txtTotPagoBaixa, 210, 100, 97, 20 );
			pinBaixa.adic( new JLabelPad( "Total em aberto" ), 310, 80, 107, 20 );
			pinBaixa.adic( txtTotAbertoBaixa, 310, 100, 107, 20 );
			pinBaixa.adic( new JLabelPad( "Juros" ), 420, 80, 80, 20 );
			pinBaixa.adic( txtJurosBaixa, 420, 100, 90, 20 );

			pinBotoesBaixa.adic( btBaixa, 5, 10, 30, 30 );

			tabBaixa.adicColuna( "" );
			tabBaixa.adicColuna( "Vencimento" );
			tabBaixa.adicColuna( "N� de parcelas" );
			tabBaixa.adicColuna( "Doc." );
			tabBaixa.adicColuna( "Pedido" );
			tabBaixa.adicColuna( "Valor da parcela" );
			tabBaixa.adicColuna( "Data pagamento" );
			tabBaixa.adicColuna( "Valor pago" );
			tabBaixa.adicColuna( "Valor desconto" );
			tabBaixa.adicColuna( "Valor juros" );
			tabBaixa.adicColuna( "Valor aberto" );
			tabBaixa.adicColuna( "Valor cancelado" );
			tabBaixa.adicColuna( "Conta" );
			tabBaixa.adicColuna( "Categoria" );
			tabBaixa.adicColuna( "Centro de custo" );
			tabBaixa.adicColuna( "Observa��o" );

			tabBaixa.setTamColuna( 0, 0 );
			tabBaixa.setTamColuna( 110, 1 );
			tabBaixa.setTamColuna( 120, 2 );
			tabBaixa.setTamColuna( 50, 3 );
			tabBaixa.setTamColuna( 70, 4 );
			tabBaixa.setTamColuna( 140, 5 );
			tabBaixa.setTamColuna( 110, 6 );
			tabBaixa.setTamColuna( 90, 7 );
			tabBaixa.setTamColuna( 110, 8 );
			tabBaixa.setTamColuna( 100, 9 );
			tabBaixa.setTamColuna( 100, 10 );
			tabBaixa.setTamColuna( 90, 11 );

			tabBaixa.setTamColuna( 80, 12 );
			tabBaixa.setTamColuna( 100, 13 );
			tabBaixa.setTamColuna( 120, 14 );
			tabBaixa.setTamColuna( 100, 15 );
			
			tabBaixa.setRowHeight( 20 );

			// Manuten��o

			tpn.addTab( "Manuten��o", pnManut );

			lcPagManut.add( new GuardaCampo( txtCodPagManut, "CodPag", "C�d.pag.", ListaCampos.DB_PK, false ) );
			lcPagManut.add( new GuardaCampo( txtDocManut, "DocPag", "Doc.pag.", ListaCampos.DB_SI, false ) );
			lcPagManut.add( new GuardaCampo( txtCodCompraManut, "CodCompra", "Compra", ListaCampos.DB_SI, false ) );
			lcPagManut.add( new GuardaCampo( txtCodForManut2, "CodFor", "Cod.for.", ListaCampos.DB_FK, false ) );
			lcPagManut.add( new GuardaCampo( txtDtEmisManut, "DataPag", "Data emiss�o", ListaCampos.DB_SI, false ) );
			lcPagManut.montaSql( false, "PAGAR", "FN" );
			lcPagManut.setQueryCommit( false );
			lcPagManut.setReadOnly( true );
			txtCodPagManut.setTabelaExterna( lcPagManut, null );
			txtCodPagManut.setFK( true );
			txtCodPagManut.setNomeCampo( "CodPag" );

			btBaixaManut.setToolTipText( "Baixa" );
			btEditManut.setToolTipText( "Editar" );
			btNovoManut.setToolTipText( "Novo" );
			btExcluirManut.setToolTipText( "Excluir" );
			btCancItem.setToolTipText( "Cancela Item" );
			btExecManut.setToolTipText( "Listar" );

			pnManut.add( pinManut, BorderLayout.NORTH );
			pnTabManut.add( pinBotoesManut, BorderLayout.EAST );
			pnTabManut.add( spnManut, BorderLayout.CENTER );
			pnManut.add( pnTabManut, BorderLayout.CENTER );

			txtDatainiManut.setVlrDate( new Date() );
			txtDatafimManut.setVlrDate( new Date() );

			pinManut.adic( new JLabelPad( "Per�odo" ), 7, 0, 200, 20 );
			pinManut.adic( txtDatainiManut, 7, 20, 100, 20 );
			pinManut.adic( new JLabelPad( "at�" ), 113, 20, 27, 20 );
			pinManut.adic( txtDatafimManut, 140, 20, 100, 20 );
			pinManut.adic( btExecManut, 776, 20, 30, 64 );

			pinManut.adic( txtCodForManut, 7, 65, 50, 20, "C�d.for." );
			pinManut.adic( txtRazForManut, 60, 65, 180, 20, "Descri��o do fornecedor" );

			JLabelPad separacao = new JLabelPad();
			separacao.setBorder( BorderFactory.createEtchedBorder() );
			pinManut.adic( separacao, 7, 95, 800, 2 );

			pinManut.adic( txtCodPagManut		, 7		, 120	, 80	, 20, "Cod.pag." );
			pinManut.adic( txtDocManut			, 90	, 120	, 77	, 20, "Documento" );
			pinManut.adic( txtCodCompraManut	, 170	, 120	, 77	, 20, "Pedido" );
			pinManut.adic( txtCodForManut2		, 250	, 120	, 77	, 20, "C�d.for." );
			pinManut.adic( txtRazForManut2		, 330	, 120	, 300	, 20, "Raz�o social do fornecedor" );
			pinManut.adic( txtDtEmisManut		, 633	, 120	, 90	, 20, "Data emiss�o" );
			pinManut.adic( txtNumCheque			, 726	, 120	, 80	, 20, "Nro.Cheque" );

			vValsData.addElement( "V" );
			vValsData.addElement( "E" );
			vLabsData.addElement( "Vencimento" );
			vLabsData.addElement( "Emiss�o" );

			rgData = new JRadioGroup<String, String>( 2, 1, vLabsData, vValsData );
			rgData.setVlrString( "V" );
			pinManut.adic( new JLabelPad( "Filtrar por:" ), 247, 0, 115, 20 );
			pinManut.adic( rgData, 247, 20, 115, 65 );

			vValsVenc.addElement( "VE" );
			vValsVenc.addElement( "AV" );
			vValsVenc.addElement( "TT" );
			vLabsVenc.addElement( "Vencidas" );
			vLabsVenc.addElement( "� vencer" );
			vLabsVenc.addElement( "Ambas" );

			pinLbFiltroStatus.adic( lbFiltroStatus, 0, 0, 350, 15 );
			pinLbFiltroStatus.tiraBorda();

			pinManut.adic( pinLbFiltroStatus, 488, 3, 80, 15 );
			pinManut.adic( pinFiltroStatus, 488, 20, 280, 65 );

			pinFiltroStatus.adic( cbAPagar		, 5		, 5		, 80	, 20 );
			pinFiltroStatus.adic( cbPagas		, 5		, 30	, 80	, 20 );
			pinFiltroStatus.adic( cbPagParcial	, 120	, 5		, 150	, 20 );
			pinFiltroStatus.adic( cbCanceladas	, 120	, 30	, 150	, 20 );

			rgVenc = new JRadioGroup<String, String>( 3, 2, vLabsVenc, vValsVenc );
			rgVenc.setVlrString( "TT" );
			pinManut.adic( new JLabelPad( "Filtrar por:" ), 365, 0, 150, 20 );
			pinManut.adic( rgVenc, 365, 20, 115, 65 );

			lcForManut.add( new GuardaCampo( txtCodForManut, "CodFor", "C�d.for.", ListaCampos.DB_PK, false ) );
			lcForManut.add( new GuardaCampo( txtRazForManut, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
			lcForManut.add( new GuardaCampo( txtCNPJForManut, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false ) );

			lcForManut.montaSql( false, "FORNECED", "CP" );
			lcForManut.setQueryCommit( false );
			lcForManut.setReadOnly( true );

			txtCodForManut.setTabelaExterna( lcForManut, null );
			txtCodForManut.setFK( true );
			txtCodForManut.setNomeCampo( "CodFor" );

			lcForManut2.add( new GuardaCampo( txtCodForManut2, "CodFor", "C�d.for.", ListaCampos.DB_PK, false ) );
			lcForManut2.add( new GuardaCampo( txtRazForManut2, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );

			lcForManut2.montaSql( false, "FORNECED", "CP" );
			lcForManut2.setQueryCommit( false );
			lcForManut2.setReadOnly( true );

			txtCodForManut2.setTabelaExterna( lcForManut2, null );
			txtCodForManut2.setFK( true );
			txtCodForManut2.setNomeCampo( "CodFor" );


			pinBotoesManut.adic( btBaixaManut, 5, 10, 30, 30 );
			pinBotoesManut.adic( btEditManut, 5, 40, 30, 30 );
			pinBotoesManut.adic( btNovoManut, 5, 70, 30, 30 );
			pinBotoesManut.adic( btEstManut, 5, 100, 30, 30 );
			pinBotoesManut.adic( btExcluirManut, 5, 130, 30, 30 );
			pinBotoesManut.adic( btCancItem, 5, 160, 30, 30 );
			pinBotoesManut.adic( btImpRec, 5, 190, 30, 30 );		

			tabManut.adicColuna( "" ); 								// STATUS
			tabManut.adicColuna( "Vencto." ); 						// DTVENCITPAG
			tabManut.adicColuna( "St" ); 							// STATUSITPAG
			tabManut.adicColuna( "Cd.for." ); 						// CODFOR
			tabManut.adicColuna( "Raz�o social do fornecedor" ); 	// RAZFOR
			tabManut.adicColuna( "Observa��es" ); 					// OBSITPAG
			tabManut.adicColuna( "C�d.pg." ); 						// CODPAG
			tabManut.adicColuna( "Cheques" ); 						// CODPAG
			tabManut.adicColuna( "Parc." ); 						// NPARCPAG
			tabManut.adicColuna( "Doc.lanc" ); 						// DOC
			tabManut.adicColuna( "Compra" ); 						// DOCCOMPRA
			tabManut.adicColuna( "Valor" ); 						// VLRPARCITPAG
			tabManut.adicColuna( "Dt.pagto." ); 					// DTPAGOITPAG
			tabManut.adicColuna( "Pago" ); 							// VLRPAGOITPAG
			tabManut.adicColuna( "Desconto" ); 						// VLRDESCITPAG
			tabManut.adicColuna( "Juros" );							// VLRJUROSITPAG
			tabManut.adicColuna( "Devolu��o" ); 					// VLRDEVITPAG
			tabManut.adicColuna( "Adicional" ); 					// VLRADICITPAG
			tabManut.adicColuna( "Aberto" ); 						// VLRAPAGITPAG
			tabManut.adicColuna( "Cancelado" ); 					// VLRCANCITPAG
			tabManut.adicColuna( "Conta" ); 						// NUMCONTA
			tabManut.adicColuna( "Categoria" ); 					// DESCPLAN
			tabManut.adicColuna( "Centro de custo" ); 				// DESCC
			tabManut.adicColuna( "Tp.Cob." ); 						// CODTIPOCOB
			tabManut.adicColuna( "Descri��o do tipo de cobran�a" ); // DESCTIPOCOB		
			tabManut.adicColuna( "Cod.Compra" ); 					// CODCOMPRA
			tabManut.adicColuna( "Cod.Plan" ); 						// CODPLAN
			tabManut.adicColuna( "Cod.CC" ); 						// CODCC
			tabManut.adicColuna( "Dt.It.Pag" ); 					// DTITPAG

			tabManut.setTamColuna( 0,	enum_tab_manut.IMGSTATUS.ordinal()  );			
			tabManut.setTamColuna( 60, 	enum_tab_manut.DTVENCITPAG.ordinal() );	
			tabManut.setTamColuna( 20, 	enum_tab_manut.STATUSITPAG.ordinal() );			
			tabManut.setTamColuna( 45, 	enum_tab_manut.CODFOR.ordinal() );
			tabManut.setTamColuna( 150, enum_tab_manut.RAZFOR.ordinal() );
			tabManut.setTamColuna( 150, enum_tab_manut.OBSITPAG.ordinal() );
			tabManut.setTamColuna( 50, 	enum_tab_manut.CODPAG.ordinal() );
			tabManut.setTamColuna( 55, 	enum_tab_manut.CHEQUES.ordinal() );		
			tabManut.setTamColuna( 35,	enum_tab_manut.NPARCPAG.ordinal() );
			tabManut.setTamColuna( 50, 	enum_tab_manut.DOC.ordinal() );
			tabManut.setTamColuna( 50, 	enum_tab_manut.DOCCOMPRA.ordinal() ); 
			tabManut.setTamColuna( 80, enum_tab_manut.VLRPARCITPAG.ordinal() );
			tabManut.setTamColuna( 60, enum_tab_manut.DTPAGOITPAG.ordinal() );
			tabManut.setTamColuna( 60, enum_tab_manut.VLRPAGOITPAG.ordinal() );
			tabManut.setTamColuna( 60, enum_tab_manut.VLRDESCITPAG.ordinal() );
			tabManut.setTamColuna( 60, enum_tab_manut.VLRJUROSITPAG.ordinal() );
			tabManut.setTamColuna( 60, enum_tab_manut.VLRDEVITPAG.ordinal() );
			tabManut.setTamColuna( 60, enum_tab_manut.VLRADICITPAG.ordinal() );
			tabManut.setTamColuna( 60, enum_tab_manut.VLRAPAGITPAG.ordinal() );
			tabManut.setTamColuna( 60, enum_tab_manut.VLRCANCITPAG.ordinal() );
			tabManut.setTamColuna( 60, enum_tab_manut.NUMCONTA.ordinal() );
			tabManut.setTamColuna( 150, enum_tab_manut.DESCPLAN.ordinal() );
			tabManut.setTamColuna( 150, enum_tab_manut.DESCCC.ordinal() );
			tabManut.setTamColuna( 60, 	enum_tab_manut.CODTIPOCOB.ordinal() );
			tabManut.setTamColuna( 150, enum_tab_manut.DESCTIPOCOB.ordinal() );		
			tabManut.setTamColuna( 50, enum_tab_manut.CODCOMPRA.ordinal() );
			tabManut.setTamColuna( 150, enum_tab_manut.CODPLAN.ordinal() );
			tabManut.setTamColuna( 150, enum_tab_manut.CODCC.ordinal() );
			tabManut.setTamColuna( 60, enum_tab_manut.DTITPAG.ordinal() );
			
			tabManut.setRowHeight( 20 );

			lcFor.addCarregaListener( this );
			lcPagManut.addCarregaListener( this );
			lcPagBaixa.addCarregaListener( this );
			btBaixa.addActionListener( this );
			btBaixaConsulta.addActionListener( this );
			btBaixaManut.addActionListener( this );
			btEditManut.addActionListener( this );
			btNovoManut.addActionListener( this );
			btExcluirManut.addActionListener( this );
			btExecManut.addActionListener( this );
			btEstManut.addActionListener( this );
			btCancItem.addActionListener( this );
			tpn.addChangeListener( this );

			tabManut.addMouseListener( this );
			txtDocManut.addKeyListener( this );
			txtCodCompraManut.addKeyListener( this );
			txtNumCheque.addKeyListener( this );
			
		}

		private void limpaConsulta() {

			txtPrimCompr.setVlrString( "" );
			txtUltCompr.setVlrString( "" );
			txtDataMaxFat.setVlrString( "" );
			txtVlrMaxFat.setVlrString( "" );
			txtVlrTotCompr.setVlrString( "" );
			txtVlrTotPago.setVlrString( "" );
			txtVlrTotAberto.setVlrString( "" );
			txtDataMaxAcum.setVlrString( "" );
			txtVlrMaxAcum.setVlrString( "" );
		}

		private void carregaConsulta() {

			PreparedStatement ps = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			ResultSet rs = null;
			ResultSet rs1 = null;
			ResultSet rs2 = null;
			StringBuffer sSQL = new StringBuffer();

			limpaConsulta();
			tabConsulta.limpa();

			try {

				// Busca os totais ...
				sSQL.append( "SELECT SUM(VLRPARCPAG),SUM(VLRPAGOPAG),SUM(VLRAPAGPAG),MIN(DATAPAG),MAX(DATAPAG) " );
				sSQL.append( "FROM FNPAGAR " );
				sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODFOR=? " );

				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "FNPAGAR" ) );
				ps.setInt( 3, txtCodFor.getVlrInteger().intValue() );

				rs = ps.executeQuery();

				if ( rs.next() ) {

					txtVlrTotCompr.setVlrString( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( 1 ) ) );
					txtVlrTotPago.setVlrString( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( 2 ) ) );
					txtVlrTotAberto.setVlrString( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( 3 ) ) );
					txtPrimCompr.setVlrString( rs.getDate( 4 ) == null ? "" : StringFunctions.sqlDateToStrDate( rs.getDate( 4 ) ) );
					txtUltCompr.setVlrString( rs.getDate( 5 ) == null ? "" : StringFunctions.sqlDateToStrDate( rs.getDate( 5 ) ) );
				}

				rs.close();
				ps.close();

				con.commit();

				// Busca a maior fatura ...
				sSQL.delete( 0, sSQL.length() );
				sSQL.append( "SELECT MAX(VLRPARCPAG),DATAPAG " );
				sSQL.append( "FROM FNPAGAR " );
				sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODFOR=? " );
				sSQL.append( "GROUP BY DATAPAG " );
				sSQL.append( "ORDER BY 1 DESC" );

				ps1 = con.prepareStatement( sSQL.toString() );
				ps1.setInt( 1, Aplicativo.iCodEmp );
				ps1.setInt( 2, ListaCampos.getMasterFilial( "FNPAGAR" ) );
				ps1.setInt( 3, txtCodFor.getVlrInteger().intValue() );

				rs1 = ps1.executeQuery();

				if ( rs1.next() ) {

					txtVlrMaxFat.setVlrString( Funcoes.strDecimalToStrCurrency( 15, 2, rs1.getString( 1 ) ) );
					txtDataMaxFat.setVlrString( rs1.getDate( "DATAPAG" ) == null ? "" : StringFunctions.sqlDateToStrDate( rs1.getDate( "DATAPAG" ) ) );
				}

				rs1.close();
				ps1.close();

				con.commit();

				// Busca o maior acumulo ...
				sSQL.delete( 0, sSQL.length() );
				sSQL.append( "SELECT EXTRACT(MONTH FROM DATAPAG), SUM(VLRPARCPAG), EXTRACT(YEAR FROM DATAPAG) " );
				sSQL.append( "FROM FNPAGAR " );
				sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODFOR=? " );
				sSQL.append( "GROUP BY 1, 3 " );
				sSQL.append( "ORDER BY 2 DESC" );

				ps2 = con.prepareStatement( sSQL.toString() );
				ps2.setInt( 1, Aplicativo.iCodEmp );
				ps2.setInt( 2, ListaCampos.getMasterFilial( "FNPAGAR" ) );
				ps2.setInt( 3, txtCodFor.getVlrInteger().intValue() );

				rs2 = ps2.executeQuery();

				if ( rs2.next() ) {

					txtDataMaxAcum.setVlrString( Funcoes.strMes( rs2.getInt( 1 ) ) + " de " + rs2.getInt( 3 ) );
					txtVlrMaxAcum.setVlrString( Funcoes.strDecimalToStrCurrency( 15, 2, rs2.getString( 2 ) ) );
				}

				rs2.close();
				ps2.close();

				con.commit();

				carregaGridConsulta();

			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao carregar a consulta!\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			} finally {
				ps = null;
				ps1 = null;
				ps2 = null;
				rs = null;
				rs1 = null;
				rs2 = null;
				sSQL = null;
			}
		}

		private void carregaGridConsulta() {

			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuffer sSQL = new StringBuffer();
			float bdVlrAPagar = 0.0f;
			float bdVlrPago = 0.0f;

			Float bdTotVencido = 0.0f;
			Float bdTotParcial = 0.0f;
			Float bdTotPago = 0.0f;
			Float bdTotVencer = 0.0f;
			Float bdTotCancelado = 0.0f;

			try {

				vCodPag.clear();
				vNParcPag.clear();

				sSQL.append( "SELECT IT.DTVENCITPAG," );
				sSQL.append( "(SELECT C.SERIE FROM CPCOMPRA C " );
				sSQL.append( "WHERE C.CODCOMPRA=P.CODCOMPRA AND C.CODEMP=P.CODEMPCP AND C.CODFILIAL=P.CODFILIALCP)," );
				sSQL.append( "P.DOCPAG,P.CODCOMPRA,P.DATAPAG,IT.VLRPARCITPAG,IT.DTPAGOITPAG,IT.VLRPAGOITPAG," );
				sSQL.append( "(CASE WHEN IT.DTPAGOITPAG IS NULL THEN CAST('today' AS DATE)-IT.DTVENCITPAG " );
				sSQL.append( "ELSE IT.DTPAGOITPAG-IT.DTVENCITPAG END ) ATRASO, P.OBSPAG," );
				sSQL.append( "(SELECT B.NOMEBANCO FROM FNBANCO B " );
				sSQL.append( "WHERE B.CODBANCO=P.CODBANCO AND B.CODEMP=P.CODEMPBO AND B.CODFILIAL=P.CODFILIALBO) AS NOMEBANCO," );
				sSQL.append( "P.CODPAG,IT.NPARCPAG,IT.VLRPAGOITPAG,IT.VLRAPAGITPAG,IT.STATUSITPAG, IT.VLRCANCITPAG " );
				sSQL.append( "FROM FNPAGAR P,FNITPAGAR IT " );
				sSQL.append( "WHERE P.CODFOR=? AND P.CODEMP=? AND P.CODFILIAL=? " );
				sSQL.append( "AND IT.CODPAG = P.CODPAG AND IT.CODEMP=P.CODEMP " );
				sSQL.append( "AND IT.CODFILIAL=P.CODFILIAL " );
				sSQL.append( "ORDER BY P.CODPAG,IT.NPARCPAG" );

				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, txtCodFor.getVlrInteger().intValue() );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "FNPAGAR" ) );

				rs = ps.executeQuery();

				for ( int i = 0; rs.next(); i++ ) {

					bdVlrAPagar = Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrApagItPag" ) ).floatValue();
					bdVlrPago = Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrPagoItPag" ) ).floatValue();

					if ( "CP".equals( rs.getString( "StatusItPag" ) ) ) {
						imgColuna = imgCancelado;
						bdTotCancelado += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrCancItPag" ) ).floatValue();
					}
					else if ( rs.getString( "StatusItPag" ).equals( "PP" ) && bdVlrAPagar == 0.0f ) {
						imgColuna = imgPago;
						bdTotPago += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrPagoItPag" ) ).floatValue();
					}
					else if ( bdVlrPago > 0 ) {
						imgColuna = imgPagoParcial;
						bdTotParcial += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrPagoItPag" ) ).floatValue();
			
						if ( rs.getDate( "DtVencItPag" ).before( Calendar.getInstance().getTime() ) ) {
							bdTotVencido += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VLRApagItPag" ) ).floatValue();
						}
						else {
							bdTotVencer += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VLRApagItPag" ) ).floatValue();
						}
						
					}
					else if ( rs.getDate( "DtVencItPag" ).before( Calendar.getInstance().getTime() ) ) {
						imgColuna = imgVencido;
						bdTotVencido += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VLRPARCITPAG" ) ).floatValue();
					}
					else if ( rs.getDate( "DtVencItPag" ).after( Calendar.getInstance().getTime() ) ) {
						imgColuna = imgNaoVencido;
						bdTotVencer += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrApagItPag" ) ).floatValue();
					}

					tabConsulta.adicLinha();
					tabConsulta.setValor( imgColuna, i, 0 );
					tabConsulta.setValor( rs.getDate( "DtVencItPag" ) == null ? "" : StringFunctions.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) ), i, 1 );
					tabConsulta.setValor( rs.getString( 2 ) == null ? "" : rs.getString( 2 ), i, 2 );
					tabConsulta.setValor( rs.getString( "DocPag" ) == null ? "" : rs.getString( "DocPag" ), i, 3 );
					tabConsulta.setValor( String.valueOf( rs.getInt( "CodCompra" ) ), i, 4 );
					tabConsulta.setValor( rs.getDate( "DataPag" ) == null ? "" : StringFunctions.sqlDateToStrDate( rs.getDate( "DataPag" ) ), i, 5 );
					tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrParcItPag" ) ), i, 6 );
					tabConsulta.setValor( rs.getDate( "DtPagoItPag" ) == null ? "" : StringFunctions.sqlDateToStrDate( rs.getDate( "DtPagoItPag" ) ), i, 7 );
					tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrPagoItPag" ) ), i, 8 );
					tabConsulta.setValor( new Integer( rs.getInt( 9 ) ), i, 9 );
					tabConsulta.setValor( rs.getString( "ObsPag" ) == null ? "" : rs.getString( "ObsPag" ), i, 10 );
					tabConsulta.setValor( rs.getString( 11 ) == null ? "" : rs.getString( 11 ), i, 11 );
					tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrCancItPag" ) ), i, 12 );

					vCodPag.addElement( rs.getString( "CodPag" ) );
					vNParcPag.addElement( rs.getString( "NParcPag" ) );
				}

				txtTotalVencido.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotVencido.doubleValue(), Aplicativo.casasDecFin )) );
				txtTotalParcial.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotParcial.doubleValue(), Aplicativo.casasDecFin )) );
				txtTotalPago.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotPago.doubleValue(), Aplicativo.casasDecFin )) );
				txtTotalVencer.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotVencer.doubleValue(), Aplicativo.casasDecFin )) );
				txtTotalCancelado.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotCancelado.doubleValue(), Aplicativo.casasDecFin )) );

				con.commit();
			} catch ( SQLException err ) {
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao montar a tabela de consulta!\n" + err.getMessage(), true, con, err );
			} finally {
				ps = null;
				rs = null;
				sSQL = null;
			}
		}

		private void carregaGridBaixa() {

			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuffer sSQL = new StringBuffer();

			Float bdTotVencido = 0.0f;
			Float bdTotParcial = 0.0f;
			Float bdTotPago = 0.0f;
			Float bdTotVencer = 0.0f;
			Float bdTotCancelado = 0.0f;

			try {

				vNParcBaixa.clear();
				vNumContas.clear();
				vCodPlans.clear();
				vCodCCs.clear();
				vCodPed.clear();
				tabBaixa.limpa();

				sSQL.append( "SELECT IT.DTVENCITPAG,IT.STATUSITPAG,P.CODPAG,IT.DOCLANCAITPAG, " );
				sSQL.append( "P.CODCOMPRA,IT.VLRPARCITPAG,IT.DTPAGOITPAG,IT.VLRPAGOITPAG, " );
				sSQL.append( "IT.VLRAPAGITPAG,IT.NUMCONTA,IT.VLRDESCITPAG, " );
				sSQL.append( "(SELECT C.DESCCONTA FROM FNCONTA C " );
				sSQL.append( "WHERE C.NUMCONTA=IT.NUMCONTA AND C.CODEMP=IT.CODEMPCA AND C.CODFILIAL=IT.CODFILIALCA), IT.CODPLAN, " );
				sSQL.append( "(SELECT PL.DESCPLAN FROM FNPLANEJAMENTO PL " );
				sSQL.append( "WHERE PL.CODPLAN=IT.CODPLAN AND PL.CODEMP=IT.CODEMPPN AND PL.CODFILIAL=IT.CODFILIALPN), IT.CODCC, " );
				sSQL.append( "(SELECT CC.DESCCC FROM FNCC CC " );
				sSQL.append( "WHERE CC.CODCC=IT.CODCC AND CC.CODEMP=IT.CODEMPCC AND CC.CODFILIAL=IT.CODFILIALCC AND CC.ANOCC=IT.ANOCC) DESCCC, " );
				sSQL.append( "IT.OBSITPAG,IT.NPARCPAG,IT.VLRJUROSITPAG,IT.DTITPAG, IT.VLRCANCITPAG " );
				sSQL.append( "FROM FNITPAGAR IT,FNPAGAR P " );
				sSQL.append( "WHERE P.CODPAG=? AND P.CODEMP=? AND P.CODFILIAL=? " );
				sSQL.append( "AND IT.CODPAG=P.CODPAG AND IT.CODEMP=P.CODEMP AND IT.CODFILIAL=P.CODFILIAL " );
				sSQL.append( "ORDER BY IT.DTVENCITPAG,IT.STATUSITPAG " );

				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, txtCodPagBaixa.getVlrInteger().intValue() );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "FNPAGAR" ) );

				System.out.println( "SQL grid baixa:" + sSQL.toString() );

				rs = ps.executeQuery();

				double bdVlrAPagar = 0.0;
				double bdVlrPago = 0.0;

				for ( int i = 0; rs.next(); i++ ) {

					bdVlrAPagar = Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrApagItPag" ) ).doubleValue();
					bdVlrPago = Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrPagoItPag" ) ).doubleValue();
					if ( "CP".equals( rs.getString( "StatusItPag" ) ) ) {
						imgColuna = imgCancelado;
						bdTotCancelado += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrCancItPag" ) ).floatValue();
					}
					else if ( "PP".equals( rs.getString( "StatusItPag" ) ) && bdVlrAPagar == 0.0 ) {
						imgColuna = imgPago;
						bdTotPago += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrPagoItPag" ) ).floatValue();
					}
					else if ( bdVlrPago > 0 ) {
						imgColuna = imgPagoParcial;
						bdTotParcial += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrPagoItPag" ) ).floatValue();
						
						if ( rs.getDate( "DtVencItPag" ).before( Calendar.getInstance().getTime() ) ) {
							bdTotVencido += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VLRApagItPag" ) ).floatValue();
						}
						else {
							bdTotVencer += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VLRApagItPag" ) ).floatValue();
						}

					}
					else if ( rs.getDate( "DtVencItPag" ).before( Calendar.getInstance().getTime() ) ) {
						imgColuna = imgVencido;
						bdTotVencido += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VLRPARCITPAG" ) ).floatValue();
					}
					else if ( rs.getDate( "DtVencItPag" ).after( Calendar.getInstance().getTime() ) ) {
						imgColuna = imgNaoVencido;
						bdTotVencer += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrApagItPag" ) ).floatValue();
					}

					tabBaixa.adicLinha();
					tabBaixa.setValor( imgColuna, i, 0 );
					tabBaixa.setValor( rs.getDate( "DtVencItPag" ) == null ? "" : StringFunctions.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) ), i, 1 );
					tabBaixa.setValor( rs.getString( "NParcPag" ), i, 2 );
					tabBaixa.setValor( rs.getString( "DocLancaItPag" ) == null ? "" : rs.getString( "DocLancaItPag" ), i, 3 );
					tabBaixa.setValor( String.valueOf( rs.getInt( "CodCompra" ) ), i, 4 );
					tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrParcItPag" ) ), i, 5 );
					tabBaixa.setValor( rs.getDate( "DtPagoItPag" ) == null ? "" : StringFunctions.sqlDateToStrDate( rs.getDate( "DtPagoItPag" ) ), i, 6 );
					tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrPagoItPag" ) ), i, 7 );
					tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrDescItPag" ) ), i, 8 );
					tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrJurosItPag" ) ), i, 9 );
					tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrApagItPag" ) ), i, 10 );
					tabBaixa.setValor( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrCancItPag" ) ), i, 11 );
					tabBaixa.setValor( rs.getString( 12 ) == null ? "" : rs.getString( 12 ), i, 12 );
					tabBaixa.setValor( rs.getString( 14 ) == null ? "" : rs.getString( 14 ), i, 13 );
					tabBaixa.setValor( rs.getString( 16 ) == null ? "" : rs.getString( 16 ), i, 14 );
					tabBaixa.setValor( rs.getString( "ObsItPag" ) == null ? "" : rs.getString( "ObsItPag" ), i, 15 );
					vCodPed.addElement( rs.getString( "CodCompra" ) );
					vNParcBaixa.addElement( rs.getString( "NParcPag" ) );
					vNumContas.addElement( rs.getString( "NumConta" ) == null ? "" : rs.getString( "NumConta" ) );
					vCodPlans.addElement( rs.getString( "CodPlan" ) == null ? "" : rs.getString( "CodPlan" ) );
					vCodCCs.addElement( rs.getString( "CodCC" ) == null ? "" : rs.getString( "CodCC" ) );
				}

				rs.close();
				ps.close();

				txtTotalVencido.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotVencido.doubleValue(), Aplicativo.casasDecFin )) );
				txtTotalParcial.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotParcial.doubleValue(), Aplicativo.casasDecFin )) );
				txtTotalPago.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotPago.doubleValue(), Aplicativo.casasDecFin )) );
				txtTotalVencer.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotVencer.doubleValue(), Aplicativo.casasDecFin )) );
				txtTotalCancelado.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotCancelado.doubleValue(), Aplicativo.casasDecFin )) );

				con.commit();
			} catch ( SQLException err ) {
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao montar a tabela de baixa!\n" + err.getMessage(), true, con, err );
			} finally {
				ps = null;
				rs = null;
				sSQL = null;
			}
		}

		private void carregaGridManut() {

			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuffer sql = new StringBuffer();
			StringBuffer sWhereManut = new StringBuffer();
			StringBuffer sWhereStatus = new StringBuffer();

			Float bdTotVencido = 0.0f;
			Float bdTotParcial = 0.0f;
			Float bdTotPago = 0.0f;
			Float bdTotVencer = 0.0f;
			Float bdTotCancelado = 0.0f;

			try {

				if ( validaPeriodo() ) {

					tabManut.limpa();
					vNumContas.clear();
					vCodPlans.clear();
					vCodCCs.clear();
					vDtEmiss.clear();
					vCodPed.clear();


					if(txtCodPagManut.getVlrInteger()>0){
						sWhereManut.append( " and IT.CODEMP="+Aplicativo.iCodEmp+ " AND IT.CODFILIAL=" + lcPagManut.getCodFilial()+ " AND IT.CODPAG= " + txtCodPagManut.getVlrInteger()+ " " );
					
					}	
					else if(txtNumCheque.getVlrInteger()>0) {
						
						sWhereManut.append( " and exists ( " );
						sWhereManut.append( "select ch.numcheq from fnpagcheq pc, fncheque ch " );
						sWhereManut.append( "where ch.codemp=pc.codempch and ch.codfilial=pc.codfilialch and ch.seqcheq=pc.seqcheq " );
						sWhereManut.append( "and pc.codemp=it.codemp and pc.codfilial=it.codfilial and pc.codpag=it.codpag and pc.nparcpag=it.nparcpag " );
						sWhereManut.append( "and ch.numcheq="+txtNumCheque.getVlrString());
						sWhereManut.append( " ) " );
						
					}
					else {

						sWhereManut.append( " AND " );
						sWhereManut.append( "V".equals( rgData.getVlrString() ) ? "IT.DTVENCITPAG" : "IT.DTITPAG" );
						sWhereManut.append( " BETWEEN ? AND ? AND P.CODEMP=? AND P.CODFILIAL=?" );

						if ( "S".equals( cbPagas.getVlrString() ) || "S".equals( cbAPagar.getVlrString() ) || "S".equals( cbPagParcial.getVlrString() ) || "S".equals( cbCanceladas.getVlrString() ) ) {

							boolean bStatus = false;

							if ( "S".equals( cbPagas.getVlrString() ) ) {
								sWhereStatus.append( "IT.STATUSITPAG='PP'" );
								bStatus = true;
							}
							if ( "S".equals( cbAPagar.getVlrString() ) ) {
								sWhereStatus.append( bStatus ? " OR IT.STATUSITPAG='P1'" : " IT.STATUSITPAG='P1'" );
								bStatus = true;
							}
							if ( "S".equals( cbPagParcial.getVlrString() ) ) {
								sWhereStatus.append( bStatus ? " OR IT.STATUSITPAG='PL'" : " IT.STATUSITPAG='PL'" );
								bStatus = true;
							}
							if ( "S".equals( cbCanceladas.getVlrString() ) ) {
								sWhereStatus.append( bStatus ? " OR IT.STATUSITPAG='CP'" : " IT.STATUSITPAG='CP'" );
							}

							sWhereManut.append( " AND (" );
							sWhereManut.append( sWhereStatus );
							sWhereManut.append( ")" );
						}
						else {

							Funcoes.mensagemInforma( null, "Voc� deve selecionar ao menos um filtro de status!" );
							return;
						}

						if ( !"TT".equals( rgVenc.getVlrString() ) ) {

							sWhereManut.append( " AND IT.DTVENCITPAG" );

							if ( "VE".equals( rgVenc.getVlrString() ) ) {

								sWhereManut.append( " <'" );
								sWhereManut.append( Funcoes.dateToStrDB( new Date() ) );
								sWhereManut.append( "'" );
							}
							else {

								sWhereManut.append( " >='" );
								sWhereManut.append( Funcoes.dateToStrDB( new Date() ) );
								sWhereManut.append( "'" );
							}
						}

						if ( !"".equals( txtCodForManut.getText().trim() ) ) {

							sWhereManut.append( " AND P.CODFOR=" );
							sWhereManut.append( txtCodForManut.getText().trim() );
						}
						
						
						

					}

					sql.append( "select ");

					sql.append( "it.dtitpag, it.dtvencitpag, it.statusitpag, p.codfor, f.razfor, p.codpag, it.nparcpag, " );
					sql.append( "it.doclancaitpag, p.codcompra, it.vlrparcitpag, it.dtpagoitpag, it.vlrpagoitpag, " );
					sql.append( "it.vlrapagitpag, it.vlrdescitpag, it.vlrdevitpag, it.vlradicitpag, it.vlrjurositpag, " );
					sql.append( "it.codplan, it.codcc, it.dtitpag, p.docpag, " );

					sql.append( "coalesce(it.numconta,'') numconta, ");
					sql.append( "coalesce(it.codtipocob,'') codtipocob, ");
					sql.append( "coalesce(it.vlrcancitpag,0) vlrcancitpag, " );

					sql.append( "coalesce(( select pl.descplan from fnplanejamento pl " );
					sql.append( "where PL.CODPLAN=IT.CODPLAN AND PL.CODEMP=IT.CODEMPPN AND PL.CODFILIAL=IT.CODFILIALPN),'') descplan, ");

					sql.append( "coalesce(( select cc.desccc from fncc cc " );
					sql.append( "where CC.CODCC=IT.CODCC AND CC.CODEMP=IT.CODEMPCC AND CC.CODFILIAL=IT.CODFILIALCC AND CC.ANOCC=IT.ANOCC),'') DESCCC," );

					sql.append( "coalesce(IT.OBSITPAG,'') obsitpag, " );

					sql.append( "coalesce((select co.doccompra from cpcompra co " );				
					sql.append( "where co.codcompra=p.codcompra and co.codemp=p.codempcp and co.codfilial=p.codfilialcp),'') doccompra," );

					sql.append( "coalesce((SELECT T.DESCTIPOCOB FROM FNTIPOCOB T " );
					sql.append( "WHERE IT.CODEMPTC=T.CODEMP AND IT.CODFILIALTC=T.CODFILIAL AND IT.CODTIPOCOB=T.CODTIPOCOB),'') DESCTIPOCOB, sn.corsinal " );

					sql.append( "from fnpagar p, cpforneced f, fnitpagar it  " );
					
					sql.append( "LEFT OUTER JOIN FNSINAL SN ON SN.CODEMP=It.CODEMPSN AND SN.CODFILIAL=It.CODFILIALSN AND SN.CODSINAL=It.CODSINAL ");

					sql.append( "WHERE P.CODPAG=IT.CODPAG AND F.CODFOR=P.CODFOR AND F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR " );
					sql.append( sWhereManut );
					sql.append( " AND IT.CODEMP=P.CODEMP AND IT.CODFILIAL=P.CODFILIAL " );
					sql.append( "ORDER BY IT.DTVENCITPAG, IT.CODPAG, IT.NPARCPAG, IT.STATUSITPAG " );
					//sql.append( "ORDER BY IT.DTVENCITPAG " );

					try {

						ps = con.prepareStatement( sql.toString() );

						if(! (txtCodPagManut.getVlrInteger()>0) && !(txtNumCheque.getVlrInteger()>0)){

							ps.setDate( 1, Funcoes.dateToSQLDate( dIniManut ) );
							ps.setDate( 2, Funcoes.dateToSQLDate( dFimManut ) );
							ps.setInt( 3, Aplicativo.iCodEmp );
							ps.setInt( 4, ListaCampos.getMasterFilial( "FNPAGAR" ) );

						}

						rs = ps.executeQuery();

						double bdVlrAPagar = 0.0;
						double bdVlrPago = 0.0;

						System.out.println( sql.toString() );

						Vector<Cheque> cheques = null;
						
						for ( int i = 0; rs.next(); i++ ) {

							tabManut.adicLinha();

							bdVlrAPagar = Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrApagItPag" ) ).doubleValue();
							bdVlrPago = Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrPagoItPag" ) ).doubleValue();

							if ( "CP".equals( rs.getString( "StatusItPag" ) ) ) {
								imgColuna = imgCancelado;
								bdTotCancelado += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrCancItPag" ) ).floatValue();
							}
							else if ( "PP".equals( rs.getString( "StatusItPag" ) ) && bdVlrAPagar == 0.0 ) {
								imgColuna = imgPago;
								bdTotPago += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrPagoItPag" ) ).floatValue();
							}
							else if ( bdVlrPago > 0 ) {
								imgColuna = imgPagoParcial;
								bdTotParcial += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrPagoItPag" ) ).floatValue();
								
								if ( rs.getDate( "DtVencItPag" ).before( Calendar.getInstance().getTime() ) ) {
									bdTotVencido += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VLRApagItPag" ) ).floatValue();
								}
								else {
									bdTotVencer += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VLRApagItPag" ) ).floatValue();
								}

							}
							else if ( rs.getDate( "DtVencItPag" ).before( Calendar.getInstance().getTime() ) ) {
								imgColuna = imgVencido;
								bdTotVencido += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VLRPARCITPAG" ) ).floatValue();
							}
							else if ( rs.getDate( "DtVencItPag" ).after( Calendar.getInstance().getTime() ) ) {
								imgColuna = imgNaoVencido;
								bdTotVencer += Funcoes.strDecimalToBigDecimal( Aplicativo.casasDecFin, rs.getString( "VlrApagItPag" ) ).floatValue();
							}

							Color corsinal = rs.getString( "corsinal" ) == null ? null : new Color(rs.getInt( "corsinal" ));

							tabManut.setValor( imgColuna, i, enum_tab_manut.IMGSTATUS.ordinal(), corsinal );

							tabManut.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( enum_tab_manut.DTVENCITPAG.name() ) ), i, enum_tab_manut.DTVENCITPAG.ordinal(), corsinal );

							tabManut.setValor( rs.getString( enum_tab_manut.STATUSITPAG.name() ),	i, enum_tab_manut.STATUSITPAG.ordinal(), corsinal );
							tabManut.setValor( rs.getString( enum_tab_manut.CODFOR.name() ),	 	i, enum_tab_manut.CODFOR.ordinal(), corsinal );
							tabManut.setValor( rs.getString( enum_tab_manut.RAZFOR.name() ), 		i, enum_tab_manut.RAZFOR.ordinal(), corsinal );
							tabManut.setValor( rs.getString( enum_tab_manut.CODPAG.name() ), 		i, enum_tab_manut.CODPAG.ordinal(), corsinal );
							tabManut.setValor( rs.getString( enum_tab_manut.NPARCPAG.name() ), 		i, enum_tab_manut.NPARCPAG.ordinal(), corsinal );

							String doclanca = rs.getString( "DocLancaItPag" );
							String docpag = rs.getString( "DocPag" );
							String doc = "";

							if( doclanca == null ) {

								if(docpag != null ) {
									doc = rs.getString( "DocPag" ) + "/" + rs.getString( "NParcPag" );
								}

							}
							else {
								doc = doclanca;
							}

							tabManut.setValor( doc, i, enum_tab_manut.DOC.ordinal(), corsinal );

							tabManut.setValor( rs.getString( enum_tab_manut.DOCCOMPRA.name() ), i, enum_tab_manut.DOCCOMPRA.ordinal(), corsinal );

							tabManut.setValor( new StringDireita( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( "VlrParcItPag" ) ) ), i, enum_tab_manut.VLRPARCITPAG.ordinal(), corsinal );
							tabManut.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( enum_tab_manut.DTPAGOITPAG.name() ) ), i, enum_tab_manut.DTPAGOITPAG.ordinal(), corsinal );
							tabManut.setValor( new StringDireita( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( enum_tab_manut.VLRPAGOITPAG.name()  ) )), i, 	enum_tab_manut.VLRPAGOITPAG.ordinal(), corsinal );
							tabManut.setValor( new StringDireita( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( enum_tab_manut.VLRDESCITPAG.name() ) )), i, 	enum_tab_manut.VLRDESCITPAG.ordinal(), corsinal );
							tabManut.setValor( new StringDireita( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( enum_tab_manut.VLRJUROSITPAG.name() ) )), i, 	enum_tab_manut.VLRJUROSITPAG.ordinal(), corsinal );
							tabManut.setValor( new StringDireita( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( enum_tab_manut.VLRDEVITPAG.name() ) )), i, 	enum_tab_manut.VLRDEVITPAG.ordinal(), corsinal );
							tabManut.setValor( new StringDireita( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( enum_tab_manut.VLRADICITPAG.name() ) )), i, 	enum_tab_manut.VLRADICITPAG.ordinal(), corsinal );
							tabManut.setValor( new StringDireita( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( enum_tab_manut.VLRAPAGITPAG.name() ) )), i, 	enum_tab_manut.VLRAPAGITPAG.ordinal(), corsinal );
							tabManut.setValor( new StringDireita( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs.getString( enum_tab_manut.VLRCANCITPAG.name() ) )), i, 	enum_tab_manut.VLRCANCITPAG.ordinal(), corsinal );

							tabManut.setValor( rs.getString( enum_tab_manut.NUMCONTA.name() 	) 	, i, enum_tab_manut.NUMCONTA.ordinal(), corsinal );
							tabManut.setValor( rs.getString( enum_tab_manut.DESCPLAN.name() 	) 	, i, enum_tab_manut.DESCPLAN.ordinal(), corsinal );
							tabManut.setValor( rs.getString( enum_tab_manut.DESCCC.name() 		) 	, i, enum_tab_manut.DESCCC.ordinal(), corsinal );
							tabManut.setValor( rs.getString( enum_tab_manut.CODTIPOCOB.name()	) 	, i, enum_tab_manut.CODTIPOCOB.ordinal(), corsinal );
							tabManut.setValor( rs.getString( enum_tab_manut.DESCTIPOCOB.name() 	) 	, i, enum_tab_manut.DESCTIPOCOB.ordinal(), corsinal );						
							tabManut.setValor( rs.getString( enum_tab_manut.OBSITPAG.name() 	) 	, i, enum_tab_manut.OBSITPAG.ordinal(), corsinal );

							tabManut.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( enum_tab_manut.DTITPAG.name()) ) 	, i, enum_tab_manut.DTITPAG.ordinal(), corsinal );
							tabManut.setValor( rs.getString( enum_tab_manut.CODCC.name() 		) 	, i, enum_tab_manut.CODCC.ordinal(), corsinal );
							tabManut.setValor( rs.getString( enum_tab_manut.CODPLAN.name() 		) 	, i, enum_tab_manut.CODPLAN.ordinal(), corsinal );
							tabManut.setValor( rs.getString( enum_tab_manut.CODCOMPRA.name() 	) 	, i, enum_tab_manut.CODCOMPRA.ordinal(), corsinal );
							tabManut.setValor( rs.getString( enum_tab_manut.NUMCONTA.name() 	) 	, i, enum_tab_manut.NUMCONTA.ordinal(), corsinal );

							vCodPed.addElement( rs.getString( enum_tab_manut.CODCOMPRA.name()  ) );
							vNumContas.addElement( rs.getString( enum_tab_manut.NUMCONTA.name() ) );
							vCodPlans.addElement( rs.getString( enum_tab_manut.CODPLAN.name() ) );
							vCodCCs.addElement( rs.getString( enum_tab_manut.CODCC.name() ) );
							vDtEmiss.addElement( Funcoes.dateToStrDate( rs.getDate( enum_tab_manut.DTITPAG.name() ) ) );
							
							cheques = DLEditaPag.buscaCheques( rs.getInt( enum_tab_manut.CODPAG.name()), rs.getInt( enum_tab_manut.NPARCPAG.name() ), con);
							
							if(cheques !=null && cheques.size()>0) {
								
								Vector<String> numcheques = new Vector<String>();
								
								for ( int ic = 0; cheques.size() > ic; ic++ ) {

									Cheque cheque = (Cheque) cheques.get( ic );

									numcheques.add( cheque.getNumcheq().toString() );

								}
								
								tabManut.setValor( numcheques, i, enum_tab_manut.CHEQUES.ordinal(), corsinal );
								
								//tabManut.setValor( "", i, enum_tab_manut.CHEQUES.ordinal(), corsinal );
								
							}
							else {
								tabManut.setValor( "", i, enum_tab_manut.CHEQUES.ordinal(), corsinal );
							}
							
							

						}

						rs.close();
						ps.close();

						txtTotalVencido.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotVencido.doubleValue(), Aplicativo.casasDecFin )) );
						txtTotalParcial.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotParcial.doubleValue(), Aplicativo.casasDecFin ) ));
						txtTotalPago.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotPago.doubleValue(), Aplicativo.casasDecFin ) ));
						txtTotalVencer.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotVencer.doubleValue(), Aplicativo.casasDecFin ) ));
						txtTotalCancelado.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble( bdTotCancelado.doubleValue(), Aplicativo.casasDecFin ) ));

						con.commit();

					} 
					catch ( SQLException err ) {
						err.printStackTrace();
						Funcoes.mensagemErro( this, "Erro ao montar a tabela de manuten��o!\n" + err.getMessage(), true, con, err );
					}
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			} finally {
				ps = null;
				rs = null;
				sql = null;
				sWhereManut = null;
				sWhereStatus = null;
			}
		}

		private void baixaConsulta() {

			if ( tabConsulta.getLinhaSel() != -1 ) {

				txtCodPagBaixa.setVlrString( vCodPag.elementAt( tabConsulta.getLinhaSel() ) );

				int iNParc = ( new Integer( vNParcPag.elementAt( tabConsulta.getLinhaSel() ) ) ).intValue();

				lcPagBaixa.carregaDados();
				tpn.setSelectedIndex( 1 );

				btBaixa.requestFocus();

				for ( int i = 0; i < vNParcBaixa.size(); i++ ) {

					if ( iNParc == ( new Integer( vNParcBaixa.elementAt( i ) ) ).intValue() ) {

						tabBaixa.setLinhaSel( i );
						break;
					}
				}
			}
		}

		private void baixar( char cOrig ) {

			PreparedStatement ps = null;
			StringBuffer sSQL = new StringBuffer();
			String[] sVals = null;
			String[] sRelPlanPag = null;
			String[] sRets = null;
			DLBaixaPag dl = null;
			ImageIcon imgStatusAt = null;
			// ObjetoHistorico historico = null;
			Integer codhistpag = null;

			try {

				/*
				 * codhistpag = (Integer) prefere.get( "codhistpag" );
				 * 
				 * if(codhistpag != 0) { historico = new ObjetoHistorico(codhistpag,con); } else { historico = new ObjetoHistorico(); historico.setHistoricocodificado( HISTORICO_PADRAO ); }
				 */

				if ( ( cOrig == 'M' ) && ( tabManut.getLinhaSel() > -1 ) ) { // Quando a fun��o eh chamada da tab MANUTEN��O

					imgStatusAt = ( (ImageIcon) tabManut.getValor( tabManut.getLinhaSel(), 0 ) );

					if ( imgStatusAt == imgPago ) {

						Funcoes.mensagemInforma( this, "A PARCELA J� FOI PAGA!" );
						return;
					}

					int iLin = tabManut.getLinhaSel();

					iCodPag = Integer.parseInt( (String) tabManut.getValor( iLin, enum_tab_manut.CODPAG.ordinal() ) );
					iNParcPag = Integer.parseInt( (String) tabManut.getValor( iLin, enum_tab_manut.NPARCPAG.ordinal() ) );

					sVals = new String[ 13 ];

					sRelPlanPag = buscaRelPlanPag( Integer.parseInt( (String) tabManut.getValor( iLin, enum_tab_manut.CODPAG.ordinal() ) ) );
					sRets = null;

					dl = new DLBaixaPag( this );

					sVals[ 0 ] = (String) tabManut.getValor( iLin, enum_tab_manut.CODFOR.ordinal() ) ;
					sVals[ 1 ] = (String) tabManut.getValor( iLin, enum_tab_manut.RAZFOR.ordinal() );

					sVals[ 2 ] = "".equals( vNumContas.elementAt( iLin ) ) ? sRelPlanPag[ 2 ] : vNumContas.elementAt( iLin );				
					sVals[ 3 ] = "".equals( vCodPlans.elementAt( iLin ) ) ? sRelPlanPag[ 1 ] : vCodPlans.elementAt( iLin );
					sVals[ 4 ] = "".equals( (String) tabManut.getValor( iLin, enum_tab_manut.DOC.ordinal() ) ) ? (String) tabManut.getValor( iLin, enum_tab_manut.DOCCOMPRA.ordinal() ) : (String) tabManut.getValor( iLin, enum_tab_manut.DOC.ordinal() );				

					sVals[ 5 ] = (String) tabManut.getValor( iLin, enum_tab_manut.DTVENCITPAG.ordinal() );
					sVals[ 6 ] = (String) tabManut.getValor( iLin, enum_tab_manut.DTVENCITPAG.ordinal() );
					sVals[ 7 ] = ((StringDireita) tabManut.getValor( iLin, enum_tab_manut.VLRPARCITPAG.ordinal()) ).toString();
					sVals[ 8 ] = Funcoes.dateToStrDate( new Date() );

					sVals[ 9 ] = ((StringDireita) tabManut.getValor( iLin, enum_tab_manut.VLRAPAGITPAG.ordinal() )).toString();
					sVals[ 10 ] = "".equals( vCodCCs.elementAt( iLin ) ) ? sRelPlanPag[ 3 ] : vCodCCs.elementAt( iLin );
					sVals[ 11 ] = (String) tabManut.getValor( iLin, enum_tab_manut.CODTIPOCOB.ordinal() );

					sVals[ 12 ] = (String) tabManut.getValor( iLin, enum_tab_manut.OBSITPAG.ordinal() );

					dl.setValores( sVals );
					dl.setConexao( con );
					dl.setVisible( true );

					if ( dl.OK ) {

						sRets = dl.getValores();

						sSQL.append( "UPDATE FNITPAGAR SET " );
						sSQL.append( "NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," );
						sSQL.append( "DOCLANCAITPAG=?,DTPAGOITPAG=?,VLRPAGOITPAG=?,ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?," );
						sSQL.append( "CODTIPOCOB=?,CODEMPTC=?,CODFILIALTC=?,OBSITPAG=?,STATUSITPAG='PP' " );
						sSQL.append( "WHERE CODPAG=? AND NPARCPAG=? AND CODEMP=? AND CODFILIAL=?" );

						try {

							ps = con.prepareStatement( sSQL.toString() );
							ps.setString( 1, sRets[ 0 ] );
							ps.setInt( 2, Aplicativo.iCodEmp );
							ps.setInt( 3, ListaCampos.getMasterFilial( "FNCONTA" ) );
							ps.setString( 4, sRets[ 1 ] );
							ps.setInt( 5, Aplicativo.iCodEmp );
							ps.setInt( 6, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
							ps.setString( 7, sRets[ 2 ] );
							ps.setDate( 8, Funcoes.strDateToSqlDate( sRets[ 3 ] ) );
							ps.setBigDecimal( 9, ConversionFunctions.stringCurrencyToBigDecimal( sRets[ 4 ] ) );

							if ( "".equals( sRets[ 5 ].trim() ) ) {
								ps.setNull( 10, Types.INTEGER );
								ps.setNull( 11, Types.CHAR );
								ps.setNull( 12, Types.INTEGER );
								ps.setNull( 13, Types.INTEGER );
							}
							else {
								ps.setInt( 10, iAnoCC );
								ps.setString( 11, sRets[ 5 ] );
								ps.setInt( 12, Aplicativo.iCodEmp );
								ps.setInt( 13, ListaCampos.getMasterFilial( "FNCC" ) );
							}
							if ( "".equals( sRets[ 6 ].trim() ) ) {
								ps.setNull( 14, Types.INTEGER );
								ps.setNull( 15, Types.INTEGER );
								ps.setNull( 16, Types.INTEGER );
							}
							else {
								ps.setInt( 14, Integer.parseInt( sRets[ 6 ] ) );
								ps.setInt( 15, Aplicativo.iCodEmp );
								ps.setInt( 16, ListaCampos.getMasterFilial( "FNTIPOCOB" ) );
							}

							ps.setString( 17, sRets[ 7 ] );
							ps.setInt( 18, iCodPag );
							ps.setInt( 19, iNParcPag );
							ps.setInt( 20, Aplicativo.iCodEmp );
							ps.setInt( 21, ListaCampos.getMasterFilial( "FNPAGAR" ) );

							ps.executeUpdate();

							con.commit();
						} catch ( SQLException err ) {
							err.printStackTrace();
							Funcoes.mensagemErro( this, "Erro ao baixar parcela!\n" + err.getMessage(), true, con, err );
						}
					}

					dl.dispose();
					carregaGridManut();
				}
				else if ( ( cOrig == 'B' ) & ( tabBaixa.getLinhaSel() > -1 ) ) { // Quando a fun��o eh chamada da tab BAIXAR

					imgStatusAt = ( (ImageIcon) tabBaixa.getValor( tabBaixa.getLinhaSel(), 0 ) );

					if ( imgStatusAt == imgPago ) {

						Funcoes.mensagemInforma( this, "A PARCELA J� FOI PAGA!" );
						return;
					}

					int iLin = tabBaixa.getLinhaSel();

					iCodPag = txtCodPagBaixa.getVlrInteger().intValue();
					iNParcPag = Integer.parseInt( (String) tabBaixa.getValor( iLin, 2 ) );

					sVals = new String[ 13 ];
					sRelPlanPag = buscaRelPlanPag( txtCodPagBaixa.getVlrInteger().intValue() );

					dl = new DLBaixaPag( this );

					sVals[ 0 ] = txtCodForBaixa.getVlrString();
					sVals[ 1 ] = txtRazForBaixa.getVlrString();
					sVals[ 2 ] = "".equals( vNumContas.elementAt( iLin ) ) ? sRelPlanPag[ 2 ] : vNumContas.elementAt( iLin );
					sVals[ 3 ] = "".equals( vCodPlans.elementAt( iLin ) ) ? sRelPlanPag[ 1 ] : vCodPlans.elementAt( iLin );
					//				sVals[ 4 ] = txtDoc.getVlrString();
					sVals[ 4 ] = (String) tabBaixa.getValor( iLin, 3 ); 

					sVals[ 5 ] = txtDtEmisBaixa.getVlrString();
					sVals[ 6 ] = (String) tabBaixa.getValor( iLin, 1 );
					sVals[ 7 ] = (String) tabBaixa.getValor( iLin, 5 );
					sVals[ 8 ] = Funcoes.dateToStrDate( new Date() );
					sVals[ 10 ] = "".equals( vCodCCs.elementAt( iLin ) ) ? sRelPlanPag[ 3 ] : vCodCCs.elementAt( iLin );

					if ( "".equals( ( (String) tabBaixa.getValor( iLin, 6 ) ).trim() ) ) {

						/*
						 * if ( "".equals( ( (String) tabBaixa.getValor( iLin, 15 ) ).trim() ) ) { historico.setData( txtDtEmisBaixa.getVlrDate() ); historico.setDocumento( sVals[ 4 ] ); historico.setPortador( sVals[ 1 ] ); historico.setValor( Funcoes.strToBd( tabBaixa.getValor( iLin, 5 ).toString() ));
						 * sVals[ 12 ] = historico.getHistoricodecodificado(); } else { sVals[ 12 ] = (String) tabBaixa.getValor( iLin, 15 ); }
						 */

						sVals[ 9 ] = (String) tabBaixa.getValor( iLin, 5 );
					}
					else {
						sVals[ 11 ] = (String) tabBaixa.getValor( iLin, 14 );
						sVals[ 9 ] = (String) tabBaixa.getValor( iLin, 7 );
					}
					sVals[ 12 ] = (String) tabBaixa.getValor( iLin, 15 );

					dl.setValores( sVals );
					dl.setConexao( con );
					dl.setVisible( true );

					if ( dl.OK ) {

						sRets = dl.getValores();

						sSQL.append( "UPDATE FNITPAGAR " );
						sSQL.append( "SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," );
						sSQL.append( "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITPAG =?,DTPAGOITPAG=?,VLRPAGOITPAG=?," );
						sSQL.append( "OBSITPAG=?,STATUSITPAG='PP' " );
						sSQL.append( "WHERE CODPAG=? AND NPARCPAG=? AND CODEMP=? AND CODFILIAL=?" );

						try {

							ps = con.prepareStatement( sSQL.toString() );
							ps.setString( 1, sRets[ 0 ] );
							ps.setInt( 2, Aplicativo.iCodEmp );
							ps.setInt( 3, ListaCampos.getMasterFilial( "FNCONTA" ) );
							ps.setString( 4, sRets[ 1 ] );
							ps.setInt( 5, Aplicativo.iCodEmp );
							ps.setInt( 6, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );

							if ( "".equals( sRets[ 5 ].trim() ) ) {

								ps.setNull( 7, Types.INTEGER );
								ps.setNull( 8, Types.CHAR );
								ps.setNull( 9, Types.INTEGER );
								ps.setNull( 10, Types.INTEGER );
							}
							else {

								ps.setInt( 7, iAnoCC );
								ps.setString( 8, sRets[ 5 ] );
								ps.setInt( 9, Aplicativo.iCodEmp );
								ps.setInt( 10, ListaCampos.getMasterFilial( "FNCC" ) );
							}

							ps.setString( 11, sRets[ 2 ] );
							ps.setDate( 12, Funcoes.strDateToSqlDate( sRets[ 3 ] ) );
							ps.setBigDecimal( 13, ConversionFunctions.stringCurrencyToBigDecimal( sRets[ 4 ] ) );
							ps.setString( 14, sRets[ 7 ] );
							ps.setInt( 15, iCodPag );
							ps.setInt( 16, iNParcPag );
							ps.setInt( 17, Aplicativo.iCodEmp );
							ps.setInt( 18, ListaCampos.getMasterFilial( "FNPAGAR" ) );

							ps.executeUpdate();

							con.commit();
						} catch ( SQLException err ) {
							err.printStackTrace();
							Funcoes.mensagemErro( this, "Erro ao baixar parcela!\n" + err.getMessage(), true, con, err );
						}
					}

					carregaGridBaixa();
					dl.dispose();
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			} finally {
				ps = null;
				sSQL = null;
				sVals = null;
				sRelPlanPag = null;
				sRets = null;
				dl = null;
				imgStatusAt = null;
			}
		}

		private void editar() {

			PreparedStatement ps = null;
			StringBuffer sql = new StringBuffer();
			String[] sVals = null;
			String[] sRets = null;
			DLEditaPag dl = null;
			ImageIcon imgStatusAt = null;
			int iLin;
			// ObjetoHistorico historico = null;
			Integer codhistpag = null;

			try {

				if ( tabManut.getLinhaSel() > -1 ) {

					codhistpag = (Integer) prefere.get( "codhistpag" );

					imgStatusAt = (ImageIcon) tabManut.getValor( tabManut.getLinhaSel(), 0 );

					iLin = tabManut.getLinhaSel();

					iCodPag = Integer.parseInt( (String) tabManut.getValor( iLin, enum_tab_manut.CODPAG.ordinal() ) );
					iNParcPag = Integer.parseInt( (String) tabManut.getValor( iLin, enum_tab_manut.NPARCPAG.ordinal() ) );

					sVals = new String[ 17 ];

					dl = new DLEditaPag( this, imgStatusAt != imgPago );

					sVals[ 0 ] = (String) tabManut.getValor( iLin, enum_tab_manut.CODFOR.ordinal() ).toString();
					sVals[ 1 ] = (String) tabManut.getValor( iLin, enum_tab_manut.RAZFOR.ordinal() ).toString();

					sVals[ 2 ] = (String) tabManut.getValor( iLin, enum_tab_manut.NUMCONTA.ordinal() ).toString();
					sVals[ 3 ] = (String) tabManut.getValor( iLin, enum_tab_manut.CODPLAN.ordinal() ).toString();
					sVals[ 4 ] = (String) tabManut.getValor( iLin, enum_tab_manut.CODCC.ordinal() ).toString();

					sVals[ 5 ] = (String) tabManut.getValor( iLin, enum_tab_manut.DOC.ordinal() ).toString();
					sVals[ 6 ] = (String) tabManut.getValor( iLin, enum_tab_manut.DTITPAG.ordinal() ).toString();					
					sVals[ 7 ] = (String) tabManut.getValor( iLin, enum_tab_manut.DTVENCITPAG.ordinal() ).toString();

					sVals[ 8 ] = 	( (StringDireita) tabManut.getValor( iLin, enum_tab_manut.VLRPARCITPAG.ordinal() )).toString();
					sVals[ 9 ] = 	( (StringDireita) tabManut.getValor( iLin, enum_tab_manut.VLRJUROSITPAG.ordinal() )).toString();
					sVals[ 10 ] = 	( (StringDireita) tabManut.getValor( iLin, enum_tab_manut.VLRDESCITPAG.ordinal() )).toString(); 
					sVals[ 11 ] = 	( (StringDireita) tabManut.getValor( iLin, enum_tab_manut.VLRADICITPAG.ordinal() )).toString(); 

					sVals[ 12 ] = (String) tabManut.getValor( iLin, enum_tab_manut.OBSITPAG.ordinal() ).toString();
					sVals[ 13 ] = (String) tabManut.getValor( iLin, enum_tab_manut.CODTIPOCOB.ordinal() ).toString();
					sVals[ 14 ] = (String) tabManut.getValor( iLin, enum_tab_manut.VLRDEVITPAG.ordinal() ).toString();

					// Cod. pagar e nparc para carregar lista de cheques
					sVals[ 15 ] = (String) tabManut.getValor( iLin, enum_tab_manut.CODPAG.ordinal() ).toString();
					sVals[ 16 ] = (String) tabManut.getValor( iLin, enum_tab_manut.NPARCPAG.ordinal() ).toString();

					// Se o doccompra estiver em branco getvalor(8) quer dizer que o lan�amento foi feito pelo usu�rio.
					dl.setValores( sVals, "".equals( tabManut.getValor( iLin, enum_tab_manut.DOCCOMPRA.ordinal() ).toString().trim() ) );

					dl.setConexao( con );
					dl.setVisible( true );

					if ( dl.OK && imgStatusAt != imgPago) {
						
						try {
							
							dl.salvaPag();
					
							
						} catch ( Exception err ) {
							Funcoes.mensagemErro( this, "Erro ao editar parcela!\n" + err.getMessage(), true, con, err );
							err.printStackTrace();
						}
						//				}

						dl.dispose();
						carregaGridManut();
					}
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			} finally {
				ps = null;
				sql = null;
				sVals = null;
				sRets = null;
				dl = null;
				imgStatusAt = null;
			}
		}

		private void novo() {

			DLNovoPag dl = new DLNovoPag( this );
			dl.setConexao( con );
			dl.setVisible( true );
			dl.dispose();
			carregaGridManut();
		}

		private void excluir() {

			PreparedStatement ps = null;
			ImageIcon imgStatusAt = null;

			try {

				if ( tabManut.getLinhaSel() > -1 ) {

					imgStatusAt = (ImageIcon) tabManut.getValor( tabManut.getLinhaSel(), 0 );

					if ( ! ( imgStatusAt == imgPagoParcial || imgStatusAt == imgPago ) ) {

						if ( Funcoes.mensagemConfirma( this, "Deseja realmente excluir esta conta e todas as suas parcelas?" ) == 0 ) {

							try {

								ps = con.prepareStatement( "DELETE FROM FNPAGAR WHERE CODPAG=? AND CODEMP=? AND CODFILIAL=?" );
								ps.setInt( 1, Integer.parseInt( (String) tabManut.getValor( tabManut.getLinhaSel(), enum_tab_manut.CODPAG.ordinal() ) ) );
								ps.setInt( 2, Aplicativo.iCodEmp );
								ps.setInt( 3, ListaCampos.getMasterFilial( "FNPAGAR" ) );

								ps.executeUpdate();

								con.commit();

								carregaGridManut();

							} catch ( SQLException err ) {
								err.printStackTrace();
								Funcoes.mensagemErro( this, "Erro ao excluir parcela!\n" + err.getMessage(), true, con, err );
							}
						}
					}
					else {
						Funcoes.mensagemErro( this, "N�O FOI POSSIVEL EXCLUIR.\n" + "A PARCELA J� FOI PAGA." );
					}
				}
				else {
					Funcoes.mensagemInforma( this, "Nenhum item foi selecionado." );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			} finally {
				ps = null;
				imgStatusAt = null;
			}
		}

		private void estorno() {

			PreparedStatement ps = null;
			ImageIcon imgStatusAt = null;

			try {

				if ( tabManut.getLinhaSel() > -1 ) {

					imgStatusAt = (ImageIcon) tabManut.getValor( tabManut.getLinhaSel(), 0 );

					if ( ( ( imgStatusAt == imgPagoParcial || imgStatusAt == imgPago ) ) ) {

						if ( Funcoes.mensagemConfirma( this, "Confirma o estorno do lan�amento?" ) == JOptionPane.YES_OPTION ) {

							int iLin = tabManut.getLinhaSel();

							iCodPag = Integer.parseInt( (String) tabManut.getValor( iLin, enum_tab_manut.CODPAG.ordinal() ) );
							iNParcPag = Integer.parseInt( (String) tabManut.getValor( iLin, enum_tab_manut.NPARCPAG.ordinal() ) );

							try {

								ps = con.prepareStatement( "UPDATE FNITPAGAR SET STATUSITPAG='P1' WHERE CODPAG=? AND NPARCPAG=? AND CODEMP=? AND CODFILIAL=?" );
								ps.setInt( 1, iCodPag );
								ps.setInt( 2, iNParcPag );
								ps.setInt( 3, Aplicativo.iCodEmp );
								ps.setInt( 4, ListaCampos.getMasterFilial( "FNPAGAR" ) );

								ps.executeUpdate();

								con.commit();
							} catch ( SQLException err ) {
								err.printStackTrace();
								Funcoes.mensagemErro( this, "Erro ao estornar registro!\n" + err.getMessage(), true, con, err );
							}

							carregaGridManut();
						}
					}
					else {
						Funcoes.mensagemInforma( this, "PARCELA AINDA N�O FOI PAGA." );
					}
				}
				else {
					Funcoes.mensagemInforma( this, "Nenhum item foi selecionado." );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			} finally {
				ps = null;
				imgStatusAt = null;
			}
		}

		private boolean validaPeriodo() {

			boolean bRetorno = false;

			if ( txtDatainiManut.getText().trim().length() < 10 ) {

				Funcoes.mensagemInforma( this, "Data inicial inv�lida!" );
			}
			else if ( txtDatafimManut.getText().trim().length() < 10 ) {

				Funcoes.mensagemInforma( this, "Data final inv�lida!" );
			}
			else if ( txtDatafimManut.getVlrDate().before( txtDatainiManut.getVlrDate() ) ) {

				Funcoes.mensagemInforma( this, "Data inicial maior que a data final!" );
			}
			else {

				dIniManut = txtDatainiManut.getVlrDate();
				dFimManut = txtDatafimManut.getVlrDate();
				bRetorno = true;
			}

			return bRetorno;
		}

		private String[] buscaRelPlanPag( int iCodPag ) {

			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuffer sSQL = new StringBuffer();
			String[] retorno = new String[ 4 ];

			try {

				sSQL.append( " SELECT C.CODPLANOPAG, PP.CODPLAN, PP.NUMCONTA, PP.CODCC " );
				sSQL.append( "FROM CPCOMPRA C, FNPLANOPAG PP, FNPAGAR P " );
				sSQL.append( "WHERE C.CODEMPPG=PP.CODEMP AND C.CODFILIALPG=PP.CODFILIAL AND C.CODPLANOPAG=PP.CODPLANOPAG " );
				sSQL.append( "AND C.CODEMP=P.CODEMPCP AND C.CODFILIAL=P.CODFILIALCP AND C.CODCOMPRA=P.CODCOMPRA " );
				sSQL.append( "AND P.CODEMP=? AND P.CODFILIAL=? AND P.CODPAG=?" );

				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "FNPAGAR" ) );
				ps.setInt( 3, iCodPag );

				rs = ps.executeQuery();

				if ( rs.next() ) {

					for ( int i = 0; i < retorno.length; i++ ) {

						retorno[ i ] = rs.getString( i + 1 ) == null ? "" : rs.getString( i + 1 );
					}
				}

				rs.close();
				ps.close();

				con.commit();
			} catch ( SQLException err ) {
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao buscar Conta!\n" + err.getMessage(), true, con, err );
			} finally {
				ps = null;
				rs = null;
				sSQL = null;
			}

			return retorno;
		}

		/*
		 * private int buscaAnoBaseCC() {
		 * 
		 * PreparedStatement ps = null; ResultSet rs = null; int iRet = 0;
		 * 
		 * try {
		 * 
		 * ps = con.prepareStatement( "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" ); ps.setInt( 1, Aplicativo.iCodEmp ); ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
		 * 
		 * rs = ps.executeQuery();
		 * 
		 * if ( rs.next() ) { iRet = rs.getInt( "ANOCENTROCUSTO" ); }
		 * 
		 * rs.close(); ps.close();
		 * 
		 * if ( ! con.getAutoCommit() ) { con.commit(); } } catch ( SQLException err ) { err.printStackTrace(); Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err ); } finally { ps = null; rs = null; }
		 * 
		 * return iRet; }
		 */

		private Map<String, Integer> getPrefere() {

			PreparedStatement ps = null;
			ResultSet rs = null;
			Integer anocc = null;
			Integer codhistpag = null;

			Map<String, Integer> retorno = new HashMap<String, Integer>();

			try {

				ps = con.prepareStatement( "SELECT ANOCENTROCUSTO,CODHISTPAG FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

				rs = ps.executeQuery();

				if ( rs.next() ) {
					anocc = rs.getInt( "ANOCENTROCUSTO" );
					codhistpag = rs.getInt( "CODHISTPAG" );
				}

				retorno.put( "codhistpag", codhistpag );
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

		public void beforeCarrega( CarregaEvent cevt ) {

		}

		public void afterCarrega( CarregaEvent cevt ) {

			tabBaixa.limpa();
			carregaGridBaixa();

			if ( cevt.getListaCampos() == lcFor ) {

				carregaConsulta();
			}
			if ( cevt.getListaCampos() == lcPagManut ) {
				carregaGridManut();
				txtNumCheque.setVlrString( "" );
			}
		}

		public void actionPerformed( ActionEvent evt ) {

			if ( evt.getSource() == btSair ) {
				dispose();
			}
			else if ( evt.getSource() == btBaixaConsulta ) {
				baixaConsulta();
			}
			else if ( evt.getSource() == btBaixa ) {
				baixar( 'B' );
			}
			else if ( evt.getSource() == btBaixaManut ) {
				baixar( 'M' );
			}
			else if ( evt.getSource() == btEditManut ) {
				editar();
			}
			else if ( evt.getSource() == btNovoManut ) {
				novo();
			}
			else if ( evt.getSource() == btExcluirManut ) {
				excluir();
			}
			else if ( evt.getSource() == btExecManut ) {
				carregaGridManut();
			}
			else if ( evt.getSource() == btEstManut ) {
				estorno();
			}
			else if ( evt.getSource() == btCancItem ) {
				cancelaItem();
			}
			else if ( evt.getSource() == btImpRec ) {
				impRecibo();
			}
			else if(evt.getSource() instanceof JMenuItem) {
				
				JMenuItem menu = (JMenuItem) evt.getSource();
				
				String opcao  = menu.getText();
				
				Integer codsinal = null;
				
				if(menu != menucancelacor && menu != menucadastracor ) {
				
					codsinal = Integer.parseInt( opcao.substring( 0, opcao.indexOf( "-" ) ));
					
				}
				else if (evt.getSource() == menucadastracor){
					
					if (Funcoes.verificaAcessoClasse(FSinalizadores.class.getCanonicalName())) {
						Aplicativo.getInstace().abreTela("Sinalizadores", FSinalizadores.class);
					}
					else {
						Funcoes.mensagemInforma(null, "O usu�rio " + Aplicativo.strUsuario + " n�o possui acesso a tela solicitada (" + FSinalizadores.class.getName()
								+ ").\nSolicite a libera��o do acesso ao administrador do sistema.");
					}
					
					return;
				}
				
				
				atualizaCor( codsinal, 
						Integer.parseInt( tabManut.getValor( tabManut.getLinhaSel(), enum_tab_manut.CODPAG.ordinal() ).toString() ), 
						Integer.parseInt( tabManut.getValor( tabManut.getLinhaSel(), enum_tab_manut.NPARCPAG.ordinal() ).toString()) );
	 			
				carregaGridManut();
								
			}
			

		}

		public void stateChanged( ChangeEvent cevt ) {

			if ( tpn.getSelectedIndex() == 0 ) {
				carregaConsulta();
			}
			if ( tpn.getSelectedIndex() == 1 ) {
				lcPagBaixa.carregaDados();
			}
		}

		private void cancelaItem() {

			String sit = "";
			int sel = tabManut.getSelectedRow();
			int codpag = 0;
			int nparcitpag = 0;
			if ( sel < 0 ) {
				Funcoes.mensagemInforma( this, "Selecione um t�tulo!" );
			}
			else {
				sit = tabManut.getValor( sel, 2 ).toString();
				if ( "P1".equals( sit ) ) {
					if ( Funcoes.mensagemConfirma( this, "Confirma cancelamento do t�tulo?" ) == JOptionPane.YES_OPTION ) {
						DLCancItem dlCanc = new DLCancItem( this );
						dlCanc.setVisible( true );
						if ( dlCanc.OK ) {
							codpag = ( Integer.parseInt( tabManut.getValor( sel, enum_tab_manut.CODPAG.ordinal() ).toString() ) );
							nparcitpag = ( Integer.parseInt( tabManut.getValor( sel, enum_tab_manut.NPARCPAG.ordinal() ).toString() ) );
							execCancItem( codpag, nparcitpag, dlCanc.getValor() );
							carregaGridManut();
						}
					}
				}
				else if ( "CP".equals( sit ) ) {
					Funcoes.mensagemInforma( this, "T�tulo j� est� cancelado!" );
				}
				else {
					Funcoes.mensagemInforma( this, "Situa��o do t�tulo n�o permite cancelamento!" );
				}
			}
		}

		private void execCancItem( int codpag, int nparcitpag, String obs ) {

			StringBuilder sql = new StringBuilder( "UPDATE FNITPAGAR SET STATUSITPAG='CP', OBSITPAG=? " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODPAG=? AND NPARCPAG=? " );
			try {
				PreparedStatement ps = con.prepareStatement( sql.toString() );
				ps.setString( 1, obs );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "FNITPAGAR" ) );
				ps.setInt( 4, codpag );
				ps.setInt( 5, nparcitpag );
				ps.executeUpdate();
				ps.close();
				con.commit();
			} catch ( SQLException e ) {
				Funcoes.mensagemErro( this, "N�o foi poss�vel efetuar o cancelamento!\n" + e.getMessage() );
			}

		}

		@ SuppressWarnings ( "unchecked" )
		private void impRecibo() {

			DLImpReciboPag dl = null;

			if ( tabManut.getLinhaSel() < 0 ) {
				Funcoes.mensagemInforma( this, "Selecione uma parcela no grid!" );
				return;
			}

			dl = new DLImpReciboPag( this, con, Integer.parseInt( tabManut.getValor( tabManut.getLinhaSel(), enum_tab_manut.CODPAG.ordinal() ).toString()), 
												Integer.parseInt( tabManut.getValor( tabManut.getLinhaSel(), enum_tab_manut.NPARCPAG.ordinal() ).toString()),
												(Vector<String>) tabManut.getValor( tabManut.getLinhaSel(), enum_tab_manut.CHEQUES.ordinal()));

			dl.setVisible( true );

			if ( dl.OK ) {

				dl.imprimir();
			}
		}

		public void setConexao( DbConnection cn ) {

			super.setConexao( cn );
			lcFor.setConexao( cn );
			lcForBaixa.setConexao( cn );
			lcForManut.setConexao( cn );
			lcForManut2.setConexao( cn );
			lcCompraBaixa.setConexao( cn );
			lcBancoBaixa.setConexao( cn );
			lcPagBaixa.setConexao( cn );
			lcPagManut.setConexao( cn );
			prefere = getPrefere();

			iAnoCC = (Integer) prefere.get( "anocc" );
			
			montaMenuCores();
			

		}

		public void mouseClicked( MouseEvent mevt ) {

			if ( mevt.getSource() == tabManut && mevt.getClickCount() == 2 ) {
				editar();
			}

			
		}

		public void mouseEntered( MouseEvent e ) {

			// TODO Auto-generated method stub
			
		}

		public void mouseExited( MouseEvent e ) {

			// TODO Auto-generated method stub
			
		}

		public void mousePressed( MouseEvent e ) {

			if (e.getModifiers() == InputEvent.BUTTON3_MASK && e.getSource()==tabManut) {
				menuCores.show(this, e.getX(), e.getY());
			}
			
		}

		public void mouseReleased( MouseEvent e ) {

			// TODO Auto-generated method stub
			
		}
		
		private void montaMenuCores() {

			try {
				
				HashMap<String, Vector<?>> cores = montaListaCores();
				
				Vector<Color> labels = (Vector<Color>) cores.get( "LAB" );
				Vector<HashMap<String, Object>> valores = (Vector<HashMap<String, Object>>) cores.get("VAL");
		
				for( int i =0; i < valores.size(); i++ ) {
					
					JMenuItem menucor = new JMenuItem();
					
					menucor.addActionListener(this);
					
					menucor.setBackground( labels.elementAt( i ) );
					
					HashMap<String, Object> hvalores = valores.elementAt( i );
					
					menucor.setText( (Integer) hvalores.get( "CODSINAL" ) + "-" + (String) hvalores.get( "DESCSINAL" ) );
					
					menuCores.add(menucor);
					
				}
				
				
				menuCores.addSeparator();
				
				menucancelacor.setText( "Limpa cor" );
				menucadastracor.setText( "Cadastro nova cor" );
				
				menucancelacor.addActionListener( this );
				menucadastracor.addActionListener( this );
				
				menuCores.add( menucancelacor );
				menuCores.add( menucadastracor );
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		private void atualizaCor(Integer codsinal, Integer codrec, Integer coditpagar ) {
			
			StringBuilder sql = new StringBuilder();
			PreparedStatement ps = null;
			
			try {
				
				sql.append( "update fnitpagar set codempsn=?, codfilialsn=?, codsinal=? " );
				sql.append( "where codemp=? and codfilial=? and codpag=? and nparcpag=? " );
				
				ps = con.prepareStatement( sql.toString() );
				
				if(codsinal!=null) {
					
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "FNSINAL" ) );
					ps.setInt( 3, codsinal );
			
				}
				else {

					ps.setNull( 1, Types.INTEGER );
					ps.setNull( 2, Types.INTEGER );
					ps.setNull( 3, Types.INTEGER );
					
				}
				
				ps.setInt( 4, Aplicativo.iCodEmp );
				ps.setInt( 5, ListaCampos.getMasterFilial( "FNITPAGAR" ) );
				ps.setInt( 6, codrec );
				ps.setInt( 7, coditpagar );
				
				ps.execute();
				
				con.commit();
				
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public HashMap<String, Vector<?>> montaListaCores() {

			Vector<HashMap<String, Object>> vVals = new Vector<HashMap<String, Object>>();
			Vector<Color> vLabs = new Vector<Color>();
		
			HashMap<String, Vector<?>> ret = new HashMap<String, Vector<?>>();

			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuffer sql = new StringBuffer();

			sql.append("select s.codsinal, s.descsinal, s.corsinal ");
			sql.append("from fnsinal s ");
			sql.append("where s.codemp=? and s.codfilial=? ");

			try {
				
				ps = con.prepareStatement(sql.toString());
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial("FNSINAL"));
				
				rs = ps.executeQuery();

				while (rs.next()) {
					
					HashMap<String, Object> hvalores = new HashMap<String, Object>();
					
					hvalores.put( "CODSINAL", rs.getInt( "CODSINAL" ) );
					hvalores.put( "DESCSINAL", rs.getString( "DESCSINAL" ) );
					
					vVals.addElement( hvalores );
					
					Color cor = new Color(rs.getInt( "corsinal" ));
					
					vLabs.addElement( cor );
					
				}

				ret.put("VAL",  vVals);
				ret.put("LAB",  vLabs);


			}
			catch (SQLException err) {
				err.printStackTrace();
				Funcoes.mensagemErro(null, "Erro ao buscar sinais");
			}
			return ret;
		}

		public void keyPressed( KeyEvent arg0 ) {

			// TODO Auto-generated method stub
			
		}

		public void keyReleased( KeyEvent arg0 ) {

			// TODO Auto-generated method stub
			
		}

		public void keyTyped( KeyEvent kevt ) {
			
			if(kevt.getSource() == txtDocManut ) {

				Integer docrec = txtDocManut.getVlrInteger();
				
				if(docrec !=null && docrec >0) {
				
					if ( (kevt.getKeyChar() == KeyEvent.VK_ENTER ) || ( kevt.getKeyChar() == KeyEvent.VK_TAB )) {
				
						Integer codpag = pesquisaDoc( docrec );
						if(codpag!=null && codpag>0) {
							txtCodPagManut.setVlrInteger( codpag );
							lcPagManut.carregaDados();
						}
				
					}
				}

			} 
			else if(kevt.getSource() == txtCodCompraManut ) {

				Integer codcompra = txtCodCompraManut.getVlrInteger();
				
				if(codcompra !=null && codcompra >0) {
				
					if ( (kevt.getKeyChar() == KeyEvent.VK_ENTER ) || ( kevt.getKeyChar() == KeyEvent.VK_TAB )) {
					
						Integer codpag = pesquisaPedido( codcompra );
							
						if(codpag!=null && codpag>0) {
							txtCodPagManut.setVlrInteger( codpag );
							lcPagManut.carregaDados();
						}
					
					}
				}

			}	
			else if(kevt.getSource() == txtNumCheque ) {

				Integer nrocheque = txtNumCheque.getVlrInteger();
				
				if(nrocheque !=null && nrocheque >0) {

					txtCodPagManut.setVlrString( "" );
					txtDocManut.setVlrString( "" );
					txtCodForManut2.setVlrString( "" );
					txtRazForManut2.setVlrString( "" );
					txtDtEmisManut.setVlrString( "" );
					lcPagManut.carregaDados();
					lcForManut2.carregaDados();
					
					if ( (kevt.getKeyChar() == KeyEvent.VK_ENTER ) || ( kevt.getKeyChar() == KeyEvent.VK_TAB )) {
					
						carregaGridManut();
					
					}
				}

			}
		}
		
		private Integer pesquisaDoc(Integer docpag) {
			
			StringBuilder sql = new StringBuilder();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Integer ret = null;
			
			try {
				
				sql.append( "select codpag from fnpagar where codemp=? and codfilial=? and docpag=?" );
				
				ps = con.prepareStatement( sql.toString() );
				
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "FNPAGAR" ) );
				ps.setInt( 3, docpag );
				
				rs = ps.executeQuery();
				
				if(rs.next()) {
					
					ret = rs.getInt( "codpag" );
					
				}
				
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			return ret;
			
		}
		
		private Integer pesquisaPedido(Integer codcompra) {
			
			StringBuilder sql = new StringBuilder();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Integer ret = null;
			
			try {
				
				sql.append( "select codpag from fnpagar where codemp=? and codfilial=? and codcompra=? " );
				
				ps = con.prepareStatement( sql.toString() );
				
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "FNPAGAR" ) );
				ps.setInt( 3, codcompra );
				
				rs = ps.executeQuery();
				
				if(rs.next()) {
					
					ret = rs.getInt( "codpag" );
					
				}
				
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			return ret;
			
		}


}
