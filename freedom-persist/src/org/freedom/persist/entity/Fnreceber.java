package org.freedom.persist.entity;

// Generated 12/05/2014 09:11:34 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Fnreceber generated by hbm2java
 */
@Entity
@Table(name = "FNRECEBER")
public class Fnreceber implements java.io.Serializable {

	private FnreceberId id;
	private Sgfilial sgfilial;
	private Fnconta fnconta;
	private Fntipocob fntipocob;
	private Vdcliente vdcliente;
	private Fnplanopag fnplanopag;
	private Fnplanejamento fnplanejamento;
	private Vdvenda vdvenda;
	private Vdvendedor vdvendedor;
	private Fnrenegrec fnrenegrec;
	private Fnbanco fnbanco;
	private Fncc fncc;
	private Fncartcob fncartcob;
	private BigDecimal vlrrec;
	private BigDecimal vlrdescrec;
	private BigDecimal vlrmultarec;
	private BigDecimal vlrjurosrec;
	private BigDecimal vlrdevrec;
	private BigDecimal vlrparcrec;
	private BigDecimal vlrpagorec;
	private BigDecimal vlrapagrec;
	private BigDecimal vlrbasecomis;
	private BigDecimal vlrretensaoiss;
	private Date dtcomprec;
	private Date datarec;
	private String statusrec;
	private BigDecimal vlrcomirec;
	private Date dtquitrec;
	private int docrec;
	private Integer nroparcrec;
	private String obsrec;
	private Character flag;
	private Character altusurec;
	private char emmanut;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set fnitrecebers = new HashSet(0);

	public Fnreceber() {
	}

	public Fnreceber(FnreceberId id, Sgfilial sgfilial, Vdcliente vdcliente,
			Fnplanopag fnplanopag, BigDecimal vlrrec, BigDecimal vlrdevrec,
			BigDecimal vlrparcrec, Date dtcomprec, Date datarec,
			String statusrec, int docrec, char emmanut, Date dtins, Date hins,
			String idusuins) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.vdcliente = vdcliente;
		this.fnplanopag = fnplanopag;
		this.vlrrec = vlrrec;
		this.vlrdevrec = vlrdevrec;
		this.vlrparcrec = vlrparcrec;
		this.dtcomprec = dtcomprec;
		this.datarec = datarec;
		this.statusrec = statusrec;
		this.docrec = docrec;
		this.emmanut = emmanut;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Fnreceber(FnreceberId id, Sgfilial sgfilial, Fnconta fnconta,
			Fntipocob fntipocob, Vdcliente vdcliente, Fnplanopag fnplanopag,
			Fnplanejamento fnplanejamento, Vdvenda vdvenda,
			Vdvendedor vdvendedor, Fnrenegrec fnrenegrec, Fnbanco fnbanco,
			Fncc fncc, Fncartcob fncartcob, BigDecimal vlrrec,
			BigDecimal vlrdescrec, BigDecimal vlrmultarec,
			BigDecimal vlrjurosrec, BigDecimal vlrdevrec,
			BigDecimal vlrparcrec, BigDecimal vlrpagorec,
			BigDecimal vlrapagrec, BigDecimal vlrbasecomis,
			BigDecimal vlrretensaoiss, Date dtcomprec, Date datarec,
			String statusrec, BigDecimal vlrcomirec, Date dtquitrec,
			int docrec, Integer nroparcrec, String obsrec, Character flag,
			Character altusurec, char emmanut, Date dtins, Date hins,
			String idusuins, Date dtalt, Date halt, String idusualt,
			Set fnitrecebers) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.fnconta = fnconta;
		this.fntipocob = fntipocob;
		this.vdcliente = vdcliente;
		this.fnplanopag = fnplanopag;
		this.fnplanejamento = fnplanejamento;
		this.vdvenda = vdvenda;
		this.vdvendedor = vdvendedor;
		this.fnrenegrec = fnrenegrec;
		this.fnbanco = fnbanco;
		this.fncc = fncc;
		this.fncartcob = fncartcob;
		this.vlrrec = vlrrec;
		this.vlrdescrec = vlrdescrec;
		this.vlrmultarec = vlrmultarec;
		this.vlrjurosrec = vlrjurosrec;
		this.vlrdevrec = vlrdevrec;
		this.vlrparcrec = vlrparcrec;
		this.vlrpagorec = vlrpagorec;
		this.vlrapagrec = vlrapagrec;
		this.vlrbasecomis = vlrbasecomis;
		this.vlrretensaoiss = vlrretensaoiss;
		this.dtcomprec = dtcomprec;
		this.datarec = datarec;
		this.statusrec = statusrec;
		this.vlrcomirec = vlrcomirec;
		this.dtquitrec = dtquitrec;
		this.docrec = docrec;
		this.nroparcrec = nroparcrec;
		this.obsrec = obsrec;
		this.flag = flag;
		this.altusurec = altusurec;
		this.emmanut = emmanut;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.fnitrecebers = fnitrecebers;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codrec", column = @Column(name = "CODREC", nullable = false)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public FnreceberId getId() {
		return this.id;
	}

	public void setId(FnreceberId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODFILIAL", referencedColumnName = "CODFILIAL", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "CODEMP", referencedColumnName = "CODEMP", nullable = false, insertable = false, updatable = false) })
	public Sgfilial getSgfilial() {
		return this.sgfilial;
	}

	public void setSgfilial(Sgfilial sgfilial) {
		this.sgfilial = sgfilial;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "NUMCONTA", referencedColumnName = "NUMCONTA"),
			@JoinColumn(name = "CODFILIALCA", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPCA", referencedColumnName = "CODEMP") })
	public Fnconta getFnconta() {
		return this.fnconta;
	}

	public void setFnconta(Fnconta fnconta) {
		this.fnconta = fnconta;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODTIPOCOB", referencedColumnName = "CODTIPOCOB"),
			@JoinColumn(name = "CODFILIALTC", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPTC", referencedColumnName = "CODEMP") })
	public Fntipocob getFntipocob() {
		return this.fntipocob;
	}

	public void setFntipocob(Fntipocob fntipocob) {
		this.fntipocob = fntipocob;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODCLI", referencedColumnName = "CODCLI", nullable = false),
			@JoinColumn(name = "CODFILIALCL", referencedColumnName = "CODFILIAL", nullable = false),
			@JoinColumn(name = "CODEMPCL", referencedColumnName = "CODEMP", nullable = false) })
	public Vdcliente getVdcliente() {
		return this.vdcliente;
	}

	public void setVdcliente(Vdcliente vdcliente) {
		this.vdcliente = vdcliente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODPLANOPAG", referencedColumnName = "CODPLANOPAG", nullable = false),
			@JoinColumn(name = "CODFILIALPG", referencedColumnName = "CODFILIAL", nullable = false),
			@JoinColumn(name = "CODEMPPG", referencedColumnName = "CODEMP", nullable = false) })
	public Fnplanopag getFnplanopag() {
		return this.fnplanopag;
	}

	public void setFnplanopag(Fnplanopag fnplanopag) {
		this.fnplanopag = fnplanopag;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODPLAN", referencedColumnName = "CODPLAN"),
			@JoinColumn(name = "CODFILIALPN", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPPN", referencedColumnName = "CODEMP") })
	public Fnplanejamento getFnplanejamento() {
		return this.fnplanejamento;
	}

	public void setFnplanejamento(Fnplanejamento fnplanejamento) {
		this.fnplanejamento = fnplanejamento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODVENDA", referencedColumnName = "CODVENDA"),
			@JoinColumn(name = "TIPOVENDA", referencedColumnName = "TIPOVENDA"),
			@JoinColumn(name = "CODFILIALVA", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPVA", referencedColumnName = "CODEMP") })
	public Vdvenda getVdvenda() {
		return this.vdvenda;
	}

	public void setVdvenda(Vdvenda vdvenda) {
		this.vdvenda = vdvenda;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODVEND", referencedColumnName = "CODVEND"),
			@JoinColumn(name = "CODFILIALVD", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPVD", referencedColumnName = "CODEMP") })
	public Vdvendedor getVdvendedor() {
		return this.vdvendedor;
	}

	public void setVdvendedor(Vdvendedor vdvendedor) {
		this.vdvendedor = vdvendedor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODRENEGREC", referencedColumnName = "CODRENEGREC"),
			@JoinColumn(name = "CODFILIALRR", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPRR", referencedColumnName = "CODEMP") })
	public Fnrenegrec getFnrenegrec() {
		return this.fnrenegrec;
	}

	public void setFnrenegrec(Fnrenegrec fnrenegrec) {
		this.fnrenegrec = fnrenegrec;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODBANCO", referencedColumnName = "CODBANCO"),
			@JoinColumn(name = "CODFILIALBO", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPBO", referencedColumnName = "CODEMP") })
	public Fnbanco getFnbanco() {
		return this.fnbanco;
	}

	public void setFnbanco(Fnbanco fnbanco) {
		this.fnbanco = fnbanco;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODCC", referencedColumnName = "CODCC"),
			@JoinColumn(name = "ANOCC", referencedColumnName = "ANOCC"),
			@JoinColumn(name = "CODFILIALCC", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPCC", referencedColumnName = "CODEMP") })
	public Fncc getFncc() {
		return this.fncc;
	}

	public void setFncc(Fncc fncc) {
		this.fncc = fncc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODCARTCOB", referencedColumnName = "CODCARTCOB", insertable = false, updatable = false),
			@JoinColumn(name = "CODFILIALCB", referencedColumnName = "CODFILIAL", insertable = false, updatable = false),
			@JoinColumn(name = "CODEMPCB", referencedColumnName = "CODEMP", insertable = false, updatable = false),
			@JoinColumn(name = "CODBANCO", referencedColumnName = "CODBANCO", insertable = false, updatable = false),
			@JoinColumn(name = "CODFILIALBO", referencedColumnName = "CODFILIALBO", insertable = false, updatable = false),
			@JoinColumn(name = "CODEMPBO", referencedColumnName = "CODEMPBO", insertable = false, updatable = false) })
	public Fncartcob getFncartcob() {
		return this.fncartcob;
	}

	public void setFncartcob(Fncartcob fncartcob) {
		this.fncartcob = fncartcob;
	}

	@Column(name = "VLRREC", nullable = false, precision = 15, scale = 5)
	public BigDecimal getVlrrec() {
		return this.vlrrec;
	}

	public void setVlrrec(BigDecimal vlrrec) {
		this.vlrrec = vlrrec;
	}

	@Column(name = "VLRDESCREC", precision = 15, scale = 5)
	public BigDecimal getVlrdescrec() {
		return this.vlrdescrec;
	}

	public void setVlrdescrec(BigDecimal vlrdescrec) {
		this.vlrdescrec = vlrdescrec;
	}

	@Column(name = "VLRMULTAREC", precision = 15, scale = 5)
	public BigDecimal getVlrmultarec() {
		return this.vlrmultarec;
	}

	public void setVlrmultarec(BigDecimal vlrmultarec) {
		this.vlrmultarec = vlrmultarec;
	}

	@Column(name = "VLRJUROSREC", precision = 15, scale = 5)
	public BigDecimal getVlrjurosrec() {
		return this.vlrjurosrec;
	}

	public void setVlrjurosrec(BigDecimal vlrjurosrec) {
		this.vlrjurosrec = vlrjurosrec;
	}

	@Column(name = "VLRDEVREC", nullable = false, precision = 15, scale = 5)
	public BigDecimal getVlrdevrec() {
		return this.vlrdevrec;
	}

	public void setVlrdevrec(BigDecimal vlrdevrec) {
		this.vlrdevrec = vlrdevrec;
	}

	@Column(name = "VLRPARCREC", nullable = false, precision = 15, scale = 5)
	public BigDecimal getVlrparcrec() {
		return this.vlrparcrec;
	}

	public void setVlrparcrec(BigDecimal vlrparcrec) {
		this.vlrparcrec = vlrparcrec;
	}

	@Column(name = "VLRPAGOREC", precision = 15, scale = 5)
	public BigDecimal getVlrpagorec() {
		return this.vlrpagorec;
	}

	public void setVlrpagorec(BigDecimal vlrpagorec) {
		this.vlrpagorec = vlrpagorec;
	}

	@Column(name = "VLRAPAGREC", precision = 15, scale = 5)
	public BigDecimal getVlrapagrec() {
		return this.vlrapagrec;
	}

	public void setVlrapagrec(BigDecimal vlrapagrec) {
		this.vlrapagrec = vlrapagrec;
	}

	@Column(name = "VLRBASECOMIS", precision = 15, scale = 5)
	public BigDecimal getVlrbasecomis() {
		return this.vlrbasecomis;
	}

	public void setVlrbasecomis(BigDecimal vlrbasecomis) {
		this.vlrbasecomis = vlrbasecomis;
	}

	@Column(name = "VLRRETENSAOISS", precision = 15, scale = 5)
	public BigDecimal getVlrretensaoiss() {
		return this.vlrretensaoiss;
	}

	public void setVlrretensaoiss(BigDecimal vlrretensaoiss) {
		this.vlrretensaoiss = vlrretensaoiss;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTCOMPREC", nullable = false, length = 10)
	public Date getDtcomprec() {
		return this.dtcomprec;
	}

	public void setDtcomprec(Date dtcomprec) {
		this.dtcomprec = dtcomprec;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATAREC", nullable = false, length = 10)
	public Date getDatarec() {
		return this.datarec;
	}

	public void setDatarec(Date datarec) {
		this.datarec = datarec;
	}

	@Column(name = "STATUSREC", nullable = false, length = 2)
	public String getStatusrec() {
		return this.statusrec;
	}

	public void setStatusrec(String statusrec) {
		this.statusrec = statusrec;
	}

	@Column(name = "VLRCOMIREC", precision = 15, scale = 5)
	public BigDecimal getVlrcomirec() {
		return this.vlrcomirec;
	}

	public void setVlrcomirec(BigDecimal vlrcomirec) {
		this.vlrcomirec = vlrcomirec;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTQUITREC", length = 10)
	public Date getDtquitrec() {
		return this.dtquitrec;
	}

	public void setDtquitrec(Date dtquitrec) {
		this.dtquitrec = dtquitrec;
	}

	@Column(name = "DOCREC", nullable = false)
	public int getDocrec() {
		return this.docrec;
	}

	public void setDocrec(int docrec) {
		this.docrec = docrec;
	}

	@Column(name = "NROPARCREC")
	public Integer getNroparcrec() {
		return this.nroparcrec;
	}

	public void setNroparcrec(Integer nroparcrec) {
		this.nroparcrec = nroparcrec;
	}

	@Column(name = "OBSREC", length = 250)
	public String getObsrec() {
		return this.obsrec;
	}

	public void setObsrec(String obsrec) {
		this.obsrec = obsrec;
	}

	@Column(name = "FLAG", length = 1)
	public Character getFlag() {
		return this.flag;
	}

	public void setFlag(Character flag) {
		this.flag = flag;
	}

	@Column(name = "ALTUSUREC", length = 1)
	public Character getAltusurec() {
		return this.altusurec;
	}

	public void setAltusurec(Character altusurec) {
		this.altusurec = altusurec;
	}

	@Column(name = "EMMANUT", nullable = false, length = 1)
	public char getEmmanut() {
		return this.emmanut;
	}

	public void setEmmanut(char emmanut) {
		this.emmanut = emmanut;
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

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnreceber")
	public Set getFnitrecebers() {
		return this.fnitrecebers;
	}

	public void setFnitrecebers(Set fnitrecebers) {
		this.fnitrecebers = fnitrecebers;
	}
*/
}
