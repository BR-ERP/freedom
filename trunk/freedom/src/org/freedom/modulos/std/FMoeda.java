/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FMoeda.java <BR>
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
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.Navegador;
import org.freedom.telas.FTabDados;

public class FMoeda extends FTabDados implements RadioGroupListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinGeral = new JPanelPad( 370, 220 );

	private Vector<String> vValsTipo = new Vector<String>();

	private Vector<String> vLabsTipo = new Vector<String>();

	private Vector<String> vValsAtua = new Vector<String>();

	private Vector<String> vLabsAtua = new Vector<String>();

	private JRadioGroup<?, ?> rgTipo = null;

	private JRadioGroup<?, ?> rgAtua = null;

	private JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtSingMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtPlurMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtDecsMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtDecpMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtValor = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 3 );

	private JTextFieldPad txtCodFbnMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JPanelPad pnCot = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinRod = new JPanelPad( 370, 80 );

	private JTablePad tab = new JTablePad();

	private JScrollPane spnTab = new JScrollPane( tab );

	private ListaCampos lcCot = new ListaCampos( this );

	private Navegador navCot = new Navegador( true );

	public FMoeda() {

		super();
		setTitulo( "Cadastro de Moedas" );
		setAtribos( 50, 50, 450, 360 );

		lcCot.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcCot );
		lcCot.setTabela( tab );

		setPainel( pinGeral );
		adicTab( "Geral", pinGeral );
		vValsTipo.addElement( "I" );
		vValsTipo.addElement( "C" );
		vLabsTipo.addElement( "�ndice de valores" );
		vLabsTipo.addElement( "Moeda corrente" );
		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabsTipo, vValsTipo );
		rgTipo.addRadioGroupListener( this );
		vValsAtua.addElement( "D" );
		vValsAtua.addElement( "S" );
		vValsAtua.addElement( "M" );
		vValsAtua.addElement( "A" );
		vValsAtua.addElement( "C" );
		vLabsAtua.addElement( "Di�ria" );
		vLabsAtua.addElement( "Semanal" );
		vLabsAtua.addElement( "Mensal" );
		vLabsAtua.addElement( "Anual" );
		vLabsAtua.addElement( "Moeda corrente" );
		rgAtua = new JRadioGroup<String, String>( 3, 2, vLabsAtua, vValsAtua );
		rgAtua.setVlrString( "M" );
		rgAtua.setAtivo( 4, false );

		setListaCampos( lcCampos );
		adicCampo( txtCodMoeda, 7, 20, 70, 20, "CodMoeda", "C�d.mda.", ListaCampos.DB_PK, true );
		adicCampo( txtSingMoeda, 80, 20, 250, 20, "SingMoeda", "Nome no singular", ListaCampos.DB_SI, true );
		adicCampo( txtCodFbnMoeda, 333, 20, 60, 20, "CodFbnMoeda", "C�d.FBN.", ListaCampos.DB_SI, true );
		
		
		adicDB( rgTipo, 7, 60, 388, 30, "TipoMoeda", "Tipo", true );
		adicCampo( txtPlurMoeda, 7, 110, 120, 20, "PlurMoeda", "Nome no plural", ListaCampos.DB_SI, true );
		adicCampo( txtDecsMoeda, 130, 110, 125, 20, "DecsMoeda", "Decimal no singular", ListaCampos.DB_SI, true );
		adicCampo( txtDecpMoeda, 258, 110, 137, 20, "DecpMoeda", "Decimal no plural", ListaCampos.DB_SI, true );
		adicDB( rgAtua, 7, 150, 388, 70, "AtualizaMoeda", "Tempo de atualiza��o", true );
		setListaCampos( false, "MOEDA", "FN" );
		lcCampos.setQueryInsert( false );

		adicTab( "Cota��o", pnCot );
		setPainel( pinRod, pnCot );
		setListaCampos( lcCot );
		setNavegador( navCot );
		pnCot.add( pinRod, BorderLayout.SOUTH );
		pnCot.add( spnTab, BorderLayout.CENTER );

		pinRod.adic( navCot, 0, 45, 270, 25 );

		adicCampo( txtData, 7, 20, 80, 20, "DataCot", "Data", ListaCampos.DB_PK, true );
		adicCampo( txtValor, 90, 20, 80, 20, "ValorCot", "Valor", ListaCampos.DB_SI, true );
		setListaCampos( false, "COTMOEDA", "FN" );
		lcCot.setQueryInsert( false );
		lcCot.montaTab();
	}

	public void valorAlterado( RadioGroupEvent rgevt ) {

		if ( rgTipo.getVlrString().compareTo( "C" ) == 0 ) {
			rgAtua.setVlrString( "C" );
			rgAtua.setAtivo( 0, false );
			rgAtua.setAtivo( 1, false );
			rgAtua.setAtivo( 2, false );
			rgAtua.setAtivo( 3, false );
			rgAtua.setAtivo( 4, true );
		}
		else {
			rgAtua.setVlrString( "M" );
			rgAtua.setAtivo( 0, true );
			rgAtua.setAtivo( 1, true );
			rgAtua.setAtivo( 2, true );
			rgAtua.setAtivo( 3, true );
			rgAtua.setAtivo( 4, false );
		}
	}
}
