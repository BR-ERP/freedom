/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLCCPrim.java <BR>
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
    
    Funcoes.setBordReq( txtSigla );
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

