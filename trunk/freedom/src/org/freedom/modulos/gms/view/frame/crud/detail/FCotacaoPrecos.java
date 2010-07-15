/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Alexandre Rocha Lima e Marcondes <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.modulos.gms <BR>
 *         Classe:
 * @(#)FCompra.java <BR>
 *                  Este programa � licenciado de acordo com a LPG-PC (Licen�a
 *                  P�blica Geral para Programas de Computador), <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                  A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e
 *                  REPRODU��ES deste Programa. <BR>
 *                  Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com
 *                  este Programa, voc� pode contatar <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                  Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR
 *                  este Programa � preciso estar <BR>
 *                  de acordo com os termos da LPG-PC <BR>
 *                  <BR>
 *                  Tela para cadastro de cota��es de pre�o para compra.
 */

package org.freedom.modulos.gms.view.frame.crud.detail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

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
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.Navegador;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.frame.FObservacao;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.view.dialog.report.DLRPedido;
import org.freedom.modulos.std.view.dialog.utility.DLBuscaProd;


public class FCotacaoPrecos extends FDetalhe implements PostListener,
CarregaListener, FocusListener, ActionListener, InsertListener {

	private static final long serialVersionUID = 1L;
	private int casasDec = Aplicativo.casasDec;
	private int casasDecFin = Aplicativo.casasDecFin;
	private JPanelPad pinCab = new JPanelPad(740, 242);
	private JPanelPad pinBotCab = new JPanelPad(104, 92);
	private JPanelPad pinBotDet = new JPanelPad(104, 63);
	private JPanelPad pinLb = new JPanelPad();

	private JLabelPad lSitItSol = null;
	private JPanelPad pinDet = new JPanelPad();
	private JButtonPad btProduto = new JButtonPad("Produto", Icone.novo("btProduto2.gif"));
	private JButtonPad btAprovaSol = new JButtonPad("Aprovar", Icone.novo("btTudo.gif"));
	private JButtonPad btFinAprovSol = new JButtonPad("Finaliz. aprov.", Icone.novo("btFechaVenda.gif"));
	private JButtonPad btCompra = new JButtonPad("Comprar", Icone.novo("btMedida.gif"));
	private JButtonPad btCancelaItem = new JButtonPad("Cancelar", Icone.novo("btRetorno.gif"));
	private JButtonPad btMotivoCancelaItem = new JButtonPad("Mot.Can", Icone.novo("btObs.gif"));
	private JButtonPad btMotivoAbaixo = new JButtonPad("Mot.Abaixo", Icone.novo("btObs.gif"));
	private JTextFieldPad txtCodSolicitacao = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtDtEmitSolicitacao = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtCodItSolicitacao = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtQtdItAprovado = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private JTextFieldPad txtIDUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,13, 0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13, 0);
	private JTextFieldPad txtCodProd2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldPad txtRefProd2 = new JTextFieldPad(JTextFieldPad.TP_STRING,13, 0);
	private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,19, 0);
	private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10, 0);
	private JTextFieldPad txtOrigSolicitacao = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldFK txtDescProd2 = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldPad txtStatusSolicitacao = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituacaoItAprov = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituacaoCompItAprov = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituacaoItComp = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtSituacaoIt = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtCodCot = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5, 0);
	private JTextFieldPad txtDtCot = new JTextFieldPad(JTextFieldPad.TP_DATE, 10,0);
	private JTextFieldPad txtIdUsuCot = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldPad txtCodUnid = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
	private JTextFieldFK txtDescUnid = new JTextFieldFK(JTextFieldPad.TP_STRING, 60, 0);			
	private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8, 0);
	private JTextFieldFK txtDescFor = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldPad txtQtdCot = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15, casasDec);
	private JTextFieldPad txtQtdAprovCot = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private JTextFieldPad txtPrecoCot = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDecFin);
	private JTextFieldPad txtCodFabProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldPad txtNomeUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,40, 0);
	private JTextFieldPad txtCodCCUsu = new JTextFieldPad(JTextFieldPad.TP_STRING, 19, 0);
	private JTextFieldPad txtVlrFreteItCompra = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDecFin);
	private JTextFieldPad txtPercIpiItCompra = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 9, casasDec);
	private JTextFieldPad txtVlrLiqItCompra = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDecFin);
	private JTextFieldPad txtVlrBaseIpiItCompra = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDecFin);
	private JTextFieldPad txtVlrIpiItCompra = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDecFin);
	private JTextAreaPad txaMotivoCancCot = new JTextAreaPad();
	private JTextAreaPad txaMotivoCotAbaixo = new JTextAreaPad();
	private JTablePad tabCot = new JTablePad();
	private JScrollPane spTabCot = new JScrollPane(tabCot);
	private Navegador navCot = new Navegador(true);	
	private JRadioGroup<?, ?> rgPriod = null;
	private Vector<String> vLabsTipo = new Vector<String>();
	private Vector<String> vValsTipo = new Vector<String>();	
	private ListaCampos lcUsu = new ListaCampos(this, "UU");
	private ListaCampos lcProd = new ListaCampos(this, "PD");
	private ListaCampos lcProd2 = new ListaCampos(this, "PD");
	private ListaCampos lcProd3 = new ListaCampos(this, "PD");
	private ListaCampos lcProd4 = new ListaCampos(this, "PD");
	private ListaCampos lcCC = new ListaCampos(this, "CC");
	private ListaCampos lcFor = new ListaCampos(this, "FR");
	private ListaCampos lcCotacao = new ListaCampos(this, "");
	private ListaCampos lcUnid = new ListaCampos(this, "UD");

	String sSitItSol = txtSituacaoIt.getVlrString();
	String sOrdSol = "";
	Integer anoCC = null;
	Integer iCodTpMov = null;
	String codCC = null;
	boolean bAprovaParcial = false;
	String SitSol = "";
	boolean[] bPrefs = null;
	boolean bAprovaCab = false;
	boolean bCotacao = false;
	int cont = 0;
	Vector<String> vItem = new Vector<String>();
	Vector<String> vProdCan = new Vector<String>();
	Vector<String> vMotivoCan = new Vector<String>();
	Vector<String> vPrecCan = new Vector<String>();
	Vector<String> vQtdCan = new Vector<String>();
	String sSitSol;
	String sSitItAprov;
	String sSitItExp;

	public FCotacaoPrecos() {
		setTitulo("Cota��o de Pre�os");
		setAtribos(15, 10, 763, 580);

		pnMaster.remove(2);
		pnGImp.removeAll();
		pnGImp.setLayout(new GridLayout(1, 3));
		pnGImp.setPreferredSize(new Dimension(220, 26));
		pnGImp.add(btPrevimp);
		pnGImp.add(btImp);

		pnMaster.add(spTab, BorderLayout.CENTER);

		String sWhereAdicProd = "ATIVOPROD='S' AND ((SELECT ANOCCUSU||CODCCUSU FROM sgretinfousu('"
			+ Aplicativo.strUsuario
			+ "')) IN "
			+ "(SELECT ANOCC||CODCC FROM EQPRODACESSO PA WHERE TIPOPA='RMA' AND PA.codemp=EQPRODUTO.CODEMP AND "
			+ "PA.CODFILIAL=EQPRODUTO.CODFILIAL AND PA.CODPROD=EQPRODUTO.CODPROD) "
			+ "OR "
			+ "((SELECT coalesce(COUNT(1),0) FROM EQPRODACESSO PA WHERE TIPOPA='RMA' AND PA.codemp=EQPRODUTO.CODEMP AND "
			+ "PA.CODFILIAL=EQPRODUTO.CODFILIAL AND PA.CODPROD=EQPRODUTO.CODPROD)=0) "
			+ "OR "
			+ "((SELECT ALMOXARIFE FROM sgretinfousu('"
			+ Aplicativo.strUsuario
			+ "'))='S') "
			+ "OR "
			+ "((SELECT APROVARMA FROM sgretinfousu('"
			+ Aplicativo.strUsuario
			+ "'))='TD') " + ") ";

		lcProd.add(new GuardaCampo(txtCodProd, "CodProd", "C�d.prod.",
				ListaCampos.DB_PK, false));
		lcProd.add(new GuardaCampo(txtDescProd, "DescProd", "Descri��o do produto",
				ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtRefProd, "RefProd", "Refer�ncia",
				ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtCodFabProd, "CodFabProd", "C�digo do fabricante", 
				ListaCampos.DB_SI, true));		
		lcProd.add(new GuardaCampo(txtCodUnid, "CodUnid", "C�d.und.",
				ListaCampos.DB_SI, txtDescUnid, false));						
		lcProd.setWhereAdic(sWhereAdicProd);
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setReadOnly(true);
		txtCodProd.setTabelaExterna(lcProd, null);

		lcProd2.add(new GuardaCampo(txtRefProd, "RefProd", "Refer�ncia",
				ListaCampos.DB_PK, false));
		lcProd2.add(new GuardaCampo(txtDescProd, "DescProd", "Descri��o",
				ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCodProd, "CodProd", "C�d.rod.",
				ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCodFabProd, "CodFabProd", "C�digo do fabricante", 
				ListaCampos.DB_SI, true));		
		lcProd2.add(new GuardaCampo(txtCodUnid, "CodUnid", "C�d.und.",
				ListaCampos.DB_SI, txtDescUnid, false));		

		txtRefProd.setNomeCampo("RefProd");
		txtRefProd.setListaCampos(lcDet);
		lcProd2.setWhereAdic(sWhereAdicProd);
		lcProd2.montaSql(false, "PRODUTO", "EQ");
		lcProd2.setQueryCommit(false);
		lcProd2.setReadOnly(true);
		txtRefProd.setTabelaExterna(lcProd2, null);

		lcProd3.add(new GuardaCampo(txtCodProd2, "CodProd", "C�d.prod.",
				ListaCampos.DB_PK, false));
		lcProd3.add(new GuardaCampo(txtDescProd2, "DescProd", "Descri��o do produto",
				ListaCampos.DB_SI, false));
		lcProd3.add(new GuardaCampo(txtRefProd2, "RefProd", "Refer�ncia",
				ListaCampos.DB_SI, false));
		lcProd3.montaSql(false, "PRODUTO", "EQ");
		lcProd3.setReadOnly(true);
		txtCodProd2.setTabelaExterna(lcProd3, null);

		lcProd4.add(new GuardaCampo(txtRefProd2, "RefProd", "Refer�ncia",
				ListaCampos.DB_PK, false));
		lcProd4.add(new GuardaCampo(txtDescProd2, "DescProd", "Descri��o",
				ListaCampos.DB_SI, false));
		lcProd4.add(new GuardaCampo(txtCodProd2, "CodProd", "C�d.rod.",
				ListaCampos.DB_SI, false));

		txtRefProd2.setNomeCampo("RefProd");
		txtRefProd2.setListaCampos(lcCotacao);
		lcProd4.montaSql(false, "PRODUTO", "EQ");
		lcProd4.setQueryCommit(false);
		lcProd4.setReadOnly(true);
		txtRefProd2.setTabelaExterna(lcProd4, null);

		lcCC.add(new GuardaCampo(txtCodCC, "CodCC", "C�d.c.c.", ListaCampos.DB_PK,
				false));
		lcCC.add(new GuardaCampo(txtAnoCC, "AnoCC", "Ano c.c.", ListaCampos.DB_PK,
				false));
		lcCC.add(new GuardaCampo(txtDescCC, "DescCC",
				"Descri��o do centro de custo", ListaCampos.DB_SI, false));
		lcCC.montaSql(false, "CC", "FN");
		lcCC.setQueryCommit(false);
		lcCC.setReadOnly(true);
		txtCodCC.setTabelaExterna(lcCC, null);
		txtAnoCC.setTabelaExterna(lcCC, null);

		lcUsu.add(new GuardaCampo(txtIDUsu, "idusu", "Id.Usu.", ListaCampos.DB_PK,
				false));
		lcUsu.add(new GuardaCampo(txtNomeUsu, "nomeusu", "Nome do usu�rio",
				ListaCampos.DB_SI, false));
		lcUsu.add(new GuardaCampo(txtCodCCUsu, "codcc", "C.Custo Usu�rio",
				ListaCampos.DB_SI, false));
		lcUsu.montaSql(false, "USUARIO", "SG");
		lcUsu.setQueryCommit(false);
		lcUsu.setReadOnly(true);
		txtIDUsu.setTabelaExterna(lcUsu, null);

		lcUnid.add(new GuardaCampo(txtCodUnid, "CodUnid", "C�d.unid.",
				ListaCampos.DB_PK, true));
		lcUnid.add(new GuardaCampo(txtDescUnid, "DescUnid",
				"Unidade", ListaCampos.DB_SI, false));
		lcUnid.montaSql(false, "UNIDADE", "EQ");
		lcUnid.setReadOnly(true);
		lcUnid.setQueryCommit(false);
		txtCodUnid.setTabelaExterna(lcUnid, null);		

		lcFor.add(new GuardaCampo(txtCodFor, "CodFor", "C�d.for.",
				ListaCampos.DB_PK, false));
		lcFor.add(new GuardaCampo(txtDescFor, "RazFor",
				"Raz�o social do fornecedor", ListaCampos.DB_SI, false));
		lcFor.montaSql(false, "FORNECED", "CP");
		lcFor.setQueryCommit(false);
		lcFor.setReadOnly(true);
		txtCodFor.setTabelaExterna(lcFor, null);

		vValsTipo.addElement("M");
		vValsTipo.addElement("A");
		vLabsTipo.addElement("Normal");
		vLabsTipo.addElement("Urgente");
		rgPriod = new JRadioGroup<String, String>(2, 1, vLabsTipo, vValsTipo);
		rgPriod.setVlrString("M");

		pinCab = new JPanelPad(740, 267);
		setListaCampos(lcCampos);
		setAltCab(267);
		setPainel(pinCab, pnCliCab);

		txtDtEmitSolicitacao.setEditable(false);
		lcCampos.setPodeExc(false);
		lcCampos.setPodeIns(false);
		nav.setAtivo(0, false);
		nav.setAtivo(1, false);
		nav.setAtivo(2, false);
		nav.setAtivo(3, false);
		nav.setAtivo(4, false);

		adicCampo(txtCodSolicitacao, 7, 20, 70, 20, "CodSol", "C�d.Sol",
				ListaCampos.DB_PK, true);
		adicCampo(txtIDUsu, 451, 20, 80, 20, "IdUsu", "Id do usu�rio",
				ListaCampos.DB_FK, true);
		adicCampo(txtDtEmitSolicitacao, 539, 20, 86, 20, "DtEmitSol",
				"Data da Sol.", ListaCampos.DB_SI, true);

		adicDescFKInvisivel(txtDescCC, "DescCC", "Descri��o do c.c.");
		adicCampo(txtCodCC, 80, 20, 130, 20, "CodCC", "C�d.CC.", ListaCampos.DB_FK,
				txtDescCC, true);
		adicCampo(txtAnoCC, 213, 20, 70, 20, "AnoCC", "Ano CC.", ListaCampos.DB_FK,
				true);
		adicDescFK(txtDescCC, 286, 20, 162, 20, "DescCC",
		"Descri��o do centro de custos");

		adicCampoInvisivel(txtStatusSolicitacao, "SitSol", "Sit.Sol.",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtOrigSolicitacao, "OrigSol", "Origem",
				ListaCampos.DB_SI, false);

		txtIDUsu.setNaoEditavel(true);
		txtDtEmitSolicitacao.setNaoEditavel(true);
		txtCodCC.setNaoEditavel(true);
		txtAnoCC.setNaoEditavel(true);

		setListaCampos(true, "SOLICITACAO", "CP");
		lcCampos.setQueryInsert(false);

		lcCotacao.setMaster(lcDet);
		lcDet.adicDetalhe(lcCotacao);

		txtQtdItAprovado.addFocusListener(this);
		lcCampos.addPostListener(this);
		lcCampos.addCarregaListener(this);
		lcCotacao.addCarregaListener(this);
		lcCotacao.addPostListener(this);
		lcCotacao.addInsertListener(this);
		lcProd.addCarregaListener(this);
		lcProd2.addCarregaListener(this);
		lcDet.addPostListener(this);
		lcDet.addCarregaListener(this);
		lcDet.addInsertListener(this);
		lcCampos.addInsertListener(this);
		lcUsu.addCarregaListener(this);

		btProduto.setToolTipText("Ver a descri��o do produto.");
		btAprovaSol.setToolTipText("Aprovar todos os �tens.");
		btFinAprovSol.setToolTipText("Finaliza Aprova��o.");
		btCompra.setToolTipText("Comprar todos os �tens.");
		btCancelaItem.setToolTipText("Cancelar �tem.");
		btMotivoCancelaItem.setToolTipText("Motivo do cancelamento do �tem.");
		btMotivoAbaixo.setToolTipText("Motivo do n�mero de cota��es baixo.");

		pinCab.adic(pinBotCab, 630, 1, 114, 99);
		pinBotCab.adic(btAprovaSol, 0, 0, 110, 30);
		pinBotCab.adic(btFinAprovSol, 0, 31, 110, 30);
		pinBotCab.adic(btCompra, 0, 62, 110, 30);

		btProduto.addActionListener(this);
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		btAprovaSol.addActionListener(this);
		btCancelaItem.addActionListener(this);
		btCompra.addActionListener(this);
		btMotivoCancelaItem.addActionListener(this);
		btMotivoAbaixo.addActionListener(this);
		btFinAprovSol.addActionListener(this);

		pinDet = new JPanelPad(740, 100);
		setPainel(pinDet, pnDet);

		setImprimir(true);

		desabAprov(true);
		desabCot(true);
	}

	private void montaDetalhe() {
		setAltDet(100);
		setListaCampos(lcDet);
		setPainel(pinCab, pnCliCab);
		setNavegador(navCot);
		lcDet.setTabela(tabCot);
		lcDet.setNavegador(navCot);
		navCot.setListaCampos(lcDet);

		lcDet.setPodeExc(false);
		lcDet.setPodeIns(false);
		txtCodItSolicitacao.setEditable(false);
		txtCodProd.setEditable(false);
		txtRefProd.setEditable(false);
		txtQtdItAprovado.setEditable(false);

		adicCampo(txtCodItSolicitacao, 7, 60, 30, 20, "CodItSol", "Item",ListaCampos.DB_PK, true);
		if (comRef()) {
			adicCampo(txtRefProd, 40, 60, 87, 20, "RefProd", "Refer�ncia",ListaCampos.DB_FK, txtDescProd, true);
			adicCampoInvisivel(txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_SI,false);
			txtRefProd.setBuscaAdic(new DLBuscaProd(con, "REFPROD", lcProd2.getWhereAdic()));
		} 
		else {
			adicCampo(txtCodProd, 40, 60, 87, 20, "CodProd", "C�d.prod.",ListaCampos.DB_FK, txtDescProd, true);
			adicCampoInvisivel(txtRefProd, "RefProd", "Refer�ncia",ListaCampos.DB_SI, false);
			txtCodProd.setBuscaAdic(new DLBuscaProd(con, "CODPROD", lcProd.getWhereAdic()));
		}

		adicDescFK(txtDescProd, 130, 60, 302, 20, "DescProd","Descri��o do produto");
		adicDB(rgPriod, 635, 177, 100, 50, "PriorItSol", "Prioridade:", true);
		rgPriod.setEnabled(false);

		adicCampo(txtQtdItAprovado, 543, 60, 80, 20, "QtdAprovItSol", "Qtd.aprov.",ListaCampos.DB_SI, false);
		adic(btProduto, 435, 45, 105, 35);
		btProduto.setEnabled(false);

		adicCampoInvisivel(txtSituacaoCompItAprov, "SitCompItSol", "Sit.Comp.It.Sol.",ListaCampos.DB_SI, false);

		lcDet.montaSql(true, "ITSOLICITACAO", "CP");
		lcDet.setWhereAdic("SitAprovItSol <> 'NA' AND SitItSol <> 'CA'");
		lcDet.setQueryInsert(false);
		lcDet.montaTab();

		tabCot.setTamColuna(30, 0);
		tabCot.setTamColuna(80, 1);
		tabCot.setTamColuna(230, 2);
		tabCot.setTamColuna(70, 3);
		tabCot.setTamColuna(70, 4);
		tabCot.setTamColuna(70, 5);
		tabCot.setTamColuna(70, 6);
		tabCot.setTamColuna(70, 7);
		tabCot.setTamColuna(70, 8);
		tabCot.setTamColuna(70, 9);
		tabCot.setTamColuna(70, 10);

		nav.setName("Mestre");
		navCot.setNavigationOnly();
		navCot.setName("Detalhe 1");
		navRod.setName("Detalhe 2");
		FlowLayout flNavCot = new FlowLayout(FlowLayout.LEFT, 0, 0);
		JPanelPad pnNavCot = new JPanelPad(JPanelPad.TP_JPANEL, flNavCot);
		pnNavCot.setBorder(null);
		pnNavCot.add(navCot);
		pnNavCot.add(nav);
		pnNavCab.add(pnNavCot, BorderLayout.WEST);
		pinCab.adic(spTabCot, 7, 87, 620, 140);

		setListaCampos(lcCotacao);
		setPainel(pinDet, pnDet);
		setNavegador(navRod);
		navRod.setListaCampos(lcCotacao);
		lcCotacao.setNavegador(navRod);
		lcCotacao.setTabela(tab);

		txtQtdItAprovado.setNaoEditavel(true);
		txtDtCot.setSoLeitura(true);

		txtRefProd2.setSoLeitura(true);
		txtCodProd2.setSoLeitura(true);

		adicCampo(txtCodCot, 7, 20, 77, 20, "CodCot", "C�d.Cot.",
				ListaCampos.DB_PK, true);
		if (comRef()) {
			adic(txtRefProd2, 187, 60, 87, 20);
		} else {
			adic(txtCodProd2, 187, 60, 87, 20);
		}		
		adicDescFK(txtDescProd2, 277, 60, 302, 20, "DescProd",
		"Descri��o do produto");
		adicCampo(txtDtCot, 87, 20, 97, 20, "DtCot", "Dt.Cot.", ListaCampos.DB_SI,
				false);
		adicCampoInvisivel(txtIdUsuCot, "IdUsuCot", "Usu.Cot.", ListaCampos.DB_SI,
				false);
		adicCampo(txtCodFor, 187, 20, 77, 20, "CodFor", "Cod.For.",
				ListaCampos.DB_FK, txtDescFor, false);
		adicDescFK(txtDescFor, 267, 20, 197, 20, "RazFor",
		"Raz�o social do fornecedor");
		adicCampo(txtQtdCot, 467, 20, 57, 20, "QtdCot", "Qtd.Cot.",
				ListaCampos.DB_SI, false);
		adic(txtDescUnid, 527, 20, 100, 20);		
		adicCampo(txtQtdAprovCot, 7, 60, 87, 20, "QtdAprovCot", "Qtd.Aprov.Cot.",
				ListaCampos.DB_SI, false);
		adicCampo(txtPrecoCot, 97, 60, 87, 20, "PrecoCot", "Preco.Cot.",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtSituacaoIt, "SitItSol", "Sit.It.Sol.",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtSituacaoItAprov, "SitAprovItSol", "Sit.Ap.It.Sol.",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtSituacaoItComp, "SitCompItSol", "Sit.Cot.It.Sol.",
				ListaCampos.DB_SI, false);
		adicCampo(txtVlrFreteItCompra, 187, 60, 87, 20, "VlrFreteItCompra", "Val.Frete.It.",
				ListaCampos.DB_SI, false);
		adicCampo(txtPercIpiItCompra, 277, 60, 87, 20, "PercIpiItCompra", "Perc.IPI.It.",
				ListaCampos.DB_SI, false);
		adicCampo(txtVlrLiqItCompra, 367, 60, 87, 20, "VlrLiqItCompra", "Val.Liq.It.",
				ListaCampos.DB_SI, false);
		adicCampo(txtVlrBaseIpiItCompra, 457, 60, 87, 20, "VlrBaseIpiItCompra", "Val.Base.IPI.It.",
				ListaCampos.DB_SI, false);
		adicCampo(txtVlrIpiItCompra, 547, 60, 87, 20, "VlrIpiItCompra", "Val.IPI.It.",
				ListaCampos.DB_SI, false);

		lcCotacao.montaSql(true, "COTACAO", "CP");
		lcCotacao.montaTab();

		tab.setTamColuna(30, 0);
		tab.setTamColuna(80, 1);
		tab.setTamColuna(230, 2);
		tab.setTamColuna(70, 3);
		tab.setTamColuna(70, 4);
		tab.setTamColuna(70, 5);
		tab.setTamColuna(70, 6);
		tab.setTamColuna(70, 7);
		tab.setTamColuna(70, 8);
		tab.setTamColuna(70, 9);
		tab.setTamColuna(70, 10);
		tab.setTamColuna(70, 11);
		tab.setTamColuna(70, 12);
		tab.setTamColuna(70, 13);

		btMotivoAbaixo.setEnabled(false);

		pinBotDet.adic(btCancelaItem, 0, 0, 110, 28);
		pinBotDet.adic(btMotivoCancelaItem, 0, 29, 110, 28);
		pinDet.adic(pinBotDet, 630, 1, 114, 63);
		lSitItSol = new JLabelPad();
		lSitItSol.setForeground(Color.WHITE);
		pinLb.adic(lSitItSol, 31, 0, 110, 20);
		pinDet.adic(pinLb, 630, 66, 114, 24);
	}

	private void buscaInfoUsuAtual() {
		String sSQL = "SELECT ANOCC,CODCC,CODEMPCC,CODFILIALCC,APROVCPSOLICITACAOUSU,COMPRASUSU "
			+ "FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? " + "AND IDUSU=?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGUSUARIO"));
			ps.setString(3, Aplicativo.strUsuario);
			rs = ps.executeQuery();
			if (rs.next()) {
				String sAprova = rs.getString("APROVCPSOLICITACAOUSU");
				String sCotacao = rs.getString("COMPRASUSU");
				if (sAprova != null) {
					if (!sAprova.equals("ND")) {
						if (sAprova.equals("TD"))
							bAprovaCab = true;
						else if ((txtCodCC.getVlrString().trim().equals(rs.getString(
						"CODCC").trim()))
						&& (lcCC.getCodEmp() == rs.getInt("CODEMPCC"))
						&& (lcCC.getCodFilial() == rs.getInt("CODFILIALCC"))
						&& (sAprova.equals("CC"))) {
							bAprovaCab = true;
						} else {
							bAprovaCab = false;
						}

					}
				}
				if (sCotacao != null) {
					if (sCotacao.equals("S"))
						bCotacao = true;
					else
						bCotacao = false;
				}
			}
			con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage());
		}
	}

	public void focusGained(FocusEvent fevt) {}

	public void focusLost(FocusEvent fevt) {}

	public void beforeCarrega(CarregaEvent cevt) {}

	public void afterPost(PostEvent pevt) {
		if (pevt.getListaCampos() == lcCampos) {
			lcCampos.carregaDados();
		}
		if (pevt.getListaCampos() == lcDet) {
			lcCampos.carregaDados();
		}
	}

	private void desabCampos(boolean bHab) {
		txtCodProd.setNaoEditavel(bHab);
		txtQtdCot.setNaoEditavel(bHab);
		txtPrecoCot.setNaoEditavel(bHab);
		txtCodFor.setNaoEditavel(bHab);
		txtQtdAprovCot.setNaoEditavel(bHab);
		rgPriod.setAtivo(!bHab);
	}

	private void desabAprov(boolean bHab) {
		if (txtStatusSolicitacao.getVlrString().equals("AT")) {
			btAprovaSol.setEnabled(false);
			if (!txtStatusSolicitacao.getVlrString().equals("AF"))
				btFinAprovSol.setEnabled(true);
			else {
				btFinAprovSol.setEnabled(false);
			}
		} else {
			btAprovaSol.setEnabled(!bHab);
		}
		btMotivoCancelaItem.setEnabled(txtSituacaoItAprov.getVlrString().equals("CA"));

		btFinAprovSol.setEnabled(!bHab);
		btCancelaItem.setEnabled(!bHab);
		txtQtdAprovCot.setEnabled(btAprovaSol.isEnabled());
	}

	private void desabCot(boolean bHab) {
		btCompra.setEnabled(!bHab);
	}

	public void carregaWhereAdic() {
		buscaInfoUsuAtual();
		if ((bAprovaCab) || (bCotacao)) {
			if (bAprovaParcial) {
				lcCampos.setWhereAdic("CODCC='" + Aplicativo.strCodCCUsu
						+ "' AND ANOCC=" + Aplicativo.strAnoCCUsu);
			}
		} else {
			lcCampos.setWhereAdic("IDUSU='" + Aplicativo.strUsuario + "'");
		}
	}

	public void afterCarrega(CarregaEvent cevt) {
		buscaInfoUsuAtual();

		sSitSol = txtStatusSolicitacao.getVlrString();
		sSitItAprov = txtSituacaoItAprov.getVlrString();
		sSitItExp = txtSituacaoItComp.getVlrString();
		sSitItSol = txtSituacaoIt.getVlrString();

		boolean bStatusTravaTudo = (sSitItExp.equals("AF") ||(sSitSol.equals("CF"))||
				sSitSol.equals("EF") || sSitItExp.equals("EF") || sSitItSol.equals("CA") || sSitItExp.equals("CA"));
		boolean bCot = bCotacao && sSitItExp.equals("CF");

		if (cevt.getListaCampos() == lcDet) {
			if (comRef()) {
				txtRefProd2.setVlrString(txtRefProd.getVlrString());
				lcProd4.carregaDados();
			} else {
				txtCodProd2.setVlrString(txtCodProd.getVlrString());
				lcProd3.carregaDados();
			}

			if (sSitItExp.equals("CA")) {
				desabCampos(true);
				btMotivoCancelaItem.setEnabled(true);
			}
			btProduto.setEnabled(!txtCodProd.getVlrString().equals(""));
		}

		if (!txtIDUsu.getVlrString().equals(Aplicativo.strUsuario) && !bCotacao
				|| bStatusTravaTudo)
			desabCampos(true);
		else
			desabCampos(false);

		if (!bAprovaCab || bStatusTravaTudo ) {
			desabAprov(true);
		} else {
			desabAprov(false);
		}

		if (bCot)
			desabCot(false);
		else 
			desabCot(true);

		if (sSitItSol.equals("CA")) {
			SitSol = "Cancelado";
			lSitItSol.setText(SitSol);
			pinLb.setBackground(cor(250, 50, 50));
		} else if (sSitItSol.equals("PE") || sSitItExp.equals("PE")) {
			SitSol = "Pendente";
			lSitItSol.setText(SitSol);
			pinLb.setBackground(cor(255, 204, 51));
		} else if (sSitItSol.equals("ET") || sSitItExp.equals("EP")) {
			SitSol = "Cotado";
			lSitItSol.setText(SitSol);
			pinLb.setBackground(cor(0, 170, 30));
		} else if (sSitItAprov.equals("AT") || sSitItAprov.equals("AP")) {
			SitSol = "Aprovado";
			lSitItSol.setText(SitSol);
			pinLb.setBackground(cor(26, 140, 255));
		}

		if ((cevt.getListaCampos() == lcProd) || (cevt.getListaCampos() == lcProd2)) {
			txtCodUnid.atualizaFK();
		}

		if (cevt.getListaCampos() == lcDet) {
			if (txtQtdItAprovado.isEditable()) {
				if (txtQtdAprovCot.getVlrDouble().compareTo(new Double(0)) <= 0)
					txtQtdAprovCot.setVlrDouble(txtQtdItAprovado.getVlrDouble());
			}
		}
	}

	public boolean[] prefs() {
		boolean[] bRet = { false };
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("UsaRefProd").trim().equals("S"))
					bRet[0] = true;
			}
			con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage(), true, con, err);
		} finally {
			rs = null;
			ps = null;
			sSQL = null;
		}
		return bRet;
	}

	private boolean dialogObsDet() {
		boolean bRet = false;
		FObservacao obs = new FObservacao(txaMotivoCancCot.getVlrString());
		if (obs != null) {
			if ((!bAprovaCab) || (sSitItExp.equals("CA")))
				obs.txa.setEnabled(false);
			obs.setVisible(true);
			if (obs.OK) {
				txaMotivoCancCot.setVlrString(obs.getTexto());
				bRet = true;
			}
		}
		obs.dispose();
		return bRet;
	}

	private boolean dialogObsAbaixo() {
		boolean bRet = false;
		FObservacao obs = new FObservacao(txaMotivoCotAbaixo.getVlrString());
		if (obs != null) {
			if ((rgPriod.getVlrString().equals("A"))
					&& (txtIDUsu.getVlrString().equals(Aplicativo.strUsuario))) {
				obs.txa.setEnabled(true);
			} else
				obs.txa.setEnabled(false);
			obs.setVisible(true);
			if (obs.OK) {
				txaMotivoCotAbaixo.setVlrString(obs.getTexto());
				bRet = true;
			}
		}
		obs.dispose();
		return bRet;
	}

	private void abreProd() {
		int iCodOrc = txtCodProd.getVlrInteger().intValue();
		if (fPrim.temTela("Cadastro de Produtos") == false) {
			FProduto tela = new FProduto();
			fPrim.criatela("Cadastro de Produtos", tela, con);
			tela.exec(iCodOrc);
		}
	}	

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btProduto)
			abreProd();
		if (evt.getSource() == btPrevimp)
			imprimir(true, txtCodSolicitacao.getVlrInteger().intValue());
		else if (evt.getSource() == btImp)
			imprimir(false, txtCodSolicitacao.getVlrInteger().intValue());
		else if (evt.getSource() == btMotivoCancelaItem) {
			dialogObsDet();
		} else if (evt.getSource() == btMotivoAbaixo) {
			dialogObsAbaixo();
		} else if (evt.getSource() == btCancelaItem) {
			lcCotacao.setState(ListaCampos.LCS_EDIT);
			if (Funcoes.mensagemConfirma(null,
			"Deseja cancelar �tem da compra?") == JOptionPane.YES_OPTION) {
				if (dialogObsDet()) {
					txtSituacaoItComp.setVlrString("CA");
					lcCotacao.post();
				}
			}
		}

		else if (evt.getSource() == btAprovaSol) {
			if (Funcoes.mensagemConfirma(
					null,
					"Deseja Aprovar todos os �tens da compra?\n Caso voc� n�o tenha informado as quantidades\n a serem aprovadas"
					+ " estar� aprovando as quantidades requeridas!") == JOptionPane.OK_OPTION) {
				;
				lcCampos.setState(ListaCampos.LCS_EDIT);
				txtStatusSolicitacao.setVlrString("CT");
				lcCampos.post();
			}
		} else if (evt.getSource() == btFinAprovSol) {
			if (Funcoes
					.mensagemConfirma(
							null,
							"Deseja finalizar o processo de aprova��o da compra?\n Ap�s este procedimento a compra n�o poder� mais ser alterada\n"
							+ "e estar� dispon�vel para expedi��o da nota fiscal!") == JOptionPane.OK_OPTION) {
				;
				lcCampos.setState(ListaCampos.LCS_EDIT);
				txtStatusSolicitacao.setVlrString("CF");
				lcCampos.post();
			}
		} else if (evt.getSource() == btCompra) {
			if (Funcoes
					.mensagemConfirma(
							null,
							"Deseja cotar todos os �tens da solicita��o de compra?\n Caso voc� n�o tenha informado as quantidades\n a serem cotadas"
							+ " estar� cotando as quantidades aprovadas!") == JOptionPane.OK_OPTION) {
				;
				lcCampos.setState(ListaCampos.LCS_EDIT);
				txtStatusSolicitacao.setVlrString("EF");
				lcCampos.post();
			}
		}

		super.actionPerformed(evt);
	}

	private void imprimir(boolean bVisualizar, int iCodSol) {
		ImprimeOS imp = new ImprimeOS("", con);
		int linPag = imp.verifLinPag() - 1;
		DLRPedido dl = new DLRPedido(sOrdSol, false);
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		imp.verifLinPag();
		imp.montaCab();

		imp.setTitulo("Relat�rio de Cota��o de Pre�os");

		String sSQL = "SELECT  (SELECT COUNT(IT.CODITSOL) FROM CPITSOLICITACAO IT "
			+ " WHERE IT.CODEMP=R.CODEMP AND IT.CODFILIAL = R.CODFILIAL AND IT.CODSOL=R.CODSOL),"
			+ "R.CODSOL,R.DTINS,R.SITSOL,R.IDUSU,R.MOTIVOSOL,R.IDUSUAPROV,R.DTAAPROVSOL,R.MOTIVOCANCSOL,"
			+ "I.CODPROD, I.QTDITSOL, I.QTDAPROVITSOL, I.SITITSOL,"
			+ "I.SITITSOL,I.SITAPROVITSOL,I.CODITSOL,"
			+ "P.REFPROD,P.DESCPROD, P.CODUNID,"
			+ "A.CODALMOX, A.DESCALMOX, CC.CODCC, CC.ANOCC, CC.DESCCC,"
			+ "(SELECT U.CODCC FROM SGUSUARIO U WHERE U.IDUSU=R.IDUSUAPROV),"
			+ "(SELECT C.DESCCC FROM FNCC C, SGUSUARIO U "
			+ "WHERE C.CODEMP=U.CODEMPCC AND C.CODFILIAL=U.CODEMPCC AND C.ANOCC=U.ANOCC "
			+ " AND C.CODCC=U.CODCC AND U.IDUSU=R.IDUSUAPROV),"
			+ " I.MOTIVOCANCITSOL, I.CODPROD, "
			+ " C.DTCOT, C.IDUSUCOT, C.CODEMPFR, C.CODFILIALFR, C.CODFOR, "
			+ " C.QTDCOT, C.QTDAPROVCOT, C.PRECOCOT, "
			+ " C.SITCOMPITSOL AS SITCOMPITCOT, "
			+ " C.SITAPROVITSOL AS SITAPROVITCOT, "
			+ " C.SITITSOL AS SITITCOT, "
			+ " C.MOTIVOCANCCOT, C.MOTIVOCOTABAIXO, "
			+ " F.RAZFOR, F.NOMEFOR, F.PESSOAFOR, F.CNPJFOR, F.INSCFOR, "
			+ " F.CPFFOR, F.RGFOR, F.FONEFOR, F.FAXFOR, F.CELFOR, "
			+ " F.EMAILFOR, F.OBSFOR "
			+ " FROM CPCOTACAO C, CPSOLICITACAO R, CPITSOLICITACAO I, "
			+ "    EQALMOX A, FNCC CC, EQPRODUTO P, CPFORNECED F "
			+ " WHERE R.CODEMP=? AND R.CODFILIAL=? AND R.CODSOL=?"
			+ " AND C.CODEMP=R.CODEMP AND C.CODFILIAL=R.CODFILIAL AND C.CODSOL=R.CODSOL "
			+ " AND C.SITAPROVITSOL <> 'NA' AND C.SITITSOL <> 'CA' "
			+ " AND R.SITSOL <> 'NA' AND R.SITSOL <> 'CA' "
			+ " AND F.CODEMP=C.CODEMPFR AND F.CODFILIAL=C.CODFILIALFR AND F.CODFOR=C.CODFOR "
			+ " AND I.CODEMP=R.CODEMP AND I.CODFILIAL=R.CODFILIAL AND I.CODSOL=R.CODSOL"
			+ " AND P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD"
			+ " AND I.CODEMP=R.CODEMP AND I.CODFILIAL=R.CODFILIAL "
			+ " AND CC.CODEMP=R.CODEMPCC AND CC.CODFILIAL=R.CODFILIALCC AND CC.CODCC=R.CODCC"
			+ " AND A.CODEMP=I.CODEMPAM AND A.CODFILIAL=I.CODFILIALAM AND A.CODALMOX=I.CODALMOX "
			+ " ORDER BY R.CODSOL,P." + dl.getValor() + ";";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(sSQL);

			ps.setInt(1, lcCampos.getCodEmp());
			ps.setInt(2, lcCampos.getCodFilial());
			ps.setInt(3, txtCodSolicitacao.getVlrInteger().intValue());

			rs = ps.executeQuery();

			imp.limpaPags();

			int codItSol = 0;

			while (rs.next()) {
				if (imp.pRow() >= (linPag - 1)) {
					imp.incPags();
					imp.eject();
				}
				if (imp.pRow() == 0) {
					imp.impCab(137, true);
					imp.say(imp.pRow() + 0, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|" + StringFunctions.replicate("=", 134) + "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "Sol. Comp. No.: ");
					imp.say(imp.pRow() + 0, 19, rs.getString("CODSOL"));
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "Requisitante: ");
					imp.say(imp.pRow() + 0, 19, rs.getString("IDUSU"));
					imp.say(imp.pRow() + 0, 30, "- C.C.: ");
					imp.say(imp.pRow() + 0, 38, (rs.getString("CODCC") != null ? rs
							.getString("CODCC").trim() : ""));
					imp.say(imp.pRow() + 0, 62, "-"
							+ (rs.getString("DESCCC") != null ? rs.getString("DESCCC").trim()
									: ""));
					imp.say(imp.pRow() + 0, 113, "- Data : ");
					imp.say(imp.pRow() + 0, 123, StringFunctions.sqlDateToStrDate(rs
							.getDate("DTINS")));
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, "Aprova��o   : ");
					imp.say(imp.pRow() + 0, 19, rs.getString("IDUSUAPROV"));
					imp.say(imp.pRow() + 0, 113, "- Data : ");
					imp.say(imp.pRow() + 0, 123, StringFunctions.sqlDateToStrDate(rs
							.getDate("DTAAPROVSOL")));
					imp.say(imp.pRow() + 0, 136, "|");

				}

				if (rs.getInt("CODITSOL") != codItSol)
				{
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|" + StringFunctions.replicate("=", 134) + "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 57, "DADOS DO(S) PRODUTO(S)");
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 2, "Item");
					imp.say(imp.pRow() + 0, 8, "Referencia");
					imp.say(imp.pRow() + 0, 22, "Descri��o dos produtos");
					imp.say(imp.pRow() + 0, 60, "Qtd.aprov.");
					imp.say(imp.pRow() + 0, 100, "Sit.item");
					imp.say(imp.pRow() + 0, 110, "Sit.aprov.");
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 136, "|");

					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");

					codItSol = rs.getInt("CODITSOL");
					imp.say(imp.pRow() + 0, 2, rs.getString("CODITSOL"));
					imp.say(imp.pRow() + 0, 8, rs.getString("REFPROD"));
					imp.say(imp.pRow() + 0, 22, rs.getString("DESCPROD").substring(0, 37));
					imp.say(imp.pRow() + 0, 60, "" + rs.getDouble("QTDAPROVITSOL"));
					imp.say(imp.pRow() + 0, 105, "" + rs.getString("SITITSOL"));
					imp.say(imp.pRow() + 0, 115, "" + rs.getString("SITAPROVITSOL"));
					imp.say(imp.pRow() + 0, 136, "|");

					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 136, "|");					

					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 57, "DADOS DO(S) FORNECEDOR(ES)");
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 136, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 2, "Cod.For.");
					imp.say(imp.pRow() + 0, 8, "Nome");					
					imp.say(imp.pRow() + 0, 60, "Fone");
					imp.say(imp.pRow() + 0, 78, "Fax");
					imp.say(imp.pRow() + 0, 96, "Cel.");
					imp.say(imp.pRow() + 0, 114, "Preco");					
					imp.say(imp.pRow() + 0, 127, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 136, "|");
				}

				if ((rs.getString("SITITCOT").equals("CA"))
						|| (rs.getString("SITAPROVITCOT").equals("NA"))) {
					vProdCan.addElement(rs.getString("NOMEFOR"));
					vPrecCan.addElement(rs.getString("PRECOCOT"));
					vQtdCan.addElement(rs.getString("QTDCOT"));
					vItem.addElement(rs.getString("CODITSOL"));
					vMotivoCan.addElement(rs.getString("MOTIVOCANCCOT") != null ? rs
							.getString("MOTIVOCANCCOT") : "");
					cont++;
				}

				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, imp.comprimido() + "|");
				imp.say(imp.pRow() + 0, 2, rs.getString("CODFOR"));
				imp.say(imp.pRow() + 0, 8, rs.getString("NOMEFOR"));
				imp.say(imp.pRow() + 0, 12, rs.getString("FONEFOR"));
				imp.say(imp.pRow() + 0, 28, rs.getString("FAXFOR"));
				imp.say(imp.pRow() + 0, 36, rs.getString("CELFOR"));
				imp.say(imp.pRow() + 0, 64, "" +  rs.getDouble("PRECOCOT"));
				imp.say(imp.pRow() + 0, 78, "|");

			}
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "|" + StringFunctions.replicate("=", 134) + "|");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 1, "|");
			imp.say(imp.pRow() + 0, 57, "INFORMA��ES ADICIONAIS");
			imp.say(imp.pRow() + 0, 136, "|");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 1, "|");
			imp.say(imp.pRow() + 0, 136, "|");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 1, "|");
			imp.say(imp.pRow() + 0, 2, "MOTIVO DA SOLICITA��O: ");
			String sMotivoRMA = (rs.getString("MOTIVOSOL") != null ? rs
					.getString("MOTIVOSOL") : "").trim();
			imp.say(imp.pRow() + 0, 26, sMotivoRMA.substring(0,
					sMotivoRMA.length() > 109 ? 109 : sMotivoRMA.length()));
			imp.say(imp.pRow() + 0, 136, "|");
			if (cont > 0) {
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "|");
				imp.say(imp.pRow() + 0, 4, "COTA��ES N�O APROVADAS:");
				imp.say(imp.pRow() + 0, 136, "|");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "|");
				imp.say(imp.pRow() + 0, 2, "Item");
				imp.say(imp.pRow() + 0, 19, "Nome");					
				imp.say(imp.pRow() + 0, 60, "Preco");
				imp.say(imp.pRow() + 0, 78, "Qtd.");
				imp.say(imp.pRow() + 0, 96, "Motivo");				
				imp.say(imp.pRow() + 0, 136, "|");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "|");
				imp.say(imp.pRow() + 0, 136, "|");
				for (int i = 0; vProdCan.size() > i; i++) {
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 2, vItem.elementAt(i).toString());
					imp.say(imp.pRow() + 0, 19, vProdCan.elementAt(i).toString());
					imp.say(imp.pRow() + 0, 60, vPrecCan.elementAt(i).toString());
					imp.say(imp.pRow() + 0, 78, vProdCan.elementAt(i).toString());
					String sMotivoCanc = vMotivoCan.elementAt(i).toString();

					imp.say(imp.pRow() + 0, 96, "- "
							+ sMotivoCanc.substring(0, sMotivoCanc.length() > 108 ? 108
									: sMotivoCanc.length()));
					imp.say(imp.pRow() + 0, 136, "|");
				}
			}

			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "+" + StringFunctions.replicate("=", 134) + "+");
			imp.say(imp.pRow() + 2, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 52, StringFunctions.replicate("_", 41));
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 62, "Ass. do funcion�rio");

			imp.eject();

			imp.fechaGravacao();

			con.commit();
			dl.dispose();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao consultar a tabela de Cota��es!"
					+ err.getMessage(), true, con, err);
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}

	private boolean comRef() {
		return bPrefs[0];
	}

	public void keyTyped(KeyEvent kevt) {
		super.keyTyped(kevt);
	}

	public void keyReleased(KeyEvent kevt) {
		super.keyReleased(kevt);
	}

	public void beforePost(PostEvent pevt) {
		if (pevt.getListaCampos() == lcCotacao) {
			if (txtQtdAprovCot.getVlrDouble().doubleValue() > txtQtdCot
					.getVlrDouble().doubleValue()) {
				Funcoes.mensagemInforma(null,
				"Quantidade aprovada maior que a cotada!");
				pevt.getListaCampos().cancelPost();
			}
			if (txtSituacaoIt.getVlrString().equals("")) {
				txtSituacaoIt.setVlrString("PE");
			}
			if (txtSituacaoItAprov.getVlrString().equals("")) {
				txtSituacaoItAprov.setVlrString("PE");
			}
			if (txtSituacaoItComp.getVlrString().equals("")) {
				txtSituacaoItComp.setVlrString("PE");
			}
			if (txtQtdItAprovado.getVlrString().equals("")) {
				txtQtdItAprovado.setVlrDouble(new Double(0));
			}
		} else if (pevt.getListaCampos() == lcCampos) {
			txtOrigSolicitacao.setVlrString("AX");
			if (txtStatusSolicitacao.getVlrString().equals("")) {
				txtStatusSolicitacao.setVlrString("PE");
			}
			if (txtSituacaoItAprov.getVlrString().equals("")) {
				txtSituacaoItAprov.setVlrString("PE");
			}
			if (txtSituacaoItComp.getVlrString().equals("")) {
				txtSituacaoItComp.setVlrString("PE");
			}
		}
	}

	public void beforeInsert(InsertEvent ievt) {}

	public void afterInsert(InsertEvent ievt) {
		if (ievt.getListaCampos() == lcCotacao) {
			txtDtCot.setVlrDate(new Date());
			txtQtdCot.setVlrDouble(txtQtdItAprovado.getVlrDouble());
			txtIdUsuCot.setVlrString(Aplicativo.strUsuario);
			if (comRef()) {
				txtRefProd2.setVlrString(txtRefProd.getVlrString());
				lcProd4.carregaDados();
			} else {
				txtCodProd2.setVlrString(txtCodProd.getVlrString());
				lcProd3.carregaDados();
			}

			txtQtdAprovCot.setVlrDouble(new Double(0.0));
		}
	}

	public void exec(int iCodCompra) {
		txtCodSolicitacao.setVlrString(iCodCompra + "");
		lcCampos.carregaDados();
	}

	public void execDev(int iCodFor, int iCodTipoMov, String sSerie, int iDoc) {
		lcCampos.insert(true);
	}

	private int buscaVlrPadrao() {
		int iRet = 0;
		String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				iRet = rs.getInt("ANOCENTROCUSTO");
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Erro ao buscar o ano-base para o centro de custo.\n"
					+ err.getMessage());
		}

		return iRet;
	}

	public void setConexao(DbConnection cn) {
		super.setConexao(cn);
		bPrefs = prefs();
		montaDetalhe();

		lcUnid.setConexao(cn);
		lcCotacao.setConexao(cn);
		lcProd.setConexao(cn);
		lcProd2.setConexao(cn);
		lcProd3.setConexao(cn);
		lcProd4.setConexao(cn);
		lcCC.setConexao(cn);
		lcCC.setWhereAdic("NIVELCC=10 AND ANOCC=" + buscaVlrPadrao());
		lcUsu.setConexao(cn);
		lcFor.setConexao(cn);
		String sSQL = "SELECT anoCC, codCC, codAlmox, aprovCPSolicitacaoUsu FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? AND IDUsu=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			ps.setString(3, Aplicativo.strUsuario);
			rs = ps.executeQuery();
			if (rs.next()) {
				anoCC = new Integer(rs.getInt("anoCC"));
				if (anoCC.intValue() == 0)
					anoCC = new Integer(buscaVlrPadrao());
				codCC = rs.getString("codCC");
			}

			con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage());
		}

		carregaWhereAdic();
	}

	public Color cor(int r, int g, int b) {
		Color color = new Color(r, g, b);
		return color;
	}
}