/**
 * @version 22/02/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.pcp <BR>
 *         Classe:
 * @(#)FRConsumoMat.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela para filtros do relat�rio de consumo de mat�ria prima por se��o.
 * 
 */

package org.freedom.modulos.pcp.view.frame.report.fsc;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import javax.swing.border.TitledBorder;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;


public class FRExtratoPorProdutoFSC extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private boolean comref = false;

	boolean cliente = false;

	boolean diario = false;
	
	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );	
	
	private JTextFieldPad txtCodSecao = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtDescSecao = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodFabProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JCheckBoxPad cbPorFolha = new JCheckBoxPad( "Por Folhas (FSC)", "S", "N" );
	
	private JCheckBoxPad cbOpsProdInter = new JCheckBoxPad( "Incluir OP's de produtos intermedi�rios", "S", "N" );
	
	//Lista Campos
	private ListaCampos lcSecao = new ListaCampos( this );
	
	private ListaCampos lcGrupo = new ListaCampos( this );
	
	private ListaCampos lcProd = new ListaCampos( this, "" );
	
	public FRExtratoPorProdutoFSC() {

	setTitulo( "Relat�rio de extrato por Produto FSC" );
		
		setAtribos( 40, 40, 370, 320 );
		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );
		
		cbPorFolha.setVlrString( "S" );
		cbOpsProdInter.setVlrString( "S" );
		
		montaListaCampos();
		montaTela();

	}
	
	public void montaTela(){

		JPanelPad pnPeriodo = new JPanelPad();
		pnPeriodo.setBorder( SwingParams.getPanelLabel( "Per�odo", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnPeriodo, 4, 5, 335, 60 );

		pnPeriodo.adic( new JLabelPad( "De:" ), 5, 05, 30, 20 );
		pnPeriodo.adic( txtDataini, 35, 05, 90, 20 );
		pnPeriodo.adic( new JLabelPad( "At�:" ), 135, 05, 30, 20 );
		pnPeriodo.adic( txtDatafim, 170, 05, 90, 20 );

		JPanelPad pnFiltros = new JPanelPad();
		pnFiltros.setBorder( SwingParams.getPanelLabel( "Filtros", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnFiltros, 4, 70, 335, 180 );
		
		pnFiltros.adic( txtCodGrup, 4, 25, 120, 20, "C�d.Grup" );
		pnFiltros.adic( txtDescGrup, 127, 25, 185, 20, "Descri��o do grupo" );
		
		pnFiltros.adic( txtCodSecao, 4, 68, 120, 20, "C�d.Se��o" );
		pnFiltros.adic( txtDescSecao, 127, 68, 185, 20, "Descri��o da se��o" );
		
		pnFiltros.adic( txtRefProd, 4, 111, 120, 20, "Ref.Prod." );
		pnFiltros.adic( txtDescProd, 127, 111, 185, 20, "Descri��o do Produto" );

		//adic(cbPorFolha, 7, 165, 300, 20);
		//adic(cbOpsProdInter, 7, 185, 300, 20);
		
	}
	
	public void montaListaCampos(){

		lcSecao.add( new GuardaCampo( txtCodSecao, "CodSecao", "C�d.Se��o", ListaCampos.DB_PK, false ) );
		lcSecao.add( new GuardaCampo( txtDescSecao, "DescSecao", "Descri��o da se��o", ListaCampos.DB_SI, false ) );
		lcSecao.montaSql( false, "SECAO", "EQ" );
		lcSecao.setReadOnly( true );
		txtCodSecao.setTabelaExterna( lcSecao, null );
		txtCodSecao.setFK( true );
		txtCodSecao.setNomeCampo( "CodSecao" );
		
		lcGrupo.add( new GuardaCampo( txtCodGrup, "CodGrup", "C�d.Grupo", ListaCampos.DB_PK, true ) );
		lcGrupo.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false ) );
		lcGrupo.montaSql( false, "GRUPO", "EQ" );
		lcGrupo.setReadOnly( true );
		txtCodGrup.setTabelaExterna( lcGrupo, null );
		txtCodGrup.setFK( true );
		txtCodGrup.setNomeCampo( "CodGrup" );
		
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Refer�ncia do produto", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodFabProd, "codfabprod", "C�d.fab.prod.", ListaCampos.DB_SI, false ) );
		txtRefProd.setTabelaExterna( lcProd, FProduto.class.getCanonicalName() );
		txtRefProd.setNomeCampo( "RefProd" );
		txtRefProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );

		
	}

	public void imprimir( boolean visualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		StringBuffer sCab2 = new StringBuffer();

		int param = 1;

		try {


			if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
				return;
			}
			
			sql.append("select  mp.dtmovprod, mp.docmovprod , pd.codprod, pd.descprod, mp.tipomovprod, ");
			sql.append("mp.qtdmovprod * (case when tm.estipomov='E' then 1 else -1 end) qtdmovprod, ");
			sql.append("pd.nroplanos, pd.qtdporplano, pd.fatorfsc, ");
			sql.append("mp.qtdmovprod * pd.fatorfsc / (pd.qtdporplano * pd.nroplanos) * (case ");
			sql.append("when tm.estipomov='E' then 1 else -1 end) qtdmovprod_folhas ");
			sql.append("from eqproduto pd, eqgrupo gp, eqmovprod mp, eqtipomov tm ");
			sql.append("where pd.codempgp=gp.codemp and pd.codfilialgp=gp.codfilial ");
			sql.append("and pd.codgrup=gp.codgrup and pd.tipoprod='F' and pd.certfsc='S' ");
			sql.append("and pd.codemp=? and pd.codfilial=? and pd.codempgp=? and pd.codfilialgp=? ");
			sql.append("and pd.codgrup=? and pd.tipoprod='F' ");
			if ( !"".equals( txtCodSecao.getVlrString() ) ) {
				sql.append( " and pd.codempsc=? and pd.codfilialsc=? and pd.codsecao=? " );
			}		
			
			if ( !"".equals( txtRefProd.getVlrString() ) ) {
				sql.append( "and pd.codemp=? and pd.codfilial=? and pd.codprod=? " );
			}
			
			sql.append("and mp.codemppd=pd.codemp and mp.codfilial=pd.codfilial ");
			sql.append("and mp.codprod=pd.codprod ");
			sql.append("and tm.codemp=mp.codemptm and tm.codfilial=mp.codfilialtm and ");
			sql.append("tm.codtipomov=mp.codtipomov ");
			sql.append("and tm.estoqtipomov='S' and mp.dtmovprod between ? and ? ");
			sql.append("order by mp.dtmovprod, pd.codprod, pd.descprod ");
		
			System.out.println("SQL:" + sql.toString());

			ps = con.prepareStatement( sql.toString() );
		
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQGRUPO" ) );
			ps.setString( param++, txtCodGrup.getVlrString() );

			if ( !"".equals( txtCodSecao.getVlrString() ) ) {
				ps.setInt( param++, lcSecao.getCodEmp() );
				ps.setInt( param++, lcSecao.getCodFilial() );
				ps.setString( param++, txtCodSecao.getVlrString() );

				sCab2.append( "Grupo: " + txtDescSecao.getVlrString() );
			}
			
			if ( !"".equals( txtRefProd.getVlrString() ) ) {
				
				ps.setInt( param++, lcProd.getCodEmp() );
				ps.setInt( param++, lcProd.getCodFilial() );
				ps.setInt( param++, txtCodProd.getVlrInteger() );
				
				sCab2.append( "Produto: " + txtDescProd.getVlrString() );
			}
			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			
			sCab.append( "Per�odo de " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) + " at� " + Funcoes.dateToStrDate( txtDatafim.getVlrDate() ) );

			rs = ps.executeQuery();

			imprimirGrafico( visualizar, rs, sCab.toString() + "\n" + sCab2.toString(), comref, "layout/rel/REL_FSC_EXTRATO_PRODUTO_01.jasper" );

			rs.close();
			ps.close();

			con.commit();

		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + err.getMessage(), true, con, err );
		} finally {
			System.gc();
		}
	}
	
	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab, final boolean bComRef , String rel ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();
	//	hParam.put( "COMREF", bComRef ? "S" : "N" );

		FPrinterJob dlGr = null;

	
		dlGr = new FPrinterJob( rel, "Relat�rio de Extrato por produto0 ", sCab, rs, hParam, this );
		

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de extrato por produto!" + err.getMessage(), true, con, err );
			}
		}
	}

	private boolean comRef() {

		boolean bRetorno = false;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement( "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				bRetorno = "S".equals( rs.getString( "UsaRefProd" ) );
			}

			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		}
		return bRetorno;
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcSecao.setConexao( cn );
		lcGrupo.setConexao( cn );
		lcProd.setConexao( cn );

		comref = comRef();
	}
}
