/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: projetos.freedom <BR>
 * Classe: @(#)DLRConsProd.java <BR>
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
import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;




public class DLRConsProd extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JRadioGroup rgOrdem = null;

  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  
  private JLabelPad lbOrdem = new JLabelPad("Listar:");
  
  public DLRConsProd(Component cOrig) {
  	super(cOrig);
    setTitulo("Relat�rio de Produtos  ");
    setAtribos(250,240);


    vLabs.addElement("Fornecedores");
    vLabs.addElement("Compras");
    vLabs.addElement("Vendas");
    vVals.addElement("F");
    vVals.addElement("C");
    vVals.addElement("V");
    
    rgOrdem = new JRadioGroup(3,1,vLabs,vVals);
    
    
    
    adic(lbOrdem,7,15,150,15);
    adic(rgOrdem,7,40,220,100);
 
  }

  
  public String getValores() {
    String sRetorno = "";
       
    sRetorno = rgOrdem.getVlrString();
    
	return sRetorno;

  }
  
}
