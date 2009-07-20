/**
 * @version 10/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.relatorios <BR>
 * Classe:
 * @(#)EvoluVendasLinha.java <BR>
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
 * Gr�fico de evolu��o de vendas no formato de linha variante.
 * 
 */

package org.freedom.graficos;

import java.awt.Color;
import java.awt.Font;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.funcoes.Funcoes;
import org.freedom.layout.componentes.LeiauteGR;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class EvoluVendasLinha extends LeiauteGR {

	private static final long serialVersionUID = 1L;

	private DbConnection con = null;

	private Font fnLegenda = new Font( "Arial", Font.BOLD, 10 );

	private Font fnTopEmp = new Font( "Arial", Font.BOLD, 11 );

	private Font fnCabEmp = new Font( "Arial", Font.PLAIN, 8 );

	private Font fnCabEmpNeg = new Font( "Arial", Font.BOLD, 8 );

	private XYSeriesCollection datasetLinha = new XYSeriesCollection();

	private ResultSet rs = null;

	Vector<?> vParamOrc = new Vector<Object>();

	public void montaG() {

		impRaz( false );
		montaCabEmp( con );
		montaRel();
	}

	private JFreeChart createXYLineChart( XYSeriesCollection dataset ) {

		JFreeChart chart = ChartFactory.createXYLineChart( "", // T�tulo
				"Meses", // Label X
				"Valores", // Label Y
				dataset, // Dados
				PlotOrientation.VERTICAL, // Orienta��o
				false, // Legenda
				false, // Tooltips
				false // urls
				);

		chart.setBackgroundPaint( new Color( 255, 255, 255 ) );
		return chart;
	}

	private void montaRel() {

		imprimeRodape( false );
		Vector<String> vLegenda = new Vector<String>();
		
		try {
			XYSeries series = new XYSeries( "Evolu��o de vendas" );
			
			while ( rs.next() ) {
				
				series.add( rs.getInt( 2 ), rs.getDouble( 1 ) );
				vLegenda.addElement( Funcoes.adicionaEspacos( Funcoes.strMes( rs.getInt( 2 ) ), 3 ) 
						+ "/" 
						+ rs.getString( 3 ) 
						+ " >" 
						+ Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( 1 ) ) 
						+ " ) " );
			}
			
			datasetLinha.addSeries( series );

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemInforma( this, "Erro na consulta de valores!\n" + e.getMessage() );
		}

		JFreeChart chart = createXYLineChart( datasetLinha );

		setBordaRel();

		int iY = 35;

		drawLinha( 0, iY, 0, 0, AL_LL );

		iY += 14;

		setFonte( fnTopEmp );
		drawTexto( "EVOLU��O DE VENDAS", 0, iY, getFontMetrics( fnCabEmp ).stringWidth( "  EVOLU��O DE VENDAS  " ), AL_CEN );
		setFonte( fnCabEmpNeg );

		iY += 6;

		drawLinha( 0, iY, 0, 0, AL_LL );

		iY += 30;

		drawGrafico( chart, 15, iY, 500, 400 );

		// setFonte(fnTitulo);
		// drawTexto("LEITURA",0,522,getFontMetrics(fnTitulo).stringWidth(" LEITURA "),AL_CEN);
		// drawLinha(30,525,25,0,AL_CDIR);
		// drawLinha(290,525,290,730);

		int ix = 0;
		iY = 525;
		int iSalto = 0;

		setFonte( fnLegenda );
		int iPos = 50;
		for ( int i = 0; vLegenda.size() > i; i++ ) {
			if ( iPos == 50 ) {
				drawTexto( vLegenda.elementAt( i ).toString(), iPos, iY );
				ix = 140;
				iSalto = 0;
			}
			else if ( iPos == 190 ) {
				drawTexto( vLegenda.elementAt( i ).toString(), iPos, iY );
				ix = 140;
				iSalto = 0;
			}
			else if ( iPos == 330 ) {
				drawTexto( vLegenda.elementAt( i ).toString(), iPos, iY );
				ix = -280;
				iSalto = 15;
			}
			iY += iSalto;
			iPos += ix;
		}

		drawRetangulo( 45, 510, 60, iY - 510 - 5, AL_CDIR );

		termPagina();
		finaliza();
	}

	public void setParam( Vector<?> vParam ) {

		vParamOrc = vParam;
	}

	public void setConsulta( ResultSet rs2 ) {

		rs = rs2;
	}

	public void setConexao( DbConnection cn ) {

		con = cn;
	}

}
