package org.freedom.persist.entity;

// Generated 31/05/2013 12:00:37 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Rhtabelairrf generated by hbm2java
 */
@Entity
@Table(name = "RHTABELAIRRF")
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

	@Id
	@Column(name = "CODTABIRRF", unique = true, nullable = false)
	public int getCodtabirrf() {
		return this.codtabirrf;
	}

	public void setCodtabirrf(int codtabirrf) {
		this.codtabirrf = codtabirrf;
	}

	@Column(name = "TETO", nullable = false, precision = 15, scale = 5)
	public BigDecimal getTeto() {
		return this.teto;
	}

	public void setTeto(BigDecimal teto) {
		this.teto = teto;
	}

	@Column(name = "ALIQUOTA", nullable = false, precision = 15, scale = 5)
	public BigDecimal getAliquota() {
		return this.aliquota;
	}

	public void setAliquota(BigDecimal aliquota) {
		this.aliquota = aliquota;
	}

	@Column(name = "REDUCAODEPENDENTE", nullable = false, precision = 15, scale = 5)
	public BigDecimal getReducaodependente() {
		return this.reducaodependente;
	}

	public void setReducaodependente(BigDecimal reducaodependente) {
		this.reducaodependente = reducaodependente;
	}

	@Column(name = "DEDUCAO", nullable = false, precision = 15, scale = 5)
	public BigDecimal getDeducao() {
		return this.deducao;
	}

	public void setDeducao(BigDecimal deducao) {
		this.deducao = deducao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTINS", nullable = false, length = 10)
	public Date getDtins() {
		return this.dtins;
	}

	public void setDtins(Date dtins) {
		this.dtins = dtins;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "HINS", nullable = false, length = 8)
	public Date getHins() {
		return this.hins;
	}

	public void setHins(Date hins) {
		this.hins = hins;
	}

	@Column(name = "IDUSUINS", nullable = false, length = 8)
	public String getIdusuins() {
		return this.idusuins;
	}

	public void setIdusuins(String idusuins) {
		this.idusuins = idusuins;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTALT", length = 10)
	public Date getDtalt() {
		return this.dtalt;
	}

	public void setDtalt(Date dtalt) {
		this.dtalt = dtalt;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "HALT", length = 8)
	public Date getHalt() {
		return this.halt;
	}

	public void setHalt(Date halt) {
		this.halt = halt;
	}

	@Column(name = "IDUSUALT", length = 8)
	public String getIdusualt() {
		return this.idusualt;
	}

	public void setIdusualt(String idusualt) {
		this.idusualt = idusualt;
	}

}
