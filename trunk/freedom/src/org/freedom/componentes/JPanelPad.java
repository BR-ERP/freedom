/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JPanelPad.java <BR>
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
 * Coment�rios da classe.....
 */

package org.freedom.componentes;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

//import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
//import javax.swing.text.JTextComponent;
public class JPanelPad extends JPanel  {
  public static int TP_JPANEL = 0; // constante criada para manter a constru��o anterior de org.freedom.componentes.JPanelPad
  private JLayeredPane lpn = new JLayeredPane();
  private boolean initFirstFocus = true;
  private Component firstFocus = null;
  public JPanelPad () { 
	setLayout(new GridLayout(1,1));
	setBorder(javax.swing.BorderFactory.createEtchedBorder());
	add(lpn);
  }
  public JPanelPad (Dimension dm) {
    setLayout(new GridLayout(1,1));
    setPreferredSize(dm);
	setBorder(javax.swing.BorderFactory.createEtchedBorder());
	lpn.setPreferredSize(dm);
	add(lpn);
  }
  public void tiraBorda() {
	setBorder(javax.swing.BorderFactory.createEmptyBorder());
  }
  public JPanelPad (int Larg, int Alt ) {
    this(new Dimension(Larg,Alt));
  } 
  public void adic(Component comp,int x , int y, int larg, int alt) {
    comp.setBounds(x,y,larg,alt);
	lpn.add(comp, JLayeredPane.DEFAULT_LAYER);
  }
  
  /** 
   * Abaixo constru��es referentes ao org.freedom.componentes.JPanelPad
   **/ 
  
  public JPanelPad(int tppanel) {
	super();
  }
  public JPanelPad(LayoutManager arg0) {
	super(arg0);
  }
  public JPanelPad(int tppanel, boolean arg0) {
	super(arg0);
  }
  public JPanelPad(int tppanel, LayoutManager arg0) {
	super(arg0);
  }
  public JPanelPad(int tppanel, LayoutManager arg0, boolean arg1) {
	super(arg0, arg1);
  }

  public void setInitFirstFocus(boolean initFirstFocus) {
	this.initFirstFocus = initFirstFocus;
  }
	
  public boolean getInitFirstFocus() {
	return this.initFirstFocus;
  }
  
  public boolean firstFocus() {
  	boolean ret = false;
  	if (firstFocus!=null) {
  		if (firstFocus.isFocusable()) {
  			firstFocus.requestFocus();
  			ret = true;
  			//System.out.println("Pegou foco");
  		}
  	}
  	return ret;
  }
  
  public void setFirstFocus(Component firstFocus) {
  	this.firstFocus = firstFocus;
  }
  
  public Component getFirstFocus() {
  	return this.firstFocus;
  }
}
