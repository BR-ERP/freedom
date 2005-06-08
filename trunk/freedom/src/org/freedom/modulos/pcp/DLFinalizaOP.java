/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLDescontItVenda.java <BR>
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
 */

package org.freedom.modulos.pcp;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLFinalizaOP extends FFDialogo implements FocusListener{
	private JTextFieldPad txtQtdPrevOP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
	private JTextFieldPad txtQtdFinalOP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
	private JTextAreaPad txaJustifcQtdProd = new JTextAreaPad();
    private double dVlr;
    private String sObs;
    boolean bJust = false;
	public DLFinalizaOP(Component cOrig,String sQtdPrevOp) {
		super(cOrig);
        txtQtdPrevOP.setVlrString(sQtdPrevOp);
		setTitulo("Finaliza��o da OP.");
		setAtribos(300,250);
		
		txtQtdPrevOP.setAtivo(false);
		adic(new JLabelPad("Quantidade prevista"),7,5,150,20);
		adic(txtQtdPrevOP,7,25,77,20);
		adic(new JLabelPad("Quantidade produzida:"),7,50,150,20);
		adic(txtQtdFinalOP,7,70,77,20); 
		
		txaJustifcQtdProd.setEnabled(bJust);
		
		adic(new JLabelPad("Justificativa da quantidade"),7,90,300,20);
		adic(txaJustifcQtdProd,7,110,270,50);
		
	}
	public void focusLost(FocusEvent fevt){
		if (fevt.getSource() == txtQtdFinalOP){
			ok();
		}
	}
	public void focusGained(FocusEvent fevt){
		
	}

	public double getValor() {
		return txtQtdFinalOP.getVlrDouble().doubleValue();
	}
    public String getObs() {
    	return "";
    }
    public void keyPressed(KeyEvent kevt) {
           super.keyPressed(kevt);
    }
    
    public void ok(){
    	if (txtQtdPrevOP.getVlrDouble().doubleValue() == getValor())
			super.ok();
    	else{
	    		bJust = true;
	    		Funcoes.mensagemErro(this,
							"Justifique a quantidade produzida.");
	    		return;
	    }
    }
}
