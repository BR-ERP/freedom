/**
 * @version 11/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FProcesso.java <BR>
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

package org.freedom.modulos.cfg;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDetalhe;
import org.freedom.telas.FPrincipal;

public class FProcesso extends FDetalhe implements ActionListener {
  private JTextFieldPad txtCodProc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtDescProc = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodTar = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTar = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodItem = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private ListaCampos lcTarefa = new ListaCampos(this,"TA");
  private JButton btTrat = new JButton(Icone.novo("btRetorno.gif"));
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  FPrincipal fPrim = null;
  public FProcesso() {

   btTrat.setToolTipText("Tratamento de Retorno");

   setTitulo("Cadastro Processos");
   setAtribos( 50, 50, 450, 350);

   setAltCab(90);
   pinCab = new JPanelPad(420,90);
   setListaCampos(lcCampos);
   setPainel( pinCab, pnCliCab);

   lcTarefa.add(new GuardaCampo( txtCodTar, "CodTarefa", "C�d.tarfa", ListaCampos.DB_PK, true));
   lcTarefa.add(new GuardaCampo( txtDescTar, "DescTarefa", "Descri��o da tarefa", ListaCampos.DB_SI, false));
   lcTarefa.montaSql(false, "TAREFA", "SG");
   lcTarefa.setQueryCommit(false);
   lcTarefa.setReadOnly(true);
   txtCodTar.setTabelaExterna(lcTarefa);
    
   adicCampo(txtCodProc, 7, 20, 70, 20,"CodProc","C�d.proc.", ListaCampos.DB_PK, true);
   adicCampo(txtDescProc, 80, 20, 230, 20,"DescProc","Descri��o do precesso", ListaCampos.DB_SI, true);
   setListaCampos( true, "PROCESSO", "SG");

   setAltDet(60);
   setPainel( pinDet, pnDet);
   setListaCampos(lcDet);
   setNavegador(navRod);

   adicCampo(txtCodItem, 7, 20, 40, 20,"SeqItProc","Item", ListaCampos.DB_PK, true);
   adicCampo(txtCodTar, 50, 20, 77, 20,"CodTarefa","C�d.tarefa", ListaCampos.DB_FK, txtDescTar, true);
   adicDescFK(txtDescTar, 130, 20, 200, 20,"DescTar","Descri��o da tarefa");
   setListaCampos( true, "ITPROCESSO", "SG");
   
   adic(btTrat,340,15,60,30);
   
   montaTab();    
   tab.setTamColuna(40,0); 
   tab.setTamColuna(80,1); 
   tab.setTamColuna(200,2);

   btImp.addActionListener(this);
   btPrevimp.addActionListener(this);  
   btTrat.addActionListener(this);
   
  }
  private void abreTrat() {
	if (fPrim.temTela("Orcamento")==false) {
	  FTratRet tela = new FTratRet(txtCodProc.getVlrInteger().intValue(),txtCodItem.getVlrInteger().intValue());
	  fPrim.criatela("Orcamento",tela,con);
	  tela.setConexao(con);
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
	else if (evt.getSource() == btTrat) 
	  abreTrat();
    super.actionPerformed(evt);
  }

  public void setConexao(Connection cn) {
	lcTarefa.setConexao(cn);
	super.setConexao(cn);
  }
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relat�rio de Processos");
    DLRProcesso dl = new DLRProcesso();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODPROC,DESCPROC FROM ATPROCESSO ORDER BY "+dl.getValor();
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
         imp.say(imp.pRow()+0,2,rs.getString("CodProc"));
         imp.say(imp.pRow()+0,25,rs.getString("DescProc"));
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
		Funcoes.mensagemErro(this,"Erro consulta tabela de Processos!\n"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
  public void setTelaPrim(FPrincipal fP) {
	  fPrim = fP;
  }
}
