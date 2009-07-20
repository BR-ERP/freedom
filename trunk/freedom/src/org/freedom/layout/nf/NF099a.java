/**
 * @version 10/12/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NF099.java <BR>
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
 * Layout da nota fiscal para a empresa 99 Ltda.
 */

package org.freedom.layout.nf;

import java.math.BigDecimal;
import java.util.Calendar;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;
import org.freedom.layout.componentes.Layout;
public class NF099a extends Layout {

	public boolean imprimir(NF nf,ImprimeOS imp) {
			 
		boolean bRetorno = super.imprimir(nf, imp);
		boolean bNat = true;
		int iNumNota = 0;
		int numMax = 42;
		int iItImp = 0;
		int iLinPag = imp.verifLinPag("NF");
		Calendar cHora = Calendar.getInstance();
		String[] sNat = new String[2];
		String sNumNota = ""; 
		String sTipoTran = "";
		String sHora;
		
		try {
			imp.limpaPags();
			
			sHora = Funcoes.strZero(String.valueOf(cHora.get(Calendar.HOUR_OF_DAY)),2) + ":" +
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.MINUTE)),2) + ":" +
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.SECOND)),2);
			  
			cab.next();
			
			iNumNota = cab.getInt(NF.C_DOC);
			
			if(iNumNota==0)
				sNumNota = "000000";
			else
				sNumNota = Funcoes.strZero(String.valueOf(iNumNota),6);
			     
			while (itens.next()) {  
				  
				if (bNat) {
					sNat[0] = Funcoes.copy(itens.getString(NF.C_DESCNAT),35);
					sNat[1] = Funcoes.setMascara(String.valueOf(itens.getInt(NF.C_CODNAT)),"#.##");
					
					bNat = false;
				}
				 
				if (imp.pRow()==0) {
					
					imp.pulaLinha(2, imp.comprimido());
					
					if (nf.getTipoNF()==NF.TPNF_ENTRADA)
						imp.say(105, "X");
					else
						imp.say( 88, "X");
					
					imp.pulaLinha(5, imp.comprimido());
					imp.say(  6, sNat[0]);
					imp.say( 42, sNat[1]);
					imp.pulaLinha(4, imp.comprimido());
					imp.say(  6, cab.getString(NF.C_RAZEMIT));
					imp.say( 92, !cab.getString(NF.C_CPFEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_CPFEMIT),"###.###.###-##") : Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##")) ;
					imp.say(125, Funcoes.dateToStrDate(cab.getDate(NF.C_DTEMITPED)));
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  6, Funcoes.copy(cab.getString(NF.C_ENDEMIT),0,50).trim()+", "+(Funcoes.copy(""+cab.getInt(NF.C_NUMEMIT),0,6).trim()).trim()+" - "+(cab.getString(NF.C_COMPLEMIT) != null ? Funcoes.copy(cab.getString(NF.C_COMPLEMIT),0,9).trim() : "").trim());
					imp.say( 70, !cab.getString(NF.C_BAIREMIT).equals("") ? Funcoes.copy(cab.getString(NF.C_BAIREMIT),0,15) : "");
					imp.say( 98, Funcoes.setMascara(cab.getString(NF.C_CEPEMIT),"#####-###"));
					imp.say(125, Funcoes.dateToStrDate(cab.getDate(NF.C_DTSAIDA)));
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  6, cab.getString(NF.C_CIDEMIT));
					imp.say( 50, (Funcoes.setMascara(cab.getString(NF.C_DDDEMIT) + " ","(####)")) + 
											(Funcoes.setMascara(cab.getString(NF.C_FONEEMIT).trim(),"####-####")));
					imp.say( 80, cab.getString(NF.C_UFEMIT));
					imp.say( 92, !cab.getString(NF.C_RGEMIT).equals("") ? cab.getString(NF.C_RGEMIT) : cab.getString(NF.C_INSCEMIT));
					imp.say(126, sHora);
					imp.pulaLinha(4, imp.comprimido());
				}	   
				
				imp.pulaLinha(1, imp.comprimido());
				imp.say(  6, String.valueOf(itens.getInt(NF.C_CODPROD)));
				imp.say( 14, itens.getString(NF.C_DESCPROD));
				imp.say( 82, Funcoes.copy(itens.getString(NF.C_ORIGFISC),0,1)+Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),0,2));
				imp.say( 87, Funcoes.copy(itens.getString(NF.C_CODUNID),4));
				imp.say( 93, Funcoes.strDecimalToStrCurrency(6,0,String.valueOf(itens.getFloat(NF.C_QTDITPED))));
				imp.say(102, Funcoes.strDecimalToStrCurrency(12,2,String.valueOf(((new BigDecimal(itens.getFloat(NF.C_VLRLIQITPED))).divide(new BigDecimal(itens.getFloat(NF.C_QTDITPED)),2,BigDecimal.ROUND_HALF_UP)))));
				imp.say(116, Funcoes.strDecimalToStrCurrency(12,2,String.valueOf(itens.getFloat(NF.C_VLRLIQITPED))));
				imp.say(132, String.valueOf(itens.getFloat(NF.C_PERCICMSITPED)));
				     
				iItImp++;
				if ((iItImp == itens.getInt(NF.C_CONTAITENS)) || (imp.pRow() == numMax)) {       	 
					if (iItImp == itens.getInt(NF.C_CONTAITENS)) {
						int iRow = imp.pRow();						
						for (int i=0; i<(numMax-iRow);i++) {
							imp.pulaLinha(1, imp.comprimido());
						}
							
						frete.next();
						imp.pulaLinha(3, imp.comprimido());
						imp.say(  6, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(cab.getFloat(NF.C_VLRBASEICMSPED))));
						imp.say( 32, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(cab.getFloat(NF.C_VLRICMSPED))));
						imp.say(116, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(cab.getFloat(NF.C_VLRLIQPED))));
						imp.pulaLinha(2, imp.comprimido());
						imp.say(  6, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(frete.getFloat(NF.C_VLRFRETEPED))));
						imp.say( 60, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRADICITPED))));
						imp.say( 87, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(cab.getFloat(NF.C_VLRIPIPED))));
						imp.say(116, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(cab.getFloat(NF.C_VLRLIQPED))));
						iItImp = 0;
					}
					else if (imp.pRow() == numMax) {
						imp.pulaLinha(3, imp.comprimido());
						imp.say(  6, "***************");
						imp.say( 32, "***************");
						imp.say(116, "***************");
						imp.pulaLinha(2, imp.comprimido());
						imp.say(  6, "***************");
						imp.say( 62, "***************");
						imp.say( 87, "***************");
						imp.say(116, "***************");
					}
					
					imp.pulaLinha(3, imp.comprimido());
					
					if (frete.getString(NF.C_TIPOFRETE)!=null) {   
						imp.say(  6, frete.getString(NF.C_RAZTRANSP));
						imp.say( 87, frete.getString(NF.C_TIPOFRETE).equals("C") ? "1" : "2");
						imp.say( 92, frete.getString(NF.C_PLACAFRETE));
						imp.say(104, frete.getString(NF.C_UFFRETE));
					}
					
					sTipoTran = frete.getString(NF.C_TIPOTRANSP);
					if (sTipoTran==null) 
						sTipoTran = "T";    
					if ( sTipoTran.equals("C") )
						imp.say(115, Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##"));
					else 
						imp.say(115, Funcoes.setMascara(frete.getString(NF.C_CNPJTRANSP),"##.###.###/####-##"));					   
					   
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  6, Funcoes.copy(frete.getString(NF.C_ENDTRANSP),0,42) + ", " + 
											Funcoes.copy(frete.getString(NF.C_NUMTRANSP),0,6));
					imp.say( 76, frete.getString(NF.C_CIDTRANSP));
					imp.say(10, frete.getString(NF.C_UFTRANSP));
					
					if (sTipoTran.equals("C") )
						imp.say(115, cab.getString(NF.C_INSCEMIT));
					else 
						imp.say(115, frete.getString(NF.C_INSCTRANSP));
					
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  2, Funcoes.strDecimalToStrCurrency(14,0,String.valueOf(frete.getFloat(NF.C_QTDFRETE))));
					imp.say( 27, frete.getString(NF.C_ESPFRETE));
					imp.say( 55, frete.getString(NF.C_MARCAFRETE));
					imp.say(102, String.valueOf(frete.getFloat(NF.C_PESOBRUTO)));
					imp.say(125, String.valueOf(frete.getFloat(NF.C_PESOLIQ)));					 
										   
					imp.pulaLinha(13, imp.comprimido());
					imp.say(125, sNumNota);
					
					for (int i=imp.pRow(); i<=iLinPag; i++)  
						imp.pulaLinha(1, imp.comprimido());
					
					imp.setPrc(0,0);
					imp.incPags();
				}
			}

			imp.fechaGravacao();
			bRetorno = true;
		} catch ( Exception err ) {
			Funcoes.mensagemErro(null,"Erro ao montar nf de venda.\n"+err.getMessage());     
			err.printStackTrace();
			bRetorno = false;
		} finally {
			cHora = null;
			sNat = null;
			sNumNota = null; 
			sTipoTran = null;
			sHora = null;
			System.gc();
		}
		return bRetorno;
	}
}

