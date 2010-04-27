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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.std.view.dialog.utility.DLCriaVendaCompra;

public class DLBuscaPedCompra extends FDialogo implements ActionListener, RadioGroupListener, CarregaListener, MouseListener, FocusListener {

	private final int POS_CODPROD = 2;

	private final int POS_QTD = 4;

	private final int POS_PRECO = 5;

	private final int POS_DESC = 6;

	private final int POS_VLRLIQ = 7;

	private final int POS_TPAGR = 8;

	private final int POS_PAI = 9;

	private final int POS_VLRAGRP = 10;

	private final int POS_CODCOMPRA = 11;

	private final int POS_CODITCOMPRA = 1;

	private static final long serialVersionUID = 1L;

	private JTablePad tabitcompra = new JTablePad();

	private JScrollPane spntabitcompra = new JScrollPane( tabitcompra );

	private JTablePad tabcompra = new JTablePad();

	private JScrollPane spntabcompra = new JScrollPane( tabcompra );

	private JPanelPad pinCab = new JPanelPad( 0, 65 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnSubRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinRod = new JPanelPad( 480, 55 );

	private JPanelPad pinSair = new JPanelPad( 120, 45 );

	private JPanelPad pinBtSel = new JPanelPad( 40, 110 );

	private JPanelPad pinBtSelOrc = new JPanelPad( 40, 110 );

	private JPanelPad pnFor = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnTabCompra = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnForTab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDocCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtDtEntCompra = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDtEmitCompra = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtStatusCompra = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtVlrProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrDesc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtVlrProdCompra = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtVlrLiqCompra = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrLiq = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JButtonPad btBuscar = new JButtonPad( "Buscar", Icone.novo( "btPesquisa.gif" ) );

	private JButtonPad btExec = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private JButtonPad btTodosCompra = new JButtonPad( Icone.novo( "btTudo.gif" ) );

	private JButtonPad btNenhumCompra = new JButtonPad( Icone.novo( "btNada.gif" ) );

	private JButtonPad btTodosItCompra = new JButtonPad( Icone.novo( "btTudo.gif" ) );

	private JButtonPad btNenhumItCompra = new JButtonPad( Icone.novo( "btNada.gif" ) );

	private JButtonPad btGerar = new JButtonPad( Icone.novo( "btGerar.gif" ) );

	private JButtonPad btAgruparItens = new JButtonPad( Icone.novo( "btAdic2.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private JButtonPad btResetCompra = new JButtonPad( Icone.novo( "btReset.gif" ) );

	private JButtonPad btResetItCompra = new JButtonPad( Icone.novo( "btReset.gif" ) );

	private ListaCampos lcFor = new ListaCampos( this, "FR" );

	private ListaCampos lcCompra = new ListaCampos( this, "CP" );

	private Vector<Object> vValidos = new Vector<Object>();

	private org.freedom.modulos.gms.view.frame.crud.detail.FCompra telacompra = null;

	private boolean[] prefs;

	public static enum COMPRA {
		SEL, CODCOMPRA, CODFOR, RAZFOR, NROITENS, NROITENSLIB, VLRLIQCOMPRA, VLRLIB
	}

	public static enum ITCOMPRA {
		SEL, CODITCOMPRA, CODPROD, DESCPROD, QTDITCOMPRA, PRECOITCOMPRA, VLRDESCITCOMPRA, VLRLIQITCOMPRA, TPAGRUP, AGRUP, VLRAGRUP, CODCOMPRA, CODLOTE
	}

	public DLBuscaPedCompra( Object cp ) {

		super();

		setTitulo( "Busca pedido de compra", this.getClass().getName() );

		setAtribos( 750, 480 );

		montaTela();

		habilitaCampos();

		montaListaCampos();

		montaTabelas();

		adicListeners();

		adicToolTips();

	}

	private void habilitaCampos() {

		txtVlrProd.setAtivo( false );
		txtVlrDesc.setAtivo( false );
		txtVlrLiq.setAtivo( false );

	}

	private void montaTela() {

		c.setLayout( new BorderLayout() );
		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pnFor, BorderLayout.CENTER );
		c.add( pinCab, BorderLayout.NORTH );

		pinCab.adic( new JLabelPad( "Pedido" ), 7, 5, 60, 20 );
		pinCab.adic( txtCodCompra, 7, 25, 60, 20 );

		pinCab.adic( new JLabelPad( "C�d.For." ), 70, 5, 50, 20 );
		pinCab.adic( txtCodFor, 70, 25, 50, 20 );

		pinCab.adic( new JLabelPad( "Raz�o social do fornecedor" ), 123, 5, 300, 20 );
		pinCab.adic( txtRazFor, 123, 25, 300, 20 );

		pinCab.adic( txtStatusCompra, 396, 5, 27, 20 );

		pinCab.adic( new JLabelPad( "Vlr.Prod." ), 426, 5, 77, 20 );
		pinCab.adic( txtVlrProdCompra, 426, 25, 77, 20 );

		pinCab.adic( new JLabelPad( "Vlr.Liq." ), 506, 5, 77, 20 );
		pinCab.adic( txtVlrLiqCompra, 506, 25, 77, 20 );

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
		pinRod.adic( new JLabelPad( "Vlr.bruto" ), 7, 0, 100, 20 );
		pinRod.adic( txtVlrProd, 7, 20, 100, 20 );
		pinRod.adic( new JLabelPad( "Vlr.desc." ), 110, 0, 97, 20 );
		pinRod.adic( txtVlrDesc, 110, 20, 97, 20 );
		pinRod.adic( new JLabelPad( "Vlr.liq." ), 210, 0, 97, 20 );
		pinRod.adic( txtVlrLiq, 210, 20, 97, 20 );

		pnTabCompra.setPreferredSize( new Dimension( 600, 133 ) );

		pnTabCompra.add( spntabcompra, BorderLayout.CENTER );
		pnTabCompra.add( pinBtSelOrc, BorderLayout.EAST );

		pinBtSelOrc.adic( btTodosCompra, 3, 3, 30, 30 );
		pinBtSelOrc.adic( btNenhumCompra, 3, 34, 30, 30 );
		pinBtSelOrc.adic( btResetCompra, 3, 65, 30, 30 );
		pinBtSelOrc.adic( btExec, 3, 96, 30, 30 );

		pnForTab.add( spntabitcompra, BorderLayout.CENTER );
		pnForTab.add( pinBtSel, BorderLayout.EAST );

		pinBtSel.adic( btTodosItCompra, 3, 3, 30, 30 );
		pinBtSel.adic( btNenhumItCompra, 3, 34, 30, 30 );
		pinBtSel.adic( btResetItCompra, 3, 65, 30, 30 );
		pinBtSel.adic( btAgruparItens, 3, 96, 30, 30 );
		pinBtSel.adic( btGerar, 3, 127, 30, 30 );

		pnFor.add( pnTabCompra, BorderLayout.NORTH );
		pnFor.add( pnForTab, BorderLayout.CENTER );

	}

	private void adicToolTips() {

		btExec.setToolTipText( "Executar montagem" );
		btTodosCompra.setToolTipText( "Selecionar tudo" );
		btNenhumCompra.setToolTipText( "Limpar sele��o" );
		btGerar.setToolTipText( "Gerar no venda" );
		btAgruparItens.setToolTipText( "Agrupar �tens" );

	}

	private void adicListeners() {

		tabitcompra.addKeyListener( this );
		tabitcompra.addMouseListener( this );
		tabcompra.addKeyListener( this );

		btBuscar.addKeyListener( this );
		btGerar.addKeyListener( this );
		btAgruparItens.addKeyListener( this );

		txtCodCompra.addActionListener( this );
		btSair.addActionListener( this );
		btBuscar.addActionListener( this );
		btExec.addActionListener( this );
		btGerar.addActionListener( this );
		btAgruparItens.addActionListener( this );
		btTodosCompra.addActionListener( this );
		btNenhumCompra.addActionListener( this );
		btTodosItCompra.addActionListener( this );
		btNenhumItCompra.addActionListener( this );
		btResetCompra.addActionListener( this );
		btResetItCompra.addActionListener( this );

		lcCompra.addCarregaListener( this );
		
		txtCodCompra.addFocusListener( this );

		addWindowListener( this );

	}

	private void montaTabelas() {

		// Monta as tabelas

		tabcompra.adicColuna( "S/N" );
		tabcompra.adicColuna( "C�d.Cp." );
		tabcompra.adicColuna( "C�d.For." );
		tabcompra.adicColuna( "Raz�o do fornecedor" );
		tabcompra.adicColuna( "N� itens." );
		tabcompra.adicColuna( "N� lib." );
		tabcompra.adicColuna( "Valor total" );
		tabcompra.adicColuna( "Valor liberado" );

		tabcompra.setTamColuna( 25, COMPRA.SEL.ordinal() );
		tabcompra.setTamColuna( 60, COMPRA.CODCOMPRA.ordinal() );
		tabcompra.setTamColuna( 60, COMPRA.CODFOR.ordinal() );
		tabcompra.setTamColuna( 210, COMPRA.RAZFOR.ordinal() );
		tabcompra.setTamColuna( 60, COMPRA.NROITENS.ordinal() );
		tabcompra.setTamColuna( 60, COMPRA.NROITENSLIB.ordinal() );
		tabcompra.setTamColuna( 100, COMPRA.VLRLIQCOMPRA.ordinal() );
		tabcompra.setTamColuna( 100, COMPRA.VLRLIB.ordinal() );

		tabcompra.setColunaEditavel( COMPRA.SEL.ordinal(), true );

		tabitcompra.adicColuna( "S/N" );
		tabitcompra.adicColuna( "�tem" );
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

		tabitcompra.setTamColuna( 25, ITCOMPRA.SEL.ordinal() );
		tabitcompra.setTamColuna( 30, ITCOMPRA.CODITCOMPRA.ordinal() );
		tabitcompra.setTamColuna( 50, ITCOMPRA.CODPROD.ordinal() );
		tabitcompra.setTamColuna( 190, ITCOMPRA.DESCPROD.ordinal() );
		tabitcompra.setTamColuna( 40, ITCOMPRA.QTDITCOMPRA.ordinal() );
		tabitcompra.setTamColuna( 60, ITCOMPRA.PRECOITCOMPRA.ordinal() );
		tabitcompra.setTamColuna( 60, ITCOMPRA.VLRDESCITCOMPRA.ordinal() );
		tabitcompra.setTamColuna( 60, ITCOMPRA.VLRLIQITCOMPRA.ordinal() );

		tabitcompra.setColunaInvisivel( ITCOMPRA.TPAGRUP.ordinal() );
		tabitcompra.setColunaInvisivel( ITCOMPRA.AGRUP.ordinal() );

		tabitcompra.setTamColuna( 60, ITCOMPRA.VLRAGRUP.ordinal() );
		tabitcompra.setTamColuna( 40, ITCOMPRA.CODCOMPRA.ordinal() );

		tabitcompra.setTamColuna( 80, ITCOMPRA.CODLOTE.ordinal() );

		tabitcompra.setColunaEditavel( ITCOMPRA.SEL.ordinal(), true );

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

		txtCodCompra.setTabelaExterna( lcCompra );
		txtCodCompra.setNomeCampo( "CodCompra" );
		txtCodCompra.setFK( true );

		lcCompra.setQueryCommit( false );
		lcCompra.setReadOnly( true );

		txtCodCompra.setListaCampos( lcCompra );
		lcCompra.montaSql( false, "COMPRA", "CP" );

		// Lista campos do fornecedor
		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.Forn.", ListaCampos.DB_PK, txtRazFor, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		txtCodFor.setTabelaExterna( lcFor );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		lcFor.setReadOnly( true );
		lcFor.montaSql( false, "FORNECED", "CP" );

	}

	private void carregar() {

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
			sql.append( "ic.vlrliqitcompra, ic.codcompra, ic.codlote " );

			sql.append( "from cpitcompra ic, eqproduto pd " );

			sql.append( "where pd.codemp=ic.codemppd and pd.codfilial=ic.codfilialpd and pd.codprod=ic.codprod " );
			sql.append( "and ic.codemp=? and ic.codfilial=? and ic.codcompra=? " );

			sql.append( "order by ic.coditcompra " );

			for ( int i = 0; i < tabcompra.getNumLinhas(); i++ ) {

				if ( ! ( (Boolean) tabcompra.getValor( i, COMPRA.SEL.ordinal() ) ).booleanValue() ) {
					continue;
				}

				codcompra = (Integer) tabcompra.getValor( i, COMPRA.CODCOMPRA.ordinal() );

				ps = con.prepareStatement( sql.toString() );

				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
				ps.setInt( 3, codcompra );

				rs = ps.executeQuery();

				int irow = 0;

				while ( rs.next() ) {

					tabitcompra.adicLinha();

					tabitcompra.setValor( new Boolean( true ), irow, ITCOMPRA.SEL.ordinal() );
					tabitcompra.setValor( rs.getInt( ITCOMPRA.CODITCOMPRA.toString()), irow, ITCOMPRA.CODITCOMPRA.ordinal() );
					tabitcompra.setValor( rs.getInt( ITCOMPRA.CODPROD.toString()), irow, ITCOMPRA.CODPROD.ordinal() );
					tabitcompra.setValor( rs.getString( ITCOMPRA.DESCPROD.toString()), irow, ITCOMPRA.DESCPROD.ordinal() );

					tabitcompra.setValor( Funcoes.strDecimalToStrCurrencyd( 2, rs.getString( ITCOMPRA.QTDITCOMPRA.toString() ) != null ? rs.getString( ITCOMPRA.QTDITCOMPRA.toString() ) : "0" ), irow, ITCOMPRA.QTDITCOMPRA.ordinal() );
					tabitcompra.setValor( Funcoes.strDecimalToStrCurrencyd( 2, rs.getString( ITCOMPRA.PRECOITCOMPRA.toString() ) != null ? rs.getString( ITCOMPRA.PRECOITCOMPRA.toString() ) : "0" ), irow, ITCOMPRA.PRECOITCOMPRA.ordinal() );
					tabitcompra.setValor( Funcoes.strDecimalToStrCurrencyd( 2, rs.getString( ITCOMPRA.VLRDESCITCOMPRA.toString() ) != null ? rs.getString( ITCOMPRA.VLRDESCITCOMPRA.toString() ) : "0" ), irow, ITCOMPRA.VLRDESCITCOMPRA.ordinal() );
					tabitcompra.setValor( Funcoes.strDecimalToStrCurrencyd( 2, rs.getString( ITCOMPRA.VLRLIQITCOMPRA.toString() ) != null ? rs.getString( ITCOMPRA.VLRLIQITCOMPRA.toString() ) : "0" ), irow, ITCOMPRA.VLRLIQITCOMPRA.ordinal() );

					tabitcompra.setValor( "", irow, ITCOMPRA.TPAGRUP.ordinal() );
					tabitcompra.setValor( "", irow, ITCOMPRA.AGRUP.ordinal() );
					tabitcompra.setValor( "0,00", irow, ITCOMPRA.VLRAGRUP.ordinal() );

					tabitcompra.setValor( rs.getInt( ITCOMPRA.CODCOMPRA.toString()), irow, ITCOMPRA.CODCOMPRA.ordinal() );

					tabitcompra.setValor( rs.getString( ITCOMPRA.CODLOTE.toString() ) == null ? "" : rs.getString( ITCOMPRA.CODLOTE.toString() ), irow, ITCOMPRA.CODLOTE.ordinal() );

					vlrprod.add( rs.getBigDecimal( ITCOMPRA.PRECOITCOMPRA.toString()) );
					vlrdesc.add( rs.getBigDecimal( ITCOMPRA.VLRDESCITCOMPRA.toString() ) );
					vlrliq.add( rs.getBigDecimal( ITCOMPRA.VLRLIQITCOMPRA.toString() ) );

					vValidos.addElement( new Integer[] { codcompra, rs.getInt( ITCOMPRA.CODITCOMPRA.toString() ) } );

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

	private void atualizaObsCompra( final StringBuffer obs, final int codcompra ) {

		PreparedStatement ps = null;

		try {

			PreparedStatement ps2 = con.prepareStatement( "UPDATE CPCOMPRA SET OBSCOMPRA=? WHERE CODEMP=? AND CODFILIAL=? AND CODCOMPRA=?" );

			ps2.setString( 1, obs.toString().length() > 10000 ? obs.toString().substring( 0, 10000 ) : obs.toString() );

			ps2.setInt( 2, Aplicativo.iCodEmp );
			ps2.setInt( 3, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			ps2.setInt( 4, codcompra );

			ps2.execute();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao atualizar observa��es da compra!\n" + err.getMessage(), true, con, err );
		}
	}

	private boolean gerar() {

		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;

		ResultSet rs = null;
		String sSQL = null;

		boolean bPrim = true;
		Integer codcompra = null;
		int[] iValsVec = null;

		StringBuffer obs = new StringBuffer();
		DLCriaVendaCompra diag = null;

		try {

			if ( tabitcompra.getNumLinhas() > 0 ) {

				boolean usaPedSeq = prefs[ 0 ];

				diag = new DLCriaVendaCompra( !usaPedSeq, null );

				if ( !usaPedSeq ) {
					diag.setNewCodigo( Integer.parseInt( telacompra.lcCampos.getNovoCodigo() ) );
				}

				diag.setVisible( true );

				if ( diag.OK ) {
					if ( !usaPedSeq )
						codcompra = diag.getNewCodigo();
				}
				else
					return false;

				for ( int i = 0; i < tabitcompra.getNumLinhas(); i++ ) {
					if ( ! ( (Boolean) tabitcompra.getValor( i, 0 ) ).booleanValue() )
						continue;

					iValsVec = (int[]) vValidos.elementAt( i );

					// Informa na observa��o da venda os or�amentos que compoe a venda.
					if ( prefs[ 2 ] ) {
						if ( bPrim ) {
							obs.append( "Or�amentos:\n" );
							obs.append( iValsVec[ 0 ] );
						}
						else {
							obs.append( iValsVec[ 0 ] );
						}

						if ( vValidos.size() > 1 && ( vValidos.size() != i + 1 ) ) {
							obs.append( " , " );
						}
						else {
							obs.append( " . " );
						}
					}

					// Informa na observa��o da venda a mesma observa��o do or�amento (primeiro do grid)
					else if ( prefs[ 3 ] ) {
						obs.append( tabcompra.getValor( 0, 8 ) );
					}

					if ( bPrim ) {
						try {
							sSQL = "SELECT IRET FROM VDADICCOMPRAORCSP(?,?,?,?,?)";
							ps = con.prepareStatement( sSQL );
							ps.setInt( 1, new Integer( tabitcompra.getValor( i, 11 ).toString() ) );
							ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
							ps.setInt( 3, Aplicativo.iCodEmp );
							ps.setString( 4, null );
							ps.setInt( 5, codcompra );
							rs = ps.executeQuery();

							if ( rs.next() )
								codcompra = rs.getInt( 1 );

							rs.close();
							ps.close();

						} catch ( SQLException err ) {
							if ( err.getErrorCode() == 335544665 ) {
								Funcoes.mensagemErro( this, "N�mero de pedido j� existe!" );
								return gerar();
							}
							else
								Funcoes.mensagemErro( this, "Erro ao gerar venda!\n" + err.getMessage(), true, con, err );

							err.printStackTrace();
							return false;
						} catch ( Exception e ) {
							Funcoes.mensagemErro( this, "Erro gen�rico ao gerar venda!\n" + e.getMessage(), true, con, e );
						}
						bPrim = false;
					}
					try {
						sSQL = "EXECUTE PROCEDURE VDADICITVENDAORCSP(?,?,?,?,?,?,?,?,?,?)";
						ps2 = con.prepareStatement( sSQL );
						ps2.setInt( 1, Aplicativo.iCodFilial );
						ps2.setInt( 2, codcompra );
						ps2.setInt( 3, new Integer( tabitcompra.getValor( i, 11 ).toString() ) );
						ps2.setInt( 4, new Integer( tabitcompra.getValor( i, 1 ).toString() ) );
						ps2.setInt( 5, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
						ps2.setInt( 6, Aplicativo.iCodEmp );
						ps2.setString( 7, null );
						ps2.setString( 8, tabitcompra.getValor( i, POS_TPAGR ).toString() );
						ps2.setFloat( 9, new Float( Funcoes.strCurrencyToDouble( tabitcompra.getValor( i, POS_QTD ).toString() ) ) );
						ps2.setFloat( 10, new Float( Funcoes.strCurrencyToDouble( tabitcompra.getValor( i, POS_DESC ).toString() ) ) );

						ps2.execute();
						ps2.close();

					} catch ( SQLException err ) {
						Funcoes.mensagemErro( this, "Erro ao gerar itvenda: '" + ( i + 1 ) + "'!\n" + err.getMessage(), true, con, err );
						try {
							con.rollback();
						} catch ( SQLException err1 ) {
						}
						return false;
					}

				}
				try {

					// Atualiza o desconto na venda de acordo com o desconto dado no or�amento.
					sSQL = "EXECUTE PROCEDURE VDATUDESCVENDAORCSP(?,?,?,?)";
					ps3 = con.prepareStatement( sSQL );
					ps3.setInt( 1, Aplicativo.iCodEmp );
					ps3.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
					ps3.setString( 3, "V" );
					ps3.setInt( 4, codcompra );

					ps3.execute();
					ps3.close();

					atualizaObsCompra( obs, codcompra );
					con.commit();
					carregar();

				} catch ( SQLException err ) {
					Funcoes.mensagemErro( this, "Erro ao realizar commit!!" + "\n" + err.getMessage(), true, con, err );
					return false;
				}
				if ( Funcoes.mensagemConfirma( null, "Compra '" + codcompra + "' gerada com sucesso!!!\n\n" + "Deseja edita-la?" ) == JOptionPane.YES_OPTION ) {
					telacompra.exec( codcompra );
					dispose();
				}
				// }
				// PDV

			}
			else
				Funcoes.mensagemInforma( this, "N�o existe nenhum item pra gerar uma venda!" );
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			ps2 = null;
			rs = null;
			iValsVec = null;
			diag = null;
		}

		return true;
	}

	private void buscar() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		Vector<Object> vVals = null;

		try {

			sql.append( "select cp.statuscompra, cp.codcompra, cp.codfor, fr.razfor, " );
			sql.append( "(select count(*) from cpitcompra ic where ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra) nroitens , " );
			sql.append( "(select count(*) from cpitcompra ic where ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra) nroitenslib, " );
			sql.append( "cp.vlrliqcompra, cp.vlrliqcompra vlrlib " );
			sql.append( "from cpcompra cp, cpforneced fr " );
			sql.append( "where " );
			sql.append( "fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor " );
			sql.append( "and cp.statuscompra in ('P1','P2','P3') " );

			if ( txtCodFor.getVlrInteger() > 0 && txtCodCompra.getVlrInteger() <= 0 ) {
				sql.append( "and cp.codempfr=? and cp.codfilialfr=? and cp.codfor=? " );
			}

			sql.append( "and cp.codemp=? and cp.codfilial=? " );

			if ( txtCodCompra.getVlrInteger() > 0 ) {
				sql.append( " and cp.codcompra=? " );
			}

			ps = con.prepareStatement( sql.toString() );

			int param = 1;

			if ( txtCodFor.getVlrInteger() > 0 && txtCodCompra.getVlrInteger() <= 0 ) {
				ps.setInt( param++, lcFor.getCodEmp() );
				ps.setInt( param++, lcFor.getCodFilial() );
				ps.setInt( param++, txtCodFor.getVlrInteger() );
			}

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPCOMPRA" ) );

			if ( txtCodCompra.getVlrInteger() > 0 ) {
				ps.setInt( param++, txtCodCompra.getVlrInteger() );
			}

			rs = ps.executeQuery();
			
			tabcompra.limpa();

			int irow = 0;

			while ( rs.next() ) {

				tabcompra.adicLinha();

				tabcompra.setValor( new Boolean( true ), irow, COMPRA.SEL.ordinal() );

				tabcompra.setValor( rs.getInt( COMPRA.CODCOMPRA.toString()), irow, COMPRA.CODCOMPRA.ordinal() );
				tabcompra.setValor( rs.getInt( COMPRA.CODFOR.toString()), irow, COMPRA.CODFOR.ordinal() );
				tabcompra.setValor( rs.getString( COMPRA.RAZFOR.toString()), irow, COMPRA.RAZFOR.ordinal() );
				
				tabcompra.setValor( Funcoes.strDecimalToStrCurrencyd( 2, rs.getString( COMPRA.NROITENS.toString() )), irow, COMPRA.NROITENS.ordinal() );
				tabcompra.setValor( Funcoes.strDecimalToStrCurrencyd( 2, rs.getString( COMPRA.NROITENSLIB.toString() )), irow, COMPRA.NROITENSLIB.ordinal() );					
				tabcompra.setValor( Funcoes.strDecimalToStrCurrencyd( 2, rs.getString( COMPRA.VLRLIQCOMPRA.toString() )), irow, COMPRA.VLRLIQCOMPRA.ordinal() );				
				tabcompra.setValor( Funcoes.strDecimalToStrCurrencyd( 2, rs.getString( COMPRA.VLRLIB.toString() )) , irow, COMPRA.VLRLIB.ordinal() );
 				
			}
			
			rs.close();
			ps.close();
			
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar compras!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
		finally {
			ps = null;
			rs = null;
			vVals = null;
		}
	}

	private boolean[] getPrefs() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		boolean[] ret = new boolean[ 4 ];
		try {
			sSQL = "SELECT P1.USAPEDSEQ, P4.AUTOFECHAVENDA,P1.ADICORCOBSPED, P1.ADICOBSORCPED " + "FROM SGPREFERE1 P1, SGPREFERE4 P4 " + "WHERE P1.CODEMP=? AND P1.CODFILIAL=? " + "AND P4.CODEMP=P1.CODEMP AND P4.CODFILIAL=P4.CODFILIAL";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( rs.getString( 1 ).equals( "S" ) )
					ret[ 0 ] = true;
				if ( rs.getString( 2 ).equals( "S" ) )
					ret[ 1 ] = true;
				if ( rs.getString( 3 ).equals( "S" ) )
					ret[ 2 ] = true;
				if ( rs.getString( 4 ).equals( "S" ) )
					ret[ 3 ] = true;

			}
			rs.close();
			ps.close();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar or�amentos!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return ret;
	}

	private void limpaNaoSelecionados( JTablePad ltab ) {

		int linhas = ltab.getNumLinhas();
		int pos = 0;
		try {
			for ( int i = 0; i < linhas; i++ ) {
				if ( ! ( (Boolean) ltab.getValor( i, 0 ) ).booleanValue() ) { // xxx
					ltab.tiraLinha( i );
					vValidos.remove( i );
					i--;
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void limpaFilhos( JTablePad ltab ) {

		int linhas = ltab.getNumLinhas();
		int pos = 0;
		try {
			for ( int i = 0; i < linhas; i++ ) {
				if ( ltab.getValor( i, POS_TPAGR ).toString().equals( "F" ) ) {
					ltab.tiraLinha( i );
					i--;
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private Float marcaFilhos( final int iLinha, final Integer codprodpai, final Float precopai ) {

		Integer codprodfilho = null;
		Float precofilho = null;
		Float vlrliqfilho = null;
		Float qtdfilho = null;
		Float ret = new Float( 0 );
		String tpagrup = null;
		int i = iLinha;
		int iPai = iLinha - 1;

		try {
			while ( i < tabitcompra.getNumLinhas() ) {
				codprodfilho = new Integer( tabitcompra.getValor( i, POS_CODPROD ).toString() );
				qtdfilho = new Float( Funcoes.strCurrencyToDouble( tabitcompra.getValor( i, POS_QTD ).toString() ) );
				vlrliqfilho = new Float( Funcoes.strCurrencyToDouble( tabitcompra.getValor( i, POS_VLRLIQ ).toString() ) );
				tpagrup = tabitcompra.getValor( i, POS_TPAGR ).toString();
				precofilho = new Float( Funcoes.strCurrencyToDouble( tabitcompra.getValor( i, POS_PRECO ).toString() ) );
				// if( codprodfilho == codprodpai && precopai == precofilho && vlrliqfilho == (qtdfilho * precofilho) && tpagrup.equals( "" ) ) {
				if ( ( codprodfilho.compareTo( codprodpai ) == 0 ) && ( precopai.compareTo( precofilho ) == 0 ) ) {
					tabitcompra.setValor( "F", i, POS_TPAGR );
					tabitcompra.setValor( String.valueOf( iPai ), i, POS_PAI );
					ret += qtdfilho;
				}
				i++;
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return ret;
	}

	private void agrupaItens() {

		Integer codprodpai = null;
		Float vlrdescnovopai = new Float( 0 );
		Float qtdatupai = null;
		Float qtdnovopai = new Float( 0 );
		Float precopai = null;
		String tpagr = "";

		try {
			limpaNaoSelecionados( tabitcompra );

			int linhaPai = -1;

			for ( int i = 0; i < tabitcompra.getNumLinhas(); i++ ) {
				codprodpai = new Integer( tabitcompra.getValor( i, POS_CODPROD ).toString() );
				qtdatupai = new Float( Funcoes.strCurrencyToDouble( tabitcompra.getValor( i, POS_QTD ).toString() ) );
				precopai = new Float( Funcoes.strCurrencyToDouble( tabitcompra.getValor( i, POS_PRECO ).toString() ) );
				tpagr = tabitcompra.getValor( i, POS_TPAGR ).toString();
				vlrdescnovopai += new Float( Funcoes.strCurrencyToDouble( tabitcompra.getValor( i, POS_DESC ).toString() ) );

				if ( tpagr.equals( "" ) ) {
					qtdnovopai = qtdatupai;
					qtdnovopai += marcaFilhos( i + 1, codprodpai, precopai );

					if ( qtdatupai.compareTo( qtdnovopai ) != 0 ) {
						tabitcompra.setValor( "P", i, POS_TPAGR );
						tabitcompra.setValor( Funcoes.strDecimalToStrCurrencyd( 2, String.valueOf( qtdnovopai ) ), i, POS_QTD );
						linhaPai = i;
					}
					else {
						tabitcompra.setValor( "N", i, POS_TPAGR );
					}
				}
			}

			if ( linhaPai > -1 ) {
				tabitcompra.setValor( Funcoes.strDecimalToStrCurrencyd( 2, String.valueOf( vlrdescnovopai ) ), linhaPai, POS_DESC );
			}
			// limpaFilhos( tab );
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
				btExec.doClick();
				tabitcompra.requestFocus();
			}
			else if ( kevt.getSource() == btGerar ) {
				if ( !gerar() ) {
					try {
						con.rollback();
					} catch ( SQLException err ) {
						Funcoes.mensagemErro( this, "Erro ao realizar rollback!!\n" + err.getMessage(), true, con, err );
					}
				}
			}
			else if ( kevt.getSource() == tabitcompra )
				btGerar.requestFocus();
		}
		// super.keyPressed(kevt);
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btBuscar ) {
			buscar();
		}
		else if ( evt.getSource() == btExec ) {
			carregar();
		}
		else if ( evt.getSource() == btGerar ) {
			if ( !gerar() ) {
				try {
					con.rollback();
				} catch ( SQLException err ) {
					Funcoes.mensagemErro( this, "Erro ao realizar rollback!!\n" + err.getMessage(), true, con, err );
				}
			}
		}
		else if ( evt.getSource() == btAgruparItens ) {
			try {
				if ( Funcoes.mensagemConfirma( null, "Confirma o agrupamento dos �tens iguais?\nSer�o agrupados apenas os �tens de c�digo e pre�os iguais." ) == JOptionPane.YES_OPTION ) {
					agrupaItens(); // comentar
				}
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro ao realizar agrupamento de �tens!!\n" + err.getMessage(), true, con, err );
			}
		}
		else if ( evt.getSource() == btTodosCompra ) {
			carregaTudo( tabcompra );
		}
		else if ( evt.getSource() == btNenhumCompra ) {
			carregaNada( tabcompra );
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
		else if ( evt.getSource() == btResetCompra ) {
			tabcompra.limpa();
			tabitcompra.limpa();
		}
		else if ( evt.getSource() == btResetItCompra ) {
			tabitcompra.limpa();
		}

	}

	public void beforeCarrega( CarregaEvent e ) {

	}

	public void afterCarrega( CarregaEvent e ) {

		if ( e.getListaCampos() == lcCompra ) {
			txtCodFor.setAtivo( false );
			// lcFor.limpaCampos( true );
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

		prefs = getPrefs();

		txtCodCompra.setFocusable( true );
		setFirstFocus( txtCodCompra );
	}

	public void mouseClicked( MouseEvent e ) {

		if ( e.getSource() == tabitcompra ) {
		
		}

	}

	public void mouseEntered( MouseEvent arg0 ) {

	}

	public void mouseExited( MouseEvent arg0 ) {

	}

	public void mousePressed( MouseEvent arg0 ) {

	}

	public void mouseReleased( MouseEvent arg0 ) {

	}

	public void focusGained( FocusEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}
 
	public void focusLost( FocusEvent evt ) {

		if(evt.getSource()==txtCodCompra) {
			if(txtCodCompra.getVlrInteger()>0) {
				txtCodFor.setAtivo( false );				
			}
			else {
				txtCodFor.setAtivo( true );
			}
		}
		
	}
}
