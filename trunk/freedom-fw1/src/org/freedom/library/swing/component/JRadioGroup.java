/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe:
 * @(#)JMenuItemPad.java <BR>
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
 * Coment�rios da classe.....
 */

package org.freedom.library.swing.component;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import org.freedom.acao.DefaultRadioGroupListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.library.persistence.ListaCampos;

public class JRadioGroup<S, T> extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	public static final int TP_NONE = -1;

	public static final int TP_STRING = 0;

	public static final int TP_INTEGER = 4;

	public static final int TP_BOOLEAN = 10;

	private ButtonGroup bg = new ButtonGroup();

	private Object oVals[] = null;

	private Object oLabs[] = null;

	private RadioGroupListener rgLis = new DefaultRadioGroupListener();

	private ListaCampos lcRadio = null;

	public int Tipo = -1;

	private Border borda = null;

	public JRadioGroup(int Lin, int Col, Vector<S> labs, Vector<T> vals) {

		this(Lin, Col, labs.toArray(), vals.toArray());
	}

	public JRadioGroup(int Lin, int Col, Vector<S> labs, Vector<T> vals, int margin_top) {
		this(Lin, Col, labs.toArray(), vals.toArray(), margin_top);
	}

	public Border getBorda() {
		return borda;
	}

	public void setBorda(Border borda) {
		this.borda = borda;
		mudaBorda();
	}

	public void mudaBorda() {
		setBorder(borda);
	}

	public JRadioGroup(int Lin, int Col, Object oLabs[], Object oVals[]) {
		this(Lin, Col, oLabs, oVals, 0);
	}

	public JRadioGroup(int Lin, int Col, Object oLabs[], Object oVals[], int margin_top) {

		setLayout(new GridLayout(Lin, Col));

		borda = BorderFactory.createEtchedBorder();
		setBorder(borda);

		this.oVals = oVals;
		this.oLabs = oLabs;

		addKeyListener(this);

		for (int i = 0; i < oVals.length; i++) {
			JRadioButton rg = new JRadioButton(( String ) oLabs[i]);
			rg.setMnemonic(i);
			rg.addActionListener(this);
			rg.addKeyListener(this);

			JPanel pnCenter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, margin_top));

			pnCenter.add(rg);
			add(pnCenter);

			bg.add(rg);

			rg.addKeyListener(this);
			rg.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		JRadioButton radio = ( ( JRadioButton ) ( ( JPanel ) getComponent(0) ).getComponent(0) );
		radio.setSelected(true);
		setTipo();

	}
	
	public void setFont(Font fonte) {
		
		try {
			
			AbstractButton rbTmp = null;
			
			if(bg!=null){
				for (Enumeration<AbstractButton> e = bg.getElements(); e.hasMoreElements();) {
					
					rbTmp = e.nextElement();
					
					if (rbTmp != null) {
						rbTmp.setFont(fonte);
					}
					
				}
			}
						
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public void novo() {

		JRadioButton radio = ( ( JRadioButton ) ( ( JPanel ) getComponent(0) ).getComponent(0) );
		radio.setSelected(true);
		fireValorAlterado(radio, 0);

	}

	private void setTipo() {

		if (oVals[0] instanceof Integer)
			Tipo = TP_INTEGER;
		else if (oVals[0] instanceof String)
			Tipo = TP_STRING;
		else if (oVals[0] instanceof Boolean)
			Tipo = TP_BOOLEAN;
	}

	public int getTipo() {

		return Tipo;
	}

	public String getVlrString() {

		return ( String ) oVals[bg.getSelection().getMnemonic()];
	}

	public Integer getVlrInteger() {

		return ( Integer ) oVals[bg.getSelection().getMnemonic()];
	}

	public Boolean getVlrBoolean() {

		return ( Boolean ) oVals[bg.getSelection().getMnemonic()];
	}

	public void setVlrString(String val) {

		for (int i = 0; i < oVals.length; i++) {
			if (val.compareTo(( String ) oVals[i]) == 0) {
				( ( JRadioButton ) ( ( JPanel ) getComponent(i) ).getComponent(0) ).setSelected(true);
				fireValorAlterado(( ( JRadioButton ) ( ( JPanel ) getComponent(i) ).getComponent(0) ), i);
				break;
			}
		}
	}

	public void setVlrInteger(Integer val) {

		for (int i = 0; i < oVals.length; i++) {
			if (val == ( Integer ) oVals[i]) {
				( ( JRadioButton ) ( ( JPanel ) getComponent(i) ).getComponent(0) ).setSelected(true);
				fireValorAlterado(( ( JRadioButton ) ( ( JPanel ) getComponent(i) ).getComponent(0) ), i);
				break;
			}
		}
	}

	public void setVlrBoolean(Boolean val) {

		for (int i = 0; i < oVals.length; i++) {
			if (val == ( Boolean ) oVals[i]) {
				( ( JRadioButton ) ( ( JPanel ) getComponent(i) ).getComponent(0) ).setSelected(true);
				fireValorAlterado(( ( JRadioButton ) ( ( JPanel ) getComponent(i) ).getComponent(0) ), i);
				break;
			}
		}
	}

	public void actionPerformed(ActionEvent evt) {

		for (int i = 0; i < oLabs.length; i++) {
			if (evt.getActionCommand() == ( String ) oLabs[i]) {
				if (lcRadio != null) {
					lcRadio.edit();
				}
				fireValorAlterado(( ( JRadioButton ) ( ( JPanel ) getComponent(i) ).getComponent(0) ), i);
			}
		}
	}

	public void setAtivo(boolean bAtiva) {

		AbstractButton rbTmp = null;
		for (Enumeration<AbstractButton> e = bg.getElements(); e.hasMoreElements();) {
			rbTmp = e.nextElement();
			if (rbTmp != null)
				rbTmp.setEnabled(bAtiva);
		}
	}

	public void setAtivo(int ind, boolean ativ) {

		( ( JRadioButton ) ( ( JPanel ) getComponent(ind) ).getComponent(0) ).setEnabled(ativ);
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

	public void setListaCampos(ListaCampos lc) {

		lcRadio = lc;
	}

	public void addRadioGroupListener(RadioGroupListener cl) {

		rgLis = cl;
	}

	public void addKeyListener(KeyListener klis) {

		// for (int i=0; i<valores.size(); i++) {
		// Funcoes.mensagemInforma(null,"I: "+i);
		// ((JRadioButton)((JPanel)getComponent(i)).getComponent(0)).addKeyListener(klis);
		// }
		super.addKeyListener(klis);
	}

	private void fireValorAlterado(JRadioButton rb, int ind) {

		rgLis.valorAlterado(new RadioGroupEvent(rb, ind, this));
	}
}
