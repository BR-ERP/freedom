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
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.freedom.infra.components.LoggerManager;
import org.freedom.tef.driver.text.TextTef;
import org.freedom.tef.driver.text.TextTefFactore;
import org.freedom.tef.driver.text.TextTefProperties;

public class ControllerTef {
	
	public static final int TEF_TEXT = 0;
	
	public static final int TEF_DEDICATED = 1;
	
	private File fileParametrosOfInitiation;
	
	private TextTefProperties defaultTextTefProperties;
	
	private int typeTef = TEF_TEXT; // default TEF text.
	
	private Logger logger;
	
	private List<ControllerTefListener> controllerTefListeners;
	
	
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
	
	public void initializeControllerTef( final TextTefProperties textTefProperties, 
	                                     final File fileParametrosOfInitiation,
	                                     final int typeTef ) throws Exception {
		
		setDefaultTextTefProperties( textTefProperties );
		setFileParametrosOfInitiation( fileParametrosOfInitiation );
		setTypeTef( typeTef );
	}

	private TextTefProperties getTextTefProperties() {	
		
		final TextTefProperties textTefProperties = new TextTefProperties();
		
		textTefProperties.set( TextTefProperties.PATH_SEND, 
                               getDefaultTextTefProperties().get( TextTefProperties.PATH_SEND ) );
		textTefProperties.set( TextTefProperties.PATH_RESPONSE, 
                               getDefaultTextTefProperties().get( TextTefProperties.PATH_RESPONSE ) );
		
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
			throw new NullPointerException( "Parametros de inicializa��o n�o especificados!" );
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
	
	public ControllerTefListener addControllerMessageListener( final ControllerTefListener listener ) {
		
		if ( listener == null ) {
			return listener;
		}
		
		if ( this.controllerTefListeners == null ) {
			this.controllerTefListeners = new ArrayList<ControllerTefListener>();
		}
		
		this.controllerTefListeners.add( listener );
	
		return listener;
	}
	
	public void removeControllerMessageListeners( final ControllerTefListener listener ) {
		
		if ( listener == null || this.controllerTefListeners == null ) {
			return;
		}
	
		this.controllerTefListeners.remove( listener );
	}
	
	private boolean fireControllerTefEvent( final String message, 
			                                final int option ) {			
		boolean tefMessage = false;
		
		ControllerTefEvent controllerTefEvent = new ControllerTefEvent( this, option, message );
		
		for ( ControllerTefListener listener : this.controllerTefListeners ) {
			tefMessage = listener.actionTef( controllerTefEvent );
			if ( ! tefMessage ) {
				break;
			}
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
	 * Este m�todo faz a requisi��o de pagamento para o gerenciador padr�o TEF<br>
	 * atr�ves da cria��o do arquivo com os parametros da requisi��o e<br>
	 * verifica o recebimento da requisi��o atr�ves da leitura do arquivo de retorno<br>
	 * e transmite a mensagem caso haja para ControllerMessageListener com ControllerMessageEvent.<br>
	 * <br>
	 * Ap�s a execu��o deste � necess�ria a execu��o da impress�o do comprovante.
	 * 
	 * @param numberDoc
	 * @param value
	 * @param flagName
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
	
	private boolean requestSaleTextTef( final Integer numberDoc,
                                        final BigDecimal value,
                                        final String flagName ) {

		boolean actionReturn = false;
		
		try {
			TextTef textTef = TextTefFactore.createTextTef( getTextTefProperties(), 
															flagName, 
															getFileParametrosOfInitiation() );	
			// verifica se est� ativo e faz a requisi��o e
			// verifica se houve resposta.
			if ( textTef.isActive() 
					&& textTef.requestSale( numberDoc, value ) 
						&& textTef.readResponseSale() ) {
					
				String messageOperator = textTef.get( TextTefProperties.MESSAGE_OPERATOR, "" );
				
				// avisa para os ouvintes a menssagem do campo 030-000.
				if ( messageOperator.trim().length() > 0 ) {
					fireControllerTefEvent( messageOperator, ControllerTefEvent.WARNING );
				}
				
				// invoca m�todo para lan�ar eventos de impress�o do comprovante.
				if ( voucherTextTef( textTef ) ) {
					textTef.confirmationOfSale();
				}
				
				actionReturn = true;
			}
			else {
				fireControllerTefEvent( "TEF n�o est� ativo!", ControllerTefEvent.END_PRINT );
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
			String etmp = "Erro ao solicitar venda:\n" + e.getMessage();
			fireControllerTefEvent( etmp, ControllerTefEvent.END_PRINT );
			whiterLogError( etmp );
		}
		
		return actionReturn;
	}
	
	private boolean voucherTextTef( final TextTef textTef ) throws Exception {

		boolean voucherTextTef = false;
		
		if ( textTef != null ) {
		
    		int amountLines = Integer.parseInt( textTef.get( TextTefProperties.AMOUNT_LINES, "0" ) );
    		
    		if ( amountLines > 0 ) {
    			if ( fireControllerTefEvent( null, ControllerTefEvent.BEGIN_PRINT )
    						&& printVoucherTextTef( textTef ) 
    								&& fireControllerTefEvent( null, ControllerTefEvent.END_PRINT ) ) {
    				voucherTextTef = true;
    			}
    		}
		}
		
		return voucherTextTef;
	}
		
	private boolean printVoucherTextTef( final TextTef textTef ) throws Exception {

		boolean actionReturn = false;
			
		List<String> responseToPrint = textTef.getResponseToPrint();
		
		if ( responseToPrint != null && responseToPrint.size() > 0 ) {	
			
			int numberTickets = 1; // alterar para vari�vel parametrizav�l.

			tickets : while ( numberTickets-- > 0 ) {
				for ( String message : responseToPrint ) {
					
					actionReturn = fireControllerTefEvent( message, ControllerTefEvent.PRINT );
					
					if ( ! actionReturn ) {
						if ( fireControllerTefEvent( "Impressora n�o responde, tentar novamente?", 
								                     ControllerTefEvent.CONFIRM ) ) {
							fireControllerTefEvent( null, ControllerTefEvent.RE_PRINT );
							actionReturn = printVoucherTextTef( textTef );
							break tickets;
						}
						else {
							actionReturn = textTef.notConfirmationOfSale();
							break tickets;							
						}
					}
				}
			}
		}
		
		return actionReturn;
	}
}
