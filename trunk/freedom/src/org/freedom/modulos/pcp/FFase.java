/**
 * @version 25/03/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FFase.java <BR>
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
 * Fases de produ��o
 * 
 */

package org.freedom.modulos.pcp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FFase extends FDados implements ActionListener {
  private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescFase = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  Vector vVals = new Vector();
  Vector vLabs = new Vector();
  private JComboBoxPad cbTipo = new JComboBoxPad(vLabs, vVals, JComboBoxPad.TP_STRING, 3, 0);
  public FFase () {
    setTitulo("Cadastro de fases de produ��o");
    setAtribos( 50, 50, 350, 165);

//  Construindo o combobox de tipo.     

    vVals.addElement("EX"); 
    vVals.addElement("CQ");
    vVals.addElement("EB");
    vLabs.addElement("Execu��o");
    vLabs.addElement("Controle da qualidade");
    vLabs.addElement("Embalagem");
    cbTipo.setItens(vLabs,vVals);
            
    adicCampo(txtCodFase, 7, 20, 70, 20,"CodFase","C�d.fase", ListaCampos.DB_PK, true);
    adicCampo(txtDescFase, 80, 20, 230, 20,"DescFase","Descri��o da fase", ListaCampos.DB_SI, true);
    adicDB(cbTipo,7,60,140,24,"tipoFase","Tipo de fase", true);
    setListaCampos( true, "FASE", "PP");
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    lcCampos.setQueryInsert(false);    
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
    super.actionPerformed(evt);
  }

  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relat�rio de fases de produ��o");
    DLRFase dl = new DLRFase();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODFASE,DESCFASE FROM PPFASE ORDER BY "+dl.getValor();
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
         if (imp.pRow()==0) {
            imp.impCab(80, false);
            imp.say(imp.pRow()+0,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,2,"C�d.fase");
            imp.say(imp.pRow()+0,25,"Descri��o");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",79));
         }
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,2,rs.getString("CodFase"));
         imp.say(imp.pRow()+0,25,rs.getString("DescFase"));
         if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
         }
      }
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",79));
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
        Funcoes.mensagemErro(this,"Erro consulta tabela de fases!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
