/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTipoFor.java <BR>
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
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.crm;
import java.awt.event.ActionListener;

import org.freedom.library.ListaCampos;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.telas.FDados;

public class FTipoCont extends FDados implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCodTipoCont = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDescTipoCont = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
	
	public FTipoCont () {
		super();
		setTitulo("Cadastro de Tipo de Contatos");
		setAtribos( 50, 50, 380, 145);
		adicCampo(txtCodTipoCont, 7, 20, 70, 20,"CodTipoCont","C�d.tp.cont.", ListaCampos.DB_PK, true);
		adicCampo(txtDescTipoCont, 80, 20, 250, 20,"DescTipoCont","Descri��o do tipo de contato", ListaCampos.DB_SI, true);
		setListaCampos( true, "TIPOCONT", "TK");
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcCampos.setQueryInsert(false);    
		setImprimir(true);
	}
		
}
