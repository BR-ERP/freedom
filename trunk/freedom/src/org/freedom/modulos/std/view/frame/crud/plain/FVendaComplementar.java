/**
 * @version 14/07/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gms <BR>
 *         Classe:
 * @(#)DLAdicPedCompra.java <BR>
 * 
 *                          Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                          modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                          na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                          Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                          sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                          Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                          Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                          de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                          Dialog para busca e gera��o de pedido de compra com base em outros pedidos de compra.
 */

package org.freedom.modulos.std.view.frame.crud.plain;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.std.inter.InterVenda;
import org.freedom.modulos.std.view.frame.crud.detail.FVenda;

public class FVendaComplementar extends FFilho implements ActionListener, CarregaListener, FocusListener, JComboBoxListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad( 0, 105 );
	
	private JPanelPad pinDet = new JPanelPad( 0, 70 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnSubRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinBtSelCp = new JPanelPad( 40, 110 );

	private JPanelPad pinRod = new JPanelPad( 480, 55 );

	private JPanelPad pinSair = new JPanelPad( 120, 45 );
	
	private JPanelPad pinGerar = new JPanelPad( 140, 45 );

	private JPanelPad pinBtSel = new JPanelPad( 40, 110 );

	private JPanelPad pnFor = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnTabCompra = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnForTab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );
	
	private JTextFieldFK txtCodTipoMov = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 9, 0 );
	
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldFK.TP_STRING, 40, 0 );
	
	private JTextFieldFK txtDocVenda = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 9, 0 );

	private JTextFieldFK txtSerie = new JTextFieldFK( JTextFieldFK.TP_STRING, 4, 0 );

	private JTextFieldFK txtDtSaidaVenda = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDtEmitVenda = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtCodCli = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldFK txtStatusVenda = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtVlrProd = new JTextFieldFK( JTextFieldFK.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtVlrDesc = new JTextFieldFK( JTextFieldFK.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtVlrProdVenda = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtVlrLiqVenda = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtVlrLiq = new JTextFieldFK( JTextFieldFK.TP_DECIMAL, 15, 2 );
	
	private JButtonPad btGerar = new JButtonPad( "Gerar", Icone.novo( "btGerar.png" ) );

	private JButtonPad btTodosItCompra = new JButtonPad( Icone.novo( "btTudo.png" ) );

	private JButtonPad btNenhumItCompra = new JButtonPad( Icone.novo( "btNada.png" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.png" ) );
	
	private JButtonPad btExec = new JButtonPad( Icone.novo( "btExecuta.png" ) );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );
	
	private ListaCampos lcVenda = new ListaCampos( this, "VD" );
	
	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );
	
	private Vector<Integer> vValsTipoMov = new Vector<Integer>();

	private Vector<String> vLabsTipoMov = new Vector<String>();
	
	private JComboBoxPad cbTipoMov =  new JComboBoxPad( vLabsTipoMov, vValsTipoMov, JComboBoxPad.TP_STRING, 60, 0 );
	
	private FVenda telavenda = null;
	
	private InterVenda venda = null;
	
	private Map<String, String> prefs = null;

	private Vector<Vector<Object>> tipos = new Vector<Vector<Object>>();
	
	
	
	public FVendaComplementar( Object vd, InterVenda venda ) {

		super(false);
		
		this.venda = venda;
		
		setTitulo( "Gera nota complementar de venda" );

		telavenda = (FVenda) vd;

		setAtribos(50,50, 750, 250 );
		
		montaComboBox();

		montaTela();

		habilitaCampos();

		montaListaCampos();

		adicListeners();

		adicToolTips();

	}
	
	private void montaComboBox() {
		
		vValsTipoMov.addElement( 0 );
		vLabsTipoMov.addElement( "<N�o Selecionado>" );
		
		for ( int i = 0;  i < tipos.size(); i++ ) {
			if (tipos.size() > 1) {
				cbTipoMov.setEnabled( true );
			}
			Integer codtpmov = new Integer((String) tipos.elementAt( i ).elementAt( 0 ));
			String descricao = (String) tipos.elementAt( i ).elementAt( 1 );
			
			vValsTipoMov.addElement(codtpmov );
			vLabsTipoMov.addElement( codtpmov + " - " + descricao  );
		}
	
		cbTipoMov.setItensGeneric( vLabsTipoMov, vValsTipoMov );
	
		vValsTipoMov.clear();
		vLabsTipoMov.clear();
		
	}
	

	private void habilitaCampos() {
		//btGerar.setEnabled( false );

	}

	private void montaTela() {

		c.setLayout( new BorderLayout() );
		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pinDet, BorderLayout.CENTER );
		c.add( pinCab, BorderLayout.NORTH );

		pinCab.adic( txtCodVenda, 7, 25, 60, 20, "Compra" );

		pinCab.adic( txtCodCli, 70, 25, 50, 20, "C�d.For." );

		pinCab.adic( txtRazCli, 123, 25, 250, 20, "Raz�o social do fornecedor" );
	
		pinCab.adic( txtSerie, 376, 25, 60, 20, "Serie." );
		
		pinCab.adic( txtDocVenda, 439, 25, 60, 20, "Doc." );
		
		pinCab.adic( txtDtEmitVenda, 502, 25, 80, 20, "Dt.emiss�o" );

		pinCab.adic( txtDtSaidaVenda, 585, 25, 80, 20, "Dt.sa�da" );
		
		pinCab.adic( txtCodTipoMov, 7, 65, 80, 20, "C�d.tp.mov" );
		
		pinCab.adic( txtDescTipoMov, 90, 65, 250, 20, "Descri��o do tipo de movimento" );
		
		pinCab.adic( txtVlrLiqVenda, 343, 65, 80, 20, "Vlr.total" );
		
		//cbTipoMov.setEnabled( false );
		pinDet.adic( cbTipoMov, 7, 20, 400, 25, "Tipo de movimento utilizado para gerar esta nota." ); 

		pnRod.setPreferredSize( new Dimension( 600, 50 ) );

		pnSubRod.setPreferredSize( new Dimension( 600, 50 ) );
		pnRod.add( pnSubRod, BorderLayout.SOUTH );

		pinSair.tiraBorda();
		pinSair.adic( btSair, 10, 10, 100, 30 );
		
		
		pinGerar.tiraBorda();
		pinGerar.adic( btGerar, 10, 10, 118, 30);
		
		btSair.setPreferredSize( new Dimension( 120, 30 ) );

		pnSubRod.add( pinGerar, BorderLayout.WEST );
		pnSubRod.add( pinSair, BorderLayout.EAST );
		pnSubRod.add( pinRod, BorderLayout.CENTER );
		

		pinRod.tiraBorda();
	/*	pinRod.adic( new JLabelPad( "Vlr.bruto" ), 7, 0, 100, 20 );
		pinRod.adic( txtVlrProd, 7, 20, 100, 20 );
		pinRod.adic( new JLabelPad( "Vlr.desc." ), 110, 0, 97, 20 );
		pinRod.adic( txtVlrDesc, 110, 20, 97, 20 );
		pinRod.adic( new JLabelPad( "Vlr.liq." ), 210, 0, 97, 20 );
		pinRod.adic( txtVlrLiq, 210, 20, 97, 20 );*/
/*		
		pinRod.adic( txtVlrProdCompra, 7, 20, 80, 20, "Vlr.Prod." );
		pinRod.adic( txtVlrLiqCompra, 90, 20, 80, 20, "Vlr.Liq." );
		pinRod.adic( txtVlrICMSCompra, 173, 20, 80, 20, "Vl.ICMS" );
		pinRod.adic( txtVlrIPICompra, 256, 20, 80, 20, "Vl.IPI" );
		pinRod.adic( txtVlrPISCompra, 339, 20, 80, 20, "Vl.PIS" );
		pinRod.adic( txtVlrCOFINSCompra, 422, 20, 80, 20, "Vl.COFINS" );
		pinRod.adic( txtVlrIICOMPRA, 505, 20, 80, 20, "Vl.II" );
*/		
	
		
	}

	private void adicToolTips() {

		btTodosItCompra.setToolTipText( "Selecionar tudo" );
		btNenhumItCompra.setToolTipText( "Limpar sele��o" );
		btGerar.setToolTipText( "Gerar venda" );
		btExec.setToolTipText( "Gerar itens da compra" );

	}

	private void adicListeners() {
/*
		tabitcompra.addKeyListener( this );
		tabcompra.addKeyListener( this );

		btBuscar.addKeyListener( this );
		btGerar.addKeyListener( this );
*/
		txtCodVenda.addActionListener( this );
		btSair.addActionListener( this );
		
		btGerar.addActionListener( this );
		
		btExec.addActionListener( this );
		btTodosItCompra.addActionListener( this );
		btNenhumItCompra.addActionListener( this );

		lcVenda.addCarregaListener( this );
		lcCli.addCarregaListener( this );
		txtCodVenda.addFocusListener( this );
		
		cbTipoMov.addComboBoxListener( this );
				//addWindowListener( this );

	}

	private void montaListaCampos() {

		// Lista campos do pedido de compra

		lcVenda.add( new GuardaCampo( txtCodVenda, "CodVenda", "C�d.venda", ListaCampos.DB_PK, null, false ) );
		lcVenda.add( new GuardaCampo( txtDocVenda, "DocVenda", "Doc.", ListaCampos.DB_SI, null, false ) );
		lcVenda.add( new GuardaCampo( txtSerie, "Serie", "Serie", ListaCampos.DB_SI, null, false ) );
		lcVenda.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "tp.mov", ListaCampos.DB_FK, txtDescTipoMov, false ) );
		lcVenda.add( new GuardaCampo( txtDtSaidaVenda, "DTSaidaVenda", "Dt.sa�da", ListaCampos.DB_SI, null, false ) );
		lcVenda.add( new GuardaCampo( txtDtEmitVenda, "DtEmitVenda", "Dt.emiss�o", ListaCampos.DB_SI, null, false ) );
		lcVenda.add( new GuardaCampo( txtCodCli, "CodCli", "Cod.Cli.", ListaCampos.DB_FK, txtRazCli, false ) );
		lcVenda.add( new GuardaCampo( txtVlrProdVenda, "VlrProdVenda", "Vlr.Prod.", ListaCampos.DB_SI, null, false ) );
		lcVenda.add( new GuardaCampo( txtVlrLiqVenda, "VlrLiqVenda", "Vlr.Liq.", ListaCampos.DB_SI, null, false ) );
		lcVenda.add( new GuardaCampo( txtStatusVenda, "StatusVenda", "Status", ListaCampos.DB_SI, null, false ) );
		
		
		txtCodVenda.setTabelaExterna( lcVenda, null );
		txtCodVenda.setNomeCampo( "CodVenda" );
		txtCodVenda.setFK( true );

		lcVenda.setQueryCommit( false );
		lcVenda.setReadOnly( true );

		txtCodVenda.setListaCampos( lcVenda );
		lcVenda.montaSql( false, "VENDA", "VD" );

		// Lista campos do cliente
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.Cli.", ListaCampos.DB_PK, txtRazCli, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do Cli", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCli, null );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		
		
		// FK Tipo de movimento
		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "C�d.Tp.Mov", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descri��o do tipo de documento", ListaCampos.DB_SI, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov, null );
		
	}
	
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btExec ) {
		}
		else if ( evt.getSource() == btTodosItCompra ) {
		}
		else if ( evt.getSource() == btNenhumItCompra ) {
		}
		else if ( evt.getSource() == txtCodVenda ) {
		}
		else if ( evt.getSource() == btGerar){
		}
	}

	public void beforeCarrega( CarregaEvent e ) {

	}

	public void afterCarrega( CarregaEvent e ) {

		if ( e.getListaCampos() == lcVenda ) {
			txtCodCli.setAtivo( false );
			
			
			if( buscaCodTipoMovAnterior( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "EQTIPOMOV" ), txtCodTipoMov.getVlrInteger() )){
				montaComboBox();
			}
			
		} 
		else if (e.getListaCampos() == lcCli	){
		}
	}

	public void firstFocus() {

		txtCodVenda.requestFocus();
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcCli.setConexao( cn );
		lcVenda.setConexao( cn );		
		lcTipoMov.setConexao( cn );
		
		txtCodVenda.setFocusable( true );
		setFirstFocus( txtCodVenda );
		
	}
	
	public boolean buscaCodTipoMovAnterior(Integer codemp, Integer codfilial, Integer codtipomov) {
		
		boolean result = false;
		tipos = new Vector<Vector<Object>>();
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codtp = null;
		String descTipoMov = null;		
		
		try{
			sql.append( "select tm.codtipomov, tm.desctipomov from eqtipomov tm where tm.codemp=? and tm.codfilial=? and tm.codtipomovtm=? " );
			ps = con.prepareStatement( sql.toString() );
			
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codtipomov );
			rs = ps.executeQuery();
			
			while(rs.next()){
				result = true;
				
				Vector<Object> vLinha = new Vector<Object>();
				vLinha.addElement( rs.getString("codtipomov") );
				vLinha.addElement(  rs.getString("desctipomov").trim() );
				tipos.addElement( vLinha );
			}	
			
		}catch (SQLException e) {
			Funcoes.mensagemErro( null, "Tipo de movimento anterior n�o encontrado!!! " );
		}
		
		return result;
	}

	public void focusGained( FocusEvent arg0 ) {

	}

	public void focusLost( FocusEvent evt ) {

	}

	public void valorAlterado( JComboBoxEvent evt ) {
		
	}
}
