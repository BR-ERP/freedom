/**
 * @version 20/05/2008 <BR>
 * @author Setpoint Inform�tica Ltda./Felipe Daniel Elias <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRegraFiscal.java <BR>
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

package org.freedom.modulos.std;
import java.sql.Connection;
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FDetalhe;

public class FRestrComiss extends FDetalhe {
	
	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();
	
	private JPanelPad pinDet = new JPanelPad();
	
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
	
	private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	
	private ListaCampos lcCli = new ListaCampos(this);
	
	public FRestrComiss () {
		
		setTitulo("Restri��o de comissionamento");
		setAtribos( 50, 50, 600, 450);
		pinCab = new JPanelPad(440,70);
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);
		montaListaCampos();

		setAltDet(90); 
		
		pinDet = new JPanelPad(600,90);
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
        lcDet.setQueryInsert(false);
        montaTab();
		
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
	}
	
	public void montaListaCampos(){
		
		adicCampo(txtCodCli, 7, 20, 100, 20,"Codcli","C�d.cli", ListaCampos.DB_PK,true);
		adicCampo(txtRazCli, 110, 20, 220, 20,"Razcli","Raz�o social", ListaCampos.DB_SI, false);  
		setListaCampos( true, "CLIENTE", "VD" );
		lcCli.setQueryCommit(false);
		lcCli.setReadOnly(true);
		txtCodCli.setTabelaExterna(lcCli);
		
	}
	public void setConexao(Connection con) {
		
		super.setConexao( con );
		lcCli.setConexao( con );
	
	}
}
