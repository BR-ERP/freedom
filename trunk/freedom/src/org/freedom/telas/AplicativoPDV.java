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
	public static boolean bModoDemo = true;
	
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
		int iRet = 0;
		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT IRETORNO FROM PVVERIFCAIXASP(?,?,?,?,?,?)"); // caixa,
																						   // emp,
																						   // filial
			ps.setInt(1, iNumEst);
			ps.setInt(2, iCodEmp);
			ps.setInt(3, iCodFilial);
			ps.setDate(4, Funcoes.dateToSQLDate(new Date()));
			ps.setInt(5, iCodFilialPad);
			ps.setString(6, strUsuario);
			ResultSet rs = ps.executeQuery();
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
			} else {
				killProg(5, "N�o foi poss�vel abrir o caixa!");
			}
		} catch (Exception err) {
			killProg(6, "Erro abrir o caixa!\n" + err.getMessage());
		}
		String sSQL = "SELECT CX.ECFCAIXA,CX.TEFCAIXA,(SELECT MODODEMOEST FROM SGESTACAO EST"
				+ " WHERE EST.CODEMP=CX.CODEMPET AND EST.CODFILIAL=CX.CODFILIALET AND"
				+ " EST.CODEST=CX.CODEST) FROM PVCAIXA CX WHERE CODCAIXA=?"
				+ " AND CODFILIAL=? AND CODEMP=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, iRet);
			ps.setInt(2, Aplicativo.iCodFilial);
			ps.setInt(3, Aplicativo.iCodEmp);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("ECFCaixa") != null && rs.getString("ECFCaixa").equals("S"))
					bECFTerm = true;
				else
					bECFTerm = false;
				if (rs.getString("TEFCaixa") != null && rs.getString("TEFCaixa").equals("S"))
					bTEFTerm = true;
				else
					bTEFTerm = false;
				if (rs.getString(3) != null && rs.getString(3).equals("S"))
					bModoDemo = true;
				else
					bModoDemo = false;
			}
			rs.close();
			ps.close();
		} catch (Exception err) {
		    err.printStackTrace();
			killProg(6, "Erro ao verificar o caixa!\n" + err.getMessage());
		}
		return bRetorno;
	}
    
	public AplicativoPDV(String sIcone, String sSplash, int iCodSis, String sDescSis, 
			int iCodModu, String sDescModu, String sDirImagem) {
	    super(sIcone, sSplash, iCodSis, sDescSis,iCodModu, sDescModu, sDirImagem,"bgFreedomSTD.jpg");
	}
    
}
