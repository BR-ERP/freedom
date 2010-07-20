/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLBaixaPag.java <BR>
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
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;

public class DLBaixaPag extends FFDialogo implements CarregaListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtRazFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtDescConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldPad txtDescPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private final JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private final JTextFieldFK txtSiglaCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtDtEmis = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtVenc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtVlrParc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtDtPagto = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtVlrPago = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtObs = new JTextFieldPad( JTextFieldPad.TP_STRING, 250, 0 );

	private final JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final ListaCampos lcConta = new ListaCampos( this );

	private final ListaCampos lcPlan = new ListaCampos( this );

	private final ListaCampos lcCC = new ListaCampos( this );

	private final ListaCampos lcTipoCob = new ListaCampos( this, "TC" );

	public DLBaixaPag( Component cOrig ) {

		super( cOrig );
		setTitulo( "Baixa" );
		setAtribos( 360, 420 );

		montaListaCampos();
		montaTela();

	}

	private void montaListaCampos() {

		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "N� Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descri��o", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta, null );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );

		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "C�digo", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descri��o", ListaCampos.DB_SI, false ) );
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

		lcPlan.add( new GuardaCampo( txtCodPlan, "CodPlan", "C�d. Plan.", ListaCampos.DB_PK, false ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descri��o", ListaCampos.DB_SI, false ) );
		lcPlan.setWhereAdic( "TIPOPLAN = 'D' AND NIVELPLAN = 6" );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlan.setReadOnly( true );
		txtCodPlan.setTabelaExterna( lcPlan, null );
		txtCodPlan.setFK( true );
		txtCodPlan.setNomeCampo( "CodPlan" );

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
	}

	private void montaTela() {

		Funcoes.setBordReq( txtCodPlan );
		Funcoes.setBordReq( txtCodConta );
		Funcoes.setBordReq( txtDoc );
		Funcoes.setBordReq( txtDtPagto );
		Funcoes.setBordReq( txtVlrPago );
		Funcoes.setBordReq( txtObs );

		txtCodFor.setAtivo( false );
		txtRazFor.setAtivo( false );
		txtDescConta.setAtivo( false );
		txtDescPlan.setAtivo( false );
		txtDtEmis.setAtivo( false );
		txtDtVenc.setAtivo( false );
		txtVlrParc.setAtivo( false );

		adic( new JLabelPad( "C�d.for." ), 7, 0, 80, 20 );
		adic( txtCodFor, 7, 20, 80, 20 );
		adic( new JLabelPad( "Raz�o do fornecedor" ), 90, 0, 250, 20 );
		adic( txtRazFor, 90, 20, 250, 20 );
		adic( new JLabelPad( "C�d.conta" ), 7, 40, 80, 20 );
		adic( txtCodConta, 7, 60, 80, 20 );
		adic( new JLabelPad( "Descri��o da conta" ), 90, 40, 250, 20 );
		adic( txtDescConta, 90, 60, 250, 20 );
		adic( new JLabelPad( "C�d.ctg." ), 7, 80, 100, 20 );
		adic( txtCodPlan, 7, 100, 100, 20 );
		adic( new JLabelPad( "Descri��o da categoria" ), 110, 80, 230, 20 );
		adic( txtDescPlan, 110, 100, 230, 20 );
		adic( new JLabelPad( "C�d.c.c." ), 7, 120, 100, 20 );
		adic( txtCodCC, 7, 140, 100, 20 );
		adic( new JLabelPad( "Descri��o do centro de custo" ), 110, 120, 230, 20 );
		adic( txtDescCC, 110, 140, 230, 20 );

		adic( new JLabelPad( "Cod.Tp.Cob" ), 7, 160, 80, 20 );
		adic( txtCodTipoCob, 7, 180, 80, 20 );
		adic( new JLabelPad( "Descri��o do tipo de cobran�a" ), 90, 160, 250, 20 );
		adic( txtDescTipoCob, 90, 180, 250, 20 );

		adic( new JLabelPad( "Doc." ), 7, 200, 110, 20 );
		adic( txtDoc, 7, 220, 110, 20 );
		adic( new JLabelPad( "Emiss�o" ), 120, 200, 107, 20 );
		adic( txtDtEmis, 120, 220, 107, 20 );
		adic( new JLabelPad( "Vencimento" ), 230, 200, 110, 20 );
		adic( txtDtVenc, 230, 220, 110, 20 );
		adic( new JLabelPad( "Vlr. Parc." ), 7, 240, 110, 20 );
		adic( txtVlrParc, 7, 260, 110, 20 );
		adic( new JLabelPad( "Dt. Pagto." ), 120, 240, 107, 20 );
		adic( txtDtPagto, 120, 260, 107, 20 );
		adic( new JLabelPad( "Vlr. Pago" ), 230, 240, 110, 20 );
		adic( txtVlrPago, 230, 260, 110, 20 );
		adic( new JLabelPad( "Observa��es" ), 7, 280, 200, 20 );
		adic( txtObs, 7, 300, 333, 20 );

		lcCC.addCarregaListener( this );

	}

	public void setValores( String[] sVals ) {

		txtCodFor.setVlrString( sVals[ 0 ] );
		txtRazFor.setVlrString( sVals[ 1 ] );
		txtCodConta.setVlrString( sVals[ 2 ] );
		txtCodPlan.setVlrString( sVals[ 3 ] );
		txtDoc.setVlrString( sVals[ 4 ] );
		txtDtEmis.setVlrString( sVals[ 5 ] );
		txtDtVenc.setVlrString( sVals[ 6 ] );
		txtVlrParc.setVlrString( sVals[ 7 ] );
		txtDtPagto.setVlrString( sVals[ 8 ] );
		txtVlrPago.setVlrString( sVals[ 9 ] );
		txtCodCC.setVlrString( sVals[ 10 ] );
		txtCodTipoCob.setVlrString( sVals[ 11 ] );
		txtObs.setVlrString( sVals[ 12 ] );

	}

	public String[] getValores() {

		String[] sRetorno = new String[ 8 ];

		sRetorno[ 0 ] = txtCodConta.getVlrString();
		sRetorno[ 1 ] = txtCodPlan.getVlrString();
		sRetorno[ 2 ] = txtDoc.getVlrString();
		sRetorno[ 3 ] = txtDtPagto.getVlrString();
		sRetorno[ 4 ] = txtVlrPago.getVlrString();
		sRetorno[ 5 ] = txtCodCC.getVlrString();
		sRetorno[ 6 ] = txtCodTipoCob.getVlrString();
		sRetorno[ 7 ] = txtObs.getVlrString();

		return sRetorno;

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {

			if ( txtCodConta.getVlrString().length() < 1 ) {
				Funcoes.mensagemInforma( this, "N�mero da conta � requerido!" );
			}
			else if ( txtCodPlan.getVlrString().length() < 13 ) {
				Funcoes.mensagemInforma( this, "C�digo da categoria � requerido!" );
			}
			else if ( txtDtPagto.getVlrString().length() < 10 ) {
				Funcoes.mensagemInforma( this, "Data do pagamento � requerido!" );
			}
			else if ( txtVlrPago.getVlrString().length() < 4 ) {
				Funcoes.mensagemInforma( this, "Valor pago � requerido!" );
			}
			else if ( txtVlrPago.getVlrDouble().doubleValue() <= 0.0 ) {
				Funcoes.mensagemInforma( this, "Valor pago deve ser maior que zero!" );
			}
			else {
				super.actionPerformed( evt );
			}
		}
		else {
			super.actionPerformed( evt );
		}

	}

	private int buscaAnoBaseCC() {

		int iRet = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement( "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				iRet = rs.getInt( "ANOCENTROCUSTO" );
			}

			rs.close();
			ps.close();

			con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
		}

		return iRet;
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcCC && txtAnoCC.getVlrInteger().intValue() == 0 ) {
			txtAnoCC.setVlrInteger( new Integer( buscaAnoBaseCC() ) );
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
		lcTipoCob.setConexao( cn );
		lcTipoCob.carregaDados();
	}

}
