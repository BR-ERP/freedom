/**
 * @version 10/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.relatorios <BR>
 * Classe: @(#)EvoluVendasLinha.java <BR>
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
 * Gr�fico de evolu��o de vendas no formato de linha variante.
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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.XYSeries;
import org.jfree.data.XYSeriesCollection;


public class EvoluVendasLinha extends LeiauteGR {
	private Connection con = null;
	private Font fnLegenda = new Font("Arial",Font.BOLD,10);
	private Font fnTopEmp = new Font("Arial",Font.BOLD,11);
	private Font fnCabEmp = new Font("Arial",Font.PLAIN,8);
	private Font fnCabEmpNeg = new Font("Arial",Font.BOLD,8);
	private XYSeriesCollection datasetLinha = new XYSeriesCollection();
	private ResultSet rs = null;
	Vector vParamOrc = new Vector();
	
	public void montaG() {
		impRaz(false);
		montaCabEmp(con);	
		montaRel();
	}
	
	private JFreeChart createXYLineChart(XYSeriesCollection dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart( 
			  "",          //T�tulo
			  "Meses",                       // Label X
			  "Valores",                     // Label Y
			  dataset,                       // Dados
			  PlotOrientation.VERTICAL,      // Orienta��o
			  false,                          // Legenda
			  false,                          // Tooltips
			  false                          // urls
		  );
		
		  chart.setBackgroundPaint(new Color(255, 255, 255));
		return chart;
	}


	private void montaRel() {
	  imprimeRodape(false);
	  Vector vLegenda = new Vector();
      try {	    
		XYSeries series = new XYSeries("Evolu��o de vendas");
	    while (rs.next()) {
  		 series.add(rs.getInt(2),rs.getDouble(1));
		 vLegenda.addElement(Funcoes.adicionaEspacos(Funcoes.strMes(rs.getInt(2)),3)+
		                     "/"+rs.getString(3)+
		                     " >"+Funcoes.strDecimalToStrCurrency(14,2,rs.getString(1))+" ) ");
		}
		datasetLinha.addSeries(series);

	
	  }
	  catch (SQLException e) {
		Funcoes.mensagemInforma(this,"Erro na consulta de valores!\n"+e.getMessage());
	  }

	  JFreeChart chart = createXYLineChart(datasetLinha);
	  
	  
	  setBordaRel();
	  
	  int iY = 35;
	  
	  drawLinha(0,iY,0,0,AL_LL);	          
      
      iY += 14;
       
	  setFonte(fnTopEmp);
	  drawTexto("EVOLU��O DE VENDAS",0,iY,getFontMetrics(fnCabEmp).stringWidth("  EVOLU��O DE VENDAS  "),AL_CEN);
	  setFonte(fnCabEmpNeg);

	  iY += 6;
	  
	  drawLinha(0,iY,0,0,AL_LL);

      iY += 30;
	  
	  drawGrafico(chart,15,iY,500,400);
	  
//	  setFonte(fnTitulo);
//	  drawTexto("LEITURA",0,522,getFontMetrics(fnTitulo).stringWidth("  LEITURA  "),AL_CEN);
//	  drawLinha(30,525,25,0,AL_CDIR);
//	  drawLinha(290,525,290,730);

	  int ix = 0;
	  iY = 525;
	  int iSalto = 0;
	  
	  setFonte(fnLegenda);
	  int iPos = 50;
	  for (int i=0;vLegenda.size()>i;i++){
	    if (iPos==50) {	     
	      drawTexto(vLegenda.elementAt(i).toString(),iPos,iY);
	      ix=140;
	      iSalto = 0;
	    } 	
	  	else if (iPos==190){
			drawTexto(vLegenda.elementAt(i).toString(),iPos,iY);
			ix=140;
			iSalto=0;	  		 
	  	}
	  	else if (iPos==330){
			drawTexto(vLegenda.elementAt(i).toString(),iPos,iY);
			ix=-280;
			iSalto=15;	  		 	  		
	  	}
	  	iY += iSalto;
	  	iPos += ix;
	  }
	  
	  drawRetangulo(45,510,60,iY-510-5,AL_CDIR);
	  
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