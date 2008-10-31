/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Felipe Daniel Elias <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLGuiaTraf.java <BR>
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

package org.freedom.modulos.std;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLGuiaTraf extends FFDialogo implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodEmp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodFilial = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodItCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 3, 0 );

	private JTextFieldPad txtDtEmissGuiaTraf = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtNumGuiaTraf = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtSeloGuiaTraf = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private JLabelPad lbCodCompra = new JLabelPad( "C�d.compra" );

	private JLabelPad lbCodEmp = new JLabelPad( "C�d.emp" );

	private JLabelPad lbCodFilial = new JLabelPad( "C�d.filial" );

	private JLabelPad lbCodItCompra = new JLabelPad( "C�d.item" );

	private JLabelPad lbNumGuiaTraf = new JLabelPad( "N�mero da guia" );

	private JLabelPad lbDataEmissGuia = new JLabelPad( "Data de emiss�o" );

	private JLabelPad lbSeloGuiaTraf = new JLabelPad( "Selo da Guia" );
	
	//int codcompra;
	
	//int coditcompra;
	
	

	public DLGuiaTraf() {

		super();
		setTitulo( "Guia de tr�fego" );
		setAtribos( 350, 150 );

		txtDtEmissGuiaTraf.setRequerido( true );
		txtNumGuiaTraf.setRequerido( true );
		txtSeloGuiaTraf.setRequerido( true );

		adic( lbDataEmissGuia, 7, 0, 100, 20 );
		adic( txtDtEmissGuiaTraf, 7, 20, 100, 20 );
		adic( lbNumGuiaTraf, 110, 0, 100, 20 );
		adic( txtNumGuiaTraf, 110, 20, 100, 20 );
		adic( lbSeloGuiaTraf, 213, 0, 100, 20 );
		adic( txtSeloGuiaTraf, 213, 20, 100, 20 );
		
		//this.codcompra = codcompra;
		//this.coditcompra = coditcompra;

	}

	private void gravaGuiaTraf() {

		if ( txtDtEmissGuiaTraf.getVlrString().equals( "" ) ) {

			Funcoes.mensagemInforma( this, "A data de emiss�o � requerida!" );
		}
		else if ( txtNumGuiaTraf.getVlrString().equals( "" ) ) {

			Funcoes.mensagemInforma( this, "O n�mero da guia � requerido!" );
		}
		else if ( txtSeloGuiaTraf.getVlrString().equals( "" ) ) {

			Funcoes.mensagemInforma( this, "O selo da guia � requerido" );

		}

		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;

		sSQL.append( "INSERT INTO EQGUIATRAF( CODEMP, CODFILIAL, CODCOMPRA, CODITCOMPRA, DTEMISGUIATRAF, " );
		sSQL.append( "NROGUIATRAF, NROGUIASELOTRAF ) VALUES (?,?,?,?,?,?,?)" );

		try {

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQGUIATRAF" ) );
			//ps.setInt( 3, codcompra );
			//ps.setInt( 4, coditcompra );
			

		} catch ( Exception e ) {

		}

	}

}


