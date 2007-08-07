/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRVendasCli <BR>
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
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRVendasCli extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JCheckBoxPad cbListaFilial = new JCheckBoxPad( "Listar vendas das filiais ?", "S", "N" );

	private JCheckBoxPad cbDesc = new JCheckBoxPad( "Ordenar decrescente ?", "S", "N" );

	private JRadioGroup rgTipo = null;

	private JRadioGroup rgOrdem = null;

	private JRadioGroup rgFaturados = null;

	private JRadioGroup rgFinanceiro = null;

	public FRVendasCli() {

		setTitulo( "Vendas por Cliente" );
		setAtribos( 80, 80, 340, 390 );

		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
		
		Vector vLabs = new Vector();
		Vector vVals = new Vector();

		vLabs.addElement( "Grafico" );
		vLabs.addElement( "Texto" );
		vVals.addElement( "G" );
		vVals.addElement( "T" );
		rgTipo = new JRadioGroup( 1, 2, vLabs, vVals );
		rgTipo.setVlrString( "T" );
		
		Vector vLabs1 = new Vector();
		Vector vVals1 = new Vector();

		vLabs1.addElement( "C�digo" );
		vLabs1.addElement( "Raz�o" );
		vLabs1.addElement( "Valor" );
		vVals1.addElement( "C" );
		vVals1.addElement( "R" );
		vVals1.addElement( "V" );
		rgOrdem = new JRadioGroup( 1, 3, vLabs1, vVals1 );
		rgOrdem.setVlrString( "V" );

		Vector vLabs2 = new Vector();
		Vector vVals2 = new Vector();

		vLabs2.addElement( "Faturado" );
		vLabs2.addElement( "N�o Faturado" );
		vLabs2.addElement( "Ambos" );
		vVals2.addElement( "S" );
		vVals2.addElement( "N" );
		vVals2.addElement( "A" );
		rgFaturados = new JRadioGroup( 3, 1, vLabs2, vVals2 );
		rgFaturados.setVlrString( "S" );

		Vector vLabs3 = new Vector();
		Vector vVals3 = new Vector();

		vLabs3.addElement( "Financeiro" );
		vLabs3.addElement( "N�o Finaceiro" );
		vLabs3.addElement( "Ambos" );
		vVals3.addElement( "S" );
		vVals3.addElement( "N" );
		vVals3.addElement( "A" );
		rgFinanceiro = new JRadioGroup( 3, 1, vLabs3, vVals3 );
		rgFinanceiro.setVlrString( "S" );

		cbDesc.setVlrString( "S" );

		adic( new JLabelPad( "Periodo:" ), 7, 10, 120, 20 );
		adic( new JLabelPad( "De:" ), 7, 30, 30, 20 );
		adic( txtDataini, 37, 30, 90, 20 );
		adic( new JLabelPad( "At�:" ), 140, 30, 30, 20 );
		adic( txtDatafim, 175, 30, 90, 20 );
		adic( rgTipo, 7, 60, 261, 30 );
		adic( new JLabelPad( "Ordem" ), 7, 90, 50, 20 );
		adic( rgOrdem, 7, 110, 261, 30 );
		adic( cbDesc, 7, 145, 250, 20 );
		adic( cbListaFilial, 7, 165, 200, 20 );
		adic( rgFaturados, 7, 190, 120, 70 );
		adic( rgFinanceiro, 148, 190, 120, 70 );

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
		String sOrdem = "";
		String sWhere1 = null;
		String sWhere2 = null;

		try {
			
			if ( rgFaturados.getVlrString().equals( "S" ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
				sCab.append( "FATURADO" );
			}
			else if ( rgFaturados.getVlrString().equals( "N" ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
				if ( sCab.length() > 0 ) {
					sCab.append( " - " );
				}
				sCab.append( "NAO FATURADO" );
			}
			else if ( rgFaturados.getVlrString().equals( "A" ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";
			}

			if ( rgFinanceiro.getVlrString().equals( "S" ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
				if ( sCab.length() > 0 ) {
					sCab.append( " - " );
				}
				sCab.append( "FINANCEIRO" );
			}
			else if ( rgFinanceiro.getVlrString().equals( "N" ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
				if ( sCab.length() > 0 ) {
					sCab.append( " - " );
				}
				sCab.append( "NAO FINANCEIRO" );
			}
			else if ( rgFinanceiro.getVlrString().equals( "A" ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";
			}

			if ( rgOrdem.getVlrString().equals( "C" ) ) {
				sOrdem = "S".equals( cbDesc.getVlrString() ) ? "V.CODCLI DESC, C.RAZCLI DESC, C.FONECLI DESC" : "V.CODCLI, C.RAZCLI, C.FONECLI";
			}
			else if ( rgOrdem.getVlrString().equals( "R" ) ) {
				sOrdem = "S".equals( cbDesc.getVlrString() ) ? "C.RAZCLI DESC, V.CODCLI DESC, C.FONECLI DESC" : "C.RAZCLI, V.CODCLI, C.FONECLI";
			}
			else if ( rgOrdem.getVlrString().equals( "V" ) ) {
				sOrdem = "S".equals( cbDesc.getVlrString() ) ? "5 DESC" : "5";
			}

			sSQL.append( "SELECT V.CODCLI, C.RAZCLI, C.DDDCLI, C.FONECLI, SUM(V.VLRLIQVENDA) AS VALOR " );
			sSQL.append( "FROM VDVENDA V, VDCLIENTE C, EQTIPOMOV TM " );
			sSQL.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? " );
			sSQL.append( "AND V.CODEMPCL=C.CODEMP AND V.CODFILIALCL=C.CODFILIAL AND V.CODCLI=C.CODCLI " );
			sSQL.append( "AND V.CODEMPTM=TM.CODEMP AND V.CODFILIALTM=TM.CODFILIAL AND V.CODTIPOMOV=TM.CODTIPOMOV " );
			sSQL.append( "AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' " );
			sSQL.append( "AND V.DTEMITVENDA BETWEEN ? AND ? " );
			sSQL.append( sWhere1 );
			sSQL.append( sWhere2 );
			sSQL.append( " GROUP BY V.CODCLI, C.RAZCLI, C.DDDCLI, C.FONECLI" );
			sSQL.append( " ORDER BY " + sOrdem );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			rs = ps.executeQuery();
			
			if ( "T".equals( rgTipo.getVlrString() ) ) {
				imprimirTexto( bVisualizar, rs, sCab.toString() );
			}
			else if ( "G".equals( rgTipo.getVlrString() ) ) {
				imprimirGrafico( bVisualizar, rs, sCab.toString() );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao montar relat�rio de vendas!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sCab = null;
			sOrdem = null;
			sWhere1 = null;
			sWhere2 = null;
			sSQL = null;
			System.gc();
		}
	}
	
	private void imprimirTexto( final boolean bVisualizar, final ResultSet rs, final String sCab ) {
		
		String sLinhaFina = Funcoes.replicate( "-", 133 );
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		int count = 1;
		
		try {

			linPag = imp.verifLinPag() - 1;
			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relat�rio de Vendas por Cliente" );
			imp.addSubTitulo( "VENDAS  -  PERIODO DE :" + txtDataini.getVlrString() + " ATE: " + txtDatafim.getVlrString() );
			imp.addSubTitulo( sCab.toString() );

			while ( rs.next() ) {

				if ( imp.pRow() == linPag ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + sLinhaFina + "+" );
					imp.eject();
					imp.incPags();
				}

				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, true );
					imp.say( 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|" + sLinhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 10, "|  Cod.cli" );
					imp.say( 23, "|  Razao Social" );
					imp.say( 75, "|  Telefone" );
					imp.say( 95, "|  Valor Total" );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );
				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "| " + Funcoes.alinhaDir( count++, 6 ) );
				imp.say( 10, "| " + Funcoes.alinhaDir( rs.getInt( "CODCLI" ), 10 ) );
				imp.say( 23, "| " + rs.getString( "RAZCLI" ) );
				imp.say( 75, "| " + ( rs.getString( "DDDCli" ) != null ? "(" + rs.getString( "DDDCli" ) + ")" : "" ) + ( rs.getString( "FoneCli" ) != null ? Funcoes.setMascara( rs.getString( "FoneCli" ).trim(), "####-####" ) : "" ) );
				imp.say( 95, "| " + Funcoes.strDecimalToStrCurrency( 18, 2, rs.getString( 5 ) ) );
				imp.say( 135, "|" );
			}

			if ( count > 1 ) {
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "+" + sLinhaFina + "+" );
			}

			imp.eject();
			imp.fechaGravacao();

			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao montar relat�rio de vendas!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} 
	}

	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = new FPrinterJob( "relatorios/VendasCliente.jasper", "Vendas por Cliente", sCab, rs, null, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de vendas por cliente!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
	}
}
