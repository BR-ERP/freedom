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
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDetalhe;

public class FSetorAtend extends FDetalhe implements ActionListener {
  private JTextFieldPad txtCodSetAt = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtDescSetAt = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodAtend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldFK txtNomeAtend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcAtend = new ListaCampos(this,"AE");
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  
  public FSetorAtend () {

   setTitulo("Cadastro de Setores de atendimento");
   setAtribos( 50, 50, 450, 350);
/*
   pnMaster.remove(2);  //Remove o JPanelPad pr�definido da class FDados
   pnGImp.removeAll(); //Remove os bot�es de impress�o para adicionar logo embaixo
   pnGImp.setLayout(new GridLayout(1,3)); //redimensiona o painel de impress�o
   pnGImp.setPreferredSize(new Dimension( 210, 26));
   pnGImp.add(btPrevimp);
   pnGImp.add(btImp);
*/      
   setAltCab(90);
   pinCab = new JPanelPad(420,90);
   setListaCampos(lcCampos);
   setPainel( pinCab, pnCliCab);

   lcAtend.add(new GuardaCampo( txtCodAtend, "CodAtend", "C�d.atend.", ListaCampos.DB_PK, true));
   lcAtend.add(new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome do atendente", ListaCampos.DB_SI,false));
   lcAtend.montaSql(false, "ATENDENTE", "AT");
   lcAtend.setQueryCommit(false);
   lcAtend.setReadOnly(true);
   txtCodAtend.setTabelaExterna(lcAtend);
    
   adicCampo(txtCodSetAt, 7, 20, 70, 20,"CodSetAt","C�d.setor",ListaCampos.DB_PK,true);
   adicCampo(txtDescSetAt, 80, 20, 250, 20,"DescSetAt","Descri��o do setor",ListaCampos.DB_SI,true);
   setListaCampos( true, "SETOR", "AT");

   setAltDet(60);
   setPainel( pinDet, pnDet);
   setListaCampos(lcDet);
   setNavegador(navRod);

   adicCampo(txtCodAtend, 7, 20, 70, 20,"CodAtend","C�d.atend.",ListaCampos.DB_PF,txtNomeAtend,true);
   adicDescFK(txtNomeAtend, 80, 20, 250, 20,"NomeAtend","Nome do atendente");
   setListaCampos( false, "SETORATENDENTE", "AT");
   
   montaTab();    
   tab.setTamColuna(70,0); 
   tab.setTamColuna(300,1);

   btImp.addActionListener(this);
   btPrevimp.addActionListener(this);  
   setImprimir(true);
  
  }
    
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
    super.actionPerformed(evt);
  }

  public void setConexao(Connection cn) {
	super.setConexao(cn);
	lcAtend.setConexao(cn);
  }
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relat�rio de Setores da Atendimento");
    DLRSetorAtend dl = new DLRSetorAtend();
    dl.setVisible(true);
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
            imp.impCab(80, false);
            imp.say(imp.pRow()+0,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,2,"C�digo");
            imp.say(imp.pRow()+0,25,"Descri��o");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",79));
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
		Funcoes.mensagemErro(this,"Erro consulta tabela de Setores de Atendimento!\n"+err.getMessage(),true,con,err);      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
