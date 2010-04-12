/**
 * @version 11/03/2009 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FROrcamento.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                      de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                      Tela de dialogo para impress�o de relat�rio de or�amentos.
 * 
 */

package org.freedom.modulos.std;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JTextFieldPad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FROrcamento extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JCheckBoxPad cbAberto = new JCheckBoxPad( "Aberto", "S", "N" );

	private JCheckBoxPad cbImpresso = new JCheckBoxPad( "Impresso", "S", "N" );

	private JCheckBoxPad cbLiberado = new JCheckBoxPad( "Liberado", "S", "N" );

	private JCheckBoxPad cbFaturadoParcial = new JCheckBoxPad( "Faturado parcial", "S", "N" );

	private JCheckBoxPad cbFaturado = new JCheckBoxPad( "Faturado", "S", "N" );
	
	private JCheckBoxPad cbProduzido = new JCheckBoxPad( "Produzido", "S", "N" );
	
	private JCheckBoxPad cbCancelado = new JCheckBoxPad( "Cancelado", "S", "N" );

	public FROrcamento() {

		super( false );
		setTitulo( "Relat�rio de Or�amentos" );
		setAtribos( 80, 80, 330, 240 );

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Per�odo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 17, 10, 80, 20 );
		adic( lbLinha, 7, 20, 300, 45 );
		adic( txtDataini, 17, 35, 125, 20 );
		adic( new JLabelPad( "�", SwingConstants.CENTER ), 142, 35, 30, 20 );
		adic( txtDatafim, 172, 35, 125, 20 );

		JLabel status = new JLabel( "Status", SwingConstants.CENTER );
		status.setOpaque( true );
		JLabel borda2 = new JLabel();
		borda2.setBorder( BorderFactory.createEtchedBorder() );
		adic( status, 15, 75, 50, 18 );
		adic( borda2, 7, 85, 300, 80 );
		
		
		adic( cbAberto, 25, 90, 80, 20 );
		adic( cbFaturadoParcial, 140, 90, 120, 20 );
		
		adic( cbImpresso, 25, 107, 80, 20 );
		adic( cbFaturado, 140, 107, 80, 20 );
		
		adic( cbLiberado, 25, 124, 80, 20 );		
		adic( cbProduzido, 140, 124, 110, 20 );
		
		adic( cbCancelado, 25, 141, 110, 20 );
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	public void imprimir( boolean bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		StringBuilder status = new StringBuilder();
		StringBuilder filtros = new StringBuilder();
		
		try {

			if ( "S".equals( cbAberto.getVlrString() ) ) {
				status.append( "'*','OA'" );
				filtros.append( " em aberto" );
			}
			if ( "S".equals( cbImpresso.getVlrString() ) ) {
				if ( status.length() > 0 ) {
					status.append( "," );
					filtros.append( "," );
				}
				status.append( "'OC'" );
				filtros.append( " completos" );
			}
			if ( "S".equals( cbLiberado.getVlrString() ) ) {
				if ( status.length() > 0 ) {
					status.append( "," );
					filtros.append( "," );
				}
				status.append( "'OL'" );
				filtros.append( " liberados" );
			}
			if ( "S".equals( cbFaturadoParcial.getVlrString() ) ) {
				if ( status.length() > 0 ) {
					status.append( "," );
					filtros.append( "," );
				}
				status.append( "'FP'" );
				filtros.append( " faturados parcialmente" );
			}
			if ( "S".equals( cbFaturado.getVlrString() ) ) {
				if ( status.length() > 0 ) {
					status.append( "," );
					filtros.append( "," );
				}
				status.append( "'OV'" );
				filtros.append( " faturados" );
			}
			if ( "S".equals( cbProduzido.getVlrString() ) ) {
				if ( status.length() > 0 ) {
					status.append( "," );
				}
				status.append( "'OP'" );
			}
			if ( "S".equals( cbCancelado.getVlrString() ) ) {
				if ( status.length() > 0 ) {
					status.append( "," );
				}
				status.append( "'CA'" );
			}
			

			sql.append( "select o.codorc, o.dtorc, o.dtvencorc," );
			sql.append( "o.codcli, cl.razcli, o.vlrliqorc " );
			sql.append( "from vdorcamento o, vdcliente cl " );
			sql.append( "where " );
			sql.append( "cl.codemp=o.codempcl and cl.codfilial=o.codfilialcl and cl.codcli=o.codcli and " );
			sql.append( "o.codemp=? and o.codfilial=? and " );
			sql.append( "o.dtorc between ? and ? " );
			if ( status.length() > 0 ) {
				sql.append( "and o.statusorc in (" + status.toString() + ")" );
			}

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			ResultSet rs = ps.executeQuery();

			HashMap<String, Object> hParam = new HashMap<String, Object>();
			hParam.put( "FILTROS", filtros + "." );

			FPrinterJob dlGr = new FPrinterJob( "layout/rel/REL_ORC_01.jasper", "Relat�rio de or�amentos", "", rs, hParam, this );

			if ( bVisualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro consultar or�amentos!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
	}
}
