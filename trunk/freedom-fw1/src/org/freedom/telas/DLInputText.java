/**
 * @version 22/10/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLInputText.java <BR>
 *                      Este programa � licenciado de acordo com a LPG-PC
 *                      (Licen�a P�blica Geral para Programas de Computador),
 *                      <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                      A LPG-PC deve acompanhar todas PUBLICA��ES,
 *                      DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 *                      Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com
 *                      este Programa, voc� pode contatar <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                      Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou
 *                      ALTERAR este Programa � preciso estar <BR>
 *                      de acordo com os termos da LPG-PC <BR>
 *                      <BR>
 *                      Dialog de edi��o e inser��o de observa��es por data nos
 *                      clientes
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JScrollPane;

import org.freedom.funcoes.Funcoes;
import org.freedom.library.JLabelPad;
import org.freedom.library.JTextAreaPad;

/**
 * @author robson
 */
public class DLInputText extends FFDialogo {
	private static final long serialVersionUID = 1L;


	private JTextAreaPad txaMemo = new JTextAreaPad();
	private JScrollPane spnMemo = new JScrollPane(txaMemo);
	private boolean required = false;

	public DLInputText(Component cOrig, String title, boolean required) {
		super(cOrig);
		setAtribos(350, 250);
		c.add(spnMemo, BorderLayout.CENTER);
		c.add(new JLabelPad(title), BorderLayout.NORTH);

		this.required = required;
	}

	public String getTexto() {
		return txaMemo.getText();
	}

	public void setTexto(String sTexto) {
		txaMemo.setText(sTexto);
	}

	public void ok() {
		if (txaMemo.getText().trim().equals("") && required) {
			Funcoes.mensagemInforma(this, "O campo de texto deve ser preenchido !");
		} else {
			OK = true;
			setVisible(false);
		}
	}

	public void cancel() {
		if (txaMemo.getText().trim().equals("") && required) {
			Funcoes.mensagemInforma(this, "O campo de texto deve ser preenchido !");
		} else {
			OK = false;
			setVisible(false);
		}

	}

}

