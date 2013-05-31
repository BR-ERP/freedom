package org.freedom.persist.entity;

// Generated 31/05/2013 12:00:37 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * AtatendimentoId generated by hbm2java
 */
@Embeddable
public class AtatendimentoId implements java.io.Serializable {

	private int codatendo;
	private short codfilial;
	private int codemp;

	public AtatendimentoId() {
	}

	public AtatendimentoId(int codatendo, short codfilial, int codemp) {
		this.codatendo = codatendo;
		this.codfilial = codfilial;
		this.codemp = codemp;
	}

	@Column(name = "CODATENDO", nullable = false)
	public int getCodatendo() {
		return this.codatendo;
	}

	public void setCodatendo(int codatendo) {
		this.codatendo = codatendo;
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
		if (!(other instanceof AtatendimentoId))
			return false;
		AtatendimentoId castOther = (AtatendimentoId) other;

		return (this.getCodatendo() == castOther.getCodatendo())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodatendo();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		return result;
	}

}
