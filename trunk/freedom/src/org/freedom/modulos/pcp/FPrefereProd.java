/**
 * @version 25/09/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FPrefereProd.java <BR>
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

package org.freedom.modulos.pcp;
import java.sql.Connection;

import javax.swing.JLabel;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FTabDados;

public class FPrefereProd extends FTabDados {
	private JTextFieldPad txtClass = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
    private JTextFieldPad txtNomeResp = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
    private JTextFieldPad txtIdentProfResp = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
    private JTextFieldPad txtCargoResp = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
	private JPanelPad pinGeral = new JPanelPad();
	public FPrefereProd() {
		setTitulo("Prefer�ncias de Produ��o");
		setAtribos(50, 50, 330, 375);
		
        setPainel(pinGeral);
        adicTab("Geral", pinGeral);

        JPanelPad pinRespon = new JPanelPad();
        JLabel lbRespon = new JLabel(" Repons�vel t�cnico");
        lbRespon.setOpaque(true);
        
        adic(lbRespon,12,10,130,20);
        adic(pinRespon,7,20,250,150);
        setPainel(pinRespon);
        adicCampo(txtNomeResp,7,30,200,20,"NOMERESP","Nome do repons�vel", ListaCampos.DB_SI, false);
        adicCampo(txtIdentProfResp,7,70,200,20,"IDENTPROFRESP","Indent.prof.", ListaCampos.DB_SI, false);
        adicCampo(txtCargoResp,7,110,200,20,"CARGORESP","Cargo", ListaCampos.DB_SI, false);
        
        setPainel(pinGeral);

        adicCampo(txtClass,7,200,200,20,"CLASSOP","Classe padr�o para O.P.", ListaCampos.DB_SI,false);

		setListaCampos(false, "PREFERE5", "SG");
		
        nav.setAtivo(0,false);
        nav.setAtivo(1,false);
        
	}
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcCampos.carregaDados();
	}
}