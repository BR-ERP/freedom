/**
 * @version 14/10/2013 <BR>
 * @author Setpoint Tecnologia em Inform�tica Ltda./<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.pcp.dao <BR>
 *         Classe: @(#)DAOOp.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * 
 * 
 */

package org.freedom.modulos.pcp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;

public class DAOOp extends AbstractDAO {

	public DAOOp( DbConnection cn ) throws Exception {
		super( cn );
		loadPrefere();
	}

	private HashMap<String, Object> prefere = null;

	private void loadPrefere() throws Exception {

		prefere = new HashMap<String, Object>();
		boolean[] bRetorno = new boolean[ 1 ];
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		sql.append( "SELECT P1.USAREFPROD, P5.RATAUTO, coalesce(prodetapas,'S') prodetapas " );
		sql.append( ", coalesce(P5.VALIDAQTDOP,'N') VALIDAQTDOP, coalesce(P5.VALIDAFASEOP,'N') VALIDAFASE" );
		sql.append( ", coalesce(P5.EDITQTDOP, 'S') EDITQTDOP, coalesce(P5.OPSEQ,'N') OPSEQ " );
		sql.append( "FROM SGPREFERE1 P1,SGPREFERE5 P5 " );
		sql.append( "WHERE P1.CODEMP=? AND P1.CODFILIAL=? " );
		sql.append( "AND P5.CODEMP=? AND P5.CODFILIAL=?" );

		bRetorno[ 0 ] = false;
		ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
		ps.setInt( 3, Aplicativo.iCodEmp );
		ps.setInt( 4, ListaCampos.getMasterFilial( "SGPREFERE5" ) );

		rs = ps.executeQuery();

		if ( rs.next() ) {
			prefere.put( "USAREFPROD", new Boolean( rs.getString( "USAREFPROD" ).trim().equals( "S" ) ) );
			prefere.put( "RATAUTO", new Boolean( rs.getString( "RATAUTO" ).trim().equals( "S" ) ) );
			prefere.put( "PRODETAPAS", new Boolean( rs.getString( "prodetapas" ).trim().equals( "S" ) ) );
			prefere.put( "VALIDAQTDOP", new Boolean( rs.getString( "VALIDAQTDOP" ).trim().equals( "S" ) ) );
			prefere.put( "VALIDAFASE", new Boolean( rs.getString( "VALIDAFASE" ).trim().equals( "S" ) ) );
			prefere.put( "EDITQTDOP", new Boolean( rs.getString( "EDITQTDOP" ).trim().equals( "S" ) ) );
			prefere.put( "OPSEQ", new Boolean( rs.getString( "OPSEQ" ).trim().equals( "S" ) ) );
		}
		else {
			prefere.put( "USAREFPROD", new Boolean( false ) );
			prefere.put( "RATAUTO", new Boolean( false ) );
			prefere.put( "PRODETAPAS", new Boolean( true ) );
			prefere.put( "VALIDAQTDOP", new Boolean( false ) );
			prefere.put( "VALIDAFASE", new Boolean( false ) );
			prefere.put( "EDITQTDOP", new Boolean( true ) );
			prefere.put( "OPSEQ", new Boolean( false ) );
			throw new Exception("N�o foram encontradas prefer�ncias para o m�dulo PCP!" );
		}
		rs.close();
		ps.close();
		getConn().commit();

	}

	
	public HashMap<String, Object> getPrefere() {
	
		return prefere;
	}

	
	public void setPrefere( HashMap<String, Object> prefere ) {
	
		this.prefere = prefere;
	}

}
