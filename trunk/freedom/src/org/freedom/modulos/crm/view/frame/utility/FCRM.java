/*
 * Projeto: Freedom Pacote: org.freedom.modules.crm Classe: @(#)FAtendimento.java
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR> modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR> na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR> sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR> Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR> Voc� deve ter recebido uma c�pia da Licen�a P�blica
 * Geral GNU junto com este programa, se n�o, <BR> escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 */

package org.freedom.modulos.crm.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.functions.FuncoesCRM;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.atd.view.frame.crud.plain.FAtendente;
import org.freedom.modulos.crm.business.component.Atendimento;
import org.freedom.modulos.crm.business.object.Chamado;
import org.freedom.modulos.crm.business.object.Prioridade;
import org.freedom.modulos.crm.view.dialog.utility.DLAtendimento;
import org.freedom.modulos.crm.view.frame.crud.plain.FChamado;
import org.freedom.modulos.crm.view.frame.crud.plain.FEspecAtend;
import org.freedom.modulos.crm.view.frame.report.FRAtendimentos;
import org.freedom.modulos.gms.business.object.StatusOS;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

/**
 * 
 * @author Setpoint Inform�tica Ltda. / Alex Rodrigues
 * @version 15/04/2010 - Anderson Sanchez
 * 			14/10/2011 - Bruno Nascimento
 */

public class FCRM extends FFilho implements CarregaListener, ActionListener, FocusListener, JComboBoxListener, KeyListener, ChangeListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private static final int ABA_NONE= -1;

	private static final int ABA_CHAMADO = 0;

	private static final int ABA_ATENDIMENTO = 1;

	private JTabbedPanePad tpnAbas = new JTabbedPanePad();

	private JPanelPad pinCli = new JPanelPad();

	private JPanelPad pinFiltrosAtend = new JPanelPad( 510, 120 );

	private JPanelPad pinFiltrosChamado = new JPanelPad( 510, 120 );

	private JPanelPad pinFiltrosTitulo = new JPanelPad( 510, 200 );
	
	private JPanelPad pnAtd = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
		
	private JPanelPad pnNavAtd = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinNavAtd = new JPanelPad ( JPanelPad.TP_JPANEL, new GridLayout( 1, 2 ) );

	private JPanelPad pnChm = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pnNavChm = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinNavChm = new JPanelPad ( JPanelPad.TP_JPANEL, new GridLayout( 1, 2 ) );
	
	private JPanelPad pinCabCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCabCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnRodCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JTablePad tabatd = new JTablePad();

	private JTablePad tabchm = new JTablePad();

	private JTablePad tabstatus = new JTablePad();

	private JTablePad tabsprioridade = new JTablePad();

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodAtendo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodAtendenteAtendimento = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeAtendenteAtendimento = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodAtendenteChamado = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeAtendenteChamado = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodChamado = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodEspec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescEspec = new JTextFieldFK( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldFK txtDescChamado = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDDDCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtFoneCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtDDDFax = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtFaxCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtDDDCel = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtCelCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtEmailCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtContatoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtEndCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtCidCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldFK txtUfCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtNumCli = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtDatainiAtend = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafimAtend = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatainiCham = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafimCham = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNParcItRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDiasAtrazo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtVlrParcItRec = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrApagItRec = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrPagoItRec = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextAreaPad txaObsItRec = new JTextAreaPad();

	private JTextFieldPad txtDtEmis = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtPagoItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtVencItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtStatusItRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JButtonPad btNovoAtendimento = new JButtonPad( Icone.novo( "btNovo.gif" ) );

	private JButtonPad btNovoChamado = new JButtonPad( Icone.novo( "btNovo.gif" ) );

	private JButtonPad btAtualizaChamados = new JButtonPad( Icone.novo( "btAtualiza.gif" ) );

	private JButtonPad btAtualizaAtendimentos = new JButtonPad( Icone.novo( "btAtualiza.gif" ) );
	
	private JButtonPad btEditar = new JButtonPad( Icone.novo( "btEditar.gif" ) );
	
	private JButtonPad btEditarAtd = new JButtonPad( Icone.novo( "btEditar.gif" ) );

	private JButtonPad btExcluir = new JButtonPad( Icone.novo( "btExcluir.gif" ) );

	private JButtonPad btExcluirAtd = new JButtonPad( Icone.novo( "btExcluir.gif" ) );
	
	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private JButtonPad btImprimir = new JButtonPad( Icone.novo( "btPrevimp.gif" ) );

	private JButtonPad btImprimirAtd = new JButtonPad( Icone.novo( "btPrevimp.gif" ) );
	
	private JButtonPad  btPrimeiro = new JButtonPad( Icone.novo( "btPrim.gif" ) );
	
	private JButtonPad 	btAnterior = new JButtonPad( Icone.novo( "btAnt.gif" ) );
	
	private JButtonPad  btProximo= new JButtonPad( Icone.novo( "btProx.gif" ) );
	
	private JButtonPad  btUltimo = new JButtonPad( Icone.novo( "btUlt.gif" ) );;
	
	private JButtonPad  btPrimeiroAtd = new JButtonPad( Icone.novo( "btPrim.gif" ) );
	
	private JButtonPad 	btAnteriorAtd = new JButtonPad( Icone.novo( "btAnt.gif" ) );
	
	private JButtonPad  btProximoAtd= new JButtonPad( Icone.novo( "btProx.gif" ) );
	
	private JButtonPad  btUltimoAtd  = new JButtonPad( Icone.novo( "btUlt.gif" ) );;
	
	private ImageIcon chamado_em_atendimento = Icone.novo( "chamado_em_atendimento.png" );

	private ImageIcon chamado_parado = Icone.novo( "cl_branco.png" );

	private Vector<String> vCodAtends = new Vector<String>();

	private Vector<String> vCodChamados = new Vector<String>();

	private Vector<Integer> vValsContr = new Vector<Integer>();

	private Vector<String> vLabsContr = new Vector<String>();

	private JComboBoxPad cbContr = new JComboBoxPad( vLabsContr, vValsContr, JComboBoxPad.TP_INTEGER, 8, 0 );

	private Vector<Integer> vValsitContr = new Vector<Integer>();

	private Vector<String> vLabsitContr = new Vector<String>();

	private JComboBoxPad cbitContr = new JComboBoxPad( vLabsitContr, vValsitContr, JComboBoxPad.TP_INTEGER, 8, 0 );

	private Vector<Integer> vValsTipo = new Vector<Integer>();

	private Vector<String> vLabsTipo = new Vector<String>();

	private JComboBoxPad cbTipoAtend = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_INTEGER, 8, 0 );

	private JComboBoxPad cbTpChamado = new JComboBoxPad( null, null, JComboBoxPad.TP_INTEGER, 4, 0 );

	// private JComboBoxPad cbStatus = new JComboBoxPad( null, null, JComboBoxPad.TP_STRING, 2, 0 );

	private ListaCampos lcCli = new ListaCampos( this );

	private ListaCampos lcAtendimento = new ListaCampos( this );

	private ListaCampos lcAtendenteAtendimento = new ListaCampos( this, "AE" );

	private ListaCampos lcAtendenteChamado = new ListaCampos( this, "AE" );

	private ListaCampos lcChamado = new ListaCampos( this, "CH" );
	
	private ListaCampos lcEspecAltend = new ListaCampos( this, "EA" );

	private ListaCampos lcItRec = new ListaCampos( this );

	private ListaCampos lcRec = new ListaCampos( this );

	private boolean carregagrid = false;

	private String tipoatendo = null; // Tipo de atendimento

	private JPanelPad pnBotConv = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 2 ) );

	private JComboBoxPad cbPrioridade = new JComboBoxPad( null, null, JComboBoxPad.TP_INTEGER, 4, 0 );

	private JScrollPane scpStatus = new JScrollPane( tabstatus );

	private JScrollPane scpPrioridade = new JScrollPane( tabsprioridade );
	
	private ImageIcon imgColuna = Icone.novo( "clAgdCanc.png" );

	private JCheckBoxPad cbEmAtendimento = new JCheckBoxPad( "S� em atendimento?", "S", "N" );

	private boolean financeiro = false;
	
	private JScrollPane scpChm = new JScrollPane( tabchm );
	
	private BigDecimal total_horas_chamados = new BigDecimal(0);
	
	private final JDialog dlMensagem = new JDialog();
	
	private DLAtendimento dl = null;
	
	private int TIPO_PK = Types.INTEGER;

	public enum GridChamado {
		DTCHAMADO, PRIORIDADE, DESCTPCHAMADO, CODCHAMADO, CLIENTE, DESCCHAMADO, DESIGNADO, STATUS, QTDHORASPREVISAO, DTPREVISAO, EM_ATENDIMENTO, DADOS_ATENDIMENTO, TIPO_ATENDIMENTO, DETCHAMADO, CODCLI
	}
	
	public enum GridAtendimento {
		
		CODATENDO, DOCATENDO, STATUSATENDO, DATAATENDO, DATAATENDOFIN, NOMECLI, OBSATENDO, CODATEND, NOMEATEND, HORAATENDO, HORAATENDOFIN, TEMPO, CODCHAMADO, CODCLI, CODESPEC, DESCESPEC 
		
	}
	
	
	private JLabelPad lbStatus = new JLabelPad();
	
	private HashMap<String, Object> prefere = null;
	
	private JTextAreaPad txtDialog = new JTextAreaPad();
	
	private JScrollPane scpAtd = new JScrollPane( tabatd );
	
	private Long total_horas_atend = new Long(0);

	public FCRM() {

		super( false );

		setTitulo( "Gest�o de relacionamento com clientes" );
		setAtribos( 20, 20, 850, 650 );

		tipoatendo = "A"; // Setando o tipo de atendimento para "A" de atendimento;

		pnCabCli.setPreferredSize( new Dimension( 500, 80 ) );

		prefere = getPrefere();
		
		montaListaCamposAtend();
	
		montaTela();

		adicFiltrosAtend();
		adicFiltrosChamado();
		
		criaTelaMensagem();

	}

	private void adicFiltrosAtend() {

		pinFiltrosAtend.setBorder( SwingParams.getPanelLabel( "Filtros de atendimentos", Color.BLUE ) );

		pinFiltrosAtend.adic( txtDatainiAtend, 7, 20, 70, 20, "Data Inicial" );
		pinFiltrosAtend.adic( txtDatafimAtend, 80, 20, 70, 20, "Data Final" );

		pinFiltrosAtend.adic( txtCodAtendenteAtendimento, 153, 20, 50, 20, "Cd.Atd." );
		pinFiltrosAtend.adic( txtNomeAtendenteAtendimento, 206, 20, 120, 20, "Nome do Atendente" );

		pinFiltrosAtend.adic( txtCodChamado, 329, 20, 40, 20, "Cd.Ch." );		
		pinFiltrosAtend.adic( txtDescChamado, 370, 20, 150, 20, "Descri��o do chamado" );
		
		pinFiltrosAtend.adic( txtCodEspec, 523, 20, 45, 20, "Cd.Esp." );		
		pinFiltrosAtend.adic( txtDescEspec, 571, 20, 200, 20, "Descri��o da especifica��o" );
		
		pinFiltrosAtend.adic( cbTipoAtend, 7, 60, 195, 20, "Tipo" );

		pinFiltrosAtend.adic( cbContr, 206, 60, 314, 20, "Contrato/Projeto" );

		pinFiltrosAtend.adic( cbitContr, 523, 60, 247, 20, "Item" );

		pinFiltrosAtend.adic( btAtualizaAtendimentos, 782, 7, 30, 76 );

		pnAtd.add( pinFiltrosAtend, BorderLayout.NORTH );

		JPanelPad pnGridAtd = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

		pnGridAtd.add( scpAtd, BorderLayout.CENTER );

		pnAtd.add( pnGridAtd, BorderLayout.CENTER );

	}

	private void adicFiltrosChamado() {

		pinFiltrosChamado.setBorder( SwingParams.getPanelLabel( "Filtros de chamados", Color.BLUE ) );

		pinFiltrosChamado.adic( txtDatainiCham, 7, 20, 70, 20, "Data Inicial" );
		pinFiltrosChamado.adic( txtDatafimCham, 80, 20, 70, 20, "Data Final" );

		pinFiltrosChamado.adic( cbEmAtendimento, 7, 50, 200, 20 );

		pinFiltrosChamado.adic( txtCodAtendenteChamado, 153, 20, 70, 20, "C�d.Atend." );
		pinFiltrosChamado.adic( txtNomeAtendenteChamado, 226, 20, 280, 20, "Nome do Atendente designado" );

		pinFiltrosChamado.adic( cbTpChamado, 226, 60, 280, 20, "Tipo" );

		pinFiltrosChamado.adic( scpStatus, 519, 7, 130, 77 );
		pinFiltrosChamado.adic( scpPrioridade, 651, 7, 130, 77 );
		
		pinFiltrosChamado.adic( btAtualizaChamados, 782, 7, 30, 76 );
		

		pnChm.add( pinFiltrosChamado, BorderLayout.NORTH );

		JPanelPad pnGridChm = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

		pnGridChm.add( scpChm, BorderLayout.CENTER );

		pnChm.add( pnGridChm, BorderLayout.CENTER );

	}

	// Constru��o padr�o lan�amento de contatos financeiro/cobran�a

	public FCRM( Integer codcli, Integer codrec, Integer nparcitrec, boolean isUpdate ) {

		super( false );

		setTitulo( "Atendimento" );
		setAtribos( 20, 20, 850, 500 );

		tipoatendo = "C"; // Setando o tipo de atendimento para "C" de Contato;

		pnCabCli.setPreferredSize( new Dimension( 500, 155 ) );

		setConexao( Aplicativo.getInstace().getConexao() );

		montaListaCamposAtend();

		montaTela();

		txtCodCli.setVlrInteger( codcli );
		txtCodRec.setVlrInteger( codrec );
		txtNParcItRec.setVlrInteger( nparcitrec );

		pinFiltrosTitulo.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "T�tulo" ) );
		pinCabCli.adic( pinFiltrosTitulo, 8, 175, 744, 116 );

		montaListaCamposFinanc();

		adicCamposFinanc();

		ativaCamposFinanc();

		lcCli.carregaDados();
		lcRec.carregaDados();
		lcItRec.carregaDados();

		tpnAbas.setSelectedIndex( 1 );

		tpnAbas.setEnabled( false );

		calcAtrazo();

		financeiro = true;

	}

	private void adicCamposFinanc() {

		pinFiltrosTitulo.setBorder( SwingParams.getPanelLabel( "T�tulo", Color.BLUE ) );

		pinFiltrosTitulo.adic( txtCodRec, 7, 20, 70, 20, "C�d.rec" );
		pinFiltrosTitulo.adic( txtNParcItRec, 80, 20, 40, 20, "Parc." );
		pinFiltrosTitulo.adic( txtCodVenda, 123, 20, 50, 20, "Venda" );
		pinFiltrosTitulo.adic( txtDoc, 176, 20, 50, 20, "Doc." );
		pinFiltrosTitulo.adic( txtDtEmis, 229, 20, 70, 20, "Emiss�o" );
		pinFiltrosTitulo.adic( txtDtVencItRec, 302, 20, 70, 20, "Vencimento" );
		pinFiltrosTitulo.adic( txtDiasAtrazo, 375, 20, 40, 20, "Atrazo" );
		pinFiltrosTitulo.adic( txaObsItRec, 421, 20, 303, 60, "Obs." );
		pinFiltrosTitulo.adic( txtVlrParcItRec, 7, 60, 113, 20, "Valor do t�tulo" );
		pinFiltrosTitulo.adic( txtVlrPagoItRec, 123, 60, 104, 20, "Total pago" );
		pinFiltrosTitulo.adic( txtVlrApagItRec, 229, 60, 110, 20, "Total em aberto" );

		pnAtd.add( pinFiltrosTitulo, BorderLayout.NORTH );

		JPanelPad pnGridFinanc = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

		JScrollPane scpFinanc = new JScrollPane( tabatd );

		pnGridFinanc.add( scpFinanc, BorderLayout.CENTER );

		pnAtd.add( pnGridFinanc, BorderLayout.CENTER );

	}

	private void ativaCamposFinanc() {

		txtCodRec.setAtivo( false );
		txtNParcItRec.setAtivo( false );
		txtCodVenda.setAtivo( false );
		txtDoc.setAtivo( false );
		txtDtEmis.setAtivo( false );
		txtDtVencItRec.setAtivo( false );
		txtDiasAtrazo.setAtivo( false );
		txaObsItRec.setAtivo( false );

		txtVlrApagItRec.setAtivo( false );
		txtVlrPagoItRec.setAtivo( false );
		txtVlrParcItRec.setAtivo( false );

		txtCodRec.setEnabled( false );
		txtCodCli.setEnabled( false );
		txtNParcItRec.setEnabled( false );

	}

	private void montaListaCamposFinanc() {

		lcRec.add( new GuardaCampo( txtCodRec, "CodRec", "C�d.rec.", ListaCampos.DB_PK, false ) );
		lcRec.add( new GuardaCampo( txtCodVenda, "CodVenda", "C�d.venda", ListaCampos.DB_SI, false ) );
		lcRec.add( new GuardaCampo( txtDoc, "DocRec", "Doc", ListaCampos.DB_SI, false ) );
		lcRec.add( new GuardaCampo( txtDtEmis, "DataRec", "Emiss�o", ListaCampos.DB_SI, false ) );

		lcRec.montaSql( false, "RECEBER", "FN" );
		lcRec.setQueryCommit( false );
		lcRec.setReadOnly( true );
		txtCodRec.setTabelaExterna( lcRec, null );
		txtCodRec.setFK( true );

		lcItRec.add( new GuardaCampo( txtCodRec, "CodRec", "C�d.rec.", ListaCampos.DB_PK, false ) );
		lcItRec.add( new GuardaCampo( txtNParcItRec, "NParcItRec", "C�d.It.rec.", ListaCampos.DB_PK, false ) );
		lcItRec.add( new GuardaCampo( txtVlrParcItRec, "VlrParcItRec", "Vlr.titulo", ListaCampos.DB_SI, false ) );
		lcItRec.add( new GuardaCampo( txtVlrApagItRec, "VlrApagItRec", "Vlr.Aberto", ListaCampos.DB_SI, false ) );
		lcItRec.add( new GuardaCampo( txtVlrPagoItRec, "VlrPagoItRec", "Vlr.Pago", ListaCampos.DB_SI, false ) );
		lcItRec.add( new GuardaCampo( txtDtVencItRec, "DtVencItRec", "Vencimento", ListaCampos.DB_SI, false ) );
		lcItRec.add( new GuardaCampo( txtDtPagoItRec, "DtPagoItRec", "Dt.Pagto.", ListaCampos.DB_SI, false ) );
		lcItRec.add( new GuardaCampo( txtStatusItRec, "StatusItRec", "Status", ListaCampos.DB_SI, false ) );
		lcItRec.add( new GuardaCampo( txaObsItRec, "ObsItRec", "Observa�oes", ListaCampos.DB_SI, false ) );

		lcItRec.montaSql( false, "ITRECEBER", "FN" );
		lcItRec.setQueryCommit( false );
		lcItRec.setReadOnly( true );
		txtCodRec.setTabelaExterna( lcItRec, null );
		txtCodRec.setFK( true );

	}

	private void calcAtrazo() {

		Integer atrazo = 0;
		

		if ( "R1".equals( txtStatusItRec.getVlrString() ) || "RL".equals( txtStatusItRec.getVlrString() ) ) {
			atrazo = ( (Long) Funcoes.getNumDias( txtDtVencItRec.getVlrDate(), new Date() ) ).intValue();
			if ( atrazo.compareTo( new Integer( 0 ) ) > 0 ) {
				txtDiasAtrazo.setVlrInteger( atrazo );
			}
			else {
				txtDiasAtrazo.setVlrInteger( new Integer( 0 ) );
			}
		}
		if ( "RP".equals( txtStatusItRec.getVlrString() ) ) {
			atrazo = ( (Long) Funcoes.getNumDias( txtDtVencItRec.getVlrDate(), txtDtPagoItRec.getVlrDate() ) ).intValue();
			if ( atrazo.compareTo( new Integer( 0 ) ) > 0 ) {
				txtDiasAtrazo.setVlrInteger( atrazo );
			}
			else {
				txtDiasAtrazo.setVlrInteger( new Integer( 0 ) );
			}
		}
	}

	private void montaListaCamposAtend() {

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtFoneCli, "FoneCli", "Telefone", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtDDDCli, "DDDCli", "DDD", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtFoneCli, "FoneCli", "Telefone", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCelCli, "CelCli", "Fax", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtEmailCli, "EmailCli", "Email", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtContatoCli, "ContCli", "Contato", ListaCampos.DB_SI, false));
	

		
		lcCli.setWhereAdic( "ATIVOCLI='S'" );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli, FCliente.class.getCanonicalName() );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		lcAtendimento.add( new GuardaCampo( txtCodAtendo, "CodAtendo", "C�d.Atendo", ListaCampos.DB_PK, false ) );
		lcAtendimento.montaSql( false, "ATENDIMENTO", "AT" );
		lcAtendimento.setReadOnly( true );
		txtCodAtendo.setTabelaExterna( lcAtendimento, null );
		txtCodAtendo.setFK( true );
		txtCodAtendo.setNomeCampo( "CodAtendo" );


		// Atendimento para funcionamento
		txtCodAtendenteAtendimento.setTabelaExterna( lcAtendenteAtendimento, FAtendente.class.getCanonicalName() );
		txtCodAtendenteAtendimento.setFK( true );
		txtCodAtendenteAtendimento.setNomeCampo( "CodAtend" );
		lcAtendenteAtendimento.add( new GuardaCampo( txtCodAtendenteAtendimento, "CodAtend", "C�d.atend.", ListaCampos.DB_PK, false ) );
		lcAtendenteAtendimento.add( new GuardaCampo( txtNomeAtendenteAtendimento, "NomeAtend", "Nome", ListaCampos.DB_SI, false ) );
		lcAtendenteAtendimento.montaSql( false, "ATENDENTE", "AT" );
		lcAtendenteAtendimento.setReadOnly( true );

		
		txtCodAtendenteChamado.setTabelaExterna( lcAtendenteChamado, FAtendente.class.getCanonicalName() );
		txtCodAtendenteChamado.setFK( true );
		txtCodAtendenteChamado.setNomeCampo( "CodAtend" );
		lcAtendenteChamado.add( new GuardaCampo( txtCodAtendenteChamado, "CodAtend", "C�d.atend.", ListaCampos.DB_PK, false ) );
		lcAtendenteChamado.add( new GuardaCampo( txtNomeAtendenteChamado, "NomeAtend", "Nome", ListaCampos.DB_SI, false ) );
		lcAtendenteChamado.montaSql( false, "ATENDENTE", "AT" );
		lcAtendenteChamado.setReadOnly( true );


		txtCodChamado.setTabelaExterna( lcChamado, FChamado.class.getCanonicalName() );
		txtCodChamado.setFK( true );
		txtCodChamado.setNomeCampo( "CodChamado" );
		lcChamado.add( new GuardaCampo( txtCodChamado, "CodChamado", "C�d.Cham.", ListaCampos.DB_PK, false ) );
		lcChamado.add( new GuardaCampo( txtDescChamado, "DescChamado", "Descri��o do chamado", ListaCampos.DB_SI, false ) );
		// lcChamado.setDinWhereAdic( "CODCLI = #N", txtCodCli );
		lcChamado.montaSql( false, "CHAMADO", "CR" );
		lcChamado.setReadOnly( true );
		
	
		
		
		txtCodEspec.setTabelaExterna( lcEspecAltend, FEspecAtend.class.getCanonicalName() );
		txtCodEspec.setFK( true );
		txtCodEspec.setNomeCampo( "CodEspec" );
		lcEspecAltend.add( new GuardaCampo( txtCodEspec, "CodEspec", "C�d.Espec.", ListaCampos.DB_PK, false ) );
		lcEspecAltend.add( new GuardaCampo( txtDescEspec, "DescEspec", "Descri��o da especifica��o", ListaCampos.DB_SI, false ) );
		// lcChamado.setDinWhereAdic( "CODCLI = #N", txtCodCli );
		lcEspecAltend.montaSql( false, "ESPECATEND", "AT" );
		lcEspecAltend.setReadOnly( true );


	}

	private void montaGridAtend() {
		
		//CODATENDO, DOCATENDO, STATUSATENDO, DATAATENDO, DATAATENDOFIN, OBSATENDO, CODATEND, NOMEATEND, HORAATENDO, HORAATENDOFIN, TEMPO, CODCHAMADO, CODCLI, CODESPEC, DESCESPEC, NOMECLI
		
		tabatd.adicColuna( "Atd." );		// C�digo do atendimento
		tabatd.adicColuna( "Doc." );		// Documento do atendimento
		tabatd.adicColuna( "Status" );		// Status do atendimento
		
		tabatd.adicColuna( "Data" );		// Data inicio atendimento
		tabatd.adicColuna( "Data fim" );	// Data final atendimento

		tabatd.adicColuna( "Cliente" );		// Nome do cliente
		
		tabatd.adicColuna( "Atendimento" ); // Observa��es do atendimento
		tabatd.adicColuna( " C�d. Atend." );// C�digo do atendente
		tabatd.adicColuna( "Atendente" );	// C�digo do atendente
		tabatd.adicColuna( "Inicio" );	// Hora inicial
		tabatd.adicColuna( "Fim" );	// Hora final
		tabatd.adicColuna( "Tempo" );		// Tempo de atendimento
		tabatd.adicColuna( "Cham." );		// C�digo do chamado
		tabatd.adicColuna( "Cod.Cli." );	// C�digo do cliente
		
		tabatd.adicColuna( "C�d.Esp." );	// C�digo da especifica��o
		tabatd.adicColuna( "Descri��o da especifica��o" );	// Descri��o da especifica��o
		
		tabatd.setTamColuna( 150, GridAtendimento.NOMECLI.ordinal() );
		tabatd.setTamColuna( 250, GridAtendimento.OBSATENDO.ordinal() );
		
		tabatd.setTamColuna( 45, GridAtendimento.HORAATENDO.ordinal() );
		tabatd.setTamColuna( 45, GridAtendimento.HORAATENDOFIN.ordinal() );
		tabatd.setTamColuna( 45, GridAtendimento.TEMPO.ordinal() );
		
		tabatd.setTamColuna( 45, GridAtendimento.CODCHAMADO.ordinal() );
		tabatd.setTamColuna( 150, GridAtendimento.DESCESPEC.ordinal() );
		
		tabatd.setColunaInvisivel( GridAtendimento.CODATENDO.ordinal() );
		tabatd.setColunaInvisivel( GridAtendimento.DOCATENDO.ordinal() );
		tabatd.setColunaInvisivel( GridAtendimento.STATUSATENDO.ordinal() );
		tabatd.setColunaInvisivel( GridAtendimento.DATAATENDOFIN.ordinal() );
		tabatd.setColunaInvisivel( GridAtendimento.CODATEND.ordinal() );
		tabatd.setColunaInvisivel( GridAtendimento.CODCLI.ordinal() );
		tabatd.setColunaInvisivel( GridAtendimento.CODESPEC.ordinal() );

		tabatd.setRowHeight( 20 );
		
	}

	private void montaGridStatus() {

		tabstatus.adicColuna( "" );
		tabstatus.adicColuna( "Cod." );
		tabstatus.adicColuna( "" ); // Imagem
		tabstatus.adicColuna( "Status" );

		tabstatus.setTamColuna( 10, 0 );

		tabstatus.setColunaInvisivel( 1 );

		tabstatus.setTamColuna( 10, 2 );

		tabstatus.setTamColuna( 90, 3 );

		tabstatus.setRowHeight( 12 );

		tabstatus.setColunaEditavel( 0, new Boolean( true ) );

	}

	private void montaGridPrioridade() {

		tabsprioridade.adicColuna( "" );
		tabsprioridade.adicColuna( "Cod." );
		tabsprioridade.adicColuna( "Prioridade" );

		tabsprioridade.setTamColuna( 10, 0 );

		tabsprioridade.setColunaInvisivel( 1 );

		tabsprioridade.setTamColuna( 100, 2 );

		tabsprioridade.setRowHeight( 12 );

		tabsprioridade.setColunaEditavel( 0, new Boolean( true ) );

	}

	private void carregaStatus() {

		Vector<Object> valores = Chamado.getValores();
		Vector<String> labels = Chamado.getLabels();

		Vector<Object> item = null;

		for ( int i = 1; i < valores.size(); i++ ) { // Come�a em um para n�o carregar o item <--Selecione-->
			
			item = new Vector<Object>();

			String valor = valores.elementAt( i ).toString();
			String label = labels.elementAt( i );
			ImageIcon icon = Chamado.getImagem( valor, StatusOS.IMG_TAMANHO_P );

			if ( Chamado.CHAMADO_CONCLUIDO.getValue().equals( valor ) ) {
				item.addElement( new Boolean( false ) );
			}
			else if ( Chamado.CHAMADO_CANCELADO.getValue().equals( valor ) ) {
				item.addElement( new Boolean( false ) );
			}
			else {
				item.addElement( new Boolean( true ) );
			}

			item.addElement( valor );
			item.addElement( icon );
			item.addElement( label );

			tabstatus.adicLinha( item );

		}

	}

	private void carregaPrioriadade() {

		Vector<Object> valores = Prioridade.getValores();
		Vector<String> labels = Prioridade.getLabels();

		Vector<Object> item = null;

		for ( int i = 1; i < valores.size(); i++ ) { // Come�a em um para n�o carregar o item <--Selecione-->

			item = new Vector<Object>();

			item.addElement( new Boolean( true ) );
			item.addElement( valores.elementAt( i ) );
			item.addElement( labels.elementAt( i ) );

			tabsprioridade.adicLinha( item );

		}

	}

	private void montaGridChamado() {

		tabchm.adicColuna( "Data" );
		tabchm.adicColuna( "Pri" );
		tabchm.adicColuna( "Tipo" );
		tabchm.adicColuna( "C�d." );
		tabchm.adicColuna( "Cliente" );
		tabchm.adicColuna( "Descri��o" );
//		tabchm.adicColuna( "Solicitante" );
		tabchm.adicColuna( "Designado" );
		tabchm.adicColuna( "St." );
		tabchm.adicColuna( "Qtd.Prev." );
		tabchm.adicColuna( "Dt.Prev." );
		tabchm.adicColuna( "" );
		tabchm.adicColuna( "Em atendimento" );
		tabchm.adicColuna( "Tipo atendimento" );
		tabchm.adicColuna( "Detalhamento do chamado" ); // Oculto;
		tabchm.adicColuna( "Codcli" ); // Oculto

		tabchm.setTamColuna( 70, GridChamado.DTCHAMADO.ordinal() );
		tabchm.setTamColuna( 20, GridChamado.PRIORIDADE.ordinal() );
		tabchm.setTamColuna( 82, GridChamado.DESCTPCHAMADO.ordinal() );
		tabchm.setTamColuna( 30, GridChamado.CODCHAMADO.ordinal() );
		tabchm.setTamColuna( 140, GridChamado.CLIENTE.ordinal() );
		tabchm.setTamColuna( 250, GridChamado.DESCCHAMADO.ordinal() );
		tabchm.setTamColuna( 75, GridChamado.DESIGNADO.ordinal() );
		tabchm.setTamColuna( 25, GridChamado.STATUS.ordinal() );
		tabchm.setTamColuna( 40, GridChamado.QTDHORASPREVISAO.ordinal() );
		tabchm.setTamColuna( 70, GridChamado.DTPREVISAO.ordinal() );
		tabchm.setTamColuna( 20, GridChamado.EM_ATENDIMENTO.ordinal() );
		tabchm.setTamColuna( 140, GridChamado.DADOS_ATENDIMENTO.ordinal() );
//		tabchm.setTamColuna( 140, GridChamado.TIPO_ATENDIMENTO.ordinal() );
	

		tabchm.setColunaInvisivel( GridChamado.DETCHAMADO.ordinal() );
		tabchm.setColunaInvisivel( GridChamado.CODCLI.ordinal() );
		tabchm.setColunaInvisivel( GridChamado.TIPO_ATENDIMENTO.ordinal() );

		tabchm.setRowHeight( 20 );

	}

	private void formataMascaras() {

		txtFoneCli.setMascara( JTextFieldPad.MC_FONE );
		txtFaxCli.setMascara( JTextFieldPad.MC_FONE );
		txtCelCli.setMascara( JTextFieldPad.MC_FONE );

	}

	private void adicionaAbas() {

		tpnAbas.setTabLayoutPolicy( JTabbedPanePad.SCROLL_TAB_LAYOUT );

		tpnAbas.setPreferredSize( new Dimension( 600, 30 ) );

		tpnAbas.setTabPlacement( SwingConstants.BOTTOM );
		
		tpnAbas.addTab( "Chamados", pnChm );
		
		tpnAbas.addTab( "Atendimentos", pnAtd );

	}

	private void adicionaFiltroCli() {

		pinCabCli.add( pinCli, BorderLayout.CENTER );
		pinCli.setBorder( SwingParams.getPanelLabel( "Filtro de cliente", Color.blue ) );

		pinCli.adic( txtCodCli, 	10, 	20, 	50, 	20, "C�d.Cli." 					);
		pinCli.adic( txtRazCli, 	63, 	20, 	200, 	20, "Raz�o social do cliente" 	);
		pinCli.adic( txtContatoCli, 266, 	20, 	130, 	20, "Contato" 					);
		pinCli.adic( txtDDDCli, 	399, 	20, 	25, 	20, "DDD" 						);	
		pinCli.adic( txtFoneCli, 	427, 	20, 	70, 	20, "Telefone" 					);	
		pinCli.adic( txtCelCli, 	500, 	20, 	70, 	20, "Celular"					);		
		pinCli.adic( txtEmailCli, 	573, 	20, 	150, 	20, "Email" 					);
		pinCli.adic( lbStatus, 		726, 	20, 	90, 	20, "Situa��o"					);
		
		lbStatus.setForeground( Color.WHITE );
		lbStatus.setFont( SwingParams.getFontboldmed() );
		lbStatus.setHorizontalAlignment( SwingConstants.CENTER );
		lbStatus.setOpaque( true );
		lbStatus.setText( "" );
		lbStatus.setBackground( Color.LIGHT_GRAY );
		lbStatus.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
	
	}
	
	private void adicionaNavegadorChm() {
		
		pnChm.add( pnNavChm, BorderLayout.SOUTH );
		pnNavChm.add( pinNavChm, BorderLayout.WEST );
		pinNavChm.setPreferredSize( new Dimension( 260, 30 ) );
		pinNavChm.add( btPrimeiro );
		pinNavChm.add( btAnterior );
		pinNavChm.add( btProximo );
		pinNavChm.add( btUltimo );
		pinNavChm.add( btNovoChamado );
		pinNavChm.add( btExcluir );
		pinNavChm.add( btEditar );
		pinNavChm.add( btImprimir );

		
	}
	
	private void adicionaNavegadorAtd() {
		
		pnAtd.add( pnNavAtd , BorderLayout.SOUTH );
		pnNavAtd.add( pinNavAtd, BorderLayout.WEST );
		pinNavAtd.setPreferredSize( new Dimension ( 260, 30 ) );
		pinNavAtd.add( btPrimeiroAtd );
		pinNavAtd.add( btAnteriorAtd );
		pinNavAtd.add( btProximoAtd );
		pinNavAtd.add( btUltimoAtd );
		pinNavAtd.add(btNovoAtendimento);
		pinNavAtd.add( btExcluirAtd );
		pinNavAtd.add(btEditarAtd);
		pinNavAtd.add( btImprimirAtd );

	}

	/*
	private void adicBotoes() {

		pnBotConv.setPreferredSize( new Dimension( 120, 30 ) );
		pnBotConv.add( btNovoAtendimento );
		pnBotConv.add( btExcluir );
		pnBotConv.add( btImprimir );
		pnBotConv.add( btNovoChamado );
	}
	 */

	private void montaTela() {

		getTela().add( pnCli, BorderLayout.CENTER );

		formataMascaras();

		pnCabCli.add( pinCabCli, BorderLayout.CENTER );
		pnCli.add( pnCabCli, BorderLayout.NORTH );
		pnCli.add( tpnAbas, BorderLayout.CENTER );
		
		adicionaAbas();
		
		adicionaFiltroCli();
		
		adicionaNavegadorChm();
		
		adicionaNavegadorAtd();
		
		montaGridAtend();

		montaGridChamado();

		montaGridStatus();

		montaGridPrioridade();

		adicRodape();

		adicListeners();

//		txtDatainiAtend.setVlrDate( Funcoes.getDataIniMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );
//		txtDatafimAtend.setVlrDate( Funcoes.getDataFimMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );

		txtDatainiAtend.setVlrDate( new Date() );
		txtDatafimAtend.setVlrDate( new Date() );
		
		txtDatainiCham.setVlrDate( Funcoes.getDataIniMes( Funcoes.getMes( new Date() ), Funcoes.getAno( new Date() ) - 1 ) );
		txtDatafimCham.setVlrDate( Funcoes.getDataFimMes( Funcoes.getMes( new Date() ) - 1, Funcoes.getAno( new Date() ) ) );

		vValsContr.addElement( -1 );
		vLabsContr.addElement( "<Todos>" );
		cbContr.setItensGeneric( vLabsContr, vValsContr );

		vValsitContr.addElement( -1 );
		vLabsitContr.addElement( "<Todos>" );
		cbitContr.setItensGeneric( vLabsitContr, vValsitContr );

		vValsTipo.addElement( -1 );
		vLabsTipo.addElement( "<Todos>" );
		cbTipoAtend.setItensGeneric( vLabsTipo, vValsTipo );

		carregaStatus();
		carregaPrioriadade();

	}

	private void adicListeners() {

		tpnAbas.addChangeListener( this );
		
		btPrimeiro.addActionListener( this );
		btAnterior.addActionListener( this );
		btProximo.addActionListener( this );
		btUltimo.addActionListener( this );
		btPrimeiroAtd.addActionListener( this );
		btAnteriorAtd.addActionListener( this );
		btProximoAtd.addActionListener( this );
		btUltimoAtd.addActionListener( this );
		btNovoAtendimento.addActionListener( this );
		btNovoChamado.addActionListener( this );
		btEditar.addActionListener( this );
		btEditarAtd.addActionListener(this );
		btExcluir.addActionListener( this );
		btExcluirAtd.addActionListener( this );
		btImprimir.addActionListener( this );
		btImprimirAtd.addActionListener( this );
		btAtualizaChamados.addActionListener( this );
		btAtualizaAtendimentos.addActionListener( this );

		btSair.addActionListener( this );
		lcCli.addCarregaListener( this );
		lcChamado.addCarregaListener( this );
		lcAtendenteAtendimento.addCarregaListener( this );

		lcAtendenteChamado.addCarregaListener( this );

		txtDatafimAtend.addFocusListener( this );
		txtDatafimCham.addFocusListener( this );

		cbContr.addComboBoxListener( this );
		cbitContr.addComboBoxListener( this );
		cbTipoAtend.addComboBoxListener( this );
		txtCodCli.addKeyListener( this );
		txtCodAtendenteAtendimento.addKeyListener( this );

		// cbStatus.addComboBoxListener( this );
		cbTpChamado.addComboBoxListener( this );
		cbPrioridade.addComboBoxListener( this );

		tabatd.addMouseListener( this );
		tabchm.addMouseListener( this );
		
	}

	private void adicRodape() {

		pnRodCli.setBorder( SwingParams.loweredetched );

		//pnRodCli.add( pnBotConv, BorderLayout.WEST );

		btSair.setPreferredSize( new Dimension( 110, 30 ) );

		pnRodCli.add( btSair, BorderLayout.EAST );

		pnCli.add( pnRodCli, BorderLayout.SOUTH );
	}
	

	private void visualizaCham() {

		FChamado chamado = null;

		if ( Aplicativo.telaPrincipal.temTela( FChamado.class.getName() ) ) {
			chamado = (FChamado) Aplicativo.telaPrincipal.getTela( FChamado.class.getName() );
		}
		else {
			chamado = new FChamado();
			Aplicativo.telaPrincipal.criatela( "Chamado", chamado, con );
		}

		chamado.exec( (Integer) tabchm.getValor( tabchm.getLinhaSel(), GridChamado.CODCHAMADO.ordinal() ) );

	}

	private void visualizaAtend() {

		String codatendo = (String) tabatd.getValor( tabatd.getLinhaSel(), GridAtendimento.CODATENDO.ordinal() ).toString();
		String codatend = (String) tabatd.getValor( tabatd.getLinhaSel(), GridAtendimento.CODATEND.ordinal() ).toString();
		
		int icodAtend = Integer.parseInt( codatend );
		int icodAtendo = Integer.parseInt( codatendo );
		
		Integer codchamado = (Integer) tabatd.getValor( tabatd.getLinhaSel(), GridAtendimento.CODCHAMADO.ordinal() );

		try {
			
			if(dl!=null && dl.isUpdate()) {
				
				dl.abreAtendimento( txtCodCli.getVlrInteger(), codchamado, this, true, con, icodAtendo, icodAtend, tipoatendo, financeiro );
				
			}
			else {
			
				dl = new DLAtendimento( txtCodCli.getVlrInteger(), codchamado, this, true, con, icodAtendo, icodAtend, tipoatendo, financeiro );
			}
			
			dl.setModal( false );
			
			dl.setVisible( true );
			
		} 
		catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar campos!" );
		}
	}

	public void carregaAtendimentos() {

		StringBuilder sql = new StringBuilder();

		if ( carregagrid ) {

			sql.append( "SELECT ATEND.CODATENDO,ATEND.DOCATENDO,ATEND.STATUSATENDO,ATEND.DATAATENDO,TA.DESCTPATENDO, " );
			sql.append( "ATEND.DATAATENDOFIN, ATEND.HORAATENDOFIN,ATEND.OBSATENDO, ATEND.CODATEND, " );
			sql.append( "A.NOMEATEND,ATEND.HORAATENDO, ATEND.CODCHAMADO, ATEND.CODCLI, ATEND.CODESPEC, ea.descespec, cl.nomecli, ");
			
			sql.append( "coalesce(ea.mrelcobespec, 'N') mrelcobespec, coalesce(ea.bhespec, 'N') bhespec, coalesce(ea.contmetaespec, 'N') contmetaespec, coalesce(ea.cobcliespec, 'N') cobcliespec " );
			
			sql.append( "FROM ATTIPOATENDO TA, ATATENDENTE A, VDCLIENTE CL, ATATENDIMENTO ATEND ");
			
			sql.append( "left outer join atespecatend ea on ea.codemp=atend.codempea and ea.codfilial=atend.codfilialea and ea.codespec=atend.codespec " );
			
			sql.append( "WHERE " );
			sql.append( "TA.CODTPATENDO=ATEND.CODTPATENDO AND TA.CODEMP=ATEND.CODEMPTO AND TA.CODFILIAL=ATEND.CODFILIALTO " );
			sql.append( "AND A.CODATEND=ATEND.CODATEND AND A.CODEMP=ATEND.CODEMPAE AND A.CODFILIAL=ATEND.CODFILIALAE " );
			sql.append( "and cl.codemp=atend.codempcl and cl.codfilial=atend.codfilialcl and cl.codcli=atend.codcli " );
			sql.append( "AND TA.TIPOATENDO=? " );

			if ( ! ( txtCodRec.getVlrInteger() > 0 ) && ( txtDatainiAtend.getVlrDate() != null && txtDatafimAtend.getVlrDate() != null ) ) {
				sql.append( "AND ATEND.DATAATENDO BETWEEN ? AND ?" );
			}

			if ( txtCodCli.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPCL=? AND ATEND.CODFILIALCL=? AND ATEND.CODCLI=? " );
			}
			if ( cbContr.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPCT=? AND ATEND.CODFILIALCT=? AND ATEND.CODCONTR=? " );
			}
			if ( cbitContr.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODITCONTR=? " );
			}
			if ( cbTipoAtend.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPTO=? AND ATEND.CODFILIALTO=? AND ATEND.CODTPATENDO=? " );
			}
			if ( txtCodAtendenteAtendimento.getVlrInteger() > 0 && ( !financeiro ) ) {
				sql.append( " AND ATEND.CODEMPAE=? AND ATEND.CODFILIALAE=? AND ATEND.CODATEND=? " );
			}
			if ( txtCodChamado.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPCH=? AND ATEND.CODFILIALCH=? AND ATEND.CODCHAMADO=? " );
			}
			if ( txtCodEspec.getVlrInteger() > 0 ) {
				sql.append( " AND ATEND.CODEMPEA=? AND ATEND.CODFILIALEA=? AND ATEND.CODESPEC=? " );
			}

			if ( txtCodRec.getVlrInteger() > 0 ) {
				sql.append( " AND EXISTS(SELECT CODREC FROM ATATENDIMENTOITREC IR " );
				sql.append( "WHERE IR.CODEMP=ATEND.CODEMP AND IR.CODFILIAL=ATEND.CODFILIAL " );
				sql.append( "AND IR.CODATENDO=ATEND.CODATENDO AND IR.CODEMPIR=? AND IR.CODFILIALIR=? " );
				sql.append( "AND IR.CODREC=? AND IR.NPARCITREC=?)" );
			}

			sql.append( "ORDER BY ATEND.DATAATENDO DESC,ATEND.HORAATENDO DESC " );

			try {
				int iparam = 1;

				PreparedStatement ps = con.prepareStatement( sql.toString() );

				ps.setString( iparam++, tipoatendo );

				if ( ! ( txtCodRec.getVlrInteger() > 0 ) && ( txtDatainiAtend.getVlrDate() != null && txtDatafimAtend.getVlrDate() != null ) ) {
					ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatainiAtend.getVlrDate() ) );
					ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatafimAtend.getVlrDate() ) );
				}

				if ( txtCodCli.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, lcCli.getCodEmp() );
					ps.setInt( iparam++, lcCli.getCodFilial() );
					ps.setInt( iparam++, txtCodCli.getVlrInteger() );
				}
				if ( cbContr.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, Aplicativo.iCodEmp );
					ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDCONTRATO" ) );
					ps.setInt( iparam++, cbContr.getVlrInteger() );
				}
				if ( cbitContr.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, cbitContr.getVlrInteger() );
				}
				if ( cbTipoAtend.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, Aplicativo.iCodEmp );
					ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
					ps.setInt( iparam++, cbTipoAtend.getVlrInteger() );
				}
				if ( txtCodAtendenteAtendimento.getVlrInteger() > 0 && ( !financeiro ) ) {
					ps.setInt( iparam++, lcAtendenteAtendimento.getCodEmp() );
					ps.setInt( iparam++, lcAtendenteAtendimento.getCodFilial() );
					ps.setInt( iparam++, txtCodAtendenteAtendimento.getVlrInteger() );
				}
				if ( txtCodRec.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, lcRec.getCodEmp() );
					ps.setInt( iparam++, lcRec.getCodFilial() );
					ps.setInt( iparam++, txtCodRec.getVlrInteger() );
					ps.setInt( iparam++, txtNParcItRec.getVlrInteger() );
				}
				if ( txtCodChamado.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, Aplicativo.iCodEmp );
					ps.setInt( iparam++, ListaCampos.getMasterFilial( "CHCHAMADO" ) );
					ps.setInt( iparam++, txtCodChamado.getVlrInteger() );
				}
				if ( txtCodEspec.getVlrInteger() > 0 ) {
					ps.setInt( iparam++, Aplicativo.iCodEmp );
					ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATESPECATEND" ) );
					ps.setInt( iparam++, txtCodEspec.getVlrInteger() );
				}
				

				ResultSet rs = ps.executeQuery();

				tabatd.limpa();
				vCodAtends.clear();
						
				total_horas_atend = new Long(0);
				
				for ( int i = 0; rs.next(); i++ ) {
					tabatd.adicLinha();

					vCodAtends.add( "" + rs.getString( "CodAtendo" ) );
					
					Color corlinha = Color.BLACK;
					
					//	ea.mrelcobspec, ea.bhespec, ea.contmetaespec, ea.cobcliespec " );
					
					
					tabatd.setValor( rs.getString( GridAtendimento.CODATENDO.name() )												, i, GridAtendimento.CODATENDO.ordinal() );
					tabatd.setValor( rs.getString( GridAtendimento.DOCATENDO.name() )												, i, GridAtendimento.DOCATENDO.ordinal() );
					tabatd.setValor( rs.getString( GridAtendimento.STATUSATENDO.name() )											, i, GridAtendimento.STATUSATENDO.ordinal() );
					
					tabatd.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( GridAtendimento.DATAATENDO.name() ) )			, i, GridAtendimento.DATAATENDO.ordinal() );
					tabatd.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( GridAtendimento.DATAATENDOFIN.name() ) )			, i, GridAtendimento.DATAATENDOFIN.ordinal() );
					
					tabatd.setValor( rs.getString( GridAtendimento.OBSATENDO.name() )												, i, GridAtendimento.OBSATENDO.ordinal() );

					tabatd.setValor( rs.getInt( GridAtendimento.CODATEND.name() )													, i, GridAtendimento.CODATEND.ordinal() );
					
					tabatd.setValor( rs.getString( GridAtendimento.NOMEATEND.name() ).trim()										, i, GridAtendimento.NOMEATEND.ordinal() );
					
					tabatd.setValor( Funcoes.copy( rs.getTime( GridAtendimento.HORAATENDO.name() ).toString() ,5 )					, i, GridAtendimento.HORAATENDO.ordinal() );
					tabatd.setValor( Funcoes.copy( rs.getTime( GridAtendimento.HORAATENDOFIN.name() ).toString(),5 )				, i, GridAtendimento.HORAATENDOFIN.ordinal() );

					tabatd.setValor( Funcoes.copy( Funcoes.longTostrTimeHoras( Funcoes.subtraiTime( rs.getTime( "HoraAtendo" ),rs.getTime( "HoraAtendoFin" ) )), 5) , i, GridAtendimento.TEMPO.ordinal() );
					
					tabatd.setValor( rs.getInt( GridAtendimento.CODCHAMADO.name() )													, i, GridAtendimento.CODCHAMADO.ordinal() );
					tabatd.setValor( rs.getInt( GridAtendimento.CODCLI.name() )														, i, GridAtendimento.CODCLI.ordinal() );
					
					tabatd.setValor( rs.getInt( GridAtendimento.CODESPEC.name() )													, i, GridAtendimento.CODESPEC.ordinal() );
					
					tabatd.setValor( 
							rs.getString( GridAtendimento.CODESPEC.name() )!= null ? 
							( rs.getString( GridAtendimento.CODESPEC.name()).trim() + " " + rs.getString( GridAtendimento.DESCESPEC.name() ).trim()) :
							""
							, i, GridAtendimento.DESCESPEC.ordinal() );
					
					tabatd.setValor( rs.getString( GridAtendimento.CODCLI.name()).trim() + " " + rs.getString( GridAtendimento.NOMECLI.name())	, i, GridAtendimento.NOMECLI.ordinal() );
					
					total_horas_atend += Funcoes.subtraiTime( rs.getTime( "HoraAtendo" ),rs.getTime( "HoraAtendoFin" ) );
						
					
				}
				
				atualizaTituloGridAtendimentos();
				
				rs.close();
				ps.close();

			} 
			catch ( SQLException err ) {
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao carregar tabela de atendimento!\n" + err.getMessage(), true, con, err );
			}
		}
	}

	private ResultSet executaQueryChamados() {

		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();

		try {

			sql.append( "select ch.codcli, ch.dtchamado, ch.prioridade, ch.codchamado, ch.descchamado, ( ch.codcli || ' ' || rtrim(cl.nomecli) || ' ' || (coalesce(ch.solicitante,'')) ) cliente, ch.codcli, ch.solicitante, ate.NOMEATEND as designado," );
			sql.append( "ch.status, ch.qtdhorasprevisao, ch.dtprevisao, ch.dtconclusao, tc.desctpchamado, coalesce(ch.ematendimento,'N') ematendimento, " );
			sql.append( "(ch.idusualt || ' desde ' || substring(cast( ch.halt as char(20)) from 1 for 5)) dados_atendimento, ch.detchamado, " );
			sql.append( "'' as tipo_atendimento " );
			sql.append( "from crchamado ch, ATATENDENTE ate, crtipochamado tc, vdcliente cl " );
			sql.append( "where tc.codemp=ch.codemptc and tc.codfilial=ch.codfilialtc and tc.codtpchamado=ch.codtpchamado and ate.CODATEND = ch.CODATEND " );
			sql.append( "and ate.CODEMP = ch.codempae and ate.CODFILIAL = ch.codfilialae and ch.codemp=? and ch.codfilial=? " );
			sql.append( " and cl.codemp=ch.codempcl and cl.codfilial=ch.codfilialcl and cl.codcli=ch.codcli ");

			// Se deve utilizar todos os filtros

			if ( "N".equals( cbEmAtendimento.getVlrString() ) ) {
				sql.append( "and dtchamado between ? and ? " );

				if ( txtCodCli.getVlrInteger() > 0 ) {
					sql.append( " and ch.codempcl=? and ch.codfilialcl=? and ch.codcli=? " );
				}
				if ( cbTpChamado.getVlrInteger() > 0 ) {
					sql.append( " and ch.codemptc=? and ch.codfilialtc=? and tc.codtpchamado=? " );
				}

				// Verifica os status selecionados

				boolean primeiro = true;

				for ( int i = 0; i < tabstatus.getNumLinhas(); i++ ) {

					if ( (Boolean) tabstatus.getValor( i, 0 ) ) {

						if ( primeiro ) {
							sql.append( " and ch.status in (" );
						}
						else {
							sql.append( "," );
						}

						sql.append( "'" + tabstatus.getValor( i, 1 ) + "'" );

						primeiro = false;
					}

					if ( i == tabstatus.getNumLinhas() - 1 && !primeiro ) {
						sql.append( " ) " );
					}

				}

				boolean prioridade1 = true;

				for ( int i = 0; i < tabsprioridade.getNumLinhas(); i++ ) {

					if ( (Boolean) tabsprioridade.getValor( i, 0 ) ) {

						if ( prioridade1 ) {
							sql.append( " and ch.prioridade in (" );
						}
						else {
							sql.append( "," );
						}

						sql.append( "'" + tabsprioridade.getValor( i, 1 ) + "'" );

						prioridade1 = false;
						primeiro = false;
					}

					if ( i == tabsprioridade.getNumLinhas() - 1 && !primeiro ) {
						sql.append( " ) " );
					}

				}

				if ( cbPrioridade.getVlrInteger() > 0 ) {
					sql.append( " and ch.prioridade=? " );
				}
				if ( txtCodAtendenteChamado.getVlrInteger() > 0 ) {
					sql.append( " and ch.codempae=? and ch.codfilialae=? and ch.codatend=? " );
				}

			}
			// Se deve filtrar apenas os em atendimento.
			else {
				sql.append( " and ch.ematendimento='S' " );
			}

			sql.append( "order by ch.prioridade, ch.dtprevisao , ch.descchamado, ch.dtchamado, ch.status, ch.dtconclusao " );

			try {
				int param = 1;

				PreparedStatement ps = con.prepareStatement( sql.toString() );
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "CRCHAMADO" ) );

				// Se deve utilizar todos os filtros

				if ( "N".equals( cbEmAtendimento.getVlrString() ) ) {

					ps.setDate( param++, Funcoes.dateToSQLDate( txtDatainiCham.getVlrDate() ) );
					ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafimCham.getVlrDate() ) );

					if ( txtCodCli.getVlrInteger() > 0 ) {
						ps.setInt( param++, lcCli.getCodEmp() );
						ps.setInt( param++, lcCli.getCodFilial() );
						ps.setInt( param++, txtCodCli.getVlrInteger() );
					}

					if ( cbTpChamado.getVlrInteger() > 0 ) {

						ps.setInt( param++, Aplicativo.iCodEmp );
						ps.setInt( param++, ListaCampos.getMasterFilial( "CRTIPOCHAMADO" ) );
						ps.setInt( param++, cbTpChamado.getVlrInteger() );

					}

					if ( cbPrioridade.getVlrInteger() > 0 ) {
						ps.setInt( param++, cbPrioridade.getVlrInteger() );
					}

					if ( txtCodAtendenteChamado.getVlrInteger() > 0 ) {
						ps.setInt( param++, Aplicativo.iCodEmp );
						ps.setInt( param++, ListaCampos.getMasterFilial( "ATATENDENTE" ) );
						ps.setInt( param++, txtCodAtendenteChamado.getVlrInteger() );

					}

				}

				rs = ps.executeQuery();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return rs;

	}

	private void carregaChamados() {

		StringBuilder sql = new StringBuilder();
		
		total_horas_chamados = new BigDecimal(0);

		if ( carregagrid ) {

			try {

				ResultSet rs = executaQueryChamados();

				tabchm.limpa();

				int row = 0;

				for ( int i = 0; rs.next(); i++ ) {
					
				
					tabchm.adicLinha();
					
					vCodChamados.add("" + rs.getString("CodChamado") );
					
					tabchm.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( GridChamado.DTCHAMADO.name() ) ), i, GridChamado.DTCHAMADO.ordinal() );
					tabchm.setValor( rs.getInt( GridChamado.PRIORIDADE.name() ), i, GridChamado.PRIORIDADE.ordinal() );
					tabchm.setValor( rs.getInt( GridChamado.CODCHAMADO.name() ), i, GridChamado.CODCHAMADO.ordinal() );
					tabchm.setValor( rs.getString( GridChamado.DESCCHAMADO.name() ), i, GridChamado.DESCCHAMADO.ordinal() );
					tabchm.setValor( rs.getString( GridChamado.DESIGNADO.name() ), i, GridChamado.DESIGNADO.ordinal() );
					
					imgColuna = Chamado.getImagem( rs.getString( "status" ), Chamado.IMG_TAMANHO_M );

					tabchm.setValor( imgColuna, row, GridChamado.STATUS.ordinal() );
					tabchm.setValor( rs.getBigDecimal( GridChamado.QTDHORASPREVISAO.name() ), i, GridChamado.QTDHORASPREVISAO.ordinal() );
					tabchm.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( GridChamado.DTPREVISAO.name() ) ), i, GridChamado.DTPREVISAO.ordinal() );
					tabchm.setValor( rs.getString( GridChamado.DESCTPCHAMADO.name() ), i, GridChamado.DESCTPCHAMADO.ordinal() );
					tabchm.setValor( rs.getString( GridChamado.CLIENTE.name() ), i, GridChamado.CLIENTE.ordinal() );
					tabchm.setValor( rs.getInt( GridChamado.CODCLI.name() ), i, GridChamado.CODCLI.ordinal() );
				
										
					// Se chamado estiver em atendimento

					if ( rs.getString( "ematendimento" ).equals( "S" ) ) {
						tabchm.setValor( chamado_em_atendimento, i, GridChamado.EM_ATENDIMENTO.ordinal() );
						tabchm.setValor( rs.getString( GridChamado.DADOS_ATENDIMENTO.name() ), i, GridChamado.DADOS_ATENDIMENTO.ordinal() );
						tabchm.setValor( rs.getString( GridChamado.TIPO_ATENDIMENTO.name() ), i, GridChamado.TIPO_ATENDIMENTO.ordinal() );
					}
					else {
						tabchm.setValor( chamado_parado, i, GridChamado.EM_ATENDIMENTO.ordinal() );
						tabchm.setValor( "", i, GridChamado.DADOS_ATENDIMENTO.ordinal() );
						tabchm.setValor( "", i, GridChamado.TIPO_ATENDIMENTO.ordinal() );
					}
					
					tabchm.setValor( rs.getString( GridChamado.DETCHAMADO.name() ), i, GridChamado.DETCHAMADO.ordinal() );
					
					total_horas_chamados = total_horas_chamados.add(rs.getBigDecimal( GridChamado.QTDHORASPREVISAO.name() ) );
					
					row++;

				}
				
				atualizaTituloGridChamados();
				

				rs.close();

				// Permitindo reordena��o
				if ( row > 0 ) {
					RowSorter<TableModel> sorter = new TableRowSorter<TableModel>( tabchm.getModel() );

					tabchm.setRowSorter( sorter );
				}
				else {
					tabchm.setRowSorter( null );
				}

			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao carregar tabela de chamados!\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			}
		}
	}
	
	private void atualizaTituloGridChamados() {
		
		String titulo = tabchm.getNumLinhas() + " Chamados - " + Funcoes.bdToStr( total_horas_chamados ) + " horas previstas ";
		scpChm.setBorder( SwingParams.getPanelLabel( titulo, Color.BLUE, TitledBorder.CENTER ) );
		
	}
	
	private void atualizaTituloGridAtendimentos() {
		
		String titulo = tabatd.getNumLinhas() + " Atendimentos - " + Funcoes.longTostrTimeHoras( total_horas_atend ) + " horas ";
		scpAtd.setBorder( SwingParams.getPanelLabel( titulo, Color.BLUE, TitledBorder.CENTER ) );

	}

	private void excluiAtend() {
		StringBuilder sql = new StringBuilder();
		if ( Funcoes.mensagemConfirma( this, "Confirma a exclus�o deste atendimento?" ) == JOptionPane.YES_OPTION ) {

			if ( tabatd.getLinhaSel() == -1 ) {
				Funcoes.mensagemInforma( this, "Selecione um item na lista!" );
				return;
			}
			try {
				sql.append( "DELETE FROM ATATENDIMENTO WHERE CODATENDO=? AND CODEMP=? AND CODFILIAL=?" );
				PreparedStatement ps = con.prepareStatement( sql.toString() );
				ps.setString( 1, "" + vCodAtends.elementAt( tabatd.getLinhaSel() ) );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
				ps.execute();
				ps.close();
				con.commit();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao salvar o atendimento!\n" + err.getMessage(), true, con, err );
			}
			carregaAtendimentos();
		}
	}
	
	private void excluiChm() {
		StringBuilder sql = new StringBuilder();
		if ( Funcoes.mensagemConfirma( this, "Confirma a exclus�o deste Chamado?" ) == JOptionPane.YES_OPTION ) {

			if ( tabchm.getLinhaSel() == -1 ) {
				Funcoes.mensagemInforma( this, "Selecione um item na lista!" );
				return;
			}
			try {
				sql.append( "DELETE FROM CRCHAMADO WHERE CODCHAMADO=? AND CODEMP=? AND CODFILIAL=?" );
				PreparedStatement ps = con.prepareStatement( sql.toString() );
				ps.setString( 1, "" + vCodChamados.elementAt( tabchm.getLinhaSel() ) );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "CRCHAMADO" ) );
				ps.execute();
				ps.close();
				con.commit();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao salvar o chamado!\n" + err.getMessage(), true, con, err );
			}
			carregaChamados();
		}
	}

	private void novoAtend( Integer codchamado, Integer codcli ) {

		Object ORets[];

		DLAtendimento dl = null;

		if ( txtCodRec.getVlrInteger() > 0 && txtNParcItRec.getVlrInteger() > 0 ) {
			dl = new DLAtendimento( txtCodCli.getVlrInteger().intValue(), null, this, con, false, tipoatendo, txtCodRec.getVlrInteger(), txtNParcItRec.getVlrInteger(), financeiro );
		}
		else {

			if ( txtCodCli.getVlrInteger() > 0 ) {
				codcli = txtCodCli.getVlrInteger();
			}

			if ( codcli == null ) {
				codcli = new Integer( 0 );
			}

			dl = new DLAtendimento( codcli.intValue(), codchamado, this, con, false, tipoatendo, financeiro );
		}
		
		dl.setModal( false );

		dl.setVisible( true );
		
	}

	private void novoChamado() {
		try {

			FChamado chamado = null;
			Integer codcli = null;

			if ( Aplicativo.telaPrincipal.temTela( FChamado.class.getName() ) ) {
				chamado = (FChamado) Aplicativo.telaPrincipal.getTela( FChamado.class.getName() );
			}
			else {
				chamado = new FChamado();
				Aplicativo.telaPrincipal.criatela( "Chamado", chamado, con );
			}

			if(txtCodCli.getVlrInteger()<1) {

				if( tpnAbas.getSelectedIndex() == ABA_CHAMADO) {

					codcli = (Integer) tabchm.getValor( tabchm.getSelectedRow(), GridChamado.CLIENTE.ordinal() );

				}
				else if( tpnAbas.getSelectedIndex() == ABA_ATENDIMENTO) {

					codcli = (Integer) tabatd.getValor( tabatd.getSelectedRow(), GridAtendimento.CODCLI.ordinal() );

				}

			}
			else {
				codcli = txtCodCli.getVlrInteger();
			}


			chamado.novo();

			chamado.setCodCli( codcli );


		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcAtendenteAtendimento ) {
			carregaAtendimentos();
		}
		if ( cevt.getListaCampos() == lcAtendenteChamado ) {
			carregaChamados();
		}
		if ( cevt.getListaCampos() == lcChamado ) {
			carregaAtendimentos();
		}

		else if ( cevt.getListaCampos() == lcCli ) {
			carregaAtendimentos();
			HashMap<String, Vector<Object>> vals = FuncoesCRM.montaComboContr( con, txtCodCli.getVlrInteger(), "<Todos>", false );
			cbContr.setItensGeneric( (Vector<?>) vals.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
			carregaChamados();
			
			if(prefere == null) {
				prefere = getPrefere();
			}
			
			if( (Boolean) prefere.get( "MOSTRACLIATRASO" ) && emAtraso() ) {
				
				lbStatus.setText( "Atraso!" );
				lbStatus.setBackground( Color.RED );
								
			}
			else if( (Boolean) prefere.get( "MOSTRACLIATRASO" )){
	
				lbStatus.setText( "Normal" );
				lbStatus.setBackground( SwingParams.getVerdeFreedom() );
				
			}
			else {
				
				lbStatus.setText( "" );
				lbStatus.setBackground( Color.LIGHT_GRAY );
				
			}
			
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}
	
	private void primeiro(JTablePad panel) {

		if ( ( panel != null ) & ( panel.getNumLinhas() > 0 ) )
			panel.setLinhaSel( 0 );
	}

	private void anterior(JTablePad panel) {

		int iLin = 0;
		if ( ( panel != null ) & ( panel.getNumLinhas() > 0 ) ) {
			iLin = panel.getLinhaSel();
			if ( iLin > 0 )
				panel.setLinhaSel( iLin - 1 );
		}
	}

	private void proximo(JTablePad panel) {

		int iLin = 0;
		if ( ( panel != null ) & ( panel.getNumLinhas() > 0 ) ) {
			iLin = panel.getLinhaSel();
			if ( iLin < ( panel.getNumLinhas() - 1 ) )
				panel.setLinhaSel( iLin + 1 );
		}
	}

	private void ultimo(JTablePad panel) {

		if ( ( panel != null ) & ( panel.getNumLinhas() > 0 ) )
			panel.setLinhaSel( panel.getNumLinhas() - 1 );
	}


    public void actionPerformed( ActionEvent evt ) {

	
        if ( evt.getSource() == btSair ) {
        	dispose();
        }
    	else if ( evt.getSource() == btPrimeiro ) {
    		primeiro(tabchm);
    	}
		else if ( evt.getSource() == btAnterior ) {
			anterior(tabchm);
		}
		else if ( evt.getSource() == btProximo ) {
			proximo(tabchm);
		}
		else if ( evt.getSource() == btUltimo ) {
			ultimo(tabchm);
		}
    	else if ( evt.getSource() == btPrimeiroAtd ) {
    		primeiro(tabatd );
    	}
		else if ( evt.getSource() == btAnteriorAtd ) {
			anterior(tabatd);
		}
		else if ( evt.getSource() == btProximoAtd ) {
			proximo(tabatd);
		}
		else if ( evt.getSource() == btUltimoAtd ) {
			ultimo(tabatd);
		}
        else if ( evt.getSource() == btNovoAtendimento ) {

                int linhasel = tabchm.getLinhaSel();

                if ( linhasel > -1 ) {
                        novoAtend( (Integer) tabchm.getValor( linhasel, GridChamado.CODCHAMADO.ordinal() ), (Integer) tabchm.getValor( linhasel, GridChamado.CODCLI.ordinal() ) );
                }
                else {
                        novoAtend( null, null );
                }

        }
        else if ( evt.getSource() == btNovoChamado ) {
                novoChamado();                  
        }
        else if ( evt.getSource() == btEditar){
        	
        	int linhasel = tabchm.getLinhaSel();
        	
        	if(linhasel > -1 ){
            	visualizaCham();
        	}        	
        	else {
        		Funcoes.mensagemInforma( this, "Nenhum chamado selecionado!" ); 
        	}

        }
        else if ( evt.getSource() == btEditarAtd ){
        	int linhasel = tabatd.getLinhaSel();
        	
        	if(linhasel > -1 ){
        		visualizaAtend();
        	}
        	else {
        		Funcoes.mensagemInforma( this, "Nenhum atendimento selecionado!" ); 
        	}
        	
        }
        else if ( evt.getSource() == btExcluir ) {
                excluiChm();
        }
        else if ( evt.getSource() == btExcluirAtd  ) {
            	excluiAtend();
        }
        else if ( evt.getSource() == btImprimir ) {
// 		 else if ( tpnAbas.getSelectedIndex() == ABA_CHAMADO ) {

            imprimiGraficoChamado( executaQueryChamados(), true );

        }
        
        else if ( evt.getSource() == btImprimirAtd) { 
            try {
                    FRAtendimentos tela = FRAtendimentos.class.newInstance();
                    tela.setParametros( txtCodCli.getVlrInteger(), txtDatainiAtend.getVlrDate(), txtDatafimAtend.getVlrDate() );
                    Aplicativo.telaPrincipal.criatela( "", tela, con );

            } catch ( Exception e ) {
                    e.printStackTrace();
            }
    }
        else if ( evt.getSource() == btAtualizaChamados ) {
                carregaChamados();
        }
        else if ( evt.getSource() == btAtualizaAtendimentos ) {
                carregaAtendimentos();
        }
}
	
	private void montaComboTipoChamado() {

		cbTpChamado.setAutoSelect( "codtpchamado", "desctpchamado", "crtipochamado" );
		cbTpChamado.carregaValores();
	}

	private void imprimiGraficoChamado( final ResultSet rs, final boolean bVisualizar ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/" );
		hParam.put( "CODCLI", txtCodCli.getVlrInteger() );
		hParam.put( "DTINI", txtDatainiCham.getVlrDate() );
		hParam.put( "DTFIM", txtDatafimCham.getVlrDate() );

		if ( txtCodCli.getVlrInteger().intValue() > 0 ) {
			hParam.put( "CLIENTE", txtCodCli.getVlrString().trim() + "-" + txtRazCli.getVlrString().trim() );
		}
		else {
			hParam.put( "CLIENTE", "DIVERSOS" );
		}

		dlGr = new FPrinterJob( "relatorios/chamados.jasper", "RELAT�RIO DE CHAMADOS", "", rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				err.printStackTrace();
			}
		}
	}

	private void montaComboTipoAtend() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CODTPATENDO,DESCTPATENDO FROM ATTIPOATENDO WHERE CODEMP=? AND CODFILIAL=? AND TIPOATENDO=? ORDER BY 2";
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
			ps.setString( 3, tipoatendo );

			rs = ps.executeQuery();

			vValsTipo.clear();
			vLabsTipo.clear();

			vValsTipo.addElement( -1 );
			vLabsTipo.addElement( "<Todos>" );

			while ( rs.next() ) {
				vValsTipo.addElement( new Integer( rs.getInt( "CodTpAtendo" ) ) );
				vLabsTipo.addElement( rs.getString( "DescTpAtendo" ) );
			}
			cbTipoAtend.setItensGeneric( vLabsTipo, vValsTipo );
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar os tipos de atendimento!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			carregagrid = true;
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcAtendimento.setConexao( cn );
		lcAtendenteAtendimento.setConexao( cn );
		lcAtendenteChamado.setConexao( cn );
		lcRec.setConexao( cn );
		lcItRec.setConexao( cn );
		lcChamado.setConexao( cn );
		lcEspecAltend.setConexao( cn );
		
		montaComboTipoAtend();
		montaComboTipoChamado();

		Integer codatend_atual = Atendimento.buscaAtendente();
		
		if ( codatend_atual != null ) {
			
			txtCodAtendenteAtendimento.setVlrInteger( codatend_atual );
			txtCodAtendenteChamado.setVlrInteger( codatend_atual );
			lcAtendenteAtendimento.carregaDados();
			lcAtendenteChamado.carregaDados();
			
		}


	}

	public void focusGained( FocusEvent arg0 ) {

	}

	public void focusLost( FocusEvent fevt ) {

		if ( fevt.getSource() == txtDatafimAtend ) {
			carregaAtendimentos();
		}
		else if ( fevt.getSource() == txtDatafimCham ) {
			carregaChamados();
		}

	}

	public void valorAlterado( JComboBoxEvent evt ) {

		if ( evt.getComboBoxPad() == cbContr ) {
			if ( cbContr.getVlrInteger() > 0 ) {
				HashMap<String, Vector<Object>> vals = FuncoesCRM.montaComboItContr( con, cbContr.getVlrInteger(), "<Todos>" );
				cbitContr.setItensGeneric( (Vector<?>) vals.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
			}
			else {
				cbitContr.limpa();
				vValsitContr.addElement( -1 );
				vLabsitContr.addElement( "<Todos>" );
				cbitContr.setItensGeneric( vLabsitContr, vValsitContr );
				carregaAtendimentos();
			}
		}
		else if ( evt.getComboBoxPad() == cbitContr ) {
			carregaAtendimentos();
		}
		else if ( evt.getComboBoxPad() == cbTipoAtend ) {
			carregaAtendimentos();
		}
		else if ( evt.getComboBoxPad() == cbTpChamado ) {
			carregaChamados();
		}
		else if ( evt.getComboBoxPad() == cbPrioridade ) {
			carregaChamados();
		}

	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
			if ( ( kevt.getSource() == txtCodCli ) && ( txtCodCli.getVlrInteger() == 0 ) ) {
				carregaAtendimentos();
			}
			else if ( ( kevt.getSource() == txtCodAtendenteAtendimento ) && ( txtCodAtendenteAtendimento.getVlrInteger() == 0 ) ) {
				carregaAtendimentos();
			}
		}
	}

	public void keyReleased( KeyEvent arg0 ) {

	}

	public void keyTyped( KeyEvent arg0 ) {

	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( cevt.getSource() == tpnAbas ) {
			if ( tpnAbas.getSelectedIndex() == ABA_ATENDIMENTO ) {
				carregaAtendimentos();
			}
			else if ( tpnAbas.getSelectedIndex() == ABA_CHAMADO ) {
				carregaChamados();
			}
		}

	}

	public void mouseClicked( MouseEvent mevt ) {

		if ( mevt.getSource() == tabatd && mevt.getClickCount() == 2 && mevt.getModifiers() == MouseEvent.BUTTON1_MASK ) {
			visualizaAtend();
		}
		else if ( mevt.getSource() == tabchm && mevt.getClickCount() == 1 ) {
			
			
			
			if (mevt.getModifiers() == MouseEvent.BUTTON3_MASK && tabchm.getLinhaSel()>-1) {

				dlMensagem.setVisible(true);
				txtDialog.setText( (String) tabchm.getValor( tabchm.getLinhaSel(), GridChamado.DETCHAMADO.ordinal() ) );
				
			}
			else if(tabchm.getLinhaSel()>-1) { 
			
				txtCodChamado.setVlrInteger( (Integer) tabchm.getValor( tabchm.getLinhaSel(), GridChamado.CODCHAMADO.ordinal() ) );
				txtCodAtendenteAtendimento.setVlrInteger( txtCodAtendenteChamado.getVlrInteger() );
				txtDatainiAtend.setVlrString( "" );
				txtDatafimAtend.setVlrString( "" );
				lcAtendenteAtendimento.carregaDados();
				lcChamado.carregaDados();
				
				
			}
			
		}
		else if ( mevt.getSource() == tabchm && mevt.getClickCount() == 2  && mevt.getModifiers() == MouseEvent.BUTTON1_MASK ) {
			visualizaCham();
		}

	}
	
	public void criaTelaMensagem() {
		
		try {
			
			dlMensagem.setSize(300, 150);
			dlMensagem.setLocationRelativeTo(dlMensagem);
			dlMensagem.setTitle("");
			 
			txtDialog.setBackground( Color.YELLOW );			
			txtDialog.setBorder( null );
			
			Container c = dlMensagem.getContentPane();
			
			txtDialog.setEditable(false);
			
			JScrollPane spnTxt = new JScrollPane(txtDialog);
			
			c.setLayout(new BorderLayout());		
			c.add(spnTxt, BorderLayout.CENTER);
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mouseEntered( MouseEvent mevt ) {

	}

	public void mouseExited( MouseEvent arg0 ) {

		// TODO Auto-generated method stub

	}

	public void mousePressed( MouseEvent arg0 ) {

		// TODO Auto-generated method stub

	}

	public void mouseReleased( MouseEvent mevt ) {

	}

	private boolean emAtraso() {

		boolean bRetorno = true;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			// Se for devolu��o n�o deve verificar parcelas em aberto...

			String sSQL = "SELECT SRETORNO FROM FNCHECAPGTOATRASOSP(?,?,?)";

			ps = con.prepareStatement( sSQL );
			
			ps.setInt( 1, txtCodCli.getVlrInteger() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, Aplicativo.iCodFilial );
			
			rs = ps.executeQuery();

			if ( rs.next() ) {
				
				bRetorno = "S".equals(  rs.getString( "SRETORNO" ) );
				
			}
			else {
				Funcoes.mensagemErro( this, "N�o foi poss�vel checar os pagamentos do cliente!" );
			}

			rs.close();
			ps.close();

			con.commit();

		} 
		catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "N�o foi poss�vel verificar os pagamentos do cliente!\n" + err.getMessage(), true, con, err );
		}

		return bRetorno;

	}
	
	private HashMap<String, Object> getPrefere() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "select MOSTRACLIATRASO, BLOQATENDCLIATRASO FROM sgprefere3 WHERE CODEMP=? AND CODFILIAL=? ";
		
		HashMap<String, Object> ret = new HashMap<String, Object>();
		
		try {
			
			con = Aplicativo.getInstace().getConexao();
			ps = con.prepareStatement( sSQL );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );

			rs = ps.executeQuery();

			vValsTipo.clear();
			vLabsTipo.clear();

			vValsTipo.addElement( -1 );
			vLabsTipo.addElement( "<Todos>" );

			while ( rs.next() ) {
				
				ret.put( "MOSTRACLIATRASO", "S".equals( rs.getString( "MOSTRACLIATRASO" ) ));
				ret.put( "BLOQATENDCLIATRASO", "S".equals( rs.getString( "MOSTRACLIATRASO" ) ));
				
			}
			
			rs.close();
			ps.close();
			
			con.commit();
		
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar preferencias!\n" + err.getMessage(), true, con, err );
		} 
		finally {
			ps = null;
			rs = null;
		}
		
		return ret;
		
	}

}
