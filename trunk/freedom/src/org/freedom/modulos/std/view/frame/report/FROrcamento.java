/**
 * @version 11/03/2009 <BR>
 * @author Setpoint Inform�tica Ltda./Vin�cius Cintra Domingos <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FROrcamento.java <BR>
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
 *                      Tela de dialogo para impress�o de relat�rio de or�amentos.
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

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

	private JCheckBoxPad cbAgruparVendedor = new JCheckBoxPad( "Agrupar por Vendedor", "S", "N" );

	private JCheckBoxPad cbCliSemVenda = new JCheckBoxPad( "Somente clientes sem venda no per�dodo", "S", "N" );

	public FROrcamento() {

		super( false );
		setTitulo( "Relat�rio de Or�amentos" );
		setAtribos( 80, 80, 330, 280 );

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
		adic( borda2, 7, 85, 300, 110 );

		adic( cbAberto, 25, 90, 80, 20 );
		adic( cbFaturadoParcial, 140, 90, 120, 20 );

		adic( cbImpresso, 25, 107, 80, 20 );
		adic( cbFaturado, 140, 107, 80, 20 );

		adic( cbLiberado, 25, 124, 80, 20 );
		adic( cbProduzido, 140, 124, 110, 20 );

		adic( cbCancelado, 25, 141, 110, 20 );
		adic( cbAgruparVendedor, 140, 141, 160, 20 );

		adic( cbCliSemVenda, 25, 159, 280, 20 );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		String reportFileName = "layout/rel/REL_ORC_01.jasper";

		StringBuilder sql = new StringBuilder();
		StringBuilder status = new StringBuilder();
		StringBuilder filtros = new StringBuilder();
		String orderBy = "";
		filtros.append( "Per�odo de " );
		filtros.append( txtDataini.getVlrString() );
		filtros.append( " at� " );
		filtros.append( txtDatafim.getVlrString() );

		try {

			if ( "S".equals( cbAberto.getVlrString() ) ) {
				status.append( "'*','OA'" );
				filtros.append( "," );
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
				filtros.append( ", produzidos" );
			}
			if ( "S".equals( cbCancelado.getVlrString() ) ) {
				if ( status.length() > 0 ) {
					status.append( "," );
				}
				status.append( "'CA'" );
				filtros.append( ", cancelados" );
			}
			if ( "S".equals( cbAgruparVendedor.getVlrString() ) ) {
				reportFileName = "layout/rel/REL_ORC_02.jasper";
				orderBy = " order by o.codvend ";
			}

			sql.append( "select o.codorc, o.dtorc, o.dtvencorc," );
			sql.append( "o.codcli, cl.razcli, o.vlrliqorc, " );
			sql.append( "o.codvend, ve.nomevend " );
			sql.append( "from vdorcamento o, vdcliente cl, vdvendedor ve " );
			sql.append( "where " );
			sql.append( "cl.codemp=o.codempcl and cl.codfilial=o.codfilialcl and cl.codcli=o.codcli and " );
			sql.append( "ve.codemp=o.codempvd and ve.codfilial=o.codfilialvd and ve.codvend=o.codvend and " );
			sql.append( "o.codemp=? and o.codfilial=? and " );
			sql.append( "o.dtorc between ? and ? " );
			if ( status.length() > 0 ) {
				sql.append( "and o.statusorc in (" + status.toString() + ")" );
			}
			if ( "S".equals( cbCliSemVenda.getVlrString() ) ) {
				filtros.append( ", somente clientes sem vendas no per�odo" );
				sql.append( " and not exists (select * from vdvenda v, vditvenda iv " );
				sql.append( " where v.codemp=iv.codemp and v.codfilial=iv.codfilial and v.tipovenda=iv.tipovenda " );
				sql.append( " and v.codvenda=iv.codvenda and v.codempcl=o.codempcl and v.codfilialcl=o.codfilialcl " );
				sql.append( " and v.codcli=o.codcli and v.dtemitvenda between ? and ? ) " );
			}
			sql.append( orderBy );

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			if ( "S".equals( cbCliSemVenda.getVlrString() ) ) {
				ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			}
			ResultSet rs = ps.executeQuery();

			HashMap<String, Object> hParam = new HashMap<String, Object>();
			hParam.put( "FILTROS", filtros + "." );

			FPrinterJob dlGr = new FPrinterJob( reportFileName, "Relat�rio de or�amentos", "", rs, hParam, this );

			if ( bVisualizar == TYPE_PRINT.VIEW ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao consultar or�amentos!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
	}
}
