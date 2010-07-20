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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public final class ConversionFunctions {

	public static BigDecimal stringToBigDecimal(Object vlr) {
		BigDecimal retorno = null;
		if (vlr == null) {
			retorno = new BigDecimal(0);
		}
		else {
			retorno = stringCurrencyToBigDecimal(vlr.toString());
		}
		return retorno;
	}

	public static String dateToStrTime(Date dVal) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dVal);
		int iHora = cal.get(Calendar.HOUR_OF_DAY);
		int iMinuto = cal.get(Calendar.MINUTE);
		int iSegundo = cal.get(Calendar.SECOND);
		return StringFunctions.strZero(String.valueOf(iHora), 2) + ":" + StringFunctions.strZero(String.valueOf(iMinuto), 2) + ":" + iSegundo;
	}

	public static File blobToFile(String FileName, Blob blob) {

		File file = null;
		InputStream input = null;

		try {

			input = blob.getBinaryStream();
			file = new File(FileName);
			FileOutputStream outStream = new FileOutputStream(file);

			int length = -1;
			int size = input.available();
			byte[] buffer = new byte[size];

			// Queimando o arquivo no disco

			while (( length = input.read(buffer) ) != -1) {
				outStream.write(buffer, 0, length);
				outStream.flush();
			}

			input.close();
			outStream.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro gera��o do arquivo para convers�o.");
		}

		return file;

	}

	public static BigDecimal stringCurrencyToBigDecimal(String strvalue) {

		BigDecimal retvalue = new BigDecimal("0");

		try {

			if (strvalue == null) {
				return new BigDecimal("0");
			}

			int pospoint = strvalue.indexOf('.');

			if (pospoint > -1) {
				strvalue = strvalue.substring(0, pospoint) + strvalue.substring(pospoint + 1);
			}

			char[] charvalue = strvalue.toCharArray();

			int iPos = strvalue.indexOf(",");

			if (iPos >= 0) {
				charvalue[iPos] = '.';
			}

			strvalue = new String(charvalue);

			retvalue = new BigDecimal(strvalue.trim());

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return retvalue;
	}

	public static Date strDate6digToDate(String strdate) {

		GregorianCalendar cal = new GregorianCalendar();

		if (strdate.trim().length() == 8) {

			strdate = strdate.trim();

			try {

				int day = Integer.parseInt(strdate.substring(0, 2));

				int mounth = Integer.parseInt(strdate.substring(3, 5)) - 1;

				int year = Integer.parseInt(strdate.substring(6));

				cal = ( GregorianCalendar ) GregorianCalendar.getInstance();

				String milenio = ( cal.get(Calendar.YEAR) + "" ).substring(0, 2);

				year = Integer.parseInt(milenio + year);

				cal = new GregorianCalendar(year, mounth, day);

			}
			catch (Exception err) {
				err.printStackTrace();
				cal = null;
			}

		}
		else {
			cal = null;
		}
		if (cal == null) {
			return null;
		}

		return cal.getTime();

	}

	public static Time strTimetoTime(String strtime) {

		Time time = null;

		try {

			strtime = StringFunctions.clearString(strtime);

			int hours = Integer.parseInt(strtime.substring(0, 2));
			int minutes = Integer.parseInt(strtime.substring(2, 4));
			int seconds = 0;

			if (strtime.length() > 4) {

				seconds = Integer.parseInt(strtime.substring(4));

			}

			Calendar cal = new GregorianCalendar();
			cal = Calendar.getInstance();

			cal.set(Calendar.HOUR_OF_DAY, hours);
			cal.set(Calendar.MINUTE, minutes);
			cal.set(Calendar.SECOND, seconds);

			time = new Time(cal.getTimeInMillis());

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return time;

	}

	public static String XMLDocumentToString(Document doc) {
		String ret = null;

		try {

			// Pegando o XML e transformando em String.

			// set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");

			// create string from xml tree
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);
			ret = sw.toString();

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return ret;

	}

}
