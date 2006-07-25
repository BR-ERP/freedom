/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)Aplicativo.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para
 * Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Classe herdada de Aplicativo contendo fun��es espec�ficas para ponto de venda.
 */
package org.freedom.telas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.pdv.FAbreCaixa;

/**
 * @author anderson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AplicativoPDV extends Aplicativo {
	public static boolean bECFTerm = false;
	public static boolean bTEFTerm = false;
	public static int iCodCaixa = 0;
	
	public boolean pegaValorINI() {
		boolean bRetorno = false;
		FAbreCaixa tela = new FAbreCaixa();
		tela.setConexao(con);
		tela.setVisible(true);
		bRetorno = tela.OK;
		return bRetorno;
	}
	
	public boolean abreCaixa() {
		boolean bRetorno = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int iRet = 0;
		try {
			ps = con.prepareStatement("SELECT CODCAIXA FROM PVCAIXA WHERE CODEMP=? AND CODFILIAL=? AND CODEST=?");
			ps.setInt(1,iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("PVCAIXA"));
			ps.setInt(3,iNumEst);
			rs = ps.executeQuery();
			if (rs.next()) {
				iCodCaixa = rs.getInt("CODCAIXA");
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			
			setECF();
			
			ps = con.prepareStatement("SELECT IRETORNO FROM PVVERIFCAIXASP(?,?,?,?,?,?)"); // caixa,
																						   // emp,
																						   // filial
			ps.setInt(1, iCodCaixa);
			ps.setInt(2, iCodEmp);
			ps.setInt(3, iCodFilial);
			ps.setDate(4, Funcoes.dateToSQLDate(new Date()));
			ps.setInt(5, iCodFilialPad);
			ps.setString(6, strUsuario);
			rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt(1);
				switch (iRet) {
				case 0: {
					bRetorno = pegaValorINI();
					break;
				}
				//case 1: { Reservado.
				case 2: {
					Funcoes.mensagemInforma(null, "Caixa j� est� aberto!");
					bRetorno = true;
					break;
				}
				case 3: {
					killProg(3,
							"J� foi realizada leitura \"Z\" neste caixa hoje!");
					break;
				}
				case 4: {
					killProg(4, "Caixa foi aberto com outro usu�rio!");
					break;
				}
				default: {
					killProg(5, "Erro na ultima transac�o de caixa.");
					break;
				}
				}
				rs.close();
				ps.close();
				if (!con.getAutoCommit())
					con.commit();

			} else {
				killProg(5, "N�o foi poss�vel abrir o caixa!");
			}
		} catch (Exception err) {
			killProg(6, "Erro abrir o caixa!\n" + err.getMessage());
		}
		return bRetorno;
	}

	private void setECF() {
		String sSQL = "SELECT CX.ECFCAIXA,CX.TEFCAIXA " +
		"FROM PVCAIXA CX WHERE CODCAIXA=?" +
		" AND CODFILIAL=? AND CODEMP=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, iCodCaixa);
			ps.setInt(2, Aplicativo.iCodFilial);
			ps.setInt(3, Aplicativo.iCodEmp);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("ECFCaixa") != null && rs.getString("ECFCaixa").equals("S"))
					bECFTerm = true;
				else
					bECFTerm = false;
				if (rs.getString("TEFCaixa") != null && rs.getString("TEFCaixa").equals("S"))
					bTEFTerm = true;
				else
					bTEFTerm = false;
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (Exception err) {
		    err.printStackTrace();
			killProg(6, "Erro ao verificar o caixa!\n" + err.getMessage());
		}
		
	}
	public AplicativoPDV(String sIcone, String sSplash, int iCodSis, String sDescSis, 
			int iCodModu, String sDescModu, String sDirImagem) {
	    super(sIcone, sSplash, iCodSis, sDescSis,iCodModu, sDescModu, sDirImagem,new FPrincipalPD("bgFreedomSTD.jpg"));
	}
    
}
