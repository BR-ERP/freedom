/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLCCAnal.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLCCAnal extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodPai = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtDescPai = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtCodAnal = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);
	private JTextFieldPad txtDescAnal = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtSiglaAnal = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
	private JLabelPad lbCodPai = new JLabelPad("C�digo");
	private JLabelPad lbDescPai = new JLabelPad("e descri��o da origem");
	private JLabelPad lbCodAnal = new JLabelPad("C�digo");
	private JLabelPad lbDescAnal = new JLabelPad("Descri��o");
	private JLabelPad lbSiglaAnal = new JLabelPad("Sig.");
	public DLCCAnal(
	    Component cOrig,
		String sCodPai,
		String sDescPai,
		String sCod,
		String sDesc,
		String sSigla) {
		super(cOrig);
		setTitulo("Nova Conta Anal�tica");
		setAtribos(450, 170);
		cancText(txtCodPai);
		cancText(txtDescPai);
		cancText(txtCodAnal);
		Funcoes.setBordReq(txtDescAnal);
		txtCodPai.setText(sCodPai);
		txtDescPai.setText(sDescPai);
		txtCodAnal.setText(sCod);
		adic(lbCodPai, 7, 0, 80, 20);
		adic(txtCodPai, 7, 20, 80, 20);
		adic(lbDescPai, 90, 0, 200, 20);
		adic(txtDescPai, 90, 20, 240, 20);
		adic(lbCodAnal, 7, 40, 100, 20);
		adic(txtCodAnal, 7, 60, 110, 20);
		adic(lbDescAnal, 120, 40, 110, 20);
		adic(txtDescAnal, 120, 60, 207, 20);
		adic(lbSiglaAnal, 330, 40, 100, 20);
		adic(txtSiglaAnal, 330, 60, 80, 20);
		if (sDesc != null) {
			setTitulo("Edi��o de Conta Anal�tica");
			txtDescAnal.setText(sDesc);
			txtSiglaAnal.setText(sSigla);
			txtDescAnal.selectAll();
		}
		txtDescAnal.requestFocus();
	}
	private void cancText(JTextFieldPad txt) {
		txt.setBackground(Color.lightGray);
		txt.setFont(new Font("Dialog", Font.BOLD, 12));
		txt.setEditable(false);
		txt.setForeground(new Color(118, 89, 170));
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			if (txtDescAnal.getText().trim().length() == 0) {
				Funcoes.mensagemInforma(
					this,
					"O campo descri��o est� em branco! ! !");
				txtDescAnal.requestFocus();
				return;
			} else if (txtSiglaAnal.getText().trim().length() == 0) {
				Funcoes.mensagemInforma(
					this,
					"O campo sigla est� em branco! ! !");
				txtSiglaAnal.requestFocus();
				return;
			}
		}
		super.actionPerformed(evt);
	}
	public String[] getValores() {
		String[] sRet = { txtDescAnal.getVlrString(), txtSiglaAnal.getVlrString()};
		return sRet;
	}
}
