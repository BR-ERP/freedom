/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FSerie.java <BR>
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
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;
import org.freedom.telas.FDialogo;
import org.freedom.bmps.Icone;
public class FSerie extends FDados implements ActionListener {
  private JTextFieldPad txtSerie = new JTextFieldPad(4);
  private JTextFieldPad txtDocSerie= new JTextFieldPad(8);
  private JButton btReset = new JButton(Icone.novo("btResetcont.gif"));
  private JTextFieldPad txtReset = new JTextFieldPad();
  private JLabel lbReset = new JLabel("Novo n�.");
  private Connection con = null;
  public FSerie () {
    setTitulo("Cadastro de S�rie de Notas");
    setAtribos( 50, 50, 350, 165);
    
    btReset.setToolTipText("Reiniciar contagem");
    
    txtDocSerie.setSoLeitura(true);
    txtReset.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    
    adicCampo(txtSerie, 7, 20, 50, 20,"Serie","C�digo",JTextFieldPad.TP_STRING,4,0,true,false,null,true);
    adicCampo(txtDocSerie, 60, 20, 250, 20,"DocSerie","Doc. n�.",JTextFieldPad.TP_INTEGER,40,0,false,false,null,false);
    adic(btReset,100,50,130,30);
    setListaCampos( true, "SERIE", "LF");
    btReset.addActionListener(this);
  }
  private void resetar() {
    FDialogo dlReset = new FDialogo();
    dlReset.setTitulo("Reset");
    dlReset.setAtribos(280,120);
    dlReset.adic(lbReset,7,5,100,20);
    dlReset.adic(txtReset,7,25,100,20);
    dlReset.show();
    if (dlReset.OK) 
      gravaReset();
    dlReset.dispose();
    lcCampos.carregaDados();
    
  }
  private void gravaReset() {
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement("UPDATE LFSERIE SET DOCSERIE=? WHERE SERIE=?");
      ps.setInt(1,txtReset.getVlrInteger().intValue());
      ps.setString(2,txtSerie.getVlrString());
      ps.executeUpdate();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao gravar o n�mero inicial!\n"+err.getMessage());
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btReset) {
      if (lcCampos.getStatus() == ListaCampos.LCS_SELECT) {
        resetar();
      }
    }
    super.actionPerformed(evt);
  }
  public void execShow(Connection cn) {
    con = cn;
    super.execShow(cn);
  }
}

