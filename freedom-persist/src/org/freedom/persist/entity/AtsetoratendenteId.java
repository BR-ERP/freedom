package org.freedom.persist.entity;

// Generated 20/11/2013 10:59:59 by Hibernate Tools 4.0.0

/**
 * AtsetoratendenteId generated by hbm2java
 */
public class AtsetoratendenteId implements java.io.Serializable {

	private int codsetat;
	private short codfilial;
	private int codemp;
	private int codatend;
	private short codfilialae;
	private int codempae;

	public AtsetoratendenteId() {
	}

	public AtsetoratendenteId(int codsetat, short codfilial, int codemp,
			int codatend, short codfilialae, int codempae) {
		this.codsetat = codsetat;
		this.codfilial = codfilial;
		this.codemp = codemp;
		this.codatend = codatend;
		this.codfilialae = codfilialae;
		this.codempae = codempae;
	}

	public int getCodsetat() {
		return this.codsetat;
	}

	public void setCodsetat(int codsetat) {
		this.codsetat = codsetat;
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

	public int getCodatend() {
		return this.codatend;
	}

	public void setCodatend(int codatend) {
		this.codatend = codatend;
	}

	public short getCodfilialae() {
		return this.codfilialae;
	}

	public void setCodfilialae(short codfilialae) {
		this.codfilialae = codfilialae;
	}

	public int getCodempae() {
		return this.codempae;
	}

	public void setCodempae(int codempae) {
		this.codempae = codempae;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AtsetoratendenteId))
			return false;
		AtsetoratendenteId castOther = (AtsetoratendenteId) other;

		return (this.getCodsetat() == castOther.getCodsetat())
				&& (this.getCodfilial() == castOther.getCodfilial())
				&& (this.getCodemp() == castOther.getCodemp())
				&& (this.getCodatend() == castOther.getCodatend())
				&& (this.getCodfilialae() == castOther.getCodfilialae())
				&& (this.getCodempae() == castOther.getCodempae());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodsetat();
		result = 37 * result + this.getCodfilial();
		result = 37 * result + this.getCodemp();
		result = 37 * result + this.getCodatend();
		result = 37 * result + this.getCodfilialae();
		result = 37 * result + this.getCodempae();
		return result;
	}

}
