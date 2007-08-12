/**
 * @version 02/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPEstacao.java <BR>
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
 * Tela de cadastros de esta��es de trabalho.
 * 
 */

package org.freedom.modulos.rep;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class RPEstacao extends FDados {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtDescEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	

	public RPEstacao() {

		super( false );
		setTitulo( "Esta��o de trabalho" );
		setAtribos( 50, 50, 420, 140 );
		
		montaTela();		
		setListaCampos( true, "ESTACAO", "SG" );
	}
	
	private void montaTela() {
		
		adicCampo( txtCodEst, 7, 30, 80, 20, "CodEst", "C�d.est.", ListaCampos.DB_PK, true );
		adicCampo( txtDescEst, 90, 30, 305, 20, "DescEst", "Descri��o da esta��o de trabalho", ListaCampos.DB_SI, true );
	}
}
