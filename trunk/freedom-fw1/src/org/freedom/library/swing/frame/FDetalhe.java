/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FDetalhe.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios da classe.....
 */

package org.freedom.library.swing.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.Navegador;
import org.freedom.library.swing.dialog.DLInfo;

public class FDetalhe extends FDados {

	private static final long serialVersionUID = 1L;

	public static boolean comScroll = false;

	private GridLayout glRod = new GridLayout(1, 1);

	private GridLayout glCab = new GridLayout(1, 1);

	private GridLayout glNavCab = new GridLayout(1, 1);

	private BorderLayout blMaster = new BorderLayout();

	private BorderLayout blCab = new BorderLayout();

	private BorderLayout blNavCab = new BorderLayout();

	public JPanelPad pnMaster = new JPanelPad(JPanelPad.TP_JPANEL, blMaster);

	public JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL, blCab);

	private JPanelPad pnBordCab = new JPanelPad(JPanelPad.TP_JPANEL, glCab);

	public JPanelPad pnDet = new JPanelPad(JPanelPad.TP_JPANEL, new GridLayout(1, 1));

	private JPanelPad pnBordDet = new JPanelPad(JPanelPad.TP_JPANEL, glRod);

	public JPanelPad pnNavCab = new JPanelPad(JPanelPad.TP_JPANEL, blNavCab);

	protected JPanelPad pnBordNavCab = new JPanelPad(JPanelPad.TP_JPANEL, glNavCab);

	public JPanelPad pnCliCab = new JPanelPad(JPanelPad.TP_JPANEL, new GridLayout(1, 1));
	
	private JPanelPad pnBtInfo = new JPanelPad(JPanelPad.TP_JPANEL, new GridBagLayout());
	
	private JPanelPad pnBtInfoDet = new JPanelPad(JPanelPad.TP_JPANEL, new GridBagLayout());

	public Navegador navRod = new Navegador(true);

	public ListaCampos lcDet = new ListaCampos(this);

	public JTablePad tab = new JTablePad();

	public JScrollPane spTab = new JScrollPane(tab);

	boolean bSetAreaCab = true;

	boolean bSetAreaDet = true;
	
	private JButtonPad btInfoDet = new JButtonPad(Icone.novo("btInfo.png"));	
	
	private DLInfo dlinfoDet = null;
	
	private JTextFieldFK txtDtinsDet = new JTextFieldFK(JTextFieldPad.TP_DATE, 10, 0);

	private JTextFieldFK txtHinsDet = new JTextFieldFK(JTextFieldPad.TP_TIME, 8, 0);

	private JTextFieldFK txtUsuInsDet = new JTextFieldFK(JTextFieldPad.TP_STRING, 20, 0);

	private JTextFieldPad txtDtAltDet = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);

	private JTextFieldPad txtHAltDet = new JTextFieldPad(JTextFieldPad.TP_TIME, 8, 0);

	private JTextFieldPad txtUsuAltDet = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);


	public FDetalhe() {
		this(true);
	}

	public FDetalhe(boolean scroll) {

		super(scroll);

		pnRodape.remove(nav);
		pnRodape.add(navRod, BorderLayout.WEST);
		
		btInfoDet.setPreferredSize(new Dimension(26,26));
		pnBtInfoDet.add(btInfoDet);
		navRod.add(pnBtInfoDet);

		btInfoDet.setToolTipText("Informa��es sobre o registro");
		btInfoDet.addActionListener(this);
		
		pnCliente.remove(pinDados);
		pnCliente.add(pnMaster, BorderLayout.CENTER);

		// Reconstruiu o FDados
		// ********************************************
		// Agora ir� construir o FDetalhe
		nav.setName("Mestre");
		navRod.setName("Detalhe");

		pnNavCab.add(nav, BorderLayout.WEST);
		
		pnBtInfo.add(btInfo);
		
		btInfo.setPreferredSize(new Dimension(26,26));
		pnNavCab.add(pnBtInfo);
		
		pnBordNavCab.setPreferredSize(new Dimension(500, 30));
		pnBordNavCab.setBorder(br);
		pnBordNavCab.add(pnNavCab);

		pnCab.add(pnCliCab, BorderLayout.CENTER);
		pnCab.add(pnBordNavCab, BorderLayout.SOUTH);

		pnBordCab.setPreferredSize(new Dimension(500, 100));
		pnBordCab.setBorder(br);
		pnBordCab.add(pnCab);

		// pnBordDet.setBackground(Color.RED);

		pnBordDet.setPreferredSize(new Dimension(500, 50));
		pnBordDet.setBorder(br);
		pnBordDet.add(pnDet);

		pnMaster.add(pnBordCab, BorderLayout.NORTH);
		pnMaster.add(pnBordDet, BorderLayout.SOUTH);
		pnMaster.add(spTab, BorderLayout.CENTER);
		lcDet.setMaster(lcCampos);
		lcCampos.adicDetalhe(lcDet);
		lcDet.setTabela(tab);
		
		txtDtinsDet.setSoLeitura(true);
		txtHinsDet.setSoLeitura(true);
		txtUsuInsDet.setSoLeitura(true);
		txtDtAltDet.setSoLeitura(true);
		txtHAltDet.setSoLeitura(true);
		txtUsuAltDet.setSoLeitura(true);

	}

	public void montaTab() {

		lcDet.montaTab();
	}

	public void setAltDet(int Alt) {

		pnBordDet.setPreferredSize(new Dimension(500, Alt));
	}

	public void setAltCab(int Alt) {

		pnBordCab.setPreferredSize(new Dimension(500, Alt));
	}

	public void setConexao(DbConnection cn) {

		super.setConexao(cn);
		lcDet.setConexao(cn);
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btInfoDet) {
			getInfoDet();
		}
		super.actionPerformed(evt);
	}

	private void getInfoDet() {

		if (lcDet.getStatus() == ListaCampos.LCS_NONE) {
			Funcoes.mensagemInforma(this, "Selecione um registro!");
		}
		else {
			dlinfoDet = new DLInfo(this, getDtinsDet(), getDtAltDet(), getHinsDet(), getHAltDet(), getUsuInsDet(), getUsuAltDet());
			dlinfoDet.setVisible(true);
		}

	}
	
	private void adicCamposInfoDet() {
		try {
			setListaCampos(lcDet);
			adicCampoInvisivel(txtDtinsDet, "dtins", "Dt.ins.", ListaCampos.DB_SI, false);
			adicCampoInvisivel(txtHinsDet, "hins", "Hr.ins.", ListaCampos.DB_SI, false);
			adicCampoInvisivel(txtUsuInsDet, "idusuins", "id.ins.", ListaCampos.DB_SI, false);

			adicCampoInvisivel(txtDtAltDet, "dtalt", "Dt.alt.", ListaCampos.DB_SI, false);
			adicCampoInvisivel(txtHAltDet, "halt", "hr.alt.", ListaCampos.DB_SI, false);
			adicCampoInvisivel(txtUsuAltDet, "idusualt", "id.alt.", ListaCampos.DB_SI, false);

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public Date getDtinsDet() {
		return txtDtinsDet.getVlrDate();
	}

	public Date getHinsDet() {
		return txtHinsDet.getVlrTime();
	}

	public String getUsuInsDet() {
		return txtUsuInsDet.getVlrString();
	}

	public Date getDtAltDet() {
		return txtDtAltDet.getVlrDate();
	}

	public Date getHAltDet() {
		return txtHAltDet.getVlrTime();
	}

	public String getUsuAltDet() {
		return txtUsuAltDet.getVlrString();
	}
	
	public void setListaCampos(boolean bAuto, String sTab, String sSigla) {
		

		if (lcDet == lcSeq) {
			adicCamposInfoDet();
		}
		
		super.setListaCampos(bAuto, sTab, sSigla);
		
	}

	public JPanelPad getPnBtInfo() {
		return pnBtInfo;
	}

	public void setPnBtInfo(JPanelPad pnBtInfo) {
		this.pnBtInfo = pnBtInfo;
	}
	
}
