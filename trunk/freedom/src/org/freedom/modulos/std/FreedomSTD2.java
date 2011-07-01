/**
 * @version 02/02/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)Freedomstd.java <BR>
 * 
 *                     Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                     na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                     Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                     Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                     Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Tela principal do m�dulo standard.
 * 
 */

package org.freedom.modulos.std;

import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrincipal2;
import org.freedom.library.swing.frame.LoginPD;
import org.freedom.modulos.std.view.background.Background34;

public class FreedomSTD2 extends FreedomSTD {

	public FreedomSTD2() {

		super( "iconstd.png", "splashSTD.png", 1, "Freedom", 1, "Standard", null, new FPrincipal2( new Background34(), "bgFreedomSTD2.jpg" ), LoginPD.class );
		this.montaMenu();
	}

	public static void main( String sParams[] ) {

		try {
			Aplicativo.setLookAndFeel( "freedom.ini" );
			FreedomSTD2 freedom = new FreedomSTD2();
			FPrincipal2.carregaAgenda();
			freedom.show();
		} catch ( Throwable e ) {
			Funcoes.criaTelaErro( "Erro de execu��o\n" + e.getMessage() + "\n" + e.getCause() + "\n" + e.getLocalizedMessage() );
			e.printStackTrace();
		}
	}
}
