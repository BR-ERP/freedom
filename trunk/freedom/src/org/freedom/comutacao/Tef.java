/**
 * @version 19/03/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.comutacao <BR>
 * Classe: @(#)TesteBema.java <BR>
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
 * Classe de interface com TEF.
 * 
 */

package org.freedom.comutacao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.Vector;

import org.freedom.funcoes.Funcoes;

public class Tef {
	boolean bAtivo = false;
	File fTmp;
	File fEnvio;
	File fStatus;
	File fRetorno;
	File fAtivo;
	static final String ARQ_TMP = "IntPos.tmp";
	static final String ARQ_ENVIO = "IntPos.001";
	static final String ARQ_STATUS = "intpos.sts";
	static final String ARQ_RETORNO = "intpos.001";
	static final String ARQ_ATIVO = "ativo.001";

	static final String TEF_HEADER = "000-000";
	static final String TEF_IDENTIFICACAO = "001-000";
	static final String TEF_DOC_FISCAL = "002-000";
	static final String TEF_VAL_TOTAL = "003-000";
	static final String TEF_ST_TRANSACAO = "009-000";
	static final String TEF_NOME_REDE = "010-000";
	static final String TEF_TIPO_TRANSACAO = "011-000";
	static final String TEF_NSU = "012-000";
	static final String TEF_AUTORIZACAO = "013-000";
	static final String TEF_LOTE_TRANSACAO = "014-000";
	static final String TEF_TIM_SERVER = "015-000";
	static final String TEF_TIM_LOCAL = "016-000";
	static final String TEF_DT_COMPROVANTE = "022-000";
	static final String TEF_HR_COMPROVANTE = "023-000";
	static final String TEF_FINALIZACAO = "027-000";
	static final String TEF_QTD_LINHAS = "028-000";
	static final String IMP_BASE = "029-"; //Base para impressao do comprovante (somar esta mais 3 digitos): 029-001,029-002...
	static final String TEF_MSG_OPERADOR = "030-000";
	static final String TEF_ADMINISTRADORA = "040-000";
	static final String TEF_EOT = "999-999"; //"E"nd "O"f "T"rasaction. (final do arquivo)
	
	private long lIdentUniq = 1;
	
	public Tef(String sPathEnv, String sPathRet) {
		fTmp = new File(sPathEnv+"/"+ARQ_TMP);
		fEnvio = new File(sPathEnv+"/"+ARQ_ENVIO);
		fStatus = new File(sPathRet+"/"+ARQ_STATUS);
		fRetorno = new File(sPathRet+"/"+ARQ_RETORNO);
		fAtivo = new File(sPathRet+"/"+ARQ_ATIVO);

		if (!verifTef()) {
			Funcoes.mensagemInforma(null,"Gerenciador Padr�o de TEF n�o est� ativo!");
		}
			
	}
	public boolean enviaArquivo(String sConteudo[]) {
		boolean bRet = false;
		
		if (fTmp.exists())
			fTmp.delete();
		try {
			PrintStream psEnvio = new PrintStream(new FileOutputStream(fTmp));
			for (int i=0;i<sConteudo.length;i++)
				psEnvio.println(sConteudo[i]);
			psEnvio.println(TEF_EOT+" = 0");
			psEnvio.close();
			fTmp.renameTo(fEnvio);
			bRet = true;
		}
		catch(IOException err) {
			Funcoes.mensagemErro(null,"N�o foi poss�vel gravar o arquivo tempor�rio de TEF!");
			err.printStackTrace();
		}
		return bRet;
	}
	public boolean verifTef() {
		boolean bRet = false;
		
		if (!fAtivo.exists() || !fAtivo.canRead())
			return false;

		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(fAtivo));
			if (prop.getProperty(TEF_HEADER,"").equals("TEF"))
				bRet = true;
			prop.clear();
		}
		catch(IOException err) {
			Funcoes.mensagemErro(null,"N�o foi poss�vel verificar se o m�dulo TEF est� ativo!");
			err.printStackTrace();
		}
		return bRet;
	}
	public boolean existeStatus(String sCab, long lIdent) {
		return existeInfo(sCab,fStatus,7,(lIdent > 0 ? lIdent : 0));//7 � padrao do GP.
	}
	public boolean existeRetorno(String sCab, long lIdent) {
		return existeInfo(sCab,fRetorno,15,(lIdent > 0 ? lIdent : 0));
	}
	public boolean existeInfo(String sCab,File fArq, int iTentativas,long lIdent) {
		String sLinha;
		boolean bRet = false;
		int iConta = 0;
		while(true) {
			try {
				Thread.sleep(1000);
			}
			catch(Exception err) {
				break;
			}
			if (!fArq.exists() || !fArq.canRead())
				continue;
			try {
				Properties prop = new Properties();
				FileInputStream fis = new FileInputStream(fArq);
				prop.load(fis);
				fis.close();
				//Se for necess�rio identifica��o o lIdent ser� maior a 0;
				if (prop.getProperty(TEF_HEADER,"").equals(sCab) && lIdent > 0 &&
					prop.getProperty(TEF_IDENTIFICACAO,"").equals(""+lIdent))
					bRet = true;
				//Se n�o for necess�rio identifica��o o lIdent ser� igual a 0;
				else if (prop.getProperty(TEF_HEADER,"").equals(sCab) && lIdent == 0)
					bRet = true;
				prop.clear();
			}
			catch(IOException err) {
				Funcoes.mensagemErro(null,"N�o foi poss�vel ler o retorno da TEF!");
				err.printStackTrace();
			}
			if (!bRet && iConta < iTentativas)
				iConta++;
			else
				break;
		}
		return bRet;
	}
	private Properties leRetorno() {
		Properties pRet = null;
		try {
			FileInputStream fis = new FileInputStream(fRetorno);
			pRet = new Properties();
			pRet.load(fis);
			fis.close();
			fRetorno.delete();
			fStatus.delete();
		}
		catch(Exception err) {
			Funcoes.mensagemErro(null,"N�o foi poss�vel carregar o retorno da TEF!");
			err.printStackTrace();
		}
		return pRet;
	}
	public Properties solicVenda(int iNumCupom, BigDecimal bigVal) {
		String pRet = null;
		boolean bRet;
		int iConta;
		if (!verifTef())
			return null;
//Pega uma identifica��o e j� deixa outra dispon�vel;		
		long lIdent = this.lIdentUniq++;
		
		bRet = enviaArquivo(new String[] {
						(TEF_HEADER + " = "+ "CRT"),
						(TEF_IDENTIFICACAO + " = "+ lIdent),
						(TEF_DOC_FISCAL + " = "+ iNumCupom),
						(TEF_VAL_TOTAL + " = "+ (Funcoes.transValor(bigVal,12,2,false)).trim())
					}
				);
		if (!bRet || 
		    !existeStatus("CRT",lIdent) || 
			!existeRetorno("CRT",lIdent))
			return null;
		
		return leRetorno();
	}
	public boolean finalizaVenda(Properties prop) {
		String pRet = null;
		boolean bRet;
		int iConta;
		if (!verifTef())
			return false;
		bRet = enviaArquivo(new String[] {
						(TEF_HEADER + " = "+ "CNF"),
						(TEF_IDENTIFICACAO + " = "+ prop.getProperty(TEF_DOC_FISCAL)),
						(TEF_DOC_FISCAL + " = "+ prop.getProperty(TEF_DOC_FISCAL)),
						(TEF_NOME_REDE + " = "+ prop.getProperty(TEF_NOME_REDE)),
						(TEF_NSU + " = "+ prop.getProperty(TEF_NSU)),
						(TEF_FINALIZACAO + " = "+ prop.getProperty(TEF_FINALIZACAO)),
					}
				);
		if (!bRet || !existeStatus("CNF", Long.parseLong(prop.getProperty(TEF_DOC_FISCAL)))) 
			return false;
		
		return bRet;
	}
	public Properties solicAdm(String sTipoTrans) {
		String pRet = null;
		boolean bRet;
		int iConta;
		if (!verifTef())
			return null;
		bRet = enviaArquivo(new String[] {
						(TEF_HEADER + " = "+ "ADM"),
						(TEF_TIPO_TRANSACAO + " = "+ sTipoTrans)
					}
				);
		if (!bRet || 
		    !existeStatus("ADM",0) || 
			!existeRetorno("ADM",0))
			return null;
		
		return leRetorno();
	}
	public boolean validaTef(Properties prop) {
		boolean bRet = false; 
		if (!prop.getProperty(TEF_ST_TRANSACAO,"").equals("0")) {
			Funcoes.mensagemErro(null,prop.getProperty(TEF_MSG_OPERADOR));
			bRet = false;
		}
		else
			bRet = true;
		return bRet;
	}
	public Object[] retImpTef(Properties prop) {
		Vector vRet = new Vector();
		String sLinha = null;
		for (int i=1;i<999;i++) {
		    if ((sLinha = prop.getProperty(IMP_BASE+Funcoes.strZero(""+i,3))) != null)
		        vRet.addElement(sLinha.replaceAll("\"",""));
		    else
		        break;
		}
		return vRet.toArray();
	}

}
