/**
 * @version 01/02/2001 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)Tabela.java <BR>
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
 * Objeto para guardar as informa��es referentes a empresa conectada.
 */

package org.freedom.componentes;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class ObjetoEmpresa {
  private HashMap hValores = new HashMap();
  public ObjetoEmpresa(Connection con){
    carregaObjeto(con);
  }
  
  private void carregaObjeto(Connection con){
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sSQL = null;
    
    sSQL = "SELECT RAZEMP,FONEEMP,FAXEMP,EMAILEMP,FOTOEMP FROM SGEMPRESA WHERE CODEMP=?";
    try {
        ps = con.prepareStatement(sSQL);
        ps.setInt(1, Aplicativo.iCodEmp);
        rs = ps.executeQuery();
        
        if(rs.next()){
        	hValores.put("RAZAOEMP",rs.getString("RAZEMP"));
        	hValores.put("FONEEMP",rs.getString("FONEEMP"));
        	hValores.put("FAXEMP",rs.getString("FAXEMP"));
        	
  		  	byte[] bVals = new byte[650000]; 
  		  	Blob bVal = rs.getBlob("FotoEmp");
  		  	if (bVal != null) {
  		  		try {
  		  			bVal.getBinaryStream().read(bVals,0,bVals.length);  		  		    
  		  			hValores.put("LOGOEMP",((ImageIcon)new ImageIcon(bVals)).getImage());
  		  		}  		  		
  		  		catch(IOException err) {
  		  			Funcoes.mensagemErro(null,"Erro ao recuperar dados!\n"+err.getMessage());
  		  			err.printStackTrace();
  		  		}  		  		
  		  	}
  		  	else{
  		  		hValores.put("LOGOEMP",null);
  		  	}
        	
        }    
    }
    catch(Exception err){
      	err.printStackTrace();
    }
  }
  
  public HashMap getAll(){
  	return hValores;
  }
  
  
}