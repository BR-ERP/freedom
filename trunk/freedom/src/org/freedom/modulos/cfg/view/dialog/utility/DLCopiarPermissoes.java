package org.freedom.modulos.cfg.view.dialog.utility;
/**
 * 
 * @version 30/07/2011 <BR>
 * @author Setpoint Inform�tica Ltda. <BR>
 * @author Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.cfg.view.dialog.utility <BR>
 *         Classe:
 * @(#)DLCopiarPermissoes.java <BR>
 * 
 *                   Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                   modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                   na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                   Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                   sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                   Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                   Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 */

import java.awt.event.ActionEvent;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FDialogo;

public class DLCopiarPermissoes extends FDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtNomeUsu = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcUsuario = new ListaCampos( this );
	
	private String usuario;

	private String tipoPessoa;
	private String cnpjAtual;
	private boolean permiteMesmoCnpj;
	public DLCopiarPermissoes() {
		super();
		setTitulo( "Copiar Permiss�es" );
		setAtribos( 400, 130 );
		this.montaListaCampos();
		this.montaTela();
	}
	
	public void montaListaCampos() {

		txtCodUsu.setNomeCampo( "IdUsuario" );
		lcUsuario.add( new GuardaCampo( txtCodUsu, "IDUSU", "ID Usuario", ListaCampos.DB_PK, txtNomeUsu, false ) );
		lcUsuario.add( new GuardaCampo( txtNomeUsu, "NOMEUSU", "Nome", ListaCampos.DB_SI, false ) );
		lcUsuario.montaSql( false, "USUARIO", "SG" );
		lcUsuario.setQueryCommit( false );
		lcUsuario.setReadOnly( true );
		txtCodUsu.setChave( ListaCampos.DB_PK );
		txtCodUsu.setListaCampos( lcUsuario );
		txtNomeUsu.setListaCampos( lcUsuario );
		txtCodUsu.setTabelaExterna( lcUsuario, null );
		txtCodUsu.setNomeCampo( "idusu" );

	}

	private void montaTela() {

		adic( new JLabelPad( "ID Usu�rio" ), 7, 0, 100, 20 );
		adic( txtCodUsu, 7, 20, 100, 20 );
		adic( new JLabelPad( "Nome" ), 110, 0, 250, 20 );
		adic( txtNomeUsu, 110, 20, 250, 20 );
	}
	
	
	@ Override
	public void setConexao( DbConnection cn ) {
		lcUsuario.setConexao( cn );
		
		super.setConexao( cn );
	}


	@ Override
	public void actionPerformed( ActionEvent evt ) {

		if(evt.getSource() == btOK){
			if("".equals( txtCodUsu.getVlrString())) {
				Funcoes.mensagemInforma( this, "Selecione o usu�rio!!!");
				return;
			}
		}
		this.usuario = txtCodUsu.getVlrString(); 
		
		super.actionPerformed( evt );
	}
	
	public String getUsuario() {
		
		return usuario;
	}

}