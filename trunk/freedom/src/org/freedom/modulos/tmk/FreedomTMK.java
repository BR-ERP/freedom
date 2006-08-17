/**
 * @version 19/09/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe:
 * @(#)FreedomTMK.java <BR>
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
 * Tela principal do m�dulo telemarketing.
 *  
 */

package org.freedom.modulos.tmk;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.atd.FAtendente;
import org.freedom.modulos.atd.FTipoAtend;
import org.freedom.modulos.std.FClasCli;
import org.freedom.modulos.std.FCliente;
import org.freedom.modulos.std.FSetor;
import org.freedom.modulos.std.FTipoCli;
import org.freedom.modulos.std.FVendedor;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrincipalPD;

public class FreedomTMK extends Aplicativo {

	public FreedomTMK() {
		super("iconAtendimento32.gif", "splashTMK.jpg", 1, "Freedom", 7, "Telemarketing", null, new FPrincipalPD(null, "bgFreedomSTD.jpg"));
		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,
				null);

		addOpcao(100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1,
				false, null);

		addOpcao(100100000, TP_OPCAO_MENU, "Contato", "", 'C', 1001010000, 2,
				false, null);
		addOpcao(1001010000, TP_OPCAO_ITEM, "Atividade", "Atividade", 'A',
				100101010, 3, true, FAtividade.class);
		addOpcao(1001010000, TP_OPCAO_ITEM, "Contato", "Contatos", 'C',
				100101020, 3, true, FContato.class);

		addOpcao(100100000, TP_OPCAO_MENU, "Atendente", "", 'A', 100102000, 2,
				false, null);
		addOpcao(100102000, TP_OPCAO_ITEM, "Tipo de Atendente",
				"Tipo de atendente", 'T', 100102010, 3, true, FTipoAtend.class);
		addOpcao(100102000, TP_OPCAO_ITEM, "Atendentes", "Atendente", 'A',
				100110020, 3, true, FAtendente.class);

		addOpcao(100100000, TP_OPCAO_MENU, "Cliente", "", 'C', 100104000, 2,
				false, null);
		addOpcao(100104000, TP_OPCAO_ITEM, "Setor", "Setor", 'S', 100104010, 3,
				true, FSetor.class);
		addOpcao(100104000, TP_OPCAO_ITEM, "Vendedor", "Vendedor", 'V',
				100104020, 3, true, FVendedor.class);
		addOpcao(100104000, TP_OPCAO_ITEM, "Tipos de cliente",
				"Tipo de cliente", 'T', 100104030, 3, true, FTipoCli.class);
		addOpcao(100104000, TP_OPCAO_ITEM, "Classifica��o de cliente",
				"Classifica��o do cliente", 'l', 100104040, 3, true,
				FClasCli.class);
		addSeparador(100104000);
		addOpcao(100104000, TP_OPCAO_ITEM, "Cliente", "Clientes", 'C',
				100104050, 3, true, FCliente.class);
		addSeparador(100104000);

		addOpcao(100000000, TP_OPCAO_MENU, "Prefer�ncias", "", 'P', 100200000,
				1, false, null);
		addOpcao(100200000, TP_OPCAO_ITEM, "Prefer�ncias Gerais...",
				"Pref. Gerais", 'G', 100201000, 2, true, FPrefere.class);
		addOpcao(100000000, TP_OPCAO_MENU, "Ferramentas", "", 'F', 100300000,
				1, false, null);
		addOpcao(100300000, TP_OPCAO_ITEM, "Importa��o de Contatos",
				"Importa��o de contatos", 'I', 100301000, 2, true,
				FImportaCto.class);
		addOpcao(100300000, TP_OPCAO_ITEM, "Cadastro de org.freedom.layout",
				"Cadastro de org.freedom.layout", 'C', 100302000, 2, true,
				FTipoImp.class);
		addOpcao(100300000, TP_OPCAO_ITEM, "Envio de e-mail aos contatos",
				"Envia e-mail", 'E', 100303000, 2, true, FEnviaMail.class);

		addOpcao(-1, TP_OPCAO_MENU, "Contatos", "", 'C', 200000000, 0, false,
				null);
		addOpcao(200000000, TP_OPCAO_ITEM, "Hist�rico", "Historico", 'H',
				200100000, 1, true, FHistorico.class);
		addOpcao(200000000, TP_OPCAO_ITEM, "Agenda", "Agenda", 'A', 200200000,
				1, true, FAgenda.class);
		addOpcao(200000000, TP_OPCAO_MENU, "Listagens", "", 'L', 200300000, 1,
				false, null);
		addOpcao(200300000, TP_OPCAO_ITEM, "Relat�rio di�rio",
				"Relat�rio di�rio", 'R', 200301000, 1, true, FRDiario.class);

		addBotao("btAtendimento.gif", "Atendimento", "Atendente", 100110020,
				FAtendente.class);

		ajustaMenu();
		
		  sNomeModulo = "Telemarkting";
		  sMailSuporte = "suporte@stpinf.com";
		  sNomeSis = "Freedom";
		  sEmpSis = "Setpoint Inform�tica Ltda.";
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
			FreedomTMK fFreedomtmk = new FreedomTMK();
			fFreedomtmk.show();
		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execu��o");
			e.printStackTrace();
		}
	}
}