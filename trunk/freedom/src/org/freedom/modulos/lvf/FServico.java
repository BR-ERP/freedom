/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTratTrib.java <BR>
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

package org.freedom.modulos.lvf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.ImprimeOS;
import org.freedom.library.JTextAreaPad;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;
import org.freedom.telas.FDados;

public class FServico extends FDados implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodServ = new JTextFieldPad(JTextFieldPad.TP_STRING,5,0);
	
	private JTextAreaPad txaDescServ =  new JTextAreaPad( 500 );
	
	public FServico () {
		super();
		
		lcCampos.setUsaME( false );
		
		setTitulo("Servi�o");
		setAtribos( 50, 50, 550, 200);
		adicCampo(txtCodServ, 7, 20, 70, 20,"CodServ","C�d.Serv.",ListaCampos.DB_PK, true);
		adicDB(txaDescServ, 80, 20, 450, 100,"DescServ","Descri��o do servi�o", true);		
		setListaCampos( true, "SERVICO", "LF");
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcCampos.setQueryInsert(false);    
		btPrevimp.addActionListener(this);
		btImp.addActionListener(this);
		setImprimir(true);
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp)
			imprimir(true);
		else if (evt.getSource() == btImp) 
			imprimir(false);
		super.actionPerformed(evt);
	}
	
	private void imprimir(boolean bVisualizar) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		DLRServico dl = null;
		ImprimeOS imp = null;
		int linPag = 0;
		
		dl = new DLRServico();
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		
		try {
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Lista de Servi�os");
			imp.limpaPags();
			
			sSQL = "SELECT CODSERV,DESCSERV " +
				   "FROM LFSERVICO " +				   
				   "ORDER BY "+dl.getValor();
			
			ps = con.prepareStatement(sSQL);
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow(), 0, imp.normal());
					imp.say(imp.pRow(), 2, "C�digo");
					imp.say(imp.pRow(), 8, "Descri��o");
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(), 0, StringFunctions.replicate("-",79));
				}
				imp.say(imp.pRow() + 1, 0, imp.normal());
				imp.say(imp.pRow(), 2, rs.getString("CodServ"));
				imp.say(imp.pRow(), 8, Funcoes.copy( rs.getString("DescServ"), 73 ));
				if (imp.pRow()>=linPag) {
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(), 0, StringFunctions.replicate("-", 79));
					imp.incPags();
					imp.eject();
				}
			}
			  
			imp.say(imp.pRow() + 1, 0, imp.normal());
			imp.say(imp.pRow(), 0, StringFunctions.replicate("=",79));
			imp.eject();
			  
			imp.fechaGravacao();
			con.commit();
			dl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de Servi�o!\n"+err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			dl = null;
		}
		    
		if (bVisualizar)
			imp.preview(this);
		else
			imp.print();
	}
}
