/**
 * @version 17/07/2008 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.pcp <BR>
 *         Classe:
 * @(#)DLFechaQual.java <BR>
 * 
 *                      Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                      modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                      na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                      Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                      sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                      Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                      Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                      de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                      Coment�rios sobre a classe...
 */
package org.freedom.modulos.pcp.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

import java.util.HashMap;
import java.util.Vector;

public class DLSelProduto extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private final ListaCampos lcProd = new ListaCampos( this, "" );

	private Integer codprod;

	public DLSelProduto( Component cOrig) {
		super(cOrig);
		
		setTitulo( "Qualidade" );
		setAtribos( 360, 150 );

		montaListaCampos();
		montaTela();


	}

	private void montaTela() {

		adic( txtCodProd, 7, 20, 80, 20, "C�d.prod" );
		adic( txtDescProd, 90, 20, 250, 20, "Descri��o do produto" );

	}

	public void montaListaCampos() {

		/*************
		 * Produto *
		 *************/

		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Refer�ncia", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProd, null );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
	}
	
	public void setConexao( DbConnection con ) {

		super.setConexao( con );
		lcProd.setConexao( con );
	}
	
	
	public void actionPerformed( ActionEvent evt ) {
		if ( evt.getSource() == btOK ) {
			codprod = txtCodProd.getVlrInteger();
			
			if (codprod <= 0) {
				Funcoes.mensagemInforma( null, "C�digo do produto � obrigat�rio!!!" );
				return;
			}
		}
		
		super.actionPerformed( evt );
	}

	
	public Integer getCodprod() {
	
		return codprod;
	}

}
