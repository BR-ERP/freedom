/**
 * @version 30/01/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.grh <BR>
 *         Classe:
 * @(#)FreedomGRH.java <BR>
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
 *                     Tela principal para o m�dulo de gest�o de recursos humanos.
 * 
 */

package org.freedom.modulos.gpe;

import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.AplicativoPD;
import org.freedom.library.swing.frame.FPrincipalPD;
import org.freedom.library.swing.frame.LoginPD;
import org.freedom.modulos.cfg.view.frame.crud.plain.FEstadoCivil;
import org.freedom.modulos.cfg.view.frame.crud.tabbed.FFeriados;
import org.freedom.modulos.gpe.view.frame.crud.plain.FBatida;
import org.freedom.modulos.gpe.view.frame.report.FRBatidas;
import org.freedom.modulos.grh.view.frame.crud.plain.FBeneficio;
import org.freedom.modulos.grh.view.frame.crud.plain.FDepto;
import org.freedom.modulos.grh.view.frame.crud.plain.FFuncao;
import org.freedom.modulos.grh.view.frame.crud.plain.FTurnos;
import org.freedom.modulos.grh.view.frame.crud.tabbed.FEmpregado;
import org.freedom.modulos.grh.view.frame.crud.tabbed.FEmpregadores;

public class FreedomGPE extends AplicativoPD {

	public FreedomGPE() {

		super( "icongrh.png", "splashGRH.png", 1, "Freedom", 10, "Gest�o de Ponto Eletr�nico", null, new FPrincipalPD( null, "bgFreedom2.jpg" ), LoginPD.class );

		addOpcao( -1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false, null );

		addOpcao( 100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1, false, null );

		addOpcao( 100100000, TP_OPCAO_ITEM, "Turnos", "Turnos", 'T', 100100100, 2, true, FTurnos.class );
		addOpcao( 100100000, TP_OPCAO_ITEM, "Fun��es", "Fun��es", 'F', 100100200, 2, true, FFuncao.class );
		addOpcao( 100100000, TP_OPCAO_ITEM, "Departamentos", "Departamentos", 'D', 100100300, 2, true, FDepto.class );
		addOpcao( 100100000, TP_OPCAO_ITEM, "Empregados", "Empregados", 'E', 100100400, 2, true, FEmpregado.class );
		addOpcao( 100100000, TP_OPCAO_ITEM, "Empregadores", "Empregadores", 'p', 100100500, 2, true, FEmpregadores.class );
		addOpcao( 100100000, TP_OPCAO_ITEM, "Benef�cios", "Cadastro de Benef�cios", 'b', 100100600, 2, true, FBeneficio.class );

		addSeparador( 100100000 );
		addOpcao( 100100000, TP_OPCAO_ITEM, "Estados civis", "Estados civis", 's', 100100700, 2, true, FEstadoCivil.class );
		addSeparador( 100100000 );
		addOpcao( 100100000, TP_OPCAO_ITEM, "Feriados", "Cadastro de Feriados", 'r', 100100800, 2, true, FFeriados.class );

		addOpcao( -1, TP_OPCAO_MENU, "Ponto", "", 'P', 200000000, 0, false, null );
		addOpcao( 200000000, TP_OPCAO_ITEM, "Digita��o de Livro Ponto", "Digita��o de Livro Ponto", 'D', 200100000, 1, true, FBatida.class );
	
		addOpcao( 200000000, TP_OPCAO_MENU, "Listagens", "", 'L', 200200000, 1, false, null );
		addOpcao( 200200000, TP_OPCAO_ITEM, "Relat�rio de Batidas/Ponto", "Relat�rio de Batidas/Ponto", 'B', 200201000, 1, true, FRBatidas.class );	
		
		ajustaMenu();

		nomemodulo = "Ponto Eletr�nico";

	}

	public static void main( String sParams[] ) {

		try {
			Aplicativo.setLookAndFeel( "freedom.ini" );
			FreedomGPE freedom = new FreedomGPE();
			freedom.show();
		} catch ( Throwable e ) {
			Funcoes.criaTelaErro( "Erro de execu��o\n\n" + e.getMessage() );
			e.printStackTrace();
		}
	}
}
