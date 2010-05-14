/*
 * Projeto: Freedom
 * Pacote: org.freedom.modules.crm
 * Classe: @(#)FConsultaCli.java
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

package org.freedom.modulos.crm.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.std.view.frame.crud.detail.FVenda;


/**
 * Consulta de informa��es referente a clientes.
 * 
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues
 * @version 24/08/2009
 */
public class FConsultaCli extends FFilho implements ActionListener, TabelaSelListener, MouseListener, KeyListener, CarregaListener {

	private static final long serialVersionUID = 1L;
	
	private static final Color GREEN = new Color( 45, 190, 60 );

	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelMaster = new JPanelPad( 700, 140 );

	private JPanelPad panelDetail = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JTabbedPanePad tabbedDetail = new JTabbedPanePad();

	private JPanelPad panelVendas = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelTabVendas = new JPanelPad( 700, 60 );

	private JPanelPad panelGridVendas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );

	private JPanelPad panelTabVendasNotas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelTabItensVendas = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad panelReceber = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelHistorico = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelSouth = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1, 10, 10 ) );
	
	// *** Geral

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDDDCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtFoneCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtEmailCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtContCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtAtivoCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JLabelPad lbAtivoCli = new JLabelPad( "Ativo", SwingConstants.CENTER );
	
	private JButtonPad btBuscar = new JButtonPad( "Buscar vendas", Icone.novo( "btExecuta.gif" ) );
	
	// *** Vendas 

	private JTextFieldFK txtUltimaCompra = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldFK txtVlrUltimaCompra = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDecFin );
	
	private JTextFieldFK txtTotalCompras = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDecFin );
	
	private JTextFieldFK txtTotalAberto = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 12, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTablePad tabVendas = new JTablePad();
	
	private JTablePad tabItensVendas = new JTablePad();
	
	private ImageIcon imgCancelado = Icone.novo( "clVencido.gif" );

	private ImageIcon imgPedido = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgFaturado = Icone.novo( "clPago.gif" );

	private ImageIcon imgColuna = null;
	
	// *** Listacampos

	private ListaCampos lcCliente = new ListaCampos( this, "CL" );
	
	private ListaCampos lcProd = new ListaCampos( this );
	
	private boolean carregandoVendas = false;
	
	
	private enum VENDAS {
		STATUS, CODVENDA, NOTA, DATA, PAGAMENTO, VENDEDOR, VALOR_PRODUTOS, VALOR_DESCONTO, VALOR_ADICIONAL, VALOR_FRETE, VALOR_LIQUIDO, TIPOVENDA;
	}
	
	private enum ITEMVENDAS {
		ITEM, CODPROD, DESCPROD, LOTE, QUANTIDADE, PRECO, DESCONTO, FRETE, TOTAL, TIPOVENDA;
	}
	

	public FConsultaCli() {

		super( false );
		setTitulo( "Consulta de clientes", this.getClass().getName() );
		setAtribos( 20, 20, 780, 600 );
    	int x = (int) (Aplicativo.telaPrincipal.dpArea.getSize().getWidth()-getWidth())/2;
    	int y = (int) (Aplicativo.telaPrincipal.dpArea.getSize().getHeight()-getHeight())/2;
    	setLocation( x, y );
		
		montaListaCampos();
		montaTela();
		
		lcCliente.addCarregaListener( this );
		lcProd.addCarregaListener( this );
		btBuscar.addActionListener( this );
		tabVendas.addTabelaSelListener( this );	
		tabVendas.addMouseListener( this );	
		tabItensVendas.addMouseListener( this );	
		btBuscar.addKeyListener( this );
		
		Calendar periodo = Calendar.getInstance();
		txtDatafim.setVlrDate( periodo.getTime() );
		periodo.set( Calendar.YEAR, periodo.get( Calendar.YEAR ) - 1 );
		txtDataini.setVlrDate( periodo.getTime() );
	}

	private void montaListaCampos() {
	
		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtDDDCli, "DDDCli", "DDD", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtFoneCli, "FoneCli", "Telefone", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtEmailCli, "EmailCli", "E-Mail", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtContCli, "ContCli", "Contato", ListaCampos.DB_SI, false ) );
		lcCliente.add( new GuardaCampo( txtAtivoCli, "AtivoCli", "ativo", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCliente, null );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCliente.setReadOnly( true );
		lcCliente.montaSql( false, "CLIENTE", "VD" );		
		
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProd, null );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );

	}

	private void montaTela() {
		
		getTela().add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelMaster, BorderLayout.NORTH );
		
		// ***** Cabe�alho

		JLabel periodo = new JLabel( "Per�odo", SwingConstants.CENTER );
		periodo.setOpaque( true );
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		
		panelMaster.adic( new JLabelPad( "C�d.Cli" ), 7, 5, 60, 20 );
		panelMaster.adic( txtCodCli, 7, 25, 60, 20 );
		panelMaster.adic( new JLabelPad( "Raz�o social do cliente" ), 70, 5, 340, 20 );
		panelMaster.adic( txtRazCli, 70, 25, 340, 20 );
		
		panelMaster.adic( new JLabelPad( "Contato" ), 413, 5, 100, 20 );
		panelMaster.adic( txtContCli, 413, 25, 100, 20 );
		
		panelMaster.adic( new JLabelPad( "DDD" ), 7, 45, 60, 20 );
		panelMaster.adic( txtDDDCli, 7, 65, 60, 20 );
		panelMaster.adic( new JLabelPad( "Fone" ), 70, 45, 75, 20 );
		panelMaster.adic( txtFoneCli, 70, 65, 75, 20 );
		panelMaster.adic( new JLabelPad( "e-mail" ), 148, 45, 262, 20 );
		panelMaster.adic( txtEmailCli, 148, 65, 262, 20 );
		
		panelMaster.adic( lbAtivoCli, 413, 65, 100, 20 );
		
		panelMaster.adic( new JLabelPad( "C�d.Prod." ), 7, 85, 60, 20 );
		panelMaster.adic( txtCodProd, 7, 105, 60, 20 );
		panelMaster.adic( new JLabelPad( "Descri��o do produto" ), 70, 85, 340, 20 );
		panelMaster.adic( txtDescProd, 70, 105, 340, 20 );

		panelMaster.adic( periodo, 540, 0, 60, 20 );
		panelMaster.adic( borda, 530, 10, 220, 45 );
		panelMaster.adic( txtDataini, 540, 25, 80, 20 );
		panelMaster.adic( new JLabel( "at�", SwingConstants.CENTER ), 620, 25, 40, 20 );
		panelMaster.adic( txtDatafim, 660, 25, 80, 20 );

		panelMaster.adic( btBuscar, 530, 60, 220, 30 );
		
		txtFoneCli.setMascara( JTextFieldPad.MC_FONE );
		
		lbAtivoCli.setOpaque( true );
		lbAtivoCli.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbAtivoCli.setBackground( GREEN );
		lbAtivoCli.setForeground( Color.WHITE );
		
		// ***** Detalhamento (abas)
		
		panelGeral.add( panelDetail, BorderLayout.CENTER );
		panelDetail.add( tabbedDetail );
		tabbedDetail.addTab( "Vendas", panelVendas );
		//tabbedDetail.addTab( "Receber", panelReceber );
		//tabbedDetail.addTab( "Hist�rico", panelHistorico );
		
		// ***** Vendas
		
		panelVendas.add( panelTabVendas, BorderLayout.NORTH );
		panelVendas.add( panelGridVendas, BorderLayout.CENTER );		
		panelGridVendas.add( panelTabVendasNotas );
		panelGridVendas.add( panelTabItensVendas );
		
		panelTabVendasNotas.setBorder( BorderFactory.createTitledBorder( "Vendas" ) );
		panelTabItensVendas.setBorder( BorderFactory.createTitledBorder( "Itens de vendas" ) );
		panelTabItensVendas.setPreferredSize( new Dimension( 700, 120 ) );
		
		panelTabVendas.adic( new JLabelPad( "�ltima Compra" ), 10, 10, 100, 20 );
		panelTabVendas.adic( txtUltimaCompra, 10, 30, 100, 20 );
		panelTabVendas.adic( new JLabelPad( "Valor �ltima compra" ), 113, 10, 120, 20 );
		panelTabVendas.adic( txtVlrUltimaCompra, 113, 30, 120, 20 );
		panelTabVendas.adic( new JLabelPad( "Total de compras" ), 236, 10, 120, 20 );
		panelTabVendas.adic( txtTotalCompras, 236, 30, 120, 20 );
		panelTabVendas.adic( new JLabelPad( "Valor em aberto" ), 359, 10, 120, 20 );
		panelTabVendas.adic( txtTotalAberto, 359, 30, 120, 20 );
		
		Color statusColor = new Color( 111, 106, 177 );
		Font statusFont = new Font( "Tomoha", Font.PLAIN, 11 );
		
		panelTabVendas.adic( new JLabelPad( imgCancelado ), 600, 5, 20, 15 );
		JLabelPad canceladas = new JLabelPad( "canceladas" );
		canceladas.setForeground( statusColor );
		canceladas.setFont( statusFont );
		panelTabVendas.adic( canceladas, 620, 5, 100, 15 );
		
		panelTabVendas.adic( new JLabelPad( imgPedido ), 600, 20, 20, 15 );
		JLabelPad pedidos = new JLabelPad( "pedidos" );
		pedidos.setForeground( statusColor );
		pedidos.setFont( statusFont );
		panelTabVendas.adic( pedidos, 620, 20, 100, 15 );
		
		panelTabVendas.adic( new JLabelPad( imgFaturado ), 600, 35, 20, 15 );
		JLabelPad faturadas = new JLabelPad( "faturadas" );
		faturadas.setForeground( statusColor );
		faturadas.setFont( statusFont );
		panelTabVendas.adic( faturadas, 620, 35, 100, 15 );
				
		tabVendas.adicColuna( "" );
		tabVendas.adicColuna( "C�digo" );
		tabVendas.adicColuna( "Doc." );
		tabVendas.adicColuna( "Data" );
		tabVendas.adicColuna( "Plano Pgto." );
		tabVendas.adicColuna( "Vendedor" );
		tabVendas.adicColuna( "V.Produtos" );
		tabVendas.adicColuna( "V.Desc." );
		tabVendas.adicColuna( "V.Adic." );
		tabVendas.adicColuna( "V.Frete" );
		tabVendas.adicColuna( "V.L�quido" );
		tabVendas.adicColuna( "Tipo Venda" );

		tabVendas.setTamColuna( 20, VENDAS.STATUS.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.CODVENDA.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.NOTA.ordinal() );
		tabVendas.setTamColuna( 70, VENDAS.DATA.ordinal() );
		tabVendas.setTamColuna( 100, VENDAS.PAGAMENTO.ordinal() );
		tabVendas.setTamColuna( 100, VENDAS.VENDEDOR.ordinal() );
		tabVendas.setTamColuna( 80, VENDAS.VALOR_PRODUTOS.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.VALOR_DESCONTO.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.VALOR_ADICIONAL.ordinal() );
		tabVendas.setTamColuna( 60, VENDAS.VALOR_FRETE.ordinal() );
		tabVendas.setTamColuna( 80, VENDAS.VALOR_LIQUIDO.ordinal() );
		tabVendas.setColunaInvisivel( VENDAS.TIPOVENDA.ordinal() );
						
		panelTabVendasNotas.add( new JScrollPane( tabVendas ) );

		tabItensVendas.adicColuna( "Item" );
		tabItensVendas.adicColuna( "C�digo" );
		tabItensVendas.adicColuna( "Descri��o do produto" );
		tabItensVendas.adicColuna( "Lote" );
		tabItensVendas.adicColuna( "Qtd." );
		tabItensVendas.adicColuna( "Pre�o" );
		tabItensVendas.adicColuna( "V.Desc." );
		tabItensVendas.adicColuna( "V.Frete" );
		tabItensVendas.adicColuna( "V.l�q." );
		tabItensVendas.adicColuna( "TipoVenda" );

		tabItensVendas.setTamColuna( 30, ITEMVENDAS.ITEM.ordinal() );
		tabItensVendas.setTamColuna( 50, ITEMVENDAS.CODPROD.ordinal() );
		tabItensVendas.setTamColuna( 200, ITEMVENDAS.DESCPROD.ordinal() );
		tabItensVendas.setTamColuna( 70, ITEMVENDAS.LOTE.ordinal() );
		tabItensVendas.setTamColuna( 50, ITEMVENDAS.QUANTIDADE.ordinal() );
		tabItensVendas.setTamColuna( 70, ITEMVENDAS.PRECO.ordinal() );
		tabItensVendas.setTamColuna( 80, ITEMVENDAS.DESCONTO.ordinal() );
		tabItensVendas.setTamColuna( 80, ITEMVENDAS.FRETE.ordinal() );
		tabItensVendas.setTamColuna( 90, ITEMVENDAS.TOTAL.ordinal() );
		tabItensVendas.setColunaInvisivel( ITEMVENDAS.TIPOVENDA.ordinal() );
				
		panelTabItensVendas.add( new JScrollPane( tabItensVendas ) );
		
		// ***** Rodap�
		
		panelGeral.add( panelSouth, BorderLayout.SOUTH );
		panelSouth.setBorder( BorderFactory.createEtchedBorder() );
		panelSouth.add( adicBotaoSair() );
	}
	
	private void buscaVendas() {
		
		if ( txtCodCli.getVlrInteger() == 0 && txtCodProd.getVlrInteger() == 0 ) {
			Funcoes.mensagemInforma( this, "Selecione um cliente ou produto!" );
			txtCodCli.requestFocus();
			return;
		}
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT V.CODVENDA, V.DOCVENDA, V.DTEMITVENDA, V.STATUSVENDA, V.CODPLANOPAG," );
			sql.append( "P.DESCPLANOPAG, V.CODVEND, VD.NOMEVEND, V.VLRPRODVENDA, V.VLRDESCVENDA," );
			sql.append( "V.VLRADICVENDA, V.VLRFRETEVENDA, V.VLRLIQVENDA, V.TIPOVENDA " );
			sql.append( "FROM VDVENDA V, FNPLANOPAG P, VDVENDEDOR VD " );
			sql.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' AND V.DTEMITVENDA BETWEEN ? AND ? AND " );
			sql.append( "P.CODEMP=V.CODEMPPG AND P.CODFILIAL=V.CODFILIALPG AND P.CODPLANOPAG=V.CODPLANOPAG AND " );
			sql.append( "VD.CODEMP=V.CODEMPVD AND VD.CODFILIAL=V.CODFILIALVD AND VD.CODVEND=V.CODVEND" );
			
			if(txtCodCli.getVlrInteger()>0) {
				sql.append( " AND V.CODEMPCL=? AND V.CODFILIALCL=? AND CODCLI=? ");
			}
			
			if(txtCodProd.getVlrInteger()>0) {
				sql.append( " AND EXISTS (" );
				sql.append( " SELECT CODVENDA FROM VDITVENDA IV WHERE ");
				sql.append( " IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA ");
				sql.append( " AND IV.CODEMPPD=? AND IV.CODFILIALPD=? AND IV.CODPROD=? ) " );
				
			}
			
			sql.append( " ORDER BY V.DTEMITVENDA DESC ");
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			int iparam = 1;
			
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			if(txtCodCli.getVlrInteger()>0) {
				ps.setInt( iparam++, lcCliente.getCodEmp() );
				ps.setInt( iparam++, lcCliente.getCodFilial() );
				ps.setInt( iparam++, txtCodCli.getVlrInteger() );				
			}			
			
			if(txtCodProd.getVlrInteger()>0) {
				ps.setInt( iparam++, lcProd.getCodEmp() );
				ps.setInt( iparam++, lcProd.getCodFilial() );
				ps.setInt( iparam++, txtCodProd.getVlrInteger() );								
			}			
			
			ResultSet rs = ps.executeQuery();		
			
			carregandoVendas = true;
			tabVendas.limpa();
			tabItensVendas.limpa();
			
			int row = 0;
			while ( rs.next() ) {
				
				tabVendas.adicLinha();
				
				if ( "C".equals( rs.getString( "STATUSVENDA" ).substring( 0, 1 ) ) ) {
					imgColuna = imgCancelado;
				}
				else if ( "P".equals( rs.getString( "STATUSVENDA" ).substring( 0, 1 ) ) ) {
					imgColuna = imgPedido;
				}
				else {
					imgColuna = imgFaturado;
				}
								
				tabVendas.setValor( imgColuna, row, VENDAS.STATUS.ordinal() );
				tabVendas.setValor( rs.getInt( "CODVENDA" ), row, VENDAS.CODVENDA.ordinal() );
				tabVendas.setValor( rs.getString( "DOCVENDA" ), row, VENDAS.NOTA.ordinal() );
				tabVendas.setValor( Funcoes.dateToStrDate( rs.getDate( "DTEMITVENDA" ) ), row, VENDAS.DATA.ordinal() );
				tabVendas.setValor( rs.getString( "DESCPLANOPAG" ), row, VENDAS.PAGAMENTO.ordinal() );
				tabVendas.setValor( rs.getString( "NOMEVEND" ), row, VENDAS.VENDEDOR.ordinal() );
				tabVendas.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRPRODVENDA" ) ), row, VENDAS.VALOR_PRODUTOS.ordinal() );
				tabVendas.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRDESCVENDA" ) ), row, VENDAS.VALOR_DESCONTO.ordinal() );
				tabVendas.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRADICVENDA" ) ), row, VENDAS.VALOR_ADICIONAL.ordinal() );
				tabVendas.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRFRETEVENDA" ) ), row, VENDAS.VALOR_FRETE.ordinal() );
				tabVendas.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRLIQVENDA" ) ), row, VENDAS.VALOR_LIQUIDO.ordinal() );
				tabVendas.setValor( rs.getString( "TIPOVENDA" ) , row, VENDAS.TIPOVENDA.ordinal() );
				
				row++;
			}
			
			sql = new StringBuilder();
			sql.append( "SELECT FIRST 1 V.DTEMITVENDA, V.VLRLIQVENDA, V.CODVENDA " );
			sql.append( "FROM VDVENDA V, VDCLIENTE C " );
			sql.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' AND V.DTEMITVENDA BETWEEN ? AND ? AND " );
			sql.append( "C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI AND " );
			sql.append( "C.CODEMP=? AND C.CODFILIAL=? AND C.CODCLI=? " );
			sql.append( "ORDER BY V.DTEMITVENDA DESC" );
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 7, txtCodCli.getVlrInteger() );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				txtUltimaCompra.setVlrDate( Funcoes.sqlDateToDate( rs.getDate( "DTEMITVENDA" ) ) );
				txtVlrUltimaCompra.setVlrBigDecimal( rs.getBigDecimal( "VLRLIQVENDA" ) );
			}
			
			sql = new StringBuilder();
			sql.append( "SELECT SUM(V.VLRLIQVENDA) TOTAL, SUM(R.VLRAPAGREC) TOTAL_ABERTO " );
			sql.append( "FROM VDVENDA V, VDCLIENTE C, FNRECEBER R " );
			sql.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' AND V.DTEMITVENDA BETWEEN ? AND ? AND " );
			sql.append( "C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI AND " );
			sql.append( "C.CODEMP=? AND C.CODFILIAL=? AND C.CODCLI=? AND " );
			sql.append( "R.CODEMPVD=V.CODEMP AND R.CODFILIALVD=V.CODFILIAL AND R.CODVENDA=V.CODVENDA AND R.TIPOVENDA=V.TIPOVENDA " );
			sql.append( "GROUP BY V.CODCLI " );
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 7, txtCodCli.getVlrInteger() );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				txtTotalCompras.setVlrBigDecimal( rs.getBigDecimal( "TOTAL" ) );
				txtTotalAberto.setVlrBigDecimal( rs.getBigDecimal( "TOTAL_ABERTO" ) );				
			}
			
			if(row>0) {
				//Selecionando primeiro do grid e carregando itens
				
				tabVendas.setLinhaSel( 0 );
				buscaItensVenda( (Integer)tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.CODVENDA.ordinal() ), "V" );
			}
			
			rs.close();
			ps.close();
			con.commit();
			
			carregandoVendas = false;
			tabVendas.requestFocus();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private void buscaItensVenda( int codvenda, String tipovenda ) {
				
		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT I.CODITVENDA, I.CODPROD, P.DESCPROD, I.CODLOTE, " );
			sql.append( "I.QTDITVENDA, I.PRECOITVENDA, I.VLRDESCITVENDA, I.VLRFRETEITVENDA, I.VLRLIQITVENDA " );
			sql.append( "FROM VDITVENDA I, EQPRODUTO P " );
			sql.append( "WHERE I.CODEMP=? AND I.CODFILIAL=? AND I.CODVENDA=? AND I.TIPOVENDA=? AND " );
			sql.append( "P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD " );
			
			if(txtCodProd.getVlrInteger()>0) {
				sql.append( " AND P.CODPROD=? " );
			}
			
			sql.append( "ORDER BY I.CODITVENDA" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			int iparam = 1;
			
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDITVENDA" ) );
			ps.setInt( iparam++, codvenda );
			ps.setString( iparam++, tipovenda );
			
			if(txtCodProd.getVlrInteger()>0) {
				ps.setInt( iparam++, txtCodProd.getVlrInteger() );
			}
			
			ResultSet rs = ps.executeQuery();		
			
			tabItensVendas.limpa();
			
			int row = 0;
			while ( rs.next() ) {
				
				tabItensVendas.adicLinha();

				tabItensVendas.setValor( rs.getInt( "CODITVENDA" ), row, ITEMVENDAS.ITEM.ordinal() );
				tabItensVendas.setValor( rs.getInt( "CODPROD" ), row, ITEMVENDAS.CODPROD.ordinal() );
				tabItensVendas.setValor( rs.getString( "DESCPROD" ), row, ITEMVENDAS.DESCPROD.ordinal() );
				tabItensVendas.setValor( rs.getString( "CODLOTE" ), row, ITEMVENDAS.LOTE.ordinal() );
				tabItensVendas.setValor( Funcoes.bdToStr( rs.getBigDecimal( "QTDITVENDA" ) ), row, ITEMVENDAS.QUANTIDADE.ordinal() );
				tabItensVendas.setValor( Funcoes.bdToStr( rs.getBigDecimal( "PRECOITVENDA" ) ), row, ITEMVENDAS.PRECO.ordinal() );
				tabItensVendas.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRDESCITVENDA" ) ), row, ITEMVENDAS.DESCONTO.ordinal() );
				tabItensVendas.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRFRETEITVENDA" ) ), row, ITEMVENDAS.FRETE.ordinal() );
				tabItensVendas.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRLIQITVENDA" ) ), row, ITEMVENDAS.TOTAL.ordinal() );
				tabItensVendas.setValor( tipovenda, row, ITEMVENDAS.TIPOVENDA.ordinal());
				
				row++;
			}
			
			rs.close();
			ps.close();
			con.commit();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btBuscar ) {
			buscaVendas();
		}
	}

	public void valorAlterado( TabelaSelEvent e ) {
		
		if ( e.getTabela() == tabVendas && tabVendas.getLinhaSel() > -1 && !carregandoVendas ) {
			buscaItensVenda( (Integer)tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.CODVENDA.ordinal() ), (String) tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.TIPOVENDA.ordinal()) );
		}
	}

	public void mouseClicked( MouseEvent e ) {
		
		if ( e.getClickCount() == 2 ) {
			if ( e.getSource() == tabVendas && tabVendas.getLinhaSel() > -1 ) {
				FVenda venda = null;    
	    		if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
	    			venda = (FVenda)Aplicativo.telaPrincipal.getTela( FVenda.class.getName() );
	    		}
	    		else {
	    			venda = new FVenda();
	    			Aplicativo.telaPrincipal.criatela( "Venda", venda, con );
	    		}    
				venda.exec( (Integer)tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.CODVENDA.ordinal() ), 
						    (String)tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.TIPOVENDA.ordinal() ));
			}
			else if ( e.getSource() == tabItensVendas && tabItensVendas.getLinhaSel() > -1 ) {
				FVenda venda = null;    
	    		if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
	    			venda = (FVenda)Aplicativo.telaPrincipal.getTela( FVenda.class.getName() );
	    		} 
	    		else {
	    			venda = new FVenda();
	    			Aplicativo.telaPrincipal.criatela( "Venda", venda, con );
	    		}    		
				venda.exec( (Integer)tabVendas.getValor( tabVendas.getLinhaSel(), VENDAS.CODVENDA.ordinal() ),
							(Integer)tabItensVendas.getValor( tabItensVendas.getLinhaSel(), ITEMVENDAS.ITEM.ordinal() ),
							(String)tabItensVendas.getValor( tabItensVendas.getLinhaSel(), ITEMVENDAS.TIPOVENDA.ordinal() ));
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

		if ( lcCliente == e.getListaCampos() ) {
			if ( "S".equals( txtAtivoCli.getVlrString().trim() ) ) {
				lbAtivoCli.setText( "Ativo" );
				lbAtivoCli.setBackground( GREEN );
			} 
			else {
				lbAtivoCli.setText( "Inativo" );
				lbAtivoCli.setBackground( Color.RED );
			}
			buscaVendas();
		}		
		if ( lcProd == e.getListaCampos() ) {
			buscaVendas();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCliente.setConexao( con );
		lcProd.setConexao( con );
		
	}
}
