package org.freedom.persist.entity;

// Generated 18/02/2014 15:53:28 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Crambienteaval generated by hbm2java
 */
public class Crambienteaval implements java.io.Serializable {

	private CrambienteavalId id;
	private String descambaval;
	private String siglaambaval;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set critfichaavals = new HashSet(0);

	public Crambienteaval() {
	}

	public Crambienteaval(CrambienteavalId id, String descambaval,
			String siglaambaval, Date dtins, Date hins, String idusuins,
			Date dtalt, Date halt) {
		this.id = id;
		this.descambaval = descambaval;
		this.siglaambaval = siglaambaval;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
	}

	public Crambienteaval(CrambienteavalId id, String descambaval,
			String siglaambaval, Date dtins, Date hins, String idusuins,
			Date dtalt, Date halt, String idusualt, Set critfichaavals) {
		this.id = id;
		this.descambaval = descambaval;
		this.siglaambaval = siglaambaval;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.critfichaavals = critfichaavals;
	}

	public CrambienteavalId getId() {
		return this.id;
	}

	public void setId(CrambienteavalId id) {
		this.id = id;
	}

	public String getDescambaval() {
		return this.descambaval;
	}

	public void setDescambaval(String descambaval) {
		this.descambaval = descambaval;
	}

	public String getSiglaambaval() {
		return this.siglaambaval;
	}

	public void setSiglaambaval(String siglaambaval) {
		this.siglaambaval = siglaambaval;
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

	public Set getCritfichaavals() {
		return this.critfichaavals;
	}

	public void setCritfichaavals(Set critfichaavals) {
		this.critfichaavals = critfichaavals;
	}

}
