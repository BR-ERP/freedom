/**
 * @version 12/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.acao <BR>
 * Classe: @(#)EditEvent.java <BR>
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

package org.freedom.acao;
import org.freedom.componentes.ListaCampos;
public class EditEvent {
  private ListaCampos lcCampos = null;
  private Object oSrc = null;
  public boolean ok = false;
  public EditEvent(ListaCampos lc) {
    lcCampos = lc;
    oSrc = lc;
  }
  public EditEvent(Object sr) {
  	oSrc = sr;
  }
  public ListaCampos getListaCampos() {
    return lcCampos;
  }
  public int getEstado() {
    return lcCampos.getStatus();
  }
  public void cancela() {
    lcCampos.cancelEdit();
  }
  public Object getSource() {
    return oSrc;
  }
}
