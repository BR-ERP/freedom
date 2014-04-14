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

import net.sf.jasperreports.engine.JasperPrintManager;

public class FRVendasCanc extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JRadioGroup<?, ?> rgTipo = null;

	private JRadioGroup<?, ?> rgFaturados = null;

	private JRadioGroup<?, ?> rgFinanceiro = null;

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcVendedor = new ListaCampos( this );

	private ListaCampos lcTipoMov = new ListaCampos( this );

	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	public FRVendasCanc() {

		setTitulo( "Vendas canceladas e denegadas" );
		setAtribos( 80, 80, 295, 270 );

		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );

		adic( new JLabelPad( "Periodo:" ), 7, 10, 120, 20 );
		adic( new JLabelPad( "De:" ), 7, 30, 30, 20 );
		adic( txtDataini, 37, 30, 90, 20 );
		adic( new JLabelPad( "At�:" ), 140, 30, 30, 20 );
		adic( txtDatafim, 175, 30, 90, 20 );

		adic( new JLabelPad( "C�d." ), 7, 50, 70, 20 );
		adic( txtCodVend, 7, 70, 70, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 80, 50, 190, 20 );
		adic( txtNomeVend, 80, 70, 190, 20 );

		adic( new JLabelPad( "C�d." ), 7, 90, 70, 20 );
		adic( txtCodTipoMov, 7, 110, 70, 20 );
		adic( new JLabelPad( "Tipo de movimento" ), 80, 90, 190, 20 );
		adic( txtDescTipoMov, 80, 110, 190, 20 );

		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.comiss.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor, null );
		txtCodVend.setFK( true );
		txtCodVend.setNomeCampo( "CodVend" );

		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "C�d.Mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		txtCodTipoMov.setTabelaExterna( lcTipoMov, null );
		txtCodTipoMov.setNomeCampo( "CodTipoMov" );
		txtCodTipoMov.setFK( true );
		lcTipoMov.setReadOnly( true );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );

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
		String sOrdem = "";
		String sWhere1 = null;
		String sWhere2 = null;

		try {

			sSQL.append( "select v.statusvenda, v.codvenda, v.docvenda, v.dtemitvenda, v.dtsaidavenda, v.codcli, " );
			sSQL.append( "c.razcli, d.codvend, d.nomevend , v.motivocancvenda, (select sum(it.vlrliqitvenda) from vditvenda it " );
			sSQL.append( "where it.codemp = v.codemp and it.codfilial=v.codfilial and it.codvenda=v.codvenda and " );
			sSQL.append( "it.tipovenda=v.tipovenda) valorcanc " );
			sSQL.append( "from vdvenda v, vdcliente c, eqtipomov t, vdvendedor d " );
			/* Status C = Cancelada e N = Denegada */
			sSQL.append( "where substring(v.statusvenda from 1 for 1) in ('C','N') and " );
			sSQL.append( "c.codemp = v.codempcl and c.codfilial = v.codfilialcl and c.codcli=v.codcli and " );
			sSQL.append( "d.codemp = v.codempvd and d.codfilial = v.codfilialvd and d.codvend= v.codvend and " );
			sSQL.append( "t.codemp = v.codemptm and t.codfilial=v.codfilialtm and t.codtipomov=v.codtipomov and " );
			sSQL.append( "v.codemp = ? and v.codfilial = ? and " );
			sSQL.append( "v.dtemitvenda between ? and ? " );

			sCab.append( "Per�do de ");
			sCab.append( txtDataini.getVlrString());
			sCab.append( " at� " );
			sCab.append(txtDatafim.getVlrString());

			if ( txtCodTipoMov.getVlrInteger().intValue() > 0 ) {
				sCab.append(" - ");
				sCab.append( "Tipo de movimento: " );
				sCab.append( txtCodTipoMov.getVlrString());
				sCab.append(" ");
				sCab.append(txtDescTipoMov.getVlrString());
				sSQL.append( "and t.codtipomov=? " );
			}
			if ( txtCodVend.getVlrInteger().intValue() > 0 ) {
				sCab.append(" - ");
				sCab.append( "Comissionado: " );
				sCab.append( txtCodVend.getVlrString());
				sCab.append(" ");
				sCab.append(txtNomeVend.getVlrString());
				sSQL.append( "and v.codvend=? " );
			}

			sSQL.append( "order by v.dtemitvenda, v.docvenda, v.motivocancvenda " );

			System.out.println( "SQL:" + sSQL.toString() );

			int iparam = 1;

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			if ( txtCodTipoMov.getVlrInteger().intValue() > 0 ) {
				ps.setInt( iparam++, txtCodTipoMov.getVlrInteger() );
			}
			if ( txtCodVend.getVlrInteger().intValue() > 0 ) {
				ps.setInt( iparam++, txtCodVend.getVlrInteger() );
			}

			rs = ps.executeQuery();

			imprimirGrafico( bVisualizar, rs, sCab.toString() );

			con.commit();

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

	public void imprimirGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = new FPrinterJob( "layout/rel/REL_VENDAS_CANC.jasper", "Vendas Canceladas e Denegadas", sCab, rs, null, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
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
		lcVendedor.setConexao( cn );
		lcTipoMov.setConexao( cn );
	}
}
