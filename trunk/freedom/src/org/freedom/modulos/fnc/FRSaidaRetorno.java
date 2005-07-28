/*
 * Created on 06/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.freedom.modulos.fnc;

/**
 * @author Robson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FRelatorio;


public class FRSaidaRetorno extends FRelatorio {
  private static final long serialVersionUID = 1L;	
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtCliente = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
 
  
  public FRSaidaRetorno() {
    setTitulo("SA�DA X RETORNO");
    setAtribos(160,80,285,180);


    txtDataini.setVlrDate(new Date());
    txtDatafim.setVlrDate(new Date());
    
    JLabelPad lbLinha = new JLabelPad();
    lbLinha.setBorder(BorderFactory.createEtchedBorder());

    adic(new JLabelPad("Periodo:"),7,5,100,20);
    adic(lbLinha,60,15,210,2);
    adic(new JLabelPad("De:"),7,30,30,20);
    adic(txtDataini,32,30,97,20);
    adic(new JLabelPad("At�:"),135,30,30,20);
    adic(txtDatafim,165,30,97,20);
    adic(new JLabelPad("Cliente"),7,55,300,20);
    adic(txtCliente,7,75,212,20);
    
    
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
  }

  public void actionPerformed(ActionEvent evt) {
      super.actionPerformed(evt);  	  	
  }
    
  
  public ResultSet getResultSet(){

    String NomeCli = "";        
    
  	NomeCli = "'"+txtCliente.getVlrString()+"%'";
  		
  	String sSQL = " select vdcliente.Nomecli as nome, fnbanco.Nomebanco as banco, "
        +" sgcheque.NCHEQUE, sgcheque.predata, sgcheque.valor, "
        +" sgcheque.codbanco, sgcheque.codcli, sgscheque.NSAIDA ,sgscheque.NCHEQUE, sgscheque.TSAIDA, "
		  +" sgscheque.DESCRICAO, sgscheque.DTINS as Dates, sgrcheque.NSAIDA, sgrcheque.NCHEQUE, sgrcheque.DTINS as Dater, "
		  +" sgrcheque.codigor from vdcliente, fnbanco, sgcheque, sgscheque, sgrcheque where "
		  +" vdcliente.CODCLI=sgcheque.CODCLI and "
		  +" fnbanco.codbanco=sgcheque.codbanco and UPPER(vdcliente.Nomecli) LIKE UPPER( "+NomeCli+" ) "
		  +" and sgcheque.NCHEQUE=sgscheque.NCHEQUE and sgcheque.CODBANCO=sgscheque.CODBANCO and sgscheque.TSAIDA=1 "
		  +" and sgrcheque.NCHEQUE=sgscheque.NCHEQUE and sgrcheque.CODBANCO=sgscheque.CODBANCO and sgrcheque.NSAIDA=sgscheque.NSAIDA and sgscheque.dtins BETWEEN ? AND ? ORDER BY sgscheque.DTINS";	
  		 
  	PreparedStatement ps = null;
  	ResultSet rs = null;
    try {
    	ps = con.prepareStatement(sSQL);
    	ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
    	ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
    	
    	    	
    	rs = ps.executeQuery();
    }
    catch(SQLException e) {
    	e.printStackTrace();
    }
  	
  	return rs;
  }
  public void imprimir(boolean bVisualizar) {

    if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
      return;
    }

    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    int numcheque = 0;
    boolean pass = false;
    
    String sPag = "";
       
    String sDataini = "";
    String sDatafim = "";
    
    sDataini = txtDataini.getVlrString();
    sDatafim = txtDatafim.getVlrString();
       
    ResultSet rs = getResultSet(); 
   
    try {
 
      imp.limpaPags();
     
      while ( rs.next() ) {
      	
      	if (numcheque != rs.getInt("NCHEQUE")){
      		pass = true;
      		numcheque = rs.getInt("NCHEQUE");      		
      	}
      	else pass = false;
      	
      	if (imp.pRow()>=(linPag-1)) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",89)+"|");
            imp.incPags();
            imp.eject();
        }
      	if (imp.pRow()==0) {
        	imp.montaCab();
        	imp.setTitulo("Relat�rio de saida x retorno"+sPag);
        	imp.addSubTitulo("RELAT�RIO DE SAIDA X RETORNO "+sPag+"   -   PERIODO DE :"+sDataini+" ATE: "+sDatafim);
        	imp.impCab(91, true);
            imp.say(imp.pRow()+0,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",88)+"|");
      	}
      	
      	if (pass==true){	
         
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,Funcoes.replicate("=",90));
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           
           imp.say(imp.pRow()+0,0," Cliente:");
           imp.say(imp.pRow()+0,2,rs.getString("NOMECLI"));
          
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           
           imp.say(imp.pRow()+0,2,"Num.Cheque:");
           imp.say(imp.pRow()+0,2,rs.getString("NCHEQUE"));
           imp.say(imp.pRow()+0,3,"Valor:");
           imp.say(imp.pRow()+0,2,rs.getString("VALOR"));
           imp.say(imp.pRow()+0,3,"Banco:");
           imp.say(imp.pRow()+0,2,rs.getString("banco"));
           
           imp.say(imp.pRow()+2,0,""+imp.comprimido());
                      
           imp.say(imp.pRow()+0,2,"Data/Sa�da");
           imp.say(imp.pRow()+0,3,"Descri��o");
           imp.say(imp.pRow()+0,42,"Data/Retorno");
           imp.say(imp.pRow()+0,5,"C�d.Retorno");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,Funcoes.replicate("-",90));          
        }
      	
        imp.say(imp.pRow()+1,0,""+imp.normal());
        imp.say(imp.pRow()+0,2,Funcoes.dateToStrDate(rs.getDate("Dates")));
        imp.say(imp.pRow()+0,3,rs.getString("DESCRICAO"));
        imp.say(imp.pRow()+0,5, Funcoes.dateToStrDate(rs.getDate("Dater")));
        imp.say(imp.pRow()+0,11,rs.getString("Codigor"));

        if (imp.pRow()>=linPag) {
           imp.incPags();
           imp.eject();
        }
     }
     
     imp.say(imp.pRow()+1,0,""+imp.normal());
     imp.say(imp.pRow()+0,0,Funcoes.replicate("=",90));
      
      imp.eject();
      
      imp.fechaGravacao();

      if (!con.getAutoCommit())
      	con.commit();
      
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de Cheques!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }        
  }
}