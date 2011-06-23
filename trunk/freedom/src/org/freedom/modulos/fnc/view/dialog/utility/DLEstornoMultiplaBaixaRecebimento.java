/**
 * @version 08/04/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLCosultaBaixa.java <BR>
 * 
 *                         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                         Coment�rios sobre a classe...
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
		StringBuilder sSQL = new StringBuilder();
		sSQL.append( "SELECT  S.DATASUBLANCA,S.VLRSUBLANCA,S.HISTSUBLANCA, S.CODLANCA " );
		sSQL.append( "FROM FNSUBLANCA S, FNLANCA L WHERE S.CODLANCA=L.CODLANCA " );
		sSQL.append( "AND S.CODEMP=L.CODEMP AND S.CODFILIAL=L.CODFILIAL " );
		sSQL.append( "AND L.CODREC=? AND L.NPARCITREC=? AND L.CODEMP=? " );
		sSQL.append( "AND L.CODFILIAL=? AND S.CODSUBLANCA=0 " );
		sSQL.append( "ORDER BY DATASUBLANCA" );
		return sSQL.toString();
	}

	
}
