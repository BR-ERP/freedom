/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FImpressora.java <BR>
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
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

import java.util.Vector;
public class FImpressora extends FDados {

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodImp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtDescImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  //private JTextFieldPad txtLinPagImp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtNSerieImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  //private JTextFieldPad txtPortaWinImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  //private JTextFieldPad txtPortaLinImp = new JTextFieldPad(JTextFieldPad.TP_STRING, 60, 0);
  private JTextFieldPad txtCodPapel = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldFK txtDescPapel = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JComboBoxPad cbTipoImp = null;
  private JComboBoxPad cbDestImp = null;
  private Vector<String> vValsDest = new Vector<String>();
  private Vector<String> vLabsDest = new Vector<String>();
  private Vector<Integer> vValsTipo = new Vector<Integer>();
  private Vector<String> vLabsTipo = new Vector<String>();
  private ListaCampos lcPapel = new ListaCampos(this,"PL");
  public FImpressora() {
  	super();
//Remove o painel de impress�o:
    pnRodape.remove(2);
//Constroi a tela FImpressoras:
    setTitulo("Cadastro de impressoras");
    setAtribos( 50, 50, 440, 270);

//Prepara o Combo para alterar o campo txtTipoImp    
    vLabsTipo.addElement("<--Selecione-->");           
    vLabsTipo.addElement("Epson Matricial");           
    vLabsTipo.addElement("HP Desk Jet");           
    vLabsTipo.addElement("HP Laser Jet");           
    vLabsTipo.addElement("Epson Stylus");           
    vLabsTipo.addElement("Epson Laser");           
    vLabsTipo.addElement("Fiscal MP20");           
    vLabsTipo.addElement("Fiscal MP40");     
    vLabsTipo.addElement("OKI ML/320/420/421");
    vLabsTipo.addElement("Argox");
               
    vValsTipo.addElement(new Integer(0));           
    vValsTipo.addElement(new Integer(1));           
    vValsTipo.addElement(new Integer(2));           
    vValsTipo.addElement(new Integer(3));           
    vValsTipo.addElement(new Integer(4));           
    vValsTipo.addElement(new Integer(5));
    vValsTipo.addElement(new Integer(6));
    vValsTipo.addElement(new Integer(7));
    vValsTipo.addElement(new Integer(8));
    vValsTipo.addElement(new Integer(9));
    
    cbTipoImp = new JComboBoxPad(vLabsTipo, vValsTipo, JComboBoxPad.TP_INTEGER, 9, 0); 
    
    vLabsDest.addElement("<--Selecione-->");           
    vLabsDest.addElement("Nota fiscal");           
    vLabsDest.addElement("Nota fiscal - servi�o");           
    vLabsDest.addElement("Pedido");           
    vLabsDest.addElement("Relat�rio simples");           
    vLabsDest.addElement("Relat�rio gr�fico");   
    vLabsDest.addElement("Etiquetas"); 
    vLabsDest.addElement("Todos (n�o NF)");     
               
    vValsDest.addElement("");           
    vValsDest.addElement("NF");           
    vValsDest.addElement("NS");           
    vValsDest.addElement("PD");           
    vValsDest.addElement("RS");           
    vValsDest.addElement("RG");  
    vValsDest.addElement("ET"); 
    vValsDest.addElement("TO");    
    
    
    cbDestImp = new JComboBoxPad(vLabsDest,vValsDest, JComboBoxPad.TP_STRING, 2, 0);
  

    lcPapel.add(new GuardaCampo( txtCodPapel, "CodPapel", "C�d.tp.papel", ListaCampos.DB_PK, true));
    lcPapel.add(new GuardaCampo( txtDescPapel, "DescPapel", "Descri��o do tipo de papel", ListaCampos.DB_SI, false));
    lcPapel.montaSql(false, "PAPEL", "SG");    
    lcPapel.setQueryCommit(false);
    lcPapel.setReadOnly(true);
    txtCodPapel.setTabelaExterna(lcPapel, FPapel.class.getCanonicalName());
   
    adicCampo(txtCodImp, 7, 20, 90, 20, "CodImp", "C�d.imp.", ListaCampos.DB_PK, true);
    adicCampo(txtDescImp, 100, 20, 276, 20, "DescImp", "Descri��o da impressora", ListaCampos.DB_SI, true);
    adicDB(cbTipoImp,7,60,140,25,"TipoImp", "Tipo de impressora", true);
    adicDB(cbDestImp, 150, 60, 140, 25, "DestImp", "Padr�o para",true);
    //adicCampo(txtLinPagImp, 7, 105, 90, 20, "LinPagImp", "Lin.pag.imp.", ListaCampos.DB_SI, true);
    adicCampo(txtNSerieImp, 293, 60, 90, 25, "NSerieImp", "Num. serie", ListaCampos.DB_SI, false);
    //adicCampo(txtPortaWinImp, 193, 105, 90, 20, "PortaWinImp", "Porta WIN", ListaCampos.DB_SI, true);
    //adicCampo(txtPortaLinImp, 286, 105, 90, 20, "PortaLinImp", "Nome LIN", ListaCampos.DB_SI, true);
    adicCampo(txtCodPapel, 7, 105, 90, 20, "CodPapel", "C�d.tp.papel", ListaCampos.DB_FK, true);
    adicDescFK(txtDescPapel, 100, 105, 276, 20, "DescPapel", "Descri��o do tipo de papel");

    setListaCampos(true, "IMPRESSORA", "SG");
  }
  public void setConexao(DbConnection cn) {
    super.setConexao(cn);
    lcPapel.setConexao(cn);      
  }
}
