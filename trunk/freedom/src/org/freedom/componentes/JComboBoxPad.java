/**
 * @version 04/06/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe:
 * @(#)JComboBoxPad.java <BR>
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
 * Campo do tipo combobox.
 */

package org.freedom.componentes;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JComboBox;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;

//public class JComboBoxPad<S, T> extends JComboBox implements JComboBoxListener, ItemListener {
public class JComboBoxPad extends JComboBox implements JComboBoxListener, ItemListener {

	private static final long serialVersionUID = 1L;

	public static final int TP_NONE = -1;

	public static final int TP_STRING = 0;

	public static final int TP_INTEGER = 4;

	private Vector<?> valores = new Vector<Object>();

	private JComboBoxListener cbLis = this;

	private ListaCampos lcCombo = null;

	private boolean criando = true;

	private int tipo = -1;

	private int tam = 8;

	private int dec = 0;

	private boolean bZeroNull = false;

	public void setZeroNulo() {

		bZeroNull = true;
	}

	//public JComboBoxPad( Vector<S> label, Vector<T> val, int tipo, int tam, int dec ) {
	public JComboBoxPad( Vector<String> label, Vector<?> val, int tipo, int tam, int dec ) {

		criando = true;
		if ( val != null && label != null ) {
			valores = val;
			for ( int i = 0; i < label.size(); i++ ) {
				addItem( label.elementAt( i ) );
			}
		}
		addItemListener( this );
		this.tipo = tipo;
		this.tam = tam;
		this.dec = dec;
		criando = false;
	}

	public void setItens( Vector<String> label, Vector<?> val ) {

		criando = true;
		removeAllItems();
		valores = val;

		for ( int i = 0; i < label.size(); i++ ) {
			addItem( label.elementAt( i ) );
		}
		criando = false;
	}

	public void setAtivo( boolean bVal ) {

		setEnabled( bVal );
	}

	public void limpa() {

		if ( getItemCount() > 0 ) {
			setSelectedIndex( 0 );
		}
	}

	public int getTipo() {

		return tipo;
	}

	public int getTam() {

		return tam;
	}

	public int getDec() {

		return dec;
	}

	public String getVlrString() {

		int iInd = getSelectedIndex();
		if ( valores != null && iInd >= 0 && iInd < valores.size() ) {
			return valores.elementAt( getSelectedIndex() ).toString();
		}
		return "";
	}

	public String getText() {

		String retorno = "";
		int iInd = getSelectedIndex();
		if ( valores != null && iInd >= 0 && iInd < valores.size() ) {
			retorno = valores.elementAt( getSelectedIndex() ).toString();
		}
		return retorno;
	}

	public Integer getVlrInteger() {

		try {
			if ( (Integer) valores.elementAt( getSelectedIndex() ) == new Integer( 0 ) && bZeroNull ) {
				return null;
			}
			return (Integer) valores.elementAt( getSelectedIndex() );
		} catch ( Exception err ) {
			return new Integer( 0 );
		}
	}

	public void setVlrString( String val ) {

		for ( int i = 0; i < valores.size(); i++ ) {
			if ( valores.elementAt( i ).equals( val ) ) {
				setSelectedIndex( i );
				fireValorAlterado( i );
				break;
			}
		}
	}

	public void setVlrInteger( Integer val ) {

		for ( int i = 0; i < valores.size(); i++ ) {
			if ( valores.elementAt( i ).equals( val ) ) {
				setSelectedIndex( i );
				fireValorAlterado( i );
				break;
			}
		}
	}

	public void setListaCampos( ListaCampos lc ) {

		lcCombo = lc;
	}

	public void addComboBoxListener( JComboBoxListener cb ) {

		cbLis = cb;
	}

	private void fireValorAlterado( int ind ) {

		cbLis.valorAlterado( new JComboBoxEvent( this, ind ) );
	}

	public void valorAlterado( JComboBoxEvent cbevt ) {

		if ( ( !criando ) && ( lcCombo != null ) ) {
			if ( lcCombo.getStatus() == ListaCampos.LCS_SELECT ) {
				lcCombo.edit();
			}
		}
	}

	public void itemStateChanged( ItemEvent itevt ) {

		if ( itevt.getStateChange() == ItemEvent.SELECTED ) {
			fireValorAlterado( getSelectedIndex() );
		}
	}
}
