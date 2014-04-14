package org.freedom.persist.entity;

// Generated 14/04/2014 10:17:08 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * Cpcompralcchave generated by hbm2java
 */
public class Cpcompralcchave implements java.io.Serializable {

	private long id;
	private int codemp;
	private int codfilial;
	private int codcompra;
	private Date dtconsulta;
	private Date hconsulta;
	private int codretorno;
	private String mensagem;
	private char chavevalida;
	private String chavenfe;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;

	public Cpcompralcchave() {
	}

	public Cpcompralcchave(long id, int codemp, int codfilial, int codcompra,
			Date dtconsulta, Date hconsulta, int codretorno, String mensagem,
			char chavevalida, String chavenfe, Date dtins, Date hins,
			String idusuins) {
		this.id = id;
		this.codemp = codemp;
		this.codfilial = codfilial;
		this.codcompra = codcompra;
		this.dtconsulta = dtconsulta;
		this.hconsulta = hconsulta;
		this.codretorno = codretorno;
		this.mensagem = mensagem;
		this.chavevalida = chavevalida;
		this.chavenfe = chavenfe;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Cpcompralcchave(long id, int codemp, int codfilial, int codcompra,
			Date dtconsulta, Date hconsulta, int codretorno, String mensagem,
			char chavevalida, String chavenfe, Date dtins, Date hins,
			String idusuins, Date dtalt, Date halt, String idusualt) {
		this.id = id;
		this.codemp = codemp;
		this.codfilial = codfilial;
		this.codcompra = codcompra;
		this.dtconsulta = dtconsulta;
		this.hconsulta = hconsulta;
		this.codretorno = codretorno;
		this.mensagem = mensagem;
		this.chavevalida = chavevalida;
		this.chavenfe = chavenfe;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCodemp() {
		return this.codemp;
	}

	public void setCodemp(int codemp) {
		this.codemp = codemp;
	}

	public int getCodfilial() {
		return this.codfilial;
	}

	public void setCodfilial(int codfilial) {
		this.codfilial = codfilial;
	}

	public int getCodcompra() {
		return this.codcompra;
	}

	public void setCodcompra(int codcompra) {
		this.codcompra = codcompra;
	}

	public Date getDtconsulta() {
		return this.dtconsulta;
	}

	public void setDtconsulta(Date dtconsulta) {
		this.dtconsulta = dtconsulta;
	}

	public Date getHconsulta() {
		return this.hconsulta;
	}

	public void setHconsulta(Date hconsulta) {
		this.hconsulta = hconsulta;
	}

	public int getCodretorno() {
		return this.codretorno;
	}

	public void setCodretorno(int codretorno) {
		this.codretorno = codretorno;
	}

	public String getMensagem() {
		return this.mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public char getChavevalida() {
		return this.chavevalida;
	}

	public void setChavevalida(char chavevalida) {
		this.chavevalida = chavevalida;
	}

	public String getChavenfe() {
		return this.chavenfe;
	}

	public void setChavenfe(String chavenfe) {
		this.chavenfe = chavenfe;
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
