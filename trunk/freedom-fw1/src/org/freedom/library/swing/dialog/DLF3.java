/**
 * @version 23/02/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)DLF3.java <BR>
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

package org.freedom.library.swing.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JScrollPane;

import org.freedom.library.swing.component.JTablePad;

public abstract class DLF3 extends FFDialogo implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTablePad tab = new JTablePad();
	private JScrollPane spnCentro = new JScrollPane(tab);
	public Object oRetVal = null;

	/**
	 * 
	 * Classe m�e para dialogos auxiliares. Construtor da classe...criado grid
	 * com <BR>
	 * 2 colunas "origem e c�digo aux", <BR>
	 * origem: origem da chave, ex: tabela de pre�os. <BR>
	 * c�digo aux.: c�digo auxilial para busca. <BR>
	 * 
	 * @param cOrig
	 *            - Janela m�e do dialogo.
	 */
	public DLF3() {
		// super(cOrig);
		setTitulo("Pesquisa auxiliar");
		setAtribos(550, 260);
		setResizable(true);

		c.add(spnCentro, BorderLayout.CENTER);

		addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e) {
				if (tab.getNumLinhas() > 0) {
					tab.requestFocus();
					// tab.setLinhaSel(0);
				}
				else
					btCancel.requestFocus();
			}
		});
		
		
		// Evitando que o ENTER no grid simule o duplo click
		InputMap im =  tab.getInputMap();
		ActionMap am =  tab.getActionMap();
	
		im.clear();
		am.clear();
		
		Action enterKey = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				try	{					
					acaoOk();
				}
				catch (Exception ex){
					ex.printStackTrace();
				}					
			}
		};

		
		
		
		
	}

	public abstract boolean setValor(Object oVal, String sTipo);

	public Object getValor() {
		return oRetVal;
	}

	public Object getValorGrid() {
		Object oRet = null;
		if (tab.getNumLinhas() > 0 && tab.getLinhaSel() >= 0)
			oRet = tab.getValor(tab.getLinhaSel(), 1);
		return oRet;
	}

	private void acaoOk() {
		
		if (( tab.getNumLinhas() > 0 ) && ( tab.getLinhaSel() >= 0 )) {
			
			tab.setLinhaSel( tab.getLinhaSel() - 1);
			
		}
		
		btOK.doClick();
		
	}
	
	public void keyPressed(KeyEvent kevt) {
		if (kevt.getSource() == tab && kevt.getKeyCode() == KeyEvent.VK_ENTER) {
				
				acaoOk();
				
		}
		else
			super.keyPressed(kevt);

	}

	public void keyReleased(KeyEvent kevt) {
	}

	public void keyTyped(KeyEvent kevt) {
	}

}
