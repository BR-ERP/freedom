/**
 * @version 24/04/2013 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.frame.report <BR>
 *         Classe: @(#)FRComprasMedia.java <BR>
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
 *         Relat�rio de m�dias de compras por item
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

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
import org.freedom.modulos.gms.view.frame.crud.special.FGrupoProd;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;

public class FRComprasMedia extends FRelatorio implements FocusListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private ListaCampos lcFor = new ListaCampos( this );

	private ListaCampos lcPlanoPag = new ListaCampos( this );

	private ListaCampos lcGrup = new ListaCampos( this );

	private ListaCampos lcProd = new ListaCampos( this );

	private Vector<String> vPesqLab = new Vector<String>();

	private Vector<String> vPesqVal = new Vector<String>();

	private Vector<String> vLabs1 = new Vector<String>();

	private Vector<String> vVals1 = new Vector<String>();

	private Vector<String> vLabs2 = new Vector<String>();

	private Vector<String> vVals2 = new Vector<String>();

	private Vector<String> vLabs3 = new Vector<String>();

	private Vector<String> vVals3 = new Vector<String>();

	private Vector<String> vLabs4 = new Vector<String>();

	private Vector<String> vVals4 = new Vector<String>();

	private Vector<String> vLabs5 = new Vector<String>();

	private Vector<String> vVals5 = new Vector<String>();

	private JRadioGroup<?, ?> rgTipoRel = null;

	private JRadioGroup<?, ?> rgFin = null;

	private JRadioGroup<?, ?> rgFiscal = null;

	private JRadioGroup<?, ?> rgAtivo = null;

	public FRComprasMedia() {

		setTitulo( "M�dia de compras por item" );
		setAtribos( 50, 10, 390, 490 );

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		txtCodFor.setTabelaExterna( lcFor, null );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		lcFor.setReadOnly( true );
		lcFor.montaSql( false, "FORNECED", "CP" );

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.plano.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false ) );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag, null );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );
		txtCodPlanoPag.setFK( true );
		lcPlanoPag.setReadOnly( true );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );

		lcGrup.add( new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, false ) );
		lcGrup.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false ) );
		txtCodGrup.setTabelaExterna( lcGrup, FGrupoProd.class.getCanonicalName() );
		txtCodGrup.setNomeCampo( "CodGrup" );
		txtCodGrup.setFK( true );
		lcGrup.setReadOnly( true );
		lcGrup.montaSql( false, "GRUPO", "EQ" );
		
		lcProd.add (new GuardaCampo( txtCodProd, "CodProd", "C�d.Prod.", ListaCampos.DB_PK, txtDescProd, false ) );
		lcProd.add (new GuardaCampo( txtDescProd, "DescProd", "DescProd.", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProd, FProduto.class.getCanonicalName() );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );	
		
		vPesqLab.addElement( "Por data emiss�o" );
		vPesqLab.addElement( "Por data entrada" );
		vPesqVal.addElement( "E" );
		vPesqVal.addElement( "D" );

		rgTipoRel = new JRadioGroup<String, String>( 1, 2, vPesqLab, vPesqVal );
		rgTipoRel.setVlrString( "D" );

		vLabs3.addElement( "Financieiro" );
		vLabs3.addElement( "N�o financeiro" );
		vLabs3.addElement( "Ambos" );
		vVals3.addElement( "F" );
		vVals3.addElement( "N" );
		vVals3.addElement( "A" );

		rgFin = new JRadioGroup<String, String>( 1, 2,  vLabs3, vVals3 );
		rgFin.setVlrString( "A" );


		vLabs4.addElement( "Fiscal" );
		vLabs4.addElement( "N�o fiscal" );
		vLabs4.addElement( "Ambos" );
		vVals4.addElement( "F" );
		vVals4.addElement( "N" );
		vVals4.addElement( "A" );

		rgFiscal = new JRadioGroup<String, String>( 1, 2,  vLabs4, vVals4 );
		rgFiscal.setVlrString( "A" );

		vLabs5.addElement( "Ativos" );
		vLabs5.addElement( "Inativos" );
		vLabs5.addElement( "Ambos" );
		vVals5.addElement( "A" );
		vVals5.addElement( "I" );
		vVals5.addElement( "B" );

		rgAtivo = new JRadioGroup<String, String>( 1, 2,  vLabs5, vVals5 );
		rgAtivo.setVlrString( "B" );

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Per�odo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 340, 45 );

		adic( new JLabelPad( "De:" ), 10, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad( "At�:" ), 152, 25, 37, 20 );
		adic( txtDatafim, 190, 25, 100, 20 );

		adic( rgTipoRel, 7, 65, 340, 30 );

		adic( new JLabelPad( "C�d.for." ), 7, 100, 80, 20 );
		adic( txtCodFor, 7, 120, 80, 20 );
		adic( new JLabelPad( "Descri��o do fornecedor" ), 90, 100, 240, 20 );
		adic( txtRazFor, 90, 120, 255, 20 );
		adic( new JLabelPad( "C�d.pl.pag." ), 7, 140, 80, 20 );
		adic( txtCodPlanoPag, 7, 160, 80, 20 );
		adic( new JLabelPad( "Descri��o do plano de pagamento" ), 90, 140, 240, 20 );
		adic( txtDescPlanoPag, 90, 160, 255, 20 );
		
		adic( new JLabelPad( "C�d.grup." ), 7, 180, 80, 20 );
		adic( txtCodGrup, 7, 200, 80, 20 );
		adic( new JLabelPad( "Descri��o do grupo" ), 90, 180, 240, 20 );
		adic( txtDescGrup, 90, 200, 255, 20 );

		adic( new JLabelPad( "C�d.prod." ), 7, 220, 80, 20 );
		adic( txtCodProd, 7, 240, 80, 20 );
		adic( new JLabelPad( "Descri��o do produto" ), 90, 220, 240, 20 );
		adic( txtDescProd, 90, 240, 255, 20 );
		
		adic( rgFin, 7, 270, 340, 30 );

		adic( rgFiscal, 7, 310, 340, 30 );

		adic( rgAtivo, 7, 350, 340, 30 );

		txtDataini.setAtivo( false );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( getDataini( txtDatafim.getVlrDate() ) );
		txtDatafim.addFocusListener( this );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcProd.setConexao( cn );
		lcGrup.setConexao( cn );
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		imprimirGrafico( bVisualizar );

	}

	public void imprimirGrafico( TYPE_PRINT bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		StringBuilder tipo = new StringBuilder();
		StringBuilder cab = new StringBuilder();
		Vector<Integer> meses = null; 

		int ultimodiadomes = getUltimoDiaDoMes( txtDatafim.getVlrDate() );
		Calendar cal = Calendar.getInstance();
		cal.setTime( txtDatafim.getVlrDate() );
		int diaatual = cal.get(Calendar.DATE);
		int iparam = 1;

		cab.append( " (Per�odo de ");
		cab.append( txtDataini.getVlrString() );
		cab.append( " at� ");
		cab.append( txtDatafim.getVlrString() );
		cab.append( " ) ");
		sql.append("select pd.codprod, pd.refprod, pd.descprod ");
		sql.append(", sum(icp01.qtditcompra) qtd01 ");
		sql.append(", sum(icp02.qtditcompra) qtd02 ");
		sql.append(", sum(icp03.qtditcompra) qtd03 ");
		sql.append(", sum(icp04.qtditcompra) qtd04 ");
		sql.append(", sum(icp05.qtditcompra) qtd05 ");
		sql.append(", sum(icp06.qtditcompra) qtd06 ");
		sql.append(", sum(icp07.qtditcompra) qtd07 ");
		sql.append(", sum(icp08.qtditcompra) qtd08 ");
		sql.append(", sum(icp09.qtditcompra) qtd09 ");
		sql.append(", sum(icp10.qtditcompra) qtd10 ");
		sql.append(", sum(icp11.qtditcompra) qtd11 ");
		sql.append(", sum(icp12.qtditcompra) qtd12 ");
		sql.append(", sum(icp12.qtditcompra) ");
		if (diaatual < ultimodiadomes) {
			sql.append(" / ");
			sql.append(ultimodiadomes);
			sql.append(" * ");
			sql.append(diaatual);
		}
		sql.append(" qtd13 ");
		sql.append("from eqproduto pd ");

		sql.append("inner join cpcompra cp on ");
		if ("E".equals( rgTipoRel.getVlrString())) {
			sql.append("cp.dtemitcompra");
			cab.append( " (Classificado por data de emiss�o)" );
		} else {
			sql.append("cp.dtentcompra");
			cab.append( " (Classificado por data de entrada)" );
		}
		sql.append(" between ? and ? ");
		sql.append(" and cp.codemp=? and cp.codfilial=? ");
		sql.append("inner join cpitcompra icp ");
		sql.append("on icp.codemppd=pd.codemp and icp.codfilialpd=pd.codfilial and icp.codprod=pd.codprod ");
		sql.append("and icp.codemp=cp.codemp and icp.codfilial=cp.codfilial and icp.codcompra=cp.codcompra ");
		
		sql.append("inner join eqtipomov tm ");
		sql.append("on tm.codemp=cp.codemptm and tm.codfilial=cp.codfilialtm and tm.codtipomov=cp.codtipomov ");

		sql.append("left outer join cpitcompra icp01 ");
		sql.append("on icp01.codemp=icp.codemp and icp01.codfilial=icp.codfilial and icp01.codcompra=icp.codcompra ");
		sql.append("and icp01.coditcompra=icp.coditcompra ");
		sql.append("and extract( month from cp.dtentcompra )=? ");

		sql.append("left outer join cpitcompra icp02 ");
		sql.append("on icp02.codemp=icp.codemp and icp02.codfilial=icp.codfilial and icp02.codcompra=icp.codcompra ");
		sql.append("and icp02.coditcompra=icp.coditcompra ");
		sql.append("and extract( month from cp.dtentcompra )=? ");

		sql.append("left outer join cpitcompra icp03 ");
		sql.append("on icp03.codemp=icp.codemp and icp03.codfilial=icp.codfilial and icp03.codcompra=icp.codcompra ");
		sql.append("and icp03.coditcompra=icp.coditcompra ");
		sql.append("and extract( month from cp.dtentcompra )=? ");

		sql.append("left outer join cpitcompra icp04 ");
		sql.append("on icp04.codemp=icp.codemp and icp04.codfilial=icp.codfilial and icp04.codcompra=icp.codcompra ");
		sql.append("and icp04.coditcompra=icp.coditcompra ");
		sql.append("and extract( month from cp.dtentcompra )=? ");

		sql.append("left outer join cpitcompra icp05 ");
		sql.append("on icp05.codemp=icp.codemp and icp05.codfilial=icp.codfilial and icp05.codcompra=icp.codcompra ");
		sql.append("and icp05.coditcompra=icp.coditcompra ");
		sql.append("and extract( month from cp.dtentcompra )=? ");

		sql.append("left outer join cpitcompra icp06 ");
		sql.append("on icp06.codemp=icp.codemp and icp06.codfilial=icp.codfilial and icp06.codcompra=icp.codcompra ");
		sql.append("and icp06.coditcompra=icp.coditcompra ");
		sql.append("and extract( month from cp.dtentcompra )=? ");

		sql.append("left outer join cpitcompra icp07 ");
		sql.append("on icp07.codemp=icp.codemp and icp07.codfilial=icp.codfilial and icp07.codcompra=icp.codcompra ");
		sql.append("and icp07.coditcompra=icp.coditcompra ");
		sql.append("and extract( month from cp.dtentcompra )=? ");

		sql.append("left outer join cpitcompra icp08 ");
		sql.append("on icp08.codemp=icp.codemp and icp08.codfilial=icp.codfilial and icp08.codcompra=icp.codcompra ");
		sql.append("and icp08.coditcompra=icp.coditcompra ");
		sql.append("and extract( month from cp.dtentcompra )=? ");

		sql.append("left outer join cpitcompra icp09 ");
		sql.append("on icp09.codemp=icp.codemp and icp09.codfilial=icp.codfilial and icp09.codcompra=icp.codcompra ");
		sql.append("and icp09.coditcompra=icp.coditcompra ");
		sql.append("and extract( month from cp.dtentcompra )=? ");

		sql.append("left outer join cpitcompra icp10 ");
		sql.append("on icp10.codemp=icp.codemp and icp10.codfilial=icp.codfilial and icp10.codcompra=icp.codcompra ");
		sql.append("and icp10.coditcompra=icp.coditcompra ");
		sql.append("and extract( month from cp.dtentcompra )=? ");

		sql.append("left outer join cpitcompra icp11 ");
		sql.append("on icp11.codemp=icp.codemp and icp11.codfilial=icp.codfilial and icp11.codcompra=icp.codcompra ");
		sql.append("and icp11.coditcompra=icp.coditcompra ");
		sql.append("and extract( month from cp.dtentcompra )=? ");

		sql.append("left outer join cpitcompra icp12 ");
		sql.append("on icp12.codemp=icp.codemp and icp12.codfilial=icp.codfilial and icp12.codcompra=icp.codcompra ");
		sql.append("and icp12.coditcompra=icp.coditcompra ");
		sql.append("and extract( month from cp.dtentcompra )=? ");


		sql.append("where pd.codemp=? and pd.codfilial=? ");
		sql.append(" and substring(cp.statuscompra from 2 for 1)<>'C' ");
		if ("A".equals( rgAtivo.getVlrString() ) ) {
			sql.append("and pd.ativoprod='S' ");
			cab.append( " ( Somente ativos ) ");
		} else if ("I".equals( rgAtivo.getVlrString() ) ) {
			sql.append("and pd.ativoprod='N' ");
			cab.append( " ( Somente inativos ) ");
		}
		if (txtCodFor.getVlrInteger().intValue()!=0) {
			sql.append( "and cp.codempfr=? and cp.codfilialr=? and cp.codfor=? " );
			cab.append( "( Fornecedor: " );
			cab.append( txtRazFor.getVlrString().trim() );
			cab.append( " ) ");
		}
		if ( txtCodPlanoPag.getVlrInteger().intValue() != 0 ) {
			sql.append( " and cp.codemppg=? and cp.codfilialpg=? and c.codplanopag=? " );
			cab.append( " ( Plano de pagamento: " );
			cab.append( txtDescPlanoPag.getVlrString().trim() );
			cab.append( " ) ");

		}
		if ( ! "".equals(txtCodGrup.getVlrString().trim()) ) {
			sql.append(" and pd.codgrup like ? " );
			cab.append(" ( Grupo: " );
			cab.append(txtDescGrup.getVlrString().trim());
			cab.append(" ) ");
		}

		if ( txtCodProd.getVlrInteger().intValue() != 0 ) {
			sql.append( " and pd.codprod=? " );
			cab.append( " ( Produto: " );
			cab.append( txtDescProd.getVlrString() );
			cab.append( " ) ");
		}
		if ("F".equals( rgFin.getVlrString() ) ) {
			sql.append(" and tm.SomaVdTipoMov='S' ");
			cab.append( " (Somente financeiros) " );
		} else if ("N".equals( rgFin.getVlrString())) {
			sql.append(" and tm.SomaVdTipoMov<>'S' ");
			cab.append( " (N�o financeiros) ");
		}

		if ("F".equals( rgFiscal.getVlrString() ) ) {
			sql.append( " AND TM.FiscalTipomov='S' ");
			cab.append( " (Somente fiscais) " );
		} else if ("N".equals( rgFiscal.getVlrString())) {
			sql.append( " AND TM.FiscalTipomov<>'S' " );
			cab.append( " (N�o fiscais) ");
		}


		sql.append("group by pd.codprod, pd.refprod, pd.descprod ");

		try {

			ps = con.prepareStatement( sql.toString() );
			// In�cio da parametriza��o da query
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "CPCOMPRA" ) );

			meses = getMeses( txtDataini.getVlrDate() );
			for (Integer mes: meses) {
				ps.setInt( iparam++, mes);
			}
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "EQPRODUTO" ) );

			if (txtCodFor.getVlrInteger().intValue()!=0) {
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "CPFORNECED" ) );
				ps.setInt( iparam++, txtCodFor.getVlrInteger() );
			}
			if ( txtCodPlanoPag.getVlrInteger().intValue() != 0 ) {
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNPLANOPAG" ) );
				ps.setInt( iparam++, txtCodPlanoPag.getVlrInteger() );
			}
			if ( !"".equals(txtCodGrup.getVlrString().trim()) ) {
				ps.setString( iparam++, txtCodGrup.getVlrString().trim()+'%' );
			}
			if ( txtCodProd.getVlrInteger().intValue() != 0 ) {
				ps.setInt( iparam++, txtCodProd.getVlrInteger() );
			}

			rs = ps.executeQuery();

		} catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, " Erro na consulta da tabela de compras" );
		}

		imprimiGrafico( rs, bVisualizar, cab.toString(), meses );
	}

	private int getUltimoDiaDoMes(Date dta) {
		int result = 31;
		Calendar cal = Calendar.getInstance();
		cal.setTime( dta );
		cal.add( Calendar.MONTH, 1 );
		cal.set( Calendar.DATE, 1 );
		cal.add( Calendar.DATE, -1 );
		result = cal.get( Calendar.DATE );
		return result;
	}
	
	private Vector<Integer> getMeses(Date dtini) {
		Vector<Integer> result = new Vector<Integer>();
		Calendar cal = Calendar.getInstance();
		cal.setTime( dtini );
		for (int i=1; i<=12; i++) {
			result.addElement( new Integer(cal.get( Calendar.MONTH ) +1 ) );
			cal.add( Calendar.MONTH, 1 );
		}
		return result;
	}

	private void imprimiGrafico( final ResultSet rs, final TYPE_PRINT bVisualizar, final String sCab, final Vector<Integer> meses ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );
		hParam.put( "MESES", meses );

		dlGr = new FPrinterJob( "relatorios/compras_media.jasper", "Relat�rio de m�dia de compras por item", sCab, rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio m�dia de compras por item!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void focusGained( FocusEvent e ) {

	}

	public void focusLost( FocusEvent e ) {
		if (e.getSource()==txtDatafim) {
			txtDataini.setVlrDate( getDataini(txtDatafim.getVlrDate()) );
		}

	}

	private Date getDataini(Date dtfim) {
		Calendar result = Calendar.getInstance();
		result.setTime( dtfim );
		int dia = result.get( Calendar.DAY_OF_MONTH );
		int mes = result.get( Calendar.MONTH );
		int ano = result.get( Calendar.YEAR );
		if ( mes ==11) {
			mes = 0;
		} else {
			mes ++;
			ano --;
		}
		dia = 1;
		result.set( ano, mes, dia, 0, 0, 0 );
		return result.getTime();
	}
}
