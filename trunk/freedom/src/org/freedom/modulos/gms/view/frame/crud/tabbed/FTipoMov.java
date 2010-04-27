/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda. Robson Sanchez e Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FTipoMov.java <BR>
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
 * 
 */

package org.freedom.modulos.gms.view.frame.crud.tabbed;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.Navegador;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FTabDados;
import org.freedom.modulos.gms.business.object.TipoMov;

public class FTipoMov extends FTabDados implements RadioGroupListener, CheckBoxListener, InsertListener, CarregaListener, JComboBoxListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pnGeral = new JPanelPad( 430, 460 );

	private JPanelPad pinRestricoes = new JPanelPad( 430, 460 );

	private JPanelPad pnRegrasVenda = new JPanelPad( "Regras para fechamento de venda", Color.BLUE );

	private JPanelPad pnRegrasGeral = new JPanelPad( "Regras para o tipo de movimento", Color.BLUE );

	private JPanelPad pnRegrasOutras = new JPanelPad( "Outras regras", Color.BLUE );

	private JPanelPad pinNavRestricoes = new JPanelPad( 680, 30 );

	private JPanelPad pinGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnRestricoes = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinDetRestricoes = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTablePad tbRestricoes = new JTablePad();

	private JScrollPane spnRestricoes = new JScrollPane( tbRestricoes );

	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTipoMov2 = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescTipoMov = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodModNota = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtCodTab = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtEspecieTipomov = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 9 );

	private JTextFieldPad txtIDUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescModNota = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldFK txtDescSerie = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTab = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescTipoMov2 = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtNomeUsu = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodRegraComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescRegraComis = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JRadioGroup<?, ?> rgESTipoMov = null;

	private JRadioGroup<?, ?> rgTipoFrete = null;

	private JComboBoxPad cbTipoMov = null;

	private JCheckBoxPad cbRestritoTipoMov = new JCheckBoxPad( "Permitir todos os usu�rios?", "S", "N" );

	private JCheckBoxPad cbFiscalTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad cbEstoqTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad cbSomaTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad chImpPedTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad cbImpNfTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad cbImpBolTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad cbImpRecTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad cbReImpNfTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad cbSeqNfTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad cbVlrMFinTipoMov = new JCheckBoxPad( "Permitir valor das parcelas menor que o total?", "S", "N" );

	private JCheckBoxPad cbMComisTipoMov = new JCheckBoxPad( "M�ltiplos comissionados ?", "S", "N" );

	private JCheckBoxPad cbEmitNFCPMov = new JCheckBoxPad( "Emite nota de compra ?", "S", "N" );

	private Navegador navRestricoes = new Navegador( true );

	private ListaCampos lcRestricoes = new ListaCampos( this, "" );

	private ListaCampos lcUsu = new ListaCampos( this, "US" );

	private ListaCampos lcModNota = new ListaCampos( this, "MN" );

	private ListaCampos lcSerie = new ListaCampos( this, "SE" );

	private ListaCampos lcTab = new ListaCampos( this, "TB" );

	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );

	private ListaCampos lcTran = new ListaCampos( this, "TN" );

	private ListaCampos lcRegraComis = new ListaCampos( this, "RC" );

	private boolean[] bPrefs = null;

	private JLabelPad separador1 = new JLabelPad();

	public FTipoMov() {

		super();
		setTitulo( "Cadastro de Tipos de Movimento" );
		setAtribos( 50, 40, 720, 490 );

		lcRestricoes.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcRestricoes );
		lcRestricoes.setTabela( tbRestricoes );

		lcModNota.add( new GuardaCampo( txtCodModNota, "CodModNota", "C�d.mod.nota", ListaCampos.DB_PK, false ) );
		lcModNota.add( new GuardaCampo( txtDescModNota, "DescModNota", "Descri��o do modelo de nota", ListaCampos.DB_SI, false ) );
		lcModNota.montaSql( false, "MODNOTA", "LF" );
		lcModNota.setQueryCommit( false );
		lcModNota.setReadOnly( true );
		txtCodModNota.setTabelaExterna( lcModNota );

		lcSerie.add( new GuardaCampo( txtCodSerie, "Serie", "C�d.serie", ListaCampos.DB_PK, false ) );
		lcSerie.add( new GuardaCampo( txtDescSerie, "DocSerie", "N�. doc", ListaCampos.DB_SI, false ) );
		lcSerie.montaSql( false, "SERIE", "LF" );
		lcSerie.setQueryCommit( false );
		lcSerie.setReadOnly( true );
		txtCodSerie.setTabelaExterna( lcSerie );

		lcTab.add( new GuardaCampo( txtCodTab, "CodTab", "C�d.tb.pc.", ListaCampos.DB_PK, false ) );
		lcTab.add( new GuardaCampo( txtDescTab, "DescTab", "Descri��o da tabela de pre�o", ListaCampos.DB_SI, false ) );
		lcTab.montaSql( false, "TABPRECO", "VD" );
		lcTab.setQueryCommit( false );
		lcTab.setReadOnly( true );
		txtCodTab.setTabelaExterna( lcTab );

		lcTipoMov.add( new GuardaCampo( txtCodTipoMov2, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov2, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov2.setTabelaExterna( lcTipoMov );

		lcUsu.add( new GuardaCampo( txtIDUsu, "IDUsu", "ID", ListaCampos.DB_PK, txtNomeUsu, false ) );
		lcUsu.add( new GuardaCampo( txtNomeUsu, "NomeUsu", "Nome nome do usu�rio", ListaCampos.DB_SI, false ) );
		lcUsu.montaSql( false, "USUARIO", "SG" );
		lcUsu.setQueryCommit( false );
		lcUsu.setReadOnly( true );
		txtIDUsu.setFK( true );
		txtIDUsu.setNomeCampo( "IDUsu" );
		txtIDUsu.setTabelaExterna( lcUsu );

		lcRegraComis.add( new GuardaCampo( txtCodRegraComis, "CodRegrComis", "C�d.rg.comis.", ListaCampos.DB_PK, txtDescRegraComis, false ) );
		lcRegraComis.add( new GuardaCampo( txtDescRegraComis, "DescRegrComis", "Descri��o da regra do comissionado", ListaCampos.DB_SI, false ) );
		lcRegraComis.montaSql( false, "REGRACOMIS", "VD" );
		lcRegraComis.setQueryCommit( false );
		lcRegraComis.setReadOnly( true );
		txtCodRegraComis.setFK( true );
		txtCodRegraComis.setNomeCampo( "IDUsu" );
		txtCodRegraComis.setTabelaExterna( lcRegraComis );

		lcTran.add( new GuardaCampo( txtCodTran, "CodTran", "C�d.tran.", ListaCampos.DB_PK, false ) );
		lcTran.add( new GuardaCampo( txtDescTran, "RazTran", "Descri��o da transportadora", ListaCampos.DB_SI, false ) );
		lcTran.montaSql( false, "TRANSP", "VD" );
		lcTran.setQueryCommit( false );
		lcTran.setReadOnly( true );
		txtCodTran.setTabelaExterna( lcTran );

		cbTipoMov = new JComboBoxPad( null, null, JComboBoxPad.TP_STRING, 2, 0 );
		cbTipoMov.addComboBoxListener( this );

		Vector<String> vValsES = new Vector<String>();
		Vector<String> vLabsES = new Vector<String>();

		vLabsES.addElement( TipoMov.ENTRADA.getName() );
		vLabsES.addElement( TipoMov.SAIDA.getName() );
		vLabsES.addElement( TipoMov.INVENTARIO.getName() );
		
		vValsES.addElement( (String) TipoMov.ENTRADA.getValue() );
		vValsES.addElement( (String) TipoMov.SAIDA.getValue() );
		vValsES.addElement( (String) TipoMov.INVENTARIO.getValue() );
		
		rgESTipoMov = new JRadioGroup<String, String>( 1, 3, vLabsES, vValsES );
		rgESTipoMov.addRadioGroupListener( this );

		Vector<String> vValsTF = new Vector<String>();
		Vector<String> vLabsTF = new Vector<String>();
		vLabsTF.addElement( "CIF" );
		vLabsTF.addElement( "FOB" );
		vValsTF.addElement( "C" );
		vValsTF.addElement( "F" );
		rgTipoFrete = new JRadioGroup<String, String>( 2, 1, vLabsTF, vValsTF );
		rgTipoFrete.setAtivo( false );

		montaCbTipoMov( "E" );

		txtCodTipoMov2.setNomeCampo( "CodTipoMov" );

		pinGeral.setPreferredSize( new Dimension( 430, 560 ) );
		pinGeral.add( pnGeral, BorderLayout.CENTER );

		setPainel( pnGeral );

		adicTab( "Geral", pnGeral );

		adicCampo( txtCodTipoMov, 7, 20, 80, 20, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, true );
		adicCampo( txtDescTipoMov, 90, 20, 250, 20, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, true );

		adicCampo( txtCodSerie, 7, 105, 80, 20, "Serie", "S�rie", ListaCampos.DB_FK, txtDescSerie, true );
		adicDescFK( txtDescSerie, 90, 105, 250, 20, "DocSerie", "Documento atual" );
		adicCampo( txtCodModNota, 7, 145, 80, 20, "CodModNota", "C�d.mod.nota", ListaCampos.DB_FK, true );
		adicDescFK( txtDescModNota, 90, 145, 250, 20, "DescModNota", "Descri��o do modelo de nota" );
		adicCampo( txtCodTipoMov2, 7, 185, 80, 20, "CodTipoMovTM", "C�d.mov.nf.", ListaCampos.DB_FK, txtDescTipoMov2, false );
		adicDescFK( txtDescTipoMov2, 90, 185, 250, 20, "DescTipoMov", "Descri��o do movimento para nota." );
		adicCampo( txtCodTab, 7, 225, 80, 20, "CodTab", "C�d.tp.pc.", ListaCampos.DB_FK, txtDescTab, false );
		adicDescFK( txtDescTab, 90, 225, 250, 20, "DescTab", "Descri��o da tab. de pre�os" );
		adicCampo( txtCodRegraComis, 7, 265, 80, 20, "CodRegrComis", "C�d.rg.comis.", ListaCampos.DB_FK, false );
		adicDescFK( txtDescRegraComis, 90, 265, 250, 20, "DescRegrComis", "Descri��o da regra de comissionado" );
		adicCampo( txtCodTran, 7, 305, 80, 20, "CodTran", "C�d.tran.", ListaCampos.DB_FK, false );
		adicDescFK( txtDescTran, 90, 305, 250, 20, "DescTran", "Descri��o da transportadora" ); 

		separador1.setBorder( BorderFactory.createEtchedBorder() );
		adic( separador1, 350, 4, 2, 380 );

		adicDB( rgESTipoMov, 360, 20, 330, 30, "ESTipoMov", "Fluxo", true );

		adicCampo( txtEspecieTipomov, 7, 60, 80, 24, "EspecieTipomov", "Esp�cie", ListaCampos.DB_SI, true );
		adicDB( cbTipoMov, 90, 60, 250, 24, "TipoMov", "Tipo de movimento", true );
		
		adic( pnRegrasGeral, 360, 64, 250, 70 );

		setPainel( pnRegrasGeral );

		adicDB( cbFiscalTipoMov, 5, 20, 60, 20, "FiscalTipoMov", "Fiscal", true );
		adicDB( cbEstoqTipoMov, 85, 20, 60, 20, "EstoqTipoMov", "Estoque", true );
		adicDB( cbSomaTipoMov, 165, 20, 70, 20, "SomaVdTipoMov", "Financeiro", true );

		setPainel( pnGeral );

		adicDB( rgTipoFrete, 613, 72, 76, 61, "CTipoFrete", "Tipo de frete", false );

		adic( pnRegrasVenda, 360, 145, 330, 115 );

		setPainel( pnRegrasVenda );

		adicDB( chImpPedTipoMov, 5, 20, 105, 20, "ImpPedTipoMov", "Imprimir pedido", true );
		adicDB( cbImpNfTipoMov, 125, 20, 85, 20, "ImpNfTipoMov", "Imprimir NF", true );
		adicDB( cbImpBolTipoMov, 220, 20, 105, 20, "ImpBolTipoMov", "Imprimir boleto", true );

		adicDB( cbImpRecTipoMov, 5, 65, 105, 20, "ImpRecTipoMov", "Imprimir recibo", true );
		adicDB( cbReImpNfTipoMov, 125, 65, 85, 20, "ReImpNfTipoMov", "Reimprimir NF", true );
		adicDB( cbSeqNfTipoMov, 220, 65, 105, 20, "SeqNfTipoMov", "Aloca Nro. NF", true );

		txtCodRegraComis.setAtivo( false );

		cbEmitNFCPMov.setEnabled( false );

		setPainel( pnGeral );

		adic( pnRegrasOutras, 360, 268, 330, 110 );
		setPainel( pnRegrasOutras );

		adicDB( cbVlrMFinTipoMov, 5, 0, 300, 20, "VlrMFinTipoMov", "", true );
		adicDB( cbRestritoTipoMov, 5, 20, 240, 20, "TUSUTIPOMOV", "", true );
		adicDB( cbMComisTipoMov, 5, 40, 240, 20, "MComisTipoMov", "", true );
		adicDB( cbEmitNFCPMov, 5, 60, 240, 20, "EmitNfCpMov", "", true );

		cbRestritoTipoMov.addCheckBoxListener( this );
		cbMComisTipoMov.addCheckBoxListener( this );

		setListaCampos( true, "TIPOMOV", "EQ" );
		lcCampos.setQueryInsert( false );

		setPainel( pinDetRestricoes, pnRestricoes );

		pinDetRestricoes.setPreferredSize( new Dimension( 430, 80 ) );
		pinDetRestricoes.add( pinNavRestricoes, BorderLayout.SOUTH );
		pinDetRestricoes.add( pinRestricoes, BorderLayout.CENTER );

		setListaCampos( lcRestricoes );
		setNavegador( navRestricoes );

		pnRestricoes.add( pinDetRestricoes, BorderLayout.SOUTH );
		pnRestricoes.add( spnRestricoes, BorderLayout.CENTER );
		pinNavRestricoes.adic( navRestricoes, 0, 0, 270, 25 );

		setPainel( pinRestricoes );

		adicCampo( txtIDUsu, 7, 20, 80, 20, "IdUsu", "Id", ListaCampos.DB_PF, txtNomeUsu, true );
		adicDescFK( txtNomeUsu, 90, 20, 250, 20, "NomeUsu", " e nome do usu�rio" );

		setListaCampos( true, "TIPOMOVUSU", "EQ" );
		lcRestricoes.setQueryInsert( false );
		lcRestricoes.setQueryCommit( false );
		lcRestricoes.montaTab();

		txtCodTipoMov.setTabelaExterna( lcRestricoes );

		tbRestricoes.setTamColuna( 80, 0 );
		tbRestricoes.setTamColuna( 280, 1 );
		lcCampos.addInsertListener( this );

	}

	public void beforeInsert( InsertEvent ievt ) {

	}

	public void afterInsert( InsertEvent ievt ) {

		if ( !bPrefs[ 0 ] ) {
			cbEstoqTipoMov.setVlrString( "N" );
		}
		if ( bPrefs[ 1 ] ) {
			cbMComisTipoMov.setVlrString( "S" );
		}
		else {
			cbMComisTipoMov.setVlrString( "N" );
		}

	}

	public void beforePost( PostEvent pevt ) {

		if ( txtCodRegraComis.getAtivo() && txtCodRegraComis.getVlrInteger() == 0 ) {
			Funcoes.mensagemErro( this, "Campo C�d.rg.comis. � requerido!" );
			pevt.cancela();
		}

		super.beforePost( pevt );
	}

	private void montaCbTipoMov( String ES ) {

		cbTipoMov.limpa();
		
		cbTipoMov.setItens( TipoMov.getLabels( ES ), TipoMov.getValores( ES ) );
		
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcTipoMov.setConexao( cn );
		lcModNota.setConexao( cn );
		lcSerie.setConexao( cn );
		lcTab.setConexao( cn );
		lcRestricoes.setConexao( cn );
		lcUsu.setConexao( cn );
		lcRegraComis.setConexao( cn );
		lcTran.setConexao( cn );
		bPrefs = prefs();
		cbEstoqTipoMov.setEnabled( bPrefs[ 0 ] ); // Habilita controle de estoque de acordo com o prefer�ncias
	}

	public void valorAlterado( RadioGroupEvent evt ) {

		if ( rgESTipoMov.getVlrString().equals( "S" ) ) {
			rgTipoFrete.setAtivo( true );
		}
		else {
			rgTipoFrete.setAtivo( false );
		}
		
		montaCbTipoMov( rgESTipoMov.getVlrString() );

	}
	
	public void valorAlterado( JComboBoxEvent evt ) {
		
		if ( evt.getComboBoxPad() == cbTipoMov ) {
			if("CP".equals(cbTipoMov.getVlrString()) || "PC".equals(cbTipoMov.getVlrString())) {
				cbEmitNFCPMov.setEnabled( true );
			}
			else {
				cbEmitNFCPMov.setVlrString( "N" );
				cbEmitNFCPMov.setEnabled( false );				
			}			
		}
		
	}


	public void valorAlterado( CheckBoxEvent evt ) {

		if ( evt.getCheckBox() == cbRestritoTipoMov ) {
			if ( evt.getCheckBox().isSelected() ) {
				removeTab( "Restri��es de Usu�rio", pnRestricoes );
			}
			else {
				adicTab( "Restri��es de Usu�rio", pnRestricoes );
			}
		}
		else if ( evt.getCheckBox() == cbMComisTipoMov ) {

			if ( bPrefs[ 1 ] ) {
				cbMComisTipoMov.setEnabled( true );
				txtCodRegraComis.setRequerido( evt.getCheckBox().isSelected() );
				txtCodRegraComis.setAtivo( evt.getCheckBox().isSelected() );
				if ( !evt.getCheckBox().isSelected() ) {
					txtCodRegraComis.setVlrInteger( 0 );
					lcRegraComis.carregaDados();
				}
			}
			else {
				cbMComisTipoMov.setSelected( false );
				cbMComisTipoMov.setEnabled( false );
			}
		}		

	}

	private boolean[] prefs() {

		boolean[] bRetorno = new boolean[ 2 ];
		String sSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sSQL = "SELECT CONTESTOQ, MULTICOMIS FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			bRetorno[ 0 ] = true;
			if ( rs.next() ) {
				bRetorno[ 0 ] = true;
				if ( "S".equals( rs.getString( "CONTESTOQ" ) ) ) {
					bRetorno[ 0 ] = true;
				}
				else {
					bRetorno[ 0 ] = false;
				}
				if ( "S".equals( rs.getString( "MULTICOMIS" ) ) ) {
					bRetorno[ 1 ] = true;
				}
				else {
					bRetorno[ 1 ] = false;
				}
			}
			rs.close();
			ps.close();
			con.commit();
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} 
		finally {
			rs = null;
			ps = null;
		}
		return bRetorno;
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if(cevt.getListaCampos()==lcCampos) {
					
	  		if("CP".equals(cbTipoMov.getVlrString()) || "PC".equals(cbTipoMov.getVlrString())) {
				cbEmitNFCPMov.setEnabled( true );
			}
			else {
				cbEmitNFCPMov.setVlrString( "N" );
				cbEmitNFCPMov.setEnabled( false );				
			}
			
		}
		
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		// TODO Auto-generated method stub

	}

}
