/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLConsultaVenda.java <BR>
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
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLConsultaObs extends FFDialogo implements ActionListener, TabelaSelListener {
  private Painel pinConsulta = new Painel(500,180);
  private JTextFieldPad txtCodCli = new JTextFieldPad();
  private JTextFieldFK txtRazCli = new JTextFieldFK();
  private JTextAreaPad txaObs = new JTextAreaPad();
  private Tabela tabConsulta = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tabConsulta);
  private JScrollPane spnObs = new JScrollPane(txaObs);
  private ListaCampos lcCliente = new ListaCampos(this);
  public DLConsultaObs(Component cOrig,ResultSet rs,Connection cn) {
  	super(cOrig);
    setTitulo("Consulta de Observ�es de clientes!");
    setAtribos(300,300);
    
    c.add(spnTab,BorderLayout.CENTER);
	c.add(pinConsulta,BorderLayout.SOUTH);
    
    txtCodCli.setAtivo(false);
    txaObs.setEditable(false);
    
    pinConsulta.adic(new JLabel("C�digo e raz�o do cliente"),7,0,200,20);
    pinConsulta.adic(txtCodCli,7,20,80,20);
    pinConsulta.adic(txtRazCli,90,20,187,20);
    pinConsulta.adic(new JLabel("Observa��o:"),7,40,100,20);
    pinConsulta.adic(spnObs,7,60,270,80);
    
    txtCodCli.setNomeCampo("CodCli");
    txtCodCli.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtRazCli.setTipo(JTextFieldPad.TP_STRING,50,0);
    lcCliente.add(new GuardaCampo( txtCodCli, 7, 100, 80, 20, "CodCli", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcCliente.add(new GuardaCampo( txtRazCli, 90, 100, 207, 20, "RazCli", "Raz�o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPagx");
	lcCliente.add(new GuardaCampo( txaObs, 90, 100, 207, 20, "ObsCli", "Observa��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPagx");
    txtRazCli.setListaCampos(lcCliente);
    lcCliente.montaSql(false, "CLIENTE", "VD");
    lcCliente.setQueryCommit(false);
    lcCliente.setReadOnly(true);
    lcCliente.setConexao(cn);

    tabConsulta.adicColuna("C�digo");
    tabConsulta.adicColuna("Raz�o");
    
    tabConsulta.setTamColuna(50,0);
    tabConsulta.setTamColuna(220,1);
    
    tabConsulta.addTabelaSelListener(this);

    carregaGridConsulta(rs);
  }
  private void carregaGridConsulta(ResultSet rs) {
    try {
      int i = -1; //Essa loucura ehh por que o rs jah foi dado next() fora desta tela.
	  do {
	  	i++;
		tabConsulta.adicLinha();
		tabConsulta.setValor(""+rs.getInt("CodCli"),i,0);
		tabConsulta.setValor((rs.getString("RazCli") != null ? rs.getString("RazCli") : ""),i,1);
	  }    	
      while (rs.next());
      rs.close();
      tabConsulta.setRowSelectionInterval(0,0);
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar a tabela de consulta!\n"+err.getMessage());
    }
  }
  public void valorAlterado(TabelaSelEvent tevt) {
	if (tevt.getTabela() == tabConsulta) {
		txtCodCli.setVlrString(""+tabConsulta.getValor(tabConsulta.getLinhaSel(),0));
		lcCliente.carregaDados();
	}
  }
}
