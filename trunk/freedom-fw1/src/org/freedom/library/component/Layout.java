/**
 * @version 05/07/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.layout <BR>
 * Classe: @(#)Layout.java <BR>
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
 * Coment�rios para a classe...
 */

package org.freedom.library.component;

import org.freedom.library.business.component.NF;
import org.freedom.library.swing.frame.Aplicativo;
public class Layout extends Object {
	//public boolean bEntrada = false;
	protected TabVector cab = null;
	protected TabVector itens = null;
	protected TabVector parc = null;
	protected TabVector adic = null;
	protected TabVector frete = null;
    protected int casasDec = Aplicativo.casasDec;
    protected int casasDecFin = Aplicativo.casasDecFin;
  
public Layout() { }
	public boolean imprimir(NF nf, ImprimeOS imp) {
		cab = nf.getTabVector(NF.T_CAB);
		itens = nf.getTabVector(NF.T_ITENS);
		parc = nf.getTabVector(NF.T_PARC);
		adic = nf.getTabVector(NF.T_ADIC);
		frete = nf.getTabVector(NF.T_FRETE);
		casasDec = nf.getCasasDec();
		return false;
	}
	
}
