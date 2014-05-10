package org.freedom.persist.entity;

// Generated 10/05/2014 10:27:15 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * Necontingencia generated by hbm2java
 */
public class Necontingencia implements java.io.Serializable {

	private long id;
	private Sgfilial sgfilial;
	private Date dtentrada;
	private Date hentrada;
	private Date dtsaida;
	private Date hsaida;
	private char tipoemissaonfe;
	private String justificativa;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Necontingencia() {
	}

	public Necontingencia(long id, Sgfilial sgfilial, Date dtentrada,
			Date hentrada, char tipoemissaonfe, String justificativa,
			Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.dtentrada = dtentrada;
		this.hentrada = hentrada;
		this.tipoemissaonfe = tipoemissaonfe;
		this.justificativa = justificativa;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Necontingencia(long id, Sgfilial sgfilial, Date dtentrada,
			Date hentrada, Date dtsaida, Date hsaida, char tipoemissaonfe,
			String justificativa, Date dtins, Date hins, String idusuins,
			Date dtalt, Date halt, String idusualt) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.dtentrada = dtentrada;
		this.hentrada = hentrada;
		this.dtsaida = dtsaida;
		this.hsaida = hsaida;
		this.tipoemissaonfe = tipoemissaonfe;
		this.justificativa = justificativa;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Sgfilial getSgfilial() {
		return this.sgfilial;
	}

	public void setSgfilial(Sgfilial sgfilial) {
		this.sgfilial = sgfilial;
	}

	public Date getDtentrada() {
		return this.dtentrada;
	}

	public void setDtentrada(Date dtentrada) {
		this.dtentrada = dtentrada;
	}

	public Date getHentrada() {
		return this.hentrada;
	}

	public void setHentrada(Date hentrada) {
		this.hentrada = hentrada;
	}

	public Date getDtsaida() {
		return this.dtsaida;
	}

	public void setDtsaida(Date dtsaida) {
		this.dtsaida = dtsaida;
	}

	public Date getHsaida() {
		return this.hsaida;
	}

	public void setHsaida(Date hsaida) {
		this.hsaida = hsaida;
	}

	public char getTipoemissaonfe() {
		return this.tipoemissaonfe;
	}

	public void setTipoemissaonfe(char tipoemissaonfe) {
		this.tipoemissaonfe = tipoemissaonfe;
	}

	public String getJustificativa() {
		return this.justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public Date getDtins() {
		return this.dtins;
	}

	public void setDtins(Date dtins) {
		this.dtins = dtins;
	}

	public Date getHins() {
		return this.hins;
	}

	public void setHins(Date hins) {
		this.hins = hins;
	}

	public String getIdusuins() {
		return this.idusuins;
	}

	public void setIdusuins(String idusuins) {
		this.idusuins = idusuins;
	}

	public Date getDtalt() {
		return this.dtalt;
	}

	public void setDtalt(Date dtalt) {
		this.dtalt = dtalt;
	}

	public Date getHalt() {
		return this.halt;
	}

	public void setHalt(Date halt) {
		this.halt = halt;
	}

	public String getIdusualt() {
		return this.idusualt;
	}

	public void setIdusualt(String idusualt) {
		this.idusualt = idusualt;
	}

}
