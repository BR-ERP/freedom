package org.freedom.persist.entity;

// Generated 30/01/2013 08:30:43 by Hibernate Tools 3.4.0.CR1

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
 * Attipoatendo generated by hbm2java
 */
@Entity
@Table(name = "ATTIPOATENDO")
public class Attipoatendo implements java.io.Serializable {

	private AttipoatendoId id;
	private Sgfilial sgfilial;
	private Sgfluxo sgfluxo;
	private String desctpatendo;
	private char tipoatendo;
	private char ativoatendo;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set<Sgprefere2> sgprefere2sForSgprefere2fk2attip = new HashSet<Sgprefere2>(
			0);
	private Set<Sgprefere2> sgprefere2sForSgprefere2fkattipo = new HashSet<Sgprefere2>(
			0);
	private Set<Atmodatendo> atmodatendos = new HashSet<Atmodatendo>(0);
	private Set<Sgprefere2> sgprefere2sForSgprefere2fk3attip = new HashSet<Sgprefere2>(
			0);
	private Set<Attipoatendosetor> attipoatendosetors = new HashSet<Attipoatendosetor>(
			0);
	private Set<Atatendimento> atatendimentos = new HashSet<Atatendimento>(0);

	public Attipoatendo() {
	}

	public Attipoatendo(AttipoatendoId id, Sgfilial sgfilial,
			String desctpatendo, char tipoatendo, char ativoatendo, Date dtins,
			Date hins, String idusuins) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.desctpatendo = desctpatendo;
		this.tipoatendo = tipoatendo;
		this.ativoatendo = ativoatendo;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Attipoatendo(AttipoatendoId id, Sgfilial sgfilial, Sgfluxo sgfluxo,
			String desctpatendo, char tipoatendo, char ativoatendo, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt,
			Set<Sgprefere2> sgprefere2sForSgprefere2fk2attip,
			Set<Sgprefere2> sgprefere2sForSgprefere2fkattipo,
			Set<Atmodatendo> atmodatendos,
			Set<Sgprefere2> sgprefere2sForSgprefere2fk3attip,
			Set<Attipoatendosetor> attipoatendosetors,
			Set<Atatendimento> atatendimentos) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.sgfluxo = sgfluxo;
		this.desctpatendo = desctpatendo;
		this.tipoatendo = tipoatendo;
		this.ativoatendo = ativoatendo;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.sgprefere2sForSgprefere2fk2attip = sgprefere2sForSgprefere2fk2attip;
		this.sgprefere2sForSgprefere2fkattipo = sgprefere2sForSgprefere2fkattipo;
		this.atmodatendos = atmodatendos;
		this.sgprefere2sForSgprefere2fk3attip = sgprefere2sForSgprefere2fk3attip;
		this.attipoatendosetors = attipoatendosetors;
		this.atatendimentos = atatendimentos;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codtpatendo", column = @Column(name = "CODTPATENDO", nullable = false)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public AttipoatendoId getId() {
		return this.id;
	}

	public void setId(AttipoatendoId id) {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODFLUXO", referencedColumnName = "CODFLUXO"),
			@JoinColumn(name = "CODFILIALFX", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPFX", referencedColumnName = "CODEMP") })
	public Sgfluxo getSgfluxo() {
		return this.sgfluxo;
	}

	public void setSgfluxo(Sgfluxo sgfluxo) {
		this.sgfluxo = sgfluxo;
	}

	@Column(name = "DESCTPATENDO", nullable = false, length = 50)
	public String getDesctpatendo() {
		return this.desctpatendo;
	}

	public void setDesctpatendo(String desctpatendo) {
		this.desctpatendo = desctpatendo;
	}

	@Column(name = "TIPOATENDO", nullable = false, length = 1)
	public char getTipoatendo() {
		return this.tipoatendo;
	}

	public void setTipoatendo(char tipoatendo) {
		this.tipoatendo = tipoatendo;
	}

	@Column(name = "ATIVOATENDO", nullable = false, length = 1)
	public char getAtivoatendo() {
		return this.ativoatendo;
	}

	public void setAtivoatendo(char ativoatendo) {
		this.ativoatendo = ativoatendo;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "attipoatendoBySgprefere2fk2attip")
	public Set<Sgprefere2> getSgprefere2sForSgprefere2fk2attip() {
		return this.sgprefere2sForSgprefere2fk2attip;
	}

	public void setSgprefere2sForSgprefere2fk2attip(
			Set<Sgprefere2> sgprefere2sForSgprefere2fk2attip) {
		this.sgprefere2sForSgprefere2fk2attip = sgprefere2sForSgprefere2fk2attip;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "attipoatendoBySgprefere2fkattipo")
	public Set<Sgprefere2> getSgprefere2sForSgprefere2fkattipo() {
		return this.sgprefere2sForSgprefere2fkattipo;
	}

	public void setSgprefere2sForSgprefere2fkattipo(
			Set<Sgprefere2> sgprefere2sForSgprefere2fkattipo) {
		this.sgprefere2sForSgprefere2fkattipo = sgprefere2sForSgprefere2fkattipo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "attipoatendo")
	public Set<Atmodatendo> getAtmodatendos() {
		return this.atmodatendos;
	}

	public void setAtmodatendos(Set<Atmodatendo> atmodatendos) {
		this.atmodatendos = atmodatendos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "attipoatendoBySgprefere2fk3attip")
	public Set<Sgprefere2> getSgprefere2sForSgprefere2fk3attip() {
		return this.sgprefere2sForSgprefere2fk3attip;
	}

	public void setSgprefere2sForSgprefere2fk3attip(
			Set<Sgprefere2> sgprefere2sForSgprefere2fk3attip) {
		this.sgprefere2sForSgprefere2fk3attip = sgprefere2sForSgprefere2fk3attip;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "attipoatendo")
	public Set<Attipoatendosetor> getAttipoatendosetors() {
		return this.attipoatendosetors;
	}

	public void setAttipoatendosetors(Set<Attipoatendosetor> attipoatendosetors) {
		this.attipoatendosetors = attipoatendosetors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "attipoatendo")
	public Set<Atatendimento> getAtatendimentos() {
		return this.atatendimentos;
	}

	public void setAtatendimentos(Set<Atatendimento> atatendimentos) {
		this.atatendimentos = atatendimentos;
	}

}