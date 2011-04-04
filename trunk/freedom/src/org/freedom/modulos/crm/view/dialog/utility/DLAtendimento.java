/*
 * Projeto: Freedom Pacote: org.freedom.modules.crm Classe: @(#)FAtendimento.java
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR> modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR> na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR> sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR> Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR> Voc� deve ter recebido uma c�pia da Licen�a P�blica
 * Geral GNU junto com este programa, se n�o, <BR> escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 */

package org.freedom.modulos.crm.view.dialog.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DateEditor;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.functions.FuncoesCRM;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.crm.business.component.Atendimento;

/**
 * 
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues
 * @version 10/10/2009 - Alex Rodrigues
 * @version 23/04/2010 - Anderson Sanchez
 */
public class DLAtendimento extends FFDialogo implements JComboBoxListener, KeyListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pnCab = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pnBotoes = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodChamado = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescChamado = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodEspec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescEspec = new JTextFieldFK( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCodAtendo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDataAtendimento = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDataAtendimentoFin = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtHoraini = new JTextFieldPad( JTextFieldPad.TP_TIME, 5, 0 );

	private JTextFieldPad txtHorafim = new JTextFieldPad( JTextFieldPad.TP_TIME, 5, 0 );

	private JTextFieldPad txtTipoAtendimento = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtStatusAtendo = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtitContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextAreaPad txaDescAtend = new JTextAreaPad();

	private JTextAreaPad txaObsInterno = new JTextAreaPad();

	private Vector<Integer> vValsTipo = new Vector<Integer>();

	private Vector<String> vLabsTipo = new Vector<String>();

	private JComboBoxPad cbTipo = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_INTEGER, 8, 0 );

	private JComboBoxPad cbStatus = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_STRING, 2, 0 );

	private Vector<Integer> vValsSetor = new Vector<Integer>();

	private Vector<String> vLabsSetor = new Vector<String>();

	private JComboBoxPad cbSetor = new JComboBoxPad( vLabsSetor, vValsSetor, JComboBoxPad.TP_INTEGER, 8, 0 );

	private Vector<Integer> vValsContr = new Vector<Integer>();

	private Vector<String> vLabsContr = new Vector<String>();

	private JComboBoxPad cbContr = new JComboBoxPad( vLabsContr, vValsContr, JComboBoxPad.TP_INTEGER, 8, 0 );

	private Vector<Integer> vValsitContr = new Vector<Integer>();

	private Vector<String> vLabsitContr = new Vector<String>();

	private JComboBoxPad cbitContr = new JComboBoxPad( vLabsitContr, vValsitContr, JComboBoxPad.TP_INTEGER, 8, 0 );

	private ListaCampos lcAtend = new ListaCampos( this, "AE" );
	
	private ListaCampos lcEspec = new ListaCampos( this, "EA" );
	
	private ListaCampos lcChamado = new ListaCampos( this, "CH" );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private ListaCampos lcAtendimento = new ListaCampos( this );

	private JLabelPad lbImg = new JLabelPad( Icone.novo( "bannerAtendimento.png" ) );

	private JButtonPad btMedida = new JButtonPad( Icone.novo( "btMedida.gif" ) );

	private JButtonPad btRun = new JButtonPad( Icone.novo( "btplay.png" ) );

	private String sPrefs[] = null;

	private int iDoc = 0;

	private boolean update = false;

	private boolean contando = false;

	private Thread contador = null;

	private JLabelPad lbContador = new JLabelPad();

	private JPanelPad pnGeral = new JPanelPad( new BorderLayout() );

	private JPanelPad pnTela = new JPanelPad( new BorderLayout() );

	private JPanelPad pnCampos = new JPanelPad( 500, 220 );

	private JPanelPad pnTxa = new JPanelPad( new GridLayout( 2, 1 ) );

	private String tipoatendo = null;

	private Integer codrec = null;

	private Integer nparcitrec = null;

	private Integer codchamado_ant = null;

	private JCheckBoxPad cbConcluiChamado = new JCheckBoxPad( "Conclui chamado?", "S", "N" );

	public DLAtendimento( int iCodCli, Integer codchamado, Component cOrig, boolean isUpdate, DbConnection conn, int codatendo, int codatend, String tipoatendo ) {

		this( iCodCli, codchamado, cOrig, conn, isUpdate, tipoatendo );

		update = isUpdate;

		txtCodAtendo.setVlrInteger( codatendo );

		lcAtendimento.carregaDados();
		cbStatus.setVlrString( txtStatusAtendo.getVlrString() );

		cbTipo.setVlrInteger( txtTipoAtendimento.getVlrInteger() );
		cbSetor.setVlrInteger( txtSetor.getVlrInteger() );
		cbContr.setVlrInteger( txtContr.getVlrInteger() );
		cbitContr.setVlrInteger( txtitContr.getVlrInteger() );
		txtCodChamado.setVlrInteger( codchamado );

		lcChamado.carregaDados();

		if ( update ) {
			pnCampos.adic( new JLabelPad( "Status" ), 494, 90, 120, 20 );
			pnCampos.adic( cbStatus, 494, 110, 120, 20 );

			cbitContr.setSize( 198, 20 );

		}
	}

	public DLAtendimento( int codcli, Integer codchamado, Component cOrig, DbConnection conn, boolean isUpdate, String tipoatendo, Integer codrec, Integer nparcitrec ) {

		this( codcli, codchamado, cOrig, conn, isUpdate, tipoatendo );

		this.codrec = codrec;
		this.nparcitrec = nparcitrec;

	}

	public DLAtendimento( int codcli, Integer codchamado, Component cOrig, DbConnection conn, boolean isUpdate, String tipoatendo ) {

		super( cOrig );

		update = isUpdate;
		this.tipoatendo = tipoatendo;

		setTitulo( "Novo atendimento" );
		setAtribos( 640, 640 );

		montaListaCampos();

		btMedida.setPreferredSize( new Dimension( 30, 30 ) );

		pnCab.add( lbImg );

		c.add( pnCab, BorderLayout.NORTH );

		c.add( pnGeral );

		pnGeral.add( pnTela, BorderLayout.CENTER );

		pnTela.add( pnCampos, BorderLayout.NORTH );

		pnTela.add( pnTxa, BorderLayout.CENTER );

		JScrollPane spnDetalhamento = new JScrollPane( txaDescAtend );
		JScrollPane spnObsInterno = new JScrollPane( txaObsInterno );

		spnDetalhamento.setBorder( BorderFactory.createTitledBorder( "Detalhamento" ) );
		spnObsInterno.setBorder( BorderFactory.createTitledBorder( "Observa��es internas" ) );

		pnTxa.add( spnDetalhamento );
		pnTxa.add( spnObsInterno );

		txaDescAtend.setBorder( BorderFactory.createEtchedBorder( Color.RED, null ) );

		setPainel( pnCampos );

		adic( new JLabelPad( "C�d.Cliente" ), 7, 10, 80, 20 );
		adic( txtCodCli, 7, 30, 80, 20 );

		adic( new JLabelPad( "Raz�o Social do Cliente" ), 90, 10, 480, 20 );
		adic( txtRazCli, 90, 30, 524, 20 );

		adic( new JLabelPad( "C�d.Atend." ), 7, 50, 80, 20 );
		adic( txtCodAtend, 7, 70, 80, 20 );

		adic( new JLabelPad( "Nome do Atendente" ), 90, 50, 200, 20 );
		adic( txtNomeAtend, 90, 70, 200, 20 );

		adic( new JLabelPad( "Tipo de Atendimento" ), 293, 50, 198, 20 );
		adic( cbTipo, 293, 70, 198, 20 );

		adic( new JLabelPad( "Setor" ), 494, 50, 120, 20 );
		adic( cbSetor, 494, 70, 120, 20 );

		adic( new JLabelPad( "Contrato/Projeto" ), 7, 90, 284, 20 );
		adic( cbContr, 7, 110, 284, 20 );

		adic( new JLabelPad( "Item" ), 294, 90, 320, 20 );
		adic( cbitContr, 294, 110, 320, 20 );

		adic( new JLabelPad( "C�d.Chamado" ), 7, 130, 80, 20 );
		adic( txtCodChamado, 7, 150, 80, 20 );

		adic( new JLabelPad( "Descri��o do chamado" ), 90, 130, 200, 20 );
		adic( txtDescChamado, 90, 150, 200, 20 );

		adic( new JLabelPad( "In�cio" ), 294, 130, 80, 20 );
		adic( txtDataAtendimento, 294, 150, 80, 20 );
		adic( txtHoraini, 377, 150, 53, 20 );

		adic( new JLabelPad( "Final" ), 433, 130, 70, 20 );
		adic( txtDataAtendimentoFin, 433, 150, 70, 20 );
		adic( txtHorafim, 506, 150, 53, 20 );
		adic( btRun, 559, 150, 19, 19 );
		
		adic( txtCodEspec, 7, 190, 80, 20, "C�d.Espec." );
		adic( txtDescEspec, 90, 190, 283, 20, "Descri��o da especifica��o do atendimento");
		
		adic( cbConcluiChamado, 376, 190, 200, 20 );

		txtDataAtendimento.setRequerido( true );
		txtDataAtendimentoFin.setRequerido( false );
		txtDataAtendimentoFin.setSoLeitura( true );
		txtDataAtendimento.addKeyListener( this );

		btMedida.addActionListener( this );
		cbTipo.addComboBoxListener( this );
		cbContr.addComboBoxListener( this );

		lcChamado.addCarregaListener( this );
		lcCli.addCarregaListener( this );
		txtCodCli.setVlrInteger( codcli );

		txtCodChamado.setVlrInteger( codchamado );

		//if ( codcli > 0 ) {
		//	txtCodCli.setAtivo( false );
		//}
		//else {
			txtCodCli.setRequerido( true );
		//}

		//if ( codchamado != null ) {
			//txtCodChamado.setAtivo( false );
		//}

		btRun.addActionListener( this );

		setConexao( conn );

		if ( !update ) {

			txtCodAtend.setVlrInteger( Atendimento.buscaAtendente() );
			lcAtend.carregaDados();

			if ( getAutoDataHora() ) {

				txtHoraini.setVlrTime( new Date() );
				txtDataAtendimento.setVlrDate( new Date() );
				txtDataAtendimentoFin.setVlrDate( new Date() );
				iniciaContagem();

			}
		}
	}

	private void montaListaCampos() {

		txtCodCli.setTabelaExterna( lcCli, null );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );

		txtCodAtend.setTabelaExterna( lcAtend, null );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );
		lcAtend.add( new GuardaCampo( txtCodAtend, "CodAtend", "C�d.atend.", ListaCampos.DB_PK, false ), "txtCodVendx" );
		lcAtend.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ), "txtCodVendx" );
		lcAtend.montaSql( false, "ATENDENTE", "AT" );
		lcAtend.setReadOnly( true );

		txtCodAtendo.setTabelaExterna( lcAtendimento, null );
		txtCodAtendo.setFK( true );
		txtCodAtendo.setNomeCampo( "CodAtendo" );
		lcAtendimento.add( new GuardaCampo( txtCodAtendo, "CodAtendo", "C�d.atendo", ListaCampos.DB_PK, false ) );
		lcAtendimento.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.atend.", ListaCampos.DB_FK, false ) );
		lcAtendimento.add( new GuardaCampo( txtCodAtend, "CodAtend", "Cod.Atend.", ListaCampos.DB_FK, false ) );
		lcAtendimento.add( new GuardaCampo( txtDataAtendimento, "dataAtendo", "Data atendimento", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtDataAtendimentoFin, "dataAtendoFin", "Data atendimento fin.", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtHoraini, "HoraAtendo", "Hora atendimento", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtHorafim, "HoraAtendoFin", "Hora atendimento fin.", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txaDescAtend, "ObsAtendo", "Descri��o", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtTipoAtendimento, "codtpatendo", "Tipo", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtSetor, "codsetat", "setor", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtContr, "codcontr", "Codcontrato", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtitContr, "coditcontr", "item do contrato", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtStatusAtendo, "statusatendo", "Status do atendimento", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txaObsInterno, "obsinterno", "Observa��o interna", ListaCampos.DB_SI, false ) );
		lcAtendimento.add( new GuardaCampo( txtCodEspec, "codespec", "C�d.Espec.", ListaCampos.DB_FK, true ) );
		
		lcAtendimento.montaSql( false, "ATENDIMENTO", "AT" );
		lcAtendimento.setReadOnly( true );

		txtCodChamado.setTabelaExterna( lcChamado, null );
		txtCodChamado.setFK( true );
		txtCodChamado.setNomeCampo( "CodChamado" );
		lcChamado.add( new GuardaCampo( txtCodChamado, "CodChamado", "C�d.Chamado", ListaCampos.DB_PK, false ) );
		lcChamado.add( new GuardaCampo( txtDescChamado, "DescChamado", "Descri��o do chamado", ListaCampos.DB_SI, false ) );
		// lcChamado.setDinWhereAdic( " STATUS NOT IN('CO','CA') AND CODCLI=#N", txtCodCli );
		lcChamado.setDinWhereAdic( " CODCLI=#N", txtCodCli );
		lcChamado.montaSql( false, "CHAMADO", "CR" );
		lcChamado.setReadOnly( true );
		
		
		txtCodEspec.setTabelaExterna( lcEspec, null );
		txtCodEspec.setFK( true );
		txtCodEspec.setNomeCampo( "CodEspec" );
		lcEspec.add( new GuardaCampo( txtCodEspec, "CodEspec", "C�d.Espec.", ListaCampos.DB_PK, true ) );
		lcEspec.add( new GuardaCampo( txtDescEspec, "DescEspec", "Descri��o da especifica��o", ListaCampos.DB_SI, false ) );
		lcEspec.montaSql( false, "ESPECATEND", "AT" );
		lcEspec.setReadOnly( true );
		
		

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

	private void montaComboTipo() {

		try {

			PreparedStatement ps = con.prepareStatement( "SELECT CODTPATENDO,DESCTPATENDO FROM ATTIPOATENDO WHERE CODEMP=? AND CODFILIAL=? AND TIPOATENDO=? ORDER BY 2" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
			ps.setString( 3, tipoatendo );

			ResultSet rs = ps.executeQuery();

			vValsTipo.clear();
			vLabsTipo.clear();
			vValsTipo.addElement( -1 );
			vLabsTipo.addElement( "<Selecione>" );

			while ( rs.next() ) {
				vValsTipo.addElement( rs.getInt( "CodTpAtendo" ) );
				vLabsTipo.addElement( rs.getString( "DescTpAtendo" ) );
			}

			cbTipo.setItensGeneric( vLabsTipo, vValsTipo );

			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar os tipos de atendimento!\n" + e.getMessage(), true, con, e );
		}
	}

	private void montaComboSetor() {

		Integer iTipo = cbTipo.getVlrInteger();
		if ( ( iTipo == null ) || ( iTipo.intValue() <= 0 ) )
			return;

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT S.CODSETAT,S.DESCSETAT FROM ATSETOR S, ATTIPOATENDOSETOR TS " + "WHERE S.CODEMP=TS.CODEMPST AND S.CODFILIAL=TS.CODFILIAL AND S.CODSETAT=TS.CODSETAT " + "" + "AND TS.CODEMP=? AND TS.CODFILIAL=? AND TS.CODTPATENDO=? ORDER BY 2";

		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
			ps.setInt( 3, iTipo.intValue() );
			rs = ps.executeQuery();

			vValsSetor.clear();
			vLabsSetor.clear();
			vValsSetor.addElement( -1 );
			vLabsSetor.addElement( "<Selecione>" );

			while ( rs.next() ) {
				vValsSetor.addElement( new Integer( rs.getInt( "CodSetAt" ) ) );
				vLabsSetor.addElement( rs.getString( "DescSetAt" ) );
			}

			cbSetor.setItensGeneric( vLabsSetor, vValsSetor );

			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar os setores!\n" + err.getMessage(), true, con, err );
		} finally {

			if ( vValsSetor.size() <= 1 ) {
				Funcoes.mensagemInforma( this, "N�o existe setor cadastrado para este tipo de atendimento." );
			}
			else if ( vValsSetor.size() == 2 ) {
				cbSetor.setSelectedIndex( 1 );
				cbSetor.setEnabled( false );
			}
			else {
				cbSetor.setEnabled( true );
			}

			ps = null;
			rs = null;
		}

	}

	private boolean getAutoDataHora() {

		boolean autoDataHora = true;

		try {

			PreparedStatement ps = con.prepareStatement( "SELECT AUTOHORATEND FROM SGPREFERE3 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				autoDataHora = "S".equals( rs.getString( "AUTOHORATEND" ) );
			}

			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao verificar prefer�ncias.\n" + e.getMessage() );
		}

		return autoDataHora;
	}

	private String[] getPref() {

		String sRets[] = { "", "" };

		try {

			PreparedStatement ps = con.prepareStatement( "SELECT CODTPATENDO,CLASSMEDIDA FROM SGPREFERE2 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				sRets[ 0 ] = rs.getString( "CodTpAtendo" ) != null ? rs.getString( "CodTpAtendo" ) : "";
				sRets[ 1 ] = rs.getString( "ClassMedida" );
			}

			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao verificar levantamento.\n" + e.getMessage() );
		}

		return sRets;
	}

	private int getCodLev() {

		int iRet = 0;

		try {

			PreparedStatement ps = con.prepareStatement( "SELECT ISEQ FROM SPGERANUM(?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setString( 3, "LV" );

			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				iRet = rs.getInt( 1 );
			}

			rs.close();
			ps.close();

			con.commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar novo c�digo para levantamento.\n" + e.getMessage(), true, con, e );
		}

		return iRet;
	}

	private void insertAtend() throws Exception {

		Object ORets[] = getValores();

		StringBuilder sql = new StringBuilder();

		sql.append( "EXECUTE PROCEDURE ATADICATENDIMENTOCLISP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );

		ps.setString( 1, (String) ORets[ 0 ] ); // Tipo de atendimento
		ps.setString( 2, (String) ORets[ 1 ] ); // Codigo do atendente
		ps.setString( 3, (String) ORets[ 2 ] ); // Setor de atendimento
		ps.setInt( 4, Aplicativo.iCodEmp ); // C�digo da empresa
		ps.setInt( 5, Aplicativo.iCodFilialPad ); // C�digo da filial
		ps.setInt( 6, Integer.parseInt( (String) ORets[ 3 ] ) ); // Nro. do atendimento
		ps.setDate( 7, Funcoes.dateToSQLDate( (Date) ORets[ 4 ] ) ); // Data de inicio do atendimento
		ps.setDate( 8, Funcoes.dateToSQLDate( (Date) ORets[ 5 ] ) ); // Data final do atendimento
		ps.setTime( 9, Funcoes.strTimeTosqlTime( ORets[ 6 ].toString() ) ); // Hora inicial do atendimento
		ps.setTime( 10, Funcoes.strTimeTosqlTime( ORets[ 7 ].toString() ) ); // Hora final do atendimento
		ps.setString( 11, (String) ORets[ 8 ] ); // Descri��o do atendimento
		ps.setInt( 12, Aplicativo.iCodEmp ); // C�digo da empresa do cliente
		ps.setInt( 13, Aplicativo.iCodFilialPad ); // C�digo da filial do cliente
		ps.setInt( 14, txtCodCli.getVlrInteger() ); // C�digo do cliente

		if ( cbContr.getVlrInteger() == -1 ) {
			ps.setNull( 15, Types.INTEGER ); // C�digo da empresa do contrato
			ps.setNull( 16, Types.INTEGER ); // C�digo da filial do contrato
			ps.setNull( 17, Types.INTEGER ); // C�digo do contrato
		}
		else {
			ps.setInt( 15, Aplicativo.iCodEmp ); // C�digo da empresa do contrato
			ps.setInt( 16, Aplicativo.iCodFilialPad ); // C�digo da filial do contrato
			ps.setInt( 17, cbContr.getVlrInteger() ); // C�digo do contrato
		}
		if ( cbitContr.getVlrInteger() == -1 ) {
			ps.setInt( 18, cbContr.getVlrInteger() ); // C�digo do �tem de contrato
		}
		else {
			ps.setInt( 18, cbitContr.getVlrInteger() ); // C�digo do �tem de contrato
		}

		if ( codrec != null && nparcitrec != null ) {
			ps.setInt( 19, codrec ); // C�digo do contas a receber
			ps.setInt( 20, nparcitrec ); // C�digo do �tem do contas a receber
		}
		else {
			ps.setNull( 19, Types.INTEGER ); // C�digo do contas a receber
			ps.setNull( 20, Types.INTEGER ); // C�digo do �tem do contas a receber
		}

		if ( txtCodChamado.getVlrInteger() > 0 ) {
			ps.setInt( 21, lcChamado.getCodEmp() ); // C�digo da empresa do chamado
			ps.setInt( 22, lcChamado.getCodFilial() ); // C�digo da filial do chamado
			ps.setInt( 23, txtCodChamado.getVlrInteger() ); // C�digo do chamado
		}
		else {
			ps.setNull( 21, Types.INTEGER );
			ps.setNull( 22, Types.INTEGER );
			ps.setNull( 23, Types.INTEGER );
		}

		ps.setString( 24, txaObsInterno.getVlrString() );

		ps.setString( 25, cbConcluiChamado.getVlrString() );
		
		ps.setInt( 26, lcEspec.getCodEmp() ); // C�digo da empresa do especifica��o
		ps.setInt( 27, lcEspec.getCodFilial() ); // C�digo da filial da especifica��o
		ps.setInt( 28, txtCodEspec.getVlrInteger() ); // C�digo da especifica��o

		ps.execute();
		ps.close();

		con.commit();
	}

	private void updateAtend() throws Exception {

		StringBuilder sql = new StringBuilder();

		sql.append( "update atatendimento a set  " );

		sql.append( "a.codatend=?, a.dataatendo=?, a.dataatendofin=?, " );
		sql.append( "a.horaatendo=?, a.horaatendofin=?, a.obsatendo=?, " );
		sql.append( "a.codempto=?, a.codfilialto=?, a.codtpatendo=?, " );
		sql.append( "a.codempsa=?, a.codfilialsa=?, a.codsetat=?, " );
		sql.append( "a.codempch=?, a.codfilialch=?, a.codchamado=?, " );
		sql.append( "a.codempct=?, a.codfilialct=?, a.codcontr=?, a.coditcontr=?, " );
		sql.append( "a.statusatendo=?, a.obsinterno=?, a.concluichamado=?, " );
		sql.append( "a.codempea=?, a.codfilialea=?, a.codespec=?, ");
		sql.append( "a.codempcl=?, a.codfilialcl=?, a.codcli=? ");
		
		sql.append( "where a.codemp=? and a.codfilial=? and a.codatendo=? " );

		PreparedStatement ps = con.prepareStatement( sql.toString() );

		ps.setInt( 1, txtCodAtend.getVlrInteger() );
		ps.setDate( 2, Funcoes.dateToSQLDate( txtDataAtendimento.getVlrDate() ) );
		ps.setDate( 3, Funcoes.dateToSQLDate( txtDataAtendimentoFin.getVlrDate() ) );

		ps.setTime( 4, Funcoes.strTimeTosqlTime( txtHoraini.getVlrString() ) );
		ps.setTime( 5, Funcoes.strTimeTosqlTime( txtHorafim.getVlrString() ) );
		ps.setString( 6, txaDescAtend.getVlrString() );

		ps.setInt( 7, Aplicativo.iCodEmp );
		ps.setInt( 8, ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
		ps.setInt( 9, cbTipo.getVlrInteger() );

		ps.setInt( 10, Aplicativo.iCodEmp );
		ps.setInt( 11, ListaCampos.getMasterFilial( "ATSETOR" ) );
		ps.setInt( 12, cbSetor.getVlrInteger() );

		if ( txtCodChamado.getVlrInteger() > 0 ) {
			ps.setInt( 13, lcChamado.getCodEmp() ); // C�digo da empresa do chamado
			ps.setInt( 14, lcChamado.getCodFilial() ); // C�digo da filial do chamado
			ps.setInt( 15, txtCodChamado.getVlrInteger() ); // C�digo do chamado
		}
		else {
			ps.setNull( 13, Types.INTEGER );
			ps.setNull( 14, Types.INTEGER );
			ps.setNull( 15, Types.INTEGER );
		}

		ps.setInt( 16, Aplicativo.iCodEmp );
		ps.setInt( 17, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );

		if ( cbContr.getVlrInteger() == -1 ) {
			ps.setNull( 18, Types.INTEGER );
		}
		else {
			ps.setInt( 18, cbContr.getVlrInteger() );
		}
		if ( cbitContr.getVlrInteger() == -1 ) {
			ps.setInt( 19, cbContr.getVlrInteger() );
		}
		else {
			ps.setInt( 19, cbitContr.getVlrInteger() );
		}

		ps.setString( 20, cbStatus.getVlrString() );

		ps.setString( 21, txaObsInterno.getVlrString() );

		ps.setString( 22, cbConcluiChamado.getVlrString() );
		
		ps.setInt( 23, Aplicativo.iCodEmp );
		ps.setInt( 24, ListaCampos.getMasterFilial( "ATESPECATEND" ) );
		ps.setInt( 25, txtCodEspec.getVlrInteger() );

		ps.setInt( 26, Aplicativo.iCodEmp );
		ps.setInt( 27, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
		ps.setInt( 28, txtCodCli.getVlrInteger() );
		
		ps.setInt( 29, Aplicativo.iCodEmp );
		ps.setInt( 30, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
		ps.setInt( 31, txtCodAtendo.getVlrInteger() );

		ps.executeUpdate();

		con.commit();

	}

	private void iniciaContagem() {

		if ( !contando ) {

			btRun.setIcon( Icone.novo( "btpause.png" ) );

			contador = new Thread( new Runnable() {

				public void run() {

					try {
						Calendar calini = null;
						while ( contando ) {
							try {
								Thread.sleep( 1000 );
							} catch ( Exception e ) {
								System.out.println( "Contagem interrompida" );
							}
							calini = new GregorianCalendar();
							txtHorafim.setVlrTime( calini.getTime() );
						}
					} catch ( Exception e ) {
						e.printStackTrace();
						Funcoes.mensagemInforma( null, "Erro na contagem!" );
					} finally {
						Calendar calini = null;
						SpinnerDateModel sdm = null;
						DateEditor de = null;
					}
				}
			} );
			try {
				contador.start();
				contando = true;
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		else {
			try {
				btRun.setIcon( Icone.novo( "btplay.png" ) );
				contador.interrupt();
			} catch ( Exception e ) {
				System.out.println( "Contagem interrompida" );
			} finally {
				contando = false;
			}
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( txtDataAtendimento.getVlrDate().after( txtDataAtendimentoFin.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final menor que a data inicial!" );
				txtDataAtendimento.requestFocus();
				return;
			}
			if ( cbTipo.getVlrInteger().equals( -1 ) ) {
				Funcoes.mensagemInforma( this, "O tipo de atendimento n�o foi selecionado!" );
				cbTipo.requestFocus();
				return;
			}
			else if ( cbSetor.getVlrInteger().equals( -1 ) ) {
				Funcoes.mensagemInforma( this, "O setor n�o foi selecionado!" );
				cbSetor.requestFocus();
				return;
			}
			else if ( txaDescAtend.getVlrString().equals( "" ) ) {
				Funcoes.mensagemInforma( this, "N�o foi digitado nenhum procedimento!" );
				txaDescAtend.requestFocus();
				return;
			}
			else if ( txtDataAtendimento.getVlrString().equals( "" ) ) {
				Funcoes.mensagemInforma( this, "Data inicial � requerida!" );
				txtDataAtendimento.requestFocus();
				return;
			}
			else if ( txtDataAtendimentoFin.getVlrString().equals( "" ) ) {
				Funcoes.mensagemInforma( this, "Data final � requerida!" );
				txtDataAtendimentoFin.requestFocus();
				return;
			}
			else if ( txtHoraini.getVlrString().equals( "" ) ) {
				Funcoes.mensagemInforma( this, "Hora inicial � requerida!" );
				txtHoraini.requestFocus();
				return;
			}
			else if ( txtHorafim.getVlrString().equals( "" ) ) {
				Funcoes.mensagemInforma( this, "Hora final � requerida!" );
				txtHorafim.requestFocus();
				return;
			}
			else if ( txtCodAtend.getVlrInteger() <= 0 ) {
				Funcoes.mensagemInforma( this, "Selecione o atendente!" );
				txtCodAtend.requestFocus();
				return;
			}

			if ( update ) {
				try {
					updateAtend();
				} catch ( Exception e ) {
					Funcoes.mensagemInforma( this, "Erro ao gravar o atendimento!\n" + e.getMessage() );
					e.printStackTrace();
					return;
				}

			}
			else {
				try {
					insertAtend();
				} 
				catch ( Exception e ) {
					e.printStackTrace();
					Funcoes.mensagemInforma( this, "Erro ao gravar o atendimento!\n" + e.getMessage() );
					return;
				}
			}

			sinalizaChamado( false, txtCodChamado.getVlrInteger() );

			super.actionPerformed( evt );

		}
		else if ( evt.getSource() == btRun ) {
			iniciaContagem();
		}
		else if ( evt.getSource() == btCancel ) {
			sinalizaChamado( false, txtCodChamado.getVlrInteger() );
			dispose();
		}
		else {
			super.actionPerformed( evt );
		}
	}

	@ Override
	public void keyPressed( KeyEvent kevt ) {

		super.keyPressed( kevt );
		if ( kevt.getSource() == txtDataAtendimento ) {
			if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
				if ( !"".equals( txtDataAtendimento.getVlrString() ) ) {
					txtDataAtendimentoFin.setVlrDate( txtDataAtendimento.getVlrDate() );
				}
				else {
					Funcoes.mensagemInforma( this, "Digite a data do atendimento!" );
					txtDataAtendimento.requestFocus();
				}
			}
		}
	}

	public void valorAlterado( JComboBoxEvent evt ) {

		if ( evt.getComboBoxPad() == cbTipo ) {
			montaComboSetor();
		}
		if ( evt.getComboBoxPad() == cbContr ) {
			HashMap<String, Vector<Object>> vals = FuncoesCRM.montaComboItContr( con, cbContr.getVlrInteger(), "<Sem contrato>" );
			cbitContr.setItensGeneric( (Vector<?>) vals.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		montaComboTipo();
		montaComboSetor();
		montaComboStatus();

		lcAtendimento.setConexao( cn );
		lcAtend.setConexao( cn );

		lcCli.setConexao( cn );
		lcCli.carregaDados();
		
		lcEspec.setConexao( cn );
		lcEspec.carregaDados();

		lcChamado.setConexao( cn );
		lcChamado.carregaDados();
		
		

		sPrefs = getPref();
	}

	public Object[] getValores() {

		Object[] sVal = new Object[ 13 ];

		sVal[ 0 ] = String.valueOf( cbTipo.getVlrInteger() );
		sVal[ 1 ] = txtCodAtend.getVlrString();
		sVal[ 2 ] = String.valueOf( cbSetor.getVlrInteger() );
		sVal[ 3 ] = String.valueOf( iDoc );
		sVal[ 4 ] = txtDataAtendimento.getVlrDate();
		sVal[ 5 ] = txtDataAtendimentoFin.getVlrDate();
		sVal[ 6 ] = txtHoraini.getVlrString();
		sVal[ 7 ] = txtHorafim.getVlrString();
		sVal[ 8 ] = txaDescAtend.getVlrString();
		sVal[ 9 ] = cbContr.getVlrInteger();
		sVal[ 10 ] = cbitContr.getVlrInteger();
		sVal[ 11 ] = txtCodChamado.getVlrInteger();
		sVal[ 12 ] = txtCodEspec.getVlrInteger();

		return sVal;

	}

	private void sinalizaChamado( boolean em_atendimento, Integer codchamado ) {

		StringBuilder sql = new StringBuilder();

		try {

			if ( codchamado != null ) {

				sql.append( "update crchamado set ematendimento=? " );
				sql.append( "where codemp=? and codfilial=? and codchamado=? " );

				PreparedStatement ps = con.prepareStatement( sql.toString() );

				ps.setString( 1, em_atendimento ? "S" : "N" );

				ps.setInt( 2, lcChamado.getCodEmp() );
				ps.setInt( 3, lcChamado.getCodFilial() );

				ps.setInt( 4, codchamado );

				ps.executeUpdate();

				con.commit();
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcChamado ) {

			sinalizaChamado( true, txtCodChamado.getVlrInteger() );

			// Guardando o chamado sinalizado
			codchamado_ant = txtCodChamado.getVlrInteger();

		}
		else if (cevt.getListaCampos() == lcCli) {
			HashMap<String, Vector<Object>> vals = FuncoesCRM.montaComboContr( con, txtCodCli.getVlrInteger(), "<Sem contrato>" );
			cbContr.setItensGeneric( (Vector<?>) vals.get( "LAB" ), (Vector<?>) vals.get( "VAL" ) );
		}

	}

	public void beforeCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcChamado ) {

			sinalizaChamado( false, codchamado_ant );

		}

	}

}
