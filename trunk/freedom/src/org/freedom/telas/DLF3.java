/**
 * @version 23/02/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)DLF3.java <BR>
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

package org.freedom.telas;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.Tabela;

public abstract class DLF3 extends FFDialogo implements KeyListener {
  private JPanelPad pinCab = new JPanelPad(0,60);
  public JTextFieldPad txtCod = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  public JTextFieldFK txtDesc = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  public Tabela tab = new Tabela();
  public JButton btSalvar = new JButton(Icone.novo("btSalvar.gif"));
  public boolean bPrimeira = false;
  private JScrollPane spnCentro = new JScrollPane(tab);
  /**
   * 
   *  Classe m�e para dialogos auxiliares.
   *  Construtor da classe...criado grid com <BR>
   *  2 colunas "origem e c�digo aux", <BR>
   *  origem: origem da chave, ex: tabela de pre�os. <BR>
   *  c�digo aux.: c�digo auxilial para busca. <BR>
   * 
   * @param cOrig - Janela m�e do dialogo.
   */
  public DLF3(Component cOrig) {
  	super(cOrig);
    setTitulo("Pesquisa auxiliar");
    setAtribos( 550, 260);
    setResizable(true);
    
    txtCod.setAtivo(false);
    btSalvar.setEnabled(false);

    setPainel(pinCab);
    adic(new JLabelPad("C�digo e descri��o da chave"),7,0,250,20);
    adic(txtCod,7,20,80,20);
    adic(txtDesc,90,20,300,20);
    adic(btSalvar,395,10,30,30);
    
    c.add( pinCab,BorderLayout.NORTH);
    c.add( spnCentro, BorderLayout.CENTER);
    
    tab.adicColuna(" Orig. ");
    tab.adicColuna("C�digo sim.");
    
    tab.setTamColuna(30,0);
    tab.setTamColuna(100,1);
    
    btSalvar.addActionListener(this);
    tab.addKeyListener(this);
    
    addInternalFrameListener(
       new InternalFrameAdapter() {
		 public void internalFrameActivated(InternalFrameEvent e) {
            if (tab.getNumLinhas() > 0) {
              tab.requestFocus();
              tab.setLinhaSel(0); 
            }
            else
              btCancel.requestFocus();
		 }
       }
    );
  }
  public abstract void setValor(Object oVal);
  public Object getValor() {
  	 if (txtCod.getVlrString().equals(""))
  	 	return null;
    return txtCod.getVlrString();  	 	
  }
  public Object getValorGrid() {
  	Object oRet = null;
  	if (tab.getNumLinhas() > 0 && tab.getLinhaSel() >= 0)
  		oRet = tab.getValor(tab.getLinhaSel(),1);
  	return oRet;  	 	
  }
  public void keyPressed(KeyEvent kevt) {
    if ( kevt.getSource() == tab && kevt.getKeyCode() == KeyEvent.VK_ENTER) {       
      if (tab.getNumLinhas() > 0) {
      	
//Esquematicos para acertar a linha selecionada...
//Quando o form fechar a linha ira pular uma vez uma vez para baixo...
//ent�o eu volto uma linha aqui:
      	
      	if (tab.getLinhaSel() > 0)
        	tab.setLinhaSel(tab.getLinhaSel()-1);
        else
        	tab.setLinhaSel(tab.getNumLinhas()-1);
      	
        btOK.doClick();
      }
    }
    else
      super.keyPressed(kevt);
  }
  public void keyReleased(KeyEvent kevt) { }
  public void keyTyped(KeyEvent kevt) { }
}

