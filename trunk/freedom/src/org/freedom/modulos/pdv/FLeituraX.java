
/**
 * @version 25/07/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe: @(#)FLeituraX.java <BR>
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
 * Tela para impress�o de leitura X
 * 
 */


package org.freedom.modulos.pdv;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;

import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.drivers.JBemaFI32;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class FLeituraX extends FFDialogo implements ActionListener {
	private JPanelPad pn = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
	private JTextAreaPad txa = new JTextAreaPad();
	private JScrollPane spn = new JScrollPane(txa);
	private JBemaFI32 bf = (FreedomPDV.bECFTerm ? new JBemaFI32() : null);	
	public FLeituraX() {
		super();
		setAtribos(400,200);
	    pn.add(spn);
	    c.add(pn);
	    txa.setEditable(false);
	    txa.setText("Impress�o de leitura X.\n" +
	    		"Pressione \"OK\" para confirmar a impress�o.\n" +
	    		"Pressione \"CANCELAR\" para sair!");
		
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource()==btOK) {
			if (bf!=null)
				bf.leituraX(Aplicativo.strUsuario, AplicativoPDV.bModoDemo);
		}
		super.actionPerformed(evt);
	}
}
