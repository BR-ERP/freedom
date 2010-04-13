/**
 * @version 12/01/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)FRDiario.java <BR>
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
 * Relat�rio di�rio de liga��es. 
 * 
 */

package org.freedom.modulos.crm.view.frame.report;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JComboBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FRelatorio;

public class FRDiario extends FRelatorio {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private Vector<String> vVals = new Vector<String>();
	private Vector<String> vLabs = new Vector<String>();
	private JComboBoxPad cbSit = null;
	private JCheckBoxPad cbComp = new JCheckBoxPad("Imprimir completo?","S","N");
	private boolean bComp = false;
	  
	public FRDiario() {
		setTitulo("Ralat�rio di�rio");
		setAtribos(80,80,290,270);
		
		vVals.addElement("");
		vVals.addElement("RJ");
		vVals.addElement("AG");
		vLabs.addElement("<--Selecione-->");	
		vLabs.addElement("Rejeitado");		
		vLabs.addElement("Agendar liga��o/visita");
		cbSit = new JComboBoxPad(vLabs, vVals, JComboBoxPad.TP_STRING, 2 , 0);
			
		txtDataini.setTipo(JTextFieldPad.TP_DATE,10,0);
		txtDatafim.setTipo(JTextFieldPad.TP_DATE,10,0);
		txtDataini.setVlrDate(new Date());
		txtDatafim.setVlrDate(new Date());
		
		adic(new JLabelPad("Periodo:"),7,5,120,20);
		adic(new JLabelPad("De:"),7,25,30,20);
		adic(txtDataini,40,25,100,20);
		adic(new JLabelPad("At�:"),143,25,23,20);
		adic(txtDatafim,169,25,100,20);
		adic(new JLabelPad("Status:"),7,45,269,20);
		adic(cbSit,7,65,262,20);
		adic(cbComp,7,100,253,20);	    
	}
	
	public void imprimir(boolean bVisualizar) {
		
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sDatahist = "";
		String sSit = cbSit.getVlrString();
		String sTit = "";
		String[] sVal;
		ImprimeOS imp = null;
		int linPag = 0;
		
		if (cbComp.getVlrString().equals("S"))
			bComp = true;
		else
			bComp = false;
		
		if (sSit.equals("RJ"))
			sTit = "REJEITADOS";
		else if (sSit.equals("AG"))
			sTit = "AGENDADOS";
		
		try {
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relat�rio di�rio de "+sTit);
			imp.addSubTitulo("RELATORIO DE DI�RIO DE "+sTit+"   -   PERIODO DE :"+txtDataini.getVlrString()+" ATE: "+txtDatafim.getVlrString());			
			imp.limpaPags();
			
			sSQL = "SELECT H.CODHISTTK,H.SITHISTTK,H.DATAHISTTK,H.DESCHISTTK,C.RAZCTO,H.HORAHISTTK,H.CODCTO " +
				   "FROM TKHISTORICO H, TKCONTATO C " +
				   "WHERE H.CODEMP=? AND H.CODFILIAL=? AND H.DATAHISTTK BETWEEN ? AND ? " +
				   "AND C.CODCTO=H.CODCTO AND C.CODEMP=H.CODEMPCO AND C.CODFILIAL=H.CODFILIALCO " +
				   (!sSit.equals("") ? " AND H.SITHISTTK='"+sSit+"' " : "") +    
				   "ORDER BY H.CODHISTTK";
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("TKHISTORICO"));
			ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
			ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow() >= linPag) {
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "+" + StringFunctions.replicate("-",133) + "+" );
					imp.eject();
					imp.incPags();
				}
				if (imp.pRow()==0) {
					imp.impCab(136, true);					   
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|");
					imp.say(imp.pRow(),135, "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + StringFunctions.replicate("-",133) + "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "| Hora ");
					imp.say(imp.pRow(), 10, "| Sit ");
					imp.say(imp.pRow(), 17, "| Contato ");
					if (bComp) {
						imp.say(imp.pRow(), 135, "|");
						imp.say(imp.pRow()+1, 0, imp.comprimido());
						imp.say(imp.pRow(), 0, "|");
						imp.say(imp.pRow(), 5, "Descri��o");
					} else
						imp.say(imp.pRow(), 80, "| Resumo");
					imp.say(imp.pRow(), 135, "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + StringFunctions.replicate("-",133) + "|" );
				}
				if (!Funcoes.sqlDateToStrDate(rs.getDate("DataHistTK")).equals(sDatahist)) {
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + StringFunctions.replicate("-",133) + "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|    Dia: " + Funcoes.sqlDateToStrDate(rs.getDate("DataHistTK")));
					imp.say(imp.pRow(),135, "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + StringFunctions.replicate("-",133) + "|");
				}

				GregorianCalendar calHora = new GregorianCalendar();
				calHora.setTime(rs.getTime("HoraHistTk"));
				
				imp.say(imp.pRow()+1, 0, imp.comprimido());
				imp.say(imp.pRow(), 0, "| " + StringFunctions.strZero(calHora.get(Calendar.HOUR_OF_DAY)+"",2)+":"+StringFunctions.strZero(calHora.get(Calendar.MINUTE)+"",2));
				imp.say(imp.pRow(), 10, "| " + rs.getString("SitHistTK"));
				imp.say(imp.pRow(), 17, "| " + Funcoes.alinhaDir(rs.getInt("CodCto"),8) + " - " + Funcoes.copy(rs.getString("RazCto"),0,50));
				if (bComp) {
					sVal = Funcoes.strToStrArray(rs.getString("DescHistTK"));
					for (int i=0;i<sVal.length;i++) {
						imp.say(imp.pRow(),135, "|");
						imp.say(imp.pRow()+1, 0, imp.comprimido());
						imp.say(imp.pRow(), 0, "|");
						imp.say(imp.pRow(), 5, Funcoes.copy(sVal[i],0,127));
					}
				}
				else
					imp.say(imp.pRow(), 80, "| " + Funcoes.copy(rs.getString("DescHistTK"),0,45).replaceAll("\n","..."));
				
				imp.say(imp.pRow(), 135, "|");
				
				sDatahist = Funcoes.sqlDateToStrDate(rs.getDate("DataHistTK"));				
			}
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "+" + StringFunctions.replicate("-",133) + "+" );
			imp.eject();		  
			imp.fechaGravacao();
			  
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta ao hist�rico!"+err.getMessage(),true,con,err);      
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sDatahist = null;
			sSit = null;
			sTit = null;
			sVal = null;
		}
		    
		if (bVisualizar)
			imp.preview(this);
		else
			imp.print();
	}
}
