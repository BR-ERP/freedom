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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Eqmovserie generated by hbm2java
 */
@Entity
@Table(name = "EQMOVSERIE")
public class Eqmovserie implements java.io.Serializable {

	private EqmovserieId id;
	private Eqrecmerc eqrecmerc;
	private Sgfilial sgfilial;
	private Ppop ppop;
	private Eqserie eqserie;
	private Eqtipomov eqtipomov;
	private Cpitcompra cpitcompra;
	private Vditvenda vditvenda;
	private Eqitrecmerc eqitrecmerc;
	private Eqitrma eqitrma;
	private Eqinvprod eqinvprod;
	private Date dtmovserie;
	private short tipomovserie;
	private Integer docmovserie;
	private String codlote;
	private Integer codemple;
	private Short codfilialle;
	private Integer codalmox;
	private Integer codempax;
	private Short codfilialax;
	private String idusuins;
	private Date dtins;
	private Date hins;
	private String idusualt;
	private Date halt;
	private Date dtalt;

	public Eqmovserie() {
	}

	public Eqmovserie(EqmovserieId id, Sgfilial sgfilial, Eqserie eqserie,
			Date dtmovserie, short tipomovserie, String idusuins, Date dtins,
			Date hins) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.eqserie = eqserie;
		this.dtmovserie = dtmovserie;
		this.tipomovserie = tipomovserie;
		this.idusuins = idusuins;
		this.dtins = dtins;
		this.hins = hins;
	}

	public Eqmovserie(EqmovserieId id, Eqrecmerc eqrecmerc, Sgfilial sgfilial,
			Ppop ppop, Eqserie eqserie, Eqtipomov eqtipomov,
			Cpitcompra cpitcompra, Vditvenda vditvenda,
			Eqitrecmerc eqitrecmerc, Eqitrma eqitrma, Eqinvprod eqinvprod,
			Date dtmovserie, short tipomovserie, Integer docmovserie,
			String codlote, Integer codemple, Short codfilialle,
			Integer codalmox, Integer codempax, Short codfilialax,
			String idusuins, Date dtins, Date hins, String idusualt, Date halt,
			Date dtalt) {
		this.id = id;
		this.eqrecmerc = eqrecmerc;
		this.sgfilial = sgfilial;
		this.ppop = ppop;
		this.eqserie = eqserie;
		this.eqtipomov = eqtipomov;
		this.cpitcompra = cpitcompra;
		this.vditvenda = vditvenda;
		this.eqitrecmerc = eqitrecmerc;
		this.eqitrma = eqitrma;
		this.eqinvprod = eqinvprod;
		this.dtmovserie = dtmovserie;
		this.tipomovserie = tipomovserie;
		this.docmovserie = docmovserie;
		this.codlote = codlote;
		this.codemple = codemple;
		this.codfilialle = codfilialle;
		this.codalmox = codalmox;
		this.codempax = codempax;
		this.codfilialax = codfilialax;
		this.idusuins = idusuins;
		this.dtins = dtins;
		this.hins = hins;
		this.idusualt = idusualt;
		this.halt = halt;
		this.dtalt = dtalt;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codmovserie", column = @Column(name = "CODMOVSERIE", nullable = false)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public EqmovserieId getId() {
		return this.id;
	}

	public void setId(EqmovserieId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "TICKET", referencedColumnName = "TICKET"),
			@JoinColumn(name = "CODFILIALRC", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPRC", referencedColumnName = "CODEMP") })
	public Eqrecmerc getEqrecmerc() {
		return this.eqrecmerc;
	}

	public void setEqrecmerc(Eqrecmerc eqrecmerc) {
		this.eqrecmerc = eqrecmerc;
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
			@JoinColumn(name = "CODOP", referencedColumnName = "CODOP"),
			@JoinColumn(name = "SEQOP", referencedColumnName = "SEQOP"),
			@JoinColumn(name = "CODFILIALOP", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPOP", referencedColumnName = "CODEMP") })
	public Ppop getPpop() {
		return this.ppop;
	}

	public void setPpop(Ppop ppop) {
		this.ppop = ppop;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "NUMSERIE", referencedColumnName = "NUMSERIE", nullable = false),
			@JoinColumn(name = "CODPROD", referencedColumnName = "CODPROD", nullable = false),
			@JoinColumn(name = "CODFILIALPD", referencedColumnName = "CODFILIAL", nullable = false),
			@JoinColumn(name = "CODEMPPD", referencedColumnName = "CODEMP", nullable = false) })
	public Eqserie getEqserie() {
		return this.eqserie;
	}

	public void setEqserie(Eqserie eqserie) {
		this.eqserie = eqserie;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODTIPOMOV", referencedColumnName = "CODTIPOMOV"),
			@JoinColumn(name = "CODFILIALTM", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPTM", referencedColumnName = "CODEMP") })
	public Eqtipomov getEqtipomov() {
		return this.eqtipomov;
	}

	public void setEqtipomov(Eqtipomov eqtipomov) {
		this.eqtipomov = eqtipomov;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODCOMPRA", referencedColumnName = "CODCOMPRA"),
			@JoinColumn(name = "CODITCOMPRA", referencedColumnName = "CODITCOMPRA"),
			@JoinColumn(name = "CODFILIALCP", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPCP", referencedColumnName = "CODEMP") })
	public Cpitcompra getCpitcompra() {
		return this.cpitcompra;
	}

	public void setCpitcompra(Cpitcompra cpitcompra) {
		this.cpitcompra = cpitcompra;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODVENDA", referencedColumnName = "CODVENDA"),
			@JoinColumn(name = "CODITVENDA", referencedColumnName = "CODITVENDA"),
			@JoinColumn(name = "TIPOVENDA", referencedColumnName = "TIPOVENDA"),
			@JoinColumn(name = "CODFILIALVD", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPVD", referencedColumnName = "CODEMP") })
	public Vditvenda getVditvenda() {
		return this.vditvenda;
	}

	public void setVditvenda(Vditvenda vditvenda) {
		this.vditvenda = vditvenda;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "TICKET", referencedColumnName = "TICKET", insertable = false, updatable = false),
			@JoinColumn(name = "CODITRECMERC", referencedColumnName = "CODITRECMERC", insertable = false, updatable = false),
			@JoinColumn(name = "CODFILIALRC", referencedColumnName = "CODFILIAL", insertable = false, updatable = false),
			@JoinColumn(name = "CODEMPRC", referencedColumnName = "CODEMP", insertable = false, updatable = false) })
	public Eqitrecmerc getEqitrecmerc() {
		return this.eqitrecmerc;
	}

	public void setEqitrecmerc(Eqitrecmerc eqitrecmerc) {
		this.eqitrecmerc = eqitrecmerc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODRMA", referencedColumnName = "CODRMA"),
			@JoinColumn(name = "CODITRMA", referencedColumnName = "CODITRMA"),
			@JoinColumn(name = "CODFILIALRM", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPRM", referencedColumnName = "CODEMP") })
	public Eqitrma getEqitrma() {
		return this.eqitrma;
	}

	public void setEqitrma(Eqitrma eqitrma) {
		this.eqitrma = eqitrma;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODINVPROD", referencedColumnName = "CODINVPROD"),
			@JoinColumn(name = "CODFILIALIV", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPIV", referencedColumnName = "CODEMP") })
	public Eqinvprod getEqinvprod() {
		return this.eqinvprod;
	}

	public void setEqinvprod(Eqinvprod eqinvprod) {
		this.eqinvprod = eqinvprod;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTMOVSERIE", nullable = false, length = 10)
	public Date getDtmovserie() {
		return this.dtmovserie;
	}

	public void setDtmovserie(Date dtmovserie) {
		this.dtmovserie = dtmovserie;
	}

	@Column(name = "TIPOMOVSERIE", nullable = false)
	public short getTipomovserie() {
		return this.tipomovserie;
	}

	public void setTipomovserie(short tipomovserie) {
		this.tipomovserie = tipomovserie;
	}

	@Column(name = "DOCMOVSERIE")
	public Integer getDocmovserie() {
		return this.docmovserie;
	}

	public void setDocmovserie(Integer docmovserie) {
		this.docmovserie = docmovserie;
	}

	@Column(name = "CODLOTE", length = 20)
	public String getCodlote() {
		return this.codlote;
	}

	public void setCodlote(String codlote) {
		this.codlote = codlote;
	}

	@Column(name = "CODEMPLE")
	public Integer getCodemple() {
		return this.codemple;
	}

	public void setCodemple(Integer codemple) {
		this.codemple = codemple;
	}

	@Column(name = "CODFILIALLE")
	public Short getCodfilialle() {
		return this.codfilialle;
	}

	public void setCodfilialle(Short codfilialle) {
		this.codfilialle = codfilialle;
	}

	@Column(name = "CODALMOX")
	public Integer getCodalmox() {
		return this.codalmox;
	}

	public void setCodalmox(Integer codalmox) {
		this.codalmox = codalmox;
	}

	@Column(name = "CODEMPAX")
	public Integer getCodempax() {
		return this.codempax;
	}

	public void setCodempax(Integer codempax) {
		this.codempax = codempax;
	}

	@Column(name = "CODFILIALAX")
	public Short getCodfilialax() {
		return this.codfilialax;
	}

	public void setCodfilialax(Short codfilialax) {
		this.codfilialax = codfilialax;
	}

	@Column(name = "IDUSUINS", nullable = false, length = 8)
	public String getIdusuins() {
		return this.idusuins;
	}

	public void setIdusuins(String idusuins) {
		this.idusuins = idusuins;
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

	@Column(name = "IDUSUALT", length = 8)
	public String getIdusualt() {
		return this.idusualt;
	}

	public void setIdusualt(String idusualt) {
		this.idusualt = idusualt;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "HALT", length = 8)
	public Date getHalt() {
		return this.halt;
	}

	public void setHalt(Date halt) {
		this.halt = halt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTALT", length = 10)
	public Date getDtalt() {
		return this.dtalt;
	}

	public void setDtalt(Date dtalt) {
		this.dtalt = dtalt;
	}

}
