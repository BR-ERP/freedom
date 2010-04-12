/*

 * Projeto: Freedom
 * Pacote: org.freedom.modules.crm
 * Classe: @(#)FPCP.java
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> 
 */

package org.freedom.modulos.pcp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.TabelaEditEvent;
import org.freedom.acao.TabelaEditListener;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.ListaCampos;
import org.freedom.library.swing.JButtonPad;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTabbedPanePad;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.modulos.std.FOrcamento;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLLoading;
import org.freedom.telas.FFilho;
import org.freedom.telas.SwingParams;


/**
 * Tela para planejamento mestre da produ��o.
 * 
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez
 * @version 03/12/2009
 */

public class FPMP extends FFilho implements ActionListener, TabelaSelListener, MouseListener, KeyListener, CarregaListener, TabelaEditListener, ChangeListener {

	// *** Vari�veis est�ticas
	
	private static final long serialVersionUID = 1L;	
	private static final Color GREEN = new Color( 45, 190, 60 );

	// *** Paineis tela
	
	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	private JPanelPad panelMaster = new JPanelPad( 700, 100 );
	private JPanelPad panelAbas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JTabbedPanePad tabbedAbas = new JTabbedPanePad();
	private JPanelPad panelSouth = new JPanelPad(30, 30 );	
	private JPanelPad panelLegenda = new JPanelPad(30, 30 );	
	private JPanelPad panelFiltros = new JPanelPad("Filtros", Color.BLUE);
	
	// *** Paineis Detalhamento
	
	private JPanelPad panelDet = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );		
	private JPanelPad panelTabDet = new JPanelPad( 700, 60 );
	private JPanelPad panelGridDet = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JPanelPad panelTabDetItens = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JTablePad tabDet = null;
	
	// *** Labels
	
	private JLabelPad sepdet = new JLabelPad();
	private JLabelPad sepagrup = new JLabelPad();
	
	// *** Paineis Agrupamento
	
	private JPanelPad panelAgrup = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );	
	private JPanelPad panelTabAgrup = new JPanelPad( 700, 60 );
	private JPanelPad panelGridAgrup = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JPanelPad panelTabAgrupItens = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JPanelPad pnCritAgrup = new JPanelPad("Crit�rios de agrupamento", Color.BLUE);
	private JTablePad tabAgrup = null;
		
	// *** Geral

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	private JButtonPad btBuscar = new JButtonPad( "Buscar", Icone.novo( "btExecuta.gif" ) );
	
	// *** Campos

	private JTextFieldFK txtQtdVendida = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );	
	private JTextFieldFK txtQtdEstoque = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );	
	private JTextFieldFK txtQtdReservado = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );
	private JTextFieldFK txtQtdProducao = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );
	private JTextFieldFK txtQtdProduzir = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	// ** Checkbox
	
	private JCheckBoxPad cbAgrupProd =  new JCheckBoxPad( "Produto", "S", "N" );
	private JCheckBoxPad cbAgrupDataAprov =  new JCheckBoxPad( "Data de aprova��o", "S", "N" );
	private JCheckBoxPad cbAgrupDataProd =  new JCheckBoxPad( "Data de produ��o", "S", "N" );
	private JCheckBoxPad cbAgrupCli =  new JCheckBoxPad( "Cliente", "S", "N" );
	private JCheckBoxPad cbPend =  new JCheckBoxPad( "Pendentes", "S", "N" );
	private JCheckBoxPad cbEmProd =  new JCheckBoxPad( "Em produ��o", "S", "N" );
	private JCheckBoxPad cbProd =  new JCheckBoxPad( "Produzidos", "S", "N" );
	
	// ** Legenda
	
	private ImageIcon imgPendente = Icone.novo( "clVencido.gif" );
	private ImageIcon imgProducao = Icone.novo( "clPagoParcial.gif" );
	private ImageIcon imgProduzido = Icone.novo( "clPago.gif" );
	private ImageIcon imgColuna = null;
	
	// *** Listacampos

	private ListaCampos lcCliente = new ListaCampos( this, "CL" );
	private ListaCampos lcProd = new ListaCampos( this );

	// *** Bot�es
	
	private JButtonPad btSelectAllDet = new JButtonPad( Icone.novo( "btTudo.gif" ) );
	private JButtonPad btDeselectAllDet = new JButtonPad( Icone.novo( "btNada.gif" ) );
	private JButtonPad btLimparGridDet = new JButtonPad( Icone.novo( "btVassoura.png" ) );
	private JButtonPad btSimulaAgrupamentoDet = new JButtonPad( Icone.novo( "btVassoura.png" ) );
	private JButtonPad btIniProdDet = new JButtonPad( Icone.novo( "btIniProd.png" ) );
	
	private JButtonPad btSelectAllAgrup = new JButtonPad( Icone.novo( "btTudo.gif" ) );
	private JButtonPad btDeselectAllAgrup = new JButtonPad( Icone.novo( "btNada.gif" ) );
	private JButtonPad btLimparGridAgrup = new JButtonPad( Icone.novo( "btVassoura.png" ) );
	private JButtonPad btSimulaAgrupamentoAgrup = new JButtonPad( Icone.novo( "btVassoura.png" ) );
	private JButtonPad btIniProdAgrup = new JButtonPad( Icone.novo( "btIniProd.png" ) );
	
	// Enums
	
	private enum DETALHAMENTO {
		MARCACAO,STATUS, CODOP, SEQOP, DATAAPROV, DTFABROP, DATAENTREGA, CODEMPOC, CODFILIALOC, CODORC, CODITORC, TIPOORC, CODCLI, RAZCLI, 
		CODEMPPD, CODFILIALPD, CODPROD, SEQEST, DESCPROD, QTDAPROV, QTDESTOQUE, QTDRESERVADO, QTDEMPROD, QTDAPROD 
	}
	
	private enum AGRUPAMENTO {
		MARCACAO,STATUS, DATAAPROV, DTFABROP, CODEMPCL, CODFILIALCL, CODCLI, RAZCLI, CODEMPPD, CODFILIALPD, CODPROD, SEQEST , DESCPROD, QTDESTOQUE, QTDRESERVADO, QTDEMPROD, QTDAPROD 
	}
	
	private enum PROCEDUREOP {
		  TIPOPROCESS, CODEMPOP, CODFILIALOP, CODOP, SEQOP, CODEMPPD, CODFILIALPD, CODPROD, CODEMPOC,  CODFILIALOC,  CODORC, TIPOORC, CODITORC, 
		  QTDSUGPRODOP, DTFABROP, SEQEST, CODEMPET, CODFILIALET, CODEST, AGRUPDATAAPROV, AGRUPDTFABROP, AGRUPCODCLI, CODEMPCL, CODFILIALCL, CODCLI, DATAAPROV,
		  CODEMPCP, CODFILIALCP, CODCOMPRA, CODITCOMPRA, JUSTFICQTDPROD, CODEMPPDENTRADA, CODFILIALPDENTRADA, CODPRODENTRADA, QTDENTRADA
	}
	
	public FPMP() {
		
		super( false );
		
		setTitulo( "Planejamento mestre da produ��o", this.getClass().getName() );
		setAtribos( 20, 20, 860, 600 );
		
    	int x = (int) (Aplicativo.telaPrincipal.dpArea.getSize().getWidth()-getWidth())/2;
    	int y = (int) (Aplicativo.telaPrincipal.dpArea.getSize().getHeight()-getHeight())/2;
    	
    	setLocation( x, y );
		
		montaListaCampos();
		criaTabelas();
		montaTela();				
		montaListeners();
		carregaValoresPadrao();
	
	}

	private void carregaValoresPadrao() {
		cbAgrupProd.setVlrString( "S" );
		cbAgrupProd.setEnabled( false );
		cbPend.setVlrString( "S" );
	}
	
	private void montaListaCampos() {
		
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProd.setWhereAdic( "TIPOPROD='F'" );
		txtCodProd.setTabelaExterna( lcProd );		
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		
		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCliente );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCliente.setReadOnly( true );
		lcCliente.montaSql( false, "CLIENTE", "VD" );	

	}
	
	private void montaListeners() {
		
		btSelectAllDet.addActionListener( this );
		btDeselectAllDet.addActionListener( this );
		btLimparGridDet.addActionListener( this );
		btIniProdDet.addActionListener( this );
		
		btSelectAllAgrup.addActionListener( this );
		btDeselectAllAgrup.addActionListener( this );
		btLimparGridAgrup.addActionListener( this );
		btIniProdAgrup.addActionListener( this );
	
		btBuscar.addActionListener( this );
	
		lcProd.addCarregaListener( this );
		lcCliente.addCarregaListener( this );
		
		tabDet.addTabelaSelListener( this );	
		tabDet.addMouseListener( this );	
		tabAgrup.addMouseListener( this );
		
	}

	private void montaTela() {
		
		getTela().add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelMaster, BorderLayout.NORTH );
		
		// ***** Cabe�alho
		
		panelMaster.adic( new JLabelPad( "C�d.Prod." ), 7, 0, 60, 20 );
		panelMaster.adic( txtCodProd, 7, 20, 60, 20 );
		panelMaster.adic( new JLabelPad( "Descri��o do produto" ), 70, 0, 340, 20 );
		panelMaster.adic( txtDescProd, 70, 20, 340, 20 );
		
		panelMaster.adic( new JLabelPad( "C�d.Cli." ), 7, 40, 60, 20 );
		panelMaster.adic( txtCodCli, 7, 60, 60, 20 );
		panelMaster.adic( new JLabelPad( "Raz�o social do cliente" ), 70, 40, 340, 20 );
		panelMaster.adic( txtRazCli, 70, 60, 340, 20 );
		
		panelFiltros.adic( cbPend, 4, 0, 100, 20 );
		panelFiltros.adic( cbEmProd, 4, 30, 100, 20 );
		panelFiltros.adic( cbProd, 114, 0, 100, 20 );
		
		panelMaster.adic( panelFiltros, 416, 0, 220, 82 );

		panelMaster.adic( btBuscar, 712, 10, 123, 30 );
		
//		***** Abas
		
		panelGeral.add( panelAbas, BorderLayout.CENTER );
		panelGeral.add( panelAbas);
		panelAbas.add( tabbedAbas );
		
		tabbedAbas.addTab( "Detalhamento", panelDet );
		tabbedAbas.addTab( "Agrupamento", panelAgrup );
		
		tabbedAbas.addChangeListener( this );
		
		
		// ***** Detalhamento
		
		panelDet.add( panelTabDet, BorderLayout.NORTH );
		panelDet.add( panelGridDet, BorderLayout.CENTER );		
		panelGridDet.add( panelTabDetItens );
		
		panelTabDet.adic( new JLabelPad( "Vendidas" ), 10, 5, 80, 20 );
		panelTabDet.adic( txtQtdVendida, 10, 25, 80, 20 );
		panelTabDet.adic( new JLabelPad( "Estoque" ), 93, 5, 80, 20 );
		panelTabDet.adic( txtQtdEstoque, 93, 25, 80, 20 );
		panelTabDet.adic( new JLabelPad( "Reservas" ), 176, 5, 80, 20 );
		panelTabDet.adic( txtQtdReservado, 176, 25, 80, 20 );		
		panelTabDet.adic( new JLabelPad( "Produ��o" ), 259, 5, 80, 20 );
		panelTabDet.adic( txtQtdProducao, 259, 25, 80, 20 );
		panelTabDet.adic( new JLabelPad( "Produzir" ), 342, 5, 80, 20 );
		panelTabDet.adic( txtQtdProduzir, 342, 25, 80, 20 );
		
		sepdet.setBorder( BorderFactory.createEtchedBorder() );
		panelTabDet.adic( sepdet, 433, 4, 2, 48 );		
		panelTabDet.adic( btIniProdDet, 443, 4, 48, 48 );
		
		panelTabDet.adic( btSelectAllDet, 743, 12, 30, 30 );
		panelTabDet.adic( btDeselectAllDet, 774, 12, 30, 30 );
		panelTabDet.adic( btLimparGridDet, 805, 12, 30, 30 );
		
		panelTabDetItens.add( new JScrollPane( tabDet ) );
		
		// ***** Agrupamento
		
		panelAgrup.add( panelTabAgrup, BorderLayout.NORTH );
		panelAgrup.add( panelGridAgrup, BorderLayout.CENTER );		
		panelGridAgrup.add( panelTabAgrupItens );		
			
		pnCritAgrup.adic( cbAgrupProd, 4, 0, 70, 20 );
		pnCritAgrup.adic( cbAgrupDataProd, 80, 0, 125, 20 );
		pnCritAgrup.adic( cbAgrupDataAprov, 206, 0, 135, 20 );
		pnCritAgrup.adic( cbAgrupCli, 339, 0, 90, 20 );		
		
		panelTabAgrup.adic(pnCritAgrup, 4, 0, 420, 53);

		sepagrup.setBorder( BorderFactory.createEtchedBorder() );
		panelTabAgrup.adic( sepagrup, 433, 4, 2, 48 );		
		panelTabAgrup.adic( btIniProdAgrup, 443, 4, 48, 48 );
		
		panelTabAgrup.adic( btSelectAllAgrup, 743, 12, 30, 30 );
		panelTabAgrup.adic( btDeselectAllAgrup, 774, 12, 30, 30 );
		panelTabAgrup.adic( btLimparGridAgrup, 805, 12, 30, 30 );

		panelTabAgrupItens.add( new JScrollPane( tabAgrup ) );
		
		// ***** Rodap�
		
		Color statusColor = new Color( 111, 106, 177 );
		Font statusFont = SwingParams.getFontpadmin(); 
		
		JLabelPad canceladas = new JLabelPad( "Pendentes" );
		canceladas.setForeground( statusColor );
		canceladas.setFont( statusFont );
		panelLegenda.adic( new JLabelPad( imgPendente ), 0, 5, 20, 15 );
		panelLegenda.adic( canceladas, 20, 5, 100, 15 );
		
		JLabelPad pedidos = new JLabelPad( "Em Produ��o" );
		pedidos.setForeground( statusColor );
		pedidos.setFont( statusFont );
		panelLegenda.adic( new JLabelPad( imgProducao ), 60, 5, 20, 15 );
		panelLegenda.adic( pedidos, 80, 5, 100, 15 );
		
		JLabelPad faturadas = new JLabelPad( "Produzidos" );
		faturadas.setForeground( statusColor );
		faturadas.setFont( statusFont );
		panelLegenda.adic( new JLabelPad( imgProduzido ), 130, 5, 20, 15 );		
		panelLegenda.adic( faturadas, 150, 5, 100, 15 );
		
		panelLegenda.setBorder( null );		
		
		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );
		panelSouth.add( adicBotaoSair());
		pnRod.add( panelLegenda, BorderLayout.CENTER );
				
	}
	
	private void criaTabelas() {
		
		// Tabela de detalhamento
		
		tabDet = new JTablePad();

		tabDet.adicColuna( "" );
		tabDet.adicColuna( "" );
		tabDet.adicColuna( "Cod.OP" );
		tabDet.adicColuna( "Seq.OP" );
		tabDet.adicColuna( "Dt.Aprov." );
		tabDet.adicColuna( "Dt.Prod." );
		tabDet.adicColuna( "Dt.Entrega" );
		tabDet.adicColuna( "codemp oc" );
		tabDet.adicColuna( "codfilial oc" );
		
		tabDet.adicColuna( "Orc." );
		tabDet.adicColuna( "It" );
		tabDet.adicColuna( "tipoorc" );
		tabDet.adicColuna( "Cli." );
		tabDet.adicColuna( "Raz�o social do cliente" );
		tabDet.adicColuna( "codemppd" );
		tabDet.adicColuna( "codfilialpd" );
		tabDet.adicColuna( "Prod." );		
		tabDet.adicColuna( "Estr." );
		tabDet.adicColuna( "Descri��o do produto" );
		tabDet.adicColuna( "Aprov." );		
		tabDet.adicColuna( "Estoque" );
		tabDet.adicColuna( "Reservado" );
		tabDet.adicColuna( "Produ��o" );
		tabDet.adicColuna( "Sugestao" );
		
		tabDet.setTamColuna( 15, DETALHAMENTO.MARCACAO.ordinal() );
		tabDet.setTamColuna( 10, DETALHAMENTO.STATUS.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.DATAAPROV.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.DTFABROP.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.DATAENTREGA.ordinal() );
		tabDet.setTamColuna( 30, DETALHAMENTO.CODORC.ordinal() );
		tabDet.setTamColuna( 15, DETALHAMENTO.CODITORC.ordinal() );		
		tabDet.setTamColuna( 40, DETALHAMENTO.CODCLI.ordinal() );
		tabDet.setTamColuna( 100, DETALHAMENTO.RAZCLI.ordinal() );
		tabDet.setTamColuna( 40, DETALHAMENTO.CODPROD.ordinal() );
		tabDet.setTamColuna( 100, DETALHAMENTO.DESCPROD.ordinal() );		
		tabDet.setTamColuna( 50, DETALHAMENTO.QTDAPROV.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDRESERVADO.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDESTOQUE.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDEMPROD.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDAPROD.ordinal() );

		tabDet.setColunaInvisivel( DETALHAMENTO.SEQEST.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.CODOP.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.SEQOP.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.CODEMPOC.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.CODFILIALOC.ordinal() );		
		tabDet.setColunaInvisivel( DETALHAMENTO.TIPOORC.ordinal() );		
		tabDet.setColunaInvisivel( DETALHAMENTO.CODEMPPD.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.CODFILIALPD.ordinal() );

		tabDet.setColunaEditavel( DETALHAMENTO.QTDAPROD.ordinal(), true );
		tabDet.setColunaEditavel( DETALHAMENTO.DTFABROP.ordinal(), true );
		
		// Tabela de Agrupamento
		
		tabAgrup = new JTablePad();
		
				
		tabAgrup.adicColuna( "" ); //MARCACAO
		tabAgrup.adicColuna( "" ); //STATUS
		tabAgrup.adicColuna( "Dt.Aprov." );//DATAAPROV
		tabAgrup.adicColuna( "Dt.Prod." );//DTFABROP
		tabAgrup.adicColuna( "codempcl" );//CODEMPCL
		tabAgrup.adicColuna( "codfilialcl" );//CODFILIALCL
		tabAgrup.adicColuna( "Cli." );//CODCLI
		tabAgrup.adicColuna( "Raz�o social do cliente" );//RAZCLI
		tabAgrup.adicColuna( "codemppd" );//CODEMPPD
		tabAgrup.adicColuna( "codfilialpd" );//CODFILIALPD
		tabAgrup.adicColuna( "Prod." );//CODPROD
		tabAgrup.adicColuna( "seqest" );//SEQEST
		tabAgrup.adicColuna( "Descri��o do produto" );//DESCPROD
		tabAgrup.adicColuna( "Estoque" );//QTDESTOQUE
		tabAgrup.adicColuna( "Reservado" );//QTDRESERVADO
		tabAgrup.adicColuna( "Produ��o" );//QTDEMPROD
		tabAgrup.adicColuna( "Produzir" );//QTDAPROD
		
		tabAgrup.setTamColuna( 15, AGRUPAMENTO.MARCACAO.ordinal() );
		tabAgrup.setTamColuna( 10, AGRUPAMENTO.STATUS.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.DTFABROP.ordinal() );
		tabAgrup.setTamColuna( 40, AGRUPAMENTO.CODPROD.ordinal() );
		tabAgrup.setTamColuna( 145, AGRUPAMENTO.DESCPROD.ordinal() );
//		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDAPROV.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDRESERVADO.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDESTOQUE.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDEMPROD.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDAPROD.ordinal() );
		
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.DATAAPROV.ordinal() );	
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.CODCLI.ordinal() );
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.RAZCLI.ordinal() );	
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.CODEMPPD.ordinal() );
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.CODFILIALPD.ordinal() );
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.SEQEST.ordinal() );
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.CODEMPCL.ordinal() );
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.CODFILIALCL.ordinal() );

		tabAgrup.setColunaEditavel( AGRUPAMENTO.DTFABROP.ordinal(), true );
		tabAgrup.setColunaEditavel( AGRUPAMENTO.QTDAPROD.ordinal(), true );		

		
	}
	
	private void montaGridDet() {
			
		try {

			StringBuilder sql = new StringBuilder();

			sql.append( "select ");
			
			sql.append( "oc.statusorc status, io.sitproditorc, io.dtaprovitorc dataaprov, ");
			sql.append( "cast('today' as date) dtfabrop, ");
			sql.append( "io.dtaprovitorc + coalesce(oc.prazoentorc,0) dataentrega, oc.codemp codempoc, ");
			sql.append( "oc.codfilial codfilialoc, oc.codorc, "); 
			sql.append( "io.coditorc, io.tipoorc ,cl.codcli, ");
			sql.append( "cl.razcli, io.codemppd, io.codfilialpd, pd.codprod, pe.seqest, ") ;
			sql.append( "pd.descprod, coalesce(io.qtdaprovitorc,0) qtdaprov, pi.codop, pi.seqop, ");
			
			sql.append( "sum(coalesce(sp.sldliqprod,0)) qtdestoque , ");
			
			sql.append( "sum(coalesce(op.qtdprevprodop,0)) qtdemprod ");
			
			sql.append( "from vdorcamento oc ");
			
			sql.append( "left outer join vditorcamento io on ");
			sql.append( "io.codemp=oc.codemp and io.codfilial=oc.codfilial and io.codorc=oc.codorc and io.tipoorc=oc.tipoorc ");
			
			sql.append( "left outer join vdcliente cl on ");
			sql.append( "cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli ");
			
			sql.append( "left outer join eqproduto pd on ");
			sql.append( "pd.codemp=io.codemppd and pd.codfilial=io.codfilialpd and pd.codprod=io.codprod ");
			
			sql.append( "left outer join ppop op on " );
			sql.append( "op.codemppd=pd.codemp and op.codfilial=pd.codfilial and op.codprod=pd.codprod and op.sitop in ('PE','BL') ");
			
			sql.append( "left outer join eqsaldoprod sp on ");
			sql.append( "sp.codemp=pd.codemp and sp.codfilial=pd.codfilial and sp.codprod=pd.codprod ");
			
			sql.append( "left outer join ppestrutura pe on ");
			sql.append( "pe.codemp=pd.codemp and pe.codfilial=pd.codfilial and pe.codprod=pd.codprod ");
			
			sql.append( "left outer join ppopitorc pi on ");
			sql.append( "pi.codempoc=io.codemp and pi.codfilialoc=io.codfilial and pi.codorc=io.codorc and pi.coditorc=io.coditorc and pi.tipoorc=io.tipoorc ");
			
			sql.append( "where oc.codemp=? and oc.codfilial=? and io.aprovitorc='S' and pd.tipoprod='F' ");
			
			StringBuffer status = new StringBuffer("");
			
			if("S".equals(cbPend.getVlrString())) {
				status.append( " 'PE' ");
			}
			if("S".equals(cbEmProd.getVlrString())) {
				if ( status.length() > 0 ) {
					status.append( "," );
				}
				status.append( "'EP'" );
			}
			if("S".equals(cbProd.getVlrString())) {
				if ( status.length() > 0 ) {
					status.append( "," );
				}
				status.append( "'PD'" );
			}

			if ( status.length() > 0 ) {
				sql.append( " and io.sitproditorc in (" );
				sql.append( status );
				sql.append( ") ");
			}
			else {
				sql.append( " and io.sitproditorc not in('PE','EP','PD') " );
			}
					 
			if(txtCodProd.getVlrInteger()>0) {
				sql.append( " and io.codemppd=? and io.codfilialpd=? and io.codprod=? " );				
			}
			if(txtCodCli.getVlrInteger()>0) {
				sql.append( " and oc.codempcl=? and oc.codfilialcl=? and oc.codcli=? " );				
			}
			
			sql.append(" group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20 ");
			
			System.out.println("SQL:" + sql.toString());
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			int iparam = 1;
			
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );

			if(txtCodProd.getVlrInteger()>0) {
				ps.setInt( iparam++, lcProd.getCodEmp() );
				ps.setInt( iparam++, lcProd.getCodFilial() );
				ps.setInt( iparam++, txtCodProd.getVlrInteger() );								
			}
			if(txtCodCli.getVlrInteger()>0) {
				ps.setInt( iparam++, lcCliente.getCodEmp() );
				ps.setInt( iparam++, lcCliente.getCodFilial() );
				ps.setInt( iparam++, txtCodCli.getVlrInteger() );								
			}	
			
			ResultSet rs = ps.executeQuery();		
			
			tabDet.limpa();
						
			int row = 0;
			
			BigDecimal totqtdaprov = new BigDecimal(0);
			BigDecimal totqtdestoq = new BigDecimal(0);
			BigDecimal totqtdreserv = new BigDecimal(0);
			BigDecimal totqtdemprod = new BigDecimal(0);
			BigDecimal totqtdaprod = new BigDecimal(0);

			sql = new StringBuilder();
			sql.append( "select coalesce(sum(io2.qtdaprovitorc),0) qtdreservado from ppopitorc oo, vditorcamento io2 where ");
			sql.append( "oo.codempoc=io2.codemp and oo.codfilialoc=io2.codfilial ");
			sql.append( "and oo.codorc=io2.codorc and oo.coditorc=io2.coditorc and oo.tipoorc=io2.tipoorc " );
			sql.append( "and io2.codemp=? and io2.codfilial=? " );
			sql.append( "and io2.codemppd=? and io2.codfilialpd=? and io2.codprod=? "); 
			sql.append( "and io2.sitproditorc='PD' and coalesce(io2.statusitorc,'PE')!='OV' " );
			
			ResultSet rs2 = null;		

			PreparedStatement ps2 = null;
			
			
			while ( rs.next() ) {
				
				tabDet.adicLinha();
				tabDet.setColColor( -1, DETALHAMENTO.DTFABROP.ordinal(), Color.WHITE, Color.RED );
				tabDet.setColColor( -1, DETALHAMENTO.QTDAPROD.ordinal(), Color.WHITE, Color.RED );
				
				ps2 = con.prepareStatement( sql.toString() );
				
				ps2.setInt( 1, rs.getInt( DETALHAMENTO.CODEMPOC.toString() ) );
				ps2.setInt( 2, rs.getInt( DETALHAMENTO.CODFILIALOC.toString() ) );
				ps2.setInt( 3, rs.getInt( DETALHAMENTO.CODEMPPD.toString() ) );
				ps2.setInt( 4, rs.getInt( DETALHAMENTO.CODFILIALPD.toString() ) );
				ps2.setInt( 5, rs.getInt( DETALHAMENTO.CODPROD.toString() ) );
				
				rs2 = ps2.executeQuery();		
		
				BigDecimal qtdreserv = new BigDecimal(0);
				
				if(rs2.next()) {
					qtdreserv = rs2.getBigDecimal( DETALHAMENTO.QTDRESERVADO.toString() ).setScale( Aplicativo.casasDec );
				}

				BigDecimal qtdaprov = rs.getBigDecimal( DETALHAMENTO.QTDAPROV.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdestoque = rs.getBigDecimal( DETALHAMENTO.QTDESTOQUE.toString() ).setScale( Aplicativo.casasDec );
				
				BigDecimal qtdemprod = rs.getBigDecimal( DETALHAMENTO.QTDEMPROD.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdaprod = rs.getBigDecimal( DETALHAMENTO.QTDAPROV.toString() ).setScale( Aplicativo.casasDec );
				
				
				totqtdestoq = qtdestoque; // N�o deve ser somado, pois varia no produto
				totqtdreserv = qtdreserv;
				totqtdemprod = qtdemprod; // N�o deve ser somado, pois vaira no produto
				
				if ( "PE".equals( rs.getString( "sitproditorc" ) ) ) {
					imgColuna = imgPendente;
					totqtdaprov = totqtdaprov.add( qtdaprov );
				}
				else if ( "EP".equals( rs.getString( "sitproditorc" ) ) ) {
					imgColuna = imgProducao;
					qtdaprod = new BigDecimal(0);
				}
				else if ( "PD".equals( rs.getString( "sitproditorc" ) ) ) {
					imgColuna = imgProduzido;
					qtdaprod = new BigDecimal(0);
				}
											
				tabDet.setValor( new Boolean(false), row, DETALHAMENTO.MARCACAO.ordinal() );
				tabDet.setValor( imgColuna, row, DETALHAMENTO.STATUS.ordinal() );
				tabDet.setValor( Funcoes.dateToStrDate( rs.getDate( DETALHAMENTO.DATAAPROV.toString() ) ), row, DETALHAMENTO.DATAAPROV.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODOP.toString().trim() ), row, DETALHAMENTO.CODOP.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.SEQOP.toString().trim() ), row, DETALHAMENTO.SEQOP.ordinal() );				
				tabDet.setValor( Funcoes.dateToStrDate( rs.getDate( DETALHAMENTO.DTFABROP.toString() ) ), row, DETALHAMENTO.DTFABROP.ordinal() );
				tabDet.setValor( Funcoes.dateToStrDate( rs.getDate( DETALHAMENTO.DATAENTREGA.toString() ) ), row, DETALHAMENTO.DATAENTREGA.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODEMPOC.toString() ), row, DETALHAMENTO.CODEMPOC.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODFILIALOC.toString() ), row, DETALHAMENTO.CODFILIALOC.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODORC.toString() ), row, DETALHAMENTO.CODORC.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODITORC.toString() ), row, DETALHAMENTO.CODITORC.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.TIPOORC.toString() ), row, DETALHAMENTO.TIPOORC.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODCLI.toString() ), row, DETALHAMENTO.CODCLI.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.RAZCLI.toString().trim() ), row, DETALHAMENTO.RAZCLI.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODEMPPD.toString() ), row, DETALHAMENTO.CODEMPPD.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODFILIALPD.toString() ), row, DETALHAMENTO.CODFILIALPD.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODPROD.toString() ), row, DETALHAMENTO.CODPROD.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.SEQEST.toString() ), row, DETALHAMENTO.SEQEST.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.DESCPROD.toString().trim() ), row, DETALHAMENTO.DESCPROD.ordinal() );
				tabDet.setValor( qtdaprov, row, DETALHAMENTO.QTDAPROV.ordinal() );		
				tabDet.setValor( qtdestoque, row, DETALHAMENTO.QTDESTOQUE.ordinal() );
				tabDet.setValor( qtdreserv, row, DETALHAMENTO.QTDRESERVADO.ordinal() );					
				tabDet.setValor( qtdemprod, row, DETALHAMENTO.QTDEMPROD.ordinal() );
				tabDet.setValor( qtdaprod , row, DETALHAMENTO.QTDAPROD.ordinal() );
				
				row++;
				
			}
			
			totqtdaprod = totqtdaprov;//(totqtdestoq.subtract( totqtdaprov )).add( totqtdreserv );
			totqtdaprod = totqtdaprod.subtract( totqtdestoq );
			totqtdaprod = totqtdaprod.add( totqtdreserv );
			
			if(totqtdaprod.floatValue()<0) {
				totqtdaprod = new BigDecimal( 0 );
			}
			
			txtQtdVendida.setVlrBigDecimal( totqtdaprov );
			
			if(txtCodProd.getVlrInteger()>0) {				
				txtQtdEstoque.setVlrBigDecimal( totqtdestoq );
				txtQtdReservado.setVlrBigDecimal( totqtdreserv );
				txtQtdProducao.setVlrBigDecimal( totqtdemprod );
				txtQtdProduzir.setVlrBigDecimal( totqtdaprod );
			}
			else {
				txtQtdEstoque.setVlrString( "-" );
				txtQtdProducao.setVlrString( "-");
				txtQtdProduzir.setVlrString( "-" );					
			}
			
			// Permitindo reordena��o
			if(row>0) {
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabDet.getModel());    
			
					/*	
				  sorter.addRowSorterListener(new RowSorterListener() {  
	                    public void sorterChanged(RowSorterEvent e) {  
	                    	 if(e.getType() == RowSorterEvent.Type.SORTED) {  
	                    		 System.out.println("teste");  
	                    	 }
	                    }  
	                });
	                
	                */

				   tabDet.setRowSorter(sorter);				   
				   
				
			}
			else {
				tabDet.setRowSorter( null );
			}
			
		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	private void montaGridAgrup() {
		
		try {

			if( "N".equals( cbAgrupProd.getVlrString() ) &&  
				 	"N".equals( cbAgrupCli.getVlrString() ) &&
				 	"N".equals( cbAgrupDataAprov.getVlrString() ) &&
				 	"N".equals( cbAgrupDataProd.getVlrString() ) ) {		
			
				Funcoes.mensagemInforma( this, "Deve haver ao menos um crit�rio de agrupamento!" );
				return;
			}
			
			StringBuilder sql = new StringBuilder();

			sql.append( "select ");
			 
			sql.append( "pd.codemp codemppd, pd.codfilial codfilialpd, pd.codprod, pd.descprod, coalesce(sp.sldliqprod,0) qtdestoque, ");
			sql.append( "sum(coalesce(po.qtdaprod,0)) qtdaprod, sum(coalesce(op.qtdprevprodop,0)) qtdemprod, ");
			sql.append( "(select first 1 pe.seqest from ppestrutura pe where pe.codemp=pd.codemp and pe.codfilial=pd.codfilial and pe.codprod=pd.codprod) seqest " );
				
			int igroup = 8; // Numero padr�o de campos
			
			if( "S".equals( cbAgrupDataAprov.getVlrString() ) ) {
				sql.append( ",io.dtaprovitorc dataaprov ");
				igroup++; // Indica que foi adicionado um novo campo que deve fazer parte do groupby
			}
			
			if( "S".equals( cbAgrupDataProd.getVlrString() ) ) {
				sql.append( ",po.dtfabrop "); 
				igroup++;// Indica que foi adicionado um novo campo que deve fazer parte do groupby
			}

			if( "S".equals( cbAgrupCli.getVlrString() ) ) {
				sql.append( ",cl.codemp codempcl, cl.codfilial codfilialcl, cl.codcli, cl.razcli ");
				igroup++;// Indica que foi adicionado um novo campo que deve fazer parte do group by
				igroup++;// Indica que foi adicionado um novo campo que deve fazer parte do group by
				igroup++;// Indica que foi adicionado um novo campo que deve fazer parte do group by
				igroup++;// Indica que foi adicionado um novo campo que deve fazer parte do group by
			}

			sql.append( "from vdorcamento oc left outer join vditorcamento io on ");
			sql.append( "io.codemp=oc.codemp and io.codfilial=oc.codfilial and io.codorc=oc.codorc and io.tipoorc=oc.tipoorc ");
			sql.append( "left outer join vdcliente cl on cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli ");
			sql.append( "left outer join eqproduto pd on pd.codemp=io.codemppd and pd.codfilial=io.codfilialpd and pd.codprod=io.codprod ");
			sql.append( "left outer join ppop op on op.codemppd=pd.codemp and op.codfilial=pd.codfilial and op.codprod=pd.codprod and op.sitop in ('PE','BL') ");
			sql.append( "left outer join eqsaldoprod sp on sp.codemp=pd.codemp and sp.codfilial=pd.codfilial and sp.codprod=pd.codprod ");
			sql.append( "inner join ppprocessaoptmp po on ");
			sql.append( "po.codemp=io.codemp and po.codfilial=io.codfilial and po.codorc=io.codorc and po.tipoorc=io.tipoorc and po.coditorc=io.coditorc ");			

			sql.append( "where oc.codemp=? and oc.codfilial=? and io.aprovitorc='S' and io.sitproditorc='PE' and pd.tipoprod='F' ");
			
			if(txtCodProd.getVlrInteger()>0) {
				sql.append( " and io.codemppd=? and io.codfilialpd=? and io.codprod=? " );				
			}
			if(txtCodCli.getVlrInteger()>0) {
				sql.append( " and oc.codempcl=? and oc.codfilialcl=? and oc.codcli=? " );				
			}

			if( "S".equals( cbAgrupProd.getVlrString() ) ||  
			 	"S".equals( cbAgrupCli.getVlrString() ) ||
			 	"S".equals( cbAgrupDataAprov.getVlrString() ) ||
			 	"S".equals( cbAgrupDataProd.getVlrString() ) ) {			
			
				sql.append( "group by ");	
			
				if( "S".equals( cbAgrupProd.getVlrString() ) ) {
					sql.append( "1, 2, 3, 4, 5 ");				
				}
			
				if( igroup>8 )  {				
					for(int i=8; i<igroup; i++) {
						sql.append( "," + (i+1) );
					}
				}

			}
			
			System.out.println("SQL:" + sql.toString());
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			int iparam = 1;
			
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );

			if(txtCodProd.getVlrInteger()>0) {
				ps.setInt( iparam++, lcProd.getCodEmp() );
				ps.setInt( iparam++, lcProd.getCodFilial() );
				ps.setInt( iparam++, txtCodProd.getVlrInteger() );								
			}
			if(txtCodCli.getVlrInteger()>0) {
				ps.setInt( iparam++, lcCliente.getCodEmp() );
				ps.setInt( iparam++, lcCliente.getCodFilial() );
				ps.setInt( iparam++, txtCodCli.getVlrInteger() );								
			}	
			
			ResultSet rs = ps.executeQuery();		
			
			tabAgrup.limpa();
						
			int row = 0;
			
//			BigDecimal totqtdaprov = new BigDecimal(0);
			BigDecimal totqtdestoq = new BigDecimal(0);
			BigDecimal totqtdemprod = new BigDecimal(0);
			BigDecimal totqtdaprod = new BigDecimal(0);			
			
			
			sql = new StringBuilder();
			sql.append( "select coalesce(sum(io2.qtdaprovitorc),0) qtdreservado from ppopitorc oo, vditorcamento io2 where ");
			sql.append( "oo.codempoc=io2.codemp and oo.codfilialoc=io2.codfilial ");
			sql.append( "and oo.codorc=io2.codorc and oo.coditorc=io2.coditorc and oo.tipoorc=io2.tipoorc " );
			sql.append( "and io2.codemp=? and io2.codfilial=? " );
			sql.append( "and io2.codemppd=? and io2.codfilialpd=? and io2.codprod=? "); 
			sql.append( "and io2.sitproditorc='PD' and coalesce(io2.statusitorc,'PE')!='OV' " );
			
			ResultSet rs2 = null;		

			PreparedStatement ps2 = null;
			
			while ( rs.next() ) {
				
				tabAgrup.adicLinha();
				
				tabAgrup.setColColor( -1, AGRUPAMENTO.DTFABROP.ordinal(), Color.WHITE, Color.RED );
				tabAgrup.setColColor( -1, AGRUPAMENTO.QTDAPROD.ordinal(), Color.WHITE, Color.RED );
				
				ps2 = con.prepareStatement( sql.toString() );
				
				ps2.setInt( 1, Aplicativo.iCodEmp );
				ps2.setInt( 2, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
				ps2.setInt( 3, Aplicativo.iCodEmp );
				ps2.setInt( 4, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
				ps2.setInt( 5, rs.getInt( AGRUPAMENTO.CODPROD.toString() ) );

				rs2 = ps2.executeQuery();		
				
				BigDecimal qtdreservado = new BigDecimal(0);
				
				if(rs2.next()) {
					qtdreservado = rs2.getBigDecimal( AGRUPAMENTO.QTDRESERVADO.toString() ).setScale( Aplicativo.casasDec );						
				}
				
				tabAgrup.setValor( new Boolean(true), row, AGRUPAMENTO.MARCACAO.ordinal() );
				tabAgrup.setValor( rs.getInt( AGRUPAMENTO.CODEMPPD.toString() ), row, AGRUPAMENTO.CODEMPPD.ordinal() );
				tabAgrup.setValor( rs.getInt( AGRUPAMENTO.CODFILIALPD.toString() ), row, AGRUPAMENTO.CODFILIALPD.ordinal() );
				tabAgrup.setValor( rs.getInt( AGRUPAMENTO.CODPROD.toString() ), row, AGRUPAMENTO.CODPROD.ordinal() );
				tabAgrup.setValor( rs.getInt( AGRUPAMENTO.SEQEST.toString() ), row, AGRUPAMENTO.SEQEST.ordinal() );
				tabAgrup.setValor( rs.getString( AGRUPAMENTO.DESCPROD.toString().trim() ), row, AGRUPAMENTO.DESCPROD.ordinal() );
				
				if("S".equals( cbAgrupDataAprov.getVlrString() )){
					tabAgrup.setColunaVisivel( 60, AGRUPAMENTO.DATAAPROV.ordinal() );					
					tabAgrup.setValor( Funcoes.dateToStrDate( rs.getDate( AGRUPAMENTO.DATAAPROV.toString() ) ), row, AGRUPAMENTO.DATAAPROV.ordinal() );
				}
				else {
					tabAgrup.setColunaInvisivel( AGRUPAMENTO.DATAAPROV.ordinal() );
				}
				
				if("S".equals( cbAgrupDataProd.getVlrString() )){										
					tabAgrup.setValor( Funcoes.dateToStrDate( rs.getDate( AGRUPAMENTO.DTFABROP.toString() ) ), row, AGRUPAMENTO.DTFABROP.ordinal() );
				}
				else {
					tabAgrup.setValor( Funcoes.dateToStrDate( new Date() ), row, AGRUPAMENTO.DTFABROP.ordinal() );
				}
				
				if("S".equals( cbAgrupCli.getVlrString() )){
					tabAgrup.setColunaVisivel( 40, AGRUPAMENTO.CODCLI.ordinal() );
					tabAgrup.setColunaVisivel( 145,AGRUPAMENTO.RAZCLI.ordinal() );
					tabAgrup.setValor( rs.getInt( AGRUPAMENTO.CODEMPCL.toString() ), row, AGRUPAMENTO.CODEMPCL.ordinal() );
					tabAgrup.setValor( rs.getInt( AGRUPAMENTO.CODFILIALCL.toString() ), row, AGRUPAMENTO.CODFILIALCL.ordinal() );
					tabAgrup.setValor( rs.getInt( AGRUPAMENTO.CODCLI.toString() ), row, AGRUPAMENTO.CODCLI.ordinal() );
					tabAgrup.setValor( rs.getString( AGRUPAMENTO.RAZCLI.toString().trim() ), row, AGRUPAMENTO.RAZCLI.ordinal() );					
				}				
				else {
					tabAgrup.setColunaInvisivel( AGRUPAMENTO.CODCLI.ordinal() );
					tabAgrup.setColunaInvisivel( AGRUPAMENTO.RAZCLI.ordinal() );					
				}


				BigDecimal qtdestoque = rs.getBigDecimal( AGRUPAMENTO.QTDESTOQUE.toString() ).setScale( Aplicativo.casasDec );				
				BigDecimal qtdemprod = rs.getBigDecimal( AGRUPAMENTO.QTDEMPROD.toString() ).setScale( Aplicativo.casasDec );

				tabAgrup.setValor( imgColuna, row, AGRUPAMENTO.STATUS.ordinal() );
											
				tabAgrup.setValor( qtdestoque, row, AGRUPAMENTO.QTDESTOQUE.ordinal() );
				tabAgrup.setValor( qtdreservado, row, AGRUPAMENTO.QTDRESERVADO.ordinal() );
				tabAgrup.setValor( qtdemprod, row, AGRUPAMENTO.QTDEMPROD.ordinal() );
				tabAgrup.setValor( rs.getBigDecimal( AGRUPAMENTO.QTDAPROD.toString() ).setScale( Aplicativo.casasDec ) , row, AGRUPAMENTO.QTDAPROD.ordinal() );
				
				row++;
				
			}
			
			// Permitindo reordena��o
		
			if(row>0) {
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabAgrup.getModel());    
				tabAgrup.setRowSorter(sorter);				   
			}
			else {
				tabAgrup.setRowSorter( null );
			}
			
		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}

	
	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btBuscar ) {
			if ( tabbedAbas.getSelectedIndex() == 1 ) {
				montaGridAgrup();
			}
			else {
				montaGridDet();
			}
		}
		else if ( e.getSource() == btSelectAllDet ) {
			selectNecessarios(tabDet);
		}
		else if ( e.getSource() == btDeselectAllDet ) {
			deselectAll(tabDet);
		}
		else if ( e.getSource() == btLimparGridDet ) {
			limpaNaoSelecionados( tabDet );
		}		
		else if ( e.getSource() == btSelectAllAgrup ) {
			selectNecessarios( tabAgrup );
		}
		else if ( e.getSource() == btDeselectAllAgrup ) {
			deselectAll(tabAgrup);
		}
		else if ( e.getSource() == btLimparGridAgrup ) {
			limpaNaoSelecionados( tabAgrup );
		}
		else if ( e.getSource() == btIniProdDet ) {
			processaOPS( true );
		}
		else if ( e.getSource() == btIniProdAgrup ) {
			processaOPS( false );
		}

		
	}

	public void valorAlterado( TabelaSelEvent e ) {
		/*		
		if ( e.getTabela() == tabOrcamentos && tabOrcamentos.getLinhaSel() > -1 && !carregandoOrcamentos ) {
			buscaItensVenda( (Integer)tabOrcamentos.getValor( tabOrcamentos.getLinhaSel(), VENDAS.CODVENDA.ordinal() ), "V" );
		}
		*/
	}

	public void mouseClicked( MouseEvent mevt ) {
		JTablePad tabEv = (JTablePad) mevt.getSource();
		
		if ( mevt.getClickCount() == 2 ) {					
			if( tabEv == tabDet && tabEv.getLinhaSel() > -1 ) {
				ImageIcon imgclicada = (ImageIcon) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.STATUS.ordinal() );
				
				if(imgclicada.equals( imgPendente )) {
					FOrcamento orc = null;    
					if ( Aplicativo.telaPrincipal.temTela( FOrcamento.class.getName() ) ) {
						orc = (FOrcamento) Aplicativo.telaPrincipal.getTela( FOrcamento.class.getName() );
					}
					else {
						orc = new FOrcamento();
						Aplicativo.telaPrincipal.criatela( "Or�amento", orc, con );
					}    	    		 
					orc.exec( (Integer) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.CODORC.ordinal() ) );
				}
				else {
					FOP op = new FOP((Integer) tabDet.getValor( tabEv.getLinhaSel(), DETALHAMENTO.CODOP.ordinal() ), (Integer) tabDet.getValor( tabEv.getLinhaSel(), DETALHAMENTO.SEQOP.ordinal() ));
					Aplicativo.telaPrincipal.criatela( "Ordens de produ��o", op, con );    	    		 
				}
			}
		}
		if ( (tabEv == tabDet || tabEv == tabAgrup) && (tabEv.getLinhaSel() > -1) ) {
			
			Boolean selecionado = (Boolean) tabEv.getValor( tabEv.getLinhaSel(), 0 );
			BigDecimal qtdaprod = null;
			ImageIcon imgclicada = null;
			
			if(tabEv == tabDet) {
				qtdaprod = (BigDecimal) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.QTDAPROD.ordinal() );
				imgclicada = (ImageIcon) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.STATUS.ordinal() );
				tabEv.setValor( ! ( selecionado ).booleanValue(), tabEv.getLinhaSel(), 0 );
			}
			
		}
		
	}

	public void mouseEntered( MouseEvent e ) { }

	public void mouseExited( MouseEvent e ) { }

	public void mousePressed( MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

	public void keyPressed( KeyEvent e ) {
		
		if ( e.getSource() == btBuscar && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btBuscar.doClick();
		}
	}

	public void keyReleased( KeyEvent e ) { }

	public void keyTyped( KeyEvent e ) { }

	public void beforeCarrega( CarregaEvent e ) { }

	public void afterCarrega( CarregaEvent e ) {

		if ( lcProd == e.getListaCampos() || lcCliente == e.getListaCampos() ) {
			montaGridDet();
		}
		
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCliente.setConexao( con );
		lcProd.setConexao( con );
		
	}

	public void valorAlterado( TabelaEditEvent evt ) {

		// TODO Auto-generated method stub
		
	}
	
	private void selectAll(JTablePad tab) {
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( true ), i, 0 );
		}
	}
	
	private void limpaNaoSelecionados(JTablePad tab) {
		int linhas = tab.getNumLinhas();
		int pos = 0;
		try {			
			for ( int i = 0; i < linhas; i++ ) {
				if ( tab.getValor( i, 0 )!=null && ! ( (Boolean) tab.getValor( i, 0 ) ).booleanValue() ) { //xxx
					tab.tiraLinha( i );
					i--;
				}					
			}									
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void selectNecessarios(JTablePad tab) {
		BigDecimal qtdaprod = null;
		
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			qtdaprod = (BigDecimal) tab.getValor( i, tab==tabDet ? DETALHAMENTO.QTDAPROD.ordinal() : AGRUPAMENTO.QTDAPROD.ordinal() );			
			tab.setValor( new Boolean (qtdaprod.floatValue()>0), i, 0 );			
		}
	}

	private void deselectAll(JTablePad tab) {
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) { 
			tab.setValor( new Boolean( false ), i, 0 );
		}
	}
	
	private void processaOPS(boolean det) {	
		
		if(Funcoes.mensagemConfirma( this, "Confirma o processamento dos itens selecionados?" )==JOptionPane.YES_OPTION) {
			if(det) {
				geraOPSDet();
			}
			else {
				geraOPSAgrup();
			}
		}
	}
	
	private void geraOPSDet() {		
		StringBuffer sql = new StringBuffer();
		sql.append( "select codopret,seqopret " );
		sql.append( "from ppgeraop(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) " );
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Integer> ops = new Vector<Integer>();
		
		BigDecimal qtdsugerida = null;
		DLLoading loading = new DLLoading();
		
		try {
			for(int i=0; i<tabDet.getNumLinhas(); i++) {
				loading.start();
				qtdsugerida = (BigDecimal)(tabDet.getValor( i, DETALHAMENTO.QTDAPROD.ordinal() ));
				
				// Caso o item do grid esteja selecionado... 
				if( (Boolean)(tabDet.getValor( i, DETALHAMENTO.MARCACAO.ordinal() )) && qtdsugerida.floatValue()>0 ) {
					try {
						ps = con.prepareStatement( sql.toString() );
			
						ps.setString( PROCEDUREOP.TIPOPROCESS.ordinal() + 1, "D" );						 
						ps.setInt( PROCEDUREOP.CODEMPOP.ordinal() + 1, Aplicativo.iCodEmp );
						ps.setInt( PROCEDUREOP.CODFILIALOP.ordinal() + 1, Aplicativo.iCodFilial );
						ps.setNull( PROCEDUREOP.CODOP.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.SEQOP.ordinal() + 1, Types.INTEGER );						
						ps.setInt( PROCEDUREOP.CODEMPPD.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODEMPPD.ordinal() ) );
						ps.setInt( PROCEDUREOP.CODFILIALPD.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODFILIALPD .ordinal()) );					
						ps.setInt( PROCEDUREOP.CODPROD.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODPROD.ordinal()) );						
						ps.setInt( PROCEDUREOP.CODEMPOC.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODEMPOC.ordinal()) );
						ps.setInt( PROCEDUREOP.CODFILIALOC.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODFILIALOC.ordinal()) );
						ps.setInt( PROCEDUREOP.CODORC.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODORC.ordinal()) );
						ps.setInt( PROCEDUREOP.CODITORC.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODITORC.ordinal()) );
						ps.setString( PROCEDUREOP.TIPOORC.ordinal() + 1, (String) tabDet.getValor( i, DETALHAMENTO.TIPOORC.ordinal()) );						
						ps.setBigDecimal( PROCEDUREOP.QTDSUGPRODOP.ordinal() + 1, (BigDecimal) tabDet.getValor( i, DETALHAMENTO.QTDAPROD.ordinal()) );						
						ps.setDate( PROCEDUREOP.DTFABROP.ordinal() + 1, Funcoes.strDateToSqlDate( (String) tabDet.getValor( i, DETALHAMENTO.DTFABROP.ordinal()) ) );						
						ps.setInt( PROCEDUREOP.SEQEST.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.SEQEST.ordinal()) );
						ps.setNull( PROCEDUREOP.CODEMPET.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODFILIALET.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODEST.ordinal() + 1, Types.INTEGER );						
						ps.setNull( PROCEDUREOP.AGRUPDATAAPROV.ordinal() + 1, Types.CHAR );
						ps.setNull( PROCEDUREOP.AGRUPDTFABROP.ordinal() + 1, Types.CHAR );
						ps.setNull( PROCEDUREOP.AGRUPCODCLI.ordinal() + 1, Types.CHAR );
						ps.setNull( PROCEDUREOP.CODEMPCL.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODFILIALCL.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODCLI.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.DATAAPROV.ordinal() + 1, Types.DATE );
						ps.setNull( PROCEDUREOP.CODEMPCP.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODFILIALCP.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODCOMPRA.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODITCOMPRA.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.JUSTFICQTDPROD.ordinal() + 1, Types.CHAR );
						ps.setNull( PROCEDUREOP.CODEMPPDENTRADA.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODFILIALPDENTRADA.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODPRODENTRADA.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.QTDENTRADA.ordinal() + 1, Types.DECIMAL );	
						
						rs = ps.executeQuery();
						
						if(rs.next()) {
							ops.addElement( rs.getInt( 1 ) );
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
		
			}
			montaGridDet();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			loading.stop();
			Funcoes.mensagemInforma( this, "As seguintes ordens de produ��o foram geradas:\n" + ops.toString() );
		}
	}

	private void geraOPSAgrup() {		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append( "select codopret,seqopret " );
		sql.append( "from ppgeraop(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) " );
		 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Integer> ops = new Vector<Integer>();
		
		BigDecimal qtdsugerida = null;
		DLLoading loading = new DLLoading();
		
		try {
			for(int i=0; i<tabAgrup.getNumLinhas(); i++) {
				loading.start();
				qtdsugerida = (BigDecimal)(tabAgrup.getValor( i, AGRUPAMENTO.QTDAPROD.ordinal() ));
				
				// Caso o item do grid esteja selecionado... 
				if( (Boolean)(tabAgrup.getValor( i, AGRUPAMENTO.MARCACAO.ordinal() )) && qtdsugerida.floatValue()>0 ) {
					try {
						ps = con.prepareStatement( sql.toString() );
			
						ps.setString( PROCEDUREOP.TIPOPROCESS.ordinal() + 1, "A" );
						ps.setInt( PROCEDUREOP.CODEMPOP.ordinal() + 1, Aplicativo.iCodEmp );
						ps.setInt( PROCEDUREOP.CODFILIALOP.ordinal() + 1, Aplicativo.iCodFilial );
						ps.setNull( PROCEDUREOP.CODOP.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.SEQOP.ordinal() + 1, Types.INTEGER );
						ps.setInt( PROCEDUREOP.CODEMPPD.ordinal() + 1, (Integer) tabAgrup.getValor( i, AGRUPAMENTO.CODEMPPD.ordinal() ) );
						ps.setInt( PROCEDUREOP.CODFILIALPD.ordinal() + 1, (Integer) tabAgrup.getValor( i, AGRUPAMENTO.CODFILIALPD .ordinal()) );					
						ps.setInt( PROCEDUREOP.CODPROD.ordinal() + 1, (Integer) tabAgrup.getValor( i, AGRUPAMENTO.CODPROD.ordinal()) );																		 						
						ps.setNull( PROCEDUREOP.CODEMPOC.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODFILIALOC.ordinal() + 1, Types.INTEGER ); 
						ps.setNull( PROCEDUREOP.CODORC.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.TIPOORC.ordinal() + 1, Types.CHAR );
						ps.setNull( PROCEDUREOP.CODITORC.ordinal() + 1, Types.INTEGER );						
						ps.setBigDecimal( PROCEDUREOP.QTDSUGPRODOP.ordinal() + 1, (BigDecimal) tabAgrup.getValor( i, AGRUPAMENTO.QTDAPROD.ordinal()) );						
						ps.setDate( PROCEDUREOP.DTFABROP.ordinal() + 1, Funcoes.strDateToSqlDate( (String) tabAgrup.getValor( i, AGRUPAMENTO.DTFABROP.ordinal()) ) );						
						ps.setInt( PROCEDUREOP.SEQEST.ordinal() + 1, (Integer) tabAgrup.getValor( i, AGRUPAMENTO.SEQEST.ordinal()) );						
						ps.setInt( PROCEDUREOP.CODEMPET.ordinal() + 1, Aplicativo.iCodEmp );						
						ps.setInt( PROCEDUREOP.CODFILIALET.ordinal() + 1, Aplicativo.iCodFilial );						
						ps.setInt( PROCEDUREOP.CODEST.ordinal() + 1, Aplicativo.iNumEst );							

						if("S".equals( cbAgrupDataAprov )){
							ps.setString( PROCEDUREOP.AGRUPDATAAPROV.ordinal() + 1, "S" );
							ps.setDate( PROCEDUREOP.DATAAPROV.ordinal() + 1, Funcoes.strDateToSqlDate( (String) tabAgrup.getValor( i, AGRUPAMENTO.DATAAPROV.ordinal()) ) );
						}
						else {
							ps.setString( PROCEDUREOP.AGRUPDATAAPROV.ordinal() + 1, "N" );	
							ps.setNull( PROCEDUREOP.DATAAPROV.ordinal() + 1, Types.DATE );
						}
								 				
						if("S".equals( cbAgrupDataProd )){
							ps.setString( PROCEDUREOP.AGRUPDTFABROP.ordinal() + 1, "S" );							
						}
						else {
							ps.setString( PROCEDUREOP.AGRUPDTFABROP.ordinal() + 1, "N" );
						}
						if("S".equals( cbAgrupCli )){
							ps.setString( PROCEDUREOP.AGRUPCODCLI.ordinal() + 1, "S" );
							ps.setInt( PROCEDUREOP.CODEMPCL.ordinal() + 1, (Integer) tabAgrup.getValor( i, AGRUPAMENTO.CODEMPCL.ordinal() ) );
							ps.setInt( PROCEDUREOP.CODFILIALCL.ordinal() + 1, (Integer) tabAgrup.getValor( i, AGRUPAMENTO.CODFILIALCL .ordinal()) );					
							ps.setInt( PROCEDUREOP.CODCLI.ordinal() + 1, (Integer) tabAgrup.getValor( i, AGRUPAMENTO.CODCLI.ordinal()) );																		 						
						}
						else {
							ps.setString( PROCEDUREOP.AGRUPCODCLI.ordinal() + 1, "N" );
							ps.setNull( PROCEDUREOP.CODEMPCL.ordinal() + 1, Types.INTEGER );
							ps.setNull( PROCEDUREOP.CODFILIALCL.ordinal() + 1, Types.INTEGER );
							ps.setNull( PROCEDUREOP.CODCLI.ordinal() + 1, Types.INTEGER );							
						}
						
						ps.setNull( PROCEDUREOP.CODEMPCP.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODFILIALCP.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODCOMPRA.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODITCOMPRA.ordinal() + 1, Types.INTEGER );	
						ps.setNull( PROCEDUREOP.JUSTFICQTDPROD.ordinal() + 1, Types.CHAR );	
						
						ps.setNull( PROCEDUREOP.CODEMPPDENTRADA.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODFILIALPDENTRADA.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODPRODENTRADA.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.QTDENTRADA.ordinal() + 1, Types.DECIMAL );	
						
						rs = ps.executeQuery();
						
						if(rs.next()) {
							ops.addElement( rs.getInt( 1 ) );
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
		
			}
			montaGridDet();
			montaGridAgrup();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			deletaTabTemp();
			loading.stop();
			Funcoes.mensagemInforma( this, "As seguintes ordens de produ��o foram geradas:\n" + ops.toString() );
		}
	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( cevt.getSource() == tabbedAbas ) {
			if ( tabbedAbas.getSelectedIndex() == 1 ) {
				geraTabTemp();
				montaGridAgrup();
			}
		}
	}
	
	private void deletaTabTemp() {
		
		StringBuilder sql = new StringBuilder("");
		PreparedStatement ps = null;
		
		try {
			
			sql.append( "delete from ppprocessaoptmp pt where pt.codempet=? and pt.codfilialet=? and pt.codest=?" );
			
			ps = con.prepareStatement(sql.toString());
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, Aplicativo.iNumEst );
			
			ps.execute();
			ps.close();
		   	
			con.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private void geraTabTemp() {
		StringBuilder sql = new StringBuilder("");
		PreparedStatement ps = null;
		try {
			
			deletaTabTemp();
			
			sql.append( "insert into ppprocessaoptmp (codemp, codfilial, codorc, coditorc, tipoorc, dtfabrop, qtdaprod, codempet, codfilialet, codest) " );
			sql.append( "values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );
			
			for( int i=0; i< tabDet.getNumLinhas(); i++ ) {
				
				if ( (( (Boolean) tabDet.getValor( i, DETALHAMENTO.MARCACAO.ordinal() )).booleanValue()) && 
					  ( (BigDecimal) tabDet.getValor( i, DETALHAMENTO.QTDAPROD.ordinal())).floatValue() > 0 ) { 

					ps = con.prepareStatement( sql.toString() );
					
					ps.setInt( 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODEMPOC.ordinal()) );
					ps.setInt( 2, (Integer) tabDet.getValor( i, DETALHAMENTO.CODFILIALOC.ordinal()) );
					ps.setInt( 3, (Integer) tabDet.getValor( i, DETALHAMENTO.CODORC.ordinal()) );
					ps.setInt( 4, (Integer) tabDet.getValor( i, DETALHAMENTO.CODITORC.ordinal()) );
					ps.setString( 5, (String) tabDet.getValor( i, DETALHAMENTO.TIPOORC.ordinal()) );
					ps.setDate( 6, Funcoes.strDateToSqlDate( (String) tabDet.getValor( i, DETALHAMENTO.DTFABROP.ordinal()) ) );
					ps.setBigDecimal( 7, (BigDecimal) tabDet.getValor( i, DETALHAMENTO.QTDAPROD.ordinal()) );
					ps.setInt( 8, Aplicativo.iCodEmp );
					ps.setInt( 9, Aplicativo.iCodFilial );
					ps.setInt( 10, Aplicativo.iNumEst );
					
					ps.execute();
				
				}
				
			}
			
			con.commit();
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

