/**
 * @version 07/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FreedomATD.java <BR>
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
 * Tela principal do m�dulo de atendimento.
 * 
 */

package org.freedom.modulos.atd;
import java.awt.event.ActionListener;

import org.freedom.modulos.std.FCliente;
import org.freedom.telas.Aplicativo;


public class FreedomATD extends Aplicativo implements ActionListener {
	public FreedomATD() {
		super("iconAtendimento32.gif","splashATD.jpg","FreedomATD - M�dulo de atendimento",1,4);
		
//Arquivo:		
		addOpcao(-1,TP_OPCAO_MENU,"Arquivo","",'A',100000000,0, false, null);
		
		  addOpcao(100000000,TP_OPCAO_MENU,"Tabelas","",'T',100100000,1, false, null);
		  
		    addOpcao(100100000,TP_OPCAO_MENU,"Conveniado","",'C',100101000,2, false, null);
		      addOpcao(100101000,TP_OPCAO_ITEM,"Tipo de Conveniado","Tipo de conveniados",'T',100101010,3, true, FTipoConv.class);
		      addOpcao(100101000,TP_OPCAO_ITEM,"Atribui��es","Abribui��es",'A',100101020,3, true, FAtribuicao.class);
		      addOpcao(100101000,TP_OPCAO_ITEM,"Encaminhador","Encaminhador",'E',100101030,3, true, FEncaminhador.class);
		      addOpcao(100101000,TP_OPCAO_ITEM,"Conveniados","",'C',100101040,3, true, null);
		      
		    addOpcao(101000000,TP_OPCAO_MENU,"Atendente","",'A',100102000,2, false, null);
		      addOpcao(100102000,TP_OPCAO_ITEM,"Tipo de Atendente","",'T',100102010,3, true, null);
		      addOpcao(100102000,TP_OPCAO_ITEM,"Atendentes","",'A',100102020,3, true, null);
		      
		    addOpcao(100100000,TP_OPCAO_MENU,"Atendimento","",'t',100103000,2, false, null);
		      addOpcao(100103000,TP_OPCAO_ITEM,"Setor de Atendimento","",'S',100103010,3, true, null);
		      addSeparador(100103000);
		      addOpcao(100103000,TP_OPCAO_ITEM,"Atendente","",'e',100103020,1, true, null);
		      addOpcao(100103000,TP_OPCAO_ITEM,"Tipo de Atendente","",'i',100103030,1, true, null);
		      
		    addSeparador(100100000);
		    addOpcao(100100000,TP_OPCAO_ITEM,"Clientes","",'l',100104000,2, true, null);
		    addSeparador(100100000);
		    
		  addOpcao(100000000,TP_OPCAO_MENU,"Prefer�ncias","",'P',100200000,1, false, null);
		  
		    addOpcao(100200000,TP_OPCAO_ITEM,"Prefer�ncias Gerais...","",'P',100201000,2, true, null);

//Atendimento:
		addOpcao(-1,TP_OPCAO_MENU,"Atendimento","",'t',110000000,0, false, null);
		  addOpcao(110000000,TP_OPCAO_ITEM,"Atendimento","",'A',110100000,1, true, null);
		  addSeparador(110000000);
		  addOpcao(110000000,TP_OPCAO_ITEM,"Tipos de Atendimento","",'T',110200000,1, true, null);

//Or�amento:
	    addOpcao(-1,TP_OPCAO_MENU,"Or�amento","",'O',1200000000,0, false, null);
		  addOpcao(1200000000,TP_OPCAO_ITEM,"Or�amento","",'O',120100000,1, true, null);
		  addSeparador(1200000000);
		  addOpcao(1200000000,TP_OPCAO_ITEM,"Pesquisa Or�amentos","",'P',120200000,1, true, null);
		  addOpcao(1200000000,TP_OPCAO_ITEM,"Aprova Or�amentos","",'A',120300000,1, true, null);
		  addOpcao(1200000000,TP_OPCAO_ITEM,"Pesquisa Autoriza��es","Pesquisa Autoriza��o",'e',120400000,1, true, FConsAutoriz.class);
		 
		addBotao("btAtendimento.gif","Atendimento",110100000);
		addBotao("btOrcamento.gif","Or�amento",120100000);
		addBotao("btConsOrcamento.gif","Pesquisa Or�amento",120200000);
		addBotao("barraConveniados.gif","Conveniados",100101040);
		addBotao("btAprovaOrc.gif","Aprova��es de Or�amantos",120300000);
		
		ajustaMenu();
		
	}
    public void execOpcao(int iOpcao) {
    	if (iOpcao == 100101010) {
			if (telaPrincipal.temTela("Tipo de conveniados")==false) {
			  FTipoConv tela = new FTipoConv();
			  telaPrincipal.criatela("Tipo de conveniados",tela,con);
			} 
    	}
		else if (iOpcao == 100101020) {
		  if (telaPrincipal.temTela("Abribui��es")==false) {
			FAtribuicao tela = new FAtribuicao();
			telaPrincipal.criatela("Abribui��es",tela,con);
		  }
		}
		else if (iOpcao == 100101030) {
			if (telaPrincipal.temTela("Encaminhador")==false) {
				FEncaminhador tela = new FEncaminhador();
				telaPrincipal.criatela("Encaminhador",tela,con);
			}
		}
		else if (iOpcao == 100101040) {
		  if (telaPrincipal.temTela("Conveniados")==false) {
			FConveniado tela = new FConveniado();
			telaPrincipal.criatela("Conveniados",tela,con);
		  } 
		}
		else if (iOpcao == 100102010) {
		  if (telaPrincipal.temTela("Tipo de atendentes")==false) {
			FTipoAtend tela = new FTipoAtend();
			telaPrincipal.criatela("Tipo de atendentes",tela,con);
		  } 
		}
		else if (iOpcao == 100102020) {
		  if (telaPrincipal.temTela("Atendentes")==false) {
			FAtendente tela = new FAtendente();
			telaPrincipal.criatela("Atendentes",tela,con);
		  } 
		}
		else if (iOpcao == 100103010) {
		  if (telaPrincipal.temTela("Setor de Atendimento")==false) {
			FSetorAtend tela = new FSetorAtend();
			telaPrincipal.criatela("Setor de Atendimento",tela,con);
		  } 
		}
		else if (iOpcao == 100103020) {
		  if (telaPrincipal.temTela("Atendente")==false) {
			FAtendente tela = new FAtendente();
			telaPrincipal.criatela("Atendente",tela,con);
		  } 
		}
		else if (iOpcao == 100103030) {
		  if (telaPrincipal.temTela("Tipo de Atendente")==false) {
			FTipoAtend tela = new FTipoAtend();
			telaPrincipal.criatela("Tipo de Atendente",tela,con);
		  } 
		}
		else if (iOpcao == 100104000){
		  if (telaPrincipal.temTela("Clientes")==false) {
			FCliente tela = new FCliente();
			telaPrincipal.criatela("Clientes",tela,con);
		  }
		}
		else if (iOpcao == 100201000) {
		  if (telaPrincipal.temTela("Pref. Gerais")==false) {
			FPrefereAtend tela = new FPrefereAtend();
			telaPrincipal.criatela("Pref. Garais",tela,con);
		  }
		}
		else if (iOpcao == 110100000){
		  if (telaPrincipal.temTela("Atendimento")==false) {
			FAtendimento tela = new FAtendimento();
			tela.setTelaPrim(telaPrincipal);
			telaPrincipal.criatela("Atendimento",tela,con);
		  } 
		}
		else if (iOpcao == 110200000) {
		  if (telaPrincipal.temTela("Tipo de atendimento")==false) {
			FTipoAtendo tela = new FTipoAtendo();
			telaPrincipal.criatela("Tipo de atendimento",tela,con);
		  } 
		}
		else if (iOpcao == 120100000){
		  if (telaPrincipal.temTela("Orcamento")==false) {
			FOrcamento tela = new FOrcamento();
			telaPrincipal.criatela("Orcamento",tela,con);
		  } 
		}
		else if (iOpcao == 120200000){
		  if (telaPrincipal.temTela("Pesquisa Orcamentos")==false) {
			FConsOrc tela = new FConsOrc();
			tela.setTelaPrim(telaPrincipal);
			telaPrincipal.criatela("Pesquisa Orcamentos",tela,con);
		  } 
		}
		else if (iOpcao == 120300000){
		  if (telaPrincipal.temTela("Aprova Orcamento")==false) {
			FAprovaOrc tela = new FAprovaOrc();
			//tela.setConexao(con);
			telaPrincipal.criatela("Aprova Orcamento",tela, con);
		  } 
		}
		else if (iOpcao == 120400000){
		   if (telaPrincipal.temTela("Pesquisa Autoriza��o")==false) {
				FConsAutoriz tela = new FConsAutoriz();
				tela.setConexao(con);
				tela.setTelaPrim(telaPrincipal);
				telaPrincipal.criatela("Pesquisa Autoriza��o",tela, con);
		   } 
		}
    }
    public static void main(String sParams[]) {
		FreedomATD freedomatd = new FreedomATD();
		freedomatd.show();
    }
}