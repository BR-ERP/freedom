/**
 * @version 20/10/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLGrpCli.java <BR>
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
 * 
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.JLabelPad;
import javax.swing.JScrollPane;

import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLGrpCli extends FFDialogo implements KeyListener, ActionListener {
  private JPanelPad pinCab = new JPanelPad(0,60);
  public JTextFieldPad txtCodPesq = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  public JTextFieldFK txtRazPesq = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  public Tabela tab = new Tabela();
  public boolean bPrimeira = false;
  private JScrollPane spnCentro = new JScrollPane(tab);
  /**
   * 
   *  Classe m�e para dialogos auxiliares.
   *  Construtor da classe...criado grid com <BR>
   *  2 colunas "origem e c�digo aux", <BR>
   *  origem: origem da chave, ex: tabela de pre�os. <BR>
   *  c�digo aux.: c�digo auxilial para busca. <BR>
   * 
   * @param cOrig - Janela m�e do dialogo.
   */
  public DLGrpCli(Component cOrig) {
  	super(cOrig);
    setTitulo("Clientes agrupados");
    setAtribos( 60, 20, 450, 350);
    setResizable(true);
    
    txtCodPesq.setAtivo(false);
    
    setPainel(pinCab);
    adic(new JLabelPad("Raz�o social do cliente principal"),7,0,300,20);
    adic(new JLabelPad("C�d.cli.p."),310,0,80,20);

    adic(txtRazPesq,7,20,300,20);
    adic(txtCodPesq,310,20,80,20);
    
    c.add( pinCab,BorderLayout.NORTH);
    c.add( spnCentro, BorderLayout.CENTER);
    
    tab.adicColuna("Raz�o social do cliente");
    tab.adicColuna("C�d.cli.");
    
    tab.setTamColuna(300,0);
    tab.setTamColuna(80,1);
    
    tab.addKeyListener(this);
    
    addWindowFocusListener(
       new WindowAdapter() {
		 public void windowGainedFocus(WindowEvent e)  {
            if (tab.getNumLinhas() > 0) {
              tab.requestFocus();
              tab.setLinhaSel(0); 
            }
            else
              btCancel.requestFocus();
		 }
       }
    );
  }

  public void keyPressed(KeyEvent kevt) {
    if ( kevt.getSource() == tab && kevt.getKeyCode() == KeyEvent.VK_ENTER) {       
      if (tab.getNumLinhas() > 0) {
      	
//Esquematicos para acertar a linha selecionada...
//Quando o form fechar a linha ira pular uma vez uma vez para baixo...
//ent�o eu volto uma linha aqui:
      	
      	if (tab.getLinhaSel() > 0)
        	tab.setLinhaSel(tab.getLinhaSel()-1);
        else
        	tab.setLinhaSel(tab.getNumLinhas()-1);
      	
        btOK.doClick();
      }
    }
    else
      super.keyPressed(kevt);
  }
  public void carregaClientes(Connection con, int iCodPesq, String sRazPesq) {
      PreparedStatement ps = null;
      ResultSet rs = null;
      String sSql = null;
      Object oLinha[] = null;
      if (iCodPesq!=0) {
          try {
              txtCodPesq.setVlrInteger(new Integer(iCodPesq));
              txtRazPesq.setVlrString(sRazPesq);
              sSql = "SELECT C.RAZCLI, C.CODCLI FROM VDCLIENTE C "+
              		"WHERE C.CODEMPPQ=? AND C.CODFILIALPQ=? AND C.CODPESQ=? AND " +
              		"( NOT (C.CODPESQ=C.CODCLI AND C.CODEMPPQ=C.CODEMP AND C.CODFILIALPQ=C.CODFILIAL) ) "+
              		"ORDER BY C.RAZCLI";
              ps = con.prepareStatement(sSql);
              ps.setInt(1,Aplicativo.iCodEmp);
              ps.setInt(2,ListaCampos.getMasterFilial("VDCLIENTE"));
              ps.setInt(3,iCodPesq);
              rs = ps.executeQuery();
              tab.limpa();
              while (rs.next()) {
                  oLinha = new Object[2];
                  oLinha[0] = rs.getString(1);
                  oLinha[1] = new Integer(rs.getInt(2));
                  tab.adicLinha(oLinha);
              }
              rs.close();
              ps.close();
              if (!con.getAutoCommit()) 
                 con.commit();
          }
          catch (SQLException e) {
              Funcoes.mensagemErro(this,"Erro ao carregar clientes agrupados.\n"+e.getMessage());
          }
          finally {
              rs = null;
              ps = null;
              sSql = null;
              oLinha = null;
          }
      }
  }
  public int getCodCli() {
     int iCodCli = 0;
     if (tab.getNumLinhas() > 0 && tab.getLinhaSel() >= 0) {
         if (tab.getValor(tab.getLinhaSel(),1)!=null)
             iCodCli = ( (Integer) tab.getValor(tab.getLinhaSel(),1)).intValue();
     }
     return iCodCli;
  }
  public void keyReleased(KeyEvent kevt) { }
  public void keyTyped(KeyEvent kevt) { }
}

