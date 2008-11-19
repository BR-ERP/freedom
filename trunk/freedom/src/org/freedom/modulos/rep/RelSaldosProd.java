/**
 * @version 11/2008 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Reginaldo Garcia Heua<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelSaldosProd.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Relatorio de clientes, em dois modos: completo e resumido.
 * 
 */

package org.freedom.modulos.rep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class RelSaldosProd extends FRelatorio {
	
	private static final long serialVersionUID = 1;
	
	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final ListaCampos lcFornecedor = new ListaCampos( this );


	public RelSaldosProd() {

		super( false );
		setTitulo( "Relatorio de saldos" );		
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
			
			sql.append( "select pr.refprod,pr.descprod,pr.saldoprod as compra,sum(it.qtditped) venda,pr.saldoprod-sum(it.qtditped) as saldo " );
			sql.append( "from rpproduto pr, rpitpedido it, rppedido pd " );
			sql.append( "where pr.codemp=it.codemppd and pr.codfilial=it.codfilialpd and pr.codprod = it.codprod " );
			sql.append( "and pd.codemp = it.codemp and pd.codfilial = it.codfilial and pd.codped = it.codped " );
			sql.append( "and pd.codemp=? and pd.codfilial=? " );
			sql.append( "and pd.dataped between ? and ? " );
			if( !txtCodFor.getVlrString().equals( "" ) ){
				sql.append( "and pd.codfor=? " );
			}			
			sql.append( "group by 1,2,3" );
									
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
			hParam.put( "REPORT_CONNECTION", con );
			
			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/rpsaldoprod.jasper", "SALDO DE PRODUTOS", null, rs, hParam, this );

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

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcFornecedor.setConexao( cn );
	}

}
