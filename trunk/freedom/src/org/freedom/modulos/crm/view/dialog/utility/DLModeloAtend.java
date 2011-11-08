/**
 * @version 08/11/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm.view.dialog.utility; <BR>
 *         Classe: @(#)DLModeloAtend.java <BR>
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
 *         Tela que retorna um modelo de atendimento.
 * 
 */

package org.freedom.modulos.crm.view.dialog.utility;

import java.awt.Component;
import java.sql.SQLException;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.crm.business.object.Atendimento;
import org.freedom.modulos.crm.dao.DAOAtendimento;
import org.freedom.modulos.crm.view.frame.crud.plain.FModAtendo;

public class DLModeloAtend extends FFDialogo {
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodmodel = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescmodel = new JTextFieldFK( JTextFieldFK.TP_STRING, 100, 0 );
	
	private Component corig = null;
	
	private DAOAtendimento daoatend = null;
	
	private DLAtendimento dl = null;
	
	private Atendimento atd = null;
	
	private ListaCampos lcModAtendo = new ListaCampos( this );


	public DLModeloAtend() {
		super();
		setTitulo( "Modelo de atendimento" );
		setAtribos( 50, 50, 450, 200 );
		montaListaCampos();
		montaTela();
		
	}
	
	private void montaTela(){
		
		adic( txtCodmodel, 7, 30, 80, 20, "C�d.model." );
		adic( txtDescmodel, 90, 30, 300, 20, "Descri��o do modelo" );
		
	}
	
	private void montaListaCampos(){
		
		lcModAtendo.add( new GuardaCampo( txtCodmodel, "CodModel", "C�d.Model", ListaCampos.DB_PK, false ) );
		lcModAtendo.add( new GuardaCampo( txtDescmodel, "DescModel", "Descri��o do atendimento", ListaCampos.DB_SI, false ) );
		lcModAtendo.montaSql( false, "MODATENDO", "AT" );
		lcModAtendo.setReadOnly( true );
		txtCodmodel.setNomeCampo( "CodModel" );
		txtCodmodel.setFK( true );
		txtCodmodel.setTabelaExterna( lcModAtendo, FModAtendo.class.getCanonicalName() );
		
	}
	
	public Integer getCodModel(){
		Integer result = null;
		if(!"".equals( txtCodmodel.getText().trim() ) ) {
			result = txtCodmodel.getVlrInteger();
		}
		return result;
	}
	
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcModAtendo.setConexao( cn );
		
		daoatend = new DAOAtendimento( cn );
		try {
			daoatend.setPrefs( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "SGPREFERE3" ) );
		} catch (SQLException e) {
			Funcoes.mensagemErro( this, "Erro carregando prefer�ncias !\b" + e.getMessage() );
		}
		

	}
	
}
