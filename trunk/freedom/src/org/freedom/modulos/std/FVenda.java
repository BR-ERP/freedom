/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FVenda.java <BR>
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
 * 
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.Logger;
import org.freedom.layout.componentes.Layout;
import org.freedom.layout.componentes.Leiaute;
import org.freedom.layout.componentes.NFSaida;
import org.freedom.modulos.util.CtrlMultiComis;
import org.freedom.modulos.util.CtrlMultiComis.ItemComis;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPassword;

public class FVenda extends FVD implements PostListener, CarregaListener, FocusListener, ActionListener, InsertListener, DeleteListener {

	private static final long serialVersionUID = 1L;
	private JPanelPad pinCabVenda = new JPanelPad();
	private int altcabcomis = 80;
	private int aumentacabcomis = -2;
	private JPanelPad pinCabComis = null;
	private JScrollPane spnCabComis = null;
	private JPanelPad pinCabFiscal = new JPanelPad();
	private JPanelPad pinDet = new JPanelPad();
	private JPanelPad pinTot = new JPanelPad( 200, 200 );
	private JPanelPad pnTot = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JPanelPad pnCenter = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	private JButton btObs = new JButton( Icone.novo( "btObs.gif" ) );
	private JButton btFechaVenda = new JButton( Icone.novo( "btOk.gif" ) );
	private JButton btConsPgto = new JButton( Icone.novo( "btConsPgto.gif" ) );
	private JButton btAdicOrc = new JButton( "Busca Or�amento", Icone.novo( "btOrcVenda.gif" ) );
	private JButton btAltComis = new JButton( Icone.novo( "btEditar.gif" ) );
	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodRegrComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	private JTextFieldPad txtTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	private JTextFieldPad txtDocVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodTratTrib = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	private JTextFieldPad txtTipoMov = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	private JTextFieldPad txtESTipoMov = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	private JTextFieldPad txtDtEmitVenda = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	private JTextFieldPad txtDtSaidaVenda = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtNomeCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodClComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtPedCliVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	private JTextFieldFK txtDescClComis = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	private JTextFieldPad txtPercComisVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 7, casasDecFin );
	private JTextFieldPad txtCodItVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtQtdItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	private JTextFieldPad txtCLoteProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	private JTextFieldPad txtVerifProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	private JTextFieldPad txtPrecoItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtPercDescItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 7, casasDecFin );
	private JTextFieldPad txtVlrDescItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrComisItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtPercComItVenda = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, casasDecFin );
	private JTextFieldPad txtCodNat = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	private JTextFieldFK txtSldLiqProd = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 15, casasDec );
	private JTextFieldPad txtPercICMSItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 7, casasDecFin );
	private JTextFieldPad txtVlrICMSItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrLiqItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtEstCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	private JTextFieldPad txtClasComis = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	private JTextFieldPad txtCodMens = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	private JTextFieldPad txtCodFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	private JTextFieldPad txtTipoFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	private JTextFieldPad txtRedFisc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, 2 );
	private JTextFieldPad txtTpRedIcmsFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	private JTextFieldPad txtVlrFreteVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrComisVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtMedComisVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 9, casasDecFin );
	private JTextFieldPad txtVlrICMSVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrIPIVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrPisVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrCofinsVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrIRVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrCSocialVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrBaseICMSVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrBaseISSVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrISSVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrProdVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrDescVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrLiqVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtVlrProdItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtBaseIPIItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );
	private JTextFieldPad txtStrDescItVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	private JTextAreaPad txaObsItVenda = new JTextAreaPad( 500 );
	private JTextFieldPad txtBaseICMSItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtAliqFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, casasDecFin );
	private JTextFieldPad txtAliqIPIItVenda = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, casasDecFin );
	private JTextFieldPad txtVlrIPIItVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtAliqIPIFisc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, casasDecFin );
	private JTextFieldPad txtVlrBrutVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDecFin );
	private JTextFieldPad txtStatusVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	private JTextFieldPad txtOrigFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	private JTextFieldPad txtCodEmpLG = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodFilialLG = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodLog = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	private JTextFieldFK txtDescCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	private JTextFieldFK txtDescProdAux = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	private JTextFieldFK txtDescNat = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	private JTextFieldFK txtDescLote = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	private JTextFieldFK txtDescFisc = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	private JTextField txtFiscalTipoMov1 = new JTextField();
	private JTextField txtFiscalTipoMov2 = new JTextField();
	private JTextFieldPad txtCodAlmoxItVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	private JTextFieldPad txtUltCamp = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );
	private JLabelPad lbStatus = new JLabelPad();
	private JCheckBoxPad chbImpPedTipoMov = new JCheckBoxPad( "Imp.ped.", "S", "N" );
	private JCheckBoxPad chbImpNfTipoMov = new JCheckBoxPad( "Imp.NF", "S", "N" );
	private JCheckBoxPad chbImpBolTipoMov = new JCheckBoxPad( "Imp.bol.?", "S", "N" );
	private JCheckBoxPad chbImpRecTipoMov = new JCheckBoxPad( "Imp.rec.?", "S", "N" );
	private JCheckBoxPad chbReImpNfTipoMov = new JCheckBoxPad( "Reimp.NF?", "S", "N" );
	private JCheckBoxPad cbAtivo = new JCheckBoxPad( "Ativo", "S", "N" );
	private ListaCampos lcTratTrib = new ListaCampos( this, "TT" );
	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );
	private ListaCampos lcCli = new ListaCampos( this, "CL" );
	private ListaCampos lcVendedor = new ListaCampos( this, "VD" );
	private ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );
	private ListaCampos lcSerie = new ListaCampos( this, "SE" );
	private ListaCampos lcProd = new ListaCampos( this, "PD" );
	private ListaCampos lcProd2 = new ListaCampos( this, "PD" );
	private ListaCampos lcNat = new ListaCampos( this, "NT" );
	private ListaCampos lcLote = new ListaCampos( this, "LE" );
	private ListaCampos lcClComis = new ListaCampos( this, "CM" );
	private ListaCampos lcFisc = new ListaCampos( this );
	private ListaCampos lcVenda2 = new ListaCampos( this );
	private ListaCampos lcAlmox = new ListaCampos( this, "AX" );
	private CtrlMultiComis ctrlmc = null;
	private JTabbedPanePad tpnCab = new JTabbedPanePad();
	private int numComissionados = 0; 
	private boolean[] bPrefs = null;
	private boolean bCtrl = false;
	private String sOrdNota = "";
	private int iCodCliAnt = 0;
	private int codregrcomis = 0;

	private enum POS_PREFS { USAREFPROD, USAPEDSEQ, USALIQREL, TIPOPRECOCUSTO, USACLASCOMIS, 
		TRAVATMNFVD, NATVENDA, BLOQVENDA, VENDAMATPRIM, DESCCOMPPED, 
		TAMDESCPROD, OBSCLIVEND, IPIVENDA, CONTESTOQ, DIASPEDT, 
		RECALCCPVENDA, USALAYOUTPED, ICMSVENDA, USAPRECOZERO, MULTICOMIS  }
	
	public FVenda() {

		setTitulo( "Venda" );
		setAtribos( 15, 10, 775, 460 );

	}

	// Fun��o criada para montar a tela conforme a prefer�ncia do usu�rio:
	// com ou sem Refer�ncia de PK;
	private void montaTela() {

		bPrefs = prefs(); // Carrega as prefer�ncias

        if ( bPrefs[POS_PREFS.MULTICOMIS.ordinal()] ) {
        	numComissionados = getNumComissionados();
        	if (numComissionados>0) { 
        		ctrlmc = new CtrlMultiComis(this, con, lcCampos, 
        				numComissionados,
        				"TIPOVENDA", "CODVENDA", txtTipoVenda, txtCodVenda,
        				txtCodVend, "VENDACOMIS");
        		ctrlmc.loadRegraComis( codregrcomis );
        	}
        }
        
		pnCliCab.add( tpnCab );
		/*
		 * , 
			, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
		 */
		//spnCabComis.setHorizontalScrollBar( null );
		pinCabVenda.setFirstFocus( txtCodVenda );
		tpnCab.addTab( "Venda", pinCabVenda );
		if (numComissionados>0) {
		   if (bPrefs[POS_PREFS.USACLASCOMIS.ordinal()]) {
			   aumentacabcomis ++;   
		   }
		   aumentacabcomis += numComissionados;
		   if ( (aumentacabcomis % 2) > 0 ) {
			   aumentacabcomis ++;
		   }
		   aumentacabcomis = ( aumentacabcomis /2 );
		   pinCabComis = new JPanelPad(750,altcabcomis + (aumentacabcomis * 50) );
		   spnCabComis = new JScrollPane(pinCabComis);
  		   spnCabComis.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
		   spnCabComis.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		   tpnCab.addTab( "Comiss�o", spnCabComis );
		} else {
		   pinCabComis = new JPanelPad(750,altcabcomis);
 	       tpnCab.addTab( "Comiss�o", pinCabComis );
		}
		tpnCab.addTab( "Fiscal", pinCabFiscal );

		btAdicOrc.setPreferredSize( new Dimension( 180, 0 ) );
		pnNavCab.add( btAdicOrc, BorderLayout.EAST );

		pnMaster.remove( 2 ); // Remove o JPanelPad predefinido da class FDados
		pnGImp.removeAll(); // Remove os bot�es de impress�o para adicionar logo embaixo
		pnGImp.setLayout( new GridLayout( 1, 4 ) ); // redimensiona o painel de impress�o
		pnGImp.setPreferredSize( new Dimension( 280, 26 ) );
		pnGImp.add( btPrevimp );
		pnGImp.add( btImp );
		pnGImp.add( btFechaVenda );
		pnGImp.add( btConsPgto );
		pnGImp.add( btObs );// Agora o painel est� maior

		pnTot.setPreferredSize( new Dimension( 110, 200 ) ); // JPanelPad de Totais
		pnTot.add( pinTot );
		pnCenter.add( pnTot, BorderLayout.EAST );
		pnCenter.add( spTab, BorderLayout.CENTER );

		JPanelPad pnLab = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
		pnLab.add( new JLabelPad( " Totais:" ) ); // Label do painel de totais

		pnMaster.add( pnCenter, BorderLayout.CENTER );

		// FK Cliente
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtDescCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "NomeCli", "Nome do cliente", ListaCampos.DB_SI, false ) );
		txtNomeCli.setSize( 197, 20 );
		lcCli.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pg.", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.comis.", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtEstCli, "UfCli", "UF", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli );

		// FK Vendedor
		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.Venda", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.add( new GuardaCampo( txtCodClComis, "CodClComis", "C�d.c.comis.", ListaCampos.DB_SI, false ) );
		lcVendedor.add( new GuardaCampo( txtPercComisVenda, "PercComVend", "% Comis.", ListaCampos.DB_SI, false ) );
		lcVendedor.add( new GuardaCampo( cbAtivo, "AtivoComis", "Ativo", ListaCampos.DB_SI, false ) );
		lcVendedor.setWhereAdic( "ATIVOCOMIS='S'" );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		lcVendedor.setQueryCommit( false );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor );

		// FK Plano de Pagamento
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pg.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descri��o plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.setWhereAdic( "ATIVOPLANOPAG='S' AND CVPLANOPAG IN ('V','A')" );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );		
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );

		// FK S�rie
		lcSerie.add( new GuardaCampo( txtCodSerie, "Serie", "S�rie", ListaCampos.DB_PK, false ) );
		lcSerie.add( new GuardaCampo( txtDocVenda, "DocSerie", "Doc. atual", ListaCampos.DB_SI, false ) );
		lcSerie.montaSql( false, "SERIE", "LF" );
		lcSerie.setQueryCommit( false );
		lcSerie.setReadOnly( true );
		txtCodSerie.setTabelaExterna( lcSerie );

		// FK de Lotes
		lcLote.add( new GuardaCampo( txtCodLote, "CodLote", "Lote", ListaCampos.DB_PK, txtDescLote, false ) );
		lcLote.add( new GuardaCampo( txtDescLote, "VenctoLote", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcLote.add( new GuardaCampo( txtSldLiqProd, "SldLiqLote", "Saldo", ListaCampos.DB_SI, false ) );
		lcLote.setDinWhereAdic( "CODPROD=#N AND (VENCTOLOTE >= #D OR #S IN('DV','PE'))", txtCodProd );
		lcLote.setDinWhereAdic( "", txtDtSaidaVenda );
		lcLote.setDinWhereAdic( "", txtTipoMov );
		lcLote.montaSql( false, "LOTE", "EQ" );
		lcLote.setQueryCommit( false );
		lcLote.setReadOnly( true );
		txtCodLote.setTabelaExterna( lcLote );
		txtDescLote.setListaCampos( lcLote );
		txtDescLote.setNomeCampo( "VenctoLote" );
		txtDescLote.setLabel( "Vencimento" );

		// FK de Classifica��o Fiscal (� acionada tamb�m quando o listaCampos de produtos � acionado)

		lcFisc.add( new GuardaCampo( txtCodFisc, "CodFisc", "C�d.fisc.", ListaCampos.DB_PK, txtDescFisc, false ) );
		lcFisc.add( new GuardaCampo( txtDescFisc, "DescFisc", "Descri��o fiscal", ListaCampos.DB_SI, false ) );
		lcFisc.add( new GuardaCampo( txtAliqIPIFisc, "AliqIPIFisc", "% IPI", ListaCampos.DB_SI, false ) );
		lcFisc.add( new GuardaCampo( txtAliqFisc, "AliqFisc", "% ICMS", ListaCampos.DB_SI, false ) );
		lcFisc.montaSql( false, "CLFISCAL", "LF" );
		lcFisc.setQueryCommit( false );
		lcFisc.setReadOnly( true );
		txtCodFisc.setTabelaExterna( lcFisc );
		txtDescFisc.setListaCampos( lcFisc );

		// FK de Natureza de Opera��o (� acionada tamb�m quando o listaCampos de Classifica��o Fiscal � acionado)

		lcNat.add( new GuardaCampo( txtCodNat, "CodNat", "CFOP", ListaCampos.DB_PK, false ) );
		lcNat.add( new GuardaCampo( txtDescNat, "DescNat", "Descri��o da CFOP", ListaCampos.DB_SI, false ) );
		lcNat.montaSql( false, "NATOPER", "LF" );
		lcNat.setQueryCommit( false );
		lcNat.setReadOnly( true );
		txtCodNat.setTabelaExterna( lcNat );
		txtDescNat.setListaCampos( lcNat );

		// FK de Almoxarifado

		lcAlmox.add( new GuardaCampo( txtCodAlmoxItVenda, "codalmox", "Cod.Almox.", ListaCampos.DB_PK, false ) );
		lcAlmox.montaSql( false, "ALMOX", "EQ" );
		lcAlmox.setQueryCommit( false );
		lcAlmox.setReadOnly( true );
		txtCodAlmoxItVenda.setTabelaExterna( lcAlmox );

		// FK de Tratamento Tribut�rio (� acionada tamb�m quando o listaCampos de Tratamento tribut�rio � acionado)

		lcTratTrib.add( new GuardaCampo( txtCodTratTrib, "CodTratTrib", "C�d.tr.trib.", ListaCampos.DB_PK, false ) );
		lcTratTrib.montaSql( false, "TRATTRIB", "LF" );
		lcTratTrib.setQueryCommit( false );
		lcTratTrib.setReadOnly( true );
		txtCodTratTrib.setTabelaExterna( lcTratTrib );

		// ListaCampos de Totais (� acionada pelo listaCampos de Venda)

		lcVenda2.add( new GuardaCampo( txtCodVenda, "CodVenda", "N.pedido", ListaCampos.DB_PK, false ) );
		// lcVenda2.add(new GuardaCampo(txtTipoVenda, "TipoVenda", "Tp.Venda",ListaCampos.DB_PK, false));
		lcVenda2.add( new GuardaCampo( txtVlrFreteVenda, "VlrFreteVenda", "Vlr. frete", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrComisVenda, "VlrComisVenda", "Vlr. comis.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtMedComisVenda, "PercMComisVenda", "Med. comis.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrBaseICMSVenda, "VlrBaseICMSVenda", "Vlr. base ICMS", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrICMSVenda, "VlrICMSVenda", "Vlr. ICMS", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrIPIVenda, "VlrIPIVenda", "Vlr. IPI", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrPisVenda, "VlrPisVenda", "Vlr. PIS", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrCofinsVenda, "VlrCofinsVenda", "Vlr. COFINS", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrIRVenda, "VlrIRVenda", "Vlr. I.R.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrCSocialVenda, "VlrCSocialVenda", "Vlr. c.social.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrProdVenda, "VlrProdVenda", "Vlr. prod.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrDescVenda, "VlrDescItVenda", "Vlr. desc.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrLiqVenda, "VlrLiqVenda", "Vlr. liq.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrBrutVenda, "VlrProdVenda", "Vlr. prod.", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrBaseISSVenda, "VlrBaseISSVenda", "Vlr. base ISS", ListaCampos.DB_SI, false ) );
		lcVenda2.add( new GuardaCampo( txtVlrISSVenda, "VlrISSVenda", "Vlr. ISS", ListaCampos.DB_SI, false ) );
		lcVenda2.setWhereAdic( "TIPOVENDA='V'" );
		lcVenda2.montaSql( false, "VENDA", "VD" );
		lcVenda2.setQueryCommit( false );
		lcVenda2.setReadOnly( true );

		// lc para trazer classificacao da comissao

		lcClComis.add( new GuardaCampo( txtCodClComis, "CodClComis", "C�d.c.comis.", ListaCampos.DB_PK, false ) );
		lcClComis.add( new GuardaCampo( txtDescClComis, "DescClComis", "Descri��o da classifica��o da comiss�o", ListaCampos.DB_SI, false ) );
		lcClComis.montaSql( false, "CLCOMIS", "VD" );
		lcClComis.setQueryCommit( false );
		lcClComis.setReadOnly( true );
		txtCodClComis.setTabelaExterna( lcClComis );

		// Coloca os comentrio nos bot�es

		btFechaVenda.setToolTipText( "Fechar a venda (F4)" );
		btConsPgto.setToolTipText( "Consulta pagamentos (F5)" );
		btObs.setToolTipText( "Observa��es (Ctrl + O)" );

		// Desativa as os TextFields para que os usu�rios n�o possam mexer

		txtCodSerie.setAtivo( false );
		txtDocVenda.setAtivo( false );
		txtVlrFreteVenda.setAtivo( false );
		txtVlrComisVenda.setAtivo( false );
		txtMedComisVenda.setAtivo( false );
		txtVlrICMSVenda.setAtivo( false );
		txtVlrIPIVenda.setAtivo( false );
		txtVlrPisVenda.setAtivo( false );
		txtVlrCofinsVenda.setAtivo( false );
		txtVlrIRVenda.setAtivo( false );
		txtVlrCSocialVenda.setAtivo( false );
		txtVlrBaseICMSVenda.setAtivo( false );
		txtVlrProdVenda.setAtivo( false );
		txtVlrDescVenda.setAtivo( false );
		txtVlrLiqVenda.setAtivo( false );
		txtVlrBaseISSVenda.setAtivo( false );
		txtVlrISSVenda.setAtivo( false );

		// Adiciona os Listeners

		txtDescProd.setToolTipText( "Clique aqui duas vezes para alterar a descri��o." );
		txtDescProd.addMouseListener( new MouseAdapter() {

			public void mouseClicked( MouseEvent mevt ) {

				if ( mevt.getClickCount() == 2 ) {
					mostraTelaDecricao( txaObsItVenda, txtCodProd.getVlrInteger().intValue(), txtDescProd.getVlrString() );
				}
			}
		} );

		btFechaVenda.addActionListener( this );
		btConsPgto.addActionListener( this );
		btObs.addActionListener( this );
		btAdicOrc.addActionListener( this );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btAltComis.addActionListener( this );

		txtPercDescItVenda.addFocusListener( this );
		txtVlrDescItVenda.addFocusListener( this );
		txtPercComItVenda.addFocusListener( this );
		txtVlrComisItVenda.addFocusListener( this );
		txtVlrProdItVenda.addFocusListener( this );
		txtQtdItVenda.addFocusListener( this );
		txtCodNat.addFocusListener( this );
		txtPrecoItVenda.addFocusListener( this );
		txtPercICMSItVenda.addFocusListener( this );
		txtAliqIPIItVenda.addFocusListener( this );

		lcCampos.addCarregaListener( this );
		lcVendedor.addCarregaListener( this );
		lcCli.addCarregaListener( this );
		lcFisc.addCarregaListener( this );
		lcProd.addCarregaListener( this );
		lcProd2.addCarregaListener( this );
		lcNat.addCarregaListener( this );
		lcVenda2.addCarregaListener( this );
		lcDet.addCarregaListener( this );
		lcPlanoPag.addCarregaListener( this );
		lcTipoMov.addCarregaListener( this );

		lcCampos.addPostListener( this );
		lcDet.addPostListener( this );

		lcCampos.addInsertListener( this );
		lcDet.addInsertListener( this );

		lcDet.addDeleteListener( this );

		lbStatus.setForeground( Color.WHITE );
		lbStatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbStatus.setOpaque( true );
		lbStatus.setVisible( false );

		setImprimir( true );
        
		txtVlrLiqItVenda.setAtivo( false );

		txtCodNat.setAtivo( bPrefs[ POS_PREFS.NATVENDA.ordinal() ] );

		txtAliqIPIItVenda.setAtivo( bPrefs[ POS_PREFS.IPIVENDA.ordinal() ] );
		txtVlrIPIItVenda.setAtivo( bPrefs[ POS_PREFS.IPIVENDA.ordinal() ] );

		// Desativa as os TextFields para que os usu�rios n�o possam mexer
		// ALTERADO PARA BUSCA DO PREEFERENCIAS.
		txtBaseICMSItVenda.setAtivo( bPrefs[ POS_PREFS.ICMSVENDA.ordinal() ] );
		txtPercICMSItVenda.setAtivo( bPrefs[ POS_PREFS.ICMSVENDA.ordinal() ] );
		txtVlrICMSItVenda.setAtivo( bPrefs[ POS_PREFS.ICMSVENDA.ordinal() ] );

		// FK Produto

		// pra definir o tamanho na tela de pesquisa.
		txtDescProdAux.setSize( 150, 20 );

		lcProd.add( new GuardaCampo( txtCodProd, "codprod", "C�d.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produtos", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Ref.prod.", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtDescProdAux, "DescAuxProd", "Descri��o auxiliar do produtos", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "C/Lote", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodFisc, "CodFisc", "C�d.fisc.", ListaCampos.DB_FK, false ) );
		lcProd.add( new GuardaCampo( txtPercComItVenda, "ComisProd", "% Comis.", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtSldLiqProd, "SldLiqProd", "Saldo", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtVerifProd, "VerifProd", "Verif. custo", ListaCampos.DB_SI, false ) );

		String sWhereAdicProd = "ATIVOPROD='S' AND TIPOPROD IN ('P','S','F'" + 
		    ( bPrefs[ POS_PREFS.VENDAMATPRIM.ordinal() ] ? ",'M'" : "" ) + ")";
				
		lcProd.setWhereAdic( sWhereAdicProd );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );
		txtCodProd.setTabelaExterna( lcProd );

		// FK do produto (*Somente em caso de refer�ncias este listaCampos
		// Trabalha como gatilho para o listaCampos de produtos, assim
		// carregando o c�digo do produto que ser� armazenado no Banco)
		lcProd2.add( new GuardaCampo( txtRefProd, "RefProd", "Ref.prod.", ListaCampos.DB_PK, false ) );
		lcProd2.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtDescProdAux, "DescAuxProd", "Descri��o auxiliar do produtos", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "C/Lote", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodFisc, "CodFisc", "C�d.fisc.", ListaCampos.DB_FK, false ) );
		lcProd2.add( new GuardaCampo( txtPercComItVenda, "ComisProd", "% comis.", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtSldLiqProd, "SldLiqProd", "Saldo", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtVerifProd, "VerifProd", "Verif. custo", ListaCampos.DB_SI, false ) );
		txtRefProd.setNomeCampo( "RefProd" );
		txtRefProd.setListaCampos( lcDet );
		lcProd2.setWhereAdic( sWhereAdicProd );
		lcProd2.montaSql( false, "PRODUTO", "EQ" );
		lcProd2.setQueryCommit( false );
		lcProd2.setReadOnly( true );
		txtRefProd.setTabelaExterna( lcProd2 );

		// FK Tipo de movimentos
		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( txtCodSerie, "Serie", "S�rie", ListaCampos.DB_FK, false ) );
		lcTipoMov.add( new GuardaCampo( txtTipoMov, "TipoMov", "Tipo mov.", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( txtESTipoMov, "ESTipoMov", "E/S", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( chbImpPedTipoMov, "ImpPedTipoMov", "Imp.ped.", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( chbImpNfTipoMov, "ImpNfTipoMov", "Imp.NF", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( chbImpBolTipoMov, "ImpBolTipoMov", "Imp.bol.", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( chbImpRecTipoMov, "ImpRecTipoMov", "Imp.rec.", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( chbReImpNfTipoMov, "ReImpNfTipoMov", "Reimp.NF", ListaCampos.DB_SI, false ) );
		lcTipoMov.add( new GuardaCampo( txtCodRegrComis, "CodRegrComis", "C�d.regr.comis.", ListaCampos.DB_SI, false ) );
		/*
		 * SELECT CODTIPOMOV, DESCTIPOMOV FROM EQTIPOMOV WHERE ( TUSUTIPOMOV='S' OR EXISTS (SELECT * FROM EQTIPOMOVUSU TU WHERE TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS=4 AND TU.CODFILIALUS=1 AND TU.IDUSU='sysdba') ) ORDER
		 * BY 1
		 */
		lcTipoMov.setWhereAdic( "( (ESTIPOMOV = 'S' OR TIPOMOV IN ('PV','DV')) AND " + " ( TUSUTIPOMOV='S' OR	EXISTS (SELECT * FROM EQTIPOMOVUSU TU " + "WHERE TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND " + "TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS="
				+ Aplicativo.iCodEmp + " AND " + "TU.CODFILIALUS=" + ListaCampos.getMasterFilial( "SGUSUARIO" ) + " AND TU.IDUSU='" + Aplicativo.strUsuario + "') ) )" );

		if ( bPrefs[ POS_PREFS.TRAVATMNFVD.ordinal() ] ) {
			txtFiscalTipoMov1.setText( "S" );
			txtFiscalTipoMov2.setText( "N" );
			lcTipoMov.setDinWhereAdic( "FISCALTIPOMOV IN(#S,#S)", txtFiscalTipoMov1 );
			lcTipoMov.setDinWhereAdic( "", txtFiscalTipoMov2 );
		}

		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );

		// Adiciona os componentes na tela e no ListaCompos da venda
		setListaCampos( lcCampos );
		setAltCab( 160 );
		setPainel( pinCabVenda );
		adicCampo( txtCodVenda, 7, 20, 90, 20, "CodVenda", "N. pedido", ListaCampos.DB_PK, true );
		// adicCampoInvisivel(txtTipoVenda,"tipovenda","Tp.Venda",ListaCampos.DB_PK,true);
		adicCampo( txtCodTipoMov, 100, 20, 77, 20, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, true );
		adicDescFK( txtDescTipoMov, 180, 20, 197, 20, "DescTipoMov", "Descri��o do tipo de movimento" );
		adicCampo( txtCodSerie, 380, 20, 77, 20, "Serie", "S�rie", ListaCampos.DB_FK, false );
		adicCampo( txtDocVenda, 460, 20, 77, 20, "DocVenda", "N doc.", ListaCampos.DB_SI, false );
		adicCampo( txtDtEmitVenda, 540, 20, 97, 20, "DtEmitVenda", "Data emis.", ListaCampos.DB_SI, true );
		adicCampo( txtDtSaidaVenda, 640, 20, 97, 20, "DtSaidaVenda", "Data sa�da", ListaCampos.DB_SI, true );
		adicCampo( txtCodCli, 7, 60, 80, 20, "CodCli", "C�d. cli.", ListaCampos.DB_FK, txtDescCli, true );
		adicDescFK( txtDescCli, 90, 60, 197, 20, "RazCli", "Raz�o social do cliente" );
		adicCampo( txtCodPlanoPag, 290, 60, 77, 20, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_FK, txtDescPlanoPag, true );
		adicDescFK( txtDescPlanoPag, 370, 60, 197, 20, "DescPlanoPag", "Descri��o do plano de pag." );
		adicCampo( txtPedCliVenda, 570, 60, 75, 20, "PedCliVenda", "N.ped.cli.", ListaCampos.DB_SI, false );
		adic( lbStatus, 649, 60, 95, 20 );

		setPainel( pinCabComis );

		adicCampo( txtCodVend, 7, 20, 80, 20, "CodVend", "C�d.comis.", ListaCampos.DB_FK, txtDescVend, true );
		adicDescFK( txtDescVend, 90, 20, 197, 20, "NomeVend", "Nome do comissionado" );
		if ( bPrefs[ POS_PREFS.USACLASCOMIS.ordinal() ] ) {
			adicCampo( txtCodClComis, 290, 20, 80, 20, "CodClComis", "C�d.c.comis.", ListaCampos.DB_FK, txtDescClComis, true );
			adicDescFK( txtDescClComis, 373, 20, 260, 20, "DescClComis", "Descri��o da class. de comis." );
			adicCampo( txtPercComisVenda, 640, 20, 57, 20, "PercComisVenda", "% comis.", ListaCampos.DB_SI, true );
			adic( new JLabelPad( "Vlr. comis." ), 7, 40, 100, 20 );
			adic( txtVlrComisVenda, 7, 60, 100, 20 );
			adic( new JLabelPad( "% M. comis." ), 110, 40, 100, 20 );
			adic( txtMedComisVenda, 110, 60, 80, 20 );
			adic( btAltComis, 200, 50, 30, 30 );
		}
		else {
			adicCampo( txtPercComisVenda, 290, 20, 57, 20, "PercComisVenda", "% comis.", ListaCampos.DB_SI, true );
		}
		
		adicCampoInvisivel( txtStatusVenda, "StatusVenda", "Sit.", ListaCampos.DB_SI, false );
		
		montaMultiComis();
		
		setListaCampos( lcCampos );

		setPainel( pinCabFiscal );

		adicCampo( txtVlrIPIVenda, 7, 20, 80, 20, "VlrIPIVenda", "Vlr. IPI", ListaCampos.DB_SI, false );
		adicCampo( txtVlrPisVenda, 90, 20, 80, 20, "VlrPisVenda", "Vlr. PIS", ListaCampos.DB_SI, false );
		adicCampo( txtVlrBaseICMSVenda, 173, 20, 80, 20, "VlrBaseIcmsVenda", "Base ICMS", ListaCampos.DB_SI, false );
		adicCampo( txtVlrICMSVenda, 256, 20, 80, 20, "VlrICMSVenda", "Vlr. ICMS", ListaCampos.DB_SI, false );
		adicCampo( txtVlrCofinsVenda, 7, 60, 80, 20, "VlrCofinsVenda", "Vlr. PIS", ListaCampos.DB_SI, false );
		adicCampo( txtVlrCSocialVenda, 90, 60, 80, 20, "VlrCSocialVenda", "Vlr. c. social", ListaCampos.DB_SI, false );
		adicCampo( txtVlrIRVenda, 173, 60, 80, 20, "VlrIRVenda", "Vlr. I.R.", ListaCampos.DB_SI, false );
		adicCampo( txtVlrBaseISSVenda, 256, 60, 80, 20, "VlrBaseISSVenda", "Base ISS", ListaCampos.DB_SI, false );
		adicCampo( txtVlrISSVenda, 339, 60, 80, 20, "VlrISSVenda", "Vlr. ISS", ListaCampos.DB_SI, false );

		lcCampos.setWhereAdic( "TIPOVENDA='V'" );
		lcCampos.setWhereAdicMax( "TIPOVENDA='V'" );
		setListaCampos( true, "VENDA", "VD" );

		setAltDet( 100 );
		pinDet = new JPanelPad( 740, 100 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		adicCampo( txtCodItVenda, 7, 20, 30, 20, "CodItVenda", "Item", ListaCampos.DB_PK, true );
		if ( bPrefs[ POS_PREFS.USAREFPROD.ordinal() ] ) {
			txtRefProd.setBuscaAdic( new DLBuscaProd( con, "REFPROD", lcProd2.getWhereAdic() ) );
			adicCampoInvisivel( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_FK, txtDescProd, false );
			adicCampoInvisivel( txtRefProd, "RefProd", "Ref.prod.", ListaCampos.DB_SI, true );
			adic( new JLabelPad( "Ref. prod." ), 40, 0, 60, 20 );
			adic( txtRefProd, 40, 20, 60, 20 );
			txtRefProd.setFK( true );
			txtRefProd.removeKeyListener( this );
		}
		else {
			txtCodProd.setBuscaAdic( new DLBuscaProd( con, "CODPROD", lcProd.getWhereAdic() ) );
			adicCampo( txtCodProd, 40, 20, 60, 20, "CodProd", "C�d.prod.", ListaCampos.DB_FK, txtDescProd, true );
		}

		adicDescFK( txtDescProd, 103, 20, 200, 20, "DescProd", "Descri��o do produto" );
		adicCampo( txtCodLote, 306, 20, 67, 20, "CodLote", "Lote", ListaCampos.DB_FK, txtDescLote, false );
		adicCampo( txtQtdItVenda, 376, 20, 67, 20, "QtdItVenda", "Qtd.", ListaCampos.DB_SI, true );

		txtQtdItVenda.setBuscaAdic( new DLBuscaEstoq( lcDet, lcAlmox, lcProd, con, "qtditvenda" ) );

		adicCampo( txtPrecoItVenda, 446, 20, 67, 20, "PrecoItVenda", "Pre�o", ListaCampos.DB_SI, true );
		adicCampo( txtPercDescItVenda, 516, 20, 47, 20, "PercDescItVenda", "% desc.", ListaCampos.DB_SI, false );
		adicCampo( txtVlrDescItVenda, 566, 20, 67, 20, "VlrDescItVenda", "V. desc.", ListaCampos.DB_SI, false );
		adicCampo( txtPercComItVenda, 636, 20, 45, 20, "PercComisItVenda", "% com.", ListaCampos.DB_SI, false );
		adicCampo( txtVlrComisItVenda, 684, 20, 50, 20, "VlrComisItVenda", "V. com.", ListaCampos.DB_SI, false );

		adicCampo( txtCodNat, 7, 60, 50, 20, "CodNat", "CFOP", ListaCampos.DB_FK, txtDescNat, true );

		adicDescFK( txtDescNat, 60, 60, 167, 20, "DescNat", "Descri��o da CFOP" );
		// txtCodAlmoxItVenda.setSoLeitura(true);
		txtCodAlmoxItVenda.setAtivo( false );
		adicCampo( txtCodAlmoxItVenda, 230, 60, 47, 20, "codalmox", "Cod.ax", ListaCampos.DB_FK, false );
		// colocar aqui o campo de saldo
		adicDescFK( txtSldLiqProd, 280, 60, 67, 20, "SldLiqProd", "Saldo" );
		adicCampo( txtBaseICMSItVenda, 350, 60, 67, 20, "VlrBaseICMSItVenda", "B. ICMS", ListaCampos.DB_SI, false );
		adicCampo( txtPercICMSItVenda, 420, 60, 57, 20, "PercICMSItVenda", "% ICMS", ListaCampos.DB_SI, true );
		adicCampo( txtVlrICMSItVenda, 480, 60, 67, 20, "VlrICMSItVenda", "V. ICMS", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtBaseIPIItVenda, "VlrBaseIPIItVenda", "B. IPI", ListaCampos.DB_SI, false );
		adicCampo( txtAliqIPIItVenda, 550, 60, 47, 20, "PercIPIItVenda", "% IPI", ListaCampos.DB_SI, false );
		adicCampo( txtVlrIPIItVenda, 600, 60, 67, 20, "VlrIPIItVenda", "V. IPI", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrProdItVenda, "VlrProdItVenda", "V. bruto", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtStrDescItVenda, "StrDescItVenda", "Descontos", ListaCampos.DB_SI, false );
		adicDBLiv( txaObsItVenda, "ObsItVenda", "Observa��o", false );
		adicCampoInvisivel( txtOrigFisc, "OrigFisc", "Origem", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodTratTrib, "CodTratTrib", "C�d.tr.trib.", ListaCampos.DB_FK, false );
		adicCampoInvisivel( txtTipoFisc, "TipoFisc", "Tipo fisc.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodMens, "CodMens", "C�d.mens.", ListaCampos.DB_SI, false );
		adicCampo( txtVlrLiqItVenda, 670, 60, 65, 20, "VlrLiqItVenda", "Vlr.item", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodEmpLG, "CodEmpLG", "Emp.log.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodFilialLG, "CodFilialLG", "Filial log.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodLog, "CodLog", "C�d.log.", ListaCampos.DB_SI, false );
		pinTot.adic( new JLabelPad( "Vlr.prod." ), 7, 0, 90, 20 );
		pinTot.adic( txtVlrProdVenda, 7, 20, 90, 20 );
		pinTot.adic( new JLabelPad( "Vlr.desc." ), 7, 40, 90, 20 );
		pinTot.adic( txtVlrDescVenda, 7, 60, 90, 20 );
		pinTot.adic( new JLabelPad( "Vlr.liq." ), 7, 80, 90, 20 );
		pinTot.adic( txtVlrLiqVenda, 7, 100, 90, 20 );
		txtCodNat.setStrMascara( "#.###" );
		lcDet.setWhereAdic( "TIPOVENDA='V'" );
		setListaCampos( true, "ITVENDA", "VD" );

		montaTab();

		int iIniRef = 3;

		if ( bPrefs[ POS_PREFS.USAREFPROD.ordinal() ] ) {
			iIniRef = 4;
		}

		tab.setTamColuna( 30, 0 );
		tab.setTamColuna( 70, 1 );
		tab.setTamColuna( 230, 2 );
		tab.setTamColuna( 70, iIniRef );
		tab.setTamColuna( 80, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 60, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 60, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 200, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 60, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 60, iIniRef++ );
		tab.setTamColuna( 70, iIniRef++ );
		tab.setTamColuna( 80, iIniRef++ );
		tab.setTamColuna( 90, iIniRef++ );
		tab.setAutoRol( true );
	}

	private void montaMultiComis() {
		int row = 0;
		int col = 1;
		int[] cols = {7, 360} ;
		int x, y, alt, larg = 0;
 	    ItemComis[] listComis = null;
		if ( numComissionados > 0 ) {
			if ( bPrefs[ POS_PREFS.USACLASCOMIS.ordinal()]) {
				row = 40;
			} 
		   setPainel( pinCabComis );
		   listComis = ctrlmc.getListComis();
		   for (ItemComis itemcomis: listComis) {
			   if (itemcomis!=null) {
				   setListaCampos( itemcomis.getLcVendaComis() );
				   adicCampoInvisivel( itemcomis.getTxtTipovenda(), itemcomis.getTxtTipovenda().getNomeCampo(), 
						   "Tipo", ListaCampos.DB_PK, true );
				   adicCampoInvisivel( itemcomis.getTxtCodvenda(), itemcomis.getTxtCodvenda().getNomeCampo(), 
						   "Pedido", ListaCampos.DB_PK, true );
				   adicCampoInvisivel( itemcomis.getTxtSeqvc(), itemcomis.getTxtSeqvc().getNomeCampo(), 
						   "Seq.", ListaCampos.DB_PK, true );
				   adicCampo(itemcomis.getTxtCodvend(),cols[col], row+20 , 80, 20, 
						   itemcomis.getTxtCodvend().getNomeCampo(), itemcomis.getLbCodvend().getText(), 
						   ListaCampos.DB_FK, itemcomis.getTxtNomevend(), "S".equals(itemcomis.getObrigitrc()) );
				   adicDescFK( itemcomis.getTxtNomevend(), cols[col] + 83, row+20 , 200, 20, 
						   itemcomis.getTxtNomevend().getNomeCampo(), itemcomis.getLbNomevend().getText() );
				   adicCampo(itemcomis.getTxtPerccomis(), cols[col] + 286, row+20 , 55, 20, 
						   itemcomis.getTxtPerccomis().getNomeCampo(), itemcomis.getLbPercvend().getText(),
						   ListaCampos.DB_SI, "S".equals(itemcomis.getObrigitrc()));
				   /*adic(itemcomis.getLbCodvend(), cols[col], row+5, 80, 10);
				   adic(itemcomis.getTxtCodvend(), cols[col], row+20 , 80, 20);
				   adic(itemcomis.getLbNomevend(), cols[col] + 83, row+5, 200, 10);
				   adic(itemcomis.getTxtNomevend(), cols[col] + 83, row+20 , 200, 20);
				   adic(itemcomis.getLbPercvend(), cols[col] + 286, row+5, 55, 10);
				   adic(itemcomis.getTxtPerccomis(), cols[col] + 286, row+20 , 55, 20); */ 
				   if (col==0) {
					   col = 1;
				   } else {
					   row+=40 ;
					   col=0;
				   }
			   }
		   }
		   //Rectangle rtgle = pinCabComis.getBounds();
		   //rtgle.height = rtgle.height + 40;
		   //spnCabComis.repaint();
		   //pinCabComis.setSize( (int) rtgle.getWidth(), row + 40 );
		  // pinCabComis.setAutoscrolls( true );
		   //pinCabComis.repaint();
		   //pinCabComis.setSize( new Dimension( (int) rtgle.getWidth(), (int) rtgle.getHeight()+200 ) );
		   //pinCabComis.setBounds(  );
		}
	}
	
	private int getNumComissionados() {
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   int result = 0;
	   try {
		  ps = con.prepareStatement( "SELECT FIRST 1 RC.CODREGRCOMIS, COUNT(*) " +
		  		"FROM VDREGRACOMIS RC, VDITREGRACOMIS IRC " +
		  		"WHERE IRC.CODEMP=RC.CODEMP AND IRC.CODFILIAL=RC.CODFILIAL AND " +
		  		"IRC.CODREGRCOMIS=RC.CODREGRCOMIS AND RC.CODEMP=? AND RC.CODFILIAL=? " +
		  		"GROUP BY 1 ORDER BY 2 DESC" );
		  ps.setInt( 1, Aplicativo.iCodEmp );
		  ps.setInt( 2, ListaCampos.getMasterFilial( "VDREGRACOMIS" ) );
		  rs = ps.executeQuery();
		  if (rs.next()) {
			  result = rs.getInt( 2 );
		  }
		  rs.close();
		  ps.close();
		  if (!con.getAutoCommit()) {
			  con.commit();
		  }
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
	   return result;
	}
	
	private void abreAdicOrc() {

		if ( !Aplicativo.telaPrincipal.temTela( "Busca or�amento" ) ) {
			DLAdicOrc tela = new DLAdicOrc( this, "V" );
			Aplicativo.telaPrincipal.criatela( "Orcamento", tela, con );
		}
	}

	private void altComisVend() {

		if ( lcCampos.getStatus() != ListaCampos.LCS_SELECT ) {
			return;
		}

		DLAltComisVend dl = new DLAltComisVend( this, txtCodVenda.getVlrInteger().intValue(), txtMedComisVenda.getVlrBigDecimal(), con );
		dl.setVisible( true );
		dl.dispose();

		lcCampos.carregaDados();

	}

	private void bloqvenda() {

		PreparedStatement ps = null;
		String sSql = null;
		String sTipoVenda = null;
		int iCodVenda = 0;

		try {

			iCodVenda = txtCodVenda.getVlrInteger().intValue();

			if ( iCodVenda != 0 ) {

				sTipoVenda = "V";

				sSql = "EXECUTE PROCEDURE VDBLOQVENDASP(?,?,?,?,?)";

				ps = con.prepareStatement( sSql );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
				ps.setString( 3, sTipoVenda );
				ps.setInt( 4, iCodVenda );
				ps.setString( 5, "S" );
				ps.executeUpdate();

				ps.close();

				if ( !con.getAutoCommit() ) {
					con.commit();
				}
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro bloqueando a venda!\n" + err.getMessage(), true, con, err );
		}
	}

	private void calcDescIt() {

		if ( txtPercDescItVenda.floatValue() != 0 ) {
			txtVlrDescItVenda.setVlrBigDecimal( 
					new BigDecimal( 
							Funcoes.arredFloat( txtVlrProdItVenda.floatValue() * txtPercDescItVenda.floatValue() / 100, casasDecFin ) ) );
		}
	}

	private void calcComisIt() {

		if ( txtPercComItVenda.floatValue() >= 0 ) {
			txtVlrComisItVenda.setVlrBigDecimal( 
					new BigDecimal( 
							Funcoes.arredFloat( ( txtVlrProdItVenda.floatValue() - txtVlrDescItVenda.floatValue() ) 
									* txtPercComItVenda.floatValue() / 100 * txtPercComisVenda.floatValue() / 100, casasDecFin ) ) );
		}
	}

	private void calcVlrProd() {

		txtVlrProdItVenda.setVlrBigDecimal( calcVlrProd( txtPrecoItVenda.getVlrBigDecimal(), txtQtdItVenda.getVlrBigDecimal() ) );
	}

	private void calcImpostos( boolean bBuscaBase ) {

		float fRed = 0;
		float fVlrProd = 0;
		float fBaseIPI = 0;
		float fBaseICMS = 0;
		float fICMS = 0;
		float fIPI = 0;
		String tpredicmsfisc = null;
	
		try {

			tpredicmsfisc = txtTpRedIcmsFisc.getVlrString();
			fRed = txtRedFisc.getVlrBigDecimal() != null ? txtRedFisc.floatValue() : 0;
			fVlrProd = calcVlrTotalProd( txtVlrProdItVenda.getVlrBigDecimal(), txtVlrDescItVenda.getVlrBigDecimal() ).floatValue();

			if ( !bBuscaBase ) {
				fBaseICMS = txtBaseICMSItVenda.floatValue();
			}

			if ( txtTipoFisc.getText().trim().equals( "II" ) ) {

				txtPercICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
				txtVlrICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );

				if ( bBuscaBase ) {
					txtBaseICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
				}

				if ( txtVlrIPIItVenda.getAtivo() ) {
					txtUltCamp = txtVlrIPIItVenda;
				}
				else if ( txtAliqIPIItVenda.getAtivo() ) {
					txtUltCamp = txtAliqIPIItVenda;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtPercComItVenda.getAtivo() ) {
					txtUltCamp = txtPercComItVenda;
				}
				else {
					txtUltCamp = txtVlrComisItVenda;
				}
			}
			else if ( txtTipoFisc.getText().trim().equals( "FF" ) ) {

				txtPercICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
				txtVlrICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );

				if ( bBuscaBase ) {
					txtBaseICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
				}

				if ( txtVlrIPIItVenda.getAtivo() ) {
					txtUltCamp = txtVlrIPIItVenda;
				}
				else if ( txtAliqIPIItVenda.getAtivo() ) {
					txtUltCamp = txtAliqIPIItVenda;
				}
				else if ( txtVlrICMSItVenda.getAtivo() ) {
					txtUltCamp = txtVlrICMSItVenda;
				}
				else if ( txtPercICMSItVenda.getAtivo() ) {
					txtUltCamp = txtPercICMSItVenda;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtPercComItVenda.getAtivo() ) {
					txtUltCamp = txtPercComItVenda;
				}
				else {
					txtUltCamp = txtVlrComisItVenda;
				}
			}
			else if ( txtTipoFisc.getText().trim().equals( "NN" ) ) {

				txtPercICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
				txtVlrICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );

				if ( bBuscaBase ) {
					txtBaseICMSItVenda.setVlrBigDecimal( new BigDecimal( "0" ) );
				}

				if ( txtVlrIPIItVenda.getAtivo() ) {
					txtUltCamp = txtVlrIPIItVenda;
				}
				else if ( txtAliqIPIItVenda.getAtivo() ) {
					txtUltCamp = txtAliqIPIItVenda;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtPercComItVenda.getAtivo() ) {
					txtUltCamp = txtPercComItVenda;
				}
				else {
					txtUltCamp = txtVlrComisItVenda;
				}

			}
			else if ( txtTipoFisc.getText().trim().equals( "TT" ) ) {

				if ( fVlrProd > 0 ) {

					if ( bBuscaBase ) {
						if ("B".equals(tpredicmsfisc)) {
							fBaseICMS = Funcoes.arredFloat( fVlrProd - fVlrProd * fRed / 100, casasDecFin );
						} else {
							fBaseICMS = Funcoes.arredFloat( fVlrProd , casasDecFin );
						}
					}

					fBaseIPI = fVlrProd;
					if ( ("V".equals(tpredicmsfisc)) && (fRed>0)) {
						fICMS = Funcoes.arredFloat( fBaseICMS * txtPercICMSItVenda.floatValue() / 100, casasDecFin );
						fICMS -= Funcoes.arredFloat( fICMS * fRed /100, casasDecFin );
					} else {
						fICMS = Funcoes.arredFloat( fBaseICMS * txtPercICMSItVenda.floatValue() / 100, casasDecFin );
					}
					fIPI = Funcoes.arredFloat( fBaseIPI * txtAliqIPIItVenda.floatValue() / 100, casasDecFin );

				}

				txtVlrICMSItVenda.setVlrBigDecimal( new BigDecimal( fICMS ) );
				txtBaseICMSItVenda.setVlrBigDecimal( new BigDecimal( fBaseICMS ) );
				txtVlrIPIItVenda.setVlrBigDecimal( new BigDecimal( fIPI ) );
				txtBaseIPIItVenda.setVlrBigDecimal( new BigDecimal( fBaseIPI ) );
				txtAliqIPIItVenda.setVlrBigDecimal( txtAliqIPIFisc.getVlrBigDecimal() );

				if ( txtVlrIPIItVenda.getAtivo() ) {
					txtUltCamp = txtVlrIPIItVenda;
				}
				else if ( txtAliqIPIItVenda.getAtivo() ) {
					txtUltCamp = txtAliqIPIItVenda;
				}
				else if ( txtVlrICMSItVenda.getAtivo() ) {
					txtUltCamp = txtVlrICMSItVenda;
				}
				else if ( txtPercICMSItVenda.getAtivo() ) {
					txtUltCamp = txtPercICMSItVenda;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtCodNat.getAtivo() ) {
					txtUltCamp = txtCodNat;
				}
				else if ( txtPercComItVenda.getAtivo() ) {
					txtUltCamp = txtPercComItVenda;
				}
				else {
					txtUltCamp = txtVlrComisItVenda;
				}
			}

			txtVlrLiqItVenda.setVlrBigDecimal( calcVlrTotalProd( txtVlrProdItVenda.getVlrBigDecimal(), txtVlrDescItVenda.getVlrBigDecimal() ) );

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void getICMS() {

		if ( txtAliqFisc.floatValue() > 0 ) {
			txtPercICMSItVenda.setVlrBigDecimal( txtAliqFisc.getVlrBigDecimal() );
			return; // Ele cai fora porque se existe um valor no CLFISCAL ele nem busca a Aliq. por Natureza da opera�ao.
		}

		String sSQL = "SELECT PERCICMS FROM LFBUSCAICMSSP(?,?,?,?)";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement( sSQL );
			ps.setString( 1, txtCodNat.getVlrString() );
			ps.setString( 2, txtEstCli.getVlrString() );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, Aplicativo.iCodFilialMz );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				txtPercICMSItVenda.setVlrBigDecimal( new BigDecimal( rs.getString( 1 ) ) );
			}

			calcImpostos( true );

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar percentual de ICMS!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	private void getLote() {
		txtCodLote.setVlrString( getLote( txtCodProd.getVlrInteger().intValue(), bPrefs[ POS_PREFS.CONTESTOQ.ordinal() ] ) );
		lcLote.carregaDados();
	}

	/**
	 * Busca da Natureza de Opera��o . Busca a natureza de opera��o atrav�s da tabela de regras fiscais.
	 * 
	 */
	private void getCFOP() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CODNAT FROM LFBUSCANATSP (?,?,?,?,?,?,?,?,?,?,?,?)";

		try {

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodFilial );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, lcProd.getCodFilial() );
			ps.setInt( 4, txtCodProd.getVlrInteger().intValue() );
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, lcCli.getCodFilial() );
			ps.setInt( 7, txtCodCli.getVlrInteger().intValue() );
			ps.setNull( 8, Types.INTEGER );
			ps.setNull( 9, Types.INTEGER );
			ps.setNull( 10, Types.INTEGER );
			ps.setInt( 11, lcTipoMov.getCodFilial() );
			ps.setInt( 12, txtCodTipoMov.getVlrInteger().intValue() );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				txtCodNat.setVlrString( rs.getString( "CODNAT" ) );
				lcNat.carregaDados();
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar natureza da opera��o!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	private void getTratTrib() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT ORIGFISC,CODTRATTRIB, REDFISC,TIPOFISC, " +
				"CODMENS,ALIQFISC,ALIQIPIFISC, TPREDICMSFISC " + 
				"FROM LFBUSCAFISCALSP(?,?,?,?,?,?,?)";

		try {

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodFilial );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, lcProd.getCodFilial() );
			ps.setInt( 4, txtCodProd.getVlrInteger().intValue() );
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, lcCli.getCodFilial() );
			ps.setInt( 7, txtCodCli.getVlrInteger().intValue() );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				txtOrigFisc.setVlrString( rs.getString( "ORIGFISC" ) );
				txtCodTratTrib.setVlrString( rs.getString( "CODTRATTRIB" ) );
				txtRedFisc.setVlrBigDecimal( new BigDecimal( rs.getString( "REDFISC" ) != null ? rs.getString( "REDFISC" ) : "0" ) );
				txtTipoFisc.setVlrString( rs.getString( "TIPOFISC" ) );
				txtCodMens.setVlrString( rs.getString( "CODMENS" ) );
				txtAliqFisc.setVlrString( rs.getString( "ALIQFISC" ) );
				txtAliqIPIFisc.setVlrBigDecimal( new BigDecimal( rs.getString( "ALIQIPIFISC" ) != null ? rs.getString( "ALIQIPIFISC" ) : "0" ) );
				txtTpRedIcmsFisc.setVlrString( rs.getString("TPREDICMSFISC") );
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar tratamento tribut�rio!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

	}

	public Vector<Object> getParansDesconto() {

		Vector<Object> param = new Vector<Object>();
		param.addElement( txtStrDescItVenda );
		param.addElement( txtPrecoItVenda );
		param.addElement( txtVlrDescItVenda );
		param.addElement( txtQtdItVenda );
		return param;
	}

	public String[] getParansPass() {

		return new String[] { "venda", 
				String.valueOf( txtCodVenda.getVlrInteger().intValue() ), 
				String.valueOf( txtCodItVenda.getVlrInteger().intValue() ), 
				String.valueOf( txtCodProd.getVlrInteger().intValue() ), 
				String.valueOf( txtVlrProdItVenda.getVlrInteger().intValue() ) };
	}

	public int[] getParansPreco() {

		int[] iRetorno = { 
				txtCodProd.getVlrInteger().intValue(), 
				txtCodCli.getVlrInteger().intValue(), 
				Aplicativo.iCodEmp, 
				ListaCampos.getMasterFilial( "VDCLIENTE" ), 
				txtCodPlanoPag.getVlrInteger().intValue(), 
				Aplicativo.iCodEmp, 
				ListaCampos.getMasterFilial( "FNPLANOPAG" ),
				txtCodTipoMov.getVlrInteger().intValue(), 
				Aplicativo.iCodEmp, 
				ListaCampos.getMasterFilial( "EQTIPOMOV" ), 
				Aplicativo.iCodEmp, 
				Aplicativo.iCodFilial, 
				txtCodVenda.getVlrInteger().intValue(), 
				Aplicativo.iCodEmp, 
				ListaCampos.getMasterFilial( "VDVENDA" ) };
		return iRetorno;
	}

	private String getLayoutPedido() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		String retorno = null;

		try {

			sSQL.append( "SELECT P.CLASSNOTAPAPEL " );
			sSQL.append( "FROM SGPAPEL P, SGIMPRESSORA I, SGESTACAOIMP EI, SGESTACAO E " );
			sSQL.append( "WHERE P.CODPAPEL=I.CODPAPEL AND P.CODEMP=I.CODEMPPL AND P.CODFILIAL=I.CODFILIALPL " );
			sSQL.append( "AND I.CODIMP=EI.CODIMP AND I.CODEMP=EI.CODEMPIP AND I.CODFILIAL=EI.CODFILIALIP AND EI.TIPOUSOIMP='PD' " );
			sSQL.append( "AND EI.CODEST=E.CODEST AND EI.CODEMP=E.CODEMP AND EI.CODFILIAL=E.CODFILIAL " );
			sSQL.append( "AND E.CODEMP=? AND E.CODFILIAL=? AND E.CODEST=?" );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPAPEL" ) );
			ps.setInt( 3, Aplicativo.iNumEst );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				retorno = rs.getString( 1 ).trim();
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar layout de pedido.\n" + e.getMessage() );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

		return retorno;
	}

	private boolean getProdUsaReceita() {

		boolean retorno = false;

		ResultSet rs = null;
		PreparedStatement ps = null;
		String sSql = null;

		try {

			sSql = "SELECT USARECEITAPROD FROM EQPRODUTO WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=?";
			ps = con.prepareStatement( sSql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setInt( 3, txtCodProd.getVlrInteger().intValue() );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				if ( "S".equals( rs.getString( 1 ) ) ) {
					retorno = true;
				}
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao verificar uso de receita no produto!\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
			sSql = null;
		}

		return retorno;

	}

	private boolean getVendaBloqueada() {

		boolean retorno = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sSql = null;
		String sTipoVenda = null;
		int iCodVenda = 0;

		try {

			iCodVenda = txtCodVenda.getVlrInteger().intValue();

			if ( iCodVenda != 0 ) {

				sTipoVenda = "V";

				sSql = "SELECT BLOQVENDA FROM VDVENDA WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA=?";
				ps = con.prepareStatement( sSql );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
				ps.setInt( 3, iCodVenda );
				ps.setString( 4, sTipoVenda );
				rs = ps.executeQuery();

				if ( rs.next() ) {
					if ( rs.getString( 1 ).equals( "S" ) ) {
						retorno = true;
					}
				}

				rs.close();
				ps.close();

				if ( !con.getAutoCommit() ) {
					con.commit();
				}

			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro bloqueando a venda!\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
			sSql = null;
		}
		return retorno;
	}

	private boolean isComissAtivo() {

		boolean retorno = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sSql = null;

		try {

			sSql = "SELECT ATIVOCOMIS FROM VDVENDEDOR WHERE CODEMP=? AND CODFILIAL=? AND CODVEND=?";

			ps = con.prepareStatement( sSql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDEDOR" ) );
			ps.setInt( 3, txtCodVend.getVlrInteger().intValue() );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				if ( rs.getString( 1 ).equals( "S" ) ) {
					retorno = true;
				}
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro verificando comissionado ativo!\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
			sSql = null;
		}

		return retorno;

	}

	private void mostraTelaDescont() {

		if ( ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) || ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) ) {

			txtVlrDescItVenda.setAtivo( true );
			txtVlrDescItVenda.setVlrString( "" );
			txtPercDescItVenda.setAtivo( false );
			txtPercDescItVenda.setVlrString( "" );
			calcVlrProd();
			calcImpostos( true );
			mostraTelaDesconto();
			calcVlrProd();
			calcImpostos( true );
			txtVlrDescItVenda.requestFocus( true );

		}

	}

	public void setLog( String[] args ) {

		if ( args != null ) {
			txtCodEmpLG.setVlrString( args[ 0 ] );
			txtCodFilialLG.setVlrString( args[ 1 ] );
			txtCodLog.setVlrString( args[ 2 ] );
		}
	}

	public void setParansPreco( BigDecimal bdPreco ) {

		txtPrecoItVenda.setVlrBigDecimal( bdPreco );
	}

	private boolean testaPgto() {

		boolean bRetorno = true;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			String sSQL = "SELECT * FROM FNCHECAPGTOSP(?,?,?)";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, txtCodCli.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, Aplicativo.iCodFilial );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				if ( rs.getString( 1 ).trim().equals( "N" ) ) {
					bRetorno = false;
				}
			}
			else {
				Funcoes.mensagemErro( this, "N�o foi poss�vel checar os pagamentos do cliente!" );
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "N�o foi poss�vel verificar os pagamentos do cliente!\n" + err.getMessage(), true, con, err );
		}

		return bRetorno;

	}

	private boolean testaLucro() {

		return super.testaLucro( new Object[] { 
				txtCodProd.getVlrInteger(), 
				txtCodAlmoxItVenda.getVlrInteger(), 
				txtPrecoItVenda.getVlrBigDecimal(), } );
	}

	public boolean testaCodLote() {

		boolean retorno = true;

		if ( !testaCodLote( txtCodLote.getVlrString().trim(), txtCodProd.getVlrInteger().intValue() ) ) {
			retorno = txtCodLote.mostraDLF2FK();
		}

		return retorno;

	}

	private void imprimir( boolean bVisualizar, int iCodVenda ) {

		PreparedStatement ps = null;
		PreparedStatement psRec = null;
		PreparedStatement psInfoAdic = null;
		ResultSet rs = null;
		ResultSet rsRec = null;
		ResultSet rsInfoAdic = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sSQLRec = new StringBuffer();
		StringBuffer sSQLInfoAdic = new StringBuffer();
		String sDiasPE = null;
		DLRPedido dl = null;
		GregorianCalendar cal = null;
		Date dtHoje = null;
		Vector<?> vDesc = null;
		Vector<?> vObs = null;
		Object layNF = null;
		ImprimeOS imp = new ImprimeOS( "", con, "PD", true );
		boolean bImpOK = false;
		int linPag = imp.verifLinPag( "PD" ) - 1;
		int iDiasPE = 0;

		try {

			dl = new DLRPedido( sOrdNota, false );
			dl.setConexao( con );
			dl.setVisible( true );

			if ( dl.OK == false ) {
				dl.dispose();
				return;
			}

			sSQL.append( "SELECT (SELECT COUNT(IC.CODITVENDA) FROM VDITVENDA IC WHERE IC.CODVENDA=V.CODVENDA AND IC.CODEMP=V.CODEMP AND IC.CODFILIAL=V.CODFILIAL AND IC.TIPOVENDA=V.TIPOVENDA)," );
			sSQL.append( "(SELECT L.CODLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE)," );
			sSQL.append( "(SELECT L.VENCTOLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE)," );
			sSQL.append( "V.CODVENDA,V.CODCLI,C.RAZCLI,C.CNPJCLI,C.CPFCLI,V.DTEMITVENDA,C.ENDCLI,C.NUMCLI,C.COMPLCLI," );
			sSQL.append( "C.BAIRCLI,C.CEPCLI,V.OBSVENDA,V.DTSAIDAVENDA,C.CIDCLI,C.UFCLI,C.FONECLI,C.FONECLI," );
			sSQL.append( "C.FAXCLI,C.INSCCLI,C.RGCLI,I.CODPROD,P.REFPROD,P.CODBARPROD,P.DESCPROD,P.CODUNID,I.PERCISSITVENDA," );
			sSQL.append( "I.QTDITVENDA,I.PRECOITVENDA,I.VLRPRODITVENDA,I.CODNAT,I.PERCICMSITVENDA," );
			sSQL.append( "I.PERCIPIITVENDA,VLRIPIITVENDA,V.VLRBASEICMSVENDA,V.VLRICMSVENDA,V.VLRPRODVENDA," );
			sSQL.append( "V.VLRFRETEVENDA,V.VLRDESCVENDA,V.VLRDESCITVENDA,V.VLRADICVENDA,V.VLRIPIVENDA," );
			sSQL.append( "V.VLRLIQVENDA,V.CODVEND,VEND.NOMEVEND,V.CODPLANOPAG,PG.DESCPLANOPAG," );
			sSQL.append( "(SELECT T.RAZTRAN FROM VDTRANSP T, VDFRETEVD F WHERE T.CODEMP=F.CODEMPTN AND " );
			sSQL.append( "T.CODFILIAL=F.CODFILIALTN AND T.CODTRAN=F.CODTRAN AND F.CODEMP=V.CODEMP AND " );
			sSQL.append( "F.CODFILIAL=V.CODFILIAL AND F.TIPOVENDA=V.TIPOVENDA AND F.CODVENDA=V.CODVENDA)," );
			sSQL.append( "(SELECT F.TIPOFRETEVD FROM VDFRETEVD F WHERE F.CODEMP=V.CODEMP AND " );
			sSQL.append( "F.CODFILIAL=V.CODFILIAL AND F.TIPOVENDA=V.TIPOVENDA AND F.CODVENDA=V.CODVENDA)," );
			sSQL.append( "I.VLRLIQITVENDA,P.DESCAUXPROD,C.DDDCLI,C.EMAILCLI,I.CODITVENDA," );
			sSQL.append( "(SELECT PE.DIASPE FROM VDPRAZOENT PE WHERE PE.CODEMP=P.CODEMPPE AND PE.CODFILIAL=P.CODFILIALPE AND PE.CODPE=P.CODPE)," );
			sSQL.append( "C.SITECLI,I.OBSITVENDA,VEND.EMAILVEND," );
			sSQL.append( "(SELECT FN.DESCFUNC FROM RHFUNCAO FN WHERE FN.CODEMP=VEND.CODEMPFU AND " );
			sSQL.append( "FN.CODFILIAL=VEND.CODFILIALFU AND FN.CODFUNC=VEND.CODFUNC), " );
			sSQL.append( "V.PEDCLIVENDA,C.CONTCLI,P.CODFISC,FC.DESCFISC,V.DOCVENDA,C.OBSCLI " );
			sSQL.append( "FROM VDVENDA V,VDCLIENTE C,VDITVENDA I,EQPRODUTO P,VDVENDEDOR VEND,FNPLANOPAG PG,LFCLFISCAL FC " );
			sSQL.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' AND V.CODVENDA=? AND " );
			sSQL.append( "C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI AND " );
			sSQL.append( "I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL AND I.TIPOVENDA=V.TIPOVENDA AND " );
			sSQL.append( "I.CODVENDA=V.CODVENDA AND P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND " );
			sSQL.append( "P.CODPROD=I.CODPROD AND VEND.CODEMP=V.CODEMPVD AND VEND.CODFILIAL=V.CODFILIALVD AND " );
			sSQL.append( "P.CODFISC=FC.CODFISC AND P.CODEMPFC=FC.CODEMP AND P.CODFILIALFC=FC.CODFILIAL AND " );
			sSQL.append( "VEND.CODVEND=V.CODVEND AND PG.CODEMP=V.CODEMPPG AND PG.CODFILIAL=V.CODFILIALPG AND " );
			sSQL.append( "PG.CODPLANOPAG=V.CODPLANOPAG " );
			sSQL.append( "ORDER BY P." + dl.getValor() + ",P.DESCPROD" );

			sSQLRec.append( "SELECT I.DTVENCITREC,I.VLRPARCITREC,I.NPARCITREC FROM FNRECEBER R, FNITRECEBER I WHERE R.CODVENDA=" );
			sSQLRec.append( iCodVenda );
			sSQLRec.append( " AND I.CODREC=R.CODREC ORDER BY I.DTVENCITREC" );

			sSQLInfoAdic.append( "SELECT CODAUXV,CPFCLIAUXV,NOMECLIAUXV,CIDCLIAUXV,UFCLIAUXV " );
			sSQLInfoAdic.append( "FROM VDAUXVENDA WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=?" );

			dl.dispose();

			if ( bPrefs[ POS_PREFS.USALAYOUTPED.ordinal() ] ) {

				try {
					layNF = Class.forName( "org.freedom.layout.pd." + getLayoutPedido() ).newInstance();
				} catch ( Exception err ) {
					err.printStackTrace();
					Funcoes.mensagemInforma( this, "N�o foi poss�vel carregar o layout do Pedido!\n" + err.getMessage() );
				}

				if ( layNF != null ) {

					if ( layNF instanceof Leiaute ) {

						ps = con.prepareStatement( sSQL.toString() );
						ps.setInt( 1, Aplicativo.iCodEmp );
						ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
						ps.setInt( 3, iCodVenda );
						rs = ps.executeQuery();

						psRec = con.prepareStatement( sSQLRec.toString() );
						rsRec = psRec.executeQuery();

						psInfoAdic = con.prepareStatement( sSQLInfoAdic.toString() );
						psInfoAdic.setInt( 1, Aplicativo.iCodEmp );
						psInfoAdic.setInt( 2, Aplicativo.iCodFilial );
						psInfoAdic.setInt( 3, iCodVenda );
						rsInfoAdic = psInfoAdic.executeQuery();

						bImpOK = ( (Leiaute) layNF ).imprimir( rs, rsRec, rsInfoAdic, imp );

						if ( bImpOK ) {
							if ( bVisualizar ) {
								imp.preview( this );
							}
							else {
								imp.print();
							}
						}

						imp.fechaPreview();

					}

				}

			}
			else {

				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
				ps.setInt( 3, iCodVenda );
				rs = ps.executeQuery();

				psRec = con.prepareStatement( sSQLRec.toString() );
				rsRec = psRec.executeQuery();

				psInfoAdic = con.prepareStatement( sSQLInfoAdic.toString() );
				psInfoAdic.setInt( 1, Aplicativo.iCodEmp );
				psInfoAdic.setInt( 2, Aplicativo.iCodFilial );
				psInfoAdic.setInt( 3, iCodVenda );
				rsInfoAdic = psInfoAdic.executeQuery();

				imp.limpaPags();
				imp.montaCab();
				imp.setTitulo( "Relat�rio de Pedidos" );

				while ( rs.next() ) {

					vDesc = new Vector<Object>();
					if ( bPrefs[ POS_PREFS.DESCCOMPPED.ordinal() ] ) {
						vDesc = Funcoes.quebraLinha( Funcoes.stringToVector( rs.getString( "ObsItVenda" ) == null ? rs.getString( "DescProd" ).trim() : rs.getString( "ObsItVenda" ).trim() ), 40 );
					}
					else {
						vDesc = Funcoes.quebraLinha( Funcoes.stringToVector( rs.getString( "DescProd" ).trim() ), 50 );
					}

					for ( int i = 0; i < vDesc.size(); i++ ) {

						if ( imp.pRow() == 0 ) {

							imp.impCab( 136, false );
							imp.say( 0, imp.comprimido() );
							imp.say( 1, "CLIENTE" );
							imp.say( 70, "PEDIDO N.: " + rs.getString( "CodVenda" ).trim() );
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 1, ( rs.getString( "RazCli" ) != null ? rs.getString( "RazCli" ).trim() : "" ) + " - " + rs.getString( "CodCli" ).trim() );// nome cliente
							imp.say( 70, "PEDIDO CLIENTE: " + ( rs.getString( "PEDCLIVENDA" ) == null ? "" : rs.getString( "PEDCLIVENDA" ) ) );
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 1, rs.getString( "CpfCli" ) != null ? "CPF    : " + Funcoes.setMascara( rs.getString( "CpfCli" ), "###.###.###-##" ) : "CNPJ   : " + Funcoes.setMascara( rs.getString( "CnpjCli" ), "##.###.###/####-##" ) );// CNJP cliente
							imp.say( 70, "CONTATO: " + ( rs.getString( "ContCli" ) != null ? rs.getString( "ContCli" ).trim() : "" ) );
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 1, ( rs.getString( "RgCli" ) != null ? "R.G.   : " + rs.getString( "RgCli" ) : "I.E.   : " + ( rs.getString( "InscCli" ) != null ? rs.getString( "InscCli" ) : "" ) ) );// IE cliente
							imp.say( 70, ( rs.getString( "EndCli" ) != null ? rs.getString( "EndCli" ).trim() + " N�:" + ( rs.getString( "NumCli" ) != null ? rs.getString( "NumCli" ) : "" ) : "" ) );// rua e n�mero do cliente
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 1, "SITE   : " + ( rs.getString( "SiteCli" ) != null ? rs.getString( "SiteCli" ).trim() : "" ) );
							imp.say( 70, ( rs.getString( "BairCli" ) != null ? rs.getString( "BairCli" ).trim() : "" ) + ( rs.getString( "CidCli" ) != null ? " - " + rs.getString( "CidCli" ).trim() : "" ) + ( rs.getString( "UFCli" ) != null ? " - " + rs.getString( "UFCli" ).trim() : "" )
									+ ( rs.getString( "CEPCli" ) != null ? " - " + rs.getString( "CEPCli" ).trim() : "" ) );// complemento do endere�o do cliente
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 1, "E-MAIl : " + ( rs.getString( "EmailCli" ) != null ? rs.getString( "EmailCli" ).trim() : "" ) );
							imp.say( 70, "TEL: " + ( rs.getString( "DDDCli" ) != null ? Funcoes.setMascara( rs.getString( "DDDCli" ), "(####)" ) : "" ) + ( rs.getString( "FoneCli" ) != null ? Funcoes.setMascara( rs.getString( "FoneCli" ).trim(), "####-####" ) : "" ) + " - FAX:"
									+ ( rs.getString( "FaxCli" ) != null ? Funcoes.setMascara( rs.getString( "FaxCli" ), "####-####" ) : "" ) );
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 0, Funcoes.replicate( "-", 135 ) );
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 55, "DADO(S) DO(S) PRODUTO(S)" );
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 0, Funcoes.replicate( "-", 135 ) );
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 1, "ITEM|  C�DIGO  |                 DESCRI��O               |     LOTE     |UN|   QUANT.   |    V.UNIT.  |    V.TOTAL    |  IPI%  |  ICMS% " );

						}

						imp.pulaLinha( 1, imp.comprimido() );

						if ( i == 0 ) {
							imp.say( 1, Funcoes.copy( rs.getString( "CodItVenda" ).trim(), 4 ) );
							imp.say( 6, Funcoes.copy( rs.getString( "RefProd" ).trim(), 10 ) );
						}

						imp.say( 17, "" + vDesc.elementAt( i ).toString() );

						if ( i == 0 ) {
							imp.say( 59, ( rs.getString( 2 ) != null ? rs.getString( 2 ).trim() : "" ) );
							imp.say( 74, rs.getString( "CodUnid" ).trim() );
							imp.say( 79, rs.getString( "QtdItVenda" ) );
							imp.say( 87, Funcoes.strDecimalToStrCurrency( 13, 2, "" + ( new BigDecimal( rs.getString( "VlrLiqItVenda" ) ) ).divide( new BigDecimal( rs.getDouble( "QtdItVenda" ) ), 2, BigDecimal.ROUND_HALF_UP ) ) );
							imp.say( 106, rs.getString( "VlrLiqItVenda" ) );
							imp.say( 122, rs.getString( "PercIPIItVenda" ) );
							imp.say( 130, rs.getString( "PercICMSItVenda" ) );
						}

					}

					if ( iDiasPE < rs.getInt( 57 ) ) {
						iDiasPE = rs.getInt( 57 );
					}

				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, Funcoes.replicate( "-", 135 ) );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 4, "TOTAL IPI: " + rs.getString( "VlrIPIVenda" ) );
				imp.say( 44, "|    TOTAL ICMS: " + rs.getString( "VlrICMSVenda" ) );
				imp.say( 84, "|    TOTAL PRODUTOS: " + rs.getString( "VlrLiqVenda" ) );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, Funcoes.replicate( "-", 135 ) );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 55, "INFORMA��ES COMPLEMENTARES" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, Funcoes.replicate( "-", 135 ) );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "PAGAMENTO.........:    " + rs.getString( "CODPLANOPAG" ) + " - " + rs.getString( "DESCPLANOPAG" ) );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "FRETE.............:    " + ( rs.getString( 51 ) != null ? ( rs.getString( 51 ).equals( "C" ) ? "POR CONTA DA EMPRESA " : "POR CONTA DO CLIENTE " ) : "" ) );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "TRANSPORTADORA....:    " + ( rs.getString( 50 ) != null ? rs.getString( 50 ) : "" ) );
				imp.pulaLinha( 1, imp.comprimido() );

				if ( bPrefs[ POS_PREFS.DIASPEDT.ordinal() ] ) {

					dtHoje = new Date();
					cal = new GregorianCalendar();
					cal.setTime( dtHoje );

					if ( iDiasPE > 0 ) {
						cal.add( GregorianCalendar.DAY_OF_YEAR, iDiasPE );
						sDiasPE = Funcoes.dateToStrDate( cal.getTime() );
					}
					else {
						sDiasPE = "";
					}

					imp.say( 0, "DATA DE ENTREGA...:    " + sDiasPE );

				}
				else {
					sDiasPE = ( iDiasPE > 0 ? iDiasPE + " dias" : "" );
					imp.say( 0, "PRAZO DE ENTREGA..:    " + sDiasPE );
				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, Funcoes.replicate( "-", 135 ) );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 62, "OBSERVAC�O" );
				imp.pulaLinha( 1, imp.comprimido() );

				vObs = Funcoes.quebraLinha( Funcoes.stringToVector( rs.getString( "ObsVenda" ) ), 115 );

				for ( int i = 0; i < vObs.size(); i++ ) {

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 20, vObs.elementAt( i ).toString() );

					if ( imp.pRow() >= linPag ) {
						imp.incPags();
						imp.eject();
					}

				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, Funcoes.replicate( "-", 135 ) );
				imp.pulaLinha( 2, imp.comprimido() );
				imp.say( 5, Funcoes.replicate( "-", 40 ) );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 5, ( rs.getString( "NomeVend" ) != null ? rs.getString( "NomeVend" ) : "" ) );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 5, ( rs.getString( 61 ) != null ? rs.getString( 61 ) : "" ) );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 5, ( rs.getString( "EmailVend" ) != null ? rs.getString( "EmailVend" ) : "" ) );

				imp.eject();
				imp.fechaGravacao();

				if ( bVisualizar ) {
					imp.preview( this );
				}
				else {
					imp.print();
				}

			}

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao consultar a tabela de Venda!" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao montar impress�o!" );
			err.printStackTrace();
		} finally {
			ps = null;
			psRec = null;
			psInfoAdic = null;
			rs = null;
			rsRec = null;
			rsInfoAdic = null;
			sSQL = null;
			sSQLRec = null;
			sSQLInfoAdic = null;
			sDiasPE = null;
			dl = null;
			cal = null;
			dtHoje = null;
			vDesc = null;
			vObs = null;
			layNF = null;
			System.gc();
		}
	}

	private void emitNota( String sTipo ) {

		PreparedStatement ps = null;
		PreparedStatement psRec = null;
		PreparedStatement psInfoAdic = null;
		ResultSet rs = null;
		ResultSet rsRec = null;
		ResultSet rsInfoAdic = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sSQLRec = new StringBuffer();
		StringBuffer sSQLInfoAdic = new StringBuffer();
		Object layNF = null;
		NFSaida nf = null;
		Vector<Object> parans = null;
		ImprimeOS imp = null;
		DLRPedido dl = null;
		boolean bImpOK = false;
		int iCodVenda = txtCodVenda.getVlrInteger().intValue();

		try {

			imp = new ImprimeOS( "", con, sTipo, true );
			imp.verifLinPag( sTipo );
			imp.setTitulo( "Nota Fiscal" );

			dl = new DLRPedido( sOrdNota, false );
			dl.setVisible( true );

			if ( dl.OK == false ) {
				dl.dispose();
				return;
			}

			try {
				layNF = Class.forName( "org.freedom.layout.nf." + imp.getClassNota() ).newInstance();
			} catch ( Exception err ) {
				Funcoes.mensagemInforma( this, "N�o foi poss�vel carregar o layout de Nota Fiscal!\n" + err.getMessage() );
			}

			if ( layNF != null ) {

				if ( layNF instanceof Layout ) {

					parans = new Vector<Object>();
					parans.addElement( new Integer( Aplicativo.iCodEmp ) );
					parans.addElement( new Integer( ListaCampos.getMasterFilial( "VDVENDA" ) ) );
					parans.addElement( new Integer( iCodVenda ) );
					nf = new NFSaida( casasDec );
					nf.carregaTabelas( con, parans );
					bImpOK = ( (Layout) layNF ).imprimir( nf, imp );

				}
				else if ( layNF instanceof Leiaute ) {

					sSQL.append( "SELECT (SELECT COUNT(IC.CODITVENDA) FROM VDITVENDA IC WHERE IC.CODVENDA=V.CODVENDA AND IC.CODEMP=V.CODEMP AND IC.CODFILIAL=V.CODFILIAL AND IC.TIPOVENDA=V.TIPOVENDA)," );
					sSQL.append( "(SELECT L.CODLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE)," );
					sSQL.append( "(SELECT L.VENCTOLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE)," );
					sSQL.append( "(SELECT M.MENS FROM LFMENSAGEM M WHERE M.CODMENS=I.CODMENS" );
					sSQL.append( " AND M.CODFILIAL=I.CODFILIALME AND M.CODEMP=I.CODEMPME)," );
					sSQL.append( "(SELECT M.MENS FROM LFMENSAGEM M WHERE M.CODMENS=CL.CODMENS" );
					sSQL.append( " AND M.CODFILIAL=CL.CODFILIALME AND M.CODEMP=CL.CODEMPME)," );
					sSQL.append( "(SELECT S.DESCSETOR FROM VDSETOR S WHERE S.CODSETOR=C.CODSETOR" );
					sSQL.append( " AND S.CODFILIAL=C.CODFILIALSR AND S.CODEMP=C.CODEMPSR)," );
					sSQL.append( "(SELECT B.NOMEBANCO FROM FNBANCO B WHERE B.CODEMP=V.CODEMPBO" );
					sSQL.append( " AND B.CODFILIAL=V.CODFILIALBO AND B.CODBANCO=V.CODBANCO)," );
					sSQL.append( "(SELECT P.SIGLAPAIS FROM SGPAIS P WHERE P.CODPAIS=C.CODPAIS)," );
					sSQL.append( "V.DOCVENDA,V.CODVENDA,V.CODCLI,C.RAZCLI,C.CNPJCLI,C.CPFCLI,V.DTEMITVENDA,C.ENDCLI," );
					sSQL.append( "C.BAIRCLI,C.CEPCLI,V.OBSVENDA,V.DTSAIDAVENDA,C.CIDCLI,C.UFCLI,C.FONECLI,C.FONECLI,C.NUMCLI,C.COMPLCLI," );
					sSQL.append( "C.FAXCLI,C.INSCCLI,C.RGCLI,I.CODPROD,P.REFPROD,P.CODBARPROD,P.DESCPROD, P.CODUNID,N.CODNAT," );
					sSQL.append( "I.VLRLIQITVENDA,N.DESCNAT,I.QTDITVENDA,I.PRECOITVENDA,I.VLRPRODITVENDA,I.CODNAT,I.PERCICMSITVENDA," );
					sSQL.append( "I.PERCISSITVENDA,PERCIPIITVENDA,VLRIPIITVENDA,V.VLRBASEICMSVENDA,V.VLRICMSVENDA,V.VLRPRODVENDA," );
					sSQL.append( "V.VLRISSVENDA,V.VLRFRETEVENDA,V.VLRDESCVENDA,V.VLRDESCITVENDA,V.VLRADICVENDA,V.VLRIPIVENDA," );
					sSQL.append( "V.VLRBASEISSVENDA,V.VLRLIQVENDA,V.CODVEND,VEND.NOMEVEND,V.CODPLANOPAG,PG.DESCPLANOPAG,F.CODTRAN," );
					sSQL.append( "T.RAZTRAN,F.TIPOFRETEVD,F.PLACAFRETEVD,F.UFFRETEVD,T.TIPOTRAN,T.CNPJTRAN,T.ENDTRAN,T.NUMTRAN,T.CIDTRAN," );
					sSQL.append( "T.UFTRAN,T.INSCTRAN,F.QTDFRETEVD,F.ESPFRETEVD,F.MARCAFRETEVD,F.PESOBRUTVD," );
					sSQL.append( "F.PESOLIQVD, I.ORIGFISC, I.CODTRATTRIB, FL.CNPJFILIAl,FL.INSCFILIAL,FL.ENDFILIAL," );
					sSQL.append( "FL.NUMFILIAL,FL.COMPLFILIAL,FL.BAIRFILIAL,FL.CEPFILIAL,FL.CIDFILIAL,FL.UFFILIAL,FL.FONEFILIAL," );
					sSQL.append( "FL.FAXFILIAL,C.ENDCOB, C.NUMCOB, C.COMPLCOB,C.CEPCOB, C.CIDCOB, P.TIPOPROD, C.INCRACLI, V.CODBANCO," );
					sSQL.append( "P.CODFISC, C.ENDENT, C.NUMENT, C.COMPLENT,C.CIDENT,C.UFENT,C.BAIRENT,C.NOMECLI,I.OBSITVENDA," );
					sSQL.append( "V.VLRPISVENDA,V.VLRCOFINSVENDA,V.VLRIRVENDA,V.VLRCSOCIALVENDA,V.CODCLCOMIS,V.PERCCOMISVENDA," );
					sSQL.append( "V.PERCMCOMISVENDA, N.IMPDTSAIDANAT,P.DESCAUXPROD, C.DDDCLI " );
					sSQL.append( " FROM VDVENDA V, VDCLIENTE C, VDITVENDA I, EQPRODUTO P, VDVENDEDOR VEND, FNPLANOPAG PG," );
					sSQL.append( " VDFRETEVD F, VDTRANSP T, LFNATOPER N, SGFILIAL FL, LFCLFISCAL CL WHERE V.TIPOVENDA='V' AND V.CODVENDA=" );
					sSQL.append( iCodVenda );
					sSQL.append( " AND V.CODEMP=? AND V.CODFILIAL=? AND FL.CODEMP=V.CODEMP AND FL.CODFILIAL=V.CODFILIAL" );
					sSQL.append( " AND C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL " );
					sSQL.append( " AND I.CODVENDA=V.CODVENDA AND I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL AND I.TIPOVENDA=V.TIPOVENDA " );
					sSQL.append( " AND P.CODPROD=I.CODPROD AND VEND.CODVEND=V.CODVEND AND PG.CODPLANOPAG=V.CODPLANOPAG AND F.CODVENDA=V.CODVENDA" );
					sSQL.append( " AND T.CODTRAN=F.CODTRAN AND N.CODNAT=I.CODNAT AND CL.CODFISC = P.CODFISC AND CL.CODFILIAL=P.CODFILIAL" );
					sSQL.append( " AND CL.CODEMP = P.CODEMP " );
					sSQL.append( "ORDER BY P." + dl.getValor() + ",P.DESCPROD" );

					sSQLRec.append( "SELECT I.DTVENCITREC,I.VLRPARCITREC,I.NPARCITREC FROM FNRECEBER R, FNITRECEBER I WHERE R.CODVENDA=" );
					sSQLRec.append( iCodVenda );
					sSQLRec.append( " AND I.CODREC=R.CODREC ORDER BY I.DTVENCITREC" );

					sSQLInfoAdic.append( "SELECT CODAUXV,CPFCLIAUXV,NOMECLIAUXV,CIDCLIAUXV,UFCLIAUXV " );
					sSQLInfoAdic.append( "FROM VDAUXVENDA WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=?" );

					ps = con.prepareStatement( sSQL.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
					rs = ps.executeQuery();

					psRec = con.prepareStatement( sSQLRec.toString() );
					rsRec = psRec.executeQuery();

					psInfoAdic = con.prepareStatement( sSQLInfoAdic.toString() );
					psInfoAdic.setInt( 1, Aplicativo.iCodEmp );
					psInfoAdic.setInt( 2, Aplicativo.iCodFilial );
					psInfoAdic.setInt( 3, txtCodVenda.getVlrInteger().intValue() );
					rsInfoAdic = psInfoAdic.executeQuery();

					bImpOK = ( (Leiaute) layNF ).imprimir( rs, rsRec, rsInfoAdic, imp );

					dl.dispose();

					if ( !con.getAutoCommit() ) {
						con.commit();
					}

				}

			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao consultar tabela de Venda!" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			psRec = null;
			psInfoAdic = null;
			rs = null;
			rsRec = null;
			rsInfoAdic = null;
			sSQL = null;
			sSQLRec = null;
			sSQLInfoAdic = null;
			layNF = null;
			nf = null;
			parans = null;
			dl = null;
		}

		if ( bImpOK ) {
			imp.preview( this );
		}

		imp.fechaPreview();
	}

	private synchronized void focusIni() {

		tpnCab.requestFocus( true );
	}

	private void focusCodprod() {

		if ( bPrefs[ POS_PREFS.USAREFPROD.ordinal() ] ) {
			txtRefProd.requestFocus();
		}
		else {
			txtCodProd.requestFocus();
		}
	}

	public void exec( int iCodVenda ) {

		txtCodVenda.setVlrString( String.valueOf( iCodVenda ) );
		lcCampos.carregaDados();
	}

	
	private boolean[] prefs() {

		boolean[] bRetorno = new boolean[ 20 ];
		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sSQL.append( "SELECT USAREFPROD,USAPEDSEQ,USALIQREL,TIPOPRECOCUSTO,ORDNOTA,USAPRECOZERO," );
			sSQL.append( "USACLASCOMIS,TRAVATMNFVD,NATVENDA,IPIVENDA,BLOQVENDA, VENDAMATPRIM, DESCCOMPPED, " );
			sSQL.append( "TAMDESCPROD, OBSCLIVEND, CONTESTOQ, DIASPEDT, RECALCPCVENDA, USALAYOUTPED, ");
			sSQL.append( "ICMSVENDA, MULTICOMIS " );
			sSQL.append( "FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				bRetorno[ POS_PREFS.USAREFPROD.ordinal() ] = "S".equals( rs.getString( "USAREFPROD" ) );
				bRetorno[ POS_PREFS.USAPEDSEQ.ordinal() ] = "S".equals( rs.getString( "USAPEDSEQ" ) );
				if ( rs.getString( "UsaLiqRel" ) == null ) {
					Funcoes.mensagemInforma( this, "Preencha op��o de desconto em prefer�ncias!" );
				}
				else {
					bRetorno[ POS_PREFS.USALIQREL.ordinal() ] = "S".equals( rs.getString( "UsaLiqRel" ) );
					sOrdNota = rs.getString( "OrdNota" );
					bRetorno[ POS_PREFS.TIPOPRECOCUSTO.ordinal() ] = "S".equals( rs.getString( "TipoPrecoCusto" ) );
					bRetorno[ POS_PREFS.USACLASCOMIS.ordinal() ] = "S".equals( rs.getString( "UsaClasComis" ) );
				}
				bRetorno[ POS_PREFS.TRAVATMNFVD.ordinal() ] = "S".equals( rs.getString( "TravaTmNfVd" ) );
				bRetorno[ POS_PREFS.NATVENDA.ordinal() ] = "S".equals( rs.getString( "NatVenda" ) );
				bRetorno[ POS_PREFS.BLOQVENDA.ordinal() ] = "S".equals( rs.getString( "BloqVenda" ) );
				bRetorno[ POS_PREFS.VENDAMATPRIM.ordinal() ] = "S".equals( rs.getString( "VendaMatPrim" ) );
				bRetorno[ POS_PREFS.DESCCOMPPED.ordinal() ] = "S".equals( rs.getString( "DescCompPed" ) );
				bRetorno[ POS_PREFS.TAMDESCPROD.ordinal() ] = "S".equals( rs.getString( "TAMDESCPROD" ) );
				bRetorno[ POS_PREFS.OBSCLIVEND.ordinal() ] = "S".equals( rs.getString( "OBSCLIVEND" ) );
				bRetorno[ POS_PREFS.IPIVENDA.ordinal() ] = "S".equals( rs.getString( "IPIVenda" ) );
				bRetorno[ POS_PREFS.CONTESTOQ.ordinal() ] = "S".equals( rs.getString( "CONTESTOQ" ) );
				bRetorno[ POS_PREFS.DIASPEDT.ordinal() ] = "S".equals( rs.getString( "DIASPEDT" ) );
				bRetorno[ POS_PREFS.RECALCCPVENDA.ordinal() ] = "S".equals( rs.getString( "RECALCPCVENDA" ) );
				bRetorno[ POS_PREFS.USALAYOUTPED.ordinal() ] = "S".equals( rs.getString( "USALAYOUTPED" ) );
				bRetorno[ POS_PREFS.ICMSVENDA.ordinal() ] = "S".equals( rs.getString( "ICMSVENDA" ) );
				bRetorno[ POS_PREFS.USAPRECOZERO.ordinal() ] = "S".equals( rs.getString( "USAPRECOZERO" ) );
				bRetorno[ POS_PREFS.MULTICOMIS.ordinal() ] = "S".equals(  rs.getString( "MULTICOMIS" ) );
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
			rs = null;
			ps = null;
		}
		return bRetorno;
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcProd2 ) {
			lcProd.edit();
		}

		/*
		 * if (lcCampos.getStatus() != ListaCampos.LCS_INSERT) { 
		 * //Cancela os 
		 * // auto-incrementos 
		 * // que sobrep�em o 
		 * // que est� 
		 * // guardado na 
		 * // tabela venda 
		 * if (cevt.getListaCampos() == lcVendedor) { 
		 * lcVendedor.cancLerCampo(2, true); 
		 * //Comiss�o do vendedor; 
		 * } else if (cevt.getListaCampos() == lcCli) { 
		 * lcCli.cancLerCampo(2, true); 
		 * //C�digo de Pagamento 
		 * lcCli.cancLerCampo(3, true); 
		 * //C�digo do Vendador 
		 * } 
		 * } else { if (cevt.getListaCampos() == lcVendedor) {
		 * //Ativa auto-incrementos 
		 * lcVendedor.cancLerCampo(2, false); 
		 * //Comiss�o do vendedor; 
		 * } else if (cevt.getListaCampos() == lcCli) {
		 * lcCli.cancLerCampo(2, false); 
		 * //C�digo do Pagamento 
		 * lcCli.cancLerCampo(3, false); 
		 * //C�digo do Vendedor } 
		 * }
		 * Por que faz a mesma coisa no if e no else? */
		if ( cevt.getListaCampos() == lcVendedor ) {// Ativa auto-incrementos
			lcVendedor.cancLerCampo( 2, false ); // Comiss�o do vendedor;
			if ( !isComissAtivo() ) { // Verifica se o comissionado � ativo.
				Funcoes.mensagemInforma( this, "Comissionado Inativo!" );
			}
		}
		else if ( cevt.getListaCampos() == lcCli ) {
			lcCli.cancLerCampo( 2, false ); // C�digo do Pagamento
			lcCli.cancLerCampo( 3, false ); // C�digo do Vendedor
		}

		if ( lcDet.getStatus() != ListaCampos.LCS_INSERT ) {
			if ( cevt.getListaCampos() == lcProd ) {
				lcProd.cancLerCampo( 5, true ); // C�digo da Classifica��o Fiscal
			}
		}
		else {
			if ( cevt.getListaCampos() == lcProd ) {
				lcProd.cancLerCampo( 5, false ); // C�digo da Classifica��o Fiscal
			}
		}

		if ( cevt.getListaCampos() == lcCampos ) {
			if ( bPrefs[ POS_PREFS.TRAVATMNFVD.ordinal() ] ) {
				txtFiscalTipoMov1.setText( "S" );
				txtFiscalTipoMov2.setText( "N" );
			}
			if ( bPrefs[ POS_PREFS.OBSCLIVEND.ordinal() ] ) {
				iCodCliAnt = txtCodCli.getVlrInteger().intValue();
			}
		}

	}

	public void afterCarrega( CarregaEvent cevt ) {

		try {
			if ( ( cevt.getListaCampos() == lcProd ) || ( cevt.getListaCampos() == lcProd2 ) ) {
				if ( txtCLoteProd.getText().trim().equals( "N" ) ) {
					txtCodLote.setAtivo( false );// Desativa o C�gigo do lote por o
					// produto n�o possuir lote
				}
				else if ( txtCLoteProd.getText().trim().equals( "S" ) ) {
					txtCodLote.setAtivo( true );// Ativa o C�gigo do Lote pois o
					// produto tem lote
					if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
						getLote();
					}
				}
				if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
					calcVlrItem( null, false );
				}
			}
			else if ( ( cevt.getListaCampos() == lcTipoMov ) && (numComissionados>0) && 
				(codregrcomis!=txtCodRegrComis.getVlrInteger().intValue()) ) {
				codregrcomis = txtCodRegrComis.getVlrInteger().intValue();
				ctrlmc.loadRegraComis( codregrcomis );
			}
			else if ( ( cevt.getListaCampos() == lcFisc ) && ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) ) {
				getCFOP();
				getTratTrib();
			}
			else if ( cevt.getListaCampos() == lcNat ) {
				if ( ( cevt.ok  ) & ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) ) {
					getICMS();
				}
			}
			else if ( cevt.getListaCampos() == lcDet ) {
				lcVenda2.carregaDados();// Carrega os Totais
			}
			else if ( cevt.getListaCampos() == lcCampos ) {
				String codvenda = txtCodVenda.getVlrString();
				lcVenda2.carregaDados();// Carrega os Totais
				txtCodVenda.setVlrString( codvenda );
				codvenda = null;
			}
			else if ( cevt.getListaCampos() == lcVenda2 ) {
				txtPercComisVenda.setAtivo( txtVlrComisVenda.floatValue() == 0 );
			}
			else if ( cevt.getListaCampos() == lcCli ) {
				if ( ( bPrefs[ POS_PREFS.OBSCLIVEND.ordinal() ] ) ) {
					if ( iCodCliAnt != txtCodCli.getVlrInteger().intValue() ) {
						iCodCliAnt = txtCodCli.getVlrInteger().intValue();
						mostraObsCli( iCodCliAnt, 
								new Point( this.getX(), this.getY() + tpnCab.getHeight() + pnCab.getHeight() ), 
								new Dimension( spTab.getWidth(), spTab.getHeight() ) );
					}
				}
				if ( bPrefs[ POS_PREFS.RECALCCPVENDA.ordinal() ] ) {
					setReCalcPreco( true );
				}
			}
			else if ( cevt.getListaCampos() == lcPlanoPag ) {
				if ( bPrefs[ POS_PREFS.RECALCCPVENDA.ordinal() ] ) {
					setReCalcPreco( true );
				}
			}

			if ( txtStatusVenda.getVlrString().trim().length() > 0 && txtStatusVenda.getVlrString().substring( 0, 1 ).equals( "C" ) ) {
				lbStatus.setText( "  CANCELADA" );
				lbStatus.setBackground( Color.RED );
				lbStatus.setVisible( true );
			}
			else if ( getVendaBloqueada() ) {
				lbStatus.setText( "  BLOQUEADA" );
				lbStatus.setBackground( Color.BLUE );
				lbStatus.setVisible( true );
			}
			else if ( txtStatusVenda.getVlrString().trim().length() > 0 
					&& ( txtStatusVenda.getVlrString().trim().equals( "V2" ) 
							|| txtStatusVenda.getVlrString().trim().equals( "V3" ) ) ) {
				lbStatus.setText( "  NF EMITIDA" );
				lbStatus.setBackground( new Color( 45, 190, 60 ) );
				lbStatus.setVisible( true );
			}
			else {
				lbStatus.setVisible( false );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void beforePost( PostEvent pevt ) {

		PreparedStatement psTipoMov = null;
		ResultSet rsTipoMov = null;

		if ( pevt.getListaCampos() == lcCampos ) {
			if ( podeReCalcPreco() && lcDet.getStatus() == ListaCampos.LCS_READ_ONLY ) {
				calcVlrItem( "VDVENDA", true );
				lcDet.carregaDados();
				calcImpostos( true );
				lcDet.edit();
				lcDet.post();
			}
			setReCalcPreco( false );
		}
		if ( ( pevt.getListaCampos() == lcCampos ) && ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) ) {
			if ( txtESTipoMov.getVlrString().equals( "E" ) ) {
				if ( Funcoes.mensagemConfirma( this, "Este movimento ir� realizar entradas no estoque.\n" + "Deseja continuar?\n" ) != 0 ) {
					pevt.cancela();
					return;
				}
			}
			if ( !testaPgto() ) {
				if ( Funcoes.mensagemConfirma( this, "Cliente com duplicatas em aberto! Continuar?" ) != 0 ) {
					pevt.cancela();
					return;
				}
			}
			if ( bPrefs[ POS_PREFS.TRAVATMNFVD.ordinal() ] ) {
				try {
					psTipoMov = con.prepareStatement( "SELECT CODTIPOMOV,DESCTIPOMOV FROM EQTIPOMOV WHERE " + "CODEMP=? AND CODFILIAL=? AND CODTIPOMOV=? AND FISCALTIPOMOV='N'" );
					psTipoMov.setInt( 1, Aplicativo.iCodEmp );
					psTipoMov.setInt( 2, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );
					psTipoMov.setInt( 3, txtCodTipoMov.getVlrInteger().intValue() );
					rsTipoMov = psTipoMov.executeQuery();
					if ( rsTipoMov.next() ) {
						if ( rsTipoMov.getInt( "CODTIPOMOV" ) != txtCodTipoMov.getVlrInteger().intValue() ) {
							Funcoes.mensagemInforma( this, "Tipo de movimento n�o permitido na inser��o!" );
							pevt.cancela();
							return;
						}
					}
					else {
						Funcoes.mensagemInforma( this, "Tipo de movimento n�o permitido na inser��o!" );
						pevt.cancela();
						return;
					}
					if ( !con.getAutoCommit() ) {
						con.commit();
					}
					rsTipoMov.close();
					psTipoMov.close();
				} catch ( SQLException err ) {
					Funcoes.mensagemErro( this, "Erro ao pesquisar tipo de movimento!\n" + err.getMessage(), true, con, err );
					pevt.cancela();
				}
			}
			txtStatusVenda.setVlrString( "*" );
		}
		else if ( pevt.getListaCampos() == lcDet ) {
			if ( ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) || ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) ) {
				if ( txtQtdItVenda.getVlrBigDecimal().floatValue() <= 0 ) {
					Funcoes.mensagemInforma( this, "Quantidade invalida!" );
					pevt.cancela();
					return;
				}
				if ( ( !bPrefs[ POS_PREFS.USAPRECOZERO.ordinal() ] ) && ( txtPrecoItVenda.getVlrBigDecimal().floatValue() <= 0 ) ) {
					Funcoes.mensagemInforma( this, "Pre�o inv�lido!" );
					pevt.cancela();
					return;
				}
				if ( txtCLoteProd.getVlrString().equals( "S" ) ) {
					if ( !testaCodLote() ) {
						pevt.cancela();
					}
				}
				if ( txtCodProd.getVlrInteger().intValue() > 0 ) {
					if ( !testaLucro() ) {
						Funcoes.mensagemInforma( this, "N�o � permitido a venda deste produto abaixo do custo!!!" );
						pevt.cancela();
					}
				}
				calcDescIt();
				calcComisIt();
			}
		}
		txtTipoVenda.setVlrString( "V" );
	}

	public void afterPost( PostEvent pevt ) {

		lcVenda2.carregaDados(); // Carrega os Totais
		if ( pevt.getListaCampos() == lcCampos ) {
			if ( bPrefs[ POS_PREFS.TRAVATMNFVD.ordinal() ] ) {
				txtFiscalTipoMov1.setText( "S" );
				txtFiscalTipoMov2.setText( "N" );
			}
		}
	}

	public void beforeInsert( InsertEvent ievt ) {

		lbStatus.setForeground( Color.WHITE );
		lbStatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbStatus.setOpaque( true );
		lbStatus.setVisible( false );
	}

	public void afterInsert( InsertEvent ievt ) {

		if ( ievt.getListaCampos() == lcCampos ) {
			if ( bPrefs[ POS_PREFS.USAPEDSEQ.ordinal() ] ) {
				txtCodVenda.setVlrInteger( testaCodPK( "VDVENDA" ) );
			}
			if ( bPrefs[ POS_PREFS.TRAVATMNFVD.ordinal() ] ) {
				txtFiscalTipoMov1.setText( "N" );
				txtFiscalTipoMov2.setText( "N" );
			}
			txtDtSaidaVenda.setVlrDate( new Date() );
			txtDtEmitVenda.setVlrDate( new Date() );
		}
		else if ( ievt.getListaCampos() == lcDet ) {
			focusCodprod();
		}
	}

	public void beforeDelete( DeleteEvent devt ) {

	}

	public void afterDelete( DeleteEvent devt ) {

		if ( devt.getListaCampos() == lcDet ) {
			lcVenda2.carregaDados();
		}
	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_CONTROL ) {
			bCtrl = true;
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_O ) {
			if ( bCtrl ) {
				btObs.doClick();
			}
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
			if ( kevt.getSource() == txtPercComisVenda || ( kevt.getSource() == txtClasComis && !txtPercComisVenda.getAtivo() ) ) {// Como estes s�o os ultimos campos
				// do painel de venda executa-se o
				// post no venda e pula para o campo
				// adequado no item.
				if ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) {
					focusIni();
					focusCodprod();
					lcCampos.post();
					lcDet.edit();
				}
				else if ( lcCampos.getStatus() == ListaCampos.LCS_EDIT ) {
					lcCampos.post();
					focusCodprod();
				}
			}
			else if ( kevt.getSource() == txtPedCliVenda ) {// Como este � o
				// ultimo campo da
				// aba de venda
				// ent�o abre a tab
				// comissao.
				tpnCab.setSelectedIndex( 1 );
				tpnCab.doLayout();
				txtCodVend.requestFocus();
			}
			else if ( kevt.getSource() == txtUltCamp ) {// Como este � o ultimo
				// campo do painel de
				// itvenda executa-se o
				// post no itvenda e
				// pula para o campo
				// adequado no item.
				if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
					lcDet.post();
					lcDet.limpaCampos( true );
					lcDet.setState( ListaCampos.LCS_NONE );
					lcDet.edit();
					focusCodprod();
				}
				else if ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
					lcDet.post();
					txtCodItVenda.requestFocus();
				}
			}
			else if ( kevt.getSource() == txtCodNat ) {// Talvez este possa ser
				// o ultimo campo por
				// isso o teste.
				if ( !txtBaseICMSItVenda.getAtivo() ) {
					if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
						lcDet.post();
						lcDet.limpaCampos( true );
						lcDet.setState( ListaCampos.LCS_NONE );
						lcDet.edit();
						focusCodprod();
					}
					else if ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
						lcDet.post();
						txtCodItVenda.requestFocus();
					}
				}
			}
			else if ( kevt.getSource() == txtRefProd || kevt.getSource() == txtCodProd ) {
				if ( kevt.getSource() == txtRefProd ) {
					lcDet.edit();
				}
				if ( getProdUsaReceita() ) {
					FPassword pass = new FPassword( this, FPassword.APROV_RECEITA_PROD, "Receita", con );
					pass.setVisible( true );
					if ( !pass.OK ) {
						lcDet.cancel( true );
					}
					pass.dispose();
				}
			}
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_F3 && kevt.getSource() == txtVlrDescItVenda ) {
			mostraTelaDescont();
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_F4 ) {
			btFechaVenda.doClick();
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_F5 ) {
			btConsPgto.doClick();
		}

		super.keyPressed( kevt );
	}

	public void keyTyped( KeyEvent kevt ) {

		super.keyTyped( kevt );
	}

	public void keyReleased( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_CONTROL ) {
			bCtrl = false;
		}

		super.keyReleased( kevt );
	}
	
	private boolean consultaCredito() {

		try {
			// Libera��o de cr�dito:
			String sSQL = "EXECUTE PROCEDURE FNLIBCREDSP(?,?,?,?,?,?,?,?);";
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setString( 3, "V" );
			ps.setInt( 4, txtCodVenda.getVlrInteger() );
			ps.setInt( 5, txtCodCli.getVlrInteger() );
			ps.setInt( 6, Aplicativo.iCodEmp );
			ps.setInt( 7, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setObject( 8, txtVlrLiqVenda.getVlrBigDecimal() );
			ps.execute();
			ps.close();
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} 
		catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemInforma( this, "Problema ao executar fechamento da venda!\nA Venda ultrapassa o limite de cr�dito pr�-estabelecido!/n" );
			Logger.gravaLogTxt( "", Aplicativo.strUsuario, Logger.LGEB_BD, "Erro ao executar fechamento (limite de cr�dito)." );
			return false;
		}
		
		return true;
		
		
	}
	
	private void fechaVenda() {
		try {
					
			if(consultaCredito()){
						
				List<Integer> lsParcRecibo = null;
				String[] sValores = null;
				
				DLFechaVenda dl = new DLFechaVenda( con, 
						txtCodVenda.getVlrInteger(), 
						this, 
						chbImpPedTipoMov.getVlrString(), 
						chbImpNfTipoMov.getVlrString(), 
						chbImpBolTipoMov.getVlrString(), 
						chbImpRecTipoMov.getVlrString(), 
						chbReImpNfTipoMov.getVlrString() );
				// dl.getDadosCli();
				dl.setVisible( true );
	
				if ( dl.OK ) {
					sValores = dl.getValores();
					if ( "S".equals( sValores[ 6 ] ) ) {
						lsParcRecibo = dl.getParcRecibo();
					}
					dl.dispose();
				}
				else {
					dl.dispose();
				}
				
				lcCampos.carregaDados();
				
				if ( sValores != null ) {
	
					// Ordem dos par�metros decrescente por que uma tela abre na
					// frente da outra.
	
					if ( sValores[ 5 ].equals( "S" ) && !sValores[ 7 ].equals( "" ) ) {
						FRBoleto fBol = new FRBoleto( this );
						fBol.setConexao( con );
						fBol.txtCodModBol.setVlrInteger( new Integer( sValores[ 7 ] ) );
						fBol.txtCodVenda.setVlrInteger( txtCodVenda.getVlrInteger() );
						fBol.imprimir( true );
					}
					else if ( ( "S".equals( sValores[ 6 ] ) ) && ( lsParcRecibo != null ) && ( lsParcRecibo.size() > 0 ) ) { // Logica para impress�o do recibo.
						FRBoleto fBol = new FRBoleto( this );
						fBol.setConexao( con );
						if ( "".equals( sValores[ 7 ] ) ) {
							Funcoes.mensagemInforma( this, "Modelo de boleto/recibo n�o foi selecionado!" );
						}
						else {
							fBol.txtCodModBol.setVlrInteger( new Integer( sValores[ 7 ] ) );
							fBol.txtCodVenda.setVlrInteger( txtCodVenda.getVlrInteger() );
							fBol.setParcelas( lsParcRecibo );
							fBol.imprimir( true );
						}
					}
	
					if ( ( sValores[ 4 ].equals( "S" ) ) || ( sValores[ 8 ].equals( "S" ) ) ) {
						if ( txtTipoMov.getVlrString().equals( "VD" ) || txtTipoMov.getVlrString().equals( "VT" ) || 
								txtTipoMov.getVlrString().equals( "TR" ) || 
								txtTipoMov.getVlrString().equals( "CS" ) || 
								txtTipoMov.getVlrString().equals( "CE" ) || 
								txtTipoMov.getVlrString().equals( "PE" ) || 
								txtTipoMov.getVlrString().equals( "DV" ) ||
								txtTipoMov.getVlrString().equals( "BN" ) ) {
							emitNota( "NF" );
						}
						else if ( txtTipoMov.getVlrString().equals( "SE" ) ) {
							emitNota( "NS" );
						}
						else {
							Funcoes.mensagemErro( this, "N�o existe nota para o tipo de movimento: '" + txtTipoMov.getVlrString() + "'" );
							return;
						}
						if ( sValores[ 8 ].equals( "N" ) ) {
							txtStatusVenda.setVlrString( "V4" );
						}
					}
					else if ( sValores[ 3 ].equals( "S" ) ) {
						imprimir( true, txtCodVenda.getVlrInteger().intValue() );
					}
					if ( sValores[ 8 ].equals( "N" ) ) {
						lcCampos.edit();
						lcCampos.post();
					}
					if ( ( sValores[ 4 ].equals( "S" ) ) && ( bPrefs[ POS_PREFS.BLOQVENDA.ordinal() ] ) ) {
						bloqvenda();
					}
				}
	
				tpnCab.setSelectedIndex( 0 );
				txtCodVenda.requestFocus();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btFechaVenda ) { //xxx 			
			if(lcCampos.carregaDados()) {
				fechaVenda();
			}
		}
		else if ( evt.getSource() == btConsPgto ) {
			DLConsultaPgto dl = new DLConsultaPgto( this, con, txtCodCli.getVlrInteger().intValue() );
			dl.setVisible( true );
			dl.dispose();
		}
		else if ( evt.getSource() == btPrevimp ) {
			imprimir( true, txtCodVenda.getVlrInteger().intValue() );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false, txtCodVenda.getVlrInteger().intValue() );
		}
		else if ( evt.getSource() == btObs ) {
			mostraObs( "VDVENDA", txtCodVenda.getVlrInteger().intValue() );
		}
		else if ( evt.getSource() == btAdicOrc ) {
			abreAdicOrc();
		}
		else if ( evt.getSource() == btAltComis ) {
			altComisVend();
		}

		super.actionPerformed( evt );
	}

	public void focusGained( FocusEvent fevt ) {

	}

	public void focusLost( FocusEvent fevt ) {

		if ( fevt.getSource() == txtPercDescItVenda ) {
			if ( txtPercDescItVenda.getText().trim().length() < 1 ) {
				txtVlrDescItVenda.setAtivo( true );
			}
			else {
				calcDescIt();
				calcVlrProd();
				calcImpostos( true );
				txtVlrDescItVenda.setAtivo( false );
			}
		}
		else if ( fevt.getSource() == txtPercComItVenda ) {
			if ( txtPercComItVenda.getText().trim().length() < 1 ) {
				txtVlrComisItVenda.setAtivo( true );
			}
			else {
				calcComisIt();
				calcVlrProd();
				calcImpostos( true );
				txtVlrComisItVenda.setAtivo( false );
			}
		}
		else if ( fevt.getSource() == txtVlrDescItVenda ) {
			if ( txtVlrDescItVenda.getText().trim().length() < 1 ) {
				txtPercDescItVenda.setAtivo( true );
			}
			else if ( txtVlrDescItVenda.getAtivo() ) {
				txtPercDescItVenda.setAtivo( false );
			}
			calcDescIt();
			calcVlrProd();
			calcImpostos( true );
		}
		else if ( fevt.getSource() == txtVlrComisItVenda ) {
			if ( txtVlrComisItVenda.getText().trim().length() < 1 ) {
				txtPercComItVenda.setAtivo( true );
			}
			else if ( txtVlrComisItVenda.getAtivo() ) {
				txtPercComItVenda.setAtivo( false );
			}
			calcComisIt();
			calcVlrProd();
			calcImpostos( true );
		}
		else if ( ( fevt.getSource() == txtQtdItVenda ) | ( fevt.getSource() == txtPrecoItVenda ) | ( fevt.getSource() == txtCodNat ) ) {
			calcVlrProd();
			calcImpostos( true );
		}
		else if ( ( fevt.getSource() == txtPercICMSItVenda ) | ( fevt.getSource() == txtAliqIPIItVenda ) ) {
			calcImpostos( false );
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		montaTela();
		lcTratTrib.setConexao( cn );
		lcTipoMov.setConexao( cn );
		lcCli.setConexao( cn );
		lcVendedor.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcSerie.setConexao( cn );
		lcProd.setConexao( cn );
		lcProd2.setConexao( cn );
		lcNat.setConexao( cn );
		lcAlmox.setConexao( cn );
		lcLote.setConexao( cn );
		lcFisc.setConexao( cn );
		lcVenda2.setConexao( cn );
		lcClComis.setConexao( cn );
	}
}
