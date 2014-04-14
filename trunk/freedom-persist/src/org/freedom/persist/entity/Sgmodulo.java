package org.freedom.persist.entity;

// Generated 14/04/2014 10:17:08 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Sgmodulo generated by hbm2java
 */
public class Sgmodulo implements java.io.Serializable {

	private SgmoduloId id;
	private String descmodu;
	private String comentmodu;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set sgmenus = new HashSet(0);

	public Sgmodulo() {
	}

	public Sgmodulo(SgmoduloId id, String descmodu, Date dtins, Date hins,
			String idusuins) {
		this.id = id;
		this.descmodu = descmodu;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Sgmodulo(SgmoduloId id, String descmodu, String comentmodu,
			Date dtins, Date hins, String idusuins, Date dtalt, Date halt,
			String idusualt, Set sgmenus) {
		this.id = id;
		this.descmodu = descmodu;
		this.comentmodu = comentmodu;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.sgmenus = sgmenus;
	}

	public SgmoduloId getId() {
		return this.id;
	}

	public void setId(SgmoduloId id) {
		this.id = id;
	}

	public String getDescmodu() {
		return this.descmodu;
	}

	public void setDescmodu(String descmodu) {
		this.descmodu = descmodu;
	}

	public String getComentmodu() {
		return this.comentmodu;
	}

	public void setComentmodu(String comentmodu) {
		this.comentmodu = comentmodu;
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

	public Set getSgmenus() {
		return this.sgmenus;
	}

	public void setSgmenus(Set sgmenus) {
		this.sgmenus = sgmenus;
	}

}
