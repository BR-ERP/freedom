/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTipoCob.java <BR>
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FTipoCob extends FDados implements ActionListener {
  private JTextFieldPad txtCodTipoCob = new JTextFieldPad(5);
  private JTextFieldPad txtDescTipoCob = new JTextFieldPad(40);
  private JTextFieldPad txtDuplCob = new JTextFieldPad(8);
  public FTipoCob () {
    setTitulo("Cadastro de Tipo de Cobran�a");
    setAtribos( 50, 50, 370, 125);
    adicCampo(txtCodTipoCob, 7, 20, 50, 20,"CodTipoCob","C�digo",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
    adicCampo(txtDescTipoCob, 60, 20, 200, 20,"DescTipoCob","Descri��o",JTextFieldPad.TP_STRING,40,0,false,false,null,true);
    adicCampo(txtDuplCob, 263, 20, 80, 20,"DuplCob","Duplicata",JTextFieldPad.TP_STRING,8,0,false,false,null,false);
    setListaCampos( true, "TIPOCOB", "FN");
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
    imp.setTitulo("Relat�rio de Tipos de Cobran�a");
    DLRTipoCob dl = new DLRTipoCob();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODTIPOCOB,DESCTIPOCOB,DUPLCOB FROM FNTIPOCOB ORDER BY "+dl.getValor();
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
         if (imp.pRow()==0) {
            imp.impCab(80);
            imp.say(imp.pRow()+0,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,2,"C�digo");
            imp.say(imp.pRow()+0,20,"Descri��o");
            imp.say(imp.pRow()+0,60,"Duplicata");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",80));
         }
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,2,rs.getString("CodTipoCob"));
         imp.say(imp.pRow()+0,20,rs.getString("DescTipoCob"));
         imp.say(imp.pRow()+0,60,rs.getString("DuplCob") != null ? rs.getString("DuplCob") : "");
         if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
         }
      }
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",80));
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de tipos de cobran�a!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
