/**		
 * Este programa � licenciado de acordo com a LGPL (Lesser General Public License), <br>
 * vers�o 2.1, Fevereiro de 1999 <br>
 * A LGPL deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <br>
 * Caso uma c�pia da LGPL n�o esteja dispon�vel junto com este Programa, voc� pode contatar <br>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <a href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt> Creative Commons</a> <br>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este programa � preciso estar de acordo com os termos da LGPL. <br>
 * <br>
 * <br>
 * Classe para obten��o de objetos TextTef especifico da Bandeira.<br>
 * <br>
 * <br>
 * @author Setpoint Inform�tica Ltda. / Alex Rodrigues <br>
 * @version 1.0 (beta), 05/03/2008 <br>
 * <br>
 * @see org.freedom.tef.text.TextTef <br>
 * @see org.freedom.tef.text.TestTextTefProperties <br>
 * @see org.freedom.tef.Flag <br>
 */

package org.freedom.tef.driver.text;

import java.io.File;

import org.freedom.tef.driver.Flag;

public final class TextTefFactory {

	/**
	 * Sobrecarrega {@link #createTextTef(TextTefProperties, String, File)} <br>
	 * Deve ser efetuado o mapeamento das Flags anteriormente. <br>
	 * 
	 * @see #createTextTef(TextTefProperties, String, File)
	 * 
	 * @param textTefProperties
	 *            Parametros para a classe de fun��o TEF.
	 * @param flagName
	 *            Bandeira
	 * @return Classe de fun��es TEF especifica da Bandeira.
	 * @throws Exception
	 *             Repassa qualquer exce��o para possibilitar tratamento pela aplica��o.
	 */
	public static TextTef createTextTef( final TextTefProperties textTefProperties, final String flagName ) throws Exception {

		return createTextTef( textTefProperties, flagName, (File) null );
	}

	/**
	 * Sobrecarrega {@link #createTextTef(TextTefProperties, String, File)} <br>
	 * 
	 * @see #createTextTef(TextTefProperties, String, File)
	 * 
	 * @param textTefProperties
	 *            Parametros para a classe de fun��o TEF.
	 * @param flagName
	 *            Bandeira
	 * @param fileParametrosOfInitiation
	 *            Caminho para o arquivo para inicia��o do mapa de Bandeiras X Classe de fun��o TEF,<br>
	 *            caso ainda n�o se tenha mapeado.
	 * @return Classe de fun��es TEF especifica da Bandeira.
	 * @throws Exception
	 *             Repassa qualquer exce��o para possibilitar tratamento pela aplica��o.
	 */
	public static TextTef createTextTef( final TextTefProperties textTefProperties, final String flagName, final String fileParametrosOfInitiation ) throws Exception {

		return createTextTef( textTefProperties, flagName, new File( fileParametrosOfInitiation ) );
	}

	/**
	 * Cria um objeto de fun��es TEF especifico para a Bandeira.
	 * 
	 * @param textTefProperties
	 *            Parametros para a classe de fun��o TEF.
	 * @param flagName
	 *            Bandeira
	 * @param fileParametrosOfInitiation
	 *            Arquivo para inicia��o do mapa de Bandeiras X Classe de fun��o TEF,<br>
	 *            caso ainda n�o se tenha mapeado.
	 * @return Classe de fun��es TEF especifica da Bandeira.
	 * @throws Exception
	 *             Repassa qualquer exce��o para possibilitar tratamento pela aplica��o.
	 */
	public static TextTef createTextTef( final TextTefProperties textTefProperties, final String flagName, final File fileParametrosOfInitiation ) throws Exception {

		TextTef textTef = null;
		if ( !Flag.isLoadTextTefFlagsMaps() ) {
			// N�o � necess�rio tratar fileParametrosOfInitiation,
			// pois Flag.loadParametrosOfInitiation j� o faz.
			Flag.loadParametrosOfInitiation( fileParametrosOfInitiation );
		}
		Class<TextTef> classTextTef = Flag.getTextTefFlagsMap().get( flagName );
		if ( classTextTef != null ) {
			try {
				textTef = classTextTef.newInstance();
				if ( textTef != null ) {
					textTef.initializeTextTef( textTefProperties );
				}
			}
			catch ( ClassCastException e ) {
				System.out.println( "[  ERROR  ] " + e.getMessage() );
				Flag.getTextTefFlagsMap().remove( flagName );
			}
			catch ( Exception e ) {
				System.out.println( "[  ERROR  ] " + e.getMessage() );
				throw new Exception( "N�o foi possiv�l criar objeto para TEF!\n" + e.getMessage(), e );
			}
		}
		else {
			throw new Exception( "Classe do objeto para TEF para a bandeira " + flagName + " n�o encontrada!" );
		}
		return textTef;
	}
}
