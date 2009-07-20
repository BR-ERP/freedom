/**
 * @version 30/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FObjetoTb.java <BR>
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
import org.freedom.infra.model.jdbc.DbConnection;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FDetalhe;

public class FObjetoTb extends FDetalhe implements InsertListener,ActionListener {
  private static final long serialVersionUID = 1L;
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtIDObj = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JTextFieldPad txtDescObj = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodTb = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDescTb = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JCheckBoxPad cbRequerido = null;  
  private ListaCampos lcTabela = new ListaCampos(this,"TB");
  public FObjetoTb() {
    lcCampos.setUsaFI(false);
	lcDet.setUsaFI(false);

	setTitulo("Vinculo entre tabelas f�sicas e auxiliares");
    setAtribos( 50, 20, 500, 350);
    setAltCab(90);
    pinCab = new JPanelPad(500,90);
    
    txtDescObj.setAtivo(false);
    
	lcTabela.add(new GuardaCampo( txtCodTb, "CODTB",  "C�d.tab.",  ListaCampos.DB_PK, txtDescTb, false));
	lcTabela.add(new GuardaCampo( txtDescTb, "DESCTB", "Descri�ao da tabela", ListaCampos.DB_SI, false));
	lcTabela.setReadOnly(true);
	lcTabela.montaSql(false, "TABELA", "SG");    
	lcTabela.setQueryCommit(false);

	txtCodTb.setTabelaExterna(lcTabela);
    txtCodTb.setNomeCampo("CODTB");    
    
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);  
       
    adicCampo(txtIDObj, 7, 20, 100, 20,"IDObj","Id.obj.",ListaCampos.DB_PK,true);
    adicCampo(txtDescObj, 110, 20, 350, 20, "DescObj","Descri��o do objeto",ListaCampos.DB_SI,true);
    lcCampos.setReadOnly(true);
    lcCampos.setQueryInsert(false);	
    setListaCampos( false, "OBJETO", "SG");
        
    setAltDet(60);
    pinDet = new JPanelPad(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);

	cbRequerido = new JCheckBoxPad("Requerido","S","N");
	cbRequerido.setVlrString("N");

    adicCampo(txtCodTb, 7, 20, 70, 20,"CODTB","C�d.tabela", ListaCampos.DB_PF, txtDescTb, true);
	adicDescFK(txtDescTb, 80, 20, 280, 20, "DESCTB", "Descri��o da tabela");
	adicDB(cbRequerido, 365, 20, 90, 20, "ObrigObjTb", "", true);    
    setListaCampos( false, "OBJETOTB", "SG");
    lcCampos.setQueryInsert(false);
    montaTab();
    
    tab.setTamColuna(50,0);
    tab.setTamColuna(415,1);
    tab.setTamColuna(30,2);
    
    lcCampos.addInsertListener(this);
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
  }
  public void setConexao(DbConnection cn) {
	lcTabela.setConexao(cn);
	super.setConexao(cn);
  }
  
  public void actionPerformed(ActionEvent evt) {
    super.actionPerformed(evt);
  }

   public void afterInsert(InsertEvent ievt) { }
   public void beforeInsert(InsertEvent ievt) { }

}
