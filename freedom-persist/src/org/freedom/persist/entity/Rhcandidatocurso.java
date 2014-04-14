package org.freedom.persist.entity;

// Generated 14/04/2014 10:17:08 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * Rhcandidatocurso generated by hbm2java
 */
public class Rhcandidatocurso implements java.io.Serializable {

	private RhcandidatocursoId id;
	private Rhcandidato rhcandidato;
	private Rhcurso rhcurso;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Rhcandidatocurso() {
	}

	public Rhcandidatocurso(RhcandidatocursoId id, Rhcandidato rhcandidato,
			Rhcurso rhcurso, Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.rhcandidato = rhcandidato;
		this.rhcurso = rhcurso;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Rhcandidatocurso(RhcandidatocursoId id, Rhcandidato rhcandidato,
			Rhcurso rhcurso, Date dtins, Date hins, String idusuins,
			Date dtalt, Date halt, String idusualt) {
		this.id = id;
		this.rhcandidato = rhcandidato;
		this.rhcurso = rhcurso;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public RhcandidatocursoId getId() {
		return this.id;
	}

	public void setId(RhcandidatocursoId id) {
		this.id = id;
	}

	public Rhcandidato getRhcandidato() {
		return this.rhcandidato;
	}

	public void setRhcandidato(Rhcandidato rhcandidato) {
		this.rhcandidato = rhcandidato;
	}

	public Rhcurso getRhcurso() {
		return this.rhcurso;
	}

	public void setRhcurso(Rhcurso rhcurso) {
		this.rhcurso = rhcurso;
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

}
