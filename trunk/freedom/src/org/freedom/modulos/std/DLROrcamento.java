/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRPedido.java <BR>
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
import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;

import java.util.Vector;
public class DLROrcamento extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JRadioGroup rgOrdem = null;
  private JRadioGroup rgModo = null;
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JLabelPad lbModo = new JLabelPad("Modo da impress�o:");
  private Vector vOLabs = new Vector();
  private Vector vOVals = new Vector();
  private Vector vMLabs = new Vector();
  private Vector vMVals = new Vector();
  public DLROrcamento(String OrdNota, String ModoNota) {
    setTitulo("Ordem do Relat�rio");
    setAtribos(370,230);
    vOLabs.addElement("C�digo");
    vOLabs.addElement("Descri��o");
    vOLabs.addElement("Marca");
    vOVals.addElement("C");
    vOVals.addElement("D");
    vOVals.addElement("M");
    rgOrdem = new JRadioGroup(1,2,vOLabs,vOVals);
    rgOrdem.setVlrString(OrdNota);
    
    vMLabs.addElement("Grafica");
    vMLabs.addElement("Texto");
    vMVals.addElement("G");
    vMVals.addElement("T");
    rgModo = new JRadioGroup(1,2,vMLabs,vMVals);
    rgModo.setVlrString(ModoNota);
    
    adic(lbModo,7,10,120,15);
    adic(rgModo,7,30,330,30);
    adic(lbOrdem,7,70,80,15);
    adic(rgOrdem,7,90,330,30);
  }
  public String getOrdem() {
    String sRetorno = "";
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sRetorno = "REFPROD";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sRetorno = "DESCPROD";
    else if (rgOrdem.getVlrString().compareTo("M") == 0 )
      sRetorno = "CODMARCA";
    return sRetorno;
  }
  public String getModo() {
    String sRetorno = "";
    if (rgModo.getVlrString().compareTo("G") == 0 )
      sRetorno = "G";
    else if (rgModo.getVlrString().compareTo("T") == 0 )
      sRetorno = "T";
    return sRetorno;
  }
}
