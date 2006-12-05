package org.freedom.util.resource;


/**
 * Classe de tratamento de erros do gerenciador de pool. <BR>
 * Projeto: freedom-pool <BR>
 * Pacote: org.freedom.util.resource <BR>
 * Classe: @(#)ResourceException.java <BR>
 * <BR>
 * Este programa � licenciado de acordo com a LGPL (Lesser General Public
 * License), <BR>
 * vers�o 2.1, Fevereiro de 1999 <BR>
 * A LGPL deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LGPL n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <a
 * href=http://creativecommons.org/licenses/LGPL/2.1/legalcode.pt> Creative
 * Commons</a> <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar de acordo com os termos da LGPL. <BR>
 * <BR>
 * @author Robson Sanchez/Setpoint Inform�tica Ltda. <BR>
 * criada: 05/10/2004. <BR>
 */
public class ResourceException extends Exception {
   /** vari�vel de versionamento da classe para serializa��o. 
    */
   private static final long serialVersionUID = 1L;

   /**
    * M�todo construtor da classe.
    * @param error recebe string de descri��o do erro.
    * @see java.lang#Exception
    */
   public ResourceException(final String error) {
      super(error);
   }
}
