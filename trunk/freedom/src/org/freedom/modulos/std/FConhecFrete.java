/**
 * @version 10/04/2008 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez	 <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FConhecFrete.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela de lan�amento de conhecimentos de frete.
 * 
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.JButtonPad;
import org.freedom.library.JLabelPad;
import org.freedom.library.JPanelPad;
import org.freedom.library.JRadioGroup;
import org.freedom.library.JTabbedPanePad;
import org.freedom.library.JTextFieldFK;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;
import org.freedom.library.Navegador;
import org.freedom.library.Tabela;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FConhecFrete extends FDetalhe implements ActionListener, ChangeListener, InsertListener, DeleteListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JTabbedPanePad tpnGeral = new JTabbedPanePad();

	private JPanelPad panelCabecalho = new JPanelPad( 500, 300 );

	private JPanelPad panelVendas = new JPanelPad();

	private JPanelPad panelCompras = new JPanelPad();

	private JTextFieldPad txtCodFrete = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );
	
	private JTextFieldPad txtCodRemet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtNomeRemet = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodDest = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtNomeDest = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtDocFrete = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldPad txtSerieFrete = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );
	
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtDtEmitFrete = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldFK txtRazTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodForTransp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodNat = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtDescNat = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtQtdFrete = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private JTextFieldPad txtVlrMercadoria = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin   );

	private JTextFieldPad txtVlrFrete = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtVlrFreteNota = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtPesoBruto = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtPesoLiq = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtAliqICMSFrete = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtVlrICMSFrete = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtVlrBaseICMSFrete = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin  );
	
	private JRadioGroup<?, ?> rgTipoContr = null;
	
	private JRadioGroup<String, String> rgTipoFrete = null;
	
	private JRadioGroup<String, String> rgTipoPgto = null;

	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldFK txtSerieVenda = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtDocVenda = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDtEmitVenda = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDtSaidaVenda = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtCodCli = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtSerieCompra = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtDocCompra = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDtEmitCompra = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDtEntCompra = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtCodFor = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JButtonPad btAtualizaConhecimentos = new JButtonPad( Icone.novo( "btTrocaNumero.gif" ) );
	
	private ListaCampos lcTransportadora = new ListaCampos( this, "TN" );
	
	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );
	
	private ListaCampos lcNat = new ListaCampos( this, "NT" );
	
	private ListaCampos lcRemetente = new ListaCampos( this, "RE" );
	
	private ListaCampos lcDestinatario = new ListaCampos( this, "DE" );

	private ListaCampos lcVenda = new ListaCampos( this, " " );

	private ListaCampos lcCliente = new ListaCampos( this, "CL" );

	private ListaCampos lcDetCompra = new ListaCampos( this );

	private ListaCampos lcCompra = new ListaCampos( this, " " );

	private ListaCampos lcFornecedor = new ListaCampos( this, "FO" );

	public Navegador navDetCompra = new Navegador( true );

	public Tabela tabDetCompra = new Tabela();

	public JScrollPane spTabDetCompra = new JScrollPane( tabDetCompra );
	
	private String sMinuta = "";
	
	private Integer oldCodVenda = null;
	
	private String oldTipoVenda = null;
	

	public FConhecFrete() {

		setTitulo( "Conhecimento de frete" );
		setAltCab( 350 );
		setAtribos( 50, 50, 730, 550 );

		montaRadios();
		montaListaCampos();		

		lcDetCompra.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcDetCompra );
		lcDetCompra.setTabela( tabDetCompra );
		navDetCompra.setName( "DetalheCompra" );
		lcDetCompra.setNavegador( navDetCompra );
		
		montaTela();		
		
		adicListeners();
		
		adicToolTips();
		
	
	}
	
	private void adicToolTips() {
	
		btAtualizaConhecimentos.setToolTipText( "Atualizar n�mero de conhecimento nas vendas" );
		
	}
	
	private void adicListeners() {
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btAtualizaConhecimentos.addActionListener( this );
		
		lcCampos.addInsertListener( this );
		lcDet.addInsertListener( this );
		
		lcDet.addPostListener( this );		
		
		lcDet.addDeleteListener( this );
		
		lcTransportadora.addCarregaListener( this );

		tpnGeral.addChangeListener( this );
		
	}
				
	private void montaRadios() {
		
		Vector<String> vVals = new Vector<String>();
		Vector<String> vLabs = new Vector<String>();		
		vVals.addElement( "1" );
		vVals.addElement( "2" );
		vLabs.addElement( "CIF" );
		vLabs.addElement( "FOB" );
		rgTipoFrete = new JRadioGroup<String, String>( 2, 1, vLabs, vVals );

		vVals = new Vector<String>();
		vLabs = new Vector<String>();
		vVals.addElement( "P" );
		vVals.addElement( "A" );
		vLabs.addElement( "Pago" );
		vLabs.addElement( "� pagar" );
		rgTipoPgto = new JRadioGroup<String, String>( 2, 1, vLabs, vVals );	
	}
	
	private void montaListaCampos() {
		
		/********************
		 *  TRANSPORTADORA  *
		 ********************/		
		lcTransportadora.add( new GuardaCampo( txtCodTran, "CodTran", "C�d.Transp.", ListaCampos.DB_PK, false ) );
		lcTransportadora.add( new GuardaCampo( txtRazTran, "RazTran", "Raz�o social da transportadora", ListaCampos.DB_SI, false ) );
		lcTransportadora.add( new GuardaCampo( txtCodForTransp, "CodFor", "C�d. fornecedor", ListaCampos.DB_SI, false ) );
		txtCodTran.setTabelaExterna( lcTransportadora );
		txtCodTran.setNomeCampo( "CodTran" );
		txtCodTran.setFK( true );
		lcTransportadora.montaSql( false, "TRANSP", "VD" );
		lcTransportadora.setReadOnly( true );
		
		/*************
		 *  TIPOMOV  *
		 *************/		
		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "C�d.Tp.Mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );
		txtCodTipoMov.setNomeCampo( "CodTipoMov" );
		txtCodTipoMov.setFK( true );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setReadOnly( true );

		/**************************
		 *  NATUREZA DA OPERA��O  *
		 **************************/		
		lcNat.add( new GuardaCampo( txtCodNat, "CodNat", "C�d.Nat.", ListaCampos.DB_PK, false ) );
		lcNat.add( new GuardaCampo( txtDescNat, "DescNat", "Descri��o da natureza da opera��o", ListaCampos.DB_SI, false ) );
		txtCodNat.setTabelaExterna( lcNat );
		txtCodNat.setNomeCampo( "CodNat" );
		txtCodNat.setFK( true );
		lcNat.montaSql( false, "NATOPER", "LF" );
		lcNat.setReadOnly( true );

		/***************
		 *  REMETENTE  *
		 ***************/		
		lcRemetente.add( new GuardaCampo( txtCodRemet, "CodUnifCod", "C�d.Rem.", ListaCampos.DB_PK, false ) );
		lcRemetente.add( new GuardaCampo( txtNomeRemet, "DescUnifCod", "Nome do remetente", ListaCampos.DB_SI, false ) );
		txtCodRemet.setTabelaExterna( lcRemetente );	
		txtCodRemet.setNomeCampo( "CodUnifCod" );	
		txtCodRemet.setFK( true );
		lcRemetente.montaSql( false, "UNIFCOD", "SG" );
		lcRemetente.setReadOnly( true );

		/******************
		 *  DESTINATARIO  *
		 ******************/		
		lcDestinatario.add( new GuardaCampo( txtCodDest, "CodUnifCod", "C�d.Dest.", ListaCampos.DB_PK, false ) );
		lcDestinatario.add( new GuardaCampo( txtNomeDest, "DescUnifCod", "Nome do destinat�rio", ListaCampos.DB_SI, false ) );
		txtCodDest.setTabelaExterna( lcDestinatario );
		txtCodDest.setNomeCampo( "CodUnifCod" );
		txtCodDest.setFK( true );
		lcDestinatario.montaSql( false, "UNIFCOD", "SG" );
		lcDestinatario.setReadOnly( true );		

		/***********
		 *  VENDA  *
		 ***********/		
		lcVenda.add( new GuardaCampo( txtDocVenda, "DocVenda", "Documento", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtCodVenda, "CodVenda", "C�d.venda", ListaCampos.DB_PK, false ) );
		lcVenda.add( new GuardaCampo( txtTipoVenda, "TipoVenda", "Tipo", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtSerieVenda, "Serie", "Serie", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtDtEmitVenda, "DtEmitVenda", "Emiss�o", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtDtSaidaVenda, "DtSaidaVenda", "Sa�da", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli", ListaCampos.DB_FK, txtRazCli, false ) );
		txtCodVenda.setListaCampos( lcVenda );
		txtCodVenda.setTabelaExterna( lcVenda );
		txtCodVenda.setNomeCampo( "CodVenda" );
		txtCodVenda.setFK( true );
		lcVenda.montaSql( false, "VENDA", "VD" );
		lcVenda.setReadOnly( true );		

		/*************
		 *  CLIENTE  *
		 *************/		
		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCliente );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCliente.montaSql( false, "CLIENTE", "VD" );
		lcCliente.setReadOnly( true );	

		/************
		 *  COMPRA  *
		 ************/		
		lcCompra.add( new GuardaCampo( txtCodCompra, "CodCompra", "C�d.compra", ListaCampos.DB_PK, false ) );
		lcCompra.add( new GuardaCampo( txtDocCompra, "DocCompra", "Documento", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtSerieCompra, "Serie", "Serie", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtDtEmitCompra, "DtEmitCompra", "Emiss�o", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtDtEntCompra, "DtEntCompra", "Entrada", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for", ListaCampos.DB_FK, txtRazCli, false ) );
		txtCodCompra.setListaCampos( lcCompra );
		txtCodCompra.setTabelaExterna( lcCompra );
		txtCodCompra.setNomeCampo( "CodCompra" );
		txtCodCompra.setFK( true );
		lcCompra.montaSql( false, "COMPRA", "CP" );
		lcCompra.setReadOnly( true );		

		/****************
		 *  FORNECEDOR  *
		 ****************/		
		lcFornecedor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, false ) );
		lcFornecedor.add( new GuardaCampo( txtRazFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		txtCodFor.setTabelaExterna( lcFornecedor );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		lcFornecedor.montaSql( false, "FORNECED", "CP" );
		lcFornecedor.setReadOnly( true );
	}
	
	private void montaTela() {
		
		setListaCampos( lcCampos );
		setPainel( panelCabecalho, pnCliCab );
		setAltCab( 250 );
		
		adicCampo( txtCodFrete, 7, 20, 90, 20, "CodFrete", "C�d.Frete", ListaCampos.DB_PK, true );	
		adicCampo( txtSerieFrete, 100, 20, 50, 20, "Serie", "Serie", ListaCampos.DB_SI, false );
		adicCampo( txtDocFrete, 153, 20, 72, 20, "DocFrete", "Doc.", ListaCampos.DB_SI, false );
		adicCampo( txtDtEmitFrete, 228, 20, 122, 20, "DTEmitFrete", "Dt.emiss�o", ListaCampos.DB_SI, true );	
		
		adicCampo( txtCodTipoMov, 353, 20, 90, 20, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, true );
		adicDescFK( txtDescTipoMov, 446, 20, 250, 20, "DescTipoMov", "Descri��o do tipo de movimento" );	
		
		adicCampo( txtCodTran, 7, 60, 90, 20, "CodTran", "C�d.Tran.", ListaCampos.DB_FK, txtRazTran, true );
		adicDescFK( txtRazTran, 100, 60, 250, 20, "RazTran", "Raz�o social da transportadora" );		
		adicCampo( txtCodNat, 353, 60, 90, 20, "CodNat", "C�d.CFOP.", ListaCampos.DB_FK, txtDescNat, true );
		adicDescFK( txtDescNat, 446, 60, 250, 20, "DescNat", "Descri��o na natureza da opera��o" );	
		
		adicCampo( txtCodRemet, 7, 100, 90, 20, "CodRemet", "C�d.Remet.", ListaCampos.DB_FK, txtNomeRemet, true );
		adicDescFK( txtNomeRemet, 100, 100, 250, 20, "DescUnifCod", "Nome do remetente" );		
		adicCampo( txtCodDest, 353, 100, 90, 20, "CodDestinat", "C�d.Destinat.", ListaCampos.DB_FK, txtNomeDest, true );
		adicDescFK( txtNomeDest, 446, 100, 250, 20, "DescUnifCod", "Nome do destinat�rio" );
		txtCodRemet.setNomeCampo( "CodUnifCod" );
		txtCodDest.setNomeCampo( "CodUnifCod" );
		
		adicCampo( txtVlrFrete, 7, 140, 100, 20, "VlrFrete", "Vlr. Frete", ListaCampos.DB_SI, false );
		adicCampo( txtVlrMercadoria, 110, 140, 100, 20, "VlrMercadoria", "Vlr.Mercadoria", ListaCampos.DB_SI, false );
		adicCampo( txtPesoBruto, 213, 140, 100, 20, "PesoBruto", "Peso Bruto", ListaCampos.DB_SI, false );
		adicCampo( txtPesoLiq, 316, 140, 100, 20, "PesoLiquido", "Peso Liquido", ListaCampos.DB_SI, false );	
		
		adicDB( rgTipoFrete, 419, 140, 100, 60, "TipoFrete", "Tipo de frete", false );	
		adicDB( rgTipoPgto, 522, 140, 123, 60, "TipoPgto", "Tipo de pagamento", false );	
		
		adicCampo( txtQtdFrete, 7, 180, 100, 20, "QtdFrete", "Volumes", ListaCampos.DB_SI, false );
		adicCampo( txtAliqICMSFrete, 110, 180, 100, 20, "AliqICMSFrete", "Aliq.ICMS", ListaCampos.DB_SI, false );	
		adicCampo( txtVlrICMSFrete, 213, 180, 100, 20, "VlrICMSFrete", "Vlr.ICMS", ListaCampos.DB_SI, false );
		adicCampo( txtVlrBaseICMSFrete, 316, 180, 100, 20, "VlrBaseICMSFrete", "Vlr.Base ICMS", ListaCampos.DB_SI, false );
		
		adic( btAtualizaConhecimentos, 661, 165, 30, 30 );
		
		setListaCampos( true, "FRETE", "LF" );
		lcCampos.setQueryInsert( true );
		
		pnDet.add( tpnGeral );
		tpnGeral.addTab( "Vendas", panelVendas );
		
		setListaCampos( lcDet );
		setPainel( panelVendas );
		setAltDet( 130 );		
		setNavegador( navRod );
	
		adicCampoInvisivel( txtTipoVenda, "TipoVenda", "Tipo", ListaCampos.DB_PK, true );
		adicCampo( txtCodVenda, 7, 20, 90, 20, "CodVenda", "C�d.Venda", ListaCampos.DB_PF, txtDocVenda, true );
		adicDescFK( txtDocVenda, 100, 20, 100, 20, "DocVenda", "Documento" );
		adic( new JLabelPad( "Serie" ), 203, 0, 100, 20 );
		adic( txtSerieVenda, 203, 20, 100, 20 );
		adic( new JLabelPad( "Emiss�o" ), 306, 0, 100, 20 );
		adic( txtDtEmitVenda, 306, 20, 100, 20 );
		adic( new JLabelPad( "Sa�da" ), 409, 0, 100, 20 );
		adic( txtDtSaidaVenda, 409, 20, 100, 20 );
		adic( new JLabelPad( "C�d. cli." ), 100, 40, 100, 20 );
		adic( txtCodCli, 100, 60, 100, 20 );
		adic( new JLabelPad( "Ras�o social do cliente" ), 203, 40, 306, 20 );
		adic( txtRazCli, 203, 60, 306, 20 );		
		
		setListaCampos( true, "FRETEVENDA", "LF" );
		lcDet.setQueryInsert( true );
		
		montaTab();
	
		tab.setTamColuna( 30, 0 );
		tab.setTamColuna( 100, 1 );
		tab.setTamColuna( 100, 2 );
		
		pnDet.add( tpnGeral );
		tpnGeral.addTab( "Compras", panelCompras );
		
		setListaCampos( lcDetCompra );
		setPainel( panelCompras );
		setAltDet( 130 );		
		setNavegador( navDetCompra );
	
		adicCampo( txtCodCompra, 7, 20, 90, 20, "CodCompra", "C�d.compra", ListaCampos.DB_PF, txtDocVenda, true );
		adicDescFK( txtDocCompra, 100, 20, 100, 20, "DocCompra", "Documento" );
		adic( new JLabelPad( "Serie" ), 203, 0, 100, 20 );
		adic( txtSerieCompra, 203, 20, 100, 20 );
		adic( new JLabelPad( "Emiss�o" ), 306, 0, 100, 20 );
		adic( txtDtEmitCompra, 306, 20, 100, 20 );
		adic( new JLabelPad( "Entrada" ), 409, 0, 100, 20 );
		adic( txtDtEntCompra, 409, 20, 100, 20 );
		adic( new JLabelPad( "C�d.for." ), 100, 40, 100, 20 );
		adic( txtCodFor, 100, 60, 100, 20 );
		adic( new JLabelPad( "Ras�o social do fornecedor" ), 203, 40, 306, 20 );
		adic( txtRazFor, 203, 60, 306, 20 );		
		
		setListaCampos( true, "FRETECOMPRA", "LF" );
		lcDetCompra.setQueryInsert( true );
		
		lcDetCompra.montaTab();
	
		tabDetCompra.setTamColuna( 100, 0 );
		tabDetCompra.setTamColuna( 100, 1 );
	}

	private void getTipoMovimento() {
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append( "select p.codtipomov9 from SGPREFERE1 p where p.codemp=? and p.codfilial=?" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				txtCodTipoMov.setVlrInteger( rs.getInt( "codtipomov9" ) );
				lcTipoMov.carregaDados();
			}
			
			rs.close();
			ps.close();
			
			con.commit();
			
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, e.getMessage(), true, con, e );
		}
	}
	
	private void getNaturezaDeOperacao() {
		
		try {

			StringBuilder sql = new StringBuilder();
			sql.append( "select count(f.siglauf) from SGFILIAL f, CPFORNECED fo " );
			sql.append( "where f.codemp=? and f.codfilial=? and " );
			sql.append( "fo.codemp=f.codemp and fo.codfilial=f.codfilial and fo.codfor=? and fo.uffor=f.siglauf" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, txtCodForTransp.getVlrInteger() );
			
			ResultSet rs = ps.executeQuery();
			
			String noEstado = null;
			
			if ( rs.next() ) {
				noEstado = rs.getInt(1)>0 ? "S" : "N";
			}
			
			StringBuilder sql2 = new StringBuilder();
			sql2.append( "select r.codnat from LFITREGRAFISCAL r " );
			sql2.append( "where r.codemptm=? and r.codfilialtm=? and r.codtipomov=? and " );
			sql2.append( "r.cvitrf='C' and r.noufitrf=? " );
			
			ps = con.prepareStatement( sql2.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			ps.setInt( 3, txtCodTipoMov.getVlrInteger() );
			ps.setString( 4, noEstado );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				txtCodNat.setVlrInteger( rs.getInt( "codnat" ) );
				lcNat.carregaDados();
			}
			
			rs.close();
			ps.close();
			
			con.commit();
			
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, e.getMessage(), true, con, e );
		}
	}
	
	private void atualizaFrete( Integer codvenda, String tipovenda, boolean soma ) {
		
		try {

			StringBuilder sql = new StringBuilder();
			sql.append( "select f.vlrfretevd, v.vlrprodvenda, f.pesobrutvd," );
			sql.append( "f.pesoliqvd, f.qtdfretevd, f.aliqicmsfretevd, f.vlricmsfretevd " );
			sql.append( "from vdfretevd f, vdvenda v " );
			sql.append( "where f.codemp=? and f.codfilial=? and f.codvenda=? and f.tipovenda=?" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, codvenda );
			ps.setString( 4, tipovenda );
			
			ResultSet rs = ps.executeQuery();
			
			String noEstado = "S";
			
			if ( rs.next() ) {
				
				lcCampos.edit();
				
				BigDecimal valorFrete = rs.getBigDecimal( "vlrfretevd" ) != null ? rs.getBigDecimal( "vlrfretevd" ) : new BigDecimal( "0.00" );
				BigDecimal valorMercadoria = rs.getBigDecimal( "vlrprodvenda" ) != null ? rs.getBigDecimal( "vlrprodvenda" ) : new BigDecimal( "0.00" );
				BigDecimal pesoBruto = rs.getBigDecimal( "pesobrutvd" ) != null ? rs.getBigDecimal( "pesobrutvd" ) : new BigDecimal( "0.00" );
				BigDecimal pesoLiquido = rs.getBigDecimal( "pesoliqvd" ) != null ? rs.getBigDecimal( "pesoliqvd" ) : new BigDecimal( "0.00" );
				BigDecimal volumes = rs.getBigDecimal( "qtdfretevd" ) != null ? rs.getBigDecimal( "qtdfretevd" ) : new BigDecimal( "0.00" );
				BigDecimal aliquotaIcmsFrete = rs.getBigDecimal( "aliqicmsfretevd" ) != null ? rs.getBigDecimal( "aliqicmsfretevd" ) : new BigDecimal( "0.00" );
				BigDecimal icmsFrete = rs.getBigDecimal( "vlricmsfretevd" ) != null ? rs.getBigDecimal( "vlricmsfretevd" ) : new BigDecimal( "0.00" );
				
				if ( soma ) {
					txtVlrFrete.setVlrBigDecimal( txtVlrFrete.getVlrBigDecimal().add( valorFrete ) );
					txtVlrMercadoria.setVlrBigDecimal( txtVlrMercadoria.getVlrBigDecimal().add( valorMercadoria ) );
					txtPesoBruto.setVlrBigDecimal( txtPesoBruto.getVlrBigDecimal().add( pesoBruto ) );
					txtPesoLiq.setVlrBigDecimal( txtPesoLiq.getVlrBigDecimal().add( pesoLiquido ) );
					txtQtdFrete.setVlrBigDecimal( txtQtdFrete.getVlrBigDecimal().add( volumes ) );
					txtAliqICMSFrete.setVlrBigDecimal( txtAliqICMSFrete.getVlrBigDecimal().add( aliquotaIcmsFrete ) );
					txtVlrICMSFrete.setVlrBigDecimal( txtVlrICMSFrete.getVlrBigDecimal().add( icmsFrete ) );
					txtVlrBaseICMSFrete.setVlrBigDecimal( txtVlrBaseICMSFrete.getVlrBigDecimal().add( valorFrete ) );
				}
				else {
					txtVlrFrete.setVlrBigDecimal( txtVlrFrete.getVlrBigDecimal().subtract( valorFrete ) );
					txtVlrMercadoria.setVlrBigDecimal( txtVlrMercadoria.getVlrBigDecimal().subtract( valorMercadoria ) );
					txtPesoBruto.setVlrBigDecimal( txtPesoBruto.getVlrBigDecimal().subtract( pesoBruto ) );
					txtPesoLiq.setVlrBigDecimal( txtPesoLiq.getVlrBigDecimal().subtract( pesoLiquido ) );
					txtQtdFrete.setVlrBigDecimal( txtQtdFrete.getVlrBigDecimal().subtract( volumes ) );
					txtAliqICMSFrete.setVlrBigDecimal( txtAliqICMSFrete.getVlrBigDecimal().subtract( aliquotaIcmsFrete ) );
					txtVlrICMSFrete.setVlrBigDecimal( txtVlrICMSFrete.getVlrBigDecimal().subtract( icmsFrete ) );
					txtVlrBaseICMSFrete.setVlrBigDecimal( txtVlrBaseICMSFrete.getVlrBigDecimal().subtract( valorFrete ) );
				}
				
				lcCampos.post();
			}
			
			rs.close();
			ps.close();
			
			con.commit();
			
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, e.getMessage(), true, con, e );
		}
	}
	
	private void atualizaNumeroConhecimento() {
		
		String conhecimento = txtDocFrete.getVlrString();
		
		if ( conhecimento == null || conhecimento.trim().length() == 0 ) {
			Funcoes.mensagemInforma( this, "N�mero de documento n�o informado!" );
			txtDocFrete.requestFocus();
			return;
		}
		
		if ( ListaCampos.LCS_EDIT == lcCampos.getStatus() ) {
			lcCampos.post();
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append( "select v.codemp, v.codfilial, v.codvenda, v.tipovenda " );
		sql.append( "from vdvenda v, lffretevenda fv, lffrete cf " );
		sql.append( "where v.codemp=fv.codemp and v.codfilial=fv.codfilial and v.tipovenda=fv.tipovenda and v.codvenda=fv.codvenda and " );
		sql.append( "cf.codemp=fv.codemp and cf.codfilial=fv.codfilial and cf.codfrete=fv.codfrete and " );
		sql.append( "cf.codemp=? and cf.codfilial=? and cf.codfrete=? " );
		sql.append( "order by fv.codvenda" );
		
		StringBuilder update = new StringBuilder();
		update.append( "update vdfretevd set conhecfretevd=? " );
		update.append( "where codemp=? and codfilial=? and tipovenda=? and codvenda=? " );
		
		try {
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "LFFTRE" ) );
			ps.setInt( 3, txtCodFrete.getVlrInteger() );
			
			ResultSet rs = ps.executeQuery();
			StringBuilder vendas = new StringBuilder();
			
			while ( rs.next() ) {

				PreparedStatement psU = con.prepareStatement( update.toString() );
				psU.setString( 1, conhecimento );
				psU.setInt( 2, rs.getInt( "codemp" ) );
				psU.setInt( 3, rs.getInt( "codfilial" ) );
				psU.setString( 4, rs.getString( "tipovenda" ) );
				psU.setInt( 5, rs.getInt( "codvenda" ) );
				psU.executeUpdate();
				psU.close();
			}
		
    		rs.close();
    		ps.close();
    		
   			con.commit();

			Funcoes.mensagemInforma( this, "Registros atualizados." );
    		
    	} catch ( SQLException e ) {
    		e.printStackTrace();
    		Funcoes.mensagemErro( this, "Erro ao atualizar n�mero de documento na venda.\n" + e.getMessage(), true, con, e );
    	}		
	}
	
	public void gerarConhecimentoFrete( Integer codvenda, String tipovenda ) {
		
		try {
			if ( codvenda == null || tipovenda == null ) {
				return;
			}
			
			StringBuilder sql = new StringBuilder();
			sql.append( "select fv.conhecfretevd, v.dtsaidavenda, fv.codtran, f.codunifcod remetente, c.codunifcod destinatario, fv.tipofretevd " );
			sql.append( "from vdvenda v, vdfretevd fv, sgfilial f, vdcliente c " ); 
			sql.append( "where v.codemp=? and v.codfilial=? and v.codvenda=? and v.tipovenda=? and " );
			sql.append( "fv.codemp=v.codemp and fv.codfilial=v.codfilial and fv.codvenda=v.codvenda and fv.tipovenda=v.tipovenda and " );
			sql.append( "f.codemp=v.codemp and f.codfilial=v.codfilial and " );
			sql.append( "c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setInt( 3, codvenda );
			ps.setString( 4, tipovenda );
			
			ResultSet rs = ps.executeQuery();
						
			conhecimento : if ( rs.next() ) {
				
				lcCampos.insert( true );
				
				if ( txtCodTipoMov.getVlrInteger() == 0 ) {
					Funcoes.mensagemErro( this, "Tipo de movimento n�o encontrado ao gerar conhecimento de frete.\n" +
							                    "Verifique o cadastro de prefer�ncias" );
					break conhecimento;
				}

				txtDocFrete.setVlrString( rs.getString( "conhecfretevd" ) );
				txtDtEmitFrete.setVlrDate( rs.getDate( "dtsaidavenda" ) );
				
				txtCodTran.setVlrInteger( rs.getInt( "codtran" ) );				
				lcTransportadora.carregaDados();
				
				if ( txtCodTran.getVlrInteger() == 0 ) {
					Funcoes.mensagemErro( this, "Transportadora n�o encontrada ao gerar conhecimento de frete." );
					break conhecimento;
				}
			
				if ( txtCodNat.getVlrString().trim().length() == 0 ) {
					Funcoes.mensagemErro( this, "Natureza de opera��o n�o encontrada ao gerar conhecimento de frete.\n" +
							                    "Verifique o tipo de movimento no cadastro de prefer�ncias e nas regras fiscais." );
					break conhecimento;
				}
				
				txtCodRemet.setVlrInteger( rs.getInt( "remetente" ) );
				lcRemetente.carregaDados();
				
				if ( txtCodRemet.getVlrInteger() == 0 ) {
					Funcoes.mensagemErro( this, "Remetente n�o encontrado ao gerar conhecimento de frete." );
					break conhecimento;
				}
				
				txtCodDest.setVlrInteger( rs.getInt( "destinatario" ) );
				lcDestinatario.carregaDados();
				
				if ( txtCodDest.getVlrInteger() == 0 ) {
					Funcoes.mensagemErro( this, "Destinat�rio n�o encontrado ao gerar conhecimento de frete." );
					break conhecimento;
				}
				
				rgTipoFrete.setVlrString( rs.getString( "tipofretevd" ) );
				
				lcCampos.post();
				
				lcDet.insert( true );
				
				txtTipoVenda.setVlrString( tipovenda );
				txtCodVenda.setVlrInteger( codvenda );
				
				lcDet.post();
			}
		
    		rs.close();
    		ps.close();
    		
   			con.commit();
    		
    	} catch ( SQLException e ) {
    		e.printStackTrace();
    		Funcoes.mensagemErro( this, "Erro ao gerar conhecimento de frete.\n" + e.getMessage(), true, con, e );
    	}		
	}

	public void beforeInsert( InsertEvent e ) { }

	public void afterInsert( InsertEvent e ) { 

		if ( e.getListaCampos() == lcCampos ) {
			
			getTipoMovimento();
			
			txtQtdFrete.setVlrBigDecimal( new BigDecimal( "0.00" ) );
			txtVlrFrete.setVlrBigDecimal( new BigDecimal( "0.00" ) );
			txtVlrMercadoria.setVlrBigDecimal( new BigDecimal( "0.00" ) );
			txtPesoBruto.setVlrBigDecimal( new BigDecimal( "0.00" ) );
			txtPesoLiq.setVlrBigDecimal( new BigDecimal( "0.00" ) );		
			txtAliqICMSFrete.setVlrBigDecimal( new BigDecimal( "0.00" ) );
			txtVlrICMSFrete.setVlrBigDecimal( new BigDecimal( "0.00" ) );
			txtVlrBaseICMSFrete.setVlrBigDecimal( new BigDecimal( "0.00" ) );			
			txtDtEmitFrete.setVlrDate( new Date() );
			
			txtDocFrete.requestFocus();
		}
		else if ( e.getListaCampos() == lcDet ) {
			txtCodVenda.requestFocus();
		}
	}

	@Override
	public void afterPost( PostEvent e ) {
		
		if ( e.getListaCampos() == lcDet ) {
			atualizaFrete( txtCodVenda.getVlrInteger(), txtTipoVenda.getVlrString(), true );
		}
	}

	public void beforeDelete( DeleteEvent e ) { 
		
		oldCodVenda = null;
		oldTipoVenda = null;
		
		if ( e.getListaCampos() == lcDet ) {			 
			oldCodVenda = txtCodVenda.getVlrInteger();
			oldTipoVenda = txtTipoVenda.getVlrString();
		} 		
	}

	public void afterDelete( DeleteEvent e ) {
		if ( e.getListaCampos() == lcDet ) {
			atualizaFrete( oldCodVenda, oldTipoVenda, false );
		}
	}

	public void beforeCarrega( CarregaEvent e ) { }
	
	public void afterCarrega( CarregaEvent e ) {
		if ( e.getListaCampos() == lcTransportadora ) {
			getNaturezaDeOperacao();			
		}
	}

	@ Override
	public void actionPerformed( ActionEvent e ) {
		
		if ( e.getSource() == btAtualizaConhecimentos ) {
			atualizaNumeroConhecimento();
		}
		
		super.actionPerformed( e );	
	}
	
	public void stateChanged( ChangeEvent e ) {

		if ( e.getSource() == tpnGeral ) {
			if ( tpnGeral.getSelectedIndex() == 0 ) {
				pnMaster.remove( spTabDetCompra );
				pnMaster.add( spTab, BorderLayout.CENTER );
				pnRodape.remove( navDetCompra );
				pnRodape.add( navRod, BorderLayout.WEST );
				setSize( getWidth(), getHeight()-1 );
			}
			else if ( tpnGeral.getSelectedIndex() == 1 ) {
				pnMaster.remove( spTab );
				pnMaster.add( spTabDetCompra, BorderLayout.CENTER );
				pnRodape.remove( navRod );
				pnRodape.add( navDetCompra, BorderLayout.WEST );
				setSize( getWidth(), getHeight()+1 );
			}
			setVisible( true );
		}
	}

	public void setConexao( DbConnection con ) {
		super.setConexao( con );
		lcTransportadora.setConexao( con );
		lcNat.setConexao( con );		
		lcTipoMov.setConexao( con );		
		lcRemetente.setConexao( con );
		lcDestinatario.setConexao( con );
		lcVenda.setConexao( con );
		lcCliente.setConexao( con );
		lcDetCompra.setConexao( con );
		lcCompra.setConexao( con );
		lcFornecedor.setConexao( con );
	}
}

