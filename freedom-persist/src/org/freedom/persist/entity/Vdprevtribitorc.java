package org.freedom.persist.entity;

// Generated 20/11/2013 10:59:59 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;

/**
 * Vdprevtribitorc generated by hbm2java
 */
public class Vdprevtribitorc implements java.io.Serializable {

	private VdprevtribitorcId id;
	private Vditorcamento vditorcamento;
	private BigDecimal vlricmsitorc;
	private BigDecimal vlripiitorc;
	private BigDecimal vlrpisitorc;
	private BigDecimal vlrcofinsitorc;
	private BigDecimal vlriritorc;
	private BigDecimal vlrcsocialitorc;
	private BigDecimal vlrissitorc;
	private BigDecimal vlrsimplesitorc;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Vdprevtribitorc() {
	}

	public Vdprevtribitorc(Vditorcamento vditorcamento, Date dtins, Date hins,
			String idusuins) {
		this.vditorcamento = vditorcamento;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Vdprevtribitorc(Vditorcamento vditorcamento,
			BigDecimal vlricmsitorc, BigDecimal vlripiitorc,
			BigDecimal vlrpisitorc, BigDecimal vlrcofinsitorc,
			BigDecimal vlriritorc, BigDecimal vlrcsocialitorc,
			BigDecimal vlrissitorc, BigDecimal vlrsimplesitorc, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt) {
		this.vditorcamento = vditorcamento;
		this.vlricmsitorc = vlricmsitorc;
		this.vlripiitorc = vlripiitorc;
		this.vlrpisitorc = vlrpisitorc;
		this.vlrcofinsitorc = vlrcofinsitorc;
		this.vlriritorc = vlriritorc;
		this.vlrcsocialitorc = vlrcsocialitorc;
		this.vlrissitorc = vlrissitorc;
		this.vlrsimplesitorc = vlrsimplesitorc;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public VdprevtribitorcId getId() {
		return this.id;
	}

	public void setId(VdprevtribitorcId id) {
		this.id = id;
	}

	public Vditorcamento getVditorcamento() {
		return this.vditorcamento;
	}

	public void setVditorcamento(Vditorcamento vditorcamento) {
		this.vditorcamento = vditorcamento;
	}

	public BigDecimal getVlricmsitorc() {
		return this.vlricmsitorc;
	}

	public void setVlricmsitorc(BigDecimal vlricmsitorc) {
		this.vlricmsitorc = vlricmsitorc;
	}

	public BigDecimal getVlripiitorc() {
		return this.vlripiitorc;
	}

	public void setVlripiitorc(BigDecimal vlripiitorc) {
		this.vlripiitorc = vlripiitorc;
	}

	public BigDecimal getVlrpisitorc() {
		return this.vlrpisitorc;
	}

	public void setVlrpisitorc(BigDecimal vlrpisitorc) {
		this.vlrpisitorc = vlrpisitorc;
	}

	public BigDecimal getVlrcofinsitorc() {
		return this.vlrcofinsitorc;
	}

	public void setVlrcofinsitorc(BigDecimal vlrcofinsitorc) {
		this.vlrcofinsitorc = vlrcofinsitorc;
	}

	public BigDecimal getVlriritorc() {
		return this.vlriritorc;
	}

	public void setVlriritorc(BigDecimal vlriritorc) {
		this.vlriritorc = vlriritorc;
	}

	public BigDecimal getVlrcsocialitorc() {
		return this.vlrcsocialitorc;
	}

	public void setVlrcsocialitorc(BigDecimal vlrcsocialitorc) {
		this.vlrcsocialitorc = vlrcsocialitorc;
	}

	public BigDecimal getVlrissitorc() {
		return this.vlrissitorc;
	}

	public void setVlrissitorc(BigDecimal vlrissitorc) {
		this.vlrissitorc = vlrissitorc;
	}

	public BigDecimal getVlrsimplesitorc() {
		return this.vlrsimplesitorc;
	}

	public void setVlrsimplesitorc(BigDecimal vlrsimplesitorc) {
		this.vlrsimplesitorc = vlrsimplesitorc;
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
