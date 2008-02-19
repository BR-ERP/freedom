/**
 * @version 28/01/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FArea.java <BR>
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
 * Formul�rio para cadastro das �reas de conhecimento para uso nas fun��es de recrutamento e sele��o.
 * 
 */

package org.freedom.modulos.grh;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FArea extends FDados {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodArea = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtDescArea = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );

	public FArea() {

		super();
		setTitulo( "Cadastro de Areas" );
		setAtribos( 50, 50, 380, 135 );

		montaTela();
		
		setImprimir( false );
	}

	private void montaTela() {

		adicCampo( txtCodArea, 7, 20, 70, 20, "CodArea", "C�d.area", ListaCampos.DB_PK, true );
		adicCampo( txtDescArea, 80, 20, 250, 20, "DescArea", "Descri��o da area", ListaCampos.DB_SI, true );
		setListaCampos( true, "AREA", "RH" );
		lcCampos.setQueryInsert( false );
	}
}
