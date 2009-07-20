/**
 * @version 05/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelEvolcaoVendas.java <BR>
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
 * Relatorio de evolu��o de vendas, em gr�ficos pizza e em barras.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.rep.RPPrefereGeral.EPrefere;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class RelEvolucaoVendas extends FRelatorio {

	private static final long serialVersionUID = 1;

	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldFK txtNomeMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JRadioGroup<String, String> rgModo;
	
	private final ListaCampos lcCliente = new ListaCampos( this );
	
	private final ListaCampos lcFornecedor = new ListaCampos( this );
	
	private final ListaCampos lcVendedor = new ListaCampos( this );
	
	private final ListaCampos lcMoeda = new ListaCampos( this );
	
	private List<Object> prefere = new ArrayList<Object>();
	

	public RelEvolucaoVendas() {

		super( false );
		setTitulo( "Relatorio de Evolu��o de Vendas" );
		setAtribos( 50, 50, 635, 290 );

		montaRadioGrupos();
		montaListaCampos();
		montaTela();

		Calendar cal = Calendar.getInstance();
		txtDtFim.setVlrDate( cal.getTime() );
		cal.set( cal.get( Calendar.YEAR ), Calendar.JANUARY, 1 );
		txtDtIni.setVlrDate( cal.getTime() );
	}

	private void montaRadioGrupos() {

		Vector<String> labs = new Vector<String>();
		labs.add( "Pizza" );
		labs.add( "Barras" );
		labs.add( "Linha" );
		Vector<String> vals = new Vector<String>();
		vals.add( "P" );
		vals.add( "B" );
		vals.add( "L" );
		rgModo = new JRadioGroup<String, String>( 3, 1, labs, vals );
	}
	
	private void montaListaCampos() {
		
		/***********
		 * CLIENTE *
		 ***********/
		
		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.montaSql( false, "CLIENTE", "RP" );
		lcCliente.setQueryCommit( false );
		lcCliente.setReadOnly( true );
		txtCodCli.setListaCampos( lcCliente );
		txtCodCli.setTabelaExterna( lcCliente );
		txtCodCli.setPK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		
		/**************
		 * FORNECEDOR *
		 **************/
		
		lcFornecedor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, false ) );
		lcFornecedor.add( new GuardaCampo( txtRazFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedor.montaSql( false, "FORNECEDOR", "RP" );
		lcFornecedor.setQueryCommit( false );
		lcFornecedor.setReadOnly( true );
		txtCodFor.setListaCampos( lcFornecedor );
		txtCodFor.setTabelaExterna( lcFornecedor );
		txtCodFor.setPK( true );
		txtCodFor.setNomeCampo( "CodFor" );
		
		/************
		 * VENDEDOR *
		 ************/
		
		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.vend.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do vendedor", ListaCampos.DB_SI, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "RP" );
		lcVendedor.setQueryCommit( false );
		lcVendedor.setReadOnly( true );
		txtCodVend.setListaCampos( lcVendedor );
		txtCodVend.setTabelaExterna( lcVendedor );
		txtCodVend.setPK( true );
		txtCodVend.setNomeCampo( "CodVend" );
		
		/*********
		 * MOEDA *
		 *********/
		
		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "C�d.moeda", ListaCampos.DB_PK, true ) );
		lcMoeda.add( new GuardaCampo( txtNomeMoeda, "SingMoeda", "Descri��o da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.montaSql( false, "MOEDA", "RP" );
		lcMoeda.setQueryCommit( false );
		lcMoeda.setReadOnly( true );
		txtCodMoeda.setListaCampos( lcMoeda );
		txtCodMoeda.setTabelaExterna( lcMoeda );
		txtCodMoeda.setPK( true );
		txtCodMoeda.setNomeCampo( "CodMoeda" );
	}

	private void montaTela() {

		adic( new JLabel( "Modo do relat�rio :" ), 10, 10, 200, 20 );
		adic( new JLabel( Icone.novo( "graficoPizza.gif" ) ), 10, 40, 30, 30 );
		adic( new JLabel( Icone.novo( "graficoBarra.png" ) ), 10, 70, 30, 30 );
		adic( new JLabel( Icone.novo( "graficoLinha.gif" ) ), 10, 100, 30, 30 );
		adic( rgModo, 25, 35, 275, 105 );

		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER );
		periodo.setOpaque( true );
		adic( periodo, 25, 150, 60, 20 );
		
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		adic( borda, 10, 160, 290, 45 );
		
		adic( txtDtIni, 25, 175, 110, 20 );
		adic( new JLabel( "at�", SwingConstants.CENTER ), 135, 175, 40, 20 );
		adic( txtDtFim, 175, 175, 110, 20 );
		
		adic( new JLabel( "C�d.for." ), 310, 35, 87, 20 );
		adic( txtCodFor, 310, 55, 87, 20 );
		adic( new JLabel( "Raz�o social do fornecedor" ), 400, 35, 210, 20 );
		adic( txtRazFor, 400, 55, 210, 20 );
		
		adic( new JLabel( "C�d.vend." ), 310, 75, 87, 20 );
		adic( txtCodVend, 310, 95, 87, 20 );
		adic( new JLabel( "Nome do vendedor" ), 400, 75, 210, 20 );
		adic( txtNomeVend, 400, 95, 210, 20 );
		
		adic( new JLabel( "C�d.cli." ), 310, 115, 87, 20 );
		adic( txtCodCli, 310, 135, 87, 20 );
		adic( new JLabel( "Raz�o social do cliente" ), 400, 115, 210, 20 );
		adic( txtRazCli, 400, 135, 210, 20 );
		
		adic( new JLabel( "C�d.moeda" ), 310, 155, 87, 20 );
		adic( txtCodMoeda, 310, 175, 87, 20 );
		adic( new JLabel( "Descri��o da moeda" ), 400, 155, 210, 20 );
		adic( txtNomeMoeda, 400, 175, 210, 20 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {

		if ( txtCodMoeda.getVlrString().trim().length() < 1 ) {
			Funcoes.mensagemInforma( this, "O campo \"C�d.moeda\" � requerido!" );
			return;
		}
		
		if ( txtDtIni.getVlrDate() != null && txtDtFim.getVlrDate() != null ) {
			if ( txtDtFim.getVlrDate().before( txtDtIni.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final inferior a inicial!" );
				return;
			}
		}

		try {

			String relatorio = "B".equals( rgModo.getVlrString() ) ? "rpevolucaovendasbar.jasper" : "rpevolucaovendaspizza.jasper";

			Date dtini = txtDtIni.getVlrDate();
			Date dtfim = txtDtFim.getVlrDate();
			
			StringBuilder from = new StringBuilder();
			StringBuilder where = new StringBuilder();
			
			if ( txtCodMoeda.getVlrString().trim().length() > 0 ) {
				from.append( ", RPMOEDA M " );
				where.append( " AND M.CODEMP=P.CODEMPMO AND M.CODFILIAL=P.CODFILIALMO AND P.CODMOEDA='" + txtCodMoeda.getVlrString() + "' AND M.CODMOEDA=P.CODMOEDA " );
			}
			if ( txtCodCli.getVlrString().trim().length() > 0 ) {
				from.append( ", RPCLIENTE C " );
				where.append( " AND C.CODEMP=P.CODEMPCL AND C.CODFILIAL=P.CODFILIALCL AND P.CODCLI=" + txtCodCli.getVlrInteger() + " AND C.CODCLI=P.CODCLI " );
			}
			if ( txtCodFor.getVlrString().trim().length() > 0 ) {
				from.append( ", RPFORNECEDOR F " );
				where.append( " AND F.CODEMP=P.CODEMPFO AND F.CODFILIAL=P.CODFILIALFO AND P.CODFOR=" + txtCodFor.getVlrInteger() + " AND F.CODFOR=P.CODFOR " );
			}
			if ( txtCodVend.getVlrString().trim().length() > 0 ) {
				from.append( ", RPVENDEDOR V " );
				where.append( " AND V.CODEMP=P.CODEMPVD AND V.CODFILIAL=P.CODFILIALVD AND P.CODVEND=" + txtCodVend.getVlrInteger().intValue() + " AND V.CODVEND=P.CODVEND "  );
			}

			StringBuilder sql = new StringBuilder();

			sql.append( "SELECT SUM(P.VLRLIQPED) AS VALOR, EXTRACT(MONTH FROM P.DATAPED) AS IMES, " );
			sql.append( "( CASE EXTRACT(MONTH FROM P.DATAPED) " );
			sql.append( "WHEN 1 THEN 'Janeiro' " );
			sql.append( "WHEN 2 THEN 'Fevereiro' " );
			sql.append( "WHEN 3 THEN 'Mar�o' " );
			sql.append( "WHEN 4 THEN 'Abril' " );
			sql.append( "WHEN 5 THEN 'Maio' " );
			sql.append( "WHEN 6 THEN 'Junho' " );
			sql.append( "WHEN 7 THEN 'Julho' " );
			sql.append( "WHEN 8 THEN 'Agosto' " );
			sql.append( "WHEN 9 THEN 'Setembro' " );
			sql.append( "WHEN 10 THEN 'Outubro' " );
			sql.append( "WHEN 11 THEN 'Novembro' " );
			sql.append( "WHEN 12 THEN 'Dezembro' END ) AS MES " );
			sql.append( "FROM RPPEDIDO P" );
			sql.append( from );
			sql.append( " WHERE P.CODEMP=? AND P.CODFILIAL=? " );
			sql.append( "AND P.DATAPED BETWEEN ? AND ?" );
			sql.append( where );
			sql.append( " GROUP BY 2 " );

			System.out.println("Query:" + sql.toString());
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPPEDIDO" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );
			ResultSet rs = ps.executeQuery();

			HashMap<String, Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "RPPEDIDO" ) );
			hParam.put( "REPORT_CONNECTION", con.getConnection() );
			hParam.put( "GRAFICO", getGrafico( rs, rgModo.getVlrString() ) );

			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/" + relatorio, "EVOLU��O DE VENDAS", null, this, hParam, con );

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

	private Image getGrafico( final ResultSet rs, final String modo ) {

		Image grafico = null;

		try {

			BufferedImage bufferedimage = null;
			JFreeChart jfreechart = null;

			if ( "P".equals( modo ) ) {
				
				DefaultPieDataset pizza = new DefaultPieDataset();

				while ( rs.next() ) {

					pizza.setValue( rs.getString( "MES" ) + "(" + Funcoes.strDecimalToStrCurrency( 12, 2, rs.getString( "VALOR" ) ) + ")", 
							rs.getDouble( "VALOR" ) );
				}

				jfreechart = ChartFactory.createPieChart3D( "", pizza, true, false, false );
			}
			else if ( "B".equals( modo ) ) {
				
				DefaultCategoryDataset barra = new DefaultCategoryDataset();

				while ( rs.next() ) {

					barra.setValue( 
							rs.getDouble( "VALOR" ), 
							rs.getString( "MES" ) + "(" + Funcoes.strDecimalToStrCurrency( 12, 2, rs.getString( "VALOR" ) ) + ")", 
							"" );
				}

				jfreechart = ChartFactory.createBarChart3D( "", "Meses", "Valores", barra, PlotOrientation.VERTICAL, true, false, false );
			}
			else if ( "L".equals( modo ) ) {

				XYSeriesCollection linha = new XYSeriesCollection();
				XYSeries serie = new XYSeries( "Evolu��o de vendas" );

				while ( rs.next() ) {

					serie.add( rs.getInt( "IMES" ), rs.getDouble( "VALOR" ) );
				}

				linha.addSeries( serie );
				jfreechart = ChartFactory.createXYLineChart( "", "Meses", "Valores", linha, PlotOrientation.VERTICAL, false, false, false );
			}

			jfreechart.setBackgroundPaint( new Color( 255, 255, 255 ) );
			
			Plot plot = jfreechart.getPlot();
			plot.setForegroundAlpha( 0.6f );

			bufferedimage = jfreechart.createBufferedImage( 770, 432 );
			grafico = bufferedimage.getScaledInstance( 770, 432, Image.SCALE_SMOOTH );

		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar grafico!\n" + e.getMessage() );
			e.printStackTrace();
		}

		return grafico;
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcCliente.setConexao( cn );
		lcFornecedor.setConexao( cn );
		lcVendedor.setConexao( cn );
		lcMoeda.setConexao( cn );
		
		prefere = RPPrefereGeral.getPrefere( cn );
		
		txtCodMoeda.setVlrString( (String) prefere.get( EPrefere.CODMOEDA.ordinal() ) );
		lcMoeda.carregaDados();
	}

}
