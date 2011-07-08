/**
 * @version 23/11/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez e Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FPrefereGeral.java <BR>
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
 * Tela de cadastro das prefer�ncias do sistema. Esse cadastro � utilizado para parametrizar o sistema de acordo com as necessidades espec�ficas da empresa.
 * 
 */

package org.freedom.modulos.std;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.SystemFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JPasswordFieldPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.Navegador;
import org.freedom.library.swing.component.PainelImagem;
import org.freedom.library.swing.frame.FTabDados;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modules.nfe.control.AbstractNFEFactory;
import org.freedom.modulos.fnc.library.swing.component.JTextFieldPlan;
import org.freedom.modulos.gms.business.object.TipoProd;

public class FPrefereGeral extends FTabDados implements CheckBoxListener, ActionListener, PostListener, EditListener, InsertListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private static final String RETRATO_DANFE = "1";

	private static final String PAISAGEM_DANFE = "2";

	private static final String PRODUCAO_NFE = "1";

	private static final String HOMOLOGACAO_NFE = "2";

	private static final String APLIC_CONTRIB_NFE = "0";

	private static final String APLIC_FISCO_NFE = "3";

//	private static final String VERSAO_NFE_1 = "100";

//	private static final String VERSAO_NFE_2 = "200";
	
	private JPanelPad pinVenda = new JPanelPad(690, 220);

	private JPanelPad pinGeral = new JPanelPad(330, 200);

	private JPanelPad pinPreco = new JPanelPad(330, 200);

	private JPanelPad pinCompra = new JPanelPad(330, 200);

	private JPanelPad pinOrcamento = new JPanelPad(330, 200);

	private JPanelPad pinFinanceiro = new JPanelPad();

	private JPanelPad pinSimples = new JPanelPad();
	
	private JPanelPad pinComissionamento = new JPanelPad();

	private JPanelPad pinContabil = new JPanelPad();

	private JPanelPad pinFiscal = new JPanelPad();
	
	private JPanelPad pinOpcoesComissionamento = new JPanelPad();
	
	private JPanelPad pinRegrasComissionamento = new JPanelPad();

	private JPanelPad pinSVV_LIC = new JPanelPad();

	private JPanelPad pinDev = new JPanelPad();

	private JPanelPad pinEstoq = new JPanelPad();

	private JPanelPad pinNFe = new JPanelPad();

	private JPanelPad pinRecursos = new JPanelPad();

	private JPanelPad pinFrete = new JPanelPad();

	private JPanelPad pinEmail = new JPanelPad();

	private JPanelPad pinProd = new JPanelPad();
	
	private JPanelPad pinOpcoesVenda = new JPanelPad();

	private JPanelPad pinOpcoesGeral = new JPanelPad();

	private JPanelPad pinCentrosdecustoGeral = new JPanelPad();

	private JPanelPad pinCasasDecGeral = new JPanelPad();

	private JPanelPad pinConsistenciasGeral = new JPanelPad();

	private JPanelPad pinValidacoesGeral = new JPanelPad();

	private JPanelPad pinCompras = new JPanelPad();
	
	private JPanelPad pinComprasCotacao = new JPanelPad();
	
	private JPanelPad pinComprasImportacao = new JPanelPad();

	private JTextFieldPad txtCodMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);

	private JTextFieldFK txtDescMoeda = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JCheckBoxPad cbGeraPagEmis = new JCheckBoxPad("Gera contas a pagar a partir da data de emiss�o.", "S", "N");

	private JCheckBoxPad cbGeraRecEmis = new JCheckBoxPad("Gera contas a receber a partir da data de emiss�o.", "S", "N");

	private JCheckBoxPad cbFechaCaixa = new JCheckBoxPad("Habilitar bloqueio de caixas", "S", "N");

	private JCheckBoxPad cbFechaCaixaAuto = new JCheckBoxPad("Efetua bloqueio autom�tico", "S", "N");
	
	private JCheckBoxPad cbEncOrcProd = new JCheckBoxPad("Sinaliza or�amentos para produ��o (Sistema Pull)", "S", "N");
	
	private JCheckBoxPad cbRMA = new JCheckBoxPad("RMA selecionado por padr�o", "S", "N");;
	
	private JTextFieldPad txtUrlWsCep = new JTextFieldPad(JTextFieldPad.TP_STRING, 150, 0);

	private JTextFieldPad txtCodTabJuros = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtDescTabJuros = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodHistRec = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtDescHistRec = new JTextFieldFK(JTextFieldPad.TP_STRING, 80, 0);

	private JTextFieldPad txtCodHistPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtDescHistPag = new JTextFieldFK(JTextFieldPad.TP_STRING, 80, 0);

	private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING, 6, 0);

	private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING, 14, 0);

	private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtDescFor = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodTipoFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldFK txtDescTipoForFT = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	
	private JTextFieldPad txtCodTipoForFT = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldFK txtDescTipoFor = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodTipoCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodTipoMov = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldPad txtCodTipoMovImp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodTipoMov2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldPad txtCodTipoMovS = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodTipoMov3 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodTipoMov4 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodTipoMov5 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodTipoMov6 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodTipoMov7 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodTipoMov8 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodTipoMov9 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodTransp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCasasDec = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 1, 0);

	private JTextFieldPad txtCasasDecFin = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 1, 0);

	private JTextFieldPad txtCasasDecPre = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 1, 0);
	
	private JTextFieldPad txtPercPrecoCusto = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 6, 2);

	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 4, 0);

	private JTextFieldPad txtDescClassOrc = new JTextFieldPad(JTextFieldPad.TP_STRING, 80, 0);

	private JTextFieldPad txtDescClassPed = new JTextFieldPad(JTextFieldPad.TP_STRING, 80, 0);

	private JTextFieldPad txtDescClassNfe = new JTextFieldPad(JTextFieldPad.TP_STRING, 80, 0);

	private JTextFieldPad txtDirNfeWin = new JTextFieldPad(JTextFieldPad.TP_STRING, 80, 0);

	private JTextFieldPad txtDirNfeLin = new JTextFieldPad(JTextFieldPad.TP_STRING, 80, 0);

	private JTextFieldPad txtDtVenctoNfe = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
	
	private JTextFieldPad txtDtVenctoEfd = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);

	private JTextFieldPad txtKeyLicNfe = new JTextFieldPad(JTextFieldPad.TP_STRING, 500, 0);
	
	private JTextFieldPad txtKeyLicEfd = new JTextFieldPad(JTextFieldPad.TP_STRING, 500, 0);

	private JTextFieldPad txtDescClassCp = new JTextFieldPad(JTextFieldPad.TP_STRING, 80, 0);

	private JTextFieldPad txtObs01 = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtObs02 = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtObs03 = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtObs04 = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtDescOrc = new JTextFieldPad(JTextFieldPad.TP_STRING, 80, 0);

	private JTextFieldPad txtTitOrcTxt01 = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);

	private JTextFieldPad txtCodMens = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);

	private JTextFieldFK txtDescMens = new JTextFieldFK(JTextFieldPad.TP_STRING, 1000, 0);

	private JTextFieldPad txtCodMensGeral = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);

	private JTextFieldFK txtDescMensGeral = new JTextFieldFK(JTextFieldPad.TP_STRING, 1000, 0);

	private JTextFieldFK txtDescTipoMov = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	
	private JTextFieldFK txtDescTipoMovImp = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldFK txtDescTipoMov2 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	
	private JTextFieldFK txtDescTipoMovS = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldFK txtDescTipoMov3 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldFK txtDescTipoMov4 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldFK txtDescTipoMov5 = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldFK txtDescTipoMov6 = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldFK txtDescTipoMov7 = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldFK txtDescTipoMov8 = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldFK txtDescTipoMov9 = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldFK txtDescTransp = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodPlanoPag2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldPad txtCodPlanoPagSV = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtPrazo = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodTab = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodClasCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldFK txtDescPlanoPag2 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	
	private JTextFieldFK txtDescPlanoPagSV = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldFK txtDescTab = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldFK txtDescClasCli = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldFK txtDescCli = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private final JTextFieldPad txtServidorSMTP = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);

	private final JTextFieldPad txtPortaSMTP = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private final JTextFieldPad txtUsuarioSMTP = new JTextFieldPad(JTextFieldPad.TP_STRING, 60, 0);

	private final JTextFieldPad txtEndEmail = new JTextFieldPad(JTextFieldPad.TP_STRING, 60, 0);

	private final JPasswordFieldPad txtSenhaSMTP = new JPasswordFieldPad(30);

	private JTextFieldPlan txtCodPlanJR = new JTextFieldPlan(JTextFieldPad.TP_STRING, 13, 0);

	private JTextFieldFK txtDescPlanJR = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPlan txtCodPlanJP = new JTextFieldPlan(JTextFieldPad.TP_STRING, 13, 0);

	private JTextFieldFK txtDescPlanJP = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPlan txtCodPlanDC = new JTextFieldPlan(JTextFieldPad.TP_STRING, 13, 0);

	private JTextFieldFK txtDescPlanDC = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPlan txtCodPlanDR = new JTextFieldPlan(JTextFieldPad.TP_STRING, 13, 0);

	private JTextFieldFK txtDescPlanDR = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPlan txtCodPlanPC = new JTextFieldPlan(JTextFieldPad.TP_STRING, 13, 0);

	private JTextFieldFK txtDescPlanPC = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	
	private JTextFieldPad txtDiasVencOrc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);

	private final String opcoes = "Op��es";

//	private JLabelPad lbProdOpcoes = new JLabelPad(opcoes, SwingConstants.CENTER);

	private JLabelPad lbRecursos = new JLabelPad(opcoes, SwingConstants.CENTER);

	private JPanelPad pnFrete = new JPanelPad();

	private JLabelPad lbCtbOpcoes = new JLabelPad(opcoes, SwingConstants.CENTER);

	private JLabelPad lbPrcOpcoes = new JLabelPad(opcoes, SwingConstants.CENTER);

	private JPanelPad pnEstOpcoes = new JPanelPad();
	
	private JPanelPad pnProdOpcoes = new JPanelPad();
	
	private JPanelPad pnProdOpCad = new JPanelPad();


//	private JLabelPad lbProdCont = new JLabelPad();

	private JLabelPad lbRecursosCont = new JLabelPad();

	private JLabelPad lbFinOpcoes = new JLabelPad();

	private JLabelPad lbFinPagar = new JLabelPad();

	private JLabelPad lbFinLibCred = new JLabelPad();
	
	private JLabelPad lbTPNossoNumero = new JLabelPad();

	private JLabelPad lbFinDefinicoes = new JLabelPad();

	private JLabelPad lbFinContas = new JLabelPad();

	private JLabelPad lbCtbCont = new JLabelPad();

	private JLabelPad lbPrcCont = new JLabelPad();

	private JRadioGroup<String, String> rgTipoValidOrc = null;
	
	private JRadioGroup<String, String> rgTpNossoNumero = null;

	private JRadioGroup<String, String> rgTipoPrecoCusto = null;

	private JRadioGroup<String, String> rgSetorVenda = null;

	private JRadioGroup<String, String> rgTipoClass = null;

	private JRadioGroup<String, String> rgOrdNota = null;

	private JRadioGroup<String, String> rgLibCred = null;

	private JRadioGroup<String, String> rgCodBar = null;

	private JRadioGroup<String, String> rgTipoCred = null;
	
	private JRadioGroup<String, String> rgCV = null;
	
	private JRadioGroup<String, String> rgAbaixCust = null;
	
	private JRadioGroup<?, ?> rgTipoProd = null;
	
	private JComboBoxPad cbSisContabil = null;
	
	private JComboBoxPad cbVersaoNFE = null;

	private JComboBoxPad cbRegimeNFE = null;
	
	private JComboBoxPad cbTamDescProd = null;

	private final JCheckBoxPad cbImpDocBol = new JCheckBoxPad("Imprime documento/parcela nos boletos", "S", "N");

	private final JCheckBoxPad cbUsaRefProd = new JCheckBoxPad("Usa refer�ncia.", "S", "N");

	private final JCheckBoxPad cbUsaPedSeq = new JCheckBoxPad("Pedido sequencial.", "S", "N", true);

	private final JCheckBoxPad cbSomaVolumes = new JCheckBoxPad("Soma volumes na venda.", "S", "N");

	private final JCheckBoxPad cbUsaOrcSeq = new JCheckBoxPad("Or�amento sequencial.", "S", "N", true);

	private final JCheckBoxPad cbUsaDescEspelho = new JCheckBoxPad("Desconto no espelho.", "S", "N");

	private final JCheckBoxPad cbUsaClasComis = new JCheckBoxPad("Classifica comiss�o na venda", "S", "N");

	private final JCheckBoxPad cbTabFreteVd = new JCheckBoxPad("Aba frete na venda.", "S", "N", true);

	private final JCheckBoxPad cbVendaMatPrim = new JCheckBoxPad("Permitir venda de mat�ria prima.", "S", "N");

	private final JCheckBoxPad cbVendaImobilizado = new JCheckBoxPad("Permitir venda de Imobilizado.", "S", "N");
	
	private final JCheckBoxPad cbVendaMatConsumo = new JCheckBoxPad("Permitir venda de material de Consumo.", "S", "N");

	private final JCheckBoxPad cbGeraCodUnif = new JCheckBoxPad("Habilita gera��o de c�digos unificados", "S", "N");

	private final JCheckBoxPad cbVisualizaLucr = new JCheckBoxPad("Mostrar lucratividade no pedido.", "S", "N");

	private final JCheckBoxPad cbTabAdicVd = new JCheckBoxPad("Aba adic. na venda.", "S", "N");

	private final JCheckBoxPad cbTravaTMNFVD = new JCheckBoxPad("Travar tipo de Mov. NF na inserc. da venda.", "S", "N", true);

	private final JCheckBoxPad cbJurosPosCalc = new JCheckBoxPad("Juros p�s-calculado.", "S", "N");

	private final JCheckBoxPad cbRgCliObrig = new JCheckBoxPad("RG obrigat�rio para o cadastro de clientes", "S", "N", true);

	private final JCheckBoxPad cbUsuAtivCli = new JCheckBoxPad("Acesso para ativa��o de cliente por usu�rio.", "S", "N");

	private final JCheckBoxPad cbCliMesmoCnpj = new JCheckBoxPad("Permitir clientes com mesmo CNPJ", "S", "N");

	private final JCheckBoxPad cbCnpjCliObrig = new JCheckBoxPad("CNPJ obrigat�rio para o cadastro de clientes", "S", "N", true);

	private final JCheckBoxPad cbCnpjForObrig = new JCheckBoxPad("CNPJ obrigat�rio para o cadastro de fornecedores", "S", "N", true);

	private final JCheckBoxPad cbInscEstForObrig = new JCheckBoxPad("IE obrigat�ria para o cadastro de fornecedores.", "S", "N", true);

	private final JCheckBoxPad cbEstLotNeg = new JCheckBoxPad("Permite saldo lote negativo.", "S", "N");

//	private final JCheckBoxPad cbUsaRefCompra = new JCheckBoxPad("Usa refer�ncia na compra. ", "S", "N");
	
	private final JCheckBoxPad cbPrecoCotacao = new JCheckBoxPad("Usa pre�o de cota��o", "S", "N");
	
	private final JCheckBoxPad cbBloqPrecoAprov = new JCheckBoxPad("Bloqueia pre�o n�o validado", "S", "N");

	private final JCheckBoxPad cbTransAbaCp = new JCheckBoxPad("Aba transp. na tela de compras.", "S", "N");

	private final JCheckBoxPad cbImportAbaCp = new JCheckBoxPad("Aba Importa��o na tela de compras.", "S", "N");

	private final JCheckBoxPad cbPrecoRel = new JCheckBoxPad("Mostra pre�o de compra nos relat�rios.", "S", "N", true);

	private final JCheckBoxPad cbEstNeg = new JCheckBoxPad("Permite saldo negativo.", "S", "N");

	private final JCheckBoxPad cbEstNegGrupo = new JCheckBoxPad("Controle de saldo negativo por grupo.", "S", "N");

	private final JCheckBoxPad cbNatVenda = new JCheckBoxPad("Habilitar campo CFOP.", "S", "N", true);

	private final JCheckBoxPad cbIPIVenda = new JCheckBoxPad("Habilitar campo IPI.", "S", "N", true);

	private final JCheckBoxPad cbIcmsVenda = new JCheckBoxPad("Habilitar campos de ICMS.", "S", "N");

	private final JCheckBoxPad cbIcmsFrete = new JCheckBoxPad("Habilitar campos de ICMS para Frete.", "S", "N");

	private final JCheckBoxPad cbComisPDupl = new JCheckBoxPad("Calcula comiss�o com base nas duplicatas.", "S", "N", true);
	
	private final JCheckBoxPad cbHabilitaLimiteDesconto = new JCheckBoxPad("Habilita Limite Desconto.", "S", "N", true);

	private final JCheckBoxPad cbCustosSICMS = new JCheckBoxPad("Pre�o de custo sem ICMS.", "S", "N", true);

	private final JCheckBoxPad cbBloqVenda = new JCheckBoxPad("Bloquear venda ap�s impress�o da NF.", "S", "N");

	private final JCheckBoxPad cbBloqCompra = new JCheckBoxPad("Bloquear compra ap�s finalizar.", "S", "N");

	private final JCheckBoxPad cbPepsProd = new JCheckBoxPad("Exibe custo PEPS no cadastro de produtos.", "S", "N");

	private final JCheckBoxPad cbBuscaProdSimilar = new JCheckBoxPad("Busca autom�tica de produtos similares.", "S", "N");

	private final JCheckBoxPad cbMultiAlmox = new JCheckBoxPad("Multi almoxarifados.", "S", "N");

	private final JCheckBoxPad cbUsaIbgeCli = new JCheckBoxPad("Usar a tabela de IBGE para o cadastro de clientes.", "S", "N");

	private final JCheckBoxPad cbUsaIbgeFor = new JCheckBoxPad("Usar a tabela de IBGE para o cadastro de fornecedores.", "S", "N");

	private final JCheckBoxPad cbUsaIbgeTransp = new JCheckBoxPad("Usar a tabela de IBGE para o cadastro de transportadores.", "S", "N");

	private final JCheckBoxPad cbPrazoEnt = new JCheckBoxPad("Prazo de entrega na venda.", "S", "N", true);

	private final JCheckBoxPad cbDiasPEData = new JCheckBoxPad("Data de entrega no pedido.", "S", "N");

	private final JCheckBoxPad cbDescCompl = new JCheckBoxPad("Descri��o completa do produto para Or�amento e Pedido.", "S", "N");

	private final JCheckBoxPad cbObsCliVend = new JCheckBoxPad("Mostrar Obs. do cliente na venda e or�amento.", "S", "N");

	private final JCheckBoxPad cbContEstoq = new JCheckBoxPad("Controla estoque.", "S", "N");

	private final JCheckBoxPad cbReCalcVenda = new JCheckBoxPad("Recalcular pre�o na venda.", "S", "N");

	private final JCheckBoxPad cbReCalcOrc = new JCheckBoxPad("Recalcular pre�o no or�amento.", "S", "N");

	private final JCheckBoxPad cbAprovOrc = new JCheckBoxPad("Permite aprova��o no fechamento.", "S", "N");

	private final JCheckBoxPad cbLayoutPed = new JCheckBoxPad("Usar layout personalizado para pedido.", "S", "N");

	private final JCheckBoxPad cbVerifAltParVenda = new JCheckBoxPad("Verificar usuario para alterar parcelas.", "S", "N");

	private final JCheckBoxPad cbUsaBuscGenProd = new JCheckBoxPad("Busca gen�rica do c�digo do produto.", "S", "N");

	private final JCheckBoxPad cbFilBuscGenProd1 = new JCheckBoxPad("C�digo do produto.", "S", "N");

	private final JCheckBoxPad cbFilBuscGenProd2 = new JCheckBoxPad("Refer�ncia do produto.", "S", "N");

	private final JCheckBoxPad cbFilBuscGenProd3 = new JCheckBoxPad("C�digo de barras", "S", "N");

	private final JCheckBoxPad cbFilBuscGenProd4 = new JCheckBoxPad("C�digo do fabricante", "S", "N");

	private final JCheckBoxPad cbFilBuscGenProd5 = new JCheckBoxPad("Refer�ncia no fornecedor", "S", "N");

	private final JCheckBoxPad cbUsaBuscGenProdORC = new JCheckBoxPad("Permitir busca generica de produto no or�amento.", "S", "N");

	private final JCheckBoxPad cbUsaLoteOrc = new JCheckBoxPad("Usa lote no or�amento.", "S", "N");

	private final JCheckBoxPad cbBuscaVlrUltCompra = new JCheckBoxPad("Busca valor da ultima compra.", "S", "N");

	private final JCheckBoxPad cbHabiitaCustoCompra = new JCheckBoxPad("Habilita campo de custo na compra.", "S", "N");

	private final JCheckBoxPad cbUsaPrecoZero = new JCheckBoxPad("Permite pre�o de produto Zero.", "S", "N");
	
	private final JCheckBoxPad cbUsaPrecoComis = new JCheckBoxPad("Usa pre�o fixo do produto para comissionamento por se��o", "S", "N");
	
	private final JCheckBoxPad cbEspecialComis = new JCheckBoxPad("Usa comissionamento especial", "S", "N");

	private final JCheckBoxPad cbUsaImgOrc = new JCheckBoxPad("Usar imagem de assinatura no or�amento.", "S", "N");

	private final JCheckBoxPad cbUsaNomeVendOrc = new JCheckBoxPad("Usar nome do comissionado no or�amento.", "S", "N");

	private final JCheckBoxPad cbConsCPFCli = new JCheckBoxPad("Validar CPF do cliente.", "S", "N", true);

	private final JCheckBoxPad cbConsCPFFor = new JCheckBoxPad("Validar CPF do fornecedor.", "S", "N", true);

	private final JCheckBoxPad cbConsIECli = new JCheckBoxPad("Validar IE do cliente.", "S", "N", true);

	private final JCheckBoxPad cbConsIECliFisica = new JCheckBoxPad("Validar IE para clientes pessoa f�sica.", "S", "N");

	private final JCheckBoxPad cbMostraTransp = new JCheckBoxPad("Mostrar aba transportadora na tela or�amento.", "S", "N");

	private final JCheckBoxPad cbHabVlrTotItOrc = new JCheckBoxPad("Permite digita��o do valor total do �tem", "S", "N");

	private final JCheckBoxPad cbGeraComisVendaOrc = new JCheckBoxPad("Carrega comiss�o do or�amento.", "S", "N");

	private final JCheckBoxPad cbCredIcmsSimples = new JCheckBoxPad("Destaca cr�dito de ICMS", "S", "N");

	private final JCheckBoxPad cbConsIEFor = new JCheckBoxPad("Validar IE do fornecedor.", "S", "N", true);

	private final JCheckBoxPad cbAltItRecImpBol = new JCheckBoxPad("Atualiza parcela na impress�o do boleto.", "S", "N");

	private final JCheckBoxPad cbEstItRecAltDtVenc = new JCheckBoxPad("Estorna parcela na altera��o da data de vencimento.", "S", "N");

	private final JCheckBoxPad cbAdicCodOrcObsPed = new JCheckBoxPad("Adicionar c�digos de or�amentos na observa��o do pedido.", "S", "N");

	private final JCheckBoxPad cbAdicObsOrcPed = new JCheckBoxPad("Adicionar observa��es do or�amento no pedido.", "S", "N");

	private final JCheckBoxPad cbMultiComis = new JCheckBoxPad("Habilita m�ltiplo comissionamento.", "S", "N");

	private final JCheckBoxPad cbLiberacaoCreGlobal = new JCheckBoxPad("Libera��o de cr�dito globalizado.", "S", "N");

	private final JCheckBoxPad cbComissManut = new JCheckBoxPad("Comissionado obrigar�rio na manuten��o de comiss�es.", "S", "N");

	private final JCheckBoxPad cbBuscaCep = new JCheckBoxPad("Habilita a busca de endere�o via CEP.", "S", "N");

	private final JCheckBoxPad cbLancaFinContr = new JCheckBoxPad("Permite lan�amento financeiro em contrato.", "S", "N");

	private final JCheckBoxPad cbLancaRMAContr = new JCheckBoxPad("Permite lan�amento de RMA em contrato.", "S", "N");

	private final JCheckBoxPad cbInfCPDevolucao = new JCheckBoxPad("Informar compra na devolu��o ?", "S", "N");

	private final JCheckBoxPad cbUsaBuscGenProdCP = new JCheckBoxPad("Busca generica do c�digo do produto.", "S", "N");
	
	private final JCheckBoxPad cbRevalidarLoteCompra = new JCheckBoxPad("Permitir Revalidar Lote", "S", "N");

	private final JCheckBoxPad cbAutenticaSMTP = new JCheckBoxPad("Autenticar ?", "S", "N");

	private final JCheckBoxPad cbSSLSMTP = new JCheckBoxPad("Usa SSL ?", "S", "N");

	private final JCheckBoxPad cbInfVdRemessa = new JCheckBoxPad("Permite vincular item com remessa ?", "S", "N");

	private PainelImagem imgAssOrc = new PainelImagem(65000);

	private ListaCampos lcMoeda = new ListaCampos(this, "MO");

	private ListaCampos lcTabJuros = new ListaCampos(this, "TJ");

	private ListaCampos lcHistRec = new ListaCampos(this, "HISTREC");

	private ListaCampos lcHistPag = new ListaCampos(this, "HISTPAG");

	private ListaCampos lcPlanJR = new ListaCampos(this, "JR");
	
	private ListaCampos lcPlanPC = new ListaCampos(this, "PC");

	private ListaCampos lcPlanJP = new ListaCampos(this, "JP");

	private ListaCampos lcPlanDC = new ListaCampos(this, "DC");

	private ListaCampos lcPlanDR = new ListaCampos(this, "DR");

	private ListaCampos lcMarca = new ListaCampos(this, "MC");

	private ListaCampos lcGrupo = new ListaCampos(this, "GP");

	private ListaCampos lcTipoFor = new ListaCampos(this, "TF");
	
	private ListaCampos lcTipoForFT = new ListaCampos(this, "FT");

	private ListaCampos lcTipoCli = new ListaCampos(this, "TC");

	private ListaCampos lcFor = new ListaCampos(this, "FR");

	private ListaCampos lcTipoMov = new ListaCampos(this, "TM");

	private ListaCampos lcTipoMov2 = new ListaCampos(this, "T2");
	
	private ListaCampos lcTipoMovS = new ListaCampos(this, "TS");

	private ListaCampos lcTipoMov3 = new ListaCampos(this, "T3");

	private ListaCampos lcTipoMov4 = new ListaCampos(this, "T4");

	private ListaCampos lcTipoMov5 = new ListaCampos(this, "T5");

	private ListaCampos lcTipoMov6 = new ListaCampos(this, "T6");

	private ListaCampos lcTipoMov7 = new ListaCampos(this, "TM");

	private ListaCampos lcTipoMov8 = new ListaCampos(this, "T8");

	private ListaCampos lcTipoMov9 = new ListaCampos(this, "T9");
	
	private ListaCampos lcTipoMovImp = new ListaCampos(this, "IM");

	private ListaCampos lcTransp = new ListaCampos(this, "TN");

	private ListaCampos lcPlanoPag = new ListaCampos(this, "PG");

	private ListaCampos lcPlanoPag2 = new ListaCampos(this, "PP");
	
	private ListaCampos lcPlanoPagSV = new ListaCampos(this, "SV");

	private ListaCampos lcTabPreco = new ListaCampos(this, "TB");

	private ListaCampos lcClasCli = new ListaCampos(this, "CE");

	private ListaCampos lcCli = new ListaCampos(this, "CL");

	private ListaCampos lcEmailNF = new ListaCampos(this, "NF");

	private ListaCampos lcPDV = new ListaCampos(this, "");

	private ListaCampos lcPrefere3 = new ListaCampos(this, "P3");

	private ListaCampos lcMens = new ListaCampos(this, "MENSORC");

	private ListaCampos lcMensGeral = new ListaCampos(this, "MS");

	private final JButtonPad btDirNfeWin = new JButtonPad(Icone.novo("btAbrirPeq.gif"));

	private final JButtonPad btDirNfeLin = new JButtonPad(Icone.novo("btAbrirPeq.gif"));

	private JRadioGroup<String, String> rgFormatoDANFE = null;

	private JRadioGroup<String, String> rgAmbienteNFE = null;

	private JRadioGroup<String, String> rgProcEmiNFE = null;

	private JRadioGroup<String, String> rgTipoCustoLuc = null;

	private JCheckBoxPad cbInfAdicProdNFE = new JCheckBoxPad("Adiciona descri��o completa do produto na NFE.", "S", "N");
	
	private JCheckBoxPad cbExibeParcObsDanfe = new JCheckBoxPad("Desdobramento de parcelas nas observa��es da DANFE.", "S", "N");

	private JTextFieldPad txtVerProcNfe = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);

	private JPanelPad pnOpcoesOrc = new JPanelPad();

	private JTextFieldPad txtCodEmailNF = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);

	private JTextFieldFK txtDescEmailNF = new JTextFieldFK(JTextFieldPad.TP_STRING, 80, 0);
	
	private JTextFieldPad txtNumDigIdentTit = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 2, 0);

	public FPrefereGeral() {

		super();

		setTitulo("Prefer�ncias Gerais");
		setAtribos(30, 40, 900, 660);

		lcCampos.setMensInserir(false);
		lcPrefere3.setMensInserir(false);
		lcPDV.setMensInserir(false);

		Vector<String> vLabsFormatoDANFE = new Vector<String>();
		Vector<String> vValsFormatoDANFE = new Vector<String>();
		vLabsFormatoDANFE.addElement("Retrato");
		vLabsFormatoDANFE.addElement("Paisagem");
		vValsFormatoDANFE.addElement(RETRATO_DANFE);
		vValsFormatoDANFE.addElement(PAISAGEM_DANFE);
		rgFormatoDANFE = new JRadioGroup<String, String>(1, 2, vLabsFormatoDANFE, vValsFormatoDANFE);

		Vector<String> vLabsAmbienteNFE = new Vector<String>();
		Vector<String> vValsAmbienteNFE = new Vector<String>();
		vLabsAmbienteNFE.addElement("Produ��o");
		vLabsAmbienteNFE.addElement("Homologa��o");
		vValsAmbienteNFE.addElement(PRODUCAO_NFE);
		vValsAmbienteNFE.addElement(HOMOLOGACAO_NFE);
		rgAmbienteNFE = new JRadioGroup<String, String>(1, 2, vLabsAmbienteNFE, vValsAmbienteNFE);

		Vector<String> vLabsProcEmiNFE = new Vector<String>();
		Vector<String> vValsProcEmiNFE = new Vector<String>();
		vLabsProcEmiNFE.addElement("Emiss�o com aplicativo do contribuinte (ex.:Setpoint-NFE)");
		vLabsProcEmiNFE.addElement("Emissao com aplicativo fornecido pelo Fisco");
		vValsProcEmiNFE.addElement(APLIC_CONTRIB_NFE);
		vValsProcEmiNFE.addElement(APLIC_FISCO_NFE);
		rgProcEmiNFE = new JRadioGroup<String, String>(2, 1, vLabsProcEmiNFE, vValsProcEmiNFE);

		Vector<String> vLabsTipoCustoLuc = new Vector<String>();
		Vector<String> vValsTipoCustoLuc = new Vector<String>();
		vLabsTipoCustoLuc.addElement("MPM");
		vLabsTipoCustoLuc.addElement("PEPS");
		vLabsTipoCustoLuc.addElement("Ultima Compra");
		vLabsTipoCustoLuc.addElement("Informado");
		vValsTipoCustoLuc.addElement("M");
		vValsTipoCustoLuc.addElement("P");
		vValsTipoCustoLuc.addElement("U");
		vValsTipoCustoLuc.addElement("I");
		rgTipoCustoLuc = new JRadioGroup<String, String>(2, 2, vLabsTipoCustoLuc, vValsTipoCustoLuc);

		lcMoeda.add(new GuardaCampo(txtCodMoeda, "CodMoeda", "C�d.moeda", ListaCampos.DB_PK, true));
		lcMoeda.add(new GuardaCampo(txtDescMoeda, "SingMoeda", "Descri��o da moeda", ListaCampos.DB_SI, false));
		lcMoeda.montaSql(false, "MOEDA", "FN");
		lcMoeda.setQueryCommit(false);
		lcMoeda.setReadOnly(true);
		txtCodMoeda.setTabelaExterna(lcMoeda, null);

		lcHistRec.add(new GuardaCampo(txtCodHistRec, "CodHist", "C�d.Hist.Rec.", ListaCampos.DB_PK, false));
		lcHistRec.add(new GuardaCampo(txtDescHistRec, "DescHist", "Descri��o do hist�rico", ListaCampos.DB_SI, false));
		lcHistRec.montaSql(false, "HISTPAD", "FN");
		lcHistRec.setQueryCommit(false);
		lcHistRec.setReadOnly(true);
		txtCodHistRec.setTabelaExterna(lcHistRec, null);

		lcHistPag.add(new GuardaCampo(txtCodHistPag, "CodHist", "C�d.Hist.Pag.", ListaCampos.DB_PK, false));
		lcHistPag.add(new GuardaCampo(txtDescHistPag, "DescHist", "Descri��o do hist�rico", ListaCampos.DB_SI, false));
		lcHistPag.montaSql(false, "HISTPAD", "FN");
		lcHistPag.setQueryCommit(false);
		lcHistPag.setReadOnly(true);
		txtCodHistPag.setTabelaExterna(lcHistPag, null);

		lcTabJuros.add(new GuardaCampo(txtCodTabJuros, "CodTbj", "C�d.tb.jur.", ListaCampos.DB_PK, false));
		lcTabJuros.add(new GuardaCampo(txtDescTabJuros, "DescTbJ", "Descri��o da tabela de juros", ListaCampos.DB_SI, false));
		lcTabJuros.montaSql(false, "TBJUROS", "FN");
		lcTabJuros.setQueryCommit(false);
		lcTabJuros.setReadOnly(true);
		txtCodTabJuros.setTabelaExterna(lcTabJuros, null);

		lcMarca.add(new GuardaCampo(txtCodMarca, "CodMarca", "C�d.marca", ListaCampos.DB_PK, false));
		lcMarca.add(new GuardaCampo(txtDescMarca, "DescMarca", "Descri��o da marca", ListaCampos.DB_SI, false));
		lcMarca.montaSql(false, "MARCA", "EQ");
		lcMarca.setQueryCommit(false);
		lcMarca.setReadOnly(true);
		txtCodMarca.setTabelaExterna(lcMarca, null);

		lcGrupo.add(new GuardaCampo(txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, false));
		lcGrupo.add(new GuardaCampo(txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false));
		lcGrupo.montaSql(false, "GRUPO", "EQ");
		lcGrupo.setQueryCommit(false);
		lcGrupo.setReadOnly(true);
		txtCodGrup.setTabelaExterna(lcGrupo, null);

		lcFor.add(new GuardaCampo(txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, false));
		lcFor.add(new GuardaCampo(txtDescFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false));
		lcFor.montaSql(false, "FORNECED", "CP");
		lcFor.setQueryCommit(false);
		lcFor.setReadOnly(true);
		txtCodFor.setTabelaExterna(lcFor, null);

		// Tipo de fornecedor para clientes / devolu��o
		
		lcTipoFor.add(new GuardaCampo(txtCodTipoFor, "CodTipoFor", "C�d.tp.for.", ListaCampos.DB_PK, false));
		lcTipoFor.add(new GuardaCampo(txtDescTipoFor, "DescTipoFor", "Descri��o do tipo de fornecedor", ListaCampos.DB_SI, false));
		lcTipoFor.montaSql(false, "TIPOFOR", "CP");
		lcTipoFor.setQueryCommit(false);
		lcTipoFor.setReadOnly(true);
		txtCodTipoFor.setTabelaExterna(lcTipoFor, null);

		// Tipo de fornecedor para transportadoras / conhecimento de frete
		lcTipoForFT.add(new GuardaCampo(txtCodTipoForFT, "CodTipoFor", "C�d.tp.for.", ListaCampos.DB_PK, false));
		lcTipoForFT.add(new GuardaCampo(txtDescTipoForFT, "DescTipoFor", "Descri��o do tipo de for. para transportadoras", ListaCampos.DB_SI, false));
		lcTipoForFT.montaSql(false, "TIPOFOR", "CP");
		lcTipoForFT.setQueryCommit(false);
		lcTipoForFT.setReadOnly(true);
		txtCodTipoForFT.setTabelaExterna(lcTipoForFT, null);
		
		lcTipoCli.add(new GuardaCampo(txtCodTipoCli, "CodTipoCli", "C�d.tp.cli.", ListaCampos.DB_PK, false));
		lcTipoCli.add(new GuardaCampo(txtDescTipoCli, "DescTipoCli", "Descri��o do tipo de cliente", ListaCampos.DB_SI, false));
		lcTipoCli.montaSql(false, "TIPOCLI", "VD");
		lcTipoCli.setQueryCommit(false);
		lcTipoCli.setReadOnly(true);
		txtCodTipoCli.setTabelaExterna(lcTipoCli, null);

		lcTipoMov.add(new GuardaCampo(txtCodTipoMov, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov.add(new GuardaCampo(txtDescTipoMov, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov.setQueryCommit(false);
		lcTipoMov.setReadOnly(true);
		txtCodTipoMov.setTabelaExterna(lcTipoMov, null);

		lcTipoMov2.add(new GuardaCampo(txtCodTipoMov2, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov2.add(new GuardaCampo(txtDescTipoMov2, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov2.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov2.setQueryCommit(false);
		lcTipoMov2.setReadOnly(true);
		txtCodTipoMov2.setTabelaExterna(lcTipoMov2, null);

		lcTipoMovS.add(new GuardaCampo(txtCodTipoMovS, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMovS.add(new GuardaCampo(txtDescTipoMovS, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMovS.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMovS.setQueryCommit(false);
		lcTipoMovS.setReadOnly(true);
		txtCodTipoMovS.setTabelaExterna(lcTipoMovS, null);

		lcTipoMov3.add(new GuardaCampo(txtCodTipoMov3, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov3.add(new GuardaCampo(txtDescTipoMov3, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov3.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov3.setQueryCommit(false);
		lcTipoMov3.setReadOnly(true);
		txtCodTipoMov3.setTabelaExterna(lcTipoMov3, null);

		lcTipoMov4.add(new GuardaCampo(txtCodTipoMov4, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov4.add(new GuardaCampo(txtDescTipoMov4, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov4.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov4.setQueryCommit(false);
		lcTipoMov4.setReadOnly(true);
		txtCodTipoMov4.setTabelaExterna(lcTipoMov4, null);

		lcTipoMov5.add(new GuardaCampo(txtCodTipoMov5, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov5.add(new GuardaCampo(txtDescTipoMov5, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov5.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov5.setQueryCommit(false);
		lcTipoMov5.setReadOnly(true);
		txtCodTipoMov5.setTabelaExterna(lcTipoMov5, null);

		lcTipoMov6.add(new GuardaCampo(txtCodTipoMov6, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov6.add(new GuardaCampo(txtDescTipoMov6, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov6.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov6.setWhereAdic(" ESTIPOMOV='I' ");
		lcTipoMov6.setQueryCommit(false);
		lcTipoMov6.setReadOnly(true);
		txtCodTipoMov6.setTabelaExterna(lcTipoMov6, null);

		lcTipoMov7.add(new GuardaCampo(txtCodTipoMov7, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov7.add(new GuardaCampo(txtDescTipoMov7, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov7.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov7.setWhereAdic(" ESTIPOMOV='I' ");
		lcTipoMov7.setQueryCommit(false);
		lcTipoMov7.setReadOnly(true);
		txtCodTipoMov7.setTabelaExterna(lcTipoMov7, null);

		lcTipoMov8.add(new GuardaCampo(txtCodTipoMov8, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov8.add(new GuardaCampo(txtDescTipoMov8, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov8.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov8.setWhereAdic(" TIPOMOV='RM' ");
		lcTipoMov8.setQueryCommit(false);
		lcTipoMov8.setReadOnly(true);
		txtCodTipoMov8.setTabelaExterna(lcTipoMov8, null);
		txtCodTipoMov8.setFK(true);

		lcTipoMov9.add(new GuardaCampo(txtCodTipoMov9, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov9.add(new GuardaCampo(txtDescTipoMov9, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov9.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov9.setWhereAdic(" TIPOMOV='CF' ");
		lcTipoMov9.setQueryCommit(false);
		lcTipoMov9.setReadOnly(true);
		txtCodTipoMov9.setTabelaExterna(lcTipoMov9, null);
		txtCodTipoMov9.setFK(true);
		
		lcTipoMovImp.add(new GuardaCampo(txtCodTipoMovImp, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMovImp.add(new GuardaCampo(txtDescTipoMovImp, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMovImp.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMovImp.setWhereAdic(" TIPOMOV='DI' ");
		lcTipoMovImp.setQueryCommit(false);
		lcTipoMovImp.setReadOnly(true);
		txtCodTipoMovImp.setTabelaExterna(lcTipoMovImp, null);
		txtCodTipoMovImp.setFK(true);
		txtCodTipoMovImp.setNomeCampo("CodTipoMov");
		
		txtCodTransp.setNomeCampo("CodTran");
		lcTransp.add(new GuardaCampo(txtCodTransp, "CodTran", "C�d.tran.", ListaCampos.DB_PK, false));
		lcTransp.add(new GuardaCampo(txtDescTransp, "RazTran", "Nome do transportador", ListaCampos.DB_SI, false));
		txtDescTransp.setListaCampos(lcTransp);
		txtCodTransp.setTabelaExterna(lcTransp, null);
		txtCodTransp.setFK(true);
		lcTransp.montaSql(false, "TRANSP", "VD");
		lcTransp.setQueryCommit(false);
		lcTransp.setReadOnly(true);

		txtCodPlanoPag.setNomeCampo("CodPlanoPag");
		lcPlanoPag.add(new GuardaCampo(txtCodPlanoPag, "CodPlanoPag", "C�d.p.pag", ListaCampos.DB_PK, false));
		lcPlanoPag.add(new GuardaCampo(txtDescPlanoPag, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag.setReadOnly(true);
		txtCodPlanoPag.setTabelaExterna(lcPlanoPag, null);
		txtCodPlanoPag.setFK(true);
		txtDescPlanoPag.setListaCampos(lcPlanoPag);

		txtCodPlanoPag2.setNomeCampo("CodPlanoPag");
		lcPlanoPag2.add(new GuardaCampo(txtCodPlanoPag2, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag2.add(new GuardaCampo(txtDescPlanoPag2, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag2.montaSql(false, "PLANOPAG", "FN");
		txtCodPlanoPag2.setTabelaExterna(lcPlanoPag2, null);
		txtCodPlanoPag2.setFK(true);
		lcPlanoPag2.setReadOnly(true);
		txtDescPlanoPag2.setListaCampos(lcPlanoPag2);

		txtCodPlanoPagSV.setNomeCampo("CodPlanoPag");
		lcPlanoPagSV.add(new GuardaCampo(txtCodPlanoPagSV, "CodPlanoPag", "C�d.p.pag", ListaCampos.DB_PK, false));
		lcPlanoPagSV.add(new GuardaCampo(txtDescPlanoPagSV, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPagSV.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPagSV.setReadOnly(true);
		txtCodPlanoPagSV.setTabelaExterna(lcPlanoPagSV, null);
		txtCodPlanoPagSV.setFK(true);
		txtDescPlanoPagSV.setListaCampos(lcPlanoPagSV);

		
		txtCodTab.setNomeCampo("CodTab");
		lcTabPreco.add(new GuardaCampo(txtCodTab, "CodTab", "C�d.tab.p�o.", ListaCampos.DB_PK, false));
		lcTabPreco.add(new GuardaCampo(txtDescTab, "DescTab", "Descri��o da tabela de pre�o", ListaCampos.DB_SI, false));
		lcTabPreco.montaSql(false, "TABPRECO", "VD");
		lcTabPreco.setReadOnly(true);
		txtCodTab.setTabelaExterna(lcTabPreco, null);
		txtCodTab.setFK(true);
		txtDescTab.setListaCampos(lcTabPreco);

		txtCodCli.setNomeCampo("CodCli");
		lcCli.add(new GuardaCampo(txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false));
		lcCli.add(new GuardaCampo(txtDescCli, "NomeCli", "Nome do cliente", ListaCampos.DB_SI, false));
		lcCli.montaSql(false, "CLIENTE", "VD");
		lcCli.setReadOnly(true);
		txtCodCli.setTabelaExterna(lcCli, null);
		txtCodCli.setFK(true);
		txtDescCli.setListaCampos(lcCli);

		txtCodClasCli.setNomeCampo("CodClasCli");
		lcClasCli.add(new GuardaCampo(txtCodClasCli, "CodClasCli", "C�d.c.cli.", ListaCampos.DB_PK, false));
		lcClasCli.add(new GuardaCampo(txtDescClasCli, "DescClasCli", "Descri��o da classifica��o do cliente", ListaCampos.DB_SI, false));
		lcClasCli.montaSql(false, "CLASCLI", "VD");
		lcClasCli.setReadOnly(true);
		txtCodClasCli.setTabelaExterna(lcClasCli, null);
		txtCodClasCli.setFK(true);
		txtDescClasCli.setListaCampos(lcClasCli);

		lcMens.add(new GuardaCampo(txtCodMens, "CodMens", "C�d.Mens.", ListaCampos.DB_PK, null, true));
		lcMens.add(new GuardaCampo(txtDescMens, "Mens", "Mensagem", ListaCampos.DB_SI, null, false));
		lcMens.montaSql(false, "MENSAGEM", "LF");
		lcMens.setQueryCommit(false);
		lcMens.setReadOnly(true);
		txtCodMens.setTabelaExterna(lcMens, null);

		lcMensGeral.add(new GuardaCampo(txtCodMensGeral, "CodMens", "C�d.Mens.", ListaCampos.DB_PK, null, false));
		lcMensGeral.add(new GuardaCampo(txtDescMensGeral, "Mens", "Mensagem", ListaCampos.DB_SI, null, false));
		lcMensGeral.montaSql(false, "MENSAGEM", "LF");
		lcMensGeral.setQueryCommit(false);
		lcMensGeral.setReadOnly(true);
		txtCodMensGeral.setTabelaExterna(lcMensGeral, null);

		lcPlanJR.add(new GuardaCampo(txtCodPlanJR, "CodPlan", "C�d.Plan.JR.", ListaCampos.DB_PK, false));
		lcPlanJR.add(new GuardaCampo(txtDescPlanJR, "DescPlan", "Descri��o do Planejamento", ListaCampos.DB_SI, false));
		lcPlanJR.setWhereAdic("TIPOPLAN = 'R' AND NIVELPLAN = 6");
		lcPlanJR.montaSql(false, "PLANEJAMENTO", "FN");
		lcPlanJR.setQueryCommit(false);
		lcPlanJR.setReadOnly(true);
		txtCodPlanJR.setTabelaExterna(lcPlanJR, null);

		lcPlanJP.add(new GuardaCampo(txtCodPlanJP, "CodPlan", "C�d.Plan.JP.", ListaCampos.DB_PK, false));
		lcPlanJP.add(new GuardaCampo(txtDescPlanJP, "DescPlan", "Descri��o do Planejamento", ListaCampos.DB_SI, false));
		lcPlanJP.setWhereAdic("TIPOPLAN = 'D' AND NIVELPLAN = 6");
		lcPlanJP.montaSql(false, "PLANEJAMENTO", "FN");
		lcPlanJP.setQueryCommit(false);
		lcPlanJP.setReadOnly(true);
		txtCodPlanJP.setTabelaExterna(lcPlanJP, null);

		lcPlanDC.add(new GuardaCampo(txtCodPlanDC, "CodPlan", "C�d.Plan.DC.", ListaCampos.DB_PK, false));
		lcPlanDC.add(new GuardaCampo(txtDescPlanDC, "DescPlan", "Descri��o do Planejamento", ListaCampos.DB_SI, false));
		lcPlanDC.setWhereAdic("TIPOPLAN = 'D' AND NIVELPLAN = 6");
		lcPlanDC.montaSql(false, "PLANEJAMENTO", "FN");
		lcPlanDC.setQueryCommit(false);
		lcPlanDC.setReadOnly(true);
		txtCodPlanDC.setTabelaExterna(lcPlanDC, null);

		lcPlanDR.add(new GuardaCampo(txtCodPlanDR, "CodPlan", "C�d.Plan.DR.", ListaCampos.DB_PK, false));
		lcPlanDR.add(new GuardaCampo(txtDescPlanDR, "DescPlan", "Descri��o do Planejamento", ListaCampos.DB_SI, false));
		lcPlanDR.setWhereAdic("TIPOPLAN = 'R' AND NIVELPLAN = 6");
		lcPlanDR.montaSql(false, "PLANEJAMENTO", "FN");
		lcPlanDR.setQueryCommit(false);
		lcPlanDR.setReadOnly(true);
		txtCodPlanDR.setTabelaExterna(lcPlanDR, null);

		
		lcPlanPC.add(new GuardaCampo(txtCodPlanPC, "CodPlan", "C�d.Plan.JP.", ListaCampos.DB_PK, false));
		lcPlanPC.add(new GuardaCampo(txtDescPlanPC, "DescPlan", "Descri��o do Planejamento", ListaCampos.DB_SI, false));
		lcPlanPC.setWhereAdic("TIPOPLAN = 'D' AND NIVELPLAN = 6");
		lcPlanPC.montaSql(false, "PLANEJAMENTO", "FN");
		lcPlanPC.setQueryCommit(false);
		lcPlanPC.setReadOnly(true);
		txtCodPlanPC.setTabelaExterna(lcPlanPC, null);

		
		// Email NFE
		lcEmailNF.add(new GuardaCampo(txtCodEmailNF, "CodEmail", "C�d.Email", ListaCampos.DB_PK, null, false));
		lcEmailNF.add(new GuardaCampo(txtDescEmailNF, "DescEmail", "Descri��o do Email", ListaCampos.DB_SI, null, false));
		lcEmailNF.montaSql(false, "EMAIL", "TK");
		lcEmailNF.setQueryCommit(false);
		lcEmailNF.setReadOnly(true);
		txtCodEmailNF.setTabelaExterna(lcEmailNF, null);
		txtCodEmailNF.setNomeCampo("CodEmail");

		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();
		vLabs.addElement("Custo MPM");
		vLabs.addElement("Custo PEPS");
		vVals.addElement("M");
		vVals.addElement("P");
		rgTipoPrecoCusto = new JRadioGroup<String, String>(1, 2, vLabs, vVals);
		rgTipoPrecoCusto.setVlrString("M");

		Vector<String> vLabs1 = new Vector<String>();
		Vector<String> vVals1 = new Vector<String>();
		vLabs1.addElement("Por Codigo");
		vLabs1.addElement("Por Descri��o");
		vLabs1.addElement("Por Marca");
		vVals1.addElement("C");
		vVals1.addElement("D");
		vVals1.addElement("M");
		rgOrdNota = new JRadioGroup<String, String>(3, 1, vLabs1, vVals1);
		rgOrdNota.setVlrString("C");

		Vector<String> vLabs2 = new Vector<String>();
		Vector<String> vVals2 = new Vector<String>();
		vLabs2.addElement("Cliente/Setor");
		vLabs2.addElement("Comissionado/Setor");
		vLabs2.addElement("Ambos");
		vVals2.addElement("C");
		vVals2.addElement("V");
		vVals2.addElement("A");
		rgSetorVenda = new JRadioGroup<String, String>(3, 1, vLabs2, vVals2);
		rgSetorVenda.setVlrString("C");

		Vector<String> vLabs6 = new Vector<String>();
		Vector<String> vVals6 = new Vector<String>();
		vLabs6.addElement("Na aplica��o");
		vLabs6.addElement("No jasper");
		vVals6.addElement("QA");
		vVals6.addElement("QJ");
		rgTipoClass = new JRadioGroup<String, String>(1, 2, vLabs6, vVals6);
		rgTipoClass.setVlrString("QA");

		Vector<String> vLabs3 = new Vector<String>();
		Vector<String> vVals3 = new Vector<String>();

		vLabs3.addElement("N�o vericar");
		vLabs3.addElement("Consulta cr�dito pr�-aprovado");

		vVals3.addElement("N");
		vVals3.addElement("L");

		rgLibCred = new JRadioGroup<String, String>(2, 1, vLabs3, vVals3);
		rgLibCred.setVlrString("N");

		Vector<String> vLabsTpValidOrc1 = new Vector<String>();
		Vector<String> vValsTpValidOrc1 = new Vector<String>();
		vLabsTpValidOrc1.addElement("Data");
		vLabsTpValidOrc1.addElement("Nro. de dias");
		vValsTpValidOrc1.addElement("D");
		vValsTpValidOrc1.addElement("N");
		rgTipoValidOrc = new JRadioGroup<String, String>(1, 2, vLabsTpValidOrc1, vValsTpValidOrc1);
		rgTipoValidOrc.setVlrString("D");
		
		Vector<String> vLabsTpNossoNumero = new Vector<String>();
		Vector<String> vValsTpNossoNumero = new Vector<String>();
		
		vLabsTpNossoNumero.addElement("Documento");
		vLabsTpNossoNumero.addElement("Cod.Receber");
		vLabsTpNossoNumero.addElement("Sequencial �nico");
		
		vValsTpNossoNumero.addElement("D");
		vValsTpNossoNumero.addElement("R");
		vValsTpNossoNumero.addElement("S");
		
		rgTpNossoNumero = new JRadioGroup<String, String>(1, 3, vLabsTpNossoNumero, vValsTpNossoNumero);
		rgTpNossoNumero.setVlrString("D");
		
		cbImpDocBol.setVlrString("N");
		cbFechaCaixa.setVlrString("N");
		cbFechaCaixaAuto.setVlrString("N");
		
		rgTipoValidOrc = new JRadioGroup<String, String>(1, 2, vLabsTpValidOrc1, vValsTpValidOrc1);
		rgTipoValidOrc.setVlrString("D");

		Vector<Integer> vValsTipo = new Vector<Integer>();
		Vector<String> vLabsTipo = new Vector<String>();
		vLabsTipo.addElement("<--Selecione-->");
		vLabsTipo.addElement("50 caracteres");
		vLabsTipo.addElement("100 caracteres");
		vValsTipo.addElement(new Integer(0));
		vValsTipo.addElement(new Integer(50));
		vValsTipo.addElement(new Integer(100));
		cbTamDescProd = new JComboBoxPad(vLabsTipo, vValsTipo, JComboBoxPad.TP_INTEGER, 4, 0);

		Vector<String> vLabsCtb = new Vector<String>();
		Vector<String> vValsCtb = new Vector<String>();

		vLabsCtb.addElement("<--Selecione-->");
		vLabsCtb.addElement("Freedom Contabil");
		vLabsCtb.addElement("Safe Contabil");
		vLabsCtb.addElement("EBS Contabil");
		vValsCtb.addElement("00");
		vValsCtb.addElement("01");
		vValsCtb.addElement("02");
		vValsCtb.addElement("03");

		cbSisContabil = new JComboBoxPad(vLabsCtb, vValsCtb, JComboBoxPad.TP_STRING, 2, 0);

		
		Vector<String> vLabsVersaoNFE = new Vector<String>();
		Vector<String> vValsVersaoNFE = new Vector<String>();

		vLabsVersaoNFE.addElement("<--Selecione-->");
		vLabsVersaoNFE.addElement(AbstractNFEFactory.VERSAO_LAYOUT_NFE_01.getName());
		vLabsVersaoNFE.addElement(AbstractNFEFactory.VERSAO_LAYOUT_NFE_02.getName());
		vValsVersaoNFE.addElement("000");
		vValsVersaoNFE.addElement(AbstractNFEFactory.VERSAO_LAYOUT_NFE_01.getValue().toString());
		vValsVersaoNFE.addElement(AbstractNFEFactory.VERSAO_LAYOUT_NFE_02.getValue().toString());

		cbVersaoNFE = new JComboBoxPad(vLabsVersaoNFE, vValsVersaoNFE, JComboBoxPad.TP_STRING, 6, 0);
		
		
		Vector<String> vLabsRegimeNFE = new Vector<String>();
		Vector<String> vValsRegimeNFE = new Vector<String>();

		vLabsRegimeNFE.addElement("<--Selecione-->");
		vLabsRegimeNFE.addElement(AbstractNFEFactory.REGIME_TRIB_NFE_SIMPLES.getName());
		vLabsRegimeNFE.addElement(AbstractNFEFactory.REGIME_TRIB_NFE_SIMPLES_EX.getName());
		vLabsRegimeNFE.addElement(AbstractNFEFactory.REGIME_TRIB_NFE_NORMAL.getName());
		vValsRegimeNFE.addElement("0");
		vValsRegimeNFE.addElement(AbstractNFEFactory.REGIME_TRIB_NFE_SIMPLES.getValue().toString());
		vValsRegimeNFE.addElement(AbstractNFEFactory.REGIME_TRIB_NFE_SIMPLES_EX.getValue().toString());
		vValsRegimeNFE.addElement(AbstractNFEFactory.REGIME_TRIB_NFE_NORMAL.getValue().toString());

		
		cbRegimeNFE = new JComboBoxPad(vLabsRegimeNFE, vValsRegimeNFE, JComboBoxPad.TP_STRING, 1, 0);
		
		// Geral

		setPainel(pinGeral);
		adicTab("Geral", pinGeral);

		adic(pinCentrosdecustoGeral, 7, 10, 170, 155);
		adic(pinCasasDecGeral, 180, 10, 167, 155);
		adic(pinValidacoesGeral, 350, 10, 340, 155);

		adic(pinOpcoesGeral, 7, 170, 340, 150);
		adic(pinConsistenciasGeral, 350, 170, 340, 150);

		pinCentrosdecustoGeral.setBorder(SwingParams.getPanelLabel("Centro de custo", Color.BLUE));
		pinCasasDecGeral.setBorder(SwingParams.getPanelLabel("Casas decimais", Color.BLUE));
		pinConsistenciasGeral.setBorder(SwingParams.getPanelLabel("Consist�ncias", Color.BLUE));
		pinOpcoesGeral.setBorder(SwingParams.getPanelLabel("Op��es", Color.BLUE));
		pinValidacoesGeral.setBorder(SwingParams.getPanelLabel("Valida��es", Color.BLUE));

		setPainel(pinCentrosdecustoGeral);

		adicCampo(txtAnoCC, 7, 20, 110, 20, "AnoCentroCusto", "Ano Base", ListaCampos.DB_SI, true);

		setPainel(pinCasasDecGeral);

		adicCampo(txtCasasDecFin, 7, 15, 110, 20, "CasasDecFin", "Financeiro", ListaCampos.DB_SI, true);
		adicCampo(txtCasasDec, 7, 55, 110, 20, "CasasDec", "Geral", ListaCampos.DB_SI, true);
		adicCampo(txtCasasDecPre, 7, 95, 110, 20, "CasasDecPre", "Pre�o", ListaCampos.DB_SI, true);

		setPainel(pinOpcoesGeral);

		adicDB(cbGeraCodUnif, 7, 0, 500, 20, "GeraCodUnif", "", true);
		adicDB(cbEstNeg, 7, 20, 160, 20, "EstNeg", "", true);
		adicDB(cbEstLotNeg, 7, 40, 200, 20, "EstLotNeg", "", true);
		adicDB(cbEstNegGrupo, 7, 60, 250, 20, "EstNegGrup", "", true);


		setPainel(pinConsistenciasGeral);

		adicDB(cbRgCliObrig, 7, 0, 280, 20, "RgCliObrig", "", true);
		adicDB(cbCnpjCliObrig, 7, 20, 300, 20, "CnpjObrigCli", "", true);
		adicDB(cbCnpjForObrig, 7, 40, 320, 20, "CnpjForObrig", "", true);
		adicDB(cbInscEstForObrig, 7, 60, 400, 20, "InscEstForObrig", "", true);
		adicDB(cbCliMesmoCnpj, 7, 80, 250, 20, "CliMesmoCnpj", "", true);
		adicDB(cbUsuAtivCli, 7, 100, 400, 20, "UsuAtivCli", "", true);

		setPainel(pinValidacoesGeral);

		adicDB(cbConsIECli, 7, 0, 400, 20, "ConsisteIECli", "", true);
		adicDB(cbConsIECliFisica, 7, 20, 400, 20, "ConsisteIEPF", "", true);
		adicDB(cbConsIEFor, 7, 40, 400, 20, "ConsisteIEFor", "", true);
		adicDB(cbConsCPFCli, 7, 60, 400, 20, "ConsistCPFCli", "", true);
		adicDB(cbConsCPFFor, 7, 80, 400, 20, "ConsisteCPFFor", "", true);

		// Venda

		setPainel(pinVenda);
		adicTab("Venda", pinVenda);

		adicDB(rgOrdNota, 7, 25, 160, 80, "OrdNota", " Ordem de Emiss�o", true);

		adicCampo(txtCodTipoMov3, 7, 130, 75, 20, "CodTipoMov3", "C�d.tp.mov", ListaCampos.DB_FK, txtDescTipoMov3, false);
		adicDescFK(txtDescTipoMov3, 85, 130, 250, 20, "DescTipoMov", "Tipo de movimento para pedido.");
		adicCampo(txtCodTipoMov, 7, 180, 75, 20, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, false);
		adicDescFK(txtDescTipoMov, 85, 180, 250, 20, "DescTipoMov", "Tipo de movimento para NF.");
		adicCampo(txtCodTipoMov4, 7, 230, 75, 20, "CodTipoMov4", "C�d.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov4, false);
		adicDescFK(txtDescTipoMov4, 85, 230, 250, 20, "DescTipoMov", "Tipo de movimento para pedido (servi�o).");
		adicCampo(txtCodTransp, 7, 280, 75, 20, "CodTran", "C�d.tran.", ListaCampos.DB_FK, txtDescTransp, false);
		adicDescFK(txtDescTransp, 85, 280, 250, 20, "RazTran", "Raz�o social da transp. padr�o para venda");
		adicCampo(txtDescClassPed, 7, 330, 250, 20, "ClassPed", "Layout padr�o para pedido.", ListaCampos.DB_SI, false);
		adicDB(rgTipoClass, 7, 370, 250, 30, "TipoClassPed", "Tipo de query", false);

		adic(pinOpcoesVenda, 348, 5, 560, 420);
		pinOpcoesVenda.setBorder(BorderFactory.createTitledBorder(opcoes));
		setPainel(pinOpcoesVenda);

		adicDB(cbUsaPedSeq, 5, 0, 160, 20, "UsaPedSeq", "", true);
		adicDB(cbPrazoEnt, 5, 20, 200, 20, "UsaTabPE", "", true);
		adicDB(cbDiasPEData, 5, 40, 200, 20, "DIASPEDT", "", true);
		adicDB(cbReCalcVenda, 5, 60, 200, 20, "ReCalcPCVenda", "", true);
		adicDB(cbVendaMatPrim, 5, 80, 250, 20, "VendaMatPrim", "", true);
		adicDB(cbTravaTMNFVD, 5, 100, 268, 20, "TravaTMNFVD", "", true);
		adicDB(cbBloqVenda, 5, 120, 265, 20, "BloqVenda", "", true);

		adicDB(cbLayoutPed, 5, 140, 280, 20, "UsaLayoutPed", "", true);
		adicDB(cbObsCliVend, 5, 160, 288, 20, "ObsCliVend", "", true);
		adicDB(cbVerifAltParVenda, 5, 180, 350, 20, "VerifAltParcVenda", "", true);
		adicDB(cbUsaPrecoZero, 5, 200, 350, 20, "UsaPrecoZero", "", true);

		adicDB(cbIcmsFrete, 5, 220, 260, 20, "AdicFreteBaseICM", "", true);
		adicDB(cbGeraComisVendaOrc, 5, 240, 400, 20, "GeraComisVendaOrc", "", true);
		adicDB(cbInfVdRemessa, 5, 260, 400, 20, "InfVdRemessa", "", true);

		adicDB(cbTabFreteVd, 290, 0, 180, 20, "TabFreteVd", "", true);
		adicDB(cbTabAdicVd, 290, 20, 180, 20, "TabAdicVd", "", true);
		adicDB(cbUsaDescEspelho, 290, 40, 180, 20, "UsaLiqRel", "", true);
		adicDB(cbIPIVenda, 290, 60, 180, 20, "IPIVenda", "", true);
		adicDB(cbNatVenda, 290, 80, 180, 20, "NatVenda", "", true);
		adicDB(cbIcmsVenda, 290, 100, 180, 20, "IcmsVenda", "", true);
		adicDB(cbSomaVolumes, 290, 120, 180, 20, "SomaVolumes", "", true);
		adicDB(cbVendaImobilizado, 290, 140, 210, 20, "VendaPatrim", "", true);
		adicDB(cbVendaMatConsumo, 290, 160, 260, 20, "VendaConsum", "", true);
		adicDB(cbVisualizaLucr, 290, 180, 240, 20, "VisualizaLucr", "", true);

		// Compra

		setPainel(pinCompra);
		
		adicTab("Compras", pinCompra);

		adic(pinCompras, 7, 5, 285, 420);
		adic(pinComprasCotacao, 300, 5, 350, 210);
		adic(pinComprasImportacao, 300, 225, 350, 210);
		
		pinCompras.setBorder(SwingParams.getPanelLabel("Op��es", Color.BLUE));		
		pinComprasCotacao.setBorder(SwingParams.getPanelLabel("Cota��es", Color.BLUE));
		pinComprasImportacao.setBorder(SwingParams.getPanelLabel("Importa��o", Color.BLUE));
		
		setPainel(pinCompras);

//		adicDB(cbUsaRefCompra, 7, 15, 200, 20, "UsaRefCompra", "", false);
		adicDB(cbTransAbaCp, 7, 15, 250, 20, "TabTranspCp", "", false);
		adicDB(cbImportAbaCp, 7, 35, 250, 20, "TabImportCp", "", false);
		adicDB(cbPrecoRel, 7, 55, 270, 20, "PrecoCpRel", "", false);
		adicDB(cbHabiitaCustoCompra, 7, 75, 300, 20, "CustoCompra", "", true);
		adicDB(cbInfCPDevolucao, 7, 95, 300, 20, "INFCPDEVOLUCAO", "", true);
		adicDB(cbUsaBuscGenProdCP, 7, 115, 300, 20, "USABUSCAGENPRODCP", "", true);
		adicDB(cbRevalidarLoteCompra, 7, 135, 300, 20, "REVALIDARLOTECOMPRA", "", true);

		adicCampo(txtDescClassCp, 11, 200, 250, 20, "ClassCp", "Layout padr�o para pedido de compra.", ListaCampos.DB_SI, false);
		adicCampo(txtObs01, 11, 240, 250, 20, "LabelObs01Cp", "Descri��o para campo Obs01.", ListaCampos.DB_SI, false);
		adicCampo(txtObs02, 11, 280, 250, 20, "LabelObs02Cp", "Descri��o para campo Obs02.", ListaCampos.DB_SI, false);
		adicCampo(txtObs03, 11, 320, 250, 20, "LabelObs03Cp", "Descri��o para campo Obs03.", ListaCampos.DB_SI, false);
		adicCampo(txtObs04, 11, 360, 250, 20, "LabelObs04Cp", "Descri��o para campo Obs04.", ListaCampos.DB_SI, false);
		
		setPainel(pinComprasCotacao);
		
		adicDB(cbPrecoCotacao, 7, 15, 200, 20, "UsaPrecoCot", "", false);
		adicDB(cbBloqPrecoAprov, 7, 35, 200, 20, "BloqPrecoAprov", "", false);
		
		setPainel(pinComprasImportacao);
		
		adicCampo(txtCodTipoMovImp	, 7		, 20	, 80	, 20	, "CodTipoMovIm", "Cod.tp.mov.", ListaCampos.DB_FK, txtDescTipoMovImp, false);
		adicDescFK(txtDescTipoMovImp, 90	, 20	, 240	, 20	, "DescTipoMovIm", "Tipo de movimento para importa��o");
		txtCodTipoMovImp.setNomeCampo("CodTipoMov");
		
		
		// Pre�o

		setPainel(pinPreco);
		adicTab("Pre�os", pinPreco);

		lbPrcCont.setBorder(BorderFactory.createEtchedBorder(1));
		lbPrcOpcoes.setOpaque(true);

		adicCampo(txtCodTab, 7, 25, 90, 20, "CodTab", "C�d.tab.pc.", ListaCampos.DB_FK, txtDescTab, false);
		adicDescFK(txtDescTab, 100, 25, 300, 20, "DescTab", "Descri��o da tabela de pre�os");
		adicCampo(txtCodPlanoPag, 7, 65, 90, 20, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_FK, txtDescPlanoPag, false);
		adicDescFK(txtDescPlanoPag, 100, 65, 300, 20, "DescPlanoPag", "Descri��o do plano de pagamento");
		adicCampo(txtCodClasCli, 7, 105, 90, 20, "CodClasCli", "C�d.c.cli", ListaCampos.DB_FK, txtDescClasCli, false);
		adicDescFK(txtDescClasCli, 100, 105, 300, 20, "DescClasCli", "Descri��o da classifica��o dos clientes");

		adic(lbPrcOpcoes, 17, 130, 70, 20);
		adic(lbPrcCont, 7, 140, 393, 140);
		adicDB(rgTipoPrecoCusto, 17, 170, 373, 30, "TipoPrecoCusto", "Controle do pre�o sobre o custo:", false);
		adicCampo(txtPercPrecoCusto, 17, 220, 100, 20, "PercPrecoCusto", "% Min. custo", ListaCampos.DB_SI, true);
		adicDB(cbCustosSICMS, 17, 250, 280, 20, "CustoSICMS", "", true);

		// Or�amento e PDV (SGPREFERE1)

		setPainel(pinOrcamento);

		adicTab("Or�amento & PDV", pinOrcamento);

		adicCampo(txtCodTipoMov2, 7, 25, 90, 20, "CodTipoMov2", "Cod.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, false);
		adicDescFK(txtDescTipoMov2, 100, 25, 300, 20, "DescTipoMov", "Tipo de movimento para or�amentos.");

		adicCampo(txtCodTipoMovS, 403, 25, 50, 20, "CodTipoMovS", "Cod.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, false);
		adicDescFK(txtDescTipoMovS, 456, 25, 270, 20, "DescTipoMov", "Tipo de movimento para or�amentos (Servi�o)");
		
		adicCampo(txtDescClassOrc, 403, 185, 322, 20, "ClassOrc", "Classe padr�o para or�amento.", ListaCampos.DB_SI, false);
		adicCampo(txtDescOrc, 403, 65, 322, 20, "DescOrc", "Descri��o do t�tulo do or�amento.", ListaCampos.DB_SI, false);

		adicCampo(txtTitOrcTxt01, 403, 105, 322, 20, "TitOrcTxt01", "T�tulo para campo TXT01", ListaCampos.DB_SI, false);

		adicDB(rgTipoCustoLuc, 7, 185, 390, 70, "TipoCustoLuc", "Tipo de custo para calculo da lucratividade", true);
		adicDB(rgTipoValidOrc, 403, 225, 322, 30, "tipovalidorc", "Validade na impress�o", true);

		setPainel(pnOpcoesOrc);

		adicDB(cbUsaOrcSeq, 10, 0, 160, 20, "UsaOrcSeq", "", true);
		adicDB(cbReCalcOrc, 10, 20, 250, 20, "ReCalcPCOrc", "", true);
		adicDB(cbUsaImgOrc, 10, 40, 300, 20, "UsaImgAssOrc", "", true);
		
		adicDB(cbAdicCodOrcObsPed, 10, 60, 370, 20, "ADICORCOBSPED", "", false);
		adicDB(cbAdicObsOrcPed, 10, 80, 370, 20, "ADICOBSORCPED", "", false);
		adicDB(cbMostraTransp, 10, 100, 370, 20, "TabTranspOrc", "", true);
		adicDB(cbHabVlrTotItOrc, 10, 120, 370, 20, "HabVlrTotItOrc", "", true);
		adicDB(cbEncOrcProd, 10, 140, 370, 20, "EncOrcProd", "", true);
		
		
		adicDB(imgAssOrc, 405, 60, 300, 95, "ImgAssOrc", "Assinatura", false);
		

		// Financeiro

		setPainel(pinFinanceiro);
		adicTab("Financeiro", pinFinanceiro);

		Vector<String> vLabs5 = new Vector<String>();
		Vector<String> vVals5 = new Vector<String>();
		vLabs5.addElement("Fechamento");
		vLabs5.addElement("Item da venda");
		vLabs5.addElement("Ambos");
		vVals5.addElement("FV");
		vVals5.addElement("II");
		vVals5.addElement("AB");

		rgTipoCred = new JRadioGroup<String, String>(2, 2, vLabs5, vVals5);
		rgTipoCred.setVlrString("AB");

		lbFinDefinicoes.setBorder(BorderFactory.createTitledBorder("Defini��es"));
		adic(lbFinDefinicoes, 7, 10, 485, 230);

		adicCampo(txtCodMoeda, 20, 50, 80, 20, "CodMoeda", "C�d.moeda", ListaCampos.DB_FK, txtDescMoeda, true);
		adicDescFK(txtDescMoeda, 100, 50, 320, 20, "SingMoeda", "Descri��o da moeda corrente.");

		adicCampo(txtCodTabJuros, 20, 90, 80, 20, "CodTbj", "C�d.tab.jr.", ListaCampos.DB_FK, txtDescTabJuros, false);
		adicDescFK(txtDescTabJuros, 100, 90, 320, 20, "DescTbj", "Descri��o da tabela de juros.");

		adicCampo(txtCodHistRec, 20, 130, 80, 20, "CodHistRec", "C�d.Hist.Rec.", ListaCampos.DB_FK, txtDescHistRec, false);
		adicDescFK(txtDescHistRec, 100, 130, 320, 20, "DescHist", "Descri��o do hist�rico padr�o para contas a receber");

		adicCampo(txtCodHistPag, 20, 170, 80, 20, "CodHistPag", "C�d.Hist.Pag.", ListaCampos.DB_FK, txtDescHistPag, false);
		adicDescFK(txtDescHistPag, 100, 170, 320, 20, "DescHist", "Descri��o do hist�rico padr�o para contas a pagar");

		adicCampo(txtCodPlanoPagSV, 20, 210, 80, 20, "CodPlanoPagSV", "C�d.Plan.Pag.", ListaCampos.DB_FK, txtDescHistPag, false);
		adicDescFK(txtDescPlanoPagSV, 100, 210, 320, 20, "DescPlanoPag", "Descri��o do plano de pagamento padr�o s/valor financeiro");

		
		lbFinContas.setBorder(BorderFactory.createTitledBorder("Contas"));
		adic(lbFinContas, 500, 10, 370, 240);

		adicCampo(txtCodPlanJR, 510, 50, 100, 20, "CodPlanJR", "C�d.Plan.JR.", ListaCampos.DB_FK, txtDescPlanJR, false);
		adicDescFK(txtDescPlanJR, 613, 50, 240, 20, "DescPlan", "Planejamento para juros recebidos");

		adicCampo(txtCodPlanJP, 510, 90, 100, 20, "CodPlanJP", "C�d.Plan.JP.", ListaCampos.DB_FK, txtDescPlanJP, false);
		adicDescFK(txtDescPlanJP, 613, 90, 240, 20, "DescPlan", "Planejamento para juros pagos");

		adicCampo(txtCodPlanDC, 510, 130, 100, 20, "CodPlanDC", "C�d.Plan.DC.", ListaCampos.DB_FK, txtDescPlanDC, false);
		adicDescFK(txtDescPlanDC, 613, 130, 240, 20, "DescPlan", "Planejamento para descontos concedidos");

		adicCampo(txtCodPlanDR, 510, 170, 100, 20, "CodPlanDR", "C�d.Plan.DR.", ListaCampos.DB_FK, txtDescPlanDR, false);
		adicDescFK(txtDescPlanDR, 613, 170, 240, 20, "DescPlan", "Planejamento para descontos obtidos");

		adicCampo(txtCodPlanPC, 510, 210, 100, 20, "CodPlanPC", "C�d.Plan.PC.", ListaCampos.DB_FK, txtDescPlanDR, false);
		adicDescFK(txtDescPlanPC, 613, 210, 240, 20, "DescPlan", "Planejamento p/pagto com cheques");

		lbFinOpcoes.setBorder(BorderFactory.createTitledBorder(opcoes));
		adic(lbFinOpcoes, 500, 255, 370, 215);

		adicDB(cbAltItRecImpBol, 510, 275, 310, 20, "AtBancoImpBol", "", false);
		adicDB(cbJurosPosCalc, 510, 295, 310, 20, "JurosPosCalc", "", false);
		adicDB(cbEstItRecAltDtVenc, 510, 315, 350, 20, "EstItRecAltDtVenc", "", false);
		adicDB(cbLiberacaoCreGlobal, 510, 335, 350, 20, "LcRedGlobal", "", false);

		adicDB(cbGeraPagEmis, 510, 355, 350, 20, "GeraPagEmis", "", true);
		adicDB(cbGeraRecEmis, 510, 375, 350, 20, "GeraRecEmis", "", true);
		adicDB(cbImpDocBol, 510, 395, 350, 20, "ImpDocBol", "", true);
		adicDB(cbFechaCaixa, 510, 415, 350, 20, "FechaCaixa", "", true);
		adicDB(cbFechaCaixaAuto, 510, 435, 350, 20, "FechaCaixaAuto", "", true);


		lbFinPagar.setBorder(BorderFactory.createTitledBorder("Contratos/Projetos"));
		adic(lbFinPagar, 10, 245, 485, 50);

		adicDB(cbLancaFinContr, 17, 265, 350, 20, "LancaFinContr", "", false);

		lbFinLibCred.setBorder(BorderFactory.createTitledBorder("Libera��o de cr�dito"));
		adic(lbFinLibCred, 7, 300, 485, 90);

		adicDB(rgLibCred, 20, 325, 220, 55, "PrefCred", "", true);
		adicDB(rgTipoCred, 243, 325, 235, 55, "TipoPrefCred", "", true);

		lbTPNossoNumero.setBorder(BorderFactory.createTitledBorder("Tipo do nosso n�mero (boletos/remessa)"));
		adic(lbTPNossoNumero, 7, 395, 485, 70);

		adicDB(rgTpNossoNumero, 20, 420, 452, 30, "tpnossonumero", "", true);	
		
		adicCampo(txtNumDigIdentTit, 7, 490, 100, 20, "NumDigIdentTit", "Nr.Dig.Ident.Tit.", ListaCampos.DB_SI, false);
		
		
		// Contabil

		setPainel(pinContabil);
		adicTab("Contabil", pinContabil);

		lbCtbCont.setBorder(BorderFactory.createEtchedBorder(1));
		lbCtbOpcoes.setOpaque(true);

		adic(lbCtbOpcoes, 17, 10, 70, 20);
		adic(lbCtbCont, 7, 20, 393, 80);
		adicDB(cbSisContabil, 17, 55, 373, 20, "SisContabil", "Sistema para exporta��o", false);

		// Fiscal

		setPainel(pinFiscal);
		adicTab("Fiscal", pinFiscal);

		adic(pinSimples, 7, 10, 430, 110);

		pinSimples.setBorder(SwingParams.getPanelLabel("Simples", Color.BLUE));

		setPainel(pinSimples);

		adicDB(cbCredIcmsSimples, 7, 0, 300, 20, "CredIcmsSimples", "", true);
		adicCampo(txtCodMensGeral, 7, 50, 70, 20, "CodMensIcmsSimples", "C�d.Mens.", ListaCampos.DB_FK, txtDescMensGeral, false);
		adicDescFK(txtDescMensGeral, 80, 50, 330, 20, "mens", " Mensagem para destaque de cr�dito de ICMS");

		/** ABA DE PARAMETROS DE COMISSIONAMENTO **/
		
		setPainel(pinComissionamento);
		adicTab("Comissionamento", pinComissionamento);
		
		adic(pinOpcoesComissionamento, 7, 10, 430, 195);
		adic(pinRegrasComissionamento, 440, 10, 430, 195);
		
		pinOpcoesComissionamento.setBorder(SwingParams.getPanelLabel("Op��es", Color.BLUE));
		pinRegrasComissionamento.setBorder(SwingParams.getPanelLabel("Regras", Color.BLUE));
		
		setPainel(pinOpcoesComissionamento);
		
		adicDB(cbMultiComis, 5, 5, 250, 20, "MultiComis", "", true);	
		adicDB(cbComisPDupl, 5, 25, 280, 20, "ComisPDupl", "", true);				
		adicDB(cbUsaClasComis, 5, 45, 250, 20, "UsaClasComis", "", true);		
		adicDB(cbUsaNomeVendOrc, 5, 65, 300, 20, "UsaNomeVendOrc", "", true);		
		adicDB(cbComissManut, 5, 85, 350, 20, "VDManutComObrig", "", false);
		adicDB(cbUsaPrecoComis, 5, 105, 410, 20, "UsaPrecoComis", "", false);
		adicDB(cbEspecialComis, 5, 125, 410, 20, "EspecialComis", "", false);
		adicDB(cbHabilitaLimiteDesconto, 5, 145, 410, 20, "ComissaoDesconto", "", true);
		
		setPainel(pinRegrasComissionamento);
		
		adicDB(rgSetorVenda, 5, 15, 160, 80, "SetorVenda", "Distrib. dos setores", true);

		
		/** FIM DE PARAMETROS DE COMISSIONAMENTO **/
		
		
		// SVV

		setPainel(pinSVV_LIC);
		adicTab("SVV/Licenciamento", pinSVV_LIC);

		adicCampo(txtCodFor, 7, 25, 90, 20, "CodFor", "C�d.for.", ListaCampos.DB_FK, txtDescFor, false);
		adicDescFK(txtDescFor, 100, 25, 300, 20, "DescFor", "Raz�o social do fornecedor");
		adicCampo(txtCodMarca, 7, 65, 90, 20, "CodMarca", "C�d.marca", ListaCampos.DB_FK, txtDescMarca, false);
		adicDescFK(txtDescMarca, 100, 65, 300, 20, "DescMarca", "Descri��o da marca.");
		adicCampo(txtCodGrup, 7, 105, 90, 20, "CodGrup", "C�d.grupo", ListaCampos.DB_FK, txtDescGrup, false);
		adicDescFK(txtDescGrup, 100, 105, 300, 20, "DescGrup", "Descri��o do grupo.");

		// Devolu��o

		setPainel(pinDev);
		adicTab("Devolu��o", pinDev);

		adicCampo(txtCodTipoFor, 7, 25, 90, 20, "CodTipoFor", "C�d.tp.for.", ListaCampos.DB_FK, txtDescTipoForFT, false);
		adicDescFK(txtDescTipoFor, 100, 25, 300, 20, "DescTipoFor", "Descri��o do tipo de fornecedor");
		adicCampo(txtCodTipoMov5, 7, 65, 90, 20, "CodTipoMov5", "C�d.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, false);
		adicDescFK(txtDescTipoMov5, 100, 65, 300, 20, "DescTipoMov", "Descri��o do tipo de movimento 5");
		adicCampo(txtCodTipoCli, 7, 105, 90, 20, "CodTipoCli", "C�d.tp.cli.", ListaCampos.DB_FK, txtDescTipoCli, false);
		adicDescFK(txtDescTipoCli, 100, 105, 300, 20, "DescTipoCli", "Descri��o do tipo de Cliente");

		// Produto
		setPainel(pinProd);
		adicTab("Produto", pinProd);
		
		pnProdOpcoes.setBorder(BorderFactory.createTitledBorder(opcoes));
		adic(pnProdOpcoes, 7, 20, 400, 335);
		pnProdOpCad.setBorder(BorderFactory.createTitledBorder("Op��es de cadastro padr�o"));
		adic(pnProdOpCad, 415, 20, 480, 335);
		
		setPainel(pnProdOpcoes);

		Vector<String> vLabs4 = new Vector<String>();
		Vector<String> vVals4 = new Vector<String>();
		vLabs4.addElement("EAN 13");
		vLabs4.addElement("39");
		vVals4.addElement("1");
		vVals4.addElement("2");

		rgCodBar = new JRadioGroup<String, String>(1, 2, vLabs4, vVals4);
		rgCodBar.setVlrString("2");

		adicDB(cbPepsProd, 7, 5, 310, 20, "PepsProd", "", false);
		adicDB(cbBuscaProdSimilar, 7, 25, 310, 20, "BuscaProdSimilar", "", false);
		adicDB(cbDescCompl, 7, 45, 480, 20, "DescCompPed", "", true);
		adicDB(cbUsaBuscGenProd, 7, 65, 350, 20, "BUSCACODPRODGEN", "", false);

		adicDB(cbFilBuscGenProd1, 27, 85, 350, 20, "FILBUSCGENPROD", "", false);
		adicDB(cbFilBuscGenProd2, 27, 105, 350, 20, "FILBUSCGENREF", "", false);
		adicDB(cbFilBuscGenProd3, 27, 125, 350, 20, "FILBUSCGENCODBAR", "", false);
		adicDB(cbFilBuscGenProd4, 27, 145, 350, 20, "FILBUSCGENCODFAB", "", false);
		adicDB(cbFilBuscGenProd5, 27, 165, 350, 20, "FILBUSCGENCODFOR", "", false);

		adicDB(cbUsaRefProd, 7, 185, 160, 20, "UsaRefProd", "", true);
		adicDB(cbTamDescProd, 7, 225, 373, 20, "TamDescProd", "Tamanho da descri��o do produto", false);
		adic(new JLabelPad("Tipo de c�digo de barras"), 7, 255, 200, 20);
		adicDB(rgCodBar, 7, 275, 180, 25, "TipoCodBar", "", false);
		
		setPainel(pnProdOpCad);
		
		Vector<String> vValsCV = new Vector<String>();
		Vector<String> vLabsCV = new Vector<String>();
		vValsCV.addElement( "C" );
		vValsCV.addElement( "V" );
		vValsCV.addElement( "A" );
		vLabsCV.addElement( "Compra" );
		vLabsCV.addElement( "Venda" );
		vLabsCV.addElement( "Ambos" );
		rgCV = new JRadioGroup<String, String>( 3, 1, vLabsCV, vValsCV );
//		rgCV.setVlrString( "V" );
		
		Vector<String> vLabsBCusto = new Vector<String>();
		Vector<String> vValsBCusto = new Vector<String>();
		vValsBCusto.addElement( "N" );
		vValsBCusto.addElement( "S" );
		vValsBCusto.addElement( "L" );
		vLabsBCusto.addElement( "Bloqueado" );
		vLabsBCusto.addElement( "Senha" );
		vLabsBCusto.addElement( "Liberado" );
		rgAbaixCust = new JRadioGroup<String, String>( 3, 1, vLabsBCusto, vValsBCusto );
//		rgAbaixCust.setVlrString( "N" );
		
		adicDB( cbRMA, 7, 5, 300, 20, "RMAPROD", "", false );
		adicDB( rgCV, 7, 50, 115, 70, "CVPROD", "Cadastro para:", false );
		adicDB( rgAbaixCust, 135, 50, 115, 70, "VERIFPROD", "Abaixo custo:", false );
		
		JPanelPad pnClassificacao = new JPanelPad();
		pnClassificacao.setBorder(BorderFactory.createTitledBorder("Classifica��o"));

		adic( pnClassificacao, 7, 125, 460, 160 );
		setPainel( pnClassificacao );
		
		rgTipoProd = new JRadioGroup<String, String>( 5, 2, TipoProd.getLabels(), TipoProd.getValores() );
		rgTipoProd.setFont( SwingParams.getFontboldmed() );
//		rgTipoProd.setVlrString( "P" );
		
		adicDB( rgTipoProd, 7, 0, 440, 130, "TIPOPROD", "Tipo:", false );

		// Estoque
		setPainel(pinEstoq);
		adicTab("Estoque", pinEstoq);

		pnEstOpcoes.setBorder(BorderFactory.createTitledBorder(opcoes));

		adicCampo(txtCodTipoMov6, 7, 25, 90, 20, "CodTipoMov6", "C�d.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, false);
		adicDescFK(txtDescTipoMov6, 100, 25, 300, 20, "DescTipoMov", "Descri��o do tp. mov. para invent�rio");
		adicCampo(txtCodTipoMov8, 7, 65, 90, 20, "CodTipoMov8", "C�d.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov8, false);
		adicDescFK(txtDescTipoMov8, 100, 65, 300, 20, "DescTipoMov", "Descri��o do tp.mov. para RMA");

		adic(pnEstOpcoes, 7, 100, 393, 130);

		setPainel(pnEstOpcoes);

		adicDB(cbContEstoq, 7, 0, 250, 20, "ContEstoq", "", true);
		adicDB(cbMultiAlmox, 7, 20, 250, 20, "MultiAlmox", "", true);
		adicDB(cbBloqCompra, 7, 40, 250, 20, "BloqCompra", "", true);
		adicDB(cbBuscaVlrUltCompra, 7, 60, 250, 20, "BuscaVlrUltCompra", "", true);
		adicDB(cbLancaRMAContr, 7, 80, 300, 20, "LancaRMAContr", "", false);

		nav.setAtivo(0, false);
		lcCampos.setPodeExc(false);
		lcCampos.addPostListener(this);

		/*****************
		 * NF Eletr�nica *
		 *****************/

		setPainel(pinNFe);
		adicTab("SPED ( NF-e/EFD )", pinNFe);

		JPanelPad pnNFeCod = new JPanelPad();
		pnNFeCod.setBorder(SwingParams.getPanelLabel("Codifica��o Padr�o", Color.BLUE));

		adic(pnNFeCod, 7, 5, 370, 100);

		setPainel(pnNFeCod);

		adicDB(cbUsaIbgeCli, 4, 7, 330, 20, "USAIBGECLI", "", true);
		adicDB(cbUsaIbgeFor, 4, 27, 350, 20, "USAIBGEFOR", "", true);
		adicDB(cbUsaIbgeTransp, 4, 47, 365, 20, "USAIBGETRANSP", "", true);

		JPanelPad pnNFePlugin = new JPanelPad();
		pnNFePlugin.setBorder(SwingParams.getPanelLabel("Configura��o do plugin NF-e", Color.BLUE));
		setPainel(pinNFe);
		adic(pnNFePlugin, 7, 105, 370, 195);

		setPainel(pnNFePlugin);

		adicCampo(txtDescClassNfe, 4, 22, 350, 20, "CLASSNFE", "Classe do plugin de integra��o NFe", ListaCampos.DB_SI, false);
		adicCampo(txtDirNfeWin, 4, 62, 327, 20, "DIRNFE", "Diret�rio padr�o para arquvos NFe (Windows)", ListaCampos.DB_SI, false);
		adicCampo(txtDirNfeLin, 4, 102, 327, 20, "DIRNFELIN", "Diret�rio padr�o para arquvos NFe (Linux)", ListaCampos.DB_SI, false);

		adic(btDirNfeWin, 333, 62, 20, 20);
		adic(btDirNfeLin, 333, 102, 20, 20);

		adicCampo(txtVerProcNfe, 4, 142, 105, 20, "VerProcNfe", "Vers�o/Aplicativo", ListaCampos.DB_SI, false);
		adicDB(cbVersaoNFE, 112, 142, 100, 20, "VersaoNFE", "Vers�o da NFE", false);
		adicDB(cbRegimeNFE, 215, 142, 130, 20, "RegimeTribNFE", "Regime trib.", false);
		
		
		JPanelPad pnNFeParam = new JPanelPad();
		pnNFeParam.setBorder(SwingParams.getPanelLabel("Par�metros", Color.BLUE));
		setPainel(pinNFe);
		adic(pnNFeParam, 380, 5, 395, 295);

		setPainel(pnNFeParam);

		adicDB(rgAmbienteNFE, 7, 20, 370, 30, "AmbienteNFE", "Ambiente", false);
		adicDB(rgFormatoDANFE, 7, 75, 370, 30, "FormatoDanfe", "Formato da DANFE", false);
		adicDB(rgProcEmiNFE, 7, 130, 370, 50, "ProcEmiNfe", "Processo de emiss�o", false);
		
		
		JPanelPad pnLicenciamento = new JPanelPad();
		pnLicenciamento.setBorder(SwingParams.getPanelLabel("Licenciamento", Color.BLUE));
		setPainel(pinNFe);
		adic(pnLicenciamento, 380, 298, 395, 140);

		setPainel(pnLicenciamento);
		
		adicDB(txtDtVenctoNfe, 7, 20, 100, 20, "DtVenctoNfe", "Vencimento NFE", false);
		adicDB(txtKeyLicNfe, 110, 20, 260, 20, "KeyLicNfe", "Chave de licenciamento NFE", false);

		adicDB(txtDtVenctoEfd, 7, 70, 100, 20, "DtVenctoEfd", "Vencimento EFD", false);
		adicDB(txtKeyLicEfd, 110, 70, 260, 20, "KeyLicEfd", "Chave de licenciamento SPED-EFD", false);

		JPanelPad pnNFeOpcoes = new JPanelPad();
		pnNFeOpcoes.setBorder(SwingParams.getPanelLabel("Op��es", Color.BLUE));

		setPainel(pinNFe);
		adic(pnNFeOpcoes, 7, 298, 370, 140);

		setPainel(pnNFeOpcoes);

		adicDB(cbInfAdicProdNFE, 7, 0, 370, 30, "InfAdProdNfe", "", false);
		adicDB(cbExibeParcObsDanfe, 7, 25, 370, 30, "ExibeParcObsDanfe", "", false);

		adicCampo(txtCodEmailNF, 7, 75, 60, 20, "CodEmailNF", "C�d.Email", ListaCampos.DB_FK, txtDescEmailNF, false);
		adicDescFK(txtDescEmailNF, 70, 75, 250, 20, "DescEmail", "Descri��o do email padr�o");
		txtCodEmailNF.setNomeCampo("CodEmail");

		/***************** 
		 * Recursos *
		 *****************/

		lbRecursos.setOpaque(true);
		lbRecursosCont.setBorder(BorderFactory.createEtchedBorder(1));

		setPainel(pinRecursos);
		adicTab("Recursos", pinRecursos);

		adic(lbRecursos, 17, 10, 70, 20);
		adic(lbRecursosCont, 7, 20, 380, 100);
		adicDB(cbBuscaCep, 10, 35, 330, 20, "BUSCACEP", "", true);
		adicCampo(txtUrlWsCep, 13, 85, 300, 20, "URLWSCEP", "URL do Web Service para consulta de CEP.", ListaCampos.DB_SI, false);

		/*************************
		 * Conhecimento de frete *
		 *************************/

		setPainel(pinFrete);
		adicTab("Conhecimento de frete", pinFrete);

		adic(pnFrete, 7, 10, 450, 155);
		pnFrete.setBorder(SwingParams.getPanelLabel("Op��es", Color.BLUE));
		
		setPainel(pnFrete);
		
		adicCampo(txtCodTipoMov9, 7, 20, 90, 20, "CodTipoMov9", "C�d.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, false);
		adicDescFK(txtDescTipoMov9, 100, 20, 330, 20, "DescTipoMov", "Descri��o do tp. mov. para conhecimento de frete");

		adicCampo(txtCodTipoForFT, 7, 60, 90, 20, "CodTipoForFT", "C�d.tp.for.", ListaCampos.DB_FK, txtDescTipoForFT, false);
		adicDescFK(txtDescTipoForFT, 100, 60, 330, 20, "DescTipoFor", "Descri��o do tipo forn. p/transportadoras");
		
		txtCodTipoForFT.setNomeCampo("codtipofor");
		
		setListaCampos(false, "PREFERE1", "SG");

		txtCodHistRec.setNomeCampo("CodHist"); // Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.

		txtCodHistPag.setNomeCampo("CodHist"); // Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.

		txtCodTipoMov2.setNomeCampo("CodTipoMov"); // Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.
		txtCodTipoMov3.setNomeCampo("CodTipoMov"); // Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.
		txtCodTipoMov4.setNomeCampo("CodTipoMov"); // Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.
		txtCodTipoMov5.setNomeCampo("CodTipoMov"); // Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.
		txtCodTipoMov6.setNomeCampo("CodTipoMov"); // Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.
		txtCodTipoMov8.setNomeCampo("CodTipoMov"); // Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.
		txtCodTipoMov9.setNomeCampo("CodTipoMov"); // Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.

		txtCodPlanJR.setNomeCampo("CodPlan");
		txtCodPlanJP.setNomeCampo("CodPlan");
		txtCodPlanDC.setNomeCampo("CodPlan");
		txtCodPlanDR.setNomeCampo("CodPlan");

		// lcSeq.adicDetalhe(lcPDV);
		// lcPDV.setMaster(lcSeq);

		setListaCampos(lcPDV);
		setNavegador(new Navegador(false));

		// Or�amento e PDV (SGPREFERE4)

		setPainel(pinOrcamento);

		pnOpcoesOrc.setBorder(SwingParams.getPanelLabel("Op��es", Color.BLACK));

		adic(pnOpcoesOrc, 7, 265, 720, 210);

		adicCampo(txtCodTipoMov7, 7, 65, 90, 20, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov7, false);
		adicDescFK(txtDescTipoMov7, 100, 65, 300, 20, "DescTipoMov", "Descri��o do tipo de movimento para PDV");
		adicCampo(txtCodPlanoPag2, 7, 105, 90, 20, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_FK, txtDescPlanoPag2, false);
		adicDescFK(txtDescPlanoPag2, 100, 105, 300, 20, "DescPlanoPag", "Descri��o do plano de pagamento");
		adicCampo(txtCodCli, 7, 145, 90, 20, "CodCli", "C�d.cli.", ListaCampos.DB_FK, txtDescCli, false);
		adicDescFK(txtDescCli, 100, 145, 300, 20, "NomeCli", "Nome do cliente");

		adicCampo(txtPrazo, 403, 145, 161, 20, "Prazo", "Prazo de entrega", ListaCampos.DB_SI, false);
		adicCampo(txtDiasVencOrc, 567, 145, 158, 20, "DiasVencOrc", "Dias vencto. or�amento", ListaCampos.DB_SI, false);

		setPainel(pnOpcoesOrc);

		adicDB(cbUsaBuscGenProdORC, 10, 140, 350, 20, "USABUSCAGENPROD", "", false);

		adicDB(cbAprovOrc, 400, 0, 350, 20, "AprovOrc", "", true);
		adicDB(cbUsaLoteOrc, 400, 20, 300, 20, "USALOTEORC", "", false);

		setListaCampos(false, "PREFERE4", "SG");

		// Email

		setListaCampos(lcPrefere3);
		setPainel(pinEmail);
		adicTab("Mail", pinEmail);

		JLabel email = new JLabel("Configura��o de e-mail", SwingConstants.CENTER);
		email.setOpaque(true);
		JLabel linha2 = new JLabel();
		linha2.setBorder(BorderFactory.createEtchedBorder());

		adic(email, 27, 10, 180, 20);
		adic(linha2, 7, 20, 403, 190);

		adicCampo(txtServidorSMTP, 17, 60, 230, 20, "SMTPMAIL", "Servidor de SMTP", ListaCampos.DB_SI, false);
		adicCampo(txtPortaSMTP, 250, 60, 50, 20, "PORTAMAIL", "Porta", ListaCampos.DB_SI, false);
		adicDB(cbAutenticaSMTP, 307, 60, 100, 20, "SMTPAUTMAIL", "", false);
		adicCampo(txtEndEmail, 17, 100, 283, 20, "ENDMAIL", "E-mail do usuario", ListaCampos.DB_SI, false);
		adicCampo(txtUsuarioSMTP, 17, 140, 283, 20, "USERMAIL", "Id do usuario", ListaCampos.DB_SI, false);
		adicDB(cbSSLSMTP, 307, 100, 100, 20, "SMTPSSLMAIL", "", false);
		adicCampo(txtSenhaSMTP, 17, 180, 283, 20, "PASSMAIL", "Senha do usuario", ListaCampos.DB_SI, false);
		setListaCampos(false, "PREFERE3", "SG");

		// fim da adic�o de abas

		lcCampos.addCarregaListener(this);
		lcPDV.addInsertListener(this);
		lcPDV.addEditListener(this);
		lcPrefere3.addInsertListener(this);
		lcPrefere3.addEditListener(this);

		btDirNfeWin.setToolTipText("Localizar diret�rio");
		btDirNfeLin.setToolTipText("Localizar diret�rio");

		btDirNfeWin.addActionListener(this);
		btDirNfeLin.addActionListener(this);

		cbEstNegGrupo.addCheckBoxListener(this);
		cbJurosPosCalc.addCheckBoxListener(this);
		cbUsaBuscGenProd.addCheckBoxListener(this);

		cbFilBuscGenProd1.setEnabled(false);
		cbFilBuscGenProd2.setEnabled(false);
		cbFilBuscGenProd3.setEnabled(false);
		cbFilBuscGenProd4.setEnabled(false);

	}
	
	public void beforePost(PostEvent pevt) {

		if (txtCasasDec.getVlrInteger().intValue() > 5) {
			Funcoes.mensagemErro(this, "N�mero de casas decimais acima do permitido!");
			txtCasasDec.requestFocus();
			pevt.cancela();
		}
		if (txtCasasDecFin.getVlrInteger().intValue() > 5) {
			Funcoes.mensagemErro(this, "N�mero de casas decimais acima do permitido!");
			txtCasasDecFin.requestFocus();
			pevt.cancela();
		}
	}

	public void afterPost(PostEvent pevt) {

		if (pevt.getListaCampos() == lcCampos) {
			if (lcPDV.getStatus() == ListaCampos.LCS_INSERT || lcPDV.getStatus() == ListaCampos.LCS_EDIT) {
				lcPDV.post();
			}
			if (lcPrefere3.getStatus() == ListaCampos.LCS_INSERT || lcPrefere3.getStatus() == ListaCampos.LCS_EDIT) {
				lcPrefere3.post();
			}
		}
	}

	public void afterEdit(EditEvent eevt) {

		if (eevt.getListaCampos() == lcPDV) {
			if (eevt.getListaCampos().getStatus() == ListaCampos.LCS_EDIT) {
				lcCampos.edit();
			}
		}
	}

	public void beforeEdit(EditEvent eevt) { }

	public void edit(EditEvent eevt) { }

	public void afterInsert(InsertEvent ievt) {

		if (ievt.getListaCampos() == lcPDV) {
			if (ievt.getListaCampos().getStatus() == ListaCampos.LCS_INSERT) {
				lcCampos.edit();
			}
		}
		if (ievt.getListaCampos() == lcPrefere3) {
			if (ievt.getListaCampos().getStatus() == ListaCampos.LCS_INSERT) {
				lcCampos.edit();
			}
		}
	}

	public void beforeInsert(InsertEvent ievt) { }

	public void valorAlterado(CheckBoxEvent cevt) {

		if (cevt.getCheckBox() == cbJurosPosCalc && cbJurosPosCalc.getVlrString().equals("S"))
			txtCodTabJuros.setAtivo(false);
		else
			txtCodTabJuros.setAtivo(true);
		if (cevt.getCheckBox() == cbEstNegGrupo) {
			if (cbEstNegGrupo.getVlrString().equals("S")) {
				cbEstNeg.setVlrString("N");
				cbEstNeg.setEnabled(false);
				cbEstLotNeg.setVlrString("N");
				cbEstLotNeg.setEnabled(false);
			}
			else {
				cbEstNeg.setEnabled(true);
				cbEstLotNeg.setEnabled(true);
			}
		}
		if (cevt.getCheckBox() == cbUsaBuscGenProd && cbUsaBuscGenProd.getVlrString().equals("S")) {
			cbFilBuscGenProd1.setEnabled(true);
			cbFilBuscGenProd2.setEnabled(true);
			cbFilBuscGenProd3.setEnabled(true);
			cbFilBuscGenProd4.setEnabled(true);
			cbFilBuscGenProd5.setEnabled(true);
		}
		else {
			cbFilBuscGenProd1.setEnabled(false);
			cbFilBuscGenProd2.setEnabled(false);
			cbFilBuscGenProd3.setEnabled(false);
			cbFilBuscGenProd4.setEnabled(false);
			cbFilBuscGenProd5.setEnabled(false);
		}
	}

	public void setConexao(DbConnection cn) {

		super.setConexao(cn);
		lcMoeda.setConexao(cn);
		lcTabJuros.setConexao(cn);
		lcMarca.setConexao(cn);
		lcGrupo.setConexao(cn);
		lcTipoFor.setConexao(cn);
		lcFor.setConexao(cn);
		lcTipoMov.setConexao(cn);
		lcTipoMovImp.setConexao(cn);
		lcTipoMov2.setConexao(cn);
		lcTipoMovS.setConexao(cn);
		lcTipoMov3.setConexao(cn);
		lcTipoMov4.setConexao(cn);
		lcTipoMov5.setConexao(cn);
		lcTipoMov6.setConexao(cn);
		lcTipoMov7.setConexao(cn);
		lcTipoMov8.setConexao(cn);
		lcTipoMov9.setConexao(cn);

		lcTransp.setConexao(cn);
		lcTipoForFT.setConexao(cn);
		lcPlanoPag.setConexao(cn);
		lcPlanoPag2.setConexao(cn);
		lcPlanoPagSV.setConexao(cn);
		lcClasCli.setConexao(cn);
		lcTabPreco.setConexao(cn);
		lcCli.setConexao(cn);
		lcPDV.setConexao(cn);
		lcPrefere3.setConexao(cn);
		lcMens.setConexao(cn);
		lcMensGeral.setConexao(cn);
		lcHistPag.setConexao(cn);
		lcHistRec.setConexao(cn);
		lcTipoCli.setConexao(cn);
		lcPlanJR.setConexao(cn);
		lcPlanJP.setConexao(cn);
		lcPlanDC.setConexao(cn);
		lcPlanDR.setConexao(cn);
		lcPlanPC.setConexao(cn);
		lcEmailNF.setConexao(cn);

		lcCampos.carregaDados();

	}

	public void afterCarrega(CarregaEvent cevt) {

		if (cevt.getListaCampos() == lcCampos) {
			if (!( lcPDV.getStatus() == ListaCampos.LCS_EDIT || lcPDV.getStatus() == ListaCampos.LCS_INSERT ))
				lcPDV.carregaDados();

			if (!( lcPrefere3.getStatus() == ListaCampos.LCS_EDIT || lcPrefere3.getStatus() == ListaCampos.LCS_INSERT ))
				lcPrefere3.carregaDados();
		}

	}

	public void beforeCarrega(CarregaEvent cevt) { }

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btDirNfeWin) {
			Thread th = new Thread(new Runnable() {
				public void run() {
					getDiretorio(SystemFunctions.OS_WINDOWS);
				}
			});
			th.start();
		}

		if (e.getSource() == btDirNfeLin) {
			Thread th = new Thread(new Runnable() {
				public void run() {
					getDiretorio(SystemFunctions.OS_LINUX);
				}
			});
			th.start();
		}

		super.actionPerformed(e);

	}

	private void getDiretorio(int SO) {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			if (SO == SystemFunctions.OS_WINDOWS) {
				txtDirNfeWin.setVlrString(fileChooser.getSelectedFile().getPath());
			}
			else if (SO == SystemFunctions.OS_LINUX) {
				txtDirNfeLin.setVlrString(fileChooser.getSelectedFile().getPath());
			}
		}
	}

}
