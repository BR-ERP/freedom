/**
 * @version 17/09/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gpe.dao <BR>
 *         Classe:
 * @(#)DAOBatida.java <BR>
 * 
 *                Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *              Classe de acesso a dados para entidade PEBATIDA
 * 
 */
package org.freedom.modulos.gpe.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modulos.gpe.business.object.Batida;
import org.freedom.modulos.gpe.business.object.Batida.PREFS;


public class DAOBatida extends AbstractDAO {
	private Object prefs[] = null;
	
	public DAOBatida( DbConnection connection ) {

		super( connection );
		
	}

	public void setPrefs(Integer codemp, Integer codfilial) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		prefs = new Object[ Batida.PREFS.values().length];
		
		try {
			sql = new StringBuilder("select lancapontoaf  " );
			sql.append( "from sgprefere3 p " );
			sql.append( "where  p.codemp=? and p.codfilial=?" );
			
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				prefs[ PREFS.LANCAPONTOAF.ordinal() ] = new Boolean("S".equals(rs.getString( PREFS.LANCAPONTOAF.toString() )));
			}
			rs.close();
			ps.close();
			getConn().commit();
		} finally {
			ps = null;
			rs = null;
			sql = null;
		}
	}
	
	public boolean carregaPonto(Integer codemp, Integer codfilial, String idusu) {
		boolean result = false;
		result = (Boolean) prefs[ PREFS.LANCAPONTOAF.ordinal()];
		if ( result ) { // Verifica se o sistema est� configurado para carregar tela de ponto
			
		} 
		return result;
	}
	
}
