/**
 * @version 7/08/2008 <BR>
 * @author Setpoint Inform�tica Ltda.
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.pcp <BR>
 *         Classe:
 * @(#)FRContraProva.java <BR>
 * 
 *                        Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                        modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                        na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                        Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                        sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                        Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                        Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                        de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                        Tela para cadastro de estruturas de produtos.
 * 
 */

package org.freedom.modulos.pcp.view.frame.report;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

public class FRContraProva extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtRefProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private ListaCampos lcProd = new ListaCampos( this, "" );

	private Vector<String> vLabs1 = new Vector<String>();

	private Vector<String> vVals1 = new Vector<String>();

	private JRadioGroup<?, ?> rgTipo = null;

	public FRContraProva() {

		super( false );
		setTitulo( "Contra-Provas" );
		setAtribos( 50, 50, 350, 230 );

		montaTela();
		montaListaCampos();

	}

	private void montaTela() {

		vLabs1.addElement( "Validade" );
		vLabs1.addElement( "Reten��o" );
		vLabs1.addElement( "Descarte" );
		vVals1.addElement( "V" );
		vVals1.addElement( "R" );
		vVals1.addElement( "D" );

		rgTipo = new JRadioGroup<String, String>( 1, 3, vLabs1, vVals1 );
		rgTipo.setVlrString( "T" );

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Per�odo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 300, 45 );

		adic( new JLabelPad( "De:" ), 10, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad( "At�:" ), 152, 25, 37, 20 );
		adic( txtDatafim, 190, 25, 100, 20 );
		adic( rgTipo, 5, 65, 300, 35 );
		adic( new JLabelPad( "C�d.Prod" ), 7, 105, 80, 20 );
		adic( txtCodProd, 7, 125, 70, 20 );
		adic( new JLabelPad( "Descri��o do produto" ), 83, 105, 200, 20 );
		adic( txtDescProd, 83, 125, 220, 20 );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	private void montaListaCampos() {

		/**************
		 * Produto *
		 **************/

		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Refer�ncia do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProd, null );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );

	}

	public void imprimir( boolean bVisualizar ) {

		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer data = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();

		try {

			if ( txtCodProd.getVlrInteger() > 0 ) {

				sWhere.append( "and ep.codprod= " + txtCodProd.getVlrString() );
			}

			if ( "V".equals( rgTipo.getVlrString() ) ) {

				data.append( "el.venctolote " );
				sCab.append( "Filtrado por Vencimento: " + txtDataini.getVlrString() + " at� " + txtDatafim.getVlrString() );
			}
			else if ( "R".equals( rgTipo.getVlrString() ) ) {

				data.append( "pr.dtretencao " );
				sCab.append( "Filtrado por Reten��o: " + txtDataini.getVlrString() + " at� " + txtDatafim.getVlrString() );
			}
			else if ( "D".equals( rgTipo.getVlrString() ) ) {

				data.append( "pr.dtdescarte " );
				sCab.append( "Filtrado por Descarte: " + txtDataini.getVlrString() + " at� " + txtDatafim.getVlrString() );
			}

			sql.append( "select op.codprod, pr.codop, ep.descprod, pr.seqop,  pr.dtretencao, pr.dtdescarte, " );
			sql.append( "el.codlote, el.venctolote " );
			sql.append( "from ppretcp pr, ppop op, eqproduto ep, eqlote el " );
			sql.append( "where " );
			sql.append( "op.codop=pr.codop and op.seqop=pr.seqop " );
			sql.append( "and ep.codprod=op.codprod and el.codemp=op.codemple and el.codfilial=op.codfilialle " );
			sql.append( "and el.codlote=op.codlote " );
			sql.append( "and pr.codemp=? and pr.codfilial=? and " + data.toString() + " between ? and ?" );
			sql.append( sWhere.toString() );

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ppop" ) );
			ps.setDate( 3, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( 4, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			rs = ps.executeQuery();

			imprimiGrafico( rs, bVisualizar, sCab.toString() );

		} catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados de Contra-Provas" );
		}
	}

	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/RelContraProva.jasper", "Relat�rio de Contra-Provas", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de Relat�rio de Contra-Provas!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection con ) {

		super.setConexao( con );
		lcProd.setConexao( con );
	}
}
