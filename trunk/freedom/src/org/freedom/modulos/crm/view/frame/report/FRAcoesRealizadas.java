/**
 * @version 28/09/2011 <BR>
 * @author Setpoint Inform�tica Ltda. / Bruno Nascimento<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)FRCronograma.java <BR>
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
 *         Relat�rio Cronograma Sint�tico.
 * 
 */

package org.freedom.modulos.crm.view.frame.report;

import java.math.BigDecimal;
import org.freedom.library.type.TYPE_PRINT;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
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
import org.freedom.modulos.crm.dao.DAOGestaoProj;
import org.freedom.modulos.crm.view.frame.crud.detail.FContrato;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

public class FRAcoesRealizadas extends FRelatorio implements CarregaListener{

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtCodCli2 = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtStatus = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCodContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodSubContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodTarefa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtDescContr = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtDescSubContr = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtDescTarefa = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtContHSubContr = new JTextFieldFK( JTextFieldFK.TP_STRING, 2, 0 );
	
	private Vector<String> vLabsSaldo = new Vector<String>();
	
	private Vector<String> vValsSaldo = new Vector<String>();
	
	private JRadioGroup<String, String> rgSaldoHoras = null;

	private ListaCampos lcCli = new ListaCampos( this );
	
	private ListaCampos lcContr = new ListaCampos( this );
	
	private ListaCampos lcContrFilho = new ListaCampos( this );
	
	private ListaCampos lcTarefa = new ListaCampos( this );
	
	private ListaCampos lcAtend = new ListaCampos( this );
	//private ListaCampos lcItContr = new ListaCampos( this );
	
	private DAOGestaoProj daogestao = null;
	
	public FRAcoesRealizadas() {		
		setTitulo( "A��es realizadas" );
		setAtribos( 80, 80, 410	, 340 );
		
		montaListaCampos();
		montaTela();
		
	}
	
	private void montaTela(){
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
		adic( txtCodCli, 7, 80, 80, 20, "Cod.Cli" );
		adic( txtRazCli, 90, 80, 225, 20, "Raz�o social do cliente" );
		adic( txtCodContr, 7, 120, 80, 20, "Cod.Contr");
		adic( txtDescContr, 90, 120, 225, 20, "Descri��o do Contrato" );
		adic( txtCodSubContr, 7, 160, 80, 20, "Cod.sub.contr" );
		adic( txtDescSubContr, 90, 160, 225, 20, "Descri��o do Sub Contrato" );
		adic( txtCodTarefa, 7, 200, 80, 20, "Cod.tarefa" );
		adic( txtDescTarefa, 90, 200, 225, 20, "Descri��o da Tarefa" );
		adic( txtCodAtend, 7, 240, 80, 20, "Cod.Atend" );
		adic( txtNomeAtend, 90, 240, 225, 20, "Nome do Atendente" );
				
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
	
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}
	
	private void montaListaCampos() {
		
		
		/**********************
		 * Tarefa  * *
		 *******************/
		lcTarefa.setQueryCommit( false );
		lcTarefa.setReadOnly( true );
		lcTarefa.add( new GuardaCampo( txtCodTarefa, "CodTarefa", "C�d.Tarefa", ListaCampos.DB_PK, txtDescTarefa, false ) );
		lcTarefa.add( new GuardaCampo( txtDescTarefa, "DescTarefa" , "Descri��o da tarefa", ListaCampos.DB_SI, false ) );
		lcTarefa.setDinWhereAdic( "CodContr=#N", txtCodSubContr );
		lcTarefa.montaSql( false, "TAREFA", "CR" );
		txtCodTarefa.setTabelaExterna( lcTarefa, null );
		txtCodTarefa.setFK( true );
		txtCodTarefa.setNomeCampo( "CODTAREFA" );
		
		/**********************
		 * Cliente * *
		 *******************/
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, true ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli, FCliente.class.getCanonicalName() );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		
		/**********************
		 * Contrato  * *
		 *******************/

		lcContr.add( new GuardaCampo( txtCodContr, "CodContr", "C�d.Contr.", ListaCampos.DB_PK, true ) );
		lcContr.add( new GuardaCampo( txtDescContr, "DescContr", "Descri��o do contrato", ListaCampos.DB_SI, false ) );
		lcContr.add( new GuardaCampo( txtContHSubContr, "ContHSubContr", "Cont.HSubContr.", ListaCampos.DB_SI, false ) );
		lcContr.add( new GuardaCampo( txtCodCli2, "CodCli", "C�d.cli", ListaCampos.DB_SI, false ) );
		lcContr.setDinWhereAdic( "CodCli=#N", txtCodCli );
		lcContr.montaSql( false, "CONTRATO", "VD" );
		lcContr.setReadOnly( true );
	
		txtCodContr.setTabelaExterna( lcContr, FContrato.class.getCanonicalName() );
		txtCodContr.setFK( true );
		txtCodContr.setNomeCampo( "CodContr" );
		
		/**********************
		 * Item do Contrato  * *
		 *******************/
		/*
		lcItContr.add( new GuardaCampo( txtCodItContr, "CoditContr", "C�d.Contr.", ListaCampos.DB_PK, false ) );
		lcItContr.add( new GuardaCampo( txtDescItContr, "Descitcontr", "Descri��o do item do contrato", ListaCampos.DB_SI, false ) );
		lcItContr.montaSql( false, "ITCONTRATO", "VD" );
		lcItContr.setDinWhereAdic( "CodContr=#N", txtCodContr );
		lcItContr.setReadOnly( true );
		txtCodItContr.setTabelaExterna( lcItContr, FContrato.class.getCanonicalName() );
		txtCodItContr.setFK( true );
		txtCodItContr.setNomeCampo( "CoditContr" );
		*/
	
		lcContrFilho.add( new GuardaCampo( txtCodSubContr, "CodContr", "C�d.Contr.", ListaCampos.DB_PK, txtDescSubContr, false ) );
		lcContrFilho.add( new GuardaCampo( txtDescSubContr, "DescContr", "Descri��o do contrato", ListaCampos.DB_SI, false ) );
		lcContrFilho.setDinWhereAdic( "CodContrSp=#N", txtCodContr );
		lcContrFilho.montaSql( false, "CONTRATO", "VD" );
		lcContrFilho.setReadOnly( true );
	
		txtCodSubContr.setTabelaExterna( lcContrFilho, FContrato.class.getCanonicalName() );
		txtCodSubContr.setFK( true );
		txtCodSubContr.setNomeCampo( "CodContr" );
		
		
		
	
		lcAtend.add( new GuardaCampo( txtCodAtend, "CodAtend", "C�d.atend.", ListaCampos.DB_PK, false ), "txtCodVendx" );
		lcAtend.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ), "txtCodVendx" );
		lcAtend.montaSql( false, "ATENDENTE", "AT" );
		lcAtend.setReadOnly( true );
		
		txtCodAtend.setTabelaExterna( lcAtend, null );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );
			
		lcCli.addCarregaListener( this );
		lcContr.addCarregaListener( this );
		//lcItContr.addCarregaListener( this );
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {
		
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data inicial maior que a data final!" );
			return;
		}
		
		String sCab = "";
		String sTitle = "";
		String Ordem = "";
		Blob fotoemp = null;
		BigDecimal totgeral = null;
		BigDecimal totcob = null;
	
		try {
			PreparedStatement ps = con.prepareStatement( daogestao.getTotaisAcao( txtContHSubContr.getVlrString(), txtCodSubContr.getVlrInteger(), txtCodTarefa.getVlrInteger(), txtCodAtend.getVlrInteger() ) );
			daogestao.setParamsQueryAcao(  ps, Aplicativo.iCodEmp , ListaCampos.getMasterFilial( "VDCONTRATO" ) , 
					txtCodContr.getVlrInteger(), txtDataini.getVlrDate(),txtDatafim.getVlrDate(),txtCodSubContr.getVlrInteger(), txtCodTarefa.getVlrInteger(), txtCodAtend.getVlrInteger() );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				totgeral = rs.getBigDecimal( "totgeral" );
				totcob = rs.getBigDecimal( "totcob" );
			}
			
			rs.close();
			ps.close();
			con.commit();

		} catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro carregando totais.\n" + e.getMessage() );
			e.printStackTrace();
		}			
		
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
		
		sCab = txtCodCli.getVlrInteger().toString() + " - " + txtRazCli.getVlrString() + " \n" + "Per�odo de " + txtDataini.getVlrString()  + " a " +  txtDatafim.getVlrString();
		sTitle = txtCodContr.getVlrInteger().toString() + " - " + txtDescContr.getVlrString();
		ResultSet rs = null;

		try{
			PreparedStatement ps = con.prepareStatement( daogestao.getQueryAcao(  txtContHSubContr.getVlrString(), txtCodSubContr.getVlrInteger(), txtCodTarefa.getVlrInteger(),txtCodAtend.getVlrInteger() ) );
			daogestao.setParamsQueryAcao(  ps, Aplicativo.iCodEmp , ListaCampos.getMasterFilial( "VDCONTRATO" ) , 
					txtCodContr.getVlrInteger(), txtDataini.getVlrDate(),txtDatafim.getVlrDate(),txtCodSubContr.getVlrInteger(), txtCodTarefa.getVlrInteger(), txtCodAtend.getVlrInteger() );
			rs = ps.executeQuery();

		} catch (Exception err) {
			Funcoes.mensagemErro( this, "Erro consulta Relat�rio A��es Realizadas\n" + err.getMessage(), true, con, err );
		}

		imprimiGrafico( bVisualizar, rs,  sCab, sTitle, fotoemp, totgeral, totcob );

	}

	private void imprimiGrafico( TYPE_PRINT bVisualizar, ResultSet rs, String sCab, String sTitle, Blob fotoemp, BigDecimal totgeral, BigDecimal totcob) {
		String report = "layout/rel/REL_ACOES_REALIZADAS.jasper";
		String label = "Relat�rio de A��es realizadas";
		
	    HashMap<String, Object> hParam = new HashMap<String, Object>();
		hParam.put( "SUBREPORT_DIR", "org/freedom/layout/rel/" );
		hParam.put( "CODEMP", new Integer(Aplicativo.iCodEmp) );
		hParam.put( "CODFILIAL", new Integer(ListaCampos.getMasterFilial( "VDCONTRATO" )) );
		hParam.put( "CODCONTR", txtCodContr.getVlrInteger() );
		hParam.put( "TITULO", sTitle );
		hParam.put( "totgeral", totgeral );
		hParam.put( "totcob", totcob );
		
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
				dlGr.print(true);
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o do relat�rio de A��es realizadas!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcContr.setConexao( cn );
		lcTarefa.setConexao( cn );
		lcContrFilho.setConexao( cn );
		lcAtend.setConexao( cn );
		
		daogestao = new DAOGestaoProj( cn );
	}

	public void afterCarrega( CarregaEvent cevt ) {
		if(cevt.getListaCampos() == lcContr ){
			if( txtCodContr.getVlrInteger() !=  txtCodCli2.getVlrInteger() ){
				txtCodCli.setVlrInteger( txtCodCli2.getVlrInteger() );
				lcCli.carregaDados();
			}	
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

}
