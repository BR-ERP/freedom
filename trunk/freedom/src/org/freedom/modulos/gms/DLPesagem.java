/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLBaixaRec.java <BR>
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

package org.freedom.modulos.gms;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

import javax.comm.SerialPort;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.EditEvent;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.infra.driver.scale.AbstractScale;
import org.freedom.infra.driver.scale.EpmSP2400;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.FFDialogo;
import org.freedom.telas.SwingParams;

public class DLPesagem extends FFDialogo implements CarregaListener, FocusListener  {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtPeso1 = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldPad txtPeso2 = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtHora = new JTextFieldPad( JTextFieldPad.TP_TIME, 8, 0 );
	
	private JLabelPad lbpeso1 = new JLabelPad(( "Peso 1" ));
	
	private JLabelPad lbpeso2 = new JLabelPad(( "Peso 2" ));
	
	private SerialPort porta =  null;
	
	private AbstractScale balanca = null;
	
	public DLPesagem( Component cOrig, String tipoprocrecmerc ) {

		super( cOrig );
		int irow = 0; // Variavel para salto na posi��o do campo de data e hora
		
		setTitulo( "Pesagem" );
		
		ajustaCampos();
		
		adic( lbpeso1 , 7, 0, 252, 20 );
		adic( txtPeso1, 7, 20, 252, 50 );

		if( FTipoRecMerc.DESCARREGAMENTO.equals( tipoprocrecmerc ) ) {
			 
			adic( lbpeso2 , 7, 80, 252, 20 );
			adic( txtPeso2, 7, 100, 252, 50 );
			
			irow = 80;
		}

		
		setAtribos( 280, 250 + irow );		
		
		adic( new JLabelPad( "Data" ), 7, 80 + irow, 135, 20 );
		adic( txtData, 7, 100 + irow, 135, 50 );
		
		adic( new JLabelPad( "Hora" ), 147, 80 + irow, 110, 20 );
		adic( txtHora, 147, 100 + irow, 110, 50 );
				
		buscaPeso();
		
	}
	
	private void buscaPeso() {
		
		try {
			
			if(balanca!=null) {
				
				Date data = balanca.getDate();
				Time hora = balanca.getTime();
				BigDecimal peso = balanca.getWeight();
				
				if(data != null) {
					txtData.setVlrDate( data );
				}
				else {
					System.out.println("Data inv�lida na leitura!");
				}
				
				if(hora != null) {
					txtHora.setVlrTime( hora );
				}
				else {
					System.out.println("Hora inv�lida na leitura!");
				}
				if(peso != null) {
					txtPeso1.setVlrBigDecimal( peso );
				}
				else {
					System.out.println("Peso inv�lido na leitura!");
				}
				
				
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void instanciaBalanca() {
		
		balanca = new EpmSP2400( 0 );
		
	}
	
	private void ajustaCampos() {
		

		instanciaBalanca();
		
		txtData.setAtivo( false );
		txtHora.setAtivo( false );		
		
		txtPeso1.setAtivo( balanca!=null );
		txtPeso2.setAtivo( balanca!=null );
		
		
		txtPeso1.setFont( SwingParams.getFontboldextra(20) );
		txtPeso2.setFont( SwingParams.getFontboldextra(20) );
		txtData.setFont( SwingParams.getFontboldextra(10) );
		txtHora.setFont( SwingParams.getFontboldextra(10) );
		
	}
	
	public BigDecimal getPeso1() {
		return txtPeso1.getVlrBigDecimal();
	}

	public BigDecimal getPeso2() {
		return txtPeso2.getVlrBigDecimal();
	}
	
	public Date getData() {
		return txtData.getVlrDate();
	}
	
	public String getHora() {
		return txtHora.getVlrString();
	}
	
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {				
				super.actionPerformed( evt );			
		}
		else {
			super.actionPerformed( evt );
		}
	}

	public void focusGained( FocusEvent fevt ) { }

	public void focusLost( FocusEvent fevt ) {

	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

	}

	public void beforeEdit( EditEvent eevt ) { }

	public void afterEdit( EditEvent eevt ) { }

	public void setConexao( DbConnection cn ) {
	
		super.setConexao( cn );
	}
	

}
