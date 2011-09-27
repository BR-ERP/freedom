/**
 * @version 27/09/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm <BR>
 *         Classe: @(#)FMarcador.java <BR>
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
 *         Tela de cadastro de Marcador.
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.plain;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.Navegador;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.crm.business.object.Contrato;

public class FSitContr extends FDados {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodContr= new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescContr = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtDtPrev = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JLabelPad lbInfoSit = new JLabelPad( " Situa��o do Contrato" );
	
	private JPanelPad pinLbInfoCaixa = new JPanelPad( 53, 15 );
	
	private JPanelPad pinInfoSit = new JPanelPad( 300, 150 );
	
	private JComboBoxPad cbSitContr = new JComboBoxPad( Contrato.getSitprojName(), Contrato.getSitprojValue(), JComboBoxPad.TP_STRING, 2, 0	 );
	
	public FSitContr( ) {

		super();
		montaTela();

	}
	
	private void montaTela(){
		
		setTitulo( "Marcador" );
		setAtribos( 50, 50, 600, 350 );
	
		
		adicCampo( txtCodContr, 7, 20, 100, 20, "CodContr", "Cod.Contr", ListaCampos.DB_PK, true );		
		adicCampo( txtDescContr, 110, 20,400, 20, "DescContr", "Descri��o do Contrato", ListaCampos.DB_SI, true );
		adicCampo( txtDtPrev, 210, 60, 100, 25, "DtPrevFin", "Data de previs�o", ListaCampos.DB_SI, true );
		adicDB( cbSitContr,  7, 60, 200, 25,"SitContr", "Situa��o", true );
		
		/*
		pinLbInfoCaixa.adic( lbInfoSit, 0, 0, 350, 15 );
		pinLbInfoCaixa.tiraBorda();
		
		adic( pinLbInfoCaixa, 10, 52, 350, 15 );
		adic( pinInfoSit, 7, 60, 400, 180 );
		*/
		nav.setNavigation( true );
		nav.setAtivo( Navegador.BT_EXCLUIR, false  );
		nav.setAtivo( Navegador.BT_NOVO, false  );		
		setListaCampos( true, "Contrato", "VD" );
	
	}

	private void montaListaCampos() {	

	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
	}
}