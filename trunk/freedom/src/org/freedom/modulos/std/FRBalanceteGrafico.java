/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRBalanceteGrafico.java <BR>
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.relatorios.BalanceteBarras;
import org.freedom.relatorios.BalancetePizza;
import org.freedom.relatorios.DLExibePizza;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRBalanceteGrafico extends FRelatorio {
  private JTextFieldPad txtCodPlan = new JTextFieldPad();   
  private JTextFieldPad txtDataini = new JTextFieldPad(); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(); 
  private JTextFieldFK txtDescPlan = new JTextFieldFK();  
  private ListaCampos lcPlan = new ListaCampos(this);
  private Connection con = null;
  private FPrinterJob dl = null;
  private JRadioGroup rgGrafico = null;
  private Vector vLabs = new Vector(2);
  private Vector vVals = new Vector(2);
  private JTextFieldPad txtCodCC = new JTextFieldPad();   
  private JTextFieldFK  txtSiglaCC = new JTextFieldFK();
  private JTextFieldFK txtDescCC = new JTextFieldFK(); 
  private ListaCampos lcCC = new ListaCampos(this);
  
  public FRBalanceteGrafico() {
    setTitulo("Balancete Gr�fico");
    setAtribos(80,80,330,345);

    txtDataini.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtDatafim.setTipo(JTextFieldPad.TP_DATE,10,0);
    
    txtCodPlan.setRequerido(true);
    
    txtCodPlan.setTipo(JTextFieldPad.TP_STRING,10,0);
    txtDescPlan.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcPlan.add(new GuardaCampo( txtCodPlan, 7, 100, 80, 20, "CodPlan", "C�d.Plan", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlan");
    lcPlan.add(new GuardaCampo( txtDescPlan, 90, 100, 207, 20, "DescPlan", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlan");
    lcPlan.setWhereAdic("NIVELPLAN<6 AND TIPOPLAN IN ('R','D')");
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    txtCodPlan.setFK(true);
    txtCodPlan.setNomeCampo("CodPlan");

	txtCodCC.setTipo(JTextFieldPad.TP_STRING,19,0);
	txtDescCC.setTipo(JTextFieldPad.TP_STRING,50,0);
	txtSiglaCC.setTipo(JTextFieldPad.TP_STRING,10,0);    
	lcCC.add(new GuardaCampo( txtCodCC, 7, 100, 80, 20, "CodCC", "C�d.CC", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodConta");
	lcCC.add(new GuardaCampo( txtSiglaCC, 90, 100, 207, 20, "SiglaCC", "Sigla", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescProdx");
	lcCC.add(new GuardaCampo( txtDescCC, 90, 100, 207, 20, "DescCC", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescConta");
	lcCC.setReadOnly(true);
	lcCC.montaSql(false, "CC", "FN");
	txtCodCC.setTabelaExterna(lcCC);
	txtCodCC.setFK(true);
	txtCodCC.setNomeCampo("CodCC");
	txtSiglaCC.setListaCampos(lcCC);

	adic(new JLabel("Periodo:"),7,5,120,20);
	adic(new JLabel("De:"),7,25,30,20);
	adic(txtDataini,40,25,117,20);
	adic(new JLabel("At�:"),160,25,22,20);
	adic(txtDatafim,185,25,120,20);
	adic(new JLabel("N�mero e descri��o do planejamento"),7,50,250,20);
	adic(txtCodPlan,7,70,80,20);
	adic(txtDescPlan,90,70,200,20);
	adic(new JLabel("C�digo e descri��o do centro de custo"),7,90,250,20);
	adic(txtCodCC,7,110,80,20);
	adic(txtDescCC,90,110,200,20);	
	adic(new JLabel("Tipo de gr�fico:"),7,130,180,20);
	adic(new JLabel(Icone.novo("graficoPizza.gif")),7,160,30,30);
	adic(new JLabel(Icone.novo("graficoBarra.gif")),7,200,30,30);
	adic(new JLabel(Icone.novo("graficoGiratorio.gif")),7,240,30,30);
    
	vLabs.addElement("Pizza");
	vLabs.addElement("Barras 3D");
	vLabs.addElement("Pizza Girat�ria");
	vVals.addElement("P");
	vVals.addElement("B");
	vVals.addElement("G");
	rgGrafico = new JRadioGroup(3,1,vLabs,vVals);
	rgGrafico.setVlrString("P");    
	rgGrafico.setBorder(BorderFactory.createEmptyBorder());
	adic(rgGrafico,42,163,250,122);

	Calendar cPeriodo = Calendar.getInstance();
    txtDatafim.setVlrDate(cPeriodo.getTime());
	cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
	txtDataini.setVlrDate(cPeriodo.getTime());
  }
  public void setConexao(Connection cn) {
    con = cn;
    lcPlan.setConexao(cn);
	lcCC.setConexao(cn);
	lcCC.setWhereAdic("NIVELCC=10 AND ANOCC="+buscaAnoBaseCC());
  }
  private int buscaAnoBaseCC() {
	int iRet = 0;
	String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
	try {
		PreparedStatement ps = con.prepareStatement(sSQL);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			iRet = rs.getInt("ANOCENTROCUSTO");
		rs.close();
		ps.close();
	}
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o centro de custo.\n"+err.getMessage());
	}
	return iRet;
  }
  
  private ResultSet buscaValores() {

	String sCodPlan = txtCodPlan.getVlrString().trim();
    String sCodCC = txtCodCC.getVlrString().trim();
	int iNivelPosterior = 0;
	ResultSet rs = null;
	int iFilial = ListaCampos.getMasterFilial("FNPLANEJAMENTO");
	
	if (!sCodPlan.equals("")) { 

        String sSQL = "SELECT MIN(NIVELPLAN) FROM FNPLANEJAMENTO WHERE CODPLAN LIKE ? AND NOT CODPLAN=? "+
                      "AND CODEMP=? AND CODFILIAL=?";
		
	    PreparedStatement ps2 = null;
	    ResultSet rs2 = null;
	    try {        
	      ps2 = con.prepareStatement(sSQL);
		  ps2.setString(1,sCodPlan+"%");
		  ps2.setString(2,sCodPlan);
	      ps2.setInt(3,Aplicativo.iCodEmp);
		  ps2.setInt(4,iFilial);
  	      rs2 = ps2.executeQuery();
	      if (rs2.next()) {
	      	iNivelPosterior = rs2.getInt(1);
	      }
		  rs2.close();
		  ps2.close();			  
	    } 
	    catch (SQLException err) {
		  Funcoes.mensagemErro(this,"Erro ao buscar nivel de planejamento!\n"+err.getMessage());
		  err.printStackTrace();
	    }
		           
        sSQL = "SELECT P.CODPLAN,P.DESCPLAN,P.NIVELPLAN,"+
		  "(SELECT ABS(SUM(SL.VLRSUBLANCA)) FROM FNSUBLANCA SL,FNLANCA L "+
		  "WHERE SUBSTR(SL.CODPLAN,1,STRLEN(RTRIM(P.CODPLAN)))=RTRIM(P.CODPLAN) AND "+
		  "SL.CODLANCA=L.CODLANCA AND SL.DATASUBLANCA BETWEEN ? AND ? AND "+
		  "SL.CODEMP=L.CODEMP AND SL.CODFILIAL=L.CODFILIAL AND "+
		  "SUBSTR(SL.CODPLAN,1,STRLEN(RTRIM('"+sCodPlan+"')))=RTRIM('"+sCodPlan+"')" +

		  (sCodCC.trim().equals("")?"":" AND SL.CODCC="+
		  "(SELECT CC.CODCC FROM FNCC CC WHERE"+
		  " SL.CODEMPCC=CC.CODEMP AND SL.CODFILIALCC=CC.CODFILIAL" +
		  " AND CC.CODFILIAL="+iFilial+" AND CC.CODCC='"+sCodCC+"')")+		  	

		  " ) "+
           
		  "FROM FNPLANEJAMENTO P WHERE P.TIPOPLAN IN ('R','D') and P.CODEMP=? AND P.CODFILIAL=? AND "+
		  
		  (iNivelPosterior==6?"":"P.NIVELPLAN<6 AND ")+
 
		  " P.NIVELPLAN="+iNivelPosterior+" AND "+
		  
		  "SUBSTR(P.CODPLAN,1,STRLEN(RTRIM('"+sCodPlan+"')))=RTRIM('"+sCodPlan+"') AND CODPLAN<>'"+sCodPlan+"' "+
		  "ORDER BY 4,P.CODPLAN,P.DESCPLAN,P.NIVELPLAN DESC";			  
	            
      System.out.println(sSQL); 
       
	  PreparedStatement ps = null;

	  try {

		ps = con.prepareStatement(sSQL);

		ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
		ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
		ps.setInt(3,Aplicativo.iCodEmp);
		ps.setInt(4,iFilial);

		     		
		rs = ps.executeQuery();

	  }
	  catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar valores financeiros!\n"+err.getMessage());
		err.printStackTrace();
	  }

    }
  return rs;
  }
  
  public void imprimir(boolean bVisualizar) {
	if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
	  Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
	  return;
	}
	else if (txtCodPlan.getVlrString().trim().equals("")) {
	  Funcoes.mensagemInforma(this,"C�digo do planejamento em branco!");
	  return;	
	}
	
	
	try {
	  
	  if (rgGrafico.getVlrString().equals("P")) {
		BalancetePizza evBalanc = new BalancetePizza();
		evBalanc.setConexao(con);
		evBalanc.setConsulta(buscaValores());
		evBalanc.setTitulo("BALANCETE GR�FICO",txtDescPlan.getVlrString().toUpperCase()+ (txtDescCC.getVlrString().trim().equals("")?"":
		" - CC: "+txtDescCC.getVlrString().trim().toUpperCase()));	  	
		dl = new FPrinterJob(evBalanc,this);	
		dl.setVisible(true);
	  }	        
	  else if (rgGrafico.getVlrString().equals("B")) { 
		BalanceteBarras evBalanc = new BalanceteBarras();
		evBalanc.setConexao(con);
		evBalanc.setConsulta(buscaValores());
		evBalanc.setTitulo("BALANCETE GR�FICO",txtDescPlan.getVlrString());	  	
		dl = new FPrinterJob(evBalanc,this);
		dl.setVisible(true);
	  }
	  else if (rgGrafico.getVlrString().equals("G")) { 
		BalancetePizza evBalanc = new BalancetePizza();
		evBalanc.setConexao(con);
		evBalanc.setConsulta(buscaValores());
		evBalanc.setTitulo("GR�FICO FINANCEIRO POR C.CUSTO","C.C: "+txtDescCC.getVlrString().trim().toUpperCase()+" - PLANEJAMENTO: "+txtDescPlan.getVlrString().toUpperCase());
		evBalanc.setGirar(true);
		evBalanc.montaG();
		int alt = getDesktopPane().getSize().width;
		int larg = getDesktopPane().getSize().height;
		DLExibePizza ex = new DLExibePizza(evBalanc.getGrafico(),alt,larg,"C.C: "+txtDescCC.getVlrString().trim().toUpperCase()+" - PLANEJAMENTO: "+txtDescPlan.getVlrString().toUpperCase(),evBalanc.getVlrLabel());
		Aplicativo.telaPrincipal.criatela("Exibe Gr�fico",ex);		  
	  }	  	 	  	  	  
	} 
	catch (Exception err) {
	  Funcoes.mensagemInforma(this,"N�o foi poss�vel carregar relat�rio!\n"+err.getMessage());
	  err.printStackTrace();
	}

  }  
}
