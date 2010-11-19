/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FRVendasItem.java <BR>
 * 
 *                       Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                       modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                       na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                       Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                       sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                       Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                       Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                       de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                       Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.gms.view.frame.report;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.border.TitledBorder;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.swing.util.SwingParams;

public class FRColetas extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodSecao = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescSecao = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JRadioGroup<?, ?> rgTipo = null;
	
	private ListaCampos lcSecao = new ListaCampos( this );

	private ListaCampos lcCliente = new ListaCampos( this );
	
	private JCheckBoxPad cbFinalizados = new JCheckBoxPad( "Itens finalizados", "S", "N" );
	
	private JCheckBoxPad cbSoGarantia = new JCheckBoxPad( "S� garantia", "S", "N" );

	private boolean comref = false;

	boolean cliente = false;

	boolean diario = false;

	public FRColetas() {

		setTitulo( "Coletas por dia" );
		setAtribos( 80, 80, 380, 380 );

		txtDescSecao.setAtivo( false );
		txtRazCli.setAtivo( false );

		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();

		vLabs.addElement( "Se��o" );
		vLabs.addElement( "Cliente" );
		vLabs.addElement( "Data" );
		vLabs.addElement( "Setor" );
		vVals.addElement( "S" );
		vVals.addElement( "C" );
		vVals.addElement( "D" );
		vVals.addElement( "R" );
		rgTipo = new JRadioGroup<String, String>( 1, 4, vLabs, vVals );
		rgTipo.setVlrString( "S" );

		lcSecao.add( new GuardaCampo( txtCodSecao, "CodSecao", "C�d.Se��o", ListaCampos.DB_PK, false ) );
		lcSecao.add( new GuardaCampo( txtDescSecao, "DescSecao", "Descri��o da se��o", ListaCampos.DB_SI, false ) );
		txtCodSecao.setTabelaExterna( lcSecao, null );
		txtCodSecao.setNomeCampo( "CodSecao" );
		txtCodSecao.setFK( true );
		lcSecao.setReadOnly( true );
		lcSecao.montaSql( false, "SECAO", "EQ" );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );

		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCliente, null );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCliente.setReadOnly( true );
		lcCliente.montaSql( false, "CLIENTE", "VD" );

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
		pnTipo.adic( rgTipo, 3, 3, 300, 30 );
		
		JPanelPad pnFiltros = new JPanelPad();
		pnFiltros.setBorder( SwingParams.getPanelLabel( "Filtros", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnFiltros, 4, 145, 325, 150 );

		pnFiltros.adic( new JLabelPad( "C�d.Se��o" ), 4, 5, 70, 20 );
		pnFiltros.adic( txtCodSecao, 4, 25, 70, 20 );

		pnFiltros.adic( new JLabelPad( "Descri��o da se��o de produ��o" ), 77, 5, 230, 20 );
		pnFiltros.adic( txtDescSecao, 77, 25, 230, 20 );

		pnFiltros.adic( new JLabelPad( "C�d.Cliente" ), 4, 45, 70, 20 );
		pnFiltros.adic( txtCodCli, 4, 65, 70, 20 );

		pnFiltros.adic( new JLabelPad( "Raz�o social do cliente" ), 77, 45, 230, 20 );
		pnFiltros.adic( txtRazCli, 77, 65, 230, 20 );
		
		pnFiltros.adic( cbFinalizados, 2, 95, 130, 20 );
		
		pnFiltros.adic( cbSoGarantia, 155, 95, 230, 20 );

	}

	public void imprimir( boolean visualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		if ( txtCodCli.getVlrInteger() > 0 ) {
			cliente = true;
			diario = false;
		}
		else {
			cliente = false;
			diario = true;
		}

		if("S".equals( rgTipo.getVlrString() )){
			if ( cliente ) {
				imprimirCliente( visualizar );
			}
			else if ( diario ) {
				imprimirDiarioPorSecao( visualizar );
			}
		}
		else if( "C".equals( rgTipo.getVlrString() ) || ("D".equals( rgTipo.getVlrString() ))  || ("R".equals( rgTipo.getVlrString() ))){
			imprimirDiarioPorClienteData( visualizar );
		}
		

	}

	private void imprimirCliente( boolean visualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		StringBuffer sCab = new StringBuffer();
		StringBuffer sCab2 = new StringBuffer();

		int param = 1;

		sql.append( "select " );
		sql.append( "se.codsecao, se.descsecao, rm.dtent, rm.hins, rm.dtprevret, it.qtditrecmerc, pd.codprod, pd.refprod, " );
		sql.append( "pd.descprod, rm.ticket, cl.codcli, cl.razcli, rm.docrecmerc, vd.nomevend, mn.nomemunic, it.numserie, it.garantia " );
		sql.append( "from " );
		sql.append( "eqrecmerc rm " );
		sql.append( "left outer join vdcliente cl on " );
		sql.append( "cl.codemp=rm.codempcl and cl.codfilial=rm.codfilialcl and cl.codcli=rm.codcli " );
		sql.append( "left outer join sgmunicipio mn on " );
		sql.append( "mn.codpais=cl.codpais and mn.siglauf=cl.siglauf and mn.codmunic=cl.codmunic " );
		sql.append( "left outer join vdvendedor vd on " );
		sql.append( "vd.codemp=rm.codempvd and vd.codfilial=rm.codfilialvd and vd.codvend=rm.codvend " );
		sql.append( "left outer join eqitrecmerc it on " );
		sql.append( "it.codemp=rm.codemp and it.codfilial=rm.codfilial and it.ticket=rm.ticket " );
		sql.append( "left outer join eqproduto pd on " );
		sql.append( "pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod " );
		sql.append( "left outer join eqsecao se on " );
		sql.append( "se.codemp=pd.codempsc and se.codfilial=pd.codfilialsc and se.codsecao=pd.codsecao " );
		sql.append( "where " );
		sql.append( "rm.codemp=? and rm.codfilial=? and rm.dtent between ? and ? " );

//		if( "S".equals( cbPendentes.getVlrString()) ) {
//			sql.append( " and it.statusitrecmerc not in ('FN') " );
//		}
		if( "N".equals( cbFinalizados.getVlrString()) ) {
			sql.append( " and it.statusitrecmerc not in ('FN','PT') " );
		}
		
		if( "S".equals( cbSoGarantia.getVlrString()) ) {
			sql.append( " and it.garantia = 'S' " );
		}

		
		if ( txtCodCli.getVlrInteger() > 0 ) {
			sql.append( "and rm.codempcl=? and rm.codfilialcl=? and rm.codcli=? " );
		}

		if ( !"".equals( txtCodSecao.getVlrString() ) ) {
			sql.append( "and pd.codempsc=? and pd.codfilialsc=? and pd.codsecao=? " );
		}

		sql.append( "order by pd.refprod " );

		try {

			System.out.println( "SQL:" + sql.toString() );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQCOLETA" ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			if ( txtCodCli.getVlrInteger() > 0 ) {
				ps.setInt( param++, lcCliente.getCodEmp() );
				ps.setInt( param++, lcCliente.getCodFilial() );
				ps.setInt( param++, txtCodCli.getVlrInteger() );

				sCab2.append( "Cliente: " + txtRazCli.getVlrString() + "\n" );
			}

			if ( !"".equals( txtCodSecao.getVlrString() ) ) {
				ps.setInt( param++, lcSecao.getCodEmp() );
				ps.setInt( param++, lcSecao.getCodFilial() );
				ps.setString( param++, txtCodSecao.getVlrString() );

				sCab2.append( "Se��o: " + txtDescSecao.getVlrString() );
			}

			System.out.println( sql.toString() );

			rs = ps.executeQuery();

		} catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados da coleta" );

		}

		imprimirGrafico( visualizar, rs, "", comref );

	}
	


	public void imprimirDiarioPorClienteData( boolean visualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		StringBuffer sCab2 = new StringBuffer();

		int param = 1;

		try {

			sql.append( "select " );
			sql.append( "se.codsecao, se.descsecao, rm.dtent, rm.hins, rm.dtprevret, it.qtditrecmerc, pd.codprod, pd.refprod, " );
			sql.append( "pd.descprod, rm.ticket, cl.codcli, cl.razcli, rm.docrecmerc, vd.nomevend, mn.nomemunic, it.garantia, it.numserie, " );
			sql.append( "sr.codsetor, sr.descsetor " );
			sql.append( "from " );
			sql.append( "eqrecmerc rm " );
			sql.append( "left outer join vdcliente cl on " );
			sql.append( "cl.codemp=rm.codempcl and cl.codfilial=rm.codfilialcl and cl.codcli=rm.codcli " );
			sql.append( "left outer join sgmunicipio mn on " );
			sql.append( "mn.codpais=cl.codpais and mn.siglauf=cl.siglauf and mn.codmunic=cl.codmunic " );
			sql.append( "left outer join vdvendedor vd on " );
			sql.append( "vd.codemp=rm.codempvd and vd.codfilial=rm.codfilialvd and vd.codvend=rm.codvend " );
			sql.append( "left outer join eqitrecmerc it on " );
			sql.append( "it.codemp=rm.codemp and it.codfilial=rm.codfilial and it.ticket=rm.ticket " );
			sql.append( "left outer join eqproduto pd on " );
			sql.append( "pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod " );
			sql.append( "left outer join eqsecao se on " );
			sql.append( "se.codemp=pd.codempsc and se.codfilial=pd.codfilialsc and se.codsecao=pd.codsecao " );
			sql.append( "left outer join vdsetor sr on " );
			sql.append( "sr.codemp=cl.codempsr and sr.codfilial=cl.codfilialsr and sr.codsetor=cl.codsetor " );
			sql.append( "where " );
			sql.append( "rm.codemp=? and rm.codfilial=? and rm.dtent between ? and ? " );

			if( "N".equals( cbFinalizados.getVlrString()) ) {
				sql.append( " and it.statusitrecmerc  not in ('FN','PT') " );
			}
			
			if( "S".equals( cbSoGarantia.getVlrString()) ) {
				sql.append( " and it.garantia = 'S' " );
			}

			
			if ( txtCodCli.getVlrInteger() > 0 ) {
				sql.append( "and rm.codempcl=? and rm.codfilialcl=? and rm.codcli=? " );
			}

			if ( !"".equals( txtCodSecao.getVlrString() ) ) {
				sql.append( "and pd.codempsc=? and pd.codfilialsc=? and pd.codsecao=? " );
			}

		//	sql.append( "group by 1,2,3,4 " );

			if( "C".equals(rgTipo.getVlrString()) ) {			
				sql.append( "order by rm.codcli, rm.ticket, pd.refprod " );
			}
			else if( "D".equals(rgTipo.getVlrString()) ) {
				sql.append( "order by pd.refprod " );
			}
			else if( "R".equals(rgTipo.getVlrString()) ) {
				sql.append( "order by sr.codsetor, cl.codcli, pd.refprod " );
			}

			
			ps = con.prepareStatement( sql.toString() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, Aplicativo.iCodFilial );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			sCab.append( "Per�odo de " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) + " at� " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) );

			if ( txtCodCli.getVlrInteger() > 0 ) {
				ps.setInt( param++, lcCliente.getCodEmp() );
				ps.setInt( param++, lcCliente.getCodFilial() );
				ps.setInt( param++, txtCodCli.getVlrInteger() );

				sCab2.append( "Cliente: " + txtRazCli.getVlrString() + "\n" );
			}

			if ( !"".equals( txtCodSecao.getVlrString() ) ) {
				ps.setInt( param++, lcSecao.getCodEmp() );
				ps.setInt( param++, lcSecao.getCodFilial() );
				ps.setString( param++, txtCodSecao.getVlrString() );

				sCab2.append( "Se��o: " + txtDescSecao.getVlrString() );
			}

			rs = ps.executeQuery();

			imprimirGrafico( visualizar, rs, sCab.toString() + "\n" + sCab2.toString(), comref );

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
	
	public void imprimirDiarioPorSecao( boolean visualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		StringBuffer sCab2 = new StringBuffer();

		int param = 1;

		try {

			sql.append( "select " );
			sql.append( "se.codsecao, se.descsecao, rm.dtent, rm.hins, rm.dtprevret, it.qtditrecmerc, pd.codprod, pd.refprod, " );
			sql.append( "pd.descprod, rm.ticket, cl.codcli, cl.razcli, it.numserie , " );

			sql.append( "pd.descprod, rm.ticket, it.garantia " );

			sql.append( "from " );
			sql.append( "eqrecmerc rm " );

			sql.append( "left outer join vdcliente cl on " );
			sql.append( "cl.codemp=rm.codempcl and cl.codfilial=rm.codfilialcl and cl.codcli=rm.codcli " );

			sql.append( "left outer join eqitrecmerc it on " );
			sql.append( "it.codemp=rm.codemp and it.codfilial=rm.codfilial and it.ticket=rm.ticket " );

			sql.append( "left outer join eqproduto pd on " );
			sql.append( "pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod " );

			sql.append( "left outer join eqsecao se on " );
			sql.append( "se.codemp=pd.codempsc and se.codfilial=pd.codfilialsc and se.codsecao=pd.codsecao " );

			sql.append( "where " );
			sql.append( "rm.codemp=? and rm.codfilial=? and rm.dtent between ? and ? " );

			if ( txtCodCli.getVlrInteger() > 0 ) {
				sql.append( "and rm.codempcl=? and rm.codfilialcl=? and rm.codcli=? " );
			}

			if ( !"".equals( txtCodSecao.getVlrString() ) ) {
				sql.append( "and pd.codempsc=? and pd.codfilialsc=? and pd.codsecao=? " );
			}
			
			if( "N".equals( cbFinalizados.getVlrString()) ) {
				sql.append( " and it.statusitrecmerc not in ('FN','PT') " );
			}
			
			if( "S".equals( cbSoGarantia.getVlrString()) ) {
				sql.append( " and it.garantia = 'S' " );
			}



		//	sql.append( "group by 1,2,3,4 " );

			sql.append( "order by pd.codsecao, rm.dtent, rm.codcli, pd.refprod " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, Aplicativo.iCodFilial );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			sCab.append( "Per�odo de " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) + " at� " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) );

			if ( txtCodCli.getVlrInteger() > 0 ) {
				ps.setInt( param++, lcCliente.getCodEmp() );
				ps.setInt( param++, lcCliente.getCodFilial() );
				ps.setInt( param++, txtCodCli.getVlrInteger() );

				sCab2.append( "Cliente: " + txtRazCli.getVlrString() + "\n" );
			}

			if ( !"".equals( txtCodSecao.getVlrString() ) ) {
				ps.setInt( param++, lcSecao.getCodEmp() );
				ps.setInt( param++, lcSecao.getCodFilial() );
				ps.setString( param++, txtCodSecao.getVlrString() );

				sCab2.append( "Se��o: " + txtDescSecao.getVlrString() );
			}

			rs = ps.executeQuery();

			imprimirGrafico( visualizar, rs, sCab.toString() + "\n" + sCab2.toString(), comref );

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

	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab, final boolean bComRef ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();
		hParam.put( "COMREF", bComRef ? "S" : "N" );

		FPrinterJob dlGr = null;

		if("S".equals(rgTipo.getVlrString())) {
			if ( diario ) {
				dlGr = new FPrinterJob( "layout/rel/REL_COLETA_01.jasper", "Rela��o de Coletas", sCab, rs, hParam, this );
			}
			else if ( cliente ) {
				dlGr = new FPrinterJob( "layout/rel/REL_COLETA_CLI.jasper", "Rela��o de Coletas por Cliente", sCab, rs, hParam, this );
			}
		}
		else if("C".equals(rgTipo.getVlrString())) {
			dlGr = new FPrinterJob( "layout/col/COL_PD.jasper", "Coleta", sCab, rs, hParam, this );
		}
		else if("D".equals(rgTipo.getVlrString())) {
			dlGr = new FPrinterJob( "layout/rel/REL_COLETA_GERAL.jasper", "Rela��o de Coletas", sCab, rs, hParam, this );
		}
		else if("R".equals(rgTipo.getVlrString())) {
			dlGr = new FPrinterJob( "layout/rel/REL_COLETA_SETOR.jasper", "Rela��o de Coletas", sCab, rs, hParam, this );
		}
		

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de coletas!" + err.getMessage(), true, con, err );
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

		lcCliente.setConexao( cn );

		comref = comRef();
	}
}
