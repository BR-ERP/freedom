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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Tksetorcto generated by hbm2java
 */
@Entity
@Table(name = "TKSETORCTO")
public class Tksetorcto implements java.io.Serializable {

	private TksetorctoId id;
	private String descsetorcto;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set<Tkcontato> tkcontatos = new HashSet<Tkcontato>(0);

	public Tksetorcto() {
	}

	public Tksetorcto(TksetorctoId id, String descsetorcto, Date dtins,
			Date hins, String idusuins) {
		this.id = id;
		this.descsetorcto = descsetorcto;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Tksetorcto(TksetorctoId id, String descsetorcto, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt,
			Set<Tkcontato> tkcontatos) {
		this.id = id;
		this.descsetorcto = descsetorcto;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.tkcontatos = tkcontatos;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codsetorcto", column = @Column(name = "CODSETORCTO", nullable = false)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public TksetorctoId getId() {
		return this.id;
	}

	public void setId(TksetorctoId id) {
		this.id = id;
	}

	@Column(name = "DESCSETORCTO", nullable = false, length = 50)
	public String getDescsetorcto() {
		return this.descsetorcto;
	}

	public void setDescsetorcto(String descsetorcto) {
		this.descsetorcto = descsetorcto;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tksetorcto")
	public Set<Tkcontato> getTkcontatos() {
		return this.tkcontatos;
	}

	public void setTkcontatos(Set<Tkcontato> tkcontatos) {
		this.tkcontatos = tkcontatos;
	}

}
