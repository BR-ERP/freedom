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
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para
 * Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela principal para o m�dulo de gest�o de recursos humanos.
 *  
 */

package org.freedom.modulos.grh;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrincipalPD;
import org.freedom.telas.LoginPD;

public class FreedomGRH extends AplicativoPD {

	public FreedomGRH() {
		super("iconAtendimento32.gif", "splashGRH.jpg", 1, "Freedom", 9, "Gest�o de Recursos Humandos", null,new FPrincipalPD(null, "bgFreedom2.jpg"),LoginPD.class);

		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,
				null);
		addOpcao(100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1,
				false, null);
		addOpcao(100100000, TP_OPCAO_ITEM, "Empregados", "Empregados", 'E',
				100101000, 2, true, FEmpregado.class);
		addOpcao(100100000, TP_OPCAO_ITEM, "Turnos", "Turnos", 'R', 100102000,
				2, true, FTurnos.class);
		addOpcao(100100000, TP_OPCAO_ITEM, "Fun�ao", "Fun��o", 'F', 100103000,
				2, true, FFuncao.class);
		addOpcao(100100000, TP_OPCAO_ITEM, "Departamento", "Departamento", 'D',
				100104000, 2, true, FDepto.class);

		addBotao("barraConveniados.gif", "Empregados", "Empregados", 100101000,
				FEmpregado.class);

		ajustaMenu();
		
		sNomeModulo = "Recursos Humanos";
		sMailSuporte = "suporte@stpinf.com";
		sNomeSis = "Freedom";
		sEmpSis = "Setpoint Inform�tica Ltda.";
		vEquipeSis.add("Robson Sanchez - Supervis�o / Analise");
		vEquipeSis.add("Anderson Sanchez - Supervis�o / Programa��o");
		vEquipeSis.add("Alex Rodrigues - Programa��o");
		vEquipeSis.add("Alexandre Marcondes - Programa��o");
		vEquipeSis.add("Fernando Oliveira - Programa��o");
		vEquipeSis.add("Moyzes Braz - Arte gr�fica");
		vEquipeSis.add("Reginaldo Garcia - Testes / Suporte");
		
	}

	public static void main(String sParams[]) {
		try {
			Aplicativo.setLookAndFeel("freedom.ini");
			FreedomGRH freedom = new FreedomGRH();
			freedom.show();
		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execu��o");
			e.printStackTrace();
		}
	}
}