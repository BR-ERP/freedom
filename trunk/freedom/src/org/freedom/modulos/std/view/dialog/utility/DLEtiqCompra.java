/**
 * @version 03/09/2007 <BR>
 * @author Setpoint Inform�tica Ltda./ Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLEtiqCompra.java <BR>
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

package org.freedom.modulos.std.view.dialog.utility;

import java.awt.Component;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;


public class DLEtiqCompra extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDataCompra = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtValorCompra = new JTextFieldFK( JTextFieldPad.TP_NUMERIC, 15, 3 );

	private JTextFieldFK txtStatusCompra = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcCompra = new ListaCampos( this );

	private ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private ListaCampos lcFornecedor = new ListaCampos( this, "FO" );
	

	public DLEtiqCompra( Component cOrig ) {

		super( cOrig );
		setTitulo( "Selecionar Compra" );
		setAtribos( 430, 200 );
		
		montaListaCampos();
		
		montaTela();
	}
	
	private void montaListaCampos() {
		
		/**************
		 *   COMPRA   *
		 **************/
		lcCompra.add( new GuardaCampo( txtCodCompra, "CodCompra", "N.pedido", ListaCampos.DB_PK, false ) );
		lcCompra.add( new GuardaCampo( txtDataCompra, "DtEmitCompra", "Dt.emiss�o", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtValorCompra, "VlrLiqCompra", "V.compra", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtStatusCompra, "StatusCompra", "Status", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_FK, txtRazFor, false ) );
		lcCompra.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pg.", ListaCampos.DB_FK, txtDescPlanoPag, false ) );
		lcCompra.montaSql( false, "COMPRA", "CP" );
		lcCompra.setReadOnly( true );
		txtCodCompra.setFK( true );
		txtCodCompra.setNomeCampo( "CodCompra" );
		txtCodCompra.setTabelaExterna( lcCompra );
		txtDataCompra.setListaCampos( lcCompra );
		txtValorCompra.setListaCampos( lcCompra );
		txtStatusCompra.setListaCampos( lcCompra );
		
		/**************************
		 *   PLANO DE PAGAMENTO   *
		 **************************/
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pg.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setFK( true );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		txtDescPlanoPag.setListaCampos( lcPlanoPag );
		
		/******************
		 *   FORNECEDOR   *
		 ******************/
		lcFornecedor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, false ) );
		lcFornecedor.add( new GuardaCampo( txtRazFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedor.montaSql( false, "FORNECED", "CP" );
		lcFornecedor.setQueryCommit( false );
		lcFornecedor.setReadOnly( true );
		txtCodFor.setFK( true );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setTabelaExterna( lcFornecedor );
		txtRazFor.setListaCampos( lcFornecedor );
	}
	
	private void montaTela() {
		

		adic( new JLabelPad( "C�d.compra" ), 7, 10, 100, 20 );
		adic( txtCodCompra, 7, 30, 100, 20 );
		adic( new JLabelPad( "Data emiss�o" ), 110, 10, 97, 20 );
		adic( txtDataCompra, 110, 30, 97, 20 );
		adic( new JLabelPad( "Valor" ), 210, 10, 150, 20 );
		adic( txtValorCompra, 210, 30, 150, 20 );
		adic( new JLabelPad( "Status" ), 363, 10, 47, 20 );
		adic( txtStatusCompra, 363, 30, 47, 20 );
		
		adic( new JLabelPad( "Fornecedor" ), 7, 50, 200, 20 );
		adic( txtRazFor, 7, 70, 200, 20 );
		adic( new JLabelPad( "Plano de pagamento" ), 210, 50, 200, 20 );
		adic( txtDescPlanoPag, 210, 70, 200, 20 );
	}
	
	public Integer getCompra() {
		return txtCodCompra.getVlrInteger();
	}
	
	public void setConexao( DbConnection cn ) {
		
		lcCompra.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcFornecedor.setConexao( cn );
	}
}
