/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLDataTransf.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.JLabel;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;
public class DLDataTransf extends FFDialogo {
	private JTextFieldPad txtDataTransf = new JTextFieldPad();
	public DLDataTransf(Component cOrig) {
		super(cOrig);
		setTitulo("Nova Data");
		setAtribos(100,100,250,120);

		txtDataTransf.setTipo(JTextFieldPad.TP_DATE,10,0);

		adic(new JLabel("Nova Data:"),7,0,110,20);
		adic(txtDataTransf,7,20,100,20);
	}
	public Date getValor() {
		return txtDataTransf.getVlrDate();
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			if (txtDataTransf.getText().length() < 10) {
				Funcoes.mensagemInforma(this,"Valor invalido para data!");
				txtDataTransf.requestFocus();
				return;
			}
		}
		super.actionPerformed(evt);
	}

}
