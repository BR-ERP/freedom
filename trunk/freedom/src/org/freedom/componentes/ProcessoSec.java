/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)ProcessoSec.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-P
 * C n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Coment�rios da classe.....
 */

package org.freedom.componentes;
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

