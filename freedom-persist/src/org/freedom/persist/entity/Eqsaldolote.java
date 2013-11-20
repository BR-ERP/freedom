package org.freedom.persist.entity;

// Generated 20/11/2013 10:59:59 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;

/**
 * Eqsaldolote generated by hbm2java
 */
public class Eqsaldolote implements java.io.Serializable {

	private EqsaldoloteId id;
	private Eqlote eqlote;
	private Eqalmox eqalmox;
	private BigDecimal sldlote;
	private BigDecimal sldreslote;
	private BigDecimal sldconsiglote;
	private BigDecimal sldliqlote;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Eqsaldolote() {
	}

	public Eqsaldolote(EqsaldoloteId id, Eqlote eqlote, Eqalmox eqalmox,
			BigDecimal sldlote, BigDecimal sldreslote,
			BigDecimal sldconsiglote, BigDecimal sldliqlote, Date dtins,
			Date hins, String idusuins) {
		this.id = id;
		this.eqlote = eqlote;
		this.eqalmox = eqalmox;
		this.sldlote = sldlote;
		this.sldreslote = sldreslote;
		this.sldconsiglote = sldconsiglote;
		this.sldliqlote = sldliqlote;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Eqsaldolote(EqsaldoloteId id, Eqlote eqlote, Eqalmox eqalmox,
			BigDecimal sldlote, BigDecimal sldreslote,
			BigDecimal sldconsiglote, BigDecimal sldliqlote, Date dtins,
			Date hins, String idusuins, Date dtalt, Date halt, String idusualt) {
		this.id = id;
		this.eqlote = eqlote;
		this.eqalmox = eqalmox;
		this.sldlote = sldlote;
		this.sldreslote = sldreslote;
		this.sldconsiglote = sldconsiglote;
		this.sldliqlote = sldliqlote;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public EqsaldoloteId getId() {
		return this.id;
	}

	public void setId(EqsaldoloteId id) {
		this.id = id;
	}

	public Eqlote getEqlote() {
		return this.eqlote;
	}

	public void setEqlote(Eqlote eqlote) {
		this.eqlote = eqlote;
	}

	public Eqalmox getEqalmox() {
		return this.eqalmox;
	}

	public void setEqalmox(Eqalmox eqalmox) {
		this.eqalmox = eqalmox;
	}

	public BigDecimal getSldlote() {
		return this.sldlote;
	}

	public void setSldlote(BigDecimal sldlote) {
		this.sldlote = sldlote;
	}

	public BigDecimal getSldreslote() {
		return this.sldreslote;
	}

	public void setSldreslote(BigDecimal sldreslote) {
		this.sldreslote = sldreslote;
	}

	public BigDecimal getSldconsiglote() {
		return this.sldconsiglote;
	}

	public void setSldconsiglote(BigDecimal sldconsiglote) {
		this.sldconsiglote = sldconsiglote;
	}

	public BigDecimal getSldliqlote() {
		return this.sldliqlote;
	}

	public void setSldliqlote(BigDecimal sldliqlote) {
		this.sldliqlote = sldliqlote;
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
