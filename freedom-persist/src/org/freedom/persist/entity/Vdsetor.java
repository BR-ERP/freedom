package org.freedom.persist.entity;

// Generated 20/11/2013 10:59:59 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Vdsetor generated by hbm2java
 */
public class Vdsetor implements java.io.Serializable {

	private VdsetorId id;
	private Sgfilial sgfilial;
	private String descsetor;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set vdclientes = new HashSet(0);
	private Set sgprefere3s = new HashSet(0);
	private Set vdvendedors = new HashSet(0);
	private Set tkcontatos = new HashSet(0);

	public Vdsetor() {
	}

	public Vdsetor(VdsetorId id, Sgfilial sgfilial, String descsetor,
			Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.descsetor = descsetor;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Vdsetor(VdsetorId id, Sgfilial sgfilial, String descsetor,
			Date dtins, Date hins, String idusuins, Date dtalt, Date halt,
			String idusualt, Set vdclientes, Set sgprefere3s, Set vdvendedors,
			Set tkcontatos) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.descsetor = descsetor;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.vdclientes = vdclientes;
		this.sgprefere3s = sgprefere3s;
		this.vdvendedors = vdvendedors;
		this.tkcontatos = tkcontatos;
	}

	public VdsetorId getId() {
		return this.id;
	}

	public void setId(VdsetorId id) {
		this.id = id;
	}

	public Sgfilial getSgfilial() {
		return this.sgfilial;
	}

	public void setSgfilial(Sgfilial sgfilial) {
		this.sgfilial = sgfilial;
	}

	public String getDescsetor() {
		return this.descsetor;
	}

	public void setDescsetor(String descsetor) {
		this.descsetor = descsetor;
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

	public Set getVdclientes() {
		return this.vdclientes;
	}

	public void setVdclientes(Set vdclientes) {
		this.vdclientes = vdclientes;
	}

	public Set getSgprefere3s() {
		return this.sgprefere3s;
	}

	public void setSgprefere3s(Set sgprefere3s) {
		this.sgprefere3s = sgprefere3s;
	}

	public Set getVdvendedors() {
		return this.vdvendedors;
	}

	public void setVdvendedors(Set vdvendedors) {
		this.vdvendedors = vdvendedors;
	}

	public Set getTkcontatos() {
		return this.tkcontatos;
	}

	public void setTkcontatos(Set tkcontatos) {
		this.tkcontatos = tkcontatos;
	}

}
