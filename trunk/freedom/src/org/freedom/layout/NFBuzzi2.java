 /**
 * @version 10/12/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NFBuzzi2.java <BR>
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
 * Layout da nota fiscal para a empresa Iswara Ltda.
 */
package org.freedom.layout;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;

public class NFBuzzi2 extends Layout {
	
	private String sMensAdic="";
	private String sNumNota = ""; 
	  
	public boolean imprimir(NF nf,ImprimeOS imp) {
		
		boolean retorno = super.imprimir(nf, imp);
		int iNumNota = 0;
		int iProd = 0;
		final int MAXPROD = 15;
		boolean bFat = true;
		boolean bNat = true;
		boolean bTotalizou = false;
		boolean bjatem = false;
		String sCodfisc = "";
		String sSigla = "";  
		String sDesc = "";
		String sHora = null;
		String[] sNat = new String[4];
		String[] sVencs = new String[6];
		String[] sVals = new String[6];
		Vector vMatObs = null;
		Vector vDesc = null;
		Vector vValores = new Vector();
		Vector vClfisc = new Vector();
		Vector vSigla = new Vector();
		Calendar cHora = Calendar.getInstance();
		
		try {
			
			imp.limpaPags();
			
			vClfisc.addElement("");
			
			sHora = Funcoes.strZero(String.valueOf(cHora.get(Calendar.HOUR_OF_DAY)),2) + ":" +
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.MINUTE)),2) + ":" +
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.SECOND)),2);
			  
			if (cab.next()) 
				sNumNota = Funcoes.strZero(String.valueOf(cab.getInt(NF.C_DOC)),6);
			else 
				sNumNota = Funcoes.strZero(String.valueOf(iNumNota),6);
			  
			while (itens.next()) {
			              
				for (int i=0; i<6; i++) {
					if (bFat) {
						if (parc.next()) {
							sVencs[i] = Funcoes.dateToStrDate(parc.getDate(NF.C_DTVENCTO));
							sVals[i] = Funcoes.strDecimalToStrCurrency(12,2,parc.getString(NF.C_VLRPARC));
						} else {
							bFat = false;
							sVencs[i] = "**********";
							sVals[i] = "************";
						}
					} else {
						bFat = false;
						sVencs[i] = "**********";
						sVals[i] = "************";
					}          
				}
				
				if (bNat) {
					sNat[0] = itens.getString(NF.C_DESCNAT);
					sNat[1] = Funcoes.setMascara(itens.getString(NF.C_CODNAT),"#.##");
					vMatObs = Funcoes.strToVectorSilabas(cab.getString(NF.C_OBSPED),70);
					bNat = false;
				}
				
				if (imp.pRow()==0) {      
					
					imp.pulaLinha( 2, imp.comprimido());
					
					if (nf.getTipoNF()==NF.TPNF_ENTRADA)
						imp.say(102,"X");
					else
						imp.say( 90,"X");
					
					imp.say(124, sNumNota);
					imp.pulaLinha(5, imp.comprimido());
					imp.say(  6, sNat[0]);
					imp.say( 46, sNat[1]);
					imp.pulaLinha(3, imp.comprimido());
					imp.say(  6, cab.getInt(NF.C_CODEMIT)+" - "+cab.getString(NF.C_RAZEMIT));
					imp.say( 90, !cab.getString(NF.C_CPFEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_CPFEMIT),"###.###.###-##") : Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##")) ;
					imp.say(125, Funcoes.dateToStrDate(cab.getDate(NF.C_DTEMITPED)));
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  6, Funcoes.copy(cab.getString(NF.C_ENDEMIT),0,50).trim()+", "+(!cab.getString(NF.C_NUMEMIT).equals("") ? Funcoes.copy(cab.getString(NF.C_NUMEMIT),0,6).trim() : "").trim()+" - "+(!cab.getString(NF.C_COMPLEMIT).equals("") ? Funcoes.copy(cab.getString(NF.C_COMPLEMIT),0,9).trim() : "").trim());
					imp.say( 76, !cab.getString(NF.C_BAIREMIT).equals("") ? Funcoes.copy(cab.getString(NF.C_BAIREMIT),0,15) : "");
					imp.say(106, Funcoes.setMascara(cab.getString(NF.C_CEPEMIT),"#####-###"));
					imp.say(125, Funcoes.dateToStrDate(cab.getDate(NF.C_DTSAIDA)));
					imp.pulaLinha(2, imp.comprimido());
					imp.say(  6, cab.getString(NF.C_CIDEMIT));
					imp.say( 50, (!cab.getString(NF.C_DDDEMIT).equals("") ? "("+cab.getString(NF.C_DDDEMIT)+")" : "")+(!cab.getString(NF.C_FONEEMIT).equals("")? Funcoes.setMascara(cab.getString(NF.C_FONEEMIT).trim(),"####-####") : ""));
					imp.say( 83, cab.getString(NF.C_UFEMIT));
					imp.say( 90, !cab.getString(NF.C_RGEMIT).equals("") ? cab.getString(NF.C_RGEMIT) : cab.getString(NF.C_INSCEMIT));
					imp.say(125, sHora);
					imp.pulaLinha(3, imp.comprimido());					   
					imp.say(  8, sVals[0]);            
					imp.say( 36, sVencs[0]);
					imp.say( 55, sVals[1]);
					imp.say( 83, sVencs[1]);
					imp.say(102, sVals[2]);
					imp.say(130, sVencs[2]);
					imp.pulaLinha(1, imp.comprimido());
					imp.say(  8, sVals[3]);            
					imp.say( 36, sVencs[3]);
					imp.say( 55, sVals[4]);
					imp.say( 83, sVencs[4]);
					imp.say(102, sVals[5]);
					imp.say(130, sVencs[5]);    
					imp.pulaLinha(3, imp.comprimido());
					
				}
				 
				if (!itens.getString(NF.C_TIPOPROD).equals("S") && iProd<MAXPROD) {
					imp.pulaLinha(1, imp.comprimido());            
					imp.say(  8, String.valueOf(itens.getInt(NF.C_CODPROD)));   
					
					vDesc = Funcoes.strToVectorSilabas(itens.getString(NF.C_OBSITPED).equals("") ? 
									itens.getString(NF.C_DESCPROD).trim() : 
										itens.getString(NF.C_OBSITPED).trim() ,46);
					
					for (int i=0;(i < (MAXPROD-iProd)) && (vDesc.size()>i); i++){
						sDesc = vDesc.elementAt(i).toString();
						
						if (i > 0) {
							imp.pulaLinha(1, imp.comprimido());
							iProd++;
						}
						
						imp.say( 16, sDesc);
						
						
					}
					
					sMensAdic = itens.getString(NF.C_DESCFISC) + " - ";
					sCodfisc = itens.getString(NF.C_CODFISC);
					   
					if(!sCodfisc.equals("")){
						
						for(int i=0; i< vClfisc.size(); i++){
							
							if(vClfisc.elementAt(i) != null){
								
								if(sCodfisc.equals((String)vClfisc.elementAt(i))){
									bjatem = true;
									sSigla = String.valueOf((char)(64 + i));
								} else
									bjatem = false;
								
							}
							
						}
						
						if(!bjatem){
							vClfisc.addElement(sCodfisc);
							sSigla = String.valueOf((char)(63 + vClfisc.size()));
							vSigla.addElement(sSigla + " = " + sCodfisc);
						}
						
					}
					
					if( iProd < MAXPROD ) {
						imp.say( 69, Funcoes.copy(itens.getString(NF.C_ORIGFISC),0,1)+Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),0,2));
						imp.say( 73, sSigla);					
						imp.say( 79, itens.getString(NF.C_CODUNID).substring(0,4));
						imp.say( 82, String.valueOf(itens.getFloat(NF.C_QTDITPED)));
						imp.say( 92, Funcoes.strDecimalToStrCurrency(8,2,String.valueOf(((new BigDecimal(itens.getFloat(NF.C_VLRLIQITPED))).divide(new BigDecimal(itens.getFloat(NF.C_QTDITPED)),2,BigDecimal.ROUND_HALF_UP)))));
						imp.say(106, Funcoes.strDecimalToStrCurrency(13,2,String.valueOf(itens.getFloat(NF.C_VLRLIQITPED))));
						imp.say(122, String.valueOf(itens.getFloat(NF.C_PERCICMSITPED)));
						iProd++;
					}
					
				}
				 
				if (!bTotalizou) {
					frete.next();
					
					vValores.addElement(String.valueOf(itens.getFloat(NF.C_VLRBASEICMSPED)));//0
					vValores.addElement(String.valueOf(itens.getFloat(NF.C_VLRICMSPED)));//1
					vValores.addElement(String.valueOf((itens.getFloat(NF.C_VLRLIQPED)-frete.getFloat(NF.C_VLRFRETEPED)-itens.getFloat(NF.C_VLRADICPED))));// 2
					vValores.addElement(String.valueOf(frete.getFloat(NF.C_VLRFRETEPED)));//3
					vValores.addElement(String.valueOf(itens.getFloat(NF.C_VLRADICPED)));//4
					vValores.addElement(String.valueOf(itens.getFloat(NF.C_VLRIPIPED)));//5
					vValores.addElement(String.valueOf(itens.getFloat(NF.C_VLRLIQPED)));//6
					vValores.addElement(frete.getString(NF.C_RAZTRANSP));//7
					vValores.addElement(frete.getString(NF.C_TIPOFRETE));//8
					vValores.addElement(frete.getString(NF.C_PLACAFRETE));//9
					vValores.addElement(frete.getString(NF.C_UFFRETE));//10   
					vValores.addElement(frete.getString(NF.C_TIPOTRANSP));//11
					vValores.addElement(cab.getString(NF.C_CNPJEMIT));//12
					vValores.addElement(frete.getString(NF.C_CNPJTRANSP));   //13         
					vValores.addElement(frete.getString(NF.C_ENDTRANSP));//14
					   
					if (frete.getString(NF.C_TIPOTRANSP).equals("C")) {
						
						vValores.addElement("");//15
						vValores.addElement("");//16
						vValores.addElement("");//17
						vValores.addElement("");//18
						
					} else {
						
						vValores.addElement(String.valueOf(frete.getInt(NF.C_NUMTRANSP)));//15
						vValores.addElement(frete.getString(NF.C_CIDTRANSP));//16
						vValores.addElement(frete.getString(NF.C_UFTRANSP));//17
						vValores.addElement(frete.getString(NF.C_INSCTRANSP));//18
					
					}
					   
					vValores.addElement(String.valueOf(frete.getFloat(NF.C_QTDFRETE)));//19
					vValores.addElement(frete.getString(NF.C_ESPFRETE));//20
					vValores.addElement(frete.getString(NF.C_MARCAFRETE));//21
					vValores.addElement(String.valueOf(frete.getFloat(NF.C_PESOBRUTO)));//22
					vValores.addElement(String.valueOf(frete.getFloat(NF.C_PESOLIQ)));//23
					vValores.addElement(String.valueOf(itens.getFloat(NF.C_VLRISSPED)));//24
					vValores.addElement(String.valueOf(cab.getInt(NF.C_CODVEND)));//25
					
					if (cab.getString(NF.C_NOMEVEND).equals(""))
						vValores.addElement(Funcoes.replicate(" ",25));// 26
					else 
						vValores.addElement(cab.getString(NF.C_NOMEVEND)+Funcoes.replicate(" ",25-cab.getString(NF.C_NOMEVEND).length()));
					
					bTotalizou = true;
					
				} 
			     
			}
			
			imp.pulaLinha(1, imp.comprimido());
			imp.say( 16, "CLASSIFICACAO FISCAL");
			
			int pos = 1;
			
			for(int i=0; i < vSigla.size(); i++){				
				if(pos==1){
					imp.pulaLinha(1, imp.comprimido());
					imp.say( 16, (String)vSigla.elementAt(i));
					pos = 2;
				} else{
					imp.say( 35, (String)vSigla.elementAt(i));
					pos = 1;
					iProd++;
				}				
			}

			if (imp.pRow()<36) {   								
				for (int i=0; i < vMatObs.size(); i++) {
					imp.pulaLinha(1, imp.comprimido());
					imp.say( 10, (String)vMatObs.elementAt(i));
				}				
			}
			
			imp.pulaLinha( 38-imp.pRow(), imp.comprimido());
			imp.say( 10, "PRORROGADO O PRAZO DE VALIDADE DA EMISS�O DAS NOTAS FISCAIS PARA 18 MESES.");
			imp.pulaLinha( 1, imp.comprimido());
			imp.say( 10, "DE ACORDO COM O DECRETO N. 5502 DE 10 DE OUTUBRO DE 2005,");
			imp.pulaLinha( 1, imp.comprimido());
			imp.say( 10, "COM VIGENCIA A PARTIR DE 1 DE SETEMBRO DE 2005.");
			
			
			impTotais(imp,vValores);
			
			imp.fechaGravacao();
			
			retorno = true;
			
			if (iProd>=MAXPROD) 
				Funcoes.mensagemInforma(null,"Podem haver erros na impress�o da nota fiscal.\n" + 
						"Produtos ultrapassam " + MAXPROD + " linhas!");

		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro(null,"Erro ao montar Nota Fiscal!\n"+err.getMessage());      
			retorno = false;
		} finally {
			sCodfisc = null;
			sSigla = null;  
			sHora = null;
			sNat = null;
			sVencs = null;
			sVals = null;
			//sMatObs = null;
			vValores = null;
			vClfisc = null;
			vSigla = null;
			cHora = null;
			System.gc();
		}
		
		return retorno;
		
	}
	
	private void impTotais(ImprimeOS imp,Vector vValores){
		
		Vector vObs = null;
		
		try {	
			
			imp.pulaLinha( 45-imp.pRow(), imp.comprimido());
			
			imp.pulaLinha( 1, imp.comprimido());
			imp.say(  6, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(0).toString()));
			imp.say( 35, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(1).toString()));
			imp.say(117, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(2).toString()));
			imp.pulaLinha( 2, imp.comprimido());
			imp.say(  6, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(3).toString()));
			imp.say( 65, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(4).toString()));
			imp.say( 90, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(5).toString()));
			imp.say(117, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(6).toString()));
			imp.pulaLinha( 3, imp.comprimido());		    	        	       	      	      
			imp.say(  8, vValores.elementAt(7) != null ? vValores.elementAt(7).toString() : "");
			imp.say( 80, vValores.elementAt(8) != null ? (vValores.elementAt(8).toString().equals("C") ? "1" : "2") : "");
			imp.say( 87, vValores.elementAt(9) != null ? vValores.elementAt(9).toString() : "");
			imp.say(103, vValores.elementAt(10) != null ? vValores.elementAt(10).toString() : "");
						  
			if ( ! vValores.elementAt(11).toString().equals("C") )
				imp.say(118, Funcoes.setMascara(vValores.elementAt(13) != null ? vValores.elementAt(13).toString() : "","##.###.###/####-##"));

			imp.pulaLinha( 2, imp.comprimido());
			imp.say(  8, vValores.elementAt(14) != null ? vValores.elementAt(14).toString().trim() : "");
			imp.say( 68, vValores.elementAt(16) != null ? vValores.elementAt(16).toString().trim() : "");
			imp.say(102, vValores.elementAt(17) != null ? vValores.elementAt(17).toString().trim() : "");
			imp.say(121, vValores.elementAt(18) != null ? vValores.elementAt(18).toString() : "");			    	      
			imp.pulaLinha( 2, imp.comprimido());		  
			imp.say(  8, vValores.elementAt(19) != null ? vValores.elementAt(19).toString() : "");
			imp.say( 25, vValores.elementAt(20) != null ? vValores.elementAt(20).toString() : "");
			imp.say( 48, vValores.elementAt(21) != null ? vValores.elementAt(21).toString() : "");
			imp.say(103, vValores.elementAt(22) != null ? vValores.elementAt(22).toString() : "");
			imp.say(128, vValores.elementAt(23) != null ? vValores.elementAt(23).toString() : "");			  
			imp.pulaLinha( 2, imp.comprimido());
			  
			vObs = Funcoes.quebraLinha(Funcoes.stringToVector(sMensAdic),37);
			
			for (int i=0; i < vObs.size(); i++) {
				imp.pulaLinha(1, imp.comprimido());				
				imp.say(  4, vObs.elementAt(i).toString());
				
				if(i==0)
					imp.say( 45, "Vendedor: " + ( vValores.elementAt(25) != null ? vValores.elementAt(25).toString() : "" ));
				if(i==1)
					imp.say( 45, vValores.elementAt(26) != null ? vValores.elementAt(26).toString().substring(0,20) : "");
			}
			  
			imp.pulaLinha(7, imp.comprimido());
			imp.say(124, sNumNota);
			imp.pulaLinha(3, imp.comprimido());
			
			imp.setPrc(0,0);
			
		} catch (Exception e) {
			e.printStackTrace();
			Funcoes.mensagemErro( null, "Erro ao imprimir totais.\n" + e.getMessage());
		} finally {  		  
			vObs = null;
		}
	
	}

}