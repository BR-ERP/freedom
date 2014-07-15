/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FRVendasCli <BR>
 * 
 *                 Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                 modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                 na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                 Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                 sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                 Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                 Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                 de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                 Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

public class FRVendasTipoMov extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JCheckBoxPad cbDesc = new JCheckBoxPad( "Ordenar decrescente ?", "DESC", "" );

	private JRadioGroup<String, String> rgOrdem = null;
	
	private JRadioGroup<?, ?> rgFormato = null;

	private JRadioGroup<String, String> rgFaturados = null;

	private JRadioGroup<String, String> rgFinanceiro = null;
	
	private JRadioGroup<?, ?> rgEmitidos = null;
	
	private Vector<String> vLabsEmit = new Vector<String>();

	private Vector<String> vValsEmit = new Vector<String>();
	
	private ListaCampos lcCli = new ListaCampos(this);


	public FRVendasTipoMov() {

		super( false );
		setTitulo( "Vendas por tipo de movimento" );
		setAtribos( 80, 80, 330, 460 );

		montaRadioGrups();
		montaTela();

		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );

		cbDesc.setVlrString( "DESC" );
		
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.Cli", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "NomeCli", "Nome do Cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		txtCodCli.setTabelaExterna( lcCli, null );	
	}

	private void montaRadioGrups() {
		
		Vector<String> vLabs1 = new Vector<String>();
		Vector<String> vVals1 = new Vector<String>();

		vLabs1.addElement( "Detalhado" );
		vLabs1.addElement( "Resumido" );
		vVals1.addElement( "D" );
		vVals1.addElement( "R" );
		rgFormato = new JRadioGroup<String, String>( 1, 2, vLabs1, vVals1 );
		rgFormato.setVlrString( "D" );

		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();

		vLabs.addElement( "Tipo de movimento" );
		vLabs.addElement( "Valor" );
		vVals.addElement( "TM.CODTIPOMOV" );
		vVals.addElement( "3" );

		rgOrdem = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgOrdem.setVlrString( "3" );

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
		vLabs3.addElement( "N�o Financeiro" );
		vLabs3.addElement( "Ambos" );
		vVals3.addElement( "S" );
		vVals3.addElement( "N" );
		vVals3.addElement( "A" );
		rgFinanceiro = new JRadioGroup<String, String>( 3, 1, vLabs3, vVals3 );
		rgFinanceiro.setVlrString( "S" );
		
		vLabsEmit.addElement( "Emitidos" );
		vLabsEmit.addElement( "N�o emitidos" );
		vLabsEmit.addElement( "Ambos" );
		vValsEmit.addElement( "S" );
		vValsEmit.addElement( "N" );
		vValsEmit.addElement( "A" );
		rgEmitidos = new JRadioGroup<String, String>( 3, 1, vLabsEmit, vValsEmit );
		rgEmitidos.setVlrString( "A" );

		
		
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Periodo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 17, 10, 80, 20 );
		adic( lbLinha, 7, 20, 300, 45 );

		adic( txtDataini, 17, 35, 125, 20 );
		adic( new JLabelPad( "�", SwingConstants.CENTER ), 142, 35, 30, 20 );
		adic( txtDatafim, 172, 35, 125, 20 );
 		adic( new JLabelPad( "C�d.Cliente" ), 7, 70, 240, 20 );
		adic( txtCodCli, 7, 90, 70, 20 );
		adic( new JLabelPad( "Raz�o social" ), 80,70, 240, 20 );
		adic( txtNomeCli, 80, 90, 220, 20 );
		adic( rgFormato, 7,	130, 300, 30 );
		adic( new JLabelPad( "Ordem" ), 7, 160, 50, 20 );
		adic( rgOrdem, 7, 180, 300, 30 );
		adic( cbDesc, 7, 215, 300, 20 );
		adic( rgFaturados, 7, 240, 145, 70 );
		adic( rgFinanceiro, 160, 240, 145, 70 );
		adic( rgEmitidos, 7, 320, 175, 70 );
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		String sWhere1 = "";
		String sWhere2 = null;
		String sWhere3 = "";

		try {
			
			if ( txtCodCli.getVlrInteger().intValue() > 0 ) {
				sWhere1 += " AND C.CODCLI = " + txtCodCli.getVlrInteger();
				sCab.append( "FILTRADO PELO CLIENTE - " + txtNomeCli.getVlrString());
			}

			if ( rgFaturados.getVlrString().equals( "S" ) ) {
				sWhere1 += " AND TM.FISCALTIPOMOV='S' ";
				sCab.append( "FATURADO" );
			}
			else if ( rgFaturados.getVlrString().equals( "N" ) ) {
				sWhere1 += " AND TM.FISCALTIPOMOV='N' ";
				if ( sCab.length() > 0 ) {
					sCab.append( " - " );
				}
				sCab.append( "NAO FATURADO" );
			}
			
			else if ( rgFaturados.getVlrString().equals( "A" ) ) {
				sWhere1 += " AND TM.FISCALTIPOMOV IN ('S','N') ";
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
			
			if ( rgEmitidos.getVlrString().equals( "S" ) ) {
				sWhere3 = " AND V.STATUSVENDA IN ('V2','V3','P3') " ;
				sCab.append(" EMITIDOS " );
			}
			else if ( rgEmitidos.getVlrString().equals( "N" ) ) {
				sWhere3 = " AND V.STATUSVENDA NOT IN ('V2','V3','P3') ";
				sCab.append( "NAO EMITIDOS" );
			}
			if ( "D".equals( rgFormato.getVlrString() ) ) {
				sSQL.append( "SELECT TM.CODTIPOMOV, TM.DESCTIPOMOV, V.DOCVENDA, V.CODVENDA, V.DTEMITVENDA, " );
				sSQL.append( "SUM( V.vlrliqvenda ) AS VALORLIQUIDO, C.RAZCLI ");
				sSQL.append( "FROM FNPLANOPAG P, VDVENDA V, EQTIPOMOV TM, VDCLIENTE C " );
				sSQL.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND P.CODPLANOPAG=V.CODPLANOPAG AND " );
				sSQL.append( "V.CODEMPPG=P.CODEMP AND V.CODFILIALPG=P.CODFILIAL AND " );
				sSQL.append( "V.CODEMPTM=TM.CODEMP AND V.CODFILIALTM=TM.CODFILIAL AND V.CODTIPOMOV=TM.CODTIPOMOV AND " );
				sSQL.append( "C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI AND V.VLRPRODVENDA > 0 AND " );
			}else{
				sSQL.append( "SELECT TM.CODTIPOMOV, TM.DESCTIPOMOV, SUM( V.VLRLIQVENDA ) AS VALORVD, " );
				sSQL.append( "SUM( V.vlrprodvenda ) AS valorbruto, SUM( V.vlrdescvenda ) AS vlrdescvenda, SUM( V.vlrdescitvenda ) AS vlrdescitvenda ");
				sSQL.append( "FROM FNPLANOPAG P, VDVENDA V, EQTIPOMOV TM, VDCLIENTE C " );
				sSQL.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND P.CODPLANOPAG=V.CODPLANOPAG AND " );
				sSQL.append( "V.CODEMPPG=P.CODEMP AND V.CODFILIALPG=P.CODFILIAL AND " );
				sSQL.append( "V.CODEMPTM=TM.CODEMP AND V.CODFILIALTM=TM.CODFILIAL AND V.CODTIPOMOV=TM.CODTIPOMOV AND " );
				sSQL.append( "C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI AND ");
			}
			
			sSQL.append( "NOT SUBSTR(V.STATUSVENDA,1,1)='C' AND " );
			sSQL.append( "V.DTEMITVENDA BETWEEN ? AND ? " );
			sSQL.append( sWhere1 );
			sSQL.append( sWhere2 );
			sSQL.append( sWhere3 );
			
			sSQL.append( "GROUP BY TM.CODTIPOMOV, TM.DESCTIPOMOV " );
			if( "D".equals( rgFormato.getVlrString() )){
				sSQL.append( ", V.CODVENDA, V.DOCVENDA, V.DTEMITVENDA, C.RAZCLI " );
			}
			
			sSQL.append( "ORDER BY " );
			sSQL.append( rgOrdem.getVlrString() + " " + cbDesc.getVlrString() );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			rs = ps.executeQuery();

			imprimirGrafico( bVisualizar, rs, sCab.toString() );

			rs.close();
			ps.close();

			con.commit();

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de vendas por tipo de movimento!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	public void imprimirGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = null;

		if ( "D".equals( rgFormato.getVlrString() ) ) {
			dlGr = new FPrinterJob( "relatorios/VendasTipoMovDetalhado.jasper", "Vendas por tipo de movimento - detalhado", sCab, rs, null, this );
		}
		else if ( "R".equals( rgFormato.getVlrString() ) ) {
			dlGr = new FPrinterJob( "relatorios/VendasTipoMov.jasper", "Vendas por tipo de movimento", sCab, rs, null, this );
		}
		
		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.preview();
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de vendas por tipo de movimento!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( con );
	}
}
