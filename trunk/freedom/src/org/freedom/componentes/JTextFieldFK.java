/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.compo <BR>
 * Classe: @(#)JTextFieldFK.java <BR>
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
import java.awt.Color;
import java.awt.Font;

import org.freedom.componentes.JTextFieldPad;
public class JTextFieldFK extends JTextFieldPad {
  public JTextFieldFK () {
    setBackground(Color.lightGray);
    setFont(new Font("Dialog", Font.BOLD, 12));
    setEditable(false);
    setForeground(new Color(111, 106, 177));  //RGB do Java R:159,G:152,B:207
  }
  public JTextFieldFK (int iTipo, int iTam, int iDec) {
  	setTipo(iTipo,iTam,iDec);
    setBackground(Color.lightGray);
    setFont(new Font("Dialog", Font.BOLD, 12));
    setEditable(false);
    setForeground(new Color(111, 106, 177));  //RGB do Java R:159,G:152,B:207
  }
  public boolean isFocusable() {
    return (false);
  }
}
