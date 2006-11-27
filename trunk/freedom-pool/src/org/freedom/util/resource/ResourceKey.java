package org.freedom.util.resource;

import org.apache.log4j.Logger;



/**
 * Classe utilizada para encapsular objetos que ser�o utilizados no pool. <BR>
 * Projeto: freedom-pool <BR>
 * Pacote: org.freedom.util.resource <BR>
 * Classe: @(#)ResourceKey.java <BR>
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
public class ResourceKey {
   /** Recurso. */
   private Object resource = null;

   /** Chave do recurso. */
   private String key = null;

   /** ID da sess�o chave. */
   private String sessionID = null;

   /** Senha do recurso. */
   private String password = null;

   /** Log4j da classe. */
   private static final Logger LOGGER = createLogger();

   /**
    * Construtor do Recurso.
    * @param sessID ID da Sess�o chave.
    * @param keyp Chave secund�rio.
    * @param pass Senha para valida��o.
    * @param res Objeto que ser� armazenado.
    */
   public ResourceKey(final String sessID, final String keyp,
         final String pass, final Object res) {
      setResource(res);
      setKey(keyp);
      setSessionID(sessID);
      setPassword(pass);
   }

   /**
    * Cria a inst�ncia do Log4j para a classe.
    * @return Retorna a inst�ncia do Log.
    */
   private static Logger createLogger() {
     return Logger.getLogger("org.freedom.util.resource.ResourceKey");
   }

   /**
    * Retorna o recurso encapsulado.
    * @return Retorna a inst�ncia do recurso encapsulado.
    */
   public final Object getResource() {
      return resource;
   }

   /**
    * Encapsula o objeto principal.
    * @param res Inst�ncia para encapsulamento.
    */
   public final void setResource(final Object res) {
      this.resource = res;
   }

   /**
    * Verifica se � um recurso v�lido.
    * @param sessID ID da sess�o chave.
    * @param keyp Chave secund�ria.
    * @return Retorna um flag indicando se o recurso � valido.
    */
   public final boolean isResource(final String sessID, final String keyp) {
      boolean isresource = false;
      try {
         if (sessionID == null) {
            isresource = isResource(key);
         } else {
            isresource = ((this.key == key) && (this.sessionID == sessionID));
         }
      } catch (Exception e) {
         LOGGER.error(e);
      }
      return isresource;
   }


   /**
    * Verifica se � um recurso v�lido.
    * @param keyp Chave secund�ria.
    * @return Retorna um flag indicando se o recurso � valido.
    */
   public final boolean isResource(final String keyp) {
      boolean isresource = false;
      try {
         isresource = (this.key == keyp);
      } catch (Exception e) {
         LOGGER.error(e);
      }
      return isresource;
   }

   /**
    * Chave para a lista de recursos.
    * @return Retorna a chave.
    */
   public final String getHashKey() {
      return sessionID + key;
   }

   /**
    * Retorna a chave encapsulada.
    * @return Retorna a chave.
    */
   public final String getKey() {
      return this.key;
   }

   /**
    * Encapsula a chave do recurso.
    * @param keyp Recebe a chave para encapsulamento.
    */
   public final void setKey(final String keyp) {
      this.key = keyp;
   }

   /**
    * Retorna o ID da sess�o encapsulada.
    * @return Retorna o ID da sess�o.
    */
   public final String getSessionID() {
      return this.sessionID;
   }

   /**
    * Encapsula o ID da sess�o.
    * @param sessID Recebe o ID da sess�o para encapsulamento.
    */
   public final void setSessionID(final String sessID) {
      this.sessionID = sessID;
   }

   /**
    * Encapsula a senha.
    * @param pass Recebe a senha para encapsulamento.
    */
   public final void setPassword(final String pass) {
      this.password = pass;
   }

   /**
    * Retorna a senha encapsulada.
    * @return Retorna a senha.
    */
   public final String getPassword() {
      return this.password;
   }
}
