/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FImpressora.java <BR>
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JComboBox;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

import java.util.Vector;
public class FImpressora extends FDados {
  private JTextFieldPad txtCodImp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtDescImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtTipoImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private JTextFieldPad txtLinPagImp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtNSerieImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 15, 0);
  private JTextFieldPad txtPortaWinImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtPortaLinImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 60, 0);
  private JTextFieldPad txtCodPapel = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldFK txtDescPapel = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JComboBox cbTipoImp = new JComboBox();
  private JComboBoxPad cbDestImp = null;
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private ListaCampos lcPapel = new ListaCampos(this,"PL");
  public FImpressora() {
//Remove o painel de impress�o:
    pnRodape.remove(2);
//Constroi a tela FImpressoras:
    setTitulo("Cadastro de impressoras");
    setAtribos( 50, 50, 400, 280);

//Prepara o Combo para alterar o campo txtTipoImp    
    cbTipoImp.addItem("");
    cbTipoImp.addItem("Epson Matricial");
    cbTipoImp.addItem("HP Desk Jet");
    cbTipoImp.addItem("HP Laser Jet");
    cbTipoImp.addItem("Epson Stylus");
    cbTipoImp.addItem("Epson Laser");
    cbTipoImp.addItem("Fiscal MP20");
    cbTipoImp.addItem("Fiscal MP40");
    cbTipoImp.setEditable(false);
    cbTipoImp.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          if (evt.getSource() == cbTipoImp) {
            if (((JComboBox) evt.getSource()).getSelectedIndex() == 0) {
              txtTipoImp.setVlrString("");
            }  
            else {
              txtTipoImp.setVlrString(""+((JComboBox) evt.getSource()).getSelectedIndex());
            }
          }
        }
      }
    );
    
    vLabs.addElement("Nota fiscal");           
    vLabs.addElement("Nota fiscal - servi�o");           
    vLabs.addElement("Pedido");           
    vLabs.addElement("Relat�rio simples");           
    vLabs.addElement("Relat�rio gr�fico");           
    vLabs.addElement("Todos (n�o NF)");           
               
    vVals.addElement("NF");           
    vVals.addElement("NS");           
    vVals.addElement("PD");           
    vVals.addElement("RS");           
    vVals.addElement("RG");           
    vVals.addElement("TO");           
    
    cbDestImp = new JComboBoxPad(vLabs,vVals);
    cbDestImp.setVlrString("TO");

    txtTipoImp.setEditable(false);
    txtTipoImp.addActionListener( 
      new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          if (evt.getSource() == txtTipoImp) {
            if (txtTipoImp.getText().trim().length() == 1) {
              cbTipoImp.setSelectedIndex(Integer.parseInt(txtTipoImp.getText().trim()));
            }
            else 
              cbTipoImp.setSelectedIndex(0);
          }
        }
      }
    );
//Prepara FKs

    lcPapel.add(new GuardaCampo( txtCodPapel, "CodPapel", "C�d.tp.papel", ListaCampos.DB_PK, true));
    lcPapel.add(new GuardaCampo( txtDescPapel, "DescPapel", "Descri��o do tipo de papel", ListaCampos.DB_SI, false));
    lcPapel.montaSql(false, "PAPEL", "SG");    
    lcPapel.setQueryCommit(false);
    lcPapel.setReadOnly(true);
    txtCodPapel.setTabelaExterna(lcPapel);
//Adiciona componentes   
    adicCampo(txtCodImp, 7, 20, 90, 20, "CodImp", "C�d.imp.", ListaCampos.DB_PK, true);
    adicCampo(txtDescImp, 100, 20, 276, 20, "DescImp", "Descri��o da impressora", ListaCampos.DB_SI, true);
    adicCampo(txtTipoImp, 7, 60, 90, 20, "TipoImp", "Tp.impressora", ListaCampos.DB_SI, true);
    pinDados.adic(cbTipoImp,100,60,276,20);
    adicCampo(txtLinPagImp, 7, 100, 90, 20, "LinPagImp", "Lin.pag.imp.", ListaCampos.DB_SI, true);
    adicCampo(txtNSerieImp, 100, 100, 90, 20, "NSerieImp", "Num. serie", ListaCampos.DB_SI, false);
    adicCampo(txtPortaWinImp, 193, 100, 90, 20, "PortaWinImp", "Porta WIN", ListaCampos.DB_SI, true);
    adicCampo(txtPortaLinImp, 286, 100, 90, 20, "PortaLinImp", "Nome LIN", ListaCampos.DB_SI, true);
    adicCampo(txtCodPapel, 7, 140, 90, 20, "CodPapel", "C�d.tp.papel", ListaCampos.DB_FK, true);
    adicDescFK(txtDescPapel, 100, 140, 276, 20, "DescPapel", "Descri��o do tipo de papel");
    adicDB(cbDestImp, 7, 180, 200, 25, "DestImp", "Padr�o para",JTextFieldPad.TP_STRING,true);

    setListaCampos(true, "IMPRESSORA", "SG");
  }
  public void execShow(Connection cn) {
    lcPapel.setConexao(cn);      
    super.execShow(cn);
  }
}
