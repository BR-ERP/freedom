/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JTextFieldFK.java <BR>
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
import java.awt.Font;

import org.freedom.componentes.JTextFieldPad;
public class JTextFieldFK extends JTextFieldPad { 
   private static final long serialVersionUID = 1L;
   public JTextFieldFK (int iTipo, int iTam, int iDec) { 
      super( iTipo, iTam, iDec);
	  setBackground(Color.lightGray);
      setFont(new Font("Dialog", Font.BOLD, 12));
      setEditable(false);
      setForeground(new Color(111, 106, 177));  //RGB do Java R:159,G:152,B:207
   }
   public boolean isFocusable() {
      return (false);
   }
}
