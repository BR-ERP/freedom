package org.freedom.persist.entity;

// Generated 14/04/2014 10:17:08 by Hibernate Tools 4.0.0

/**
 * PvsequenciaId generated by hbm2java
 */
public class PvsequenciaId implements java.io.Serializable {

	private int codemp;
	private short codfilial;
	private int codcaixa;

	public PvsequenciaId() {
	}

	public PvsequenciaId(int codemp, short codfilial, int codcaixa) {
		this.codemp = codemp;
		this.codfilial = codfilial;
		this.codcaixa = codcaixa;
	}

	public int getCodemp() {
		return this.codemp;
	}

	public void setCodemp(int codemp) {
		this.codemp = codemp;
	}

	public short getCodfilial() {
		return this.codfilial;
	}

	public void setCodfilial(short codfilial) {
		this.codfilial = codfilial;
	}

	public int getCodcaixa() {
		return this.codcaixa;
	}

	public void setCodcaixa(int codcaixa) {
		this.codcaixa = codcaixa;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PvsequenciaId))
			return false;
		PvsequenciaId castOther = (PvsequenciaId) other;

		return (this.getCodemp() == castOther.getCodemp())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodcaixa() == castOther.getCodcaixa());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodemp();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodcaixa();
		return result;
	}

}
