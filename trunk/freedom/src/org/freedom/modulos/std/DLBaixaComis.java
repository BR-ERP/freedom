/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: projeto.freedomstd <BR>
 * Classe:
 * @(#)DLBaixaComis.java <BR>
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
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Component;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.JLabelPad;
import org.freedom.library.JPanelPad;
import org.freedom.library.JTextFieldFK;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;
import org.freedom.library.Tabela;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JScrollPane;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLBaixaComis extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JPanelPad pnCliente = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinCentro = new JPanelPad( 580, 100 );

	private JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtVlr = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private JTextFieldPad txtObs = new JTextFieldPad( JTextFieldPad.TP_STRING, 300, 0 );

	private JTextFieldFK txtDescConta = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescPlan = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private Tabela tab = new Tabela();

	private JScrollPane spnTab = new JScrollPane( tab );

	private ListaCampos lcConta = new ListaCampos( this );

	private ListaCampos lcPlan = new ListaCampos( this );

	private Date dIni = null;

	private Date dFim = null;

	private Integer iCodVend = null;

	private String sEmitRel = "";

	public DLBaixaComis( Component cOrig, DbConnection cn, String sM, Date dI, Date dF, Integer iCodV ) {

		super( cOrig );
		sEmitRel = sM;
		setTitulo( "Baixar" );
		setAtribos( 600, 300 );
		setConexao( cn );
		dIni = dI;
		dFim = dF;
		iCodVend = iCodV;

		c.add( pnCliente, BorderLayout.CENTER );

		pnCliente.add( pinCentro, BorderLayout.SOUTH );
		pnCliente.add( spnTab, BorderLayout.CENTER );

		Funcoes.setBordReq( txtCodConta );
		Funcoes.setBordReq( txtCodPlan );
		Funcoes.setBordReq( txtData );
		Funcoes.setBordReq( txtDoc );
		Funcoes.setBordReq( txtVlr );

		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "N� Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descri��o", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );

		lcPlan.add( new GuardaCampo( txtCodPlan, "CodPlan", "C�d.plan.", ListaCampos.DB_PK, false ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descri��o", ListaCampos.DB_SI, false ) );
		lcPlan.setWhereAdic( "TIPOPLAN = 'D' AND NIVELPLAN = 6" );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlan.setReadOnly( true );
		txtCodPlan.setTabelaExterna( lcPlan );
		txtCodPlan.setFK( true );
		txtCodPlan.setNomeCampo( "CodPlan" );

		setPainel( pinCentro );

		adic( new JLabelPad( "C�d.conta" ), 7, 0, 80, 20 );
		adic( txtCodConta, 7, 20, 80, 20 );
		adic( new JLabelPad( "Descri��o da conta" ), 90, 0, 190, 20 );
		adic( txtDescConta, 90, 20, 197, 20 );
		adic( new JLabelPad( "C�d.plan." ), 290, 0, 77, 20 );
		adic( txtCodPlan, 290, 20, 77, 20 );
		adic( new JLabelPad( "Descri��o da planejamento" ), 370, 0, 200, 20 );
		adic( txtDescPlan, 370, 20, 200, 20 );
		adic( new JLabelPad( "Data" ), 7, 40, 100, 20 );
		adic( txtData, 7, 60, 100, 20 );
		adic( new JLabelPad( "Valor tot." ), 110, 40, 107, 20 );
		adic( txtVlr, 110, 60, 107, 20 );
		adic( new JLabelPad( "Doc" ), 220, 40, 77, 20 );
		adic( txtDoc, 220, 60, 77, 20 );
		adic( new JLabelPad( "Obs." ), 300, 40, 270, 20 );
		adic( txtObs, 300, 60, 270, 20 );

		tab.adicColuna( "Cliente" );
		tab.adicColuna( "Doc." );
		tab.adicColuna( "Parc." );
		tab.adicColuna( "Valor" );
		tab.adicColuna( "Emiss�o" );
		tab.adicColuna( "Vencimento" );

		tab.setTamColuna( 200, 0 );
		tab.setTamColuna( 70, 1 );
		tab.setTamColuna( 30, 2 );
		tab.setTamColuna( 100, 3 );
		tab.setTamColuna( 95, 4 );
		tab.setTamColuna( 95, 5 );
		tab.setTamColuna( 95, 6 );

		montaTabela();

		txtData.setVlrDate( new Date() );
		txtObs.setVlrString( "PAGAMENTO DE COMISS�ES AO COMISSIONADO: " + iCodV );

	}

	private void montaTabela() {

		tab.limpa();
		BigDecimal bSum = new BigDecimal( "0" );
		String sSQL = "SELECT CL.RAZCLI,R.DOCREC,ITR.NPARCITREC," + "C.VLRCOMI,C.DATACOMI,C.DTVENCCOMI FROM VDCOMISSAO C, VDCLIENTE CL," + "FNRECEBER R, FNITRECEBER ITR WHERE C.CODEMP=? AND C.CODFILIAL=? AND " + "R.CODVEND = ? AND ITR.CODEMP=R.CODEMP AND "
				+ " ITR.CODFILIAL=R.CODFILIAL AND ITR.CODREC =R.CODREC AND " + "C.CODEMPRC = ITR.CODEMP AND C.CODFILIALRC = ITR.CODFILIAL AND " + "C.CODREC = ITR.CODREC AND " + ( sEmitRel.equals( "E" ) ? "C.DATACOMI" : "C.DTVENCCOMI" )
				+ " BETWEEN ? AND ? AND CL.CODEMP=R.CODEMPCL AND CL.CODFILIAL=R.CODFILIALCL AND " + "CL.CODCLI=R.CODCLI AND C.STATUSCOMI = 'C2'" + "AND C.NPARCITREC = ITR.NPARCITREC ORDER BY C.DTVENCCOMI";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, iCodVend.intValue() );
			ps.setDate( 4, Funcoes.dateToSQLDate( dIni ) );
			ps.setDate( 5, Funcoes.dateToSQLDate( dFim ) );
			ResultSet rs = ps.executeQuery();
			for ( int i = 0; rs.next(); i++ ) {
				tab.adicLinha();
				tab.setValor( rs.getString( "RazCli" ), i, 0 );
				tab.setValor( rs.getString( "DocRec" ), i, 1 );
				tab.setValor( rs.getString( "NParcItRec" ), i, 2 );
				tab.setValor( Funcoes.strDecimalToStrCurrency( 10, 2, rs.getString( "VlrComi" ) ), i, 3 );
				tab.setValor( Funcoes.dateToStrDate( rs.getDate( "datacomi" ) ), i, 4 );
				tab.setValor( Funcoes.dateToStrDate( rs.getDate( "DtVencComi" ) ), i, 5 );
				bSum = bSum.add( new BigDecimal( rs.getString( "VlrComi" ) ) );
			}
			txtVlr.setVlrBigDecimal( bSum );
			// rs.close();
			// ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro lendo ResultSet!\n" + err.getMessage(), true, con, err );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcConta.setConexao( cn );
		lcPlan.setConexao( cn );
	}

	public String[] getValores() {

		String[] sRetorno = new String[ 6 ];
		sRetorno[ 0 ] = txtCodConta.getVlrString();
		sRetorno[ 1 ] = txtCodPlan.getVlrString();
		sRetorno[ 2 ] = txtData.getVlrString();
		sRetorno[ 3 ] = txtDoc.getVlrString();
		sRetorno[ 4 ] = txtVlr.getVlrString();
		sRetorno[ 5 ] = txtObs.getVlrString();
		return sRetorno;
	}
}
