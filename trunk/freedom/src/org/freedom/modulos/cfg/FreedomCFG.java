/**
 * @version 07/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe:
 * @(#)FreedomCFG.java <BR>
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
 * Tela principal do m�dulo configurador.
 *  
 */

package org.freedom.modulos.cfg;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrincipal;
import org.freedom.telas.FPrincipalPD;

public class FreedomCFG extends Aplicativo {

	public FreedomCFG() {
		super("iconConfiguracao32.gif", "splashConf.jpg", 1, "Freedom", 2, "Configurador do Sistema", null,new FPrincipalPD("bgFreedomSTD.jpg"));
		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,
				null);
		addOpcao(100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1,
				false, null);
		addOpcao(100100000, TP_OPCAO_MENU, "Acesso ao sistema", "", 'A',
				100101000, 2, false, null);
		addOpcao(100101000, TP_OPCAO_ITEM, "Grupos", "Grupos", 'G', 100101010,
				3, true, FGrupo.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Usuarios", "Usuarios", 'U',
				100101020, 3, true, FUsuario.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Acesso", "Acesso Menu", 'A',
				100101030, 3, true, FAcesso.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Menu", "Menu X Objeto", 'M',
				100101040, 3, true, FMenuObj.class);
		addOpcao(100100000, TP_OPCAO_MENU, "Clientes/Conveniados", "", 'C',
				100102000, 2, false, null);
		addOpcao(100102000, TP_OPCAO_ITEM, "Grau de instru��o",
				"Grau de Instru��o", 'G', 100102010, 3, true, FGrauInst.class);
		addOpcao(100102000, TP_OPCAO_ITEM, "Paises", "Paises", 'P', 100102020,
				3, true, FPais.class);
		addOpcao(100100000, TP_OPCAO_MENU, "Objetos", "", 'O', 100103000, 2,
				false, null);
		addOpcao(100103000, TP_OPCAO_ITEM, "Tabela", "Tabelas auxiliares", 'T',
				100103010, 3, true, FTabela.class);
		addOpcao(100103000, TP_OPCAO_ITEM, "Objetos aux.",
				"Vinculo entre tabelas f�sicas e auxiliares", 'O', 100103020,
				3, true, FObjetoTb.class);
		addOpcao(100100000, TP_OPCAO_MENU, "Fluxos", "", 'F', 100104000, 2,
				false, null);
		addOpcao(100104000, TP_OPCAO_ITEM, "Processos", "Processos", 'P',
				100104100, 3, true, FProcesso.class);
		addOpcao(100104000, TP_OPCAO_ITEM, "Fluxos", "Cadastro de fluxos", 'F',
				100104110, 3, true, FFluxo.class);

		addOpcao(100000000, TP_OPCAO_MENU, "Ferramentas", "", 'e', 100200000,
				1, false, null);
		addOpcao(100200000, TP_OPCAO_ITEM, "Ajuste de Sequencia",
				"Ajusta sequencia", 'A', 100201000, 2, true, FAjustaSeq.class);

		addOpcao(100000000, TP_OPCAO_MENU, "Prefer�ncias", "", 'P', 100300000,
				1, false, null);
		addOpcao(100300000, TP_OPCAO_ITEM, "Visual",
				"Configura��o de Visual", 'A', 100301000, 2, true, FVisual.class);
		
		addBotao("barraGrupo.gif", "Cadastro de Grupos", "Grupos", 100101010,
				FGrupo.class);
		addBotao("barraUsuario.gif", "Cadastro de Usuarios", "Usuarios",
				100101020, FUsuario.class);
		addBotao("barraAcesso.gif", "Controle de Acessos", "Acesso Menu",
				100101030, FAcesso.class);

		
		//	  addBotao("btProcessos.gif", "Controle de processos","", 100110000,
		// null);
		//	  addBotao("btFluxo.gif", "Controle de Fluxos","", 100111000, null);
		FVisual teste = new FVisual(true);
		teste.setConexao(con);
		teste.show();

		ajustaMenu();

		conIB = conexaoIB(getParameter("driver"), getParameter("bancocfg")); // Inicia
		// a
		// vari�vel
		// de
		// conex�o
		// com
		// o
		// banco
		// interno
		// do
		// interbase
		
		sNomeModulo = "Administrativo";
		sNomeSis = "Freedom";
		sEmpSis = "Setpoint Inform�tica Ltda.";
		sMailSuporte = "suporte@stpinf.com";
		vEquipeSis.add("Robson Sanchez - Supervis�o / Analise");
		vEquipeSis.add("Anderson Sanchez - Supervis�o / Programa��o");
		vEquipeSis.add("Alex Rodrigues - Programa��o");
		vEquipeSis.add("Alexandre Marcondes - Programa��o");
		vEquipeSis.add("Fernando Oliveira - Programa��o");
		vEquipeSis.add("Moyzes Braz - Arte gr�fica");
		vEquipeSis.add("Leandro Oliveira - Testes / Suporte");
	}

	public static void main(String sParams[]) {
		try {
			Aplicativo.setLookAndFeel("freedom.ini");
			FreedomCFG freedom = new FreedomCFG();
			freedom.show();

		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execu��o");
			e.printStackTrace();
		}
	}
}