/**
 * @version 30/03/2011 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm <BR>
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
 *         Relat�rio resumido de atendimentos por atendente
 * 
 */

package org.freedom.modulos.crm.view.frame.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;
import org.freedom.modulos.atd.view.frame.crud.plain.FAtendente;
import org.freedom.modulos.crm.view.frame.crud.plain.FEspecAtend;

public class FRResumoAtendente extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private ListaCampos lcCli = new ListaCampos( this );

	private JRadioGroup< ? , ?> rgTipo = null;
	
	private JRadioGroup<?, ?> rgPremiacao = null;
	
	private ListaCampos lcAtendente = new ListaCampos( this, "AE" );
	
	private ListaCampos lcEspecAtend = new ListaCampos( this, "EA" );
	
	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodEspec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescEspec = new JTextFieldFK( JTextFieldPad.TP_STRING, 1000, 0 );

	
	public FRResumoAtendente() {

		setTitulo( "Relat�rio de atendimentos/estat�stico" );
		
		setAtribos( 80, 80, 390	, 393 );

		
		montaRadioGrupos();
		montaListaCampos();
		montaTela();


	}
	
	private void montaRadioGrupos() {
		
		Vector<String> vLabs0 = new Vector<String>();
		Vector<String> vVals0 = new Vector<String>();
		
		vLabs0.addElement( "Detalhado" );
		vLabs0.addElement( "Resumido" );
		vLabs0.addElement( "Especifica��o");
		vLabs0.addElement( "Projeto");
		
		vVals0.addElement( "D" );
		vVals0.addElement( "R" );
		vVals0.addElement( "E" );
		vVals0.addElement( "P" );
		
		rgTipo = new JRadioGroup<String, String>( 2, 3, vLabs0, vVals0 );
		rgTipo.setVlrString( "R" );
		
		Vector<String> vLabs1 = new Vector<String>();
		Vector<String> vVals1 = new Vector<String>();
		
		vLabs1.addElement( "Sim" );
		vLabs1.addElement( "N�o" );
		vLabs1.addElement( "Ambos");
		
		vVals1.addElement( "S" );
		vVals1.addElement( "N" );
		vVals1.addElement( "A" );
		
		rgPremiacao  = new JRadioGroup<String, String>( 1, 3, vLabs1, vVals1 );
		rgPremiacao.setVlrString( "A" );
		
	}
	

	public void setParametros( Integer codcli, Date dtini, Date dtfim ) {

		txtDataini.setVlrDate( dtini );
		txtDatafim.setVlrDate( dtfim );
		
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Per�odo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 300, 45 );
		
		adic( txtDataini, 38, 25, 95, 20 );		
		adic( txtDatafim, 178, 25, 95, 20 );	
		
		adic (txtCodCli, 7, 85, 80, 20, "C�d.Cli.");
		adic (txtNomeCli, 90, 85, 215, 20, "Nome do cliente");
		
		adic (txtCodAtend, 7, 125, 80, 20, "C�d.Atend.");
		adic (txtNomeAtend, 90, 125, 215, 20, "Nome do atendente");
		
		adic (txtCodEspec, 7, 165, 80, 20, "C�d.Espec.");
		adic (txtDescEspec, 90, 165, 215, 20, "Descri��o da especifica��o");
		
		adic( rgTipo, 7, 200, 300, 60 );
		
		adic( rgPremiacao, 7, 280, 300, 30, "Participa��o em Premia��es");

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
	
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );

	}

	private void montaListaCampos() {
		
		txtCodAtend.setTabelaExterna( lcAtendente, FAtendente.class.getCanonicalName() );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );
		lcAtendente.add( new GuardaCampo( txtCodAtend, "CodAtend", "C�d.atend.", ListaCampos.DB_PK, false ) );
		lcAtendente.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ) );
		lcAtendente.montaSql( false, "ATENDENTE", "AT" );
		lcAtendente.setReadOnly( true );
		
		txtCodCli.setTabelaExterna( lcCli, FAtendente.class.getCanonicalName() );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.Cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "NomeCli", "Nome do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		
		txtCodEspec.setTabelaExterna( lcEspecAtend, FEspecAtend.class.getCanonicalName() );
		txtCodEspec.setFK( true );
		txtCodEspec.setNomeCampo( "CodEspec" );
		lcEspecAtend.add( new GuardaCampo( txtCodEspec, "CodEspec", "C�d.Espec.", ListaCampos.DB_PK, false ) );
		lcEspecAtend.add( new GuardaCampo( txtDescEspec, "DescEspec", "Descri��o da especifica��o", ListaCampos.DB_SI, false ) );
		lcEspecAtend.montaSql( false, "ESPECATEND", "AT" );
		lcEspecAtend.setReadOnly( true );


	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		StringBuilder sCab =  new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int iparam = 1;

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		
		if("R".equals( rgTipo.getVlrString() )) {
						
			sql.append( "select a.anoatendo, a.mesatendo, a.nomeatend, sum(a.totalgeral) totalgeral, ");
			sql.append( "sum(a.totalmeta) totalmeta, sum(a.totalcomis) totalcomis, ");
			sql.append( "sum(a.totalcobcli) totalcobcli ");
			sql.append( "from atatendimentovw02 a ");
			
			sql.append( "where ");
			
			sql.append( "a.codemp=? and a.codfilial=? and a.dataatendo between ? and ? ");

			if(txtCodCli.getVlrInteger()>0) {
			
				sql.append( "and a.codempcl=? and a.codfilialcl=? and a.codcli=? " );
				sCab.append(txtCodCli.getVlrInteger().toString() + " - " + txtNomeCli.getVlrString() + " | " );
			}
			
			if(txtCodAtend.getVlrInteger()>0) {
				
				sql.append( "and a.codempae=? and a.codfilialae=? and a.codatend=? " );
				sCab.append( txtCodAtend.getVlrInteger().toString() + " - " + txtNomeAtend.getVlrString() + " | " );
			}
			
			if(txtCodEspec.getVlrInteger()>0) {
				
				sql.append( "and a.codempea=? and a.codfilialea=? and a.codespec=? " );
				sCab.append( "Especifica��o - " + txtDescEspec.getVlrString() + " | ");
			}
			if ( ! "A".equals( rgPremiacao.getVlrString() ) ) {
				
				sql.append( "and a.partpremiatend=? " );
				sCab.append( "Premia��o: " + rgPremiacao.getVlrString() + " | " );
			}
			sql.append( "group by a.anoatendo, a.mesatendo, a.nomeatend " );
			sql.append( "order by a.anoatendo, a.mesatendo, a.nomeatend" );
			
			sCab.append(   "Per�odo de " + txtDataini.getVlrString()  + " a " +  txtDatafim.getVlrString() );
		}
		else if ("D".equals( rgTipo.getVlrString() )) {
			
			if( ! (txtCodAtend.getVlrInteger() > 0) ) {
				
				Funcoes.mensagemInforma( this, "Informe um atendente!" );
				txtCodAtend.requestFocus();
				return;
				
			}
			
			sql.append( "select a.dataatendo, a.horaatendo, a.horaatendofin, a.nomeatend, ");
			sql.append( "a.obsatendo, a.codcli, a.nomecli, a.totalgeral, ");
			sql.append( "a.totalmeta, a.totalcomis, a.totalcobcli, ");
			sql.append( "a.codespec, a.descespec " );
			sql.append( "from atatendimentovw02 a ");
			sql.append( "where ");
			sql.append( "a.codemp=? and a.codfilial=? and a.dataatendo between ? and ? ");
			 
			if(txtCodCli.getVlrInteger()>0) {
				
				sql.append( "and a.codempcl=? and a.codfilialcl=? and a.codcli=? " );
				sCab.append(txtCodCli.getVlrInteger().toString() + " - " + txtNomeCli.getVlrString() + " | " );
			}
			
			if(txtCodAtend.getVlrInteger()>0) {
				
				sql.append( "and a.codempae=? and a.codfilialae=? and a.codatend=? " );
				sCab.append( txtCodAtend.getVlrInteger().toString() + " - " + txtNomeAtend.getVlrString() + " | " );
			}
			
			if(txtCodEspec.getVlrInteger()>0) {
				
				sql.append( "and a.codempea=? and a.codfilialea=? and a.codespec=? " );
				sCab.append( "Especifica��o - " + txtDescEspec.getVlrString() + " | ");
			}

			if ( ! "A".equals( rgPremiacao.getVlrString() ) ) {
				
				sql.append( "and a.partpremiatend=? " );
				sCab.append( "Premia��o: " + rgPremiacao.getVlrString() + " | " );
			}
			
			sql.append( "order by a.dataatendo, a.horaatendo ");
			sCab.append(   "Per�odo de " + txtDataini.getVlrString()  + " a " +  txtDatafim.getVlrString() );
			
		} else if ("E".equals( rgTipo.getVlrString() )) {
			
			sql.append( "select a.descespec, a.codespec, sum(a.totalgeral) totalgeral, ");
			sql.append( "sum(a.totalmeta) totalmeta, sum(a.totalcomis) totalcomis, sum(a.totalcobcli) totalcobcli ");
			sql.append( "from atatendimentovw02 a ");
			sql.append( "where ");
			sql.append( "a.codemp=? and a.codfilial=? and a.dataatendo between ? and ? ");
			 
			if(txtCodCli.getVlrInteger()>0) {
				
				sql.append( "and a.codempcl=? and a.codfilialcl=? and a.codcli=? " );
				sCab.append(txtCodCli.getVlrInteger().toString() + " - " + txtNomeCli.getVlrString() + " | " );
			}
			
			if(txtCodAtend.getVlrInteger()>0) {
				
				sql.append( "and a.codempae=? and a.codfilialae=? and a.codatend=? " );
				sCab.append( txtCodAtend.getVlrInteger().toString() + " - " + txtNomeAtend.getVlrString() + " | " );
			}
			
			if(txtCodEspec.getVlrInteger()>0) {
				
				sql.append( "and a.codempea=? and a.codfilialea=? and a.codespec=? " );
				sCab.append( "Especifica��o - " + txtDescEspec.getVlrString() + " | ");
			}
			if ( ! "A".equals( rgPremiacao.getVlrString() ) ) {
				
				sql.append( "and a.partpremiatend=? " );
				sCab.append( "Premia��o: " + rgPremiacao.getVlrString() + " | " );
			}
			
			sql.append( "group by a.descespec, a.codespec ");
			sql.append( "order by 3 desc, 4 desc, 5 desc, a.descespec, a.codespec" );
			sCab.append(   "Per�odo de " + txtDataini.getVlrString()  + " a " +  txtDatafim.getVlrString() );
		} else	if("P".equals( rgTipo.getVlrString() )) {
						
			sql.append( "select a.anoatendo, a.mesatendo, a.nomeatend, " );
			sql.append( "c.codcontr, c.desccontr, ic.coditcontr, ic.descitcontr, ");
			sql.append( "sum(a.totalgeral) totalgeral, ");
			sql.append( "sum(a.totalmeta) totalmeta, sum(a.totalcomis) totalcomis, ");
			sql.append( "sum(a.totalcobcli) totalcobcli ");
			sql.append( "from atatendimentovw02 a ");
			sql.append( "left outer join vdcontrato c on ");
			sql.append( "c.codemp=a.codempct and c.codfilial=a.codfilialct and c.codcontr=a.codcontr ");
			sql.append( "left outer join vditcontrato ic on ");
			sql.append( "ic.codemp=a.codempct and ic.codfilial=a.codfilialct and ic.codcontr=a.codcontr and ic.coditcontr=a.coditcontr ");
			
			sql.append( "where ");
			
			sql.append( "a.codemp=? and a.codfilial=? and a.dataatendo between ? and ? ");

			if(txtCodCli.getVlrInteger()>0) {
			
				sql.append( "and a.codempcl=? and a.codfilialcl=? and a.codcli=? " );
				sCab.append(txtCodCli.getVlrInteger().toString() + " - " + txtNomeCli.getVlrString() + " | " );
			}
			
			if(txtCodAtend.getVlrInteger()>0) {
				
				sql.append( "and a.codempae=? and a.codfilialae=? and a.codatend=? " );
				sCab.append( txtCodAtend.getVlrInteger().toString() + " - " + txtNomeAtend.getVlrString() + " | " );
			}
			
			if(txtCodEspec.getVlrInteger()>0) {
				
				sql.append( "and a.codempea=? and a.codfilialea=? and a.codespec=? " );
				sCab.append( "Especifica��o - " + txtDescEspec.getVlrString() + " | ");
			}
			if ( ! "A".equals( rgPremiacao.getVlrString() ) ) {
				
				sql.append( "and a.partpremiatend=? " );
				sCab.append( "Premia��o: " + rgPremiacao.getVlrString() + " | " );
			}
			sql.append( "group by a.nomeatend, a.anoatendo, a.mesatendo, " );
			sql.append( "c.codcontr, c.desccontr, ic.coditcontr, ic.descitcontr ");
			sql.append( "order by a.nomeatend, a.anoatendo, a.mesatendo, " );
			sql.append( "c.desccontr, ic.descitcontr ");
			
			sCab.append(   "Per�odo de " + txtDataini.getVlrString()  + " a " +  txtDatafim.getVlrString() );
		}

		
		
		System.out.println( "SQL:" + sql.toString() );

		try {

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			
			if(txtCodCli.getVlrInteger()>0) {
				
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
				ps.setInt( iparam++, txtCodCli.getVlrInteger() );
				
			}
			
			if(txtCodAtend.getVlrInteger()>0) {
				
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATATENDENTE" ) );
				ps.setInt( iparam++, txtCodAtend.getVlrInteger() );
				
			}
			
			if(txtCodEspec.getVlrInteger()>0) {
				
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "ATESPECATEND" ) );
				ps.setInt( iparam++, txtCodEspec.getVlrInteger() );
				
			}

			if ( ! "A".equals( rgPremiacao.getVlrString() ) ) {
				ps.setString( iparam++, rgPremiacao.getVlrString() );
			}
			
			rs = ps.executeQuery();
		
		} 
		catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, " Erro na consulta da view de resumo de atendimentos" );
			
		}

		imprimiGrafico( rs, bVisualizar, sCab );

	}

	private void imprimiGrafico( final ResultSet rs, final TYPE_PRINT bVisualizar, StringBuilder sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/" );
		hParam.put( "DTINI", txtDataini.getVlrDate() );
		hParam.put( "DTFIM", txtDatafim.getVlrDate() );
		hParam.put( "CONEXAO", con.getConnection() );

		if("R".equals( rgTipo.getVlrString() )) {
			dlGr = new FPrinterJob( "layout/rel/REL_CRM_RESUMO_ATENDENTE_01.jasper", "Resumo de atendimentos por atendente (Resumido)", sCab.toString() , rs, hParam, this );
		}
		else if ("D".equals( rgTipo.getVlrString() )) {
			dlGr = new FPrinterJob( "layout/rel/REL_CRM_DETALHAMENTO_ATENDENTE_01.jasper", "Resumo de atendimentos por atendente (Detalhado)", sCab.toString(), rs, hParam, this );
		}
		else if ("E".equals( rgTipo.getVlrString() )) {
			dlGr = new FPrinterJob( "layout/rel/REL_CRM_RESUMO_ESPECIFICACAO_01.jasper", "Resumo de atendimentos por especifica��o", sCab.toString(), rs, hParam, this );
		}
		else if ("P".equals( rgTipo.getVlrString() )) {
			dlGr = new FPrinterJob( "layout/rel/REL_CRM_RESUMO_PROJETO_01.jasper", "Resumo de atendimentos por projeto", sCab.toString(), rs, hParam, this );
		}
		
		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio resumo de atendimentos!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		lcCli.setConexao( cn );
		lcAtendente.setConexao( cn );
		lcEspecAtend.setConexao( cn );
		
	}

}
