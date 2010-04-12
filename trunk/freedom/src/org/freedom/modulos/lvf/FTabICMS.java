/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.lvf <BR>
 * Classe: @(#)FTabICMS.java <BR>
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
 * Tabela de al�quotas de ICMS por estado. 
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
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;

public class FTabICMS extends FDados implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtUFTabICMS = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
	private JTextFieldPad txtAliqTabICMSInter = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,9,2);
	private JTextFieldPad txtAliqTabICMSIntra = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,9,2);

	public FTabICMS() {
		super();
		setTitulo("Cadastro de al�quotas de ICMS");
		setAtribos( 50, 50, 330, 125);
		adicCampo(txtUFTabICMS, 7, 20, 50, 20,"UFTI","UF", ListaCampos.DB_PK, true);
		adicCampo(txtAliqTabICMSInter, 60, 20, 110, 20,"ALIQTI","Al�q. Interestadual", ListaCampos.DB_SI, true);
		adicCampo(txtAliqTabICMSIntra, 173, 20, 110, 20,"ALIQINTRATI","Al�q. Intraestadual", ListaCampos.DB_SI, true);
		setListaCampos( false, "TABICMS", "LF");
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
		ImprimeOS imp = null;
		int linPag = 0;

		try {
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relat�rio de Al�quotas");
			imp.limpaPags();
			
			sSQL = "SELECT UFTI,ALIQTI " +
				   "FROM LFTABICMS " +
				   "WHERE CODEMP=? AND CODFILIAL=? " +
				   "ORDER BY UFTI";
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("LFTABICMS"));
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow(), 0, imp.normal());
					imp.say(imp.pRow(), 2, "UF");
					imp.say(imp.pRow(), 20, "Al�quota");
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(),0,StringFunctions.replicate("-",79));
				}
				imp.say(imp.pRow() + 1, 0, imp.normal());
				imp.say(imp.pRow(), 2, rs.getString("UFTI"));
				imp.say(imp.pRow(), 20, Funcoes.strDecimalToStrCurrency(9,2,rs.getString("AliqTi")));
				if (imp.pRow()>=linPag) {
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(),0,StringFunctions.replicate("-",79));
					imp.incPags();
					imp.eject();
				}
			}
			
			imp.say(imp.pRow() + 1, 0, imp.normal());
			imp.say(imp.pRow(),0,StringFunctions.replicate("=",79));
			imp.eject();      
			imp.fechaGravacao();
			  
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro na consulta � tabela de icms!\n"+err.getMessage());      
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		    
		if (bVisualizar) 
			imp.preview(this);
		else 
			imp.print();
	}
}
