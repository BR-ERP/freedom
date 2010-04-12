/**
 * @version 08/04/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLCosultaBaixa.java <BR>
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

package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.freedom.funcoes.Funcoes;

public class DLConsultaBaixa extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtVlrParc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 9, 2 );

	private JTextFieldPad txtVlrJuros = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 9, 2 );

	private JTextFieldPad txtVlrDesc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 9, 2 );

	private JTextFieldPad txtVlrPago = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 9, 2 );

	private JTextFieldPad txtVlrAberto = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 9, 2 );

	private JTablePad tabConsulta = new JTablePad();

	private JScrollPane spnTab = new JScrollPane( tabConsulta );

	private JPanelPad pinConsulta = new JPanelPad( 0, 60 );

	public DLConsultaBaixa( Component cOrig, DbConnection cn, int iCodRec, int iNParc ) {

		super( cOrig );
		setConexao( cn );
		setTitulo( "Consulta de Baixas" );
		setAtribos( 100, 100, 530, 300 );

		setToFrameLayout();

		pnRodape.setPreferredSize( new Dimension( 500, 32 ) );
		pnRodape.setBorder( BorderFactory.createEtchedBorder() );
		c.add( pinConsulta, BorderLayout.NORTH );
		c.add( spnTab, BorderLayout.CENTER );

		txtVlrParc.setAtivo( false );
		txtVlrJuros.setAtivo( false );
		txtVlrDesc.setAtivo( false );
		txtVlrPago.setAtivo( false );
		txtVlrAberto.setAtivo( false );

		pinConsulta.adic( new JLabelPad( "V.Parcela" ), 7, 0, 250, 20 );
		pinConsulta.adic( txtVlrParc, 7, 20, 100, 20 );
		pinConsulta.adic( new JLabelPad( "V.Juros" ), 110, 0, 110, 20 );
		pinConsulta.adic( txtVlrJuros, 110, 20, 97, 20 );
		pinConsulta.adic( new JLabelPad( "V.Desconto" ), 210, 0, 110, 20 );
		pinConsulta.adic( txtVlrDesc, 210, 20, 97, 20 );
		pinConsulta.adic( new JLabelPad( "V.Pago" ), 310, 0, 110, 20 );
		pinConsulta.adic( txtVlrPago, 310, 20, 97, 20 );
		pinConsulta.adic( new JLabelPad( "V.Aberto" ), 410, 0, 110, 20 );
		pinConsulta.adic( txtVlrAberto, 410, 20, 97, 20 );

		tabConsulta.adicColuna( "Data do pagto." );
		tabConsulta.adicColuna( "Vlr. Pago." );
		tabConsulta.adicColuna( "Obs:" );

		tabConsulta.setTamColuna( 100, 0 );
		tabConsulta.setTamColuna( 100, 1 );
		tabConsulta.setTamColuna( 310, 2 );

		carregaGridConsulta( iCodRec, iNParc );
	}

	private void carregaGridConsulta( int iCodRec, int iNParc ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		
		try {
			
			sSQL.append( "SELECT S.DATASUBLANCA,S.VLRSUBLANCA,S.HISTSUBLANCA " );
			sSQL.append( "FROM FNSUBLANCA S, FNLANCA L WHERE S.CODLANCA=L.CODLANCA " );
			sSQL.append( "AND S.CODEMP=L.CODEMP AND S.CODFILIAL=L.CODFILIAL " );
			sSQL.append( "AND L.CODREC=? AND L.NPARCITREC=? AND L.CODEMP=? " );
			sSQL.append( "AND L.CODFILIAL=? AND S.CODSUBLANCA=0 " );
			sSQL.append( "ORDER BY DATASUBLANCA" );
			
			ps = con.prepareStatement( sSQL.toString() );
			
			ps.setInt( 1, iCodRec );
			ps.setInt( 2, iNParc );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "FNLANCA" ) );
			
			rs = ps.executeQuery();
			
			int i = 0;
			
			while(rs.next()) {
				tabConsulta.adicLinha();
				tabConsulta.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DataSubLanca" ) ), i, 0 );
				tabConsulta.setValor( Funcoes.strDecimalToStrCurrency( 2, rs.getString( "VlrSubLanca" ) ), i, 1 );
				tabConsulta.setValor( rs.getString( "HistSubLanca" ), i, 2 );
				
				i++;
				
			}
			
			rs.close();
			ps.close();
			
			con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar a tabela de consulta!\n" + err.getMessage(), true, con, err );
		}
	}

	public void setValores( BigDecimal bigVals[] ) {

		txtVlrParc.setVlrBigDecimal( bigVals[ 0 ] );
		txtVlrPago.setVlrBigDecimal( bigVals[ 1 ] );
		txtVlrDesc.setVlrBigDecimal( bigVals[ 2 ] );
		txtVlrJuros.setVlrBigDecimal( bigVals[ 3 ] );
		txtVlrAberto.setVlrBigDecimal( bigVals[ 4 ] );
	}
}
