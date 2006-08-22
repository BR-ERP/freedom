/**
 * @version 22/05/2006 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: layout <BR>
 * Classe: @(#)NFIswara.java <BR>
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class NFIswara extends Layout {
    
	private NF nf = null;
	
	public boolean imprimir(NF nf,ImprimeOS imp) {
	
		boolean retorno = super.imprimir(nf, imp);
		boolean bFat = true;
		boolean bNat = true;
		boolean bjatem = false;
		boolean bjatem1 = false;
		boolean bjatem2 = false;
		final int MAXLINE = 32;
		int iNumNota = 0;
		int iItImp = 0;
		int iContaFrete = 0;
		int iLinPag = imp.verifLinPag("NF");
		int sizeObs = 0;
		int indexDescFisc = 0;
		int indexSigla = 0;
		int indexObs = 0;
		String sCodfisc = null;
		String sSigla = null;
		String sTemp = null;
		String sDescFisc = "";
		String sEmitente = null;
		String[] sValsCli = new String[4];
		String[] sNat = new String[2];
		String[] sVencs = new String[4];
		String[] sVals = new String[4];
		Vector vClfisc = new Vector();
		Vector vSigla = new Vector();
		Vector vObs = new Vector();
		Vector vDescFisc = new Vector();
		
		try{
		    
		    this.nf = nf;
		    
		    sEmitente = getEmitente();
			
			if ( cab.next() ) { 
				iNumNota = cab.getInt(NF.C_DOC);
		        vObs = Funcoes.strToVectorSilabas(cab.getString(NF.C_OBSPED),120);				
			}
							
			for ( int i=0; i < 4; i++ ) {
				if ( bFat ) {
					if ( parc.next() ) {
						sVencs[i] = (parc.getDate(NF.C_DTVENCTO)!=null ? Funcoes.dateToStrDate(parc.getDate(NF.C_DTVENCTO)) : "");
						sVals[i] = Funcoes.strDecimalToStrCurrency(12,2,String.valueOf(parc.getFloat(NF.C_VLRPARC)));
					} else {
						bFat = false;
						sVencs[i] = "";
						sVals[i] = "";
					}
				} else {
					bFat = false;
					sVencs[i] = "";
					sVals[i] = "";
				}
			}
			
			imp.limpaPags();
			
			vClfisc.addElement("");
			
			while (itens.next()) {
				
				if (bNat) {
					sNat[0] = Funcoes.copy(itens.getString(NF.C_DESCNAT).trim(),32);
					sNat[1] = Funcoes.setMascara(itens.getString(NF.C_CODNAT),"#.###");
					bNat = false;				     
				}
				
				if (adic.next()) {
					sValsCli[0] = !adic.getString(NF.C_CPFEMITAUX).equals("") ? adic.getString(NF.C_CPFEMITAUX) : cab.getString(NF.C_CPFEMIT);
					sValsCli[1] = !adic.getString(NF.C_NOMEEMITAUX).equals("") ? adic.getString(NF.C_NOMEEMITAUX) : cab.getString(NF.C_RAZEMIT);
					sValsCli[2] = !adic.getString(NF.C_CIDEMITAUX).equals("") ? adic.getString(NF.C_CIDEMITAUX) : cab.getString(NF.C_CIDEMIT);
					sValsCli[3] = !adic.getString(NF.C_UFEMITAUX).equals("") ? adic.getString(NF.C_UFEMITAUX) : cab.getString(NF.C_UFEMIT);
				} else {
					sValsCli[0] = cab.getString(NF.C_CPFEMIT);
					sValsCli[1] = cab.getString(NF.C_RAZEMIT);
					sValsCli[2] = cab.getString(NF.C_CIDEMIT);
					sValsCli[3] = cab.getString(NF.C_UFEMIT); 
				}
				
				if (imp.pRow()==0) {	
					
					//	Imprime cabe�alho da nota ...
					
					imp.pulaLinha( 1, imp.comprimido());
					
					if (nf.getTipoNF()==NF.TPNF_ENTRADA)
						imp.say(103, "X");
					else
						imp.say( 88, "X");

					imp.say( 130, Funcoes.strZero(String.valueOf(iNumNota),6) );
					imp.pulaLinha( 5, imp.comprimido());
					imp.say(  2, sNat[0]);
					imp.say( 37, sNat[1]);
					imp.pulaLinha( 3, imp.comprimido());  
					imp.say(  2, sValsCli[1] );
					imp.say( 91, !sValsCli[0].equals("") ? Funcoes.setMascara(sValsCli[0],"###.###.###-##") : Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##")) ;
					imp.say(124, (cab.getDate(NF.C_DTEMITPED) != null ? Funcoes.dateToStrDate(cab.getDate(NF.C_DTEMITPED)) : ""));
					imp.pulaLinha( 2, imp.comprimido());
					imp.say(  2, Funcoes.copy(cab.getString(NF.C_ENDEMIT),0,50).trim() + ", " + Funcoes.copy(cab.getString(NF.C_NUMEMIT),0,6).trim() + " - " + Funcoes.copy(cab.getString(NF.C_COMPLEMIT),0,9).trim());
					imp.say( 72, Funcoes.copy(cab.getString(NF.C_BAIREMIT),0,23));
					imp.say( 102, Funcoes.setMascara(cab.getString(NF.C_CEPEMIT),"#####-###"));
					
					if (!itens.getString(NF.C_IMPDTSAIDA).equals("N"))
						imp.say(124, (cab.getDate(NF.C_DTSAIDA)!= null ? Funcoes.dateToStrDate(cab.getDate(NF.C_DTSAIDA)) : ""));
					
					imp.pulaLinha( 2, imp.comprimido());
					imp.say(  2, sValsCli[2]);
					imp.say( 50, (!cab.getString(NF.C_DDDEMIT).equals("") ? "("+cab.getString(NF.C_DDDEMIT)+")" : "")+
											(!cab.getString(NF.C_FONEEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_FONEEMIT).trim(),"####-####") : "").trim());
					imp.say( 81, sValsCli[3]);
					imp.say( 91, !cab.getString(NF.C_RGEMIT).equals("") ? cab.getString(NF.C_RGEMIT) : cab.getString(NF.C_INSCEMIT));
										
					//	Fim do cabe�alho ...
					
					//	Imprime dados da fatura ...
										
					imp.pulaLinha( 3, imp.comprimido());
					imp.say(  2, Funcoes.copy(cab.getString(NF.C_DESCPLANOPAG),26) + " = " +
					             (!sVencs[0].equals("") ? ( sVencs[0] + " | " + sVals[0] ) : "" ) +
					             (!sVencs[1].equals("") ? ( " - " + sVencs[1] + " |" + sVals[1] ) : "" ) +
					             (!sVencs[2].equals("") ? ( " - " + sVencs[2] + " |" + sVals[2] ) : "" ) +
					             (!sVencs[3].equals("") ? ( " - " + sVencs[3] + " |" + sVals[3] ) : "" ) );
					imp.pulaLinha( 2, imp.comprimido());
					imp.say(  2, Funcoes.copy(String.valueOf(cab.getInt(NF.C_CODPED)),26));
					imp.say( 30, Funcoes.copy(String.valueOf(cab.getInt(NF.C_CODEMIT)),26));
					imp.say( 58, sEmitente);
					imp.say( 86, Funcoes.copy(cab.getString(NF.C_CODVEND),26));
					imp.say(114, Funcoes.copy(cab.getString(NF.C_NOMEBANCO),26));
					imp.pulaLinha( 2, imp.comprimido());
					
					//	Fim dos dados da fatura ...
					
				}
				
				//	Monta a observa��o ...
				
				sTemp = itens.getString(NF.C_DESCFISC).trim();
				if( sDescFisc.length() > 0 ) {
					if( sDescFisc.indexOf( sTemp ) > -1 )
						bjatem1 = true;
					if(!bjatem1)
						vDescFisc.addElement( sDescFisc );
					bjatem1 = false;
				} else 
				    sDescFisc = sTemp;
				
				sDescFisc = itens.getString(NF.C_DESCFISC2).trim();
				if( sDescFisc.length() > 0 ) {
					if( sDescFisc.indexOf( sTemp ) > -1 )
						bjatem2 = true;				
					if(!bjatem2)
						vDescFisc.addElement( sDescFisc );	
					bjatem2 = false;					
				} else 
				    sDescFisc = sTemp;
				
				//	Fim da observa��o ...
				
				//	Defini��o da sigla para a classifica��o fiscal...
				
				sCodfisc = itens.getString(NF.C_CODFISC);
				   
				if( ! sCodfisc.equals("") ) {
					
					for( int i=0; i < vClfisc.size(); i++ ) {						
						if( vClfisc.elementAt(i) != null ) {							
							if( sCodfisc.equals( (String)vClfisc.elementAt(i) ) ) {
								bjatem = true;
								sSigla = String.valueOf((char)(64 + i));
							} else
								bjatem = false;							
						}						
					}
					
					if( ! bjatem ) {
						vClfisc.addElement(sCodfisc);
						sSigla = String.valueOf( (char)(63 + vClfisc.size() ) );
						vSigla.addElement( sSigla + " = " + sCodfisc );
					}
					
				}
				
				// 	Fim da classifica��o fiscal ...
				
				//	Imprime os dados do item no corpo da nota ...
				
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say(  0, Funcoes.alinhaCentro(itens.getInt(NF.C_CODPROD),5) );
				imp.say(  7, Funcoes.copy(itens.getString(NF.C_DESCPROD).trim(), 23) );
				imp.say( 32, itens.getString(NF.C_CODLOTE) );
				imp.say( 49, Funcoes.dateToStrDate(itens.getDate(NF.C_VENCLOTE)));
				imp.say( 62, sSigla);
				imp.say( 66, Funcoes.copy(itens.getString(NF.C_ORIGFISC),1) + Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),2));
				imp.say( 71, Funcoes.copy(itens.getString(NF.C_CODUNID),4));
				imp.say( 77, Funcoes.strDecimalToStrCurrency( 9,0,String.valueOf(itens.getFloat(NF.C_QTDITPED))));          
				imp.say( 90, Funcoes.strDecimalToStrCurrency(12,2,String.valueOf(itens.getFloat(NF.C_VLRPRODITPED) / itens.getFloat(NF.C_QTDITPED))));
				imp.say(105, Funcoes.strDecimalToStrCurrency(13,2,String.valueOf(itens.getFloat(NF.C_VLRPRODITPED))));
				imp.say(121, ((int)itens.getFloat(NF.C_PERCICMSITPED))+"%");
				imp.say(126, ((int)itens.getFloat(NF.C_PERCIPIITPED))+"%");
				imp.say(130, Funcoes.strDecimalToStrCurrency(6,2,String.valueOf(itens.getFloat(NF.C_VLRIPIITPED))));
				
				// 	Fim da impress�o do item ...
				
				
				iItImp++;
				if ((iItImp == itens.getInt(NF.C_CONTAITENS)) || (imp.pRow() == MAXLINE - 2)) {
									    
				    if (iItImp == itens.getInt(NF.C_CONTAITENS)) {	
				        
						//	Imprime o desconto ...
				        
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say(  8, "Valor do desconto : " + Funcoes.strDecimalToStrCurrency( 9,2,String.valueOf(cab.getFloat(NF.C_VLRDESCITPED))));
				    						
						//	Imprime observa��o no corpo da nota ...
						
						for( int i=0; i < vObs.size(); i++ ) {
						    if( imp.pRow() < MAXLINE ) {
						        imp.pulaLinha( 1, imp.comprimido() );
								imp.say(  8, (String)vObs.elementAt(i));
						    }
						}
				    }
					
					imp.pulaLinha( ( MAXLINE + 2 ) - imp.pRow(), imp.comprimido());
					
					//	Imprime totais ...
					
					if (iItImp == itens.getInt(NF.C_CONTAITENS)) {        

						imp.pulaLinha( 1, imp.comprimido());
						imp.say(  4, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRBASEICMSPED))));
						imp.say( 32, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRICMSPED))));
						imp.say(114, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRPRODPED))));
						imp.pulaLinha( 2, imp.comprimido());
						imp.say(  4, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(frete.getFloat(NF.C_VLRFRETEPED))));
						imp.say( 58, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRADICPED))));
						imp.say( 87, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRIPIPED))));
						imp.say(114, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRLIQPED))));
						iItImp = 0;						
					
					} else {

						imp.pulaLinha( 1, imp.comprimido());
						imp.say(  4, "********************");
						imp.say( 32, "********************");
						imp.say(114, "********************");
						imp.pulaLinha( 2, imp.comprimido());
						imp.say(  4, "********************");
						imp.say( 58, "********************");
						imp.say( 87, "********************");
						imp.say(114, "********************");
						
					}
					
					//	Fim da impress�o dos totais ...
					
					//	Imprime informa��es do frete ...
					
					if (iContaFrete == 0){
						frete.next();
						iContaFrete++;
					}
					
					imp.pulaLinha( 3, imp.comprimido());
					imp.say(  2, frete.getString(NF.C_RAZTRANSP));
					imp.say( 81, frete.getString(NF.C_TIPOFRETE).equals("C") ? "1" : "2");
					imp.say( 90, frete.getString(NF.C_PLACAFRETE));
					imp.say(107, frete.getString(NF.C_UFFRETE));
					   						
					if ( frete.getString(NF.C_TIPOTRANSP).equals("C") )
						imp.say(116, Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT) ,"##.###.###/####-##"));
					else 
						imp.say(116, Funcoes.setMascara(frete.getString(NF.C_CNPJTRANSP) ,"##.###.###/####-##"));
					
					imp.pulaLinha( 2, imp.comprimido());
					imp.say(  2, frete.getString(NF.C_ENDTRANSP).trim() + ", " + frete.getInt(NF.C_NUMTRANSP));
					imp.say( 62, frete.getString(NF.C_CIDTRANSP));
					imp.say(107, frete.getString(NF.C_UFTRANSP));					
					
					if ( frete.getString(NF.C_TIPOTRANSP).equals("C") )
						imp.say(116, cab.getString(NF.C_INSCEMIT));
					else 
						imp.say(116, frete.getString(NF.C_INSCTRANSP));
					   
					imp.pulaLinha( 2, imp.comprimido());
					imp.say(  6, Funcoes.strDecimalToStrCurrency(10,0,String.valueOf(frete.getString(NF.C_QTDFRETE))));
					imp.say( 21, Funcoes.copy(frete.getString(NF.C_ESPFRETE),17));
					imp.say( 40, frete.getString(NF.C_MARCAFRETE));
					imp.say( 62, Funcoes.copy(frete.getString(NF.C_CONHECFRETEPED),20));
					imp.say( 87, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(frete.getFloat(NF.C_PESOBRUTO))));
					imp.say(114, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(frete.getString(NF.C_PESOLIQ))));
					imp.pulaLinha( 3, imp.comprimido());					
					imp.say(  2, cab.getString(NF.C_ENDENTEMIT) + " , " + 
							     cab.getString(NF.C_NUMENTEMIT) + "   " + 
							     cab.getString(NF.C_COMPLENTEMIT) + "  /  " + 
							     cab.getString(NF.C_BAIRENTEMIT) + " - " + 
							     cab.getString(NF.C_CIDENTEMIT) + " / " + 
							     cab.getString(NF.C_UFENTEMIT));					
					imp.pulaLinha( 2, imp.comprimido());					

					//	Fim da impress�o do frete ...
					
					//	Imprime observa��o e classifica��es fiscais ...
					
					vDescFisc = Funcoes.strToVectorSilabas(sDescFisc,51);
					
					sizeObs = vSigla.size();
					sizeObs = vDescFisc.size() > sizeObs ? vDescFisc.size() : sizeObs;
					
					int aux = 0;
					for( int i=indexObs; i < 5 ; i++ ) {				
						if( aux < sizeObs ) {
							imp.pulaLinha( 1, imp.comprimido());
							if( vSigla.size() > 0 && indexSigla < vSigla.size() )
								imp.say(  2, (String)vSigla.elementAt(indexSigla++));							
							if( vDescFisc.size() > 0 && indexDescFisc < vDescFisc.size() )
								imp.say( 31, Funcoes.copy((String)vDescFisc.elementAt(indexDescFisc++),51));							
						} else{
							imp.pulaLinha( 1, imp.comprimido());							
						} 
					}
					
					// 	Fim da observa��o ...
					
					//	Imprime canhoto ...
					
					imp.pulaLinha( 3, imp.normal());
					imp.pulaLinha( 1, imp.comprimido());
					imp.say( 31, Funcoes.copy(cab.getString(NF.C_RAZEMIT),40) );
					imp.say( 73, Funcoes.copy(cab.getString(NF.C_NOMEEMIT),40) );
					imp.say(130, Funcoes.strZero(String.valueOf(iNumNota),6) );
					
					imp.pulaLinha( iLinPag - imp.pRow(), imp.comprimido());					
					imp.setPrc(0,0);
					imp.incPags();
				}
			}
			
			imp.fechaGravacao();
			retorno = true;
			
		} catch ( Exception err ) {
			Funcoes.mensagemErro(null,"Erro ao montar nota \n" + err.getMessage()); 
			err.printStackTrace();
		} finally {
			sValsCli = null;
			sNat = null;
			sVencs = null;
			sVals = null;
			System.gc();
		}
		
		return retorno;
		
	}
	
	private String getEmitente() {
	    
	    String retorno = "";
	    String sSQL = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    try {
	        
	        sSQL =  "SELECT COMENTUSU FROM SGUSUARIO " +
	        		"WHERE CODEMP=? AND CODFILIAL=? AND IDUSU=?"; 
	        ps = nf.getConexao().prepareStatement(sSQL);
	        ps.setInt(1, Aplicativo.iCodEmp);
	        ps.setInt(2, Aplicativo.iCodFilial);
	        ps.setString(3, Aplicativo.strUsuario);
	        rs = ps.executeQuery();
	        
	        if(rs.next()) {
	            retorno = Funcoes.copy(rs.getString("COMENTUSU"),4);
	        }
	        
	        if(!nf.getConexao().getAutoCommit())
	            nf.getConexao().commit();
	        
	    } catch ( Exception e ) {
	        e.printStackTrace();
        }
	    
	    return retorno;
	    
	}
	
}
