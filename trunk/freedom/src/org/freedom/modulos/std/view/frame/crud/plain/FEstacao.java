/**
 * @version 07/03/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FEstacao.java <BR>
 * 
 *                   Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                   modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                   na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                   Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                   sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                   Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                   Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                   Coment�rios sobre a classe...
 */

package org.freedom.modulos.std.view.frame.crud.plain;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import javax.comm.SerialPort;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.driver.scale.Bci10000;
import org.freedom.infra.driver.scale.EpmSP2400;
import org.freedom.infra.driver.scale.FilizolaBP15;
import org.freedom.infra.functions.SystemFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.Navegador;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.gms.business.object.TipoRecMerc;

public class FEstacao extends FDetalhe implements PostListener, ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private ListaCampos lcImp = new ListaCampos( this, "IP" );

	private ListaCampos lcPapel = new ListaCampos( this, "PP" );

	private JTextFieldPad txtCodEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtFonteTxt = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtTamFonteTxt = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JCheckBoxPad cbModoDemoEst = new JCheckBoxPad( "Demonstrativo?", "S", "N" );

	private JCheckBoxPad cbNfeEst = new JCheckBoxPad( "Emite NFE?", "S", "N" );

	private JTextFieldPad txtNroImp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodImp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodPapel = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtPortaWin = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtPortaLin = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescImp = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescPapel = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtPathCacerts = new JTextFieldPad( JTextFieldPad.TP_STRING, 256, 0);

	private JCheckBoxPad cbImpPad = new JCheckBoxPad( "Impressora padr�o?", "S", "N" );

	private JCheckBoxPad cbImpGrafica = new JCheckBoxPad( "Impress�o gr�fica?", "S", "N" );

	private Vector<String> vValTipoUsoImp = new Vector<String>();

	private Vector<String> vLabTipoUsoImp = new Vector<String>();

	private JRadioGroup<?, ?> rgTipoUsoImp = null;

	private JTabbedPanePad tpnGeral = new JTabbedPanePad();

	private JPanelPad panelImpressorasCampos = new JPanelPad( 500, 80 );

	private JPanelPad panelImpressoras = new JPanelPad( new GridLayout( 1, 1 ) );

	private JPanelPad panelBalancasCampos = new JPanelPad( 500, 80 );

	private JPanelPad panelBalancas = new JPanelPad( new GridLayout( 1, 1 ) );

	public JTablePad tabDetBal = new JTablePad();

	public JScrollPane spTabDetBal = new JScrollPane( tabDetBal );

	public Navegador navDetBal = new Navegador( true );

	private ListaCampos lcDetBal = new ListaCampos( this );

	private JTextFieldPad txtNroBal = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtParityBal = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 1, 0 );

	private Vector<String> vValDriversBal = new Vector<String>();

	private Vector<String> vLabDriversBal = new Vector<String>();

	private Vector<Integer> vValPortasBal = new Vector<Integer>();

	private Vector<String> vLabPortasBal = new Vector<String>();

	private Vector<Integer> vValSpeedBal = new Vector<Integer>();

	private Vector<String> vLabSpeedBal = new Vector<String>();

	private Vector<Integer> vValBitsBal = new Vector<Integer>();

	private Vector<String> vLabBitsBal = new Vector<String>();

	private Vector<Integer> vValStopBitsBal = new Vector<Integer>();

	private Vector<String> vLabStopBitsBal = new Vector<String>();

	private Vector<Integer> vValParidadeBal = new Vector<Integer>();

	private Vector<String> vLabParidadeBal = new Vector<String>();

	private JComboBoxPad cbDriverBal = new JComboBoxPad( vLabDriversBal, vValDriversBal, JComboBoxPad.TP_STRING, 50, 0 );

	private JComboBoxPad cbPortaBal = new JComboBoxPad( vLabPortasBal, vValPortasBal, JComboBoxPad.TP_INTEGER, 1, 0 );

	private JComboBoxPad cbSpeedBal = new JComboBoxPad( vLabSpeedBal, vValSpeedBal, JComboBoxPad.TP_INTEGER, 5, 0 );

	private JComboBoxPad cbParidadeBal = new JComboBoxPad( vLabParidadeBal, vValParidadeBal, JComboBoxPad.TP_INTEGER, 1, 0 );

	private JComboBoxPad cbBitsBal = new JComboBoxPad( vLabBitsBal, vValBitsBal, JComboBoxPad.TP_INTEGER, 1, 0 );

	private JComboBoxPad cbStopBitsBal = new JComboBoxPad( vLabStopBitsBal, vValStopBitsBal, JComboBoxPad.TP_INTEGER, 1, 0 );

	private JComboBoxPad cbTipoProcRecMerc = null;

	private Vector<String> vValsTipoProc = new Vector<String>();

	private Vector<String> vLabsTipoProc = new Vector<String>();
	
	private final JButtonPad btDirCacerts = new JButtonPad(Icone.novo("btAbrirPeq.png"));
	

	public FEstacao() {

		setTitulo( "Cadastro de esta��es de trabalho" );
		setAtribos( 50, 10, 550, 540 );

		pinCab = new JPanelPad( 530, 100 );

		montaValores();

		montaListacampos();

		lcDetBal.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcDetBal );
		lcDetBal.setTabela( tabDetBal );
		navDetBal.setName( "DetalheBalanca" );
		lcDetBal.setNavegador( navDetBal );

		montaTela();

		adicListeners();

		montaTabelas();

	}

	private void montaValores() {

		vValTipoUsoImp.addElement( "NF" );
		vValTipoUsoImp.addElement( "NS" );
		vValTipoUsoImp.addElement( "PD" );
		vValTipoUsoImp.addElement( "RS" );
		vValTipoUsoImp.addElement( "RG" );
		vValTipoUsoImp.addElement( "ET" );
		vValTipoUsoImp.addElement( "CH" );
		vValTipoUsoImp.addElement( "TO" );

		vLabTipoUsoImp.addElement( "Nota fiscal" );
		vLabTipoUsoImp.addElement( "Nota fiscal - servi�o" );
		vLabTipoUsoImp.addElement( "Pedido" );
		vLabTipoUsoImp.addElement( "Relat�rio simples" );
		vLabTipoUsoImp.addElement( "Relat�rio gr�fico" );
		vLabTipoUsoImp.addElement( "Etiquetas" );
		vLabTipoUsoImp.addElement( "Cheques" );
		vLabTipoUsoImp.addElement( "Todos" );

		vLabDriversBal.addElement( "<Selecione um modelo>" );
		vLabDriversBal.addElement( FilizolaBP15.NOME_BAL );
		vLabDriversBal.addElement( EpmSP2400.NOME_BAL );
		vLabDriversBal.addElement( Bci10000.NOME_BAL );

		vValDriversBal.addElement( "" );
		vValDriversBal.addElement( FilizolaBP15.class.toString().substring( 6 ) );
		vValDriversBal.addElement( EpmSP2400.class.toString().substring( 6 ) );
		vValDriversBal.addElement( Bci10000.class.toString().substring( 6 ) );

		vLabPortasBal.addElement( "<Selecione uma porta>" );

		if ( SystemFunctions.OS_LINUX == SystemFunctions.getOS() || SystemFunctions.OS_OSX == SystemFunctions.getOS()) {
			vLabPortasBal.addElement( "/dev/ttyS0" );
			vLabPortasBal.addElement( "/dev/ttyS1" );
			vLabPortasBal.addElement( "/dev/ttyS2" );
			vLabPortasBal.addElement( "/dev/ttyS3" );
			vLabPortasBal.addElement( "/dev/ttyUSB0" );
			vLabPortasBal.addElement( "/dev/ttyUSB1" );
		}
		else {
			vLabPortasBal.addElement( "COM1" );
			vLabPortasBal.addElement( "COM2" );
			vLabPortasBal.addElement( "COM3" );
			vLabPortasBal.addElement( "COM4" );			
			vLabPortasBal.addElement( "COM4" );		
		}

		vValPortasBal.addElement( -1 );
		vValPortasBal.addElement( 0 );
		vValPortasBal.addElement( 1 );
		vValPortasBal.addElement( 2 );
		vValPortasBal.addElement( 3 );
		
		if ( SystemFunctions.OS_LINUX == SystemFunctions.getOS() || SystemFunctions.OS_OSX == SystemFunctions.getOS()) {
			vValPortasBal.addElement( 20 );
			vValPortasBal.addElement( 21 );
		}
		

		vLabSpeedBal.addElement( "2400" );
		vLabSpeedBal.addElement( "4800" );
		vLabSpeedBal.addElement( "7200" );
		vLabSpeedBal.addElement( "9600" );
		vLabSpeedBal.addElement( "12000" );

		vValSpeedBal.addElement( 2400 );
		vValSpeedBal.addElement( 4800 );
		vValSpeedBal.addElement( 7200 );
		vValSpeedBal.addElement( 9600 );
		vValSpeedBal.addElement( 12000 );

		vLabParidadeBal.addElement( "Sem paridade" );
		vLabParidadeBal.addElement( "Par" );
		vLabParidadeBal.addElement( "Impar" );

		vValParidadeBal.addElement( SerialPort.PARITY_NONE );
		vValParidadeBal.addElement( SerialPort.PARITY_EVEN );
		vValParidadeBal.addElement( SerialPort.PARITY_ODD );

		vLabBitsBal.addElement( "5 bits" );
		vLabBitsBal.addElement( "6 bits" );
		vLabBitsBal.addElement( "7 bits" );
		vLabBitsBal.addElement( "8 bits" );

		vValBitsBal.addElement( SerialPort.DATABITS_5 );
		vValBitsBal.addElement( SerialPort.DATABITS_6 );
		vValBitsBal.addElement( SerialPort.DATABITS_7 );
		vValBitsBal.addElement( SerialPort.DATABITS_8 );

		vLabStopBitsBal.addElement( "1 bit" );
		vLabStopBitsBal.addElement( "1,5 bit" );
		vLabStopBitsBal.addElement( "2 bits" );

		vValStopBitsBal.addElement( SerialPort.STOPBITS_1 );
		vValStopBitsBal.addElement( SerialPort.STOPBITS_1_5 );
		vValStopBitsBal.addElement( SerialPort.STOPBITS_2 );

		vLabsTipoProc.addElement( "Todos" );
		vLabsTipoProc.addElement( TipoRecMerc.PROCESSO_PESAGEM_INICIAL.getName() );
		vLabsTipoProc.addElement( TipoRecMerc.PROCESSO_DESCARREGAMENTO.getName() );
		vLabsTipoProc.addElement( TipoRecMerc.PROCESSO_PESAGEM_FINAL.getName() );

		vValsTipoProc.addElement( "TO" );
		vValsTipoProc.addElement( (String) TipoRecMerc.PROCESSO_PESAGEM_INICIAL.getValue() );
		vValsTipoProc.addElement( (String) TipoRecMerc.PROCESSO_DESCARREGAMENTO.getValue() );
		vValsTipoProc.addElement( (String) TipoRecMerc.PROCESSO_PESAGEM_FINAL.getValue() );

		cbTipoProcRecMerc = new JComboBoxPad( vLabsTipoProc, vValsTipoProc, JComboBoxPad.TP_STRING, 2, 0 );

		rgTipoUsoImp = new JRadioGroup<String, String>( 3, 3, vLabTipoUsoImp, vValTipoUsoImp );

		cbDriverBal = new JComboBoxPad( vLabDriversBal, vValDriversBal, JComboBoxPad.TP_STRING, 50, 0 );

		cbPortaBal = new JComboBoxPad( vLabPortasBal, vValPortasBal, JComboBoxPad.TP_INTEGER, 1, 0 );

		cbSpeedBal = new JComboBoxPad( vLabSpeedBal, vValSpeedBal, JComboBoxPad.TP_INTEGER, 5, 0 );

		cbParidadeBal = new JComboBoxPad( vLabParidadeBal, vValParidadeBal, JComboBoxPad.TP_INTEGER, 1, 0 );

		cbBitsBal = new JComboBoxPad( vLabBitsBal, vValBitsBal, JComboBoxPad.TP_INTEGER, 1, 0 );

		cbStopBitsBal = new JComboBoxPad( vLabStopBitsBal, vValStopBitsBal, JComboBoxPad.TP_INTEGER, 1, 0 );

	}

	private void montaListacampos() {

		/********************
		 * IMPRESSORA *
		 ********************/
		lcImp.add( new GuardaCampo( txtCodImp, "CodImp", "C�d.imp.", ListaCampos.DB_PK, false ) );
		lcImp.add( new GuardaCampo( txtDescImp, "DescImp", "Descri��o da impressora", ListaCampos.DB_SI, false ) );
		lcImp.add( new GuardaCampo( txtCodPapel, "CodPapel", "C�d.papel", ListaCampos.DB_SI, false ) );
		lcImp.montaSql( false, "IMPRESSORA", "SG" );
		lcImp.setQueryCommit( false );
		lcImp.setReadOnly( true );
		txtCodImp.setTabelaExterna( lcImp, FImpressora.class.getCanonicalName() );

		/********************
		 * PAPEL *
		 ********************/
		lcPapel.add( new GuardaCampo( txtCodPapel, "CodPapel", "C�d.papel.", ListaCampos.DB_PK, false ) );
		lcPapel.add( new GuardaCampo( txtDescPapel, "DescPapel", "Descri��o do papel", ListaCampos.DB_SI, false ) );
		lcPapel.montaSql( false, "PAPEL", "SG" );
		lcPapel.setQueryCommit( false );
		lcPapel.setReadOnly( true );
		txtCodPapel.setTabelaExterna( lcPapel, FPapel.class.getCanonicalName() );

	}

	private void montaTabelas() {

		// Tabela de impressoras

		tab.setTamColuna( 50, 0 );
		tab.setTamColuna( 70, 1 );
		tab.setTamColuna( 230, 2 );
		tab.setTamColuna( 70, 3 );
		tab.setTamColuna( 230, 4 );

		// Tabela de Balan�as

		tabDetBal.setTamColuna( 50, 0 );
		tabDetBal.setTamColuna( 200, 1 );
		tabDetBal.setTamColuna( 50, 2 );
		tabDetBal.setTamColuna( 60, 3 );
		tabDetBal.setTamColuna( 60, 4 );
		tabDetBal.setTamColuna( 60, 5 );
		tabDetBal.setTamColuna( 60, 6 );
		tabDetBal.setTamColuna( 60, 7 );

	}

	private void montaTela() {

		/********************
		 * CABE�ALHO *
		 ********************/

		pinCab = new JPanelPad( 740, 130 );
		setListaCampos( lcCampos );
		setAltCab( 165 );
		setPainel( pinCab, pnCliCab );
		cbModoDemoEst.setVlrString( "N" );
		cbNfeEst.setVlrString( "N" );

		adicCampo( txtCodEst, 7, 20, 80, 20, "Codest", "N� esta��o", ListaCampos.DB_PK, true );
		adicCampo( txtDescEst, 90, 20, 257, 20, "Descest", "Descri��o da esta��o de trabalho", ListaCampos.DB_SI, true );
		adicDB( cbModoDemoEst, 350, 20, 170, 20, "ModoDemoEst", "Modo", true );
		adicCampo( txtFonteTxt, 7, 60, 270, 20, "FonteTxt", "Fonte para visualiza��o de relat�rios texto", ListaCampos.DB_SI, false );
		adicCampo( txtTamFonteTxt, 280, 60, 65, 20, "TamFonteTxt", "Tamanho", ListaCampos.DB_SI, false );
		adicDB( cbNfeEst, 350, 60, 150, 20, "NfeEst", "NFE", true );
		adicCampo( txtPathCacerts, 7, 100, 350, 20, "PathCacerts", "Caminho para arquivo de armazenamento de certificados", ListaCampos.DB_SI, false);
		adic(btDirCacerts, 360, 100, 20, 20);
		
		setListaCampos( true, "ESTACAO", "SG" );
		lcCampos.setQueryInsert( false );

		pnDet.add( tpnGeral );

		setAltDet( 250 );

		/*************************
		 * DETALHE IMPRESSORAS *
		 *************************/

		tpnGeral.addTab( "Impressoras", panelImpressoras );

		setPainel( panelImpressorasCampos );

		panelImpressoras.add( panelImpressorasCampos );

		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtNroImp, 7, 20, 80, 20, "NroImp", "N� imp.", ListaCampos.DB_PK, true );
		adicCampo( txtCodImp, 90, 20, 80, 20, "CodImp", "C�d.imp.", ListaCampos.DB_FK, txtDescImp, true );
		adicDescFK( txtDescImp, 173, 20, 300, 20, "DescImp", "Descri��o da impressora" );
		adicCampo( txtCodPapel, 7, 60, 80, 20, "CodPapel", "C�d.papel", ListaCampos.DB_FK, txtDescPapel, true );
		adicDescFK( txtDescPapel, 90, 60, 300, 20, "DescPapel", "Descri��o do papel" );
		adicCampo( txtPortaWin, 7, 100, 100, 20, "PortaWin", "Porta Windows", ListaCampos.DB_SI, true );
		adicCampo( txtPortaLin, 110, 100, 100, 20, "PortaLin", "Porta Linux", ListaCampos.DB_SI, true );
		adicDB( cbImpPad, 213, 100, 147, 20, "ImpPad", "Padr�o", true );
		adicDB( cbImpGrafica, 363, 100, 150, 20, "ImpGrafica", "Gr�fica", true );
		adicDB( rgTipoUsoImp, 7, 140, 500, 70, "TipoUsoImp", "Tipo uso", true );

		setListaCampos( true, "ESTACAOIMP", "SG" );

		lcDet.setQueryInsert( false );

		montaTab();

		/*************************
		 * DETALHE BALANCAS *
		 *************************/

		tpnGeral.addTab( "Balan�as", panelBalancas );

		panelBalancas.add( panelBalancasCampos );

		setListaCampos( lcDetBal );
		setPainel( panelBalancasCampos );
		setNavegador( navDetBal );

		adicCampo( txtNroBal, 7, 20, 60, 24, "NroBal", "Seq.Bal.", ListaCampos.DB_PK, true );
		adicDB( cbDriverBal, 70, 20, 200, 24, "DriverBal", "Driver", true );
		adicDB( cbPortaBal, 273, 20, 160, 24, "PortaBal", "Porta", true );
		adicDB( cbSpeedBal, 436, 20, 80, 24, "SpeedBal", "Velocidade", true );

		adicDB( cbParidadeBal, 7, 64, 115, 24, "ParityBal", "Paridade", true );
		adicDB( cbBitsBal, 125, 64, 71, 24, "BitsBal", "Bits", true );
		adicDB( cbStopBitsBal, 199, 64, 71, 24, "StopBitBal", "Stop Bits", true );
		adicDB( cbTipoProcRecMerc, 273, 64, 160, 24, "TipoProcRecMerc", "Padr�o para:", true );

		setListaCampos( true, "ESTACAOBAL", "SG" );

		lcDetBal.setQueryInsert( true );

		lcDetBal.montaTab();

	}

	private void adicListeners() {

		tpnGeral.addChangeListener( this );

		lcCampos.addPostListener( this );

		lcDet.addPostListener( this );
		
		btDirCacerts.addActionListener( this );

	}

	public void afterPost( PostEvent pevt ) {

		PreparedStatement ps = null;
		String sSQL = null;
		int iNroImp = 0;
		if ( ( pevt.getListaCampos() == lcDet ) && ( cbImpPad.getVlrString().equals( "S" ) ) ) {
			try {
				iNroImp = txtNroImp.getVlrInteger().intValue();
				sSQL = "EXECUTE PROCEDURE SGESTACAOIMPSP01(?,?,?,?,?)";
				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "SGESTACAOIMP" ) );
				ps.setInt( 3, txtCodEst.getVlrInteger().intValue() );
				ps.setInt( 4, txtNroImp.getVlrInteger().intValue() );
				ps.setString( 5, cbImpPad.getVlrString() );
				ps.execute();
				ps.close();
				con.commit();
				lcDet.carregaItens();
				txtNroImp.setVlrInteger( new Integer( iNroImp ) );
				lcDet.carregaDados();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ajustando impressora padr�o!\n" + err.getMessage(), true, con, err );
			} finally {
				ps = null;
				sSQL = null;
				iNroImp = 0;
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcImp.setConexao( cn );
		lcPapel.setConexao( cn );
		lcDetBal.setConexao( cn );

	}

	public void stateChanged( ChangeEvent e ) {

		if ( e.getSource() == tpnGeral ) {
			if ( tpnGeral.getSelectedIndex() == 0 ) { // Clique na aba de impressoras
				pnMaster.remove( spTabDetBal );
				pnMaster.add( spTab, BorderLayout.CENTER );
				pnRodape.remove( navDetBal );
				pnRodape.add( navRod, BorderLayout.WEST );
				setSize( getWidth(), getHeight() - 1 );
				setAltDet( 250 );
			}
			else if ( tpnGeral.getSelectedIndex() == 1 ) { // Clique na aba de balan�as
				pnMaster.remove( spTab );
				pnMaster.add( spTabDetBal, BorderLayout.CENTER );
				pnRodape.remove( navRod );
				pnRodape.add( navDetBal, BorderLayout.WEST );
				setSize( getWidth(), getHeight() + 1 );
				setAltDet( 140 );
			}
			setVisible( true );
		}
	}
	
	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btDirCacerts) {
			Thread th = new Thread(new Runnable() {
				public void run() {
					getDiretorio();
				}
			});
			th.start();
		}
	}
	
	private void getDiretorio() {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				txtPathCacerts.setVlrString(fileChooser.getSelectedFile().getPath());
		}
	}
}
