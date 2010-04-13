/**
 * @version 30/01/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: projetos.freedom.gms <BR>
 * Classe: @(#)FAtribuicao.java <BR>
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
 * Atribui��es das pessoas que comp�em o fluxo da RMA.
 * 
 */

package org.freedom.modulos.gms.view.frame.crud.plain;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FAtribuicao extends FDados implements ActionListener, PostListener, CarregaListener {
  private static final long serialVersionUID = 1L;	
  private JTextFieldPad txtIdAtrib = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtDescAtrib = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtRmaAtrib = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
  private JTextAreaPad txaObsAtrib = new JTextAreaPad(500);
  private JScrollPane spnObs = new JScrollPane(txaObsAtrib);
  private JCheckBoxPad cbReq = new JCheckBoxPad("Requisitante",new Integer(1),new Integer(0));
  private JCheckBoxPad cbGer = new JCheckBoxPad("Gerente",new Integer(2),new Integer(0));
  private JCheckBoxPad cbDir = new JCheckBoxPad("Diretor",new Integer(4),new Integer(0));
  private JCheckBoxPad cbAlm = new JCheckBoxPad("Almoxarife",new Integer(8),new Integer(0));
  public FAtribuicao () {
  	super();
    setTitulo("Cadastro de atribui��es");
    setAtribos(50, 50, 340, 280);
    adicCampo(txtIdAtrib, 7, 20, 70, 20,"IdAtrib","C�d.atrib.", ListaCampos.DB_PK, true);
    adicCampo(txtDescAtrib, 80, 20, 230, 20,"DescAtrib","Descri��o da atirbui��o", ListaCampos.DB_SI, true);
    
    adicCampoInvisivel(txtRmaAtrib, "RmaAtrib","Atrib.", ListaCampos.DB_SI, true);
    adic(cbReq,7,40,150,20);
    adic(cbGer,160,40,150,20);
    adic(cbDir,7,60,150,20);
    adic(cbAlm,160,60,150,20);
    
    adicDBLiv(txaObsAtrib, "ObsAtrib","Obs.", false);
    adic(new JLabelPad("Observa��o"),7,80,303,20);
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
