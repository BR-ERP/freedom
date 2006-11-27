package org.freedom.util.resource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.log4j.Logger;


/**
 * Classe Pool de recursos gen�rico  <BR>
 * Serve para o armazenamento qualquer tipo de objeto.
 * Projeto: freedom-pool <BR>
 * Pacote: org.freedom.util.resource <BR>
 * Classe: @(#)AbstractResourcePool.java <BR>
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
 * criada: 24/07/2006. <BR>
 */
public abstract class AbstractResourcePool implements Runnable, HttpSessionBindingListener {

   /** N�mero inicial de conex�es. */
   public static final int INI_CON = 10;

   /** availableRes - Recursos dispon�veis. */
   private transient Map availableRes , inUseResources;

   /** maxResources - N�mero m�ximo de recursos que podem ser inst�nciados. */
   private transient int maxResources;

   /** waitIfMaxedOut - Indica se deve aguardar a libera��o de recursos. */
   private transient boolean waitIfMaxedOut;

   /** Guarda a �ltima exce��o ocorrida ( m�todo @see run() ). */
   private transient ResourceException error = null;

   /** Indica se o comportamento � realmente de um pool de recursos. */
   private transient boolean ispool; 

   /** Log4j da classe. */
   private static final Logger LOGGER = createLogger();

   // Extens�es t�m que implementar estes tr�s m�todos
   /**
    * Criador de recursos.
    * @throws ResourceException Exce��o na cria��o de recurso.
    * @return Retorna um recurso.
    */
   public abstract ResourceKey createResource() throws ResourceException;

   /**
    * Verifica se um recurso � v�lido.
    * @param resource Recebe um recurso para avalia��o.
    * @return Indica se o recurso � v�lido.
    */
   public abstract boolean isResourceValid(ResourceKey resource);

   /**
    * Fecha um determinado recurso.
    * @param resource Recurso que devera ser fechado.
    */
   public abstract void closeResource(ResourceKey resource);

   /**
    * Construtor da classe sem par�netros.
    */
   public AbstractResourcePool() {
      this(INI_CON, // por padr�o, um m�ximo de 10 recursos no pool
            false); // n�o espera pelo recurso se maximizado
   }

   /**
    * Seta o atributo ispool.
    * @param isp Recebe um valor l�gico para ispool.
    */
   public final void setIspool(boolean isp) {
      this.ispool = isp;
   }

   /**
    * Retorna flag ispool.
    * @return Retorna um valor l�gico indicando o comportamento do pool.
    */
   public final boolean getIspool() {
      return this.ispool;
   }

   /**
    * Construtor da classe com par�metros iniciais.
    * @param max N�mero m�ximo de recursos inicializados.
    * @param waitIfMaxOut Flag que indica se dever� esperar
    * um conex�o ser liberada.
    */
   public AbstractResourcePool(final int max, final boolean waitIfMaxOut) {
      availableRes = new HashMap();
      inUseResources = new HashMap();
      this.maxResources = max;
      this.waitIfMaxedOut = waitIfMaxOut;
   }

   /**
    * Retorna um vetor com os recursos dispon�veis.
    * @return HashMap com os recursos dispon�veis.
    */
   protected final Map getAvailableRes() {
      return this.availableRes;
   }

   /**
    * Valida e retorna os recursos a partir dos par�metros.
    * @param sessionID Sess�o chave.
    * @param key Chave secund�ria.
    * @param password Senha.
    * @return Retorna um recurso.
    * @throws ResourceException Caso aconte�a uma exce��o aborta
    * com um ResourceException.
    */
   public final ResourceKey getResource(final String sessionID,
         final String key, final String password) throws ResourceException {
      return getResource(sessionID, key, password, 0);
   }

   /**
    * Retorna um recurso a partir dos par�metros chave.
    * @param sessionID Sess�o chave.
    * @param key Chave secund�ria.
    * @param password Senha.
    * @param timeout Tempo de espera pelo recurso.
    * @return Retorna um recurso.
    * @throws ResourceException Caso aconte�a uma exce��o aborta
    * com um ResourceException.
    */
   public final synchronized ResourceKey getResource(
      final String sessionID, final String key, final String password,
      final long timeout) throws ResourceException {
      ResourceKey resource = getFirstAvailableResource(sessionID + key);
      if (resource == null) { // Sem recursos dispon�veis
         if ((countResources() < maxResources) || (maxResources == 0)) {
            waitForAvailableResource();
            resource = getFirstResource(sessionID, key, password);
         } else if (waitIfMaxedOut) { // limite m�ximo de recursos atingido
            try {
               wait(timeout);
            } catch (InterruptedException ex) {
               LOGGER.error(ex);
            }
            resource = getFirstResource(sessionID, key, password);
         } else {
            throw new ResourceException(
               "N�mero m�ximo de recursos atingidos. Tente mais tarde.");
         }
      }
      if (resource != null) {
         resource.setSessionID(sessionID);
         inUseResources.put(resource.getHashKey(), resource);
      }
      return resource;
   }

   /** Cria a inst�ncia do Log4j da classe.
    ** @return Retorna a inst�ncia de log para a classe.
    */
   private static Logger createLogger() {
      return Logger.getLogger("org.freedom.util.resource.AbstractResourcePool");
   }

   /**
    * Recicla o recurso para reutiliza��o.
    * @param resource Recebe o recurso para a reciclagem.
    */
   public final synchronized void recycleResource(final ResourceKey resource) {
      inUseResources.remove(resource.getHashKey());
      availableRes.put(resource.getHashKey(), resource);
      notifyAll(); // notifica threads em espera de con dispon�veis
   }

   /**
    * Desliga o Pool fechando todos os recursos.
    */
   public final void shutdown() {
      closeResources(availableRes);
      closeResources(inUseResources);
      availableRes.clear();
      inUseResources.clear();
   }

   /**
    * Inicia o Thread do Pool de recursos.
    */
   public final synchronized void run() {
      ResourceKey resource;
      ResourceException errorrun = null;
      try {
         resource = createResource(); // cria��o de subclasses
         if (resource != null) {
            availableRes.put(resource.getHashKey(), resource);
         }
      } catch (ResourceException ex) {
         errorrun = ex; // armazena a exce��o
      }
      error = errorrun;
      notifyAll(); // notifica threas em espera
   }

   /**
    * Retorna o primeiro recurso dispon�vel.
    * @param key Chave para buscar o recursos.
    * @return Retorna o recurso dispon�vel.
    */
   private ResourceKey getFirstAvailableResource(final String key) {
      ResourceKey resource = null;

      if (availableRes.size() > 0) {
         resource = getFirstResource(key);
      }
      if ((resource != null) && (!isResourceValid(resource))) {
         resource = getFirstAvailableResource(key); // tenta novamente
      }
      return resource;
   }

   /**
    * Aguarda a libera��o de um recurso.
    * @throws ResourceException Caso aconte�a uma exce��o aborta
    * o m�todo atrav�s de um ResourceException.
    */
   private void waitForAvailableResource() throws ResourceException {
      final Thread thread = new Thread(this);
      thread.start(); // thread cria um recurso: veja run()
      try {
         wait(); // espera que un novo recurso seja criado
         // ou que um recurso seja reciclado
      } catch (InterruptedException ex) {
         LOGGER.error(ex);
      }
      if (error != null) { // exce��o pega em run()
         throw error;
      } // reemite exce��o pega em run()
   }

   /**
    * Fecha um lista de recursos.
    * @param resources Recebe a lista de recursos a fechar.
    */
   private void closeResources(final Map resources) {
      resources.clear();
   }

   /**
    * Consulta o n�mero de recursos totais.
    * @return Retorna o n�mero de recursos.
    */
   private int countResources() {
      return availableRes.size() + inUseResources.size();
   }

   /**
    * Encontra e retorna o primeiro recurso encontrado com a chave.
    * @param key Chave para a pesquisa do recurso.
    * @return Retorna o recurso encontrado.
    */
   private ResourceKey getFirstResource(final String key) {
      String keypesq = null;
      if (key == null) {
         keypesq = "";
      } else {
         keypesq = key;
      }
      final ResourceKey resource = (ResourceKey)
         availableRes.get(keypesq);
      if (resource != null) {
         availableRes.remove(key);
      }
      return resource;
   }

   /**
    * Retorna o primerio recurso econtrado atrav�s de uma chave composta.
    * @param sessionID ID da Sess�o chave.
    * @param key Chave secund�ria.
    * @param password Senha para valida��o.
    * @return Retorna o recurso encontrado.
    */
   private ResourceKey getFirstResource(final String sessionID,
         final String key, final String password) {
      final String keypesq = (key == null ? "" : key);
      final ResourceKey resource = (ResourceKey)
         availableRes.get(sessionID + keypesq);
      if (resource != null && resource.getPassword().equals(password)) {
            availableRes.remove(resource.getHashKey());
      }
      return resource;
   }

   /**
    * Limpa o recurso do cache.
    * @param resource ResourceKey Recurso a ser removido.
    */
   public final synchronized void clearResource(final ResourceKey resource) {
      if (availableRes != null) {
         availableRes.remove(resource.getHashKey());
      }
      if (inUseResources != null) {
         inUseResources.remove(resource.getHashKey());
      }
   }

   /**
    * Seta o n�mero m�ximo de recursos.
    * @param maxRes Recebe o n�mero m�ximo de recursos.
    */
   public final void setMaxResources(final int maxRes) {
      this.maxResources = maxRes;
   }

   /**
    * Retorna um recurso a partir de uma sess�o chave.
    * @param sessionID ID da Sess�o chave para a pesquisa.
    * @return Retorna o recurso encontrado.
    */
   public final ResourceKey getResourceSession(final String sessionID) {
      final ResourceKey resource = (ResourceKey)
         inUseResources.get(sessionID);
      return resource;
   }

   /**
    * M�todo executado quando uma sess�o http for iniciada.
    * @param event HttpSessionBindingEvent.
    */
   public final void valueBound(final HttpSessionBindingEvent event) {
   }

   /**
    * M�todo ser� executado quando uma sess�o http expirar.
    * @param event HttpSessionBindingEvent.
    */
   public final void valueUnbound(final HttpSessionBindingEvent event) {
      final String sessionID = event.getSession().getId();
      final ResourceKey resource = getResourceSession(sessionID);
      if (getIspool()) {
         recycleResource(resource);
      } else {
         closeResource(resource);
      }
   }
}
