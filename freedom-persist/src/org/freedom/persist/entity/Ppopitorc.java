package org.freedom.persist.entity;

// Generated 10/05/2014 10:27:15 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;

/**
 * Ppopitorc generated by hbm2java
 */
public class Ppopitorc implements java.io.Serializable {

	private PpopitorcId id;
	private Vditorcamento vditorcamento;
	private Ppop ppop;
	private BigDecimal qtdprod;
	private BigDecimal qtdfinalproditorc;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Ppopitorc() {
	}

	public Ppopitorc(PpopitorcId id, Vditorcamento vditorcamento, Ppop ppop,
			BigDecimal qtdprod, Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.vditorcamento = vditorcamento;
		this.ppop = ppop;
		this.qtdprod = qtdprod;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Ppopitorc(PpopitorcId id, Vditorcamento vditorcamento, Ppop ppop,
			BigDecimal qtdprod, BigDecimal qtdfinalproditorc, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt) {
		this.id = id;
		this.vditorcamento = vditorcamento;
		this.ppop = ppop;
		this.qtdprod = qtdprod;
		this.qtdfinalproditorc = qtdfinalproditorc;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public PpopitorcId getId() {
		return this.id;
	}

	public void setId(PpopitorcId id) {
		this.id = id;
	}

	public Vditorcamento getVditorcamento() {
		return this.vditorcamento;
	}

	public void setVditorcamento(Vditorcamento vditorcamento) {
		this.vditorcamento = vditorcamento;
	}

	public Ppop getPpop() {
		return this.ppop;
	}

	public void setPpop(Ppop ppop) {
		this.ppop = ppop;
	}

	public BigDecimal getQtdprod() {
		return this.qtdprod;
	}

	public void setQtdprod(BigDecimal qtdprod) {
		this.qtdprod = qtdprod;
	}

	public BigDecimal getQtdfinalproditorc() {
		return this.qtdfinalproditorc;
	}

	public void setQtdfinalproditorc(BigDecimal qtdfinalproditorc) {
		this.qtdfinalproditorc = qtdfinalproditorc;
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
