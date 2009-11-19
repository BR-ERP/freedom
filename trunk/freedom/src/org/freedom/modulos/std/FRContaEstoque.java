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
 * Classe para filtros e carregamento de relat�rio de contagem de estoque.
 * 
 */

package org.freedom.modulos.std;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRContaEstoque extends FRelatorio {
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
	private JTextFieldPad txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JLabelPad lbCodGrup = new JLabelPad("C�d.grupo");
	private JLabelPad lbDescGrup = new JLabelPad("Descri��o do grupo");
	private JRadioGroup<?, ?> rgOrdem = null;
	private Vector<String> vLabs = new Vector<String>(2);
	private Vector<String> vVals = new Vector<String>(2);
	private ListaCampos lcGrup = new ListaCampos(this);
	
	private JCheckBoxPad cbSemEstoq = new JCheckBoxPad("Imprimir produtos sem saldo?","S","N");
	private JCheckBoxPad cbComSaldo = new JCheckBoxPad("Imprimir saldo dos produtos?","S","N");
	
	public FRContaEstoque() {
		
		setTitulo("Relat�rio de Giro de estoque");
		
		setAtribos(140,40,340,290);
		vLabs.addElement("C�digo");
		vLabs.addElement("Descri��o"); 

		vVals.addElement("C");
		vVals.addElement("D");

		rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
		rgOrdem.setVlrString("D");
        
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createTitledBorder( "Posi��o do dia:") );
		
		adic( lbLinha, 7, 0, 300, 65 );
		adic( txtDataini, 17, 25, 75, 20 );

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
		
	    adic(cbSemEstoq,7,170,250,20);
	    adic(cbComSaldo,7,190,250,20);
	    
	    cbSemEstoq.setVlrString("S");
	    cbComSaldo.setVlrString("N");

	
	}

	public void imprimir( boolean bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		StringBuilder status = new StringBuilder();
		StringBuilder filtros = new StringBuilder();
		
		try {

			sql.append( "select codprod, refprod, codbarprod, descprod, sldprod ");
			sql.append( "from eqrelpepssp(?,?,?,null,null,null,?,?,?,null,null,null,null)");
			sql.append( "where ativoprod='S'");
			
			if("N".equals( cbSemEstoq.getVlrString() )) {
				sql.append(" and sldprod>0" );
			}
						
			if("C".equals(rgOrdem.getVlrString())) {
				sql.append("order by codprod ");
			}
			
			if("D".equals(rgOrdem.getVlrString())) {
				sql.append("order by descprod ");
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
			else {
				ps.setNull(iparam++,Types.INTEGER);			
	  			ps.setNull(iparam++,Types.SMALLINT);
	  			ps.setNull(iparam++,Types.CHAR);				
			}

			ResultSet rs = ps.executeQuery();

			HashMap<String, Object> hParam = new HashMap<String, Object>();
			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "CODFILIAL", Aplicativo.iCodFilial );
			hParam.put( "DATA", txtDataini.getVlrDate() ); 
			hParam.put( "SUBREPORT_DIR", "org/freedom/layout/rel"); 
			hParam.put( "COM_SALDO",cbComSaldo.getVlrString() );

			FPrinterJob dlGr = new FPrinterJob( "layout/rel/REL_CONTA_ESTOQUE.jasper", "Relat�rio de Contagem de estoque", "", rs, hParam, this );

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
