package org.freedom.persist.entity;

// Generated 14/04/2014 10:17:08 by Hibernate Tools 4.0.0

/**
 * RhvagacaracrestId generated by hbm2java
 */
public class RhvagacaracrestId implements java.io.Serializable {

	private int codvaga;
	private short codfilial;
	private int codemp;
	private String codcarac;
	private short codfilialct;
	private int codempct;

	public RhvagacaracrestId() {
	}

	public RhvagacaracrestId(int codvaga, short codfilial, int codemp,
			String codcarac, short codfilialct, int codempct) {
		this.codvaga = codvaga;
		this.codfilial = codfilial;
		this.codemp = codemp;
		this.codcarac = codcarac;
		this.codfilialct = codfilialct;
		this.codempct = codempct;
	}

	public int getCodvaga() {
		return this.codvaga;
	}

	public void setCodvaga(int codvaga) {
		this.codvaga = codvaga;
	}

	public short getCodfilial() {
		return this.codfilial;
	}

	public void setCodfilial(short codfilial) {
		this.codfilial = codfilial;
	}

	public int getCodemp() {
		return this.codemp;
	}

	public void setCodemp(int codemp) {
		this.codemp = codemp;
	}

	public String getCodcarac() {
		return this.codcarac;
	}

	public void setCodcarac(String codcarac) {
		this.codcarac = codcarac;
	}

	public short getCodfilialct() {
		return this.codfilialct;
	}

	public void setCodfilialct(short codfilialct) {
		this.codfilialct = codfilialct;
	}

	public int getCodempct() {
		return this.codempct;
	}

	public void setCodempct(int codempct) {
		this.codempct = codempct;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RhvagacaracrestId))
			return false;
		RhvagacaracrestId castOther = (RhvagacaracrestId) other;

		return (this.getCodvaga() == castOther.getCodvaga())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp())
				&& ((this.getCodcarac() == castOther.getCodcarac()) || (this
						.getCodcarac() != null
						&& castOther.getCodcarac() != null && this
						.getCodcarac().equals(castOther.getCodcarac())))
				&& (this.getCodfilialct() == castOther.getCodfilialct())
				&& (this.getCodempct() == castOther.getCodempct());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodvaga();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		result = 37 * result
				+ (getCodcarac() == null ? 0 : this.getCodcarac().hashCode());
		result = 37 * result + this.getCodfilialct();
		result = 37 * result + this.getCodempct();
		return result;
	}

}
