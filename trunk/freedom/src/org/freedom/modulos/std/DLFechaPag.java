/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLFechaPag.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.Date;


import org.freedom.library.JLabelPad;
import org.freedom.library.JTextFieldPad;
import org.freedom.telas.FFDialogo;

public class DLFechaPag extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtParcItPag = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDtVencItPag = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	public DLFechaPag( Component cOrig, BigDecimal bigParcItPag, Date dDtVencItPag ) {

		super( cOrig );
		setTitulo( "Parcela" );
		setAtribos( 250, 150 );

		txtParcItPag.setVlrBigDecimal( bigParcItPag );
		txtDtVencItPag.setVlrDate( dDtVencItPag );

		adic( new JLabelPad( "Valor" ), 7, 0, 100, 20 );
		adic( new JLabelPad( "Vencimento" ), 110, 0, 100, 20 );
		adic( txtParcItPag, 7, 20, 100, 20 );
		adic( txtDtVencItPag, 110, 20, 100, 20 );
	}

	public Object[] getValores() {

		Object[] oRetorno = new Object[ 2 ];
		oRetorno[ 0 ] = txtParcItPag.getVlrBigDecimal();
		oRetorno[ 1 ] = txtDtVencItPag.getVlrDate();
		return oRetorno;
	}
}
