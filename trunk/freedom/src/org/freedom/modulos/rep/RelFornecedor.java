/**
 * @version 03/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelFornecedor.java <BR>
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
 * Relatorio de fornecedores, em dois modos: completo e resumido.
 * 
 */

package org.freedom.modulos.rep;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.ListaCampos;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JLabel;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class RelFornecedor extends FRelatorio {

	private static final long serialVersionUID = 1;

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JRadioGroup<String, String> rgModo;
	
	private JRadioGroup<String, String> rgOrdem;
	
	private final ListaCampos lcFor = new ListaCampos( this );

	public RelFornecedor() {

		super( false );
		setTitulo( "Relatorio de fonecedores" );		
		setAtribos( 50, 50, 325, 250 );
		
		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.montaSql( false, "FORNECEDOR", "RP" );
		lcFor.setQueryCommit( false );
		lcFor.setReadOnly( true );
		txtCodFor.setListaCampos( lcFor );
		txtCodFor.setTabelaExterna( lcFor );
		txtCodFor.setPK( true );
		txtCodFor.setNomeCampo( "CodFor" );
		
		Vector<String> labs = new Vector<String>();
		labs.add( "Completo" );
		labs.add( "Resumido" );
		Vector<String> vals = new Vector<String>();
		vals.add( "C" );
		vals.add( "R" );
		rgModo = new JRadioGroup<String, String>( 1, 2, labs, vals );
		
		Vector<String> labs1 = new Vector<String>();
		labs1.add( "C�digo" );
		labs1.add( "Raz�o social" );
		Vector<String> vals1 = new Vector<String>();
		vals1.add( "CODFOR" );
		vals1.add( "RAZFOR" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, labs1, vals1 );
		
		adic( new JLabel( "Modo :" ), 10, 10, 200, 20 );
		adic( rgModo, 10, 35, 290, 30 );
		adic( new JLabel( "Ordem do relatorio :" ), 10, 70, 200, 20 );
		adic( rgOrdem, 10, 95, 290, 30 );
		
		adic( new JLabel( "C�d.for." ), 10, 130, 77, 20 );
		adic( txtCodFor, 10, 150, 77, 20 );
		adic( new JLabel( "Raz�o social do fornecedor" ), 90, 130, 210, 20 );
		adic( txtRazFor, 90, 150, 210, 20 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {

		try {
			
			String relatorio = "C".equals( rgModo.getVlrString() ) ? "rpfornecedorcomp.jasper" : "rpfornecedorresum.jasper";
			String modo = "C".equals( rgModo.getVlrString() ) ? "completo" : "resumido";
			
			StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT CODFOR,RAZFOR,NOMEFOR,CNPJFOR,INSCFOR,ENDFOR,CIDFOR,ESTFOR,CEPFOR,BAIRFOR,DDDFOR," );
			sql.append( "FONEFOR,FAXFOR,EMAILFOR,CODREPFOR " );
			sql.append( "FROM RPFORNECEDOR " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? " );
			if ( txtRazFor.getVlrString().trim().length() > 0 ) {
				sql.append( "AND CODFOR=" + txtCodFor.getVlrInteger().intValue() );
			}
			sql.append( "ORDER BY " + rgOrdem.getVlrString() );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPFORNECEDOR" ) );
			ResultSet rs = ps.executeQuery();
			
			HashMap<String,Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "REPORT_CONNECTION", con.getConnection() );
			
			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/"+relatorio, "FORNECEDORES ( "+ modo +" )", null, rs, hParam, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcFor.setConexao( cn );
	}

}
