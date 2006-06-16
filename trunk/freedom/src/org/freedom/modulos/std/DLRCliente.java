/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRCliente.java <BR>
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

package org.freedom.modulos.std;
import java.awt.Component;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FFDialogo;

public class DLRCliente extends FFDialogo {

	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCid = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
	private JTextFieldPad txtDe = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
	private JTextFieldPad txtA = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
	private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodTipoCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodClasCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldFK txtDescTipoCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldFK txtDescClasCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JCheckBoxPad cbObs = new JCheckBoxPad("Imprimir Observa��es ?","S","N");
	private JCheckBoxPad cbFis = new JCheckBoxPad("F�sica","S","N");
	private JCheckBoxPad cbJur = new JCheckBoxPad("Jur�dica","S","N");
	private JRadioGroup rgOrdem = null;
	private JRadioGroup rgModo = null;
	private ListaCampos lcSetor = new ListaCampos(this);
	private ListaCampos lcTipoCli = new ListaCampos(this);
	private ListaCampos lcClasCli = new ListaCampos(this);
	private ListaCampos lcVendedor = new ListaCampos(this);
	private Vector vLabs = new Vector();
	private Vector vVals = new Vector();
	private Vector vLabsModo = new Vector();
	private Vector vValsModo = new Vector();
	
	public DLRCliente(Component cOrig, Connection cn) {
		super(cOrig);
		setTitulo("Relat�rio de Clientes");
		setAtribos(465,520);
		setLocationRelativeTo( this );
		
		vLabs.addElement("C�digo");
		vLabs.addElement("Raz�o");
		vLabs.addElement("Cidade");
		vVals.addElement("C");
		vVals.addElement("R");
		vVals.addElement("I");
		rgOrdem = new JRadioGroup(1,3,vLabs,vVals);
		rgOrdem.setVlrString("R");
		
		vLabsModo.addElement("Resumido 1");
		vLabsModo.addElement("Resumido 2");
		vLabsModo.addElement("Completo");
		vLabsModo.addElement("Alinhar  Filial");
		vValsModo.addElement("R");
		vValsModo.addElement("J");
		vValsModo.addElement("C");
		vValsModo.addElement("A");
		
		rgModo = new JRadioGroup(1,4,vLabsModo,vValsModo);
		rgModo.setVlrString("R");
		
		cbObs.setVlrString("N");
		cbFis.setVlrString("S");
		cbJur.setVlrString("S");
		
		lcSetor.add(new GuardaCampo( txtCodSetor, "CodSetor", "C�d.setor", ListaCampos.DB_PK,false));
		lcSetor.add(new GuardaCampo( txtDescSetor, "DescSetor", "Descri��o do setor",  ListaCampos.DB_SI,false));
		lcSetor.montaSql(false, "SETOR", "VD");
		lcSetor.setReadOnly(true);
		txtCodSetor.setTabelaExterna(lcSetor);
		txtCodSetor.setFK(true);
		txtCodSetor.setNomeCampo("CodSetor");
		
		lcTipoCli.add(new GuardaCampo( txtCodTipoCli, "CodTipoCli", "C�d.tp.cli.",  ListaCampos.DB_PK,false));
		lcTipoCli.add(new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descri��o do tipo de cliente",  ListaCampos.DB_SI, false));
		lcTipoCli.montaSql(false, "TIPOCLI", "VD");
		lcTipoCli.setReadOnly(true);
		txtCodTipoCli.setTabelaExterna(lcTipoCli);
		txtCodTipoCli.setFK(true);
		txtCodTipoCli.setNomeCampo("CodTipoCli");
		
		lcClasCli.add(new GuardaCampo( txtCodClasCli, "CodClasCli", "C�d.cl.cli.",  ListaCampos.DB_PK,false));
		lcClasCli.add(new GuardaCampo( txtDescClasCli, "DescClasCli", "Descri��o da classifica��o do cliente",  ListaCampos.DB_SI, false));
		lcClasCli.montaSql(false, "CLASCLI", "VD");
		lcClasCli.setReadOnly(true);
		txtCodClasCli.setTabelaExterna(lcClasCli);
		txtCodClasCli.setFK(true);
		txtCodClasCli.setNomeCampo("CodClasCli");
		
		lcVendedor.add(new GuardaCampo( txtCodVend, "CodVend", "C�d.comiss.",  ListaCampos.DB_PK,false));
		lcVendedor.add(new GuardaCampo( txtNomeVend, "NomeVend", "Nome do comissionado",  ListaCampos.DB_SI,false));
		lcVendedor.montaSql(false, "VENDEDOR", "VD");
		lcVendedor.setReadOnly(true);
		txtCodVend.setTabelaExterna(lcVendedor);
		txtCodVend.setFK(true);
		txtCodVend.setNomeCampo("CodVend");
		    
		JLabelPad lbOrdem = new JLabelPad("   Ordenar por:");
		lbOrdem.setOpaque( true );
		JLabelPad lbBordaOrdem = new JLabelPad();
		lbBordaOrdem.setBorder( BorderFactory.createEtchedBorder());
		adic(lbOrdem,15,5,100,20);
		adic(lbBordaOrdem,7,15,433,60);
		adic(rgOrdem,15,30,240,30);
		adic(cbObs,270,35,190,20);
		
		JLabelPad lbSelecao = new JLabelPad("   Sele��o :");
		lbSelecao.setOpaque( true );
		JLabelPad lbBordaSelecao = new JLabelPad();
		lbBordaSelecao.setBorder( BorderFactory.createEtchedBorder());
		adic(lbSelecao,15,75,85,20);
		adic(lbBordaSelecao,7,85,433,70);
		adic(new JLabelPad("De:",SwingConstants.RIGHT),15,100,30,20);
		adic(txtDe,50,100,375,20);
		adic(new JLabelPad("�:",SwingConstants.RIGHT),15,125,30,20);
		adic(txtA,50,125,375,20);
		
		JLabelPad lbPessoa = new JLabelPad("   Pessoa :");
		lbPessoa.setOpaque( true );
		JLabelPad lbBordaPessoa = new JLabelPad();
		lbBordaPessoa.setBorder( BorderFactory.createEtchedBorder());
		adic(lbPessoa,15,155,85,20);
		adic(lbBordaPessoa,7,165,256,40);
		adic(cbFis,40,175,80,20);
		adic(cbJur,160,175,80,20);	
		
		adic(new JLabelPad("Cidade"),270,165,140,20);
		adic(txtCid,270,185,170,20);
		
		adic(new JLabelPad("Modo do relat�rio:"),7,205,170,20);
		adic(rgModo,7,225,433,30);
		
		adic(new JLabelPad("C�d.setor"),7,260,80,20);
		adic(txtCodSetor,7,280,80,20);
		adic(new JLabelPad("Descri��o do setor"),90,260,350,20);
		adic(txtDescSetor,90,280,350,20);
		adic(new JLabelPad("C�d.comiss."),7,300,80,20);
		adic(txtCodVend,7,320,80,20);
		adic(new JLabelPad("Nome do comissionado"),90,300,350,20);
		adic(txtNomeVend,90,320,350,20);
		adic(new JLabelPad("C�d.tp.cli."),7,340,80,20);
		adic(txtCodTipoCli,7,360,80,20);
		adic(new JLabelPad("Descri��o do tipo de cliente"),90,340,350,20);
		adic(txtDescTipoCli,90,360,350,20);
		adic(new JLabelPad("C�d.cl.cli."),7,380,80,20);
		adic(txtCodClasCli,7,400,80,20);
		adic(new JLabelPad("Descri��o da classifica��o do cliente"),90,380,350,20);
		adic(txtDescClasCli,90,400,350,20);
		
		lcSetor.setConexao(cn);
		lcTipoCli.setConexao(cn);
		lcClasCli.setConexao(cn);
		lcVendedor.setConexao(cn);
		
	}
	
	public String[] getValores() {
		
		String[] sRetorno = new String[17];
		
		if (rgOrdem.getVlrString().equals("C"))
			sRetorno[0] = "C1.CODCLI";
		else if (rgOrdem.getVlrString().equals("R"))
			sRetorno[0] = "C1.RAZCLI";
		else if (rgOrdem.getVlrString().equals("I"))
			sRetorno[0] = "C1.CIDCLI, C1.RAZCLI";
		sRetorno[1] = cbObs.getVlrString();
		sRetorno[2] = txtDe.getText();
		sRetorno[3] = txtA.getText();
		sRetorno[4] = cbFis.getVlrString();
		sRetorno[5] = txtCid.getText();        
		sRetorno[6] = cbJur.getVlrString();
		sRetorno[7] = rgModo.getVlrString();
		sRetorno[8] = txtCodSetor.getText();
		sRetorno[9] = txtDescSetor.getText();
		sRetorno[10] = txtCodTipoCli.getText();
		sRetorno[11] = txtDescTipoCli.getText();
		sRetorno[12] = rgOrdem.getVlrString();
		sRetorno[13] = txtCodVend.getVlrString();
		sRetorno[14] = txtNomeVend.getVlrString();
		sRetorno[15] = txtCodClasCli.getVlrString();
		sRetorno[16] = txtDescClasCli.getText();
		
		return sRetorno;
		
	}
}
