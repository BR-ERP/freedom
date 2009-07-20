/**
 * @version 21/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTipoCred.java <BR>
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
 * Tipos para libera��o de cr�dito...
 * 
 */

package org.freedom.modulos.std;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
public class FTipoCred extends FDados implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCodTipoCred = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDescTipoCred = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtVlrTipoCred = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
	
	public FTipoCred() {
		super();
		setTitulo("Cadastro de tipos de credito");
		setAtribos(50, 50, 350, 165);
		adicCampo(txtCodTipoCred, 7, 20, 80, 20,"CodTpCred","C�d.tp.cred.", ListaCampos.DB_PK, true);
		adicCampo(txtDescTipoCred, 90, 20, 240, 20,"DescTpCred","Descri��o do tipo de credito", ListaCampos.DB_SI, true);
		adicCampo(txtVlrTipoCred, 7, 60, 120, 20,"VlrTpCred","Valor", ListaCampos.DB_SI, true);
		setListaCampos( true, "TIPOCRED", "FN");
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
		DLRTipoCred dl = null;
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = 0;
		int iTot = 0;
		
		dl = new DLRTipoCred();
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}

		try {
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relat�rio de Tipos de Cr�dito");
			imp.limpaPags();
			
			sSQL = "SELECT TP.CODTPCRED,TP.DESCTPCRED," +
				   "(SELECT COUNT(CLI.CODCLI) FROM VDCLIENTE CLI " +
				   							 "WHERE CLI.CODEMP=TP.CODEMP AND CLI.CODFILAL=TP.CODFILIAL " +
				   							 "AND CLI.CODTPCRED = TP.CODTPCRED) "+
				   "FROM FNTIPOCRED TP " +
				   "WHERE TP.CODEMP=? AND TP.CODFILIAL=? " +
				   "ORDER BY TP." + dl.getValor();
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("FNTIPOCRED"));
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow(),0, imp.normal());
					imp.say(imp.pRow(), 2, "C�d.tp.cred.");
					imp.say(imp.pRow(), 20, "Descri��o");
					imp.say(imp.pRow(), 70, "Qtd.cli.");
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(), 0, Funcoes.replicate("-",79));
				}
				imp.say(imp.pRow() + 1,0, imp.normal());
				imp.say(imp.pRow(), 2, rs.getString("CodTpCred"));
				imp.say(imp.pRow(), 20, rs.getString("DescTpCred"));
				imp.say(imp.pRow(), 70, Funcoes.alinhaDir(rs.getInt(3),8));
				iTot += rs.getInt(3);
				if (imp.pRow()>=linPag) {
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(), 0, Funcoes.replicate("-",79));
					imp.incPags();
					imp.eject();
				}
			}
			  
			imp.say(imp.pRow() + 1, 0, imp.normal());
			imp.say(imp.pRow(), 0, Funcoes.replicate("=",79));
			imp.say(imp.pRow() + 1, 0, imp.normal());
			imp.say(imp.pRow(), 0, "|");
			imp.say(imp.pRow(), 50, "Total de clientes:");
			imp.say(imp.pRow(), 71, Funcoes.alinhaDir(iTot,8));
			imp.say(imp.pRow(), 80, "|");
			imp.say(imp.pRow() + 1, 0, imp.normal());
			imp.say(imp.pRow(), 0, Funcoes.replicate("=",79));			  
			imp.eject();      
			imp.fechaGravacao();
			
			con.commit();
			dl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro na consulta tabela de tipos de tipos de cr�ditos!\n"+err.getMessage(),true,con,err);
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
