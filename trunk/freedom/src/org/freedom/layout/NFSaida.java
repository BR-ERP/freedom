/**
 * @version 08/08/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.layout <BR>
 * Classe: @(#)NFSaida.java <BR>
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

package org.freedom.layout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.componentes.NF;
import org.freedom.componentes.TabVector;

public class NFSaida extends NF {
	public NFSaida(int casasDec) {
		super(casasDec);
	}
	
	public boolean carregaTabelas(Connection con, Vector parans ) {
		boolean retorno = true;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "SELECT V.CODCLI, C.RAZCLI " +
					"FROM VDVENDA V, VDCLIENTE C" +
					"WHERE C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND " +
					"C.CODCLI=V.CODCLI AND V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' AND " +
					"V.CODVENDA=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1,((Integer) parans.elementAt(0)).intValue());
			ps.setInt(2,((Integer) parans.elementAt(1)).intValue());
			ps.setInt(3,((Integer) parans.elementAt(2)).intValue());
			rs = ps.executeQuery();
			cab = new TabVector(2);
			while (rs.next()) {
				cab.addRow();
				cab.setInt(C_CODEMIT, rs.getInt("CODCLI"));
				cab.setString(C_RAZEMIT, rs.getString("RAZCLI"));
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		}
		catch (SQLException e) {
			retorno = false;
		}
		finally {
			rs = null;
			ps = null;
		}
		return retorno;
	}
	
}
