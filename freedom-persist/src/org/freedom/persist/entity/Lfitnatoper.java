package org.freedom.persist.entity;

// Generated 20/11/2013 10:59:59 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * Lfitnatoper generated by hbm2java
 */
public class Lfitnatoper implements java.io.Serializable {

	private LfitnatoperId id;
	private Lftabicms lftabicms;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Lfitnatoper() {
	}

	public Lfitnatoper(LfitnatoperId id, Lftabicms lftabicms, Date dtins,
			Date hins, String idusuins) {
		this.id = id;
		this.lftabicms = lftabicms;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Lfitnatoper(LfitnatoperId id, Lftabicms lftabicms, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt) {
		this.id = id;
		this.lftabicms = lftabicms;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public LfitnatoperId getId() {
		return this.id;
	}

	public void setId(LfitnatoperId id) {
		this.id = id;
	}

	public Lftabicms getLftabicms() {
		return this.lftabicms;
	}

	public void setLftabicms(Lftabicms lftabicms) {
		this.lftabicms = lftabicms;
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
