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
 * Tela de cadastro da tabela NCM (Nomenclatura Comum do Mercosul).
 * 
 */

package org.freedom.modulos.lvf.view.frame.crud.detail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;



public class FNCM extends FDetalhe implements ActionListener{

	
	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JTextFieldPad txtCodNCM = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDescNCM = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtAliqNCM = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );
	
	private JTextAreaPad txaTextoNCM =  new JTextAreaPad( 2000 );
	
	private JTextAreaPad txaExcecaoNCM =  new JTextAreaPad( 1000 );
	
	private JTextFieldPad txtCodNBM = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldFK txtDescNBM = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	
	private ListaCampos lcNBM = new ListaCampos( this, "LF" );

		
	public FNCM() {

		setTitulo( "Tabela NCM" );
		setAltCab( 280 );
		setAtribos( 50, 50, 620, 550 );
		
		pinCab = new JPanelPad(  );

		lcCampos.setUsaME( false );
		
		lcDet.setUsaME( false );
		
		montaListaCampos();		
		
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );
		adicCampo( txtCodNCM, 7, 20, 80, 20, "CodNCM", "C�d.NCM", ListaCampos.DB_PK, true );
		adicCampo( txtDescNCM, 90, 20, 420, 20, "DescNCM", "Descri��o da NCM", ListaCampos.DB_SI, true );
		adicCampo( txtAliqNCM, 513, 20, 75, 20, "AliqNCM", "Aliq. NCM", ListaCampos.DB_SI, false );
		
		adicDB( txaTextoNCM,7,60,582,100, "TextoNCM", "Texto da NCM", false );
		adicDB( txaExcecaoNCM,7,180,582,50, "ExcecaoNCM", "Exce��o", false );		
		
		setListaCampos( true, "NCM", "LF" );
		lcCampos.setQueryInsert( false );
		
		setAltDet( 70 );
		pinDet = new JPanelPad( );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		adicCampo( txtCodNBM, 7, 25, 90, 20, "CodNBM", "C�d.NBM", ListaCampos.DB_PF, txtDescNBM, true );
		adicDescFK( txtDescNBM, 100, 25, 470, 20, "DescNBM", "Descri��o da NBM" );

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
		 *  NBM  *
		 * ***********/

		lcNBM.setUsaME( false );
		txtCodNBM.setTabelaExterna( lcNBM, FNBM.class.getCanonicalName() );
		txtCodNBM.setFK( true );
		txtCodNBM.setNomeCampo( "CodNBM" );
		lcNBM.add( new GuardaCampo( txtCodNBM, "CodNBM", "C�d.NBM", ListaCampos.DB_PK, true ) );
		lcNBM.add( new GuardaCampo( txtDescNBM, "DescNBM", "Descri��o da NBM", ListaCampos.DB_SI, false ) );
		lcNBM.montaSql( false, "NBM", "LF" );
		
	}
	
	
	@ Override
	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
		
	}

	public void setConexao( DbConnection con ) {
		super.setConexao( con );
		lcNBM.setConexao( con );
	}
}
