/**
 * @version 06/09/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FObsCliVend.java <BR>
 * 
 *                      Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 *                      modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                      A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 *                      Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 *                      sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                      Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                      Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 *                      de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                      Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;

import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.dialog.FFDialogo;

public class FObsCliVend extends FFDialogo implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JTextAreaPad txaObs = new JTextAreaPad();

	private JScrollPane spObs = new JScrollPane( txaObs );

	public FObsCliVend() {

		super();
		txaObs.setEditable( false );
		c.add( spObs, BorderLayout.CENTER );
		btOK.requestFocus();
		txaObs.addKeyListener( this );
		btCancel.setVisible( false );

		addKeyListener( this );
	}

	public static void showVend( int x, int y, int larg, int alt, String sObsCli ) {

		FObsCliVend tela = new FObsCliVend();
		tela.setAtribos( x, y, larg, alt + 50 );
		tela.txaObs.setText( sObsCli );
		tela.setVisible( true );

	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_SPACE )
			btOK.doClick();
		else
			super.keyPressed( kevt );
	}

	public void keyReleased( KeyEvent kevt ) {

		super.keyReleased( kevt );
	}

	public void keyTyped( KeyEvent kevt ) {

		super.keyTyped( kevt );
	}

}
