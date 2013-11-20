package org.freedom.persist.entity;

// Generated 20/11/2013 10:59:59 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * Eqmovserie generated by hbm2java
 */
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

	public EqmovserieId getId() {
		return this.id;
	}

	public void setId(EqmovserieId id) {
		this.id = id;
	}

	public Eqrecmerc getEqrecmerc() {
		return this.eqrecmerc;
	}

	public void setEqrecmerc(Eqrecmerc eqrecmerc) {
		this.eqrecmerc = eqrecmerc;
	}

	public Sgfilial getSgfilial() {
		return this.sgfilial;
	}

	public void setSgfilial(Sgfilial sgfilial) {
		this.sgfilial = sgfilial;
	}

	public Ppop getPpop() {
		return this.ppop;
	}

	public void setPpop(Ppop ppop) {
		this.ppop = ppop;
	}

	public Eqserie getEqserie() {
		return this.eqserie;
	}

	public void setEqserie(Eqserie eqserie) {
		this.eqserie = eqserie;
	}

	public Eqtipomov getEqtipomov() {
		return this.eqtipomov;
	}

	public void setEqtipomov(Eqtipomov eqtipomov) {
		this.eqtipomov = eqtipomov;
	}

	public Cpitcompra getCpitcompra() {
		return this.cpitcompra;
	}

	public void setCpitcompra(Cpitcompra cpitcompra) {
		this.cpitcompra = cpitcompra;
	}

	public Vditvenda getVditvenda() {
		return this.vditvenda;
	}

	public void setVditvenda(Vditvenda vditvenda) {
		this.vditvenda = vditvenda;
	}

	public Eqitrecmerc getEqitrecmerc() {
		return this.eqitrecmerc;
	}

	public void setEqitrecmerc(Eqitrecmerc eqitrecmerc) {
		this.eqitrecmerc = eqitrecmerc;
	}

	public Eqitrma getEqitrma() {
		return this.eqitrma;
	}

	public void setEqitrma(Eqitrma eqitrma) {
		this.eqitrma = eqitrma;
	}

	public Eqinvprod getEqinvprod() {
		return this.eqinvprod;
	}

	public void setEqinvprod(Eqinvprod eqinvprod) {
		this.eqinvprod = eqinvprod;
	}

	public Date getDtmovserie() {
		return this.dtmovserie;
	}

	public void setDtmovserie(Date dtmovserie) {
		this.dtmovserie = dtmovserie;
	}

	public short getTipomovserie() {
		return this.tipomovserie;
	}

	public void setTipomovserie(short tipomovserie) {
		this.tipomovserie = tipomovserie;
	}

	public Integer getDocmovserie() {
		return this.docmovserie;
	}

	public void setDocmovserie(Integer docmovserie) {
		this.docmovserie = docmovserie;
	}

	public String getCodlote() {
		return this.codlote;
	}

	public void setCodlote(String codlote) {
		this.codlote = codlote;
	}

	public Integer getCodemple() {
		return this.codemple;
	}

	public void setCodemple(Integer codemple) {
		this.codemple = codemple;
	}

	public Short getCodfilialle() {
		return this.codfilialle;
	}

	public void setCodfilialle(Short codfilialle) {
		this.codfilialle = codfilialle;
	}

	public Integer getCodalmox() {
		return this.codalmox;
	}

	public void setCodalmox(Integer codalmox) {
		this.codalmox = codalmox;
	}

	public Integer getCodempax() {
		return this.codempax;
	}

	public void setCodempax(Integer codempax) {
		this.codempax = codempax;
	}

	public Short getCodfilialax() {
		return this.codfilialax;
	}

	public void setCodfilialax(Short codfilialax) {
		this.codfilialax = codfilialax;
	}

	public String getIdusuins() {
		return this.idusuins;
	}

	public void setIdusuins(String idusuins) {
		this.idusuins = idusuins;
	}

	public Date getDtins() {
		return this.dtins;
	}

	public void setDtins(Date dtins) {
		this.dtins = dtins;
	}

	public Date getHins() {
		return this.hins;
	}

	public void setHins(Date hins) {
		this.hins = hins;
	}

	public String getIdusualt() {
		return this.idusualt;
	}

	public void setIdusualt(String idusualt) {
		this.idusualt = idusualt;
	}

	public Date getHalt() {
		return this.halt;
	}

	public void setHalt(Date halt) {
		this.halt = halt;
	}

	public Date getDtalt() {
		return this.dtalt;
	}

	public void setDtalt(Date dtalt) {
		this.dtalt = dtalt;
	}

}
