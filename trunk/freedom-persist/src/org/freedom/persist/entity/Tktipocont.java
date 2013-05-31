package org.freedom.persist.entity;

// Generated 31/05/2013 12:00:37 by Hibernate Tools 3.4.0.CR1

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
 * Tktipocont generated by hbm2java
 */
@Entity
@Table(name = "TKTIPOCONT")
public class Tktipocont implements java.io.Serializable {

	private TktipocontId id;
	private Sgfilial sgfilial;
	private String desctipocont;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set<Sgprefere3> sgprefere3sForSgprefere3fktktip1 = new HashSet<Sgprefere3>(
			0);
	private Set<Sgprefere3> sgprefere3sForSgprefere3fktktip2 = new HashSet<Sgprefere3>(
			0);
	private Set<Tkcontato> tkcontatos = new HashSet<Tkcontato>(0);

	public Tktipocont() {
	}

	public Tktipocont(TktipocontId id, Sgfilial sgfilial, Date dtins,
			Date hins, String idusuins) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Tktipocont(TktipocontId id, Sgfilial sgfilial, String desctipocont,
			Date dtins, Date hins, String idusuins, Date dtalt, Date halt,
			String idusualt, Set<Sgprefere3> sgprefere3sForSgprefere3fktktip1,
			Set<Sgprefere3> sgprefere3sForSgprefere3fktktip2,
			Set<Tkcontato> tkcontatos) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.desctipocont = desctipocont;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.sgprefere3sForSgprefere3fktktip1 = sgprefere3sForSgprefere3fktktip1;
		this.sgprefere3sForSgprefere3fktktip2 = sgprefere3sForSgprefere3fktktip2;
		this.tkcontatos = tkcontatos;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codtipocont", column = @Column(name = "CODTIPOCONT", nullable = false)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public TktipocontId getId() {
		return this.id;
	}

	public void setId(TktipocontId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODFILIAL", referencedColumnName = "CODFILIAL", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "CODEMP", referencedColumnName = "CODEMP", nullable = false, insertable = false, updatable = false) })
	public Sgfilial getSgfilial() {
		return this.sgfilial;
	}

	public void setSgfilial(Sgfilial sgfilial) {
		this.sgfilial = sgfilial;
	}

	@Column(name = "DESCTIPOCONT", length = 40)
	public String getDesctipocont() {
		return this.desctipocont;
	}

	public void setDesctipocont(String desctipocont) {
		this.desctipocont = desctipocont;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tktipocontBySgprefere3fktktip1")
	public Set<Sgprefere3> getSgprefere3sForSgprefere3fktktip1() {
		return this.sgprefere3sForSgprefere3fktktip1;
	}

	public void setSgprefere3sForSgprefere3fktktip1(
			Set<Sgprefere3> sgprefere3sForSgprefere3fktktip1) {
		this.sgprefere3sForSgprefere3fktktip1 = sgprefere3sForSgprefere3fktktip1;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tktipocontBySgprefere3fktktip2")
	public Set<Sgprefere3> getSgprefere3sForSgprefere3fktktip2() {
		return this.sgprefere3sForSgprefere3fktktip2;
	}

	public void setSgprefere3sForSgprefere3fktktip2(
			Set<Sgprefere3> sgprefere3sForSgprefere3fktktip2) {
		this.sgprefere3sForSgprefere3fktktip2 = sgprefere3sForSgprefere3fktktip2;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tktipocont")
	public Set<Tkcontato> getTkcontatos() {
		return this.tkcontatos;
	}

	public void setTkcontatos(Set<Tkcontato> tkcontatos) {
		this.tkcontatos = tkcontatos;
	}

}
