package org.freedom.persist.entity;

// Generated 20/11/2013 10:59:59 by Hibernate Tools 4.0.0

/**
 * EqrecamostragemId generated by hbm2java
 */
public class EqrecamostragemId implements java.io.Serializable {

	private int ticket;
	private int coditrecmerc;
	private int codamostragem;
	private short codfilial;
	private int codemp;

	public EqrecamostragemId() {
	}

	public EqrecamostragemId(int ticket, int coditrecmerc, int codamostragem,
			short codfilial, int codemp) {
		this.ticket = ticket;
		this.coditrecmerc = coditrecmerc;
		this.codamostragem = codamostragem;
		this.codfilial = codfilial;
		this.codemp = codemp;
	}

	public int getTicket() {
		return this.ticket;
	}

	public void setTicket(int ticket) {
		this.ticket = ticket;
	}

	public int getCoditrecmerc() {
		return this.coditrecmerc;
	}

	public void setCoditrecmerc(int coditrecmerc) {
		this.coditrecmerc = coditrecmerc;
	}

	public int getCodamostragem() {
		return this.codamostragem;
	}

	public void setCodamostragem(int codamostragem) {
		this.codamostragem = codamostragem;
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
		if (!(other instanceof EqrecamostragemId))
			return false;
		EqrecamostragemId castOther = (EqrecamostragemId) other;

		return (this.getTicket() == castOther.getTicket())
				&& (this.getCoditrecmerc() == castOther.getCoditrecmerc())
				&& (this.getCodamostragem() == castOther.getCodamostragem())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getTicket();
		result = 37 * result + this.getCoditrecmerc();
		result = 37 * result + this.getCodamostragem();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		return result;
	}

}
