/**
 * @version 24/10/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FRRazFor.java <BR>
 * 
 *                   Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                   modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                   na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                   Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                   sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                   Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                   Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                   Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

public class FRRazFor extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcFor = new ListaCampos( this );

	private JTextFieldPad txtCnpjFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	public FRRazFor() {

		super( false );
		setTitulo( "Compras por Raz�o" );
		setAtribos( 50, 50, 340, 195 );

		montaTela();
		montaListaCampos();
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Per�odo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 15, 5, 80, 20 );
		adic( lbLinha, 7, 15, 303, 40 );

		adic( new JLabelPad( "De:", SwingConstants.CENTER ), 10, 25, 40, 20 );
		adic( txtDataini, 50, 25, 100, 20 );
		adic( new JLabelPad( "At�:", SwingConstants.CENTER ), 150, 25, 45, 20 );
		adic( txtDatafim, 195, 25, 100, 20 );
		adic( new JLabelPad( "C�d.For" ), 7, 60, 80, 20 );
		adic( txtCodFor, 7, 80, 80, 20 );
		adic( new JLabelPad( "Descri��o do fornecedor" ), 90, 60, 220, 20 );
		adic( txtDescFor, 90, 80, 220, 20 );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	private void montaListaCampos() {

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtDescFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtCnpjFor, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false ) );

		txtCodFor.setTabelaExterna( lcFor, null );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		lcFor.setReadOnly( true );
		lcFor.montaSql( false, "FORNECED", "CP" );
	}

	@ Override
	public void imprimir( TYPE_PRINT bVisualizar ) {

		int param = 1;
		int codfor = 0;

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sCab = new StringBuffer();

		try {

			codfor = txtCodFor.getVlrInteger().intValue();
			if ( codfor != 0 ) {
				sCab.append( "FORNECEDOR - " + txtDescFor.getVlrString() );
			}

			sSQL.append( "SELECT F.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sSQL.append( "CAST( '" );
			sSQL.append( Funcoes.dateToStrDB( txtDataini.getVlrDate() ) );
			/**
			 * Tipo A = Saldo anterior Busca na FNPAGAR todas as compras com valor financeiro a pagar (VLRPAG)
			 */
			sSQL.append( "' AS DATE) DATA, 'A' TIPO, ");
			sSQL.append( "'A' TIPOSUBLANCA, ");
			sSQL.append( "0 DOC, 0.00 VLRDEB, " );
			sSQL.append( "(COALESCE( ( SELECT SUM(P.VLRPARCPAG) " );
			sSQL.append( "FROM FNPAGAR P " );
			sSQL.append( "WHERE P.CODEMP=? AND P.CODFILIAL=? AND " );
			sSQL.append( "P.CODEMPFR=F.CODEMP AND P.CODFILIALFR=F.CODFILIAL AND " );
			sSQL.append( "P.CODFOR=F.CODFOR AND " );
			sSQL.append( "P.DATAPAG < ? ),0) + " );
			/**
			 * Subtrai o valor pago do saldo anterior O sinal do sublanca � invertido, ent�o o sinal - est� subtraindo
			 */
			sSQL.append( "COALESCE( ( SELECT SUM(SL.VLRSUBLANCA*-1) " );
			sSQL.append( "FROM FNSUBLANCA SL " );
			sSQL.append( "WHERE SL.CODEMPFR=F.CODEMP AND SL.CODFILIALFR=F.CODFILIAL AND " );
			sSQL.append( "SL.CODFOR=F.CODFOR AND SL.CODSUBLANCA<>0 AND " );
			sSQL.append( "SL.CODEMP=? AND SL.CODFILIAL=? AND " );
			sSQL.append( "SL.DATASUBLANCA < ? ), 0) +  " );

			/**
			 * Subtrai o valor do desconto na data do lan�amento financeiro
			 */

			/*sSQL.append( "COALESCE( ( SELECT SUM(SL.VLRSUBLANCA) FROM FNSUBLANCA SL, SGPREFERE1 PF WHERE  " );
			sSQL.append( " SL.CODEMPFR=F.CODEMP AND SL.CODFILIALFR=F.CODFILIAL AND SL.CODFOR=F.CODFOR AND " );
			//sSQL.append( " SL.CODEMPPN=PF.CODEMPDR AND SL.CODFILIALPN=PF.CODFILIALDR AND SL.CODPLAN=PF.CODPLANDR AND " );
			sSQL.append( " SL.TIPOSUBLANCA=? AND " );
			sSQL.append( " SL.CODEMP=? AND SL.CODFILIAL=? AND SL.DATASUBLANCA < ? ), 0) - " );
*/
			/**
			 * Subtrai o valor das devolu��es do saldo anterior
			 */
			sSQL.append( "( COALESCE( ( SELECT SUM(VD.VLRLIQVENDA) " );
			sSQL.append( "FROM VDVENDA VD, EQTIPOMOV TM, EQCLIFOR CF " );
			sSQL.append( "WHERE VD.CODEMP=? AND VD.CODFILIAL=? AND  TM.CODEMP=VD.CODEMPTM AND " );
			sSQL.append( "TM.CODFILIAL=VD.CODFILIALTM AND TM.ESTIPOMOV='S' AND TM.TIPOMOV='DV' AND " );
			sSQL.append( "TM.CODTIPOMOV=VD.CODTIPOMOV AND CF.CODEMPFR=F.CODEMP AND " );
			sSQL.append( "CF.CODFILIALFR=F.CODFILIAL AND CF.CODFOR=F.CODFOR AND VD.CODEMPCL=CF.CODEMP AND " );
			sSQL.append( "VD.CODFILIALCL=CF.CODFILIAL AND VD.CODCLI=CF.CODCLI AND VD.DTEMITVENDA < ? ),0) ) ) " );
			sSQL.append( " VLRCRED " );
			/**
			 * Filtro do fornecedor
			 */
			sSQL.append( "FROM CPFORNECED F " );
			sSQL.append( "WHERE F.CODEMP=? AND F.CODFILIAL=? AND " );
			if ( codfor != 0 ) {
				sSQL.append( "F.CODFOR=? AND " );
			}
			/**
			 * Verifica a exist�ncia dos valores
			 */
			sSQL.append( "( EXISTS (SELECT * FROM FNPAGAR P2 " );
			sSQL.append( "WHERE P2.CODEMP=? AND P2.CODFILIAL=? AND " );
			sSQL.append( "P2.CODEMPFR=F.CODEMP AND P2.CODFILIALFR=F.CODFILIAL AND " );
			sSQL.append( "P2.CODFOR=F.CODFOR AND DATAPAG BETWEEN ? AND ? )" );
			sSQL.append( "OR EXISTS (SELECT * FROM FNSUBLANCA SL2 " );
			sSQL.append( "WHERE F.CODEMP=SL2.CODEMPFR AND " );
			sSQL.append( "F.CODFILIAL=SL2.CODFILIALFR AND F.CODFOR=SL2.CODFOR AND " );
			sSQL.append( "SL2.CODEMP=? AND SL2.CODFILIAL=? AND " );
			sSQL.append( "SL2.DATASUBLANCA BETWEEN ? AND ? ) OR " );
			sSQL.append( " EXISTS (SELECT * FROM VDVENDA VD, EQTIPOMOV TM, EQCLIFOR CF " );
			sSQL.append( "WHERE VD.CODEMP=? AND VD.CODFILIAL=? AND TM.CODEMP=VD.CODEMPTM AND " );
			sSQL.append( "TM.CODFILIAL=VD.CODFILIALTM AND TM.CODTIPOMOV=VD.CODTIPOMOV AND " );
			sSQL.append( "TM.ESTIPOMOV='S' AND TM.TIPOMOV='DV' AND " );
			sSQL.append( "CF.CODEMPFR=F.CODEMP AND CF.CODFILIALFR=F.CODFILIAL AND CF.CODFOR=F.CODFOR AND " );
			sSQL.append( "VD.CODEMPCL=CF.CODEMP AND VD.CODFILIALCL=CF.CODFILIAL AND " );
			sSQL.append( "VD.CODCLI=CF.CODCLI AND VD.DTEMITVENDA BETWEEN ? AND ? ) ) " );
			/**
			 * Fim da query do saldo anterior
			 */

			/**
			 * Query das compras
			 */
			sSQL.append( "UNION ALL " );
			sSQL.append( "SELECT P.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sSQL.append( "P.DATAPAG DATA, 'C' TIPO, ");
			sSQL.append( "'C' TIPOSUBLANCA, ");

			sSQL.append( "P.DOCPAG DOC, " );
			sSQL.append( "0.00 VLRDEB, P.VLRPARCPAG VLRCRED " );
			sSQL.append( "FROM FNPAGAR P, CPFORNECED F " );
			sSQL.append( "WHERE F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND " );
			sSQL.append( "F.CODFOR=P.CODFOR AND " );
			sSQL.append( "P.CODEMP=? AND P.CODFILIAL=? AND " );
			if ( codfor != 0 ) {
				sSQL.append( "F.CODFOR=? AND " );
			}
			sSQL.append( "P.DATAPAG BETWEEN ? AND ? " );

			/**
			 * Query dos pagamentos
			 */
			sSQL.append( "UNION ALL " );
			sSQL.append( "SELECT SL.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sSQL.append( "SL.DATASUBLANCA DATA, ");
			sSQL.append( " (CASE WHEN SL.TIPOSUBLANCA='P' THEN 'P' ELSE 'X' END) TIPO, ");
			sSQL.append( "SL.TIPOSUBLANCA, ");
			sSQL.append( "P.DOCPAG DOC, SL.VLRSUBLANCA VLRDEB, ");
			sSQL.append( "(CASE WHEN SL.TIPOSUBLANCA IN ('J','D','M') THEN SL.VLRSUBLANCA ELSE 0.00 END) VLRCRED " );
			sSQL.append( "FROM FNSUBLANCA SL, CPFORNECED F, FNPAGAR P " );
			sSQL.append( "WHERE F.CODEMP=SL.CODEMPFR AND F.CODFILIAL=SL.CODFILIALFR AND " );
			sSQL.append( "P.CODEMP=SL.CODEMPPG AND P.CODFILIAL=SL.CODFILIALPG AND P.CODPAG=SL.CODPAG AND " );
			// Incluido no tiposublanca P - Padr�o, M - Multa e J-Juros
			//sSQL.append( "SL.TIPOSUBLANCA IN ('P','M','J') AND ");
			sSQL.append( "F.CODFOR=SL.CODFOR AND SL.CODSUBLANCA<>0 AND " );
			if ( codfor != 0 ) {
				sSQL.append( "F.CODFOR=? AND " );
			}
			sSQL.append( "SL.CODEMP=? AND SL.CODFILIAL=? AND " );
			sSQL.append( "SL.DATASUBLANCA BETWEEN ? AND ? " );
			/**
			 * Query dos cancelamentos
			 */
			sSQL.append( "UNION ALL ");
			sSQL.append( " SELECT F.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sSQL.append( " IP.DTVENCITPAG DATA, 'X' TIPO, " );
			sSQL.append( "'X' TIPOSUBLANCA, ");
			sSQL.append( "P.DOCPAG DOC, COALESCE(SUM(IP.VLRCANCITPAG),0) VLRCRED, 0.00 VLRDEB " );
			sSQL.append( "FROM FNPAGAR P, FNITPAGAR IP, CPFORNECED F WHERE P.CODEMP=? AND P.CODFILIAL=? AND P.CODEMPFR=F.CODEMP AND " );
			sSQL.append( "P.CODFILIALFR=F.CODFILIAL AND P.CODFOR=F.CODFOR ");
			sSQL.append( "AND IP.CODEMP=P.CODEMP AND IP.CODFILIAL=P.CODFILIAL AND IP.CODPAG=P.CODPAG AND IP.STATUSITPAG IN ('CP') ");
			if ( codfor != 0 ) {
				sSQL.append( "AND C.CODFOR=? " );
			}
			//sSQL.append( "AND R.CODEMP=? AND R.CODFILIAL=? AND " );
			sSQL.append( "AND R.DATAREC BETWEEN ? AND ? " );
			sSQL.append( "GROUP BY 1, 2, 3, 4, 5, 6 ");
			/**

			
			/**
			 * Query das devolu��es
			 */
			sSQL.append( "UNION ALL SELECT F.CODFOR CODEMIT, F.RAZFOR RAZEMIT, VD.DTEMITVENDA DATA, " );
			sSQL.append( " 'Z' TIPO, ");
			sSQL.append( "'Z' TIPOSUBLANCA, " );
			sSQL.append( "VD.DOCVENDA DOC, VD.VLRLIQVENDA VLRCRED, 0.00 VLRDEB " );
			sSQL.append( "FROM VDVENDA VD, EQTIPOMOV TM, EQCLIFOR CF, CPFORNECED F " );
			sSQL.append( "WHERE TM.CODEMP=VD.CODEMPTM AND TM.CODFILIAL=VD.CODFILIALTM AND " );
			sSQL.append( "TM.CODTIPOMOV=VD.CODTIPOMOV AND TM.ESTIPOMOV='S' AND TM.TIPOMOV='DV' AND " );
			sSQL.append( "CF.CODEMPFR=F.CODEMP AND CF.CODFILIALFR=F.CODFILIAL AND CF.CODFOR=F.CODFOR AND " );
			sSQL.append( "VD.CODEMPCL=CF.CODEMP AND VD.CODFILIALCL=CF.CODFILIAL AND " );
			sSQL.append( "VD.CODCLI=CF.CODCLI AND " );
			if ( codfor != 0 ) {
				sSQL.append( "F.CODFOR=? AND " );
			}
			sSQL.append( "VD.CODEMP=? AND VD.CODFILIAL=? AND " );
			sSQL.append( "VD.DTEMITVENDA BETWEEN ? AND ? " );

			/**
			 * Query dos descontos
			 */
/*			sSQL.append( "UNION ALL SELECT F.CODFOR CODEMIT, F.RAZFOR RAZEMIT, SL.DATASUBLANCA DATA, " );
			sSQL.append( " 'X' TIPO, P.DOCPAG DOC, (SL.VLRSUBLANCA * -1) VLRDEB,  0.00 VLRCRED " );
			sSQL.append( "FROM FNSUBLANCA SL, FNPAGAR P, CPFORNECED F " );
			sSQL.append( "WHERE SL.CODEMPPG=P.CODEMP AND SL.CODFILIALPG=P.CODFILIAL AND " );
			sSQL.append( " SL.TIPOSUBLANCA=? AND " );
			sSQL.append( " SL.CODPAG=P.CODPAG AND F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND " );
			sSQL.append( "F.CODFOR=P.CODFOR AND " );
			if ( codfor != 0 ) {
				sSQL.append( "F.CODFOR=? AND " );
			}
			sSQL.append( "P.CODEMP=? AND P.CODFILIAL=? AND " );
			sSQL.append( "SL.DATASUBLANCA BETWEEN ? AND ?  " );
*/
			sSQL.append( "ORDER BY 1, 2, 3, 4, 6, 5" );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( param++, Aplicativo.iCodEmp ); // 1
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) ); // 2
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 3
			ps.setInt( param++, Aplicativo.iCodEmp ); // 4
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); // 5
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 6

			// Paramatro do saldo de descontos

			// Tipo sublanca = "D" Desconto
			/*ps.setString( param++, "D" ); // 7
			ps.setInt( param++, Aplicativo.iCodEmp ); // 4
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); // 5
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 6
*/
			// Parametros do saldo de devolu��es
			ps.setInt( param++, Aplicativo.iCodEmp ); // 7
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) ); // 8
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 9
			//
			ps.setInt( param++, Aplicativo.iCodEmp ); // 10
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPFORNECED" ) ); // 11
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 12
			}
			// Parametros do exists
			ps.setInt( param++, Aplicativo.iCodEmp ); // 13
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) ); // 14
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 15
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 16
			ps.setInt( param++, Aplicativo.iCodEmp ); // 17
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); // 18
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 19
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 20
			// Parametros do exists referente ao saldo de devolu��es
			ps.setInt( param++, Aplicativo.iCodEmp ); // 21
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) ); // 22
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 23
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 24
			//
			ps.setInt( param++, Aplicativo.iCodEmp ); // 25
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) ); // 26
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 27
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 28
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 29
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 30
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 31
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); // 32
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 33
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 34
			// Par�metros cancelamentos
			ps.setInt( param++, Aplicativo.iCodEmp ); // 35
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNPAGAR" ) ); // 36
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 37
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 38
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 39
			// Par�metros das devolu��es
			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 40
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 41
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) ); // 42
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 43
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 44

			// Parametros dos descontos
			
            // Tipo sublanca = D - Descontos
			/*ps.setString( param++, "D" ); // 4

			if ( codfor != 0 ) {
				ps.setInt( param++, codfor ); // 41
			}
			ps.setInt( param++, Aplicativo.iCodEmp ); // 42
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNSUBLANCA" ) ); // 43
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) ); // 44
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) ); // 45
*/
			System.out.println( "QUERY" + sSQL.toString() );

			rs = ps.executeQuery();

			imprimiGrafico( bVisualizar, rs, sCab.toString() );

			rs.close();
			ps.close();

			con.commit();

		} catch ( Exception err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados na tabela!\n" + err.getMessage(), true, con, err );
		}
	}

	private void imprimiGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNPAGAR" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/FRRazFor.jasper", "Relat�rio de Raz�o por fornecedor", sCab, rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio por raz�o!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );

	}
}
