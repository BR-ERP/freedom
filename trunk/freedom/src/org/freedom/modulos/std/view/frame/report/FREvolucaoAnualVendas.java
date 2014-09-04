/**
 * @version 31/10/2013 <BR>
 * @author Setpoint Tecnologia em Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.frame.report <BR>
 *         Classe: * @(#)FRAcompMensalVendas.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Relat�rio de acompanhamento mensal de vendas
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
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

public class FREvolucaoAnualVendas extends FRelatorio implements FocusListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtAnoini = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtMesini = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtAnofim = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtMesfim = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JRadioGroup<?, ?> rgFaturados = null;

	private JRadioGroup<?, ?> rgFinanceiro = null;

	private ListaCampos lcVend = new ListaCampos( this );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private Vector<String> vLabsEmit = new Vector<String>();

	private Vector<String> vValsEmit = new Vector<String>();

	public FREvolucaoAnualVendas() {

		setTitulo( "Evolu��o anual de vendas" );
		setAtribos( 80, 80, 333, 400 );

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

		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.comiss.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "VD" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );
		txtCodVend.setNomeCampo( "CodVend" );
		txtCodVend.setFK( true );
		txtCodVend.setTabelaExterna( lcVend, null );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "NomeCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCli, FCliente.class.getCanonicalName() );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );

		adic( new JLabelPad( "Periodo:" ), 7, 5, 100, 20 );
		adic( lbLinha, 60, 15, 210, 2 );
		adic( new JLabelPad( "M�s" ), 10, 25, 40, 20 );
		adic( txtMesini, 10, 45, 40, 20 );
		adic( new JLabelPad( "/" ), 54, 45, 5, 20 );
		adic( new JLabelPad( "Ano" ), 64, 25, 40, 20 );
		adic( txtAnoini, 64, 45, 60, 20 );

		adic( new JLabelPad( "M�s" ), 140, 25, 40, 20 );
		adic( txtMesfim, 140, 45, 40, 20 );
		adic( new JLabelPad( "/" ), 184, 45, 5, 20 );
		adic( new JLabelPad( "Ano" ), 194, 25, 40, 20 );
		adic( txtAnofim, 194, 45, 60, 20 );

		adic( new JLabelPad( "De:" ), 10, 70, 97, 20 );
		adic( txtDataini, 32, 70, 97, 20 );

		adic( new JLabelPad( "At�:" ), 140, 70, 100, 20 );
		adic( txtDatafim, 170, 70, 100, 20 );

		adic( txtCodVend, 7, 110, 70, 20, "C�d.comiss." );
		adic( txtDescVend, 80, 110, 190, 20, "Nome do comissionado" );

		adic( txtCodCli, 7, 150, 70, 20, "C�d.Cli" );
		adic( txtNomeCli, 80, 150, 190, 20, "Nome do cliente" );

		adic( rgFaturados, 7, 180, 120, 70 );

		adic( rgFinanceiro, 153, 180, 120, 70 );
		
		btExportXLS.setEnabled( true );
		txtDataini.setEditable( false );
		txtDatafim.setEditable( false );
		
		setParamIni();
		
		txtAnoini.addFocusListener( this );
		txtMesini.addFocusListener( this );
		txtAnofim.addFocusListener( this );
		txtMesfim.addFocusListener( this );
	}

	private StringBuilder getQuerReport( Integer codemp, Integer codfilialcl, Integer codcli
			, Integer codfilialva, Integer codvend, Date dataini, Date datafim, String faturado
			, String financeiro, StringBuilder filtros, Vector<String> meses, TYPE_PRINT visualizar  ) {

		StringBuilder sql = new StringBuilder();
		sql.append( "select p.codprod, p.descprod " );
		for ( int i = 0; i < meses.size(); i++ ) {
			String anomes = meses.elementAt( i );
			String ano = anomes.substring( 0, 4 );
			String mes = anomes.substring( 4 );
			sql.append( ", sum((case when extract(month from v.dtemitvenda)=" );
			sql.append( mes );
			sql.append( " and extract(year from v.dtemitvenda)=" );
			sql.append( ano );
			sql.append( " then v.vlrliqvenda else 0 end)) vlr_" );
			if (visualizar==TYPE_PRINT.EXPORT) {
				sql.append(mes);
				sql.append("_");
				sql.append(ano);
			} else {
				sql.append( i + 1 );
			}
		}
		sql.append( " , sum(v.vlrliqvenda) subtotal " );
		sql.append( " from eqproduto p " );
		sql.append( " inner join vditvenda iv on ");
		sql.append( " iv.codemppd=pd.codemp and iv.codfilialpd=p.codfilial ");
		sql.append( " and iv.codprod=p.codprod ");
		sql.append( " inner join vdvenda v on " );
		sql.append( " v.codemp=iv.codemp and v.codfilial=iv.codfilial" );
		sql.append( " and iv.tipovenda=v.tipovenda and iv.codvenda=v.codvenda ");
		sql.append( " inner join vdcliente c on ");
		sql.append( " c.codemp=v.codempcl and c.codfilial=v.codempcl and c.codcli=v.codcli ");
		sql.append( " inner join eqtipomov tm on" );
		sql.append( " tm.codemp=v.codemptm and tm.codfilial=v.codfilialtm and tm.codtipomov=v.codtipomov" );
		sql.append( " inner join fnplanopag pp on " );
		sql.append( " pp.codemp=v.codemppg and pp.codfilial=v.codfilialpg and pp.codplanopag=v.codplanopag ");
		sql.append( " where v.codemp=? and v.codfilial=?" );
		sql.append( " and v.dtemitvenda between ? and ?" );
		sql.append( " and substring(v.statusvenda from 1 for 1) not in ('C','N') " );
		if ( "S".equalsIgnoreCase( faturado ) ) {
			sql.append( " and tm.fiscaltipomov='S' " );
			filtros.append( ", faturados" );
		}
		else if ( "N".equalsIgnoreCase( faturado ) ) {
			sql.append( " and tm.fiscaltipomov='N' " );
			filtros.append( ", n�o faturados" );
		}
		if ( "S".equalsIgnoreCase( financeiro ) ) {
			sql.append( " and tm.somavdtipomov='S' and pp.parcplanopag>0 " );
			filtros.append( ", financeiros" );

		}
		else if ( "N".equalsIgnoreCase( financeiro ) ) {
			sql.append( " and tm.somavdtipomov='N' " );
			filtros.append( ", n�o financeiros" );
		}
		if ( codcli!=0 ) {
			sql.append( " and c.codemp=? and c.codfilial=? and c.codcli=? ");
			filtros.append( ", c�d.cliente: " );
			filtros.append( codcli );
		}
		if ( codvend!=0 ) {
			sql.append( " and v.codempvd=? and c.codfilialvd=? and c.codvend=? ");
			filtros.append( ", c�d.comissioando: " );
			filtros.append( codvend );
		}
		sql.append( " group by c.codcli, c.razcli" );
		sql.append( " order by c.codcli" );
		return sql;
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final menor que a data inicial!" );
			return;
		}
		if ( Funcoes.getAno(  txtDatafim.getVlrDate() )!= Funcoes.getAno( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Per�odo deve ficar dentro do mesmo ano !" );
			return;
		}
		Vector<String> meses = Funcoes.getMeses( txtDataini.getVlrDate(), txtDatafim.getVlrDate() );
		if ( meses.size()>12 && bVisualizar!=TYPE_PRINT.EXPORT ) {
			Funcoes.mensagemInforma( this
					, "N�o � permitido a visualiza��o ou impress�o do relat�rio com per�odo superior a 12 meses!\n" );
			return;
		}

		try {

			StringBuilder filtros = new StringBuilder();
			filtros.append( "Per�odo de ");
			filtros.append( txtDataini.getVlrString());
			filtros.append( " at� " );
			filtros.append( txtDatafim.getVlrString() );

			int codemp = Aplicativo.iCodEmp;
			int codfilialcl = ListaCampos.getMasterFilial( "VDCLIENTE" );
			int codcli = txtCodCli.getVlrInteger();
			int codfilialva = ListaCampos.getMasterFilial( "VDVENDEDOR" );
			int codvend = txtCodVend.getVlrInteger();
			
			StringBuilder sql = getQuerReport( codemp, codfilialcl, codcli
					, codfilialva, codvend	, txtDataini.getVlrDate(), txtDatafim.getVlrDate()
					, rgFaturados.getVlrString(), rgFinanceiro.getVlrString(), filtros, meses, bVisualizar );

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilialcl );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			if ( codcli!=0 ) {
				ps.setInt( param++, codemp );
				ps.setInt( param++, codfilialcl );
				ps.setInt( param++, codcli );
			}
			if ( codvend!=0 ) {
				ps.setInt( param++, codemp );
				ps.setInt( param++, codfilialva );
				ps.setInt( param++, codvend );
			}

			ResultSet rs = ps.executeQuery();

			if (bVisualizar==TYPE_PRINT.EXPORT) {
				if (btExportXLS.execute(rs, getTitle())) {
					Funcoes.mensagemInforma( this, "Arquivo exportado com sucesso !" );
				}
			} else {
				imprimirGrafico( bVisualizar, rs, filtros, meses );
			}
			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de vendas!\n" + err.getMessage(), true, con, err );
		} finally {
			System.gc();
		}
	}

	private void imprimirGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final StringBuilder filtros, Vector<String> meses ) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put( "MESES", meses );
		
		FPrinterJob dlGr = null;

		dlGr = new FPrinterJob( "relatorios/evolucaomensalvendas.jasper", "Evolu��o mensal de vendas", filtros.toString(), rs, params, this );

		if ( bVisualizar == TYPE_PRINT.VIEW ) {
			dlGr.preview();
		}
		else {
			try {
				dlGr.print(true);
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcVend.setConexao( con );
		lcCli.setConexao( con );
	}

	public void focusGained( FocusEvent e ) {

	}

	private void setDataini() {
		
		txtDataini.setVlrDate( Funcoes.encodeDate( txtAnoini.getVlrInteger(), txtMesini.getVlrInteger(), 1 ) );
	}

	private void setParamIni() {
		
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.MONTH, -1 );
		Date datafim = cal.getTime();
		cal.add( Calendar.MONTH, +1 );
		cal.add( Calendar.YEAR, -1 );
		Date dataini = cal.getTime();
		txtMesfim.setVlrInteger( Funcoes.getMes( datafim ) );
		txtAnofim.setVlrInteger( Funcoes.getAno( datafim ) );
		txtMesini.setVlrInteger( Funcoes.getMes( dataini ) );
		txtAnoini.setVlrInteger( Funcoes.getAno( dataini ) );
		setDataini();
		setDatafim();
	}
	
	private void setDatafim() {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime( Funcoes.encodeDate( txtAnofim.getVlrInteger(), txtMesfim.getVlrInteger()+1, 1 ) );
		cal.add( Calendar.DAY_OF_MONTH, -1 );
		txtDatafim.setVlrDate( cal.getTime() );
	}

	public void focusLost( FocusEvent e ) {

		if ( e.getSource()==txtAnoini || e.getSource()==txtMesini ) {
			setDataini();
		} else if ( e.getSource()==txtAnofim || e.getSource()==txtMesfim ) {
			setDatafim();
		}
		
	}
}
