/**
 * @version 18/05/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.library.type <BR>
 * Classe: @(#)StringCentro.java <BR>
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
 * Classe para permitir a ordena��o de textos centralizados no JTablePad.
 */

package org.freedom.library.type;

public class StringCentro implements Comparable<String> {
	private String sTexto = "";

	public StringCentro(String sTexto) {
		if (sTexto != null)
			this.sTexto = sTexto.trim();
	}

	public String toString() { 
		return sTexto;
	}

	public int compareTo(String arg0) {
		return sTexto.compareTo(arg0.toString());
	}
	
	public int compareTo(StringCentro arg0) {
		return sTexto.compareTo(arg0.toString());
	}
	
	
}
