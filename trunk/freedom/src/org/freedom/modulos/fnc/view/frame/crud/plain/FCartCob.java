/**
 * @version 08/2007 <BR>
 * @author Setpoint Inform�tica Ltda./ Alex Rodrigues <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FCartCob.java <BR>
 * 
 *                   Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                   modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                   na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                   Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                   sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                   Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                   Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                   Formul�rio de cadastro de Carteiras de Cobran�a.<br>
 * 
 */

package org.freedom.modulos.fnc.view.frame.crud.plain;

import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FCartCob extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodCartCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtDescCartCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCartCobCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldPad txtVariacao = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	protected final ListaCampos lcBanco = new ListaCampos( this, "BO" );

	public FCartCob() {

		super();
		setTitulo( "Cadastro de carteiras de cobran�a" );
		setAtribos( 50, 50, 420, 200 );

		montaListaCampos();

		montaTela();

		setListaCampos( false, "CARTCOB", "FN" );

	}

	private void montaTela() {

		adicCampo( txtCodBanco, 7, 20, 80, 20, "CodBanco", "C�d. banco", ListaCampos.DB_PF, txtNomeBanco, true );
		adicDescFK( txtNomeBanco, 90, 20, 300, 20, "NomeBanco", "Nome do banco" );

		adicCampo( txtCodCartCob, 7, 60, 80, 20, "CodCartCob", "C�d.cart.cob.", ListaCampos.DB_PK, true );
		adicCampo( txtDescCartCob, 90, 60, 237, 20, "DescCartCob", "Descri��o da carteira de cobra�a", ListaCampos.DB_SI, true );		
		adicCampo( txtCartCobCnab, 330, 60, 60, 20, "CartCobCnab", "C�d. cnab", ListaCampos.DB_SI, true );
		adicCampo( txtVariacao, 7, 100, 80, 20, "VariacaoCartCob", "Varia��o", ListaCampos.DB_SI, false );

	}

	private void montaListaCampos() {

		/***************
		 * FNBANCO *
		 ***************/
		Integer numero = new Integer(0);
		new Long(numero.longValue());
		
		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, true ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setNomeCampo( "CodBanco" );
		txtCodBanco.setTabelaExterna( lcBanco, FBanco.class.getCanonicalName() );
		txtCodBanco.setListaCampos( lcBanco );
		txtCodBanco.setFK( true );
		txtNomeBanco.setListaCampos( lcBanco );
	}

	@ Override
	public synchronized void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
	}
}
