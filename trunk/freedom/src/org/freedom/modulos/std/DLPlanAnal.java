/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freeedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLPlanAnal.java <BR>
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
 * Tela de cadastro de planejamento financeiro (Contas anal�ticas).
 */

package org.freedom.modulos.std;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLPlanAnal extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodPai = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescPai = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodAnal = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescAnal = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JLabelPad lbCodPai = new JLabelPad("C�d.origem");
  private JLabelPad lbDescPai = new JLabelPad("Descri��o da origem");
  private JLabelPad lbCodAnal = new JLabelPad("C�digo");
  private JLabelPad lbDescAnal = new JLabelPad("Descri��o");
  private JLabelPad lbFinPlan = new JLabelPad("Finalidade");
  
  private Vector vValsTipoPlan = new Vector();
  private Vector vLabsTipoPlan = new Vector();
  private Vector vValsFinPlan = new Vector();
  private Vector vLabsFinPlan = new Vector();
  private JRadioGroup rgTipoPlan = null; 
  private JRadioGroup rgFinPlan = null; 
  public DLPlanAnal(Component cOrig,String sCodPai, String sDescPai, String sCod, String sDesc, String sTipo, String sFin) {
  	super(cOrig);
  	setTitulo("Planejamento financeiro (Conta Anal�tica)");
    setAtribos(430,410);
    cancText(txtCodPai);
    cancText(txtDescPai);
    cancText(txtCodAnal);
    Funcoes.setBordReq(txtDescAnal);    
    txtCodPai.setVlrString(sCodPai);
    txtDescPai.setVlrString(sDescPai);
    txtCodAnal.setVlrString(sCod);
    vValsTipoPlan.addElement("B");
    vValsTipoPlan.addElement("C");
    vValsTipoPlan.addElement("D");
    vValsTipoPlan.addElement("R");
    vLabsTipoPlan.addElement("Bancos");
    vLabsTipoPlan.addElement("Caixa");
    vLabsTipoPlan.addElement("Despesas");
    vLabsTipoPlan.addElement("Receitas");
    rgTipoPlan = new JRadioGroup(2,2,vLabsTipoPlan,vValsTipoPlan);
    rgTipoPlan.setVlrString(sTipo);
    rgTipoPlan.setAtivo(0,false);
    rgTipoPlan.setAtivo(1,false);
    rgTipoPlan.setAtivo(2,false);
    rgTipoPlan.setAtivo(3,false);

    vValsFinPlan.addElement("RV");
    vValsFinPlan.addElement("OR");
    vValsFinPlan.addElement("ER");
    vValsFinPlan.addElement("CF");
    vValsFinPlan.addElement("CV");
    vValsFinPlan.addElement("II");
    vValsFinPlan.addElement("RF");
    vValsFinPlan.addElement("DF");
    vValsFinPlan.addElement("CS");
    vValsFinPlan.addElement("IR");
    vValsFinPlan.addElement("OO");
    vLabsFinPlan.addElement("RV - Receitas sobre vendas");
    vLabsFinPlan.addElement("OR - Outras receitas");
    vLabsFinPlan.addElement("ER - Estorno de receitas");
    vLabsFinPlan.addElement("CF - Custo fixo");
    vLabsFinPlan.addElement("CV - Custo vari�vel");
    vLabsFinPlan.addElement("II - Investimentos");
    vLabsFinPlan.addElement("RF - Receitas financeiras");
    vLabsFinPlan.addElement("DF - Despesas financeiras");
    vLabsFinPlan.addElement("CS - Contribui��o social");
    vLabsFinPlan.addElement("IR - Imposto de renda");
    vLabsFinPlan.addElement("OO - Outros");
    rgFinPlan = new JRadioGroup(6,2,vLabsFinPlan,vValsFinPlan);
    if (sFin.trim().equals(""))
      rgFinPlan.setVlrString(sFin);
    adic(lbCodPai,7,0,80,20);
    adic(txtCodPai,7,20,80,20);
    adic(lbDescPai,90,0,300,20);
    adic(txtDescPai,90,20,300,20);
    adic(lbCodAnal,7,40,110,20);
    adic(txtCodAnal,7,60,110,20);
    adic(lbDescAnal,120,40,270,20);
    adic(txtDescAnal,120,60,270,20);
    adic(rgTipoPlan,7,90,383,60);
    adic(lbFinPlan,7,155,270,20);
    adic(rgFinPlan,7,175,383,130);
    if (sDesc != null) {
      setTitulo("Edi��o de Conta Anal�tica");
      txtDescAnal.setVlrString(sDesc);
      txtDescAnal.selectAll();
    }
    txtDescAnal.requestFocus();
  }
  private void cancText(JTextFieldPad txt) {
    txt.setBackground(Color.lightGray);
    txt.setFont(new Font("Dialog", Font.BOLD, 12));
    txt.setEditable(false);
    txt.setForeground(new Color(118, 89, 170));
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtDescAnal.getText().trim().length() == 0) {
		Funcoes.mensagemErro(this,"O campo descri��o est� em branco!");
        txtDescAnal.requestFocus();
        return;
      }
    }
    super.actionPerformed(evt);
  }
  public String[] getValores() {
  	String[] sRet = new String[2];  
	sRet[0]	= txtDescAnal.getVlrString();
	sRet[1] = rgFinPlan.getVlrString();
    return sRet;
  }
}