/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLRClasCli.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;

import java.awt.Component;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;

public class DLRClasCli extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JRadioGroup rgOrdem = null;

	private JRadioGroup rgTipo = null;

	private JLabelPad lbOrdem = new JLabelPad( "Ordenar por:" );

	private Vector vLabs = new Vector();

	private Vector vVals = new Vector();

	private Vector vLabs1 = new Vector();

	private Vector vVals1 = new Vector();

	public DLRClasCli( Component cOrig ) {

		super( cOrig );
		setTitulo( "Ordem do Relat�rio" );
		setAtribos( 300, 190 );
		vLabs.addElement( "C�digo" );
		vLabs.addElement( "Descri��o" );
		vVals.addElement( "C" );
		vVals.addElement( "D" );

		vVals1.addElement( "T" );
		vVals1.addElement( "G" );
		vLabs1.addElement( "Texto" );
		vLabs1.addElement( "Grafico" );

		rgOrdem.setVlrString( "D" );
		rgTipo.setVlrString( "G" );

		rgOrdem = new JRadioGroup( 1, 2, vLabs, vVals );
		rgTipo = new JRadioGroup( 1, 2, vLabs1, vVals1 );

		adic( lbOrdem, 7, 0, 80, 15 );
		adic( rgOrdem, 7, 20, 270, 30 );
		adic( rgTipo, 7, 60, 270, 30 );

	}

	public String getValor() {

		String sRetorno = "";

		if ( rgOrdem.getVlrString().compareTo( "C" ) == 0 ) {
			sRetorno = "CODCLASCLI";
		}
		else if ( rgOrdem.getVlrString().compareTo( "D" ) == 0 ) {
			sRetorno = "DESCCLASCLI";
		}
		
		return sRetorno;
	}
}
