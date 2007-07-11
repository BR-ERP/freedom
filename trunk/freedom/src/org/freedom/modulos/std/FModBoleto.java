/**
 * @version 18/12/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FModBoleto.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Monta o org.freedom.layout para o boleto banc�rio.
 * 
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.acao.PostEvent;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FTabDados;

public class FModBoleto extends FTabDados implements ActionListener, JComboBoxListener, CheckBoxListener {

	private static final long serialVersionUID = 1L;
	
	private final CardLayout cardlayout = new CardLayout();
	
	private final JPanelPad pnGeral = new JPanelPad( new BorderLayout() );

	private final JPanelPad pinCab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelCampos = new JPanelPad();
	
	private final JPanelPad panelBoleto = new JPanelPad( JPanelPad.TP_JPANEL, cardlayout );
	
	private final JPanelPad pinPreImp = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelPreImp = new JPanelPad();
	
	private final JPanelPad panelBolElect = new JPanelPad();
	
	private final JPanelPad panelBancos = new JPanelPad( new BorderLayout() );
	
	private final JPanelPad panelCamposBancos = new JPanelPad();
	
	private final JTextFieldPad txtCodModBol = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtDescModBol = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final JTextFieldPad txtCarteira = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JCheckBoxPad ckPreImp = new JCheckBoxPad( "Usa boleto pr�-impresso ?", "S", "N" );

	private final JTextAreaPad txaBoleto = new JTextAreaPad( 10000 );

	private final JScrollPane spnCli = new JScrollPane( txaBoleto );

	private final JTextFieldPad txtAdic = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 8, 2 );

	private final JButton btAdic = new JButton( Icone.novo( "btOk.gif" ) );

	private JComboBoxPad cbCamposDin = null;

	private JComboBoxPad cbCamposEspec = null;
	
	private final JTextFieldPad txtClassModBol = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private final JTextFieldPad txtEspecie = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldPad txtMdeCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JCheckBoxPad ckAceite = new JCheckBoxPad( "Aceite ?", "S", "N" );
	
	private final JTextFieldPad txtDescLocaPag = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private final JTextAreaPad txaInstrucao = new JTextAreaPad( 500 );

	private final JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldFK txtDescConta = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	//private final JButton btPath = new JButton( "..." );

	private JComboBoxPad cbAcao = null;
	
	private final Tabela tabBancos = new Tabela();
	
	private final Navegador navBancos = new Navegador( true );
	
	private final ListaCampos lcConta = new ListaCampos( this, "CC" );
	
	private final ListaCampos lcBanco = new ListaCampos( this, "BO" );
	
	private final ListaCampos lcItModBol = new ListaCampos( this );
	

	public FModBoleto() {

		super();
		setTitulo( "Modelo de boleto/Recibo" );
		setAtribos( 30, 30, 740, 500 );

		
		lcItModBol.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcItModBol );
		lcItModBol.setTabela( tabBancos );
		lcItModBol.setNavegador( navBancos );
		navBancos.setListaCampos( lcItModBol );
		
		montaCombos();
		montaListaCampos();
		montaTela();		

		lcItModBol.montaTab();
		tabBancos.setTamColuna( 100, 0 );
		tabBancos.setTamColuna( 250, 1 );
		tabBancos.setTamColuna( 120, 2 );
		
		txaBoleto.setFont( new Font( "Courier", Font.PLAIN, 11 ) );
		txaBoleto.setTabSize( 0 );
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		btAdic.addActionListener( this );
		//btPath.addActionListener( this );

		cbCamposDin.addComboBoxListener( this );
		cbAcao.addComboBoxListener( this );
		
		ckPreImp.addCheckBoxListener( this );

		ckAceite.setVlrString( "N" );
		
		ckPreImp.setVlrString( "S" );
		
		setImprimir( true );
		
	}
	
	private void montaListaCampos() {
		
		/*************
		 *  FNCONTA  *
		 *************/
		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "C�d.conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descri��o da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );
		
		/*************
		 *  FNBANCO  *
		 *************/
		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setReadOnly( true );
		txtCodBanco.setListaCampos( lcBanco );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );
		
		/*******************
		 *  FNITMODBOLETO  *
		 *******************/
		lcItModBol.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PF, txtNomeBanco, false ) );
		lcItModBol.add( new GuardaCampo( txtCarteira, "CartCob", "Carteira", ListaCampos.DB_SI, false ) );
		lcItModBol.montaSql( false, "ITMODBOLETO", "FN" );
		lcItModBol.setQueryInsert( false );
		lcItModBol.setQueryCommit( false );
	}
	
	private void montaCombos() {
		
		Vector<String> vLabs = new Vector<String>();
		vLabs.addElement( "<--Selecione-->" );
		vLabs.addElement( "Vencimento" );
		vLabs.addElement( "Data documento" );
		vLabs.addElement( "N� documento" );
		vLabs.addElement( "N� baixa" );
		vLabs.addElement( "Parcela (No.)" );
		vLabs.addElement( "Parcela (A,B...)" );
		vLabs.addElement( "Total de parcelas" );
		vLabs.addElement( "Valor do documento" );
		vLabs.addElement( "Valor liquido" );
		vLabs.addElement( "Valor extenso" );
		vLabs.addElement( "Desconto na parcela" );
		vLabs.addElement( "C�digo do cliente" );
		vLabs.addElement( "Raz�o social do cliente" );
		vLabs.addElement( "Nome do cliente" );
		vLabs.addElement( "CPF ou CNPJ do cliente" );
		vLabs.addElement( "IE ou RG do cliente" );
		vLabs.addElement( "Endere�o do cliente" );
		vLabs.addElement( "N�mero" );
		vLabs.addElement( "Complemento" );
		vLabs.addElement( "CEP" );
		vLabs.addElement( "Bairro do cliente" );
		vLabs.addElement( "Cidade do cliente" );
		vLabs.addElement( "UF do cliente" );
		vLabs.addElement( "Telefone do cliente" );
		vLabs.addElement( "DDD do cliente" );
		vLabs.addElement( "CFOP" );
		vLabs.addElement( "Descri��o da natureza" );
		vLabs.addElement( "Codigo do or�amento" );
	//	vLabs.addElement( "C�digo do conveniado" );
		vLabs.addElement( "Nome do conveniado" );
		vLabs.addElement( "Observa��es do or�amento" );
				

		Vector<String> vVals = new Vector<String>();
		vVals.addElement( "" ); // larg: 10
		vVals.addElement( "[VENCIMEN]" ); // larg: 10
		vVals.addElement( "[DATADOC_]" ); // larg: 10
		vVals.addElement( "[__DOCUMENTO__]" ); // larg: 15
		vVals.addElement( "[CODREC]" ); // larg: 8
		vVals.addElement( "[P]" ); // larg: 3
		vVals.addElement( "[A]" ); // larg: 3
		vVals.addElement( "[T]" ); // larg: 3
		vVals.addElement( "[VALOR_DOCUMEN]" ); // larg: 15
		vVals.addElement( "[VLIQ_DOCUMENT]" ); // larg: 15
		vVals.addElement( "[VALOR_EXTENSO]" ); // larg: 15
		vVals.addElement( "[DESC_DOCUMENT]" ); // larg: 15
		vVals.addElement( "[CODCLI]" ); // larg: 8
		vVals.addElement( "[_____________RAZAO____DO____CLIENTE_____________]" ); // larg: 50
		vVals.addElement( "[_____________NOME_____DO____CLIENTE_____________]" ); // larg: 50
		vVals.addElement( "[CPF/CNPJ_ CLIENT]" ); // larg: 18
		vVals.addElement( "[____IE/RG____CLIENTE]" ); // larg: 22
		vVals.addElement( "[____________ENDERECO____DO____CLIENTE___________]" ); // larg: 50
		vVals.addElement( "[NUMERO]" ); // larg: 8
		vVals.addElement( "[____COMPLEMENTO___]" ); // larg: 20
		vVals.addElement( "[__CEP__]" ); // larg: 9
		vVals.addElement( "[___________BAIRRO___________]" ); // larg: 30
		vVals.addElement( "[___________CIDADE___________]" ); // larg: 30
		vVals.addElement( "[UF]" ); // larg: 2
		vVals.addElement( "[__TELEFONE___]" ); // larg: 15
		vVals.addElement( "[DDD]" );// larg: 4
		vVals.addElement( "[CODNAT]" ); // larg: 8
		vVals.addElement( "[______________NATUREZA_DA_OPERACAO______________]" ); // larg: 50
		vVals.addElement( "[_CODORC_]" ); // larg: 10
//		vVals.addElement( "[_CODCONV]" ); // larg: 10
		vVals.addElement( "[_____________________NOMECONV___________________]" ); // larg: 50
		vVals.addElement( "[OBSORC_LLL_CCC]" ); // larg: 50
		

		cbCamposDin = new JComboBoxPad( vLabs, vVals, JComboBoxPad.TP_STRING, 50, 0 );

		Vector<String> vLabs2 = new Vector<String>();
		vLabs2.addElement( "<--Selecione-->" );
		vLabs2.addElement( "'%' Valor" );
		vLabs2.addElement( "Valor '+'" );
		vLabs2.addElement( "Valor '-'" );
		vLabs2.addElement( "Vencimento '+'" );

		Vector<String> vVals2 = new Vector<String>();
		vVals2.addElement( "" );
		vVals2.addElement( "[#####.##%_VAL]" );
		vVals2.addElement( "[#####.##+_VAL]" );
		vVals2.addElement( "[#####.##-_VAL]" );
		vVals2.addElement( "[###+_VEN]" );

		cbCamposEspec = new JComboBoxPad( vLabs2, vVals2, JComboBoxPad.TP_STRING, 20, 0 );

		Vector<String> vLabs3 = new Vector<String>();
		vLabs3.addElement( "<--Selecione-->" );
		vLabs3.addElement( "Limpa se campo vazio" );

		Vector<String> vVals3 = new Vector<String>();
		vVals3.addElement( "" );
		vVals3.addElement( "<LP><_LP>" );

		cbAcao = new JComboBoxPad( vLabs3, vVals3, JComboBoxPad.TP_STRING, 10, 0 );
	}

	private void montaTela() {
		
		/****************
		 *  Aba Geral  *
		 ****************/
				
		adicTab( "Geral", pnGeral );
		
		panelCampos.setPreferredSize( new Dimension( 750, 110 ) );
		pinCab.add( panelCampos, BorderLayout.NORTH );
		
		setPainel( panelCampos );
		
		adicCampo( txtCodModBol, 7, 30, 90, 20, "CodModBol", "C�d.mod.bol.", ListaCampos.DB_PK, true );
		adicCampo( txtDescModBol, 100, 30, 250, 20, "DescModBol", "Descri��o do modelo de boleto", ListaCampos.DB_SI, true );
		
		adicCampo( txtMdeCob, 353, 30, 97, 20, "mdeCob" , "Modalidade", ListaCampos.DB_SI, true );
		adicDB( ckPreImp, 460, 30, 200, 20, "PreImpModBol", "", false );
		adicCampo( txtCodConta, 7, 70, 90, 20, "NumConta", "N� da conta", ListaCampos.DB_FK, txtDescConta, false );
		adicDescFK( txtDescConta, 100, 70, 350, 20, "DescConta", "Descri��o da conta" );
		adicDBLiv( txaBoleto, "TxaModBol", "Corpo", false );
		adicDBLiv( txtClassModBol, "ClassModBol", "Classe modelo", false );
		adicDBLiv( txtEspecie, "EspDocModBol", "Esp�cie Doc.", false );
		adicDBLiv( ckAceite, "AceiteModBol", "Aceite", false );
		adicDBLiv( txtDescLocaPag, "DescLPModBol", "Esp�cie Doc.", false );
		adicDBLiv( txaInstrucao, "InstPagModBol", "Instru��o", false );
		
		setListaCampos( true, "MODBOLETO", "FN" );


		/***********************************
		 *  painel de boleto pre-impresso  *
		 ***********************************/
		panelPreImp.adic( new JLabelPad( "Campos de dados" ), 7, 10, 223, 20 );
		panelPreImp.adic( cbCamposDin, 7, 30, 223, 20 );
		panelPreImp.adic( new JLabelPad( "Campos especiais de dados" ), 240, 10, 220, 20 );
		panelPreImp.adic( cbCamposEspec, 240, 30, 115, 20 );
		panelPreImp.adic( txtAdic, 360, 30, 100, 20 );
		panelPreImp.adic( btAdic, 470, 20, 30, 30 );
		panelPreImp.adic( new JLabelPad( "A�oes" ), 7, 50, 223, 20 );
		panelPreImp.adic( cbAcao, 7, 70, 223, 20 );
		panelPreImp.setPreferredSize( new Dimension( 750, 110 ) );
		
		pinPreImp.add( panelPreImp, BorderLayout.NORTH );
		pinPreImp.add( spnCli, BorderLayout.CENTER );
		
		/*********************************
		 *  painel de boleto eletronico  *
		 *********************************/
		panelBolElect.adic( new JLabelPad( "Class do modelo de boleto" ), 7, 10, 350, 20 );
		panelBolElect.adic( txtClassModBol, 7, 30, 350, 20 );
		panelBolElect.adic( new JLabelPad( "Esp�cie doc." ), 360, 10, 100, 20 );
		panelBolElect.adic( txtEspecie, 360, 30, 100, 20 );
		panelBolElect.adic( ckAceite, 470, 30, 100, 20 );
		panelBolElect.adic( new JLabelPad( "Descri��o do local de pagamento" ), 7, 50, 450, 20 );
		panelBolElect.adic( txtDescLocaPag, 7, 70, 453, 20 );
		panelBolElect.adic( new JLabelPad( "Instru��es de cobran�a" ), 7, 90, 450, 20 );
		panelBolElect.adic( new JScrollPane( txaInstrucao ), 7, 110, 453, 130 );
		//panelBolElect.adic( btPath, 370, 27, 26, 26 );
		
		panelBoleto.add( "preimp", pinPreImp );
		panelBoleto.add( "bolelect", panelBolElect );	
		pinCab.add( panelBoleto, BorderLayout.CENTER );
		
		
		/****************
		 *  Aba Bancos  *
		 ****************/
		
		adicTab( "Bancos", panelBancos );
		
		panelBancos.add( new JScrollPane( tabBancos ), BorderLayout.CENTER );
		
		panelCamposBancos.setPreferredSize( new Dimension( 600, 100 ) );		
		panelCamposBancos.adic( new JLabelPad( "C�d.banco" ), 7, 10, 90, 20 );
		panelCamposBancos.adic( txtCodBanco, 7, 30, 90, 20 );
		panelCamposBancos.adic( new JLabelPad( "Nome do banco" ), 100, 10, 250, 20 );
		panelCamposBancos.adic( txtNomeBanco, 100, 30, 250, 20 );
		panelCamposBancos.adic( new JLabelPad( "Carteira" ), 353, 10, 250, 20 );
		panelCamposBancos.adic( txtCarteira, 353, 30, 97, 20 );
		
		panelCamposBancos.adic( navBancos, 0, 65, 270, 30 );
		
		panelBancos.add( panelCamposBancos, BorderLayout.SOUTH );
		
		pnGeral.add( pinCab );
	
	}
	
	@Override
	public void beforePost( PostEvent pevt ) {

		if ( pevt.getListaCampos() == lcCampos ) {
		
			if ( txaBoleto.getVlrString() == null || txaBoleto.getVlrString().trim().length() == 0 ) {
				
				txaBoleto.setVlrString( "Modelo em branco" );
			}
		}
		
		super.beforePost( pevt );
	}

	public void valorAlterado( CheckBoxEvent evt ) {

		if ( evt.getCheckBox() == ckPreImp ) {
			
			if ( "S".equals( ckPreImp.getVlrString() ) ) {
				cardlayout.first( panelBoleto );
			}
			else if ( "N".equals( ckPreImp.getVlrString() ) ) {
				cardlayout.last( panelBoleto );
			}
		}
	}

	public void valorAlterado( JComboBoxEvent evt ) {

		if ( evt.getComboBoxPad() == cbCamposDin ) {
			
			txaBoleto.insert( cbCamposDin.getVlrString(), txaBoleto.getCaretPosition() );
		}
		else if ( evt.getComboBoxPad() == cbAcao ) {
			
			txaBoleto.insert( cbAcao.getVlrString(), txaBoleto.getCaretPosition() );
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btAdic ) {
			
			BigDecimal bigVal = txtAdic.getVlrBigDecimal().setScale( 2, BigDecimal.ROUND_HALF_UP );
			String sVal = bigVal.toString();
			
			if ( cbCamposEspec.getSelectedIndex() < 3 ) { 
				// Campos de valores			
				sVal = cbCamposEspec.getVlrString().replaceAll( "#####.##", Funcoes.strZero( bigVal.setScale( 2, BigDecimal.ROUND_HALF_UP ).toString(), 8 ) );
			}
			else if ( cbCamposEspec.getSelectedIndex() == 3 ) { 
				// Campos de datas				
				sVal = cbCamposEspec.getVlrString().replaceAll( "###", Funcoes.strZero( bigVal.intValue() + "", 3 ) );
			}

			txaBoleto.insert( sVal, txaBoleto.getCaretPosition() );
		}
		else if ( evt.getSource() == btImp ) {
		
			imprimir( false );
		}
		else if ( evt.getSource() == btPrevimp ) {
		
			imprimir( true );
		}
		
		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {

		if ( "S".equals( ckPreImp.getVlrString() ) ) {
			imprimirTexto( bVisualizar );
		}
		else {
			imprimeGrafico( bVisualizar );
		}
	}
	
	private void imprimirTexto( boolean bVisualizar ) {
		
		ImprimeOS imp = new ImprimeOS( "", con );
		imp.verifLinPag();
		imp.setTitulo( "Teste de boleto" );
		imp.limpaPags();
		
		String[] sLinhas = txaBoleto.getText().split( "\n" );
		
		for ( int i = 0; i < sLinhas.length; i++ ) {
		
			imp.say( imp.pRow() + 1, 0, sLinhas[ i ] );
		}
		
		imp.eject();
		imp.fechaGravacao();
		
		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}
	
	private void imprimeGrafico( boolean bVisualizar ) {
		
		/*FPrinterJob dlGr = new FPrinterJob( "relatorios/TipoCli.jasper", "Vendas por Cliente", null, rs, null, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de vendas por cliente!" + err.getMessage(), true, con, err );
			}
		}*/
	}
	
	public void setConexao( Connection cn ) {
		
		super.setConexao( cn );
		
		lcConta.setConexao( cn );
		lcBanco.setConexao( cn );
		lcItModBol.setConexao( cn );
	}
	
}
