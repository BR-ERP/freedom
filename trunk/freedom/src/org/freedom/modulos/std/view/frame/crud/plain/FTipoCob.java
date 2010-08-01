/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FTipoCob.java <BR>
 * 
 *                   Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                   modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                   na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                   Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                   sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                   Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                   Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                   Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.crud.plain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.fnc.view.frame.crud.plain.FTalaoCheq;
import org.freedom.modulos.fnc.view.frame.crud.tabbed.FConta;
import org.freedom.modulos.std.view.dialog.report.DLRTipoCob;

public class FTipoCob extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtDescTipoCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtDuplCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtNumconta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescconta = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSeqtalao = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );

	private ListaCampos lcConta = new ListaCampos( this, "" );

	private ListaCampos lcTalaoCheq = new ListaCampos( this, "" );
	
	private JRadioGroup<?, ?> rgTipoFebraban = null;

	private final JCheckBoxPad cbObriCartCob = new JCheckBoxPad( "Obrigar carteira de cobra�a?", "S", "N" );

	public FTipoCob() {

		super();
		setTitulo( "Cadastro de Tipo de Cobran�a" );
		setAtribos( 50, 50, 480, 320 );
		
		lcConta.add( new GuardaCampo( txtNumconta, "Numconta", "N�mero conta", ListaCampos.DB_PK, txtDescconta, false ) );
		lcConta.add( new GuardaCampo( txtDescconta, "Descconta", "Descri�ao do conta", ListaCampos.DB_SI, null, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setQueryCommit( false );
		lcConta.setReadOnly( true );
		txtNumconta.setTabelaExterna( lcConta, FConta.class.getCanonicalName() );

		lcTalaoCheq.add( new GuardaCampo( txtNumconta, "Numconta", "N�mero conta", ListaCampos.DB_PF, txtDescconta, false ) );
		lcTalaoCheq.add( new GuardaCampo( txtSeqtalao, "Seqtalao", "Seq.", ListaCampos.DB_PK, txtDescconta, false ) );
		lcTalaoCheq.montaSql( false, "TALAOCHEQ", "FN" );
		lcTalaoCheq.setQueryCommit( false );
		lcTalaoCheq.setReadOnly( true );
		txtSeqtalao.setTabelaExterna( lcTalaoCheq, FTalaoCheq.class.getCanonicalName() );
		
		montaRadioGrupos();

		montaTela();

		setListaCampos( true, "TIPOCOB", "FN" );
		lcCampos.setQueryInsert( false );

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

		rgTipoFebraban.setVlrString( "00" );
	}

	private void montaRadioGrupos() {

		/*********************
		 * TIPO FEBRABAN *
		 *********************/

		Vector<String> vLabsTipoFebraban = new Vector<String>();
		Vector<String> vValsTipoFebraban = new Vector<String>();

		vLabsTipoFebraban.addElement( "Carteira" );
		vLabsTipoFebraban.addElement( "Cheque" );
		vLabsTipoFebraban.addElement( "SIACC" );
		vLabsTipoFebraban.addElement( "CNAB" );
		vValsTipoFebraban.addElement( "00" );
		vValsTipoFebraban.addElement( "CQ" );
		vValsTipoFebraban.addElement( "01" );
		vValsTipoFebraban.addElement( "02" );

		rgTipoFebraban = new JRadioGroup<String, String>( 1, 2, vLabsTipoFebraban, vValsTipoFebraban );
	}

	private void montaTela() {

		adicCampo( txtCodTipoCob, 7, 20, 80, 20, "CodTipoCob", "C�d.tp.cob.", ListaCampos.DB_PK, true );
		adicCampo( txtDescTipoCob, 90, 20, 250, 20, "DescTipoCob", "Descri��o do tipo de cobran�a", ListaCampos.DB_SI, true );
		adicCampo( txtDuplCob, 343, 20, 80, 20, "DuplCob", "Duplicata", ListaCampos.DB_SI, false );
		adicDB( rgTipoFebraban, 7, 70, 416, 30, "TipoFebraban", "Tipo de cob. FEBRABAN ou forma de pagto.", false );
		adicDB( cbObriCartCob, 7, 110, 416, 20, "ObrigCartCob", "", true );
		adic( new JLabelPad( "Informa��es para impress�o de cheques" ), 7, 145, 420, 20 );
		
		JLabelPad borda = new JLabelPad();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		adic( borda, 7, 165, 420, 4 );
		adicCampo( txtNumconta, 7, 195, 80, 20, "Numconta", "N�mero conta", ListaCampos.DB_FK, true );
		adicDescFK( txtDescconta, 90, 195, 230, 20, "Descconta", "Descri��o da conta" );
		adicCampo( txtSeqtalao, 330, 195, 90, 20, "Seqtalao", "Seq. talon�rio", ListaCampos.DB_FK, true);
		
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

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		DLRTipoCob dl = null;
		ImprimeOS imp = null;
		int linPag = 0;

		dl = new DLRTipoCob();
		dl.setVisible( true );
		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}

		try {

			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.montaCab();
			imp.setTitulo( "Relat�rio de Tipos de Cobran�a" );
			imp.limpaPags();

			sSQL = "SELECT CODTIPOCOB,DESCTIPOCOB,DUPLCOB " + "FROM FNTIPOCOB " + "WHERE CODEMP=? AND CODFILIAL=? " + "ORDER BY " + dl.getValor();

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNTIPOCOB" ) );
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if ( imp.pRow() == 0 ) {
					imp.impCab( 80, false );
					imp.say( imp.pRow(), 0, imp.normal() );
					imp.say( imp.pRow(), 2, "C�digo" );
					imp.say( imp.pRow(), 20, "Descri��o" );
					imp.say( imp.pRow(), 60, "Duplicata" );
					imp.say( imp.pRow() + 1, 0, imp.normal() );
					imp.say( imp.pRow(), 0, StringFunctions.replicate( "-", 79 ) );
				}
				imp.say( imp.pRow() + 1, 0, imp.normal() );
				imp.say( imp.pRow(), 2, rs.getString( "CodTipoCob" ) );
				imp.say( imp.pRow(), 20, rs.getString( "DescTipoCob" ) );
				imp.say( imp.pRow(), 60, rs.getString( "DuplCob" ) != null ? rs.getString( "DuplCob" ) : "" );
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
			Funcoes.mensagemErro( this, "Erro consulta tabela de tipos de cobran�a!" + err.getMessage(), true, con, err );
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
	
	public void setConexao(DbConnection cn) {
		super.setConexao( cn );
		lcConta.setConexao( cn );
		lcTalaoCheq.setConexao( cn );
	}
	
}
