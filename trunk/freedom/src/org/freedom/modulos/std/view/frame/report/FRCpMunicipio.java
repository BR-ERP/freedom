/**
 * @version 22/09/2011 <BR>
 * @author Setpoint Inform�tica Ltda. / Bruno Nascimento<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FRCpMunicipio.java <BR>
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
 *         Relat�rio de compras de produtos agrupando o total por munic�pio
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JasperPrintManager;
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
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;

public class FRCpMunicipio extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private ListaCampos lcProd = new ListaCampos( this );
	
	private ListaCampos lcItCompra = new ListaCampos( this );
	
	public FRCpMunicipio() {

		setTitulo( "Compras por Munic�pio" );
		setAtribos( 50, 50, 345, 250 );

		montaListaCampos();
		montaTela();

	}

	public void montaListaCampos() {
		
		lcProd.add (new GuardaCampo( txtCodProd, "CodProd", "C�d.Prod.", ListaCampos.DB_PK, txtDescProd, false ) );
		lcProd.add (new GuardaCampo( txtDescProd, "DescProd", "DescProd.", ListaCampos.DB_SI, false ) );
		txtCodProd.setNomeCampo( "CodProd" );
		lcProd.montaSql( false, "PRODUTO", "EQ" );	
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );
		txtCodProd.setTabelaExterna( lcProd, FProduto.class.getCanonicalName() );
	
		
		lcItCompra.add (new GuardaCampo( txtCodCompra, "CodCompra", "C�d.Compra.", ListaCampos.DB_PK, false ) );
		lcItCompra.add (new GuardaCampo( txtCodProd, "CodProd", "C�d.Prod.", ListaCampos.DB_FK, false ) );
		lcItCompra.montaSql( false, "ITCOMPRA", "CP" );	
		lcItCompra.setQueryCommit( false );
		lcItCompra.setReadOnly( true );
			
		
	}

	public void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "   	Per�odo:" );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 300, 45 );

		adic( new JLabelPad( "De:" ), 10, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad( "At�:" ), 152, 25, 37, 20 );
		adic( txtDatafim, 190, 25, 100, 20 );
		adic( new JLabelPad( "C�d.Prod." ), 7, 60, 80, 20 );
		adic( txtCodProd, 7, 80, 80, 20 );
		adic( new JLabelPad( "Descri��o do Produto" ), 90, 60, 210, 20 );
		adic( txtDescProd, 90, 80, 215, 20 );
		

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}
	
	public void imprimir( boolean bVisualizar ){
		Blob fotoemp = null;
		try { 

			PreparedStatement ps = con.prepareStatement( "SELECT FOTOEMP FROM SGEMPRESA WHERE CODEMP=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				fotoemp = rs.getBlob( "FOTOEMP" );
			}
			rs.close();
			ps.close();
			con.commit();
		} catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro carregando logotipo.\n" + e.getMessage() );
			e.printStackTrace();
		}	


		imprimiCpMunicipio( bVisualizar, fotoemp );


	}

	public void imprimiCpMunicipio( boolean bVisualizar , Blob fotoemp ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();
	
		StringBuilder sWhere = new StringBuilder();
		BigDecimal bTotal = null;
		StringBuilder sCab = new StringBuilder();

		try {

			bTotal = new BigDecimal( "0" );
			
			
			sCab.append( "PER�ODO DE " );
			sCab.append( txtDataini.getVlrString() );
			sCab.append( " � " );
			sCab.append( txtDatafim.getVlrString() );
			sCab.append( " \n" );
			if ( txtCodProd.getVlrInteger().intValue() > 0 ) {

				sWhere.append( " AND IT.CODPROD= " + txtCodProd.getVlrInteger() );
				sCab.append( "PRODUTO: " + txtDescProd.getVlrString() );

			}
			
			sSQL.append( "SELECT M.SIGLAUF, M.NOMEMUNIC, " );
			sSQL.append( "SUM(IT.QTDITCOMPRA)/1000 QTDTONELADA " );
			sSQL.append( "FROM CPCOMPRA CP, CPITCOMPRA IT, CPFORNECED F, SGMUNICIPIO M "  );
			sSQL.append( "WHERE CP.CODEMP=? AND CP.CODFILIAL=? AND CP.DTENTCOMPRA BETWEEN ? AND ? AND " );
			sSQL.append( "IT.CODEMP=CP.CODEMP AND IT.CODFILIAL=CP.CODFILIAL AND IT.CODCOMPRA=CP.CODCOMPRA AND " );
			sSQL.append( "F.CODEMP=CP.CODEMPFR AND F.CODFILIAL=CP.CODFILIALFR AND F.CODFOR=CP.CODFOR AND " );
			sSQL.append( "M.CODPAIS=F.CODPAIS AND M.SIGLAUF=F.SIGLAUF AND M.CODMUNIC=F.CODMUNIC AND " );
			sSQL.append( "CP.STATUSCOMPRA='C3' AND IT.CODEMPPD=? AND IT.CODFILIALPD=? " );
			sSQL.append( sWhere ); 
			sSQL.append( "GROUP BY M.SIGLAUF, M.NOMEMUNIC ORDER BY M.SIGLAUF, M.NOMEMUNIC " );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			ps.setDate( 3, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( 4, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "EQPRODUTO" ) );

			rs = ps.executeQuery();

			imprimirGrafico( bVisualizar, rs, sCab.toString(), fotoemp );

			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta tabela de Compra!\n" + err.getMessage(), true, con, err );

		} finally {
			System.gc();
		}
	}

	private void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab, Blob fotoemp) {

		FPrinterJob dlGr = null;
		String report = "relatorios/FRCpMunicipio.jasper";
		String label = "Relatorio de compras por �tem";
		
	    HashMap<String, Object> hParam = new HashMap<String, Object>();

		try {
			
			hParam.put( "LOGOEMP",  new ImageIcon(fotoemp.getBytes(1, ( int ) fotoemp.length())).getImage() );
		} catch ( SQLException e ) {
			
			Funcoes.mensagemErro( this, "Erro carregando logotipo !\n" + e.getMessage()  );
			e.printStackTrace();
		}


		dlGr = new FPrinterJob( report, label, sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de compra por munic�pio." + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcProd.setConexao( cn );
		lcItCompra.setConexao( cn );
	}

}
	

