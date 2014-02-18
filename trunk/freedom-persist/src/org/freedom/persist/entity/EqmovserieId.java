package org.freedom.persist.entity;

// Generated 18/02/2014 15:53:28 by Hibernate Tools 4.0.0

/**
 * EqmovserieId generated by hbm2java
 */
public class EqmovserieId implements java.io.Serializable {

	private int codmovserie;
	private short codfilial;
	private int codemp;

	public EqmovserieId() {
	}

	public EqmovserieId(int codmovserie, short codfilial, int codemp) {
		this.codmovserie = codmovserie;
		this.codfilial = codfilial;
		this.codemp = codemp;
	}

	public int getCodmovserie() {
		return this.codmovserie;
	}

	public void setCodmovserie(int codmovserie) {
		this.codmovserie = codmovserie;
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
		if (!(other instanceof EqmovserieId))
			return false;
		EqmovserieId castOther = (EqmovserieId) other;

		return (this.getCodmovserie() == castOther.getCodmovserie())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodmovserie();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		return result;
	}

}
