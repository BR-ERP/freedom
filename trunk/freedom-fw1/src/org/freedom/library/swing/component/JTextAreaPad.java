/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JTextFieldPad.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Coment�rios da classe.....
 */

package org.freedom.library.swing.component;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import org.freedom.library.persistence.Campo;
import org.freedom.library.persistence.ListaCampos;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

public class JTextAreaPad extends JTextArea implements KeyListener, Campo {

	private static final long serialVersionUID = 1L;
	private ListaCampos lcTxa = null;
	public int iTamanho = 10000;
	public int iDecimal = 0;
	private int iMascara = -1;
	private int tipoCampo = JTextFieldPad.TP_STRING;
	//private int tipoCampo = TP_NONE;

	boolean bAtivo = true;

	/**
	 * Construtor da classe (sem tamanho). <BR>
	 * Coloca o tamanho padr�o: 10000.
	 * 
	 */
	public JTextAreaPad() {
		this(0);
		this.setBorder(BorderFactory.createEtchedBorder());
	}

	/**
	 * Construtor da classe (com tamanho). <BR>
	 * N�mero de caracteres que o txa pode aceitar.
	 * 
	 */
	public JTextAreaPad(int iTam) {
		if (iTam > 0)
			iTamanho = iTam;
		addKeyListener(this);
		this.setLineWrap(true);
	}

	public String getVlrString() {
		return getText();
	}

	public void setListaCampos(ListaCampos lc) {
		lcTxa = lc;
	}

	public void setVlrString(String val) {
		setText(val);
		setCaretPosition(0);
	}

	public void setAtivo(boolean b) {
		if (b) {
			setEditable(true);
			bAtivo = true;
			this.setBackground(Color.WHITE);
			this.setBorder(BorderFactory.createEtchedBorder());
		}
		else {
			setEditable(false);
			bAtivo = false;
			this.setBackground(new Color(238, 238, 238));
			this.setBorder(BorderFactory.createLineBorder(new Color(184, 207, 229)));
		}
	}

	public void keyTyped(KeyEvent kevt) {
		if (( kevt.getKeyChar() != KeyEvent.CHAR_UNDEFINED ) && ( kevt.getKeyChar() != ( char ) 8 ) && ( kevt.getKeyChar() != ( char ) 10 ) && ( kevt.getKeyChar() != ( char ) 9 )) {
			if (getText().length() > iTamanho)
				kevt.setKeyChar(( char ) 0);
			else if (lcTxa != null)
				lcTxa.edit();
		}
	}

	public void setNaoEditavel(boolean b) {
		// bSoLeitura = b;
		if (b) {
			setEditable(false);
			bAtivo = false;
		}
		else {
			setEditable(true);
			bAtivo = true;
		}
	}

	public void keyPressed(KeyEvent kevt) {
	}

	public void keyReleased(KeyEvent kevt) {
	}
	
	public ListaCampos getTabelaExterna() {
		return null;
	}
	
	public String getStrMascara() {
		return "";
	}
	
	public int getTamanho() {
		return iTamanho;
	}

	public void setTamanho(int tam) {
		this.iTamanho = tam;
	}
	
	public int getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(int tipoCampo) {
		this.tipoCampo = tipoCampo;
	}
	
	public void setDecimal(int dec) {
		this.iDecimal = dec;
	}
	
	public int getDecimal() {
		return this.iDecimal;
	}

	public int getIMascara() {
		return iMascara;
	}

	public void setIMascara(int iMascara) {
		this.iMascara = iMascara;
	}

	public ListaCampos getListaCampos() {
		return this.lcTxa;
	}

	public int getMascara() {
		return iMascara;
	}
	
	public Integer getVlrInteger() {
		return new Integer(0);
	}
	
	public void setVlrBigDecimal(BigDecimal vlr) {
		
	}

	public void setVlrInteger(Integer vlr) {
		
	}
	
	public void cancelaDLF2() {
		//runDLF2 = false;
	}
	
}
