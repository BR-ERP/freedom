package org.freedom.persist.entity;

// Generated 30/01/2013 08:30:43 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Vdvendaconsig generated by hbm2java
 */
@Entity
@Table(name = "VDVENDACONSIG")
public class Vdvendaconsig implements java.io.Serializable {

	private VdvendaconsigId id;
	private Eqproduto eqproduto;
	private Vdconsignacao vdconsignacao;
	private Vdcliente vdcliente;
	private Fnplanopag fnplanopag;
	private Fnsublanca fnsublancaByVdvendaconsigfkfnsublancasc;
	private Vditvenda vditvenda;
	private Fnsublanca fnsublancaByVdvendaconsigfkfnsublanca;
	private BigDecimal preco;
	private BigDecimal precovenda;
	private BigDecimal qtdvendaco;
	private BigDecimal qtdtroca;
	private BigDecimal qtdbonif;
	private BigDecimal desconto;
	private char recebido;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Vdvendaconsig() {
	}

	public Vdvendaconsig(VdvendaconsigId id, Eqproduto eqproduto,
			Vdconsignacao vdconsignacao, Vdcliente vdcliente,
			Fnplanopag fnplanopag, BigDecimal preco, BigDecimal qtdvendaco,
			BigDecimal qtdtroca, BigDecimal qtdbonif, char recebido,
			Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.eqproduto = eqproduto;
		this.vdconsignacao = vdconsignacao;
		this.vdcliente = vdcliente;
		this.fnplanopag = fnplanopag;
		this.preco = preco;
		this.qtdvendaco = qtdvendaco;
		this.qtdtroca = qtdtroca;
		this.qtdbonif = qtdbonif;
		this.recebido = recebido;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Vdvendaconsig(VdvendaconsigId id, Eqproduto eqproduto,
			Vdconsignacao vdconsignacao, Vdcliente vdcliente,
			Fnplanopag fnplanopag,
			Fnsublanca fnsublancaByVdvendaconsigfkfnsublancasc,
			Vditvenda vditvenda,
			Fnsublanca fnsublancaByVdvendaconsigfkfnsublanca, BigDecimal preco,
			BigDecimal precovenda, BigDecimal qtdvendaco, BigDecimal qtdtroca,
			BigDecimal qtdbonif, BigDecimal desconto, char recebido,
			Date dtins, Date hins, String idusuins, Date dtalt, Date halt,
			String idusualt) {
		this.id = id;
		this.eqproduto = eqproduto;
		this.vdconsignacao = vdconsignacao;
		this.vdcliente = vdcliente;
		this.fnplanopag = fnplanopag;
		this.fnsublancaByVdvendaconsigfkfnsublancasc = fnsublancaByVdvendaconsigfkfnsublancasc;
		this.vditvenda = vditvenda;
		this.fnsublancaByVdvendaconsigfkfnsublanca = fnsublancaByVdvendaconsigfkfnsublanca;
		this.preco = preco;
		this.precovenda = precovenda;
		this.qtdvendaco = qtdvendaco;
		this.qtdtroca = qtdtroca;
		this.qtdbonif = qtdbonif;
		this.desconto = desconto;
		this.recebido = recebido;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codconsig", column = @Column(name = "CODCONSIG", nullable = false)),
			@AttributeOverride(name = "codvendaco", column = @Column(name = "CODVENDACO", nullable = false)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public VdvendaconsigId getId() {
		return this.id;
	}

	public void setId(VdvendaconsigId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODPROD", referencedColumnName = "CODPROD", nullable = false),
			@JoinColumn(name = "CODFILIALPD", referencedColumnName = "CODFILIAL", nullable = false),
			@JoinColumn(name = "CODEMPPD", referencedColumnName = "CODEMP", nullable = false) })
	public Eqproduto getEqproduto() {
		return this.eqproduto;
	}

	public void setEqproduto(Eqproduto eqproduto) {
		this.eqproduto = eqproduto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODCONSIG", referencedColumnName = "CODCONSIG", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "CODFILIAL", referencedColumnName = "CODFILIAL", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "CODEMP", referencedColumnName = "CODEMP", nullable = false, insertable = false, updatable = false) })
	public Vdconsignacao getVdconsignacao() {
		return this.vdconsignacao;
	}

	public void setVdconsignacao(Vdconsignacao vdconsignacao) {
		this.vdconsignacao = vdconsignacao;
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
			@JoinColumn(name = "CODSUBLANCASC", referencedColumnName = "CODSUBLANCA"),
			@JoinColumn(name = "CODLANCASC", referencedColumnName = "CODLANCA"),
			@JoinColumn(name = "CODFILIALSC", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPSC", referencedColumnName = "CODEMP") })
	public Fnsublanca getFnsublancaByVdvendaconsigfkfnsublancasc() {
		return this.fnsublancaByVdvendaconsigfkfnsublancasc;
	}

	public void setFnsublancaByVdvendaconsigfkfnsublancasc(
			Fnsublanca fnsublancaByVdvendaconsigfkfnsublancasc) {
		this.fnsublancaByVdvendaconsigfkfnsublancasc = fnsublancaByVdvendaconsigfkfnsublancasc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODVENDA", referencedColumnName = "CODVENDA"),
			@JoinColumn(name = "CODITVENDA", referencedColumnName = "CODITVENDA"),
			@JoinColumn(name = "TIPOVENDA", referencedColumnName = "TIPOVENDA"),
			@JoinColumn(name = "CODFILIALVD", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPVD", referencedColumnName = "CODEMP") })
	public Vditvenda getVditvenda() {
		return this.vditvenda;
	}

	public void setVditvenda(Vditvenda vditvenda) {
		this.vditvenda = vditvenda;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODSUBLANCA", referencedColumnName = "CODSUBLANCA"),
			@JoinColumn(name = "CODLANCA", referencedColumnName = "CODLANCA"),
			@JoinColumn(name = "CODFILIALSL", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPSL", referencedColumnName = "CODEMP") })
	public Fnsublanca getFnsublancaByVdvendaconsigfkfnsublanca() {
		return this.fnsublancaByVdvendaconsigfkfnsublanca;
	}

	public void setFnsublancaByVdvendaconsigfkfnsublanca(
			Fnsublanca fnsublancaByVdvendaconsigfkfnsublanca) {
		this.fnsublancaByVdvendaconsigfkfnsublanca = fnsublancaByVdvendaconsigfkfnsublanca;
	}

	@Column(name = "PRECO", nullable = false, precision = 15, scale = 5)
	public BigDecimal getPreco() {
		return this.preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	@Column(name = "PRECOVENDA", precision = 15, scale = 5)
	public BigDecimal getPrecovenda() {
		return this.precovenda;
	}

	public void setPrecovenda(BigDecimal precovenda) {
		this.precovenda = precovenda;
	}

	@Column(name = "QTDVENDACO", nullable = false, precision = 15, scale = 5)
	public BigDecimal getQtdvendaco() {
		return this.qtdvendaco;
	}

	public void setQtdvendaco(BigDecimal qtdvendaco) {
		this.qtdvendaco = qtdvendaco;
	}

	@Column(name = "QTDTROCA", nullable = false, precision = 15, scale = 5)
	public BigDecimal getQtdtroca() {
		return this.qtdtroca;
	}

	public void setQtdtroca(BigDecimal qtdtroca) {
		this.qtdtroca = qtdtroca;
	}

	@Column(name = "QTDBONIF", nullable = false, precision = 15, scale = 5)
	public BigDecimal getQtdbonif() {
		return this.qtdbonif;
	}

	public void setQtdbonif(BigDecimal qtdbonif) {
		this.qtdbonif = qtdbonif;
	}

	@Column(name = "DESCONTO", precision = 15, scale = 5)
	public BigDecimal getDesconto() {
		return this.desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	@Column(name = "RECEBIDO", nullable = false, length = 1)
	public char getRecebido() {
		return this.recebido;
	}

	public void setRecebido(char recebido) {
		this.recebido = recebido;
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

}