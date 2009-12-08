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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.TabelaEditEvent;
import org.freedom.acao.TabelaEditListener;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modulos.std.FOrcamento;
import org.freedom.modulos.std.FVenda;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;
import org.freedom.telas.SwingParams;


/**
 * Tela para planejamento da produ��o.
 * 
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez
 * @version 03/12/2009
 */

public class FPCP extends FFilho implements ActionListener, TabelaSelListener, MouseListener, KeyListener, CarregaListener, TabelaEditListener {

	private static final long serialVersionUID = 1L;	
	private static final Color GREEN = new Color( 45, 190, 60 );

	// *** Paineis tela
	
	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	private JPanelPad panelMaster = new JPanelPad( 700, 100 );
	private JPanelPad panelAbas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JTabbedPanePad tabbedAbas = new JTabbedPanePad();
	private JPanelPad panelSouth = new JPanelPad(30, 30 );	
	private JPanelPad panelLegenda = new JPanelPad(30, 30 );	

	// *** Paineis Detalhamento
	
	private JPanelPad panelDet = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );		
	private JPanelPad panelTabDet = new JPanelPad( 700, 60 );
	private JPanelPad panelGridDet = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JPanelPad panelTabDetItens = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private Tabela tabDet = null;
	
	// *** Paineis Agrupamento
	
	private JPanelPad panelAgrup = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );	
	private JPanelPad panelTabAgrup = new JPanelPad( 700, 60 );
	private JPanelPad panelGridAgrup = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JPanelPad panelTabAgrupItens = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	private JPanelPad pnCritAgrup = new JPanelPad();
	private Tabela tabAgrup = null;
		
	// *** Geral

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	private JButtonPad btBuscar = new JButtonPad( "Buscar", Icone.novo( "btExecuta.gif" ) );
	
	// *** Campos

	private JTextFieldFK txtQuantidadeVendida = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );	
	private JTextFieldFK txtQuantidadeEstoque = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );	
	private JTextFieldFK txtQuantidadeProducao = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );
	private JTextFieldFK txtQuantidadeProduzir = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDec );
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	// ** Checkbox
	
	private JCheckBoxPad cbAgrupProd =  new JCheckBoxPad( "Produto", "S", "N" );
	private JCheckBoxPad cbAgrupDataAprov =  new JCheckBoxPad( "Dt.Aprova��o", "S", "N" );
	private JCheckBoxPad cbAgrupDataEntrega =  new JCheckBoxPad( "Dt.Entrega", "S", "N" );
	private JCheckBoxPad cbAgrupCli =  new JCheckBoxPad( "Cliente", "S", "N" );
	
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
	private JButtonPad btSelectNecessariosDet = new JButtonPad( Icone.novo( "btSelEstrela.png" ) );
	private JButtonPad btLimparGridDet = new JButtonPad( Icone.novo( "btVassoura.png" ) );
	private JButtonPad btSimulaAgrupamentoDet = new JButtonPad( Icone.novo( "btVassoura.png" ) );
	
	private JButtonPad btSelectAllAgrup = new JButtonPad( Icone.novo( "btTudo.gif" ) );
	private JButtonPad btDeselectAllAgrup = new JButtonPad( Icone.novo( "btNada.gif" ) );
	private JButtonPad btSelectNecessariosAgrup = new JButtonPad( Icone.novo( "btSelEstrela.png" ) );
	private JButtonPad btLimparGridAgrup = new JButtonPad( Icone.novo( "btVassoura.png" ) );
	private JButtonPad btSimulaAgrupamentoAgrup = new JButtonPad( Icone.novo( "btVassoura.png" ) );

	// Enums
	
	private enum DETALHAMENTO {
		MARCACAO,STATUS, DATAAPROV, DATAENTREGA, CODORC, CODITORC, CODCLI, RAZCLI, CODPROD, DESCPROD, QTDAPROV, QTDESTOQUE, QTDEMPROD, QTDAPROD ;
	}
	
	private enum AGRUPAMENTO {
		MARCACAO,STATUS, DATAAPROV, DATAENTREGA, CODCLI, RAZCLI, CODPROD, DESCPROD, QTDAPROV, QTDESTOQUE, QTDEMPROD, QTDAPROD ;
	}
	
	public FPCP() {

		super( false );
		
		setTitulo( "Consulta de clientes", this.getClass().getName() );
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
	}
	
	private void montaListaCampos() {
		
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
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
		btSelectNecessariosDet.addActionListener( this );
		btLimparGridDet.addActionListener( this );
		
		btSelectAllAgrup.addActionListener( this );
		btDeselectAllAgrup.addActionListener( this );
		btSelectNecessariosAgrup.addActionListener( this );
		btLimparGridAgrup.addActionListener( this );
	
		btBuscar.addActionListener( this );
	
		lcProd.addCarregaListener( this );
		lcCliente.addCarregaListener( this );
		
//		tabDet.addTabelaEditListener( this );
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

		panelMaster.adic( btBuscar, 712, 10, 123, 30 );
		
//		***** Abas
		
		panelGeral.add( panelAbas, BorderLayout.CENTER );
		panelGeral.add( panelAbas);
		panelAbas.add( tabbedAbas );
		
		tabbedAbas.addTab( "Detalhamento", panelDet );
		tabbedAbas.addTab( "Agrupamento", panelAgrup );
		
		// ***** Detalhamento
		
		panelDet.add( panelTabDet, BorderLayout.NORTH );
		panelDet.add( panelGridDet, BorderLayout.CENTER );		
		panelGridDet.add( panelTabDetItens );
		
		panelTabDet.adic( new JLabelPad( "Qtd.Vendida" ), 10, 5, 80, 20 );
		panelTabDet.adic( txtQuantidadeVendida, 10, 25, 80, 20 );
		panelTabDet.adic( new JLabelPad( "Qtd.Estoque" ), 93, 5, 80, 20 );
		panelTabDet.adic( txtQuantidadeEstoque, 93, 25, 80, 20 );
		panelTabDet.adic( new JLabelPad( "Qtd.Produ��o" ), 176, 5, 80, 20 );
		panelTabDet.adic( txtQuantidadeProducao, 176, 25, 80, 20 );
		panelTabDet.adic( new JLabelPad( "Qtd.Produzir" ), 259, 5, 80, 20 );
		panelTabDet.adic( txtQuantidadeProduzir, 259, 25, 80, 20 );
		
		panelTabDet.adic( btSelectAllDet, 712, 12, 30, 30 );
		panelTabDet.adic( btDeselectAllDet, 743, 12, 30, 30 );
		panelTabDet.adic( btSelectNecessariosDet, 774, 12, 30, 30 );
		panelTabDet.adic( btLimparGridDet, 805, 12, 30, 30 );
		
		JLabelPad separacao = new JLabelPad();
		separacao.setBorder( BorderFactory.createEtchedBorder() );
		panelTabDet.adic( separacao, 350, 4, 2, 47 );

		panelTabDetItens.add( new JScrollPane( tabDet ) );

		
		// ***** Agrupamento
		
		panelAgrup.add( panelTabAgrup, BorderLayout.NORTH );
		panelAgrup.add( panelGridAgrup, BorderLayout.CENTER );		
		panelGridAgrup.add( panelTabAgrupItens );		
		
		pnCritAgrup.setBorder( SwingParams.getPanelLabel() );
		
		pnCritAgrup.adic( cbAgrupProd, 4, 0, 90, 20 );
		pnCritAgrup.adic( cbAgrupDataEntrega, 94, 0, 90, 20 );
		pnCritAgrup.adic( cbAgrupDataAprov, 184, 0, 110, 20 );
		pnCritAgrup.adic( cbAgrupCli, 294, 0, 90, 20 );		
		
		panelTabAgrup.adic(pnCritAgrup, 4, 0, 375, 53);
		
		panelTabAgrup.adic( btSelectAllAgrup, 712, 12, 30, 30 );
		panelTabAgrup.adic( btDeselectAllAgrup, 743, 12, 30, 30 );
		panelTabAgrup.adic( btSelectNecessariosAgrup, 774, 12, 30, 30 );
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
		
		tabDet = new Tabela();
		
		tabDet.adicColuna( "" );
		tabDet.adicColuna( "" );
		tabDet.adicColuna( "Dt.Aprov." );
		tabDet.adicColuna( "Dt.Entrega" );
		tabDet.adicColuna( "Orc." );
		tabDet.adicColuna( "Item" );
		tabDet.adicColuna( "Cli." );
		tabDet.adicColuna( "Raz�o social do cliente" );
		tabDet.adicColuna( "Prod." );
		tabDet.adicColuna( "Descri��o do produto" );
		tabDet.adicColuna( "Aprov." );
		tabDet.adicColuna( "Estoque" );
		tabDet.adicColuna( "Produ��o" );
		tabDet.adicColuna( "Produzir" );
		
		tabDet.setTamColuna( 15, DETALHAMENTO.MARCACAO.ordinal() );
		tabDet.setTamColuna( 10, DETALHAMENTO.STATUS.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.DATAAPROV.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.DATAENTREGA.ordinal() );
		tabDet.setTamColuna( 30, DETALHAMENTO.CODORC.ordinal() );
		tabDet.setTamColuna( 30, DETALHAMENTO.CODITORC.ordinal() );
		tabDet.setTamColuna( 40, DETALHAMENTO.CODCLI.ordinal() );
		tabDet.setTamColuna( 145, DETALHAMENTO.RAZCLI.ordinal() );
		tabDet.setTamColuna( 40, DETALHAMENTO.CODPROD.ordinal() );
		tabDet.setTamColuna( 145, DETALHAMENTO.DESCPROD.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDAPROV.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDESTOQUE.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDEMPROD.ordinal() );
		tabDet.setTamColuna( 60, DETALHAMENTO.QTDAPROD.ordinal() );

		// Tabela de Agrupamento
		
		tabAgrup = new Tabela();
		
		tabAgrup.adicColuna( "" );
		tabAgrup.adicColuna( "" );
		tabAgrup.adicColuna( "Dt.Aprov." );
		tabAgrup.adicColuna( "Dt.Entrega" );
		tabAgrup.adicColuna( "Cli." );
		tabAgrup.adicColuna( "Raz�o social do cliente" );
		tabAgrup.adicColuna( "Prod." );
		tabAgrup.adicColuna( "Descri��o do produto" );
		tabAgrup.adicColuna( "Aprov." );
		tabAgrup.adicColuna( "Estoque" );
		tabAgrup.adicColuna( "Produ��o" );
		tabAgrup.adicColuna( "Produzir" );
		
		tabAgrup.setTamColuna( 15, AGRUPAMENTO.MARCACAO.ordinal() );
		tabAgrup.setTamColuna( 10, AGRUPAMENTO.STATUS.ordinal() );
		tabAgrup.setTamColuna( 40, AGRUPAMENTO.CODPROD.ordinal() );
		tabAgrup.setTamColuna( 145, AGRUPAMENTO.DESCPROD.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDAPROV.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDESTOQUE.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDEMPROD.ordinal() );
		tabAgrup.setTamColuna( 60, AGRUPAMENTO.QTDAPROD.ordinal() );
		
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.DATAAPROV.ordinal() );
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.DATAENTREGA.ordinal() );
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.CODCLI.ordinal() );
		tabAgrup.setColunaInvisivel( AGRUPAMENTO.RAZCLI.ordinal() );				
		
	}
	
	private void montaGridDet() {
			
		try {

			StringBuilder sql = new StringBuilder();

			sql.append( "select ");
			
			sql.append( "oc.statusorc status, io.sitproditorc, io.dtaprovitorc dataaprov, ");
			sql.append( "io.dtaprovitorc + coalesce(oc.prazoentorc,0) dataentrega, oc.codorc, io.coditorc, cl.codcli, ");
			sql.append( "cl.razcli, pd.codprod, pd.descprod, coalesce(io.qtdaprovitorc,0) qtdaprov, ");

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
			
			sql.append( "where oc.codemp=? and oc.codfilial=? and io.aprovitorc='S' and io.sitproditorc='PE' ");
			
			if(txtCodProd.getVlrInteger()>0) {
				sql.append( " and io.codemppd=? and io.codfilialpd=? and io.codprod=? " );				
			}
			if(txtCodCli.getVlrInteger()>0) {
				sql.append( " and oc.codempcl=? and oc.codfilialcl=? and oc.codcli=? " );				
			}
			
			sql.append(" group by 1,2,3,4,5,6,7,8,9,10,11 ");
			
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
			BigDecimal totqtdemprod = new BigDecimal(0);
			BigDecimal totqtdaprod = new BigDecimal(0);
						
			while ( rs.next() ) {
				
				tabDet.adicLinha();
				
				tabDet.setValor( new Boolean(true), row, DETALHAMENTO.MARCACAO.ordinal() );
				tabDet.setValor( Funcoes.dateToStrDate( rs.getDate( DETALHAMENTO.DATAAPROV.toString() ) ), row, DETALHAMENTO.DATAAPROV.ordinal() );
				tabDet.setValor( Funcoes.dateToStrDate( rs.getDate( DETALHAMENTO.DATAENTREGA.toString() ) ), row, DETALHAMENTO.DATAENTREGA.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODORC.toString() ), row, DETALHAMENTO.CODORC.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODITORC.toString() ), row, DETALHAMENTO.CODITORC.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODCLI.toString() ), row, DETALHAMENTO.CODCLI.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.RAZCLI.toString().trim() ), row, DETALHAMENTO.RAZCLI.ordinal() );
				tabDet.setValor( rs.getInt( DETALHAMENTO.CODPROD.toString() ), row, DETALHAMENTO.CODPROD.ordinal() );
				tabDet.setValor( rs.getString( DETALHAMENTO.DESCPROD.toString().trim() ), row, DETALHAMENTO.DESCPROD.ordinal() );

				BigDecimal qtdaprov = rs.getBigDecimal( DETALHAMENTO.QTDAPROV.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdestoque = rs.getBigDecimal( DETALHAMENTO.QTDESTOQUE.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdemprod = rs.getBigDecimal( DETALHAMENTO.QTDEMPROD.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdaprod = qtdaprov.subtract( qtdestoque ).subtract( qtdemprod ).setScale( Aplicativo.casasDec );
				
				if(qtdaprod.floatValue() < 0) {
					qtdaprod = new BigDecimal(0);
				}
				
				totqtdaprov = totqtdaprov.add( qtdaprov );
				totqtdestoq = qtdestoque; // N�o deve ser somado, pois varia no produto
				totqtdemprod = qtdemprod; // N�o deve ser somado, pois vaira no produto
				
				if ( "PE".equals( rs.getString( "sitproditorc" ) ) ) {
					imgColuna = imgPendente;
				}
				else if ( "EP".equals( rs.getString( "sitproditorc" ) ) ) {
					imgColuna = imgProducao;
				}
				else if ( "PD".equals( rs.getString( "sitproditorc" ) ) ) {
					imgColuna = imgProduzido;
				}
											
				tabDet.setValor( imgColuna, row, DETALHAMENTO.STATUS.ordinal() );
								
				tabDet.setValor( qtdaprov, row, DETALHAMENTO.QTDAPROV.ordinal() );				
				tabDet.setValor( qtdestoque, row, DETALHAMENTO.QTDESTOQUE.ordinal() );
				tabDet.setValor( qtdemprod, row, DETALHAMENTO.QTDEMPROD.ordinal() );
				tabDet.setValor( qtdaprod , row, DETALHAMENTO.QTDAPROD.ordinal() );
				
				row++;
				
			}
			
			totqtdaprod = (totqtdestoq.subtract( totqtdaprov ));
			
			if(totqtdaprod.floatValue()>0) {
				totqtdaprod = new BigDecimal( 0 );
			}
			else {
				totqtdaprod = totqtdaprod.abs();
			}
			
			txtQuantidadeVendida.setVlrBigDecimal( totqtdaprov );
			if(txtCodProd.getVlrInteger()>0) {				
				txtQuantidadeEstoque.setVlrBigDecimal( totqtdestoq );
				txtQuantidadeProducao.setVlrBigDecimal( totqtdemprod );
				txtQuantidadeProduzir.setVlrBigDecimal( totqtdaprod );
			}
			else {
				txtQuantidadeEstoque.setVlrString( "-" );
				txtQuantidadeProducao.setVlrString( "-");
				txtQuantidadeProduzir.setVlrString( "-" );					
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
				 	"N".equals( cbAgrupDataEntrega.getVlrString() ) ) {		
			
				Funcoes.mensagemInforma( this, "Deve haver ao menos um crit�rio de agrupamento!" );
				return;
			}
			
			StringBuilder sql = new StringBuilder();

			sql.append( "select ");
			 
			sql.append( "pd.codprod, pd.descprod, coalesce(sp.sldliqprod,0) qtdestoque, sum(coalesce(io.qtdaprovitorc,0)) qtdaprov, sum(coalesce(op.qtdprevprodop,0)) qtdemprod ");
			
	
			int igroup = 5; // Numero padr�o de campos
			
			if( "S".equals( cbAgrupDataAprov.getVlrString() ) ) {
				sql.append( ",io.dtaprovitorc dataaprov ");
				igroup++; // Indica que foi adicionado um novo campo que deve fazer parte do groupby
			}
			
			if( "S".equals( cbAgrupDataEntrega.getVlrString() ) ) {
				sql.append( ",io.dtaprovitorc + coalesce(oc.prazoentorc,0) dataentrega ");
				igroup++;// Indica que foi adicionado um novo campo que deve fazer parte do groupby
			}

			if( "S".equals( cbAgrupCli.getVlrString() ) ) {
				sql.append( ",cl.codcli, cl.razcli ");
				igroup++;// Indica que foi adicionado um novo campo que deve fazer parte do groupby
				igroup++;// Indica que foi adicionado um novo campo que deve fazer parte do groupby
			}

			sql.append( "from vdorcamento oc left outer join vditorcamento io on ");
			sql.append( "io.codemp=oc.codemp and io.codfilial=oc.codfilial and io.codorc=oc.codorc and io.tipoorc=oc.tipoorc ");
			sql.append( "left outer join vdcliente cl on cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli ");
			sql.append( "left outer join eqproduto pd on pd.codemp=io.codemppd and pd.codfilial=io.codfilialpd and pd.codprod=io.codprod ");
			sql.append( "left outer join ppop op on op.codemppd=pd.codemp and op.codfilial=pd.codfilial and op.codprod=pd.codprod and op.sitop in ('PE','BL') ");
			sql.append( "left outer join eqsaldoprod sp on sp.codemp=pd.codemp and sp.codfilial=pd.codfilial and sp.codprod=pd.codprod ");

			sql.append( "where oc.codemp=? and oc.codfilial=? and io.aprovitorc='S' and io.sitproditorc='PE' ");

			if(txtCodProd.getVlrInteger()>0) {
				sql.append( " and io.codemppd=? and io.codfilialpd=? and io.codprod=? " );				
			}
			if(txtCodCli.getVlrInteger()>0) {
				sql.append( " and oc.codempcl=? and oc.codfilialcl=? and oc.codcli=? " );				
			}

			if( "S".equals( cbAgrupProd.getVlrString() ) ||  
			 	"S".equals( cbAgrupCli.getVlrString() ) ||
			 	"S".equals( cbAgrupDataAprov.getVlrString() ) ||
			 	"S".equals( cbAgrupDataEntrega.getVlrString() ) ) {			
			
				sql.append( "group by ");	
			
				if( "S".equals( cbAgrupProd.getVlrString() ) ) {
					sql.append( "1, 2, 3 ");				
				}
			
				if( igroup>5 )  {				
					for(int i=5; i<igroup; i++) {
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
			
			BigDecimal totqtdaprov = new BigDecimal(0);
			BigDecimal totqtdestoq = new BigDecimal(0);
			BigDecimal totqtdemprod = new BigDecimal(0);
			BigDecimal totqtdaprod = new BigDecimal(0);			
			
			while ( rs.next() ) {
				
				tabAgrup.adicLinha();
				
				tabAgrup.setValor( new Boolean(true), row, AGRUPAMENTO.MARCACAO.ordinal() );
				tabAgrup.setValor( rs.getInt( AGRUPAMENTO.CODPROD.toString() ), row, AGRUPAMENTO.CODPROD.ordinal() );
				tabAgrup.setValor( rs.getString( AGRUPAMENTO.DESCPROD.toString().trim() ), row, AGRUPAMENTO.DESCPROD.ordinal() );
				
				if("S".equals( cbAgrupDataAprov.getVlrString() )){
					tabAgrup.setColunaVisivel( 60, AGRUPAMENTO.DATAAPROV.ordinal() );					
					tabAgrup.setValor( Funcoes.dateToStrDate( rs.getDate( AGRUPAMENTO.DATAAPROV.toString() ) ), row, AGRUPAMENTO.DATAAPROV.ordinal() );
				}
				else {
					tabAgrup.setColunaInvisivel( AGRUPAMENTO.DATAAPROV.ordinal() );
				}
				
				if("S".equals( cbAgrupDataEntrega.getVlrString() )){
					tabAgrup.setColunaVisivel( 60, AGRUPAMENTO.DATAENTREGA.ordinal() );					
					tabAgrup.setValor( Funcoes.dateToStrDate( rs.getDate( AGRUPAMENTO.DATAENTREGA.toString() ) ), row, AGRUPAMENTO.DATAENTREGA.ordinal() );
				}
				else {
					tabAgrup.setColunaInvisivel( AGRUPAMENTO.DATAENTREGA.ordinal() );
				}
				
				if("S".equals( cbAgrupCli.getVlrString() )){
					tabAgrup.setColunaVisivel( 40, AGRUPAMENTO.CODCLI.ordinal() );
					tabAgrup.setColunaVisivel( 145,AGRUPAMENTO.RAZCLI.ordinal() );
					tabAgrup.setValor( rs.getInt( AGRUPAMENTO.CODCLI.toString() ), row, AGRUPAMENTO.CODCLI.ordinal() );
					tabAgrup.setValor( rs.getString( AGRUPAMENTO.RAZCLI.toString().trim() ), row, AGRUPAMENTO.RAZCLI.ordinal() );					
				}				
				else {
					tabAgrup.setColunaInvisivel( AGRUPAMENTO.CODCLI.ordinal() );
					tabAgrup.setColunaInvisivel( AGRUPAMENTO.RAZCLI.ordinal() );					
				}

				BigDecimal qtdaprov = rs.getBigDecimal( AGRUPAMENTO.QTDAPROV.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdestoque = rs.getBigDecimal( AGRUPAMENTO.QTDESTOQUE.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdemprod = rs.getBigDecimal( AGRUPAMENTO.QTDEMPROD.toString() ).setScale( Aplicativo.casasDec );
				BigDecimal qtdaprod = qtdaprov.subtract( qtdestoque ).subtract( qtdemprod ).setScale( Aplicativo.casasDec );
				
				if(qtdaprod.floatValue() < 0) {
					qtdaprod = new BigDecimal(0);
				}
				
				totqtdaprov = totqtdaprov.add( qtdaprov );
				totqtdestoq = qtdestoque; // N�o deve ser somado, pois varia no produto
				totqtdemprod = qtdemprod; // N�o deve ser somado, pois vaira no produto				
				 
				tabAgrup.setValor( imgColuna, row, AGRUPAMENTO.STATUS.ordinal() );
							
				tabAgrup.setValor( qtdaprov, row, AGRUPAMENTO.QTDAPROV.ordinal() );				
				tabAgrup.setValor( qtdestoque, row, AGRUPAMENTO.QTDESTOQUE.ordinal() );
				tabAgrup.setValor( qtdemprod, row, AGRUPAMENTO.QTDEMPROD.ordinal() );
				tabAgrup.setValor( qtdaprod , row, AGRUPAMENTO.QTDAPROD.ordinal() );
				
				row++;
				
			}
			
			totqtdaprod = (totqtdestoq.subtract( totqtdaprov ));
			
			if(totqtdaprod.floatValue()>0) {
				totqtdaprod = new BigDecimal( 0 );
			}
			else {
				totqtdaprod = totqtdaprod.abs();
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
			montaGrids();
		}
		else if ( e.getSource() == btSelectAllDet ) {
			selectAll(tabDet);
		}
		else if ( e.getSource() == btDeselectAllDet ) {
			deselectAll(tabDet);
		}
		else if ( e.getSource() == btSelectNecessariosDet ) {
			selectNecessarios(tabDet);
		}
		else if ( e.getSource() == btLimparGridDet ) {
			limpaNaoSelecionados( tabDet );
		}		
		else if ( e.getSource() == btSelectAllAgrup ) {
			selectAll(tabAgrup);
		}
		else if ( e.getSource() == btDeselectAllAgrup ) {
			deselectAll(tabAgrup);
		}
		else if ( e.getSource() == btSelectNecessariosAgrup ) {
			selectNecessarios(tabAgrup);
		}
		else if ( e.getSource() == btLimparGridAgrup ) {
			limpaNaoSelecionados( tabAgrup );
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
		Tabela tabEv = (Tabela) mevt.getSource();
		
		if ( mevt.getClickCount() == 2 ) {					
			if( tabEv == tabDet && tabEv.getLinhaSel() > -1 ) {
				FOrcamento orc = null;    
				if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
					orc = (FOrcamento) Aplicativo.telaPrincipal.getTela( FOrcamento.class.getName() );
				}
				else {
					orc = new FOrcamento();
					Aplicativo.telaPrincipal.criatela( "Or�amento", orc, con );
				}    	    		 
				orc.exec( (Integer) tabEv.getValor( tabEv.getLinhaSel(), DETALHAMENTO.CODORC.ordinal() ) );				
			}
		}
		if ( (tabEv == tabDet || tabEv == tabAgrup) && (tabEv.getLinhaSel() > -1) ) {
				tabEv.setValor( ! ( (Boolean) tabEv.getValor( tabEv.getLinhaSel(), 0 ) ).booleanValue(), tabEv.getLinhaSel(), 0 );			
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

	private void montaGrids() {
		montaGridDet();
		montaGridAgrup();
	}
	
	public void afterCarrega( CarregaEvent e ) {

		if ( lcProd == e.getListaCampos() ) {
			montaGrids();
		}
		else if ( lcCliente == e.getListaCampos() ) {
			montaGrids();
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
	
	private void selectAll(Tabela tab) {
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( true ), i, 0 );
		}
	}
	
	private void limpaNaoSelecionados(Tabela tab) {
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
	
	private void selectNecessarios(Tabela tab) {
		BigDecimal qtdaprod = null;
		
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			qtdaprod = (BigDecimal) tab.getValor( i, tab==tabDet ? DETALHAMENTO.QTDAPROD.ordinal() : AGRUPAMENTO.QTDAPROD.ordinal() );			
			tab.setValor( new Boolean (qtdaprod.floatValue()>0), i, 0 );			
		}
	}

	private void deselectAll(Tabela tab) {
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( false ), i, 0 );
		}
	}
	
}

