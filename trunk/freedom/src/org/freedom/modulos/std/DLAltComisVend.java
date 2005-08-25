/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLAltComisVenda.java <BR>
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
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLAltComisVend extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtPercComis = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 2);
  private JButton btExec = new JButton(Icone.novo("btExecuta.gif"));
  private int iCodVenda = 0;
  public DLAltComisVend(Component cOrig, int iCodVenda, BigDecimal bigComis, Connection con) {
    super(cOrig);
    
    setTitulo("Altera��o de comiss�o");
    setAtribos(250, 120);
    setConexao(con);
    this.iCodVenda = iCodVenda;

    setToFrameLayout();

    txtPercComis.setVlrBigDecimal(bigComis);

    adic(new JLabelPad("% Comiss�o"), 7, 0, 133, 20);
    adic(txtPercComis, 7, 20, 140, 20);
    adic(btExec, 160, 10, 30, 30);

    btExec.addActionListener(this);
  }
  private void alterar() {
    if (txtPercComis.getVlrDouble().doubleValue() < 0) {
      Funcoes.mensagemInforma(this, "Percentual inv�lido!");
      return;
    }
    String sSQL = "UPDATE VDVENDA SET PERCMCOMISVENDA=? WHERE CODVENDA=?"
        + " AND CODEMP=? AND CODFILIAL=?";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setBigDecimal(1, txtPercComis.getVlrBigDecimal());
      ps.setInt(2, iCodVenda);
      ps.setInt(3, Aplicativo.iCodEmp);
      ps.setInt(4, ListaCampos.getMasterFilial("VDVENDA"));
      ps.executeUpdate();
      ps.close();
      if (!con.getAutoCommit())
        con.commit();
    }
    catch (SQLException err) {
      Funcoes.mensagemErro(this, "Erro ao alterar a venda!\n"
          + err.getMessage(),true,con,err);
      err.printStackTrace();
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btExec) {
      alterar();
    }
  }
  public BigDecimal getValore() {
    return txtPercComis.getVlrBigDecimal();
  }
}