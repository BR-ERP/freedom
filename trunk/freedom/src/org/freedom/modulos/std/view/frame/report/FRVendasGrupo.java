/**
 * @version 16/11/2012 <BR>
 * @author Setpoint Inform�tica Ltda./ Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FRVendasTipoCli.java <BR>
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
 *                       Tela para impress�o de relat�rio detalhado e resumo de vendas por tipo de cliente.
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

public class FRVendasGrupo extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JCheckBoxPad cbVendaCanc = new JCheckBoxPad( "Mostrar Canceladas", "S", "N" );

	private JCheckBoxPad cbMostrarGrafico = new JCheckBoxPad( "Mostrar Graf�co", "S", "N" );

	private JRadioGroup<?, ?> rgFaturados = null;

	private JRadioGroup<?, ?> rgFinanceiro = null;

	private Vector<String> vLabsFat = new Vector<String>();

	private Vector<String> vValsFat = new Vector<String>();

	private Vector<String> vLabsFin = new Vector<String>();

	private Vector<String> vValsFin = new Vector<String>();

	private JRadioGroup<?, ?> rgTipo = null;
	
	private JComboBoxPad cbTipo = null;

	private Vector<String> vLabs1 = new Vector<String>();

	private Vector<String> vVals1 = new Vector<String>();

	private ListaCampos lcCli = new ListaCampos( this, "NT" );

	private ListaCampos lcTipoCli = new ListaCampos( this, "TM" );
	
	private ListaCampos lcVend = new ListaCampos(this);

	private BigDecimal bTotalCFOP;

	private BigDecimal bTotalGeral;

	private String sLinhaFina = StringFunctions.replicate( "-", 133 );

	private String sLinhaLarga = StringFunctions.replicate( "=", 133 );

	public FRVendasGrupo()  {
		super( false );

		setTitulo( "Vendas por grupo / comissionado" );
		setAtribos( 80, 80, 413, 390 );

		vLabsFat.addElement( "Faturado" );
		vLabsFat.addElement( "N�o Faturado" );
		vLabsFat.addElement( "Ambos" );
		vValsFat.addElement( "S" );
		vValsFat.addElement( "N" );
		vValsFat.addElement( "A" );
		rgFaturados = new JRadioGroup<String, String>( 3, 1, vLabsFat, vValsFat );
		rgFaturados.setVlrString( "S" );

		vLabsFin.addElement( "Financeiro" );
		vLabsFin.addElement( "N�o Finaceiro" );
		vLabsFin.addElement( "Ambos" );
		vValsFin.addElement( "S" );
		vValsFin.addElement( "N" );
		vValsFin.addElement( "A" );
		rgFinanceiro = new JRadioGroup<String, String>( 3, 1, vLabsFin, vValsFin );
		rgFinanceiro.setVlrString( "S" );

		vLabs1.addElement( "Relat�rio resumido por grupo" );
		vLabs1.addElement( "Relat�rio resumido por vendedor" );
		vVals1.addElement( "RRG" );
		vVals1.addElement( "RRV" );

//		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs1, vVals1 );
//		rgTipo.setVlrString( "R" );
		cbTipo = new JComboBoxPad( vLabs1, vVals1, JComboBoxPad.TP_STRING, 10, 0 );
		cbTipo.setVlrString( "RRG" );
		
		
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.Cli", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Nome do Cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		txtCodCli.setTabelaExterna( lcCli, null );

		lcTipoCli.add( new GuardaCampo( txtCodTipoCli, "CodTipoCli", "C�d.tp.cli.", ListaCampos.DB_PK, false ) );
		lcTipoCli.add( new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descri��o do tipo de Cliente", ListaCampos.DB_SI, false ) );
		lcTipoCli.montaSql( false, "TIPOCLI", "VD" );
		lcTipoCli.setQueryCommit( false );
		lcTipoCli.setReadOnly( true );
		txtCodTipoCli.setNomeCampo( "CodTipoCli" );
		txtCodTipoCli.setFK( true );
		txtCodTipoCli.setTabelaExterna( lcTipoCli, null );
		
		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.Comiss.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtNomeVend, "NomeVend", "Descri��o do Comissionado", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "VD" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );
		txtCodVend.setNomeCampo( "CodVend" );
		txtCodVend.setFK( true );
		txtCodVend.setTabelaExterna( lcVend, null );
		
		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );

		adic( new JLabelPad( "Periodo:" ), 7, 5, 100, 20 );
		adic( lbLinha, 60, 15, 210, 2 );
		adic( new JLabelPad( "De:" ), 7, 30, 30, 20 );
		adic( txtDataini, 32, 30, 97, 20 );
		adic( new JLabelPad( "At�:" ), 140, 30, 30, 20 );
		adic( txtDatafim, 170, 30, 100, 20 );
		adic( new JLabelPad( "C�d.tp.cli." ), 7, 60, 210, 20 );
		adic( txtCodTipoCli, 7, 80, 70, 20 );
		adic( new JLabelPad( "Descri��o do tipo de cliente" ), 80, 60, 210, 20 );
		adic( txtDescTipoCli, 80, 80, 290, 20 );
		adic( new JLabelPad( "C�d.Comis." ), 7, 100, 210, 20 );
		adic( txtCodVend, 7, 120, 70, 20 );
		adic( new JLabelPad( "Descri��o do Comissionado" ), 80, 100, 310, 20 );
		adic( txtNomeVend, 80, 120, 290, 20 );
		adic( new JLabelPad( "C�d.Cli" ), 7, 140, 70, 20 );
		adic( txtCodCli, 7, 160, 70, 20 );
		adic( new JLabelPad( "Nome do Cliente" ), 80, 140, 310, 20 );
		adic( txtRazCli, 80, 160, 290, 20 );

		adic( cbTipo, 7, 190, 365, 20 );
		adic( rgFaturados, 7, 223, 120, 70 );
		adic( rgFinanceiro, 153, 223, 120, 70 );
		adic( cbVendaCanc, 4, 293, 143, 20 );
		adic( cbMostrarGrafico, 149, 293, 200, 20 );

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( con );
		lcTipoCli.setConexao( con );
		lcVend.setConexao( con );
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		String tipo = cbTipo.getVlrString();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		StringBuilder cab = new StringBuilder();
		
		sql.append( " select v.codvend, vv.nomevend, gp.codgrup, gp.descgrup ");
		sql.append( " , i.codnat, v.dtemitvenda, v.dtsaidavenda, v.codvenda, v.docvenda ");
		sql.append( " , c.codcli, c.razcli, c.nomecli ");
		sql.append( " , p.codplanopag, p.descplanopag ");
		sql.append( ", sum(i.vlrproditvenda) vlrproditvenda ");
		sql.append( ", sum(i.qtditvenda) qtditvenda ");
		sql.append( ", sum(i.vlrliqitvenda + i.vlrdescitvenda) vlrbruto ");
		sql.append( ", sum(i.vlrdescitvenda) vlrdesc ");
		sql.append( ", sum(i.vlrliqitvenda) vlrliq ");
		sql.append( " from vdvenda v, vditvenda i, eqproduto pd, vdcliente c, ");
		sql.append( " vdtipocli tc, eqtipomov tm, lfnatoper nt, fnplanopag p, vdvendedor vv, eqgrupo gp ");
		sql.append( " where v.codemp=? and v.codfilial=? and v.dtemitvenda between ? and ? ");
		sql.append( " and i.codemp=v.codemp and i.codfilial=v.codfilial and i.tipovenda=v.tipovenda and i.codvenda=v.codvenda ");
		sql.append( " and pd.codemp=i.codemppd and pd.codfilial=i.codfilialpd and pd.codprod=i.codprod ");
		sql.append( " and c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli ");
		sql.append( " and tc.codemp=c.codemptc and tc.codfilial=c.codfilialtc and tc.codtipocli=c.codtipocli ");
		sql.append( " and tm.codemp=v.codemptm and tm.codfilial=v.codfilialtm and tm.codtipomov=v.codtipomov ");
		sql.append( " and nt.codemp=i.codempnt and nt.codfilial=i.codfilialnt and nt.codnat=i.codnat ");
		sql.append( " and p.codemp=v.codemppg and p.codfilial=v.codfilialpg and p.codplanopag=v.codplanopag ");
		sql.append( " and vv.codemp=v.codempvd and vv.codfilial=v.codfilialvd and vv.codvend=v.codvend ");
		sql.append( " and gp.codemp=pd.codempgp and gp.codfilial=pd.codfilialgp and gp.codgrup=pd.codgrup ");

		cab.append(" ( Per�odo de " );
		cab.append( txtDataini.getVlrString() );
		cab.append( " AT� " );
		cab.append( txtDatafim.getVlrString() );
		cab.append(" ) ");

		if ( txtCodCli.getVlrInteger().intValue() > 0 )
			sql.append( " and v.codempcl=? and v.codfilialcl=? and v.codcli=? ");
		    cab.append( " ( Cliente: " );
		    cab.append( txtRazCli.getVlrString().trim());
		    cab.append( " ) ");

		if ( txtCodTipoCli.getVlrInteger().intValue() > 0 ) {
			sql.append( " and c.codemptc=? and c.codfilialtc=? and c.codtipocli=? ");
		    cab.append( " ( Tipo de cliente: " );
		    cab.append( txtDescTipoCli.getVlrString().trim());
		    cab.append( " ) ");
		}
		
		if ( txtCodVend.getVlrInteger().intValue() > 0 ) {
			sql.append( " and v.codempvd=? and v.codfilialvd=? and c.codvend=? ");
		    cab.append( " ( Vendedor: " );
		    cab.append( txtNomeVend.getVlrString().trim());
		    cab.append( " ) ");
		}
	
		if ( rgFaturados.getVlrString().equals( "S" ) ) {
			sql.append( "and tm.fiscaltipomov='S' ");
			cab.append( " ( faturados ) ");
		}
		else if ( rgFaturados.getVlrString().equals( "N" ) ) {
			sql.append( "and tm.fiscaltipomov='N' ");
			cab.append( " ( n�o faturados ) ");
		}

		if ( rgFinanceiro.getVlrString().equals( "S" ) ) {
			sql.append( "and tm.somavdtipomov='S' ");
			cab.append( " ( financeiros ) ");
		}
		else if ( rgFinanceiro.getVlrString().equals( "N" ) ) {
			sql.append( "and tm.somavdtipomov='N' ");
			cab.append( " ( n�o financeiros ) ");
		}

		if ( cbVendaCanc.getVlrString().equals( "N" ) ) {
			sql.append( " and not substring(v.statusvenda from 1 for 1)='C' ");
			cab.append( " ( sem cancelados ) ");
		}
		sql.append( " group by v.codvend, vv.nomevend, gp.codgrup, gp.descgrup ");
		sql.append( " , i.codnat, v.dtemitvenda, v.dtsaidavenda, v.codvenda, v.docvenda ");
		sql.append( " , c.codcli, c.razcli, c.nomecli ");
		sql.append( " , p.codplanopag, p.descplanopag ");
		if ("RRG".equals( tipo ) ) {
			sql.append( " order by gp.descgrup, vv.nomevend, v.dtemitvenda, c.razcli, c.nomecli ");
		} else {
			sql.append( " order by vv.nomevend, gp.descgrup, v.dtemitvenda, c.razcli, c.nomecli ");
		}

		try {
			
			int param = 1;
			
			ps = con.prepareStatement( sql.toString() );
		
		
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			
			if ( txtCodCli.getVlrInteger().intValue() > 0 ) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
				ps.setInt( param++, txtCodCli.getVlrInteger().intValue());
			}
			
			if ( txtCodTipoCli.getVlrInteger().intValue() > 0 ) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "VDTIPOCLI" ) );
				ps.setInt( param++, txtCodTipoCli.getVlrInteger().intValue());
			}
			
			if ( txtCodVend.getVlrInteger().intValue() > 0 ) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDEDOR" ) );
				ps.setInt( param++, txtCodVend.getVlrInteger().intValue());
			}
			
			rs = ps.executeQuery();	
			
			imprimeGrafico( rs, bVisualizar, cab.toString() , tipo );
			
			rs.close();
			ps.close();
			con.commit();

			
		} catch ( Exception e ) {

			Funcoes.mensagemErro( this, "Erro ao buscar dados da venda !\n" + e.getMessage() );
			e.printStackTrace();
		}

		
	}
	
	
	public void imprimeGrafico( final ResultSet rs, final TYPE_PRINT bVisualizar, final String sCab, final String tipo ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "VDVENDA" ) );
		hParam.put( "FILTROS", sCab );
		hParam.put( "RESUMO", cbMostrarGrafico.getVlrString() );
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/" );
		hParam.put( "CONEXAO", con.getConnection() );
		//hParam.put( "sqlTable", sqlSubTxt );

		String rel = null;
		if ("RRG".equals( tipo )) {
			rel = "relatorios/vendasgrupos_rel_grup_res.jasper";
		} else {
			rel = "relatorios/vendasgrupos_rel_vend_res.jasper";
		}

		FPrinterJob dlGr = new FPrinterJob( rel, "Vendas por grupos", null, rs, hParam, this );
		

		if ( bVisualizar==TYPE_PRINT.VIEW ) {

			dlGr.setVisible( true );

		}
		else {
			try {

				JasperPrintManager.printReport( dlGr.getRelatorio(), true );

			} catch ( Exception err ) {

				Funcoes.mensagemErro( this, "Erro na impress�o do relat�rio de Compras por CFOP!\n" + err.getMessage(), true, con, err );
			}
		}
	}
}
