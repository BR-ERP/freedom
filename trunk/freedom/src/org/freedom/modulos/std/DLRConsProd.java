/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: projetos.freedom <BR>
 * Classe: @(#)DLRConsProd.java <BR>
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
import java.awt.Component;
import java.util.Vector;
import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;




public class DLRConsProd extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JRadioGroup<?, ?> rgOrdem = null;

  private Vector<String> vLabs = new Vector<String>();
  private Vector<String> vVals = new Vector<String>();
  
  private JLabelPad lbOrdem = new JLabelPad("Listar:");
  
  public DLRConsProd(Component cOrig) {
  	super(cOrig);
    setTitulo("Relat�rio de Produtos  ");
    setAtribos(250,240);


    vLabs.addElement("Fornecedores");
    vLabs.addElement("Compras");
    vLabs.addElement("Vendas");
    vVals.addElement("F");
    vVals.addElement("C");
    vVals.addElement("V");
    
    rgOrdem = new JRadioGroup<String, String>(3,1,vLabs,vVals);
    
    
    
    adic(lbOrdem,7,15,150,15);
    adic(rgOrdem,7,40,220,100);
 
  }

  
  public String getValores() {
    String sRetorno = "";
       
    sRetorno = rgOrdem.getVlrString();
    
	return sRetorno;

  }
  
}
