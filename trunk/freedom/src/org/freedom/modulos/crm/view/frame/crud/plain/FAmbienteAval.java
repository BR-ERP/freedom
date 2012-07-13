/**
 * @version 20/04/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm <BR>
 *         Classe: @(#)FQualificacao.java <BR>
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
 *         Tela de cadastro de qualifica��es de chamados.
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.plain;

import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FAmbienteAval extends FDados {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodAmbAval= new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescAmbAval = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtSiglaAmbAval = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	public FAmbienteAval() {

		super();

		nav.setNavigation( true );

		setTitulo( "Ambiente" );
		
		setAtribos( 50, 50, 480, 150);
		
		adicCampo( txtCodAmbAval, 7, 20, 100, 20, "CodAmbAval", "C�d.Amb.Aval.", ListaCampos.DB_PK, true );		
		adicCampo( txtDescAmbAval, 110, 20, 300, 20, "DescAmbAval", "Descri��o do ambiente da avalia��o", ListaCampos.DB_SI, true );
		adicCampo( txtSiglaAmbAval, 413, 20, 45, 20, "SiglaAmbAval", "Sigla", ListaCampos.DB_SI, true );
		
		setListaCampos( true, "AMBIENTEAVAL", "CR" );
		
	
		lcCampos.setQueryInsert( false );
		
	}

}
