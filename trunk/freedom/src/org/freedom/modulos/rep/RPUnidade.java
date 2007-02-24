/**
 * @version 02/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPUnidade.java <BR>
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
 * Tela para cadastro de unidades.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class RPUnidade extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtDescUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	public RPUnidade() {

		super();
		setTitulo( "Cadastro de unidades" );		
		setAtribos( 50, 50, 410, 140 );
		
		montaTela();
		setListaCampos( true, "UNIDADE", "RP" );
	}
	
	private void montaTela() {
		
		adicCampo( txtCodUnid, 7, 30, 70, 20, "CodUnid", "C�d.unid.", ListaCampos.DB_PK, true );
		adicCampo( txtDescUnid, 80, 30, 300, 20, "DescUnid", "Descri��o da unidade", ListaCampos.DB_SI, true );
	}
}
