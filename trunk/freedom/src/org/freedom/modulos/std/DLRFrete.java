/**
 * @version 21/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRFrete <BR>
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

import javax.swing.JLabel;

import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
public class DLRFrete extends FFDialogo {
  private JRadioGroup rgOrdem = null;
  private JLabel lbOrdem = new JLabel("Ordenar por:");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  public DLRFrete() {
    setTitulo("Ordem do Relat�rio");
    setAtribos(280,200);
    
    
    GregorianCalendar cPeriodo = new GregorianCalendar();
    txtDatafim.setVlrDate(cPeriodo.getTime());
	cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
	txtDataini.setVlrDate(cPeriodo.getTime());
	
    vLabs.addElement("Doc.Venda.");
    vLabs.addElement("Transp.");
    vVals.addElement("D");
    vVals.addElement("T");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("C");
    adic(new JLabel("Periodo:"),7,5,120,20);
    adic(new JLabel("De:"),7,27,30,20);
    adic(txtDataini,37,27,90,20);
    adic(new JLabel("At�:"),140,27,30,20);
    adic(txtDatafim,175,27,90,20);
    
    
    adic(lbOrdem,7,60,80,15);
    adic(rgOrdem,7,80,255,28);
  }
  public String [] getValores() {
  	String[] sRetorno = new String[11];
    if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sRetorno[0] = "VD.DOCVENDA";
    else if (rgOrdem.getVlrString().compareTo("T") == 0 )
      sRetorno[0] = "T.NOMETRAN";
    sRetorno[1] = txtDataini.getVlrString();
    sRetorno[2] = txtDatafim.getVlrString();
    
    if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
		
    }
    return sRetorno;
    
       
    
  }
}
