/**
 * @version 03/04/2014 <BR>
 * @author Setpoint Tecnologia em Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.lvf.view.frame.report <BR>
 *         Classe: @(#)FRProdICMS.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Relat�rio de produtos e al�quotas de ICMS.
 * 
 */

package org.freedom.modulos.lvf.view.frame.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;
import org.freedom.modulos.cfg.view.frame.crud.plain.FUF;
import org.freedom.modulos.std.view.frame.crud.plain.FTipoFisc;

public class FRProdICMS extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtSiglaUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldFK txtNomeUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodFiscCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescFiscCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JCheckBoxPad cbSoST = new JCheckBoxPad( "Somente produtos com ST", "S", "N" );

	private JCheckBoxPad cbSoSaidas = new JCheckBoxPad( "Somente regras de sa�da", "S", "N" );
	
	private JCheckBoxPad cbSoProdVenda = new JCheckBoxPad( "Somente produtos de venda", "S", "N" );

	private ListaCampos lcUF = new ListaCampos( this );

	private ListaCampos lcTipoFiscCli = new ListaCampos( this );

	public FRProdICMS() {

		setTitulo( "Produtos/ICMS" );
		setAtribos( 100, 100, 500, 250 );

		montaListaCampos();
		montaTela();

	}

	private void montaTela() {
		adic( new JLabelPad( "UF" ), 7, 0, 50, 20 );
		adic( txtSiglaUF, 7, 20, 50, 20 );
		adic( new JLabelPad( "Nome UF" ), 60, 0, 250, 20 );
		adic( txtNomeUF, 60, 20, 250, 20 );
		adic( new JLabelPad( "C�d.fiscal" ), 7, 40, 70, 20 );
		adic( txtCodFiscCli, 7, 60, 70, 20 );
		adic( new JLabelPad( "Descri��o" ), 80, 40, 250, 20 );
		adic( txtDescFiscCli, 80, 60, 250, 20 );
		adic( cbSoST, 7, 85, 250, 25 );
		adic( cbSoSaidas, 7, 110, 250, 25 );
		adic( cbSoProdVenda, 7, 135, 250, 25 );
		cbSoST.setVlrString( "S" );
		cbSoSaidas.setVlrString( "S" );
		cbSoProdVenda.setVlrString( "S" );
	}

	private void montaListaCampos() {
		/***************
		 * UF *
		 **************/
		lcUF.setUsaME( false );
		lcUF.add( new GuardaCampo( txtSiglaUF, "SiglaUf", "Sigla", ListaCampos.DB_PK, true ) );
		lcUF.add( new GuardaCampo( txtNomeUF, "NomeUf", "Nome", ListaCampos.DB_SI, false ) );
		lcUF.add( new GuardaCampo( txtCodPais, "Codpais", "C�d.pa�s", ListaCampos.DB_PK, true ) );
		lcUF.setDinWhereAdic( "codpais=#N", txtCodPais );
		lcUF.montaSql( false, "UF", "SG" );
		lcUF.setQueryCommit( false );
		lcUF.setReadOnly( true );
		txtSiglaUF.setNomeCampo( "SiglaUf" );
		txtSiglaUF.setFK( true );
		txtSiglaUF.setTabelaExterna( lcUF, FUF.class.getCanonicalName() );
		/***************
		 * Tipo fiscal *
		 **************/
		lcTipoFiscCli.setUsaME( false );
		lcTipoFiscCli.add( new GuardaCampo( txtCodFiscCli, "CodFiscCli", "C�d.fiscal", ListaCampos.DB_PK, true ) );
		lcTipoFiscCli.add( new GuardaCampo( txtDescFiscCli, "DescFiscCli", "Descri��o", ListaCampos.DB_SI, false ) );
		lcTipoFiscCli.montaSql( false, "TIPOFISCCLI", "LF" );
		lcTipoFiscCli.setQueryCommit( false );
		lcTipoFiscCli.setReadOnly( true );
		txtCodFiscCli.setNomeCampo( "CodFiscCli" );
		txtCodFiscCli.setFK( true );
		txtCodFiscCli.setTabelaExterna( lcTipoFiscCli, FTipoFisc.class.getCanonicalName() );

	}

	private int getCodpais() {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("select codpais from sgfilial f where f.codemp=? and f.codfilial=?");
		try {
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGFILIAL" ) );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt( "codpais" );
			}
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro carregando c�idog do pais.\n" + e.getMessage() );
			try {
				con.rollback();
			} catch( SQLException rerr) {
				rerr.printStackTrace();
			}
		}
		return result;
	}
	
	public void imprimir( TYPE_PRINT bVisualizar ) {

		imprimeGrafico( bVisualizar );
	}

	
	private void imprimeGrafico(final TYPE_PRINT visualizar ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		StringBuilder filtros = new StringBuilder();
		int param = 1;

		try {

			sql.append( "select pd.codprod, pd.descprod, cf.codncm,  icf.siglauf ");
			sql.append(", tfc.codfisccli, tfc.descfisccli, icf.margemvlagr, icf.aliqfiscintra, icf.aliqfisc ");
			sql.append(" from eqproduto pd ");
			sql.append(" inner join lfclfiscal cf ");
			sql.append(" on cf.codemp=pd.codempfc and cf.codfilial=pd.codfilialfc and cf.codfisc=pd.codfisc ");
			sql.append(" inner join lfitclfiscal icf ");
			sql.append(" on icf.codemp=cf.codemp and icf.codfilial=cf.codfilial and icf.codfisc=cf.codfisc ");
			sql.append(" inner join lftipofisccli tfc ");
			sql.append(" on tfc.codemp=icf.codempfc and tfc.codfilial=icf.codfilialfc and tfc.codfisccli=icf.codfisccli ");
			sql.append(" where pd.codemp=? and pd.codfilial=? ");
			if (!"".equals( txtSiglaUF.getVlrString().trim())) {
				sql.append(" and icf.siglauf=? ");
				filtros.append( " ( UF: " );
				filtros.append(txtSiglaUF.getVlrString());
				filtros.append( " - ");
				filtros.append(txtNomeUF.getVlrString());
				filtros.append( " ) ");
			}
			if (!"".equals( txtCodFiscCli.getVlrString().trim())) {
				sql.append(" and icf.codempfc=? and icf.codfilialfc=? and icf.codfisccli=? ");
				filtros.append( " ( Tipo fiscal: " );
				filtros.append(txtCodFiscCli.getVlrString());
				filtros.append( " - ");
				filtros.append(txtDescFiscCli.getVlrString());
				filtros.append( " ) ");
			}
			if ("S".equals(cbSoST.getVlrString())) {
				sql.append(" and icf.tipofisc='FF' ");
				filtros.append( " ( " );
				filtros.append(cbSoST.getText());
				filtros.append( " ) ");
			}
			if ("S".equals(cbSoSaidas.getVlrString())) {
				sql.append(" and icf.tipousoitfisc='VD' ");
				filtros.append( " ( " );
				filtros.append(cbSoSaidas.getText());
				filtros.append( " ) ");
			}
			if ("S".equals(cbSoProdVenda.getVlrString())) {
				sql.append(" and pd.cvprod in ('V','A') ");
				filtros.append( " ( " );
				filtros.append(cbSoProdVenda.getText());
				filtros.append( " ) ");
			}
			sql.append(" and icf.ativoitfisc='S' ");
			sql.append(" order by pd.descprod, pd.codprod, icf.siglauf, tfc.codfisccli" );
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			if (!"".equals( txtSiglaUF.getVlrString().trim())) {
				ps.setString( param++, txtSiglaUF.getVlrString() );
			}
			if (!"".equals( txtCodFiscCli.getVlrString().trim())) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "LFTIPOFISCCLI" ) );
				ps.setInt( param++, txtCodFiscCli.getVlrInteger() );
			}
			rs = ps.executeQuery();
			
			String pathReportFile = "relatorios/ProdICMS.jasper";

			FPrinterJob dlGr = null;
			HashMap<String, Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "VDVENDA" ) );
			hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
			hParam.put( "FILTROS", filtros.toString() );

			dlGr = new FPrinterJob( pathReportFile, "Relat�rio de PRODUTOS/ICMS ", filtros.toString(), rs, hParam, this );

			if ( visualizar==TYPE_PRINT.VIEW ) {
				dlGr.setVisible( true );
			}
			else {
				try {
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );
				} catch ( Exception err ) {
					Funcoes.mensagemErro( this, "Erro na impress�o do relat�rio!" + err.getMessage(), true, con, err );
				}
			}
		} catch (SQLException e) {
			Funcoes.mensagemErro( this, "Erro executando a consulta !\n"+e.getMessage() );
		} finally {
			sql = null;
			filtros = null;
			ps = null;
			rs = null;
			System.gc();
		}
	}
	
	@ Override
	public void setConexao( DbConnection cn ) {
	
		super.setConexao( cn );
		lcUF.setConexao( cn );
		lcTipoFiscCli.setConexao( cn );
		txtCodPais.setVlrInteger( getCodpais() );
	}
}
