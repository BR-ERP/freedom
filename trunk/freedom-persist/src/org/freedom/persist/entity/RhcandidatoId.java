package org.freedom.persist.entity;

// Generated 30/01/2013 08:30:43 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RhcandidatoId generated by hbm2java
 */
@Embeddable
public class RhcandidatoId implements java.io.Serializable {

	private int codcand;
	private short codfilial;
	private int codemp;

	public RhcandidatoId() {
	}

	public RhcandidatoId(int codcand, short codfilial, int codemp) {
		this.codcand = codcand;
		this.codfilial = codfilial;
		this.codemp = codemp;
	}

	@Column(name = "CODCAND", nullable = false)
	public int getCodcand() {
		return this.codcand;
	}

	public void setCodcand(int codcand) {
		this.codcand = codcand;
	}

	@Column(name = "CODFILIAL", nullable = false)
	public short getCodfilial() {
		return this.codfilial;
	}

	public void setCodfilial(short codfilial) {
		this.codfilial = codfilial;
	}

	@Column(name = "CODEMP", nullable = false)
	public int getCodemp() {
		return this.codemp;
	}

	public void setCodemp(int codemp) {
		this.codemp = codemp;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RhcandidatoId))
			return false;
		RhcandidatoId castOther = (RhcandidatoId) other;

		return (this.getCodcand() == castOther.getCodcand())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodcand();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		return result;
	}

}