/**
 * @version 20/05/2008 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRegraFiscal.java <BR>
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

package org.freedom.modulos.std.view.frame.report;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;

public class FRegraComiss extends FDetalhe {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JTextFieldPad txtCodRegrComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtDescRegrComis = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodTipoVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldFK txtDescTipoVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtPercComis = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 7, 3 );

	private JTextFieldPad txtSeqComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JCheckBoxPad cbObrig = new JCheckBoxPad( "Sim", "S", "N" );

	private ListaCampos lcTipoVend = new ListaCampos( this, "TV" );
	

	public FRegraComiss() {

		setTitulo( "Regra de comissionamento" );
		setAtribos( 50, 50, 600, 450 );
		
		pinCab = new JPanelPad( 440, 60 );
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );
		adicCampo( txtCodRegrComis, 7, 20, 80, 20, "CodRegrComis", "C�d.regra", ListaCampos.DB_PK, true );
		adicCampo( txtDescRegrComis, 90, 20, 300, 20, "DescRegrComis", "Descri��o da regra de comissionamento", ListaCampos.DB_SI, true );
		setListaCampos( true, "REGRACOMIS", "VD" );
		lcCampos.setQueryInsert( true );

		lcTipoVend.add( new GuardaCampo( txtCodTipoVend, "CodTipoVend", "C�d.tp.comis.", ListaCampos.DB_PK, false ) );
		lcTipoVend.add( new GuardaCampo( txtDescTipoVend, "DescTipoVend", "Descri��o do tipo de comissionado", ListaCampos.DB_SI, false ) );
		lcTipoVend.montaSql( false, "TIPOVEND", "VD" );
		lcTipoVend.setQueryCommit( false );
		lcTipoVend.setReadOnly( true );
		txtCodTipoVend.setTabelaExterna( lcTipoVend, null );

		setAltDet( 80 );

		pinDet = new JPanelPad( 600, 80 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		adicCampo( txtSeqComis, 7, 25, 30, 20, "SeqItRc", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtCodTipoVend, 40, 25, 80, 20, "CodTipoVend", "C�d.tp.comis.", ListaCampos.DB_FK, txtDescTipoVend, true );
		adicDescFK( txtDescTipoVend, 123, 25, 280, 20, "DescTipoVend", "Descri��o do tipo de comissionado" );
		adicCampo( txtPercComis, 406, 25, 55, 20, "PercComisItRc", "% Comis.", ListaCampos.DB_SI, true );
		adicDB( cbObrig, 461, 25, 87, 20, "ObrigItRc", "Obrigat�rio?", true );
		setListaCampos( true, "ITREGRACOMIS", "VD" );
		lcDet.setQueryInsert( true );

		montaTab();
		tab.setTamColuna( 220, 2 );

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
	}

	public void setConexao( DbConnection con ) {

		super.setConexao( con );
		lcTipoVend.setConexao( con );
	}
}
