/**
 * @version 24/10/2013 <BR>
 * @author Setpoint Tecnologia em Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.dao <BR>
 *         Classe: * @(#)DAOTrocaRefprod.java <BR>
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
 *                     Classe respons�vel pela persist�ncia dos dados, auxiliar a tela de troca de refer�ncia de produtos.
 * 
 */
package org.freedom.modulos.std.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;


public class DAOTrocaRefprod extends AbstractDAO {

	public DAOTrocaRefprod( DbConnection connection, Integer codemp, Integer codfilial ) {
		super( connection, codemp, codfilial );
	}

	public StringBuffer seekRefprod(String refprod) throws Exception {
		StringBuffer found = new StringBuffer();
		StringBuilder sql = new StringBuilder();
		sql.append("select codprod, descprod, refprod from eqproduto where codemp=? and codfilial=? and refprod=?");
		try {
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, getCodfilial() );
			ps.setString( param++, refprod );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				found.append("Refer�ncia est� sendo utilizada em outro produto.\nC�digo: ");
				found.append( rs.getInt( "codprod" ) );
				found.append(" - ");
				found.append("Descri��o: ");
				found.append(rs.getString( "descprod" ).trim());
			}
		} catch (SQLException e) {
			getConn().rollback();
			throw new Exception(e.getMessage());
		}
		return found;
	}
}
