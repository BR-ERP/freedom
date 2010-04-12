/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLSubGrupo.java <BR>
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
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std.view.dialog.tool;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;


import org.freedom.funcoes.Funcoes;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLSubGrupo extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodPai = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescPai = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtSiglaPai = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtCodSubGrup = new JTextFieldPad(JTextFieldPad.TP_INTEGER,20,0);
  private JTextFieldPad txtDescSubGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtSiglaSubGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  
  private JLabelPad lbCodPai = new JLabelPad("C�digo");
  private JLabelPad lbDescPai = new JLabelPad("Descri��o do grupo superior");
  private JLabelPad lbSiglaPai = new JLabelPad("Sigla");
  private JLabelPad lbCodSubGrup = new JLabelPad("C�digo");
  private JLabelPad lbDescSubGrup = new JLabelPad("Descri��o");
  private JLabelPad lbSiglaSubGrup = new JLabelPad("Sigla");
  private JCheckBoxPad cbEstNeg = new JCheckBoxPad("Permitir saldo negativo?", "S", "N");
  private JCheckBoxPad cbEstLotNeg = new JCheckBoxPad("Permitir saldo de lote negativo?", "S", "N");
  public DLSubGrupo(String sCodPai, String sDescPai, String sCod, String sDesc, String sSigla,String sSiglaPai,boolean bEstNeg,String sEstNeg,String sEstNegLot) {
    setTitulo("Novo Sub-Grupo");
    setAtribos(400,240);
    cancText(txtCodPai);
    cancText(txtDescPai);
    cancText(txtSiglaPai);
    cancText(txtCodSubGrup);
    txtCodPai.setVlrString(sCodPai);
    txtDescPai.setVlrString(sDescPai);
    txtSiglaPai.setVlrString(sSiglaPai);	
    txtCodSubGrup.setVlrString(sCod);
    txtSiglaSubGrup.setVlrString(sSigla);	
    cbEstLotNeg.setVlrString(sEstNegLot);
    cbEstNeg.setVlrString(sEstNeg);
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
    adic(cbEstNeg,7,90,250,20);
    adic(cbEstLotNeg,7,110,250,20);
    
   	cbEstNeg.setEnabled(bEstNeg);
   	cbEstLotNeg.setEnabled(bEstNeg);
    
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
  	String[] sRetorno = new String[4];
  	sRetorno[0] = txtDescSubGrup.getText();
  	sRetorno[1] = txtSiglaSubGrup.getText();
    sRetorno[2] = cbEstNeg.getVlrString();
    sRetorno[3] = cbEstLotNeg.getVlrString();
    return sRetorno;
  }
}
