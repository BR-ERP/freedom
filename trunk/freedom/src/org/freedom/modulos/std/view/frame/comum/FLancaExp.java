/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FLancaExp.java <BR>
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

package org.freedom.modulos.std.view.frame.comum;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.std.view.dialog.report.DLRLancaExp;

public class FLancaExp extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodLExp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTipoExp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTipoExp = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescCli = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldPad txtDtRExp = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtDtSExp = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtQtdExp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private ListaCampos lcTipoExp = new ListaCampos(this,"TE");
	private ListaCampos lcCli = new ListaCampos(this,"CL");
	public FLancaExp() {
		super();
		setTitulo("Lan�amentos de Expositores");
		setAtribos(50,50,400,200);

		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		
		lcTipoExp.add(new GuardaCampo( txtCodTipoExp, "CodTipoExp", "C�d.tp.exp.", ListaCampos.DB_PK, false));
    		lcTipoExp.add(new GuardaCampo( txtDescTipoExp, "DescTipoExp", "descri��o do tipo de expositor", ListaCampos.DB_SI, false));
		lcTipoExp.montaSql(false, "TIPOEXP", "EQ");   
                lcTipoExp.setQueryCommit(false);
		lcTipoExp.setReadOnly(true);
		txtCodTipoExp.setTabelaExterna(lcTipoExp);
		
		lcCli.add(new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false));
    		lcCli.add(new GuardaCampo( txtDescCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false));
                lcCli.setQueryCommit(false);
		lcCli.montaSql(false, "CLIENTE", "VD");    
                lcCli.setQueryCommit(false);
		lcCli.setReadOnly(true);
		txtCodCli.setTabelaExterna(lcCli);
		
		adicCampo(txtCodLExp, 7, 20, 80, 20, "CodLExp", "C�d.exp.", ListaCampos.DB_PK, true);
		adicCampo(txtQtdExp, 90, 20, 77, 20, "QtdLExp", "Quantidade", ListaCampos.DB_SI, true);
		adicCampo(txtDtSExp, 170, 20, 97, 20, "DtLExp", "Data saida", ListaCampos.DB_SI, true);
		adicCampo(txtDtRExp, 270, 20, 100, 20, "DtRetLExp", "Data retorno", ListaCampos.DB_SI, false);
		adicCampo(txtCodTipoExp, 7, 60, 80, 20, "CodTipoExp", "C�d.tp.exp.", ListaCampos.DB_FK, txtDescTipoExp, true);
		adicDescFK(txtDescTipoExp, 90, 60, 250, 20, "DescTipoExp", "Descri��o do tipo de expositor");
		adicCampo(txtCodCli, 7, 100, 80, 20, "CodCli", "C�d.cli.", ListaCampos.DB_FK, txtDescCli, true);
		adicDescFK(txtDescCli, 90, 100, 250, 20, "RazCli", "Descri��o do cliente");
		setListaCampos( true, "LANCTOEXP", "EQ");
        lcCampos.setQueryInsert(false); 
        setImprimir(true);
	}
	public void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		String sWhere = "";
		imp.setTitulo("Relat�rio de Lancamentos de Expositores");
 		DLRLancaExp dlle = new DLRLancaExp();
		dlle.setConexao(con);
		dlle.setVisible(true);
		if (!dlle.OK) {
			dlle.dispose();
			return;
		}
		String sRet[] = dlle.getValores();
		dlle.dispose();
		if (sRet[2].trim().length() > 0) {
			sWhere = " AND CODCLI = "+sRet[2];
		}
		else if (sRet[3].trim().length() > 0) {
			sWhere = " AND CODVEND = "+sRet[3];
		}
		if (sRet[4].trim().length() > 0) {
			sWhere += " AND CODTIPOEXP = "+sRet[4];
		}
		if (sRet[5].equals("R")) {
			sWhere += " AND DTRETLEXP IS NOT NULL";
		}
		else if (sRet[5].equals("E")) {
			sWhere += " AND DTRETLEXP IS NULL";
		}
		String sSQL = "SELECT C.CODVEND,V.NOMEVEND,C.CODCLI,C.RAZCLI,"+
			     "L.DTLEXP,T.DESCTIPOEXP,L.DTRETLEXP FROM "+
			     "VDCLIENTE C, VDVENDEDOR V, EQLANCTOEXP L, EQTIPOEXP T "+
			     "WHERE C.CODCLI = L.CODCLI AND V.CODVEND = "+
			     "C.CODVEND AND T.CODTIPOEXP = L.CODTIPOEXP AND "+
			     "L.DTLEXP BETWEEN ? AND ?"+sWhere;
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setDate(1,Funcoes.strDateToSqlDate(sRet[0]));
			ps.setDate(2,Funcoes.strDateToSqlDate(sRet[1]));
		        ResultSet rs = ps.executeQuery();
		        imp.limpaPags();
	        	while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(136, false);
					String sTitulo = "RELATORIO LANCAMENTOS DE EXPOSITORES   -   PERIODO DE :"+sRet[0]+" ATE: "+sRet[1];
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"+"+StringFunctions.replicate("-",134)+"+");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|   Emitido em :"+Funcoes.dateToStrDate(new Date()));
					imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|");
					imp.say(imp.pRow()+0,(136-sTitulo.length())/2,sTitulo);
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+StringFunctions.replicate("-",134)+"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|Vend");
					imp.say(imp.pRow()+0,7,"|Nome Comissionado");
					imp.say(imp.pRow()+0,38,"|Cod.Cli ");
					imp.say(imp.pRow()+0,49,"|Raz�o Cliente");
					imp.say(imp.pRow()+0,81,"|Data Saida");
					imp.say(imp.pRow()+0,93,"|Tipo de Expositor");
					imp.say(imp.pRow()+0,125,"|Data Ret. ");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+StringFunctions.replicate("-",134)+"|");
				}	
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,0,"|"+rs.getString("CodVend"));
				imp.say(imp.pRow()+0,7,"|"+Funcoes.copy(rs.getString("NomeVend"),0,30));
				imp.say(imp.pRow()+0,38,"|"+rs.getString("CodCli"));
				imp.say(imp.pRow()+0,49,"|"+Funcoes.copy(rs.getString("RazCli"),0,30));
				imp.say(imp.pRow()+0,81,"|"+Funcoes.sqlDateToStrDate(rs.getDate("DtLExp")));
				imp.say(imp.pRow()+0,93,"|"+Funcoes.copy(rs.getString("DescTipoExp"),0,30));
				imp.say(imp.pRow()+0,125,"|"+(rs.getDate("DtRetLExp") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtRetLExp")) : ""));
				imp.say(imp.pRow()+0,136,"|");
				if (imp.pRow()>=linPag) {
					imp.incPags();
					imp.eject();
				}
	        	}
	        	imp.say(imp.pRow()+1,0,""+imp.comprimido());
        		imp.say(imp.pRow()+0,0,"+"+StringFunctions.replicate("-",134)+"+");
		        imp.eject();
		
        		imp.fechaGravacao();

//		        rs.close();
//        		ps.close();
       			con.commit();
		}
		catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de setores!\n"+err.getMessage(),true,con,err);
		}
		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp) 
			imprimir(true);
		else if (evt.getSource() == btImp) 
			imprimir(false);
		super.actionPerformed(evt);
	}
	public void setConexao(DbConnection cn) {
		super.setConexao(cn);
		lcCampos.setConexao(cn);
		lcTipoExp.setConexao(cn);
		lcCli.setConexao(cn);
	}
}
