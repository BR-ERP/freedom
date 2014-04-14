package org.freedom.persist.entity;

// Generated 14/04/2014 10:17:08 by Hibernate Tools 4.0.0

/**
 * VddetorcId generated by hbm2java
 */
public class VddetorcId implements java.io.Serializable {

	private int codorc;
	private char tipoorc;
	private short codfilial;
	private int codemp;

	public VddetorcId() {
	}

	public VddetorcId(int codorc, char tipoorc, short codfilial, int codemp) {
		this.codorc = codorc;
		this.tipoorc = tipoorc;
		this.codfilial = codfilial;
		this.codemp = codemp;
	}

	public int getCodorc() {
		return this.codorc;
	}

	public void setCodorc(int codorc) {
		this.codorc = codorc;
	}

	public char getTipoorc() {
		return this.tipoorc;
	}

	public void setTipoorc(char tipoorc) {
		this.tipoorc = tipoorc;
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
		if (!(other instanceof VddetorcId))
			return false;
		VddetorcId castOther = (VddetorcId) other;

		return (this.getCodorc() == castOther.getCodorc())
				&& (this.getTipoorc() == castOther.getTipoorc())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodorc();
		result = 37 * result + this.getTipoorc();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		return result;
	}

}
