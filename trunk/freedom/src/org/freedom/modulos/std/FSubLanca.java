/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FSubLanca.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.FuncoesCRM;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FSubLanca extends FDetalhe implements RadioGroupListener, FocusListener, EditListener, PostListener, DeleteListener, ActionListener, CarregaListener, JComboBoxListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad( 500, 200 );

	private JPanelPad pinDet = new JPanelPad( 500, 100 );

	private JTextFieldPad txtCodLanca = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDataLanca = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDocLanca = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private JTextFieldPad txtCodPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodEmpPlan = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodFilialPlan = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtHistLanca = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private JTextFieldPad txtHistSubLanca = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private JTextFieldPad txtCodPlanSub = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescPlan = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldFK txtSiglaCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtVlrLanca = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtCodSubLanca = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtVlrAtualLanca = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldPad txtCodContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
//	private JTextFieldPad txtCodContr2 = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodItContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextAreaPad txaHistLanca = new JTextAreaPad();

	private JCheckBoxPad cbTransf = new JCheckBoxPad( "Sim!", "S", "N" );

	private JScrollPane spnTxa = new JScrollPane( txaHistLanca );

	private JButton btSalvar = new JButton( Icone.novo( "btSalvar.gif" ) );

	private JButton btNovo = new JButton( Icone.novo( "btNovo.gif" ) );

	private ListaCampos lcPlan = new ListaCampos( this, "PN" );

	private ListaCampos lcCC = new ListaCampos( this, "CC" );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private ListaCampos lcFor = new ListaCampos( this, "FR" );
	
	private ListaCampos lcContrato = new ListaCampos( this, "CT" );
	
	private ListaCampos lcItContrato = new ListaCampos( this, "CT" );

	private String sCodLanca = "";

	private String sCodPlan = "";

	private Date dIni = null;

	private Date dFim = null;

	private JRadioGroup<?, ?> rgTipoLanca;

	private JLabelPad lbRazCli = null;

	private JLabelPad lbRazFor = null;

	private JLabelPad lbCodCli = null;
	
	private JLabelPad lbCodFor = null;
	
	private HashMap<String, Object> prefere = null;
	
	private JPanelPad pnTipoLanca = new JPanelPad();
	
	private Vector<String> vValsTipo = null;
	
	private Vector<String> vLabsTipo = null;
	
	private Vector<Integer> vValsContr = new Vector<Integer>();

	private Vector<String> vLabsContr = new Vector<String>();

	private JComboBoxPad cbContr = new JComboBoxPad( vLabsContr, vValsContr, JComboBoxPad.TP_INTEGER, 8, 0 );
	
	private Vector<Integer> vValsitContr = new Vector<Integer>();

	private Vector<String> vLabsitContr = new Vector<String>();

	private JComboBoxPad cbitContr = new JComboBoxPad( vLabsitContr, vValsitContr, JComboBoxPad.TP_INTEGER, 8, 0 );

	
	public FSubLanca( String sCodL, String sCodP, Date dini, Date dfim ) {

		lcItContrato.setMaster( lcCampos );
		dIni = dini;
		dFim = dfim;

		sCodLanca = sCodL;
		sCodPlan = sCodP;

		pnCab.remove( 1 ); // Remove o navegador do cabe�alho

		setTitulo( "Sub-Lan�amentos" );
		setAtribos( 20, 1, 655, 450 );
		
		prefere = getPrefere(Aplicativo.getInstace().getConexao());
						
		txtVlrAtualLanca.setAtivo( false );				

		txtCodPlan.setTipo( JTextFieldPad.TP_STRING, 13, 0 );
		lcPlan.add( new GuardaCampo( txtCodPlanSub, "CodPlan", "C�d.planejamento", ListaCampos.DB_PK, txtDescPlan, false ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descri��o do planejamento", ListaCampos.DB_SI, false ) );
		lcPlan.setReadOnly( true );
		lcPlan.setQueryCommit( false );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		txtCodPlanSub.setTabelaExterna( lcPlan );
		txtDescPlan.setListaCampos( lcPlan );

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli", ListaCampos.DB_PK, txtRazCli, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.setReadOnly( true );
		lcCli.setQueryCommit( false );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		txtCodCli.setTabelaExterna( lcCli );
		txtRazCli.setListaCampos( lcCli );

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, txtDescPlan, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.setReadOnly( true );
		lcFor.setQueryCommit( false );
		lcFor.montaSql( false, "FORNECED", "CP" );
		txtCodFor.setTabelaExterna( lcFor );
		txtRazFor.setListaCampos( lcFor );

		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "C�d.c.c.", ListaCampos.DB_PK, txtDescCC, false ) );
		lcCC.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano-Base", ListaCampos.DB_PK, txtDescCC, false ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descri��o do centro de custo", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla", ListaCampos.DB_SI, false ) );
		lcCC.setReadOnly( true );
		lcCC.setQueryCommit( false );
		lcCC.setWhereAdic( "NIVELCC=10" );
		lcCC.montaSql( false, "CC", "FN" );
		txtCodCC.setTabelaExterna( lcCC );
		txtAnoCC.setTabelaExterna( lcCC );
		txtDescCC.setListaCampos( lcCC );
		txtSiglaCC.setListaCampos( lcCC );

		lcContrato.add( new GuardaCampo( txtCodContr, "CodContr", "C�d.Contr.", ListaCampos.DB_PK, false ) );
		lcContrato.setReadOnly( true );
		lcContrato.setQueryCommit( false );
		lcContrato.montaSql( false, "CONTRATO", "VD" );
		txtCodContr.setTabelaExterna( lcContrato );

		lcItContrato.add( new GuardaCampo( txtCodContr, "CodContr", "C�d.Contr.", ListaCampos.DB_PK, false ) );
		lcItContrato.add( new GuardaCampo( txtCodItContr, "CodItContr", "C�d.It.Contr.", ListaCampos.DB_PK, false ) );		
		lcItContrato.setReadOnly( true );
		lcItContrato.setQueryCommit( false );
		lcItContrato.montaSql( false, "ITCONTRATO", "VD" );
		txtCodItContr.setTabelaExterna( lcItContrato );
		
		setAltCab( 190 );
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );
		adicCampo( txtCodLanca, 7, 20, 80, 20, "CodLanca", "N� L�to.", ListaCampos.DB_PK, true );
		adicCampoInvisivel( txtCodPlan, "CodPlan", "Cod. Planejamento", ListaCampos.DB_SI, true );
		adicCampoInvisivel( txtCodEmpPlan, "CodEmpPN", "Emp. Planejamento", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodFilialPlan, "CodFilialPN", "Filial. Planejamento", ListaCampos.DB_SI, false );
		adicCampo( txtDataLanca, 90, 20, 97, 20, "DataLanca", "Data", ListaCampos.DB_SI, true );
		adicCampo( txtDocLanca, 190, 20, 77, 20, "DocLanca", "Doc.", ListaCampos.DB_SI, false );
		adicCampo( txtHistLanca, 270, 20, 355, 20, "HistBLanca", "Hist�rio Banc�rio", ListaCampos.DB_SI, true );
		adicDB( cbTransf, 7, 60, 80, 20, "TransfLanca", "Transfer�ncia", true );
		adic( new JLabelPad( "Vlr. Lan�amento" ), 95, 40, 100, 20 );
		adic( txtVlrAtualLanca, 95, 60, 97, 20 );		

		JPanelPad pnTxa = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
		pnTxa.add( spnTxa );
		adic( pnTxa, 7, 95, 618, 50 );
		adicDBLiv( txaHistLanca, "HistLanca", "Hist�rico descriminado", false );
		adicDB( rgTipoLanca, 210, 60, 414, 28, "tipolanca", "Tipo de lan�amento", true );
		
		adic( btNovo, 7, 150, 30, 30 );
		adic( btSalvar, 37, 150, 30, 30 );
		
		setListaCampos( true, "LANCA", "FN" );
		setAltDet( 100 );
		setPainel( pinDet, pnDet );
		
		setListaCampos( lcDet );
		setNavegador( navRod );
		adicCampoInvisivel( txtCodSubLanca, "CodSubLanca", "Item", ListaCampos.DB_PK, false );
		adicCampo( txtCodPlanSub, 7, 20, 100, 20, "CodPlan", "C�d.Planej.", ListaCampos.DB_FK, txtDescPlan, true );
		adicDescFK( txtDescPlan, 110, 20, 207, 20, "DescPlan", "Descri��o do plano de contas" );

		adicCampo( txtCodCC, 320, 20, 100, 20, "CodCC", "C�d.c.c.", ListaCampos.DB_FK, txtDescCC, false );
		adicCampoInvisivel( txtAnoCC, "AnoCC", "Ano-base", ListaCampos.DB_FK, txtDescCC, false );
		adicDescFK( txtDescCC, 423, 20, 202, 20, "DescCC", "Descri��o do centro de custo" );
		adicCampo( txtVlrLanca, 7, 60, 100, 20, "VlrSubLanca", " Valor", ListaCampos.DB_SI, true );
		adicCampo( txtHistSubLanca, 110, 60, 516, 20, "HistSubLanca", "Hist�rico do Lancamento", ListaCampos.DB_SI, false );

		
		txtCodCli.setRequerido( true );
		txtCodFor.setRequerido( true );
		txtCodCli.setVisible( false );
		txtRazCli.setVisible( false );
		txtCodFor.setVisible( false );
		txtRazFor.setVisible( false );

		adic( pnTipoLanca, 7, 85, 618, 55 );
		pnTipoLanca.setVisible( false );
				
		setPainel( pnTipoLanca );
		
		lbCodCli = adicCampo( txtCodCli, 2, 18, 95, 20, "CodCli", "C�d. Cliente", ListaCampos.DB_FK, false );
		lbRazCli = adicDescFK( txtRazCli, 100, 18, 503, 20, "RazCli", "Raz�o social do cliente" );

		lbCodFor = adicCampo( txtCodFor, 2, 18, 95, 20, "CodFor", "C�d. Fornecedor", ListaCampos.DB_FK, false );
		lbRazFor = adicDescFK( txtRazFor, 100, 18, 503, 20, "RazFor", "Raz�o social do fornecedor" );

		if("S".equals( (String) prefere.get( "LANCAFINCONTR" ))) {		
			adicCampoInvisivel( txtCodContr, "CodContr", "Cod.Contr.", ListaCampos.DB_FK, false );
//			adicCampoInvisivel( txtCodContr2, "CodContr", "Cod.Contr.", ListaCampos.DB_FK, false );
			adicCampoInvisivel( txtCodItContr, "CodItContr", "Cod.It.Contr.", ListaCampos.DB_SI, false );
		}
		
		lbCodCli.setVisible( false );
		lbRazCli.setVisible( false );
		lbCodFor.setVisible( false );
		lbRazFor.setVisible( false );
		
		adic( new JLabelPad( "Contrato/Projeto" ), 3, 40, 284, 20 );
		adic( cbContr, 3, 60, 284, 20 );
		adic( new JLabelPad( "Item" ), 294, 40, 320, 20 );
		adic( cbitContr, 294, 60, 310, 20 );

		lcDet.setWhereAdic( "CODSUBLANCA > 0" );
		setListaCampos( true, "SUBLANCA", "FN" );
		montaTab();

		tab.setTamColuna( 30, 0 );
		tab.setTamColuna( 95, 1 );
		tab.setTamColuna( 200, 2 );
		tab.setTamColuna( 0, 3 );
		tab.setTamColuna( 0, 4 );
		tab.setTamColuna( 0, 5 );
		tab.setTamColuna( 200, 7 );

		txtCodPlanSub.addFocusListener( this );
		lcCampos.addPostListener( this );
		lcCampos.addEditListener( this );
		lcDet.addPostListener( this );
		lcDet.addDeleteListener( this );
		btSalvar.addActionListener( this );
		btNovo.addActionListener( this );
		lcCli.addCarregaListener( this );
		lcDet.addCarregaListener( this );				
		cbContr.addComboBoxListener( this );
		cbitContr.addComboBoxListener( this );
		rgTipoLanca.addRadioGroupListener( this );
		
	}

	private void atualizaSaldo() {

		try {
			PreparedStatement ps = con.prepareStatement( "SELECT VLRLANCA FROM FNLANCA WHERE CODEMP=? AND CODFILIAL=? AND CODLANCA=?" );
			ps.setInt( 1, Aplicativo.iCodEmp);
			ps.setInt( 2, ListaCampos.getMasterFilial("FNLANCA"));			
			ps.setInt( 3, txtCodLanca.getVlrInteger().intValue() );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() )
				txtVlrAtualLanca.setVlrBigDecimal( new BigDecimal( rs.getString( "VlrLanca" ) ) );
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao atualizar o saldo!\n" + err.getMessage(), true, con, err );
		}
	}

	public void valorAlterado( RadioGroupEvent rgevt ) {

		if ( rgTipoLanca.getVlrString().compareTo( "A" ) == 0 ) {			
			setAtribos( this.getX(), this.getY(), 655, 450 );
			setAltDet( 100 );
			
			txtCodCli.setVisible( false );
			txtRazCli.setVisible( false );
			txtCodFor.setVisible( false );
			txtRazFor.setVisible( false );

			lbCodCli.setVisible( false );
			lbRazCli.setVisible( false );
			lbCodFor.setVisible( false );
			lbRazFor.setVisible( false );

			pnTipoLanca.setVisible( false );

		}
		else if ( rgTipoLanca.getVlrString().compareTo( "F" ) == 0 ) {
			setAtribos( this.getX(), this.getY(), 655, 520 );
			setAltDet( 160 );
			pnTipoLanca.setBorder( BorderFactory.createTitledBorder( "" ) );
			pnTipoLanca.setSize( pnTipoLanca.getWidth(), 55 );
			
			pnTipoLanca.setVisible( true );			
			txtCodCli.setVisible( false );
			txtRazCli.setVisible( false );
			txtCodFor.setVisible( true );
			txtRazFor.setVisible( true );

			lbCodCli.setVisible( false );
			lbRazCli.setVisible( false );
			lbCodFor.setVisible( true );
			lbRazFor.setVisible( true );
		}
		else if ( rgTipoLanca.getVlrString().compareTo( "C" ) == 0 ) {
			setAtribos( this.getX(), this.getY(), 655, 520 );
			setAltDet( 160 );
			pnTipoLanca.setBorder( BorderFactory.createTitledBorder( "" ) );
			pnTipoLanca.setSize( pnTipoLanca.getWidth(), 55 );
			
			pnTipoLanca.setVisible( true );
			txtCodCli.setVisible( true );
			txtRazCli.setVisible( true );
			txtCodFor.setVisible( false );
			txtRazFor.setVisible( false );
			
			lbCodCli.setVisible( true );
			lbRazCli.setVisible( true );
			lbCodFor.setVisible( false );
			lbRazFor.setVisible( false );			
		}
		else if ( rgTipoLanca.getVlrString().compareTo( "O" ) == 0 ) {
			setAtribos( this.getX(), this.getY(), 655, 540 );
			setAltDet( 200 );
			pnTipoLanca.setBorder( BorderFactory.createTitledBorder( "" ) );			
			pnTipoLanca.setSize( pnTipoLanca.getWidth(), 55 + 40 );		
			pnTipoLanca.setVisible( true );
			
			txtCodCli.setVisible( true );
			txtRazCli.setVisible( true );
			
			txtCodFor.setVisible( false );
			txtRazFor.setVisible( false );
			
			lbCodCli.setVisible( true );
			lbRazCli.setVisible( true );
			
			lbCodFor.setVisible( false );
			lbRazFor.setVisible( false );
		}		

	}

	private void carregar() {

		if ( sCodLanca == null ) {
			lcCampos.insert( true );
			txtCodPlan.setText( sCodPlan );
			txtCodEmpPlan.setVlrString( "" + Aplicativo.iCodEmp );
			txtCodFilialPlan.setVlrString( "" + ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
			txtVlrAtualLanca.setVlrBigDecimal( new BigDecimal( 0 ) );
			cbTransf.setVlrString( "N" );
			cbTransf.setEnabled( true );
			txtDataLanca.setVlrDate( dFim );
			txtDataLanca.requestFocus();
		}
		else {
			txtCodLanca.setText( sCodLanca );
			lcCampos.carregaDados();
			lcCampos.edit();
			atualizaSaldo();
			txtDataLanca.requestFocus();
			if ( tab.getNumLinhas() > 0 ) {
				cbTransf.setEnabled( false );
				rgTipoLanca.setAtivo( false );
			}
			else {
				rgTipoLanca.setAtivo( true );
			}
		}

	}

	private void novo() {

		fireInternalFrameEvent( InternalFrameEvent.INTERNAL_FRAME_CLOSED );
		sCodLanca = null;
		carregar();
	}

	private boolean testaData() {

		boolean bRetorno = false;
		if ( ( txtDataLanca.getVlrDate().after( dFim ) ) || ( txtDataLanca.getVlrDate().before( dIni ) ) ) {
			txtDataLanca.requestFocus();
			Funcoes.mensagemInforma( null, "Data n�o est� contida no periodo inicial!" );
		}
		else
			bRetorno = true;
		return bRetorno;
	}

	public String[] getValores() {

		String[] sRetorno = new String[ 8 ];
		sRetorno[ 0 ] = txtCodLanca.getVlrString().trim();
		sRetorno[ 1 ] = txtDataLanca.getVlrString().trim();
		sRetorno[ 2 ] = cbTransf.getVlrString().trim();
		sRetorno[ 3 ] = txtDocLanca.getVlrString().trim();
		sRetorno[ 4 ] = txtVlrAtualLanca.getVlrString().trim();
		sRetorno[ 5 ] = txtHistLanca.getVlrString().trim();
		sRetorno[ 6 ] = txtCodPlan.getVlrString().trim();
		return sRetorno;
	}

	public void focusGained( FocusEvent fevt ) {

		if ( fevt.getSource() == txtCodPlanSub ) {
			if ( ( ( lcCampos.getStatus() == ListaCampos.LCS_EDIT ) || ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) ) && ( testaData() ) )
				lcCampos.post();
			if ( cbTransf.getVlrString() == "S" ) {
				lcPlan.setWhereAdic( "NIVELPLAN=6 AND TIPOPLAN IN ('C','B')" );
				txtAnoCC.setVlrString( "" );
				txtCodCC.setAtivo( false );
			}
			else {
				lcPlan.setWhereAdic( "NIVELPLAN=6 AND TIPOPLAN IN ('D','R')" );
				txtAnoCC.setVlrInteger( (Integer) prefere.get( "ANOCC" ) );
				txtCodCC.setAtivo( true );
			}
			lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		}
	}

	private void testaCodLanca() { // Traz o verdadeiro n�mero do codvenda atrav�s do generator do banco

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( "SELECT * FROM SPGERANUM(?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNLANCA" ) );
			ps.setString( 3, "LA" );
			rs = ps.executeQuery();
			rs.next();
			txtCodLanca.setText( rs.getString( 1 ) );
			// rs.close();
			// ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao confirmar c�digo do lanca!\n" + err.getMessage(), true, con, err );
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btNovo ) {
			novo();
		}
		else if ( evt.getSource() == btSalvar ) {
			if ( ( ( lcCampos.getStatus() == ListaCampos.LCS_EDIT ) || ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) ) && ( testaData() ) )
				lcCampos.post();
			if ( cbTransf.getVlrString() == "S" ) {
				lcPlan.setWhereAdic( "NIVELPLAN=6 AND TIPOPLAN IN ('C','B')" );
				txtAnoCC.setVlrString( "" );
				txtCodCC.setAtivo( false );
			}
			else {
				lcPlan.setWhereAdic( "NIVELPLAN=6 AND TIPOPLAN IN ('D','R')" );
				txtAnoCC.setVlrInteger( (Integer) prefere.get( "ANOCC" ));
				txtCodCC.setAtivo( true );
			}
			lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		}
		super.actionPerformed( evt );
	}

	public void afterPost( PostEvent pevt ) {

		if ( pevt.getListaCampos() == lcDet ) {
			atualizaSaldo();
			if ( tab.getNumLinhas() > 0 )
				cbTransf.setEnabled( false );
		}
		else if ( pevt.getListaCampos() == lcCampos ) {
			btSalvar.setEnabled( false );
		}

		if ( tab.getNumLinhas() > 0 ) {
			cbTransf.setEnabled( false );
			rgTipoLanca.setAtivo( false );
		}
		else {
			cbTransf.setEnabled( true );
			rgTipoLanca.setAtivo( true );
		}
	}

	public void beforePost( PostEvent pevt ) {

		if ( pevt.getListaCampos() == lcCampos ) {
			if ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) {
				testaCodLanca();
			}
		}
		if ( pevt.getListaCampos() == lcDet ) {
			if ( ( rgTipoLanca.getVlrString().equals( "C" ) ) && ( txtCodCli.getVlrString().trim().equals( "" ) ) ) {
				Funcoes.mensagemErro( this, "C�digo do cliente n�o pode ser nulo!" );				
				pevt.cancela();
			}
			else if ( ( rgTipoLanca.getVlrString().equals( "F" ) ) && ( txtCodFor.getVlrString().trim().equals( "" ) ) ) {
				Funcoes.mensagemErro( this, "C�digo do fornecedor n�o pode ser nulo!" );
				pevt.cancela();
			}
			else {
				System.out.println("codcontr:" + txtCodContr.getVlrInteger());
				System.out.println("coditcontr:" + txtCodItContr.getVlrInteger());
			}
		}
	}

	public void afterEdit( EditEvent eevt ) {
		if ( eevt.getListaCampos() == lcCampos ) {
			btSalvar.setEnabled( true );
		}
	}

	public void afterDelete( DeleteEvent devt ) {
		atualizaSaldo();
	}

	public void beforeDelete( DeleteEvent devt ) { }

	public void beforeEdit( EditEvent eevt ) { }

	public void focusLost( FocusEvent fevt ) { }

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcPlan.setConexao( cn );
		lcCC.setConexao( cn );
		lcFor.setConexao( cn );
		lcCli.setConexao( cn );
		lcContrato.setConexao( cn );
		lcItContrato.setConexao( cn );

		carregar();
	}

	private HashMap<String,Object> getPrefere(DbConnection con) {
		HashMap<String,Object> prefere = new HashMap<String, Object>();		
		String sSQL = "SELECT ANOCENTROCUSTO, LANCAFINCONTR FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				prefere.put( "ANOCC", rs.getInt( "ANOCENTROCUSTO" ));
				prefere.put( "LANCAFINCONTR", rs.getString( "LANCAFINCONTR" ) );
				
			}
			
			vValsTipo = new Vector<String>();
			vValsTipo.addElement( "A" );
			vValsTipo.addElement( "F" );
			vValsTipo.addElement( "C" );
			
			vLabsTipo = new Vector<String>();
			vLabsTipo.addElement( "Avulso" );
			vLabsTipo.addElement( "Fornecedor" );
			vLabsTipo.addElement( "Cliente" );

			if("S".equals( (String) prefere.get( "LANCAFINCONTR" ))) {
				vValsTipo.addElement( "O" );
				vLabsTipo.addElement( "Contrato" );
				rgTipoLanca = new JRadioGroup<String, String>( 1, 4, vLabsTipo, vValsTipo );
			}
			else {
				rgTipoLanca = new JRadioGroup<String, String>( 1, 3, vLabsTipo, vValsTipo );
			}
						
			rs.close();
			ps.close();
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar prefer�ncias do sistema \n" + err.getMessage(), true, con, err );
		}
		return prefere;
	}

	public void edit( EditEvent eevt ) { }

	public void afterCarrega( CarregaEvent cevt ) {
		
		if (cevt.getListaCampos() == lcCli ) {	  		
	  		HashMap<String,Vector<Object>> vals = FuncoesCRM.montaComboContr( con, txtCodCli.getVlrInteger(), "<N�o selecionado>" );
	  		cbContr.setItens( (Vector<?>)vals.get( "LAB" ), (Vector<?>)vals.get( "VAL" ) ); 	  		
	  	}
		if (cevt.getListaCampos() == lcDet ) {
			if("O".equals(rgTipoLanca.getVlrString())) {
				cbContr.setVlrInteger( txtCodContr.getVlrInteger() );
				cbitContr.setVlrInteger( txtCodItContr.getVlrInteger() );
			}
		}
				
	}

	public void beforeCarrega( CarregaEvent cevt ) {}

	public void valorAlterado( JComboBoxEvent evt ) {
		if( evt.getComboBoxPad() == cbContr ){
	  		HashMap<String,Vector<Object>> vals = FuncoesCRM.montaComboItContr( con, cbContr.getVlrInteger(), "<N�o selecionado>" );
	  		cbitContr.setItens( (Vector<?>)vals.get( "LAB" ), (Vector<?>)vals.get( "VAL" ) );
		}
		else if (evt.getComboBoxPad() == cbitContr ) {
			if(cbContr.getVlrInteger()>0)
				txtCodContr.setVlrInteger( cbContr.getVlrInteger() );
			if(cbitContr.getVlrInteger()>0)			
			txtCodItContr.setVlrInteger( cbitContr.getVlrInteger() );
		}
		
	}
		

}
