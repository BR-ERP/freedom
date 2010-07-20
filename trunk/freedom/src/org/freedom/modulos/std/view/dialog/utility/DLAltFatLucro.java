/**
 * @version 01/09/2009 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)DLAltComisVenda.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Coment�rios sobre a classe...
 */

package org.freedom.modulos.std.view.dialog.utility;

import java.awt.Component;
import java.math.BigDecimal;

import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLAltFatLucro extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtFatLucr = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private int iCodVenda = 0;

	public DLAltFatLucro( Component cOrig, BigDecimal fatLucro ) {

		super( cOrig );

		setTitulo( "Altera��o de fator de lucratividade" );
		setAtribos( 250, 130 );

		if ( fatLucro != null ) {
			txtFatLucr.setVlrBigDecimal( fatLucro );
		}

		adic( new JLabelPad( "Fator" ), 7, 0, 133, 20 );
		adic( txtFatLucr, 7, 20, 140, 20 );

	}

	public BigDecimal getValor() {

		return txtFatLucr.getVlrBigDecimal();
	}
}
