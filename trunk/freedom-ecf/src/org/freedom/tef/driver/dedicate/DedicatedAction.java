package org.freedom.tef.driver.dedicate;

import java.util.ArrayList;
import java.util.List;

import org.freedom.tef.app.TefAction;


public class DedicatedAction implements TefAction {
	
	public static final DedicatedAction ERRO = new DedicatedAction( -1 );
	
	public static final DedicatedAction WARNING = new DedicatedAction( -2 );
	
	/**
	 * Est� de volvendo um valor para, se desejado, ser armazenado pela automa��o.
	 */
	public static final DedicatedAction ARMAZENAR = new DedicatedAction( 0 );
	
	/**
	 * Mensagem para visor do eperador.
	 */
	public static final DedicatedAction MENSAGEM_OPERADOR = new DedicatedAction( 1 );
	
	/**
	 * Mensagem para visor do cliente.
	 */
	public static final DedicatedAction MENSAGEM_CLIENTE = new DedicatedAction( 2 );
	
	/**
	 * Mensagem para os dois visores.
	 */
	public static final DedicatedAction MENSAGEM_TODOS = new DedicatedAction( 3 );
	
	/**
	 * Texto que dever� ser utilizado como cabe�alho na apresenta��o do menu( Comando 21 ).
	 */
	public static final DedicatedAction CABECALHO_MENU = new DedicatedAction( 4 );
	
	/**
	 * Deve remover a mensagem apresentada no visor do operador.
	 */
	public static final DedicatedAction REMOVER_MESAGEM_OPERADOR = new DedicatedAction( 11 );
	
	/**
	 * Deve remover a mensagem apresentada no visor do cliente.
	 */
	public static final DedicatedAction REMOVER_MESAGEM_CLIENTE = new DedicatedAction( 12 );
	
	/**
	 * Deve remover a mensagem apresentada no visor do operador e do cliente.
	 */
	public static final DedicatedAction REMOVER_MESAGEM_TODOS = new DedicatedAction( 13 );
	
	/**
	 * Deve remover o texto utilizado como cabe�alho na apresenta��o do menu.
	 */
	public static final DedicatedAction REMOVER_CABECALHO_MENU = new DedicatedAction( 14 );
	
	/**
	 * Cabe�alho a ser apresentado pela aplica��o.
	 */
	public static final DedicatedAction CABECALHO = new DedicatedAction( 15 );
	
	/**
	 * Remover cabe�alho.
	 */
	public static final DedicatedAction REMOVER_CABECALHO = new DedicatedAction( 16 );
	
	/**
	 * Deve obter uma resposta do tipo SIM/N�O. 
	 * No retorno o primenro car�cter presente em Buffer deve conter 0 se confirma e 1 se cancela.
	 */
	public static final DedicatedAction RETORNAR_CONFIRMACAO = new DedicatedAction( 20 );
	
	/**
	 * Deve apresentar um menu de op��es e permitir que o usu�rio selecione uma delas. 
	 * Na chamada o par�metro Buffer cont�m as op��es da forma que ele desejar 
	 * (n�o sendo necess�rio incluir os �ndices 1,2,...) e ap�s a sele��o feira pelo usu�rio, 
	 * retornar em Buffer o �ndice i escolhido pelo operador ( em ASCII )
	 */
	public static final DedicatedAction MOSTRAR_MENU = new DedicatedAction( 21 );
	
	/**
	 * Deve aquardar uma tecla do operador. 
	 * � utilizada quando se deseja que o operador seja avisado de alguma mensagem apresentada na tela.
	 */
	public static final DedicatedAction AGUADAR_TECLA_OPERADOR = new DedicatedAction( 22 );
	
	/**
	 * Este comando indica que a rotina est� perguntando para a aplica��o se ele deseja interromper 
	 * o processo de coleta de dados ou n�o. Esse c�digo ocorre quando a DLL est� acessando algum perif�rico 
	 * e permite que a automa��o interrompa esse acesso 
	 * (por exemplo: aquardando a passagem de um cart��o pela lietora ou a difitra��o de senha pelo cliente)
	 */ 
	public static final DedicatedAction CONFIRMAR_IMTERRUPSAO = new DedicatedAction( 23 );
	
	/**
	 * Deve ser lido um campo cujo tamanho est� entre Tamanho Minimo e Tamanho M�ximo.
	 * O campo lido de ser devolvido em Buffer
	 */
	public static final DedicatedAction LER_CAMPO = new DedicatedAction( 30 );
	
	/**
	 * Deve ser lido o n�mero de um cheque. 
	 * A coleta pode ser feita via leitura de CMC-7 ou pela diguta��o da primeira linha do cheque. 
	 * No retorno deve ser devolvido em Buffer "0:" ou "1:" seguido do n�mero coletado manualmente ou 
	 * pela leitura do CMC-7, respectivamente. Quando o n�mero for coletado manualmente o formato � o sequinte:
	 * Compensa��o (3), Banco (3), Agencia(4), C1 (1), Conta Conrrente (10), C2 (1), N�mero do cheque (6), e C3(1), nesta ordem.
	 * Notar que estes campos s�o os que est�o na parte superior de um cheque e na ordem apresentada.
	 */
	 // Sugerimos que na coleta seja apresentada uma interface que perminta ao operador indentificar 
	 // e digitar adequadamente estas informa��es de forma que a consulta n�o seja feita com dados errados,
	 // retornando como bom um cheque com problemas.
	public static final DedicatedAction LER_CHEQUE = new DedicatedAction( 31 );
	
	/** 
	 * Deve ser lido um campo monet�rio ou seja, aceita o delimitador de centavos e 
	 * devolvido no par�metro Buffer.
	 */
	public static final DedicatedAction LER_VALOR = new DedicatedAction( 34 );
	
	/**
	 * Deve ser lido um c�digo em barras ou o mesmo deve ser coletado manualmente.
	 * No retorno Buffer deve conter "0:" ou "1:" 
	 * sequido do c�digo em barras coletado manualmente ou pela leitora, respectivamente
	 */
	public static final DedicatedAction LER_CODIGO_DE_BARRAS = new DedicatedAction( 35 );
		

	private int code;
	
	private static final List<DedicatedAction> actions = new ArrayList<DedicatedAction>();
	
	static {
		actions.add( ERRO );
		actions.add( WARNING );
		actions.add( ARMAZENAR );
		actions.add( MENSAGEM_OPERADOR );
		actions.add( MENSAGEM_CLIENTE );
		actions.add( MENSAGEM_TODOS );
		actions.add( CABECALHO_MENU );
		actions.add( REMOVER_MESAGEM_OPERADOR );
		actions.add( REMOVER_MESAGEM_CLIENTE );
		actions.add( REMOVER_MESAGEM_TODOS );
		actions.add( REMOVER_CABECALHO_MENU );
		actions.add( CABECALHO );
		actions.add( REMOVER_CABECALHO );
		actions.add( RETORNAR_CONFIRMACAO );
		actions.add( MOSTRAR_MENU );
		actions.add( AGUADAR_TECLA_OPERADOR );
		actions.add( CONFIRMAR_IMTERRUPSAO );
		actions.add( LER_CAMPO );
		actions.add( LER_CHEQUE );
		actions.add( LER_VALOR );
		actions.add( LER_CODIGO_DE_BARRAS );
	}
	
	
	private DedicatedAction( final Integer code ) {
		this.code = code;		
	}
	
	public int code() {
		return code;
	}
	
	public static DedicatedAction getDedicatedAction( final Integer code ) {
		
		for ( DedicatedAction da : actions ) {
			if ( da.code == code ) {
				return da;
			}
		}
		
		return null;
	}

	@Override
	public boolean equals( Object obj ) {

		if ( obj == null || !(obj instanceof DedicatedAction) ) {
			return false;
		}
		
		return this.code == ((DedicatedAction)obj).code();
	}
}
