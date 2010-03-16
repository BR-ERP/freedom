/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FModNota.java <BR>
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
 *         Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.telas.FDados;

public class FModNota extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodModNota = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescModNota = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private JRadioGroup<String, String> rbTipoModNota;

	public FModNota() {

		super( false );
		
		setTitulo( "Cadastro de Modelo de NFs" );
		setAtribos( 50, 50, 375, 165 );
		
		Vector<String> vLabs = new Vector<String>();
		vLabs.add( "Normal" );
		vLabs.add( "NF-e" );
		vLabs.add( "Outras" );
		Vector<String> vVals = new Vector<String>();
		vVals.add( "N" );
		vVals.add( "E" );
		vVals.add( "O" );
		rbTipoModNota = new JRadioGroup<String, String>( 1, 3, vLabs, vVals );
		rbTipoModNota.setVlrString( "N" );
		
		adicCampo( txtCodModNota, 7, 20, 90, 20, "CodModNota", "C�d.mod.nts.", ListaCampos.DB_PK, true );
		adicCampo( txtDescModNota, 100, 20, 250, 20, "DescModNota", "Descri��o da nota fiscal", ListaCampos.DB_SI, true );
		adicDB( rbTipoModNota, 7, 60, 343, 30, "TipoModNota", "Tipo", false );
		
		setListaCampos( true, "MODNOTA", "LF" );
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
			
			PreparedStatement ps = con.prepareStatement( "SELECT CODMODNOTA,DESCMODNOTA FROM LFMODNOTA ORDER BY " + dl.getValor() );
			ResultSet rs = ps.executeQuery();
			
			int linPag = imp.verifLinPag() - 1;
			imp.montaCab();
			imp.setTitulo( "Relat�rio de modelos de NFs" );
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
				imp.say( 2, rs.getString( "CodModNota" ) );
				imp.say( 30, rs.getString( "DescModNota" ) );
				
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
			Funcoes.mensagemErro( this, "Erro consulta tabela de modelos de NFs!\n" + e.getMessage(), true, con, e );
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
