package org.freedom.persist.entity;

// Generated 12/05/2014 09:11:34 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * FnparcpagId generated by hbm2java
 */
@Embeddable
public class FnparcpagId implements java.io.Serializable {

	private int nroparcpag;
	private int codplanopag;
	private short codfilial;
	private int codemp;

	public FnparcpagId() {
	}

	public FnparcpagId(int nroparcpag, int codplanopag, short codfilial,
			int codemp) {
		this.nroparcpag = nroparcpag;
		this.codplanopag = codplanopag;
		this.codfilial = codfilial;
		this.codemp = codemp;
	}

	@Column(name = "NROPARCPAG", nullable = false)
	public int getNroparcpag() {
		return this.nroparcpag;
	}

	public void setNroparcpag(int nroparcpag) {
		this.nroparcpag = nroparcpag;
	}

	@Column(name = "CODPLANOPAG", nullable = false)
	public int getCodplanopag() {
		return this.codplanopag;
	}

	public void setCodplanopag(int codplanopag) {
		this.codplanopag = codplanopag;
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
		if (!(other instanceof FnparcpagId))
			return false;
		FnparcpagId castOther = (FnparcpagId) other;

		return (this.getNroparcpag() == castOther.getNroparcpag())
				&& (this.getCodplanopag() == castOther.getCodplanopag())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getNroparcpag();
		result = 37 * result + this.getCodplanopag();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		return result;
	}

}
