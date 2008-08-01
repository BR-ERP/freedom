package org.freedom.tef.driver.dedicate;


public enum Modalidade {
	
	CHEQUE( 0, "Cheque" ),
	DEBITO( 1, "D�bito" ),
	CREDITO( 2, "Cr�dito" ),
	VOUCHER( 3, "Voucher" ),
	DINHEIRO( 98, "Dinheiro" ),
	OUTRA( 99, "Outra" );
	
	private Integer code;
	
	private String name;
	
	private Modalidade( Integer code, String name ) {
		this.code = code;
		this.name = name;
	}
	
	Integer getCode() {
		return this.code;
	}
	
	String getName() {
		return this.name;
	}
}
