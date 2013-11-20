package org.freedom.persist.entity;

// Generated 20/11/2013 10:59:59 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;

/**
 * Rhtabelairrf generated by hbm2java
 */
public class Rhtabelairrf implements java.io.Serializable {

	private int codtabirrf;
	private BigDecimal teto;
	private BigDecimal aliquota;
	private BigDecimal reducaodependente;
	private BigDecimal deducao;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Rhtabelairrf() {
	}

	public Rhtabelairrf(int codtabirrf, BigDecimal teto, BigDecimal aliquota,
			BigDecimal reducaodependente, BigDecimal deducao, Date dtins,
			Date hins, String idusuins) {
		this.codtabirrf = codtabirrf;
		this.teto = teto;
		this.aliquota = aliquota;
		this.reducaodependente = reducaodependente;
		this.deducao = deducao;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Rhtabelairrf(int codtabirrf, BigDecimal teto, BigDecimal aliquota,
			BigDecimal reducaodependente, BigDecimal deducao, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt) {
		this.codtabirrf = codtabirrf;
		this.teto = teto;
		this.aliquota = aliquota;
		this.reducaodependente = reducaodependente;
		this.deducao = deducao;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public int getCodtabirrf() {
		return this.codtabirrf;
	}

	public void setCodtabirrf(int codtabirrf) {
		this.codtabirrf = codtabirrf;
	}

	public BigDecimal getTeto() {
		return this.teto;
	}

	public void setTeto(BigDecimal teto) {
		this.teto = teto;
	}

	public BigDecimal getAliquota() {
		return this.aliquota;
	}

	public void setAliquota(BigDecimal aliquota) {
		this.aliquota = aliquota;
	}

	public BigDecimal getReducaodependente() {
		return this.reducaodependente;
	}

	public void setReducaodependente(BigDecimal reducaodependente) {
		this.reducaodependente = reducaodependente;
	}

	public BigDecimal getDeducao() {
		return this.deducao;
	}

	public void setDeducao(BigDecimal deducao) {
		this.deducao = deducao;
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
