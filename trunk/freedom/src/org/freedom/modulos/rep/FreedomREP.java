/**
 * @version 02/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)FreedomREP.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela principal do m�dulo de gest�o para representa��es.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrincipalPD;
import org.freedom.telas.LoginPD;

public class FreedomREP extends AplicativoRep implements ActionListener {

	public FreedomREP() {

		super( "iconrep.png", "splashREP.jpg", 1, "Freedom", 11, "Representa��es Comerciais", null, new FPrincipalPD( null, "bgFreedom2.jpg" ), LoginPD.class );
		
		addOpcao( -1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false, null );
			addOpcao( 100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1, false, null );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Cliente", "Cliente", 'C', 100101000, 2, true, RPCliente.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Tipo de cliente", "Tipo de cliente", 't', 100102000, 2, true, RPTipoCli.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Contato", "Contato", 'o', 100103000, 2, true, RPContato.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Vendedor", "Vendedor", 'V', 100104000, 2, true, RPVendedor.class );
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Fornecedor", "Fornecedor", 'F', 100105000, 2, true, RPFornecedor.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Transportadoras", "Transportadoras", 'T', 100106000, 2, true, RPTransportadora.class );
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Grupos", "Grupos", 'G', 100107000, 2, true, RPGrupo.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Unidades", "Unidades", 'U', 100108000, 2, true, RPUnidade.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Produtos", "Produtos", 'P', 100109000, 2, true, RPProduto.class );
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Planos de pagamento", "Planos de pagamento", 'r', 100110000, 2, true, RPPlanoPag.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Moeda", "Moeda", 'M', 100111000, 2, true, RPMoeda.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Cota��o", "Cota��o", 'a', 100112000, 2, true, RPCotMoeda.class );
			addSeparador( 100000000 );
			addOpcao( 100000000, TP_OPCAO_ITEM, "Prefer�ncias", "Prefer�ncias", 'P', 100200000, 1, true, RPPrefereGeral.class );
			addSeparador( 100000000 );
			addOpcao( 100000000, TP_OPCAO_ITEM, "Esta��o de trabalho", "Esta��o de trabalho", 't', 100300000, 1, true, RPEstacao.class );
			addOpcao( 100000000, TP_OPCAO_ITEM, "Empresa", "Empresa", 'E', 100400000, 1, true, RPEmpresa.class );
			addSeparador( 100000000 );
			addOpcao( 100000000, TP_OPCAO_ITEM, "Importa��o", "Importa��o de dados", 'I', 100500000, 1, true, RPImportacao.class );

		addOpcao( -1, TP_OPCAO_MENU, "Vendas", "", 'V', 200000000, 0, false, null );
			addOpcao( 200000000, TP_OPCAO_ITEM, "Pedidos", "Pedidos", 'P', 200100000, 1, true, RPPedido.class );
			addSeparador( 200000000 );
			addOpcao( 200000000, TP_OPCAO_ITEM, "Consuta de pedidos", "Consuta de pedidos", 'C', 200200000, 1, true, RPConsPedido.class );
			addSeparador( 200000000 );
			addOpcao( 200000000, TP_OPCAO_ITEM, "Faturamento", "Faturamento", 'F', 200300000, 1, true, RPFaturamento.class );
			addOpcao( 200000000, TP_OPCAO_ITEM, "Manuten��o de Comiss�o", "Manuten��o de Comiss�o", 'M', 200400000, 1, true, RPComissao.class );

		//addOpcao( -1, TP_OPCAO_MENU, "Pagar", "Pagar", 'P', 300000000, 0, false, null );
		//	addOpcao( 300000000, TP_OPCAO_ITEM, "Pagar", "Pagar", 'P', 300100000, 1, true, RPPagar.class );
		
		//addOpcao( -1, TP_OPCAO_MENU, "Receber", "Receber", 'R', 400000000, 0, false, null );
		//	addOpcao( 400000000, TP_OPCAO_ITEM, "Receber", "Receber", 'R', 400100000, 1, true, RPReceber.class );
		
		addOpcao( -1, TP_OPCAO_MENU, "Relatorios", "", 'l', 500000000, 0, false, null );
			addOpcao( 500000000, TP_OPCAO_MENU, "Vendas", "", 'V', 500100000, 1, false, null );
				addOpcao( 500100000, TP_OPCAO_ITEM, "Pedidos", "Pedidos", 'P', 500101000, 2, true, RelPedido.class );
				addOpcao( 500100000, TP_OPCAO_ITEM, "Resumo Diario", "Resumo Diario", 'R', 500102000, 2, true, RelResumoDiario.class );
				addOpcao( 500100000, TP_OPCAO_ITEM, "Hist�rico de clientes", "Hist�rico de clientes", 'H', 500103000, 2, true, RelHistoricoCliente.class );
				addOpcao( 500100000, TP_OPCAO_ITEM, "Produtos por clientes", "Produtos por clientes", 'P', 500104000, 2, true, RelProdutoCliente.class );
				addOpcao( 500100000, TP_OPCAO_ITEM, "Pedidos por vendedor", "Pedidos por vendedor", 'v', 500105000, 2, true, RelPedidoVend.class );
				addSeparador( 500100000 );
				addOpcao( 500100000, TP_OPCAO_ITEM, "Evolu��o de vendas", "Evolu��o de vendas", 'E', 500106000, 2, true, RelEvolucaoVendas.class );
			addOpcao( 500000000, TP_OPCAO_MENU, "Curvas ABC", "", 'A', 500200000, 1, false, null );
				addOpcao( 500200000, TP_OPCAO_ITEM, "Curva de Produtos", "Curva de Produtos", 'P', 500201000, 2, true, RelCurvaABCProdutos.class );
				addOpcao( 500200000, TP_OPCAO_ITEM, "Curva de Clientes", "Curva de Clientes", 'P', 500202000, 2, true, RelCurvaABCClientes.class );
				addOpcao( 500200000, TP_OPCAO_ITEM, "Curva de Data", "Curva de Data", 'P', 500203000, 2, true, RelCurvaABCData.class );
			addOpcao( 500000000, TP_OPCAO_MENU, "Produtos", "", 'P', 500300000, 1, false, null );
				addOpcao( 500300000, TP_OPCAO_ITEM, "Produtos", "Produtos", 'P', 500301000, 1, true, RelProduto.class );
				addOpcao( 500300000, TP_OPCAO_ITEM, "Tabela com saldo", "Tabela com saldo", 'c', 500302000, 2, true, RelSaldosProd.class );
				addOpcao( 500300000, TP_OPCAO_ITEM, "Tabela sem saldo", "Tabela sem saldo", 's', 500303000, 2, true, RelsemSaldosProd.class );
				addOpcao( 500300000, TP_OPCAO_ITEM, "Tabela vendas", "Tabela vendas", 'v', 500304000, 2, true, RelVendedorProd.class );
			addOpcao( 500000000, TP_OPCAO_MENU, "Clientes", "", 'C', 500400000, 1, false, null );
				addOpcao( 500400000, TP_OPCAO_ITEM, "Clientes", "Clientes", 'C', 500401000, 2, true, RelCliente.class );
				addOpcao( 500400000, TP_OPCAO_ITEM, "Tipos de cliente", "Tipos de cliente", 'T', 500402000, 2, true, RelTipoCli.class );
				addOpcao( 500400000, TP_OPCAO_ITEM, "Contatos", "Contatos", 'o', 500403000, 2, true, RelContato.class );
			addSeparador( 500000000 );
			addOpcao( 500000000, TP_OPCAO_ITEM, "Vendedores", "Vendedores", 'e', 500500000, 1, true, RelVendedor.class );
			addOpcao( 500000000, TP_OPCAO_ITEM, "Fornecedores", "Fornecedores", 'F', 500600000, 1, true, RelFornecedor.class );
			addOpcao( 500000000, TP_OPCAO_ITEM, "Grupos", "Grupos", 'G', 500700000, 1, true, RelGrupo.class );
			addOpcao( 500000000, TP_OPCAO_ITEM, "Transportadoras", "Transportadoras", 'T', 500800000, 1, true, RelTransportadora.class );
			
			addBotao( "btCliente.gif", "Cliente", "Cliente", 100101000, RPCliente.class );
			addBotao( "btAtendimento.gif", "Vendedor", "Vendedor", 100104000, RPVendedor.class );
			addBotao( "btForneced.gif", "Fornecedor", "Fornecedor", 100105000, RPFornecedor.class );
			addBotao( "btProduto.gif", "Produtos", "Produtos", 100108000, RPProduto.class );
			addBotao( "btSaida.png", "Pedidos", "Pedidos", 200100000, RPPedido.class );
		
		ajustaMenu();

		sNomeModulo = "Representa��o";
		sMailSuporte = "suporte@stpinf.com";
		sNomeSis = "Freedom";
		sEmpSis = "Setpoint Inform�tica Ltda.";
		vEquipeSis.add( "Robson Sanchez - Supervis�o/Analise" );
		vEquipeSis.add( "Anderson Sanchez - Supervis�o/Desenvolvimento" );
		vEquipeSis.add( "Alex Rodrigues - Desenvolvimento" );
		vEquipeSis.add( "Moyzes Braz - Arte gr�fica" );

	}

	public static void main( String sParams[] ) {

		try {
			Aplicativo.setLookAndFeel( "freedom.ini" );
			FreedomREP freedom = new FreedomREP();
			freedom.show();
		} catch ( Throwable e ) {
			Funcoes.criaTelaErro( "Erro de execu��o\n" + e.getMessage() );
			e.printStackTrace();
		}
	}
}
