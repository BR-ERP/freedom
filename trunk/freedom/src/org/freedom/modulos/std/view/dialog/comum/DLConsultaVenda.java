/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLConsultaVenda.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std.view.dialog.comum;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;

import org.freedom.funcoes.Funcoes;

public class DLConsultaVenda extends FFDialogo implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinConsulta = new JPanelPad( 500, 100 );

	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtDocVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtVlrVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private JTextFieldPad txtDtEmitVenda = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtSaida = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTablePad tabConsulta = new JTablePad();

	private JScrollPane spnTab = new JScrollPane( tabConsulta );

	private ListaCampos lcPlanoPag = new ListaCampos( this );

	private ListaCampos lcVenda = new ListaCampos( this );

	public DLConsultaVenda( Component cOrig, DbConnection cn, int iCodVenda, String sTipoVenda ) {

		super( cOrig );
		setConexao( cn );
		txtCodVenda.setVlrString( String.valueOf( iCodVenda ) );
		txtTipoVenda.setVlrString( sTipoVenda );
		setTitulo( "Consulta de Venda" );
		setAtribos( 120, 140, 500, 300 );

		setToFrameLayout();

		c.add( pinConsulta, BorderLayout.NORTH );
		c.add( spnTab, BorderLayout.CENTER );

		txtCodVenda.setAtivo( false );
		txtCodPlanoPag.setAtivo( false );
		txtVlrVenda.setAtivo( false );
		txtDocVenda.setAtivo( false );
		txtDtEmitVenda.setAtivo( false );
		txtDtSaida.setAtivo( false );

		pinConsulta.adic( new JLabelPad( "N� pedido" ), 7, 0, 100, 20 );
		pinConsulta.adic( txtCodVenda, 7, 20, 100, 20 );
		pinConsulta.adic( new JLabelPad( "Doc.venda" ), 110, 0, 200, 20 );
		pinConsulta.adic( txtDocVenda, 110, 20, 100, 20 );

		pinConsulta.adic( new JLabelPad( "C�d.p.pag." ), 212, 0, 200, 20 );
		pinConsulta.adic( txtCodPlanoPag, 212, 20, 77, 20 );
		pinConsulta.adic( new JLabelPad( "Descri��o do plano" ), 292, 0, 200, 20 );
		pinConsulta.adic( txtDescPlanoPag, 292, 20, 187, 20 );
		pinConsulta.adic( new JLabelPad( "Data emiss�o" ), 7, 40, 150, 20 );
		pinConsulta.adic( txtDtEmitVenda, 7, 60, 110, 20 );
		pinConsulta.adic( new JLabelPad( "Data saida" ), 120, 40, 150, 20 );
		pinConsulta.adic( txtDtSaida, 120, 60, 100, 20 );
		pinConsulta.adic( new JLabelPad( "Valor total" ), 223, 40, 100, 20 );
		pinConsulta.adic( txtVlrVenda, 223, 60, 100, 20 );

		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false ) );
		txtDescPlanoPag.setListaCampos( lcPlanoPag );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		lcPlanoPag.setConexao( con );

		txtCodVenda.setNomeCampo( "CodVenda" );
		lcVenda.add( new GuardaCampo( txtCodVenda, "CodVenda", "N� pedido", ListaCampos.DB_PK, false ) );
		lcVenda.add( new GuardaCampo( txtTipoVenda, "TipoVenda", "Tipo.Venda", ListaCampos.DB_PK, false ) );
		lcVenda.add( new GuardaCampo( txtDocVenda, "DocVenda", "N� documento", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtDtEmitVenda, "DtEmitVenda", "Dt.emiss�o", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtDtSaida, "DtSaidaVenda", "Dt.sa�da", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_FK, false ) );
		lcVenda.add( new GuardaCampo( txtVlrVenda, "VlrLiqVenda", "Valor", ListaCampos.DB_SI, false ) );

		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		txtCodPlanoPag.setListaCampos( lcVenda );
		txtVlrVenda.setListaCampos( lcVenda );
		lcVenda.montaSql( false, "VENDA", "VD" );
		lcVenda.setReadOnly( true );
		lcVenda.setConexao( con );
		lcVenda.carregaDados();

		tabConsulta.adicColuna( "Item" );
		tabConsulta.adicColuna( "Ref.prod." );
		tabConsulta.adicColuna( "Descri��o" );
		tabConsulta.adicColuna( "C�d.lote" );
		tabConsulta.adicColuna( "Quant." );
		tabConsulta.adicColuna( "Pre�o" );
		tabConsulta.adicColuna( "Desc" );
		tabConsulta.adicColuna( "Vl.liq" );

		tabConsulta.setTamColuna( 50, 0 );
		tabConsulta.setTamColuna( 70, 1 );
		tabConsulta.setTamColuna( 180, 2 );
		tabConsulta.setTamColuna( 70, 3 );
		tabConsulta.setTamColuna( 80, 4 );
		tabConsulta.setTamColuna( 100, 5 );
		tabConsulta.setTamColuna( 100, 6 );
		tabConsulta.setTamColuna( 100, 7 );

		carregaGridConsulta();
	}

	private void carregaGridConsulta() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer(); 
		
		try {
			
			sSQL.append( "SELECT IT.CODITVENDA, P.DESCPROD, IT.QTDITVENDA," );
			sSQL.append( "IT.PRECOITVENDA,IT.VLRLIQITVENDA,IT.CODLOTE,IT.VLRDESCITVENDA, IT.REFPROD " );
			sSQL.append( "FROM VDITVENDA IT, EQPRODUTO P " );
			sSQL.append( "WHERE IT.CODVENDA=? AND IT.CODEMP=? AND IT.CODFILIAL=? AND IT.TIPOVENDA=? " );
			sSQL.append( "AND P.CODPROD=IT.CODPROD AND P.CODEMP=IT.CODEMPPD AND P.CODFILIAL=IT.CODFILIALPD " );
			sSQL.append( "ORDER BY CODITVENDA" );
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, txtCodVenda.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "VDITVENDA" ) );
			ps.setString( 4, txtTipoVenda.getVlrString() );

			rs = ps.executeQuery();
			
			for ( int i = 0; rs.next(); i++ ) {
				
				tabConsulta.adicLinha();
				tabConsulta.setValor( String.valueOf( rs.getInt( "CodItVenda" ) ), i, 0 );
				tabConsulta.setValor( String.valueOf( rs.getString( "RefProd" ) ), i, 1 );
				tabConsulta.setValor( ( rs.getString( "DescProd" ) != null ? rs.getString( "DescProd" ) : "" ), i, 2 );
				tabConsulta.setValor( ( rs.getString( "CodLote" ) != null ? rs.getString( "CodLote" ) : "" ), i, 3 );
				tabConsulta.setValor( ( rs.getString( "QtdItVenda" ) != null ? rs.getString( "QtdItVenda" ) : "" ), i, 4 );
				tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 13, 2, rs.getString( "PrecoItVenda" ) ), i, 5 );
				tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 13, 2, rs.getString( "VlrDescItVenda" ) ), i, 6 );
				tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 13, 2, rs.getString( "VlrLiqItVenda" ) ), i, 7 );
			}
			
			rs.close();
			ps.close();

			con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar a tabela de consulta!\n" + err.getMessage(), true, con, err );
		}
	}
}
