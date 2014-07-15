/**
 * @version 16/01/2008 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FRVendCliProd.java <BR>
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
 *                        Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
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

public class FRVendasCliProd extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtCodComiss = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeComiss = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private Vector<String> vLabs = new Vector<String>();

	private Vector<String> vVals = new Vector<String>();

	private JRadioGroup<String, String> rgTipo = null;
	
	private Vector<String> vLabsRel = new Vector<String>();

	private Vector<String> vValsRel = new Vector<String>();

	private JRadioGroup<String, String> rgTipoDeRelatorio = null;

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private ListaCampos lcComiss = new ListaCampos( this, "VD" );

	private ListaCampos lcTipoCli = new ListaCampos( this );
	
	private JCheckBoxPad cbObsItVenda = new JCheckBoxPad( "Imprimir Obs./�tens", "S", "N" );
	
	private JCheckBoxPad cbAgrupcli = new JCheckBoxPad( "Utilizar grupo de clientes", "S", "N" );
	
	public FRVendasCliProd() {

		super( false );
		setTitulo( "Ultimas Vendas de Cliente/Produto" );
		setAtribos( 50, 50, 355, 390 );

		montaRadioGrupo();
		montaListaCampos();
		montaTela();
	}

	private void montaRadioGrupo() {

		vLabs.addElement( "Texto" );
		vLabs.addElement( "Grafico" );
		vVals.addElement( "T" );
		vVals.addElement( "G" );

		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgTipo.setVlrString( "G" );
		
		vLabsRel.addElement( "Retrato" );
		vLabsRel.addElement( "Paisagem" );
		vValsRel.addElement( "relatorios/UltVendCli.jasper" );
		vValsRel.addElement( "relatorios/UltVendCli_landscape.jasper" );

		rgTipoDeRelatorio = new JRadioGroup<String, String>( 1, 2, vLabsRel, vValsRel );
		rgTipoDeRelatorio.setVlrString( "relatorios/UltVendCli.jasper" );
		
	}

	private void montaListaCampos() {

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCli, null );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );

		lcComiss.add( new GuardaCampo( txtCodComiss, "CodVend", "C�d.comiss.", ListaCampos.DB_PK, false ) );
		lcComiss.add( new GuardaCampo( txtNomeComiss, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		txtCodComiss.setTabelaExterna( lcComiss, null );
		txtCodComiss.setNomeCampo( "CodVend" );
		txtCodComiss.setFK( true );
		lcComiss.setReadOnly( true );
		lcComiss.montaSql( false, "VENDEDOR", "VD" );

		lcTipoCli.add( new GuardaCampo( txtCodTipoCli, "CodTipoCli", "C�d.tp.cli.", ListaCampos.DB_PK, false ) );
		lcTipoCli.add( new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descri��o do tipo de cliente", ListaCampos.DB_SI, false ) );
		txtCodTipoCli.setTabelaExterna( lcTipoCli, null );
		txtCodTipoCli.setNomeCampo( "CodTipoCli" );
		txtCodTipoCli.setFK( true );
		lcTipoCli.setReadOnly( true );
		lcTipoCli.montaSql( false, "TIPOCLI", "VD" );

	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Periodo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 15, 5, 80, 20 );
		adic( lbLinha, 7, 15, 320, 45 );

		adic( new JLabelPad( "De:", SwingConstants.CENTER ), 17, 30, 40, 20 );

		adic( txtDataini, 57, 30, 100, 20 );

		adic( new JLabelPad( "At�:", SwingConstants.CENTER ), 157, 30, 45, 20 );
		adic( txtDatafim, 202, 30, 100, 20 );

		adic( new JLabelPad( "C�d.Tp.Cliente" ), 7, 70, 90, 20 );
		adic( txtCodTipoCli, 7, 90, 90, 20 );
		adic( new JLabelPad( "Descri��o do tipo de cliente" ), 100, 70, 227, 20 );
		adic( txtDescTipoCli, 100, 90, 227, 20 );

		adic( new JLabelPad( "C�d.Cliente" ), 7, 110, 90, 20 );
		adic( txtCodCli, 7, 130, 90, 20 );
		adic( new JLabelPad( "Raz�o social do cliente" ), 100, 110, 227, 20 );
		adic( txtRazCli, 100, 130, 227, 20 );

		adic( cbAgrupcli, 7, 150, 200, 20);
		adic( new JLabelPad( "C�d.Comiss." ), 7, 170, 90, 20 );
		adic( txtCodComiss, 7, 190, 90, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 100, 170, 227, 20 );
		adic( txtNomeComiss, 100, 190, 227, 20 );

		adic( rgTipo, 7, 215, 320, 30 );
		
		adic( rgTipoDeRelatorio, 7, 255, 320, 30 );
		
		adic( cbObsItVenda,  	7,	295, 	200, 	20 );
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
		cbAgrupcli.setVlrString( "N" );
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		StringBuffer cab = new StringBuffer();
		cab.append( "Per�odo de : " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) + " At� : " + Funcoes.dateToStrDate( txtDatafim.getVlrDate() ) );
		if ("S".equals(cbAgrupcli.getVlrString())) {
			cab.append(" - Clientes de mesmo grupo");
		}
		try {
			sql.append( "select razcli_ret razcli, codcli_ret codcli, ");
			if( "N".equals( cbObsItVenda.getVlrString() )) {
				sql.append( "descprod_ret descprod, ");
			}
			else {
				sql.append( "coalesce(obsitvenda_ret,descprod_ret) as descprod, ");
			}
			sql.append( "codprod_ret codprod, " );
			sql.append( "dtemitvenda_ret dtemitvenda, docvenda_ret docvenda, serie_ret serie");
			sql.append(", precovenda_ret precovenda, refprod_ret refprod, qtdprod_ret qtditvenda " );
			sql.append( "from vdretultvdcliprod (?,?,?,?,?,?,?,?,?,?) " );

			ps = con.prepareStatement( sql.toString() );

			int param = 1;
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, lcCli.getCodFilial() );

			if ( txtRazCli.getVlrString().trim().length() > 0 ) {
				ps.setInt( param++, txtCodCli.getVlrInteger() );
			}
			else {
				ps.setNull( param++, Types.INTEGER );
			}

			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDEDOR" ) );

			if ( txtNomeComiss.getVlrString().trim().length() > 0 ) {
				ps.setInt( param++, txtCodComiss.getVlrInteger() );
			}
			else {
				ps.setNull( param++, Types.INTEGER );
			}
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( param++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			ps.setInt( param++, lcTipoCli.getCodFilial() );
			if ( txtDescTipoCli.getVlrString().trim().length() > 0 ) {
				ps.setInt( param++, txtCodTipoCli.getVlrInteger() );
			}
			else {
				ps.setNull( param++, Types.INTEGER );
			}
			ps.setString( param++, cbAgrupcli.getVlrString() );
			rs = ps.executeQuery();

			if ( "G".equals( rgTipo.getVlrString() ) ) {
				imprimiGrafico( bVisualizar, rs, cab.toString() );
			}
			else {
				imprimiTexto( bVisualizar, rs );
			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemInforma( this, "Erro ao buscar dados da venda!" );
		}
	}

	public void imprimiTexto( final TYPE_PRINT bVisualizar, final ResultSet rs ) {

		PreparedStatement ps = null;
		StringBuffer sSQL = new StringBuffer();
		ImprimeOS imp = null;
		int linPag = 0;
		String sLinFina = StringFunctions.replicate( "-", 133 );
		String sLinDupla = StringFunctions.replicate( "=", 133 );
		int codcli = -1;
		boolean printCliente = true;

		try {
 
			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.verifLinPag();
			imp.montaCab();
			imp.setTitulo( "Relat�rio de Vendas por cliente/produto" );
			imp.addSubTitulo( "PERIODO DE :" + txtDataini.getVlrString() + " AT�: " + txtDatafim.getVlrString() );
			imp.limpaPags();

			while ( rs.next() ) {

				if ( imp.pRow() >= linPag - 1 ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + sLinFina + "+" );
					imp.incPags();
					imp.eject();
				}

				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, true );

					imp.pulaLinha( 0, imp.comprimido() );
					imp.say( 0, "|" + sLinDupla + "|" );

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 3, "CLIENTE" );
					imp.say( 44, "|" );
					imp.say( 46, "PRODUTO" );
					imp.say( 88, "|" );
					imp.say( 93, "VLR. UNIT." );
					imp.say( 107, "|" );
					imp.say( 108, "DT.UT.COMPRA" );
					imp.say( 120, "|" );
					imp.say( 122, "DOC." );
					imp.say( 135, "|" );

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinDupla + "|" );
				}

				if ( codcli == rs.getInt( "CODCLI" ) ) {
					printCliente = false;
				}
				else {
					if ( codcli != -1 ) { // -1 � o valor default, isso n�o imprime na primeira vez.
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" + sLinFina + "|" );
					}
					codcli = rs.getInt( "CODCLI" );
					printCliente = true;
				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );

				if ( printCliente ) {
					imp.say( 3, Funcoes.copy( rs.getString( "RAZCLI" ).trim(), 0, 40 ) );
				}

				imp.say( 44, "|" );
				imp.say( 46, Funcoes.copy( rs.getString( "DESCPROD" ).trim(), 0, 41 ) );
				imp.say( 88, "|" );
				imp.say( 90, rs.getBigDecimal( "PRECOVENDA" ) != null ? Funcoes.strDecimalToStrCurrency( 16, Aplicativo.casasDecPre, String.valueOf( rs.getBigDecimal( "PRECOVENDA" ) ) ) : "" );
				imp.say( 107, "|" );
				imp.say( 109, rs.getDate( "DTEMITVENDA" ) != null ? StringFunctions.sqlDateToStrDate( rs.getDate( "DTEMITVENDA" ) ) : "" );
				imp.say( 120, "|" );
				imp.say( 122, rs.getString( "DOCVENDA" ) != null ? Funcoes.copy( rs.getString( "DOCVENDA" ), 0, 12 ) : "" );
				imp.say( 135, "|" );

			}

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + sLinFina + "+" );
			imp.eject();
			imp.fechaGravacao();

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao imprmir relat�rio texto!\n" + e.getMessage(), true, con, e );
		}

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	private void imprimiGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "VDVENDA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( rgTipoDeRelatorio.getVlrString(), "Ultimas Vendas por Cliente/Produto", sCab, rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.preview();
		}
		else {
			try {
				dlGr.print(true);
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de Cliente por Produto!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcComiss.setConexao( cn );
		lcTipoCli.setConexao( cn );

	}
}
