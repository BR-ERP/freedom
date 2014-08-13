/**
 * @version 01/09/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.pcp <BR>
 *         Classe:
 * @(#)FEstrutura.java <BR>
 * 
 *                     Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                     na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                     Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                     Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                     Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Tela para cadastro de estruturas de produtos.
 * 
 */

package org.freedom.modulos.pcp.view.frame.crud.detail;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
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
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.type.TYPE_PRINT;
import org.freedom.modulos.gms.business.object.TipoProd;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.pcp.dao.DAOEstrutura;
import org.freedom.modulos.pcp.view.dialog.report.DLREstrutura;
import org.freedom.modulos.pcp.view.dialog.utility.DLSelProduto;
import org.freedom.modulos.pcp.view.frame.crud.plain.FFase;
import org.freedom.modulos.pcp.view.frame.crud.plain.FModLote;
import org.freedom.modulos.pcp.view.frame.crud.plain.FTipoAnalise;
import org.freedom.modulos.pcp.view.frame.crud.plain.FTipoRec;
import org.freedom.modulos.std.view.frame.crud.plain.FUnidade;

public class FEstrutura extends FDetalhe implements ChangeListener, ActionListener, CarregaListener, InsertListener , PostListener {

	private static final long serialVersionUID = 1L;

	private int casasDec = Aplicativo.casasDec;

	//private JPanelPad pnAdicionalCab = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pinCab = new JPanelPad( new BorderLayout() );

	private JPanelPad pinCabCampos = new JPanelPad();

	private JPanelPad pinCabObservacao = new JPanelPad( new GridLayout( 1, 1 ) );

	private JPanelPad pinDetFases = new JPanelPad( new GridLayout( 2, 1 ) );

	private JPanelPad pinDetFasesCampos = new JPanelPad();

	private JPanelPad pinDetFasesInstrucao = new JPanelPad( new GridLayout( 1, 1 ) );

	private JPanelPad pinDetItens = new JPanelPad( 590, 110 );
	
	private JPanelPad pinSubProd = new JPanelPad( 590, 110 );

	private JPanelPad pinDetEstrAnalise = new JPanelPad( 590, 110 );

	private JPanelPad pinDetDistrib = new JPanelPad();
	
	private JPanelPad pinCabGeral = new JPanelPad();
	
	private JPanelPad pinCabConf = new JPanelPad();

	private JPanelPad pinCabFichaTecnica = new JPanelPad();
	
	private JPanelPad pinCabDesccompl = new JPanelPad();

	private JPanelPad pinCabFinalidadeuso = new JPanelPad();

	private JPanelPad pinCabModousar = new JPanelPad();

	private JPanelPad pinCabMecanismoacao = new JPanelPad();

	private JTabbedPanePad tpnCab = new JTabbedPanePad();
	
	private JTabbedPanePad tpnAbas = new JTabbedPanePad();
	
	private String abaProd = "N";
	
	private String abaConf = "N";

	private JTextFieldPad txtCodProdEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdEst = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescEstDistrib = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtCLoteProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtSerieProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodFase = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescFase = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtTipoFase = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtDescEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtQtdEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSeqItem = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSeqItemSP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtSeqDistrib = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodProdItem = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodProdItemSP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtRefProdItem = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtRefProdItemSP = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JTextFieldFK txtCodUnidItemSP = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldFK txtDescProdItem = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtCodUnidItem = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtDescProdItemSP = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodProdDistrib = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtVlrMin = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private JTextFieldPad txtVlrMax = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private JTextFieldPad txtEspecificacao = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtQtdItEst = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 9 );
	
	private JTextFieldPad txtQtdItEstSP = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, casasDec );

	private JTextFieldPad txtRMA = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtRefProdEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodModLote = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNroDiasValid = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtSeqEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtSeqEstDistrib = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
    /*MANIPULACAO VARCHAR(100),    SEGUIMENTO VARCHAR(100),     CATEGORIA VARCHAR(100),     ANVISA VARCHAR(300),     MINISTERIOAGRI VARCHAR(300),
    PRINCIPIOATIVO VARCHAR(300),     DESCCOMPL VARCHAR(1000),
    FINALIDADEUSO VARCHAR(1000),
    MODOUSAR VARCHAR(1000),
    RESTRICAOUSO VARCHAR(1000),
    COMPOSICAO VARCHAR(1000),
    MECANISMOACAO VARCHAR(1000),
    CARACTERISTICA01 VARCHAR(100),
    CARACTERISTICA02 VARCHAR(100),
    CARACTERISTICA03 VARCHAR(100),
*/
	
	private JTextFieldPad txtManipulacao = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private JTextFieldPad txtSeguimento = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCategoria = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextAreaPad txaAnvisa = new JTextAreaPad( 300 );
	
	private JScrollPane spnAnvisa = new JScrollPane( txaAnvisa );

	private JTextAreaPad txaMinisterioagri = new JTextAreaPad( 300 );
	
	private JScrollPane spnMinisterioagri = new JScrollPane( txaMinisterioagri );

	private JTextAreaPad txaPrincipioativo = new JTextAreaPad( 300 );
	
	private JScrollPane spnPrincipioativo = new JScrollPane( txaPrincipioativo );
	
	private JTextAreaPad txaDesccompl = new JTextAreaPad( 1000 );
	
	private JScrollPane spnDesccompl = new JScrollPane( txaDesccompl );

	private JTextAreaPad txaFinalidadeuso = new JTextAreaPad( 1000 );
	
	private JScrollPane spnFinalidadeuso = new JScrollPane( txaFinalidadeuso );

	private JTextAreaPad txaModousar = new JTextAreaPad( 1000 );
	
	private JScrollPane spnModousar = new JScrollPane( txaModousar );

	private JTextAreaPad txaMecanismoacao = new JTextAreaPad( 1000 );
	
	private JScrollPane spnMecanismoacao = new JScrollPane( txaMecanismoacao );

	private JTextFieldFK txtDescModLote = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtSeqEfEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTpRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTpRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtTempoEf = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodEstAnalise = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTpAnalise = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtDescTpAnalise = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtTpExp = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtCodUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtCasasDec = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtNumeroFT = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtDtRevisaoFT = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JCheckBoxPad cbFinaliza = new JCheckBoxPad( "Finaliza", "S", "N" );

	private JCheckBoxPad cbAtiva = new JCheckBoxPad( "Ativa", "S", "N" );

	private JCheckBoxPad cbGLoteOPP = new JCheckBoxPad( "Mod.lote da OP principal", "S", "N" );

	private JCheckBoxPad cbOpDensidade = new JCheckBoxPad( "Usa densidade na OP", "S", "N" );
	
	private JCheckBoxPad cbEstDinamica = new JCheckBoxPad( "Estrutura din�mica", "S", "N" );
	
	private JCheckBoxPad cbDespAuto = new JCheckBoxPad( "Lan�ar desperd�cio autom�tico ?", "S", "N" );
	
	private JCheckBoxPad cbBloqQtdProd = new JCheckBoxPad( "Bloquear produ��o maior que consumo ?", "S", "N" );

	private JCheckBoxPad cbRmaAutoItEst = new JCheckBoxPad( "Rma", "S", "N" );

	private JCheckBoxPad cbCProva = new JCheckBoxPad( "Contra prova?", "S", "N" );

	private JCheckBoxPad cbQtdVariavelItem = new JCheckBoxPad( "Qtd. vari�vel?", "S", "N" );

	private JCheckBoxPad cbQtdVariavelItemSP = new JCheckBoxPad( "Qtd. vari�vel?", "S", "N" );
	
	private JCheckBoxPad cbQtdFixaItem = new JCheckBoxPad( "Qtd. fixa?", "S", "N" );
	
	private JCheckBoxPad cbPermiteAjusteItEst = new JCheckBoxPad( "Permite ajuste?", "S", "N" );

	private JCheckBoxPad cbQtdFixaItemSP = new JCheckBoxPad( "Qtd. fixa?", "S", "N" );
	
	private JCheckBoxPad cbEmitCert = new JCheckBoxPad( "Certificado?", "S", "N" );
	
	private JCheckBoxPad cbExpedirRMA = new JCheckBoxPad( "Finalizar OP somente com RMA expedida?", "S", "N");

	private JCheckBoxPad cbGeraOp = new JCheckBoxPad( "Gerar nova OP em caso de desperd�cio ?", "S", "N");
	
	private JTextAreaPad txaModoPreparo = new JTextAreaPad();

	private JTextAreaPad txaObservacao = new JTextAreaPad();

	private JTablePad tabItens = new JTablePad();

	private JTablePad tabEstru = new JTablePad();

	private JTablePad tabDist = new JTablePad();
	
	private JTablePad tabSubProd = new JTablePad();

	private JTablePad tabQuali = new JTablePad();

	private JScrollPane spItens = new JScrollPane( tabItens );

	private JScrollPane spQuali = new JScrollPane( tabQuali );

	private JScrollPane spDist = new JScrollPane( tabDist );
	
	private JScrollPane spSubProd = new JScrollPane( tabSubProd );

	private JScrollPane spEstru = new JScrollPane( tabEstru );

	private JScrollPane spnModoPreparo = new JScrollPane( txaModoPreparo );

	private JScrollPane spnObservacao = new JScrollPane( txaObservacao );

	private ListaCampos lcProdEst = new ListaCampos( this, "" );

	private ListaCampos lcProdItem = new ListaCampos( this, "PD" );

	private ListaCampos lcProdItemSP = new ListaCampos( this, "PD" );
	
	private ListaCampos lcProdItemRef = new ListaCampos( this, "PD" );
	
	private ListaCampos lcProdItemRefSP = new ListaCampos( this, "PD" );

	private ListaCampos lcFase = new ListaCampos( this, "FS" );

	private ListaCampos lcModLote = new ListaCampos( this, "ML" );

	private ListaCampos lcDetItens = new ListaCampos( this );

	private ListaCampos lcDetEstrAnalise = new ListaCampos( this );

	private ListaCampos lcDetDistrib = new ListaCampos( this );

	private ListaCampos lcEstDistrib = new ListaCampos( this, "DE" );
	
	private ListaCampos lcSubProd = new ListaCampos( this, "" );

	private ListaCampos lcTipoRec = new ListaCampos( this, "TR" );

	private ListaCampos lcTpAnalise = new ListaCampos( this, "TA" );

	private ListaCampos lcUnid = new ListaCampos( this, "UD" );
	
	private HashMap<String, Object> prefere = null;
	
	private Vector<String> vTipoExternoVal = new Vector<String>();
	
	private Vector<String> vTipoExternoLab = new Vector<String>();
	
	private Vector<String> vBloqQtdVal = new Vector<String>();
	
	private Vector<String> vBloqQtdLab = new Vector<String>();
	
	private JCheckBoxPad cbExternaFase = new JCheckBoxPad( "Externa", "S", "N" );
	
	private JRadioGroup<String, String> rgBloqQtdProd ;
	
	private JRadioGroup<String, String> rgTipoExterno ;
	
	private JButtonPad btCopiar = new JButtonPad( "Copiar Estrutura", Icone.novo( "btCopiarModel.png" ) );

	private DAOEstrutura daoestru = null;
	
	public JButtonPad btPrevimpFT = new JButtonPad(Icone.novo("btPrevimp.png"));

	public JButtonPad btImprimeFT = new JButtonPad(Icone.novo("btImprime.png"));

	public FEstrutura() {
		setTitulo( "Estrutura de produtos" );
		setAtribos( 380, 20, 680, 650 );
		setAltCab( 250 );

	}
	
	private void adicAbas() {

		
		//Cab
		pnCliCab.add( tpnCab );
		tpnCab.addTab( "Geral", pinCab );
		tpnCab.addTab( "Descri��o completa", pinCabDesccompl);
		tpnCab.addTab( "Finalidade de uso", pinCabFinalidadeuso);
		tpnCab.addTab( "Modo de usar", pinCabModousar);
		tpnCab.addTab( "Mecanismo de a��o", pinCabMecanismoacao);
		tpnCab.addTab( "Ficha T�cnica", pinCabFichaTecnica );
		tpnCab.addTab( "Configura��es", pinCabConf );
		
		//Detalhe
		
		pnMaster.remove( spTab );
		pnMaster.remove( pnDet );

		tpnAbas.addTab( "Fases", spTab );
		tpnAbas.addTab( "Itens X Fase", spItens );
		tpnAbas.addTab( "Controle de qualidade", spQuali );
		tpnAbas.addTab( "Distribui��o X Fase", spDist );
		tpnAbas.addTab( "Subproduto", spSubProd );

		pnMaster.add( tpnAbas, BorderLayout.CENTER );
	
	}
	
	
private void montaTela() {

		
		pnMaster.remove( spTab );
		pnMaster.remove( pnDet );

		tpnAbas.addTab( "Fases", spTab );
		tpnAbas.addTab( "Itens X Fase", spItens );
		tpnAbas.addTab( "Controle de qualidade", spQuali );
		tpnAbas.addTab( "Distribui��o X Fase", spDist );
		tpnAbas.addTab( "Subproduto", spSubProd );

		pnMaster.add( tpnAbas, BorderLayout.CENTER );

		pnGImp.removeAll();
		pnGImp.setLayout( new GridLayout( 1, 2 ) );
		pnGImp.setPreferredSize( new Dimension( 100, 26 ) );
		pnGImp.add( btPrevimp );
		pnGImp.add( btImp );

		//pnAdicionalCab.setPreferredSize( new Dimension(30,30) );
		//pnAdicionalCab.add( btPrevimpFT );
		//pnNavCab.add( pnAdicionalCab, BorderLayout.WEST );
		
		btPrevimpFT.setPreferredSize( new Dimension(26,26) );
		btImprimeFT.setPreferredSize( new Dimension(26,26) );
		getPnBtInfo().add( btPrevimpFT );
		
		cbAtiva.setVlrString( "N" );
		
		vTipoExternoLab.addElement( "Envio" );
		vTipoExternoLab.addElement( "Retorno" );
		vTipoExternoVal.addElement( "E" );
		vTipoExternoVal.addElement( "R" );
		rgTipoExterno = new JRadioGroup<String, String>( 1, 2, vTipoExternoLab, vTipoExternoVal );
		
		rgTipoExterno.setAtivo( false );

		lcDetItens.setMaster( lcDet );
		lcDet.adicDetalhe( lcDetItens );
		lcDetDistrib.setMaster( lcDet );
		lcDet.adicDetalhe( lcDetDistrib );
		lcDetEstrAnalise.setMaster( lcDet );
		lcDet.adicDetalhe( lcDetEstrAnalise );

		lcSubProd.setMaster( lcDet );
		lcDet.adicDetalhe( lcSubProd );

		// txtQtdMat.addKeyListener( this );

		pinCabCampos.setPreferredSize( new Dimension( 500, 130 ) );

		adicAbas();
		pinCab.add( pinCabCampos, BorderLayout.NORTH );
		pinCab.add( pinCabObservacao, BorderLayout.CENTER );
		pinCabCampos.setPreferredSize( new Dimension( 500, 130 ) );
		setPainel( pinCabCampos );

		// pinCabCampos = new JPanelPad( 500, 90 );
		// setPainel( pinCab, pnCliCab );

		setListaCampos( lcCampos );
		lcCampos.addPostListener( this );
		lcProdEst.setUsaME( false );
		lcProdEst.add( new GuardaCampo( txtCodProdEst, "CodProd", "C�d.prod.", ListaCampos.DB_PK, true ) );
		lcProdEst.add( new GuardaCampo( txtDescProdEst, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProdEst.add( new GuardaCampo( txtRefProdEst, "RefProd", "Referencia", ListaCampos.DB_SI, false ) );
		lcProdEst.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "Usa Lote", ListaCampos.DB_SI, false ) );
		lcProdEst.add( new GuardaCampo( txtSerieProd, "SerieProd", "Usa S�rie", ListaCampos.DB_SI, false ) );
		lcProdEst.setWhereAdic( "ATIVOPROD='S' AND TIPOPROD IN ('" + TipoProd.PRODUTO_ACABADO.getValue() + "','" + TipoProd.PRODUTO_INTERMEDIARIO.getValue() + "')" );
		//lcProdEst.setWhereAdic( "TIPOPROD='F' AND CODEMP=" + Aplicativo.iCodEmp + " AND CODFILIAL=" + Aplicativo.iCodFilial );
		lcProdEst.montaSql( false, "PRODUTO", "EQ" );
		lcProdEst.setQueryCommit( false );
		lcProdEst.setReadOnly( true );
		txtRefProdEst.setTabelaExterna( lcProdEst, FProduto.class.getCanonicalName() );
		txtCodProdEst.setTabelaExterna( lcProdEst, FProduto.class.getCanonicalName() );
		txtSeqEst.setTabelaExterna( lcProdEst, FProduto.class.getCanonicalName() );
		txtDescProdEst.setListaCampos( lcProdEst );

		lcProdItem.add( new GuardaCampo( txtCodProdItem, "CodProd", "C�d.prod.", ListaCampos.DB_PK, true ) );
		lcProdItem.add( new GuardaCampo( txtDescProdItem, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProdItem.add( new GuardaCampo( txtCodUnidItem, "CodUnid", "C�digo da unidade", ListaCampos.DB_SI, false ) );
		lcProdItem.add( new GuardaCampo( txtRefProdItem, "RefProd", "Refer�ncia", ListaCampos.DB_SI, false ) );
		lcProdItem.add( new GuardaCampo( txtRMA, "RMAProd", "RMA", ListaCampos.DB_SI, false ) );

		lcProdItem.montaSql( false, "PRODUTO", "EQ" );
		lcProdItem.setQueryCommit( false );
		lcProdItem.setReadOnly( true );
		txtCodProdItem.setTabelaExterna( lcProdItem, FProduto.class.getCanonicalName() );
		
		
		lcProdItemSP.add( new GuardaCampo( txtCodProdItemSP, "CodProd", "C�d.prod.", ListaCampos.DB_PK, true ) );
		lcProdItemSP.add( new GuardaCampo( txtDescProdItemSP, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProdItemSP.add( new GuardaCampo( txtRefProdItemSP, "RefProd", "Refer�ncia", ListaCampos.DB_SI, false ) );
		lcProdItemSP.add( new GuardaCampo( txtCodUnidItemSP, "CodUnid", "Unid.", ListaCampos.DB_SI, false ) );
		
		lcProdItemSP.setWhereAdic( "TIPOPROD='"+ TipoProd.SUB_PRODUTO.getValue()  +"' AND CODEMP=" + Aplicativo.iCodEmp + " AND CODFILIAL=" + Aplicativo.iCodFilial );

		lcProdItemSP.montaSql( false, "PRODUTO", "EQ" );
		lcProdItemSP.setQueryCommit( false );
		lcProdItemSP.setReadOnly( true );
		txtCodProdItemSP.setTabelaExterna( lcProdItemSP, FProduto.class.getCanonicalName() );

		// txtDescProdItem.setListaCampos( lcDetItens );

		lcProdItemRefSP.add( new GuardaCampo( txtRefProdItemSP, "RefProd", "Refer�ncia", ListaCampos.DB_PK, false ) );
		lcProdItemRefSP.add( new GuardaCampo( txtDescProdItemSP, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProdItemRefSP.add( new GuardaCampo( txtCodProdItemSP, "CodProd", "C�d.prod.", ListaCampos.DB_SI, true ) );
		lcProdItemRefSP.add( new GuardaCampo( txtCodUnidItemSP, "CodUnid", "Unid.", ListaCampos.DB_SI, false ) );
		lcProdItemSP.setWhereAdic( "TIPOPROD='"+ TipoProd.SUB_PRODUTO.getValue()  +"' AND CODEMP=" + Aplicativo.iCodEmp + " AND CODFILIAL=" + Aplicativo.iCodFilial );

		lcProdItemRefSP.montaSql( false, "PRODUTO", "EQ" );
		lcProdItemRefSP.setQueryCommit( false );
		lcProdItemRefSP.setReadOnly( true );
		txtRefProdItemSP.setTabelaExterna( lcProdItemRefSP, FProduto.class.getCanonicalName() );
		// txtRefProdItem.setListaCampos( lcDetItens );
		
		
		lcProdItemRef.add( new GuardaCampo( txtRefProdItem, "RefProd", "Refer�ncia", ListaCampos.DB_PK, false ) );
		lcProdItemRef.add( new GuardaCampo( txtDescProdItem, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProdItemRef.add( new GuardaCampo( txtCodProdItem, "CodProd", "C�d.prod.", ListaCampos.DB_SI, true ) );
		lcProdItemRef.add( new GuardaCampo( txtRMA, "RMAProd", "RMA", ListaCampos.DB_SI, false ) );

		lcProdItemRef.montaSql( false, "PRODUTO", "EQ" );
		lcProdItemRef.setQueryCommit( false );
		lcProdItemRef.setReadOnly( true );
		txtRefProdItem.setTabelaExterna( lcProdItemRef, FProduto.class.getCanonicalName() );

		lcFase.add( new GuardaCampo( txtCodFase, "CodFase", "C�d.fase", ListaCampos.DB_PK, true ) );
		lcFase.add( new GuardaCampo( txtDescFase, "DescFase", "Descri��o da fase", ListaCampos.DB_SI, false ) );
		lcFase.add( new GuardaCampo( txtTipoFase, "TipoFase", "Tipo da fase", ListaCampos.DB_SI, false ) );
		lcFase.add( new GuardaCampo( cbExternaFase, "ExternaFase", "Externa", ListaCampos.DB_SI, false));
		lcFase.montaSql( false, "FASE", "PP" );
		lcFase.setQueryCommit( false );
		lcFase.setReadOnly( true );
		txtCodFase.setTabelaExterna( lcFase, FFase.class.getCanonicalName() );
		txtCodFase.setNomeCampo( "codfase" );
		txtDescFase.setListaCampos( lcFase );

		lcModLote.add( new GuardaCampo( txtCodModLote, "CodModLote", "C�d.Mod.Lote", ListaCampos.DB_PK, false ) );
		lcModLote.add( new GuardaCampo( txtDescModLote, "DescModLote", "Descri��o do modelo de lote", ListaCampos.DB_SI, false ) );
		lcModLote.montaSql( false, "MODLOTE", "EQ" );
		lcModLote.setQueryCommit( false );
		lcModLote.setReadOnly( true );
		txtCodModLote.setTabelaExterna( lcModLote, FModLote.class.getCanonicalName() );
		txtDescModLote.setListaCampos( lcModLote );

		adicCampo( txtCodProdEst, 7, 20, 80, 20, "CodProd", "C�d.prod.", ListaCampos.DB_PF, txtDescProdEst, true );
		adicDescFK( txtDescProdEst, 90, 20, 297, 20, "DescProd", "Descri��o do produto" );
		adicCampo( txtSeqEst, 390, 20, 85, 20, "SeqEst", "Seq.Est.", ListaCampos.DB_PF, true );// era pra ser DB_PF, mas ta dando erro.
		adicCampo( txtQtdEst, 7, 60, 80, 20, "QtdEst", "Quantidade", ListaCampos.DB_SI, true );
		adicCampo( txtDescEst, 90, 60, 297, 20, "DescEst", "Descri��o", ListaCampos.DB_SI, true );
		adicCampoInvisivel( txtRefProdEst, "RefProd", "Ref.prod.", ListaCampos.DB_SI, false );
		adicDB( cbAtiva, 485, 20, 80, 20, "ATIVOEST", "", true );
		adicDB( cbGLoteOPP, 485, 40, 160, 20, "GLOTEOPP", "", true );
		adicCampo( txtCodModLote, 7, 100, 80, 20, "CodModLote", "C�d.Mod.Lote", ListaCampos.DB_FK, txtDescModLote, false );
		adicDescFK( txtDescModLote, 90, 100, 297, 20, "DescModLote", "Descri��o do modelo do lote" );
		adicCampo( txtNroDiasValid, 390, 60, 85, 20, "NroDiasValid", "Dias de valid.", ListaCampos.DB_SI, false );
		adicDB( cbOpDensidade, 485, 60, 250, 20, "USADENSIDADEOP", "", true );
		adicDB( cbEstDinamica, 485, 80, 250, 20, "ESTDINAMICA", "", true );
		adic(btCopiar, 450, 105, 180, 20, "", true );
		
		
		setPainel( pinCabObservacao );
		GridLayout go = (GridLayout) pinCabObservacao.getLayout();
		go.setHgap( 10 );
		go.setVgap( 10 );
		pinCabObservacao.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Observa��o" ) );
		adicDBLiv( txaObservacao, "Observacao", "Observa��o", false );
		pinCabObservacao.add( spnObservacao );
		
		setPainel( pinCabDesccompl);
		adicDB(txaDesccompl, 7, 20, 500, 130, "Desccompl", "Descri��o", false );
		
		setPainel( pinCabFinalidadeuso);
		adicDB(txaFinalidadeuso, 7, 20, 500, 130, "Finalidadeuso", "Finalidade de uso", false );

		setPainel( pinCabModousar);
		adicDB(txaModousar, 7, 20, 500, 130, "Modousar", "Modo de usar", false );

		setPainel( pinCabMecanismoacao);
		adicDB(txaMecanismoacao, 7, 20, 500, 130, "Mecanismoacao", "Mecanismo de a��o", false );

		setPainel( pinCabFichaTecnica );
		adicCampo( txtNumeroFT, 7, 20, 80, 20, "NumeroFT", "F.T. Nro.", ListaCampos.DB_SI, false );
		adicCampo( txtDtRevisaoFT, 90, 20, 80, 20, "DtRevisaoFT", "Dt.revis�o", ListaCampos.DB_SI, false );

		setPainel( pinCabConf );
		
		vBloqQtdLab.addElement( "Sim" );
		vBloqQtdLab.addElement( "N�o" );
		vBloqQtdVal.addElement( "S" );
		vBloqQtdVal.addElement( "N" );
		rgBloqQtdProd = new JRadioGroup<String, String>( 1, 2, vBloqQtdLab, vBloqQtdVal );
		rgBloqQtdProd.setVlrString( "N" );
		
		adicDB( cbDespAuto, 7, 10, 250, 20, "DESPAUTO", "", true );
		adicDB( cbExpedirRMA, 7, 30, 400, 20, "EXPEDIRRMA", "", true );
		adicDB( cbGeraOp, 7, 50, 270, 20, "GerarOp", "", true );
		adicDB( rgBloqQtdProd, 7, 95, 230, 30, "BLOQQTDPROD", "Bloquear produ��o maior que consumo", true );

		setListaCampos( false, "ESTRUTURA", "PP" );
		lcCampos.setQueryInsert( false );

		// Detalhe Fases

		lcTipoRec.add( new GuardaCampo( txtCodTpRec, "CodTpRec", "C�d.tp.rec.", ListaCampos.DB_PK, true ) );
		lcTipoRec.add( new GuardaCampo( txtDescTpRec, "DescTpRec", "Descri��o do tipo de recurso", ListaCampos.DB_SI, false ) );
		lcTipoRec.montaSql( false, "TIPOREC", "PP" );
		lcTipoRec.setQueryCommit( false );
		lcTipoRec.setReadOnly( true );
		txtCodTpRec.setTabelaExterna( lcTipoRec, FTipoRec.class.getCanonicalName() );

		setPainel( pinDetFases, pnDet );
		pinDetFases.add( pinDetFasesCampos );
		pinDetFases.add( pinDetFasesInstrucao );
		setPainel( pinDetFasesCampos );
		setListaCampos( lcDet );

		adicCampo( txtSeqEfEst, 7, 20, 40, 20, "SeqEf", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtCodFase, 50, 20, 77, 20, "CodFase", "C�d.fase", ListaCampos.DB_PF, txtDescFase, true );
		adicDescFK( txtDescFase, 130, 20, 310, 20, "DescFase", "Descri��o da fase" );

		adicCampo( txtTempoEf, 450, 20, 80, 20, "TempoEf", "Tempo(Seg)", ListaCampos.DB_SI, true );
		adicCampo( txtCodTpRec, 7, 60, 80, 20, "CodTpRec", "C�d.tp.rec.", ListaCampos.DB_FK, txtDescTpRec, true );
		adicDescFK( txtDescTpRec, 90, 60, 350, 20, "DescTpRec", "Desc. tipo de recurso" );
		adicDescFKInvisivel( txtTipoFase, "TipoFase", "Tipo da fase" );

		adicDB( cbFinaliza, 533, 20, 80, 20, "FINALIZAOP", "", true );

		setPainel( pinDetFasesInstrucao );
		GridLayout gi = (GridLayout) pinDetFasesInstrucao.getLayout();
		gi.setHgap( 10 );
		gi.setVgap( 10 );

		pinDetFasesInstrucao.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Instru��es" ) );
		adicDBLiv( txaModoPreparo, "Instrucoes", "Instru��es", false );
		pinDetFasesInstrucao.add( spnModoPreparo );
		
		setListaCampos( true, "ESTRUFASE", "PP" );
		lcDet.setQueryInsert( false );

		// Fim do detalhe fases

		// Detalhe Itens

		setPainel( pinDetItens );
		setListaCampos( lcDetItens );
		setNavegador( navRod );

		adicCampo( txtSeqItem, 7, 20, 40, 20, "SeqItEst", "Item", ListaCampos.DB_PK, true );

		if ( comRef() ) {
			adicCampoInvisivel( txtRefProdItem, "RefProdPD", "Refer�ncia", ListaCampos.DB_FK, txtDescProdItem, true );
			adicCampoInvisivel( txtCodProdItem, "CodProdPD", "C�d.Prod.", ListaCampos.DB_SI, true );

			adic( new JLabelPad( "Refer�ncia" ), 50, 0, 115, 20 );
			adic( txtRefProdItem, 50, 20, 115, 20 );
			txtRefProdItem.setRequerido( true );
			txtRefProdItem.setPK( true );
			txtRefProdItem.setFK( true );
			txtRefProdItem.setNomeCampo( "refprod" );
		}
		else {
			adicCampo( txtCodProdItem, 50, 20, 115, 20, "CodProdPD", "C�d.prod.", ListaCampos.DB_FK, txtDescProdItem, false );
			adicCampoInvisivel( txtRefProdItem, "RefProdPD", "Refer�ncia", ListaCampos.DB_SI, true );
			txtCodProdItem.setNomeCampo( "codprod" );
		}

		adicDescFK( txtDescProdItem, 168, 20, 290, 20, "DescProd", "Descri��o do produto" );
		adicDescFK( txtCodUnidItem, 461, 20, 30, 20, "CodUnid", "Unid." );
		adicCampo( txtQtdItEst, 498, 20, 140, 20, "QtdItEst", "Qtd.", ListaCampos.DB_SI, true );

		adicDB( cbRmaAutoItEst, 		10, 	50, 	150, 	20, "RmaAutoItEst", "", true );
		adicDB( cbCProva, 				160, 	50, 	150, 	20, "CPROVA", "", true );
		adicDB( cbQtdVariavelItem, 		10, 	70, 	150, 	20, "QtdVariavel", "", true );
		adicDB( cbQtdFixaItem, 			160, 	70, 	150, 	20, "QtdFixa", "", true );
		adicDB( cbPermiteAjusteItEst, 	310, 	50, 	150, 	20, "PermiteAjusteItEst", "", true );
		adicDB( rgTipoExterno, 			460, 	65, 	180, 	30, "TipoExterno", "Tipo externo", false );

		setListaCampos( true, "ITESTRUTURA", "PP" );
		lcDetItens.setQueryInsert( false );
		lcDetItens.setTabela( tabItens );

		// Fim Detalhe Itens

		// Detalhe Distribui��o

		lcEstDistrib.add( new GuardaCampo( txtCodProdDistrib, "Codprod", "C�d.prod.", ListaCampos.DB_PK, txtDescEstDistrib, true ) );
		lcEstDistrib.add( new GuardaCampo( txtSeqEstDistrib, "seqest", "Seq.Est.", ListaCampos.DB_PK, txtDescEstDistrib, true ) );
		lcEstDistrib.add( new GuardaCampo( txtDescEstDistrib, "DescEst", "Descri��o da estrutura", ListaCampos.DB_SI, false ) );
		lcEstDistrib.setWhereAdic( "ATIVOEST='S'" );
		lcEstDistrib.montaSql( false, "ESTRUTURA", "PP" );
		lcEstDistrib.setReadOnly( true );
		lcEstDistrib.setQueryCommit( false );
		txtCodProdDistrib.setListaCampos( lcEstDistrib );
		txtCodProdDistrib.setTabelaExterna( lcEstDistrib, FEstrutura.class.getCanonicalName() );
		txtCodProdDistrib.setNomeCampo( "Codprod" );
		txtCodProdDistrib.setFK( true );
		txtSeqEstDistrib.setListaCampos( lcEstDistrib );
		txtSeqEstDistrib.setTabelaExterna( lcEstDistrib, FEstrutura.class.getCanonicalName() );
		txtSeqEstDistrib.setNomeCampo( "seqest" );
		txtSeqEstDistrib.setFK( true );
		txtDescEstDistrib.setListaCampos( lcEstDistrib );

		setPainel( pinDetDistrib );
		setListaCampos( lcDetDistrib );

		adicCampo( txtSeqDistrib, 7, 20, 60, 20, "seqde", "Seq.", ListaCampos.DB_PK, true );
		adicCampo( txtCodProdDistrib, 70, 20, 77, 20, "CodProdDe", "C�d.prod.", ListaCampos.DB_FK, true );
		adicCampo( txtSeqEstDistrib, 150, 20, 77, 20, "SeqEstDe", "Seq.Est", ListaCampos.DB_FK, txtDescEstDistrib, true );
		// adicDB( cbQtdVariavelDistrib, 7, 60, 70, 20, "QtdVariavel", "Qtd.vari�vel", true );
		adicDescFK( txtDescEstDistrib, 230, 20, 277, 20, "DescEst", "Descri��o da estrutura" );
		setListaCampos( true, "DISTRIB", "PP" );
		lcDetDistrib.setQueryInsert( false );
		lcDetDistrib.setTabela( tabDist );
		lcDetDistrib.montaTab();

		// Fim Detalhe Distribui��o
		
		setAltDet( 210 );
		setPainel( pinDetFases, pnDet );
		setListaCampos( lcDet );
		lcDet.montaTab();
		lcDetItens.montaTab();

		// Controle de Qualidade

		setPainel( pinDetEstrAnalise );
		setListaCampos( lcDetEstrAnalise );
		setNavegador( navRod );

		lcTpAnalise.add( new GuardaCampo( txtCodTpAnalise, "CodTpAnalise", "C�d.Tp.An�lise", ListaCampos.DB_PK, null, false ) );
		lcTpAnalise.add( new GuardaCampo( txtDescTpAnalise, "DescTpAnalise", "Descri��o da An�lise", ListaCampos.DB_SI, false ) );
		lcTpAnalise.add( new GuardaCampo( txtTpExp, "TipoExpec", "Tipo expecifica��o", ListaCampos.DB_SI, false ) );
		lcTpAnalise.add( new GuardaCampo( txtCodUnid, "CodUnid", "C�d.Unid.", ListaCampos.DB_FK, txtCasasDec, false ) );

		lcTpAnalise.montaSql( false, "TIPOANALISE", "PP" );
		lcTpAnalise.setReadOnly( true );
		lcTpAnalise.setQueryCommit( false );
		txtCodTpAnalise.setListaCampos( lcTpAnalise );
		txtCodTpAnalise.setTabelaExterna( lcTpAnalise, FTipoAnalise.class.getCanonicalName() );

		lcUnid.add( new GuardaCampo( txtCodUnid, "CodUnid", "C�d.unid.", ListaCampos.DB_PK, true ) );
		lcUnid.add( new GuardaCampo( txtCasasDec, "CasasDec", "C�d.unid.", ListaCampos.DB_SI, true ) );
		lcUnid.montaSql( false, "UNIDADE", "EQ" );
		lcUnid.setReadOnly( true );
		lcUnid.setQueryCommit( false );
		txtCodUnid.setTabelaExterna( lcUnid, FUnidade.class.getCanonicalName() );

		adicCampo( txtCodEstAnalise, 7, 20, 70, 20, "CODESTANALISE", "C�d.Est.An.", ListaCampos.DB_PK, true );
		adicCampo( txtCodTpAnalise, 80, 20, 70, 20, "CodTpAnalise", "C�d.Tp.An", ListaCampos.DB_FK, txtDescTpAnalise, true );
		adicDescFK( txtDescTpAnalise, 155, 20, 300, 20, "DescTpAnalise", "Descri��o da an�lise" );

		/*
		 * adic( txtCodUnid, 458, 20, 50, 20 ); adic( txtCasasDec, 511, 20, 50, 20 );
		 */

		adicDescFKInvisivel( txtTpExp, "TipoExpec", "TipoExpec" );
		adicCampo( txtVlrMin, 7, 65, 70, 20, "VlrMin", "Vlr.Min.", ListaCampos.DB_SI, false );
		adicCampo( txtVlrMax, 80, 65, 70, 20, "VlrMax", "Vlr.M�x.", ListaCampos.DB_SI, false );
		adicCampo( txtEspecificacao, 153, 65, 300, 20, "Especificacao", "Especifica��o", ListaCampos.DB_SI, false );
		adicDB( cbEmitCert, 456, 65, 100, 20, "EmitCert", "", true );

		setListaCampos( true, "ESTRUANALISE", "PP" );
		lcDetEstrAnalise.setQueryInsert( true );

		lcDetEstrAnalise.setTabela( tabQuali );
		lcDetEstrAnalise.montaTab();

		lcDetEstrAnalise.setSQLMax( "select max(codestanalise) from ppestruanalise where codemp=? and codfilial=? " );

		// lcDetEstrAnalise.add( new GuardaCampo( txtCodProdEst, "CodProd", "C�d.prod.", ListaCampos.DB_FK, false ));

		// fim controle de qualidade
		
		
		// Detalhe Itens SubProd

		setPainel( pinSubProd );
		setListaCampos( lcSubProd );
		setNavegador( navRod );

		adicCampo( txtSeqItemSP, 7, 20, 40, 20, "SeqItEstSp", "Item", ListaCampos.DB_PK, true );

		if ( comRef() ) {
			adicCampoInvisivel( txtRefProdItemSP, "RefProdPD", "Refer�ncia", ListaCampos.DB_FK, txtDescProdItemSP, true );
			adicCampoInvisivel( txtCodProdItemSP, "CodProdPD", "C�d.Prod.", ListaCampos.DB_SI, true );

			adic( txtRefProdItemSP, 50, 20, 115, 20, "Refer�ncia" );
			
			txtRefProdItemSP.setRequerido( true );
			txtRefProdItemSP.setPK( true );
			txtRefProdItemSP.setFK( true );
			txtRefProdItemSP.setNomeCampo( "refprod" );
		}
		else {
			adicCampo( txtCodProdItemSP, 50, 20, 115, 20, "CodProdPD", "C�d.prod.", ListaCampos.DB_FK, txtDescProdItemSP, false );
			adicCampoInvisivel( txtRefProdItemSP, "RefProdPD", "Refer�ncia", ListaCampos.DB_SI, true );
			
			txtCodProdItemSP.setNomeCampo( "codprod" );
		}

		adicDescFK( txtDescProdItemSP, 168, 20, 290, 20, "DescProd", "Descri��o do produto" );
		adicDescFK( txtCodUnidItemSP, 461, 20, 30, 20, "CodUnid", "Unid." );
		adicCampo( txtQtdItEstSP, 494, 20, 120, 20, "QtdItEstSP", "Qtd.Prevista", ListaCampos.DB_SI, true );

//		adicDB( cbQtdVariavelItemSP, 210, 60, 100, 20, "QtdVariavel", "", true );
//		adicDB( cbQtdFixaItemSP, 330, 60, 100, 20, "QtdFixa", "", true );

		setListaCampos( true, "ITESTRUTURASUBPROD", "PP" );
		lcSubProd.setQueryInsert( false );
		lcSubProd.setTabela( tabSubProd );
		lcSubProd.montaTab();


		// Fim subprod

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btImprimeFT.addActionListener( this );
		btPrevimpFT.addActionListener( this );
		btCopiar.addActionListener( this );
		setImprimir( true );
		tpnAbas.addChangeListener( this );
		lcCampos.addInsertListener( this );
		lcCampos.addCarregaListener( this );
		lcDet.addCarregaListener( this );
		lcDet.addPostListener( this );
		lcProdEst.addCarregaListener( this );
		lcProdItem.addCarregaListener( this );
		lcProdItemRef.addCarregaListener( this );
		lcDetDistrib.addCarregaListener( this );
		lcFase.addCarregaListener( this );
		lcTpAnalise.addCarregaListener( this );
		lcUnid.addCarregaListener( this );
		lcDetEstrAnalise.addPostListener( this );

		tabQuali.setTamColuna( 250, 2 );

		tab.setTamColuna( 35, 0 );
		tab.setTamColuna( 50, 1 );
		tab.setTamColuna( 230, 2 );
		tab.setTamColuna( 80, 3 );
		tab.setTamColuna( 70, 4 );
		tab.setTamColuna( 0, 5 );
		tab.setTamColuna( 60, 6 );
		tab.setTamColuna( 0, 7 );

		tab.setColunaInvisivel( 5 );
		tab.setColunaInvisivel( 7 );

		// Tabela Itens X Fase

		tabItens.setTamColuna( 35, 0 );
		tabItens.setTamColuna( 60, 1 );
		tabItens.setTamColuna( 340, 2 );
		tabItens.setTamColuna( 80, 3 );
		tabItens.setTamColuna( 40, 4 );
		tabItens.setTamColuna( 75, 5 );

		// Tabela Distribuicao X Fase

		tabDist.setTamColuna( 35, 0 );
		tabDist.setTamColuna( 60, 1 );
		tabDist.setTamColuna( 50, 2 );
		tabDist.setTamColuna( 370, 3 );
		tabItens.setTamColuna( 75, 4 );

		cbRmaAutoItEst.setEnabled( false );
		setAltDet( 190 );
		navRod.setListaCampos( lcDet );
		lcDet.setNavegador( navRod );
		nav.setListaCampos( lcCampos );
		lcCampos.setNavegador( nav );

	}

	private void imprimirTexto( ResultSet rs, TYPE_PRINT visualizar, boolean resumido ) {

		ImprimeOS imp = null;
		int linPag = 0;
		imp = new ImprimeOS( "", con );
		linPag = imp.verifLinPag() - 1;
		imp.montaCab();
		imp.setTitulo( "Relat�rio de Estrutura do Produto" );
		imp.limpaPags();

		try {

			if ( resumido ) {

				while ( rs.next() ) {

					if ( imp.pRow() == 0 ) {
						imp.impCab( 136, true );
						imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
						imp.say( imp.pRow() + 1, 0, "| C�d.prod." );
						imp.say( imp.pRow() + 0, 13, "| Descri��o do produto" );
						imp.say( imp.pRow() + 0, 50, "| Seq.Est." );
						imp.say( imp.pRow() + 0, 60, "| Qtd." );
						imp.say( imp.pRow() + 0, 70, "| Descri��o" );
						imp.say( imp.pRow() + 0, 101, "| Ativo" );
						imp.say( imp.pRow() + 0, 110, "| Mod.Lote" );
						imp.say( imp.pRow() + 0, 121, "| Validade" );
						imp.say( imp.pRow() + 0, 135, "|" );
					}

					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "| " + rs.getString( "CodProd" ) );
					imp.say( imp.pRow() + 0, 13, "| " + rs.getString( "DescProd" ).substring( 0, 34 ) );
					imp.say( imp.pRow() + 0, 50, "| " + rs.getString( "SeqEst" ) );
					imp.say( imp.pRow() + 0, 60, "| " + rs.getString( "QtdEst" ) );
					imp.say( imp.pRow() + 0, 70, "| " + rs.getString( "DescEst" ).substring( 0, 29 ) );
					imp.say( imp.pRow() + 0, 100, "| " + ( rs.getString( "AtivoEst" ).equals( "S" ) ? "Sim" : "N�o" ) );
					imp.say( imp.pRow() + 0, 110, "| " + rs.getString( "CodModLote" ) );
					imp.say( imp.pRow() + 0, 121, "| " + rs.getString( "NroDiasValid" ) + " Dias" );
					imp.say( imp.pRow() + 0, 135, "|" );

					if ( imp.pRow() >= linPag ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );
						imp.incPags();
						imp.eject();
					}
				}
				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );

			}
			else {
				int seqest = 0;
				int cont = 0;
				while ( rs.next() ) {

					String sCodProd = txtCodProdEst.getVlrString();

					if ( imp.pRow() >= linPag ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );
						imp.incPags();
						imp.eject();
					}
					if ( imp.pRow() == 0 ) {
						imp.impCab( 136, true );
						imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( " ", 133 ) + "|" );
					}
					if ( ( !sCodProd.equals( rs.getString( 1 ) ) ) || ( seqest != rs.getInt( "SEQEST" ) ) ) {
						cont = 0;
						sCodProd = rs.getString( 1 );
						seqest = rs.getInt( "SEQEST" );
					}
					if ( sCodProd.equals( rs.getString( 1 ) ) && cont == 0 ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "=", 133 ) + "|" );

						if ( comRef() ) {
							imp.say( imp.pRow() + 1, 0, "| Refer�ncia" );
						}
						else {
							imp.say( imp.pRow() + 1, 0, "| C�d.prod." );
						}

						imp.say( imp.pRow() + 0, 13, "| Descri��o do produto" );
						imp.say( imp.pRow() + 0, 50, "| Seq.Est." );
						imp.say( imp.pRow() + 0, 60, "| Qtd." );
						imp.say( imp.pRow() + 0, 70, "| Descri��o" );
						imp.say( imp.pRow() + 0, 101, "| Ativo" );
						imp.say( imp.pRow() + 0, 110, "| Mod.Lote" );
						imp.say( imp.pRow() + 0, 121, "| Validade" );
						imp.say( imp.pRow() + 0, 135, "|" );

						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "=", 133 ) + "|" );

						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );

						if ( comRef() ) {
							imp.say( imp.pRow() + 0, 0, "| " + rs.getString( "REFPROD" ).trim() );
						}
						else {
							imp.say( imp.pRow() + 0, 0, "| " + rs.getString( "CODPROD" ) );
						}

						imp.say( imp.pRow() + 0, 13, "| " + rs.getString( "DESCPROD" ).substring( 0, 34 ) );
						imp.say( imp.pRow() + 0, 50, "| " + rs.getString( "SEQEST" ) );
						imp.say( imp.pRow() + 0, 60, "| " + rs.getString( "QTDEST" ) );
						imp.say( imp.pRow() + 0, 70, "| " + rs.getString( "DESCEST" ).substring( 0, 29 ) );
						imp.say( imp.pRow() + 0, 100, "| " + ( rs.getString( "ATIVOEST" ).equals( "S" ) ? "Sim" : "N�o" ) );
						imp.say( imp.pRow() + 0, 110, "| " + rs.getString( "CODMODLOTE" ) == null ? "" : rs.getString( "CODMODLOTE" ) );
						imp.say( imp.pRow() + 0, 121, "| " + rs.getString( "NRODIASVALID" ) == null ? "" : ( rs.getString( "NRODIASVALID" ) + " Dias" ) );
						imp.say( imp.pRow() + 0, 135, "|" );

						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );

						imp.say( imp.pRow() + 1, 0, "| Item" );
						imp.say( imp.pRow() + 0, 8, "| Cod.prod" );
						imp.say( imp.pRow() + 0, 20, "| Descri��o do produto" );
						imp.say( imp.pRow() + 0, 60, "| Qtd." );
						imp.say( imp.pRow() + 0, 70, "| Cod.fase" );
						imp.say( imp.pRow() + 0, 80, "| Descri��o da fase" );
						imp.say( imp.pRow() + 0, 123, "| Auto Rma" );
						imp.say( imp.pRow() + 0, 135, "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
						cont++;
					}

					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );

					imp.say( imp.pRow() + 0, 0, "| " + rs.getString( "SEQITEST" ) );
					if ( comRef() ) {
						imp.say( imp.pRow() + 0, 8, "| " + ( rs.getString( "REFPRODPD" ) == null ? "" : rs.getString( "REFPRODPD" ).trim() ) );
					}
					else {
						imp.say( imp.pRow() + 0, 8, "| " + rs.getString( "CODPRODPD" ) );
					}
					imp.say( imp.pRow() + 0, 20, "| " + rs.getString( "DESCPRODPD" ).substring( 0, 38 ) );
					imp.say( imp.pRow() + 0, 60, "| " + rs.getString( "QTDITEST" ) );
					imp.say( imp.pRow() + 0, 70, "| " + rs.getString( "CODFASE" ) );
					imp.say( imp.pRow() + 0, 80, "| " + rs.getString( "DESCFASE" ).substring( 0, 38 ) );
					imp.say( imp.pRow() + 0, 123, "|   " + ( rs.getString( "RMAAUTOITEST" ).equals( "S" ) ? "Sim" : "N�o" ) );
					imp.say( imp.pRow() + 0, 135, "|" );
				}

				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );

			}

			imp.eject();
			imp.fechaGravacao();

			con.commit();

			if ( visualizar == TYPE_PRINT.VIEW )
				imp.preview( this );
			else
				imp.print();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void imprimirFT( TYPE_PRINT bVisualizar ) {
		String layoutft = (String) prefere.get("layoutft");
		byte[] img01 = (byte[]) prefere.get("img01");
		if (layoutft==null || "".equals(layoutft)) {
			Funcoes.mensagemInforma( this, "Layout de ficha t�cnica n�o foi definido nos par�metros preferenciais !" );
			return;
		}
		if (lcCampos.getStatus()==ListaCampos.LCS_INSERT 
				|| lcCampos.getStatus()==ListaCampos.LCS_NONE
				|| "".equals(txtCodProdEst.getVlrString())
				|| "".equals(txtSeqEst.getVlrString())
			) {
			Funcoes.mensagemInforma( this, "Selecione uma estrutura para impress�o da ficha t�cnica !" );
		}
		if (layoutft.indexOf( "org" )==-1) {
			layoutft = "layout/ft/"+layoutft;
		}
		try { 
			StringBuilder sql = new StringBuilder();
			sql.append("select pd.codprod, pd.descprod, et.numeroft, et.dtrevisaoft ");
			sql.append(", et.desccompl, et.finalidadeuso, et.modousar, et.mecanismoacao ");
			sql.append(",img01.binimg img01 ");
			sql.append(",img02.binimg img02 ");
			sql.append(",img03.binimg img03 ");
			sql.append(",img04.binimg img04 ");
			sql.append(",img05.binimg img05 ");
			sql.append(",img06.binimg img06 ");
			sql.append(",imgpd.binimg imgpd ");
			sql.append("from eqproduto pd ");
			sql.append("inner join ppestrutura et ");
			sql.append("on et.codemp=pd.codemp and et.codfilial=pd.codfilial and et.codprod=pd.codprod ");
			sql.append("left outer join sgprefere5 p5 ");
			sql.append("on p5.codemp=? and p5.codfilial=? ");
			sql.append("left outer join sgimagem img01 ");
			sql.append("on img01.codemp=p5.codempi1 and img01.codfilial=p5.codfiliali1 and img01.codimg=p5.codimgft01 ");
			sql.append("left outer join sgimagem img02 ");
			sql.append("on img02.codemp=p5.codempi2 and img02.codfilial=p5.codfiliali2 and img02.codimg=p5.codimgft02 ");
			sql.append("left outer join sgimagem img03 ");
			sql.append("on img03.codemp=p5.codempi3 and img03.codfilial=p5.codfiliali3 and img03.codimg=p5.codimgft03 ");
			sql.append("left outer join sgimagem img04 ");
			sql.append("on img04.codemp=p5.codempi4 and img04.codfilial=p5.codfiliali4 and img04.codimg=p5.codimgft04 ");
			sql.append("left outer join sgimagem img05 ");
			sql.append("on img05.codemp=p5.codempi5 and img05.codfilial=p5.codfiliali5 and img05.codimg=p5.codimgft05 ");
			sql.append("left outer join sgimagem img06 ");
			sql.append("on img06.codemp=p5.codempi6 and img06.codfilial=p5.codfiliali6 and img06.codimg=p5.codimgft06 ");
			sql.append("left outer join eqgrupoimg gimg ");
			sql.append("on gimg.codemp=pd.codempgp and gimg.codfilial=pd.codfilialgp and gimg.codgrup=pd.codgrup ");
			sql.append("and gimg.seqimg=(select first 1 gimg1.seqimg from eqgrupoimg gimg1, sgimagem imgc ");
			sql.append("where gimg1.codemp=pd.codempgp and gimg1.codfilial=pd.codfilialgp and gimg1.codgrup=pd.codgrup ");
			sql.append("and imgc.codemp=gimg1.codemp and imgc.codfilial=gimg1.codfilial and imgc.codimg=gimg1.codimg ");
			sql.append("and imgc.codempci=p5.codempci and imgc.codfilialci=p5.codfilialci and imgc.codcatimg=p5.codcatimg ");
			sql.append(")");
			sql.append("left outer join sgimagem imgpd ");
			sql.append("on imgpd.codemp=gimg.codempim and imgpd.codfilial=gimg.codfilialim and imgpd.codimg=gimg.codimg ");
			sql.append("where pd.codemp=? and pd.codfilial=? and pd.codprod=? ");
			sql.append("and et.seqest=? "); 
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			System.out.println(sql);
			int param = 1;
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGPREFERE5" ) );
			ps.setInt( param++, lcCampos.getCodEmp() );
			ps.setInt( param++, lcCampos.getCodFilial() );
			ps.setInt( param++, txtCodProdEst.getVlrInteger() );
			ps.setInt( param++, txtSeqEst.getVlrInteger() );
			ResultSet rs = ps.executeQuery();
			HashMap<String, Object> hParam = new HashMap<String, Object>();
			//hParam.put( "IMG01", img01 );
			//hParam.put( "IMG02", img01 );
			
			FPrinterJob dlGr = null;
			try {
				dlGr = new FPrinterJob( layoutft, "Ficha t�cnica do produto", "", rs, hParam, this );
				if ( bVisualizar==TYPE_PRINT.VIEW ) {
					dlGr.preview();
				}
				else {
						dlGr.print(true);
				}
			} catch ( Exception err ) {
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro na impress�o de ficha t�cnica !" + err.getMessage(), true, con, err );
			}
			rs.close();
			ps.close();
			con.commit();
		} catch (SQLException err) {
			String mensagemErr = err.getMessage();
			try {
				con.rollback();
			} catch (SQLException errroll) {
				errroll.printStackTrace();
			}
			Funcoes.mensagemErro( this, "Erro imprimindo ficha t�cnica !\n"+mensagemErr );
		}
	}

	private void imprimir( TYPE_PRINT bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sWhere = "";
		String[] sValores;
		DLREstrutura dl = null;

		try {

			dl = new DLREstrutura();
			dl.setVisible( true );

			if ( dl.OK == false ) {
				dl.dispose();
				return;
			}

			sValores = dl.getValores( comRef() );

			boolean resumido = "R".equals( sValores[ 1 ] );

			if ( resumido && sValores[ 3 ].equals( "G" ) ) {
				Funcoes.mensagemInforma( this, "N�o existe modelo de relat�rio resumido para o tipo de impress�o gr�fica!" );
				return;
			}

			dl.dispose();

			if ( "A".equals( sValores[ 2 ] ) ) {

				if ( txtCodProdEst.getVlrInteger() > 0 ) {
					sWhere += " AND E.CODPROD=" + txtCodProdEst.getVlrString();
					sWhere += " AND E.SEQEST=" + txtSeqEst.getVlrString();
				}
				else {
					Funcoes.mensagemInforma( this, "Nenhum estrutura selecionada para impress�o!" );
					return;
				}

			}

			if ( resumido ) {

				try {

					sSQL = "SELECT E.CODPROD, E.REFPROD, PD.DESCPROD, E.SEQEST, E.QTDEST, E.DESCEST, E.ATIVOEST, E.CODMODLOTE, E.NRODIASVALID " + "FROM PPESTRUTURA E, EQPRODUTO PD " + "WHERE PD.CODEMP=E.CODEMP AND PD.CODFILIAL=E.CODFILIAL AND E.CODPROD=PD.CODPROD " + sWhere + " ORDER BY "
							+ sValores[ 0 ] + ",E.SEQEST";

					ps = con.prepareStatement( sSQL );
					rs = ps.executeQuery();

				} catch ( SQLException err ) {
					Funcoes.mensagemErro( this, "Erro consulta tabela de estrutura do produto!\n" + err.getMessage(), true, con, err );
				}
			}
			else if ( sValores[ 1 ].equals( "C" ) ) {

				sSQL = "SELECT E.CODPROD, E.REFPROD, PD.DESCPROD, IT.REFPRODPD, E.SEQEST, E.QTDEST, E.DESCEST, E.ATIVOEST, " + "E.CODMODLOTE, E.NRODIASVALID, IT.SEQITEST, IT.CODPRODPD, PI.DESCPROD DESCPRODPD, IT.QTDITEST, " + "IT.CODFASE, F.DESCFASE, IT.RMAAUTOITEST "
						+ "FROM PPESTRUTURA E, PPITESTRUTURA IT, EQPRODUTO PD, EQPRODUTO PI, PPFASE F " + "WHERE E.CODPROD=PD.CODPROD AND E.CODEMP=PD.CODEMP AND E.CODFILIAL=PD.CODFILIAL " + "AND IT.CODPROD=E.CODPROD AND IT.SEQEST=E.SEQEST AND IT.CODEMP=E.CODEMP AND IT.CODFILIAL=E.CODFILIAL "
						+ "AND IT.CODPRODPD=PI.CODPROD AND IT.CODEMPPD=PI.CODEMP AND IT.CODFILIALPD=PI.CODFILIAL " + "AND IT.CODFASE=F.CODFASE AND IT.CODEMPFS=F.CODEMP AND IT.CODFILIALFS=F.CODFILIAL " + sWhere + " ORDER BY " + sValores[ 0 ] + " ";

				try {

					ps = con.prepareStatement( sSQL );
					rs = ps.executeQuery();

				} catch ( SQLException err ) {
					Funcoes.mensagemErro( this, "Erro consulta tabela de insumos do produto!\n" + err.getMessage(), true, con, err );
				}
			}

			System.out.println( "SQL:" + sSQL );

			if ( sValores[ 3 ].equals( "T" ) ) {
				imprimirTexto( rs, bVisualizar, resumido );
			}
			else {
				imprimirGrafico( bVisualizar, rs, comRef(), resumido );
			}

		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sWhere = null;
			sValores = null;
			dl = null;
		}

	}

	public void imprimirGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final boolean bComRef, boolean resumido ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();
		hParam.put( "COMREF", bComRef ? "S" : "N" );

		FPrinterJob dlGr = null;

		dlGr = new FPrinterJob( "layout/rel/REL_ESTRUTURA_01.jasper", "Rela��o de componentes do produto", "", rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.preview();
		}
		else {
			try {
				dlGr.print(true);
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de extruturas!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btCopiar )
			copiarEstrutura();
		else if ( evt.getSource() == btPrevimp )
			imprimir( TYPE_PRINT.VIEW );
		else if ( evt.getSource() == btImp )
			imprimir( TYPE_PRINT.PRINT);
		else if ( evt.getSource() == btPrevimpFT )
			imprimirFT( TYPE_PRINT.VIEW );
		else if ( evt.getSource() == btImprimeFT )
			imprimirFT( TYPE_PRINT.PRINT);
		super.actionPerformed( evt );
	}

	private void copiarEstrutura() {
		
		DLSelProduto dl = new DLSelProduto(this);
		dl.setConexao( con );
		dl.setVisible( true );
		
		if (dl.OK) {
			try {
				daoestru.copiarEstrutura( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "PPESTRUTURA" ), 1, dl.getCodprod(), txtCodProdEst.getVlrInteger() );
				Funcoes.mensagemInforma( this, "Estrutura copiada com sucesso" + dl.getCodprod() );
			} catch (SQLException e) {
				try {
					con.rollback();	
				} catch ( SQLException e2 ) {
					Funcoes.mensagemErro( this, "Erro ao realizar rollback!!!");
				}
				
				Funcoes.mensagemErro( this, "Inconsist�ncia ao copiar estrutura, tente novamente!!!");
				e.printStackTrace();
			}
			
		} 		
		
	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( ( (JTabbedPanePad) ( cevt.getSource() ) ) == tpnAbas ) {
			if ( tpnAbas.getSelectedIndex() == 0 ) {
				setAltDet( 200 );
				pnDet.removeAll();
				setPainel( pinDetFases, pnDet );
				setListaCampos( lcDet );
				pnDet.repaint();
				navRod.setListaCampos( lcDet );
				navRod.setAtivo( 6, true );
			}
			else if ( tpnAbas.getSelectedIndex() == 1 ) {
				setAltDet( 110 );
				pnDet.removeAll();
				setPainel( pinDetItens, pnDet );
				setListaCampos( lcDetItens );
				pnDet.repaint();
				navRod.setListaCampos( lcDetItens );
				navRod.setAtivo( 6, true );
			}
			else if ( tpnAbas.getSelectedIndex() == 2 ) {
				setAltDet( 110 );
				pnDet.removeAll();
				setPainel( pinDetEstrAnalise, pnDet );
				setListaCampos( lcDetItens );
				pnDet.repaint();
				navRod.setListaCampos( lcDetEstrAnalise );
				navRod.setAtivo( 6, true );
			}
			else if ( tpnAbas.getSelectedIndex() == 3 ) {
				setAltDet( 110 );
				pnDet.removeAll();
				setPainel( pinDetDistrib, pnDet );
				setListaCampos( lcDetDistrib );
				pnDet.repaint();
				navRod.setListaCampos( lcDetDistrib );
				navRod.setAtivo( 6, false );
			}
			else if ( tpnAbas.getSelectedIndex() == 4 ) { // Aba subprod
				setAltDet( 110 );
				pnDet.removeAll();
				setPainel( pinSubProd, pnDet );
				setListaCampos( lcSubProd );
				pnDet.repaint();
				navRod.setListaCampos( lcSubProd );
				navRod.setAtivo( 6, false );
			}
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcProdItem || cevt.getListaCampos() == lcProdItemRef ) {
			String sRma = txtRMA.getVlrString();
			if ( sRma.equals( "S" ) )
				cbRmaAutoItEst.setEnabled( true );
			else
				cbRmaAutoItEst.setEnabled( false );
		}
		else if ( cevt.getListaCampos() == lcProdEst ) {
			if ( txtCLoteProd.getVlrString().equals( "S" ) ) {
				txtCodModLote.setAtivo( true );
				txtNroDiasValid.setAtivo( true );
			}
			else if ( "S".equals( txtSerieProd.getVlrString() ) ) {
				txtNroDiasValid.setAtivo( true );
			}
			else {
				txtCodModLote.setAtivo( false );
				txtNroDiasValid.setAtivo( false );
			}
		}
		else if ( cevt.getListaCampos() == lcDet ) {
			bloqueiaAbas();
		}
		if ( cevt.getListaCampos() == lcTpAnalise ) {
			if ( "MM".equals( txtTpExp.getVlrString() ) ) {
				txtVlrMin.setEnabled( true );
				txtVlrMax.setEnabled( true );
				txtVlrMin.setRequerido( true );
				txtVlrMax.setRequerido( true );
				txtEspecificacao.setEnabled( false );
				txtEspecificacao.setRequerido( false );
			}
			else if ( "DT".equals( txtTpExp.getVlrString() ) ) {
				txtVlrMin.setEnabled( false );
				txtVlrMax.setEnabled( false );
				txtVlrMin.setRequerido( false );
				txtVlrMax.setRequerido( false );
				txtEspecificacao.setEnabled( true );
				txtEspecificacao.setRequerido( true );
			}
		}
		if ( cevt.getListaCampos() == lcUnid ) {
			txtVlrMin.setDecimal( txtCasasDec.getVlrInteger() );
			txtVlrMax.setDecimal( txtCasasDec.getVlrInteger() );
		}
		else if ( cevt.getListaCampos() == lcFase ) {
			rgTipoExterno.setAtivo( "S".equals( cbExternaFase.getVlrString() ));
		}

	}

	private void bloqueiaAbas() {

		if ( cbFinaliza.getStatus() ) {
			tpnAbas.setEnabledAt( 3, true );
		}
		else {
			tpnAbas.setEnabledAt( 3, false );
		}

		if ( txtTipoFase.getVlrString().equals( "CQ" ) ) {
			tpnAbas.setEnabledAt( 2, true );
			cbCProva.setEnabled( true );
		}
		else {
			tpnAbas.setEnabledAt( 2, false );
			cbCProva.setEnabled( false );
		}

	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterPost( PostEvent pevt ) {

		if ( pevt.getListaCampos() == lcCampos ) {
			if ( tpnAbas.getSelectedIndex() != 0 )
				tpnAbas.setSelectedIndex( 1 );
		}
		else if ( pevt.getListaCampos() == lcDet ) {
			bloqueiaAbas();
		}
	}

	public void beforePost( PostEvent pevt ) {

		// super.beforePost( pevt );

		if ( pevt.getListaCampos() == lcDetEstrAnalise ) {
			if ( "MM".equals( txtTpExp.getVlrString() ) ) {
				if ( txtVlrMin.getVlrString().equals( "0" ) || txtVlrMax.getVlrInteger().equals( "0" ) ) {
					Funcoes.mensagemInforma( this, "Informe os valores!" );
					pevt.cancela();
				}

			}
			else if ( "DT".equals( txtTpExp.getVlrString() ) ) {
				if ( "".equals( txtEspecificacao.getVlrString() ) ) {
					Funcoes.mensagemInforma( this, "Informe a descri��o!" );
					pevt.cancela();
				}
			}
		}
	}

	private boolean comRef() {

		return ( (Boolean) prefere.get( "usarefprod" ) ).booleanValue();
	}

	private void getPreferencias() {

		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			prefere = new HashMap<String, Object>();

			sql.append( "select pf1.usarefprod, pf5.expedirrma, coalesce(pf5.layoutft,'') layoutft " );
			//sql.append( ", img01.binimg img01 ");
			sql.append( "from sgprefere1 pf1 ");
			sql.append(", sgprefere5 pf5 ");
			//sql.append( "left outer join sgimagem img01 on ");
			//sql.append( "img01.codemp=pf5.codempi1 and img01.codfilial=pf5.codfiliali1 and img01.codimg=pf5.codimgft01 ");
			sql.append( "where pf1.codemp=? and pf1.codfilial=? and pf5.codemp= pf1.codemp and pf5.codfilial=pf1.codfilial" );
			
			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				prefere.put( "usarefprod", new Boolean( "S".equals( rs.getString( "usarefprod" ) ) ) );
				prefere.put( "expedirrma",  rs.getString( "expedirrma" ) );
				prefere.put( "layoutft", rs.getString( "layoutft" ));
				//prefere.put( "img01", rs.getBytes("img01")  );
			}
			rs.close();
			ps.close();
			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		getPreferencias();

		montaTela();

		lcProdEst.setConexao( cn );
		lcProdItem.setConexao( cn );
		lcProdItemRef.setConexao( cn );
		lcModLote.setConexao( cn );
		lcTipoRec.setConexao( cn );
		lcFase.setConexao( cn );
		lcDetDistrib.setConexao( cn );
		lcDetItens.setConexao( cn );
		lcEstDistrib.setConexao( cn );
		lcTpAnalise.setConexao( cn );
		lcDetEstrAnalise.setConexao( cn );
		lcSubProd.setConexao( cn );
		lcProdItemSP.setConexao( cn );
		lcProdItemRefSP.setConexao( cn );
		lcUnid.setConexao( cn );
		
		
		daoestru = new DAOEstrutura( cn );
	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_ENTER && kevt.getSource() == txtQtdItEst ) {

			if ( lcDetItens.getStatus() == ListaCampos.LCS_INSERT ) {

				cbRmaAutoItEst.setVlrString( "S" );
				cbQtdVariavelItem.setVlrString( "S" );

				lcDetItens.post();
				lcDetItens.limpaCampos( true );
				lcDetItens.setState( ListaCampos.LCS_NONE );
				lcDetItens.edit();

				focusCodprodDetItens();

			}
		}
	}

	private void focusCodprodDetItens() {

		if ( comRef() ) {
			txtRefProdItem.requestFocus();
		}
		else {
			txtCodProdItem.requestFocus();
		}
	}

	public void beforeInsert( InsertEvent ievt ) {
	
	}

	public void afterInsert( InsertEvent ievt ) {
		if( ievt.getListaCampos() == lcCampos ){
			cbExpedirRMA.setVlrString( (String) prefere.get( "expedirrma" ) );
			rgBloqQtdProd.setVlrString( "N" );
		}
	}

}
