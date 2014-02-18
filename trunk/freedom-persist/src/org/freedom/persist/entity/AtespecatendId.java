package org.freedom.persist.entity;

// Generated 18/02/2014 15:53:28 by Hibernate Tools 4.0.0

/**
 * AtespecatendId generated by hbm2java
 */
public class AtespecatendId implements java.io.Serializable {

	private int codespec;
	private short codfilial;
	private int codemp;

	public AtespecatendId() {
	}

	public AtespecatendId(int codespec, short codfilial, int codemp) {
		this.codespec = codespec;
		this.codfilial = codfilial;
		this.codemp = codemp;
	}

	public int getCodespec() {
		return this.codespec;
	}

	public void setCodespec(int codespec) {
		this.codespec = codespec;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AtespecatendId))
			return false;
		AtespecatendId castOther = (AtespecatendId) other;

		return (this.getCodespec() == castOther.getCodespec())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodespec();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		return result;
	}

}
