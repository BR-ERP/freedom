/**
 * @version 10/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.relatorios <BR>
 * Classe: @(#)DLExibePizza.java <BR>
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
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.freedom.telas.FFilho;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
public class DLExibePizza extends FFilho implements ActionListener {
  private JPanel pnCli = new JPanel(new GridLayout(1,1));  
  private JPanel pnCab = new JPanel(new FlowLayout(FlowLayout.CENTER));
  private JPanel pnRod = new JPanel(new FlowLayout(FlowLayout.CENTER));
  public DLExibePizza(JFreeChart ch,int larg,int alt,String sTitulo,String sVlr) {    
    setAtribos(0,0,larg,alt);
    setTitulo("Visualiza��o de gr�fico");
	ChartPanel chartPanel = new ChartPanel(ch);
    Container c = getContentPane();
    pnCli.add(chartPanel);      
	
	pnCab.setPreferredSize(new Dimension(100,20));
	pnCab.add(new JLabel(sTitulo));
	pnCab.setBackground(new Color(255,255,255));
	
	pnRod.setPreferredSize(new Dimension(100,20));
	pnRod.add(new JLabel(sVlr));
	pnRod.setBackground(new Color(255,255,255));
	
	c.add(pnCab, BorderLayout.NORTH);
    c.add(pnCli, BorderLayout.CENTER);
	c.add(pnRod, BorderLayout.SOUTH);

  }
  public void actionPerformed(ActionEvent evt) {}
}
