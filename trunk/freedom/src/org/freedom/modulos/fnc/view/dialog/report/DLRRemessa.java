/**
 * @version 18/05/2005 <BR>
 * @author Setpoint Inform�tica Ltda / Robson Sanchez.
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc.view.dialog.report <BR>
 * Classe:
 * @(#)DLRRemessa.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Dialog para escolha do formato de impress�o do relat�rio de remessa de cobran�a.
 * 
 */

package org.freedom.modulos.fnc.view.dialog.report;

import java.awt.Component;
import java.util.Vector;

import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLRRemessa extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JRadioGroup<?, ?> rgFormato = null;

	private JLabelPad lbFormato = new JLabelPad( "Formato:" );

	private Vector<String> vLabs = new Vector<String>();

	private Vector<String> vVals = new Vector<String>();

	public DLRRemessa( Component cOrig ) {

		super( cOrig );
		
		setTitulo( "Relat�rio de Remessa" );
		setAtribos( 291, 160 );

		vLabs.addElement( "Retrato" );
		vLabs.addElement( "Paisagem" );

		vVals.addElement( "R" );
		vVals.addElement( "P" );
		
		rgFormato = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgFormato.setVlrString( "R" );
		
		adic( lbFormato, 7, 10, 80, 15 );
		adic( rgFormato, 7, 30, 264, 30 );

	}

	public String getFormato() {

		return rgFormato.getVlrString();
	}
	
}
