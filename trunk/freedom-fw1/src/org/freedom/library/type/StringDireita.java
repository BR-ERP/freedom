/**
 * @version 19/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)StringDireita.java <BR>
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

package org.freedom.library.type;

import java.math.BigDecimal;

public class StringDireita implements Comparable<String> {
	private String text = "";

	public StringDireita(String text) {
		if (text != null)
			this.text = text.trim();
	}

	public String toString() {
		return text;
	}

	public int compareTo(String arg0) {
		return text.compareTo(arg0.toString());
	}
	
	public BigDecimal getBigDecimal() {
		BigDecimal result = null;
		if (text!=null && !"".equals(text.trim())) {
			String str = text.trim().replace(',', '.');
			int pos = str.lastIndexOf('.');
			if (pos>-1) {
				String str1 = str.substring(0, pos);
				String str2 = str.substring(pos);
				str = str1.replace(".","")+str2;
			} 
			result = new BigDecimal(str);
		}
		return result;
	}
	
}
