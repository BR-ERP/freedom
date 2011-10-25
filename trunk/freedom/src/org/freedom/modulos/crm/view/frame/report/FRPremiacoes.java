/**
 * @version 28/09/2011 <BR>
 * @author Setpoint Inform�tica Ltda./ Bruno Nascimento<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)FRSitContr.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Relat�rio Situa��o Projetos/Contratos.
 * 
 */

package org.freedom.modulos.crm.view.frame.report;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

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
import org.freedom.modulos.atd.view.frame.crud.plain.FAtendente;

public class FRPremiacoes extends FRelatorio {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private ListaCampos lcAtendente = new ListaCampos( this, "AE" );
	
	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	
	public FRPremiacoes() {		
		setTitulo( "Relat�rio de premia��o" );
		setAtribos( 80, 80, 410	, 300 );
		
		montaListaCampos();
		montaTela();
		
	}
	
	public void setParametros( Integer codcli, Date dtini, Date dtfim ) {

		txtDataini.setVlrDate( dtini );
		txtDatafim.setVlrDate( dtfim );
		
	}
	
	private void montaTela(){
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Per�odo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 300, 45 );
		
		adic( txtDataini, 38, 25, 95, 20 );		
		adic( txtDatafim, 178, 25, 95, 20 );
		
		adic( txtCodAtend, 7, 80, 80, 20, "Cod.Atend" );
		adic( txtNomeAtend, 90, 80, 225, 20, "Nome do atendente");
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
	
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );

				
	}
	
	private void montaListaCampos() {
		
		//Atendente
		lcAtendente.add( new GuardaCampo( txtCodAtend, "CodAtend", "C�d.atend.", ListaCampos.DB_PK, false ) );
		lcAtendente.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ) );
		lcAtendente.montaSql( false, "ATENDENTE", "AT" );
		lcAtendente.setReadOnly( true );
		txtCodAtend.setTabelaExterna( lcAtendente, FAtendente.class.getCanonicalName() );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );	
	}
	
	public void imprimir( boolean bVisualizar ) {

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

	
		String sCab = "";
		String Ordem = "";
		//StringBuffer sWhere = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		
		sql.append("select a.codemp, a.codfilial, a.codempct,  a.codfilialct, a.codcontr, ");
		sql.append("c.desccontr, a.coditcontr, ic.descitcontr, a.codempae, a.codfilialae, a.nomeatend, ");
		sql.append("((select sum(a2.totalgeral) from atatendimentovw02 a2 ");
		sql.append("where a2.codempct=a.codempct and a2.codfilialct=a.codfilialct and ");
		sql.append("a2.codcontr=a.codcontr and a2.coditcontr=a.coditcontr ) ) totalhorastrab, ");
		sql.append("((select sum( a5.qtditvenda ) from atatendimentovw05 a5 ");
		sql.append("where a5.codempct=a.codempct and a5.codfilialct=a.codfilialct and ");
		sql.append("a5.codcontr=a.codcontr and a5.coditcontr=a.coditcontr)) qtditvenda, ");
		sql.append("((select sum( a5.vlrliqitvenda ) from atatendimentovw05 a5 ");
		sql.append("where a5.codempct=a.codempct and a5.codfilialct=a.codfilialct and ");
		sql.append("a5.codcontr=a.codcontr and a5.coditcontr=a.coditcontr)) vlrliqitvenda, ");
		sql.append("sum(a.totalcomis) totalcomis ");
		sql.append("from vdcontrato c, vditcontrato ic, atatendimentovw02 a, vdfincontr fn ");
		sql.append("where a.codemp=? and a.codfilial=? and ");
		sql.append("c.codemp=a.codempct and c.codfilial=a.codfilialct and ");
		sql.append("ic.codemp=c.codemp and ic.codfilial=c.codfilial and ");
		sql.append("ic.codcontr=c.codcontr and ");
		sql.append("a.codempct=c.codemp and a.codfilialct=c.codfilial and ");
		sql.append("a.codcontr=c.codcontr and a.coditcontr=ic.coditcontr and ");
		sql.append("c.tpcobcontr='ES' and ");
		sql.append("fn.codemp=a.codempct and fn.codfilial=a.codfilialct and ");
		sql.append("fn.codcontr=a.codcontr and ");
		sql.append("fn.dtfincontr between ? and ? and ");
		sql.append("(  select sum( am.totalmeta ) from atatendimentovw02 am ");
		sql.append("where am.codemp=? and am.codfilial=? and ");
		sql.append("am.dataatendo between ? and ? and ");
		sql.append("am.codempae=a.codempae and am.codfilialae=a.codfilialae and ");
		sql.append("am.codatend=a.codatend ) >= 100 ");
		sql.append("group by 1,2,3,4,5,6,7,8,9,10,11  ");
		sql.append("union all ");
		sql.append("select a.codemp, a.codfilial, a.codempct,  a.codfilialct, a.codcontr, ");
		sql.append("c.desccontr, ");
		sql.append("a.coditcontr, ic.descitcontr, a.codempae, a.codfilialae, a.nomeatend, ");
		sql.append("((select sum(a2.totalgeral) from atatendimentovw02 a2 ");
		sql.append("where a2.codempct=a.codempct and a2.codfilialct=a.codfilialct and ");
		sql.append("a2.codcontr=a.codcontr and a2.coditcontr=a.coditcontr and ");
		sql.append("a2.dataatendo between ? and ? ) ) totalhorastrab, ");
		sql.append("((select sum( a5.qtditvenda ) from atatendimentovw05 a5 ");
		sql.append("where a5.codempct=a.codempct and a5.codfilialct=a.codfilialct and ");
		sql.append("a5.codcontr=a.codcontr and a5.coditcontr=a.coditcontr and ");
		sql.append("a5.dtfinapura between ? and ? )) qtditvenda, ");
		sql.append("((select sum( a5.vlrliqitvenda ) from atatendimentovw05 a5 ");
		sql.append("where a5.codempct=a.codempct and a5.codfilialct=a.codfilialct and ");
		sql.append("a5.codcontr=a.codcontr and a5.coditcontr=a.coditcontr and ");
		sql.append("a5.dtfinapura between ? and ?)) vlrliqitvenda, ");
		sql.append("sum(a.totalcomis) totalcomis ");
		sql.append("from vdcontrato c, vditcontrato ic, atatendimentovw02 a ");
		sql.append("where a.codemp=? and a.codfilial=? and ");
	    sql.append("c.codemp=a.codempct and c.codfilial=a.codfilialct and ");
	    sql.append("ic.codemp=c.codemp and ic.codfilial=c.codfilial and ");
	    sql.append("ic.codcontr=c.codcontr and ");
	    sql.append("a.codempct=c.codemp and a.codfilialct=c.codfilial and ");
	    sql.append("a.codcontr=c.codcontr and a.coditcontr=ic.coditcontr and ");
	    sql.append("c.tpcobcontr='ME' and ");
	    sql.append("a.dataatendo between ? and ? and ");
	    sql.append("(  select sum( am.totalmeta ) from atatendimentovw02 am ");
	    sql.append("where am.codemp= ? and am.codfilial= ? and ");
	    sql.append("am.dataatendo between ? and ? and ");
	    sql.append("am.codempae=a.codempae and am.codfilialae=a.codfilialae and ");
	    sql.append("am.codatend=a.codatend) >= 100 ");
	    sql.append("group by 1,2,3,4,5,6,7,8,9,10,11 ");
	    sql.append("order by 11, 6, 8 ");

		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			ps.setDate( 3, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( 4, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			ps.setDate( 7, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( 8, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			ps.setDate( 9, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( 10, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			ps.setDate( 11, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( 12, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			ps.setDate( 13, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( 14, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			ps.setInt( 15, Aplicativo.iCodEmp );
			ps.setInt( 16, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			ps.setDate( 17, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( 18, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			ps.setInt( 19, Aplicativo.iCodEmp );
			ps.setInt( 20, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			ps.setDate( 21, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( 22, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			
			rs = ps.executeQuery();

		} catch (Exception err) {
			Funcoes.mensagemErro( this, "Erro consulta Situa��o de Projetos/Contratos!\n" + err.getMessage(), true, con, err );
		}
		
		imprimiGrafico( bVisualizar, rs,  sCab, fotoemp );

	}

	private void imprimiGrafico( boolean bVisualizar, ResultSet rs, String sCab, Blob fotoemp) {
		String report = "layout/rel/REL_PREMIACAO.jasper";
		String label = "Relat�rio de Premia��o";
		
	    HashMap<String, Object> hParam = new HashMap<String, Object>();

	    try {
			hParam.put( "LOGOEMP",  new ImageIcon(fotoemp.getBytes(1, ( int ) fotoemp.length())).getImage() );
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro carregando logotipo !\n" + e.getMessage()  );
			e.printStackTrace();
		}
	
		FPrinterJob dlGr = new FPrinterJob( report, label, sCab, rs, hParam , this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		} else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de Premia��o!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcAtendente.setConexao( cn );
	}

}
