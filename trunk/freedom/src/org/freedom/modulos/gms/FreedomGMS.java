/**
 * @version 30/01/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.gms <BR>
 * Classe: @(#)FreedomGMS.java <BR>
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
 * Tela principal para o m�dulo de gest�o de materiais.
 * 
 */

package org.freedom.modulos.gms;

import org.freedom.modulos.std.FAlmox;
import org.freedom.modulos.std.FAprovaOrc;
import org.freedom.modulos.std.FCLFiscal;
import org.freedom.modulos.std.FCancVenda;
import org.freedom.modulos.std.FCliente;
import org.freedom.modulos.std.FCompra;
import org.freedom.modulos.std.FConsOrc;
import org.freedom.modulos.std.FConsPreco;
import org.freedom.modulos.std.FConsulta;
import org.freedom.modulos.std.FDevolucao;
import org.freedom.modulos.std.FEstacao;
import org.freedom.modulos.std.FFornecedor;
import org.freedom.modulos.std.FFrete;
import org.freedom.modulos.std.FGrade;
import org.freedom.modulos.std.FGrupo;
import org.freedom.modulos.std.FImpTabFor;
import org.freedom.modulos.std.FImpressora;
import org.freedom.modulos.std.FInventario;
import org.freedom.modulos.std.FKardex;
import org.freedom.modulos.std.FLancaExp;
import org.freedom.modulos.std.FMarca;
import org.freedom.modulos.std.FModEtiqueta;
import org.freedom.modulos.std.FModGrade;
import org.freedom.modulos.std.FModNota;
import org.freedom.modulos.std.FNatoPer;
import org.freedom.modulos.std.FOrcamento;
import org.freedom.modulos.std.FPapel;
import org.freedom.modulos.std.FPlanoPag;
import org.freedom.modulos.std.FPrefereGeral;
import org.freedom.modulos.std.FProcessaEQ;
import org.freedom.modulos.std.FProduto;
import org.freedom.modulos.std.FRComprasFor;
import org.freedom.modulos.std.FRConfEstoq;
import org.freedom.modulos.std.FRDemanda;
import org.freedom.modulos.std.FREstoqueMin;
import org.freedom.modulos.std.FREtiqueta;
import org.freedom.modulos.std.FREvoluVendas;
import org.freedom.modulos.std.FRInvPeps;
import org.freedom.modulos.std.FRMediaItem;
import org.freedom.modulos.std.FRMovProd;
import org.freedom.modulos.std.FRResumoDiario;
import org.freedom.modulos.std.FRSaldoLote;
import org.freedom.modulos.std.FRUltimaVenda;
import org.freedom.modulos.std.FRVencLote;
import org.freedom.modulos.std.FRVendaSetor;
import org.freedom.modulos.std.FRVendasDet;
import org.freedom.modulos.std.FRVendasFisico;
import org.freedom.modulos.std.FRVendasGeral;
import org.freedom.modulos.std.FRVendasItem;
import org.freedom.modulos.std.FRomaneio;
import org.freedom.modulos.std.FSerie;
import org.freedom.modulos.std.FStatusItOrc;
import org.freedom.modulos.std.FTipoCob;
import org.freedom.modulos.std.FTipoFor;
import org.freedom.modulos.std.FTipoMov;
import org.freedom.modulos.std.FTransp;
import org.freedom.modulos.std.FTratTrib;
import org.freedom.modulos.std.FUnidade;
import org.freedom.modulos.std.FVariantes;
import org.freedom.modulos.std.FVenda;
import org.freedom.telas.Aplicativo;



public class FreedomGMS extends Aplicativo {

	public FreedomGMS() {
		super("iconAtendimento32.gif","splashGMS.jpg","FreedomGMS - M�dulo de gerenciamento de mateirais e servi�os",1,8);      		
		
		addOpcao(-1,TP_OPCAO_MENU,"Arquivo","",'A',100000000,0, false, null);
			addOpcao(100000000,TP_OPCAO_MENU,"Cadastros","",'T',100100000,1, false, null);
		    	addOpcao(100100000,TP_OPCAO_MENU,"Clientes","",'C',100101000,2, false, null);
		    		addOpcao(100101000,TP_OPCAO_ITEM,"Clientes", "",'C',100101010,3, true, null);
		        addSeparador(100100000);	    
		    	addOpcao(100100000,TP_OPCAO_MENU,"Fornecedores","",'C',100201000,2, false, null);
		    		addOpcao(100201000,TP_OPCAO_ITEM,"Tipos de fornecedores","",'e',100201010,3, true, null);  
		    		addOpcao(100201000,TP_OPCAO_ITEM,"Fornecedores","",'r',100201020,3, true, null);    
		        addSeparador(100100000);
		        addOpcao(100100000,TP_OPCAO_MENU,"Produtos","",'u',100301000,2, false, null);
		        	addOpcao(100301000,TP_OPCAO_ITEM,"Tratamentos tribut�rios","",'t',100301010,3, true, null);
		        	addOpcao(100301000,TP_OPCAO_ITEM,"Classifica��es fiscais","",'l',100130020,3, true, null);
		        	addOpcao(100301000,TP_OPCAO_ITEM,"Almoxarifados","",'x',100130030,3, true, null);
		        	addOpcao(100301000,TP_OPCAO_ITEM,"Grupos","",'r',100130040,3, true, null);
		        	addOpcao(100301000,TP_OPCAO_ITEM,"Marcas","",'c',100130050,3, true, null);
		        	addOpcao(100301000,TP_OPCAO_ITEM,"Unidades","",'U',100130060,3, true, null);
			      	addOpcao(100301000,TP_OPCAO_ITEM,"Kits de produtos","",'K',100130070,3, true, null);
		        	addOpcao(100301000,TP_OPCAO_ITEM,"Produtos","",'P',100130080,3, true, null);
		        	addSeparador(100301000);
		        	addOpcao(100301000,TP_OPCAO_MENU,"Grade de produtos","",'G',100130090,3, false, null);        
		        		addOpcao(100130090,TP_OPCAO_ITEM,"Variantes","",'V',100130091,4, true, null);
		        		addOpcao(100130090,TP_OPCAO_ITEM,"Modelos","",'M',100130092,4, true, null);
		        		addOpcao(100130090,TP_OPCAO_ITEM,"Grades","",'r',100130093,4, true, null);
		        addSeparador(100100000);	  		    		    		    		  	   
		        addOpcao(100100000,TP_OPCAO_MENU,"Outros cadastros","",'C',100401000,2, false, null);		    
		        	addOpcao(100401000,TP_OPCAO_ITEM,"Transportadoras","",'p',100401010,3, true, null);  	    
		        	addSeparador(100401000);
		        	addOpcao(100401000,TP_OPCAO_ITEM,"Tipo de cobran�a","",'o',100401020,3, true, null);
		        	addOpcao(100401000,TP_OPCAO_ITEM,"Plano de pagamento","",'s',100401030,3, true, null);
		        	addSeparador(100401000);
		        	addOpcao(100401000,TP_OPCAO_ITEM,"Natureza de opera��o","",'z',100401040,3, true, null);
		        	addSeparador(100401000); 
				    addOpcao(100100000,TP_OPCAO_MENU,"Atribui��es","",'t',100401050,3, false, null);
				      	addOpcao(100401050,TP_OPCAO_ITEM,"Atribui��es","",'r',100401051,4, true, null);
				      	addOpcao(100401050,TP_OPCAO_ITEM,"Atribui��es por usu�rio","",'u',100401052,4, true, null);		        	
	            
		    addOpcao(100000000,TP_OPCAO_MENU,"Ferramentas","",'F',100200000,1, false, null);
		    	addOpcao(100200000,TP_OPCAO_MENU,"Etiquetas","",'t',100230000,2, false, null);
		    		addOpcao(100230000,TP_OPCAO_ITEM,"Modelo","",'M',100230100,3, true, null);
		    		addOpcao(100230000,TP_OPCAO_ITEM,"Imprimir","",'I',100230200,3, true, null); // LOM
		        addSeparador(100200000);
		    	addOpcao(100200000,TP_OPCAO_ITEM,"Imp. tabelas de fornecedores","",'I',100240000,2, true, null);
	        	addSeparador(100200000);
		        addOpcao(100200000,TP_OPCAO_ITEM,"Ajuste do item do or�amento","",'A',100250000,2, true, null);

		    addOpcao(100000000,TP_OPCAO_MENU,"Configura��es","",'C',100300000,1, false, null);
		    	addOpcao(100300000,TP_OPCAO_ITEM,"Impressora","",'I',100310000,2, true, null);
		    	addOpcao(100300000,TP_OPCAO_ITEM,"Papel","",'P',100320000,2, true, null);
		    	addOpcao(100300000,TP_OPCAO_ITEM,"Esta��o","",'E',100330000,2, true, null);// lom
		    	addSeparador(100300000);
		    	addOpcao(100300000,TP_OPCAO_MENU,"Prefer�ncias","",'P',100340000,2, false, null);
		    		addOpcao(100340000,TP_OPCAO_ITEM,"Prefer�ncias gerais","",'g',100340010,3, true, null);
		    		addOpcao(100300000,TP_OPCAO_ITEM,"S�rie de NFs","",'N',100340020,3, true, null);
		    		addOpcao(100300000,TP_OPCAO_ITEM,"Modelo de NFs","",'M',100340030,3, true, null);
  
	    addOpcao(-1,TP_OPCAO_MENU,"Entrada","",'E',200000000,0, false, null);
	    	addOpcao(200000000,TP_OPCAO_ITEM,"Solicita��o de Compra","",'S',200300000,1, true, null);
	    	addOpcao(200000000,TP_OPCAO_ITEM,"Aprova Solicita��o de Compra","",'A',200400000,1, true, null);
	    	addOpcao(200000000,TP_OPCAO_ITEM,"Pesquisa Solicita��es de Compra","",'P',200500000,1, true, null);
	    	addSeparador(200000000);
	    	addOpcao(200000000,TP_OPCAO_ITEM,"Cota��o de Pre�os","",'T',200600000,1, true, null);
	    	addOpcao(200000000,TP_OPCAO_ITEM,"Aprova Cota��o de Pre�os","",'P',200700000,1, true, null);
	    	addSeparador(200000000);
	    	addOpcao(200000000,TP_OPCAO_ITEM,"Compra","",'C',200100000,1, true, null);
	    	addOpcao(200000000,TP_OPCAO_MENU,"Listagens","",'L',200200000,1, false, null);
	    	addOpcao(200200000,TP_OPCAO_ITEM,"Compras por fornecedor","",'F',200210000,2, true, null);

	    addOpcao(-1,TP_OPCAO_MENU,"Sa�da","",'S',300000000,0, false, null);
	    	addOpcao(300000000,TP_OPCAO_ITEM,"Venda","",'V',300100000,1, true, null);
	    	addOpcao(300000000,TP_OPCAO_ITEM,"Cancela venda","",'C',300200000,1, true, null);
	    	addOpcao(300000000,TP_OPCAO_ITEM,"Devolu��o de vendas","",'D',300300000,1, true, null);
	    	addOpcao(300000000,TP_OPCAO_ITEM,"Lan�amento de Frete","",'L',300400000,1, true, null);
	    	addSeparador(300000000);
	    	addOpcao(300000000,TP_OPCAO_ITEM,"Aprova or�amento","",'A',300500000,1, true, null);
	    	addOpcao(300000000,TP_OPCAO_ITEM,"Or�amento","",'O',300600000,1, true, null);
	    	addOpcao(300000000,TP_OPCAO_ITEM,"Pesquisa Or�amento","",'P',300700000,1, true, null);
	    	addSeparador(300000000);
	    	addOpcao(300000000,TP_OPCAO_ITEM,"Romaneio","",'R',300800000,1, true, null);  		
	    	//addOpcao(300000000,TP_OPCAO_ITEM,"Lan�amento de expositores",'x',300900000,1,true);
	    	addOpcao(300000000,TP_OPCAO_MENU,"Listagens","",'s',301000000,1, false, null);				
	    	addOpcao(301000000,TP_OPCAO_ITEM,"Resumo di�rio","",'R',301010000,2, true, null);
	    	addOpcao(301000000,TP_OPCAO_ITEM,"Vendas geral","",'V',301020000,2, true, null);
	    	addOpcao(301000000,TP_OPCAO_ITEM,"Vendas f�sico","",'d',301030000,2, true, null);
	    	addOpcao(301000000,TP_OPCAO_ITEM,"Vendas detalhado","",'n',301040000,2, true, null);	        
	    	addOpcao(301000000,TP_OPCAO_ITEM,"Vendas por �tem","",'e',301050000,2, true, null);
	    	addOpcao(301000000,TP_OPCAO_ITEM,"M�dia de vendas por �tem","",'o',301060000,2, true, null);
	    	addOpcao(301000000,TP_OPCAO_ITEM,"Ultimas Vendas por Cliente","",'C',301070000,2, true, null);
	    	addOpcao(301000000,TP_OPCAO_ITEM,"Vendas por Setor","",'t',301080000,2, true, null);
	    	addOpcao(300000000,TP_OPCAO_MENU,"Gr�ficos","",'G',301100000,1, false, null);	  
	    		addOpcao(301100000,TP_OPCAO_ITEM,"Evolu��o de vendas","",'E',301110000,2, true, null);
	        addSeparador(300000000);
	        addOpcao(300000000,TP_OPCAO_MENU,"Consultas","",'n',301200000,1, false, null);	  
	        addOpcao(301200000,TP_OPCAO_ITEM,"Pre�os","",'P',301210000,2, true, null);
	  
	    addOpcao(-1,TP_OPCAO_MENU,"Estoque","",'E',400000000,0, false, null);
	    	addOpcao(400000000,TP_OPCAO_ITEM,"Kardex","",'K',400100000,1, true, null);
	    	addOpcao(400000000,TP_OPCAO_ITEM,"Invent�rio","",'I',400200000,1, true, null);
	    	addOpcao(400000000,TP_OPCAO_ITEM,"Consulta estoque","",'C',400300000,1, true, null);
	    	addOpcao(400000000,TP_OPCAO_ITEM,"Consulta produto","",'P',400400000,1, true, null);
	    	addOpcao(400000000,TP_OPCAO_ITEM,"Tipos de movimentos","",'T',400500000,1, true, null);		
	    	addSeparador(400000000);
		addOpcao(400000000,TP_OPCAO_ITEM,"Reprocessa estoque","",'R',400600000,1, true, null);
		addSeparador(400000000);
		addOpcao(400000000,TP_OPCAO_MENU,"Listagens","",'L',400700000,1, false, null);
	  	  addOpcao(400700000,TP_OPCAO_ITEM,"Estoque m�nimo","",'s',400701000,2, true, null);
	  	  addOpcao(400700000,TP_OPCAO_ITEM,"Produtos/Movimentos","",'P',400702000,2, true, null);
		  addOpcao(400700000,TP_OPCAO_ITEM,"Vencimentos de lote","",'V',400703000,2, true, null);
		  addOpcao(400700000,TP_OPCAO_ITEM,"Saldos de lote","",'l',400704000,2, true, null);
		  addOpcao(400700000,TP_OPCAO_ITEM,"Demanda","",'D',400705000,2, true, null);
		  addOpcao(400700000,TP_OPCAO_ITEM,"Confer�ncia","",'C',400706000,2, true, null);
		  addOpcao(400700000,TP_OPCAO_ITEM,"Invent�rio PEPS","",'I',400707000,2, true, null);		
				
	    ajustaMenu();
    }

    public void execOpcao(int iOpcao) {


        if (iOpcao==100101010) {
            if (telaPrincipal.temTela("Clientes")==false) {
              FCliente tela = new FCliente();
              telaPrincipal.criatela("Clientes",tela,con);
            }
          }
        else if (iOpcao==100201010) {
            if (telaPrincipal.temTela("TipoFor")==false) {
              FTipoFor tela = new FTipoFor();
              telaPrincipal.criatela("TipoFor",tela,con);
            } 
          }
        else if (iOpcao==100201020) {
            if (telaPrincipal.temTela("Fornecedor")==false) {
              FFornecedor tela = new FFornecedor();
              telaPrincipal.criatela("Fornecedor",tela,con);
            } 
        }
        else if (iOpcao==100301010) {
              if (telaPrincipal.temTela("Tratamento Tribut�rio")==false) {
                FTratTrib tela = new FTratTrib();
                telaPrincipal.criatela("Tratamento Tribut�rio",tela,con);
              } 
        }
        else if (iOpcao==100130020) {
              if (telaPrincipal.temTela("Classifica��es")==false) {
                FCLFiscal tela = new FCLFiscal();
                tela.setTelaPrim(telaPrincipal);
                telaPrincipal.criatela("Classifica��es",tela,con);
              } 
        }                           
	    else if (iOpcao==100130030) {
	        if (telaPrincipal.temTela("Almoxarifado")==false) {
	          FAlmox tela = new FAlmox();
	          telaPrincipal.criatela("Almoxarifado",tela,con);
	        }
	    }
	    else if (iOpcao==100130040) {
	        if (telaPrincipal.temTela("Grupos")==false) {
	          FGrupo tela = new FGrupo();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("Grupos",tela,con);
	        }
	    }	 
	    else if (iOpcao==100130050) {
	        if (telaPrincipal.temTela("Marcas")==false) {
	          FMarca tela = new FMarca();
	          telaPrincipal.criatela("Marcas",tela,con);
	        } 
	    }
	    else if (iOpcao==100130060) {
	        if (telaPrincipal.temTela("Unidades")==false) {
	          FUnidade tela = new FUnidade();
	          telaPrincipal.criatela("Unidades",tela,con);
	        }
	    }
	    else if (iOpcao==100130070) {
	        if (telaPrincipal.temTela("Kits de produtos")==false) {
	          FGrupo tela = new FGrupo();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("Kits de produtos",tela,con);
	        }
	    }
	    else if (iOpcao==100130080){
	        if (telaPrincipal.temTela("Produtos")==false) {
	          FProduto tela = new FProduto();
	          telaPrincipal.criatela("Produtos",tela,con);
	        }
	    }	    	    	    
	    else if (iOpcao==100130091) {
	        if (telaPrincipal.temTela("Variantes")==false) {
	          FVariantes tela = new FVariantes();
	          telaPrincipal.criatela("Variantes",tela,con);
	        } 
	    }
	    else if (iOpcao==100130092) {
	        if (telaPrincipal.temTela("Modelo de Grade")==false) {
	          FModGrade tela = new FModGrade();
	          telaPrincipal.criatela("Modelo de Grade",tela,con);
	        } 
	    }
	    else if (iOpcao==100130093) {
	        if (telaPrincipal.temTela("Grade")==false) {
	          FGrade tela = new FGrade();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("Grade",tela,con);
	        }
	    }	    	    
	    else if (iOpcao==100401010) {
	          if (telaPrincipal.temTela("Transportadora")==false) {
	            FTransp tela = new FTransp();
	            telaPrincipal.criatela("Transportadora",tela,con);
	          } 
	    }	    
	    else if (iOpcao==100401020) {
	        if (telaPrincipal.temTela("TipoCob")==false) {
	          FTipoCob tela = new FTipoCob();
	          telaPrincipal.criatela("TipoCob",tela,con);
	        } 
	    }	    
	    else if (iOpcao==100401030) {
	        if (telaPrincipal.temTela("PlanoPag")==false) {
	          FPlanoPag tela = new FPlanoPag();
	          telaPrincipal.criatela("PlanoPag",tela,con);
	        } 
	    }	    
	    else if (iOpcao==100401040) {
	        if (telaPrincipal.temTela("Naturezas")==false) {
	          FNatoPer tela = new FNatoPer();
	          tela.setTelaPrim(telaPrincipal);
	          telaPrincipal.criatela("Naturezas",tela,con);
	        } 
	    }	    
		else if (iOpcao==100401051) {
			if (telaPrincipal.temTela("Atribui��o")==false) {
				FAtribuicao tela = new FAtribuicao();
				telaPrincipal.criatela("Atribui��o",tela,con);
			}
		}
		else if (iOpcao==100401052) {
			if (telaPrincipal.temTela("Atribui��o por usu�rio")==false) {
				FAtribUsu tela = new FAtribUsu();
				//tela.setConexao(con);
				telaPrincipal.criatela("Atribui��o por usu�rio",tela,con);
			}
		}
	    else if (iOpcao==100230100) {
	    	if (telaPrincipal.temTela("Modelo de etiquetas")==false) {
	    		FModEtiqueta tela = new FModEtiqueta();		
	    		telaPrincipal.criatela("Modelo de etiquetas",tela,con);
	    	}
	    }	
	    else if (iOpcao==100230200) {                                      // LOM
	    	if (telaPrincipal.temTela("Etiquetas")==false) {              // LOM
	    		FREtiqueta tela = new FREtiqueta ();                       // LOM	
	    		//tela.setConexao(con);                                    // LOM
	    		telaPrincipal.criatela("Etiquetas",tela,con);                // LOM
	    	}
	    }
	    else if (iOpcao==100240000) {
	     	if (telaPrincipal.temTela("Imp. tabelas de fornecedores")==false) {
	     		FImpTabFor tela = new FImpTabFor();		
	     		//tela.setConexao(con);
	     		telaPrincipal.criatela("Imp. tabelas de fornecedores",tela,con);
	     	}
	     }	  
	    else if (iOpcao==100250000) {
	        if (telaPrincipal.temTela("Ajuste do item do or�amento")==false) {
	            FStatusItOrc tela = new FStatusItOrc();     
	            //tela.setConexao(con);
	            telaPrincipal.criatela("Ajuste do item do or�amento",tela,con);
	        }
	    }   	    
	    else if (iOpcao==100310000) {
	        if (telaPrincipal.temTela("Impressoras")==false) {
	          FImpressora tela = new FImpressora();
	          telaPrincipal.criatela("Impressoras",tela,con);
	        }
	    }    
	    else if (iOpcao==100320000) {
	        if (telaPrincipal.temTela("Papeis")==false) {
	          FPapel tela = new FPapel();
	          telaPrincipal.criatela("Papeis",tela,con);
	        }
	    }
	    else if (iOpcao==100330000) {// LOM
	          if (telaPrincipal.temTela("Estacao")==false) {// LOM
	            FEstacao tela = new FEstacao();// LOM
	            telaPrincipal.criatela("Estacao",tela,con);// LOM
	          }
	    }
		else if (iOpcao==100340010) {
		      if (telaPrincipal.temTela("Pref. Gerais")==false) {
		        FPrefereGeral tela = new FPrefereGeral();
		        telaPrincipal.criatela("Pref. Garais",tela,con);
		      }
		}
	    else if (iOpcao==100340020) {
	        if (telaPrincipal.temTela("Serie NF")==false) {
	          FSerie tela = new FSerie();
	          telaPrincipal.criatela("Serie NF",tela,con);
	        } 
	    }
	    else if (iOpcao==100340030) {
	        if (telaPrincipal.temTela("Modelo NF")==false) {
	          FModNota tela = new FModNota();
	          telaPrincipal.criatela("Modelo NF",tela,con);
	        } 
	    }    
	    else if (iOpcao==200100000) {
	        if (telaPrincipal.temTela("Compra")==false) {
	          FCompra tela = new FCompra();
	          telaPrincipal.criatela("Compra",tela,con);
	        } 
	    }    
	    else if (iOpcao==200210000) {
	        if (telaPrincipal.temTela("Compras por Fornecedor")==false) {
	          FRComprasFor tela = new FRComprasFor();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("Compras por Fornecedor",tela,con);
	        }
	    }	    
	    
	    else if (iOpcao==200300000) {
	        if (telaPrincipal.temTela("Solicita��o de Compra")==false) {
	          FSolicitacaoCompra tela = new FSolicitacaoCompra();
	          telaPrincipal.criatela("Solicita��o de Compra",tela,con);
	        } 
	      }
	    else if (iOpcao==200400000) {
	        if (telaPrincipal.temTela("Aprova Solicita��o de Compra")==false) {
	          FAprovaSolicitacaoCompra tela = new FAprovaSolicitacaoCompra();
	          telaPrincipal.criatela("Aprova Solicita��o de Compra",tela,con);
	        } 
	      }
	    else if (iOpcao==200500000) {
	        if (telaPrincipal.temTela("Pesquisa Solicita��es de Compra")==false) {
	          FConsSol tela = new FConsSol();
			  //tela.setConexao(con);
			  tela.setTelaPrim(telaPrincipal);
	          telaPrincipal.criatela("Pesquisa Solicita��es de Compra",tela,con);
	        } 
	      }
	    else if (iOpcao==200600000) {
	        if (telaPrincipal.temTela("Cota��o de Pre�os")==false) {
	          FCotacaoPrecos tela = new FCotacaoPrecos();
	          telaPrincipal.criatela("Cota��o de Pre�os",tela,con);
	        } 
	      }
	    else if (iOpcao==200700000) {
	        if (telaPrincipal.temTela("Aprova Cota��o de Pre�os")==false) {
		      //FAprovaCotacaoPrecos tela = new FAprovaCotacaoPrecos();
		      //telaPrincipal.criatela("Aprova Cota��o de Pre�os",tela,con);
	        } 
	      }
	    else if (iOpcao==300100000) {
	        if (telaPrincipal.temTela("Venda")==false) {
	          FVenda tela = new FVenda();
	          tela.setTelaPrim(telaPrincipal);
	          telaPrincipal.criatela("Venda",tela,con);
	        } 
	    }    
		else if (iOpcao==300200000) {
		      if (telaPrincipal.temTela("Cancelamento")==false) {
		        FCancVenda tela = new FCancVenda();
		        //tela.setConexao(con);
		        telaPrincipal.criatela("Cancelamento",tela,con);
		      }
		}
	    else if (iOpcao==300300000) {
	        if (telaPrincipal.temTela("Devolu��o de vendas")==false) {
	           FDevolucao tela = new FDevolucao(telaPrincipal.dpArea);
	           tela.setConexao(con);
	           tela.setVisible(true);
	           tela.dispose();
	        }
	    }	    
	    else if (iOpcao==300400000) {
	        if (telaPrincipal.temTela("Lan�amento de Frete")==false) {
	           FFrete tela = new FFrete();
	           telaPrincipal.criatela("Lan�amento de Frete",tela,con);
	           
	        }
	    }	    
		else if (iOpcao==300500000) {
		    if (telaPrincipal.temTela("Aprova Orcamento")==false) {
			    FAprovaOrc tela = new FAprovaOrc();
			    //tela.setConexao(con);
			    telaPrincipal.criatela("Aprova Orcamento",tela,con);
			} 
		}
		else if (iOpcao==300600000) {
			if (telaPrincipal.temTela("Or�amento")==false) {
				FOrcamento tela = new FOrcamento();
				telaPrincipal.criatela("Or�amento",tela,con);
			} 
		}						
		else if (iOpcao==300700000) {
			if (telaPrincipal.temTela("Pesquisa Or�amento")==false) {
					FConsOrc tela = new FConsOrc();
					//tela.setConexao(con);
					tela.setTelaPrim(telaPrincipal);
					telaPrincipal.criatela("Pesquisa Or�amento",tela,con);
			} 
		}	    
	    else if (iOpcao==300800000) {
	        if (telaPrincipal.temTela("Romaneio")==false) {
	          FRomaneio tela = new FRomaneio();
	          telaPrincipal.criatela("Romaneio",tela,con);
	        } 
	    }
	    else if (iOpcao==300900000) {
	        if (telaPrincipal.temTela("LancaExp")==false) {
	          FLancaExp tela = new FLancaExp();
	          telaPrincipal.criatela("LancaExp",tela,con);
	        } 
	    }
	    else if (iOpcao==301010000) {
	        if (telaPrincipal.temTela("Resumo Di�rio")==false) {
	          FRResumoDiario tela = new FRResumoDiario();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("Resumo Di�rio",tela,con);
	        }
	      }
	    else if (iOpcao==301020000) {
	        if (telaPrincipal.temTela("Vendas em Geral")==false) {
	          FRVendasGeral tela = new FRVendasGeral();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("Vendas em Geral",tela,con);
	        }
	    }	    
	    else if (iOpcao==301030000) {
	        if (telaPrincipal.temTela("F�sico de Vendas")==false) {
	          FRVendasFisico tela = new FRVendasFisico();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("F�sico de Vendas",tela,con);
	        }
	    }	   
	    else if (iOpcao==301040000) {
	        if (telaPrincipal.temTela("Vendas Detalhadas")==false) {
	          FRVendasDet tela = new FRVendasDet();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("Vendas Detalhadas",tela,con);
	        }
	    }	
	    else if (iOpcao==301050000) {
	        if (telaPrincipal.temTela("Vendas por Item")==false) {
	          FRVendasItem tela = new FRVendasItem();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("Vendas por Item",tela,con);
	        }
	    }
	    else if (iOpcao==301060000) {
	        if (telaPrincipal.temTela("Media de vendas por item")==false) {
	          FRMediaItem tela = new FRMediaItem();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("Media de vendas por item",tela,con);
	        }
	    }	      
	    else if (iOpcao==301070000) {
	      	if (telaPrincipal.temTela("Ultimas Vendas por Cliente")==false) {
	      		FRUltimaVenda tela = new FRUltimaVenda();
	      		//tela.setConexao(con);
	      		telaPrincipal.criatela("Ultimas Vendas por Cliente",tela,con);
	      	}
	    }
	    else if (iOpcao==301080000) {
	      	if (telaPrincipal.temTela("Vendas por Setor")==false) {
	      		FRVendaSetor tela = new FRVendaSetor();
	      		//tela.setConexao(con);
	      		telaPrincipal.criatela("Vendas por Setor",tela,con);
	      	}
	    }	    
		else if (iOpcao==301110000) {
			  if (telaPrincipal.temTela("Evolu��o de vendas")==false) {
				FREvoluVendas tela = new FREvoluVendas();
				//tela.setConexao(con);
				telaPrincipal.criatela("Evolu��o de vendas",tela,con);
			  }
		}	    
		else if (iOpcao==301210000) {
			  if (telaPrincipal.temTela("Consulta de pre�os")==false) {
				FConsPreco tela = new FConsPreco();
				//tela.setConexao(con);
				telaPrincipal.criatela("Consulta de pre�os",tela,con);
			  }
		}	
	    else if (iOpcao==400100000) {
	        if (telaPrincipal.temTela("Kardex")==false) {
	          FKardex tela = new FKardex();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("Kardex",tela,con);
	        }
	    }		
	    else if (iOpcao==400200000) {
	        if (telaPrincipal.temTela("Invent�rio")==false) {
	          FInventario tela = new FInventario();
	          telaPrincipal.criatela("Invent�rio",tela,con);
	        } 
	    }	    
	    else if (iOpcao==400300000) {
	    	if (telaPrincipal.temTela("Consulta")==false) {
	    		FConsulta tela = new FConsulta();
	    		//tela.setConexao(con);
	    		telaPrincipal.criatela("Consulta",tela,con);
	    	}
	    }	    
	    else if (iOpcao==400500000) {
	        if (telaPrincipal.temTela("Tipo de Movimento")==false) {
	          FTipoMov tela = new FTipoMov();
	          telaPrincipal.criatela("Tipo de Movimento",tela,con);
	        } 
	    }	    
		else if (iOpcao==400600000) {
			if (telaPrincipal.temTela("Reprocessa estoque")==false) {
				FProcessaEQ tela = new FProcessaEQ();
				//tela.setConexao(con);
				telaPrincipal.criatela("Reprocessa estoque",tela,con);
			}     
		}	    
	    else if (iOpcao==400701000) {
	        if (telaPrincipal.temTela("Estoque M�nimo")==false) {
	          FREstoqueMin tela = new FREstoqueMin();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("Estoque M�nimo",tela,con);
	        }
	    }	    
		else if (iOpcao==400702000) {
			  if (telaPrincipal.temTela("Listagem de Produtos")==false) {
			    FRMovProd tela = new FRMovProd();
			    //tela.setConexao(con);
				telaPrincipal.criatela("Listagem de Produtos",tela,con);
			  }     
		}	    
	    else if (iOpcao==400703000) {
	        if (telaPrincipal.temTela("Vencimento Lote")==false) {
	          FRVencLote tela = new FRVencLote();
	          //tela.setConexao(con);
	          telaPrincipal.criatela("Vencimento Lote",tela,con);
	        } 
	      }
	  	else if (iOpcao==400704000) {
	  	  if (telaPrincipal.temTela("Saldos de Lote")==false) {
	  		FRSaldoLote tela = new FRSaldoLote();
	  		//tela.setConexao(con);
	  		telaPrincipal.criatela("Saldos de Lote",tela,con);
	  	  } 
	  	}	    
	    else if (iOpcao==400705000) {
	    	if (telaPrincipal.temTela("Demanda")==false) {
	    		FRDemanda tela = new FRDemanda();		
	    		//tela.setConexao(con);
	    		telaPrincipal.criatela("Demanda",tela,con);
	    	}
	    }	    
	    else if (iOpcao==400706000) {
	    	if (telaPrincipal.temTela("Confer�ncia de Estoque")==false) {
	    		FRConfEstoq tela = new FRConfEstoq();		
	    		//tela.setConexao(con);
	    		telaPrincipal.criatela("Confer�ncia de Estoque",tela,con);
	    	}
	    }
	    else if (iOpcao==400707000) {
	    	if (telaPrincipal.temTela("Invent�rio PEPS")==false) {
	    		FRInvPeps tela = new FRInvPeps();		
	    		//tela.setConexao(con);
	    		telaPrincipal.criatela("Invent�rio PEPS",tela,con);
	    	}
	    }	    
    
    } 
    public static void main(String sParams[]) {
		FreedomGMS freedom = new FreedomGMS();
		freedom.show();
    }
}
