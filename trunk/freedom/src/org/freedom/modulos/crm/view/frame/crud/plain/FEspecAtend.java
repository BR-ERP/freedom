/**
 * @version 20/01/2011 <BR>
 * @author Setpoint Inform�tica Ltda.
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm <BR>
 *         Classe: @(#)FOrigContato.java <BR>
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
 * 	Tela para cadastrameto de tipos de especifica��o de atendimentos
 * 
 */
package org.freedom.modulos.crm.view.frame.crud.plain;

import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;

public class FEspecAtend extends FDados {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodEspec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtDescSpec = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private final JCheckBoxPad cbPgComiEspec = new JCheckBoxPad( "Paga comiss�o ?", "S", "N" );
	
	private final JCheckBoxPad cbCobCliEspec = new JCheckBoxPad( "Cobra do cliente ?", "S", "N" );
	
	private final JCheckBoxPad cbContMetaEspec = new JCheckBoxPad( "Conta para meta do atendente ?", "S", "N" );
	
	private final JTextFieldPad txtTempoMinCobEspec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtTempoMaxCobEspec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldPad txtPercComiEspec = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 2, Aplicativo.casasDecFin );
	
	public FEspecAtend() {

		super();
		
		setTitulo( "Especifica��o de Atendimentos" );
		
		setAtribos( 15, 30, 500, 300 );
		
		montaTela(); 
		
	}

	private void montaTela() {
		
		nav.setNavigation( true );

		adicCampo( 	txtCodEspec, 			 7, 	 20, 	100, 	20, "CodEspec", 		"C�d.Espec.", 					ListaCampos.DB_PK, true );
		adicCampo( 	txtDescSpec, 		   110, 	 20, 	360, 	20, "DescEspec", 		"Descri��o da especifica��o", 	ListaCampos.DB_SI, true );
		
		adicCampo( 	txtTempoMinCobEspec, 	 7, 	 60, 	100, 	20, "TempoMinCobEspec", "Tempo Min.(m)", 				ListaCampos.DB_SI, true );
		adicCampo( 	txtTempoMaxCobEspec,   110, 	 60, 	100, 	20, "TempoMaxCobEspec", "Tempo Max.(m)", 				ListaCampos.DB_SI, true );
		adicCampo( 	txtPercComiEspec, 	   213, 	 60, 	100, 	20, "PercComiEspec", 	"% Comiss�o", 					ListaCampos.DB_SI, true );
		
		adicDB( 	cbCobCliEspec, 			 7, 	100, 	300, 	20, "CobCliEspec", 		"", 												true );
		adicDB( 	cbContMetaEspec, 		 7, 	140, 	300, 	20, "ContMetaEspec", 	"", 												true );
		adicDB( 	cbPgComiEspec, 			 7, 	180, 	300, 	20, "PgComiEspec", 		"", 												true );
		
		

		setListaCampos( true, "ESPECATEND", "AT" );
	}

}
