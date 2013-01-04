/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLChecaLFSaida.java <BR>
 * 
 *                         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                         Coment�rios sobre a classe...
 */

package org.freedom.modulos.std.view.dialog.utility;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.dialog.DLRelatorio;
import org.freedom.library.type.TYPE_PRINT;

public class DLChecaLFSaida extends DLRelatorio {

	private static final long serialVersionUID = 1L;

	public JTablePad tab = new JTablePad();

	private JScrollPane spnTab = new JScrollPane( tab );

	private JPanelPad pnCliente = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	public DLChecaLFSaida() {

		setTitulo( "Inconsist�ncias de Vendas" );
		setAtribos( 600, 320 );

		c.add( pnCliente, BorderLayout.CENTER );
		pnCliente.add( spnTab, BorderLayout.CENTER );
		tab.adicColuna( "Pedido" );
		tab.adicColuna( "S�rie" );
		tab.adicColuna( "Nota" );
		tab.adicColuna( "Emiss�o" );
		tab.adicColuna( "Inconsist�ncia" );

		tab.setTamColuna( 80, 0 );
		tab.setTamColuna( 40, 1 );
		tab.setTamColuna( 80, 2 );
		tab.setTamColuna( 100, 3 );
		tab.setTamColuna( 200, 4 );

	}

	public void imprimir( TYPE_PRINT bVal ) {

		if ( bVal==TYPE_PRINT.VIEW ) {
			System.out.println( "imprimiu" );
		}
	}

};
