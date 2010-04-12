/**
 * @version 02/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPPagar.java <BR>
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
 * Tela para manuten��o de pagamentos.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.telas.FDados;

public class RPPagar extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtDuplicata = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtParcDuplic = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtPedido = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtVlrPag = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtVlrComiss = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtVlrPago = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtDtEmiss = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtVenc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtPagamento = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	public RPPagar() {

		super( false );
		setTitulo( "Contas a pagar" );		
		setAtribos( 50, 50, 435, 340 );
		
		montaTela();
		setListaCampos( true, "PAGAR", "RP" );
		
		txtPedido.setAtivo( false );
		txtVlrPag.setAtivo( false );
		txtVlrComiss.setAtivo( false );
		txtDtEmiss.setAtivo( false );
		txtDtVenc.setAtivo( false );
	}
	
	private void montaTela() {
		
		adicCampo( txtCodFor, 7, 20, 100, 20, "CodFor", "C�d.for.", ListaCampos.DB_PK, txtRazFor, true );
		adicDescFK( txtRazFor, 110, 20, 300, 20, "RazFor", "Raz�o social do fornecedor" );
		
		JLabel linha = new JLabel();
		linha.setBorder( BorderFactory.createEtchedBorder() );
		linha.setOpaque( true );
		
		adic( linha, 7, 55, 404, 2 );
		
		adicCampo( txtDuplicata, 7, 80, 82, 20, "Duplic", "Duplicata", ListaCampos.DB_SI, false );
		adicCampo( txtParcDuplic, 92, 80, 15, 20, "ParcDuplic", "", ListaCampos.DB_SI, false );
		adicCampo( txtPedido, 110, 80, 100, 20, "CodPed", "Pedido", ListaCampos.DB_SI, false );
		
		adicCampo( txtCodCli, 7, 120, 100, 20, "CodCli", "C�d.cli.", ListaCampos.DB_FK, txtRazCli, true );
		adicDescFK( txtRazCli, 110, 120, 300, 20, "RazCli", "Raz�o social do cliente" );		
		adicCampo( txtCodVend, 7, 160, 100, 20, "CodVend", "C�d.vend.", ListaCampos.DB_FK, txtNomeVend, true );
		adicDescFK( txtNomeVend, 110, 160, 300, 20, "NomeVend", "Nome do vendedor" );
		
		adicCampo( txtVlrPag, 7, 200, 100, 20, "VlrPag", "Valor da fatura", ListaCampos.DB_SI, false );
		adicCampo( txtVlrComiss, 110, 200, 100, 20, "VlrComiss", "Valor da comiss�o", ListaCampos.DB_SI, false );		
		adicCampo( txtDtEmiss, 213, 200, 100, 20, "DtEmiss", "Emiss�o", ListaCampos.DB_SI, false );
		adicCampo( txtDtVenc, 316, 200, 95, 20, "DtVenc", "Vencimento", ListaCampos.DB_SI, false );
		
		adicCampo( txtVlrPago, 7, 240, 120, 20, "VlrPago", "Valor recebido", ListaCampos.DB_SI, false );
		adicCampo( txtDtPagamento, 130, 240, 120, 20, "DtPgto", "Data recebimento", ListaCampos.DB_SI, false );
	}
}
