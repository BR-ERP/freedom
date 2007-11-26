package org.freedom.ecf.app;

import static org.freedom.ecf.app.EParametro.PARAM_ACRECIMO;
import static org.freedom.ecf.app.EParametro.PARAM_ALIQUOTA;
import static org.freedom.ecf.app.EParametro.PARAM_CODIGO;
import static org.freedom.ecf.app.EParametro.PARAM_DEPARTAMENTO;
import static org.freedom.ecf.app.EParametro.PARAM_DESCONTO;
import static org.freedom.ecf.app.EParametro.PARAM_DESCRICAO;
import static org.freedom.ecf.app.EParametro.PARAM_QUANTIDADE;
import static org.freedom.ecf.app.EParametro.PARAM_UNIDADE_MEDIDA;
import static org.freedom.ecf.app.EParametro.PARAM_VALOR_UNITARIO;
import static org.freedom.ecf.app.EParametro.PARAM_FORMA_PAGAMENTO;
import static org.freedom.ecf.driver.EStatus.RETORNO_OK;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.freedom.ecf.com.Serial;
import org.freedom.ecf.driver.AbstractECFDriver;
import org.freedom.infra.components.LoggerManager;

public class Control {
		
	private boolean modoDemostracao;	
	
	private AbstractECFDriver ecf;
	
	private List<String> aliquotas;
	
	private String messageLog;
	
	private Logger logger;
	
	
	/**
	 * Contrutor de classe.<br>
	 * Invoca o construtor Control( String, int, boolean )<br>
	 * com a porta default Serial.COM1 e o estado de modo demostra��o false.<br>
	 * 
	 * @see org.freedom.ecf.com.Serial#COM1
	 * @param ecfdriver
	 *            nome do driver de comunica��o serial.
	 * @throws IllegalArgumentException
	 *             caso o nome do driver seja invalido.
	 * @throws NullPointerException
	 *             caso o driver n�o consiga ser inst�nciado.
	 */
	public Control( final String ecfdriver ) 
		throws IllegalArgumentException, NullPointerException {

		this( ecfdriver, Serial.COM1, false );
	}

	/**
	 * Contrutor de classe.<br>
	 * Invoca o construtor Control( String, int, boolean )<br>
	 * com a porta default Serial.COM1<br>
	 * 
	 * @see org.freedom.ecf.com.Serial#COM1
	 * @param ecfdriver
	 *            nome do driver de comunica��o serial.
	 * @param mododemostracao
	 *            estado de modo demostra��o.
	 * @throws IllegalArgumentException
	 *             caso o nome do driver seja invalido.
	 * @throws NullPointerException
	 *             caso o driver n�o consiga ser inst�nciado.
	 */
	public Control( final String ecfdriver, final boolean mododemostracao ) 
		throws IllegalArgumentException, NullPointerException {

		this( ecfdriver, Serial.COM1, mododemostracao );
	}

	/**
	 * Contrutor de classe.<br>
	 * Invoca o construtor Control( String, int, boolean )<br>
	 * com o estado de modo demostra��o false.<br>
	 * 
	 * @param ecfdriver
	 *            nome do driver de comunica��o serial.
	 * @param porta
	 *            porta serial.
	 * @throws IllegalArgumentException
	 *             caso o nome do driver seja invalido.
	 * @throws NullPointerException
	 *             caso o driver n�o consiga ser inst�nciado.
	 */
	public Control( final String ecfdriver, final int porta ) 
		throws IllegalArgumentException, NullPointerException {

		this( ecfdriver, porta, false );
	}

	/**
	 * Contrutor de classe.<br>
	 * Invoca o construtor Control( String, int, boolean )<br>
	 * com o estado de modo demostra��o false e convertendo o nome da porta para a constante indicativa.<br>
	 * 
	 * @see org.freedom.ecf.com.Serial#convPorta(int)
	 * @param ecfdriver
	 *            nome do driver de comunica��o serial.
	 * @param porta
	 *            nome da porta serial.
	 * @throws IllegalArgumentException
	 *             caso o nome do driver seja invalido.
	 * @throws NullPointerException
	 *             caso o driver n�o consiga ser inst�nciado.
	 */
	public Control( final String ecfdriver, final String porta ) 
		throws IllegalArgumentException, NullPointerException {

		this( ecfdriver, Serial.convPorta( porta ), false );
	}

	/**
	 * Contrutor de classe.<br>
	 * Invoca o construtor Control( String, int, boolean )<br>
	 * convertendo o nome da porta para a constante indicativa.<br>
	 * 
	 * @see org.freedom.ecf.com.Serial#convPorta(int)
	 * @param ecfdriver
	 *            nome do driver de comunica��o serial.
	 * @param porta
	 *            nome da porta serial.
	 * @param mododemostracao
	 *            estado de modo demostra��o.
	 * @throws IllegalArgumentException
	 *             caso o nome do driver seja invalido.
	 * @throws NullPointerException
	 *             caso o driver n�o consiga ser inst�nciado.
	 */
	public Control( final String ecfdriver, final String porta, final boolean mododemostracao ) 
		throws IllegalArgumentException, NullPointerException {

		this( ecfdriver, Serial.convPorta( porta ), mododemostracao );
	}

	/**
	 * Contrutor de classe.<br>
	 * Valida o nome do driver de comunica��o serial e instancia a classe com este nome<br>
	 * abrindo a porta serial e definindo o estado de modo demonstra��o.<br>
	 * 
	 * @param ecfdriver
	 *            nome do driver de comunica��o serial.
	 * @param porta
	 *            porta serial
	 * @param mododemostracao
	 *            estado de modo demostra��o.
	 * @throws IllegalArgumentException
	 *             caso o nome do driver seja invalido.
	 * @throws NullPointerException
	 *             caso o driver n�o consiga ser inst�nciado.
	 */
	public Control( final String ecfdriver, final int porta, final boolean mododemostracao ) 
		throws IllegalArgumentException, NullPointerException {

		if ( ecfdriver == null || ecfdriver.trim().length() == 0 ) {
			throw new IllegalArgumentException( "Driver de impressora fiscal invalido." );
		}

		try {
			Object obj = Class.forName( ecfdriver ).newInstance();
			if ( obj instanceof AbstractECFDriver ) {
				this.ecf = (AbstractECFDriver) obj;
				this.ecf.ativaPorta( porta > 0 ? porta : Serial.COM1 );
			}
		} catch ( ClassNotFoundException e ) {
			e.printStackTrace();
		} catch ( InstantiationException e ) {
			e.printStackTrace();
		} catch ( IllegalAccessException e ) {
			e.printStackTrace();
		}

		if ( this.ecf == null ) {
			throw new NullPointerException( 
					"N�o foi poss�vel carregar o driver da impressora.\n" 
					+ ecfdriver );
		}

		setModoDemostracao( mododemostracao );
		
		try {
			logger = LoggerManager.getLogger( "log/freedom-ecf.log" );
		} catch ( RuntimeException e ) {
			e.printStackTrace();
		}
	}
	
	public void setModoDemostracao( final boolean modoDemostracao ) {
		this.modoDemostracao = modoDemostracao;
	}
	
	public boolean isModoDemostracao() {
		return this.modoDemostracao;
	}
	
	public boolean notIsModoDemostracao() {
		return ! isModoDemostracao();
	}
	
	private void setAliquotas() {
		this.aliquotas = getAllAliquotas();
	}
	
	public List<String> getAliquotas() {
		
		List<String> returnlist = null;
		
		if ( this.aliquotas != null ) {
			returnlist = new ArrayList<String>();
			Collections.copy( this.aliquotas, returnlist );
		}
		
		return returnlist;
	}
	
	public void setMessageLog( final String message ) {
		this.messageLog = message;
	}
	
	public String getMessageLog() {
		return this.messageLog;
	}

	public boolean decodeReturn( final int arg ) {

		boolean returnOfAction = true;

		setMessageLog( RETORNO_OK.getMessage() );
		String str = ecf.decodeReturnECF( arg ).getMessage();

		if ( ! RETORNO_OK.getMessage().equals( str ) ) {
			returnOfAction = false;
			setMessageLog( str );
		}

		return returnOfAction;
	}
	
	// Comandos ...
	
	public boolean programaAliquota( BigDecimal aliquota, final char tipoaliquota ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {	
			aliquota = aliquota.setScale( 2, BigDecimal.ROUND_HALF_UP );
			String sAliquota = ecf.parseParam( aliquota.floatValue(), 4, 2 );
			returnOfAction = decodeReturn( ecf.adicaoDeAliquotaTriburaria( sAliquota, tipoaliquota ) );
			if ( ! returnOfAction ) {
				whiterLogError( "[PROGRAMA ALIQUOTA] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean nomeiaDepartamento( final int index, final String departamento ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {	
			returnOfAction = decodeReturn( ecf.nomeiaDepartamento( index, departamento ) );
			if ( ! returnOfAction ) {
				whiterLogError( "[NOMEIA DEPARTAMENTO] " );
			}
		}
		
		return returnOfAction;
	}
	
	public String programaFormaPagamento( final String formapagamento ) {

		String returnOfAction = null;
		
		if ( notIsModoDemostracao() ) {	
			returnOfAction = ecf.programaFormaPagamento( formapagamento );
			if ( returnOfAction == null ) {
				setMessageLog( "Forma de pagamento n�o encontrada/programada." );
				whiterLogError( "[FORMA DE PAGAMENTO] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean leituraX() {
		
		return leituraX( false );
	}
	
	public boolean leituraX( final boolean saidaSerial ) {
		
		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {	
			returnOfAction = 
				saidaSerial ? decodeReturn( ecf.leituraXSerial() ) : decodeReturn( ecf.leituraX() );
			if ( ! returnOfAction ) {
				whiterLogError( "[LEITURA X] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean suprimento( final BigDecimal valor ) {
		
		return suprimento( valor, "Dinheiro" );
	}
	
	public boolean suprimento( final BigDecimal valor, String formaDePagamento ) { 

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {	
			if ( valor != null && valor.floatValue() > 0f ) {
				BigDecimal valorsuprimento = valor.setScale( 2, BigDecimal.ROUND_HALF_UP );
				if ( formaDePagamento == null || formaDePagamento.trim().length() == 0 ) {
					formaDePagamento = "Dinheiro";
				}
				returnOfAction = decodeReturn( 
						ecf.comprovanteNFiscalNVinculado( 
								AbstractECFDriver.SUPRIMENTO, 
								valorsuprimento.floatValue(), 
								formaDePagamento ) );
			}
			if ( !returnOfAction ) {
				whiterLogError( "[SUPRIMENTO] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean abreCupom() {
		
		return abreCupom( null );
	}
	
	public boolean abreCupom( final String cnpj_cpf ) {
		
		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {
			returnOfAction = cnpj_cpf != null && cnpj_cpf.trim().length() > 0 
					? decodeReturn( ecf.aberturaDeCupom( cnpj_cpf ) ) 
							: decodeReturn( ecf.aberturaDeCupom() );
			if ( !returnOfAction ) {
				whiterLogError( "[ABERTURA DE CUPOM] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean unidadeMedida( final String unidade ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {
			if ( unidade != null && unidade.trim().length() > 0 ) {
				returnOfAction = decodeReturn( ecf.programaUnidadeMedida( unidade ) );
				if ( !returnOfAction ) {
					whiterLogError( "[UNIDADE DE MEDIDA] " );
				}
			} 
			else {
				returnOfAction = false;
				setMessageLog( "unidade de medida inv�lida." );
				whiterLogError( "[UNIDADE DE MEDIDA] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean aumetaDescricaoItem( final String descricao ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {
			if ( descricao != null && descricao.trim().length() > 0 ) {
				returnOfAction = decodeReturn( ecf.aumentaDescItem( tiraAcentos( descricao ) ) );
				if ( !returnOfAction ) {
					whiterLogError( "[AUMENTA DESCRI��O] " );
				}
			} 
			else {
				returnOfAction = false;
				setMessageLog( "descri��o do item inv�lida." );
				whiterLogError( "[AUMENTA DESCRI��O] " );
			}
		}
		
		return returnOfAction;
	}
	
	private boolean validaParamVenda( final EParametro arg0, final Object arg1, final char arg2 ) {
		
		boolean actionOK = true;

		if ( arg0 == null  ) {
			actionOK = false;
		}
		else if ( arg1 == null ) {
			setMessageLog( arg0.getName() + " inv�lido.[" + arg1.toString() +"]" );
			actionOK = false;
		}
		else {
			
			switch ( arg0 ) {
    
    			case PARAM_CODIGO: {
    				if ( ((String)arg1).trim().length() == 0 ) {
    					setMessageLog( "C�digo do produto inv�lido.[" + arg1 +"]" );
    					actionOK = false;
    				}
    				break;
    			}
    			case PARAM_DESCRICAO: {
    				if ( ((String)arg1).trim().length() == 0 ) {
        				setMessageLog( "Descri��o do item inv�lida.[" + arg1 +"]" );
        				actionOK = false;
    				}
    				break;
    			}
    			case PARAM_ALIQUOTA: {
    				if ( ((BigDecimal)arg1).floatValue() <= 0.0f
    						&& ((BigDecimal)arg1).floatValue() > 99.994f ) {
        				setMessageLog( "Aliquota inv�lida.[" + arg1 +"]" );
        				actionOK = false;
    				}
    				break;
    			}
    			case PARAM_TIPO_QUANTIDADE: {
    				break;
    			}
    			case PARAM_QUANTIDADE: {
    				if (((BigDecimal)arg1).floatValue() <= 0.0f 
    						|| ( arg2 == AbstractECFDriver.QTD_INTEIRO 
    								&& ((BigDecimal)arg1).floatValue() > 9999f )
    						|| ( arg2 == AbstractECFDriver.QTD_DECIMAL 
    								&& ((BigDecimal)arg1).floatValue() > 9999.9994f ) ) {
    					setMessageLog( "Quantidade inv�lida.[" + arg1 +"]" );
    					actionOK = false; 
    				}
    				break;
    			}
    			case PARAM_CASAS_DECIMAIS: {
    				break;
    			}
    			case PARAM_VALOR_UNITARIO: {
    				if (((BigDecimal)arg1).floatValue() <= 0.0f
    						|| ((BigDecimal)arg1).floatValue() > 999999.994f ) {
    				setMessageLog( "Valor inv�lido.[" + arg1 +"]" );
    				actionOK = false;
    			}
    				break;
    			}
    			case PARAM_TIPO_DESCONTO: {
    				break;
    			}
    			case PARAM_DESCONTO: {
    				if ( ((BigDecimal)arg1).floatValue() < 0.0f
    						|| ( arg2 == AbstractECFDriver.DESCONTO_PERC 
    								&& ((BigDecimal)arg1).floatValue() > 99.994f )
    						|| ( arg2 == AbstractECFDriver.DESCONTO_VALOR
    								&& ((BigDecimal)arg1).floatValue() > 9999.9994f ) ) {
    					setMessageLog( "Desconto inv�lido.[" + arg1 +"]" );
    					actionOK = false;
    				}
    				break;
    			}
    			case PARAM_ACRECIMO: {
    				if ( ((BigDecimal)arg1).floatValue() < 0.0f 
    						|| ( arg2 == AbstractECFDriver.ACRECIMO_PERC
    								&& ((BigDecimal)arg1).floatValue() > 99.994f )
    						|| ( arg2 == AbstractECFDriver.ACRECIMO_VALOR
    								&& ((BigDecimal)arg1).floatValue() > 9999.9994f ) ) {
    					setMessageLog( "Acr�cimo inv�lido.[" + arg1 +"]" );
    					actionOK = false;
    				}
    				break;
    			}
    			case PARAM_DEPARTAMENTO: {
    				if ( (Integer)arg1 <= 0 && (Integer)arg1 > 20 ) {
        				setMessageLog( "Departamento inv�lido.[" + arg1 +"]" );
        				actionOK = false;
    				}
    				break;
    			}
    			case PARAM_UNIDADE_MEDIDA: {
    				if ( ((String)arg1).trim().length() == 0 ) {
        				setMessageLog( "Unidade de medida inv�lida.[" + arg1 +"]" );
        				actionOK = false;
    				}
    				break;
    			}
    			case PARAM_FORMA_PAGAMENTO: {
    				if ( ((String)arg1).trim().length() == 0 ) {
        				setMessageLog( "Forma de pagamento inv�lida.[" + arg1 +"]" );
        				actionOK = false;
    				}
    				break;
    			}
			}			
		}
		
		return actionOK;
	}
	
	public boolean vendaItem ( 
			final String codigo, final String descricao, final BigDecimal aliquota, 
			final BigDecimal quantidade, final BigDecimal valor ) {
		
		return vendaItem( 
				codigo, descricao, aliquota, 
				quantidade, valor, new BigDecimal( "0" ) );
	}
	
	public boolean vendaItem (
			final String codigo, final String descricao, final BigDecimal aliquota, 
			final BigDecimal quantidade, final BigDecimal valor, final BigDecimal desconto ) {
		
		return vendaItem( 
				codigo, descricao, aliquota,  
				AbstractECFDriver.QTD_DECIMAL, quantidade, 
				AbstractECFDriver.DUAS_CASAS_DECIMAIS, valor, 
				AbstractECFDriver.DESCONTO_VALOR, desconto );
	}
	
	public boolean vendaItem( 
			final String codigo, 
			final String descricao, 
			BigDecimal aliquota, 
			final char tipoQuantidade,
			BigDecimal quantidade, 
			final int casasDecimais,
			BigDecimal valor, 
			final char tipoDesconto,
			BigDecimal desconto ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {
			boolean actionOK = true;
			if ( ! validaParamVenda( PARAM_CODIGO, codigo, '0' ) ) {
				actionOK = false;
			}
			else if ( ! validaParamVenda( PARAM_DESCRICAO, descricao, '0' ) ) {
    			actionOK = false;
    		}
			else if ( ! validaParamVenda( PARAM_ALIQUOTA, aliquota, '0' ) ) {
    			actionOK = false;
    		}
			else if ( ! validaParamVenda( PARAM_QUANTIDADE, quantidade, tipoQuantidade ) ) {
    			actionOK = false;
    		}
			else if ( ! validaParamVenda( PARAM_VALOR_UNITARIO, valor, '0' ) ) {
    			actionOK = false;
    		}
			else if ( ! validaParamVenda( PARAM_DESCONTO, desconto, tipoDesconto ) ) {
    			actionOK = false;
    		}
			
			if ( actionOK ) {
				String strAliquota = getIndexAliquota( aliquota.floatValue() );
				quantidade = tipoQuantidade == AbstractECFDriver.QTD_INTEIRO 
								? new BigDecimal( quantidade.intValue() )
										: quantidade.setScale( 3, BigDecimal.ROUND_HALF_UP ) ;
				desconto = desconto.setScale( 2, BigDecimal.ROUND_HALF_UP );
				int countWhite = 0;
				if ( casasDecimais == 3 ) {
					valor = valor.setScale( 3, BigDecimal.ROUND_HALF_UP );
					while ( ! decodeReturn( ecf.vendaItemTresCasas( 
							codigo, 
							tiraAcentos( descricao ), 
							strAliquota, 
							tipoQuantidade,
							quantidade.floatValue(), 
							valor.floatValue(),
							tipoDesconto,
							desconto.floatValue() ) ) ) {	
						
						try {
							Thread.sleep( 2000 );
						} catch ( InterruptedException e ) {
							whiterLogError( e );
						}
						
						if ( (countWhite++) >= 5 ) {
							returnOfAction = false;
							break;
						}
					}
				}
				else {
					valor = valor.setScale( 2, BigDecimal.ROUND_HALF_UP );
					while ( ! decodeReturn( ecf.vendaItem( 
							codigo, 
							tiraAcentos( descricao ), 
							strAliquota, 
							tipoQuantidade,
							quantidade.floatValue(), 
							valor.floatValue(),
							tipoDesconto,
							desconto.floatValue() ) ) ) {	
						
						try {
							Thread.sleep( 2000 );
						} catch ( InterruptedException e ) {
							whiterLogError( e );
						}
						
						if ( (countWhite++) >= 5 ) {
							returnOfAction = false;
							break;
						}
					}
				}
			}
			else {
				returnOfAction = false;
			}
			
			if ( !returnOfAction ) {
				whiterLogError( "[VENDA DE ITEM] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean vendaItemDepartamento(
			 final String codigo, 
			 final String descricao, 
			 final BigDecimal aliquota, 
			 BigDecimal valor, 
			 BigDecimal quantidade, 
			 final BigDecimal acrescimo, 
			 BigDecimal desconto, 
			 final int departamento, 
			 final String unidadeMedida ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {	
			boolean actionOK = true;
			if ( ! validaParamVenda( PARAM_CODIGO, codigo, '0' ) ) {
				actionOK = false;
			}
			else if ( ! validaParamVenda( PARAM_DESCRICAO, descricao, '0' ) ) {
    			actionOK = false;
    		}
			else if ( ! validaParamVenda( PARAM_ALIQUOTA, aliquota, '0' ) ) {
    			actionOK = false;
    		}
			else if ( ! validaParamVenda( PARAM_VALOR_UNITARIO, valor, '0' ) ) {
    			actionOK = false;
    		}
			else if ( ! validaParamVenda( PARAM_QUANTIDADE, quantidade, AbstractECFDriver.QTD_DECIMAL ) ) {
    			actionOK = false;
    		}
			else if ( ! validaParamVenda( PARAM_ACRECIMO, acrescimo, AbstractECFDriver.ACRECIMO_VALOR ) ) {
    			actionOK = false;
    		}
			else if ( ! validaParamVenda( PARAM_DESCONTO, desconto, AbstractECFDriver.DESCONTO_VALOR ) ) {
    			actionOK = false;
    		}
			else if ( ! validaParamVenda( PARAM_DEPARTAMENTO, departamento, '0' ) ) {
    			actionOK = false;
    		}
			else if ( ! validaParamVenda( PARAM_UNIDADE_MEDIDA, unidadeMedida, '0' ) ) {
    			actionOK = false;
    		}
			
			if ( actionOK ) {
				String strAliquota = getIndexAliquota( aliquota.floatValue() );
				quantidade = quantidade.setScale( 3, BigDecimal.ROUND_HALF_UP ) ;
				desconto = desconto.setScale( 2, BigDecimal.ROUND_HALF_UP );
				int countWhite = 0;
				valor = valor.setScale( 3, BigDecimal.ROUND_HALF_UP );
				while ( ! decodeReturn( ecf.vendaItemDepartamento( 
						strAliquota,
						valor.floatValue(),
						quantidade.floatValue(),
						desconto.floatValue(),
						acrescimo.floatValue(),
						departamento,
						unidadeMedida,							
						codigo,
						descricao ) ) ) {	
					
					try {
						Thread.sleep( 2000 );
					} catch ( InterruptedException e ) {
						whiterLogError( e );
					}
					
					if ( (countWhite++) >= 5 ) {
						returnOfAction = false;
						break;
					}
				}
			}
			else {
				returnOfAction = false;
			}
			if ( !returnOfAction ) {
				whiterLogError( "[VENDA DEPARTAMENTO] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean cancelaItem() {
		
		return cancelaItem( 0 );
	}
	
	public boolean cancelaItem( final int item ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {	
			returnOfAction = decodeReturn( 
					item == 0 ?
						ecf.cancelaItemAnterior() : ecf.cancelaItemGenerico( item ) );
			if ( !returnOfAction ) {
				whiterLogError( "[CANCELAMENTO DE ITEM] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean iniciaFechamentoCupom( final char opcao, BigDecimal percentual ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {	
			boolean actionOK = true;
			if ( opcao == AbstractECFDriver.ACRECIMO_PERC
					|| opcao == AbstractECFDriver.ACRECIMO_VALOR ) {
				if ( ! validaParamVenda( PARAM_ACRECIMO, percentual, opcao ) ) {
	    			actionOK = false;
	    		}
			}
			else if ( opcao == AbstractECFDriver.DESCONTO_PERC
					|| opcao == AbstractECFDriver.DESCONTO_VALOR ) {
				if ( ! validaParamVenda( PARAM_DESCONTO, percentual, opcao ) ) {
	    			actionOK = false;
	    		}
			}
			else {
				actionOK = false;
				setMessageLog( "Op��o indefinida." );
			}
			if ( actionOK ) {
				percentual = percentual.setScale( 2, BigDecimal.ROUND_HALF_UP );
				returnOfAction = decodeReturn( ecf.iniciaFechamentoCupom( opcao, percentual.floatValue() ) );				
			}
			if ( !returnOfAction ) {
				whiterLogError( "[INICIA FECHAMENTO DE CUPOM] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean efetuaFormaPagamento( 
			final String formaPagamento, BigDecimal valor, final String descricaoAuxiliar ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {	
			boolean actionOK = true;
			if ( ! validaParamVenda( PARAM_FORMA_PAGAMENTO, formaPagamento, '0' ) ) {
    			actionOK = false;
			}
			else if ( ! validaParamVenda( PARAM_VALOR_UNITARIO, valor, '0' ) ) {
    			actionOK = false;
			}
			if ( actionOK ) {
				String sIndice = "Dinheiro".equals( formaPagamento.trim() ) 
									? "01" : programaFormaPagamento( formaPagamento );
				valor = valor.setScale( 2, BigDecimal.ROUND_HALF_UP );
				returnOfAction = decodeReturn( 
						ecf.efetuaFormaPagamento( sIndice, valor.floatValue(), descricaoAuxiliar ) );				
			}
			if ( !returnOfAction ) {
				whiterLogError( "[EFETUA FORMA DE PAGAMENTO] " );
			}
		}
		
		return returnOfAction;
	}
	
	public boolean finalizaFechamentoCupom( final String mensagem ) {

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {	
			returnOfAction = decodeReturn( ecf.finalizaFechamentoCupom( mensagem ) );
			if ( !returnOfAction ) {
				whiterLogError( "[FINALIZA FECHAMENTO DE CUPOM] " );
			}
		}
		
		return returnOfAction;
	}

	
	public boolean sangria( final BigDecimal valor ) {
		
		return sangria( valor, "Dinheiro" );
	}
	
	public boolean sangria( final BigDecimal valor, String formaDePagamento ) { 

		boolean returnOfAction = true;
		
		if ( notIsModoDemostracao() ) {	
			if ( valor != null && valor.floatValue() > 0f ) {
				BigDecimal valorsangria = valor.setScale( 2, BigDecimal.ROUND_HALF_UP );
				if ( formaDePagamento == null || formaDePagamento.trim().length() == 0 ) {
					formaDePagamento = "Dinheiro";
				}
				returnOfAction = decodeReturn( 
						ecf.comprovanteNFiscalNVinculado( 
								AbstractECFDriver.SANGRIA, 
								valorsangria.floatValue(), 
								formaDePagamento ) );
			}
			if ( !returnOfAction ) {
				whiterLogError( "[SANGRIA] " );
			}
		}
		
		return returnOfAction;
	}
	
	public List<String> getAllAliquotas() {

		List<String> returnOfAction = new ArrayList<String>();
		
		if ( notIsModoDemostracao() ) {	
			String sAliquotas = ( ecf.retornoAliquotas() ).trim();
			int tamanho = 0;
			if ( sAliquotas != null && sAliquotas.trim().length() > 2 ) {
				tamanho = Integer.parseInt( sAliquotas.trim().substring( 0, 2 ) );				
			}			
			String tmp = sAliquotas.trim().substring( 2 );
			for ( int i=0; i < tamanho; i++ ) {				
				returnOfAction.add( tmp.substring( i * 4, ( i * 4 ) + 4 ) );				
			}
		}
		
		return returnOfAction;
	}
	
	public String getIndexAliquota( final float arg ) {
		
		String indexAliquota = null;
		
		if ( arg > 0.0f && arg < 99.99f ) {
			if ( this.aliquotas == null ) {
				setAliquotas();
			}
			String tmp = ecf.floatToString( arg, 4, 2 );
			int index = 1;
			for ( String s : this.aliquotas ) {
				if ( s.equals( tmp ) ) {
					indexAliquota = ecf.strZero( String.valueOf( index ), 2 );
					break;
				}
				index++;
			}
		}
		
		return indexAliquota;
	}

	/**
	 * Realiza a leitura da porta serial para capturar os dados retornados na mesma.<br>
	 * 
	 * @return leitura da porta serial.
	 */
	public String readSerialPort() {

		String strReturn = null;

		if ( notIsModoDemostracao() ) {
			strReturn = new String( ecf.getBytesLidos() );
		}

		return strReturn;
	}
	
	private void whiterLogError( final Throwable e ) {
		
		if ( logger != null ) {
			logger.error( getMessageLog(), e );
		}
	}
	
	private void whiterLogError( final String actionName ) {
		
		if ( logger != null ) {
			logger.error( actionName + getMessageLog() );
		}
	}
	
	// fun��es que devem ser esternas.

	public String tiraAcentos( String sTexto ) {

		String sRet = "";
		char cVals[] = sTexto.toCharArray();
		for ( int i = 0; i < cVals.length; i++ ) {
			cVals[ i ] = tiraAcento( cVals[ i ] );
		}
		sRet = new String( cVals );
		
		return sRet;
	}

	public char tiraAcento( char cKey ) {

		char cTmp = cKey;

		if ( contido( cTmp, "����" ) ) {
			cTmp = 'a';
		}
		else if ( contido( cTmp, "����" ) ) {
			cTmp = 'A';
		}
		else if ( contido( cTmp, "���" ) ) {
			cTmp = 'e';
		}
		else if ( contido( cTmp, "���" ) ) {
			cTmp = 'E';
		}
		else if ( contido( cTmp, "���" ) ) {
			cTmp = 'i';
		}
		else if ( contido( cTmp, "���" ) ) {
			cTmp = 'I';
		}
		else if ( contido( cTmp, "����" ) ) {
			cTmp = 'o';
		}
		else if ( contido( cTmp, "����" ) ) {
			cTmp = 'O';
		}
		else if ( contido( cTmp, "����" ) ) {
			cTmp = 'u';
		}
		else if ( contido( cTmp, "����" ) ) {
			cTmp = 'U';
		}
		else if ( contido( cTmp, "�" ) ) {
			cTmp = 'c';
		}
		else if ( contido( cTmp, "�" ) ) {
			cTmp = 'C';
		}
		
		return cTmp;
	}

	public boolean contido( char cTexto, String sTexto ) {

		boolean bRetorno = false;
		
		for ( int i = 0; i < sTexto.length(); i++ ) {
			if ( cTexto == sTexto.charAt( i ) ) {
				bRetorno = true;
				break;
			}
		}
		
		return bRetorno;
	}
}
