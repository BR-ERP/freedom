/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FLogin.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Coment�rios para a classe...
 */

package org.freedom.library.swing.frame;

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;
import org.freedom.infra.functions.SystemFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;

public class LoginPD extends Login implements ActionListener, FocusListener {
	private static final long serialVersionUID = 1L;

	public LoginPD() {
		super();
	}

	public void inicializaLogin() {

	}

	public DbConnection getConection() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		DbConnection conRet = null;

		if (conLogin == null)
			return null;

		// Compara a vers�o do banco com a vers�o do aplicativo;

		String versaosis = SystemFunctions.getVersionSis(this.getClass());
		String versaobd = SystemFunctions.getVersionDB(conLogin);

		if (( versaosis.compareTo(versaobd) == 0 ) || ( ( "N".equals(Aplicativo.getParameter("validaversao").trim()) ) )) {

			if (bAdmin) {
				if (adicConFilial(conLogin)) {
					conRet = conLogin;
				}
			}

			sql.append("SELECT G.IDGRPUSU, U.ATIVOUSU, U.IDUSU FROM SGGRPUSU G, SGUSUARIO U ");
			sql.append("WHERE G.IDGRPUSU=U.IDGRPUSU AND G.CODEMP=U.CODEMPIG AND ");
			sql.append("G.CODFILIAL=U.CODFILIALIG AND U.IDUSU=? ");

			ps = conLogin.prepareStatement(sql.toString());
			ps.setString(1, txtUsuario.getVlrString().trim().toLowerCase());
			rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println("IDGRUP = " + rs.getString("IDGRPUSU"));
				props.put("sql_role_name", rs.getString("IDGRPUSU"));

				if ("N".equals(rs.getString("ATIVOUSU"))) {
					throw new Exception("O usu�rio " + rs.getString("IDUSU") + " est� inativo!");
				}

				rs.close();
				ps.close();
				conLogin.close();
				conRet = new DbConnection(strBanco, props);
				adicConFilial(conRet);

			}
		}
		else {
			throw new Exception("A vers�o do banco de dados (" + versaobd + ") � diferente da vers�o dos execut�veis (" + versaosis + ")\n"
					+ "Realize os procedimentos de atualiza��o do Sistema, ou contate o suporte t�cnico.");
		}

		return conRet;

	}

	protected boolean execConexao(String sUsu, String sSenha) {

		nfe = Aplicativo.getParameter("nfe");
		nfse = Aplicativo.getParameter("nfse");

		strBanco = Aplicativo.getParameter("banco");
		strDriver = Aplicativo.getParameter("driver");

		try {
			Class.forName(strDriver);
		}
		catch (java.lang.ClassNotFoundException e) { 
			Funcoes.mensagemErro(this, "Driver n�o foi encontrado:\n" + strDriver + "\n" + e.getMessage());
			return false;
		}

		try {
			props.put("user", sUsu);
			props.put("password", sSenha);
			conLogin = new DbConnection(strBanco, props);
			conLogin.setAutoCommit(false);
		}
		catch (java.sql.SQLException e) {
			if (e.getErrorCode() == 335544472 || e.getErrorCode() == 335544345)
				Funcoes.mensagemErro(this, "Nome do usu�rio ou senha inv�lidos ! ! !");
			else
				Funcoes.mensagemErro(this, "N�o foi poss�vel estabelecer conex�o com o banco de dados.\n" + e.getMessage());
			e.printStackTrace();
			return false;
		}

		if ("S".equalsIgnoreCase(nfe)) {

			strBanconfe = Aplicativo.getParameter("banconfe");

			try {
				props.put("user", sUsu);
				props.put("password", sSenha);
				conNFE = new DbConnection(strBanconfe, props);
				conNFE.setAutoCommit(false);
			}
			catch (java.sql.SQLException e) {
				if (e.getErrorCode() == 335544472 || e.getErrorCode() == 335544345)
					Funcoes.mensagemErro(this, "Nome do usu�rio ou senha inv�lidos ! ! !");
				else
					Funcoes.mensagemErro(this, "N�o foi poss�vel estabelecer conex�o com o banco de dados nfe.\n" + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}
		// Nota fiscal eletr�nica de servi�os
		if("S".equalsIgnoreCase(nfse)) {
			
			
			
			
			
		}

		txtUsuario.setAtivo(false);
		txpSenha.setEditable(false);
		
		return true;
	}

	protected boolean montaCombo(String sUsu) {
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			if (bAdmin) {
				sql.append("SELECT CODFILIAL,NOMEFILIAL,1 FROM SGFILIAL FL WHERE CODEMP=?");
			}
			else {
				sql.append("SELECT FL.CODFILIAL,FL.NOMEFILIAL,AC.CODFILIAL FROM SGFILIAL FL, SGACESSOEU AC ");
				sql.append("WHERE FL.CODEMP = ? AND LOWER(AC.IDUSU) = ? AND ");
				sql.append("FL.CODEMP = AC.CODEMPFL AND FL.CODFILIAL = AC.CODFILIALFL");
			}
			ps = conLogin.prepareStatement(sql.toString());
			ps.setInt(1, Aplicativo.iCodEmp);
			if (!bAdmin) {
				ps.setString(2, sUsu);
			}
			rs = ps.executeQuery();
			vVals.clear();
			vLabs.clear();
			while (rs.next()) {
				vVals.addElement(new Integer(rs.getInt("CODFILIAL")));
				vLabs.addElement(rs.getString("NOMEFILIAL") != null ? rs.getString("NOMEFILIAL") : "");
				if (rs.getInt(1) == rs.getInt(3))
					iFilialPadrao = rs.getInt(1);
			}

			cbEmp.setItensGeneric(vLabs, vVals);
			cbEmp.setVlrInteger(new Integer(iFilialPadrao));

			sUsuAnt = sUsu;

			// Buscar c�digo da filial matriz
			sql.delete(0, sql.length());
			sql.append("SELECT FL.CODFILIAL FROM SGFILIAL FL ");
			sql.append("WHERE FL.CODEMP=? AND FL.MZFILIAL=?");
			ps = conLogin.prepareStatement(sql.toString());
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setString(2, "S");
			rs = ps.executeQuery();
			if (rs.next())
				iFilialMz = rs.getInt("CODFILIAL");
			rs.close();
			ps.close();
			conLogin.commit();

		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar dados da empresa\n" + err);
			err.printStackTrace();
		}
		finally {
			sql = null;
			rs = null;
			ps = null;
		}
		return true;

	}

	private void atualizalogins(DbConnection con) {

		String desativa_triggers_orig = "ALTER TRIGGER #TRIGGER# INACTIVE";

		String ativa_triggers_orig = "ALTER TRIGGER #TRIGGER# ACTIVE";

		String select_trigger_orig = "select rdb$trigger_name from rdb$triggers where rdb$relation_name = '#TABELA#' and rdb$trigger_name not like 'CHECK%'";

		StringBuilder select_tabelas = new StringBuilder("select rdb$relation_name from rdb$relations where rdb$flags is not null and rdb$relation_name not like '%VW%'");

		String update_tabelas_orig = "update #TABELA# set hins=cast('now' as time), dtins=cast('today' as date), idusuins=user" + " where hins is null or dtins is null or idusuins is null";

		String update_table = null;
		String select_trigger = null;
		String desativa_trigger = null;
		String ativa_trigger = null;

		ResultSet rs = null;
		PreparedStatement ps = null;

		Vector<String> tabelas = new Vector<String>();
		Vector<String> triggers = null;

		try {
			ps = con.prepareStatement(select_tabelas.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				tabelas.addElement(rs.getString("rdb$relation_name").trim());
			}
			con.commit();

			for (int i = 0; tabelas.size() > i; i++) {

				select_trigger = select_trigger_orig.replaceAll("#TABELA#", tabelas.elementAt(i));

				ps = con.prepareStatement(select_trigger);
				rs = ps.executeQuery();

				triggers = new Vector<String>();

				while (rs.next()) {
					triggers.addElement(rs.getString("rdb$trigger_name"));
				}

				for (int i2 = 0; triggers.size() > i2; i2++) {
					System.out.println("DESATIVANDO TRIGGER:" + triggers.elementAt(i2));
					desativa_trigger = desativa_triggers_orig.replaceAll("#TRIGGER#", triggers.elementAt(i2).trim());
					ps = con.prepareStatement(desativa_trigger);
					ps.execute();
				}

				con.commit();

				System.out.println("ATUALIZANDO TABELA: " + tabelas.elementAt(i));

				update_table = update_tabelas_orig.replaceAll("#TABELA#", tabelas.elementAt(i));

				ps = con.prepareStatement(update_table);

				ps.execute();

				for (int i2 = 0; triggers.size() > i2; i2++) {
					System.out.println("REATIVANDO TRIGGER:" + triggers.elementAt(i2));
					ativa_trigger = ativa_triggers_orig.replaceAll("#TRIGGER#", triggers.elementAt(i2));
					ps = con.prepareStatement(ativa_trigger);
					ps.execute();
				}

				con.commit();

			}

			con.commit();

			System.out.println("FINALIZOU ATUALIZA��ES!!!");

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected boolean adicConFilial(DbConnection conX) {
		boolean bRet = false;
		String sSQL = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {

			if ("S".equalsIgnoreCase(Aplicativo.getParameter("gera_log_ins"))) {
				atualizalogins(conX);
			}

			sSQL = "SELECT SRET FROM SGINICONSP(?,?,?,?)";
			ps = conX.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setString(2, txtUsuario.getVlrString().trim().toLowerCase());
			if (iFilialPadrao == 0)
				ps.setNull(3, Types.INTEGER);
			else
				ps.setInt(3, iFilialPadrao);
			ps.setInt(4, iCodEst);
			rs = ps.executeQuery();
			if (rs.next())
				bRet = rs.getInt(1) == 1; // grava true se tiver efetuado a
			// conexao
			rs.close();
			ps.close();
			conX.commit();
			// ps = conX.prepareStatement(
			// "SELECT CURRENT_CONNECTION FROM SGEMPRESA" );
			// rs = ps.executeQuery();
			// if ( rs.next() ) {
			// System.out.println("1-Conex�o: "+rs.getInt( "CURRENT_CONNECTION"
			// ));
			// }

		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao gravar filial atual no banco!\n" + err.getMessage());
			err.printStackTrace();
		}
		finally {
			rs = null;
			ps = null;
			sSQL = null;
		}
		return bRet;
	}

}
