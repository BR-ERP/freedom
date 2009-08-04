/*
 * Projeto: Setpoint-nfe
 * Pacote: org.freedom.modulos.nfe.database.jdbc
 * Classe: @(#)NFEConnectionFactory.java
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 */
package org.freedom.modules.nfe.bean;

/**
 * 
 * @author Setpoint Inform�tica Ltda./Robson Sanchez
 * @version 04/04/2009
 */
public class FreedomNFEKey extends AbstractNFEKey {
	
	public static final String CODEMP = "CODEMP";
	
	public static final String CODFILIAL = "CODFILIAL";
	
	public static final String CODVENDA = "CODVENDA";
	
	public static final String TIPOVENDA = "TIPOVENDA";
	
	public static final String DIRNFE = "DIRNFE";

	
	public FreedomNFEKey( Integer codemp, Integer codfilial, String tipovenda, Integer codvenda, String dirNFE ) {
	
		put( CODEMP, codemp );
		put( CODFILIAL, codfilial );
		put( TIPOVENDA, tipovenda );
		put( CODVENDA, codvenda );
		put( DIRNFE, dirNFE );
	}
}
