/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLDataTransf.java <BR>
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
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Date;


import org.freedom.funcoes.Funcoes;
import org.freedom.library.JLabelPad;
import org.freedom.library.JTextFieldPad;
import org.freedom.telas.FFDialogo;

public class DLDataTransf extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataTransf = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	public DLDataTransf(Component cOrig) {
		super(cOrig);
		setTitulo("Nova Data");
		setAtribos(100,100,250,120);

		adic(new JLabelPad("Nova Data:"),7,0,110,20);
		adic(txtDataTransf,7,20,100,20);
	}
	public Date getValor() {
		return txtDataTransf.getVlrDate();
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			if (txtDataTransf.getText().length() < 10) {
				Funcoes.mensagemInforma(this,"Valor invalido para data!");
				txtDataTransf.requestFocus();
				return;
			}
		}
		super.actionPerformed(evt);
	}

}
