package org.freedom.modulos.pdv;

/**
 * @version 30/06/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe: @(#)FFechaVenda.java <BR>
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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.comutacao.Tef;
import org.freedom.telas.FFDialogo;

public class DLAdmTef extends FFDialogo implements ActionListener {
    JComboBoxPad cbComando = null;
    JButton btExec = new JButton(Icone.novo("btExecuta.gif"));
    Tef tef;
	public DLAdmTef(Tef tef, Component cPai) {
		super(cPai);
		this.tef = tef;
		
		Vector vLabs = new Vector();
		Vector vVals = new Vector();
		vLabs.addElement("Administrativas - Outras"); 
		vLabs.addElement("Administrativa - Fechamento/Transmiss�o de Lote"); 
		vVals.addElement("00"); 
		vVals.addElement("01");
		cbComando = new JComboBoxPad(vLabs,vVals,JTextFieldPad.TP_STRING,2,0);
		
		setTitulo("Administra��o TEF");
		setAtribos(350,150);
		
		adic(new JLabelPad("Comando a ser disparado:"),10,10,250,15);
		adic(cbComando,10,25,250,20);
		adic(btExec,270,15,30,30);
		
		btExec.addActionListener(this);
		
	}
	private void processaTef() {
		boolean bRet = false;
		Properties retTef = tef.solicAdm(cbComando.getVlrString());
		
		if (retTef == null || !tef.validaTef(retTef))
		    return;
		
		retTef.list(System.out);
		
	}
	public void actionPerformed(ActionEvent evt) {
	    if (evt.getSource() == btExec)
	        processaTef();
	    super.actionPerformed(evt);
	}
}

