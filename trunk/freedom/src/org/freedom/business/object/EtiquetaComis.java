/**
 * @version 16/09/2008 <BR>
 * @author Setpoint Inform�tica Ltda.
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)ObjetoEtiquetaComis.java <BR>
 * 
 *                              Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                              modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                              na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                              Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                              sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                              Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                              Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                              de acordo com os termos da LPG-PC <BR>
 * <BR>
 */
package org.freedom.business.object;

import org.freedom.library.business.object.Etiqueta;

public class EtiquetaComis extends Etiqueta {

	public EtiquetaComis() {

		adicOpcao( "C�digo do vendedor", "#CODIGO#", "CODVEND", new Integer( 8 ), null, "C�d.Vend." );
		adicOpcao( "Nome do vendedor", "#NOME#", "NOMEVEND", new Integer( 40 ), null, "Nome Vend." );
		adicOpcao( "Endere�o do Vendedor", "#ENDERE�O#", "ENDVEND", new Integer( 50 ), null, "Endere�o" );
		adicOpcao( "N�mero", "#NUMERO#", "NUMVEND", new Integer( 20 ), null, "Nro." );
		adicOpcao( "Complemento", "#COMPLCLI#", "COMPLCLI", new Integer( 50 ), null, "Complemento" );
		adicOpcao( "Bairro do vendedor", "#BAIRRO#", "BAIRVEND", new Integer( 30 ), null, "Bairro" );
		adicOpcao( "Cidade do vendedor", "#CIDADE#", "CIDVEND", new Integer( 30 ), null, "Cidade" );
		adicOpcao( "CEP", "#CEP#", "CEPVEND", new Integer( 9 ), "#####-###", "Cep" );
		adicOpcao( "UF do Vendedor", "#UF#", "UFVEND", new Integer( 2 ), null, "UF" );

		setNometabela( "VDVENDEDOR" );
		setPK();

	}

	public void setPK() {

		this.PK = "CODVEND";
	}

}
