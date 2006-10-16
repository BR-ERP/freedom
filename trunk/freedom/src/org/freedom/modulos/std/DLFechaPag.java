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
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.Date;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JTextFieldPad;
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
