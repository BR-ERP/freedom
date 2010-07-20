/**
 * @version 29/12/2003 <BR>
 * @author Setpoint Inform�tica Ltda.
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.fnc <BR>
 *         Classe:
 * @(#)FreedomFNC.java <BR>
 * 
 *                     Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                     na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                     Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                     Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                     Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Tela principal do m�dulo financeiro.
 * 
 */

package org.freedom.modulos.lvf;

import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.AplicativoPD;
import org.freedom.library.swing.frame.FPrincipalPD;
import org.freedom.library.swing.frame.LoginPD;
import org.freedom.modulos.lvf.view.frame.crud.plain.FTabICMS;
import org.freedom.modulos.lvf.view.frame.report.FRIcms;
import org.freedom.modulos.lvf.view.frame.report.FRIcmsNcm;
import org.freedom.modulos.lvf.view.frame.report.FRIpi;
import org.freedom.modulos.lvf.view.frame.report.FRMovPisCofins;
import org.freedom.modulos.lvf.view.frame.report.FRPisCofins;
import org.freedom.modulos.lvf.view.frame.report.FRRegitroEntrada;
import org.freedom.modulos.lvf.view.frame.report.FRRegitroSaida;
import org.freedom.modulos.lvf.view.frame.utility.FSintegra;
import org.freedom.modulos.std.view.frame.crud.detail.FEmpresa;
import org.freedom.modulos.std.view.frame.crud.plain.FClasCli;
import org.freedom.modulos.std.view.frame.crud.plain.FEstacao;
import org.freedom.modulos.std.view.frame.crud.plain.FImpressora;
import org.freedom.modulos.std.view.frame.crud.plain.FMensagem;
import org.freedom.modulos.std.view.frame.crud.plain.FPapel;
import org.freedom.modulos.std.view.frame.crud.plain.FSetor;
import org.freedom.modulos.std.view.frame.crud.plain.FTipoCli;
import org.freedom.modulos.std.view.frame.crud.plain.FTipoFor;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCredCli;
import org.freedom.modulos.std.view.frame.crud.tabbed.FFornecedor;
import org.freedom.modulos.std.view.frame.crud.tabbed.FMoeda;
import org.freedom.modulos.std.view.frame.crud.tabbed.FVendedor;
import org.freedom.modulos.std.view.frame.report.FRImpServ;
import org.freedom.modulos.std.view.frame.report.FRegraFiscal;
import org.freedom.modulos.std.view.frame.utility.FGeraFiscal;

public class FreedomLVF extends AplicativoPD {

	public FreedomLVF() {

		super( "iconstd.png", "splashFNC.png", 1, "Freedom", 10, "Livros Fiscais", null, new FPrincipalPD( null, "bgFreedom2.jpg" ), LoginPD.class );

		addOpcao( -1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false, null );
		addOpcao( 100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1, false, null );
		addOpcao( 100100000, TP_OPCAO_MENU, "Cliente", "", 'C', 100101000, 2, false, null );
		addOpcao( 100101000, TP_OPCAO_ITEM, "Setor", "Setor", 'S', 100101010, 3, true, FSetor.class );
		addOpcao( 100101000, TP_OPCAO_ITEM, "Comissionado", "Comissionado", 's', 100101020, 3, true, FVendedor.class );
		addOpcao( 100101000, TP_OPCAO_ITEM, "Tipo de cliente", "TipoCli", 'T', 100101030, 3, true, FTipoCli.class );
		addOpcao( 100101000, TP_OPCAO_ITEM, "Classifica��o de cliente", "Classifi��o de Clientes", 'f', 100101040, 3, true, FClasCli.class );
		addOpcao( 100101000, TP_OPCAO_ITEM, "Cliente", "Clientes", 'C', 100101050, 3, true, FCliente.class );
		addOpcao( 100101000, TP_OPCAO_ITEM, "Cr�dito por cliente", "Cr�dito por cliente", 'r', 100101060, 3, true, FCredCli.class );
		addSeparador( 100100000 );
		addOpcao( 100100000, TP_OPCAO_ITEM, "Moeda", "Moeda", 'M', 100102000, 2, true, FMoeda.class );
		addSeparador( 100100000 );
		addOpcao( 100100000, TP_OPCAO_ITEM, "Tipo de fornecedor", "TipoFor", 'i', 100107000, 2, true, FTipoFor.class );
		addOpcao( 100100000, TP_OPCAO_ITEM, "Fornecedor", "Fornecedor", 'F', 100108000, 2, true, FFornecedor.class );
		addSeparador( 100100000 );
		addOpcao( 100000000, TP_OPCAO_MENU, "Ferramentas", "", 'F', 100200000, 1, false, null );
		addOpcao( 100000000, TP_OPCAO_MENU, "Prefer�ncias", "", 'P', 100300000, 1, false, null );
		addOpcao( 100000000, TP_OPCAO_MENU, "Configura��es", "", 'C', 100400000, 1, false, null );
		addOpcao( 100400000, TP_OPCAO_ITEM, "Esta��o de trabalho", "Esta��es de trabalho", 't', 100401000, 2, true, FEstacao.class );
		addOpcao( 100400000, TP_OPCAO_ITEM, "Impressora", "Impressoras", 'I', 100402000, 2, true, FImpressora.class );
		addOpcao( 100400000, TP_OPCAO_ITEM, "Papel", "Papeis", 'P', 100403000, 2, true, FPapel.class );
		addSeparador( 100400000 );
		addOpcao( 100400000, TP_OPCAO_ITEM, "Empresa", "Empresa", 'E', 100404000, 2, true, FEmpresa.class );

		addOpcao( -1, TP_OPCAO_MENU, "Fiscal", "", 'F', 200000000, 0, false, null );
		addOpcao( 200000000, TP_OPCAO_ITEM, "Gerar", "Gera Fiscal", 'G', 200100000, 1, true, FGeraFiscal.class );
		addOpcao( 200000000, TP_OPCAO_ITEM, "Regras fiscais", "Regras Fiscais", 'R', 200200000, 1, true, FRegraFiscal.class );
		addOpcao( 200000000, TP_OPCAO_ITEM, "Sintegra", "Gera Arquivo Sintegra", 'S', 200300000, 1, true, FSintegra.class );
		addOpcao( 200000000, TP_OPCAO_ITEM, "Tabela de Al�quotas", "Tabela de al�quotas", 'T', 200400000, 1, true, FTabICMS.class );
		addOpcao( 200000000, TP_OPCAO_ITEM, "Mensagens", "Mensagens", 'M', 200500000, 1, true, FMensagem.class );
		addOpcao( 200000000, TP_OPCAO_MENU, "Listagens", "", 'L', 200600000, 1, false, null );
		addOpcao( 200600000, TP_OPCAO_ITEM, "ICMS", "ICMS", 'I', 200601000, 2, true, FRIcms.class );
		addOpcao( 200600000, TP_OPCAO_ITEM, "ICMS por NCM/CFOP", "ICMS por NCM/CFOP", 'N', 200604000, 2, true, FRIcmsNcm.class );
		addOpcao( 200600000, TP_OPCAO_ITEM, "IPI", "IPI", 'P', 200606000, 2, true, FRIpi.class );
		addOpcao( 200600000, TP_OPCAO_ITEM, "Impostos sobre servi�os", "Impostos sobre servi�os", 'S', 200602000, 2, true, FRImpServ.class );
		addOpcao( 800600000, TP_OPCAO_ITEM, "PIS/COFINS", "Mov. PIS/COFINS", 'P', 200605000, 2, true, FRPisCofins.class );
		addOpcao( 200600000, TP_OPCAO_ITEM, "Movimenta��o com PIS e COFINS", "Movimenta��o com PIS e COFINS", 'P', 200603000, 2, true, FRMovPisCofins.class );

		addOpcao( -1, TP_OPCAO_MENU, "Entrada", "", 'E', 300000000, 0, false, null );
		addOpcao( 300000000, TP_OPCAO_ITEM, "Registro de Entrada", "Registro de Entrada", 'E', 300100000, 1, true, FRRegitroEntrada.class );

		addOpcao( -1, TP_OPCAO_MENU, "Saida", "", 'S', 400000000, 0, false, null );
		addOpcao( 400000000, TP_OPCAO_ITEM, "Registro de Saida", "Registro de Saida", 'S', 400100000, 1, true, FRRegitroSaida.class );

		addBotao( "barraUsuario.gif", "Cliente", "Clientes", 100101050, FCliente.class );
		addBotao( "btForneced.gif", "Fornecedor", "Fornecedor", 100108000, FFornecedor.class );

		ajustaMenu();

		nomemodulo = "Livros fiscais";

	}

	public static void main( String sParams[] ) {

		try {
			Aplicativo.setLookAndFeel( "freedom.ini" );
			FreedomLVF freedom = new FreedomLVF();
			freedom.show();
		} catch ( Throwable e ) {
			Funcoes.criaTelaErro( "Erro de execu��o\n\n\n" + e.getMessage() );
			e.printStackTrace();
		}
	}
}
