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
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios para a classe...
 */

package org.freedom.library.swing.frame;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.freedom.bmps.Icone;
import org.freedom.plugin.AbstractBackground;

public class FPrincipal2 extends FPrincipal implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;

	private AbstractBackground fundo = null;

	public FPrincipal2(AbstractBackground fundo, String sImgFundo) {
		super(null, sImgFundo);
		this.fundo = fundo;
	}

	public void inicializaTela() {
		setBgColor(new Color(255, 255, 255));
		addLinks(Icone.novo("lgSTP2.jpg"), Icone.novo("lgFreedom2.jpg"));
		adicBotoes();
		adicAgenda();
	}

	public void setConexao(DbConnection con) {
		super.setConexao(con);
		fundo.setConexao(con);
		addFundo(fundo);
	}

	public void remConFilial() {
		String sSQL = "EXECUTE PROCEDURE SGFIMCONSP";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			// ps.setInt( 1, Aplicativo.iCodEmp );
			// ps.setInt( 2, Aplicativo.iCodFilialPad );
			// ps.setString( 3, Aplicativo.strUsuario );
			ps.execute();
			ps.close();
			con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(null, "Erro ao remover filial ativa no banco!\n" + err.getMessage());
		}
	}

	public void fecharJanela() {
		if (con != null) {
			try {
				remConFilial();
				con.close();
			}
			catch (java.sql.SQLException e) {
				System.out.println("N�o foi poss�vel fechar a conexao com o banco de dados!");
				// Funcoes.mensagemErro(null,
				// "N�o foi poss�vel fechar a conexao com o banco de dados!" );
			}
		}
		System.exit(0);
	}

	@Override
	public void windowOpen() {
		
	}

}