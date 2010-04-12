/**
 * @version 24/02/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRSimilar.java <BR>
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

import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.dialog.FFDialogo;

import java.util.Vector;
public class DLRSimilar extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JRadioGroup<?, ?> rgOrdem = null;
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private Vector<String> vLabs = new Vector<String>();
  private Vector<String> vVals = new Vector<String>();
  public DLRSimilar() {
    setTitulo("Ordem do Relat�rio");
    setAtribos(300,150);
    vLabs.addElement("C�d.prod.");
    vLabs.addElement("Ref.prod.");
    vLabs.addElement("C�d.sim.");
    vLabs.addElement("Desc.prod.");
    vVals.addElement("C");
    vVals.addElement("R");
    vVals.addElement("S");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup<String, String>(2,2,vLabs,vVals);
    rgOrdem.setVlrString("D");
    adic(lbOrdem,7,0,80,15);
    adic(rgOrdem,7,20,280,60);
  }
  public String getValor() {
    String sRetorno = "";
    if (rgOrdem.getVlrString().equals("C"))
      sRetorno = "S.CODPROD";
    else if (rgOrdem.getVlrString().equals("R"))
    	sRetorno = "P.REFPROD";
    else if (rgOrdem.getVlrString().equals("S"))
    	sRetorno = "S.REFPRODSIM";
    else if (rgOrdem.getVlrString().equals("D"))
    	sRetorno = "P.DESCPROD";
    return sRetorno;
  }
}
