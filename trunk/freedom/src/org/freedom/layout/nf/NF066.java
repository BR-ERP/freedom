/**
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: layout <BR>
 * Classe: @(#)NFIswara.java <BR>
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
 * Layout da nota fiscal para a empresa Iswara Ltda.
 */

package org.freedom.layout.nf;
import java.math.BigDecimal;
import java.util.Vector;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;
import org.freedom.layout.componentes.Layout;

public class NF066 extends Layout {
    
	private NF nf = null;
	
	public boolean imprimir( NF nf, ImprimeOS imp ) {

		boolean retorno = super.imprimir( nf, imp );
		boolean complementar = false;
		boolean bFat = true;
		boolean bNat = true;
		boolean bjatem = false;
		boolean bvlriss = true;
		final int MAXLINE = 55; // Ultima linha para impress�o de itens
		final int MAXPROD = 27; // Numero de �tens impressos
		int iNumNota = 0;
		int iItImp = 0;
		int iProdImp = 0;
		int iContaFrete = 0;
		int iLinPag = imp.verifLinPag( "NF" );
		int sizeObs = 0;
		int indexObs = 0;
		int indexSigla = 0;
		int indexServ = 0;
		String sCodfisc = null;
		String sSigla = null;
		String sTemp = null;
		String sDescFisc = "";
		String sObsVenda = "";
		String[] sValsCli = new String[ 4 ];
		String[] sNat = new String[ 2 ];
		String[] sVencs = new String[ 9 ];
		String[] sVals = new String[ 9 ];
		String[] sDuplics = new String[ 9 ];
		Vector<?> vObsVenda = new Vector<Object>();
		Vector<String> vClfisc = new Vector<String>();
		Vector<String> vSigla = new Vector<String>();
		Vector<?> vDescServ = new Vector<Object>();
		Vector<Object[]> vServico = new Vector<Object[]>();
		Vector<String> vMens = new Vector<String>();

		try {

			// alterando espa�amento vertical.
			imp.setaEspVert( ImprimeOS.EPSON_8PP );
			
			if ( cab.next() ) {
				iNumNota = cab.getInt(NF.C_DOC);
		        vMens = Funcoes.strToVectorSilabas(cab.getString(NF.C_MENSAGENS),60);
			}

			complementar = "CO".equals( cab.getString( NF.C_TIPOMOV ) );			
			
			for ( int i = 0; i < 9; i++ ) {
				if ( bFat ) {
					if ( parc.next() ) {
						sDuplics[ i ] = Funcoes.strZero( String.valueOf( iNumNota ), 6 ) + " / " + parc.getInt( NF.C_NPARCITREC );
						sVencs[ i ] = ( parc.getDate( NF.C_DTVENCTO ) != null ? Funcoes.dateToStrDate( parc.getDate( NF.C_DTVENCTO ) ) : "" );
						sVals[ i ] = Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( parc.getBigDecimal( NF.C_VLRPARC ) ) );
					}
					else {
						bFat = false;
						sDuplics[ i ] = "";
						sVencs[ i ] = "";
						sVals[ i ] = "";
					}
				}
				else {
					bFat = false;
					sVencs[ i ] = "";
					sVals[ i ] = "";
				}
			}

			imp.limpaPags();

			vClfisc.addElement( "" );

			while ( itens.next() ) {

				if ( bNat ) {
					sNat[ 0 ] = Funcoes.copy( itens.getString( NF.C_DESCNAT ).trim(), 32 );
					sNat[ 1 ] = Funcoes.setMascara( itens.getString( NF.C_CODNAT ), "#.###" );
					bNat = false;
				}

				if ( adic.next() ) {
					sValsCli[ 0 ] = !adic.getString( NF.C_CPFEMITAUX ).equals( "" ) ? adic.getString( NF.C_CPFEMITAUX ) : cab.getString( NF.C_CPFEMIT );
					sValsCli[ 1 ] = !adic.getString( NF.C_NOMEEMITAUX ).equals( "" ) ? adic.getString( NF.C_NOMEEMITAUX ) : cab.getString( NF.C_RAZEMIT );
					sValsCli[ 2 ] = !adic.getString( NF.C_CIDEMITAUX ).equals( "" ) ? adic.getString( NF.C_CIDEMITAUX ) : cab.getString( NF.C_CIDEMIT );
					sValsCli[ 3 ] = !adic.getString( NF.C_UFEMITAUX ).equals( "" ) ? adic.getString( NF.C_UFEMITAUX ) : cab.getString( NF.C_UFEMIT );
				}
				else {
					sValsCli[ 0 ] = cab.getString( NF.C_CPFEMIT );
					sValsCli[ 1 ] = cab.getString( NF.C_RAZEMIT );
					sValsCli[ 2 ] = cab.getString( NF.C_CIDEMIT );
					sValsCli[ 3 ] = cab.getString( NF.C_UFEMIT );
				}

				if ( imp.pRow() == 0 ) {

					// Imprime cabe�alho da nota

					imp.pulaLinha( 1, imp.comprimido() );					
					imp.say( 0, imp.normal() + imp.comprimido() + imp.expandido() );
					imp.say( 76, Funcoes.strZero( String.valueOf( iNumNota ), 6 ) );
					imp.pulaLinha( 2, imp.comprimido() );
					
					if ( nf.getTipoNF() == NF.TPNF_ENTRADA ) {
						imp.say( 120, "X" );
					}
					else {
						imp.say( 105, "X" );
					}

					imp.pulaLinha( 7, imp.comprimido() );
					imp.say( 4, sNat[ 0 ] );
					imp.say( 52, sNat[ 1 ] );
					imp.pulaLinha( 3, imp.comprimido() );
					imp.say( 4, sValsCli[ 1 ] );
					imp.say( 101, !sValsCli[ 0 ].equals( "" ) ? Funcoes.setMascara( sValsCli[ 0 ], "###.###.###-##" ) : Funcoes.setMascara( cab.getString( NF.C_CNPJEMIT ), "##.###.###/####-##" ) );
					imp.say( 146, ( cab.getDate( NF.C_DTEMITPED ) != null ? Funcoes.dateToStrDate( cab.getDate( NF.C_DTEMITPED ) ) : "" ) );
					imp.pulaLinha( 2, imp.comprimido() );
					imp.say( 4, Funcoes.copy( cab.getString( NF.C_ENDEMIT ), 0, 50 ).trim() + ", " + Funcoes.copy( cab.getString( NF.C_NUMEMIT ), 0, 6 ).trim() + (cab.getString( NF.C_COMPLEMIT )==null?"":" - ") + Funcoes.copy( cab.getString( NF.C_COMPLEMIT ), 0, 9 ).trim() );
					imp.say( 84, Funcoes.copy( cab.getString( NF.C_BAIREMIT ), 0, 23 ) );
					imp.say( 124, Funcoes.setMascara( cab.getString( NF.C_CEPEMIT ), "#####-###" ) );

					if ( !itens.getString( NF.C_IMPDTSAIDA ).equals( "N" ) ) {
						imp.say( 146, ( cab.getDate( NF.C_DTSAIDA ) != null ? Funcoes.dateToStrDate( cab.getDate( NF.C_DTSAIDA ) ) : "" ) );
					}

					imp.pulaLinha( 2, imp.comprimido() );
					imp.say( 4, sValsCli[ 2 ] );
					imp.say( 68, ( !cab.getString( NF.C_DDDEMIT ).equals( "" ) ? "(" + cab.getString( NF.C_DDDEMIT ) + ")" : "" ) + ( !cab.getString( NF.C_FONEEMIT ).equals( "" ) ? Funcoes.setMascara( cab.getString( NF.C_FONEEMIT ).trim(), "####-####" ) : "" ).trim() );
					imp.say( 95, sValsCli[ 3 ] );
					imp.say( 101, !cab.getString( NF.C_RGEMIT ).equals( "" ) ? cab.getString( NF.C_RGEMIT ) : cab.getString( NF.C_INSCEMIT ) );

					// Fim do cabe�alho					

					// Imprime dados da fatura

					imp.pulaLinha( 3, imp.comprimido() );
					imp.say( 4, sDuplics[ 0 ] );
					imp.say( 20, sVals[ 0 ] );
					imp.say( 39, sVencs[ 0 ] );
										
					imp.say( 53, sDuplics[ 1 ] );
					imp.say( 68, sVals[ 1 ] );
					imp.say( 85, sVencs[ 1 ] );
					
					imp.say( 110, sDuplics[ 2 ] );
					imp.say( 126, sVals[ 2 ] );
					imp.say( 145, sVencs[ 2 ] );
					
					imp.pulaLinha( 3, imp.comprimido() );

					// Fim dos dados da fatura
				}

				// Monta a menssagem fiscal ...

				sTemp = itens.getString( NF.C_DESCFISC ).trim();
				if ( sDescFisc.indexOf( sTemp ) == -1 ) {
					sDescFisc += sTemp;
				}	
				sTemp = itens.getString( NF.C_DESCFISC2 ).trim();
				if ( sDescFisc.indexOf( sTemp ) == -1 ) {
					sDescFisc += sTemp;
				}

				// Fim da menssagem fiscal ...

				// Defini��o da sigla para a classifica��o fiscal

				sCodfisc = itens.getString( NF.C_CODFISC );

				if ( !sCodfisc.equals( "" ) ) {
					for ( int i = 0; i < vClfisc.size(); i++ ) {
						if ( vClfisc.elementAt( i ) != null ) {
							if ( sCodfisc.equals( vClfisc.elementAt( i ) ) ) {
								bjatem = true;
								sSigla = String.valueOf( (char) ( 64 + i ) );
							}
							else {
								bjatem = false;
							}
						}
					}
					if ( !bjatem ) {
						vClfisc.addElement( sCodfisc );
						sSigla = String.valueOf( (char) ( 63 + vClfisc.size() ) );
						vSigla.addElement( sCodfisc );
					}
				}

				// Fim da classifica��o fiscal

				// Imprime os dados do item no corpo da nota

				if ( !"S".equals( itens.getString( NF.C_TIPOPROD ) ) ) {
					imp.pulaLinha( 1, imp.comprimido() );
					
					if( complementar ) {
						imp.say( 17, Funcoes.copy( itens.getString( NF.C_OBSITPED ).trim(), 48 ) );
					}
					else {
						imp.say( 3, itens.getString( NF.C_REFPROD ) );
						imp.say( 13, Funcoes.copy( itens.getString( NF.C_DESCPROD ).trim(), 48 ) );
						imp.say( 69, sSigla );
						imp.say( 74, Funcoes.copy( itens.getString( NF.C_ORIGFISC ), 0, 1 ) + Funcoes.copy( itens.getString( NF.C_CODTRATTRIB ), 0, 2 ) );
						imp.say( 80, Funcoes.copy( itens.getString( NF.C_CODUNID ), 4 ) );						
						imp.say( 86, Funcoes.strDecimalToStrCurrency( 6, 2, String.valueOf( itens.getBigDecimal( NF.C_QTDITPED ) ) ) );
						imp.say( 91, itens.getBigDecimal( NF.C_PERCDESCITVENDA ).compareTo( new BigDecimal(0)) > 0 ? Funcoes.strDecimalToStrCurrency( 4, 1, String.valueOf( itens.getBigDecimal( NF.C_PERCDESCITVENDA )) ) + "%" : "" );
						imp.say( 110, Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( itens.getBigDecimal( NF.C_VLRPRODITPED ).divide( itens.getBigDecimal( NF.C_QTDITPED ), BigDecimal.ROUND_HALF_UP ) ) ));
						imp.say( 132, Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( itens.getBigDecimal( NF.C_VLRLIQITPED ) ) ) );
						imp.say( 144, ( itens.getBigDecimal( NF.C_PERCICMSITPED ).toBigInteger().intValue() ) + "%" );
						imp.say( 149, ( itens.getBigDecimal( NF.C_PERCIPIITPED ).toBigInteger().intValue() ) + "%" );
						imp.say( 152, Funcoes.strDecimalToStrCurrency( 6, 2, String.valueOf( itens.getBigDecimal(  NF.C_VLRIPIITPED ) ) ) );	
					}
					iProdImp++;
				}

				iItImp++;

				// Fim da impress�o do item

				if ( ( iItImp == itens.getInt( NF.C_CONTAITENS ) ) || ( iProdImp == MAXPROD ) || ( imp.pRow() == MAXLINE ) ) {

					if ( iItImp == itens.getInt( NF.C_CONTAITENS ) ) {

//						Imprime o desconto ...				        
//						imp.pulaLinha( 1, imp.comprimido() );
//						imp.say(  8, "Valor do desconto : " + Funcoes.strDecimalToStrCurrency( 9,2,String.valueOf(cab.getFloat(NF.C_VLRDESCITPED))));				    				
//						Imprime observa��o no corpo da nota ...
						
						imp.pulaLinha( 1, imp.comprimido() );
						
						for( int i=0; i < vMens.size(); i++ ) {
						    if( imp.pRow() < MAXLINE ) {
						        imp.pulaLinha( 1, imp.comprimido() );
								imp.say( 8, (String)vMens.elementAt(i));
						    }
						}
					}
					
					// Imprime totais

					if ( iContaFrete == 0 ) {
						frete.next();
						iContaFrete++;
					}

					if ( iItImp == itens.getInt( NF.C_CONTAITENS ) ) {
						
						imp.pulaLinha( MAXLINE - imp.pRow() , imp.comprimido() );
						imp.pulaLinha( 3, imp.comprimido() );
						imp.say( 6, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getBigDecimal( NF.C_VLRBASEICMSPED ) ) ) );
						imp.say( 36, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getBigDecimal( NF.C_VLRICMSPED ) ) ) );
												
						if(!complementar){
							imp.say( 131, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf(cab.getBigDecimal( NF.C_VLRPRODPED )) ));
						}
						
						imp.pulaLinha( 2, imp.comprimido() );
						
						if(!complementar){
							imp.say( 6, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( frete.getBigDecimal( NF.C_VLRFRETEPED ) ) ) );
							imp.say( 68, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getBigDecimal( NF.C_VLRADICPED ) ) ) );
							imp.say( 97, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getBigDecimal( NF.C_VLRIPIPED ) ) ) );
						}						
						
						if (nf.getTipoNF() == NF.TPNF_ENTRADA) {
							imp.say( 131, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getBigDecimal( NF.C_VLRLIQPED ).add( cab.getBigDecimal( NF.C_VLRADICPED )))));
						}
						else{
							if(!complementar) {
								imp.say( 131, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getBigDecimal( NF.C_VLRLIQPED ) )));
							}
							iItImp = 0;
						}
					}
					else {
						
						imp.pulaLinha( MAXLINE - imp.pRow() , imp.comprimido() );
						imp.pulaLinha( 3, imp.comprimido() );
						imp.say( 6, "********************" );
						imp.say( 36, "********************" );
						imp.say( 131, "********************" );
						imp.pulaLinha( 2, imp.comprimido() );
						imp.say( 6, "********************" );
						imp.say( 68, "********************" );
						imp.say( 97, "********************" );
						imp.say( 131, "********************" );
					}

					// Fim da impress�o dos totais

					// Imprime informa��es do frete

					imp.pulaLinha( 3, imp.comprimido() );
					
					if(!complementar) {
					
						imp.say( 4, frete.getString( NF.C_RAZTRANSP ) );
						imp.say( 99, "C".equals(frete.getString( NF.C_TIPOFRETE ) ) ? "1" : "2" );
						imp.say( 104, frete.getString( NF.C_PLACAFRETE ) );
						imp.say( 122, frete.getString( NF.C_UFFRETE ) );

						if ( "C".equals(frete.getString( NF.C_TIPOTRANSP ) ) ) {
							imp.say( 128, Funcoes.setMascara( cab.getString( NF.C_CNPJEMIT ), "##.###.###/####-##" ) );
						} 
						else {
							if ("".equals( frete.getString( NF.C_CNPJTRANSP ) )) {
								imp.say( 128, Funcoes.setMascara( frete.getString( NF.C_CPFTRANSP ), "###.###.###-##" ) );
							} 
							else {
								imp.say( 128, Funcoes.setMascara( frete.getString( NF.C_CNPJTRANSP ), "##.###.###/####-##" ) );
							}
						}

						imp.pulaLinha( 2, imp.comprimido() );
						imp.say( 4, (frete.getString( NF.C_ENDTRANSP )==null ? "" : frete.getString( NF.C_ENDTRANSP ).trim()) + ", " + frete.getInt( NF.C_NUMTRANSP ));
						imp.say( 86, frete.getString( NF.C_CIDTRANSP )==null ? "" : frete.getString( NF.C_CIDTRANSP ) );
						imp.say( 122, frete.getString( NF.C_UFTRANSP )==null ? "" : frete.getString( NF.C_UFTRANSP ) );

						if ( "C".equals(frete.getString( NF.C_TIPOTRANSP )) ) {
							imp.say( 128, cab.getString( NF.C_INSCEMIT ) );
						}
						else {
							imp.say( 128, frete.getString( NF.C_INSCTRANSP ) );
						}
						
						imp.pulaLinha( 2, imp.comprimido() );
					
						imp.say( 6, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( frete.getString( NF.C_QTDFRETE ) ) ) );
						imp.say( 22, Funcoes.copy( frete.getString( NF.C_ESPFRETE ), 27 ) );
						imp.say( 51, Funcoes.copy( frete.getString( NF.C_MARCAFRETE ), 22 ) );
						imp.say( 86, Funcoes.copy( frete.getString( NF.C_CONHECFRETEPED ), 20 ) );
						imp.say( 124, Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( frete.getBigDecimal( NF.C_PESOBRUTO ) ) ) );
						imp.say( 137, Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( frete.getBigDecimal( NF.C_PESOLIQ ) ) ) );
						imp.pulaLinha( 3, imp.comprimido() );
					
					}

					// Fim da impress�o do frete

					// Imprime observa��o e classifica��es fiscais

					if(complementar) {
						vObsVenda = Funcoes.strToVectorSilabas( sObsVenda, 40 );
						imp.pulaLinha( 5, imp.comprimido() );
					}
					else {
						vObsVenda = Funcoes.strToVectorSilabas( ( sDescFisc.length() > 0 ? sDescFisc + "\n" : "" ) + sObsVenda, 40 );
					}
					

					sizeObs = vSigla.size();

					for ( int i = 0; i < 7; i++ ) {
						if ( i < sizeObs ) {
							imp.say( 6, vSigla.elementAt( i ) );
							imp.pulaLinha( 1, imp.comprimido() );
						}
						imp.pulaLinha( 1, imp.comprimido() );
					}

					// Fim da observa��o

					// Imprime canhoto					
					imp.pulaLinha( iLinPag - imp.pRow() -6, imp.comprimido() );					
					imp.say( 0, imp.normal() + imp.comprimido() + imp.expandido() );
					imp.say( 10, Funcoes.strZero( String.valueOf( iNumNota ), 6 ) );										
					imp.pulaLinha( iLinPag - imp.pRow() + 2, imp.comprimido() );
					imp.setPrc( 0, 0 );
					imp.incPags();
				}
			}

			imp.fechaGravacao();
			retorno = true;

		} catch ( Exception err ) {
			Funcoes.mensagemErro( null, "Erro ao montar nota \n" + err.getMessage() );
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
}
