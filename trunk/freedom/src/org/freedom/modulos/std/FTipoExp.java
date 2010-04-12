/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTipoExp.java <BR>
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

package org.freedom.modulos.std;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class FTipoExp extends FDados implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCod= new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDesc= new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);

	public FTipoExp () {
		super();
		setTitulo("Cadastro de tipos de expositores");
		setAtribos(50, 50, 350, 125);
		adicCampo(txtCod, 7, 20, 70, 20,"CodTipoExp","C�d.tp.exp.", ListaCampos.DB_PK, true);
		adicCampo(txtDesc, 80, 20, 250, 20,"DescTipoExp","Descri��o do tipo de expositor", ListaCampos.DB_SI, true);
		setListaCampos( true, "TIPOEXP", "EQ");
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcCampos.setQueryInsert(false);    
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
		DLRTipoExp dl = null;
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = 0;
		
		dl = new DLRTipoExp();
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}

		try {
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relat�rio de Tipos de Expositores");
			imp.limpaPags();
			
			sSQL = "SELECT CODTIPOEXP,DESCTIPOEXP " +
				   "FROM EQTIPOEXP " +
				   "WHERE CODEMP=? AND CODFILIAL=? " +
				   "ORDER BY "+dl.getValor();
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("EQTIPOEXP"));
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow(), 0, imp.normal());
					imp.say(imp.pRow(), 2, "C�d.tp.exp.");
					imp.say(imp.pRow(), 30, "Descri��o");
					imp.say(imp.pRow() + 1, 0, "" + imp.normal());
					imp.say(imp.pRow(), 0, StringFunctions.replicate("-",79));
				}
				imp.say(imp.pRow() + 1, 0, imp.normal());
				imp.say(imp.pRow(), 2, rs.getString("CodTipoExp"));
				imp.say(imp.pRow(), 30, rs.getString("DescTipoExp"));
				if (imp.pRow()>=linPag) {
					imp.say(imp.pRow() + 1 , 0, imp.normal());
					imp.say(imp.pRow(), 0, StringFunctions.replicate("-",79));
					imp.incPags();
					imp.eject();
				}
			}
			  
			imp.say(imp.pRow() + 1 , 0, imp.normal());
			imp.say(imp.pRow(), 0, StringFunctions.replicate("=",79));
			imp.eject();
			  
			imp.fechaGravacao();
			
			con.commit();
			dl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de tipos de cliente!\n"+err.getMessage(),true,con,err);
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
