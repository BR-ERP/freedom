/**
 * @version 30/07/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.fnc.view.frame.crud.plain <BR>
 *         Classe: @(#)FTalaoCheq.java <BR>
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

package org.freedom.modulos.fnc.view.frame.crud.plain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.fnc.view.frame.crud.tabbed.FConta;

public class FTalaoCheq extends FDados implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtNumconta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescconta = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSeqtalao = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );
	
	private JTextFieldPad txtDttalao = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JCheckBoxPad cbxAtivotalao = new JCheckBoxPad( "Ativo", "S", "N" );
	
	private JTextFieldPad txtCheqinitalao = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCheqfimtalao = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCheqatualtalao = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private ListaCampos lcConta = new ListaCampos( this, "" );

	public FTalaoCheq() {

		super();
		setTitulo( "Cadastro de Talon�rio de Cheques" );
		setAtribos( 50, 50, 450, 310 );

		lcConta.add( new GuardaCampo( txtNumconta, "Numconta", "N�mero conta", ListaCampos.DB_PK, txtDescconta, true ) );
		lcConta.add( new GuardaCampo( txtDescconta, "Descconta", "Descri�ao do conta", ListaCampos.DB_SI, null, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setQueryCommit( false );
		lcConta.setReadOnly( true );
		txtNumconta.setTabelaExterna( lcConta, FConta.class.getCanonicalName() );

		adicCampo( txtNumconta, 7, 20, 80, 20, "Numconta", "N�mero conta", ListaCampos.DB_PF, true );
		adicCampo( txtSeqtalao, 90, 20, 60, 20, "Seqtalao", "Sequencia", ListaCampos.DB_PK, true);
		adicDescFK( txtDescconta, 153, 20, 210, 20, "Descconta", "Descri��o da conta" );
		adicCampo( txtDttalao, 7, 60, 70, 20, "Dttalao", "Data", ListaCampos.DB_SI, true );
		adicDB( cbxAtivotalao, 70, 60, 70, 20, "DvBanco", "Ativo", true );
		
		adicCampo( txtCheqinitalao, 7, 100, 80, 20, "Cheqinitalao", "Cheque inicial", ListaCampos.DB_SI, false );
		adicCampo( txtCheqfimtalao, 90, 100, 80, 20, "Cheqfimtalao", "Cheque final", ListaCampos.DB_SI, false );
		adicCampo( txtCheqfimtalao, 173, 100, 80, 20, "Cheqatualtalao", "Cheque final", ListaCampos.DB_SI, false );
		setListaCampos( false, "TALAOCHEQ", "FN" );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );
		setImprimir( true );
		
	}

	public void actionPerformed( ActionEvent evt ) {

	}

	private void imprimir( boolean bVisualizar ) {

	}

	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcConta.setConexao( cn );
	}
}
