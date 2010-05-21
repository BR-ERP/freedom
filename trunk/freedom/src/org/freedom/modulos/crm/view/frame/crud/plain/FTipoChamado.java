/**
 * @version 02/11/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTipoChamado.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Tela para cadastro de tipo de chamado.
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.plain;

import java.awt.event.ActionListener;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FTipoChamado extends FDados implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodChamado = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDescTipoChamado = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	
	public FTipoChamado () {
		
		super();
		
		nav.setNavigation( true );
		
		setTitulo("Cadastro de Tipo de Chamados");
		setAtribos( 50, 50, 380, 145);
		
		adicCampo(txtCodChamado, 7, 20, 70, 20,"CodTpChamado","C�d.tp.cham.", ListaCampos.DB_PK, true);
		adicCampo(txtDescTipoChamado, 80, 20, 250, 20,"DescTpChamado","Descri��o do tipo de chamado", ListaCampos.DB_SI, true);
		
		setListaCampos( true, "TIPOCHAMADO", "CR");
		
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcCampos.setQueryInsert(false);    
		
		setImprimir(false);
	}
		
}
