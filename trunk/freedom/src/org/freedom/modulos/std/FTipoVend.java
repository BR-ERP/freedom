/**
 * @version 13/05/2008 <BR>
 * @author Setpoint Inform�tica Ltda./Felipe Daniel Elias <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: FTipoVend
 * @(#)FClasCli.java <BR>
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
 */

package org.freedom.modulos.std;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;



public class FTipoVend extends FDados{
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodTipoVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	
	public FTipoVend(){
		
		super();
		setAtribos( 50, 50, 350, 165 );
		setTitulo( "Cadastro de tipos de comissionado" );
		adicCampo( txtCodTipoVenda, 7, 20, 70, 20, "CODTIPOVEND", "C�d.tp.vend", ListaCampos.DB_PK, true );
		adicCampo( txtDescTipoVenda, 80, 20, 250, 20, "DESCTIPOVEND", "Descri��o do tipo da venda", ListaCampos.DB_PK, true );
		setListaCampos( true, "TIPOVEND", "VD" );
		
	}

}
