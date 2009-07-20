/**
 * @version 22/05/2006 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: layout <BR>
 * Classe:
 * @(#)NFIswara.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
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

public class NF035LX extends Layout {

	public boolean imprimir( NF nf, ImprimeOS imp ) {

		boolean retorno = super.imprimir( nf, imp );
		boolean bFat = true;
		boolean bNat = true;
		boolean bjatem = false;
		boolean bvlriss = true;
		final int MAXLINE = 43;
		final int MAXPROD = 12;
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
		imp.setImpEject( false );

		try {

			if ( cab.next() ) {
				iNumNota = cab.getInt( NF.C_DOC );
				sObsVenda = cab.getString( NF.C_OBSPED ).replace( "\n", "" );
			}

			for ( int i = 0; i < 9; i++ ) {
				if ( bFat ) {
					if ( parc.next() ) {
						sDuplics[ i ] = Funcoes.strZero( String.valueOf( iNumNota ), 6 ) + " / " + parc.getInt( NF.C_NPARCITREC );
						sVencs[ i ] = ( parc.getDate( NF.C_DTVENCTO ) != null ? Funcoes.dateToStrDate( parc.getDate( NF.C_DTVENCTO ) ) : "" );
						sVals[ i ] = Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( parc.getFloat( NF.C_VLRPARC ) ) );
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

					if ( nf.getTipoNF() == NF.TPNF_ENTRADA ) {
						imp.say( 104, "X" );
					}
					else {
						imp.say( 105, "X" );
					}

					imp.say( 145, Funcoes.strZero( String.valueOf( iNumNota ), 6 ) );

					imp.pulaLinha( 4, imp.comprimido() );
					imp.say( 0, sNat[ 0 ] );
					imp.say( 51, sNat[ 1 ] );
					imp.pulaLinha( 3, imp.comprimido() );
					imp.say( 2, sValsCli[ 1 ] );
					imp.say( 103, !sValsCli[ 0 ].equals( "" ) ? Funcoes.setMascara( sValsCli[ 0 ], "###.###.###-##" ) : Funcoes.setMascara( cab.getString( NF.C_CNPJEMIT ), "##.###.###/####-##" ) );
					imp.say( 140, ( cab.getDate( NF.C_DTEMITPED ) != null ? Funcoes.dateToStrDate( cab.getDate( NF.C_DTEMITPED ) ) : "" ) );
					imp.pulaLinha( 2, imp.comprimido() );
					imp.say( 0, Funcoes.copy( cab.getString( NF.C_ENDEMIT ), 0, 50 ).trim() + ", " + Funcoes.copy( cab.getString( NF.C_NUMEMIT ), 0, 6 ).trim() + " - " + Funcoes.copy( cab.getString( NF.C_COMPLEMIT ), 0, 9 ).trim() );
					imp.say( 81, Funcoes.copy( cab.getString( NF.C_BAIREMIT ), 0, 23 ) );
					imp.say( 120, Funcoes.setMascara( cab.getString( NF.C_CEPEMIT ), "#####-###" ) );

					if ( !itens.getString( NF.C_IMPDTSAIDA ).equals( "N" ) ) {
						imp.say( 140, ( cab.getDate( NF.C_DTSAIDA ) != null ? Funcoes.dateToStrDate( cab.getDate( NF.C_DTSAIDA ) ) : "" ) );
					}

					imp.pulaLinha( 2, imp.comprimido() );
					imp.say( 0, sValsCli[ 2 ] );
					imp.say( 65, ( !cab.getString( NF.C_DDDEMIT ).equals( "" ) ? "(" + cab.getString( NF.C_DDDEMIT ) + ")" : "" ) + ( !cab.getString( NF.C_FONEEMIT ).equals( "" ) ? Funcoes.setMascara( cab.getString( NF.C_FONEEMIT ).trim(), "####-####" ) : "" ).trim() );
					imp.say( 95, sValsCli[ 3 ] );
					imp.say( 105, !cab.getString( NF.C_RGEMIT ).equals( "" ) ? cab.getString( NF.C_RGEMIT ) : cab.getString( NF.C_INSCEMIT ) );

					// Fim do cabe�alho

					// Imprime dados da fatura

					imp.pulaLinha( 3, imp.comprimido() );
					imp.say( 0, sDuplics[ 0 ] );
					imp.say( 22, sVals[ 0 ] );
					imp.say( 40, sVencs[ 0 ] );
					imp.say( 57, sDuplics[ 1 ] );
					imp.say( 76, sVals[ 1 ] );
					imp.say( 94, sVencs[ 1 ] );
					imp.say( 106, sDuplics[ 2 ] );
					imp.say( 121, sVals[ 2 ] );
					imp.say( 143, sVencs[ 2 ] );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, sDuplics[ 3 ] );
					imp.say( 22, sVals[ 3 ] );
					imp.say( 40, sVencs[ 3 ] );
					imp.say( 57, sDuplics[ 4 ] );
					imp.say( 76, sVals[ 4 ] );
					imp.say( 94, sVencs[ 4 ] );
					imp.say( 106, sDuplics[ 5 ] );
					imp.say( 121, sVals[ 5 ] );
					imp.say( 143, sVencs[ 5 ] );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, sDuplics[ 6 ] );
					imp.say( 22, sVals[ 6 ] );
					imp.say( 40, sVencs[ 6 ] );
					imp.say( 57, sDuplics[ 7 ] );
					imp.say( 76, sVals[ 7 ] );
					imp.say( 95, sVencs[ 7 ] );
					imp.say( 106, sDuplics[ 8 ] );
					imp.say( 121, sVals[ 8 ] );
					imp.say( 143, sVencs[ 8 ] );
					imp.pulaLinha( 2, imp.comprimido() );

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
						vSigla.addElement( sSigla + " = " + sCodfisc );
					}
				}

				// Fim da classifica��o fiscal

				// Imprime os dados do item no corpo da nota

				if ( !"S".equals( itens.getString( NF.C_TIPOPROD ) ) ) {

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, itens.getString( NF.C_REFPROD ) );
					imp.say( 17, Funcoes.copy( itens.getString( NF.C_DESCPROD ).trim(), 48 ) );
					imp.say( 77, sSigla );
					imp.say( 83, Funcoes.copy( itens.getString( NF.C_ORIGFISC ), 0, 1 ) + Funcoes.copy( itens.getString( NF.C_CODTRATTRIB ), 0, 2 ) );
					imp.say( 91, Funcoes.copy( itens.getString( NF.C_CODUNID ), 4 ) );
					imp.say( 99, Funcoes.strDecimalToStrCurrency( 8, 2, String.valueOf( itens.getFloat( NF.C_QTDITPED ) ) ) );
					imp.say( 112, Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( itens.getFloat( NF.C_VLRPRODITPED ) / itens.getFloat( NF.C_QTDITPED ) ) ) );
					imp.say( 126, Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( itens.getFloat( NF.C_VLRPRODITPED ) ) ) );
					imp.say( 138, ( (int) itens.getFloat( NF.C_PERCICMSITPED ) ) + "%" );
					imp.say( 144, ( (int) itens.getFloat( NF.C_PERCIPIITPED ) ) + "%" );
					imp.say( 148, Funcoes.strDecimalToStrCurrency( 6, 2, String.valueOf( itens.getFloat( NF.C_VLRIPIITPED ) ) ) );
					iProdImp++;
				}
				else {
					// guarda servi�o;
					vServico.addElement( new Object[] { 
							"".equals( itens.getString( NF.C_OBSITPED ).trim() ) ? itens.getString( NF.C_DESCPROD ) : itens.getString( NF.C_OBSITPED ).trim(), 
							new BigDecimal( itens.getFloat( NF.C_QTDITPED ) ), 
							new BigDecimal( itens.getFloat( NF.C_VLRLIQITPED ) ),
							new BigDecimal( itens.getFloat( NF.C_VLRISSITPED ) ) } );
				}

				iItImp++;

				// Fim da impress�o do item

				if ( ( iItImp == itens.getInt( NF.C_CONTAITENS ) ) || ( iProdImp == MAXPROD ) || ( imp.pRow() == MAXLINE ) ) {

					if ( iItImp == itens.getInt( NF.C_CONTAITENS ) ) {
						if ( cab.getFloat( NF.C_VLRDESCITPED ) > 0.0f ) {
							// Imprime o desconto
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 8, "Valor do desconto : " + Funcoes.strDecimalToStrCurrency( 9, 2, String.valueOf( cab.getFloat( NF.C_VLRDESCITPED ) ) ) );
						}
						if ( vServico.size() > 0 && indexServ < vServico.size() ) {
							imp.pulaLinha( 33 - imp.pRow(), imp.comprimido() );
						}
						else {
							imp.pulaLinha( MAXLINE - imp.pRow(), imp.comprimido() );
						}
					}


					// Imprime servi�o
					
					//System.out.println("Tam vServico" +vServico.size());
					if ( vServico.size() > 0 ) {
						 
						for ( int row = 0; row < 11; row++ ) {

							imp.pulaLinha( 1, imp.comprimido() );
							
							if ( indexServ < vServico.size() ) {
								
								vDescServ = Funcoes.strToVectorSilabas( ((String) vServico.get( indexServ )[ 0 ]).trim(), 80 );
								
								for ( int i = 0; i < vDescServ.size(); i++ ) {
									
									imp.say( 4, (String) vDescServ.elementAt( i ) );
									if ( i == 0 ) {
										imp.say( 97, Funcoes.strDecimalToStrCurrency( 8, 2, String.valueOf( (BigDecimal) vServico.get( indexServ )[ 1 ] ) ) );
										imp.say( 113, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( (BigDecimal) vServico.get( indexServ )[ 2 ] ) ) );
									}
									
									if ( bvlriss && row == 6 ) {
										imp.say( 133, Funcoes.strDecimalToStrCurrency( 15, 2, String.valueOf( cab.getFloat( NF.C_VLRISS ) ) ) );
										bvlriss = false;
									}

									if ( i < ( vDescServ.size() - 1 ) ) {
										imp.pulaLinha( 1, imp.comprimido() );
									}
									if ( (i>0) && (i==vDescServ.size()-1)) {  
									  row+=vDescServ.size()-1;
									}
								}
								
								indexServ++;
							}
							
							if ( bvlriss && row == 6 ) {
								imp.say( 133, Funcoes.strDecimalToStrCurrency( 15, 2, String.valueOf( cab.getFloat( NF.C_VLRISS ) ) ) );
								bvlriss = false;
							}
						}

						imp.say( 133, Funcoes.strDecimalToStrCurrency( 15, 2, String.valueOf( cab.getFloat( NF.C_BASEISS ) ) ) );
					}

					// Imprime totais

					if ( iContaFrete == 0 ) {
						frete.next();
						iContaFrete++;
					}

					if ( iItImp == itens.getInt( NF.C_CONTAITENS ) ) {
						imp.pulaLinha( 46 - imp.pRow(), imp.comprimido() );
						imp.say( 0, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getFloat( NF.C_VLRBASEICMSPED ) ) ) );
						imp.say( 32, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getFloat( NF.C_VLRICMSPED ) ) ) );
						imp.say( 120, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getFloat( NF.C_VLRPRODPED ) - cab.getFloat( NF.C_BASEISS ) ) ) );
						imp.pulaLinha( 2, imp.comprimido() );
						imp.say( 0, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( frete.getFloat( NF.C_VLRFRETEPED ) ) ) );
						imp.say( 58, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getFloat( NF.C_VLRADICPED ) ) ) );
						imp.say( 87, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getFloat( NF.C_VLRIPIPED ) ) ) );
						
						
						if (nf.getTipoNF() == NF.TPNF_ENTRADA) {
							imp.say( 120, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getFloat( NF.C_VLRLIQPED ) + 
									cab.getFloat( NF.C_VLRADICPED ))));
						}
						else{
							imp.say( 120, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( cab.getFloat( NF.C_VLRLIQPED ) )));
						}
						iItImp = 0;
					}
					else {
						imp.pulaLinha( 47 - imp.pRow(), imp.comprimido() );
						imp.say( 0, "********************" );
						imp.say( 32, "********************" );
						imp.say( 120, "********************" );
						imp.pulaLinha( 2, imp.comprimido() );
						imp.say( 0, "********************" );
						imp.say( 58, "********************" );
						imp.say( 87, "********************" );
						imp.say( 120, "********************" );
					}

					// Fim da impress�o dos totais

					// Imprime informa��es do frete

					imp.pulaLinha( 3, imp.comprimido() );
					imp.say( 0, frete.getString( NF.C_RAZTRANSP ) );
					imp.say( 102, "C".equals(frete.getString( NF.C_TIPOFRETE ) ) ? "1" : "2" );
					imp.say( 107, frete.getString( NF.C_PLACAFRETE ) );
					imp.say( 126, frete.getString( NF.C_UFFRETE ) );

					if ( "C".equals(frete.getString( NF.C_TIPOTRANSP ) ) ) {
						imp.say( 133, Funcoes.setMascara( cab.getString( NF.C_CNPJEMIT ), "##.###.###/####-##" ) );
					} else {
						if ("".equals( frete.getString( NF.C_CNPJTRANSP ) )) {
							imp.say( 133, Funcoes.setMascara( frete.getString( NF.C_CPFTRANSP ), "###.###.###-##" ) );
						} else {
							imp.say( 133, Funcoes.setMascara( frete.getString( NF.C_CNPJTRANSP ), "##.###.###/####-##" ) );
						}
					}

					imp.pulaLinha( 2, imp.comprimido() );
					imp.say( 0, frete.getString( NF.C_ENDTRANSP ).trim() + ", " + frete.getInt( NF.C_NUMTRANSP ) );
					imp.say( 88, frete.getString( NF.C_CIDTRANSP ) );
					imp.say( 126, frete.getString( NF.C_UFTRANSP ) );

					if ( frete.getString( NF.C_TIPOTRANSP ).equals( "C" ) ) {
						imp.say( 133, cab.getString( NF.C_INSCEMIT ) );
					}
					else {
						imp.say( 133, frete.getString( NF.C_INSCTRANSP ) );
					}

					imp.pulaLinha( 2, imp.comprimido() );
					imp.say( 0, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( frete.getString( NF.C_QTDFRETE ) ) ) );
					imp.say( 25, Funcoes.copy( frete.getString( NF.C_ESPFRETE ), 27 ) );
					imp.say( 56, Funcoes.copy( frete.getString( NF.C_MARCAFRETE ), 22 ) );
					imp.say( 76, Funcoes.copy( frete.getString( NF.C_CONHECFRETEPED ), 20 ) );
					imp.say( 120, Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( frete.getFloat( NF.C_PESOBRUTO ) ) ) );
					imp.say( 140, Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( frete.getFloat( NF.C_PESOLIQ ) ) ) );
					imp.pulaLinha( 2, imp.comprimido() );

					// Fim da impress�o do frete

					// Imprime observa��o e classifica��es fiscais

					vObsVenda = Funcoes.strToVectorSilabas( ( sDescFisc.length() > 0 ? sDescFisc + "\n" : "" ) + sObsVenda, 40 );

					sizeObs = vSigla.size();
					sizeObs = vObsVenda.size() > sizeObs ? vObsVenda.size() : sizeObs;

					int aux = 0;
					for ( int i = 0; i < 7; i++ ) {
						if ( aux < sizeObs ) {
							imp.pulaLinha( 1, imp.comprimido() );
							if ( vSigla.size() > 0 && indexSigla < vSigla.size() ) {
								imp.say( 2, vSigla.elementAt( indexSigla++ ) );
							}
							if ( vObsVenda.size() > 0 && indexObs < vObsVenda.size() ) {
								imp.say( 20, Funcoes.copy( (String) vObsVenda.elementAt( indexObs++ ), 40 ) );
							}
						}
						else {
							imp.pulaLinha( 1, imp.comprimido() );
						}
					}

					// Fim da observa��o

					// Imprime canhoto

					imp.pulaLinha( 4, imp.comprimido() );
					imp.say( 145, Funcoes.strZero( String.valueOf( iNumNota ), 6 ) );

					imp.pulaLinha( iLinPag - imp.pRow(), imp.comprimido() );
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
