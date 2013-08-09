/**
 * @version 02/08/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gms.view.frame.report <BR>
 *         Classe:
 * @(#)FRPesoRecMerc.java <BR>
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
 * Tela para filtros do relat�rio de peso de recebimentos de mercadorias.
 * 
 */

package org.freedom.modulos.gms.view.frame.report;

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
import org.freedom.library.type.TYPE_PRINT;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.view.frame.crud.plain.FAlmox;
import org.freedom.modulos.std.view.frame.crud.tabbed.FFornecedor;
import org.freedom.modulos.std.view.frame.crud.tabbed.FTransp;

public class FRPesoRecMerc extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private ListaCampos lcTransp = new ListaCampos( this );

	private ListaCampos lcForneced = new ListaCampos( this );
	
	private ListaCampos lcProduto = new ListaCampos( this );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private ListaCampos lcAlmox = new ListaCampos( this );
	
	private JTextFieldPad txtCodAlmox = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescAlmox = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );


	public FRPesoRecMerc() {

		setTitulo( "Rela��o de recebimento de mercadoria" );
		setAtribos( 80, 80, 380, 360 );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );

		// Transportadora
		lcTransp.add( new GuardaCampo( txtCodTran, "CodTran", "C�d.Tran.", ListaCampos.DB_PK, false ) );
		lcTransp.add( new GuardaCampo( txtNomeTran, "NomeTran", "Nome do transportador", ListaCampos.DB_SI, false ) );
		txtCodTran.setTabelaExterna( lcTransp, FTransp.class.getCanonicalName() );
		txtCodTran.setNomeCampo( "CodTran" );
		txtCodTran.setFK( true );
		lcTransp.setReadOnly( true );
		lcTransp.montaSql( false, "TRANSP", "VD" );

		//Fornecedores
		lcForneced.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.For.", ListaCampos.DB_PK, false ) );
		lcForneced.add( new GuardaCampo( txtNomeFor, "NomeFor", "Nome do fornecedor", ListaCampos.DB_SI, false ) );
		txtCodFor.setTabelaExterna( lcForneced, FFornecedor.class.getCanonicalName() );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		lcForneced.setReadOnly( true );
		lcForneced.montaSql( false, "FORNECED", "CP" );
		
		// * Almoxarifado (
		
		lcAlmox.add( new GuardaCampo( txtCodAlmox, "CodAlmox", "C�d.almox.", ListaCampos.DB_PK, false ) );
		lcAlmox.add( new GuardaCampo( txtDescAlmox, "DescAlmox", "Descri��o do almoxarifado", ListaCampos.DB_SI, false ) );
		lcAlmox.montaSql( false, "ALMOX", "EQ" );
		lcAlmox.setReadOnly( true );
		lcAlmox.setQueryCommit( false );
		
		txtCodAlmox.setTabelaExterna( lcAlmox, FAlmox.class.getCanonicalName() );
		txtCodAlmox.setNomeCampo( "CodAlmox" );
		txtCodAlmox.setFK( true );
		
		// * Produto (

		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, false ) );
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );

		txtCodProd.setTabelaExterna( lcProduto, FProduto.class.getCanonicalName() );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );

		lcProduto.setReadOnly( true );
		lcProduto.montaSql( false, "PRODUTO", "EQ" );
		
		JPanelPad pnPeriodo = new JPanelPad();
		pnPeriodo.setBorder( SwingParams.getPanelLabel( "Per�odo", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnPeriodo, 4, 5, 325, 60 );

		pnPeriodo.adic( new JLabelPad( "De:" ), 5, 05, 30, 20 );
		pnPeriodo.adic( txtDataini, 35, 05, 90, 20 );
		pnPeriodo.adic( new JLabelPad( "At�:" ), 135, 05, 30, 20 );
		pnPeriodo.adic( txtDatafim, 170, 05, 90, 20 );
		
		JPanelPad pnFiltros = new JPanelPad();
		pnFiltros.setBorder( SwingParams.getPanelLabel( "Filtros", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnFiltros, 4, 75, 325, 200 );

		pnFiltros.adic( new JLabelPad( "C�d.Tran." ), 4, 5, 70, 20 );
		pnFiltros.adic( txtCodTran, 4, 25, 70, 20 );

		pnFiltros.adic( new JLabelPad( "Nome do transportador" ), 77, 5, 230, 20 );
		pnFiltros.adic( txtNomeTran, 77, 25, 230, 20 );

		pnFiltros.adic( new JLabelPad( "C�d.For." ), 4, 45, 70, 20 );
		pnFiltros.adic( txtCodFor, 4, 65, 70, 20 );

		pnFiltros.adic( new JLabelPad( "Nome do fornecedor" ), 77, 45, 230, 20 );
		pnFiltros.adic( txtNomeFor, 77, 65, 230, 20 );
		
		pnFiltros.adic( new JLabelPad( "C�d.Prod." ), 4, 85, 70, 20 );
		pnFiltros.adic( txtCodProd, 4, 105, 70, 20 );

		pnFiltros.adic( new JLabelPad( "Descri��o do produto" ), 77, 85, 230, 20 );
		pnFiltros.adic( txtDescProd, 77, 105, 230, 20 );
		
		pnFiltros.adic( new JLabelPad( "C�d.Almox." ), 4, 125, 70, 20 );
		pnFiltros.adic( txtCodAlmox, 4, 145, 70, 20 );

		pnFiltros.adic( new JLabelPad( "Descri��o do almoxarifado" ), 77, 125, 230, 20 );
		pnFiltros.adic( txtDescAlmox, 77, 145, 230, 20 ); 

	}

	public void imprimir( TYPE_PRINT visualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		StringBuffer sCab = new StringBuffer();		

		int param = 1;

		sql.append( "select ");
		sql.append( "rm.codfor, fr.nomefor, tr.nometran, rm.placaveiculo, rm.dtent, rm.desconto, ");
		sql.append( "br.nomebairro, rm.ticket, br.vlrfrete preco, ");
		sql.append( "(select first 1 coalesce(ra.pesoamost,0) from eqrecamostragem ra ");
		sql.append( "where ra.codemp=rm.codemp and ra.codfilial=rm.codfilial and rm.ticket=ra.ticket ");
		sql.append( "order by ra.coditrecmerc, ra.codamostragem ");
		sql.append( ") ");
		sql.append( "- ");
		sql.append( "(select first 1 coalesce(ra.pesoamost, 0) from eqrecamostragem ra ");
		sql.append( "where ra.codemp=rm.codemp and ra.codfilial=rm.codfilial and rm.ticket=ra.ticket ");
		sql.append( "order by ra.coditrecmerc desc, ra.codamostragem desc ");
		sql.append( ") peso, rm.mediaamostragem ");
		sql.append( "from ");
		sql.append( "eqrecmerc rm ");
		sql.append( "left outer join vdtransp tr on ");
		sql.append( "tr.codemp=rm.codemptn and tr.codfilial=rm.codfilialtn and tr.codtran=rm.codtran ");
		sql.append( "left outer join cpforneced fr on ");
		sql.append( "fr.codemp=rm.codempfr and fr.codfilial=rm.codfilialfr and fr.codfor=rm.codfor ");
		sql.append( "left outer join sgbairro br on ");
		sql.append( "br.codpais=rm.codpais and br.siglauf=rm.siglauf and br.codmunic=rm.codmunic and br.codbairro=rm.codbairro ");
		sql.append( "where ");
		sql.append( "rm.codemp=? and rm.codfilial=? and rm.dtent between ? and ? ");

		if ( txtCodTran.getVlrInteger() > 0 ) {
			sql.append( "and rm.codemptn=? and rm.codfilialtn=? and rm.codtran=? " );
		}
		if ( txtCodFor.getVlrInteger() > 0 ) {
			sql.append( "and rm.codempfr=? and rm.codfilialfr=? and rm.codfor=? " );
		}
		if ( txtCodProd.getVlrInteger() > 0 ) {
			sql.append( "and rm.codemppd=? and rm.codfilialpd=? and rm.codprod=? " );
		}
		if ( txtCodAlmox.getVlrInteger() > 0 ) {
			sql.append( "and rm.codempax=? and rm.codfilialax=? and rm.codalmox=? " );
		}


		sql.append( " order by rm.dtent,rm.ticket " );

		try {

			System.out.println( "SQL:" + sql.toString() );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			if ( txtCodTran.getVlrInteger() > 0 ) {
				ps.setInt( param++, lcTransp.getCodEmp() );
				ps.setInt( param++, lcTransp.getCodFilial() );
				ps.setInt( param++, txtCodTran.getVlrInteger() );

				sCab.append( "Transportador: " + txtNomeTran.getVlrString() + "\n" );
			}

			if ( txtCodFor.getVlrInteger() > 0 ) {
				ps.setInt( param++, lcForneced.getCodEmp() );
				ps.setInt( param++, lcForneced.getCodFilial() );
				ps.setInt( param++, txtCodFor.getVlrInteger() );

				sCab.append( "Fornecedor: " + txtNomeFor.getVlrString() + "\n" );
			}
			if ( txtCodProd.getVlrInteger() > 0 ) {
				
				ps.setInt( param++, lcProduto.getCodEmp() );
				ps.setInt( param++, lcProduto.getCodFilial() );
				ps.setInt( param++, txtCodProd.getVlrInteger() );
				
				sCab.append( "Produto: " + txtDescProd.getVlrString() + "\n" );				
			}
			if ( txtCodAlmox.getVlrInteger() > 0 ) {
				
				ps.setInt( param++, lcAlmox.getCodEmp() );
				ps.setInt( param++, lcAlmox.getCodFilial() );
				ps.setInt( param++, txtCodAlmox.getVlrInteger() );
				
				sCab.append( "Almoxarifado: " + txtDescAlmox.getVlrString() + "\n" );				
			}

			
			System.out.println( "SQL:" + sql.toString() );

			rs = ps.executeQuery();

		} 
		catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados das entradas." );

		}
 
		imprimirGrafico( visualizar, rs, sCab.toString() );

	}

	public void imprimirGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final String sCab ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();

		FPrinterJob dlGr = null;

		dlGr = new FPrinterJob( "layout/rel/REL_RECMERC_01.jasper", "Relat�rio de entradas", sCab, rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcTransp.setConexao( cn );
		lcForneced.setConexao( cn );
		lcProduto.setConexao( cn );
		lcAlmox.setConexao( cn );

	}
}
