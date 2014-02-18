package org.freedom.persist.entity;

// Generated 18/02/2014 15:53:28 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * Sgcnae generated by hbm2java
 */
public class Sgcnae implements java.io.Serializable {

	private String codcnae;
	private String desccnae;
	private Character seccnae;
	private String divcnae;
	private String grupcnae;
	private Date dtrevcnae;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Sgcnae() {
	}

	public Sgcnae(String codcnae, String desccnae, Date dtins, Date hins,
			String idusuins, Date dtalt, Date halt, String idusualt) {
		this.codcnae = codcnae;
		this.desccnae = desccnae;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public Sgcnae(String codcnae, String desccnae, Character seccnae,
			String divcnae, String grupcnae, Date dtrevcnae, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt) {
		this.codcnae = codcnae;
		this.desccnae = desccnae;
		this.seccnae = seccnae;
		this.divcnae = divcnae;
		this.grupcnae = grupcnae;
		this.dtrevcnae = dtrevcnae;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public String getCodcnae() {
		return this.codcnae;
	}

	public void setCodcnae(String codcnae) {
		this.codcnae = codcnae;
	}

	public String getDesccnae() {
		return this.desccnae;
	}

	public void setDesccnae(String desccnae) {
		this.desccnae = desccnae;
	}

	public Character getSeccnae() {
		return this.seccnae;
	}

	public void setSeccnae(Character seccnae) {
		this.seccnae = seccnae;
	}

	public String getDivcnae() {
		return this.divcnae;
	}

	public void setDivcnae(String divcnae) {
		this.divcnae = divcnae;
	}

	public String getGrupcnae() {
		return this.grupcnae;
	}

	public void setGrupcnae(String grupcnae) {
		this.grupcnae = grupcnae;
	}

	public Date getDtrevcnae() {
		return this.dtrevcnae;
	}

	public void setDtrevcnae(Date dtrevcnae) {
		this.dtrevcnae = dtrevcnae;
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
