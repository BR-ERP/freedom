/**
 * @version 01/09/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FreedomPCP.java <BR>
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
 * Tela principal do m�dulo de produ��o.
 * 
 */

package org.freedom.modulos.pcp;
import java.awt.event.ActionListener;

import org.freedom.modulos.std.FAlmox;
import org.freedom.modulos.std.FCLFiscal;
import org.freedom.modulos.std.FGrupo;
import org.freedom.modulos.std.FMarca;
import org.freedom.modulos.std.FProduto;
import org.freedom.modulos.std.FUnidade;
import org.freedom.telas.Aplicativo;


public class FreedomPCP extends Aplicativo implements ActionListener {
  public FreedomPCP() {
		
	super("iconAtendimento32.gif","splashPCP.jpg","FreedomPCP - Modulo de planejamento e controle de produ��o FREEDOM",1,5);
	addOpcao(-1,TP_OPCAO_MENU,"Arquivo","",'A',100000000,0, false, null);
	addOpcao(100000000,TP_OPCAO_MENU,"Tabelas","",'T',100100000,1, false, null);
	addOpcao(100000000,TP_OPCAO_MENU,"Prefer�ncias","",'F',110100000,1, false, null);
	 
	addOpcao(100100000,TP_OPCAO_MENU,"Produtos","",'P',100101000,2, false, null);
	addOpcao(100101000,TP_OPCAO_ITEM,"Classifica��o fiscal","",'l',100120020,3, true, null);
    addOpcao(100101000,TP_OPCAO_ITEM,"Almoxarifado","",'x',100120030,3, true, null);
    addOpcao(100101000,TP_OPCAO_ITEM,"Grupo","",'r',100120040,3, true, null);
    addOpcao(100101000,TP_OPCAO_ITEM,"Marca","",'c',100120050,3, true, null);
    addOpcao(100101000,TP_OPCAO_ITEM,"Unidade","",'U',100120060,3, true, null);
    addOpcao(100101000,TP_OPCAO_ITEM,"Produto","",'P',100120070,3, true, null);
	addBotao("btProduto.gif","Produtos",100101000);
	addSeparador(100100000);
	addOpcao(100100000,TP_OPCAO_ITEM,"Estrutura","",'E',100102000,2, true, null);
	addOpcao(100100000,TP_OPCAO_ITEM,"Tipos de recursos","",'T',100103000,2, true, null);
	addOpcao(100100000,TP_OPCAO_ITEM,"Recursos de produ��o","",'R',100104000,2, true, null);
	addOpcao(100100000,TP_OPCAO_ITEM,"Fases de produ��o","",'F',100105000,2, true, null);

	addBotao("btEstProduto.gif","Estrutura de produto",100102000);
		
	addOpcao(110100000,TP_OPCAO_ITEM,"Prefer�ncias gerais","",'G',110101000,2, true, null);

	addOpcao(-1,TP_OPCAO_MENU,"Produ��o","",'P',200000000,0, false, null);
	addOpcao(200000000,TP_OPCAO_ITEM,"Ordens de produ��o","",'O',200100000,1, true, null);
	
	ajustaMenu();
  }
  public void execOpcao(int iOpcao) {
	if (iOpcao==110101000){
	  if (telaPrincipal.temTela("Prefer�ncias gerais")==false) {
        FPrefereProd tela = new FPrefereProd();
	    telaPrincipal.criatela("Prefer�ncias gerais",tela,con);
	  }
	}
	else if (iOpcao==100102000){
	  if (telaPrincipal.temTela("Estrutura de produto")==false) {
	    FEstrutura tela = new FEstrutura();
	    tela.setTelaPrim(telaPrincipal); 
	    telaPrincipal.criatela("Estrutura de produto",tela,con);
	  }
	}
	else if (iOpcao==100120070){
	  if (telaPrincipal.temTela("Produto")==false) {
	    FProduto tela = new FProduto();
	    telaPrincipal.criatela("Produto",tela,con);
	  }
	}
	else if (iOpcao==100120060){
		  if (telaPrincipal.temTela("Unidade")==false) {
		    FUnidade tela = new FUnidade();
		    telaPrincipal.criatela("Unidade",tela,con);
	  }
    }
	else if (iOpcao==100120050){
		  if (telaPrincipal.temTela("Marca")==false) {
		    FMarca tela = new FMarca();
		    telaPrincipal.criatela("Marca",tela,con);
	  }
    }
	else if (iOpcao==100120040){
		  if (telaPrincipal.temTela("Grupo")==false) {
		    FGrupo tela = new FGrupo();
		    //tela.setConexao(con);
		    telaPrincipal.criatela("Grupo",tela,con);
	  }
    }
	else if (iOpcao==100120030){
		  if (telaPrincipal.temTela("Almoxarifado")==false) {
		    FAlmox tela = new FAlmox();
		    telaPrincipal.criatela("Almoxarifado",tela,con);
	  }
  }
	else if (iOpcao==100120020){
		  if (telaPrincipal.temTela("Classifica��o Fiscal")==false) {
		    FCLFiscal tela = new FCLFiscal();
            tela.setTelaPrim(telaPrincipal);
		    telaPrincipal.criatela("Classifica��o Fiscal",tela,con);
	  }
  }
	else if (iOpcao==100103000){
		if (telaPrincipal.temTela("Tipos de recursos")==false) {
			FTipoRec tela = new FTipoRec();
			telaPrincipal.criatela("Tipos de recursos",tela,con);
		}
	}	
	else if (iOpcao==100104000){
		if (telaPrincipal.temTela("Recursos de produ��o")==false) {
			FRecursos tela = new FRecursos();
			telaPrincipal.criatela("Recursos de produ��o",tela,con);
		}
	}
	else if (iOpcao==100105000){
		if (telaPrincipal.temTela("Fases de produ��o")==false) {
			FFase tela = new FFase();
			telaPrincipal.criatela("Fases de produ��o",tela,con);
		}
	}
	else if (iOpcao==200100000){
		if (telaPrincipal.temTela("Ordens de produ��o")==false) {
			FOP tela = new FOP();
            tela.setTelaPrim(telaPrincipal);
			telaPrincipal.criatela("Ordens de produ��o",tela,con);
		}
	}
	
	
  }
  public static void main(String sParams[]) {
		FreedomPCP freedom = new FreedomPCP();
		freedom.show();
  }
}