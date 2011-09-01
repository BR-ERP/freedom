/**
 * @version 05/07/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm.view.frame.crud.plain <BR>
 *         Classe: @(#)FModAtendo.java <BR>
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
 *         Modelo de atendimento
 * 
 */
package org.freedom.modulos.crm.view.frame.crud.plain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.crm.view.frame.crud.detail.FContrato;

public class FModAtendo extends FDados implements ActionListener, CarregaListener, JComboBoxListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodmodel = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescmodel = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodChamado = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescChamado = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodEspec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescEspec = new JTextFieldFK( JTextFieldPad.TP_STRING, 100, 0 );

	private JTextFieldPad txtCodAtendo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodTpAtendo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescTpAtendo = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0);
	
	private JTextFieldPad txtContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtStatusAtendo = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtitContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtCodSetAt = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescSetAt = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0);

	private JTextFieldPad txtCodContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 30, 0 );

	private JTextFieldPad txtCodItContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescContr = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldFK txtDescItContr = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextAreaPad txaDescAtendo = new JTextAreaPad();
	
	private JTextAreaPad txaObsInterno = new JTextAreaPad();

	private Vector<Integer> vValsTipo = new Vector<Integer>();

	private Vector<String> vLabsTipo = new Vector<String>();

	private JComboBoxPad cbStatus = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_STRING, 2, 0 );

	private Vector<Integer> vValsSetor = new Vector<Integer>();

	private Vector<String> vLabsSetor = new Vector<String>();

	private JComboBoxPad cbSetor = new JComboBoxPad( vLabsSetor, vValsSetor, JComboBoxPad.TP_INTEGER, 8, 0 );

	private ListaCampos lcEspec = new ListaCampos( this, "EA" );

	private ListaCampos lcChamado = new ListaCampos( this, "CH" );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );
	
	private ListaCampos lcTpAtendo = new ListaCampos( this, "TO");
	
	private ListaCampos lcSetAt = new ListaCampos( this, "SA");
	
	private ListaCampos lcContrato = new ListaCampos( this, "CT" );

	private ListaCampos lcItContrato = new ListaCampos( this, "CT" );
	
	private ListaCampos lcModAtendo = new ListaCampos( this );
	
	
	public FModAtendo() {
		super();

		montaListaCampos();
		montaTela();
	}

	private void montaListaCampos() {

		txtCodCli.setTabelaExterna( lcCli, null );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		lcCli.addCarregaListener( this ); 

		txtCodChamado.setTabelaExterna( lcChamado, null );
		txtCodChamado.setFK( true );
		txtCodChamado.setNomeCampo( "CodChamado" );
		lcChamado.add( new GuardaCampo( txtCodChamado, "CodChamado", "C�d.Chamado", ListaCampos.DB_PK, false ) );
		lcChamado.add( new GuardaCampo( txtDescChamado, "DescChamado", "Descri��o do chamado", ListaCampos.DB_SI, false ) );

		lcChamado.setDinWhereAdic( " CODCLI=#N", txtCodCli );
		lcChamado.montaSql( false, "CHAMADO", "CR" );
		lcChamado.setReadOnly( true );


		txtCodEspec.setTabelaExterna( lcEspec, null );
		txtCodEspec.setFK( true );
		txtCodEspec.setNomeCampo( "CodEspec" );
		lcEspec.add( new GuardaCampo( txtCodEspec, "CodEspec", "C�d.Espec.", ListaCampos.DB_PK, false ) );
		lcEspec.add( new GuardaCampo( txtDescEspec, "DescEspec", "Descri��o da especifica��o", ListaCampos.DB_SI, false ) );
		lcEspec.montaSql( false, "ESPECATEND", "AT" );
		lcEspec.setReadOnly( true );

		txtCodTpAtendo.setTabelaExterna( lcTpAtendo, null );
		txtCodTpAtendo.setFK( true );
		txtCodTpAtendo.setNomeCampo( "CodTpAtendo" );
		lcTpAtendo.add( new GuardaCampo( txtCodTpAtendo, "CodTpAtendo", "C�d.Tp.Atend.", ListaCampos.DB_PK, false ) );
		lcTpAtendo.add( new GuardaCampo( txtDescTpAtendo, "DescTpAtendo", "Descri��o do tipo de atendimento", ListaCampos.DB_SI, false ) );
		lcTpAtendo.montaSql( false, "TIPOATENDO", "AT" );
		lcTpAtendo.setReadOnly( true );

		txtCodSetAt.setTabelaExterna( lcSetAt, null );
		txtCodSetAt.setFK( true );
		txtCodSetAt.setNomeCampo( "CodSetAt" );
		lcSetAt.add( new GuardaCampo( txtCodSetAt, "CodSetAt", "C�d.Setor", ListaCampos.DB_PK, false ) );
		lcSetAt.add( new GuardaCampo( txtDescSetAt, "DescSetAt", "Descri��o de atendimento", ListaCampos.DB_SI, false ) );
		lcSetAt.montaSql( false, "SETOR", "AT" );
		lcSetAt.setReadOnly( true );

		lcContrato.add( new GuardaCampo( txtCodContr, "CodContr", "C�d.Contrato", ListaCampos.DB_PK, true ) );
		lcContrato.add( new GuardaCampo( txtDescContr, "DescContr", "Desc.Contr.", ListaCampos.DB_SI, false ) );
		lcContrato.montaSql( false, "CONTRATO", "VD" );
		lcContrato.setQueryCommit( false );
		lcContrato.setReadOnly( true );
		txtCodContr.setTabelaExterna( lcContrato, FContrato.class.getCanonicalName() );
		lcContrato.setDinWhereAdic( " CODCLI=#N", txtCodCli );

		lcItContrato.add( new GuardaCampo( txtCodItContr, "CodItContr", "C�d.It.Contr.", ListaCampos.DB_PK, true ) );
		lcItContrato.add( new GuardaCampo( txtCodContr, "CodContr", "C�d.Contrato", ListaCampos.DB_PK, true ) );
		lcItContrato.add( new GuardaCampo( txtDescItContr, "DescItContr", "Desc.It.Contr.", ListaCampos.DB_SI, false ) );
		lcItContrato.setDinWhereAdic( "CodContr=#N", txtCodContr );
		txtCodContr.setPK( true );
		lcItContrato.montaSql( false, "ITCONTRATO", "VD" );
		lcItContrato.setQueryCommit( false );
		lcItContrato.setReadOnly( true );
		txtCodItContr.setTabelaExterna( lcItContrato, FContrato.class.getCanonicalName() );		
		
		
		lcModAtendo.add( new GuardaCampo( txtCodAtendo, "CodModel", "C�d.Model", ListaCampos.DB_PK, false ) );
		lcModAtendo.add( new GuardaCampo( txtStatusAtendo, "statusatendo", "Status do Atendimento.", ListaCampos.DB_SI, false ) );
		lcModAtendo.montaSql( false, "MODATENDO", "AT" );
		lcModAtendo.setReadOnly( true );
		txtCodmodel.setNomeCampo( "CodModel" );
		txtCodmodel.setFK( true );
		txtCodmodel.setTabelaExterna( lcModAtendo, FModAtendo.class.getCanonicalName() );

				
	}

	
	private void montaTela() {

		setTitulo( "Modelos de Atendimento" );
		setAtribos( 10, 50, 690, 560 );
		adicCampo( txtCodmodel, 7, 20, 80, 20, "Codmodel", "C�d.model.", ListaCampos.DB_PK, true );
		adicCampo( txtDescmodel, 90, 20, 510, 20, "DescModel", "Descri��o do modelo", ListaCampos.DB_SI, true );
		
		adicCampo( txtCodCli, 7, 60, 80, 20, "Codcli", "C�d.Cliente", ListaCampos.DB_FK, txtRazCli, false );
		adicDescFK( txtRazCli, 90, 60, 510, 20, "Razcli", "Raz�o Social do Cliente" );

		adicCampo( txtCodTpAtendo, 7,100, 80, 20, "CodTpAtendo", "C�d.Tp.Atend.", ListaCampos.DB_FK, txtDescTpAtendo, false);
		adicDescFK( txtDescTpAtendo, 90, 100, 200, 20, "DescTpAtendo", "Descri��o do tipo de atendimento" );
		adicCampo( txtCodSetAt, 293, 100, 80, 20, "CodSetAt", "C�d.Setor", ListaCampos.DB_FK, txtDescSetAt, false);
		adicDescFK( txtDescSetAt, 376, 100, 225, 20, "DescSetAt", "Descri��o do setor" );

		adicCampo( txtCodContr, 7, 140, 80, 20, "CodContr", "C�d.Contrato", ListaCampos.DB_FK, true );
		adicDescFK( txtDescContr, 90, 140, 510, 20, "DescContr", "Descri�o do contrato" );
		
		adicCampo( txtCodItContr, 7, 180, 80, 20, "CodItContr", "C�d.It.Contr.", ListaCampos.DB_FK, txtDescItContr, true );
		adicDescFK( txtDescItContr, 90, 180, 510, 20, "DescItContr", "Descri��o do item de contrato" );
		
		
		adicCampo( txtCodChamado, 7, 220, 80, 20, "Codchamado", "C�d.Chamado", ListaCampos.DB_FK, txtDescChamado, false  );
		adicDescFK( txtDescChamado, 90, 220, 510, 20, "Descchamado", "Descri��o do chamado" );

		adicCampo( txtCodEspec, 7, 260, 80, 20, "Codespec", "C�d.Espec.", ListaCampos.DB_FK, txtDescEspec, false );
		adicDescFK( txtDescEspec, 90, 260, 390, 20, "Descespec", "Descri��o da especifica��o do atendimento");
		adicDB( cbStatus, 483, 259, 120, 24, "StatusAtendo", "Status", false );

		adicDB(txaDescAtendo, 7, 300, 590, 50, "ObsAtendo", "Descri��o do atendimento", true);
		adicDB(txaObsInterno, 7, 370, 590, 50, "ObsInterno", "Observa��es internas", false);
		
		setListaCampos( true, "MODATENDO", "AT" );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );
		setImprimir( true );
		
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		montaComboStatus();
		
		lcModAtendo.setConexao( cn );
		
		lcCli.setConexao( cn );

		lcEspec.setConexao( cn );

		lcChamado.setConexao( cn );

		lcTpAtendo.setConexao( cn );
		
		lcSetAt.setConexao( cn );

		lcContrato.setConexao( cn );

		lcItContrato.setConexao( cn );

	}

	private void montaComboStatus() {

		Vector<String> vValsStatus = new Vector<String>();
		Vector<String> vLabsStatus = new Vector<String>();
		vValsStatus.addElement( "AA" );
		vValsStatus.addElement( "NC" );
		vLabsStatus.addElement( "Atendido" );
		vLabsStatus.addElement( "N�o computado" );

		cbStatus.setItensGeneric( vLabsStatus, vValsStatus );
	}
	
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp )
			imprimir( true );
		else if ( evt.getSource() == btImp )
			imprimir( false );
		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
	}

	public void afterCarrega( CarregaEvent cevt ) {

	}


	public void beforeCarrega( CarregaEvent cevt ) {

		
	}

	public void valorAlterado( JComboBoxEvent evt ) {

	}

}
