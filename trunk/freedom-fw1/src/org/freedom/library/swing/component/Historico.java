/**
 * @version 29/10/2013 <BR>
 * @author Setpoint Tecnologia em Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.library.swing.component <BR>
 * Classe: @(#)Historico.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Classe de armazenamento de logs. 
 */

package org.freedom.library.swing.component;

import java.util.Date;

public class Historico {
	private Date dataOperacao;
	private String TipoOperacao;
	private String historico;
	private Integer id;

	public Date getDataOperacao() {
		return dataOperacao;
	}

	public String getTipoOperacao() {
		return TipoOperacao;
	}

	public String getHistorico() {
		return historico;
	}

	public Integer getId() {
		return id;
	}

	public void setDataOperacao(Date dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		TipoOperacao = tipoOperacao;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
