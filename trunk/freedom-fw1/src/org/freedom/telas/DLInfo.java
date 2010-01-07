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

package org.freedom.telas;

import java.util.Date;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JTextFieldFK;

import org.freedom.componentes.JTextFieldPad;

public class DLInfo extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldFK txtDtins = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldFK txtHins = new JTextFieldFK( JTextFieldPad.TP_TIME, 10, 0 );
	
	private JTextFieldFK txtUsuIns = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JTextFieldFK txtDtAlt = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldFK txtHAlt = new JTextFieldFK( JTextFieldPad.TP_TIME, 10, 0 );
	
	private JTextFieldFK txtUsuAlt = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );
	
	private FDados telaorig = null;
	
	public DLInfo( FDados cOrig, Date dtins, Date dtalt, Date hins, Date halt, String idins, String idalt ) {

		super( cOrig );
		
		setTitulo( "Informa��es do registro" ); 
		
		setAtribos( 270, 180 );

		txtDtins.setVlrDate( dtins );
		txtHins.setVlrTime( hins );
		
		txtDtAlt.setVlrDate( dtalt );
		txtHAlt.setVlrTime( halt );
		
		txtUsuIns.setVlrString(idins);
		txtUsuAlt.setVlrString(idalt);

		adic( new JLabelPad( "Data e hora da inser��o" ), 7, 0, 200, 20 );		
		adic( new JLabelPad( "Usu�rio" ), 150, 0, 100, 20 );
				
		adic( txtDtins, 7, 20, 70, 20 );
		adic( txtHins, 77, 20, 70, 20 );
		adic( txtUsuIns, 150, 20, 100, 20 );

		adic( new JLabelPad( "Data e hora da altera��o" ), 7, 40, 200, 20 );
		adic( new JLabelPad( "Usu�rio" ), 150, 40, 100, 20 );
		
		adic( txtDtAlt, 7, 60, 70, 20 );
		adic( txtHAlt, 77, 60, 70, 20 );
		adic( txtUsuAlt, 150, 60, 100, 20 );

		telaorig = (FDados) telaorig;
	}
	

}
