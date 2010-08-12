/**
 * @version 09/08/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gms.view.frame.report <BR>
 *         Classe:
 * @(#)FRPrecoMedioRecMerc.java <BR>
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
 * Tela para filtros do relat�rio de pre�o m�dio por carga.
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

public class FRPrecoMedioRecMerc extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private ListaCampos lcTransp = new ListaCampos( this );

	private ListaCampos lcForneced = new ListaCampos( this );

	public FRPrecoMedioRecMerc() {

		setTitulo( "Rela��o de pre�o m�dio di�rio" );
		setAtribos( 80, 80, 380, 280 );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );

		// Transportadora
		lcTransp.add( new GuardaCampo( txtCodTran, "CodTran", "C�d.Tran.", ListaCampos.DB_PK, false ) );
		lcTransp.add( new GuardaCampo( txtNomeTran, "NomeTran", "Nome do transportador", ListaCampos.DB_SI, false ) );
		txtCodTran.setTabelaExterna( lcTransp, null );
		txtCodTran.setNomeCampo( "CodTran" );
		txtCodTran.setFK( true );
		lcTransp.setReadOnly( true );
		lcTransp.montaSql( false, "TRANSP", "VD" );

		//Fornecedores
		lcForneced.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.For.", ListaCampos.DB_PK, false ) );
		lcForneced.add( new GuardaCampo( txtNomeFor, "NomeFor", "Nome do fornecedor", ListaCampos.DB_SI, false ) );
		txtCodFor.setTabelaExterna( lcForneced, null );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		lcForneced.setReadOnly( true );
		lcForneced.montaSql( false, "FORNECED", "CP" );
		
		JPanelPad pnPeriodo = new JPanelPad();
		pnPeriodo.setBorder( SwingParams.getPanelLabel( "Per�odo", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnPeriodo, 4, 5, 325, 60 );

		pnPeriodo.adic( new JLabelPad( "De:" ), 5, 05, 30, 20 );
		pnPeriodo.adic( txtDataini, 35, 05, 90, 20 );
		pnPeriodo.adic( new JLabelPad( "At�:" ), 135, 05, 30, 20 );
		pnPeriodo.adic( txtDatafim, 170, 05, 90, 20 );
		
		JPanelPad pnFiltros = new JPanelPad();
		pnFiltros.setBorder( SwingParams.getPanelLabel( "Filtros", Color.BLACK, TitledBorder.LEFT ) );

		adic( pnFiltros, 4, 75, 325, 120 );

		pnFiltros.adic( new JLabelPad( "C�d.Tran." ), 4, 5, 70, 20 );
		pnFiltros.adic( txtCodTran, 4, 25, 70, 20 );

		pnFiltros.adic( new JLabelPad( "Nome do transportador" ), 77, 5, 230, 20 );
		pnFiltros.adic( txtNomeTran, 77, 25, 230, 20 );

		pnFiltros.adic( new JLabelPad( "C�d.For." ), 4, 45, 70, 20 );
		pnFiltros.adic( txtCodFor, 4, 65, 70, 20 );

		pnFiltros.adic( new JLabelPad( "Nome do fornecedor" ), 77, 45, 230, 20 );
		pnFiltros.adic( txtNomeFor, 77, 65, 230, 20 );

	}

	public void imprimir( boolean visualizar ) {

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
		sql.append( "cp.dtentcompra, rm.dtent, cp.codfor, fr.nomefor, br.nomebairro, al.descalmox, ic.qtditcompra, ");
		sql.append( "rm.rendaamostragem, ic.precoitcompra, ic.vlrproditcompra ");
		sql.append( "from ");
		sql.append( "cpcompra cp left outer join cpforneced fr on ");
		sql.append( "fr.codemp=cp.codempfr and fr.codfilial=cp.codfilialfr and fr.codfor=cp.codfor ");
		sql.append( "right outer join eqrecmerc rm on ");
		sql.append( "rm.codemp=cp.codemprm and rm.codfilial=cp.codfilialrm and rm.ticket=cp.ticket ");
		sql.append( "left outer join sgbairro br on ");
		sql.append( "br.codpais=rm.codpais and br.siglauf=rm.siglauf and br.codmunic=rm.codmunic and br.codbairro=rm.codbairro ");
		sql.append( "left outer join eqalmox al on ");
		sql.append( "al.codemp=rm.codemp and al.codfilial=rm.codfilial and al.codalmox=rm.codalmox ");
		sql.append( "left outer join cpitcompra ic on ");
		sql.append( "ic.codemp=cp.codemp and ic.codfilial=cp.codfilial and ic.codcompra=cp.codcompra ");
		sql.append( "where cp.codemp=? and cp.codfilial=? and rm.dtent between ? and ? ");

		if ( txtCodTran.getVlrInteger() > 0 ) {
			sql.append( "and cp.codemptn=? and cp.codfilialtn=? and cp.codtran=? " );
		}
		if ( txtCodFor.getVlrInteger() > 0 ) {
			sql.append( "and cp.codempfr=? and cp.codfilialfr=? and cp.codfor=? " );
		}

		sql.append( " order by rm.dtent,rm.ticket " );

		try {

			System.out.println( "SQL:" + sql.toString() );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			sCab.append( "Per�odo de " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) 
					+ " at� " + Funcoes.dateToStrDate( txtDatafim.getVlrDate() )
					+ "\n" );
			
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
			
			System.out.println( "SQL:" + sql.toString() );

			rs = ps.executeQuery();

		} 
		catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados das entradas." );

		}
 
		imprimirGrafico( visualizar, rs, sCab.toString() );

	}

	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();

		FPrinterJob dlGr = null;

		dlGr = new FPrinterJob( "layout/rel/REL_RECMERC_02.jasper", "Relat�rio de pre�o m�dio/di�rio", sCab, rs, hParam, this );

		if ( bVisualizar ) {
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

	}
}
