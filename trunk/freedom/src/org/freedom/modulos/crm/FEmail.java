/**
 * @version 20/01/2009 <BR>
 * @author Setpoint Inform�tica Ltda.
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.crm <BR>
 * Classe: @(#)FEmail.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * 
 */

package org.freedom.modulos.crm;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;

import java.util.Vector;

import org.freedom.telas.FDados;


public class FEmail extends FDados {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodEmail = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtDescEmail = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtAssuntoEmail = new JTextFieldPad( JTextFieldPad.TP_STRING, 120, 0  );
	
	private JTextFieldPad txtCharsetEmail = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0  );
	
	private JTextAreaPad txaCorpoEmail = new JTextAreaPad( 20000 );
	
	private JTextFieldPad txtFormato = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private JTextFieldPad txtCodConfEmail = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtEmailRemet = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );
	
	private JTextFieldPad txtNomeRemet = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private ListaCampos lcConfEmail = new ListaCampos( this, "CM" );
	
	private JRadioGroup<String, String> rgFormato = null;
	
	public FEmail(){
	
		super();
		setTitulo( "Email" );
		setAtribos( 15, 30, 720, 810 );
		
		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();
		vLabs.addElement( "Texto" );
		vLabs.addElement( "Html" );
		vVals.addElement( "text/plain" );
		vVals.addElement( "text/html " );
		rgFormato = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		//rgFormato.setVlrString( vVals.elementAt( 0 ) );
		
		montaListaCampos();
		montaTela();
	
	}
	
	private void montaListaCampos(){
		
		lcConfEmail.add(new GuardaCampo( txtCodConfEmail,  "CodConfEmail", "C�d.Conf.", ListaCampos.DB_PK, null, false ));
		lcConfEmail.add(new GuardaCampo( txtEmailRemet, "EmailRemet", "Email remetente", ListaCampos.DB_SI, null, false ));
		lcConfEmail.add(new GuardaCampo( txtNomeRemet, "NomeRemet", "Nome Remetente", ListaCampos.DB_SI, null, false ));
		lcConfEmail.montaSql( false, "CONFEMAIL", "TK");
		lcConfEmail.setReadOnly( true );
		txtCodConfEmail.setTabelaExterna( lcConfEmail );
		txtCodConfEmail.setFK( true );
		txtCodConfEmail.setNomeCampo( "CodConfEmail" );
	}

	
	private void montaTela(){
		
		adicCampo( txtCodEmail, 7, 25, 70, 20, "CodEmail", "C�d.Email", ListaCampos.DB_PK, true );
		adicCampo( txtDescEmail, 80, 25, 500, 20, "DescEmail", "Descri��o do Email", ListaCampos.DB_SI, true );
		adicCampo( txtAssuntoEmail, 7, 65, 675, 20, "Assunto", "Assunto", ListaCampos.DB_SI, true );
		adicCampo( txtCodConfEmail, 7, 105, 70, 20, "CodConfEmail", "C�d.Conf", ListaCampos.DB_FK, txtEmailRemet, true );
		adicDescFK( txtEmailRemet, 80, 105, 400, 20, "EmailRemet", "Email remetente" );
		adicDB( rgFormato, 483, 105, 200, 30, "Formato", "Formato", true );
		adicCampo( txtCharsetEmail, 586, 25, 97, 20, "Charset", "Charset", ListaCampos.DB_SI, true );		
		adicDB( txaCorpoEmail, 7, 145, 680, 580, "Corpo", "Corpo do email", true );
		
		setListaCampos( true, "EMAIL", "TK" );		
	
	}
	
	public void setConexao( DbConnection cn ){
		
		super.setConexao( cn );
		lcConfEmail.setConexao( cn );
		
	}
}
