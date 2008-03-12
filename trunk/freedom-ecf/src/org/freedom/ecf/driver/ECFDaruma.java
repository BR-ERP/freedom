
package org.freedom.ecf.driver;

import static org.freedom.ecf.driver.EStatus.RETORNO_OK;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.freedom.ecf.com.Serial;

/**
 * Classe implementa metodos de acesso a comandos de impress�o <BR>
 * Projeto: freedom-ecf <BR>
 * Pacote: org.freedom.ecf.driver <BR>
 * Classe:
 * 
 * @(#)ECFDaruma.java <BR>
 *                    <BR>
 *                    Este programa � licenciado de acordo com a LGPL (Lesser General Public License), <BR>
 *                    vers�o 2.1, Fevereiro de 1999 <BR>
 *                    A LGPL deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 *                    Caso uma c�pia da LGPL n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 *                    o LICENCIADOR ou ent�o pegar uma c�pia em: <a href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt> Creative Commons</a> <BR>
 *                    Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar de acordo com os termos da LGPL. <BR>
 *                    <BR>
 * @author Setpoint Inform�tica Ltda. Robson Sanchez/Alex Rodrigues <BR>
 * @version 1.0.0 - 10/12/2007 <BR>
 *          <BR>
 */

public class ECFDaruma extends AbstractECFDriver {
	
	private static final char CEND = (char) 255;
	
	private static Map<String, String> formasDePagamento;	
	
	private String descricaoDoProduto;
	
	private String unidadeDeMedida;
	
	private int indexItemAtual = 0;
	
	private boolean relatorioGerencialAberto = false;

	/**
	 * Construtor da classe ECFDaruma. <BR>
	 */
	public ECFDaruma() {

		super();
	}

	/**
	 * Construtor da classe ECFDaruma. <BR>
	 * Inicia a constru��o da classe chamando o construtor padr�o da classe super <BR>
	 * e chama o metodo ativaPorta(int). <BR>
	 * 
	 * @param com
	 *            parametro para ativa��o da porta serial.<BR>
	 */
	public ECFDaruma( final int com ) {

		this();
		ativaPorta( com );		
		setFormasDePagamento( retornoFormasDePagamento() );
	}

	/**
	 * Construtor da classe ECFDaruma. <BR>
	 * Inicia a constru��o da classe chamando o construtor padr�o da classe super <BR>
	 * e chama o metodo ativaPorta(int). <BR>
	 * 
	 * @param port
	 *            parametro para ativa��o da porta serial.<BR>
	 */
	public ECFDaruma( final String port ) {

		this( Serial.convPorta( port ) );
	}	
	
	public static Map<String, String> getFormasDePagamento() {	
		return formasDePagamento;
	}
	
	public static void setFormasDePagamento( Map<String, String> formasDePagamento ) {	
		ECFDaruma.formasDePagamento = formasDePagamento;
	}

	public String getDescricaoDoProduto() {	
		return descricaoDoProduto;
	}
	
	public void setDescricaoDoProduto( String descricaoDoProduto ) {	
		this.descricaoDoProduto = descricaoDoProduto;
	}
	
	public String getUnidadeDeMedida() {	
		return unidadeDeMedida;
	}
	
	public void setUnidadeDeMedida( String unidadeDeMedida ) {	
		this.unidadeDeMedida = unidadeDeMedida;
	}
	
	public int getIndexItemAtual() {	
		return indexItemAtual;
	}
	
	public void setIndexItemAtual( int indexItemAtual ) {	
		this.indexItemAtual = indexItemAtual;
	}

	/**
	 * Prepara o comando conforme o protocolo de comunica��o com a impressora. <BR>
	 * O protocolo de comunica��o com a impressora � estruturado <BR>
	 * em blocos e possui a seguinte forma: <BR>
	 * <BR>
	 * CMD - Sequ�ncia de bytes que comp�e o comando e seus par�metros. <BR>
	 * 
	 * @param CMD
	 *            comando a ser executado e seus par�metros. <BR>
	 * @see org.freedom.ecf.driver.AbstractECFDriver#preparaCmd(byte[])
	 */
	public byte[] preparaCmd( final byte[] CMD ) {

		final int tamCMD = CMD.length;
		final int tam = tamCMD + 1;
		byte[] retorno = new byte[ tam ];
		for ( int i = 0; i < tam; i++ ) {
			if ( i == ( tam - 1 ) ) {
				retorno[ i ] = 13;
			} else {
				retorno[ i ] = CMD[ i ];
			}
		}

		return retorno;
	}

	/**
	 * Este metodo executa o comando chamando os metodos <BR>
	 * preparaCmd(byte[]) <BR>
	 * enviaCmd(byte[]) <BR>
	 * aguardaImpressal(byte[]) <BR>
	 * e devolve o resultado do emvio do camando. <BR>
	 * 
	 * @param CMD
	 *            comando a ser executado e seus par�metros. <BR>
	 * @see org.freedom.ecf.driver.AbstractECFDriver#executaCmd(byte[], int)
	 */
	public int executaCmd( final byte[] CMD, final int tamRetorno ) {
		return executaCmd( CMD, tamRetorno, true );
	}
	
	public int executaCmd( final byte[] CMD, final int tamRetorno, final boolean checkcmd ) {

		byte[] retorno = null;
		byte[] cmd = null;

		cmd = preparaCmd( CMD );
		retorno = enviaCmd( cmd, tamRetorno );

		int cmdRetorno = checkcmd ? checkRetorno( retorno ) : checkRetorno2( retorno ); 
		
		return cmdRetorno;
	}

	/**
	 * Converte o retorno dos comandos do formato BCD para ASCII. <BR>
	 * 
	 * @param bcdParam
	 *            Retorno a ser convertido. <BR>
	 * @return String com o resultado da conver��o. <BR>
	 */
	private String bcdToAsc( final byte[] bcdParam ) {

		final StringBuffer retorno = new StringBuffer();

		int bcd = 0;
		byte byteBH = 0;
		byte byteBL = 0;

		for ( int i = 0; i < bcdParam.length; i++ ) {

			bcd = bcdParam[ i ];

			// Ajuste dos bytes para o padr�o de c�lculo (o java trabalha com bytes de -128 a 127)
			if ( bcd < 0 ) {
				bcd += 256;
			}

			byteBH = (byte) ( bcd / 16 );
			byteBL = (byte) ( bcd % 16 );

			retorno.append( byteBH );
			retorno.append( byteBL );
		}

		return retorno.toString();
	}

	/**
	 * Formata os retorna enviado pela impressora <BR>
	 * separando o STATUS do estado da impressora dos dados do retorno, onde <BR>
	 * ACK (06) - byte indicativo de recebimento correto. <BR>
	 * ST1 2 ST2 - bytes de estado da impressora. <BR>
	 * NAK (15h ou 21d) - byte indicativo de recebimento incorreto. <BR>
	 * <BR>
	 * O retorno tem a seguinte sintaxe : <BR>
	 * [ACK][retorno solicitado][ST1][ST2] <BR>
	 * 
	 * @param bytes
	 *            bytes retornados pela porta serial.<BR>
	 * @return retorno indece para a mensagem.
	 * @see org.freedom.ecf.driver.AbstractECFDriver#checkRetorno(byte[])
	 */
	public int checkRetorno( final byte[] bytes ) {

		int retorno = 0;
		int erro = 0;
		int warning = 0;
		byte e1 = 0;
		byte e2 = 0;
		byte w1 = 0;
		byte w2 = 0;
		byte[] bytesLidos;
		
		if ( bytes != null ) {
			
			if ( bytes.length > 2 ) {
				e1 = bytes[ 2 ];
			}
			if ( bytes.length > 3 ) {
				e2 = bytes[ 3 ];
			}
			if ( bytes.length > 4 ) {
				w1 = bytes[ 4 ];
			}
			if ( bytes.length > 5 ) {
				w2 = bytes[ 5 ];
			}
			if ( bytes.length > 7 ) {
				bytesLidos = new byte[ bytes.length - 7 ];
				System.arraycopy( bytes, 6, bytesLidos, 0, bytesLidos.length );
				setBytesLidos( bytesLidos );
			}

			erro = checkError( e1, e2 );
			warning = checkWarning( w1, w2 );
			
			if ( erro != 0 ) {
				retorno = erro;
			}
			else if ( warning != 1000 ) {
				retorno = warning;
			}
		}
	    
		return retorno;
	}
	
	public int checkRetorno2( final byte[] bytes ) {

		int retorno = RETORNO_OK.getCode();
		byte[] bytesLidos;
		
		if ( bytes != null ) {
			if ( bytes.length > 2 ) {
				bytesLidos = new byte[ bytes.length - 2 ];
				System.arraycopy( bytes, 1, bytesLidos, 0, bytesLidos.length );
				setBytesLidos( bytesLidos );
			}
		}
	    
		return retorno;
	}

	/**
	 * Auxilia o metodo checkRetorno.<BR>
	 * 
	 * @param ST1
	 * @return retorno checado
	 */
	private int checkError( byte e1, byte e2 ) {

		int retorno = Integer.parseInt( "" + (char) e1 + (char) e2 );

		return retorno;
	}

	/**
	 * Auxilia o metodo checkRetorno.<BR>
	 * 
	 * @param ST2
	 * @return retorno checado
	 */
	private int checkWarning( byte w1, byte w2 ) {

		// O C�digo na EStatus tem o 10 na frente para diferenciar dos de erros.
		int retorno = Integer.parseInt( "10" + (char) w1 + (char) w2 );

		return retorno;
	}

	public EStatus decodeReturnECF( final int arg ) {

		EStatus status = RETORNO_OK;

		for ( EStatus es : EStatus.values() ) {
			if ( arg == es.getCode() ) {
				status = es;
				break;
			}
		}

		return status;
	}

	public int alteraSimboloMoeda( final String simbolo ) {

		return -1;
	}

	public int adicaoDeAliquotaTriburaria( final String aliq, final char opt ) {

		return -1;
	}

	public int programaHorarioVerao() {

		final char CCMD = (char) 228;
		byte[] CMD = { ESC, (byte) CCMD };
		
		final StringBuffer buf = new StringBuffer();

		buf.append( "xx" );
		buf.append( horarioVeraoAtivo() ? 0 : 1 );
		buf.append( "xxxxxxxxxxxxxxxxx" );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 7 );
	}

	private boolean horarioVeraoAtivo() {

		final char CCMD = (char) 229;
		final byte[] CMD = { ESC, (byte) CCMD };
		
		executaCmd( CMD, 33 );
		final String retorno = new String( getBytesLidos() );
		
		boolean ativo = ( retorno.length() > 2 && retorno.charAt( 2 ) == '1' );
		System.out.println( retorno );
		
		return ativo;
	}

	public int nomeiaTotalizadorNaoSujeitoICMS( final int indice, final String desc ) {

		return -1;
	}

	public int programaTruncamentoArredondamento( final char opt ) {

		return -1;
	}

	public int programarEspacoEntreLinhas( final int espaco ) {

		return -1;
	}

	public int programarLinhasEntreCupons( final int espaco ) {

		return -1;
	}
	
	public int nomeiaDepartamento( final int index, final String descricao ) {

		return -1;
	}

	/**
	 * Abertura de Cupom Fiscal. <br>
	 * 
	 * @return estado da impressora.<br>
	 */
	public int aberturaDeCupom() {

		final char CCMD = (char) 200;
		final byte[] CMD = { ESC, (byte) CCMD };
		
		setIndexItemAtual( 0 );

		return executaCmd( CMD, 13 );
	}

	/**
	 * Abertura de Cupom Fiscal, informando o CNPJ/CPF do cliente.<br>
	 * 
	 * @param cnpj
	 *            CNPJ/CPF do cliente.<br>
	 * 
	 * @return estado da impressora.<br>
	 */
	public int aberturaDeCupom( final String cnpj ) {

		int retorno = indendificacaoConsumidor( cnpj );

		if ( retorno == 0 ) {
			retorno = aberturaDeCupom();
		}

		return retorno;
	}
	
	public int indendificacaoConsumidor( final String texto ) {

		final char CCMD = (char) 208;
		byte[] CMD = { ESC, (byte) CCMD };
		
		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( texto, 153 ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 7 );
	}

	/**
	 * Programa na mem�ria da impressora a unidade de medida que deseja usar no pr�ximo comando<br>
	 * de Venda de Item. Este comando tem validade somente para a impress�o de um Item,<br>
	 * voltando ao default que � sem a unidade de medida.<br>
	 * � necess�rio program�-la, novamente, caso deseje us�-la para a pr�xima venda.<br>
	 * No exemplo abaixo, est� sendo programada a unidade Kg.<br>
	 * 
	 * Exemplo:<br>
	 * <br>
	 * <p style="background=#EEEEEE">// para definir a unidade de medida para o pr�ximo item.<br>
	 * programaUnidadeMedida( "Kg" );// Kilograma<br>
	 * </p>
	 * <br>
	 * 
	 * @param descUnid
	 *            unidade de medida.<br>
	 * 
	 * @return estado da impressora.<br>
	 */
	public int programaUnidadeMedida( final String descUnid ) {
		
		setUnidadeDeMedida( descUnid );
		
		return 0;
	}

	public int aumentaDescItem( final String descricao ) {

		setDescricaoDoProduto( descricao );

		return 0;
	}

	/**
	 * Este comando executa a venda de item utilizando valor com duas casas decimais.<br>
	 * Para execu��o deste comando obrigatoriamente o cupom deve estar aberto.<br>
	 * 
	 * @param codProd
	 *            c�digo do produto.<br>
	 * @param descProd
	 *            descri��o do produto.<br>
	 * @param aliquota
	 *            aliquota do item.<br>
	 * @param tpqtd
	 *            tipo de quantidade, inteiro ou decimal.<br>
	 * @param qtd
	 *            quantidade do item.<br>
	 * @param valor
	 *            valor do item.<br>
	 * @param tpdesc
	 *            tipo do desconto, percentual ou valor.<br>
	 * @param desconto
	 *            valor do desconto.<br>
	 * 
	 * @return estado da impressora.<br>
	 */
	public int vendaItem( final String codProd, final String descProd, final String aliquota, final char tpqtd, final float qtd, final float valor, final char tpdesc, final float desconto ) {

		/*
		 * Descri��o de produto.
		 */
		
		final char CCMD = (char) 202;
		byte[] CMD = { ESC, (byte) CCMD };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( aliquota, 2 ) );
		buf.append( parseParam( codProd, 18 ) );
		if ( tpdesc == DESCONTO_PERC ) {
			buf.append( 0 );
		} else if ( tpdesc == DESCONTO_VALOR ) {
			buf.append( 1 );
		} else if ( tpdesc == ACRECIMO_PERC  ) {
			buf.append( 2 );
		} else if ( tpdesc == ACRECIMO_VALOR) {
			buf.append( 3 );
		}
		buf.append( parseParam( desconto, 9, 2 ) );
		buf.append( parseParam( valor, 10, 3 ) );		
		buf.append( parseParam( qtd, 8, 3 ) );		
		if ( getUnidadeDeMedida() != null ) {			
			buf.append( parseParam( getUnidadeDeMedida(), 2 ) );
			setUnidadeDeMedida( null );
		}
		else {
			buf.append( parseParam( "  ", 2 ) );
		}
		if ( getDescricaoDoProduto() != null ) {			
			buf.append( parseParam( getDescricaoDoProduto(), 199 ).trim() );
			setDescricaoDoProduto( null );
		}
		else {
			buf.append( parseParam( descProd, 28 ).trim() );
		}

		CMD = adicBytes( CMD, buf.toString().getBytes() );
		CMD = adicBytes( CMD, new byte[]{ (byte)CEND } );
		
		final int returnCommand = executaCmd( CMD, 21 );
		
		if ( returnCommand == RETORNO_OK.getCode() ) {
			 indexItemAtual++;
		}

		return returnCommand;
	}

	/**
	 * Este comando executa a venda de item utilizando valor com tr�s casas decimais.<br>
	 * Para execu��o deste comando obrigatoriamente o cupom deve estar aberto.<br>
	 * 
	 * @param codProd
	 *            c�digo do produto.<br>
	 * @param descProd
	 *            descri��o do produto.<br>
	 * @param aliquota
	 *            aliquota do item.<br>
	 * @param tpqtd
	 *            tipo de quantidade, inteiro ou decimal.<br>
	 * @param qtd
	 *            quantidade do item.<br>
	 * @param valor
	 *            valor do item.<br>
	 * @param tpdesc
	 *            tipo do desconto, percentual ou valor.<br>
	 * @param desconto
	 *            valor do desconto.<br>
	 * 
	 * @return estado da impressora.<br>
	 */
	public int vendaItemTresCasas( final String codProd, final String descProd, final String aliquota, final char tpqtd, final float qtd, final float valor, final char tpdesc, final float desconto ) {

		/*
		 * Descri��o de produto.
		 */
		
		final char CCMD = (char) 202;
		byte[] CMD = { ESC, (byte) CCMD };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( aliquota, 2 ) );
		buf.append( parseParam( codProd, 18 ) );
		if ( tpdesc == DESCONTO_PERC ) {
			buf.append( 0 );
		} else if ( tpdesc == DESCONTO_VALOR ) {
			buf.append( 1 );
		} else if ( tpdesc == ACRECIMO_PERC  ) {
			buf.append( 2 );
		} else if ( tpdesc == ACRECIMO_VALOR) {
			buf.append( 3 );
		}
		buf.append( parseParam( desconto, 9, 2 ) );
		buf.append( parseParam( valor, 10, 3 ) );		
		buf.append( parseParam( qtd, 8, 3 ) );		
		if ( getUnidadeDeMedida() != null ) {			
			buf.append( parseParam( getUnidadeDeMedida(), 2 ) );
			setUnidadeDeMedida( null );
		}
		else {
			buf.append( parseParam( "  ", 2 ) );
		}
		if ( getDescricaoDoProduto() != null ) {			
			buf.append( parseParam( getDescricaoDoProduto(), 199 ).trim() );
			setDescricaoDoProduto( null );
		}
		else {
			buf.append( parseParam( descProd, 28 ).trim() );
		}

		CMD = adicBytes( CMD, buf.toString().getBytes() );
		CMD = adicBytes( CMD, new byte[]{ (byte)CEND } );
		
		final int returnCommand = executaCmd( CMD, 21 );
		
		if ( returnCommand == RETORNO_OK.getCode() ) {
			 indexItemAtual++;
		}

		return returnCommand;
	}

	public int vendaItemDepartamento( final String sitTrib, final float valor, final float qtd, final float desconto, final float acrescimo, final int departamento, final String unidade, final String codProd, final String descProd ) {

		return -1;
	}

	public int cancelaItemAnterior() {
		
		final int returnCommand = cancelaItemGenerico( getIndexItemAtual() );
		
		return returnCommand;
	}

	public int cancelaItemGenerico( final int item ) {

		final char CCMD = (char) 204;
		byte[] CMD = { ESC, (byte) CCMD };
		
		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( item, 3 ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 10 );
	}

	public int iniciaFechamentoCupom( final char opt, final float valor ) {

		/*
		 * Totaliza��o de Cupom Fiscal.
		 */
		
		final char CCMD = (char) 206;
		byte[] CMD = { ESC, (byte) CCMD };

		final StringBuffer buf = new StringBuffer();
		if ( opt == DESCONTO_PERC ) {
			buf.append( 0 );
		} else if ( opt == DESCONTO_VALOR ) {
			buf.append( 1 );
		} else if ( opt == ACRECIMO_PERC  ) {
			buf.append( 2 );
		} else if ( opt == ACRECIMO_VALOR) {
			buf.append( 3 );
		}
		buf.append( parseParam( valor, 12, 2 ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 19 );
	}

	public int efetuaFormaPagamento( final String indice, final float valor, final String descForma ) {

		/*
		 * Descri��o da forma de pagamento.
		 */
		
		final char CCMD = (char) 207;
		byte[] CMD = { ESC, (byte) CCMD };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( indice, 2 ) );
		buf.append( parseParam( valor, 12, 2 ) );
		buf.append( parseParam( descForma, 47 ).trim() );

		CMD = adicBytes( CMD, buf.toString().getBytes() );
		CMD = adicBytes( CMD, new byte[]{ (byte)CEND } );

		return executaCmd( CMD, 19 );
	}

	public int finalizaFechamentoCupom( final String mensagem ) {
		
		/*
		 * Fechamento de cupom fiscal com mesagem promocional.
		 */

		final char CCMD = (char) 209;
		byte[] CMD = { ESC, (byte) CCMD };

		final StringBuffer buf = new StringBuffer();
		buf.append( parseParam( "S", 1 ).trim() );
		buf.append( parseParam( mensagem, 383 ).trim() );

		CMD = adicBytes( CMD, buf.toString().getBytes() );
		CMD = adicBytes( CMD, new byte[]{ (byte)CEND } );

		return executaCmd( CMD, 19 );
	}

	public int cancelaCupom() {

		final char CCMD = (char) 211;
		byte[] CMD = { ESC, (byte) CCMD };

		return executaCmd( CMD, 13 );
	}

	public String programaFormaPagamento( final String descricao ) {
		
		String indexFormaDePagamento = null;

		if ( descricao != null && "Dinheiro".equals( descricao ) ) {
			indexFormaDePagamento = "01";
		} 
		else {
			
			String formaDePagamento = null;
			String indexFirstNull = null;
			
			for ( int index = 51; index <= 66; index++ ) {
				formaDePagamento = getFormasDePagamento().get( String.valueOf( index ) );
				if ( formaDePagamento != null && descricao != null && 
						descricao.trim().equals( formaDePagamento.trim() ) ) {
					indexFormaDePagamento = String.valueOf( index );
					break;
				}
				else if ( indexFirstNull == null && "���������������������".equals( formaDePagamento ) ) {
					indexFirstNull = String.valueOf( index );
				}
			}

			if ( indexFormaDePagamento == null && descricao != null ) {
				final char CCMD = (char) 222;
				byte[] CMD = { ESC, (byte) CCMD };

				final StringBuffer buf = new StringBuffer();
				buf.append( indexFirstNull );
				buf.append( parseParam( descricao, 21 ) );

				CMD = adicBytes( CMD, buf.toString().getBytes() );
				
				executaCmd( CMD, 9 );
				
				indexFormaDePagamento = new String( getBytesLidos() );
			}
		}
		
		return indexFormaDePagamento;
	}

	public int estornoFormaPagamento( final String descricaoOrigem, final String descricaoDestino, final float valor ) {
		
		String indexFormaDePagamentoOrigem = null;
		String indexFormaDePagamentoDestino = null;

		if ( descricaoOrigem != null && "Dinheiro".equals( descricaoOrigem ) ) {
			indexFormaDePagamentoOrigem = "01";
		} 
		else {
			
			String formaDePagamentoOrigem = null;
			
			for ( int index = 51; index <= 66; index++ ) {
				formaDePagamentoOrigem = getFormasDePagamento().get( String.valueOf( index ) );
				if ( formaDePagamentoOrigem != null && descricaoOrigem != null && 
						descricaoOrigem.trim().equals( formaDePagamentoOrigem.trim() ) ) {
					indexFormaDePagamentoOrigem = String.valueOf( index );
					break;
				}
			}
		}
		
		if ( descricaoDestino != null && "Dinheiro".equals( descricaoDestino ) ) {
			indexFormaDePagamentoDestino = "01";
		} 
		else {
			
			String formaDePagamentoDestino = null;
			
			for ( int index = 51; index <= 66; index++ ) {
				formaDePagamentoDestino = getFormasDePagamento().get( String.valueOf( index ) );
				if ( formaDePagamentoDestino != null && descricaoDestino != null && 
						descricaoDestino.trim().equals( formaDePagamentoDestino.trim() ) ) {
					indexFormaDePagamentoDestino = String.valueOf( index );
					break;
				}
			}
		}
		
		final char CCMD = (char) 219;
		byte[] CMD = { ESC, (byte) CCMD };

		final StringBuffer buf = new StringBuffer();
		buf.append( indexFormaDePagamentoOrigem );
		buf.append( indexFormaDePagamentoDestino );
		buf.append( parseParam( Integer.parseInt( retornoNumeroCupom() ), 6 ) );
		buf.append( parseParam( valor , 12, 2 ) );
		
		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 17 );
	}

	/**
	 * Executa a Redu��o Z para a data atual da impressora fiscal.<BR>
	 * 
	 * @return estado da impressora.<br>
	 */
	public int reducaoZ() {

		final char CCMD = (char) 252;
		byte[] CMD = { ESC, (byte) CCMD };
		
		final SimpleDateFormat sdt = new SimpleDateFormat( "ddMMyyHHmmss" );
		final String time = sdt.format( Calendar.getInstance().getTime() );
		
		CMD = adicBytes( CMD, time.getBytes() );

		return executaCmd( CMD, 13 );
	}

	/**
	 * Executa a Leitura X na impressora fiscal.<BR>
	 * 
	 * @return estado da impressora.<br>
	 */
	public int leituraX() {

		final char CCMD = (char) 250;
		final byte[] CMD = { ESC, (byte) CCMD };

		return executaCmd( CMD, 13 );
	}

	public int leituraXSerial() {

		final char CCMD = (char) 251;
		final byte[] CMD = { ESC, (byte) CCMD };

		return executaCmd( CMD, 13 );
	}

	public int leituraMemoriaFiscal( final Date dataIni, final Date dataFim, final char tipo ) {
		
		return -1;
	}

	public int leituraMemoriaFiscal( final int ini, final int fim, final char tipo ) {

		return -1;
	}

	public int relatorioGerencial( final String texto ) {
		
		if ( ! relatorioGerencialAberto ) {
    		final char CCMD = (char) 210;
    		byte[] CMD = { ESC, (byte) CCMD };
    
    		final StringBuffer buf = new StringBuffer();
    		buf.append( parseParam( 1, 2 ) );
    		
    		CMD = adicBytes( CMD, buf.toString().getBytes() );
    		
    		executaCmd( CMD, 15 );
    		
    		relatorioGerencialAberto = true;
		}

		return usaComprovanteNFiscalVinculado( texto );
	}

	public int fechamentoRelatorioGerencial() {
		
		final char CCMD = (char) 216;
		byte[] CMD = { ESC, (byte) CCMD };
		
		relatorioGerencialAberto = false;
		
		return executaCmd( CMD, 7 );
	}
	
	public int comprovanteNFiscalNVinculado( final String opt, final float valor, final String formaPag ) {

		final char CCMD = (char) 212;
		byte[] CMD = { ESC, (byte) CCMD };

		final StringBuffer buf = new StringBuffer();
		/*buf.append( programaFormaPagamento( formaPag ) );
		buf.append( parseParam( doc, 6 ) );
		buf.append( parseParam( valor, 12, 2 ) );*/

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 15 );
	}

	public int abreComprovanteNFiscalVinculado( final String formaPag, final float valor, final int doc ) {

		final char CCMD = (char) 213;
		byte[] CMD = { ESC, (byte) CCMD };

		final StringBuffer buf = new StringBuffer();
		buf.append( programaFormaPagamento( formaPag ) );
		buf.append( parseParam( doc, 6 ) );
		buf.append( parseParam( valor, 12, 2 ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 15 );
	}

	public int usaComprovanteNFiscalVinculado( final String texto ) {
		
		final char CCMD = (char) 215;
		byte[] CMD = { ESC, (byte) CCMD };
		byte[] CMDT = null;
	
		final char[] str = texto.toCharArray();
		String tmp = "";
		int ret = 0;
		int index = 0;
		
		for ( int i=0; i < str.length; i++ ) {
			if ( index++ == 47 || i == str.length || str[ i ] == 10 ) {
				CMDT = adicBytes( CMD, parseParam( tmp, 48 ).getBytes() );
				ret = executaCmd( CMDT, 7 );
				if ( EStatus.RETORNO_OK != decodeReturnECF( ret ) ) {
					return ret;
				}
				tmp = "";
				index = 0;
			}			
			if( str[ i ] != 10 ) {
				tmp += String.valueOf( str[ i ] );
			}
		}

		return ret;
	}

	public int autenticacaoDeDocumento() {

		return -1;
	}

	public int programaCaracterParaAutenticacao( final int[] caracteres ) {
		
		return -1;
	}

	public int acionaGavetaDinheiro( final int time ) {

		return -1;
	}

	public String retornoEstadoGavetaDinheiro() {

		return "-1";
	}

	/**
	 * Atrav�s deste comando � poss�vel verificar o estado atual da impressora.<br>
	 * 
	 * @see org.freedom.ecf.driver.EStatus
	 * @return estado da impressora.<br>
	 */
	public List<EStatus> getStatus() {
		
		final List<EStatus> status = new ArrayList<EStatus>();	
		final byte[] CMD = { GS, ENQ };
		executaCmd( CMD, 14, false );
		
		final String tmp = new String( getBytesLidos() );
		final char[] chs = tmp.toCharArray();
		final int value[][] = new int[16][4];
		
		value[0] = new int[] { 0, 0, 0, 0 };
		value[1] = new int[] { 0, 0, 0, 1 };
		value[2] = new int[] { 0, 0, 1, 0 };
		value[3] = new int[] { 0, 0, 1, 1 };
		value[4] = new int[] { 0, 1, 0, 0 };
		value[5] = new int[] { 0, 1, 0, 1 };
		value[6] = new int[] { 0, 1, 1, 0 };
		value[7] = new int[] { 0, 1, 1, 1 };
		value[8] = new int[] { 1, 0, 0, 0 };
		value[9] = new int[] { 1, 0, 0, 1 };
		value[10] = new int[] { 1, 0, 1, 0 };
		value[11] = new int[] { 1, 0, 1, 1 };
		value[12] = new int[] { 1, 1, 0, 0 };
		value[13] = new int[] { 1, 1, 0, 1 };
		value[14] = new int[] { 1, 1, 1, 0 };
		value[15] = new int[] { 1, 1, 1, 1 };		

		int index = 1;
		
		final Map<String, EStatus> ms = new HashMap<String, EStatus>();
		
		for ( EStatus s : EStatus.values() ) {
			if ( s.getCode() > 2000 ) {
				ms.put( String.valueOf(s.getCode()), s );
			}
		}
		
		String key = "";
		
		for ( char ch : chs ) {
			if ( index != 8 && index <= 10 ) {
				for( int i=3; i >= 0 ; i--) {
					key = "";
					key += 2;
					key += index;
					key += i;
					key += value[Integer.parseInt( String.valueOf( ch ), 16 )][i];
					if ( ms.get( key ) != null ) {
						status.add( ms.get( key ) );
					}
				}
			}
			index++;
		}

		return status;
	}

	public String retornoAliquotas() {

		return "-1";
	}
	
	public Map<String, String> retornoFormasDePagamento() {

		final char CCMD = (char) 234;
		final byte[] CMD = { ESC, (byte) CCMD };
		
		executaCmd( CMD, 1431 );

		Map<String, String> returnAction = new HashMap<String, String>();
		final String[] keys = { 
				"01","02","03","04","05","06","07","08",
				"09","10","11","12","12","14","15","16",
				"51","52","53","54","55","56","57","58",
				"59","60","61","62","62","64","65","66" };
		final String returnCommand = new String( getBytesLidos() );
		int offset = 714;
		if ( returnCommand != null && returnCommand.length() > 714 ) {
			for ( int i=0; i < 32; i++ ) {
				returnAction.put( keys[ i ], returnCommand.substring( offset+1, offset+=22 ) );
			}			
		}
		else {
			return null;
		}
		
		return returnAction;
	}

	public String retornoTotalizadoresParciais() {

		return "-1";
	}

	public String retornoSubTotal() {

		final char CCMD = (char) 205;
		final byte[] CMD = { ESC, (byte) CCMD };
		
		executaCmd( CMD, 19 );
		
		return new String( getBytesLidos() );
	}

	/**
	 * Este comando poder� ser utilizado logo ap�s a abertura de um Cupom Fiscal, assim recebendo<br>
	 * o seu n�mero, ou ap�s qualquer Cupom fechado.<br>
	 * 
	 * @return n�mero do cupom<br>
	 */
	public String retornoNumeroCupom() {

		final char CCMD = (char) 239;
		final byte[] CMD = { ESC, (byte) CCMD };
		
		executaCmd( CMD, 227 );

		final String str = new String( getBytesLidos() );
		String retorno = "99999998";
				
		if ( str != null && str.length() >=16 ) {
			retorno =  str.substring( 2, 8 );
		}
		
		return retorno;
	}
	
	public boolean retornoDocumentoAberto() {		
		
		final char CCMD = (char) 235;
		final byte[] CMD = { ESC, (byte) CCMD };
		
		executaCmd( CMD, 65 );
		
		final char[] chr = new String( getBytesLidos() ).toCharArray();
		
		if ( chr != null && chr.length >=3 ) {
			if ( '1' == chr[2] ) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Retorna a imforma��o da impressora comforme o parametro especificado.<br>
	 * 
	 * <table border=1>
	 * <tr>
	 * <td><b>paramtro<b></td>
	 * <td><b>vari�vel<b></td>
	 * <td><b>tamanho<b></td>
	 * </tr>
	 * <tr>
	 * <td>0</td>
	 * <td>n�mero de serie</td>
	 * <td>15 caracteres ASCII</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>Vers�o do FIRMWARE</td>
	 * <td>2 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>CNPJ/IE</td>
	 * <td>33 caracteres ASCII</td>
	 * </tr>
	 * <tr>
	 * <td>3</td>
	 * <td>Grande Total</td>
	 * <td>9 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>4</td>
	 * <td>Cancelamentos</td>
	 * <td>7 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>5</td>
	 * <td>Descontos</td>
	 * <td>7 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>6</td>
	 * <td>Contador Seq��ncial</td>
	 * <td>3 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>7</td>
	 * <td>N�mero de Opera��es N�o Fiscais</td>
	 * <td>3 bytes</td>
	 * <tr>
	 * <tr>
	 * <td>8</td>
	 * <td>N�mero de Cupons Cancelados</td>
	 * <td>2 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>9</td>
	 * <td>N�mero de Redu��es</td>
	 * <td>2 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>10</td>
	 * <td>N�mero de Interven��es T�cnicas</td>
	 * <td>2 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>11</td>
	 * <td>N�mero de Interven��es T�cnicas</td>
	 * <td>2 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>12</td>
	 * <td>N�mero do �ltimo Item Vendido</td>
	 * <td>2 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>13</td>
	 * <td>Cliche do Propriet�rio</td>
	 * <td>186 caracteres ASCII</td>
	 * </tr>
	 * <tr>
	 * <td>14</td>
	 * <td>N�mero do Caixa</td>
	 * <td>2 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>15</td>
	 * <td>N�mero da Loja</td>
	 * <td>2 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>16</td>
	 * <td>Moeda</td>
	 * <td>2 caracteres ASCII</td>
	 * </tr>
	 * <tr>
	 * <td>17</td>
	 * <td>FLAGS FISCAIS</td>
	 * <td>1 byte</td>
	 * </tr>
	 * <tr>
	 * <td>18</td>
	 * <td>Minutos Ligada</td>
	 * <td>2 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>19</td>
	 * <td>Minutos Imprimindo</td>
	 * <td>2 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>20</td>
	 * <td>FLAG de Interven��o T�cnica</td>
	 * <td>1 byte</td>
	 * </tr>
	 * <tr>
	 * <td>21</td>
	 * <td>FLAG de EPROM Conectada</td>
	 * <td>1 byte</td>
	 * </tr>
	 * <tr>
	 * <td>22</td>
	 * <td>Valor Pago no �ltimo Cupom</td>
	 * <td>7 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>23</td>
	 * <td>Data e Hora Atual(DDMMAAHHMMSS)</td>
	 * <td>6 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>24</td>
	 * <td>Contadores dos Totalizadores N�o Sujeitos ao ICMS</td>
	 * <td>9 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>25</td>
	 * <td>Descri��o dos Totalizadores Nao Sujeitos ao ICMS</td>
	 * <td>9 totalizadores com 19 caracteres</td>
	 * </tr>
	 * <tr>
	 * <td>26</td>
	 * <td>Data da �ltima Redu��o (DDMMAA)</td>
	 * <td>3 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>27</td>
	 * <td>Data do Movimento (DDMMAA)</td>
	 * <td>3 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>28</td>
	 * <td>FLAG de Truncamento</td>
	 * <td>1 byte</td>
	 * </tr>
	 * <tr>
	 * <td>29</td>
	 * <td>FLAG de Vincula��o ao ISS</td>
	 * <td>2 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>30</td>
	 * <td>Totalizador de Acr�scimo</td>
	 * <td>7 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>31</td>
	 * <td>Contador de Bilhete de Passagem</td>
	 * <td>3 bytes</td>
	 * </tr>
	 * <tr>
	 * <td>32</td>
	 * <td>Formas de Pagamento</td>
	 * <td></td>
	 * </tr>
	 * </table> <br>
	 * Para mais informa��es consulte a documenta��o da impressora.<br>
	 * <br>
	 * 
	 * @param var
	 *            indica��o da informa��o.<br>
	 * 
	 * @return informa��o da impressora.<br>
	 */
	public String retornoVariaveis( final char var ) {

		final byte[] CMD = { ESC, 35, (byte) var };
		/*
		 * o tamanho dos bytes de retorno varia conforme o parametro.
		 */
		executaCmd( CMD, 0 );

		String retorno = "";

		if ( var == V_NUM_SERIE || var == V_CNPJ_IE || var == V_CLICHE || var == V_MOEDA || var == V_DEPARTAMENTOS ) {
			retorno = new String( getBytesLidos() );
		} else {
			retorno = bcdToAsc( getBytesLidos() );
		}

		return retorno;
	}

	/**
	 * Este comando s� ter� efeito quando a impressora indicar �Pouco Papel.<br>
	 * A impressora retorna ACK n1 n2 ST1 ST2. Onde n1+(n2*256) � o n�mero de linhas impressas na condi��o de �Pouco Papel.<br>
	 * 
	 * @return estado do papel<br>
	 */
	public String retornoEstadoPapel() {

		final byte[] CMD = { ESC, 62, 54 };

		executaCmd( CMD, 5 );

		return bcdToAsc( getBytesLidos() );
	}

	/**
	 * Leitura dos Dados da �ltima Redu��o.<br>
	 * 
	 * @return �ltima redu��o<br>
	 */
	public String retornoUltimaReducao() {

		final byte[] CMD = { ESC, 62, 55 };

		executaCmd( CMD, 308 );

		return bcdToAsc( getBytesLidos() );
	}

	/**
	 * Com este comando pode-se programar a moeda no singular.<br>
	 * Este comando possui o par�metro com a descri��o da moeda no tamanho de 19 bytes.<br>
	 * 
	 * @param nomeSingular
	 *            descri��o.<br>
	 * 
	 * @return estado da impressora.<br>
	 */
	public int programaMoedaSingular( final String nomeSingular ) {

		byte[] CMD = { ESC, 58 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( nomeSingular, 19, false ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );
	}

	/**
	 * Com este comando pode-se programar a moeda no plural.<br>
	 * Este comando possui o par�metro com a descri��o da moeda no tamanho de 22 bytes.<br>
	 * 
	 * @param nomePlurar
	 *            descri��o.<br>
	 * 
	 * @return estado da impressora.<br>
	 */
	public int programaMoedaPlural( final String nomePlurar ) {

		byte[] CMD = { ESC, 59 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( nomePlurar, 19, false ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		return executaCmd( CMD, 3 );
	}

	/**
	 * Este comando retorna pela porta serial 1 byte correspondendo ao estado atual de<br>
	 * inser��o ou n�o do cheque.<br>
	 * 
	 * @return estado da impressora.<br>
	 */
	public String retornoStatusCheque() {

		byte[] CMD = { ESC, 62, 48 };

		executaCmd( CMD, 3 );

		return bcdToAsc( getBytesLidos() );
	}

	/**
	 * <br>
	 * 
	 * @return estado da impressora.<br>
	 */
	public int cancelaImpressaoCheque() {

		byte[] CMD = { ESC, 62, 49 };

		return executaCmd( CMD, 3 );
	}

	/**
	 * Fun��o para impress�o de cheques.<br>
	 * <br>
	 * 
	 * @param valor
	 *            valor do cheque.
	 * @param favorecido
	 *            favorecido pelo cheque.
	 * @param localidade
	 *            pra�a do cheque
	 * @param dia
	 *            dia para composi��o da data.
	 * @param mes
	 *            m�s para composi��o da data.
	 * @param ano
	 *            ano para composi��o da data.
	 * 
	 * @return estado da impressora.<br>
	 */
	public int imprimeCheque( final float valor, final String favorecido, final String localidade, final int dia, final int mes, final int ano ) {

		byte[] CMD = { ESC, 57 };

		final StringBuffer buf = new StringBuffer();

		buf.append( parseParam( valor, 14, 2 ) );
		buf.append( parseParam( favorecido, 45, false ) );
		buf.append( parseParam( localidade, 27, false ) );
		buf.append( parseParam( dia, 2 ) );
		buf.append( parseParam( mes, 2 ) );
		buf.append( parseParam( ano, 4 ) );

		CMD = adicBytes( CMD, buf.toString().getBytes() );

		final byte[] posicoes = { 55, 10, 1, 6, 18, 50, 54, 71, 2, 5, 8, 10, 12, 0 };

		CMD = adicBytes( CMD, posicoes );

		return executaCmd( CMD, 3 );
	}

	public void aguardaImpressao() {

		byte[] CMD = { ESC, 19 };
		byte[] retorno = new byte[ 1 ];
		CMD = preparaCmd( CMD );

		while ( retorno.length < 2 ) {

			// depois que entra do la�o e ocorre algum erro no envio do comando
			// a condi��o de retorno == null valida o la�o
			// tornando ele um la�o infinito...

			retorno = enviaCmd( CMD, 3 );

			try {
				Thread.sleep( 100 );
			} catch ( InterruptedException e ) {
			}
		}
	}

	public int habilitaCupomAdicional( final char opt ) {

		byte[] CMD = { ESC, 68 };

		CMD = adicBytes( CMD, parseParam( opt, 1 ).getBytes() );

		return executaCmd( CMD, 3 );
	}

	public int resetErro() {

		final byte[] CMD = { ESC, 70 };

		return executaCmd( CMD, 3 );
	}
}
