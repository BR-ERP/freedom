package org.freedom.persist.entity;

// Generated 10/05/2014 10:27:15 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Eqprocrecmerc generated by hbm2java
 */
public class Eqprocrecmerc implements java.io.Serializable {

	private EqprocrecmercId id;
	private Sgfilial sgfilial;
	private Eqtiporecmerc eqtiporecmerc;
	private String descprocrecmerc;
	private String tipoprocrecmerc;
	private short nroamostprocrecmerc;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set eqitrecmercs = new HashSet(0);

	public Eqprocrecmerc() {
	}

	public Eqprocrecmerc(EqprocrecmercId id, Sgfilial sgfilial,
			Eqtiporecmerc eqtiporecmerc, String descprocrecmerc,
			String tipoprocrecmerc, short nroamostprocrecmerc, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.eqtiporecmerc = eqtiporecmerc;
		this.descprocrecmerc = descprocrecmerc;
		this.tipoprocrecmerc = tipoprocrecmerc;
		this.nroamostprocrecmerc = nroamostprocrecmerc;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public Eqprocrecmerc(EqprocrecmercId id, Sgfilial sgfilial,
			Eqtiporecmerc eqtiporecmerc, String descprocrecmerc,
			String tipoprocrecmerc, short nroamostprocrecmerc, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt,
			Set eqitrecmercs) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.eqtiporecmerc = eqtiporecmerc;
		this.descprocrecmerc = descprocrecmerc;
		this.tipoprocrecmerc = tipoprocrecmerc;
		this.nroamostprocrecmerc = nroamostprocrecmerc;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.eqitrecmercs = eqitrecmercs;
	}

	public EqprocrecmercId getId() {
		return this.id;
	}

	public void setId(EqprocrecmercId id) {
		this.id = id;
	}

	public Sgfilial getSgfilial() {
		return this.sgfilial;
	}

	public void setSgfilial(Sgfilial sgfilial) {
		this.sgfilial = sgfilial;
	}

	public Eqtiporecmerc getEqtiporecmerc() {
		return this.eqtiporecmerc;
	}

	public void setEqtiporecmerc(Eqtiporecmerc eqtiporecmerc) {
		this.eqtiporecmerc = eqtiporecmerc;
	}

	public String getDescprocrecmerc() {
		return this.descprocrecmerc;
	}

	public void setDescprocrecmerc(String descprocrecmerc) {
		this.descprocrecmerc = descprocrecmerc;
	}

	public String getTipoprocrecmerc() {
		return this.tipoprocrecmerc;
	}

	public void setTipoprocrecmerc(String tipoprocrecmerc) {
		this.tipoprocrecmerc = tipoprocrecmerc;
	}

	public short getNroamostprocrecmerc() {
		return this.nroamostprocrecmerc;
	}

	public void setNroamostprocrecmerc(short nroamostprocrecmerc) {
		this.nroamostprocrecmerc = nroamostprocrecmerc;
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

	public Set getEqitrecmercs() {
		return this.eqitrecmercs;
	}

	public void setEqitrecmercs(Set eqitrecmercs) {
		this.eqitrecmercs = eqitrecmercs;
	}

}
