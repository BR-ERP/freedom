/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FLogin.java <BR>
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
 * Coment�rios para a classe...
 */

package org.freedom.telas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import org.freedom.infra.model.jdbc.DbConnection;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;

public abstract class Login extends FDialogo implements ActionListener, FocusListener {
	private static final long serialVersionUID = 1L;	
	protected JTextFieldPad txtUsuario = new JTextFieldPad(JTextFieldPad.TP_STRING,15,0);
	protected JPasswordFieldPad txpSenha = new JPasswordFieldPad(9);
	protected Vector<Integer> vVals = new Vector<Integer>();
	protected Vector<String> vLabs = new Vector<String>();
	protected JComboBoxPad cbEmp = new JComboBoxPad(vLabs, vVals, JComboBoxPad.TP_INTEGER, 8 , 0);
	protected String nfe = "";
	protected String strBanco = "";
	protected String strBanconfe = "";
	protected String strDriver = "";
	protected String sUsuAnt = "";
	protected int iFilialMz = 0;
	protected DbConnection conLogin = null;
	protected JLabelPad lbInstrucoes = new JLabelPad("");
	protected Properties props = new Properties();
	public boolean bAdmin = false;
	protected int iFilialPadrao = 0;
	protected int iCodEst = 0;
	protected int tries = 0;
	
	public abstract void inicializaLogin();
	
	public void execLogin(String sBanco, String sDriver, String sImg, int iCodEstP){
		strBanco = sBanco;
		strDriver = sDriver;
			
		this.iCodEst = iCodEstP;

		ImageIcon ic = Icone.novo(sImg); 
		JLabelPad lbImg = new JLabelPad(Icone.novo(sImg));
		int iWidth = ic.getIconWidth();
		int iHeight = ic.getIconHeight();
		setAtribos(iWidth+12,iHeight+170);
		lbImg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

		adic(lbImg,0,0,iWidth,iHeight);
		
		adic(new JLabelPad ("Usuario: "),7,iHeight,180,20);
		adic(txtUsuario,7,iHeight+20,180,20);
		adic(new JLabelPad ("Senha: "),190,iHeight,100,20);
		adic(txpSenha,190,iHeight+20,100,20);
		adic(new JLabelPad("Filial:"),7,iHeight+40,200,20);
		adic(cbEmp,7,iHeight+60,283,20);
		adic(lbInstrucoes,7,iHeight+83,300,20);
		setVisible(true);
		
	}
	
	public Login () {		
		String sUsuarioTst = Aplicativo.getParameter("usuariotst");
		String sSenhaTst = Aplicativo.getParameter("senhatst");
		 	
		setTitulo("Login", this.getClass().getName());		
		lbInstrucoes.setForeground(Color.BLUE );				
		txtUsuario.addFocusListener(this);
		txpSenha.addFocusListener(this);
		cbEmp.addFocusListener(this);
		btOK.addFocusListener(this);
		
		if((sUsuarioTst!=null) && (sSenhaTst!=null)){
			if((sUsuarioTst.length()>0) && (sSenhaTst.length()>0)){
				txtUsuario.setVlrString(sUsuarioTst);
				txpSenha.setVlrString(sSenhaTst);
//				btOK.doClick();			
			}	
		}					
	}
	
	public String[] getStrVals() {
		String[] ret = new String[3];
		ret[0] = txtUsuario.getText().trim().toLowerCase();
		ret[1] = txpSenha.getVlrString();
		return ret;
	}
	  
	public int getFilial() {
		if (cbEmp.getVlrInteger().intValue() == 0)
			return 1;
		return cbEmp.getVlrInteger().intValue();
	}
	
	public String getNomeFilial() {
		String retorno = (String) cbEmp.getItemAt(cbEmp.getSelectedIndex());
		if (retorno==null) {
			retorno = "DEMONSTRA��O";
		}
		return retorno;
	} 
	 
	public int getFilialMz() {
		return iFilialMz;
	}
	  
	public int getFilialPad() {
		return iFilialPadrao;
	}
	
	public abstract DbConnection getConection() throws Exception; 
	
	protected abstract boolean execConexao(String sUsu, String sConexao);
	
	protected abstract boolean montaCombo(String sUsu);
	  
	protected abstract boolean adicConFilial(final DbConnection conX);
	  
	public void focusLost(FocusEvent fevt) { }
	  
	public void focusGained(FocusEvent fevt) {
		
		if ( fevt.getSource()==txtUsuario)
			lbInstrucoes.setText("Digite sua identifica��o de usu�rio!");
		else if ( fevt.getSource()==txpSenha)
			lbInstrucoes.setText("Digite sua senha!");
		else if (fevt.getSource()==cbEmp) {
			if ( !sUsuAnt.equals(txtUsuario.getVlrString().trim().toLowerCase() ) )
				btOK.requestFocus();
			else
				lbInstrucoes.setText("Selecione a filial!");
		}
		else if ( fevt.getSource()==btOK) {
			if ( !sUsuAnt.equals(txtUsuario.getVlrString().trim().toLowerCase() ) ) {	
				lbInstrucoes.setText("Pressione espa�o p/ conectar ao banco de dados!");
				if (tries == 0)  {
					btOK.doClick();
					tries++;
				}			   
			} else
				lbInstrucoes.setText("Pressione espa�o p/ entrar no sistema!");	
		}
		
	}
		  
	public void actionPerformed( ActionEvent evt ) {
	  	
		String sUsu = txtUsuario.getText().trim().toLowerCase();
		          
		if ( evt.getSource() == btOK ) {			
			if ( sUsu.trim().equals("") ) {
				Funcoes.mensagemInforma( this, "Usuario em branco!" );
				txtUsuario.requestFocus();
				return;
			} else if ( txpSenha.getVlrString().trim().equals("") ) {
				Funcoes.mensagemInforma( this, "Senha em branco!" );
				txpSenha.requestFocus();
				return;
			}
			
			if (sUsu.equals("sysdba"))
				bAdmin = true;
			
			if ( !sUsuAnt.equals(sUsu) ) {
				if (!execConexao(sUsu, txpSenha.getVlrString().trim()))
					return;
				montaCombo(sUsu);
				cbEmp.requestFocus();
				if (cbEmp.getItemCount() == 1)
					btOK.doClick();
				return;
			} else if ((cbEmp.getVlrInteger().intValue()==0 ) && (!bAdmin)) {
				if ( sUsuAnt.equals(sUsu) ) {	
					Funcoes.mensagemInforma( this, "Filial n�o foi selecionada!" );
					cbEmp.requestFocus();
					return;
				}
			}
		}
		super.actionPerformed(evt);
		
	}
	
	public static DbConnection getConexao(final String strBanco, final String strDriver, final String sUsu, final String sSenha) {	
	    DbConnection cRetorno = null;
		Properties params = new Properties();
		/*try {
			Class.forName(strDriver);
		} catch (java.lang.ClassNotFoundException e) {
			System.out.println("Driver nao foi encontrado:\n"+strDriver+"\n"+e.getMessage ());
		}*/
		
		try {
			//params.put("user", sUsu);
			//params.put("password", sSenha);
			//cRetorno = DriverManager.getDbConnection(strBanco, params);
			cRetorno = new DbConnection(strDriver, strBanco, sUsu, sSenha);
			cRetorno.setAutoCommit(false);
		} 
		catch (java.sql.SQLException e) {
			if (e.getErrorCode() == 335544472)
				System.out.println("Nome do usu�rio ou senha inv�lidos ! ! !");
			else                                                                             
				System.out.println("N�o foi poss�vel estabelecer conex�o com o banco de dados.\n"+e.getMessage());
			e.printStackTrace();
			return cRetorno;
		}
		return cRetorno;
	}	
}    
