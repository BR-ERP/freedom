/**
 * @version 17/05/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Anderso Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc.view.dialog.utility <BR>
 * Classe: @(#)DLDataCompensacao.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 * Dialog para informar a data de compensa��o dos cheques.
 */

package org.freedom.modulos.fnc.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Date;

import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLDataCompensacao extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataCompensacao = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	public DLDataCompensacao( Component cOrig ) {

		super( cOrig );
		setTitulo( "Informe a data" );
		setAtribos( 100, 100, 250, 140 );

		adic( new JLabelPad( "Data da compensa��o:" ), 7, 0, 160, 20 );
		adic( txtDataCompensacao, 7, 20, 100, 20 );
		
		txtDataCompensacao.setVlrDate( new Date() );
		
	}

	public Date getValor() {

		return txtDataCompensacao.getVlrDate();
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( txtDataCompensacao.getText().length() < 10 ) {
				Funcoes.mensagemInforma( this, "Valor invalido para data!" );
				txtDataCompensacao.requestFocus();
				return;
			}
		}
		super.actionPerformed( evt );
	}

}
