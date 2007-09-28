/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe:
 * @(#)JMenuItemPad.java <BR>
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

package org.freedom.componentes;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;

import org.freedom.acao.DefaultRadioGroupListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;

import java.util.Enumeration;
import java.util.Vector;


public class JRadioGroup<S, T> extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	public static final int TP_NONE = -1;

	public static final int TP_STRING = 0;

	public static final int TP_INTEGER = 4;

	public static final int TP_BOOLEAN = 10;

	private ButtonGroup bg = new ButtonGroup();

	private Object oVals[] = null;

	private Object oLabs[] = null;

	private RadioGroupListener rgLis = new DefaultRadioGroupListener();

	private ListaCampos lcRadio = null;

	public int Tipo = -1;

	public JRadioGroup( int Lin, int Col, Vector<S> labs, Vector<T> vals ) {

		this( Lin, Col, labs.toArray(), vals.toArray() );
	}

	public JRadioGroup( int Lin, int Col, Object oLabs[], Object oVals[] ) {

		setLayout( new GridLayout( Lin, Col ) );
		setBorder( BorderFactory.createEtchedBorder() );
		this.oVals = oVals;
		this.oLabs = oLabs;
		addKeyListener( this );
		for ( int i = 0; i < oVals.length; i++ ) {
			JRadioButton rg = new JRadioButton( (String) oLabs[ i ] );
			rg.setMnemonic( i );
			rg.addActionListener( this );
			rg.addKeyListener( this );
			JPanel pnCenter = new JPanel( new FlowLayout( FlowLayout.LEFT, 10, 0 ) );
			pnCenter.add( rg );
			add( pnCenter );
			bg.add( rg );
			rg.addKeyListener( this );
		}
		( (JRadioButton) ( (JPanel) getComponent( 0 ) ).getComponent( 0 ) ).setSelected( true );
		setTipo();
	}

	public void novo() {

		( (JRadioButton) ( (JPanel) getComponent( 0 ) ).getComponent( 0 ) ).setSelected( true );
		fireValorAlterado( ( (JRadioButton) ( (JPanel) getComponent( 0 ) ).getComponent( 0 ) ), 0 );
	}

	private void setTipo() {

		if ( oVals[ 0 ] instanceof Integer )
			Tipo = TP_INTEGER;
		else if ( oVals[ 0 ] instanceof String )
			Tipo = TP_STRING;
		else if ( oVals[ 0 ] instanceof Boolean )
			Tipo = TP_BOOLEAN;
	}

	public int getTipo() {

		return Tipo;
	}

	public String getVlrString() {

		return (String) oVals[ bg.getSelection().getMnemonic() ];
	}

	public Integer getVlrInteger() {

		return (Integer) oVals[ bg.getSelection().getMnemonic() ];
	}

	public Boolean getVlrBoolean() {

		return (Boolean) oVals[ bg.getSelection().getMnemonic() ];
	}

	public void setVlrString( String val ) {

		for ( int i = 0; i < oVals.length; i++ ) {
			if ( val.compareTo( (String) oVals[ i ] ) == 0 ) {
				( (JRadioButton) ( (JPanel) getComponent( i ) ).getComponent( 0 ) ).setSelected( true );
				fireValorAlterado( ( (JRadioButton) ( (JPanel) getComponent( i ) ).getComponent( 0 ) ), i );
				break;
			}
		}
	}

	public void setVlrInteger( Integer val ) {

		for ( int i = 0; i < oVals.length; i++ ) {
			if ( val == (Integer) oVals[ i ] ) {
				( (JRadioButton) ( (JPanel) getComponent( i ) ).getComponent( 0 ) ).setSelected( true );
				fireValorAlterado( ( (JRadioButton) ( (JPanel) getComponent( i ) ).getComponent( 0 ) ), i );
				break;
			}
		}
	}

	public void setVlrBoolean( Boolean val ) {

		for ( int i = 0; i < oVals.length; i++ ) {
			if ( val == (Boolean) oVals[ i ] ) {
				( (JRadioButton) ( (JPanel) getComponent( i ) ).getComponent( 0 ) ).setSelected( true );
				fireValorAlterado( ( (JRadioButton) ( (JPanel) getComponent( i ) ).getComponent( 0 ) ), i );
				break;
			}
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		for ( int i = 0; i < oLabs.length; i++ ) {
			if ( evt.getActionCommand() == (String) oLabs[ i ] ) {
				if ( lcRadio != null ) {
					lcRadio.edit();
				}
				fireValorAlterado( ( (JRadioButton) ( (JPanel) getComponent( i ) ).getComponent( 0 ) ), i );
			}
		}
	}

	public void setAtivo( boolean bAtiva ) {

		AbstractButton rbTmp = null;
		for ( Enumeration<AbstractButton> e = bg.getElements(); e.hasMoreElements(); ) {
			rbTmp = e.nextElement();
			if ( rbTmp != null )
				rbTmp.setEnabled( bAtiva );
		}
	}

	public void setAtivo( int ind, boolean ativ ) {

		( (JRadioButton) ( (JPanel) getComponent( ind ) ).getComponent( 0 ) ).setEnabled( ativ );
	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
			transferFocus();
		}
	}

	public void keyTyped( KeyEvent kevt ) {

	}

	public void keyReleased( KeyEvent kevt ) {

	}

	public void setListaCampos( ListaCampos lc ) {

		lcRadio = lc;
	}

	public void addRadioGroupListener( RadioGroupListener cl ) {

		rgLis = cl;
	}

	public void addKeyListener( KeyListener klis ) {

		// for (int i=0; i<valores.size(); i++) {
		// Funcoes.mensagemInforma(null,"I: "+i);
		// ((JRadioButton)((JPanel)getComponent(i)).getComponent(0)).addKeyListener(klis);
		// }
		super.addKeyListener( klis );
	}

	private void fireValorAlterado( JRadioButton rb, int ind ) {

		rgLis.valorAlterado( new RadioGroupEvent( rb, ind ) );
	}
}
