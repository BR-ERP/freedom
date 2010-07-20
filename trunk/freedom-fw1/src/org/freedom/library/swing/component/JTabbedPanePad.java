/**
 * @version 22/03/2005 <BR>
 * @author Setpoint Inform�tica Ltda / Robson Sanchez.
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.componentes <BR>
 * Classe:
 * @(#)JTabbedPanePad.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * ? <BR>
 * 
 */

package org.freedom.library.swing.component;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTabbedPane;

public class JTabbedPanePad extends JTabbedPane implements FocusListener {

	private static final long serialVersionUID = 1L;
	private boolean initFirstFocus = true;
	private boolean controlFocus = true;

	// Flag que indica para o component procurar o primeiro campo para foco.
	/**
	 * 
	 */
	public JTabbedPanePad() {
		super();
		setChangedListener();
	}

	/**
	 * @param arg0
	 */
	public JTabbedPanePad(int arg0) {
		super(arg0);
		setChangedListener();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public JTabbedPanePad(int arg0, int arg1) {
		super(arg0, arg1);
		setChangedListener();
	}

	private void setChangedListener() {
		addFocusListener(this);
	}

	public void focusGained(FocusEvent arg0) {
		// System.out.println("Ganhou foco no Tabbed");
		firstFocus(getSelectedComponent());
		controlFocus = false;
	}

	public void focusLost(FocusEvent arg0) {
		// System.out.println("Perdeu foco no Tabbed");
		// initFirstFocus = true;

	}

	public void setInitFirstFocus(boolean initFirstFocus) {
		this.initFirstFocus = initFirstFocus;
	}

	public boolean getInitFirstFocus() {
		return this.initFirstFocus;
	}

	public void firstFocus(Component c) {
		if (( initFirstFocus ) && ( c != null ) && ( controlFocus )) {
			if (c instanceof JPanelPad) {
				// System.out.println("Entrou no first focus do tabbed");
				if (!( ( JPanelPad ) c ).firstFocus())
					transferFocus();
			}
		}
	}

}
