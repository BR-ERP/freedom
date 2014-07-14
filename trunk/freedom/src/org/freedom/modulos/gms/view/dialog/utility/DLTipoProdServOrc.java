/**
 * @version 21/08/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLTipoOrcamento.java <BR>
 * 
 *                 Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                 modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                 na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                 Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                 sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                 Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                 Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                 de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Dialog para sele��o dos tipos de produtos deve ser gerados em or�amento de ordem de sevi�o.
 */

package org.freedom.modulos.gms.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.std.view.frame.crud.detail.FPlanoPag;

public class DLTipoProdServOrc extends FFDialogo implements CarregaListener {

	private static final long serialVersionUID = 1L;

	public static final short COMPONENTES = 0;
	
	public static final short SERVICOS = 1;
	
	public static final short NOVOS = 2;
	
	private JCheckBoxPad cbComponentes = new JCheckBoxPad( "Componentes?", "S", "N" );
	
	private JCheckBoxPad cbServicos = new JCheckBoxPad( "Servi�os?", "S", "N" );
	
	private JCheckBoxPad cbNovos = new JCheckBoxPad( "Produtos novos?", "S", "N" );
	
//	private JLabelPad lbTitulo = new JLabelPad( "Selecione os tipos de produto/servi�o que devem ser or�ados." );
	
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtCNPJCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	
	private JTextFieldPad txtCodFiscCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );
	
	private ListaCampos lcTipoFiscCli = new ListaCampos( this, "FC" );
	
	private JTextFieldFK txtDescFiscCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldFK txtCalcISSTF = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );
	
	private ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );
	
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private Integer codcli = null;

	public DLTipoProdServOrc( Component orig ) {

		super( orig );

		setConexao( Aplicativo.getInstance().con );
		
		// Tipo fiscal do cliente
		lcTipoFiscCli.add( new GuardaCampo( txtCodFiscCli, "CodFiscCli", "Cod.Fisc.Cli.", ListaCampos.DB_PK, false ) );
		lcTipoFiscCli.add( new GuardaCampo( txtDescFiscCli, "DescFiscCli", "Descri��o do tipo fiscal do cliente", ListaCampos.DB_SI, false ) );
		lcTipoFiscCli.add( new GuardaCampo( txtCalcISSTF, "CalcISSTF", "Tipo fiscal com destaque de ISS", ListaCampos.DB_SI, false ) );
		lcTipoFiscCli.montaSql( false, "TIPOFISCCLI", "LF" );
		lcTipoFiscCli.setQueryCommit( false );
		lcTipoFiscCli.setReadOnly( true );
		txtCodFiscCli.setTabelaExterna( lcTipoFiscCli, null );
		
		// FK Plano de pagamento
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_PK, true ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.setWhereAdic( "ATIVOPLANOPAG='S' AND CVPLANOPAG IN ('V','A')" );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag, FPlanoPag.class.getCanonicalName() );
		txtDescPlanoPag.setListaCampos( lcPlanoPag );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		
		// * Cliente

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.Cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCNPJCli, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCNPJCli, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCodFiscCli, "CodFiscCli", "C�d.Fisc.", ListaCampos.DB_FK, false ) );
		lcCli.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.Plan.Pag.", ListaCampos.DB_FK, true ) );

		txtCodCli.setTabelaExterna( lcCli, null );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );

		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		
		setTitulo( "Tipo de produto/servi�o" );
		setAtribos( 520, 300 );

//		adic( lbTitulo, 7, 0, 353, 60 );
		
		adic( txtCodCli, 7,20,80,20,"C�d.Cli." );		
		adic( txtRazCli, 90,20,300,20,"C�d.Cli." );
		adic( txtDescFiscCli, 393,20,100,20,"Tipo Fiscal" );
		
		adic( txtCodPlanoPag, 7,60,80,20,"C�d.Plan.Pg." );		
		adic( txtDescPlanoPag, 90,60,300,20,"Descri��o do plano de pagamento" );
		
		adic( cbComponentes, 7, 100, 200, 20 );
		adic( cbServicos, 7, 130, 200, 20 );
		adic( cbNovos, 7, 160, 200, 20 );

//		cbComponentes.setVlrString( "S" );
//		cbServicos.setVlrString( "S" );
		
		txtCodCli.setEnabled( false );
		
		lcTipoFiscCli.addCarregaListener( this );
		
	}

	public String getComponentes() {
		return cbComponentes.getVlrString();
	}
	
	public String getServicos() {
		return cbServicos.getVlrString();
	}

	public String getNovos() {
		return cbNovos.getVlrString();
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			super.actionPerformed( evt );
		}
		else if ( evt.getSource() == btCancel ) {
			super.actionPerformed( evt );
		}
	}

	
	public Integer getCodcli() {
	
		return codcli;
	}

	
	public void setCodcli( Integer codcli ) {
	
		this.codcli = codcli;
		
		txtCodCli.setVlrInteger( codcli );
		lcCli.carregaDados();
						
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcCli.setConexao( cn );
		lcTipoFiscCli.setConexao( cn );
		lcPlanoPag.setConexao( cn );

	}
	
	public Integer getPlanoPag() {
		return txtCodPlanoPag.getVlrInteger();
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if( cevt.getListaCampos() == lcTipoFiscCli ) {
			if("S".equals( txtCalcISSTF.getVlrString() )) {
				cbServicos.setVlrString( "S" );
			}
			else {
				cbNovos.setVlrString( "S" );
			}
		}
		
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		// TODO Auto-generated method stub
		
	}

	
}



