/**
 * @version 12/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)DadosImagem.java <BR>
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
 * Coment�rios da classe.....
 */

package org.freedom.componentes;
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
