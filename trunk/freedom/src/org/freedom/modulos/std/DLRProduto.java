/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRProduto.java <BR>
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
import java.awt.GridLayout;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FFDialogo;

import java.sql.Connection;
import java.util.Vector;
public class DLRProduto extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JRadioGroup rgOrdem = null;
  private JRadioGroup rgModo = null;
  private JPanelPad pnlbSelec = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JPanelPad pnlbSelec2 = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JPanelPad pinSelec = new JPanelPad(172,90);
  private JPanelPad pinSelec2 = new JPanelPad(172,90);
  private JLabelPad lbSelec = new JLabelPad(" Selec�o por descri��o:");
  private JLabelPad lbSelec2 = new JLabelPad(" Selec�o por c�digo:");
  private JLabelPad lbDe = new JLabelPad("De:");
  private JLabelPad lbA = new JLabelPad("�:");
  private JTextFieldPad txtDe = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JTextFieldPad txtA = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JLabelPad lbDe2 = new JLabelPad("De:");
  private JLabelPad lbA2 = new JLabelPad("�:");
  private JTextFieldPad txtDe2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,30,0);
  private JTextFieldPad txtA2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,30,0);
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JCheckBoxPad cbAtivoProd = new JCheckBoxPad("Ativo","S","N");
  private JLabelPad lbAtivoProd = new JLabelPad(" Produtos Ativos:");
  private JLabelPad lbForn = new JLabelPad("Fornecedor");
  private JLabelPad lbMarca = new JLabelPad("C�d.marca");
  private JLabelPad lbDescMarca = new JLabelPad("Descri��o da Marca");
  private JLabelPad lbModo = new JLabelPad("Modo do relat�rio:");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  private Vector vLabsModo = new Vector();
  private Vector vValsModo = new Vector();
  private JLabelPad lbAlmox = new JLabelPad("C�d.almox.");
  private JLabelPad lbDescAlmox = new JLabelPad("Descri��o do almoxarifado");
  private JTextFieldPad txtCodForn = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescForn = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodAlmox = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescAlmox = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtSiglaMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
  private ListaCampos lcAlmox = new ListaCampos(this);
  private ListaCampos lcCodForn = new ListaCampos(this);
  private ListaCampos lcMarca= new ListaCampos(this);
  
  public DLRProduto(Connection cn) {
	
    setTitulo("Relat�rio de Produtos");
    setAtribos(460,460);
    vLabs.addElement("C�digo");
    vLabs.addElement("Descri��o");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");

    vLabsModo.addElement("Resumido");
    vLabsModo.addElement("Completo");
    vValsModo.addElement("R");
    vValsModo.addElement("C");
    rgModo = new JRadioGroup(1,2,vLabsModo,vValsModo);
    rgModo.setVlrString("R");
    
    lcAlmox.add(new GuardaCampo( txtCodAlmox, "CodAlmox", "C�d.almox.", ListaCampos.DB_PK, false));
    lcAlmox.add(new GuardaCampo( txtDescAlmox, "DescAlmox", "Descri��o do almoxarifado", ListaCampos.DB_SI,false));
    lcAlmox.montaSql(false, "ALMOX", "EQ");
    lcAlmox.setReadOnly(true);
    txtCodAlmox.setTabelaExterna(lcAlmox);
    txtCodAlmox.setFK(true);
    txtCodAlmox.setNomeCampo("CodAlmox");

    lcCodForn.add(new GuardaCampo( txtCodForn, "CodFor", "C�d.for.", ListaCampos.DB_PK, false));
    lcCodForn.add(new GuardaCampo( txtDescForn, "RazFor", "Descri��o do fornecedor", ListaCampos.DB_SI, false));
    lcCodForn.montaSql(false, "FORNECED", "CP");
    lcCodForn.setReadOnly(true);
    txtCodForn.setTabelaExterna(lcCodForn);
    txtCodForn.setFK(true);
    txtCodForn.setNomeCampo("CodFor");

    lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "C�d.marca", ListaCampos.DB_PK, false));
    lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca", "Descri��o da marca", ListaCampos.DB_SI, false));
    lcMarca.add(new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI, false));
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setNomeCampo("CodMarca");
    txtCodMarca.setFK(true);
    lcMarca.setReadOnly(true);
    lcMarca.montaSql(false, "MARCA", "EQ"); 
    
    

   
    adic(lbOrdem,7,5,180,20);
    adic(rgOrdem,7,25,240,30);
	adic(lbAtivoProd,250,5,120,15);
	adic(cbAtivoProd,250,25,290,30);
	
	pnlbSelec.add(lbSelec);
    adic(pnlbSelec,10,60,146,15);
    pinSelec.adic(lbDe,7,5,30,20);
    pinSelec.adic(txtDe,7,25,243,20);
    pinSelec.adic(lbA,7,45,30,20);
    pinSelec.adic(txtA,7,65,243,20);
    adic(pinSelec,7,70,260,100);
    
    pnlbSelec2.add(lbSelec2);
    adic(pnlbSelec2,275,60,130,15);
    pinSelec2.adic(lbDe2,7,5,30,20);
    pinSelec2.adic(txtDe2,7,25,153,20);
    pinSelec2.adic(lbA2,7,45,30,20);
    pinSelec2.adic(txtA2,7,65,153,20);
    adic(pinSelec2,270,70,170,100);
    
    adic(lbForn,7,180,80,20);
    adic(txtCodForn,7,200,80,20);
    adic(txtDescForn,90,200,350,20);
    adic(lbModo,7,230,120,20);
    adic(rgModo,7,250, 400,30);
    adic(lbAlmox,7,290,250,20);
    adic(txtCodAlmox,7,310,80,20);
    adic(lbDescAlmox,90,290,250,20);
    adic(txtDescAlmox,90,310,350,20);
    adic (lbMarca,7,330,350,20);
    adic(txtCodMarca,7,350,80,20);
    adic (lbDescMarca,90,330,350,20);
    adic(txtDescMarca,90,350,350,20);
    
    
	lcAlmox.setConexao(cn);
	lcCodForn.setConexao(cn);
	lcMarca.setConexao(cn);
  }
  public String[] getValores() {
    String[] sRetorno = new String[13];
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sRetorno[0] = "CODPROD";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
    sRetorno[0] = "DESCPROD";
    sRetorno[1] = txtDe.getText();
    sRetorno[2] = txtA.getText();
    sRetorno[3] = cbAtivoProd.getVlrString();
    sRetorno[4] = txtCodForn.getVlrString();
    sRetorno[5] = txtDescForn.getVlrString();
    sRetorno[6] = rgModo.getVlrString();
    sRetorno[7] = txtCodAlmox.getText();
    sRetorno[8] = txtDescAlmox.getText();
    sRetorno[9] = txtCodMarca.getVlrString();
    sRetorno[10]= txtDescMarca.getText();
    sRetorno[11] = txtDe2.getText();
    sRetorno[12] = txtA2.getText();
 
    return sRetorno;
  }
}
