/**
 * @version 01/08/2008 <BR>
 * @author Setpoint Inform�tica Ltda.
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FMetodoAnalitico.java <BR>
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
 * Tela para cadastro de tipos de clientes.
 * 
 */

package org.freedom.modulos.pcp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.ListaCampos;
import org.freedom.library.Navegador;
import org.freedom.library.PainelImagem;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldPad;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.telas.FTabDados;


public class FMetodoAnalitico extends FTabDados {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodMtAnalise = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldPad txtDescMtAnalise = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextFieldPad txtTituloMet = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtFonteMet = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCodFoto = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtDtAlt = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtHAlt = new JTextFieldPad( JTextFieldPad.TP_TIME, 10, 0 );
	
	private JTextFieldPad txtIdAlt = new JTextFieldPad( JTextFieldPad.TP_STRING, 128, 0 );

	private JTextAreaPad txtDescFoto = new JTextAreaPad();
		
	private JPanelPad pinGeral = new JPanelPad( 330, 260 );
	
	private JPanelPad pinMaterial = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinReagentes = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinProced = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinCaract = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinAbas = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinCampos = new JPanelPad( 200, 140 );
	
	private JPanelPad pnFoto = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinRodFoto = new JPanelPad( 650, 170 );
	
	private JTextAreaPad txaMaterial = new JTextAreaPad();
	
	private JScrollPane spnMaterial = new JScrollPane( txaMaterial );

	private JTextAreaPad txaReagente = new JTextAreaPad();
	
	private JScrollPane spnReagente = new JScrollPane( txaReagente );
	
	private JTextAreaPad txaProced = new JTextAreaPad();
	
	private JScrollPane spnProced = new JScrollPane( txaProced );
	
	private Navegador navFoto = new Navegador( true );
	
	private ListaCampos lcFoto = new ListaCampos( this );
	
	private JTablePad tabFoto = new JTablePad();

	private JScrollPane spnFoto = new JScrollPane( tabFoto );
	
	private PainelImagem imFotoProd = new PainelImagem( 65000 );
	
	
	public FMetodoAnalitico(){
		
		setTitulo( "M�todos Anal�ticos" );
		setAtribos( 50, 50, 700, 600 );
		
		lcFoto.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcFoto );
		lcFoto.setTabela( tabFoto );
		
		montaTela();
		
	}
	
	private void montaTela(){

		pnCliente.removeAll();		
		setPainel( pinCampos );
		
		adicCampo( txtCodMtAnalise, 7, 20, 70, 20, "CodMtAnalise", "C�d.M�todo", ListaCampos.DB_PK, true );
		adicCampo( txtDescMtAnalise, 80, 20, 595, 20, "DescMtAnalise", "Descri��o do m�todo anal�tico", ListaCampos.DB_SI, true );		
		adicCampo( txtTituloMet, 7, 60, 400, 20, "TituloAnalise", "Titulo", ListaCampos.DB_SI, true );		
		adicCampo( txtFonteMet, 7, 100, 400, 20, "FonteMtAnalise", "Fonte", ListaCampos.DB_SI, true );	
		adic( new JLabelPad("Data"), 420, 75, 80, 20 );
		adicCampo(  txtDtAlt, 420, 95, 80, 20, "DtAlt", "Data", ListaCampos.DB_SI, false );
		adic( new JLabelPad("Hora"), 507, 75, 80, 20 );
		adicCampo( txtHAlt, 507, 95, 80, 20, "HAlt", "Hora", ListaCampos.DB_SI, false );
		adic( new JLabelPad("Usu�rio"), 590, 75, 80, 20 );
		adicCampo( txtIdAlt, 590, 95, 80, 20, "IdUsuAlt", "Usu�rio", ListaCampos.DB_SI, false );
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbPeriodo = new JLabelPad( "�ltima revis�o:" , SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );
		
		adic( lbPeriodo,415, 55, 100, 20 );
		adic( lbLinha,413, 64, 265, 60 );
		
		txtDtAlt.setSoLeitura( true );
		txtHAlt.setSoLeitura( true );
		txtIdAlt.setSoLeitura( true );
		
		
		pinAbas.setPreferredSize( new Dimension( 300, 300 ) );
		pnCliente.add( pinCampos, BorderLayout.NORTH );
		
		/****************
		 *   Material   *
		 ****************/		

		adicTab( "Material", pinMaterial );
		adicDBLiv( txaMaterial, "MatAnalise", "Material", false );
		pinMaterial.add( spnMaterial );
		
		/****************
		 *   Reagentes  *
		 ****************/
		
	    adicTab( "Reagentes", pinReagentes );
	    adicDBLiv( txaReagente, "ReagAnalise", "Reagentes", false );
	    pinReagentes.add( spnReagente );
		
		/*******************
		 *  Procedimentos  *
		 *******************/
		
		adicTab( "Procedimentos", pinProced );
		adicDBLiv( txaProced, "ProcAnalise", "Procedimento", false );
		pinProced.add( spnProced );
				
		setListaCampos( true, "METODOANALISE", "PP" );
		
		/********************
		 * Caracteristicas  *
		 ********************/
		
		setPainel( pinRodFoto, pnFoto );
		adicTab( "Caracteristica", pnFoto );
		setListaCampos( lcFoto );
		setNavegador( navFoto );
		pnFoto.add( pinRodFoto, BorderLayout.SOUTH );
		pnFoto.add( spnFoto, BorderLayout.CENTER );
		pinRodFoto.adic( navFoto, 0, 140, 270, 25 );
		
		adicCampo( txtCodFoto, 7, 20, 70, 20, "CodFotoMTan", "N� foto", ListaCampos.DB_PK, true );
		adicDB( txtDescFoto, 7, 60, 480, 70, "DescFotoMtan", "Descri��o da foto", true );
		adicDB( imFotoProd, 500, 20, 150, 140, "FotoMTan", "Foto", true );	
		tabFoto.setTamColuna( 500, 1 );
		
		setListaCampos( true, "FOTOMTAN", "PP" );
		lcFoto.setQueryInsert( false );
		lcFoto.setQueryCommit( false );
		lcFoto.montaTab();
		
		tabFoto.setColunaInvisivel( 2 );
		tabFoto.setTamColuna( 350, 1 );
		
		}
	
	public void setConexao( DbConnection con ){
		
		super.setConexao( con );
		lcFoto.setConexao( con );
		
	}
}
