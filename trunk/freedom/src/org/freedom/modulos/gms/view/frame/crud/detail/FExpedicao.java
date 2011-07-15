/**
 * @version 11/07/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gms.view.frame.crud.detail <BR>
 *         Classe: @(#)FExpedicao.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * 
 *         Tela de cadastro de expedi��o de produtos.
 * 
 */

package org.freedom.modulos.gms.view.frame.crud.detail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.frame.FPassword;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.library.type.StringDireita;
import org.freedom.modulos.gms.business.object.Expedicao;
import org.freedom.modulos.gms.business.object.TipoExpedicao;
import org.freedom.modulos.gms.view.dialog.utility.DLPesagem;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.gms.view.frame.utility.FControleExpedicao;
import org.freedom.modulos.gms.view.frame.utility.FGeraRomaneio;
import org.freedom.modulos.std.view.frame.crud.detail.FVenda;
import org.freedom.modulos.std.view.frame.crud.plain.FAlmox;
import org.freedom.modulos.std.view.frame.crud.tabbed.FTransp;
import org.freedom.modulos.std.view.frame.report.FRomaneio;

public class FExpedicao extends FDetalhe implements FocusListener, CarregaListener, PostListener, InsertListener, KeyListener {

	// *** Constantes

	private static final long serialVersionUID = 1L;

	// *** Variaveis

	private HashMap<String, Object> prefere = null;

	private boolean novo = true;

	private Vector<String> vValsFrete = new Vector<String>();

	private Vector<String> vLabsFrete = new Vector<String>();

	// *** Campos (Cabe�alho)

	private JTextFieldPad txtTicket = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtPlaca = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTipoExpedDet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTipoExped = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtRefProdCab = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtRefProdDet = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtDescTipoExped = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodProdCab = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdCab = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodProdPadrao = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodMot = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeMot = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodVeic = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtModeloVeic = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtCNPJTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtVlrFrete = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, Aplicativo.casasDecFin );

	private JTextFieldPad txtCodProcExped = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtDescProcExped = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtTipoProcExped = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtStatus = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtCodRoma = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtStatusItExped = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtPeso1 = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDataPesagem = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtSaida = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtHoraPesagem = new JTextFieldPad( JTextFieldPad.TP_TIME, 8, 0 );

	private JRadioGroup<String, String> rgFrete = null;

	private JTextFieldPad txtCodAlmoxProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodAlmox = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescAlmox = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtPesoEntrada = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, 0 );
	
	private JTextFieldPad txtQtdInformada = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, 0 );
	
	private JTextFieldPad txtPesoMedio = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, 2 );
	
	private JTextFieldPad txtQtdRomaneio = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, 0 );
	
	private JTextFieldPad txtPesoRomaneio = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, 0 );
	
	private JTextFieldPad txtPesoSaida = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, 0 );
	
	private JTextFieldPad txtPesoLiquido = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, 0 );
	
	// *** Campos (Detalhe)

	private JTextFieldPad txtCodItExped = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodProdDet = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdDet = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	// *** Paineis

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

//	private JPanelPad pinDetGrid = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 2 ) );
	
	private JPanelPad pinDetGrid = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout(  ) );

	private JPanelPad pinCabObs = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	// *** Lista Campos

	private ListaCampos lcTran = new ListaCampos( this, "TN" );

	private ListaCampos lcProdCab = new ListaCampos( this, "PD" );

	private ListaCampos lcProdDet = new ListaCampos( this, "PD" );

	private ListaCampos lcTipoExped = new ListaCampos( this, "TE" );

	private ListaCampos lcProc = new ListaCampos( this, "TE" );

	private ListaCampos lcAlmox = new ListaCampos( this, "AX" );

	private ListaCampos lcMotorista = new ListaCampos( this, "MT" );

	private ListaCampos lcVeiculo = new ListaCampos( this, "VE" );

	// *** Labels

	private JLabelPad lbStatus = new JLabelPad();

	// *** Separador label

	private JLabelPad sepdet = new JLabelPad();

	private JLabelPad sepcab = new JLabelPad();
	
	// *** Bot�es

	private ImageIcon imgBalanca = Icone.novo( "btBalanca2.png" );
	
	private ImageIcon imgRomaneio = Icone.novo( "btRomaneio.png" );
	
	private JButtonPad btProcesso = new JButtonPad( "Pesagem", imgBalanca );

	// *** Tela do Painel de controle
	private FControleExpedicao tela_mae = null;

	// *** Tabelas

	private JTablePad tabPesagem = null;

	// *** Plugin

	private boolean atualizaPesoManual = false;

	private JTextAreaPad txaObs = new JTextAreaPad();

	private JScrollPane spnObs = new JScrollPane( txaObs );

	private JTabbedPanePad tpnCab = new JTabbedPanePad();

	private JPanelPad pinCabExpedicao = new JPanelPad();

	public FExpedicao() {

		montaTela();
	}
	
	Expedicao expedicao = null;

	private void montaTela() {

		setTitulo( "Expedi��o de produtos acabados" );
		setAtribos( 20, 20, 705, 550 );

		nav.setNavigation( true );

		configuraCampos();

		criaTabelas();
		montaListaCampos();
		montaPaineis();
		montaTab();
		ajustaTabela();
		adicListeners();

		setImprimir( true );

	}

	private void criaTabelas() {

		// Tabela de pesagens

		tabPesagem = new JTablePad();
		tabPesagem.setRowHeight( 21 );

		tabPesagem.adicColuna( "Seq." );
		tabPesagem.adicColuna( "Data" );
		tabPesagem.adicColuna( "Hora" );
		tabPesagem.adicColuna( "Peso" );

		tabPesagem.setColunaInvisivel( 0 );
		tabPesagem.setTamColuna( 60, 1 );
		tabPesagem.setTamColuna( 50, 2 );
		tabPesagem.setTamColuna( 75, 3 );

	}

	private void montaPaineis() {

		pnMaster.remove( spTab );
		pnMaster.add( pinDetGrid, BorderLayout.CENTER );

		spTab.setPreferredSize( new Dimension(500,500) );
		
		pinDetGrid.add( spTab, BorderLayout.WEST );
		pinDetGrid.add( new JScrollPane( tabPesagem), BorderLayout.CENTER );

		pnCliCab.add( tpnCab );
		tpnCab.addTab( "Geral", pinCabExpedicao );

		pinCabObs.add( spnObs );
		tpnCab.addTab( "Observa��es", pinCabObs );

		montaCabecalho();
		montaDetalhe();

	}

	public FExpedicao( boolean novo ) {

		super();

		this.novo = novo;

		montaTela();

	}

	private void configuraCampos() {

		txtPlaca.setUpperCase( true );
		txtPlaca.setMascara( JTextFieldPad.MC_PLACA );

		vValsFrete.addElement( "C" );
		vValsFrete.addElement( "F" );
		vLabsFrete.addElement( "CIF" );
		vLabsFrete.addElement( "FOB" );

		rgFrete = new JRadioGroup<String, String>( 1, 2, vLabsFrete, vValsFrete, -4 );

		lbStatus.setForeground( Color.WHITE );
		lbStatus.setBackground( Color.GRAY );
		lbStatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbStatus.setOpaque( true );
		lbStatus.setHorizontalAlignment( SwingConstants.CENTER );
	}

	private void montaCabecalho() {

		setAltCab( 275 );

		setListaCampos( lcCampos );
		setPainel( pinCabExpedicao );

		adicCampo( txtTicket			, 7		, 20	, 70	, 20, "Ticket"			, "Ticket"		, ListaCampos.DB_PK, true );
		adic( txtPlaca					, 80	, 20	, 70	, 20 );

		adicCampo( txtCodTipoExped		, 153	, 20	, 40	, 20, "CodTipoExped"	, "C�d.T."		, ListaCampos.DB_FK, txtDescTipoExped, true );
		adicDescFK( txtDescTipoExped	, 196	, 20	, 218	, 20, "DescTipoExped"	, "Tipo de expedi��o" );

		adicCampo( txtDtSaida			, 417	, 20	, 70	, 20, "DtSaida"			, "Data"		, ListaCampos.DB_SI, true );

		adicCampo( txtCodProdCab		, 7		, 60	, 70	, 20, "CodProd"			, "Cod.Pd."		, ListaCampos.DB_FK, txtDescProdCab, true );
		adicDescFK( txtDescProdCab		, 80	, 60	, 200	, 20, "DescProd"		, "Descri��o do Produto" );
		
		adicCampoInvisivel( txtRefProdCab, "RefProd", "Refer�ncia", ListaCampos.DB_SI, false );

		adicCampo( txtCodAlmox			, 283	, 60	, 50	, 20, "CodAlmox"		, "Almox."		, ListaCampos.DB_FK, true );
		adicDescFK( txtDescAlmox		, 336	, 60	, 150	, 20, "DescAlmox"		, "Descri��o do almoxarifado" );

		adicCampo( txtCodVeic			, 7		, 100	, 70	, 20, "CodVeic"			, "Cod.Veic."	, ListaCampos.DB_FK, txtModeloVeic, true );
		adicDescFK( txtModeloVeic		, 80	, 100	, 405	, 20, "Modelo"			, "Modelo do ve�culo" );
		
		adicCampo( txtCodMot			, 7		, 140	, 70	, 20, "CodMot"			, "Cod.Mot."	, ListaCampos.DB_FK, txtNomeMot, true );
		adicDescFK( txtNomeMot			, 80	, 140	, 405	, 20, "NomeMot"			, "Nome do motorista" );

		adicCampo( txtCodTran			, 7		, 180	, 70	, 20, "CodTran"			, "Cod.Tran."	, ListaCampos.DB_FK, txtNomeTran, true );
		adicDescFK( txtNomeTran			, 80	, 180	, 305	, 20, "NomeTran"		, "Nome da transportadora" );

		adicCampo( txtQtdInformada		, 388	, 180	, 97	, 20, "QtdInformada"	, "Qtd.Informada", ListaCampos.DB_SI, false );
		
		adicCampoInvisivel( txtStatus	, "Status"		, "Status"	, ListaCampos.DB_SI	, false );
		adicCampoInvisivel( txtCodRoma	, "CodRoma"		, "C�d.Roma", ListaCampos.DB_SI	, false );

		adicCampo( txtPesoEntrada		, 500	, 63	, 80	, 35, "PesoEntrada"		, "Peso inicial", ListaCampos.DB_SI, false );
		adicCampo( txtPesoSaida			, 500	, 115	, 80	, 35, "PesoSaida"		, "Peso final"	, ListaCampos.DB_SI, false );
		adic( txtPesoLiquido			, 500	, 166	, 80	, 35, "Peso L�quido" );
		
		adic( txtPesoMedio				, 583	, 63	, 90	, 35, "Peso m�dio" );
		adic( txtQtdRomaneio			, 583	, 115	, 90	, 35, "Qtd.Romaneio" );
		adic( txtPesoRomaneio			, 583	, 166	, 90	, 35, "Peso Romaneio" );
		
		txtPesoEntrada.setSoLeitura( true );
		txtPesoSaida.setSoLeitura( true );
		txtPesoLiquido.setSoLeitura( true );
		txtPesoMedio.setSoLeitura( true );
		txtQtdRomaneio.setSoLeitura( true );
		txtPesoRomaneio.setSoLeitura( true );
		
		txtPesoEntrada.setFont( SwingParams.getFontboldmax(3) );
		txtPesoSaida.setFont( SwingParams.getFontboldmax(3) );
		txtPesoLiquido.setFont( SwingParams.getFontboldmax(3) );
		txtPesoMedio.setFont( SwingParams.getFontboldmax(3) );
		txtQtdRomaneio.setFont( SwingParams.getFontboldmax(3) );
		txtPesoRomaneio.setFont( SwingParams.getFontboldmax(3) );
		
		adicDBLiv( txaObs, "ObsExped", "Observa��es", false );

		sepcab.setBorder( BorderFactory.createEtchedBorder() );
		adic( sepcab					, 493	, 4		, 2		, 200 );

		adic( lbStatus					, 500	, 4		, 173	, 40 );

		setListaCampos( true, "EXPEDICAO", "EQ" );

		lcCampos.setQueryInsert( true );

	}

	private void montaDetalhe() {

		setAltDet( 65 );

		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		lcDet.setPodeIns( false );
		lcDet.setPodeExc( false );

		navRod.btNovo.setEnabled( false );
		navRod.btExcluir.setEnabled( false );

		adicCampo( txtCodItExped		, 7		, 20	, 40	, 20, "CodItExped", "Seq.", ListaCampos.DB_PK, true );
		
		adicCampo( txtCodProcExped 		, 50	, 20	, 70	, 20, "CodProcExped", "Cod.Proc.", ListaCampos.DB_FK, txtDescProcExped, true );
		adicDescFK( txtDescProcExped	, 123	, 20	, 340	, 20, "DescProcExped", "Descri��o do processo" );

		adicCampoInvisivel( txtCodTipoExpedDet, "CodTipoExped", "Cod.Tp.Exped.", ListaCampos.DB_SI, true );

		JLabelPad lbcodprod = adicCampo( txtCodProdDet			, 50	, 60	, 50	, 20, "CodProd", "C�d.Pd.", ListaCampos.DB_FK, txtDescProdDet, true );
		JLabelPad lbdescprod = adicDescFK( txtDescProdDet		, 103	, 60	, 230	, 20, "DescProd", "Descri��o do Produto" );
		adicCampoInvisivel( txtRefProdDet, "RefProd", "Refer�ncia", ListaCampos.DB_SI, false );
		
		txtCodProdDet.setVisible( false );
		txtDescProdDet.setVisible( false );
		lbcodprod.setVisible( false );
		lbdescprod.setVisible( false );
		
		txtCodProcExped.setEditable( false );
		txtCodItExped.setEditable( false );
		
		txtStatusItExped.setSoLeitura( true );
		adicCampoInvisivel( txtStatusItExped, "StatusItExped", "Status", ListaCampos.DB_SI, false );

		setListaCampos( true, "ITEXPEDICAO", "EQ" );
		lcDet.setQueryInsert( false );

		sepdet.setBorder( BorderFactory.createEtchedBorder() );
		adic( sepdet, 495, 4, 2, 48 );

		adic( btProcesso, 507, 4, 168, 47 );
		btProcesso.setToolTipText( "Realiza pesagem - (F12)" );
		
		
	}

	private void carregaTipoRec() {

		txtCodTipoExped.setVlrInteger( (Integer) prefere.get( "codtipoexped" ) );
		lcTipoExped.carregaDados();

	}

	private void buscaPrefere() {

		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			prefere = new HashMap<String, Object>();

			sql.append( "select coalesce(pf8.codtipoexped,0) codtipoexped " );
			sql.append( "from sgprefere8 pf8 " );
			sql.append( "where pf8.codemp=? and pf8.codfilial=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE8" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				prefere.put( "codtipoexped", rs.getInt( "codtipoexped" ) );
			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void buscaVeiculo( String placa ) {

		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sql.append( "select ve.codveic from vdveiculo ve " );
			sql.append( "where ve.codemp=? and ve.codfilial=? and ve.placa=?" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDTRANSP" ) );
			ps.setString( 3, placa );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				txtCodVeic.setVlrInteger( rs.getInt( "codveic" ) );
				lcVeiculo.carregaDados();

			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void buscaMotorista( Integer codtran ) {

		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sql.append( "select first 1 tm.codmot " );
			sql.append( "from vdtranspmot tm " );
			sql.append( "where tm.codemp=? and tm.codfilial=? and tm.codtran=?" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDTRANSP" ) );
			ps.setInt( 3, codtran );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				txtCodMot.setVlrInteger( rs.getInt( "codmot" ) );

				lcMotorista.setWhereAdic( "CODTRAN=" + codtran );

				lcMotorista.carregaDados();

			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void buscaTransp( String placa ) {

		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sql.append( "select tp.codtran from vdtransp tp " );
			sql.append( "where tp.codemp=? and tp.codfilial=? and tp.placatran=?" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDTRANSP" ) );
			ps.setString( 3, placa );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				txtCodTran.setVlrInteger( rs.getInt( "codtran" ) );
				lcTran.carregaDados();

			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void ajustaTabela() {

		tab.setRowHeight( 21 );
		
		tab.setTamColuna( 30, 0 ); //Sequencial
		tab.setColunaInvisivel( 1 ); //C�digo do processo
		tab.setTamColuna( 210, 2 ); //Descri��o do processo
		tab.setColunaInvisivel( 3 ); //C�digo do tipo de expedi��o
		tab.setColunaInvisivel( 4 ); //C�digo do produto
		tab.setColunaInvisivel( 5 ); //Descri��o do produto
		tab.setColunaInvisivel( 6 ); //Referencia do produto
		tab.setTamColuna( 50, 7 ); //Status
		tab.setTamColuna( 65, 8 ); //Data de inser��o
		tab.setTamColuna( 65, 9 ); //Hora de inser��o
		tab.setTamColuna( 65, 10 ); //Usuario que inseriu
		
		tab.setColunaInvisivel( 11 ); //Data de altera��o
		tab.setColunaInvisivel( 12 ); //Hora de altera��o
		tab.setColunaInvisivel( 13 ); //Usu�rio de altera��o
		
	}

	public void exec( int ticket, int tiporecmerc, FControleExpedicao tela_mae ) {

		try {
			lcCampos.edit();
			txtTicket.setVlrInteger( ticket );
			txtCodTipoExped.setVlrInteger( tiporecmerc );
			lcCampos.carregaDados();

			setTelaMae( tela_mae );

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void adicListeners() {

		txtPlaca.addFocusListener( this );

		lcCampos.addInsertListener( this );
		lcCampos.addCarregaListener( this );
		lcDet.addPostListener( this );
		lcDet.addCarregaListener( this );
		lcTipoExped.addCarregaListener( this );
		lcProdCab.addCarregaListener( this );

		btProcesso.addActionListener( this );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

	}

	private void montaListaCampos() {

		// * Tipo de Recebimento Cabe�alho

		lcTipoExped.add( new GuardaCampo( txtCodTipoExped	, "CodTipoExped"	, "C�d.Tipo.Exp."					, ListaCampos.DB_PK, false ) );
		lcTipoExped.add( new GuardaCampo( txtDescTipoExped	, "DescTipoExped"	, "Descri��o do tipo de expedi��o"	, ListaCampos.DB_SI, false ) );
		lcTipoExped.add( new GuardaCampo( txtCodProdPadrao	, "Codprod"			, "C�d.Prod."						, ListaCampos.DB_SI, false ) );

		txtCodTipoExped.setTabelaExterna( lcTipoExped, FTipoRecMerc.class.getCanonicalName() );
		txtCodTipoExped.setNomeCampo( "CodTipoExped" );
		txtCodTipoExped.setFK( true );

		lcTipoExped.setReadOnly( true );
		lcTipoExped.montaSql( false, "TIPOEXPEDICAO", "EQ" );

		// * Produto (Cabe�alho)

		lcProdCab.add( new GuardaCampo( txtCodProdCab	, "CodProd"		, "C�d.prod."				, ListaCampos.DB_PK, false ) );
		lcProdCab.add( new GuardaCampo( txtDescProdCab	, "DescProd"	, "Descri��o do produto"	, ListaCampos.DB_SI, false ) );
		lcProdCab.add( new GuardaCampo( txtRefProdCab	, "RefProd"		, "Refer�ncia"				, ListaCampos.DB_SI, false ) );
		lcProdCab.add( new GuardaCampo( txtCodAlmoxProd	, "CodAlmox"	, "C�d.Almox."				, ListaCampos.DB_SI, false ) );

		txtCodProdCab.setTabelaExterna( lcProdCab, FProduto.class.getCanonicalName() );
		txtCodProdCab.setNomeCampo( "CodProd" );
		txtCodProdCab.setFK( true );

		lcProdCab.setReadOnly( true );
		lcProdCab.montaSql( false, "PRODUTO", "EQ" );

		// * Almoxarifado (Cabe�alho)

		lcAlmox.add( new GuardaCampo( txtCodAlmox		, "CodAlmox"		, "C�d.almox."				, ListaCampos.DB_PK, true ) );
		lcAlmox.add( new GuardaCampo( txtDescAlmox		, "DescAlmox"		, "Descri��o do almoxarifado", ListaCampos.DB_SI, false ) );
		
		lcAlmox.montaSql( false, "ALMOX", "EQ" );
		lcAlmox.setReadOnly( true );
		lcAlmox.setQueryCommit( false );
		txtCodAlmox.setTabelaExterna( lcAlmox, FAlmox.class.getCanonicalName() );

		// * Transportadora

		lcTran.add( new GuardaCampo( txtCodTran			, "CodTran"			, "C�d.For."				, ListaCampos.DB_PK, false ) );
		lcTran.add( new GuardaCampo( txtNomeTran		, "NomeTran"		, "Nome da transportadora"	, ListaCampos.DB_SI, false ) );
		lcTran.add( new GuardaCampo( txtCNPJTran		, "CnpjTran"		, "CNPJ"					, ListaCampos.DB_SI, false ) );

		txtCodTran.setTabelaExterna( lcTran, FTransp.class.getCanonicalName() );
		txtCodTran.setNomeCampo( "CodTran" );
		txtCodTran.setFK( true );

		lcTran.setReadOnly( true );
		lcTran.montaSql( false, "TRANSP", "VD" );

		// * Produto (Detalhe)

		lcProdDet.add( new GuardaCampo( txtCodProdDet	, "CodProd"			, "C�d.prod."				, ListaCampos.DB_PK, false ) );
		lcProdDet.add( new GuardaCampo( txtDescProdDet	, "DescProd"		, "Descri��o do produto"	, ListaCampos.DB_SI, false ) );
		lcProdDet.add( new GuardaCampo( txtRefProdDet	, "RefProd"			, "Refer�ncia"				, ListaCampos.DB_SI, false ) );

		txtCodProdDet.setTabelaExterna( lcProdDet, FProduto.class.getCanonicalName() );
		txtCodProdDet.setNomeCampo( "CodProd" );
		txtCodProdDet.setFK( true );

		lcProdDet.setReadOnly( true );
		lcProdDet.montaSql( false, "PRODUTO", "EQ" );

		/***************
		 * PROCESSOS *
		 ***************/

		lcProc.add( new GuardaCampo( txtCodProcExped	, "CodProcExped"	, "C�d.Proc."				, ListaCampos.DB_PK, false ) );
		lcProc.add( new GuardaCampo( txtDescProcExped	, "DescProcExped"	, "Descri��o do processo"	, ListaCampos.DB_SI, false ) );
		lcProc.add( new GuardaCampo( txtCodTipoExpedDet	, "CodTipoExped"	, "Cod.Tp.Exp."				, ListaCampos.DB_SI, false ) );
		lcProc.add( new GuardaCampo( txtTipoProcExped	, "TipoProcExped"	, "Tp.Proc.Exp."			, ListaCampos.DB_SI, false ) );

		txtCodProcExped.setTabelaExterna( lcProc, null );
		txtCodProcExped.setNomeCampo( "CodProcExped" );
		txtCodProcExped.setFK( true );

		lcProc.setReadOnly( true );
		lcProc.montaSql( false, "PROCEXPED", "EQ" );

		/***************
		 * MOTORISTAS *
		 ***************/

		lcMotorista.add( new GuardaCampo( txtCodMot		, "CodMot"	, "C�d.Mot."			, ListaCampos.DB_PK, false ) );
		lcMotorista.add( new GuardaCampo( txtNomeMot	, "NomeMot"	, "Nome do motorista"	, ListaCampos.DB_SI, false ) );

		txtCodMot.setTabelaExterna( lcMotorista, null );
		txtCodMot.setNomeCampo( "CodMot" );
		txtCodMot.setFK( true );

		lcMotorista.setReadOnly( true );
		lcMotorista.montaSql( false, "MOTORISTA", "VD" );

		/***************
		 * VEICULOS *
		 ***************/

		lcVeiculo.add( new GuardaCampo( txtCodVeic		, "CodVeic"	, "C�d.Veic."			, ListaCampos.DB_PK, false ) );
		lcVeiculo.add( new GuardaCampo( txtModeloVeic	, "Modelo"	, "Modelo do ve�culo"	, ListaCampos.DB_SI, false ) );
		lcVeiculo.add( new GuardaCampo( txtPlaca		, "Placa"	, "Placa"				, ListaCampos.DB_SI, false ) );
		
		txtCodVeic.setTabelaExterna( lcVeiculo, null );
		txtCodVeic.setNomeCampo( "CodVeic" );
		txtCodVeic.setFK( true );

		lcVeiculo.setReadOnly( true );
		lcVeiculo.montaSql( false, "VEICULO", "VD" );

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		else if ( evt.getSource() == btProcesso ) {
			if ( validaProcesso() ) {
				
				if(txtTipoProcExped.getVlrString().equals( TipoExpedicao.PROCESSO_ROMANEIO.getValue() )) {
				
					geraRomaneio();
					
				}
				else {
				
					capturaAmostra();
					
				}
			}
		}

		super.actionPerformed( evt );
	}
	
	private void geraRomaneio() {
		
		if( txtCodRoma.getVlrInteger()>0 ) {
			
			FRomaneio romaneio = null;
			
			if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
				romaneio = (FRomaneio) Aplicativo.telaPrincipal.getTela( FRomaneio.class.getName() );
			}
			else {
				romaneio = new FRomaneio();
				Aplicativo.telaPrincipal.criatela( "Romaneio de carga", romaneio, con );
			}

			romaneio.exec( txtCodRoma.getVlrInteger() );
			
		}
		else {
	
			FGeraRomaneio romaneio = null;
			
			if ( Aplicativo.telaPrincipal.temTela( FVenda.class.getName() ) ) {
					romaneio = (FGeraRomaneio) Aplicativo.telaPrincipal.getTela( FGeraRomaneio.class.getName() );
			}
			else {
				romaneio = new FGeraRomaneio();
				Aplicativo.telaPrincipal.criatela( "Gera��o de romaneio de carga", romaneio, con );
			}
	
			romaneio.exec( txtDtSaida.getVlrDate(), txtCodTran.getVlrInteger(), txtCodProdDet.getVlrInteger(), txtTicket.getVlrInteger() );
		
		}
		
	}

	private boolean validaProcesso() {

		boolean ret = false;
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			if ( "PE".equals( txtStatusItExped.getVlrString() ) ) {

				sql.append( "select first 1 statusitexped from eqitexpedicao " );
				sql.append( "where codemp=? and codfilial=? and ticket=? and coditexped < ? " );
				sql.append( "order by coditexped desc" );

				ps = con.prepareStatement( sql.toString() );

				ps.setInt( 1, lcCampos.getCodEmp() );
				ps.setInt( 2, lcCampos.getCodFilial() );
				ps.setInt( 3, txtTicket.getVlrInteger() );
				ps.setInt( 4, txtCodItExped.getVlrInteger() );

				rs = ps.executeQuery();

				if ( rs.next() ) {

					String statusant = rs.getString( "statusitexped" );

					if ( "PE".equals( statusant ) ) {
						Funcoes.mensagemInforma( this, "Processo anterior ainda est� pendente!" );
						ret = false;
					}
					else if ( "FN".equals( statusant ) ) {
						ret = true;
					}

				}
				else {
					ret = true;
				}
			}
			else {

				if ( atualizaPesoManual ) {
					AtualizaAmostra();
				}
				else {
					Funcoes.mensagemInforma( this, "Processo ja foi finalizado" );
				}
				ret = false;
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return ret;
	}

	private void imprimir( boolean bVisualizar ) {

		ImprimeOS imp = new ImprimeOS( "", con );

		String DataP1 = null;
		String HoraP1 = null;
		String UnidP1 = null;
		String interno = null;
		BigDecimal PesoP1 = null;

		String DataP2 = null;
		String HoraP2 = null;
		String UnidP2 = null;

		BigDecimal PesoP2 = null;
		BigDecimal PesoLiq = null;
		BigDecimal PesoTotal = null;
		BigDecimal media = null;
		BigDecimal desconto = null;

		String renda = null;

		Expedicao expedicao = new Expedicao( this, txtTicket.getVlrInteger(), con );

		try {

			try {

				HashMap<String, Object> p1 = expedicao.getPrimeirapesagem();

				PesoP1 = (BigDecimal) p1.get( "peso" );
				DataP1 = (String) p1.get( "data" );
				HoraP1 = (String) p1.get( "hora" );
				UnidP1 = (String) p1.get( "unid" );
				interno = (String) p1.get( "interno" );

				HashMap<String, Object> p2 = expedicao.getSegundapesagem();

				PesoP2 = (BigDecimal) p2.get( "peso" );
				DataP2 = (String) p2.get( "data" );
				HoraP2 = (String) p2.get( "hora" );
				UnidP2 = (String) p2.get( "unid" );

				PesoLiq = PesoP1.subtract( PesoP2 );

				PesoTotal = PesoLiq;
				

			} catch ( Exception e ) {
				System.out.println( "Erro ao buscar pesagens!" );
			}

			imp.pulaLinha( 0, imp.comprimido() + " " );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 77, txtDescAlmox.getVlrString() );

			imp.pulaLinha( 2, imp.comprimido() );

			imp.say( imp.pRow(), 3, "PLACA:............:" );
			imp.say( imp.pRow(), 24, Funcoes.setMascara( txtPlaca.getVlrString(), JTextFieldPad.mascplaca ) );

			imp.say( imp.pRow(), 35, "FRETE:" );
			imp.say( imp.pRow(), 41, "F".equals( rgFrete.getVlrString() ) ? "FOB" : "CIF" );

			imp.say( imp.pRow(), 70, "INTERNO.:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );

			imp.say( imp.pRow(), 48, StringFunctions.strZero( txtTicket.getVlrString(), 10 ) );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "PRODUTO:..........:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );
			imp.say( imp.pRow(), 15, txtDescProdCab.getVlrString().trim() );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "PRODUTOR:.........:" );

			imp.pulaLinha( 2, imp.comprimido() );

			imp.say( imp.pRow(), 3, "PRIMEIRA PESAGEM..:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );

			imp.say( imp.pRow(), 15, Funcoes.strDecimalToStrCurrency( 7, 0, String.valueOf( PesoP1 ) ) );

			imp.say( imp.pRow(), 23, UnidP1 );
			imp.say( imp.pRow(), 30, DataP1 );
			imp.say( imp.pRow(), 46, HoraP1 );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "SEGUNDA PESAGEM...:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );

			imp.say( imp.pRow(), 15, Funcoes.strDecimalToStrCurrency( 7, 0, String.valueOf( PesoP2 ) ) );
			imp.say( imp.pRow(), 23, UnidP2 );
			imp.say( imp.pRow(), 30, DataP2 );
			imp.say( imp.pRow(), 46, HoraP2 );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 3, "LIQUIDO...........:" );

			imp.say( imp.pRow(), 0, " " + imp.normal() );

			imp.say( imp.pRow(), 15, Funcoes.strDecimalToStrCurrency( 7, 0, String.valueOf( PesoLiq ) ) );
			imp.say( imp.pRow(), 23, UnidP1 );

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( imp.pRow(), 3, "DESCONTO..........:" );
			imp.say( imp.pRow(), 15, Funcoes.strDecimalToStrCurrency( 2, 2, String.valueOf( desconto ) ) + " %" );

			imp.say( imp.pRow(), 30, "OBS.:" + Funcoes.copy( txaObs.getVlrString(), 0, 30 ) );

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( imp.pRow(), 3, "TOTAL.............:" );

			imp.say( imp.pRow(), 15, Funcoes.strDecimalToStrCurrency( 7, 0, String.valueOf( PesoTotal ) ) );
			imp.say( imp.pRow(), 23, UnidP1 );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.pulaLinha( 1, imp.comprimido() );

			imp.say( imp.pRow(), 0, " " + imp.normal() );


			imp.pulaLinha( 3 );
			imp.fechaGravacao();

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro na impress�o de ticket!\n" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcProdCab.setConexao( cn );
		lcProdDet.setConexao( cn );
		lcAlmox.setConexao( cn );
		lcTran.setConexao( cn );
		lcTipoExped.setConexao( cn );
		lcProc.setConexao( cn );
		lcMotorista.setConexao( cn );
		lcVeiculo.setConexao( cn );

		buscaPrefere();

		if ( novo ) {
			lcCampos.insert( true );
		}

	}

	private void capturaAmostra() {

		DLPesagem dl = null;

		try {

			dl = new DLPesagem( this, txtTipoProcExped.getVlrString(),true );

			dl.setConexao( con );
			dl.execShow();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		if ( dl.OK ) {

			txtPeso1.setVlrBigDecimal( dl.getPeso1() );

			txtDataPesagem.setVlrDate( dl.getData() );
			txtHoraPesagem.setVlrString( dl.getHora() );

			if ( txtPeso1.getVlrBigDecimal().floatValue() > 0 && txtDataPesagem.getVlrDate() != null && txtHoraPesagem.getVlrString() != null ) {

				salvaAmostra();

			}

			limpaAmostra();
			montaTabPesagem();

		}

		dl.dispose();
	}

	private void limpaAmostra() {

		txtPeso1.setVlrBigDecimal( new BigDecimal( 0 ) );
		txtDataPesagem.setVlrDate( null );
		txtHoraPesagem.setVlrString( null );
	}

	private void salvaAmostra() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		Integer codamostragem = 0;
		ResultSet rs = null;

		try {

			sql.append( "select coalesce( max(codamostragem), 0 ) " );
			sql.append( "from eqexpedamostragem " );
			sql.append( "where " );
			sql.append( "codemp=? and codfilial=? and ticket=? and coditexped=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, lcDet.getCodEmp() );
			ps.setInt( 2, lcDet.getCodFilial() );
			ps.setInt( 3, txtTicket.getVlrInteger() );
			ps.setInt( 4, txtCodItExped.getVlrInteger() );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				codamostragem = rs.getInt( 1 );
			}

			codamostragem++;

			sql = new StringBuilder();

			sql.append( "insert into eqexpedamostragem " );
			sql.append( "(codemp,codfilial,ticket,coditexped,codamostragem,pesoamost,dataamost,horaamost)" );
			sql.append( "values(?, ?, ?, ?, ?, ?, ?, ?)" );

			ps = con.prepareStatement( sql.toString() );

			Integer coditrecmerc = txtCodItExped.getVlrInteger();

			ps.setInt( 1, lcDet.getCodEmp() );
			ps.setInt( 2, lcDet.getCodFilial() );
			ps.setInt( 3, txtTicket.getVlrInteger() );
			ps.setInt( 4, coditrecmerc );
			ps.setInt( 5, codamostragem );
			ps.setBigDecimal( 6, txtPeso1.getVlrBigDecimal() );
			ps.setDate( 7, Funcoes.dateToSQLDate( txtDataPesagem.getVlrDate() ) );
			ps.setTime( 8, Funcoes.strTimeTosqlTime( txtHoraPesagem.getVlrString() ) );

			ps.execute();
			con.commit();

			lcDet.edit();
			lcDet.post();

			txtCodItExped.setVlrInteger( coditrecmerc );
			lcDet.carregaDados();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void AtualizaAmostra() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		Integer codamostragem = 0;
		ResultSet rs = null;

		try {

			sql = new StringBuilder();

			sql.append( "update eqexpedamostragem set pesoamost=? " );
			sql.append( "where codemp=? and codfilial=? and ticket=? and coditexped=? and codamostragem=? " );

			int i = 0;

			while ( tabPesagem.getNumLinhas() > i ) {

				codamostragem = (Integer) tabPesagem.getValor( i, 0 );

				txtPeso1.setVlrBigDecimal( ConversionFunctions.stringCurrencyToBigDecimal( ( (StringDireita) tabPesagem.getValor( i, 3 ) ).toString() ) );

				ps = con.prepareStatement( sql.toString() );

				ps.setBigDecimal( 1, txtPeso1.getVlrBigDecimal() );

				ps.setInt( 3, lcDet.getCodEmp() );
				ps.setInt( 4, lcDet.getCodFilial() );
				ps.setInt( 5, txtTicket.getVlrInteger() );
				ps.setInt( 6, txtCodItExped.getVlrInteger() );
				ps.setInt( 7, codamostragem );

				ps.execute();

				i++;
			}

			con.commit();

			// lcDet.edit();
			// lcDet.post();

			montaTabPesagem();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void focusGained( FocusEvent e ) {

		// TODO Auto-generated method stub

	}

	public void focusLost( FocusEvent e ) {

		if ( e.getSource() == txtPlaca ) {

			if ( txtCodTran.getVlrInteger() == 0 ) {
				buscaTransp( txtPlaca.getVlrString() );
				buscaVeiculo( txtPlaca.getVlrString() );
				buscaMotorista( txtCodTran.getVlrInteger() );
			}

		}

	}

	private void montaTabPesagem() {

		try {

			StringBuilder sql = new StringBuilder();

			sql.append( "select " );
			sql.append( "codamostragem, pesoamost, dataamost, horaamost " );
			sql.append( "from eqexpedamostragem " );
			sql.append( "where codemp=? and codfilial=? and ticket=? and coditexped=? " );

			StringBuffer status = new StringBuffer( "" );

			System.out.println( "SQL:" + sql.toString() );

			PreparedStatement ps = con.prepareStatement( sql.toString() );

			int iparam = 1;

			ps.setInt( iparam++, lcDet.getCodEmp() );
			ps.setInt( iparam++, lcDet.getCodFilial() );
			ps.setInt( iparam++, txtTicket.getVlrInteger() );
			ps.setInt( iparam++, txtCodItExped.getVlrInteger() );

			ResultSet rs = ps.executeQuery();

			tabPesagem.limpa();

			int row = 0;

			while ( rs.next() ) {

				tabPesagem.adicLinha();

				tabPesagem.setValor( rs.getInt( "codamostragem" ), row, 0 );
				tabPesagem.setValor( rs.getDate( "dataamost" ), row, 1 );
				tabPesagem.setValor( rs.getString( "horaamost" ), row, 2 );
				tabPesagem.setValor( Funcoes.bdToStr( rs.getBigDecimal( "pesoamost" ), 2 ), row, 3 );

				row++;

			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void atualizaStatus() {

		Expedicao.atualizaStatus( txtStatus.getVlrString(), lbStatus );

	}

	private void calculaPesoLiquido() {
		
		BigDecimal pesoentrada = txtPesoEntrada.getVlrBigDecimal();
		BigDecimal pesosaida = txtPesoSaida.getVlrBigDecimal();
		BigDecimal pesoliquido = new BigDecimal(0);
		
		try {
			
			if(pesoentrada!=null && pesosaida!=null) {
				
				pesoliquido = pesosaida.subtract( pesoentrada );
			
			}
			
			txtPesoLiquido.setVlrBigDecimal( pesoliquido );
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void calculaPesoMedio() {
		
		BigDecimal pesoliquido = txtPesoLiquido.getVlrBigDecimal();
		BigDecimal qtdinformad = txtQtdInformada.getVlrBigDecimal();
		BigDecimal pesomedio   = new BigDecimal(0);
		
		try {
			
			if(qtdinformad!=null && pesoliquido!=null && pesoliquido.floatValue()>0 && qtdinformad.floatValue()>0) {
				
				pesomedio = pesoliquido.divide( qtdinformad, BigDecimal.ROUND_CEILING );
				
			}
			
			txtPesoMedio.setVlrBigDecimal( pesomedio );
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void afterCarrega( CarregaEvent cevt ) {

		
		
		if ( cevt.getListaCampos() == lcCampos ) {
			
			expedicao = new Expedicao( this, txtTicket.getVlrInteger(), con ); 
			
			atualizaStatus();
			// Se n�o foi realizada nenhuma pesagem deve carregar a sequencia 1 para facilitar a utilizacao
			if ( txtStatus.getVlrString().equals( Expedicao.STATUS_PENDENTE.getValue() ) ) {
				carregaSequencia( 0 );
				txtPesoRomaneio.setVlrBigDecimal( new BigDecimal(0) );
				txtQtdRomaneio.setVlrBigDecimal( new BigDecimal(0) );
				
			}
			// Se ja tiver sido realizada a pesagem 1 deve carregar a sequencia 2 para facilitar a utilizacao
			if ( txtStatus.getVlrString().equals( Expedicao.STATUS_PESAGEM_INICIAL.getValue() ) ) {
				carregaSequencia( 1 );
				txtPesoRomaneio.setVlrBigDecimal( new BigDecimal(0) );
				txtQtdRomaneio.setVlrBigDecimal( new BigDecimal(0) );
				
			}
			// Se ja tiver sido realizada a pesagem 2 deve carregar a proxima sequencia
			else if ( txtStatus.getVlrString().equals( Expedicao.STATUS_PESAGEM_SAIDA.getValue() ) ) {
				carregaSequencia( 2 );
				
				txtPesoRomaneio.setVlrBigDecimal( expedicao.getRomaneio().get( "PESO" ) );
				txtQtdRomaneio.setVlrBigDecimal( expedicao.getRomaneio().get( "QTD" ) );
				
			}
			else {
				txtPesoRomaneio.setVlrBigDecimal( new BigDecimal(0) );
				txtQtdRomaneio.setVlrBigDecimal( new BigDecimal(0) );
				
			}
			
			
			calculaPesoLiquido();
			calculaPesoMedio();
			
			

		}
		else if ( cevt.getListaCampos() == lcTipoExped ) {
			carregaProdutoPadrao();
		}

		else if ( cevt.getListaCampos() == lcDet ) {

			limpaAmostra();

			montaTabPesagem();
			
			if(txtTipoProcExped.getVlrString().equals( TipoExpedicao.PROCESSO_ROMANEIO.getValue() )) {
			
				btProcesso.setIcon( imgRomaneio );
				btProcesso.setText( "Romaneio" );
				
			}
			else {
				
				btProcesso.setIcon( imgBalanca );
				btProcesso.setText( "Pesagem" );
				
			}

		}
		else if ( cevt.getListaCampos() == lcProdCab && lcCampos.getStatus() == ListaCampos.LCS_INSERT ) {

			txtCodAlmox.setVlrInteger( txtCodAlmoxProd.getVlrInteger() );
			lcAlmox.carregaDados();

		}

	}

	// Futuramente deve ser implementado para buscar automaticamente o
	// coditrecmerc a partir do status e do tipo de processo.
	private void carregaSequencia( Integer proximos ) {

		try {

			if ( proximos == 0 ) {
				lcDet.carregaItem( 0 );
			}

			for ( int i = 0; i < proximos; i++ ) {
				lcDet.next( Types.INTEGER );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void carregaProdutoPadrao() {

		try {

			if ( txtCodProdPadrao.getVlrInteger() > 0 && ( ListaCampos.LCS_INSERT == lcCampos.getStatus() ) ) {
				txtCodProdCab.setVlrInteger( txtCodProdPadrao.getVlrInteger() );
				lcProdCab.carregaDados();
			}
			
		} 
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		// TODO Auto-generated method stub

	}

	public void beforePost( PostEvent pevt ) {

		super.beforePost( pevt );

		if ( pevt.getListaCampos() == lcCampos ) {
			if ( "".equals( txtStatus.getVlrString() ) ) {
				txtStatus.setVlrString( "PE" );
			}
		}
	}

	public void afterPost( PostEvent pevt ) {

		super.beforePost( pevt );

		if ( pevt.getListaCampos() == lcDet ) {
			lcCampos.carregaDados();
		}
		else if ( pevt.getListaCampos() == lcCampos ) {
			lcDet.carregaDados();
			carregaSequencia( 0 );
		}

	}

	public void beforeInsert( InsertEvent ievt ) {

		if(ievt.getListaCampos()==lcCampos) {
			txtPesoMedio.setVlrBigDecimal( new BigDecimal(0) );
			txtPesoRomaneio.setVlrBigDecimal( new BigDecimal(0) );
			txtPesoLiquido.setVlrBigDecimal( new BigDecimal(0) );
			txtQtdRomaneio.setVlrBigDecimal( new BigDecimal(0) );
		}

	}

	public void afterInsert( InsertEvent ievt ) {

		if ( ievt.getListaCampos() == lcCampos ) {
			carregaTipoRec();

			txtDtSaida.setVlrDate( new Date() );

		}

	}

	public void setTelaMae( FControleExpedicao tela_mae ) {

		this.tela_mae = tela_mae;
	}

	public void dispose() {

		super.dispose();

		if ( tela_mae != null ) {
			tela_mae.montaGrid();
		}

	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_F12 ) {
			btProcesso.doClick();
		}
		if ( kevt.getKeyCode() == KeyEvent.VK_F11 ) {
			liberaCampo( true );
		}

	}

	private void liberaPeso( boolean libera ) {

		tabPesagem.setColunaEditavel( 3, libera );
		tabPesagem.setColunaEditavel( 4, libera );

		atualizaPesoManual = libera;

	}

	private void liberaCampo( boolean libera ) {

		FPassword fpw = new FPassword( this, FPassword.LIBERA_CAMPO_PESAGEM, null, con );
		fpw.execShow();

		if ( fpw.OK ) {
			liberaPeso( libera );
		}

		fpw.dispose();

	}

}
