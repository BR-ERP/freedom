/**
 * @version 02/01/2012 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gpe.view.frame.crud.plain <BR>
 *         Classe: (#)FExcluLivroPonto.java <BR>
 * 
 * 
 *                Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                Tela para exclus�o de batidas.
 * 
 */

package org.freedom.modulos.gpe.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JScrollPane;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.crm.business.object.ContratoVW.EColContr;
import org.freedom.modulos.gpe.business.object.Batida.EColPonto;
import org.freedom.modulos.gpe.dao.DAOBatida;
import org.freedom.modulos.grh.view.frame.crud.plain.FTurnos;

public class FExcluLivroPonto extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtMatempr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 7, 0 );
	
	private JTextFieldFK txtNomeempr = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0);
	
	private JTextFieldFK txtDtBat = new JTextFieldFK( JTextFieldFK.TP_DATE, 10, 0);

	private JTextFieldFK txtHBat = new JTextFieldFK( JTextFieldFK.TP_TIME, 5, 0);
	
	private JTextFieldFK txtIdUsuIns = new JTextFieldFK( JTextFieldFK.TP_STRING, 128, 0);
	
	private JTextFieldFK txtTipoBat = new JTextFieldFK( JTextFieldFK.TP_STRING, 2, 0);
	
	private ListaCampos lcEmpr = new ListaCampos( this );
	
	//private ListaCampos lcBatida = new ListaCampos( this ); 
	
	/*******
	 Paineis
	 ********/
	
	private JPanelPad pnDetail = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pnPonto = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinCab = new JPanelPad( 700, 100);
	
	private JPanelPad pinNav = new JPanelPad (  JPanelPad.TP_JPANEL, new GridLayout( 1, 2 ) );
	
	private JTabbedPanePad tabAbas = new JTabbedPanePad();
	
	//Geral
	private JTablePad tabPonto = new JTablePad();
	
	private JScrollPane scpPonto = new JScrollPane( tabPonto );
	
	private JPanelPad pnGridAtd = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private DAOBatida daobatida = null; 
	
	//Botoes
	
	private JButtonPad btEditar = new JButtonPad( Icone.novo( "btEditar.gif" ) );
	
	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private JButtonPad btImprimir = new JButtonPad( Icone.novo( "btPrevimp.gif" ) );
	
	private JButtonPad  btPrimeiro = new JButtonPad( Icone.novo( "btPrim.gif" ) );
	
	private JButtonPad 	btAnterior = new JButtonPad( Icone.novo( "btAnt.gif" ) );
	
	private JButtonPad  btProximo= new JButtonPad( Icone.novo( "btProx.gif" ) );
	
	private JButtonPad  btUltimo = new JButtonPad( Icone.novo( "btUlt.gif" ) );
	
	private JButtonPad  btGerar = new JButtonPad( Icone.novo( "btGerar.gif" ) );
	
	private JButtonPad  btExcluir = new JButtonPad( Icone.novo( "btExcluir.gif" ) );
	
	public FExcluLivroPonto() {

		super( false );
		setTitulo( "Exclus�o de Livro Ponto" );
		setAtribos( 50, 50, 550, 400 );
		montaListaCampos();
		montaTela();
		carregaListener();
	}
	
	private void montaListaCampos() {
		
		lcEmpr.add( new GuardaCampo( txtMatempr, "Matempr", "Matr�cula", ListaCampos.DB_PK, true ) );
		lcEmpr.add( new GuardaCampo( txtNomeempr, "Nomeempr", "Nome", ListaCampos.DB_SI, false ) );
		txtMatempr.setTabelaExterna( lcEmpr, FTurnos.class.getCanonicalName() );
		lcEmpr.setReadOnly( true );
		lcEmpr.montaSql( false, "EMPREGADO", "RH" );

	}

	private void montaTela() {
		
		getTela().add( pnCliente , BorderLayout.CENTER );
		pnCliente.add( pinCab, BorderLayout.NORTH );
		
		
		pinCab.adic( txtDataini, 7, 20, 70, 20, "Data Inicial" );
		pinCab.adic( txtDatafim, 80, 20, 70, 20, "Data Final" );
		pinCab.adic( txtMatempr, 153, 20, 70, 20, "Matr�cula" );
		pinCab.adic( btGerar, 7, 60, 30, 30 );
		txtMatempr.setFK( true );
		txtMatempr.setNomeCampo( "Matempr" );
		
		
		pinCab.adic( txtNomeempr, 226, 20, 300, 20, "Nome" );
		
		txtDataini.setVlrDate( new Date() );	
		txtDatafim.setVlrDate( new Date() );
		
		
		// ***** Grid

		pnCliente.add( pnDetail, BorderLayout.CENTER );
		pnDetail.add( tabAbas );
		tabAbas.addTab( "Batida", pnPonto );
	
		// ***** Ponto
		montaGridPonto();
		
		
		pnCliente.add( pnRodape, BorderLayout.SOUTH );
		adicNavegador();
		pnRodape.add( adicBotaoSair() );
		
		pnPonto.add( scpPonto, BorderLayout.CENTER );
	}
	
	
private void montaGridPonto(){
		
		tabPonto.adicColuna( "Sel" );
		tabPonto.adicColuna( "Data" );
		tabPonto.adicColuna( "Hor�rio" );
		tabPonto.adicColuna( "Tp.bat." );
		tabPonto.adicColuna( "Matr�cula" );
		tabPonto.adicColuna( "IDUSU" );
		
		tabPonto.setTamColuna( 30, EColPonto.SEL.ordinal() );
		tabPonto.setTamColuna( 70, EColPonto.DTBAT.ordinal() );
		tabPonto.setTamColuna( 70, EColPonto.HBAT.ordinal() );
		tabPonto.setTamColuna( 30, EColPonto.TIPOBAT.ordinal() );
		tabPonto.setTamColuna( 70, EColPonto.MATEMPR.ordinal() );
		tabPonto.setTamColuna( 260, EColPonto.IDUSUINS.ordinal() );
	
		tabPonto.setColunaEditavel( EColPonto.SEL.ordinal(), true );
	}
	
	private void carregaListener(){
		btGerar.addActionListener( this );
		btPrimeiro.addActionListener( this );
		btProximo.addActionListener( this );
		btAnterior.addActionListener( this );
		btUltimo.addActionListener( this );
		btEditar.addActionListener( this );
		btSair.addActionListener( this );
		btExcluir.addActionListener( this );
	}
	
	
	private void loadBatida(){
		try {
			Vector<Vector<Object>> datavector = daobatida.loadBatida( Aplicativo.iCodEmp , ListaCampos.getMasterFilial( "PEBATIDA" ), txtMatempr.getVlrInteger(), txtDataini.getVlrDate(), txtDatafim.getVlrDate() );
			tabPonto.limpa();
			
			for(Vector<Object> row : datavector){
				tabPonto.adicLinha( row );
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro carregando prefer�ncias !\b" + err.getMessage() );
			err.printStackTrace();
		}
	}
	
	private void excluir(){
		Integer matempr =( (Integer) tabPonto.getValor( tabPonto.getLinhaSel(), EColPonto.MATEMPR.ordinal() ) ); 
		String hbat =( (String) tabPonto.getValor( tabPonto.getLinhaSel(), EColPonto.HBAT.ordinal() ) );
		Date dtbat =( (Date) tabPonto.getValor( tabPonto.getLinhaSel(), EColPonto.DTBAT.ordinal() ) );
		try {
			if ( hbat !=null &&  hbat.length() >= 5 ) {
				daobatida.excluiBatida( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "PEBATIDA" ) , matempr, dtbat, hbat );	
			}
			
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}

	private void adicNavegador(){
		
		pnRodape.add( pinNav, BorderLayout.WEST );
		
		pinNav.setPreferredSize( new Dimension( 200, 30 ) );
		pinNav.add( btPrimeiro );
		pinNav.add( btAnterior );
		pinNav.add( btProximo );
		pinNav.add( btUltimo );
		pinNav.add( btEditar );
		pinNav.add( btImprimir );
		pinNav.add( btExcluir );

		btExcluir.setToolTipText( "Excluir" );
		btEditar.setToolTipText( "Editar" );
		btImprimir.setToolTipText( "Imprimir" );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcEmpr.setConexao( cn );
		
		daobatida = new DAOBatida( cn );
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
		
		if ( evt.getSource() == btGerar ) {
			if( txtMatempr.getVlrInteger() == 0 ){
				Funcoes.mensagemInforma( this, "Digite a matr�cula do funcion�rio !!!" );
			} else {
				loadBatida();
			}
		}
		else if ( evt.getSource() == btPrimeiro ) {
    		primeiro(tabPonto);
    	}
		else if ( evt.getSource() == btAnterior ) {
			anterior(tabPonto);
		}
		else if ( evt.getSource() == btProximo ) {
			proximo(tabPonto);
		}
		else if ( evt.getSource() == btUltimo ) {
			ultimo(tabPonto);
		}
		else if ( evt.getSource() == btExcluir ) {
			excluir();
		}
	}

}
