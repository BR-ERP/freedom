/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLNovoRec.java <BR>
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
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLNovoRec extends FFDialogo implements PostListener{
  private JPanel pnRec = new JPanel(new BorderLayout());
  private Painel pinCab = new Painel(580,130);
  private JTextFieldPad txtCodCli = new JTextFieldPad();
  private JTextFieldFK txtDescCli = new JTextFieldFK();
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad();
  private JTextFieldFK txtDescPlanoPag = new JTextFieldFK();
  private JTextFieldPad txtCodBanco = new JTextFieldPad();
  private JTextFieldFK txtDescBanco = new JTextFieldFK();
  private JTextFieldPad txtCodRec = new JTextFieldPad();
  private JTextFieldPad txtNParcRec = new JTextFieldPad();
  private JTextFieldPad txtVlrParcItRec = new JTextFieldPad();
  private JTextFieldPad txtVlrDescItRec = new JTextFieldPad();
  private JTextFieldPad txtDtVencItRec = new JTextFieldPad();
  private JTextFieldPad txtVlrParcRec = new JTextFieldPad();
  private JTextFieldPad txtDtEmisRec = new JTextFieldPad();
  private JTextFieldPad txtDocRec = new JTextFieldPad();
  private JTextFieldPad txtObs = new JTextFieldPad();
  private JTextFieldPad txtStatus = new JTextFieldPad();
  private Tabela tabRec = new Tabela();
  private JScrollPane spnTab = new JScrollPane(tabRec);
  private ListaCampos lcReceber = new ListaCampos(this);
  private ListaCampos lcItReceber = new ListaCampos(this);
  private ListaCampos lcCli = new ListaCampos(this,"CL");
  private ListaCampos lcPlanoPag = new ListaCampos(this,"PG");
  private ListaCampos lcBanco = new ListaCampos(this,"BO");
  private Connection con = null;
  public DLNovoRec(Component cOrig) {
  	super(cOrig);
    setTitulo("Novo");
    setAtribos(600,320);
    
    lcItReceber.setMaster(lcReceber);
    lcReceber.adicDetalhe(lcItReceber);
    lcItReceber.setTabela(tabRec);

    txtCodCli.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescCli.setTipo(JTextFieldPad.TP_STRING,50,0);
    lcCli.add(new GuardaCampo( txtCodCli, 7, 100, 80, 20, "CodCli", "C�d.Cli", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodCli");
    lcCli.add(new GuardaCampo( txtDescCli, 90, 100, 207, 20, "RazCli", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescCli");
    lcCli.montaSql(false, "CLIENTE", "VD");
    lcCli.setQueryCommit(false);
    lcCli.setReadOnly(true);
    txtCodCli.setTabelaExterna(lcCli);
    txtCodCli.setFK(true);
    txtCodCli.setNomeCampo("CodCli");

    txtCodPlanoPag.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescPlanoPag.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, 7, 100, 80, 20, "CodPlanoPag", "C�d.PlanoPag", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPag");
    lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, 90, 100, 207, 20, "DescPlanoPag", "Raz�o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPag");
    lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
    lcPlanoPag.setQueryCommit(false);
    lcPlanoPag.setReadOnly(true);
    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
    txtCodPlanoPag.setFK(true);
    txtCodPlanoPag.setNomeCampo("CodPlanoPag");

    txtCodBanco.setTipo(JTextFieldPad.TP_STRING,3,0);
    txtDescBanco.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcBanco.add(new GuardaCampo( txtCodBanco, 7, 100, 80, 20, "CodBanco", "N� Banco", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodBanco");
    lcBanco.add(new GuardaCampo( txtDescBanco, 90, 100, 207, 20, "NomeBanco", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescBanco");
    lcBanco.montaSql(false, "BANCO", "FN");
    lcBanco.setQueryCommit(false);
    lcBanco.setReadOnly(true);
    txtCodBanco.setTabelaExterna(lcBanco);
    txtCodBanco.setFK(true);
    txtCodBanco.setNomeCampo("CodBanco");

    txtVlrParcRec.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtDtEmisRec.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtObs.setTipo(JTextFieldPad.TP_STRING,250,0);
    txtStatus.setTipo(JTextFieldPad.TP_STRING,2,0);
    txtDocRec.setTipo(JTextFieldPad.TP_INTEGER,10,0);
    lcReceber.add(new GuardaCampo( txtCodRec, 7, 100, 80, 20, "CodRec", "C�d.Rec", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodPlanoPagx");
    lcReceber.add(new GuardaCampo( txtCodCli, 7, 100, 80, 20, "CodCli", "C�d.Cli", false, true, null, JTextFieldPad.TP_INTEGER,true),"txtCodPlanoPagx");
    lcReceber.add(new GuardaCampo( txtCodPlanoPag, 7, 100, 80, 20, "CodPlanoPag", "C�d.PlanoPag", false, true, null, JTextFieldPad.TP_INTEGER,true),"txtCodPlanoPagx");
    lcReceber.add(new GuardaCampo( txtCodBanco, 7, 100, 80, 20, "CodBanco", "C�d.Banco", false, true, null, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcReceber.add(new GuardaCampo( txtVlrParcRec, 7, 100, 80, 20, "VlrParcRec", "Valor", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcReceber.add(new GuardaCampo( txtVlrParcRec, 7, 100, 80, 20, "VlrAPagRec", "Valor", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcReceber.add(new GuardaCampo( txtVlrParcRec, 7, 100, 80, 20, "VlrRec", "Valor", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcReceber.add(new GuardaCampo( txtDtEmisRec, 7, 100, 80, 20, "DataRec", "Emiss�o", false, false, null, JTextFieldPad.TP_DATE,false),"txtCodPlanoPagx");
    lcReceber.add(new GuardaCampo( txtDocRec, 7, 100, 80, 20, "DocRec", "Doc.", false, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodPlanoPagx");
    lcReceber.add(new GuardaCampo( txtObs, 7, 100, 80, 20, "ObsRec", "Obs.", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcReceber.add(new GuardaCampo( txtStatus, 7, 100, 80, 20, "StatusRec", "Status", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcReceber.montaSql(true,"RECEBER", "FN");

    txtNParcRec.setNomeCampo("NParcRec");
    txtNParcRec.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtVlrParcItRec.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrDescItRec.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtDtVencItRec.setTipo(JTextFieldPad.TP_DATE,10,0);
    lcItReceber.add(new GuardaCampo( txtNParcRec, 7, 100, 80, 20, "NParcItRec", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcItReceber.add(new GuardaCampo( txtVlrParcItRec, 7, 100, 80, 20, "VlrParcItRec", "Valor Tot.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcItReceber.add(new GuardaCampo( txtDtVencItRec, 7, 100, 80, 20, "DtVencItRec", "Vencimento", false, false, null, JTextFieldPad.TP_DATE,false),"txtCodPlanoPagx");
    lcItReceber.add(new GuardaCampo( txtVlrDescItRec, 7, 100, 80, 20, "VlrDescItRec", "Valor Tot.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcItReceber.montaSql(false, "ITRECEBER", "FN");
    lcItReceber.setQueryCommit(false);
    txtNParcRec.setListaCampos(lcItReceber);
    txtVlrParcItRec.setListaCampos(lcItReceber);
    txtVlrDescItRec.setListaCampos(lcItReceber);
    txtDtVencItRec.setListaCampos(lcItReceber);
    
    lcItReceber.montaTab();
    tabRec.addMouseListener( //Adiciona o mouse listener para que possa editar os itens.
      new MouseAdapter() {
        public void mouseClicked(MouseEvent mevt) {
          if ((mevt.getClickCount() == 2) & (tabRec.getLinhaSel() >= 0)) {
             lcItReceber.edit();
             DLFechaParcela dl = new DLFechaParcela(DLNovoRec.this,txtVlrParcItRec.getVlrBigDecimal(),txtDtVencItRec.getVlrDate(),txtVlrDescItRec.getVlrBigDecimal());
             dl.show();
            if (dl.OK) {
              txtVlrParcItRec.setVlrBigDecimal((BigDecimal)dl.getValores()[0]);
              txtDtVencItRec.setVlrDate((Date)dl.getValores()[1]);
              txtVlrDescItRec.setVlrBigDecimal((BigDecimal)dl.getValores()[2]);
              lcItReceber.post();
              //Atualiza lcReceber              
              if (lcReceber.getStatus() == ListaCampos.LCS_EDIT) 
                lcReceber.post(); // Caso o lcReceber estaja como edit executa o post que atualiza
              else 
                lcReceber.carregaDados(); //Caso n�o, atualiza
            }
            else {
              dl.dispose();
              lcItReceber.cancel(false);
            }
            dl.dispose();
          }
        }
      }
    );
    c.add(pnRec);
    
    pnRec.add(pinCab,BorderLayout.NORTH);
    pnRec.add(spnTab,BorderLayout.CENTER);

    setPainel(pinCab);
    adic(new JLabel("C�digo e nome do cliente"),7,0,250,20);
    adic(txtCodCli,7,20,80,20);
    adic(txtDescCli,90,20,197,20);
    adic(new JLabel("C�digo e descri��o do plano de pagto."),290,0,250,20);
    adic(txtCodPlanoPag,290,20,77,20);
    adic(txtDescPlanoPag,370,20,200,20);
    adic(new JLabel("C�digo e descri�ao do banco"),7,40,250,20);
    adic(txtCodBanco,7,60,80,20);
    adic(txtDescBanco,90,60,197,20);
    adic(new JLabel("Valor"),290,40,107,20);
    adic(txtVlrParcRec,290,60,107,20);
    adic(new JLabel("Emiss�o"),400,40,100,20);
    adic(txtDtEmisRec,400,60,100,20);
    adic(new JLabel("Doc."),7,80,80,20);
    adic(txtDocRec,7,100,80,20);
    adic(new JLabel("Observa��es"),90,80,300,20);
    adic(txtObs,90,100,300,20);
    
    lcReceber.addPostListener(this);
  }
  private void testaCodRec() { //Traz o verdadeiro n�mero do codrec
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
		ps = con.prepareStatement("SELECT * FROM SPGERANUM(?,?,?)");
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("FNRECEBER"));
		ps.setString(3,"RC");
		rs = ps.executeQuery();
		rs.next();
		txtCodRec.setVlrString(rs.getString(1));
//      rs.close();
//      ps.close();
		//if (!con.getAutoCommit())
//      con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao confirmar c�digo da conta a receber!\n"+err.getMessage());
    }
  }
  public void beforePost(PostEvent evt) { 
    if ((evt.getListaCampos().equals(lcReceber)) & (lcReceber.getStatus() == ListaCampos.LCS_INSERT)) {
        testaCodRec();
        txtStatus.setVlrString("R1");
    }
  }
  public void afterPost(PostEvent evt) { }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtDtEmisRec.getVlrString().length() < 10) {
		Funcoes.mensagemErro(this,"Data de emiss�o � requerido!");
      }
      else {
        if (lcReceber.getStatus() == ListaCampos.LCS_INSERT) {
          lcReceber.post();
        }
        else {
          super.actionPerformed(evt);
        }
      }
    }
    else {
      super.actionPerformed(evt);
    }
  }
  public void setConexao(Connection cn) {
    con = cn;
    lcCli.setConexao(cn);
    lcPlanoPag.setConexao(cn);
    lcReceber.setConexao(cn);
    lcItReceber.setConexao(cn);
    lcBanco.setConexao(cn);
    lcReceber.insert(true);
  }
}
