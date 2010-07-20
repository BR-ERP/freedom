/**
 * @version 18/02/2008 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.grh <BR>
 *         Classe:
 * @(#)FEmpregado.java <BR>
 * 
 *                     Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                     na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                     Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                     Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                     Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Tela de cadastro de empregados.
 * 
 */

package org.freedom.modulos.grh.view.frame.crud.plain;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FCurso extends FDados {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodCurso = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtDescCurso = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );

	private final JTextFieldPad txtIntituicaoCurso = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private final JTextFieldPad txtDuracaoCurso = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextAreaPad txtConteudoCurso = new JTextAreaPad( 1000 );

	private final JTextFieldPad txtCodNivel = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescNivel = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private final JTextFieldPad txtCodArea = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescArea = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private final ListaCampos lcNivel = new ListaCampos( this, "NC" );

	private final ListaCampos lcArea = new ListaCampos( this, "AR" );

	public FCurso() {

		super( false );

		nav.setNavigation( true );
		setTitulo( "Cadastro de Cursos" );
		setAtribos( 50, 50, 425, 400 );

		montaListaCampos();

		montaTela();

		setImprimir( false );
	}

	private void montaListaCampos() {

		lcNivel.add( new GuardaCampo( txtCodNivel, "CodNivelCurso", "C�d.n�vel", ListaCampos.DB_PK, true ) );
		lcNivel.add( new GuardaCampo( txtDescNivel, "DescNivelCurso", "Descri��o do n�vel do curso", ListaCampos.DB_SI, false ) );
		lcNivel.montaSql( false, "NIVELCURSO", "RH" );
		lcNivel.setQueryCommit( false );
		lcNivel.setReadOnly( true );
		txtCodNivel.setTabelaExterna( lcNivel, FNivelCurso.class.getCanonicalName() );

		lcArea.add( new GuardaCampo( txtCodArea, "CodArea", "C�d.�rea", ListaCampos.DB_PK, true ) );
		lcArea.add( new GuardaCampo( txtDescArea, "DescArea", "Descri��o da �rea", ListaCampos.DB_SI, false ) );
		lcArea.montaSql( false, "AREA", "RH" );
		lcArea.setQueryCommit( false );
		lcArea.setReadOnly( true );
		txtCodArea.setTabelaExterna( lcArea, FArea.class.getCanonicalName() );
	}

	private void montaTela() {

		adicCampo( txtCodCurso, 7, 20, 90, 20, "CodCurso", "C�d.curso", ListaCampos.DB_PK, true );
		adicCampo( txtDescCurso, 100, 20, 300, 20, "DescCurso", "Descri��o do curso", ListaCampos.DB_SI, true );
		adicCampo( txtCodNivel, 7, 60, 90, 20, "CodNivelCurso", "C�d.n�vel", ListaCampos.DB_FK, true );
		adicDescFK( txtDescNivel, 100, 60, 300, 20, "DescNivelCurso", "Descri��o do n�vel" );
		adicCampo( txtCodArea, 7, 100, 90, 20, "CodArea", "C�d.�rea", ListaCampos.DB_FK, false );
		adicDescFK( txtDescArea, 100, 100, 300, 20, "DescArea", "Descri��o da �rea" );

		adicCampo( txtIntituicaoCurso, 7, 140, 270, 20, "instituicaoCurso", "Institui��o de ensino", ListaCampos.DB_SI, false );
		adicCampo( txtDuracaoCurso, 280, 140, 120, 20, "DuracaoCurso", "Duracao(em meses)", ListaCampos.DB_SI, false );

		adicDB( txtConteudoCurso, 7, 180, 393, 140, "ContProgCurso", "Conte�do program�tico", false );

		setListaCampos( true, "CURSO", "RH" );
		lcCampos.setQueryInsert( false );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcNivel.setConexao( cn );
		lcArea.setConexao( cn );
	}
}
