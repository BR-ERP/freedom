/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FZoom.java <BR>
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
 * Coment�rios para a classe...
 */

package org.freedom.telas;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.PainelImagem;
public class FZoom extends JDialog implements KeyListener, ActionListener {
	private static final long serialVersionUID = 1L;

  private Image imZoom = null;
  private JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());  
  private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));  
  private JPanelPad pinCab = new JPanelPad(385,40);
  private JButton btZoom100 = new JButton(Icone.novo("btZoom100.gif"));
  private JButton btZoomIn = new JButton(Icone.novo("btZoomIn.gif"));
  private JButton btZoomPag = new JButton(Icone.novo("btZoomPag.gif"));
  private JTextFieldPad txtZoom = new JTextFieldPad(JTextFieldPad.TP_INTEGER,3,0);
  private JButton btMais = new JButton(Icone.novo("btZoomMais.gif"));
  private JButton btMenos = new JButton(Icone.novo("btZoomMenos.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private PainelImagem pimImagem = null;
  private JScrollPane spnCli = null;
  public FZoom(Image im, int iTam) {
    imZoom = im;
    setTitle("Zoom");
    setSize( 400, 400);
    setModal(true);
    setLocationRelativeTo(this);  
    Container c = getContentPane();
    c.setLayout(new BorderLayout());
    c.setBackground(Color.white);
    c.add(pnCab,BorderLayout.NORTH);
    pnCab.setPreferredSize(new Dimension(200,40));
    btSair.setPreferredSize(new Dimension(80,30));
    pnCab.add(pinCab,BorderLayout.WEST);
    pinCab.adic(btZoom100,7,5,30,30);
    pinCab.adic(btZoomIn,40,5,30,30);
    pinCab.adic(btZoomPag,73,5,30,30);
    pinCab.adic(txtZoom,106,5,50,30);
    pinCab.adic(btMais,159,5,30,30);
    pinCab.adic(btMenos,192,5,30,30);
    pinCab.adic(btSair,285,5,100,30);
    c.add(pnCli,BorderLayout.CENTER);
    pimImagem = new PainelImagem(pnCli,iTam);
    pimImagem.setVlrBytes(imZoom);
    pimImagem.setZoom(100);
    spnCli = new JScrollPane(pimImagem);
    pnCli.add(spnCli);

    txtZoom.setEnterSai(false);
    txtZoom.setVlrString(""+pimImagem.getZoom());

    btZoom100.addActionListener(this);
    btZoomIn.addActionListener(this);
    btZoomPag.addActionListener(this);
    btMais.addActionListener(this);
    btMenos.addActionListener(this);
    btSair.addActionListener(this);
    txtZoom.addKeyListener(this);

  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair)
      dispose();
    else if (evt.getSource() == btZoom100) {
      pimImagem.setZoom(100);
      pimImagem.repaint();
    }
    else if (evt.getSource() == btZoomIn) {
      int iEnc = pimImagem.getPercEncaixa();
      pimImagem.setZoom(iEnc - (iEnc / 8));
      pimImagem.repaint();
    }
    else if (evt.getSource() == btZoomPag) {
      pimImagem.setZoom(pimImagem.getPercEncaixa());
      pimImagem.repaint();
    }
    else if (evt.getSource() == btMais) {
      if (pimImagem.getZoom() < 990) {
        pimImagem.setZoom(pimImagem.getZoom()+10);
        pimImagem.repaint();
      }
    }
    else if (evt.getSource() == btMenos) {
      if (pimImagem.getZoom() > 10) {
        pimImagem.setZoom(pimImagem.getZoom()-10);
        pimImagem.repaint();
      }
    }
    txtZoom.setVlrString(""+pimImagem.getZoom());
  }
  public void keyPressed(KeyEvent kevt) {
    if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
      if (txtZoom.getText().trim().length() == 0) {
        txtZoom.setVlrString(""+pimImagem.getZoom());
      }
      else {
        pimImagem.setZoom(txtZoom.getVlrInteger().intValue());
        pimImagem.repaint();
      }
    }
  }
  public void keyTyped(KeyEvent kevt) { }
  public void keyReleased(KeyEvent kevt) { }
}
       

