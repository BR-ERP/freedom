package org.freedom.modulos.pdv;

/**
 * @version 30/06/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FFechaVenda.java <BR>
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
 * 
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.comutacao.Tef;
import org.freedom.drivers.ECFDriver;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFilho;

public class FAdmTef extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JComboBoxPad cbComando = null;

	private JButton btExec = new JButton( Icone.novo( "btExecuta.gif" ) );

	private JPanelPad pn = new JPanelPad();

	private ECFDriver ecf = new ECFDriver( !AplicativoPDV.usaEcfDriver() );

	private Tef tef = AplicativoPDV.bTEFTerm ? new Tef( Aplicativo.strTefEnv, Aplicativo.strTefRet ) : null;

	private Properties retTef = null;

	public FAdmTef() {

		super( false );
		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();
		vLabs.addElement( "<--Selecione-->" );
		vLabs.addElement( "Administrativas - Outras" );
		vVals.addElement( "" );
		vVals.addElement( "1" );
		cbComando = new JComboBoxPad( vLabs, vVals, JTextFieldPad.TP_STRING, 2, 0 );

		setTitulo( "Administra��o TEF" );
		setAtribos( 100, 100, 350, 150 );

		pn.adic( new JLabelPad( "Comando a ser disparado:" ), 10, 10, 250, 15 );
		pn.adic( cbComando, 10, 25, 250, 20 );
		pn.adic( btExec, 270, 15, 30, 30 );

		getTela().add( pn, BorderLayout.CENTER );
		adicBotaoSair();

		btExec.addActionListener( this );
		
		if ( tef == null ) {
			Funcoes.mensagemErro( null, "Este terminal n�o esta capacitado para TEF." );
			dispose();
		}
	}

	private boolean processaTef() {

		boolean bRet = false;

		if ( cbComando.getVlrString().equals( "1" ) ) {
			retTef = tef.solicAdm();
		}
		else {
			return false;
		}

		if ( retTef == null || ! tef.validaTef( retTef ) ) {
			bRet = false;
		}
		else {
			bRet = true;
		}

		return bRet;
	}

	private boolean finalizaTEF( final Properties retTef ) {

		boolean bRet = false;
		Object sLinhas[] = tef.retImpTef( retTef );
		String sComprovante = "";

		// verifica se ha linhas a serem impressas, caso contr�rio sai sem
		// imprimir nada.
		if ( sLinhas.length == 0 ) {
			return true;
		}

		for ( int i = 0; i < sLinhas.length; i++ ) {
			sComprovante += sLinhas[ i ] + "\n";
		}

		for ( int i = 0; i < 3 || bRet; i++ ) {
			if ( ! ecf.relatorioGerencialTef( sComprovante ) ) {
				bRet = false;
			}
			else {
				if ( ! ecf.fechaRelatorioGerencial() ) {
					bRet = false;
				}
				else {
					bRet = true;
				}
			}
		}
		
		if ( ! bRet ) {
			Funcoes.mensagemInforma( null, "Impress�o n�o confimada, transa��o cancelada." );
			tef.naoConfirmaCNF( retTef );
		}
		
		tef.confirmaCNF( retTef );
		
		return bRet;

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btExec ) {
			if ( processaTef() ) {
				finalizaTEF( retTef );
			}
		}
	}
}
