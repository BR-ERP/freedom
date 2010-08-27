/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLEditaRec.java <BR>
 * 
 *                     Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                     na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                     Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                     Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                     Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Coment�rios sobre a classe...
 */

package org.freedom.modulos.fnc.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

public class DLEditaRec extends FFDialogo implements CarregaListener, FocusListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtCodBordero = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtCodRec = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );// novo

	private JTextFieldPad txtnParcitrec = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );// novo

	private JTextFieldPad txtDatBordero = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtNumConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtNumContaBor = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtDescContaBordero = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescBordero = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescConta = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescPlan = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldFK txtSiglaCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDtEmis = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtVenc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtPrev = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrJuros = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrDesc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrDev = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrParc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtObs = new JTextFieldPad( JTextFieldPad.TP_STRING, 250, 0 );

	private final JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodCartCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldFK txtDescCartCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcBanco = new ListaCampos( this );

	private ListaCampos lcBordero = new ListaCampos( this );

	private ListaCampos lcDataBord = new ListaCampos( this );

	private ListaCampos lcNumConta = new ListaCampos( this );

	private ListaCampos lcNumContabor = new ListaCampos( this );

	private ListaCampos lcConta = new ListaCampos( this );

	private ListaCampos lcPlan = new ListaCampos( this );

	private ListaCampos lcCC = new ListaCampos( this );

	private final ListaCampos lcCartCob = new ListaCampos( this, "CB" );

	private final ListaCampos lcTipoCob = new ListaCampos( this, "TC" );

	private JCheckBoxPad cbDescPont = new JCheckBoxPad( "Desconto pontualidade?", "S", "N" );

	public enum EColEdit {
		CODCLI, RAZCLI, NUMCONTA, CODPLAN, CODCC, DOC, DTEMIS, DTVENC, VLRJUROS, VLRDESC, VLRDEVOLUCAO, VLRPARC, OBS, CODBANCO, CODTPCOB, DESCTPCOB, CODCARTCOB, DESCCARTCOB, DESCPONT, DTPREV, CODBOR, CODREC, CORREC, NPARCITREC
	};

	public enum EColRet {
		NUMCONTA, CODPLAN, CODCC, DOC, VLRJUROS, VLRDESC, VLRDEVOLUCAO, DTVENC, OBS, CODBANCO, CODTPCOB, DESCTPCOB, CODCARTCOB, DESCCARTCOB, DESCPONT, DTPREV, CODBOR, CODREC, NPARCITREC, VLRPARC
	};

	public DLEditaRec( Component cOrig, final boolean bEdita ) {

		super( cOrig );
		setTitulo( bEdita ? "Editar" : "Visualizar" );
		setAtribos( 365, 530 );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco, null );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );

		/************************
		 * CARTEIRA DE COBRAN�A *
		 ************************/

		txtCodCartCob.setNomeCampo( "CodCartCob" );
		lcCartCob.add( new GuardaCampo( txtCodCartCob, "CodCartCob", "C�d.cart.cob", ListaCampos.DB_PK, false ) );
		lcCartCob.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, false ) );
		lcCartCob.add( new GuardaCampo( txtDescCartCob, "DescCartCob", "Desc.Cart.Cob", ListaCampos.DB_SI, false ) );
		lcCartCob.setDinWhereAdic( "CODBANCO = #S", txtCodBanco );
		lcCartCob.montaSql( false, "CARTCOB", "FN" );
		lcCartCob.setQueryCommit( false );
		lcCartCob.setReadOnly( true );
		txtCodCartCob.setTabelaExterna( lcCartCob, null );
		txtCodCartCob.setListaCampos( lcCartCob );
		txtDescCartCob.setListaCampos( lcCartCob );
		txtCodCartCob.setFK( true );

		lcBordero.add( new GuardaCampo( txtCodBordero, "CodBor", "C�d.bordero", ListaCampos.DB_PK, false ) );
		lcBordero.add( new GuardaCampo( txtDescBordero, "ObsBor", "Observa��o Bordero", ListaCampos.DB_SI, false ) );
		lcBordero.montaSql( false, "BORDERO", "FN" );
		lcBordero.setReadOnly( true );
		txtCodBordero.setTabelaExterna( lcBordero, null );
		txtCodBordero.setFK( true );
		txtCodBordero.setNomeCampo( "CodBor" );
		txtCodBordero.setAtivo( false );

		lcDataBord.add( new GuardaCampo( txtDatBordero, "DatBor", "Dat.bordero", ListaCampos.DB_PK, false ) );
		lcDataBord.add( new GuardaCampo( txtDescBordero, "ObsBor", "Observa��o Bordero", ListaCampos.DB_SI, false ) );
		lcDataBord.montaSql( false, "BORDERO", "FN" );
		lcDataBord.setReadOnly( true );
		txtDatBordero.setTabelaExterna( lcDataBord, null );
		txtDatBordero.setFK( true );
		txtDatBordero.setNomeCampo( "DatBor" );
		txtDatBordero.setAtivo( false );

		lcNumConta.add( new GuardaCampo( txtNumConta, "NumConta", "Num.Conta", ListaCampos.DB_PK, false ) );
		lcNumConta.montaSql( false, "BORDERO", "FN" );
		lcNumConta.setReadOnly( true );
		txtNumConta.setTabelaExterna( lcNumConta, null );
		txtNumConta.setFK( true );
		txtNumConta.setNomeCampo( "numConta" );
		txtNumConta.setAtivo( false );

		lcNumContabor.add( new GuardaCampo( txtNumContaBor, "NumContaBor", "Num.ContaBor", ListaCampos.DB_PK, false ) );
		lcNumContabor.montaSql( false, "BORDERO", "FN" );
		lcNumContabor.setReadOnly( true );
		txtNumContaBor.setTabelaExterna( lcNumContabor, null );
		txtNumContaBor.setFK( true );
		txtNumContaBor.setNomeCampo( "txtNumContaBor" );
		txtNumContaBor.setAtivo( false );

		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "N� Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descri��o da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta, null );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );
		txtDescConta.setTabelaExterna( lcConta, null );
		txtDescConta.setLabel( "Descri��o da Conta" );

		lcPlan.add( new GuardaCampo( txtCodPlan, "CodPlan", "C�d.plan.", ListaCampos.DB_PK, false ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descri��o do planejamento", ListaCampos.DB_SI, false ) );
		lcPlan.setWhereAdic( "TIPOPLAN = 'R' AND NIVELPLAN = 6" );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlan.setReadOnly( true );
		txtCodPlan.setTabelaExterna( lcPlan, null );
		txtCodPlan.setFK( true );
		txtCodPlan.setNomeCampo( "CodPlan" );

		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "C�d.c.c.", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla c.c.", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descri��o do centro de custos", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano-Base", ListaCampos.DB_PK, false ) );
		lcCC.setReadOnly( true );
		lcCC.setQueryCommit( false );
		lcCC.setWhereAdic( "NIVELCC=10" );
		lcCC.montaSql( false, "CC", "FN" );
		txtCodCC.setTabelaExterna( lcCC, null );
		txtCodCC.setFK( true );
		txtCodCC.setNomeCampo( "CodCC" );
		txtAnoCC.setTabelaExterna( lcCC, null );
		txtAnoCC.setFK( true );
		txtAnoCC.setNomeCampo( "AnoCC" );

		txtCodTipoCob.setNomeCampo( "CodTipoCob" );
		lcTipoCob.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "C�d.tp.cob.", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTipoCob, "DescTipoCob", "Descri��o do tipo de cobran�a.", ListaCampos.DB_SI, false ) );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCob.setQueryCommit( false );
		lcTipoCob.setReadOnly( true );
		txtCodTipoCob.setTabelaExterna( lcTipoCob, null );
		txtCodTipoCob.setListaCampos( lcTipoCob );
		txtDescTipoCob.setListaCampos( lcTipoCob );
		txtCodTipoCob.setFK( true );

		txtCodCli.setAtivo( false );
		// txtRazCli.setAtivo( false );
		// txtDescConta.setAtivo( false );
		// txtDescPlan.setAtivo( false );
		txtDtEmis.setAtivo( false );
//		txtVlrParc.setAtivo( false );

		adic( new JLabelPad( "C�d.cli." ), 7, 0, 250, 20 );
		adic( txtCodCli, 7, 20, 80, 20 );
		adic( new JLabelPad( "Raz�o social do cliente" ), 90, 0, 250, 20 );
		adic( txtRazCli, 90, 20, 250, 20 );
		adic( new JLabelPad( "Cod.Tp.Cob" ), 7, 40, 80, 20 );
		adic( txtCodTipoCob, 7, 60, 80, 20 );
		adic( new JLabelPad( "Descri��o do tipo de cobran�a" ), 90, 40, 250, 20 );
		adic( txtDescTipoCob, 90, 60, 250, 20 );
		adic( new JLabelPad( "C�d.banco" ), 7, 80, 250, 20 );
		adic( txtCodBanco, 7, 100, 80, 20 );
		adic( new JLabelPad( "Descri��o do banco" ), 90, 80, 250, 20 );
		adic( txtDescBanco, 90, 100, 250, 20 );

		adic( new JLabelPad( "C�d.cart.cob." ), 7, 120, 250, 20 );
		adic( txtCodCartCob, 7, 140, 80, 20 );
		adic( new JLabelPad( "Descri��o da carteira de cobran�a" ), 90, 120, 250, 20 );
		adic( txtDescCartCob, 90, 140, 250, 20 );

		adic( new JLabelPad( "N�conta" ), 7, 160, 250, 20 );
		adic( txtCodConta, 7, 180, 80, 20 );
		adic( new JLabelPad( "Descri��o da conta" ), 90, 160, 250, 20 );
		adic( txtDescConta, 90, 180, 250, 20 );

		adic( new JLabelPad( "C�d.categoria" ), 7, 200, 110, 20 );
		adic( txtCodPlan, 7, 220, 110, 20 );
		adic( new JLabelPad( "Descri��o da categoria" ), 120, 200, 220, 20 );
		adic( txtDescPlan, 120, 220, 220, 20 );

		adic( new JLabelPad( "C�d.c.c." ), 7, 240, 110, 20 );
		adic( txtCodCC, 7, 260, 110, 20 );
		adic( new JLabelPad( "Descri��o do centro de custo" ), 120, 240, 220, 20 );
		adic( txtDescCC, 120, 260, 220, 20 );

		adic( new JLabelPad( "Doc." ), 7, 280, 110, 20 );
		adic( txtDoc, 7, 300, 110, 20 );

		adic( new JLabelPad( "Emiss�o" ), 120, 280, 72, 20 );
		adic( txtDtEmis, 120, 300, 72, 20 );

		adic( new JLabelPad( "Vencimento" ), 195, 280, 71, 20 );
		adic( txtDtVenc, 195, 300, 71, 20 );

		adic( new JLabelPad( "Previs�o" ), 269, 280, 71, 20 );
		adic( txtDtPrev, 269, 300, 71, 20 );

		adic( new JLabelPad( "Vlr.juros." ), 7, 320, 81, 20 );
		adic( txtVlrJuros, 7, 340, 81, 20 );

		adic( new JLabelPad( "Vlr.desc." ), 91, 320, 81, 20 );
		adic( txtVlrDesc, 91, 340, 81, 20 );

		adic( cbDescPont, 185, 340, 200, 20 );
		cbDescPont.setVlrString( "S" );

		adic( new JLabelPad( "Vlr.devolu��o" ), 7, 360, 81, 20 );
		adic( txtVlrDev, 7, 380, 81, 20 );

		adic( new JLabelPad( "Vlr.parcela" ), 91, 360, 81, 20 );
		adic( txtVlrParc, 91, 380, 81, 20 );

		adic( new JLabelPad( "Observa��es" ), 7, 400, 240, 20 );
		adic( txtObs, 7, 420, 333, 20 );

		adic( new JLabelPad( "C�d.Border�" ), 7, 440, 110, 20 );
		adic( txtCodBordero, 7, 460, 110, 20 );

		adic( new JLabelPad( "Observa��o do border�" ), 120, 440, 220, 20 );
		adic( txtDescBordero, 120, 460, 220, 20 );

		adic( new JLabelPad( "Data.Border�" ), 7, 480, 110, 20 );
		adic( txtDatBordero, 7, 500, 110, 20 );

		adic( new JLabelPad( "Num.Conta" ), 120, 480, 220, 20 );
		adic( txtNumConta, 120, 500, 220, 20 );

		adic( new JLabelPad( "Num.Conta.Border�" ), 7, 520, 220, 20 );
		adic( txtNumContaBor, 7, 540, 220, 20 );

		lcCC.addCarregaListener( this );
		txtDtVenc.addFocusListener( this );

		if ( !bEdita ) {
			txtAnoCC.setAtivo( bEdita );
			txtCodBanco.setAtivo( bEdita );
			txtCodCartCob.setAtivo( bEdita );
			txtCodCC.setAtivo( bEdita );
			txtCodCli.setAtivo( bEdita );
			txtCodConta.setAtivo( bEdita );
			txtCodPlan.setAtivo( bEdita );
			txtCodTipoCob.setAtivo( bEdita );
			txtDoc.setAtivo( bEdita );
			txtDtEmis.setAtivo( bEdita );
			txtDtVenc.setAtivo( bEdita );
			txtDtPrev.setAtivo( bEdita );
			txtObs.setAtivo( bEdita );
			txtSiglaCC.setAtivo( bEdita );
			txtVlrDesc.setAtivo( bEdita );
			txtVlrJuros.setAtivo( bEdita );
			txtVlrParc.setAtivo( bEdita );
			txtCodBordero.setAtivo( bEdita );
			btOK.setVisible( bEdita );
		}

	}

	public void setValores( Object[] sVals ) {

		txtCodCli.setVlrInteger( (Integer) sVals[ EColEdit.CODCLI.ordinal() ] );
		txtRazCli.setVlrString( (String) sVals[ EColEdit.RAZCLI.ordinal() ] );
		txtCodConta.setVlrString( (String) sVals[ EColEdit.NUMCONTA.ordinal() ] );
		txtCodPlan.setVlrString( (String) sVals[ EColEdit.CODPLAN.ordinal() ] );
		txtCodCC.setVlrString( (String) sVals[ EColEdit.CODCC.ordinal() ] );
		txtDoc.setVlrString( (String) sVals[ EColEdit.DOC.ordinal() ] );
		txtDtEmis.setVlrDate( (Date) sVals[ EColEdit.DTEMIS.ordinal() ] );
		txtDtVenc.setVlrDate( (Date) sVals[ EColEdit.DTVENC.ordinal() ] );
		txtDtPrev.setVlrDate( (Date) sVals[ EColEdit.DTPREV.ordinal() ] );

		txtVlrJuros.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRJUROS.ordinal() ] );
		txtVlrDesc.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRDESC.ordinal() ] );
		txtVlrDev.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRDEVOLUCAO.ordinal() ] );
		txtVlrParc.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRPARC.ordinal() ] );
		txtObs.setVlrString( (String) sVals[ EColEdit.OBS.ordinal() ] );
		txtCodBanco.setVlrString( (String) sVals[ EColEdit.CODBANCO.ordinal() ] );
		txtCodTipoCob.setVlrString( (String) sVals[ EColEdit.CODTPCOB.ordinal() ] );
		txtDescTipoCob.setVlrString( (String) sVals[ EColEdit.DESCTPCOB.ordinal() ] );
		txtCodCartCob.setVlrString( (String) sVals[ EColEdit.CODCARTCOB.ordinal() ] );
		txtDescCartCob.setVlrString( (String) sVals[ EColEdit.DESCCARTCOB.ordinal() ] );
		cbDescPont.setVlrString( (String) sVals[ EColEdit.DESCPONT.ordinal() ] );
		// txtCodBordero.setVlrString( (String) sVals[ EColEdit.CODBOR.ordinal()] );//novo
		txtCodRec.setVlrInteger( (Integer) sVals[ EColEdit.CODREC.ordinal() ] );// novo
		txtnParcitrec.setVlrInteger( (Integer) sVals[ EColEdit.NPARCITREC.ordinal() ] );// novo

		lcConta.carregaDados();
		lcBanco.carregaDados();
		lcPlan.carregaDados();
		lcCC.carregaDados();
		lcBordero.carregaDados();

	}

	public Object[] getValores() {

		Object[] oRetorno = new Object[ EColRet.values().length ];
		oRetorno[ EColRet.NUMCONTA.ordinal() ] = txtCodConta.getVlrString();
		oRetorno[ EColRet.CODPLAN.ordinal() ] = txtCodPlan.getVlrString();
		oRetorno[ EColRet.CODCC.ordinal() ] = txtCodCC.getVlrString();
		oRetorno[ EColRet.DOC.ordinal() ] = txtDoc.getVlrString();
		oRetorno[ EColRet.VLRJUROS.ordinal() ] = txtVlrJuros.getVlrBigDecimal();
		oRetorno[ EColRet.VLRDESC.ordinal() ] = txtVlrDesc.getVlrBigDecimal();
		oRetorno[ EColRet.VLRDEVOLUCAO.ordinal() ] = txtVlrDev.getVlrBigDecimal();
		oRetorno[ EColRet.DTVENC.ordinal() ] = txtDtVenc.getVlrDate();
		oRetorno[ EColRet.DTPREV.ordinal() ] = txtDtPrev.getVlrDate();
		oRetorno[ EColRet.OBS.ordinal() ] = txtObs.getVlrString();
		oRetorno[ EColRet.CODBANCO.ordinal() ] = txtCodBanco.getVlrString();
		oRetorno[ EColRet.CODTPCOB.ordinal() ] = txtCodTipoCob.getVlrString();
		oRetorno[ EColRet.DESCTPCOB.ordinal() ] = txtDescTipoCob.getVlrString();
		oRetorno[ EColRet.CODCARTCOB.ordinal() ] = txtCodCartCob.getVlrString();
		oRetorno[ EColRet.DESCCARTCOB.ordinal() ] = txtDescCartCob.getVlrString();
		oRetorno[ EColRet.DESCPONT.ordinal() ] = cbDescPont.getVlrString();
		oRetorno[ EColRet.CODBOR.ordinal() ] = txtCodBordero.getVlrString();
		oRetorno[ EColRet.CODREC.ordinal() ] = txtCodRec.getVlrInteger();
		oRetorno[ EColRet.NPARCITREC.ordinal() ] = txtnParcitrec.getVlrInteger();
		oRetorno[ EColRet.VLRPARC.ordinal() ] = txtVlrParc.getVlrBigDecimal();

		return oRetorno;
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK && txtDtVenc.getVlrString().length() < 10 ) {
			Funcoes.mensagemInforma( this, "Data do vencimento � requerido!" );
		}
		else {
			super.actionPerformed( evt );
		}
	}

	private int buscaAnoBaseCC() {

		int iRet = 0;

		try {

			PreparedStatement ps = con.prepareStatement( "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				iRet = rs.getInt( "ANOCENTROCUSTO" );
			}

			rs.close();
			ps.close();

			con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err );
		}

		return iRet;
	}

	/**
	 * M�todo que preenche os novos campos de visualiza��o tendo como entrada no primeiro parametro o CODREC e segundo o NPARCITREC.
	 * 
	 */
	private void buscaBordero( String valor1, int valor2 ) {

		int codBor = 0;
		String dataBor = "";
		String numConta = "";
		String obsBor = "";
		String statusBor = "";
		String numContaBordero = "";

		try {

			PreparedStatement ps = con.prepareStatement( "SELECT bo.numconta, bo.numcontabor, ibo.codbor, bo.dtbor, bo.obsbor, bo.statusbor " + "FROM FNITRECEBER re, FNITBORDERO ibo, FNBORDERO bo " + "WHERE re.codemp = ibo.codemp " + "AND re.codfilial = ibo.codfilial "
					+ "AND re.codrec = ibo.codrec " + "AND ibo.codemp = bo.codemp " + "AND ibo.codfilial = bo.codfilial " + "AND ibo.codbor = bo.codbor  " + "AND re.codemp = ? AND re.codfilial = ? " + "AND re.codrec = ? AND re.nparcitrec = ? " );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
			ps.setString( 3, valor1 );
			ps.setInt( 4, valor2 );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {

				setAtribos( 365, 650 );

				codBor = rs.getInt( "CODBOR" );
				dataBor = rs.getString( "DTBOR" );
				numConta = rs.getString( "NUMCONTA" );
				obsBor = rs.getString( "OBSBOR" );
				statusBor = rs.getString( "STATUSBOR" );
				numContaBordero = rs.getString( "NUMCONTABOR" );

				txtCodBordero.setVlrInteger( codBor );
				txtDatBordero.setVlrString( dataBor );
				txtNumContaBor.setVlrString( numContaBordero );
				txtNumConta.setVlrString( numConta );

			}

			rs.close();
			ps.close();

			con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar border�.\n" + err.getMessage(), true, con, err );
		}

	}

	public void beforeCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcCC && txtAnoCC.getVlrInteger().intValue() == 0 ) {
			txtAnoCC.setVlrInteger( new Integer( buscaAnoBaseCC() ) );
		}

		if ( txtnParcitrec.getVlrInteger() != 0 ) {
			buscaBordero( txtCodRec.getVlrString(), txtnParcitrec.getVlrInteger() );
		}

	}

	public void afterCarrega( CarregaEvent cevt ) {

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcConta.setConexao( cn );
		lcConta.carregaDados();
		lcPlan.setConexao( cn );
		lcPlan.carregaDados();
		lcCC.setConexao( cn );
		lcCC.carregaDados();
		lcBanco.setConexao( cn );
		lcTipoCob.setConexao( cn );
		lcBanco.carregaDados();
		lcCartCob.setConexao( cn );
		lcCartCob.carregaDados();
		lcBordero.setConexao( cn );
		lcBordero.carregaDados();

	}

	public void focusGained( FocusEvent arg0 ) {

		// TODO Auto-generated method stub

	}

	public void focusLost( FocusEvent e ) {

		if ( e.getSource() == txtDtVenc ) {

			txtDtPrev.setVlrDate( txtDtVenc.getVlrDate() );

		}

	}
}
