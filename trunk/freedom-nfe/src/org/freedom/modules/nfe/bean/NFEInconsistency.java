/*
 * Projeto: Freedom-nfe
 * Pacote: org.freedom.modules.nfe.event
 * Classe: @(#)NFEListener.java
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
package org.freedom.modules.nfe.bean;

/**
 * Classe para defini��o de incosist�ncia na NF-e.<br>
 * 
 * @see	org.freedom.modules.nfe.control.AbstractNFEFactory
 * 
 * @author Setpoint Inform�tica Ltda./Robson Sanchez
 * @version 10/08/2009
 */
public class NFEInconsistency {
	
	private TypeInconsistency typeInconsistency;
	
	private String description;
	
	private String correctiveAction;
	
	private String field;
	
	private String valueField;
	
	
	public enum TypeInconsistency {
		
		WARNNIG, ERROR, MESSAGE;
	}

	public NFEInconsistency( TypeInconsistency typeInconsistency, String description ) {
		setTypeInconsistency( typeInconsistency );
		setDescription( description );
	}

	public NFEInconsistency( TypeInconsistency typeInconsistency, String description, String correctiveAction ) {
		setTypeInconsistency( typeInconsistency );
		setDescription( description );
		setCorrectiveAction( correctiveAction );
	}

	public NFEInconsistency( TypeInconsistency typeInconsistency, String description, String correctiveAction, String field, Object valueField ) {
		setTypeInconsistency( typeInconsistency );
		setDescription( description );
		setCorrectiveAction( correctiveAction );
		setField( field );
		setValueField( String.valueOf( valueField ) );
	}

	private void setTypeInconsistency( TypeInconsistency typeInconsistency ) {
		this.typeInconsistency = typeInconsistency;
	}

	public TypeInconsistency getTypeInconsistency() {
		return typeInconsistency;
	}

	private void setDescription( String description ) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	private void setCorrectiveAction( String correctiveAction ) {
		this.correctiveAction = correctiveAction;
	}

	public String getCorrectiveAction() {
		return correctiveAction;
	}

	public String getField() {
		return field;
	}

	public NFEInconsistency setField( String field ) {
		this.field = field;
		return this;
	}

	public String getValueField() {
		return valueField;
	}

	public NFEInconsistency setValueField( String valueField ) {
		this.valueField = valueField;
		return this;
	}
}
