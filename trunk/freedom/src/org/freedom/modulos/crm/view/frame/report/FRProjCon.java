/**
 * @version 20/08/2011 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)FRDiario.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Relat�rio di�rio de liga��es.
 * 
 */

package org.freedom.modulos.crm.view.frame.report;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperPrintManager;

public class FRProjCon extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JRadioGroup<String, String> rgSitCon = null;
	
	private Vector<String> vLabsSitCon = new Vector<String>();
	private Vector<String> vValsSitCon = new Vector<String>();
	
	private JCheckBoxPad cbCobMensa = new JCheckBoxPad( "Cobran�a Mensal", "S", "N" );
	private JCheckBoxPad cbCobBimes = new JCheckBoxPad( "Cobran�a Bimenstral", "S", "N" );
	private JCheckBoxPad cbCobAnual = new JCheckBoxPad( "Cobran�a Anual", "S", "N" );
	private JCheckBoxPad cbCobEspor = new JCheckBoxPad( "Cobran�a Espor�dica", "S", "N" );
	
	private Vector<String> vLabsReceb = new Vector<String>();
	private Vector<String> vValsReceb = new Vector<String>();
	
	private JRadioGroup<String, String> rgReceb = null;

	public FRProjCon() {

		setTitulo( "Relat�rio de Projetos / Contratos" );
		setAtribos( 80, 80, 440, 230 );
		
		vLabsSitCon.addElement( "Ativo" );
		vLabsSitCon.addElement( "Inativo" );
		vLabsSitCon.addElement( "Ambos" );
		vValsSitCon.addElement( "A" );
		vValsSitCon.addElement( "I" );
		vValsSitCon.addElement( "S" );
		
		rgSitCon = new JRadioGroup<String, String>( 1, 3, vLabsSitCon, vValsSitCon );
		rgSitCon.setVlrString( "A" );

		vLabsReceb.addElement( "Receb�veis" );
		vLabsReceb.addElement( "Investimento" );
		vLabsReceb.addElement( "Ambos" );
		
		vValsReceb.addElement( "R" );
		vValsReceb.addElement( "I" );
		vValsReceb.addElement( "A" );
		
		rgReceb = new JRadioGroup<String, String>( 1,3, vLabsReceb, vValsReceb );
		adic( cbCobMensa, 7, 13, 180, 20 );
		adic( cbCobBimes, 200, 13, 180, 20 );
		adic( cbCobAnual, 7, 37, 180, 20 );
		adic( cbCobEspor, 200, 37, 180, 20 );
		
		adic( rgSitCon, 7, 63, 360, 30 );
	
		
		adic( rgReceb, 7, 96, 360, 30 );
		
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {
		
		StringBuilder sql = new StringBuilder();
		StringBuilder sFiltros = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean param = false;
		int iparam = 1;
		
		if ( cbCobMensa.getVlrString().equals( "N" )  && cbCobBimes.getVlrString().equals( "N" )  && cbCobAnual.getVlrString().equals( "N" ) && cbCobEspor.getVlrString().equals( "N" ) ) {
			Funcoes.mensagemInforma( this, "Cobran�a n�o selecionada !" );
			return;
		}
		
		sql.append( "SELECT CT.DESCCONTR, CT.CODCONTR, CT.CODCLI, CL.RAZCLI, ");
		sql.append( "SUM(I.QTDITCONTR) QTDITCONTR, SUM(I.VLRITCONTR) VLRITCONTR, SUM(I.QTDITCONTR*I.VLRITCONTR) TOTITCONTR ");
		sql.append( "FROM VDCONTRATO CT, VDITCONTRATO I, VDCLIENTE CL ");
		sql.append( "WHERE I.CODEMP=CT.CODEMP AND I.CODFILIAL=CT.CODFILIAL AND I.CODCONTR=CT.CODCONTR AND ");
		sql.append( "CT.CODEMP=? AND CT.CODFILIAL=? AND CL.CODEMP=CT.CODEMPCL AND CL.CODFILIAL=CT.CODFILIALCL AND CL.CODCLI=CT.CODCLI AND ");
		
		if ( rgSitCon.getVlrString().equals( "A" )){
			sql.append( "CT.ATIVO='S' AND "); 
			sFiltros.append( "  ATIVO " );
		} else if ( rgSitCon.getVlrString().equals( "I" ) ) {
			sql.append( "CT.ATIVO='N' AND ");
			sFiltros.append( "  N�O ATIVO " );
		} else {
			sql.append( "CT.ATIVO IN ('S','N') AND " );
		}
		
		sql.append( "CT.TPCOBCONTR IN (" ); 
		
		if ( cbCobMensa.getVlrString().equals( "S" ) ) {
			sFiltros.append( !sFiltros.equals( "" ) ? " - " : "" );
			sFiltros.append( " COBRAN�A MENSAL " );
			sql.append( "'ME'" );
			param=true;
		}
		if ( cbCobBimes.getVlrString().equals( "S" ) ) {
			if (param) {
				sql.append( "," );
			}
			sFiltros.append( !sFiltros.equals( "" ) ? " - " : "" );
			sFiltros.append( " COBRAN�A BIMESTRAL " );
			sql.append( "'BI'" );
			param = true;
		} 
		if ( cbCobAnual.getVlrString().equals( "S" ) ) {
			if (param) {
				sql.append( "," );
			} 
			sFiltros.append( !sFiltros.equals( "" ) ? " - " : "" );
			sFiltros.append( " COBRAN�A ANUAL " );
			sql.append( "'AN'" ); 
			param = true;

		}
		if ( cbCobEspor.getVlrString().equals( "S" ) ) {
			if (param) {
				sql.append( "," );
			} 
			sFiltros.append( !sFiltros.equals( "" ) ? " - " : "" );
			sFiltros.append( " COBRAN�A ESPOR�DICA " );
			sql.append( "'ES'" );
			param = true;
		} 
		
		sql.append( ") " );
		
		if ( rgReceb.getVlrString().equals( "R" ) ) {
			sql.append( " AND CT.RECEBCONTR='S' ");
		} else if ( rgReceb.getVlrString().equals( "I" ) ) {
			sql.append( " AND CT.RECEBCONTR='N' ");
		}	else  {
			Funcoes.mensagemInforma( this, "Relat�rio Detalhado n�o dispon�vel para modo texto !" );
			return;
		}

		
		sql.append( "GROUP BY CT.DESCCONTR, CT.CODCONTR, CT.CODCLI, CL.RAZCLI " );
		sql.append( "ORDER BY CL.RAZCLI, CT.CODCLI, CT.DESCCONTR, CT.CODCONTR" );

		try {

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDCONTRATO" ) );
			
			rs = ps.executeQuery();

		} catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, " Erro na consulta!" );
		}

		imprimiGrafico( rs, bVisualizar, sFiltros );

	}

	private void imprimiGrafico( final ResultSet rs, final TYPE_PRINT bVisualizar, StringBuilder sFiltros ) {

		FPrinterJob dlGr = null;
		String report = "layout/rel/REL_PROJ_CRONT_01.jasper";
		String label = "Relat�rio de projetos/contratos";
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/" );
		

		dlGr = new FPrinterJob( report, label, sFiltros.toString(), rs, hParam,  this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de projetos/contratos!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
	}

}
