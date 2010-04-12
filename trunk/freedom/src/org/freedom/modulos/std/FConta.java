/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FConta.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.ListaCampos;
import org.freedom.library.Navegador;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.telas.FTabDados;

public class FConta extends FTabDados implements CheckBoxListener, RadioGroupListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtAgConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );

	private JTextFieldPad txtNumConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDescConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtDataConta = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtCodPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescPlan = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JCheckBoxPad cbAtivaConta = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad chbRestritoTipoMov = new JCheckBoxPad( "Todos os usu�rios?", "S", "N" );

	private Vector<String> vValsTipo = new Vector<String>();

	private Vector<String> vLabsTipo = new Vector<String>();

	private JRadioGroup<?, ?> rgTipo = null;

	private Vector<String> vValsTipoCaixa = new Vector<String>();

	private Vector<String> vLabsTipoCaixa = new Vector<String>();
	
	private JRadioGroup<?, ?> rgTipoCaixa = null;

	private ListaCampos lcBanco = new ListaCampos( this, "BO" );

	private ListaCampos lcMoeda = new ListaCampos( this, "MA" );

	private ListaCampos lcPlan = new ListaCampos( this, "PN" );

	private ListaCampos lcRestricoes = new ListaCampos( this, "" );

	private ListaCampos lcUsu = new ListaCampos( this, "US" );

	private ListaCampos lcHist = new ListaCampos( this, "HP" );

	private JPanelPad pinCamposGeral = new JPanelPad( 430, 400 );

	private JPanelPad pinCamposContabil = new JPanelPad( 430, 400 );

	private JPanelPad pnRestricoes = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinDetRestricoes = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTablePad tbRestricoes = new JTablePad();

	private JScrollPane spnRestricoes = new JScrollPane( tbRestricoes );

	private JPanelPad pinNavRestricoes = new JPanelPad( 680, 30 );

	private JPanelPad pinCamposRestricoes = new JPanelPad( 430, 400 );

	private Navegador navRestricoes = new Navegador( true );

	private JTextFieldPad txtIDUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtNomeUsu = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodContDeb = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodContCred = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodForContab = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JTextFieldPad txtCodHistPad = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescHistPad = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );
		
	public FConta() {

		super( false );
		setTitulo( "Cadastro de Contas" );
		setAtribos( 50, 50, 420, 380 );

		lcRestricoes.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcRestricoes );
		lcRestricoes.setTabela( tbRestricoes );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );

		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "C�d.mda.", ListaCampos.DB_PK, true ) );
		lcMoeda.add( new GuardaCampo( txtDescMoeda, "SingMoeda", "Descri��o da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.montaSql( false, "MOEDA", "FN" );
		lcMoeda.setQueryCommit( false );
		lcMoeda.setReadOnly( true );
		txtCodMoeda.setTabelaExterna( lcMoeda );

		lcPlan.add( new GuardaCampo( txtCodPlan, "CodPlan", "C�d.planj.", ListaCampos.DB_PK, true ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descri��o do plano de contas", ListaCampos.DB_SI, false ) );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlan.setQueryCommit( false );
		lcPlan.setReadOnly( true );
		txtCodPlan.setTabelaExterna( lcPlan );

		lcUsu.add( new GuardaCampo( txtIDUsu, "IDUsu", "ID", ListaCampos.DB_PK, txtNomeUsu, false ) );
		lcUsu.add( new GuardaCampo( txtNomeUsu, "NomeUsu", "Nome nome do usu�rio", ListaCampos.DB_SI, false ) );
		lcUsu.montaSql( false, "USUARIO", "SG" );
		lcUsu.setQueryCommit( false );
		lcUsu.setReadOnly( true );
		txtIDUsu.setFK( true );
		txtIDUsu.setNomeCampo( "IDUsu" );
		txtIDUsu.setTabelaExterna( lcUsu );
		
		lcHist.add( new GuardaCampo( txtCodHistPad, "CodHist", "C�d.hist.", ListaCampos.DB_PK, false ) );
		lcHist.add( new GuardaCampo( txtDescHistPad, "DescHist", "Descri��o do historico padr�o", ListaCampos.DB_SI, false ) );
		lcHist.montaSql( false, "HISTPAD", "FN" );
		lcHist.setQueryCommit( false );
		lcHist.setReadOnly( true );
		txtCodHistPad.setTabelaExterna( lcHist );

		vValsTipo.addElement( "B" );
		vValsTipo.addElement( "C" );
		vLabsTipo.addElement( "Bancos" );
		vLabsTipo.addElement( "Caixa" );
		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabsTipo, vValsTipo );

		vValsTipoCaixa.addElement( "P" );
		vValsTipoCaixa.addElement( "F" );
		vLabsTipoCaixa.addElement( "Previs�o" );
		vLabsTipoCaixa.addElement( "F�sico" );
		rgTipoCaixa = new JRadioGroup<String, String>( 1, 2, vLabsTipoCaixa, vValsTipoCaixa );

		rgTipoCaixa.setAtivo( false );
		
		rgTipo.addRadioGroupListener( this );
		
		/***************
		 *  ABA GERAL  *
		 ***************/
		setPainel( pinCamposGeral );

		adicTab( "Geral", pinCamposGeral );

		adicCampo( txtNumConta, 7, 20, 140, 20, "NumConta", "N� da conta", ListaCampos.DB_PK, true );
		adicCampo( txtDescConta, 150, 20, 240, 20, "DescConta", "Descri��o da conta", ListaCampos.DB_SI, true );
		adicCampo( txtAgConta, 7, 60, 75, 20, "AgenciaConta", "Ag�ncia", ListaCampos.DB_SI, false );
		adicCampo( txtCodBanco, 85, 60, 62, 20, "CodBanco", "C�d.banco", ListaCampos.DB_FK, false );
		adicDescFK( txtDescBanco, 150, 60, 240, 20, "NomeBanco", "Nome do banco" );
		adicCampo( txtDataConta, 7, 100, 75, 20, "DataConta", "Data", ListaCampos.DB_SI, true );
		adicCampo( txtCodMoeda, 85, 100, 62, 20, "CodMoeda", "C�d.mda.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescMoeda, 150, 100, 240, 20, "SingMoeda", "Descri��o da moeda" );
		adicCampo( txtCodPlan, 7, 140, 140, 20, "CodPlan", "C�d.planejamento", ListaCampos.DB_FK, true );
		adicDescFK( txtDescPlan, 150, 140, 240, 20, "DescPlan", "Descri��o do planejamento" );

		adicDB( rgTipo, 7, 180, 170, 30, "TipoConta", "Tipo", true );
		adicDB( rgTipoCaixa, 7, 235, 170, 30, "TipoCaixa", "Tipo de Caixa", true );
		
		adicDB( cbAtivaConta, 190, 185, 50, 20, "ativaconta", "Ativa", true );
		adicDB( chbRestritoTipoMov, 250, 185, 240, 20, "TUSUCONTA", "Permiss�o", true );
		
		/******************
		 *  ABA CONTABIL  *
		 ******************/
		setPainel( pinCamposContabil );

		adicTab( "Cont�bil", pinCamposContabil );
		
		adicCampo( txtCodContDeb, 7, 20, 150, 20, "CodContDeb", "C�d.cont.d�bito", ListaCampos.DB_SI, false );
		adicCampo( txtCodContCred, 160, 20, 150, 20, "CodContCred", "C�d.cont.cr�dito", ListaCampos.DB_SI, false );
		adicCampo( txtCodHistPad, 7, 60, 80, 20, "CodHist", "C�d.hist.", ListaCampos.DB_FK, txtDescHistPad, false );
		adicDescFK( txtDescHistPad, 90, 60, 300, 20, "DescHist", "Descri��o do hist�rico padr�o" );		

		setListaCampos( false, "CONTA", "FN" );
		lcCampos.setQueryInsert( false );
		

		setPainel( pinDetRestricoes, pnRestricoes );	

		pinDetRestricoes.setPreferredSize( new Dimension( 430, 80 ) );
		pinDetRestricoes.add( pinNavRestricoes, BorderLayout.SOUTH );
		pinDetRestricoes.add( pinCamposRestricoes, BorderLayout.CENTER );

		
		/********************
		 *  ABA RESTRI��ES  *
		 ********************/
		setListaCampos( lcRestricoes );
		setNavegador( navRestricoes );

		pnRestricoes.add( pinDetRestricoes, BorderLayout.SOUTH );
		pnRestricoes.add( spnRestricoes, BorderLayout.CENTER );
		pinNavRestricoes.adic( navRestricoes, 0, 0, 270, 25 );

		setPainel( pinCamposRestricoes );

		adicCampo( txtIDUsu, 7, 20, 80, 20, "IdUsu", "Id", ListaCampos.DB_PF, txtNomeUsu, true );
		adicDescFK( txtNomeUsu, 90, 20, 250, 20, "NomeUsu", " e nome do usu�rio" );

		setListaCampos( true, "CONTAUSU", "FN" );
		lcRestricoes.setQueryInsert( false );
		lcRestricoes.setQueryCommit( false );
		lcRestricoes.montaTab();

		txtNumConta.setTabelaExterna( lcRestricoes );

		tbRestricoes.setTamColuna( 80, 0 );
		tbRestricoes.setTamColuna( 280, 1 );

		chbRestritoTipoMov.addCheckBoxListener( this );

	}

	public void valorAlterado( CheckBoxEvent evt ) {

		if ( evt.getCheckBox() == chbRestritoTipoMov ) {
			if ( evt.getCheckBox().isSelected() ) {
				removeTab( "Restri��es de Usu�rio", pnRestricoes );
			}
			else {
				adicTab( "Restri��es de Usu�rio", pnRestricoes );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
		lcMoeda.setConexao( cn );
		lcPlan.setConexao( cn );
		lcRestricoes.setConexao( cn );
		lcUsu.setConexao( cn );
		lcHist.setConexao( cn );
	}


	public void valorAlterado( RadioGroupEvent evt ) {

		if(evt.getSource() == rgTipo) {
			if("C".equals( rgTipo.getVlrString() )) {
				rgTipoCaixa.setAtivo( true );
			}
			else {
				rgTipoCaixa.setVlrString( "F" );
				rgTipoCaixa.setAtivo( false );
			}
			
		}
		
	}
}
