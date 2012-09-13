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
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JScrollPane;

import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLFinalizaOP extends FFDialogo implements FocusListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtQtdPrevOP = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 3 );

	private JTextFieldPad txtQtdFinalOP = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 3 );

	private JTextAreaPad txaJustifcQtdProd = new JTextAreaPad();

	private JLabelPad lJustifcQtdProd = new JLabelPad( "Justificativa" );

	boolean bJust = false;
	
	private String bloqqtdprod = null;

	public DLFinalizaOP(Component cOrig, BigDecimal qtdprevop, String bloqqtdprod){
	
		super( cOrig );

		this.bloqqtdprod = bloqqtdprod;
		txtQtdPrevOP.setVlrBigDecimal( qtdprevop );
		txtQtdFinalOP.setVlrBigDecimal( qtdprevop );

		setTitulo( "Finaliza��o da OP." );
		setAtribos( 250, 140 );

		txtQtdPrevOP.setAtivo( false );
		adic( new JLabelPad( "Qtd. prevista:" ), 7, 5, 100, 20 );
		adic( txtQtdPrevOP, 7, 25, 110, 20 );

		adic( new JLabelPad( "Qtd. produzida:" ), 1120, 5, 100, 20 );
		adic( txtQtdFinalOP, 120, 25, 110, 20 );

		txaJustifcQtdProd.setVisible( false );
		lJustifcQtdProd.setVisible( false );

		adic( lJustifcQtdProd, 7, 45, 300, 20 );
		adic( new JScrollPane( txaJustifcQtdProd ), 7, 65, 222, 50 );

		txtQtdFinalOP.addFocusListener( this );
		txtQtdFinalOP.addKeyListener( this );

	}

	public DLFinalizaOP( Component cOrig, BigDecimal qtdprevop ) {

		super( cOrig );

		txtQtdPrevOP.setVlrBigDecimal( qtdprevop );
		txtQtdFinalOP.setVlrBigDecimal( qtdprevop );

		setTitulo( "Finaliza��o da OP." );
		setAtribos( 250, 140 );

		txtQtdPrevOP.setAtivo( false );
		adic( new JLabelPad( "Qtd. prevista:" ), 7, 5, 100, 20 );
		adic( txtQtdPrevOP, 7, 25, 110, 20 );

		adic( new JLabelPad( "Qtd. produzida:" ), 1120, 5, 100, 20 );
		adic( txtQtdFinalOP, 120, 25, 110, 20 );

		txaJustifcQtdProd.setVisible( false );
		lJustifcQtdProd.setVisible( false );

		adic( lJustifcQtdProd, 7, 45, 300, 20 );
		adic( new JScrollPane( txaJustifcQtdProd ), 7, 65, 222, 50 );

		txtQtdFinalOP.addFocusListener( this );

	}

	public void focusLost( FocusEvent fevt ) {

		if ( fevt.getSource() == txtQtdFinalOP ) {
			if ( ( txtQtdPrevOP.getVlrDouble().doubleValue() != txtQtdFinalOP.getVlrDouble().doubleValue() ) && ( txaJustifcQtdProd.getVlrString().equals( "" ) ) ) {
				setSize( 250, 210 );
				lJustifcQtdProd.setVisible( true );
				txaJustifcQtdProd.setVisible( true );
				setVisible( true );
				txaJustifcQtdProd.requestFocus();
			}
			else {
				setSize( 250, 140 );
				lJustifcQtdProd.setVisible( false );
				txaJustifcQtdProd.setVisible( false );
				txaJustifcQtdProd.setVlrString( "" );
			}
		}
	}

	public void focusGained( FocusEvent fevt ) {

	}

	public BigDecimal getValor() {

		return txtQtdFinalOP.getVlrBigDecimal();
	}

	public String getObs() {

		return txaJustifcQtdProd.getVlrString();
	}

	public void keyPressed( KeyEvent kevt ) {

		super.keyPressed( kevt );
	}

	public void ok() {
		
		if("S".equals( getBloqqtdprod())){
			if ( txtQtdFinalOP.getVlrDouble().doubleValue() > txtQtdPrevOP.getVlrDouble().doubleValue() ) {
				Funcoes.mensagemInforma( this, "N�o � permitido quantidade produzida maior que quantidade prevista!!!" );
				txtQtdFinalOP.setVlrBigDecimal( txtQtdPrevOP.getVlrBigDecimal() );
				txtQtdFinalOP.requestFocus();
				return;
				
			} else if( ( txtQtdPrevOP.getVlrDouble().doubleValue() != getValor().doubleValue() ) && ( txaJustifcQtdProd.getVlrString().equals( "" ) ) ) {
				Funcoes.mensagemErro( this, "Quantidade produzida difere da quantidade prevista.\nJustifique." );
				return;
			}
			else {
				super.ok();
			}
		} else {
			if ( ( txtQtdPrevOP.getVlrDouble().doubleValue() != getValor().doubleValue() ) && ( txaJustifcQtdProd.getVlrString().equals( "" ) ) ) {
				Funcoes.mensagemErro( this, "Quantidade produzida difere da quantidade prevista.\nJustifique." );
				return;
			}
			else {
				super.ok();
			}
		}
	}

	
	public String getBloqqtdprod() {
	
		return bloqqtdprod;
	}

	
}
