package org.freedom.persist.entity;

// Generated 20/11/2013 10:59:59 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Fnbanco generated by hbm2java
 */
public class Fnbanco implements java.io.Serializable {

	private FnbancoId id;
	private Sgfilial sgfilial;
	private Fnmodboleto fnmodboleto;
	private String nomebanco;
	private char dvbanco;
	private String sitebanco;
	private byte[] imgbolbanco;
	private byte[] imgbolbanco2;
	private String layoutcheqbanco;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set fncartcobs = new HashSet(0);
	private Set sgitprefere6s = new HashSet(0);
	private Set fncontas = new HashSet(0);
	private Set vdclicontases = new HashSet(0);
	private Set vdvendas = new HashSet(0);
	private Set cpforneceds = new HashSet(0);
	private Set fnpagars = new HashSet(0);
	private Set vdmodcontrs = new HashSet(0);
	private Set cpcompras = new HashSet(0);
	private Set fncheques = new HashSet(0);
	private Set fnrecebers = new HashSet(0);
	private Set fnitrecebers = new HashSet(0);
	private Set vdclientes = new HashSet(0);

	public Fnbanco() {
	}

	public Fnbanco(FnbancoId id, Sgfilial sgfilial, String nomebanco,
			char dvbanco, Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.nomebanco = nomebanco;
		this.dvbanco = dvbanco;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Fnbanco(FnbancoId id, Sgfilial sgfilial, Fnmodboleto fnmodboleto,
			String nomebanco, char dvbanco, String sitebanco,
			byte[] imgbolbanco, byte[] imgbolbanco2, String layoutcheqbanco,
			Date dtins, Date hins, String idusuins, Date dtalt, Date halt,
			String idusualt, Set fncartcobs, Set sgitprefere6s, Set fncontas,
			Set vdclicontases, Set vdvendas, Set cpforneceds, Set fnpagars,
			Set vdmodcontrs, Set cpcompras, Set fncheques, Set fnrecebers,
			Set fnitrecebers, Set vdclientes) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.fnmodboleto = fnmodboleto;
		this.nomebanco = nomebanco;
		this.dvbanco = dvbanco;
		this.sitebanco = sitebanco;
		this.imgbolbanco = imgbolbanco;
		this.imgbolbanco2 = imgbolbanco2;
		this.layoutcheqbanco = layoutcheqbanco;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.fncartcobs = fncartcobs;
		this.sgitprefere6s = sgitprefere6s;
		this.fncontas = fncontas;
		this.vdclicontases = vdclicontases;
		this.vdvendas = vdvendas;
		this.cpforneceds = cpforneceds;
		this.fnpagars = fnpagars;
		this.vdmodcontrs = vdmodcontrs;
		this.cpcompras = cpcompras;
		this.fncheques = fncheques;
		this.fnrecebers = fnrecebers;
		this.fnitrecebers = fnitrecebers;
		this.vdclientes = vdclientes;
	}

	public FnbancoId getId() {
		return this.id;
	}

	public void setId(FnbancoId id) {
		this.id = id;
	}

	public Sgfilial getSgfilial() {
		return this.sgfilial;
	}

	public void setSgfilial(Sgfilial sgfilial) {
		this.sgfilial = sgfilial;
	}

	public Fnmodboleto getFnmodboleto() {
		return this.fnmodboleto;
	}

	public void setFnmodboleto(Fnmodboleto fnmodboleto) {
		this.fnmodboleto = fnmodboleto;
	}

	public String getNomebanco() {
		return this.nomebanco;
	}

	public void setNomebanco(String nomebanco) {
		this.nomebanco = nomebanco;
	}

	public char getDvbanco() {
		return this.dvbanco;
	}

	public void setDvbanco(char dvbanco) {
		this.dvbanco = dvbanco;
	}

	public String getSitebanco() {
		return this.sitebanco;
	}

	public void setSitebanco(String sitebanco) {
		this.sitebanco = sitebanco;
	}

	public byte[] getImgbolbanco() {
		return this.imgbolbanco;
	}

	public void setImgbolbanco(byte[] imgbolbanco) {
		this.imgbolbanco = imgbolbanco;
	}

	public byte[] getImgbolbanco2() {
		return this.imgbolbanco2;
	}

	public void setImgbolbanco2(byte[] imgbolbanco2) {
		this.imgbolbanco2 = imgbolbanco2;
	}

	public String getLayoutcheqbanco() {
		return this.layoutcheqbanco;
	}

	public void setLayoutcheqbanco(String layoutcheqbanco) {
		this.layoutcheqbanco = layoutcheqbanco;
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

	public Set getFncartcobs() {
		return this.fncartcobs;
	}

	public void setFncartcobs(Set fncartcobs) {
		this.fncartcobs = fncartcobs;
	}

	public Set getSgitprefere6s() {
		return this.sgitprefere6s;
	}

	public void setSgitprefere6s(Set sgitprefere6s) {
		this.sgitprefere6s = sgitprefere6s;
	}

	public Set getFncontas() {
		return this.fncontas;
	}

	public void setFncontas(Set fncontas) {
		this.fncontas = fncontas;
	}

	public Set getVdclicontases() {
		return this.vdclicontases;
	}

	public void setVdclicontases(Set vdclicontases) {
		this.vdclicontases = vdclicontases;
	}

	public Set getVdvendas() {
		return this.vdvendas;
	}

	public void setVdvendas(Set vdvendas) {
		this.vdvendas = vdvendas;
	}

	public Set getCpforneceds() {
		return this.cpforneceds;
	}

	public void setCpforneceds(Set cpforneceds) {
		this.cpforneceds = cpforneceds;
	}

	public Set getFnpagars() {
		return this.fnpagars;
	}

	public void setFnpagars(Set fnpagars) {
		this.fnpagars = fnpagars;
	}

	public Set getVdmodcontrs() {
		return this.vdmodcontrs;
	}

	public void setVdmodcontrs(Set vdmodcontrs) {
		this.vdmodcontrs = vdmodcontrs;
	}

	public Set getCpcompras() {
		return this.cpcompras;
	}

	public void setCpcompras(Set cpcompras) {
		this.cpcompras = cpcompras;
	}

	public Set getFncheques() {
		return this.fncheques;
	}

	public void setFncheques(Set fncheques) {
		this.fncheques = fncheques;
	}

	public Set getFnrecebers() {
		return this.fnrecebers;
	}

	public void setFnrecebers(Set fnrecebers) {
		this.fnrecebers = fnrecebers;
	}

	public Set getFnitrecebers() {
		return this.fnitrecebers;
	}

	public void setFnitrecebers(Set fnitrecebers) {
		this.fnitrecebers = fnitrecebers;
	}

	public Set getVdclientes() {
		return this.vdclientes;
	}

	public void setVdclientes(Set vdclientes) {
		this.vdclientes = vdclientes;
	}

}
