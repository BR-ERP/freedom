/**
 * @version 15/07/2009 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.nfe.control <BR>
 * Classe: @(#)AbstractNFEFactory <BR>
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
 * Classe padr�o para API NFE.
 */

package org.freedom.modules.nfe.control;

import java.util.ArrayList;
import java.util.List;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modules.nfe.event.NFEEvent;
import org.freedom.modules.nfe.event.NFEListener;

public abstract class AbstractNFEFactory implements NFEListener{

	private boolean valid = true;

	private DbConnection conSis = null;
	
	private DbConnection conNFE = null;
	
	private List<NFEListener> listEvent = new ArrayList<NFEListener>();
	
	public AbstractNFEFactory() {
		addNFEListener( this );
	}

	private void fireValidSend() {
		NFEEvent event = new NFEEvent(this);
		for (NFEListener obj: listEvent) {
			obj.beforeValidSend(event);
		}
		for (NFEListener obj: listEvent) {
			obj.validSend(event);
		}
		for (NFEListener obj: listEvent) {
			obj.afterValidSend(event);
		}
	}
	
	private void fireRunSend() {
		NFEEvent event = new NFEEvent(this);
		for (NFEListener obj: listEvent) {
			obj.beforeRunSend(event);
		}
		for (NFEListener obj: listEvent) {
			obj.runSend(event);
		}
		for (NFEListener obj: listEvent) {
			obj.afterRunSend(event);
		}
	}
	
	public void post() {
		fireValidSend();
		fireRunSend();
	}

	public void addNFEListener(NFEListener event) {
		this.listEvent.add(event);
	}

	public void removeNFEListener(NFEListener event) {
		this.listEvent.remove(event);
	}
	
	public boolean isValid() {
	
		return valid;
	}

	
	public void setValid( boolean valid ) {
	
		this.valid = valid;
	}

	public DbConnection getConSis() {
		return conSis;
	}

	public void setConSis(DbConnection conSis) {
		this.conSis = conSis;
	}

	public DbConnection getConNFE() {
		return conNFE;
	}

	public void setConNFE(DbConnection conNFE) {
		this.conNFE = conNFE;
	}
}
