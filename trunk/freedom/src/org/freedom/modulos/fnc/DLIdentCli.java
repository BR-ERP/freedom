/**
 * @version 07/03/2007 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLIdentCli.java <BR>
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
 */

package org.freedom.modulos.fnc;

import java.awt.Component;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLIdentCli extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private final JTextFieldPad txtRazCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtAgenciaCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 9, 0 );

	private final JTextFieldPad txtIdentCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private static boolean MRET = false;

	public DLIdentCli( final Component cOrig ) {

		super( cOrig );
		setTitulo( "Identifica��o do cliente" );
		setAtribos( 420, 200 );
		
		txtCodCli.setAtivo( false );
		txtRazCli.setAtivo( false );
		
		montaTela();
	}
	
	private void montaTela() {
		
		txtAgenciaCli.setRequerido( true );
		txtIdentCli.setRequerido( true );
		
		adic( new JLabelPad( "C�d.cli." ), 7, 5, 80, 20 );
		adic( txtCodCli, 7, 25, 80, 20 );
		adic( new JLabelPad( "Raz�o social do cliente" ), 90, 5, 280, 20 );
		adic( txtRazCli, 90, 25, 280, 20 );

		adic( new JLabelPad( "Ag�ncia" ), 7, 50, 80, 20 );
		adic( txtAgenciaCli, 7, 70, 80, 20 );
		adic( new JLabelPad( "Identifica��o" ), 90, 50, 100, 20 );
		adic( txtIdentCli, 90, 70, 100, 20 );
	}

	public static Object[] execIdentCli( final Component cOrig, final Integer codCli, final String razCli, final String agenciaCli, final String identCli ) {

		Object[] retorno = { new Boolean( false ), "", "" };
		DLIdentCli dl = new DLIdentCli( cOrig );
		
		dl.txtCodCli.setVlrInteger( codCli );
		dl.txtRazCli.setVlrString( razCli );
		dl.txtAgenciaCli.setVlrString( agenciaCli );
		dl.txtIdentCli.setVlrString( identCli );
		dl.execShow();
		
		if ( MRET ) {
			retorno[ 0 ] = new Boolean( MRET );
			retorno[ 1 ] = dl.txtAgenciaCli.getVlrString();
			retorno[ 2 ] = dl.txtIdentCli.getVlrString();
		}
		
		return retorno;
	}

	@ Override
	public void ok() {

		if ( txtAgenciaCli.getVlrString().trim().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Campo ag�ncia � obrigat�rio!" );
			txtAgenciaCli.requestFocus();
			return;
		}
		else if ( txtIdentCli.getVlrString().trim().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Campo identifica��o � obrigat�rio!" );
			txtIdentCli.requestFocus();
			return;
		}
		
		MRET = true;
		
		super.ok();
	}

	@ Override
	public void cancel() {

		MRET = false;
		
		super.cancel();
	}

}
