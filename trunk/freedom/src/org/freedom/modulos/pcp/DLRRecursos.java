/**
 * @version 25/03/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)DLRRecursos.java <BR>
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
 * Tela de dialogo para rela��o de recursos de produ��o...
 */

package org.freedom.modulos.pcp;
import java.awt.Component;
import java.util.Vector;


import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.telas.FFDialogo;

public class DLRRecursos extends FFDialogo {
	private static final long serialVersionUID = 1L;
	
  private JRadioGroup<?, ?> rgOrdem = null;
  private JRadioGroup<?, ?> rgTipo = null;
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JLabelPad lbTipo = new JLabelPad("Tipo de impress�o:");
  private Vector<String> vLabsOrd = new Vector<String>();
  private Vector<String> vValsOrd = new Vector<String>();
  private Vector<String> vLabsTipo = new Vector<String>();
  private Vector<String> vValsTipo = new Vector<String>();

  public DLRRecursos(Component cOrig) {
  	super(cOrig);
    setTitulo("Ordem do Relat�rio");
    setAtribos(300,220);
    vLabsOrd.addElement("C�digo");
    vLabsOrd.addElement("Nome");
    vValsOrd.addElement("C");
    vValsOrd.addElement("N");
    rgOrdem = new JRadioGroup<String, String>(1,2,vLabsOrd,vValsOrd);
    rgOrdem.setVlrString("N");
    
    vLabsTipo.addElement("Texto");
    vLabsTipo.addElement("Gr�fica");
    vValsTipo.addElement("T");
    vValsTipo.addElement("G");
    rgTipo = new JRadioGroup<String, String>(1,2,vLabsTipo,vValsTipo);
    rgTipo.setVlrString("G");

    adic(lbOrdem,7,5,80,15);
    adic(rgOrdem,7,25,275,30);
    
    adic(lbTipo,7,65,265,15);
    adic(rgTipo,7,85,275,30);

  }
  public Vector<String> getValores() {
  	Vector<String> vRet = new Vector<String>();
	
  	if(rgOrdem.getVlrString().compareTo("C") == 0)
  		vRet.addElement("CODRECP");
  	else if (rgOrdem.getVlrString().compareTo("N") == 0 )
  		vRet.addElement("DESCRECP");
  	if(rgTipo.getVlrString().compareTo("G") == 0)
  		vRet.addElement("G");
  	else if (rgTipo.getVlrString().compareTo("T") == 0 )
  		vRet.addElement("T");
  	return vRet;
  }
}
