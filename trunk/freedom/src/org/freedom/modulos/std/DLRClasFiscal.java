/**
 * @version 07/05/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRClasFiscal.java <BR>
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
import java.awt.Component;
import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;

public class DLRClasFiscal extends FFDialogo {
  private JRadioGroup rgOrdem = null;
  private JCheckBoxPad cbListaExec = new JCheckBoxPad("Listar exe��es ? ","S","N");
  private JLabel lbOrdem = new JLabel("Ordenar por:");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  public DLRClasFiscal(Component cOrig) {
  	super(cOrig);
    setTitulo("Ordem do Relat�rio");
    setAtribos(300,160);
    vLabs.addElement("C�digo");
    vLabs.addElement("Descri��o");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");
    adic(lbOrdem,7,5,80,15);
    adic(rgOrdem,7,25,280,30);
    adic(cbListaExec,7,60,280,30);
    
    
	cbListaExec.setVlrString("N");

  }
  
  
  public String[] getValores() {
    String[] sRetorno = new String[2];
    sRetorno[0] = cbListaExec.getVlrString();
    sRetorno[1] = rgOrdem.getVlrString();
	
    return sRetorno;
  }
  
  
  
  public String getValor() {
    String sOrdem = "";
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sOrdem = "CODFISC";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sOrdem = "DESCFISC";
    return sOrdem;
  }
}
