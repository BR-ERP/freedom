/**
 * @version 19/11/2009 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez<BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRGiroEstoque.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Classe para filtros e carregamento de relat�rio de giro de estoque.
 * 
 */

package org.freedom.modulos.std;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRGiroEstoque extends FRelatorio {
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
	private JTextFieldPad txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JLabelPad lbCodGrup = new JLabelPad("C�d.grupo");
	private JLabelPad lbDescGrup = new JLabelPad("Descri��o do grupo");
    private JCheckBoxPad cbSemEstoq = new JCheckBoxPad("Imprimir produtos sem estoque?","S","N");
	private JRadioGroup<?, ?> rgOrdem = null;
	private Vector<String> vLabs = new Vector<String>(2);
	private Vector<String> vVals = new Vector<String>(2);
	private ListaCampos lcGrup = new ListaCampos(this);

	public FRGiroEstoque() {
		
		setTitulo("Relat�rio de Giro de estoque");
		
		setAtribos(140,40,340,290);
		
		vLabs.addElement("C�digo");
		vLabs.addElement("Descri��o");
		vLabs.addElement("+ Vendido");   
		vVals.addElement("C");
		vVals.addElement("D");
		vVals.addElement("M");
		rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
		rgOrdem.setVlrString("D");
        
		JPanelPad pnLinha = new JPanelPad("Posi��o do dia:", Color.BLUE);
		
		adic( pnLinha, 7, 0, 300, 65 );
		pnLinha.adic( txtDataini, 7, 7, 75, 20 );

		txtDataini.setVlrDate( new Date() );

		lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, false));
		lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false));
		lcGrup.montaSql(false, "GRUPO", "EQ");
		lcGrup.setReadOnly(true);
		txtCodGrup.setTabelaExterna(lcGrup);
		txtCodGrup.setFK(true);
		txtCodGrup.setNomeCampo("CodGrup");
    
		adic(lbCodGrup,7,75,250,20);
		adic(txtCodGrup,7,95,80,20);		
		adic(lbDescGrup,90,75,250,20);
		adic(txtDescGrup,90,95,216,20);
		adic(rgOrdem,7,130,300,30);   
		
		cbSemEstoq.setVlrString( "S" );
		
		adic(cbSemEstoq, 7,165,300,30);
			
	}

	public void imprimir( boolean bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		StringBuilder status = new StringBuilder();
		StringBuilder filtros = new StringBuilder();
		
		try {

			sql.append( "select codprod,refprod,codfabprod,codbarprod,descprod," );
			sql.append( "dtultcp,doccompra,identcontainer,qtdultcp,qtdvendida,saldoatu,saldoant " );
			sql.append( "from eqrelgiroprod(?,?,?) " );
			
			if( "N".equals( cbSemEstoq.getVlrString()) ) {
				sql.append( " where saldoatu>0 " );
			}
			
			if(txtCodGrup.getVlrInteger()>0) {
				sql.append( " where codempgp=? and codfilialgp=? and codgrup=? " );
			}
			
			if("C".equals(rgOrdem.getVlrString())) {
				sql.append("order by codprod,descprod,qtdvendida desc ");
			}
			if("D".equals(rgOrdem.getVlrString())) {
				sql.append("order by descprod, qtdvendida desc ");
			}
			if("M".equals(rgOrdem.getVlrString())) {
				sql.append("order by qtdvendida desc ");
			}

			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			int iparam = 1;
			
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, Aplicativo.iCodFilial );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );

			if(txtCodGrup.getVlrInteger()>0) {
				ps.setInt( iparam++, lcGrup.getCodEmp() );
				ps.setInt( iparam++, lcGrup.getCodFilial() );
				ps.setString( iparam++, txtCodGrup.getVlrString() );
			}


			ResultSet rs = ps.executeQuery();

			HashMap<String, Object> hParam = new HashMap<String, Object>();
			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "CODFILIAL", Aplicativo.iCodFilial );
			hParam.put( "DATA", txtDataini.getVlrDate() ); 
			hParam.put( "SUBREPORT_DIR", "org/freedom/layout/rel"); 

			FPrinterJob dlGr = new FPrinterJob( "layout/rel/REL_GIRO_ESTOQUE.jasper", "Relat�rio de Giro de estoque", "", rs, hParam, this );

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
