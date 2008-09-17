
package org.freedom.infra.util.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.GapContent;

/**
 * Projeto: <a href="http://sourceforge.net/projects/freedom-erp/">Freedom-infra</a> <br>
 * Este programa � licenciado de acordo com a LPG-PC <br>
 * (Licen�a P�blica Geral para Programas de Computador) vers�o 2.1.0 ou qualquer vers�o posterior. <br>
 * <br>
 * 
 * IntegerDocument visa prover a aplica��o de uma mascara exclusiva para n�meros inteiros.
 * Sua utiliza��o � simples, pois a mascara a ser utilizada somente permitir� caracteres n�mericos.
 * O padr�o para o tamanho da m�scara � de 10 algarismos, mas este pode ser definido atr�ves de
 * parametro no costrutor.
 * 
 * @see			javax.swing.text.Document
 * @see			org.freedom.infra.util.text.Mask
 * 
 * @author 		Alex Rodrigues
 * @version 	0.0.2 � 16/05/2008
 * 
 * @since 		13/05/2008
 */
public class IntegerDocument extends Document {

	private static final long serialVersionUID = 1l;
	

	public IntegerDocument() {
		this( new GapContent(), 10 );
	}

	public IntegerDocument( final Content c ) {
		this( c, 10 );
	}

	public IntegerDocument( final int size ) {
		this( new GapContent(), size );
	}

	public IntegerDocument( final Content c, final int size ) {
		super( c );
		setMask( Mask.createInteger( size ) );
	}
	
	/**
     * Este metodo foi sobrescrito para que seja possiv�l, validar-se
     * o texto a ser inserido no documento, e formata-lo conforme a mascara,
     * antes do texto ser inserido no documento.
     * 
     * @see		#validateMask(String, int)
     * @see		#validateInteger(String)
     * 
     * @since 	13/05/2008
     */
	@Override
	public void insertString( final int offs, final String str, final AttributeSet a ) throws BadLocationException {

		StringBuilder newstr = new StringBuilder(
				mask != null ? validateMask( str, offs ) : validateInteger( str ) );

		super.insertString( offs, newstr.toString(), a );

	}
	
	/**
	 * Este metodo foi sobrescrito para que seja possiv�l
	 * atualizar o indice da posi��o na mascara.
	 * 
	 * @since	13/05/2008
	 */
	@Override
	public void remove( final int offs, final int len ) throws BadLocationException {

		if ( mask != null ) {
			if ( getText( offs, len ).indexOf( '-' ) > -1 ) {
				mask.setLength( mask.length() - 1 );
				super.remove( offs, len );
			}
			else {
				super.remove( offs, len );
				index = getLength();
				if ( getText( 0, index ).indexOf( '-' ) > -1 ) {
					--index;
				}
			}
		}
		else {
			super.remove( offs, len );
		}
	}
	
	/**
	 * Verifica a possibilidade de inserss�o do texto,
	 * se sim encaminha o texto para valida��o com <code>validateMaskAux</code>.
	 * 
	 * @see		#validateMaskAux(char, StringBuilder, int).
	 * 
	 * @since	13/05/2008
	 */
	private String validateMask( String str, int offs ) throws BadLocationException {
		
		StringBuilder newstr = new StringBuilder();
		final char[] value = str.toCharArray();
		
		if ( offs >= mask.length() ) {
			return newstr.toString();
		}

		for ( int i = 0; i < value.length; i++ ) {
			if ( ! validateMaskAux( value[i], newstr, offs ) ) {
				break;
			}
		}

		return newstr.toString();
	}
	
	/**
	 * Re-distribui a valida��o para os metodos de valida��o de negativo e o de valida��o de inteiro.
	 * 
	 * @see		#validateMaskAuxNegative(char, StringBuilder, int)
	 * @see		#validateMaskAuxInteger(char, StringBuilder)
	 *  
	 * @since	13/05/2008
	 */
	private boolean validateMaskAux( char value, StringBuilder str, int offs ) throws BadLocationException {
		
		final char[] chars = mask.getChars();

		if ( ! validateMaskAuxNegative( value, str, offs ) ) {
			if ( index >= chars.length ) {
				return false;
			}
			validateMaskAuxInteger( value, str );
		}
		
		return true;
	}
	
	/**
	 * Verifica se o caracter � o negativo.
	 * 
	 * @return	Verdadeiro para n�mero negativo.
	 * 
	 * @since	13/05/2008
	 */
	private boolean validateMaskAuxNegative( char value, StringBuilder str, int offs ) throws BadLocationException {
		
		if ( value == Mask.NEGATIVE 
				&& offs == 0 && getText( 0, getLength() ).indexOf( Mask.NEGATIVE ) == -1 ) {
			mask.setLength( mask.length()+1 );
			str.append( value );
			return true;
		}
			
		return false;
	}
	
	/**
	 * Verifica se o valor � um inteiro.
	 * 
	 * @since	13/05/2008
	 */
	private void validateMaskAuxInteger( char value, StringBuilder str) throws BadLocationException {
		
		final char[] chars = mask.getChars();
		
		if ( chars[ index ] == Mask.INTEGER ) {
			if ( Character.isDigit( value ) ) {
				str.append( value );
				index++;
			}
		}
	}
	
	/**
	 * Valida o texto para utiliza��o somente de algarismos n�mericos, sem a utiliza��o de mascara.
	 * 
	 * @param 	str Texto a ser validado
	 * @return	Texto com os caracters v�lidos.
	 * 
	 * @since	13/05/2008
	 */
	private String validateInteger( String str ) {
		
		StringBuilder newstr = new StringBuilder();
		final char[] value = str.toCharArray();
		
		for ( char c : value ) {
			if ( Character.isDigit( c ) || c == Mask.NEGATIVE ) {
				newstr.append( c );
			}
		}

		return newstr.toString();
	}

}
