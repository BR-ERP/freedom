/**
 * @version 23/01/2009 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)FRDiario.java <BR>
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
 *         Relat�rio di�rio de liga��es.
 * 
 */

package org.freedom.modulos.crm.view.frame.report;

import org.freedom.library.type.TYPE_PRINT;
import java.awt.Component;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.modulos.atd.view.frame.crud.tabbed.FAtendente;
import org.freedom.modulos.crm.view.frame.crud.detail.FContrato;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

public class FRAtendimentos extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodItContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldFK txtDescContr = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtDescItContr = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

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

	private ListaCampos lcContr = new ListaCampos( this, "" );
	
	private ListaCampos lcItContr = new ListaCampos( this );

	private JCheckBoxPad cbTodos = new JCheckBoxPad( "Todos os atendimentos", "S", "N" );

	private JCheckBoxPad cbSuprimirResumo = new JCheckBoxPad( "Suprimir resumo", "S", "N" );

	private boolean bComp = false;

	private Component tela = null;

	private ListaCampos lcAtend = new ListaCampos( this, "AE" );

	public FRAtendimentos() {

		setTitulo( "Relat�rio de atendimentos/cobran�a" );
		setAtribos( 80, 80, 350	, 390 );

		montaListaCampos();
		montaTela();
		tela = this;

	}

	public void setParametros( Integer codcli, Date dtini, Date dtfim ) {

		txtCodCli.setVlrInteger( codcli );
		txtDataini.setVlrDate( dtini );
		txtDatafim.setVlrDate( dtfim );
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Per�odo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 310, 45 );
		
		adic( txtDataini, 38, 25, 95, 20, "De:" );		
		adic( txtDatafim, 178, 25, 95, 20, "At�:" );		

		adic( txtCodCli, 7, 80, 80, 20, "C�d.Cli" );		
		adic( txtRazCli, 90, 80, 225, 20, "Raz�o social do cliente" );

		adic( txtCodContr, 7, 130, 80, 20, "C�d.Contr." );
		adic( txtDescContr, 90, 130, 225, 20, "Descri��o do contrato" );
		
		adic( txtCodItContr, 7, 170, 80, 20, "C�d.It.Contr." );
		adic( txtDescItContr, 90, 170, 225, 20, "Descri��o do �tem de contrato" );
		
		adic( txtCodAtend, 7, 210, 80, 20, "C�d.Atend." );
		adic( txtNomeAtend, 90, 210, 225, 20, "Nome do atendente" );

		adic( cbTodos, 7, 250, 300, 20 );

		adic( cbSuprimirResumo, 7, 270, 300, 20 );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );

	}

	private void montaListaCampos() {

		// Cliente
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli, FCliente.class.getCanonicalName() );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		// Contrato

		lcContr.add( new GuardaCampo( txtCodContr, "CodContr", "C�d.Contr.", ListaCampos.DB_PK, false ) );
		lcContr.add( new GuardaCampo( txtDescContr, "DescContr", "Descri��o do contrato", ListaCampos.DB_SI, false ) );
		lcContr.montaSql( false, "CONTRATO", "VD" );
		lcContr.setReadOnly( true );
		lcContr.setDinWhereAdic( "CODCLI=#N ", txtCodCli );
		txtCodContr.setTabelaExterna( lcContr, FContrato.class.getCanonicalName() );
		txtCodContr.setFK( true );
		txtCodContr.setNomeCampo( "CodContr" );
		
		
		// Item de Contrato

		lcItContr.add( new GuardaCampo( txtCodItContr, "CodItContr", "C�d.It.Contr.", ListaCampos.DB_PK, false ) );
		lcItContr.add( new GuardaCampo( txtCodContr, "CodContr", "C�d.Contr.", ListaCampos.DB_PF, false ) );
		lcItContr.add( new GuardaCampo( txtDescItContr, "DescItContr", "Descri��o do �tem de contrato", ListaCampos.DB_SI, false ) );
		lcItContr.montaSql( false, "ITCONTRATO", "VD" );
		lcItContr.setReadOnly( true );
		lcItContr.setDinWhereAdic( "CODCONTR=#N ", txtCodContr );
		txtCodItContr.setTabelaExterna( lcItContr, FContrato.class.getCanonicalName() );
		txtCodItContr.setFK( true );
		txtCodItContr.setNomeCampo( "CodItContr" );


		// Atendente

		txtCodAtend.setTabelaExterna( lcAtend, FAtendente.class.getCanonicalName() );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );
		lcAtend.add( new GuardaCampo( txtCodAtend, "CodAtend", "C�d.atend.", ListaCampos.DB_PK, false ), "txtCodVendx" );
		lcAtend.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ), "txtCodVendx" );
		lcAtend.montaSql( false, "ATENDENTE", "AT" );
		lcAtend.setReadOnly( true );

	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		StringBuilder sqlcontrato = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int iparam = 1;
		int acumuloitcontr = 0;
		Date dtinicontr = new Date();
		try {
			if (txtCodItContr.getVlrInteger().intValue()!=0) {
				sqlcontrato.append("select i.acumuloitcontr, c.dtinicio from vditcontrato i, vdcontrato c ");
				sqlcontrato.append("where i.codemp=? and i.codfilial=? and i.codcontr=? and i.coditcontr=? and ");
				sqlcontrato.append("c.codemp=i.codemp and c.codfilial=i.codfilial and c.codcontr=i.codcontr ");
				ps = con.prepareStatement( sqlcontrato.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDITCONTRATO" ) );
				ps.setInt( 3, txtCodContr.getVlrInteger().intValue() );
				ps.setInt( 4, txtCodItContr.getVlrInteger().intValue() );
				rs = ps.executeQuery();
				if (rs.next()) {
					acumuloitcontr = rs.getInt( "acumuloitcontr" );
					dtinicontr = rs.getDate( "dtinicio" );
				}
				rs.close();
				ps.close();
				con.commit();
			}
			
			if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
				Funcoes.mensagemInforma( tela, "Data final maior que a data inicial!" );
				return;
			}
		
			sql.append( "select a.codtpatendo, a.codatend, a.dataatendo, a.dataatendofin, a.codatendo, a.codchamado, a.descchamado, " );
			sql.append( "a.horaatendo, a.horaatendofin, a.obsatendo, a.codatend, a.nomeatend, a.desctpatendo, a.razcli, a.statusatendo, " );
			sql.append( "a.totalgeral totalhoras, a.totalcobcli " );
			sql.append( "from atatendimentovw02 a " );
			sql.append( "where a.codemp=? and a.codfilial=? and a.dataatendo between ? and ?  and a.mrelcobespec='S' " );
	
			if ( txtCodContr.getVlrInteger().intValue() > 0 ) {
				sql.append( " and a.codempct=? and a.codfilialct=? and a.codcontr=? " );
			}
			
			if ( txtCodItContr.getVlrInteger().intValue() > 0 ) {
				sql.append( " and a.coditcontr=? " );
			}
			
			else if ( "N".equals( cbTodos.getVlrString() ) ) {
				sql.append( " and a.codcontr is null " );
			}
			if ( txtCodCli.getVlrInteger().intValue() > 0 ) {
				sql.append( " and a.codempcl=? and a.codfilialcl=? and a.codcli=? " );
			}
			if ( txtCodAtend.getVlrInteger().intValue() > 0 ) {
				sql.append( " and a.codempae=? and a.codfilialae=? and a.codatend=? " );
			}
	
			sql.append( " order by a.dataatendo, a.horaatendo " );
	
			System.out.println( "SQL:" + sql.toString() );

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );

			if ( txtCodContr.getVlrInteger().intValue() > 0 ) {
				ps.setInt( iparam++, lcContr.getCodEmp() );
				ps.setInt( iparam++, lcContr.getCodFilial() );
				ps.setInt( iparam++, txtCodContr.getVlrInteger() );
			}
			
			if ( txtCodItContr.getVlrInteger().intValue() > 0 ) {
				ps.setInt( iparam++, txtCodItContr.getVlrInteger() );
			}
			
			if ( txtCodCli.getVlrInteger().intValue() > 0 ) {
				ps.setInt( iparam++, lcCli.getCodEmp() );
				ps.setInt( iparam++, lcCli.getCodFilial() );
				ps.setInt( iparam++, txtCodCli.getVlrInteger() );
			}
			if ( txtCodAtend.getVlrInteger().intValue() > 0 ) {
				ps.setInt( iparam++, lcAtend.getCodEmp() );
				ps.setInt( iparam++, lcAtend.getCodFilial() );
				ps.setInt( iparam++, txtCodAtend.getVlrInteger() );
			}

			rs = ps.executeQuery();

		} catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, " Erro na consulta da tabela de atendimentos" );
		}

		imprimiGrafico( rs, bVisualizar, acumuloitcontr, dtinicontr );

	}

	private void imprimiGrafico( final ResultSet rs, final TYPE_PRINT bVisualizar, int acumuloitcontr, Date dtinicontr ) {

		FPrinterJob dlGr = null;
		int mescob = 0;
		HashMap<String, Object> hParam = new HashMap<String, Object>();
		Date dtiniac = txtDataini.getVlrDate();
		if (acumuloitcontr!=0) {
			dtiniac = Funcoes.somaMes( dtiniac, -1*acumuloitcontr ); 
			// Verificar se o contrato � novo para n�o calcular acumulo.
			if (dtinicontr.compareTo( dtiniac )>0) {
				dtiniac = dtinicontr;
				acumuloitcontr = Funcoes.contaMeses( dtiniac, txtDataini.getVlrDate() );
			}
		}
		
		mescob = Funcoes.getMes( txtDataini.getVlrDate() ); 
			
		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/" );
		hParam.put( "CODCLI", txtCodCli.getVlrInteger() );
		hParam.put( "DTINI", txtDataini.getVlrDate() );
		hParam.put( "DTFIM", txtDatafim.getVlrDate() );
		hParam.put( "DTINIAC", dtiniac );
		hParam.put( "ACUMULOITCONTR", new Integer(acumuloitcontr) );
		hParam.put( "MESCOB", new Integer(mescob) );
		hParam.put( "CODCONTR", txtCodContr.getVlrInteger() );
		hParam.put( "CODITCONTR", txtCodItContr.getVlrInteger() );
		hParam.put( "DESCCONTR", txtDescContr.getVlrString() );
		hParam.put( "DESCITCONTR", txtDescItContr.getVlrString() );
		hParam.put( "CONEXAO", con.getConnection() );
		hParam.put( "SUPRIMIRRESUMO", cbSuprimirResumo.getVlrString() );

		if ( txtCodCli.getVlrInteger().intValue() > 0 ) {
			hParam.put( "CLIENTE", txtCodCli.getVlrString().trim() + "-" + txtRazCli.getVlrString().trim() );
			dlGr = new FPrinterJob( "relatorios/atendimentos.jasper", "RELAT�RIO DE ATENDIMENTOS", "", rs, hParam, this );
		}
		else {
			hParam.put( "CLIENTE", "DIVERSOS" );
			dlGr = new FPrinterJob( "relatorios/atendimentos_cli.jasper", "RELAT�RIO DE ATENDIMENTOS", "", rs, hParam, this );
		}
		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.preview();
		}
		else {
			dlGr.print(true);
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcContr.setConexao( cn );
		lcItContr.setConexao( cn );
		lcAtend.setConexao( cn );
	}

}
