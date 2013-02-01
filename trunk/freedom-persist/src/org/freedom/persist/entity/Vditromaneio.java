package org.freedom.persist.entity;

// Generated 30/01/2013 08:30:43 by Hibernate Tools 3.4.0.CR1

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Vditromaneio generated by hbm2java
 */
@Entity
@Table(name = "VDITROMANEIO")
public class Vditromaneio implements java.io.Serializable {

	private VditromaneioId id;
	private Vdromaneio vdromaneio;
	private Vdvenda vdvenda;
	private Date dtprevitroma;
	private Character flag;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Vditromaneio() {
	}

	public Vditromaneio(VditromaneioId id, Vdromaneio vdromaneio,
			Vdvenda vdvenda, Date dtprevitroma, Date dtins, Date hins,
			String idusuins) {
		this.id = id;
		this.vdromaneio = vdromaneio;
		this.vdvenda = vdvenda;
		this.dtprevitroma = dtprevitroma;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Vditromaneio(VditromaneioId id, Vdromaneio vdromaneio,
			Vdvenda vdvenda, Date dtprevitroma, Character flag, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt) {
		this.id = id;
		this.vdromaneio = vdromaneio;
		this.vdvenda = vdvenda;
		this.dtprevitroma = dtprevitroma;
		this.flag = flag;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "coditroma", column = @Column(name = "CODITROMA", nullable = false)),
			@AttributeOverride(name = "codroma", column = @Column(name = "CODROMA", nullable = false)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public VditromaneioId getId() {
		return this.id;
	}

	public void setId(VditromaneioId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODROMA", referencedColumnName = "CODROMA", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "CODFILIAL", referencedColumnName = "CODFILIAL", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "CODEMP", referencedColumnName = "CODEMP", nullable = false, insertable = false, updatable = false) })
	public Vdromaneio getVdromaneio() {
		return this.vdromaneio;
	}

	public void setVdromaneio(Vdromaneio vdromaneio) {
		this.vdromaneio = vdromaneio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODVENDA", referencedColumnName = "CODVENDA", nullable = false),
			@JoinColumn(name = "TIPOVENDA", referencedColumnName = "TIPOVENDA", nullable = false),
			@JoinColumn(name = "CODFILIALVA", referencedColumnName = "CODFILIAL", nullable = false),
			@JoinColumn(name = "CODEMPVA", referencedColumnName = "CODEMP", nullable = false) })
	public Vdvenda getVdvenda() {
		return this.vdvenda;
	}

	public void setVdvenda(Vdvenda vdvenda) {
		this.vdvenda = vdvenda;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTPREVITROMA", nullable = false, length = 10)
	public Date getDtprevitroma() {
		return this.dtprevitroma;
	}

	public void setDtprevitroma(Date dtprevitroma) {
		this.dtprevitroma = dtprevitroma;
	}

	@Column(name = "FLAG", length = 1)
	public Character getFlag() {
		return this.flag;
	}

	public void setFlag(Character flag) {
		this.flag = flag;
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