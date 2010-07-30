/**
 * @version 29/03/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FSecaoProd.java <BR>
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
 *         Tela para cadastro de se��es de produ��o.
 * 
 */

package org.freedom.modulos.grh.view.frame.crud.special;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;

public class FTabelaIRRF extends FDados implements ActionListener, PostListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodTabIRRF = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtTeto = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 8, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtAliquota = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 8, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtDeducao = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 8, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtReducaoDependente = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 8, Aplicativo.casasDecFin );

	private JPanelPad pnCampos = new JPanelPad(500,60 );
	
	private JTablePad tab = null;
	
	public FTabelaIRRF() {

		super();

		setTitulo( "Tabela de IRRF" );
		setAtribos( 50, 50, 450, 300 );
		criaTabela();

		lcCampos.setUsaME( false );
		
		ajustaPaineis();
		
		setPainel( pnCampos );
		
		adicCampo( txtCodTabIRRF, 7, 20, 60, 20, "CodTabIRRF", "Seq.", ListaCampos.DB_PK, true );
		adicCampo( txtTeto, 70, 20, 87, 20, "Teto", "Teto", ListaCampos.DB_SI, true );
		adicCampo( txtAliquota, 160, 20, 87, 20, "Aliquota", "Aliquota", ListaCampos.DB_SI, true );
		adicCampo( txtDeducao, 250, 20, 87, 20, "Deducao", "Dedu��o", ListaCampos.DB_SI, true );		
		adicCampo( txtReducaoDependente, 340, 20, 87, 20, "ReducaoDependente", "Red.Dep.", ListaCampos.DB_SI, true );

		setListaCampos( true, "TABELAIRRF", "RH" );

		lcCampos.setUsaFI( false );
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );
		lcCampos.addPostListener( this );
		tab.addMouseListener( this );

		setImprimir( true );
		
		carregaTabela();

	}
	
	private void ajustaPaineis() {
		pnCliente.removeAll();
		
		pnCliente.add( pnCampos, BorderLayout.SOUTH );
		pnCliente.add( new JScrollPane( tab ), BorderLayout.CENTER );
		
	}
	
	private void criaTabela() {

		// Tabela de pesagens

		tab = new JTablePad();
		tab.setRowHeight( 21 );

		tab.adicColuna( "Seq." );
		tab.adicColuna( "Teto" );
		tab.adicColuna( "Al�quota" );
		tab.adicColuna( "Dedu��o" );
		tab.adicColuna( "Redu��o dep." );

		tab.setTamColuna( 30, 0 );
		tab.setTamColuna( 100, 1 );
		tab.setTamColuna( 80, 2 );
		tab.setTamColuna( 100, 3 );
		tab.setTamColuna( 100, 4 );

	}
	
	private void carregaTabela() {
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			tab.limpa();
			
			sql.append( "SELECT CODTABIRRF, TETO, ALIQUOTA, DEDUCAO, REDUCAODEPENDENTE FROM RHTABELAIRRF ORDER BY CODTABIRRF" );
			
			ps = Aplicativo.getInstace().con.prepareStatement( sql.toString() );
			
			rs = ps.executeQuery();
			
			int lin = 0;
			int col = 0;
			
			while(rs.next()) {

				tab.adicLinha();
				
				col = 0;
				
				tab.setValor( rs.getInt( "CODTABIRRF" ), lin, col++ );
				tab.setValor( rs.getBigDecimal( "TETO" ), lin, col++ );
				tab.setValor( rs.getBigDecimal( "ALIQUOTA" ), lin, col++ );
				tab.setValor( rs.getBigDecimal( "DEDUCAO" ), lin, col++ );
				tab.setValor( rs.getBigDecimal( "REDUCAODEPENDENTE" ), lin, col++ );
				
				lin ++;
				
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp )
			imprimir( false );
		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {

		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		imp.montaCab();
		imp.setTitulo( "Tabela de IRRF" );

		String sSQL = "SELECT CODTABIRRF, TETO, ALIQUOTA, DEDUCAO, REDUCAODEPENDENTE FROM RHTABELAIRRF ORDER BY CODTABIRRF";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement( sSQL );
			rs = ps.executeQuery();
			imp.limpaPags();

			while ( rs.next() ) {

				if ( imp.pRow() == 0 ) {
					imp.impCab( 80, false );
					imp.say( imp.pRow() + 0, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 0, 2, "Seq." );
					imp.say( imp.pRow() + 0, 8, "Teto" );
					imp.say( imp.pRow() + 0, 25, "Al�quota" );
					imp.say( imp.pRow() + 0, 40, "Dedu��o" );
					imp.say( imp.pRow() + 0, 60, "Red.Dep." );
					imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, StringFunctions.replicate( "-", 79 ) );
				}

				imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
				imp.say( imp.pRow() + 0, 2, rs.getString( "CodTabIRRF" ) );
				imp.say( imp.pRow() + 0, 8, Funcoes.strDecimalToStrCurrency( Aplicativo.casasDecFin, rs.getString( "Teto" )) );
				imp.say( imp.pRow() + 0, 25, Funcoes.strDecimalToStrCurrency( Aplicativo.casasDecFin, rs.getString( "Aliquota" )) );
				imp.say( imp.pRow() + 0, 40, Funcoes.strDecimalToStrCurrency( Aplicativo.casasDecFin, rs.getString( "Deducao" )) );
				imp.say( imp.pRow() + 0, 60, Funcoes.strDecimalToStrCurrency( Aplicativo.casasDecFin, rs.getString( "ReducaoDependente" )) );

				if ( imp.pRow() >= linPag ) {
					imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, StringFunctions.replicate( "=", 79 ) );
					imp.incPags();
					imp.eject();
				}
			}

			imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
			imp.say( imp.pRow() + 0, 0, StringFunctions.replicate( "=", 79 ) );
			imp.eject();

			imp.fechaGravacao();

			con.commit();
			
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de IRRF!\n" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}
	
	public void afterPost( PostEvent pevt ) {

		super.beforePost( pevt );

		if ( pevt.getListaCampos() == lcCampos ) {
			carregaTabela();
		}
	}

	public void mouseClicked( MouseEvent mevt ) {

		if ( mevt.getSource() == tab && mevt.getClickCount() == 2 ) {
			txtCodTabIRRF .setVlrInteger( (Integer) tab.getValor( tab.getLinhaSel(), 0 ) );
			lcCampos.carregaDados();			
		}

	}


	public void mouseEntered( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

	public void mouseExited( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

	public void mousePressed( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

	public void mouseReleased( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}
}
