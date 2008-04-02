/**
 * @version 12/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.acao <BR>
 * Classe:
 * @(#)RadioGroupEvent.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
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

	public RadioGroupEvent( JRadioButton rb, int ind, Object source ) {

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
