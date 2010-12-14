/**
 * @version 05/08/2008 <BR>
 * @author Setpoint Inform�tica Ltda.
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.pcp <BR>
 *         Classe:
 * @(#)FRAnalise.java <BR>
 * 
 *                    Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                    modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                    na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                    Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                    sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                    Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                    Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                    de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                    Tela para cadastro de estruturas de produtos.
 * 
 */

package org.freedom.modulos.pcp.view.frame.report;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import net.sf.jasperreports.engine.JasperPrintManager;

public class FRProducao extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodProdDE = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdDE = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodProdATE = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdATE = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRefProdDE = new JTextFieldFK( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtRefProdATE = new JTextFieldFK( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcProdDE = new ListaCampos( this, "" );

	private ListaCampos lcProdATE = new ListaCampos( this, "" );

	private ListaCampos lcGrup = new ListaCampos( this );

	public FRProducao() {

		super( false );
		setTitulo( "Produ��o" );
		setAtribos( 50, 50, 350, 260 );

		montaTela();
		montaListaCampos();
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Per�odo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 300, 45 );

		adic( new JLabelPad( "De:" ), 10, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad( "At�:" ), 152, 25, 37, 20 );
		adic( txtDatafim, 190, 25, 100, 20 );

		adic( new JLabelPad( "C�d.Prod.Ini." ), 7, 55, 80, 20 );
		adic( txtCodProdDE, 7, 75, 80, 20 );

		adic( new JLabelPad( "Descri��o do produto inicial" ), 90, 55, 210, 20 );
		adic( txtDescProdDE, 90, 75, 210, 20 );

		adic( new JLabelPad( "C�d.Prod.Fin." ), 7, 95, 80, 20 );
		adic( txtCodProdATE, 7, 115, 80, 20 );

		adic( new JLabelPad( "Descri��o do produto final" ), 90, 95, 200, 20 );
		adic( txtDescProdATE, 90, 115, 210, 20 );

		adic( new JLabelPad( "C�d.Grupo" ), 7, 135, 80, 20 );
		adic( txtCodGrup, 7, 155, 80, 20 );

		adic( new JLabelPad( "Descri��o do Grupo" ), 90, 135, 200, 20 );
		adic( txtDescGrup, 90, 155, 210, 20 );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	private void montaListaCampos() {

		/******************
		 * Produto "DE" *
		 ******************/

		lcProdDE.add( new GuardaCampo( txtCodProdDE, "CodProd", "C�d.prod.", ListaCampos.DB_PK, false ) );
		lcProdDE.add( new GuardaCampo( txtRefProdDE, "RefProd", "Refer�ncia do produto", ListaCampos.DB_SI, false ) );
		lcProdDE.add( new GuardaCampo( txtDescProdDE, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProdDE.setWhereAdic( "TIPOPROD='F'" );
		txtCodProdDE.setTabelaExterna( lcProdDE, null );
		txtCodProdDE.setNomeCampo( "CodProd" );
		txtCodProdDE.setFK( true );
		lcProdDE.setReadOnly( true );
		lcProdDE.montaSql( false, "PRODUTO", "EQ" );

		/******************
		 * Produto "ATE" *
		 ******************/

		lcProdATE.add( new GuardaCampo( txtCodProdATE, "CodProd", "C�d.prod.", ListaCampos.DB_PK, false ) );
		lcProdATE.add( new GuardaCampo( txtRefProdATE, "RefProd", "Refer�ncia do produto", ListaCampos.DB_SI, false ) );
		lcProdATE.add( new GuardaCampo( txtDescProdATE, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProdATE.setWhereAdic( "TIPOPROD='F'" );
		txtCodProdATE.setTabelaExterna( lcProdATE, null );
		txtCodProdATE.setNomeCampo( "CodProd" );
		txtCodProdATE.setFK( true );
		lcProdATE.setReadOnly( true );
		lcProdATE.montaSql( false, "PRODUTO", "EQ" );

		/******************
		 * Produto "ATE" *
		 ******************/

		lcGrup.add( new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, false ) );
		lcGrup.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false ) );
		lcGrup.montaSql( false, "GRUPO", "EQ" );
		lcGrup.setReadOnly( true );
		txtCodGrup.setTabelaExterna( lcGrup, null );
		txtCodGrup.setFK( true );
		txtCodGrup.setNomeCampo( "CodGrup" );

	}

	public void imprimir( boolean b ) {

		StringBuffer sql = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sql.append( "select op.codprod, pd.descprod, pd.codunid, sum(coalesce(en.qtdent, op.qtdfinalprodop)) as qtdfinalprodop " );
			sql.append( "from eqproduto pd, ppop op " );
			sql.append( "left outer join ppopentrada en on en.codemp=op.codemp and en.codfilial=op.codfilial and en.codop=op.codop and en.seqop=op.seqop " );
			sql.append( "where " );

			sql.append( "op.codemp=? and op.codfilial=? " );

			if ( txtCodProdDE.getVlrInteger() > 0 && txtCodProdATE.getVlrInteger() > 0 ) {
				sql.append( "and op.codemppd=? and op.codfilialpd=? and op.codprod between ? and ? " );
			}
			else if ( txtCodProdDE.getVlrInteger() > 0 ) {
				sql.append( "and op.codemppd=? and op.codfilialpd=? and op.codprod=? " );
			}

			sql.append( "and pd.codemp=op.codemppd and pd.codfilial=op.codfilialpd and pd.codprod=op.codprod " );
			sql.append( "and op.sitop<>'CA' and coalesce(en.dtent,op.dtfabrop) between ? and ? " );
			sql.append( "and op.qtdfinalprodop>0 " );

			if ( txtCodGrup.getText().trim().length() > 0 ) {

				sql.append( "and pd.codempgp=? and pd.codfilialgp=? and pd.codgrup like '" );
				sql.append( txtCodGrup.getText().trim() );
				sql.append( "%'" );

			}

			sql.append( "group by op.codprod, pd.descprod, pd.codunid " );
			sql.append( "order by pd.descprod " );

			sql.append( sWhere.toString() );

			ps = con.prepareStatement( sql.toString() );

			int iparam = 1;

			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "PPOP" ) );

			if ( txtCodProdDE.getVlrInteger() > 0 && txtCodProdATE.getVlrInteger() > 0 ) {
				ps.setInt( iparam++, lcProdDE.getCodEmp() );
				ps.setInt( iparam++, lcProdDE.getCodFilial() );
				ps.setInt( iparam++, txtCodProdDE.getVlrInteger() );
				ps.setInt( iparam++, txtCodProdATE.getVlrInteger() );
			}
			else if ( txtCodProdDE.getVlrInteger() > 0 ) {
				ps.setInt( iparam++, lcProdDE.getCodEmp() );
				ps.setInt( iparam++, lcProdDE.getCodFilial() );
				ps.setInt( iparam++, txtCodProdDE.getVlrInteger() );
			}

			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );

			if ( txtCodGrup.getText().trim().length() > 0 ) {
				ps.setInt( iparam++, lcGrup.getCodEmp() );
				ps.setInt( iparam++, lcGrup.getCodFilial() );
			}

			rs = ps.executeQuery();

			sCab.append( "Perido: " + txtDataini.getVlrString() + " At�: " + txtDatafim.getVlrString() );

			imprimiGrafico( rs, b, sCab.toString() );

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar produ��o", true, con, err );
		}
	}

	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		StringBuilder sql = new StringBuilder();
		StringBuilder status = new StringBuilder();
		StringBuilder filtros = new StringBuilder();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "layout/rel/REL_PROD_01.jasper", "Relat�rio de Produ��o", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de Relat�rio de Produ��o!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection con ) {

		super.setConexao( con );
		lcProdDE.setConexao( con );
		lcProdATE.setConexao( con );
		lcGrup.setConexao( con );

	}
}
