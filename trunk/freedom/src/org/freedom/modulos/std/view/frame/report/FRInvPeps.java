/**
 * @version 03/08/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FRInvPeps.java <BR>
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

package org.freedom.modulos.std.view.frame.report;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

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

public class FRInvPeps extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private final int TAM_GRUPO = 14;

	private JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtPagina = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );

	private JTextFieldPad txtCodAlmox = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescAlmox = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodMarca = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );

	private JTextFieldFK txtDescMarca = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtSiglaMarca = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, TAM_GRUPO, 0 );

	private JTextFieldFK txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JCheckBoxPad cbComEstoq = new JCheckBoxPad( "Imprimir somento produtos com estoque ?", "S", "N" );

	private JCheckBoxPad cbRelTexto = new JCheckBoxPad( "Relat�rio texto ?", "S", "N" );

	private JCheckBoxPad cbAtivos = new JCheckBoxPad( "Somente ativos?", "S", "N" );

	private ListaCampos lcAlmox = new ListaCampos( this );

	private ListaCampos lcGrup = new ListaCampos( this );

	private ListaCampos lcMarca = new ListaCampos( this );

	private JLabelPad lbCodAlmox = new JLabelPad( "C�d.almox." );

	private JLabelPad lbCodMarca = new JLabelPad( "C�d.marca" );

	private JLabelPad lbCodGrup = new JLabelPad( "C�d.grupo" );

	private JLabelPad lbDescAlmox = new JLabelPad( "Descri��o do almoxarifado" );

	private JLabelPad lbDescMarca = new JLabelPad( "Descri��o da marca" );

	private JLabelPad lbDescGrup = new JLabelPad( "Descri��o do grupo" );

	private JRadioGroup<?, ?> rgOrdem = null;

	private Vector<String> vDesc = new Vector<String>();

	private Vector<String> vOpc = new Vector<String>();

	private JRadioGroup<?, ?> rgCusto = null;

	private Vector<String> vDescCusto = new Vector<String>();

	private Vector<String> vOpcCusto = new Vector<String>();

	private boolean[] bPrefs = null;

	public FRInvPeps() {

		setTitulo( "Invent�rio" );
		setAtribos( 80, 30, 400, 380 );

		GregorianCalendar cal = new GregorianCalendar();
		cal.add( Calendar.DATE, 0 );
		txtData.setVlrDate( cal.getTime() );
		txtPagina.setVlrInteger( new Integer( 1 ) );

		vDesc.addElement( "Descri��o" );
		vDesc.addElement( "C�digo" );
		vOpc.addElement( "D" );
		vOpc.addElement( "C" );
		rgOrdem = new JRadioGroup<String, String>( 2, 1, vDesc, vOpc );

		vDescCusto.addElement( "C.PEPS" );
		vDescCusto.addElement( "C.MPM" );
		vDescCusto.addElement( "P.BASE" );
		vOpcCusto.addElement( "P" );
		vOpcCusto.addElement( "M" );
		vOpcCusto.addElement( "B" );

		rgCusto = new JRadioGroup<String, String>( 1, 3, vDescCusto, vOpcCusto );

		cbComEstoq.setVlrString( "N" );
		cbRelTexto.setVlrString( "N" );
		cbAtivos.setVlrString( "S" );

		lcAlmox.add( new GuardaCampo( txtCodAlmox, "CodAlmox", "C�d.almox.", ListaCampos.DB_PK, false ) );
		lcAlmox.add( new GuardaCampo( txtDescAlmox, "DescAlmox", "Descri��o do almox.", ListaCampos.DB_SI, false ) );
		txtCodAlmox.setTabelaExterna( lcAlmox, null );
		txtCodAlmox.setNomeCampo( "CodAlmox" );
		txtCodAlmox.setFK( true );
		lcAlmox.setReadOnly( true );
		lcAlmox.montaSql( false, "ALMOX", "EQ" );

		lcMarca.add( new GuardaCampo( txtCodMarca, "CodMarca", "C�d.marca", ListaCampos.DB_PK, false ) );
		lcMarca.add( new GuardaCampo( txtDescMarca, "DescMarca", "Descri��o da marca", ListaCampos.DB_SI, false ) );
		lcMarca.add( new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI, false ) );
		lcMarca.montaSql( false, "MARCA", "EQ" );
		lcMarca.setReadOnly( true );
		txtCodMarca.setTabelaExterna( lcMarca, null );
		txtCodMarca.setFK( true );
		txtCodMarca.setNomeCampo( "CodMarca" );

		lcGrup.add( new GuardaCampo( txtCodGrup, "CodGrup", "C�d.gurpo", ListaCampos.DB_PK, false ) );
		lcGrup.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false ) );
		lcGrup.montaSql( false, "GRUPO", "EQ" );
		lcGrup.setReadOnly( true );
		txtCodGrup.setTabelaExterna( lcGrup, null );
		txtCodGrup.setFK( true );
		txtCodGrup.setNomeCampo( "CodGrup" );

		adic( new JLabelPad( "Ordem:" ), 7, 0, 100, 20 );
		adic( rgOrdem, 7, 20, 100, 60 );
		adic( new JLabelPad( "Estoque de:" ), 117, 0, 100, 20 );
		adic( txtData, 117, 20, 100, 20 );
		adic( new JLabelPad( "P�gina inicial:" ), 117, 40, 100, 20 );
		adic( txtPagina, 117, 60, 100, 20 );
		adic( lbCodAlmox, 7, 80, 250, 20 );
		adic( txtCodAlmox, 7, 100, 80, 20 );
		adic( lbDescAlmox, 90, 80, 250, 20 );
		adic( txtDescAlmox, 90, 100, 250, 20 );
		adic( lbCodMarca, 7, 120, 250, 20 );
		adic( txtCodMarca, 7, 140, 80, 20 );
		adic( lbDescMarca, 90, 120, 250, 20 );
		adic( txtDescMarca, 90, 140, 250, 20 );
		adic( lbCodGrup, 7, 160, 250, 20 );
		adic( txtCodGrup, 7, 180, 80, 20 );
		adic( lbDescGrup, 90, 160, 250, 20 );
		adic( txtDescGrup, 90, 180, 250, 20 );
		adic( cbComEstoq, 7, 200, 300, 20 );
		adic( cbRelTexto, 7, 220, 300, 20 );
		adic( cbAtivos, 7, 240, 250, 20 );
		adic( rgCusto, 7, 270, 250, 30 );

	}

	public void imprimirTexto(TYPE_PRINT bVisualizar, ResultSet rs, StringBuilder filtros, String campocodigo) throws SQLException {
		BigDecimal bdCustoTot = new BigDecimal(0);
		double deSldProd = 0;
		int linPag = 0;
		ImprimeOS imp = new ImprimeOS( "", con );
		imp.limpaPags();
		imp.montaCab( txtPagina.getVlrInteger().intValue() - 1 );
		imp.setTitulo( "Relatorio de invent�rio de estoque" );
		imp.addSubTitulo( filtros.toString() );
		linPag = imp.verifLinPag() - 1;

		//imp.addSubTitulo( sFiltros2 );

		while ( rs.next() ) {
			if ( imp.pRow() >= ( linPag - 1 ) ) {
				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );
				imp.incPags();
				imp.eject();

			}
			if ( imp.pRow() == 0 ) {
				imp.impCab( 136, true );
				/*
				 * imp.say(imp.pRow()+0,0,"|"+StringFunctions.replicate("-",133)+"|"); imp.say(imp.pRow()+1,0,""+imp.comprimido()); imp.say(imp.pRow()+0,0,"| CODIGO"); imp.say(imp.pRow()+0,16,"| DESCRICAO "); imp.say(imp.pRow()+0,70,"| SALDO"); imp.say(imp.pRow()+0,83,"| CUSTO UNIT.");
				 * imp.say(imp.pRow()+0,101,"| CUSTO TOTAL"); imp.say(imp.pRow()+0,135,"|"); imp.say(imp.pRow()+1,0,""+imp.comprimido()); imp.say(imp.pRow()+0,0,"|"+StringFunctions.replicate("-",133)+"|");
				 */

				imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "| CODIGO" );
				imp.say( imp.pRow() + 0, 16, "| COD.FAB. " );
				imp.say( imp.pRow() + 0, 29, "| COD.BAR. " );
				imp.say( imp.pRow() + 0, 43, "| DESCRICAO " );
				imp.say( imp.pRow() + 0, 95, "| SALDO" );// 19
				imp.say( imp.pRow() + 0, 106, "| CUSTO UNIT." ); // 20
				imp.say( imp.pRow() + 0, 118, "| CUSTO TOTAL" ); // 15
				imp.say( imp.pRow() + 0, 135, "|" );
				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );

			}
			/*
			 * imp.say(imp.pRow()+1,0,""+imp.comprimido()); imp.say(imp.pRow()+0,0,"|"+Funcoes.adicEspacosEsquerda(rs.getString(sCpCodigo).trim(),13)); imp.say(imp.pRow()+0,16,"| "+Funcoes.adicionaEspacos(rs.getString("DESCPROD"),50));
			 * imp.say(imp.pRow()+0,70,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("SLDPROD")+"",10)); imp.say(imp.pRow()+0,83,"|"+Funcoes.strDecimalToStrCurrency(15,2,rs.getDouble("CUSTOUNIT")+""));
			 * imp.say(imp.pRow()+0,101,"|"+Funcoes.strDecimalToStrCurrency(15,2,rs.getDouble("CUSTOTOT")+"")); imp.say(imp.pRow()+0,135,"|");
			 */

			imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
			imp.say( imp.pRow() + 0, 0, "|" + Funcoes.adicEspacosEsquerda( rs.getString( campocodigo ).trim(), 13 ) );
			imp.say( imp.pRow() + 0, 16, "|" + Funcoes.adicEspacosEsquerda( rs.getString( "CODFABPROD" ).trim(), 11 ) );
			imp.say( imp.pRow() + 0, 29, "|" + Funcoes.adicEspacosEsquerda( rs.getString( "CODBARPROD" ).trim(), 12 ) );
			imp.say( imp.pRow() + 0, 43, "| " + Funcoes.adicionaEspacos( rs.getString( "DESCPROD" ), 49 ) );
			imp.say( imp.pRow() + 0, 95, "|" + Funcoes.adicEspacosEsquerda( rs.getDouble( "SLDPROD" ) + "", 8 ) );// 19
			imp.say( imp.pRow() + 0, 106, "|" + Funcoes.strDecimalToStrCurrency( 11, 2, rs.getDouble( "CUSTOUNIT" ) + "" ) ); // 18
			imp.say( imp.pRow() + 0, 119, "|" + Funcoes.strDecimalToStrCurrency( 14, 2, rs.getDouble( "CUSTOTOT" ) + "" ) ); // 17
			imp.say( imp.pRow() + 0, 135, "|" );

			deSldProd += rs.getDouble( "SLDPROD" );
			BigDecimal custo = rs.getBigDecimal( "CUSTOTOT" ); 
//			bdCustoTot = bdCustoTot.add( custo.setScale( Aplicativo.casasDecFin ) );
			bdCustoTot = bdCustoTot.add( custo.setScale( Aplicativo.casasDecFin, BigDecimal.ROUND_FLOOR ) );

		}

		rs.close();
		con.commit();
		/*
		 * imp.say(imp.pRow()+1,0,""+imp.comprimido()); imp.say(imp.pRow()+0,0,"|"+StringFunctions.replicate("-",133)+"|"); imp.say(imp.pRow()+1,0,""+imp.comprimido()); imp.say(imp.pRow()+0,0,"| TOTAL");
		 * imp.say(imp.pRow()+0,70,"|"+Funcoes.adicEspacosEsquerda(Funcoes.arredDouble(deSldProd,2)+"",10)); imp.say(imp.pRow()+0,83,"|"); imp.say(imp.pRow()+0,101,"|"+Funcoes.strDecimalToStrCurrency(15,2,deCustoTot+"")); imp.say(imp.pRow()+0,135,"|");
		 * imp.say(imp.pRow()+1,0,""+imp.comprimido()); imp.say(imp.pRow()+0,0,"+"+StringFunctions.replicate("-",133)+"+");
		 */

		imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
		imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
		imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
		imp.say( imp.pRow() + 0, 0, "| TOTAL" );
		imp.say( imp.pRow() + 0, 95, "|" + Funcoes.adicEspacosEsquerda( Funcoes.arredDouble( deSldProd, 2 ) + "", 8 ) );// 19
		imp.say( imp.pRow() + 0, 106, "|" ); // 18
		imp.say( imp.pRow() + 0, 119, "|" + Funcoes.strDecimalToStrCurrency( 14, 2, bdCustoTot + "" ) );// 17
		imp.say( imp.pRow() + 0, 135, "|" );
		imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
		imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );

		imp.eject();
		imp.fechaGravacao();
		
		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}

	}

	public void imprimirGrafico(TYPE_PRINT bVisualizar, ResultSet rs, StringBuilder filtros) {
		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNITPAGAR" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", filtros.toString() );

		dlGr = new FPrinterJob( "layout/rel/FRInvPeps.jasper", "Relat�rio de invent�rio", filtros.toString(), rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de invent�rio!" + err.getMessage(), true, con, err );
			}
		}

	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		StringBuilder filtros = new StringBuilder();
		String campocodigo = "";
		String comestoque = "";
		String codmarca = "";
		String codgrup = "";
		String imprimetexto = "";
		/*String sSql = "";
		String sSemEstoq = "";
		String sCodMarca = "";
		String sCodGrup = "";
		String sFiltros1 = "";
		String sFiltros2 = ""; */
		int codalmox = 0;
//		ImprimeOS imp = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			campocodigo = ( bPrefs[ 0 ] ? "REFPROD" : "CODPROD" );
			codalmox = txtCodAlmox.getVlrInteger().intValue();
			comestoque = cbComEstoq.getVlrString();
			codmarca = txtCodMarca.getVlrString().trim();
			codgrup = txtCodGrup.getVlrString().trim();
			
			sql.append("select ");
			sql.append( "codprod, refprod ");
			sql.append(",descprod, sldprod, custounit, custotot ");
			sql.append(",coalesce(codfabprod,0) codfabprod, coalesce(codbarprod,0) codbarprod, ativoprod ");
			sql.append(" from eqrelpepssp(?,?,?,?,?,?,?,?,?,?,?,?,?,'N',? ) ");
			sql.append(" where ativoprod in ");

			if ( cbAtivos.getVlrString().equals( "S" ) ) {
				sql.append( " ('S') " );
			} else {
				sql.append( " ('S','N') " );
			}
			sql.append(" order by " );
			if ("D".equals(rgOrdem.getVlrString())) {
				sql.append(" descprod ");
			} else {
				sql.append(campocodigo);
			}
			
//			System.out.println( sql.toString() );
			try {
				if ( comestoque.equals( "S" ) )
					filtros.append(  " ( SOMENTE PROD. C/ESTOQUE ) " );
				ps = con.prepareStatement( sql.toString() );
				int param = 1;
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
				ps.setDate( param++, Funcoes.dateToSQLDate( txtData.getVlrDate() ) );
				if ( codmarca.equals( "" ) ) {
					ps.setNull( param++, Types.INTEGER );
					ps.setNull( param++, Types.SMALLINT );
					ps.setNull( param++, Types.CHAR );
				}
				else {
					ps.setInt( param++, Aplicativo.iCodEmp );
					ps.setInt( param++, ListaCampos.getMasterFilial( "EQMARCA" ) );
					ps.setString( param++, codmarca );
					filtros.append( " ( MARCA: ");
					filtros.append(codmarca);
					filtros.append( "-" );
					filtros.append(txtDescMarca.getVlrString().trim());
					filtros.append(" ) ");
				}
				if ( codgrup.equals( "" ) ) {
					ps.setNull( param++, Types.INTEGER );
					ps.setNull( param++, Types.SMALLINT );
					ps.setNull( param++, Types.CHAR );
				}
				else {
					ps.setInt( param++, Aplicativo.iCodEmp );
					ps.setInt( param++, ListaCampos.getMasterFilial( "EQGRUPO" ) );
					ps.setString( param++, codgrup );
					filtros.append(" ( GRUPO: ");
					filtros.append( codgrup );
					filtros.append( "-" );
					filtros.append( txtDescGrup.getVlrString().trim() );
					filtros.append(" ) ");
				}
				ps.setString( param++, rgCusto.getVlrString() );
				if ( codalmox == 0 ) {
					ps.setNull( param++, Types.INTEGER );
					ps.setNull( param++, Types.INTEGER );
					ps.setNull( param++, Types.INTEGER );
				}
				else {
					ps.setInt( param++, Aplicativo.iCodEmp );
					ps.setInt( param++, ListaCampos.getMasterFilial( "EQALMOX" ) );
					ps.setInt( param++, codalmox );
					filtros.append( " ( ALMOX.: " );
					filtros.append(codalmox);
					filtros.append("-");
					filtros.append(txtDescAlmox.getVlrString().trim());
					filtros.append(" ) ");
				}

				ps.setString( param++, cbComEstoq.getVlrString() );
				
				rs = ps.executeQuery();
				
				if ("S".equals(imprimetexto)) {
					imprimirTexto( bVisualizar, rs, filtros, campocodigo );
				} else {
					imprimirGrafico( bVisualizar, rs, filtros);
				}


			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro executando a consulta.\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			}
		} finally {
			ps = null;
			rs = null;
		}

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcAlmox.setConexao( cn );
		lcGrup.setConexao( cn );
		lcMarca.setConexao( cn );
		bPrefs = getPrefere();
	}

	private boolean[] getPrefere() {

		boolean[] bRetorno = { false };
		String sSQL = "SELECT USAREFPROD" + " FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( rs.getString( "UsaRefProd" ) != null ) {
					if ( rs.getString( "UsaRefProd" ).trim().equals( "S" ) )
						bRetorno[ 0 ] = true;
				}
			}
			rs.close();
			ps.close();
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

}
