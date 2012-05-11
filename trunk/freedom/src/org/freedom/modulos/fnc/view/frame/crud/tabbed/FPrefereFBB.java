/**
 * @version 8/02/2007 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.fnc <BR>
 *         Classe:
 * @(#)FPrefereFBB.java <BR>
 * 
 *                      Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                      modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                      na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                      Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                      sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                      Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                      Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                      de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                      Tela de preferencias para Febraban
 * 
 */

package org.freedom.modulos.fnc.view.frame.crud.tabbed;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.acao.PostEvent;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.Navegador;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FTabDados;
import org.freedom.modulos.fnc.view.frame.crud.plain.FBanco;

public class FPrefereFBB extends FTabDados implements CarregaListener, JComboBoxListener {

	private static final long serialVersionUID = 1L;

	private final JPanelPad panelGeral = new JPanelPad();

	private final JPanelPad panelSiacc = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelCaminhos = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelTabSiacc = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelCamposSiacc = new JPanelPad();
	
	private final JPanelPad panelCamposCaminhos = new JPanelPad();

	private final JPanelPad panelNavSiacc = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JTabbedPanePad tbCnab = new JTabbedPanePad();

	private final JPanelPad panelCnab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelCnabManager = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelCnabGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelCnabPref = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelTabCnab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelCamposCnab = new JPanelPad();

	private final JPanelPad panelCamposPref = new JPanelPad( 300, 340 );

	private final JPanelPad panelNavCnab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JTextFieldPad txtNomeEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtNomeEmpCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtTipoSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtCodBancoSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldFK txtNomeBancoSiacc = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodConvSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtVersaoSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldPad txtCaminhoRemessa = new JTextFieldPad( JTextFieldPad.TP_STRING, 300, 0 );
	
	private final JTextFieldPad txtCaminhoRetorno = new JTextFieldPad( JTextFieldPad.TP_STRING, 300, 0 );
	
	private final JTextFieldPad txtBackupRemessa = new JTextFieldPad( JTextFieldPad.TP_STRING, 300, 0 );
	
	private final JTextFieldPad txtBackupRetorno = new JTextFieldPad( JTextFieldPad.TP_STRING, 300, 0 );

	private final JTextFieldPad txtIdentServSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 17, 0 );

	private final JTextFieldPad txtContaComprSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 16, 0 );

	private final JTextFieldPad txtNroSeqSiacc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JRadioGroup<?, ?> rgIdentAmbCliSiacc;

	private JRadioGroup<?, ?> rgIdentAmbBcoSiacc;

	private final JTextFieldPad txtTipoCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtCodBancoCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldFK txtNomeBancoCnab = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodConvCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtVersaoCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtIdentServCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 17, 0 );

	private final JTextFieldPad txtContaComprCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 16, 0 );

	private final JTextFieldPad txtNroSeqCnab = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtNumContaCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldFK txtAgenciaCnab = new JTextFieldFK( JTextFieldFK.TP_STRING, 6, 0 );

	private final JTextFieldFK txtDescContaCnab = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );

	private final JTextFieldPad txtNumContaSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldFK txtAgenciaSiacc = new JTextFieldFK( JTextFieldFK.TP_STRING, 6, 0 );

	private final JTextFieldFK txtDescContaSiacc = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );

	private final JTextFieldPad txtModalidadeCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtConvBol = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JRadioGroup<?, ?> rgIdentAmbCliCnab;

	private JRadioGroup<?, ?> rgIdentAmbBcoCnab;

	private JComboBoxPad cbFormaCadastramento;

	private JComboBoxPad cbPadraoCNAB;

	private JComboBoxPad cbTipoDocumento;

	private JComboBoxPad cbEmissaoBloqueto;

	private JComboBoxPad cbDistribuicao;

	private JComboBoxPad cbEspecieTitulo;

	private JComboBoxPad cbJurosMora;

	private final JTextFieldPad txtVlrJuros = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JComboBoxPad cbDesconto;

	private final JTextFieldPad txtVlrDesconto = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JComboBoxPad cbProtesto;

	private final JTextFieldPad txtNumDiasProtesto = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 3, 0 );

	private JComboBoxPad cbDevolucao;

	private final JTextFieldPad txtNumDiasDevolucao = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 3, 0 );

	private JComboBoxPad cbAceite;

	private final ListaCampos lcSiacc = new ListaCampos( this, "BO" );

	private final ListaCampos lcCnab = new ListaCampos( this, "BO" );

	private final ListaCampos lcBancoSiacc = new ListaCampos( this, "BO" );

	private final ListaCampos lcBancoCnab = new ListaCampos( this, "BO" );

	private final ListaCampos lcContaCnab = new ListaCampos( this, "CA" );

	private final ListaCampos lcContaSiacc = new ListaCampos( this, "CA" );

	private final Navegador nvSiacc = new Navegador( true );

	private final Navegador nvCnab = new Navegador( true );

	private final JTablePad tabSiacc = new JTablePad();

	private final JTablePad tabCnab = new JTablePad();

	public static final String TP_SIACC = "01";

	public static final String TP_CNAB = "02";

	// Esp�cie do t�tulo
	private Vector<String> vLabs5;
	
	private Vector<Integer> vVals5;

	// Instru��o de protesto / juros
	private Vector<String> vLabs8 = new Vector<String>();
	
	private Vector<Integer> vVals8 = new Vector<Integer>();
	
	private final JButtonPad btGetCaminhoRemessa = new JButtonPad(Icone.novo("btAbrirPeq.gif"));
	private final JButtonPad btGetCaminhoRetorno = new JButtonPad(Icone.novo("btAbrirPeq.gif"));
	private final JButtonPad btGetBackupRemessa = new JButtonPad(Icone.novo("btAbrirPeq.gif"));
	private final JButtonPad btGetBackupRetorno = new JButtonPad(Icone.novo("btAbrirPeq.gif"));

	public FPrefereFBB() {

		setTitulo( "Prefer�ncias Febraban" );
		setAtribos( 20, 20, 790, 600 );

		montaRadioGrupos();
		montaComboBoxs();
		montaListaCampos();
		montaTela();

		lcSiacc.montaTab();
		lcCnab.montaTab();

		tabSiacc.setTamColuna( 40, 0 );
		tabSiacc.setTamColuna( 80, 1 );
		tabSiacc.setTamColuna( 150, 2 );
		tabSiacc.setTamColuna( 50, 3 );

		tabCnab.setTamColuna( 40, 0 );
		tabCnab.setTamColuna( 80, 1 );
		tabCnab.setTamColuna( 150, 2 );
		tabCnab.setTamColuna( 50, 3 );

		txtTipoSiacc.setVlrString( TP_SIACC );
		txtTipoCnab.setVlrString( TP_CNAB );

		lcCnab.addPostListener( this );
		lcSiacc.addPostListener( this );

		lcCampos.addCarregaListener( this );
		lcCnab.addCarregaListener( this );
		lcSiacc.addCarregaListener( this );
		
		btGetCaminhoRemessa.setToolTipText("Localizar diret�rio");
		btGetCaminhoRetorno.setToolTipText("Localizar diret�rio");
		btGetBackupRemessa.setToolTipText("Localizar diret�rio");
		btGetBackupRetorno.setToolTipText("Localizar diret�rio");

		btGetCaminhoRemessa.addActionListener(this);
		btGetCaminhoRetorno.addActionListener(this);
		btGetBackupRemessa.addActionListener(this);
		btGetBackupRetorno.addActionListener(this);

	}

	private void montaRadioGrupos() {

		Vector<String> vLabs0 = new Vector<String>();
		Vector<String> vVals0 = new Vector<String>();
		vLabs0.add( "Produ��o" );
		vLabs0.add( "Teste" );
		vVals0.add( "P" );
		vVals0.add( "T" );
		rgIdentAmbCliSiacc = new JRadioGroup<String, String>( 2, 1, vLabs0, vVals0 );

		Vector<String> vLabs1 = new Vector<String>();
		Vector<String> vVals1 = new Vector<String>();
		vLabs1.add( "Produ��o" );
		vLabs1.add( "Teste" );
		vVals1.add( "P" );
		vVals1.add( "T" );
		rgIdentAmbBcoSiacc = new JRadioGroup<String, String>( 2, 1, vLabs1, vVals1 );

		Vector<String> vLabs2 = new Vector<String>();
		Vector<String> vVals2 = new Vector<String>();
		vLabs2.add( "Produ��o" );
		vLabs2.add( "Teste" );
		vVals2.add( "P" );
		vVals2.add( "T" );
		rgIdentAmbCliCnab = new JRadioGroup<String, String>( 2, 1, vLabs2, vVals2 );

		Vector<String> vLabs3 = new Vector<String>();
		Vector<String> vVals3 = new Vector<String>();
		vLabs3.add( "Produ��o" );
		vLabs3.add( "Teste" );
		vVals3.add( "P" );
		vVals3.add( "T" );
		rgIdentAmbBcoCnab = new JRadioGroup<String, String>( 2, 1, vLabs3, vVals3 );
	}

	private void montaComboBoxs() {

		Vector<String> vLabs1 = new Vector<String>();
		Vector<Integer> vVals1 = new Vector<Integer>();
		vLabs1.addElement( "com cadastro" );
		vLabs1.addElement( "sem cadastro" );
		vVals1.addElement( 1 );
		vVals1.addElement( 2 );
		cbFormaCadastramento = new JComboBoxPad( vLabs1, vVals1, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs2 = new Vector<String>();
		Vector<Integer> vVals2 = new Vector<Integer>();
		vLabs2.addElement( "tradicional" );
		vLabs2.addElement( "escritural" );
		vVals2.addElement( 1 );
		vVals2.addElement( 2 );
		cbTipoDocumento = new JComboBoxPad( vLabs2, vVals2, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs3 = new Vector<String>();
		Vector<Integer> vVals3 = new Vector<Integer>();
		vLabs3.addElement( "Banco emite" );
		vLabs3.addElement( "Empresa emite" );
		vLabs3.addElement( "Banco pr�-emite e empresa completa" );
		vLabs3.addElement( "Banco reemite" );
		vLabs3.addElement( "Banco n�o reemite" );
		vLabs3.addElement( "Combra�a sem papel" );
		vVals3.addElement( 1 );
		vVals3.addElement( 2 );
		vVals3.addElement( 3 );
		vVals3.addElement( 4 );
		vVals3.addElement( 5 );
		vVals3.addElement( 6 );
		cbEmissaoBloqueto = new JComboBoxPad( vLabs3, vVals3, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs4 = new Vector<String>();
		Vector<Integer> vVals4 = new Vector<Integer>();
		vLabs4.addElement( "Banco" );
		vLabs4.addElement( "Empresa" );
		vVals4.addElement( 1 );
		vVals4.addElement( 2 );
		cbDistribuicao = new JComboBoxPad( vLabs4, vVals4, JComboBoxPad.TP_INTEGER, 1, 0 );

		// Esp�cio do t�tulo
        geraEspecieTitulo( "240", false );
		cbEspecieTitulo = new JComboBoxPad( vLabs5, vVals5, JComboBoxPad.TP_INTEGER, 2, 0 );

		// Instru��o de protesto
        geraProtesto("240", false);
		cbProtesto = new JComboBoxPad( vLabs8, vVals8, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs6 = new Vector<String>();
		Vector<Integer> vVals6 = new Vector<Integer>();
		vLabs6.addElement( "Valor por dia" );
		vLabs6.addElement( "Taxa mensal" );
		vLabs6.addElement( "Isento" );
		vVals6.addElement( 1 );
		vVals6.addElement( 2 );
		vVals6.addElement( 3 );
		cbJurosMora = new JComboBoxPad( vLabs6, vVals6, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs7 = new Vector<String>();
		Vector<Integer> vVals7 = new Vector<Integer>();
		vLabs7.addElement( "Sem desconto" );
		vLabs7.addElement( "Valor fixo at� a data informada" );
		vLabs7.addElement( "Percentual at� a data informada" );
		vLabs7.addElement( "Valor por antecipa��o por dia corrido" );
		vLabs7.addElement( "Valor por antecipa��o por dia util" );
		vLabs7.addElement( "Percentual sobre o valor nominal dia corrido" );
		vLabs7.addElement( "Percentual sobre o valor nominal dia util" );
		vVals7.addElement( 0 );
		vVals7.addElement( 1 );
		vVals7.addElement( 2 );
		vVals7.addElement( 3 );
		vVals7.addElement( 4 );
		vVals7.addElement( 5 );
		vVals7.addElement( 6 );
		cbDesconto = new JComboBoxPad( vLabs7, vVals7, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs9 = new Vector<String>();
		Vector<Integer> vVals9 = new Vector<Integer>();
		vLabs9.addElement( "Baixar / Devolver" );
		vLabs9.addElement( "N�o baixar / N�o devlover" );
		vVals9.addElement( 1 );
		vVals9.addElement( 2 );
		cbDevolucao = new JComboBoxPad( vLabs9, vVals9, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs10 = new Vector<String>();
		Vector<String> vVals10 = new Vector<String>();
		vLabs10.addElement( "Sim" );
		vLabs10.addElement( "N�o" );
		vVals10.addElement( "S" );
		vVals10.addElement( "N" );
		cbAceite = new JComboBoxPad( vLabs10, vVals10, JComboBoxPad.TP_STRING, 1, 0 );

		Vector<String> vLabs11 = new Vector<String>();
		Vector<String> vVals11 = new Vector<String>();
		vLabs11.addElement( "240 bytes" );
		vLabs11.addElement( "400 bytes" );
		vVals11.addElement( "240" );
		vVals11.addElement( "400" );
		cbPadraoCNAB = new JComboBoxPad( vLabs11, vVals11, JComboBoxPad.TP_STRING, 1, 0 );
		cbPadraoCNAB.addComboBoxListener( this );

	}

	private void geraProtesto( final String cnab, final boolean troca) {
		vLabs8 = new Vector<String>();
		vVals8 = new Vector<Integer>();
		if ("240".equals( cnab )) { 
			vLabs8.addElement( "Dias corridos" );
			vLabs8.addElement( "Dias ut�is" );
			vLabs8.addElement( "N�o protestar" );
			vVals8.addElement( 1 );
			vVals8.addElement( 2 );
			vVals8.addElement( 3 );
		} else {
			if ("341".equals(txtCodBancoCnab.getVlrString())) { // Ita�
				// Inserir instru��es Ita�
				vLabs8.addElement( "00 - Sem instru��es" );
				vLabs8.addElement( "02 - Devolver ap�s 05 dias do vencimento" );
				vLabs8.addElement( "03 - Devolver ap�s 30 dias do vencimento" );
				vLabs8.addElement( "05 - Receber conforme instru��es no pr�prio t�tulo" );
				vLabs8.addElement( "07 - Devolver ap�s 15 dias do vencimento" );
				vLabs8.addElement( "09 - Protestar " );
				vLabs8.addElement( "10 - N�o protestar" );
				vLabs8.addElement( "20 - N�o receber ap�s 10 dias do vencimento" );
				vLabs8.addElement( "21 - N�o receber ap�s 15 dias do vencimento" );
				vLabs8.addElement( "24 - N�o receber ap�s 30 dias do vencimento" );
				vLabs8.addElement( "30 - Import�ncia de desconto por dia" );
				vLabs8.addElement( "43 - Sujeito a protesto se n�o for pago no vencimento" );
				vLabs8.addElement( "54 - Ap�s o vencimento pag�vel somente na empresa" );
				vLabs8.addElement( "59 - Cobran�a negociada.Pag�vel somente por este boleto na rede banc�ria" );
				vVals8.addElement( 0 );
				vVals8.addElement( 2 );
				vVals8.addElement( 3 );
				vVals8.addElement( 5 );
				vVals8.addElement( 7 );
				vVals8.addElement( 9 );
				vVals8.addElement( 10 );
				vVals8.addElement( 20 );
				vVals8.addElement( 21 );
				vVals8.addElement( 24 );
				vVals8.addElement( 30 );
				vVals8.addElement( 43 );
				vVals8.addElement( 54 );
				vVals8.addElement( 59 );				
			} else if ("237".equals(txtCodBancoCnab.getVlrString())) { // Bradesco
				// Inserir instru��es Bradesco 400 bytes
				vLabs8.addElement( "00 - Sem instru��es" );
				vLabs8.addElement( "06 - Protestar" );
				vLabs8.addElement( "05 - Protesto Falimentar" );
				vLabs8.addElement( "18 - Decurso de Prazo" );
				vLabs8.addElement( "08 - N�o cobrar juros de mora" );
				vLabs8.addElement( "09 - N�o receber ap�s o vencimento " );
				vLabs8.addElement( "10 - Multa de 10% ap�s o 4 dias do vencimento" );
				vLabs8.addElement( "11 - N�o receber ap�s 8 dias do vencimento" );
				vLabs8.addElement( "12 - Cobrar encargos ap�s 5 dias do vencimento" );
				vLabs8.addElement( "13 - Cobrar encargos ap�s 10 dias do vencimento" );
				vLabs8.addElement( "14 - Cobrar encargos ap�s 15 dias do vencimento" );
				vLabs8.addElement( "15 - Conceder desconto mesmo se pago ap�s o vencimento" );
				vVals8.addElement( 0 );
				vVals8.addElement( 6 );
				vVals8.addElement( 5 );
				vVals8.addElement( 18 );
				vVals8.addElement( 8 );
				vVals8.addElement( 9 );
				vVals8.addElement( 10 );
				vVals8.addElement( 11 );
				vVals8.addElement( 12 );
				vVals8.addElement( 13 );
				vVals8.addElement( 14 );
				vVals8.addElement( 15 );
								
			} else {
				vLabs8.addElement( "00 - Sem instru��es" );
				vLabs8.addElement( "01 - Cobrar juros (Disp. se inf. vlr. a ser cobr. p/ dia atraso)" );
				vLabs8.addElement( "03 - Protestar no 3o dia �til ap�s vencido" );
				vLabs8.addElement( "04 - Protestar no 4o dia �til ap�s vencido" );
				vLabs8.addElement( "05 - Protestar no 5o dia �til ap�s vencido" );
				vLabs8.addElement( "10 - Protestar no 10o dia corrido ap�s vencido" );
				vLabs8.addElement( "15 - Protestar no 15o dia corrido ap�s vencido" );
				vLabs8.addElement( "20 - Protestar no 20o dia corrido ap�s vencido" );
				vLabs8.addElement( "25 - Protestar no 25o dia corrido ap�s vencido" );
				vLabs8.addElement( "30 - Protestar no 30o dia corrido ap�s vencido" );
				vLabs8.addElement( "45 - Protestar no 45o dia corrido ap�s vencido" );
				vLabs8.addElement( "06 - Indica Protesto em dias corridos, com prazo de 6 a 29, 35 ou 40 dias corridos" );
				vLabs8.addElement( "07 - N�o protestar" );
				vLabs8.addElement( "22 - Conceder desconto s� at� a data estipulada" );
				vVals8.addElement( 0 );
				vVals8.addElement( 1 );
				vVals8.addElement( 3 );
				vVals8.addElement( 4 );
				vVals8.addElement( 5 );
				vVals8.addElement( 10 );
				vVals8.addElement( 15 );
				vVals8.addElement( 20 );
				vVals8.addElement( 25 );
				vVals8.addElement( 30 );
				vVals8.addElement( 45 );
				vVals8.addElement( 06 );
				vVals8.addElement( 07 );
				vVals8.addElement( 22 );
			}
		   /* - 00 - Sem de instru��es
			- 01 - Cobrar juros (Dispens�vel se informado o valor a ser cobrado por dia de
			atraso).
			- 03 - Protestar no 3o dia �til ap�s vencido
			- 04 - Protestar no 4o dia �til ap�s vencido
			- 05 - Protestar no 5o dia �til ap�s vencido
			- 10 - Protestar no 10o dia corrido ap�s vencido
			- 15 - Protestar no 15o dia corrido ap�s vencido
			- 20 - Protestar no 20o dia corrido ap�s vencido
			- 25 - Protestar no 25o dia corrido ap�s vencido
			- 30 - Protestar no 30o dia corrido ap�s vencido
			- 45 - Protestar no 45o dia corrido ap�s vencido
			- 06 - Indica Protesto em dias corridos, com prazo de 6 a 29, 35 ou 40 dias
			Corridos.
			- Obrigat�rio impostar, nas posi��es 392 a 393 o prazo de protesto
			desejado: 6 a 29, 35 ou 40 dias.
			- 07 - N�o protestar
			- 22 - Conceder desconto s� at� a data estipulada */	
		}
		if (troca) {
			cbProtesto.setItensGeneric( vLabs8, vVals8 );
		}

	}
	private void geraEspecieTitulo( final String cnab, final boolean troca ) {
		vLabs5 = new Vector<String>();
		vVals5 = new Vector<Integer>();
		if ( "240".equals( cnab ) ) {
			vLabs5.addElement( "CH- Cheque" );
			vLabs5.addElement( "DM- Duplicata mercantil" );
			vLabs5.addElement( "DMI- Duplic. mercantil p/indic." );
			vLabs5.addElement( "DS- Duplicata de servi�o" );
			vLabs5.addElement( "DSI- Duplic. de servi�o p/indic." );
			vLabs5.addElement( "DR- Duplicata rural" );
			vLabs5.addElement( "LC- Letra de cambio" );
			vLabs5.addElement( "NCC- Nota de cr�dito comercial" );
			vLabs5.addElement( "NCE- Nota de cr�dito a exporta��o" );
			vLabs5.addElement( "NCI- Nota de cr�dito ind�stria" );
			vLabs5.addElement( "NCR- Nota de cr�dito rural" );
			vLabs5.addElement( "NP- Nota promiss�ria" );
			vLabs5.addElement( "NPR- Nota promiss�ria rural" );
			vLabs5.addElement( "TM- Triplicata mercant�l" );
			vLabs5.addElement( "TS- Triplicata de servi�o" );
			vLabs5.addElement( "NS- Nota de seguro" );
			vLabs5.addElement( "RC- Recibo" );
			vLabs5.addElement( "FAT- Fatura" );
			vLabs5.addElement( "ND- Nota de d�bito" );
			vLabs5.addElement( "AP- Apolice de seguro" );
			vLabs5.addElement( "ME- Mensalidade escolar" );
			vLabs5.addElement( "PC- Parcela de cons�rcio" );
			vLabs5.addElement( "Outros" );
			vVals5.addElement( 1 );
			vVals5.addElement( 2 );
			vVals5.addElement( 3 );
			vVals5.addElement( 4 );
			vVals5.addElement( 5 );
			vVals5.addElement( 6 );
			vVals5.addElement( 7 );
			vVals5.addElement( 8 );
			vVals5.addElement( 9 );
			vVals5.addElement( 10 );
			vVals5.addElement( 11 );
			vVals5.addElement( 12 );
			vVals5.addElement( 13 );
			vVals5.addElement( 14 );
			vVals5.addElement( 15 );
			vVals5.addElement( 16 );
			vVals5.addElement( 17 );
			vVals5.addElement( 18 );
			vVals5.addElement( 19 );
			vVals5.addElement( 20 );
			vVals5.addElement( 21 );
			vVals5.addElement( 22 );
			vVals5.addElement( 99 );
		} else {
/*			00 - informado nos registros com comando 97-Despesas de Susta��o de Protesto
			nas posi��es 109/110 desde que o titulo n�o conste mais da exist�ncia
			01 -duplicata mercantil
			02 - nota promiss�ria
			03 - nota de seguro
			05 - recibo
			08 - letra de cambio
			09 - warrant
			10 - cheque
			12 - duplicata de servi�o
			13 - nota de debito
			15 - ap�lice de seguro
			25 - divida ativa da Uni�o
			26 - divida ativa de Estado
			27 - divida ativa de Munic�pio */

			vLabs5.addElement( "DM- Duplicata mercantil" );
			vLabs5.addElement( "NP- Nota promiss�ria" );
			vLabs5.addElement( "NS- Nota de seguro" );
			vLabs5.addElement( "RC- Recibo" );
			vLabs5.addElement( "LC- Letra de cambio" );
			vLabs5.addElement( "WT- Warrant" );
			vLabs5.addElement( "CQ- Cheque" );
			vLabs5.addElement( "DS- Duplicata de servi�o" );
			vLabs5.addElement( "ND- Nota de d�bito" );
			vLabs5.addElement( "AP- Apolice de seguro" );
			vLabs5.addElement( "DAU- Divida ativa da Uni�o" );
			vLabs5.addElement( "DAE- Divida ativa do Estado" );
			vLabs5.addElement( "DAM- Divida ativa do Munic�pio" );
			vVals5.addElement( 1 );
			vVals5.addElement( 2 );
			vVals5.addElement( 3 );
			vVals5.addElement( 5 );
			vVals5.addElement( 8 );
			vVals5.addElement( 9 );
			vVals5.addElement( 10 );
			vVals5.addElement( 12 );
			vVals5.addElement( 13 );
			vVals5.addElement( 15 );
			vVals5.addElement( 25 );
			vVals5.addElement( 26 );
			vVals5.addElement( 27 );
			
		}

		if ( troca ) {
			cbEspecieTitulo.setItensGeneric( vLabs5, vVals5);
		}
		
	}
	
	private void montaListaCampos() {

		/**********************
		 * FNBANCO SIACC *
		 **********************/
		lcBancoSiacc.add( new GuardaCampo( txtCodBancoSiacc, "CodBanco", "C�d.banco", ListaCampos.DB_PK, true ) );
		lcBancoSiacc.add( new GuardaCampo( txtNomeBancoSiacc, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		//lcBancoSiacc.setDinWhereAdic( " CODBANCO=#N ", txtCodBancoSiacc );
		lcBancoSiacc.montaSql( false, "BANCO", "FN" );
		lcBancoSiacc.setQueryCommit( false );
		lcBancoSiacc.setReadOnly( true );
		txtCodBancoSiacc.setTabelaExterna( lcBancoSiacc, FBanco.class.getCanonicalName() );

		lcSiacc.setMaster( lcCampos );
		lcSiacc.setTabela( tabSiacc );

		lcCampos.adicDetalhe( lcSiacc );

		/**********************
		 * FNBANCO CNAB *
		 **********************/
		lcBancoCnab.add( new GuardaCampo( txtCodBancoCnab, "CodBanco", "C�d.banco", ListaCampos.DB_PK, true ) );
		lcBancoCnab.add( new GuardaCampo( txtNomeBancoCnab, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		//lcBancoCnab.setDinWhereAdic( " CODBANCO=#N ", txtCodBancoCnab );
		lcBancoCnab.montaSql( false, "BANCO", "FN" );
		lcBancoCnab.setQueryCommit( false );
		lcBancoCnab.setReadOnly( true );
		txtCodBancoCnab.setTabelaExterna( lcBancoCnab, FBanco.class.getCanonicalName() );

		/**********************
		 * FNCONTA CONTA CNAB *
		 **********************/
		lcContaCnab.add( new GuardaCampo( txtNumContaCnab, "NumConta", "N� Conta", ListaCampos.DB_PK, false ) );
		lcContaCnab.add( new GuardaCampo( txtAgenciaCnab, "AgenciaConta", "Ag�ncia", ListaCampos.DB_SI, false ) );
		lcContaCnab.add( new GuardaCampo( txtDescContaCnab, "DescConta", "Descri��o da conta", ListaCampos.DB_SI, false ) );
		lcContaCnab.montaSql( false, "CONTA", "FN" );
		lcContaCnab.setQueryCommit( false );
		lcContaCnab.setReadOnly( true );
		txtNumContaCnab.setTabelaExterna( lcContaCnab, FConta.class.getCanonicalName() );

		/***********************
		 * FNCONTA CONTA SIACC *
		 ***********************/
		lcContaSiacc.add( new GuardaCampo( txtNumContaSiacc, "NumConta", "N� Conta", ListaCampos.DB_PK, false ) );
		lcContaSiacc.add( new GuardaCampo( txtAgenciaSiacc, "AgenciaConta", "Ag�ncia", ListaCampos.DB_SI, false ) );
		lcContaSiacc.add( new GuardaCampo( txtDescContaSiacc, "DescConta", "Descri��o da conta", ListaCampos.DB_SI, false ) );
		lcContaSiacc.montaSql( false, "CONTA", "FN" );
		lcContaSiacc.setQueryCommit( false );
		lcContaSiacc.setReadOnly( true );
		txtNumContaSiacc.setTabelaExterna( lcContaSiacc, FConta.class.getCanonicalName() );

		lcCnab.setMaster( lcCampos );
		lcCnab.setTabela( tabCnab );

		lcCampos.adicDetalhe( lcCnab );
	}

	private void montaTela() {

		/*****************
		 * GERAL *
		 *****************/

		lcCampos.setMensInserir( false );

		setPainel( panelGeral );
		adicTab( "Geral", panelGeral );
		adicCampo( txtNomeEmp, 7, 30, 250, 20, "NomeEmp", "Nome da empresa (siacc)", ListaCampos.DB_SI, true );
		adicCampo( txtNomeEmpCnab, 7, 70, 250, 20, "NomeEmpCnab", "Nome da empresa (cnab)", ListaCampos.DB_SI, true );

		nav.setAtivo( 0, false );
		lcCampos.setPodeExc( false );

		setListaCampos( false, "PREFERE6", "SG" );

		/*****************
		 * SIACC *
		 *****************/
		setListaCampos( lcSiacc );
		setNavegador( nvSiacc );

		adicTab( "SIACC", panelSiacc );
		setPainel( panelCamposSiacc, panelSiacc );

		panelSiacc.add( panelTabSiacc, BorderLayout.NORTH );
		panelSiacc.add( panelCamposSiacc, BorderLayout.CENTER );
		panelSiacc.add( panelNavSiacc, BorderLayout.SOUTH );

		panelTabSiacc.setPreferredSize( new Dimension( 300, 100 ) );
		panelTabSiacc.setBorder( BorderFactory.createEtchedBorder() );
		panelTabSiacc.add( new JScrollPane( tabSiacc ), BorderLayout.CENTER );

		//lcSiacc.add( new GuardaCampo( txtTipoSiacc, "TipoFebraban", "Tipo", ListaCampos.DB_PK, true ) );
		adicCampo( txtCodBancoSiacc, 7, 30, 100, 20, "CodBanco", "C�d.banco", ListaCampos.DB_PF, txtNomeBancoSiacc, true );
		adicDescFK( txtNomeBancoSiacc, 110, 30, 260, 20, "NomeBanco", "Nome do banco" );
		adicCampoInvisivel(  txtTipoSiacc, "TipoFebraban",  "Tipo", ListaCampos.DB_PK, true );
		adicCampo( txtCodConvSiacc, 7, 70, 140, 20, "CodConv", "Conv�nio", ListaCampos.DB_SI, false );
		adicCampo( txtVersaoSiacc, 150, 70, 50, 20, "VerLayout", "Vers�o", ListaCampos.DB_SI, false );
		adicCampo( txtIdentServSiacc, 203, 70, 100, 20, "IdentServ", "Ident. Servi�o", ListaCampos.DB_SI, false );
		adicCampo( txtNroSeqSiacc, 306, 70, 63, 20, "NroSeq", "Sequ�ncia", ListaCampos.DB_SI, false );
		adicCampo( txtNumContaSiacc, 7, 110, 80, 20, "NumConta", "N� da conta", ListaCampos.DB_FK, true );
		adicDescFK( txtAgenciaSiacc, 90, 110, 57, 20, "AgenciaConta", "Ag�ncia" );
		adicDescFK( txtDescContaSiacc, 150, 110, 220, 20, "DescConta", "Descri��o da conta" );
		adicCampo( txtContaComprSiacc, 7, 150, 140, 20, "ContaCompr", "Conta Compromisso", ListaCampos.DB_SI, false );
		adicDB( rgIdentAmbCliSiacc, 7, 190, 178, 60, "IdentAmbCli", "Ambiente do cliente", false );
		adicDB( rgIdentAmbBcoSiacc, 193, 190, 178, 60, "IdentAmbBco", "Ambiente do banco", false );
		
		setListaCampos( false, "ITPREFERE6", "SG" );
		lcSiacc.setWhereAdic( " TIPOFEBRABAN='01' " );

		panelNavSiacc.setPreferredSize( new Dimension( 300, 30 ) );
		panelNavSiacc.setBorder( BorderFactory.createEtchedBorder() );
		panelNavSiacc.add( nvSiacc, BorderLayout.WEST );

		/****************
		 * CNAB *
		 ****************/
		setListaCampos( lcCnab );
		setNavegador( nvCnab );

		adicTab( "CNAB", panelCnab );

		panelCnab.add( panelCnabManager, BorderLayout.CENTER );

		panelTabCnab.setPreferredSize( new Dimension( 300, 100 ) );
		panelTabCnab.setBorder( BorderFactory.createEtchedBorder() );
		panelTabCnab.add( new JScrollPane( tabCnab ), BorderLayout.CENTER );

		panelCnabManager.add( panelTabCnab, BorderLayout.NORTH );
		panelCnabManager.add( tbCnab, BorderLayout.CENTER );
		panelCnabManager.add( panelNavCnab, BorderLayout.SOUTH );

		tbCnab.setTabPlacement( SwingConstants.BOTTOM );

		/*** ABA GERAL ***/

		tbCnab.add( "geral", panelCnabGeral );
		panelCnabGeral.add( panelCamposCnab, BorderLayout.CENTER );
		setPainel( panelCamposCnab );

		adicCampo( txtCodBancoCnab, 7, 30, 100, 20, "CodBanco", "C�d.banco", ListaCampos.DB_PF, txtNomeBancoCnab, true );
		adicDescFK( txtNomeBancoCnab, 110, 30, 260, 20, "NomeBanco", "Nome do banco" );
		adicCampoInvisivel( txtTipoCnab, "TipoFebraban", "Tipo", ListaCampos.DB_PK, true );
//		lcCnab.add( new GuardaCampo( , , "Tipo", ListaCampos.DB_PK, true ) );
		adicCampo( txtCodConvCnab, 7, 70, 140, 20, "CodConv", "Conv�nio", ListaCampos.DB_SI, false );
		adicCampo( txtVersaoCnab, 150, 70, 50, 20, "VerLayout", "Vers�o", ListaCampos.DB_SI, false );
		adicCampo( txtIdentServCnab, 203, 70, 100, 20, "IdentServ", "Ident. Servi�o", ListaCampos.DB_SI, false );
		adicCampo( txtNroSeqCnab, 306, 70, 63, 20, "NroSeq", "Sequ�ncia", ListaCampos.DB_SI, false );
		adicCampo( txtNumContaCnab, 7, 110, 80, 20, "NumConta", "N� da conta", ListaCampos.DB_FK, true );
		adicDescFK( txtAgenciaCnab, 90, 110, 57, 20, "AgenciaConta", "Ag�ncia" );
		adicDescFK( txtDescContaCnab, 150, 110, 220, 20, "DescConta", "Descri��o da conta" );
		adicCampo( txtContaComprCnab, 7, 150, 140, 20, "ContaCompr", "Conta Compromisso", ListaCampos.DB_SI, false );
		adicCampo( txtModalidadeCnab, 150, 150, 100, 20, "MdeCob", "Modalidade", ListaCampos.DB_SI, false );
		adicCampo( txtConvBol, 253, 150, 117, 20, "ConvCob", "Conv�nio boleto", ListaCampos.DB_SI, false );

		adicDB( rgIdentAmbCliCnab, 7, 190, 178, 60, "IdentAmbCli", "Ambiente do cliente", false );
		adicDB( rgIdentAmbBcoCnab, 193, 190, 178, 60, "IdentAmbBco", "Ambiente do banco", false );

		panelNavCnab.setPreferredSize( new Dimension( 300, 30 ) );
		panelNavCnab.setBorder( BorderFactory.createEtchedBorder() );
		panelNavCnab.add( nvCnab, BorderLayout.WEST );

		/****************/

		/*** ABA PREF ***/

		tbCnab.add( "prefer�ncias", panelCnabPref );
		panelCnabPref.add( new JScrollPane( panelCamposPref ), BorderLayout.CENTER );
		setPainel( panelCamposPref );

		adicDB( cbFormaCadastramento, 10, 20, 220, 20, "FORCADTIT", "Cadastramento do titulo no banco", false );
		adicDB( cbPadraoCNAB, 233, 20, 117, 20, "PADRAOCNAB", "Padr�o CNAB", false );

		adicDB( cbTipoDocumento, 10, 60, 137, 20, "TIPODOC", "Tipo de documento", false );
		adicDB( cbEmissaoBloqueto, 150, 60, 200, 20, "IDENTEMITBOL", "Emiss�o do bloqueto", false );

		adicDB( cbDistribuicao, 10, 100, 137, 20, "IDENTDISTBOL", "Distribui��o", false );
		adicDB( cbEspecieTitulo, 150, 100, 200, 20, "ESPECTIT", "Esp�cie do titulo", false );

		adicDB( cbJurosMora, 10, 140, 250, 20, "CODJUROS", "Indentifica��o para cobran�a de juros", false );
		adicDB( txtVlrJuros, 270, 140, 80, 20, "VLRPERCJUROS", "Valor/%", false );
		adicDB( cbDesconto, 10, 180, 250, 20, "CODDESC", "Indentifica��o para consess�o de desconto", false );
		adicDB( txtVlrDesconto, 270, 180, 80, 20, "VLRPERCDESC", "Valor/%", false );
		adicDB( cbProtesto, 10, 220, 250, 20, "CODPROT", "Instru��o de protesto", false );
		adicDB( txtNumDiasProtesto, 270, 220, 80, 20, "DIASPROT", "Dias", false );
		adicDB( cbDevolucao, 10, 260, 250, 20, "CODBAIXADEV", "C�digo para devolu��o", false );
		adicDB( txtNumDiasDevolucao, 270, 260, 80, 20, "DIASBAIXADEV", "Dias", false );
		adicDB( cbAceite, 10, 300, 340, 20, "ACEITE", "Aceite", false );

		
		tbCnab.add( "Caminhos", panelCaminhos );

		setPainel( panelCamposCaminhos, panelCaminhos );
		
		adicCampo( txtCaminhoRemessa	, 7		, 20	, 300	, 20	, "CaminhoRemessa"	, "Pasta padr�o para arquivo de remessa"			, ListaCampos.DB_SI, false );
		adicCampo( txtCaminhoRetorno	, 7		, 60	, 300	, 20	, "CaminhoRetorno"	, "Pasta padr�o para arquivo de retorno"			, ListaCampos.DB_SI, false );
		adicCampo( txtBackupRemessa		, 7		, 100	, 300	, 20	, "BackupRemessa"	, "Pasta padr�o para backup de arquivo de remessa"	, ListaCampos.DB_SI, false );
		adicCampo( txtBackupRetorno		, 7		, 140	, 300	, 20	, "BackupRetorno"	, "Pasta padr�o para backup de arquivo de retorno"	, ListaCampos.DB_SI, false );
		
		adic(btGetCaminhoRemessa		, 310	, 20	, 20	, 20);
		adic(btGetCaminhoRetorno		, 310	, 60	, 20	, 20);
		adic(btGetBackupRemessa			, 310	, 100	, 20	, 20);
		adic(btGetBackupRetorno			, 310	, 140	, 20	, 20);
		
		
		/****************/

		setListaCampos( false, "ITPREFERE6", "SG" );
		lcCnab.setWhereAdic( " TIPOFEBRABAN='02' " );
	}

	public void afterCarrega( CarregaEvent e ) {

	}

	public void beforeCarrega( CarregaEvent e ) {

		if ( e.getListaCampos() == lcCampos ) {

			txtTipoCnab.setVlrString( TP_CNAB );
			txtTipoSiacc.setVlrString( TP_SIACC );
		}
	}

	public void beforePost( PostEvent e ) {

		if ( e.getListaCampos() == lcSiacc ) {

			txtTipoSiacc.setVlrString( TP_SIACC );
		}
		else if ( e.getListaCampos() == lcCnab ) {

			txtTipoCnab.setVlrString( TP_CNAB );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcSiacc.setConexao( cn );
		lcCnab.setConexao( cn );
		lcBancoSiacc.setConexao( cn );
		lcBancoCnab.setConexao( cn );
		lcContaCnab.setConexao( cn );
		lcContaSiacc.setConexao( cn );

		lcCampos.carregaDados();
	}

	public void valorAlterado( JComboBoxEvent evt ) {
		if ( evt.getComboBoxPad()==cbPadraoCNAB ) {
			geraEspecieTitulo( evt.getComboBoxPad().getVlrString(), true );
			geraProtesto( evt.getComboBoxPad().getVlrString(), true );
		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btGetCaminhoRemessa) {
			Thread th = new Thread(new Runnable() {
				public void run() {
					getPasta( txtCaminhoRemessa );
				}
			});
			th.start();
		}

		if (e.getSource() == btGetCaminhoRetorno) {
			Thread th = new Thread(new Runnable() {
				public void run() {
					getPasta( txtCaminhoRetorno );
				}
			});
			th.start();
		}

		if (e.getSource() == btGetBackupRemessa) {
			Thread th = new Thread(new Runnable() {
				public void run() {
					getPasta( txtBackupRemessa );
				}
			});
			th.start();
		}

		if (e.getSource() == btGetBackupRetorno) {
			Thread th = new Thread(new Runnable() {
				public void run() {
					getPasta( txtBackupRetorno );
				}
			});
			th.start();
		}
		
		super.actionPerformed(e);

	}

	
	private void getPasta(JTextFieldPad campo) {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			campo.setVlrString(fileChooser.getSelectedFile().getPath());			
		}
	}
	
}
