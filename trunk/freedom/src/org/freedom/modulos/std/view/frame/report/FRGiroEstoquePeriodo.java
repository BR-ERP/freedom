/**
 * @version 19/11/2009 <BR>
 * @author Filipe Chagas<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FRGiroEstoquePeriodo.java <BR>
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
 *         Classe para filtros e carregamento de relat�rio de giro de estoque.
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
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

public class FRGiroEstoquePeriodo extends FRelatorio{

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDataFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JLabelPad lbCodGrup = new JLabelPad( "C�d.grupo" );

	private JLabelPad lbDescGrup = new JLabelPad( "Descri��o do grupo" );

	private JCheckBoxPad cbSemEstoq = new JCheckBoxPad( "Imprimir produtos sem estoque?", "S", "N" );

	private JRadioGroup<?, ?> rgOrdem = null;

	private Vector<String> vLabs = new Vector<String>( 2 );

	private Vector<String> vVals = new Vector<String>( 2 );

	private ListaCampos lcGrup = new ListaCampos( this );

	public FRGiroEstoquePeriodo() {

		setTitulo( "Relat�rio de Giro de estoque" );

		setAtribos( 140, 40, 340, 290 );

		vLabs.addElement( "C�digo" );
		vLabs.addElement( "Descri��o" );
		vLabs.addElement( "+ Vendido" );
		vVals.addElement( "C" );
		vVals.addElement( "D" );
		vVals.addElement( "M" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgOrdem.setVlrString( "D" );
		
		Calendar periodo = Calendar.getInstance();
		txtDataFim.setVlrDate( periodo.getTime() );
		periodo.set( Calendar.DAY_OF_MONTH, 1 );
		txtDataini.setVlrDate( periodo.getTime() );		

		JPanelPad pnLinha = new JPanelPad( "Per�odo:", Color.BLACK );

		adic( pnLinha, 7, 0, 300, 65 );
		pnLinha.adic( new JLabelPad("De"), 7, 7, 20, 20 );
		pnLinha.adic( txtDataini, 30, 7, 75, 20 );
		pnLinha.adic( new JLabelPad("�"), 108, 7, 10, 20 );
		pnLinha.adic( txtDataFim, 121, 7, 75, 20 );
		
		
		lcGrup.add( new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, false ) );
		lcGrup.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false ) );
		lcGrup.montaSql( false, "GRUPO", "EQ" );
		lcGrup.setReadOnly( true );
		txtCodGrup.setTabelaExterna( lcGrup, null );
		txtCodGrup.setFK( true );
		txtCodGrup.setNomeCampo( "CodGrup" );

		adic( lbCodGrup, 7, 75, 250, 20 );
		adic( txtCodGrup, 7, 95, 80, 20 );
		adic( lbDescGrup, 90, 75, 250, 20 );
		adic( txtDescGrup, 90, 95, 216, 20 );
		adic( rgOrdem, 7, 130, 300, 30 );

		cbSemEstoq.setVlrString( "S" );

		adic( cbSemEstoq, 7, 165, 300, 30 );

	}

	public void imprimir( boolean bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		StringBuilder status = new StringBuilder();
		StringBuilder filtros = new StringBuilder();

		try {

			sql.append( "select codprod,refprod,codfabprod,codbarprod,descprod," );
			sql.append( "dtultcp,doccompra,identcontainer,qtdultcp,qtdvendida,saldoatu,saldoant " );
			sql.append( "from eqrelgiroprodperi(?,?,?,?) " );
			sql.append( " where dtultcp between ? and ? " );

			if ( "N".equals( cbSemEstoq.getVlrString() ) ) {
				sql.append( " and saldoatu>0 " );
			}

			if ( txtCodGrup.getVlrInteger() > 0 ) {
				sql.append( " and codempgp=? and codfilialgp=? and codgrup=? " );
			}

			if ( "C".equals( rgOrdem.getVlrString() ) ) {
				sql.append( "order by codprod,descprod,qtdvendida desc " );
			}
			if ( "D".equals( rgOrdem.getVlrString() ) ) {
				sql.append( "order by descprod, qtdvendida desc " );
			}
			if ( "M".equals( rgOrdem.getVlrString() ) ) {
				sql.append( "order by qtdvendida desc " );
			}

			PreparedStatement ps = con.prepareStatement( sql.toString() );

			int iparam = 1;

			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, Aplicativo.iCodFilial );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataFim.getVlrDate() ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataFim.getVlrDate() ) );

			if ( txtCodGrup.getVlrInteger() > 0 ) {
				ps.setInt( iparam++, lcGrup.getCodEmp() );
				ps.setInt( iparam++, lcGrup.getCodFilial() );
				ps.setString( iparam++, txtCodGrup.getVlrString() );
			}

			ResultSet rs = ps.executeQuery();

			HashMap<String, Object> hParam = new HashMap<String, Object>();
			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "CODFILIAL", Aplicativo.iCodFilial );
			hParam.put( "DATAINI", txtDataini.getVlrDate() );
			hParam.put( "DATAFIM", txtDataFim.getVlrDate() );
			hParam.put( "SUBREPORT_DIR", "org/freedom/layout/rel" );

			FPrinterJob dlGr = new FPrinterJob( "layout/rel/REL_GIRO_ESTOQUE_PERIODO.jasper", "Relat�rio de Giro de estoque por per�odo", "", rs, hParam, this );

			if ( bVisualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro consultar giro do estoque!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	public void setConexao( DbConnection cn ) {

		lcGrup.setConexao( cn );
		super.setConexao( cn );

	}

}