/**
 * @version 25/03/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FFase.java <BR>
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

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JComboBoxPad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FFase extends FDados implements ActionListener {
	private static final long serialVersionUID = 1L;
	
  private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescFase = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  Vector<String> vVals = new Vector<String>();
  Vector<String> vLabs = new Vector<String>();
  private JComboBoxPad cbTipo = new JComboBoxPad(vLabs, vVals, JComboBoxPad.TP_STRING, 3, 0);
  public FFase () {
  	super();
    setTitulo("Cadastro de fases de produ��o");
    setAtribos( 50, 50, 350, 165);

//  Construindo o combobox de tipo.     

    vVals.addElement("");
    vVals.addElement("EX"); 
    vVals.addElement("CQ");
    vVals.addElement("EB");
    vLabs.addElement("<--Selecione-->");
    vLabs.addElement("Execu��o");
    vLabs.addElement("Controle da qualidade");
    vLabs.addElement("Embalagem");
    cbTipo.setItensGeneric(vLabs,vVals);
            
    adicCampo(txtCodFase, 7, 20, 70, 20,"CodFase","C�d.fase", ListaCampos.DB_PK, true);
    adicCampo(txtDescFase, 80, 20, 230, 20,"DescFase","Descri��o da fase", ListaCampos.DB_SI, true);
    adicDB(cbTipo,7,60,230,24,"tipoFase","Tipo de fase", true);
    setListaCampos( true, "FASE", "PP");
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    lcCampos.setQueryInsert(false);
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
            imp.say(imp.pRow()+0,0,StringFunctions.replicate("-",79));
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
      imp.say(imp.pRow()+0,0,StringFunctions.replicate("=",79));
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
        Funcoes.mensagemErro(this,"Erro consulta tabela de fases!"+err.getMessage(),true,con,err);      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
