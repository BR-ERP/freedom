/**
 * @version 01/06/2001 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FRelatorio.java <BR>
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
 * Formul�rio modelo para relat�rios.
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;

public abstract class FRelatorio extends FFilho implements ActionListener, KeyListener {

	private JPanelPad pinCli = new JPanelPad( 350, 170 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCentRod = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 0, 0 ) );

	public JPanelPad pnBotoes = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 2 ) );

	private JButton btImp = new JButton( Icone.novo( "btImprime.gif" ) );

	private JButton btPrevimp = new JButton( Icone.novo( "btPrevimp.gif" ) );

	private JButton btSair = new JButton( "Sair", Icone.novo( "btSair.gif" ) );
	
	private final DLLoading wait = new DLLoading();

	boolean setArea = true;

	boolean bCtrl = false;

	Container c = null;

	public FRelatorio() {
		this( true );
	}

	public FRelatorio(boolean comScroll) {
		super(comScroll);
		setTitulo( "Requisi�ao de Relat�rio" );
		setAtribos( 100, 100, 350, 200 );
		c = super.getTela();
		pnRod.setBorder( BorderFactory.createEtchedBorder() );
		c.add( pnRod, BorderLayout.SOUTH );
		pnRod.setPreferredSize( new Dimension( 350, 32 ) );
		btSair.setPreferredSize( new Dimension( 100, 30 ) );
		pnRod.add( btSair, BorderLayout.EAST );
		pnRod.add( pnCentRod, BorderLayout.CENTER );
		pnBotoes.setPreferredSize( new Dimension( 80, 28 ) );
		pnCentRod.add( pnBotoes );
		pnBotoes.add( btImp );
		pnBotoes.add( btPrevimp );

		btImp.setToolTipText( "Imprimir (Ctrl + I)" );
		btPrevimp.setToolTipText( "Visualizar Impress�o (Ctrl + P)" );
		btSair.setToolTipText( "Sair (ESC)" );

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btSair.addActionListener( this );
		btImp.addKeyListener( this );
		btPrevimp.addKeyListener( this );
		btSair.addKeyListener( this );
	}
	
	public void setPanel( JPanelPad pn ) {

		c.remove( pinCli );
		c.add( pn, BorderLayout.CENTER );
		setArea = false;
	}

	public void setPainel( JPanelPad pin ) {

		pinCli = pin;
		setArea = false;
	}

    public void setAreaComp() {
	    pinCli = new JPanelPad((int)getSize().getWidth()-10,
	      (int)getSize().getHeight()-65);  
	    pnCliente.add(pinCli, BorderLayout.CENTER);
	    setArea = false;
    }
    
	public void adic( Component comp, int iX, int iY, int iLarg, int iAlt ) {

		if ( setArea )
			setAreaComp();
		comp.addKeyListener( this );
		pinCli.adic( comp, iX, iY, iLarg, iAlt );
	}

	/**
     * 
     * Retorna o container do rootPane. Sobrep�s o getTela do FFilho por ele 'resetar' o Layout.
     * 
     * @see FFilho#getTela
     * 
     */
	public Container getTela() {

		return c;
	}

	public abstract void imprimir( boolean b );

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			wait.stop();
			dispose();
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		else if ( evt.getSource() == btPrevimp ) {
			//wait.start();
			//Thread th = new Thread( new Runnable() {
			//	public void run() {
					imprimir( true );
			//		wait.stop();
			//	}
			//} );
			//th.start();
		}
	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_CONTROL ) {
			bCtrl = true;
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_I ) {
			if ( bCtrl )
				btImp.doClick();
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_P ) {
			if ( bCtrl )
				btPrevimp.doClick();
		}
		else if ( kevt.getKeyCode() == KeyEvent.VK_ESCAPE ) {
			btSair.doClick();
		}
	}

	public void keyReleased( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_CONTROL ) {
			bCtrl = false;
		}
	}

	public void keyTyped( KeyEvent kevt ) {

	}
}
