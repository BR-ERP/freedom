package org.freedom.persist.entity;

// Generated 12/05/2014 09:11:34 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Vdveiculo generated by hbm2java
 */
@Entity
@Table(name = "VDVEICULO")
public class Vdveiculo implements java.io.Serializable {

	private VdveiculoId id;
	private Vdtransp vdtransp;
	private Sgmunicipio sgmunicipio;
	private String placa;
	private String renavam;
	private String fabricante;
	private String modelo;
	private String desccor;
	private Integer codcor;
	private String obs;
	private Integer anofabric;
	private Integer anomodelo;
	private String rntc;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set eqexpedicaos = new HashSet(0);
	private Set vdtransps = new HashSet(0);

	public Vdveiculo() {
	}

	public Vdveiculo(VdveiculoId id, String placa, Date dtins, Date hins,
			String idusuins) {
		this.id = id;
		this.placa = placa;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Vdveiculo(VdveiculoId id, Vdtransp vdtransp,
			Sgmunicipio sgmunicipio, String placa, String renavam,
			String fabricante, String modelo, String desccor, Integer codcor,
			String obs, Integer anofabric, Integer anomodelo, String rntc,
			Date dtins, Date hins, String idusuins, Date dtalt, Date halt,
			String idusualt, Set eqexpedicaos, Set vdtransps) {
		this.id = id;
		this.vdtransp = vdtransp;
		this.sgmunicipio = sgmunicipio;
		this.placa = placa;
		this.renavam = renavam;
		this.fabricante = fabricante;
		this.modelo = modelo;
		this.desccor = desccor;
		this.codcor = codcor;
		this.obs = obs;
		this.anofabric = anofabric;
		this.anomodelo = anomodelo;
		this.rntc = rntc;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.eqexpedicaos = eqexpedicaos;
		this.vdtransps = vdtransps;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codveic", column = @Column(name = "CODVEIC", nullable = false)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public VdveiculoId getId() {
		return this.id;
	}

	public void setId(VdveiculoId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODTRAN", referencedColumnName = "CODTRAN"),
			@JoinColumn(name = "CODFILIALTN", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPTN", referencedColumnName = "CODEMP") })
	public Vdtransp getVdtransp() {
		return this.vdtransp;
	}

	public void setVdtransp(Vdtransp vdtransp) {
		this.vdtransp = vdtransp;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODMUNIC", referencedColumnName = "CODMUNIC"),
			@JoinColumn(name = "SIGLAUF", referencedColumnName = "SIGLAUF"),
			@JoinColumn(name = "CODPAIS", referencedColumnName = "CODPAIS") })
	public Sgmunicipio getSgmunicipio() {
		return this.sgmunicipio;
	}

	public void setSgmunicipio(Sgmunicipio sgmunicipio) {
		this.sgmunicipio = sgmunicipio;
	}

	@Column(name = "PLACA", nullable = false, length = 7)
	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@Column(name = "RENAVAM", length = 12)
	public String getRenavam() {
		return this.renavam;
	}

	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}

	@Column(name = "FABRICANTE", length = 100)
	public String getFabricante() {
		return this.fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	@Column(name = "MODELO", length = 250)
	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	@Column(name = "DESCCOR", length = 60)
	public String getDesccor() {
		return this.desccor;
	}

	public void setDesccor(String desccor) {
		this.desccor = desccor;
	}

	@Column(name = "CODCOR")
	public Integer getCodcor() {
		return this.codcor;
	}

	public void setCodcor(Integer codcor) {
		this.codcor = codcor;
	}

	@Column(name = "OBS", length = 1000)
	public String getObs() {
		return this.obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Column(name = "ANOFABRIC")
	public Integer getAnofabric() {
		return this.anofabric;
	}

	public void setAnofabric(Integer anofabric) {
		this.anofabric = anofabric;
	}

	@Column(name = "ANOMODELO")
	public Integer getAnomodelo() {
		return this.anomodelo;
	}

	public void setAnomodelo(Integer anomodelo) {
		this.anomodelo = anomodelo;
	}

	@Column(name = "RNTC", length = 20)
	public String getRntc() {
		return this.rntc;
	}

	public void setRntc(String rntc) {
		this.rntc = rntc;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTINS", nullable = false, length = 10)
	public Date getDtins() {
		return this.dtins;
	}

	public void setDtins(Date dtins) {
		this.dtins = dtins;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "HINS", nullable = false, length = 8)
	public Date getHins() {
		return this.hins;
	}

	public void setHins(Date hins) {
		this.hins = hins;
	}

	@Column(name = "IDUSUINS", nullable = false, length = 8)
	public String getIdusuins() {
		return this.idusuins;
	}

	public void setIdusuins(String idusuins) {
		this.idusuins = idusuins;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTALT", length = 10)
	public Date getDtalt() {
		return this.dtalt;
	}

	public void setDtalt(Date dtalt) {
		this.dtalt = dtalt;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "HALT", length = 8)
	public Date getHalt() {
		return this.halt;
	}

	public void setHalt(Date halt) {
		this.halt = halt;
	}

	@Column(name = "IDUSUALT", length = 8)
	public String getIdusualt() {
		return this.idusualt;
	}

	public void setIdusualt(String idusualt) {
		this.idusualt = idusualt;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "vdveiculo")
	public Set getEqexpedicaos() {
		return this.eqexpedicaos;
	}

	public void setEqexpedicaos(Set eqexpedicaos) {
		this.eqexpedicaos = eqexpedicaos;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "VDTRANSPVEIC", joinColumns = {
			@JoinColumn(name = "CODVEIC", nullable = false, updatable = false),
			@JoinColumn(name = "CODFILIALVE", nullable = false, updatable = false),
			@JoinColumn(name = "CODEMPVE", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "CODTRAN", nullable = false, updatable = false) })
	public Set getVdtransps() {
		return this.vdtransps;
	}

	public void setVdtransps(Set vdtransps) {
		this.vdtransps = vdtransps;
	}
*/
}
