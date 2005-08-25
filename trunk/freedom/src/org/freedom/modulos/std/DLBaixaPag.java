/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLBaixaPag.java <BR>
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
 */

package org.freedom.modulos.std;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.JLabelPad;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLBaixaPag extends FFDialogo implements CarregaListener {

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldPad txtRazFor = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodConta = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtDescConta = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtDescPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);
  private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldFK  txtSiglaCC = new JTextFieldFK(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldFK  txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtDoc = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtDtEmis = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtVenc = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtVlrParc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtDtPagto = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtVlrPago = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtObs = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcConta = new ListaCampos(this);
  private ListaCampos lcPlan = new ListaCampos(this);
  private ListaCampos lcCC = new ListaCampos(this);
  public DLBaixaPag(Component cOrig) {
  	super(cOrig);
    setTitulo("Baixa");
    setAtribos(380,450);
    
    
    Funcoes.setBordReq(txtCodPlan);
    Funcoes.setBordReq(txtCodConta);
    Funcoes.setBordReq(txtDoc);
    Funcoes.setBordReq(txtDtPagto);
    Funcoes.setBordReq(txtVlrPago);
    Funcoes.setBordReq(txtObs);

    lcConta.add(new GuardaCampo( txtCodConta, "NumConta", "N� Conta", ListaCampos.DB_PK,false));
    lcConta.add(new GuardaCampo( txtDescConta, "DescConta", "Descri��o", ListaCampos.DB_SI,false));
    lcConta.montaSql(false, "CONTA", "FN");
    lcConta.setReadOnly(true);
    txtCodConta.setTabelaExterna(lcConta);
    txtCodConta.setFK(true);
    txtCodConta.setNomeCampo("NumConta");

	lcCC.add(new GuardaCampo( txtCodCC, "CodCC", "C�digo", ListaCampos.DB_PK,false));
	lcCC.add(new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla", ListaCampos.DB_SI,false));
	lcCC.add(new GuardaCampo( txtDescCC, "DescCC", "Descri��o", ListaCampos.DB_SI,false));
	lcCC.add(new GuardaCampo( txtAnoCC, "AnoCC", "Ano-Base", ListaCampos.DB_PK,false));
	lcCC.setReadOnly(true);
	lcCC.setQueryCommit(false);
	lcCC.setWhereAdic("NIVELCC=10");
	lcCC.montaSql(false, "CC", "FN");    
	txtCodCC.setTabelaExterna(lcCC);
	txtCodCC.setFK(true);
	txtCodCC.setNomeCampo("CodCC");
	txtAnoCC.setTabelaExterna(lcCC);
	txtAnoCC.setFK(true);
	txtAnoCC.setNomeCampo("AnoCC");

    lcPlan.add(new GuardaCampo( txtCodPlan, "CodPlan", "C�d. Plan.", ListaCampos.DB_PK,false));
    lcPlan.add(new GuardaCampo( txtDescPlan, "DescPlan", "Descri��o", ListaCampos.DB_SI,false));
    lcPlan.setWhereAdic("TIPOPLAN = 'D' AND NIVELPLAN = 6");
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    txtCodPlan.setFK(true);
    txtCodPlan.setNomeCampo("CodPlan");

    txtCodFor.setAtivo(false);
    txtRazFor.setAtivo(false);
    txtDescConta.setAtivo(false);
    txtDescPlan.setAtivo(false);
    txtDtEmis.setAtivo(false);
    txtDtVenc.setAtivo(false);
    txtVlrParc.setAtivo(false);
    
    adic(new JLabelPad("C�digo e raz�o do fornecedor"),7,0,250,20);
    adic(txtCodFor,7,20,80,20);
    adic(txtRazFor,90,20,200,20);
    adic(new JLabelPad("N�mero e descri��o da conta"),7,40,250,20);
    adic(txtCodConta,7,60,80,20);
    adic(txtDescConta,90,60,200,20);
    adic(new JLabelPad("C�digo e descri��o da categoria"),7,80,250,20);
    adic(txtCodPlan,7,100,100,20);
    adic(txtDescPlan,110,100,200,20);
	adic(new JLabelPad("C�digo e descri��o do centro de custo"),7,120,250,20);
	adic(txtCodCC,7,140,100,20);
	adic(txtDescCC,110,140,200,20);
    adic(new JLabelPad("Doc."),7,160,110,20);
    adic(txtDoc,7,180,110,20);
    adic(new JLabelPad("Emiss�o"),120,160,107,20);
    adic(txtDtEmis,120,180,107,20);
    adic(new JLabelPad("Vencimento"),230,160,110,20);
    adic(txtDtVenc,230,180,110,20);
    adic(new JLabelPad("Vlr. Parc."),7,200,110,20);
    adic(txtVlrParc,7,220,110,20);
    adic(new JLabelPad("Dt. Pagto."),120,200,107,20);
    adic(txtDtPagto,120,220,107,20);
    adic(new JLabelPad("Vlr. Pago"),230,200,110,20);
    adic(txtVlrPago,230,220,110,20);
    adic(new JLabelPad("Observa��es"),7,240,200,20);
    adic(txtObs,7,260,333,20);
    
    lcCC.addCarregaListener(this);
  }
  public void setValores(String[] sVals) {
    txtCodFor.setVlrString(sVals[0]);
    txtRazFor.setVlrString(sVals[1]);
    txtCodConta.setVlrString(sVals[2]);
    txtCodPlan.setVlrString(sVals[3]);
    txtDoc.setVlrString(sVals[4]);
    txtDtEmis.setVlrString(sVals[5]);
    txtDtVenc.setVlrString(sVals[6]);
    txtVlrParc.setVlrString(sVals[7]);
    txtDtPagto.setVlrString(sVals[8]);
    txtVlrPago.setVlrString(sVals[9]);
    txtCodCC.setVlrString(sVals[10]);
    txtObs.setVlrString(sVals[11]);
  }
  public String[] getValores() {
    String[] sRetorno = new String[7];
    sRetorno[0] = txtCodConta.getVlrString();
    sRetorno[1] = txtCodPlan.getVlrString();
    sRetorno[2] = txtDoc.getVlrString();
    sRetorno[3] = txtDtPagto.getVlrString();
    sRetorno[4] = txtVlrPago.getVlrString();
    sRetorno[5] = txtCodCC.getVlrString();
	sRetorno[6] = txtObs.getVlrString();
    return sRetorno;
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtCodConta.getVlrString().length() < 1) {
		Funcoes.mensagemInforma(this,"N�mero da conta � requerido!");
      }
      else if (txtCodPlan.getVlrString().length() < 13) {
		Funcoes.mensagemInforma(this,"C�digo da categoria � requerido!");
      }
      else if (txtDtPagto.getVlrString().length() < 10) {
		Funcoes.mensagemInforma(this,"Data do pagamento � requerido!");
      }
      else if (txtVlrPago.getVlrString().length() < 4) {
		Funcoes.mensagemInforma(this,"Valor pago � requerido!");
      }
      else {
        super.actionPerformed(evt);
      }
    }
    else {
      super.actionPerformed(evt);
    }
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
		Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o centro de custo.\n"+err.getMessage(),true,con,err);
	}
	return iRet;
  }
  public void beforeCarrega(CarregaEvent cevt) {
  	if (cevt.getListaCampos() == lcCC && txtAnoCC.getVlrInteger().intValue() == 0) {
  		txtAnoCC.setVlrInteger(new Integer(buscaAnoBaseCC()));
  	}
  }
  public void afterCarrega(CarregaEvent cevt) {
  }
  public void setConexao(Connection cn) {
  	super.setConexao(cn);
    lcConta.setConexao(cn);
    lcConta.carregaDados();
    lcPlan.setConexao(cn);
    lcPlan.carregaDados();
	lcCC.setConexao(cn);
	lcCC.carregaDados();
  }
}
