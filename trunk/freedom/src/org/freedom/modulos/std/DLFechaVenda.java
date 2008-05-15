/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLFechaVenda.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;
import org.freedom.telas.FPassword;

public class DLFechaVenda extends FFDialogo implements FocusListener, MouseListener, CheckBoxListener, RadioGroupListener {

	private static final long serialVersionUID = 1L;

	private final int ABA_FECHAMENTO = 0;
	
	private final int ABA_FERETE = 1;
	
	private final int ABA_ADIC = 2;
	
	private final int ABA_RECEBER = 3;
	
	private final int ABA_COMISSAO = 4;

	private final JTabbedPanePad tpn = new JTabbedPanePad();

	private final JPanelPad pinFecha = new JPanelPad( 400, 300 );

	private final JPanelPad pinFrete = new JPanelPad( 400, 300 );

	private final JPanelPad pnReceber = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad pnComis = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );;

	private final JPanelPad pinInfEspec = new JPanelPad( 0, 0 );

	private final JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtVlrDescItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtPercDescVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private final JTextFieldPad txtVlrDescVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtPercAdicVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );

	private final JTextFieldPad txtVlrAdicVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtVlrProdVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtPlacaFreteVD = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtUFFreteVD = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtVlrFreteVD = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtConhecFreteVD = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldPad txtQtdFreteVD = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );

	private final JTextFieldPad txtPesoBrutVD = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );

	private final JTextFieldPad txtPesoLiqVD = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );

	private final JTextFieldPad txtEspFreteVD = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtMarcaFreteVD = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldFK txtVlrIcmsFreteVD = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPercIcmsFreteVD = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtCodAuxV = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCPFCliAuxV = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );

	private final JTextFieldPad txtNomeCliAuxV = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCidCliAuxV = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtUFCliAuxV = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtCodRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );
	
	private final JTextFieldPad txtCodBancoItRec = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldPad txtCodCartCobItRec = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtCodModBol = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtNParcItRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtVlrParcItRec = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtVlrDescItRec = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtVlrParcRec = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtDtVencItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtCodComi = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtVlrComi = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtDtVencComi = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtStatusVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private final JTextFieldPad txtAltUsuRec = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private final JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtCodTipoCobItRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtCodCartCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldFK txtDescCartCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldFK txtDescTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldFK txtDescBancoItRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldFK txtDescTipoCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldFK txtDescTipoCobItRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldFK txtDescCartCobItRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JCheckBoxPad cbImpPed = new JCheckBoxPad( "Imprime Pedido?", "S", "N" );

	private final JCheckBoxPad cbImpNot = new JCheckBoxPad( "Imprime Nota?", "S", "N" );

	private final JCheckBoxPad cbImpBol = new JCheckBoxPad( "Imprime Boleto?", "S", "N" );

	private final JCheckBoxPad cbImpRec = new JCheckBoxPad( "Imprime Recibo?", "S", "N" );

	private final JCheckBoxPad cbImpReciboItRec = new JCheckBoxPad( "Imp.rec.", "S", "N" );

	private final JCheckBoxPad cbReImpNot = new JCheckBoxPad( "Reimprime Nota?", "S", "N" );

	private final JCheckBoxPad cbAdicFrete = new JCheckBoxPad( "adiciona valor do frete na nota?", "S", "N" );

	private final JCheckBoxPad cbAdicICMSFrete = new JCheckBoxPad( "adiciona valor do frete na base de ICMS?", "S", "N" );

	private final JRadioGroup<?, ?> rgFreteVD;

	private final ListaCampos lcVenda = new ListaCampos( this );

	private final ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private final ListaCampos lcTran = new ListaCampos( this, "TN" );

	private final ListaCampos lcAuxVenda = new ListaCampos( this );

	private final ListaCampos lcFreteVD = new ListaCampos( this );

	private final ListaCampos lcReceber = new ListaCampos( this );

	private final ListaCampos lcBanco = new ListaCampos( this, "BO" );

	private final ListaCampos lcTipoCob = new ListaCampos( this, "TC" );

	private final ListaCampos lcBancoItRec = new ListaCampos( this, "BO" );

	private final ListaCampos lcCartCobItRec = new ListaCampos( this, "CB" );
	
	private final ListaCampos lcTipoCobItRec = new ListaCampos( this, "TC" );
	
	private final ListaCampos lcCartCob = new ListaCampos( this, "CB" );

	private final ListaCampos lcItReceber = new ListaCampos( this );

	private final ListaCampos lcComis = new ListaCampos( this );

	private final Navegador navItRec = new Navegador( false );

	private final Navegador navRec = new Navegador( false );

	private final Navegador navComis = new Navegador( false );

	private final Tabela tabRec = new Tabela();

	private final Tabela tabComis = new Tabela();

	private int iCodVendaFecha = 0;
	
	private boolean bCarregaReceber = true;

	private boolean bCarFrete = false;

	private boolean bPrefs[] = null;

	public DLFechaVenda( Connection cn, Integer iCodVenda, Component cOrig, String impPed, String impNf, String impBol, String impRec, String reImpNf ) {

		super( cOrig );
		setConexao( cn );
		iCodVendaFecha = iCodVenda.intValue();
		setTitulo( "Fechar Venda" );
		setAtribos( 395, 450 );		

		bPrefs = prefs();

		lcItReceber.setMaster( lcReceber );
		lcReceber.adicDetalhe( lcItReceber );
		lcItReceber.setTabela( tabRec );
		lcComis.setMaster( lcReceber );
		lcReceber.adicDetalhe( lcComis );
		lcComis.setTabela( tabComis );

		navItRec.setName( "ItReceber" );
		lcItReceber.setNavegador( navItRec );
		navRec.setName( "Receber" );
		lcReceber.setNavegador( navRec );
		navComis.setName( "Comiss�o" );
		lcComis.setNavegador( navComis );

		c.add( tpn );

		pnComis.add( new JScrollPane( tabComis ) );

		tpn.add( "Fechamento", pinFecha );
		tpn.add( "Frete", pinFrete );
		tpn.add( "Inf. espec�ficas", pinInfEspec );
		tpn.add( "Receber", pnReceber );
		tpn.add( "Comiss�o", pnComis );
		
		Vector<String> vVals = new Vector<String>();
		Vector<String> vLabs = new Vector<String>();
		vVals.addElement( "C" );
		vVals.addElement( "F" );
		vLabs.addElement( "CIF" );
		vLabs.addElement( "FOB" );

		rgFreteVD = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );

		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false ) );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		txtCodPlanoPag.setFK( true );
		txtDescPlanoPag.setListaCampos( lcPlanoPag );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		lcPlanoPag.setConexao( cn );

		txtCodTran.setNomeCampo( "CodTran" );
		lcTran.add( new GuardaCampo( txtCodTran, "CodTran", "C�d.tran.", ListaCampos.DB_PK, false ) );
		lcTran.add( new GuardaCampo( txtDescTran, "RazTran", "Nome do transportador", ListaCampos.DB_SI, false ) );
		txtDescTran.setListaCampos( lcTran );
		txtCodTran.setTabelaExterna( lcTran );
		txtCodTran.setFK( true );
		lcTran.montaSql( false, "TRANSP", "VD" );
		lcTran.setQueryCommit( false );
		lcTran.setReadOnly( true );
		lcTran.setConexao( cn );

		txtCodBanco.setNomeCampo( "CodBanco" );
		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.add( new GuardaCampo( txtCodModBol, "CodModBol", "C�d.m.bl/rc.", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		lcBanco.setConexao( cn );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setListaCampos( lcBanco );
		txtDescBanco.setListaCampos( lcBanco );
		txtCodBanco.setFK( true );
		
		txtCodCartCob.setNomeCampo( "CodCartCob" );
		lcCartCob.add( new GuardaCampo( txtCodCartCob, "CodCartCob", "C�d.cart.cob", ListaCampos.DB_PK, false ) );
		lcCartCob.add( new GuardaCampo( txtDescCartCob, "DescCartCob", "Desc.Cart.Cob", ListaCampos.DB_SI, false ) );
		lcCartCob.setDinWhereAdic( "CODBANCO = #S", txtCodBanco );
		lcCartCob.montaSql( false, "CARTCOB", "FN" );
		lcCartCob.setQueryCommit( false );
		lcCartCob.setReadOnly( true );
		lcCartCob.setConexao( cn );
		txtCodCartCob.setTabelaExterna( lcCartCob );
		txtCodCartCob.setListaCampos( lcCartCob );
		txtDescCartCob.setListaCampos( lcCartCob );
		txtCodCartCob.setFK( true );		

		txtCodTipoCob.setNomeCampo( "CodTipoCob" );
		lcTipoCob.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "C�d.tp.cob.", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTipoCob, "DescTipoCob", "Descri��o do tipo de cobran�a.", ListaCampos.DB_SI, false ) );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCob.setQueryCommit( false );
		lcTipoCob.setReadOnly( true );
		lcTipoCob.setConexao( cn );
		txtCodTipoCob.setTabelaExterna( lcTipoCob );
		txtCodTipoCob.setListaCampos( lcTipoCob );
		txtDescTipoCob.setListaCampos( lcTipoCob );
		txtCodTipoCob.setFK( true );
		
		txtCodTipoCobItRec.setNomeCampo( "CodTipoCob" );
		lcTipoCobItRec.add( new GuardaCampo( txtCodTipoCobItRec, "CodTipoCob", "C�d.tp.cob.", ListaCampos.DB_PK, false ) );
		lcTipoCobItRec.add( new GuardaCampo( txtDescTipoCobItRec, "DescTipoCob", "Descri��o do tipo de cobran�a.", ListaCampos.DB_SI, false ) );
		lcTipoCobItRec.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCobItRec.setQueryCommit( false );
		lcTipoCobItRec.setReadOnly( true );
		lcTipoCobItRec.setConexao( cn );
		txtCodTipoCobItRec.setTabelaExterna( lcTipoCobItRec );
		txtCodTipoCobItRec.setListaCampos( lcTipoCobItRec );
		txtDescTipoCobItRec.setListaCampos( lcTipoCobItRec );
		txtCodTipoCobItRec.setFK( true );

		txtCodBancoItRec.setNomeCampo( "CodBanco" );
		lcBancoItRec.add( new GuardaCampo( txtCodBancoItRec, "CodBanco", "C�d.banco", ListaCampos.DB_PK, false ) );
		lcBancoItRec.add( new GuardaCampo( txtDescBancoItRec, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBancoItRec.montaSql( false, "BANCO", "FN" );
		lcBancoItRec.setQueryCommit( false );
		lcBancoItRec.setReadOnly( true );
		lcBancoItRec.setConexao( cn );
		txtCodBancoItRec.setTabelaExterna( lcBancoItRec );
		txtCodBancoItRec.setListaCampos( lcBancoItRec );
		txtDescBancoItRec.setListaCampos( lcBancoItRec );
		txtCodBancoItRec.setFK( true );

		txtCodCartCobItRec.setNomeCampo( "CodCartCob" );
		lcCartCobItRec.add( new GuardaCampo( txtCodCartCobItRec, "CodCartCob", "C�d.Cart.Cob.", ListaCampos.DB_PK, false ) );
		lcCartCobItRec.add( new GuardaCampo( txtDescCartCobItRec, "DescCartCob", "Descri��o da carteira de cobran�a", ListaCampos.DB_SI, false ) );
		lcCartCobItRec.setWhereAdicSubSel( "CODBANCO=master.CODBANCO" );
		lcCartCobItRec.montaSql( false, "CARTCOB", "FN" );
		lcCartCobItRec.setQueryCommit( false );
		lcCartCobItRec.setReadOnly( true );
		lcCartCobItRec.setConexao( cn );
		txtCodCartCobItRec.setTabelaExterna( lcCartCobItRec );
		txtCodCartCobItRec.setListaCampos( lcCartCobItRec );
		txtDescCartCobItRec.setListaCampos( lcCartCobItRec );
		txtCodCartCobItRec.setFK( true );

		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		lcVenda.add( new GuardaCampo( txtTipoVenda, "TipoVenda", "Tp.venda", ListaCampos.DB_PK, false ) );
		lcVenda.add( new GuardaCampo( txtCodVenda, "CodVenda", "N.pedido", ListaCampos.DB_PK, false ) );
		lcVenda.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cod.p.pag.", ListaCampos.DB_FK, txtDescPlanoPag, false ) );
		lcVenda.add( new GuardaCampo( txtVlrDescItVenda, "VlrDescItVenda", "% Desc it.", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtVlrDescVenda, "VlrDescVenda", "% Desc it.", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtVlrAdicVenda, "VlrAdicVenda", "% Adic.", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtVlrProdVenda, "VlrProdVenda", "V.prod.", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "Cod.tp.cob.", ListaCampos.DB_FK, txtDescTipoCob, false ) );
		lcVenda.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_FK, txtDescBanco, false ) );
		lcVenda.add( new GuardaCampo( txtCodCartCob, "CodCartCob", "C�d.cart.cob", ListaCampos.DB_FK, txtDescCartCob, false ));
		lcVenda.add( new GuardaCampo( txtStatusVenda, "StatusVenda", "Status", ListaCampos.DB_SI, false ) );
		lcVenda.montaSql( false, "VENDA", "VD" );
		lcVenda.setConexao( cn );
		txtCodVenda.setNomeCampo( "CodVenda" );
		txtVlrAdicVenda.setListaCampos( lcVenda );
		txtPercAdicVenda.setListaCampos( lcVenda );
		txtVlrDescVenda.setListaCampos( lcVenda );
		txtPercDescVenda.setListaCampos( lcVenda );
		txtStatusVenda.setListaCampos( lcVenda );
		txtCodPlanoPag.setListaCampos( lcVenda );

		lcFreteVD.add( new GuardaCampo( txtTipoVenda, "TipoVenda", "Tipo", ListaCampos.DB_PK, false ) );
		lcFreteVD.add( new GuardaCampo( txtCodVenda, "CodVenda", "N.pedido", ListaCampos.DB_PK, false ) );
		lcFreteVD.add( new GuardaCampo( txtCodTran, "CodTran", "C�d.tran.", ListaCampos.DB_FK, txtDescTran, false ) );
		lcFreteVD.add( new GuardaCampo( rgFreteVD, "TipoFreteVD", "Tipo", ListaCampos.DB_SI, true ) );
		lcFreteVD.add( new GuardaCampo( txtConhecFreteVD, "ConhecFreteVD", "Conhec.", ListaCampos.DB_SI, false ) );
		lcFreteVD.add( new GuardaCampo( txtPlacaFreteVD, "PlacaFreteVD", "Placa", ListaCampos.DB_SI, true ) );
		lcFreteVD.add( new GuardaCampo( txtUFFreteVD, "UFFreteVD", "Placa", ListaCampos.DB_SI, true ) );
		lcFreteVD.add( new GuardaCampo( txtVlrFreteVD, "VlrFreteVD", "Valor", ListaCampos.DB_SI, true ) );
		lcFreteVD.add( new GuardaCampo( txtQtdFreteVD, "QtdFreteVD", "Qtd.", ListaCampos.DB_SI, true ) );
		lcFreteVD.add( new GuardaCampo( txtPesoBrutVD, "PesoBrutVD", "Peso bruto", ListaCampos.DB_SI, true ) );
		lcFreteVD.add( new GuardaCampo( txtPesoLiqVD, "PesoLiqVD", "Peso liq.", ListaCampos.DB_SI, true ) );
		lcFreteVD.add( new GuardaCampo( txtEspFreteVD, "EspFreteVD", "Esp.fiscal", ListaCampos.DB_SI, true ) );
		lcFreteVD.add( new GuardaCampo( txtMarcaFreteVD, "MarcaFreteVD", "Marca", ListaCampos.DB_SI, true ) );
		lcFreteVD.add( new GuardaCampo( cbAdicFrete, "AdicFreteVD", "frete na nota", ListaCampos.DB_SI, true ) );
		lcFreteVD.add( new GuardaCampo( cbAdicICMSFrete, "AdicFreteBaseICM", "frete no icms", ListaCampos.DB_SI, true ) );
		lcFreteVD.add( new GuardaCampo( txtPercIcmsFreteVD, "AliqICMSFreteVD", "aliquota", ListaCampos.DB_SI, false ) );
		lcFreteVD.add( new GuardaCampo( txtVlrIcmsFreteVD, "VlrIcmsFreteVD", "valor icms", ListaCampos.DB_SI, false ) );
		lcFreteVD.montaSql( false, "FRETEVD", "VD" );
		lcFreteVD.setConexao( cn );	
		rgFreteVD.setListaCampos( lcFreteVD );
		txtPlacaFreteVD.setListaCampos( lcFreteVD );
		txtUFFreteVD.setListaCampos( lcFreteVD );
		txtVlrFreteVD.setListaCampos( lcFreteVD );
		txtQtdFreteVD.setListaCampos( lcFreteVD );
		txtPesoBrutVD.setListaCampos( lcFreteVD );
		txtPesoLiqVD.setListaCampos( lcFreteVD );
		txtEspFreteVD.setListaCampos( lcFreteVD );
		txtMarcaFreteVD.setListaCampos( lcFreteVD );
		txtConhecFreteVD.setListaCampos( lcFreteVD );
		txtCodTran.setListaCampos( lcFreteVD );
		cbAdicFrete.setListaCampos( lcFreteVD );
		cbAdicICMSFrete.setListaCampos( lcFreteVD );
		txtPercIcmsFreteVD.setListaCampos( lcFreteVD );
		txtVlrIcmsFreteVD.setListaCampos( lcFreteVD );
		txtPlacaFreteVD.setStrMascara("###-####");	
		
		cbAdicFrete.setVlrString( "S" );
		cbAdicICMSFrete.setVlrString( "N" );

		lcAuxVenda.add( new GuardaCampo( txtTipoVenda, "TipoVenda", "Tp.venda", ListaCampos.DB_PK, false ) );
		lcAuxVenda.add( new GuardaCampo( txtCodVenda, "CodVenda", "N.pedido", ListaCampos.DB_PK, false ) );
		lcAuxVenda.add( new GuardaCampo( txtCodAuxV, "CodAuxV", "C�d.aux.", ListaCampos.DB_PK, false ) );
		lcAuxVenda.add( new GuardaCampo( txtCPFCliAuxV, "CPFCliAuxV", "CPF", ListaCampos.DB_SI, false ) );
		lcAuxVenda.add( new GuardaCampo( txtNomeCliAuxV, "NomeCliAuxV", "Nome", ListaCampos.DB_SI, false ) );
		lcAuxVenda.add( new GuardaCampo( txtCidCliAuxV, "CidCliAuxV", "Cidade", ListaCampos.DB_SI, false ) );
		lcAuxVenda.add( new GuardaCampo( txtUFCliAuxV, "UFCliAuxV", "UF", ListaCampos.DB_SI, false ) );
		lcAuxVenda.montaSql( false, "AUXVENDA", "VD" );
		lcAuxVenda.setConexao( cn );
		txtCodAuxV.setListaCampos( lcAuxVenda );
		txtCPFCliAuxV.setListaCampos( lcAuxVenda );
		txtNomeCliAuxV.setListaCampos( lcAuxVenda );
		txtCidCliAuxV.setListaCampos( lcAuxVenda );
		txtUFCliAuxV.setListaCampos( lcAuxVenda );
		txtCPFCliAuxV.setMascara( JTextFieldPad.MC_CPF );

		JPanelPad pinTopRec = new JPanelPad( 400, 60 );
		pinTopRec.setPreferredSize( new Dimension( 400, 60 ) );
		pnReceber.add( pinTopRec, BorderLayout.NORTH );
		JScrollPane spnTabRec = new JScrollPane( tabRec );
		pnReceber.add( spnTabRec, BorderLayout.CENTER );

		txtVlrParcRec.setAtivo( false );

		pinTopRec.adic( new JLabelPad( "Valor Tot." ), 7, 0, 130, 20 );
		pinTopRec.adic( txtVlrParcRec, 7, 20, 130, 20 );

		txtCodRec.setNomeCampo( "CodRec" );
		lcReceber.add( new GuardaCampo( txtCodRec, "CodRec", "C�d.rec.", ListaCampos.DB_PK, false ) );
		lcReceber.add( new GuardaCampo( txtVlrParcRec, "VlrParcRec", "Valor tot.", ListaCampos.DB_SI, false ) );
		lcReceber.add( new GuardaCampo( txtTipoVenda, "TipoVenda", "Tp.venda", ListaCampos.DB_SI, false ) );
		lcReceber.add( new GuardaCampo( txtAltUsuRec, "AltUsuRec", "Usu.alt.", ListaCampos.DB_SI, false ) );
		lcReceber.montaSql( false, "RECEBER", "FN" );
		lcReceber.setConexao( cn );
		txtCodRec.setListaCampos( lcReceber );
		txtVlrParcRec.setListaCampos( lcReceber );
		txtTipoVenda.setListaCampos( lcReceber );
		txtAltUsuRec.setListaCampos( lcReceber );
		
		txtNParcItRec.setNomeCampo( "NParcItRec" );
		lcItReceber.add( new GuardaCampo( txtNParcItRec, "NParcItRec", "N.parc.", ListaCampos.DB_PK, false ) );
		lcItReceber.add( new GuardaCampo( cbImpReciboItRec, "ImpReciboItRec", "Imp.rec.", ListaCampos.DB_SI, false) );
		lcItReceber.add( new GuardaCampo( txtVlrParcItRec, "VlrParcItRec", "Valor tot.", ListaCampos.DB_SI, false ) );
		lcItReceber.add( new GuardaCampo( txtDtVencItRec, "DtVencItRec", "Dt. vencto.", ListaCampos.DB_SI, false ) );
		lcItReceber.add( new GuardaCampo( txtVlrDescItRec, "VlrDescItRec", "Valor desc.", ListaCampos.DB_SI, false ) );
		lcItReceber.add( new GuardaCampo( txtCodTipoCobItRec, "CodTipoCob", "Cod.tp.cob.", ListaCampos.DB_FK, txtDescTipoCobItRec, false ) );
		lcItReceber.add( new GuardaCampo( txtCodBancoItRec, "CodBanco", "C�d.banco", ListaCampos.DB_FK, txtDescBancoItRec, false ) );
		lcItReceber.add( new GuardaCampo( txtCodCartCobItRec, "CodCartCob", "C�d.Cart.Cob.", ListaCampos.DB_FK, txtDescCartCobItRec, false ) );
		lcItReceber.montaSql( false, "ITRECEBER", "FN" );
		lcItReceber.setQueryCommit( false );
		txtNParcItRec.setListaCampos( lcItReceber );
		txtVlrParcItRec.setListaCampos( lcItReceber );
		txtVlrDescItRec.setListaCampos( lcItReceber );
		txtDtVencItRec.setListaCampos( lcItReceber );
		txtCodTipoCobItRec.setListaCampos( lcItReceber );
		txtCodBancoItRec.setListaCampos( lcItReceber );
		txtCodCartCobItRec.setListaCampos( lcItReceber );
		txtDescTipoCobItRec.setLabel( "Descri��o do tipo de cobran�a" );
		txtDescBancoItRec.setLabel( "Nome do banco" );
		txtDescCartCobItRec.setLabel( "Descri��o da carteira de cobran�a" );
		lcItReceber.montaTab();
		lcItReceber.setConexao( cn );
		tabRec.setColunaEditavel( 1, true );		
		tabRec.addMouseListener( this );
		
		txtCodComi.setNomeCampo( "CodComi" );
		lcComis.add( new GuardaCampo( txtCodComi, "CodComi", "C�d.comis.", ListaCampos.DB_PK, false ) );
		lcComis.add( new GuardaCampo( txtVlrComi, "VlrComi", "Valor da comiss�o", ListaCampos.DB_SI, false ) );
		lcComis.add( new GuardaCampo( txtDtVencComi, "DtVencComi", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcComis.montaSql( false, "COMISSAO", "VD" );
		lcComis.setQueryCommit( false );
		lcComis.montaTab();
		lcComis.setConexao( cn );

		tabComis.addMouseListener( this );

		txtTipoVenda.setVlrString( "V" );
		txtCodVenda.setVlrInteger( iCodVenda );
		lcVenda.carregaDados();
		
		getDadosCli();

		cbImpNot.addCheckBoxListener( this );
		cbImpPed.addCheckBoxListener( this );
		cbImpBol.addCheckBoxListener( this );
		cbImpRec.addCheckBoxListener( this );
		cbReImpNot.addCheckBoxListener( this );
		cbAdicICMSFrete.addCheckBoxListener( this );

		// Carrega o frete
		
		lcFreteVD.setReadOnly( true );
		if ( lcFreteVD.carregaDados() ) {
			lcFreteVD.setReadOnly( false );
			lcFreteVD.setState( ListaCampos.LCS_SELECT );
			bCarFrete = true;
		}
		else {
			lcFreteVD.setReadOnly( false );
		}

		// Carrega o aux
		int iCodAux = getCodAux();
		if ( iCodAux > 0 ) {
			txtCodAuxV.setVlrInteger( new Integer( iCodAux ) );
			lcAuxVenda.carregaDados();
		}
		else {
			txtCodAuxV.setVlrInteger( new Integer( 1 ) );
		}
		
		setPainel( pinFecha );
		adic( new JLabelPad( "C�d.p.pg." ), 7, 0, 100, 20 );
		adic( txtCodPlanoPag, 7, 20, 100, 20 );
		adic( new JLabelPad( "Descri��o do plano de pagamento" ), 110, 0, 270, 20 );
		adic( txtDescPlanoPag, 110, 20, 250, 20 );

		adic( new JLabelPad( "C�d.tp.cob." ), 7, 40, 100, 20 );
		adic( txtCodTipoCob, 7, 60, 100, 20 );
		adic( new JLabelPad( "Descri��o do Tipo de cobran�a" ), 110, 40, 250, 20 );
		adic( txtDescTipoCob, 110, 60, 250, 20 );
		
		adic( new JLabelPad( "C�d.banco" ), 7, 80, 100, 20 );
		adic( txtCodBanco, 7, 100, 100, 20 );
		adic( new JLabelPad( "Descri��o do Banco" ), 110, 80, 250, 20 );
		adic( txtDescBanco, 110, 100, 250, 20 );
		
		adic( new JLabelPad("C�d. Cart. cob"), 7, 120, 100, 20 );
		adic( txtCodCartCob, 7, 140, 100, 20 );
		adic( new JLabelPad("Descri�ao da Carteira de cobran�a"), 110, 120, 250, 20 );
		adic( txtDescCartCob, 110, 140, 250, 20 );

		adic( new JLabel( "% Desc." ), 7, 160, 100, 20 );
		adic( txtPercDescVenda, 7, 180, 100, 20 );
		adic( new JLabelPad( "V Desc." ), 110, 160, 100, 20 );
		adic( txtVlrDescVenda, 110, 180, 100, 20 );
		adic( new JLabelPad( "% Adic." ), 7, 200, 100, 20 );
		adic( txtPercAdicVenda, 7, 220, 100, 20 );
		adic( new JLabelPad( "V Adic." ), 110, 200, 100, 20 );
		adic( txtVlrAdicVenda, 110, 220, 100, 20 );
		
		adic( cbImpPed, 230, 170, 150, 20 );
		adic( cbImpNot, 230, 190, 150, 20 );
		adic( cbImpBol, 230, 210, 150, 20 );
		adic( cbImpRec, 230, 230, 150, 20 );
		adic( cbReImpNot, 230, 250, 150, 20 );		
		

		setPainel( pinFrete );
		adic( new JLabelPad( "C�d.tran." ), 7, 0, 80, 20 );
		adic( txtCodTran, 7, 20, 80, 20 );
		adic( new JLabelPad( "Nome do transportador" ), 90, 0, 270, 20 );
		adic( txtDescTran, 90, 20, 270, 20 );
		adic( new JLabelPad( "Tipo" ), 7, 40, 170, 20 );
		adic( rgFreteVD, 7, 60, 130, 30 );
		adic( cbAdicFrete, 150, 60, 220, 30 );
		adic( new JLabelPad( "Conhec." ), 7, 90, 230, 20 );
		adic( txtConhecFreteVD, 7, 110, 175, 20 );
		adic( new JLabelPad( "Placa" ), 185, 90, 86, 20 );
		adic( txtPlacaFreteVD, 185, 110, 86, 20 );
		adic( new JLabelPad( "UF" ), 274, 90, 86, 20 );
		adic( txtUFFreteVD, 274, 110, 86, 20 );
		adic( new JLabelPad( "Valor" ), 7, 130, 86, 20 );
		adic( txtVlrFreteVD, 7, 150, 86, 20 );
		adic( new JLabelPad( "Volumes" ), 96, 130, 86, 20 );
		adic( txtQtdFreteVD, 96, 150, 86, 20 );
		adic( new JLabelPad( "Peso B." ), 185, 130, 86, 20 );
		adic( txtPesoBrutVD, 185, 150, 86, 20 );
		adic( new JLabelPad( "Peso L." ), 274, 130, 86, 20 );
		adic( txtPesoLiqVD, 274, 150, 86, 20 );
		adic( new JLabelPad( "Espec." ), 7, 170, 175, 20 );
		adic( txtEspFreteVD, 7, 190, 175, 20 );
		adic( new JLabelPad( "Marca" ), 185, 170, 175, 20 );
		adic( txtMarcaFreteVD, 185, 190, 175, 20 );
		
		if ( bPrefs[3] ) {
			adic( cbAdicICMSFrete, 7, 220, 300, 30 );
			adic( new JLabelPad( "% icms" ), 7, 250, 86, 20 );
			adic( txtPercIcmsFreteVD, 7, 270, 86, 20 );
			adic( new JLabelPad( "Valor do icms do frete" ), 96, 250, 175, 20 );
			adic( txtVlrIcmsFreteVD, 96, 270, 175, 20 );
		}
		
		Funcoes.setBordReq( rgFreteVD );
		Funcoes.setBordReq( txtPlacaFreteVD );
		Funcoes.setBordReq( txtUFFreteVD );
		Funcoes.setBordReq( txtVlrFreteVD );
		Funcoes.setBordReq( txtQtdFreteVD );
		Funcoes.setBordReq( txtPesoBrutVD );
		Funcoes.setBordReq( txtPesoLiqVD );
		Funcoes.setBordReq( txtEspFreteVD );
		Funcoes.setBordReq( txtMarcaFreteVD );

		rgFreteVD.addRadioGroupListener( this );

		setPainel( pinInfEspec );

		adic( new JLabelPad( "Nome" ), 7, 0, 240, 20 );
		adic( txtNomeCliAuxV, 7, 20, 240, 20 );
		adic( new JLabelPad( "CPF" ), 250, 0, 100, 20 );
		adic( txtCPFCliAuxV, 250, 20, 100, 20 );
		adic( new JLabelPad( "Cidade" ), 7, 40, 300, 20 );
		adic( txtCidCliAuxV, 7, 60, 300, 20 );
		adic( new JLabelPad( "UF" ), 310, 40, 40, 20 );
		adic( txtUFCliAuxV, 310, 60, 40, 20 );

		if ( txtVlrDescItVenda.getVlrBigDecimal().doubleValue() > 0 ) {
			txtPercDescVenda.setAtivo( false );
			txtVlrDescVenda.setAtivo( false );
		}

		tpn.setEnabledAt( 1, false );
		tpn.setEnabledAt( 2, false );
		tpn.setEnabledAt( 3, false );
		tpn.setEnabledAt( 4, false );

		txtPercDescVenda.addFocusListener( this );
		txtVlrDescVenda.addFocusListener( this );
		txtPercAdicVenda.addFocusListener( this );
		txtVlrAdicVenda.addFocusListener( this );
		txtVlrFreteVD.addFocusListener( this );

		cbImpPed.setVlrString( impPed );
		cbImpNot.setVlrString( impNf );
		cbImpBol.setVlrString( impBol );
		cbImpRec.setVlrString( impRec );
		cbReImpNot.setVlrString( reImpNf );

		lcVenda.edit();
	}

	private void calcPeso() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer(); 
		
		BigDecimal bLiq = new BigDecimal( "0" );
		BigDecimal bBrut = new BigDecimal( "0" );
		
		try {
			
			sSQL.append( "SELECT SUM(I.QTDITVENDA * P.PESOLIQPROD) AS TOTPESOLIQ, " );
			sSQL.append( "SUM(I.QTDITVENDA * P.PESOBRUTPROD) AS TOTPESOBRUT " );
			sSQL.append( "FROM VDITVENDA I,EQPRODUTO P " );
			sSQL.append( "WHERE I.CODVENDA=? AND I.CODEMP=? AND I.CODFILIAL=? AND I.TIPOVENDA=? AND P.CODPROD=I.CODPROD" );
			
			lcFreteVD.edit();
			txtCodTran.setVlrInteger( new Integer( getCodTran() ) );
			lcTran.carregaDados();
			
			txtPlacaFreteVD.setVlrString( "*******" );
			txtUFFreteVD.setVlrString( "**" );
			txtVlrFreteVD.setVlrBigDecimal( new BigDecimal( "0" ) );
			txtQtdFreteVD.setVlrBigDecimal( new BigDecimal( "0" ) );
			txtEspFreteVD.setVlrString( "Volume" );
			txtMarcaFreteVD.setVlrString( "**********" );
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, iCodVendaFecha );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "VDITVENDA" ) );
			ps.setString( 4, txtTipoVenda.getVlrString() );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				bLiq = new BigDecimal( rs.getString( "TOTPESOLIQ" ) != null ? rs.getString( "TOTPESOLIQ" ) : "0" );
				bBrut = new BigDecimal( rs.getString( "TOTPESOBRUT" ) != null ? rs.getString( "TOTPESOBRUT" ) : "0" );
				bLiq = bLiq.setScale( Aplicativo.casasDec );
				bBrut = bBrut.setScale( Aplicativo.casasDec );
				txtPesoLiqVD.setVlrBigDecimal( bLiq );
				txtPesoBrutVD.setVlrBigDecimal( bBrut );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao calcular o peso!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			bLiq = null;
			bBrut = null;
		}
	}
	
	private boolean finaliza() {

		boolean bRet = false;
		
		if ( lcReceber.getStatus() == ListaCampos.LCS_EDIT ) {
			
			lcReceber.post();
		}
		
		if ( "N".equals( cbReImpNot.getVlrString() ) ) {
			
			lcVenda.edit();
			
			if ( "V2".equals( txtStatusVenda.getVlrString() ) ) {
			
				txtStatusVenda.setVlrString( "V3" );
			}
			else if ( "P2".equals( txtStatusVenda.getVlrString() ) ) {
			
				txtStatusVenda.setVlrString( "P3" );
			}
			
			bRet = lcVenda.post();
		}
		else {
		
			bRet = true;
		}
		
		return bRet;
	}

	private void gravaVenda() {

		if ( "N".equals( cbReImpNot.getVlrString() ) ) {			
			txtPlacaFreteVD.getVlrString();			
			if ( lcFreteVD.getStatus() == ListaCampos.LCS_EDIT || lcFreteVD.getStatus() == ListaCampos.LCS_INSERT ) {			
				lcFreteVD.post();
			}
			lcVenda.edit();			
			if ( "S".equals( cbImpNot.getVlrString() ) ) {			
				txtStatusVenda.setVlrString( "V2" );
			}
			else if ( "P".equals( txtStatusVenda.getVlrString().substring( 0, 1 ) ) ) {			
				txtStatusVenda.setVlrString( "P2" );
			}
			else if ( "V".equals( txtStatusVenda.getVlrString().substring( 0, 1 ) ) ) {			
				txtStatusVenda.setVlrString( "V2" );
			}			
			lcVenda.post();
			if ( lcAuxVenda.getStatus() == ListaCampos.LCS_EDIT || lcAuxVenda.getStatus() == ListaCampos.LCS_INSERT ) {			
				lcAuxVenda.post();
			}
		}
		
		int iCodRec = getCodRec();		
		if ( iCodRec > 0 ) {			
			txtCodRec.setVlrInteger( new Integer( iCodRec ) );
			if ("S".equals( cbImpRec.getVlrString())) {
				gravaImpRecibo( iCodRec, 1, new Boolean(true) );
			}
			lcReceber.carregaDados();
		}
	}

	private void alteraRec() {

		lcItReceber.edit();
		
		DLFechaParcela dl = new DLFechaParcela( this, con ); 
				
		Object[] valores = new Object[] {
				txtVlrParcItRec.getVlrBigDecimal(),
				txtDtVencItRec.getVlrDate(), 
				txtVlrDescItRec.getVlrBigDecimal(), 
				txtCodTipoCobItRec.getVlrInteger(), 
				txtCodBancoItRec.getVlrString(),
				txtCodCartCobItRec.getVlrString()
		};

		dl.setValores( valores );
		dl.setVisible( true );
		
		if ( dl.OK ) {
			
			valores = dl.getValores();
			
			txtVlrParcItRec.setVlrBigDecimal( (BigDecimal) valores[ DLFechaParcela.EFields.VALOR.ordinal() ] );
			txtDtVencItRec.setVlrDate( (Date) valores[ DLFechaParcela.EFields.DATA.ordinal() ] );
			txtVlrDescItRec.setVlrBigDecimal( (BigDecimal) valores[ DLFechaParcela.EFields.DESCONTO.ordinal() ] );
			txtCodTipoCobItRec.setVlrString( (String) valores[ DLFechaParcela.EFields.TIPOCOB.ordinal() ] );
			txtCodBancoItRec.setVlrString( (String) valores[ DLFechaParcela.EFields.BANCO.ordinal() ] );
			txtCodCartCobItRec.setVlrString( (String) valores[ DLFechaParcela.EFields.CARTCOB.ordinal() ] );
			
			txtAltUsuRec.setVlrString( "S" );
			
			lcItReceber.post();
			
			txtAltUsuRec.setVlrString( "N" );
			
			// Atualiza lcReceber
			if ( lcReceber.getStatus() == ListaCampos.LCS_EDIT ) {
			
				lcReceber.post(); // Caso o lcReceber estaja como edit executa o post que atualiza
			}
			else {
			
				lcReceber.carregaDados(); // Caso n�o, atualiza
			}
		}
		else {
			
			lcItReceber.cancel( true );
		}
		
		dl.dispose();
	}

	private void gravaImpRecibo( int codrec, int nparcitrec, boolean imprecibo ) {

		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		sql.append( "UPDATE FNITRECEBER IR SET IMPRECIBOITREC=? " );
		sql.append( "WHERE IR.CODEMP=? AND IR.CODFILIAL=? AND IR.CODREC=? AND IR.NPARCITREC=?"); 
		try {
			ps = con.prepareStatement( sql.toString() );
			if (imprecibo) {
				ps.setString( 1, "S" );
			} else {
				ps.setString( 1, "N" );
			}
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
			ps.setInt( 4, codrec );
			ps.setInt( 5, nparcitrec );
			ps.executeUpdate();
				if (!con.getAutoCommit()) {
					con.commit();
				}
				ps.close();
		} catch (SQLException e) {
			Funcoes.mensagemErro( this, "N�o foi poss�vel gravar informa��es do recibo!\n" + e.getMessage());
		}
		
	}

	private void alteraComis() {

		lcComis.edit();
		
		DLFechaParcela dl = new DLFechaParcela( this, con ); 
				
		Object[] valores = new Object[] {
				txtVlrComi.getVlrBigDecimal(), 
				txtDtVencComi.getVlrDate(), 
				txtVlrDescItRec.getVlrBigDecimal(), 
				txtCodTipoCob.getVlrInteger(),
				txtCodBanco.getVlrString(),
				"" //para evitar erro na leitura do array
		};
		
		dl.setValores( valores );
		dl.setVisible( true );
		
		if ( dl.OK ) {
			
			valores = dl.getValores();
		
			txtVlrComi.setVlrBigDecimal( (BigDecimal) valores[ DLFechaParcela.EFields.VALOR.ordinal() ] );
			txtDtVencComi.setVlrDate( (Date) valores[ DLFechaParcela.EFields.DATA.ordinal() ] );
			lcComis.post();
		}
		else {
			
			lcComis.cancel( false );
		}
		
		dl.dispose();
	}

	private boolean prox() {

		boolean bRet = false;
		
		switch ( tpn.getSelectedIndex() ) {
			
			case ABA_FECHAMENTO :
				
				txtCodVenda.setVlrInteger( new Integer( iCodVendaFecha ) );
				txtTipoVenda.setVlrString( "V" );
								
				// frete
				if ( bPrefs[ 0 ] ) {	
					if ( ! bCarFrete ) {					
						calcPeso();
						getTipoFrete();
					}
					tpn.setEnabledAt( 0, false );		
					tpn.setEnabledAt( 1, true );		
					tpn.setEnabledAt( 2, false );
					tpn.setEnabledAt( 3, false );
					tpn.setEnabledAt( 4, false );
					tpn.setSelectedIndex( 1 );
				}
				// adicional
				else if ( bPrefs[ 1 ] ) {			
					tpn.setEnabledAt( 0, false );		
					tpn.setEnabledAt( 1, false );		
					tpn.setEnabledAt( 2, true );	
					tpn.setEnabledAt( 3, false );
					tpn.setEnabledAt( 4, false );
					tpn.setSelectedIndex( 2 );
				}
				else {					
					gravaVenda();	
					tpn.setEnabledAt( 0, false );		
					tpn.setEnabledAt( 1, false );		
					tpn.setEnabledAt( 2, false );	
					tpn.setEnabledAt( 3, true );
					tpn.setEnabledAt( 4, true );
					tpn.setSelectedIndex( 3 );
					btCancel.setEnabled( false );
				}
				
				break;
			case ABA_FERETE :
				
				if ( bPrefs[ 0 ] ) {				
					lcFreteVD.edit();
				}
				// adicional
				if ( bPrefs[ 1 ] ) {					
					tpn.setEnabledAt( 0, false );		
					tpn.setEnabledAt( 1, false );		
					tpn.setEnabledAt( 2, true );	
					tpn.setEnabledAt( 3, false );
					tpn.setEnabledAt( 4, false );
					tpn.setSelectedIndex( 2 );
				}
				else {					
					gravaVenda();
					tpn.setEnabledAt( 0, false );		
					tpn.setEnabledAt( 1, false );		
					tpn.setEnabledAt( 2, false );	
					tpn.setEnabledAt( 3, true );
					tpn.setEnabledAt( 4, true );
					tpn.setSelectedIndex( 3 );
					btCancel.setEnabled( false );
				}
				
				break;
			case ABA_ADIC :

				gravaVenda();
				tpn.setEnabledAt( 0, false );		
				tpn.setEnabledAt( 1, false );		
				tpn.setEnabledAt( 2, false );	
				tpn.setEnabledAt( 3, true );
				tpn.setEnabledAt( 4, true );
				tpn.setSelectedIndex( 3 );
				btCancel.setEnabled( false );
				
				break;
			default :
				
				gravaVenda();
			
				bRet = finaliza();
		}
		
		return bRet;
	}

	private boolean[] prefs() {

		boolean[] bRetorno = new boolean[ 4 ];
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = con.prepareStatement( 
					"SELECT TABFRETEVD,TABADICVD,VERIFALTPARCVENDA,ADICFRETEBASEICM FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
			
				bRetorno[ 0 ] = rs.getString( "TABFRETEVD" ).trim().equals( "S" );
				bRetorno[ 1 ] = rs.getString( "TABADICVD" ).trim().equals( "S" );
				bRetorno[ 2 ] = rs.getString( "VERIFALTPARCVENDA" ).trim().equals( "S" );
				bRetorno[ 3 ] = rs.getString( "ADICFRETEBASEICM" ).trim().equals( "S" );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
		}
		
		return bRetorno;
	}

	private int getCodRec() {

		int iRetorno = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			ps = con.prepareStatement( "SELECT CODREC FROM FNRECEBER WHERE TIPOVENDA='V' AND CODVENDA=? AND CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, iCodVendaFecha );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {			
				iRetorno = rs.getInt( "CodRec" );
			}
			
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar o c�digo da conta a receber!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
		}
		
		return iRetorno;
	}

	private int getCodAux() {

		int iRet = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			ps = con.prepareStatement( "SELECT CODAUXV FROM VDAUXVENDA WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='V'" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDAUXVENDA" ) );
			ps.setInt( 3, txtCodVenda.getVlrInteger().intValue() );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {				
				iRet = rs.getInt( "CodAuxV" );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar codaux.\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
		}
		
		return iRet;
	}

	private int getCodTran() {

		int iRetorno = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;

		try {
			
			sSQL = "SELECT C.CODTRAN " + 
				   "FROM VDCLIENTE C, VDVENDA V " + 
				   "WHERE C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL " +
				   "AND V.CODVENDA=? AND TIPOVENDA='V' AND V.CODEMP=? AND V.CODFILIAL=?";
			
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, iCodVendaFecha );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "VDVENDA" ) );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {			
				iRetorno = rs.getInt( "CodTran" );
			}
			
			if ( iRetorno == 0 ) {
				
				sSQL = "SELECT CODTRAN FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=? ";
				
				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, Aplicativo.iCodFilial );
				
				rs = ps.executeQuery();
				
				if ( rs.next() ) {				
					iRetorno = rs.getInt( "CodTran" );
				}
			}
			
			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o c�digo da Transportadora do cliente!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

		return iRetorno;
	}

	private void getTipoFrete() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		
		try {
			
			sSQL.append( "SELECT T.CTIPOFRETE FROM EQTIPOMOV T, VDVENDA V " );
			sSQL.append( "WHERE T.CODEMP=V.CODEMPTM AND T.CODFILIAL=V.CODFILIALTM AND T.CODTIPOMOV=V.CODTIPOMOV " );
			sSQL.append( "AND V.CODEMP=? AND V.CODFILIAL=? AND V.CODVENDA=? AND V.TIPOVENDA='V'" );
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );
			ps.setInt( 3, iCodVendaFecha );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {			
				if ( ! "".equals( rs.getString( 1 ) ) && rs.getString( 1 ) != null ) {				
					rgFreteVD.setVlrString( rs.getString( 1 ).equals( "F" ) ? "F" : "C" );
				}
			}
			
			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar o c�digo da conta a receber!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}
	
	public void getDadosCli(){
	
		StringBuilder sSQL = new StringBuilder() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		if( "P1".equals( txtStatusVenda.getVlrString() ) || "V1".equals( txtStatusVenda.getVlrString() )){
			
			try {
				
				sSQL.append( "SELECT C.CODTIPOCOB, C.CODBANCO, C.CODCARTCOB " );
				sSQL.append( "FROM VDCLIENTE C, VDVENDA V " );
				sSQL.append( "WHERE C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI " );
				sSQL.append( "AND V.CODEMP=? AND V.CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='V'" );
				
				ps = con.prepareStatement( sSQL.toString() );
				
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" )); 
				ps.setInt( 3, txtCodVenda.getVlrInteger());
				rs = ps.executeQuery();
				
				if(rs.next()){
					
					txtCodBanco.setVlrString( rs.getString( "CODBANCO" ));	
					txtCodTipoCob.setVlrString( rs.getString( "CODTIPOCOB" ));
					txtCodCartCob.setVlrString( rs.getString( "CODCARTCOB" ) );
					
					lcBanco.carregaDados();		
					lcTipoCob.carregaDados(); 
		
				}
			} catch ( Exception e ) {
			
				Funcoes.mensagemErro( this, "Erro ao carregar dados do cliente \n" +e.getMessage());
				e.printStackTrace();
		
			}
		}
	}

	public List<Integer> getParcRecibo() {
		List<Integer> lsRet = new ArrayList<Integer>();
		Boolean sel = new Boolean(false);
		for (int i=0; i < tabRec.getNumLinhas(); i++) {
			sel = (Boolean) tabRec.getValor( i, 1 );
			if (sel) {
				lsRet.add( (Integer) tabRec.getValor( i, 0 ) ); // Coluna da parcela
			}
		}
		return lsRet;
	}
	
	public String[] getValores() {

		String[] sRetorno = new String[ 9 ];
		sRetorno[ 0 ] = txtCodPlanoPag.getVlrString();
		sRetorno[ 1 ] = txtVlrDescVenda.getVlrString();
		sRetorno[ 2 ] = txtVlrAdicVenda.getVlrString();
		sRetorno[ 3 ] = cbImpPed.getVlrString();
		sRetorno[ 4 ] = cbImpNot.getVlrString();
		sRetorno[ 5 ] = cbImpBol.getVlrString();
		sRetorno[ 6 ] = cbImpRec.getVlrString();
		sRetorno[ 7 ] = txtCodModBol.getVlrString();
		sRetorno[ 8 ] = cbReImpNot.getVlrString();		
		return sRetorno;
	}
	
	private void calculaIcmsFrete() {
		if ( "S".equals( cbAdicICMSFrete.getVlrString() ) ) {
			BigDecimal icms = 
				txtVlrFreteVD.getVlrBigDecimal().divide( new BigDecimal( "100.00" ) ).multiply( txtPercIcmsFreteVD.getVlrBigDecimal() );
			txtVlrIcmsFreteVD.setVlrBigDecimal( icms );
		}
	}
	
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {			
			if ( prox() ) {				
				super.actionPerformed( evt );				
			}
		}
		else {
			super.actionPerformed( evt );
		}
	}

	public void focusLost( FocusEvent fevt ) {

		if ( fevt.getSource() == txtPercDescVenda ) {			
			if ( txtPercDescVenda.getText().trim().length() < 1 ) {			
				txtVlrDescVenda.setAtivo( true );
			}
			else {				
				txtVlrDescVenda.setVlrBigDecimal( txtVlrProdVenda.getVlrBigDecimal().multiply( txtPercDescVenda.getVlrBigDecimal() ).divide( new BigDecimal( "100" ), 3, BigDecimal.ROUND_HALF_UP ) );
				txtVlrDescVenda.setAtivo( false );
			}
		}
		else if ( fevt.getSource() == txtPercAdicVenda ) {			
			if ( txtPercAdicVenda.getText().trim().length() < 1 ) {
				txtVlrAdicVenda.setAtivo( true );
			}
			else {
				txtVlrAdicVenda.setVlrBigDecimal( txtVlrProdVenda.getVlrBigDecimal().multiply( txtPercAdicVenda.getVlrBigDecimal() ).divide( new BigDecimal( "100" ), 3, BigDecimal.ROUND_HALF_UP ) );
				txtVlrAdicVenda.setAtivo( false );
			}
		}
		else if ( fevt.getSource() == txtVlrFreteVD ) {
			calculaIcmsFrete();
		}
	}

	public void focusGained( FocusEvent fevt ) { }

	public void mouseClicked( MouseEvent mevt ) {
		
		Tabela tab = (Tabela) mevt.getSource();
		String imprecibo = "N";
		if ( mevt.getClickCount() == 1) {			
			gravaImpRecibo( txtCodRec.getVlrInteger(), (Integer) tabRec.getValor( tabRec.getLinhaSel(), 0 ),
					(Boolean) tabRec.getValor( tabRec.getLinhaSel(), 1 ) );			
		}
		else if ( mevt.getClickCount() == 2 ) {			
			if ( tab == tabRec && tabRec.getLinhaSel() >= 0 ) {
				if ( bPrefs[ 2 ] ) {					
					FPassword fpw = new FPassword( this, FPassword.ALT_PARC_VENDA, null, con );
					fpw.execShow();					
					if ( fpw.OK ) {					
						alteraRec();
					}					
					fpw.dispose();
				}
				else {				
					alteraRec();
				}
			}
			else if ( tab == tabComis && tabComis.getLinhaSel() >= 0 ) {			
				alteraComis();
			}
		}
	}

	public void mouseEntered( MouseEvent e ) { }

	public void mouseExited( MouseEvent e ) { }

	public void mousePressed( MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

	public void valorAlterado( CheckBoxEvent evt ) {

		if ( evt.getCheckBox() == cbReImpNot ) {
			if ( cbReImpNot.getVlrString().equals( "S" ) ) {
				cbImpBol.setVlrString( "N" );
				cbImpRec.setVlrString( "N" );
				cbImpNot.setVlrString( "N" );
				cbImpPed.setVlrString( "N" );
			}
		}
		else if ( ( evt.getCheckBox() == cbImpNot ) || ( evt.getCheckBox() == cbImpBol ) || 
				( evt.getCheckBox() == cbImpRec ) || ( evt.getCheckBox() == cbImpPed ) ) {
			if ( ( (JCheckBoxPad) evt.getCheckBox() ).getVlrString().equals( "S" ) ) {
				cbReImpNot.setVlrString( "N" );
			}
		}
		else if ( evt.getCheckBox() == cbAdicICMSFrete ) {
			txtPercIcmsFreteVD.setEditable( "S".equals( cbAdicICMSFrete.getVlrString() ) );
			if ( "N".equals( cbAdicICMSFrete.getVlrString() ) ) {
				txtPercIcmsFreteVD.setVlrString( "" );
				txtVlrIcmsFreteVD.setVlrString( "" );
			}
		}
	}

	public void valorAlterado( RadioGroupEvent evt ) {

		if ( rgFreteVD.getVlrString().equals( "C" ) ) {
			cbAdicFrete.setVlrString( "S" );
		}
		else if ( rgFreteVD.getVlrString().equals( "F" ) ) {
			cbAdicFrete.setVlrString( "N" );
		}
	}

	@Override
    public void keyPressed( KeyEvent e ) {
		if ( e.getKeyCode() == KeyEvent.VK_ENTER && 
				e.getSource() == txtPercIcmsFreteVD && 
					txtVlrFreteVD.getVlrBigDecimal() != null && 
						txtVlrFreteVD.getVlrBigDecimal().floatValue() > 0 ) {
			calculaIcmsFrete();
		}
		else {
			super.keyPressed( e );
		}
    }
}
