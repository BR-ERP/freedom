/**
 * @version 02/02/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)Freedomstd.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Tela principal do m�dulo standard.
 * 
 */

package org.freedom.modulos.std;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.grh.FFuncao;
import org.freedom.telas.Aplicativo;

public class FreedomSTD extends Aplicativo {
  public FreedomSTD() {
	super("iconStandart32.gif","splashSTD.jpg","Sistema Integrado de Informa��es Gerenciais",1,1, "freedom.ini", null);
	addOpcao(-1,TP_OPCAO_MENU,"Arquivo","",'A',100000000,0, false, null);
	  addOpcao(100000000,TP_OPCAO_MENU,"Tabelas","",'T',100100000,1, false, null);
	    addOpcao(100100000,TP_OPCAO_MENU,"Cliente","",'C',100101000,2, false, null);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Tipo de cliente...","TipoCli",'T',100101010,3, true, FTipoCli.class);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Classifica��o de cliente...", "Classifi��o de Clientes", 'f',100101020,3, true, FClasCli.class);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Cliente...", "Clientes",'C',100101030,3, true, FCliente.class);
		  addOpcao(100101000,TP_OPCAO_ITEM,"Tipo fiscal de cliente...","Tipo Fiscal de Cliente",'p',100101040,3, true, FTipoFiscCli.class);
		  addOpcao(100101000,TP_OPCAO_ITEM,"Cr�dito por cliente...","Cr�dito por cliente",'r',100101050,3, true, FCredCli.class);
	    addOpcao(100100000,TP_OPCAO_MENU,"Comissionado","",'C',100102000,2, false, null);
	      addOpcao(100102000,TP_OPCAO_ITEM,"Setor", "Setor",'S',100102010,3, true, FSetor.class);
	      addOpcao(100102000,TP_OPCAO_ITEM,"Comissionado", "Comissionado",'i',100102020,4, true, FVendedor.class);
	      addOpcao(100102000,TP_OPCAO_ITEM,"Classif. de Comiss�es", "Classifica��o de Comiss�es",'P',100102030,5, true, FCLComis.class);
	      addOpcao(100102000,TP_OPCAO_ITEM,"Fun��es", "Fun��es",'F',100102040,5, true, FFuncao.class);
	      
        addSeparador(100100000);
        addOpcao(100100000,TP_OPCAO_ITEM,"Moeda","Moeda",'M',100112000,2, true, FMoeda.class);
	    addOpcao(100100000,TP_OPCAO_ITEM,"Banco","Banco",'B',100113000,2, true, FBanco.class);
	    addOpcao(100100000,TP_OPCAO_ITEM,"Tipo de cobran�a","TipoCob",'o',100114000,2, true, FTipoCob.class);
	    addOpcao(100100000,TP_OPCAO_ITEM,"Plano de pagamento","PlanoPag",'s',100115000,2, true, FPlanoPag.class);
	 //   addOpcao(100100000,TP_OPCAO_ITEM,"Tipo de expositor",'x',100116000,2,true, FTipoExp.class);	    
	    addSeparador(100100000);	    
	    addOpcao(100100000,TP_OPCAO_ITEM,"Transportadora","Transportadora",'p',100117000,2, true, FTransp.class);  	    
	    addSeparador(100100000);	    
	    addOpcao(100100000,TP_OPCAO_ITEM,"Tipo de fornecedor","TipoFor",'e',100118000,2, true, FTipoFor.class);  
	    addOpcao(100100000,TP_OPCAO_ITEM,"Fornecedor","Fornecedor",'r',100119000,2, true, FFornecedor.class);    
	    addSeparador(100100000);
        addOpcao(100100000,TP_OPCAO_ITEM,"Natureza de opera��o","Naturezas",'z',100120000,2, true, FNatoPer.class);
	    addSeparador(100100000);
	    addOpcao(100100000,TP_OPCAO_MENU,"Produto","",'u',100130000,2, false, null);
          addOpcao(100130000,TP_OPCAO_ITEM,"Tratamento tribut�rio","Tratamento Tribut�rio",'t',100130010,3, true, FTratTrib.class);
          addOpcao(100130000,TP_OPCAO_ITEM,"Classifica��o fiscal","Classifica��es",'l',100130020,3, true, FCLFiscal.class);
          addOpcao(100130000,TP_OPCAO_ITEM,"Almoxarifado","Almoxarifado",'x',100130030,3, true, FAlmox.class);
          addOpcao(100130000,TP_OPCAO_ITEM,"Grupo","Grupos",'r',100130040,3, true, FGrupo.class);
          addOpcao(100130000,TP_OPCAO_ITEM,"Marca","Marcas",'c',100130050,3, true, FMarca.class);
          addOpcao(100130000,TP_OPCAO_ITEM,"Unidade","Unidades",'U',100130060,3, true, FUnidade.class);
          addOpcao(100130000,TP_OPCAO_ITEM,"Produto","Produtos",'P',100130070,3, true, FProduto.class);
	      addSeparador(100130000);
          addOpcao(100130000,TP_OPCAO_ITEM,"Similaridade","Similar",'S',100130080,3, true, FSimilar.class);
          addOpcao(100130000,TP_OPCAO_MENU,"Grade de produtos","",'G',100130090,3, false, null);        
	        addOpcao(100130090,TP_OPCAO_ITEM,"Variantes","Variantes",'V',100130091,4, true, FVariantes.class);
	        addOpcao(100130090,TP_OPCAO_ITEM,"Modelo","Modelo de Grade",'M',100130092,4, true, FModGrade.class);
            addOpcao(100130090,TP_OPCAO_ITEM,"Grade","Grade",'r',100130093,4, true, FGrade.class);
	    addSeparador(100100000);
        addOpcao(100100000,TP_OPCAO_MENU,"Pre�o","",'�',100140000,2, false, null);
          addOpcao(100140000,TP_OPCAO_ITEM,"Manuten��o de Pre�os","Manuten��o de Pre�os",'M',100140010,3, true, FManutPreco.class);
          addOpcao(100140000,TP_OPCAO_ITEM,"Copia pre�o","Copia Precos",'i',100140020,3, true, FCpProd.class);
          addOpcao(100140000,TP_OPCAO_ITEM,"Tabela de pre�o","Tabelas de Pre�os",'a',100140030,3, true, FTabPreco.class);
          addOpcao(100140000,TP_OPCAO_ITEM,"Lista de pre�o","Lista de Pre�os",'l',100140040,3, true, FRListaPreco.class);
            
	addOpcao(100000000,TP_OPCAO_MENU,"Ferramentas","",'F',100200000,1, false, null);
	  addOpcao(100200000,TP_OPCAO_ITEM,"Altera��o de numero de nota","Altera��o de doc",'A',100210000,2, true, FTrocaDoc.class);
	//  addOpcao(100200000,TP_OPCAO_ITEM,"Exportar SVV","Exporta��o SVV",'E',100220000,2,true, FSVV.class);
	  addOpcao(100200000,TP_OPCAO_MENU,"Etiquetas","",'t',100230000,2, false, null);
	     addOpcao(100230000,TP_OPCAO_ITEM,"Modelo","Modelo de etiquetas",'M',100230100,3, true, FModEtiqueta.class);
	     addOpcao(100230000,TP_OPCAO_ITEM,"Imprimir","Etiquetas",'I',100230200,3, true, FREtiqueta.class); // LOM
	  addOpcao(100200000,TP_OPCAO_ITEM,"Imp. tabelas de fornecedores","Imp. tabelas de fornecedores",'I',100240000,2, true, FImpTabFor.class);
      addOpcao(100200000,TP_OPCAO_ITEM,"Ajuste do item do or�amento","Ajuste do item do or�amento",'A',100250000,2, true, FStatusItOrc.class);
      addOpcao(100200000,TP_OPCAO_MENU,"Bloqueios e desbloqueios","",'B',100260000,2, false, null);
         addOpcao(100260000,TP_OPCAO_ITEM,"Compras","Bloqueio e desbloqueio de compras",'C',100260100,3, true, FBloqCompra.class);
         addOpcao(100260000,TP_OPCAO_ITEM,"Vendas","Bloqueio e desbloqueio de vendas",'V',100260200,3, true, FBloqVenda.class);
      addOpcao(100200000,TP_OPCAO_ITEM,"Canc. de v�nc. venda x or�amento","Canc. de v�nc. venda x or�amento",'v',100270000,2, true, FCancVendaOrc.class);
	addOpcao(100000000,TP_OPCAO_MENU,"Prefer�ncias","",'P',100300000,1, false, null);
	  addOpcao(100300000,TP_OPCAO_ITEM,"Prefer�ncias gerais","Pref. Gerais",'g',100310000,2, true, FPrefereGeral.class);
	  addOpcao(100300000,TP_OPCAO_ITEM,"S�rie de NFs","Serie NF",'N',100320000,2, true, FSerie.class);
	  addOpcao(100300000,TP_OPCAO_ITEM,"Modelo de NFs","Modelo NF",'M',100330000,2, true, FModNota.class);
	addSeparador(100100000);
	addOpcao(100000000,TP_OPCAO_MENU,"Configura��es","",'C',100400000,1, false, null);
	  addOpcao(100400000,TP_OPCAO_ITEM,"Caixa PDV","Configura��es",'C',100410000,2, true, FCaixa.class);		  
	  addOpcao(100400000,TP_OPCAO_ITEM,"Impressora","Impressoras",'I',100420000,2, true, FImpressora.class);
	  addOpcao(100400000,TP_OPCAO_ITEM,"Papel","Papeis",'P',100430000,2, true, FPapel.class);
	  addOpcao(100400000,TP_OPCAO_ITEM,"Esta��o de trabalho","Esta��es de trabalho",'E',100440000,2, true, FEstacao.class);// lom
	  addSeparador(100400000);
	  addOpcao(100400000,TP_OPCAO_ITEM,"Empresa","Empresa",'E',100450000,2, true, FEmpresa.class);	  	  

  addOpcao(-1,TP_OPCAO_MENU,"Entrada","",'E',200000000,0, false, null);
    addOpcao(200000000,TP_OPCAO_ITEM,"Compra","Compras",'C',200100000,1, true, FCompra.class);
	addOpcao(200000000,TP_OPCAO_MENU,"Listagens","",'L',200200000,1, false, null);
	  addOpcao(200200000,TP_OPCAO_ITEM,"Compras por fornecedor","Compras por Fornecedor",'f',200210000,2, true, FRComprasFor.class);

  addOpcao(-1,TP_OPCAO_MENU,"Sa�da","",'S',300000000,0, false, null);
    addOpcao(300000000,TP_OPCAO_ITEM,"Venda","Venda",'V',300100000,1, true, FVenda.class);
    addOpcao(300000000,TP_OPCAO_ITEM,"Cancela venda","Cancelamento",'C',300200000,1, true, FCancVenda.class);
    addOpcao(300000000,TP_OPCAO_ITEM,"Devolu��o de vendas","Devolu��o de vendas",'D',300300000,1, true, FDevolucao.class);
    addOpcao(300000000,TP_OPCAO_ITEM,"Lan�amento de Frete","Lan�amento de Frete",'L',300400000,1, true, FFrete.class);
	addSeparador(300000000);
	addOpcao(300000000,TP_OPCAO_ITEM,"Aprova or�amento","Aprova Orcamento",'A',300500000,1, true, FAprovaOrc.class);
	addOpcao(300000000,TP_OPCAO_ITEM,"Or�amento","Or�amento",'O',300600000,1, true, FOrcamento.class);
	addOpcao(300000000,TP_OPCAO_ITEM,"Pesquisa Or�amento","Pesquisa Or�amento",'P',300700000,1, true, FConsOrc.class);
	addSeparador(300000000);
	addOpcao(300000000,TP_OPCAO_ITEM,"Romaneio","Romaneio",'R',300800000,1, true, FRomaneio.class);  		
	//addOpcao(300000000,TP_OPCAO_ITEM,"Lan�amento de expositores","LancaExp",'x',300900000,1, true, FLancaExp.class);
	addOpcao(300000000,TP_OPCAO_MENU,"Listagens","",'s',301000000,1, false, null);				
      addOpcao(301000000,TP_OPCAO_ITEM,"Resumo di�rio","Resumo Di�rio",'R',301010000,2, true, FRResumoDiario.class);
	  addOpcao(301000000,TP_OPCAO_ITEM,"Vendas geral","Vendas em Geral",'V',301020000,2, true, FRVendasGeral.class);
	  addOpcao(301000000,TP_OPCAO_ITEM,"Vendas f�sico","F�sico de Vendas",'d',301030000,2, true, FRVendasFisico.class);
	  addOpcao(301000000,TP_OPCAO_ITEM,"Vendas detalhado","Vendas Detalhadas",'n',301040000,2, true, FRVendasDet.class);	        
	  addOpcao(301000000,TP_OPCAO_ITEM,"Vendas por �tem","Vendas por Item",'e',301050000,2, true, FRVendasItem.class);
	  addOpcao(301000000,TP_OPCAO_ITEM,"M�dia de vendas por �tem","Media de vendas por item",'o',301060000,2, true, FRMediaItem.class);
	  addOpcao(301000000,TP_OPCAO_ITEM,"Ultimas Vendas por Cliente","Ultimas Vendas por Cliente",'C',301070000,2, true, FRUltimaVenda.class);
	  addOpcao(301000000,TP_OPCAO_ITEM,"Vendas por Setor","Vendas por Setor",'t',301080000,2, true, FRVendaSetor.class);
	  addOpcao(301000000,TP_OPCAO_ITEM,"Gerenciamento de contas","Gerenciamento de contas",'i',301090000,2, true, FRGerContas.class);
	  
   addOpcao(300000000,TP_OPCAO_MENU,"Gr�ficos","",'G',301100000,1, false, null);	  
      addOpcao(301100000,TP_OPCAO_ITEM,"Evolu��o de vendas","Evolu��o de vendas",'E',301110000,2, true, FREvoluVendas.class);
   addSeparador(300000000);
   addOpcao(300000000,TP_OPCAO_MENU,"Consultas","",'n',301200000,1, false, null);	  
      addOpcao(301200000,TP_OPCAO_ITEM,"Pre�os","Consulta de pre�os",'P',301210000,2, true, FConsPreco.class);

  addOpcao(-1,TP_OPCAO_MENU,"Pagar","",'P',400000000,0, false, null);
    addOpcao(400000000,TP_OPCAO_ITEM,"Comiss�o","Comissao",'C',400100000,1, true, FManutComis.class);
    addOpcao(400000000,TP_OPCAO_ITEM,"Manuten��o","Manuten��o de contas a pagar",'M',400200000,1, true, FManutPag.class);
	addOpcao(400000000,TP_OPCAO_MENU,"Listagens","",'s',400300000,1, false, null);				
	  addOpcao(400300000,TP_OPCAO_ITEM,"Pagar/Pagas","Pagar/Pagas",'P',400410000,2, true, FRPagar.class);
	  addOpcao(400300000,TP_OPCAO_ITEM,"Comiss�es","Comiss�o",'C',400420000,2, true, FRComissoes.class);

  addOpcao(-1,TP_OPCAO_MENU,"Receber","",'R',500000000,0, false, null);
    addOpcao(500000000,TP_OPCAO_ITEM,"Manuten��o","Manuten��o de contas a receber",'M',500100000,1, true, FManutRec.class);
	addOpcao(500000000,TP_OPCAO_ITEM,"CNAB","",'N',500200000,1, true, null);    
    addOpcao(500000000,TP_OPCAO_MENU,"Listagens","",'s',500300000,1, false, null);				
  	  addOpcao(500300000,TP_OPCAO_ITEM,"Receber/Recebidas","Receber/Recebidas",'R',500310000,2, true, FRReceber.class);
	  addOpcao(500300000,TP_OPCAO_ITEM,"Inadimplentes","Inadimplentes",'I',500320000,2, true, FRInadimplentes.class);
	  addOpcao(500300000,TP_OPCAO_ITEM,"Bordero de cobran�a","Bordero de cobran�a",'B',500330000,2, true, FRBordero.class);

  addOpcao(-1,TP_OPCAO_MENU,"Financeiro","",'F',600000000,0, false, null);
    addOpcao(600000000,TP_OPCAO_MENU,"Boleto","",'B',600100000,1, false, null);
      addOpcao(600100000,TP_OPCAO_ITEM,"Modelo","Modelo de boleto",'M',600110000,2, true, FModBoleto.class);
      addOpcao(600100000,TP_OPCAO_ITEM,"Imprimir","Boleto",'I',600120000,2, true, FRBoleto.class);
    addOpcao(600000000,TP_OPCAO_ITEM,"Banco","Banco",'a',600200000,1, true, FBanco.class);
    addOpcao(600000000,TP_OPCAO_ITEM,"Planejamento","Planejamento",'P',600300000,1, true, FPlanejamento.class);
    addOpcao(600000000,TP_OPCAO_ITEM,"Centro de custo","Centro de Custos",'C',600400000,1, true, FCentroCusto.class);    
	addOpcao(600000000,TP_OPCAO_ITEM,"Contas","Contas",'o',600500000,1, true, FConta.class);
	addOpcao(600000000,TP_OPCAO_ITEM,"Lan�amentos","Lan�amentos",'L',600600000,1, true, FLanca.class);
	addSeparador(600000000);
	addOpcao(600000000,TP_OPCAO_ITEM,"Tipo de cr�dito","Tipo de cr�dito",'L',600700000,1, true, FTipoCred.class);
	addOpcao(600000000,TP_OPCAO_ITEM,"Libera��o de cr�dito","Libera��o de cr�dito",'i',600800000,1, true, FLiberaCredito.class);
	addSeparador(600000000);
	addOpcao(600000000,TP_OPCAO_ITEM,"Tabela de juros","Tabelas de juros",'T',600900000,1, true, FTabJuros.class);
	addSeparador(600000000);
	addOpcao(600000000,TP_OPCAO_ITEM,"Reprocessa saldo","Reprocessamento de saldos",'R',601000000,1, true, FProcessaSL.class);
	addSeparador(600000000);
    addOpcao(600000000,TP_OPCAO_MENU,"Listagens","",'s',601100000,1, false, null);				
  	  addOpcao(601100000,TP_OPCAO_ITEM,"Extrato","Extrato",'E',601110000,2, true, FRExtrato.class);
	  addOpcao(601100000,TP_OPCAO_ITEM,"Balancete","Balancete",'B',601120000,2, true, FRBalancete.class);
	  addOpcao(601100000,TP_OPCAO_ITEM,"Relat�rio financeiro por C.C.","Relatorio Financeiro por C.C.",'R',601130000,2, true, FRCentroCusto.class);
      addOpcao(601100000,TP_OPCAO_ITEM,"Raz�o financeiro","Raz�o financeiro",'z',601140000,2, true, FRRazaoFin.class);
      addOpcao(601100000,TP_OPCAO_ITEM,"Fluxo de caixa","",'F',601150000,2, true, FRFluxoCaixa.class);
      addOpcao(601100000,TP_OPCAO_ITEM,"Ponto de equilibrio","",'q',601160000,2, true, FRPontoEqui.class);      
	addOpcao(600000000,TP_OPCAO_MENU,"Gr�ficos","Fluxo de caixa",'G',601200000,1, false, null);
	  addOpcao(601200000,TP_OPCAO_ITEM,"Balancete Gr�fico","Balancete Gr�fico",'G',601210000,2, true, FRBalanceteGrafico.class);
	  addOpcao(601200000,TP_OPCAO_ITEM,"Gr�fico financeiro por C.C","Gr�fico Financeiro por C.C",'f',601220000,2, true, FRGraficoCC.class);
  
  addOpcao(-1,TP_OPCAO_MENU,"Estoque","",'E',700000000,0, false, null);
    addOpcao(700000000,TP_OPCAO_ITEM,"Kardex","Kardex",'K',700100000,1, true, FKardex.class);
	addOpcao(700000000,TP_OPCAO_ITEM,"Invent�rio","Invent�rio",'I',700200000,1, true, FInventario.class);
	addOpcao(700000000,TP_OPCAO_ITEM,"Consulta estoque","Consulta",'C',700300000,1, true, FConsulta.class);
	addOpcao(700000000,TP_OPCAO_ITEM,"Consulta produto","Consulta produto",'P',700400000,1, true, FConsProd.class);
	addOpcao(700000000,TP_OPCAO_ITEM,"Tipos de movimentos","Tipo de Movimento",'T',700500000,1, true, FTipoMov.class);		
	addSeparador(700000000);
	addOpcao(700000000,TP_OPCAO_ITEM,"Reprocessa estoque","Reprocessa estoque",'R',700600000,1, true, FProcessaEQ.class);
	addSeparador(700000000);
	addOpcao(700000000,TP_OPCAO_MENU,"Listagens","",'L',700700000,1, false, null);
  	  addOpcao(700700000,TP_OPCAO_ITEM,"Estoque m�nimo","Estoque M�nimo",'s',700701000,2, true, FREstoqueMin.class);
  	  addOpcao(700700000,TP_OPCAO_ITEM,"Produtos/Movimentos","Listagem de Produtos",'P',700702000,2, true, FRMovProd.class);
	  addOpcao(700700000,TP_OPCAO_ITEM,"Vencimentos de lote","Vencimento Lote",'V',700703000,2, true, FRVencLote.class);
	  addOpcao(700700000,TP_OPCAO_ITEM,"Saldos de lote","Saldos de Lote",'l',700704000,2, true, FRSaldoLote.class);
	  addOpcao(700700000,TP_OPCAO_ITEM,"Demanda","Demanda",'D',700705000,2, true, FRDemanda.class);
	  addOpcao(700700000,TP_OPCAO_ITEM,"Confer�ncia","Confer�ncia de Estoque",'C',700706000,2, true, FRConfEstoq.class);
	  addOpcao(700700000,TP_OPCAO_ITEM,"Invent�rio","Invent�rio",'I',700707000,2, true, FRInvPeps.class);
	  
  addOpcao(-1,TP_OPCAO_MENU,"Fiscal","",'F',800000000,0, false, null);
    addOpcao(800000000,TP_OPCAO_ITEM,"Gerar","Gera Fiscal",'G',800100000,1, true, FGeraFiscal.class);
	addOpcao(800000000,TP_OPCAO_ITEM,"Regras fiscais","Regras Fiscais",'R',800200000,1, true, FRegraFiscal.class);
	addOpcao(800000000,TP_OPCAO_ITEM,"Sintegra","Gera Arquivo Sintegra",'S',800300000,1, true, FSintegra.class);
	addOpcao(800000000,TP_OPCAO_ITEM,"Tabela de Al�quotas","Tabela de al�quotas",'T',800400000,1, true, FTabICMS.class);
	addOpcao(800000000,TP_OPCAO_ITEM,"Mensagens","Mensagens",'M',800500000,1, true, FMensagem.class);
	addOpcao(800000000,TP_OPCAO_MENU,"Listagens","",'L',800600000,1, false, null);
	  addOpcao(800600000,TP_OPCAO_ITEM,"ICMS sobre vendas","Icms Vendas e Compras",'I',800601000,2, true, FRVendasIcms.class);
	  addOpcao(800600000,TP_OPCAO_ITEM,"Impostos sobre servi�os","Impostos sobre servi�os",'S',800602000,2, true, FRImpServ.class);
	  addOpcao(800600000,TP_OPCAO_ITEM,"Pis e cofins","Pis e cofins",'P',800603000,2, true, FRPisCofins.class);

  addBotao("btCliente.gif","Cliente","Clientes", 100101030, FCliente.class);
  addBotao("btSaida.gif","Venda","Venda", 300100000, FVenda.class);
  addBotao("btEntrada.gif","Compra","Compras", 200100000, FCompra.class); 
  addBotao("btContaPagar.gif","Contas a pagar","Manuten��o de contas a pagar", 400200000, FManutPag.class);
  addBotao("btContaReceber.gif","Contas a receber","Manuten��o de contas a receber", 500100000, FManutRec.class);  
  addBotao("btLancamentoFin.gif","Lan�amentos financeiros","Lan�amentos", 600600000, FLanca.class);
  addBotao("btEstoque.gif","Consulta estoque","Consulta", 700300000, FConsulta.class);   
  addBotao("btProduto.gif","Cadastro de produtos","Produtos", 100130070, FProduto.class);
  addBotao("btEstProduto.gif","Consulta produto","Consulta produto", 700400000, FConsProd.class);
  //addBotao("btEmprestimo.gif","Cadastro de similaridades",100120080);

  ajustaMenu();
 	
  sNomeModulo = "Administrativo";
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
    FreedomSTD freedom = new FreedomSTD();
    freedom.show();
	} catch (Throwable e) {
		Funcoes.criaTelaErro("Erro de execu��o");
		e.printStackTrace();
	}
  }
}