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
import org.freedom.drivers.ECFDriver;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class FLeituraX extends FFDialogo implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JLayeredPane pn = new JLayeredPane();

	private ECFDriver ecf = new ECFDriver( !AplicativoPDV.usaEcfDriver() );

	public FLeituraX() {

		super();
		
		setAtribos( 400, 200 );
				
		StringBuffer texto = new StringBuffer();
		texto.append( "<HTML>" );
		texto.append( "Impress�o de leitura X.<BR><BR>" );
		texto.append( "Pressione \" OK \" para confirmar a impress�o.<BR>" );
		texto.append( "Pressione \" CANCELAR \" para sair!<BR>" );
		texto.append( "</HTML>" );
		
		JLabelPad label = new JLabelPad( texto.toString() );
		label.setFont( new Font( "Arial", Font.PLAIN, 14 ) );
		label.setForeground( Color.BLUE );
		label.setBounds( 40, 20, 300, 100 );
		
		pn.add( label );

		c.add( pn );
		
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK && ecf != null ) {
			ecf.leituraX();
		}
	
		super.actionPerformed( evt );
	}
}
