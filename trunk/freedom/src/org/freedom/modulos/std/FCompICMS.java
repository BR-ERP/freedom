/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCompICMS.java <BR>
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
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.freedom.componentes.Painel;
import org.freedom.telas.FFilho;
import org.freedom.bmps.Icone;
public class FCompICMS extends FFilho implements ActionListener {
  private Painel pinCab = new Painel(400,100);
  private Painel pinRod = new Painel(400,120);
  private JPanel pnRod = new JPanel(new BorderLayout());
  private JButton btSair = new JButton(Icone.novo("btSair.gif"));
  public FCompICMS() {
    setTitulo("Compara ICMS");
    setAtribos(50,50,400,400);
    
    Container c = getContentPane();
    c.setLayout(new BorderLayout());
    c.add(pinCab,BorderLayout.NORTH);
    
    btSair.setPreferredSize(new Dimension(100,30));
    pnRod.setPreferredSize(new Dimension(400,30));

    pnRod.add(pinRod,BorderLayout.NORTH);    
    pnRod.add(btSair,BorderLayout.EAST);
    c.add(pnRod,BorderLayout.SOUTH);
    
  }
  public void actionPerformed(ActionEvent evt) {
  }
}



