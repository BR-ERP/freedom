/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLGrupo.java <BR>
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
 */

package org.freedom.modulos.std;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.JCheckBoxPad;
import org.freedom.library.JLabelPad;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLGrupo extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodGrupo = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtDescGrupo = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSiglaGrupo = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JLabelPad lbCodGrupo = new JLabelPad( "C�d.grupo" );

	private JLabelPad lbDescGrupo = new JLabelPad( "Descri��o do grupo" );

	private JLabelPad lbSiglaGrupo = new JLabelPad( "Sigla" );

	private JCheckBoxPad cbEstNeg = new JCheckBoxPad( "Permitir saldo negativo?", "S", "N" );

	private JCheckBoxPad cbEstLotNeg = new JCheckBoxPad( "Permitir saldo de lote negativo?", "S", "N" );

	private boolean bEdit = false;

	public DLGrupo( Component cOrig, DbConnection cn, String sCod, String sDesc, String sSigla, boolean bEstNeg, String sEstNeg, String sEstNegLot ) {

		super( cOrig );
		setConexao( cn );
		setTitulo( "Novo Grupo" );
		setAtribos( 400, 200 );
		Funcoes.setBordReq( txtCodGrupo );
		Funcoes.setBordReq( txtDescGrupo );

		adic( lbCodGrupo, 7, 0, 80, 20 );
		adic( txtCodGrupo, 7, 20, 80, 20 );
		adic( lbDescGrupo, 90, 0, 200, 20 );
		adic( txtDescGrupo, 90, 20, 200, 20 );
		adic( lbSiglaGrupo, 293, 0, 80, 20 );
		adic( txtSiglaGrupo, 293, 20, 80, 20 );
		adic( cbEstNeg, 7, 50, 250, 20 );
		adic( cbEstLotNeg, 7, 70, 250, 20 );

		cbEstNeg.setEnabled( bEstNeg );
		cbEstLotNeg.setEnabled( bEstNeg );

		if ( sCod != null ) {
			setTitulo( "Edi��o de Grupo" );
			txtCodGrupo.setText( sCod );
			txtDescGrupo.setVlrString( sDesc );
			txtSiglaGrupo.setVlrString( sSigla );

			cbEstLotNeg.setVlrString( sEstNegLot );
			cbEstNeg.setVlrString( sEstNeg );

			txtDescGrupo.selectAll();
			txtCodGrupo.setBackground( Color.lightGray );
			txtCodGrupo.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
			txtCodGrupo.setEditable( false );
			txtCodGrupo.setForeground( new Color( 118, 89, 170 ) );
			bEdit = true;
		}
	}

	public String[] getValores() {

		String[] sRetorno = new String[ 5 ];
		sRetorno[ 0 ] = txtCodGrupo.getText();
		sRetorno[ 1 ] = txtDescGrupo.getText();
		sRetorno[ 2 ] = txtSiglaGrupo.getText();
		sRetorno[ 3 ] = cbEstNeg.getVlrString();
		sRetorno[ 4 ] = cbEstLotNeg.getVlrString();
		return sRetorno;
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( txtCodGrupo.getText().trim().length() == 0 ) {
				Funcoes.mensagemInforma( this, "C�diogo em branco! ! ! " );
				txtCodGrupo.requestFocus();
				return;
			}
			else if ( txtDescGrupo.getText().trim().length() == 0 ) {
				Funcoes.mensagemInforma( this, "Descri��o em branco! ! !" );
				txtDescGrupo.requestFocus();
				return;
			}
			else if ( ( !bEdit ) & ( !verifCod() ) ) {
				Funcoes.mensagemInforma( this, "C�digo j� existe! ! !" );
				txtCodGrupo.requestFocus();
				return;
			}
		}
		super.actionPerformed( evt );
	}

	private boolean verifCod() {

		String sSQL = "SELECT CODGRUP FROM EQGRUPO WHERE CODEMP=? AND CODFILIAL=? AND CODGRUP=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQGRUPO" ) );
			ps.setString( 3, txtCodGrupo.getVlrString());
			rs = ps.executeQuery();

			if( rs.next() ) {
				return false;
			}
					
			con.commit();
			
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao consultar a tabela GRUPO!" + "\n" + err.getMessage(), true, con, err );
		}
		return true;
	}
}
