/**
 * @version 24/06/2009 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLCancItem.java <BR>
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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;

import org.freedom.funcoes.Funcoes;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.telas.FFDialogo;

public class DLCancItem extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private final JTextAreaPad txaObs = new JTextAreaPad(250);
	private JScrollPane spn = new JScrollPane( txaObs );
	
	public DLCancItem( Component cOrig ) {

		super( cOrig );
		setTitulo( "Motivo do Cancelamento" );
		setAtribos( 360, 250 );
		montaTela();
		
	}

	private void montaTela() {
		c.add( spn, BorderLayout.CENTER );
	}

	public String getValor() {
		return txaObs.getVlrString();
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK && "".equals(txaObs.getVlrString().trim()) ) {
			Funcoes.mensagemInforma( this, "Motivo do cancelamento requerido!" );
		}
		else {
			super.actionPerformed( evt );
		}
	}

}
