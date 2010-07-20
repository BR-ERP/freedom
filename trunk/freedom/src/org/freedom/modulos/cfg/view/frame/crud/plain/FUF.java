/**
 * @version 16/12/2008 <BR>
 * @author Setpoint Inform�tica Ltda.
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FUF.java <BR>
 * 
 *              Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *              modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *              na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *              Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *              sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *              Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *              Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *              de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *              Coment�rios sobre a classe...
 * 
 */
package org.freedom.modulos.cfg.view.frame.crud.plain;

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.modulos.std.view.dialog.report.DLRUf;

public class FUF extends FDados {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtSigla = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtNomeUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtCodUf = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtNomePais = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JRadioGroup<?, ?> rgRegiao = null;

	private ListaCampos lcPais = new ListaCampos( this );

	public FUF() {

		super();
		setTitulo( "Cadastro de UF" );
		setAtribos( 50, 50, 370, 250 );

		lcCampos.setUsaME( false );

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		montaTela();
		montaListaCampos();

		setImprimir( true );
	}

	private void montaTela() {

		Vector<String> vVals = new Vector<String>();
		Vector<String> vLabs = new Vector<String>();

		vLabs.addElement( "Norte" );
		vLabs.addElement( "Nordeste" );
		vLabs.addElement( "Sul" );
		vLabs.addElement( "Sudeste" );
		vLabs.addElement( "Centro Oeste" );
		vVals.addElement( "N" );
		vVals.addElement( "NE" );
		vVals.addElement( "S" );
		vVals.addElement( "SE" );
		vVals.addElement( "CO" );
		rgRegiao = new JRadioGroup<String, String>( 2, 3, vLabs, vVals );

		adicCampo( txtSigla, 7, 20, 60, 20, "SiglaUf", "UF", ListaCampos.DB_PK, true );
		adicCampo( txtCodPais, 70, 20, 60, 20, "CodPais", "C�d.Pa�s", ListaCampos.DB_PF, txtNomePais, true );
		adicDescFK( txtNomePais, 133, 20, 200, 20, "NomePais", "Nome do pa�s" );
		adicCampo( txtNomeUF, 7, 60, 273, 20, "NomeUF", "Nome", ListaCampos.DB_SI, true );
		adicCampo( txtCodUf, 283, 60, 50, 20, "CodUf", "C�d.Uf", ListaCampos.DB_SI, true );
		adicDB( rgRegiao, 7, 100, 325, 60, "RegiaoUF", "Regi�o", true );
		setListaCampos( false, "UF", "SG" );

	}

	private void montaListaCampos() {

		lcPais.setUsaME( false );

		lcPais.add( new GuardaCampo( txtCodPais, "Codpais", "C�d.pais.", ListaCampos.DB_PK, false ) );
		lcPais.add( new GuardaCampo( txtNomePais, "NomePais", "Descri��o do pa�s", ListaCampos.DB_SI, false ) );
		lcPais.montaSql( false, "PAIS", "SG" );
		lcPais.setQueryCommit( false );
		lcPais.setReadOnly( true );
		txtCodPais.setTabelaExterna( lcPais, FPais.class.getCanonicalName() );
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
		StringBuffer sSQL = new StringBuffer();
		DLRUf dl = new DLRUf();

		try {

			dl.setVisible( true );
			if ( dl.OK == false ) {
				dl.dispose();
				return;
			}

			sSQL.append( "SELECT U.SIGLAUF, U.CODPAIS, U.NOMEUF, U.CODUF, U.REGIAOUF, P.NOMEPAIS " );
			sSQL.append( "FROM SGUF U, SGPAIS P " );
			sSQL.append( "WHERE U.CODPAIS=P.CODPAIS ORDER BY " + dl.getValor() );

			ps = con.prepareStatement( sSQL.toString() );
			rs = ps.executeQuery();

			if ( "T".equals( dl.getTipo() ) ) {
				imprimirTexto( bVisualizar, rs );
			}
			else if ( "G".equals( dl.getTipo() ) ) {
				imprimirGrafico( bVisualizar, rs );
			}

			rs.close();
			ps.close();

			con.commit();
			dl.dispose();
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao montar relat�rio de UF!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();

		}
	}

	private void imprimirTexto( final boolean bVisualizar, final ResultSet rs ) {

		String sLinhaFina = StringFunctions.replicate( "-", 125 );
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		imp.montaCab();

		try {

			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relat�rio de UF" );

			while ( rs.next() ) {

				if ( imp.pRow() == linPag ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + sLinhaFina + "+" );
					imp.eject();
					imp.incPags();
				}

				if ( imp.pRow() == 0 ) {
					imp.impCab( 126, false );
					imp.say( 0, imp.normal() );
					imp.say( 2, "C�d.UF" );
					imp.say( 10, "|" );
					imp.say( 14, "Sigla.UF" );
					imp.say( 24, "|" );
					imp.say( 28, "Nome.UF" );
					imp.say( 60, "|" );
					imp.say( 64, "Regi�o.UF" );
					imp.say( 75, "|" );
					imp.say( 79, "C�d Pa�s" );
					imp.say( 89, "|" );
					imp.say( 93, "Nome Pa�s" );
					imp.say( 126, "|" );
					imp.pulaLinha( 1, imp.normal() );
					imp.say( 0, StringFunctions.replicate( "-", 125 ) );
				}

				imp.pulaLinha( 1, imp.normal() );
				imp.say( 2, rs.getString( "CodUf" ).trim() );
				imp.say( 10, "|" );
				imp.say( 14, rs.getString( "SiglaUf" ).trim() );
				imp.say( 24, "|" );
				imp.say( 28, rs.getString( "NomeUf" ).trim() );
				imp.say( 60, "|" );
				imp.say( 64, rs.getString( "RegiaoUf" ).trim() );
				imp.say( 75, "|" );
				imp.say( 79, rs.getString( "CodPais" ).trim() );
				imp.say( 89, "|" );
				imp.say( 93, rs.getString( "NomePais" ).trim() );
				imp.say( 126, "|" );

				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.pulaLinha( 1, imp.normal() );
			imp.say( 0, StringFunctions.replicate( "=", 125 ) );
			imp.pulaLinha( 1, imp.normal() );

			imp.eject();
			imp.fechaGravacao();

			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta UF!\n" + err.getMessage(), true, con, err );

		}
	}

	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs ) {

		FPrinterJob dlGr = new FPrinterJob( "relatorios/RelUF.jasper", "UF", null, rs, null, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de UF!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcPais.setConexao( cn );
	}
}
