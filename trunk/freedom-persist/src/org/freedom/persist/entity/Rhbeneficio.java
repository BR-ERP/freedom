package org.freedom.persist.entity;

// Generated 30/01/2013 08:30:43 by Hibernate Tools 3.4.0.CR1

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Rhbeneficio generated by hbm2java
 */
@Entity
@Table(name = "RHBENEFICIO")
public class Rhbeneficio implements java.io.Serializable {

	private RhbeneficioId id;
	private String descbenef;
	private BigDecimal valorbenef;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set<Rhempregadobenef> rhempregadobenefs = new HashSet<Rhempregadobenef>(
			0);

	public Rhbeneficio() {
	}

	public Rhbeneficio(RhbeneficioId id, String descbenef, Date dtins,
			Date hins, String idusuins) {
		this.id = id;
		this.descbenef = descbenef;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Rhbeneficio(RhbeneficioId id, String descbenef,
			BigDecimal valorbenef, Date dtins, Date hins, String idusuins,
			Date dtalt, Date halt, String idusualt,
			Set<Rhempregadobenef> rhempregadobenefs) {
		this.id = id;
		this.descbenef = descbenef;
		this.valorbenef = valorbenef;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.rhempregadobenefs = rhempregadobenefs;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codbenef", column = @Column(name = "CODBENEF", nullable = false)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public RhbeneficioId getId() {
		return this.id;
	}

	public void setId(RhbeneficioId id) {
		this.id = id;
	}

	@Column(name = "DESCBENEF", nullable = false, length = 60)
	public String getDescbenef() {
		return this.descbenef;
	}

	public void setDescbenef(String descbenef) {
		this.descbenef = descbenef;
	}

	@Column(name = "VALORBENEF", precision = 15, scale = 5)
	public BigDecimal getValorbenef() {
		return this.valorbenef;
	}

	public void setValorbenef(BigDecimal valorbenef) {
		this.valorbenef = valorbenef;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rhbeneficio")
	public Set<Rhempregadobenef> getRhempregadobenefs() {
		return this.rhempregadobenefs;
	}

	public void setRhempregadobenefs(Set<Rhempregadobenef> rhempregadobenefs) {
		this.rhempregadobenefs = rhempregadobenefs;
	}

}