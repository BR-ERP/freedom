/**
 * @version 16/03/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTabJuros.java <BR>
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
 * 
 */

package org.freedom.modulos.std;
import java.util.Vector;

import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.Painel;
import org.freedom.telas.FDetalhe;

public class FTabJuros extends FDetalhe {
  private Painel pinCab = new Painel(0,90);
  private Painel pinDet = new Painel(0,60);
  private JTextFieldPad txtCodTbJ = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescTbJ = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JComboBoxPad cbTipo = new JComboBoxPad();
  private JTextFieldPad txtAno = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldPad txtMes = new JTextFieldPad(JTextFieldPad.TP_INTEGER,2,0);
  private JTextFieldPad txtPerc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,9,2);
  public FTabJuros() {
    setTitulo("Tabelas de juros");
    setAtribos( 50, 20, 380, 380);
    setAltCab(140);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    
    Vector vLabs = new Vector();
    vLabs.addElement("Di�rio");
    vLabs.addElement("Mensal");
    vLabs.addElement("Bimestral");
    vLabs.addElement("Trimestral");
    vLabs.addElement("Semestral");
    vLabs.addElement("Anual");
    Vector vVals = new Vector();
    vVals.addElement("D");
    vVals.addElement("M");
    vVals.addElement("B");
    vVals.addElement("T");
    vVals.addElement("S");
    vVals.addElement("A");
    cbTipo.setItens(vLabs,vVals);
    
    adicCampo(txtCodTbJ, 7, 20, 80, 20,"CodTbJ","C�d.tb.juros",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);
    adicCampo(txtDescTbJ, 90, 20, 250, 20, "DescTbJ", "Descri��o da tabela", JTextFieldPad.TP_STRING, 50, 0,false,false,null,true);
    adicDB(cbTipo, 7, 60, 150, 30, "TipoTbJ", "Tipo", JTextFieldPad.TP_STRING,true);
    setListaCampos( false, "TBJUROS", "FN");
    lcCampos.setQueryInsert(false);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);

    adicCampo(txtAno, 7, 20, 80, 20,"AnoItTbJ","Ano",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);
    adicCampo(txtMes, 90, 20, 57, 20,"MesItTbJ","Mes",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);
    adicCampo(txtPerc, 150, 20, 90, 20,"PercItTbJ","Perc.",JTextFieldPad.TP_DECIMAL,9,2,false,false,null,true);
    setListaCampos( false, "ITTBJUROS", "FN");
    lcDet.setQueryInsert(false);
    montaTab();
  }
}
