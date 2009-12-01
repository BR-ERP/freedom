/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FFornecedor.java <BR>
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
 * 
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;

import org.freedom.componentes.JButtonPad;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.Endereco;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FAndamento;
import org.freedom.telas.FTabDados;
import org.freedom.webservices.WSCep;

public class FFornecedor extends FTabDados implements RadioGroupListener, PostListener, InsertListener, ActionListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelDados = new JPanelPad();

	private JPanelPad panelObservacao = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );
	
	private JPanelPad panelFornecedCli = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtRazFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNomeFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodTipoFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCnpjFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtInscFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCpfFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );

	private JTextFieldPad txtRgFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtEndFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNumFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtComplFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtBairFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtUFFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtCidFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCepFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtFoneFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldPad txtFaxFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtDDDFoneFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtDDDMun = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );
	
	private JTextFieldPad txtDDDFaxFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtDDDCelFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtEmailFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtContFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCelFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtSiteFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodContDeb = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodContCred = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodForContab = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JTextFieldPad txtCodHistPad = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescHistPad = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextFieldFK txtNomeForCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtcnpjForCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );
	
	private JTextFieldFK txtcpfForCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 11, 0 );
	
	private JTextFieldFK txtBairForCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );
	
	private JTextFieldFK txtEndForCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtNumForCli = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtinscForCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JTextFieldFK txtCnpjForCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 15, 0 );
	
	private JTextFieldFK txtrgForCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );
	
	private JTextFieldPad txtCodForCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescForCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JCheckBoxPad cbAtivo = new JCheckBoxPad( "Ativo", "S", "N" );

	private Vector<String> vPessoaLab = new Vector<String>();

	private Vector<String> vPessoaVal = new Vector<String>();

	private JRadioGroup<?, ?> rgPessoa = null;

	private JTextAreaPad txaObs = new JTextAreaPad();

	private JScrollPane spnObs = new JScrollPane( txaObs );

	private ListaCampos lcTipoFor = new ListaCampos( this, "TF" );

	private ListaCampos lcHistorico = new ListaCampos( this, "HP" );
	
	private JButtonPad btFirefox = new JButtonPad( Icone.novo( "firefox.gif" ));

	private Map<String, Object> prefs = null;
	
	private String sURLBanco = null;
	
	private Navegador navForCli = new Navegador( true );
	
	private JPanelPad pnForCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinForCli = new JPanelPad( 0, 80 );
	
	private JPanelPad pinBtFor = new JPanelPad( 0, 30 );
	
	private JButtonPad btBuscaFor = new JButtonPad(Icone.novo("btPesquisa.gif"));
	
	private ListaCampos lcForCli = new ListaCampos( this );
	
	private Tabela tabCliFor = new Tabela();
	
	private JScrollPane spnTabCliFor = new JScrollPane( tabCliFor );
	
	private ListaCampos lcForxCli = new ListaCampos( this, "FR" );
	
	private ListaCampos lcUF = new ListaCampos( this );
	
	private ListaCampos lcMunic = new ListaCampos( this );
	
	private ListaCampos lcPais = new ListaCampos( this );
	
	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPais = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtSiglaUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldFK txtNomeUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 ); 
	
	private JTextFieldPad txtCodMun = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescMun = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JButtonPad btBuscaEnd = new JButtonPad( Icone.novo( "btBuscacep.gif" ) );

	private Map<String, Object> bPref = null;
	
	
	

	public FFornecedor() {

		super();
		setTitulo( "Cadastro de Fornecedores" );
		setAtribos( 50, 20, 530, 670 );
		setImprimir( true );
		lcForCli.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcForCli );
		lcForCli.setTabela( tabCliFor );
		
		
	}

	private void montaTela() {

		lcCampos.addPostListener( this );
		lcCampos.addInsertListener( this );
		lcMunic.addCarregaListener( this );

		lcTipoFor.add( new GuardaCampo( txtCodTipoFor, "CodTipoFor", "C�d.tp.for.", ListaCampos.DB_PK, true ) );
		lcTipoFor.add( new GuardaCampo( txtDescTipoFor, "DescTipoFor", "Descri��o do tipo de fornecedor", ListaCampos.DB_SI, false ) );
		lcTipoFor.montaSql( false, "TIPOFOR", "CP" );
		lcTipoFor.setQueryCommit( false );
		lcTipoFor.setReadOnly( true );
		txtCodTipoFor.setTabelaExterna( lcTipoFor );
		
		lcHistorico.add( new GuardaCampo( txtCodHistPad, "CodHist", "C�d.hist.", ListaCampos.DB_PK, false ) );
		lcHistorico.add( new GuardaCampo( txtDescHistPad, "DescHist", "Descri��o do historico padr�o", ListaCampos.DB_SI, false ) );
		lcHistorico.montaSql( false, "HISTPAD", "FN" );
		lcHistorico.setQueryCommit( false );
		lcHistorico.setReadOnly( true );
		txtCodHistPad.setTabelaExterna( lcHistorico );
		
		
		/***************
		 *    PA�S     *
		 **************/
		
		lcPais.setUsaME( false );
		lcPais.add( new GuardaCampo( txtCodPais, "CodPais", "Cod.pa�s.", ListaCampos.DB_PK, true ) );
		lcPais.add( new GuardaCampo( txtDescPais, "NomePais", "Nome", ListaCampos.DB_SI, false ) );
		lcPais.montaSql( false, "PAIS", "SG" );
		lcPais.setQueryCommit( false );
		lcPais.setReadOnly( true );
		txtCodPais.setTabelaExterna( lcPais );
		
		/***************
		 *      UF     *
		 **************/		
		
		lcUF.setUsaME( false );		
		lcUF.add( new GuardaCampo( txtSiglaUF, "SiglaUf", "Sigla", ListaCampos.DB_PK, true ) );
		lcUF.add( new GuardaCampo( txtNomeUF, "NomeUf", "Nome", ListaCampos.DB_SI, false ) );		
		lcMunic.setDinWhereAdic( "CODPAIS = #S", txtCodPais );
		lcUF.montaSql( false, "UF", "SG" );
		lcUF.setQueryCommit( false );
		lcUF.setReadOnly( true );
		txtSiglaUF.setTabelaExterna( lcUF );
		
		/***************
		 *  MUNICIPIO  *
		 **************/
		
		lcMunic.setUsaME( false );		
		lcMunic.add( new GuardaCampo( txtCodMun, "CodMunic", "C�d.Muni", ListaCampos.DB_PK, true ) );
		lcMunic.add( new GuardaCampo( txtDescMun, "NomeMunic", "Nome Muni.", ListaCampos.DB_SI, false ) );
		lcMunic.add( new GuardaCampo( txtDDDMun, "DDDMunic", "DDD Munic.", ListaCampos.DB_SI, false ) );
		lcMunic.setDinWhereAdic( "SIGLAUF = #S", txtSiglaUF );
		lcMunic.montaSql( false, "MUNICIPIO", "SG" );
		lcMunic.setQueryCommit( false );
		lcMunic.setReadOnly( true );
		txtCodMun.setTabelaExterna( lcMunic );		

		vPessoaLab.addElement( "F�sica" );
		vPessoaLab.addElement( "Jur�dica" );
		vPessoaVal.addElement( "F" );
		vPessoaVal.addElement( "J" );
		rgPessoa = new JRadioGroup<String, String>( 2, 1, vPessoaLab, vPessoaVal );
		rgPessoa.addRadioGroupListener( this );

		panelDados = new JPanelPad( 470, 300 );
		setPainel( panelDados );
		adicTab( "Fornecedor", panelDados );
		adicCampo( txtCodFor, 7, 20, 70, 20, "CodFor", "C�d.for.", ListaCampos.DB_PK, true );
		adicCampo( txtRazFor, 80, 20, 267, 20, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, true );
		adicDB( rgPessoa, 350, 20, 100, 60, "PessoaFor", "Pessoa", true );
		adicDB( cbAtivo, 7, 60, 70, 20, "AtivoFor", "Ativo", true );
		adicCampo( txtNomeFor, 80, 60, 267, 20, "NomeFor", "Nome fantasia", ListaCampos.DB_SI, true );
		adicCampo( txtCodTipoFor, 7, 100, 70, 20, "CodTipoFor", "C�d.tp.for.", ListaCampos.DB_FK, txtDescTipoFor, true );
		adicDescFK( txtDescTipoFor, 80, 100, 207, 20, "DescTipoFor", "Descri��o do tipo de Fornecedor" );
		adicCampo( txtCpfFor, 290, 100, 160, 20, "CpfFor", "CPF", ListaCampos.DB_SI, false );
		adicCampo( txtRgFor, 7, 140, 150, 20, "RgFor", "RG", ListaCampos.DB_SI, false );
		adicCampo( txtCnpjFor, 160, 140, 147, 20, "CnpjFor", "Cnpj", ListaCampos.DB_SI, false );
		adicCampo( txtInscFor, 310, 140, 140, 20, "InscFor", "Inscri��o Estadual", ListaCampos.DB_SI, false );
		adicCampo( txtCepFor, 7, 180, 70, 20, "CepFor", "Cep", ListaCampos.DB_SI, false );
		adic( btBuscaEnd, 80, 180, 20, 20 );
		adicCampo( txtEndFor, 103, 180, 220, 20, "EndFor", "Endere�o", ListaCampos.DB_SI, false );
		adicCampo( txtNumFor, 326, 180, 65, 20, "NumFor", "Num.", ListaCampos.DB_SI, false );
		adicCampo( txtComplFor, 394, 180, 58, 20, "ComplFor", "Compl.", ListaCampos.DB_SI, false );
		adicCampo( txtBairFor, 7, 220, 190, 20, "BairFor", "Bairro", ListaCampos.DB_SI, false );		
		adicCampo( txtDDDFoneFor, 7, 260, 47, 20, "DDDFoneFor", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneFor, 57, 260, 90, 20, "FoneFor", "Telefone", ListaCampos.DB_SI, false );
		adicCampo( txtDDDFaxFor, 150, 260, 47, 20, "DDDFaxFor", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFaxFor, 200, 260, 90, 20, "FaxFor", "Fax", ListaCampos.DB_SI, false );
		adicCampo( txtDDDCelFor, 293, 260, 47, 20, "DDDCelFor", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtCelFor, 343, 260, 107, 20, "CelFor", "Celular", ListaCampos.DB_SI, false );
		adicCampo( txtEmailFor, 7, 300, 220, 20, "EmailFor", "E-Mail", ListaCampos.DB_SI, false );
		adicCampo( txtSiteFor, 235, 300, 190, 20, "SiteFor", "Site", ListaCampos.DB_SI, false );
		adicCampo( txtContFor, 7, 340, 443, 20, "ContFor", "Contato", ListaCampos.DB_SI, false );
		adicCampo( txtCodForContab, 7, 380, 145, 20, "CodForContab", "C�d.for.cont�bil", ListaCampos.DB_SI, false );
		adicCampo( txtCodContDeb, 155, 380, 145, 20, "CodContDeb", "C�d.cont.d�bito", ListaCampos.DB_SI, false );
		adicCampo( txtCodContCred, 303, 380, 147, 20, "CodContCred", "C�d.cont.cr�dito", ListaCampos.DB_SI, false );
		adicCampo( txtCodHistPad, 7, 420, 80, 20, "CodHist", "C�d.hist.", ListaCampos.DB_FK, txtDescHistPad, false );
		adicDescFK( txtDescHistPad, 90, 420, 356, 20, "DescHist", "Descri��o do hist�rico padr�o" );
		adic(btFirefox, 430, 300, 20, 20 );
		btFirefox.setToolTipText( "Acessar Site" );
		
		 if ( (Boolean) prefs.get( "USAIBGEFOR" ) ) {

			adicCampo( txtCodPais, 7, 460, 70, 20, "CodPais", "Cod.pa�s", ListaCampos.DB_FK, true );
			adicDescFK( txtDescPais, 80, 460, 217, 20, "DescPais", "Nome do pa�s" );
			adicCampo( txtSiglaUF, 7, 500, 70, 20, "SiglaUf", "Sigla UF", ListaCampos.DB_FK, true );
			adicDescFK( txtNomeUF, 80, 500, 217, 20, "NomeUF", "Nome UF" );
			adicCampo( txtCodMun, 7, 540, 70, 20, "CodMunic", "Cod.munic.", ListaCampos.DB_FK, false );
			adicDescFK( txtDescMun, 80, 540, 217, 20, "NomeMunic", "Nome do municipio" );			
								
		 }
		 else{
			 
			 adicCampo( txtCidFor, 200, 220, 190, 20, "CidFor", "Cidade", ListaCampos.DB_SI, false );
			 adicCampo( txtUFFor, 393, 220, 60, 20, "UFFor", "UF", ListaCampos.DB_SI, false );
		 }
			 
			
		adicTab( "Observa��es", panelObservacao );
		adicDBLiv( txaObs, "ObsFor", "Observa��es", false );
		panelObservacao.add( spnObs );
		
		setListaCampos( true, "FORNECED", "CP" );
		lcCampos.setQueryInsert( false );
		
		if ( (Boolean) prefs.get( "BUSCACEP" ) ) {
			btBuscaEnd.setEnabled( true );
		}
		else {
			btBuscaEnd.setEnabled( false );
		}

		btBuscaEnd.addActionListener( this );
		btBuscaEnd.setToolTipText( "Busca Endere�o a partir do CEP" );
		
		 /************************
		 * Fornecedor x Cliente  *
		 ************************/
		
		setPainel( pinForCli, pnForCli );
		adicTab( "Fornecedor x Cliente", pnForCli );	
		setListaCampos( lcForCli );
		
		pnForCli.add( pinForCli, BorderLayout.CENTER );
		pnForCli.add( pinBtFor, BorderLayout.SOUTH );
		pinBtFor.adic( navForCli, 0, 0, 270, 25 );
		setNavegador( navForCli );
		
		navForCli.setAtivo( 0, false );
		navForCli.setAtivo( 1, false );
		navForCli.setAtivo( 2, false );
		navForCli.setAtivo( 3, false );
		navForCli.setAtivo( 4, false );
		navForCli.setAtivo( 6, false );
		navForCli.setAtivo( 8, true );
		
		lcForxCli.add( new GuardaCampo( txtCodForCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, null, true ) );
		lcForxCli.add( new GuardaCampo( txtDescForCli, "RazCli", "Raz�o social do Cliente", ListaCampos.DB_SI, false ) );
		lcForxCli.add( new GuardaCampo( txtNomeForCli, "NomeCli", "Nome do Cliente", ListaCampos.DB_SI, false ) );
		lcForxCli.add( new GuardaCampo( txtcpfForCli, "CpfCli", "CPF", ListaCampos.DB_SI, false ) );
		lcForxCli.add( new GuardaCampo( txtinscForCli, "InscCli", "Inscri��o Estadual", ListaCampos.DB_SI, false ) );
		lcForxCli.add( new GuardaCampo( txtrgForCli, "RgCli", "RG", ListaCampos.DB_SI, false ) );
		lcForxCli.add( new GuardaCampo( txtCnpjForCli, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false ) );
		lcForxCli.add( new GuardaCampo( txtEndForCli, "EndCli", "Endere�o", ListaCampos.DB_SI, false ) );
		lcForxCli.add( new GuardaCampo( txtNumForCli, "NumCli", "N�", ListaCampos.DB_SI, false ) );
		lcForxCli.add( new GuardaCampo( txtBairForCli, "BairCli", "Bairro", ListaCampos.DB_SI, false ) );
		lcForxCli.montaSql( false, "CLIENTE", "VD" );
		lcForxCli.setReadOnly( true );
		lcForxCli.setQueryCommit( false );
		txtCodForCli.setListaCampos( lcForxCli );
		txtCodForCli.setTabelaExterna( lcForxCli );
		
		adic( btBuscaFor, 7, 7, 30, 30 );
		btBuscaFor.setToolTipText("Buscar fornecedor");
		adicCampo( txtCodForCli, 50, 20, 55, 20, "CodCli", "C�d.Cli", ListaCampos.DB_PF, txtDescForCli,  true );
		adicDescFK( txtDescForCli, 110, 20, 200, 20, "RazCli", "Raz�o social do Cliente" );
		setListaCampos( false, "CLIFOR", "EQ" );		
		lcForCli.montaTab();
		lcForCli.setQueryInsert( false );
		lcForCli.setQueryCommit( false );
		
		adic( new JLabelPad("Nome"), 313, 0, 200, 20 );
		adic( txtNomeForCli, 313, 20, 180, 20 );
		adic( new JLabelPad("Endere�o"), 7, 40, 200, 20 );
		adic( txtEndForCli , 7, 60, 305, 20 );
		adic( new JLabelPad("Bairro"), 315, 40, 120, 20 );
		adic( txtBairForCli , 315, 60, 131, 20 );
		adic( new JLabelPad("N�"), 450, 40, 200, 20 );
		adic( txtNumForCli , 450, 60, 45, 20 );
		adic( new JLabelPad("CNPJ"), 7, 80, 160, 20 );
		adic( txtCnpjForCli, 7, 100, 160, 20 );
		adic( new JLabelPad("Inscri��o Estadual"), 170, 80, 200, 20 );
		adic( txtinscForCli, 170, 100, 142, 20 );
		adic( new JLabelPad("CPF"), 315, 80, 200, 20 );
		adic( txtcpfForCli, 315, 100, 180, 20 );
		txtcpfForCli.setMascara(  JTextFieldPad.MC_CPF );
		txtCnpjForCli.setMascara(  JTextFieldPad.MC_CNPJ );
	
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btFirefox.addActionListener( this );
		btBuscaFor.addActionListener( this );
		
		txtCnpjFor.setMascara( JTextFieldPad.MC_CNPJ );
		txtCepFor.setMascara( JTextFieldPad.MC_CEP );
		txtFoneFor.setMascara( JTextFieldPad.MC_FONE );
		txtFaxFor.setMascara( JTextFieldPad.MC_FONE );

	}
	
	private void setValores(){
		
		txtNomeForCli.setVlrString( txtNomeFor.getVlrString() );
		txtcpfForCli.setVlrInteger( txtCpfFor.getVlrInteger() );
		txtinscForCli.setVlrString( txtInscFor.getVlrString() );
		txtrgForCli.setVlrInteger( txtRgFor.getVlrInteger() );
		txtCnpjForCli.setVlrInteger( txtCnpjFor.getVlrInteger() );
		txtEndForCli.setVlrString( txtEndFor.getVlrString() );
		txtNumForCli.setVlrInteger( txtNumFor.getVlrInteger() );
		txtBairForCli.setVlrString( txtBairFor.getVlrString() );
		
	}
	private void fazBusca(){ 
		
		String sCodCli = txtCodFor.getVlrString();
		int codFor = 0;
		
		if( "".equals( sCodCli ) ){
			
			Funcoes.mensagemInforma( this, "Selecione um fornecedor! " );
		}

		codFor = pesqCli();
		
		if( codFor != 0 ){

			txtCodForCli.setVlrInteger( codFor );
			setValores();
            lcForCli.carregaDados();
			lcForxCli.carregaDados();
			
		} else {
			
			if ( Funcoes.mensagemConfirma( this, "N�o foi encontrado nenhum cliente equivalente!\n" +
					"Dejeja replicar os dados do fornecedor automaticamente?" ) == JOptionPane.YES_OPTION ) {
				codFor = inserirCli();
				
				if ( codFor!=0 ) {
					
					txtCodForCli.setVlrInteger( codFor );
					lcForCli.carregaDados();
					lcForxCli.carregaDados();
					Funcoes.mensagemInforma( this, "Cliente inserido com sucesso!" );
					setValores();
						
				}
			}
		}
	}
	private int getCodCli(){ 
		
		int codigo = 0;
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		
		sSQL.append( "SELECT MAX( C.CODCLI ) FROM VDCLIENTE C WHERE C.CODEMP=? AND C.CODFILIAL=?" );
		
		try {
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ResultSet rs  = ps.executeQuery(); 
			
			if( rs.next() ){
			
				codigo = rs.getInt( 1 ) + 1;
			}
			
		} catch ( SQLException err ) {
			
			err.printStackTrace();
			Funcoes.mensagemInforma( this, "Erro ao buscar �ltimo cliente!" + err.getMessage());  
		}
		
		return codigo;
	}
	
	private int inserirCli() { 
		
		int codcli = getCodCli();
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		
		sSQL.append( "INSERT INTO VDCLIENTE " );
		sSQL.append( "( CODEMP, CODFILIAL, CODCLI, NOMECLI, RAZCLI, ATIVOCLI, PESSOACLI, CODTIPOCLI, CODEMPTI, CODFILIALTI, " );
		sSQL.append( "CNPJCLI, INSCCLI, CPFCLI, ENDCLI, NUMCLI, BAIRCLI )" );
		sSQL.append( "VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) " );
		
		try {
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 3, codcli );
			ps.setString( 4, txtNomeFor.getVlrString() );
			ps.setString( 5, txtRazFor.getVlrString() );
			ps.setString( 6, "S" );
			ps.setString( 7, rgPessoa.getVlrString() );
			ps.setInt( 8, (Integer)prefs.get("CODTIPOCLI") );
			ps.setInt( 9, Aplicativo.iCodEmp );
			ps.setInt( 10, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setString( 11, txtCnpjFor.getVlrString() );
			ps.setString( 12, txtInscFor.getVlrString() );
			ps.setString( 13, txtCpfFor.getVlrString() );
			ps.setString( 14, txtEndFor.getVlrString() );
			ps.setInt( 15, txtNumFor.getVlrInteger() );
			ps.setString( 16, txtBairFor.getVlrString() );
			
			
			ps.executeUpdate();
			
			con.commit();
		} catch ( SQLException  e ) {
			
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao inserir Cliente" + e.getMessage() );
		}
		
		return codcli;
	}
	
	private int pesqCli() { // OK
		
		int codfor = 0;
		StringBuilder sSql = new StringBuilder();
		String chave = null;
		PreparedStatement ps = null;
		
		if ( "J".equals(rgPessoa.getVlrString()) ) {
			
			sSql.append( "SELECT CODCLI FROM VDCLIENTE C WHERE C.CODEMP=? AND C.CODFILIAL=? AND CNPJCLI=? " );
			chave = txtCnpjFor.getVlrString();
			
		} else {
			
			sSql.append( "SELECT CODCLI FROM VDCLIENTE C WHERE C.CODEMP=? AND C.CODFILIAL=? AND CPFCLI=? " );
			chave = txtCpfFor.getVlrString();
			
		}
		try {
			
			ps = con.prepareStatement( sSql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ));
			ps.setString( 3, chave );
			ResultSet rs = ps.executeQuery();
		
			if( rs.next() ){
			
				codfor = rs.getInt( "CODCLI" ); 
			}

			con.commit();
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao pesquisar cliente! " + e.getMessage() );
		}
		
		return codfor;
		
	}

	private void imprimir( boolean bVisualizar ) {

		FAndamento And = null;
		ImprimeOS imp = new ImprimeOS( "", con );
		Vector<String> vFiltros = new Vector<String>();
		int linPag = imp.verifLinPag() - 1;
		int iContaReg = 0;
		String sObs = "";
		String sWhere = "";
		String sAnd = " WHERE ";
		String[] sValores;

		DLRFornecedor dl = new DLRFornecedor( this, con );

		dl.setVisible( true );
		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}

		sValores = dl.getValores();
		dl.dispose();
		if ( sValores[ 1 ].equals( "S" ) ) {
			sObs = ",OBSFOR";
		}
		if ( sValores[ 2 ].trim().length() > 0 ) {
			sWhere = sWhere + sAnd + "RAZFOR >= '" + sValores[ 2 ] + "'";
			vFiltros.add( "RAZAO MAIORES QUE " + sValores[ 2 ].trim() );
			sAnd = " AND ";
		}
		if ( sValores[ 3 ].trim().length() > 0 ) {
			sWhere = sWhere + sAnd + "RAZFOR <= '" + sValores[ 3 ] + "'";
			vFiltros.add( "RAZAO MENORES QUE " + sValores[ 3 ].trim() );
			sAnd = " AND ";
		}
		if ( sValores[ 4 ].equals( "N" ) ) {
			sWhere = sWhere + sAnd + "PESSOAFOR <> 'F'";
			vFiltros.add( "PESSOAS JURIDICAS" );
			sAnd = " AND ";
		}
		if ( sValores[ 5 ].length() > 0 ) {
			sWhere = sWhere + sAnd + "CIDFOR = '" + sValores[ 5 ] + "'";
			vFiltros.add( "CIDADE = " + sValores[ 5 ].trim() );
			sAnd = " AND ";
		}
		if ( sValores[ 6 ].equals( "N" ) ) {
			sWhere = sWhere + sAnd + "PESSOAFOR <> 'J'";
			vFiltros.add( "PESSOAS FISICA" );
			sAnd = " AND ";
		}
		if ( sValores[ 8 ].length() > 0 ) {
			sWhere = sWhere + sAnd + "CODTIPOFOR = " + sValores[ 8 ];
			vFiltros.add( "TIPO DE FORNECEDOR = " + sValores[ 9 ] );
			sAnd = " AND ";
		}
		if ( sValores[ 7 ].equals( "C" ) ) {
			String sSQL = "SELECT CODFOR,RAZFOR,PESSOAFOR,NOMEFOR,CONTFOR,ENDFOR,NUMFOR," + "BAIRFOR,CIDFOR,COMPLFOR,UFFOR,CEPFOR,CNPJFOR,INSCFOR,CPFFOR,RGFOR," + "FONEFOR,DDDFONEFOR,FAXFOR,EMAILFOR" + sObs + " FROM CPFORNECED" + sWhere + " ORDER BY " + sValores[ 0 ];
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement( "SELECT COUNT(*) FROM CPFORNECED" + sWhere );
				rs = ps.executeQuery();
				rs.next();
				And = new FAndamento( "Montando Relat�rio, Aguarde!", 0, rs.getInt( 1 ) - 1 );
				con.commit();
				ps = con.prepareStatement( sSQL );
				rs = ps.executeQuery();

				imp.limpaPags();
				imp.setTitulo( "Relat�rio de Fornecedores" );
				imp.addSubTitulo( "Relat�rio de Fornecedores" );
				imp.addSubTitulo( "Filtrado por:" );
				for ( int i = 0; i < vFiltros.size(); i++ ) {
					String sTmp = vFiltros.elementAt( i );
					imp.addSubTitulo( sTmp );
				}

				while ( rs.next() ) {
					if ( imp.pRow() == 0 ) {

						imp.montaCab();
						imp.impCab( 136, true );
						imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" );
						imp.say( imp.pRow() + 0, 135, "|" );
					}
					if ( imp.pRow() != 0 ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
					}
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" );
					imp.say( imp.pRow() + 0, 2, "C�digo:" );
					imp.say( imp.pRow() + 0, 10, rs.getString( "CodFor" ) );
					imp.say( imp.pRow() + 0, 20, "Raz�o:" );
					imp.say( imp.pRow() + 0, 27, rs.getString( "RazFor" ) );
					imp.say( imp.pRow() + 0, 128, "Tipo:" );
					imp.say( imp.pRow() + 0, 134, rs.getString( "PessoaFor" ) );
					imp.say( imp.pRow() + 0, 135, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" );
					imp.say( imp.pRow() + 0, 1, "Nome:" );
					imp.say( imp.pRow() + 0, 7, rs.getString( "NomeFor" ) );
					imp.say( imp.pRow() + 0, 60, "Contato:" );
					imp.say( imp.pRow() + 0, 70, rs.getString( "ContFor" ) );
					imp.say( imp.pRow() + 0, 135, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" );
					imp.say( imp.pRow() + 0, 1, "Endere�o:" );
					imp.say( imp.pRow() + 0, 11, rs.getString( "EndFor" ) );
					imp.say( imp.pRow() + 0, 62, "N.:" );
					imp.say( imp.pRow() + 0, 67, "" + rs.getInt( "NumFor" ) );
					imp.say( imp.pRow() + 0, 76, "Compl.:" );
					imp.say( imp.pRow() + 0, 85, rs.getString( "ComplFor" ) != null ? rs.getString( "ComplFor" ).trim() : "" );
					imp.say( imp.pRow() + 0, 94, "Bairro:" );
					imp.say( imp.pRow() + 0, 102, rs.getString( "BairFor" ) != null ? rs.getString( "BairFor" ).trim() : "" );
					imp.say( imp.pRow() + 0, 135, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" );
					imp.say( imp.pRow() + 0, 1, "Cidade:" );
					imp.say( imp.pRow() + 0, 8, rs.getString( "CidFor" ) );
					imp.say( imp.pRow() + 0, 88, "UF:" );
					imp.say( imp.pRow() + 0, 93, rs.getString( "UfFor" ) );
					imp.say( imp.pRow() + 0, 121, "CEP:" );
					imp.say( imp.pRow() + 0, 126, rs.getString( "CepFor" ) != null ? Funcoes.setMascara( rs.getString( "CepFor" ), "#####-###" ) : "" );
					imp.say( imp.pRow() + 0, 135, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" );
					if ( ( rs.getString( "CnpjFor" ) ) != null && ( rs.getString( "InscFor" ) != null ) ) {
						imp.say( imp.pRow() + 0, 1, "CNPJ:" );
						imp.say( imp.pRow() + 0, 7, Funcoes.setMascara( rs.getString( "CnpjFor" ), "##.###.###/####-##" ) );
						imp.say( imp.pRow() + 0, 49, "IE:" );
						if ( !rs.getString( "InscFor" ).trim().toUpperCase().equals( "ISENTO" ) && rs.getString( "UFFor" ) != null ) {
							Funcoes.vIE( rs.getString( "InscFor" ), rs.getString( "UFFor" ) );
							imp.say( imp.pRow() + 0, 54, Funcoes.sIEValida );
						}
					}
					else {
						imp.say( imp.pRow() + 0, 1, "CPF:" );
						imp.say( imp.pRow() + 0, 6, Funcoes.setMascara( rs.getString( "CPFFor" ), "###.###.###-##" ) );
						imp.say( imp.pRow() + 0, 49, "RG:" );
						imp.say( imp.pRow() + 0, 54, rs.getString( "RgFor" ) );
					}
					imp.say( imp.pRow() + 0, 80, "Tel:" );
					imp.say( imp.pRow() + 0, 86, ( rs.getString( "DDDFoneFor" ) != null ? "(" + rs.getString( "DDDFoneFor" ) + ")" : "" ) + ( rs.getString( "FoneFor" ) != null ? Funcoes.setMascara( rs.getString( "FoneFor" ).trim(), "####-####" ) : "" ).trim() );
					imp.say( imp.pRow() + 0, 121, "Fax:" );
					imp.say( imp.pRow() + 0, 126, rs.getString( "FaxFor" ) != null ? Funcoes.setMascara( rs.getString( "FaxFor" ), "####-####" ) : "" );
					imp.say( imp.pRow() + 0, 135, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" );
					imp.say( imp.pRow() + 0, 1, "Contato:" );
					imp.say( imp.pRow() + 0, 9, rs.getString( "ContFor" ) );
					imp.say( imp.pRow() + 0, 70, "E-mail:" );
					imp.say( imp.pRow() + 0, 79, rs.getString( "EmailFor" ) );
					imp.say( imp.pRow() + 0, 135, "|" );
					if ( sObs.length() > 0 ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" );
						imp.say( imp.pRow() + 0, 1, "Obs:" );
						imp.say( imp.pRow() + 0, 6, rs.getString( "ObsFor" ) );
						imp.say( imp.pRow() + 0, 135, "|" );
					}

					if ( imp.pRow() >= linPag ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
						imp.incPags();
						imp.eject();
					}
					And.atualiza( iContaReg );
					iContaReg++;
				}
				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
				imp.eject();

				imp.fechaGravacao();

				con.commit();
				dl.dispose();
				And.dispose();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro consulta tabela de setores!\n" + err.getMessage(), true, con, err );
			}
		}
		else if ( dl.getValores()[ 7 ].equals( "R" ) ) {
			String sSQL = "SELECT CODFOR,RAZFOR,ENDFOR,CIDFOR,DDDFONEFOR,FONEFOR FROM CPFORNECED" + sWhere + " ORDER BY " + dl.getValores()[ 0 ];
			// System.out.println("...ERRR>"+sSQL);
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement( "SELECT COUNT(*) FROM CPFORNECED" + sWhere );
				rs = ps.executeQuery();
				rs.next();
				And = new FAndamento( "Montando Relat�rio, Aguarde!", 0, rs.getInt( 1 ) - 1 );
				con.commit();
				ps = con.prepareStatement( sSQL );
				rs = ps.executeQuery();
				imp.limpaPags();

				imp.setTitulo( "Relat�rio de Fornecedores" );
				imp.addSubTitulo( "Relat�rio de Fornecedores" );
				while ( rs.next() ) {
					if ( imp.pRow() == 0 ) {

						imp.montaCab();
						imp.impCab( 136, true );

						imp.say( imp.pRow() + 0, 2, "|" + Funcoes.replicate( " ", 60 ) + "Filtrado por:" + Funcoes.replicate( " ", 60 ) + "|" );
						for ( int i = 0; i < vFiltros.size(); i++ ) {
							String sTmp = vFiltros.elementAt( i );
							sTmp = "|" + Funcoes.replicate( " ", ( ( ( 135 - sTmp.length() ) / 2 ) - 1 ) ) + sTmp;
							sTmp += Funcoes.replicate( " ", 134 - sTmp.length() ) + "|";
							imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
							imp.say( imp.pRow() + 0, 2, sTmp );
						}

						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" );
						imp.say( imp.pRow() + 0, 2, "C�digo" );
						imp.say( imp.pRow() + 0, 10, "Raz�o:" );
						imp.say( imp.pRow() + 0, 50, "Endere�o:" );
						imp.say( imp.pRow() + 0, 90, "Cidade:" );
						imp.say( imp.pRow() + 0, 120, "Tel:" );
						imp.say( imp.pRow() + 0, 135, "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					}
					imp.say( imp.pRow() + 0, 0, "|" );
					imp.say( imp.pRow() + 0, 2, rs.getString( "CodFor" ) );
					imp.say( imp.pRow() + 0, 10, rs.getString( "RazFor" ) != null ? rs.getString( "RazFor" ).substring( 0, 39 ) : "" );
					imp.say( imp.pRow() + 0, 50, rs.getString( "EndFor" ) != null ? rs.getString( "EndFor" ).substring( 0, 39 ) : "" );
					imp.say( imp.pRow() + 0, 90, rs.getString( "CidFor" ) != null ? rs.getString( "CidFor" ).substring( 0, 29 ) : "" );
					imp.say( imp.pRow() + 0, 120, ( rs.getString( "DDDFoneFor" ) != null ? "(" + rs.getString( "DDDFoneFor" ) + ")" : "" ) + ( rs.getString( "FoneFor" ) != null ? Funcoes.setMascara( rs.getString( "FoneFor" ).trim(), "####-####" ) : "" ).trim() );
					imp.say( imp.pRow() + 0, 135, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					if ( imp.pRow() >= linPag ) {
						imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
						imp.incPags();
						imp.eject();
					}
					And.atualiza( iContaReg );
					iContaReg++;
				}
				imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
				imp.eject();

				imp.fechaGravacao();

				con.commit();
				dl.dispose();
				And.dispose();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro na consulta da tabela de setores!\n" + err.getMessage(), true, con, err );
			}
		}
		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	public void valorAlterado( RadioGroupEvent rgevt ) {

		if ( rgPessoa.getVlrString().equals( "J" ) ) {
			txtCnpjFor.setEnabled( true );
			if ( (Boolean)prefs.get( "CNPJFOROBRIG" ))
				setBordaReq( txtCnpjFor );
			else
				setBordaPad( txtCnpjFor );
			txtInscFor.setEnabled( true );
			if ( (Boolean)prefs.get( "INSCESTFOROBRIG" ))
				setBordaReq( txtInscFor );
			else
				setBordaPad( txtInscFor );
			txtCpfFor.setEnabled( false );
			setBordaPad( txtCpfFor );
			txtRgFor.setEnabled( false );
			setBordaPad( txtRgFor );
		}
		else if ( rgPessoa.getVlrString().equals( "F" ) ) {
			txtCnpjFor.setEnabled( false );
			setBordaPad( txtCnpjFor );
			txtInscFor.setEnabled( false );
			setBordaPad( txtInscFor );
			txtCpfFor.setEnabled( true );
			setBordaReq( txtCpfFor );
			txtRgFor.setEnabled( true );
			setBordaReq( txtRgFor );
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
			Vector<?> vVal = Funcoes.stringToVector( txaObs.getText() );
			int iTam = vVal.size();
			for ( int i = 0; i < iTam; i++ ) {
				System.out.println( i + " : " + vVal.elementAt( i ) );
			}
		}
		else if ( evt.getSource() == btImp ){
			imprimir( false );
			
		}
		else if ( evt.getSource() == btBuscaEnd ) {
			buscaEndereco();
		}
		
		super.actionPerformed( evt );
		
		 if(evt.getSource() == btFirefox ){
		    	
		    	if(!txtSiteFor.getVlrString().equals( "" )){
		    		
		    		sURLBanco = txtSiteFor.getVlrString();
		        	Funcoes.executeURL( Aplicativo.strOS, Aplicativo.strBrowser, sURLBanco );
		    	}
		    	else
		    		Funcoes.mensagemInforma( this, "Informe o Site do Fornecedor! " );
		    }
		 if( evt.getSource() == btBuscaFor ){
			 
			 fazBusca();
		 }
	}

	public void beforePost( PostEvent pevt ) {

		if ( rgPessoa.getVlrString().equals( "F" ) )
			return;
		else if ( ( txtCnpjFor.getVlrString().trim().equals( "" ) ) && ( (Boolean)prefs.get( "CNPJFOROBRIG" ))) {
			pevt.cancela();
			Funcoes.mensagemInforma( this, "Campo CNPJ � requerido! ! !" );
			txtCnpjFor.requestFocus();
			return;
		}
		else if ( ( txtInscFor.getVlrString().trim().equals( "" ) ) && ( (Boolean)prefs.get( "INSCESTFOROBRIG" )) ) {
			if ( Funcoes.mensagemConfirma( this, "Inscri��o Estadual em branco! Inserir ISENTO?" ) == JOptionPane.OK_OPTION )
				pevt.cancela();
			txtInscFor.setVlrString( "ISENTO" );
			txtInscFor.requestFocus();
			return;
		}
		else if ( txtInscFor.getVlrString().trim().equalsIgnoreCase( "ISENTO" ) ) {
			return;
		}
		else if ( txtUFFor.getText().trim().length() < 2 ) {
			if ( (Boolean) prefs.get( "USAIBGEFOR" ) ) {
				txtUFFor.setVlrString( txtSiglaUF.getVlrString() );
			}
			else {			
				pevt.cancela();			
				Funcoes.mensagemInforma( this, "Campo UF � requerido! ! !" );
				txtUFFor.requestFocus();
				return;
			}
		}
		else if ( (Boolean)prefs.get( "CONSISTEIEFOR" ) ) { 
			if ( ! Funcoes.vIE( txtInscFor.getText(), txtUFFor.getText() ) ) {
				pevt.cancela();
				Funcoes.mensagemInforma( this, "Inscri��o Estadual Inv�lida ! ! !" );
				txtInscFor.requestFocus();
				return;
			}

			if ( ! txtInscFor.getText().trim().equals( "" ) ) {
				txtInscFor.setVlrString( Funcoes.sIEValida );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcTipoFor.setConexao( cn );
		lcHistorico.setConexao( cn );
		lcForCli.setConexao( cn );
		lcForxCli.setConexao( cn );
		lcMunic.setConexao( cn );
		lcUF.setConexao( cn );
		lcPais.setConexao( cn );
		prefs = getPrefs();
		//prefs = getPrefere();
		montaTela();
	}
	

	public void afterPost( PostEvent pevt ) {

	}

	public void afterInsert( InsertEvent ievt ) {

		cbAtivo.setVlrString( "S" );
	}

	public void beforeInsert( InsertEvent ievt ) {

	}
	
	/*private Map<String, Object> getPrefere() {

		Map<String, Object> retorno = new HashMap<String, Object>();
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sSQL.append( "SELECT BUSCACEP " );
			sSQL.append( "FROM SGPREFERE1 P  " );
			sSQL.append( "WHERE P.CODEMP=? AND P.CODFILIAL=?" );

			try {

				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

				rs = ps.executeQuery();

				if ( rs.next() ) {

					retorno.put( "BUSCACEP", new Boolean( "S".equals( rs.getString( "BUSCACEP" ) ) ) );
				}

				rs.close();
				ps.close();

				if ( !con.getAutoCommit() ) {
					con.commit();
				}
			} catch ( SQLException err ) {

				Funcoes.mensagemErro( this, "Erro ao verificar prefer�ncias!\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			}
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
		}
		return retorno;
	}*/
	
	private void buscaEndereco() {

		if ( !"".equals( txtCepFor.getVlrString() ) ) {

			txtEndFor.setEnabled( false );
			txtComplFor.setEnabled( false );
			txtBairFor.setEnabled( false );
			txtCidFor.setEnabled( false );
			txtUFFor.setEnabled( false );
			txtCodPais.setEnabled( false );
			txtSiglaUF.setEnabled( false );
			txtCodMun.setEnabled( false );
			txtDDDFoneFor.setEnabled( false );
			txtDDDFaxFor.setEnabled( false );
			txtDDDCelFor.setEnabled( false );

			Thread th = new Thread( new Runnable() {

				public void run() { 

					try {
						WSCep cep = new WSCep();
						cep.setCon( con );
						cep.setCep( txtCepFor.getVlrString() );
						cep.busca();
						Endereco endereco = cep.getEndereco();

						txtEndFor.setVlrString( endereco.getTipo() + " " + endereco.getLogradouro() );
						txtComplFor.setVlrString( endereco.getComplemento() );
						txtBairFor.setVlrString( endereco.getBairro() );
						txtCidFor.setVlrString( endereco.getCidade() );
						txtUFFor.setVlrString( endereco.getSiglauf() );
						txtCodPais.setVlrInteger( endereco.getCodpais() );
						txtSiglaUF.setVlrString( endereco.getSiglauf() );
						txtCodMun.setVlrString( endereco.getCodmunic() );

						lcPais.carregaDados();
						lcUF.carregaDados();
						lcMunic.carregaDados();

						txtNumFor.requestFocus();
					} 
					catch ( Exception e ) {
						Funcoes.mensagemInforma( null, "N�o foi encontrado o endere�o para o CEP informado!" );
					} 
					finally {
						txtEndFor.setEnabled( true );
						txtComplFor.setEnabled( true );
						txtBairFor.setEnabled( true );
						txtCidFor.setEnabled( true );
						txtUFFor.setEnabled( true );
						txtCodPais.setEnabled( true );
						txtSiglaUF.setEnabled( true );
						txtCodMun.setEnabled( true );
						txtDDDFoneFor.setEnabled( true );
						txtDDDFaxFor.setEnabled( true );
						txtDDDCelFor.setEnabled( true );
					}
				}
			} );
			try {
				th.start();
			} catch ( Exception err ) {
				Funcoes.mensagemInforma( null, "N�o foi encontrado o endere�o para o CEP informado!" );
				txtCepFor.requestFocus();
			}
		}
		else {
			Funcoes.mensagemInforma( null, "Digite um CEP para busca!" );
			txtCepFor.requestFocus();
		}

	}
	
	private Map<String, Object> getPrefs() {

		Map<String, Object> retorno = new HashMap<String, Object>();
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			retorno.put( "CNPJFOROBRIG", new Boolean(true));
			retorno.put( "INSCESTFOROBRIG", new Boolean(true));
		
			sSQL.append( "SELECT CNPJFOROBRIG, INSCESTFOROBRIG, CONSISTEIEFOR, CODTIPOCLI, USAIBGEFOR, BUSCACEP FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			
			try {
				
				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, Aplicativo.iCodFilial );
				rs = ps.executeQuery();
				
				if ( rs.next() ) {
				
					retorno.put( "CNPJFOROBRIG", new Boolean( "S".equals( rs.getString( "CNPJFOROBRIG" ))));
					retorno.put( "INSCESTFOROBRIG", new Boolean( "S".equals( rs.getString( "INSCESTFOROBRIG" ))));
					retorno.put( "CONSISTEIEFOR", new Boolean( "S".equals( rs.getString( "CONSISTEIEFOR" ))));
					retorno.put( "CODTIPOCLI", rs.getInt( "CODTIPOCLI" ) );
					retorno.put( "USAIBGEFOR", new Boolean( "S".equals( rs.getString( "USAIBGEFOR" ))));
					retorno.put( "BUSCACEP", new Boolean( "S".equals( rs.getString( "BUSCACEP" ) ) ) );
					
				}
			
				rs.close();
				ps.close();
				
				con.commit();
				
			} catch ( SQLException err ) {
				
				Funcoes.mensagemErro( this, "Erro ao verificar prefer�ncias!\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			}
		} finally {
			
			sSQL = null;
			ps = null;
			rs = null;
		}
		return retorno;
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcMunic ) {
			if("".equals( txtDDDFoneFor.getVlrString())) {
				txtDDDFoneFor.setVlrString( txtDDDMun.getVlrString() );
			}
			if("".equals( txtDDDFaxFor.getVlrString())) {
				txtDDDFaxFor.setVlrString( txtDDDMun.getVlrString() );
			}
			if("".equals( txtDDDCelFor.getVlrString())) {
				txtDDDCelFor.setVlrString( txtDDDMun.getVlrString() );
			}
		}

		
	}

	public void beforeCarrega( CarregaEvent cevt ) {}

}
