/**
 * @version 12/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FTarefa.java <BR>
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
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FTarefa extends FDados implements ActionListener {
  private JTextFieldPad txtCodTarefa = new JTextFieldPad(5);
  private JTextFieldPad txtDescTarefa = new JTextFieldPad(50);
  private JTextFieldPad txtCodObj = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescObj = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private Vector vValsTipo = new Vector();
  private Vector vLabsTipo = new Vector();
  private JComboBoxPad cbTipo = new JComboBoxPad(vLabsTipo,vValsTipo); 
  private ListaCampos lcObjeto = new ListaCampos(this,"OB");
  public FTarefa () {
    setTitulo("Cadastro de tarefas");
    setAtribos( 50, 50, 330, 205);
    
    
//Montando tipos:

	vValsTipo.add("01");
	vValsTipo.add("02");
	vValsTipo.add("03");
	vValsTipo.add("04");
	vValsTipo.add("05");
	vLabsTipo.add("Consulta");
	vLabsTipo.add("Insere");
	vLabsTipo.add("Atualiza");
	vLabsTipo.add("Exclui");
	vLabsTipo.add("Executa");
    cbTipo.setItens(vLabsTipo,vValsTipo);
    
    
    lcObjeto.setUsaFI(false);
	lcObjeto.add(new GuardaCampo( txtCodObj, 7, 100, 80, 20, "CodObj", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodVarGx");
	lcObjeto.add(new GuardaCampo( txtDescObj, 90, 100, 207, 20, "NomeObj", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescVarGx");
	lcObjeto.montaSql(false, "OBJETO", "SG");
	lcObjeto.setQueryCommit(false);
	lcObjeto.setReadOnly(true);
	txtCodObj.setTabelaExterna(lcObjeto);
    
    adicCampo(txtCodTarefa, 7, 20, 50, 20,"CodTarefa","C�digo",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
    adicCampo(txtDescTarefa, 60, 20, 250, 20,"DescTarefa","Descri��o",JTextFieldPad.TP_STRING,50,0,false,false,null,true);
	adicCampo(txtCodObj, 7, 65, 50, 20,"CodObj","C�digo",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescObj,true);
	adicDescFK(txtDescObj, 60, 65, 250, 20,"NomeObj","e nome do objeto",JTextFieldPad.TP_STRING,50,0);
	adicDB(cbTipo, 7, 105, 230, 25, "TipoTarefa", "Tipo",JTextFieldPad.TP_STRING,true);
    setListaCampos( true, "TAREFA", "SG");
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
  public void execShow(Connection cn) {
	lcObjeto.setConexao(cn);
	super.execShow(cn);
  }
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relat�rio de tarefas");
    DLRTarefa dl = new DLRTarefa();
    dl.show();
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODTAREFA,DESCTAREFA FROM SGTAREFA ORDER BY "+dl.getValor();
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
         imp.say(imp.pRow()+0,2,rs.getString("CodTarefa"));
         imp.say(imp.pRow()+0,25,rs.getString("DescTarefa"));
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
		Funcoes.mensagemErro(this,"Erro consulta tabela de tarefas!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
