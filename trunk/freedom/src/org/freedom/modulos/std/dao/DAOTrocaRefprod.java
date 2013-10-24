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

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;


public class DAOTrocaRefprod extends AbstractDAO {

	public DAOTrocaRefprod( DbConnection connection, Integer codemp, Integer codfilial ) {
		super( connection, codemp, codfilial );
	}

}
