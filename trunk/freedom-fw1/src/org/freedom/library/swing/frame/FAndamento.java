/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FAndamento.java <BR>
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
 * Coment�rios da classe.....
 */

package org.freedom.library.swing.frame;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;

public class FAndamento extends JFrame {
	private static final long serialVersionUID = 1L;	
	private JPanelPad pin = new JPanelPad(310,150);
	private JProgressBar pb = new JProgressBar();
	private JLabelPad lbAnd = new JLabelPad(""); 
	
	public FAndamento(String sLabel,int iMin, int iMax) {
		setBounds(100,100,310,150);
		pb.setStringPainted(true);
		pb.setMaximum(iMax);
		pb.setMinimum(iMin);
		setTitle("Andamento");
		getContentPane().setLayout(new GridLayout(1,1));
		lbAnd.setText(sLabel);
		pin.adic(lbAnd,7,20,200,20);
		pin.adic(pb,7,60,280,20);
		getContentPane().add(pin);
	}
	
	public synchronized void atualiza(int iVal) {
		pb.setValue(iVal);
		pb.updateUI();
	}
	
	public void setLabel(String sLabel) {
		lbAnd.setText(sLabel);
		lbAnd.updateUI();
	}
}
