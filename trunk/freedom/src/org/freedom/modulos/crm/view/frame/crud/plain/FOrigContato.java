/**
 * @version 20/01/2009 <BR>
 * @author Setpoint Inform�tica Ltda.
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm <BR>
 *         Classe: @(#)FOrigContato.java <BR>
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
 * 
 */
package org.freedom.modulos.crm.view.frame.crud.plain;

import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FOrigContato extends FDados {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodOrigCont = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtDescOrigCont = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JCheckBoxPad cbWeb = new JCheckBoxPad( "Publicar na web", "S", "N" );

	public FOrigContato() {

		super();
		setTitulo( "Origem do contato" );
		setAtribos( 15, 30, 400, 185 );
		montaTela();
	}

	private void montaTela() {

		adicCampo( txtCodOrigCont, 7, 25, 70, 20, "CodOrigCont", "C�d.Orig.", ListaCampos.DB_PK, true );
		adicCampo( txtDescOrigCont, 80, 25, 290, 20, "DescOrigCont", "Origem do contato", ListaCampos.DB_SI, true );
		adicDB( cbWeb, 7, 50, 120, 20, "web", "", true );

		setListaCampos( true, "ORIGCONT", "TK" );
	}

}
