/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JTextFieldPad.java <BR>
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
import javax.swing.JTextArea;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
public class JTextAreaPad extends JTextArea implements KeyListener { 
  private ListaCampos lcTxa = null;
  public int iTamanho = 10000;
  public int iDecimal = 0;
  public int iTipo = JTextFieldPad.TP_STRING;
  /**
   *  Construtor da classe (sem tamanho). <BR>
   *  Coloca o tamanho padr�o: 10000.
   *
   */
  public JTextAreaPad() {
  	this(0);
  }
  /**
   *  Construtor da classe (com tamanho). <BR>
   *  N�mero de caracteres que o txa pode aceitar.
   *
   */
  public JTextAreaPad(int iTam) {
  	if (iTam > 0)
  	  iTamanho = iTam;
  	addKeyListener(this);
  } 
  public String getVlrString() {
    return getText();
  }
  public void setListaCampos(ListaCampos lc) {
    lcTxa = lc;
  }     
  public void setVlrString(String val) {
    setText(val);
    setCaretPosition(0);
  }
  public void keyTyped(KeyEvent kevt) {  
    if ((kevt.getKeyChar() != KeyEvent.CHAR_UNDEFINED) && 
       (kevt.getKeyChar() != (char) 8) &&
       (kevt.getKeyChar() != (char) 10) && 
       (kevt.getKeyChar() != (char) 9)) {
       if (getText().length() > iTamanho)
       	  kevt.setKeyChar((char)0);
       else if (lcTxa != null)   	
          lcTxa.edit();     
    }
  }
  public void keyPressed(KeyEvent kevt) { }
  public void keyReleased(KeyEvent kevt) { }
}
