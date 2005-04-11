/**
 * @version 01/02/2001 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)Tabela.java <BR>
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
 * Objeto para guardar as informa��es necess�rias para a cria��o e utiliza��o de modelos de etiquetas.
 */

package org.freedom.componentes;

import java.util.Vector;

public abstract class ObjetoEtiqueta {
  private Vector vLabels = new Vector();
  private Vector vLabelsAdic = new Vector();
  private Vector vValores = new Vector();
  private Vector vValoresAdic = new Vector();
  private Vector vCampos = new Vector();
  private Vector vCamposAdic = new Vector();
  private Vector vTams = new Vector();
  private Vector vTamsAdic = new Vector();
  private Vector vMascaras = new Vector();
  private Vector vMascarasAdic = new Vector();
  private String sTexto = "";

  public ObjetoEtiqueta() { 

  }
  
  public void adicOpcao(String sLabel,String sValor,String sCampo,Integer iTam,String sMascara){
      vLabels.addElement(sLabel);
      vValores.addElement(sValor);
      vCampos.addElement(sCampo);
      vTams.addElement(iTam);
      vMascaras.addElement(sMascara);
  }

/**
 * @return Returns the vLabels.
 */
public Vector getLabels() {
    return vLabels;
}
/**
 * @return Returns the vLabels.
 */
public Vector getLabelsAdic() {
    return vLabelsAdic;
}
/**
 * @return Returns the vCampos.
 */
public Vector getCampos() {
    return vCampos;
}
/**
 * @return Returns the vCamposAdic.
 */
public Vector getCamposAdic() {
    return vCamposAdic;
}
/**
 * @return Returns the vTams.
 */
public Vector getTams() {
    return vTams;
}
/**
 * @return Returns the vTamsAdic.
 */
public Vector getTamsAdic() {
    return vTamsAdic;
}

/**
 * @return Returns the vValores.
 */
public Vector getValores() {
    return vValores;
}
/**
 * @return Returns the vValoresAdic.
 */
public Vector getValoresAdic() {
    return vValoresAdic;
}
/**
 * @return Returns the vMascaras.
 */
public Vector getMascaras() {
    return vMascaras;
}
/**
 * @return Returns the vMascarasAdic.
 */
public Vector getMascarasAdic() {
    return vMascarasAdic;
}
public void setTexto(String sTexto){
    this.sTexto = sTexto;
    getAdic();    
}
public Vector getAdic(){
        for(int i2=0;vValores.size()>i2;i2++) {
            if((sTexto.indexOf(vValores.elementAt(i2).toString()))>(-1)){
                vCamposAdic.addElement(vCampos.elementAt(i2).toString());
                vTamsAdic.addElement(vTams.elementAt(i2).toString());
                vLabelsAdic.addElement(vLabels.elementAt(i2).toString());
                vMascarasAdic.addElement(vMascaras.elementAt(i2)==null?null:vMascaras.elementAt(i2).toString());
                vValoresAdic.addElement(vValores.elementAt(i2).toString());                
            }                                 
    }   
    
    return vCamposAdic;
}
}