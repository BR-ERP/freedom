package org.freedom.persist.entity;

// Generated 31/05/2013 12:00:37 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * VdvendaorcId generated by hbm2java
 */
@Embeddable
public class VdvendaorcId implements java.io.Serializable {

	private int codvenda;
	private int coditvenda;
	private char tipovenda;
	private int codfilial;
	private int codemp;
	private int codorc;
	private int coditorc;
	private char tipoorc;
	private int codfilialor;
	private int codempor;

	public VdvendaorcId() {
	}

	public VdvendaorcId(int codvenda, int coditvenda, char tipovenda,
			int codfilial, int codemp, int codorc, int coditorc, char tipoorc,
			int codfilialor, int codempor) {
		this.codvenda = codvenda;
		this.coditvenda = coditvenda;
		this.tipovenda = tipovenda;
		this.codfilial = codfilial;
		this.codemp = codemp;
		this.codorc = codorc;
		this.coditorc = coditorc;
		this.tipoorc = tipoorc;
		this.codfilialor = codfilialor;
		this.codempor = codempor;
	}

	@Column(name = "CODVENDA", nullable = false)
	public int getCodvenda() {
		return this.codvenda;
	}

	public void setCodvenda(int codvenda) {
		this.codvenda = codvenda;
	}

	@Column(name = "CODITVENDA", nullable = false)
	public int getCoditvenda() {
		return this.coditvenda;
	}

	public void setCoditvenda(int coditvenda) {
		this.coditvenda = coditvenda;
	}

	@Column(name = "TIPOVENDA", nullable = false, length = 1)
	public char getTipovenda() {
		return this.tipovenda;
	}

	public void setTipovenda(char tipovenda) {
		this.tipovenda = tipovenda;
	}

	@Column(name = "CODFILIAL", nullable = false)
	public int getCodfilial() {
		return this.codfilial;
	}

	public void setCodfilial(int codfilial) {
		this.codfilial = codfilial;
	}

	@Column(name = "CODEMP", nullable = false)
	public int getCodemp() {
		return this.codemp;
	}

	public void setCodemp(int codemp) {
		this.codemp = codemp;
	}

	@Column(name = "CODORC", nullable = false)
	public int getCodorc() {
		return this.codorc;
	}

	public void setCodorc(int codorc) {
		this.codorc = codorc;
	}

	@Column(name = "CODITORC", nullable = false)
	public int getCoditorc() {
		return this.coditorc;
	}

	public void setCoditorc(int coditorc) {
		this.coditorc = coditorc;
	}

	@Column(name = "TIPOORC", nullable = false, length = 1)
	public char getTipoorc() {
		return this.tipoorc;
	}

	public void setTipoorc(char tipoorc) {
		this.tipoorc = tipoorc;
	}

	@Column(name = "CODFILIALOR", nullable = false)
	public int getCodfilialor() {
		return this.codfilialor;
	}

	public void setCodfilialor(int codfilialor) {
		this.codfilialor = codfilialor;
	}

	@Column(name = "CODEMPOR", nullable = false)
	public int getCodempor() {
		return this.codempor;
	}

	public void setCodempor(int codempor) {
		this.codempor = codempor;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VdvendaorcId))
			return false;
		VdvendaorcId castOther = (VdvendaorcId) other;

		return (this.getCodvenda() == castOther.getCodvenda())
				&& (this.getCoditvenda() == castOther.getCoditvenda())
				&& (this.getTipovenda() == castOther.getTipovenda())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp())
				&& (this.getCodorc() == castOther.getCodorc())
				&& (this.getCoditorc() == castOther.getCoditorc())
				&& (this.getTipoorc() == castOther.getTipoorc())
				&& (this.getCodfilialor() == castOther.getCodfilialor())
				&& (this.getCodempor() == castOther.getCodempor());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodvenda();
		result = 37 * result + this.getCoditvenda();
		result = 37 * result + this.getTipovenda();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		result = 37 * result + this.getCodorc();
		result = 37 * result + this.getCoditorc();
		result = 37 * result + this.getTipoorc();
		result = 37 * result + this.getCodfilialor();
		result = 37 * result + this.getCodempor();
		return result;
	}

}
