/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)ProcessoSec.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Caso uma c�pia da LPG-P
 * C n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Coment�rios da classe.....
 */

package org.freedom.library.business.componet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

import org.freedom.acao.Processo;
public class ProcessoSec implements Processo {
	private Thread th = null;
	private Timer tim = null;
	private Processo pPros = this;
	private Processo pTimer = this;
	public ProcessoSec(int iTempo,Processo pTim,Processo proc) {
		pPros = proc;
		pTimer = pTim;
		tim = new Timer(iTempo,
				new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				pTimer.run();
			}
		}
		);
		th = new Thread(proc);
	}
	public void iniciar() {
		if (th == null) 
			th = new Thread(pPros);
		th.start();
		tim.start();
	}
	public void parar() {
		th.interrupt();
		th = null;
		tim.stop();
	}
	public int getTempo() {
		return tim.getDelay();
	}
	public void run() { }
}

