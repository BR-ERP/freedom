/**
 * @version 10/06/2009 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FNCM.java <BR>
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
 * Tela de cadastro da tabela NBM (Nomenclatura Brasileira de Mercadorias).
 * 
 */

package org.freedom.modulos.lvf.view.frame.crud.detail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;



public class FNBM extends FDetalhe implements ActionListener{

	
	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JTextFieldPad txtCodNBM = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldPad txtDescNBM = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );
	
	private JTextAreaPad txaTextoNCM =  new JTextAreaPad( 2000 );
	
	private JTextFieldPad txtCodNCM = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescNCM = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	
	private JTextFieldFK txtTextoNCM = new JTextFieldFK( JTextFieldPad.TP_STRING, 2000, 0 );
	
	private ListaCampos lcNCM = new ListaCampos( this, "LF" );

		
	public FNBM() {

		setTitulo( "Tabela NBM" );
		setAltCab( 90 );
		setAtribos( 50, 50, 620, 550 );
		txaTextoNCM.setEditable( false );
		
		pinCab = new JPanelPad(  );

		lcCampos.setUsaME( false );
		
		lcDet.setUsaME( false );
		
		montaListaCampos();		
		
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );
		adicCampo( txtCodNBM, 7, 20, 90, 20, "CodNBM", "C�d.NBM", ListaCampos.DB_PK, true );
		adicCampo( txtDescNBM, 100, 20, 490, 20, "DescNBM", "Descri��o da NBM", ListaCampos.DB_SI, true );
		
		setListaCampos( true, "NBM", "LF" );
		lcCampos.setQueryInsert( false );
		
		setAltDet( 190 );
		pinDet = new JPanelPad( );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		adicCampo( txtCodNCM, 7, 25, 90, 20, "CodNCM", "C�d.NCM", ListaCampos.DB_PF, txtDescNCM, true );
		adicDescFK( txtDescNCM, 100, 25, 488, 20, "DescNCM", "Descri��o da NCM" );
		adic( txaTextoNCM,7,65,582,100, "Texto da NCM" );

		setListaCampos( true, "NCMNBM", "LF" );
		lcDet.setQueryInsert( false );
		
		montaTab();
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

		tab.setTamColuna( 100, 0 );
		tab.setTamColuna( 470, 1 );

	}
	
	private void montaListaCampos(){
		
		/*************
		 *  NCM  *
		 * ***********/

		lcNCM.setUsaME( false );
		txtCodNCM.setTabelaExterna( lcNCM );
		txtCodNCM.setFK( true );
		txtCodNCM.setNomeCampo( "CodNCM" );
		lcNCM.add( new GuardaCampo( txtCodNCM, "CodNCM", "C�d.NCM", ListaCampos.DB_PK, true ) );
		lcNCM.add( new GuardaCampo( txtDescNCM, "DescNCM", "Descri��o da NCM", ListaCampos.DB_SI, false ) );
//		lcNCM.add( new GuardaCampo( txtTextoNCM, "TextoNCM", "Texto da NCM", ListaCampos.DB_SI, false ) );
		lcNCM.add( new GuardaCampo( txaTextoNCM, "TextoNCM", "Texto da NCM", ListaCampos.DB_SI, false ) );
		

		lcNCM.montaSql( false, "NCM", "LF" );
		
	}
	
	
	@ Override
	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
		
	}

	public void setConexao( DbConnection con ) {
		super.setConexao( con );
		lcNCM.setConexao( con );
	}
}
