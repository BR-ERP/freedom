/**
 * @version 07/04/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FRReceber.java <BR>
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
 *                    Tela para filtros do relat�rio de contas a receber/pagar
 * 
 */

package org.freedom.modulos.fnc.view.frame.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
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

public class FRRecPag extends FRelatorio implements RadioGroupListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcBanco = new ListaCampos( this );

	private boolean bPref = false;

	public FRRecPag() {

		super( false );
		setTitulo( "Receber/Pagar" );
		setAtribos( 40, 50, 328, 185 );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );

		montaListaCampos();
		montaRadioGroups();
		montaTela();

	}

	private void montaListaCampos() {

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco.", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco, null );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );

	}

	private void montaRadioGroups() {

	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );

		adic( new JLabelPad( "Periodo:" ), 7, 0, 80, 20 );
		adic( lbLinha, 7, 20, 300, 40 );

		adic( new JLabelPad( "De:", SwingConstants.CENTER ), 17, 30, 30, 20 );
		adic( txtDataini, 47, 30, 80, 20 );

		adic( new JLabelPad( "At�:", SwingConstants.CENTER ), 127, 30, 30, 20 );
		adic( txtDatafim, 157, 30, 80, 20 );

		adic( new JLabelPad( "C�d.banco" ), 7, 65, 80, 20 );
		adic( txtCodBanco, 7, 85, 80, 20 );

		adic( new JLabelPad( "Descri��o do banco" ), 90, 65, 215, 20 );
		adic( txtDescBanco, 90, 85, 215, 20 );

	}

	public void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuilder sql = new StringBuilder();
		StringBuilder filtros = new StringBuilder();

		String titulo = null;

		try {

			if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
				return;
			}

			filtros.append( "Per�odo de " + txtDataini.getVlrString() + " at� " + txtDatafim.getVlrString() );

			titulo = "Contas a Receber/Pagar";

			sql.append( "select ip.codemp, ip.codfilial, ip.dtvencitpag vencimento, " );
			sql.append( "cast(sum(ip.vlrapagitpag) as numeric(15,2)) vlrapagar, cast(0 as decimal(15,2)) vlrreceber " );
			sql.append( "from fnitpagar ip, fnpagar pg " );
			sql.append( "where " );
			sql.append( "ip.codemp = ? and ip.codfilial = ? and ip.dtvencitpag between ? and ? " );
			sql.append( "and pg.codemp = ip.codemp and pg.codfilial = ip.codfilial and pg.codpag = ip.codpag " );
			sql.append( "and ip.statusitpag != 'PP' and cast(ip.vlrapagitpag as numeric(15,2)) > 0 " );

			if ( txtCodBanco.getVlrString().length() > 0 ) {
				sql.append( " and pg.codempbo=? and pg.codfilialbo=? and pg.codbanco=? " );
				filtros.append( "\nBanco.: " + txtCodBanco.getVlrString() + " - " + txtDescBanco.getVlrString().trim() );
			}

			sql.append( "group by 1 , 2, 3 " );

			sql.append( "union all " );

			sql.append( "select ir.codemp, ir.codfilial, coalesce(ir.dtprevitrec,ir.dtvencitrec) vencimento, " );
			sql.append( "cast(0 as decimal(15,2)) vlrpagar, " );
			sql.append( "cast(sum(ir.vlrapagitrec) as numeric(15,2)) vlrareceber " );
			sql.append( "from fnitreceber ir " );
			sql.append( "where " );
			sql.append( "ir.codemp = ? and ir.codfilial = ? and ir.dtvencitrec between ? and ? " );
			sql.append( "and ir.statusitrec != 'RP' and cast(ir.vlrapagitrec as numeric(15,2)) > 0 " );

			if ( txtCodBanco.getVlrString().length() > 0 ) {
				sql.append( " and ir.codempbo=? AND ir.codfilialbo=? and ir.codbanco=? " );
			}

			sql.append( "group by 1 , 2, 3 " );

			sql.append( "order by 1, 3, 3 " );

			int iparam = 1;
			ps = con.prepareStatement( sql.toString() );

			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, Aplicativo.iCodFilial );

			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			if ( txtCodBanco.getVlrString().length() > 0 ) {
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNBANCO" ) );
				ps.setString( iparam++, txtCodBanco.getVlrString() );
			}

			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, Aplicativo.iCodFilial );

			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			if ( txtCodBanco.getVlrString().length() > 0 ) {
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNBANCO" ) );
				ps.setString( iparam++, txtCodBanco.getVlrString() );
			}

			rs = ps.executeQuery();

			imprimirGrafico( bVisualizar, rs, filtros.toString() );

			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de contas a receber/pagar!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	private void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNPAGAR" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "layout/rel/REL_REC_PAG_01.jasper", "Contas a receber/pagar", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o do relat�rio contas a receber/pagar!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcBanco.setConexao( cn );

	}

	public void valorAlterado( RadioGroupEvent evt ) {

	}
}
