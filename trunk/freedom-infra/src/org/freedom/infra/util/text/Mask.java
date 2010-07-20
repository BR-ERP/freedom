package org.freedom.infra.util.text;

/**
 * Projeto: <a
 * href="http://sourceforge.net/projects/freedom-erp/">Freedom-infra</a> <br>
 * Este programa � licenciado de acordo com a LPG-PC <br>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada
 * pela Funda��o do Software Livre (FSF); <BR>
 * <br>
 * 
 * Est� classe � uma representa��o de mascara para formata��o da apresenta��o.<br>
 * Com base nesta mascara ser� formatada a apresenta��o.<br>
 * 
 * <blockquote> Exemplo: 0000/00/00 - desta forma dever� ser substituido o '0'
 * pelo caracter inteiro.<br>
 * Para elabora��o da mascara verifique o valor das seguintes constantes.<br>
 * <code>
 * <ul>
 * <li>ALL      = # </li>
 * <li>CHAR     = A </li>
 * <li>INTEGER  = 0 </li>
 * </ul>
 * </code> Qualquer caracter diferente destes ser� identificado como mascara e
 * n�o dever� ser substituido, como o caracter '/' apresentado no exemplo.
 * </blockquote>
 * 
 * @see Document
 * @see org.freedom.infra.beans.Field
 * 
 * @author Alex Rodrigues
 * @version 0.0.2 � 16/05/2008
 * 
 * @since 13/05/2008
 */
public class Mask {

	/**
	 * Constante para indentificar <code>Caracteres</code>
	 */
	public static final char CHAR = 'A';

	/**
	 * Constante para indentificar <code>Inteiros</code>
	 */
	public static final char INTEGER = '0';

	/**
	 * Constante para indentificar <code>Negativo</code>
	 */
	public static final char NEGATIVE = '-';

	/**
	 * Constante para indentificar <code>N�mero de ponto flutuante</code>
	 */
	public static final char DECIMAL = ',';

	/**
	 * Constante para indentificar <code>Qualquer caracter</code>
	 */
	public static final char ALL = '#';

	/**
	 * Tamanho da mascara.
	 */
	private int length = 0;

	/**
	 * Array com os caracteres formadores da mascara.
	 */
	private char[] chars = null;

	/**
	 * Construtor que recebe o modelo da mascara.
	 * 
	 * @param mask
	 *            String contendo o modelo de mascara
	 * 
	 * @since 13/05/2008
	 */
	public Mask(final String mask) {

		length = mask.length();
		chars = mask.toCharArray();
	}

	/**
	 * @return C�pia do array de caracteres que formam a mascara. � retornada
	 *         uma c�pia para proteger a forma��o da mascara.
	 */
	public char[] getChars() {

		char[] retorno = new char[chars.length];
		System.arraycopy(chars, 0, retorno, 0, retorno.length);

		return retorno;
	}

	public int length() {
		return length;
	}

	public void setLength(final int length) {
		this.length = length;
	}

	public String getMask() {
		return new String(chars);
	}

	// //////////////////////////////////////////////////////////////////////////////
	// //
	// MASCARAS PR�-DEFINIDAS //
	// //
	// //////////////////////////////////////////////////////////////////////////////

	/**
	 * @param size
	 *            Tamanho da mascara.
	 * @return Mascara para inteiros.
	 * 
	 * @since 13/05/2008
	 */
	public static Mask createInteger(final int size) {

		StringBuilder mask = new StringBuilder();

		int count = 0;
		while (count++ < size) {
			mask.append("0");
		}

		return new Mask(mask.toString());
	}

	/**
	 * @param size
	 *            Tamanho da mascara, sem contar o caracter de separa��o da
	 *            decimal.
	 * @param precision
	 *            N�mero de casas decimais.
	 * @return Mascara para n�mero de ponto flutuante.
	 * 
	 * @since 13/05/2008
	 */
	public static Mask createDecimal(final int size, final int precision) {

		StringBuilder mask = new StringBuilder();

		int count = 0;
		while (count++ < size - precision) {
			mask.append("0");
		}

		mask.append(",");
		count = 0;

		while (count++ < precision) {
			mask.append("0");
		}

		return new Mask(mask.toString());
	}

	/**
	 * @param size
	 *            Tamanho m�ximo para o texto.
	 * @return Mascara para texto.
	 * 
	 * @since 13/05/2008
	 */
	public static Mask createString(final int size) {

		StringBuilder mask = new StringBuilder();

		int count = 0;
		while (count++ < size) {
			mask.append("#");
		}

		return new Mask(mask.toString());
	}

	/**
	 * @return Mascara para data no formato dia/mes/ano ( dd/MM/yyyy )
	 * 
	 * @since 13/05/2008
	 */
	public static Mask createDateDDMMYYYY() {

		return new Mask("00/00/0000");
	}

	/**
	 * @return Mascara para data no formato ano/mes/dia ( yyyy/MM/dd )
	 * 
	 * @since 13/05/2008
	 */
	public static Mask createDateYYYYMMDD() {

		return new Mask("0000/00/00");
	}

	/**
	 * @return Mascara para tempo no formato hora:minuto:segundo ( HH:MM:SS )
	 * 
	 * @since 13/05/2008
	 */
	public static Mask createTimeHHMMSS() {

		return new Mask("00:00:00");
	}

	/**
	 * @return Mascara para tempo no formato hora:minuto:segundo:milisegundo (
	 *         HH:MM:SS.sss )
	 * 
	 * @since 13/05/2008
	 */
	public static Mask createTimeHHMMSSmmm() {

		return new Mask("00:00:00.000");
	}

}
