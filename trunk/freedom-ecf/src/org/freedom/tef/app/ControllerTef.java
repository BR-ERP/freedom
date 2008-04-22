/**		
 * Este programa � licenciado de acordo com a LGPL (Lesser General Public License), <br>
 * vers�o 2.1, Fevereiro de 1999 <br>
 * A LGPL deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <br>
 * Caso uma c�pia da LGPL n�o esteja dispon�vel junto com este Programa, voc� pode contatar <br>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <a href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt> Creative Commons</a> <br>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este programa � preciso estar de acordo com os termos da LGPL. <br>
 * <br>
 * <br>
 * Classe para abstra��o da comunica��o entre aplicativo e fun��es tef.<BR>
 * <br>
 * <br>
 * @author Setpoint Inform�tica Ltda. / Alex Rodrigues <br>
 * @version 1.0 (beta), 05/03/2008 <br>
 * <br>
 * @see org.freedom.tef.text.TextTef <br>
 * @see org.freedom.tef.text.TextTefFactore <br>
 */

package org.freedom.tef.app;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.freedom.infra.components.LoggerManager;
import org.freedom.tef.driver.text.TextTef;
import org.freedom.tef.driver.text.TextTefFactore;
import org.freedom.tef.driver.text.TextTefProperties;

import static org.freedom.tef.driver.text.TextTef.*;
import static org.freedom.tef.driver.text.TextTefProperties.*;
import static org.freedom.tef.app.ControllerTefEvent.*;

public class ControllerTef {
	
	public static final int TEF_TEXT = 0;
	
	public static final int TEF_DEDICATED = 1;
	
	private static final int VOUCHER_ERROR = -1;

	private static final int VOUCHER_NO = 0;

	private static final int VOUCHER_OK = 1;
	
	private File fileParametrosOfInitiation;
	
	private TextTefProperties defaultTextTefProperties;
	
	private int typeTef = TEF_TEXT; // default TEF text.
	
	private int numberOfTickets = 1;
	
	private Logger logger;
	
	private ControllerTefListener controllerTefListener;
	
	
	public ControllerTef() {		

		super();
		
		try {
			logger = LoggerManager.getLogger( "log/freedomTEF.log" );
		} catch ( RuntimeException e ) {
			e.printStackTrace();
		}
	}
	
	public ControllerTef( final TextTefProperties defaultTextTefProperties, 
			              final File fileParametrosOfInitiation,
			              final int typeTef ) throws Exception {
		this();
		
		initializeControllerTef( defaultTextTefProperties, fileParametrosOfInitiation, typeTef );
	}	
	
	/**
	 * Inicializa as propriedades do objeto.
	 * 
	 * @param 	textTefProperties			Lista de propriedades
	 * @param 	fileParametrosOfInitiation	Arquivo de mapeamento de bandeiras.
	 * @param 	typeTef						Tipo de comunica��o ( Texto ou Dedicado )
	 * 
	 * @throws 	Exception
	 */
	public void initializeControllerTef( final TextTefProperties textTefProperties, 
	                                     final File fileParametrosOfInitiation,
	                                     final int typeTef ) throws Exception {
		
		setDefaultTextTefProperties( textTefProperties );
		setFileParametrosOfInitiation( fileParametrosOfInitiation );
		setTypeTef( typeTef );
	}

	private TextTefProperties getTextTefProperties() {	
		
		final TextTefProperties textTefProperties = new TextTefProperties();
		
		textTefProperties.set( PATH_SEND, getDefaultTextTefProperties().get( PATH_SEND ) );
		textTefProperties.set( PATH_RESPONSE, getDefaultTextTefProperties().get( PATH_RESPONSE ) );
		
		return textTefProperties;
	}

	private TextTefProperties getDefaultTextTefProperties() {			
		return this.defaultTextTefProperties;
	}
	
	public void setDefaultTextTefProperties( final TextTefProperties defaultTextTefProperties ) {
	
		if ( defaultTextTefProperties == null ) {
			throw new NullPointerException( "TextTefProperties n�o especificado!" );
		}
		
		this.defaultTextTefProperties = defaultTextTefProperties;
	}
	
	public File getFileParametrosOfInitiation() {	
		return fileParametrosOfInitiation;
	}
	
	public void setFileParametrosOfInitiation( final File fileParametrosOfInitiation ) {	
		
		if ( fileParametrosOfInitiation == null ) {
			throw new NullPointerException( "Arquivo de parametros de inicializa��o inv�lido!" );
		}
		
		this.fileParametrosOfInitiation = fileParametrosOfInitiation;
	}

	public int getTypeTef() {	
		return typeTef;
	}
	
	public void setTypeTef( final int typeTef ) throws IllegalArgumentException {
		
		if ( TEF_TEXT == typeTef || TEF_DEDICATED == typeTef ) {
			this.typeTef = typeTef;
		}
		else {
			throw new IllegalArgumentException( "Tipo de gerenciamento de TEF inv�lido!" );
		}		
	}
	
	public ControllerTefListener setControllerMessageListener( final ControllerTefListener listener ) {
		
		return this.controllerTefListener = listener;
	}
	
	private boolean fireControllerTefEvent( final int option ) {
		return fireControllerTefEvent( option, null );
	}
	
	private boolean fireControllerTefEvent( final int option, 
			                                final String message ) {			
		boolean tefMessage = false;
		
		ControllerTefEvent controllerTefEvent = new ControllerTefEvent( this, option, message );
		
		if ( this.controllerTefListener != null ) {
			tefMessage = this.controllerTefListener.actionTef( controllerTefEvent );
		}
		
		return tefMessage;
	}

	private void whiterLogError( final String message ) {		
		if ( logger != null ) {
			logger.error( message );
		}
	}
	
	// ************************************************************************** \\
	// ***                                                                    *** \\
	// ***                   Implementa��o das fun��es de TEF                 *** \\
	// ***                                                                    *** \\	
	// ************************************************************************** \\
	
	/**
	 * Este m�todo faz a requisi��o de pagamento para o gerenciador padr�o TEF. <br>
	 * 
	 * @param numberDoc		N�mero do documento.
	 * @param value			Valor da pagamento.
	 * @param flagName		Nome da bandeira.
	 * 
	 * @return verdadeiro para envio correto da requisi��o e recebimento correto do retorno. 
	 */
	public boolean requestSale( final Integer numberDoc,
			                    final BigDecimal value,
			                    final String flagName ) {
		
		boolean actionReturn = false;
		
		if ( TEF_TEXT == getTypeTef() ) {
			actionReturn = requestSaleTextTef( numberDoc, value, flagName );
		}
		
		return actionReturn;
	}

	/**
	 * Este m�todo faz a requisi��o das fun��es administrativas do gerenciador padr�o TEF. <br>
	 * 
	 * @param flagName		Nome da bandeira.
	 * 
	 * @return verdadeiro para envio correto da requisi��o e recebimento correto do retorno. 
	 */
	public boolean requestAdministrator( final String flagName ) {
		
		boolean actionReturn = false;
		
		if ( TEF_TEXT == getTypeTef() ) {
			actionReturn = requestAdministratorTextTef( flagName );
		}
		
		return actionReturn;
	}
	
	/**
	 * Efetua a requisi��o de venda para TEF Texto,<br>
	 * atr�ves da cria��o do arquivo com os par�metros da requisi��o e<br>
	 * verifica o recebimento da requisi��o atr�ves da leitura do arquivo de retorno<br>
	 * e transmite a mensagem caso haja para ControllerMessageListener com ControllerMessageEvent.<br>
	 * <br>
	 * 
	 * @see	#requestSale(Integer, BigDecimal, String)
	 * 
	 * @param numberDoc		N�mero do documento.
	 * @param value			Valor da pagamento.
	 * @param flagName		Nome da bandeira.
	 * 
	 * @return verdadeiro para envio correto da requisi��o e recebimento correto do retorno.
	 */
	private boolean requestSaleTextTef( final Integer numberDoc,
                                        final BigDecimal value,
                                        final String flagName ) {
		boolean actionReturn = false;
		
		try {
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), 
															flagName, 
															getFileParametrosOfInitiation() );	
			if ( ! standardManagerActive( textTef ) ) {
				return actionReturn;
			}
						
			if ( textTef.requestSale( numberDoc, value ) 
					&& textTef.readResponse( CRT ) ) {
					
				String messageOperator = textTef.get( MESSAGE_OPERATOR, "" );
				
				// avisa para os ouvintes a menssagem do campo 030-000.
				if ( messageOperator.trim().length() > 0 ) {
					fireControllerTefEvent( WARNING, messageOperator );
				}
				
				// invoca m�todo para lan�ar eventos de impress�o do comprovante.
				if ( voucherTextTef( textTef ) == VOUCHER_OK ) {
					actionReturn = textTef.confirmation();
				}
				else {
					actionReturn = noConfirmation( textTef );					
				}
			}
			
		} catch ( Exception e ) {
			String etmp = "Erro ao solicitar venda:\n" + e.getMessage();
			fireControllerTefEvent( ERROR, etmp );
			whiterLogError( etmp );
		}
		
		return actionReturn;
	}
	
	/**
	 * Efetua a requisi��o das fun��es administrativas do gerenciador padr�o de TEF.<br>
	 * 
	 * @see	#requestAdministrator(String)
	 * 
	 * @param flagName		Nome da bandeira.
	 * 
	 * @return verdadeiro para envio correto da requisi��o e recebimento correto do retorno.
	 */
	private boolean requestAdministratorTextTef( final String flagName ) {
		
		boolean actionReturn = false;
		
		try {
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), 
															flagName, 
															getFileParametrosOfInitiation() );	
			if ( ! standardManagerActive( textTef ) ) {
				return actionReturn;
			}
						
			if ( textTef.requestAdministrator() 
					&& textTef.readResponse( ADM ) ) {
					
				String messageOperator = textTef.get( MESSAGE_OPERATOR, "" );
				
				// avisa para o ouvinte a menssagem do campo 030-000.
				if ( messageOperator.trim().length() > 0 ) {
					fireControllerTefEvent( WARNING, messageOperator );
				}
				
				// invoca m�todo para lan�ar eventos de impress�o do comprovante.
				int voucher = voucherTextTef( textTef );
				if ( voucher == VOUCHER_OK ) {
					actionReturn = textTef.confirmation();
				}
				else if ( voucher == VOUCHER_ERROR ) {
					actionReturn = noConfirmation( textTef );					
				}
			}
			
		} catch ( Exception e ) {
			String etmp = "Erro ao acionar ADM:\n" + e.getMessage();
			fireControllerTefEvent( ERROR, etmp );
			whiterLogError( etmp );
		}
		
		return actionReturn;
	}
	
	/**
	 * Verifica se o gerenciador padr�o de TEF est� ativo,<br>
	 * do contr�rio envia menssagem para ouvinte.<br>
	 * 
	 * @param 	textTef		Objeto de TEF.
	 * 
	 * @return	Verdadeiro para gerenciador padr�o ativo.
	 * 
	 * @throws 	Exception
	 */
	private boolean standardManagerActive( final TextTef textTef ) throws Exception {
		
		boolean active = true;
		
		if ( textTef != null && ! textTef.standardManagerActive() ) {
			active = false;
			fireControllerTefEvent( ERROR, "TEF n�o est� ativo!" );
		}
		
		return active;
	}
	
	/**
	 * Envia comando de n�o confirma��o da transa��o TEF, e envia menssagem da n�o confirma��o para o ouvinte.<br>
	 * O n�o envio do comando de confirma��o ou n�o confirma��o deixa a transa��o em estado pendente.<br>
	 * 
	 * @param 	textTef		Objeto de TEF.
	 * 
	 * @return	Verdadeiro para envio correto da n�o confirma��o.
	 * 
	 * @throws Exception
	 */
	private boolean noConfirmation( final TextTef textTef ) throws Exception {
		
		boolean active = false;
		
		if ( textTef != null ) {
			fireControllerTefEvent( WARNING, "Transa��o n�o efetuada. Favor reter o Cupom." );
			active = textTef.noConfirmation();
		}
		
		return active;
	}
	
	/**
	 * Verifica as condi��es para impress�o do comprovante de TEF.
	 * 
	 * @param textTef	Objeto de TEF.
	 * 
	 * @return 	Indice da situa��o do comprovante:<br>
	 * 			VOUCHER_NO : 	caso n�o ouver menssagem para impress�o ( AMOUNT_LINES == 0 )<br>
	 * 			VOUCHER_OK : 	voucherTextTefAux(TextTef) verdadeiro<br>
	 * 			VOUCHER_ERROR : voucherTextTefAux(TextTef) false<br>
	 * 
	 * @see		#voucherTextTefAux(TextTef)
	 * 
	 * @throws 	Exception
	 */
	private int voucherTextTef( final TextTef textTef ) throws Exception {

		int voucherTextTef = VOUCHER_NO;
		
		if ( textTef != null ) {
    		
        	int amountLines = Integer.parseInt( textTef.get( AMOUNT_LINES, "0" ) );
        		
    		// verifica a quantidade de linhas do comprovante tef para invocar a impress�o.
    		if ( amountLines > 0 ) {
    			voucherTextTef = voucherTextTefAux( textTef ) ? VOUCHER_OK : VOUCHER_ERROR;
    		}
		}    			
		
		return voucherTextTef;
	}
	
	/**
	 * Auxiliar a {@link #voucherTextTef(TextTef)},<br>
	 * tratando da l�gica de acionamento de eventos de impress�o.<br>
	 * 
	 * @param textTef	Objeto TEF.
	 * 
	 * @return 	Verdadeiro para correta impress�o do comprovante,<br>
	 * 			esta condi��o de verdadeira � devolvida, pela aplica��o, atrav�s de eventos.<br>
	 * 
	 * @see		#voucherTextTef(TextTef)
	 * @see		#printVoucherTextTef(TextTef)
	 * @see		#fireControllerTefEvent(int)
	 * @see		#fireControllerTefEvent(int, String)
	 * 
	 * @throws 	Exception
	 */
	private boolean voucherTextTefAux( final TextTef textTef ) throws Exception {
	
		// Testa come�o da impress�o at� o sucesso,
		// e tenta a retomado do processo, �pos confirma��o da aplica��o,
		// do contr�rio para o processo.
		boolean beginPrint = fireControllerTefEvent( BEGIN_PRINT );
		while ( !beginPrint ) {
			if ( fireControllerTefEvent( CONFIRM, "Impressora n�o responde, tentar novamente?" ) ) {
				beginPrint = fireControllerTefEvent( BEGIN_PRINT );
			} else {
				return false;
			}
		}

		// Testa impress�o at� o sucesso,
		// e tenta a retomado do processo, �pos confirma��o da aplica��o,
		// do contr�rio para o processo.
		boolean printVoucher = printVoucherTextTef( textTef );
		while ( !printVoucher ) {
			if ( fireControllerTefEvent( CONFIRM, "Impressora n�o responde, tentar novamente?" ) ) {
				if ( fireControllerTefEvent( RE_PRINT ) ) {
					printVoucher = printVoucherTextTef( textTef );
				}
			} else {
				return false;
			}
		}

		// Testa final impress�o at� o sucesso,
		// e tenta a retomado do processo, �pos confirma��o da aplica��o,
		// do contr�rio para o processo.
		boolean endPrint = fireControllerTefEvent( END_PRINT );
		while ( !endPrint ) {
			if ( fireControllerTefEvent( CONFIRM, "Impressora n�o responde, tentar novamente?" ) ) {
				if ( fireControllerTefEvent( RE_PRINT ) && 
						printVoucherTextTef( textTef ) ) {
					endPrint = fireControllerTefEvent( END_PRINT );
				}
			} else {
				return false;
			}
		}
		
		return true;
	}
		
	/**
	 * Executa o acinamento da impress�o do conteudo do comprovante TEF.
	 * 
	 * @param textTef	Objetc TEF
	 * 
	 * @return 	Verdadeiro para correta impress�o do comprovante,<br>
	 * 			esta condi��o de verdadeira � devolvida, pela aplica��o, atrav�s de eventos.<br>
	 * 
	 * @see		#voucherTextTefAux(TextTef)
	 * @see		#fireControllerTefEvent(int)
	 * @see		#fireControllerTefEvent(int, String)
	 * 
	 * @throws 	Exception
	 */
	private boolean printVoucherTextTef( final TextTef textTef ) throws Exception {

		boolean actionReturn = false;
			
		// recupera a lista de linhas do comprovante tef.
		List<String> responseToPrint = textTef.getResponseToPrint();
		
		if ( responseToPrint != null && responseToPrint.size() > 0 ) {	
			
			int nt = this.numberOfTickets;

			tickets : while ( nt-- > 0 ) {
				for ( String message : responseToPrint ) {
					
					// aciona evento para que, a aplica��o
					// envie o comando de impress�o para impressora fiscal.
					actionReturn = fireControllerTefEvent( PRINT, message );
					
					if ( ! actionReturn ) {
						break tickets;	
					}
				}
			}
		}
		
		return actionReturn;
	}
}
