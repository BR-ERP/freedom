/**
 * @version 25/03/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)FOP.java <BR>
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
 * Tela de cadastro de ordens de produ��o.
 * 
 */

package org.freedom.modulos.pcp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.freedom.acao.CancelEvent;
import org.freedom.acao.CancelListener;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.layout.componentes.LeiauteGR;
import org.freedom.modulos.gms.FRma;
import org.freedom.modulos.std.DLBuscaProd;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;
import org.freedom.telas.FFDialogo;
import org.freedom.telas.FPrinterJob;

public class FOP extends FDetalhe implements ChangeListener, PostListener, CancelListener, InsertListener, ActionListener, CarregaListener, KeyListener, FocusListener {

	private static final long serialVersionUID = 1L;

	private int casasDec = Aplicativo.casasDec;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JTabbedPanePad tpnAbas = new JTabbedPanePad();

	private JTextFieldPad txtCodOP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSeqOP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodProdEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescEst = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtQtdEst = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtCodProdDet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtRefProdDet = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldPad txtUsaDensidadeOP = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtRefProdEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescProdDet = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDtFabProd = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtQtdPrevProdOP = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );
	
	private JTextFieldPad txtQtdSugProdOP = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );
	
	private JTextFieldFK txtVlrDensidade = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 15, casasDec );
	
	private JTextFieldPad txtQtdFinalProdOP = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtDtValidOP = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtSeqItOp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtQtdItOp = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtQtdCopiaItOp = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldPad txtCodLoteProdRat = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodLoteProdDet = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescLoteProdDet = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodLoteProdEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtGeraRMAAut = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldFK txtDescLoteProdEst = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtSldLiqProd = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private JTextFieldFK txtUsaLoteDet = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldFK txtSitOp = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtUsaLoteEst = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodAlmoxEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescAlmoxEst = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodModLote = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescModLote = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldFK txtModLote = new JTextFieldFK( JTextFieldPad.TP_STRING, 100, 0 );

	private JTextFieldPad txtNroDiasValid = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtSeqEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtQtdDigRat = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, casasDec );

	private JTextFieldPad txtLoteRat = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtVencLoteRat = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtSldLoteRat = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, casasDec );

	private ListaCampos lcProdEstCod = new ListaCampos( this, "PD" );

	private ListaCampos lcProdEstRef = new ListaCampos( this, "PD" );

	private ListaCampos lcProdDetCod = new ListaCampos( this, "PD" );

	private ListaCampos lcProdDetRef = new ListaCampos( this, "PD" );

	private ListaCampos lcLoteProdDet = new ListaCampos( this, "LE" );

	private ListaCampos lcLoteProdEst = new ListaCampos( this, "LE" );

	private ListaCampos lcLoteProdRat = new ListaCampos( this, "LE" );

	private ListaCampos lcModLote = new ListaCampos( this, "ML" );

//	private JButton btFase = new JButton( "Fases", Icone.novo( "btFechaVenda.gif" ) );

	private JButton btFinaliza = new JButton( "Finaliza", Icone.novo( "btOP.gif" ) );
	
	private JButton btCancela = new JButton( "Cancela", Icone.novo( "btCancelar.gif" ) );
	
	private JButton btRMA = new JButton( "RMA", Icone.novo( "btRma.gif" ) );

	private JButton btLote = new JButton( "Lote", Icone.novo( "btSimilar.gif" ) );

	private JButton btRatearItem = new JButton( "", Icone.novo( "btAdic2.gif" ) );

	private JButton btDistrb = new JButton( "Distribui��o", Icone.novo( "btDistOP.gif" ) );

	private FPrinterJob dl = null;

	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );

	private JTextFieldPad txtCodTpMov = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private Integer iCodTpMov = null;

	private JPanelPad pinBotCab = new JPanelPad( 104, 96 );

	private ListaCampos lcAlmoxEst = new ListaCampos( this, "AX" );

	private Tabela tabSimu = new Tabela();

	public Tabela tabRMA = new Tabela();

	public Tabela tabOPS = new Tabela();

	private JScrollPane spSimu = new JScrollPane( tabSimu );

	public JScrollPane spRma = new JScrollPane( tabRMA );

	public JScrollPane spOPS = new JScrollPane( tabOPS );

	private ImageIcon imgCancelada = Icone.novo( "clVencido.gif" );

	private ImageIcon imgExpedida = Icone.novo( "clPago.gif" );

	private ImageIcon imgAprovada = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgOPPrinc = Icone.novo( "clEng_dourada.gif" );

	private ImageIcon imgOPSub = Icone.novo( "clEng_azul.gif" );

	private ImageIcon imgPendente = Icone.novo( "clNaoVencido.gif" );

	private ImageIcon imgColunaRMA = null;

	private boolean bBuscaRMA = false;

	private boolean bBuscaOPS = false;
	
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> prefere = null;

	public FOP( int codOp, int seqOp ) {

		txtCodOP.setVlrInteger( codOp );
		txtSeqOP.setVlrInteger( seqOp );
	}
	
	public FOP(){
		
	}

	private void montaTela() {


		btRatearItem.setBorder( BorderFactory.createEmptyBorder() );
		setName( "Ordens de produ��o" );
		setTitulo( "Ordens de produ��o" );
		setAtribos( 15, 10, 700, 580 );
		setAltCab( 200 );

		pnMaster.remove( spTab );

		tpnAbas.addTab( "Previs�o", spSimu );
		tpnAbas.addTab( "OP", spTab );
		tpnAbas.addTab( "Rma", spRma );
		tpnAbas.addTab( "OP's relacionadas", spOPS );
		pnMaster.add( tpnAbas, BorderLayout.CENTER );

		btFinaliza.setToolTipText( "Processo de produ��o" );
		btRMA.setToolTipText( "Gera ou exibe RMA." );		 
		btLote.setToolTipText( "Cadastra lote" );
		btRatearItem.setToolTipText( "Ratear �tem" );
		btDistrb.setToolTipText( "Distribui��o" );
		btCancela.setToolTipText( "Cancela O.P." );

		pinCab.adic( pinBotCab, 560, 2, 115, 159 );
	
		pinBotCab.adic( btLote, 0, 0, 110, 30 );
		pinBotCab.adic( btRMA, 0, 31, 110, 30 );
		pinBotCab.adic( btDistrb, 0, 62, 110, 30 );
		pinBotCab.adic( btFinaliza, 0, 93, 110, 30 );
		pinBotCab.adic( btCancela, 0, 124, 110, 30 );
		
		lcModLote.add( new GuardaCampo( txtCodModLote, "CodModLote", "Cod.Mod.Lote", ListaCampos.DB_PK, txtDescModLote, false ) );
		lcModLote.add( new GuardaCampo( txtDescModLote, "DescModLote", "Descri��o do modelo de lote", ListaCampos.DB_SI, false ) );
		lcModLote.add( new GuardaCampo( txtModLote, "txaModLote", "Corpo", ListaCampos.DB_SI, false ) );
		lcModLote.montaSql( false, "MODLOTE", "EQ" );
		lcModLote.setQueryCommit( false );
		lcModLote.setReadOnly( true );
		txtCodModLote.setTabelaExterna( lcModLote );

		lcAlmoxEst.add( new GuardaCampo( txtCodAlmoxEst, "CodAlmox", "Cod.almox.", ListaCampos.DB_PK, txtDescAlmoxEst, false ) );
		lcAlmoxEst.add( new GuardaCampo( txtDescAlmoxEst, "DescAlmox", "Descri��o do almoxarifado", ListaCampos.DB_SI, false ) );
		lcAlmoxEst.montaSql( false, "ALMOX", "EQ" );
		lcAlmoxEst.setQueryCommit( false );
		lcAlmoxEst.setReadOnly( true );
		txtDescAlmoxEst.setSoLeitura( true );
		txtCodAlmoxEst.setTabelaExterna( lcAlmoxEst );

		// FK de Lotes
		lcLoteProdEst.add( new GuardaCampo( txtCodLoteProdEst, "CodLote", "Lote", ListaCampos.DB_PK, txtDescLoteProdEst, false ) );
		lcLoteProdEst.add( new GuardaCampo( txtDescLoteProdEst, "VenctoLote", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcLoteProdEst.add( new GuardaCampo( txtSldLiqProd, "SldLiqLote", "Saldo", ListaCampos.DB_SI, false ) );
		lcLoteProdEst.montaSql( false, "LOTE", "EQ" );
		lcLoteProdEst.setQueryCommit( false );
		lcLoteProdEst.setReadOnly( true );
		txtCodLoteProdEst.setTabelaExterna( lcLoteProdEst );

		// lista campos para o lote do rateio
		txtLoteRat.setNomeCampo( "CodLote" );
		txtLoteRat.setFK( true );
		lcLoteProdRat.add( new GuardaCampo( txtLoteRat, "CodLote", "Lote", ListaCampos.DB_PK, txtVencLoteRat, false ) );
		lcLoteProdRat.add( new GuardaCampo( txtVencLoteRat, "VenctoLote", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcLoteProdRat.add( new GuardaCampo( txtSldLoteRat, "SldLiqLote", "Saldo", ListaCampos.DB_SI, false ) );
		lcLoteProdRat.setDinWhereAdic( "CODPROD=#N", txtCodProdDet );
		lcLoteProdRat.montaSql( false, "LOTE", "EQ" );
		lcLoteProdRat.setQueryCommit( false );
		lcLoteProdRat.setReadOnly( true );
		txtLoteRat.setTabelaExterna( lcLoteProdRat );

		lcTipoMov.add( new GuardaCampo( txtCodTpMov, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.setWhereAdic( "((ESTIPOMOV = 'E') AND TIPOMOV='OP' AND" + " ( TUSUTIPOMOV='S' OR	EXISTS (SELECT * FROM EQTIPOMOVUSU TU " + "WHERE TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND " + "TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS=" + Aplicativo.iCodEmp
				+ " AND " + "TU.CODFILIALUS=" + ListaCampos.getMasterFilial( "SGUSUARIO" ) + " AND TU.IDUSU='" + Aplicativo.strUsuario + "') ) " + ")" );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setReadOnly( true );
		txtCodTpMov.setTabelaExterna( lcTipoMov );

		lcProdEstCod.add( new GuardaCampo( txtCodProdEst, "Codprod", "C�d.prod.", ListaCampos.DB_PK, txtDescEst, true ) );
		lcProdEstCod.add( new GuardaCampo( txtSeqEst, "seqest", "Seq.Est.", ListaCampos.DB_PK, txtDescEst, true ) );
		lcProdEstCod.add( new GuardaCampo( txtDescEst, "DescEst", "Descri��o da estrutura", ListaCampos.DB_SI, false ) );
		lcProdEstCod.add( new GuardaCampo( txtRefProdEst, "refprod", "Refer�ncia", ListaCampos.DB_SI, false ) );
		lcProdEstCod.add( new GuardaCampo( txtQtdEst, "QtdEst", "Quantidade", ListaCampos.DB_SI, false ) );
		lcProdEstCod.add( new GuardaCampo( txtCodModLote, "CodModLote", "Modelo de Lote", ListaCampos.DB_FK, false ) );
		lcProdEstCod.add( new GuardaCampo( txtNroDiasValid, "NroDiasValid", "Dias de validade", ListaCampos.DB_SI, false ) );
		lcProdEstCod.add( new GuardaCampo( txtUsaDensidadeOP, "UsaDensidadeOp", "Usa Densidade", ListaCampos.DB_SI, false ) );
		
		lcProdEstCod.setWhereAdic( "ATIVOEST='S'" );
		lcProdEstCod.montaSql( false, "ESTRUTURA", "PP" );
		lcProdEstCod.setQueryCommit( false );
		lcProdEstCod.setReadOnly( true );
		txtCodProdEst.setTabelaExterna( lcProdEstCod );
		txtSeqEst.setTabelaExterna( lcProdEstCod );
		txtCodProdEst.setNomeCampo( "codprod" );

		lcProdEstRef.add( new GuardaCampo( txtRefProdEst, "refprod", "Refer�ncia", ListaCampos.DB_PK, txtDescEst, true ) );
		lcProdEstRef.add( new GuardaCampo( txtSeqEst, "seqest", "Seq.Est.", ListaCampos.DB_PK, txtDescEst, true ) );
		lcProdEstRef.add( new GuardaCampo( txtCodProdEst, "Codprod", "C�d.prod.", ListaCampos.DB_SI, true ) );
		lcProdEstRef.add( new GuardaCampo( txtDescEst, "DescEst", "Descri��o da estrutura", ListaCampos.DB_SI, false ) );
		lcProdEstRef.add( new GuardaCampo( txtQtdEst, "QtdEst", "Quantidade", ListaCampos.DB_SI, false ) );
		lcProdEstRef.add( new GuardaCampo( txtCodModLote, "CodModLote", "Modelo de Lote", ListaCampos.DB_FK, false ) );
		lcProdEstRef.add( new GuardaCampo( txtNroDiasValid, "NroDiasValid", "Dias de validade", ListaCampos.DB_SI, false ) );
		lcProdEstRef.add( new GuardaCampo( txtUsaDensidadeOP, "UsaDensidadeOp", "Usa Densidade", ListaCampos.DB_SI, false ) );		

		lcProdEstRef.setWhereAdic( "ATIVOEST='S'" );
		lcProdEstRef.montaSql( false, "ESTRUTURA", "PP" );
		lcProdEstRef.setQueryCommit( false );
		lcProdEstRef.setReadOnly( true );
		txtRefProdEst.setTabelaExterna( lcProdEstRef );
		txtSeqEst.setTabelaExterna( lcProdEstRef );
		txtRefProdEst.setNomeCampo( "refprod" );

		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );

		adicCampo( txtCodOP, 7, 20, 70, 20, "CodOP", "N� OP.", ListaCampos.DB_PK, true );
		adicCampo( txtSeqOP, 80, 20, 60, 20, "SeqOP", "Seq. OP.", ListaCampos.DB_PK, true );
		adicCampo( txtCodTpMov, 143, 20, 70, 20, "CodTipoMov", "C�d.Tp.Mov.", ListaCampos.DB_FK, txtDescTipoMov, true );
		adicDescFK( txtDescTipoMov, 216, 20, 258, 20, "DescTipoMov", "C�d.Tp.Mov." );
		adicCampo( txtDtFabProd, 477, 20, 75, 20, "dtfabrop", "Dt.Fabric.", ListaCampos.DB_SI, true );

		if ( (Boolean) prefere.get( "USAREFPROD" ) ) {
			adicCampo( txtRefProdEst, 7, 60, 70, 20, "refprod", "Refer�ncia", ListaCampos.DB_FK, true );
			adicCampoInvisivel( txtCodProdEst, "CodProd", "C�d.prod.", ListaCampos.DB_FK, txtDescEst, true );
			txtCodProdEst.setFK( true );
		}
		else {
			adicCampo( txtCodProdEst, 7, 60, 70, 20, "codprod", "C�d.prod.", ListaCampos.DB_FK, txtDescEst, true );
			adicCampoInvisivel( txtRefProdEst, "RefProd", "Ref.prod.", ListaCampos.DB_FK, null, true );
			txtRefProdEst.setFK( true );
		}
		
		adicCampo( txtSeqEst, 80, 60, 60, 20, "seqest", "Seq.Est.", ListaCampos.DB_FK, txtDescEst, true );
		adicDescFK( txtDescEst, 143, 60, 184, 20, "descprod", "Descri��o da estrutura" );
		adicDescFK( txtQtdEst, 330, 60, 80, 20, "qtdest", "Qtd.est." );
		
		pinCab.adic( new JLabelPad("Densid."), 413,40 ,57 ,20 );
		pinCab.adic( txtVlrDensidade, 413, 60, 57, 20 ); // xxx
//		pinDados.adic( txtQtdFinalProdOP, 413, 60, 80, 20 ); // xxx

		formataCampoLimpo( txtQtdPrevProdOP, new Color(0,0,255) );
		formataCampoLimpo( txtQtdFinalProdOP, new Color(255,0,0) );
		
		adicCampo( txtQtdSugProdOP, 473, 60, 80, 20, "qtdsugprodop", "Qtd.Sugerida", ListaCampos.DB_SI, true );		
		adicCampo( txtQtdPrevProdOP, 7, 140, 110, 20, "qtdprevprodop", "Qtd.prevista", ListaCampos.DB_SI, false );
		adicCampo( txtQtdFinalProdOP, 120, 140, 110, 20, "qtdfinalprodop", "Qtd.Produzida", ListaCampos.DB_SI, false );
		
		adicCampo( txtCodAlmoxEst, 7, 100, 70, 20, "codalmox", "C�d.Almox.", ListaCampos.DB_FK, txtDescAlmoxEst, true );
		adicDescFK( txtDescAlmoxEst, 80, 100, 300, 20, "descalmox", "Descri��o do almoxarifado" );
		adicCampo( txtCodLoteProdEst, 383, 100, 87, 20, "CodLote", "Lote", ListaCampos.DB_FK, txtDescLoteProdEst, false );
		adicDescFKInvisivel( txtDescLoteProdEst, "VenctoLote", "Vencto.Lote" );
		adicCampo( txtDtValidOP, 473, 100, 80, 20, "dtvalidpdop", "Dt. validade", ListaCampos.DB_SI, false );
		
		adicCampo( txtSitOp, 473,140,50,20, "sitop", "sit.op.", ListaCampos.DB_SI, false );
				
		setListaCampos( true, "OP", "PP" );

		txtCodTpMov.setAtivo( false );

		lcCampos.addCarregaListener( this );
		lcDet.addCarregaListener( this );
		lcProdEstCod.addCarregaListener( this );
		lcProdEstRef.addCarregaListener( this );
		lcLoteProdEst.addCarregaListener( this );
		lcModLote.addCarregaListener( this );

		lcCampos.addInsertListener( this );
		lcDet.addInsertListener( this );

		lcCampos.addPostListener( this );
		lcDet.addPostListener( this );

		lcCampos.addCancelListener( this );
		lcDet.addCancelListener( this );

		btFinaliza.addActionListener( this );
		
		btRMA.addActionListener( this );
		btLote.addActionListener( this );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btRatearItem.addActionListener( this );
		btDistrb.addActionListener( this );
		btCancela.addActionListener( this );

		txtQtdSugProdOP.addFocusListener( this );
		txtSeqEst.addFocusListener( this );

		montaDet();

		tabSimu.adicColuna( "Fase" );// 0
		tabSimu.adicColuna( "C�d.prod" );// 1
		tabSimu.adicColuna( "Descri��o do produto" );// 2
		tabSimu.adicColuna( "und." );// 3
		tabSimu.adicColuna( "Compsi��o" );// 4
		tabSimu.adicColuna( "Saldo" );// 5
		tabSimu.adicColuna( "Utilizado" );// 6
		tabSimu.adicColuna( "rma" );// 7

		tabSimu.setTamColuna( 40, 0 );
		tabSimu.setTamColuna( 70, 1 );
		tabSimu.setTamColuna( 200, 2 );
		tabSimu.setTamColuna( 40, 3 );
		tabSimu.setTamColuna( 80, 4 );
		tabSimu.setTamColuna( 80, 5 );
		tabSimu.setTamColuna( 80, 6 );
		tabSimu.setTamColuna( 30, 7 );

		tabRMA.adicColuna( "" );// 0
		tabRMA.adicColuna( "C�d.rma." );// 1
		tabRMA.adicColuna( "C�d.prod." );// 2
		tabRMA.adicColuna( "Descri��o do produto" );// 3
		tabRMA.adicColuna( "Aprov." );// 4
		tabRMA.adicColuna( "Exp." );// 5
		tabRMA.adicColuna( "Dt. req." );// 6
		tabRMA.adicColuna( "Qt. req." );// 7
		tabRMA.adicColuna( "Dt. aprov" );// 8
		tabRMA.adicColuna( "Qt. aprov" );// 9
		tabRMA.adicColuna( "Dt. exp" );// 10
		tabRMA.adicColuna( "Qt. exp" );// 11
		tabRMA.adicColuna( "Saldo" );// 12

		tabRMA.setTamColuna( 13, 0 );
		tabRMA.setTamColuna( 80, 1 );
		tabRMA.setTamColuna( 80, 2 );
		tabRMA.setTamColuna( 180, 3 );
		tabRMA.setTamColuna( 50, 4 );
		tabRMA.setTamColuna( 50, 5 );
		tabRMA.setTamColuna( 80, 6 );
		tabRMA.setTamColuna( 80, 7 );
		tabRMA.setTamColuna( 80, 8 );
		tabRMA.setTamColuna( 80, 9 );
		tabRMA.setTamColuna( 80, 10 );
		tabRMA.setTamColuna( 80, 11 );
		tabRMA.setTamColuna( 80, 12 );

		tabOPS.adicColuna( "" );// 0
		tabOPS.adicColuna( "C�d.OP." );// 1
		tabOPS.adicColuna( "Seq.OP." );// 2
		tabOPS.adicColuna( "C�d.PD." );// 3
		tabOPS.adicColuna( "Seq.Est." );// 4
		tabOPS.adicColuna( "Descri��o do produto" );// 5
		tabOPS.adicColuna( "Descri��o da estrutura" );// 6

		tabOPS.setTamColuna( 13, 0 );
		tabOPS.setTamColuna( 70, 1 );
		tabOPS.setTamColuna( 70, 2 );
		tabOPS.setTamColuna( 70, 3 );
		tabOPS.setTamColuna( 70, 4 );
		tabOPS.setTamColuna( 200, 5 );
		tabOPS.setTamColuna( 200, 6 );

		tabRMA.addMouseListener( new MouseAdapter() {

			public void mouseClicked( MouseEvent mevt ) {

				if ( mevt.getSource() == tabRMA && mevt.getClickCount() == 2 )
					abreRma();
			}
		} );

		tabOPS.addMouseListener( new MouseAdapter() {

			public void mouseClicked( MouseEvent mevt ) {

				if ( mevt.getSource() == tabOPS && mevt.getClickCount() == 2 )
					abreOps();
			}
		} );

		tpnAbas.addChangeListener( this );
		txtCodOP.addFocusListener( this );
		txtSeqOP.addKeyListener( this );
		setImprimir( true );

	}

	private void formataCampoLimpo(JTextFieldPad campo, Color cor) {
		try {
			campo.setAtivo( false );
			campo.tiraBorda();
			campo.setForeground( cor );
			campo.setFont(new Font("Dialog", Font.BOLD, 14));
			campo.setHorizontalAlignment( SwingConstants.LEFT );						
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void montaDet() {

		setAltDet( 60 );
		pinDet = new JPanelPad( 440, 50 );
		setPainel( pinDet, pnDet );

		txtCodLoteProdDet.setAtivo( true );
		txtSeqItOp.setAtivo( false );
		txtQtdItOp.setAtivo( false );
		txtCodProdDet.setAtivo( false );
		txtRefProdDet.setAtivo( false );

		// FK de Lotes
		lcLoteProdDet.add( new GuardaCampo( txtCodLoteProdDet, "CodLote", "Lote", ListaCampos.DB_PK, txtDescLoteProdDet, false ) );
		lcLoteProdDet.add( new GuardaCampo( txtDescLoteProdDet, "VenctoLote", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcLoteProdDet.add( new GuardaCampo( txtSldLiqProd, "SldLiqLote", "Saldo", ListaCampos.DB_SI, false ) );
		lcLoteProdDet.setDinWhereAdic( "CODPROD=#N", txtCodProdDet );
		lcLoteProdDet.montaSql( false, "LOTE", "EQ" );
		lcLoteProdDet.setQueryCommit( false );
		lcLoteProdDet.setReadOnly( true );
		txtCodLoteProdDet.setTabelaExterna( lcLoteProdDet );

		lcProdDetCod.add( new GuardaCampo( txtCodProdDet, "Codprod", "C�d.prod.", ListaCampos.DB_PK, txtDescProdDet, true ) );
		lcProdDetCod.add( new GuardaCampo( txtDescProdDet, "Descprod", "Descri�ao do produto", ListaCampos.DB_SI, false ) );
		lcProdDetCod.add( new GuardaCampo( txtRefProdDet, "refprod", "referencia", ListaCampos.DB_SI, false ) );
		lcProdDetCod.add( new GuardaCampo( txtUsaLoteDet, "CLOTEPROD", "Usa Lote", ListaCampos.DB_SI, false ) );
		lcProdDetCod.montaSql( false, "PRODUTO", "EQ" );
		lcProdDetCod.setQueryCommit( false );
		lcProdDetCod.setReadOnly( true );
		txtCodProdDet.setTabelaExterna( lcProdDetCod );
		txtCodProdDet.setNomeCampo( "codprod" );

		lcProdDetRef.add( new GuardaCampo( txtRefProdDet, "refprod", "C�d.prod.", ListaCampos.DB_PK, txtDescProdDet, true ) );
		lcProdDetRef.add( new GuardaCampo( txtDescProdDet, "Descprod", "Descri�ao do produto", ListaCampos.DB_SI, false ) );
		lcProdDetRef.add( new GuardaCampo( txtCodProdDet, "Codprod", "C�d.prod.", ListaCampos.DB_SI, true ) );
		lcProdDetRef.add( new GuardaCampo( txtUsaLoteDet, "CLOTEPROD", "Usa Lote", ListaCampos.DB_SI, false ) );
		lcProdDetRef.montaSql( false, "PRODUTO", "EQ" );
		lcProdDetRef.setQueryCommit( false );
		lcProdDetRef.setReadOnly( true );
		txtRefProdDet.setTabelaExterna( lcProdDetRef );
		txtRefProdDet.setNomeCampo( "refprod" );
		txtRefProdDet.setFK( true );

		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtSeqItOp, 7, 20, 50, 20, "seqitop", "Seq.", ListaCampos.DB_PK, true );
		if ( (Boolean) prefere.get( "USAREFPROD" ) ) {
			adicCampo( txtCodProdDet, 60, 20, 70, 20, "CodProd", "C�d.prod.", ListaCampos.DB_PF, txtDescProdDet, true );
			txtCodProdDet.setBuscaAdic( new DLBuscaProd( con, "CODPROD", lcProdDetCod.getWhereAdic() ) );
		}
		else {
			adic( new JLabelPad( "Refer�ncia" ), 60, 0, 70, 20 );
			adic( txtRefProdDet, 60, 20, 70, 20 );
			adicCampoInvisivel( txtCodProdDet, "CodProd", "C�d.prod.", ListaCampos.DB_PF, txtDescProdDet, true );
			txtRefProdDet.setFK( true );
			txtRefProdDet.setBuscaAdic( new DLBuscaProd( con, "REFPROD", lcProdDetRef.getWhereAdic() ) );
		}
		adicDescFK( txtDescProdDet, 133, 20, 250, 20, "descprod", "Descri��o do produto" );
		adicCampo( txtCodLoteProdDet, 386, 20, 90, 20, "codlote", "Lote", ListaCampos.DB_FK, false );
		adicCampo( txtQtdItOp, 479, 20, 90, 20, "qtditop", "Quantidade", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtQtdCopiaItOp, "qtdcopiaitop", "Qtd. rateada", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodLoteProdRat, "codloterat", "lote rateado", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtGeraRMAAut, "GERARMA", "Gera Rma", ListaCampos.DB_SI, false );
		setListaCampos( true, "ITOP", "PP" );
		lcDet.setQueryInsert( false );

		adic( btRatearItem, 572, 20, 20, 20 );

		btFinaliza.setEnabled( false );
		
		btRMA.setEnabled( false );
		btLote.setEnabled( false );
		btDistrb.setEnabled( false );
		btCancela.setEnabled( false );

		montaTab();
		tab.setTamColuna( 55, 0 );
		tab.setTamColuna( 70, 1 );
		tab.setTamColuna( 200, 2 );
		tab.setTamColuna( 100, 3 );
		tab.setTamColuna( 0, 4 );
	}

	private BigDecimal getQtdTotal( BigDecimal arg ) {

		BigDecimal ret = null;

		try {
			ret = arg.multiply( txtQtdPrevProdOP.getVlrBigDecimal() );
		} catch ( Exception e ) {
			ret = new BigDecimal( 0 );
		}

		ret = ret.setScale( Aplicativo.casasDec, BigDecimal.ROUND_HALF_UP );
		return ret;

	}

	private String getLote( ListaCampos lcProd, JTextFieldPad txtProd, boolean bSaldoPos ) {

		String sRet = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		try {

			sSQL = "SELECT MIN(L.CODLOTE) FROM EQLOTE L WHERE " + "L.CODPROD=? AND L.CODFILIAL=? " + ( bSaldoPos ? "AND L.SLDLIQLOTE>0 " : "" ) + "AND L.CODEMP=? AND L.VENCTOLOTE = " + "( " + "SELECT MIN(VENCTOLOTE) FROM EQLOTE LS WHERE LS.CODPROD=L.CODPROD "
					+ "AND LS.CODFILIAL=L.CODFILIAL AND LS.CODEMP=L.CODEMP AND LS.SLDLIQLOTE>0 " + "AND VENCTOLOTE >= CAST('today' AS DATE)" + ")";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, txtProd.getVlrInteger().intValue() );
			ps.setInt( 2, lcProd.getCodFilial() );
			ps.setInt( 3, Aplicativo.iCodEmp );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				String sCodLote = rs.getString( 1 );
				if ( sCodLote != null )
					sRet = sCodLote.trim();
			}

			rs.close();
			ps.close();
			if ( !con.getAutoCommit() )
				con.commit();
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar lote!\n" + err );
		} 
		finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return sRet;
	}

	private void getRma() {
		
		String codop = null; 
		String seqop = null; 
		String sitrma = null;
		String sitaprovrma = null;
		String sitexprma = null;
		StringBuffer sql = new StringBuffer();		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int iLin = 0;

		try {
			
			codop = txtCodOP.getVlrString();
			seqop = txtSeqOP.getVlrString();
			tabRMA.limpa();
			
			if ( ( codop.trim().equals( "" ) ) || ( seqop.trim().equals( "" ) ) ) {
				return;
			}

			sql.append( "SELECT R.CODRMA, IT.CODPROD,IT.REFPROD,PD.DESCPROD,IT.SITITRMA,");
			sql.append( "IT.SITAPROVITRMA,IT.SITEXPITRMA,IT.DTINS,IT.DTAPROVITRMA,IT.DTAEXPITRMA,");
			sql.append( "IT.QTDITRMA,IT.QTDAPROVITRMA,IT.QTDEXPITRMA,PD.SLDPROD,R.CODOP ");
			sql.append( "FROM EQRMA R, EQITRMA IT, EQPRODUTO PD ");
			sql.append( "WHERE R.CODEMP=IT.CODEMP AND R.CODFILIAL=IT.CODFILIAL AND R.CODRMA=IT.CODRMA ");
			sql.append( "AND PD.CODEMP=IT.CODEMP AND PD.CODFILIAL=IT.CODFILIAL AND PD.CODPROD=IT.CODPROD ");
			sql.append( "AND R.CODEMPOF=? AND R.CODFILIALOF=? AND R.CODOP=? AND R.SEQOP=?");

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, lcCampos.getCodFilial() );
			ps.setInt( 3, Integer.parseInt( codop ) );
			ps.setInt( 4, Integer.parseInt( seqop ) );

			rs = ps.executeQuery();

			while ( rs.next() ) {

				tabRMA.adicLinha();

				sitrma = rs.getString( 5 );
				sitaprovrma = rs.getString( 6 );
				sitexprma = rs.getString( 7 );
				
				if ( sitrma.equalsIgnoreCase( "PE" ) ) {
					imgColunaRMA = imgPendente;
				}
				else if ( sitrma.equalsIgnoreCase( "CA" ) ) {
					imgColunaRMA = imgCancelada;
				}
				else if ( sitrma.equalsIgnoreCase( "EF" ) || sitexprma.equals( "EP" ) || sitexprma.equals( "ET" ) ) {
					imgColunaRMA = imgExpedida;
				}
				else if ( sitrma.equalsIgnoreCase( "AF" ) || sitaprovrma.equals( "AP" ) || sitaprovrma.equals( "AT" ) ) {
					imgColunaRMA = imgAprovada;
				}
				
				tabRMA.setValor( imgColunaRMA, iLin, 0 );// SitItRma
				tabRMA.setValor( new Integer( rs.getInt( 1 ) ), iLin, 1 );// CodRma
				tabRMA.setValor( rs.getString( 2 ) == null ? "" : rs.getString( 2 ) + "", iLin, 2 );// CodProd
				tabRMA.setValor( rs.getString( 4 ) == null ? "" : rs.getString( 4 ).trim() + "", iLin, 3 );// DescProd
				tabRMA.setValor( rs.getString( 6 ) == null ? "" : rs.getString( 6 ) + "", iLin, 4 );// SitAprov
				tabRMA.setValor( rs.getString( 7 ) == null ? "" : rs.getString( 7 ) + "", iLin, 5 );// SitExp
				tabRMA.setValor( rs.getString( 8 ) == null ? "" : Funcoes.sqlDateToStrDate( rs.getDate( 8 ) ) + "", iLin, 6 );// Dt Req
				tabRMA.setValor( rs.getString( 9 ) == null ? "" : Funcoes.sqlDateToStrDate( rs.getDate( 9 ) ) + "", iLin, 8 );// Dt Aprov
				tabRMA.setValor( rs.getString( 10 ) == null ? "" : Funcoes.sqlDateToStrDate( rs.getDate( 10 ) ) + "", iLin, 10 );// Dt Exp
				tabRMA.setValor( rs.getString( 11 ) == null ? "" : rs.getString( 11 ) + "", iLin, 7 );// Qtd Req
				tabRMA.setValor( rs.getString( 12 ) == null ? "" : rs.getString( 12 ) + "", iLin, 9 );// Qtd Aprov
				tabRMA.setValor( rs.getString( 13 ) == null ? "" : rs.getString( 13 ) + "", iLin, 11 );// Qdt Exp
				tabRMA.setValor( rs.getString( 14 ) == null ? "" : rs.getString( 14 ) + "", iLin, 12 );// Saldo Prod

				iLin++;

			}

			if ( !con.getAutoCommit() )
				con.commit();
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela EQRMA!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} 
		finally {
			ps = null;
			rs = null;
			sql = null;
			sitrma = null;
			sitaprovrma = null;
			sitexprma = null;
		}
	}

	private void getOPS() {
		String codop; 
		String seqop;
		int iLin = 0;
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			tabOPS.limpa();
			
			codop = txtCodOP.getVlrString();
			seqop = txtSeqOP.getVlrString();
			
			if ( ( codop.equals( "" ) ) || ( seqop.equals( "" ) ) ) {
				return;
			}
			
			sql.append( "SELECT OP.CODOP,OP.SEQOP,PD.CODPROD,OP.SEQEST,PD.DESCPROD,ET.DESCEST, "); 
			sql.append( "OP.QTDPREVPRODOP, OP.QTDFINALPRODOP,OP.DTFABROP,OP.SITOP ");
			sql.append( "FROM PPOP OP, EQPRODUTO PD, PPESTRUTURA ET ");
			sql.append( "WHERE ET.CODEMP=OP.CODEMPPD AND ET.CODFILIAL=OP.CODFILIALPD AND ET.CODPROD=OP.CODPROD ");
			sql.append( "AND ET.SEQEST=OP.SEQEST AND PD.CODEMP=OP.CODEMPPD AND PD.CODFILIAL=OP.CODFILIALPD ");
			sql.append( "AND PD.CODPROD=OP.CODPROD AND OP.CODEMP=? AND OP.CODFILIAL=? AND OP.CODOP=? ");
			sql.append( "ORDER BY OP.SEQOP");
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, lcCampos.getCodEmp() );
			ps.setInt( 2, lcCampos.getCodFilial() );
			ps.setInt( 3, txtCodOP.getVlrInteger().intValue() );
			rs = ps.executeQuery();

			while ( rs.next() ) {

				tabOPS.adicLinha();

				if ( rs.getInt( "SEQOP" ) == 0 ) {
					tabOPS.setValor( imgOPPrinc, iLin, 0 );
				}
				else {
					tabOPS.setValor( imgOPSub, iLin, 0 );
				}

				tabOPS.setValor( new Integer( rs.getInt( "CODOP" ) ), iLin, 1 );
				tabOPS.setValor( new Integer( rs.getInt( "SEQOP" ) ), iLin, 2 );
				tabOPS.setValor( new Integer( rs.getInt( "CODPROD" ) ), iLin, 3 );
				tabOPS.setValor( new Integer( rs.getInt( "SEQEST" ) ), iLin, 4 );
				tabOPS.setValor( rs.getString( "DESCPROD" ), iLin, 5 );
				tabOPS.setValor( rs.getString( "DESCEST" ), iLin, 6 );

				iLin++;

			}

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela EQRMA!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} 
		finally {
			ps = null;
			rs = null;
			sql = null;
		}
	}

	private void getTipoMov() {
		if ( txtCodTpMov.getVlrString().equals( "" ) ) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sSQL = "SELECT CODTIPOMOV FROM SGPREFERE5 WHERE CODEMP=? AND CODFILIAL=?";

			try {
				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
				rs = ps.executeQuery();
				if ( rs.next() ) {
					if ( rs.getString( 1 ) != null )
						iCodTpMov = new Integer( rs.getInt( 1 ) );
					else {
						iCodTpMov = new Integer( 0 );
						Funcoes.mensagemInforma( null, "N�o existe um tipo de movimento padr�o para OP definido nas prefer�ncias!" );
					}
				}
				rs.close();
				ps.close();
			} 
			catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao buscar documento de prefer�ncias!\n" + err.getMessage() );
			} 
			finally {
				ps = null;
				rs = null;
				sSQL = null;
			}
		}
	}

	public void carregaProduto() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CLOTEPROD,VLRDENSIDADE FROM EQPRODUTO WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=?";
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			ps.setInt( 3, txtCodProdEst.getVlrInteger().intValue() );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				txtUsaLoteEst.setVlrString( rs.getString( "CLOTEPROD" ) );
				txtVlrDensidade.setVlrBigDecimal( rs.getBigDecimal( "VLRDENSIDADE" ) == null ? new BigDecimal(1) : rs.getBigDecimal( "VLRDENSIDADE" ) );				
			}

			rs.close();
			ps.close();
			if ( !con.getAutoCommit() )
				con.commit();
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar obrigatoriedade de lote no produto!\n", true, con, err );
		} 
		finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	private void simularOP() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		Object[] linha = new Object[ 8 ];

		try {

			tabSimu.limpa();
			
			if(txtUsaDensidadeOP.getVlrString().equals( "S" )) {
				txtQtdPrevProdOP.setVlrBigDecimal( txtQtdSugProdOP.getVlrBigDecimal().multiply( txtVlrDensidade.getVlrBigDecimal() ) );
			}
			else {
				txtQtdPrevProdOP.setVlrBigDecimal( txtQtdSugProdOP.getVlrBigDecimal() );
			}
			
			sSQL = "SELECT IT.CODFASE, IT.SEQITEST, IT.CODPRODPD, P.DESCPROD, " + "P.CODUNID, IT.QTDITEST, P.SLDLIQPROD, IT.RMAAUTOITEST " + "FROM PPESTRUTURA E, PPITESTRUTURA IT, EQPRODUTO P " + "WHERE E.CODEMP=? AND E.CODFILIAL=? AND E.CODPROD=? AND E.SEQEST=? "
					+ "AND E.CODEMP=IT.CODEMP AND E.CODFILIAL=IT.CODFILIAL " + "AND E.CODPROD=IT.CODPROD AND E.SEQEST=IT.SEQEST " + "AND P.CODEMP=IT.CODEMPPD AND P.CODFILIAL=IT.CODFILIALPD AND P.CODPROD=IT.CODPRODPD " + "ORDER BY IT.CODFASE, IT.SEQITEST";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPITESTRUTURA" ) );
			ps.setInt( 3, txtCodProdEst.getVlrInteger().intValue() );
			ps.setInt( 4, txtSeqEst.getVlrInteger().intValue() );
			rs = ps.executeQuery();

			while ( rs.next() ) {

				linha[ 0 ] = new Integer( rs.getInt( "CODFASE" ) );
				linha[ 1 ] = new Integer( rs.getInt( "CODPRODPD" ) );
				linha[ 2 ] = rs.getString( "DESCPROD" ) != null ? rs.getString( "DESCPROD" ).trim() : "";
				linha[ 3 ] = rs.getString( "CODUNID" ) != null ? rs.getString( "CODUNID" ) : "";
				linha[ 4 ] = ( rs.getBigDecimal( "QTDITEST" ) != null ? rs.getBigDecimal( "QTDITEST" ) : new BigDecimal( 0 ) );
				linha[ 4 ] = ( (BigDecimal) linha[ 4 ] ).setScale( Aplicativo.casasDec, BigDecimal.ROUND_HALF_UP );
				linha[ 5 ] = ( rs.getBigDecimal( "SLDLIQPROD" ) != null ? rs.getBigDecimal( "SLDLIQPROD" ) : new BigDecimal( 0 ) );
				linha[ 5 ] = ( (BigDecimal) linha[ 5 ] ).setScale( Aplicativo.casasDec, BigDecimal.ROUND_HALF_UP );
				linha[ 6 ] = getQtdTotal( ( rs.getBigDecimal( "QTDITEST" ) != null ? rs.getBigDecimal( "QTDITEST" ) : new BigDecimal( 0 ) ) );
				linha[ 7 ] = rs.getString( "RMAAUTOITEST" ) != null ? rs.getString( "RMAAUTOITEST" ) : "";

				tabSimu.adicLinha( linha );

			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() )
				con.commit();

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao consulrar tabela de PPITESTRUTURA.\n" + e.getMessage() );
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao simular OP.\n" + e.getMessage() );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			linha = null;
		}

	}

	private void abreRma() {

		int iRma = ( (Integer) tabRMA.getValor( tabRMA.getLinhaSel(), 1 ) ).intValue();
		if ( fPrim.temTela( "Requisi��o de material" ) == false ) {
			FRma tela = new FRma();
			fPrim.criatela( "Requisi��o de material", tela, con );
			tela.exec( iRma );
		}
	}

	private void abreOps() {

	}

	public void finalizaOP() {
		if ( fPrim.temTela( "OP x Fases" ) == false ) {
			int codop = txtCodOP.getVlrInteger().intValue();
			int seqop = txtSeqOP.getVlrInteger().intValue();
			int seqest = txtSeqEst.getVlrInteger().intValue();
			
			FOPFase tela = new FOPFase(codop , seqop, seqest );
			fPrim.criatela( "OP x Fases", tela, con );
			tela.setConexao( con );
		}
	}

	private boolean temSldLote() {

		boolean bRet = false; 
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sSQL = null;
		String sSaida = "";
		int iSldNeg = 0;
		int iTemp = 0;
		float fSldLote = 0f;
		try {
			sSQL = "SELECT SLDLOTE FROM EQLOTE " + "WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=? AND CODLOTE=? ";
			for ( int i = 0; i < tab.getRowCount(); i++ ) {
				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, Aplicativo.iCodFilial );
				ps.setInt( 3, ( (Integer) tab.getValor( i, 1 ) ).intValue() );
				ps.setString( 4, (String) tab.getValor( i, 3 ) );
				rs = ps.executeQuery();
				if ( rs.next() )
					fSldLote = rs.getFloat( "SLDLOTE" );
				if ( ( fSldLote < ( Funcoes.strCurrencyToBigDecimal( (String) tab.getValor( i, 5 ) ).floatValue() ) ) && ( ! ( (String) tab.getValor( i, 3 ) ).equals( "" ) ) ) {
					iSldNeg++;
					sSaida += "\nProduto: " + tab.getValor( i, 1 ) + Funcoes.replicate( " ", 20 ) + "Lote: " + tab.getValor( i, 3 );
				}
				rs.close();
				ps.close();
			}

			if ( iSldNeg > 0 ) {
				iTemp = Funcoes.mensagemConfirma( this, "Estes lotes possuem saldo menor que a quantidade solicitada." + sSaida + "\n\nDeseja gerar RMA com lote sem saldo?" );
				if ( iTemp == JOptionPane.NO_OPTION )
					bRet = false;
				else if ( iTemp == JOptionPane.YES_OPTION )
					bRet = true;
			}
			else
				bRet = true;
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro ao verificar quantidade de Lote\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao verificar quantidade de Lote\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		} finally {
			rs = null;
			ps = null;
			sSQL = null;
			iSldNeg = 0;
			fSldLote = 0;
			iTemp = 0;
		}
		return bRet;
	}

	private boolean liberaRMA() {

		boolean retorno = true;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sUsaLote = null;
		String sSQL = null;
		String codLote = null;
		Integer codProd = null;
		try {
			for ( int i = 0; i < lcDet.getTab().getRowCount(); i++ ) {
				codProd = (Integer) lcDet.getTab().getValor( i, 1 );
				codLote = "" + lcDet.getTab().getValor( i, 3 );

				sSQL = "SELECT CLOTEPROD FROM EQPRODUTO WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=?";

				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
				ps.setInt( 3, codProd.intValue() );
				rs = ps.executeQuery();
				if ( rs.next() )
					sUsaLote = rs.getString( 1 );

				if ( sUsaLote.equals( "S" ) ) {
					if ( !FOP.existeLote( con, codProd.intValue(), codLote ) ) {
						retorno = false;
						break;
					}
				}

				rs.close();
				ps.close();
			}
		} 
		catch ( SQLException ex ) {
			Funcoes.mensagemErro( this, "Erro ao verificar condi��es para RMA\n" + ex.getMessage() );
			ex.printStackTrace();
		} 
		catch ( Exception ex ) {
			ex.printStackTrace();
		} 
		finally {
			codProd = null;
			sUsaLote = null;
			sSQL = null;
			codLote = null;
			ps = null;
			rs = null;
		}
		return retorno;
	}

	private ResultSet itensRma() {
		StringBuffer sql = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			sql.append( "SELECT GERARMA FROM PPITOP WHERE CODEMP=? AND CODFILIAL=? AND CODOP=? AND SEQOP=? AND GERARMA='S'" );
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPITOP" ) );
			ps.setInt( 3, txtCodOP.getVlrInteger().intValue() );
			ps.setInt( 4, txtSeqOP.getVlrInteger().intValue() );
			
			rs = ps.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	private boolean faltaRma() {
		boolean ret = true;
		
		try {
			ret = ((ResultSet) itensRma()).getFetchSize()>0;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public void geraRMA() {

		String sSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		try {
			
			/*
			sSQL = "SELECT GERARMA FROM PPITOP WHERE CODEMP=? AND CODFILIAL=? AND CODOP=? AND SEQOP=? AND GERARMA='S'";
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPITOP" ) );
			ps.setInt( 3, txtCodOP.getVlrInteger().intValue() );
			ps.setInt( 4, txtSeqOP.getVlrInteger().intValue() );
			rs = ps.executeQuery();*/
			
			rs = itensRma();
						
			if ( rs.next() ) {
				try {
					if ( temSldLote() ) {
						if ( Funcoes.mensagemConfirma( this, "Confirma a gera��o de RMA para a OP:" + txtCodOP.getVlrString() + " SEQ:" + txtSeqOP.getVlrString() + "?" ) == JOptionPane.YES_OPTION ) {
							ps2 = con.prepareStatement( "EXECUTE PROCEDURE EQGERARMASP(?,?,?,?)" );
							ps2.setInt( 1, Aplicativo.iCodEmp );
							ps2.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
							ps2.setInt( 3, txtCodOP.getVlrInteger().intValue() );
							ps2.setInt( 4, txtSeqOP.getVlrInteger().intValue() );
							ps2.execute();
							ps2.close();
							if ( !con.getAutoCommit() )
								con.commit();

							try {
								ps3 = con.prepareStatement( "SELECT CODRMA FROM EQRMA WHERE CODEMP=? AND CODFILIAL=? AND " + "CODEMPOF=CODEMP AND CODFILIALOF=? AND CODOP=? AND SEQOP=?" );
								ps3.setInt( 1, Aplicativo.iCodEmp );
								ps3.setInt( 2, ListaCampos.getMasterFilial( "PPITOP" ) );
								ps3.setInt( 3, ListaCampos.getMasterFilial( "PPOP" ) );
								ps3.setInt( 4, txtCodOP.getVlrInteger().intValue() );
								ps3.setInt( 5, txtSeqOP.getVlrInteger().intValue() );

								rs2 = ps3.executeQuery();
								String sRma = "";
								while ( rs2.next() ) {
									sRma += rs2.getString( 1 ) + " - ";
								}
								if ( sRma.length() > 0 )
									Funcoes.mensagemInforma( this, "Foram geradas as seguintes RMA:\n" + sRma );

								rs2.close();
							} catch ( Exception err ) {
								Funcoes.mensagemErro( this, "Erro ao buscar RMA criada", true, con, err );
								err.printStackTrace();
							}
						}
					}
				} catch ( Exception err ) {
					Funcoes.mensagemErro( this, "Erro ao criar RMA", true, con, err );
					err.printStackTrace();
				}
			}
			else {
				Funcoes.mensagemInforma( this, "N�o h� itens para gerar RMA.\n " + "Os itens n�o geram RMA automaticamente\n" + "ou o processo de gera��o de RMA j� foi efetuado." );
			}
			rs.close();
			ps.close();
			if ( !con.getAutoCommit() )
				con.commit();
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao consultar RMA", true, con, err );
			err.printStackTrace();
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
			rs2 = null;
			ps2 = null;
			ps3 = null;
		}
	}

	public void ratearItem( boolean bPergunta ) {

		boolean bResposta = true;
		BigDecimal bdVlrNova = null;
		BigDecimal bdQtdDigitada = null;
		String sCodLote = null;
		try {
			if ( bPergunta ) {
				bResposta = ( Funcoes.mensagemConfirma( Aplicativo.framePrinc, "Deseja realmente ratear este item para a OP?" ) == JOptionPane.YES_OPTION );
			}

			if ( bResposta ) {
				try {

					FFDialogo diag = new FFDialogo( this );
					diag.setTitulo( "Rateio" );
					diag.setAtribos( 250, 160 );
					diag.adic( new JLabelPad( "Quantidade: ", SwingConstants.RIGHT ), 7, 10, 80, 20 );
					diag.adic( txtQtdDigRat, 90, 10, 120, 20 );
					diag.adic( new JLabelPad( "Lote: ", SwingConstants.RIGHT ), 7, 40, 80, 20 );
					diag.adic( txtLoteRat, 90, 40, 120, 20 );
					diag.setVisible( true );

					if ( diag.OK ) {
						if ( ! ( "" + txtQtdDigRat.getVlrBigDecimal() ).equals( "" ) && !txtLoteRat.getVlrString().equals( "" ) ) {
							bdQtdDigitada = txtQtdDigRat.getVlrBigDecimal();
							sCodLote = txtLoteRat.getVlrString();

							bdVlrNova = txtQtdItOp.getVlrBigDecimal().subtract( bdQtdDigitada );

							if ( ( bdVlrNova.compareTo( txtQtdItOp.getVlrBigDecimal() ) > 0 ) || ( bdVlrNova.compareTo( new BigDecimal( 0 ) ) <= 0 ) ) {
								Funcoes.mensagemErro( Aplicativo.framePrinc, "Quantidade inv�lida!" );
								ratearItem( false );
							}

							txtQtdCopiaItOp.setVlrBigDecimal( bdQtdDigitada );
							txtQtdItOp.setVlrBigDecimal( bdVlrNova );
							txtCodLoteProdRat.setVlrString( sCodLote );
							lcDet.edit();
							lcDet.post();
							lcCampos.carregaDados();
						}
					}

					txtQtdDigRat.setVlrString( "" );
					txtLoteRat.setVlrString( "" );
				} 
				catch ( Exception err ) {
					Funcoes.mensagemErro( Aplicativo.framePrinc, "Valor inv�lido!" );
					err.printStackTrace();
					return;
				}
			}
		} catch ( Exception err ) {
			Funcoes.mensagemErro( Aplicativo.framePrinc, "Erro no rateamento!" );
			err.printStackTrace();
			return;
		} finally {
			bdVlrNova = null;
			bdQtdDigitada = null;
			sCodLote = null;
		}
	}

	public static boolean existeLote( Connection cn, int iCodProd, String sCodLote ) {

		boolean bRet = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CODLOTE FROM EQLOTE WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=? AND CODLOTE=?";
		try {
			ps = cn.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQLOTE" ) );
			ps.setInt( 3, iCodProd );
			ps.setString( 4, sCodLote );
			rs = ps.executeQuery();
			if ( rs.next() )
				bRet = true;

			rs.close();
			ps.close();
			if ( !cn.getAutoCommit() )
				cn.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( null, "Erro ao buscar existencia do lote!\n", true, cn, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return bRet;
	}

	public static Object[] gravaLote( Connection cn, boolean bInsere, String sCodModLote, String sUsaLoteEst, String sModLote, int iCodProd, Date dtFabProd, int iNroDiasValid, String sCodLote ) {

		Object[] retorno = null;
		ObjetoModLote objMl = null;
		try {
			if ( ! ( sCodModLote.equals( "" ) ) ) {
				if ( sCodLote == null ) {
					objMl = new ObjetoModLote();
					objMl.setTexto( sModLote );
					sCodLote = objMl.getLote( new Integer( iCodProd ), null, dtFabProd, cn );
				}
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime( dtFabProd );
				cal.add( GregorianCalendar.DAY_OF_YEAR, iNroDiasValid );
				Date dtVenctoLote = cal.getTime();
				retorno = new Object[ 3 ];
				retorno[ 0 ] = sCodLote;
				retorno[ 1 ] = dtVenctoLote;
				retorno[ 2 ] = new Boolean( false );
				if ( ( !existeLote( cn, iCodProd, sCodLote ) ) && ( bInsere ) ) {
					if ( Funcoes.mensagemConfirma( null, "Deseja criar o lote " + sCodLote.trim() + " ?" ) == JOptionPane.YES_OPTION ) {
						PreparedStatement ps = null;
						String sSql = "INSERT INTO EQLOTE (CODEMP,CODFILIAL,CODPROD,CODLOTE,DINILOTE,VENCTOLOTE) VALUES(?,?,?,?,?,?)";
						try {
							ps = cn.prepareStatement( sSql );
							ps.setInt( 1, Aplicativo.iCodEmp );
							ps.setInt( 2, ListaCampos.getMasterFilial( "EQLOTE" ) );
							ps.setInt( 3, iCodProd );
							ps.setString( 4, sCodLote );
							ps.setDate( 5, Funcoes.dateToSQLDate( dtFabProd ) );
							ps.setDate( 6, Funcoes.dateToSQLDate( dtVenctoLote ) );
							if ( ps.executeUpdate() == 0 )
								Funcoes.mensagemInforma( null, "N�o foi poss�vel inserir registro na tabela de Lotes!" );

							if ( !cn.getAutoCommit() )
								cn.commit();
							retorno[ 2 ] = new Boolean( true );
						} catch ( SQLException err ) {
							Funcoes.mensagemErro( null, "Erro ao inserir registro na tabela de Lotes!\n" + err.getMessage(), true, cn, err );
						} finally {
							ps = null;
							sSql = null;
						}
					}
				}
				else if ( bInsere )
					Funcoes.mensagemInforma( null, "Lote j� cadastrado para o produto!" );
			}
		} finally {
			sCodLote = null;
			objMl = null;
		}
		return retorno;
	}

	public void gravaLote( boolean bInsere ) {

		Object[] lote = gravaLote( con, bInsere, txtCodModLote.getVlrString(), txtUsaLoteEst.getVlrString(), txtModLote.getVlrString(), txtCodProdEst.getVlrInteger().intValue(), txtDtFabProd.getVlrDate(), txtNroDiasValid.getVlrInteger().intValue(), null );
		try {
			if ( lote != null ) {
				txtCodLoteProdEst.setVlrString( (String) lote[ 0 ] );
				txtDtValidOP.setVlrDate( (Date) lote[ 1 ] );
			}
		} finally {
			lote = null;
		}
	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSql = "SELECT CLASSOP FROM SGPREFERE5 WHERE CODEMP=? AND CODFILIAL=?";
		String sClassOP = "";
		Vector<Object> vParamOP = new Vector<Object>();
		LeiauteGR leiOP = null;
		try {
			try {
				ps = con.prepareStatement( sSql );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE5" ) );
				rs = ps.executeQuery();

				if ( rs.next() ) {
					if ( rs.getString( "CLASSOP" ) != null ) {
						sClassOP = rs.getString( "CLASSOP" ).trim();
					}
				}
				rs.close();
				ps.close();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao carregar a tabela SGPREFERE5!\n" + err.getMessage(), true, con, err );
			}
			if ( sClassOP.trim().equals( "" ) )
				Funcoes.mensagemErro( this, "N�o existe org.freedom.layout para ordem de produ��o. \n Cadastre o org.freedom.layout no documento de prefer�ncias do m�dulo de produ��o \n e tente novamente." );
			else {
				try {
					leiOP = (LeiauteGR) Class.forName( "org.freedom.layout.op." + sClassOP ).newInstance();
					leiOP.setConexao( con );
					vParamOP.clear();
					vParamOP.addElement( txtCodOP.getText() );
					vParamOP.addElement( txtSeqOP.getText() );
					leiOP.setParam( vParamOP );
					if ( bVisualizar ) {
						dl = new FPrinterJob( leiOP, this );
						dl.setVisible( true );
					}
					else
						leiOP.imprimir( true );
				} catch ( Exception err ) {
					Funcoes.mensagemInforma( this, "N�o foi poss�vel carregar o leiaute de Ordem de produ��o!\n" + err.getMessage() );
					err.printStackTrace();
				}
			}
		} finally {
			ps = null;
			rs = null;
			sSql = null;
			sClassOP = null;
			vParamOP = null;
			leiOP = null;
			System.gc();
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
		if ( evt.getSource() == btImp ){
			imprimir( false );
		}
		else if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btFinaliza ) {
			finalizaOP();
		}
		else if ( evt.getSource() == btRMA ) {
			geraRMA();
		}
		else if ( evt.getSource() == btLote ) {
			gravaLote( true );
		}
		else if ( evt.getSource() == btRatearItem ) {
			ratearItem( true );
		}
		else if ( evt.getSource() == btDistrb ) {
			distribuicao();
		}
		else if ( evt.getSource() == btDistrb ) {
			cancelaOP();
		}
	}

	private void cancelaOP() {		
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		
		try {
			
			if( Funcoes.mensagemConfirma( null, "Confirma o cancelamento da O.P.?" ) == JOptionPane.OK_OPTION ) {
			
				sql.append( "UPDATE PPOP SET SITOP='CA' " );
				sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODOP=? AND SEQOP=?" );
							
				ps = con.prepareStatement(sql.toString());
				
				ps.setInt( 1, lcCampos.getCodEmp() );
				ps.setInt( 2, lcCampos.getCodFilial() );
				ps.setInt( 3, txtCodOP.getVlrInteger().intValue() );
				ps.setInt( 4, txtSeqOP.getVlrInteger().intValue() );
				
				ps.executeUpdate();
				    
				ps.close();
			
				if(!con.getAutoCommit()) {
					con.commit();
				}
			}
		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private void distribuicao() {
		Object[] sValores = new Object[ 8 ];
		
		try {
			lcCampos.carregaDados();
			
			sValores[ 0 ] = txtCodOP.getVlrInteger();
			sValores[ 1 ] = txtSeqOP.getVlrInteger();
			sValores[ 2 ] = txtCodProdEst.getVlrInteger();
			sValores[ 3 ] = txtRefProdEst.getVlrString();
			sValores[ 4 ] = txtSeqEst.getVlrInteger();
			sValores[ 5 ] = txtDescEst.getVlrString();
			sValores[ 6 ] = txtQtdFinalProdOP.getVlrBigDecimal();
			sValores[ 7 ] = txtQtdPrevProdOP.getVlrBigDecimal();

			DLDistrib dl = new DLDistrib( con, this, (Boolean) prefere.get( "USAREFPROD" ) );
			dl.carregaCampos( sValores );
			dl.carregaTabela( txtCodOP.getVlrInteger().intValue(), txtSeqOP.getVlrInteger().intValue() );
			dl.setVisible( true );
		
			if ( dl.OK ) {
				dl.dispose();
			}
			else {
				dl.dispose();
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getSource() == txtSeqOP )
			if ( ( (JTextFieldPad) kevt.getSource() ).getVlrString().trim().equals( "" ) )
				( (JTextFieldPad) kevt.getSource() ).setVlrInteger( new Integer( 0 ) );
	}

	public void keyTyped( KeyEvent kevt ) {

	}

	public void keyReleased( KeyEvent kevt ) {

	}

	public void focusGained( FocusEvent arg0 ) {

	}

	public void focusLost( FocusEvent campo ) {

		if ( campo.getSource() == txtCodOP ) {
			if ( ( ! ( (JTextFieldPad) campo.getSource() ).getVlrString().trim().equals( "" ) ) && ( txtSeqOP.getVlrString().trim().equals( "" ) ) )
				txtSeqOP.setVlrInteger( new Integer( 0 ) );
		}
		else if ( campo.getSource() == txtQtdSugProdOP && txtQtdSugProdOP.getVlrString().trim().length() > 0 ) {
			simularOP();
		}
		else if (campo.getSource() == txtSeqEst) {
			if((Boolean) prefere.get( "USAREFPROD" )){
				lcProdEstRef.carregaDados();
			}
			else {
				lcProdEstCod.carregaDados();
			}
		}

	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( ( (JTabbedPanePad) ( cevt.getSource() ) ) == tpnAbas ) {
			if ( tpnAbas.getSelectedIndex() == 2 ) {
				if ( bBuscaRMA )
					getRma();
			}
			else if ( tpnAbas.getSelectedIndex() == 3 ) {
				if ( bBuscaOPS )
					getOPS();
			}
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcCampos ) {
			bBuscaRMA = false;
			bBuscaOPS = false;
		}
	}

	private void bloqueiaOp() {
		String sitop = null;
		boolean lote = false;
		boolean rma = false;
		
		try {
			
			sitop = txtSitOp.getVlrString();
			lote = existeLote( con, txtCodProdEst.getVlrInteger(), txtCodLoteProdEst.getVlrString() );
			rma = faltaRma();
			
			if(sitop.equals( "PE" )) {
				btLote.setEnabled( !lote );
				btRMA.setEnabled( true );
				btFinaliza.setEnabled( true );
				btDistrb.setEnabled( true );
				btCancela.setEnabled( true );
				txtCodProdEst.setAtivo( false );
				txtSeqEst.setAtivo( false ); 
			}
			else if(sitop.equals( "FN" )) {
				btLote.setEnabled( false );
				btRMA.setEnabled( rma );
				btFinaliza.setEnabled( false );
				btDistrb.setEnabled( false );
				btCancela.setEnabled( true );
				txtCodProdEst.setAtivo( false );
				txtSeqEst.setAtivo( false );
				txtQtdSugProdOP.setAtivo( false );
				txtCodLoteProdEst.setAtivo( false );
				txtDtValidOP.setAtivo( false );
				txtDtFabProd.setAtivo( false );
				txtCodAlmoxEst.setAtivo( false );
				
			}
			else if(sitop.equals( "CA" )) {
				btLote.setEnabled( false );
				btRMA.setEnabled( false );
				btFinaliza.setEnabled( false );
				btDistrb.setEnabled( false );
				btCancela.setEnabled( false );
				txtCodProdEst.setAtivo( false );
				txtSeqEst.setAtivo( false );
			}

		}
		catch (Exception e) {
			e.printStackTrace(); 
		}
		
	}
	
	public void afterCarrega( CarregaEvent cevt ) {
		try {
			if ( cevt.getListaCampos() == lcCampos ) {
				bloqueiaOp();
/*				btRMA.setEnabled( ( lcCampos.getStatus() != ListaCampos.LCS_NONE ) && ( lcCampos.getStatus() != ListaCampos.LCS_INSERT ) && liberaRMA() );
				btFinaliza.setEnabled( ( lcCampos.getStatus() != ListaCampos.LCS_NONE ) && ( lcCampos.getStatus() != ListaCampos.LCS_INSERT ) );
				btDistrb.setEnabled( ( lcCampos.getStatus() != ListaCampos.LCS_NONE ) && ( lcCampos.getStatus() != ListaCampos.LCS_INSERT ) );
	*/			
				bBuscaRMA = true;
				bBuscaOPS = true;
				tabSimu.limpa();
			}
	
			if ( ( cevt.getListaCampos() == lcProdEstCod ) || ( cevt.getListaCampos() == lcProdEstRef ) ) {
				
				carregaProduto();
				
				if ( txtQtdPrevProdOP.getVlrString().equals( "" ) ) {
					txtQtdSugProdOP.setVlrDouble( txtQtdEst.getVlrDouble() );
				}
	
				if ( ( txtCodLoteProdEst.getVlrString().equals( "" ) ) && ( txtUsaLoteEst.getVlrString().equals( "S" ) ) ) {
					txtCodLoteProdEst.setVlrString( getLote( lcProdEstCod, txtCodProdEst, false ) );
					txtDtValidOP.setAtivo( false );
	
					lcLoteProdEst.setDinWhereAdic( "CODPROD=#N AND (VENCTOLOTE >= #D)", txtCodProdEst );
					lcLoteProdEst.setDinWhereAdic( "", txtDtFabProd );
	
					lcLoteProdEst.carregaDados();
				}
				else if ( ( txtUsaLoteEst.getVlrString().equals( "N" ) ) ) {
					txtCodLoteProdEst.setAtivo( false );
					txtDtValidOP.setAtivo( true );
				}
				
			}
	
			if ( cevt.getListaCampos() == lcLoteProdEst )
				txtDtValidOP.setVlrDate( txtDescLoteProdEst.getVlrDate() );
	
			if ( cevt.getListaCampos() == lcDet ) {
				if ( txtUsaLoteDet.getVlrString().equals( "S" ) ) {
					txtCodLoteProdDet.setVlrString( getLote( lcProdDetCod, txtCodProdDet, true ) );
					txtCodLoteProdDet.setAtivo( true );
					lcLoteProdDet.carregaDados();
					btRMA.setEnabled( liberaRMA() );
				}
				else if ( ( txtUsaLoteDet.getVlrString().equals( "N" ) ) )
					txtCodLoteProdDet.setAtivo( false );
			}
	
			if ( cevt.getListaCampos() == lcModLote ) {
				if ( ! ( txtCodModLote.getVlrString().equals( "" ) ) && ( txtCodLoteProdEst.getVlrString().equals( "" ) ) ) {
					gravaLote( false );
					btLote.setEnabled( true );
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void beforeInsert( InsertEvent ievt ) {}

	public void beforePost( PostEvent pevt) {
		if(!(txtQtdFinalProdOP.getVlrBigDecimal().compareTo( new BigDecimal(0) )>0)) {
			txtQtdFinalProdOP.setVlrBigDecimal( new BigDecimal(0) );
		}
		
	}
	public void afterPost( PostEvent pevt ) {
/*		if ( lcCampos.getStatusAnt() == ListaCampos.LCS_INSERT ) {
			txtCodProdEst.setAtivo( false );
		}*/
		if ( pevt.getListaCampos() == lcCampos ) {
			if ( tpnAbas.getSelectedIndex() == 0 ) {
				tpnAbas.setSelectedIndex( 1 );
			}
		}
		else if ( pevt.getListaCampos() == lcDet ) {
			btRMA.setEnabled( liberaRMA() );
		}
	}

	public void afterInsert( InsertEvent ievt ) {
		if ( ievt.getListaCampos() == lcCampos ) {
			getTipoMov();
			txtCodTpMov.setVlrInteger( iCodTpMov );
			lcTipoMov.carregaDados();
			txtDtFabProd.setVlrDate( new Date() );
			if ( txtSeqOP.getVlrString().trim().equals( "" ) ) {
				txtSeqOP.setVlrInteger( new Integer( 0 ) );
			}
		}
	}

	public void beforeCancel( CancelEvent cevt ) {
//		txtCodProdEst.setAtivo( false );
	}

	public void afterCancel( CancelEvent cevt ) {

	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> getPrefere( Connection con ) { 
		HashMap<String, Object> retorno = new HashMap<String,Object>();		
		boolean[] bRetorno = new boolean[ 1 ];
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			sql.append("SELECT P1.USAREFPROD FROM SGPREFERE1 P1,SGPREFERE5 P5 ");
			sql.append("WHERE P1.CODEMP=? AND P1.CODFILIAL=? ");
			sql.append("AND P5.CODEMP=? AND P5.CODFILIAL=?");
			
			bRetorno[ 0 ] = false;
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "SGPREFERE5" ) );

			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				retorno.put("USAREFPROD", new Boolean(rs.getString( "USAREFPROD" ).trim().equals( "S" )));				
			}
			
			rs.close();
			ps.close();
			if ( !con.getAutoCommit() )
				con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} 
		finally {
			ps = null;
			rs = null;
			sql = null;
		}
		return retorno;
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		prefere = getPrefere( cn );
		montaTela();
		lcProdEstCod.setConexao( cn );
		lcProdEstRef.setConexao( cn );
		lcProdDetCod.setConexao( cn );
		lcProdDetRef.setConexao( cn );
		lcTipoMov.setConexao( cn );
		lcLoteProdDet.setConexao( cn );
		lcLoteProdRat.setConexao( cn );
		lcLoteProdEst.setConexao( cn );
		lcAlmoxEst.setConexao( cn );
		lcModLote.setConexao( cn );
		lcCampos.carregaDados();
		
	}
}
