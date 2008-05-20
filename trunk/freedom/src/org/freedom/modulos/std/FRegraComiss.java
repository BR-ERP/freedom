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

public class FRegraComiss extends FDetalhe {
	
	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();
	
	private JPanelPad pinDet = new JPanelPad();
	
	private JTextFieldPad txtCodRegrComis = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
	
	private JTextFieldPad txtDescRegrComis = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	
	private JTextFieldPad txtCodTipoVend= new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
	
	private JTextFieldFK txtDescTipoVend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	
	private JTextFieldPad txtPercComis = new JTextFieldPad(JTextFieldPad.TP_INTEGER,7,0);
	
	private JTextFieldPad txtSeqComis = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	
	private JCheckBoxPad cbObrig = new JCheckBoxPad("Sim","S","N");
	
	private ListaCampos lcTipoVend = new ListaCampos(this);
	
	public FRegraComiss () {
		
		
		setTitulo("Regra de comissionamento");
		setAtribos( 50, 50, 600, 450);
		pinCab = new JPanelPad(440,70);
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);
		adicCampo(txtCodRegrComis, 7, 20, 100, 20,"CodRegrComis","C�d.Reg.Comiss", ListaCampos.DB_PK,true);
		adicCampo(txtDescRegrComis, 110, 20, 220, 20,"DescRegrComis","Descri��o da regra de comissionado", ListaCampos.DB_SI, true);
		setListaCampos( true, "REGRACOMIS", "VD");
        lcCampos.setQueryInsert(false);

		lcTipoVend.setUsaME(false);
		lcTipoVend.add(new GuardaCampo( txtCodTipoVend, "CodTipoVend", "C�d.tipo.vend",  ListaCampos.DB_PK, false));
		lcTipoVend.add(new GuardaCampo( txtDescTipoVend, "DescTipoVend", "Descri��o do tipo de comissionado",  ListaCampos.DB_SI, false));
		lcTipoVend.montaSql(false, "TIPOVEND", "VD");    
		lcTipoVend.setQueryCommit(false);
		lcTipoVend.setReadOnly(true);
		txtCodTipoVend.setTabelaExterna(lcTipoVend);
		

		setAltDet(90);
		
		pinDet = new JPanelPad(600,90);
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		adicCampo( txtCodTipoVend, 7, 25, 110, 20,"CodTipoVend","C�d.tipo.vend", ListaCampos.DB_PF,true);
		adicDescFK( txtDescTipoVend, 120, 25, 220, 20, "DescTipoVend", "Descri��o do tipo de comissionado");
		adicCampo( txtPercComis, 343, 25, 55, 20,"PercComisItRc","% Comis", ListaCampos.DB_SI,true);
		adicCampo( txtSeqComis, 401, 25, 60, 20,"SeqItRc","Sequ�ncia", ListaCampos.DB_PK,true);
		adicDB( cbObrig, 475, 25, 87, 20, "ObrigItRc", "Obrigat�rio?",true);
		
		setListaCampos( false, "ITREGRACOMIS", "VD");
        lcDet.setQueryInsert(false);
		
        montaTab();
		
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
	}
	public void setConexao(Connection con) {
		
		super.setConexao(con);
		lcTipoVend.setConexao(con);
	}
}
