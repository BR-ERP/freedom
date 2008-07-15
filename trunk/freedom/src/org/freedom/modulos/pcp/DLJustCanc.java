/**
 * @author Setpoint Inform�tica Ltda.
 * @author Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)DLJustCanc.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 */
package org.freedom.modulos.pcp;

import javax.swing.JScrollPane;

import org.freedom.componentes.JTextAreaPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;


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
