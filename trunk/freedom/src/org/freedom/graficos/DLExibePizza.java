/**
 * @version 10/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.relatorios <BR>
 * Classe: @(#)DLExibePizza.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Tela de exibi��o de graficos, permite a utiliza��o dos gr�ficos pizza 3D rotat�rios.
 * 
 */

package org.freedom.graficos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;

import org.freedom.telas.FFilho;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
public class DLExibePizza extends FFilho implements ActionListener {
  private static final long serialVersionUID = 1L;
  private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));  
  private JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL,new FlowLayout(FlowLayout.CENTER));
  private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new FlowLayout(FlowLayout.CENTER));
  public DLExibePizza(JFreeChart ch,int larg,int alt,String sTitulo,String sVlr) {    
  	super(false);
    setAtribos(0,0,larg,alt);
    setTitulo("Visualiza��o de gr�fico");
	ChartPanel chartPanel = new ChartPanel(ch);
    Container c = getContentPane();
    pnCli.add(chartPanel);      
	
	pnCab.setPreferredSize(new Dimension(100,20));
	pnCab.add(new JLabelPad(sTitulo));
	pnCab.setBackground(new Color(255,255,255));
	
	pnRod.setPreferredSize(new Dimension(100,20));
	pnRod.add(new JLabelPad(sVlr));
	pnRod.setBackground(new Color(255,255,255));
	
	c.add(pnCab, BorderLayout.NORTH);
    c.add(pnCli, BorderLayout.CENTER);
	c.add(pnRod, BorderLayout.SOUTH);

  }
  public void actionPerformed(ActionEvent evt) {}
}
