/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FObservacao.java <BR>
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
 * Classe para implementa��o de observa��es..<BR>
 * ATEN��O!! ESTA CLASSE � DERIVADA DE FDialogo E N�O FFDialogo. 
 */
package org.freedom.telas;
import java.awt.GridLayout;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.componentes.JTextAreaPad;
public class FObservacao extends FDialogo {
  private JPanelPad pn = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JTextAreaPad txa = new JTextAreaPad();
  private JScrollPane spn = new JScrollPane(txa);
  /**
   * 
   * Construtor sem titulo.
   * 
   * @param sPad - Texto inicial.
   */
  public FObservacao(String sPad) {
  	this(null,sPad,0);
  }
  /**
   * 
   * Construtor com titulo.
   * 
   * @param sTit - T�tulo da janela.
   * @param sPad - Texto inicial.
   */
  public FObservacao(String sTit, String sPad) {
  	this(sTit,sPad,0);
  }
  public FObservacao(String sTit, String sPad, int iTam) {
  	if (sTit != null)
  		setTitulo(sTit);
    else
        setTitulo("Observa��o");
    setAtribos(250,180);      
    pn.add(spn);
    c.add(pn);
    txa.setText(sPad);
    if (iTam > 0)
      txa.iTamanho = iTam;
  }
  /**
   * 
   * Retorna o texto digitado na dialog de observa��o.
   * @return sTexto - Texto digitado.
   * 
   */
  public String getTexto() {
  	String sTexto = txa.getText();
    return sTexto;
  }
}
