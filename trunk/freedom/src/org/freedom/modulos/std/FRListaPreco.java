/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRListaProd.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRListaPreco extends FRelatorio {
	private Painel pinOpt = new Painel(595,100);
	private Painel pinPlan = new Painel(595,450);
	private JPanel pnOpt = new JPanel(new GridLayout(1,1));
	private JPanel pnPlan = new JPanel(new GridLayout(1,1));
	private JTextFieldPad txtCodGrup = new JTextFieldPad();
	private JTextFieldPad txtCodMarca = new JTextFieldPad();
	private JTextFieldPad txtCodClasCli = new JTextFieldPad ();
	private JTextFieldPad txtCodTabPreco = new JTextFieldPad ();
	private JTextFieldPad txtCodPlanoPag1 = new JTextFieldPad();
	private JTextFieldPad txtCodPlanoPag2 = new JTextFieldPad();
	private JTextFieldPad txtCodPlanoPag3 = new JTextFieldPad();
	private JTextFieldPad txtCodPlanoPag4 = new JTextFieldPad();
	private JTextFieldPad txtCodPlanoPag5 = new JTextFieldPad();
	private JTextFieldPad txtCodPlanoPag6 = new JTextFieldPad();
	private JTextFieldPad txtCodPlanoPag7 = new JTextFieldPad();
	private JTextFieldPad txtDescGrup = new JTextFieldFK();
	private JTextFieldPad txtDescClasCli = new JTextFieldFK ();
	private JTextFieldPad txtDescTabPreco = new JTextFieldFK ();
	private JTextFieldPad txtDescMarca = new JTextFieldFK();
	private JTextFieldPad txtSiglaMarca = new JTextFieldFK();
	private JTextFieldPad txtDescPlanoPag1 = new JTextFieldFK();
	private JTextFieldPad txtDescPlanoPag2 = new JTextFieldFK();
	private JTextFieldPad txtDescPlanoPag3 = new JTextFieldFK();
	private JTextFieldPad txtDescPlanoPag4 = new JTextFieldFK();
	private JTextFieldPad txtDescPlanoPag5 = new JTextFieldFK();
	private JTextFieldPad txtDescPlanoPag6 = new JTextFieldFK();
	private JTextFieldPad txtDescPlanoPag7 = new JTextFieldFK();
	private JLabel lbCodGrup = new JLabel("C�digo  e desc.do grupo");
	private JLabel lbCodMarca = new JLabel("C�digo  e desc.da marca");
	private JLabel lbCodClasCli = new JLabel( "C�digo  e desc.Class.Cliente");
	private JLabel lbCodTabPreco = new JLabel("C�digo  e desc.Tab.Pre�o");
	private JLabel lbOrdem = new JLabel("Ordem");
	private JLabel lbCodPlanoPag1 = new JLabel("C�digo e descri��o do plano de pgto. (1)");
	private JLabel lbCodPlanoPag2 = new JLabel("C�digo e descri��o do plano de pgto. (2)");
	private JLabel lbCodPlanoPag3 = new JLabel("C�digo e descri��o do plano de pgto. (3)");
	private JLabel lbCodPlanoPag4 = new JLabel("C�digo e descri��o do plano de pgto. (4)");
	private JLabel lbCodPlanoPag5 = new JLabel("C�digo e descri��o do plano de pgto. (5)");
	private JLabel lbCodPlanoPag6 = new JLabel("C�digo e descri��o do plano de pgto. (6)");
	private JLabel lbCodPlanoPag7 = new JLabel("C�digo e descri��o do plano de pgto. (7)");
	private JRadioGroup rgOrdem = null;
	private Vector vLabs = new Vector(2);
	private Vector vVals = new Vector(2);
	private ListaCampos lcGrup = new ListaCampos(this);
	private ListaCampos lcMarca = new ListaCampos(this);
	private ListaCampos lcClassCli = new ListaCampos(this);
	private ListaCampos lcTabPreco = new ListaCampos(this);
	private ListaCampos lcPlanoPag1 = new ListaCampos(this);
	private ListaCampos lcPlanoPag2 = new ListaCampos(this);
	private ListaCampos lcPlanoPag3 = new ListaCampos(this);
	private ListaCampos lcPlanoPag4 = new ListaCampos(this);
	private ListaCampos lcPlanoPag5 = new ListaCampos(this);
	private ListaCampos lcPlanoPag6 = new ListaCampos(this);
	private ListaCampos lcPlanoPag7 = new ListaCampos(this);
        private JCheckBoxPad cbAgrupar = new JCheckBoxPad("Agrupar","S","N");
        
	private Connection con = null;
	public FRListaPreco() {
		setTitulo("Lista de Pre�os");
		setAtribos(50,50,620,385);
		vLabs.addElement("C�digo");
		vLabs.addElement("Descri��o");
		vVals.addElement("C");
		vVals.addElement("D");
		rgOrdem = new JRadioGroup(2,1,vLabs,vVals);
		rgOrdem.setVlrString("D");

		txtCodGrup.setTipo(JTextFieldPad.TP_STRING,14,0);
		txtDescGrup.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcGrup.add(new GuardaCampo( txtCodGrup, 7, 100, 20, 20, "CodGrup", "C�digo", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodGrup");
		lcGrup.add(new GuardaCampo( txtDescGrup, 90, 100, 20, 20, "DescGrup", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescGrup");
		lcGrup.montaSql(false, "GRUPO", "EQ");
		lcGrup.setReadOnly(true);
		txtCodGrup.setTabelaExterna(lcGrup);
		txtCodGrup.setFK(true);
		txtCodGrup.setNomeCampo("CodGrup");

		txtCodMarca.setTipo(JTextFieldPad.TP_STRING,6,0);
		txtDescMarca.setTipo(JTextFieldPad.TP_STRING,40,0);
		txtSiglaMarca.setTipo(JTextFieldPad.TP_STRING,20,0);
		lcMarca.add(new GuardaCampo( txtCodMarca, 7, 100, 80, 20, "CodMarca", "C�digo", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodMarca");
		lcMarca.add(new GuardaCampo( txtDescMarca, 90, 100, 207, 20, "DescMarca", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescMarca");
		lcMarca.add(new GuardaCampo( txtSiglaMarca, 90, 100, 207, 20, "SiglaMarca", "Sigla", false, false, null, JTextFieldPad.TP_STRING,false),"txtSiglaMarca");
		txtCodMarca.setTabelaExterna(lcMarca);
		txtCodMarca.setNomeCampo("CodMarca");
		txtCodMarca.setFK(true);
		lcMarca.setReadOnly(true);
		lcMarca.montaSql(false, "MARCA", "EQ");
		
  
		txtCodClasCli.setTipo(JTextFieldPad.TP_INTEGER,6,0);
		txtDescClasCli.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcClassCli.add(new GuardaCampo( txtCodClasCli, 7, 100, 80, 20, "CodClasCli", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodClasCli");
		lcClassCli.add(new GuardaCampo( txtDescClasCli, 90, 100, 207, 20, "DescClasCli", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescClasCli");
		txtCodClasCli.setTabelaExterna(lcClassCli);
		txtCodClasCli.setNomeCampo("CodClasCli");
		txtCodClasCli.setFK(true);
		lcClassCli.setReadOnly(true);
		lcClassCli.montaSql(false, "CLASCLI", "VD");
		
		
		
		txtCodTabPreco.setTipo(JTextFieldPad.TP_INTEGER,6,0);
		txtDescTabPreco.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcTabPreco.add(new GuardaCampo( txtCodTabPreco, 7, 100, 80, 20, "CodTab", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txCodTabPreco");
		lcTabPreco.add(new GuardaCampo( txtDescTabPreco, 90, 100, 207, 20, "DescTab", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTabPreco");
		txtCodTabPreco.setTabelaExterna(lcTabPreco);
		txtCodTabPreco.setNomeCampo("CodTab");
		txtCodTabPreco.setFK(true);
		lcTabPreco.setReadOnly(true);
		lcTabPreco.montaSql(false, "TABPRECO", "VD");
		

		txtCodPlanoPag1.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescPlanoPag1.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcPlanoPag1.add(new GuardaCampo( txtCodPlanoPag1, 7, 100, 80, 20, "CodPlanoPag", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPag1");
		lcPlanoPag1.add(new GuardaCampo( txtDescPlanoPag1, 90, 100, 207, 20, "DescPlanoPag", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPag1");
		lcPlanoPag1.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag1.setReadOnly(true);
		txtCodPlanoPag1.setTabelaExterna(lcPlanoPag1);
		txtCodPlanoPag1.setFK(true);
		txtCodPlanoPag1.setNomeCampo("CodPlanoPag");

		txtCodPlanoPag2.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescPlanoPag2.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcPlanoPag2.add(new GuardaCampo( txtCodPlanoPag2, 7, 100, 80, 20, "CodPlanoPag", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPag2");
		lcPlanoPag2.add(new GuardaCampo( txtDescPlanoPag2, 90, 100, 207, 20, "DescPlanoPag", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPag2");
		lcPlanoPag2.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag2.setReadOnly(true);
		txtCodPlanoPag2.setTabelaExterna(lcPlanoPag2);
		txtCodPlanoPag2.setFK(true);
		txtCodPlanoPag2.setNomeCampo("CodPlanoPag");

		txtCodPlanoPag3.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescPlanoPag3.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcPlanoPag3.add(new GuardaCampo( txtCodPlanoPag3, 7, 100, 80, 20, "CodPlanoPag", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPag3");
		lcPlanoPag3.add(new GuardaCampo( txtDescPlanoPag3, 90, 100, 207, 20, "DescPlanoPag", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPag3");
		lcPlanoPag3.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag3.setReadOnly(true);
		txtCodPlanoPag3.setTabelaExterna(lcPlanoPag3);
		txtCodPlanoPag3.setFK(true);
		txtCodPlanoPag3.setNomeCampo("CodPlanoPag");

		txtCodPlanoPag4.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescPlanoPag4.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcPlanoPag4.add(new GuardaCampo( txtCodPlanoPag4, 7, 100, 80, 20, "CodPlanoPag", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPag4");
		lcPlanoPag4.add(new GuardaCampo( txtDescPlanoPag4, 90, 100, 207, 20, "DescPlanoPag", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPag4");
		lcPlanoPag4.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag4.setReadOnly(true);
		txtCodPlanoPag4.setTabelaExterna(lcPlanoPag4);
		txtCodPlanoPag4.setFK(true);
		txtCodPlanoPag4.setNomeCampo("CodPlanoPag");

		txtCodPlanoPag5.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescPlanoPag5.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcPlanoPag5.add(new GuardaCampo( txtCodPlanoPag5, 7, 100, 80, 20, "CodPlanoPag", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPag5");
		lcPlanoPag5.add(new GuardaCampo( txtDescPlanoPag5, 90, 100, 207, 20, "DescPlanoPag", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPag5");
		lcPlanoPag5.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag5.setReadOnly(true);
		txtCodPlanoPag5.setTabelaExterna(lcPlanoPag5);
		txtCodPlanoPag5.setFK(true);
		txtCodPlanoPag5.setNomeCampo("CodPlanoPag");

		txtCodPlanoPag6.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescPlanoPag6.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcPlanoPag6.add(new GuardaCampo( txtCodPlanoPag6, 7, 100, 80, 20, "CodPlanoPag", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPag6");
		lcPlanoPag6.add(new GuardaCampo( txtDescPlanoPag6, 90, 100, 207, 20, "DescPlanoPag", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPag6");
		lcPlanoPag6.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag6.setReadOnly(true);
		txtCodPlanoPag6.setTabelaExterna(lcPlanoPag6);
		txtCodPlanoPag6.setFK(true);
		txtCodPlanoPag6.setNomeCampo("CodPlanoPag");

		txtCodPlanoPag7.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescPlanoPag7.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcPlanoPag7.add(new GuardaCampo( txtCodPlanoPag7, 7, 100, 80, 20, "CodPlanoPag", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPag7");
		lcPlanoPag7.add(new GuardaCampo( txtDescPlanoPag7, 90, 100, 207, 20, "DescPlanoPag", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPag7");
		lcPlanoPag7.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag7.setReadOnly(true);
		txtCodPlanoPag7.setTabelaExterna(lcPlanoPag7);
		txtCodPlanoPag7.setFK(true);
		txtCodPlanoPag7.setNomeCampo("CodPlanoPag");

		pnOpt.add(new JLabel("Op�oes do relat�rio"));
		adic(pnOpt,10,7,170,15);
		adic(pinOpt,5,15,595,100);
		pnPlan.add(new JLabel("Planos a serem impressos"));
		adic(pnPlan,10,125,200,15);
		adic(pinPlan,5,130,595,180);

		pinOpt.adic(lbCodGrup,7,10,200,20);
		pinOpt.adic(txtCodGrup,7,30,50,20);
		pinOpt.adic(txtDescGrup,59,30,165,20);
		
		pinOpt.adic(lbCodMarca,7,50,200,20);
		pinOpt.adic(txtCodMarca,7,70,50,20);
		pinOpt.adic(txtDescMarca,59,70,165,20);
		
		pinOpt.adic(lbCodClasCli,230,10,280,20);
		pinOpt.adic(txtCodClasCli,230,30,50,20);
		pinOpt.adic(txtDescClasCli,283,30,170,20);
		
		
		pinOpt.adic(lbCodTabPreco,230,50,280,20);
		pinOpt.adic(txtCodTabPreco,230,70,50,20);
		pinOpt.adic(txtDescTabPreco,283,70,170,20);
		
		
		
		pinOpt.adic(lbOrdem,460,10,100,20);
		pinOpt.adic(rgOrdem,460,30,120,60);
		pinPlan.adic(lbCodPlanoPag1,7,10,250,20);
		pinPlan.adic(txtCodPlanoPag1,7,30,80,20);
		pinPlan.adic(txtDescPlanoPag1,90,30,200,20);
		pinPlan.adic(lbCodPlanoPag2,300,10,250,20);
		pinPlan.adic(txtCodPlanoPag2,300,30,77,20);
		pinPlan.adic(txtDescPlanoPag2,380,30,200,20);
		pinPlan.adic(lbCodPlanoPag3,7,50,250,20);
		pinPlan.adic(txtCodPlanoPag3,7,70,80,20);
		pinPlan.adic(txtDescPlanoPag3,90,70,200,20);
		pinPlan.adic(lbCodPlanoPag4,300,50,250,20);
		pinPlan.adic(txtCodPlanoPag4,300,70,77,20);
		pinPlan.adic(txtDescPlanoPag4,380,70,200,20);
		pinPlan.adic(lbCodPlanoPag5,7,90,250,20);
		pinPlan.adic(txtCodPlanoPag5,7,110,80,20);
		pinPlan.adic(txtDescPlanoPag5,90,110,200,20);
		pinPlan.adic(lbCodPlanoPag6,300,90,250,20);
		pinPlan.adic(txtCodPlanoPag6,300,110,77,20);
		pinPlan.adic(txtDescPlanoPag6,380,110,200,20);
		pinPlan.adic(lbCodPlanoPag7,7,130,250,20);
		pinPlan.adic(txtCodPlanoPag7,7,150,77,20);
		pinPlan.adic(txtDescPlanoPag7,90,150,200,20);
        pinPlan.adic(cbAgrupar,300,150,200,20);
	}
	private boolean comRef() {
		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("UsaRefProd").trim().equals("S"))
					bRetorno = true;
			}
//			rs.close();
//			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
		}
		return bRetorno;
	}
	public void imprimir(boolean bVisualizar) {
//		boolean bFechaImp = false;
		String sOrdem = rgOrdem.getVlrString();
		String sCab = "";
		String sWhere = "";
		String sOrdenado = "";
		String sSubGrupo = "";
                String sCodRel = "";
                String sAgrupar = "";

		String sCodprod = "";
		String sCodProdPrint = Funcoes.replicate(" ",12)+"|";
		String sDescProd = Funcoes.replicate(" ",39)+"|";
		String sCodunid = "";
		String sCodgrup = "";
		String sTextoImp = "";
		String sPrecopag1 = Funcoes.replicate(" ",9)+"|";
		String sPrecopag2 = Funcoes.replicate(" ",9)+"|";
		String sPrecopag3 = Funcoes.replicate(" ",9)+"|";
		String sPrecopag4 = Funcoes.replicate(" ",9)+"|";
		String sPrecopag5 = Funcoes.replicate(" ",9)+"|";
		String sPrecopag6 = Funcoes.replicate(" ",9)+"|";
		String sPrecopag7 = Funcoes.replicate(" ",9)+"|";
		int iContaItem = 0;
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		imp.setTitulo("Lista de Pre�os");
                
                sAgrupar = cbAgrupar.getVlrString();
                //System.out.println("Agrupar: "+sAgrupar);
                
                if (comRef()) 
                  sCodRel = "REFPROD";
                else
                  sCodRel = "CODPROD";

		if (sOrdem.equals("C")) {
			sOrdem = (sAgrupar.equals("S")?"P.CODGRUP,":"")+"P."+sCodRel;
			sOrdenado = "ORDENADO POR "+(sCodRel.equals("CODPROD") ? "CODIGO" : "REFERENCIA");
		}
		else {
			sOrdem = (sAgrupar.equals("S")?"P.CODGRUP,":"")+"P.DESCPROD";
			sOrdenado = "ORDENADO POR DESCRICAO";
		}
		sOrdem = sOrdem + ",PP.CODPLANOPAG";
		sOrdenado = "|"+Funcoes.replicate(" ",68-(sOrdenado.length()/2))+sOrdenado;
		sOrdenado += Funcoes.replicate(" ",134-sOrdenado.length())+" |";
		if (txtCodGrup.getText().trim().length() > 0) {
			String sTmp = "GRUPO: "+txtDescGrup.getText().trim();
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
		}
		if (txtCodMarca.getText().trim().length() > 0) {
			sWhere += " AND P.CODMARCA = '"+txtCodMarca.getText().trim()+"'";
			String sTmp = "MARCA: "+txtDescMarca.getText().trim();
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
		}

		if (txtCodClasCli.getText().trim().length() > 0) {
			sWhere += " AND PP.CODCLASCLI = '"+txtCodClasCli.getText().trim()+"'";
			String sTmp = "CLASS.CLIENTE: "+txtDescClasCli.getText().trim();
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
		}
		
		if (txtCodTabPreco.getText().trim().length() > 0) {
			sWhere += " AND PP.CODTAB = '"+txtCodTabPreco.getText().trim()+"'";
			String sTmp = "TABELA: "+txtDescTabPreco.getText().trim();
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
		}
		
		
		String sSQL = "SELECT G.DESCGRUP,P.CODGRUP,P.CODPROD,P.REFPROD,"+
			"P.DESCPROD,P.CODUNID,"+
			"PP.CODPLANOPAG,PG.DESCPLANOPAG,PP.PRECOPROD "+
			"FROM EQPRODUTO P, VDPRECOPROD PP, FNPLANOPAG PG, EQGRUPO G "+ 
			"WHERE P.CODPROD = PP.CODPROD "+
			"AND G.CODGRUP = P.CODGRUP "+
			"AND P.CODGRUP LIKE ? AND P.ATIVOPROD='S' "+
			"AND PG.CODPLANOPAG = PP.CODPLANOPAG "+
			"AND PP.CODPLANOPAG IN (?,?,?,?,?,?,?)"+sWhere+
			" ORDER BY "+sOrdem; 
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setString(1,txtCodGrup.getVlrString().trim().length() < 14 ? txtCodGrup.getVlrString().trim()+"%":txtCodGrup.getVlrString().trim());
			ps.setInt(2,txtCodPlanoPag1.getVlrInteger().intValue());
			ps.setInt(3,txtCodPlanoPag2.getVlrInteger().intValue());
			ps.setInt(4,txtCodPlanoPag3.getVlrInteger().intValue());
			ps.setInt(5,txtCodPlanoPag4.getVlrInteger().intValue());
			ps.setInt(6,txtCodPlanoPag5.getVlrInteger().intValue());
			ps.setInt(7,txtCodPlanoPag6.getVlrInteger().intValue());
			ps.setInt(8,txtCodPlanoPag7.getVlrInteger().intValue());
			rs = ps.executeQuery();
			imp.limpaPags();
			while ( rs.next() ) {
            	if ((sCodprod.length() > 0) && (!sCodprod.equals(rs.getString("codprod")))) {
					sTextoImp = sPrecopag1+sPrecopag2+sPrecopag3+sPrecopag4+
					sPrecopag5+sPrecopag6+sPrecopag7+" "+
					Funcoes.copy(sCodunid,0,8)+"|";
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+Funcoes.copy(sCodProdPrint,0,12));
					imp.say(imp.pRow()+0,14,"|"+Funcoes.copy(sDescProd,0,39));
					imp.say(imp.pRow()+0,56,"|");
					imp.say(imp.pRow()+0,57,sTextoImp);
					if (!(imp.pRow()>=(linPag-1))) {
						imp.say(imp.pRow()+1,0,""+imp.comprimido());
   						imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
					}
					sTextoImp = "";
					sPrecopag1 = Funcoes.replicate(" ",9)+"|";
					sPrecopag2 = Funcoes.replicate(" ",9)+"|";
					sPrecopag3 = Funcoes.replicate(" ",9)+"|";
					sPrecopag4 = Funcoes.replicate(" ",9)+"|";
					sPrecopag5 = Funcoes.replicate(" ",9)+"|";
					sPrecopag6 = Funcoes.replicate(" ",9)+"|";
					sPrecopag7 = Funcoes.replicate(" ",9)+"|";
                }
				if (imp.pRow()>=(linPag-1)) {
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
					imp.incPags();
					imp.eject();
				}
                else if ((sAgrupar.equals("S")) && (sCodgrup.length() > 0) && (!sCodgrup.equals(rs.getString("Codgrup")))) {
			 		imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
					imp.incPags();
					imp.eject();
				}
				sCodgrup = rs.getString("codgrup");
				sCodProdPrint = rs.getString(sCodRel);
				sDescProd = rs.getString("descprod");
				sCodprod = rs.getString("codprod");

				if (imp.pRow()==0) {
					
					sSubGrupo = "SUBGRUPO: "+rs.getString("DescGrup").trim();
					sSubGrupo = "|"+Funcoes.replicate(" ",68-(sSubGrupo.length()/2))+sSubGrupo;
					sSubGrupo += Funcoes.replicate(" ",134-sSubGrupo.length())+" |";
					
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
					imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|");
					imp.say(imp.pRow()+0,62,"LISTA DE PRE�OS");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,sOrdenado);
					if (sCab.length() > 0) imp.say(imp.pRow()+0,0,sCab);
						imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
                   	if (sAgrupar.equals("S")) {
				   		imp.say(imp.pRow()+1,0,""+imp.comprimido());
				   		imp.say(imp.pRow()+0,0,sSubGrupo);
				   		imp.say(imp.pRow()+1,0,""+imp.comprimido());
				   		imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
                    }
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"| Codigo");
					imp.say(imp.pRow()+0,14,"| Desc.");
					imp.say(imp.pRow()+0,56,"| "+Funcoes.copy(txtDescPlanoPag1.getVlrString(),0,8)+
								"| "+Funcoes.copy(txtDescPlanoPag2.getVlrString(),0,8)+
								"| "+Funcoes.copy(txtDescPlanoPag3.getVlrString(),0,8)+
								"| "+Funcoes.copy(txtDescPlanoPag4.getVlrString(),0,8)+
								"| "+Funcoes.copy(txtDescPlanoPag5.getVlrString(),0,8)+
								"| "+Funcoes.copy(txtDescPlanoPag6.getVlrString(),0,8)+
								"| "+Funcoes.copy(txtDescPlanoPag7.getVlrString(),0,8)+
								"| Unidade");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
				}
				if (rs.getString("Codplanopag").equals(txtCodPlanoPag1.getVlrString()))
					sPrecopag1 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd"))+"|";
				else if (rs.getString("Codplanopag").equals(txtCodPlanoPag2.getVlrString()))
					sPrecopag2 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd"))+"|";
				if (rs.getString("Codplanopag").equals(txtCodPlanoPag3.getVlrString()))
					sPrecopag3 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd"))+"|";
				if (rs.getString("Codplanopag").equals(txtCodPlanoPag4.getVlrString()))
					sPrecopag4 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd"))+"|";
				if (rs.getString("Codplanopag").equals(txtCodPlanoPag5.getVlrString()))
					sPrecopag5 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd"))+"|";
				if (rs.getString("Codplanopag").equals(txtCodPlanoPag6.getVlrString()))
					sPrecopag6 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd"))+"|";
				if (rs.getString("Codplanopag").equals(txtCodPlanoPag7.getVlrString()))
					sPrecopag7 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd"))+"|";
				sCodunid = rs.getString("Codunid");
				sCodprod = rs.getString("Codprod");
				sCodgrup = rs.getString("Codgrup");
			    iContaItem++;
			}	
			System.out.println("Items: "+iContaItem);
            if (sCodprod.length() > 0) {
				sTextoImp = sPrecopag1+sPrecopag2+sPrecopag3+sPrecopag4+
				sPrecopag5+sPrecopag6+sPrecopag7+" "+
				Funcoes.copy(sCodunid,0,8)+"|";
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,0,"|"+Funcoes.copy(sCodProdPrint,0,12));
				imp.say(imp.pRow()+0,14,"|"+Funcoes.copy(sDescProd,0,39));
				imp.say(imp.pRow()+0,56,"|");
				imp.say(imp.pRow()+0,57,sTextoImp); 
            }
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");

			imp.eject();
				
			imp.fechaGravacao();
			
//			rs.close();
//			ps.close();
			if (!con.getAutoCommit())
				con.commit();
//			dl.dispose();
		}	
		catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de pre�os!"+err.getMessage());
        }
		if (bVisualizar)
			imp.preview(this);
		else
			imp.print();
	}
	public void setConexao(Connection cn) {
		con = cn;
		lcGrup.setConexao(cn);
		lcMarca.setConexao(cn);
		lcTabPreco.setConexao(cn);
		lcClassCli.setConexao(cn);
		lcPlanoPag1.setConexao(cn);
		lcPlanoPag2.setConexao(cn);
		lcPlanoPag3.setConexao(cn);
		lcPlanoPag4.setConexao(cn);
		lcPlanoPag5.setConexao(cn);
		lcPlanoPag6.setConexao(cn);
		lcPlanoPag7.setConexao(cn);
	}
}

