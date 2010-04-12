/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe: @(#)FTipoAgenda.java <BR>
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
 * Tela de cadastro de tipos de agendamento
 * 
 */

package org.freedom.modulos.atd; 

import java.awt.BorderLayout;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.ListaCampos;
import org.freedom.library.Navegador;
import org.freedom.library.swing.JTextFieldPad;

import javax.swing.JLabel;

import org.freedom.telas.FDialogo;

public class FTipoAgenda extends FDialogo {
	
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCodTipoAGD= new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDescTipoAGD= new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
	public ListaCampos lcCampos = new ListaCampos(null); 
	public Navegador nav = new Navegador(false); 
	  
	public FTipoAgenda () {
		  
		super();
		setTitulo("Tipos de Agendamentos", this.getClass().getName() );
		setAtribos( 345, 140 );
		setToFrameLayout();
		
		pnRodape.add(nav, BorderLayout.WEST);
		nav.setListaCampos(lcCampos);
		lcCampos.setNavegador(nav);
		
		lcCampos.add(new GuardaCampo( txtCodTipoAGD, "CodTipoAGD", "C�d.tp.agd.", ListaCampos.DB_PK, true));
		lcCampos.add(new GuardaCampo( txtDescTipoAGD, "DescTipoAGD", "Descri��o do tipo de agendamento", ListaCampos.DB_SI,true));
		lcCampos.montaSql(true, "TIPOAGENDA", "SG");    
		lcCampos.setReadOnly(false);
		txtCodTipoAGD.setTabelaExterna(lcCampos);
		txtCodTipoAGD.setFK(true);
		txtCodTipoAGD.setNomeCampo("CodTipoAGD");
		
		adic(new JLabel("C�d.tp.agd."), 7, 10, 70, 20);
		adic(txtCodTipoAGD, 7, 30, 70, 20);
		adic(new JLabel("Descri��o do tipo de agendamento"), 80, 10, 240, 20);
		adic(txtDescTipoAGD, 80, 30, 240, 20);
		    
		lcCampos.setQueryInsert(false);
	    
	}
	
	public void setConexao(DbConnection con) {
		//super.setConexao(con);
		lcCampos.setConexao(con);
	}
  
}
