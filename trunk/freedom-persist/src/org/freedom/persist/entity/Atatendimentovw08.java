package org.freedom.persist.entity;

// Generated 12/05/2014 09:11:34 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Atatendimentovw08 generated by hbm2java
 */
@Entity
@Table(name = "ATATENDIMENTOVW08")
public class Atatendimentovw08 implements java.io.Serializable {

	private Atatendimentovw08Id id;

	public Atatendimentovw08() {
	}

	public Atatendimentovw08(Atatendimentovw08Id id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "dataatendo", column = @Column(name = "DATAATENDO", length = 10)),
			@AttributeOverride(name = "dtfincontr", column = @Column(name = "DTFINCONTR", length = 10)),
			@AttributeOverride(name = "codempae", column = @Column(name = "CODEMPAE")),
			@AttributeOverride(name = "codfilialae", column = @Column(name = "CODFILIALAE")),
			@AttributeOverride(name = "codatend", column = @Column(name = "CODATEND")),
			@AttributeOverride(name = "nomeatend", column = @Column(name = "NOMEATEND", length = 50)),
			@AttributeOverride(name = "codempct", column = @Column(name = "CODEMPCT")),
			@AttributeOverride(name = "codfilialct", column = @Column(name = "CODFILIALCT")),
			@AttributeOverride(name = "codcontr", column = @Column(name = "CODCONTR")),
			@AttributeOverride(name = "coditcontr", column = @Column(name = "CODITCONTR")),
			@AttributeOverride(name = "desccontr", column = @Column(name = "DESCCONTR", length = 80)),
			@AttributeOverride(name = "descitcontr", column = @Column(name = "DESCITCONTR", length = 80)),
			@AttributeOverride(name = "tpcobcontr", column = @Column(name = "TPCOBCONTR", length = 2)),
			@AttributeOverride(name = "sitcontr", column = @Column(name = "SITCONTR", length = 2)),
			@AttributeOverride(name = "totalcomis", column = @Column(name = "TOTALCOMIS", precision = 0, scale = 4)),
			@AttributeOverride(name = "codempoc", column = @Column(name = "CODEMPOC")),
			@AttributeOverride(name = "codfilialoc", column = @Column(name = "CODFILIALOC")),
			@AttributeOverride(name = "tipoorc", column = @Column(name = "TIPOORC", length = 1)),
			@AttributeOverride(name = "codorc", column = @Column(name = "CODORC")) })
	public Atatendimentovw08Id getId() {
		return this.id;
	}

	public void setId(Atatendimentovw08Id id) {
		this.id = id;
	}

}
