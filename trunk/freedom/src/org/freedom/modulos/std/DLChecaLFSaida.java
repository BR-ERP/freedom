/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLChecaLFSaida.java <BR>
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

import java.awt.BorderLayout;

import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.componentes.Tabela;
import org.freedom.telas.DLRelatorio;

public class DLChecaLFSaida extends DLRelatorio {

	private static final long serialVersionUID = 1L;

	public Tabela tab = new Tabela();

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

	public void imprimir( boolean bVal ) {

		if ( bVal ) {
			System.out.println( "imprimiu" );
		}
	}

};
