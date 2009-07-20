/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JMenuItemPad.java <BR>
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

public class ObjetoBD {
  private String sIDObj = "";
  private String sDescObj = "";
  private String sTipoObj = "";
  private String sComentObj = "";
  private String sUsomeObj = "";
  
  public ObjetoBD(String sIDObj,  
         String sDescObj, String sTipoObj,String sComentObj,
         String sUsomeObj ) {
  	this.sIDObj = sIDObj;
  	this.sDescObj = sDescObj;
  	this.sComentObj = sComentObj;
  	this.sUsomeObj = sUsomeObj;
  	this.sTipoObj = sTipoObj;
  	
  }

  public String getIDObj() {
    return this.sIDObj;
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
  
  public void setIDObj(String sIDObj) {
    this.sIDObj = sIDObj;
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
