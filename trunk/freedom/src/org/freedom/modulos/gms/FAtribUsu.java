/**
 * @version 03/02/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.gms <BR>
 * Classe: @(#)FAribUsu.java <BR>
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
 * Formul�rio de cadastro de usu�rios do sistema.
 * 
 */

package org.freedom.modulos.gms;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;


public class FAtribUsu extends FFilho implements  CarregaListener, ActionListener {
  private static final long serialVersionUID = 1L;
  private JPanelPad pinCli = new JPanelPad();
  private JTextFieldPad txtCodUsu = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private static Vector<String> vAtrib = new Vector<String>();
  private static Vector<String> vDisp =  new Vector<String>();
  private static Vector<String> vCodAtrib = new Vector<String>();
  private static Vector<String> vCodDisp =  new Vector<String>();
  private JList lsAtrib = new JList();
  private JList lsDisp = new JList();
  private JScrollPane spnAtrib = new JScrollPane(lsAtrib);
  private JScrollPane spnDisp = new JScrollPane(lsDisp);
  private JButton btAdicAtrib = new JButton(Icone.novo("btFlechaDir.gif"));
  private JButton btDelAtrib = new JButton(Icone.novo("btFlechaEsq.gif"));
  private JButton btSalvar = new JButton(Icone.novo("btSalvar.gif"));
  private ListaCampos lcUsu = new ListaCampos(this,"");
  public FAtribUsu () {
  	super(false);
    setTitulo("Atribui��es por Usu�rio");
    setAtribos( 50, 50, 395, 240);
  
    lcUsu.add(new GuardaCampo( txtCodUsu, "IDUsu", "ID", ListaCampos.DB_PK, txtNomeUsu, false));
    lcUsu.add(new GuardaCampo( txtNomeUsu, "NomeUsu", "Nome nome do usu�rio", ListaCampos.DB_SI, false));
    lcUsu.montaSql(false, "USUARIO", "SG");    
    lcUsu.setQueryCommit(false);
    lcUsu.setReadOnly(true);
    txtCodUsu.setFK(true);
    txtCodUsu.setNomeCampo("IDUsu");
    txtCodUsu.setTabelaExterna(lcUsu);
    
    getTela().add(pinCli,BorderLayout.CENTER);
    
    pinCli.adic(new JLabelPad("ID"), 7, 0, 200, 20);
    pinCli.adic(txtCodUsu, 7, 20, 80, 20);
    pinCli.adic(new JLabelPad("Nome do usu�rio"),90, 0, 200, 20);
    pinCli.adic(txtNomeUsu, 90, 20, 280, 20);
    
    pinCli.adic(new JLabelPad("Atribui��es dispon�veis:"),7,40,160,20);
    pinCli.adic(spnDisp,7,60,160,100);
    pinCli.adic(btAdicAtrib,175,75,30,30);
    pinCli.adic(btDelAtrib,175,115,30,30);
    pinCli.adic(new JLabelPad("Acesso:"),212,40,158,20);
    pinCli.adic(spnAtrib,212,60,158,100);
    
    btSalvar.setEnabled(false);
    btSalvar.setPreferredSize(new Dimension(30,0));
    adicBotaoSair().add(btSalvar,BorderLayout.WEST);

    btAdicAtrib.addActionListener(this);
    btDelAtrib.addActionListener(this);
  }
  private void adicionaEmp() {
  	if (lsDisp.isSelectionEmpty()) 
  	  return;
  	for (int i=lsDisp.getMaxSelectionIndex();i>=0;i--) {
  	  if (lsDisp.isSelectedIndex(i)) {
        vAtrib.add(vDisp.elementAt(i));
        vDisp.remove(i);
        vCodAtrib.add(vCodDisp.elementAt(i));
        vCodDisp.remove(i);
  	  }
  	}
    lsDisp.setListData(vDisp);
    lsAtrib.setListData(vAtrib);
    btSalvar.setEnabled(true);
  }
  private void removeAtrib() {
  	if (lsAtrib.isSelectionEmpty()) 
  	  return;
  	for (int i=lsAtrib.getMaxSelectionIndex();i>=0;i--) {
  	  if (lsAtrib.isSelectedIndex(i)) {
        vDisp.add(vAtrib.elementAt(i));
        vAtrib.remove(i);
        vCodDisp.add(vCodAtrib.elementAt(i));
        vCodAtrib.remove(i);
  	  }
  	}
    lsDisp.setListData(vDisp);
    lsAtrib.setListData(vAtrib);
    btSalvar.setEnabled(true);
  }
  private void carregaDisp() {
  	try {
      PreparedStatement ps;
      ps = con.prepareStatement("SELECT IDATRIB,DESCATRIB FROM SGATRIBUICAO WHERE CODEMP=? AND CODFILIAL=?");
  	  ps.setInt(1,Aplicativo.iCodEmp);
  	  ps.setInt(2,ListaCampos.getMasterFilial("SGATRIBUICAO"));
  	  ResultSet rs = ps.executeQuery();
  	  vDisp.clear();
  	  vCodDisp.clear();
      while (rs.next()) {
        vCodDisp.addElement(rs.getString("IdAtrib"));
        vDisp.addElement(rs.getString("DescAtrib") != null ? rs.getString("DescAtrib").trim(): "");
  	  }
  	  rs.close();
	  ps.close();
	  if (!con.getAutoCommit())
	  	con.commit();
  	  lsDisp.setListData(vDisp);
	}
  	catch(SQLException err) {
		Funcoes.mensagemErro(this,"N�o foi carregar as atribui��es dipon�veis.\n"+err.getMessage(),true,con,err);
	}  		
  }
  private void carregaAcesso() {
  	if (txtCodUsu.getVlrString().trim().equals("")) {
  		Funcoes.mensagemInforma(this,"ID do usu�rio em branco!");
  		return;
  	}
  		
  	int iPos = 0;
  	try {
      String sSQL = "SELECT A.IDATRIB,A.DESCATRIB FROM SGATRIBUICAO A, SGATRIBUSU AU WHERE "+
                    "A.CODEMP = AU.CODEMP AND A.CODFILIAL = AU.CODFILIAL" +
                    "AND AU.IDUSU = ? AND CODEMPUU=? AND CODFILIALUU=?";
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setString(1,txtCodUsu.getVlrString());
      ps.setInt(2,Aplicativo.iCodEmp);
      ps.setInt(3,lcUsu.getCodFilial());
      ResultSet rs = ps.executeQuery();
      vAtrib.clear();
      vCodAtrib.clear();
      while (rs.next()) {
        vCodAtrib.addElement(rs.getString("IDAtrib"));
        vAtrib.addElement(rs.getString("DescAtrib") != null ? rs.getString("DescAtrib").trim(): "");
        
        iPos = vCodDisp.indexOf(rs.getString("DescAtrib"));
        
        vCodDisp.remove(iPos);
        vDisp.remove(iPos);
  	  }
  	  rs.close();
	  ps.close();
	  if (!con.getAutoCommit())
	  	con.commit();
  	  lsAtrib.setListData(vAtrib);
  	  lsDisp.setListData(vDisp);
	}
  	catch(SQLException err) {
		Funcoes.mensagemInforma(this,"N�o foi carregar as filiais que o usu�rio tem acesso.\n"+err);
	}  		
  }
/*  private void gravaAcesso() {
  	String sSep = "";
  	String sSqlI = "";
  	String sSqlD = "";
    for (int i=0; i<vCodAtrib.size();i++) {
      sSqlI += sSep + vCodAtrib.elementAt(i);
      sSep = ",";
    }
    
    try {
      sSqlD = "DELETE FROM SGACESSOEU WHERE IDUSU=? AND CODEMP=?";
      PreparedStatement ps = con.prepareStatement(sSqlD);
      ps.setString(1,txtCodUsu.getVlrString());
      ps.setInt(2,Aplicativo.iCodEmp);
      ps.executeUpdate();
      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      if (vCodAtrib.size() > 0) {
        sSqlI = "INSERT INTO SGACESSOEU (CODEMP,CODFILIAL,IDUSU,CODEMPFL,CODFILIALFL) "+	
                "SELECT CODEMP,"+Aplicativo.iCodFilial+",'"+txtCodUsu.getVlrString()+"',CODEMP,CODFILIAL FROM SGFILIAL "+
                "WHERE CODEMP=? AND CODFILIAL IN ("+sSqlI+")";
        ps = con.prepareStatement(sSqlI);
        ps.setInt(1,Aplicativo.iCodEmp);
        ps.executeUpdate();
        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
        btSalvar.setEnabled(false);
      }
    }
    catch (SQLException err) {
		Funcoes.mensagemInforma(this,"Erro ao cadastrar o acesso!\n"+err.getMessage());
    }
  } */
  public void afterCarrega(CarregaEvent pevt) { 
  	carregaDisp();
  	carregaAcesso();
  }
  public void actionPerformed(ActionEvent evt) {
  	if (evt.getSource() == btAdicAtrib)
  		adicionaEmp();
  	else if (evt.getSource() == btDelAtrib)
  		removeAtrib();
  }
  public void beforeCarrega(CarregaEvent pevt) {
  }
  public void setConexao(Connection cn) {
  	super.setConexao(cn);
  	lcUsu.setConexao(cn);
  }
  	
}
