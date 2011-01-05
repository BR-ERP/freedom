/**
 * @version 05/01/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.lvf <BR>
 *         Classe: @(#)FModDocFisc.java <BR>
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
 *        Tela para cadastro e edi��o de modelos de documentos fiscais.
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
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.std.view.dialog.report.DLRModNota;

public class FModDocFisc extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodModDocFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtDescModDocFisc = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );

	public FModDocFisc() {

		super( false );

		setTitulo( "Cadastro de Modelo de Documentos fiscais" );
		setAtribos( 50, 50, 375, 115 );

		adicCampo( txtCodModDocFisc, 7, 20, 90, 20, "CodModDocFisc", "C�d.mod.doc.fisc.", ListaCampos.DB_PK, true );
		adicCampo( txtDescModDocFisc, 100, 20, 250, 20, "DescModDocFisc", "Descri��o de documento fiscal", ListaCampos.DB_SI, true );

		setListaCampos( true, "MODDOCFISC", "LF" );
		lcCampos.setQueryInsert( false );

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

		setImprimir( true );
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}

		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {

		ImprimeOS imp = new ImprimeOS( "", con );

		DLRModNota dl = new DLRModNota();
		dl.setVisible( true );

		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}

		try {

			PreparedStatement ps = con.prepareStatement( "SELECT CODMODDOCFISC,DESCMODDOCFISC FROM LFMODDOCFISC ORDER BY " + dl.getValor() );
			ResultSet rs = ps.executeQuery();

			int linPag = imp.verifLinPag() - 1;
			imp.montaCab();
			imp.setTitulo( "Relat�rio de modelos de documentos fiscais" );
			imp.limpaPags();

			while ( rs.next() ) {

				if ( imp.pRow() == 0 ) {
					imp.impCab( 80, false );
					imp.pulaLinha( 0, imp.normal() );
					imp.say( 2, "C�d.mod.nfs." );
					imp.say( 30, "Descri��o do modelo de nota" );
					imp.pulaLinha( 1, imp.normal() );
					imp.say( 0, StringFunctions.replicate( "-", 79 ) );
				}

				imp.pulaLinha( 1, imp.normal() );
				imp.say( 2, rs.getString( "CodModDocFisc" ) );
				imp.say( 30, rs.getString( "DescModDocFisc" ) );

				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.pulaLinha( 1, imp.normal() );
			imp.say( 0, StringFunctions.replicate( "=", 79 ) );
			imp.eject();

			imp.fechaGravacao();

			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta tabela de modelos de documentos fiscais!\n" + e.getMessage(), true, con, e );
		}

		dl.dispose();

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}
}
