/**
 * @version 29/07/2008 <BR>
 * @author Setpoint Inform�tica Ltda.
 * @author Reginaldo Garcia Heua 
 * <BR> * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLMultiComiss.java <BR>
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
 */
package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLMultiComiss extends FFDialogo implements MouseListener, PostListener, ActionListener, EditListener {

	private static final long serialVersionUID = 1L;
	
	private JPanelPad pinCab = new JPanelPad( 400, 50 );
	
	private JPanelPad pnComiss = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private Tabela tabComiss = new Tabela();

	private JScrollPane spnComiss = new JScrollPane( tabComiss );
	
	private ListaCampos lcVendedor = new ListaCampos( this, "VD" );
	
	private ListaCampos lcVendaComis = new ListaCampos( null,"" );
	
	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldPad txtSeqVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldFK txtTipoVend = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodClComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtPercComisVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 7, Aplicativo.casasDecFin );
	
	private JPanelPad pnBot = new JPanelPad( new FlowLayout( FlowLayout.LEFT, 5, 5 ) );
	
	private JPanelPad pnBotSair = new JPanelPad( new FlowLayout( FlowLayout.LEFT, 5, 5 ) );
	
	private Navegador nvRodape = new Navegador( false );
	
	private Integer codvenda = null;
	 
	public JButton btSair = new JButton( "Sair", Icone.novo("btSair.gif"));
	
	private ImageIcon imgObrigatorio = Icone.novo( "clObrigatorio.gif" );
	
	private ImageIcon imgObrigatorioCompleto = Icone.novo( "clObrigatorioCompleto.gif" );
	
	private ImageIcon imgNObrigatorio = Icone.novo( "clNaoObrigatorio.gif" );
	
	private ImageIcon imgStatus = null;
	
	String tipoComis = null;
	
	private enum eComiss {
	
		OBRIGATORIO, SEQ, CODTPVEND, DESCTPCOMIS, CODVEND,  DESCVEND, PERCCOMISS, CODVENDA, TIPOVENDA ;
	};

	public DLMultiComiss( Connection con, int codvenda ){
	
		setAtribos( 620, 350 );
		setTitulo( "Multi-Comissionados" );
		
		this.codvenda = codvenda;
		
		montaListaCampos();
		setConexao( con );		
		montaTela();
		montaTab();		
		
		tabComiss.addMouseListener( this );
		btSair.addActionListener( this );
		
		txtCodVend.requestFocus();
		tabComiss.setLinhaSel( 0 );
		setLinhaTab();
	}
	
	private void montaTela(){
		
		nvRodape.setAtivo( 0, false );
		nvRodape.setAtivo( 1, false ); 	
		nvRodape.setAtivo( 2, false );
		nvRodape.setAtivo( 3, false );
		nvRodape.setAtivo( 4, false );
		
		pinCab.setPreferredSize( new Dimension( 400, 50 ) );
		spnComiss.setPreferredSize( new Dimension( 400, 180 ) );
		pnComiss.add( spnComiss, BorderLayout.NORTH );
		pnComiss.add( pinCab, BorderLayout.CENTER );		
		c.add( pnComiss, BorderLayout.CENTER );
		
		pnRodape.removeAll();
		pnRodape.setPreferredSize( new Dimension(300,40));
		pnRodape.add( pnBot, BorderLayout.WEST );
		
		tabComiss.adicColuna( "" );
		tabComiss.adicColuna( "seq." );
		tabComiss.adicColuna( "Cod.tp.vend" );
		tabComiss.adicColuna( "Tipo de comissionado" );
		tabComiss.adicColuna( "Cod.Vend" );
		tabComiss.adicColuna( "Vendedor" );
		tabComiss.adicColuna( "% Comis." );
		tabComiss.adicColuna( "C�d.Venda" );
		tabComiss.adicColuna( "Tipo Venda" );
		
		tabComiss.setTamColuna( 20, eComiss.OBRIGATORIO.ordinal() );
		tabComiss.setTamColuna( 220, eComiss.DESCTPCOMIS.ordinal() );
		tabComiss.setTamColuna( 150, eComiss.DESCVEND.ordinal() );
		tabComiss.setTamColuna( 70, eComiss.PERCCOMISS.ordinal() );
		tabComiss.setTamColuna( 60, eComiss.CODVEND.ordinal() );
		tabComiss.setTamColuna( 70, eComiss.CODTPVEND.ordinal() );	
		tabComiss.setColunaInvisivel( eComiss.SEQ.ordinal() );
		tabComiss.setColunaInvisivel( eComiss.CODVENDA.ordinal() );
		tabComiss.setColunaInvisivel( eComiss.TIPOVENDA.ordinal() );
		
		setPainel( pinCab );
		
		adic( new JLabelPad("C�d.Vend"), 7, 5, 70, 20 );
		adic( txtCodVend, 7, 25, 70, 20 );
		adic( new JLabelPad("Nome do vendedor"), 80, 5, 250, 20 );
		adic( txtDescVend, 80, 25, 250, 20 );
		adic( new JLabelPad("% comis."), 333, 5, 70, 20 );
		adic( txtPercComisVenda, 333, 25, 70, 20 );
		
		pnBot.tiraBorda();
		
		pnBotSair.add( btSair );
		pnRodape.add( pnBotSair, BorderLayout.EAST );
		
		pnBot.add( nvRodape );
		nvRodape.setListaCampos( lcVendaComis );
		
		
	}
	
	private void montaListaCampos(){
		
		
		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.Vend.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.setWhereAdic( "ATIVOCOMIS='S' ");
		lcVendedor.setDinWhereAdic( "CODTIPOVEND=#N", txtTipoVend );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor );
		txtCodVend.setListaCampos( lcVendedor );
		txtCodVend.setNomeCampo( "CodVend" );
		txtCodVend.setFK( true );
		
			
		lcVendaComis.add( new GuardaCampo( txtCodVenda, "CodVenda", "C�d.Venda", ListaCampos.DB_PK, false ) );
		lcVendaComis.add( new GuardaCampo( txtTipoVenda, "TipoVenda", "Tipo", ListaCampos.DB_PK, false ) );
		lcVendaComis.add( new GuardaCampo( txtSeqVenda, "SeqVc", "seq.", ListaCampos.DB_PK, false ) );
		lcVendaComis.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.Vend.", ListaCampos.DB_FK, false ) );
		lcVendaComis.add( new GuardaCampo( txtPercComisVenda, "Percvc", "%.Comiss.", ListaCampos.DB_SI, true ) );
		lcVendaComis.montaSql( false, "VENDACOMIS", "VD" );
		lcVendaComis.setQueryCommit( false );
		lcVendaComis.setReadOnly( false );
		lcVendaComis.setNavegador( nvRodape );
		
		lcVendaComis.addPostListener( this );
		lcVendaComis.addEditListener( this );
		
	}
	
	private void setVlrCampos(){

		try {
			
			if( !"".equals( tabComiss.getValor( tabComiss.getLinhaSel(), eComiss.CODVEND.ordinal() ).toString())){			
				txtCodVend.setVlrInteger( new Integer( tabComiss.getValor( tabComiss.getLinhaSel(), eComiss.CODVEND.ordinal() ).toString()));
			}	
		
			txtCodVenda.setVlrInteger( new Integer( tabComiss.getValor( tabComiss.getLinhaSel(), eComiss.CODVENDA.ordinal()).toString()));
			txtTipoVenda.setVlrString( tabComiss.getValor( tabComiss.getLinhaSel(), eComiss.TIPOVENDA.ordinal() ).toString());
			txtSeqVenda.setVlrInteger( new Integer( tabComiss.getValor( tabComiss.getLinhaSel(), eComiss.SEQ.ordinal() ).toString()) );
			txtTipoVend.setVlrInteger( new Integer( tabComiss.getValor( tabComiss.getLinhaSel(), eComiss.CODTPVEND.ordinal() ).toString()) );
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setLinhaTab(){
				
		setVlrCampos();
		
		lcVendaComis.carregaDados();
		nvRodape.setAtivo( 2, true );
		lcVendaComis.edit();
		
		if( imgObrigatorio == tabComiss.getValor( tabComiss.getLinhaSel(), eComiss.OBRIGATORIO.ordinal() )){
			
			txtCodVend.setRequerido( true );
			txtPercComisVenda.setRequerido( true );
		}
		else
		{
			txtCodVend.setRequerido( false );
			txtPercComisVenda.setRequerido( false );
		}
	}
	
	private void montaTab(){
		
		StringBuffer sql = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement ps = null;
		txtCodVend.requestFocus();
		
		try {

			sql.append( "SELECT VC.SEQVC, VC.CODVEND, TV.DESCTIPOVEND, VC.CODVEND, VE.NOMEVEND, VC.PERCVC, VC.CODVENDA, VC.TIPOVENDA, RC.OBRIGITRC, TV.CODTIPOVEND " );
			sql.append( "FROM VDITREGRACOMIS RC, VDTIPOVEND TV, VDVENDACOMIS VC " );
			sql.append( "LEFT OUTER JOIN VDVENDEDOR VE ON " );
			sql.append( "VE.CODEMP=VC.CODEMPVD AND VE.CODFILIAL=VC.CODFILIALVD AND VC.CODVEND=VE.CODVEND " );
			sql.append( "WHERE " );
			sql.append( "RC.CODEMP=VC.CODEMPRC AND RC.CODFILIAL=VC.CODFILIALRC AND RC.CODREGRCOMIS=VC.CODREGRCOMIS " );
			sql.append( "AND RC.SEQITRC=VC.SEQITRC " );
			sql.append( "AND TV.CODEMP=RC.CODEMPTV AND TV.CODFILIAL=RC.CODFILIALTV AND TV.CODTIPOVEND=RC.CODTIPOVEND " );
			sql.append( "AND VC.CODEMP=? AND VC.CODFILIAL=? AND VC.TIPOVENDA='V' AND VC.CODVENDA=?" );
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDREGRACOMIS" ) );
			ps.setInt( 3, codvenda );
			
			rs = ps.executeQuery();
			
			int i = 0;

			tabComiss.limpa();
			
			while( rs.next() ){
				
				tabComiss.adicLinha();
				
				if( "S".equals( rs.getString( "OBRIGITRC" ) )){					
					imgStatus = imgObrigatorio;					
				}
				else if( "N".equals( rs.getString( "OBRIGITRC" ) )){				
					imgStatus = imgNObrigatorio;
				}
				if(rs.getString( "CODVEND" ) != null) {
					imgStatus = imgObrigatorioCompleto;
				}
				
				tabComiss.setValor( imgStatus, i, eComiss.OBRIGATORIO.ordinal() ); 
				tabComiss.setValor( rs.getString( "SEQVC" ) != null ?  rs.getString( "SEQVC" ) : "", i, eComiss.SEQ.ordinal() );
				tabComiss.setValor( rs.getString( "CODTIPOVEND" ) != null ? rs.getString( "CODTIPOVEND" ) : "" , i, eComiss.CODTPVEND.ordinal() );
				tabComiss.setValor( rs.getString( "DESCTIPOVEND" ) != null ? rs.getString( "DESCTIPOVEND" ) : "" , i, eComiss.DESCTPCOMIS.ordinal() );
				tabComiss.setValor( rs.getString( "CODVEND" ) != null ? rs.getString( "CODVEND" ) : ""  , i, eComiss.CODVEND.ordinal() );
				tabComiss.setValor( rs.getString( "NOMEVEND" ) != null ? rs.getString( "NOMEVEND" ) : "", i, eComiss.DESCVEND.ordinal() );
				tabComiss.setValor( rs.getString( "PERCVC" ) != null ? rs.getString( "PERCVC" ) : "", i, eComiss.PERCCOMISS.ordinal() );
				tabComiss.setValor( rs.getString( "CODVENDA" ) != null ? rs.getString( "CODVENDA" ) : ""  , i, eComiss.CODVENDA.ordinal() );
				tabComiss.setValor( rs.getString( "TIPOVENDA" ) != null ? rs.getString( "TIPOVENDA" ) : ""  , i, eComiss.TIPOVENDA.ordinal() );
						
				i++;
			}		
					
			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} 
		
		
		catch ( SQLException e ) {
			
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar tabela!", true, con, e );
			
		}
	}
	public void setConexao( Connection con ){		
		
		super.setConexao( con );
		lcVendedor.setConexao( con );
		lcVendaComis.setConexao( con );
		
	}

	public void mouseClicked( MouseEvent mevt ) {

		if ( mevt.getClickCount() == 2 ) {
			if ( mevt.getSource() == tabComiss && tabComiss.getLinhaSel() >= 0 ) {	

				setLinhaTab();
			}
		}
	}

	public void mouseEntered( MouseEvent e ) {}

	public void mouseExited( MouseEvent e ) {}

	public void mousePressed( MouseEvent e ) {}

	public void mouseReleased( MouseEvent e ) {}

	public void afterPost( PostEvent pevt ) {
		if ( pevt.getListaCampos() == lcVendaComis ) {
			montaTab();
			txtCodVend.requestFocus();
		}
	}
	public void beforePost( PostEvent pevt ) {}
	
	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
		
		if( evt.getSource() == btSair ){
			
			dispose();
		}
	}

	public void afterEdit( EditEvent eevt ) {
		nvRodape.setAtivo( 3, true );
		nvRodape.setAtivo( 4, true );
			
	}

	public void beforeEdit( EditEvent eevt ) {}

	public void edit( EditEvent eevt ) {}
}
