/**
 * @version 24/10/2007 <BR>
 * @author Setpoint Inform�tica Ltda./Reginaldo Garcia Heua <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRRazFor.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;


public class FRRazFor extends FRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private ListaCampos lcFor = new ListaCampos(this);
	
	public FRRazFor(){
		
		setTitulo( "Compras por Raz�o" );
		setAtribos( 50, 50, 340, 195 );
		
		montaTela();
		montaListaCampos();
	}

	private void montaTela(){
	
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbPeriodo = new JLabelPad("	     Periodo:");
		lbPeriodo.setOpaque(true);
		
		adic(lbPeriodo,7, 1, 80, 20 );
		adic(lbLinha,5,10,300,45);
		
		adic( new JLabelPad( "De:" ), 10, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad( "At�:" ), 140, 25, 37, 20 );
		adic( txtDatafim, 180, 25, 100, 20 );
		adic( new JLabelPad("C�d.For"), 7, 60, 100, 20 );
		adic( txtCodFor, 7, 80, 80, 20 );
		adic( new JLabelPad("Descri��o do fornecedor"), 93, 60, 180, 20 );
		adic( txtDescFor, 93, 80, 210, 20 );
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
		
	}
	
	private void montaListaCampos(){
		
		lcFor.add(new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, false));
		lcFor.add(new GuardaCampo( txtDescFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI,false));
		txtCodFor.setTabelaExterna(lcFor);
		txtCodFor.setNomeCampo("CodFor");
		txtCodFor.setFK(true);
		lcFor.setReadOnly(true);
		lcFor.montaSql(false, "FORNECED", "CP");
		
	}
	
	@ Override
	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		
		try {
			
			if( txtCodFor.getVlrString() != null && txtCodFor.getVlrString().trim().length()>0 ){
				
				sCab.append( "FORNECEDOR - " + txtDescFor.getVlrString() );
				sWhere.append( "" );
			}
			
			sSQL.append( "" );
			sSQL.append( "" );
			sSQL.append( "" );
			sSQL.append( "" );
			sSQL.append( "" );
			sSQL.append( "" );
			sSQL.append( "" );
			sSQL.append( sWhere );
		
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial( "FNPAGAR" )); 
			ps.setDate(3, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ));
			ps.setDate(4, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ));
			rs = ps.executeQuery();
			
			imprimiGrafico( bVisualizar, rs, sCab);
			
			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( Exception err ) {
			
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados na tabela!\n" + err.getMessage(), true, con, err );
		}
	}
	
	private void imprimiGrafico( final boolean bVisualizar, final ResultSet rs, final StringBuffer sCab ) {
		
		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();
		
		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNPAGAR" ));
		hParam.put( "RAZAOEMP" , Aplicativo.sEmpSis );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/FRRazFor.jasper", "Relat�rio por Raz�o", sCab.toString(), rs, hParam, this );
		
		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio por raz�o!" + err.getMessage(), true, con, err );
			}
		}
	}
	
	public void setConexao(Connection cn) {
		
		super.setConexao(cn);
		lcFor.setConexao( cn );
		
	}
}
