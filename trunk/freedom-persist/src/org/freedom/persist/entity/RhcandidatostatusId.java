package org.freedom.persist.entity;

// Generated 14/04/2014 10:17:08 by Hibernate Tools 4.0.0

/**
 * RhcandidatostatusId generated by hbm2java
 */
public class RhcandidatostatusId implements java.io.Serializable {

	private int codcand;
	private short codfilial;
	private int codemp;
	private int sqstcand;

	public RhcandidatostatusId() {
	}

	public RhcandidatostatusId(int codcand, short codfilial, int codemp,
			int sqstcand) {
		this.codcand = codcand;
		this.codfilial = codfilial;
		this.codemp = codemp;
		this.sqstcand = sqstcand;
	}

	public int getCodcand() {
		return this.codcand;
	}

	public void setCodcand(int codcand) {
		this.codcand = codcand;
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

	public int getSqstcand() {
		return this.sqstcand;
	}

	public void setSqstcand(int sqstcand) {
		this.sqstcand = sqstcand;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RhcandidatostatusId))
			return false;
		RhcandidatostatusId castOther = (RhcandidatostatusId) other;

		return (this.getCodcand() == castOther.getCodcand())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp())
				&& (this.getSqstcand() == castOther.getSqstcand());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodcand();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		result = 37 * result + this.getSqstcand();
		return result;
	}

}
