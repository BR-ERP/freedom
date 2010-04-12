/**
 * @version 12/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)DadosImagem.java <BR>
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
 * Coment�rios da classe.....
 */

package org.freedom.library.component;
import java.io.FileInputStream;

public class DadosImagem {
   private FileInputStream fisImagem;
   private int iTamanho = 0;
   private byte[] byImagem = null;
   
   public DadosImagem(FileInputStream fisIm, int iTam, byte[] byI) {
     byImagem = byI;
     fisImagem = fisIm;
     iTamanho = iTam;
   }
   
   public FileInputStream getInputStream() {
      return fisImagem;
   }
   
   public void setInputStream(FileInputStream fisIm)  {
     fisImagem = fisIm;
   }
   
   public int getTamanho() {
     return iTamanho;
   }
   
   public void setTamanho(int iTam) {
      iTamanho = iTam;
   }
   
   public byte[] getBytes() {
     return byImagem;
   }
   
   public void setBytes(byte[] byI) {
     byImagem = byI;
   }
        
}
