 /**
 * @version 13/04/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NFBemabra.java <BR>
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.funcoes.Funcoes;

public class NFBemabra extends Leiaute {
	
	private BigDecimal bigSomaServ = null;
	private String sMensAdic = null;
	 
	public boolean imprimir(ResultSet rs,ResultSet rsRec,ImprimeOS imp) {
	
		boolean bRetorno;
		int iNumNota = 0;
		int iProd = 0;
		int iServ = 0;
		boolean bFat = true;
		boolean bNat = true;
		boolean bTotalizou = false;
		String sTipoTran = "";
		String sDesc = null;
		String sNumNota = null; 
		String sHora = null;
		String sDescProdConcatenada = null;
		String[] sNat = new String[4];
		String[] sVencs = new String[4];
		String[] sVals = new String[4];
		String[] sDuplics = new String[4];
		String[] sMatObs = null;
		Vector vValores = new Vector();
		Vector vDesc = new Vector();
		Vector vDesc2 = null;
		Calendar cHora = Calendar.getInstance();

		bigSomaServ = new BigDecimal(0);

		try {
			
			imp.limpaPags();
			
			sHora = Funcoes.strZero(String.valueOf(cHora.get(Calendar.HOUR_OF_DAY)),2) + ":" +
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.MINUTE)),2) + ":" +
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.SECOND)),2);
	
			  
			while (rs.next()) {
				
				iNumNota = rs.getInt("DocVenda");
				
				if (iNumNota==0)
					sNumNota = "000000";
				else
					sNumNota = Funcoes.strZero(""+iNumNota,6);

				for (int i=0; i<4; i++) {
					if (bFat) {
						if (rsRec.next()) {
							sDuplics[i] = sNumNota+"/"+rsRec.getInt("NPARCITREC");
							sVencs[i] = Funcoes.sqlDateToStrDate(rsRec.getDate("DtVencItRec"));
							sVals[i] = Funcoes.strDecimalToStrCurrency(12,2,rsRec.getString("VlrParcItRec"));
						} else {
							bFat = false;
							sDuplics[i] = "********";
							sVencs[i] = "********";
							sVals[i] = "********";
						}
					} else {
						bFat = false;
						sDuplics[i] = "********";
						sVencs[i] = "********";
						sVals[i] = "********";
					}				  
				}
				
				if (bNat) {
					sNat[0] = rs.getString("DescNat");
					sNat[1] = Funcoes.setMascara(rs.getString("CodNat"),"#.##");
					sMatObs = Funcoes.strToStrArray(rs.getString("ObsVenda") != null ? rs.getString("ObsVenda") : "",3);
					bNat = false;
				}
				
				if (imp.pRow()==0) {
					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(), 85,"X");
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),118, sNumNota);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, sNat[0]);
					imp.say(imp.pRow(), 50, sNat[1]);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, rs.getInt("CodCli")+" - "+rs.getString("RazCli"));
					imp.say(imp.pRow(), 85, rs.getString("CpfCli") != null ? Funcoes.setMascara(rs.getString("CpfCli"),"###.###.###-##") : Funcoes.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##")) ;
					imp.say(imp.pRow(),120, Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda")));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, Funcoes.copy(rs.getString("EndCli"),0,50).trim()+", "+(rs.getString("NumCli") != null ? Funcoes.copy(rs.getString("NumCli"),0,6).trim() : "").trim()+" - "+(rs.getString("ComplCli") != null ? Funcoes.copy(rs.getString("ComplCli"),0,9).trim() : "").trim());
					imp.say(imp.pRow(), 71, rs.getString("BairCli")!=null ? Funcoes.copy(rs.getString("BairCli"),0,15) : "");
					imp.say(imp.pRow(), 99, Funcoes.setMascara(rs.getString("CepCli"),"#####-###"));
					imp.say(imp.pRow(),120, Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					
					if (rs.getString(8) == null) {//Se n�o tem pa�s:
						
						imp.say(imp.pRow(),  6, Funcoes.copy(rs.getString("CidCli"),30));
						imp.say(imp.pRow(), 48, (rs.getString("DDDCli") != null ? "("+rs.getString("DDDCli")+")" : "") +
												(rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli").trim(),"####-####") : ""));
						imp.say(imp.pRow(), 76, rs.getString("UfCli"));
						
					} else {
						
						imp.say(imp.pRow(),  6, rs.getString("CidCli").trim() + "-" + rs.getString("UfCli"));
						imp.say(imp.pRow(), 48, (rs.getString("DDDCli") != null ? "("+rs.getString("DDDCli")+")" : "") +
												(rs.getString("FoneCli") != null ? Funcoes.setMascara(rs.getString("FoneCli").trim(),"####-####") : ""));
						imp.say(imp.pRow(), 76, Funcoes.copy(rs.getString(8),5));
						
					}
					
					imp.say(imp.pRow(), 85, rs.getString("RgCli") != null ? rs.getString("RgCli") : rs.getString("InscCli"));
					imp.say(imp.pRow(),120, sHora);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					
				}
				 
				if (!rs.getString("TipoProd").equals("S")) {
					
					imp.say(imp.pRow()+1,0, imp.comprimido());         
					imp.say(imp.pRow(),  6, rs.getString("CodProd"));
					
					sDescProdConcatenada = rs.getString("DescProd").trim() + " - " + 
										  (rs.getString("DescAuxProd") != null ? rs.getString("DescAuxProd") : "");
					
					vDesc2 = Funcoes.strToVectorSilabas((rs.getString("ObsItVenda") == null || rs.getString("ObsItVenda").equals(""))
							? (sDescProdConcatenada) : rs.getString("ObsItVenda"), 46);
										
					for (int iConta=0; ( (iConta < 20) && (vDesc2.size()>iConta) ); iConta++){
						if (!vDesc2.elementAt(iConta).toString().equals(""))
							sDesc = vDesc2.elementAt(iConta).toString();
						else
							sDesc = "";
						
						if (iConta > 0)
							imp.say(imp.pRow()+1,0, imp.comprimido());
						
						imp.say(imp.pRow(), 14, sDesc);
						
						iProd = iProd + vDesc2.size();
						
						sMensAdic = rs.getString(5) != null ? rs.getString(5).trim() : "";
						sMensAdic += rs.getString(4) != null ? rs.getString(4).trim() : "";
					}    
					
					imp.say(imp.pRow(), 81, Funcoes.copy(rs.getString("OrigFisc"),0,1)+Funcoes.copy(rs.getString("CodTratTrib"),0,2));
					imp.say(imp.pRow(), 87, rs.getString("CodUnid").substring(0,4));
					imp.say(imp.pRow(), 92, String.valueOf(rs.getDouble("QtdItVenda")));
					imp.say(imp.pRow(),102, Funcoes.strDecimalToStrCurrency(8,2,String.valueOf(((new BigDecimal(rs.getDouble("VlrLiqItVenda"))).divide(new BigDecimal(rs.getDouble("QtdItVenda")),2,BigDecimal.ROUND_HALF_UP)))));
					imp.say(imp.pRow(),113, Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrLiqItVenda")));
					imp.say(imp.pRow(),130, String.valueOf(rs.getDouble("PercICMSItVenda")));
					
				} else {
					
					vDesc.clear();
					vDesc.addElement(Funcoes.strToVectorSilabas((rs.getString("ObsItVenda") == null || rs.getString("ObsItVenda").equals(""))
										? (rs.getString("DescProd").trim()) : rs.getString("ObsItVenda"), 45 ));
					
					vDesc.addElement(Funcoes.strDecimalToStrCurrency(13,2,rs.getString("VlrLiqItVenda"))); 
										
					bigSomaServ = bigSomaServ.add(new BigDecimal(rs.getDouble("VlrLiqItVenda")));
					 	
					iServ = iServ + vDesc.size();
				    
				}
				 
				if (!bTotalizou) {
					 
					vValores.addElement(rs.getString("VlrBaseICMSVenda"));//0
					vValores.addElement(rs.getString("VlrICMSVenda"));//1
					vValores.addElement(String.valueOf(rs.getBigDecimal("VlrLiqVenda").subtract(rs.getBigDecimal("VlrFreteVenda")).subtract(rs.getBigDecimal("VlrAdicVenda")).subtract(rs.getBigDecimal("VlrIPIVenda"))));// 2
					vValores.addElement(rs.getString("VlrFreteVenda"));//3
					vValores.addElement(rs.getString("VlrAdicVenda"));//4
					vValores.addElement(rs.getString("VlrIPIVenda"));//5
					vValores.addElement(rs.getString("VlrLiqVenda"));//6
					vValores.addElement(rs.getString("RazTran"));//7
					vValores.addElement(rs.getString("TipoFreteVD"));//8
					vValores.addElement(rs.getString("PlacaFreteVD"));//9
					vValores.addElement(rs.getString("UfFreteVD"));//10   
					vValores.addElement(sTipoTran = rs.getString("TipoTran"));//11
					vValores.addElement(rs.getString("CnpjCli"));//12
					vValores.addElement(rs.getString("CnpjTran"));//13         
					vValores.addElement(rs.getString("EndTran") != null ? rs.getString("EndTran") : "");//14
					   
					if (sTipoTran.equals("C")){
						
						vValores.addElement("");//15
						vValores.addElement("");//16
						vValores.addElement("");//17
						vValores.addElement("");//18
						
					} else {
						
						vValores.addElement(rs.getString("NumTran") != null ? rs.getString("NumTran") : "");//15
						vValores.addElement(rs.getString("CidTran") != null ? rs.getString("CidTran") : "");//16
						vValores.addElement(rs.getString("UfTran") != null ? rs.getString("UfTran") : "");//17
						vValores.addElement(rs.getString("InscTran") != null ? rs.getString("InscTran") : ""); //18
						
					}
					   
					vValores.addElement(rs.getString("QtdFreteVD"));//19
					vValores.addElement(rs.getString("EspFreteVD"));//20
					vValores.addElement(rs.getString("MarcaFreteVD"));//21
					vValores.addElement(rs.getString("PesoBrutVD"));//22
					vValores.addElement(rs.getString("PesoLiqVD"));//23
					vValores.addElement(rs.getString("VlrIssVenda"));//24
					
					bTotalizou = true;
					
				} 
				     
			}
			if (imp.pRow()<39) {
				
				imp.say(imp.pRow()+1,0, imp.comprimido());
				
				for (int i=0; i < 3; i++) {
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(), 21, sMatObs[i]);
				}
			}

			impTotais(imp,vValores);
			
			imp.fechaGravacao();
			
			bRetorno = true;
			
			if (iProd > 20) 
				Funcoes.mensagemInforma(null,"Podem haver erros na impress�o da nota fiscal."+"\n"+"Produtos ultrapassam vinte linhas!");      	
			
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro(null,"Erro ao consultar tabela de Venda!" + err.getMessage());      
			bRetorno = false;
		}
		return bRetorno;
		
	}
	
	private void impTotais(ImprimeOS imp,Vector vValores) {
		
		String[] sMatObs = null;
		
		try {	
				
			for (int i=0;(imp.pRow()<40);i++)
				imp.say(imp.pRow()+1,0, imp.comprimido());
			  
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow(),  4, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(0).toString()));
			imp.say(imp.pRow(),  3, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(1).toString()));
			imp.say(imp.pRow(),115, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(2).toString()));
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow(),  4, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(3).toString()));
			imp.say(imp.pRow(), 57, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(4).toString()));
			imp.say(imp.pRow(), 83, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(5).toString()));
			imp.say(imp.pRow(),115, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(6).toString()));
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow(),  6, vValores.elementAt(7).toString());
			imp.say(imp.pRow(), 83, vValores.elementAt(8).toString().equals("C") ? "1" : "2");
			imp.say(imp.pRow(), 90, vValores.elementAt(9).toString());
			imp.say(imp.pRow(),102, vValores.elementAt(10).toString());
			  			  
			if ( !vValores.elementAt(11).toString().equals("C") )
				imp.say(imp.pRow(),113, Funcoes.setMascara(vValores.elementAt(13).toString() != null ? vValores.elementAt(13).toString() : "","##.###.###/####-##"));

			imp.say(imp.pRow()+1,0, imp.comprimido());			  
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow(),  6, vValores.elementAt(14).toString().trim());
			imp.say(imp.pRow(), 66, vValores.elementAt(16).toString().trim());
			imp.say(imp.pRow(),102, vValores.elementAt(17).toString().trim());
			imp.say(imp.pRow(),119, vValores.elementAt(18).toString());			  
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());			
			imp.say(imp.pRow(),  6, vValores.elementAt(19).toString());
			imp.say(imp.pRow(), 23, vValores.elementAt(20).toString());
			imp.say(imp.pRow(), 46, vValores.elementAt(21).toString());
			imp.say(imp.pRow(), 97, vValores.elementAt(22).toString());
			imp.say(imp.pRow(),120, vValores.elementAt(23).toString());			  
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());			  
			  
			sMatObs = Funcoes.strToStrArray(sMensAdic,5);
			
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow(),  2, sMatObs[0]);
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow(),  2, sMatObs[1]);
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow(),  2, sMatObs[2]);
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow(),  2, sMatObs[3]);
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow(),  2, sMatObs[4]);			  
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			  			      
			imp.setPrc(0,0);
			
		} catch (Exception e) {
			e.printStackTrace();
			Funcoes.mensagemErro(null,"Erro ao imprimir totais!" + e.getMessage());
		} finally {  		  
			sMatObs = null;
		}
	}
}