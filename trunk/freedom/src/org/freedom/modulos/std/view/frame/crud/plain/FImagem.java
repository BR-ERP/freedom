/**
 * @version 27/07/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm <BR>
 *         Classe: @(#)FImagem.java <BR>
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
 *         Tela de inser��o de imagem.
 * 
 */

package org.freedom.modulos.std.view.frame.crud.plain;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.freedom.acao.CarregaEvent;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.PainelImagem;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.std.view.frame.crud.plain.FCategoriaImg;
import org.freedom.modulos.std.view.dialog.report.DLRImagem;

public class FImagem extends FDados implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodImg= new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescImg = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCodCatImg= new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldFK txtDescCatImg = new JTextFieldFK( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCodEmpCi = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtCodFilialCi = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private PainelImagem imFotoImg = new PainelImagem( 65000 );
	
	private ListaCampos lcCatImg = new ListaCampos( this );
	
	private JPanelPad pinCor = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	
	public FImagem() {

		this( false, null );
		
	}
		
	public FImagem( boolean novo, DbConnection pcon ) {

		super();

		nav.setNavigation( true );

		setTitulo( "Inser��o de Imagens" );
		setAtribos( 50, 50, 530, 480 );
		
		montaListaCampos();
		
		adicCampo( txtCodImg, 7, 20, 80, 20, "CODIMG", "Cod.Imagem.", ListaCampos.DB_PK, true );		
		adicCampo( txtDescImg, 90, 20, 415, 20, "DESCIMG", "Descri��o da Imagem", ListaCampos.DB_SI, true );
		
		adicCampo( txtCodCatImg, 7, 60, 80, 20, "CODCATIMG", "Cod.CatImg.", ListaCampos.DB_SI, true );		
		adicDescFK(  txtDescCatImg, 90, 60, 415, 20, "DESCCATIMG", "Descri��o da Categoria da Imagem" );
		
		adicCampoInvisivel( txtCodEmpCi, "CODEMPCI", "", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodEmpCi, "CODFILIALCI", "", ListaCampos.DB_SI, false );
		adicDB( imFotoImg, 7, 100, 500, 200, "BinImg", "Foto:(Max 64Kb)", true );
	
	
		
		setListaCampos( true, "IMAGEM", "SG" );
		

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );
			

		setImprimir( true );
		
		if ( novo ) {
			setConexao( pcon );
			lcCampos.insert( true );
		}

	}

	
	
	public void setCodCatImg( Integer codcatimg ) {

		txtCodCatImg.setVlrInteger( codcatimg );
		lcCatImg.carregaDados();
	}

	private void montaListaCampos() {	
		
		lcCatImg.setUsaME( false );
		lcCatImg.add( new GuardaCampo( txtCodCatImg, "CODCATIMG", "Cod.Imagem.", ListaCampos.DB_PK, true ) );
		lcCatImg.add( new GuardaCampo( txtDescCatImg, "DESCCATIMG", "Descri��o da Imagem", ListaCampos.DB_SI, false ) );
		lcCatImg.montaSql( false, "CATIMG", "SG" );
		lcCatImg.setQueryCommit( false );
		lcCatImg.setReadOnly( true );
		txtCodCatImg.setTabelaExterna( lcCatImg, FCategoriaImg.class.getCanonicalName() );
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
		DLRImagem sl = null;
		ImprimeOS imp = null;
		int linPag = 0;

		sl = new DLRImagem();
		sl.setVisible( true );
		if ( sl.OK == false ) {
			sl.dispose();
			return;
		}

		try {

			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.montaCab();
			imp.setTitulo( "Relat�rio de Imagens" );
			imp.limpaPags();

			sSQL = "SELECT CODIMG,DESCIMG FROM SGIMAGEM WHERE CODEMPCI=? AND CODFILIALCI=? ORDER BY " + sl.getValor();

			ps = con.prepareStatement( sSQL );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGIMAGEM" ) );
			
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				if ( imp.pRow() == 0 ) {
					imp.impCab( 80, false );
					imp.say( imp.pRow(), 0, imp.normal() );
					imp.say( imp.pRow(), 2, "C�d.Imagem.." );
					imp.say( imp.pRow(), 30, "Descri��o da Imagem" );
					imp.say( imp.pRow() + 1, 0, imp.normal() );
					imp.say( imp.pRow(), 0, StringFunctions.replicate( "-", 79 ) );
				}
				imp.say( imp.pRow() + 1, 0, imp.normal() );
				imp.say( imp.pRow(), 2, rs.getString( "CodImg" ) );
				imp.say( imp.pRow(), 30, rs.getString( "DescImg" ) );
				if ( imp.pRow() >= linPag ) {
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
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
			sl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de Imagens!" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sl = null;
		}

		if ( bVisualizar )
			imp.preview( this );
		else
			imp.print();
	}

	public void afterCarrega( CarregaEvent pevt ) {
		
}
	public void stateChanged( ChangeEvent e ) {
	
}
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );


		lcCatImg.setConexao( cn );


	}
}