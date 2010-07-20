/**
 * @version 12/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.acao <BR>
 * Classe:
 * @(#)RadioGroupEvent.java <BR>
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

package org.freedom.acao;

import javax.swing.JRadioButton;

public class RadioGroupEvent {

	private Object source = null;

	private JRadioButton rButton = null;

	private int Indice = -1;

	public RadioGroupEvent(JRadioButton rb, int ind, Object source) {

		rButton = rb;
		Indice = ind;
		this.source = source;
	}

	public JRadioButton getRadioButton() {

		return rButton;
	}

	public int getIndice() {

		return Indice;
	}

	public Object getSource() {

		return source;
	}
}
