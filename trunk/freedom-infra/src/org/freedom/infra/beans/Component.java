package org.freedom.infra.beans;

/**
 * Projeto: <a href="http://sourceforge.net/projects/freedom-erp/">Freedom-infra</a> <br>
 * Este programa � licenciado de acordo com a LPG-PC <br>
 * (Licen�a P�blica Geral para Programas de Computador) vers�o 2.1.0 ou qualquer vers�o posterior. <br>
 * <br>
 *
 * Interface para padroniza��o de componentes que 
 * 
 * <br>
 * @author 		Alex Rodrigues
 * @version 	0.0.1 � 16/05/2008
 * 
 * @since 		16/05/2008
 */
public interface Component {
	
	void setValue( Object value );
	
	Object getValue();
	
	Field getField();

}
