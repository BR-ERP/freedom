/**
 * @version 11/2008 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelSaldosProd.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Relatorio de clientes, em dois modos: completo e resumido.
 * 
 */

package org.freedom.modulos.rep;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;


public class RelSaldosProd extends FRelatorio {
	
	private static final long serialVersionUID = 1;
	
	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final ListaCampos lcFornecedor = new ListaCampos( this );


	public RelSaldosProd() {

		super( false );
		setTitulo( "Tabela com saldo" );		
		setAtribos( 50, 50, 325, 200 );

		montaListaCampos();
		montaTela();
		
		Calendar cal = Calendar.getInstance();			
		txtDtFim.setVlrDate( cal.getTime() );		
		cal.set( cal.get( Calendar.YEAR ), 0, 1 );
		txtDtIni.setVlrDate( cal.getTime() );	
	}
		
	private void montaListaCampos() {
		
		/**************
		 * FORNECEDOR *
		 **************/
		
		lcFornecedor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, false ) );
		lcFornecedor.add( new GuardaCampo( txtRazFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedor.montaSql( false, "FORNECEDOR", "RP" );
		lcFornecedor.setQueryCommit( false );
		lcFornecedor.setReadOnly( true );
		txtCodFor.setListaCampos( lcFornecedor );
		txtCodFor.setTabelaExterna( lcFornecedor );
		txtCodFor.setPK( true );
		txtCodFor.setNomeCampo( "CodFor" );
	}
	
	private void montaTela() {
		
		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER );
		periodo.setOpaque( true );
		adic( periodo, 25, 10, 60, 20 );
		
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		adic( borda, 10, 20, 290, 45 );
		
		adic( txtDtIni, 25, 35, 110, 20 );
		adic( new JLabel( "at�", SwingConstants.CENTER ), 135, 35, 40, 20 );
		adic( txtDtFim, 175, 35, 110, 20 );
		
		adic( new JLabel( "C�d.for." ), 10, 70, 77, 20 );
		adic( txtCodFor, 10, 90, 77, 20 );
		adic( new JLabel( "Raz�o social do fornecedor" ), 90, 70, 210, 20 );
		adic( txtRazFor, 90, 90, 210, 20 );	
	}

	public void imprimir( boolean visualizar ) {

		try {
			
			StringBuilder sql = new StringBuilder();
			Date dtini = txtDtIni.getVlrDate();
			Date dtfim = txtDtFim.getVlrDate();
			
			sql.append( "select pr.codfor, pr.refprod,pr.descprod,pr.saldoprod as compra, pr.precoprod1, " );
			sql.append( "sum((select coalesce(sum(it.qtditped),0) from rpitpedido it ,rppedido pd where " );
			sql.append( "pd.codemp=it.codemp and pd.codfilial=it.codfilial and pd.codped=it.codped and " ); 
			sql.append( "pd.codemp=? and pd.codfilial=? and pd.dataped between ? and ? and " );
			sql.append( "it.codemppd=pr.codemp and it.codfilialpd=pr.codfilial and it.codprod=pr.codprod " );
			sql.append( ")) as venda " );
			sql.append( "from rpproduto pr " );
			
			if( !txtCodFor.getVlrString().equals( "" ) ){
				sql.append( "where pr.codfor=? " );
			}
			sql.append( "group by 1,2,3,4,5 " );
			sql.append( "order by pr.descprod " );
									
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "rpproduto" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );
			
			if( !txtCodFor.getVlrString().equals( "" )){
				ps.setInt( 5, txtCodFor.getVlrInteger() );
			}
			
			ResultSet rs = ps.executeQuery();
			
			HashMap<String,Object> hParam = new HashMap<String, Object>();
			
			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "REPORT_CONNECTION", con.getConnection() );
			
			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/rpsaldoprod.jasper", "TABELA", null, rs, hParam, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}
			
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcFornecedor.setConexao( cn );
	}

}
