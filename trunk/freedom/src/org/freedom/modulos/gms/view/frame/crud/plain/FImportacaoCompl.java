/**
 * @version 30/01/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: projetos.freedom.gms <BR>
 *         Classe: @(#)FAtribuicao.java <BR>
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
 *         Atribui��es das pessoas que comp�em o fluxo da RMA.
 * 
 */

package org.freedom.modulos.gms.view.frame.crud.plain;

import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;

public class FImportacaoCompl extends FFDialogo implements ActionListener, PostListener, CarregaListener {
	
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtID = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtDescAdic = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtVlrDespAdic = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 50, Aplicativo.casasDecPre );
	
	private ListaCampos lcImportacaoCompl = new ListaCampos( this );
	
	
	Integer codemp = null;
	Integer codfilial = null;
	Integer codimp = null;
	
	public FImportacaoCompl(Integer codemp, Integer codfilial, Integer codimp) {
		super();
		setTitulo( "Cadastro de atribui��es" );
		
		this.codemp = codemp;
		this.codfilial = codfilial;
		this.codimp = codimp;
		montaListaCampos();
		montaTela();
		addListener();

		
	}
	
	private void montaListaCampos(){
		
		lcImportacaoCompl.add( new GuardaCampo( txtID, "ID", "C�d.Comp.", ListaCampos.DB_PK, null, false ) );
		lcImportacaoCompl.add( new GuardaCampo( txtDescAdic, "DESCADIC", "Doc.", ListaCampos.DB_SI, null, false ) );
		lcImportacaoCompl.add( new GuardaCampo( txtVlrDespAdic, "DESPADIC", "Serie", ListaCampos.DB_SI, null, false ) );

		txtID.setTabelaExterna( lcImportacaoCompl, null );
		txtID.setNomeCampo( "ID" );
		txtID.setFK( true );

		lcImportacaoCompl.setQueryCommit( false );
		lcImportacaoCompl.setReadOnly( true );

		txtID.setListaCampos( lcImportacaoCompl );
		lcImportacaoCompl.montaSql( false, "IMPORTACAOCOMPL", "CP" );
	}
	
	
	private void addListener(){

	}
	
	
	private void montaTela(){
		setAtribos( 50, 50, 340, 280 );
		adic( txtID, 7, 20, 70, 20, "ID" );
		adic( txtDescAdic, 80, 20, 230, 20, "DESCADIC");
		adic( txtVlrDespAdic, 313, 20, 100, 20, "VLRDESPADIC");

	}


	public void beforePost( PostEvent pevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	
	public void afterPost( PostEvent pevt ) {

		// TODO Auto-generated method stub
		
	}
	
	@ Override
	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		
		lcImportacaoCompl.setConexao( cn );
	}
}
