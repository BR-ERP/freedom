/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FSetorAtend.java <BR>
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

package org.freedom.modulos.atd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDetalhe;

public class FSetorAtend extends FDetalhe implements ActionListener {
  private JTextFieldPad txtCodSetAt = new JTextFieldPad(5);
  private JTextFieldPad txtDescSetAt = new JTextFieldPad();
  private JTextFieldPad txtCodAtend = new JTextFieldPad();
  private JTextFieldFK txtNomeAtend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcAtend = new ListaCampos(this,"AE");
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  
  public FSetorAtend () {

   setTitulo("Cadastro de Setores de atendimento");
   setAtribos( 50, 50, 420, 350);
/*
   pnMaster.remove(2);  //Remove o Painel pr�definido da class FDados
   pnGImp.removeAll(); //Remove os bot�es de impress�o para adicionar logo embaixo
   pnGImp.setLayout(new GridLayout(1,3)); //redimensiona o painel de impress�o
   pnGImp.setPreferredSize(new Dimension( 210, 26));
   pnGImp.add(btPrevimp);
   pnGImp.add(btImp);
*/      
   setAltCab(90);
   pinCab = new Painel(420,90);
   setListaCampos(lcCampos);
   setPainel( pinCab, pnCliCab);

   lcAtend.add(new GuardaCampo( txtCodAtend, 7, 100, 80, 20, "CodAtend", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodAtend");
   lcAtend.add(new GuardaCampo( txtNomeAtend, 90, 100, 207, 20, "NomeAtend", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtNomeAtend");
   lcAtend.montaSql(false, "ATENDENTE", "AT");
   lcAtend.setQueryCommit(false);
   lcAtend.setReadOnly(true);
   txtCodAtend.setTabelaExterna(lcAtend);
    
   adicCampo(txtCodSetAt, 7, 20, 50, 20,"CodSetAt","C�digo",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
   adicCampo(txtDescSetAt, 60, 20, 250, 20,"DescSetAt","Descri��o",JTextFieldPad.TP_STRING,50,0,false,false,null,true);
   setListaCampos( true, "SETOR", "AT");

   setAltDet(60);
   setPainel( pinDet, pnDet);
   setListaCampos(lcDet);
   setNavegador(navRod);

   adicCampo(txtCodAtend, 7, 20, 50, 20,"CodAtend","C�digo",JTextFieldPad.TP_INTEGER,10,0,true,true,txtNomeAtend,true);
   adicDescFK(txtNomeAtend, 60, 20, 250, 20,"NomeAtend","Nome do atendente",JTextFieldPad.TP_STRING,100,0);
   setListaCampos( false, "SETORATENDENTE", "AT");
   
   montaTab();    
   tab.setTamColuna(40,0); 
   tab.setTamColuna(300,1);

   btImp.addActionListener(this);
   btPrevimp.addActionListener(this);  
  
  }
    
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
    super.actionPerformed(evt);
  }

  public void execShow(Connection cn) {
	lcAtend.setConexao(cn);
	super.execShow(cn);
  }
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relat�rio de Setores da Atendimento");
    DLRSetorAtend dl = new DLRSetorAtend();
    dl.show();
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODSETAT,DESCSETAT FROM ATSETOR ORDER BY "+dl.getValor();
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
            imp.say(imp.pRow()+0,25,"Descri��o");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",80));
         }
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,2,rs.getString("CodSetAt"));
         imp.say(imp.pRow()+0,25,rs.getString("DescSetAt"));
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
		Funcoes.mensagemErro(this,"Erro consulta tabela de Setores de Atendimento!\n"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
