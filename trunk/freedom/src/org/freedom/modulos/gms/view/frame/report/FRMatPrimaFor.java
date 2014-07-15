/**
 * @version 09/04/2012 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FRProdFor.java <BR>
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
 *         Relat�rio Mat�ria prima por Fornecedor.
 * 
 */

package org.freedom.modulos.gms.view.frame.report;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

public class FRMatPrimaFor extends FRelatorio {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	

	public FRMatPrimaFor() {
		setTitulo( "Mat.prima por Fornecedor" );
		setAtribos( 80, 80, 410	, 140 );

		montaListaCampos();
		montaTela();

	}
	
	private void montaListaCampos()	{
		
	}
	
	private void montaTela() {
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Per�odo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 300, 45 );
		
		adic( new JLabelPad( "De:" ), 15, 25, 25, 20 );
		adic( txtDataini, 42, 25, 95, 20 );
		adic( new JLabelPad( "At�:" ), 148, 25, 25, 20 );
		adic( txtDatafim, 178, 25, 95, 20 );
		
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {
		
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data inicial maior que a data final!" );
			return;
		}
		Blob fotoemp = null;
		
		try {
			PreparedStatement ps = con.prepareStatement( "SELECT FOTOEMP FROM SGEMPRESA WHERE CODEMP=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				fotoemp = rs.getBlob( "FOTOEMP" );
			}
			rs.close();
			ps.close();
			con.commit();

		} catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro carregando logotipo.\n" + e.getMessage() );
			e.printStackTrace();
		}	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sCab = "Per�odo de " + txtDataini.getVlrString()  + " a " +  txtDatafim.getVlrString();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select p.codprod, p.codunid, p.descprod, f.codfor, f.razfor, f.nomefor, c.doccompra, c.codcompra, c.dtemitcompra ");
		sql.append(", c.dtentcompra, ic.precoitcompra, ic.qtditcompra, ic.vlrliqitcompra ");
		sql.append(", ic.vlripiitcompra, ic.vlrfreteitcompra, c.codplanopag, pp.descplanopag ");
		sql.append(", ic.codnat, c.codtran ");
		sql.append("from cpcompra c ");
		sql.append("inner join cpitcompra ic on ");
		sql.append("ic.codemp=c.codemp and ic.codfilial=c.codfilial and ic.codcompra=c.codcompra ");
		sql.append("inner join eqproduto p on ");
		sql.append("p.codemp=ic.codemppd and p.codfilial=ic.codfilialpd and p.codprod=ic.codprod ");
		sql.append("and p.tipoprod in ('M') ");
		sql.append("inner join cpforneced f on ");
		sql.append("f.codemp=c.codempfr and f.codfilial=c.codfilialfr and f.codfor=c.codfor ");
		sql.append("inner join fnplanopag pp on ");
		sql.append("pp.codemp=c.codemppg and pp.codfilial=c.codfilialpg and ");
		sql.append("pp.codplanopag=c.codplanopag ");
		sql.append("where c.codemp=? and c.codfilial=? ");
		sql.append("and c.dtemitcompra between ? and ? ");
		sql.append("and c.codcompra=(select first 1 cm.codcompra ");
		sql.append("from cpcompra cm, cpitcompra itm, eqproduto pm ");
		sql.append("where pm.codemp=p.codemp and pm.codfilial=p.codfilial and pm.codprod=p.codprod ");
		sql.append("and itm.codemppd=pm.codemp and itm.codfilialpd=pm.codfilial and ");
		sql.append("itm.codprod=pm.codprod ");
		sql.append("and cm.codemp=itm.codemp and cm.codfilial=itm.codfilial and cm.codcompra=itm.codcompra ");
		sql.append("and cm.codempfr=f.codemp and cm.codfilialfr=f.codfilial and cm.codfor=f.codfor ");
		sql.append("and cm.dtemitcompra between ? and ? ");
		sql.append("order by cm.dtemitcompra desc) ");
		sql.append("order by p.descprod, f.razfor ");
		
		try{
			
			int param = 1;
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			
			rs = ps.executeQuery();

		} catch (Exception err) {
			Funcoes.mensagemErro( this, "Erro consulta Relat�rio A��es Realizadas\n" + err.getMessage(), true, con, err );
		}

		imprimiGrafico( bVisualizar, rs, sCab,  fotoemp );
	}
	
	private void imprimiGrafico( TYPE_PRINT bVisualizar, ResultSet rs, String sCab, Blob fotoemp ) {
		String report = "layout/rel/REL_MATPRIMA_FOR.jasper";
		String label = "Relat�rio de Mat.prima por Fornecedor";
		
	    HashMap<String, Object> hParam = new HashMap<String, Object>();
		hParam.put( "SUBREPORT_DIR", "org/freedom/layout/rel/" );
		hParam.put( "CODEMP", new Integer(Aplicativo.iCodEmp) );
		hParam.put( "CODFILIAL", new Integer(ListaCampos.getMasterFilial( "CPCOMPRA" )) );
		
	    try {
			hParam.put( "LOGOEMP",  new ImageIcon(fotoemp.getBytes(1, ( int ) fotoemp.length())).getImage() );
	
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro carregando logotipo !\n" + e.getMessage()  );
			e.printStackTrace();
		}
		
		FPrinterJob dlGr = new FPrinterJob( report, label, sCab, rs, hParam , this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.preview();
		} else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o do relat�rio de A��es realizadas!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

	}

}