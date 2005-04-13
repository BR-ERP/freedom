/**
 * @version 29/12/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FreedomFNC.java <BR>
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
 * Tela principal do m�dulo financeiro.
 *  
 */

package org.freedom.modulos.fnc;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.FBanco;
import org.freedom.modulos.std.FCentroCusto;
import org.freedom.modulos.std.FClasCli;
import org.freedom.modulos.std.FCliente;
import org.freedom.modulos.std.FConta;
import org.freedom.modulos.std.FEmpresa;
import org.freedom.modulos.std.FEstacao;
import org.freedom.modulos.std.FFornecedor;
import org.freedom.modulos.std.FImpressora;
import org.freedom.modulos.std.FLanca;
import org.freedom.modulos.std.FManutPag;
import org.freedom.modulos.std.FManutRec;
import org.freedom.modulos.std.FModBoleto;
import org.freedom.modulos.std.FMoeda;
import org.freedom.modulos.std.FPapel;
import org.freedom.modulos.std.FPlanejamento;
import org.freedom.modulos.std.FPlanoPag;
import org.freedom.modulos.std.FProcessaSL;
import org.freedom.modulos.std.FRBalancete;
import org.freedom.modulos.std.FRBalanceteGrafico;
import org.freedom.modulos.std.FRBoleto;
import org.freedom.modulos.std.FRCentroCusto;
import org.freedom.modulos.std.FRExtrato;
import org.freedom.modulos.std.FRFluxoCaixa;
import org.freedom.modulos.std.FRGraficoCC;
import org.freedom.modulos.std.FRInadimplentes;
import org.freedom.modulos.std.FRPagar;
import org.freedom.modulos.std.FRRazaoFin;
import org.freedom.modulos.std.FRReceber;
import org.freedom.modulos.std.FSetor;
import org.freedom.modulos.std.FTipoCli;
import org.freedom.modulos.std.FTipoCob;
import org.freedom.modulos.std.FTipoFor;
import org.freedom.modulos.std.FVendedor;
import org.freedom.telas.Aplicativo;

public class FreedomFNC extends Aplicativo {
	public FreedomFNC() {
		super("iconStandart32.gif", "splashFNC.jpg",
				"Freedom - M�dulo financeiro", 1, 6, "freedom.ini", null);
		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,
				null);
		addOpcao(100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1,
				false, null);
		addOpcao(100100000, TP_OPCAO_MENU, "Cliente", "", 'C', 100101000, 2,
				false, null);
		addOpcao(100101000, TP_OPCAO_ITEM, "Setor", "Setor", 'S', 100101010, 3,
				true, FSetor.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Comissionado", "Comissionado", 'i',
				100101020, 3, true, FVendedor.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Tipo de cliente...", "TipoCli",
				'T', 100101030, 3, true, FTipoCli.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Classifica��o de cliente...",
				"Classifi��o de Clientes", 'f', 100101040, 3, true,
				FClasCli.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Cliente...", "Clientes", 'C',
				100101050, 3, true, FCliente.class);
		addSeparador(100100000);
		addOpcao(100100000, TP_OPCAO_ITEM, "Moeda", "Moeda", 'M', 100102000, 2,
				true, FMoeda.class);
		addOpcao(100100000, TP_OPCAO_ITEM, "Banco", "Banco", 'B', 100103000, 2,
				true, FBanco.class);
		addOpcao(100100000, TP_OPCAO_ITEM, "Tipo de cobran�a", "TipoCob", 'o',
				100104000, 2, true, FTipoCob.class);
		addOpcao(100100000, TP_OPCAO_ITEM, "Plano de pagamento", "PlanoPag",
				's', 100105000, 2, true, FPlanoPag.class);
		addSeparador(100100000);
		addOpcao(100100000, TP_OPCAO_ITEM, "Tipo de fornecedor", "TipoFor",
				'e', 100107000, 2, true, FTipoFor.class);
		addOpcao(100100000, TP_OPCAO_ITEM, "Fornecedor", "Fornecedor", 'r',
				100108000, 2, true, FFornecedor.class);

		addOpcao(100000000, TP_OPCAO_MENU, "Ferramentas", "", 'F', 100200000,
				1, false, null);
		addOpcao(100000000, TP_OPCAO_MENU, "Prefer�ncias", "", 'P', 100300000,
				1, false, null);
		addOpcao(100300000, TP_OPCAO_ITEM, "Prefer�ncias gerais",
				"Pref. Gerais", 'g', 100310000, 2, true, FPrefereGeral.class);
		addSeparador(100100000);
		addOpcao(100000000, TP_OPCAO_MENU, "Configura��es", "", 'C', 100400000,
				1, false, null);
		addOpcao(100400000, TP_OPCAO_ITEM, "Esta��o de trabalho",
				"Esta��es de trabalho", 'T', 100410000, 2, true, FEstacao.class);
		addOpcao(100400000, TP_OPCAO_ITEM, "Impressora", "Impressoras", 'I',
				100420000, 2, true, FImpressora.class);
		addOpcao(100400000, TP_OPCAO_ITEM, "Papel", "Papeis", 'P', 100430000,
				2, true, FPapel.class);
		addSeparador(100400000);
		addOpcao(100400000, TP_OPCAO_ITEM, "Empresa", "Empresa", 'E',
				100440000, 2, true, FEmpresa.class);

		addOpcao(-1, TP_OPCAO_MENU, "Pagar", "", 'P', 400000000, 0, false, null);
		addOpcao(400000000, TP_OPCAO_ITEM, "Manuten��o",
				"Manuten��o de contas a pagar", 'M', 400200000, 1, true,
				FManutPag.class);
		addOpcao(400000000, TP_OPCAO_MENU, "Listagens", "", 's', 400300000, 1,
				false, null);
		addOpcao(400300000, TP_OPCAO_ITEM, "Pagar/Pagas", "Pagar/Pagas", 'P',
				400410000, 2, true, FRPagar.class);

		addOpcao(-1, TP_OPCAO_MENU, "Receber", "", 'R', 500000000, 0, false,
				null);
		addOpcao(500000000, TP_OPCAO_ITEM, "Manuten��o",
				"Manuten��o de contas a receber", 'M', 500100000, 1, true,
				FManutRec.class);
		addOpcao(500000000, TP_OPCAO_ITEM, "CNAB", "", 'N', 500200000, 1, true,
				null);
		addOpcao(500000000, TP_OPCAO_MENU, "Listagens", "", 's', 500300000, 1,
				false, null);
		addOpcao(500300000, TP_OPCAO_ITEM, "Receber/Recebidas",
				"Receber/Recebidas", 'R', 500310000, 2, true, FRReceber.class);
		addOpcao(500300000, TP_OPCAO_ITEM, "Inadimplentes", "Inadimplentes",
				'I', 500320000, 2, true, FRInadimplentes.class);

		addOpcao(-1, TP_OPCAO_MENU, "Financeiro", "", 'F', 600000000, 0, false,
				null);
		addOpcao(600000000, TP_OPCAO_MENU, "Boleto", "", 'B', 600100000, 1,
				false, null);
		addOpcao(600100000, TP_OPCAO_ITEM, "Modelo", "Modelo de boleto", 'M',
				600110000, 2, true, FModBoleto.class);
		addOpcao(600100000, TP_OPCAO_ITEM, "Imprimir", "Boleto", 'I',
				600120000, 2, true, FRBoleto.class);
		addOpcao(600000000, TP_OPCAO_ITEM, "Banco", "Banco", 'a', 600200000, 1,
				true, FBanco.class);
		addOpcao(600000000, TP_OPCAO_ITEM, "Planejamento", "Planejament", 'P',
				600300000, 1, true, FPlanejamento.class);
		addOpcao(600000000, TP_OPCAO_ITEM, "Centro de custo",
				"Centro de Custos", 'C', 600400000, 1, true, FCentroCusto.class);
		addOpcao(600000000, TP_OPCAO_ITEM, "Contas", "Contas", 'o', 600500000,
				1, true, FConta.class);
		addOpcao(600000000, TP_OPCAO_ITEM, "Lan�amentos", "Lan�amentos", 'L',
				600600000, 1, true, FLanca.class);
		addSeparador(600000000);
		addOpcao(600000000, TP_OPCAO_ITEM, "Tipo de cr�dito", "", 'L',
				600700000, 1, true, null);
		addOpcao(600000000, TP_OPCAO_ITEM, "Libera��o de cr�dito", "", 'i',
				600800000, 1, true, null);
		addSeparador(600000000);
		addOpcao(600000000, TP_OPCAO_ITEM, "Reprocessa saldo",
				"Reprocessamento de saldos", 'R', 600900000, 1, true,
				FProcessaSL.class);
		addSeparador(600000000);

		addOpcao(600000000, TP_OPCAO_MENU, "Listagens", "", 's', 601000000, 1,
				false, null);
		addOpcao(601000000, TP_OPCAO_ITEM, "Extrato", "Extrato", 'E',
				601010000, 2, true, FRExtrato.class);
		addOpcao(601000000, TP_OPCAO_ITEM, "Balancete", "Balancete", 'B',
				601020000, 2, true, FRBalancete.class);
		addOpcao(601000000, TP_OPCAO_ITEM, "Relat�rio financeiro por C.C.",
				"Relatorio Financeiro por C.C.", 'R', 601030000, 2, true,
				FRCentroCusto.class);
		addOpcao(601000000, TP_OPCAO_ITEM, "Raz�o financeiro",
				"Raz�o financeiro", 'z', 601040000, 2, true, FRRazaoFin.class);
		addOpcao(601000000, TP_OPCAO_ITEM, "Fluxo de caixa", "Fluxo de caixa",
				'F', 601050000, 2, true, FRFluxoCaixa.class);

		addOpcao(600000000, TP_OPCAO_MENU, "Gr�ficos", "", 'G', 601100000, 1,
				false, null);
		addOpcao(601100000, TP_OPCAO_ITEM, "Balancete Gr�fico",
				"Balancete Gr�fico", 'G', 601110000, 2, true,
				FRBalanceteGrafico.class);
		addOpcao(601100000, TP_OPCAO_ITEM, "Gr�fico financeiro por C.C",
				"Gr�fico Financeiro por C.C", 'f', 601120000, 2, true,
				FRGraficoCC.class);

		//addBotao("btProduto.gif","Cadastro de produtos","", 100120070, null);
		addBotao("barraUsuario.gif", "Cliente", "Clientes", 100101050,
				FCliente.class);
		//addBotao("barraVenda.gif","Venda","", 300100000, null);
		//addBotao("barraCompra.gif","Compra","", 200100000, null);
		addBotao("btContaPagar.gif", "Contas a pagar",
				"Manuten��o de contas a pagar", 400200000, FManutPag.class);
		addBotao("btContaReceber.gif", "Contas a receber",
				"Manuten��o de contas a receber", 500100000, FManutRec.class);
		addBotao("btLancamentoFin.gif", "Lan�amentos financeiros",
				"Lan�amentos", 600600000, FLanca.class);
		//addBotao("barraEstoque.gif","Consulta estoque","", 700300000, null);

		ajustaMenu();
		
		sNomeModulo = "Financeiro";
		  sNomeSis = "Freedom";
		  sEmpSis = "Setpoint Inform�tica Ltda.";
		  vEquipeSis.add("Robson Sanchez - Supervis�o / Analise");
		  vEquipeSis.add("Anderson Sanchez - Supervis�o / Programa��o");	
		  vEquipeSis.add("Sidnei Varanis - Supervis�o");
		  vEquipeSis.add("Alex Rodrigues - Programa��o");
		  vEquipeSis.add("Alexandre Marcondes - Programa��o");
		  vEquipeSis.add("Fernando Oliveira - Programa��o");
		  vEquipeSis.add("Moyzes Braz - Arte gr�fica");
		  vEquipeSis.add("Leandro Oliveira - Testes / Suporte");

	}

	public static void main(String sParams[]) {
		try {
			FreedomFNC freedom = new FreedomFNC();
			freedom.show();
		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execu��o");
			e.printStackTrace();
		}
	}
}