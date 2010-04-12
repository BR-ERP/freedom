/**
 * @version 19/06/2008 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe:
 * @(#)FAtribuicao.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Formul�rio de cadastro de caracter�sticas pessoais.
 * 
 */

package org.freedom.modulos.grh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FCaracteristica extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCarac = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private JTextFieldPad txtDescCarac = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextAreaPad txaObsCarac = new JTextAreaPad();

	public FCaracteristica() {

		super();
		setTitulo( "Cadastro de Atribui��es" );
		setAtribos( 20, 20, 370, 225 );
		adicCampo( txtCodCarac, 7, 20, 80, 20, "CodCarac", "C�d.caract.", ListaCampos.DB_PK, true );
		adicCampo( txtDescCarac, 90, 20, 250, 20, "DescCarac", "Descri��o da caracter�stica", ListaCampos.DB_SI, true );
		adicDB( txaObsCarac, 7, 60, 333, 80, "ObsCarac", "Observa��o", true );
		setListaCampos( false, "CARACTERISTICA", "RH" );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );
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
		int linPag = imp.verifLinPag() - 1;
		imp.montaCab();
		imp.setTitulo( "Relat�rio de Caracter�sticas" );
		/*DLRTipoConv dl = new DLRTipoConv();
		dl.setVisible( true );
		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}*/
		String sSQL = "SELECT CODCARAC,DESCCARAC FROM RHCARACTERISTICA ";
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
					imp.say( imp.pRow() + 0, 2, "C�d.caract." );
					imp.say( imp.pRow() + 0, 25, "Descri��o" );
					imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, StringFunctions.replicate( "-", 79 ) );
				}
				imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
				imp.say( imp.pRow() + 0, 2, rs.getString( "CodCarac" ) );
				imp.say( imp.pRow() + 0, 25, rs.getString( "DescCarac" ) );
				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
			imp.say( imp.pRow() + 0, 0, StringFunctions.replicate( "=", 79 ) );
			imp.eject();

			imp.fechaGravacao();

			con.commit();
			//dl.dispose();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta tabela de caracter�sticas!" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}
}
