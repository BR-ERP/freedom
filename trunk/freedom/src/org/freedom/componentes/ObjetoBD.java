/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JMenuItemPad.java <BR>
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

public class ObjetoBD {
  private int iCodObj = 0;
  private String sNomeObj = "";
  private String sDescObj = "";
  private String sTipoObj = "";
  private String sComentObj = "";
  private String sUsomeObj = "";
  
  public ObjetoBD(int iCodObj, String sNomeObj, 
         String sDescObj, String sTipoObj,String sComentObj,
         String sUsomeObj ) {
  	this.iCodObj = iCodObj;
  	this.sNomeObj = sNomeObj;
  	this.sDescObj = sDescObj;
  	this.sComentObj = sComentObj;
  	this.sUsomeObj = sUsomeObj;
  	this.sTipoObj = sTipoObj;
  	
  }

  public int getCodObj() {
    return this.iCodObj;
  }  
  
  public String getNomeObj() {
    return this.sNomeObj;
  }
  
  public String getDescObj() {
    return this.sDescObj;
  }

  public String getTipoObj() {
    return this.sTipoObj;
  }
  
  public String setComentObj() {
    return this.sComentObj;
  }

  public String getUsomeObj() {
    return this.sUsomeObj;
  }
  
  public void setCodObj(int iCodObj) {
    this.iCodObj = iCodObj;
  }  
  
  public void setNomeObj(String sNomeObj) {
    this.sNomeObj = sNomeObj;
  }

  public void setDescObj(String sDescObj ) {
    this.sDescObj = sDescObj;
  }
  
  public void setTipoObj(String sTipoObj ) {
    this.sTipoObj = sTipoObj;
  }
  
  public void setComentObj(String sComentObj) {
    this.sComentObj = sComentObj;
  }

  public void setUsomeObj(String sUsomeObj) {
    this.sUsomeObj = sUsomeObj;
  }
  

}
