/**
 * @version 11/08/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.atd <BR>
 *         Classe: @(#)FGrupoOrc.java <BR>
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
 *         Cadastro de GrupoOrc   
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.detail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;



public class FGrupoOrc extends FDetalhe implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodGO = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescGO = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSeqItGo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	//private JTextFieldPad txtDescItGo = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextAreaPad txtDescItGo = new JTextAreaPad( 10000 );
	
	private JScrollPane jspDesc = new JScrollPane( txtDescItGo );

	private ListaCampos lcGrupo = new ListaCampos( this, "GR" );

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	public FGrupoOrc() {

		super();

		nav.setNavigation( true );

		setTitulo( "Cadastro de Grupo de Or�amento" );
		setAtribos( 50, 50, 550, 450 );

		setAltCab( 90 );
		pinCab = new JPanelPad( 420, 90 );
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );

		lcGrupo.add( new GuardaCampo( txtSeqItGo, "SeqItGo", "Seq.It.Go", ListaCampos.DB_PK, true ) );
		lcGrupo.add( new GuardaCampo( txtDescItGo, "DescItGo", "Descri��o", ListaCampos.DB_SI, true ) );
		lcGrupo.montaSql( false, "ITGRUPORC", "VD" );
		lcGrupo.setQueryCommit( false );
		lcGrupo.setReadOnly( true );
		

		adicCampo( txtCodGO, 7, 20, 70, 20, "CodGO", "C�d.GO", ListaCampos.DB_PK, true );
		adicCampo( txtDescGO, 80, 20, 400, 20, "DescGO", "Descri��o do Agrupamento", ListaCampos.DB_SI, true );
		setListaCampos( true, "GRUPORC", "VD" );

		setAltDet( 140 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtSeqItGo, 7, 20, 70, 20, "SeqItGo", "Seq.It.Go", ListaCampos.DB_PK, true );
		//adicCampo( txtDescItGo, 80, 20, 250, 20, "DescItGo", "Descri��o dos itens", ListaCampos.DB_SI, true );
		adicDBLiv( txtDescItGo, "DESCITGO", "Descri��o", true );
		adic( jspDesc,80, 20, 400, 80, "Descri��o do Item de Agrupamento" );
		setListaCampos( false, "ITGRUPORC", "VD" );

		montaTab();
		tab.setTamColuna( 70, 0 );
		tab.setTamColuna( 300, 1 );

	}

	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcGrupo.setConexao( cn );
	}

}
