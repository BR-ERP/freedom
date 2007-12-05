/**
 * @version 05/12/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FTrnsLancCat.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios sobre a classe...
 */
package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;

import javax.swing.BorderFactory;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FFilho;

public class FTrnsLancCat extends FFilho {

	private static final long serialVersionUID = 1L;
	
	private JPanelPad panelRodape = null;
	
	private JPanelPad panelCentro = new JPanelPad();
	
	private JTextFieldPad txtNumContaOrig = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtDescContaOrgi = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtNumContaDest = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtDescContaDest = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcPlanOrig = new ListaCampos( this, "PN" );
	
	private ListaCampos lcPlanDest = new ListaCampos( this, "PN" );

	public FTrnsLancCat() {

		super( false );
		setTitulo( " Tramsfer�ncia de lan�amentos entre categorias" );
		setAtribos( 40, 40, 400, 400 );
		
		montaTela();
		montaListaCampos();
	}
	
	private void montaTela(){
		
		panelRodape = adicBotaoSair();
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );
		panelRodape.setPreferredSize( new Dimension( 600, 32 ) );
		
		pnCliente.add( panelCentro,BorderLayout.CENTER );
		panelCentro.setBorder( BorderFactory.createEtchedBorder() );
		panelCentro.setPreferredSize( new Dimension( 600, 200 ) );
		
		/***********************
		 * Categoria de Origem *
		 ********************* */
		
		panelCentro.adic( new JLabelPad("C�d.cat.Origem"), 7, 10, 100, 20 );
		panelCentro.adic( txtNumContaOrig, 7, 30, 100, 20 ); 
		panelCentro.adic( new JLabelPad("Descri��o da categoria de Origem"), 110, 10, 200, 20 );
		panelCentro.adic( txtDescContaOrgi, 110, 30, 250, 20 );
		
		/************************
		 * Categoria de Destino *
		 ************************/
		
		panelCentro.adic( new JLabelPad("C�d.cat.Destino"), 7, 50, 100, 20 );
		panelCentro.adic( txtNumContaDest, 7, 70, 100, 20 );
		panelCentro.adic( new JLabelPad("Descri��o da categoria de Destino"), 110, 50, 200, 20 );
		panelCentro.adic( txtDescContaDest, 110, 70, 250, 20 );
			
	}
	
	private void montaListaCampos(){
		
		/***********************
		 * Categoria de Origem *
		 ********************* */
		
		lcPlanOrig.add( new GuardaCampo( txtNumContaOrig, "CodPlan", "C�d.planj.", ListaCampos.DB_PK, true ) );
		lcPlanOrig.add( new GuardaCampo( txtDescContaOrgi, "DescPlan", "Descri��o do plano de contas", ListaCampos.DB_SI, false ) );
		lcPlanOrig.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlanOrig.setQueryCommit( false );
		lcPlanOrig.setReadOnly( true );
		txtNumContaOrig.setTabelaExterna( lcPlanOrig );
		txtNumContaOrig.setListaCampos( lcPlanOrig );
		txtNumContaOrig.setFK( true );
		txtNumContaOrig.setRequerido( true );
		txtDescContaOrgi.setListaCampos( lcPlanOrig );

		/************************
		 * Categoria de Destino *
		 ************************/
		
		lcPlanDest.add( new GuardaCampo( txtNumContaDest, "CodPlan", "C�d.planj.", ListaCampos.DB_PK, true ) );
		lcPlanDest.add( new GuardaCampo( txtDescContaDest, "DescPlan", "Descri��o do plano de contas", ListaCampos.DB_SI, false ) );
		lcPlanDest.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlanDest.setQueryCommit( false );
		lcPlanDest.setReadOnly( true );
		txtDescContaDest.setTabelaExterna( lcPlanDest );
		txtNumContaDest.setListaCampos( lcPlanDest );
		txtNumContaDest.setFK( true );
		txtNumContaDest.setRequerido( true );
		txtDescContaOrgi.setListaCampos( lcPlanDest );
	}
	
	public void setConexao( Connection cn ) {
		
		super.setConexao( cn );
		lcPlanOrig.setConexao( cn );
		lcPlanDest.setConexao( cn );
		
	}
}
