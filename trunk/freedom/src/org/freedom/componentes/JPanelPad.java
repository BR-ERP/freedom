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

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
public class JPanelPad extends JPanel {
//  private JPanel pnGeral = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
//  private JPanel pnFixo = new JPanel(new GridLayout(1,1));
//  private JPanel pnMaster = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
  private JLayeredPane lpn = new JLayeredPane();
  public JPanelPad () { 
	setLayout(new GridLayout(1,1));
	setBorder(javax.swing.BorderFactory.createEtchedBorder());
	add(lpn);
  }
  public JPanelPad (Dimension dm) {
    setLayout(new GridLayout(1,1));
    setPreferredSize(dm);
//    add(pnGeral);
//    pnGeral.setBorder(javax.swing.BorderFactory.createEtchedBorder());
	setBorder(javax.swing.BorderFactory.createEtchedBorder());
	lpn.setPreferredSize(dm);
	add(lpn);
  }
  public void tiraBorda() {
//    pnGeral.setBorder(BorderFactory.createEmptyBorder());
	setBorder(javax.swing.BorderFactory.createEmptyBorder());
  }
  public JPanelPad (int Larg, int Alt ) {
    this(new Dimension(Larg,Alt));
  } 
  public void adic(Component comp,int x , int y, int larg, int alt) {
    comp.setBounds(x,y,larg,alt);
//    lpn.add(comp, JLayeredPane.DEFAULT_LAYER);
//    pnGeral.add(lpn);
	lpn.add(comp, JLayeredPane.DEFAULT_LAYER);
  }
}
