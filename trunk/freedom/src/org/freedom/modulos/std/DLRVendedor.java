/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRCliente.java <BR>
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
 * Tela de op��es para o relat�rio de vendedores.
 */

package org.freedom.modulos.std;
import java.awt.Component;
import java.awt.GridLayout;
import java.sql.Connection;
import java.util.Vector;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FFDialogo;

public class DLRVendedor extends FFDialogo {
  private JRadioGroup rgOrdem = null;
//  private JPanelPad pnlbSelec = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
//  private JPanelPad pinSelec = new JPanelPad(400,90);
  private JTextFieldPad txtCid = new JTextFieldPad();
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JLabelPad lbCid = new JLabelPad("Cidade");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  private JLabelPad lbSetor = new JLabelPad("C�d.setor");
  private JLabelPad lbDescSetor = new JLabelPad("Descri��o do setor");
  private JLabelPad lbCodFuncaoVend = new JLabelPad("C�d.Funcao");
  private JLabelPad lbDescFuncaoVend = new JLabelPad("Descri��o do Funcao do vendedor");
  private JLabelPad lbVendedor = new JLabelPad("C�d.repr.");
  private JLabelPad lbNomeVendedor = new JLabelPad("Nome do representante");
  private JLabelPad lbCodClasComi = new JLabelPad("C�d.Clas.Comi.");
  private JLabelPad lbDescClasComi = new JLabelPad("Descri��o da classifica��o de comiss�o");

  private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodFuncaoVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodFunc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK txtDescFunc = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private ListaCampos lcSetor = new ListaCampos(this);
  private ListaCampos lcVendedor = new ListaCampos(this);
  private ListaCampos lcFuncao = new ListaCampos(this,"FU");
  private ListaCampos lcClComis = new ListaCampos(this,"CC");
  private JTextFieldPad txtCodClComis = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldFK txtDescClComis = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);


  public DLRVendedor(Component cOrig, Connection cn) {
  	super(cOrig);
    setTitulo("Relat�rio de Comissionados");
    setAtribos(460,475);
    vLabs.addElement("C�digo");
    vLabs.addElement("Nome");
    vVals.addElement("C");
    vVals.addElement("N");

    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("C");

    lcClComis.add(new GuardaCampo( txtCodClComis, "CodClComis", "C�d.cl.comis.", ListaCampos.DB_PK, txtDescClComis, false));
    lcClComis.add(new GuardaCampo( txtDescClComis, "DescClComis", "Descri�ao da classifica��o da comiss�o", ListaCampos.DB_SI, false));
    lcClComis.montaSql(false, "CLCOMIS", "VD");    
    lcClComis.setQueryCommit(false);
    lcClComis.setReadOnly(true);
    txtCodClComis.setTabelaExterna(lcClComis);
    txtCodClComis.setFK(true);
    txtCodClComis.setNomeCampo("CodClComis");

    
    lcSetor.add(new GuardaCampo( txtCodSetor, "CodSetor", "C�d.setor", ListaCampos.DB_PK,false));
    lcSetor.add(new GuardaCampo( txtDescSetor, "DescSetor", "Descri��o do setor",  ListaCampos.DB_SI,false));
    lcSetor.montaSql(false, "SETOR", "VD");
    lcSetor.setReadOnly(true);
    txtCodSetor.setTabelaExterna(lcSetor);
    txtCodSetor.setFK(true);
    txtCodSetor.setNomeCampo("CodSetor");

    lcFuncao.add(new GuardaCampo( txtCodFunc, "CodFunc", "C�d.fun��o", ListaCampos.DB_PK, txtDescFunc, false));
    lcFuncao.add(new GuardaCampo( txtDescFunc, "DescFunc", "Descri�ao da fun��o", ListaCampos.DB_SI, false));
    lcFuncao.montaSql(false, "FUNCAO", "RH");    
    lcFuncao.setQueryCommit(false);
    lcFuncao.setReadOnly(true);
    txtCodFunc.setTabelaExterna(lcFuncao);
    txtCodFunc.setFK(true);
    txtCodFunc.setNomeCampo("CodFunc");

    

    lcVendedor.add(new GuardaCampo( txtCodVend, "CodVend", "C�d.repr.",  ListaCampos.DB_PK,false));
    lcVendedor.add(new GuardaCampo( txtNomeVend, "NomeVend", "Nome do representante",  ListaCampos.DB_SI,false));
    lcVendedor.montaSql(false, "VENDEDOR", "VD");
    lcVendedor.setReadOnly(true);
    txtCodVend.setTabelaExterna(lcVendedor);
    txtCodVend.setFK(true);
    txtCodVend.setNomeCampo("CodVend");
    

    adic(lbOrdem,7,5,180,20);
    adic(rgOrdem,7,25,350,30);
//    adic(pnlbSelec,10,63,80,15);
//    adic(pinSelec,7,70,433,70);
    adic(lbCid,7,65,140,20);
    adic(txtCid,7,85,250,20);
    adic(lbSetor,7,105,250,20);
    adic(txtCodSetor,7,125,80,20);
    adic(lbDescSetor,90,105,250,20);
    adic(txtDescSetor,90,125,350,20);
    adic(lbVendedor,7,145,300,20);
    adic(txtCodVend,7,165,80,20);
    adic(lbNomeVendedor,90,145,300,20);
    adic(txtNomeVend,90,165,350,20);
    adic(lbCodFuncaoVend,7,185,300,20);
    adic(txtCodFunc,7,205,80,20);
    adic(lbDescFuncaoVend,90,185,300,20);
    adic(txtDescFunc,90,205,350,20);

    adic(lbCodClasComi,7,225,300,20);
    adic(txtCodClComis,7,245,80,20);
    adic(lbDescClasComi,90,225,300,20);
    adic(txtDescClComis,90,245,350,20);

    
	lcSetor.setConexao(cn);
	lcFuncao.setConexao(cn);
	lcVendedor.setConexao(cn);
	lcClComis.setConexao(cn);
  }
  public String[] getValores() {
    String[] sRetorno = new String[15];
    if (rgOrdem.getVlrString().equals("C"))
      sRetorno[0] = "VD.CODVEND, VD.NOMEVEND";
    else if (rgOrdem.getVlrString().equals("N"))
      sRetorno[0] = "VD.NOMEVEND,VD.CODVEND";
    sRetorno[1] = txtCid.getText();        
    sRetorno[2] = txtCodClComis.getVlrString();
    sRetorno[3] = txtCodSetor.getText();
    sRetorno[4] = txtCodFuncaoVend.getText();
    sRetorno[5] = txtCodVend.getText();    

    return sRetorno;
  }
}
