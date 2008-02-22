/**
 * @version 30/01/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FreedomGRH.java <BR>
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
 * Tela principal para o m�dulo de gest�o de recursos humanos.
 * 
 */

package org.freedom.modulos.grh;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.cfg.FEstadoCivil;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrincipalPD;
import org.freedom.telas.LoginPD;

public class FreedomGRH extends AplicativoPD {

	public FreedomGRH() {

		super( "iconAtendimento32.gif", "splashGRH.jpg", 1, "Freedom", 9, "Gest�o de Recursos Humandos", null, new FPrincipalPD( null, "bgFreedom2.jpg" ), LoginPD.class );

		addOpcao( -1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false, null );
		addOpcao( 100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1, false, null );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Candidatos", "Candidatos", 'C', 100100100, 2, true, FCandidato.class );
			addSeparador( 100100000 );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Turnos", "Turnos", 'T', 100100200, 2, true, FTurnos.class );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Fun�ao", "Fun��o", 'F', 100100300, 2, true, FFuncao.class );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Departamento", "Departamento", 'D', 100100400, 2, true, FDepto.class );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Empregados", "Empregados", 'E', 100100500, 2, true, FEmpregado.class );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Empregadores", "Empregadores", 'p', 100100600, 2, true, FEmpregadores.class );
			addSeparador( 100100000 );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Area", "Area", 'A', 100100700, 2, true, FArea.class );
			addOpcao( 100100000, TP_OPCAO_ITEM, "N�vel/Curso", "N�vel/Curso", 'N', 100100800, 2, true, FNivelCurso.class );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Curso", "Curso", 'u', 100100900, 2, true, FCurso.class );
			addSeparador( 100100000 );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Estados civis", "Estados civis", 's', 100101000, 2, true, FEstadoCivil.class );
			addSeparador( 100100000 );
			addOpcao( 100100000, TP_OPCAO_ITEM, "Vagas", "Vagas", 'V', 100101100, 2, true, FVaga.class );
			
		addBotao( "barraConveniados.gif", "Empregados", "Empregados", 100100500, FEmpregado.class );
		addBotao( "barraGrupo.gif", "Candidatos", "Candidatos", 100100100, FCandidato.class );

		ajustaMenu();

		sNomeModulo = "Recursos Humanos";
		sMailSuporte = "suporte@stpinf.com";
		sNomeSis = "Freedom";
		sEmpSis = "Setpoint Inform�tica Ltda.";
		vEquipeSis.add( "Robson Sanchez - Supervis�o / Analise" );
		vEquipeSis.add( "Anderson Sanchez - Supervis�o / Programa��o" );
		vEquipeSis.add( "Alex Rodrigues - Programa��o" );
		vEquipeSis.add( "Alexandre Marcondes - Programa��o" );
		vEquipeSis.add( "Fernando Oliveira - Programa��o" );
		vEquipeSis.add( "Moyzes Braz - Arte gr�fica" );
		vEquipeSis.add( "Reginaldo Garcia - Testes / Suporte" );

	}

	public static void main( String sParams[] ) {

		try {
			Aplicativo.setLookAndFeel( "freedom.ini" );
			FreedomGRH freedom = new FreedomGRH();
			freedom.show();
		} catch ( Throwable e ) {
			Funcoes.criaTelaErro( "Erro de execu��o\n\n" + e.getMessage() );
			e.printStackTrace();
		}
	}
}
