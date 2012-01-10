/**
 * @version 10/01/2012 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gms<BR>
 *         Classe: @(#)FAtualizaForneced.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Tela com o objetivo de atualizar vinculos de fornecedores com produtos.
 * 
 */
package org.freedom.modulos.gms.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.crm.business.object.ProdFor.EColProdFor;
import org.freedom.modulos.gms.dao.DAOProdFor;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.view.frame.crud.tabbed.FFornecedor;

public class FAtualizaForneced extends FFDialogo implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JPanelPad pinForneced = new JPanelPad( 600, 100);
	
    private JTablePad tabForneced = new JTablePad();
	
	private JScrollPane scpForneced = new JScrollPane( tabForneced );
	 
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldFK.TP_STRING, 100, 0 );
	
	private JButtonPad btGerar = new JButtonPad( Icone.novo( "btExecuta.gif" ) );
	
	private ListaCampos lcProd = new ListaCampos( this );
	
	private ListaCampos lcFor = new ListaCampos( this );
	
	private DAOProdFor daoprodfor = null;
	
	public FAtualizaForneced() {
		setTitulo( "Atualiza Fornecedor/Produto" );
		setAtribos( 600, 430 );
		
		montaListaCampos();
		carregaListener();
		montaTela();
	}
	
	private void montaListaCampos(){
		
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		txtCodProd.setTabelaExterna( lcProd, FProduto.class.getCanonicalName() );
		
		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK(true);
		lcFor.setReadOnly( true );
		lcFor.montaSql( false, "FORNECED", "CP" );
		txtCodFor.setTabelaExterna( lcFor, FFornecedor.class.getCanonicalName() );
	}
	
	
	private void montaTela(){
		
		Container c = getContentPane();
		c.setLayout( new BorderLayout() );
		c.add( pinForneced, BorderLayout.NORTH );
	
		pinForneced.adic( txtDataini, 7, 20, 80, 20, "Data In�cial" );
		pinForneced.adic( txtDatafim, 90, 20, 80, 20, "Data Final" );
		pinForneced.adic( txtCodFor, 173, 20, 80, 20, "C�d.for." ); 
		pinForneced.adic( txtRazFor, 256, 20, 300, 20, "Raz�o do fornecedor" ); 
		pinForneced.adic( txtCodProd, 7, 60, 80, 20, "C�d.prod." ); 
		pinForneced.adic( txtDescProd, 90, 60, 440, 20, "Descri��o do produto" ); 
		pinForneced.adic( btGerar, 533, 55, 30, 30 ); 
		
		c.add( scpForneced, BorderLayout.CENTER );
		montaGridProdFor();
		
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
		
		adicBotaoSair();
	}
private void montaGridProdFor(){
		
		tabForneced.adicColuna( "Descri��o do produto" );
		tabForneced.adicColuna( "C�d.Prod" );
		tabForneced.adicColuna( "Raz�o do fornecedor" );
		tabForneced.adicColuna( "C�d.For." );
		
		tabForneced.setTamColuna( 200, EColProdFor.DESCPROD.ordinal() );
		tabForneced.setTamColuna( 80, EColProdFor.CODPROD.ordinal() );
		tabForneced.setTamColuna( 200, EColProdFor.RAZFOR.ordinal() );
		tabForneced.setTamColuna( 80, EColProdFor.CODFOR.ordinal() );
		
	}
	private void carregaListener(){
		btGerar.addActionListener( this );
		/*
		lcContrato.addCarregaListener( this );
		
		btAnterior.addActionListener( this );
		btPrimeiro.addActionListener( this );
		btProximo.addActionListener( this );
		btUltimo.addActionListener( this );
		btEditar.addActionListener( this );
		*/
	}

	

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcProd.setConexao( cn );
		lcFor.setConexao( cn );
		
		daoprodfor = new DAOProdFor( cn );
	}
	
	private void loadProdFor(){
		try {
			Vector<Vector<Object>> datavector = daoprodfor.loadProdFor(  Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "EQPRODUTO" ), 
					txtDataini.getVlrDate(), txtDatafim.getVlrDate() );
			tabForneced.limpa();
			
			for(Vector<Object> row : datavector){
				tabForneced.adicLinha( row );
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro carregando grid de fornecedor/produto !\b" + err.getMessage() );
			err.printStackTrace();
		}
	}
	
	
	public void actionPerformed(ActionEvent evt){
		if ( evt.getSource() == btGerar ) {
			if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			}
			loadProdFor();
		}
	}

	

}
