package org.freedom.persist.entity;

// Generated 10/05/2014 10:27:15 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;

/**
 * Fnrazclivw01Id generated by hbm2java
 */
public class Fnrazclivw01Id implements java.io.Serializable {

	private Integer codempcl;
	private Short codfilialcl;
	private Integer codcli;
	private String razcli;
	private Integer codemprc;
	private Short codfilialrc;
	private Integer codempsl;
	private Short codfilialsl;
	private Integer codempcp;
	private Short codfilialcp;
	private Date data;
	private Character tipo;
	private Character tiposublanca;
	private Integer doc;
	private BigDecimal vlrdeb;
	private BigDecimal vlrcred;

	public Fnrazclivw01Id() {
	}

	public Fnrazclivw01Id(Integer codempcl, Short codfilialcl, Integer codcli,
			String razcli, Integer codemprc, Short codfilialrc,
			Integer codempsl, Short codfilialsl, Integer codempcp,
			Short codfilialcp, Date data, Character tipo,
			Character tiposublanca, Integer doc, BigDecimal vlrdeb,
			BigDecimal vlrcred) {
		this.codempcl = codempcl;
		this.codfilialcl = codfilialcl;
		this.codcli = codcli;
		this.razcli = razcli;
		this.codemprc = codemprc;
		this.codfilialrc = codfilialrc;
		this.codempsl = codempsl;
		this.codfilialsl = codfilialsl;
		this.codempcp = codempcp;
		this.codfilialcp = codfilialcp;
		this.data = data;
		this.tipo = tipo;
		this.tiposublanca = tiposublanca;
		this.doc = doc;
		this.vlrdeb = vlrdeb;
		this.vlrcred = vlrcred;
	}

	public Integer getCodempcl() {
		return this.codempcl;
	}

	public void setCodempcl(Integer codempcl) {
		this.codempcl = codempcl;
	}

	public Short getCodfilialcl() {
		return this.codfilialcl;
	}

	public void setCodfilialcl(Short codfilialcl) {
		this.codfilialcl = codfilialcl;
	}

	public Integer getCodcli() {
		return this.codcli;
	}

	public void setCodcli(Integer codcli) {
		this.codcli = codcli;
	}

	public String getRazcli() {
		return this.razcli;
	}

	public void setRazcli(String razcli) {
		this.razcli = razcli;
	}

	public Integer getCodemprc() {
		return this.codemprc;
	}

	public void setCodemprc(Integer codemprc) {
		this.codemprc = codemprc;
	}

	public Short getCodfilialrc() {
		return this.codfilialrc;
	}

	public void setCodfilialrc(Short codfilialrc) {
		this.codfilialrc = codfilialrc;
	}

	public Integer getCodempsl() {
		return this.codempsl;
	}

	public void setCodempsl(Integer codempsl) {
		this.codempsl = codempsl;
	}

	public Short getCodfilialsl() {
		return this.codfilialsl;
	}

	public void setCodfilialsl(Short codfilialsl) {
		this.codfilialsl = codfilialsl;
	}

	public Integer getCodempcp() {
		return this.codempcp;
	}

	public void setCodempcp(Integer codempcp) {
		this.codempcp = codempcp;
	}

	public Short getCodfilialcp() {
		return this.codfilialcp;
	}

	public void setCodfilialcp(Short codfilialcp) {
		this.codfilialcp = codfilialcp;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Character getTipo() {
		return this.tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public Character getTiposublanca() {
		return this.tiposublanca;
	}

	public void setTiposublanca(Character tiposublanca) {
		this.tiposublanca = tiposublanca;
	}

	public Integer getDoc() {
		return this.doc;
	}

	public void setDoc(Integer doc) {
		this.doc = doc;
	}

	public BigDecimal getVlrdeb() {
		return this.vlrdeb;
	}

	public void setVlrdeb(BigDecimal vlrdeb) {
		this.vlrdeb = vlrdeb;
	}

	public BigDecimal getVlrcred() {
		return this.vlrcred;
	}

	public void setVlrcred(BigDecimal vlrcred) {
		this.vlrcred = vlrcred;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Fnrazclivw01Id))
			return false;
		Fnrazclivw01Id castOther = (Fnrazclivw01Id) other;

		return ((this.getCodempcl() == castOther.getCodempcl()) || (this
				.getCodempcl() != null && castOther.getCodempcl() != null && this
				.getCodempcl().equals(castOther.getCodempcl())))
				&& ((this.getCodfilialcl() == castOther.getCodfilialcl()) || (this
						.getCodfilialcl() != null
						&& castOther.getCodfilialcl() != null && this
						.getCodfilialcl().equals(castOther.getCodfilialcl())))
				&& ((this.getCodcli() == castOther.getCodcli()) || (this
						.getCodcli() != null && castOther.getCodcli() != null && this
						.getCodcli().equals(castOther.getCodcli())))
				&& ((this.getRazcli() == castOther.getRazcli()) || (this
						.getRazcli() != null && castOther.getRazcli() != null && this
						.getRazcli().equals(castOther.getRazcli())))
				&& ((this.getCodemprc() == castOther.getCodemprc()) || (this
						.getCodemprc() != null
						&& castOther.getCodemprc() != null && this
						.getCodemprc().equals(castOther.getCodemprc())))
				&& ((this.getCodfilialrc() == castOther.getCodfilialrc()) || (this
						.getCodfilialrc() != null
						&& castOther.getCodfilialrc() != null && this
						.getCodfilialrc().equals(castOther.getCodfilialrc())))
				&& ((this.getCodempsl() == castOther.getCodempsl()) || (this
						.getCodempsl() != null
						&& castOther.getCodempsl() != null && this
						.getCodempsl().equals(castOther.getCodempsl())))
				&& ((this.getCodfilialsl() == castOther.getCodfilialsl()) || (this
						.getCodfilialsl() != null
						&& castOther.getCodfilialsl() != null && this
						.getCodfilialsl().equals(castOther.getCodfilialsl())))
				&& ((this.getCodempcp() == castOther.getCodempcp()) || (this
						.getCodempcp() != null
						&& castOther.getCodempcp() != null && this
						.getCodempcp().equals(castOther.getCodempcp())))
				&& ((this.getCodfilialcp() == castOther.getCodfilialcp()) || (this
						.getCodfilialcp() != null
						&& castOther.getCodfilialcp() != null && this
						.getCodfilialcp().equals(castOther.getCodfilialcp())))
				&& ((this.getData() == castOther.getData()) || (this.getData() != null
						&& castOther.getData() != null && this.getData()
						.equals(castOther.getData())))
				&& ((this.getTipo() == castOther.getTipo()) || (this.getTipo() != null
						&& castOther.getTipo() != null && this.getTipo()
						.equals(castOther.getTipo())))
				&& ((this.getTiposublanca() == castOther.getTiposublanca()) || (this
						.getTiposublanca() != null
						&& castOther.getTiposublanca() != null && this
						.getTiposublanca().equals(castOther.getTiposublanca())))
				&& ((this.getDoc() == castOther.getDoc()) || (this.getDoc() != null
						&& castOther.getDoc() != null && this.getDoc().equals(
						castOther.getDoc())))
				&& ((this.getVlrdeb() == castOther.getVlrdeb()) || (this
						.getVlrdeb() != null && castOther.getVlrdeb() != null && this
						.getVlrdeb().equals(castOther.getVlrdeb())))
				&& ((this.getVlrcred() == castOther.getVlrcred()) || (this
						.getVlrcred() != null && castOther.getVlrcred() != null && this
						.getVlrcred().equals(castOther.getVlrcred())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCodempcl() == null ? 0 : this.getCodempcl().hashCode());
		result = 37
				* result
				+ (getCodfilialcl() == null ? 0 : this.getCodfilialcl()
						.hashCode());
		result = 37 * result
				+ (getCodcli() == null ? 0 : this.getCodcli().hashCode());
		result = 37 * result
				+ (getRazcli() == null ? 0 : this.getRazcli().hashCode());
		result = 37 * result
				+ (getCodemprc() == null ? 0 : this.getCodemprc().hashCode());
		result = 37
				* result
				+ (getCodfilialrc() == null ? 0 : this.getCodfilialrc()
						.hashCode());
		result = 37 * result
				+ (getCodempsl() == null ? 0 : this.getCodempsl().hashCode());
		result = 37
				* result
				+ (getCodfilialsl() == null ? 0 : this.getCodfilialsl()
						.hashCode());
		result = 37 * result
				+ (getCodempcp() == null ? 0 : this.getCodempcp().hashCode());
		result = 37
				* result
				+ (getCodfilialcp() == null ? 0 : this.getCodfilialcp()
						.hashCode());
		result = 37 * result
				+ (getData() == null ? 0 : this.getData().hashCode());
		result = 37 * result
				+ (getTipo() == null ? 0 : this.getTipo().hashCode());
		result = 37
				* result
				+ (getTiposublanca() == null ? 0 : this.getTiposublanca()
						.hashCode());
		result = 37 * result
				+ (getDoc() == null ? 0 : this.getDoc().hashCode());
		result = 37 * result
				+ (getVlrdeb() == null ? 0 : this.getVlrdeb().hashCode());
		result = 37 * result
				+ (getVlrcred() == null ? 0 : this.getVlrcred().hashCode());
		return result;
	}

}
