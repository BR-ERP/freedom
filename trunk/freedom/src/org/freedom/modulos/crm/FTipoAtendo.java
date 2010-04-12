/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FTipoAtendo.java <BR>
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.ImprimeOS;
import org.freedom.library.ListaCampos;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.telas.FDetalhe;

public class FTipoAtendo extends FDetalhe implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JPanelPad pinCab = new JPanelPad();
	
	private JPanelPad pinDet = new JPanelPad();
	
	private JTextFieldPad txtCodTipoAtendo = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	
	private JTextFieldPad txtDescTipoAtendo = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	
	private JTextFieldPad txtCodSetAt = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	
	private JTextFieldFK txtDescSetAt = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	
//	private JTextFieldPad txtCodFluxo = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	
//	private JTextFieldFK txtDescFluxo = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	
	private ListaCampos lcSetAt = new ListaCampos(this,"ST");
	
//	private ListaCampos lcFluxo = new ListaCampos(this,"FX");
	
	private JRadioGroup<?, ?> rgTipoAtend = null;
	
	private Vector<String> vValsTipo = new Vector<String>();
	
	private Vector<String> vLabsTipo = new Vector<String>();

	public FTipoAtendo () {
	
		setTitulo("Tipo de Atendimento");
		setAtribos( 50, 50, 370, 340);

		vValsTipo.addElement( "C" );
		vValsTipo.addElement( "A" );

		vLabsTipo.addElement( "Contato" );
		vLabsTipo.addElement( "Atendimento" );

		rgTipoAtend = new JRadioGroup<String, String>( 1, 2, vLabsTipo, vValsTipo );
		rgTipoAtend.setVlrString( "TT" );
		
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);

		lcSetAt.add(new GuardaCampo( txtCodSetAt, "CodSetAt", "C�d.setor", ListaCampos.DB_PK,true));
		lcSetAt.add(new GuardaCampo( txtDescSetAt, "DescSetAt", "Descri��o do setor", ListaCampos.DB_SI,false));
		lcSetAt.montaSql(false, "SETOR", "AT");
		lcSetAt.setQueryCommit(false);
		lcSetAt.setReadOnly(true);
		txtCodSetAt.setTabelaExterna(lcSetAt);

//		lcFluxo.add(new GuardaCampo( txtCodFluxo, "CodFluxo", "C�d.fluxo", ListaCampos.DB_PK, true));
//		lcFluxo.add(new GuardaCampo( txtDescFluxo, "DescFluxo", "Descri��o do fluxo", ListaCampos.DB_SI,false));
//		lcFluxo.montaSql(false, "FLUXO", "SG");
//		lcFluxo.setQueryCommit(false);
//		lcFluxo.setReadOnly(true);
//		txtCodFluxo.setTabelaExterna(lcFluxo);

		setAltCab(140);

		adicCampo(txtCodTipoAtendo, 7, 20, 80, 20,"CodTpAtendo","C�d.tp.atend.",ListaCampos.DB_PK,true);
		adicCampo(txtDescTipoAtendo, 90, 20, 250, 20,"DescTpAtendo","Descri��o do tipo de atendimento",ListaCampos.DB_SI,true);
//		adicCampo(txtCodFluxo, 7, 60, 80, 20,"CodFluxo","C�d.fluxo",ListaCampos.DB_FK,txtDescFluxo,true);
//		adicDescFK(txtDescFluxo, 90, 60, 250, 20,"DescFluxo","Descri��o do fluxo");
		adicDB( rgTipoAtend, 7, 60, 333, 30, "tipoatendo", "Tipo", true );
		
		setListaCampos( true, "TIPOATENDO", "AT");

		setAltDet(60);
		setPainel( pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);

		adicCampo(txtCodSetAt, 7, 20, 80, 20,"CodSetAt","C�d.setor",ListaCampos.DB_PF,txtDescSetAt,true);
		adicDescFK(txtDescSetAt, 90, 20, 250, 20,"DescSetAt","Descri��o do setor");
		setListaCampos( false, "TIPOATENDOSETOR", "AT");
		montaTab();

		tab.setTamColuna(200,1);

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
	public void setConexao(DbConnection cn) {
		super.setConexao(cn);
		lcSetAt.setConexao(cn);
//		lcFluxo.setConexao(cn);
	}
	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		imp.montaCab();
		imp.setTitulo("Relat�rio de Tipos de Atendimentos");
		DLRTipoAtendo dl = new DLRTipoAtendo();
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		String sSQL = "SELECT CODTPATENDO,DESCTPATENDO FROM ATTIPOATENDO ORDER BY "+dl.getValor();
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
					imp.say(imp.pRow()+0,2,"C�digo");
					imp.say(imp.pRow()+0,25,"Descri��o");
					imp.say(imp.pRow()+1,0,""+imp.normal());
					imp.say(imp.pRow()+0,0,StringFunctions.replicate("-",79));
				}
				imp.say(imp.pRow()+1,0,""+imp.normal());
				imp.say(imp.pRow()+0,2,rs.getString("CodTpAtendo"));
				imp.say(imp.pRow()+0,25,rs.getString("DescTpAtendo"));
				if (imp.pRow()>=linPag) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say(imp.pRow()+1,0,""+imp.normal());
			imp.say(imp.pRow()+0,0,StringFunctions.replicate("=",79));
			imp.eject();

			imp.fechaGravacao();

			//      rs.close();
			//      ps.close();
			con.commit();
			dl.dispose();
		}  
		catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de tipos de atendimentos!"+err.getMessage(),true,con,err);      
		}

		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
	}
}
