/**
 * @version 01/02/2001 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)Tabela.java <BR>
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
 * Objeto para guardar as informa��es referentes a empresa conectada.
 */

package org.freedom.library.business.object;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;

public class Empresa {

	private HashMap<String, Object> hValores = new HashMap<String, Object>();
	private String razemp = "";

	public Empresa(DbConnection con){
		carregaObjeto(con);
	}

	private void carregaObjeto(DbConnection con){
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();


		sql.append( "SELECT E.RAZEMP,F.FONEFILIAL,F.FAXFILIAL,F.EMAILFILIAL,E.FOTOEMP,F.ENDFILIAL,F.NUMFILIAL,F.BAIRFILIAL,F.CEPFILIAL,F.SIGLAUF,F.DDDFILIAL,CNPJFILIAL,INSCFILIAL, " );
		sql.append( "(SELECT M.NOMEMUNIC FROM SGMUNICIPIO M WHERE M.CODPAIS=F.CODPAIS AND M.SIGLAUF=F.SIGLAUF AND M.CODMUNIC=F.CODMUNIC) AS CIDFILIAL " );
		sql.append( "FROM SGEMPRESA E, SGFILIAL F " ); 
		sql.append( "WHERE F.CODEMP=? AND F.CODFILIAL=? AND E.CODEMP=F.CODEMP");

		try {
			ps = con.prepareStatement(sql.toString());
			ps.setInt( 1, Aplicativo.iCodEmp);
			ps.setInt( 2, Aplicativo.iCodFilial );
			rs = ps.executeQuery();

			if(rs.next()){
				
				razemp = rs.getString("RAZEMP"); 
				
				hValores.put("RAZEMP",razemp);
				
				hValores.put("FONEFILIAL",rs.getString("FONEFILIAL"));
				hValores.put("FAXFILIAL",rs.getString("FAXFILIAL"));
				hValores.put("ENDFILIAL",rs.getString("ENDFILIAL"));
				hValores.put("NUMFILIAL",rs.getString("NUMFILIAL"));
				hValores.put("CIDFILIAL",rs.getString("CIDFILIAL"));
				hValores.put("BAIRFILIAL",rs.getString("BAIRFILIAL"));
				hValores.put("UFFILIAL",rs.getString("SIGLAUF"));
				hValores.put("DDDFILIAL",rs.getString("DDDFILIAL"));
				hValores.put("CNPJFILIAL",rs.getString("CNPJFILIAL"));
				hValores.put("INSCFILIAL",rs.getString("INSCFILIAL"));
				hValores.put("CEPFILIAL",rs.getString("CEPFILIAL"));

				hValores.put("RODAPE",rs.getString("ENDFILIAL")!=null ? ( rs.getString("ENDFILIAL").trim() + ", " 
						+(rs.getString("NUMFILIAL")==null?"":rs.getString("NUMFILIAL").trim() + "-") 
						+(rs.getString("BAIRFILIAL")==null?"":rs.getString("BAIRFILIAL").trim() + " - ") 
						+(rs.getString("CIDFILIAL")==null?"":rs.getString("CIDFILIAL").trim() + "-") 
						+(rs.getString("SIGLAUF")==null?"":rs.getString("SIGLAUF").trim()+" - ") 
						+(rs.getString("CEPFILIAL")==null?"":"CEP "+rs.getString("CEPFILIAL").trim()) ) : ""  );

				Blob bVal = rs.getBlob("FOTOEMP");

				if (bVal != null) {
					try {
						hValores.put("LOGOEMP",new ImageIcon(bVal.getBytes( 1, (int) bVal.length() )).getImage());
					}  		  		
					catch(Exception err) {
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

	public HashMap<String, Object> getAll(){
		return hValores;
	}

	public String toString() {
		return razemp;
	}
	
	public String getEndereco() {
		return (String) hValores.get( "ENDFILIAL" ); 
	}
	
	public String getNumFilial() {
		return (String) hValores.get( "NUMFILIAL" ); 
	}
	
	public String getCidFilial() {
		return (String) hValores.get( "CIDFILIAL" ); 
	}
	
	public String getUFFilial() {
		return (String) hValores.get( "UFFILIAL" ); 
	}

	public String getBairFilial() {
		return (String) hValores.get( "BAIRFILIAL" ); 
	}
	
	public String getEnderecoCompleto() {
		return (String) hValores.get( "RODAPE" ); 
	}



}