/**
 * @version 25/07/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FLeituraX.java <BR>
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
 * Tela para impress�o de leitura X
 * 
 */

package org.freedom.modulos.pdv;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;

import org.freedom.componentes.JLabelPad;
import org.freedom.ecf.app.ControllerECF;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class FLeituraX extends FFDialogo implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JLayeredPane pn = new JLayeredPane();

	private final ControllerECF ecf; 
	

	public FLeituraX() {

		super();
		
		setAtribos( 400, 200 );
		
		ecf = new ControllerECF( 
				AplicativoPDV.getEcfdriver(), 
				AplicativoPDV.getPortaECF(), 
				AplicativoPDV.bModoDemo, 
				AplicativoPDV.getEcflayout() );
				
		StringBuffer texto = new StringBuffer();
		texto.append( "<HTML>" );
		texto.append( "Impress�o de leitura X.<BR><BR>" );
		texto.append( "Pressione [ OK ] para confirmar a impress�o.<BR>" );
		texto.append( "Pressione [ CANCELAR ] para sair!<BR>" );
		texto.append( "</HTML>" );
		
		JLabelPad label = new JLabelPad( texto.toString() );
		label.setFont( new Font( "Arial", Font.PLAIN, 14 ) );
		label.setForeground( Color.BLUE );
		label.setBounds( 40, 10, 300, 100 );
		
		pn.add( label );

		c.add( pn );
		
		eUltimo();
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( ! ecf.leituraX() ) {
				Funcoes.mensagemErro( this, ecf.getMessageLog() );
			}
		}
	
		super.actionPerformed( evt );
	}
}
