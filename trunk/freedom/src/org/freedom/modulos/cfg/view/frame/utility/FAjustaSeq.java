/**
 * @version 15/02/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.cfg <BR>
 *         Classe: @(#)FAjustaSeq.java <BR>
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
 * 
 */

package org.freedom.modulos.cfg.view.frame.utility;

import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FAjustaSeq extends FDados {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtSgTab = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtNroSeq = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	public FAjustaSeq() {

		super();
		setTitulo( "Ajusta sequencia" );
		setAtribos( 50, 50, 350, 150 );

		adicCampo( txtSgTab, 7, 20, 50, 20, "SgTab", "Sigla", ListaCampos.DB_PK, true );
		adicCampo( txtNroSeq, 60, 20, 80, 20, "NroSeq", "Sequencia", ListaCampos.DB_SI, true );
		lcCampos.montaSql( false, "SEQUENCIA", "SG" );

	}

}
