/*
 * Projeto: Freedom-nfe
 * Pacote: org.freedom.modules.nfe.control
 * Classe: @(#)AbstractNFEFactory.java
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 */

package org.freedom.modules.nfe.control;

import java.util.ArrayList;
import java.util.List;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.infra.pojos.Constant;
import org.freedom.modules.nfe.bean.AbstractNFEKey;
import org.freedom.modules.nfe.bean.NFEInconsistency;
import org.freedom.modules.nfe.bean.ReturnMessageKey;
import org.freedom.modules.nfe.event.NFEEvent;
import org.freedom.modules.nfe.event.NFEListener;

/**
 * Classe padr�o para implementa��o de NF-e.
 * 
 * @author Setpoint Inform�tica Ltda./Robson Sanchez
 * @version 15/07/2009 Robson Sanchez
 * @version 10/03/2010 Anderson Sanchez
 */
public abstract class AbstractNFEFactory {

	private boolean valid = true;

	private boolean service = false;
	
	private DbConnection conSys = null;

	private DbConnection conNFE = null;

	private AbstractNFEKey key = null;

	private List<NFEInconsistency> listInconsistency;
	
	public ReturnMessageKey returnMessage;

	private final List<NFEListener> listEvent = new ArrayList<NFEListener>();

	private Constant tpNF = AbstractNFEFactory.TP_NF_OUT;
	
	public static final Constant TP_NF_IN = new Constant("Entrada", new Integer(0));

	public static final Constant TP_NF_OUT = new Constant("Saida", new Integer(1));

	public static final Constant TP_NF_BOTH = new Constant("Ambos", new Integer(3));
	
	public static final Constant VERSAO_LAYOUT_NFE_01 = new Constant("NFE 1.0", "1.10" );
	
	public static final Constant VERSAO_LAYOUT_NFE_02 = new Constant("NFE 2.0", "2.00" );

	public static final Constant REGIME_TRIB_NFE_SIMPLES = new Constant("Simples Nacional", "1" );
	
	public static final Constant REGIME_TRIB_NFE_SIMPLES_EX = new Constant("Simples Nacional - Excesso de sublimite de receita bruta", "2"  );
	
	public static final Constant REGIME_TRIB_NFE_NORMAL = new Constant("Regime Normal", "3" );
	
	// Constantes par aidentifica��o das situa��es do documento fiscal eletr�nico
	public static final Constant SIT_DOC_REGULAR = new Constant("Documento regular", "00"); 
	
	public static final Constant SIT_DOC_REGULAR_EXP = new Constant("Documento regular expont�neo", "01"); 
	
	public static final Constant SIT_DOC_CANCELADO = new Constant("Documento cancelado", "02"); 
	
	public static final Constant SIT_DOC_CANCELADO_EXP = new Constant("Documento cancelado expont�neo", "03"); 
	
	public static final Constant SIT_DOC_NFE_DENEGADA = new Constant("NFE Denegada", "04"); 
	
	public static final Constant SIT_DOC_NFE_NRO_INUTILIZ = new Constant("NFE Numera��o inutilizada", "05"); 
	
	public static final Constant SIT_DOC_COMPLEMENTAR = new Constant("Documento fiscal complementar", "06"); 
	
	public static final Constant SIT_DOC_COMPLEMENTAR_EXP = new Constant("Documento fiscal complementar expont�neo", "07"); 
	
	public static final Constant SIT_DOC_REGIME_ESPECIAL = new Constant("Documento emitido com base em Regime Especial ou Norma Espec�fica", "08"); 
	
	public static String KIND_APP_OWN = "0";
	
	public static String KIND_APP_FISCO = "3";
	
	public static String KIND_ENV_PROD = "1";
	
	public static String KIND_ENV_HOMOLOG = "2";
	
	private String kindTransmission = KIND_APP_FISCO;

	private String KindEnv = KIND_ENV_HOMOLOG;
	
	private boolean autorizada = false;
	
	private String chaveNfe = null;

	private String tempDir;
	
	private String siglaUfEmitente;
	
	public enum TYPE_PROC {NFE, CANCELAMENTO};
	/*ASSINADA;1
AUTORIZADA;10772
CANCELADA;130
CANCELADA_EVENTO;93
DENEGADA;9
EM_DIGITACAO;2
REJEITADA;2*/
	
	public enum SITUACAO_NFE_DB {EM_DIGITACAO, ASSINADA, AUTORIZADA, CANCELADA_EVENTO, DENEGADA, REJEITADA, CANCELADA}; 
	
	private TYPE_PROC type_proc = null;
	
	private String motivoCancNfe = null;
	
	private String cnpjFilial = null;
	
	public enum SYSTEM {
		FREEDOM
	};

	public AbstractNFEFactory() {
		
	}

	public boolean isValid() {
		return valid;
	}

	public void setService(boolean service) {
		this.service = service;
	}

	public boolean isService() {
		return service;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public DbConnection getConSys() {
		return conSys;
	}

	public void setConSys(DbConnection conSys) {
		this.conSys = conSys;
	}

	public DbConnection getConNFE() {
		return conNFE;
	}

	public void setConNFE(DbConnection conNFE) {
		this.conNFE = conNFE;
	}

	public void setKey(AbstractNFEKey key) {
		this.key = key;
	}

	public AbstractNFEKey getKey() {
		return key;
	}

	public void addInconsistency(String description, String correctiveAction) {
		this.addInconsistency(new NFEInconsistency(NFEInconsistency.TypeInconsistency.ERROR, description, correctiveAction));
	}

	public void addInconsistency(NFEInconsistency inconsistency) {
		if (inconsistency != null) {
			listInconsistency.add(inconsistency);
		}
	}

	public List<NFEInconsistency> getListInconsistency() {

		if (this.listInconsistency == null) {
			this.listInconsistency = new ArrayList<NFEInconsistency>();
		}

		return listInconsistency;
	}

	public ReturnMessageKey getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(ReturnMessageKey returnMessage) {
		this.returnMessage = returnMessage;
	}

	public void setTpNF(Constant tpNF) {
		this.tpNF = tpNF;
	}

	public Constant getTpNF() {
		return tpNF;
	}

	public void setListInconsistency(List<NFEInconsistency> listInconsistency) {
		this.listInconsistency = listInconsistency;
	}

	public synchronized void addNFEListener(NFEListener event) {
		this.listEvent.add(event);
	}

	public void removeNFEListener(NFEListener event) {
		this.listEvent.remove(event);
	}

	protected abstract void validSend();

	protected abstract void runSend();
	
	public void post() {

		fireBeforeValidSend();

		validSend();

		fireAfterValidSend();

		/*
		 * Verifica se a nota fiscal � v�lida.
		 * Notas Fiscal de servi�o n�o s�o enviadas pelo runSend.
		 * :TODO REAVALIAR RUNSEND
		 */
		if (isValid() && !isService()) {

			fireBeforeRunSend();

			runSend();

			fireAfterRunSend();
		}
	}

	private void fireBeforeValidSend() {

		NFEEvent event = new NFEEvent(this);

		for (NFEListener obj : listEvent) {
			obj.beforeValidSend(event);
		}
	}

	private void fireAfterValidSend() {

		NFEEvent event = new NFEEvent(this);

		for (NFEListener obj : listEvent) {
			obj.afterValidSend(event);
		}
	}

	private void fireBeforeRunSend() {

		NFEEvent event = new NFEEvent(this);

		for (NFEListener obj : listEvent) {
			obj.beforeValidSend(event);
		}
	}

	private void fireAfterRunSend() {

		NFEEvent event = new NFEEvent(this);

		for (NFEListener obj : listEvent) {
			obj.afterRunSend(event);
		}
	}
	
	public abstract boolean consistChaveNFE(String chavenfe) throws Exception;

	public String getKindTransmission() {
		return kindTransmission;
	}

	public void setKindTransmission(String kindTransmission) {
		this.kindTransmission = kindTransmission;
	}

	public String getKindEnv() {
		return KindEnv;
	}

	public void setKindEnv(String kindEnv) {
		KindEnv = kindEnv;
	}

	public boolean isAutorizada() {
		return autorizada;
	}

	public void setAutorizada(boolean autorizada) {
		this.autorizada = autorizada;
	}

	public String getChaveNfe() {
		return chaveNfe;
	}

	public void setChaveNfe(String chaveNfe) {
		this.chaveNfe = chaveNfe;
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public TYPE_PROC getType_proc() {
		return type_proc;
	}

	public void setType_proc(TYPE_PROC type_proc) {
		this.type_proc = type_proc;
	}

	public String getMotivoCancNfe() {
		return motivoCancNfe;
	}

	public void setMotivoCancNfe(String motivoCancNfe) {
		this.motivoCancNfe = motivoCancNfe;
	}

	public String getCnpjFilial() {
		return cnpjFilial;
	}

	public void setCnpjFilial(String cnpjFilial) {
		this.cnpjFilial = cnpjFilial;
	}

	public String getSiglaUfEmitente() {
		return siglaUfEmitente;
	}

	public void setSiglaUfEmitente(String siglaUfEmitente) {
		this.siglaUfEmitente = siglaUfEmitente;
	}

}
