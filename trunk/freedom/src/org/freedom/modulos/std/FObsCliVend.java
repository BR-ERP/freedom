/**
 * @version 06/09/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FObsCliVend.java <BR>
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
 * Coment�rios sobre a classe...
 *  
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.ScrollPane;

import javax.swing.JScrollPane;

import org.freedom.componentes.JTextAreaPad;
import org.freedom.telas.FFDialogo;

public class FObsCliVend extends FFDialogo {
	public JTextAreaPad txaObs = new JTextAreaPad();
	private JScrollPane spObs = new JScrollPane(txaObs);
	public FObsCliVend() {
		super();
		txaObs.setEditable(false);
		c.add(spObs, BorderLayout.CENTER);
		btOK.requestFocus();
	}
	public static void showVend(int x, int y, int larg, int alt, String sObsCli) {
		FObsCliVend tela = new FObsCliVend();
		tela.setAtribos(x, y, larg, alt + 50 );
		tela.txaObs.setText(sObsCli);
		tela.show();
		
	}
	
}
