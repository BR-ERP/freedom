/**
 * @version 02/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPTipoCli.java <BR>
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
 * Tela para cadastro de tipos de clientes.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class RPTipoCli extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescTipoCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtTipo = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	public RPTipoCli() {

		super();
		setTitulo( "Cadastro de tipos de clientes" );		
		setAtribos( 50, 50, 410, 140 );
		
		montaTela();
		setListaCampos( true, "TIPOCLI", "RP" );
	}
	
	private void montaTela() {
		
		adicCampo( txtCodTipoCli, 7, 30, 70, 20, "CodTipoCli", "C�d.tp.cli.", ListaCampos.DB_PK, true );
		adicCampo( txtDescTipoCli, 80, 30, 250, 20, "DescTipoCli", "Descri��o do tipo de cliente", ListaCampos.DB_SI, true );
		adicCampo( txtTipo, 333, 30, 50, 20, "TipoCli", "Tipo", ListaCampos.DB_SI, false );
	}
}
