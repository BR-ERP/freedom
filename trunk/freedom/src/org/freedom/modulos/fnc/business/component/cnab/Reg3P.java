package org.freedom.modulos.fnc.business.component.cnab;

import java.math.BigDecimal;
import java.util.Date;

import org.freedom.library.business.component.Banco;
import org.freedom.library.business.exceptions.ExceptionCnab;
import org.freedom.modulos.fnc.library.business.compoent.FbnUtil.ETipo;

public class Reg3P extends Reg3 {

	private String agencia;

	private String digAgencia;

	private String conta;

	private String digConta;

	private String digAgConta;

	private String identTitulo;

	private int codCarteira;

	private int formaCadTitulo;

	private int tipoDoc;

	private int identEmitBol;

	private int identDist;

	private String docCobranca;

	private Date dtVencTitulo;

	private BigDecimal vlrTitulo;

	private String agenciaCob;

	private String digAgenciaCob;

	private int especieTit;

	private char aceite;

	private Date dtEmitTit;

	private int codJuros;

	private Date dtJuros;

	private BigDecimal vlrJurosTaxa;
	
	private BigDecimal vlrPercMulta;
	
	//private BigDecimal vlrJuros;

	private int codDesc;

	private Date dtDesc;

	private BigDecimal vlrpercConced;

	private BigDecimal vlrIOF;

	private BigDecimal vlrAbatimento;

	private String identTitEmp;

	private int codProtesto;

	private int diasProtesto;

	private int codBaixaDev;

	private int diasBaixaDevol;

	private int codMoeda;

	private String contrOperCred;

	public Reg3P() {

		super( 'P' );
	}

	public Reg3P( final String line ) throws ExceptionCnab {

		this();
		parseLine( line );
	}

	public char getAceite() {

		return aceite;
	}

	/**
	 * A - Aceite.<br>
	 * N - N�o aceite.<br>
	 */
	public void setAceite( final char aceite ) {

		this.aceite = aceite;
	}

	public String getAgencia() {

		return agencia;
	}

	public void setAgencia( final String agencia ) {

		this.agencia = agencia;
	}

	public String getAgenciaCob() {

		return agenciaCob;
	}

	public void setAgenciaCob( final String agenciaCob ) {

		this.agenciaCob = agenciaCob;
	}

	public int getCodBaixaDev() {

		return codBaixaDev;
	}

	/**
	 * C�digo para baixa/devolu��o.<br>
	 * 1 - Baixar/Devolver.<br>
	 * 2 - N�o baixar/ N�o devolver.<br>
	 */
	public void setCodBaixaDev( final int codBaixaDev ) {

		this.codBaixaDev = codBaixaDev;
	}

	public int getCodCarteira() {

		return codCarteira;
	}

	/**
	 * Carteira.<br>
	 * 1 - Cobran�a simples.<br>
	 * 2 - Cobran�a vinculada.<br>
	 * 3 - Cobran�a caucionada.<br>
	 * 4 - Cobran�a descontada.<br>
	 * 7 - Cobran�a direta especial / carteira 17.
	 */
	public void setCodCarteira( final int codCarteira ) {

		this.codCarteira = codCarteira;
	}

	public int getCodDesc() {

		return codDesc;
	}

	/**
	 * C�digo do desconto.<br>
	 * 1 - Valor fixo at� a data informada.<br>
	 * 2 - Percentual at� a data informada.<br>
	 * 3 - Valor por antecipa��o por dia corrido.<br>
	 * 4 - Valor por antecipa��o por dia util.<br>
	 * 5 - Percentual sobre o valor nominal dia corrido.<br>
	 * 6 - Percentual sobre o valor nominal dia util.<br>
	 * Obs.: Para as op��es 1 e 2 ser� obrigat�rio a informa��o da data.<br>
	 */
	public void setCodDesc( final int codDesc ) {

		this.codDesc = codDesc;
	}

	public int getCodJuros() {

		return codJuros;
	}

	/**
	 * C�digo do juros de mora.<br>
	 * 1 - Valor por dia.<br>
	 * 2 - Taxa mensal.<br>
	 * 3 - Isento.<br>
	 */
	public void setCodJuros( final int codJuros ) {

		this.codJuros = codJuros;
	}

	public int getCodMoeda() {

		return codMoeda;
	}

	/**
	 * C�digo da moeda.<br>
	 * 01 - Reservado para uso futuro.<br>
	 * 02 - Dolar americano comercial/venda.<br>
	 * 03 - Dolar americano turismo/venda.<br>
	 * 04 - ITRD.<br>
	 * 05 - IDTR.<br>
	 * 06 - UFIR di�ria.<br>
	 * 07 - UFIR mensal.<br>
	 * 08 - FAJ-TR.<br>
	 * 09 - Real.<br>
	 */
	public void setCodMoeda( final int codMoeda ) {

		this.codMoeda = codMoeda;
	}

	public int getCodProtesto() {

		return codProtesto;
	}

	/**
	 * C�digo para protesto.<br>
	 * 1 - Dias corridos.<br>
	 * 2 - Dias ut�is.<br>
	 * 3 - N�o protestar.<br>
	 */
	public void setCodProtesto( final int codProtesto ) {

		this.codProtesto = codProtesto;
	}

	public String getConta() {

		return conta;
	}

	public void setConta( final String conta ) {

		this.conta = conta;
	}

	public String getContrOperCred() {

		return contrOperCred;
	}

	public void setContrOperCred( final String contrOperCred ) {

		this.contrOperCred = contrOperCred;
	}

	public int getDiasBaixaDevol() {

		return diasBaixaDevol;
	}

	/**
	 * N�mero de dias para a Baixa / Devolu��o.<br>
	 */
	public void setDiasBaixaDevol( final int diasBaixaDevol ) {

		this.diasBaixaDevol = diasBaixaDevol;
	}

	public int getDiasProtesto() {

		return diasProtesto;
	}

	/**
	 * N�mero de dias para protesto.<br>
	 */
	public void setDiasProtesto( final int diasProtesto ) {

		this.diasProtesto = diasProtesto;
	}

	public String getDigAgConta() {

		return digAgConta;
	}

	public void setDigAgConta( final String digAgConta ) {

		this.digAgConta = digAgConta;
	}

	public String getDigAgencia() {

		return digAgencia;
	}

	public void setDigAgencia( final String digAgencia ) {

		this.digAgencia = digAgencia;
	}

	public String getDigAgenciaCob() {

		return digAgenciaCob;
	}

	public void setDigAgenciaCob( final String digAgenciaCob ) {

		this.digAgenciaCob = digAgenciaCob;
	}

	public String getDigConta() {

		return digConta;
	}

	public void setDigConta( final String digConta ) {

		this.digConta = digConta;
	}

	public String getDocCobranca() {

		return docCobranca;
	}

	/**
	 * N�mero utilizado pelo cliente para identifica��o do titulo.<br>
	 */
	public void setDocCobranca( final String docCobranca ) {

		this.docCobranca = docCobranca;
	}

	public Date getDtDesc() {

		return dtDesc;
	}

	public void setDtDesc( final Date dtDesc ) {

		this.dtDesc = dtDesc;
	}

	public Date getDtEmitTit() {

		return dtEmitTit;
	}

	public void setDtEmitTit( final Date dtEmitTit ) {

		this.dtEmitTit = dtEmitTit;
	}

	public Date getDtJuros() {

		return dtJuros;
	}

	/**
	 * Se inv�lida ou n�o informada, ser� assumida a data do vencimento.<br>
	 */
	public void setDtJuros( final Date dtJuros ) {

		this.dtJuros = dtJuros;
	}

	public Date getDtVencTitulo() {

		return dtVencTitulo;
	}

	/**
	 * Data de vencimento do titulo.<br>
	 * A vista - preencher com 11111111.<br>
	 * Contra-apresenta��o - preencher com 99999999.<br>
	 * Obs.: O prazo legal para vencimento "a vista" ou "contra apresenta��o"<br>
	 * � de 15 dias da data do registro no banco.<br>
	 */
	public void setDtVencTitulo( final Date dtVencTitulo ) {

		this.dtVencTitulo = dtVencTitulo;
	}

	public int getEspecieTit() {

		return especieTit;
	}

	/**
	 * Especie do titulo CB 240.<br>
	 * 01 - CH Cheque.<br>
	 * 02 - DM Duplicata mercant�l.<br>
	 * 03 - DMI Duplicata mercant�l p/ indica��o.<br>
	 * 04 - DS Duplicata de servi�o.<br>
	 * 05 - DSI DUplicata de servi�� p/ indica��o.<br>
	 * 06 - DR Duplicata rural.<br>
	 * 07 - LC Letra de cambio.<br>
	 * 08 - NCC Nota de cr�dito comercial.<br>
	 * 09 - NCE Nota de cr�dito a exporta��o.<br>
	 * 10 - NCI Nota de cr�dito ind�stria.<br>
	 * 11 - NCR Nota de cr�dito rural.<br>
	 * 12 - NP Nota promiss�ria.<br>
	 * 13 - NPR Nota promiss�ria rural.<br>
	 * 14 - TM Triplicata mercant�l.<br>
	 * 15 - TS Triplicata de servi�o.<br>
	 * 16 - NS Nota de seguro.<br>
	 * 17 - RC Recibo.<br>
	 * 18 - FAT Fatura.<br>
	 * 19 - ND Nota de d�bito.<br>
	 * 20 - AP Apolice de seguro.<br>
	 * 21 - ME Mensalidade escolar.<br>
	 * 22 - PC Parcela de cons�rcio.<br>
	 * 99 - Outros.<br>
	 */
	public void setEspecieTit( final int especieTit ) {

		this.especieTit = especieTit;
	}

	public int getFormaCadTitulo() {

		return formaCadTitulo;
	}

	/**
	 * Forma de cadastramento do titulo.<br>
	 * 1 - Com cadastro.<br>
	 * 2 - Sem cadastro.<br>
	 */
	public void setFormaCadTitulo( final int formaCadTitulo ) {

		this.formaCadTitulo = formaCadTitulo;
	}

	public int getIdentDist() {

		return identDist;
	}

	/**
	 * Identifica��o da distribui��o.<br>
	 * 1 - Banco.<br>
	 * 2 - Cliente.<br>
	 */
	public void setIdentDist( final int identDist ) {

		this.identDist = identDist;
	}

	public int getIdentEmitBol() {

		return identEmitBol;
	}

	/**
	 * Identifica��o da emiss�o de bloqueto.<br>
	 * 1 - Banco emite.<br>
	 * 2 - Cliente emite.<br>
	 * 3 - Banco pr�-emite e o cliente completa.<br>
	 * 4 - Banco reemite.<br>
	 * 5 - Banco n�o reemite.<br>
	 * 6 - Cobran�a sem papel.<br>
	 * Obs.: Os campos 4 e 5 s� ser�o aceitos para c�digo de movimento para remessa 31.
	 */
	public void setIdentEmitBol( final int identEmitBol ) {

		this.identEmitBol = identEmitBol;
	}

	public String getIdentTitEmp() {

		return identTitEmp;
	}

	public void setIdentTitEmp( final String identTitEmp ) {

		this.identTitEmp = identTitEmp;
	}

	public String getIdentTitulo() {

		return identTitulo;
	}

	/**
	 * Nosso n�mero.<br>
	 */
	public void setIdentTitulo( final String identTitulo ) {

		this.identTitulo = identTitulo;
	}

	public int getTipoDoc() {

		return tipoDoc;
	}

	/**
	 * Tipo de documento.<br>
	 * 1 - Tradicional.<br>
	 * 2 - Escrutiral.<br>
	 */
	public void setTipoDoc( final int tipoDoc ) {

		this.tipoDoc = tipoDoc;
	}

	public BigDecimal getVlrAbatimento() {

		return vlrAbatimento;
	}

	public void setVlrAbatimento( final BigDecimal vlrAbatimento ) {

		this.vlrAbatimento = vlrAbatimento;
	}

	public BigDecimal getVlrIOF() {

		return vlrIOF;
	}

	public void setVlrIOF( final BigDecimal vlrIOF ) {

		this.vlrIOF = vlrIOF;
	}

	public BigDecimal getVlrJurosTaxa() {

		return vlrJurosTaxa;
	}

	public void setVlrJurosTaxa( final BigDecimal vlrJurosTaxa ) {

		this.vlrJurosTaxa = vlrJurosTaxa;
	}

/*		public BigDecimal getVlrJuros() {

		return vlrJuros;
	}

	public void setVlrJuros( final BigDecimal vlrJuros ) {

		this.vlrJuros = vlrJuros;
	} */
	
	

	public BigDecimal getVlrpercConced() {

		return vlrpercConced;
	}

	public void setVlrpercConced( final BigDecimal vlrpercConced ) {

		this.vlrpercConced = vlrpercConced;
	}

	public BigDecimal getVlrTitulo() {

		return vlrTitulo;
	}

	public void setVlrTitulo( final BigDecimal vlrTitulo ) {

		this.vlrTitulo = vlrTitulo;
	}

	
	public BigDecimal getVlrPercMulta() {
	
		return vlrPercMulta;
	}

	
	public void setVlrPercMulta( BigDecimal vlrPercMulta ) {
	
		this.vlrPercMulta = vlrPercMulta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#getLine()
	 */
	@ Override
	public String getLine( String padraocnab ) throws ExceptionCnab {

		StringBuilder line = new StringBuilder();

		try {
			if (Banco.SICOOB.equals( getCodBanco() )) {
				line.append( format("0", ETipo.$9, 7, 0) ); // 7 Zeros
				line.append( "3" ); // 1 Igual a 3
				line.append( format( getSeqLote(), ETipo.$9, 5, 0 ) ); // 5  N�mero de sequ�ncia do registro no lote, iniciando sempre em 1
				line.append( "P" ); // 1 C�d. segmento do reg. detalhe - igual a P
				line.append( " " ); // 1 Brancos
				String codinstrucao = Banco.getCodinstrucaoSicoob(getCodMovimento());
				line.append( codinstrucao ); // 2 codigo da instrucao
				line.append( format(" ", ETipo.X, 23, 0 ) ); // 23 Brancos
				line.append( format( getIdentTitulo(), ETipo.X, 17, 0 ) ); // 17 Nosso n�mero
				line.append( format( getCodCarteira(), ETipo.$9, 1, 0) ); // 1 carteira = 9
										// Tipo de documento  			   // 02 - DM - duplicata mercantil
				line.append( format( getEspecieTit(), ETipo.$9, 2, 0 ) ) ; //   04 - DS - duplicata de servi�o
																		   //   07 - LC - letra de c�mbio
											                               //   12 - NP - nota promiss�ria
																		   //   17 - RC - Recibo
																		   // 	 19 - ND - Nota de d�bito
																		   //	 20 - NS - Nota de servi�o
																		   //   99 - Outros
				line.append( getIdentEmitBol()); // 1 Identifica��o da emiss�o do bloqueto 1-Banco / 2-benefici�rio
				line.append( " " ); // 1 Branco
				line.append( format(getIdentTitEmp(), ETipo.X, 15, 0) ); // 15 Numero do documento de cobran�a 
																		//- N�mero utilizado pelo Benefici�rio para identificar o t�tulo
				line.append( CnabUtil.dateToString( getDtVencTitulo(), "DDMMAAAA" ) ); // 8 Vencimento do t�tulo
				line.append( format( getVlrTitulo(), ETipo.$9, 15, 2 ) ); // 15 Valor nominal do t�tulo
				line.append( format("0", ETipo.$9, 6, 0) ); // 6 Zeros
				line.append( format( getAceite(), ETipo.X, 1, 0 ) ); // 1 Aceite
				line.append( "  " ); // 2 Brancos
				line.append( CnabUtil.dateToString( getDtEmitTit(), "DDMMAAAA" ) ); // 8 Emiss�o do t�tulo
				String tipojuros = null;
				if (getCodJuros()==1) { // Valor por dia
					tipojuros = "2";
				} else if (getCodJuros()==2) { // Taxa mensal
					tipojuros = "3"; 
				} else { // 3 Isento
					tipojuros = "1"; // Isento
				}
				line.append( tipojuros ); // 1 Tipo de juros 1-Isento/2-Valor/3-Porcentagem
				line.append( format( getVlrJurosTaxa(), ETipo.$9, 15, 2 ) ); // 15 Valor juros/taxa, conforme item anterior
				line.append( format("0", ETipo.$9, 9, 0) ); // 9 Zeros
				line.append( CnabUtil.dateToString( getDtDesc(), "DDMMAAAA" ) ); // 8 Data limite para desconto
				line.append( format( getVlrpercConced(), ETipo.$9, 15, 2 ) ); // 15 Valor de desconto a ser concedido
				line.append( format(" ", ETipo.X, 15, 0) ); // 15 Brancos / Filler
				line.append( format( getVlrAbatimento(), ETipo.$9, 15, 2 ) ); // 15 Valor do abatimento se houver
				line.append( format( getIdentTitEmp(), ETipo.X, 25, 0 ) ); // 25 Uso da empresa benefici�rio 
																			// (Este valor deve ser �nico para cada t�tulo. 
																			//N�o pode ser repetido em novas remessas)
				String tipoprotesto = "1";
				if (getCodProtesto()==3) {
					tipoprotesto = "0";
				}
				line.append( format( tipoprotesto, ETipo.$9, 1, 0 ) ); // 1 Protesto autom�tico 0 - N�o / 1 - SIM
				line.append( format( getDiasProtesto(), ETipo.$9, 2, 0 ) ); // 2 N�mero de dias para protesto
				line.append( format("0", ETipo.$9, 4, 0) ); // 4 Zeros
				line.append( format( getCodMoeda(), ETipo.$9, 2, 0 ) ); // 2 C�digo da moeda - 09 = Real
				line.append( format( getContrOperCred(), ETipo.$9, 10, 0 ) ); // N. do contr. da operacao d cred 
				                                                              // Caso o t�tulo seja vinculado a um contrato de desconto de t�tulos
				line.append( format("0", ETipo.$9, 1, 0) ); // 1 Zeros
				System.out.println("Reg3P: "+line.toString().length());

			} else {
				line.append( super.getLineReg3( padraocnab ) );
				line.append( format( getAgencia(), ETipo.$9, 5, 0 ) );
				line.append( format( getDigAgencia(), ETipo.X, 1, 0 ) );
				line.append( format( getConta(), ETipo.$9, 12, 0 ) );
				line.append( format( getDigConta(), ETipo.X, 1, 0 ) );
				line.append( format( getDigAgConta(), ETipo.X, 1, 0 ) );
				line.append( format( getIdentTitulo(), ETipo.X, 20, 0 ) );
				line.append( format( getCodCarteira(), ETipo.$9, 1, 0 ) );
				line.append( format( getFormaCadTitulo(), ETipo.$9, 1, 0 ) );
				line.append( format( getTipoDoc(), ETipo.$9, 1, 0 ) );
				line.append( format( getIdentEmitBol(), ETipo.$9, 1, 0 ) );
				line.append( format( getIdentDist(), ETipo.$9, 1, 0 ) );
				line.append( format( getDocCobranca(), ETipo.X, 15, 0 ) );
				line.append( CnabUtil.dateToString( getDtVencTitulo(), null ) );
				line.append( format( getVlrTitulo(), ETipo.$9, 15, 2 ) );
				line.append( format( getAgenciaCob(), ETipo.$9, 5, 0 ) );
				line.append( format( getDigAgenciaCob(), ETipo.$9, 1, 0 ) );
				line.append( format( getEspecieTit(), ETipo.$9, 2, 0 ) );
				line.append( format( getAceite(), ETipo.X, 1, 0 ) );
				line.append( CnabUtil.dateToString( getDtEmitTit(), null ) );
				line.append( format( getCodJuros(), ETipo.$9, 1, 0 ) );
				line.append( CnabUtil.dateToString( getDtJuros(), null ) );
				line.append( format( getVlrJurosTaxa(), ETipo.$9, 15, 2 ) );
				line.append( format( getCodDesc(), ETipo.$9, 1, 0 ) );
				line.append( CnabUtil.dateToString( getDtDesc(), null ) );
				line.append( format( getVlrpercConced(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrIOF(), ETipo.$9, 15, 2 ) );
				line.append( format( getVlrAbatimento(), ETipo.$9, 15, 2 ) );
				line.append( format( getIdentTitEmp(), ETipo.X, 25, 0 ) );
				line.append( format( getCodProtesto(), ETipo.$9, 1, 0 ) );
				line.append( format( getDiasProtesto(), ETipo.$9, 2, 0 ) );
				line.append( format( getCodBaixaDev(), ETipo.$9, 1, 0 ) );
				line.append( format( getDiasBaixaDevol(), ETipo.$9, 3, 0 ) );
				line.append( format( getCodMoeda(), ETipo.$9, 2, 0 ) );
				line.append( format( getContrOperCred(), ETipo.$9, 10, 0 ) );
				line.append( " " );
			}
			line.append( (char) 13 );
			line.append( (char) 10 );
		} catch ( Exception e ) {
			throw new ExceptionCnab( "CNAB registro 3 segmento P.\nErro ao escrever registro.\n" + e.getMessage() );
		}

		return line.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.freedom.modulos.fnc.CnabUtil.Reg3#parseLine(java.lang.String)
	 */
	@ Override
	public void parseLine( String line ) throws ExceptionCnab {

		try {

			if ( line == null ) {
				throw new ExceptionCnab( "Linha nula." );
			}
			else {

				super.parseLineReg3( line );
				setAgencia( line.substring( 17, 22 ) );
				setDigAgencia( line.substring( 22, 23 ) );
				setConta( line.substring( 23, 35 ) );
				setDigConta( line.substring( 35, 36 ) );
				setDigAgConta( line.substring( 36, 37 ) );
				setIdentTitulo( line.substring( 37, 57 ) );
				setCodCarteira( line.substring( 57, 58 ).trim().length() > 0 ? Integer.parseInt( line.substring( 57, 58 ).trim() ) : 0 );
				setFormaCadTitulo( line.substring( 58, 59 ).trim().length() > 0 ? Integer.parseInt( line.substring( 58, 59 ).trim() ) : 0 );
				setTipoDoc( line.substring( 59, 60 ).trim().length() > 0 ? Integer.parseInt( line.substring( 59, 60 ).trim() ) : 0 );
				setIdentEmitBol( line.substring( 60, 61 ).trim().length() > 0 ? Integer.parseInt( line.substring( 60, 61 ).trim() ) : 0 );
				setIdentDist( line.substring( 61, 62 ).trim().length() > 0 ? Integer.parseInt( line.substring( 61, 62 ).trim() ) : 0 );
				setDocCobranca( line.substring( 62, 77 ) );
				setDtVencTitulo( CnabUtil.stringDDMMAAAAToDate( line.substring( 77, 85 ).trim() ) );
				setVlrTitulo( CnabUtil.strToBigDecimal( line.substring( 85, 100 ) ) );
				setAgenciaCob( line.substring( 100, 105 ) );
				setDigAgenciaCob( line.substring( 105, 106 ).trim() );
				setEspecieTit( line.substring( 106, 108 ).trim().length() > 0 ? Integer.parseInt( line.substring( 106, 108 ).trim() ) : 0 );
				setAceite( line.substring( 108, 109 ).charAt( 0 ) );
				setDtEmitTit( CnabUtil.stringDDMMAAAAToDate( line.substring( 109, 117 ).trim() ) );
				setCodJuros( line.substring( 117, 118 ).trim().length() > 0 ? Integer.parseInt( line.substring( 117, 118 ).trim() ) : 0 );
				setDtJuros( CnabUtil.stringDDMMAAAAToDate( line.substring( 118, 126 ).trim() ) );
				setVlrJurosTaxa( CnabUtil.strToBigDecimal( line.substring( 126, 141 ) ) );
				setCodDesc( line.substring( 141, 142 ).trim().length() > 0 ? Integer.parseInt( line.substring( 141, 142 ).trim() ) : 0 );
				setDtDesc( CnabUtil.stringDDMMAAAAToDate( line.substring( 142, 150 ).trim() ) );
				setVlrpercConced( CnabUtil.strToBigDecimal( line.substring( 150, 165 ) ) );
				setVlrIOF( CnabUtil.strToBigDecimal( line.substring( 165, 180 ) ) );
				setVlrAbatimento( CnabUtil.strToBigDecimal( line.substring( 180, 195 ) ) );
				setIdentTitEmp( line.substring( 195, 220 ) );
				setCodProtesto( line.substring( 220, 221 ).trim().length() > 0 ? Integer.parseInt( line.substring( 220, 221 ).trim() ) : 0 );
				setDiasProtesto( line.substring( 221, 223 ).trim().length() > 0 ? Integer.parseInt( line.substring( 221, 223 ).trim() ) : 0 );
				setCodBaixaDev( line.substring( 223, 224 ).trim().length() > 0 ? Integer.parseInt( line.substring( 223, 224 ).trim() ) : 0 );
				setDiasBaixaDevol( line.substring( 224, 227 ).trim().length() > 0 ? Integer.parseInt( line.substring( 224, 227 ).trim() ) : 0 );
				setCodMoeda( line.substring( 227, 229 ).trim().length() > 0 ? Integer.parseInt( line.substring( 227, 229 ).trim() ) : 0 );
				setContrOperCred( line.substring( 229, 239 ) );
			}
		} catch ( Exception e ) {
			throw new ExceptionCnab( "CNAB registro 3 segmento P.\nErro ao ler registro.\n" + e.getMessage() );
		}
	}
}