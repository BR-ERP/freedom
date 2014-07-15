/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FOrcamento.java <BR>
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
 *                     Tela de or�amento, tela respons�vel pela inser��o e edi��o de or�amentos por cliente <BR>
 *                     diferente da tela de or�amento do atendimento que � por conveniado. <BR>
 * 
 */

package org.freedom.modulos.std.view.frame.crud.detail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.infra.util.db.DBEngine;
import org.freedom.infra.util.db.DBEngine.Variable;
import org.freedom.library.business.component.Lucratividade;
import org.freedom.library.business.object.EmailBean;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.component.LeiauteGR;
import org.freedom.library.dao.DAOEmail;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.Navegador;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.library.type.TYPE_PRINT;
import org.freedom.modulos.atd.view.frame.crud.plain.FEncaminhador;
import org.freedom.modulos.atd.view.frame.crud.plain.FTipoConv;
import org.freedom.modulos.atd.view.frame.crud.tabbed.FAtendente;
import org.freedom.modulos.atd.view.frame.crud.tabbed.FConveniado;
import org.freedom.modulos.crm.business.object.Atendimento;
import org.freedom.modulos.crm.dao.DAOAtendimento;
import org.freedom.modulos.crm.view.frame.crud.plain.FNovoAtend;
import org.freedom.modulos.crm.view.frame.utility.FCRM.COL_ATENDIMENTO;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FTipoMov;
import org.freedom.modulos.lvf.business.component.CalcImpostos;
import org.freedom.modulos.std.DLBuscaEstoq;
import org.freedom.modulos.std.DLCodProd;
import org.freedom.modulos.std.business.component.Orcamento;
import org.freedom.modulos.std.business.component.Orcamento.OrcVenda;
import org.freedom.modulos.std.business.component.Orcamento.PrefOrc;
import org.freedom.modulos.std.business.component.Orcamento.ResultBuscaClassOrc;
import org.freedom.modulos.std.business.component.Orcamento.ResultClassOrc;
import org.freedom.modulos.std.dao.DAOOrcamento;
import org.freedom.modulos.std.view.dialog.report.DLROrcamento;
import org.freedom.modulos.std.view.dialog.utility.DLAltFatLucro;
import org.freedom.modulos.std.view.dialog.utility.DLBuscaProd;
import org.freedom.modulos.std.view.dialog.utility.DLCompOrc;
import org.freedom.modulos.std.view.dialog.utility.DLCopiaOrc;
import org.freedom.modulos.std.view.dialog.utility.DLReplicaOrc;
import org.freedom.modulos.std.view.frame.crud.plain.FAlmox;
import org.freedom.modulos.std.view.frame.crud.plain.FCLComis;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;
import org.freedom.modulos.std.view.frame.crud.tabbed.FTransp;
import org.freedom.modulos.std.view.frame.crud.tabbed.FVendedor;
import org.freedom.modulos.std.view.frame.utility.FSelOrc;

public class FOrcamento extends FVD implements PostListener, CarregaListener, FocusListener, ActionListener, InsertListener, DeleteListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private JTabbedPanePad tpnCab = new JTabbedPanePad();

	private JTabbedPanePad tpnDet = new JTabbedPanePad();

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JPanelPad pinTot = new JPanelPad( 200, 200 );

	private JPanelPad pnTot = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pnCenter = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinCabOrc = new JPanelPad();

	private JPanelPad pinCabTransp = new JPanelPad();

	private JPanelPad pinCabPedidos = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinCabAtendimentos = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinPrevisao =  new JPanelPad();

	private JTextFieldPad txtCodOrc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDtOrc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtVencOrc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtPreventOrc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtHPreventOrc = new JTextFieldPad( JTextFieldPad.TP_TIME, 8, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTpConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoConv = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescConv = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodEnc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtEstCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtDescIpi = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodItOrc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtQtdItOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodBarras = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCLoteProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtPrecoItOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecPre );

	private JTextFieldPad txtPercDescItOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 5 );

	private JTextFieldPad txtVlrDescItOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrFreteOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrLiqItOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrEdDescOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrEdAdicOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtPercDescOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );

	private JTextFieldPad txtVlrDescOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrIPIItOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtPercAdicOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );

	private JTextFieldPad txtVlrAdicOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrLiqOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrProdItOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtPercComisItOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );

	private JTextFieldPad txtPercComVend = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );

	private JTextFieldPad txtPercComProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 6, 2 );

	private JTextFieldPad txtVlrComisItOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrComisOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtStrDescItOrc = new JTextFieldPad( JTextFieldPad.TP_STRING, 500, 0 );

	private JTextFieldPad txtVlrProdOrc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtStatusOrc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JCheckBoxPad cbEmiteItOrc = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbAceiteItOrc = new JCheckBoxPad( "", "S", "N" );

	private JCheckBoxPad cbAprovItOrc = new JCheckBoxPad( "", "S", "N" );

	private JComboBoxPad cbFatItOrc = null;

	private JTextFieldPad txtFatItOrc = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtSitProdItOrc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtStatusItOrc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JComboBoxPad cbSitProdItOrc = null;

	private JComboBoxPad cbStatusItOrc = null;

	private JCheckBoxPad cbSitEntItOrc = new JCheckBoxPad( "", "E", "N" );

	private JCheckBoxPad cbCancItOrc = new JCheckBoxPad( "", "S", "N" );

	private JTextFieldPad txtCodTpCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodClComiss = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtPrazoEntOrc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodAlmoxItOrc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodEmpLG = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodFilialLG = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodLog = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtTxt01 = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtACOrc = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldFK txtNomeEnc = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtContCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescAlmoxItOrc = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtCodUnid = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldFK txtDescClComiss = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtSldLiqProd = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, casasDec );

	private JTextFieldFK txtDescLote = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodTranCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtRazTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JRadioGroup<?, ?> rgTipoFrete = null;

	private JTextAreaPad txaObsItOrc = new JTextAreaPad( 500 );

	private JButtonPad btObs = new JButtonPad( Icone.novo( "btObs1.png" ) );

	private JButtonPad btOrc = new JButtonPad( Icone.novo( "btImprimeOrc.png" ) );

	private JButtonPad btOrcTst = new JButtonPad( Icone.novo( "btFisio.png" ) );

	private JButtonPad btOrcTst2 = new JButtonPad( Icone.novo( "btEmprestimo.png" ) );

	private JButtonPad btFechaOrc = new JButtonPad( Icone.novo( "btOk.png" ) );

	private JButtonPad btCopiaOrcamento = new JButtonPad( Icone.novo( "btCopiar.png" ) );

	private JButtonPad btReplicaOrcamento = new JButtonPad( Icone.novo( "btReplicaOrc.png" ) );

	private JButtonPad btCriaLancamento = new JButtonPad("Contato", Icone.novo( "btContatoOrc.png" ) );

	private JLabelPad lbStatus = new JLabelPad();

	private JTablePad tabPedidos = new JTablePad();

	private JScrollPane spPedidos = new JScrollPane( tabPedidos );
	
	private JTablePad tabAtendimentos = new JTablePad();
	
	private JScrollPane spAtendimentos = new JScrollPane( tabAtendimentos );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private ListaCampos lcEnc = new ListaCampos( this, "EC" );

	private ListaCampos lcProd = new ListaCampos( this, "PD" );

	private ListaCampos lcProd2 = new ListaCampos( this, "PD" );

	private ListaCampos lcOrc2 = new ListaCampos( this );

	private ListaCampos lcPrevTrib = new ListaCampos( this );

	private ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private ListaCampos lcVend = new ListaCampos( this, "VD" );

	private ListaCampos lcConv = new ListaCampos( this, "CV" );

	private ListaCampos lcTipoConv = new ListaCampos( this, "TC" );

	private ListaCampos lcTipoCli = new ListaCampos( this, "TC" );

	private ListaCampos lcAlmox = new ListaCampos( this, "AX" );

	private ListaCampos lcClComiss = new ListaCampos( this, "CM" );

	private ListaCampos lcLote = new ListaCampos( this, "LE" );

	private ListaCampos lcAtend = new ListaCampos( this, "AE" );

	private final ListaCampos lcTran = new ListaCampos( this, "TN" );

	private Vector<Object> vParamOrc = new Vector<Object>();

	private String sModoNota = "";

	private String oldStatusOrc = null;

	private BigDecimal bdVlrDescItAnt;

	private FPrinterJob dl = null;

	private Object[] oPrefs = null;

	private boolean bCtrl = false;

	private int iCodCliAnt = 0;

	private final JCheckBoxPad cbAdicFrete = new JCheckBoxPad( "adiciona valor do frete no or�amento?", "S", "N" );

	private JPanelPad pinCabLucratividade = new JPanelPad();

	private JPanelPad pinDetStatus = new JPanelPad();

	private JPanelPad pinCabDetCustos = new JPanelPad();

	private JPanelPad pnLucrGeral = new JPanelPad();

	private JPanelPad pnLucrItem = new JPanelPad();

	private JPanelPad pnDetCustosGeral = new JPanelPad();

	private JPanelPad pnDetCustosItem = new JPanelPad();

	private JPanelPad pnAdicionalCab = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JTextFieldFK txtTotFat = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotCusto = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotCustoUC = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotComiss = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotPIS = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotCofins = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotICMS = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotCSOCIAL = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotIR = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotIPI = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotCustoUCIt = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotComissIt = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotPISIt = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotCofinsIt = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotICMSIt = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotCSOCIALIt = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotIRIt = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotIPIIt = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtTotLucro = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JProgressBar pbLucrTotal = new JProgressBar();

	private JProgressBar pbLucrItem = new JProgressBar();

	private JTextFieldFK txtItemFat = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtItemCusto = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldFK txtItemLucro = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );

	private HashMap<String, Object> permusu = null;

	private BigDecimal fatluc = new BigDecimal( 1 );

	private BigDecimal cem = new BigDecimal( 100 );

	private DAOEmail daoemail = null;

	//private DAOOrcamento daoorcamento = null;
	
	private boolean bImprimir = true;

	/*private String classorcpd = null;
	
	private String classorclaudosus = null;
	
	private String classorcctaluguel = null;
	*/
	private String classorc[] = null;
	
	//CLASSORCPD, CLASSORCLAUDOSUS, CLASSORCCTALUGUEL
	
	//private DAOAtendimento daoatendo = null;
	
	//private Map<String, Object> infAtendente = null;
	
	//private boolean acesatdolerout = false;
	
	//private boolean acesatdoaltout = false;
	
	//private Integer codatend_atual = null;
	
	DAOAtendimento daoatend = null;

	public FOrcamento() {

		super();

		nav.setNavigation( true );

		setTitulo( "Or�amento" );
		setAtribos( 50, 50, 790, 490 );

		txtDescProd.setToolTipText( "Clique aqui duas vezes para alterar a descri��o." );
		txtDescProd.addMouseListener( this );

		// Coloca os coment�rio nos bot�es
		btFechaOrc.setToolTipText( "Completar o Or�amento (F4)" ); 
		btObs.setToolTipText( "Observa��es (Ctrl + O)" );
		btOrc.setToolTipText( "Imprime or�amento padr�o" );
		btOrcTst.setToolTipText( "Imprime or�amento assinado" );
		btOrcTst2.setToolTipText( "Imprime contrato de loca��o" );
		btCopiaOrcamento.setToolTipText( "Copia or�amento." );
		btReplicaOrcamento.setToolTipText( "Replica or�amento entre empresas." );
		btCriaLancamento.setToolTipText( "Gera atendimento." );

		// Desativa as os TextFields para que os usu�rios n�o fussem
		txtVlrDescOrc.setAtivo( false );
		txtVlrAdicOrc.setAtivo( false );
		txtVlrLiqOrc.setAtivo( false );

		Vector<String> vValsTF = new Vector<String>();
		Vector<String> vLabsTF = new Vector<String>();
		vLabsTF.addElement( "CIF" );
		vLabsTF.addElement( "FOB" );
		vValsTF.addElement( "C" );
		vValsTF.addElement( "F" );
		rgTipoFrete = new JRadioGroup<String, String>( 1, 21, vLabsTF, vValsTF );

		lbStatus.setForeground( Color.WHITE );
		lbStatus.setFont( SwingParams.getFontboldmed() );
		lbStatus.setOpaque( true );
		lbStatus.setHorizontalAlignment( SwingConstants.CENTER );
		lbStatus.setVisible( false );

		// txtPercComisItOrc.setEditable( false );
		txtVlrComisItOrc.setEditable( false );
		txtVlrIPIItOrc.setEditable( false );

		// Adiciona os Listeners
		montaListeners();

		setImprimir( true );
	}

	private void montaListeners() {
		
		btFechaOrc.addActionListener( this );
		btObs.addActionListener( this );
		btOrc.addActionListener( this );
		btOrcTst.addActionListener( this );
		btOrcTst2.addActionListener( this );

		btCopiaOrcamento.addActionListener( this );
		btReplicaOrcamento.addActionListener( this );
		btCriaLancamento.addActionListener( this );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

		txtRefProd.addKeyListener( this );
		txtCodPlanoPag.addKeyListener( this );
		txtCodTran.addKeyListener( this );

		txtPercDescItOrc.addFocusListener( this );
		txtVlrDescItOrc.addFocusListener( this );
		txtVlrLiqItOrc.addFocusListener( this );
		txtQtdItOrc.addFocusListener( this );
		txtPrecoItOrc.addFocusListener( this );
		txtPercComisItOrc.addFocusListener( this );

		lcCampos.addCarregaListener( this );
		lcProd2.addCarregaListener( this );
		lcCli.addCarregaListener( this );
		lcProd.addCarregaListener( this );
		lcProd2.addCarregaListener( this );
		lcDet.addCarregaListener( this );
		lcPlanoPag.addCarregaListener( this );
		lcCli.addCarregaListener( this );

		lcCampos.addInsertListener( this );
		lcDet.addInsertListener( this );
		lcDet.addCarregaListener( this );
		lcDet.addPostListener( this );
		lcCampos.addPostListener( this );
		lcCampos.addDeleteListener( this );
		lcDet.addDeleteListener( this );
		
	}

	private void montaListaCampos() {

		// FK Tipo de cliente
		lcTipoCli.add( new GuardaCampo( txtCodTpCli, "CodTipoCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcTipoCli.add( new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		txtCodTpCli.setTabelaExterna( lcTipoCli, FTipoMov.class.getCanonicalName() );
		txtDescTipoCli.setListaCampos( lcTipoCli );
		lcTipoCli.montaSql( false, "TIPOCli", "VD" );
		lcTipoCli.setQueryCommit( false );
		lcTipoCli.setReadOnly( true );

		// FK Plano de pagamento
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.setWhereAdic( "ATIVOPLANOPAG='S' AND CVPLANOPAG IN ('V','A')" );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag, FPlanoPag.class.getCanonicalName() );
		txtDescPlanoPag.setListaCampos( lcPlanoPag );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );

		// FK Vendedor
		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.comiss.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVend.add( new GuardaCampo( txtPercComVend, "PercComVend", "Perc. Comis.", ListaCampos.DB_SI, false ) );

		txtCodVend.setTabelaExterna( lcVend, FVendedor.class.getCanonicalName() );
		txtNomeVend.setListaCampos( lcVend );
		lcVend.montaSql( false, "VENDEDOR", "VD" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );

		// FK Classifica��o de comiss�o
		lcClComiss.add( new GuardaCampo( txtCodClComiss, "CodClComis", "C�d.cl.comiss.", ListaCampos.DB_PK, false ) );
		lcClComiss.add( new GuardaCampo( txtDescClComiss, "DescClComis", "Descri��o da class. da comiss�o", ListaCampos.DB_SI, false ) );
		lcClComiss.montaSql( false, "CLCOMIS", "VD" );
		lcClComiss.setQueryCommit( false );
		lcClComiss.setReadOnly( true );
		txtCodClComiss.setTabelaExterna( lcClComiss, FCLComis.class.getCanonicalName() );

		// FK Encaminhador
		lcEnc.add( new GuardaCampo( txtCodEnc, "CodEnc", "C�d.enc.", ListaCampos.DB_PK, false ) );
		lcEnc.add( new GuardaCampo( txtNomeEnc, "NomeEnc", "Descri��o do encaminhador", ListaCampos.DB_SI, false ) );
		txtCodEnc.setTabelaExterna( lcEnc, FEncaminhador.class.getCanonicalName() );
		txtNomeEnc.setListaCampos( lcEnc );
		txtCodEnc.setNomeCampo( "CodEnc" );
		lcEnc.montaSql( false, "ENCAMINHADOR", "AT" );
		lcEnc.setQueryCommit( false );
		lcEnc.setReadOnly( true );

		// FK Conveniado
		lcConv.add( new GuardaCampo( txtCodConv, "CodConv", "C�d.conv.", ListaCampos.DB_PK, false ) );
		lcConv.add( new GuardaCampo( txtDescConv, "NomeConv", "Nome do coveniado", ListaCampos.DB_SI, false ) );
		lcConv.add( new GuardaCampo( txtCodTpConv, "CodTpConv", "Tipo de Conveniado", ListaCampos.DB_SI, false ) );
		lcConv.add( new GuardaCampo( txtCodEnc, "CodEnc", "Encaminhador", ListaCampos.DB_FK, false ) );
		lcConv.montaSql( false, "CONVENIADO", "AT" );
		lcConv.setQueryCommit( false );
		lcConv.setReadOnly( true );
		txtCodConv.setTabelaExterna( lcConv, FConveniado.class.getCanonicalName() );

		// FK Tipo de conveniado
		lcTipoConv.add( new GuardaCampo( txtCodTpConv, "CodTpConv", "C�d.tp.conv.", ListaCampos.DB_PK, false ) );
		lcTipoConv.add( new GuardaCampo( txtDescTipoConv, "DescTpConv", "Descri��o do tipo de conveniado", ListaCampos.DB_SI, false ) );
		txtCodTpConv.setTabelaExterna( lcTipoConv, FTipoConv.class.getCanonicalName() );
		txtDescTipoConv.setListaCampos( lcTipoConv );
		lcTipoConv.montaSql( false, "TIPOCONV", "AT" );
		lcTipoConv.setQueryCommit( false );
		lcTipoConv.setReadOnly( true );

		// FK Lote
		lcLote.add( new GuardaCampo( txtCodLote, "CodLote", "Lote", ListaCampos.DB_PK, txtDescLote, false ) );
		lcLote.add( new GuardaCampo( txtDescLote, "VenctoLote", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcLote.add( new GuardaCampo( txtSldLiqProd, "SldLiqLote", "Saldo", ListaCampos.DB_SI, false ) );
		lcLote.setDinWhereAdic( "CODPROD=#N ", txtCodProd );
		lcLote.montaSql( false, "LOTE", "EQ" );
		lcLote.setQueryCommit( false );
		lcLote.setReadOnly( true );
		txtCodLote.setTabelaExterna( lcLote, null );
		txtDescLote.setListaCampos( lcLote );
		txtDescLote.setNomeCampo( "VenctoLote" );
		txtDescLote.setLabel( "Vencimento" );

		// FK Cliente
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "NomeCli", "Nome do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.comiss.", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtEstCli, "UfCli", "UF", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtDescIpi, "DescIpi", "Desconto de IPI", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCodTranCli, "CodTran", "C�d.Transp.", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtContCli, "ContCli", "Contato", ListaCampos.DB_SI, false ) );

		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli, FCliente.class.getCanonicalName() );
		txtNomeCli.setSize( 250, 20 );

		// FK de atendente
		lcAtend.add( new GuardaCampo( txtCodAtend, "CodAtend", "C�d.atend.", ListaCampos.DB_PK, false ) );
		lcAtend.add( new GuardaCampo( txtDescAtend, "NomeAtend", "Nome do atendente", ListaCampos.DB_SI, false ) );
		txtCodAtend.setTabelaExterna( lcAtend, FAtendente.class.getCanonicalName() );
		txtDescAtend.setListaCampos( lcAtend );
		lcAtend.montaSql( false, "ATENDENTE", "AT" );
		lcAtend.setQueryCommit( false );
		lcAtend.setReadOnly( true );

		// FK de Almoxarifado
		lcAlmox.add( new GuardaCampo( txtCodAlmoxItOrc, "codalmox", "Cod.Almox.", ListaCampos.DB_PK, false ) );
		lcAlmox.add( new GuardaCampo( txtDescAlmoxItOrc, "DescAlmox", "Descri��o do almoxarifado", ListaCampos.DB_SI, false ) );
		lcAlmox.montaSql( false, "ALMOX", "EQ" );
		lcAlmox.setQueryCommit( false );
		lcAlmox.setReadOnly( true );
		txtCodAlmoxItOrc.setTabelaExterna( lcAlmox, FAlmox.class.getCanonicalName() );

		// FK Produto
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, txtDescProd, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Refer�ncia do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodBarras, "CodBarProd", "C�digo de barras", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtSldLiqProd, "SldLiqProd", "Saldo", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodAlmoxItOrc, "CodAlmox", "Cd.almox.", ListaCampos.DB_SI, txtDescAlmoxItOrc, false ) );
		lcProd.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "C/Lote", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtPercComProd, "ComisProd", "% Comiss�o", ListaCampos.DB_SI, true ) );
		lcProd.add( new GuardaCampo( txtCodUnid, "CodUnid", "Cod.Unidade", ListaCampos.DB_SI, false ) );


		String sWhereAdicProd = "ATIVOPROD='S' ";
		if ( ! (Boolean) oPrefs[Orcamento.PrefOrc.VDPRODQQCLAS.ordinal()] ) {
			sWhereAdicProd += "AND TIPOPROD IN ('P','S','F'" + ( (Boolean) oPrefs[ Orcamento.PrefOrc.VENDAMATPRIM.ordinal() ] ? ",'M'" : "" ) + ")";
		}
		lcProd.setWhereAdic( sWhereAdicProd );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );
		txtCodProd.setTabelaExterna( lcProd, FProduto.class.getCanonicalName() );

		// FK do produto (*Somente em caso de refer�ncias este listaCampos
		// Trabalha como gatilho para o listaCampos de produtos, assim
		// carregando o c�digo do produto que ser� armazenado no Banco)
		lcProd2.add( new GuardaCampo( txtRefProd, "RefProd", "Ref.prod.", ListaCampos.DB_PK, txtDescProd, false ) );
		lcProd2.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtSldLiqProd, "SldLiqProd", "Saldo", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodAlmoxItOrc, "CodAlmox", "Cd.almox.", ListaCampos.DB_SI, txtDescAlmoxItOrc, false ) );
		lcProd2.add( new GuardaCampo( txtCLoteProd, "CLoteProd", "C/Lote", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtPercComProd, "ComisProd", "% Comiss�o", ListaCampos.DB_SI, true ) );

		txtRefProd.setNomeCampo( "RefProd" );
		txtRefProd.setListaCampos( lcDet );
		lcProd2.setWhereAdic( sWhereAdicProd );
		lcProd2.montaSql( false, "PRODUTO", "EQ" );
		lcProd2.setQueryCommit( false );
		lcProd2.setReadOnly( true );
		txtRefProd.setTabelaExterna( lcProd2, FProduto.class.getCanonicalName() );

		// ListaCampos de Totais (� acionada pelo listaCampos de Orcamento)
		lcOrc2.add( new GuardaCampo( txtCodOrc, "CodOrc", "C�d.Or�.", ListaCampos.DB_PK, false ) );
		lcOrc2.add( new GuardaCampo( txtPercDescOrc, "PercDescOrc", "% desc.", ListaCampos.DB_SI, false ) );
		lcOrc2.add( new GuardaCampo( txtVlrDescOrc, "VlrDescOrc", "Vlr.desc.", ListaCampos.DB_SI, false ) );
		lcOrc2.add( new GuardaCampo( txtPercAdicOrc, "PercAdicOrc", "% adic.", ListaCampos.DB_SI, false ) );
		lcOrc2.add( new GuardaCampo( txtVlrAdicOrc, "VlrAdicOrc", "Vlr.adic.", ListaCampos.DB_SI, false ) );
		lcOrc2.add( new GuardaCampo( txtVlrLiqOrc, "VlrLiqOrc", "Vlr.total", ListaCampos.DB_SI, false ) );
		lcOrc2.add( new GuardaCampo( txtVlrProdOrc, "VlrProdOrc", "Vlr.parcial", ListaCampos.DB_SI, false ) );
		lcOrc2.montaSql( false, "ORCAMENTO", "VD" );
		lcOrc2.setQueryCommit( false );
		lcOrc2.setReadOnly( true );

		lcTran.add( new GuardaCampo( txtCodTran, "CodTran", "C�d.tran.", ListaCampos.DB_PK, false ) );
		lcTran.add( new GuardaCampo( txtRazTran, "RazTran", "Raz�o social da transportadora", ListaCampos.DB_SI, false ) );
		lcTran.montaSql( false, "TRANSP", "VD" );
		lcTran.setQueryCommit( false );
		lcTran.setReadOnly( true );
		txtCodTran.setTabelaExterna( lcTran, FTransp.class.getCanonicalName() );

		// ListaCampos de Previsionamento de tributos
		lcPrevTrib.add( new GuardaCampo( txtCodOrc, "CodOrc", "C�d.Or�.", ListaCampos.DB_PK, false ) );
		lcPrevTrib.add( new GuardaCampo( txtCodItOrc, "CodItOrc", "C�d.It.Or�.", ListaCampos.DB_PK, false ) );
		lcPrevTrib.add( new GuardaCampo( txtVlrIPIItOrc, "VlrIpiItOrc", "Vlr.IPI", ListaCampos.DB_SI, false ) );
		lcPrevTrib.montaSql( false, "PREVTRIBITORC", "VD" );
		lcPrevTrib.setQueryCommit( false );
		lcPrevTrib.setReadOnly( true );

		// FK Tipo de movimentos
		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov, FTipoMov.class.getCanonicalName() );

	}

	
	private void montaOrcamento() {

		Vector<String> lbFatItOrc = new Vector<String>();

		lbFatItOrc.addElement( "Sim" );
		lbFatItOrc.addElement( "N�o" );
		lbFatItOrc.addElement( "Parcial" );

		Vector<String> vlFatItOrc = new Vector<String>();

		vlFatItOrc.addElement( "S" );
		vlFatItOrc.addElement( "N" );
		vlFatItOrc.addElement( "P" );

		cbFatItOrc = new JComboBoxPad( lbFatItOrc, vlFatItOrc, JComboBoxPad.TP_STRING, 1, 0 );

		Vector<String> lbSitProdItOrc = new Vector<String>();

		lbSitProdItOrc.addElement( "Pendente" );
		lbSitProdItOrc.addElement( "Em produ��o" );
		lbSitProdItOrc.addElement( "N�o produzir" );
		lbSitProdItOrc.addElement( "Produzido" );

		Vector<String> vlSitProdItOrc = new Vector<String>();

		vlSitProdItOrc.addElement( "PE" );
		vlSitProdItOrc.addElement( "EP" );
		vlSitProdItOrc.addElement( "NP" );
		vlSitProdItOrc.addElement( "PD" );

		cbSitProdItOrc = new JComboBoxPad( lbSitProdItOrc, vlSitProdItOrc, JComboBoxPad.TP_STRING, 2, 0 );

		Vector<String> lbStatusItOrc = new Vector<String>();

		lbStatusItOrc.addElement( "Em aberto" );
		lbStatusItOrc.addElement( "Em aberto" );
		lbStatusItOrc.addElement( "completo/impresso" );
		lbStatusItOrc.addElement( "liberado/aprovado" );
		lbStatusItOrc.addElement( "Faturado" );
		lbStatusItOrc.addElement( "Cancelado/desaprovado" );


		Vector<String> vlStatusItOrc = new Vector<String>();

		vlStatusItOrc.addElement( "*" );
		vlStatusItOrc.addElement( "OA" );
		vlStatusItOrc.addElement( "OC" );
		vlStatusItOrc.addElement( "OL" );
		vlStatusItOrc.addElement( "OV" );
		vlStatusItOrc.addElement( "OP" );
		vlStatusItOrc.addElement( "CA" );

		cbStatusItOrc = new JComboBoxPad( lbStatusItOrc, vlStatusItOrc, JComboBoxPad.TP_STRING, 2, 0 );

		pnCliCab.add( tpnCab );

		pnMaster.remove( 2 ); // Remove o JPanelPad pr�definido da class FDados
		pnGImp.removeAll(); // Remove os bot�es de impress�o para adicionar logo embaixo
		pnGImp.setLayout( new GridLayout( 1, 5 ) ); // redimensiona o painel de impress�o
		pnGImp.setPreferredSize( new Dimension( 210, 26 ) );
		pnGImp.add( btPrevimp );
		pnGImp.add( btImp );
		pnGImp.add( btFechaOrc );
		pnGImp.add( btObs );// Agora o painel est� maior
		pnGImp.add( btOrc );// Bot�o provis�rio para emiss�o de or�amento padr�o

		if ( Aplicativo.nomemodulo.equals( "Atendimento" ) ) {
			pnGImp.add( btOrcTst );// Bot�o para teste de laudo fisioterapia
			pnGImp.add( btOrcTst2 );// Outro bot�o de teste para contrato
		}

		pnTot.setPreferredSize( new Dimension( 120, 200 ) ); // JPanelPad de Totais
		pnTot.add( pinTot );
		pnCenter.add( pnTot, BorderLayout.EAST );
		pnCenter.add( spTab, BorderLayout.CENTER );

		JPanelPad pnLab = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
		pnLab.add( new JLabelPad( " Totais:" ) ); // Label do painel de totais

		pnMaster.add( pnCenter, BorderLayout.CENTER );

		// Adiciona os componentes na tela e no ListaCompos da orcamento
		tpnCab.addTab( "Or�amento", pinCabOrc );

		pinCab = new JPanelPad();
		setListaCampos( lcCampos );

		setPainel( pinCabOrc );

		adicCampo( txtCodOrc, 7, 20, 90, 20, "CodOrc", "N� or�amento", ListaCampos.DB_PK, true );
		adicCampo( txtDtOrc, 440, 20, 107, 20, "DtOrc", "Data", ListaCampos.DB_SI, true );


		if ( Aplicativo.nomemodulo.equals( "Atendimento" )) {
			setAltCab( 190 );
			adicCampoInvisivel( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_FK, txtRazCli, false );
			adicDescFK( txtRazCli, 7, 100, 345, 20, "RazCli", "Raz�o social do cliente" );
			adicCampo( txtCodConv, 100, 20, 87, 20, "CodConv", "C�d.conv.", ListaCampos.DB_FK, txtDescConv, true );
			adicDescFK( txtDescConv, 190, 20, 247, 20, "NomeConv", "Nome do conveniado" );
			adicCampo( txtCodVend, 7, 60, 90, 20, "CodVend", "C�d.comiss.", ListaCampos.DB_FK, txtNomeVend, true );
			adicDescFK( txtNomeVend, 100, 60, 250, 20, "NomeVend", "Nome do comissionado" );
			adicDescFK( txtDescTipoConv, 456, 60, 283, 20, "DescTpConv", "Tipo de conveniado" );
			adicDescFK( txtNomeEnc, 355, 100, 383, 20, "NomeEnc", "Org.Encaminhador" );

			if ( !oPrefs[ Orcamento.PrefOrc.TITORCTXT01.ordinal() ].equals( "" ) ) {
				adicCampo( txtTxt01, 353, 60, 100, 20, "Txt01", oPrefs[ Orcamento.PrefOrc.TITORCTXT01.ordinal() ].toString().trim(), ListaCampos.DB_SI, false );
			}		

			adicCampoInvisivel( txtCodTpConv, "CodTpConv", "C�d.tp.conv.", ListaCampos.DB_FK, txtDescTipoConv, false );
			adicCampoInvisivel( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pg.", ListaCampos.DB_FK, txtDescPlanoPag, true );
			adicCampoInvisivel( txtCodAtend, "CodAtend", "Plano atendente.", ListaCampos.DB_FK, txtDescAtend, false );

		}
		else {

			setAltCab( 160 );

			adicCampo( txtCodCli, 100, 20, 87, 20, "CodCli", "C�d.cli.", ListaCampos.DB_FK, txtRazCli, true );
			adicDescFK( txtRazCli, 190, 20, 247, 20, "RazCli", "Raz�o social do cliente" );
			adicDescFK( txtDescTipoCli, 270, 60, 147, 20, "DescTipoCli", "Desc. do tipo de cliente" );
			adicCampo( txtCodPlanoPag, 420, 60, 77, 20, "CodPlanoPag", "C�d.p.pg.", ListaCampos.DB_FK, txtDescPlanoPag, true );
			adicDescFK( txtDescPlanoPag, 500, 60, 240, 20, "DescPlanoPag", "Descri��o do plano de pagamento" );
			adicCampo( txtCodVend, 7, 60, 90, 20, "CodVend", "C�d.comiss.", ListaCampos.DB_FK, txtNomeVend, true );
			adicDescFK( txtNomeVend, 100, 60, 167, 20, "NomeVend", "Nome do comissionado" );
			adicCampoInvisivel( txtCodClComiss, "CodClComis", "C�d.cl.comiss.", ListaCampos.DB_FK, txtDescClComiss, false );

			if(Aplicativo.nomemodulo.equals( "CRM" )) {

				adicCampoInvisivel( txtCodAtend, "CodAtend", "Plano atendente.", ListaCampos.DB_FK, txtDescAtend, false );

			}

		}

		adicCampo( txtDtVencOrc, 550, 20, 87, 20, "DtVencOrc", "Dt.valid.", ListaCampos.DB_SI, true );
		adicCampo( txtPrazoEntOrc, 640, 20, 100, 20, "PrazoEntOrc", "Dias p/ entrega", ListaCampos.DB_SI, false );

		adicCampoInvisivel( txtPercDescOrc, "PercDescOrc", "% desc.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrDescOrc, "VlrDescOrc", "Vlr.desc.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtPercAdicOrc, "PercAdicOrc", "% adic.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtVlrAdicOrc, "VlrAdicOrc", "Vlr.adic.", ListaCampos.DB_SI, false );

		/*
		 * porque usar estes campos? adicCampoInvisivel(txtVlrEdDescOrc, "VlrDescOrc", "Vlr.desc.",ListaCampos.DB_SI, false); adicCampoInvisivel(txtVlrEdAdicOrc, "VlrAdicOrc", "Vlr.adic.",ListaCampos.DB_SI, false);
		 */

		adicCampoInvisivel( txtStatusOrc, "StatusOrc", "Status", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodTipoMov, "codtipomov", "C�d.Tipo Mov.", ListaCampos.DB_FK, false );

		if ( "S".equals( oPrefs[ Orcamento.PrefOrc.ABATRANSP.ordinal() ].toString() ) ) {
			tpnCab.addTab( "Transportadora", pinCabTransp );
			setListaCampos( lcCampos );
			setPainel( pinCabTransp );
			adicCampo( txtCodTran, 7, 25, 70, 20, "Codtran", "C�d.transp.", ListaCampos.DB_FK, true );
			adicDescFK( txtRazTran, 80, 25, 250, 20, "Raztran", "Raz�o social da transportadora" );
			adicDB( rgTipoFrete, 333, 25, 140, 25, "TipoFrete", "Tipo de Frete", false );
			adicDB( cbAdicFrete, 476, 25, 240, 25, "AdicFrete", "Adic. Frete", false );

			adicCampo( txtVlrFreteOrc, 7, 65, 70, 20, "VlrFreteOrc", "Vlr. frete", ListaCampos.DB_SI, false );
		}


		tpnCab.addTab( "Previs�o", pinPrevisao );
		setListaCampos( lcCampos );
		setPainel( pinPrevisao );
		adicCampo( txtDtPreventOrc, 7, 25, 80, 20, "DTPREVENTORC", "Dt.previs�o", ListaCampos.DB_SI, false );
		adicCampo( txtHPreventOrc, 90, 25, 80, 20, "HPREVENTORC", "H.previs�o", ListaCampos.DB_SI, false );

		adicCampoInvisivel( txtACOrc, "ACOrc", "Aos cuidados", ListaCampos.DB_SI, false );		

		setListaCampos( true, "ORCAMENTO", "VD" );

		tpnCab.addTab( "Pedidos", pinCabPedidos );

		pinCabPedidos.add( spPedidos, BorderLayout.CENTER );

		montaTabelaPedidos();
		
		tpnCab.addTab( "Atendimentos", pinCabAtendimentos );

		pinCabAtendimentos.add( spAtendimentos, BorderLayout.CENTER );

		montaTabelaAtend();

		JPanelPad navEast = new JPanelPad();
		navEast.setPreferredSize( new Dimension( 354, 30 ) );
		navEast.adic( btCriaLancamento, 0, 0, 140, 25 );
		navEast.adic( lbStatus, 143, 3, 150, 20 );
		navEast.adic( btCopiaOrcamento, 296, 0, 28, 25 );
		navEast.adic( btReplicaOrcamento, 326, 0, 28, 25 );

		navEast.tiraBorda();
		pnNavCab.add( navEast, BorderLayout.EAST );

		txtVlrLiqItOrc.setEditable( (Boolean) oPrefs[ Orcamento.PrefOrc.HABVLRTOTITORC.ordinal() ] );
		btCriaLancamento.setEnabled( false );

		if ( ( "S".equals( permusu.get( "VISUALIZALUCR" ) ) ) && (Boolean) ( oPrefs[ Orcamento.PrefOrc.VISUALIZALUCR.ordinal() ] ) ) {
			adicPainelLucr();
		}
		
		btReplicaOrcamento.setEnabled( false );

	}

	private void montaTabelaPedidos() {

		tabPedidos.adicColuna( "C�d.venda" );
		tabPedidos.adicColuna( "Documento" );
		tabPedidos.adicColuna( "S�rie" );
		tabPedidos.adicColuna( "C�d.cli." );
		tabPedidos.adicColuna( "Raz�o social do cliente" );
		tabPedidos.adicColuna( "Emiss�o" );
		tabPedidos.adicColuna( "Sa�da" );
		tabPedidos.adicColuna( "C�d.pag." );
		tabPedidos.adicColuna( "Descri��o do plano de pagamento" );
		tabPedidos.adicColuna( "Item" );
		tabPedidos.adicColuna( "Quantidade" );
		tabPedidos.adicColuna( "Pre�o" );
		tabPedidos.adicColuna( "Vlr. l�quido" );
		tabPedidos.adicColuna( "Tipo Venda" );

		tabPedidos.setTamColuna( 80, OrcVenda.CODVENDA.ordinal() );
		tabPedidos.setTamColuna( 80, OrcVenda.DOCVENDA.ordinal() );
		tabPedidos.setTamColuna( 50, OrcVenda.SERIE.ordinal() );
		tabPedidos.setTamColuna( 80, OrcVenda.CODCLI.ordinal() );
		tabPedidos.setTamColuna( 200, OrcVenda.RAZCLI.ordinal() );
		tabPedidos.setTamColuna( 80, OrcVenda.DTEMISSAO.ordinal() );
		tabPedidos.setTamColuna( 80, OrcVenda.DTSAIDA.ordinal() );
		tabPedidos.setTamColuna( 80, OrcVenda.CODPAG.ordinal() );
		tabPedidos.setTamColuna( 200, OrcVenda.DESCPAG.ordinal() );
		tabPedidos.setTamColuna( 80, OrcVenda.CODITVENDA.ordinal() );
		tabPedidos.setTamColuna( 80, OrcVenda.QTDITVENDA.ordinal() );
		tabPedidos.setTamColuna( 80, OrcVenda.PRECOITVENDA.ordinal() );
		tabPedidos.setTamColuna( 80, OrcVenda.VLRLIQITVENDA.ordinal() );
		tabPedidos.setColunaInvisivel( OrcVenda.TIPOVENDA.ordinal() );

		tabPedidos.addMouseListener( this );
	}
	
	private void montaTabelaAtend() {

		tabAtendimentos.adicColuna( "Doc." ); // Documento do atendimento
		tabAtendimentos.adicColuna( "Status" ); // Status do atendimento
		tabAtendimentos.adicColuna( "Data" ); // Data inicio atendimento
		tabAtendimentos.adicColuna( "C�d.atd." ); // C�digo do atendimento
		tabAtendimentos.adicColuna( "C�d.orc." ); // C�digo do or�amento
		tabAtendimentos.adicColuna( "Data fim" ); // Data final atendimento
		tabAtendimentos.adicColuna( "Cliente" ); // Nome do cliente
		tabAtendimentos.adicColuna( "Atendimento" ); // Observa��es do atendimento
		tabAtendimentos.adicColuna( " C�d. Atend." );// C�digo do atendente
		tabAtendimentos.adicColuna( "Atendente" ); // C�digo do atendente
		tabAtendimentos.adicColuna( "Inicio" ); // Hora inicial
		tabAtendimentos.adicColuna( "Fim" ); // Hora final
		tabAtendimentos.adicColuna( "Tempo" ); // Tempo de atendimento
		tabAtendimentos.adicColuna( "Cobran�a" ); // Tempo de atendimento
		tabAtendimentos.adicColuna( "Cham." ); // C�digo do chamado
		tabAtendimentos.adicColuna( "Cod.Cli." ); // C�digo do cliente
		tabAtendimentos.adicColuna( "C�d.Esp." ); // C�digo da especifica��o
		tabAtendimentos.adicColuna( "Descri��o da especifica��o" ); // Descri��o da especifica��o

		tabAtendimentos.setTamColuna( 45, COL_ATENDIMENTO.CODATENDO.ordinal() );
		tabAtendimentos.setTamColuna( 45, COL_ATENDIMENTO.CODORC.ordinal() );
		tabAtendimentos.setTamColuna( 150, COL_ATENDIMENTO.NOMECLI.ordinal() );
		tabAtendimentos.setTamColuna( 250, COL_ATENDIMENTO.OBSATENDO.ordinal() );
		tabAtendimentos.setTamColuna( 45, COL_ATENDIMENTO.HORAATENDO.ordinal() );
		tabAtendimentos.setTamColuna( 45, COL_ATENDIMENTO.HORAATENDOFIN.ordinal() );
		tabAtendimentos.setTamColuna( 45, COL_ATENDIMENTO.TEMPO.ordinal() );
		tabAtendimentos.setTamColuna( 45, COL_ATENDIMENTO.TEMPOCOB.ordinal() );
		tabAtendimentos.setTamColuna( 45, COL_ATENDIMENTO.CODCHAMADO.ordinal() );
		tabAtendimentos.setTamColuna( 150, COL_ATENDIMENTO.DESCESPEC.ordinal() );
		// tabatd.setColunaInvisivel( COL_ATENDIMENTO.CODATENDO.ordinal() );
		tabAtendimentos.setTamColuna(45, COL_ATENDIMENTO.DOCATENDO.ordinal() );
		tabAtendimentos.setTamColuna(45, COL_ATENDIMENTO.STATUSATENDO.ordinal() );
		tabAtendimentos.setTamColuna(60, COL_ATENDIMENTO.DATAATENDOFIN.ordinal() );
		tabAtendimentos.setTamColuna(45, COL_ATENDIMENTO.CODATEND.ordinal() );
		tabAtendimentos.setTamColuna(45, COL_ATENDIMENTO.CODCLI.ordinal() );
		tabAtendimentos.setTamColuna(45, COL_ATENDIMENTO.CODESPEC.ordinal() );
		tabAtendimentos.setRowHeight( 20 );

		tabAtendimentos.addMouseListener( this );

	}

	// Fun��o criada para montar a tela conforme a prefer�ncia do usu�rio:
	// com ou sem Refer�ncia sendo PK;
	private void montaDetalhe() {

		pnDet.removeAll();

		pnDet.add( tpnDet );

		tpnDet.addTab( "Item", pinDet );
		setPainel( pinDet );

		setAltDet( 130 ); 
		pinDet = new JPanelPad( 740, 100 );
		//		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		adicCampo( txtCodItOrc, 7, 20, 30, 20, "CodItOrc", "Item", ListaCampos.DB_PK, true );

		if ( (Boolean) oPrefs[ Orcamento.PrefOrc.USAREFPROD.ordinal() ] ) {
			adicCampoInvisivel( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_FK, txtDescProd, false );
			adicCampoInvisivel( txtRefProd, "RefProd", "Ref.prod.", ListaCampos.DB_FK, true );
			adic( new JLabelPad( "Refer�ncia" ), 40, 0, 67, 20 );
			adic( txtRefProd, 40, 20, 67, 20 );
			txtRefProd.setFK( true );
			txtRefProd.setBuscaAdic( new DLBuscaProd( con, "REFPROD", lcProd2.getWhereAdic() ) );
		}
		else {
			adicCampo( txtCodProd, 40, 20, 67, 20, "CodProd", "C�d.prod.", ListaCampos.DB_FK, txtDescProd, true );
			txtCodProd.setBuscaAdic( new DLBuscaProd( con, "CODPROD", lcProd.getWhereAdic() ) );
		}

		txtQtdItOrc.setBuscaAdic( new DLBuscaEstoq( lcDet, lcAlmox, lcProd, con, "qtditvenda" ) );
		txtCodAlmoxItOrc.setAtivo( false );

		adicDescFK( txtDescProd, 110, 20, ( ( (Boolean) oPrefs[ Orcamento.PrefOrc.USALOTEORC.ordinal() ] ) ? 187 : 277 ), 20, "DescProd", "Descri��o do produto" );

		adicDBLiv( txaObsItOrc, "ObsItOrc", "Observa��o", false );

		if ( (Boolean) oPrefs[ Orcamento.PrefOrc.USALOTEORC.ordinal() ] ) {
			adicCampo( txtCodLote, 300, 20, 88, 20, "CodLote", "Lote", ListaCampos.DB_FK, txtDescLote, false );
		}
		adicCampo( txtQtdItOrc, 391, 20, 60, 20, "QtdItOrc", "Qtd.", ListaCampos.DB_SI, true );
		adicCampo( txtPrecoItOrc, 454, 20, 60, 20, "PrecoItOrc", "Pre�o", ListaCampos.DB_SI, true );
		adicCampo( txtPercDescItOrc, 517, 20, 60, 20, "PercDescItOrc", "% desc.", ListaCampos.DB_SI, false );
		adicCampo( txtVlrDescItOrc, 580, 20, 60, 20, "VlrDescItOrc", "Vlr. desc.", ListaCampos.DB_SI, false );

		adicCampo( txtPercComisItOrc, 643, 20, 60, 20, "PercComisItOrc", "% comis.", ListaCampos.DB_SI, false );
		adicCampo( txtVlrComisItOrc, 706, 20, 57, 20, "VlrComisItOrc", "Vlr.comis.", ListaCampos.DB_SI, false );

		adicCampoInvisivel( txtVlrProdItOrc, "VlrProdItOrc", "Valor bruto", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtStrDescItOrc, "StrDescItOrc", "Descontos", ListaCampos.DB_SI, false );

		// if ( "S".equals(oPrefs[Orcamento.PrefOrc.CONTRIBIPI.ordinal()].toString()) ) {
		adic( new JLabelPad( "Vlr. IPI" ), 7, 40, 99, 20 );
		adic( txtVlrIPIItOrc, 7, 60, 99, 20 );
		// }

		adicCampo( txtVlrLiqItOrc, 109, 60, 80, 20, "VlrLiqItOrc", "Valor item", ListaCampos.DB_SI, false );

		adicCampo( txtCodAlmoxItOrc, 391, 60, 60, 20, "CodAlmox", "Cd.almox.", ListaCampos.DB_FK, txtDescAlmoxItOrc, false );
		adicDescFK( txtDescAlmoxItOrc, 454, 60, 200, 20, "DescAlmox", "Descri��o do almoxarifado" );
		adicDescFK( txtCodUnid, 657, 60, 30, 20, "CodUnid", "Unid" );
		adicDescFK( txtSldLiqProd, 690, 60, 70, 20, "SldLiqProd", "Saldo" );

		adicCampoInvisivel( txtCodEmpLG, "CodEmpLG", "Emp.log.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodFilialLG, "CodFilialLG", "Filial log.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodLog, "CodLog", "C�d.log.", ListaCampos.DB_SI, false );

		txtFatItOrc.setSoLeitura( true );
		txtSitProdItOrc.setSoLeitura( true );
		txtStatusItOrc.setSoLeitura( true );

		adicCampoInvisivel( txtFatItOrc, "fatitorc", "", ListaCampos.DB_SI	, false );
		adicCampoInvisivel( txtSitProdItOrc, "sitproditorc", "", ListaCampos.DB_SI	, false );
		adicCampoInvisivel( txtStatusItOrc, "StatusItOrc", "", ListaCampos.DB_SI	, false );


		/**********************************Aba de Status dos itens do or�amento *************************/

		tpnDet.addTab( "Status", pinDetStatus );	

		setPainel( pinDetStatus );

		adicDB( cbEmiteItOrc		, 7		, 20	, 80, 20	, "EmitItOrc"		, "Emitido"		, false );
		adicDB( cbAceiteItOrc		, 90	, 20	, 80, 20	, "AceiteItOrc"		, "Aceito"		, false );		
		adicDB( cbAprovItOrc		, 173	, 20	, 80, 20	, "AprovItOrc"		, "Aprovado"	, false );
		adicDB( cbCancItOrc			, 256	, 20	, 80, 20	, "CancItOrc"		, "Cancelado"	, false );
		adicDB( cbSitEntItOrc		, 339	, 20	, 80, 20	, "SitEntItOrc"		, "Entregue"	, false );


		adic( cbFatItOrc			, 7		, 60	, 80, 20	, "Faturado"	, false );
		adic( cbSitProdItOrc		, 90	, 60	, 150, 20	, "Produ��o"	, false );		
		adic( cbStatusItOrc			, 256	, 60	, 150, 20	, "Status"		, false );


		cbEmiteItOrc.setEnabled( false );
		cbAceiteItOrc.setEnabled( false );

		cbAprovItOrc.setEnabled( false );
		cbCancItOrc.setEnabled( false );
		cbFatItOrc.setEnabled( false );
		cbSitEntItOrc.setEnabled( false );
		cbSitProdItOrc.setEnabled( false );
		cbStatusItOrc.setEnabled( false );


		/**********************************************************************************************/

		pinTot.adic( new JLabelPad( "Total desc." ), 7, 0, 90, 20 );
		pinTot.adic( txtVlrDescOrc, 7, 20, 100, 20 );
		pinTot.adic( new JLabelPad( "Total adic." ), 7, 40, 90, 20 );
		pinTot.adic( txtVlrAdicOrc, 7, 60, 100, 20 );
		pinTot.adic( new JLabelPad( "Total geral" ), 7, 80, 90, 20 );
		pinTot.adic( txtVlrLiqOrc, 7, 100, 100, 20 );

		setListaCampos( true, "ITORCAMENTO", "VD" );
		montaTab();

		tab.setAutoRol( true );

		tab.setTamColuna( 30, 0 );
		tab.setTamColuna( 70, 1 );
		tab.setTamColuna( 230, 2 );
		tab.setTamColuna( 60, 3 );
		tab.setTamColuna( 230, 4 );
		tab.setTamColuna( 60, 5 );
		tab.setTamColuna( 70, 6 );
		tab.setTamColuna( 70, 7 );
	}


	//M�todo que bloqueia a edi��o dos campos de comiss�o no or�amento e item do or�amento.
	public void bloqueiaComissao(boolean bloqueia) {
		txtVlrComisItOrc.setAtivo(!bloqueia);
		txtPercComisItOrc.setAtivo(!bloqueia);
	}

	public void setLog( String[] args ) {

		if ( args != null ) {
			txtCodEmpLG.setVlrString( args[ 0 ] );
			txtCodFilialLG.setVlrString( args[ 1 ] );
			txtCodLog.setVlrString( args[ 2 ] );
		}
	}

	public void setParansPreco( BigDecimal bdPreco ) {
		txtPrecoItOrc.setVlrBigDecimal( bdPreco );
	}

	public Vector<JTextFieldPad> getParansDesconto() {

		Vector<JTextFieldPad> param = new Vector<JTextFieldPad>();
		param.addElement( txtStrDescItOrc );
		param.addElement( txtPrecoItOrc );
		param.addElement( txtVlrDescItOrc );
		param.addElement( txtQtdItOrc );
		return param;
	}

	public String[] getParansPass() {
		return new String[] { "or�amento", txtCodOrc.getVlrString().trim(), 
				txtCodItOrc.getVlrString().trim(), txtCodProd.getVlrString().trim(), 
				txtVlrProdItOrc.getVlrString().trim() };
	}

	private Integer getClcomiss( int codvend ) {

		Integer result = 0;
		Integer codemp = Aplicativo.iCodEmp;
		Integer codfilial = ListaCampos.getMasterFilial( "VDVENDEDOR" );
		try {
			
			result = getDaoorcamento().getClcomiss( codfilial, codvend );
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar a class. da comiss�o." + err.getMessage(), true, con, err );
			err.printStackTrace();
		}

		return result;
	}

	private Integer getCodCli() {

		Integer result = null;

		try {
			result = (Integer) oPrefs[ Orcamento.PrefOrc.CODCLI.ordinal() ];
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o c�digo do cliente.\n"
						+ "Provavelmente n�o foram gravadas corretamente as prefer�ncias!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}

		return result;
	}

	private Integer getCodTipoCli() {
		Integer result = null;
		try {
			result = getDaoorcamento().getCodtipocli( ListaCampos.getMasterFilial( "VDCLIENTE" ), txtCodCli.getVlrInteger() );
		} catch (Exception err) {
			Funcoes.mensagemErro( this, err.getMessage() );
		}
		return result;
	}

	private void getLote() {
		txtCodLote.setVlrString( getLote( txtCodProd.getVlrInteger().intValue(), ( (Boolean) oPrefs[ Orcamento.PrefOrc.CONTESTOQ.ordinal() ] ).booleanValue() ) );
		lcLote.carregaDados();
	}

	public int[] getParansPreco() {
		int[] iRetorno = { txtCodProd.getVlrInteger().intValue(), txtCodCli.getVlrInteger().intValue(), Aplicativo.iCodEmp, 
				ListaCampos.getMasterFilial( "VDCLIENTE" ), txtCodPlanoPag.getVlrInteger().intValue(), Aplicativo.iCodEmp, 
				ListaCampos.getMasterFilial( "FNPLANOPAG" ), ((Integer) oPrefs[ Orcamento.PrefOrc.CODTIPOMOV2.ordinal() ] ).intValue(), 
				Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "EQTIPOMOV" ), Aplicativo.iCodEmp, Aplicativo.iCodFilial, 
				txtCodOrc.getVlrInteger().intValue(), Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDORCAMENTO" ) };
		return iRetorno;
	}

	private int getPlanoPag() {

		int iRet = 0;

		try {
			iRet = Integer.parseInt( oPrefs[ Orcamento.PrefOrc.CODPLANOPAG.ordinal() ].toString() );
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o plano de pagamento.\n" + "Provavelmente n�o foram gravadas corretamente as prefer�ncias!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}

		return iRet;
	}

	private Integer getPrazo() {
		Integer result = null;
		try {
			result = Orcamento.getPrazo(oPrefs);
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o prazo.\n" + "Provavelmente n�o foram gravadas corretamente as prefer�ncias!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
		return result;
	}

	private Integer getVendedor() {
		Integer result = null;
		try {
			result = getDaoorcamento().getVendedor( 
					ListaCampos.getMasterFilial( "VDCLIENTE" ), txtCodCli.getVlrInteger().intValue()
					,  ListaCampos.getMasterFilial( "SGUSUARIO" ), Aplicativo.getUsuario().getIdusu() );
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, err.getMessage() );
		}
		return result;
	}

	private void calcDescIt() {

		if ( txtPercDescItOrc.floatValue() > 0 ) {
			txtVlrDescItOrc.setVlrBigDecimal( new BigDecimal( Funcoes.arredFloat( txtVlrProdItOrc.floatValue() * txtPercDescItOrc.floatValue() / 100, casasDecPre ) ) );
			bdVlrDescItAnt = txtVlrDescItOrc.getVlrBigDecimal();
		} else if ( txtVlrDescItOrc.floatValue() == 0 ) {
			txtPercDescItOrc.setVlrString( "" );
			bdVlrDescItAnt = txtVlrDescItOrc.getVlrBigDecimal();
		}
	}

	private void calcPercDescIt() {

		if ( txtVlrDescItOrc.floatValue() > 0 ) {

			BigDecimal vlrdesc = txtVlrDescItOrc.getVlrBigDecimal();
			BigDecimal vlrprod = txtVlrProdItOrc.getVlrBigDecimal();

			txtPercDescItOrc.setVlrBigDecimal( ( vlrdesc.multiply( cem ) ).divide( vlrprod, casasDecPre, BigDecimal.ROUND_HALF_UP ) );

			bdVlrDescItAnt = txtVlrDescItOrc.getVlrBigDecimal();
		} else if ( txtVlrDescItOrc.floatValue() == 0 ) {
			txtPercDescItOrc.setVlrString( "" );
			bdVlrDescItAnt = txtVlrDescItOrc.getVlrBigDecimal();
		}
	}

	private void calcTot() {
		txtVlrLiqItOrc.setVlrBigDecimal( impostos.calcVlrTotalProd( txtVlrProdItOrc.getVlrBigDecimal(), txtVlrDescItOrc.getVlrBigDecimal() ) );
	}

	private void calcVlrProd() {
		txtVlrProdItOrc.setVlrBigDecimal( calcVlrProd( txtPrecoItOrc.getVlrBigDecimal(), txtQtdItOrc.getVlrBigDecimal() ) );
	}

	private boolean testaLucro() {
		return super.testaLucro( new Object[] { txtCodProd.getVlrInteger(), txtCodAlmoxItOrc.getVlrInteger(), txtPrecoItOrc.getVlrBigDecimal(), }, fatluc, new Date() );
	}

	private boolean testaCodlote() {

		boolean result = false;
		
		try {
			result = getDaoorcamento().testaCodlote(  ListaCampos.getMasterFilial( "EQLOTE" )
					, txtCodProd.getVlrInteger(), txtCodLote.getVlrString().trim() );
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, err.getMessage());
		}

		return result;
	}

	private void mostraTelaDescont() {

		if ( lcDet.getStatus() == ListaCampos.LCS_INSERT || lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
			txtPercDescItOrc.setVlrString( "" );
			txtVlrDescItOrc.setVlrString( "" );
			calcVlrProd();
			calcTot();
			mostraTelaDesconto();
			calcVlrProd();
			calcTot();
			txtVlrDescItOrc.requestFocus( true );
		}
	}

	private void carregaPercComis() {

		if ( lcDet.getStatus() == ListaCampos.LCS_INSERT || lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
			MathContext mcPerc= new MathContext( casasDecFin, RoundingMode.HALF_EVEN   );

			BigDecimal perccomisvend = txtPercComVend.getVlrBigDecimal();
			BigDecimal perccomisprod = txtPercComProd.getVlrBigDecimal();
			BigDecimal perccomisitem = new BigDecimal( 0 );
			BigDecimal vlrcomisitem = new BigDecimal( 0 );
			BigDecimal vlrliqitem = txtVlrLiqItOrc.getVlrBigDecimal();

			if ( ( perccomisvend != null && perccomisvend.floatValue() > 0 ) && ( perccomisprod != null && perccomisprod.floatValue() > 0 ) ) {
				perccomisvend = perccomisvend.divide( cem,  mcPerc );
				perccomisprod = perccomisprod.divide( cem, mcPerc );

				perccomisitem = perccomisvend.multiply( perccomisprod );

				vlrcomisitem = vlrliqitem.multiply( perccomisitem );

				txtPercComisItOrc.setVlrBigDecimal( perccomisitem.multiply( cem ) );
				txtVlrComisItOrc.setVlrBigDecimal( vlrcomisitem );

			}
		}
	}

	private void calcComisIt() {

		if ( txtPercComisItOrc.floatValue() >= 0 ) {
			if( !(Boolean) oPrefs[ Orcamento.PrefOrc.COMISSAODESCONTO.ordinal()] ){
				txtVlrComisItOrc.setVlrBigDecimal( new BigDecimal( 
						Funcoes.arredFloat( 
								( txtVlrProdItOrc.floatValue() - txtVlrDescItOrc.floatValue() ) * 
								txtPercComisItOrc.floatValue() / 100 * 
								txtPercComVend.floatValue() / 100, 
								casasDecFin ) ) );
			}else{
				txtVlrComisItOrc.setVlrBigDecimal( new BigDecimal( 
						Funcoes.arredFloat( 
								( txtVlrProdItOrc.floatValue() - txtVlrDescItOrc.floatValue() ) * 
								txtPercComisItOrc.floatValue() / 100 , 
								casasDecFin ) ) );
			}
		}
	}

	private void calcComis() {

		if ( lcDet.getStatus() == ListaCampos.LCS_INSERT || lcDet.getStatus() == ListaCampos.LCS_EDIT ) {

			BigDecimal perccomisitem = txtPercComisItOrc.getVlrBigDecimal();
			BigDecimal vlrcomisitem = new BigDecimal( 0 );
			BigDecimal vlrliqitem = txtVlrLiqItOrc.getVlrBigDecimal();

			if ( perccomisitem != null && perccomisitem.floatValue() > 0 ) {

				vlrcomisitem = vlrliqitem.multiply( perccomisitem.divide( cem ) );

				// txtPercComisItOrc.setVlrBigDecimal( perccomisitem.multiply( cem ) );
				txtVlrComisItOrc.setVlrBigDecimal( vlrcomisitem );

			}
		}
	}

	private void carregaStatus() {
		btReplicaOrcamento.setEnabled( false );
		if ((Boolean) oPrefs[PrefOrc.REPLICAORC.ordinal()]) {
			if (Orcamento.STATUS_APROVADO.getValue().equals( txtStatusOrc.getVlrString() ) 
					|| Orcamento.STATUS_FATURADO.getValue().equals( txtStatusOrc.getVlrString() )
					|| Orcamento.STATUS_FATURADO_PARCIAL.getValue().equals( txtStatusOrc.getVlrString() )) {
				btReplicaOrcamento.setEnabled( true );
			}
		}
		if ( Orcamento.STATUS_ABERTO.getValue().equals( txtStatusOrc.getVlrString() ) || Orcamento.STATUS_PENDENTE.getValue().equals( txtStatusOrc.getVlrString() ) ) {
			lbStatus.setText( Orcamento.STATUS_ABERTO.getName() + "/" + Orcamento.STATUS_PENDENTE.getName() );
			lbStatus.setBackground( Color.ORANGE );
			lbStatus.setVisible( true );
		} else if ( Orcamento.STATUS_COMPLETO.getValue().equals( txtStatusOrc.getVlrString() ) ) {
			lbStatus.setText( Orcamento.STATUS_COMPLETO.getName() );
			lbStatus.setBackground( Color.BLUE );
			lbStatus.setVisible( true );
		} else if ( Orcamento.STATUS_APROVADO.getValue().equals( txtStatusOrc.getVlrString() ) ) {
			lbStatus.setText( Orcamento.STATUS_APROVADO.getName() );
			lbStatus.setBackground( new Color( 45, 190, 60 ) );
			lbStatus.setVisible( true );
		} else if ( Orcamento.STATUS_FATURADO.getValue().equals( txtStatusOrc.getVlrString() ) ) {
			lbStatus.setText( Orcamento.STATUS_FATURADO.getName() );
			lbStatus.setBackground( Color.RED );
			lbStatus.setVisible( true );
		} else if ( Orcamento.STATUS_FATURADO_PARCIAL.getValue().equals( txtStatusOrc.getVlrString() ) ) {
			lbStatus.setText( Orcamento.STATUS_FATURADO_PARCIAL.getName() );
			lbStatus.setBackground( Color.RED );
			lbStatus.setVisible( true );
		} else if ( Orcamento.STATUS_PRODUZIDO.getValue().equals( txtStatusOrc.getVlrString() ) ) {
			lbStatus.setText( Orcamento.STATUS_PRODUZIDO.getName() );
			lbStatus.setBackground( new Color(123,83,83) );
			lbStatus.setVisible( true );
		} else if ( Orcamento.STATUS_CANCELADO.getValue().equals( txtStatusOrc.getVlrString() ) ) {
			lbStatus.setText( Orcamento.STATUS_CANCELADO.getName() );
			lbStatus.setBackground( Color.RED );
			lbStatus.setVisible( true );
		} else {
			lbStatus.setForeground( Color.WHITE );
			lbStatus.setBackground( Color.GRAY );
			lbStatus.setText( "" );
			lbStatus.setVisible( false );
		}

	}

	private void carregaPedidos() {
		tabPedidos.limpa();
		try {
			Vector<Vector<Object>> dataVector = getDaoorcamento().carregaPedidos( txtCodOrc.getVlrInteger() );
			for (Vector<Object> row: dataVector) {
				tabPedidos.adicLinha( row );
			}
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, err.getMessage() );
		}
	}
	
	private void carregaTabAtendo() {
		try {
			tabAtendimentos.limpa();
			tabAtendimentos.setDataVector( daoatend.carregaGridPorCliente( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "ATATENDIMENTO" ), 
					txtCodCli.getVlrInteger(), daoatend.getCodatend_atual(), daoatend.isAcesatdolerout()));
		} catch (SQLException e) {
			Funcoes.mensagemErro( this, "Erro ao carregar grid de atendimento!!!" );
		}
	}

	private void abreVenda() {

		if ( tabPedidos.getLinhaSel() > -1 ) {
			FVenda tela = null;
			if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
				tela = (FVenda) Aplicativo.telaPrincipal.getTela( FVenda.class.getName() );
			} else {
				tela = new FVenda();
				Aplicativo.telaPrincipal.criatela( "Venda", tela, con );
			}
			tela.exec( (Integer) tabPedidos.getValor( tabPedidos.getLinhaSel(), OrcVenda.CODVENDA.ordinal() ), (Integer) tabPedidos.getValor( tabPedidos.getLinhaSel(), OrcVenda.CODITVENDA.ordinal() ), (String) tabPedidos.getValor( tabPedidos.getLinhaSel(), OrcVenda.TIPOVENDA.ordinal() ) );
		}
	}
	
	private void visualizaAtend() {

		if (daoatend.getCodatend_atual() == null) {
			Funcoes.mensagemInforma( this, "N�o existe atendente vinculado a este usu�rio, verifique!!!" );
			return;
		}

		Integer codatendo = (Integer) tabAtendimentos.getValor( tabAtendimentos.getLinhaSel(), COL_ATENDIMENTO.CODATENDO.ordinal() );
		Integer codatend = (Integer) tabAtendimentos.getValor( tabAtendimentos.getLinhaSel(), COL_ATENDIMENTO.CODATEND.ordinal());
		boolean atendimentoBloqueado = false;
		boolean bloqueiaFinalizar = false;

		int icodAtend = codatend;
		int icodAtendo = codatendo;

		Integer codchamado = (Integer) tabAtendimentos.getValor( tabAtendimentos.getLinhaSel(), COL_ATENDIMENTO.CODCHAMADO.ordinal() );

		try {

			FNovoAtend dl = new FNovoAtend( true );
			atendimentoBloqueado = !daoatend.bloquearAtendimentos( codatendo, (String) tabAtendimentos.getValor( tabAtendimentos.getLinhaSel(), COL_ATENDIMENTO.DATAATENDOFIN.ordinal() ), 
					(String) tabAtendimentos.getValor( tabAtendimentos.getLinhaSel(), COL_ATENDIMENTO.HORAATENDOFIN.ordinal() ),
					codatend );
			bloqueiaFinalizar = daoatend.bloquearChamadosFinalizar( daoatend.getCodatend_atual() );

			if ( dl != null && dl.isUpdate() ) {
				dl.adicAtendimento( txtCodCli.getVlrInteger(), codchamado, this, true, con, icodAtendo, 
						icodAtend, "A", false, (Integer) tabAtendimentos.getValor( tabAtendimentos.getLinhaSel(), COL_ATENDIMENTO.CODORC.ordinal() ), 
								atendimentoBloqueado, bloqueiaFinalizar );
			}
			else {
				dl = new FNovoAtend( txtCodCli.getVlrInteger(), codchamado, this, true, con, icodAtendo
						, icodAtend, "A", false, null, (Integer) tabAtendimentos.getValor( tabAtendimentos.getLinhaSel()
								, COL_ATENDIMENTO.CODORC.ordinal() ),atendimentoBloqueado, bloqueiaFinalizar  );
			}
			if ( fPrim.temTela( "Edi��o de Atendimento: " + icodAtendo ) == false ) {
				fPrim.criatela( "Edi��o de Atendimento: " + icodAtendo, dl, con );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar campos!" );
		}

	}

	private void fechaOrc() {

		Object[] oValores = null;

		String destinatario = txtACOrc.getVlrString().equals( "" ) ? txtContCli.getVlrString() : txtACOrc.getVlrString();

		DLCompOrc dl = new DLCompOrc( this, ( txtVlrDescOrc.floatValue() > 0 ), txtVlrProdOrc.getVlrBigDecimal(), txtPercDescOrc.getVlrBigDecimal(), 
				txtVlrDescOrc.getVlrBigDecimal(), txtPercAdicOrc.getVlrBigDecimal(), txtVlrAdicOrc.getVlrBigDecimal(),
				txtCodPlanoPag.getVlrInteger(), daoemail, destinatario );
		try {

			// Verifica se o or�amento foi gerado por um atendimento e adiciona a PK para ser preenchida na tela de complemento.
			if ( txtStatusOrc.getVlrString().equals( "OA" ) || txtCodAtend.getVlrInteger().intValue() > 0 ) {
				dl.setFKAtend( txtCodAtend.getVlrInteger().intValue() );

			}

			dl.setConexao( con );
			dl.setVisible( true );

			if ( dl.OK ) {
				oValores = dl.getValores();
				dl.dispose();
			} else {
				dl.dispose();
			}

			if ( oValores != null ) {

				//	lcCampos.carregaDados();

				lcCampos.edit( );

				txtPercDescOrc.setVlrBigDecimal( (BigDecimal) oValores[ 0 ] );
				txtVlrDescOrc.setVlrBigDecimal( (BigDecimal) oValores[ 1 ] );
				txtPercAdicOrc.setVlrBigDecimal( (BigDecimal) oValores[ 2 ] );
				txtVlrAdicOrc.setVlrBigDecimal( (BigDecimal) oValores[ 3 ] );

				if ( oValores[ 3 ] != txtCodPlanoPag.getVlrInteger() ) {
					txtCodPlanoPag.setVlrInteger( (Integer) ( oValores[ 4 ] ) );
				}

				// pega o status antigo do or�amento;
				oldStatusOrc = txtStatusOrc.getVlrString().trim();
				// Ajusta o status para OC - or�amento completo.
				if ( "OC-OL-OV".indexOf( oldStatusOrc ) == -1 ) {
					txtStatusOrc.setVlrString( "OC" );
				}
				if ( ( oValores[ 7 ] != null ) && ( ( (Integer) oValores[ 7 ] ) ).intValue() > 0 ) {
					txtCodAtend.setVlrInteger( (Integer) oValores[ 7 ] );
				}

				if ( ( oValores[ 8 ] != null ) ) {
					txtACOrc.setVlrString( oValores[ 8 ].toString() );
				}


				lcCampos.post();
				lcCampos.carregaDados();
				if ( oValores[ 5 ].equals( "S" ) ) {
					aprovar();
					lcCampos.carregaDados();
				}

				if ( oValores[ 6 ].equals( "S" ) ) {
					imprimir( TYPE_PRINT.VIEW );
				}

			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			oValores = null;
			dl = null;
		}
	}

	private void copiaOrcamento() {

		DLCopiaOrc dl = null;

		try {
			Integer codorc = txtCodOrc.getVlrInteger(); 
			if ( codorc.intValue() == 0 || lcCampos.getStatus() != ListaCampos.LCS_SELECT ) {
				Funcoes.mensagemInforma( this, "Selecione um or�amento cadastrado antes!" );
				return;
			}
			dl = new DLCopiaOrc( this );
			dl.setConexao( con );
			dl.setVisible( true );
			if ( !dl.OK ) {
				dl.dispose();
				return;
			}
			int[] vals = dl.getValores();
			codorc = getDaoorcamento().copiaOrcamento( codorc, vals );
			if (codorc!=null) {
				if ( Funcoes.mensagemConfirma( this, "Or�amento '" + codorc + "' criado com sucesso!\nGostaria de edita-lo agora?" ) == JOptionPane.OK_OPTION ) {
					txtCodOrc.setVlrInteger( codorc );
					lcCampos.carregaDados();
				}
			}
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao copiar o or�amento!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			if (dl!=null) {
				dl.dispose();
			}
		}
		
	}

	private void replicaOrcamento() {
		DLReplicaOrc dl = null;
		if ( txtCodOrc.getVlrInteger().intValue() == 0 || lcCampos.getStatus() != ListaCampos.LCS_SELECT ) {
			Funcoes.mensagemInforma( this, "Selecione um or�amento cadastrado antes!" );
			return;
		}
		dl = new DLReplicaOrc( this );
		dl.setConexao( con );
		dl.setVisible( true );
		if ( !dl.OK ) {
			dl.dispose();
			return;
		}
		int codemp = dl.getCodemp().intValue();
		dl.dispose();
		if (codemp!=0) {
			DBEngine engine = new DBEngine(con);
			List<Variable> listvar = new ArrayList<Variable>();
			listvar.add( engine.new Variable("#CODEMP_SOURCE#", String.valueOf(Aplicativo.iCodEmp) ));
			listvar.add( engine.new Variable("#CODEMP_TARGET#", String.valueOf(codemp) ));
			listvar.add( engine.new Variable("#CODORC#", txtCodOrc.getVlrString()) );
			String job = (String) oPrefs[Orcamento.PrefOrc.SQLREPLICAORC.ordinal()];
			if (engine.execute( job, listvar )) {
				Funcoes.mensagemInforma( this, "Replica��o executada com sucesso !" );
			}
		}
	}

	public void aprovar() {

		PreparedStatement ps = null;
		String sSQL = null;
		String status = "OC";
		try {

			if ( tab.getRowCount() <= 0 ) {
				Funcoes.mensagemInforma( this, "N�o ha nenhum �tem para ser aprovado" );
				return;
			}
			/* isso aqui � pra n�o deixar aprovar mais de uma vez... */
			if ( oldStatusOrc.equals( "OL" ) || oldStatusOrc.equals( "OV" ) ) {
				if ( oldStatusOrc.equals( "OV" ) ) {
					Funcoes.mensagemInforma( this, "Or�amento j� foi faturado." );
				}
				lcCampos.edit();
				txtStatusOrc.setVlrString( oldStatusOrc );
				lcCampos.post();
				lcCampos.carregaDados();
				return;
			}

			/* TIRADO O UPDATE EM EMITITORC PARA N�O PERMITIR A EMIS�O DO MESMO ITEM MAIS DE UMA VEZ */
			/*
			 * sSQL = "UPDATE VDITORCAMENTO SET ACEITEITORC='S', APROVITORC='S', VENCAUTORIZORC=?, " + "EMITITORC='N' " + "WHERE CODEMP=? AND CODFILIAL=? AND CODITORC=? AND CODORC=?";
			 */

			sSQL = "UPDATE VDITORCAMENTO SET ACEITEITORC='S', APROVITORC='S', VENCAUTORIZORC=? " + "WHERE CODEMP=? AND CODFILIAL=? AND CODITORC=? AND CODORC=?";

			try {
				ps = con.prepareStatement( sSQL );
				for ( int iLin = 0; iLin < tab.getRowCount(); iLin++ ) {
					ps.setDate( 1, Funcoes.dateToSQLDate( txtDtVencOrc.getVlrDate() ) );
					ps.setInt( 2, Aplicativo.iCodEmp );
					ps.setInt( 3, ListaCampos.getMasterFilial( "VDITORCAMENTO" ) );
					ps.setInt( 4, Integer.parseInt( tab.getValor( iLin, 0 ).toString() ) );
					ps.setInt( 5, txtCodOrc.getVlrInteger().intValue() );
					ps.execute();
				}
				con.commit();
				status = "OL";
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao atualizar a tabela ITORCAMENTO!\n" + err.getMessage(), true, con, err );
			}

			try {
				sSQL = "UPDATE VDORCAMENTO SET STATUSORC=? WHERE CODEMP=? AND CODFILIAL=? AND CODORC=?";
				ps = con.prepareStatement( sSQL );
				ps.setString( 1, status );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
				ps.setInt( 4, txtCodOrc.getVlrInteger().intValue() );
				ps.execute();
				con.commit();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao atualizar a tabela ORCAMENTO!\n" + err.getMessage(), true, con, err );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			sSQL = null;
		}
	}

	private synchronized void iniOrc() {

		txtCodCli.setVlrInteger( getCodCli() );
		lcCli.carregaDados();
		txtCodTpCli.setVlrInteger( getCodTipoCli() );
		lcTipoCli.carregaDados();
		txtCodPlanoPag.setVlrInteger( getPlanoPag() );
		lcPlanoPag.carregaDados();
		txtCodVend.setVlrInteger( getVendedor() );
		lcVend.carregaDados();
		lcProd.limpaCampos( true );
		lcProd2.limpaCampos( true );
		txtVlrAdicOrc.setVlrString( "" );
		txtVlrEdAdicOrc.setVlrString( "" );
		txtVlrEdDescOrc.setVlrString( "" );
		txtVlrLiqOrc.setVlrString( "" );
		txtVlrProdOrc.setVlrString( "" );
		txtDtOrc.setVlrDate( new Date() );
		try {
			txtDtVencOrc.setVlrDate( Orcamento.getVencimento( (Integer) oPrefs[ Orcamento.PrefOrc.DIASVENCORC.ordinal() ] ) );
		} catch (Exception err) {
			Funcoes.mensagemErro( null, "Erro carregando data de vencimento!\n"+err.getMessage() );
		}
		txtPrazoEntOrc.setVlrInteger( getPrazo() );
		tab.limpa();
		txtCodOrc.requestFocus();
	}

	private synchronized void iniItem() {

		lcDet.insert( true );
		txtCodItOrc.setVlrInteger( new Integer( 1 ) );
		if ( (Boolean) oPrefs[ Orcamento.PrefOrc.USAREFPROD.ordinal() ] ) {
			txtRefProd.requestFocus();
		} else {
			txtCodProd.requestFocus();
		}
	}

	public void exec( int iCodOrc ) {
		lcCampos.setState( ListaCampos.LCS_SELECT );
		txtCodOrc.setVlrString( String.valueOf( iCodOrc ) );
		lcCampos.carregaDados();
	}

	public void show() {
		super.show();
		lcCampos.insert( true );
		iniOrc();
	}

	private void focusCodprod() {
		if ( (Boolean) oPrefs[ Orcamento.PrefOrc.USAREFPROD.ordinal() ] ) {
			txtRefProd.requestFocus();
		} else {
			txtCodProd.requestFocus();
		}
	}

	private void imprimir( TYPE_PRINT bVisualizar ) {

		String sOrdem = "";

		DLROrcamento dlo = new DLROrcamento( oPrefs[ Orcamento.PrefOrc.ORDNOTA.ordinal() ].toString(), sModoNota );
		dlo.setVisible( true );
		if ( dlo.OK == false ) {
			dlo.dispose();
			return;
		}

		if ( dlo.getModo().equals( "G" ) ) {
			imprimeGrafico( bVisualizar);
		} else if ( dlo.getModo().equals( "T" ) ) {
			sOrdem = dlo.getOrdem();
			imprimeTexto( bVisualizar, sOrdem );
		}
	}

	public void imprimeGrafico( TYPE_PRINT bVisualizar) {
		LeiauteGR leiorc = null;
		String[] split;
		String[] classorc = new String[ResultClassOrc.values().length];
		classorc[ResultClassOrc.CLASSORC.ordinal()] = "";
		classorc[ResultClassOrc.DESCORC.ordinal()] = "";
		try {
			classorc = getDaoorcamento().getClassorc( oPrefs, ListaCampos.getMasterFilial( "ATTIPOCONV" ), txtCodTpConv.getVlrInteger() );
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, err.getMessage() );
		}
		try {
			if ( "".equals( classorc[ResultClassOrc.CLASSORC.ordinal()] ) || classorc[ResultClassOrc.CLASSORC.ordinal()].indexOf( "jasper" ) > -1 ) {
				HashMap<String, Object> hParam = new HashMap<String, Object>();
				hParam.put( "CODORC", txtCodOrc.getVlrInteger() );
				hParam.put( "CODEMP", Aplicativo.iCodEmp );
				hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
				hParam.put( "CODFILIALPF", ListaCampos.getMasterFilial( "SGPREFERE1" ) );
				hParam.put( "CIDADEDIAMESANO", Funcoes.getCidadeDiaMesAnoExtenso( Funcoes.getCidadeFilial( con ), new Date() ) );
				hParam.put( "CODEMPCL", Aplicativo.iCodEmp );
				hParam.put( "CODFILIALCL", ListaCampos.getMasterFilial( "VDCLIENTE" ) );
				hParam.put( "CODCLI", txtCodCli.getVlrInteger());
				hParam.put( "PESO", calcPeso());
				hParam.put( "USUARIO", StringFunctions.properCase( Aplicativo.getUsuario().getIdusu() ) );
				hParam.put( "SUBREPORT_DIR", "org/freedom/layout/orc/" ); 
				if ( "".equals( classorc[ResultClassOrc.CLASSORC.ordinal()] ) ) {
					classorc[ResultClassOrc.CLASSORC.ordinal()] = "ORC_PD.jasper";
				} else {
					String[] vClassOrc = classorc[ResultClassOrc.CLASSORC.ordinal()].split( "\\," );
					String[] vDescOrd = classorc[ResultClassOrc.DESCORC.ordinal()].split( "\\," );
					if ( vClassOrc.length > 1 ) { 
						FSelOrc fS = new FSelOrc();
						classorc[ResultClassOrc.CLASSORC.ordinal()] = fS.seleciona( vClassOrc, vDescOrd );
						if ( classorc[ResultClassOrc.CLASSORC.ordinal()] == null ) {
							return;
						}
					}
				}
				EmailBean mail = Aplicativo.getEmailBean();
				if (mail==null) {
					Funcoes.mensagemInforma( this, "N�o foram encontradas informa��es para envio de E-mail !" );
				} else {
					mail.setPara( daoemail.getEmailCli( txtCodCli.getVlrInteger(), con ) );			
				}
				FPrinterJob dlGr = new FPrinterJob( "layout/orc/" + classorc[ResultClassOrc.CLASSORC.ordinal()], null, null, this, hParam, con, mail, bImprimir, bImprimir );
				if ( bVisualizar==TYPE_PRINT.VIEW ) {
					dlGr.preview();
				} else {
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );
				}
			} else {
				leiorc = (LeiauteGR) Class.forName( "org.freedom.layout.orc." + classorc[ResultClassOrc.CLASSORC.ordinal()] ).newInstance();
				leiorc.setConexao( con );
				vParamOrc.clear();
				vParamOrc.addElement( txtCodOrc.getText() );
				vParamOrc.addElement( txtCodCli.getText() );
				leiorc.setParam( vParamOrc );

				if ( bVisualizar==TYPE_PRINT.VIEW ) {
					dl = new FPrinterJob( leiorc, this );
					dl.setVisible( true );
				} else {
					leiorc.imprimir( true );
				}
			}
		} catch ( Exception err ) {
			Funcoes.mensagemInforma( this, "N�o foi poss�vel carregar o layout de Or�amento!\n" + err.getMessage() );
			err.printStackTrace();
		}
	}

	private void imprimiGraficoPad( TYPE_PRINT bVisualizar, String layoutorc, Date dtorc ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODORC", txtCodOrc.getVlrInteger() );
		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
		hParam.put( "CODFILIALPF", ListaCampos.getMasterFilial( "SGPREFERE1" ) );
		hParam.put( "CIDADEDIAMESANO", Funcoes.getCidadeDiaMesAnoExtenso( Funcoes.getCidadeFilial( con ), new Date() ) );
		hParam.put( "MESEXTENSO", Funcoes.getMesExtenso(dtorc) );
		hParam.put( "SUBREPORT_DIR", "org/freedom/layout/orc/" );
		hParam.put( "PESO", calcPeso());

		EmailBean mail = Aplicativo.getEmailBean();
		mail.setPara( daoemail.getEmailCli( txtCodCli.getVlrInteger(), con ) );

		dlGr = new FPrinterJob( layoutorc, null, null, this, hParam, con, mail );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.preview();
		} else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de Or�amento!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void imprimeTexto( TYPE_PRINT bVisualizar, String ordem ) {

		ResultSet rs = null;
		String linhaFina = StringFunctions.replicate( "-", 135 );
		Vector<?> vDesc = null;
		Vector<?> vObs = null;
		ImprimeOS imp = new ImprimeOS( "", con );
		Integer codorc = txtCodOrc.getVlrInteger();
		int linPag = imp.verifLinPag() - 1;

		try {

			imp.montaCab();
			imp.setTitulo( "OR�AMENTO" );
			imp.limpaPags();
			rs = getDaoorcamento().getResultSetImprimeTexto( ordem, codorc );
			while ( rs.next() ) {
				vDesc = new Vector<Object>();
				if ( ( (Boolean) oPrefs[ Orcamento.PrefOrc.DESCCOMPPED.ordinal() ] ).booleanValue() ) {
					vDesc = Funcoes.quebraLinha( Funcoes.stringToVector( rs.getString( "ObsItOrc" ) == null ? rs.getString( "DescProd" ).trim() : rs.getString( "ObsItOrc" ).trim() ), 50 );
				} else {
					vDesc = Funcoes.quebraLinha( Funcoes.stringToVector( rs.getString( "DescProd" ).trim() ), 50 );
				}
				for ( int i = 0; i < vDesc.size(); i++ ) {
					if ( imp.pRow() == 0 ) {
						imp.impCab( 136, false );
						imp.say( 0, imp.comprimido() );
						imp.say( 1, "CLIENTE" );
						imp.say( 70, "OR�AMENTO: " + ( rs.getString( "CodOrc" ) != null ? rs.getString( "CodOrc" ).trim() : "" ) );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 1, ( rs.getString( "RazCli" ) != null ? rs.getString( "RazCli" ).trim() : "" ) + " - " + ( rs.getString( "CodCli" ) != null ? rs.getString( "CodCli" ).trim() : "" ) );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 1, rs.getString( "CpfCli" ) != null ? "CPF    : " + Funcoes.setMascara( rs.getString( "CpfCli" ), "###.###.###-##" ) : "CNPJ   : " + Funcoes.setMascara( rs.getString( "CnpjCli" ), "##.###.###/####-##" ) );
						imp.say( 70, "CONTATO: " + ( rs.getString( "ContCli" ) != null ? rs.getString( "ContCli" ).trim() : "" ) );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 1, rs.getString( "RgCli" ) != null ? "R.G.   : " + rs.getString( "RgCli" ) : "I.E.   : " + rs.getString( "InscCli" ) );// IE cliente
						imp.say( 70, ( rs.getString( "EndCli" ) != null ? rs.getString( "EndCli" ).trim() : "" ) + ( rs.getString( "NumCli" ) != null ? "  N�: " + rs.getString( "NumCli" ).trim() : "" ) );// rua e n�mero do cliente
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 1, "SITE   : " + ( rs.getString( "SiteCli" ) != null ? rs.getString( "SiteCli" ).trim() : "" ) );
						imp.say( 70, ( rs.getString( "BairCli" ) != null ? rs.getString( "BairCli" ).trim() : "" ) + ( rs.getString( "CidCli" ) != null ? " - " + rs.getString( "CidCli" ).trim() : "" ) + ( rs.getString( "UFCli" ) != null ? " - " + rs.getString( "UFCli" ).trim() : "" )
								+ ( rs.getString( "CepCli" ) != null ? " - " + rs.getString( "CepCli" ).trim() : "" ) );// complemento do endere�o do cliente
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 1, "E-MAIl : " + ( rs.getString( "EmailCli" ) != null ? rs.getString( "EmailCli" ).trim() : "" ) );
						imp.say( 70, "TEL: " + ( rs.getString( "DDDCli" ) != null ? "(" + rs.getString( "DDDCli" ) + ")" : "" ) + ( rs.getString( "FoneCli" ) != null ? Funcoes.setMascara( rs.getString( "FoneCli" ).trim(), "####-####" ) : "" ) + " - FAX:"
								+ ( rs.getString( "FaxCli" ) != null ? Funcoes.setMascara( rs.getString( "FaxCli" ), "####-####" ) : "" ) );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, linhaFina );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 55, "DADO(S) DO(S) PRODUTO(S)" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, linhaFina );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 1, "IT" );
						imp.say( 6, "|   C�DIGO" );
						imp.say( 19, "|                     DESCRI��O" );
						imp.say( 72, "| UN" );
						imp.say( 77, "|    QUANT." );
						imp.say( 91, "|   V.UNIT." );
						imp.say( 105, "|   V.DESC." );
						imp.say( 119, "|     V.TOTAL" );
					}
					imp.pulaLinha( 1, imp.comprimido() );
					if ( i == 0 ) {
						imp.say( 1, rs.getString( "CodItOrc" ).trim() );
						if ( ( (Boolean) oPrefs[ Orcamento.PrefOrc.USAREFPROD.ordinal() ] ).booleanValue() ) {
							imp.say( 7, rs.getString( "RefProd" ).trim() );
						} else {
							imp.say( 7, rs.getString( "CodProd" ).trim() );
						}
					}
					imp.say( 20, (String) vDesc.elementAt( i ).toString() );
					if ( i == 0 ) {
						imp.say( 74, Funcoes.copy( rs.getString( "CodUnid" ).trim(), 2 ) );
						imp.say( 78, Funcoes.strDecimalToStrCurrency( 12, 2, rs.getString( "QtdItOrc" ) ) );
						imp.say( 92, Funcoes.strDecimalToStrCurrency( 12, 2, rs.getString( "PrecoItOrc" ) ) );
						imp.say( 106, Funcoes.strDecimalToStrCurrency( 12, 2, rs.getString( "VlrDescItOrc" ) ) );
						imp.say( 120, Funcoes.strDecimalToStrCurrency( 15, 2, rs.getString( "VlrLiqItOrc" ) ) );
					}
				}
			}
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, linhaFina );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 86, "|    TOTAL PRODUTOS: " + rs.getString( "VlrLiqOrc" ) );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, linhaFina );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 55, "INFORMA��ES COMPLEMENTARES" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, linhaFina );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "PAGAMENTO.........:    " + rs.getString( "CODPLANOPAG" ) + " - " + rs.getString( "DESCPLANOPAG" ) );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "PRAZO DE ENTREGA..:    " + rs.getString( "PrazoEntOrc" ) );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, linhaFina );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 62, "OBSERVAC�O" );
			imp.pulaLinha( 1, imp.comprimido() );
			vObs = Funcoes.quebraLinha( Funcoes.stringToVector( rs.getString( "ObsOrc" ) ), 115 );
			for ( int i = 0; i < vObs.size(); i++ ) {
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 20, vObs.elementAt( i ).toString() );
				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, linhaFina );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 5, StringFunctions.replicate( "-", 40 ) );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 5, rs.getString( "NomeVend" ) );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 5, rs.getString( 37 ) );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 5, rs.getString( "EmailVend" ) );
			imp.eject();
			imp.fechaGravacao();
			rs.close();
			con.commit();
		} catch ( SQLException err ) {
			try {
				con.rollback();
			} catch (SQLException errroll) {
				errroll.printStackTrace();
			}
			Funcoes.mensagemErro( this, "Erro ao consultar a tabela de Venda!" + err.getMessage(), true, con, err );
		} finally {
			vDesc = null;
			vObs = null;
			rs = null;
		}
		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			imp.preview( this );
		} else {
			imp.print();
		}
	}

	public void focusGained( FocusEvent fevt ) { }

	private void calcImpostos( boolean buscabase ) {
		setCalcImpostos( buscabase );
		getCalcImpostos();
	}

	public void focusLost( FocusEvent fevt ) {
		Boolean comissaoDesconto = (Boolean) oPrefs[ Orcamento.PrefOrc.COMISSAODESCONTO.ordinal() ];

		if ( (fevt.getSource() == txtVlrDescItOrc || fevt.getSource() == txtPercDescItOrc ) && txtCodProd.getVlrInteger()>0 ) {

			if(txtPercDescItOrc.getText().trim().length() < 1 && !comissaoDesconto){
				txtVlrDescItOrc.setAtivo( true );
			}

			calcDescIt();
			calcVlrProd();
			calcTot();
			carregaComisIt();

			if ( lcDet.getStatus() == ListaCampos.LCS_INSERT && ( !(Boolean) oPrefs[ Orcamento.PrefOrc.HABVLRTOTITORC.ordinal() ] ) ) {
				lcDet.post();
				lcDet.limpaCampos( true );
				lcDet.setState( ListaCampos.LCS_NONE );
				lcDet.edit();
				focusCodprod();
			} 
			else if ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
				lcDet.post();
				txtCodItOrc.requestFocus();
			}

		} else if ( (( fevt.getSource() == txtQtdItOrc ) || ( fevt.getSource() == txtPrecoItOrc ) || 
				( fevt.getSource() == txtVlrDescItOrc ) || ( fevt.getSource() == txtPercComisItOrc )) && txtCodProd.getVlrInteger()>0 ) {
			calcDescIt();
			calcVlrProd();
			calcTot();
			calcComis();

			carregaComisIt();

		} else if ( ( fevt.getSource() == txtVlrLiqItOrc ) && txtCodProd.getVlrInteger()>0 ) {

			if ( ( lcDet.getStatus() == ListaCampos.LCS_INSERT || lcDet.getStatus() == ListaCampos.LCS_EDIT ) && 
					( (Boolean) oPrefs[ Orcamento.PrefOrc.HABVLRTOTITORC.ordinal() ] ) ) {

				calcDesconto();

				lcDet.post();
				lcDet.limpaCampos( true );
				lcDet.setState( ListaCampos.LCS_NONE );
				lcDet.edit();

				focusCodprod();
			}
		}
	}

	private void calcDesconto() {
		BigDecimal vlrdesconto = null;
		BigDecimal vlrdigitado = null;
		BigDecimal preco = null;

		try {
			vlrdigitado = txtVlrLiqItOrc.getVlrBigDecimal();
			preco = txtPrecoItOrc.getVlrBigDecimal().multiply( txtQtdItOrc.getVlrBigDecimal() );

			if ( preco == null || preco.floatValue() <= 0 ) {

				txtPrecoItOrc.setVlrBigDecimal( vlrdigitado );

			} else if ( vlrdigitado.compareTo( preco ) < 0 ) {

				vlrdesconto = preco.subtract( vlrdigitado );

				txtVlrDescItOrc.setVlrBigDecimal( vlrdesconto );
				calcPercDescIt();
				calcVlrProd();
				calcTot();

			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void carregaComisIt(){
		BigDecimal vlrComissao = new BigDecimal(0);
		try {
			BigDecimal vlrcomissao = getDaoorcamento().carregaComisIt( oPrefs, ListaCampos.getMasterFilial( "VDREGCOMISDESC" ), txtPercDescItOrc.getVlrBigDecimal() );
			calcComisIt();
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, err.getMessage() );
		}
	}

	public void permitirImpressao(boolean valor) {
		bImprimir = valor;
		btImp.setEnabled( bImprimir );
	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_CONTROL ) {
			bCtrl = true;
		} else if ( kevt.getKeyCode() == KeyEvent.VK_O ) {
			if ( bCtrl ) {
				btObs.doClick();
			}
		} else if ( kevt.getKeyCode() == KeyEvent.VK_F4 ) {
			btFechaOrc.doClick();
		} else if ( kevt.getKeyCode() == KeyEvent.VK_F3 ) {
			if ( kevt.getSource() == txtPercDescItOrc || kevt.getSource() == txtVlrDescItOrc ) {
				mostraTelaDescont();
			}
		}else if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {

			if ( kevt.getSource() == txtCodPlanoPag ) {
				if( (lcCampos.getStatus() == ListaCampos.LCS_INSERT ) && 	
						( "N".equals( oPrefs[ Orcamento.PrefOrc.ABATRANSP.ordinal() ].toString() ) ) ) {
					//lcCampos.post();

					tpnCab.setSelectedIndex( 1 );
					pinPrevisao.doLayout();
					txtDtPreventOrc.requestFocus();
				} else {
					// Como este � o
					// ultimo campo da
					// aba de or�amento
					// ent�o abre a tab
					// transportadora.
					tpnCab.setSelectedIndex( 1 );
					pinCabTransp.doLayout();
					txtCodTran.requestFocus();
				}
			}


			else if ( ( kevt.getSource() == txtCodTran && lcCampos.getStatus() == ListaCampos.LCS_INSERT ) && 
					( "S".equals( oPrefs[ Orcamento.PrefOrc.ABATRANSP.ordinal() ].toString() ) ) ) {
				//lcCampos.post();

				tpnCab.setSelectedIndex( 2 );
				pinPrevisao.doLayout();
				txtDtPreventOrc.requestFocus();

			}
			else if( kevt.getSource() == txtHPreventOrc){
				lcCampos.post();
			}

			else if ( kevt.getSource() == txtRefProd ) {
				lcDet.edit();
			}
		}	 

		if ( kevt.getKeyCode() == KeyEvent.VK_F12 && ( ( "S".equals( permusu.get( "VISUALIZALUCR" ) ) && ( (Boolean) oPrefs[ Orcamento.PrefOrc.VISUALIZALUCR.ordinal() ] ) ) ) ) {
			DLAltFatLucro dl = new DLAltFatLucro( this, fatluc );
			dl.setVisible( true );
			if ( dl.OK ) {
				fatluc = dl.getValor();
				dl.dispose();
			}

			dl.dispose();
			atualizaLucratividade();
		} 

		super.keyPressed( kevt );
	}

	public void keyReleased( KeyEvent kevt ) {
		if ( kevt.getKeyCode() == KeyEvent.VK_CONTROL ) {
			bCtrl = false;
		}

		super.keyReleased( kevt );
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btFechaOrc ) {
			fechaOrc();
		} else if ( evt.getSource() == btPrevimp ) {
			imprimir( TYPE_PRINT.VIEW );
		} else if ( evt.getSource() == btImp ) {
			imprimir( TYPE_PRINT.PRINT);
		} else if ( evt.getSource() == btOrc ) {
			imprimiGraficoPad( TYPE_PRINT.VIEW, classorc[ResultBuscaClassOrc.CLASSORCPD.ordinal()], txtDtOrc.getVlrDate() );
		} else if ( evt.getSource() == btOrcTst ) {
			if (classorc[ResultBuscaClassOrc.CLASSORCLAUDOSUS.ordinal()]==null || "".equals(classorc[ResultBuscaClassOrc.CLASSORCLAUDOSUS.ordinal()])) {
				imprimeLaudoSusJava();
			} else {
				imprimiGraficoPad( TYPE_PRINT.VIEW, classorc[ResultBuscaClassOrc.CLASSORCLAUDOSUS.ordinal()], txtDtOrc.getVlrDate() );
			}
		} else if ( evt.getSource() == btOrcTst2 ) {
			if (classorc[ResultBuscaClassOrc.CLASSORCCTALUGUEL.ordinal()]==null) {
				imprimeContratoAluguelJava();
			} else {
				imprimiGraficoPad( TYPE_PRINT.VIEW, classorc[ResultBuscaClassOrc.CLASSORCCTALUGUEL.ordinal()], txtDtOrc.getVlrDate() );
			}
		} else if ( evt.getSource() == btObs ) {
			mostraObs( "VDORCAMENTO", txtCodOrc.getVlrInteger().intValue() );
		} else if ( evt.getSource() == btCopiaOrcamento ) {
			copiaOrcamento();
		} else if ( evt.getSource() == btReplicaOrcamento ) {
			replicaOrcamento();
		} else if ( evt.getSource() == btCriaLancamento) {
			novoModelo();
		}

		super.actionPerformed( evt );
	}

	private void imprimeContratoAluguelJava() {
		LeiauteGR leiOrc = null;
		try {
			leiOrc = (LeiauteGR) Class.forName( "org.freedom.layout.orc." + "ContratoAluguelApr" ).newInstance();
			leiOrc.setConexao( con );
			vParamOrc.clear();
			vParamOrc.addElement( txtCodOrc.getText() );
			vParamOrc.addElement( txtCodConv.getText() );
			leiOrc.setParam( vParamOrc );

			dl = new FPrinterJob( leiOrc, this );
			dl.setVisible( true );
		} catch ( Exception err ) {
			Funcoes.mensagemInforma( this, "N�o foi poss�vel carregar o layout de Contrato de loca��o!\n" + err.getMessage() );
			err.printStackTrace();
		}
	}
	
	private void imprimeLaudoSusJava() {
		LeiauteGR leiOrc = null;
		try {
			leiOrc = (LeiauteGR) Class.forName( "org.freedom.layout.orc." + "LaudoAprSusFisio" ).newInstance();
			leiOrc.setConexao( con );
			vParamOrc.clear();
			vParamOrc.addElement( txtCodOrc.getText() );
			vParamOrc.addElement( txtCodConv.getText() );
			leiOrc.setParam( vParamOrc );

			dl = new FPrinterJob( leiOrc, this );
			dl.setVisible( true );
		} catch ( Exception err ) {
			Funcoes.mensagemInforma( this, "N�o foi poss�vel carregar o leiaute de Or�amento Fisio.!\n" + err.getMessage() );
			err.printStackTrace();
		}
	}
	
	public void beforeCarrega( CarregaEvent cevt ) {
		if ( cevt.getListaCampos() == lcProd2 ) {
			lcProd.edit();
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {
		if ( cevt.getListaCampos() == lcDet ) {

			lcOrc2.carregaDados();// Carrega os Totais
			bloqueiaComissao( (Boolean) oPrefs[ Orcamento.PrefOrc.BLOQCOMISSORC.ordinal() ] );

			if ( ( "S".equals( permusu.get( "VISUALIZALUCR" ) ) && ( (Boolean) oPrefs[ Orcamento.PrefOrc.VISUALIZALUCR.ordinal() ] ) ) ) {

				if ( lcDet.getStatus() != ListaCampos.LCS_INSERT ) {
					lcPrevTrib.carregaDados(); // Carrega previsionamento de tributos
				}

				atualizaLucratividade();

			}

			cbStatusItOrc.setVlrString( txtStatusItOrc.getVlrString() );
			cbSitProdItOrc.setVlrString( txtSitProdItOrc.getVlrString() );
			cbFatItOrc.setVlrString( txtFatItOrc.getVlrString() );

		} 
		else if ( ( cevt.getListaCampos() == lcProd ) || ( cevt.getListaCampos() == lcProd2 ) ) {
			if ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) {
				if ( ( (Boolean) oPrefs[ Orcamento.PrefOrc.USALOTEORC.ordinal() ] ).booleanValue() && txtCLoteProd.getVlrString().equals( "S" ) ) {
					getLote();
					txtCodLote.setAtivo( true );
				} else {
					txtCodLote.setAtivo( false );
				}

				calcVlrItem( null, false );
				carregaPercComis();
			} else if ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) {
				carregaPercComis();
			}

			lcAlmox.carregaDados();
		} else if ( cevt.getListaCampos() == lcCampos ) {
			String s = txtCodOrc.getVlrString();
			lcOrc2.carregaDados();// Carrega os Totais
			txtCodOrc.setVlrString( s );
			s = null;
			carregaStatus();
			carregaPedidos();
			if (daoatend != null && daoatend.getCodatend_atual()!=null) {
				carregaTabAtendo();
			}
			if ( ( "S".equals( permusu.get( "VISUALIZALUCR" ) ) && ( (Boolean) oPrefs[ Orcamento.PrefOrc.VISUALIZALUCR.ordinal() ] ) ) ) {
				lcPrevTrib.carregaDados(); // Carrega previsionamento de tributos
				atualizaLucratividade();
			}
			if( (!((Boolean) oPrefs[Orcamento.PrefOrc.PERMITIMPORCANTAP.ordinal()]).booleanValue()) 
					&& (Orcamento.STATUS_ABERTO.getValue().equals(txtStatusOrc.getVlrString()) || Orcamento.STATUS_PENDENTE.getValue().equals(txtStatusOrc.getVlrString()) 
							|| Orcamento.STATUS_COMPLETO.getValue().equals(txtStatusOrc.getVlrString()))) {
				permitirImpressao( false );
			} else {
				permitirImpressao(true);
			}
			if ("".equals( txtStatusOrc.getVlrString() ))	{
				btCriaLancamento.setEnabled( false );
			} else {
				btCriaLancamento.setEnabled( true );
			}
		} else if ( cevt.getListaCampos() == lcCli ) {
			if ( ( (Boolean) oPrefs[ Orcamento.PrefOrc.OBSCLIVEND.ordinal() ] ).booleanValue() ) {
				if ( iCodCliAnt != txtCodCli.getVlrInteger().intValue() ) {
					iCodCliAnt = txtCodCli.getVlrInteger().intValue();
					mostraObsCli( iCodCliAnt, new Point( this.getX(), this.getY() + pinCab.getHeight() + pnCab.getHeight() + 10 ), new Dimension( spTab.getWidth(), 150 ) );
				}
			}

			if ( ( (Boolean) oPrefs[ Orcamento.PrefOrc.RECALCPCORC.ordinal() ] ).booleanValue() ) {
				setReCalcPreco( true );
			}
			txtCodTpCli.setVlrInteger( getCodTipoCli() );
			lcTipoCli.carregaDados();

			if(lcCampos.getStatus() == ListaCampos.LCS_INSERT) {
				if ( "S".equals( oPrefs[ Orcamento.PrefOrc.ABATRANSP.ordinal() ].toString() ) ) {
					txtCodTran.setVlrInteger( txtCodTranCli.getVlrInteger() );
				}
			}

		} else if ( cevt.getListaCampos() == lcPlanoPag ) {
			if ( ( (Boolean) oPrefs[ Orcamento.PrefOrc.RECALCPCORC.ordinal() ] ).booleanValue() ) {
				setReCalcPreco( true );
			}
		}
	}

	public void beforePost( PostEvent evt ) {

		if ( evt.getListaCampos() == lcCampos ) {

			if (cancelaEvento()) {
				Funcoes.mensagemErro( this, "Or�amento Bloqueado!" );
				evt.cancela();
				return;
			}

			if ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) {
				if ( ( (Boolean) oPrefs[ Orcamento.PrefOrc.USAORCSEQ.ordinal() ] ).booleanValue() ) {
					txtCodOrc.setVlrInteger( writePK( "VDORCAMENTO" ) );
				}
				txtStatusOrc.setVlrString( "*" );
			}
			if ( podeReCalcPreco() ) {
				calcVlrItem( "VDORCAMENTO", true );
			}
			txtCodClComiss.setVlrInteger( getClcomiss( txtCodVend.getVlrInteger().intValue() ) );
			txtCodTipoMov.setVlrInteger( (Integer) oPrefs[ Orcamento.PrefOrc.CODTIPOMOV2.ordinal() ] );
			// lcTipoMov.carregaDados();

		} else if ( evt.getListaCampos() == lcDet ) {
			if ( ( lcDet.getStatus() == ListaCampos.LCS_INSERT ) || ( lcDet.getStatus() == ListaCampos.LCS_EDIT ) ) {
				if (cancelaEvento()) {
					Funcoes.mensagemErro( this, "Or�amento Bloqueado!" );
					evt.cancela();
					return;
				}

				if ( txtQtdItOrc.getVlrBigDecimal().floatValue() <= 0 ) {
					Funcoes.mensagemInforma( this, "Quantidade invalida!" );
					evt.cancela();
					return;
				}
				if ( txtPrecoItOrc.getVlrBigDecimal().floatValue() < 0 ) {
					Funcoes.mensagemInforma( this, "Pre�o invalido!" );
					evt.cancela();
					return;
				}
				if ( !testaLucro() ) {
					Funcoes.mensagemInforma( this, "N�o � permitido a venda deste produto abaixo do custo!!!" );
					evt.cancela();
				}
			}
		}
		setReCalcPreco( false );
	}

	public void afterPost( PostEvent pevt ) {
		lcOrc2.carregaDados(); // Carrega os Totais

		if ( ( "S".equals( permusu.get( "VISUALIZALUCR" ) ) && ( (Boolean) oPrefs[ Orcamento.PrefOrc.VISUALIZALUCR.ordinal() ] ) ) ) {
			lcPrevTrib.carregaDados(); // Carrega previsionamento de tributos
		}

		if ( pevt.getListaCampos() == lcCampos ) {
			if ( lcDet.getStatus() == ListaCampos.LCS_NONE ) {
				iniItem();
			}
		}
	}

	public void beforeDelete( DeleteEvent devt ) { 

		if (devt.getListaCampos() == lcCampos) {
			if (cancelaEvento()) {
				Funcoes.mensagemErro( this, "Or�amento Bloqueado!" );
				devt.cancela();
				return;
			}
		}

		if (devt.getListaCampos() == lcDet) {
			if (cancelaEvento()) {
				Funcoes.mensagemErro( this, "Or�amento Bloqueado!" );
				devt.cancela();
				return;
			}
		}
	}

	public void afterDelete( DeleteEvent devt ) {
		if ( devt.getListaCampos() == lcDet ) {
			lcOrc2.carregaDados();
			if ( ( "S".equals( permusu.get( "VISUALIZALUCR" ) ) && ( (Boolean) oPrefs[ Orcamento.PrefOrc.VISUALIZALUCR.ordinal() ] ) ) ) {
				lcPrevTrib.carregaDados(); // Carrega previsionamento de tributos
				atualizaLucratividade();
			}
		}
	}

	public boolean cancelaEvento() {
		boolean cancelar = false;

		if (((Boolean) oPrefs[Orcamento.PrefOrc.BLOQEDITORCAPOSAP.ordinal()]) && ( 
				(!"".equals( txtStatusOrc.getVlrString()) && !Orcamento.STATUS_ABERTO.getValue().equals(txtStatusOrc.getVlrString()) && !Orcamento.STATUS_PENDENTE.getValue().equals(txtStatusOrc.getVlrString()) 
						&& !Orcamento.STATUS_COMPLETO.getValue().equals(txtStatusOrc.getVlrString())))) {
			cancelar = true;

		}

		return cancelar;
	}


	public void beforeInsert( InsertEvent e ) {
		if ( e.getListaCampos() == lcCampos ) {
			lbStatus.setVisible( false );
			tabPedidos.limpa();
		}

		if ( e.getListaCampos() == lcDet ) {
			if (cancelaEvento()) {
				Funcoes.mensagemErro( this, "Or�amento Bloqueado!" );
				e.cancela();
				return;
			}
		}
	}

	public void afterInsert( InsertEvent e ) {

		if ( e.getListaCampos() == lcCampos ) {
			btCriaLancamento.setEnabled( false );
			iniOrc();
			fatluc = new BigDecimal(1);
		} else if ( e.getListaCampos() == lcDet ) {
			focusCodprod();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		setDaoorcamento( new DAOOrcamento( cn, Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDORCAMENTO" ) ) );
		daoemail = new DAOEmail( cn, Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "TKEMAIL" ) ); 
		try {
			daoatend = new DAOAtendimento( cn, Aplicativo.iCodEmp, ListaCampos.getMasterFilial("ATATENDENTE")  );
			daoatend.setPrefs( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "SGPREFERE3" ));
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro carregando prefer�ncias !\b" + e.getMessage() );
		}
		lcProd.setConexao( cn );
		lcProd2.setConexao( cn );
		lcLote.setConexao( cn );
		lcOrc2.setConexao( cn );
		lcPrevTrib.setConexao( cn );
		lcCli.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcVend.setConexao( cn );
		lcTipoCli.setConexao( cn );
		lcAlmox.setConexao( cn );
		lcClComiss.setConexao( cn );
		lcConv.setConexao( cn );
		lcTipoConv.setConexao( cn );
		lcEnc.setConexao( cn );
		lcAtend.setConexao( cn );
		lcTran.setConexao( cn );
		lcTipoMov.setConexao( cn );
		permusu = getPermissaoUsu();
		try {
			oPrefs = getDaoorcamento().getPrefere(ListaCampos.getMasterFilial( "SGPREFERE1" )
				, ListaCampos.getMasterFilial( "SGESTACAOIMP" ), Aplicativo.getInstance().getCodest()); // Carrega as prefer�ncias
		} catch (Exception err) {
			Funcoes.mensagemErro( null, err.getMessage() );
			dispose();
			return;
		}
		montaListaCampos();
		montaOrcamento();
		montaDetalhe();

		if ( ( (Boolean) oPrefs[ Orcamento.PrefOrc.USABUSCAGENPROD.ordinal() ] ).booleanValue() ) {
			if ( ( (Boolean) oPrefs[ Orcamento.PrefOrc.USAREFPROD.ordinal() ] ).booleanValue() ) {
				txtRefProd.setBuscaGenProd( new DLCodProd( cn, null, null ) );
			} else {
				txtCodProd.setBuscaGenProd( new DLCodProd( cn, null, null ) );
			}
		}
		if ( ( (Boolean) oPrefs[ Orcamento.PrefOrc.BLOQPRECOORC.ordinal() ] ).booleanValue() ) {
			txtPrecoItOrc.setEditable( false );
		} 
		
		// Busca classes para impress�o de or�amentos
		try {
			classorc = getDaoorcamento().buscaClassOrc(ListaCampos.getMasterFilial( "SGPREFERE1" ));
		} catch (Exception err) {
			Funcoes.mensagemErro( null, err.getMessage() );
		}
		
	}

	
	private void atualizaLucratividade() {

		if ( ( "S".equals( permusu.get( "VISUALIZALUCR" ) ) ) && (Boolean) ( oPrefs[ Orcamento.PrefOrc.VISUALIZALUCR.ordinal() ] ) ) {

			Lucratividade luc = new Lucratividade( txtCodOrc.getVlrInteger(), "O", txtCodItOrc.getVlrInteger(), fatluc, oPrefs[ Orcamento.PrefOrc.TIPOCUSTO.ordinal() ].toString(), con, "S".equals(txtDescIpi.getVlrString()) );

			/****************************
			 * Atualizando painel geral
			 ****************************/

			txtTotFat.setVlrBigDecimal( luc.getTotfat() == null ? new BigDecimal( 0 ) : luc.getTotfat() );
			txtTotCusto.setVlrBigDecimal( luc.getTotcusto() == null ? new BigDecimal( 0 ) : luc.getTotcusto() );
			txtTotLucro.setVlrBigDecimal( luc.getTotlucro() == null ? new BigDecimal( 0 ) : luc.getTotlucro() );

			txtTotCustoUC.setVlrBigDecimal( luc.getVlrcustoinfo() == null ? new BigDecimal( 0 ) : luc.getVlrcustoinfo() );
			txtTotComiss.setVlrBigDecimal( luc.getVlrcomis() == null ? new BigDecimal( 0 ) : luc.getVlrcomis() );
			txtTotPIS.setVlrBigDecimal( luc.getVlrpis() == null ? new BigDecimal( 0 ) : luc.getVlrpis() );
			txtTotCofins.setVlrBigDecimal( luc.getVlrcofins() == null ? new BigDecimal( 0 ) : luc.getVlrcofins() );
			txtTotCSOCIAL.setVlrBigDecimal( luc.getVlrcsocial() == null ? new BigDecimal( 0 ) : luc.getVlrcsocial() );
			txtTotIR.setVlrBigDecimal( luc.getVlrir() == null ? new BigDecimal( 0 ) : luc.getVlrir() );
			txtTotIPI.setVlrBigDecimal( luc.getVlripi() == null ? new BigDecimal( 0 ) : luc.getVlripi() );
			txtTotICMS.setVlrBigDecimal( luc.getVlricms() == null ? new BigDecimal( 0 ) : luc.getVlricms() );

			pbLucrTotal.setValue( luc.getPerclucrvenda().toBigInteger().intValue() );

			// Lucro menor que 20% (Vermelho)
			if ( luc.getPerclucrvenda().compareTo( new BigDecimal( 20.00 ) ) <= 0 ) {
				pbLucrTotal.setForeground( new Color( 255, 0, 0 ) );

			}
			// Lucro maior que 20% menor que 30% (Laranja)
			else if ( luc.getPerclucrvenda().compareTo( new BigDecimal( 20.00 ) ) > 0 && luc.getPerclucrvenda().compareTo( new BigDecimal( 30.00 ) ) <= 0 ) {
				pbLucrTotal.setForeground( new Color( 226, 161, 35 ) );
			}
			// Lucro maior que 30% e menor que 50% (Azul)
			else if ( luc.getPerclucrvenda().compareTo( new BigDecimal( 30.00 ) ) > 0 && luc.getPerclucrvenda().compareTo( new BigDecimal( 50.00 ) ) <= 0 ) {
				pbLucrTotal.setForeground( new Color( 0, 0, 255 ) );
			}
			// Lucro maior que 50% (Verde)
			else {
				pbLucrTotal.setForeground( new Color( 0, 143, 20 ) );
			}

			/****************************
			 * Atulizando painel item
			 ****************************/

			txtItemFat.setVlrBigDecimal( luc.getItemfat() == null ? new BigDecimal( 0 ) : luc.getItemfat() );
			txtItemCusto.setVlrBigDecimal( luc.getItemcusto() == null ? new BigDecimal( 0 ) : luc.getItemcusto() );
			txtItemLucro.setVlrBigDecimal( luc.getItemlucro() == null ? new BigDecimal( 0 ) : luc.getItemlucro() );

			txtTotCustoUCIt.setVlrBigDecimal( luc.getVlrcustoinfoit() == null ? new BigDecimal( 0 ) : luc.getVlrcustoinfoit() );
			txtTotComissIt.setVlrBigDecimal( luc.getVlrcomisit() == null ? new BigDecimal( 0 ) : luc.getVlrcomisit() );
			txtTotPISIt.setVlrBigDecimal( luc.getVlrpisit() == null ? new BigDecimal( 0 ) : luc.getVlrpisit() );
			txtTotCofinsIt.setVlrBigDecimal( luc.getVlrcofinsit() == null ? new BigDecimal( 0 ) : luc.getVlrcofinsit() );
			txtTotCSOCIALIt.setVlrBigDecimal( luc.getVlrcsocialit() == null ? new BigDecimal( 0 ) : luc.getVlrcsocialit() );
			txtTotIRIt.setVlrBigDecimal( luc.getVlririt() == null ? new BigDecimal( 0 ) : luc.getVlririt() );
			txtTotIPIIt.setVlrBigDecimal( luc.getVlripiit() == null ? new BigDecimal( 0 ) : luc.getVlripiit() );
			txtTotICMSIt.setVlrBigDecimal( luc.getVlricmsit() == null ? new BigDecimal( 0 ) : luc.getVlricmsit() );

			pbLucrItem.setValue( luc.getPerclucrit().toBigInteger().intValue() );
			pnLucrItem.setBorder( BorderFactory.createTitledBorder( txtCodItOrc.getVlrString() + "-" + txtDescProd.getVlrString().trim() ) );
			pnDetCustosItem.setBorder( BorderFactory.createTitledBorder( txtCodItOrc.getVlrString() + "-" + txtDescProd.getVlrString().trim() ) );

			// Lucro menor que 20% (Vermelho)
			if ( luc.getPerclucrit().compareTo( new BigDecimal( 20.00 ) ) <= 0 ) {
				pbLucrItem.setForeground( new Color( 255, 0, 0 ) );

			}
			// Lucro maior que 20% menor que 30% (Laranja)
			else if ( luc.getPerclucrit().compareTo( new BigDecimal( 20.00 ) ) > 0 && luc.getPerclucrit().compareTo( new BigDecimal( 30.00 ) ) <= 0 ) {
				pbLucrItem.setForeground( new Color( 226, 161, 35 ) );
			}
			// Lucro maior que 30% e menor que 50% (Azul)
			else if ( luc.getPerclucrit().compareTo( new BigDecimal( 30.00 ) ) > 0 && luc.getPerclucrit().compareTo( new BigDecimal( 50.00 ) ) <= 0 ) {
				pbLucrItem.setForeground( new Color( 0, 0, 255 ) );
			}
			// Lucro maior que 50% (Verde)
			else {
				pbLucrItem.setForeground( new Color( 0, 143, 20 ) );
			}

		}

	}

	private void adicPainelLucr() {

		try {

			/**************************
			 * Aba Lucratividade
			 **************************/

			tpnCab.addTab( "Lucratividade", pinCabLucratividade );
			setPainel( pinCabLucratividade );

			/******************************
			 * Painel Geral - lucratividade
			 *******************************/

			pnLucrGeral.setBorder( BorderFactory.createTitledBorder( "Lucratividade Geral" ) );
			adic( pnLucrGeral, 4, 0, 230, 92 );
			setPainel( pnLucrGeral );
			pbLucrTotal.setStringPainted( true );

			adic( pbLucrTotal, 2, 2, 215, 20 );

			adic( new JLabelPad( "Faturado" ), 2, 25, 75, 20 );
			adic( txtTotFat, 2, 45, 70, 20 );

			adic( new JLabelPad( "Custo" ), 75, 25, 75, 20 );
			adic( txtTotCusto, 75, 45, 70, 20 );

			adic( new JLabelPad( "Lucro" ), 153, 25, 75, 20 );
			adic( txtTotLucro, 148, 45, 70, 20 );

			/****************************
			 * Painel Item - lucratividade
			 *****************************/

			setPainel( pinCabLucratividade );

			pnLucrItem.setBorder( BorderFactory.createTitledBorder( "Lucratividade Item" ) );
			adic( pnLucrItem, 240, 0, 230, 92 );

			setPainel( pnLucrItem );
			pbLucrItem.setStringPainted( true );

			adic( pbLucrItem, 2, 2, 215, 20 );

			adic( new JLabelPad( "Faturado" ), 2, 25, 75, 20 );
			adic( txtItemFat, 2, 45, 70, 20 );

			adic( new JLabelPad( "Custo" ), 75, 25, 75, 20 );
			adic( txtItemCusto, 75, 45, 70, 20 );

			adic( new JLabelPad( "Lucro" ), 153, 25, 75, 20 );
			adic( txtItemLucro, 148, 45, 70, 20 );

			/**************************
			 * Aba Det. Custos
			 **************************/

			tpnCab.addTab( "Det. custos", pinCabDetCustos );
			setPainel( pinCabDetCustos );

			/****************************
			 * Painel Geral - Det. Custos
			 *****************************/
			pnDetCustosGeral.setBorder( BorderFactory.createTitledBorder( "Detalhamento Geral" ) );
			adic( pnDetCustosGeral, 4, 0, 350, 92 );
			setPainel( pnDetCustosGeral );

			adic( new JLabelPad( "Ult.Compra" ), 4, 0, 80, 15 );
			adic( txtTotCustoUC, 4, 15, 80, 15 );

			adic( new JLabelPad( "Comiss�o" ), 90, 0, 80, 15 );
			adic( txtTotComiss, 90, 15, 80, 15 );

			adic( new JLabelPad( "PIS" ), 173, 0, 80, 15 );
			adic( txtTotPIS, 173, 15, 80, 15 );

			adic( new JLabelPad( "Cofins" ), 256, 0, 80, 15 );
			adic( txtTotCofins, 256, 15, 80, 15 );

			adic( new JLabelPad( "C.Social" ), 4, 33, 80, 15 );
			adic( txtTotCSOCIAL, 4, 48, 80, 15 );

			adic( new JLabelPad( "IR" ), 90, 33, 80, 15 );
			adic( txtTotIR, 90, 48, 80, 15 );

			adic( new JLabelPad( "IPI" ), 173, 33, 80, 15 );
			adic( txtTotIPI, 173, 48, 80, 15 );

			adic( new JLabelPad( "ICMS" ), 256, 33, 80, 15 );
			adic( txtTotICMS, 256, 48, 80, 15 );

			/****************************
			 * Painel Item - Det. Custos
			 *****************************/
			setPainel( pinCabDetCustos );
			pnDetCustosItem.setBorder( BorderFactory.createTitledBorder( "" ) );
			adic( pnDetCustosItem, 360, 0, 350, 92 );
			setPainel( pnDetCustosItem );

			adic( new JLabelPad( "Ult.Compra" ), 4, 0, 80, 15 );
			adic( txtTotCustoUCIt, 4, 15, 80, 15 );

			adic( new JLabelPad( "Comiss�o" ), 90, 0, 80, 15 );
			adic( txtTotComissIt, 90, 15, 80, 15 );

			adic( new JLabelPad( "PIS" ), 173, 0, 80, 15 );
			adic( txtTotPISIt, 173, 15, 80, 15 );

			adic( new JLabelPad( "Cofins" ), 256, 0, 80, 15 );
			adic( txtTotCofinsIt, 256, 15, 80, 15 );

			adic( new JLabelPad( "C.Social" ), 4, 33, 80, 15 );
			adic( txtTotCSOCIALIt, 4, 48, 80, 15 );

			adic( new JLabelPad( "IR" ), 90, 33, 80, 15 );
			adic( txtTotIRIt, 90, 48, 80, 15 );

			adic( new JLabelPad( "IPI" ), 173, 33, 80, 15 );
			adic( txtTotIPIIt, 173, 48, 80, 15 );

			adic( new JLabelPad( "ICMS" ), 256, 33, 80, 15 );
			adic( txtTotICMSIt, 256, 48, 80, 15 );

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private HashMap<String, Object> getPermissaoUsu() {
		HashMap<String, Object> result = null;
		try {
			result = getDaoorcamento().getPermissaoUsu( ListaCampos.getMasterFilial( "SGUSUARIO" ), Aplicativo.getUsuario().getIdusu() );
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, err.getMessage() );
		}
		return result;
	}

	private void setCalcImpostos( boolean buscabase ) {

		try {
			impostos.setCodprod( txtCodProd.getVlrInteger() );
			impostos.setTipotransacao( CalcImpostos.TRANSACAO_SAIDA );
			impostos.setCoddestinatario( txtCodCli.getVlrInteger() );
			impostos.setCodtipomov( txtCodTipoMov.getVlrInteger() );

			impostos.setCodfisc( null );
			impostos.setCoditfisc( null );

			impostos.calcTratTrib();


			impostos.setBuscabase( buscabase );
			impostos.setVlrprod( txtVlrProdItOrc.getVlrBigDecimal() );
			impostos.setVlrdescit( txtVlrDescItOrc.getVlrBigDecimal() );

			impostos.calcTratTrib();

			impostos.calcIPI();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void getCalcImpostos() {
		try {
			// Verifica se o cliente deve receber o desconto o IPI
			String descipi = txtDescIpi.getVlrString();

			if( descipi == null || "".equals( descipi ) ) {
				descipi = "N";
			}

			if( descipi.equals( "S" ) && impostos.getAliqipifisc().compareTo( new BigDecimal(0) ) >0 ){

				//					BigDecimal preco_bruto = buscaPreco( getParansPreco() );
				BigDecimal preco_bruto = txtVlrLiqItOrc.getVlrBigDecimal();

				BigDecimal vlr_ipi = preco_bruto.subtract( preco_bruto ).multiply( impostos.getAliqipifisc().divide( new BigDecimal(100) ) );
				BigDecimal preco_liquido_menos_ipi =  preco_bruto.divide( new BigDecimal(1).add (impostos.getAliqipifisc().divide( new BigDecimal(100)) ), BigDecimal.ROUND_CEILING);
				BigDecimal preco_liquido_menos_mais_desconto =  preco_liquido_menos_ipi.add( txtVlrDescItOrc.getVlrBigDecimal() );

				setParansPreco( preco_liquido_menos_ipi );
				txtVlrLiqItOrc.setVlrBigDecimal( preco_liquido_menos_ipi.add (txtVlrIPIItOrc.getVlrBigDecimal()) );
			}

			txtVlrIPIItOrc.setVlrBigDecimal( impostos.getVlripiit() );

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private BigDecimal calcPeso() {
		BigDecimal result = new BigDecimal(0);
		if ( txtCodTran.getVlrInteger() == 0 ) { // se n�o tiver transportadora no tipo de movimento, pega do cliente.
			txtCodTran.setVlrInteger( getCodTran() );
			lcTran.carregaDados();
		}
		try {
			result = getDaoorcamento().calcPeso(txtCodOrc.getVlrInteger());
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, err.getMessage() );
		} 
		return result;
	}

	private Integer getCodTran() {
		Integer result = null;
		try {
			getDaoorcamento().getCodTran( txtCodOrc.getVlrInteger(), ListaCampos.getMasterFilial( "SGPREFERE1" ) );
		} catch (Exception err) {
			Funcoes.mensagemErro( this, err.getMessage() );
		}
		return result;
	}

	
	private void novoModelo(){
		
		Integer codmodel = (Integer) oPrefs[Orcamento.PrefOrc.CODMODELOR.ordinal()];
		
        Atendimento atd = new Atendimento();
        	
        if(codmodel != null){
	       	try {
				atd = daoatend.loadModelAtend( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "ATATENDIMENTO" ), Aplicativo.iCodEmp, 
						ListaCampos.getMasterFilial( "ATMODATENDO" ), codmodel);
				novoAtend( null, txtCodCli.getVlrInteger(), atd, "Novo atendimento a partir de modelo" );
			} catch ( SQLException e ) {
				Funcoes.mensagemErro( this, "Erro carregando modelo de atendimento!\n" + e.getMessage()	);
				e.printStackTrace();
			}
        }
    	
	}

	private void novoAtend( Integer codchamado, Integer codcli, org.freedom.modulos.crm.business.object.Atendimento atd, String titulo ) {

		Object ORets[];

		FNovoAtend dl = null;
		boolean financeiro = false;
		String tipoatendo = "A"; // Setando o tipo de atendimento para "A" de atendimento;
		

		if (atd==null) {

			if ( txtCodCli.getVlrInteger() > 0 ) {
				codcli = txtCodCli.getVlrInteger();
			}

			if ( codcli == null ) {
				codcli = new Integer( 0 );
			}

			dl = new FNovoAtend( codcli.intValue(), codchamado, this, con, tipoatendo, false, financeiro , null, false);

		} else {
			atd.setCodcli( codcli );
			atd.setCodorc( txtCodOrc.getVlrInteger() );
			
			dl = new FNovoAtend( this, con, atd, tipoatendo, titulo, false );
		}

/*		dl.setModal( false );

		dl.setVisible( true );
*/
		Aplicativo.telaPrincipal.criatela( "Novo Atendimento", dl, con );
	}

	public void mouseClicked( MouseEvent e ) {
		if (e.getSource()==txtDescProd) {
			if ( e.getClickCount() == 2 ) {
				mostraTelaDecricao( txaObsItOrc, txtCodProd.getVlrInteger().intValue(), txtDescProd.getVlrString(), (Boolean) oPrefs[PrefOrc.BLOQDESCCOMPORC.ordinal()] );
			}
		} else if (e.getSource()==tabPedidos) {
			if ( e.getClickCount() == 2 ) {
				abreVenda();
			}
		} else if (e.getSource()==tabAtendimentos) {
			if ( e.getClickCount() == 2 && e.getModifiers() == MouseEvent.BUTTON1_MASK ) {
				visualizaAtend();
			}
		}
	}

	public void mousePressed( MouseEvent e ) {

		
	}

	public void mouseReleased( MouseEvent e ) {

		
	}

	public void mouseEntered( MouseEvent e ) {

		
	}

	public void mouseExited( MouseEvent e ) {

		
	}
	
	public void setAtivaNavegacao(boolean ativar) {
		nav.setAtivo( Navegador.BT_ANTERIOR, ativar );
		nav.setAtivo( Navegador.BT_PROXIMO, ativar );
		nav.setAtivo( Navegador.BT_PRIMEIRO, ativar );
		nav.setAtivo( Navegador.BT_ULTIMO, ativar );
		txtCodOrc.setEnabled( ativar );
	}

}
