/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCompICMS.java <BR>
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
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std.view.frame.tools;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.freedom.bmps.Icone;
import org.freedom.library.swing.JButtonPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.frame.FFilho;

public class FCompICMS extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

  private JPanelPad pinCab = new JPanelPad(400,100);
  private JPanelPad pinRod = new JPanelPad(400,120);
  private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JButtonPad btSair = new JButtonPad(Icone.novo("btSair.gif"));
  public FCompICMS() {
  	super(false);
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



