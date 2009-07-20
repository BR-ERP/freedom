/**
 * @version 03/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelTipoCli.java <BR>
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
 * Relatorio de tipo de cliente.
 * 
 */

package org.freedom.modulos.rep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JLabel;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class RelTipoCli extends FRelatorio {

	private static final long serialVersionUID = 1;
	
	private JRadioGroup<String, String> rgOrdem;

	public RelTipoCli() {

		super( false );
		setTitulo( "Relatorio de tipos de clientes" );		
		setAtribos( 50, 50, 285, 150 );
		
		Vector<String> labs = new Vector<String>();
		labs.add( "C�digo" );
		labs.add( "Descri��o" );
		Vector<String> vals = new Vector<String>();
		vals.add( "CODTIPOCLI" );
		vals.add( "DESCTIPOCLI" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, labs, vals );
		
		adic( new JLabel( "Ordem do relatorio :" ), 10, 10, 200, 20 );
		adic( rgOrdem, 10, 35, 250, 30 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {

		try {
			
			StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT TP.CODTIPOCLI AS CODIGO ,TP.DESCTIPOCLI AS DESCRICAO,TP.TIPOCLI AS TIPO," );
			sql.append( "(SELECT COUNT(CLI.CODCLI) FROM RPCLIENTE CLI " );
			sql.append( "WHERE CLI.CODEMPTC=TP.CODEMP AND CLI.CODFILIALTC=TP.CODFILIAL AND CLI.CODTIPOCLI=TP.CODTIPOCLI) AS QTD " ); 
			sql.append( "FROM RPTIPOCLI TP " );
			sql.append( "WHERE TP.CODEMP=? AND TP.CODFILIAL=? " );
			sql.append( "ORDER BY " + rgOrdem.getVlrString() );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPTIPOCLI" ) );
			ResultSet rs = ps.executeQuery();
			
			HashMap<String,Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "REPORT_CONNECTION", con.getConnection() );
			
			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/rptipocli.jasper", "TIPOS DE CLIENTE", null, rs, hParam, this );

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

}
