/**
 * @version 25/06/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRCentroCusto.java <BR>
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
 * Relat�rio financeiro com filtro por contas e centros de custos.
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
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRCentroCusto extends FRelatorio {
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodConta = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 10, 0);

	private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,
			19, 0);

	private JTextFieldFK txtSiglaCC = new JTextFieldFK(JTextFieldPad.TP_STRING,
			10, 0);

	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);

	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);

	private JTextFieldFK txtDescConta = new JTextFieldFK(
			JTextFieldPad.TP_STRING, 40, 0);

	private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,
			50, 0);

	private JTextFieldPad txtCodPlan = new JTextFieldPad(
			JTextFieldPad.TP_STRING, 13, 0);

	private JCheckBoxPad cbUsaAnal = new JCheckBoxPad("Somente anal�ticas",
			"S", "N");

	private ListaCampos lcCC = new ListaCampos(this);

	private ListaCampos lcConta = new ListaCampos(this);

	int iAnoBase = 0;

	public FRCentroCusto() {
		setTitulo("Relat�rio financeiro por Centro de Custo");
		setAtribos(80, 80, 330, 240);

		lcConta.add(new GuardaCampo(txtCodConta, "NumConta", "C�d.conta",
				ListaCampos.DB_PK, false));
		lcConta.add(new GuardaCampo(txtDescConta, "DescConta",
				"Descri��o da conta", ListaCampos.DB_SI, false));
		lcConta.add(new GuardaCampo(txtCodPlan, "CodPlan", "Cod.plan.",
				ListaCampos.DB_SI, false));
		lcConta.montaSql(false, "CONTA", "FN");
		lcConta.setReadOnly(true);
		txtCodConta.setTabelaExterna(lcConta);
		txtCodConta.setFK(true);
		txtCodConta.setNomeCampo("NumConta");

		lcCC.add(new GuardaCampo(txtCodCC, "CodCC", "C�d.cc.",
				ListaCampos.DB_PK, false));
		lcCC.add(new GuardaCampo(txtSiglaCC, "SiglaCC", "Sigla",
				ListaCampos.DB_SI, false));
		lcCC.add(new GuardaCampo(txtDescCC, "DescCC",
				"Descri��o do centro de custo", ListaCampos.DB_SI, false));
		lcCC.setReadOnly(true);
		lcCC.montaSql(false, "CC", "FN");
		txtCodCC.setTabelaExterna(lcCC);
		txtCodCC.setFK(true);
		txtCodCC.setNomeCampo("CodCC");
		txtSiglaCC.setListaCampos(lcCC);

		adic(new JLabelPad("Periodo:"), 7, 5, 120, 20);
		adic(new JLabelPad("De:"), 7, 25, 30, 20);
		adic(txtDataini, 40, 25, 117, 20);
		adic(new JLabelPad("At�:"), 160, 25, 22, 20);
		adic(txtDatafim, 185, 25, 120, 20);
		adic(new JLabelPad("N� da conta"), 7, 50, 250, 20);
		adic(txtCodConta, 7, 70, 80, 20);
		adic(new JLabelPad("Descri��o da conta"), 90, 50, 250, 20);
		adic(txtDescConta, 90, 70, 200, 20);
		adic(new JLabelPad("C�d.cc."), 7, 90, 250, 20);
		adic(txtCodCC, 7, 110, 80, 20);
		adic(new JLabelPad("Descri��o do centro de custo"), 90, 90, 250, 20);
		adic(txtDescCC, 90, 110, 200, 20);
		adic(cbUsaAnal, 7, 140, 150, 20);

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,
				cPeriodo.get(Calendar.DAY_OF_MONTH) - 30);
		txtDataini.setVlrDate(cPeriodo.getTime());
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		iAnoBase = buscaAnoBaseCC();
		lcConta.setConexao(cn);
		lcCC.setConexao(cn);
		lcCC.setWhereAdic("ANOCC=" + iAnoBase);
	}

	private int buscaAnoBaseCC() {
		int iRet = 0;
		String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				iRet = rs.getInt("ANOCENTROCUSTO");
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Erro ao buscar o ano-base para o centro de custo.\n"
							+ err.getMessage(),true,con,err);
		}
		return iRet;
	}

	public void imprimir(boolean bVisualizar) {
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this,
					"Data final maior que a data inicial!");
			return;
		}
		ImprimeOS imp = new ImprimeOS("", con);
		int linPag = imp.verifLinPag() - 1;
		int iNivelcc = 0;
		String sCodPlan = txtCodPlan.getVlrString().trim();
		String sCodCC = txtCodCC.getVlrString().trim();
		String sCC = "";
		String sConta = "";

		BigDecimal bTotal = new BigDecimal("0");

		String sDesccc = "";
		String sSiglacc = "";

		String sSQL = "SELECT CM.CODCC,CM.SIGLACC,CM.DESCCC,CM.NIVELCC,"
				+
				//  Primeira sub-select >>
				"(SELECT SUM(SL.VLRSUBLANCA*-1) FROM FNSUBLANCA SL, FNLANCA L, FNPLANEJAMENTO PL"
				+ " WHERE L.FLAG IN "
				+ Aplicativo.carregaFiltro(con,
						org.freedom.telas.Aplicativo.iCodEmp)
				+ (sCodPlan.equals("") ? "" : " AND L.CODPLAN=?")
				+ " AND L.CODEMP=CM.CODEMP"
				+ " AND L.CODFILIAL=?"
				+ " AND SL.CODEMP=L.CODEMP"
				+ " AND SL.CODFILIAL=L.CODFILIAL"
				+ " AND SL.CODLANCA=L.CODLANCA"
				+ " AND SL.DATASUBLANCA BETWEEN ? AND ?"
				+ " AND SUBSTR(SL.CODCC,1,STRLEN(RTRIM(CM.CODCC)))=RTRIM(CM.CODCC)"
				+ " AND SL.CODEMPCC=CM.CODEMP"
				+ " AND SL.CODFILIALCC=CM.CODFILIAL"
				+ " AND SL.CODSUBLANCA > 0"
				+ " AND PL.CODPLAN=SL.CODPLAN"
				+ " AND PL.CODEMP=SL.CODEMPPN"
				+ " AND PL.CODFILIAL=SL.CODFILIALPN"
				+ " AND PL.TIPOPLAN = 'R'),"
				+
				//  Fim da primeira e inicio da segunda >>
				"(SELECT SUM(SL.VLRSUBLANCA*-1) FROM FNSUBLANCA SL, FNLANCA L, FNPLANEJAMENTO PL"
				+ " WHERE L.FLAG IN "
				+ Aplicativo.carregaFiltro(con,
						org.freedom.telas.Aplicativo.iCodEmp)
				+ (sCodPlan.equals("") ? "" : " AND L.CODPLAN=?")
				+ " AND L.CODEMP=CM.CODEMP"
				+ " AND L.CODFILIAL=?"
				+ " AND SL.CODEMP=L.CODEMP"
				+ " AND SL.CODFILIAL=L.CODFILIAL"
				+ " AND SL.CODLANCA=L.CODLANCA"
				+ " AND SL.DATASUBLANCA BETWEEN ? AND ?"
				+ " AND SUBSTR(SL.CODCC,1,STRLEN(RTRIM(CM.CODCC)))=RTRIM(CM.CODCC)"
				+ " AND SL.CODEMPCC=CM.CODEMP"
				+ " AND SL.CODFILIALCC=CM.CODFILIAL"
				+ " AND SL.CODSUBLANCA > 0"
				+ " AND PL.CODPLAN=SL.CODPLAN"
				+ " AND PL.CODEMP=SL.CODEMPPN"
				+ " AND PL.CODFILIAL=SL.CODFILIALPN"
				+ " AND PL.TIPOPLAN = 'D'),"
				+
				//  Fim da segunda e inicio da terceira >>
				"(SELECT SUM(SL.VLRSUBLANCA*-1) FROM FNSUBLANCA SL, FNLANCA L, FNPLANEJAMENTO PL"
				+ " WHERE L.FLAG IN "
				+ Aplicativo.carregaFiltro(con,
						org.freedom.telas.Aplicativo.iCodEmp)
				+ (sCodPlan.equals("") ? "" : " AND L.CODPLAN=?")
				+ " AND L.CODEMP=CM.CODEMP"
				+ " AND L.CODFILIAL=?"
				+ " AND SL.CODEMP=L.CODEMP"
				+ " AND SL.CODFILIAL=L.CODFILIAL"
				+ " AND SL.CODLANCA=L.CODLANCA"
				+ " AND SL.DATASUBLANCA BETWEEN ? AND ?"
				+ " AND SUBSTR(SL.CODCC,1,STRLEN(RTRIM(CM.CODCC)))=RTRIM(CM.CODCC)"
				+ " AND SL.CODEMPCC=CM.CODEMP"
				+ " AND SL.CODFILIALCC=CM.CODFILIAL"
				+ " AND SL.CODSUBLANCA > 0"
				+ " AND PL.CODPLAN=SL.CODPLAN"
				+ " AND PL.CODEMP=SL.CODEMPPN"
				+ " AND PL.CODFILIAL=SL.CODFILIALPN"
				+ " AND PL.TIPOPLAN IN ('R','D'))"
				+
				//  Fim da terceira sub-select>>
				" FROM FNCC CM"
				+ " WHERE CM.CODEMP=? AND CM.CODFILIAL=? AND ANOCC=?"
				+ (sCodCC.equals("") ? ""
						: " AND SUBSTR(CM.CODCC,1,STRLEN(RTRIM('" + sCodCC
								+ "')))=RTRIM('" + sCodCC + "')")
				+ (cbUsaAnal.getVlrString().equals("S") ? " AND CM.NIVELCC=10"
						: "") + " ORDER BY CM.CODCC,CM.DESCCC,CM.NIVELCC";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			int iParam = 1;
			ps = con.prepareStatement(sSQL);

			//Debitos:

			if (!sCodPlan.trim().equals(""))
				ps.setString(iParam++, sCodPlan);
			ps.setInt(iParam++, ListaCampos.getMasterFilial("FNLANCA"));
			ps
					.setDate(iParam++, Funcoes.dateToSQLDate(txtDataini
							.getVlrDate()));
			ps
					.setDate(iParam++, Funcoes.dateToSQLDate(txtDatafim
							.getVlrDate()));

			//Creditos:

			if (!sCodPlan.trim().equals(""))
				ps.setString(iParam++, sCodPlan);
			ps.setInt(iParam++, ListaCampos.getMasterFilial("FNLANCA"));
			ps
					.setDate(iParam++, Funcoes.dateToSQLDate(txtDataini
							.getVlrDate()));
			ps
					.setDate(iParam++, Funcoes.dateToSQLDate(txtDatafim
							.getVlrDate()));

			//Saldo:

			if (!sCodPlan.trim().equals(""))
				ps.setString(iParam++, sCodPlan);
			ps.setInt(iParam++, ListaCampos.getMasterFilial("FNLANCA"));
			ps
					.setDate(iParam++, Funcoes.dateToSQLDate(txtDataini
							.getVlrDate()));
			ps
					.setDate(iParam++, Funcoes.dateToSQLDate(txtDatafim
							.getVlrDate()));

			ps.setInt(iParam++, Aplicativo.iCodEmp);
			ps.setInt(iParam++, ListaCampos.getMasterFilial("FNCC"));
			ps.setInt(iParam++, iAnoBase);

			/*
			 * if (!sCodConta.trim().equals("")) {
			 * ps.setInt(iParam++,ListaCampos.getMasterFilial("FNCONTA"));
			 * ps.setString(iParam++,sCodConta); } if
			 * (!sCodCC.trim().equals("")) {
			 * ps.setInt(iParam++,ListaCampos.getMasterFilial("FNCC"));
			 * ps.setString(iParam++,sCodCC); }
			 */
			rs = ps.executeQuery();
			imp.limpaPags();
			
			imp.setTitulo("Balancete");
			imp.addSubTitulo("RELATORIO FINANCEIRO POR CENTRO DE CUSTO");
			
			while (rs.next()) {
				if (imp.pRow() == 0) {
					imp.montaCab();
					
					if (!(sCodPlan.trim().equals(""))) {
						sConta = "CONTA: " + sCodPlan + " - "
								+ txtDescConta.getVlrString();
						imp.addSubTitulo(sConta);
					}
					if (!(sCodCC.equals(""))) {
						sCC = "CENTRO DE CUSTO: " + txtSiglaCC.getVlrString()
								+ " - " + txtDescCC.getVlrString();
						imp.addSubTitulo(sCC);
					}
					imp.impCab(136, true);

					imp.say(imp.pRow() + 0, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|");
					imp.say(imp.pRow() + 0, 135, "|");
					imp.say(imp.pRow() + 1, 0, "|"
							+ Funcoes.replicate("-", 133) + "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow(), 0, "| C�d.cc.");
					imp.say(imp.pRow(), 23, "| Sigla ");
					imp.say(imp.pRow(), 36, "| Descri��o ");
					imp.say(imp.pRow(), 89, "| Receita ");
					imp.say(imp.pRow(), 104, "| Despesa ");
					imp.say(imp.pRow(), 119, "| Saldo ");
					imp.say(imp.pRow(), 135, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-", 133)
							+ "|");
				}

				if (rs.getString(7) != null) {
					iNivelcc = rs.getString("NIVELCC") == null ? 1 : rs
							.getInt("NIVELCC");
					iNivelcc = iNivelcc == 0 ? 1 : iNivelcc;
					sDesccc = " " + Funcoes.replicate(" ", iNivelcc)
							+ rs.getString("DESCCC");
					sSiglacc = Funcoes.copy(
							rs.getString("SIGLACC") != null ? rs
									.getString("SIGLACC") : "", 1, 10);
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow(), 0, "| "
							+ Funcoes.copy(rs.getString("CODCC"), 0, 19)
							+ " | "
							+ sSiglacc
							+ " | "
							+ Funcoes.copy(sDesccc, 0, 50)
							+ " | "
							+ (rs.getString(5) != null ? Funcoes
									.strDecimalToStrCurrency(12, 2, rs
											.getString(5)) : Funcoes.replicate(
									" ", 12))
							+ " | "
							+ (rs.getString(6) != null ? Funcoes
									.strDecimalToStrCurrency(12, 2, rs
											.getString(6)) : Funcoes.replicate(
									" ", 12))
							+ " | "
							+ (rs.getString(7) != null ? Funcoes
									.strDecimalToStrCurrency(12, 2, rs
											.getString(7)) : Funcoes.replicate(
									" ", 12)));
					imp.say(imp.pRow(), 136, "|");
					if (iNivelcc == 10) {
						bTotal = bTotal
								.add(new BigDecimal(
										rs.getString(7) != null ? rs
												.getString(7) : "0"));
					}
				}

				if (imp.pRow() == (linPag - 1)) {
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "+"
							+ Funcoes.replicate("-", 133) + "+");
					imp.eject();
					imp.incPags();
				}

			}

			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("-", 133) + "+");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow(), 0, "|");
			imp.say(imp.pRow(), 70, "TOTAL RECEITAS/DESPESAS");
			imp.say(imp.pRow(), 119, "| "
					+ Funcoes.strDecimalToStrCurrency(12, 2, "" + bTotal));
			imp.say(imp.pRow(), 135, "|");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("-", 133) + "+");

			imp.eject();

			imp.fechaGravacao();

			//      rs.close();
			//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
			//      dl.dispose();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Erro na consulta de relat�rio financeiro por centro de custo!\n"
							+ err.getMessage(),true,con,err);
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}
}