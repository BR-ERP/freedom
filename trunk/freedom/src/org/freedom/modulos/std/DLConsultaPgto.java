/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLConsultaPgto.java <BR>
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
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLConsultaPgto extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTablePad tabConsulta = new JTablePad();
  private JScrollPane spnTab = new JScrollPane(tabConsulta);
  private ListaCampos lcCli = new ListaCampos(this);
  private JPanelPad pinConsulta = new JPanelPad(0,60);
  public DLConsultaPgto(Component cOrig,DbConnection cn,int iCodCli) {
  	super(cOrig);
  	con = cn; 
    lcCli.setConexao(cn);
    txtCodCli.setVlrString(""+iCodCli);
    setTitulo("Consulta de Pagamentos");
    setAtribos(100,100,500,300);
    
	setToFrameLayout();
    
    pnRodape.setPreferredSize(new Dimension(500,32));
    pnRodape.setBorder(BorderFactory.createEtchedBorder());
    c.add(pinConsulta,BorderLayout.NORTH);
    c.add(spnTab,BorderLayout.CENTER);
    
    txtCodCli.setAtivo(false);
    
    pinConsulta.adic(new JLabelPad("C�digo e descri��o do cliente"),7,0,250,20);
    pinConsulta.adic(txtCodCli,7,20,100,20);
    pinConsulta.adic(txtRazCli,110,20,250,20);
    
    txtCodCli.setNomeCampo("CodCli");
    lcCli.add(new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK,false));
    lcCli.add(new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI,false));
    txtRazCli.setListaCampos(lcCli);
    lcCli.montaSql(false, "CLIENTE", "VD");
    lcCli.setReadOnly(true);
    lcCli.carregaDados();

    tabConsulta.adicColuna("Vencto.");
    tabConsulta.adicColuna("S�rie");
    tabConsulta.adicColuna("Doc");
    tabConsulta.adicColuna("C�d. Venda");
    tabConsulta.adicColuna("Dt. Venda.");
    tabConsulta.adicColuna("Valor");
    tabConsulta.adicColuna("Dt. Pagto.");
    tabConsulta.adicColuna("Vlr. Pago.");
    tabConsulta.adicColuna("Atraso");
    tabConsulta.adicColuna("Observa��es");
    tabConsulta.adicColuna("Banco");
    tabConsulta.adicColuna("TV");
    
    tabConsulta.setTamColuna(90,0);
    tabConsulta.setTamColuna(50,1);
    tabConsulta.setTamColuna(80,2);
    tabConsulta.setTamColuna(80,3);
    tabConsulta.setTamColuna(90,4);
    tabConsulta.setTamColuna(100,5);
    tabConsulta.setTamColuna(90,6);
    tabConsulta.setTamColuna(100,7);
    tabConsulta.setTamColuna(60,8);
    tabConsulta.setTamColuna(200,9);
    tabConsulta.setTamColuna(200,10);
    tabConsulta.setTamColuna(20,11);
    tabConsulta.addMouseListener( 
      new MouseAdapter() {
        public void mouseClicked(MouseEvent mevt) {
          if (mevt.getSource() == tabConsulta) {
            if (mevt.getClickCount() == 2) {
              int iLin = tabConsulta.getLinhaSel();
              if (iLin >= 0) {
                int iCodVenda = Integer.parseInt((String)tabConsulta.getValor(iLin,3));
                String sTipoVenda = (String) tabConsulta.getValor(iLin,11);
                DLConsultaVenda dl = new DLConsultaVenda(DLConsultaPgto.this,con,iCodVenda, sTipoVenda);
                dl.setVisible(true);
                dl.dispose();
              }
            }
          }
        }
      }
    );

    carregaGridConsulta();
  }
  private void carregaGridConsulta() {
    String sSQL = "SELECT IT.DTVENCITREC,V.SERIE,R.DOCREC,V.CODVENDA,"+
                  "R.DATAREC,IT.VLRPARCITREC,IT.DTPAGOITREC,IT.VLRPAGOITREC,"+
                  "(CAST('today' AS DATE)-IT.DTVENCITREC) AS ATRASO,"+
                  "R.OBSREC,(SELECT B.NOMEBANCO FROM FNBANCO B "+
                  "WHERE B.CODBANCO = R.CODBANCO) AS NOMEBANCO,"+
                  "R.CODREC,IT.NPARCITREC, R.TIPOVENDA " +
                  "FROM FNRECEBER R, VDVENDA V, FNITRECEBER IT "+
                  "WHERE R.CODCLI=? AND V.CODVENDA=R.CODVENDA AND"+
                  " IT.STATUSITREC NOT IN ('RP') AND IT.CODREC = R.CODREC "+
                  "ORDER BY IT.DTVENCITREC,R.CODREC,IT.NPARCITREC";  
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,txtCodCli.getVlrInteger().intValue());
      ResultSet rs = ps.executeQuery();
      for (int i=0; rs.next(); i++) {
        tabConsulta.adicLinha();
        tabConsulta.setValor((rs.getDate("DtVencItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtVencItRec")) : ""),i,0);
        tabConsulta.setValor((rs.getString("Serie") != null ? rs.getString("Serie") : ""),i,1);
        tabConsulta.setValor((rs.getString("DocRec") != null ? rs.getString("DocRec") : ""),i,2);
        tabConsulta.setValor(""+rs.getInt("CodVenda"),i,3);
        tabConsulta.setValor((rs.getDate("DataRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DataRec")) : ""),i,4);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrParcItRec")),i,5);
        tabConsulta.setValor((rs.getDate("DtPagoItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtPagoItRec")) : ""),i,6);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrPagoItRec")),i,7);
        tabConsulta.setValor(rs.getString(9),i,8);
        tabConsulta.setValor(rs.getString("ObsRec") != null ? rs.getString("ObsRec") : "",i,9);
        tabConsulta.setValor(rs.getString(11) != null ? rs.getString(11) : "",i,10);
        tabConsulta.setValor(rs.getString("TipoVenda"),i,11);
      }
      rs.close();
      ps.close();
//      con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar a tabela de consulta!\n"+err.getMessage(),true,con,err);
    }
  }
}

