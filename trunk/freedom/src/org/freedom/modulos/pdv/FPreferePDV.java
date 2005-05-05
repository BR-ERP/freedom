/**
 * @version 23/03/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe: @(#)FPrefere.java <BR>
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
 * Tela para cadastro de prefer�ncias do PDV
 * 
 */


package org.freedom.modulos.pdv;
import java.awt.event.ActionListener;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FTabDados;

public class FPreferePDV extends FTabDados implements ActionListener {
	private JPanelPad pinVenda = new JPanelPad();
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8 , 0);
	private JTextFieldFK txtDescTipoMov= new JTextFieldFK(JTextFieldPad.TP_STRING, 50 , 0);
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8 , 0);
	private JTextFieldFK txtDescPlanoPag= new JTextFieldFK(JTextFieldPad.TP_STRING, 50 , 0);
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8 , 0);
	private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING, 50 , 0);
	private ListaCampos lcTipoMov = new ListaCampos(this,"TM");
	private ListaCampos lcPlanoPag = new ListaCampos(this,"PP");
	private ListaCampos lcCliente = new ListaCampos(this,"CL");
	public FPreferePDV() {
		setTitulo("Prefer�ncias do PDV");
		setAtribos(50, 50, 355, 375);
		
		lcTipoMov.add(new GuardaCampo(txtCodTipoMov,"CodTipoMov","C�d.tp.mov.",ListaCampos.DB_PK,true));
		lcTipoMov.add(new GuardaCampo(txtDescTipoMov,"DescTipoMov","Descri��o do tipo de movimento",ListaCampos.DB_SI,false));
		lcTipoMov.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov.setReadOnly(true);
		txtCodTipoMov.setTabelaExterna(lcTipoMov);
		txtCodTipoMov.setFK(true);
		txtCodTipoMov.setNomeCampo("CodTipoMov");
		
		lcCliente.add(new GuardaCampo(txtCodCli,"CodCli","C�d.tp.mov.",ListaCampos.DB_PK,true));
		lcCliente.add(new GuardaCampo(txtRazCli,"RazCli","Raz�o do cliente padr�o",ListaCampos.DB_SI,false));
		lcCliente.montaSql(false, "CLIENTE", "VD");
		lcCliente.setReadOnly(true);
		txtCodCli.setTabelaExterna(lcCliente);
		txtCodCli.setFK(true);
		txtCodCli.setNomeCampo("CodCli");

		lcPlanoPag.add(new GuardaCampo(txtCodPlanoPag,"CodPlanoPag","C�d.cli.",ListaCampos.DB_PK,true));
		lcPlanoPag.add(new GuardaCampo(txtDescPlanoPag,"DescPlanoPag","Descri��o do plano de pagamento",ListaCampos.DB_SI,false));
		lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag.setReadOnly(true);
		txtCodPlanoPag.setTabelaExterna(lcPlanoPag);

		setPainel(pinVenda);
		adicTab("Venda", pinVenda);
		adicCampo(txtCodTipoMov,10,30,77,20,"CodTipoMov","C�d.tp.mov.",ListaCampos.DB_FK,true);
		adicDescFK(txtDescTipoMov,90,30,230,20,"DescTipoMov","Descri��o do tipo de movimento");
		adicCampo(txtCodPlanoPag,10,70,77,20,"CodPlanoPag","C�d.p.pag.",ListaCampos.DB_FK,true);
		adicDescFK(txtDescPlanoPag,90,70,230,20,"DescPlanoPag","Descri��o do plano de pagamento");
		adicCampo(txtCodCli,10,110,77,20,"CodCli","C�d.cli.",ListaCampos.DB_FK,true);
		adicDescFK(txtRazCli,90,110,230,20,"RazCli","Raz�o do cliente padr�o");
		setListaCampos(false, "PREFERE4", "SG");

		nav.setAtivo(0,false);
		nav.setAtivo(1,false);
	}
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcTipoMov.setConexao(cn);
		lcPlanoPag.setConexao(cn);
		lcCliente.setConexao(cn);
		lcCampos.carregaDados();
	}
}