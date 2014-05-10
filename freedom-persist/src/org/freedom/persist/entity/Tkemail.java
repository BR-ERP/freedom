package org.freedom.persist.entity;

// Generated 10/05/2014 10:27:15 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Tkemail generated by hbm2java
 */
public class Tkemail implements java.io.Serializable {

	private TkemailId id;
	private Tkconfemail tkconfemail;
	private String descemail;
	private String assunto;
	private String corpo;
	private String formato;
	private String charset;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set sgprefere3sForSgprefere3fktkemec = new HashSet(0);
	private Set sgprefere3sForSgprefere3fktkemai = new HashSet(0);
	private Set sgprefere3sForSgprefere3fktkeme2 = new HashSet(0);
	private Set sgprefere3sForSgprefere3fktkemea = new HashSet(0);
	private Set sgprefere3sForSgprefere3fktkemen = new HashSet(0);
	private Set tkcampanhaemails = new HashSet(0);

	public Tkemail() {
	}

	public Tkemail(TkemailId id, Tkconfemail tkconfemail, String descemail,
			String assunto, String corpo, String formato, String charset,
			Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.tkconfemail = tkconfemail;
		this.descemail = descemail;
		this.assunto = assunto;
		this.corpo = corpo;
		this.formato = formato;
		this.charset = charset;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Tkemail(TkemailId id, Tkconfemail tkconfemail, String descemail,
			String assunto, String corpo, String formato, String charset,
			Date dtins, Date hins, String idusuins, Date dtalt, Date halt,
			String idusualt, Set sgprefere3sForSgprefere3fktkemec,
			Set sgprefere3sForSgprefere3fktkemai,
			Set sgprefere3sForSgprefere3fktkeme2,
			Set sgprefere3sForSgprefere3fktkemea,
			Set sgprefere3sForSgprefere3fktkemen, Set tkcampanhaemails) {
		this.id = id;
		this.tkconfemail = tkconfemail;
		this.descemail = descemail;
		this.assunto = assunto;
		this.corpo = corpo;
		this.formato = formato;
		this.charset = charset;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.sgprefere3sForSgprefere3fktkemec = sgprefere3sForSgprefere3fktkemec;
		this.sgprefere3sForSgprefere3fktkemai = sgprefere3sForSgprefere3fktkemai;
		this.sgprefere3sForSgprefere3fktkeme2 = sgprefere3sForSgprefere3fktkeme2;
		this.sgprefere3sForSgprefere3fktkemea = sgprefere3sForSgprefere3fktkemea;
		this.sgprefere3sForSgprefere3fktkemen = sgprefere3sForSgprefere3fktkemen;
		this.tkcampanhaemails = tkcampanhaemails;
	}

	public TkemailId getId() {
		return this.id;
	}

	public void setId(TkemailId id) {
		this.id = id;
	}

	public Tkconfemail getTkconfemail() {
		return this.tkconfemail;
	}

	public void setTkconfemail(Tkconfemail tkconfemail) {
		this.tkconfemail = tkconfemail;
	}

	public String getDescemail() {
		return this.descemail;
	}

	public void setDescemail(String descemail) {
		this.descemail = descemail;
	}

	public String getAssunto() {
		return this.assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getCorpo() {
		return this.corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public String getFormato() {
		return this.formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
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

	public Set getSgprefere3sForSgprefere3fktkemec() {
		return this.sgprefere3sForSgprefere3fktkemec;
	}

	public void setSgprefere3sForSgprefere3fktkemec(
			Set sgprefere3sForSgprefere3fktkemec) {
		this.sgprefere3sForSgprefere3fktkemec = sgprefere3sForSgprefere3fktkemec;
	}

	public Set getSgprefere3sForSgprefere3fktkemai() {
		return this.sgprefere3sForSgprefere3fktkemai;
	}

	public void setSgprefere3sForSgprefere3fktkemai(
			Set sgprefere3sForSgprefere3fktkemai) {
		this.sgprefere3sForSgprefere3fktkemai = sgprefere3sForSgprefere3fktkemai;
	}

	public Set getSgprefere3sForSgprefere3fktkeme2() {
		return this.sgprefere3sForSgprefere3fktkeme2;
	}

	public void setSgprefere3sForSgprefere3fktkeme2(
			Set sgprefere3sForSgprefere3fktkeme2) {
		this.sgprefere3sForSgprefere3fktkeme2 = sgprefere3sForSgprefere3fktkeme2;
	}

	public Set getSgprefere3sForSgprefere3fktkemea() {
		return this.sgprefere3sForSgprefere3fktkemea;
	}

	public void setSgprefere3sForSgprefere3fktkemea(
			Set sgprefere3sForSgprefere3fktkemea) {
		this.sgprefere3sForSgprefere3fktkemea = sgprefere3sForSgprefere3fktkemea;
	}

	public Set getSgprefere3sForSgprefere3fktkemen() {
		return this.sgprefere3sForSgprefere3fktkemen;
	}

	public void setSgprefere3sForSgprefere3fktkemen(
			Set sgprefere3sForSgprefere3fktkemen) {
		this.sgprefere3sForSgprefere3fktkemen = sgprefere3sForSgprefere3fktkemen;
	}

	public Set getTkcampanhaemails() {
		return this.tkcampanhaemails;
	}

	public void setTkcampanhaemails(Set tkcampanhaemails) {
		this.tkcampanhaemails = tkcampanhaemails;
	}

}
