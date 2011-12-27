/**
 * @version 30/07/2011 <BR>
 * @author Setpoint Inform�tica Ltda. <BR>
 * @author Fabiano Frizzo(ffrizzo at gmail.com) <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.fnc.view.dialog.utility <BR>
 *         Classe:
 * @(#)DLEstornoMultiplaBaixaRecebimento.java <BR>
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

package org.freedom.modulos.fnc.view.dialog.utility;

import java.awt.Component;

import org.freedom.infra.model.jdbc.DbConnection;

public class DLEstornoMultiplaBaixaRecebimento extends DLEstornoMultiplaBaixa {

	private static final long serialVersionUID = 1L;
	
	public DLEstornoMultiplaBaixaRecebimento( Component cOrig, DbConnection cn, int iCodRec, int iNParc ) {

		super( cOrig, cn, iCodRec, iNParc );
	}

	@ Override
	public String getSqlSelect() {
		StringBuilder sql = new StringBuilder();
		sql.append( "SELECT  S.DATASUBLANCA,(S.VLRSUBLANCA*-1) VLRSUBLANCA,S.HISTSUBLANCA, S.CODLANCA " );
		sql.append( "FROM FNSUBLANCA S WHERE ");
		sql.append( "S.CODREC=? AND S.NPARCITREC=? AND S.CODEMP=? " );
		sql.append( "AND S.CODFILIAL=? AND S.TIPOSUBLANCA='P' " );
		sql.append( "ORDER BY DATASUBLANCA" );
		return sql.toString();
	}

	
}
