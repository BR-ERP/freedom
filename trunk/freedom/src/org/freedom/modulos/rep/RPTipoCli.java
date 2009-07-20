/**
 * @version 02/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPTipoCli.java <BR>
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
 * Tela para cadastro de tipos de clientes.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class RPTipoCli extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtDescTipoCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtTipo = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	public RPTipoCli() {

		super( false );
		setTitulo( "Cadastro de tipos de clientes" );		
		setAtribos( 50, 50, 410, 140 );
		
		montaTela();
		setListaCampos( true, "TIPOCLI", "RP" );
	}
	
	private void montaTela() {
		
		adicCampo( txtCodTipoCli, 7, 30, 70, 20, "CodTipoCli", "C�d.tp.cli.", ListaCampos.DB_PK, true );
		adicCampo( txtDescTipoCli, 80, 30, 250, 20, "DescTipoCli", "Descri��o do tipo de cliente", ListaCampos.DB_SI, true );
		adicCampo( txtTipo, 333, 30, 50, 20, "TipoCli", "Tipo", ListaCampos.DB_SI, false );
	}
}
