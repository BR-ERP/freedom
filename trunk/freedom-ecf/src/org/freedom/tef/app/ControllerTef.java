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
 * @see org.freedom.tef.text.TextTefFactory <br>
 */

package org.freedom.tef.app;

import static org.freedom.tef.driver.text.TextTef.ADM;
import static org.freedom.tef.driver.text.TextTef.CNC;
import static org.freedom.tef.driver.text.TextTef.CRT;
import static org.freedom.tef.driver.text.TextTefProperties.AMOUNT_LINES;
import static org.freedom.tef.driver.text.TextTefProperties.MESSAGE_OPERATOR;
import static org.freedom.tef.driver.text.TextTefProperties.PATH_RESPONSE;
import static org.freedom.tef.driver.text.TextTefProperties.PATH_SEND;

import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.freedom.tef.driver.Flag;
import org.freedom.tef.driver.dedicate.DedicatedTef;
import org.freedom.tef.driver.dedicate.DedicatedTefEvent;
import org.freedom.tef.driver.dedicate.DedicatedTefListener;
import org.freedom.tef.driver.text.TextTef;
import org.freedom.tef.driver.text.TextTefAction;
import org.freedom.tef.driver.text.TextTefFactory;
import org.freedom.tef.driver.text.TextTefProperties;

public class ControllerTef implements DedicatedTefListener {
	
	public static final int TEF_TEXT = 0;
	
	public static final int TEF_DEDICATED = 1;
	
	private static final int VOUCHER_ERROR = -1;

	private static final int VOUCHER_NO = 0;

	private static final int VOUCHER_OK = 1;
	
	private int typeTef = TEF_TEXT; // default TEF text.
	
	private File fileFlagsMap;
	
	private TextTefProperties defaultTextTefProperties;
	
	private int numberOfTickets = 1;
	
	private Logger logger;
	
	private DedicatedTef dedicateTef;
	
	private ControllerTefListener controllerTefListener;
	
	
	public ControllerTef() {		

		super();
		
		try {
//			logger = LoggerManager.getLogger( "log/freedomTEF.log" );  ANDERSON Implementar novo log.
		} catch ( RuntimeException e ) {
			e.printStackTrace();
		}
	}
	
	public ControllerTef( final ControllerTefListener controllerTefListener ) {		
		this();		
		setControllerMessageListener( controllerTefListener );
	}
	
	public ControllerTef( final int typeTef ) {		
		this( typeTef, null );
	}
	
	public ControllerTef( final int typeTef,
	   		  			  final ControllerTefListener controllerTefListener ) {		
		this();		
		setTypeTef( typeTef );
		setControllerMessageListener( controllerTefListener );
	}
	
	public static ControllerTef getControllerTextTef( final TextTefProperties defaultTextTefProperties, 
			              					   		  final File fileParametrosOfInitiation,
			              					   		  final int typeTef ) throws Exception {
		return getControllerTextTef( defaultTextTefProperties, 
									 fileParametrosOfInitiation, 
									 typeTef, 
									 null );
	}
	
	public static ControllerTef getControllerTextTef( final TextTefProperties defaultTextTefProperties, 
			              					   		  final File fileParametrosOfInitiation,
			              					   		  final int typeTef,
			              					   		  final ControllerTefListener controllerTefListener ) throws Exception {		
		final ControllerTef controllerTef = new ControllerTef( TEF_TEXT );		
		controllerTef.initializeControllerTextTef( defaultTextTefProperties, 
												   fileParametrosOfInitiation, 
												   typeTef );
		controllerTef.setControllerMessageListener( controllerTefListener );
		return controllerTef;
	}	
	
	public static ControllerTef getControllerDedicatedTef( final ControllerTefListener controllerTefListener ) throws Exception {
		final ControllerTef controllerTef = new ControllerTef( TEF_DEDICATED );	
		controllerTef.setControllerMessageListener( controllerTefListener );
		controllerTef.dedicateTef = DedicatedTef.getInstance( controllerTef );
		return controllerTef;
	}
	
	public static ControllerTef getControllerDedicatedTef( final String file,
														   final ControllerTefListener controllerTefListener ) throws Exception {
		return getControllerDedicatedTef( new File( file ), controllerTefListener );
	}
	
	public static ControllerTef getControllerDedicatedTef( final File file,
														   final ControllerTefListener controllerTefListener ) throws Exception {
		final ControllerTef controllerTef = new ControllerTef( TEF_DEDICATED );	
		controllerTef.setControllerMessageListener( controllerTefListener );
		if ( file == null || !file.exists() ) {
			controllerTef.dedicateTef = DedicatedTef.getInstance( controllerTef );
		}
		else {
			controllerTef.dedicateTef = DedicatedTef.getInstance( file, controllerTef );
		}		
		return controllerTef;
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
	public void initializeControllerTextTef( final TextTefProperties textTefProperties, 
	                                     	 final File fileParametrosOfInitiation,
	                                     	 final int typeTef ) throws Exception {
		
		setDefaultTextTefProperties( textTefProperties );
		setFileFlagsMap( fileParametrosOfInitiation );
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
	
	public File getFileFlagsMap() {	
		return fileFlagsMap;
	}
	
	public void setFileFlagsMap( final File fileFlagsMap ) {	
		
		if ( fileFlagsMap == null ) {
			throw new NullPointerException( "Arquivo de parametros de inicializa��o inv�lido!" );
		}
		
		this.fileFlagsMap = fileFlagsMap;
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
	
	private boolean fireControllerTefEvent( final TefAction option ) {
		return fireControllerTefEvent( option, null );
	}
	
	private boolean fireControllerTefEvent( final TefAction option, 
			                                final String message ) {			
		boolean tefMessage = false;
		
		if ( this.controllerTefListener != null ) {
			tefMessage = this.controllerTefListener.actionTef( 
												new ControllerTefEvent( this, option, message ) );
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
	 * Verifica se o gerenciador padr�o de TEF est� ativo.<br>
	 * 
	 * @return	Verdadeiro para gerenciador padr�o ativo.
	 * 
	 * @throws 	Exception
	 */
	public boolean standardManagerActive() throws Exception {
		
		if ( TEF_TEXT == getTypeTef() ) {			
			Flag.loadParametrosOfInitiation( getFileFlagsMap() );
			Object[] flags = Flag.getTextTefFlagsMap().keySet().toArray();		
			TextTef textTef = null;		
			
			for ( Object flagName : flags ) {
				textTef = TextTefFactory.createTextTef( getTextTefProperties(), (String)flagName );
				if ( textTef != null ) {
					break; 
				}
			}
			return standardManagerActive( textTef );
		}
		else {
			return false;
		}		
	}

	/**
	 * Este m�todo faz a requisi��o de pagamento para o gerenciador padr�o TEF. <br>
	 * 
	 * @param numberDoc		N�mero do documento.
	 * @param value			Valor da pagamento.
	 * @param arg			Nome da bandeira(Text) / Nome do operador(Dedicated).
	 * 
	 * @return verdadeiro para envio correto da requisi��o e recebimento correto do retorno. 
	 */
	public boolean requestSale( final Integer numberDoc,
			                    final BigDecimal value,
			                    final String arg ) {
		
		boolean actionReturn = false;
		
		if ( TEF_TEXT == getTypeTef() ) {
			actionReturn = requestSaleTextTef( numberDoc, value, arg );
		}
		else if ( TEF_DEDICATED == getTypeTef() ) {
			actionReturn = requestSaleDedicatedTef( numberDoc, value, arg );
		}
		
		return actionReturn;
	}

	/**
	 * Este m�todo faz a requisi��o das fun��es administrativas de TEF. <br>
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
	 * Este m�todo faz a requisi��o de cancelamento. <br>
	 * 
	 * @param rede			Rede da transa��o
	 * @param nsu			NSU da transa��o.
	 * @param date			Data da transa��o
	 * @param hour			Hora da transa��o.
	 * @param value			Valor da transa��o.
	 * @param flagName		Nome da bandeira.
	 * 
	 * @return verdadeiro para envio correto da requisi��o e recebimento correto do retorno. 
	 */
	public boolean requestCancel( final String rede,
                                  final String nsu,
                                  final Date date,
                                  final String hour,
                                  final BigDecimal value,
                                  final String flagName ) {
		
		boolean actionReturn = false;
		
		if ( TEF_TEXT == getTypeTef() ) {
			actionReturn = requestCancelTextTef( rede, nsu, date, hour, value, flagName );
		}
		
		return actionReturn;
	}	
	
	// ************************************************************************** \\
	// ***                          INICIO  TEF  TEXTO                        *** \\	
	// ************************************************************************** \\
	
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
		
		boolean active = false;
		
		if ( TEF_TEXT == getTypeTef() && textTef != null ) {
			active = textTef.standardManagerActive();
			if ( ! active ) {
				fireControllerTefEvent( TextTefAction.ERROR, "TEF n�o est� ativo!" );
			}
		}
		
		return active;
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
			TextTef textTef = TextTefFactory.createTextTef( getTextTefProperties(), 
															flagName, 
															getFileFlagsMap() );	
			if ( ! standardManagerActive( textTef ) ) {
				return actionReturn;
			}
						
			if ( textTef.requestSale( numberDoc, value ) 
					&& textTef.readResponse( CRT ) ) {
					
				String messageOperator = textTef.get( MESSAGE_OPERATOR, "" );
				
				// avisa para os ouvintes a menssagem do campo 030-000.
				if ( messageOperator.trim().length() > 0 ) {
					fireControllerTefEvent( TextTefAction.WARNING, messageOperator );
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
			fireControllerTefEvent( TextTefAction.ERROR, etmp );
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
			TextTef textTef = TextTefFactory.createTextTef( getTextTefProperties(), 
															flagName, 
															getFileFlagsMap() );	
			if ( ! standardManagerActive( textTef ) ) {
				return actionReturn;
			}
						
			if ( textTef.requestAdministrator() 
					&& textTef.readResponse( ADM ) ) {
					
				String messageOperator = textTef.get( MESSAGE_OPERATOR, "" );
				
				// avisa para o ouvinte a menssagem do campo 030-000.
				if ( messageOperator.trim().length() > 0 ) {
					fireControllerTefEvent( TextTefAction.WARNING, messageOperator );
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
			fireControllerTefEvent( TextTefAction.ERROR, etmp );
			whiterLogError( etmp );
		}
		
		return actionReturn;
	}
	
	/**
	 */
	private boolean requestCancelTextTef( final String rede,
                                          final String nsu,
                                          final Date date,
                                          final String hour,
                                          final BigDecimal value,
                                          final String flagName ) {
		
		boolean actionReturn = false;
		
		try {
			TextTef textTef = TextTefFactory.createTextTef( getTextTefProperties(), 
															flagName, 
															getFileFlagsMap() );	
			if ( ! standardManagerActive( textTef ) ) {
				return actionReturn;
			}
						
			
			if ( textTef.requestCancel( rede, nsu, date, hour, value ) && textTef.readResponse( CNC ) ) {
					
				String messageOperator = textTef.get( MESSAGE_OPERATOR, "" );
				
				// avisa para o ouvinte a menssagem do campo 030-000.
				if ( messageOperator.trim().length() > 0 ) {
					fireControllerTefEvent( TextTefAction.WARNING, messageOperator );
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
			String etmp = "Erro ao acionar CNC:\n" + e.getMessage();
			fireControllerTefEvent( TextTefAction.ERROR, etmp );
			whiterLogError( etmp );
		}
		
		return actionReturn;
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
			fireControllerTefEvent( TextTefAction.WARNING, "Transa��o n�o efetuada. Favor reter o Cupom." );
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
		boolean beginPrint = fireControllerTefEvent( TextTefAction.BEGIN_PRINT );
		while ( !beginPrint ) {
			if ( fireControllerTefEvent( TextTefAction.CONFIRM, "Impressora n�o responde, tentar novamente?" ) ) {
				beginPrint = fireControllerTefEvent( TextTefAction.BEGIN_PRINT );
			} else {
				return false;
			}
		}

		// Testa impress�o at� o sucesso,
		// e tenta a retomado do processo, �pos confirma��o da aplica��o,
		// do contr�rio para o processo.
		boolean printVoucher = printVoucherTextTef( textTef );
		while ( !printVoucher ) {
			if ( fireControllerTefEvent( TextTefAction.CONFIRM, "Impressora n�o responde, tentar novamente?" ) ) {
				if ( fireControllerTefEvent( TextTefAction.RE_PRINT ) ) {
					printVoucher = printVoucherTextTef( textTef );
				}
			} else {
				return false;
			}
		}

		// Testa final impress�o at� o sucesso,
		// e tenta a retomado do processo, �pos confirma��o da aplica��o,
		// do contr�rio para o processo.
		boolean endPrint = fireControllerTefEvent( TextTefAction.END_PRINT );
		while ( !endPrint ) {
			if ( fireControllerTefEvent( TextTefAction.CONFIRM, "Impressora n�o responde, tentar novamente?" ) ) {
				if ( fireControllerTefEvent( TextTefAction.RE_PRINT ) && 
						printVoucherTextTef( textTef ) ) {
					endPrint = fireControllerTefEvent( TextTefAction.END_PRINT );
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
					actionReturn = fireControllerTefEvent( TextTefAction.PRINT, message );
					
					if ( ! actionReturn ) {
						break tickets;	
					}
				}
			}
		}
		
		return actionReturn;
	}

	// ************************************************************************** \\
	// ***                           FIM  TEF  TEXTO                          *** \\	
	// ************************************************************************** \\

	// ************************************************************************** \\
	// ***                        INICIO  TEF  DEDICADO                       *** \\	
	// ************************************************************************** \\
	
	private boolean requestSaleDedicatedTef( final Integer numberDoc,
                                             final BigDecimal value,
                                             final String operator ) {
		boolean actionReturn = false;
		
		try {
			if ( dedicateTef != null ) {
				actionReturn = dedicateTef.requestSale( value, 
														numberDoc, 
														Calendar.getInstance().getTime(), 
														operator );
			}
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return actionReturn;
	}

	public void actionNextCommand() {
		
		if ( TEF_DEDICATED == getTypeTef() && dedicateTef != null ) {
			dedicateTef.actionNextCommand();
		}
	}

	public boolean checkPinPad() {
		
		boolean actionReturn = false;
		
		if ( TEF_DEDICATED == getTypeTef() && dedicateTef != null ) {
			actionReturn = dedicateTef.checkPinPad();
		}
		
		return actionReturn;
	}

	public boolean readCard( final String message ) {
		
		boolean actionReturn = false;
		
		if ( TEF_DEDICATED == getTypeTef() && dedicateTef != null ) {
			actionReturn = dedicateTef.readCard( message );
		}
		
		return actionReturn;
	}

	public boolean readYesNoCard( final String message ) {
		
		boolean actionReturn = false;
		
		if ( TEF_DEDICATED == getTypeTef() && dedicateTef != null ) {
			actionReturn = dedicateTef.readYesNoCard( message );
		}
		
		return actionReturn;
	}

	public boolean actionCommand( DedicatedTefEvent e ) {
		return fireControllerTefEvent( e.getAction(), e.getMessage() );
	}

	// ************************************************************************** \\
	// ***                         FIM   TEF  DEDICADO                        *** \\	
	// ************************************************************************** \\
}
