/**
 * @version 15/02/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FAjustaSeq.java <BR>
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
 * 
 */

package org.freedom.modulos.cfg;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FAjustaSeq extends FDados {
  private JTextFieldPad txtSgTab = new JTextFieldPad();
  private JTextFieldPad txtNroSeq = new JTextFieldPad();
  public FAjustaSeq() {
    setTitulo("Ajusta sequencia");
    setAtribos(50,50,350,150);
    
    txtSgTab.setTipo(JTextFieldPad.TP_STRING,2,0);
    txtNroSeq.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    
    adicCampo(txtSgTab, 7, 20, 50, 20, "SgTab", "Sigla", ListaCampos.DB_PK, true);
    adicCampo(txtNroSeq, 60, 20, 80, 20, "NroSeq", "Sequencia", ListaCampos.DB_SI, true);
    lcCampos.montaSql(false, "SEQUENCIA", "SG");

  }
  
}

