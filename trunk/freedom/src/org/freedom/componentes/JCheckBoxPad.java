/**
 * @version 04/06/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JCheckBoxPad.java <BR>
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;

import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
public class JCheckBoxPad extends JCheckBox implements ActionListener, KeyListener, CheckBoxListener{

  private static final long serialVersionUID = 1L;
  public static final int TP_NONE = -1;
  public static final int TP_STRING = 0;
  public static final int TP_INTEGER = 4;
  public static final int TP_BOOLEAN = 10;
  private Object oValorS = null;
  private Object oValorN = null;
  private ListaCampos lcCheck = null;
  public int Tipo = -1;
  private CheckBoxListener cbLis = this;
  public JCheckBoxPad (String lab, Object vals, Object valn){
    super(lab);
    oValorS = vals;
    oValorN = valn;
    setSelected(false);
    addActionListener(this);
    addKeyListener(this);
    setTipo();
  }
  private void setTipo() {
    if (oValorS instanceof Integer)
      Tipo = TP_INTEGER;
    else if (oValorS instanceof String)
      Tipo = TP_STRING;
    else if (oValorS instanceof Boolean)
      Tipo = TP_BOOLEAN;
  }
  public int getTipo() {
    return Tipo;
  }
  public void setListaCampos(ListaCampos lc) {
    lcCheck = lc;
  }
  public String getVlrString() {
    if (isSelected())
      return (String) oValorS;
    return (String) oValorN;
  }
  public Integer getVlrInteger() {
    if (isSelected())
      return (Integer) oValorS;
    return (Integer) oValorN;
  }
  public Boolean getVlrBoolean() {
    if (isSelected())
      return (Boolean) oValorS;
    return (Boolean) oValorN;
  }
  public void setVlrString(String val) {
    if (val.equals(oValorS))
      setSelected(true);
    else if (val.equals(oValorN))
      setSelected(false);
    fireValorAlterado();
  }
  public void setVlrInteger(Integer val) {
  	if (val == null)
  		return;
    if (val.equals(oValorS))
       setSelected(true);
    if (val.equals(oValorN))
    	setSelected(false);
    fireValorAlterado();
  }
  public void setVlrBoolean(Boolean val) {
    if (val == (Boolean) oValorS)
      setSelected(true);
    else if (val == (Boolean) oValorN)
      setSelected(false);
    fireValorAlterado();
  }
  public boolean getStatus() {
    return isSelected();
  }
  public void addCheckBoxListener(CheckBoxListener cbl) {
    cbLis = cbl;
  }
  private void fireValorAlterado() {
    cbLis.valorAlterado(new CheckBoxEvent(this));
  }
  public void actionPerformed(ActionEvent evt) {
    if (lcCheck != null) {
      lcCheck.edit();
    }
    fireValorAlterado();
  }
  public void keyPressed(KeyEvent kevt) {
    if (kevt.getKeyCode() == KeyEvent.VK_ENTER)
      transferFocus();
  }
  public void keyTyped(KeyEvent kevt) {
/*    if (kevt.getKeyCode() == kevt.VK_ENTER)
      transferFocus();*/
  }
  public void keyReleased(KeyEvent kevt) { }
  public void valorAlterado(CheckBoxEvent cbevt) { }
}
