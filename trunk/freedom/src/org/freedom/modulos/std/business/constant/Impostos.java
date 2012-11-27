package org.freedom.modulos.std.business.constant;

import java.util.ArrayList;
import java.util.List;



public enum Impostos {
	
	IPI("IPI", "Imposto sobre Produto Industrializado"), 
	PIS("PIS", "Programa de integra��o social "),
	COFINS("COFINS", "Contribui��o Social para o Financiamento da Seguridade Social"),
	ICMS("ICMS", "Imposto sobre Circula��o de Mercadorias e Servi�os"),
	ISS("ISS","Impostos sobre Servi�os de Qualquer Natureza"),
	FUNRURAL("FUNRURAL","Contribui��o ao Funrural "),
	IR("IR","Imposto sobre a Renda e proventos de qualquer natureza"),
	II("II", "Imposto sobre a importa��o de produtos estrangeiros"),
	TXSISCOMEX("TXSISCOMEX", "Taxa Siscomex"),
	ICMSDIF("ICMSDIF", "Imposto sobre Circula��o de Mercadorias e Servi�os Diferido"),
	ICMSPRES("ICMSPRES", "Imposto sobre Circula��o de Mercadorias e Servi�os Presumido")
	;
	
	private String value;
	
	private String desc;

	private Impostos(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	
	public String getDesc() {
	
		return desc;
	}

	public void setDesc( String desc ) {
	
		this.desc = desc;
	}

	public String getValue() {
	
		return value;
	}

	public void setValue( String value ) {
	
		this.value = value;
	}
	
	public static List<Impostos> getImpostos() {
		List<Impostos> listaImp = new ArrayList<Impostos>();
		
		for (Impostos imp: Impostos.values()) {
			listaImp.add( imp );
		}
	
		return listaImp;
	}
	
}
