/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)DLRRomaneio.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Coment�rios sobre a classe...
 */

package org.freedom.modulos.std.view.dialog.report;

import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.dialog.FFDialogo;

import java.util.Vector;

public class DLRRomaneio extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JRadioGroup<?, ?> rgOrdem = null;
	
	private JRadioGroup<?, ?> rgTipoRel = null;

	private JLabelPad lbOrdem = new JLabelPad( "Ordenar por:" );

	private Vector<String> vLabs = new Vector<String>();

	private Vector<String> vVals = new Vector<String>();
	
	private Vector<String> vLabTipoRel = new Vector<String>();

	private Vector<String> vValTipoRel = new Vector<String>();

	public DLRRomaneio() {

		setTitulo( "Ordem do Relat�rio" );
		setAtribos( 300, 190 );
		
		vLabs.addElement( "Refer�ncia" );
		vLabs.addElement( "Descri��o" );
		vLabs.addElement( "Local" );
		
		vVals.addElement( "C" );
		vVals.addElement( "D" );
		vVals.addElement( "L" );
		
		vLabTipoRel.addElement( "Produto" );
		vLabTipoRel.addElement( "Cliente" );
		
		vValTipoRel.addElement( "P" );
		vValTipoRel.addElement( "C" );

		rgOrdem = new JRadioGroup<String, String>( 1, 3, vLabs, vVals );
		rgOrdem.setVlrString( "D" );
		
		rgTipoRel = new JRadioGroup<String, String>( 1, 2, vLabTipoRel, vValTipoRel );
		rgTipoRel.setVlrString( "C" );
		
		adic( lbOrdem, 7, 0, 80, 15 );
		adic( rgOrdem, 7, 20, 280, 30 );
		adic( rgTipoRel, 7, 70, 280, 30 );
		
		
	}

	public String getValor() {

		String sRetorno = "";

		if ( "C".equals( rgOrdem.getVlrString() ) ) {
			sRetorno = "P.REFPROD";
		}
		else if ( "D".equals( rgOrdem.getVlrString() ) ) {
			sRetorno = "P.DESCPROD";
		}
		else if ( "L".equals( rgOrdem.getVlrString() ) ) {
			sRetorno = "P.LOCALPROD";
		}

		return sRetorno;

	}
	
	public String getTipoRel() {
		return rgTipoRel.getVlrString();
	}
	
}
