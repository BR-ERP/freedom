package org.freedom.library.business.component.test;

import java.math.BigDecimal;
import java.util.Date;

import org.freedom.library.business.component.Sicredi;
import org.freedom.library.functions.Funcoes;

public class TesteSicred {

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
		String modalidade = "01";
		String banco = "748";
		String dvbanco = "0";
		Long doc = new Long(10236);
		Long seq = new Long(47);
		Long codrec = new Long(9930);
		Long nparc = new Long(1);
		Sicredi boleto = new Sicredi();
		String convenio = agencia+posto+conta;
		String moeda = "9";
		Long fatorVenc = new Long(5561);
		String tiponossonumero = "S"; // Sequencial
		Date data = Funcoes.encodeDate(2012, 10, 30); // 30.11.2012
		Date vencto = Funcoes.encodeDate(2012, 11, 28); // 28.12.2012

		BigDecimal valortit = new BigDecimal(825.00f);
		
		String nossonumero = boleto.geraNossoNumero(tiponossonumero,modalidade, agencia+posto+conta, doc, seq, codrec, nparc, data, true );
		String codebar = boleto.geraCodBar(banco,moeda,dvbanco, fatorVenc, valortit, convenio, tiponossonumero,  doc, seq, codrec, nparc, data, agencia, conta, "", modalidade );
		String linhadigitavel = boleto.geraLinhaDig(codebar, fatorVenc, valortit);

		System.out.println("Nosso n�mero");
		System.out.println(nossonumero);
		System.out.println(nossonumero.length());
		System.out.println("C�digo de barras");
		System.out.println(codebar);
		System.out.println(codebar.length());
		System.out.println("Linha digit�vel");
		System.out.println(linhadigitavel);
		System.out.println(linhadigitavel.length());
		
		//System.out.println("Digito nosso n�mero: " + boleto.digVerif("17224100056", 11, true));
		
		//System.out.println("A" + ( (int) (byte) 'A') );
		//Integer tcast = new Integer(65);
		//System.out.println((char) tcast.intValue() );

	}

}
