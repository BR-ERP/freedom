/**
 * @version 10/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.relatorios <BR>
 * Classe: @(#)EvoluVendasBarras.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Gr�fico de evolu��o de vendas no formato de barras verticais 3D.
 * 
 */

package org.freedom.graficos;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


import org.freedom.funcoes.Funcoes;
import org.freedom.layout.LeiauteGR;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.CategoryDataset;
import org.jfree.data.DefaultCategoryDataset;

public class EvoluVendasBarras extends LeiauteGR {
	private static final long serialVersionUID = 1L;
	private Connection con = null;
	private Font fnTopEmp = new Font("Arial",Font.BOLD,11);
	private Font fnCabEmp = new Font("Arial",Font.PLAIN,8);
	private Font fnCabEmpNeg = new Font("Arial",Font.BOLD,8);
	private DefaultCategoryDataset datasetBar = new DefaultCategoryDataset();
	private ResultSet rs = null;
	Vector vParamOrc = new Vector();
	
	public void montaG() {
		impRaz(false);
		montaCabEmp(con);	
		montaRel();
	}
	
	private JFreeChart createChart(CategoryDataset dataset) {    
	  JFreeChart chart = ChartFactory.createBarChart3D(	  
			"",          //T�tulo
			"Meses",                       // Label X
			"Valores",                     // Label Y
			dataset,                       // Dados
			PlotOrientation.VERTICAL,      // Orienta��o
			true,                          // Legenda
			false,                          // Tooltips
			false                          // urls
		);
		
		chart.setBackgroundPaint(new Color(255, 255, 255));
		CategoryPlot plot = chart.getCategoryPlot();		
		plot.setForegroundAlpha(0.6f);       
		
      return chart;
	}
		

	private void montaRel() {
	  imprimeRodape(false);
      try {	    
	    while (rs.next()) {
    	  datasetBar.addValue(rs.getDouble(1),Funcoes.adicionaEspacos(Funcoes.strMes(rs.getInt(2)),3)+
                           "/"+rs.getString(3)+
                          " >"+Funcoes.strDecimalToStrCurrency(14,2,rs.getString(1)+" ) ")
                           ,"");        

		}

	  }
	  catch (SQLException e) {
		Funcoes.mensagemInforma(this,"Erro na consulta de valores!\n"+e.getMessage());
	  }
  
	  JFreeChart chart = createChart(datasetBar);			  		    			  
	  
	  setBordaRel();
	  
	  int iY = 35;
	  
	  drawLinha(0,iY,0,0,AL_LL);	          
      
      iY += 14;
       
	  setFonte(fnTopEmp);
	  drawTexto("EVOLU��O DE VENDAS",0,iY,getFontMetrics(fnCabEmp).stringWidth("  EVOLU��O DE VENDAS  "),AL_CEN);
	  setFonte(fnCabEmpNeg);

	  iY += 6;
	  
	  drawLinha(0,iY,0,0,AL_LL);

      iY += 50;
	  
	  drawGrafico(chart,15,iY,500,500);
	  
	  termPagina();
	  finaliza();
	}

	public void setParam(Vector vParam) {
		vParamOrc = vParam;
	}

	public void setConsulta(ResultSet rs2) {
		rs = rs2;
	}
	public void setConexao(Connection cn) {
	  con = cn;
	}

}