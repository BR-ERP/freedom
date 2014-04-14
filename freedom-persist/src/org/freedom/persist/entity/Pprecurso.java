package org.freedom.persist.entity;

// Generated 14/04/2014 10:17:08 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Pprecurso generated by hbm2java
 */
public class Pprecurso implements java.io.Serializable {

	private PprecursoId id;
	private Sgfilial sgfilial;
	private Pptiporec pptiporec;
	private String descrecp;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set ppopfases = new HashSet(0);

	public Pprecurso() {
	}

	public Pprecurso(PprecursoId id, Sgfilial sgfilial, Pptiporec pptiporec,
			String descrecp, Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.pptiporec = pptiporec;
		this.descrecp = descrecp;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Pprecurso(PprecursoId id, Sgfilial sgfilial, Pptiporec pptiporec,
			String descrecp, Date dtins, Date hins, String idusuins,
			Date dtalt, Date halt, String idusualt, Set ppopfases) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.pptiporec = pptiporec;
		this.descrecp = descrecp;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.ppopfases = ppopfases;
	}

	public PprecursoId getId() {
		return this.id;
	}

	public void setId(PprecursoId id) {
		this.id = id;
	}

	public Sgfilial getSgfilial() {
		return this.sgfilial;
	}

	public void setSgfilial(Sgfilial sgfilial) {
		this.sgfilial = sgfilial;
	}

	public Pptiporec getPptiporec() {
		return this.pptiporec;
	}

	public void setPptiporec(Pptiporec pptiporec) {
		this.pptiporec = pptiporec;
	}

	public String getDescrecp() {
		return this.descrecp;
	}

	public void setDescrecp(String descrecp) {
		this.descrecp = descrecp;
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

	public Set getPpopfases() {
		return this.ppopfases;
	}

	public void setPpopfases(Set ppopfases) {
		this.ppopfases = ppopfases;
	}

}
