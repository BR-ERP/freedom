/**
 * @version 30/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: projetos.freeedomcfg <BR>
 * Classe: @(#)FTabela.java <BR>
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

package org.freedom.modulos.cfg.view.frame.crud.detail;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;

public class FTabela extends FDetalhe implements InsertListener,ActionListener {
  private static final long serialVersionUID = 1L;
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodTb = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtDescTb = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtSiglaTb = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtCodItTb = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtDescItTb = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JCheckBoxPad cbPadrao = null;  
  public FTabela() {
    setTitulo("Cadastro de Tabelas auxiliares");
    setAtribos( 50, 20, 500, 350);
    setAltCab(90);
    pinCab = new JPanelPad(500,90);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
       
    adicCampo(txtCodTb, 7, 20, 70, 20,"CodTb","C�d.tab.", ListaCampos.DB_PK, true);
    adicCampo(txtDescTb, 80, 20, 297, 20, "DescTb","Descri��o da tabela", ListaCampos.DB_SI, true);
    adicCampo(txtSiglaTb, 380, 20, 80, 20, "SiglaTb","Sigla", ListaCampos.DB_SI, true);
  
    setListaCampos( true, "TABELA", "SG");
    lcCampos.setQueryInsert(false);
    setAltDet(60);
    pinDet = new JPanelPad(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);

	cbPadrao = new JCheckBoxPad("Padr�o","S","N");
	cbPadrao.setVlrString("N");

    adicCampo(txtCodItTb, 7, 20, 70, 20,"CodItTb","Item", ListaCampos.DB_PK, true);
    adicCampo(txtDescItTb, 80, 20, 280, 20,"DescItTb","Descri��o do item", ListaCampos.DB_SI, true);
	adicDB(cbPadrao, 365, 20, 90, 20, "PadraoIttb", "", true);    
    
    setListaCampos( true, "ITTABELA", "SG");
    lcCampos.setQueryInsert(false);
    montaTab();
    
    tab.setTamColuna(70,0);
    tab.setTamColuna(395,1);
    tab.setTamColuna(20,2);
    
    lcCampos.addInsertListener(this);
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
  }
  public void actionPerformed(ActionEvent evt) {
    super.actionPerformed(evt);
  }

   public void afterInsert(InsertEvent ievt) { }
   public void beforeInsert(InsertEvent ievt) { }

}
