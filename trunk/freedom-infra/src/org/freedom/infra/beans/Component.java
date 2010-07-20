package org.freedom.infra.beans;

/**
 * Projeto: <a
 * href="http://sourceforge.net/projects/freedom-erp/">Freedom-infra</a> <br>
 * Este programa � licenciado de acordo com a LPG-PC <br>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada
 * pela Funda��o do Software Livre (FSF); <BR>
 * <br>
 * 
 * Interface para padroniza��o de componentes que
 * 
 * <br>
 * 
 * @author Alex Rodrigues
 * @version 0.0.1 � 16/05/2008
 * 
 * @since 16/05/2008
 */
public interface Component {

	void setValue(Object value);

	Object getValue();

	Field getField();

}
