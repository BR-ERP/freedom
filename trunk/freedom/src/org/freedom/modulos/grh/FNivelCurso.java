/**
 * @version 28/01/2008 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FNivelCurso.java <BR>
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
 * Formul�rio para cadastro de n�veis de cursos, para uso nas fun��es de recrutamento e sele��o.
 * 
 */

package org.freedom.modulos.grh;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FNivelCurso extends FDados {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodNivelCurso = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescNivelCurso = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );

	public FNivelCurso() {

		super();
		setTitulo( "Cadastro de n�veis de cursos" );
		setAtribos( 50, 50, 380, 135 );
		
		montaTela();
		
		setImprimir( false );
	}
	
	private void montaTela() {

		adicCampo( txtCodNivelCurso, 7, 20, 70, 20, "CodNivelCurso", "C�d.N�vel", ListaCampos.DB_PK, true );
		adicCampo( txtDescNivelCurso, 80, 20, 250, 20, "DescNivelCurso", "Descri��o do n�vel", ListaCampos.DB_SI, true );
		setListaCampos( true, "NIVELCURSO", "RH" );
		lcCampos.setQueryInsert( false );
	}
}
