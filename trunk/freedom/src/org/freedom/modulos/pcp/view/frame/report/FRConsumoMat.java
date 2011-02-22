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

package org.freedom.modulos.pcp.view.frame.report;

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
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.swing.util.SwingParams;

public class FRConsumoMat extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

//	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

//	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodSecao = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescSecao = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

//	private JRadioGroup<?, ?> rgTipo = null;
	
	private ListaCampos lcSecao = new ListaCampos( this );

//	private ListaCampos lcCliente = new ListaCampos( this );
	
//	private JCheckBoxPad cbFinalizados = new JCheckBoxPad( "Apenas �tens finalizados", "S", "N" );

	private boolean comref = false;

	boolean cliente = false;

	boolean diario = false;

	public FRConsumoMat() {

		setTitulo( "Relat�rio de comissionamento/produtividade" );
		setAtribos( 80, 80, 380, 380 );

		txtDescSecao.setAtivo( false );
//		txtRazCli.setAtivo( false );
/*
		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();

		vLabs.addElement( "Por �tem" );
		vLabs.addElement( "Por T�cnico" );
		vLabs.addElement( "Detalhado" );
		vVals.addElement( "I" );
		vVals.addElement( "T" );
		vVals.addElement( "D" );
		
		rgTipo = new JRadioGroup<String, String>( 1, 3, vLabs, vVals );
		rgTipo.setVlrString( "I" );
*/
		lcSecao.add( new GuardaCampo( txtCodSecao, "CodSecao", "C�d.Se��o", ListaCampos.DB_PK, false ) );
		lcSecao.add( new GuardaCampo( txtDescSecao, "DescSecao", "Descri��o da se��o", ListaCampos.DB_SI, false ) );
		txtCodSecao.setTabelaExterna( lcSecao, null );
		txtCodSecao.setNomeCampo( "CodSecao" );
		txtCodSecao.setFK( true );
		lcSecao.setReadOnly( true );
		lcSecao.montaSql( false, "SECAO", "EQ" );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );
/*
		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCliente, null );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCliente.setReadOnly( true );
		lcCliente.montaSql( false, "CLIENTE", "VD" );
*/
		JPanelPad pnPeriodo = new JPanelPad();
		pnPeriodo.setBorder( SwingParams.getPanelLabel( "Per�odo", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnPeriodo, 4, 5, 325, 60 );

		pnPeriodo.adic( new JLabelPad( "De:" ), 5, 05, 30, 20 );
		pnPeriodo.adic( txtDataini, 35, 05, 90, 20 );
		pnPeriodo.adic( new JLabelPad( "At�:" ), 135, 05, 30, 20 );
		pnPeriodo.adic( txtDatafim, 170, 05, 90, 20 );

		JPanelPad pnTipo = new JPanelPad();
		pnTipo.setBorder( SwingParams.getPanelLabel( "Tipo", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnTipo, 4, 70, 325, 70 );
	//	pnTipo.adic( rgTipo, 3, 3, 300, 30 );
		
		JPanelPad pnFiltros = new JPanelPad();
		pnFiltros.setBorder( SwingParams.getPanelLabel( "Filtros", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnFiltros, 4, 145, 325, 150 );

		pnFiltros.adic( new JLabelPad( "C�d.Se��o" ), 4, 5, 70, 20 );
		pnFiltros.adic( txtCodSecao, 4, 25, 70, 20 );

		pnFiltros.adic( new JLabelPad( "Descri��o da se��o de produ��o" ), 77, 5, 230, 20 );
		pnFiltros.adic( txtDescSecao, 77, 25, 230, 20 );

		/*
		pnFiltros.adic( new JLabelPad( "C�d.Cliente" ), 4, 45, 70, 20 );
		pnFiltros.adic( txtCodCli, 4, 65, 70, 20 );

		pnFiltros.adic( new JLabelPad( "Raz�o social do cliente" ), 77, 45, 230, 20 );
		pnFiltros.adic( txtRazCli, 77, 65, 230, 20 );
		
		pnFiltros.adic( cbFinalizados, 2, 95, 230, 20 );
*/
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

			
			sql.append( "select ");
			
			
			
			sql.append( "pd.codsecao, sc.descsecao, ");
			
			//INICIO DAS COMPRAS			
			sql.append( "( ");
			sql.append( "select ");
			sql.append( "sum(ic.qtditcompra) from cpitcompra ic, cpcompra cp, eqtipomov tm ");
			sql.append( "where ");
			sql.append( "cp.codemp=ic.codemp and cp.codfilial=ic.codfilial and cp.codcompra=ic.codcompra and cp.dtentcompra between ? and ? ");
			sql.append( "and ic.codemppd=pd.codemp and ic.codfilialpd=pd.codfilial and ic.codprod=pd.codprod ");
			sql.append( "and cp.codemptm=tm.codemp and cp.codfilialtm=tm.codfilial and cp.codtipomov=tm.codtipomov and tm.estoqtipomov='S' ");
			sql.append( "and cp.codemp=? and cp.codfilial=? and cp.statuscompra in ('P2','P3','C2','C3','EP','ET') ");
			sql.append( ") recepcionadas ");
			// FIM DAS COMPRAS
		
			sql.append( " , ");
			
			//INICIO DO CONSUMO
			
			sql.append( "( ");
			sql.append( "select ");
			sql.append( "sum(ir.qtdexpitrma) ");
			sql.append( "from ");
			sql.append( "eqitrma ir, eqproduto pd ");
			sql.append( "where ");
			sql.append( "ir.codemp=? and ir.codfilial=? and ir.dtaexpitrma between ? and ? ");
			sql.append( "and pd.codemp=ir.codemppd and pd.codfilial=ir.codfilialpd and pd.codprod=ir.codprod and pd.codsecao='M' ");
			sql.append( ") consumidas ");

			// FIM DO CONSUMO
			
			sql.append( ", ");
			
			// ESTOQUE ANTERIOR
			
			sql.append( "( ");
			sql.append( "select sldprod from eqcustoprodsp(pd.codemp, pd.codfilial, pd.codprod, ?, 'P', null, null, null, 'S') ");
			sql.append( ") saldoanterior ");

			// FIM ESTOQUE ANTERIOR
			
			sql.append( "from eqproduto pd ");
			sql.append( "inner join eqsecao sc on sc.codemp=pd.codempsc and sc.codfilial=pd.codfilialsc and sc.codsecao=pd.codsecao and pd.codsecao='M' ");
			sql.append( "and pd.tipoprod='M' ");

			if ( !"".equals( txtCodSecao.getVlrString() ) ) {
				sql.append( "and pd.codempsc=? and pd.codfilialsc=? and pd.codsecao=? " );
			}
			
		
			System.out.println("SQL:" + sql.toString());

			ps = con.prepareStatement( sql.toString() );

			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, Aplicativo.iCodFilial );
			
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, Aplicativo.iCodFilial );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );


			sCab.append( "Per�odo de " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) + " at� " + Funcoes.dateToStrDate( txtDatafim.getVlrDate() ) );

			if ( !"".equals( txtCodSecao.getVlrString() ) ) {
				ps.setInt( param++, lcSecao.getCodEmp() );
				ps.setInt( param++, lcSecao.getCodFilial() );
				ps.setString( param++, txtCodSecao.getVlrString() );

				sCab2.append( "Se��o: " + txtDescSecao.getVlrString() );
			}
			
			

			rs = ps.executeQuery();

			imprimirGrafico( visualizar, rs, sCab.toString() + "\n" + sCab2.toString(), comref, "layout/rel/REL_CONS_MAT_01.jasper" );

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

	
		dlGr = new FPrinterJob( rel, "Relat�rio de consumo de mat�ria prima ", sCab, rs, hParam, this );
		

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de consumo!" + err.getMessage(), true, con, err );
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

		comref = comRef();
	}
}
