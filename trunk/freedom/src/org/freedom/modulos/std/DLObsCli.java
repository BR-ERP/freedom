/**
 * @version 22/10/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLObsCli.java <BR>
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
 * Dialog de edi��o e inser��o de observa��es por data nos clientes
 */
package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JScrollPane;

import org.freedom.componentes.JTextAreaPad;
import org.freedom.telas.FFDialogo;



/**
 * @author robson
 *
 * 
 */
public class DLObsCli extends FFDialogo {
    private JTextAreaPad txaObs = new JTextAreaPad();
    private JScrollPane spnObs = new JScrollPane(txaObs);

    public DLObsCli(Component cOrig) {
        super(cOrig);
        setAtribos(350,250);
        c.add(spnObs,BorderLayout.CENTER);
        //btOK.addActionListener(this);
        //btCancel.addActionListener(this);
    }
    
    public String getTexto() {
        return txaObs.getText();
    }
    
    public void setTexto(String sTexto) {
        txaObs.setText(sTexto);
    }
    
    /*public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btOK) 
            OK = true;
        else if (evt.getSource() == btCancel)
            OK = false;
        super.actionPerformed(evt);
    }*/
}

