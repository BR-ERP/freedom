/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freeedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLPlanAnal.java <BR>
 * 
 *                     Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                     na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                     Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                     Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                     Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Tela de cadastro de planejamento financeiro (Contas anal�ticas).
 */

package org.freedom.modulos.std.view.dialog.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Vector;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLPlanAnal extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private final JTabbedPanePad tabbedpanel = new JTabbedPanePad();

	private final JPanelPad panelgeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelfields = new JPanelPad();

	private final JPanelPad panelcontabil = new JPanelPad();

	private final JTextFieldPad txtCodPai = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtDescPai = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodAnal = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtDescAnal = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtCodRedAnal = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtCodContDeb = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtCodContCred = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtCodForContab = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtCodHistPad = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescHistPad = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private Vector<String> vValsTipoPlan = new Vector<String>();

	private Vector<String> vLabsTipoPlan = new Vector<String>();

	private Vector<String> vValsFinPlan = new Vector<String>();

	private Vector<String> vLabsFinPlan = new Vector<String>();

	private Vector<String> vValsEsFinPlan = new Vector<String>();

	private Vector<String> vLabsEsFinPlan = new Vector<String>();

	private Vector<String> vValsClasFinPlan = new Vector<String>();

	private Vector<String> vLabsClasFinPlan = new Vector<String>();

	private JRadioGroup<String, String> rgTipoPlan = null;

	private JRadioGroup<String, String> rgFinPlan = null;

	private JRadioGroup<String, String> rgEsFinPlan = null;

	private JRadioGroup<String, String> rgClasFinPlan = null;

	private JCheckBoxPad cbLanctoXPlan = new JCheckBoxPad( "Lan�amento cruzado", "S", "N" );
	
	private ListaCampos lcHist = new ListaCampos( this, "HP" );

	public DLPlanAnal( Component cOrig, String sCodPai, String sDescPai, String sCod, String sDesc
			, String sTipo, String sFin, String sCodContCred, String sCodContDeb, int iCodHist
			, String sFinalidade, String sESFin, String sClasFin, Integer codredplan, String lanctoxplan ) {

		super( cOrig );
		
		setTitulo( "Planejamento financeiro (Conta Anal�tica)" );
		setAtribos( 430, 600 );

		montaListaCampos();
		montaRadioGroup();
		montaTela();

		cancText( txtCodPai );
		cancText( txtDescPai );
		cancText( txtCodAnal );
		Funcoes.setBordReq( txtDescAnal );

		txtCodPai.setVlrString( sCodPai );
		txtDescPai.setVlrString( sDescPai );
		txtCodAnal.setVlrString( sCod );
		txtCodContCred.setVlrString( sCodContCred );
		txtCodContDeb.setVlrString( sCodContDeb );
		txtCodHistPad.setVlrInteger( iCodHist );
		rgFinPlan.setVlrString( sFinalidade );
		rgTipoPlan.setVlrString( sTipo );
		
		if(codredplan!=null && codredplan > 0) {
			txtCodRedAnal.setVlrInteger( codredplan );
		}
		
		if ( "".equals( sFin.trim() ) ) {
			rgFinPlan.setVlrString( sFin );
		}
		if ( sDesc != null ) {
			setTitulo( "Edi��o de Conta Anal�tica" );
			txtDescAnal.setVlrString( sDesc );
			txtDescAnal.selectAll();
		}
		if ( sESFin == null || "".equals( sESFin ) ) {
			if ( "D".equals( sTipo ) ) {
				sESFin = "S";
			}
			else {
				sESFin = "E";
			}
		}
		if ( sClasFin == null || "".equals( sESFin ) ) {
			sClasFin = "O";
		}
		rgEsFinPlan.setVlrString( sESFin );
		rgClasFinPlan.setVlrString( sClasFin );
		cbLanctoXPlan.setVlrString( lanctoxplan );
		txtDescAnal.requestFocus();
	}

	private void montaListaCampos() {

		lcHist.add( new GuardaCampo( txtCodHistPad, "CodHist", "C�d.hist.", ListaCampos.DB_PK, false ) );
		lcHist.add( new GuardaCampo( txtDescHistPad, "DescHist", "Descri��o do historico padr�o", ListaCampos.DB_SI, false ) );
		lcHist.montaSql( false, "HISTPAD", "FN" );
		lcHist.setQueryCommit( false );
		lcHist.setReadOnly( true );
		txtCodHistPad.setTabelaExterna( lcHist, null );
		txtCodHistPad.setListaCampos( lcHist );
		txtCodHistPad.setNomeCampo( "CodHist" );
		txtCodHistPad.setChave( ListaCampos.DB_PK );
		txtDescHistPad.setListaCampos( lcHist );
		txtDescHistPad.setNomeCampo( "DescHist" );
		txtDescHistPad.setLabel( "Descri��o do historico padr�o" );
	}

	private void montaRadioGroup() {

		vValsTipoPlan.addElement( "B" );
		vValsTipoPlan.addElement( "C" );
		vValsTipoPlan.addElement( "D" );
		vValsTipoPlan.addElement( "R" );
		vLabsTipoPlan.addElement( "Bancos" );
		vLabsTipoPlan.addElement( "Caixa" );
		vLabsTipoPlan.addElement( "Despesas" );
		vLabsTipoPlan.addElement( "Receitas" );
		
		rgTipoPlan = new JRadioGroup<String, String>( 2, 2, vLabsTipoPlan, vValsTipoPlan );
		rgTipoPlan.setAtivo( 0, false );
		rgTipoPlan.setAtivo( 1, false );
		rgTipoPlan.setAtivo( 2, false );
		rgTipoPlan.setAtivo( 3, false );

		vValsFinPlan.addElement( "RV" );
		vValsFinPlan.addElement( "OR" );
		vValsFinPlan.addElement( "ER" );
		vValsFinPlan.addElement( "CF" );
		vValsFinPlan.addElement( "CV" );
		vValsFinPlan.addElement( "II" );
		vValsFinPlan.addElement( "RF" );
		vValsFinPlan.addElement( "DF" );
		vValsFinPlan.addElement( "CS" );
		vValsFinPlan.addElement( "IR" );
		vValsFinPlan.addElement( "OO" );
		vLabsFinPlan.addElement( "RV - Receitas sobre vendas" );
		vLabsFinPlan.addElement( "OR - Outras receitas" );
		vLabsFinPlan.addElement( "ER - Estorno de receitas" );
		vLabsFinPlan.addElement( "CF - Custo fixo" );
		vLabsFinPlan.addElement( "CV - Custo vari�vel" );
		vLabsFinPlan.addElement( "II - Investimentos" );
		vLabsFinPlan.addElement( "RF - Receitas financeiras" );
		vLabsFinPlan.addElement( "DF - Despesas financeiras" );
		vLabsFinPlan.addElement( "CS - Contribui��o social" );
		vLabsFinPlan.addElement( "IR - Imposto de renda" );
		vLabsFinPlan.addElement( "OO - Outros" );
		
		rgFinPlan = new JRadioGroup<String, String>( 6, 2, vLabsFinPlan, vValsFinPlan );

		vValsEsFinPlan.addElement( "E" );
		vValsEsFinPlan.addElement( "S" );
		vLabsEsFinPlan.addElement( "Entrada" );
		vLabsEsFinPlan.addElement( "Sa�da" );
		
		rgEsFinPlan = new JRadioGroup<String, String>( 1, 2, vLabsEsFinPlan, vValsEsFinPlan );

		vValsClasFinPlan.addElement( "O" );
		vValsClasFinPlan.addElement( "N" );
		vLabsClasFinPlan.addElement( "Operacional" );
		vLabsClasFinPlan.addElement( "N�o Operacional" );
		
		rgClasFinPlan = new JRadioGroup<String, String>( 1, 2, vLabsClasFinPlan, vValsClasFinPlan );

	}

	private void montaTela() {

		panelfields.adic( txtCodPai			, 7		, 20	, 80	, 20	, "C�d.origem"			 			);
		panelfields.adic( txtDescPai		, 90	, 20	, 300	, 20	, "Descri��o da origem" 			);
		panelfields.adic( txtCodAnal		, 7		, 60	, 110	, 20	, "C�digo" 							);
		panelfields.adic( txtDescAnal		, 120	, 60	, 270	, 20	, "Descri��o" 						);

		panelfields.adic( txtCodRedAnal 	, 7		, 100	, 110	, 20	, "C�digo reduzido" 				);
		
		panelfields.adic( rgTipoPlan		, 7		, 130	, 383	, 60 										);
		panelfields.adic( rgFinPlan			, 7		, 215	, 383	, 130	, "Finalidade" 						);
		panelfields.adic( rgEsFinPlan		, 7		, 370	, 383	, 30	, "Origem" 							);
		panelfields.adic( rgClasFinPlan		, 7		, 420	, 383	, 30	, "Classifica��o" 					);
		panelfields.adic( cbLanctoXPlan		, 7		, 460	, 383	, 20	, "" 					);

		panelcontabil.adic( txtCodContDeb	, 7		, 20	, 150	, 20	, "C�d.cont.d�bito" 				);
		panelcontabil.adic( txtCodContCred	, 160	, 20	, 150	, 20	, "C�d.cont.cr�dito" 				);
		panelcontabil.adic( txtCodHistPad	, 7		, 60	, 80	, 20	, "C�d.hist." 						);
		panelcontabil.adic( txtDescHistPad	, 90	, 60	, 300	, 20	, "Descri��o do historico padr�o" 	);

		tabbedpanel.add( "Geral"			, panelfields 	);
		tabbedpanel.add( "Cont�bil"			, panelcontabil );

		panelgeral.add( tabbedpanel, BorderLayout.CENTER );
		
		setPanel( panelgeral );
		
	}

	private void cancText( JTextFieldPad txt ) {

		txt.setBackground( Color.lightGray );
		txt.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
		txt.setEditable( false );
		txt.setForeground( new Color( 118, 89, 170 ) );
		
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( txtDescAnal.getText().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "O campo descri��o est� em branco!" );
				txtDescAnal.requestFocus();
				return;
			}
		}
		super.actionPerformed( evt );
	}

	public Object[] getValores() {

		Object[] ret = new Object[ 9 ];
		
		ret[ 0 ] = txtDescAnal.getVlrString();
		ret[ 1 ] = rgFinPlan.getVlrString();
		ret[ 2 ] = txtCodContCred.getVlrString();
		ret[ 3 ] = txtCodContDeb.getVlrString();
		ret[ 4 ] = txtCodHistPad.getVlrInteger();
		ret[ 5 ] = rgEsFinPlan.getVlrString();
		ret[ 6 ] = rgClasFinPlan.getVlrString();
		ret[ 7 ] = txtCodRedAnal.getVlrInteger();
		ret[ 8 ] = cbLanctoXPlan.getVlrString();

		return ret;
	}

	@ Override
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcHist.setConexao( cn );
		lcHist.carregaDados();
	}
}
