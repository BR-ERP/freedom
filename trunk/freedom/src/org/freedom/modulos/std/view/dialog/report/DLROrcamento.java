/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRPedido.java <BR>
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

import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.dialog.FFDialogo;

import java.util.Vector;
public class DLROrcamento extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JRadioGroup<?, ?> rgOrdem = null;
  private JRadioGroup<?, ?> rgModo = null;
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JLabelPad lbModo = new JLabelPad("Modo da impress�o:");
  private Vector<String> vOLabs = new Vector<String>();
  private Vector<String> vOVals = new Vector<String>();
  private Vector<String> vMLabs = new Vector<String>();
  private Vector<String> vMVals = new Vector<String>();
  public DLROrcamento(String OrdNota, String ModoNota) {
    setTitulo("Ordem do Relat�rio");
    setAtribos(370,230);
    vOLabs.addElement("C�digo");
    vOLabs.addElement("Descri��o");
    vOLabs.addElement("Marca");
    vOVals.addElement("C");
    vOVals.addElement("D");
    vOVals.addElement("M");
    rgOrdem = new JRadioGroup<String, String>(1,2,vOLabs,vOVals);
    rgOrdem.setVlrString(OrdNota);
    
    vMLabs.addElement("Grafica");
    vMLabs.addElement("Texto");
    vMVals.addElement("G");
    vMVals.addElement("T");
    rgModo = new JRadioGroup<String, String>(1,2,vMLabs,vMVals);
    rgModo.setVlrString(ModoNota);
    
    rgModo.setVlrString( "G" );
    
    adic(lbModo,7,10,120,15);
    adic(rgModo,7,30,330,30);
    adic(lbOrdem,7,70,80,15);
    adic(rgOrdem,7,90,330,30);
  }
  public String getOrdem() {
    String sRetorno = "";
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sRetorno = "REFPROD";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sRetorno = "DESCPROD";
    else if (rgOrdem.getVlrString().compareTo("M") == 0 )
      sRetorno = "CODMARCA";
    return sRetorno;
  }
  public String getModo() {
    String sRetorno = "";
    if (rgModo.getVlrString().compareTo("G") == 0 )
      sRetorno = "G";
    else if (rgModo.getVlrString().compareTo("T") == 0 )
      sRetorno = "T";
    return sRetorno;
  }
}
