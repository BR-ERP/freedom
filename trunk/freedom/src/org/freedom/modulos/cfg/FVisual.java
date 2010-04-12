/**
 * @version 12/07/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FFluxo.java <BR>
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
package org.freedom.modulos.cfg;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Vector;

import org.freedom.library.JComboBoxPad;
import org.freedom.library.JLabelPad;
import org.freedom.library.JPanelPad;
import org.freedom.telas.FFilho;

public class FVisual extends FFilho {
	private static final long serialVersionUID = 1L;
	private JComboBoxPad cbLookAndFeel = null;
	private Vector<String> vDescLookAndFeel = new Vector<String>();
	private Vector<?> vValLookAndFeel = new Vector<Object>();
	private JLabelPad lbLookAndFeel = new JLabelPad("Selecione o visual desejado");
	private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	
//	public FVisual(boolean comScroll) {
	public FVisual() {
		super(true);
		setTitulo("Configura��o de Visual");
		setAtribos(10,10,300,200);
		
		Container c = getContentPane();

		c.setLayout(new BorderLayout());
		
		c.add(pnCli, BorderLayout.CENTER);
  	    
		cbLookAndFeel = new JComboBoxPad(vDescLookAndFeel, vValLookAndFeel, JComboBoxPad.TP_STRING, 100, 0);
		pnCli.adic(lbLookAndFeel,30,30,100,20);
		pnCli.adic(cbLookAndFeel,60,60,100,20);
		
		
	}


}
