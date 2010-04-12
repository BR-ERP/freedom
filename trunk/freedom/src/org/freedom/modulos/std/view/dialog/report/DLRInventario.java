/**
 * @version 11/11/2004 <BR>
 * @author Setpoint Inform�tica Ltda. Robson Sanchez e Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRInventario.java <BR>
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
 */

package org.freedom.modulos.std.view.dialog.report;
import java.awt.Component;
import java.awt.event.ActionEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;

import java.util.GregorianCalendar;
import java.util.Vector;


import org.freedom.funcoes.Funcoes;

public class DLRInventario extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JCheckBoxPad cbGrupo = new JCheckBoxPad("Dividir por grupo","S","N");
  private JRadioGroup<?, ?> rgOrdem = null;
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodAlmox = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
  private JTextFieldFK txtDescAlmox = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JLabelPad lbGrup = new JLabelPad("C�d.grupo");
  private JLabelPad lbDescGrup = new JLabelPad("Descri��o do grupo");
  private JLabelPad lbCodAlmox = new JLabelPad("C�d.Almox");
  private JLabelPad lbDescAlmox = new JLabelPad("Desc.Almoxarifado");  
  private Vector<String> vLabs = new Vector<String>();
  private Vector<String> vVals = new Vector<String>();
  private JTextFieldPad txtData = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcAlmox = new ListaCampos(this);

  public DLRInventario(DbConnection cn,Component cOrig) {
  	super(cOrig);
    setTitulo("Posi��o do estoque");
    setAtribos(310, 310);
    vLabs.addElement("C�digo");
    vLabs.addElement("Descri��o");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");
    
    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK,false));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false));
    lcGrup.montaSql(false, "GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtCodGrup.setNomeCampo("CodGrup");
    lcGrup.setConexao(cn);
    
	lcAlmox.add( new GuardaCampo( txtCodAlmox, "CodAlmox", "C�d.almox.", ListaCampos.DB_PK, true ) );
	lcAlmox.add( new GuardaCampo( txtDescAlmox, "DescAlmox", "Descri��o do almoxarifado", ListaCampos.DB_SI, false ) );
	lcAlmox.montaSql( false, "ALMOX", "EQ" );
	lcAlmox.setReadOnly(true);
    txtCodAlmox.setTabelaExterna(lcAlmox);
    txtCodAlmox.setFK(true);
    txtCodAlmox.setNomeCampo("CodAlmox");
    lcAlmox.setConexao(cn);

    txtData.setRequerido(true);

    GregorianCalendar cal = new GregorianCalendar();
    txtData.setVlrDate(cal.getTime());
    
    adic(new JLabelPad("Data do estoque"),7,0,280,20);
    adic(txtData,7,20,110,20);
    adic(lbGrup,7,40,280,20);
    adic(txtCodGrup,7,60,80,20);
    adic(lbDescGrup,90,40,280,20);
    adic(txtDescGrup,90,60,200,20);
    
       
    adic(lbOrdem,7,80,80,20);
    adic(rgOrdem,7,100,280,30);
    adic(cbGrupo,7,130,280,20);
    
    adic(lbCodAlmox,7,150,280,20);
    adic(txtCodAlmox,7,170,80,20);
    adic(lbDescAlmox,90,150,280,20);
    adic(txtDescAlmox,90,170,200,20);
              
    
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtData.getVlrString().equals("")) {
        Funcoes.mensagemErro(null,"Data do estoque em branco!");
        txtData.requestFocus();
        return;
      }
      if (txtCodAlmox.getVlrInteger().intValue()==0 || txtCodAlmox.getVlrInteger()==null) {
          Funcoes.mensagemErro(null,"Almoxarifado em branco !");
          txtCodAlmox.requestFocus();
          return;
        }
    }
    super.actionPerformed(evt);
  }
  public Object[] getValores() {
    Object[] oRetorno = new Object[5];
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      oRetorno[0] = "CODPROD";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
      oRetorno[0] = "DESCPROD";
    oRetorno[1] = txtCodGrup.getVlrString();
    oRetorno[2] = cbGrupo.getVlrString();
    oRetorno[3] = txtData.getVlrDate();
    oRetorno[4] = txtCodAlmox.getVlrInteger();
    return oRetorno;
  }
}
