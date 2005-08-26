/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLCCPrim.java <BR>
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
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLCCPrim extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodCont = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtDescCont = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtSigla = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JPanelPad pinCont = new JPanelPad(450,100);
  private JLabelPad lbCod = new JLabelPad("C�digo");
  private JLabelPad lbDesc = new JLabelPad("Descri��o");
  public DLCCPrim(Component cOrig, String sCod, String sDesc, String sSigla) {
  	super(cOrig);
    setTitulo("Novo Item \"Nivel 1\"");
    setAtribos(450,130);
    Funcoes.setBordReq(txtDescCont); 
    txtCodCont.setAtivo(false);
    txtCodCont.setVlrString(sCod);
    pinCont.adic(lbCod,7,5,80,20);
    pinCont.adic(txtCodCont,7,25,100,20);
    pinCont.adic(lbDesc,110,5,150,20);
    pinCont.adic(txtDescCont,110,25,217,20);
	pinCont.adic(new JLabelPad("Sig."),330,5,100,20);
	pinCont.adic(txtSigla,330,25,80,20);
    c.add(pinCont, BorderLayout.CENTER);
    if (sDesc != null) {                                                                 
      setTitulo("Edi��o de Conta de 1� Nivel");
      txtDescCont.setVlrString(sDesc);
	  txtSigla.setVlrString(sSigla);
    }
    txtDescCont.requestFocus();
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtDescCont.getText().trim().length() == 0) {
		Funcoes.mensagemInforma(this,"O campo descri��o est� em branco! ! !");
        txtDescCont.requestFocus();
        return;
      }
	  else if (txtSigla.getText().trim().length() == 0) {
		Funcoes.mensagemInforma(this,"O campo sigla est� em branco! ! !");
		txtSigla.requestFocus();
		return;
	  }
    }
    super.actionPerformed(evt);
  }
  public String[] getValores() {
    String sRetorno[] = {txtDescCont.getVlrString(),txtSigla.getVlrString()};
    return sRetorno;
  }
}

