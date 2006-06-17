/*
 * Classe de driver para impressoras Bematech
 * Autor: Robson Sanchez/Setpoint Inform�tica Ltda.
 * Data: 05/04/2006
 */
package org.freedom.ecf.driver;




public class ECFBematech extends ECFDriver {
	
	public ECFBematech(int com) {
		super(com);
	}
	
	public byte[] preparaCmd(byte[] CMD) {
		int tamCMD = CMD.length;
		int tam = tamCMD + 2;
		byte NBL = (byte) (tam % 256);
		byte NBH = (byte) (tam / 256);
		byte CSL = 0;
		byte CSH = 0;
		int soma = 0;
		byte[] retorno = new byte[5 + tamCMD];
		retorno[0] = STX;
		retorno[1] = NBL;
		retorno[2] = NBH;
		for (int i=0; i<tamCMD; i++) {
			soma += CMD[i];
			retorno[i+3] = CMD[i];
		}
		CSL = (byte) (soma % 256);
		CSH = (byte) (soma / 256);
        retorno[retorno.length-2] = CSL;
        retorno[retorno.length-1] = CSH;
		return retorno;
	}

	public int executaCmd(byte[] CMD) {
		byte[] retorno = null;
		CMD = preparaCmd(CMD);
		retorno = enviaCmd(CMD);
		aguardaImpressao();
		return checkRetorno(retorno);
	}
	
	public void aguardaImpressao() {
		byte[] CMD = {ESC,19}; // status 
		byte[] retorno = null;
		CMD = preparaCmd(CMD);
		while ((retorno==null) || (retorno.length<2)) {
		   retorno = enviaCmd(CMD);
		   try {
			   Thread.sleep(3000);
		   }
		   catch (InterruptedException e) {
			   
		   }
		}
	}
	
	public int checkRetorno(byte[] bytes) {
		int retorno = 0;
		bytesLidos = null;
		//byte[] flags = new byte[3];
		byte ack = 0;
		byte st1 = 0;
		byte st2 = 0;
		if (bytes!=null) {
		   if (bytes.length>3) 
			   bytesLidos = new byte[bytes.length-3];
		   for (int i=0; i<bytes.length; i++) {
			   if (i==0)
			       ack = bytes[i];
			   else if (i==1)
			       st1 = bytes[i];
			   else if (i==2)
				   st2 = bytes[i];
			   else
				   bytesLidos[i-3] = bytes[i];
		   }
		   if (ack==ACK)
			   retorno = 1;
		   else {
			   retorno = -27; // Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)
			   if (st1>127) st1 -= 128;
			   if (st1>63) st1 -= 64;
			   if (st1>31) st1 -= 32;
			   if (st1>15) st1 -= 16;
			   if (st1>7) st1 -= 8;
			   if (st1>3) st1 -= 4;
			   if (st1>1) st1 -= 2;
			   if (st1>0) {
				   st1 -= 1;
				   retorno = -2; //"Par�metro inv�lido na fun��o. ou N�mero de par�metros inv�lido na fun�ao"
			   }
			   if (st2>127) {
				   retorno = -2; //"Par�metro inv�lido na fun��o."
				   st2 -= 128;
			   }
			   if (st2>63) st2 -= 64;
			   if (st2>31) st2 -= 32;
			   if (st2>15) st2 -= 16;
			   if (st2>7) st2 -= 8;
			   if (st2>3) st2 -= 4;
			   if (st2>1) st2 -= 2;
			   if (st2>0) {
				   st2 -= 1;
				   retorno = -2; //"Par�metro inv�lido na fun��o. ou N�mero de par�metros inv�lido na fun�ao"
			   }
		   }
		}
		return retorno;
	}
	
	//	Abre o cupom para venda.
	public int aberturaDeCupom() {
		byte[] CMD = {ESC,0};
		return executaCmd(CMD);
	}
	
	//	Abre o cupom para venda passando o cnpj/cpf do cliente.
	public int aberturaDeCupom(String cnpj) {
		byte[] CMD = {ESC,0};
		CMD = adicBytes(CMD,parseParam(cnpj,29).getBytes());
		return executaCmd(CMD);
	}
	
	//	Altera simbolo da moeda corrente, n�o a nescidade de passar o $ no parametro.
	public int alteraSimboloMoeda(String simbolo) {
		byte[] CMD = {ESC,1};
		CMD = adicBytes(CMD,parseParam(simbolo,2).getBytes());
		return executaCmd(CMD);
	}
	
	//	Executa a redu��o Z.
	public int reducaoZ() {
		byte[] CMD = {ESC,5};
		return executaCmd(CMD);
	}
	
	// 	Executa a leitura X.
	public int leituraX() {
		byte[] CMD = {ESC,6};
		return executaCmd(CMD);
	}
	
	//	Venda de item.
	public int vendaItem(String codProd, String descProd, String sitTrib, float qtd, float valor, float desconto) {
		byte[] CMD = {ESC,9};
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(codProd,13) );
		buf.append( parseParam(descProd,29) );
		buf.append( parseParam(sitTrib,2) );
		buf.append( parseParam(qtd,7,3) );
		buf.append( parseParam(valor,8,2) );
		buf.append( parseParam(desconto,8,2) );
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Cancelamento do item anterior.
	public int cancelaItemAnterior() {
		byte[] CMD = {ESC,13};
		return executaCmd(CMD);
	}
	
	//	Cancelamento do cupom.
	public int cancelaCupom() {
		byte[] CMD = {ESC,14};
		return executaCmd(CMD);
	}
	
	//	Cancelamento de item generico.
	public int cancelaItemGenerico(int item) {
		byte[] CMD = {ESC,31};
		CMD = adicBytes(CMD,parseParam(item,4).getBytes());
		return executaCmd(CMD);
	}
	
	//	Inicia o fechamento do cupom.
	public int iniciaFechamentoCupom(char opt, float valor) {
		int tamanho = 14;
		if( opt == ACRECIMO_PERCENTUAL || opt == DESCONTO_PERCENTUAL ) 
			tamanho = 4;			
		byte[] CMD = {ESC,32};
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(String.valueOf(opt),1) );
		buf.append( parseParam(valor,tamanho,2) );		
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Termina o fechamento do cupom.
	public int terminaFechamentoCupom(String menssagem) {
		byte[] CMD = {ESC,34};
		CMD = adicBytes(CMD,parseParam(menssagem,384).getBytes());
		return executaCmd(CMD);
	}
	
	//	Programa a unidade de medida
	//	Valida somente para um item, depois volta ao default.
	public int programaUnidadeMedida(String descUnid) {
		byte[] CMD = {ESC,62,51};
		CMD = adicBytes(CMD,parseParam(descUnid,2).getBytes());
		return executaCmd(CMD);
	}
	
	//	Aumenta a descri��o do item para 200 caracteres
	//	Valida somente para um item, depois volta ao default.
	public int aumentaDescItem(String descricao) {
		byte[] CMD = {ESC,62,52};
		CMD = adicBytes(CMD,parseParam(descricao,200).getBytes());
		return executaCmd(CMD);
	}
	
	//	Venda de item com entrada de Departamento, Desconto e Unidade
	public int vendaItemDepartamento(String sitTrib, float valor, float qtd, float desconto, float acrescimo, String departamento, String unidade, String codProd, String descProd) {
		byte[] CMD = {ESC,63};
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(sitTrib,2) );
		buf.append( parseParam(valor,9,3) );
		buf.append( parseParam(qtd,7,3) );
		buf.append( parseParam(desconto,10,2) );
		buf.append( parseParam(acrescimo,10,2) );
		buf.append( parseParam(departamento,2) );
		buf.append( parseParam("00000000000000000000",20) );
		buf.append( parseParam(unidade,2) );
		buf.append( parseParam(codProd,49) );
		buf.append( parseParam(descProd,201) );
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Nomeia os departamentos.
	public int nomeiaDepartamento(int index, String descricao) {
		byte[] CMD = {ESC,65};
		CMD = adicBytes(CMD,parseParam(descricao,20).getBytes());
		return executaCmd(CMD);
	}
	
	//	Programa formas de pagamentos,
	//	Validas somente para o mesmo dia.
	public int programaFormaPagamento(String[] descricoes) {
		byte[] CMD = {ESC,73};
		StringBuffer buf = new StringBuffer();
		int size = descricoes.length < 48 ? descricoes.length : 48; 
		for(int i=0; i<size; i++) {
			buf.append( parseParam(descricoes[i],16) );
		}
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	//	Efetua forma de pagamento.
	public int efetuaFormaPagamento(int indice, float valor, String descForma) {
		byte[] CMD = {ESC,72};
		StringBuffer buf = new StringBuffer();
		buf.append( parseParam(indice,2) );
		buf.append( parseParam(valor,14,2) );
		buf.append( parseParam(descForma,80) );
		CMD = adicBytes(CMD,buf.toString().getBytes());
		return executaCmd(CMD);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 *        case 0: sMensagem = "Erro de comunica��o f�sica"; break;
       case 1: sMensagem = ""; break;
       case -2: sMensagem = "Par�metro inv�lido na fun��o."; break;
       case -3: sMensagem = "Aliquota n�o programada"; break;
       case -4: sMensagem = "O arquivo de inicializa��o BEMAFI32.INI n�o foi encontrado no diret�rio de sistema do Windows"; break;
       case -5: sMensagem = "Erro ao abrir a porta de comunica��o"; break;
       case -8: sMensagem = "Erro ao criar ou gravar no arquivo STATUS.TXT ou RETORNO.TXT"; break;
       case -27: sMensagem = "Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)"; break;
       case -30: sMensagem = "Fun��o n�o compat�vel com a impressora YANCO"; break;
       case -31: sMensagem = "Forma de pagamento n�o finalizada"; break;
       default : sMensagem = "Retorno indefinido: "+iRetorno; break;
	 */
}
