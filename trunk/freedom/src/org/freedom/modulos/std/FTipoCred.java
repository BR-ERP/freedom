/**
 * @version 21/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTipoCred.java <BR>
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
 * Tipos para libera��o de cr�dito...
 * 
 */

package org.freedom.modulos.std;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;
public class FTipoCred extends FDados implements ActionListener {
  private JTextFieldPad txtCodTipoCred = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtDescTipoCred = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtVlrTipoCred = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
  public FTipoCred() {
    setTitulo("Cadastro de tipos de credito");
    setAtribos(50, 50, 350, 165);
    adicCampo(txtCodTipoCred, 7, 20, 80, 20,"CodTpCred","C�d.tp.cred.", ListaCampos.DB_PK, true);
    adicCampo(txtDescTipoCred, 90, 20, 240, 20,"DescTpCred","Descri��o do tipo de credito", ListaCampos.DB_SI, true);
	adicCampo(txtVlrTipoCred, 7, 60, 120, 20,"VlrTpCred","Valor", ListaCampos.DB_SI, true);
    setListaCampos( true, "TIPOCRED", "FN");
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
    int iTot = 0;
    imp.montaCab();
    imp.setTitulo("Relat�rio de Tipos de Cr�dito");
    DLRTipoCred dl = new DLRTipoCred();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT TP.CODTPCRED,TP.DESCTPCRED,(SELECT COUNT(CLI.CODCLI) \n"+
                  "FROM VDCLIENTE CLI WHERE CLI.CODTPCRED = TP.CODTPCRED) \n"+
                  "FROM FNTIPOCRED TP ORDER BY TP."+dl.getValor();
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
            imp.say(imp.pRow()+0,2,"C�d.tp.cred.");
            imp.say(imp.pRow()+0,20,"Descri��o");
            imp.say(imp.pRow()+0,70,"Qtd.cli.");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",80));
         }
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,2,rs.getString("CodTpCred"));
         imp.say(imp.pRow()+0,20,rs.getString("DescTpCred"));
         imp.say(imp.pRow()+0,70,Funcoes.alinhaDir(rs.getInt(3),8));
         iTot += rs.getInt(3);
         if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
         }
      }
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",80));
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,"|");
      imp.say(imp.pRow()+0,50,"Total de clientes:");
      imp.say(imp.pRow()+0,71,Funcoes.alinhaDir(iTot,8));
      imp.say(imp.pRow()+0,80,"|");
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
       Funcoes.mensagemErro(this,"Erro na consulta tabela de tipos de tipos de cr�ditos!\n"+err.getMessage());
       err.printStackTrace();      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
