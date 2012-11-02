/**
 * @version 19/11/2009 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FRGiroEstoque.java <BR>
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
 *         Classe para filtros e carregamento de relat�rio de valores em estoque.
 * 
 */

package org.freedom.modulos.gms.view.frame.report;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.modulos.std.view.dialog.utility.DLAltFatLucro;
import org.freedom.modulos.std.view.frame.crud.plain.FClasCli;

public class FRValorEstoque extends FRelatorio {

	private static final long serialVersionUID = 1L;

	// private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTabPreco = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );

	private JTextFieldPad txtDescTabPreco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JLabelPad lbCodGrup = new JLabelPad( "C�d.grupo" );

	private JLabelPad lbDescGrup = new JLabelPad( "Descri��o do grupo" );

	private JLabelPad lbCodTabPreco = new JLabelPad( "C�d.Tab." );

	private JLabelPad lbDescTabPreco = new JLabelPad( "Descri��o da tabela de pre�os" );

	private JLabelPad lbCodPlanoPag1 = new JLabelPad( "C�d.Pag." );

	private JLabelPad lbDescPlanoPag1 = new JLabelPad( "Descri��o do Plano de pagamento" );

	private JTextFieldPad txtCodPlanoPag1 = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescPlanoPag1 = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtDtEstoq = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JRadioGroup<?, ?> rgOrdem = null;

	private JRadioGroup<?, ?> rgAtivo  = null;

	private JCheckBoxPad cbImportacao = new JCheckBoxPad( "Somente importa��o", "S", "N" );

	private Vector<String> vLabs = new Vector<String>( 2 );

	private Vector<String> vVals = new Vector<String>( 2 );

	private Vector<String> vLabsAtivo = new Vector<String>( 3 );

	private Vector<String> vValsAtivo = new Vector<String>( 3 );

	private ListaCampos lcGrup = new ListaCampos( this );

	private ListaCampos lcTabPreco = new ListaCampos( this );

	private ListaCampos lcPlanoPag1 = new ListaCampos( this );

	private HashMap<String, Object> preferencias = null;
	
	private JTextFieldPad txtCodClas = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescClas = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private BigDecimal fatluc = new BigDecimal( 1 );
	
	private ListaCampos lcClas = new ListaCampos( this );

	public FRValorEstoque() {

		setTitulo( "Valor em estoque" );

		setAtribos( 140, 40, 350, 400 );

		vLabs.addElement( "C�digo" );
		vLabs.addElement( "Descri��o" );

		vVals.addElement( "C" );
		vVals.addElement( "D" );

		rgOrdem = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgOrdem.setVlrString( "D" );

		vLabsAtivo.addElement( "Ativo" );
		vLabsAtivo.addElement( "Inativos" );
		vLabsAtivo.addElement( "Ambos" );

		vValsAtivo.addElement( "('S')" );
		vValsAtivo.addElement( "('N')" );
		vValsAtivo.addElement( "('S','N')" );

		rgAtivo = new JRadioGroup<String, String>( 1, 2, vLabsAtivo, vValsAtivo );
		rgAtivo.setVlrString( "('S')" );
		
		lcGrup.add( new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, false ) );
		lcGrup.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false ) );
		lcGrup.montaSql( false, "GRUPO", "EQ" );
		lcGrup.setReadOnly( true );
		txtCodGrup.setTabelaExterna( lcGrup, null );
		txtCodGrup.setFK( true );
		txtCodGrup.setNomeCampo( "CodGrup" );

		lcTabPreco.add( new GuardaCampo( txtCodTabPreco, "CodTab", "C�d.tab.pc.", ListaCampos.DB_PK, false ) );
		lcTabPreco.add( new GuardaCampo( txtDescTabPreco, "DescTab", "Descri��o da tabela de pre�o", ListaCampos.DB_SI, false ) );
		txtCodTabPreco.setTabelaExterna( lcTabPreco, null );
		txtCodTabPreco.setNomeCampo( "CodTab" );
		txtCodTabPreco.setFK( true );
		lcTabPreco.setReadOnly( true );
		lcTabPreco.montaSql( false, "TABPRECO", "VD" );

		lcPlanoPag1.add( new GuardaCampo( txtCodPlanoPag1, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag1.add( new GuardaCampo( txtDescPlanoPag1, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag1.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag1.setReadOnly( true );
		txtCodPlanoPag1.setTabelaExterna( lcPlanoPag1, null );
		txtCodPlanoPag1.setFK( true );
		txtCodPlanoPag1.setNomeCampo( "CodPlanoPag" );
		
		lcClas.add( new GuardaCampo( txtCodClas, "CodClasCli", "C�d.c.cli.", ListaCampos.DB_PK, false ) );
		lcClas.add( new GuardaCampo( txtDescClas, "DescClasCli", "Descri��o da classifica��o do cliente", ListaCampos.DB_SI, false ) );
		lcClas.montaSql( false, "CLASCLI", "VD" );
		lcClas.setReadOnly( true );
		txtCodClas.setTabelaExterna( lcClas, FClasCli.class.getCanonicalName() );
		txtCodClas.setFK( true );
		txtCodClas.setNomeCampo( "CodClasCli" );
		
		adic( lbCodGrup, 7, 10, 250, 20 );
		adic( txtCodGrup, 7, 30, 80, 20 );

		adic( lbDescGrup, 90, 10, 250, 20 );
		adic( txtDescGrup, 90, 30, 216, 20 );

		adic( lbCodTabPreco, 7, 50, 250, 20 );
		adic( txtCodTabPreco, 7, 70, 80, 20 );

		adic( lbDescTabPreco, 90, 50, 250, 20 );
		adic( txtDescTabPreco, 90, 70, 216, 20 );

		adic( lbCodPlanoPag1, 7, 90, 250, 20 );
		adic( txtCodPlanoPag1, 7, 110, 80, 20 );

		adic( lbDescPlanoPag1, 90, 90, 250, 20 );
		adic( txtDescPlanoPag1, 90, 110, 216, 20 );
		
		adic( txtCodClas, 7, 190, 80, 20,"C�d.Clas." );
		adic( txtDescClas, 90, 190, 216, 20, "Descri��o da classifica��o do cliente" );

		adic( rgOrdem, 7, 140, 300, 30 );

		adic( cbImportacao,7, 210, 300, 30 );

		adic( rgAtivo, 7, 240, 300, 30 );

		adic( txtDtEstoq, 7, 290, 120, 20,"Estoque em" );

		txtCodTabPreco.setVlrInteger( 1 );
		txtCodPlanoPag1.setVlrInteger( 1 );
		txtCodClas.setVlrString( "1" );
		txtDtEstoq.setVlrDate( new Date());
		cbImportacao.setVlrString( "N" );

	}

	private HashMap<String, Object> getPreferencias() {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			sql.append( "SELECT TIPOCUSTOLUC, CODTIPOMOVIM " );
			sql.append( "FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				retorno.put( "tipocusto", rs.getString( "TIPOCUSTOLUC" ) ); // U - Ultima compra, M - MPM, P - PEPS, I - Informado
				retorno.put( "codtipomov", rs.getInt( "CODTIPOMOVIM" ) ); // Retorna tipo de movimento padr�o para importa��o.

			}

			rs.close();

			ps.close();

			con.commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
		}
		return retorno;
	}

	public void imprimir( boolean bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		StringBuilder status = new StringBuilder();
		StringBuilder filtros = new StringBuilder();

		try {

			preferencias = getPreferencias();

			String tipocusto = (String) preferencias.get( "tipocusto" );
			
			Integer codtipomov = (Integer) preferencias.get( "codtipomov" );

			String campocusto = "ncusto";

			if ( "I".equals( tipocusto ) ) {
				campocusto = campocusto + "info";
			}
			else if ( "U".equals( tipocusto ) ) {
				campocusto = campocusto + "uc";
			}
			else if ( "P".equals( tipocusto ) ) {
				campocusto = campocusto + "peps";
			}
			else {
				campocusto = campocusto + "mpm";
			}

			sql.append( "select p.codemp, p.codfilial, p.codprod, p.codfabprod, p.refprod, p.codbarprod, p.descprod " );
			sql.append( ", mp.sldmovprod sldprod " );
			/*
			ALTER PROCEDURE EQCUSTOPRODSP (ICODEMP INTEGER,
					SCODFILIAL SMALLINT,
					ICODPROD INTEGER,
					DTESTOQ DATE,
					CTIPOCUSTO CHAR(1) CHARACTER SET NONE,
					ICODEMPAX INTEGER,
					SCODFILIALAX SMALLINT,
					ICODALMOX INTEGER,
					CCOMSALDO CHAR(10) CHARACTER SET NONE)
					RETURNS (SLDPROD NUMERIC(15, 5),
					CUSTOUNIT NUMERIC(15, 5),
					CUSTOTOT NUMERIC(15, 5))
			*/
			if ( "I".equals( tipocusto ) ) {
				sql.append( ", p.custoinfoprod custo" );
				
			} else {
				sql.append(", (select custo.custounit from eqcustoprodsp(p.codprod, p.codfilial, p.codprod, mp.dtmovprod, '");
				sql.append(tipocusto);
				sql.append("', null, null, null, 'S') custo ) custo ");
			}
			 
			/**
			 *     SELECT P.NCUSTOPEPS  FROM EQCALCPEPSSP(:ICODEMP, :SCODFILIAL,
    :ICODPROD, :NSALDO, :DDTMOVPROD, null, null, null ) P
    INTO :NCUSTOPEPS;
			 * 
			 * SELECT FIRST 1 MP.DTMOVPROD, MP.SLDMOVPROD , MP.CUSTOMPMMOVPROD
		    FROM EQMOVPROD MP
		    WHERE MP.CODEMPPD=:ICODEMP AND MP.CODFILIALPD=:SCODFILIAL AND MP.CODPROD=:ICODPROD
		    ORDER BY MP.DTMOVPROD DESC, MP.CODMOVPROD DESC
		    INTO :DDTMOVPROD, :NSALDO, :NCUSTOMPM;
			*/
			
			//sql.append( "(select " );
			//sql.append( campocusto );
			//sql.append( " from eqprodutosp01(p.codemp,p.codfilial,p.codprod,null,null,null)) custo, " );
			sql.append( ", coalesce(p.qtdembalagem,1) qtdembalagem " );
			if ( txtCodTabPreco.getVlrInteger() > 0 ) {
				sql.append( " ,pp.precoprod/(coalesce(p.qtdembalagem,1)) precoprod, 'S' imppreco " );
			}
			else {
				sql.append( ",p.precobaseprod as precoprod, 'N' imppreco " );
			}
			sql.append( ", c.dtemitcompra, c.identcontainer " );
			if ( txtCodTabPreco.getVlrInteger() > 0 ) {
				sql.append( "from vdprecoprod pp, eqproduto p " );
			} else {
				sql.append( "from eqproduto p " );
			}
			sql.append( "left outer join eqmovprod mp on ");
			sql.append( "mp.codemp=? and mp.codfilial=? and mp.codemppd=p.codemp ");
			sql.append( "and mp.codfilialpd=p.codfilial and mp.codprod=p.codprod " );
			sql.append( "and mp.codmovprod = (select first 1 codmovprod from eqmovprod mp2 "); 
			sql.append( "where mp2.codemp=mp.codemp and mp2.codfilial=mp.codfilial ");
			sql.append( "and mp2.codemppd=p.codemp and mp2.codfilialpd=p.codfilial and mp2.codprod=p.codprod ");
			sql.append( "and mp2.dtmovprod <= ? ");
			sql.append( "order by mp2.dtmovprod desc, mp2.codmovprod desc ) " );
			sql.append( "left outer join cpcompra c on ");
			sql.append( "exists( select * from cpitcompra ic ");
			sql.append( "where ic.codemp=c.codemp and ic.codfilial=c.codfilial and ic.codcompra=c.codcompra and ");
			sql.append( "ic.codemppd=p.codemp and ic.codfilialpd=p.codfilial and ic.codprod=p.codprod " );
			sql.append( "and c.codcompra = (select first 1 c2.codcompra from cpcompra c2, cpitcompra ic2 ");
			sql.append( "where ");
			if( (codtipomov > 0 && codtipomov != null ) && ("S".equals( cbImportacao.getVlrString() ) )){
				sql.append( "c2.codtipomov=? and " );
			}
			sql.append( "ic2.codemp=c2.codemp and ic2.codfilial=c2.codfilial and ic2.codcompra=c2.codcompra and  " );
			sql.append( "ic2.codemppd=ic.codemppd and ic2.codfilial=ic.codfilial and ic2.codprod=ic.codprod and (c2.statuscompra= 'C3' or c2.statuscompra='C2') ");
			sql.append( "order by c2.dtemitcompra desc ) ) " );
			if ( txtCodTabPreco.getVlrInteger() > 0 ) {
				sql.append( "where p.ativoprod in ");
				sql.append( rgAtivo.getVlrString() );
				sql.append(" and pp.codemp=? and pp.codfilial=? and pp.codprod=p.codprod and " );
				sql.append( "pp.codemptb=? and pp.codfilialtb=? and pp.codtab=? and " );
				if ( txtCodPlanoPag1.getVlrInteger() > 0 ) {
					sql.append( "pp.codemppg=? and pp.codfilialpg=? and pp.codplanopag=? and " );
				}
				if ( ! "".equals( txtCodClas.getVlrString()) ) {
					sql.append( "pp.codempcc=? and pp.codfilialcc=? and pp.codclascli=? and " );
				}
				sql.append( "p.codemp=pp.codemp and p.codfilial=pp.codfilial and p.codprod=pp.codprod " );
				if ( txtCodGrup.getVlrInteger() > 0 ) {
					sql.append( " and p.codempgp=? and p.codfilialgp=? and p.codgrup=? " );
				}
			}
			else {
				sql.append( "where p.ativoprod in " );
				sql.append( rgAtivo.getVlrString() );
				if ( txtCodGrup.getVlrInteger() > 0 ) {
					sql.append( " and p.codempgp=? and p.codfilialgp=? and p.codgrup=? " );
				}
			}
			sql.append( "and (c.statuscompra= 'C3' or c.statuscompra='C2')  and mp.sldmovprod > 0 " );
			if( "S".equals( cbImportacao.getVlrString() )){
			sql.append( " and c.identcontainer is not null " );
			}
		
			if ( "C".equals( rgOrdem.getVlrString() ) ) {
				sql.append( "order by p.codprod " );
			}
			if ( "D".equals( rgOrdem.getVlrString() ) ) {
				sql.append( "order by p.descprod " );
			}

			System.out.println( "SQL:" + sql.toString() );

			PreparedStatement ps = con.prepareStatement( sql.toString() );

			int iparam = 1;
			
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "EQMOVPROD" ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDtEstoq.getVlrDate() ) );
			
			if( (codtipomov > 0 && codtipomov != null ) && ("S".equals( cbImportacao.getVlrString() ) )){
				ps.setInt( iparam++, codtipomov);
			}

			if ( txtCodTabPreco.getVlrInteger() > 0 ) {

				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, Aplicativo.iCodFilial );

				ps.setInt( iparam++, lcTabPreco.getCodEmp() );
				ps.setInt( iparam++, lcTabPreco.getCodFilial() );
				ps.setInt( iparam++, txtCodTabPreco.getVlrInteger() );

				if ( txtCodPlanoPag1.getVlrInteger() > 0 ) {

					ps.setInt( iparam++, lcPlanoPag1.getCodEmp() );
					ps.setInt( iparam++, lcPlanoPag1.getCodFilial() );
					ps.setInt( iparam++, txtCodPlanoPag1.getVlrInteger() );

				}
				if ( ! "".equals( txtCodClas.getVlrString()) ) {
					ps.setInt( iparam++, lcClas.getCodEmp() );
					ps.setInt( iparam++, lcClas.getCodFilial() );
					ps.setString( iparam++, txtCodClas.getVlrString() );
				}
			}

			if ( txtCodGrup.getVlrInteger() > 0 ) {
				ps.setInt( iparam++, lcGrup.getCodEmp() );
				ps.setInt( iparam++, lcGrup.getCodFilial() );
				ps.setString( iparam++, txtCodGrup.getVlrString() );
			}

			ResultSet rs = ps.executeQuery();
			
			System.out.println("SQL:" + sql.toString());

			HashMap<String, Object> hParam = new HashMap<String, Object>();
			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "CODFILIAL", Aplicativo.iCodFilial );
			// hParam.put( "DATA", txtDataini.getVlrDate() );
			hParam.put( "FATLUC", fatluc );
			hParam.put( "SUBREPORT_DIR", "org/freedom/layout/rel" );

			FPrinterJob dlGr = new FPrinterJob( "layout/rel/REL_VALOR_ESTOQUE.jasper", "Relat�rio de Valores em estoque", "", rs, hParam, this );

			if ( bVisualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro consultar valores em estoque!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcGrup.setConexao( cn );
		lcTabPreco.setConexao( cn );
		lcPlanoPag1.setConexao( cn );
		lcClas.setConexao( cn );
		
		lcPlanoPag1.carregaDados();
		lcTabPreco.carregaDados();
		lcClas.carregaDados();
		
	}

	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getKeyCode() == KeyEvent.VK_F12 ) {
			DLAltFatLucro dl = new DLAltFatLucro( this, fatluc );
			dl.setVisible( true );
			if ( dl.OK ) {
				fatluc = dl.getValor();
				dl.dispose();
			}

			dl.dispose();

		}

		super.keyPressed( kevt );
	}
	

}
