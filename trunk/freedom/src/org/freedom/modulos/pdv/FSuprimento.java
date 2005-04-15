/**
 * @version 15/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe: @(#)FAbreCaixa.java <BR>
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

package org.freedom.modulos.pdv;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.freedom.componentes.JLabelPad;
import javax.swing.SwingConstants;

import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.drivers.JBemaFI32;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;


public class FSuprimento extends FFDialogo {
	private JTextFieldFK txtData = new JTextFieldFK(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtValor = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,10,2);
	private JTextFieldFK txtDataAnt = new JTextFieldFK(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldFK txtSldAnt = new JTextFieldFK(JTextFieldPad.TP_STRING,10,2);
	private JTextFieldFK txtStatusAnt = new JTextFieldFK(JTextFieldPad.TP_STRING,10,0);
	private JTextFieldFK txtUsuarioAnt = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldFK txtUsuarioAtual = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JBemaFI32 bf = (FreedomPDV.bECFTerm ? new JBemaFI32() : null);
	public FSuprimento() {
		super(Aplicativo.telaPrincipal);
		setTitulo("Suprimento de caixa");
		setAtribos(345,275);
		
		txtData.setVlrDate(Calendar.getInstance().getTime());
		txtValor.setVlrBigDecimal(new BigDecimal(0));
		txtUsuarioAtual.setVlrString(Aplicativo.strUsuario);

		txtValor.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSldAnt.setHorizontalAlignment(SwingConstants.RIGHT);
					
		adic(new JLabelPad("�ltima opera��o"),7,5,95,20);
		adic(txtDataAnt,7,25,90,20);
		adic(new JLabelPad("Saldo final"),104,5,80,20);
		adic(txtSldAnt,104,25,80,20);
		adic(new JLabelPad("Status do caixa"),191,5,130,20);
		adic(txtStatusAnt,191,25,130,20);
		
		adic(new JLabelPad("�ltimo operador"),7,45,150,20);
		adic(txtUsuarioAnt,7,65,150,20);
		
		adic(new JLabelPad("Operador atual"),7,100,150,20);
		adic(txtUsuarioAtual,7,120,150,20);		
		adic(new JLabelPad("Data"),164,100,90,20);
		adic(txtData,164,120,90,20);
		
		adic(new JLabelPad("Valor do suprimento"),7,140,120,20);
		adic(txtValor,7,160,120,20);
		
	}	
	private void executaQuery() {
		try	 {
		  
		  PreparedStatement ps = con.prepareStatement("SELECT * FROM PVRETMOVCAIXASP(?,?,?,?)");
		  ps.setInt(1,Aplicativo.iNumEst);
		  System.out.println("caixa: "+Aplicativo.iNumEst);
		  
		  ps.setInt(3,ListaCampos.getMasterFilial("PVMOVCAIXA"));
		  ps.setInt(2,Aplicativo.iCodEmp);
		  ps.setDate(4,Funcoes.dateToSQLDate(new Date()));		  		  
		  ResultSet rs = ps.executeQuery();
		  if (rs.next()) {
		    if (rs.getDate(1)==null) {  
		      Funcoes.mensagemErro(this,"Caixa n�o est� aberto!");
		    }
		    else {
		     txtDataAnt.setVlrDate(rs.getDate(1));
		  
		  
			 txtSldAnt.setVlrString(Funcoes.strDecimalToStrCurrency(10,2,rs.getString(3)));
		     txtUsuarioAnt.setVlrString(rs.getString(4));
		     
		     txtStatusAnt.setVlrString(JBemaFI32.transStatus(rs.getString(2).toCharArray()[0]));	  
		    
		    } 
		  }	
		  if (!con.getAutoCommit())
		  	con.commit();
		  rs.close();
		}		
		  catch(SQLException err){
			Funcoes.mensagemErro(this, "Ocorreu erro na consulta de movimento de caixa!\n"+err.getMessage());	
		  }		
			 			
	}
		
	private void executaSuprimento() {
		 if (!FreedomPDV.bECFTerm || bf.suprimento(Aplicativo.strUsuario,txtValor.getVlrBigDecimal(),"Dinheiro",FreedomPDV.bModoDemo)) {
		  try {
			PreparedStatement ps = con.prepareStatement("EXECUTE PROCEDURE PVSUPRIMENTOSP(?,?,?,?,?,?)");
					
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("PVMOVCAIXA"));
			ps.setBigDecimal(3,txtValor.getVlrBigDecimal());
			ps.setInt(4,Aplicativo.iNumEst);
			ps.setDate(5,Funcoes.dateToSQLDate(new Date()));
            ps.setString(6,Aplicativo.strUsuario);
      	
			
			//ps.setDate(5,Funcoes.dateToSQLDate(new Date()));
			ps.execute();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		  }
		  catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao executar suprimento!\n"+err.getMessage());
		  }
		  if (FreedomPDV.bECFTerm)
		    bf.abreGaveta(Aplicativo.strUsuario,FreedomPDV.bModoDemo);
		}		  
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			executaSuprimento();
		}
		super.actionPerformed(evt);
	}
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		executaQuery();
	}


}
