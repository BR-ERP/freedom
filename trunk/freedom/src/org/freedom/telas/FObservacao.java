/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FObservacao.java <BR>
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
 * Classe para implementa��o de observa��es..<BR>
 * ATEN��O!! ESTA CLASSE � DERIVADA DE FDialogo E N�O FFDialogo.
 */
package org.freedom.telas;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;

public class FObservacao extends FDialogo {

	private static final long serialVersionUID = 1L;

	protected JPanelPad pn = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	public JTextAreaPad txa = new JTextAreaPad();

	private JScrollPane spn = new JScrollPane( txa );

	/**
	 * 
	 * Construtor sem titulo.
	 * 
	 * @param sPad -
	 *            Texto inicial.
	 */
	public FObservacao( String sPad ) {

		this( null, sPad, 0 );
	}

	/**
	 * 
	 * Construtor com titulo.
	 * 
	 * @param sTit -
	 *            T�tulo da janela.
	 * @param sPad -
	 *            Texto inicial.
	 */
	public FObservacao( String sTit, String sPad ) {

		this( sTit, sPad, 0 );
	}

	public FObservacao( String sTit, String sPad, int iTam ) {

		if ( sTit != null ) {
		
			setTitulo( sTit, this.getClass().getName() );
		}
		else {
		
			setTitulo( "Observa��o", this.getClass().getName() );
		}
		
		setAtribos( 400, 200 );
		
		pn.add( spn, BorderLayout.CENTER );
		
		c.add( pn );
		
		txa.setText( sPad );
		
		if ( iTam > 0 ) {

			txa.iTamanho = iTam;	
		}
	}

	/**
	 * 
	 * Retorna o texto digitado na dialog de observa��o.
	 * 
	 * @return sTexto - Texto digitado.
	 * 
	 */
	public String getTexto() {

		String sTexto = txa.getText();
		return sTexto;
	}
}
