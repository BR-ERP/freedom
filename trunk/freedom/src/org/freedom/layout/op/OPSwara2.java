/**
 * @version 23/04/2004<BR>
 * @author Setpoint Inform�tica Ltda. - Anderson Sanchez/Alex Rodrigues <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: leiautes <BR>
 *         Classe:
 * @(#)OPSwara.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Layout de Ordem de produ��o personalizada para empresa ISwara...
 */

package org.freedom.layout.op;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.layout.componentes.LeiauteGR;
import org.freedom.telas.Aplicativo;

import com.lowagie.text.pdf.Barcode128;

public class OPSwara2 extends LeiauteGR {

	private static final long serialVersionUID = 1L;

	private Connection con = null;

	private Font fnTitulo = new Font( "Times New Roman", Font.BOLD, 13 );

	private Font fnInstrucoes = new Font( "Arial", Font.PLAIN, 6 );
	
	private Font fnArial8 = new Font( "Arial", Font.PLAIN, 8 );

	private Font fnArial9 = new Font( "Arial", Font.PLAIN, 9 );

	private Font fnArial9N = new Font( "Arial", Font.BOLD, 9 );

	private Vector<?> vParamOP = new Vector<Object>();

	private final int iPosMax = 740;

	private int iY = 140;

	private Vector<Vector<String>> vItens = new Vector<Vector<String>>();

	private Vector<String> vItem = new Vector<String>();

	private int iCodOP = 0;

	private int iSeqOP = 0;

	private String sDescProd = "";

	private String sLote = "";

	private String sQtd = "";

	private String sCodUnid = "";

	private Double dbQtd = new Double( 1 );

	private String sDtFabrica = "";

	private String sDtValidade = "";

	public void montaG() {

		impRaz( false );
		montaRel();
	}

	public void setParam( Vector<?> vParam ) {

		vParamOP = vParam;
	}

	@SuppressWarnings("unchecked")
	private void montaRel() {

		setMargemPdf( 5, 5 );

		iCodOP = Integer.parseInt( vParamOP.elementAt( 0 ).toString() );
		iSeqOP = Integer.parseInt( vParamOP.elementAt( 1 ).toString() );

		try {

			StringBuilder sSQL = new StringBuilder();

			sSQL.append( "SELECT ITOP.CODOP,ITOP.SEQITOP,OP.DTEMITOP,OP.CODPROD," );
			sSQL.append( "(SELECT PROD2.DESCPROD FROM EQPRODUTO PROD2 " );
			sSQL.append( "WHERE PROD2.CODPROD=OP.CODPROD AND PROD2.CODEMP=OP.CODEMPPD " );
			sSQL.append( "AND PROD2.CODFILIAL=OP.CODFILIALPD)," );
			sSQL.append( "EST.DESCEST,EST.QTDEST,OP.DTFABROP,OP.QTDPREVPRODOP,DTVALIDPDOP," );
			sSQL.append( "OP.DTINS,ITOP.CODPROD,PROD.DESCPROD,UNID.DESCUNID,ITOP.CODLOTE," );
			sSQL.append( "ITOP.QTDITOP,OP.QTDPREVPRODOP,ITOP.CODFASE,OP.CODLOTE,PROD.CODUNID," );
			sSQL.append( "(SELECT PROD2.CODUNID FROM EQPRODUTO PROD2 " );
			sSQL.append( "WHERE PROD2.CODPROD=OP.CODPROD AND PROD2.CODEMP=OP.CODEMPPD " );
			sSQL.append( "AND PROD2.CODFILIAL=OP.CODFILIALPD) " );
			sSQL.append( "FROM PPESTRUTURA EST,PPOP OP, PPITOP ITOP, EQUNIDADE UNID, EQPRODUTO PROD " );
			sSQL.append( "WHERE EST.CODPROD=OP.CODPROD AND EST.SEQEST=OP.SEQEST AND ");
			sSQL.append( "EST.CODEMP=OP.CODEMP AND EST.CODFILIAL=OP.CODFILIAL AND ");
			sSQL.append( "ITOP.CODOP=OP.CODOP AND ITOP.SEQOP=OP.SEQOP AND " );
			sSQL.append( "UNID.CODUNID=PROD.CODUNID AND PROD.CODPROD = ITOP.CODPROD AND OP.CODOP=? AND " );
			sSQL.append( "OP.SEQOP=? AND OP.CODEMP=? AND OP.CODFILIAL=? " );
			sSQL.append( "ORDER BY ITOP.CODPROD " );

			PreparedStatement ps = con.prepareStatement( sSQL.toString() );

			ps.setInt( 1, iCodOP );
			ps.setInt( 2, iSeqOP );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "PPOP" ) );

			ResultSet rs = ps.executeQuery();

			while ( rs.next() ) {
				vItem = new Vector<String>();
				vItem.addElement( ( rs.getString( 12 ) != null ? rs.getString( 12 ) : "" ) ); // C�digo
				vItem.addElement( ( rs.getString( 13 ) != null ? rs.getString( 13 ) : "" ) ); // Descri��o
				vItem.addElement( ( rs.getString( 16 ) != null ? Funcoes.strDecimalToStrCurrency( 3, rs.getString( 16 ) ) : "0" ) ); // Quantidade
				vItem.addElement( ( rs.getString( 20 ) != null ? rs.getString( 20 ) : "" ) ); // C�digo da Unidade
				vItem.addElement( ( rs.getString( 15 ) != null ? rs.getString( 15 ) : "" ) ); // Lote
				vItem.addElement( ( rs.getString( 18 ) != null ? rs.getString( 18 ) : "0" ) ); // Fase
				vItens.addElement( (Vector<String>) vItem.clone() );
			}

			sDescProd = ( rs.getString( 5 ) != null ? rs.getString( 5 ).trim() : "" );
			dbQtd = ( rs.getString( 9 ) != null ? new Double( Funcoes.strDecimalToBigDecimal( 3, rs.getString( 9 ) ).doubleValue() ) : dbQtd );
			sQtd = ( (int) dbQtd.doubleValue() ) + "";
			sCodUnid = ( rs.getString( 21 ) != null ? rs.getString( 21 ).trim() : "" );
			sDtFabrica = ( rs.getDate( 8 ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( 8 ) ) : "" );
			sDtValidade = ( rs.getDate( 10 ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( 10 ) ) : "" );
			sLote = ( rs.getString( 19 ) != null ? rs.getString( 19 ).trim() : "" );

			montaCabEmp();
			montaCab();

			sSQL.delete( 0, sSQL.length() );
			sSQL.append( "SELECT OPF.SEQOF, OPF.CODFASE,F.DESCFASE,F.TIPOFASE,OPF.TEMPOOF,OPF.CODRECP," );
			sSQL.append( "REC.DESCRECP,EF.INSTRUCOES " );
			sSQL.append( "FROM PPOP OP, PPOPFASE OPF, PPFASE F, PPRECURSO REC,PPESTRUFASE EF " );
			sSQL.append( "WHERE F.CODFASE=OPF.CODFASE AND F.CODEMP=OPF.CODEMPFS AND F.CODFILIAL=OPF.CODFILIALFS " );
			sSQL.append( "AND OP.CODEMP=OPF.CODEMP AND OP.CODFILIAL=OPF.CODFILIAL AND OP.CODOP=OPF.CODOP " );
			sSQL.append( "AND OP.SEQOP=OPF.SEQOP AND REC.CODRECP=OPF.CODRECP AND REC.CODEMP=OPF.CODEMPRP " );
			sSQL.append( "AND REC.CODFILIAL=OPF.CODFILIALRP AND OPF.CODOP=? AND OPF.SEQOP=? " );
			sSQL.append( "AND OPF.CODEMP=? AND OPF.CODFILIAL=? AND EF.CODEMP=OPF.CODEMP " );
			sSQL.append( "AND EF.CODFILIAL=OPF.CODFILIAL AND EF.CODPROD=OP.CODPROD AND EF.SEQEST=OP.SEQEST " );
			sSQL.append( "AND EF.SEQEF=OPF.SEQOF " );
			sSQL.append( "ORDER BY OPF.SEQOF" );

			PreparedStatement psFases = con.prepareStatement( sSQL.toString() );

			psFases.setInt( 1, iCodOP );
			psFases.setInt( 2, iSeqOP );
			psFases.setInt( 3, Aplicativo.iCodEmp );
			psFases.setInt( 4, ListaCampos.getMasterFilial( "PPOPFASE" ) );

			ResultSet rsFases = psFases.executeQuery();

			montaFases( rsFases );

			rs.close();
			ps.close();

			terminaOP( true );
			finaliza();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao montar o cabe�alho do relat�rio!!!\n" + err.getMessage() );
			err.printStackTrace();
		}
	}

	private void impFaseEx( ResultSet rsFases ) {

		String sCod = "";
		String sQtd = "";
		String sUnid = "";
		String sLote = "";

		try {
				
			String sFase = rsFases.getString( 3 ) != null ? rsFases.getString( 3 ).trim() : "";
			String sRecurso = rsFases.getString( 7 ) != null ? rsFases.getString( 7 ).trim() : "";
			String sInstrucoes = rsFases.getString( "INSTRUCOES" ) != null ? rsFases.getString( "INSTRUCOES" ).trim() : "";
			Double dbQtdEstr = new Double( rsFases.getFloat( 5 ) / 60 );
			int iSeqOf = rsFases.getInt( 1 );
			int iCodFaseF = rsFases.getInt( 2 );
			int iCodFaseI = 0;
			int iYIni = iY;
			int iInst = 0;
			
			iY += 3;			

			if ( iY + 60 > iPosMax ) {
				terminaOP( false );
				iYIni = iY;
			}

			setFonte( fnTitulo );
			drawTexto( "FASE: " + iSeqOf + " - " + sFase, 10, iY );

			if ( sInstrucoes.length() > 0 ) {
				
				// impress�o das instru��es de uso.
				
				setFonte( fnArial9N );
				drawTexto( "Instru��o de preparo", 260, iY - 5 );
				iInst = impLabelSilabas( sInstrucoes, 7, 260, 270, iY + 2, fnInstrucoes );
			}

			iY += 12;

			setFonte( fnArial9N );
			drawTexto( "Recurso:", 10, iY );
			setFonte( fnArial9 );
			drawTexto( sRecurso, 60, iY );
			
			iY += 12;
			
			setFonte( fnArial9N );
			drawTexto( "Tempo estimado(min.):", 10, iY );
			setFonte( fnArial9 );
			drawTexto( String.valueOf( dbQtdEstr.floatValue() * dbQtd.floatValue() ), 120, iY );
			
			iY += 12;
			
			setFonte( fnArial9N );
			drawTexto( "Responsavel Pesagem : ", 10, iY );
			drawLinha( 125, iY, 255, iY );

			iY += 12;

			if ( iInst > iY ) {
				
				/* verifica o tamanho da instru��o 
				 * para definir onde por a linha que separa as fases.
				 */ 				
				
				iY = iInst + 2;
			}

			if ( iY + 10 > iPosMax ) {
				iY += 10;
				drawRetangulo( 5, iYIni - 15, 5, iY - iYIni, AL_CDIR );
				terminaOP( false );
				iYIni = iY;
			}
			
			int impCab = 0;
			
			for ( int i = 0; vItens.size() > i; i++ ) {

				sCod = vItens.elementAt( i ).elementAt( 0 ).toString();
				sQtd = vItens.elementAt( i ).elementAt( 2 ).toString();
				sUnid = vItens.elementAt( i ).elementAt( 3 ).toString();
				sLote = vItens.elementAt( i ).elementAt( 4 ).toString();
				iCodFaseI = Integer.parseInt( vItens.elementAt( i ).elementAt( 5 ).toString() );

				if ( iCodFaseI == iCodFaseF ) {
					
					if ( iY + 20 > iPosMax ) {
						iY += 10;
						drawRetangulo( 5, iYIni - 15, 5, iY - iYIni, AL_CDIR );
						terminaOP( false );
						iYIni = iY;

						drawLinha( 5, iY, 5, 0, AL_CDIR );
						
						iY += 12;

						setFonte( fnArial9N );
						drawTexto( "C�d.", 30, iY );
						drawTexto( "C�d.Barras", 100, iY );
						drawTexto( "Qtd.", 340, iY );
						drawTexto( "Unid", 380, iY );
						drawTexto( "Lote", 420, iY );
						drawTexto( "Qtd. Pesada", 490, iY );
						drawLinha( 5, iY + 5, 5, 0, AL_CDIR );

						iY += 18;

						setFonte( fnArial9 );
					}
					
					if ( impCab == 0 ) {
						
						// imprime cabe�alho dos itens...
						
						drawLinha( 5, iY, 5, 0, AL_CDIR );
						
						iY += 12;

						setFonte( fnArial9N );
						drawTexto( "C�d.", 30, iY );
						drawTexto( "C�d.Barras", 100, iY );
						drawTexto( "Qtd.", 340, iY );
						drawTexto( "Unid", 380, iY );
						drawTexto( "Lote", 420, iY );
						drawTexto( "Qtd. Pesada", 490, iY );
						drawLinha( 5, iY + 5, 5, 0, AL_CDIR );

						iY += 18;

						setFonte( fnArial9 );
						
						impCab++;
					}
					
					// Cria a imagem do C�digo de Barras.

					Barcode128 b = new Barcode128();
					String sBarCode = "P#" + iSeqOf + "#" + iCodOP + "#" + iSeqOP + "#" + sCod.trim() + "#" + sLote.trim() + "#" + sQtd.trim();
					sBarCode = sBarCode.replace( '/', '_' );
					System.out.println( sBarCode );
					b.setCode( sBarCode );
					Image image = b.createAwtImage( Color.BLACK, Color.WHITE );
					ImageIcon icon = new ImageIcon( image );
					
					drawTexto( sCod, 30, iY ); // C�digo
					drawImagem( icon, 100, iY - 9, 200, 15 );// C�digo de Barras
					drawTexto( Funcoes.alinhaDir( sQtd, 15 ), 320, iY );// Quantidade
					drawTexto( sUnid, 380, iY );// Unidade
					drawTexto( sLote, 420, iY );// Lote
					drawLinha( 490, iY, 560, iY );

					iY += 18;
				}
			}

			iY += 10;
			drawRetangulo( 5, iYIni - 15, 5, iY - iYIni, AL_CDIR );

		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			sCod = null;
			sQtd = null;
			sUnid = null;
			sLote = null;
		}
	}

	private void impFaseCq( ResultSet rsFases ) {
	
		try {		
			
			String sFase = rsFases.getString( 3 ) != null ? rsFases.getString( 3 ).trim() : "";
			String sRecurso = rsFases.getString( 7 ) != null ? rsFases.getString( 7 ).trim() : "";
			String sInstrucoes = rsFases.getString( "INSTRUCOES" ) != null ? rsFases.getString( "INSTRUCOES" ).trim() : "";
			Double dbQtdEstr = new Double( rsFases.getFloat( 5 ) / 60 );
			int iSeqOf = rsFases.getInt( 1 );
			int iYIni = iY + 5;
			int iInst = 0;
							
			if ( iY + 60 > iPosMax ) {
				terminaOP( false );
				iYIni = iY;
			}
			
			iY += 5;
	
			if ( sInstrucoes.length() > 0 ) {
				
				// impress�o das instru��es de uso.
				
				setFonte( fnArial9N );
				drawTexto( "Instru��o de preparo", 260, iY - 5 );
				iInst = impLabelSilabas( sInstrucoes, 7, 260, 270, iY + 2, fnInstrucoes );
			}

			setFonte( fnTitulo );
			drawTexto( "FASE: " + iSeqOf, 10, iY );
	
			iY += 12;
	
			drawTexto( sFase.toUpperCase(), 10, iY );
	
			iY += 12;

			setFonte( fnArial9N );
			drawTexto( "Recurso:", 10, iY );
			setFonte( fnArial9 );
			drawTexto( sRecurso, 60, iY );
			
			iY += 12;
			
			setFonte( fnArial9N );
			drawTexto( "Tempo estimado(min.):", 10, iY );
			setFonte( fnArial9 );
			drawTexto( String.valueOf( dbQtdEstr.floatValue() * dbQtd.floatValue() ), 120, iY );
			
			iY += 12;
			
			setFonte( fnArial9N );
			drawTexto( "Responsavel Pesagem : ", 10, iY );
			drawLinha( 125, iY, 255, iY );

			iY += 12;

			if ( iInst > iY ) {
				
				/* verifica o tamanho da instru��o 
				 * para definir onde por a linha que separa as fases.
				 */ 				
				
				iY = iInst + 2;
			}
			
			if ( iY + 100 > iPosMax ) {
				iY += 40;
				drawRetangulo( 5, iYIni - 15, 5, iY - iYIni, AL_CDIR );
				terminaOP( false );
				iYIni = iY;
			}
				
			drawLinha( 5, iY, 5, 0, AL_CDIR );
			drawLinha( 283, iY, 283, iY + 100 );
			
			int iYP = iY;
			int iYL = iY;
	
			// imprime produ��o...
						
			iYP += 10;	
			
			setFonte( fnArial9 );
			drawTexto( "Produ��o/Semi Elaborado", 18, iYP );
			drawTexto( "Produ��o/Produto Acabado", 154, iYP );

			iYP += 5;
			
			drawRetangulo( 10, iYP, 130, 80 );
			drawRetangulo( 146, iYP, 130, 80 );			
			
			iYP += 12;

			drawTexto( "Amostra retirada para an�lise", 15, iYP );
			drawTexto( "Amostra retirada para an�lise", 149, iYP );
			iYP += 20;
			setFonte( fnArial9 );
			drawTexto( "Quantidade:", 15, iYP );
			drawLinha( 68, iYP, 135, iYP );
			drawTexto( "Quantidade:", 151, iYP );
			drawLinha( 204, iYP, 271, iYP );
			iYP += 15;
			drawTexto( "Nome:", 15, iYP );
			drawLinha( 68, iYP, 135, iYP );
			drawTexto( "Nome:", 151, iYP );
			drawLinha( 204, iYP, 271, iYP );
			iYP += 15;
			drawTexto( "Data:", 15, iYP );
			drawLinha( 68, iYP, 135, iYP );
			drawTexto( "Data:", 151, iYP );
			drawLinha( 204, iYP, 271, iYP );
	
			// imprime laboratorio...
			
			iYL += 10;	
			
			setFonte( fnArial9 );
			drawTexto( "Laborat�rio/Semi Elaborado", 297, iYL );
			drawTexto( "Laborat�rio/Produto Acabado", 433, iYL );

			iYL += 5;
			
			drawRetangulo( 290, iYL, 130, 80 );
			drawRetangulo( 426, iYL, 130, 80 );			
			
			iYL += 12;

			drawTexto( "Amostra retirada para an�lise", 295, iYL );
			drawTexto( "Amostra retirada para an�lise", 431, iYL );
			iYL += 20;
			setFonte( fnArial9 );
			drawTexto( "Resultado:", 295, iYL );
			drawLinha( 347, iYL, 415, iYL );
			drawTexto( "Resultado:", 431, iYL );
			drawLinha( 484, iYL, 551, iYL );
			iYL += 15;
			drawTexto( "Nome:", 295, iYL );
			drawLinha( 347, iYL, 415, iYL );
			drawTexto( "Nome:", 431, iYL );
			drawLinha( 484, iYL, 551, iYL );
			iYL += 15;
			drawTexto( "Data:", 295, iYL );
			drawLinha( 347, iYL, 415, iYL );
			drawTexto( "Data:", 431, iYL );
			drawLinha( 484, iYL, 551, iYL );

			iY = iYL;
			
			iY += 40;
			
			drawRetangulo( 5, iYIni - 15, 5, iY - iYIni, AL_CDIR );
	
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void impFaseEB( ResultSet rsFases ) {

		try {

			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuilder sSQL = new StringBuilder();
			String sCodProd = null;
			String sDesc = null;
			String sQtd = null;
			String sUnid = null;
			String sLote = null;
			String sBarCode = null;
			String sSeqOP = null;
			String sFase = rsFases.getString( 3 ) != null ? rsFases.getString( 3 ).trim() : "";
			String sInstrucoes = rsFases.getString( "INSTRUCOES" ) != null ? rsFases.getString( "INSTRUCOES" ).trim() : "";
			String sRecurso = rsFases.getString( 7 ) != null ? rsFases.getString( 7 ).trim() : "";
			Double dbQtdEstr = new Double( rsFases.getFloat( 5 ) / 60 );
			Vector<Vector<String>> vItensEB = null;
			Vector<String> vColunasEB = null;
			int iSeqOf = rsFases.getInt( 1 );
			int iCodFaseF = rsFases.getInt( 2 );
			int iCodFaseI = 0;
			int iYIni = 0;
			int iInst = 0;
			

			sSQL.append( "SELECT O.SEQOP, O.CODPROD, P.DESCPROD , O.CODLOTE, O.QTDPREVPRODOP, P.CODUNID, F.CODFASE " );
			sSQL.append( "FROM PPOP O, EQPRODUTO P, PPOPFASE F " );
			sSQL.append( "WHERE O.CODEMPPD=P.CODEMP AND O.CODFILIALPD=P.CODFILIAL AND O.CODPROD=P.CODPROD " );
			sSQL.append( "AND O.CODEMP=F.CODEMP AND O.CODFILIAL=F.CODFILIAL AND O.CODOP=F.CODOP AND O.SEQOP=F.SEQOP " );
			sSQL.append( "AND O.CODEMP=? AND O.CODFILIAL=? AND O.CODOPM=? AND O.SEQOPM=? " );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setInt( 3, iCodOP );
			ps.setInt( 4, iSeqOP );
			rs = ps.executeQuery();

			vItensEB = new Vector<Vector<String>>();

			while ( rs.next() ) {

				vColunasEB = new Vector<String>();
				vColunasEB.addElement( ( rs.getString( "CODPROD" ) != null ? rs.getString( "CODPROD" ) : "" ) ); // C�digo
				vColunasEB.addElement( ( rs.getString( "DESCPROD" ) != null ? rs.getString( "DESCPROD" ) : "" ) ); // Descri��o
				vColunasEB.addElement( ( rs.getString( "QTDPREVPRODOP" ) != null ? Funcoes.strDecimalToStrCurrency( 0, rs.getString( "QTDPREVPRODOP" ) ) : "0" ) ); // Quantidade
				vColunasEB.addElement( ( rs.getString( "CODUNID" ) != null ? rs.getString( "CODUNID" ) : "" ) ); // Unidade
				vColunasEB.addElement( ( rs.getString( "CODLOTE" ) != null ? rs.getString( "CODLOTE" ) : "" ) ); // Lote
				vColunasEB.addElement( ( rs.getString( "SEQOP" ) != null ? rs.getString( "SEQOP" ) : "0" ) ); // Sequencia da OP
				vColunasEB.addElement( ( rs.getString( "CODFASE" ) != null ? rs.getString( "CODFASE" ) : "0" ) ); // C�digo da Fase
				vItensEB.addElement( vColunasEB );
			}

			rs.close();
			ps.close();
			
			if ( iY + 80 > iPosMax ) {
				terminaOP( false );
				iYIni = iY;
			}

			iY += 5;

			iYIni = iY;
			
			if ( sInstrucoes.length() > 0 ) {
				
				// impress�o das instru��es de uso.
				
				setFonte( fnArial9N );
				drawTexto( "Instru��o de preparo", 325, iY - 5 );
				iInst = impLabelSilabas( sInstrucoes, 7, 325, 200, iY + 2, fnInstrucoes );
			}

			setFonte( fnTitulo );
			drawTexto( "FASE: " + iSeqOf, 10, iY );
	
			iY += 14;
	
			drawTexto( sFase.toUpperCase(), 10, iY );
	
			iY += 12;

			setFonte( fnArial9N );
			drawTexto( "Recurso:", 10, iY );
			setFonte( fnArial9 );
			drawTexto( sRecurso, 60, iY );
			
			iY += 12;
			
			setFonte( fnArial9N );
			drawTexto( "Tempo estimado(min.):", 10, iY );
			setFonte( fnArial9 );
			drawTexto( String.valueOf( dbQtdEstr.floatValue() * dbQtd.floatValue() ), 120, iY );
			setFonte( fnArial9N );
			drawTexto( "Densidade:", 180, iY );
			drawLinha( 240, iY, 315, iY );
			
			iY += 12;
			
			setFonte( fnArial9N );
			drawTexto( "Responsavel : ", 10, iY );
			drawLinha( 80, iY, 175, iY );
			drawTexto( "Rendimento Te�rico:", 180, iY );
			drawLinha( 280, iY, 315, iY );

			iY += 12;
			
			setFonte( fnArial9N );
			drawTexto( "data : ", 10, iY );
			drawLinha( 40, iY, 175, iY );
			drawTexto( "Rendimento Pr�tico:", 180, iY );
			drawLinha( 280, iY, 315, iY );

			iY += 12;

			if ( iInst > iY ) {
				
				/* verifica o tamanho da instru��o 
				 * para definir onde por a linha que separa as fases.
				 */ 				
				
				iY = iInst + 2;
			}
			
			int impCab = 0;

			for ( int i = 0; vItensEB.size() > i; i++ ) {

				vColunasEB = vItensEB.elementAt( i );
				sCodProd = vColunasEB.elementAt( 0 ).toString();
				sDesc = vColunasEB.elementAt( 1 ).toString();
				sQtd = vColunasEB.elementAt( 2 ).toString();
				sUnid = vColunasEB.elementAt( 3 ).toString();
				sLote = vColunasEB.elementAt( 4 ).toString();
				sSeqOP = vColunasEB.elementAt( 5 ).toString();
				iCodFaseI = Integer.parseInt( vColunasEB.elementAt( 6 ).toString() );

				if ( iCodFaseI == iCodFaseF ) {
					
					if ( impCab == 0 ) {
						
						if ( iY + 46 > iPosMax ) {
							iY += 15;
							drawRetangulo( 5, iYIni - 15, 5, iY - iYIni, AL_CDIR );
							terminaOP( false );
							iYIni = iY;
						}

						drawLinha( 5, iY, 5, 0, AL_CDIR );

						iY += 10;

						setFonte( fnArial9N );
						drawTexto( "Embalagens a serem Descarregadas", 5, iY, 150, AL_CEN );

						iY += 4;
						
						drawLinha( 5, iY, 5, 0, AL_CDIR );

						iY += 12;

						drawTexto( "C�d.", 10, iY );
						drawTexto( "Tipo de Embalagem", 40, iY );
						drawTexto( "C�digo de Barras", 180, iY );
						drawTexto( "Lote", 385, iY );
						drawTexto( "Qtd.", 450, iY );
						drawTexto( "Emb.", 480, iY );

						iY += 4;
						
						drawLinha( 5, iY, 5, 0, AL_CDIR );

						iY += 14;
						
						setFonte( fnArial9 );
						
						impCab++;
					}
					
					if ( iY + 20 > iPosMax ) {
						iY += 15;
						drawRetangulo( 5, iYIni - 15, 5, iY - iYIni, AL_CDIR );
						terminaOP( false );
						iYIni = iY;
						
						iY += 2;

						setFonte( fnArial9N );
						drawTexto( "Embalagens a serem Descarregadas", 5, iY, 150, AL_CEN );
						
						iY += 4;
						
						drawLinha( 5, iY, 5, 0, AL_CDIR );

						iY += 12;

						drawTexto( "C�d.", 10, iY );
						drawTexto( "Tipo de Embalagem", 40, iY );
						drawTexto( "C�digo de Barras", 180, iY );
						drawTexto( "Lote", 385, iY );
						drawTexto( "Qtd.", 450, iY );
						drawTexto( "Emb.", 480, iY );
						
						iY += 4;
						
						drawLinha( 5, iY, 5, 0, AL_CDIR );

						iY += 14;
						
						setFonte( fnArial9 );
					}

					drawTexto( sCodProd, 10, iY ); // Codigo
					drawTexto( sDesc.substring( 0, 20 ), 40, iY ); // Descri��o

					Barcode128 barra = new Barcode128();
					sBarCode = "D#"/* +sSeqOF */+ "#" + iCodOP + "#" + sSeqOP + "###";
					sBarCode = sBarCode.replace( '/', '_' );
					barra.setCode( sBarCode );
					System.out.println( sBarCode );
					Image image = barra.createAwtImage( Color.BLACK, Color.WHITE );
					ImageIcon icon = new ImageIcon( image );

					drawImagem( icon, 163, iY - 9, 200, 15 );

					drawTexto( sLote, 370, iY );// Lote
					drawTexto( Funcoes.alinhaDir( sQtd, 15 ) + "   " + sUnid, 415, iY );// Quantidade
					drawLinha( 480, iY, 50, 0, AL_BCEN );
					iY += 20;
				}
			}			

			iY += 15;

			drawRetangulo( 5, iYIni - 15, 5, iY - iYIni, AL_CDIR );

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( null, "Erro carregando itens!\n" + e.getMessage() );
		} 
	}

	private void montaFases( ResultSet rsFases ) {
	
		try {
	
			while ( rsFases.next() ) {
	
				if ( rsFases.getString( 4 ).equals( "EX" ) ) {
					impFaseEx( rsFases );
				}
				else if ( rsFases.getString( 4 ).equals( "CQ" ) ) {
					impFaseCq( rsFases );
				}
				else if ( rsFases.getString( 4 ).equals( "EB" ) ) {
					impFaseEB( rsFases );
				}
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}

	private int impLabelSilabas( String sTexto, int iSalto, int iMargem, int iLargura, int iY, Font fonte ) {
	
		double iPixels = getFontMetrics( fonte ).stringWidth( sTexto );
		double iNLinhas = iPixels / iLargura;
		int iNCaracteres = Funcoes.tiraChar( sTexto, "\n" ).length();
		int iNCaracPorLinha = (int) ( iNCaracteres / iNLinhas );
	
		Vector<?> vTextoSilabas = Funcoes.strToVectorSilabas( sTexto, iNCaracPorLinha );
	
		for ( int i = 0; vTextoSilabas.size() > i; i++ ) {
	
			setFonte( fonte );
	
			drawTexto( vTextoSilabas.elementAt( i ).toString(), iMargem, iY );
			iY += iSalto;
		}
	
		return iY;
	}

	private void montaCabEmp() {

		double dAltLogo = 50;

		try {

			String sSQL = "SELECT NOMEEMP,CNPJEMP,FONEEMP,FAXEMP,FOTOEMP,ENDEMP,NUMEMP FROM SGEMPRESA WHERE CODEMP=?";
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ResultSet rs = ps.executeQuery();
			int iX = 0;

			if ( rs.next() ) {

				setFonte( fnTitulo );

				int iLargLogo = 0;
				byte[] bVals = new byte[ 650000 ];

				Blob bVal = rs.getBlob( "FotoEmp" );

				if ( bVal != null ) {

					try {
						bVal.getBinaryStream().read( bVals, 0, bVals.length );
					} catch ( IOException err ) {
						Funcoes.mensagemErro( null, "Erro ao recuperar dados!\n" + err.getMessage() );
						err.printStackTrace();
					}

					ImageIcon img = new ImageIcon( bVals );
					double dFatProp = dAltLogo / img.getIconHeight();
					drawImagem( img, 2, 12, (int) ( img.getIconWidth() * dFatProp ), (int) dAltLogo );
					iLargLogo = (int) ( img.getIconWidth() * dFatProp );
				}

				sNomeEmp = rs.getString( "NomeEmp" ).trim();
				sCGCEmp = Funcoes.setMascara( rs.getString( "CnpjEmp" ), "##.###.###/####-##" );
				sEndEmp = rs.getString( "EndEmp" ).trim() + ", " + rs.getInt( "NumEmp" );

				iX = 5 + iLargLogo;

				drawTexto( sNomeEmp, iX, 15 );

				setFonte( fnArial8 );

				drawTexto( "C.N.P.J.:   " + sCGCEmp, iX, 30 );
				drawTexto( "Telefone.:   " + Funcoes.setMascara( rs.getString( "FoneEmp" ).trim(), "####-####" ), iX, 40 );
				drawTexto( "Fax.:   " + Funcoes.setMascara( rs.getString( "FaxEmp" ), "####-####" ), iX, 50 );
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			drawTexto( "Etiqueta Conta Prova", 287, 9 );
			drawRetangulo( 240, 12, 167, 48 );

			String sNome = "";
			String sCargo = "";
			String sID = "";

			try {

				ps = con.prepareStatement( "SELECT NOMERESP,IDENTPROFRESP,CARGORESP FROM SGPREFERE5 WHERE CODEMP=? AND CODFILIAL=?" );

				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE5" ) );

				rs = ps.executeQuery();

				if ( rs.next() ) {

				    sNome = rs.getString( "NOMERESP" ) != null ? rs.getString( "NOMERESP" ).trim() : "";
				    sCargo = rs.getString( "CARGORESP" ) != null ? rs.getString( "CARGORESP" ).trim() : "";
				    sID = rs.getString( "IDENTPROFRESP" ) != null ? rs.getString( "IDENTPROFRESP" ).trim() : "";
				}

				rs.close();
				ps.close();

				if ( !con.getAutoCommit() ) {
					con.commit();
				}
			} catch ( SQLException e ) {
				e.printStackTrace();
			}

			setFonte( fnArial8 );
			drawLinha( 415, 28, 560, 28 );
			drawTexto( sNome, 415, 38 );
			drawTexto( sCargo, 415, 48 );
			drawTexto( sID, 415, 58 );

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao montar o cabe�alho da empresa!!!\n" + err.getMessage() );
		}
	}

	private void montaCab() {

		try {

			setBordaRel();
			setFonte( fnTitulo );
			drawLinha( 0, 65, 0, 0, AL_BDIR );
			drawRetangulo( 5, 70, 5, 50, AL_CDIR );

			drawTexto( "ORDEM DE PRODU��O", 0, 85, 150, AL_CEN );
			setFonte( fnArial9N );

			drawTexto( "O.P. n�mero:", 10, 100 );
			drawTexto( "Produto:", 110, 100 );
			drawTexto( "Lote:", 420, 100 );
			drawTexto( "Qtd.:", 10, 112 );
			drawTexto( "Data de fabrica��o:", 110, 112 );
			drawTexto( "Data de validade:", 270, 112 );
			drawTexto( "Emiss�o:", 420, 112 );

			setFonte( fnArial9 );

			drawTexto( String.valueOf( iCodOP ), 70, 100 ); // C�digo da OP
			drawTexto( sDescProd, 153, 100 ); // Descri��o do produto a ser fabricado
			drawTexto( sLote, 475, 100 );// Lote do produto
			drawTexto( sQtd + " - " + sCodUnid, 40, 112 ); // qtd. a fabricar
			drawTexto( sDtFabrica, 200, 112 ); // Data de fabrica��o
			drawTexto( sDtValidade, 350, 112 ); // Data de validade
			drawTexto( Funcoes.dateToStrDate( Calendar.getInstance().getTime() ), 475, 112 );// Data

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao montar dados do cliente!!!\n" + err.getMessage() );
			err.printStackTrace();
		}
	}
	
	private void terminaOP( boolean obs ) {

		if ( obs ) {
	        setFonte( fnArial9N );
			iY = iY + 20;
			drawTexto( "OBS.:___________________________________________________________________________________________", 20, iY );
			iY = iY + 15;
			drawTexto( "Nome:__________________________________________   Data:__________________________________________", 20, iY );
		}
		//else {
	    termPagina();		
	    iY = 140;
	    montaCabEmp();
	    montaCab();
		//}
	}

	public void setConexao( Connection cn ) {

		con = cn;
	}
}
