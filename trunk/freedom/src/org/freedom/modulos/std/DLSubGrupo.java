/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLSubGrupo.java <BR>
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
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDialogo;

public class DLSubGrupo extends FDialogo {
  private JTextFieldPad txtCodPai = new JTextFieldPad();
  private JTextFieldPad txtDescPai = new JTextFieldPad();
  private JTextFieldPad txtSiglaPai = new JTextFieldPad();
  private JTextFieldPad txtCodSubGrup = new JTextFieldPad();
  private JTextFieldPad txtDescSubGrup = new JTextFieldPad(50);
  private JTextFieldPad txtSiglaSubGrup = new JTextFieldPad(10);
  private JLabel lbCodPai = new JLabel("C�digo");
  private JLabel lbDescPai = new JLabel("Descri��o do grupo superior");
  private JLabel lbSiglaPai = new JLabel("Sigla");
  private JLabel lbCodSubGrup = new JLabel("C�digo");
  private JLabel lbDescSubGrup = new JLabel("Descri��o");
  private JLabel lbSiglaSubGrup = new JLabel("Sigla");
  public DLSubGrupo(String sCodPai, String sDescPai, String sCod, String sDesc, String sSigla) {
    setTitulo("Novo Sub-Grupo");
    setAtribos(400,170);
    cancText(txtCodPai);
    cancText(txtDescPai);
    cancText(txtSiglaPai);
    cancText(txtCodSubGrup);
    txtCodPai.setVlrString(sCodPai);
    txtDescPai.setVlrString(sDescPai);
    txtSiglaPai.setVlrString(sSigla);	
    txtCodSubGrup.setVlrString(sCod);
    txtSiglaSubGrup.setVlrString(sSigla);	
    adic(lbCodPai,7,0,80,20);
    adic(txtCodPai,7,20,80,20);
    adic(lbDescPai,90,0,200,20);
    adic(txtDescPai,90,20,200,20);
    adic(lbSiglaPai,293,0,80,20);
    adic(txtSiglaPai,293,20,80,20);
    adic(lbCodSubGrup,7,40,80,20);
    adic(txtCodSubGrup,7,60,80,20);
    adic(lbDescSubGrup,90,40,100,20);
    adic(txtDescSubGrup,90,60,200,20);
    adic(lbSiglaSubGrup,293,40,80,20);
    adic(txtSiglaSubGrup,293,60,80,20);
    if (sDesc != null) {
      setTitulo("Edi��o de Sub-Grupo");
      txtDescSubGrup.setVlrString(sDesc);
      txtDescSubGrup.selectAll();
    }
  }
  private void cancText(JTextFieldPad txt) {
    txt.setBackground(Color.lightGray);
    txt.setFont(new Font("Dialog", Font.BOLD, 12));
    txt.setEditable(false);
    txt.setForeground(new Color(118, 89, 170));
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtDescSubGrup.getText().trim().length() == 0) {
		Funcoes.mensagemInforma(this,"O campo descri��o est� em branco! ! !");
        txtDescSubGrup.requestFocus();
        return;
      }
    }
    super.actionPerformed(evt);
  }
  public String[] getValor() {
  	String[] sRetorno = new String[2];
  	sRetorno[0] = txtDescSubGrup.getText();
  	sRetorno[1] = txtSiglaSubGrup.getText();
    return sRetorno;
  }
}
