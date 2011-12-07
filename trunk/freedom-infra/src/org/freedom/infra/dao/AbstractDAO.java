/**
 * @version 02/08/2011 <BR>
 * @author Setpoint Tecnologia em Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.infra.dao <BR>
 * Classe: @(#)AbstractDAO.java <BR>
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
 * Classe base para implementa��es de m�todos de acesso a dados
 */

package org.freedom.infra.dao;

import org.freedom.infra.model.jdbc.DbConnection;

public abstract class AbstractDAO {
	private DbConnection conn;
	Integer codemp = null; 
	Integer codfilial = null;
	
	public AbstractDAO(DbConnection connection) {
		setConn(connection);
	}
	
	public AbstractDAO(DbConnection connection, Integer codemp, Integer codfilial) {
		this(connection);
		setCodemp(codemp);
		setCodfilial(codfilial);
	}
	
	public DbConnection getConn() {
		return conn;
	}

	public void setConn(DbConnection conn) {
		this.conn = conn;
	}

	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	public Integer getCodfilial() {
		return codfilial;
	}

	public void setCodfilial(Integer codfilial) {
		this.codfilial = codfilial;
	}	
}
