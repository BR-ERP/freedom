/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRVendasFisico.java <BR>
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
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRVendasFisico extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JCheckBoxPad cbVendaCanc = new JCheckBoxPad( "Mostrar Canceladas", "S", "N" );

	private JRadioGroup<?, ?> rgTipo = null;

	private JRadioGroup<?, ?> rgOrdem = null;

	private JRadioGroup<?, ?> rgFaturados = null;

	private JRadioGroup<?, ?> rgFinanceiro = null;

	private ListaCampos lcVend = new ListaCampos( this );

	public FRVendasFisico() {

		setTitulo( "Fechamento Fisico de Vendas" );
		setAtribos( 80, 80, 325, 400 );

		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );

		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();

		vLabs.addElement( "Grafico" );
		vLabs.addElement( "Texto" );
		vVals.addElement( "G" );
		vVals.addElement( "T" );
		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgTipo.setVlrString( "T" );

		Vector<String> vLabs1 = new Vector<String>();
		Vector<String> vVals1 = new Vector<String>();

		vLabs1.addElement( "C�digo" );
		vLabs1.addElement( "Descri��o" );
		vVals1.addElement( "C" );
		vVals1.addElement( "D" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, vLabs1, vVals1 );
		rgOrdem.setVlrString( "D" );

		Vector<String> vLabs2 = new Vector<String>();
		Vector<String> vVals2 = new Vector<String>();

		vLabs2.addElement( "Faturado" );
		vLabs2.addElement( "N�o Faturado" );
		vLabs2.addElement( "Ambos" );
		vVals2.addElement( "S" );
		vVals2.addElement( "N" );
		vVals2.addElement( "A" );
		rgFaturados = new JRadioGroup<String, String>( 3, 1, vLabs2, vVals2 );
		rgFaturados.setVlrString( "S" );

		Vector<String> vLabs3 = new Vector<String>();
		Vector<String> vVals3 = new Vector<String>();

		vLabs3.addElement( "Financeiro" );
		vLabs3.addElement( "N�o Finaceiro" );
		vLabs3.addElement( "Ambos" );
		vVals3.addElement( "S" );
		vVals3.addElement( "N" );
		vVals3.addElement( "A" );
		rgFinanceiro = new JRadioGroup<String, String>( 3, 1, vLabs3, vVals3 );
		rgFinanceiro.setVlrString( "S" );

		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.comiss.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "VD" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );
		txtCodVend.setNomeCampo( "CodVend" );
		txtCodVend.setFK( true );
		txtCodVend.setTabelaExterna( lcVend );

		adic( new JLabelPad( "Periodo:" ), 7, 5, 100, 20 );
		adic( lbLinha, 7, 25, 273, 40 );
		adic( new JLabelPad( "De:" ), 15, 35, 30, 20 );
		adic( txtDataini, 45, 35, 95, 20 );
		adic( new JLabelPad( "At�:" ), 145, 35, 30, 20 );
		adic( txtDatafim, 175, 35, 95, 20 );
		adic( new JLabelPad( "C�d.comiss." ), 7, 70, 210, 20 );
		adic( txtCodVend, 7, 90, 70, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 80, 70, 210, 20 );
		adic( txtDescVend, 80, 90, 200, 20 );
		adic( new JLabelPad( "Ordenar por:" ), 7, 115, 80, 20 );
		adic( rgOrdem, 7, 135, 273, 30 );
		adic( rgTipo, 7, 170, 273, 30 );
		adic( rgFaturados, 7, 205, 125, 70 );
		adic( rgFinanceiro, 156, 205, 125, 70 );
		adic( cbVendaCanc, 7, 280, 200, 20 );
	}

	public void imprimir( boolean bVisualizar ) {
	
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
    	
		/** @version 24/01/2008 <BR>
    	 * "Inicializa��o de vari�veis com valor branco" - Implementa��o realizada por 
    	 * @author Pedro Henrique (pedro.enrique.contech@gmail.com) <BR>
    	 * Revisado e "commitado" por Anderson Sanchez (Setpoint Inform�tica Ltda)    	
    	 */
		
		String sWhere1 = "";
		String sWhere2 = "";
		String sWhere3 = "";
		
		try {

			if ( "S".equals( rgFaturados.getVlrString() ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
				sCab.append( "FATURADO" );
			}
			else if ( "N".equals( rgFaturados.getVlrString() ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
				if ( sCab.length() > 0 ) {
					sCab.append( " - " );
				}
				sCab.append( "NAO FATURADO" );
			}
			else if ( "A".equals( rgFaturados.getVlrString() ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";
			}

			if ( "S".equals( rgFinanceiro.getVlrString() ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
				if ( sCab.length() > 0 ) {
					sCab.append( " - " );
				}
				sCab.append( "FINANCEIRO" );
			}
			else if ( "N".equals( rgFinanceiro.getVlrString() ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
				if ( sCab.length() > 0 ) {
					sCab.append( " - " );
				}
				sCab.append( "NAO FINANCEIRO" );
			}
			else if ( "A".equals( rgFinanceiro.getVlrString() ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";
			}
		 
			if ( "N".equals( cbVendaCanc.getVlrString() ) ) {
				sWhere3 = " AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' ";
			}
			
			if ( txtCodVend.getText().trim().length() > 0 ) {
				sWhere.append( " AND V.CODVEND=" );
				sWhere.append( txtCodVend.getText().trim() );
				sWhere.append( " AND V.CODEMPVD=" );
				sWhere.append( Aplicativo.iCodEmp );
				sWhere.append( " AND V.CODFILIALVD=" );
				sWhere.append( lcVend.getCodFilial() );
				if ( sCab.length() > 0 ) {
					sCab.append( "\n" );
				}
				sCab.append( "Representante : " );
				sCab.append( txtCodVend.getVlrString() );
				sCab.append( " - " );
				sCab.append( txtDescVend.getText().trim() );
			}		
	
			sSQL.append( "SELECT SUBSTRING(P.CODGRUP FROM 1 FOR 4) as GRUPO," );
			sSQL.append( "P.CODPROD,P.REFPROD,P.DESCPROD,G.DESCGRUP,P.CUSTOMPMPROD,IT.CODITVENDA," ); 
			sSQL.append( "SUM(IT.QTDITVENDA) QTDITVENDA,SUM(IT.VLRDESCITVENDA) VLRDESCITVENDA,SUM(IT.VLRLIQITVENDA) VLRLIQITVENDA " ); 
			sSQL.append( "FROM VDVENDA V,VDITVENDA IT,EQPRODUTO P,EQGRUPO G,EQTIPOMOV TM " );
			sSQL.append( "WHERE V.DTEMITVENDA BETWEEN ? AND ? AND IT.CODEMP=? AND IT.CODFILIAL=? " ); 
			sSQL.append( "AND V.CODEMP=IT.CODEMP AND V.CODFILIAL=IT.CODFILIAL AND V.CODVENDA=IT.CODVENDA AND V.TIPOVENDA=IT.TIPOVENDA " );
			sSQL.append( "AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV " );
			sSQL.append( "AND P.CODEMP=IT.CODEMPPD AND P.CODFILIAL=IT.CODFILIALPD AND P.CODPROD=IT.CODPROD " );
			sSQL.append( "AND G.CODEMP=P.CODEMPGP AND G.CODFILIAL=P.CODFILIALGP AND G.CODGRUP=P.CODGRUP " );
			sSQL.append( sWhere );
			sSQL.append( sWhere1 );
			sSQL.append( sWhere2 );
			sSQL.append( sWhere3 );
			sSQL.append( "AND IT.QTDITVENDA > 0 " );
			sSQL.append( "AND (V.FLAG IN " + AplicativoPD.carregaFiltro( con, org.freedom.telas.Aplicativo.iCodEmp ) + ") " ); 
			sSQL.append( "AND TM.TIPOMOV IN ('VD','VE','PV','VT','SE') " );
			sSQL.append( "GROUP BY 1,P.CODPROD,P.REFPROD,P.DESCPROD,G.DESCGRUP,IT.CODITVENDA, P.CUSTOMPMPROD " );
			sSQL.append( "ORDER BY 1," );
			sSQL.append( "C".equals( rgOrdem.getVlrString() ) ? comRef() ? "P.REFPROD" : "P.CODPROD" : "P.DESCPROD" );
	
			ps = con.prepareStatement( sSQL.toString() );
			ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "VDITVENDA" ) );
			rs = ps.executeQuery();
			
			if ( "T".equals( rgTipo.getVlrString() ) ) {
				imprimirTexto( bVisualizar, rs, Funcoes.strToVectorSilabas( sCab.toString(), 130 ) );
			}
			else if ( "G".equals( rgTipo.getVlrString() ) ) {
				imprimirGrafico( bVisualizar, rs, sCab.toString() );
			}
			
			rs.close();
			ps.close();
	
			con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta ao relat�rio de vendas fisico!" + err.getMessage() );
		} finally {
			System.gc();
		}
	}

	private void imprimirTexto( final boolean bVisualizar, final ResultSet rs, final Vector<?> cab ) {
		
		String sCodGrup = null;
		String sGrup = null;
		final String sLinhaFina = Funcoes.replicate( "-", 133 );
		final String sLinhaDupla = Funcoes.replicate( "=", 133 );
		ImprimeOS imp = null;
		int linPag = 0;
	
		try {
	
			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relat�rio Fisco de Vendas" );
			imp.addSubTitulo( "RELATORIO FISICO DE VENDAS   -   PERIODO DE :" + txtDataini.getVlrString() + " ATE: " + txtDatafim.getVlrString() );
			for ( int i=0; i < cab.size(); i++) {
				imp.addSubTitulo( (String) cab.elementAt( i ) );
			}
			
		 /* 1  GRUPO
		    2  CODPROD
		    3  REFPROD 
		    4  DESCPROD 
		    5  DESCGRUP 
		    6  CUSTOMPMPROD 
		    7  CODITVENDA," ); 
			8  SUM(IT.QTDITVENDA) QTDITVENDA
			9  SUM(IT.VLRDESCITVENDA) VLRDESCITVENDA
			10 SUM(IT.VLRLIQITVENDA) VLRLIQITVENDA */

			BigDecimal bQtdVenda = new BigDecimal( "0" );
			BigDecimal bDescVenda = new BigDecimal( "0" );
			BigDecimal bVlrUnitVenda = new BigDecimal( "0" );
			BigDecimal bVlrTotVenda = new BigDecimal( "0" );			
			BigDecimal bVlrUnitCusto = new BigDecimal( "0" );
			BigDecimal bVlrTotCusto = new BigDecimal( "0" );
			BigDecimal bVlrUnitLucro = new BigDecimal( "0" );
			BigDecimal bVlrTotLucro = new BigDecimal( "0" );
			BigDecimal bMargem = new BigDecimal( "0" );
			
			BigDecimal bTotalVendaGrupo = new BigDecimal( "0" );
			BigDecimal bTotalCustoGrupo = new BigDecimal( "0" );
			BigDecimal bTotalDescGrupo = new BigDecimal( "0" );
			BigDecimal bTotalLucroGrupo = new BigDecimal( "0" );
			BigDecimal bMargemGrupo = new BigDecimal( "0" );

			BigDecimal bTotalVenda = new BigDecimal( "0" );
			BigDecimal bTotalCusto = new BigDecimal( "0" );
			BigDecimal bTotalDesc = new BigDecimal( "0" );
			BigDecimal bTotalLucro = new BigDecimal( "0" );
			BigDecimal bMargemTotal = new BigDecimal( "0" );
				
			while ( rs.next() ) {
				
				if ( imp.pRow() >= ( linPag - 1 ) ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + sLinhaFina + "+" );
					imp.incPags();
					imp.eject();
				}
				
				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, true );
					imp.pulaLinha( 0, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 15, "|" );
					imp.say( 46, "|" );
					imp.say( 60, "V E N D A" );
					imp.say( 79, "|" );
					imp.say( 87, "C U S T O" );
					imp.say( 103, "|" );
					imp.say( 113, "LUCRO ESTIMADO" );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 3, "Refer�ncia" );
					imp.say( 15, "|" );
					imp.say( 16, "Descri��o" );
					imp.say( 46, "|" );
					imp.say( 49, "Qtd." );
					imp.say( 56, "Vlr. Unit." );
					imp.say( 68, "Vlr. Total" );
					imp.say( 79, "|" );
					imp.say( 81, "Vlr. Unit. " );
					imp.say( 92, "Vlr. Total" );
					imp.say( 103, "|" );
					imp.say( 106, "P/ Unid. " );
					imp.say( 116, "Vlr. Total " );
					imp.say( 128, "Margem" );
					imp.say( 135, "|" );
				}
				
				imp.pulaLinha( 1, imp.comprimido() );
				
				if ( sCodGrup == null || ! sCodGrup.equals( rs.getString( 1 ) ) ) {

					if ( sCodGrup != null && ! sCodGrup.equals( rs.getString( 1 ) ) ) {
												
						if ( bTotalVendaGrupo.floatValue() > 0 ) {
							bMargemGrupo =new BigDecimal( bTotalLucroGrupo.floatValue() / ( bTotalVendaGrupo.floatValue() / 100 ) ).setScale( 2, BigDecimal.ROUND_HALF_UP );
						} else {
							bMargemGrupo = new BigDecimal( "100" );
						}

						imp.say( 0, "|" + sLinhaFina + "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" );
						imp.say( 15, "|" );
						imp.say( 23, "Totais do Grupo :" );
						imp.say( 46, "|VENDA");
						imp.say( 65, Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalVendaGrupo ) ) );
						imp.say( 79, "|CUSTO" );
						imp.say( 89, Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalCustoGrupo ) ) );						
						imp.say( 103, "|LUCRO" );
						imp.say( 110, Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( bTotalLucroGrupo ) ) );
						imp.say( 121, "MARGEN" );
						imp.say( 128, Funcoes.strDecimalToStrCurrency( 5, 2, String.valueOf( bMargemGrupo ) ) );
						imp.say( 134, "%|" );												
						imp.pulaLinha( 1, imp.comprimido() );

						bTotalVenda = bTotalVenda.add( bTotalVendaGrupo );
						bTotalCusto = bTotalCusto.add( bTotalCustoGrupo );
						bTotalDesc = bTotalDesc.add( bTotalDescGrupo );
						bTotalLucro = bTotalLucro.add( bTotalLucroGrupo );
						
						bTotalVendaGrupo = new BigDecimal( "0" );
						bTotalCustoGrupo = new BigDecimal( "0" );
						bTotalDescGrupo = new BigDecimal( "0" );
						bTotalLucroGrupo = new BigDecimal( "0" );						
					}
					
					sCodGrup = rs.getString( 1 );

					sGrup = "GRUPO: " + sCodGrup + " - " + rs.getString( "DescGrup" );

					imp.say( 0, "|" + sLinhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( ( 135 - sGrup.length() ) / 2, sGrup );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
				}
	
				bQtdVenda = rs.getBigDecimal( 8 ) != null ? rs.getBigDecimal( 8 ).setScale( 2, BigDecimal.ROUND_HALF_UP ) : new BigDecimal( "0" );
				bDescVenda = rs.getBigDecimal( 9 ) != null ? rs.getBigDecimal( 9 ).setScale( 2, BigDecimal.ROUND_HALF_UP ) : new BigDecimal( "0" );
				bVlrTotVenda = rs.getBigDecimal( 10 ) != null ? rs.getBigDecimal( 10 ).setScale( 2, BigDecimal.ROUND_HALF_UP ) : new BigDecimal( "0" );				
				bVlrUnitVenda = bVlrTotVenda.divide( bQtdVenda, 2, BigDecimal.ROUND_HALF_UP );
				bVlrUnitCusto = rs.getBigDecimal( 6 ) != null ? rs.getBigDecimal( 6 ).setScale( 2, BigDecimal.ROUND_HALF_UP ) : new BigDecimal( "0" );
				bVlrTotCusto = bVlrUnitCusto.multiply( bQtdVenda ).setScale( 2, BigDecimal.ROUND_HALF_UP );
				bVlrUnitLucro = bVlrUnitVenda.subtract( bVlrUnitCusto ).setScale( 2, BigDecimal.ROUND_HALF_UP );
				bVlrTotLucro = bVlrTotVenda.subtract( bVlrTotCusto ).setScale( 2, BigDecimal.ROUND_HALF_UP );
				
				if ( bVlrTotVenda.floatValue() > 0 ) {
					bMargem =new BigDecimal( bVlrTotLucro.floatValue() / ( bVlrTotVenda.floatValue() / 100 ) ).setScale( 2, BigDecimal.ROUND_HALF_UP );
				} else {
					bMargem = new BigDecimal( "100" );
				}
				
				imp.say( 0, "|" );
				imp.say( 2, rs.getString( 3 ) + "|" );
				imp.say( 16, Funcoes.copy( rs.getString( "DescProd" ), 0, 30 ) + "|" );
				imp.say( 47, Funcoes.strDecimalToStrCurrency( 8, 2, String.valueOf( bQtdVenda ) ) );
				imp.say( 57, Funcoes.strDecimalToStrCurrency( 8, 2, String.valueOf( bVlrUnitVenda ) ) );
				imp.say( 67, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( bVlrTotVenda ) ) + "|" );
				imp.say( 81, Funcoes.strDecimalToStrCurrency( 8, 2, String.valueOf( bVlrUnitCusto ) ) );
				imp.say( 91, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( bVlrTotCusto ) ) + "|" );
				imp.say( 105, Funcoes.strDecimalToStrCurrency( 8, 2, String.valueOf( bVlrUnitLucro ) ) );
				imp.say( 115, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( bVlrTotLucro ) ) );
				imp.say( 128, Funcoes.strDecimalToStrCurrency( 5, 2, String.valueOf( bMargem ) ) );
				imp.say( 134, "%|" );
	
				bTotalVendaGrupo = bTotalVendaGrupo.add( bVlrTotVenda );
				bTotalCustoGrupo = bTotalCustoGrupo.add( bVlrTotCusto );
				bTotalDescGrupo = bTotalDescGrupo.add( bDescVenda );
				bTotalLucroGrupo = bTotalLucroGrupo.add( bVlrTotLucro );
			}
			
			if ( bTotalVendaGrupo.floatValue() > 0 ) {
				bMargemGrupo =new BigDecimal( bTotalLucroGrupo.floatValue() / ( bTotalVendaGrupo.floatValue() / 100 ) ).setScale( 2, BigDecimal.ROUND_HALF_UP );
			} else {
				bMargemGrupo = new BigDecimal( "100" );
			}

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" + sLinhaFina + "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 15, "|" );
			imp.say( 23, "Totais do Grupo :" );
			imp.say( 46, "|VENDA");
			imp.say( 65, Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalVendaGrupo ) ) );
			imp.say( 79, "|CUSTO" );
			imp.say( 89, Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalCustoGrupo ) ) );						
			imp.say( 103, "|LUCRO" );
			imp.say( 110, Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( bTotalLucroGrupo ) ) );
			imp.say( 121, "MARGEN" );
			imp.say( 128, Funcoes.strDecimalToStrCurrency( 5, 2, String.valueOf( bMargemGrupo ) ) );
			imp.say( 134, "%|" );					

			bTotalVenda = bTotalVenda.add( bTotalVendaGrupo );
			bTotalCusto = bTotalCusto.add( bTotalCustoGrupo );
			bTotalDesc = bTotalDesc.add( bTotalDescGrupo );
			bTotalLucro = bTotalLucro.add( bTotalLucroGrupo );
			
			if ( bTotalVenda.floatValue() > 0 ) {
				bMargemTotal =new BigDecimal( bTotalLucro.floatValue() / ( bTotalVenda.floatValue() / 100 ) ).setScale( 2, BigDecimal.ROUND_HALF_UP );
			} else {
				bMargemTotal = new BigDecimal( "100" );
			}
	
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + sLinhaDupla + "+" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 51, "R E S U M O  G E R A L" );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 30, "VENDA TOTAL:    " + Funcoes.strDecimalToStrCurrency( 16, 2, String.valueOf( bTotalVenda ) ) );
			imp.say( 64, "LUCRO ESTIMADO: " + Funcoes.strDecimalToStrCurrency( 16, 2, String.valueOf( bTotalLucro ) ) );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );	
			imp.say( 30, "CUSTO TOTAL:    " + Funcoes.strDecimalToStrCurrency( 16, 2, String.valueOf( bTotalCusto ) ) );
			imp.say( 64, "MARGEM MEDIA:   " + Funcoes.strDecimalToStrCurrency( 16, 2, String.valueOf( bMargemTotal ) ) + "%" );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 30, "DESCONTOS:      " + Funcoes.strDecimalToStrCurrency( 16, 2, String.valueOf( bTotalDesc ) ) );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + sLinhaDupla + "+" );
	
			imp.eject();
			imp.fechaGravacao();
			
			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta ao relat�rio de vendas fisico!" + err.getMessage() );
		}
	}
	
	private void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {
	
		FPrinterJob dlGr = null;
	
		dlGr = new FPrinterJob( "relatorios/VendasFisico.jasper", "Vendas Fisico - resumido", sCab, rs, null, this );
		
		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de resumo diario!" + err.getMessage(), true, con, err );
			}
		}
	}

	private boolean comRef() {
	
		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() )
				if ( rs.getString( "UsaRefProd" ).trim().equals( "S" ) )
					bRetorno = true;
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
		}
		return bRetorno;
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcVend.setConexao( con );
	}
}
