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

public class FRDesempVend extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcVend = new ListaCampos( this );
	
	private JRadioGroup<?, ?> rgFaturados = null;

	private JRadioGroup<?, ?> rgFinanceiro = null;
	
	private JRadioGroup<?, ?> rgEmitidos = null;
	
	private Vector<String> vLabsFatu = new Vector<String>();

	private Vector<String> vValsFatu = new Vector<String>();
	
	private Vector<String> vLabsFinan = new Vector<String>();

	private Vector<String> vValsFinan = new Vector<String>();
		
	private Vector<String> vLabsEmit = new Vector<String>();

	private Vector<String> vValsEmit = new Vector<String>();

	
	public FRDesempVend() {

		super( false );
		setTitulo( "Desempenho por vendedor" );
		setAtribos( 80, 80, 330, 350 );

		montaListaCampos();
		montaTela();

		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );

	}

	private void montaListaCampos() {

		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.comiss.", ListaCampos.DB_PK, txtDescVend, false ) );
		lcVend.add( new GuardaCampo( txtDescVend, "NomeVend", "Descri��o do comissionado", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "VD" );
		lcVend.setReadOnly( true );
		txtCodVend.setPK( true );
		txtCodVend.setTabelaExterna( lcVend, null );
		txtCodVend.setListaCampos( lcVend );
		txtCodVend.setNomeCampo( "CodVend" );
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Per�odo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );
		
		vLabsFatu.addElement( "Faturado" );
		vLabsFatu.addElement( "N�o Faturado" );
		vLabsFatu.addElement( "Ambos" );
		vValsFatu.addElement( "S" );
		vValsFatu.addElement( "N" );
		vValsFatu.addElement( "A" );
		rgFaturados = new JRadioGroup<String, String>( 3, 1, vLabsFatu, vValsFatu );
		rgFaturados.setVlrString( "S" );

		vLabsFinan.addElement( "Financeiro" );
		vLabsFinan.addElement( "N�o Financeiro" );
		vLabsFinan.addElement( "Ambos" );
		vValsFinan.addElement( "S" );
		vValsFinan.addElement( "N" );
		vValsFinan.addElement( "A" );
		rgFinanceiro = new JRadioGroup<String, String>( 3, 1, vLabsFinan, vValsFinan );
		rgFinanceiro.setVlrString( "S" );
		
		vLabsEmit.addElement( "Emitidos" );
		vLabsEmit.addElement( "N�o emitidos" );
		vLabsEmit.addElement( "Ambos" );
		vValsEmit.addElement( "S" );
		vValsEmit.addElement( "N" );
		vValsEmit.addElement( "A" );
		rgEmitidos = new JRadioGroup<String, String>( 3, 1, vLabsEmit, vValsEmit );
		rgEmitidos.setVlrString( "A" );

		adic( lbPeriodo, 17, 10, 80, 20 );
		adic( lbLinha, 7, 20, 300, 45 );
		adic( txtDataini, 17, 35, 125, 20 );
		adic( new JLabelPad( "�", SwingConstants.CENTER ), 142, 35, 30, 20 );
		adic( txtDatafim, 172, 35, 125, 20 );
		adic( new JLabelPad( "C�d.Vend" ), 7, 65, 60, 20 );
		adic( txtCodVend, 7, 85, 60, 20 );
		adic( new JLabelPad( "Nome do vendedor" ), 70, 65, 200, 20 );
		adic( txtDescVend, 70, 85, 235, 20 );
		
		adic( rgFaturados, 7, 115, 120, 70 );
		adic( rgFinanceiro, 148, 115, 120, 70 );
		adic( rgEmitidos, 7, 195, 120, 70 );

	}

	public void imprimir( TYPE_PRINT bVisualizar ) {
		
		int param = 1;
		
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		//StringBuffer sCab = new StringBuffer();
		String sWhere1 = "";
		String sWhere2 = "";
		String sCab = "PER�ODO: "+txtDataini.getVlrString()+" AT� "+txtDatafim.getVlrString()+"\n";

		try {
			
			if ( rgFaturados.getVlrString().equals( "S" ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
				sCab += "FATURADO" ;
			}
			else if ( rgFaturados.getVlrString().equals( "N" ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
				if ( sCab.length() > 0 ) {
					sCab += " - " ;
				}
				sCab += "N�O FATURADO" ;
			}
			if ( rgFinanceiro.getVlrString().equals( "S" ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
				if ( sCab.length() > 0 ) {
					sCab += " - " ;
				}
				sCab += "FINANCEIRO" ;
			}
			else if ( rgFinanceiro.getVlrString().equals( "N" ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
				if ( sCab.length() > 0 ) {
					sCab +=  " - " ;
				}
				sCab += "N�O FINANCEIRO" ;
			}
			if ( rgEmitidos.getVlrString().equals( "S" ) ) {
				sWhere2 = " AND VD.STATUSVENDA IN ('V2','V3','P3') ";
				sCab += " S� EMITIDOS " ;
			}
			else if ( rgEmitidos.getVlrString().equals( "N" ) ) {
				sWhere2 = " AND VD.STATUSVENDA NOT IN ('V2','V3','P3') ";
				sCab += " N�O EMITIDOS " ;
			}
			
			sSQL.append( "SELECT V.CODVEND, V.NOMEVEND, " ); 
			sSQL.append( "COUNT(DISTINCT VD.CODVENDA) AS TOTVENDCOMIS, " ); 
			sSQL.append( "SUM(VI.VLRLIQITVENDA) AS TOTVENDAS, " );
			sSQL.append( "(SUM(VI.VLRLIQITVENDA)/COUNT(DISTINCT VD.CODVENDA)) AS VLRMEDVENDA, " );
			sSQL.append( "COUNT(VI.CODITVENDA) AS QTDITENS, " );
			sSQL.append( "(SUM(VI.VLRLIQITVENDA)/COUNT(VI.CODITVENDA)) AS ITEMMEDIO, " );
			sSQL.append( "(SELECT COUNT(*) FROM VDORCAMENTO WHERE CODEMP=? AND CODFILIAL=? AND CODVEND=V.codvend  AND DTORC BETWEEN ? AND ?) AS QTDORC ");
			sSQL.append( "FROM VDVENDA VD " );
			sSQL.append( "LEFT OUTER JOIN VDVENDEDOR V ON V.CODVEND = VD.CODVEND " );
			sSQL.append( "LEFT OUTER JOIN VDITVENDA VI ON VI.CODVENDA = VD.CODVENDA " );
			sSQL.append( "LEFT OUTER JOIN EQTIPOMOV TM ON VD.CODEMPTM=TM.CODEMP AND VD.CODFILIALTM=TM.CODFILIAL AND VD.CODTIPOMOV=TM.CODTIPOMOV " );
			sSQL.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND " );
			sSQL.append( "VD.CODEMPVD=V.CODEMP AND VD.CODFILIALVD=V.CODFILIAL AND " );
			sSQL.append( "NOT SUBSTR(VD.STATUSVENDA,1,1)='C' AND " );
			sSQL.append( "VD.DTEMITVENDA BETWEEN ? AND ? " ); 
			
			if ( txtCodVend.getVlrInteger().intValue() > 0 ) {
				sSQL.append( "AND V.CODVEND=? " );
			}
			
			sSQL.append( sWhere1 );
			sSQL.append( sWhere2 );
			
			sSQL.append( " GROUP BY 1,2 " );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDEDOR" ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDEDOR" ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			
			if ( txtCodVend.getVlrInteger().intValue() > 0 ) {
				ps.setInt( param++, txtCodVend.getVlrInteger().intValue() );
			}
		
			rs = ps.executeQuery();

			imprimirGrafico( bVisualizar, rs, sCab.toString() );

			rs.close();
			ps.close();

			con.commit();

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao montar relat�rio de desempenho por vendedor!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	public void imprimirGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = new FPrinterJob( "relatorios/FRDesempVend.jasper", "Desempenho por vendedor", sCab, rs, null, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.preview();
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de desempenho por vendedor!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcVend.setConexao( cn );
	}
}
