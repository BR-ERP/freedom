/**
 * @version 21/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRFrete <BR>
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
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;


import org.freedom.funcoes.Funcoes;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.telas.FFDialogo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
public class DLRFrete extends FFDialogo {

	private static final long serialVersionUID = 1L;
  private JRadioGroup<?, ?> rgOrdem = null;
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private Vector<String> vLabs = new Vector<String>();
  private Vector<String> vVals = new Vector<String>();
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
    rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
    rgOrdem.setVlrString("C");
    adic(new JLabelPad("Periodo:"),7,5,120,20);
    adic(new JLabelPad("De:"),7,27,30,20);
    adic(txtDataini,37,27,90,20);
    adic(new JLabelPad("At�:"),140,27,30,20);
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
