/**
 * @version 23/02/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Anerson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLBuscaEstoq.java <BR>
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
 * Tela para busca de saldos de estoque em v�rios almoxarifados.
 */

package org.freedom.modulos.std;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.ImageIcon;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLF3;

public class DLBuscaEstoq extends DLF3 implements TabelaSelListener {
   private Vector vValsProd = new Vector();
   private String sSQL = "";   
   private ListaCampos lcCampos = null;
   private ListaCampos lcProd = null;
   Integer iCodAlmox = new Integer(0);
   boolean bRet = false;
   private ImageIcon imgBaixa = Icone.novo("clPago.gif");
   private ImageIcon imgVisualiza = Icone.novo("clVencido.gif");
   private ImageIcon imgPadrao = Icone.novo("clNaoVencido.gif");
   private ImageIcon imgColuna = null;
   public DLBuscaEstoq(ListaCampos lc1, ListaCampos lc2, Component cOrig,Connection con,String sCol) {
   	 super(cOrig);
   	 
     setAtribos( 560, 260);
   	 
   	 lcCampos = lc1;
   	 lcProd = lc2;
   	 
   	 setConexao(con);
   	 
   	 tab.adicColuna("Cd.Filial");
     tab.adicColuna("Nome da filial");    
     tab.adicColuna("Cd.Almox.");
   	 tab.adicColuna("Nome Almoxarifado");   	  
   	 tab.adicColuna("Saldo");   	
   	 tab.adicColuna("");
   	 tab.setTamColuna(60,0);
   	 tab.setTamColuna(160,1); 
   	 tab.setTamColuna(60,2);
   	 tab.setTamColuna(160,3);
   	 tab.setTamColuna(90,4);   	 	 
   	 tab.setTamColuna(20,5);
   	 tab.addTabelaSelListener(this); 

  	 setTitulo("Saldo do produto nos almoxarifados");
 	 tab.addKeyListener(this);
   }
   public Object getValor() {
	if(lcCampos!=null) {   	 		 
		if (lcCampos.getCampo("codalmox")!=null)
			lcCampos.getCampo("codalmox").setVlrInteger(iCodAlmox);
		}
		else {
		  	System.out.println("Lista Campos nulo no busca Estoq!!!!");
		}
   	return oRetVal;
  }
   public boolean setValor(Object oVal,String sTipo) { 
     bRet = false;

     
      sSQL = "SELECT A.CODFILIAL,F.NOMEFILIAL,A.CODALMOX,A.DESCALMOX, "+
      		"(SELECT SP.SLDLIQPROD FROM EQSALDOPROD SP WHERE "+
      		"SP.CODEMPAX=A.CODEMP AND SP.CODFILIALAX=A.CODFILIAL AND "+
      		 "SP.CODALMOX=A.CODALMOX AND "+
      		 "SP.CODEMP=? AND SP.CODFILIAL=? AND SP.CODPROD=?), "+
      		"(SELECT AF.BAIXAESTOQAF FROM EQALMOXFILIAL AF "+
      		"WHERE AF.CODEMP=A.CODEMP AND AF.CODFILIAL=A.CODFILIAL AND "+
      		"AF.CODALMOX=A.CODALMOX AND AF.CODEMPAF=F.CODEMP AND "+
      		"AF.CODFILIALAF=F.CODFILIAL), PD.CODALMOX "+
      		"FROM EQPRODUTO PD, EQALMOX A, SGEMPRESA E, SGFILIAL F " +
      		"WHERE PD.CODEMP=A.CODEMP AND PD.CODFILIAL=A.CODFILIAL AND PD.CODPROD=? AND " +
      		"E.CODEMP=A.CODEMP AND A.CODEMP=? AND A.CODFILIAL=? AND "+
      		 "F.CODEMP=A.CODEMP AND F.CODFILIAL=A.CODFILIAL AND "+
      		 "(E.MULTIALMOXEMP='N' OR EXISTS(SELECT CODALMOX FROM EQALMOXFILIAL AF "+ 
      		 "WHERE AF.CODEMP=A.CODEMP AND AF.CODFILIAL=A.CODFILIAL "+
      		"AND AF.CODALMOX=A.CODALMOX AND AF.CODEMPAF=? AND AF.CODFILIALAF=?))";
;

   	  System.out.println(sSQL);
      try {
      	PreparedStatement ps = con.prepareStatement(sSQL);

      	ps.setInt(1,lcProd.getCodEmp());
      	ps.setInt(2,lcProd.getCodFilial());
      	ps.setInt(3,(lcProd.getCampo("codprod").getVlrInteger()).intValue());
      	ps.setInt(4,(lcProd.getCampo("codprod").getVlrInteger()).intValue());
      	ps.setInt(5,Aplicativo.iCodEmp);
      	ps.setInt(6,Aplicativo.iCodFilial);
      	ps.setInt(7,Aplicativo.iCodEmp);
      	ps.setInt(8,Aplicativo.iCodFilial);

        tab.limpa();
        tab.removeAll();

      	ResultSet rs = ps.executeQuery();
      	int iCont = 0;
      	int iPadrao = 0;
      	while (rs.next()) {
      		if (iCont ==0)
      			if(lcCampos!=null) {   	 		  
   	 		    	if (lcCampos.getCampo("codalmox")!=null)
   	 		    		lcCampos.getCampo("codalmox").setVlrInteger(new Integer(rs.getInt(3)));   	 		    	      	   
      			}
      	   String sImgColuna = rs.getString(6);
      	   if (sImgColuna!=null){
      	   	if (sImgColuna.equals("S"))
      	   		imgColuna = imgBaixa;
      	   	else
      	   		imgColuna = imgVisualiza;      	   	
      	   }
      	   else
      	   	  imgColuna = imgBaixa;
      	   
      	   if(rs.getString(3).equals(rs.getString(7))) {
      	   	 imgColuna = imgPadrao;
      	   	 iPadrao = iCont;
      	   }
      	   
      	   tab.adicLinha( new Object[] {
      	      rs.getString(1) != null ? rs.getString(1) : "",
      		  rs.getString(2) != null ? rs.getString(2) : "",
      		  rs.getString(3) != null ? rs.getString(3) : "",
			  rs.getString(4) != null ? rs.getString(4) : "",
			  rs.getString(5) != null ? Funcoes.strDecimalToStrCurrency(13,2,rs.getString(5)) : "",
		      imgColuna,
      	   });
      	   if (iCont>0)
      	   		bRet = true;
      	   else
      	   		bRet = false;
      	   iCont++;
      	}

      	tab.changeSelection(iPadrao,0,true,true);
      	tab.setLinhaSel(iPadrao);
      	
      	System.out.println("Almox. Padrao na linha:"+iPadrao);
      	System.out.println("Almox. Padrao:"+tab.getValueAt(iPadrao,3));
      	
      	rs.close();
      	ps.close();
      	if (!con.getAutoCommit())
      		con.commit();
      }
      catch (SQLException err) {
      	 Funcoes.mensagemErro(this,"Erro ao buscar filiais almoxarifados e saldos!\n"+err.getMessage());
      	 err.printStackTrace();
      }
      return bRet;
   }
   public void actionPerformed(ActionEvent evt) {
   	  super.actionPerformed(evt);
   }
   public void valorAlterado(TabelaSelEvent tsevt) {
/*
   	try {   	
   	 	if (tsevt.getTabela() == tab) {
   	 		if (tab.getNumLinhas() > 0) {
   	 		    if (bRet)
   	 		    	iCodAlmox = new Integer(Integer.parseInt(tab.getValueAt(tab.getLinhaSel(),2).toString()));   	 		   
   	 		}
       }   	  
   	 }
   	 
   	 catch(Exception e) {
   	 	e.printStackTrace();
   	 }
   	 */
    }

public void setValor(Object oVal) {
	
}
};        
