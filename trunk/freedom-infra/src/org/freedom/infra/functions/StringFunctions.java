/**
 * @version 01/03/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez
 *         <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.funcoes <BR>
 * Classe:
 * @(#)Funcoes.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para
 * Programas de Computador), <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Classe de fun��es de tratamento de texto.
 */

package org.freedom.infra.functions;


public final class StringFunctions {

	public static String ltrim( String text) {
		if( text==null || "".equals(text)) {
			return "";
		}
		
		while (text.charAt(0) == ' ') { 
			text = text.substring(1);
		}
		return text;
		
	}
	
	public static String alltrim(String text) {
		
		if( text==null || "".equals(text)) {
			return "";
		}
		
		text = ltrim(text.trim());
		
		return text;
		
	}
	
	public static String clearString(String str) {
		
		String sResult = "";
		String sCaracs = "=<>- .,;/\\";
		
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				if (sCaracs.indexOf(str.substring(i, i + 1)) == -1)
					sResult = sResult + str.substring(i, i + 1);
			}
		}
		return sResult;
	}

	

}

