/**
 * @version 05/12/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.atd <BR>
 *         Classe: @(#)FRTermReceb.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Coment�rios sobre a classe...
 * 
 */
package org.freedom.modulos.atd.view.frame.report;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrintManager;

public class FRTermReceb extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private ListaCampos lcOrc = new ListaCampos( this, "" );

	private JTextFieldPad txtCodOrc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDtOrc = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtVencOrc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	public FRTermReceb() {

		setTitulo( "Termo de Recebimento" );
		setAtribos( 30, 20, 400, 300 );

		montaTela();
		montaListaCampos();

	}

	private void montaTela() {

		adic( new JLabelPad( "Num.Orc" ), 7, 5, 70, 20 );
		adic( txtCodOrc, 7, 25, 70, 20 );
		adic( new JLabelPad( "Dt.emiss�o" ), 80, 5, 90, 20 );
		adic( txtDtOrc, 80, 25, 90, 20 );
		// adic( new JLabelPad("Item or�."), 173, 5, 70, 20 );
		// adic(txtCodItOrc, 173, 25, 70, 20 );
		// adic( new JLabelPad("Cod.Prod"), 7, 45, 70, 20 );
		// adic( txtCodProd, 7, 65, 70, 20 );
		// adic( new JLabelPad("Descri��o do produto"), 80, 45, 290, 20 );
		// adic(txtDescProd, 80, 65, 290, 20 );

	}

	private void montaListaCampos() {

		lcOrc.add( new GuardaCampo( txtCodOrc, "CodOrc", "C�d.Or�.", ListaCampos.DB_PK, false ) );
		lcOrc.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtCodConv, "CodConv", "C�d.conv.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtDtOrc, "DtOrc", "Dt.emiss�o", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtDtVencOrc, "DtVencOrc", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_SI, false ) );
		lcOrc.montaSql( false, "ORCAMENTO", "VD" );
		// lcOrc.setQueryCommit( true );
		lcOrc.setReadOnly( true );
		txtCodOrc.setTabelaExterna( lcOrc, null );
		txtCodOrc.setFK( true );
		txtCodOrc.setNomeCampo( "CODORC" );

	}

	@ Override
	public void imprimir( boolean b ) {

		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuffer sql = new StringBuffer();
		HashMap<String, Object> hParam = new HashMap<String, Object>();
		Date dtRec = new Date();
		if ( txtCodOrc.getVlrInteger() == 0 ) {
			Funcoes.mensagemInforma( this, "Selecione um or�amento!" );
			return;
		}

		sql.append( "SELECT O.CODEMP, O.CODFILIAL, O.CODORC, CV.NOMECONV, CV.RGCONV, " );
		sql.append( "P2.CABTERMR01 , P2.CABTERMR02, P2.RODTERMR, IO.NUMAUTORIZORC, P.DESCPROD, " );
		sql.append( "E.FOTOEMP, O.DTORC, E.CIDEMP, VD.NOMEVEND " );
		sql.append( "FROM VDVENDEDOR VD,VDITORCAMENTO IO, ATCONVENIADO CV, SGEMPRESA E, EQPRODUTO P, VDORCAMENTO O " );
		sql.append( "LEFT OUTER JOIN SGPREFERE2 P2 ON " );
		sql.append( "P2.CODEMP=O.CODEMP AND P2.CODFILIAL=? " );
		sql.append( "WHERE O.CODEMP=? AND O.CODFILIAL=? AND O.CODORC=? AND " );
		sql.append( "IO.CODEMP=O.CODEMP AND IO.CODFILIAL=O.CODFILIAL AND " );
		sql.append( "IO.CODORC=O.CODORC AND IO.SITENTITORC='N' AND " );
		sql.append( "IO.SITTERMRITORC='E' AND CV.CODEMP=O.CODEMPCV AND " );
		sql.append( "CV.CODFILIAL=O.CODFILIALCV AND CV.CODCONV=O.CODCONV AND " );
		sql.append( "P.CODEMP=IO.CODEMPPD AND P.CODFILIAL=IO.CODFILIALPD AND P.CODPROD=IO.CODPROD AND " );
		sql.append( "E.CODEMP=P2.CODEMP " );
		sql.append( "AND VD.CODEMP=O.CODEMPVD AND VD.CODFILIAL=O.CODFILIALVD AND VD.CODVEND=O.CODVEND" );

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODORC", txtCodOrc.getVlrInteger() );

		hParam.put( "DATAIMP", Funcoes.getDiaMes( dtRec ) + " DE " + Funcoes.getMesExtenso( dtRec ) + " DE " + Funcoes.getAno( dtRec ) + "." );

		try {
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, ListaCampos.getMasterFilial( "SGPREFERE2" ) );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
			ps.setInt( 4, txtCodOrc.getVlrInteger() );
			rs = ps.executeQuery();
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Ocorreu um erro executando a consulta.\n" + e.getMessage() );
			return;
		}

		FPrinterJob dlGr = new FPrinterJob( "relatorios/TermReceb.jasper", "TERMO DE RECEBIMENTO", null, rs, hParam, this );

		if ( b ) {
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

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcOrc.setConexao( cn );
	}

}
