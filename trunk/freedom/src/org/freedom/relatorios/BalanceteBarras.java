/**
 * @version 10/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.relatorios <BR>
 * Classe: @(#)BalanceteBarras.java <BR>
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
 * Gr�fico de balancete financeiro no formato de barras verticais 3D.
 * 
 */


package org.freedom.relatorios;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.layout.LeiauteGR;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.CategoryDataset;
import org.jfree.data.DefaultCategoryDataset;

import org.freedom.funcoes.Funcoes;

public class BalanceteBarras extends LeiauteGR {
	private Connection con = null;
	private Font fnTopEmp = new Font("Arial",Font.BOLD,11);
	private Font fnCabEmp = new Font("Arial",Font.PLAIN,8);
	private Font fnCabEmpNeg = new Font("Arial",Font.BOLD,8);
	private DefaultCategoryDataset datasetBar = new DefaultCategoryDataset();
	private ResultSet rs = null;
	private String sTitulo1 = "";
	private String sTitulo2 = "";
	Vector vParamOrc = new Vector();
	
	public void montaG() {
		impRaz(false);
		montaCabEmp(con);	
		montaRel();
	}
	
	private JFreeChart createChart(CategoryDataset dataset) {    
	  JFreeChart chart = ChartFactory.createBarChart3D(	  
			"",          //T�tulo
			"Planejamento",                       // Label X
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
	  Vector vData = new Vector();
	  double dVlrOutros = 0.0;      
	  double dVlrTotal = 0.0;
	  double dValor = 0.0;
	  double dValorPerc = 0.0;
	  String sLabel = "";
	  try {	    
		while (rs.next()) {
			Vector vLinha = new Vector();
			vLinha.addElement(rs.getString(2).trim());
			vLinha.addElement(new Double (rs.getDouble(4))); 
			vData.addElement(vLinha);
			dVlrTotal += rs.getDouble(4);		    
		}
	  } 
	  catch (SQLException e) {
		Funcoes.mensagemInforma(this,"Erro na consulta de valores!\n"+e.getMessage());
	  }
    
	  for (int i2=0;vData.size()>i2;i2++){
		dValor = ((Double) ((Vector) vData.elementAt(i2)).elementAt(1)).doubleValue();	
		dValorPerc = (dValor*100)/dVlrTotal;	
		if (dValorPerc<3.0) {
		  dVlrOutros += dValor;		  	
		}
		else {
		  sLabel = ((String) ((Vector) vData.elementAt(i2)).elementAt(0));
		  sLabel = sLabel + " ("+		
				   Funcoes.strDecimalToStrCurrency(14,2,dValor+"")+" ) ";
		  datasetBar.addValue(dValor,sLabel,"");
	  	
		} 			
	  }
	  if (dVlrOutros>0.0) 
		datasetBar.addValue(dVlrOutros,"Outros valores","");

	  JFreeChart chart = createChart(datasetBar);	  		    			  
	  
	  setBordaRel();
	  
	  int iY = 35;
	  
	  drawLinha(0,iY,0,0,AL_LL);	          
      
	  iY += 14;
       
	  setFonte(fnTopEmp);
	  drawTexto(sTitulo1,0,iY,getFontMetrics(fnCabEmp).stringWidth("  "+sTitulo1+"  "),AL_CEN);
	  setFonte(fnCabEmpNeg);

	  iY += 6;
	  
	  drawLinha(0,iY,0,0,AL_LL);

	  iY +=14;
	  
	  setFonte(fnTopEmp);
	  drawTexto(sTitulo2,0,iY,getFontMetrics(fnCabEmp).stringWidth("  "+sTitulo2+"  "),AL_CEN);
	  setFonte(fnCabEmpNeg);

	  iY += 6;

	  drawLinha(0,iY,0,0,AL_LL);

	  iY += 50;
	  
	  drawGrafico(chart,15,iY,500,550);

	  iY += 12;
       
	  setFonte(fnTopEmp);
	  drawTexto("Valor total:"+Funcoes.strDecimalToStrCurrency(14,2,dVlrTotal+""),0,iY,getFontMetrics(fnCabEmp).stringWidth("  Valor total:"+Funcoes.strDecimalToStrCurrency(14,2,dVlrTotal+"")+"  "),AL_CEN);
	  
	  termPagina();
	  finaliza();

	}

	public void setParam(Vector vParam) {
		vParamOrc = vParam;
	}

	public void setConsulta(ResultSet rs2) {
		rs = rs2;
	}
	public void setTitulo(String sTit1,String sTit2) {
		sTitulo1 = sTit1;
		sTitulo2 = sTit2;
	}

	public void setConexao(Connection cn) {
	  con = cn;
	}

}