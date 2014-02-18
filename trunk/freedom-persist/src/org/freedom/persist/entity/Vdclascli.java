package org.freedom.persist.entity;

// Generated 18/02/2014 15:53:28 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Vdclascli generated by hbm2java
 */
public class Vdclascli implements java.io.Serializable {

	private VdclascliId id;
	private Sgfilial sgfilial;
	private String descclascli;
	private String siglaclascli;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set vdprecoprods = new HashSet(0);
	private Set sgprefere2s = new HashSet(0);
	private Set sgprefere1s = new HashSet(0);
	private Set vdclientes = new HashSet(0);

	public Vdclascli() {
	}

	public Vdclascli(VdclascliId id, Sgfilial sgfilial, String descclascli,
			Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.descclascli = descclascli;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Vdclascli(VdclascliId id, Sgfilial sgfilial, String descclascli,
			String siglaclascli, Date dtins, Date hins, String idusuins,
			Date dtalt, Date halt, String idusualt, Set vdprecoprods,
			Set sgprefere2s, Set sgprefere1s, Set vdclientes) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.descclascli = descclascli;
		this.siglaclascli = siglaclascli;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.vdprecoprods = vdprecoprods;
		this.sgprefere2s = sgprefere2s;
		this.sgprefere1s = sgprefere1s;
		this.vdclientes = vdclientes;
	}

	public VdclascliId getId() {
		return this.id;
	}

	public void setId(VdclascliId id) {
		this.id = id;
	}

	public Sgfilial getSgfilial() {
		return this.sgfilial;
	}

	public void setSgfilial(Sgfilial sgfilial) {
		this.sgfilial = sgfilial;
	}

	public String getDescclascli() {
		return this.descclascli;
	}

	public void setDescclascli(String descclascli) {
		this.descclascli = descclascli;
	}

	public String getSiglaclascli() {
		return this.siglaclascli;
	}

	public void setSiglaclascli(String siglaclascli) {
		this.siglaclascli = siglaclascli;
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

	public Set getVdprecoprods() {
		return this.vdprecoprods;
	}

	public void setVdprecoprods(Set vdprecoprods) {
		this.vdprecoprods = vdprecoprods;
	}

	public Set getSgprefere2s() {
		return this.sgprefere2s;
	}

	public void setSgprefere2s(Set sgprefere2s) {
		this.sgprefere2s = sgprefere2s;
	}

	public Set getSgprefere1s() {
		return this.sgprefere1s;
	}

	public void setSgprefere1s(Set sgprefere1s) {
		this.sgprefere1s = sgprefere1s;
	}

	public Set getVdclientes() {
		return this.vdclientes;
	}

	public void setVdclientes(Set vdclientes) {
		this.vdclientes = vdclientes;
	}

}
