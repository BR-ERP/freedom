/**
 * @version 17/12/2008 <BR>
 * @author Setpoint Inform�tica Ltda.
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FUF.java <BR>
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
 * Coment�rios sobre a classe...
 * 
 */
package org.freedom.modulos.cfg;

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modulos.std.DLRMunicipio;
import org.freedom.telas.FDados;
import org.freedom.telas.FPrinterJob;


public class FMunicipio extends FDados{

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodMunicipio = new JTextFieldPad( JTextFieldPad.TP_STRING, 5, 0 );
	
	private JTextFieldPad txtSiglaUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldFK txtNomeUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 ); 
	
	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 ); 
	
	private JTextFieldFK txtNomePais = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextFieldPad txtNomeMunicipio = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextFieldPad txtDDDMunicipio = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	
	private ListaCampos lcPais = new ListaCampos( this );

	private ListaCampos lcUF = new ListaCampos( this );
	
	public FMunicipio(){
		
		super();
		setTitulo( "Cadastro de UF" );
		setAtribos( 50, 50, 370, 220 );
		
		lcCampos.setUsaME( false );			
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		montaTela();
		montaListaCampos();
		
		setImprimir( true );
	}
	
	private void montaTela(){
		
		adicCampo( txtCodMunicipio, 7, 20, 60, 20, "CodMunic", "C�d.Mun.", ListaCampos.DB_PK, true );
		adicCampo( txtSiglaUF, 70, 20, 50, 20, "SiglaUf", "UF", ListaCampos.DB_PF, txtNomeUF, true );
		adicDescFK( txtNomeUF, 123, 20, 220, 20, "NomeUF", "Nome" );
		adicCampo( txtCodPais, 7, 60, 60, 20, "CodPais", "C�d.Pais", ListaCampos.DB_PF, txtNomePais, true );
		adicDescFK( txtNomePais, 70, 60, 273, 20, "NomePais", "Nome do Pa�s" );
		adicCampo( txtNomeMunicipio, 7, 100, 275, 20, "NomeMunic", "Municipio", ListaCampos.DB_SI, true );
		adicCampo( txtDDDMunicipio, 285, 100, 55, 20, "DDDMunic", "DDD", ListaCampos.DB_SI, true );
		
		setListaCampos( false, "MUNICIPIO", "SG" );
	}
	
	private void montaListaCampos(){
		
		/**********
		 *  PA�S  *
		 **********/
		
		lcPais.setUsaME( false );		
		lcPais.add( new GuardaCampo( txtCodPais, "Codpais", "C�d.pais.", ListaCampos.DB_PK, false ) );
		lcPais.add( new GuardaCampo( txtNomePais, "NomePais", "Descri��o do pa�s", ListaCampos.DB_SI, false ) );
		lcPais.montaSql( false, "PAIS", "SG" );
		lcPais.setQueryCommit( false );
		lcPais.setReadOnly( true );
		txtCodPais.setTabelaExterna( lcPais );
		
		/**********
		 *   UF   *
		 **********/
		
		lcUF.setUsaME( false );		
		lcUF.add( new GuardaCampo( txtSiglaUF, "SiglaUf", "Sigla UF", ListaCampos.DB_PK, false ) );
		lcUF.add( new GuardaCampo( txtNomeUF, "NomeUF", "Nome UF", ListaCampos.DB_SI, false ) );
		lcUF.montaSql( false, "UF", "SG" );
		lcUF.setQueryCommit( false );
		lcUF.setReadOnly( true );
		txtSiglaUF.setTabelaExterna( lcUF );
		
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
		DLRMunicipio dl = new DLRMunicipio();

		try {

			dl.setVisible( true );
			if ( dl.OK == false ) {
				dl.dispose();
				return;
			}
			
			sSQL.append( "SELECT M.CODMUNIC, M.SIGLAUF, M.CODPAIS, M.NOMEMUNIC, M.DDDMUNIC, " );
			sSQL.append( "P.NOMEPAIS, U.NOMEUF " );
			sSQL.append( "FROM SGMUNICIPIO M, SGUF U, SGPAIS P " );
			sSQL.append( "WHERE M.SIGLAUF=U.SIGLAUF AND M.CODPAIS=P.CODPAIS ORDER BY " + dl.getValor() );

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
			Funcoes.mensagemErro( this, "Erro ao montar relat�rio de Munic�pio!\n" + err.getMessage(), true, con, err );
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
			imp.setTitulo( "Relat�rio de Munic�pio" );

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
					imp.say( 2, "C�d.Munic" );
					imp.say( 12, "|" );
					imp.say( 14, "Nome.Munic" );
					imp.say( 40, "|" );
					imp.say( 42, "DDD" );
					imp.say( 47, "|" );
					imp.say( 49, "Sigla.UF" );
					imp.say( 58, "|" );
					imp.say( 60, "Nome.UF" );
					imp.say( 95, "|" );
					imp.say( 96, "Cod.Pa�s" );
					imp.say( 105, "|" );
					imp.say( 106, "Nome.Pa�s" );
					imp.say( 126, "|" );
					imp.pulaLinha( 1, imp.normal() );
					imp.say( 0, StringFunctions.replicate( "-", 125 ) );
				}

				imp.pulaLinha( 1, imp.normal() );
				imp.say( 2, rs.getString( "CodMunic" ).trim() );
				imp.say( 12, "|" );
				imp.say( 14, rs.getString( "NomeMunic" ).trim() );
				imp.say( 40, "|" );
				imp.say( 42, rs.getString( "DddMunic" ).trim() );
				imp.say( 47, "|" );
				imp.say( 49, rs.getString( "SiglaUf" ).trim() );
				imp.say( 58, "|" );
				imp.say( 60, rs.getString( "NomeUf" ).trim() );
				imp.say( 95, "|" );
				imp.say( 96, rs.getString( "CodPais" ).trim() );
				imp.say( 105, "|" );
				imp.say( 106, rs.getString( "NomePais" ).trim() );
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
			Funcoes.mensagemErro( this, "Erro consulta Municipio!\n" + err.getMessage(), true, con, err );

		}
	}
	
	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs ) {

		FPrinterJob dlGr = new FPrinterJob( "relatorios/RelMunicipio.jasper", "Municipio", null, rs, null, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de Munic�pio!" + err.getMessage(), true, con, err );
			}
		}
	}
	
	
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcPais.setConexao( cn ); 
		lcUF.setConexao( cn );
	}
}
