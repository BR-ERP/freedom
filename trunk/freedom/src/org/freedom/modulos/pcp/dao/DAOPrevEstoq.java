/**
 * @version 22/04/2013 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento<BR>
 *  
 *          Projeto: Freedom <BR>
 * 
 *          Pacote: org.freedom.modulos.pcp.dao <BR>
 *          Classe: @(#)DAOPull.java <BR>
 * 
 *                  Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                  modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                  na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                  Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                  sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                  Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                  Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                  de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                  DAO que possui a responsabilidade sobre o Sistema de produ��o Puxada ( Pull System ).
 * 
 */


package org.freedom.modulos.pcp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;

public class DAOPrevEstoq extends AbstractDAO {

	public DAOPrevEstoq( DbConnection cn) {
		super( cn );
	}


	public boolean comRef() throws SQLException {

		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;


		ps = Aplicativo.getInstace().getConexao().prepareStatement( sSQL );

		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

		rs = ps.executeQuery();

		if ( rs.next() )
			if ( rs.getString( "UsaRefProd" ).trim().equals( "S" ) )
				bRetorno = true;

		ps.close();
		rs.close();
		return bRetorno;
	}

	
}


