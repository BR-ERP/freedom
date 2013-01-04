/**
 * @version 03/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.rep <BR>
 *         Classe:
 * @(#)RelGrupo.java <BR>
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
 *                   Relatorio de grupos de produtos.
 * 
 */

package org.freedom.modulos.rep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JLabel;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

public class RelTransportadora extends FRelatorio {

	private static final long serialVersionUID = 1;

	private JRadioGroup<String, String> rgOrdem;

	public RelTransportadora() {

		super( false );
		setTitulo( "Listagem de transportadoras" );
		setAtribos( 50, 50, 285, 150 );

		Vector<String> labs = new Vector<String>();
		labs.add( "C�digo" );
		labs.add( "Nome fantasia" );
		Vector<String> vals = new Vector<String>();
		vals.add( "CODTRAN" );
		vals.add( "NOMETRAN" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, labs, vals );

		adic( new JLabel( "Ordem do relatorio :" ), 10, 10, 200, 20 );
		adic( rgOrdem, 10, 35, 250, 30 );
	}

	@ Override
	public void imprimir( TYPE_PRINT visualizar ) {

		try {

			StringBuilder sql = new StringBuilder();

			sql.append( "SELECT CODTRAN, NOMETRAN, ENDTRAN, CIDTRAN, CEPTRAN, " );
			sql.append( "ESTTRAN, DDDTRAN, FONETRAN, FAXTRAN, EMAILTRAN " );
			sql.append( "FROM RPTRANSP " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? " );
			sql.append( "ORDER BY " + rgOrdem.getVlrString() );

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPTRANSP" ) );
			ResultSet rs = ps.executeQuery();

			HashMap<String, Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "REPORT_CONNECTION", con.getConnection() );

			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/rptransportadora.jasper", "TRANSPORTADORAS", null, rs, hParam, this );

			if ( visualizar == TYPE_PRINT.VIEW ) {
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

}
