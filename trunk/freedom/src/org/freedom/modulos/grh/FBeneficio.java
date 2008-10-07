/**
 * @version 03/10/2008 <BR>
 * @author Setpoint Inform�tica Ltda./Felipe Daniel Elias <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FFuncao.java <BR>
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
 * Tela de cadastro de fun��es
 * 
 */

package org.freedom.modulos.grh;

import java.awt.event.ActionListener;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FBeneficio extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodBenef = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtDescBenef = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );

	private final JTextFieldPad txtVlrBenef = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	public FBeneficio() {

		super();
		setTitulo( "Cadastro de Benef�cios" );
		setAtribos( 50, 50, 470, 125 );

		montaTela();
	}

	private void montaTela() {

		adicCampo( txtCodBenef, 7, 20, 70, 20, "CodBenef", "C�d.benef.", ListaCampos.DB_PK, true );
		adicCampo( txtDescBenef, 80, 20, 260, 20, "DescBenef", "Descri��o do benef�cio", ListaCampos.DB_SI, true );
		adicCampo( txtVlrBenef, 345, 20, 100, 20, "ValorBenef", "Valor( R$ )", ListaCampos.DB_SI, false );
		setListaCampos( true, "BENEFICIO", "RH" );
		lcCampos.setQueryInsert( false );
	}

}
