/**
 * @version 04/06/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.gms <BR>
 * Classe:
 * @(#)DLInfoVendedor.java <BR>
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
 * Dialog para entrada de c�digo do vendedor...
 */

package org.freedom.modulos.gms.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLInfoVendedor extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JLabelPad lbCodVend = new JLabelPad( "C�d.Comis." );

	private JLabelPad lbNomeComis = new JLabelPad( "Nome do comissionado" );
	
	private ListaCampos lcComis = new ListaCampos( this, "VD" );
	
	public DLInfoVendedor( Component cOrig, DbConnection cn ) {

		super( cOrig );
		
		setConexao( cn );
		setTitulo( "Informe o comissionado" );
		setAtribos( 370, 140 );
		
		montaListaCampos();

		txtCodVend.setRequerido( true );

		adic( lbCodVend, 7, 0, 80, 20 );
		adic( txtNomeVend, 7, 20, 80, 20 );

		adic( lbNomeComis, 90, 0, 250, 20);
		adic( txtNomeVend, 90, 20, 250, 20);
		
		txtCodVend.requestFocus();
	}

	private void montaListaCampos() {

		lcComis.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.comis.", ListaCampos.DB_PK, txtNomeVend, true ) );
		lcComis.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcComis.setWhereAdic( "ATIVOCOMIS='S' " );
		lcComis.montaSql( false, "VENDEDOR", "VD" );
		lcComis.setQueryCommit( false );
		lcComis.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcComis, null );
		txtCodVend.setFK( true );
		txtCodVend.setNomeCampo( "codvend" );

	}

	public Integer getValor() {

		return txtCodVend.getVlrInteger();
	}
	

	public void actionPerformed( ActionEvent evt ) {
		
		if ( evt.getSource() == btOK ) {
			if ( txtCodVend.getText().trim().length() == 0 ) {
				Funcoes.mensagemInforma( this, "Informe o comissionado!" );
				txtCodVend.requestFocus();
				
			}
			else {
				super.actionPerformed( evt );
			}
		}
		else if ( evt.getSource() == btCancel ) {
			super.actionPerformed( evt );
		}
		
	}
	
	public void setConexao( DbConnection cn) {
		super.setConexao( cn );
		lcComis.setConexao( cn );
	}
	
}
