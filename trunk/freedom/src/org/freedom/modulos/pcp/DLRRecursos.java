/**
 * @version 25/03/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)DLRRecursos.java <BR>
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
 * Tela de dialogo para rela��o de recursos de produ��o...
 */

package org.freedom.modulos.pcp;
import java.awt.Component;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;

public class DLRRecursos extends FFDialogo {
	private static final long serialVersionUID = 1L;
	
  private JRadioGroup rgOrdem = null;
  private JRadioGroup rgTipo = null;
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JLabelPad lbTipo = new JLabelPad("Tipo de impress�o:");
  private Vector<String> vLabsOrd = new Vector<String>();
  private Vector<String> vValsOrd = new Vector<String>();
  private Vector<String> vLabsTipo = new Vector<String>();
  private Vector<String> vValsTipo = new Vector<String>();

  public DLRRecursos(Component cOrig) {
  	super(cOrig);
    setTitulo("Ordem do Relat�rio");
    setAtribos(300,220);
    vLabsOrd.addElement("C�digo");
    vLabsOrd.addElement("Nome");
    vValsOrd.addElement("C");
    vValsOrd.addElement("N");
    rgOrdem = new JRadioGroup(1,2,vLabsOrd,vValsOrd);
    rgOrdem.setVlrString("N");
    
    vLabsTipo.addElement("Texto");
    vLabsTipo.addElement("Gr�fica");
    vValsTipo.addElement("T");
    vValsTipo.addElement("G");
    rgTipo = new JRadioGroup(1,2,vLabsTipo,vValsTipo);
    rgTipo.setVlrString("G");

    adic(lbOrdem,7,5,80,15);
    adic(rgOrdem,7,25,275,30);
    
    adic(lbTipo,7,65,265,15);
    adic(rgTipo,7,85,275,30);

  }
  public Vector<String> getValores() {
  	Vector<String> vRet = new Vector<String>();
	
  	if(rgOrdem.getVlrString().compareTo("C") == 0)
  		vRet.addElement("CODRECP");
  	else if (rgOrdem.getVlrString().compareTo("N") == 0 )
  		vRet.addElement("DESCRECP");
  	if(rgTipo.getVlrString().compareTo("G") == 0)
  		vRet.addElement("G");
  	else if (rgTipo.getVlrString().compareTo("T") == 0 )
  		vRet.addElement("T");
  	return vRet;
  }
}
