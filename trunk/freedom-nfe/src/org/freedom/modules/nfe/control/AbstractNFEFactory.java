/*
 * Projeto: Freedom-nfe
 * Pacote: org.freedom.modules.nfe.control
 * Classe: @(#)AbstractNFEFactory.java
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
package org.freedom.modules.nfe.control;

import java.util.ArrayList;
import java.util.List;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modules.nfe.bean.AbstractNFEKey;
import org.freedom.modules.nfe.bean.NFEInconsistency;
import org.freedom.modules.nfe.event.NFEEvent;
import org.freedom.modules.nfe.event.NFEListener;

/**
 * Classe padr�o para implementa��o de NF-e.
 * 
 * @author Setpoint Inform�tica Ltda./Robson Sanchez
 * @version 15/07/2009
 */
public abstract class AbstractNFEFactory {

	private boolean valid = true;

	private DbConnection conSys = null;

	private DbConnection conNFE = null;

	private AbstractNFEKey key = null;

	private List<NFEInconsistency> listInconsistency;

	private final List<NFEListener> listEvent = new ArrayList<NFEListener>();

	public enum SYSTEM {
		FREEDOM
	};
	

	public AbstractNFEFactory() { }

	public boolean isValid() {
		return valid;
	}

	public void setValid( boolean valid ) {
		this.valid = valid;
	}

	public DbConnection getConSys() {
		return conSys;
	}

	public void setConSys( DbConnection conSys ) {
		this.conSys = conSys;
	}

	public DbConnection getConNFE() {
		return conNFE;
	}

	public void setConNFE( DbConnection conNFE ) {
		this.conNFE = conNFE;
	}

	public void setKey( AbstractNFEKey key ) {
		this.key = key;
	}

	public AbstractNFEKey getKey() {
		return key;
	}

	public void addInconsistency( String description, String correctiveAction ) {
		this.addInconsistency( new NFEInconsistency( NFEInconsistency.TypeInconsistency.ERROR, description, correctiveAction ) );
	}

	public void addInconsistency( NFEInconsistency inconsistency ) {
		if ( inconsistency != null ) {
			listInconsistency.add( inconsistency );
		}
	}

	public List<NFEInconsistency> getListInconsistency() {
		
		if ( this.listInconsistency == null ) {
			this.listInconsistency = new ArrayList<NFEInconsistency>();
		}
		
		return listInconsistency;
	}

	public void setListInconsistency( List<NFEInconsistency> listInconsistency ) {
		this.listInconsistency = listInconsistency;
	}

	public synchronized void addNFEListener( NFEListener event ) {
		this.listEvent.add( event );
	}

	public void removeNFEListener( NFEListener event ) {
		this.listEvent.remove( event );
	}
	
	protected abstract void validSend();
	
	protected abstract void runSend();

	public void post() {
		
		fireBeforeValidSend();
		
		validSend();
		
		fireAfterValidSend();
		
		if ( isValid() ) {

			fireBeforeRunSend();
			
			runSend();
			
			fireAfterRunSend();	
		}
	}

	private void fireBeforeValidSend() {
		
		NFEEvent event = new NFEEvent( this );
		
		for ( NFEListener obj : listEvent ) {
			obj.beforeValidSend( event );
		}
	}

	private void fireAfterValidSend() {
		
		NFEEvent event = new NFEEvent( this );
		
		for ( NFEListener obj : listEvent ) {
			obj.afterValidSend( event );
		}
	}

	private void fireBeforeRunSend() {
		
		NFEEvent event = new NFEEvent( this );
		
		for ( NFEListener obj : listEvent ) {
			obj.beforeValidSend( event );
		}
	}

	private void fireAfterRunSend() {
		
		NFEEvent event = new NFEEvent( this );
		
		for ( NFEListener obj : listEvent ) {
			obj.afterRunSend( event );
		}
	}
}
