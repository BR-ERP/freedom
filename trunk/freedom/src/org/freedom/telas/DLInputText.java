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
 *                      vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 *                      A LPG-PC deve acompanhar todas PUBLICA��ES,
 *                      DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 *                      Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com
 *                      este Programa, voc� pode contatar <BR>
 *                      o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 *                      Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
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

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.componentes.JTextAreaPad;
import org.freedom.funcoes.Funcoes;

/**
 * @author robson
 */
public class DLInputText extends FFDialogo {

	private JTextAreaPad txaMemo = new JTextAreaPad();
	private JScrollPane spnMemo = new JScrollPane(txaMemo);
	private boolean required = false;

	public DLInputText(Component cOrig, String title, boolean required) {
		super(cOrig);
		setAtribos(350, 250);
		c.add(spnMemo, BorderLayout.CENTER);
		c.add(new JLabel(title), BorderLayout.NORTH);

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

