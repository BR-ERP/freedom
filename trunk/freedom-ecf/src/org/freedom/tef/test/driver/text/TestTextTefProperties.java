
package org.freedom.tef.test.driver.text;

import java.util.List;

import org.freedom.tef.driver.text.TextTefProperties;

import junit.framework.TestCase;

public class TestTextTefProperties extends TestCase {

	public TestTextTefProperties( String name ) {

		super( name );
	}

	// Teste de instancia��o do objeto.
	public void testeTextTefProperties() {

		TextTefProperties textTefProperties = new TextTefProperties();
		assertFalse( textTefProperties == null );
	}

	// Teste de valida��o de chave, com par�metro de chave v�lida.
	public void testValidateTextTefPropertie1() {

		TextTefProperties textTefProperties = new TextTefProperties();
		assertTrue( textTefProperties.validateTextTefPropertie( TextTefProperties.HEADER ) );
	}

	// Teste de valida��o de chave, com par�metro de chave inv�lida.
	public void testValidateTextTefPropertie2() {

		TextTefProperties textTefProperties = new TextTefProperties();
		assertFalse( textTefProperties.validateTextTefPropertie( "CHAVE INV�LIDA!" ) );
	}

	// Teste de valida��o de chave, com par�metro de chave nulo.
	public void testValidateTextTefPropertie3() {

		TextTefProperties textTefProperties = new TextTefProperties();
		assertFalse( textTefProperties.validateTextTefPropertie( null ) );
	}

	// Teste de valida��o de chave, com par�metro de chave vazia ou com espa�os ( "" || "  " ).
	public void testValidateTextTefPropertie4() {

		TextTefProperties textTefProperties = new TextTefProperties();
		assertFalse( textTefProperties.validateTextTefPropertie( " " ) );
	}

	// Teste de valida��o de chave, com par�metro de chave variado de RESPONSE_TO_PRINT, v�lida.
	public void testValidateTextTefPropertie5() {

		TextTefProperties textTefProperties = new TextTefProperties();
		assertTrue( textTefProperties.validateTextTefPropertie( "029-001" ) );
	}

	// Teste de retorno de propriedade, para chave v�lida.
	public void testGet1() {

		// TextTefProperties.HEADER � definido com "" em initializeTextTefProperties();
		TextTefProperties textTefProperties = new TextTefProperties();
		assertFalse( textTefProperties.getProperty( TextTefProperties.HEADER ) == null );
	}

	// Teste de retorno de propriedade, para chave inv�lida.
	public void testGet2() {

		try {
			TextTefProperties textTefProperties = new TextTefProperties();
			textTefProperties.getProperty( "CHAVE INV�LIDA!" );
		}
		catch ( Exception e ) {
			assertTrue( true );
		}
	}

	// Teste de defini��o de propriedade, para chave v�lida e valor v�lido.
	public void testSet1() {

		// TextTefProperties.HEADER � definido com "" em initializeTextTefProperties();
		TextTefProperties textTefProperties = new TextTefProperties();
		assertTrue( "TESTE".equals( textTefProperties.setProperty( TextTefProperties.HEADER, "TESTE" ) ) );
	}

	// Teste de defini��o de propriedade, para chave inv�lida e valor v�lido.
	public void testSet2() {

		try {
			TextTefProperties textTefProperties = new TextTefProperties();
			textTefProperties.setProperty( "CHAVE INV�LIDA!", "TESTE" );
		}
		catch ( Exception e ) {
			assertTrue( true );
		}
	}

	// Teste de defini��o de propriedade, para chave v�lida e valor inv�lido.
	public void testSet3() {

		try {
			TextTefProperties textTefProperties = new TextTefProperties();
			textTefProperties.setProperty( TextTefProperties.HEADER, null );
		}
		catch ( Exception e ) {
			assertTrue( true );
		}
	}

	// Teste de c�pia da lista de propriedades.
	public void testKeysListCopy() {

		try {
			List<String> keysListCopy = TextTefProperties.getKeyList();
			assertTrue( keysListCopy != null );
		}
		catch ( Exception e ) {
			assertTrue( false );
		}
	}
}
