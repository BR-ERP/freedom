/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLBuscaImportacao.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela para busca de importa��o para emiss�o de compra.
 */

package org.freedom.modulos.gms.view.dialog.utility;

import java.awt.Component;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

public class DLBuscaImportacao extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad 	txtCodImp 			= 	new JTextFieldPad( 	JTextFieldPad.TP_INTEGER	, 8		, 0 );
	private JTextFieldPad 	txtInvoice 			= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 20	, 0 );
	private JTextFieldPad 	txtDI	 			= 	new JTextFieldPad( 	JTextFieldPad.TP_STRING		, 20	, 0 );
	private JTextFieldPad 	txtCodFor 			= 	new JTextFieldPad( 	JTextFieldPad.TP_INTEGER	, 8		, 0 );
	private JTextFieldPad 	txtCodForCompra		= 	new JTextFieldPad( 	JTextFieldPad.TP_INTEGER	, 8		, 0 );
	private JTextFieldPad 	txtVMLE			  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVMLD			  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrAD		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtVlrFrete		  	= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, Aplicativo.casasDecFin );
	private JTextFieldPad 	txtDtImp			= 	new JTextFieldPad( 	JTextFieldPad.TP_DATE		, 10	, 0 );
	private JTextFieldPad 	txtDtEmb			= 	new JTextFieldPad( 	JTextFieldPad.TP_DATE		, 10	, 0 );
	private JTextFieldPad 	txtDtChegada		= 	new JTextFieldPad( 	JTextFieldPad.TP_DATE		, 10	, 0 );
	private JTextFieldPad 	txtPesoBruto		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, 0 );
	private JTextFieldPad 	txtPesoLiquido		= 	new JTextFieldPad( 	JTextFieldPad.TP_DECIMAL	, 15	, 0 );

	private ListaCampos lcImportacao = new ListaCampos( this );

	public DLBuscaImportacao( Component cOrig, DbConnection cn ) {

		super( cOrig );
		
		setTitulo( "Busca Importa��o" );
		
		setAtribos( 350, 180 );
		setLocationRelativeTo( this );

		lcImportacao.add( new GuardaCampo( txtCodImp		, "CodImp"			, "C�d.Imp."			, ListaCampos.DB_PK, false ) );
		lcImportacao.add( new GuardaCampo( txtInvoice		, "Invoice"			, "Invoice"				, ListaCampos.DB_SI, false ) );
		lcImportacao.add( new GuardaCampo( txtCodFor		, "CodFor"			, "C�d.For."			, ListaCampos.DB_SI, false ) );
		lcImportacao.add( new GuardaCampo( txtDI			, "DI"				, "DI"					, ListaCampos.DB_SI, false ) );
		lcImportacao.add( new GuardaCampo( txtDtImp			, "DtImp"			, "Dt.Imp."				, ListaCampos.DB_SI, false ) );
		lcImportacao.add( new GuardaCampo( txtDtEmb			, "Dtemb"			, "Dt.Emb."				, ListaCampos.DB_SI, false ) );
		lcImportacao.add( new GuardaCampo( txtDtChegada		, "DtChegada"		, "Dt.Chegada"			, ListaCampos.DB_SI, false ) );
		lcImportacao.add( new GuardaCampo( txtDtChegada		, "DtChegada"		, "Dt.Chegada"			, ListaCampos.DB_SI, false ) );
		lcImportacao.add( new GuardaCampo( txtPesoBruto		, "PesoBruto"		, "Peso bruto"			, ListaCampos.DB_SI, false ) );
		lcImportacao.add( new GuardaCampo( txtPesoLiquido	, "PesoLiquido"		, "Peso Liquido"		, ListaCampos.DB_SI, false ) );
		lcImportacao.add( new GuardaCampo( txtVlrFrete		, "VlrFrete"		, "Vlr.Frete"			, ListaCampos.DB_SI, false ) );
		lcImportacao.add( new GuardaCampo( txtVMLE			, "VMLE"			, "VMLE"				, ListaCampos.DB_SI, false ) );
		lcImportacao.add( new GuardaCampo( txtVMLD			, "VMLD"			, "VMLD"				, ListaCampos.DB_SI, false ) );
		lcImportacao.add( new GuardaCampo( txtVlrAD			, "VlrAD"			, "Vlr.Aduaneiro"		, ListaCampos.DB_SI, false ) );
		
		lcImportacao.montaSql( false, "IMPORTACAO", "CP" );
		lcImportacao.setReadOnly( true );
		
		txtCodImp.setTabelaExterna( lcImportacao, null );
		txtCodImp.setFK( true );
		txtCodImp.setNomeCampo( "CodImp" );

		adic( txtCodImp			, 7		, 20	, 100	, 20	, "C�d.Imp." );
		adic( txtInvoice		, 110	, 20	, 100	, 20	, "Invoice" );
		adic( txtDI				, 213	, 20	, 100	, 20	, "DI" );
		adic( txtPesoLiquido	, 7		, 60	, 100	, 20	, "Peso L�quido" );
		adic( txtVlrFrete		, 110	, 60	, 100	, 20	, "Frete" );
		adic( txtVlrAD			, 213	, 60	, 100	, 20	, "Vlr.Aduaneiro" );
		
		txtInvoice.setSoLeitura( true );
		txtDI.setSoLeitura( true );
		txtPesoLiquido.setSoLeitura( true );
		txtVlrFrete.setSoLeitura( true );
		txtVlrAD.setSoLeitura( true );
		
		lcImportacao.setConexao( cn );
		
	}

	public void setCodFor(Integer codfor) {
		
		txtCodForCompra.setVlrInteger( codfor );
		
	}
	
	public Integer getCodImp() {

		return txtCodImp.getVlrInteger();

	}
}
