package org.freedom.persist.entity;

// Generated 31/05/2013 12:00:37 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Vdkit generated by hbm2java
 */
@Entity
@Table(name = "VDKIT")
public class Vdkit implements java.io.Serializable {

	private VdkitId id;
	private Eqproduto eqproduto;
	private String refprod;
	private BigDecimal qtdkit;
	private BigDecimal precokit;
	private BigDecimal vlrtotkit;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set<Vditkit> vditkits = new HashSet<Vditkit>(0);

	public Vdkit() {
	}

	public Vdkit(VdkitId id, Eqproduto eqproduto, String refprod,
			BigDecimal qtdkit, BigDecimal precokit, BigDecimal vlrtotkit,
			Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.eqproduto = eqproduto;
		this.refprod = refprod;
		this.qtdkit = qtdkit;
		this.precokit = precokit;
		this.vlrtotkit = vlrtotkit;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Vdkit(VdkitId id, Eqproduto eqproduto, String refprod,
			BigDecimal qtdkit, BigDecimal precokit, BigDecimal vlrtotkit,
			Date dtins, Date hins, String idusuins, Date dtalt, Date halt,
			String idusualt, Set<Vditkit> vditkits) {
		this.id = id;
		this.eqproduto = eqproduto;
		this.refprod = refprod;
		this.qtdkit = qtdkit;
		this.precokit = precokit;
		this.vlrtotkit = vlrtotkit;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.vditkits = vditkits;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codkit", column = @Column(name = "CODKIT", nullable = false)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public VdkitId getId() {
		return this.id;
	}

	public void setId(VdkitId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODPROD", referencedColumnName = "CODPROD", nullable = false),
			@JoinColumn(name = "CODFILIALPD", referencedColumnName = "CODFILIAL", nullable = false),
			@JoinColumn(name = "CODEMPPD", referencedColumnName = "CODEMP", nullable = false) })
	public Eqproduto getEqproduto() {
		return this.eqproduto;
	}

	public void setEqproduto(Eqproduto eqproduto) {
		this.eqproduto = eqproduto;
	}

	@Column(name = "REFPROD", nullable = false, length = 20)
	public String getRefprod() {
		return this.refprod;
	}

	public void setRefprod(String refprod) {
		this.refprod = refprod;
	}

	@Column(name = "QTDKIT", nullable = false, precision = 15, scale = 5)
	public BigDecimal getQtdkit() {
		return this.qtdkit;
	}

	public void setQtdkit(BigDecimal qtdkit) {
		this.qtdkit = qtdkit;
	}

	@Column(name = "PRECOKIT", nullable = false, precision = 15, scale = 5)
	public BigDecimal getPrecokit() {
		return this.precokit;
	}

	public void setPrecokit(BigDecimal precokit) {
		this.precokit = precokit;
	}

	@Column(name = "VLRTOTKIT", nullable = false, precision = 15, scale = 5)
	public BigDecimal getVlrtotkit() {
		return this.vlrtotkit;
	}

	public void setVlrtotkit(BigDecimal vlrtotkit) {
		this.vlrtotkit = vlrtotkit;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vdkit")
	public Set<Vditkit> getVditkits() {
		return this.vditkits;
	}

	public void setVditkits(Set<Vditkit> vditkits) {
		this.vditkits = vditkits;
	}

}
