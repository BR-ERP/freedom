/**
 * @version 12/08/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.atd <BR>
 *         Classe: @(#)FDetOrc.java <BR>
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
 *         Detalhamento de Or�amento.
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.detail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JScrollPane;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.std.view.frame.crud.detail.FOrcamento;




public class FDetOrc extends FDetalhe implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	//Campos puxado da classe FOrcamento
	private JTextFieldPad txtCodOrc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDtOrc = new JTextFieldFK( JTextFieldFK.TP_DATE, 10, 0 );
	
	private JTextFieldFK txtDtVencOrc = new JTextFieldFK( JTextFieldFK.TP_DATE, 10, 0 );
	
	private JTextFieldFK txtCodCli = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtPrazoEntOrc = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtCodPlanoPag = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtCodVend = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	//Class FGrupoOrc
	private JTextFieldPad txtCodGO = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtSeqItGo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtCodEmpGo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	//Classe FDetOrc
	private JTextFieldPad txtTitDetOrc = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextAreaPad txtAtivDetOrc = new JTextAreaPad( 10000 );

	private JTextFieldPad txtSeqDetOrc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextAreaPad txtTextoItDetOrc = new JTextAreaPad( 10000 );
	//private JTextFieldPad txtDescItGo = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
		
	private JScrollPane jspDetTexto = new JScrollPane( txtTextoItDetOrc );
	
	private JScrollPane jspDetAtiv = new JScrollPane( txtAtivDetOrc );

	private ListaCampos lcDet = new ListaCampos( this, "GR" );
	
	private ListaCampos lcOrc = new ListaCampos( this, "OC" );
	
	private ListaCampos lcTipoCli = new ListaCampos( this, "CL" );

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();
	
	
	
	
	//Lista Campos
	
	public FDetOrc() {

		super();

		nav.setNavigation( true );

		setTitulo( "Cadastro de Grupo de Or�amento" );
		setAtribos( 50, 50, 790, 490 );

		setAltCab( 190 );
		pinCab = new JPanelPad( 420, 90 );
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );
		

		//FK OR�AMENTO
		lcOrc.add( new GuardaCampo( txtCodOrc, "CodOrc", "C�d.Or�.", ListaCampos.DB_PK, false ) );
		lcOrc.add( new GuardaCampo( txtDtOrc, "DtOrc", "Data", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.comiss.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pg.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtPrazoEntOrc, "PrazoEntOrc", "Dias p/ entrega", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtDtVencOrc, "DtVencOrc", "Dt.valid.", ListaCampos.DB_SI, false ) );
		lcOrc.montaSql( false, "ORCAMENTO", "VD" );
		lcOrc.setQueryCommit( false );
		lcOrc.setReadOnly( true );
		txtCodOrc.setTabelaExterna( lcOrc, FOrcamento.class.getCanonicalName() );
					
		lcDet.add( new GuardaCampo( txtSeqDetOrc, "SeqDetOrc", "SeqDetOrc", ListaCampos.DB_PK, true ) );
		lcDet.add( new GuardaCampo( txtTextoItDetOrc, "TextoItDetOrc", "Texto Detalhado", ListaCampos.DB_SI, true ) );
		lcDet.add( new GuardaCampo( txtSeqItGo, "SeqItGo", "SeqItGo", ListaCampos.DB_SI, true ) );
		lcDet.add( new GuardaCampo( txtCodEmpGo, "CodEmpGo", "CodEmpGo", ListaCampos.DB_SI, true ) );
		lcDet.add( new GuardaCampo( txtCodGO, "CodGo", "Texto Detalhado", ListaCampos.DB_SI, true ) );
		lcDet.montaSql( false, "ITDETORC", "VD" );
		lcDet.setQueryCommit( false );
		lcDet.setReadOnly( true );
		

	    //Campos Or�amento.
		adicCampo( txtCodOrc, 7, 20, 90, 20, "CodOrc", "N� or�amento", ListaCampos.DB_PK, true );
		adicDescFK( txtDtOrc, 440, 20, 107, 20, "DtOrc", "Data" );
		adicDescFK( txtCodVend, 7, 60, 90, 20, "CodVend", "C�d.comiss." );
		adicDescFK( txtCodCli, 100, 20, 87, 20, "CodCli", "C�d.cli." );
		adicDescFK( txtRazCli, 190, 20, 247, 20, "RazCli", "Raz�o social do cliente" );
		adicDescFK( txtDescTipoCli, 270, 60, 147, 20, "DescTipoCli", "Desc. do tipo de cliente" );
		adicDescFK( txtCodPlanoPag, 420, 60, 77, 20, "CodPlanoPag", "C�d.p.pg." );
		adicDescFK( txtDescPlanoPag, 500, 60, 240, 20, "DescPlanoPag", "Descri��o do plano de pagamento" );
		adicDescFK( txtDtVencOrc, 550, 20, 87, 20, "DtVencOrc", "Dt.valid." );
		adicDescFK( txtPrazoEntOrc, 640, 20, 100, 20, "PrazoEntOrc", "Dias p/ entrega" );
		adicDescFK( txtNomeVend, 100, 60, 167, 20, "NomeVend", "Nome do comissionado" );
		
		//Campos FDetOrc
		adicCampo( txtTitDetOrc, 7, 100, 381, 20, "TITDETORC", "T�tulo", ListaCampos.DB_PK, true );
		adicDBLiv( txtAtivDetOrc, "AtivDetOrc", "Resumo Ativadade", true );
		adic( jspDetAtiv, 395, 100, 347, 40, "Resumo Atividade" );
		setListaCampos( true, "DETORC", "VD" );

		setAltDet( 140 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtSeqDetOrc, 7, 20, 70, 20, "txtSeqDetOrc", "SeqDetOrc", ListaCampos.DB_PK, true );
		adicDBLiv( txtTextoItDetOrc, "txtTextoItDetOrc", "Texto Detalhado", true );
		adic( jspDetTexto,80, 20, 400, 80, "Descri��o do Item de Agrupamento" );
		setListaCampos( false, "ITDETORC", "VD" );
		
		montaTab();
		tab.setTamColuna( 70, 0 );
		tab.setTamColuna( 300, 1 );

	}
	

	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcDet.setConexao( cn );
		lcOrc.setConexao( cn );
		lcTipoCli.setConexao( cn );
	}

}
