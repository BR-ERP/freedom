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
 * Formul�rio para cadastro de n�veis de cursos, para uso nas fun��es de recrutamento e sele��o.
 * 
 */

package org.freedom.modulos.grh;

import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.telas.FDados;

public class FNivelCurso extends FDados {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodNivelCurso = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtDescNivelCurso = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );

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
