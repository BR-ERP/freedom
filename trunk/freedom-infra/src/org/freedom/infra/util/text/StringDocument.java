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
 * StringDocument tem por objetivo permitir a aplica��o de uma m�scara gen�rica,
 * conseguindo-se aplicar qualquer tipo de m�scara.
 * Contudo seu uso n�o � aplicado a m�scaras exclusivamente num�ricas, pois n�o faz
 * o tratamento para valores negativos ou de ponto flutuante.<br>
 * O seu uso � bem aplicado de telefones ou c�digos postais por exemplos.
 * <blockquote>
 * <code>
 * (####) 0000-0000 - para telefone  <br>
 * ou <br>
 * 00.000-000 - C�digo postal
 * </code>
 * </blockquote>
 * Seu uso tamb�m � interessante em c�digos de refer�ncia,
 * como um c�digo de produtos que exija a valida��o para n�mero e letras.<br>
 * 
 * <blockquote>
 * <code>
 * C�digo do produto : ZW-1234
 * m�scara           : AA-0000
 * </code>
 * </blockquote>
 * 
 * @see			org.freedom.infra.util.text.Document
 * @see			org.freedom.infra.util.text.Mask
 * 
 * @author 		Alex Rodrigues
 * @version 	0.0.1 � 16/05/2008
 * 
 * @since 		16/05/2008
 */
public class StringDocument extends Document {
	
	private static final long serialVersionUID = 1l;
	

	public StringDocument() {
		this( new GapContent(), null );
	}

	public StringDocument( final Content c ) {
		this( c, null );
	}

	public StringDocument( final int size ) {
		this( new GapContent(), size );
	}

	public StringDocument( final String mask ) {
		this( new GapContent(), new Mask( mask ) );
	}

	public StringDocument( final Mask mask ) {
		this( new GapContent() ,mask );
	}

	public StringDocument( final Content c, final int size ) {
		this( c , Mask.createString( size ) );
	}

	public StringDocument( final Content c, final Mask mask ) {
		super( c );
		setMask( mask );
	}
	
	/**
     * Este metodo foi sobrescrito para que seja possiv�l, validar-se
     * o texto a ser inserido no documento, e formata-lo conforme a mascara,
     * antes do texto ser inserido no documento.
     * 
     * @see		#validateCharacter(char[], char, StringBuilder)
     * 
     * @since 	16/05/2008
     */
	@Override
	public void insertString( final int offs, final String str, final AttributeSet a ) throws BadLocationException {

		if ( mask != null ) {
			
			final StringBuilder newstr = new StringBuilder();
			
			if ( getLength() >= mask.length() ) {
				return;
			}

			final char[] chars = mask.getChars();
			final char[] value = str.toCharArray();

			for ( int i = 0; i < value.length; i++ ) {
				if ( index >= chars.length ) {
					break;
				}
				validateCharacter( chars, value[ i ], newstr );
			}
			
			super.insertString( offs, newstr.toString(), a );
		}
		else {
			super.insertString( offs, str, a );
		}

	}
	
	/**
	 * Este metodo foi sobrescrito para que seja possiv�l
	 * atualizar o indice da posi��o na mascara.
	 * 
	 * @since	16/05/2008
	 */
	@Override
	public void remove( final int arg0, final int arg1 ) throws BadLocationException {

		super.remove( arg0, arg1 );
		index = arg0;
	}
	
	/**
	 * Este � um metodo auxiliar para o metodo <code>insertString</code>.<br> 
	 * <blockquote>
	 * A partir do caracter da mascara definimos a caracteristica do valor
	 * a ser inserido no buffer, este buffer posteriormente ser� inserido no documento,
	 * ap�s todo o texto ter sido validado.
	 * </blockquote>
	 * 
	 * @param 	ch		Carater da mascara para baseamento da valida��o
	 * @param 	value	Carater a ser validado
	 * @param 	str		Buffer a qual ser� adicionado
	 */
	private void validateCharacter( char[] ch, char value, StringBuilder str ) {
		
		if ( getLength()+str.length() >= ch.length ) {
			return;
		}
		if ( ch[index] == Mask.ALL ) {
			str.append( value );
			index++;
		}
		else if ( ch[index] == Mask.INTEGER ) {
			if ( Character.isDigit( value ) ) {						
				str.append( value );						
				index++;
			}
		}
		else if ( ch[index] == Mask.CHAR ) {
			if ( Character.isLetter( value ) ) {						
				str.append( value );						
				index++;
			}
		}
		else {		
			if ( ch[index] == value ) {
				str.append( ch[index++] );
			}
			else {
				str.append( ch[index++] );
				validateCharacter( ch, value, str );
			}
		}
	}

}
