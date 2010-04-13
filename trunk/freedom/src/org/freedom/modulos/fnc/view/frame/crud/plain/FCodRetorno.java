/**
 * @version 28/02/2007 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FCodRetorno.java <BR>
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
 * Tela de cadastros de c�digos de retorno.
 * 
 */
package org.freedom.modulos.fnc.view.frame.crud.plain;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;

public class FCodRetorno extends FDados implements RadioGroupListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldPad txtTipoFebraban = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodRet = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldPad txtDescRet = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JRadioGroup<?, ?> rgTipoFebraban;

	private final ListaCampos lcBanco = new ListaCampos( this, "BO" );

	private Vector<String> vLabs = new Vector<String>();

	private Vector<String> vVals = new Vector<String>();
	
	private JRadioGroup<String, String> rgTipoRetorno = null;

	public FCodRetorno() {

		setTitulo( "C�digos de retorno" );
		setAtribos( 200, 60, 387, 310 );

		vLabs.add( "SIACC" );
		vLabs.add( "CNAB" );
		vVals.add( "01" );
		vVals.add( "02" );
		rgTipoFebraban = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, true ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );

		montaTela();

		setListaCampos( false, "FBNCODRET", "FN" );

		rgTipoFebraban.addRadioGroupListener( this );
	}

	private void montaTela() {

		adic( new JLabel( "Tipo:"), 7, 0, 353, 20  );
		adic( rgTipoFebraban, 7, 20, 353, 30  );	
		
		txtTipoFebraban.setVlrString( "01" );		
		lcCampos.add( new GuardaCampo( txtTipoFebraban, "TipoFebraban", "Tipo", ListaCampos.DB_PK, true ) );

		
		Vector<String> vTipoRetornoLabs = new Vector<String>();		

		vTipoRetornoLabs.addElement( "Rejei��o de entrada" );
		vTipoRetornoLabs.addElement( "Confirma��o de entrada" );
		vTipoRetornoLabs.addElement( "Advert�ncia" );
		vTipoRetornoLabs.addElement( "Confirma��o de baixa" );		
		vTipoRetornoLabs.addElement( "Rejei��o de baixa" );
		vTipoRetornoLabs.addElement( "Indefinido" );

		Vector<String> vTipoRetornoVals = new Vector<String>();
		vTipoRetornoVals.addElement( "RE" );
		vTipoRetornoVals.addElement( "CE" );
		vTipoRetornoVals.addElement( "AD" );
		vTipoRetornoVals.addElement( "CB" );		
		vTipoRetornoVals.addElement( "RB" );
		vTipoRetornoVals.addElement( "IN" );

		rgTipoRetorno = new JRadioGroup<String, String>( 3, 2, vTipoRetornoLabs, vTipoRetornoVals );
		
		adicCampo( txtCodBanco, 7, 70, 90, 20, "CodBanco", "C�d.banco", ListaCampos.DB_PF, txtNomeBanco, true );
		adicDescFK( txtNomeBanco, 100, 70, 260, 20, "NomeBanco", "Nome do banco" );
		adicCampo( txtCodRet, 7, 110, 90, 20, "CodRet", "C�d.retorno", ListaCampos.DB_PK, true );
		adicCampo( txtDescRet, 100, 110, 260, 20, "DescRet", "Descri��o do retorno", ListaCampos.DB_SI, true );
		adicDB( rgTipoRetorno, 7, 150, 353, 80, "tiporet", "Tipo de retorno", true );
	}

	public void valorAlterado( RadioGroupEvent evt ) {

		if ( evt.getIndice() >= 0 ) {
			lcCampos.limpaCampos( true );
			txtTipoFebraban.setVlrString( (String) vVals.elementAt( evt.getIndice() ) );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
	}

}
