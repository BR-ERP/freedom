
package org.freedom.tef.driver.dedicate;

public enum Modality {
	MENU( 0, "Menu" ), CHEQUE( 1, "Cheque" ), DEBITO( 2, "D�bito" ), CREDITO( 3, "Cr�dito" ), VOUCHER( 4, "Voucher" ), DINHEIRO( 98, "Dinheiro" ), OUTRA( 99, "Outra" );

	private Integer code;

	private String name;

	private Modality( Integer code, String name ) {

		this.code = code;
		this.name = name;
	}

	Integer code() {

		return this.code;
	}

	String getName() {

		return this.name;
	}
}
