/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FPrincipal.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para
 * Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios para a classe...
 */

package org.freedom.telas;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;
import org.freedom.plugin.Backgroud34;

public class FPrincipal2 extends FPrincipal implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	
	private Backgroud34 fundo = new Backgroud34();
	
	public FPrincipal2(String sImgFundo) {
		super(null, sImgFundo);
	}

	public void inicializaTela() {
		setBgColor(new Color(255,255,255)); 
	    addLinks(Icone.novo("lgSTP2.jpg"), Icone.novo("lgFreedom2.jpg"));
		adicBotoes();
		adicAgenda();
	}
	
	public void setConexao( Connection con ) {
		super.setConexao( con );
		fundo.setConexao( con );
		addFundo( fundo );
	}

	public void remConFilial(){ 	
		String sSQL = "EXECUTE PROCEDURE SGFIMCONSP(?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilialPad );
			ps.setString( 3, Aplicativo.strUsuario );
			ps.execute();
			ps.close();
			if ( !con.getAutoCommit() )
				con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(null, "Erro ao remover filial ativa no banco!\n" + err.getMessage() );
		}
	}	
	
	public void fecharJanela() {
		if ( con != null ) {
			try {
				remConFilial();
				con.close();
			} catch ( java.sql.SQLException e ) {
				Funcoes.mensagemErro(null, "N�o foi poss�vel fechar a conexao com o banco de dados!" );
			}
		}
		System.exit( 0 );
	}
		
}