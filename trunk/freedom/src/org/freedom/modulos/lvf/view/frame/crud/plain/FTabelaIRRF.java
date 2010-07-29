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

package org.freedom.modulos.lvf.view.frame.crud.plain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;

public class FTabelaIRRF extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad CodTabIRRF = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtTeto = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 8, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtAliquota = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 8, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtDeducao = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 8, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtReducaoDependente = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 8, Aplicativo.casasDecFin );

	public FTabelaIRRF() {

		super();

		setTitulo( "Tabela de IRRF" );
		setAtribos( 50, 50, 450, 370 );

		lcCampos.setUsaME( false );
		
		adicCampo( CodTabIRRF, 7, 20, 60, 20, "CodTabIRRF", "Seq.", ListaCampos.DB_PK, true );
		adicCampo( txtTeto, 70, 20, 87, 20, "Teto", "Teto", ListaCampos.DB_SI, true );
		adicCampo( txtAliquota, 160, 20, 87, 20, "Aliquota", "Aliquota", ListaCampos.DB_SI, true );
		adicCampo( txtDeducao, 250, 20, 87, 20, "Deducao", "Dedu��o", ListaCampos.DB_SI, true );		
		adicCampo( txtReducaoDependente, 340, 20, 87, 20, "ReducaoDependente", "Red.Dep.", ListaCampos.DB_SI, true );

		setListaCampos( true, "TABELAIRRF", "LF" );

		lcCampos.setUsaFI( false );
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );

		setImprimir( true );

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

		String sSQL = "SELECT CODTABIRRF, TETO, ALIQUOTA, DEDUCAO, REDUCAODEPENDENTE FROM LFTABELAIRRF ORDER BY CODTABIRRF";

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
}
