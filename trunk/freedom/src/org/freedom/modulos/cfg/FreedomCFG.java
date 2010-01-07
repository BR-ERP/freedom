/**
 * @version 07/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe:
 * @(#)FreedomCFG.java <BR>
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
 * Tela principal do m�dulo configurador.
 * 
 */

package org.freedom.modulos.cfg;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.crm.FConfEmail;
import org.freedom.modulos.pdv.FLeFiscal;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrincipalPD;
import org.freedom.telas.LoginPD;

public class FreedomCFG extends AplicativoPD {

	public FreedomCFG() {

		super( "iconcfg.png", "splashCFG.png", 1, "Freedom", 2, "Configurador do Sistema", null, new FPrincipalPD( null, "bgFreedom2.jpg" ),LoginPD.class);
		
		addOpcao( -1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false, null );
			addOpcao( 100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1, false, null );
				addOpcao( 100100000, TP_OPCAO_MENU, "Acesso ao sistema", "", 'A', 100101000, 2, false, null );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Grupos", "Grupos", 'G', 100101010, 3, true, FGrupo.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Usuarios", "Usuarios", 'U', 100101020, 3, true, FUsuario.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Acesso", "Acesso Menu", 'A', 100101030, 3, true, FAcesso.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Menu", "Menu X Objeto", 'M', 100101040, 3, true, FMenuObj.class );
				addOpcao( 100100000, TP_OPCAO_MENU, "Tabela Geogr�ficas", "", 'C', 100102000, 2, false, null );					
					addOpcao( 100102000, TP_OPCAO_ITEM, "Paises", "Paises", 'P', 100102020, 3, true, FPais.class );
					addOpcao( 100102000, TP_OPCAO_ITEM, "Cidades", "Cidades", 'd', 100102030, 3, true, FMunicipio.class );
					addOpcao( 100102000, TP_OPCAO_ITEM, "Estados", "Estados", 'E', 100102040, 3, true, FUF.class );
					addOpcao( 100102000, TP_OPCAO_ITEM, "Bairros", "Bairros", 'B', 100102050, 3, true, FBairro.class );
				addOpcao( 100100000, TP_OPCAO_MENU, "Objetos", "", 'O', 100103000, 2, false, null );
					addOpcao( 100103000, TP_OPCAO_ITEM, "Tabela", "Tabelas auxiliares", 'T', 100103010, 3, true, FTabela.class );
					addOpcao( 100103000, TP_OPCAO_ITEM, "Objetos aux.", "Vinculo entre tabelas f�sicas e auxiliares", 'O', 100103020, 3, true, FObjetoTb.class );
				addOpcao( 100100000, TP_OPCAO_MENU, "Fluxos", "", 'F', 100104000, 2, false, null );
					addOpcao( 100104000, TP_OPCAO_ITEM, "Processos", "Processos", 'P', 100104100, 3, true, FProcesso.class );
					addOpcao( 100104000, TP_OPCAO_ITEM, "Fluxos", "Cadastro de fluxos", 'F', 100104110, 3, true, FFluxo.class );
				
				addOpcao( 100100000, TP_OPCAO_MENU, "Outras tabelas gen�ricas", "", 's', 100105000, 2, false, null );			
					addOpcao( 100105000, TP_OPCAO_ITEM, "Estados civis", "Estados civis", 'i', 100105100, 3, true, FEstadoCivil.class );
					addOpcao( 100105000, TP_OPCAO_ITEM, "Cadastro de Feriado", "Cadastro de Feriado", 'i', 100105200, 3, true, FFeriados.class );
					addOpcao( 100105000, TP_OPCAO_ITEM, "Configura��o de email", "Configura��o de email", 'e', 100105300, 3, true, FConfEmail.class );
					addOpcao( 100105000, TP_OPCAO_ITEM, "Grau de instru��o", "Grau de Instru��o", 'G', 100105410, 3, true, FGrauInst.class );
					
		addOpcao( 100000000, TP_OPCAO_MENU, "Ferramentas", "", 'e', 100200000, 1, false, null );
			addOpcao( 100200000, TP_OPCAO_ITEM, "Ajuste de Sequencia", "Ajusta sequencia", 'A', 100201000, 2, true, FAjustaSeq.class );
			addOpcao( 100200000, TP_OPCAO_ITEM, "Leitura Fiscal", "Leitura Fiscal", 'F', 100202000, 2, true, FLeFiscal.class );

		addOpcao( 100000000, TP_OPCAO_MENU, "Prefer�ncias", "", 'P', 100300000, 1, false, null );
			addOpcao( 100300000, TP_OPCAO_ITEM, "Visual", "Configura��o de Visual", 'A', 100301000, 2, true, FVisual.class );
		
		addBotao( "barraGrupo.gif", "Cadastro de Grupos", "Grupos", 100101010, FGrupo.class );
		addBotao( "barraUsuario.gif", "Cadastro de Usuarios", "Usuarios", 100101020, FUsuario.class );
		addBotao( "barraAcesso.gif", "Controle de Acessos", "Acesso Menu", 100101030, FAcesso.class );


		ajustaMenu();

		// Inicia a vari�vel de conex�o com o banco interno do interbase
		conIB = conexaoIB( getParameter( "driver" ), getParameter( "bancocfg" ) ); 

		sNomeModulo = "Configurador";
		sNomeSis = "Freedom";
		sEmpSis = "Setpoint Inform�tica Ltda.";
		sMailSuporte = "suporte@stpinf.com";
		vEquipeSis.add( "Robson Sanchez - Supervis�o / Analise" );
		vEquipeSis.add( "Anderson Sanchez - Supervis�o / Programa��o" );
		vEquipeSis.add( "Alex Rodrigues - Programa��o" );
		vEquipeSis.add( "Alexandre Marcondes - Programa��o" );
		vEquipeSis.add( "Fernando Oliveira - Programa��o" );
		vEquipeSis.add( "Moyzes Braz - Arte gr�fica" );

	}

	public static void main( String sParams[] ) {

		try {
			Aplicativo.setLookAndFeel( "freedom.ini" );
			FreedomCFG freedom = new FreedomCFG();
			freedom.show();

		} catch ( Throwable e ) {
			Funcoes.criaTelaErro( "Erro de execu��o" );
			e.printStackTrace();
		}
	}
}
