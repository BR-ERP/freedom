/**
 * @version 11/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FFluxo.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.cfg;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.ImprimeOS;
import org.freedom.library.JPanelPad;
import org.freedom.library.JTextFieldFK;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;
import org.freedom.telas.FDetalhe;

public class FFluxo extends FDetalhe implements ActionListener {
  private static final long serialVersionUID = 1L;
  private JTextFieldPad txtCodFluxo = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtDescFluxo = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodProc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescProc = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodItem = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private ListaCampos lcProc = new ListaCampos(this,"PC");
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  
  public FFluxo () {

   setTitulo("Cadastro Fluxos");
   setAtribos( 50, 50, 450, 350);

   setAltCab(90);
   pinCab = new JPanelPad(420,90);
   setListaCampos(lcCampos);
   setPainel( pinCab, pnCliCab);

   lcProc.add(new GuardaCampo( txtCodProc, "CodProc", "C�d.proc.", ListaCampos.DB_PK, true));
   lcProc.add(new GuardaCampo( txtDescProc, "DescProc", "Descri��o do processo", ListaCampos.DB_SI, false));
   lcProc.montaSql(false, "PROCESSO", "SG");
   lcProc.setQueryCommit(false);
   lcProc.setReadOnly(true);
   txtCodProc.setTabelaExterna(lcProc);
    
   adicCampo(txtCodFluxo, 7, 20, 70, 20,"CodFluxo","C�d.fluxo", ListaCampos.DB_PK, true);
   adicCampo(txtDescFluxo, 80, 20, 230, 20,"DescFluxo","Descri��o do fluxo", ListaCampos.DB_SI, true);
   setListaCampos( true, "FLUXO", "SG");

   setAltDet(60);
   setPainel( pinDet, pnDet);
   setListaCampos(lcDet);
   setNavegador(navRod);

   adicCampo(txtCodItem, 7, 20, 40, 20,"SeqItFluxo","Item", ListaCampos.DB_PK, true);
   adicCampo(txtCodProc, 50, 20, 70, 20,"CodProc","C�d.proc.", ListaCampos.DB_FK, txtDescProc, true);
   adicDescFK(txtDescProc, 123, 20, 230, 20,"DescProc","Descri��o do processo");
   setListaCampos( true, "ITFLUXO", "SG");
   
   montaTab();    
   tab.setTamColuna(40,0); 
   tab.setTamColuna(45,1); 
   tab.setTamColuna(350,2);

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

  public void setConexao(DbConnection cn) {
	super.setConexao(cn);
	lcProc.setConexao(cn);
  }
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relat�rio de Fluxos");
    DLRFluxo dl = new DLRFluxo(this);
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODFLUXO,DESCFLUXO FROM ATFLUXO ORDER BY "+dl.getValor();
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
            imp.say(imp.pRow()+0,0,StringFunctions.replicate("-",79));
         }
         imp.say(imp.pRow()+1,0,""+imp.normal());
         imp.say(imp.pRow()+0,2,rs.getString("CodFluxo"));
         imp.say(imp.pRow()+0,25,rs.getString("DescFluxo"));
         if (imp.pRow()>=linPag) {
            imp.incPags();
            imp.eject();
         }
      }
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,StringFunctions.replicate("=",79));
      imp.eject();
      
      imp.fechaGravacao();
      con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de fluxos!\n"+err.getMessage(),true,con,err);      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
