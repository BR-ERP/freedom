package org.freedom.modulos.fnc.business.component.cnab;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.business.component.Banco;
import org.freedom.library.business.exceptions.ExceptionCnab;
import org.freedom.modulos.fnc.library.business.compoent.FbnUtil.ETipo;

public class Reg3Q extends Reg3 {

	private int tipoInscCli;

	private String cpfCnpjCli;

	private String razCli;

	private String endCli;

	private String bairCli;

	private String cepCli;

	// private String sufxCepCli;
	private String cidCli;

	private String ufCli;

	private int tipoInscAva;

	private String cpfCnpjAva;

	private String razAva;

	private int codCompensacao;

	private String nossoNumero;

	public Reg3Q() {

		super( 'Q' );
	}

	public Reg3Q( final String line ) throws ExceptionCnab {

		this();
		parseLine( line );
	}

	public String getBairCli() {

		return bairCli;
	}

	public void setBairCli( final String bairCli ) {

		this.bairCli = bairCli;
	}

	public String getCepCli() {

		return cepCli;
	}

	public void setCepCli( final String cepCli ) {

		this.cepCli = cepCli;
	}

	public String getCidCli() {

		return cidCli;
	}

	public void setCidCli( final String cidCli ) {

		this.cidCli = cidCli;
	}

	public int getCodCompensacao() {

		return codCompensacao;
	}

	/**
	 * Somente para troca de arquivos entre bancos.<br>
	 */
	private void setCodCompensacao( final int codCompensacao ) {

		this.codCompensacao = codCompensacao;
	}

	public String getCpfCnpjAva() {

		return cpfCnpjAva;
	}

	/**
	 * Inscri��o. Conforme o tipo da inscri��o.<br>
	 * 
	 * @see org.freedom.modulos.fnc.business.component.cnab.CnabUtil.Reg3Q#setTipoInscAva(int tipoInscEmp )
	 */
	public void setCpfCnpjAva( final String cpfCnpjAva ) {

		this.cpfCnpjAva = cpfCnpjAva;
	}

	public String getCpfCnpjCli() {

		return cpfCnpjCli;
	}

	/**
	 * Inscri��o. Conforme o tipo da inscri��o.<br>
	 * 
	 * @see org.freedom.modulos.fnc.business.component.cnab.CnabUtil.Reg3Q#setTipoInscCli(int tipoInscEmp )
	 */
	public void setCpfCnpjCli( final String cpfCnpjCli ) {

		this.cpfCnpjCli = cpfCnpjCli;
	}

	public String getEndCli() {

		return endCli;
	}

	public void setEndCli( final String endCli ) {

		this.endCli = endCli;
	}

	public String getNossoNumero() {

		return nossoNumero;
	}

	/**
	 * Somente para troca de arquivos entre bancos.<br>
	 */
	private void setNossoNumero( final String nossoNumero ) {

		this.nossoNumero = nossoNumero;
	}

	public String getRazAva() {

		return razAva;
	}

	/**
	 * Informa��o obrigat�ria quando se tratar de titulo negociado em nome de terceiros.<br>
	 */
	public void setRazAva( final String razAva ) {

		this.razAva = razAva;
	}

	public String getRazCli() {

		return razCli;
	}

	public void setRazCli( final String razCli ) {

		this.razCli = razCli;
	}

	public int getTipoInscAva() {

		return tipoInscAva;
	}

	/**
	 * Indica o tipo de inscri��o da empresa.<br>
	 * 0 - Isento / N�o informado.<br>
	 * 1 - CPF.<br>
	 * 2 - CNPJ.<br>
	 */
	public void setTipoInscAva( final int tipoInscAva ) {

		this.tipoInscAva = tipoInscAva;
	}

	public int getTipoInscCli() {

		return tipoInscCli;
	}

	/**
	 * Indica o tipo de inscri��o da empresa.<br>
	 * 0 - Isento / N�o informado.<br>
	 * 1 - CPF.<br>
	 * 2 - CNPJ.<br>
	 */
	public void setTipoInscCli( final int tipoInscCli ) {

		this.tipoInscCli = tipoInscCli;
	}

	public String getUfCli() {

		return ufCli;
	}

	public void setUfCli( final String ufCli ) {

		this.ufCli = ufCli;
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

			if (Banco.SICOOB.equals(getCodBanco())) {
				line.append( format( "0", ETipo.$9, 7, 0 ) ); // 7 Zeros
				line.append( getRegistroDetalhe() ); // 1 Registro detalhe = 3
				line.append( format( getSeqLote(), ETipo.$9, 5, 0 ) ); // 5 N�mero sequencial do lote
				line.append( getSegmento() ); // 1 Cod. seguimento do reg. detalhe = Q
				line.append( " " ); // 1 Brancos
				String codinstrucao = Banco.getCodinstrucaoSicoob(getCodMovimento());
				line.append( codinstrucao ); // 2 codigo da instrucao
				line.append( format( getTipoInscCli(), ETipo.$9, 2, 0 ) ); //  2 Tipo da inscri��o do pagador 01 - CPF / 02 - CNPJ
				line.append( format( getCpfCnpjCli(), ETipo.$9, 14, 0 ) ); // 14 N�mero de inscri��o do pagador
				line.append( format( getRazCli(), ETipo.X, 40, 0 ) ); // 40 Nome ou raz�o social do pagador
				line.append( format( getEndCli(), ETipo.X, 40, 0 ) ); // 40 Endere�o do pagador
				line.append( format( getBairCli(), ETipo.X, 15, 0 ) ); // 15 Bairro do pagador
				line.append( format( getCepCli(), ETipo.$9, 8, 0 ) ); // 8 Cep do pagador
				line.append( format( getCidCli(), ETipo.X, 15, 0 ) ); // 15 Cidade do pagador
				line.append( format( getUfCli(), ETipo.X, 2, 0 ) ); // 2 Estado do pagador
				line.append( format( getTipoInscAva(), ETipo.$9, 2, 0 ) ); // 2 Tipo de inscri��o do sacador
				line.append( format( getCpfCnpjAva(), ETipo.$9, 14, 0 ) ); // 14 N�mero de inscri��o do sacador ou avalaista
				line.append( format( getRazAva(), ETipo.X, 40, 0 ) ); // 40 Nome ou raz�o social do sacador avalista
				line.append( StringFunctions.replicate( " ", 31 ) ); // 31 Bracos / Filler
			} else {
				line.append( super.getLineReg3( padraocnab ) );
				line.append( format( getTipoInscCli(), ETipo.$9, 1, 0 ) );
				line.append( format( getCpfCnpjCli(), ETipo.$9, 15, 0 ) );
				line.append( format( getRazCli(), ETipo.X, 40, 0 ) );
				line.append( format( getEndCli(), ETipo.X, 40, 0 ) );
				line.append( format( getBairCli(), ETipo.X, 15, 0 ) );
				line.append( format( getCepCli(), ETipo.$9, 8, 0 ) );
				line.append( format( getCidCli(), ETipo.X, 15, 0 ) );
				line.append( format( getUfCli(), ETipo.X, 2, 0 ) );
				line.append( format( getTipoInscAva(), ETipo.$9, 1, 0 ) );
				line.append( format( getCpfCnpjAva(), ETipo.$9, 15, 0 ) );
				line.append( format( getRazAva(), ETipo.X, 40, 0 ) );
				line.append( format( getCodCompensacao(), ETipo.$9, 3, 0 ) );
				line.append( format( getNossoNumero(), ETipo.X, 20, 0 ) );
				line.append( StringFunctions.replicate( " ", 8 ) );
			}
			line.append( (char) 13 );
			line.append( (char) 10 );
		} catch ( Exception e ) {
			throw new ExceptionCnab( "CNAB registro 3 segmento Q.\nErro ao escrever registro.\n" + e.getMessage() );
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
				setTipoInscCli( line.substring( 17, 18 ).trim().length() > 0 ? Integer.parseInt( line.substring( 17, 18 ).trim() ) : 0 );
				setCpfCnpjCli( line.substring( 18, 33 ) );
				setRazCli( line.substring( 33, 73 ) );
				setEndCli( line.substring( 73, 113 ) );
				setBairCli( line.substring( 113, 128 ) );
				setCepCli( line.substring( 128, 136 ) );
				setCidCli( line.substring( 136, 151 ) );
				setUfCli( line.substring( 151, 153 ) );
				setTipoInscAva( line.substring( 153, 154 ).trim().length() > 0 ? Integer.parseInt( line.substring( 153, 154 ).trim() ) : 0 );
				setCpfCnpjAva( line.substring( 154, 169 ) );
				setRazAva( line.substring( 169, 209 ) );
				setCodCompensacao( line.substring( 209, 212 ).trim().length() > 0 ? Integer.parseInt( line.substring( 209, 212 ).trim() ) : 0 );
				setNossoNumero( line.substring( 212, 232 ) );
			}
		} catch ( Exception e ) {
			throw new ExceptionCnab( "CNAB registro 3 segmento Q.\nErro ao ler registro.\n" + e.getMessage() );
		}
	}
}