/**
 * @version 27/06/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freeedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)DLAtualiza.java <BR>
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
 * 
 */

package org.freedom.modulos.cfg;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;


public class DLAtualiza extends FFDialogo {
   private Connection con = null;
   
   public DLAtualiza(Component cOrig) {
   	 super(cOrig);
	 setTitulo("Atualiza��o do sistema");
	 setAtribos(450, 300);
   
   }
   public void setConexao(Connection cn) {
	 con = cn;
   }   

   public void actionPerformed(ActionEvent evt) {
	   if (evt.getSource() == btOK) {
	   	  try {
	   	  	 String sColumns = "";
	   	     Statement stmt = con.createStatement();
		     ResultSet rs = stmt.executeQuery("SELECT * FROM VDTIPOCLI");
			 ResultSetMetaData rsmd = rs.getMetaData();
			 int numberOfColumns = rsmd.getColumnCount();
			 for (int i=1; i<=numberOfColumns; i++) {
			 	sColumns = sColumns + rsmd.getColumnName(i) +", ";
			 }
			 Funcoes.mensagemErro(this,"N�m. de cols.: "+numberOfColumns);
			 Funcoes.mensagemErro(this,"Colunas: \n"+sColumns);
			 //boolean b = rsmd.isSearchable(1);
	   	  }
	   	  catch (SQLException e) {
	   	  	 Funcoes.mensagemErro(this,"Erro: "+e.getMessage());
	   	  }
	   	  
	   }
	   super.actionPerformed(evt);
   }
}