/**
 * @version 20/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLRClasCli.java <BR>
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
 *                     Coment�rios sobre a classe...
 */

package org.freedom.modulos.std.view.dialog.report;

import java.awt.Component;
import java.util.Vector;

import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLRTipoRestr extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JRadioGroup<?, ?> rgOrdem = null;

	private JRadioGroup<?, ?> rgTipo = null;

	private JLabelPad lbOrdem = new JLabelPad( "Ordenar por:" );

	public DLRTipoRestr( Component cOrig ) {

		super( cOrig );
		setTitulo( "Ordem do Relat�rio" );
		setAtribos( 300, 190 );

		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();

		vLabs.addElement( "C�digo" );
		vLabs.addElement( "Descri��o" );
		vVals.addElement( "C" );
		vVals.addElement( "D" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgOrdem.setVlrString( "D" );

		Vector<String> vLabs1 = new Vector<String>();
		Vector<String> vVals1 = new Vector<String>();

		vVals1.addElement( "G" );
		vVals1.addElement( "T" );
		vLabs1.addElement( "Grafico" );
		vLabs1.addElement( "Texto" );
		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs1, vVals1 );
		rgTipo.setVlrString( "G" );

		adic( lbOrdem, 7, 0, 80, 15 );
		adic( rgOrdem, 7, 20, 270, 30 );
		adic( rgTipo, 7, 60, 270, 30 );
	}

	public String getOrdem() {

		String sRetorno = "DESCTPRESTR";

		if ( rgOrdem.getVlrString().compareTo( "C" ) == 0 ) {
			sRetorno = "CODTPRESTR";
		}

		return sRetorno;
	}

	public String getTipo() {

		return rgTipo.getVlrString();
	}

}
