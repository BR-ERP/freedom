/**
 * @version 30/01/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe: @(#)FreedomGRH.java <BR>
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
 * Tela principal para o m�dulo de gest�o de recursos humanos.
 * 
 */

package org.freedom.modulos.grh;

import org.freedom.telas.Aplicativo;

public class FreedomGRH extends Aplicativo {

	public FreedomGRH() {
		super("iconAtendimento32.gif","splashGRH.jpg","FreedomGMS - M�dulo de gerenciamento de mateirais e servi�os",1,8);      		
		
		addOpcao(-1,TP_OPCAO_MENU,"Arquivo","",'A',100000000,0, false, null);
			addOpcao(100000000,TP_OPCAO_MENU,"Cadastros","",'T',100100000,1, false, null);		    	
	    		addOpcao(100100000,TP_OPCAO_ITEM,"Empregados", "Empregados",'E',100101000,2, true, FEmpregado.class );		
	    		addOpcao(100100000,TP_OPCAO_ITEM,"Turnos", "Turnos",'R',100102000,2, true, FTurnos.class);
	    		addOpcao(100100000,TP_OPCAO_ITEM,"Fun�ao", "Fun��o",'F',100103000,2, true, FFuncao.class);
	    		addOpcao(100100000,TP_OPCAO_ITEM,"Departamento", "Departamento",'D',100104000,2, true, FDepto.class);
		        	    				
	    		
	    		addBotao("barraConveniados.gif","Empregados",100101000);
	    			    		
	    		
	    ajustaMenu();
    }

	public static void main(String sParams[]) {
		FreedomGRH freedom = new FreedomGRH();
		freedom.show();
    }
}