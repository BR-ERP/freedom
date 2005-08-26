/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLCCSin.java <BR>
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLCCSin extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodPai = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescPai = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodSin = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescSin = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtSigla = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JLabelPad lbCodPai = new JLabelPad("C�d.origem");
  private JLabelPad lbDescPai = new JLabelPad("Descri��o da origem");
  private JLabelPad lbCodSin = new JLabelPad("C�digo");
  private JLabelPad lbDescSin = new JLabelPad("Descri��o");
  private String sCodPai = "";
  private String sDesc = "";
  public DLCCSin(Component cOrig, String sCodP, String sDescPai, String sCod, String sD, String sSigla) {
  	super(cOrig);
    setTitulo("Nova Conta Sint�tica");
    setAtribos(410,170);
    sCodPai = sCodP;
    sDesc = sD;
    cancText(txtCodPai);
    cancText(txtDescPai);
    cancText(txtCodSin);
    Funcoes.setBordReq(txtDescSin);    
    txtCodPai.setVlrString(sCodPai);
    txtDescPai.setVlrString(sDescPai);
    txtCodSin.setVlrString(sCod);
    adic(lbCodPai,7,0,80,20);
    adic(txtCodPai,7,20,80,20);
    adic(lbDescPai,90,0,200,20);
    adic(txtDescPai,90,20,200,20);
    adic(lbCodSin,7,40,80,20);
    adic(txtCodSin,7,60,80,20);
    adic(lbDescSin,90,40,100,20);
    adic(txtDescSin,90,60,197,20);
	adic(new JLabelPad("Sigla"),290,40,100,20);
	adic(txtSigla,290,60,80,20);
    if (sDesc != null) {
      setTitulo("Edi��o de Conta Sint�tica");
      txtDescSin.setVlrString(sDesc);
	  txtSigla.setVlrString(sSigla);
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
    String[] sRetorno = {txtDescSin.getVlrString(),txtSigla.getVlrString()};
    return sRetorno;
  }
}
