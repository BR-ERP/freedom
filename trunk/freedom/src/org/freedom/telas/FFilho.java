/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FFilho.java <BR>
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
 * Classe com funcoes basicas para controle do um InternalFrame.
 */

package org.freedom.telas;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.freedom.bmps.Icone;



public class FFilho extends JInternalFrame implements InternalFrameListener {
  public String strTemp = "";

  public FFilho () { 
     /* Construtor da classe. */
     
     super("Filho01",true,true,true,true);
     setVisible(true);
     addInternalFrameListener(this);
  }
  public void setTitulo(String tit) {
  	if (getName() == null)
  	  setName(tit); 
    setTitle(tit);
  }
  public void setAtribos(int Esq, int Topo, int Larg, int Alt) { 
    setBounds(Esq,Topo,Larg,Alt); 
  }
  public void setTela(Container c) { setContentPane(c); }
  public Container getTela() { 
          Container tela = getContentPane();
          tela.setLayout(new BorderLayout());
          return tela; 
  }
  public void internalFrameActivated(InternalFrameEvent wevt) {
//    Funcoes.mensageInforma(null,"ON ACTIVATED");
  }
  public void internalFrameClosed(InternalFrameEvent wevt) {
//    Funcoes.mensagemInforma(null,"ON CLOSED");
  }
  public void internalFrameClosing(InternalFrameEvent wevt) {
//    Funcoes.mensagemInforma(null,"ON CLOSING");
/*    try {
      setClosed(false);
    }
    catch (Exception err) { }*/
  }
  public JPanel adicBotaoSair() {
    Container c = getContentPane();
    JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
    JPanel pnRod = new JPanel(new BorderLayout());
    pnRod.setPreferredSize(new Dimension(200,30));        
    btSair.setPreferredSize(new Dimension(110,30));        
    pnRod.add(btSair,BorderLayout.EAST);
    c.add(pnRod,BorderLayout.SOUTH);
    btSair.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          dispose();
        }
      }
    );
    return pnRod;
  }
  
  public void dispose() {
  	System.gc();
  	super.dispose();
  }
  /**
   *  Ajusta conex�o da tela. <BR>
   *  Adiciona a conex�o vigente a este formul�rio.
   *  Esta fun��o � geramente chamada da classe criadora da tela, <BR>
   *  esta fun��o ser� sobrescrita em cada classe filha para serem <BR>
   *  devidamente ajustada as conex�es necess�rias na tela.
   *
   *  @param cn: Conexao valida e ativa que ser� repassada e esta tela.
   */

  public void internalFrameDeactivated(InternalFrameEvent wevt) { }
  public void internalFrameDeiconified(InternalFrameEvent wevt) { }
  public void internalFrameIconified(InternalFrameEvent wevt) { }
  public void internalFrameOpened(InternalFrameEvent wevt) {
 /*   Container cpOpened = getContentPane();
    Component cOpened = null;
    if (cpOpened != null) {
       for (int i=0 ; i<cpOpened.getComponentCount() ; i++) {
         cOpened = cpOpened.getComponent(i);
         if (cOpened!=null) {
            if (cOpened.hasFocus()) {
               cOpened.nextFocus();
               cOpened.requestFocus();
               break;
            }
         }
       }
    }*/
  }
}
