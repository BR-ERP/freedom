/**
 * @version 22/11/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: FreedomSTD <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:(#)FRRegDuplicatas.java <BR>
 * 
 *                      Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                      modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                      na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                      Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                      sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                      Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                      Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                      de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                      Relat�rio de Registros de Duplicatas.
 * 
 */
package org.freedom.modulos.std.view.frame.report;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.modulos.crm.business.object.Atendimento;
import org.freedom.modulos.crm.business.object.Atendimento.PREFS;


public class FRRegDuplicatas extends FRelatorio {

	private static final long serialVersionUID = 1L;
	
	private final JTextFieldPad txtDataIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDataFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtFolhaInicial = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private Blob fotoEmp = null;
	
	private enum DADOS_EMPRESA{ CNPJFILIAL, INSCFILIAL, ENDFILIAL, NUMFILIAL }

	private Object dadosEmpresa[] = null;

	public FRRegDuplicatas() {

		super();
		setTitulo( "Registro de Duplicatas" );
		setAtribos( 50, 50, 320, 180 );

		montaTela();
		txtFolhaInicial.setRequerido( true );
	}
	
	
	private void montaTela() {

		JLabel bordaData = new JLabel();
		bordaData.setBorder( BorderFactory.createEtchedBorder() );
		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER );
		periodo.setOpaque( true );

		adic( periodo, 20, 0, 80, 20 );
		adic( txtDataIni, 20, 20, 100, 20 );
		adic( new JLabel( "at�", SwingConstants.CENTER ), 120, 20, 40, 20 );
		adic( txtDataFim, 160, 20, 100, 20 );
		adic( bordaData, 10, 10, 260, 40 );

		adic( new JLabel( "P�gina Inicial" ), 10, 50, 100, 20 );
		adic( txtFolhaInicial, 10, 70, 100, 20 );
		
		Calendar cal = Calendar.getInstance();
		txtDataFim.setVlrDate( cal.getTime() );
		cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) - 1 );
		txtDataIni.setVlrDate( cal.getTime() );
	}
	
	public void consist(){
		if ( txtDataIni.getVlrDate() != null && txtDataFim.getVlrDate() != null ) {
			if ( txtDataFim.getVlrDate().before( txtDataIni.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final inferior a inicial!" );
				txtDataIni.requestFocus();
				return;
			}
		}

		else if ( txtFolhaInicial.getVlrInteger() < 1 ) {
			Funcoes.mensagemInforma( this, "Folha incial n�o informada!" );
			txtFolhaInicial.requestFocus();
			return;
		}
		
	}

	public void imprimir( boolean bVisualizar ) {
		
		consist();
		Blob fotoemp = null;
		StringBuilder sql = null;
		String sCab = null;
		
		try {
			PreparedStatement ps = con.prepareStatement( "SELECT FOTOEMP FROM SGEMPRESA WHERE CODEMP=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				fotoemp = rs.getBlob( "FOTOEMP" );
			}
			rs.close();
			ps.close();
			con.commit();

		} catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro carregando logotipo.\n" + e.getMessage() );
			e.printStackTrace();
		}		
		
		sCab = "Per�odo de " + txtDataIni.getVlrString()  + " a " +  txtDataFim.getVlrString();
		
		
		sql = new StringBuilder("SELECT ");
		sql.append( "vd.dtemitvenda emissao, cl.codcli, cl.razcli, ir.dtvencitrec vencimento, ir.dtpagoitrec pagamento, " );
		sql.append( "vd.docvenda nota, tm.serie, ir.codrec fatura, ir.nparcitrec parcela, ir.vlritrec valor " );
		sql.append( "from fnitreceber ir, fnreceber re, vdvenda vd, vdcliente cl, eqtipomov tm " );
		sql.append( "where tm.fiscaltipomov='S' and " );
		sql.append( "re.codemp=? and re.codfilial=? and re.codemp=ir.codemp and re.codfilial=ir.codfilial and re.codrec=ir.codrec and " );
		sql.append( "vd.codemp=re.codempva and vd.codfilial=re.codfilialva and vd.codvenda=re.codvenda and " );
		sql.append( "vd.tipovenda=re.tipovenda and cl.codemp=vd.codempcl and " );
		sql.append( "cl.codfilial=vd.codfilialcl and cl.codcli=vd.codcli and " );
		sql.append( "tm.codemp=vd.codemptm and tm.codfilial=vd.codfilialtm and tm.codtipomov=vd.codtipomov " );
		sql.append( "and vd.dtemitvenda between ? and ? " );
		sql.append( "order by vd.dtemitvenda, vd.docvenda, tm.serie" );
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
				
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataIni.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDataFim.getVlrDate() ) );
			
			rs = ps.executeQuery();

		} catch (Exception err) {
			Funcoes.mensagemErro( this, "Erro consulta Registro de duplicatas\n" + err.getMessage(), true, con, err );
		}

		imprimiGrafico( bVisualizar, sCab, rs, fotoemp);
	}
	
	private void imprimiGrafico( boolean bVisualizar, 
			String sCab, ResultSet rs, Blob fotoemp ) {
		
		String report = "relatorios/RegistroDuplicata.jasper";
		String label = "Relat�rio de Duplicatas";
		//Object[] empresa = getEmpresa();
		
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "FOLHA", txtFolhaInicial.getVlrInteger() );
		hParam.put( "PERIODO", txtDataIni.getVlrString() + " at� " + txtDataFim.getVlrString() );
		hParam.put( "REPORT_CONNECTION", con.getConnection() );
		/*
		hParam.put( "CNPJFILIAL", (String) empresa[ DADOS_EMPRESA.CNPJFILIAL.ordinal() ] );
		hParam.put( "INSCFILIAL", (String) empresa[ DADOS_EMPRESA.INSCFILIAL.ordinal() ] );
		hParam.put( "ENDFILIAL", (String) empresa[ DADOS_EMPRESA.ENDFILIAL.ordinal() ] );
		hParam.put( "NUMFILIAL", (Integer) empresa[ DADOS_EMPRESA.NUMFILIAL.ordinal() ] );
		*/
		
		try {
			hParam.put( "LOGOEMP",  new ImageIcon(fotoemp.getBytes(1, ( int ) fotoemp.length())).getImage() );
	
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro carregando logotipo !\n" + e.getMessage()  );
			e.printStackTrace();
		}
		
		FPrinterJob dlGr = new FPrinterJob( report, label, sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		} else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de Registro de duplicatas!" + err.getMessage(), true, con, err );
			}
		}
	}
	
	private Object[] getEmpresa() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		dadosEmpresa = new Object[ DADOS_EMPRESA.values().length];

		try {

			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT CNPJFILIAL, INSCFILIAL, ENDFILIAL, NUMFILIAL FROM SGFILIAL WHERE CODEMP=? AND CODFILIAL=? " );

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGFILIAL" ) );
			rs = ps.executeQuery();

			if ( rs.next() ) {

				dadosEmpresa[ DADOS_EMPRESA.CNPJFILIAL.ordinal() ] = rs.getString( DADOS_EMPRESA.CNPJFILIAL.toString() );
				dadosEmpresa[ DADOS_EMPRESA.INSCFILIAL.ordinal() ] = rs.getString( DADOS_EMPRESA.INSCFILIAL.toString() );
				dadosEmpresa[ DADOS_EMPRESA.ENDFILIAL.ordinal() ] = rs.getString( DADOS_EMPRESA.ENDFILIAL.toString() );
				dadosEmpresa[ DADOS_EMPRESA.NUMFILIAL.ordinal() ] = new Integer( rs.getInt( DADOS_EMPRESA.NUMFILIAL.toString() ) );
				
			}
			rs.close();
			ps.close();
			con.commit();
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao busca dados da filial!\n" + e.getMessage() );
			e.printStackTrace();
		}
		return dadosEmpresa;
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
	
	}
}