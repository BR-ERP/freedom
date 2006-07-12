package org.freedom.util.resource;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Pool de recursos gen�rico.
 * Serve para o armazenamento qualquer tipo de objeto.
 * @author: Robson Sanchez/Setpoint Inform�tica Ltda.
 */
public abstract class AbstractResourcePool implements Runnable {

   /** N�mero inicial de conex�es. */
   public static final int INI_CON = 10;

   /** availableRes - Recursos dispon�veis. */
   private transient Map availableRes , inUseResources;

   /** maxResources - N�mero m�ximo de recursos que podem ser inst�nciados. */
   private transient int maxResources;

   /** waitIfMaxedOut - Indica se deve aguardar a libera��o de recursos. */
   private transient boolean waitIfMaxedOut;

   /** error - Guarda a �ltima exce��o ocorrida ( m�todo @see run() ). */
   private transient ResourceException error = null;

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
         if (countResources() < maxResources) {
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
    * Retorna um recurso a partir de uma sess�o chave.
    * @param sessionID ID da Sess�o chave para a pesquisa.
    * @return Retorna o recurso encontrado.
    */
   public final ResourceKey getResourceSession(final String sessionID) {
      final ResourceKey resource = (ResourceKey)
         inUseResources.get(sessionID);
      return resource;
   }
}
