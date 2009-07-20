/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLPlanPrim.java <BR>
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
 * Tela de cadastro de planejamento financeiro (N�vel 1).
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTabbedPanePad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLPlanPrim extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodCont = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtDescCont = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JPanelPad pinCont = new JPanelPad(360,220);
  private Vector<String> vVals = new Vector<String>();
  private Vector<String> vLabs = new Vector<String>();
  private JRadioGroup<?, ?> rgTipo = null;
  private JTabbedPanePad ptb = new JTabbedPanePad();
  private JLabelPad lbCod = new JLabelPad("C�digo");
  private JLabelPad lbDesc = new JLabelPad("Descri��o");
  public DLPlanPrim(Component cOrig,String sCod, String sDesc, String sTipo) {
  	super(cOrig);
    setTitulo("Planejamento financeiro (N�vel 1)");
    setAtribos(360,220);
    Funcoes.setBordReq(txtDescCont);    
    txtCodCont.setBackground(Color.lightGray);
    txtCodCont.setFont(new Font("Dialog", Font.BOLD, 12));
    txtCodCont.setEditable(false);
    txtCodCont.setForeground(new Color(118, 89, 170));
    txtCodCont.setVlrString(sCod);
    vVals.addElement("B");
    vVals.addElement("R");
    vVals.addElement("D");
    vLabs.addElement("Caixa e Bancos");
    vLabs.addElement("Receita");
    vLabs.addElement("Despesa");
    rgTipo = new JRadioGroup<String, String>(1,3,vLabs,vVals);
    rgTipo.setVlrString("D");
    pinCont.adic(lbCod,7,0,80,20);
    pinCont.adic(txtCodCont,7,20,100,20);
    pinCont.adic(lbDesc,110,0,150,20);
    pinCont.adic(txtDescCont,110,20,220,20);
    pinCont.adic(rgTipo,7,50,323,25);
    ptb.add("1� Nivel",pinCont);
    c.add(ptb, BorderLayout.CENTER);
    if (sDesc != null) {
      setTitulo("Edi��o de Conta de 1� Nivel");
      rgTipo.setVlrString(sTipo);
      txtDescCont.setVlrString(sDesc);
      txtDescCont.selectAll();
      rgTipo.setVlrString(sTipo);
      rgTipo.setAtivo(0,false);
      rgTipo.setAtivo(1,false);
      rgTipo.setAtivo(2,false);
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
    }
    super.actionPerformed(evt);
  }
  public String[] getValores() {
    String[] sRetorno = {txtDescCont.getText(),rgTipo.getVlrString()};
    return sRetorno;
  }
}

