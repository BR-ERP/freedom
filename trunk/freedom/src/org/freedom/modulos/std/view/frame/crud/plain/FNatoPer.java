/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FNatoPer.java <BR>
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

package org.freedom.modulos.std.view.frame.crud.plain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.std.view.dialog.report.DLRNatOper;
import org.freedom.modulos.std.view.frame.crud.detail.FItNatoper;

public class FNatoPer extends FDados implements ActionListener, InsertListener {
	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodNat = new JTextFieldPad(JTextFieldPad.TP_STRING,5,0);
  private JTextFieldPad txtDescNat = new JTextFieldPad(JTextFieldPad.TP_STRING,60,0);
  private JTextFieldPad txtAliqeNat = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,6,2);
  private JTextFieldPad txtAliqfNat = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,6,2);
  private JTextAreaPad txaTxtNat = new JTextAreaPad(500);
  private JCheckBoxPad cbImpDtSaidaNat = new JCheckBoxPad("Imprimir data de sa�da na NF?","S","N");
  private JButtonPad btItNatoper = new JButtonPad(Icone.novo("btBrasil.gif"));
  public FNatoPer() {
  	super();
  	//cbImpDtSaidaNat.set
    setTitulo("Naturezas de Opre��o (CFOP)");
    setAtribos( 50, 50, 365, 285);
    adicCampo(txtCodNat, 7, 20, 70, 20,"CodNat","C�d.nat.op.",ListaCampos.DB_PK,true);
    adicCampo(txtDescNat, 80, 20, 230, 20,"DescNat","Descri��o da natureza da opera��o",ListaCampos.DB_SI,true);
    adicCampo(txtAliqeNat, 7, 60, 90, 20,"AliqENat","Aliq.estadual",ListaCampos.DB_SI,false);
    adicCampo(txtAliqfNat, 100, 60, 90, 20,"AliqFNat","Aliq.federal",ListaCampos.DB_SI,false);
    adicDB(cbImpDtSaidaNat, 7, 90, 250,20,"ImpDtSaidaNat","",true);
    adicDB(txaTxtNat,7,130,250,50,"txtNat","Texto completo",false);
    //adicCampo(txaTxtNat,7,130,250,50,"txtNat","Texto completo",JTextFieldPad.TP_STRING,false);
    
    
	adic(btItNatoper,200,48,110,40);

    txtCodNat.setStrMascara("#.###");
    
    setListaCampos( false, "NATOPER", "LF");
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    btItNatoper.addActionListener(this);
    lcCampos.setQueryInsert(false);
    lcCampos.addInsertListener(this);
    setImprimir(true);
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btPrevimp)
        imprimir(true);
    else if (evt.getSource() == btImp) 
      imprimir(false);
	else if (evt.getSource() == btItNatoper && lcCampos.getStatus() == ListaCampos.LCS_SELECT) {
	  abreItNatoper();    	
	}
	super.actionPerformed(evt);
  }
  private void abreItNatoper() {
	  if (!fPrim.temTela("Item Natoper")) {
		FItNatoper tela = new FItNatoper();
		fPrim.criatela("Item Natoper",tela,con);
		tela.exec(txtCodNat.getVlrString());
	  } 
  }
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    
    DLRNatOper dl = new DLRNatOper();
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    String sSQL = "SELECT CODNAT,DESCNAT,ALIQENAT,ALIQFNAT FROM LFNATOPER ORDER BY "+dl.getValor();
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sAliqe = "";
    String sAliqf = "";
    char[] cAliqe= new char[9];
    char[] cAliqf= new char[9];
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      imp.montaCab();
      imp.setTitulo("Relat�rio de naturezas de opera��es");
      imp.addSubTitulo("Relat�rio de naturezas de opera��es");
      while ( rs.next() ) {
      	 if (imp.pRow()>=linPag) {
         	imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"+"+StringFunctions.replicate("-",133)+"+");
            imp.incPags();
            imp.eject();
         }
         if (imp.pRow()==0) {         	
            imp.impCab(136, true);
            
            imp.say(imp.pRow()+0,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,0,"|");
            imp.say(imp.pRow()+0,2,"C�d.nat.op.");
            imp.say(imp.pRow()+0,12,"|");
            imp.say(imp.pRow()+0,14,"Descri��o da naturaza da opera��o");
            imp.say(imp.pRow()+0,76,"|");
            imp.say(imp.pRow()+0,78,"Aliq.estadual");
            imp.say(imp.pRow()+0,102,"|");
            imp.say(imp.pRow()+0,108,"Aliq.federal");
            imp.say(imp.pRow()+0,135,"|");
            imp.say(imp.pRow()+1,0,""+imp.normal());
            imp.say(imp.pRow()+0,0,"|"+StringFunctions.replicate("-",133)+"|");
         }
                  
         sAliqe = rs.getString("AliqeNat") != null ? rs.getString("AliqeNat") : "";
         if ((sAliqe.length() > 0) & (sAliqe.indexOf('.') != -1)) {
           cAliqe = sAliqe.toCharArray();
           cAliqe[sAliqe.indexOf('.')] = ',';
           sAliqe = new String(cAliqe);           
         }
         
         sAliqf = rs.getString("AliqfNat") != null ? rs.getString("AliqfNat") : "";
         if ((sAliqf.length() > 0) & (sAliqf.indexOf('.') != -1)) {
           cAliqf = sAliqf.toCharArray();
           cAliqf[sAliqf.indexOf('.')] = ',';
           sAliqf = new String(cAliqf);           
         }
         
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"|");
         imp.say(imp.pRow()+0,2,Funcoes.setMascara(rs.getString("CodNat"),"#.###"));
         imp.say(imp.pRow()+0,12,"|");
         imp.say(imp.pRow()+0,14,rs.getString("DescNat"));
         imp.say(imp.pRow()+0,76,"|");
         imp.say(imp.pRow()+0,78,sAliqe);
         imp.say(imp.pRow()+0,102,"|");
         imp.say(imp.pRow()+0,108,sAliqf);
         imp.say(imp.pRow()+0,135,"|");
                
      }
      
      imp.say(imp.pRow()+1,0,""+imp.normal());
      imp.say(imp.pRow()+0,0,"+"+StringFunctions.replicate("-",133)+"+");
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      con.commit();
      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela de natureza de opera��es!\n"+err.getMessage(),true,con,err);      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
/* (non-Javadoc)
 * @see org.freedom.acao.InsertListener#beforeInsert(org.freedom.acao.InsertEvent)
 */
  public void afterInsert(InsertEvent ievt) {
	cbImpDtSaidaNat.setVlrString("S");
  }
  public void beforeInsert(InsertEvent ievt) { }
}
