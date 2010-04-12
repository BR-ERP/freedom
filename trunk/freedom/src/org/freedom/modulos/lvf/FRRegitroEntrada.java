/**
 * @version 05/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.lvf <BR>
 * Classe:
 * @(#)FRRegistroEntrada.java <BR>
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
 * Comentario sobre a classe.
 * 
 */

package org.freedom.modulos.lvf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

public class FRRegitroEntrada extends FRelatorio {

	private static final long serialVersionUID = 1;

	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtPaginaIncial = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	public FRRegitroEntrada() {

		super();
		setTitulo( "Registro de Entrada" );		
		setAtribos( 50, 50, 295, 170 );
		
		montaTela();

		Calendar cal = Calendar.getInstance();
		txtDtFim.setVlrDate( cal.getTime() );
		cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) - 1 );
		txtDtIni.setVlrDate( cal.getTime() );
		
		txtPaginaIncial.setRequerido( true );
	}
	
	private void montaTela() {
		
		JLabel bordaData = new JLabel();
		bordaData.setBorder( BorderFactory.createEtchedBorder() );
		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER );
		periodo.setOpaque( true );
	
		adic( periodo, 20, 0, 80, 20 );
		adic( txtDtIni, 20, 20, 100, 20 );
		adic( new JLabel( "at�", SwingConstants.CENTER ), 120, 20, 40, 20 );
		adic( txtDtFim, 160, 20, 100, 20 );
		adic( bordaData, 10, 10, 260, 40 );
		
		adic( new JLabel( "P�gina Inicial" ), 10, 50, 100, 20 );
		adic( txtPaginaIncial, 10, 70, 100, 20 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {

		if ( txtDtIni.getVlrDate() != null && txtDtFim.getVlrDate() != null ) {
			if ( txtDtFim.getVlrDate().before( txtDtIni.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final inferior a inicial!" );
				txtDtIni.requestFocus();
				return;
			}
		}
		
		if ( txtPaginaIncial.getVlrInteger() < 1 ) {
			Funcoes.mensagemInforma( this, "P�ginal incial n�o informada!" );
			txtPaginaIncial.requestFocus();
			return;
		}
		
		try {
			
			String[] empresa = getEmpresa();
			
			StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT L.CODLF, L.TIPOLF, L.ANOMESLF, L.ESPECIELF, L.DOCINILF, L.SERIELF," );
			sql.append( "L.CODNAT, L.DTESLF, L.DTEMITLF, L.VLRCONTABILLF, L.VLRBASEICMSLF," );
			sql.append( "L.ALIQICMSLF, L.VLRICMSLF, L.VLRISENTASICMSLF, L.VLROUTRASICMSLF," );
			sql.append( "L.VLRBASEIPILF, L.ALIQIPILF, L.VLRIPILF, L.VLRISENTASIPILF," );
			sql.append( "L.VLROUTRASIPILF, L.CODEMITLF, L.UFLF, L.DOCFIMLF, L.OBSLF " );
			sql.append( "FROM LFLIVROFISCAL L " );
			sql.append( "WHERE L.CODEMP=? AND L.CODFILIAL=? AND L.TIPOLF='E' " );
			sql.append( "AND L.DTEMITLF BETWEEN ? AND ? " );
			sql.append( "ORDER BY L.DTEMITLF, L.DOCINILF, L.SERIELF, L.CODEMITLF, L.CODNAT " );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );
			ResultSet rs = ps.executeQuery();
			
			HashMap<String,Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "FOLHA", txtPaginaIncial.getVlrInteger() );
			hParam.put( "CNPJ", empresa[ 0 ] );
			hParam.put( "INSC", empresa[ 1 ] );
			hParam.put( "PERIODO", txtDtIni.getVlrString() + " at� " + txtDtFim.getVlrString() );
			hParam.put( "REPORT_CONNECTION", con.getConnection() );
			
			FPrinterJob dlGr = new FPrinterJob( "relatorios/RegistroEntrada.jasper", "REGISTRO DE ENTRADAS", null, rs, hParam, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}
			
			rs.close();
			ps.close();
			
			con.commit();
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}

	}
	
	private String[] getEmpresa() {
		
		String[] empresa = { "", "" };
		
		try {
			
			StringBuilder sql = new StringBuilder();			
			sql.append( "SELECT CNPJFILIAL, INSCFILIAL FROM SGFILIAL WHERE CODEMP=? AND CODFILIAL=? " );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGFILIAL" ) );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				empresa[ 0 ] = rs.getString( "CNPJFILIAL" );
				empresa[ 1 ] = rs.getString( "INSCFILIAL" );
			}
			
			rs.close();
			ps.close();
			
			con.commit();
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao busca dados da filial!\n" + e.getMessage() );
			e.printStackTrace();
		}
		
		return empresa;
	}

}
