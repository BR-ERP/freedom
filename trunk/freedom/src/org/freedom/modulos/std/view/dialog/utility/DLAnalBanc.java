/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLAnalBanc.java <BR>
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

package org.freedom.modulos.std.view.dialog.utility;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTabbedPanePad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;

import java.util.Date;
import java.util.Vector;

import javax.swing.JTextField;

import org.freedom.funcoes.Funcoes;

public class DLAnalBanc extends FFDialogo implements FocusListener{

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodPai = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescPai = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodAnal = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescAnal = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtAgCont = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldPad txtNumCont = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtDescCont = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodBanco = new JTextFieldPad(JTextFieldPad.TP_STRING,3,0);
  private JTextFieldFK txtDescBanco = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtDataCont = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldFK txtDescMoeda = new JTextFieldFK(JTextFieldPad.TP_STRING,20,0);
//  private JCheckBoxPad cbCompSldCx = new JCheckBoxPad("Comp�e saldo de caixa","S","N");
  private JTabbedPanePad tbp = new JTabbedPanePad();
  private JPanelPad pinGeral = new JPanelPad(370,240);
  private JPanelPad pinDet = new JPanelPad(370,240);
  private JLabelPad lbCodPai = new JLabelPad("C�digo");
  private JLabelPad lbDescPai = new JLabelPad("e descri��o da origem");
  private JLabelPad lbCodAnal = new JLabelPad("C�diogo");
  private JLabelPad lbDescAnal = new JLabelPad("Descri��o");
  private JLabelPad lbAgCont = new JLabelPad("Ag�ncia");
  private JLabelPad lbNumCont = new JLabelPad("N�mero");
  private JLabelPad lbDescCont = new JLabelPad("Descri��o");
  private JLabelPad lbCodBanc = new JLabelPad("C�digo");
  private JLabelPad lbDescBanc = new JLabelPad("e nome do banco");
  private JLabelPad lbDataCont = new JLabelPad("Data Inicial");
  private JLabelPad lbCodMoeda = new JLabelPad("C�digo");
  private JLabelPad lbDescMoeda = new JLabelPad("e descri��o da moeda");
  private Vector<String> vVals = new Vector<String>();
  private Vector<String> vLabs = new Vector<String>();
  private JRadioGroup<?, ?> rgTipo = null; 
  private Vector<String> vValsCont = new Vector<String>();
  private Vector<String> vLabsCont = new Vector<String>();
  private JRadioGroup<?, ?> rgTipoCont = null; 
  private ListaCampos lcBanc = new ListaCampos(this,"BO");
  private ListaCampos lcMoeda = new ListaCampos(this,"MA");
  public DLAnalBanc(Component cOrig, String sCodPai, String sDescPai, String sCod, String sDesc, String sTipo,String[] ContVals) {
  	super(cOrig);
    setTitulo("Nova Conta Anal�tica");
    setAtribos(380,305);
//Monta a tab Geral:
    cancText(txtCodPai); //Por ser an�litica s�o desabilitados estes campos:
    cancText(txtDescPai); 
    cancText(txtCodAnal); 
    txtCodPai.setVlrString(sCodPai); //Eles Receber valor padr�o:
    txtDescPai.setVlrString(sDescPai); 
    txtCodAnal.setVlrString(sCod); 
    vVals.addElement("B");  //Aqui � montado o JRadioGroup<?, ?> do tipo de conta:
    vVals.addElement("C");
    vVals.addElement("D");
    vVals.addElement("R");
    vLabs.addElement("Bancos");
    vLabs.addElement("Caixa");
    vLabs.addElement("Despesas");
    vLabs.addElement("Receitas");
    rgTipo = new JRadioGroup<String, String>(2,2,vLabs,vVals);
    rgTipo.setVlrString(sTipo);
    rgTipo.setAtivo(0,false);
    rgTipo.setAtivo(1,false);
    rgTipo.setAtivo(2,false);
    rgTipo.setAtivo(3,false);
    lcMoeda.add(new GuardaCampo( txtCodMoeda, "CodMoeda", "C�digo", ListaCampos.DB_PK,true));
    lcMoeda.add(new GuardaCampo( txtDescMoeda, "SingMoeda", "Descri��o", ListaCampos.DB_SI,false));
    lcMoeda.montaSql(false, "MOEDA", "FN");    
    lcMoeda.setReadOnly(true);
    txtCodMoeda.setTabelaExterna(lcMoeda); //Tabela da Foreign Key
    txtCodMoeda.setFK(true);
    txtCodMoeda.setNomeCampo("CodMoeda");
    vValsCont.addElement("B"); //Aqui � montado o JRadioGroup<?, ?> do tipo de entrada:
    vValsCont.addElement("C");
    vLabsCont.addElement("Bancos");
    vLabsCont.addElement("Caixa");
    rgTipoCont = new JRadioGroup<String, String>(1,2,vLabsCont,vValsCont);
    Funcoes.setBordReq(txtDescAnal);
    Funcoes.setBordReq(txtNumCont);    
    Funcoes.setBordReq(txtDescCont);    
    Funcoes.setBordReq(txtDataCont);    
    Funcoes.setBordReq(txtCodMoeda);    
    pinGeral.adic(lbCodPai,7,0,80,20);//S�o adicionados os componentes:
    pinGeral.adic(txtCodPai,7,20,80,20);
    pinGeral.adic(lbDescPai,90,0,200,20);
    pinGeral.adic(txtDescPai,90,20,240,20);
    pinGeral.adic(lbCodAnal,7,40,100,20);
    pinGeral.adic(txtCodAnal,7,60,110,20);
    pinGeral.adic(lbDescAnal,120,40,110,20);
    pinGeral.adic(txtDescAnal,120,60,210,20);
    pinGeral.adic(rgTipo,7,90,323,60);
//    pinGeral.adic( cbCompSldCx, 7, 120, 210, 20 );
    pinDet.adic(lbAgCont,7,0,80,20);
    pinDet.adic(txtAgCont,7,20,80,20);
    pinDet.adic(lbNumCont,90,0,240,20);
    pinDet.adic(txtNumCont,90,20,240,20);
    pinDet.adic(lbDescCont,7,40,323,20);
    pinDet.adic(txtDescCont,7,60,323,20);
    pinDet.adic(lbCodBanc,7,80,80,20);
    pinDet.adic(txtCodBanco,7,100,80,20);
    pinDet.adic(lbDescBanc,90,80,240,20);
    pinDet.adic(txtDescBanco,90,100,240,20);
    pinDet.adic(lbDataCont,7,120,90,20);
    pinDet.adic(txtDataCont,7,140,90,20);
    pinDet.adic(rgTipoCont,100,130,223,30);
    pinDet.adic(lbCodMoeda,7,160,80,20);
    pinDet.adic(txtCodMoeda,7,180,80,20);
    pinDet.adic(lbDescMoeda,90,160,240,20);
    pinDet.adic(txtDescMoeda,90,180,240,20);
// Monta a tab Detalhe:   
	lcBanc.add(new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK,false));
	lcBanc.add(new GuardaCampo( txtDescBanco, "NomeBanco", "Descri��o do banco", ListaCampos.DB_SI, false));
	lcBanc.montaSql(false, "BANCO", "FN");    
	lcBanc.setReadOnly(true);
	txtCodBanco.setTabelaExterna(lcBanc);
	txtCodBanco.setFK(true);
	txtCodBanco.setNomeCampo("CodBanco");
	//Se for Novo:
    if (sTipo.compareTo("B") == 0) {//Para cada entrada muda-se os Valores e Campos abilitados:
      rgTipoCont.setVlrString(sTipo);
      rgTipoCont.setAtivo(0,false);
      rgTipoCont.setAtivo(1,false);
//      cbCompSldCx.setEnabled( true );
    }
    else if (sTipo.compareTo("C") == 0) {
      cancText(txtAgCont);
      txtCodBanco.setEnabled(false);
      rgTipoCont.setVlrString(sTipo);
      rgTipoCont.setAtivo(0,false);
      rgTipoCont.setAtivo(1,false);
//      cbCompSldCx.setEnabled( true );
    }
    //else {
//        cbCompSldCx.setEnabled( false );
    //}
    //Se for Editar:
    if (sDesc != null) {
      setTitulo("Edi��o de Conta Anal�tica");
      txtDescAnal.setVlrString(sDesc);
      txtDescAnal.selectAll();
      txtDescAnal.requestFocus();
      txtAgCont.setVlrString(ContVals[0]);
      txtNumCont.setVlrString(ContVals[1]);
      txtDescCont.setVlrString(ContVals[2]);
      txtCodBanco.setVlrString(ContVals[3]);
      txtDataCont.setVlrString(ContVals[4]);
      txtCodMoeda.setVlrString(ContVals[5]);
    }
    txtDescAnal.addFocusListener(this);
    txtDescAnal.requestFocus();
    tbp.add("Geral",pinGeral);
    tbp.add("Detalhe",pinDet);
    c.add(tbp,BorderLayout.CENTER);
  }
  public void focusGained(FocusEvent fevt) { }
  public void focusLost(FocusEvent fevt) {//Copia a descri��o o planejamento para a descri��o da conta:
    if (txtDescCont.getText().length() == 0)
      txtDescCont.setVlrString(txtDescAnal.getText());
  }
  public void setConexao(DbConnection cn) {//Seta as devidas conex�es nos listacampos de Foreign Key
    lcBanc.setConexao(cn);
    lcMoeda.setConexao(cn);
  }
  private void cancText(JTextField txt) {//Desabilita TextFields
    txt.setBackground(Color.lightGray);
    txt.setFont(new Font("Dialog", Font.BOLD, 12));
    txt.setEditable(false);
    txt.setForeground(new Color(118, 89, 170));
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) { //Valida os valores:
      if (txtDescAnal.getText().trim().length() == 0) {
        Funcoes.mensagemInforma(this,"O campo descri��o est� em branco! ! !");
        txtDescAnal.requestFocus();
        return;
      }
      else if (txtNumCont.getText().trim().length() == 0) {
        Funcoes.mensagemInforma(this,"O campo n�mero da conta est� em branco! ! !");
        txtNumCont.requestFocus();
        return;
      }
      else if (txtDescCont.getText().trim().length() == 0) {
        Funcoes.mensagemInforma(this,"O campo descri��o da conta est� em branco! ! !");
        txtDescCont.requestFocus();
        return;
      }
      else if (txtDataCont.getText().trim().length() == 0) {
        Funcoes.mensagemInforma(this,"O campo data da conta est� em branco! ! !");
        txtDataCont.requestFocus();
        return;
      }
      else if (txtCodMoeda.getText().trim().length() == 0) {
        Funcoes.mensagemInforma(this,"O campo c�digo da moeda est� em branco! ! !");
        txtCodMoeda.requestFocus();
        return;
      }
    }
    super.actionPerformed(evt);
  }
  public String[] getValores() { //Devolve os valores a Tela de planejamento:
    String[] sVals = new String[7];
    sVals[0] = txtDescAnal.getText();
    sVals[1] = txtAgCont.getText();
    sVals[2] = txtNumCont.getText();
    sVals[3] = txtDescCont.getText();
    sVals[4] = txtCodBanco.getText();
    sVals[5] = Funcoes.dateToStrDate(txtDataCont.getVlrDate());
    sVals[6] = txtCodMoeda.getText();
    return sVals;
  }
  public Date getData() {
    return txtDataCont.getVlrDate();
  }
}

