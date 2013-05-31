package org.freedom.persist.entity;

// Generated 31/05/2013 12:00:37 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Cpequivcfop generated by hbm2java
 */
@Entity
@Table(name = "CPEQUIVCFOP")
public class Cpequivcfop implements java.io.Serializable {

	private CpequivcfopId id;
	private Lfnatoper lfnatoperByCpequivcfopfklfnan;
	private Lfnatoper lfnatoperByCpequivcfopfklfnat;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Cpequivcfop() {
	}

	public Cpequivcfop(Lfnatoper lfnatoperByCpequivcfopfklfnan,
			Lfnatoper lfnatoperByCpequivcfopfklfnat, Date dtins, Date hins,
			String idusuins) {
		this.lfnatoperByCpequivcfopfklfnan = lfnatoperByCpequivcfopfklfnan;
		this.lfnatoperByCpequivcfopfklfnat = lfnatoperByCpequivcfopfklfnat;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Cpequivcfop(Lfnatoper lfnatoperByCpequivcfopfklfnan,
			Lfnatoper lfnatoperByCpequivcfopfklfnat, Date dtins, Date hins,
			String idusuins, Date dtalt, Date halt, String idusualt) {
		this.lfnatoperByCpequivcfopfklfnan = lfnatoperByCpequivcfopfklfnan;
		this.lfnatoperByCpequivcfopfklfnat = lfnatoperByCpequivcfopfklfnat;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codnat", column = @Column(name = "CODNAT", nullable = false, length = 4)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public CpequivcfopId getId() {
		return this.id;
	}

	public void setId(CpequivcfopId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODNATND", referencedColumnName = "CODNAT", nullable = false),
			@JoinColumn(name = "CODFILIALND", referencedColumnName = "CODFILIAL", nullable = false),
			@JoinColumn(name = "CODEMPND", referencedColumnName = "CODEMP", nullable = false) })
	public Lfnatoper getLfnatoperByCpequivcfopfklfnan() {
		return this.lfnatoperByCpequivcfopfklfnan;
	}

	public void setLfnatoperByCpequivcfopfklfnan(
			Lfnatoper lfnatoperByCpequivcfopfklfnan) {
		this.lfnatoperByCpequivcfopfklfnan = lfnatoperByCpequivcfopfklfnan;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Lfnatoper getLfnatoperByCpequivcfopfklfnat() {
		return this.lfnatoperByCpequivcfopfklfnat;
	}

	public void setLfnatoperByCpequivcfopfklfnat(
			Lfnatoper lfnatoperByCpequivcfopfklfnat) {
		this.lfnatoperByCpequivcfopfklfnat = lfnatoperByCpequivcfopfklfnat;
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
