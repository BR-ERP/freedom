/**
 * @version 30/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)DLRTipoAtend.java <BR>
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
 * 
 */

package org.freedom.modulos.atd;

import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;

public class DLRTipoAtend extends FFDialogo {
  private static final long serialVersionUID = 1L;
  private JRadioGroup<?, ?> rgOrdem = null;
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private Vector<String> vLabs = new Vector<String>();
  private Vector<String> vVals = new Vector<String>();
  public DLRTipoAtend() {
	setTitulo("Ordem do Relat�rio");
	setAtribos(300,140);
	vLabs.addElement("C�digo");
	vLabs.addElement("Descri��o");
	vVals.addElement("C");
	vVals.addElement("D");
	rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
	rgOrdem.setVlrString("D");
	adic(lbOrdem,7,0,80,15);
	adic(rgOrdem,7,20,280,30);
  }
  public String getValor() {
	String sRetorno = "";
	if (rgOrdem.getVlrString().compareTo("C") == 0 )
	  sRetorno = "CODTPATEND";
	else if (rgOrdem.getVlrString().compareTo("D") == 0 )
	  sRetorno = "DESCTPATEND";
	return sRetorno;
  }
}