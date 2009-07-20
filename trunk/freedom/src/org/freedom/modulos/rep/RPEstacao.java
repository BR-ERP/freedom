/**
 * @version 02/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPEstacao.java <BR>
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
 * Tela de cadastros de esta��es de trabalho.
 * 
 */

package org.freedom.modulos.rep;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class RPEstacao extends FDados {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtDescEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	

	public RPEstacao() {

		super( false );
		setTitulo( "Esta��o de trabalho" );
		setAtribos( 50, 50, 420, 140 );
		
		montaTela();		
		setListaCampos( true, "ESTACAO", "SG" );
	}
	
	private void montaTela() {
		
		adicCampo( txtCodEst, 7, 30, 80, 20, "CodEst", "C�d.est.", ListaCampos.DB_PK, true );
		adicCampo( txtDescEst, 90, 30, 305, 20, "DescEst", "Descri��o da esta��o de trabalho", ListaCampos.DB_SI, true );
	}
}
