/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FPrincipal.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios para a classe...
 */

package org.freedom.telas;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;

public class FPrincipalPD extends FPrincipal implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	public FPrincipalPD( String sDirImagem, String sImgFundo ) {

		this( sDirImagem, sImgFundo, null, null, null, null, null );
	}

	public FPrincipalPD( String sDirImagem, String sImgFundo, String sImgLogoSis, String sImgLogoEmp, Color bgcolor, String urlempresa, String urlsistema ) {
		
		super( sDirImagem, sImgFundo, sImgLogoSis, sImgLogoEmp, bgcolor, urlempresa, urlsistema );

	}

	
	public void inicializaTela() {
		addFundo();
		addLinks( Icone.novo( imgLogoEmp ) , Icone.novo( imgLogoSis ));		
		setBgColor( padrao );
		adicBotoes();

	}

	public void remConFilial() {

		String sSQL = "EXECUTE PROCEDURE SGFIMCONSP";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
//			ps.setInt( 1, Aplicativo.iCodEmp );
//			ps.setInt( 2, Aplicativo.iCodFilialPad );
//			ps.setString( 3, Aplicativo.strUsuario );
			ps.execute();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao remover filial ativa no banco!\n" + err.getMessage() );
		}
	}

	public void fecharJanela() {

		if ( con != null ) {
			try {
				remConFilial();
				con.close();
			} catch ( java.sql.SQLException e ) {
				System.out.println("N�o foi poss�vel fechar a conexao com o banco de dados!");
//				Funcoes.mensagemErro( null, "N�o foi poss�vel fechar a conexao com o banco de dados!" );
			}
		}
		System.exit( 0 );
	}
}
