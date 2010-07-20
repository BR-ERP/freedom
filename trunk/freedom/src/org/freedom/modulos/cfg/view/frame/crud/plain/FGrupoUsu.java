/**
 * @version 08/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.cfg <BR>
 *         Classe: @(#)FGrupo.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.cfg.view.frame.crud.plain;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JScrollPane;

import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FGrupoUsu extends FDados implements PostListener, DeleteListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtNomeGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextAreaPad txaComentGrup = new JTextAreaPad( JTextFieldPad.TP_STRING );

	private JScrollPane spnObs = new JScrollPane( txaComentGrup );

	public FGrupoUsu() {

		super();
		setTitulo( "Cadastro de Grupos" );
		setAtribos( 50, 50, 400, 200 );

		adicCampo( txtCodGrup, 7, 20, 80, 20, "IDGrpUsu", "ID", ListaCampos.DB_PK, true );
		adicCampo( txtNomeGrup, 90, 20, 282, 20, "NomeGrpUsu", "Nome", ListaCampos.DB_SI, true );
		adicDBLiv( txaComentGrup, "ComentGrpUsu", "Coment�rio", false );
		adic( new JLabelPad( "Coment�rio" ), 7, 40, 100, 20 );
		adic( spnObs, 7, 60, 365, 60 );
		setListaCampos( false, "GRPUSU", "SG" );
		lcCampos.addPostListener( this );
		lcCampos.addDeleteListener( this );
		lcCampos.setQueryInsert( false );
	}

	public void beforePost( PostEvent pevt ) {

		if ( lcCampos.getStatus() != ListaCampos.LCS_INSERT )
			return;
		try {
			PreparedStatement ps = con.prepareStatement( "CREATE ROLE " + txtCodGrup.getVlrString() );
			ps.execute();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemInforma( this, "N�o foi poss�vel criar grupo no banco de dados.\n" + err );
			pevt.cancela();
		}
	}

	public void beforeDelete( DeleteEvent devt ) {

		try {
			PreparedStatement ps = con.prepareStatement( "DROP ROLE " + txtCodGrup.getVlrString() );
			ps.execute();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemInforma( this, "N�o foi poss�vel excluir o grupo no banco de dados.\n" + err );
			devt.cancela();
		}
	}

	public void afterPost( PostEvent pevt ) {

	}

	public void afterDelete( DeleteEvent devt ) {

	}
}
