/**
 * @version 06/01/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)DLNovoAgend.java <BR>
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
 * Novo agendamento
 * 
 */

package org.freedom.modulos.atd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;


public class DLNovoAgen extends FFDialogo {
	private JPanel pnCab = new JPanel(new GridLayout(1,1));
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JSpinner txtHoraini = new JSpinner();
	private JSpinner txtHorafim = new JSpinner();
	private JTextFieldPad txtAssunto = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtIdUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
	private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextAreaPad txaDescAtend = new JTextAreaPad();
	private ListaCampos lcUsuario = new ListaCampos(this);
	private JScrollPane spnDesc = new JScrollPane(txaDescAtend);
	private JLabel lbImg = new JLabel(Icone.novo("bannerTMKagendamento.jpg"));
	private JComboBoxPad cbTipo = new JComboBoxPad();
	public DLNovoAgen(Component cOrig) {
		this("",null,cOrig);
	}
	public DLNovoAgen(String sIdUsu,Date Data, Component cOrig) {
		super(cOrig);
		setTitulo("Novo agendamento");
		setAtribos(460,470);
		
//Acertando o spinner		
		GregorianCalendar cal = new GregorianCalendar();
		GregorianCalendar cal1 = new GregorianCalendar();
		GregorianCalendar cal2 = new GregorianCalendar();
	
		if(Data != null) {
			Data.setHours(new Date().getHours());
			Data.setMinutes(new Date().getMinutes());

			cal.setTime(Data);
			cal1.setTime(Data);
			cal2.setTime(Data);
		}
		
		cal.add(Calendar.DATE,0);		
		cal1.add(Calendar.YEAR,-100);		
		cal2.add(Calendar.YEAR,100);
		
		txtDataini.setVlrDate(cal.getTime());
		txtHoraini.setModel(new SpinnerDateModel(cal.getTime(),cal1.getTime(),cal2.getTime(),Calendar.HOUR_OF_DAY));
		txtHoraini.setEditor(new JSpinner.DateEditor(txtHoraini,"kk:mm"));
		txtDatafim.setVlrDate(cal.getTime());
		txtHorafim.setModel(new SpinnerDateModel(cal.getTime(),cal1.getTime(),cal2.getTime(),Calendar.HOUR_OF_DAY));
		txtHorafim.setEditor(new JSpinner.DateEditor(txtHorafim,"kk:mm"));
		
//Construindo o combobox de tipo.		

		Vector vVals = new Vector();
		vVals.addElement("RE");
		vVals.addElement("VI");
		vVals.addElement("LI");
		vVals.addElement("TA");
		vVals.addElement("CO");
		Vector vLabs = new Vector();
		vLabs.addElement("Reuni�o");
		vLabs.addElement("Visita");
		vLabs.addElement("Liga��o");
		vLabs.addElement("Tarefa");
		vLabs.addElement("Compromisso");
		cbTipo.setItens(vLabs,vVals);
		
		lcUsuario.add(new GuardaCampo( txtIdUsu, 7, 20, 80, 20, "IdUsu", "ID", true, false, txtNomeUsu, JTextFieldPad.TP_STRING,true),"txtCodVendx");
		lcUsuario.add(new GuardaCampo( txtNomeUsu, 7, 20, 200, 20, "NomeUsu", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
		lcUsuario.montaSql(false, "USUARIO", "SG");    
		lcUsuario.setReadOnly(true);
		txtIdUsu.setTabelaExterna(lcUsuario);
		txtIdUsu.setFK(true);
		txtIdUsu.setNomeCampo("IdUsu");
		
	    pnCab.setPreferredSize(new Dimension(500,60));
		pnCab.add(lbImg);
	    c.add(pnCab,BorderLayout.NORTH);
	    
		adic(new JLabel("Usu�rio"),7,5,200,20);
		adic(txtIdUsu,7,25,80,20);
	//	adic(txtNomeUsu,90,25,197,20);
		adic(new JLabel("Tipo"),290,5,150,20);
		adic(cbTipo,290,25,100,20);
		adic(new JLabel("Data inicio:"),7,45,100,20);
		adic(txtDataini,7,65,100,20);
		adic(new JLabel("hora"),110,45,87,20);
		adic(txtHoraini,110,65,87,20);
		adic(new JLabel("Data fim:"),200,45,97,20);
		adic(txtDatafim,200,65,100,20);
		adic(new JLabel("hora"),300,45,87,20);
		adic(txtHorafim,300,65,87,20);
		adic(new JLabel("Assunto"),7,85,380,20);
		adic(txtAssunto,7,105,380,20);
		
		JLabel lbChamada = new JLabel(" A��o: ");
		lbChamada.setOpaque(true);
		JLabel lbLinha = new JLabel();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		
		adic(lbChamada,20,130,60,20);
		adic(lbLinha,7,140,433,2);
		adic(spnDesc,7,155,433,180);
		
		if (!sIdUsu.equals("")) {
		  txtIdUsu.setVlrString(sIdUsu);
		  txtIdUsu.setAtivo(false);
		}
	
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			if (txtIdUsu.getVlrString().equals("")) { 
				Funcoes.mensagemInforma(this,"C�digo do leitor inv�lido!");
				return;
			}
			else if (txaDescAtend.getVlrString().equals("")) {
				Funcoes.mensagemInforma(this,"N�o foi digitado nenhuma a��o!");
				return;
			}
		}
		super.actionPerformed(evt);
	}
	public String[] getValores() {
		String[] sVal = new String[9];
		sVal[0] = txtDataini.getVlrString();
		sVal[1] = ((JSpinner.DateEditor)txtHoraini.getEditor()).getTextField().getText();
		sVal[2] = txtDatafim.getVlrString();
		sVal[3] = ((JSpinner.DateEditor)txtHorafim.getEditor()).getTextField().getText();
		sVal[4] = txtAssunto.getVlrString();
		sVal[5] = txaDescAtend.getVlrString();
		sVal[6] = cbTipo.getVlrString();
		sVal[7] = ""+lcUsuario.getCodFilial();
		sVal[8] = txtIdUsu.getVlrString();
		return sVal;
	}
	public void setValores(String[] sVal) {
		txtDataini.setVlrString(sVal[0]);
		((JSpinner.DateEditor)txtHoraini.getEditor()).getTextField().setText(sVal[1]);
		txtDatafim.setVlrString(sVal[2]);
		((JSpinner.DateEditor)txtHorafim.getEditor()).getTextField().setText(sVal[3]);
		txtAssunto.setVlrString(sVal[4]);
		txaDescAtend.setVlrString(sVal[5]);
		cbTipo.setVlrString(sVal[6]);
		lcUsuario.carregaDados();
	}
	public void setConexao(Connection cn) {
		lcUsuario.setConexao(cn);
	}
}
