/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLPlanSin.java <BR>
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
 * Tela de cadastro de planejamento financeiro (Contas sint�ticas).
 */

package org.freedom.modulos.std;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Vector;


import org.freedom.funcoes.Funcoes;
import org.freedom.library.JLabelPad;
import org.freedom.library.JRadioGroup;
import org.freedom.library.JTextFieldPad;
import org.freedom.telas.FFDialogo;

public class DLPlanSin extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodPai = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescPai = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodSin = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescSin = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JLabelPad lbCodPai = new JLabelPad("C�d.origem");
  private JLabelPad lbDescPai = new JLabelPad("Descri��o da origem");
  private JLabelPad lbCodSin = new JLabelPad("C�digo");
  private JLabelPad lbDescSin = new JLabelPad("Descri��o");
  private Vector<String> vVals = new Vector<String>();
  private Vector<String> vLabs = new Vector<String>();
  private JRadioGroup<?, ?> rgTipo = null; 
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
    rgTipo = new JRadioGroup<String, String>(2,2,vLabs,vVals);
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
