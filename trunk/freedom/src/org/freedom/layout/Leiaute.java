/**
 * @version 14/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)Leiaute.java <BR>
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
 * Coment�rios para a classe...
 */

package org.freedom.layout;
import org.freedom.componentes.ImprimeOS;

import java.sql.ResultSet;
public class Leiaute extends Object {
  public boolean bEntrada = false; 
  public Leiaute() { }
  public boolean imprimir(ResultSet rs,ResultSet rsRec,ResultSet rsInfoAdic,ImprimeOS imp) {
    return imprimir(rs,rsRec,imp);
  }
  public boolean imprimir(ResultSet rs,ResultSet rsRec,ImprimeOS imp) {
  	 return false;
  }
}
