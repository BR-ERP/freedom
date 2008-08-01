/**
 * @version 01/08/2008 <BR>
 * @author Setpoint Inform�tica Ltda.
 * @author Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FMetodoAnalitico.java <BR>
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

package org.freedom.modulos.pcp;

import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;


public class FMetodoAnalitico extends FDados {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodMtAnalise = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldPad txtDescMtAnalise = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextAreaPad txaObsMtAnalise = new JTextAreaPad();

	public FMetodoAnalitico(){
		
		setTitulo( "M�todos Anal�ticos" );
		setAtribos( 50, 50, 380, 220 );
		montaTela();
	}
	
	private void montaTela(){
		
		adicCampo( txtCodMtAnalise, 7, 20, 70, 20, "CodMtAnalise", "C�d.M�todo", ListaCampos.DB_PK, true );
		adicCampo( txtDescMtAnalise, 80, 20, 260, 20, "DescMtAnalise", "Descri��o do m�todo anal�tico", ListaCampos.DB_SI, true );
		adicDB( txaObsMtAnalise, 7, 60, 335, 80, "ObsMtAnalise", "Observa��o do m�todo", false );
		
		setListaCampos( true, "METODOANALISE", "PP" );
	
	}
}
