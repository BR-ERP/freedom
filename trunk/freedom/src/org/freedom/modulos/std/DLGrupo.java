/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLGrupo.java <BR>
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
 */

package org.freedom.modulos.std;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLGrupo extends FFDialogo {
  private Connection con = null;
  private JTextFieldPad txtCodGrupo = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtDescGrupo = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtSiglaGrupo = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JLabel lbCodGrupo = new JLabel("C�d.grupo");
  private JLabel lbDescGrupo = new JLabel("Descri��o do grupo");
  private JLabel lbSiglaGrupo = new JLabel("Sigla");
  private boolean bEdit = false;
  public DLGrupo(Component cOrig,Connection cn, String sCod, String sDesc) {
  	super(cOrig);
    con = cn;
    setTitulo("Novo Grupo");
    setAtribos(400,140);
    Funcoes.setBordReq(txtCodGrupo);    
    Funcoes.setBordReq(txtDescGrupo);    
   
    adic(lbCodGrupo,7,0,80,20);
    adic(txtCodGrupo,7,20,80,20);
    adic(lbDescGrupo,90,0,200,20);
    adic(txtDescGrupo,90,20,200,20);
    adic(lbSiglaGrupo,293,0,80,20);
    adic(txtSiglaGrupo,293,20,80,20);
    if (sCod != null) {
      setTitulo("Edi��o de Grupo");
      txtCodGrupo.setText(sCod);
      txtDescGrupo.setVlrString(sDesc);
      txtDescGrupo.selectAll();
      txtCodGrupo.setBackground(Color.lightGray);
      txtCodGrupo.setFont(new Font("Dialog", Font.BOLD, 12));
      txtCodGrupo.setEditable(false);
      txtCodGrupo.setForeground(new Color(118, 89, 170));
      bEdit = true;
    }    
  }
  public String[] getValores() {
    String[] sRetorno = new String[3];
    sRetorno[0] = txtCodGrupo.getText();
    sRetorno[1] = txtDescGrupo.getText();
    sRetorno[2] = txtSiglaGrupo.getText();
    return sRetorno;
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtCodGrupo.getText().trim().length() == 0) {
		Funcoes.mensagemInforma(this,"C�diogo em branco! ! ! ");
        txtCodGrupo.requestFocus();
        return;
      }
      else if (txtDescGrupo.getText().trim().length() == 0) {
		Funcoes.mensagemInforma(this,"Descri��o em branco! ! !");
        txtDescGrupo.requestFocus();
        return;
      }
      else if ((!bEdit) & (!verifCod())) {
		Funcoes.mensagemInforma(this,"C�digo j� existe! ! !");
        txtCodGrupo.requestFocus();
        return;
      }
    }
    super.actionPerformed(evt);
  }
  private boolean verifCod() {
    String sSQL = "SELECT CODGRUP FROM EQGRUPO";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      while (rs.next()) 
        if (rs.getString("CodGrup").trim().compareTo(txtCodGrupo.getText().trim()) == 0) 
          return false;
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
         con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela GRUPO!"+"\n"+err.getMessage());
    }
    return true;
  }          
}
