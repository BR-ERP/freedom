/**
 * @version 06/02/2006 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FPassword.java <BR>
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
 * FFDialog para valida��o de senha.
 */
package org.freedom.telas;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;

public class FPassword extends FFDialogo{

	public static final long serialVersionUID = 1L;
	/**
	 * Permiss�o para vender o produto abaixo do custo.
	 */
	public static final int BAIXO_CUSTO = 0;
	/**
	 * Permiss�o para abrir gaveta do PDV.
	 */
	public static final int ABRE_GAVETA = 1;
	/**
	 * Permiss�o para alterar as parcelas no fechamento da venda.
	 */
	public static final int ALT_PARC_VENDA = 2;
	
	private JTextFieldPad txtUsu = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JPasswordFieldPad txtPass = new JPasswordFieldPad(8);
	private String[] param = null;
	private int tipo = 0;
	private int[] log = null;
	
	/**
	 * 
	 * @param arg0 Component pai.
	 * @param arg1 Tipo da permiss�o.
	 * @param arg2 Parametros para o log(para o tipo BAIXO_CUSTO).
	 * @param arg3 Titulo.
	 * @param arg4 Conex�o.
	 */
	public FPassword(Component arg0,int arg1, String[] arg2,String arg3, Connection arg4) {
		super(arg0);			
		tipo = arg1;		
		setParam(arg2);
		setTitulo(arg3);
		setConexao(arg4);
		montaTela();
	}
	
	public FPassword(Component arg0,int arg1, String arg2, Connection arg3) {
		this(arg0,arg1,null,arg2,arg3);
	}
	
	private void montaTela() {
		setAtribos(300, 140);
		adic(new JLabelPad("Usu�rio: "), 7, 10, 100, 20);
		adic(new JLabelPad("Senha: "), 7, 30, 100, 20);
		adic(txtUsu, 110, 10, 150, 20);
		adic(txtPass, 110, 30, 150, 20);
		adic(new JLabelPad("Senha: "), 7, 30, 100, 20);		
		
		txtUsu.setVlrString(Aplicativo.strUsuario);
		setPrimeiroFoco(txtPass);
	}
	
	public void execShow(){
		setVisible(true);
		firstFocus();
	}
	
	public void ok() {
		boolean ret = false;
		
		switch (tipo) {
		case BAIXO_CUSTO:
			ret = getBaixoCusto();
			break;
		case ABRE_GAVETA:
			ret = getAbreGaveta();
			break;
		case ALT_PARC_VENDA:
			ret = getAltParcVenda();
			break;
		default:
			break;
		}

        OK = ret;
        setVisible(false);
	}
	
	private boolean getBaixoCusto() {
		boolean ret = false;
		try {
			if((ret = getPermissao(BAIXO_CUSTO))) {
				log = Aplicativo.gravaLog(txtUsu.getVlrString().toLowerCase().trim(), "PR", "LIB",
						"Libera��o de "  + param[0] + " abaixo do custo",
						""+param[0]+" [" + param[1] + "], " + 		//codigo da tabela
						"Item: ["    	 + param[2] + "], " + 		//codigo do item
						"Produto: [" 	 + param[3] + "], " +		//codigo do produto
						"Pre�o: ["   	 + param[4] + "]"			//pre�o do produto
						, con);
			} else
				Funcoes.mensagemErro(this,"A��o n�o permitida para este usu�rio.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}	

	private boolean getAbreGaveta() {
		boolean ret = false;
		try {
			if(!(ret = getPermissao(ABRE_GAVETA)))
				Funcoes.mensagemErro(this,"A��o n�o permitida para este usu�rio.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}	

	private boolean getAltParcVenda() {
		boolean ret = false;
		try {
			if(!(ret = getPermissao(ALT_PARC_VENDA)))
				Funcoes.mensagemErro(this,"A��o n�o permitida para este usu�rio.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	} 	

	private boolean getPermissao(int tipo) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Properties props = null;
		String sIDUsu = null;
		String sSQL = null;
		boolean[] permissoes = new boolean[]{false,false,false};
		try {
			do {
				try {
					props = new Properties();
					sIDUsu = txtUsu.getVlrString().toLowerCase().trim();
					props.put("user", sIDUsu);
					props.put("password", txtPass.getVlrString());
					if (sIDUsu.equals("") || txtPass.getVlrString().trim().equals("")) {
						Funcoes.mensagemErro(this, "Campo em branco!");
						continue;
					}
					DriverManager.getConnection(Aplicativo.strBanco, props).close();
					
					sSQL = "SELECT BAIXOCUSTOUSU, ABREGAVETAUSU, ALTPARCVENDA FROM SGUSUARIO "
						 + "WHERE IDUSU = ? AND CODEMP=? AND CODFILIAL=?";
					ps = con.prepareStatement(sSQL);
					ps.setString(1, sIDUsu);
					ps.setInt(2, Aplicativo.iCodEmp);
					ps.setInt(3, Aplicativo.iCodFilial);
					rs = ps.executeQuery();
					if (rs.next()) {
						if ((rs.getString(1) != null ? rs.getString(1) : "").equals("S")) {
							permissoes[0] = true;
						} 
						if ((rs.getString(2) != null ? rs.getString(1) : "").equals("S")) {
							permissoes[1] = true;
						}
						if ((rs.getString(3) != null ? rs.getString(1) : "").equals("S")) {
							permissoes[2] = true;
						}
					} 
					
				} catch(SQLException sqle){
					if (sqle.getErrorCode() == 335544472) {
						Funcoes.mensagemErro(this,"Nome do usu�rio ou senha inv�lidos ! ! !");
						continue;
					}
					Funcoes.mensagemErro(this,"Erro ao verificar senha.",true,con,sqle);
					sqle.printStackTrace();
					return false;
				} catch(Exception e){
					e.printStackTrace();
				} finally {
					ps = null;
					rs = null;
					props = null;
					sIDUsu = null;
					sSQL = null;
				}
				break;
			} while(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return permissoes[tipo];
	}	
	
	public String[] getLog() {
		return new String[] {Aplicativo.iCodEmp+"",log[0]+"",log[1]+""};
	}
		
	public void setParam(String[] arg) {
		param = arg;
	}
	
	public void setTitulo(String arg) {
		if(!(arg != null && arg.trim().length() > 0))
			arg = "Permiss�o";
		super.setTitulo(arg);
	}

}
