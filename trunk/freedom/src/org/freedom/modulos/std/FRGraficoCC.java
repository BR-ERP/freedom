/**
 * @version 01/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRGraficoCC.java <BR>
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
 * Relat�rio gr�fico financeiro por Centros de custo...
 * 
 */

package org.freedom.modulos.std;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import org.freedom.componentes.JLabelPad;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.graficos.BalanceteBarras;
import org.freedom.graficos.BalancetePizza;
import org.freedom.graficos.DLExibePizza;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRGraficoCC extends FRelatorio {
	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);   
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldFK txtDescPlan = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);  
  private ListaCampos lcPlan = new ListaCampos(this);
  private FPrinterJob dl = null;
  private JRadioGroup<?, ?> rgGrafico = null;
  private Vector<String> vLabs = new Vector<String>(2);
  private Vector<String> vVals = new Vector<String>(2);
  private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);   
  private JTextFieldFK  txtSiglaCC = new JTextFieldFK(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0); 
  private ListaCampos lcCC = new ListaCampos(this);
  private int iAnoCC = 0;
  public FRGraficoCC() {
    setTitulo("Gr�fico Financeiro por C.C");
    setAtribos(80,80,330,345);
    
    txtCodPlan.setRequerido(true);
	txtCodCC.setRequerido(true);
      
    lcPlan.add(new GuardaCampo(txtCodPlan,"CodPlan","C�d.plan.",ListaCampos.DB_PK,true));
    lcPlan.add(new GuardaCampo(txtDescPlan,"DescPlan","C�d.plan.",ListaCampos.DB_SI,true));
    
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");
    lcPlan.setWhereAdic("NIVELPLAN<6");
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    txtCodPlan.setFK(true);
    txtCodPlan.setNomeCampo("CodPlan");

	lcCC.add(new GuardaCampo(txtCodCC,"CodCC","C�d.cc.",ListaCampos.DB_PK,true));
	lcCC.add(new GuardaCampo(txtSiglaCC,"SiglaCC","Sigla",ListaCampos.DB_SI,true));
	lcCC.add(new GuardaCampo(txtDescCC,"DescCC","Descri��o do centro de custo",ListaCampos.DB_SI,true));
	
	
	lcCC.setReadOnly(true);
	lcCC.montaSql(false, "CC", "FN");
	txtCodCC.setTabelaExterna(lcCC);
	txtCodCC.setFK(true);
	txtCodCC.setNomeCampo("CodCC");
	txtSiglaCC.setListaCampos(lcCC);

    adic(new JLabelPad("Periodo:"),7,5,120,20);
    adic(new JLabelPad("De:"),7,25,30,20);
    adic(txtDataini,40,25,117,20);
    adic(new JLabelPad("At�:"),160,25,22,20);
    adic(txtDatafim,185,25,120,20);
    adic(new JLabelPad("N� plan."),7,50,250,20);
    adic(txtCodPlan,7,70,80,20);
    adic(new JLabelPad("Descri��o do planejamento"),90,50,250,20);
    adic(txtDescPlan,90,70,200,20);
	adic(new JLabelPad("C�d.cc."),7,90,250,20);
	adic(txtCodCC,7,110,80,20);
	adic(new JLabelPad("Descri��o do centro de custo"),90,90,250,20);
	adic(txtDescCC,90,110,200,20);	
	adic(new JLabelPad("Tipo de gr�fico:"),7,130,180,20);
	adic(new JLabelPad(Icone.novo("graficoPizza.gif")),7,160,30,30);
	adic(new JLabelPad(Icone.novo("graficoBarra.png")),7,200,30,30);
	adic(new JLabelPad(Icone.novo("graficoGiratorio.gif")),7,240,30,30);
    
	vLabs.addElement("Pizza");
	vLabs.addElement("Barras 3D");
	vLabs.addElement("Pizza Girat�ria");
	vVals.addElement("P");
	vVals.addElement("B");
	vVals.addElement("G");
	rgGrafico = new JRadioGroup<String, String>(3,1,vLabs,vVals);
	rgGrafico.setVlrString("P");    
	rgGrafico.setBorder(BorderFactory.createEmptyBorder());
	adic(rgGrafico,42,163,250,122);

	Calendar cPeriodo = Calendar.getInstance();
    txtDatafim.setVlrDate(cPeriodo.getTime());
	cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
	txtDataini.setVlrDate(cPeriodo.getTime());
  }
  public void setConexao(DbConnection cn) {
    super.setConexao(cn);
    lcPlan.setConexao(cn);
	lcCC.setConexao(cn);
	lcCC.setWhereAdic("NIVELCC<>10 AND ANOCC="+buscaAnoBaseCC());
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
		    iAnoCC = iRet;
		rs.close();
		ps.close();
	}
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o centro de custo.\n"+err.getMessage(),true,con,err);
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

        String sSQL = "SELECT MIN(NIVELCC) FROM FNCC WHERE CODCC LIKE ? AND (NOT CODCC=?) "+
                      "AND CODEMP=? AND CODFILIAL=? AND ANOCC=? ORDER BY 1";
		
	    PreparedStatement ps2 = null;
	    ResultSet rs2 = null;
	    try {        
	      ps2 = con.prepareStatement(sSQL);
		  ps2.setString(1,sCodCC+"%");
		  ps2.setString(2,sCodCC);
	      ps2.setInt(3,Aplicativo.iCodEmp);
		  ps2.setInt(4,iFilial);
  	      ps2.setInt(5,iAnoCC);
  	      rs2 = ps2.executeQuery();
	      if (rs2.next()) {
	      	iNivelPosterior = rs2.getInt(1);
	      }
		  rs2.close();
		  ps2.close();			  
	    }  
	    catch (SQLException err) {
		  Funcoes.mensagemErro(this,"Erro ao buscar nivel de planejamento!\n"+err.getMessage(),true,con,err);	      	
	    }

		sSQL = "SELECT CC.CODCC,CC.SIGLACC,CC.DESCCC,"+
			     "( SELECT ABS(SUM(SL.VLRSUBLANCA)) FROM FNSUBLANCA SL WHERE "+
			       "SL.CODEMPCC=CC.CODEMP AND SL.CODFILIALCC=CC.CODFILIAL AND "+
		           "SUBSTR(SL.CODCC,1,STRLEN(RTRIM(CC.CODCC)))=RTRIM(CC.CODCC) AND "+
			       "SL.ANOCC=CC.ANOCC AND "+
				   "SL.CODEMPPN=? AND SL.CODFILIALPN=? AND SL.CODPLAN LIKE '"+sCodPlan+"%' "+
				   "AND SL.DATASUBLANCA BETWEEN ? AND ? ) "+
		       "FROM FNCC CC WHERE CC.CODEMP=? AND CC.CODFILIAL=? AND CC.CODCC LIKE '"+sCodCC+"%' "+
		       (iNivelPosterior==10?"":"AND CC.NIVELCC='"+iNivelPosterior+"' ")+    		
			   "AND CC.ANOCC=? "+
			   "ORDER BY CC.CODCC";
       
	  PreparedStatement ps = null;

	  try {

		ps = con.prepareStatement(sSQL);

		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,iFilial);
		ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
		ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
		ps.setInt(5,Aplicativo.iCodEmp);
		ps.setInt(6,iFilial);
		ps.setInt(7,iAnoCC);		
		rs = ps.executeQuery();

	  }
	  catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar valores financeiros!\n"+err.getMessage(),true,con,err);
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
	else if (txtCodCC.getVlrString().trim().equals("")) {
	  Funcoes.mensagemInforma(this,"C�digo do Centro de Custo em branco!");
	  return;	
	}
	
	try {	  
	  if (rgGrafico.getVlrString().equals("P")) {
		BalancetePizza evBalanc = new BalancetePizza();
		evBalanc.setConexao(con);
		evBalanc.setConsulta(buscaValores());
		evBalanc.setTitulo("GR�FICO FINANCEIRO POR C.CUSTO","C.C: "+txtDescCC.getVlrString().trim().toUpperCase()+" - PLANEJAMENTO: "+txtDescPlan.getVlrString().toUpperCase());	  	
		dl = new FPrinterJob(evBalanc,this);	
		dl.setVisible(true);
	  }	        
	  else if (rgGrafico.getVlrString().equals("B")) { 
		BalanceteBarras evBalanc = new BalanceteBarras();
		evBalanc.setConexao(con);
		evBalanc.setConsulta(buscaValores());
		evBalanc.setTitulo("GR�FICO FINANCEIRO POR C.CUSTO","C.C: "+txtDescCC.getVlrString().trim().toUpperCase()+" - PLANEJAMENTO: "+txtDescPlan.getVlrString().toUpperCase());
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
		Aplicativo.telaPrincipal.criatela("Exibe Gr�fico",ex,con);		  
	  }	  	 
	} 
	catch (Exception err) {
	  Funcoes.mensagemInforma(this,"N�o foi poss�vel carregar relat�rio!\n"+err.getMessage());
	  err.printStackTrace();
	}
  }  
}
