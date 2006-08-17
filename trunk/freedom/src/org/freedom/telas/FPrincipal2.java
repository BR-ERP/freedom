/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FPrincipal.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para
 * Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios para a classe...
 */

package org.freedom.telas;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import org.freedom.bmps.Icone;

public class FPrincipal2 extends FPrincipal implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	
	public FPrincipal2(String sImgFundo) {
		super(null, sImgFundo);
	}

	public void inicializaTela() {
		setBgColor(new Color(255,255,255)); 
		addFundo();
	    addLinks(Icone.novo("lgSTP2.jpg"), Icone.novo("lgFreedom2.jpg"));
		adicBotoes();
		adicAgenda();
	}

}