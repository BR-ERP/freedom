/*
 * Projeto: Freedom-nfe
 * Pacote: org.freedom.modulos.nfe.event 
 * Classe: @(#)NFEEvent.java 
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 */

package org.freedom.modules.nfe.event;

import org.freedom.modules.nfe.control.AbstractNFEFactory;

/**
 * Classe evento para NFE.
 * 
 * @author Setpoint Inform�tica Ltda./Robson Sanchez
 * @version 05/08/2009
 */
public class NFEEvent {

	private AbstractNFEFactory nfefactory;

	public NFEEvent(AbstractNFEFactory nfefactory) {
		this.nfefactory = nfefactory;
	}

	public AbstractNFEFactory getNfefactory() {
		return nfefactory;
	}

	public void setNfefactory(AbstractNFEFactory nfefactory) {
		this.nfefactory = nfefactory;

	}
}
