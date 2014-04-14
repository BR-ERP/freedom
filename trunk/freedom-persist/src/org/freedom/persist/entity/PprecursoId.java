package org.freedom.persist.entity;

// Generated 14/04/2014 10:17:08 by Hibernate Tools 4.0.0

/**
 * PprecursoId generated by hbm2java
 */
public class PprecursoId implements java.io.Serializable {

	private int codrecp;
	private short codfilial;
	private int codemp;

	public PprecursoId() {
	}

	public PprecursoId(int codrecp, short codfilial, int codemp) {
		this.codrecp = codrecp;
		this.codfilial = codfilial;
		this.codemp = codemp;
	}

	public int getCodrecp() {
		return this.codrecp;
	}

	public void setCodrecp(int codrecp) {
		this.codrecp = codrecp;
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
		if (!(other instanceof PprecursoId))
			return false;
		PprecursoId castOther = (PprecursoId) other;

		return (this.getCodrecp() == castOther.getCodrecp())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodrecp();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		return result;
	}

}
