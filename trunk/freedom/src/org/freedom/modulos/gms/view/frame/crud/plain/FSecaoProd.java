/**
 * @version 29/03/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FSecaoProd.java <BR>
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
 * Tela para cadastro de se��es de produ��o.
 * 
 */

package org.freedom.modulos.gms.view.frame.crud.plain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.gms.view.dialog.report.DLRSecaoProd;

public class FSecaoProd extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodSecao = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);

	private JTextFieldPad txtDescSecao = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);

	public FSecaoProd() {
		
		super();
		
		setTitulo("Cadastro Se��es de produtos");
		setAtribos( 50, 50, 370, 125);
		
		adicCampo(txtCodSecao, 7, 20, 90, 20,"CodSecao","C�d.Se��o", ListaCampos.DB_PK, true);
		adicCampo(txtDescSecao, 100, 20, 235, 20,"DescSecao","Descri��o da se��o", ListaCampos.DB_SI, true);
		
		setListaCampos( false, "SECAO", "EQ");
	
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcCampos.setQueryInsert(false);    
		
		setImprimir(true);
	
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp) {
			imprimir(true);
		}
		else if (evt.getSource() == btImp) 
			imprimir(false);
		super.actionPerformed(evt);
	}

	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		imp.montaCab();
		imp.setTitulo("Relat�rio de se��es de produ��o");
		DLRSecaoProd dl = new DLRSecaoProd();
		dl.setVisible(true);
		
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		
		String sSQL = "SELECT CODSECAO, DESCSECAO FROM EQSECAO ORDER BY " + dl.getValor();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
		
			ps = con.prepareStatement(sSQL);
			rs = ps.executeQuery();
			imp.limpaPags();
		
			while ( rs.next() ) {
				
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow()+0,0,""+imp.normal());
					imp.say(imp.pRow()+0,0,"");
					imp.say(imp.pRow()+0,2,"C�d.Se��o");
					imp.say(imp.pRow()+0,20,"Descri��o da se��o");
					imp.say(imp.pRow()+1,0,""+imp.normal());
					imp.say(imp.pRow()+0,0,StringFunctions.replicate("-",79));
				}
				
				imp.say(imp.pRow()+1,0,""+imp.normal());
				imp.say(imp.pRow()+0,2,rs.getString("CodSecao"));
				imp.say(imp.pRow()+0,20,rs.getString("DescSecao"));
				
				if (imp.pRow()>=linPag) {
					imp.say(imp.pRow()+1,0,""+imp.normal());
					imp.say(imp.pRow()+0,0,StringFunctions.replicate("=",79));
					imp.incPags();
					imp.eject();
				}
			}

			imp.say(imp.pRow()+1,0,""+imp.normal());
			imp.say(imp.pRow()+0,0,StringFunctions.replicate("=",79));
			imp.eject();

			imp.fechaGravacao();

			con.commit();
			
			dl.dispose();
		}  
		catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de se��es!\n"+err.getMessage(),true,con,err);      
		}

		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
	}
}
