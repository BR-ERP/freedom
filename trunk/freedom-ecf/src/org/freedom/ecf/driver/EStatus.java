
package org.freedom.ecf.driver;

public enum EStatus {

	RETORNO_OK( "RETORNO_OK" ),
	ERRO_COMUNICACAO( "Erro de comunica��o f�sica." ),
	PARAMETRO_INVALIDO( "Par�metro inv�lido na fun��o." ),
	ALIQUOTA_NAO_PROGRAMADA( "Aliquota n�o programada." ),
	ARQ_INI_NAO_ENCONTRADO( "O arquivo de inicializa��o n�o encontrado." ),
	ERRO_ABRIR_PORTA( "Erro ao abrir a porta de comunica��o." ),
	ERRO_GRAVAR_RETORNO( "Erro ao criar ou gravar no arquivo STATUS.TXT ou RETORNO.TXT" ),
	NAO_STATUS_600( "Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)" ),
	FUNCAO_NAO_COMPATIVEL( "Fun��o n�o compat�vel com a impressora YANCO" ),
	FORMA_PAGAMENTO_NAO_FINALIZADA( "Forma de pagamento n�o finalizada" ),
	RETORNO_INDEFINIDO( "Retorno indefinido: " );

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
