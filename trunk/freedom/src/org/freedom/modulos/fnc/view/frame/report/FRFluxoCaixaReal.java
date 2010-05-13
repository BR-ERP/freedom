/**
 * @version 06/11/2009 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.fnc <BR>
 *         Classe: @(#)FRFluxoCaixaReal.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Fluxo de Caixa / Realizado.
 * 
 */

package org.freedom.modulos.fnc.view.frame.report;

import java.awt.Component;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

public class FRFluxoCaixaReal extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	
	private boolean bComp = false;
	
	private Component tela = null;
	
	private ListaCampos lcAtend = new ListaCampos( this, "AE" );

	public FRFluxoCaixaReal() {

		setTitulo( "Relat�rio de atendimentos" );
		setAtribos( 80, 80, 370, 200 );
	
		montaTela();
		tela = this; 
		
	}
	
	public void setParametros(Integer codcli, Date dtini, Date dtfim ) {
		txtDataini.setVlrDate( dtini );
		txtDatafim.setVlrDate( dtfim );	
	}


	private void montaTela() {
		
		//JLabelPad lbLinha = new JLabelPad();
		//lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Per�odo:" , SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );
		
		adic( lbPeriodo, 7, 1, 80, 20 );
		//adic( lbLinha, 5, 10, 180, 45 );	
		adic( new JLabelPad("De:"), 15, 25, 20, 20 );
		adic( txtDataini, 38, 25, 95, 20 );
		adic( new JLabelPad("At�:"), 145, 25, 35, 20 );
		adic( txtDatafim, 178, 25, 95, 20 );

		Calendar cPeriodo = Calendar.getInstance();
	    txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
		txtDataini.setVlrDate(cPeriodo.getTime());
		
	}
	
	public void imprimir( boolean bVisualizar ) {

		StringBuilder sqlsaldo = new StringBuilder();
		StringBuilder sql = new StringBuilder();
		BigDecimal saldosl = new BigDecimal(0);
		PreparedStatement ps = null; 
		ResultSet rs = null;
		int iparam = 1;		
		
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( tela, "Data final maior que a data inicial!" );
			return;
		}
		
		sqlsaldo.append( "SELECT SUM((SELECT FIRST 1 SL.SALDOSL FROM FNSALDOLANCA SL " );
		sqlsaldo.append( "WHERE SL.DATASL < ? AND ");
		sqlsaldo.append( "SL.CODEMP=CT.CODEMPPN AND SL.CODFILIAL=CT.CODFILIALPN AND SL.CODPLAN=CT.CODPLAN " );
		sqlsaldo.append( "ORDER BY SL.DATASL DESC )) SALDOSL FROM FNCONTA CT " );
		sqlsaldo.append( "WHERE CT.CODEMP=? AND CT.CODFILIAL=? AND CT.TIPOCAIXA='F' AND CT.ATIVACONTA='S'" );
		
		sql.append( "SELECT P.CLASFINPLAN CLASFIN, P.ESFINPLAN ESFIN, P.CODPLAN, P.DESCPLAN, P.NIVELPLAN, ");
        sql.append( "(SELECT SUM(SL.VLRSUBLANCA*-1) FROM FNSUBLANCA SL, FNLANCA L ");
   		sql.append( "WHERE SL.CODPLAN LIKE RTRIM(P.CODPLAN)||'%' AND SL.CODLANCA=L.CODLANCA AND ");
		sql.append( "SL.DATASUBLANCA BETWEEN ? AND ? AND ");
		sql.append( "SL.CODEMP=L.CODEMP AND SL.CODFILIAL=L.CODFILIAL AND L.CODEMP=? AND L.CODFILIAL=?) VLRSUBLANCA ");
		sql.append( "FROM FNPLANEJAMENTO P WHERE P.TIPOPLAN IN ('R','D') AND ");
		sql.append( "P.CODEMP=? AND P.CODFILIAL=? AND ");
		sql.append( "EXISTS( SELECT * FROM FNSUBLANCA SL, FNLANCA L ");
		sql.append( "WHERE SL.CODPLAN LIKE RTRIM(P.CODPLAN)||'%' AND SL.CODLANCA=L.CODLANCA AND ");
		sql.append( "SL.DATASUBLANCA BETWEEN ? AND ? AND ");
		sql.append( "SL.CODEMP=L.CODEMP AND SL.CODFILIAL=L.CODFILIAL AND L.CODEMP=? AND L.CODFILIAL=? ) ");
		sql.append( "ORDER BY  1 DESC, 2, 3, 4, 5" );
		
		try {
			//System.out.println(sqlsaldo.toString());
			ps = con.prepareStatement( sqlsaldo.toString() );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDataini.getVlrString()));
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNCONTA" ));
			rs = ps.executeQuery();
			if ( rs.next() ) {
				saldosl = rs.getBigDecimal( "SALDOSL" );
			}
			con.commit();

			iparam = 1;
			System.out.println(sql.toString());
			ps = con.prepareStatement( sql.toString() );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDataini.getVlrString()));
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString()));
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNLANCA" ));
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ));
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDataini.getVlrString()));
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString()));
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNLANCA" ));
			rs = ps.executeQuery();
			
		} 
		catch ( SQLException err ) {
				
			err.printStackTrace();
			Funcoes.mensagemErro( this," Erro na consulta da tabela de atendimentos" );
		}
		
		imprimiGrafico( rs, bVisualizar, saldosl );
		
	}
	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar, final BigDecimal saldosl ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNLANCA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/fnc/"); 
		hParam.put( "SALDOSL", saldosl );
		hParam.put( "DTINI", txtDataini.getVlrDate());
		hParam.put( "DTFIM", txtDatafim.getVlrDate());
		hParam.put( "CONEXAO", con.getConnection() );
				
		dlGr = new FPrinterJob( "relatorios/fnc/fluxocaixareal.jasper", "FLUXO DE CAIXA REALIZADO", "", rs, hParam,this );	

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( tela, "Erro na impress�o de relat�rio de fluxo de caixa realizado!" + err.getMessage(), true, con, err );
			}
		}
	}
	
}
