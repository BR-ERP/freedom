package org.freedom.persist.entity;

// Generated 10/05/2014 10:27:15 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * Vdfotoprod generated by hbm2java
 */
public class Vdfotoprod implements java.io.Serializable {

	private VdfotoprodId id;
	private Eqproduto eqproduto;
	private String descfotoprod;
	private char tipofotoprod;
	private int largfotoprod;
	private int altfotoprod;
	private byte[] fotoprod;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Vdfotoprod() {
	}

	public Vdfotoprod(VdfotoprodId id, Eqproduto eqproduto,
			String descfotoprod, char tipofotoprod, int largfotoprod,
			int altfotoprod, byte[] fotoprod, Date dtins, Date hins,
			String idusuins) {
		this.id = id;
		this.eqproduto = eqproduto;
		this.descfotoprod = descfotoprod;
		this.tipofotoprod = tipofotoprod;
		this.largfotoprod = largfotoprod;
		this.altfotoprod = altfotoprod;
		this.fotoprod = fotoprod;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Vdfotoprod(VdfotoprodId id, Eqproduto eqproduto,
			String descfotoprod, char tipofotoprod, int largfotoprod,
			int altfotoprod, byte[] fotoprod, Date dtins, Date hins,
			String idusuins, Date dtalt, Date halt, String idusualt) {
		this.id = id;
		this.eqproduto = eqproduto;
		this.descfotoprod = descfotoprod;
		this.tipofotoprod = tipofotoprod;
		this.largfotoprod = largfotoprod;
		this.altfotoprod = altfotoprod;
		this.fotoprod = fotoprod;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public VdfotoprodId getId() {
		return this.id;
	}

	public void setId(VdfotoprodId id) {
		this.id = id;
	}

	public Eqproduto getEqproduto() {
		return this.eqproduto;
	}

	public void setEqproduto(Eqproduto eqproduto) {
		this.eqproduto = eqproduto;
	}

	public String getDescfotoprod() {
		return this.descfotoprod;
	}

	public void setDescfotoprod(String descfotoprod) {
		this.descfotoprod = descfotoprod;
	}

	public char getTipofotoprod() {
		return this.tipofotoprod;
	}

	public void setTipofotoprod(char tipofotoprod) {
		this.tipofotoprod = tipofotoprod;
	}

	public int getLargfotoprod() {
		return this.largfotoprod;
	}

	public void setLargfotoprod(int largfotoprod) {
		this.largfotoprod = largfotoprod;
	}

	public int getAltfotoprod() {
		return this.altfotoprod;
	}

	public void setAltfotoprod(int altfotoprod) {
		this.altfotoprod = altfotoprod;
	}

	public byte[] getFotoprod() {
		return this.fotoprod;
	}

	public void setFotoprod(byte[] fotoprod) {
		this.fotoprod = fotoprod;
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
