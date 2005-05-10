/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FOrcamento.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para
 * Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela de or�amento, tela respons�vel pela inser��o e edi��o de or�amentos por
 * cliente <BR>
 * diferente da tela de or�amento do atendimento que � por conveniado. <BR>
 *  
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import javax.swing.JOptionPane;
import org.freedom.componentes.JPanelPad;

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
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.layout.LeiauteGR;
import org.freedom.modulos.atd.DLDescontItOrc;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FObservacao;
import org.freedom.telas.FPrinterJob;

public class FOrcamento extends FVD implements PostListener, CarregaListener,
		FocusListener, ActionListener, InsertListener, DeleteListener {
	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JPanelPad pinTot = new JPanelPad(200, 200);

	private JPanelPad pnTot = new JPanelPad(JPanelPad.TP_JPANEL,
			new GridLayout(1, 1));

	private JPanelPad pnCenter = new JPanelPad(JPanelPad.TP_JPANEL,
			new BorderLayout());

	private JButton btObs = new JButton(Icone.novo("btObs.gif"));

	private JButton btOrc = new JButton(Icone.novo("btImprimeOrc.gif"));

	private JButton btFechaOrc = new JButton(Icone.novo("btOk.gif"));

	private JTextFieldPad txtCodOrc = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtDtOrc = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);

	private JTextFieldPad txtDtVencOrc = new JTextFieldPad(
			JTextFieldPad.TP_DATE, 10, 0);

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodCli = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtEstCli = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);

	private JTextFieldPad txtCodItOrc = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtQtdItOrc = new JTextFieldPad(
			JTextFieldPad.TP_NUMERIC, 15, 2);

	private JTextFieldPad txtCodProd = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtRefProd = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 13, 0);

	private JTextFieldPad txtCodBarras = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 13, 0);

	private JTextFieldPad txtPrecoItOrc = new JTextFieldPad(
			JTextFieldPad.TP_NUMERIC, 15, 2);

	private JTextFieldPad txtPercDescItOrc = new JTextFieldPad(
			JTextFieldPad.TP_NUMERIC, 6, 2);

	private JTextFieldPad txtVlrDescItOrc = new JTextFieldPad(
			JTextFieldPad.TP_NUMERIC, 15, 2);

	private JTextFieldPad txtVlrLiqItOrc = new JTextFieldPad(
			JTextFieldPad.TP_NUMERIC, 15, 2);

	private JTextFieldPad txtVlrEdDescOrc = new JTextFieldPad(
			JTextFieldPad.TP_NUMERIC, 15, 2);

	private JTextFieldPad txtVlrEdAdicOrc = new JTextFieldPad(
			JTextFieldPad.TP_NUMERIC, 15, 2);

	private JTextFieldPad txtVlrDescOrc = new JTextFieldPad(
			JTextFieldPad.TP_NUMERIC, 15, 2);

	private JTextFieldPad txtVlrAdicOrc = new JTextFieldPad(
			JTextFieldPad.TP_NUMERIC, 15, 2);

	private JTextFieldPad txtVlrLiqOrc = new JTextFieldPad(
			JTextFieldPad.TP_NUMERIC, 15, 2);

	private JTextFieldPad txtVlrProdItOrc = new JTextFieldPad(
			JTextFieldPad.TP_NUMERIC, 15, 2);

	private JTextFieldPad txtStrDescItOrc = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 500, 0);

	private JTextFieldPad txtVlrProdOrc = new JTextFieldPad(
			JTextFieldPad.TP_NUMERIC, 15, 2);

	private JTextFieldPad txtStatusOrc = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 2, 0);

	private JTextFieldPad txtCodTpCli = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodVend = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtPrazoEntOrc = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtNomeVend = new JTextFieldFK(
			JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,
			50, 0);

	private JTextFieldFK txtDescProd = new JTextFieldFK(
			JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(
			JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK(
			JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtCodAlmoxItOrc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,
			5, 0);
	
//	private JTextFieldFK txtDescAlmox = new JTextFieldFK(
	//		JTextFieldPad.TP_STRING, 50, 0);
	
	private JTextAreaPad txaObsItOrc = new JTextAreaPad(500);

	private ListaCampos lcCli = new ListaCampos(this, "CL");

	private ListaCampos lcProd = new ListaCampos(this, "PD");

	private ListaCampos lcProd2 = new ListaCampos(this, "PD");

	private ListaCampos lcOrc2 = new ListaCampos(this);

	private ListaCampos lcPlanoPag = new ListaCampos(this, "PG");

	private ListaCampos lcVend = new ListaCampos(this, "VD");

	private ListaCampos lcTipoCli = new ListaCampos(this, "TC");
	
	private ListaCampos lcAlmox = new ListaCampos(this,"AX");

	private JButton btExp = new JButton(Icone.novo("btExportar.gif"));

	private FPrinterJob dl = null;

	Object[] oPrefs = null;

	boolean bCtrl = false;

	Vector vParamOrc = new Vector();

	public FOrcamento() {
		setTitulo("Or�amento");
		setAtribos(15, 10, 765, 460);

		txtDescProd.setToolTipText("Clique aqui duas vezes para alterar a descri��o.");
		txtDescProd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mevt) {
				if (mevt.getClickCount() == 2)
					mostraTelaDecricao();
			}
		});
		setImprimir(true);
	}

	private void montaOrcamento() {
		oPrefs = prefs(); //Carrega as prefer�ncias

		pnMaster.remove(2); //Remove o JPanelPad pr�definido da class FDados
		pnGImp.removeAll(); //Remove os bot�es de impress�o para adicionar logo
		// embaixo
		pnGImp.setLayout(new GridLayout(1, 5)); //redimensiona o painel de
												// impress�o
		pnGImp.setPreferredSize(new Dimension(210, 26));
		pnGImp.add(btPrevimp);
		pnGImp.add(btImp);
		pnGImp.add(btFechaOrc);
		pnGImp.add(btObs);//Agora o painel est� maior
		pnGImp.add(btOrc);//Bot�o provis�rio para emiss�o de or�amento padr�o

		pnTot.setPreferredSize(new Dimension(120, 200)); //JPanelPad de Totais
		pnTot.add(pinTot);
		pnCenter.add(pnTot, BorderLayout.EAST);
		pnCenter.add(spTab, BorderLayout.CENTER);

		JPanelPad pnLab = new JPanelPad(JPanelPad.TP_JPANEL, new GridLayout(1,
				1));
		pnLab.add(new JLabelPad(" Totais:")); //Label do painel de totais

		pnMaster.add(pnCenter, BorderLayout.CENTER);

		lcTipoCli.add(new GuardaCampo(txtCodTpCli, "CodTipoCli", "C�d.cli.",
				ListaCampos.DB_PK, false));
		lcTipoCli.add(new GuardaCampo(txtDescTipoCli, "DescTipoCli",
				"Raz�o social do cliente", ListaCampos.DB_SI, false));
		txtCodTpCli.setTabelaExterna(lcTipoCli);
		txtDescTipoCli.setListaCampos(lcTipoCli);
		lcTipoCli.montaSql(false, "TIPOCli", "VD");
		lcTipoCli.setQueryCommit(false);
		lcTipoCli.setReadOnly(true);

		lcPlanoPag.add(new GuardaCampo(txtCodPlanoPag, "CodPlanoPag",
				"C�d.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag.add(new GuardaCampo(txtDescPlanoPag, "DescPlanoPag",
				"Descri��o do plano de pagamento", ListaCampos.DB_SI, false));
		txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
		txtDescPlanoPag.setListaCampos(lcPlanoPag);
		lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag.setQueryCommit(false);
		lcPlanoPag.setReadOnly(true);

		lcVend.add(new GuardaCampo(txtCodVend, "CodVend", "C�d.comiss.",
				ListaCampos.DB_PK, false));
		lcVend.add(new GuardaCampo(txtNomeVend, "NomeVend",
				"Nome do comissionado", ListaCampos.DB_SI, false));
		txtCodVend.setTabelaExterna(lcVend);
		txtNomeVend.setListaCampos(lcVend);
		lcVend.montaSql(false, "VENDEDOR", "VD");
		lcVend.setQueryCommit(false);
		lcVend.setReadOnly(true);

		//FK Cliente
		lcCli.add(new GuardaCampo(txtCodCli, "CodCli", "C�d.cli.",
				ListaCampos.DB_PK, false));
		lcCli.add(new GuardaCampo(txtRazCli, "RazCli",
				"Raz�o social do cliente", ListaCampos.DB_SI, false));
		lcCli.add(new GuardaCampo(txtCodPlanoPag, "CodPlanoPag", "C�d.p.pg.",
				ListaCampos.DB_SI, false));
		lcCli.add(new GuardaCampo(txtCodVend, "CodVend", "C�d.comiss.",
				ListaCampos.DB_SI, false));
		lcCli.add(new GuardaCampo(txtEstCli, "UfCli", "UF", ListaCampos.DB_SI,
				false));
		//lcCli.setWhereAdic("ATIVOCLI='S'");
		lcCli.montaSql(false, "CLIENTE", "VD");
		lcCli.setQueryCommit(false);
		lcCli.setReadOnly(true);
		txtCodCli.setTabelaExterna(lcCli);
		
		//FK de Almoxarifado

		lcAlmox.add(new GuardaCampo(txtCodAlmoxItOrc, "codalmox", "Cod.Almox.",
				ListaCampos.DB_PK, false));

		lcAlmox.montaSql(false, "ALMOX", "EQ");
		lcAlmox.setQueryCommit(false);
		lcAlmox.setReadOnly(true);
		txtCodAlmoxItOrc.setTabelaExterna(lcAlmox);
		
		
		//FK Produto
		lcProd.add(new GuardaCampo(txtCodProd, "CodProd", "C�d.prod.",
				ListaCampos.DB_PK, txtDescProd, false));
		lcProd.add(new GuardaCampo(txtDescProd, "DescProd",
				"Descri��o do produto", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtRefProd, "RefProd",
				"Refer�ncia do produto", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtCodBarras, "CodBarProd",
				"C�digo de barras", ListaCampos.DB_SI, false));
		lcProd.setWhereAdic("ATIVOPROD='S'");
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setQueryCommit(false);
		lcProd.setReadOnly(true);
		txtCodProd.setTabelaExterna(lcProd);

		//FK do produto (*Somente em caso de refer�ncias este listaCampos
		//Trabalha como gatilho para o listaCampos de produtos, assim
		//carregando o c�digo do produto que ser� armazenado no Banco)
		lcProd2.add(new GuardaCampo(txtRefProd, "RefProd", "Ref.prod.",
				ListaCampos.DB_PK, txtDescProd, false));
		lcProd2.add(new GuardaCampo(txtDescProd, "DescProd",
				"Descri��o do produto", ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCodProd, "CodProd", "C�d.prod.",
				ListaCampos.DB_SI, false));
		txtRefProd.setNomeCampo("RefProd");
		txtRefProd.setListaCampos(lcDet);
		lcProd2.setWhereAdic("ATIVOPROD='S'");
		lcProd2.montaSql(false, "PRODUTO", "EQ");
		lcProd2.setQueryCommit(false);
		lcProd2.setReadOnly(true);
		txtRefProd.setTabelaExterna(lcProd2);

		//ListaCampos de Totais (� acionada pelo listaCampos de Orcamento)

		lcOrc2.add(new GuardaCampo(txtCodOrc, "CodOrc", "C�d.Or�.",
				ListaCampos.DB_PK, false));
		lcOrc2.add(new GuardaCampo(txtVlrDescOrc, "VlrDescOrc", "Vlr.desc.",
				ListaCampos.DB_SI, false));
		lcOrc2.add(new GuardaCampo(txtVlrAdicOrc, "VlrAdicOrc", "Vlr.adic.",
				ListaCampos.DB_SI, false));
		lcOrc2.add(new GuardaCampo(txtVlrLiqOrc, "VlrLiqOrc", "Vlr.total",
				ListaCampos.DB_SI, false));
		lcOrc2.add(new GuardaCampo(txtVlrProdOrc, "VlrProdOrc", "Vlr.parcial",
				ListaCampos.DB_SI, false));
		lcOrc2.montaSql(false, "ORCAMENTO", "VD");
		lcOrc2.setQueryCommit(false);
		lcOrc2.setReadOnly(true);

		//Coloca os coment�rio nos bot�es

		btFechaOrc.setToolTipText("Completar o Or�amento (F4)");
		btObs.setToolTipText("Observa��es (Ctrl + O)");
		btOrc.setToolTipText("Imprime or�amento padr�o");

		//Desativa as os TextFields para que os usu�rios n�o fussem

		txtVlrDescOrc.setAtivo(false);
		txtVlrAdicOrc.setAtivo(false);
		txtVlrLiqOrc.setAtivo(false);

		//Adiciona os componentes na tela e no ListaCompos da orcamento
		pinCab = new JPanelPad(740, 180);
		setListaCampos(lcCampos);
		setAltCab(130);
		setPainel(pinCab, pnCliCab);
		adicCampo(txtCodOrc, 7, 20, 90, 20, "CodOrc", "N� or�amento",
				ListaCampos.DB_PK, true);
		adicCampo(txtDtOrc, 100, 20, 87, 20, "DtOrc", "Data",
				ListaCampos.DB_SI, true);
		adicCampo(txtCodCli, 190, 20, 77, 20, "CodCli", "C�d.cli.",
				ListaCampos.DB_FK, txtRazCli, true);
		adicDescFK(txtRazCli, 270, 20, 277, 20, "RazCli",
				"Raz�o social do cliente");
		adicCampo(txtDtVencOrc, 550, 20, 87, 20, "DtVencOrc", "Dt.valid.",
				ListaCampos.DB_SI, true);
		adicCampo(txtPrazoEntOrc, 640, 20, 100, 20, "PrazoEntOrc",
				"Dias p/ entrega", ListaCampos.DB_SI, false);
		adicCampo(txtCodVend, 7, 60, 80, 20, "CodVend", "C�d.comiss.",
				ListaCampos.DB_FK, txtNomeVend, true);
		adicDescFK(txtNomeVend, 90, 60, 207, 20, "NomeVend",
				"Nome do comissionado");
		adicDescFK(txtDescTipoCli, 300, 60, 117, 20, "DescTipoCli",
				"Descri��o do tipo de cliente");
		adicCampo(txtCodPlanoPag, 420, 60, 77, 20, "CodPlanoPag", "C�d.p.pg.",
				ListaCampos.DB_FK, txtDescPlanoPag, true);
		adicDescFK(txtDescPlanoPag, 500, 60, 240, 20, "DescPlanoPag",
				"Descri��o do plano de pagamento");
		adicCampoInvisivel(txtVlrEdDescOrc, "VlrDescOrc", "Vlr.desc.",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtVlrEdAdicOrc, "VlrAdicOrc", "Vlr.adic.",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtStatusOrc, "StatusOrc", "Status",
				ListaCampos.DB_SI, false);
		setListaCampos(true, "ORCAMENTO", "VD");

		//pnRodape.add(btExp);
		btExp.setPreferredSize(new Dimension(30, 30));
		pnNavCab.add(btExp, BorderLayout.EAST);

		//adic(btExp, 633, 50, 30, 30);

		txtVlrLiqItOrc.setAtivo(false);

		//Adiciona os Listeners
		btFechaOrc.addActionListener(this);
		btObs.addActionListener(this);
		btOrc.addActionListener(this);

		txtRefProd.addKeyListener(this);
		txtPercDescItOrc.addFocusListener(this);
		txtVlrDescItOrc.addFocusListener(this);
		txtQtdItOrc.addFocusListener(this);
		txtPrecoItOrc.addFocusListener(this);
		lcCampos.addPostListener(this);
		lcCampos.addCarregaListener(this);
		lcProd2.addCarregaListener(this);
		lcDet.addPostListener(this);
		lcDet.addCarregaListener(this);
		lcCampos.addInsertListener(this);
		lcDet.addDeleteListener(this);

		btExp.addActionListener(this);
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcProd.addCarregaListener(this);
		lcProd2.addCarregaListener(this);

	}

	//Fun��o criada para montar a tela conforme a prefer�ncia do usu�rio:
	//com ou sem Refer�ncia sendo PK;
	private void montaDetalhe() {
		setAltDet(60);
		pinDet = new JPanelPad(740, 100);
		setPainel(pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);
		adicCampo(txtCodItOrc, 7, 20, 30, 20, "CodItOrc", "Item",
				ListaCampos.DB_PK, true);
		if (((Boolean) oPrefs[0]).booleanValue()) {
			adicCampoInvisivel(txtCodProd, "CodProd", "C�d.prod.",
					ListaCampos.DB_FK, txtDescProd, false);
			adicCampoInvisivel(txtRefProd, "RefProd", "Ref.prod.",
					ListaCampos.DB_FK, false);
			adic(new JLabelPad("Refer�ncia"), 40, 0, 67, 20);
			adic(txtRefProd, 40, 20, 67, 20);
			txtRefProd.setFK(true);
			txtRefProd.setBuscaAdic(new DLBuscaProd(con, "REFPROD",lcProd2.getWhereAdic()));
		} else {
			adicCampo(txtCodProd, 40, 20, 67, 20, "CodProd", "C�d.prod.",
					ListaCampos.DB_FK, txtDescProd, true);
			txtCodProd.setBuscaAdic(new DLBuscaProd(con, "CODPROD",lcProd.getWhereAdic()));
		}
		adicDescFK(txtDescProd, 110, 20, 227, 20, "DescProd",
				"Descri��o do produto");
		adicCampo(txtQtdItOrc, 340, 20, 47, 20, "QtdItOrc", "Qtd.",
				ListaCampos.DB_SI, true);

		adicCampoInvisivel(txtCodAlmoxItOrc, "codalmox", "Cod.Almox",	ListaCampos.DB_FK, false);
		
		txtQtdItOrc.setBuscaAdic(new DLBuscaEstoq(lcDet, lcAlmox,lcProd,con,"qtditvenda"));
				
		adicCampo(txtPrecoItOrc, 390, 20, 67, 20, "PrecoItOrc", "Pre�o",
				ListaCampos.DB_SI, true);
		adicCampo(txtPercDescItOrc, 460, 20, 57, 20, "PercDescItOrc",
				"% desc.", ListaCampos.DB_SI, false);
		adicCampo(txtVlrDescItOrc, 520, 20, 80, 20, "VlrDescItOrc",
				"Valor desc.", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtVlrProdItOrc, "VlrProdItOrc", "Valor bruto",
				ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtStrDescItOrc, "StrDescItOrc", "Descontos",
				ListaCampos.DB_SI, false);
		adicDBLiv(txaObsItOrc, "ObsItOrc", "Observa��o", false);
		adicCampo(txtVlrLiqItOrc, 603, 20, 80, 20, "VlrLiqItOrc", "Valor item",
				ListaCampos.DB_SI, false);
		pinTot.adic(new JLabelPad("Total desc."), 7, 0, 90, 20);
		pinTot.adic(txtVlrDescOrc, 7, 20, 100, 20);
		pinTot.adic(new JLabelPad("Total adic."), 7, 40, 90, 20);
		pinTot.adic(txtVlrAdicOrc, 7, 60, 100, 20);
		pinTot.adic(new JLabelPad("Total geral"), 7, 80, 90, 20);
		pinTot.adic(txtVlrLiqOrc, 7, 100, 100, 20);

		setListaCampos(true, "ITORCAMENTO", "VD");
		montaTab();

		tab.setAutoRol(true);

		tab.setTamColuna(30, 0);
		tab.setTamColuna(70, 1);
		tab.setTamColuna(230, 2);
		tab.setTamColuna(60, 3);
		tab.setTamColuna(70, 4);
		tab.setTamColuna(60, 5);
		tab.setTamColuna(70, 6);
		tab.setTamColuna(90, 7);
	}

	private void calcTot() {
		BigDecimal bVlrProd = txtVlrProdItOrc.getVlrBigDecimal().subtract(
				txtVlrDescItOrc.getVlrBigDecimal()).divide(new BigDecimal("1"),
				3, BigDecimal.ROUND_HALF_UP);
		txtVlrLiqItOrc.setVlrBigDecimal(bVlrProd);
	}

	private void calcVlrProd() {
		BigDecimal bPreco = txtPrecoItOrc.getVlrBigDecimal();
		BigDecimal bQtd = txtQtdItOrc.getVlrBigDecimal();
		txtVlrProdItOrc.setVlrBigDecimal(bPreco.multiply(bQtd).divide(
				new BigDecimal("1"), 3, BigDecimal.ROUND_HALF_UP));
	}

	private void mostraTelaDescont() {
		String sObsDesc = "";
		int iFim = 0;
		if ((lcDet.getStatus() == ListaCampos.LCS_INSERT)
				|| (lcDet.getStatus() == ListaCampos.LCS_EDIT)) {
			txtVlrDescItOrc.setAtivo(true);
			txtPercDescItOrc.setAtivo(false);
			txtPercDescItOrc.setVlrString("");
			txtVlrDescItOrc.setVlrString("");
			calcVlrProd();
			calcTot();
			DLDescontItOrc dl = new DLDescontItOrc(this, txtPrecoItOrc
					.getVlrDouble().doubleValue(), parseDescs());
			dl.setVisible(true);
			txtVlrDescItOrc.setAtivo(true);
			txtPercDescItOrc.setAtivo(false);
			txtPercDescItOrc.setVlrString("");
			if (dl.OK) {
				txtVlrDescItOrc.setVlrBigDecimal(new BigDecimal(dl.getValor()
						* txtQtdItOrc.getVlrDouble().doubleValue()));
				sObsDesc = txtStrDescItOrc.getText();
				iFim = sObsDesc.indexOf("\n");
				if (iFim >= 0)
					sObsDesc = dl.getObs() + " " + sObsDesc.substring(iFim);
				else
					sObsDesc = dl.getObs() + " \n";
				txtStrDescItOrc.setVlrString(sObsDesc);
			}
			dl.dispose();
			calcVlrProd();
			calcTot();
			txtVlrDescItOrc.requestFocus(true);
		}
	}

	private String[] parseDescs() {
		String[] sRet = new String[5];
		String sObs = txtStrDescItOrc.getText();
		int iFim = sObs.indexOf('\n');
		int iPos = 0;
		//        System.out.println("1 :"+sObs);
		if (iFim > 0) {
			sObs = sObs.substring(0, iFim);
			//              System.out.println("2 :"+sObs);
			if (sObs.indexOf("Desc.: ") == 0) {
				sObs = sObs.substring(7);
				//                    System.out.println("3 :"+sObs);
				iPos = sObs.indexOf('+');
				for (int i = 0; (iPos > 0) && (i < 5); i++) {
					sRet[i] = sObs.substring(0, iPos - 1);
					if (iPos != iFim)
						sObs = sObs.substring(iPos + 1);
					//                          System.out.println("4 :"+sObs);
					if (iPos == iFim)
						break;
					if ((iPos = sObs.indexOf('+')) == -1)
						iPos = iFim = sObs.length();
				}
			}
		}
		return sRet;
	}

	private void mostraTelaDecricao() {
		if (txtCodProd.getVlrString().equals(""))
			return;
		String sDesc = txaObsItOrc.getVlrString();
		if (sDesc.equals(""))
			sDesc = buscaDescComp();
		if (sDesc.equals(""))
			sDesc = txtDescProd.getVlrString();

		FObservacao obs = new FObservacao("Descri��o completa", sDesc, 500);
		obs.setSize(400, 200);
		obs.setVisible(true);
		if (obs.OK) {
			txaObsItOrc.setVlrString(obs.getTexto());
			lcDet.edit();
		}
		obs.dispose();
	}

	private String buscaDescComp() {
		String sRet = "";
		String sSQL = "SELECT DESCCOMPPROD FROM EQPRODUTO WHERE CODPROD=?"
				+ " AND CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, txtCodProd.getVlrInteger().intValue());
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setInt(3, lcCampos.getCodFilial());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				sRet = rs.getString("DescCompProd");
			}

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar descri��o completa!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		}
		return sRet != null ? sRet : "";
	}

	private void testaCodOrc() { //Traz o verdadeiro n�mero do codorcamento
		// atrav�s do generator do banco
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM SPGERANUM(?,?,?)");
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("VDORCAMENTO"));
			ps.setString(3, "OC");
			rs = ps.executeQuery();
			rs.next();
			txtCodOrc.setVlrString(rs.getString(1));
			//		rs.close();
			//		ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Erro ao confirmar c�digo da orcamento!\n"
							+ err.getMessage(),true,con,err);
		}
	}

	public void focusGained(FocusEvent fevt) {
	}

	public void focusLost(FocusEvent fevt) {
		if (fevt.getSource() == txtPercDescItOrc) {
			if (txtPercDescItOrc.getText().trim().length() < 1) {
				txtVlrDescItOrc.setAtivo(true);
			} else {
				txtVlrDescItOrc.setVlrBigDecimal(txtVlrProdItOrc
						.getVlrBigDecimal().multiply(
								txtPercDescItOrc.getVlrBigDecimal()).divide(
								new BigDecimal("100"), 3,
								BigDecimal.ROUND_HALF_UP));
				calcVlrProd();
				calcTot();
				txtVlrDescItOrc.setAtivo(false);
			}
		} else if (fevt.getSource() == txtVlrDescItOrc) {
			if (txtVlrDescItOrc.getText().trim().length() < 1) {
				txtPercDescItOrc.setAtivo(true);
			} else if (txtVlrDescItOrc.getAtivo()) {
				txtPercDescItOrc.setAtivo(false);
			}
		} else if ((fevt.getSource() == txtQtdItOrc)
				| (fevt.getSource() == txtPrecoItOrc)) {
			calcVlrProd();
			calcTot();
		}
	}

	public void beforeCarrega(CarregaEvent cevt) {
		if (cevt.getListaCampos() == lcProd2)
			lcProd.edit();
	}

	public void afterPost(PostEvent pevt) {
		lcOrc2.carregaDados(); //Carrega os Totais
	}

	public void afterCarrega(CarregaEvent cevt) {
		if (cevt.getListaCampos() == lcDet) {
			lcOrc2.carregaDados();//Carrega os Totais
		} else if ((cevt.getListaCampos() == lcProd)
				|| (cevt.getListaCampos() == lcProd2)) {
			if (lcDet.getStatus() == ListaCampos.LCS_INSERT) {
				buscaPreco();
			}
		} else if (cevt.getListaCampos() == lcCampos) {
			String s = txtCodOrc.getVlrString();
			lcOrc2.carregaDados();//Carrega os Totais
			txtCodOrc.setVlrString(s);
		}
	}

	public void keyPressed(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_CONTROL) {
			bCtrl = true;
		} else if (kevt.getKeyCode() == KeyEvent.VK_O) {
			if (bCtrl) {
				btObs.doClick();
			}
		} else if (kevt.getKeyCode() == KeyEvent.VK_F4) {
			btFechaOrc.doClick();
		} else if (kevt.getKeyCode() == KeyEvent.VK_F3) {
			if (kevt.getSource() == txtPercDescItOrc
					|| kevt.getSource() == txtVlrDescItOrc)
				mostraTelaDescont();
		}
		if (kevt.getSource() == txtRefProd)
			lcDet.edit();
		super.keyPressed(kevt);
	}

	public void actionPerformed(ActionEvent evt) {
		Object[] bValores = null;
		if (evt.getSource() == btFechaOrc) {
			DLCompOrc dl = new DLCompOrc(this, (txtVlrDescItOrc
					.getVlrBigDecimal().intValue() > 0), txtVlrProdOrc
					.getVlrBigDecimal(), txtVlrEdDescOrc.getVlrBigDecimal(),
					txtVlrEdAdicOrc.getVlrBigDecimal(), txtCodPlanoPag,
					txtDescPlanoPag);

			dl.setVisible(true);
			if (dl.OK) {
				bValores = dl.getValores();
				dl.dispose();
			} else {
				dl.dispose();
			}
			if (bValores != null) {
				lcCampos.edit();
				txtVlrEdDescOrc.setVlrBigDecimal((BigDecimal) bValores[0]);
				txtVlrEdAdicOrc.setVlrBigDecimal((BigDecimal) bValores[1]);

				// Ajusta o status para OC - or�amento completo.
				txtStatusOrc.setVlrString("OC");
				lcCampos.post();
				lcCampos.carregaDados();

				if (bValores[2].equals("S")) {
					imprimir(true);
				}
			}
		} else if (evt.getSource() == btPrevimp)
			imprimir(true);
		else if (evt.getSource() == btImp)
			imprimir(false);
		else if (evt.getSource() == btOrc) {
			ImprimeOrc imp = new ImprimeOrc(txtCodOrc.getVlrInteger()
					.intValue());
			imp.setConexao(con);
			dl = new FPrinterJob(imp, this);
			dl.setVisible(true);
		} else if (evt.getSource() == btObs) {
			FObservacao obs = null;
			try {
				PreparedStatement ps = con
						.prepareStatement("SELECT OBSORC FROM VDORCAMENTO WHERE CODORC=?");
				ps.setInt(1, txtCodOrc.getVlrInteger().intValue());
				ResultSet rs = ps.executeQuery();
				if (rs.next())
					obs = new FObservacao((rs.getString("ObsOrc") != null ? rs
							.getString("ObsOrc") : ""));
				else
					obs = new FObservacao("");
				//		  rs.close();
				//		  ps.close();
				if (!con.getAutoCommit())
					con.commit();
			} catch (SQLException err) {
				Funcoes.mensagemErro(this, "Erro ao carregar a observa��o!\n"
						+ err.getMessage(),true,con,err);
			}
			if (obs != null) {
				obs.setSize(400, 200);
				obs.setVisible(true);
				if (obs.OK) {
					try {
						PreparedStatement ps = con
								.prepareStatement("UPDATE VDORCAMENTO SET OBSORC=? WHERE CODORC=?");
						ps.setString(1, obs.getTexto());
						ps.setInt(2, txtCodOrc.getVlrInteger().intValue());
						ps.executeUpdate();
						if (!con.getAutoCommit())
							con.commit();
					} catch (SQLException err) {
						Funcoes.mensagemErro(this,
								"Erro ao inserir observa��o no or�amento!\n"
										+ err.getMessage(),true,con,err);
					}
				}
				obs.dispose();
			}
		} else if (evt.getSource() == btExp)
			exportar();
		super.actionPerformed(evt);
	}

	private void exportar() {
		if (txtCodOrc.getVlrInteger().intValue() == 0
				|| lcCampos.getStatus() != ListaCampos.LCS_SELECT) {
			Funcoes.mensagemInforma(this,
					"Selecione um or�amento cadastrado antes!");
			return;
		}
		DLCopiaOrc dl = new DLCopiaOrc(this);
		dl.setConexao(con);
		dl.setVisible(true);
		if (!dl.OK) {
			dl.dispose();
			return;
		}
		int[] iVals = dl.getValores();
		dl.dispose();
		String sSQL = "SELECT IRET FROM VDCOPIAORCSP(?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, lcCampos.getCodFilial());
			ps.setInt(3, txtCodOrc.getVlrInteger().intValue());
			ps.setInt(4, iVals[1]);
			ps.setInt(5, iVals[0]);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (Funcoes.mensagemConfirma(this, "Or�amento '" + rs.getInt(1)
						+ "' criado com sucesso!\n"
						+ "Gostaria de edita-lo agora?") == JOptionPane.OK_OPTION) {
					txtCodOrc.setVlrInteger(new Integer(rs.getInt(1)));
					lcCampos.carregaDados();
				}
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao copiar o or�amento!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		}
		dl.dispose();
	}

	private void imprimir(boolean bVisualizar) {
		String sClassOrc = "";
		String sSql = "SELECT CLASSORC FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		LeiauteGR leiOrc = null;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sSql);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getString("CLASSORC") != null) {
					sClassOrc = rs.getString("CLASSORC").trim();
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Erro ao carregar a tabela SGPREFERE1!\n"
							+ err.getMessage(),true,con,err);
		}
		if (sClassOrc.trim().equals("")) {
			ImprimeOrc imp = new ImprimeOrc(txtCodOrc.getVlrInteger()
					.intValue());
			imp.setConexao(con);
			if (bVisualizar) {
				dl = new FPrinterJob(imp, this);
				dl.setVisible(true);
			} else
				imp.imprimir(true);
		} else {
			try {
				leiOrc = (LeiauteGR) Class.forName(
						"org.freedom.layout." + sClassOrc).newInstance();
				leiOrc.setConexao(con);
				vParamOrc.clear();
				vParamOrc.addElement(txtCodOrc.getText());
				vParamOrc.addElement(txtCodCli.getText());
				leiOrc.setParam(vParamOrc);
				if (bVisualizar)
					dl = new FPrinterJob(leiOrc, this);
				else
					leiOrc.imprimir(true);
			} catch (Exception err) {
				Funcoes.mensagemInforma(this,
						"N�o foi poss�vel carregar o leiaute de Or�amento!\n"
								+ err.getMessage());
				err.printStackTrace();
			}
		}
	}

	private Object[] prefs() {
		Object[] oRetorno = new Object[4];
		String sSQL = "SELECT USAREFPROD,USALIQREL,TIPOPRECOCUSTO,CODTIPOMOV2 FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("UsaRefProd").trim().equals("S"))
					oRetorno[0] = new Boolean(true);
				else
					oRetorno[0] = new Boolean(false);
				if (rs.getString("UsaLiqRel") == null) {
					oRetorno[1] = new Boolean(false);
					Funcoes.mensagemInforma(this,
							"Preencha op��o de desconto em prefer�ncias!");
				} else {
					if (rs.getString("UsaLiqRel").trim().equals("S"))
						oRetorno[1] = new Boolean(true);
					else
						oRetorno[1] = new Boolean(false);
				}
				if (rs.getString("TipoPrecoCusto").equals("M"))
					oRetorno[2] = new Boolean(true);
				else
					oRetorno[2] = new Boolean(false);
				if (rs.getString("CODTIPOMOV2") != null)
					oRetorno[3] = new Integer(rs.getInt("CODTIPOMOV2"));
				else
					oRetorno[3] = new Integer(0);
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Erro ao carregar a tabela SGPREFERE1!\n"
							+ err.getMessage(),true,con,err);
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
		}

		return oRetorno;
	}

	public void keyTyped(KeyEvent kevt) {
		super.keyTyped(kevt);
	}

	public void keyReleased(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_CONTROL)
			bCtrl = false;
		super.keyReleased(kevt);
	}

	public void beforePost(PostEvent evt) {
		if ((evt.getListaCampos() == lcCampos)
				&& (lcCampos.getStatus() == ListaCampos.LCS_INSERT)) {
			testaCodOrc();
			txtStatusOrc.setVlrString("*");
		}
	}

	public void beforeDelete(DeleteEvent devt) {
	}

	public void beforeInsert(InsertEvent ievt) {
	}

	public void afterInsert(InsertEvent ievt) {
		if (ievt.getListaCampos() == lcCampos) {
			txtDtOrc.setVlrDate(new Date());
			GregorianCalendar clVenc = new GregorianCalendar();
			clVenc.add(Calendar.DATE, 15);
			txtDtVencOrc.setVlrDate(clVenc.getTime());
		}
	}

	public void afterDelete(DeleteEvent devt) {
		if (devt.getListaCampos() == lcDet)
			lcOrc2.carregaDados();
	}

	public void exec(int iCodOrc) {
		txtCodOrc.setVlrString(iCodOrc + "");
		lcCampos.carregaDados();
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		montaOrcamento();
		montaDetalhe();
		lcProd.setConexao(cn);
		lcProd2.setConexao(cn);
		lcOrc2.setConexao(cn);
		lcCli.setConexao(cn);
		lcPlanoPag.setConexao(cn);
		lcVend.setConexao(cn);
		lcTipoCli.setConexao(cn);
		lcAlmox.setConexao(cn);
		iniVenda();
	}

	public int[] getParansPreco() {
		int[] iRetorno = { txtCodProd.getVlrInteger().intValue(),
				txtCodCli.getVlrInteger().intValue(), Aplicativo.iCodEmp,
				ListaCampos.getMasterFilial("VDCLIENTE"),
				txtCodPlanoPag.getVlrInteger().intValue(), Aplicativo.iCodEmp,
				ListaCampos.getMasterFilial("FNPLANOPAG"),
				((Integer) oPrefs[3]).intValue(), Aplicativo.iCodEmp,
				ListaCampos.getMasterFilial("EQTIPOMOV"), Aplicativo.iCodEmp,
				Aplicativo.iCodFilial };
		return iRetorno;

	}

	public void setParansPreco(BigDecimal bdPreco) {
		txtPrecoItOrc.setVlrBigDecimal(bdPreco);
	}

	private int retPlanoPag() {
		int iRet = 0;
		String sSQL = "SELECT CodPlanoPag FROM SGPREFERE4 WHERE "
				+ "CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt("CodPlanoPag");
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar o plano de pagamento.\n"
					+"Provavelmente n�o foram gravadas corretamente as prefer�ncias!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		}
		return iRet;
	}

	private String retCodCli() {
		String iRet = "";
		String sSQL = "SELECT CodCli FROM SGPREFERE4 WHERE "
				+ "CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getString("CodCli");
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar o c�digo do cliente.\n" +
			"Provavelmente n�o foram gravadas corretamente as prefer�ncias!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		}
		return iRet;
	}

	private int retPrazo() {
		int iRet = 0;
		String sSQL = "SELECT Prazo FROM SGPREFERE4 WHERE "
				+ "CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt("Prazo");
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar o prazo.\n" +
			"Provavelmente n�o foram gravadas corretamente as prefer�ncias!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		}
		return iRet;
	}

	private int retVendedor() {
		int iRet = 0;
		String sSQL = "SELECT CODVEND FROM ATATENDENTE WHERE "
				+ "IDUSU=? AND CODEMPUS=? AND CODFILIALUS=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setString(1, Aplicativo.strUsuario);
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setInt(3, Aplicativo.iCodFilialPad);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt("CodVend");
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar o comissionado.\n"
					+ "O usu�rio '" + Aplicativo.strUsuario
					+ "' � um comissionado?\n" + err.getMessage(),true,con,err);
			err.printStackTrace();
		}
		return iRet;
	}

	private synchronized void iniVenda() {
		lcCampos.insert(true);
		txtCodCli.setVlrString(retCodCli());
		lcCli.carregaDados();
		lcProd.limpaCampos(true);
		lcProd2.limpaCampos(true);
		txtCodPlanoPag.setVlrInteger(new Integer(retPlanoPag()));
		txtVlrAdicOrc.setVlrString("");
		txtVlrEdAdicOrc.setVlrString("");
		txtVlrEdDescOrc.setVlrString("");
		txtVlrLiqOrc.setVlrString("");
		txtVlrProdOrc.setVlrString("");
		lcPlanoPag.carregaDados();
		txtCodVend.setVlrInteger(new Integer(retVendedor()));
		txtDtOrc.setVlrDate(new Date());
		txtDtVencOrc.setVlrDate(new Date());
		txtPrazoEntOrc.setVlrInteger(new Integer(retPrazo()));
		tab.limpa();
		iniItem();
	}

	private synchronized void iniItem() {
		txtCodItOrc.setVlrString("");
		txtCodProd.setVlrString("");
		txtPercDescItOrc.setVlrString("");
		txtPrecoItOrc.setVlrString("");
		txtQtdItOrc.setVlrString("");
		txtVlrLiqItOrc.setVlrString("");
		txtVlrProdItOrc.setVlrString("");
		if (((Boolean) oPrefs[0]).booleanValue()) {
			txtRefProd.requestFocus();
		} else {
			txtCodProd.requestFocus();
		}
	}
}