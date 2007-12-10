package org.freedom.ecf.app;


public enum EStatusImpressora {

	IMPRESSORA_OK( "Impressora OK" ),
	FIM_DE_PAPEL( "Fim de papel." ),
    POUCO_PAPEL( "Pouco papel." ),
    RELOGIO_ERROR( "Erro no rel�gio." ),
    IMPRESSORA_EM_ERRO( "Impressora em erro." ),
    NO_ESC( "Primeiro dado do comando n�o foi ESC." ),
    NO_COMMAND( "Comando inexistente." ),
    CUPOM_FISCAL_ABERTO( "Cupom fiscal aberto." ),
    NU_PARAMS_INVALIDO( "N�mero de par�metro de CMD inv�lido." ),
    TP_PARAM_INVALIDO( "Tipo de par�metro de CMD inv�lido." ),
    OUT_OF_MEMORY( "Mem�ria fiscal lotada." ),
    MEMORY_ERROR( "Erro na mem�ria RAM CMOS n�o vol�til." ),
    NO_ALIQUOTA( "Al�quota n�o programada." ),
    OUT_OF_ALIQUOTA( "Capacidade de al�quotas esgotada." ),
    NO_ACESESS_CANCELAMENTO( "Cancelamento n�o permitido." ),
    NO_CNPJ_IE( "CNPJ/IE do propriet�rio n�o programados." ),
    COMMAND_NO_EXECUTE( "Comando n�o executado." );

	private final String mensagem;

	EStatusImpressora( String mensagem ) {

		this.mensagem = mensagem;
	}

	String getMensagem() {

		return this.mensagem;
	}
}
