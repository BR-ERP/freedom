/**		
 * Este programa � licenciado de acordo com a LGPL (Lesser General Public License), <br>
 * vers�o 2.1, Fevereiro de 1999 <br>
 * A LGPL deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <br>
 * Caso uma c�pia da LGPL n�o esteja dispon�vel junto com este Programa, voc� pode contatar <br>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <a href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt> Creative Commons</a> <br>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este programa � preciso estar de acordo com os termos da LGPL. <br>
 * <br>
 * <br>
 * Classe para mapear as bandeiras e suas respectivas classes de fun��es TEF.<br>
 * <br>
 * <br>
 * @author Setpoint Inform�tica Ltda. / Alex Rodrigues <br>
 * @version 1.0 (beta), 05/03/2008 <br>
 */

package org.freedom.tef.driver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.freedom.tef.driver.text.TextTef;

public class Flag {

	private static Map<String, Class<TextTef>> textTefFlagsMap = null;

	private static boolean loadTextTefFlagsMaps = false;

	/**
	 * 
	 * @return Mapa de [ Nome de Bandeira ] X [ Classe de fun��es TEF ].
	 */
	public static Map<String, Class<TextTef>> getTextTefFlagsMap() {

		if ( textTefFlagsMap == null ) {
			textTefFlagsMap = new HashMap<String, Class<TextTef>>();
		}
		return textTefFlagsMap;
	}

	/**
	 * 
	 * @return Retorna se os mapas foram carregados.
	 */
	public static boolean isLoadTextTefFlagsMaps() {

		return loadTextTefFlagsMaps;
	}

	public static void setLoadTextTefFlagsMaps( final boolean loadTextTefFlagsMaps ) {

		Flag.loadTextTefFlagsMaps = loadTextTefFlagsMaps;
	}

	/**
	 * Sobrecarrega {@link #loadParametrosOfInitiation(File)} <br>
	 * para facilitar chamada utilizando o caminho para o arquivo de inicializa��o.<br>
	 * 
	 * @param fileParametrosOfInitiation
	 *            Caminho para arquivo de inicializa��o.
	 * @throws Exception
	 *             Repassa qualquer exce��o para possibilitar tratamento pela aplica��o.
	 */
	public static void loadParametrosOfInitiation( final String fileParametrosOfInitiation ) throws Exception {

		loadParametrosOfInitiation( new File( fileParametrosOfInitiation ) );
	}

	/**
	 * Carrega o arquivo de inicializa��o para mapeamento das bandeiras.
	 * 
	 * @param fileParametrosOfInitiation
	 *            Arquivo para leitura do mapeamento.
	 * @throws Exception
	 *             Repassa qualquer exce��o para possibilitar tratamento pela aplica��o.
	 */
	public static void loadParametrosOfInitiation( final File fileParametrosOfInitiation ) throws Exception {

		if ( fileParametrosOfInitiation == null || !fileParametrosOfInitiation.exists() ) {
			throw new NullPointerException( "Arquivo de mapeamento de bandeiras n�o especificado!" );
		}
		loadTextTefFlagsMap( fileParametrosOfInitiation );
	}

	/**
	 * Le o arquivo de mapeamento das bandeiras e guarda em um java.util.Map<String,Class<TextTef>> <br>
	 * que prover� as classes para instanciar os objetos de fun��es TEF conforme a Bandeira.
	 * 
	 * @param fileParametrosOfInitiation
	 *            Arquivo para leitura do mapeamento.
	 * @throws Exception
	 *             Repassa qualquer exce��o para possibilitar tratamento pela aplica��o.
	 */
	@SuppressWarnings( "unchecked" )
	private static void loadTextTefFlagsMap( final File fileParametrosOfInitiation ) throws Exception {

		setLoadTextTefFlagsMaps( false );
		FileReader reader = new FileReader( fileParametrosOfInitiation );
		BufferedReader buffered = new BufferedReader( reader );
		if ( buffered != null ) {
			String line = "";
			String name = null;
			String sclass = null;
			int is = -1;
			while ( ( line = buffered.readLine() ) != null ) {
				line = line.trim();
				if ( line.length() == 0 ) {
					continue;
				}
				else if ( '#' == line.charAt( 0 ) ) {
					continue;
				}
				else if ( line.length() > 1 && ( '/' == line.charAt( 0 ) && '/' == line.charAt( 1 ) ) ) {
					continue;
				}
				is = line.indexOf( '=' );
				if ( is > -1 ) {
					name = line.substring( 0, is );
					sclass = line.substring( is + 1 );
					try {
						getTextTefFlagsMap().put( name, (Class<TextTef>) Class.forName( sclass ) );
					}
					catch ( ClassNotFoundException e ) {
						System.out.println( "[  ERROR  ] " + e.getMessage() );
					}
				}
			}
			setLoadTextTefFlagsMaps( getTextTefFlagsMap().size() > 0 );
		}
	}
}
