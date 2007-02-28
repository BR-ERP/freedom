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
import org.freedom.modulos.std.FRBordero;
import org.freedom.modulos.std.FRCentroCusto;
import org.freedom.modulos.std.FRExtrato;
import org.freedom.modulos.std.FRFluxoCaixa;
import org.freedom.modulos.std.FRGraficoCC;
import org.freedom.modulos.std.FRInadimplentes;
import org.freedom.modulos.std.FRPagar;
import org.freedom.modulos.std.FRRazaoFin;
import org.freedom.modulos.std.FRReceber;
import org.freedom.modulos.std.FRReceberSetor;
import org.freedom.modulos.std.FSetor;
import org.freedom.modulos.std.FTipoCli;
import org.freedom.modulos.std.FTipoCob;
import org.freedom.modulos.std.FTipoFor;
import org.freedom.modulos.std.FVendedor;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrincipalPD;
import org.freedom.telas.LoginPD;

public class FreedomFNC extends AplicativoPD {

	public FreedomFNC() {

		super( "iconStandart32.gif", "splashFNC.jpg", 1, "Freedom", 6, "Financeiro", null, new FPrincipalPD( null, "bgFreedomSTD.jpg" ), LoginPD.class );
		
		addOpcao( -1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false, null );
			addOpcao( 100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1, false, null );
				addOpcao( 100100000, TP_OPCAO_MENU, "Cliente", "", 'C', 100101000, 2, false, null );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Setor", "Setor", 'S', 100101010, 3, true, FSetor.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Comissionado", "Comissionado", 's', 100101020, 3, true, FVendedor.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Tipo de cliente...", "TipoCli", 'T', 100101030, 3, true, FTipoCli.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Classifica��o de cliente...", "Classifi��o de Clientes", 'f', 100101040, 3, true, FClasCli.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Cliente...", "Clientes", 'C', 100101050, 3, true, FCliente.class );
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Moeda", "Moeda", 'M', 100102000, 2, true, FMoeda.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Banco", "Banco", 'B', 100103000, 2, true, FBanco.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Tipo de cobran�a", "TipoCob", 'T', 100104000, 2, true, FTipoCob.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Plano de pagamento", "PlanoPag", 'P', 100105000, 2, true, FPlanoPag.class );
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Tipo de fornecedor", "TipoFor", 'F', 100107000, 2, true, FTipoFor.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Fornecedor", "Fornecedor", 'F', 100108000, 2, true, FFornecedor.class );	
			addOpcao( 100000000, TP_OPCAO_MENU, "Ferramentas", "", 'F', 100200000, 1, false, null );
			addOpcao( 100000000, TP_OPCAO_MENU, "Prefer�ncias", "", 'P', 100300000, 1, false, null );
				addOpcao( 100300000, TP_OPCAO_ITEM, "Prefer�ncias gerais", "Prefer�ncia Gerais", 'G', 100310000, 2, true, FPrefereFNC.class );
				addOpcao( 100300000, TP_OPCAO_ITEM, "Prefer�ncias febraban", "Prefer�ncia Febraban", 'F', 100310000, 2, true, FPrefereFBB.class );
			addSeparador( 100100000 );
			addOpcao( 100000000, TP_OPCAO_MENU, "Configura��es", "", 'C', 100400000, 1, false, null );
				addOpcao( 100400000, TP_OPCAO_ITEM, "Esta��o de trabalho", "Esta��es de trabalho", 't', 100401000, 2, true, FEstacao.class );
				addOpcao( 100400000, TP_OPCAO_ITEM, "Impressora", "Impressoras", 'I', 100402000, 2, true, FImpressora.class );
				addOpcao( 100400000, TP_OPCAO_ITEM, "Papel", "Papeis", 'P', 100403000, 2, true, FPapel.class );
				addSeparador( 100400000 );
				addOpcao( 100400000, TP_OPCAO_ITEM, "Empresa", "Empresa", 'E', 100404000, 2, true, FEmpresa.class );

		addOpcao( -1, TP_OPCAO_MENU, "Pagar", "", 'P', 200000000, 0, false, null );
		addOpcao( 200000000, TP_OPCAO_ITEM, "Manuten��o", "Manuten��o de contas a pagar", 'M', 200100000, 1, true, FManutPag.class );
		addOpcao( 200000000, TP_OPCAO_MENU, "Listagens", "", 'L', 200200000, 1, false, null );
			addOpcao( 200200000, TP_OPCAO_ITEM, "Pagar/Pagas", "Pagar/Pagas", 'P', 200201000, 2, true, FRPagar.class );

		addOpcao( -1, TP_OPCAO_MENU, "Receber", "", 'R', 300000000, 0, false, null );
		addOpcao( 300000000, TP_OPCAO_ITEM, "Manuten��o", "Manuten��o de contas a receber", 'M', 300100000, 1, true, FManutRec.class );
		addOpcao( 300000000, TP_OPCAO_ITEM, "CNAB", "", 'N', 300200000, 1, true, null );
		addOpcao( 300000000, TP_OPCAO_MENU, "Listagens", "", 'L', 300300000, 1, false, null );
			addOpcao( 300300000, TP_OPCAO_ITEM, "Receber/Recebidas", "Receber/Recebidas", 'R', 300301000, 2, true, FRReceber.class );
			addOpcao( 300300000, TP_OPCAO_ITEM, "Inadimplentes", "Inadimplentes", 'I', 300302000, 2, true, FRInadimplentes.class );
			addOpcao( 300300000, TP_OPCAO_ITEM, "Bordero de cobran�a", "Bordero de cobran�a", 'B', 300303000, 2, true, FRBordero.class );
			addOpcao( 300300000, TP_OPCAO_ITEM, "Descontos por setor", "Descontos por setor", 'D', 300304000, 2, true, FRReceberSetor.class );

		addOpcao( -1, TP_OPCAO_MENU, "Financeiro", "", 'F', 400000000, 0, false, null );
			addOpcao( 400000000, TP_OPCAO_MENU, "Boleto", "", 'o', 400100000, 1, false, null );
				addOpcao( 400100000, TP_OPCAO_ITEM, "Modelo", "Modelo de boleto", 'M', 400101000, 2, true, FModBoleto.class );
				addOpcao( 400100000, TP_OPCAO_ITEM, "Imprimir", "Boleto", 'I', 400101000, 2, true, FRBoleto.class );
			addOpcao( 400000000, TP_OPCAO_ITEM, "Banco", "Banco", 'B', 400200000, 1, true, FBanco.class );
			addOpcao( 400000000, TP_OPCAO_ITEM, "Planejamento", "Planejament", 'P', 400300000, 1, true, FPlanejamento.class );
			addOpcao( 400000000, TP_OPCAO_ITEM, "Centro de custo", "Centro de Custos", 'C', 400400000, 1, true, FCentroCusto.class );
			addOpcao( 400000000, TP_OPCAO_ITEM, "Contas", "Contas", 't', 400500000, 1, true, FConta.class );
			addOpcao( 400000000, TP_OPCAO_ITEM, "Lan�amentos", "Lan�amentos", 'L', 400600000, 1, true, FLanca.class );
			addSeparador( 400000000 );
			addOpcao( 400000000, TP_OPCAO_ITEM, "Tipo de cr�dito", "", 'T', 400700000, 1, true, null );
			addOpcao( 400000000, TP_OPCAO_ITEM, "Libera��o de cr�dito", "", 'i', 400800000, 1, true, null );
			addSeparador( 400000000 );
			addOpcao( 400000000, TP_OPCAO_ITEM, "Reprocessa saldo", "Reprocessamento de saldos", 'R', 400900000, 1, true, FProcessaSL.class );
			addSeparador( 400000000 );
			addOpcao( 400000000, TP_OPCAO_MENU, "Listagens", "", 's', 401000000, 1, false, null );
				addOpcao( 401000000, TP_OPCAO_ITEM, "Extrato", "Extrato", 'E', 401001000, 2, true, FRExtrato.class );
				addOpcao( 401000000, TP_OPCAO_ITEM, "Balancete", "Balancete", 'B', 401002000, 2, true, FRBalancete.class );
				addOpcao( 401000000, TP_OPCAO_ITEM, "Relat�rio financeiro por C.C.", "Relatorio Financeiro por C.C.", 'R', 401003000, 2, true, FRCentroCusto.class );
				addOpcao( 401000000, TP_OPCAO_ITEM, "Raz�o financeiro", "Raz�o financeiro", 'z', 401004000, 2, true, FRRazaoFin.class );
				addOpcao( 401000000, TP_OPCAO_ITEM, "Fluxo de caixa", "Fluxo de caixa", 'F', 401005000, 2, true, FRFluxoCaixa.class );	
			addOpcao( 400000000, TP_OPCAO_MENU, "Gr�ficos", "", 'G', 401100000, 1, false, null );
				addOpcao( 401100000, TP_OPCAO_ITEM, "Balancete Gr�fico", "Balancete Gr�fico", 'B', 401101000, 2, true, FRBalanceteGrafico.class );
				addOpcao( 401100000, TP_OPCAO_ITEM, "Gr�fico financeiro por C.C", "Gr�fico Financeiro por C.C", 'F', 401102000, 2, true, FRGraficoCC.class );

		addBotao( "barraUsuario.gif", "Cliente", "Clientes", 100101050, FCliente.class );
		addBotao( "btContaPagar.gif", "Contas a pagar", "Manuten��o de contas a pagar", 200100000, FManutPag.class );
		addBotao( "btContaReceber.gif", "Contas a receber", "Manuten��o de contas a receber", 300100000, FManutRec.class );
		addBotao( "btLancamentoFin.gif", "Lan�amentos financeiros", "Lan�amentos", 400600000, FLanca.class );
		
		ajustaMenu();

		sNomeModulo = "Financeiro";
		sNomeSis = "Freedom";
		sMailSuporte = "suporte@stpinf.com";
		sEmpSis = "Setpoint Inform�tica Ltda.";
		vEquipeSis.add( "Robson Sanchez - Supervis�o / Analise" );
		vEquipeSis.add( "Anderson Sanchez - Supervis�o / Programa��o" );
		vEquipeSis.add( "Sidnei Varanis - Supervis�o" );
		vEquipeSis.add( "Alex Rodrigues - Programa��o" );
		vEquipeSis.add( "Alexandre Marcondes - Programa��o" );
		vEquipeSis.add( "Fernando Oliveira - Programa��o" );
		vEquipeSis.add( "Moyzes Braz - Arte gr�fica" );
		vEquipeSis.add( "Reginaldo Garcia - Testes / Suporte" );

	}

	public static void main( String sParams[] ) {

		try {
			Aplicativo.setLookAndFeel( "freedom.ini" );
			FreedomFNC freedom = new FreedomFNC();
			freedom.show();
		} catch ( Throwable e ) {
			Funcoes.criaTelaErro( "Erro de execu��o" );
			e.printStackTrace();
		}
	}
}
