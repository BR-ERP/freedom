/**
 * @version 29/08/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)DLEditQtd.java <BR>
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
 *         Classe que permite a edi��o da quantidade no Or�amento.
 */

package org.freedom.modulos.std.view.dialog.utility;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLEditQtd extends FFDialogo {
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtItem = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtCodProd = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK  txtQtd = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldFK txtQtdAFat = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldPad txtQtdFat = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JLabelPad lbItem= new JLabelPad( "Item" );
	
	private JLabelPad lbCodProd = new JLabelPad( "C�d.Prod" );

	private JLabelPad lbDescProd = new JLabelPad( "Descri��o do produto" );

	private JLabelPad lbQtd = new JLabelPad( "Qtd" );

	private JLabelPad lbQtdAFat = new JLabelPad( "Qtd A Fat" );
	
	private JLabelPad lbQtdFat = new JLabelPad( "Qtd Fat" );
	
	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinCab = new JPanelPad( 0, 80 );

	private ListaCampos lcItCompra = new ListaCampos( this );
	
	private boolean[] prefs;
	
	private enum ITENS { CODPROD, QTDITORC, QTDAFATITORC, QTDFATITORC };
	
	public DLEditQtd() {

		super();
		
		setTitulo( "Editar quantidade", this.getClass().getName() );
		setAtribos( 500, 200 );
		setResizable( true );
		
		montaTela();
	
	}
	
	public void montaTela(){

		
		setPanel( panelGeral );
		panelGeral.add( pinCab );
			

		pinCab.adic( lbItem, 7, 5, 80, 20 );
		pinCab.adic( txtItem, 7, 25, 80, 20 );
		pinCab.adic( lbCodProd, 90, 5, 80, 20 );
		pinCab.adic( txtCodProd, 90, 25, 80, 20 );
		pinCab.adic( lbDescProd, 173, 5, 250, 20 );
		pinCab.adic( txtDescProd, 173, 25, 250, 20 );
		
		pinCab.adic( lbQtd, 7, 65, 80, 20 );
		pinCab.adic( txtQtd, 7, 85, 80, 20 );
		pinCab.adic( lbQtdAFat, 175, 65, 80, 20 );
		pinCab.adic( txtQtdAFat, 175, 85, 80, 20 );
		pinCab.adic( lbQtdFat, 343, 65, 80, 20 );
		pinCab.adic( txtQtdFat, 343, 85, 80, 20 );
	}

	

	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
	}
	
	private boolean[] getPrefs() {
		
		return prefs;
		
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		montaTela();
		prefs = getPrefs();
	}
}
