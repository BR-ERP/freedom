/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLConsultaVenda.java <BR>
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

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;

import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;


public class DLConsultaObs extends FFDialogo implements ActionListener, TabelaSelListener {

	private static final long serialVersionUID = 1L;

  private JPanelPad pinConsulta = new JPanelPad(500,180);
  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextAreaPad txaObs = new JTextAreaPad();
  private JTablePad tabConsulta = new JTablePad();
  private JScrollPane spnTab = new JScrollPane(tabConsulta);
  private JScrollPane spnObs = new JScrollPane(txaObs);
  private ListaCampos lcCliente = new ListaCampos(this);
  public DLConsultaObs(Component cOrig,ResultSet rs,DbConnection cn) {
  	super(cOrig);
    setTitulo("Consulta de Observ��es de clientes!");
    setAtribos(300,350);
    
    c.add(spnTab,BorderLayout.CENTER);
	c.add(pinConsulta,BorderLayout.NORTH);
    
    txtCodCli.setAtivo(false);
    txaObs.setEditable(false);
    
    pinConsulta.adic(new JLabelPad("C�d.cli."),7,0,200,20);
    pinConsulta.adic(txtCodCli,7,20,80,20);
    pinConsulta.adic(new JLabelPad("Raz�o social do cliente"),90,0,200,20);
    pinConsulta.adic(txtRazCli,90,20,187,20);
    pinConsulta.adic(new JLabelPad("Observa��o:"),7,40,100,20);
    pinConsulta.adic(spnObs,7,60,270,80);
    
    txtCodCli.setNomeCampo("CodCli");
    lcCliente.add(new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK,false));
    lcCliente.add(new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI,false));
	lcCliente.add(new GuardaCampo( txaObs, "ObsCli", "Observa��o", ListaCampos.DB_SI,false));
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
		Funcoes.mensagemErro(this,"Erro ao montar a tabela de consulta!\n"+err.getMessage(),true,con,err);
    }
  }
  public void valorAlterado(TabelaSelEvent tevt) {
	if (tevt.getTabela() == tabConsulta) {
		txtCodCli.setVlrString(""+tabConsulta.getValor(tabConsulta.getLinhaSel(),0));
		lcCliente.carregaDados();
	}
  }
}
