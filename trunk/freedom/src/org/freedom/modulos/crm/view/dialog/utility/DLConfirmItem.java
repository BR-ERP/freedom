/**
 * @version 14/03/2014 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm.view.dialog.utility <BR>
 *         Classe: @(#)DLConfirmItem.java <BR>
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
 *         Confirma��o de item para or�amento
 * 
 */

package org.freedom.modulos.crm.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;

import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.std.orcamento.bean.Item;

public class DLConfirmItem extends FFDialogo implements FocusListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldFK txtCodprod = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 10, 0 );
	
	private final JTextFieldFK txtDescprod = new JTextFieldFK( JTextFieldFK.TP_STRING, 100, 0);
	
	private final JTextFieldPad txtQtd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private final JTextFieldPad txtPreco = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecPre );

	private final JTextFieldPad txtPercDesc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecPre );

	private final JTextFieldPad txtVlrDesc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecPre );

	private final JTextFieldFK txtVlrLiq = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecPre );

	private Item result = null;

	private Integer codemp;
	
	private Integer codfilial;
	
	private static enum FIELDS_CONFIRM {CODPROD, DESCPROD, QTD, PRECO, PERCDESC, VLRDESC, VLRLIQ}

	private BigDecimal percdesc_old = null;
	
	private BigDecimal vlrdesc_old = null;
	
	public DLConfirmItem(Component cOrig) {

		super(cOrig);
		setTitulo( "Confirma��o de item" );
		setAtribos( 600, 250 );
		montaTela();
	
	}
	
	public void montaTela(){

		adic( txtCodprod, 7, 25, 70, 20, "C�d.prod." );
		adic( txtDescprod, 80, 25, 390, 20, "Descri��o");
		adic( txtQtd, 7, 65, 90, 20, "Quantidade" );
		adic( txtPreco, 100, 65, 90, 20, "Pre�o");
		adic( txtPercDesc, 193, 65, 90, 20, "% Desconto");
		adic( txtVlrDesc, 286, 65, 90, 20, "Vlr.desconto");
		adic( txtVlrLiq, 379, 65, 90, 20, "Vlr.l�quido");
		txtQtd.addFocusListener( this );
		txtPreco.addFocusListener( this );
		txtPercDesc.addFocusListener( this );
		txtVlrDesc.addFocusListener( this );
		
	}
	
	public void setValues(Item item) {
		setCodemp( item.getCodemp() );
		setCodfilial( item.getCodfilial() );
		txtCodprod.setVlrInteger( item.getCodprod() );
		txtDescprod.setVlrString( item.getDescprod() );
		txtQtd.setVlrBigDecimal( item.getQtd() );
		txtPreco.setVlrBigDecimal( item.getPreco() );
		txtPercDesc.setVlrBigDecimal( item.getPercdesc() );
		txtVlrDesc.setVlrBigDecimal( item.getVlrdesc() );
		txtVlrLiq.setVlrBigDecimal( item.getVlrliq() );
		
	}
	
	public void actionPerformed( ActionEvent evt ) {
		if (evt.getSource()==btOK) {
            result = new Item(getCodemp(), getCodfilial(), txtCodprod.getVlrInteger(), txtDescprod.getVlrString());
            result.setQtd( txtQtd.getVlrBigDecimal() );
            result.setPreco( txtPreco.getVlrBigDecimal() );
            result.setPercdesc( txtPercDesc.getVlrBigDecimal() );
            result.setVlrdesc( txtVlrDesc.getVlrBigDecimal() );
            result.setVlrliq( txtVlrLiq.getVlrBigDecimal() );
		} else if (evt.getSource()==btCancel) {
			result = null;
		}
		super.actionPerformed( evt );
	}

	
	public Item getResult() {
	
		return result;
	}

	
	public void setResult( Item result ) {
	
		this.result = result;
	}

	
	public Integer getCodemp() {
	
		return codemp;
	}

	
	public void setCodemp( Integer codemp ) {
	
		this.codemp = codemp;
	}

	
	public Integer getCodfilial() {
	
		return codfilial;
	}

	
	public void setCodfilial( Integer codfilial ) {
	
		this.codfilial = codfilial;
	}

	public void focusGained( FocusEvent e ) {
		if (e.getSource()==txtPercDesc) {
			percdesc_old = txtPercDesc.getVlrBigDecimal();
		} else if (e.getSource()==txtVlrDesc) {
			vlrdesc_old = txtVlrDesc.getVlrBigDecimal();
		}
	}

	public void focusLost( FocusEvent e ) {
		FIELDS_CONFIRM param = null;
		if (e.getSource()==txtQtd) {
			param = FIELDS_CONFIRM.QTD;
		} else if (e.getSource()==txtPreco) {
			param = FIELDS_CONFIRM.PRECO;
		} else if (e.getSource()==txtPercDesc) {
			param = FIELDS_CONFIRM.PERCDESC;
		} else if (e.getSource()==txtVlrDesc) {
			param = FIELDS_CONFIRM.VLRDESC;
		} else if (e.getSource()==txtVlrLiq) {
			param = FIELDS_CONFIRM.VLRLIQ;
		}
		calcVlrLiq(param);
		
	}

	private void calcVlrLiq(FIELDS_CONFIRM field) {
		BigDecimal qtd = txtQtd.getVlrBigDecimal();
		BigDecimal preco = txtPreco.getVlrBigDecimal();
		BigDecimal percdesc = txtPercDesc.getVlrBigDecimal();
		BigDecimal vlrdesc = txtVlrDesc.getVlrBigDecimal();
		BigDecimal vlrliq = txtVlrLiq.getVlrBigDecimal();
		if (field==FIELDS_CONFIRM.PERCDESC && !percdesc.equals( percdesc_old ))  {
			vlrdesc = qtd.multiply( preco ).multiply( percdesc ).divide( new BigDecimal(100f) );
			txtVlrDesc.setVlrBigDecimal( vlrdesc );
		}
		if (field==FIELDS_CONFIRM.VLRDESC && !vlrdesc.equals( vlrdesc_old ))  {
			percdesc = BigDecimal.ZERO;
			txtPercDesc.setVlrBigDecimal( percdesc );
		}
		
		if (field!=FIELDS_CONFIRM.VLRLIQ ) {
			vlrliq = qtd.multiply( preco ).subtract( vlrdesc );
			txtVlrLiq.setVlrBigDecimal( vlrliq );
		} 
		
	}
}
