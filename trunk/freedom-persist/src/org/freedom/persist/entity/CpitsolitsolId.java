package org.freedom.persist.entity;

// Generated 12/05/2014 09:11:34 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CpitsolitsolId generated by hbm2java
 */
@Embeddable
public class CpitsolitsolId implements java.io.Serializable {

	private int codsol;
	private short coditsol;
	private int codsoln;
	private short coditsoln;
	private short codfilial;
	private int codemp;
	private short codfilialsn;
	private int codempsn;

	public CpitsolitsolId() {
	}

	public CpitsolitsolId(int codsol, short coditsol, int codsoln,
			short coditsoln, short codfilial, int codemp, short codfilialsn,
			int codempsn) {
		this.codsol = codsol;
		this.coditsol = coditsol;
		this.codsoln = codsoln;
		this.coditsoln = coditsoln;
		this.codfilial = codfilial;
		this.codemp = codemp;
		this.codfilialsn = codfilialsn;
		this.codempsn = codempsn;
	}

	@Column(name = "CODSOL", nullable = false)
	public int getCodsol() {
		return this.codsol;
	}

	public void setCodsol(int codsol) {
		this.codsol = codsol;
	}

	@Column(name = "CODITSOL", nullable = false)
	public short getCoditsol() {
		return this.coditsol;
	}

	public void setCoditsol(short coditsol) {
		this.coditsol = coditsol;
	}

	@Column(name = "CODSOLN", nullable = false)
	public int getCodsoln() {
		return this.codsoln;
	}

	public void setCodsoln(int codsoln) {
		this.codsoln = codsoln;
	}

	@Column(name = "CODITSOLN", nullable = false)
	public short getCoditsoln() {
		return this.coditsoln;
	}

	public void setCoditsoln(short coditsoln) {
		this.coditsoln = coditsoln;
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

	@Column(name = "CODFILIALSN", nullable = false)
	public short getCodfilialsn() {
		return this.codfilialsn;
	}

	public void setCodfilialsn(short codfilialsn) {
		this.codfilialsn = codfilialsn;
	}

	@Column(name = "CODEMPSN", nullable = false)
	public int getCodempsn() {
		return this.codempsn;
	}

	public void setCodempsn(int codempsn) {
		this.codempsn = codempsn;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CpitsolitsolId))
			return false;
		CpitsolitsolId castOther = (CpitsolitsolId) other;

		return (this.getCodsol() == castOther.getCodsol())
				&& (this.getCoditsol() == castOther.getCoditsol())
				&& (this.getCodsoln() == castOther.getCodsoln())
				&& (this.getCoditsoln() == castOther.getCoditsoln())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp())
				&& (this.getCodfilialsn() == castOther.getCodfilialsn())
				&& (this.getCodempsn() == castOther.getCodempsn());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodsol();
		result = 37 * result + this.getCoditsol();
		result = 37 * result + this.getCodsoln();
		result = 37 * result + this.getCoditsoln();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		result = 37 * result + this.getCodfilialsn();
		result = 37 * result + this.getCodempsn();
		return result;
	}

}
