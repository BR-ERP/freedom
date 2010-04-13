/**
 * @version 29/05/2008 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FAlteraCliRec.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Coment�rios sobre a classe...
 */
package org.freedom.modulos.std.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;

public class FAlteraCliRec extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCli = new JPanelPad( 350, 100 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtSerie = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtVlrLiqVenda = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtStatusVenda = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldFK txtDoc = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private ListaCampos lcVenda = new ListaCampos( this );
	
	private ListaCampos lcCli = new ListaCampos( this );
	
	private JButtonPad btTrocaDoc = new JButtonPad(Icone.novo("btTrocaNumero.gif"));

	private Vector<String> vLabs1 = new Vector<String>();
	
	private Vector<String> vVals1 = new Vector<String>();
	
	private JRadioGroup<?, ?> rgTipo = null;

	
	public FAlteraCliRec() {

		super( false );
		setTitulo( "Altera��o de cliente/receber" );
		setAtribos( 50, 50, 370, 230 );
		
		vLabs1.addElement( "Venda" );
		vLabs1.addElement( "ECF" );
		vVals1.addElement( "V" );
		vVals1.addElement( "E" );

		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs1, vVals1 );
		rgTipo.setVlrString( "E" );

	
		montaListaCampos();
		montaTela();
		
		btTrocaDoc.addActionListener( this );
		
	}
		private void montaTela() {

		Container c = getContentPane();
		c.setLayout( new BorderLayout() );

		pnRod.setPreferredSize( new Dimension( 350, 30 ) );
		pnRod.add( adicBotaoSair(), BorderLayout.EAST );

		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pinCli, BorderLayout.CENTER );

	
		pinCli.adic( rgTipo, 7, 7, 330, 30 );
		pinCli.adic( new JLabelPad( "Pedido:" ), 7, 40, 80, 20 );
		pinCli.adic( txtCodVenda, 7, 60, 80, 20 );
		pinCli.adic( new JLabelPad( "S�rie" ), 90, 40, 67, 20 );
		pinCli.adic( txtSerie, 90, 60, 67, 20 );
		pinCli.adic( new JLabelPad( "Valor" ), 160, 40, 100, 20 );
		pinCli.adic( txtVlrLiqVenda, 160, 60, 100, 20 );
		pinCli.adic( new JLabelPad( "Doc." ), 263, 40, 73, 20 );
		pinCli.adic( txtDoc, 263, 60, 75, 20 );
		pinCli.adic( new JLabelPad( "C�d.Cli" ), 7, 80, 80, 20 );
		pinCli.adic( txtCodCli, 7, 100, 80, 20 );
		pinCli.adic( new JLabelPad( "Raz�o social do cliente" ), 90, 80, 245, 20 );
		pinCli.adic( txtRazCli, 90, 100, 250, 20 );
		pinCli.adic( btTrocaDoc, 310, 130, 30, 30 );

	}
	
	private void montaListaCampos() {

		/*************
		 *  Pedido   *
		 *************/
		
		lcVenda.add( new GuardaCampo(rgTipo, "TipoVenda", "Tipo venda", ListaCampos.DB_PK, true) );
		lcVenda.add( new GuardaCampo( txtCodVenda, "CodVenda", "C�d.venda", ListaCampos.DB_PK, true ) );
		lcVenda.add(new GuardaCampo( txtDoc, "DocVenda", "Documento", ListaCampos.DB_SI, false));
		lcVenda.add( new GuardaCampo( txtSerie, "Serie", "S�rie", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtVlrLiqVenda, "VlrLiqVenda", "V. liq.", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtStatusVenda, "StatusVenda", "Status", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.Cli", ListaCampos.DB_FK, false ) );				
		lcVenda.montaSql( false, "VENDA", "VD" );
		lcVenda.setReadOnly( true );
		txtCodVenda.setTabelaExterna( lcVenda );
		txtCodVenda.setFK( true );
		txtCodVenda.setNomeCampo( "CodVenda" );
		
		/*************
		 * Cliente   * 
		*************/
		
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );
	}
	
	private void alteraEManut( String sManut ) throws SQLException {
		
		PreparedStatement ps = null;
		StringBuilder sSQL = new StringBuilder();

		sSQL.append( "UPDATE VDVENDA SET EMMANUT=? WHERE CODEMP=? AND CODFILIAL=? AND TIPOVENDA=? AND CODVENDA=?" );

		ps = con.prepareStatement( sSQL.toString() );
		ps.setString( 1, sManut );
		ps.setInt( 2, Aplicativo.iCodEmp );
		ps.setInt( 3, ListaCampos.getMasterFilial( "VDVENDA" ) );
		ps.setString( 4, rgTipo.getVlrString() );
		ps.setInt( 5, txtCodVenda.getVlrInteger() );
		ps.executeUpdate();

		con.commit();
	
	}
	
	private void alteraVenda( int codCli, String tipoVenda,  int codVenda ) throws SQLException {
	
		PreparedStatement ps = null;
		StringBuilder sSQL = new StringBuilder();
		
		sSQL.append( "UPDATE VDVENDA SET CODEMPCL=?, CODFILIALCL=?, CODCLI=? WHERE CODEMP=? AND CODFILIAL=? AND TIPOVENDA=? AND CODVENDA=?" );
		
		ps = con.prepareStatement( sSQL.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
		ps.setInt( 3, codCli );
		ps.setInt( 4, Aplicativo.iCodEmp );
		ps.setInt( 5, ListaCampos.getMasterFilial( "VDVENDA" ) );
		ps.setString( 6, tipoVenda );
		ps.setInt( 7, codVenda );
		ps.executeUpdate();

		con.commit();
	}
	
	private void alteraReceber( int codCli,  String tipoVenda,  int codVenda ) throws SQLException {
		
		PreparedStatement ps = null;
		StringBuilder sSQL = new StringBuilder();
		
		sSQL.append( "UPDATE FNRECEBER SET CODEMPCL=?, CODFILIALCL=?, CODCLI=? WHERE CODEMPVA=? AND CODFILIALVA=? AND TIPOVENDA=? AND CODVENDA=?" );
		ps = con.prepareStatement( sSQL.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
		ps.setInt( 3, codCli );
		ps.setInt( 4, Aplicativo.iCodEmp );
		ps.setInt( 5, ListaCampos.getMasterFilial( "VDVENDA" ) );
		ps.setString( 6, tipoVenda );
		ps.setInt( 7, codVenda );
		ps.executeUpdate();

		con.commit();
	
	}
	private void alteraCli(){

		if ( txtCodVenda.getVlrInteger().intValue() == 0  ) {

			Funcoes.mensagemInforma( this, "Informe um pedido!" );
			txtCodVenda.requestFocus();
			return;
		}
		
		if( txtCodCli.getVlrInteger().intValue() == 0 ){
			
			Funcoes.mensagemInforma( this, "Informe o cliente!" );
			txtCodCli.requestFocus();
			return;
		}
		
		try {
			
			alteraEManut( "S" );
			alteraVenda( txtCodCli.getVlrInteger().intValue(), rgTipo.getVlrString(), txtCodVenda.getVlrInteger().intValue() );
			alteraReceber(  txtCodCli.getVlrInteger().intValue(), rgTipo.getVlrString(), txtCodVenda.getVlrInteger().intValue() );
			alteraEManut( "N" );
			
			Funcoes.mensagemInforma( this, "Cliente alterado com sucesso!" );
			
		} catch ( SQLException e ) {
		
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao alterar cliente "  + e.getMessage() );
			
			try {
				con.rollback();
			
			} catch ( SQLException err ) {
			
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro na altera��o! "  + e.getMessage() );
			}
		}
	}
	
	public void setConexao(DbConnection cn) {
		
		super.setConexao( cn );
		lcVenda.setConexao( cn );
		lcCli.setConexao( cn );
	}

	public void actionPerformed( ActionEvent e ) {
	
		if( e.getSource() == btTrocaDoc ){
			
			alteraCli();
		}
	}
}
