/*
 * Projeto: Setpoint-nfe
 * Pacote: org.freedom.modules.nfe.event
 * Classe: @(#)NFEEvent.java
 */

package org.freedom.modules.nfe.event;

import org.freedom.modules.nfe.control.AbstractNFEFactory;

/**
 * Evento de NF-e.<br>
 * 
 * Este evento de alto n�vel � acionado, quanto ocorre uma a��o da implementa��o de NF-e.<br>
 * Este evento ser� enviado a todos os ouvintes registrados pela implementa��o de NF-e.<br>
 * 
 * @see	org.freedom.modules.nfe.event.NFEListener
 * 
 * @author Setpoint Inform�tica Ltda./Robson Sanchez
 * @version 03/08/2009
 */
public class NFEEvent {

	private AbstractNFEFactory source;

	public NFEEvent( AbstractNFEFactory source ) {

		this.source = source;
	}

	public AbstractNFEFactory getSource() {

		return source;
	}
}
