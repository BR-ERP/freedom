/**
 * @version 16/12/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FProcessaSL.java <BR>
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
 * Efetua somat�rias nos lan�amentos e insere saldos.
 * 
 */


package org.freedom.modulos.std.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.freedom.acao.Processo;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.business.component.ProcessoSec;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;


/**
 * @author robson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FProcessaSL extends FFilho implements ActionListener {
	private static final long serialVersionUID = 1L;

    private JPanelPad pin = new JPanelPad();
	private JButtonPad btProcessar = new JButtonPad("Executar agora!",Icone.novo("btExecuta.gif"));
	private JLabelPad lbStatus = new JLabelPad();
	boolean bRunProcesso = false;
    public FProcessaSL() {
    	super(false);
    	setTitulo("Processamento de saldo");
    	setAtribos(100,100,500,410);
    	
    	Container c = getContentPane();
    	c.setLayout(new BorderLayout());
        c.add(pin,BorderLayout.CENTER);

        pin.adic(new JLabelPad("--> PROCESSAMENTO DE SALDO <--"),10,15,250,20);

        JLabelPad lbAviso = new JLabelPad();
        lbAviso.setForeground(Color.RED);
        lbAviso.setText("<HTML> ATEN��O! <BR><BR>"+
                               " O processamento de saldo � uma rotina que exige muito processamento "+
                               "tando da esta��o cliente como do servidor. Assegure-se que neste momento "+
                               "nenhum outro usu�rio de outra esta��o esteja relizando tarefas que exijam "+
                               "agilidade no servidor.</HTML>"
                               );
      
        pin.adic(lbAviso,10,60,460,150);
        pin.adic(btProcessar,10,230,180,30);

    	lbStatus.setForeground(Color.BLUE);
      
        pin.adic(lbStatus,10,270,400,20);

		adicBotaoSair();

		btProcessar.addActionListener(this);
        state("Aguardando...");
    }
    private void processar() {
       boolean bOK = false;
       String sSQL = "DELETE FROM FNSALDOLANCA WHERE CODEMP=? AND CODFILIAL=?"; // altera��o aqui
       try {
       	 state("Excluindo base atual de saldos...");
       	 PreparedStatement ps = con.prepareStatement(sSQL);
       	 ps.setInt(1, Aplicativo.iCodEmp);
       	 ps.setInt( 2, ListaCampos.getMasterFilial( "FNSALDOLANCA" ) );
       	 ps.executeUpdate();
       	 ps.close();
       	 state("Base excluida...");
       	 bOK = true;
       }
       catch (SQLException err) {
       	Funcoes.mensagemErro(this,"Erro ao excluir os saldos!\n"+err.getMessage(),true,con,err);
       	err.printStackTrace();
       }
       if (bOK) {
       	 bOK = false;
         sSQL = "SELECT CODPLAN,DATASUBLANCA,SUM(VLRSUBLANCA) VLRSUBLANCA FROM "+
         "FNSUBLANCA WHERE CODEMP=? AND CODFILIAL=? GROUP BY CODPLAN,DATASUBLANCA "+
         "ORDER BY CODPLAN,DATASUBLANCA";
         try {
       	   state("Iniciando reconstru��o...");
       	   PreparedStatement ps = con.prepareStatement(sSQL);
       	   ps.setInt(1,Aplicativo.iCodEmp);
       	   ps.setInt(2,ListaCampos.getMasterFilial("FNLANCA"));
       	   ResultSet rs = ps.executeQuery();
       	   String sPlanAnt = "";
       	   double dSaldo = 0;
       	   bOK = true;
       	   int iFilialPlan = ListaCampos.getMasterFilial("FNPLANEJAMENTO");
       	   int iFilialSaldo = ListaCampos.getMasterFilial("FNSALDOSL");
       	   while (rs.next() && bOK) {
          	   if ("1010100000004".equals( rs.getString("CodPlan") )) {
           		   System.out.println("Debug");
           	   }
       		   if (sPlanAnt.equals(rs.getString("CodPlan"))) {
       	   	  	dSaldo += rs.getDouble("VLRSUBLANCA");
       	   	  }
       	   	  else
       	   	  	dSaldo = rs.getDouble("VLRSUBLANCA");
       	 	  bOK = insereSaldo(iFilialSaldo,iFilialPlan,rs.getString("CodPlan"),rs.getDate("DataSubLanca"),dSaldo);
       	 	  sPlanAnt = rs.getString("CodPlan");
          	   if ("1010100000004".equals( sPlanAnt )) {
           		   System.out.println("Debug");
           	   }
           }

       	   ps.close();
       	   state("Aguardando grava��o final...");
         }
         catch (SQLException err) {
         	bOK = false;
         	Funcoes.mensagemErro(this,"Erro ao excluir os lan�amentos!\n"+err.getMessage(),true,con,err);
       	    err.printStackTrace();
         }
       }
       try {
         if (bOK) {
       		con.commit();
       	   	state("Registros processados com sucesso!");
          }
         else { 
       	   state("Registros antigos restaurados!");
       	   con.rollback();
         }
       }
       catch (SQLException err) {
         Funcoes.mensagemErro(this,"Erro ao relizar precedimento!\n"+err.getMessage(),true,con,err);
         err.printStackTrace();
       }
       bRunProcesso = false;
       btProcessar.setEnabled(true);
    } 
    private boolean insereSaldo(int iFilialSaldo,int iFilialPlan,String sCodPlan,Date dData,double dVal) {
    	boolean bRet = false;
    	String sSQL = "INSERT INTO FNSALDOLANCA "+
                     "(CODEMP,CODFILIAL,CODEMPPN,CODFILIALPN,CODPLAN,DATASL,PREVSL,SALDOSL)"+
    	             " VALUES (?,?,?,?,?,?,?,?)";
    	try {
    		state("Processano plan: "+sCodPlan+"...dia: "+StringFunctions.sqlDateToStrDate(dData));
    		PreparedStatement ps = con.prepareStatement(sSQL);
    		ps.setInt(1,Aplicativo.iCodEmp);
    		ps.setInt(2,iFilialSaldo);
    		ps.setInt(3,Aplicativo.iCodEmp);
    		ps.setInt(4,iFilialPlan);
    		ps.setString(5,sCodPlan);
    		ps.setDate(6,dData);
   		    ps.setDouble(7,0);
   		    ps.setDouble(8,dVal);
   		    ps.executeUpdate();
   		    ps.close();
    		bRet = true;
    	}
    	catch (SQLException err) {
    		Funcoes.mensagemErro(this,"Erro ao excluir os lan�amentos!"+"\n"+err.getMessage(),true,con,err);
    	}
    	return bRet;
    }
	public void actionPerformed(ActionEvent evt) {
		 if (evt.getSource() == btProcessar) {
			ProcessoSec pSec = new ProcessoSec(100,
			  new Processo() {
				public void run() {
				  lbStatus.updateUI();
				}
			  },
			  new Processo() {
				public void run() {
				  processar();
				}
			  }
			);
			bRunProcesso = true;
			btProcessar.setEnabled(false);
			pSec.iniciar();
		 }
	}
	public void state(String sStatus) {
		lbStatus.setText(sStatus);
	}
}
