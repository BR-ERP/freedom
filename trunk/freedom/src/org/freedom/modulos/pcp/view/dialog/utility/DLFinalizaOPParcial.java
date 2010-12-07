/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)DLDescontItVenda.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Coment�rios sobre a classe...
 */

package org.freedom.modulos.pcp.view.dialog.utility;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import javax.swing.JScrollPane;

import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLFinalizaOPParcial extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtQtdPrevOP = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 3 );

	private JTextFieldPad txtQtdEnt = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 3 );

	private JTextAreaPad txaObsEnt = new JTextAreaPad();

	private JLabelPad lObsEnt = new JLabelPad( "Observa��es" );

	boolean bJust = false;

	public DLFinalizaOPParcial( Component cOrig, BigDecimal qtdprev ) {

		super( cOrig );

		setTitulo( "Finaliza��o parcial da OP." );
		setAtribos( 250, 300 );

		txtQtdPrevOP.setVlrBigDecimal( qtdprev );
		txtQtdPrevOP.setAtivo( false );

		adic( txtQtdPrevOP, 7, 25, 110, 20, "Qtd. prevista" );
		adic( txtQtdEnt, 120, 25, 110, 20, "Qtd. produzida:" );

		adic( lObsEnt, 7, 45, 300, 20 );
		adic( new JScrollPane( txaObsEnt ), 7, 65, 222, 140 );

	}

	public void focusGained( FocusEvent fevt ) {

	}

	public BigDecimal getQtdEnt() {

		return txtQtdEnt.getVlrBigDecimal();
	}

	public String getObs() {

		return txaObsEnt.getVlrString();
	}
	
	public void keyPressed( KeyEvent kevt ) {

		super.keyPressed( kevt );
	}

	public void ok() {
		
		BigDecimal qtdent = txtQtdEnt.getVlrBigDecimal();		
		BigDecimal qtdprev = txtQtdPrevOP.getVlrBigDecimal();
		
		if( ( qtdent!=null && qtdent.floatValue()>0) && qtdent.compareTo( qtdprev )<=0 ){
			
			super.ok();
			
		}
		else {			
			
			Funcoes.mensagemInforma( this, "A quantidade informada n�o � v�lida!\n pois � maior que a quantidade prevista ou nula." );
			
		}
		
	}
}
