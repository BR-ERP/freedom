/**
 * @version 17/05/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCLComis.java <BR>
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;


public class FCLComis extends FDados implements PostListener { 
  private JTextFieldPad txtCodClComis = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
  private JTextFieldPad txtDescClComis = new JTextFieldPad(JTextFieldPad.TP_STRING,60,0);
  private JTextFieldPad txtPercFatClComis = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 9, 2);
  private JTextFieldPad txtPercPgtoComis = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 9, 2);
  
  private ListaCampos lcClComis = new ListaCampos(this,"CL");
  
  
  public FCLComis () {
    setTitulo("Cadastro de Classifica��o de Comiss�es");
    setAtribos( 50, 50, 400, 205);

    setListaCampos( true, "CLCOMIS", "VD");
    
    lcClComis.add(new GuardaCampo( txtCodClComis,"CodClComis", "C�d.c.comis.", ListaCampos.DB_PK, null,false));
    lcClComis.add(new GuardaCampo( txtDescClComis,"DescClComis", "Descri�ao da classifica��o da comiss�o", ListaCampos.DB_SI, null,false));
    lcClComis.montaSql(false, "VDCLCOMIS", "CL");    
    lcClComis.setQueryCommit(false);
    lcClComis.setReadOnly(true);
    
    JLabel lbDistriCom = new JLabel(" Distribui��o de Comiss�es ");
	lbDistriCom.setOpaque(true);
	JLabel lbLinha = new JLabel();
	lbLinha.setBorder(BorderFactory.createEtchedBorder());
    
    
	
    adicCampo(txtCodClComis, 7, 20, 80, 20, "CodClComis", "C�d.c.comis.",  ListaCampos.DB_PK, null, true);
    adicCampo(txtDescClComis, 90, 20, 250, 20, "DescClComis", "Descri��o da classifica��o da comiss�o", ListaCampos.DB_SI, null, true);
    
    adic(lbDistriCom,7,50,250,20);
    adic(lbLinha,7,72,250,2);
    
    adicCampo(txtPercFatClComis, 7, 100, 120, 20, "PercFatClComis", "% S/Faturamento",  ListaCampos.DB_SI, null, true);
    adicCampo(txtPercPgtoComis, 140, 100, 120, 20, "PercPgtoClComis", "% S/Recebimento.", ListaCampos.DB_SI, null, true);
        
    setListaCampos( true, "CLCOMIS", "VD");
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    
  }
  
  
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp)
        imprimir(true);
    else if (evt.getSource() == btImp) 
      imprimir(false);

    super.actionPerformed(evt);
  }
  
  
  
private void imprimir(boolean bVisualizar) {
  ImprimeOS imp = new ImprimeOS("",con);
  int linPag = imp.verifLinPag()-1;
  imp.montaCab();
  imp.setTitulo("Relat�rio de Classifica��o de Comiss�es");
  DLRClComis dl = new DLRClComis();
  dl.setVisible(true);
  if (dl.OK == false) {
    dl.dispose();
    return;
  }
  String sSQL = "SELECT CODCLCOMIS,DESCCLCOMIS,PERCFATCLCOMIS, PERCPGTOCLCOMIS FROM VDCLCOMIS ORDER BY "+dl.getValor();
  PreparedStatement ps = null;
  ResultSet rs = null;

  try {
    ps = con.prepareStatement(sSQL);
    rs = ps.executeQuery();
    imp.limpaPags();
    while ( rs.next() ) {
       if (imp.pRow()==0) {
          imp.impCab(136);
          imp.say(imp.pRow()+0,0,""+imp.normal());
          imp.say(imp.pRow()+0,0,"");
          imp.say(imp.pRow()+0,0,"|");
          imp.say(imp.pRow()+0,2,"C�digo");
          imp.say(imp.pRow()+0,12,"|");
          imp.say(imp.pRow()+0,14,"Descri��o");
          imp.say(imp.pRow()+0,76,"|");
          imp.say(imp.pRow()+0,80,"Perc.Fat.");
          imp.say(imp.pRow()+0,100,"|");
          imp.say(imp.pRow()+0,108,"Perc.Receb.");
          imp.say(imp.pRow()+0,136,"|");
          imp.say(imp.pRow()+1,0,""+imp.normal());
          imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
       }
                
       
       imp.say(imp.pRow()+1,0,""+imp.normal());
       imp.say(imp.pRow()+0,0,"|");
       imp.say(imp.pRow()+0,2,rs.getString("CodClComis"));
       imp.say(imp.pRow()+0,12,"|");
       imp.say(imp.pRow()+0,14,rs.getString("DescClComis"));
       imp.say(imp.pRow()+0,76,"|");
       imp.say(imp.pRow()+0,80,rs.getString("percFatClComis"));
       imp.say(imp.pRow()+0,100,"|");
       imp.say(imp.pRow()+0,108,rs.getString("PercPgtoClComis"));
       imp.say(imp.pRow()+0,136,"|");
       
       if (imp.pRow()>=linPag) {
          imp.incPags();
          imp.eject();
       }
    }
    
    imp.say(imp.pRow()+1,0,""+imp.normal());
    imp.say(imp.pRow()+0,0,Funcoes.replicate("=",136));
    imp.eject();
    
    imp.fechaGravacao();
    
//    rs.close();
//    ps.close();
    if (!con.getAutoCommit())
    	con.commit();
    dl.dispose();
  }  
  catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela de class.comiss�o!"+err.getMessage());      
  }
  
  if (bVisualizar) {
    imp.preview(this);
  }
  else {
    imp.print();
  }
}
  
  
  public void setConexao(Connection cn) {
	super.setConexao(cn);
    lcClComis.setConexao(cn);
  }
}
