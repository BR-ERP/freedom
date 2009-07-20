package org.freedom.infra.beans;

import java.math.BigDecimal;

import org.freedom.infra.util.text.Mask;

/**
 * Projeto: <a href="http://sourceforge.net/projects/freedom-erp/">Freedom-infra</a> <br>
 * Este programa � licenciado de acordo com a LPG-PC <br>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * <br>
 *
 * Est� classe representa um campo e deve trabalhar como um objeto de transi��o para o valor 
 * assossiado a um <code>Component</code>.
 * 
 * @see			Component
 * 
 * @author 		Alex Rodrigues
 * @version 	0.0.2 � 13/06/2008
 * 
 * @since 		16/05/2008
 */
public class Field {
	
	/**
	 * Refer�ncia ao valor do campo.
	 */
	private Object value;
	
	/**
	 * Mascara aplicada ao valor do campo na apresenta��o do mesmo.
	 */
	private Mask mask;
	
		
	public void setValue( final Object value ) {	
		this.value = value;
	}
	
	public Object getValue() {	
		return value;
	}
		
	public Mask getMask() {	
		return mask;
	}
	
	public void setMask( final Mask mask ) {	
		this.mask = mask;
	}
	
	public String toString() {
		return value.toString();
	}

	public Integer toInteger() {
		
		Integer integer = null;
		try {
			if ( value != null ) {
				integer = new Integer( value.toString() );
			}
		}
		catch ( NumberFormatException e ) {
			e.printStackTrace();
		}
		
		return integer ;
	}

	public BigDecimal toBigDecimal() {
		
		BigDecimal bigdecimalvalue = null;
		try {
			if ( value != null ) {
				bigdecimalvalue = new BigDecimal( value.toString().replace( "\\.", "" ).replace( ',', '.' ) );
			}
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return bigdecimalvalue ;
	}

}
