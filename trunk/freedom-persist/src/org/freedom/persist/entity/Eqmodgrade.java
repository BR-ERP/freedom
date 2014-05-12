package org.freedom.persist.entity;

// Generated 12/05/2014 09:11:34 by Hibernate Tools 4.0.0

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
 * Eqmodgrade generated by hbm2java
 */
@Entity
@Table(name = "EQMODGRADE")
public class Eqmodgrade implements java.io.Serializable {

	private EqmodgradeId id;
	private Eqproduto eqproduto;
	private Sgfilial sgfilial;
	private String descmodg;
	private String descprodmodg;
	private String desccompprodmodg;
	private String refmodg;
	private String codfabmodg;
	private String codbarmodg;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set eqitmodgrades = new HashSet(0);

	public Eqmodgrade() {
	}

	public Eqmodgrade(EqmodgradeId id, Eqproduto eqproduto, Sgfilial sgfilial,
			String descmodg, String descprodmodg, String refmodg,
			String codfabmodg, String codbarmodg, Date dtins, Date hins,
			String idusuins) {
		this.id = id;
		this.eqproduto = eqproduto;
		this.sgfilial = sgfilial;
		this.descmodg = descmodg;
		this.descprodmodg = descprodmodg;
		this.refmodg = refmodg;
		this.codfabmodg = codfabmodg;
		this.codbarmodg = codbarmodg;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Eqmodgrade(EqmodgradeId id, Eqproduto eqproduto, Sgfilial sgfilial,
			String descmodg, String descprodmodg, String desccompprodmodg,
			String refmodg, String codfabmodg, String codbarmodg, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt,
			Set eqitmodgrades) {
		this.id = id;
		this.eqproduto = eqproduto;
		this.sgfilial = sgfilial;
		this.descmodg = descmodg;
		this.descprodmodg = descprodmodg;
		this.desccompprodmodg = desccompprodmodg;
		this.refmodg = refmodg;
		this.codfabmodg = codfabmodg;
		this.codbarmodg = codbarmodg;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.eqitmodgrades = eqitmodgrades;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codmodg", column = @Column(name = "CODMODG", nullable = false)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public EqmodgradeId getId() {
		return this.id;
	}

	public void setId(EqmodgradeId id) {
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

	@Column(name = "DESCMODG", nullable = false, length = 40)
	public String getDescmodg() {
		return this.descmodg;
	}

	public void setDescmodg(String descmodg) {
		this.descmodg = descmodg;
	}

	@Column(name = "DESCPRODMODG", nullable = false, length = 20)
	public String getDescprodmodg() {
		return this.descprodmodg;
	}

	public void setDescprodmodg(String descprodmodg) {
		this.descprodmodg = descprodmodg;
	}

	@Column(name = "DESCCOMPPRODMODG", length = 50)
	public String getDesccompprodmodg() {
		return this.desccompprodmodg;
	}

	public void setDesccompprodmodg(String desccompprodmodg) {
		this.desccompprodmodg = desccompprodmodg;
	}

	@Column(name = "REFMODG", nullable = false, length = 10)
	public String getRefmodg() {
		return this.refmodg;
	}

	public void setRefmodg(String refmodg) {
		this.refmodg = refmodg;
	}

	@Column(name = "CODFABMODG", nullable = false, length = 10)
	public String getCodfabmodg() {
		return this.codfabmodg;
	}

	public void setCodfabmodg(String codfabmodg) {
		this.codfabmodg = codfabmodg;
	}

	@Column(name = "CODBARMODG", nullable = false, length = 10)
	public String getCodbarmodg() {
		return this.codbarmodg;
	}

	public void setCodbarmodg(String codbarmodg) {
		this.codbarmodg = codbarmodg;
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

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "eqmodgrade")
	public Set getEqitmodgrades() {
		return this.eqitmodgrades;
	}

	public void setEqitmodgrades(Set eqitmodgrades) {
		this.eqitmodgrades = eqitmodgrades;
	}
*/
}
