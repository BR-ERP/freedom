/**
 * @version 29/12/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.funcoes <BR>
 * Classe: @(#)Logger.java <BR>
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
 * Esta classe est� respons�vel por guardar as constantes para cria��o do logs.
 */

package org.freedom.funcoes;

import java.util.Date;

public class Logger {
	//Erros de Comuni��o:
	public static final int idErroCom = 1;
	
	public static final LoggerItem LGEC_COM = new LoggerItem(idErroCom,1,"Erro de comunica��o com o Banco de Dados");

	//Erros internos de banco de dados:
	public static final int idErroBD = 2;
	
	public static final LoggerItem LGEB_BD = new LoggerItem(idErroBD,1,"Erro interno de Banco de Dados");

	//Informa��es de PDV:
	public static final int idInfoPDV = 3;
	
	public static final LoggerItem LGIF_ABERT_CAIXA = new LoggerItem(idInfoPDV,1,"Abertura de caixa");
	public static final LoggerItem LGIF_FECHA_CAIXA = new LoggerItem(idInfoPDV,2,"Fechamento de caixa");
	public static final LoggerItem LGIF_ABERT_VENDA = new LoggerItem(idInfoPDV,3,"Abertura de tela de vendas");
	public static final LoggerItem LGIF_FECHA_VENDA = new LoggerItem(idInfoPDV,4,"Fechamento de tela de vendas");
	public static final LoggerItem LGIF_LIB_CUPOM = new LoggerItem(idInfoPDV,5,"Libera��o de cupom");
	public static final LoggerItem LGIF_CANC_ITEM = new LoggerItem(idInfoPDV,6,"Cancelamento de �tem de cupom fical");

	//Erros de PDV:
	public static final int idErroPDV = 4;
	
	public static final LoggerItem LGEP_ABERTFECHA_CAIXA = new LoggerItem(idErroPDV,1,"Erro na abertura/fechamento caixa");
	public static final LoggerItem LGEP_STATUS_IMPRES = new LoggerItem(idErroPDV,2,"Erro na leitura do status da impressora");
	public static final LoggerItem LGEP_ABERT_CUPOM = new LoggerItem(idErroPDV,3,"Erro na abertura do cupom fiscal");
	public static final LoggerItem LGEP_TROCA_VERAO = new LoggerItem(idErroPDV,4,"Erro na troca do hor�rio de ver�o");
	public static final LoggerItem LGEP_ABERT_GAVETA = new LoggerItem(idErroPDV,5,"Erro na abertura da gaveta");
	public static final LoggerItem LGEP_PROG_MOEDA = new LoggerItem(idErroPDV,6,"Erro na programa��o da moeda");
	public static final LoggerItem LGEP_IMPRES_CHEQUE = new LoggerItem(idErroPDV,7,"Erro na impress�o do cheque");
	public static final LoggerItem LGEP_AUT_DOC = new LoggerItem(idErroPDV,8,"Erro na autentica��o do documento");
	public static final LoggerItem LGEP_CANC_ITEM = new LoggerItem(idErroPDV,9,"Erro no cancelamento do item");
	public static final LoggerItem LGEP_CANC_CUPOM = new LoggerItem(idErroPDV,10,"Erro no cancelamento do cupom");
	public static final LoggerItem LGEP_INCL_ALIQ = new LoggerItem(idErroPDV,11,"Erro na inclus�o da al�quota");
	public static final LoggerItem LGEP_FECHA_CUPOM = new LoggerItem(idErroPDV,12,"Erro no fechamento do cupom");
	public static final LoggerItem LGEP_FAZ_SANGRIA = new LoggerItem(idErroPDV,13,"Erro na sangria");
	public static final LoggerItem LGEP_FAZ_SUPRIMENTO = new LoggerItem(idErroPDV,14,"Erro no suprimento");
	public static final LoggerItem LGEP_RET_NCUPOM = new LoggerItem(idErroPDV,15,"Erro no retorno do n�mero do cupom");
	public static final LoggerItem LGEP_IMPRES_ITEM = new LoggerItem(idErroPDV,16,"Erro na impress�o do �tem de venda");
	public static final LoggerItem LGEP_LEITRA_X = new LoggerItem(idErroPDV,17,"Erro na leitura 'X'");
	public static final LoggerItem LGEP_REDUCAO_Z = new LoggerItem(idErroPDV,18,"Erro na redu��o 'Z'");
	public static final LoggerItem LGEP_RET_ALIQ = new LoggerItem(idErroPDV,19,"Erro na fun��o de retorno das al�quotas");
	public static final LoggerItem LGEP_RET_TOTALIZ = new LoggerItem(idErroPDV,20,"Erro na fun��o de retorno dos totalizadores");
	public static final LoggerItem LGEP_RET_CANC = new LoggerItem(idErroPDV,21,"Erro na fun��o de retorno dos cancelamentos");
	public static final LoggerItem LGEP_RET_RED = new LoggerItem(idErroPDV,22,"Erro na fun��o de retorno das redu��es");
	public static final LoggerItem LGEP_DATA_FISCALREDZ = new LoggerItem(idErroPDV,23,"Erro na fun��o de retorno da data fiscal/redu��o'Z'");
	public static final LoggerItem LGEP_RET_VCANC = new LoggerItem(idErroPDV,24,"Erro na fun��o de retorno do valor de cancelamentos");
	public static final LoggerItem LGEP_RET_VDESC = new LoggerItem(idErroPDV,25,"Erro na fun��o de retorno do valor de descontos");
	public static final LoggerItem LGEP_RET_FLAG = new LoggerItem(idErroPDV,26,"Erro na fun��o de retorno do flag fiscal");
	public static final LoggerItem LGEP_RET_GTOTAL = new LoggerItem(idErroPDV,27,"Erro na fun��o de retorno do grande total");
	public static final LoggerItem LGEP_ABRE_N_FISCAL_VIN = new LoggerItem(idErroPDV,28,"Erro na fun��o abrir comprovante n�o fiscal vinculado");
	public static final LoggerItem LGEP_USA_N_FISCAL_VIN = new LoggerItem(idErroPDV,29,"Erro na fun��o usar comprovante n�o fiscal vinculado");
	public static final LoggerItem LGEP_FECHA_N_FISCAL_VIN = new LoggerItem(idErroPDV,30,"Erro na fun��o fechar comprovante n�o fiscal vinculado");
	public static final LoggerItem LGEP_INICIA_TEF = new LoggerItem(idErroPDV,31,"Erro na fun��o de inicializa��o da TEF");
	public static final LoggerItem LGEP_FINALIZA_TEF = new LoggerItem(idErroPDV,32,"Erro na fun��o de finaliza��o de TEF");
    
// Logs de PDV:
	public static boolean gravaLogTxt(String sEmp, String sUserID, LoggerItem lg, String sTextoAdic) {
		Date dtLog = new Date();
	    System.out.println("LOG: "+sEmp+"|"+sUserID+"|"+Funcoes.strZero(""+lg.idpai,2)+"|"+Funcoes.strZero(""+lg.id,2)+"|"+lg.desc+"|"+sTextoAdic+"|"+dtLog);
	    return true;
    }
}
class LoggerItem {
    public int id = 0;
    public int idpai = 0;
    public String desc = "";
    public LoggerItem(int idpai,int id, String desc) {
        this.id = id;
        this.idpai = idpai;
        this.desc = desc;
    }
}

