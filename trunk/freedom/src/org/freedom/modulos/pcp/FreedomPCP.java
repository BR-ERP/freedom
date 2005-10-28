/**
 * @version 01/09/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)FreedomPCP.java <BR>
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
 * Tela principal do m�dulo de produ��o.
 *  
 */

package org.freedom.modulos.pcp;

import java.awt.event.ActionListener;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.gms.FConsRMA;
import org.freedom.modulos.gms.FConsRmaItem;
import org.freedom.modulos.gms.FRma;
import org.freedom.modulos.std.FAlmox;
import org.freedom.modulos.std.FCLFiscal;
import org.freedom.modulos.std.FConsPreco;
import org.freedom.modulos.std.FConsulta;
import org.freedom.modulos.std.FGrupo;
import org.freedom.modulos.std.FInventario;
import org.freedom.modulos.std.FKardex;
import org.freedom.modulos.std.FMarca;
import org.freedom.modulos.std.FProcessaEQ;
import org.freedom.modulos.std.FProduto;
import org.freedom.modulos.std.FRConfEstoq;
import org.freedom.modulos.std.FRDemanda;
import org.freedom.modulos.std.FREstoqueMin;
import org.freedom.modulos.std.FRInvPeps;
import org.freedom.modulos.std.FRMovProd;
import org.freedom.modulos.std.FRSaldoLote;
import org.freedom.modulos.std.FRVencLote;
import org.freedom.modulos.std.FTipoMov;
import org.freedom.modulos.std.FUnidade;
import org.freedom.telas.Aplicativo;

public class FreedomPCP extends Aplicativo implements ActionListener {
	public FreedomPCP() {

		super(
				"iconAtendimento32.gif", "splashPCP.jpg", 1, "Freedom", 5, "Planejamento e Controle da Produ��o", null,"bgFreedomSTD.jpg");
		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,null);
			addOpcao(100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1,false, null);			
				addOpcao(100100000, TP_OPCAO_MENU, "Produtos", "", 'P', 100101000, 2,false, null);
					addOpcao(100101000, TP_OPCAO_ITEM, "Classifica��o fiscal","Classifica��o Fiscal", 'l', 100120020, 3, true,FCLFiscal.class);
					addOpcao(100101000, TP_OPCAO_ITEM, "Almoxarifado", "Almoxarifado", 'x',100120030, 3, true, FAlmox.class);
					addOpcao(100101000, TP_OPCAO_ITEM, "Grupo", "Grupo", 'r', 100120040, 3,true, FGrupo.class);
					addOpcao(100101000, TP_OPCAO_ITEM, "Marca", "Marca", 'c', 100120050, 3,true, FMarca.class);
					addOpcao(100101000, TP_OPCAO_ITEM, "Unidade", "Unidade", 'U',100120060, 3, true, FUnidade.class);
					addOpcao(100101000, TP_OPCAO_ITEM, "Produto", "Produto", 'P',100120070, 3, true, FProduto.class);

					addSeparador(100100000);
		
				addOpcao(100100000, TP_OPCAO_MENU, "PCP","PCP", 'C', 100102000, 1, false, null);
					addOpcao(100102000, TP_OPCAO_ITEM, "Tipos de recursos","Tipos de recursos", 'T', 100102010, 2, true, FTipoRec.class);
					addOpcao(100102000, TP_OPCAO_ITEM, "Recursos de produ��o","Recursos de produ��o", 'R', 100102020, 2, true,FRecursos.class);
					addOpcao(100102000, TP_OPCAO_ITEM, "Fases de produ��o","Fases de produ��o", 'F', 100102030, 2, true, FFase.class);
					addOpcao(100102000, TP_OPCAO_ITEM, "Estrutura", "Estrutura de produto",'E', 100102040, 2, true, FEstrutura.class);
					addOpcao(100102000, TP_OPCAO_ITEM, "Modelos de Lote", "Modelo de lote",'E', 100102050, 2, true, FModLote.class);
		
		    addOpcao(100000000, TP_OPCAO_MENU, "Prefer�ncias", "", 'F', 110100000,1, false, null);
				addOpcao(110100000, TP_OPCAO_ITEM, "Prefer�ncias de Produ��o","Prefer�ncias de Produ��o", 'u', 110105000, 2, true,FPrefereProd.class);
			
		addOpcao(-1, TP_OPCAO_MENU, "Produ��o", "", 'P', 200000000, 0, false,null);
			addOpcao(200000000, TP_OPCAO_ITEM, "Ordens de produ��o","Ordens de produ��o", 'O', 200100000, 1, true, FOP.class);
			addSeparador(200000000);			
			addOpcao(200000000, TP_OPCAO_ITEM, "Requisi��o de material", "Requisi��o de material", 'm',200200000, 1, true, FRma.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Pesquisa requisi��o de material", "Pesquisa requisi��o de material", 'm',200300000, 1, true, FConsRMA.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Pesquisa RMA por item", "Pesquisa RMA por item", 'm',200400000, 1, true, FConsRmaItem.class);
			addOpcao(200000000, TP_OPCAO_ITEM, "Baixa RMA via C�d.Barras","Baixa RMA", 'B', 200500000, 1, true,FBaixaRMACodBar.class);

			addOpcao(-1, TP_OPCAO_MENU, "Estoque", "", 'E', 400000000, 0, false,null);
			addOpcao(400000000, TP_OPCAO_ITEM, "Kardex", "Kardex", 'K', 400100000,1, true, FKardex.class);
			addOpcao(400000000, TP_OPCAO_ITEM, "Invent�rio", "Invent�rio", 'I',	400200000, 1, true, FInventario.class);
			addOpcao(400000000, TP_OPCAO_ITEM, "Consulta estoque", "Consulta", 'C',	400300000, 1, true, FConsulta.class);
			addOpcao(400000000, TP_OPCAO_ITEM, "Consulta produto","Consulta produto", 'P', 400400000, 1, true, FConsPreco.class);
			addOpcao(400000000, TP_OPCAO_ITEM, "Tipos de movimentos","Tipo de Movimento", 'T', 400500000, 1, true, FTipoMov.class);
			addSeparador(400000000);
			addOpcao(400000000, TP_OPCAO_ITEM, "Reprocessa estoque","Reprocessa estoque", 'R', 400600000, 1, true,FProcessaEQ.class);
			addSeparador(400000000);
			addOpcao(400000000, TP_OPCAO_MENU, "Listagens", "", 'L', 400700000, 1,false, null);
				addOpcao(400700000, TP_OPCAO_ITEM, "Estoque m�nimo", "Estoque M�nimo",'s', 400701000, 2, true, FREstoqueMin.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Produtos/Movimentos","Listagem de Produtos", 'P', 400702000, 2, true,FRMovProd.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Vencimentos de lote","Vencimento Lote", 'V', 400703000, 2, true, FRVencLote.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Saldos de lote", "Saldos de Lote",'l', 400704000, 2, true, FRSaldoLote.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Demanda", "Demanda", 'D',400705000, 2, true, FRDemanda.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Confer�ncia","Confer�ncia de Estoque", 'C', 400706000, 2, true,FRConfEstoq.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Invent�rio PEPS","Invent�rio PEPS", 'I', 400707000, 2, true, FRInvPeps.class);
				addOpcao(400700000, TP_OPCAO_ITEM, "Custo de Produ��o","Custo de Produ��o", 'u', 400708000, 2, true, FRCustoProducao.class);
					
		addBotao("btEstProduto.gif", "Estrutura de produto","Estrutura de produto", 100102040, FEstrutura.class);
		addBotao("btProduto.gif", "Produtos", "Produto", 100101000,FProduto.class);
		addBotao("btOP.gif","Ordens de Produ��o","Ordens de Produ��o",200100000,FOP.class);
		addBotao("btRma.gif","Requisi��o de material", "Requisi��o de material", 200200000,FRma.class);
		addBotao("btEstoque.gif","Consulta estoque","Consulta", 400300000, FConsulta.class);
		
		ajustaMenu(); 
		
		sNomeModulo = "Produ��o";
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
			FreedomPCP freedom = new FreedomPCP();
			freedom.show();
		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execu��o");
			e.printStackTrace();
		}
	}
}