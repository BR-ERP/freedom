/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FVariantes.java <BR>
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

package org.freedom.modulos.std.view.frame.crud.plain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.std.view.dialog.report.DLRVariantes;

public class FVariantes extends FDetalhe implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JPanelPad pinVariantes = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JTextFieldPad txtCodVarG = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescVarG = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private JTextFieldPad txtSeqItVarG = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescItVarG = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private JTextFieldPad txtSiglaItVarG = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	public FVariantes() {

		super();
		setTitulo( "Cadastro de Variantes da Grade" );
		
		setAtribos( 50, 50, 700, 400 );
		montaListaCampos();
		montaTela();


		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );
		setImprimir( true );
	}
	
	public void montaListaCampos(){
		
	}
	
	public void montaTela(){
		
		setListaCampos( lcCampos );
	//	setAltCab( 60 );
	
		pnCliCab.add(pinVariantes);
		setPainel( pinVariantes );
		adicCampo( txtCodVarG, 7, 20, 70, 20, "CodVarG", "C�d.var.", ListaCampos.DB_PK, true );
		adicCampo( txtDescVarG, 80, 20, 403, 20, "DescVarG", "Descri��o da variante", ListaCampos.DB_SI, true );
		setListaCampos( true, "VARGRADE", "EQ" );
		
		montaDetalhe();
		
	}
	
	public void montaDetalhe(){
		setAltDet( 60 );
		pinDet = new JPanelPad( 600, 80 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		
		adicCampo( txtSeqItVarG, 7, 20, 70, 20, "SeqItVarG", "Seq.it.var.", ListaCampos.DB_PK, true );
		adicCampo( txtDescItVarG, 80, 20, 403, 20, "DescItVarG", "Descri��o do item da variante", ListaCampos.DB_SI, true );
		adicCampo( txtSiglaItVarG, 486, 20, 100, 20, "SiglaItVarG", "Sigla", ListaCampos.DB_SI, true );
		setListaCampos( true, "ITVARGRADE", "EQ" );
		lcDet.setQueryInsert( false );
		
		montaTab();
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp )
			imprimir( true );
		else if ( evt.getSource() == btImp )
			imprimir( false );
		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		DLRVariantes dl = null;
		ImprimeOS imp = null;
		int linPag = 0;

		dl = new DLRVariantes();
		dl.setVisible( true );
		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}

		try {

			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.montaCab();
			imp.setTitulo( "Relat�rio de Variantes da Grade" );
			imp.limpaPags();

			sSQL = "SELECT CODVARG,DESCVARG " + "FROM EQVARGRADE " + "WHERE CODEMP=? AND CODFILIAL=? " + "ORDER BY " + dl.getValor();

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQVARGRADE" ) );
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if ( imp.pRow() == 0 ) {
					imp.impCab( 80, false );
					imp.say( imp.pRow(), 0, imp.normal() );
					imp.say( imp.pRow(), 2, "C�d.var." );
					imp.say( imp.pRow(), 30, "Descri��o" );
					imp.say( imp.pRow() + 1, 0, imp.normal() );
					imp.say( imp.pRow(), 0, StringFunctions.replicate( "-", 79 ) );
				}
				imp.say( imp.pRow() + 1, 0, imp.normal() );
				imp.say( imp.pRow(), 2, rs.getString( "CodVarG" ) );
				imp.say( imp.pRow(), 30, rs.getString( "DescVarG" ) );
				if ( imp.pRow() >= linPag ) {
					imp.say( imp.pRow() + 1, 0, imp.normal() );
					imp.say( imp.pRow(), 0, StringFunctions.replicate( "-", 79 ) );
					imp.incPags();
					imp.eject();
				}
			}

			imp.say( imp.pRow() + 1, 0, imp.normal() );
			imp.say( imp.pRow(), 0, StringFunctions.replicate( "=", 79 ) );
			imp.eject();

			imp.fechaGravacao();
			con.commit();
			dl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro na consulta a tabela VARGRADE!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			dl = null;
		}

		if ( bVisualizar )
			imp.preview( this );
		else
			imp.print();
	}
}
