/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FPrecoBase.java <BR>
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

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFilho;

public class FPrecoBase extends FFilho implements ActionListener {
	private static final long serialVersionUID = 1L;

  private JPanelPad pinCli = new JPanelPad(300,150);
  private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());

  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JButton btGerar = new JButton("Gerar",Icone.novo("btGerar.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private ListaCampos lcGrup = new ListaCampos(this,"GP");
  private ListaCampos lcPlanoPag = new ListaCampos(this,"PG");
  public FPrecoBase() {
  	super(false);
    setTitulo("Ajusta Pre�o Base");
    setAtribos(50,50,310,190);
    
    Container c = getContentPane();
    
    Funcoes.setBordReq(txtCodGrup);
    Funcoes.setBordReq(txtCodPlanoPag);
    
    txtCodGrup.setNomeCampo("CodGrup");
    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, false));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Decri��o do grupo", ListaCampos.DB_SI, false));
    lcGrup.montaSql(false,"GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtDescGrup.setListaCampos(lcGrup);

    txtCodPlanoPag.setNomeCampo("CodPlanoPag");
    lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pg.", ListaCampos.DB_PK, false));
    lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descri��o do plano de pagto.", ListaCampos.DB_SI, false));
    lcPlanoPag.montaSql(false,"PLANOPAG", "FN");
    lcPlanoPag.setReadOnly(true);
    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
    txtCodPlanoPag.setFK(true);
    txtDescPlanoPag.setListaCampos(lcPlanoPag);
    
    c.setLayout(new BorderLayout());
    c.add(pnRod,BorderLayout.SOUTH);
    c.add(pinCli,BorderLayout.CENTER);
    
    btSair.setPreferredSize(new Dimension(100,30));
    
    pnRod.setBorder(BorderFactory.createEtchedBorder());
    pnRod.setPreferredSize(new Dimension(300,30));
    pnRod.setLayout(new BorderLayout());
    pnRod.add(btSair,BorderLayout.EAST);
    
    pinCli.adic(new JLabelPad("C�digo e descri��o do grupo"),7,0,250,20);
    pinCli.adic(txtCodGrup,7,20,80,20);
    pinCli.adic(txtDescGrup,90,20,200,20);
    pinCli.adic(new JLabelPad("C�digo e descri��o do plano de pagamento"),7,40,250,20);
    pinCli.adic(txtCodPlanoPag,7,60,80,20);
    pinCli.adic(txtDescPlanoPag,90,60,200,20);
    pinCli.adic(btGerar,7,90,120,30);
    
    btSair.addActionListener(this);
    btGerar.addActionListener(this);
  }
  private void gerar() {
    if (txtCodPlanoPag.getVlrInteger().intValue() == 0) {
		Funcoes.mensagemInforma(this,"C�digo do plano de pagamento inv�lido!");
      return;
    }
    String sSQL = "UPDATE EQPRODUTO PR SET PRECOBASEPROD=(SELECT MAX(P.PRECOPROD)"+
                 " FROM VDPRECOPROD P WHERE P.CODPROD=PR.CODPROD"+
                 " AND P.CODPLANOPAG=?) WHERE PR.CODGRUP LIKE ?";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,txtCodPlanoPag.getVlrInteger().intValue());
      ps.setString(2,txtCodGrup.getVlrString().length() < 14 ? txtCodGrup.getVlrString()+"%":txtCodGrup.getVlrString());
      ps.executeUpdate();
      con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao Gerar Pre�o Base!\n"+err.getMessage(),true,con,err);
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair) {
      dispose();
    }
    else if (evt.getSource() == btGerar) {
      gerar();
    }
  }
  public void setConexao(DbConnection cn) {
    super.setConexao(cn);
    lcGrup.setConexao(cn);
    lcPlanoPag.setConexao(cn);
  }
}


