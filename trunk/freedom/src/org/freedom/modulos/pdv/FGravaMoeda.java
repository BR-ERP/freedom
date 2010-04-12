/**
 * @version 15/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FGravaMoeda.java <BR>
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

package org.freedom.modulos.pdv;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.AplicativoPDV;

import org.freedom.ecf.app.ControllerECF;
import org.freedom.funcoes.Funcoes;

public class FGravaMoeda extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldFK txtSingMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldFK txtPlurMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final ControllerECF ecf;

	private ListaCampos lcMoeda = new ListaCampos( this, "" );
	

	public FGravaMoeda() {

		setTitulo( "Ajusta moeda na impressora." );
		setAtribos( 385, 180 );

		ecf = new ControllerECF( 
				AplicativoPDV.getEcfdriver(), 
				AplicativoPDV.getPortaECF(), 
				AplicativoPDV.bModoDemo, 
				AplicativoPDV.getEcflayout() );
		
		montaListaCampos();
		montaTela();
	}
		
	private void montaListaCampos() {
		
		txtCodMoeda.setTipo( JTextFieldPad.TP_STRING, 4, 0 );
		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "C�d.moeda", ListaCampos.DB_PK, true ) );
		lcMoeda.add( new GuardaCampo( txtSingMoeda, "SingMoeda", "Descri��o da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.add( new GuardaCampo( txtPlurMoeda, "PlurMoeda", "Descri��o do plural da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.montaSql( false, "MOEDA", "FN" );
		lcMoeda.setQueryCommit( false );
		lcMoeda.setReadOnly( true );
		txtCodMoeda.setFK( true );
		txtCodMoeda.setNomeCampo( "CodMoeda" );
		txtCodMoeda.setTabelaExterna( lcMoeda );
	}
	
	private void montaTela() {

		adic( new JLabelPad( "Sigla" ), 7, 5, 50, 15 );
		adic( txtCodMoeda, 7, 20, 50, 20 );
		adic( new JLabelPad( "Nome no singular" ), 60, 5, 147, 15 );
		adic( txtSingMoeda, 60, 20, 147, 20 );
		adic( new JLabelPad( "Nome no plural" ), 210, 5, 150, 15 );
		adic( txtPlurMoeda, 210, 20, 150, 20 );
		adic( new JLabelPad( 
				"<HTML>" +
				"Este comando so ser� executado<BR>" +
				"se n�o tiver havido movimenta��o no dia." +
				"</HTML>" ), 7, 45, 400, 40 );
	}

	private void gravaMoeda() {

		if ( ! ecf.programaMoeda( txtCodMoeda.getVlrString(), txtSingMoeda.getVlrString(), txtPlurMoeda.getVlrString() ) ) {
			Funcoes.mensagemErro( this, ecf.getMessageLog() );
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			gravaMoeda();
		}
		super.actionPerformed( evt );
	}

	@ Override
	public void keyPressed( KeyEvent e ) {

		if ( e.getSource() == btOK && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btOK.doClick();
		}
		else if ( e.getSource() == btCancel && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btCancel.doClick();
		}
		else {
			super.keyPressed( e );
		}
	}

	public void setConexao( DbConnection cn ) {

		lcMoeda.setConexao( cn );
	}
}
