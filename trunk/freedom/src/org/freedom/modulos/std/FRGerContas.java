/**
 * @version 24/03/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRGerContas.java <BR>
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
 * Tela de op��es para o relat�rio de gerenciamento de contas.
 * 
 */

package org.freedom.modulos.std;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.relatorios.GerContas;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRGerContas extends FRelatorio  {
  private final int POS_CODSETOR = 0;
  private final int POS_MES = 1;
  private final int POS_CODGRUP = 2;
  private final int POS_SIGLAGRUP = 3;
  private final int POS_CODVEND = 4;
  private final int POS_VALOR = 5;
  private final int POS_TOTSETOR = 6;
  private final int TAM_GRUPO = 14;
  private final int NUM_COLUNAS = 9;
  private Connection con;
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodSetor = new JTextFieldPad();
  private JTextFieldFK txtDescSetor = new JTextFieldFK();
  private JTextFieldPad txtCodVend = new JTextFieldPad();
  private JTextFieldFK txtNomeVend = new JTextFieldFK();
  private JTextFieldPad txtCodCli=new JTextFieldPad();
  private JTextFieldFK txtRazCli = new JTextFieldFK();  
  private JCheckBoxPad cbVendas = new JCheckBoxPad("S� vendas?","S","N");
  private JCheckBoxPad cbCliPrinc = new JCheckBoxPad("Mostrar no cliente principal?","S","N");
  private JCheckBoxPad cbIncluiPed = new JCheckBoxPad("Incluir pedidos n�o faturados?","S","N");
  private JLabel lbCodSetor = new JLabel("C�d.setor");
  private JLabel lbDescSetor = new JLabel("Descri��o do setor");
  private JLabel lbCodVend = new JLabel("C�d.repr.");
  private JLabel lbDescVend = new JLabel("Nome do representante");
  private JLabel lbCliente = new JLabel("C�d.cli.");
  private JLabel lbRazCli = new JLabel("Raz�o social do cliete");
  private ListaCampos lcSetor = new ListaCampos(this);
  private ListaCampos lcVendedor = new ListaCampos(this);
  private ListaCampos lcCliente = new ListaCampos(this);
  private Vector vLabOrdemRel = new Vector();
  private Vector vValOrdemRel = new Vector();
  private JRadioGroup rgOrdemRel = null;
  private FPrinterJob dl = null;

  public FRGerContas() {
    setTitulo("Gerenciamento de contas");
    setAtribos(80,0,425,310);

    GregorianCalendar cal = new GregorianCalendar();
    cal.add(Calendar.DATE,-30);
    txtDataini.setVlrDate(cal.getTime());
    cal.add(Calendar.DATE,30);
    txtDatafim.setVlrDate(cal.getTime());
    txtDataini.setRequerido(true);    
    txtDatafim.setRequerido(true);

    cbVendas.setVlrString("S");
    cbCliPrinc.setVlrString("S");
    cbIncluiPed.setVlrString("N");        
    
    vLabOrdemRel.addElement("Valor");
    vLabOrdemRel.addElement("Raz�o social");
    vLabOrdemRel.addElement("C�d.cli.");
    vLabOrdemRel.addElement("Cidade");
    vLabOrdemRel.addElement("Categoria");
    
    vValOrdemRel.addElement("VL");
    vValOrdemRel.addElement("RA");
    vValOrdemRel.addElement("CC");
    vValOrdemRel.addElement("CI");
    vValOrdemRel.addElement("CA");
        
    rgOrdemRel = new JRadioGroup(5,1,vLabOrdemRel,vValOrdemRel);
            
    txtCodSetor.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescSetor.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcSetor.add(new GuardaCampo( txtCodSetor,0,0,0,0,"CodSetor","C�d.setor",true,false, null, JTextFieldPad.TP_INTEGER, false ),"txtCodSetor");
    lcSetor.add(new GuardaCampo( txtDescSetor,0,0,0,0,"DescSetor","Descri��o do setor",false,false, null, JTextFieldPad.TP_STRING, false ),"txtDescSetor");
    lcSetor.montaSql(false,"SETOR","VD");
    lcSetor.setReadOnly(true);
    txtCodSetor.setTabelaExterna(lcSetor);
    txtCodSetor.setFK(true);
    txtCodSetor.setNomeCampo("CodSetor");

    txtCodVend.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtNomeVend.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcVendedor.add(new GuardaCampo( txtCodVend,0,0,0,0,"CodVend","C�d.repr.",true,false, null, JTextFieldPad.TP_INTEGER, false ),"txtCodVend");
    lcVendedor.add(new GuardaCampo( txtNomeVend,0,0,0,0,"NomeVend","Nome do representante",false,false, null, JTextFieldPad.TP_STRING, false ),"txtNomeVend");
    lcVendedor.montaSql(false,"VENDEDOR","VD");
    lcVendedor.setReadOnly(true);
    txtCodVend.setTabelaExterna(lcVendedor);
    txtCodVend.setFK(true);
    txtCodVend.setNomeCampo("CodVend");

	txtCodCli.setTipo(JTextFieldPad.TP_INTEGER,8,0);
	txtRazCli.setTipo(JTextFieldPad.TP_STRING,40,0);
	lcCliente.add(new GuardaCampo( txtCodCli, 7, 100, 80, 20, "CodCli", "C�d.cli.", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodCli");
	lcCliente.add(new GuardaCampo( txtRazCli, 90, 100, 207, 20, "RazCli", "Raz�o social do cliente", false, false, null, JTextFieldPad.TP_STRING,false),"txtRazCli");
	txtCodCli.setTabelaExterna(lcCliente);
	txtCodCli.setNomeCampo("CodCli");
	txtCodCli.setFK(true);
	lcCliente.setReadOnly(true);
	lcCliente.montaSql(false, "CLIENTE", "VD");
    
    adic(new JLabel("Ordem"),280,0,80,20);
    adic(rgOrdemRel,280,20,120,120);
    adic(new JLabel("Per�odo"),7,0,250,20);
    adic(txtDataini,7,20,100,20);
    adic(txtDatafim,110,20,100,20);    
    adic(lbCodSetor,7,45,250,20);
    adic(txtCodSetor,7,65,70,20);
    adic(lbDescSetor,80,45,250,20);
    adic(txtDescSetor,80,65,190,20);
    adic(lbCodVend,7,90,250,20);
    adic(txtCodVend,7,110,70,20);
    adic(lbDescVend,80,90,250,20);
    adic(txtNomeVend,80,110,190,20);
	adic(lbCliente,7,135,250,20);
	adic(txtCodCli,7,155,70,20);
	adic(lbRazCli,80,135,250,20);
	adic(txtRazCli,80,155,320,20);
    
    adic(cbVendas,7,180,100,25);
    adic(cbCliPrinc,110,180,200,25);
    adic(cbIncluiPed,7,205,295,25);
    
  }

  
  public void imprimir(boolean bVisualizar) {
	if (txtDataini.getVlrString().length() < 10 || txtDatafim.getVlrString().length() < 10) {
		Funcoes.mensagemInforma(this,"Per�odo inv�lido!");
		return;
	}
	
       
	GerContas gerContas = new GerContas();
	gerContas.setConexao(con);
	gerContas.setConsulta(buscaValores());	  	
	dl = new FPrinterJob(gerContas,this);
	dl.setVisible(true);

	
  }

  private ResultSet buscaValores() {
  	String sSql = "";
  	String sWhere = "";
  	String sFrom = "";
    ResultSet rs = null;
  	try {
  		   sSql = "";

  		    PreparedStatement ps = null;
 			ps = con.prepareStatement(sSql);  		   
  		    rs = ps.executeQuery(sSql);
  			
  		}
  		catch (SQLException e) {
  			Funcoes.mensagemErro(this,"Erro executando a consulta.\n"+e.getMessage());
  			e.printStackTrace();
  		}
  	return rs;
  	}
  	  	
  
  public void setConexao(Connection cn) {
    con = cn;
    lcSetor.setConexao(cn);
    lcVendedor.setConexao(cn);
    lcCliente.setConexao(cn);
  }
  
}
