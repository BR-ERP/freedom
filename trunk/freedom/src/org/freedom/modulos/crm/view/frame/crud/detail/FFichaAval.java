/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.atd <BR>
 *         Classe: @(#)FTipoAtendo.java <BR>
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

package org.freedom.modulos.crm.view.frame.crud.detail;

import java.awt.event.ActionEvent;
import java.util.Vector;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.crm.view.frame.crud.plain.FMotivoAval;
import org.freedom.modulos.crm.view.frame.crud.tabbed.FContato;

public class FFichaAval extends FDetalhe  {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();
	
	private JTextFieldPad txtDtFichaAval = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtSeqFichaAval = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodCont = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtRazCont = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodMotAval= new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescMotAval = new JTextFieldFK( JTextFieldFK.TP_STRING, 100, 0 );

	private JTextFieldPad txtAndarFichaAval = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JRadioGroup<?, ?> rgLocalFichaAval = null;

	private JRadioGroup<?, ?> rgFinaliFichaAval = null;
	
	private JCheckBoxPad cbPredentrfichaAval = new JCheckBoxPad( "PREDIO/CASA EST� SENDO ENTREGUE AGORA?", "S", "N" );
	
	private ListaCampos lcContato = new ListaCampos( this, "CO" );
	
	private ListaCampos lcMotAval = new ListaCampos( this, "MA" );

	public FFichaAval() {

		nav.setNavigation( true );

		setTitulo( "Ficha Avaliativa" );
		setAltCab( 260 );
		setAtribos( 50, 50, 715, 600 );
		montaListaCampos();
		montaTela();

	}
	
	public void montaListaCampos(){
		
		// FK Contato
		txtCodCont.setTabelaExterna( lcContato, FContato.class.getCanonicalName());
		txtCodCont.setFK( true );
		txtCodCont.setNomeCampo( "CodContr" );
		lcContato.add( new GuardaCampo( txtCodCont, "CodCto", "C�d.Contato", ListaCampos.DB_PK, false ) );
		lcContato.add( new GuardaCampo( txtRazCont, "RazCto", "Raz�o do contato.", ListaCampos.DB_SI, false ) );
		lcContato.montaSql( false, "CONTATO", "TK" );
		lcContato.setReadOnly( true );
		lcContato.setQueryCommit( false );
		
		// FK Motivo Aval.
		txtCodMotAval.setTabelaExterna( lcMotAval, FMotivoAval.class.getCanonicalName());
		txtCodMotAval.setFK( true );
		txtCodMotAval.setNomeCampo( "MotAval" );
		lcMotAval.add( new GuardaCampo( txtCodMotAval, "CodMotAval", "C�d.Motivo", ListaCampos.DB_PK, false ) );
		lcMotAval.add( new GuardaCampo( txtDescMotAval, "DescMotAval", "Descri��o do motivo da avalia��o.", ListaCampos.DB_SI, false ) );
		lcMotAval.montaSql( false, "MotivoAval", "CR" );
		lcMotAval.setReadOnly( true );
		lcMotAval.setQueryCommit( false );
		
	}
	
	public void montaTela(){
		montaGrupoRadio();
		
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );
	
		adicCampo( txtSeqFichaAval, 7, 20, 80, 20, "SeqFichaAval", "Seq.Ficha", ListaCampos.DB_PK, true );
		adicCampo( txtCodCont, 90, 20, 80, 20, "CodCto", "C�d.Contato", ListaCampos.DB_FK, txtRazCont, true );
		adicDescFK( txtRazCont, 173, 20, 351, 20, "RazCto", "Raz�o do contato" );
		adicCampo( txtDtFichaAval, 530, 20, 110, 20, "DtFichaAval", "Dt.ficha aval.", ListaCampos.DB_PK, true );
	
		adicCampo( txtCodMotAval, 7, 60, 80, 20, "CodMotAval", "C�d.Motivo", ListaCampos.DB_FK ,txtDescMotAval, true );
		adicDescFK( txtDescMotAval, 90, 60, 300, 20, "DescMotAval", "Descri��o do motivo da avalia��o" );
		
		adicDB( rgLocalFichaAval, 7, 100, 320, 30, "LocalFichaAval", "Local Ficha Avaliativa", false );
		adicDB( rgFinaliFichaAval, 330, 100, 320, 30, "FinaliFichaAval", "Finalidade Ficha Avaliativa", false );
		adicDB( cbPredentrfichaAval, 7, 130, 320, 30, "PredentrfichaAval", "", false );
		
		adicCampo( txtAndarFichaAval, 330, 150, 80, 20, "AndarFichaAval", "Andar", ListaCampos.DB_SI , true );
		setListaCampos( true, "FICHAAVAL", "CR" );
		lcCampos.setQueryInsert( false );
		montaTab();
		
	}
	
	private void montaGrupoRadio(){
		
		Vector<String> vValsLocal = new Vector<String>();
		Vector<String> vLabsLocal = new Vector<String>();
		vLabsLocal.addElement( "Apartamento" );
		vLabsLocal.addElement( "Casa" );
		vLabsLocal.addElement( "Empresa" );
		vValsLocal.addElement( "A" );
		vValsLocal.addElement( "C" );
		vValsLocal.addElement( "E" );
		rgLocalFichaAval = new JRadioGroup<String, String>( 1, 3, vLabsLocal, vValsLocal );
		rgLocalFichaAval.setVlrString( "A" );

		Vector<String> vValsFinali = new Vector<String>();
		Vector<String> vLabsFinali = new Vector<String>();
		vLabsFinali.addElement( "Crian�a" );
		vLabsFinali.addElement( "Animal" );
		vLabsFinali.addElement( "Outros" );
		vValsFinali.addElement( "C" );
		vValsFinali.addElement( "A" );
		vValsFinali.addElement( "O" );
		rgFinaliFichaAval = new JRadioGroup<String, String>( 1, 3, vLabsFinali, vValsFinali );
		rgFinaliFichaAval.setVlrString( "C" );

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp )
			imprimir( false );
		super.actionPerformed( evt );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcContato.setConexao( cn );
		lcMotAval.setConexao( cn );

	}

	private void imprimir( boolean bVisualizar ) {

	}
}
