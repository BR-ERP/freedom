/**
 * @version 04/03/2013 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.dialog.utility <BR>
 *         Classe:
 * @(#)DLSelLayout.java <BR>
 * 
 *                     Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                     na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                     Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                     Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                     Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Classe de sele��o de layouts
 */

package org.freedom.modulos.std.view.dialog.utility;

import java.awt.Component;
import java.util.Vector;

import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLSelLayout extends FFDialogo {

	private Component owner = null;

	private static final long serialVersionUID = 1L;

	private JRadioGroup<String, String> rgLayout = null;
	
	private Vector<String> labLayout = new Vector<String>();
	private Vector<String> valLayout = new Vector<String>();

	public DLSelLayout( Component cOrig, String class01, String class02 ) {
		super( cOrig );
		setTitulo( "Sele��o de layout" );
		setAtribos( 250, 150 );
		labLayout.addElement( class01 );
		labLayout.addElement( class02 );
		valLayout.addElement( class01 );
		valLayout.addElement( class02 );
		rgLayout = new JRadioGroup<String, String>( 2, 1, labLayout, valLayout );
		adic( rgLayout, 7, 7, 100, 40 );
	}

	public String getLayoutSel() {
		return rgLayout.getVlrString();
	}
}
