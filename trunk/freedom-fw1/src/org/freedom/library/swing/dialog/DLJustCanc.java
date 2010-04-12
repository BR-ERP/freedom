/**
 * @author Setpoint Inform�tica Ltda.
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)DLJustCanc.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 */
package org.freedom.library.swing.dialog;

import javax.swing.JScrollPane;

import org.freedom.funcoes.Funcoes;
import org.freedom.library.swing.JTextAreaPad;


public class DLJustCanc extends FFDialogo {

	private static final long serialVersionUID = 1L;
	
	private JTextAreaPad txaJustCanc = new JTextAreaPad();
	
	public DLJustCanc(){
			
		setTitulo("Justificativa do cancelamento");
		setAtribos( 330, 190 );
		
		txaJustCanc.requestFocus();
		adic( new JScrollPane( txaJustCanc ), 7, 7, 300, 70 );
		
	}

	public String getValor(){
		
		String sRet = "";
		
		if( txaJustCanc.getVlrString().equals( "" )){
			sRet = "";
		}
		else{
			sRet = txaJustCanc.getVlrString();
		}
		return sRet;
	}
    public void ok(){
    	if ((txaJustCanc.getVlrString().equals(""))){
	        Funcoes.mensagemInforma(this,"Informe o motivo do cancelamento!");
    	    return;
    	}
    	else{
    	    super.ok();
	    }
    }
}
