
package org.freedom.ecf.driver;

public enum EStatus {

	RETORNO_OK( "RETORNO_OK" ),
	RETORNO_INDEFINIDO( "Retorno indefinido: " ),

	// Status da impressora Bematech...
	BEMA_ERRO_COMUNICACAO( 0, "Erro de comunica��o f�sica." ),
	BEMA_PARAMETRO_INVALIDO( -2, "Par�metro inv�lido na fun��o." ),
	BEMA_ALIQUOTA_NAO_PROGRAMADA( -3, "Aliquota n�o programada." ),
	BEMA_ARQ_INI_NAO_ENCONTRADO( -4, "O arquivo de inicializa��o n�o encontrado." ),
	BEMA_ERRO_ABRIR_PORTA( -5, "Erro ao abrir a porta de comunica��o." ),
	BEMA_ERRO_GRAVAR_RETORNO( -8, "Erro ao criar ou gravar no arquivo STATUS.TXT ou RETORNO.TXT" ),
	BEMA_NAO_STATUS_600( -27, "Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)" ),
	BEMA_FUNCAO_NAO_COMPATIVEL( -30, "Fun��o n�o compat�vel com a impressora YANCO" ),
	BEMA_FORMA_PAGAMENTO_NAO_FINALIZADA( -31, "Forma de pagamento n�o finalizada" ),
	
	// Status da impressra daruma;	
	DARUMA_ERROR_01( 1, "Comando habilitado somente em modo manuten��o." ),
	DARUMA_ERROR_02( 2, "Falha na grava��o da Mem�ria Fiscal." ),
	DARUMA_ERROR_03( 3, "Capaciadade de Mem�ria Fiscal esgotada." ),
	DARUMA_ERROR_04( 4, "Data fornecida n�o coincide com a data interna da IF." ),
	DARUMA_ERROR_05( 5, "Impressora Fiscal bloqueada por erro fiscal." ),
	DARUMA_ERROR_06( 6, "Erro no campo de controle da Mem�ria Fiscal." ),
	DARUMA_ERROR_07( 7, "Existem uma mem�ria fiscal instalada." ),
	DARUMA_ERROR_08( 8, "Senha incorreta." ),
	DARUMA_ERROR_09( 9, "Comando habilitado somente em modo opera��o." ),
	DARUMA_ERROR_10( 10, "Documento aberto." ),
	DARUMA_ERROR_11( 11, "Documento n�o aberto." ),
	DARUMA_ERROR_12( 12, "N�o a documento a cancelar." ),
	DARUMA_ERROR_13( 13, "Campo n�merico com valores inv�lidos." ),
	DARUMA_ERROR_14( 14, "Capacidade m�xima da mem�ria foi atingida." ),
	DARUMA_ERROR_15( 15, "Item solicitado n�o foi encontrado." ),
	DARUMA_ERROR_16( 16, "Erro na sintaxe do comando." ),
	DARUMA_ERROR_17( 17, "Overflow nos c�uculos internos." ),
	DARUMA_ERROR_18( 18, "Al�quota de inposto n�o definida para o totalizador selecionado." ),
	DARUMA_ERROR_19( 19, "Mem�ria fiscal vazia." ),
	DARUMA_ERROR_20( 20, "Nenhum campo requer atualiza��o." ),
	DARUMA_ERROR_21( 21, "Repita o comando de Redu��o Z." ),
	DARUMA_ERROR_22( 22, "Redu��o Z do dia j� foi executada." ),
	DARUMA_ERROR_23( 23, "Redu��o Z pendente." ),
	DARUMA_ERROR_24( 24, "Valor de desconto ou acr�cimo inv�lido." ),
	DARUMA_ERROR_25( 25, "Caracteres n�o estamp�veis foram encontrados." ),
	DARUMA_ERROR_26( 26, "Comando n�o pode ser executado." ),
	DARUMA_ERROR_27( 27, "Opera��o abortada." ),
	DARUMA_ERROR_28( 28, "Campo n�merico em zero n�o permitido." ),
	DARUMA_ERROR_29( 29, "Documento anterior n�o foi cupom fiscal." ),
	DARUMA_ERROR_30( 30, "Foi selecionado um documento n�o fiscal inv�lido ou n�o programando." ),
	DARUMA_ERROR_31( 31, "N�o pode autenticar." ),
	DARUMA_ERROR_32( 32, "Desconto de INSS n�o habilitado" ),
	DARUMA_ERROR_33( 33, "Emita comprovantes n�o fiscais vinculados pendentes." ),
	DARUMA_ERROR_34( 34, "Impressora fiscal bloqueada por erro fiscal." ),
	DARUMA_ERROR_35( 35, "Rel�gio interno inoperante." ),
	DARUMA_ERROR_36( 36, "Vers�o do firmware gravada na MF n�o coincide ccom a esperada." ),
	DARUMA_ERROR_38( 38, "Foi selecionada uma forma de pagamento inv�lida." ),
	DARUMA_ERROR_39( 39, "Erro na sequencia de fechamento do documento." ),
	DARUMA_ERROR_40( 40, "J� foi emitido algum documento ap�s a ult�ma redu��o Z." ),
	DARUMA_ERROR_41( 41, "Data/Hora fornecida � anterior a �ltima gravada na Mem�ria Fiscal." ),
	DARUMA_ERROR_42( 42, "Leitura X do in�cio do dia ainda n�o foi emitida." ),
	DARUMA_ERROR_43( 43, "N�o pode mais emitir CNF Vinculado solicitado." ),
	DARUMA_ERROR_44( 44, "Forma de pagamento j� definida." ),
	DARUMA_ERROR_45( 45, "Campo em brando ou contendo caracter de controle." ),
	DARUMA_ERROR_47( 47, "Nenhum perif�rico homologado conectado a porta auxiliar." ),
	DARUMA_ERROR_48( 48, "Valor do estorno superior ao total acumulado nesta forma de pagamento." ),
	DARUMA_ERROR_49( 49, "Forma de pagamento a ser estornada n�o foi encontrada na mem�ria." ),
	DARUMA_ERROR_50( 50, "Impressora sem papel." ),
	DARUMA_ERROR_61( 61, "Opera��o interrompida por falta de energia el�trica." ),
	DARUMA_ERROR_70( 71, "Falha na comunida��o com o m�dulo impressor." ),
	DARUMA_ERROR_80( 81, "Perif�rico conectado a porta auxiliar n�o homologado." ),
	DARUMA_ERROR_81( 82, "Banco n�o cadastrado." ),
	DARUMA_ERROR_82( 83, "Nada a imprimir." ),
	DARUMA_ERROR_83( 84, "Extenso n�o cabe no cheque." ),
	DARUMA_ERROR_84( 85, "Leitor de CMC-7 n�o instalado." ),
	DARUMA_ERROR_86( 86, "N�o h� mais mem�ria para o cadastro de bancos." ),
	DARUMA_ERROR_87( 87, "Impress�o no verso somento ap�s a impress�o da frente do cheque." ),
	DARUMA_ERROR_88( 88, "Valor inv�lido para o cheque." ),	
	DARUMA_WARNING_01( 1001, "Near End foi detectado." ),
	DARUMA_WARNING_02( 1002, "Execute redu��o Z." ),
	DARUMA_WARNING_04( 1004, "Queda de energia." ),
	DARUMA_WARNING_10( 1010, "Bateria interna requer substitui��o." ),
	DARUMA_WARNING_20( 1020, "Opera��o habilitada somente em MIT." );
	

	private String message;
	private int code;
	

	EStatus( final String arg ) {
		this.message = arg;
	}
	
	EStatus( final int code, final String arg ) {
		this.code = code;
		this.message = arg;
	}
	
	public void setMessage( final String arg ) {
		this.message = arg;
	}

	public String getMessage() {
		return this.message;
	}

	public int getCode() {
		return this.code;
	}
}
