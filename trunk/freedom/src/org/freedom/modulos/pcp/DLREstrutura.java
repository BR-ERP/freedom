/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRProduto.java <BR>
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

package org.freedom.modulos.pcp;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;
public class DLREstrutura extends FFDialogo {
	private static final long serialVersionUID = 1L;
	
  private JRadioGroup rgOrdem = null;
  private JRadioGroup rgModo = null;
  private JRadioGroup rgEstrut = null;
  private JLabelPad lbModo = new JLabelPad("Modo do Relat�rio:");
  private JLabelPad lbEstrut = new JLabelPad("Estrutura:");
  private JLabelPad lbOrdem = new JLabelPad("Ordenado por:");
  private Vector<String> vLabs = new Vector<String>();
  private Vector<String> vVals = new Vector<String>();
  private Vector<String> vLabsModo = new Vector<String>();
  private Vector<String> vValsModo = new Vector<String>();
  private Vector<String> vLabsEstrut = new Vector<String>();
  private Vector<String> vValsEstrut = new Vector<String>();
  
  public DLREstrutura() {	
    setTitulo("Relat�rio de Estrutura de Produtos");
    setAtribos(295,280);
    
    vLabsEstrut.addElement("Atual");
    vLabsEstrut.addElement("Todas");
    vValsEstrut.addElement("A");
    vValsEstrut.addElement("T");
    rgEstrut = new JRadioGroup<String, String>(1,2,vLabsEstrut,vValsEstrut);
    rgEstrut.setVlrString("T");
    
    vLabsModo.addElement("Resumido");
    vLabsModo.addElement("Completo");
    vValsModo.addElement("R");
    vValsModo.addElement("C");
    rgModo = new JRadioGroup<String, String>(1,2,vLabsModo,vValsModo);
    rgModo.setVlrString("C");
    
    vLabs.addElement("C�digo");
    vLabs.addElement("Descri��o");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");

    adic(lbEstrut,10,10,70,20);
    adic(rgEstrut,10,30,260,30);
    adic(lbModo,10,70,150,20);
    adic(rgModo,10,90,260,30);
    adic(lbOrdem,10,130,150,20);
    adic(rgOrdem,10,150,260,30);
    
  }
  public String[] getValores() {
    String[] sRetorno = new String[3];
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sRetorno[0] = "E.CODPROD";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
    sRetorno[0] = "PD.DESCPROD";
    sRetorno[1] = rgModo.getVlrString();
    sRetorno[2] = rgEstrut.getVlrString();
    
    return sRetorno;
  }
}
