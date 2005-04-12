/**
 * @version 25/06/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRRazaoFin.java <BR>
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
 * Coment�rios sobre a classe...
 *  
 */

package org.freedom.modulos.std;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRRazaoFin extends FRelatorio {
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);

	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);

	private JTextFieldFK txtDescPlan = new JTextFieldFK(
			JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldPad txtCodPlan = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 13, 0);

	private ListaCampos lcPlan = new ListaCampos(this);

	public FRRazaoFin() {
		setTitulo("Relat�rio raz�o financeiro");
		setAtribos(80, 80, 330, 180);

		lcPlan.add(new GuardaCampo(txtCodPlan, "CodPlan", "C�d.plan",
				ListaCampos.DB_PK, false));
		lcPlan.add(new GuardaCampo(txtDescPlan, "DescPlan",
				"Descri��o do planejamento", ListaCampos.DB_SI, false));
		lcPlan.montaSql(false, "PLANEJAMENTO", "FN");
		lcPlan.setWhereAdic("NIVELPLAN=6");
		lcPlan.setReadOnly(true);
		txtCodPlan.setTabelaExterna(lcPlan);
		txtCodPlan.setFK(true);
		txtCodPlan.setNomeCampo("CodPlan");

		adic(new JLabelPad("Periodo:"), 7, 5, 125, 20);
		adic(new JLabelPad("De:"), 7, 25, 30, 20);
		adic(txtDataini, 32, 25, 125, 20);
		adic(new JLabelPad("At�:"), 160, 25, 22, 20);
		adic(txtDatafim, 185, 25, 125, 20);
		adic(new JLabelPad("N� planejamento"), 7, 50, 250, 20);
		adic(txtCodPlan, 7, 70, 100, 20);
		adic(new JLabelPad("Descri��o do planejamento"), 110, 50, 240, 20);
		adic(txtDescPlan, 110, 70, 200, 20);

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,
				cPeriodo.get(Calendar.DAY_OF_MONTH) - 30);
		txtDataini.setVlrDate(cPeriodo.getTime());
		txtCodPlan.setRequerido(true);
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcPlan.setConexao(cn);
	}

	private double buscaSaldo() {
		double dRet = 0.00;

		String sSQL = "SELECT SL.CODPLAN, SALDOSL FROM FNSALDOLANCA SL "
				+ "WHERE SL.CODEMP=? AND SL.CODFILIAL=? AND SL.CODEMPPN=? AND SL.CODFILIALPN=? AND SL.CODPLAN=? AND SL.DATASL="
				+ "(SELECT MAX(SL2.DATASL) FROM FNSALDOLANCA SL2 WHERE SL2.CODPLAN=SL.CODPLAN AND SL2.DATASL<?)";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			int iParam = 1;
			ps = con.prepareStatement(sSQL);

			//	 Parametros da subselect to max

			//	 Parametros da select master

			ps.setInt(iParam++, Aplicativo.iCodEmp);
			ps.setInt(iParam++, Aplicativo.iCodFilial);
			ps.setInt(iParam++, Aplicativo.iCodEmp);
			ps.setInt(iParam++, lcPlan.getCodFilial());
			ps.setString(iParam++, txtCodPlan.getVlrString());

			ps
					.setDate(iParam++, Funcoes.dateToSQLDate(txtDataini
							.getVlrDate()));

			rs = ps.executeQuery();

			if (rs.next())
				dRet = Funcoes
						.strCurrencyToDouble(rs.getString("SALDOSL") == null ? "0,00"
								: rs.getString("SALDOSL"));
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar saldo.\n"
					+ err.getMessage());
		}
		return dRet;
	}

	public void imprimir(boolean bVisualizar) {
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this,
					"Data final maior que a data inicial!");
			return;
		}
		ImprimeOS imp = new ImprimeOS("", con);
		int linPag = imp.verifLinPag() - 1;
		String sCodPlan = txtCodPlan.getVlrString().trim();
		String sConta = "";
		BigDecimal bVlrSubLanca = new BigDecimal("0");
		BigDecimal bTotal = new BigDecimal("0");

		String sDataini = "";
		String sDatafim = "";

		sDataini = txtDataini.getVlrString();
		sDatafim = txtDatafim.getVlrString();

		if (sCodPlan.equals("")) {
			Funcoes.mensagemInforma(this, "Informe um c�digo de conta !");
			return;
		}

		String sSQL = "SELECT SL.DATASUBLANCA,SL.CODLANCA,SL.HISTSUBLANCA,SL.VLRSUBLANCA "
				+ "FROM FNSUBLANCA SL WHERE SL.CODEMP=? AND "
				+ "SL.CODFILIAL=? AND SL.CODPLAN=? AND SL.CODEMPPN=? AND CODFILIALPN=? AND "
				+ "SL.DATASUBLANCA BETWEEN ? AND ? ORDER BY DATASUBLANCA";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			int iParam = 1;
			ps = con.prepareStatement(sSQL);

			ps.setInt(iParam++, Aplicativo.iCodEmp);
			ps.setInt(iParam++, Aplicativo.iCodFilial);
			ps.setString(iParam++, txtCodPlan.getVlrString());
			ps.setInt(iParam++, Aplicativo.iCodEmp);
			ps.setInt(iParam++, lcPlan.getCodFilial());
			ps
					.setDate(iParam++, Funcoes.dateToSQLDate(txtDataini
							.getVlrDate()));
			ps
					.setDate(iParam++, Funcoes.dateToSQLDate(txtDatafim
							.getVlrDate()));

			rs = ps.executeQuery();
			imp.limpaPags();

			while (rs.next()) {
				if (imp.pRow() == 0) {
					imp.montaCab();
					imp.setTitulo("Raz�o financeiro");
					imp.addSubTitulo("RELATORIO RAZ�O FINANCEIRO");
					if (!(sCodPlan.trim().equals(""))) {
						sConta = "CONTA: " + sCodPlan + " - "
								+ txtDescPlan.getVlrString();
						imp.addSubTitulo(sConta);
					}
					imp.impCab(136, true);

					String sSaldoAnt = Funcoes.strDecimalToStrCurrency(13, 2,
							buscaSaldo() + "");

					imp.say(imp.pRow() + (sCodPlan.trim().equals("") ? 0 : 1),
							0, "|" + Funcoes.replicate("-", 133) + "|");
					imp.say(imp.pRow() + 1, 0, "|");
					imp.say(imp.pRow() + 0, 104, "SALDO ANTERIOR:");
					imp.say(imp.pRow() + 0, 118, "" + sSaldoAnt);
					imp.say(imp.pRow(), 135, "|");
					imp.say(imp.pRow() + 1, 0, "|"
							+ Funcoes.replicate("-", 133) + "|");
					imp.say(imp.pRow() + 1, 0, "|"
							+ Funcoes.replicate("-", 133) + "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow(), 0, "| Data.");
					imp.say(imp.pRow(), 23, "| C�d.Lanc.");
					imp.say(imp.pRow(), 36, "| Hist�rico");
					imp.say(imp.pRow(), 89, "| Receita ");
					imp.say(imp.pRow(), 104, "| Despesa ");
					imp.say(imp.pRow(), 119, "| Saldo ");
					imp.say(imp.pRow(), 135, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-", 133)
							+ "|");
				}

				imp.say(imp.pRow() + 1, 1, "|");
				imp.say(imp.pRow() + 0, 3, Funcoes.dateToStrDate(rs
						.getDate("DATASUBLANCA")));
				imp.say(imp.pRow() + 0, 23, "|");
				imp.say(imp.pRow(), 25, rs.getString("CODLANCA"));
				imp.say(imp.pRow() + 0, 36, "|");
				imp.say(imp.pRow(), 38, rs.getString("HISTSUBLANCA"));

				bVlrSubLanca = new BigDecimal(rs.getString("VLRSUBLANCA"));
				bTotal = bTotal.add(bVlrSubLanca);

				if (bVlrSubLanca.doubleValue() < 0) {
					imp.say(imp.pRow() + 0, 89, "|");
					imp.say(imp.pRow(), 89, Funcoes.strDecimalToStrCurrency(13,
							2, (bVlrSubLanca.doubleValue() * -1) + ""));
					imp.say(imp.pRow() + 0, 104, "|");
					imp.say(imp.pRow() + 0, 119, "|");
				} else {
					imp.say(imp.pRow() + 0, 89, "|");
					imp.say(imp.pRow() + 0, 104, "|");
					imp.say(imp.pRow(), 104, Funcoes.strDecimalToStrCurrency(
							13, 2, bVlrSubLanca.doubleValue() + ""));
					imp.say(imp.pRow() + 0, 119, "|");
				}

				imp.say(imp.pRow() + 0, 121, ""
						+ Funcoes.strDecimalToStrCurrency(12, 2, "" + bTotal));
				imp.say(imp.pRow() + 0, 135, "|");
			}

			if (imp.pRow() == (linPag - 1)) {
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("-", 133)
						+ "+");
				imp.eject();
				imp.incPags();

			}

			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("=", 133) + "+");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "|");
			imp.say(imp.pRow() + 0, 104, "|");
			imp.say(imp.pRow() + 0, 106, "SALDO");
			imp.say(imp.pRow() + 0, 119, "| "
					+ Funcoes.strDecimalToStrCurrency(12, 2, "" + bTotal));
			imp.say(imp.pRow() + 0, 135, "|");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("=", 133) + "+");

			imp.eject();

			imp.fechaGravacao();

			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro na consulta de sublan�amentos!"
					+ err.getMessage());
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}
}