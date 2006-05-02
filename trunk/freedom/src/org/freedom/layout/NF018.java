/**
 * @version 10/12/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NF018.java <BR>
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
 * Layout da nota fiscal para a empresa 018 Ltda.
 */

package org.freedom.layout;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;
public class NF018 extends Layout {

	public boolean imprimir(NF nf,ImprimeOS imp) {
				 
		boolean bRetorno = super.imprimir(nf, imp);
		boolean bFat = true;
		boolean bNat = true;
		int iNumNota = 0;
		int numMax = 36;
		int iItImp = 0;
		int iLinPag = imp.verifLinPag("NF");
		int iContaObs = 0; //Contador para a gamb de asterisco
		int iContaObs2 = 0; //Contador para a gamb de '"'
		Calendar cHora = Calendar.getInstance();
		Vector vMens = null;
		String[] sNat = new String[2];
		String[] sVencs = new String[2];
		String[] sVals = new String[2];
		String[] sMatObs = null;
		String[] sMarcs = {"\"","\"\"","\"\"\"","\"\"\"\""}; //Tipos de Marcs. 
		String[] sMarcs2 = {"*","**","***","****"}; //Tipos de Marcs.
		String sNumNota = null; 
		String sTipoTran = null;
		String sMens = "";
		String sTmp = null;
		String sTmp2 = null;
		String sHora;
		
		try {
			imp.limpaPags();
				
			sHora  = Funcoes.strZero(String.valueOf(cHora.get(Calendar.HOUR_OF_DAY)),2)+":"
					+Funcoes.strZero(String.valueOf(cHora.get(Calendar.MINUTE)),2)+":"
					+Funcoes.strZero(String.valueOf(cHora.get(Calendar.SECOND)),2);
			  
			cab.next();
			
			iNumNota = cab.getInt(NF.C_DOC);
			
			if(iNumNota==0)
				sNumNota = "000000";
			else 
				sNumNota = Funcoes.strZero(""+iNumNota,6);
			
			for (int i=0; i<2; i++) {
				if (bFat) {
					if (parc.next()) {
						sVencs[i] = Funcoes.dateToStrDate(parc.getDate(NF.C_DTVENCTO));
						sVals[i] = Funcoes.strDecimalToStrCurrency(10,2,parc.getString(NF.C_VLRPARC));
					} else {
						bFat = false;
						sVencs[i] = "";
						sVals[i] = "";
					}
				}
				else {
					bFat = false;
					sVencs[i] = "";
					sVals[i] = "";
				}
			}
			
			     
			while (itens.next()) {  
					  
				if (bNat) {
					sNat[0] = Funcoes.copy(itens.getString(NF.C_DESCNAT),40);
					sNat[1] = Funcoes.setMascara(String.valueOf(itens.getInt(NF.C_CODNAT)),"#.##");
					sMatObs = Funcoes.strToStrArray(cab.getString(NF.C_OBSPED),3);						
					bNat = false;
				}
				 
				if (imp.pRow()==0) {
					imp.say(imp.pRow()+1,0, imp.comprimido());
					if (nf.getTipoNF()==NF.TPNF_ENTRADA)
						imp.say(imp.pRow(),105,"X");
					else
						imp.say(imp.pRow(), 88,"X");
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  2, sNat[0]);
					imp.say(imp.pRow(), 50, sNat[1]);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  2, cab.getInt(NF.C_CODEMIT)+" - "+cab.getString(NF.C_RAZEMIT));
					imp.say(imp.pRow(), 92, !cab.getString(NF.C_CPFEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_CPFEMIT),"###.###.###-##") : Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##")) ;
					imp.say(imp.pRow(),126, Funcoes.dateToStrDate(cab.getDate(NF.C_DTEMITPED)));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  2, Funcoes.copy(cab.getString(NF.C_ENDEMIT),0,50).trim()+", "+(Funcoes.copy(""+cab.getInt(NF.C_NUMEMIT),0,6).trim()).trim()+" - "+(cab.getString(NF.C_COMPLEMIT) != null ? Funcoes.copy(cab.getString(NF.C_COMPLEMIT),0,9).trim() : "").trim());
					imp.say(imp.pRow(), 72, Funcoes.copy(cab.getString(NF.C_BAIREMIT),0,15));
					imp.say(imp.pRow(),105, Funcoes.setMascara(cab.getString(NF.C_CEPEMIT),"#####-###"));
					imp.say(imp.pRow(),126, Funcoes.dateToStrDate(cab.getDate(NF.C_DTSAIDA)));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  2, cab.getString(NF.C_CIDEMIT));
					imp.say(imp.pRow(), 56, (Funcoes.setMascara(cab.getString(NF.C_DDDEMIT) + " ","(####)")) + 
											(Funcoes.setMascara(cab.getString(NF.C_FONEEMIT).trim(),"####-####")));
					imp.say(imp.pRow(), 83, cab.getString(NF.C_UFEMIT));
					imp.say(imp.pRow(), 92, !cab.getString(NF.C_RGEMIT).equals("") ? cab.getString(NF.C_RGEMIT) : cab.getString(NF.C_INSCEMIT));
					imp.say(imp.pRow(),126, sHora);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(), 42, sVals[0]);
					imp.say(imp.pRow(), 56, sVencs[0]);
					imp.say(imp.pRow(),112, sVals[1]);
					imp.say(imp.pRow(),126, sVencs[1]); 
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
				}
							
				sTmp = itens.getString(NF.C_DESCFISC).trim()+'\n' ; 
				//Gambs para colocar '"':
				if (sTmp.length() > 0) {
					if (sMens.indexOf(sTmp) == -1 && iContaObs < 4)//Esta mensagem ainda n�o esta na obs ent�o posso adiciona-la.
						sMens += sMarcs[iContaObs++]+" "+sTmp;
				}
				sTmp2 = itens.getString(NF.C_DESCFISC2).trim()+'\n' ;
				//Gambs para colocar '*'
				if (sTmp2.trim().length() > 0 && !sTmp.equals(sTmp2)) {
					if (sMens.indexOf(sTmp2) == -1 && iContaObs2 < 4) //Esta mensagem ainda n�o esta na obs ent�o posso adiciona-la.
						sMens += sMarcs2[iContaObs2++]+" "+sTmp2;
				} 			   
				
				imp.say(imp.pRow()+1,0, imp.comprimido());
				imp.say(imp.pRow(),  4, itens.getString(NF.C_DESCPROD));
				imp.say(imp.pRow(), 59, Funcoes.copy(itens.getString(NF.C_ORIGFISC),0,1)+Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),0,2));
				imp.say(imp.pRow(), 65, itens.getString(NF.C_CODUNID).substring(0,4));
				imp.say(imp.pRow(), 71, Funcoes.strDecimalToStrCurrency(10,3,String.valueOf(itens.getFloat(NF.C_QTDITPED))));
				imp.say(imp.pRow(), 90, Funcoes.strDecimalToStrCurrency(8,2,String.valueOf(((new BigDecimal(itens.getFloat(NF.C_VLRLIQITPED))).divide(new BigDecimal(itens.getFloat(NF.C_QTDITPED)),2,BigDecimal.ROUND_HALF_UP)))));
				imp.say(imp.pRow(),101, Funcoes.strDecimalToStrCurrency(13,2,String.valueOf(itens.getFloat(NF.C_VLRLIQITPED))));
				imp.say(imp.pRow(),116, String.valueOf(itens.getFloat(NF.C_PERCICMSITPED)));
				imp.say(imp.pRow(),121, String.valueOf(itens.getFloat(NF.C_PERCIPIITPED)));
				imp.say(imp.pRow(),128, Funcoes.strDecimalToStrCurrency(7,2,""+itens.getFloat(NF.C_VLRIPIITPED)));
				     
				iItImp++;
				if ((iItImp == itens.getInt(NF.C_CONTAITENS)) || (imp.pRow() == numMax)) {  

					frete.next();
					
					if (iItImp == itens.getInt(NF.C_CONTAITENS)) {
						int iRow = imp.pRow();
						if (!sMatObs[0].equals("")) {
							imp.say(imp.pRow()+1,0, imp.comprimido());
							imp.say(imp.pRow(), 23, sMatObs[0]);
						}
						if (!sMatObs[1].equals("")) {
							imp.say(imp.pRow()+1,0, imp.comprimido());
							imp.say(imp.pRow(), 23, sMatObs[1]);
						}
						if (!sMatObs[2].equals("")) {
							imp.say(imp.pRow()+1,0, imp.comprimido());
							imp.say(imp.pRow(), 23, sMatObs[2]);
						}
						for (int i=0; i<(numMax-iRow);i++)
							imp.say(imp.pRow()+1,0, imp.comprimido());
							
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  1, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRBASEICMSPED))));
						imp.say(imp.pRow(), 32, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRICMSPED))));
						imp.say(imp.pRow(),116, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRLIQPED))));
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  2, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(frete.getFloat(NF.C_VLRFRETEPED))));
						imp.say(imp.pRow(), 60, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRADICPED))));
						imp.say(imp.pRow(), 87, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRIPIPED))));
						imp.say(imp.pRow(),116, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRLIQPED))));
						iItImp = 0;
					}
					else if (imp.pRow() == numMax) {
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  2, "***************");
						imp.say(imp.pRow(), 32, "***************");
						imp.say(imp.pRow(),116, "***************");
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  2, "***************");
						imp.say(imp.pRow(), 62, "***************");
						imp.say(imp.pRow(), 87, "***************");
						imp.say(imp.pRow(),116, "***************");
					}
					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					
					if (frete.getString(NF.C_TIPOFRETE)!=null) {   
						imp.say(imp.pRow(),  4, frete.getString(NF.C_RAZTRANSP));
						imp.say(imp.pRow(), 85, frete.getString(NF.C_TIPOFRETE).equals("C") ? "1" : "2");
						imp.say(imp.pRow(), 88, frete.getString(NF.C_PLACAFRETE));
						imp.say(imp.pRow(),102, frete.getString(NF.C_UFFRETE));
					}
					
					sTipoTran = frete.getString(NF.C_TIPOTRANSP);
					
					if ( sTipoTran.equals("C") )
						imp.say(imp.pRow(),115, Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##"));
					else 
						imp.say(imp.pRow(),115, Funcoes.setMascara(frete.getString(NF.C_CNPJTRANSP),"##.###.###/####-##"));					   
					   
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  4, Funcoes.copy(frete.getString(NF.C_ENDTRANSP),0,42)+", "+Funcoes.copy(frete.getString(NF.C_NUMTRANSP),0,6));
					imp.say(imp.pRow(), 76, frete.getString(NF.C_CIDTRANSP));
					imp.say(imp.pRow(),110, frete.getString(NF.C_UFTRANSP));
					
					if (sTipoTran.equals("C") )
						imp.say(imp.pRow(),115,cab.getString(NF.C_INSCEMIT));
					else 
						imp.say(imp.pRow(),115,frete.getString(NF.C_INSCTRANSP));
					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  4, String.valueOf(frete.getFloat(NF.C_QTDFRETE)));
					imp.say(imp.pRow(), 27, frete.getString(NF.C_ESPFRETE));
					imp.say(imp.pRow(), 55, frete.getString(NF.C_MARCAFRETE));
					imp.say(imp.pRow(),102, String.valueOf(frete.getFloat(NF.C_PESOBRUTO)));
					imp.say(imp.pRow(),125, String.valueOf(frete.getFloat(NF.C_PESOLIQ)));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					  			   
					vMens = Funcoes.strToVectorSilabas(sMens,60);
					   
					for (int i=0; i < vMens.size(); i++) {
						if( vMens.elementAt(i)!= null) {
							imp.say(imp.pRow()+1,0, imp.comprimido());
							imp.say(imp.pRow(),  4, (String)vMens.elementAt(i));
						}
					}
										   
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),125, sNumNota);
					
					for (int i=imp.pRow(); i<=iLinPag; i++)  
						imp.say(imp.pRow()+1,0, imp.comprimido());
							
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
			vMens = null;
			sNat = null;
			sVencs = null;
			sVals = null;
			sMatObs = null;
			sMarcs = null; 
			sMarcs2 = null;
			sNumNota = null; 
			sTipoTran = null;
			sMens = null;
			sTmp = null;
			sTmp2 = null;
			sHora = null;
			System.gc();
		}
		return bRetorno;
	}
}