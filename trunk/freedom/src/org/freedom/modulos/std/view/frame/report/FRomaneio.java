/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FRomaneio.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.modulos.std.view.dialog.report.DLRRomaneio;

public class FRomaneio extends FDetalhe implements InsertListener, ActionListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JTextFieldPad txtCodRoma = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDataRoma = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtSaidaRoma = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtPrevRoma = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtEntregaRoma = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtStatusRoma = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtCodItRoma = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTransp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDtPrevItRoma = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDescVenda = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtRazTransp = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JButtonPad btBuscar = new JButtonPad( "Busca venda", Icone.novo( "btGerar.gif" ) );

	private ListaCampos lcVenda = new ListaCampos( this, "VA" );

	private ListaCampos lcTransp = new ListaCampos( this, "TN" );
	
	private ListaCampos lcExpedicao = new ListaCampos( this, "EX" );

	private JTextFieldPad txtTicket = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtPesoInicial = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 10, 0 );
	
	private JTextFieldFK txtPesoFinal = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 10, 0 );
	
	private JTextFieldFK txtPesoLiquido = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 10, 0 );
	
	public FRomaneio() {

		setTitulo( "Cadastro de Romaneio" );
		setAtribos( 20, 20, 760, 540 );

		montaListaCampos();
		montaTela();

		lcCampos.addInsertListener( this );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		
		lcExpedicao.addCarregaListener( this );
		
		setImprimir( true );
		
	}

	private void montaTela() {

		setAltCab( 140 );
		
		pinCab = new JPanelPad( 500, 90 );
		
		nav.setNavigation( true );
		
		setListaCampos( lcCampos );
		
		setPainel( pinCab, pnCliCab );
		
		adicCampo( txtCodRoma			, 7		, 20	, 80	, 20, "CodRoma"			, "C�d.roma."	, ListaCampos.DB_PK, true );
		adicCampo( txtDataRoma			, 90	, 20	, 77	, 20, "DataRoma"		, "Data"		, ListaCampos.DB_SI, true );
		adicCampo( txtDtSaidaRoma		, 170	, 20	, 77	, 20, "DtSaidaRoma"		, "Dt.sa�da"	, ListaCampos.DB_SI, true );
		adicCampo( txtDtPrevRoma		, 250	, 20	, 77	, 20, "DtPrevRoma"		, "Dt.prevista"	, ListaCampos.DB_SI, true );
		adicCampo( txtDtEntregaRoma		, 330	, 20	, 87	, 20, "DtEntregaRoma"	, "Dt.entrega"	, ListaCampos.DB_SI, false );
		
		adicCampo( txtTicket			, 420	, 20	, 50	, 20, "ticket"			, "Ticket"		, ListaCampos.DB_FK, txtPesoInicial, false );
		adicDescFK( txtPesoInicial		, 473	, 20	, 80	, 20, "PesoEntrada"		, "Peso entrada" );
		adicDescFK( txtPesoFinal		, 556	, 20	, 80	, 20, "PesoSaida"		, "Peso sa�da" );
		adic( txtPesoLiquido			, 639	, 20	, 80	, 20, "PesoLiquido" );
		
		adicCampoInvisivel( txtStatusRoma, "StatusRoma", "Status", ListaCampos.DB_SI, false );
		
		adicCampo( txtCodTransp			, 7		, 65	, 70	, 20, "CodTran"			, "C�d.Transp"		, ListaCampos.DB_FK, txtRazTransp, true );
		adicDescFK( txtRazTransp		, 80	, 65	, 250	, 20, "RazTransp"		, "Raz�o social da transportadora" );
		
		setListaCampos( true, "ROMANEIO", "VD" );
		
		lcCampos.setQueryInsert( false );

		adic( btBuscar, 340, 60, 150, 30 );
		btBuscar.addActionListener( this );

		setAltDet( 60 );
		pinDet = new JPanelPad( 590, 110 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		
		adicCampo( txtCodItRoma, 7, 20, 50, 20, "CodItRoma", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtCodVenda, 60, 20, 77, 20, "CodVenda", "C�d.venda", ListaCampos.DB_FK, true );
		adicDescFK( txtDescVenda, 140, 20, 147, 20, "VlrLiqVenda", "Valor da venda" );
		adicCampo( txtDtPrevItRoma, 290, 20, 100, 20, "DtPrevItRoma", "Data de previs�o", ListaCampos.DB_SI, true );
		
		setListaCampos( true, "ITROMANEIO", "VD" );
		lcCampos.setQueryInsert( false );
		montaTab();

		tab.setTamColuna( 150, 2 );
		tab.setTamColuna( 100, 3 );
		
	}
	
	private void calculaPesoLiquido() {
		
		BigDecimal pesoentrada = txtPesoInicial.getVlrBigDecimal();
		BigDecimal pesosaida = txtPesoFinal.getVlrBigDecimal();
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
	
	public void exec(Integer codroma) {
		try {
			if(codroma!=null) {
				txtCodRoma.setVlrInteger( codroma );
				lcCampos.carregaDados();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void montaListaCampos() {

		/*************
		 * VENDA *
		 ************/

		lcVenda.add( new GuardaCampo( txtCodVenda	, "CodVenda"	, "C�d.venda"		, ListaCampos.DB_PK, true ) );
		lcVenda.add( new GuardaCampo( txtDescVenda	, "VlrLiqVenda"	, "Valor da venda"	, ListaCampos.DB_SI, false ) );
		lcVenda.montaSql( false, "VENDA", "VD" );
		lcVenda.setQueryCommit( false );
		lcVenda.setReadOnly( true );
		txtCodVenda.setTabelaExterna( lcVenda, null );
		txtDescVenda.setListaCampos( lcVenda );

		/********************
		 * TRANSPORTADORA *
		 ********************/

		lcTransp.add( new GuardaCampo( txtCodTransp	, "CodTran"		, "C�d.trans"		, ListaCampos.DB_PK, true ) );
		lcTransp.add( new GuardaCampo( txtRazTransp	, "RazTran"		, "Raz�o social da transportadora", ListaCampos.DB_SI, false ) );
		lcTransp.montaSql( false, "TRANSP", "VD" );
		lcTransp.setQueryCommit( false );
		lcTransp.setReadOnly( true );
		txtCodTransp.setTabelaExterna( lcTransp, null );

		/********************
		 * EXPEDICAO        *
		 ********************/

		lcExpedicao.add( new GuardaCampo( txtTicket			, "ticket"			, "Ticket"		, ListaCampos.DB_PK, false ) );
		lcExpedicao.add( new GuardaCampo( txtPesoInicial	, "PesoEntrada"		, "Peso inicial", ListaCampos.DB_SI, false ) );
		lcExpedicao.add( new GuardaCampo( txtPesoFinal		, "PesoSaida"		, "Peso final"	, ListaCampos.DB_SI, false ) );
		lcExpedicao.montaSql( false, "EXPEDICAO", "EQ" );
		lcExpedicao.setQueryCommit( false );
		lcExpedicao.setReadOnly( true );
		txtTicket.setTabelaExterna( lcExpedicao, null );
		
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		else if ( evt.getSource() == btBuscar ) {
			gerar();
		}
		super.actionPerformed( evt );
	}

	private int getmax() {

		int imax = 0;
		StringBuffer sql = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement ps = null;

		sql.append( "select max(coditroma) from vditromaneio where codemp=? and codfilial=? and codroma=? " );

		try {

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "vdromaneio" ) );
			ps.setInt( 3, txtCodRoma.getVlrInteger() );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				imax = rs.getInt( 1 ) + 1;
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar max(romaneio)!" );
		}

		return imax;
	}

	private void gerar() {

		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		sql.append( "select v.codemp, v.codfilial, v.codvenda, v.tipovenda " );
		sql.append( "from vdvenda v, vdfretevd f " );
		sql.append( "where " );
		sql.append( "v.codemp=f.codemp and v.codfilial=f.codfilial and v.tipovenda=f.tipovenda and v.codvenda=f.codvenda " );
		sql.append( "and v.codvenda not in ( select codvenda from vditromaneio where codemp=? and codfilial=? and codroma=? ) and " );
		sql.append( "f.codemptn=? and f.codfilialtn=? and f.codtran=? and " );
		sql.append( "v.dtsaidavenda=? " );

		try {

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, lcCampos.getCodFilial() );
			ps.setInt( 3, txtCodRoma.getVlrInteger() );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "vdvenda" ) );
			ps.setInt( 6, txtCodTransp.getVlrInteger() );
			ps.setDate( 7, Funcoes.dateToSQLDate( txtDtSaidaRoma.getVlrDate() ) );

			rs = ps.executeQuery();

			while ( rs.next() ) {

				insertRegistros( rs.getInt( "codemp" ), rs.getInt( "codfilial" ), txtCodRoma.getVlrInteger(), getmax(), rs.getInt( "codemp" ), rs.getInt( "codfilial" ), rs.getString( "tipovenda" ), rs.getInt( "codvenda" ), Funcoes.dateToSQLDate( txtDtSaidaRoma.getVlrDate() ) );

			}

			con.commit();

			lcCampos.carregaDados();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados da venda!" );
		}
	}

	private void insertRegistros( int codemp, int codfilial, int codroma, int coditroma, int codempva, int codfilialva, String tipovenda, int codvenda, Date dtprevitroma ) {

		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;

		sql.append( "insert into vditromaneio( codemp, codfilial, codroma, coditroma, codempva, codfilialva, " );
		sql.append( "tipovenda, codvenda, dtprevitroma ) values( ?,?,?,?,?,?,?,?,? )" );

		try {

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			ps.setInt( 3, codroma );
			ps.setInt( 4, coditroma );
			ps.setInt( 5, codempva );
			ps.setInt( 6, codfilialva );
			ps.setString( 7, tipovenda );
			ps.setInt( 8, codvenda );
			ps.setDate( 9, Funcoes.dateToSQLDate( dtprevitroma ) );

			ps.execute();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemInforma( this, "Erro ao inserir dados na tabela VDITROMANEIO" );
		}
	}

	private void imprimir( boolean bVisualizar ) {

		String sProd = "P.CODPROD ";
		DLRRomaneio dl = new DLRRomaneio();
		StringBuffer sql = new StringBuffer();

		if ( comRefProd() ) {

			sProd = "P.REFPROD ";
		}

		dl.setVisible( true );
		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}

		sql.append( "SELECT R.DATAROMA, " );
		sql.append( sProd );
		sql.append( ", P.DESCPROD, P.CODUNID, R.CODROMA, " );
		sql.append( "SUM(I.QTDITVENDA) QTDITVENDA, " );
		sql.append( "SUM(I.VLRLIQITVENDA/I.QTDITVENDA) VLRUNIT, " );
		sql.append( "SUM(I.VLRLIQITVENDA) VLRTOTAL, " );
		sql.append( "P.REFPROD,P.LOCALPROD " );
		sql.append( "FROM VDROMANEIO R, VDITROMANEIO IR, " );
		sql.append( "VDVENDA V,VDITVENDA I,EQPRODUTO P " );
		sql.append( "WHERE R.CODROMA=? AND IR.CODROMA=R.CODROMA  " );
		sql.append( "AND V.CODVENDA=IR.CODVENDA AND I.CODVENDA=V.CODVENDA  " );
		sql.append( "AND P.CODPROD=I.CODPROD AND I.QTDITVENDA>0 " );
		sql.append( "GROUP BY R.DATAROMA, " );
		sql.append( sProd );
		sql.append( ", P.DESCPROD, P.CODUNID, R.CODROMA, P.REFPROD, P.LOCALPROD " );
		sql.append( "ORDER BY " );
		sql.append( ( dl.getValor().trim().equals( "CODPROD" ) ? sProd : dl.getValor() ) );

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, txtCodRoma.getVlrInteger().intValue() );
			rs = ps.executeQuery();

			imprimiGrafico( rs, bVisualizar );

			con.commit();

			dl.dispose();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta do relat�rio\n!" + err.getMessage(), true, con, err );
		}
	}

	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar ) {

		FPrinterJob dlGr = null;

		dlGr = new FPrinterJob( "relatorios/Romaneio.jasper", "Romaneio", null, rs, null, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de Romaneio!" + err.getMessage(), true, con, err );
			}
		}
	}

	private boolean comRefProd() {

		boolean bResultado = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				bResultado = rs.getString( "UsaRefProd" ).trim().equals( "S" );
			}

			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao consultar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		}
		return bResultado;
	}

	public void afterInsert( InsertEvent ievt ) {

		txtStatusRoma.setVlrString( "R1" );
	}

	public void beforeInsert( InsertEvent ievt ) {

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		lcVenda.setConexao( cn );
		lcTransp.setConexao( cn );
		lcExpedicao.setConexao( cn );
		
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if( cevt.getListaCampos() == lcExpedicao ) {
			
			calculaPesoLiquido();
			
		}
		
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		// TODO Auto-generated method stub
		
	}
}
