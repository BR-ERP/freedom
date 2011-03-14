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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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


public class FRBalancoProd extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private boolean comref = false;

	boolean cliente = false;

	boolean diario = false;
	
	private ListaCampos lcGrup = new ListaCampos( this );
	
	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JCheckBoxPad cbPorFolha = new JCheckBoxPad( "Por Planos/Folhas?", "S", "N" );

	public FRBalancoProd() {

	setTitulo( "Relat�rio de consumo de mat�ria prima" );
		
		setAtribos( 80, 80, 370, 250 );

		lcGrup.add( new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, false ) );
		lcGrup.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false ) );
		lcGrup.montaSql( false, "GRUPO", "EQ" );
		lcGrup.setReadOnly( true );
		txtCodGrup.setTabelaExterna( lcGrup, null );
		txtCodGrup.setFK( true );
		txtCodGrup.setNomeCampo( "CodGrup" );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );

		JPanelPad pnPeriodo = new JPanelPad();
		pnPeriodo.setBorder( SwingParams.getPanelLabel( "Per�odo", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnPeriodo, 4, 5, 335, 60 );

		pnPeriodo.adic( new JLabelPad( "De:" ), 5, 05, 30, 20 );
		pnPeriodo.adic( txtDataini, 35, 05, 90, 20 );
		pnPeriodo.adic( new JLabelPad( "At�:" ), 135, 05, 30, 20 );
		pnPeriodo.adic( txtDatafim, 170, 05, 90, 20 );

		JPanelPad pnFiltros = new JPanelPad();
		pnFiltros.setBorder( SwingParams.getPanelLabel( "Filtros", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnFiltros, 4, 70, 335, 85 );

		pnFiltros.adic( txtCodGrup, 4, 25, 120, 20, "C�d.Grupo" );
		pnFiltros.adic( txtDescGrup, 127, 25, 185, 20, "Descri��o do grupo" );

		adic(cbPorFolha, 7, 165, 200, 20);
		

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
			sql.append( "pd.codgrup, gp.descgrup, ");
			
			if("S".equals( cbPorFolha.getVlrString())) {
				sql.append( "sum( coalesce( ope.qtdent, op.qtdfinalprodop ) / (pd.nroplanos*pd.qtdporplano) ) produzidas, ");
				sql.append( "sum( coalesce( sp.qtditsp,0 )  / (pd.nroplanos*pd.qtdporplano) ) sobras, ");
				sql.append( "sum( iv.qtditvenda ) / (pd.nroplanos*pd.qtdporplano) vendidas, ");
				sql.append( "sum( ( select sldprod from eqcustoprodsp(pd.codemp, pd.codfilial, pd.codprod, ?, 'P', null, null, null, 'S') )  / (pd.nroplanos*pd.qtdporplano) ) saldoanterior ");
			
			}
			else {

				sql.append( "sum(coalesce(ope.qtdent, op.qtdfinalprodop)) produzidas, ");
				sql.append( "sum(coalesce(sp.qtditsp,0)) sobras, ");
				sql.append( "sum(iv.qtditvenda) vendidas, ");
				sql.append( "sum((select sldprod from eqcustoprodsp(pd.codemp, pd.codfilial, pd.codprod, ?, 'P', null, null, null, 'S')) ) saldoanterior ");

			}
			
			sql.append( "from ");
			sql.append( "ppop op ");
			sql.append( "left outer join ppopentrada ope on ope.codemp=op.codemp and ope.codfilial=op.codfilial and ope.codop=op.codop and ope.seqop=op.seqop ");
			sql.append( "left outer join eqproduto pd on pd.codemp=op.codemppd and pd.codfilial=op.codfilialpd and pd.codprod=op.codprod ");
			sql.append( "left outer join eqsecao sc on sc.codemp=pd.codempsc and sc.codfilial=pd.codfilialsc and sc.codsecao=pd.codsecao ");
			sql.append( "left outer join vditvenda iv on iv.codemppd=pd.codemp and iv.codfilialpd=pd.codfilial and iv.codprod=pd.codprod ");
			sql.append( "left outer join vdvenda vd on vd.codemp=iv.codemp and vd.codfilial=iv.codfilial and vd.codvenda=iv.codvenda and vd.tipovenda=iv.tipovenda ");
			sql.append( "and vd.statusvenda in ('P3', 'V2', 'V3') ");
			sql.append( "where ");
			sql.append( "op.dtfabrop between ? and ? ");
			sql.append( "group by 1,2 ");
			
			if("S".equals( cbPorFolha.getVlrString())) {
				sql.append(",pd.nroplanos, pd.qtdporplano");
			}

			if ( !"".equals( txtCodGrup.getVlrString() ) ) {
				sql.append( "and pd.codempgp=? and pd.codfilialgp=? and pd.codgrup=? " );
			}
			
			System.out.println("SQL:" + sql.toString());

			ps = con.prepareStatement( sql.toString() );

			Date dtant = txtDataini.getVlrDate();
			Calendar cant = new GregorianCalendar();
			cant.setTime( dtant );
			cant.add( Calendar.DAY_OF_YEAR, -1 ); 
			
			ps.setDate( param++, Funcoes.dateToSQLDate( cant.getTime()) );
			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, Aplicativo.iCodFilial );
			


			sCab.append( "Per�odo de " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) + " at� " + Funcoes.dateToStrDate( txtDatafim.getVlrDate() ) );

			if ( !"".equals( txtCodGrup.getVlrString() ) ) {
				ps.setInt( param++, lcGrup.getCodEmp() );
				ps.setInt( param++, lcGrup.getCodFilial() );
				ps.setString( param++, txtCodGrup.getVlrString() );

				sCab2.append( "Grupo: " + txtDescGrup.getVlrString() );
			}
			
			

			rs = ps.executeQuery();

			imprimirGrafico( visualizar, rs, sCab.toString() + "\n" + sCab2.toString(), comref, "layout/rel/REL_PRODUCAO_GRUPO_01.jasper" );

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

		lcGrup.setConexao( cn );

		comref = comRef();
	}
}
