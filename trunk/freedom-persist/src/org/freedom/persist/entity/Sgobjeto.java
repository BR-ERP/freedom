package org.freedom.persist.entity;

// Generated 14/04/2014 10:17:08 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Sgobjeto generated by hbm2java
 */
public class Sgobjeto implements java.io.Serializable {

	private SgobjetoId id;
	private Sgempresa sgempresa;
	private String descobj;
	private String tipoobj;
	private String comentobj;
	private Character usomeobj;
	private String siglaobj;
	private short nivelobj;
	private int numregobj;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set sgobjetotbs = new HashSet(0);
	private Set sgmenuobjs = new HashSet(0);

	public Sgobjeto() {
	}

	public Sgobjeto(SgobjetoId id, Sgempresa sgempresa, String descobj,
			String tipoobj, short nivelobj, int numregobj, Date dtins,
			Date hins, String idusuins) {
		this.id = id;
		this.sgempresa = sgempresa;
		this.descobj = descobj;
		this.tipoobj = tipoobj;
		this.nivelobj = nivelobj;
		this.numregobj = numregobj;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Sgobjeto(SgobjetoId id, Sgempresa sgempresa, String descobj,
			String tipoobj, String comentobj, Character usomeobj,
			String siglaobj, short nivelobj, int numregobj, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt,
			Set sgobjetotbs, Set sgmenuobjs) {
		this.id = id;
		this.sgempresa = sgempresa;
		this.descobj = descobj;
		this.tipoobj = tipoobj;
		this.comentobj = comentobj;
		this.usomeobj = usomeobj;
		this.siglaobj = siglaobj;
		this.nivelobj = nivelobj;
		this.numregobj = numregobj;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.sgobjetotbs = sgobjetotbs;
		this.sgmenuobjs = sgmenuobjs;
	}

	public SgobjetoId getId() {
		return this.id;
	}

	public void setId(SgobjetoId id) {
		this.id = id;
	}

	public Sgempresa getSgempresa() {
		return this.sgempresa;
	}

	public void setSgempresa(Sgempresa sgempresa) {
		this.sgempresa = sgempresa;
	}

	public String getDescobj() {
		return this.descobj;
	}

	public void setDescobj(String descobj) {
		this.descobj = descobj;
	}

	public String getTipoobj() {
		return this.tipoobj;
	}

	public void setTipoobj(String tipoobj) {
		this.tipoobj = tipoobj;
	}

	public String getComentobj() {
		return this.comentobj;
	}

	public void setComentobj(String comentobj) {
		this.comentobj = comentobj;
	}

	public Character getUsomeobj() {
		return this.usomeobj;
	}

	public void setUsomeobj(Character usomeobj) {
		this.usomeobj = usomeobj;
	}

	public String getSiglaobj() {
		return this.siglaobj;
	}

	public void setSiglaobj(String siglaobj) {
		this.siglaobj = siglaobj;
	}

	public short getNivelobj() {
		return this.nivelobj;
	}

	public void setNivelobj(short nivelobj) {
		this.nivelobj = nivelobj;
	}

	public int getNumregobj() {
		return this.numregobj;
	}

	public void setNumregobj(int numregobj) {
		this.numregobj = numregobj;
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

	public Set getSgobjetotbs() {
		return this.sgobjetotbs;
	}

	public void setSgobjetotbs(Set sgobjetotbs) {
		this.sgobjetotbs = sgobjetotbs;
	}

	public Set getSgmenuobjs() {
		return this.sgmenuobjs;
	}

	public void setSgmenuobjs(Set sgmenuobjs) {
		this.sgmenuobjs = sgmenuobjs;
	}

}
