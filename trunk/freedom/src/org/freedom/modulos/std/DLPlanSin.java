/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLPlanSin.java <BR>
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
 * Tela de cadastro de planejamento financeiro (Contas sint�ticas).
 */

package org.freedom.modulos.std;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;
public class DLPlanSin extends FFDialogo {
  private JTextFieldPad txtCodPai = new JTextFieldPad();
  private JTextFieldPad txtDescPai = new JTextFieldPad();
  private JTextFieldPad txtCodSin = new JTextFieldPad();
  private JTextFieldPad txtDescSin = new JTextFieldPad(50);
  private JLabel lbCodPai = new JLabel("C�digo");
  private JLabel lbDescPai = new JLabel("e descri��o da origem");
  private JLabel lbCodSin = new JLabel("C�diogo");
  private JLabel lbDescSin = new JLabel("Descri��o");
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JRadioGroup rgTipo = null; 
  private String sCodPai = "";
  private String sDesc = "";
  public DLPlanSin(Component cOrig, String sCodP, String sDescPai, String sCod, String sD, String sTipo) {
  	super(cOrig);
    setTitulo("Planejamento financeiro (Conta Sint�tica)");
    setAtribos(310,250);
    sCodPai = sCodP;
    sDesc = sD;
    cancText(txtCodPai);
    cancText(txtDescPai);
    cancText(txtCodSin);
    Funcoes.setBordReq(txtDescSin);    
    txtCodPai.setVlrString(sCodPai);
    txtDescPai.setVlrString(sDescPai);
    txtCodSin.setVlrString(sCod);
    vVals.addElement("B");
    vVals.addElement("C");
    vVals.addElement("D");
    vVals.addElement("R");
    vLabs.addElement("Bancos");
    vLabs.addElement("Caixa");
    vLabs.addElement("Despesas");
    vLabs.addElement("Receitas");
    rgTipo = new JRadioGroup(2,2,vLabs,vVals);
    setTipo(sTipo);    
    adic(lbCodPai,7,0,80,20);
    adic(txtCodPai,7,20,80,20);
    adic(lbDescPai,90,0,200,20);
    adic(txtDescPai,90,20,200,20);
    adic(lbCodSin,7,40,80,20);
    adic(txtCodSin,7,60,80,20);
    adic(lbDescSin,90,40,100,20);
    adic(txtDescSin,90,60,200,20);
    adic(rgTipo,7,90,283,60);
    if (sDesc != null) {
      setTitulo("Edi��o de Conta Sint�tica");
      txtDescSin.setVlrString(sDesc);
      txtDescSin.selectAll();
    }
    txtDescSin.requestFocus();
  }
  private void cancText(JTextFieldPad txt) {
    txt.setBackground(Color.lightGray);
    txt.setFont(new Font("Dialog", Font.BOLD, 12));
    txt.setEditable(false);
    txt.setForeground(new Color(118, 89, 170));
  }
  private void setTipo(String sTipo) {
    if ((sDesc == null) & (sCodPai.trim().length() == 1) & (sTipo.compareTo("B") == 0)) {
      rgTipo.setVlrString("B");
      rgTipo.setAtivo(0,true);
      rgTipo.setAtivo(1,true);
      rgTipo.setAtivo(2,false);
      rgTipo.setAtivo(3,false);
    }
    else {
      rgTipo.setVlrString(sTipo);
      rgTipo.setAtivo(0,false);
      rgTipo.setAtivo(1,false);
      rgTipo.setAtivo(2,false);
      rgTipo.setAtivo(3,false);
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtDescSin.getText().trim().length() == 0) {
		Funcoes.mensagemInforma(this,"O campo descri��o est� em branco! ! !");
        txtDescSin.requestFocus();
        return;
      }
    }
    super.actionPerformed(evt);
  }
  public String[] getValores() {
    String[] sRetorno = {txtDescSin.getText(),rgTipo.getVlrString()};
    return sRetorno;
  }
}
