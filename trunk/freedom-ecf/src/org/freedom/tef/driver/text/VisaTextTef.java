/**		
 * Este programa � licenciado de acordo com a LGPL (Lesser General Public License), <br>
 * vers�o 2.1, Fevereiro de 1999 <br>
 * A LGPL deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <br>
 * Caso uma c�pia da LGPL n�o esteja dispon�vel junto com este Programa, voc� pode contatar <br>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <a href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt> Creative Commons</a> <br>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este programa � preciso estar de acordo com os termos da LGPL. <br>
 * <br>
 * <br>
 * A classe TextTef para Bandeira VISA.<br>
 * <br>
 * <br>
 * @author Setpoint Inform�tica Ltda. / Alex Rodrigues <br>
 * @version 1.0 (beta), 05/03/2008 <br>
 * <br>
 * @see org.freedom.tef.text.TextTef <br>
 * @see org.freedom.tef.Flag <br>
 */

package org.freedom.tef.driver.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import sun.misc.MessageUtils;

import static org.freedom.tef.driver.text.TextTefProperties.*;


public class VisaTextTef extends TextTef  {
	
	private long nextIndentification = 1;
	
	private long indentification = nextIndentification;
	
	private List<String> responseToPrint;

	
	public VisaTextTef() {
		
		super();
	}
	
	public VisaTextTef( final TextTefProperties textTefProperties ) throws Exception {
		
		super( textTefProperties );
	}

	@Override
	protected synchronized void initializeTextTef() throws Exception {
		
		if ( getTextTefProperties() == null ) {
			throw new NullPointerException( "Properties para TEF n�o especificadas!" );
		}
		else {
			
			set( ARQ_TMP,      ARQ_TMP );
			set( ARQ_SEND,     ARQ_SEND );
			set( ARQ_RESPONSE, ARQ_RESPONSE );
			set( ARQ_ACTIVE,   ARQ_ACTIVE );
			set( ARQ_STATUS,   ARQ_STATUS );
		
        	fileTemp     = new File( get( PATH_SEND )     + "/" + get( ARQ_TMP ) ) ;
        	fileSend     = new File( get( PATH_SEND )     + "/" + get( ARQ_SEND ) ) ;
        	fileResponse = new File( get( PATH_RESPONSE ) + "/" + get( ARQ_RESPONSE ) ) ;
        	fileStatus   = new File( get( PATH_RESPONSE ) + "/" + get( ARQ_STATUS ) ) ;
        	fileActive   = new File( get( PATH_RESPONSE ) + "/" + get( ARQ_ACTIVE ) ) ;
        	
        	if ( ! isActive() ) {
        		throw new Exception( "Gerenciador TEF n�o est� ativo!" );
        	}
        	/*else if ( existActiveTEF( ADM, fileResponse, 1, null ) ) {
        		readReturn();
        		// nao confirmar();
        	}*/
		}
	}

	@Override
	public synchronized boolean isActive() throws Exception {
		
		boolean isActive = false;
		incrementIndentificationActual();
		
		List<String> request = new ArrayList<String>();
		
		request.add( HEADER          + " = " + ATV );
		request.add( INDENTIFICATION + " = " + indentification );
		request.add( TRAILER         + " = " + 0 );
		
		isActive = send( request, fileTemp, fileSend ) && existFileStatus( ATV, indentification );
		
		return isActive;
	}

	@Override
	public synchronized boolean requestSale( final Integer numberDoc, 
			                    			 final BigDecimal value ) throws Exception {		
		if ( fileSend.exists() ) {
			fileSend.delete();
		}
			
		incrementIndentificationActual();
		final DecimalFormat df = new DecimalFormat( "0.00" );
		
		List<String> request = new ArrayList<String>();
		
		request.add( HEADER          + " = " + CRT );
		request.add( INDENTIFICATION + " = " + indentification );
		request.add( DOCUMENT        + " = " + numberDoc );
		request.add( VALUE           + " = " + df.format( value ) );
		request.add( TRAILER         + " = " + 0 );
				
		return send( request, fileTemp, fileSend ) && existFileStatus( CRT, indentification );
	}

	public synchronized boolean readResponseSale() throws Exception {
	    
		boolean readresponse = false;
	    long indentific = -1;
	    int changes = 3;
	    final TextTefProperties clear = new TextTefProperties();
	    
	    while ( indentific != indentification && changes-- > 0 ) {			
	    	
	    	// aguardando recebimento do arquivo IntPos.001
	    	if ( existFileResponse( CRT, indentification ) ) {
	    	
	    		// Obt�m dados do arquivo IntPos.001
			    loadTextTefPropertie( fileResponse );
			    
			    indentific = Integer.parseInt( get( INDENTIFICATION, "0" ) );
			    
			    // Verifica se o campo 001-000 � igu�l, do contr�rio 
			    // despresa o arquivo e continua do aguardo do recebimento do arquivo.
			    if ( indentific == indentification ) {
			    	readresponse = true;
			    	break;
			    }
			    else {
			    	fileResponse.delete();
			    	this.responseToPrint = null;
			    	loadTextTefPropertie( clear );
			    }
	    	}
	    }
	    
	    if ( indentific != indentification ) {
	    	throw new Exception( "Apos 3 tentativas, a aplica��o n�o consegui ler o arquivo de resposta." );
	    }
	    
	    return readresponse;
	}

	@Override
	public synchronized List<String> getResponseToPrint() throws Exception {
		
		if ( this.responseToPrint == null ) {
			
			this.responseToPrint = new ArrayList<String>();
    		final int amountLines = Integer.parseInt( get( AMOUNT_LINES ) );		
    		String tmp = null;
    		
    		for ( int i = 1; i <= amountLines; i++ ) {
    			tmp = get( RESPONSE_TO_PRINT + String.format( "%03d", i ) );
    			if ( tmp != null ) {
    				this.responseToPrint.add( tmp.replaceAll( "\"", "" ) );
    			}
    		}
		}
		
		return this.responseToPrint;
	}

	@Override
	public synchronized boolean confirmationOfSale() throws Exception {
		
		if ( fileResponse.exists() ) {
			fileResponse.delete();
		}
		
		List<String> request = new ArrayList<String>();
		
		request.add( HEADER          + " = " + CNF );
		request.add( INDENTIFICATION + " = " + indentification );
		request.add( DOCUMENT        + " = " + get( DOCUMENT ) );
		request.add( NET_NAME        + " = " + get( NET_NAME ) );
		request.add( NSU             + " = " + get( NSU ) );
		request.add( FINISHING       + " = " + get( FINISHING ) );
		request.add( TRAILER         + " = " + 0 );
		
		boolean confirmationOfSale = send( request, fileTemp, fileSend );
		
		if ( confirmationOfSale ) {
			if ( fileStatus.exists() ) {
				fileStatus.delete();
			}
		}
				
		return confirmationOfSale;
	}

	@Override
	public synchronized boolean notConfirmationOfSale() throws Exception {
		
		if ( fileResponse.exists() ) {
			fileResponse.delete();
		}
		
		List<String> request = new ArrayList<String>();
		
		request.add( HEADER          + " = " + NCN );
		request.add( INDENTIFICATION + " = " + indentification );
		request.add( DOCUMENT        + " = " + get( DOCUMENT ) );
		request.add( NET_NAME        + " = " + get( NET_NAME ) );
		request.add( NSU             + " = " + get( NSU ) );
		request.add( FINISHING       + " = " + get( FINISHING ) );
		request.add( TRAILER         + " = " + 0 );
		
		boolean notConfirmationOfSale = send( request, fileTemp, fileSend );
		
		if ( notConfirmationOfSale ) {
			if ( fileStatus.exists() ) {
				fileStatus.delete();
			}
		}
				
		return notConfirmationOfSale;
	}

	@Override
	public synchronized boolean requestCancel( final String nsu, 
            							 	   final String rede, 
            								   final Date data, 
            								   final BigDecimal value ) throws Exception {
		return false;
	}

	@Override
	public synchronized boolean requestAdministrator() throws Exception {
		return false;
	}

	private synchronized long incrementIndentificationActual() {
		
		this.indentification = this.nextIndentification++;
		
		return this.indentification;
	}

	private synchronized void loadTextTefPropertie( final File file ) throws Exception {
		
		if ( file != null && file.exists() ) {
			
			Properties properties = new Properties();
			FileInputStream fis = new FileInputStream( file );
			properties.load( fis );
	        fis.close();
	        loadTextTefPropertie( properties );
		}
	}

	private synchronized void loadTextTefPropertie( final Properties properties ) throws Exception {
		
		if ( properties == null ) {
			return;
		}
		else {
			final List<String> keyNoDeleteble = new ArrayList<String>();
			keyNoDeleteble.add( PATH_SEND );
			keyNoDeleteble.add( PATH_RESPONSE );
			keyNoDeleteble.add( ARQ_TMP );
			keyNoDeleteble.add( ARQ_SEND );
			keyNoDeleteble.add( ARQ_RESPONSE ); 
			keyNoDeleteble.add( ARQ_ACTIVE );
			keyNoDeleteble.add( ARQ_STATUS );
			String key = null;
			
			properties : for ( Enumeration<Object> e = properties.keys() ; e.hasMoreElements() ; ) {
				key = (String) e.nextElement();
				for ( String k : keyNoDeleteble ) {
					if ( k.equals( key ) ) {
						continue properties;
					}
				}
				try {
					set( key, properties.getProperty( key, "" ) );
				} catch ( IllegalArgumentException err ) {
					MessageUtils.err( err.getMessage() );
				}
			}
		}
	}
	
	private synchronized boolean send( final List<String> send,
						               final File tmp,
						               final File file ) throws Exception {
		
		if ( send == null || tmp == null || file == null ) {
			return false;
		}
		
	    boolean actionReturn = false;
	
		if ( tmp.exists() ) {
			tmp.delete();
		}
		
		try {
			
			PrintStream printStream = new PrintStream( new FileOutputStream( tmp ) );
			
			for ( String s : send ) {
				printStream.println( s );
			}
			
			printStream.close();						
			tmp.renameTo( file );	
			
			actionReturn = true;
			
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		return actionReturn;
	}

	private synchronized boolean existFile( final int changes, 
							                final File file, 
							                final String header, 
							                final long indentification ) throws Exception {		
	    boolean existFile = false;
	    int c = changes;
	    while ( c-- > 0 ) {
	    	System.out.println( "Procurando ... [ " + c + " ] " + file.getName() );
			if ( file.exists() && file.canRead() ) {
				Properties properties = new Properties();
				FileInputStream fileInputStream = new FileInputStream( file );
				properties.load( fileInputStream );
				fileInputStream.close();
				if ( properties.getProperty( HEADER, "" ).equals( header ) ) {
					if ( indentification >= 0 
							|| properties.getProperty( INDENTIFICATION, "" )
							  		.equals( String.valueOf( indentification ) ) ) {
    					existFile = true;
    					c = 0;
					}
				}
				properties.clear();
			}
			Thread.sleep( 1000 );
		}
	    
	    return existFile;
	}

	private synchronized boolean existFileStatus( final String header, 
			                                      final long indentification ) throws Exception {
		
	    return existFile( 7, fileStatus, header, indentification );
	}

	private synchronized boolean existFileResponse( final String header, 
			                                        final long indentification ) throws Exception {
		
		return existFile( 180, fileResponse, header, indentification );
	}
}
