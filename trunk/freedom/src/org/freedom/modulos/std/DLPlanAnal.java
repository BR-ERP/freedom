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
  private JTextFieldPad txtCodPai = new JTextFieldPad();
  private JTextFieldPad txtDescPai = new JTextFieldPad();
  private JTextFieldPad txtCodAnal = new JTextFieldPad();
  private JTextFieldPad txtDescAnal = new JTextFieldPad(50);
  private JLabelPad lbCodPai = new JLabelPad("C�d.origem");
  private JLabelPad lbDescPai = new JLabelPad("Descri��o da origem");
  private JLabelPad lbCodAnal = new JLabelPad("C�digo");
  private JLabelPad lbDescAnal = new JLabelPad("Descri��o");
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JRadioGroup rgTipo = null; 
  public DLPlanAnal(Component cOrig,String sCodPai, String sDescPai, String sCod, String sDesc, String sTipo) {
  	super(cOrig);
  	setTitulo("Planejamento financeiro (Conta Anal�tica)");
    setAtribos(350,280);
    cancText(txtCodPai);
    cancText(txtDescPai);
    cancText(txtCodAnal);
    Funcoes.setBordReq(txtDescAnal);    
    txtCodPai.setVlrString(sCodPai);
    txtDescPai.setVlrString(sDescPai);
    txtCodAnal.setVlrString(sCod);
    vVals.addElement("B");
    vVals.addElement("C");
    vVals.addElement("D");
    vVals.addElement("R");
    vLabs.addElement("Bancos");
    vLabs.addElement("Caixa");
    vLabs.addElement("Despesas");
    vLabs.addElement("Receitas");
    rgTipo = new JRadioGroup(2,2,vLabs,vVals);
    rgTipo.setVlrString(sTipo);
    rgTipo.setAtivo(0,false);
    rgTipo.setAtivo(1,false);
    rgTipo.setAtivo(2,false);
    rgTipo.setAtivo(3,false);
    adic(lbCodPai,7,0,80,20);
    adic(txtCodPai,7,20,80,20);
    adic(lbDescPai,90,0,200,20);
    adic(txtDescPai,90,20,240,20);
    adic(lbCodAnal,7,40,100,20);
    adic(txtCodAnal,7,60,110,20);
    adic(lbDescAnal,120,40,110,20);
    adic(txtDescAnal,120,60,210,20);
    adic(rgTipo,7,90,323,60);
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
		Funcoes.mensagemErro(this,"O campo descri��o est� em branco! ! !");
        txtDescAnal.requestFocus();
        return;
      }
    }
    super.actionPerformed(evt);
  }
  public String getValor() {
  	String sRet = txtDescAnal.getText(); 
    return sRet;
  }
}