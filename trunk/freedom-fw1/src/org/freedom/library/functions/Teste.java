package org.freedom.library.functions;

import java.math.BigDecimal;
import java.util.Date;

//import org.freedom.library.business.component.BancodoBrasil;
import org.freedom.library.business.component.Sicredi;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Date dtBase = Funcoes.encodeDate( 1997, 10, 7 );
		// Date dtVencto = Funcoes.encodeDate( 2007, 8, 23 );
		// Long fatvenc = new Long(Funcoes.getNumDiasAbs( dtVencto,
		// Funcoes.encodeDate( 1997, 10, 7 ) ));
		// BigDecimal vlrtit = new BigDecimal(2000);
		/*
		 * String barra = org.freedom.funcoes.Boleto.geraCodBar( "1", "9", "9",
		 * fatvenc, vlrtit, "1421609", new Long(1), new Long(16), "3275-1",
		 * "5688-X", "17", "00" );
		 */
		// String linhadig = Boleto.geraLinhaDig( barra, fatvenc, vlrtit );
		// System.out.println(barra);
		// System.out.println(barra.length());
		// System.out.println(linhadig);
		// System.out.println(linhadig.length());

		// System.out.println(org.freedom.funcoes.Boleto.geraNossoNumero( "00",
		// "1421609", new Long(1), new Long(1) ));
		// System.out.println("Digito verif."+Boleto.digVerif(
		// "0019340100000000330375031750416060680935011" , 11 ));

		// System.out.println("Digito campo1: "+Boleto.digVerif( "001903477", 10
		// ));
		// System.out.println("Digito campo2: "+Boleto.digVerif( "9483981606",
		// 10 ));
		// System.out.println("Digito campo3: "+Boleto.digVerif( "0680935011",
		// 10 ));
		// System.out.println("Digito campo1: "+Boleto.digVerif( "", 10 ));
		//BancodoBrasil boleto = new BancodoBrasil();
		String agencia = "0726";
		String posto = "17";
		String conta = "19221";
		String modalidade = "11";
		Long doc = new Long(45454);
		Long seq = new Long(1);
		Long codrec = new Long(7472);
		Long nparc = new Long(1);
		Sicredi boleto = new Sicredi();
		
		Date data = Funcoes.encodeDate(2012, 05, 14);
		BigDecimal valortit = new BigDecimal(999999.00f);
		String nossonumero = boleto.geraNossoNumero("S",modalidade, agencia+posto+conta, doc, seq, codrec, nparc, data, true );
		System.out.println(nossonumero);
		System.out.println(nossonumero.length());
		String codebar = boleto.geraCodBar("748","9","", new Long(9999), valortit, "01650200623", "",  new Long(1048), new Long(3), new Long(1081), new Long(3), data, agencia, conta, "", modalidade );
		String linhadigitavel = boleto.geraLinhaDig(codebar, new Long(9999), valortit);
		System.out.println(codebar);
		System.out.println(codebar.length());
		System.out.println(linhadigitavel);
		System.out.println(linhadigitavel.length());
		
		//System.out.println("Digito nosso n�mero: " + boleto.digVerif("17224100056", 11, true));
		
		System.out.println("A" + ( (int) (byte) 'A') );
		Integer tcast = new Integer(65);
		System.out.println((char) tcast.intValue() );

	}

}
