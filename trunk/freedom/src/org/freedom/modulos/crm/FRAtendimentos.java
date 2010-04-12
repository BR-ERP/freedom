/**
 * @version 23/01/2009 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)FRDiario.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Relat�rio di�rio de liga��es.
 * 
 */

package org.freedom.modulos.crm;

import java.awt.Component;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRAtendimentos extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtDescContr = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtDDDCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );
	
	private JTextFieldFK txtFoneCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 12, 0 );
	
	private JTextFieldFK txtEmailCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtContatoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldFK txtEndCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtCidCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );
	
	private JTextFieldFK txtUfCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldFK txtNumCli = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private ListaCampos lcCli = new ListaCampos( this );
	
	private ListaCampos lcContr = new ListaCampos( this );
	
	private JCheckBoxPad cbTodos = new JCheckBoxPad( "Todos os atendimentos", "S", "N" );

	private boolean bComp = false;
	
	private Component tela = null;
	
	private ListaCampos lcAtend = new ListaCampos( this, "AE" );

	public FRAtendimentos() {

		setTitulo( "Relat�rio de atendimentos" );
		setAtribos( 80, 80, 350, 300 );
	
		montaListaCampos();
		montaTela();
		tela = this; 
		
	}
	
	public void setParametros(Integer codcli, Date dtini, Date dtfim ) {
		txtCodCli.setVlrInteger( codcli );		
		txtDataini.setVlrDate( dtini );
		txtDatafim.setVlrDate( dtfim );	
	}


	private void montaTela() {
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Per�odo:" , SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );
		
		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 310, 45 );	
		adic( new JLabelPad("De:"), 15, 25, 20, 20 );
		adic( txtDataini, 38, 25, 95, 20 );
		adic( new JLabelPad("At�:"), 145, 25, 35, 20 );
		adic( txtDatafim, 178, 25, 95, 20 );
		adic( new JLabelPad("C�d.Cli"), 7, 60, 80, 20 );
		adic( txtCodCli, 7, 80, 80, 20  );
		adic( new JLabelPad("Raz�o social do cliente"), 90, 60, 250, 20 );
		adic( txtRazCli, 90, 80, 225, 20  );

		adic( new JLabelPad("C�d.Contr."), 7, 110, 80, 20 );
		adic( txtCodContr, 7, 130, 80, 20  );
		adic( new JLabelPad("Descri��o do contrato"), 90, 110, 250, 20 );
		adic( txtDescContr, 90, 130, 225, 20  );

		adic( new JLabelPad("C�d.Atend."), 7, 150, 80, 20 );
		adic( txtCodAtend, 7, 170, 80, 20  );
		adic( new JLabelPad("Nome do atendente"), 90, 150, 250, 20 );
		adic( txtNomeAtend, 90, 170, 225, 20  );

		
		
		adic( cbTodos, 7,200,300,20 );

		Calendar cPeriodo = Calendar.getInstance();
	    txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
		txtDataini.setVlrDate(cPeriodo.getTime());
		
	}
	
	private void montaListaCampos(){
		//Cliente
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		//Contrato
		
		lcContr.add( new GuardaCampo( txtCodContr, "CodContr", "C�d.Contr.", ListaCampos.DB_PK, false ) );
		lcContr.add( new GuardaCampo( txtDescContr, "DescContr", "Descri��o do contrato", ListaCampos.DB_SI, false ) );
		lcContr.montaSql( false, "CONTRATO", "VD" );
		lcContr.setReadOnly( true );
		lcContr.setDinWhereAdic( "CODCLI=#N ", txtCodCli );
		txtCodContr.setTabelaExterna( lcContr );
		txtCodContr.setFK( true );
		txtCodContr.setNomeCampo( "CodContr" );
		
		//Atendente
		
		txtCodAtend.setTabelaExterna( lcAtend );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );
		lcAtend.add( new GuardaCampo( txtCodAtend, "CodAtend", "C�d.atend.", ListaCampos.DB_PK, false ), "txtCodVendx" );
		lcAtend.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ), "txtCodVendx" );
		lcAtend.montaSql( false, "ATENDENTE", "AT" );
		lcAtend.setReadOnly( true );

		
	}
	
	public void imprimir( boolean bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null; 
		ResultSet rs = null;
		int iparam = 1;		
		
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( tela, "Data final maior que a data inicial!" );
			return;
		}
				
		sql.append( "select a.codtpatendo, a.codatend, a.dataatendo, a.dataatendofin, a.codatendo, " );
		sql.append( "a.horaatendo, a.horaatendofin, a.obsatendo, a.codatend, atd.nomeatend, t.desctpatendo, cl.razcli, a.statusatendo " );
		sql.append( "from  atatendimento a, atatendente atd , attipoatendo t, vdcliente cl where " );
		sql.append( "atd.codemp=a.codempae and atd.codfilial=a.codfilialae " );
		sql.append( "and t.codemp=a.codempto and t.codfilial=a.codfilial and t.codtpatendo=a.codtpatendo " );
		sql.append( "and cl.codemp=a.codempcl and cl.codfilial=a.codfilialcl and cl.codcli=a.codcli " );
		sql.append( "and atd.codatend=a.codatend and a.codemp=? and a.codfilial=? " );
		sql.append( "and a.dataatendo between ? and ?  " );
		
		if(txtCodContr.getVlrInteger().intValue()>0) {
			sql.append( " and a.codempct=? and a.codfilialct=? and a.codcontr=? "  );
		}
		else if("N".equals( cbTodos.getVlrString() )) {
			sql.append( " and a.codcontr is null " );
		}
		if(txtCodCli.getVlrInteger().intValue()>0) {
			sql.append(" and a.codempcl=? and a.codfilialcl=? and a.codcli=? ");
		}
		if(txtCodAtend.getVlrInteger().intValue()>0) {
			sql.append(" and a.codempae=? and a.codfilialae=? and a.codatend=? ");
		}

		sql.append(" order by a.dataatendo, a.horaatendo ");
		
		try {
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( iparam++, Aplicativo.iCodEmp);
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATATENDIMENTO" ));
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDataini.getVlrString()));
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString()));
			
			if(txtCodContr.getVlrInteger().intValue()>0) {
				ps.setInt( iparam++, lcContr.getCodEmp() );
				ps.setInt( iparam++, lcContr.getCodFilial() );
				ps.setInt( iparam++, txtCodContr.getVlrInteger() );
			}
			if(txtCodCli.getVlrInteger().intValue()>0) {				
				ps.setInt( iparam++, lcCli.getCodEmp() );
				ps.setInt( iparam++, lcCli.getCodFilial() );
				ps.setInt( iparam++, txtCodCli.getVlrInteger() );				
			}
			if(txtCodAtend.getVlrInteger().intValue()>0) {
				ps.setInt( iparam++, lcAtend.getCodEmp() );
				ps.setInt( iparam++, lcAtend.getCodFilial() );
				ps.setInt( iparam++, txtCodAtend.getVlrInteger() );								
			}
									
			rs = ps.executeQuery();
			
		} 
		catch ( SQLException err ) {
				
			err.printStackTrace();
			Funcoes.mensagemErro( this," Erro na consulta da tabela de atendimentos" );
		}
		
		imprimiGrafico( rs, bVisualizar );
		
	}
	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/"); 
		hParam.put( "CODCLI", txtCodCli.getVlrInteger() );
		hParam.put( "DTINI", txtDataini.getVlrDate());
		hParam.put( "DTFIM", txtDatafim.getVlrDate());
		hParam.put( "CODCONTR", txtCodContr.getVlrInteger());
		hParam.put( "DESCCONTR", txtDescContr.getVlrString());
		hParam.put( "CONEXAO", con.getConnection() );
				
		if(txtCodCli.getVlrInteger().intValue()>0) {
			hParam.put( "CLIENTE", txtCodCli.getVlrString().trim() + "-" + txtRazCli.getVlrString().trim() );
			dlGr = new FPrinterJob( "relatorios/atendimentos.jasper", "RELAT�RIO DE ATENDIMENTOS", "", rs, hParam,this );
		}
		else {
			hParam.put( "CLIENTE", "DIVERSOS" );
			dlGr = new FPrinterJob( "relatorios/atendimentos_cli.jasper", "RELAT�RIO DE ATENDIMENTOS", "", rs, hParam,this );	
		}

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( tela, "Erro na impress�o de relat�rio Compras Geral!" + err.getMessage(), true, con, err );
			}
		}
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcContr.setConexao( cn );
		lcAtend.setConexao( cn );
	}
	
}
