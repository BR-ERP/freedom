/**
 * @version 12/05/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc.view.dialog.utility <BR>
 * Classe:
 * @(#)DLBuscaLancaValor.java <BR>
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
 * Dialog para entrada de informa��es sobre busca de lan�amentos financeiros por valor.
 */

package org.freedom.modulos.fnc.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;

import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.fnc.library.swing.component.JTextFieldPlan;

public class DLBuscaLancaValor extends FFDialogo implements KeyListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPlan txtValor1 = new JTextFieldPlan( JTextFieldPad.TP_DECIMAL, 10, Aplicativo.casasDecFin );
	private JTextFieldPlan txtValor2 = new JTextFieldPlan( JTextFieldPad.TP_DECIMAL, 10, Aplicativo.casasDecFin );
	private JCheckBoxPad cbFiltraData = new JCheckBoxPad( "Filtrar data", true, false );
	
	public DLBuscaLancaValor( Component cOrig ) {

		super( cOrig );
		
		setTitulo( "Informe o valor para pesquisa" );
		
		setAtribos( 370, 140 );

		txtValor1.setRequerido( true );

		adic( txtValor1		, 7		, 20, 90, 20, "Valor inicial" );
		adic( txtValor2		, 100	, 20, 90, 20, "Valor final" );
		adic( cbFiltraData	, 193	, 20, 90, 20, "Valor final" );

		
		txtValor1.requestFocus();
		txtValor1.addKeyListener( this );
		
		cbFiltraData.setVlrBoolean( true );
		
	}

	public boolean getFiltroData() {
		return cbFiltraData.getVlrBoolean();
	}
	
	public BigDecimal getValor1() {

		return txtValor1.getVlrBigDecimal().floatValue()>0 ? txtValor1.getVlrBigDecimal() : null;
	}

	public BigDecimal getValor2() {

		return txtValor2.getVlrBigDecimal().floatValue()>0 ? txtValor2.getVlrBigDecimal() : null;
		
	}
	
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( getValor1() == null && getValor2() == null ) {
				Funcoes.mensagemInforma( this, "Informe ao menos o valor inicial para busca" );
				txtValor1.requestFocus();

			}
			else {
				super.actionPerformed( evt );
			}
		}
		else if ( evt.getSource() == btCancel ) {
			super.actionPerformed( evt );
		}

	}

	public void keyTyped( KeyEvent kevt ) {
		
		if(kevt.getSource() == txtValor2 && ( (kevt.getKeyChar() == KeyEvent.VK_ENTER ) || ( kevt.getKeyChar() == KeyEvent.VK_TAB )) ) {
			
			btOK.doClick();

		} 
		
	}


}
