package org.freedom.persist.entity;

// Generated 10/05/2014 10:27:15 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * Crfichaorc generated by hbm2java
 */
public class Crfichaorc implements java.io.Serializable {

	private CrfichaorcId id;
	private Critfichaaval critfichaaval;
	private Vditorcamento vditorcamento;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Crfichaorc() {
	}

	public Crfichaorc(CrfichaorcId id, Critfichaaval critfichaaval,
			Vditorcamento vditorcamento, Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.critfichaaval = critfichaaval;
		this.vditorcamento = vditorcamento;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Crfichaorc(CrfichaorcId id, Critfichaaval critfichaaval,
			Vditorcamento vditorcamento, Date dtins, Date hins,
			String idusuins, Date dtalt, Date halt, String idusualt) {
		this.id = id;
		this.critfichaaval = critfichaaval;
		this.vditorcamento = vditorcamento;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public CrfichaorcId getId() {
		return this.id;
	}

	public void setId(CrfichaorcId id) {
		this.id = id;
	}

	public Critfichaaval getCritfichaaval() {
		return this.critfichaaval;
	}

	public void setCritfichaaval(Critfichaaval critfichaaval) {
		this.critfichaaval = critfichaaval;
	}

	public Vditorcamento getVditorcamento() {
		return this.vditorcamento;
	}

	public void setVditorcamento(Vditorcamento vditorcamento) {
		this.vditorcamento = vditorcamento;
	}

	public Date getDtins() {
		return this.dtins;
	}

	public void setDtins(Date dtins) {
		this.dtins = dtins;
	}

	public Date getHins() {
		return this.hins;
	}

	public void setHins(Date hins) {
		this.hins = hins;
	}

	public String getIdusuins() {
		return this.idusuins;
	}

	public void setIdusuins(String idusuins) {
		this.idusuins = idusuins;
	}

	public Date getDtalt() {
		return this.dtalt;
	}

	public void setDtalt(Date dtalt) {
		this.dtalt = dtalt;
	}

	public Date getHalt() {
		return this.halt;
	}

	public void setHalt(Date halt) {
		this.halt = halt;
	}

	public String getIdusualt() {
		return this.idusualt;
	}

	public void setIdusualt(String idusualt) {
		this.idusualt = idusualt;
	}

}
