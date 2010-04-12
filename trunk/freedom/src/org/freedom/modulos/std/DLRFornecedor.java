/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freeedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRFornecedor.java <BR>
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

import java.awt.Component;
import java.awt.GridLayout;

import org.freedom.telas.FFDialogo;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;

import java.util.Vector;
public class DLRFornecedor extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JRadioGroup<?, ?> rgOrdem = null;
  private JRadioGroup<?, ?> rgModo = null;
  private JPanelPad pnlbSelec = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JPanelPad pinSelec = new JPanelPad(350,70);
  private JPanelPad pnlbPessoa = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JPanelPad pinPessoa = new JPanelPad(450,40);
  private JTextFieldPad txtCid = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JLabelPad lbSelec = new JLabelPad(" Selec�o:");
  private JLabelPad lbDe = new JLabelPad("De:");
  private JLabelPad lbA = new JLabelPad("�:");
  private JTextFieldPad txtDe = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtA = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JLabelPad lbPessoa = new JLabelPad(" Selecionar pessoas:");
  private JLabelPad lbCid = new JLabelPad("Cidade");
  private JLabelPad lbModo = new JLabelPad("Modo do relat�rio:");
  private JCheckBoxPad cbObs = new JCheckBoxPad("Imprimir Observa��es ?","S","N");
  private JCheckBoxPad cbFis = new JCheckBoxPad("F�sica","S","N");
  private JCheckBoxPad cbJur = new JCheckBoxPad("Jur�dica","S","N");
  private Vector<String> vLabs = new Vector<String>();
  private Vector<String> vVals = new Vector<String>();
  private Vector<String> vLabsModo = new Vector<String>();
  private Vector<String> vValsModo = new Vector<String>();
  private JLabelPad lbTipoFor = new JLabelPad("C�d.t.for.");
  private JLabelPad lbDescTipoFor = new JLabelPad("Descri��o do tipo de fornecedor");
  private JTextFieldPad txtCodTipoFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTipoFor = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private ListaCampos lcTipoFor = new ListaCampos(this);
  public DLRFornecedor(Component cOrig, DbConnection cn) {
  	super(cOrig);
  	setTitulo("Relat�rio de Fornecedores");
    setAtribos(420,400);
    vLabs.addElement("C�digo");
    vLabs.addElement("Raz�o");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");

    vLabsModo.addElement("Resumido");
    vLabsModo.addElement("Completo");
    vValsModo.addElement("R");
    vValsModo.addElement("C");
    rgModo = new JRadioGroup<String, String>(1,2,vLabsModo,vValsModo);
    rgModo.setVlrString("R");

    cbObs.setVlrString("N");
	cbFis.setVlrString("N");
	cbJur.setVlrString("S");

    lcTipoFor.add(new GuardaCampo( txtCodTipoFor, "CodTipoFor", "C�d.tp.for.", ListaCampos.DB_PK, false));
    lcTipoFor.add(new GuardaCampo( txtDescTipoFor, "DescTipoFor", "Descri��o do tipo de fornecedor",ListaCampos.DB_SI, false));
    lcTipoFor.montaSql(false, "TIPOFOR", "CP");
    lcTipoFor.setReadOnly(true);
    txtCodTipoFor.setTabelaExterna(lcTipoFor);
    txtCodTipoFor.setFK(true);
    txtCodTipoFor.setNomeCampo("CodTipoFor");

    pnlbSelec.add(lbSelec);
    adic(lbOrdem,7,5,180,20);
    adic(rgOrdem,7,25,200,30);
    adic(cbObs,210,35,190,20);
    adic(pnlbSelec,10,63,80,15);
    pinSelec.adic(lbDe,7,10,30,20);
    pinSelec.adic(txtDe,40,15,360,20);
    pinSelec.adic(lbA,7,40,30,20);
    pinSelec.adic(txtA,40,40,360,20);
    adic(pinSelec,7,70,393,70);
	pnlbPessoa.add(lbPessoa);
	adic(pnlbPessoa,10,148,170,15);
	pinPessoa.adic(cbFis,7,10,80,20);
	pinPessoa.adic(cbJur,90,10,80,20);
	adic(pinPessoa,7,155,200,40);
    adic(lbCid,210,155,190,20);
    adic(txtCid,210,175,190,20);
    adic(lbModo,7,200,140,20);
    adic(rgModo,7,220,393,30);
    adic(lbTipoFor,7,250,300,20);
    adic(txtCodTipoFor,7,280,80,20);
    adic(lbDescTipoFor,90,250,300,20);
    adic(txtDescTipoFor,90,280,310,20);
    
	lcTipoFor.setConexao(cn);
  }
  public String[] getValores() {
    String[] sRetorno = new String[10];
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sRetorno[0] = "CODFOR";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sRetorno[0] = "RAZFOR";
    sRetorno[1] = cbObs.getVlrString();
    sRetorno[2] = txtDe.getText();
    sRetorno[3] = txtA.getText();
	sRetorno[4] = cbFis.getVlrString();
	sRetorno[5] = txtCid.getVlrString();
	sRetorno[6] = cbJur.getVlrString();
    sRetorno[7] = rgModo.getVlrString();
    sRetorno[8] = txtCodTipoFor.getText();
    sRetorno[9] = txtDescTipoFor.getText();
    return sRetorno;
  }
}
