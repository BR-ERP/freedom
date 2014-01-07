/**
 * @version 03/10/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva/Robson Sanchez
 *         <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.funcoes <BR>
 * Classe:
 * @(#)Funcoes.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para
 * Programas de Computador), <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios da classe.....
 */

package org.freedom.library.functions;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class FuncoesCRM {

	public FuncoesCRM() {
	}

	public static HashMap<String, Vector<Object>> montaComboContr(DbConnection con, Integer codcli, Integer codcontr,
			String textonulo, boolean soativos) {

		Vector<Object> vVals = new Vector<Object>();
		Vector<Object> vLabs = new Vector<Object>();
		HashMap<String, Vector<Object>> ret = new HashMap<String, Vector<Object>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		sql.append("select ct.codcontr, ct.desccontr ");
		sql.append("from vdcliente cl, vdcontrato ct ");
		sql.append("where ct.codempcl=cl.codemp and ct.codfilialcl=cl.codfilial and ct.codcli=cl.codcli ");
		sql.append("and cl.codemp=? and cl.codfilial=? and cl.codcli=? ");
		
		
		if(soativos) {
			sql.append(" and ( coalesce(ct.ativo,'N')='S' ");
			if (codcontr!=null) {
				sql.append(" or ct.codcontr=");
				sql.append(codcontr);
			}
			sql.append(" )");
		}

		try {
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("VDCLIENTE"));
			ps.setInt(3, codcli);
			rs = ps.executeQuery();

			vVals.addElement(-1);
			vLabs.addElement(textonulo);

			while (rs.next()) {
				vVals.addElement(new Integer(rs.getInt("codcontr")));
				vLabs.addElement(rs.getString("desccontr"));
			}

			ret.put("VAL", vVals);
			ret.put("LAB", vLabs);

			rs.close();
			ps.close();

			con.commit();

		}
		catch (SQLException err) {
			err.printStackTrace();
			Funcoes.mensagemErro(null, "Erro ao buscar dados do contrato");
		}
		return ret;
	}

	public static HashMap<String, Vector<Object>> montaComboItContr(DbConnection con, Integer codcontr, String textonulo) {

		Vector<Object> vVals = new Vector<Object>();
		Vector<Object> vLabs = new Vector<Object>();
		HashMap<String, Vector<Object>> ret = new HashMap<String, Vector<Object>>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		sql.append("select i.coditcontr, i.descitcontr from vdcontrato c, vditcontrato i ");
		sql.append("where c.codemp=i.codemp and c.codfilial=i.codfilial and c.codcontr=i.codcontr ");
		sql.append("and c.codemp=? and c.codfilial=? and c.codcontr=?");

		try {

			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("VDCONTRATO"));
			ps.setInt(3, codcontr);

			rs = ps.executeQuery();

			vVals.addElement(-1);
			vLabs.addElement(textonulo);

			int icont = 0;

			while (rs.next()) {

				vVals.addElement(new Integer(rs.getInt("coditcontr")));
				vLabs.addElement(rs.getString("descitcontr"));
				icont++;

			}

			ret.put("VAL", vVals);
			ret.put("LAB", vLabs);

			rs.close();
			ps.close();

			con.commit();

		}
		catch (SQLException err) {
			err.printStackTrace();
			Funcoes.mensagemErro(null, "Erro ao montar combo de �tens de contrato!");
		}
		return ret;
	}

}
