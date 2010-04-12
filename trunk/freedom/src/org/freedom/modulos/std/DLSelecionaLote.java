/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLLote.java <BR>
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

package org.freedom.modulos.std;

import java.awt.Component;
import java.awt.event.ActionEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.JLabelPad;
import org.freedom.library.JTextFieldFK;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;

import java.util.Date;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLSelecionaLote extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDataINILote = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtVenctoLote = new JTextFieldFK( JTextFieldFK.TP_DATE, 10, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JLabelPad lbCodLote = new JLabelPad( "C�d.lote" );

	private JLabelPad lbCodProd = new JLabelPad( "C�d.prod." );

	private JLabelPad lbDescProd = new JLabelPad( "Descri��o do produto" );

	private JLabelPad lbDataINILote = new JLabelPad( "Data Inicial" );

	private JLabelPad lbVenctoLote = new JLabelPad( "Vencimento" );
	
	private ListaCampos lcLote = new ListaCampos( this, "LE" );
	
	private JTextFieldFK txtSldLiqProd = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtDtSaidaVenda = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	public DLSelecionaLote( Component cOrig, String sCodProd, String sDescProd, DbConnection cn ) {

		super( cOrig );
		setConexao( cn );
		setTitulo( "Lote" );
		setAtribos( 395, 180 );

		adic( lbCodLote, 7, 0, 150, 20 );
		adic( txtCodLote, 7, 20, 150, 20 );
		adic( lbDataINILote, 160, 0, 102, 20 );
		adic( txtDataINILote, 160, 20, 102, 20 );
		adic( lbVenctoLote, 265, 0, 120, 20 );
		adic( txtVenctoLote, 265, 20, 102, 20 );
		adic( lbCodProd, 7, 40, 300, 20 );
		adic( txtCodProd, 7, 60, 80, 20 );
		adic( lbDescProd, 90, 40, 278, 20 );
		adic( txtDescProd, 90, 60, 278, 20 );

		txtCodProd.setVlrString( sCodProd );
		txtDescProd.setVlrString( sDescProd );
		txtCodLote.requestFocus();
		txtDtSaidaVenda.setVlrDate( new Date() );
		txtCodProd.setEditable( false );
		
		// FK de Lotes
		
		
		
		txtCodLote.setNomeCampo( "codlote" );
		
		lcLote.add( new GuardaCampo( txtCodLote, "CodLote", "Lote", ListaCampos.DB_PK, txtVenctoLote, true ) );
		lcLote.add( new GuardaCampo( txtVenctoLote, "VenctoLote", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcLote.add( new GuardaCampo( txtDataINILote, "DIniLote", "Dt.Inicio", ListaCampos.DB_SI, false ) );
		lcLote.add( new GuardaCampo( txtSldLiqProd, "SldLiqLote", "Saldo", ListaCampos.DB_SI, false ) );
		lcLote.setDinWhereAdic( "CODPROD=#N AND (VENCTOLOTE >= #D )", txtCodProd );
		lcLote.setDinWhereAdic( "", txtDtSaidaVenda );
		txtCodLote.setFK(true);
		txtCodLote.setTabelaExterna( lcLote );
		txtCodLote.setNomeCampo( "codlote" );
		lcLote.setQueryCommit( false );
		lcLote.setReadOnly( true );
		lcLote.montaSql( false, "LOTE", "EQ" );
		
		lcLote.setConexao( cn );

		/*txtVenctoLote.setListaCampos( lcLote );
		txtVenctoLote.setNomeCampo( "VenctoLote" );
		txtVenctoLote.setLabel( "Vencimento" );*/			
		
	

//		txtCodProd.setVlrString( sCodProd );
		
		
	}

	public String getValor() {

		return txtCodLote.getVlrString();
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if(!"".equals( txtCodLote.getVlrString())) {
				super.actionPerformed( evt );
			}
			else {
				Funcoes.mensagemErro( null, "Lote inv�lido!" );
				return;
			}
		}
		else if ( evt.getSource() == btCancel ) {
			super.actionPerformed( evt );
		}
	}
}
