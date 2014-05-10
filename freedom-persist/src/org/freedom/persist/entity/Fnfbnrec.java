package org.freedom.persist.entity;

// Generated 10/05/2014 10:27:15 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * Fnfbnrec generated by hbm2java
 */
public class Fnfbnrec implements java.io.Serializable {

	private FnfbnrecId id;
	private Sgitprefere6 sgitprefere6;
	private Fnitreceber fnitreceber;
	private String stipofebraban;
	private String sitremessa;
	private String sitretorno;
	private String nomearquivo;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Fnfbnrec() {
	}

	public Fnfbnrec(Sgitprefere6 sgitprefere6, Fnitreceber fnitreceber,
			String stipofebraban, String sitremessa, String sitretorno,
			Date dtins, Date hins, String idusuins) {
		this.sgitprefere6 = sgitprefere6;
		this.fnitreceber = fnitreceber;
		this.stipofebraban = stipofebraban;
		this.sitremessa = sitremessa;
		this.sitretorno = sitretorno;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Fnfbnrec(Sgitprefere6 sgitprefere6, Fnitreceber fnitreceber,
			String stipofebraban, String sitremessa, String sitretorno,
			String nomearquivo, Date dtins, Date hins, String idusuins,
			Date dtalt, Date halt, String idusualt) {
		this.sgitprefere6 = sgitprefere6;
		this.fnitreceber = fnitreceber;
		this.stipofebraban = stipofebraban;
		this.sitremessa = sitremessa;
		this.sitretorno = sitretorno;
		this.nomearquivo = nomearquivo;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public FnfbnrecId getId() {
		return this.id;
	}

	public void setId(FnfbnrecId id) {
		this.id = id;
	}

	public Sgitprefere6 getSgitprefere6() {
		return this.sgitprefere6;
	}

	public void setSgitprefere6(Sgitprefere6 sgitprefere6) {
		this.sgitprefere6 = sgitprefere6;
	}

	public Fnitreceber getFnitreceber() {
		return this.fnitreceber;
	}

	public void setFnitreceber(Fnitreceber fnitreceber) {
		this.fnitreceber = fnitreceber;
	}

	public String getStipofebraban() {
		return this.stipofebraban;
	}

	public void setStipofebraban(String stipofebraban) {
		this.stipofebraban = stipofebraban;
	}

	public String getSitremessa() {
		return this.sitremessa;
	}

	public void setSitremessa(String sitremessa) {
		this.sitremessa = sitremessa;
	}

	public String getSitretorno() {
		return this.sitretorno;
	}

	public void setSitretorno(String sitretorno) {
		this.sitretorno = sitretorno;
	}

	public String getNomearquivo() {
		return this.nomearquivo;
	}

	public void setNomearquivo(String nomearquivo) {
		this.nomearquivo = nomearquivo;
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
