/**
 * @version 26/09/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm <BR>
 *         Classe: @(#)FMarcador.java <BR>
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
 *         Tela de cadastro de Marcador.
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.plain;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FMarcador extends FDados {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodMarCor= new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescMarCor = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtVermaCor = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
		
		
	public FMarcador( ) {

		super();
		montaTela();

	}
	
	private void montaTela(){
		
		setTitulo( "Marcador" );
		setAtribos( 50, 50, 500, 200 );
		
		adicCampo( txtCodMarCor, 7, 20, 100, 20, "CODMARCOR", "Cod.Marcor", ListaCampos.DB_PK, true );		
		adicCampo( txtDescMarCor, 110, 20, 350, 20, "DESCMARCOR", "Descri��o do Marcador", ListaCampos.DB_SI, true );
		adicCampo( txtVermaCor, 7, 63, 100, 20, "VERMARCOR", "Vers�o", ListaCampos.DB_SI, false );
		
		setListaCampos( true, "MARCADOR", "CR" );
	
	}

	private void montaListaCampos() {	

	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
	}
}