/*
 * Projeto: Freedom
 * Pacote: org.freedom.modules.std
 * Classe: @(#)DLBuscaVenda.java
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;


/**
 * Busca nota de remessa para importar itens para remessa consignada.
 * 
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues
 * @version 27/08/2009
 */
public class DLBuscaRemessa extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelMaster = new JPanelPad( 700, 60 );

	private JPanelPad panelGrid = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JTextFieldPad txtDataIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDataFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );	
	
	private JButtonPad btBuscar = new JButtonPad( Icone.novo( "btExecuta.gif" ) );
	
	private Tabela tabItens = new Tabela();
	
	private Integer codigoVenda;
	
	private Integer codigoItem;
	
	private Integer codigoProduto;

		
	private enum ITENS {
		CODVENDA, ITEM, CODPROD, DESCPROD, QUANTIDADE, PRECO, CODCLI, RAZCLI, CODPLANOPAG, DESCPLANOPAG;
	}
	
	
	public DLBuscaRemessa( Integer produto ) {

		super();
		setTitulo( "Selecione o item de nota de remessa" );
		setAtribos( 700, 320 );
		setResizable( true );
				
		montaTela();		

		btBuscar.addActionListener( this );
		
		Calendar calendar = Calendar.getInstance();
		txtDataFim.setVlrDate( calendar.getTime() );
		calendar.set( Calendar.DAY_OF_YEAR, 1 );
		txtDataIni.setVlrDate( calendar.getTime() );
		
		codigoProduto = produto;
	}

	private void montaTela() {
		
		setPanel( panelGeral );
		panelGeral.add( panelMaster, BorderLayout.NORTH );
		
		JPanelPad pnPeriodo = new JPanelPad();
		pnPeriodo.setBorder( BorderFactory.createTitledBorder( "Per�odo" ) );
		pnPeriodo.adic( new JLabelPad( "De:" ), 4, 0, 20, 20 );
		pnPeriodo.adic( txtDataIni, 25, 0, 75, 20 );
		pnPeriodo.adic( new JLabelPad( "At�:" ), 105, 0, 25, 20 );
		pnPeriodo.adic( txtDataFim, 130, 0, 75, 20 );

		panelMaster.adic( pnPeriodo, 4, 4, 220, 50 );
		panelMaster.adic( btBuscar, 224, 12, 30, 40 );
		
		// ***** Grid
		
		panelGeral.add( panelGrid, BorderLayout.CENTER );
		panelGrid.setBorder( BorderFactory.createEtchedBorder() );
				
		tabItens.adicColuna( "Venda" );
		tabItens.adicColuna( "Item" );
		tabItens.adicColuna( "C�digo" );
		tabItens.adicColuna( "Descri��o do produto" );
		tabItens.adicColuna( "Quantidade" );
		tabItens.adicColuna( "Pre�o" );
		tabItens.adicColuna( "C�d.cli." );
		tabItens.adicColuna( "Raz�o social do cliente" );
		tabItens.adicColuna( "C�d.pl.pag." );
		tabItens.adicColuna( "Descri��o do plano de pagamento" );
		
		tabItens.setTamColuna( 80, ITENS.CODVENDA.ordinal() );
		tabItens.setTamColuna( 60, ITENS.ITEM.ordinal() );
		tabItens.setTamColuna( 80, ITENS.CODPROD.ordinal() );
		tabItens.setTamColuna( 230, ITENS.DESCPROD.ordinal() );
		tabItens.setTamColuna( 80, ITENS.QUANTIDADE.ordinal() );
		tabItens.setTamColuna( 80, ITENS.PRECO.ordinal() );
		tabItens.setTamColuna( 80, ITENS.CODCLI.ordinal() );
		tabItens.setTamColuna( 230, ITENS.RAZCLI.ordinal() );
		tabItens.setTamColuna( 80, ITENS.CODPLANOPAG.ordinal() );
		tabItens.setTamColuna( 230, ITENS.DESCPLANOPAG.ordinal() );
				
		panelGrid.add( new JScrollPane( tabItens ), BorderLayout.CENTER );
	}
	
	private void carregaVendas() {
		
		try {
			
			StringBuilder selectVendas = new StringBuilder();
			selectVendas.append( "SELECT I.CODVENDA, I.CODITVENDA, I.CODPROD, P.DESCPROD, I.QTDITVENDA, I.PRECOITVENDA, " );
			selectVendas.append( "V.CODCLI, C.RAZCLI, V.CODPLANOPAG, PG.DESCPLANOPAG " );
			selectVendas.append( "FROM VDITVENDA I, VDVENDA V, VDCLIENTE C, FNPLANOPAG PG, EQPRODUTO P " );
			selectVendas.append( "WHERE I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL AND I.CODVENDA=V.CODVENDA AND I.TIPOVENDA=V.TIPOVENDA AND " );
			selectVendas.append( "V.CODEMP=? AND V.CODFILIAL=? AND V.DTEMITVENDA BETWEEN ? AND ? AND " );
			selectVendas.append( "P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD AND P.CODPROD=? AND " );
			selectVendas.append( "C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI AND " );
			selectVendas.append( "PG.CODEMP=V.CODEMPPG AND PG.CODFILIAL=V.CODFILIALPG AND PG.CODPLANOPAG=V.CODPLANOPAG " );
			//selectVendas.append( "PG.CODEMP=V.CODEMPPG AND PG.CODFILIAL=V.CODFILIALPG AND PG.CODPLANOPAG=V.CODPLANOPAG AND " );
			selectVendas.append( "ORDER BY I.CODVENDA, I.CODITVENDA" );
			
			PreparedStatement ps = con.prepareStatement( selectVendas.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDITVENDA" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataIni.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDataFim.getVlrDate() ) );
			ps.setInt( 5, codigoProduto );
			
			ResultSet rs = ps.executeQuery();
			
			tabItens.limpa();
			
			int row = 0;
			while ( rs.next() ) {
				
				tabItens.adicLinha();
				tabItens.setValor( rs.getInt( "CODVENDA" ), row, ITENS.CODVENDA.ordinal() );
				tabItens.setValor( rs.getInt( "CODITVENDA" ), row, ITENS.ITEM.ordinal() );
				tabItens.setValor( rs.getInt( "CODPROD" ), row, ITENS.CODPROD.ordinal() );
				tabItens.setValor( rs.getString( "DESCPROD" ), row, ITENS.DESCPROD.ordinal() );
				tabItens.setValor( Funcoes.bdToStr( rs.getBigDecimal( "QTDITVENDA" ) ), row, ITENS.QUANTIDADE.ordinal() );
				tabItens.setValor( Funcoes.bdToStr( rs.getBigDecimal( "PRECOITVENDA" ) ), row, ITENS.PRECO.ordinal() );
				tabItens.setValor( rs.getInt( "CODCLI" ), row, ITENS.CODCLI.ordinal() );
				tabItens.setValor( rs.getString( "RAZCLI" ), row, ITENS.RAZCLI.ordinal() );
				tabItens.setValor( rs.getInt( "CODPLANOPAG" ), row, ITENS.CODPLANOPAG.ordinal() );
				tabItens.setValor( rs.getString( "DESCPLANOPAG" ), row, ITENS.DESCPLANOPAG.ordinal() );
				
				row++;
			}
			
			con.commit();
			
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar nota de remessa.\n" + e.getMessage(), true, con, e );
		}
	}

	public HashMap<String, Integer> getVenda() {

		HashMap<String, Integer> venda = new HashMap<String, Integer>();
		
		try {
			
			venda.put( "codvenda", codigoVenda );
			venda.put( "coditvenda", codigoItem );
		} catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao selecionar �tem de compra!" );
		}

		return venda;
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) { 

		if ( e.getSource() == btBuscar ) {
			carregaVendas();
		}
		else {
			super.actionPerformed( e );
		}
	}

	public void keyPressed( KeyEvent e ) {

		if ( e.getSource() == tabItens && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			
			if ( tabItens.getNumLinhas() > 0 && btOK.isEnabled() ) {
				btOK.doClick();
			}
			else if ( !btOK.isEnabled() ) {
				if ( tabItens.getLinhaSel() == tabItens.getNumLinhas() - 1 ) {
					tabItens.setLinhaSel( tabItens.getNumLinhas() - 2 );
				}
				else {
					tabItens.setLinhaSel( tabItens.getLinhaSel() - 1 );
				}
			}
		}
		else if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
			btCancel.doClick();
		}
	}

	public void ok() {

		if ( tabItens.getLinhaSel() > -1 ) {
			codigoVenda = (Integer) tabItens.getValueAt( tabItens.getLinhaSel(), ITENS.CODVENDA.ordinal() );
			codigoItem = (Integer) tabItens.getValueAt( tabItens.getLinhaSel(), ITENS.ITEM.ordinal() );
		}
		
		super.ok();
	}
}
