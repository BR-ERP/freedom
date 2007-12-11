
package org.freedom.ecf.driver;

public enum EStatus {

	RETORNO_OK( "RETORNO_OK" ),

	// Status da impressora Bematech...
	ERRO_COMUNICACAO( "Erro de comunica��o f�sica." ),
	PARAMETRO_INVALIDO( "Par�metro inv�lido na fun��o." ),
	ALIQUOTA_NAO_PROGRAMADA( "Aliquota n�o programada." ),
	ARQ_INI_NAO_ENCONTRADO( "O arquivo de inicializa��o n�o encontrado." ),
	ERRO_ABRIR_PORTA( "Erro ao abrir a porta de comunica��o." ),
	ERRO_GRAVAR_RETORNO( "Erro ao criar ou gravar no arquivo STATUS.TXT ou RETORNO.TXT" ),
	NAO_STATUS_600( "Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)" ),
	FUNCAO_NAO_COMPATIVEL( "Fun��o n�o compat�vel com a impressora YANCO" ),
	FORMA_PAGAMENTO_NAO_FINALIZADA( "Forma de pagamento n�o finalizada" ),
	RETORNO_INDEFINIDO( "Retorno indefinido: " ),
	
	// Status da impressra daruma;
	
	ERROR_01( "Comando habilitado somente em modo manuten��o." ),
	ERROR_02( "Falha na grava��o da Mem�ria Fiscal." ),
	ERROR_03( "Capaciadade de Mem�ria Fiscal esgotada." ),
	ERROR_04( "Data fornecida n�o coincide com a data interna da IF." ),
	ERROR_05( "Impressora Fiscal bloqueada por erro fiscal." ),
	ERROR_06( "Erro no campo de controle da Mem�ria Fiscal." ),
	ERROR_07( "Existem uma mem�ria fiscal instalada." ),
	ERROR_08( "Senha incorreta." ),
	ERROR_09( "Comando habilitado somente em modo opera��o." ),
	ERROR_10( "Documento aberto." ),
	ERROR_11( "Documento n�o aberto." ),
	ERROR_12( "N�o a documento a cancelar." ),
	ERROR_13( "Campo n�merico com valores inv�lidos." ),
	ERROR_14( "Capacidade m�xima da mem�ria foi atingida." ),
	ERROR_15( "Item solicitado n�o foi encontrado." ),
	ERROR_16( "Erro na sintaxe do comando." ),
	ERROR_17( "Overflow nos c�uculos internos." ),
	ERROR_18( "Al�quota de inposto n�o definida para o totalizador selecionado." ),
	ERROR_19( "Mem�ria fiscal vazia." ),
	ERROR_20( "Nenhum campo requer atualiza��o." ),
	ERROR_21( "Repita o comando de Redu��o Z." ),
	ERROR_22( "Redu��o Z do dia j� foi executada." ),
	ERROR_23( "Redu��o Z pendente." ),
	ERROR_24( "Valor de desconto ou acr�cimo inv�lido." ),
	ERROR_25( "Caracteres n�o estamp�veis foram encontrados." ),
	ERROR_26( "Comando n�o pode ser executado." ),
	ERROR_27( "Opera��o abortada." ),
	ERROR_28( "Campo n�merico em zero n�o permitido." ),
	ERROR_29( "Documento anterior n�o foi cupom fiscal." ),
	ERROR_30( "Foi selecionado um documento n�o fiscal inv�lido ou n�o programando." ),
	ERROR_31( "N�o pode autenticar." ),
	ERROR_32( "Desconto de INSS n�o habilitado" ),
	ERROR_33( "Emita comprovantes n�o fiscais vinculados pendentes." ),
	ERROR_34( "Impressora fiscal bloqueada por erro fiscal." ),
	ERROR_35( "Rel�gio interno inoperante." ),
	ERROR_36( "Vers�o do firmware gravada na MF n�o coincide ccom a esperada." ),
	ERROR_38( "Foi selecionada uma forma de pagamento inv�lida." ),
	ERROR_39( "Erro na sequencia de fechamento do documento." ),
	ERROR_40( "J� foi emitido algum documento ap�s a ult�ma redu��o Z." ),
	ERROR_41( "Data/Hora fornecida � anterior a �ltima gravada na Mem�ria Fiscal." ),
	ERROR_42( "Leitura X do in�cio do dia ainda n�o foi emitida." ),
	ERROR_43( "N�o pode mais emitir CNF Vinculado solicitado." ),
	ERROR_44( "Forma de pagamento j� definida." ),
	ERROR_45( "Campo em brando ou contendo caracter de controle." ),
	ERROR_47( "Nenhum perif�rico homologado conectado a porta auxiliar." ),
	ERROR_48( "Valor do estorno superior ao total acumulado nesta forma de pagamento." ),
	ERROR_49( "Forma de pagamento a ser estornada n�o foi encontrada na mem�ria." ),
	ERROR_50( "Impressora sem papel." ),
	ERROR_61( "Opera��o interrompida por falta de energia el�trica." ),
	ERROR_70( "Falha na comunida��o com o m�dulo impressor." ),
	ERROR_80( "Perif�rico conectado a porta auxiliar n�o homologado." ),
	ERROR_81( "" ),
	ERROR_82( "" ),
	ERROR_83( "" ),
	ERROR_84( "" ),
	ERROR_85( "" ),
	ERROR_86( "" ),
	ERROR_87( "" ),
	ERROR_88( "" );
	

	private String message;
	

	EStatus( final String arg ) {
		this.message = arg;
	}
	
	public void setMessage( final String arg ) {
		this.message = arg;
	}

	public String getMessage() {
		return this.message;
	}
}
