/**
 * @version 19/09/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)DLRegBatida.java <BR>
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
 *         Tela que registra Batida de ponto do empregado.
 * 
 */

package org.freedom.modulos.crm.view.dialog.utility;

import java.awt.event.ActionEvent;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLPesqObsAtendo extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextAreaPad txaPesqObsAtend = new JTextAreaPad();
	
	private String mensagem = null;
	
	public DLPesqObsAtendo() {

		super();
		setTitulo( "Pesquisa atendimento." );
		setAtribos( 50, 50, 430, 200 );
		montaTela();
	
	}
	
	public void montaTela(){

		adic( txaPesqObsAtend, 7, 25, 400, 80, "Descri��o Atendimento" );
		
	}
	
	public void actionPerformed( ActionEvent evt ) {
		boolean result = false;
		if (evt.getSource()==btOK) {
            mensagem = txaPesqObsAtend.getVlrString();
		} 
		super.actionPerformed( evt );
	
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
	}

	
	public String getMensagem() {
	
		return mensagem;
	}

	
	public void setMensagem( String mensagem ) {
	
		this.mensagem = mensagem;
	}
}
