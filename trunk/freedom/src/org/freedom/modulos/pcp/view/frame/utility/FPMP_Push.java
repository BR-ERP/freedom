/*
 * 
 * Projeto: Freedom Pacote: org.freedom.modules.crm Classe: @(#)FPCP_Push.java
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR> modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR> na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR> sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR> Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR> Voc� deve ter recebido uma c�pia da Licen�a P�blica
 * Geral GNU junto com este programa, se n�o, <BR> escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 */

package org.freedom.modulos.pcp.view.frame.utility;

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
import java.sql.SQLException;
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
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.DLLoading;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.gms.business.object.TipoProd;
import org.freedom.modulos.gms.view.frame.crud.plain.FSecaoProd;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.pcp.view.frame.crud.detail.FEstrutura;
import org.freedom.modulos.std.view.frame.crud.detail.FOrcamento;

/**
 * Tela para planejamento mestre da produ��o (Sistema de produ��o Empurrada (Pull System). Baseada no estoque m�nimo e requisi��es pendentes.
 * 
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez
 * @version 20/09/2010
 */

public class FPMP_Push extends FFilho implements ActionListener, TabelaSelListener, MouseListener, KeyListener, CarregaListener, TabelaEditListener, ChangeListener {

	// *** Vari�veis est�ticas

	private static final long serialVersionUID = 1L;

	private static final Color GREEN = new Color( 45, 190, 60 );

	// *** Paineis tela

	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelMaster = new JPanelPad( 700, 100 );

	private JPanelPad panelAbas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JTabbedPanePad tabbedAbas = new JTabbedPanePad();

	private JPanelPad panelSouth = new JPanelPad( 30, 30 );

	private JPanelPad panelLegenda = new JPanelPad( 30, 30 );

//	private JPanelPad panelFiltros = new JPanelPad( "Filtros", Color.BLUE );

	// *** Paineis Detalhamento

	private JPanelPad panelDet = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelTabDet = new JPanelPad( 700, 60 );

	private JPanelPad panelGridDet = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelTabDetItens = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JTablePad tabDet = null;

	// *** Labels

	private JLabelPad sepdet = new JLabelPad();

	// *** Geral

	private JButtonPad btBuscar = new JButtonPad( "Buscar", Icone.novo( "btExecuta.gif" ) );

	// *** Campos

	private JTextFieldFK txtQtdRequisitada = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );

	private JTextFieldFK txtQtdEstoque = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );

	private JTextFieldFK txtQtdReservado = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );

	private JTextFieldFK txtQtdProducao = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );

	private JTextFieldFK txtQtdProduzir = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodSecao = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldFK txtDescSecao = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	// ** Checkbox

	private JCheckBoxPad cbAgrupProd = new JCheckBoxPad( "Produto", "S", "N" );

	private JCheckBoxPad cbAgrupDataAprov = new JCheckBoxPad( "Data de aprova��o", "S", "N" );

	private JCheckBoxPad cbAgrupDataProd = new JCheckBoxPad( "Data de produ��o", "S", "N" );

	private JCheckBoxPad cbAgrupCli = new JCheckBoxPad( "Cliente", "S", "N" );

	private JCheckBoxPad cbPend = new JCheckBoxPad( "Pendentes", "S", "N" );

	private JCheckBoxPad cbEmProd = new JCheckBoxPad( "Em produ��o", "S", "N" );

	private JCheckBoxPad cbProd = new JCheckBoxPad( "Produzidos", "S", "N" );

	// ** Legenda

	private ImageIcon imgUrgente = Icone.novo( "clVencido.gif" );

	private ImageIcon imgNormal = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgColuna = null;

	// *** Listacampos

	private ListaCampos lcCliente = new ListaCampos( this, "CL" );

	private ListaCampos lcProd = new ListaCampos( this );
	
	private ListaCampos lcProd2 = new ListaCampos( this );
	
	private ListaCampos lcSecao = new ListaCampos( this );

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
	
	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );


	// Enums

	private enum DETALHAMENTO {
		MARCACAO, STATUS, CODEMPPD, CODFILIALPD, CODPROD, REFPROD, SEQEST, DESCPROD, QTDMINPROD, QTDESTOQUE, QTDREQ, QTDEMPROD, DTFABROP, QTDAPROD
	}

	private enum PROCEDUREOP {
		TIPOPROCESS, CODEMPOP, CODFILIALOP, CODOP, SEQOP, CODEMPPD, CODFILIALPD, CODPROD, CODEMPOC, CODFILIALOC, CODORC, TIPOORC, CODITORC, QTDSUGPRODOP, DTFABROP, SEQEST, CODEMPET, CODFILIALET, CODEST, AGRUPDATAAPROV, AGRUPDTFABROP, AGRUPCODCLI, CODEMPCL, CODFILIALCL, CODCLI, DATAAPROV, CODEMPCP, CODFILIALCP, CODCOMPRA, CODITCOMPRA, JUSTFICQTDPROD, CODEMPPDENTRADA, CODFILIALPDENTRADA, CODPRODENTRADA, QTDENTRADA
	}

	public FPMP_Push() {

		super( false );

		setTitulo( "Planejamento mestre da produ��o (Push System)", this.getClass().getName() );
		setAtribos( 20, 20, 860, 600 );

		int x = (int) ( Aplicativo.telaPrincipal.dpArea.getSize().getWidth() - getWidth() ) / 2;
		int y = (int) ( Aplicativo.telaPrincipal.dpArea.getSize().getHeight() - getHeight() ) / 2;

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
		lcProd.setWhereAdic( "ATIVOPROD='S' AND TIPOPROD IN ('" + TipoProd.PRODUTO_ACABADO.getValue() + "','" + TipoProd.PRODUTO_INTERMEDIARIO.getValue() + "')" );
		txtCodProd.setTabelaExterna( lcProd, null );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		
		lcProd2.add( new GuardaCampo( txtRefProd, "RefProd", "Refer�ncia", ListaCampos.DB_PK, true ) );
		lcProd2.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o", ListaCampos.DB_SI, false ) );
		lcProd2.add( new GuardaCampo( txtCodProd, "codprod", "C�d.prod.", ListaCampos.DB_SI, false ) );

		txtRefProd.setNomeCampo( "RefProd" );

		lcProd2.setWhereAdic( "ATIVOPROD='S' AND TIPOPROD IN ('" + TipoProd.PRODUTO_ACABADO.getValue() + "','" + TipoProd.PRODUTO_INTERMEDIARIO.getValue() + "')" );
		lcProd2.montaSql( false, "PRODUTO", "EQ" );
		lcProd2.setQueryCommit( false );
		lcProd2.setReadOnly( true );
		txtRefProd.setTabelaExterna( lcProd2, FProduto.class.getCanonicalName() );
		txtRefProd.setFK( true );FEstrutura
		
		lcSecao.add( new GuardaCampo( txtCodSecao, "CodSecao", "C�d.Se��o", ListaCampos.DB_PK, false ) );
		lcSecao.add( new GuardaCampo( txtDescSecao, "DescSecao", "Descri��o da se��o", ListaCampos.DB_SI, false ) );
		lcSecao.montaSql( false, "SECAO", "EQ" );
		txtCodSecao.setNomeCampo( "CodSecao" );
		txtCodSecao.setFK( true );
		lcSecao.setReadOnly( true );
		lcSecao.setQueryCommit( false );
		txtCodSecao.setTabelaExterna( lcSecao, FSecaoProd.class.getCanonicalName() );

	}

	private void montaListeners() {

		btSelectAllDet.addActionListener( this );
		btDeselectAllDet.addActionListener( this );
		btLimparGridDet.addActionListener( this );
		btIniProdDet.addActionListener( this );

		btSelectAllAgrup.addActionListener( this );
		btDeselectAllAgrup.addActionListener( this );
		btLimparGridAgrup.addActionListener( this );

		btBuscar.addActionListener( this );

		lcProd.addCarregaListener( this );
		lcCliente.addCarregaListener( this );

		tabDet.addTabelaSelListener( this );
		tabDet.addMouseListener( this );

		tabbedAbas.addChangeListener( this );

	}

	private void montaTela() {

		getTela().add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelMaster, BorderLayout.NORTH );

		// ***** Cabe�alho

		
		if(comRef()) {
			panelMaster.adic( txtRefProd, 7, 20, 120, 20, "Refer�ncia" );
		}
		else {
			panelMaster.adic( txtCodProd, 7, 20, 120, 20, "C�d.Prod." );
			
		}

		panelMaster.adic( txtDescProd, 130, 20, 400, 20, "Descri��o do produto" );

		panelMaster.adic( txtCodSecao, 7, 60, 120, 20, "C�d.Se��o" );
		panelMaster.adic( txtDescSecao, 130, 60, 400, 20, "Descri��o da se��o" );

//		panelFiltros.adic( cbPend, 4, 0, 100, 20 );
//		panelFiltros.adic( cbEmProd, 4, 30, 100, 20 );
//		panelFiltros.adic( cbProd, 114, 0, 100, 20 );

//		panelMaster.adic( panelFiltros, 416, 0, 220, 82 );

		panelMaster.adic( btBuscar, 712, 10, 123, 30 );

		// ***** Abas

		panelGeral.add( panelAbas, BorderLayout.CENTER );
		panelGeral.add( panelAbas );
		panelAbas.add( tabbedAbas );

		tabbedAbas.addTab( "Detalhamento", panelDet );

		// ***** Detalhamento

		panelDet.add( panelTabDet, BorderLayout.NORTH );
		panelDet.add( panelGridDet, BorderLayout.CENTER );
		panelGridDet.add( panelTabDetItens );

		panelTabDet.adic( txtQtdRequisitada, 10, 25, 80, 20, "Requisitada" );
		panelTabDet.adic( txtQtdEstoque, 93, 25, 80, 20, "Estoque" );
		panelTabDet.adic( txtQtdProducao, 176, 25, 80, 20, "Produ��o" );
		panelTabDet.adic( txtQtdProduzir, 259, 25, 80, 20, "Produzir"  );

		sepdet.setBorder( BorderFactory.createEtchedBorder() );
		panelTabDet.adic( sepdet, 433, 4, 2, 48 );
		panelTabDet.adic( btIniProdDet, 443, 4, 48, 48 );

		panelTabDet.adic( btSelectAllDet, 743, 12, 30, 30 );
		panelTabDet.adic( btDeselectAllDet, 774, 12, 30, 30 );
		panelTabDet.adic( btLimparGridDet, 805, 12, 30, 30 );

		panelTabDetItens.add( new JScrollPane( tabDet ) );

				
		// ***** Rodap�

		Color statusColor = new Color( 111, 106, 177 );
		Font statusFont = SwingParams.getFontpadmin();

		JLabelPad canceladas = new JLabelPad( "Urgentes" );
		canceladas.setForeground( statusColor );
		canceladas.setFont( statusFont );
		panelLegenda.adic( new JLabelPad( imgUrgente ), 0, 5, 20, 15 );
		panelLegenda.adic( canceladas, 20, 5, 100, 15 );

		JLabelPad pedidos = new JLabelPad( "Normal" );
		pedidos.setForeground( statusColor );
		pedidos.setFont( statusFont );
		panelLegenda.adic( new JLabelPad( imgNormal ), 60, 5, 20, 15 );
		panelLegenda.adic( pedidos, 80, 5, 100, 15 );

		panelLegenda.setBorder( null );

		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );
		panelSouth.add( adicBotaoSair() );
		pnRod.add( panelLegenda, BorderLayout.CENTER );

	}

	private void criaTabelas() {

		// Tabela de detalhamento

		tabDet = new JTablePad();

		tabDet.adicColuna( "" ); // Marca��o
		tabDet.adicColuna( "" ); // Status
		tabDet.adicColuna( "codemppd" ); //Codemppd
		tabDet.adicColuna( "codfilialpd" ); // Codfilialpd
		tabDet.adicColuna( "C�digo" ); // Codprod
		tabDet.adicColuna( "Refer�ncia" ); // RefProd
		
		tabDet.adicColuna( "Seq.Estr." ); //CodEst
		tabDet.adicColuna( "Descri��o do produto" );//Descri��o do produto
		
		tabDet.adicColuna( "Minimo" ); // Qtd.Minima
		tabDet.adicColuna( "Estoque" ); // Qtd.Estoque 
		
		tabDet.adicColuna( "RMA" ); //Qtd.requerida 
		tabDet.adicColuna( "Em Prod." ); // Qtd.emprodu��o
		tabDet.adicColuna( "Data" ); // Data de fabricacao
		tabDet.adicColuna( "Sugestao" ); // Sugest�o de produ��o

//		MARCACAO, STATUS, DTFABROP, CODEMPPD, CODFILIALPD, CODPROD, SEQEST, DESCPROD, QTDMINPROD, QTDESTOQUE, QTDREQ,QTDEMPROD, QTDAPROD
		
		tabDet.setTamColuna( 17, DETALHAMENTO.MARCACAO.ordinal() );
		tabDet.setTamColuna( 10, DETALHAMENTO.STATUS.ordinal() );


		tabDet.setColunaInvisivel( DETALHAMENTO.CODEMPPD.ordinal() );
		tabDet.setColunaInvisivel( DETALHAMENTO.CODFILIALPD.ordinal() );

		tabDet.setTamColuna( 60, DETALHAMENTO.CODPROD.ordinal() );
		tabDet.setTamColuna( 70, DETALHAMENTO.REFPROD.ordinal() );

		tabDet.setColunaInvisivel( DETALHAMENTO.SEQEST.ordinal() );
		
		tabDet.setTamColuna( 300, DETALHAMENTO.DESCPROD.ordinal() );
		tabDet.setTamColuna( 50, DETALHAMENTO.QTDMINPROD.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDESTOQUE.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDREQ.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDEMPROD.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.DTFABROP.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDAPROD.ordinal() );
		
		tabDet.setColunaEditavel( DETALHAMENTO.QTDAPROD.ordinal(), true );
		tabDet.setColunaEditavel( DETALHAMENTO.DTFABROP.ordinal(), true );
		
		tabDet.setRowHeight( 22 );

	}

	private void carregaItens() {

		try {
			
			tabDet.limpa(); 

			StringBuilder sql = new StringBuilder();

			
			sql.append( "select pd.codemp codemppd, pd.codfilial codfilialpd, pd.codprod, pd.refprod, es.seqest, pd.descprod, pd.qtdminprod, pd.sldprod qtdestoque, ");
			
			// RMA n�o atendidas
			sql.append( "coalesce( ");			
			sql.append( "(select sum(ir.qtdaprovitrma - ir.qtdexpitrma) from eqitrma ir ");
			sql.append( "where ir.codemppd=pd.codemp and ir.codfilial=pd.codfilial and ir.codprod=pd.codprod ");
			sql.append( "and ir.qtdaprovitrma > 0 and ir.sitaprovitrma in ('AP','AT') ");
			sql.append( "and ir.qtdexpitrma < ir.qtdaprovitrma) ");
			sql.append( ",0) qtdreq, ");
			
			// Ops n�o finalizadas
			sql.append( "coalesce( ");
			sql.append( "(select sum(op.qtdprevprodop) from ppop op ");
			sql.append( "where op.codemppd=pd.codemp and op.codfilial=pd.codfilial and op.codprod=pd.codprod ");
			sql.append( "and op.sitop='PE'), 0 ) qtdemprod ");
			
			sql.append( "from eqproduto pd, ppestrutura es ");
			sql.append( "where ");
			sql.append( "pd.ativoprod='S' and pd.sldprod < pd.qtdminprod and pd.tipoprod='F' and pd.codemp=? and pd.codfilial=? and ");

			sql.append( "es.codemp=pd.codemp and es.codfilial=pd.codfilial and es.codprod = pd.codprod ");
			

			if ( txtCodProd.getVlrInteger() > 0 ) {
				sql.append( " and pd.codprod=? ");
			}
			
			if ( ( null != txtCodSecao.getVlrString() ) && ( ! "".equals( txtCodSecao.getVlrString() )) ) {
				sql.append( " and pd.codempsc=? and pd.codfilialsc=? and pd.codsecao=? ");
			}
			
			StringBuffer status = new StringBuffer( "" );

			System.out.println( "SQL:" + sql.toString() );

			PreparedStatement ps = con.prepareStatement( sql.toString() );

			int iparam = 1;

			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "EQPRODUTO" ) );

			if ( txtCodProd.getVlrInteger() > 0 ) {
				ps.setInt( iparam++, txtCodProd.getVlrInteger() );
			}
			if ( ( null != txtCodSecao.getVlrString() ) && ( ! "".equals( txtCodSecao.getVlrString() )) ) {
				ps.setInt( iparam++, lcSecao.getCodEmp() );
				ps.setInt( iparam++, lcSecao.getCodFilial() );
				ps.setString( iparam++, txtCodSecao.getVlrString() );
			}

			ResultSet rs = ps.executeQuery();

			int row = 0;

			BigDecimal totqtdminimo = new BigDecimal( 0 );
			BigDecimal totqtdestoq = new BigDecimal( 0 );
			BigDecimal totqtdreq = new BigDecimal( 0 );
			BigDecimal totqtdemprod = new BigDecimal( 0 );
			BigDecimal totqtdaprod = new BigDecimal( 0 );

			ResultSet rs2 = null;

			PreparedStatement ps2 = null;

			while ( rs.next() ) {

				

				BigDecimal qtdreserv = new BigDecimal( 0 );

				BigDecimal qtdminimo = rs.getBigDecimal( DETALHAMENTO.QTDMINPROD.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdestoque = rs.getBigDecimal( DETALHAMENTO.QTDESTOQUE.toString() ).setScale( Aplicativo.casasDec );

				BigDecimal qtdemprod = rs.getBigDecimal( DETALHAMENTO.QTDEMPROD.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdreq = rs.getBigDecimal( DETALHAMENTO.QTDREQ.toString() ).setScale( Aplicativo.casasDec );

				BigDecimal qtdaprod = new BigDecimal( qtdminimo.floatValue() + qtdreq.floatValue() - qtdestoque.floatValue() - qtdemprod.floatValue() ); 
				
				totqtdminimo = totqtdminimo.add( qtdminimo );
				totqtdestoq = totqtdestoq.add( qtdestoque );
				totqtdreq = totqtdreq.add( qtdreq );
				totqtdemprod = totqtdemprod.add( qtdemprod );
				totqtdaprod = totqtdaprod.add( qtdaprod );

				if(qtdaprod.floatValue() > 0) {
					tabDet.adicLinha();
					tabDet.setColColor( -1, DETALHAMENTO.DTFABROP.ordinal(), Color.WHITE, Color.RED );
					tabDet.setColColor( -1, DETALHAMENTO.QTDAPROD.ordinal(), Color.WHITE, Color.RED );
					
					tabDet.setValor( new Boolean( false ), row, DETALHAMENTO.MARCACAO.ordinal() );
					
					if ( qtdreq.floatValue() > 0 )  {
						imgColuna = imgUrgente;
					}
					else {
						imgColuna = imgNormal;
					}
						
					tabDet.setValor( imgColuna, row, DETALHAMENTO.STATUS.ordinal() );
					
					tabDet.setValor( Funcoes.dateToStrDate( new Date() ), row, DETALHAMENTO.DTFABROP.ordinal() );
					tabDet.setValor( rs.getInt( DETALHAMENTO.CODEMPPD.toString() ), row, DETALHAMENTO.CODEMPPD.ordinal() );
					tabDet.setValor( rs.getInt( DETALHAMENTO.CODFILIALPD.toString() ), row, DETALHAMENTO.CODFILIALPD.ordinal() );
					tabDet.setValor( rs.getInt( DETALHAMENTO.CODPROD.toString() ), row, DETALHAMENTO.CODPROD.ordinal() );
					tabDet.setValor( rs.getString( DETALHAMENTO.REFPROD.toString() ), row, DETALHAMENTO.REFPROD.ordinal() );
					tabDet.setValor( rs.getInt( DETALHAMENTO.SEQEST.toString() ), row, DETALHAMENTO.SEQEST.ordinal() );
					tabDet.setValor( rs.getString( DETALHAMENTO.DESCPROD.toString().trim() ), row, DETALHAMENTO.DESCPROD.ordinal() );
					
					tabDet.setValor( qtdminimo, row, DETALHAMENTO.QTDMINPROD.ordinal() );
					tabDet.setValor( qtdestoque, row, DETALHAMENTO.QTDESTOQUE.ordinal() );
					tabDet.setValor( qtdreq, row, DETALHAMENTO.QTDREQ.ordinal() );				
					tabDet.setValor( qtdemprod, row, DETALHAMENTO.QTDEMPROD.ordinal() );
					tabDet.setValor( qtdaprod, row, DETALHAMENTO.QTDAPROD.ordinal() );
	
					row++;
	
				}
	
	
				if ( totqtdaprod.floatValue() < 0 ) {
					totqtdaprod = new BigDecimal( 0 );
				}
	
				txtQtdRequisitada.setVlrBigDecimal( totqtdreq );
				txtQtdEstoque.setVlrBigDecimal( totqtdestoq );
				
				if(txtCodProd.getVlrInteger()>0) {
				
					txtQtdProducao.setVlrBigDecimal( totqtdemprod );
					txtQtdProduzir.setVlrBigDecimal( totqtdaprod );
	
				}
				else {
					
					txtQtdProducao.setVlrString( "-" );
					txtQtdProduzir.setVlrString( "-" );				
				}
			}
			
			// Permitindo reordena��o
			if ( row > 0 ) {
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>( tabDet.getModel() );

				/*
				 * sorter.addRowSorterListener(new RowSorterListener() { public void sorterChanged(RowSorterEvent e) { if(e.getType() == RowSorterEvent.Type.SORTED) { System.out.println("teste"); } } });
				 */

				tabDet.setRowSorter( sorter );

			}
			else {
				tabDet.setRowSorter( null );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btBuscar ) {
			if ( tabbedAbas.getSelectedIndex() == 1 ) {

			}
			else {
				carregaItens();
			}
		}
		else if ( e.getSource() == btSelectAllDet ) {
			selectNecessarios( tabDet );
		}
		else if ( e.getSource() == btDeselectAllDet ) {
			deselectAll( tabDet );
		}
		else if ( e.getSource() == btLimparGridDet ) {
			limpaNaoSelecionados( tabDet );
		}
		else if ( e.getSource() == btIniProdDet ) {
			processaOPS( true );
		}

	}

	public void valorAlterado( TabelaSelEvent e ) {

		/*
		 * if ( e.getTabela() == tabOrcamentos && tabOrcamentos.getLinhaSel() > -1 && !carregandoOrcamentos ) { buscaItensVenda( (Integer)tabOrcamentos.getValor( tabOrcamentos.getLinhaSel(), VENDAS.CODVENDA.ordinal() ), "V" ); }
		 */
	}

	public void mouseClicked( MouseEvent mevt ) {

		JTablePad tabEv = (JTablePad) mevt.getSource();

		if ( mevt.getClickCount() == 2 ) {
			if ( tabEv == tabDet && tabEv.getLinhaSel() > -1 ) {
				ImageIcon imgclicada = (ImageIcon) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.STATUS.ordinal() );

				if ( imgclicada.equals( imgUrgente ) ) {
					FOrcamento orc = null;
					if ( Aplicativo.telaPrincipal.temTela( FOrcamento.class.getName() ) ) {
						orc = (FOrcamento) Aplicativo.telaPrincipal.getTela( FOrcamento.class.getName() );
					}
					else {
						orc = new FOrcamento();
						Aplicativo.telaPrincipal.criatela( "Or�amento", orc, con );
					}
//					orc.exec( (Integer) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.CODORC.ordinal() ) );
				}
				else {
//					FOP op = new FOP( (Integer) tabDet.getValor( tabEv.getLinhaSel(), DETALHAMENTO.CODOP.ordinal() ), (Integer) tabDet.getValor( tabEv.getLinhaSel(), DETALHAMENTO.SEQOP.ordinal() ) );
//					Aplicativo.telaPrincipal.criatela( "Ordens de produ��o", op, con );
				}
			}
		}
		if ( ( tabEv == tabDet ) && ( tabEv.getLinhaSel() > -1 ) ) {

			Boolean selecionado = (Boolean) tabEv.getValor( tabEv.getLinhaSel(), 0 );
			BigDecimal qtdaprod = null;
			ImageIcon imgclicada = null;

			if ( tabEv == tabDet ) {
				qtdaprod = (BigDecimal) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.QTDAPROD.ordinal() );
				imgclicada = (ImageIcon) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.STATUS.ordinal() );
				tabEv.setValor( ! ( selecionado ).booleanValue(), tabEv.getLinhaSel(), 0 );
			}

		}

	}

	public void mouseEntered( MouseEvent e ) {

	}

	public void mouseExited( MouseEvent e ) {

	}

	public void mousePressed( MouseEvent e ) {

	}

	public void mouseReleased( MouseEvent e ) {

	}

	public void keyPressed( KeyEvent e ) {

		if ( e.getSource() == btBuscar && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btBuscar.doClick();
		}
	}

	public void keyReleased( KeyEvent e ) {

	}

	public void keyTyped( KeyEvent e ) {

	}

	public void beforeCarrega( CarregaEvent e ) {

	}

	public void afterCarrega( CarregaEvent e ) {

		if ( lcProd == e.getListaCampos() || lcCliente == e.getListaCampos() ) {
			carregaItens();
		}

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCliente.setConexao( con );
		lcProd.setConexao( con );
		lcSecao.setConexao( con );
		lcProd2.setConexao( con );

	}
	
	private boolean comRef() {

		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = Aplicativo.getInstace().getConexao().prepareStatement( sSQL );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			
			rs = ps.executeQuery();
			
			if ( rs.next() )
				if ( rs.getString( "UsaRefProd" ).trim().equals( "S" ) )
					bRetorno = true;
			
			
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} 
		finally {
			sSQL = null;
			ps = null;
			rs = null;
		}
		return bRetorno;
	}


	public void valorAlterado( TabelaEditEvent evt ) {

		// TODO Auto-generated method stub

	}

	private void selectAll( JTablePad tab ) {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( true ), i, 0 );
		}
	}

	private void limpaNaoSelecionados( JTablePad tab ) {

		int linhas = tab.getNumLinhas();
		int pos = 0;
		try {
			for ( int i = 0; i < linhas; i++ ) {
				if ( tab.getValor( i, 0 ) != null && ! ( (Boolean) tab.getValor( i, 0 ) ).booleanValue() ) { // xxx
					tab.tiraLinha( i );
					i--;
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void selectNecessarios( JTablePad tab ) {

		BigDecimal qtdaprod = null;

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			qtdaprod = (BigDecimal) tab.getValor( i, DETALHAMENTO.QTDAPROD.ordinal() );
			tab.setValor( new Boolean( qtdaprod.floatValue() > 0 ), i, 0 );
		}
	}

	private void deselectAll( JTablePad tab ) {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( false ), i, 0 );
		}
	}

	private void processaOPS( boolean det ) {

		if ( Funcoes.mensagemConfirma( this, "Confirma o processamento dos itens selecionados?" ) == JOptionPane.YES_OPTION ) {
			if ( det ) {
				geraOPS();
			}
			else {

			}
		}
	}

	private void geraOPS() {

		StringBuffer sql = new StringBuffer();
		sql.append( "select codopret,seqopret " );
		sql.append( "from ppgeraop(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) " );
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Integer> ops = new Vector<Integer>();

		BigDecimal qtdsugerida = null;
		DLLoading loading = new DLLoading();

		try {
			for ( int i = 0; i < tabDet.getNumLinhas(); i++ ) {
				loading.start();
				qtdsugerida = (BigDecimal) ( tabDet.getValor( i, DETALHAMENTO.QTDAPROD.ordinal() ) );

				// Caso o item do grid esteja selecionado...
				if ( (Boolean) ( tabDet.getValor( i, DETALHAMENTO.MARCACAO.ordinal() ) ) && qtdsugerida.floatValue() > 0 ) {
					try {
						ps = con.prepareStatement( sql.toString() );

						ps.setString( PROCEDUREOP.TIPOPROCESS.ordinal() + 1, "C" );//x
						ps.setInt( PROCEDUREOP.CODEMPOP.ordinal() + 1, Aplicativo.iCodEmp );//x
						ps.setInt( PROCEDUREOP.CODFILIALOP.ordinal() + 1, Aplicativo.iCodFilial );//x
						ps.setNull( PROCEDUREOP.CODOP.ordinal() + 1, Types.INTEGER );//x
						ps.setNull( PROCEDUREOP.SEQOP.ordinal() + 1, Types.INTEGER );
						ps.setInt( PROCEDUREOP.CODEMPPD.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODEMPPD.ordinal() ) );
						ps.setInt( PROCEDUREOP.CODFILIALPD.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODFILIALPD.ordinal() ) );
						ps.setInt( PROCEDUREOP.CODPROD.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.CODPROD.ordinal() ) );
						
						ps.setNull( PROCEDUREOP.CODEMPOC.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODFILIALOC.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.CODORC.ordinal() + 1, Types.INTEGER );
						ps.setNull( PROCEDUREOP.TIPOORC.ordinal() + 1, Types.CHAR );
						ps.setNull( PROCEDUREOP.CODITORC.ordinal() + 1, Types.INTEGER );
						
						ps.setBigDecimal( PROCEDUREOP.QTDSUGPRODOP.ordinal() + 1, (BigDecimal) tabDet.getValor( i, DETALHAMENTO.QTDAPROD.ordinal() ) );
						ps.setDate( PROCEDUREOP.DTFABROP.ordinal() + 1, Funcoes.strDateToSqlDate( (String) tabDet.getValor( i, DETALHAMENTO.DTFABROP.ordinal() ) ) );
						ps.setInt( PROCEDUREOP.SEQEST.ordinal() + 1, (Integer) tabDet.getValor( i, DETALHAMENTO.SEQEST.ordinal() ) );
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

						if ( rs.next() ) {
							ops.addElement( rs.getInt( 1 ) );
						}
					} catch ( Exception e ) {
						e.printStackTrace();
					}
				}

			}
			carregaItens();
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			loading.stop();
			Funcoes.mensagemInforma( this, "As seguintes ordens de produ��o foram geradas:\n" + ops.toString() );
		}
	}

	public void stateChanged( ChangeEvent cevt ) {

		if ( cevt.getSource() == tabbedAbas ) {
			if ( tabbedAbas.getSelectedIndex() == 1 ) {
				geraTabTemp();

			}
		}
	}

	private void deletaTabTemp() {

		StringBuilder sql = new StringBuilder( "" );
		PreparedStatement ps = null;

		try {

			sql.append( "delete from ppprocessaoptmp pt where pt.codempet=? and pt.codfilialet=? and pt.codest=?" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, Aplicativo.iNumEst );

			ps.execute();
			ps.close();

			con.commit();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void geraTabTemp() {

		StringBuilder sql = new StringBuilder( "" );
		PreparedStatement ps = null;
		try {

			deletaTabTemp();

			sql.append( "insert into ppprocessaoptmp (codemp, codfilial, codorc, coditorc, tipoorc, dtfabrop, qtdaprod, codempet, codfilialet, codest) " );
			sql.append( "values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

			for ( int i = 0; i < tabDet.getNumLinhas(); i++ ) {

				if ( ( ( (Boolean) tabDet.getValor( i, DETALHAMENTO.MARCACAO.ordinal() ) ).booleanValue() ) && ( (BigDecimal) tabDet.getValor( i, DETALHAMENTO.QTDAPROD.ordinal() ) ).floatValue() > 0 ) {

					ps = con.prepareStatement( sql.toString() );

					ps.setDate( 6, Funcoes.strDateToSqlDate( (String) tabDet.getValor( i, DETALHAMENTO.DTFABROP.ordinal() ) ) );
					ps.setBigDecimal( 7, (BigDecimal) tabDet.getValor( i, DETALHAMENTO.QTDAPROD.ordinal() ) );
					ps.setInt( 8, Aplicativo.iCodEmp );
					ps.setInt( 9, Aplicativo.iCodFilial );
					ps.setInt( 10, Aplicativo.iNumEst );

					ps.execute();

				}

			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

}





