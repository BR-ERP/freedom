package org.freedom.persist.entity;

// Generated 18/02/2014 15:53:28 by Hibernate Tools 4.0.0

/**
 * EqmodloteId generated by hbm2java
 */
public class EqmodloteId implements java.io.Serializable {

	private int codmodlote;
	private short codfilial;
	private int codemp;

	public EqmodloteId() {
	}

	public EqmodloteId(int codmodlote, short codfilial, int codemp) {
		this.codmodlote = codmodlote;
		this.codfilial = codfilial;
		this.codemp = codemp;
	}

	public int getCodmodlote() {
		return this.codmodlote;
	}

	public void setCodmodlote(int codmodlote) {
		this.codmodlote = codmodlote;
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
		if (!(other instanceof EqmodloteId))
			return false;
		EqmodloteId castOther = (EqmodloteId) other;

		return (this.getCodmodlote() == castOther.getCodmodlote())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodmodlote();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		return result;
	}

}
