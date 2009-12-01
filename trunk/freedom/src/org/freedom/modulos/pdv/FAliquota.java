/**
 * @version 17/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FAliquota.java <BR>
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
 * 
 */

package org.freedom.modulos.pdv;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.Tabela;
import org.freedom.ecf.app.ControllerECF;
import org.freedom.ecf.driver.AbstractECFDriver;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class FAliquota extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private final JPanelPad pinCab = new JPanelPad( 400, 60 );

	private final JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JTextFieldPad txtAliquota = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 4, 2 );

	private final JButtonPad btInsere = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private final Tabela tab = new Tabela();

	private final JScrollPane spnTab = new JScrollPane( tab );

	private final ControllerECF ecf;
	
	private List<String> aliquotas;
	
	private int sizeAliquotas;
	

	public FAliquota() {

		setTitulo( "Ajusta aliquotas" );
		setAtribos( 100, 150, 395, 195 );
		
		ecf = new ControllerECF( 
				AplicativoPDV.getEcfdriver(), 
				AplicativoPDV.getPortaECF(), 
				AplicativoPDV.bModoDemo, 
				AplicativoPDV.getEcflayout() );
		
		montaTela();
		
		setToFrameLayout();
		
		carregaTabela();
	}
		
	private void montaTela() {
		
		btInsere.setPreferredSize( new Dimension( 30, 30 ) );
		btInsere.setToolTipText( "Insere al�quota" );
		btInsere.addActionListener( this );

		setPanel( pnCli );
		
		pnCli.add( spnTab, BorderLayout.CENTER );
		pnCli.add( pinCab, BorderLayout.NORTH );

		pinCab.adic( new JLabelPad( "Inserir aliquota" ), 7, 5, 87, 20 );
		pinCab.adic( txtAliquota, 7, 25, 87, 20 );
		pinCab.adic( btInsere, 120, 15, 30, 30 );

		tab.adicColuna( "" );
		tab.adicColuna( "" );
		tab.adicColuna( "" );
		tab.adicColuna( "" );

		tab.setTamColuna( 95, 0 );
		tab.setTamColuna( 95, 1 );
		tab.setTamColuna( 95, 2 );
		tab.setTamColuna( 95, 3 );
		
		tab.setFont( new Font( "Arial", Font.PLAIN, 12 ) );		
	}

	private void insereAliquota() {

		DecimalFormat df = new DecimalFormat( "00.00" );
		String strAliquota = df.format( txtAliquota.getVlrBigDecimal().doubleValue() ).replaceAll( ",", "" );
		
		if ( aliquotas.contains( strAliquota ) ) {
			Funcoes.mensagemErro( this, "Al�quota j� foi cadastrada!" );
		}
		else {
			if ( sizeAliquotas > 15 ) {
				Funcoes.mensagemErro( this, "Quantidade maxima de aliquotas j� foi atingida!" );
			}
			else {
				if ( ! ecf.programaAliquota( txtAliquota.getVlrBigDecimal(), AbstractECFDriver.ICMS ) ) {
					Funcoes.mensagemErro( this, ecf.getMessageLog() );
				}					
				carregaTabela();
			}
		}
	}

	private void carregaTabela() {
		
		aliquotas = ecf.getAliquotas();
		
		tab.limpa();
		sizeAliquotas = 0;
		
		String aliq = null;
		DecimalFormat df = new DecimalFormat( "00.00" );
		
		int row = 0;
		int col = 0;
		int size = aliquotas.size();
		int oldRow = -1;
		
		float aliquota = 0.0f;
		
		for ( int i=0; i < size; i++ ) {
			
			aliquota = new Float( aliquotas.get( i ) ).floatValue();
			
			if ( aliquota > 0.0f ) {
				
				if ( row != oldRow ) {
					tab.adicLinha();
					oldRow = row;
				}
				
				aliq = 
					"T" + 
					Funcoes.strZero( String.valueOf( i + 1 ), 2 ) + 
					" = " + 
					df.format( aliquota / 100 ) + " %";
				
				tab.setValor( aliq, row, col++ );
				
				if ( col == 4 ) {					
					col = 0;
					row++;	
				}		
				
				sizeAliquotas++;
			}
		}
	}

	@Override
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btInsere ) {
			insereAliquota();
		}
	}

}
