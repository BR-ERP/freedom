/**
 * @version 30/01/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: projetos.freedom.gms <BR>
 * Classe: @(#)FAtribuicao.java <BR>
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
 * Atribui��es das pessoas que comp�em o fluxo da RMA.
 * 
 */

package org.freedom.modulos.gms;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.telas.FDados;

public class FAtribuicao extends FDados implements ActionListener, PostListener, CarregaListener {
  private JTextFieldPad txtIdAtrib = new JTextFieldPad();
  private JTextFieldPad txtDescAtrib = new JTextFieldPad();
  private JTextFieldPad txtRmaAtrib = new JTextFieldPad();
  private JTextAreaPad txaObsAtrib = new JTextAreaPad(500);
  private JScrollPane spnObs = new JScrollPane(txaObsAtrib);
  private JCheckBoxPad cbReq = new JCheckBoxPad("Requisitante",new Integer(1),new Integer(0));
  private JCheckBoxPad cbGer = new JCheckBoxPad("Gerente",new Integer(2),new Integer(0));
  private JCheckBoxPad cbDir = new JCheckBoxPad("Diretor",new Integer(4),new Integer(0));
  private JCheckBoxPad cbAlm = new JCheckBoxPad("Almoxarife",new Integer(8),new Integer(0));
  public FAtribuicao () {
    setTitulo("Cadastro de atribui��es");
    setAtribos(50, 50, 350, 280);
    adicCampo(txtIdAtrib, 7, 20, 50, 20,"IdAtrib","C�digo",JTextFieldPad.TP_STRING,10,0,true,false,null,true);
    adicCampo(txtDescAtrib, 60, 20, 250, 20,"DescAtrib","Descri��o",JTextFieldPad.TP_STRING,50,0,false,false,null,true);
    
    adicCampoInvisivel(txtRmaAtrib, "RmaAtrib","Atrib.",JTextFieldPad.TP_STRING,2,0,false,false,null,true);
    adic(cbReq,7,40,150,20);
    adic(cbGer,160,40,150,20);
    adic(cbDir,7,60,150,20);
    adic(cbAlm,160,60,150,20);
    
    adicDBLiv(txaObsAtrib, "ObsAtrib","Obs.",JTextFieldPad.TP_STRING,false);
    adic(new JLabel("Observa��o"),7,80,303,20);
    adic(spnObs,7,100,303,100);
    
    setListaCampos( true, "ATRIBUICAO", "SG");
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    lcCampos.setQueryInsert(false);
    lcCampos.addCarregaListener(this);
    cbReq.setListaCampos(lcCampos);
    cbGer.setListaCampos(lcCampos);
    cbDir.setListaCampos(lcCampos);
    cbAlm.setListaCampos(lcCampos);
  }
  private void sendAtribRma() {
  	 int iSoma = 0;
 	 iSoma += cbReq.getVlrInteger().intValue();
 	 iSoma += cbGer.getVlrInteger().intValue();
 	 iSoma += cbDir.getVlrInteger().intValue();
 	 iSoma += cbAlm.getVlrInteger().intValue();
 	 txtRmaAtrib.setText(""+iSoma);
  }
  private void putAtribRma() {
  	int iSoma = txtRmaAtrib.getVlrInteger().intValue();
  	if ((iSoma-8) >= 0) {
  		cbAlm.setVlrInteger(new Integer(8));
  		iSoma-=8;
  	}
  	else
  		cbAlm.setVlrInteger(new Integer(0));
  	if ((iSoma-4) >= 0) {
  		cbDir.setVlrInteger(new Integer(4));
  		iSoma-=4;
  	}
  	else
  		cbDir.setVlrInteger(new Integer(0));
  	if ((iSoma-2) >= 0) {
  		cbGer.setVlrInteger(new Integer(2));
  		iSoma-=2;
  	}
  	else
  		cbGer.setVlrInteger(new Integer(0));
  	if ((iSoma-1) >= 0) {
  		cbReq.setVlrInteger(new Integer(1));
  		iSoma-=1;
  	}
  	else
  		cbReq.setVlrInteger(new Integer(0));
  }
  public void valorAlterado(CheckBoxEvent evt) {
     if (evt.getCheckBox() != null)
	   lcCampos.edit();
  }
  public void beforePost(PostEvent pevt) {
     if (pevt.getListaCampos() == lcCampos)
    	sendAtribRma();
  }
  public void afterCarrega(CarregaEvent cevt) {
  	if (cevt.getListaCampos() == lcCampos)
  		putAtribRma();
  }
  public void beforeCarrega(CarregaEvent cevt) { }
}
