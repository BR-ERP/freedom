/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JPanelPad.java <BR>
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
 * Coment�rios da classe.....
 */

package org.freedom.componentes;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import org.freedom.telas.SwingParams;
//import javax.swing.text.JTextComponent;
public class JPanelPad extends JPanel  {

  private static final long serialVersionUID = 1L;
  public static int TP_JPANEL = 0; // constante criada para manter a constru��o anterior de org.freedom.componentes.JPanelPad
  private JLayeredPane lpn = new JLayeredPane();
  private boolean initFirstFocus = true;
  private Component firstFocus = null;
  
  public JPanelPad () { 
	setLayout(new GridLayout(1,1));
	setBorder(javax.swing.BorderFactory.createEtchedBorder());
	add(lpn);
  }
  
  public JPanelPad (String titulo, Color cortitulo) {
	  setLayout(new GridLayout(1,1));
	  setBorder(SwingParams.getPanelLabel( titulo , cortitulo));
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
