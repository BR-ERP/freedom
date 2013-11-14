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
 * Classe de fun��es para tratamento de texto.
 */

package org.freedom.infra.functions;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringFunctions {

	public static String ltrim(String text) {
		if (text == null || "".equals(text)) {
			return "";
		}

		while (text.charAt(0) == ' ') {
			text = text.substring(1);
		}
		return text;

	}

	public static String alltrim(String text) {

		if (text == null || "".equals(text)) {
			return "";
		}

		text = ltrim(text.trim());

		return text;

	}

	public static String properCase(String text) {
		String ret = text;

		try {
			if (text != null) {

				if (text.length() > 0) {

					ret = ((text.charAt(0) + "").toUpperCase())
							+ text.toLowerCase().substring(1);

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	public static String sqlDateToStrDate(java.sql.Date d) {

		String ret = "";
		GregorianCalendar cal = new GregorianCalendar();

		int iDia = cal.get(Calendar.DAY_OF_MONTH);
		int iMes = cal.get(Calendar.MONTH) + 1;
		int iAno = cal.get(Calendar.YEAR);

		if (d == null) {
			return ret;
		}

		try {

			cal.setTime(d);

			iDia = cal.get(Calendar.DAY_OF_MONTH);
			iMes = cal.get(Calendar.MONTH) + 1;
			iAno = cal.get(Calendar.YEAR);

			ret = StringFunctions.strZero("" + iDia, 2) + "/"
					+ StringFunctions.strZero("" + iMes, 2) + "/" + iAno;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;

	}

	public static String clearString(String str) {
		return clearString(str, "");
	}

	public static String clearStringOld(String str) {

		String sResult = "";
		String sCaracs = "=<>-.,;/\\?";

		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				if (sCaracs.indexOf(str.substring(i, i + 1)) == -1)
					sResult = sResult + str.substring(i, i + 1);
			}
		}
		return sResult;
	}

	public static String clearString(String str, String strnew) {

		if (str == null) {
			return "";
		}

		StringBuilder validstring = new StringBuilder();

		validstring.append(str.replaceAll("\\W", strnew));

		return validstring.toString().trim();

	}

	public static String replicate(String text, int times) {

		StringBuffer ret = new StringBuffer("");

		try {
			for (int i = 0; i < times; i++) {
				ret.append(text);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret.toString();

	}

	public static String strZero(String text, int times) {

		String ret = null;

		try {
			if (text == null) {
				ret = replicate("0", times);
			} else if (text.length() > times) {
				return text.substring(0, times);
			} else {
				ret = replicate("0", times - text.trim().length());
				ret += text.trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;

	}

	public static char clearAccent(char cKey) {

		char cTmp = cKey;

		if (isContained(cTmp, "�����"))
			cTmp = 'a';
		else if (isContained(cTmp, "�����"))
			cTmp = 'A';
		else if (isContained(cTmp, "����"))
			cTmp = 'e';
		else if (isContained(cTmp, "����"))
			cTmp = 'E';
		else if (isContained(cTmp, "����"))
			cTmp = 'i';
		else if (isContained(cTmp, "����"))
			cTmp = 'I';
		else if (isContained(cTmp, "�����"))
			cTmp = 'o';
		else if (isContained(cTmp, "�����"))
			cTmp = 'O';
		else if (isContained(cTmp, "����"))
			cTmp = 'u';
		else if (isContained(cTmp, "����"))
			cTmp = 'U';
		else if (isContained(cTmp, "�"))
			cTmp = 'c';
		else if (isContained(cTmp, "�"))
			cTmp = 'C';
		else if (isContained(cTmp, "&"))
			cTmp = 'E';
		return cTmp;
	}

	public static boolean isContained(char cTexto, String sTexto) {
		boolean bRetorno = false;
		for (int i = 0; i < sTexto.length(); i++) {
			if (cTexto == sTexto.charAt(i)) {
				bRetorno = true;
				break;
			}
		}
		return bRetorno;
	}

	public static String clearAccents(String sTexto) {
		if (sTexto == null) {
			return "";
		}

		String sRet = "";
		char cVals[] = sTexto.toCharArray();

		for (int i = 0; i < cVals.length; i++) {
			cVals[i] = clearAccent(cVals[i]);
		}

		sRet = new String(cVals);

		return sRet;
	}

	public static String[] REPLACES = { "a", "e", "i", "o", "u", "c", "A", "E",
			"I", "O", "U", "C" };

	public static Pattern[] PATTERNS = null;

	public static void compilePatterns() {
		PATTERNS = new Pattern[REPLACES.length];
		PATTERNS[0] = Pattern.compile("[�����]");
		PATTERNS[1] = Pattern.compile("[����]");
		PATTERNS[2] = Pattern.compile("[����]");
		PATTERNS[3] = Pattern.compile("[�����]");
		PATTERNS[4] = Pattern.compile("[����]");
		PATTERNS[5] = Pattern.compile("[�]");
		PATTERNS[6] = Pattern.compile("[�����]");
		PATTERNS[7] = Pattern.compile("[����]");
		PATTERNS[8] = Pattern.compile("[����]");
		PATTERNS[9] = Pattern.compile("[�����]");
		PATTERNS[10] = Pattern.compile("[����]");
		PATTERNS[11] = Pattern.compile("[�]");
	}

	public static String tiraCaracEspecias(String text) {
		if (PATTERNS == null) {
			compilePatterns();
		}

		String result = text;
		for (int i = 0; i < PATTERNS.length; i++) {
			Matcher matcher = PATTERNS[i].matcher(result);
			result = matcher.replaceAll(REPLACES[i]);
		}
		return result;
	}

}
