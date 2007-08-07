/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRMediaItem.java <BR>
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
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FRelatorio;

public class FRMediaItem extends FRelatorio {
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtMesfim = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 2, 0);
	private JTextFieldPad txtAnofim = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 4, 0);
	private JTextFieldPad txtNumMes = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 2, 0);
	private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING, 14, 0);
	private JTextFieldPad txtDescGrup = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING, 6, 0);
	private JTextFieldPad txtDescMarca = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldPad txtSiglaMarca = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
	private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldFK txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
	private JCheckBoxPad cbVendaCanc = new JCheckBoxPad("Mostrar Canceladas", "S", "N");
	private JRadioGroup rgOrdem = null;	
	private JRadioGroup rgFaturados = null;
	private JRadioGroup rgFinanceiro = null;
	private Vector vLabs = new Vector();
	private Vector vVals = new Vector(); 
	private Vector vLabsFat = new Vector();
	private Vector vValsFat = new Vector();
	private Vector vLabsFin = new Vector();
	private Vector vValsFin = new Vector();
	private ListaCampos lcGrup = new ListaCampos(this);
	private ListaCampos lcMarca = new ListaCampos(this);
	private ListaCampos lcVend = new ListaCampos(this);

	public FRMediaItem() {
		setTitulo("Media de vendas por item");
		setAtribos(80, 80, 335, 540);

		txtDescGrup.setAtivo(false);
		txtDescMarca.setAtivo(false);

		lcGrup.add(new GuardaCampo(txtCodGrup, "CodGrup", "C�d.grupo",ListaCampos.DB_PK, false));
		lcGrup.add(new GuardaCampo(txtDescGrup, "DescGrup","Descri��o do grupo", ListaCampos.DB_SI, false));
		txtCodGrup.setTabelaExterna(lcGrup);
		txtCodGrup.setNomeCampo("CodGrup");
		txtCodGrup.setFK(true);
		lcGrup.setReadOnly(true);
		lcGrup.montaSql(false, "GRUPO", "EQ");

		lcMarca.add(new GuardaCampo(txtCodMarca, "CodMarca", "C�d.marca",ListaCampos.DB_PK, false));
		lcMarca.add(new GuardaCampo(txtDescMarca, "DescMarca","Descri��o da marca", ListaCampos.DB_SI, false));
		lcMarca.add(new GuardaCampo(txtSiglaMarca, "SiglaMarca", "Sigla",ListaCampos.DB_SI, false));

		lcVend.add(new GuardaCampo(txtCodVend, "CodVend", "C�d.comiss.",ListaCampos.DB_PK, false));
		lcVend.add(new GuardaCampo(txtDescVend, "NomeVend","Nome do comissionado", ListaCampos.DB_SI, false));
		txtCodVend.setTabelaExterna(lcVend);
		txtCodVend.setNomeCampo("CodVend");
		txtCodVend.setFK(true);
		lcVend.setReadOnly(true);
		lcVend.montaSql(false, "VENDEDOR", "VD");

		txtCodMarca.setTabelaExterna(lcMarca);
		txtCodMarca.setNomeCampo("CodMarca");
		txtCodMarca.setFK(true);
		lcMarca.setReadOnly(true);
		lcMarca.montaSql(false, "MARCA", "EQ");

		vLabs.addElement("C�digo");
		vLabs.addElement("Descri��o");
		vVals.addElement("C");
		vVals.addElement("D");
		rgOrdem = new JRadioGroup(1, 2, vLabs, vVals);
		rgOrdem.setVlrString("D");
		
		vLabsFat.addElement("Faturado");
		vLabsFat.addElement("N�o Faturado");
		vLabsFat.addElement("Ambos");
		vValsFat.addElement("S");
		vValsFat.addElement("N");
		vValsFat.addElement("A");
		rgFaturados = new JRadioGroup(3, 1, vLabsFat, vValsFat);
		rgFaturados.setVlrString("S");
		
		vLabsFin.addElement("Financeiro");
		vLabsFin.addElement("N�o Finaceiro");
		vLabsFin.addElement("Ambos");
		vValsFin.addElement("S");
		vValsFin.addElement("N");
		vValsFin.addElement("A");
		rgFinanceiro = new JRadioGroup(3, 1, vLabsFin, vValsFin);
		rgFinanceiro.setVlrString("S");
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbLinha2 = new JLabelPad();
		lbLinha2.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbLinha3 = new JLabelPad();
		lbLinha3.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbLinha4 = new JLabelPad();
		lbLinha4.setBorder(BorderFactory.createEtchedBorder());

		adic(new JLabelPad("M�dia de vendas anteriores a:"), 7, 5, 240, 20);
		adic(lbLinha, 180, 15, 100, 2);
		adic(new JLabelPad("Mes:"), 7, 30, 40, 20);
		adic(txtMesfim, 40, 30, 47, 20);
		adic(new JLabelPad("Ano:"), 100, 30, 37, 20);
		adic(txtAnofim, 130, 30, 70, 20);
		adic(new JLabelPad("Calcular a m�dia dos �ltmos meses:"), 7, 60, 240,20);
		adic(lbLinha2, 218, 70, 62, 2);
		adic(new JLabelPad("N� de meses (m�x. 12):"), 7, 85, 200, 20);
		adic(txtNumMes, 150, 85, 40, 20);
		adic(lbLinha3, 7, 117, 273, 2);
		adic(new JLabelPad("C�d.grupo"), 7, 125, 240, 20);
		adic(txtCodGrup, 7, 145, 90, 20);
		adic(new JLabelPad("Descri��o do grupo"), 100, 125, 240, 20);
		adic(txtDescGrup, 100, 145, 180, 20);
		adic(new JLabelPad("C�d.marca"), 7, 165, 240, 20);
		adic(txtCodMarca, 7, 185, 90, 20);
		adic(new JLabelPad("Descri��o da Marca"), 100, 165, 240, 20);
		adic(txtDescMarca, 100, 185, 180, 20);
		adic(new JLabelPad("C�d.comiss."), 7, 205, 200, 20);
		adic(txtCodVend, 7, 225, 70, 20);
		adic(new JLabelPad("Nome do comissionado"), 80, 205, 200, 20);
		adic(txtDescVend, 80, 225, 200, 20);
		adic(rgFaturados, 7, 250, 120, 75);
		adic(rgFinanceiro, 158, 250, 120, 75);		
		adic(lbLinha4, 7, 335, 273, 2);
		adic(lbOrdem, 7, 345, 80, 15);
		adic(rgOrdem, 7, 370, 273, 30);
		adic(cbVendaCanc, 7, 410, 200, 20);

	}

	private boolean comRef() {
		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("UsaRefProd").trim().equals("S"))
					bRetorno = true;
			}
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage(),true,con,err);
		}
		return bRetorno;
	}

	public void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("", con);
		int linPag = imp.verifLinPag() - 1;

		String sWhere = " WHERE ";
	  	String sWhere1 = "";
	  	String sWhere2 = "";
	  	String sWhere3 = "";
		String sFiltroVend = "";
		String sCab = "";
		String sCab1 = "";
		String sOrder = "";
		String sSubSel = "";
		String sCodProd = "";
		String sSubCab = "";
		String sSubWhere = "";
		double dQtd[] = new double[12];
		int iPos = 68;
		int iAno = txtAnofim.getVlrInteger().intValue();
		int iMes = txtMesfim.getVlrInteger().intValue();
		int iNumMes = txtNumMes.getVlrInteger().intValue();
		int iNumItens = 0;
		if (iAno < 1) {
			Funcoes.mensagemInforma(this, "Ano inv�lido!");
			return;
		} else if ((iMes < 1) || (iMes > 12)) {
			Funcoes.mensagemInforma(this, "M�s inv�lido!");
			return;
		} else if ((iNumMes < 1) || (iNumMes > 12)) {
			Funcoes.mensagemInforma(this, "Num de M�dias Inv�lido!");
			return;
		}
		GregorianCalendar cFim = new GregorianCalendar(iAno, iMes - 1, 1);
		int iMesIni = cFim.get(Calendar.MONTH) - iNumMes;
		int iAnoIni = cFim.get(Calendar.YEAR);
		GregorianCalendar cIni = new GregorianCalendar(iAnoIni, iMesIni, 1);

		if (comRef()) {
			sCodProd = "REFPROD";
		} else {
			sCodProd = "CODPROD";
		}
		if (rgOrdem.getVlrString().equals("C")) {
			sOrder = "P." + sCodProd;
		} else {
			sOrder = "P.DESCPROD";
		}
		
		if(rgFaturados.getVlrString().equals("S")){
			sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
			sCab1 += " - SO FATURADO";
		}
		else if(rgFaturados.getVlrString().equals("N")){
			sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
			sCab1 += " - NAO FATURADO";
		}
		else if(rgFaturados.getVlrString().equals("A")){
			sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";
		}	
		if(rgFinanceiro.getVlrString().equals("S")){
			sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
			sCab1 += " - SO FINANCEIRO";
		}
		else if(rgFinanceiro.getVlrString().equals("N")){
			sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
			sCab1 += " - NAO FINANCEIRO";
		}
		else if(rgFinanceiro.getVlrString().equals("A")){
			sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";
		}

		if(cbVendaCanc.getVlrString().equals("N"))
			sWhere3 = " AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' ";
		
		int iSoma = 0;
		String sOr = "";
		for (int i = 0; i < iNumMes; i++) {
			cIni.set(Calendar.MONTH, cIni.get(Calendar.MONTH) + 1);
			if (txtCodVend.getText().trim().length() > 0) {
				sFiltroVend = " V" + (i + 2) + ".CODVEND = "+ txtCodVend.getText().trim();
				String sTmp = "REPR.: " + txtCodVend.getVlrString() + " - "+ txtDescVend.getText().trim();
				sFiltroVend += " AND V" + (i + 2) + ".CODEMPVD="+ Aplicativo.iCodEmp + " AND V" + (i + 2)+ ".CODFILIALVD=" + lcVend.getCodFilial() + " AND ";
				sCab = sTmp ;
			}
			sSubSel += ",(SELECT SUM(IT.QTDITVENDA) FROM VDITVENDA IT, VDVENDA V,\n"
					+ " EQTIPOMOV TM WHERE V.FLAG IN "
					+ AplicativoPD.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)
					+ " AND IT.CODVENDA=V.CODVENDA AND IT.TIPOVENDA=V.TIPOVENDA AND IT.CODPROD=P.CODPROD\n"
					+ " AND TM.CODTIPOMOV=V.CODTIPOMOV"
	                + sWhere1 + sWhere2 + sWhere3		  
					+ " AND TM.CODEMP=V.CODEMPTM"
					+ " AND TM.CODFILIAL=V.CODFILIALTM"
					+ " AND TM.TIPOMOV IN ('VD','PV','VT','SE')"
					+ " AND V.DTEMITVENDA BETWEEN '"
					+ Funcoes.dateToStrDB(cIni.getTime())
					+ "' AND '"
					+ Funcoes.dateToStrDB(Funcoes.periodoMes(cIni.get(Calendar.MONTH) + 1, cIni.get(Calendar.YEAR))[1]) + "')";
			sSubWhere += sOr
					+ "EXISTS (SELECT IT"
					+ (i + 2)
					+ ".CODPROD FROM VDITVENDA IT"
					+ (i + 2)
					+ ",\n"
					+ "VDVENDA V"
					+ (i + 2)
					+ " WHERE "
					+ sFiltroVend
					+ "V"
					+ (i + 2)
					+ ".FLAG IN "
					+ AplicativoPD.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)
					+ " AND IT"
					+ (i + 2)
					+ ".CODPROD = P.CODPROD\n"
					+ " AND V"
					+ (i + 2)
					+ ".CODVENDA = IT"
					+ (i + 2)
					+ ".CODVENDA\n"
					+ " AND V"
					+ (i + 2)
					+ ".TIPOVENDA = IT"
					+ (i + 2)
					+ ".TIPOVENDA\n"
					+ " AND V"
					+ (i + 2)
					+ ".DTEMITVENDA BETWEEN '"
					+ Funcoes.dateToStrDB(cIni.getTime())
					+ "' AND '"
					+ Funcoes.dateToStrDB(Funcoes.periodoMes(cIni.get(Calendar.MONTH) + 1, cIni.get(Calendar.YEAR))[1]) + "')";
			sSubCab += " | "
					+ Funcoes.strZero("" + (cIni.get(Calendar.MONTH) + 1), 2);
			sSubCab += "/" + (cIni.get(Calendar.YEAR));
			sOr = " OR ";
			iSoma++;
		}
		sSubCab += " | Media    ";
		if (txtCodGrup.getText().trim().length() > 0) {
			sWhere += "P.CODGRUP LIKE '" + txtCodGrup.getText().trim()+ "%' AND ";
			sCab = "GRUPO: " + txtDescGrup.getText().trim();
		}
		if (txtCodMarca.getText().trim().length() > 0) {
			sWhere += "P.CODMARCA = '" + txtCodMarca.getText().trim()+ "' AND ";
			sCab = "MARCA: " + txtDescMarca.getText().trim();
		}

		String sSQL = "SELECT P." + sCodProd
				+ ",P.DESCPROD,P.SLDPROD,P.DTULTCPPROD,P.QTDULTCPPROD\n"
				+ sSubSel + " FROM EQPRODUTO P" + sWhere + " (" + sSubWhere
				+ ") ORDER BY " + sOrder;
		PreparedStatement ps = null;
		ResultSet rs = null;
		System.out.println(sSQL);
		try {
			ps = con.prepareStatement(sSQL);
			rs = ps.executeQuery();
			imp.limpaPags();
			
			imp.setTitulo("Relat�rio de media de vendas por item");
			imp.addSubTitulo("RELATORIO DE MEDIAS DE VENDAS POR ITEM");
			if (sCab.length() > 0){
				imp.addSubTitulo(sCab);
				imp.addSubTitulo(sCab1);
			}
			
			while (rs.next()) {
				if (imp.pRow() == 0) {
					imp.montaCab();					
					imp.impCab(136, true);

					imp.say(imp.pRow() + 0, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|"+ Funcoes.replicate("-", 133) + "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "| Cod. Prod     ");
					imp.say(imp.pRow() + 0, 16,"  Desc. Produto                            ");
					imp.say(imp.pRow() + 0, 59, "| Estoque  ");
					imp.say(imp.pRow() + 0, 70, "| Dt.Ult.Cp. ");
					imp.say(imp.pRow() + 0, 83, "| Q.Ult.Cp ");
					imp.say(imp.pRow() + 0, 135, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|  ");
					imp.say(imp.pRow() + 0, 4, sSubCab);
					imp.say(imp.pRow() + 0, 135, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|"+ Funcoes.replicate("-", 133) + "|");
				}
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "| "+ Funcoes.copy(rs.getString(1), 0, 13) + " ");
				imp.say(imp.pRow() + 0, 16, "  "+ Funcoes.copy(rs.getString("DescProd"), 0, 40) + " ");
				imp.say(imp.pRow() + 0, 59, "| "+ Funcoes.strDecimalToStrCurrency(8, 0, rs.getString("SldProd")) + " ");
				if (rs.getDate("DTULTCPPROD") != null) {
					imp.say(imp.pRow() + 0, 70, "| "+ Funcoes.sqlDateToStrDate(rs.getDate("DTULTCPPROD")) + " ");
				} else {
					imp.say(imp.pRow() + 0, 70, "|            ");
				}
				imp.say(imp.pRow() + 0, 83, "| "+ Funcoes.strDecimalToStrCurrency(8, 0, ""+ rs.getDouble("QTDULTCPPROD")) + " ");
				imp.say(imp.pRow() + 0, 135, "|");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "|  ");
				double dSomaItem = 0;
				double dMediaItem = 0;
				iPos = 4;
				for (int i = 0; i < iSoma; i++) {
					imp.say(imp.pRow() + 0, iPos, " | "+ Funcoes.strDecimalToStrCurrency(7, 0, rs.getString(6 + i) != null ? rs.getString(6 + i) : " "));
					dQtd[i] += rs.getDouble(6 + i);
					dSomaItem += rs.getDouble(6 + i);
					iPos += 10;
				}
				dMediaItem = dSomaItem / iNumMes;
				imp.say(imp.pRow() + 0, iPos, " | "+ Funcoes.strDecimalToStrCurrency(7, 0, ""+ dMediaItem));
				imp.say(imp.pRow() + 0, 135, "|");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "|" + Funcoes.replicate("-", 133)+ "|");
				if (imp.pRow() >= (linPag - 1)) {
					imp.incPags();
					imp.eject();
				}
				iNumItens++;
			}
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("=", 133) + "+");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "| T:");
			
			iPos = 4;
			for (int i = 0; i < iSoma; i++) {
				BigDecimal bVal = new BigDecimal(dQtd[i]);
				bVal = bVal.setScale(1);
				imp.say(imp.pRow() + 0, iPos, "| tst"+ Funcoes.strDecimalToStrCurrency(7, 0, "" + bVal));
				iPos += 10;
			}
			
			imp.say(imp.pRow() + 0, 135, "|");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("=", 133) + "+");

			imp.eject();

			imp.fechaGravacao();

			if (!con.getAutoCommit())
				con.commit();
			
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro consulta tabela vendas!"+ err.getMessage(),true,con,err);
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcGrup.setConexao(con);
		lcMarca.setConexao(con);
		lcVend.setConexao(con);
	}
}