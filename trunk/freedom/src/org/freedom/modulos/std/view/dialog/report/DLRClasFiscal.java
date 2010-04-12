/**
 * @version 07/05/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRClasFiscal.java <BR>
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

package org.freedom.modulos.std.view.dialog.report;
import java.awt.Component;
import java.util.Vector;


import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLRClasFiscal extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JRadioGroup<?, ?> rgOrdem = null;
  private JCheckBoxPad cbListaExec = new JCheckBoxPad("Listar exe��es ? ","S","N");
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private Vector<String> vLabs = new Vector<String>();
  private Vector<String> vVals = new Vector<String>();
  public DLRClasFiscal(Component cOrig) {
  	super(cOrig);
    setTitulo("Ordem do Relat�rio");
    setAtribos(300,180);
    vLabs.addElement("C�digo");
    vLabs.addElement("Descri��o");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");
    adic(lbOrdem,7,5,80,15);
    adic(rgOrdem,7,25,280,30);
    adic(cbListaExec,7,60,280,30);
    
    
	cbListaExec.setVlrString("N");

  }
  
  
  public String[] getValores() {
    String[] sRetorno = new String[2];
    sRetorno[0] = cbListaExec.getVlrString();
    sRetorno[1] = rgOrdem.getVlrString();
	
    return sRetorno;
  }
  
  
  
  public String getValor() {
    String sOrdem = "";
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sOrdem = "CODFISC";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sOrdem = "DESCFISC";
    return sOrdem;
  }
}
