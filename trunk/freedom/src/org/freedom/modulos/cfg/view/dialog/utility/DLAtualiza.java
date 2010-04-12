/**
 * @version 27/06/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freeedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)DLAtualiza.java <BR>
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
 * 
 */

package org.freedom.modulos.cfg.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.freedom.funcoes.Funcoes;
import org.freedom.library.swing.dialog.FFDialogo;


public class DLAtualiza extends FFDialogo {
   private static final long serialVersionUID = 1L;   
   public DLAtualiza(Component cOrig) {
   	 super(cOrig);
	 setTitulo("Atualiza��o do sistema");
	 setAtribos(450, 300);
   
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