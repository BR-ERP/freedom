package org.freedom.persist.entity;

// Generated 18/02/2014 15:53:28 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Rhvaga generated by hbm2java
 */
public class Rhvaga implements java.io.Serializable {

	private RhvagaId id;
	private Rhempregador rhempregador;
	private Rhturno rhturno;
	private Rhfuncao rhfuncao;
	private BigDecimal faixasalini;
	private BigDecimal faixasalfim;
	private String stvaga;
	private Date dtultst;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set rhvagastatuses = new HashSet(0);
	private Set rhvagacandidatos = new HashSet(0);
	private Set rhvagacaracrests = new HashSet(0);
	private Set rhvagacursos = new HashSet(0);
	private Set rhcandidatostatuses = new HashSet(0);
	private Set rhvagacaracqualis = new HashSet(0);

	public Rhvaga() {
	}

	public Rhvaga(RhvagaId id, Rhempregador rhempregador, Rhfuncao rhfuncao,
			BigDecimal faixasalini, BigDecimal faixasalfim, String stvaga,
			Date dtultst, Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.rhempregador = rhempregador;
		this.rhfuncao = rhfuncao;
		this.faixasalini = faixasalini;
		this.faixasalfim = faixasalfim;
		this.stvaga = stvaga;
		this.dtultst = dtultst;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Rhvaga(RhvagaId id, Rhempregador rhempregador, Rhturno rhturno,
			Rhfuncao rhfuncao, BigDecimal faixasalini, BigDecimal faixasalfim,
			String stvaga, Date dtultst, Date dtins, Date hins,
			String idusuins, Date dtalt, Date halt, String idusualt,
			Set rhvagastatuses, Set rhvagacandidatos, Set rhvagacaracrests,
			Set rhvagacursos, Set rhcandidatostatuses, Set rhvagacaracqualis) {
		this.id = id;
		this.rhempregador = rhempregador;
		this.rhturno = rhturno;
		this.rhfuncao = rhfuncao;
		this.faixasalini = faixasalini;
		this.faixasalfim = faixasalfim;
		this.stvaga = stvaga;
		this.dtultst = dtultst;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.rhvagastatuses = rhvagastatuses;
		this.rhvagacandidatos = rhvagacandidatos;
		this.rhvagacaracrests = rhvagacaracrests;
		this.rhvagacursos = rhvagacursos;
		this.rhcandidatostatuses = rhcandidatostatuses;
		this.rhvagacaracqualis = rhvagacaracqualis;
	}

	public RhvagaId getId() {
		return this.id;
	}

	public void setId(RhvagaId id) {
		this.id = id;
	}

	public Rhempregador getRhempregador() {
		return this.rhempregador;
	}

	public void setRhempregador(Rhempregador rhempregador) {
		this.rhempregador = rhempregador;
	}

	public Rhturno getRhturno() {
		return this.rhturno;
	}

	public void setRhturno(Rhturno rhturno) {
		this.rhturno = rhturno;
	}

	public Rhfuncao getRhfuncao() {
		return this.rhfuncao;
	}

	public void setRhfuncao(Rhfuncao rhfuncao) {
		this.rhfuncao = rhfuncao;
	}

	public BigDecimal getFaixasalini() {
		return this.faixasalini;
	}

	public void setFaixasalini(BigDecimal faixasalini) {
		this.faixasalini = faixasalini;
	}

	public BigDecimal getFaixasalfim() {
		return this.faixasalfim;
	}

	public void setFaixasalfim(BigDecimal faixasalfim) {
		this.faixasalfim = faixasalfim;
	}

	public String getStvaga() {
		return this.stvaga;
	}

	public void setStvaga(String stvaga) {
		this.stvaga = stvaga;
	}

	public Date getDtultst() {
		return this.dtultst;
	}

	public void setDtultst(Date dtultst) {
		this.dtultst = dtultst;
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

	public String getIdusuins() {
		return this.idusuins;
	}

	public void setIdusuins(String idusuins) {
		this.idusuins = idusuins;
	}

	public Date getDtalt() {
		return this.dtalt;
	}

	public void setDtalt(Date dtalt) {
		this.dtalt = dtalt;
	}

	public Date getHalt() {
		return this.halt;
	}

	public void setHalt(Date halt) {
		this.halt = halt;
	}

	public String getIdusualt() {
		return this.idusualt;
	}

	public void setIdusualt(String idusualt) {
		this.idusualt = idusualt;
	}

	public Set getRhvagastatuses() {
		return this.rhvagastatuses;
	}

	public void setRhvagastatuses(Set rhvagastatuses) {
		this.rhvagastatuses = rhvagastatuses;
	}

	public Set getRhvagacandidatos() {
		return this.rhvagacandidatos;
	}

	public void setRhvagacandidatos(Set rhvagacandidatos) {
		this.rhvagacandidatos = rhvagacandidatos;
	}

	public Set getRhvagacaracrests() {
		return this.rhvagacaracrests;
	}

	public void setRhvagacaracrests(Set rhvagacaracrests) {
		this.rhvagacaracrests = rhvagacaracrests;
	}

	public Set getRhvagacursos() {
		return this.rhvagacursos;
	}

	public void setRhvagacursos(Set rhvagacursos) {
		this.rhvagacursos = rhvagacursos;
	}

	public Set getRhcandidatostatuses() {
		return this.rhcandidatostatuses;
	}

	public void setRhcandidatostatuses(Set rhcandidatostatuses) {
		this.rhcandidatostatuses = rhcandidatostatuses;
	}

	public Set getRhvagacaracqualis() {
		return this.rhvagacaracqualis;
	}

	public void setRhvagacaracqualis(Set rhvagacaracqualis) {
		this.rhvagacaracqualis = rhvagacaracqualis;
	}

}
