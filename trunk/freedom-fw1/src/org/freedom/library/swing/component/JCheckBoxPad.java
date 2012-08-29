/*
 * Projeto: Freedom
 * Pacote: org.freedom.componentes
 * Classe: @(#)JCheckBoxPad.java
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 */

package org.freedom.library.swing.component;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.library.persistence.ListaCampos;

/**
 * Customisa JCheckBox.
 * 
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva
 * @version 28/08/2009 - Alex Rodrigues
 */

public class JCheckBoxPad extends JCheckBox implements ActionListener, KeyListener, CheckBoxListener {

	private static final long serialVersionUID = 1L;

	public static final int TP_NONE = -1;

	public static final int TP_STRING = 0;

	public static final int TP_INTEGER = 4;

	public static final int TP_BOOLEAN = 10;
	
	private Object oValorS = null;

	private Object oValorN = null;
	
	private Object oValorNeutro = null;
	
	private Object oValorSel = null;

	private ListaCampos lcCheck = null;
	
	private boolean firstSelect = false;

	public int Tipo = -1;

	private CheckBoxListener cbLis = this;

	public JCheckBoxPad(String lab, Object vals, Object valn) {
		this(lab, vals, valn, false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));

	}

	public JCheckBoxPad(String lab, Object vals, Object valn, boolean selected) {

		super(lab);

		oValorS = vals;
		oValorN = valn;
		oValorNeutro = valn;
		if (selected) {
			oValorSel = oValorS;
		} else {
			oValorSel = oValorN;
		}
		setSelected(selected);

		addActionListener(this);
		addKeyListener(this);

		setTipo();
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private void setTipo() {
		if (oValorS instanceof Integer) {
			Tipo = TP_INTEGER;
		}
		else if (oValorS instanceof String) {
			Tipo = TP_STRING;
		}
		else if (oValorS instanceof Boolean) {
			Tipo = TP_BOOLEAN;
		}
	}

	public int getTipo() {
		return Tipo;
	}

	public void setListaCampos(ListaCampos lc) {
		lcCheck = lc;
	}

	public String getVlrString() {
		if (oValorSel==null) {
			oValorSel = oValorN;
		}
		return isSelected() ? ( String ) oValorS : (String) oValorSel;
	}

	public Integer getVlrInteger() {
		if (oValorSel==null) {
			oValorSel = oValorN;
		}
		return isSelected() ? ( Integer ) oValorS : ( Integer ) oValorSel;
	}

	public Boolean getVlrBoolean() {
		if (oValorSel==null) {
			oValorSel = oValorN;
		}
		return isSelected() ? ( Boolean ) oValorS : ( Boolean ) oValorSel;
	}

	public void setVlrString(String val) {
		if (val.equals(oValorS)) {
			setSelected(true);
			setoValorSel(oValorS);
		} else if (val.equals(oValorNeutro)) {
			setSelected(false);
			setoValorSel(oValorNeutro);
			firstSelect = true;
		} else {
			setSelected(false);
			setoValorSel(oValorN);
		}
		fireValorAlterado();
	}

	public void setVlrInteger(Integer val) {
		if (val == null) {
			return;
		}
		if (val.equals(oValorS)) {
			setSelected(true);
			setoValorSel(oValorS);
		} else if (val.equals(oValorNeutro)) {
			setSelected(false);
			setoValorSel(oValorNeutro);
			firstSelect=true;
		} else {
			setSelected(false);
			setoValorSel(oValorN);
		}
		fireValorAlterado();
	}

	public void setVlrBoolean(Boolean val) {
		if (val == ( Boolean ) oValorS) {
			setSelected(true);
			setoValorSel(Boolean.TRUE);
		}
		else {
			setSelected(false);
			setoValorSel(Boolean.FALSE);
		}
		fireValorAlterado();
	}

	public boolean getStatus() {
		return isSelected();
	}

	public void addCheckBoxListener(CheckBoxListener cbl) {
		cbLis = cbl;
	}

	private void fireValorAlterado() {
		cbLis.valorAlterado(new CheckBoxEvent(this));
	}

	public void actionPerformed(ActionEvent evt) {
		if (lcCheck != null) {
			lcCheck.edit();
		}
		fireValorAlterado();
	}

	public void keyPressed(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
			transferFocus();
		}
	}

	public void keyTyped(KeyEvent kevt) {
	}

	public void keyReleased(KeyEvent kevt) {
	}

	public void valorAlterado(CheckBoxEvent cbevt) {
		if (cbevt.getCheckBox().isSelected()) {
			setoValorSel(oValorS);
			
		} else { // Tratamento para evitar problemas com valor neutro. 
			if ( firstSelect ) {
				firstSelect = false;
			} else { 
				setoValorSel(oValorN);
			}
		}
	}
	
	public Object getoValorNeutro() {
		return oValorNeutro;
	}

	public void setoValorNeutro(Object oValorNeutro) {
		this.oValorNeutro = oValorNeutro;
	}

	public Object getoValorSel() {
		return oValorSel;
	}

	public void setoValorSel(Object oValorSel) {
		this.oValorSel = oValorSel;
	}
}
