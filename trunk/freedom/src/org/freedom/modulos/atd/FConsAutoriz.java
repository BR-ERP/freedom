/**
 * @version 30/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>cbConveniado
 * Classe: @(#)FConsAutoriz.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Formul�rio de consulta de or�amento.
 * 
 */

package org.freedom.modulos.atd;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;
import org.freedom.telas.FPrincipal;

public class FConsAutoriz extends FFilho implements ActionListener {
	private JPanelPad pinCab = new JPanelPad(0,210);
	private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtNomeCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);	
	private JTextFieldPad txtCodConv = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtNomeConv = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtCodEnc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNomeEnc = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtDtIni = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtDtFim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtCid = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
	private JLabelPad lbCid = new JLabelPad("Cidade");
	private JCheckBoxPad cbVencidas = new JCheckBoxPad("Vencidas","S","N");
	private JCheckBoxPad cbCompleto = new JCheckBoxPad("Completo","S","N");
	private JCheckBoxPad cbLiberado = new JCheckBoxPad("Liberado","S","N");
	private JCheckBoxPad cbFaturado = new JCheckBoxPad("Faturado","S","N");
//	private JCheckBoxPad cbConveniado = new JCheckBoxPad("Listar conveniados","S","N");		
	
	
	private JRadioGroup gbVenc;
	
	private Tabela tab = new Tabela();
	private JButton btBusca = new JButton("Buscar",Icone.novo("btPesquisa.gif"));
	private JButton btPrevimp = new JButton ("Imprimir",Icone.novo("btPrevimp.gif"));
	private JScrollPane spnTab = new JScrollPane(tab);
	private ListaCampos lcConv = new ListaCampos(this,"PR");
	private ListaCampos lcCli = new ListaCampos(this,"CL");
	private ListaCampos lcEnc = new ListaCampos(this,"EC");
	private FPrincipal fPrim;
	public FConsAutoriz() {
		setTitulo("Pesquisa Autoriza��o");
		setAtribos(10,10,605,430);
		
		txtDtIni.setRequerido(true);
		txtDtFim.setRequerido(true);

		lcConv.add(new GuardaCampo( txtCodConv, "CodConv", "C�d.conv", ListaCampos.DB_PK ,false));
		lcConv.add(new GuardaCampo( txtNomeConv, "NomeConv", "Nome do conveniado", ListaCampos.DB_SI, false));
		txtCodConv.setTabelaExterna(lcConv);
		txtCodConv.setNomeCampo("CodConv");
		txtCodConv.setFK(true);
		lcConv.setReadOnly(true);
		lcConv.montaSql(false, "CONVENIADO", "AT");

		lcCli.add(new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK ,false));
		lcCli.add(new GuardaCampo( txtNomeCli, "NomeCli", "Raz�o social do cliente", ListaCampos.DB_SI, false));
		txtCodCli.setTabelaExterna(lcCli);
		txtCodCli.setNomeCampo("CodCli");
		txtCodCli.setFK(true);
		lcCli.setReadOnly(true);
		lcCli.montaSql(false, "CLIENTE", "VD");
		
		
		lcEnc.add(new GuardaCampo( txtCodEnc, "CodEnc", "C�d.enc.", ListaCampos.DB_PK, false));
		lcEnc.add(new GuardaCampo( txtNomeEnc, "NomeEnc", "Nome do encaminhador", ListaCampos.DB_SI, false));
		txtCodEnc.setTabelaExterna(lcEnc);
		txtCodEnc.setNomeCampo("CodEnc");
		txtCodEnc.setFK(true);
		lcEnc.setReadOnly(true);
		lcEnc.montaSql(false, "ENCAMINHADOR", "AT");

		getTela().add(pnCli,BorderLayout.CENTER);
		pnCli.add(pinCab,BorderLayout.NORTH);
		pnCli.add(spnTab,BorderLayout.CENTER);
		adicBotaoSair();
		
		Vector vVals = new Vector();
		vVals.addElement("D");
		vVals.addElement("V");
		Vector vLabs = new Vector();
		vLabs.addElement("Data de emiss�o");
		vLabs.addElement("Data de venc. da autoriza��o");
		gbVenc = new JRadioGroup(2,1,vLabs,vVals);
		
		pinCab.adic(new JLabelPad("C�d.cli."),7,0,280,20);
		pinCab.adic(txtCodCli,7,20,70,20);
		pinCab.adic(new JLabelPad("Raz�o social do cliente"),80,0,280,20);
		pinCab.adic(txtNomeCli,80,20,294,20);

		pinCab.adic(new JLabelPad("C�d.conv."),7,40,280,20);
		pinCab.adic(txtCodConv,7,60,70,20);
		pinCab.adic(new JLabelPad("Nome do conveniado"),80,40,280,20);
		pinCab.adic(txtNomeConv,80,60,294,20);
		
		pinCab.adic(new JLabelPad("C�d.enc."),7,80,250,20);
		pinCab.adic(txtCodEnc,7,100,70,20);
		pinCab.adic(new JLabelPad("Descri��o do Encaminhador"),80,80,250,20);
		pinCab.adic(txtNomeEnc,80,100,294,20);
						
//		pinCab.adic(cbConveniado,300,60,150,20);
		
		pinCab.adic(new JLabelPad("Per�odo:"),380,0,90,20);
		pinCab.adic(txtDtIni,380,20,87,20);
		pinCab.adic(new JLabelPad("At�"),470,20,27,20);
		pinCab.adic(txtDtFim,494,20,87,20);
		
		
		pinCab.adic(lbCid,380,40,50,20);
		pinCab.adic(txtCid,380,60,100,20);
		
		cbVencidas.setVlrString("S");
		
			
						
		pinCab.adic(new JLabelPad("Status:"),7,125,90,20);
		pinCab.adic(cbVencidas,7,145,80,20);
		pinCab.adic(cbCompleto,7,170,80,20);
		pinCab.adic(cbLiberado,120,145,80,20);
		pinCab.adic(cbFaturado,120,170,80,20);		
		
		pinCab.adic(new JLabelPad("Filtrar por:"),230,125,110,20);
		pinCab.adic(gbVenc,230,143,240,55);
			
		pinCab.adic(btBusca,484,105,100,30);

		pinCab.adic(btPrevimp,484,162,100,30);
		
		txtDtIni.setVlrDate(new Date());
		txtDtFim.setVlrDate(new Date());

		tab.adicColuna("C�d.orc");
		tab.adicColuna("Emiss�o.");
		tab.adicColuna("Vcto.orc.");
		tab.adicColuna("C�d.conv.");
		tab.adicColuna("Nome do conveniado");
		tab.adicColuna("Fone do conveniado");
		tab.adicColuna("Valid.autoriz.");
		tab.adicColuna("Num.autoriz");
		tab.adicColuna("C�d.prod.");
		tab.adicColuna("C�d.barra");
		tab.adicColuna("Desc.prod.");
		tab.adicColuna("Cidade");
        tab.adicColuna("Encaminhador");
        
		tab.setTamColuna(70,0);
		tab.setTamColuna(70,1);
		tab.setTamColuna(70,2);
		tab.setTamColuna(70,3);
		tab.setTamColuna(150,4);
		tab.setTamColuna(120,5);
		tab.setTamColuna(110,6);
		tab.setTamColuna(90,7);
		tab.setTamColuna(90,8);
		tab.setTamColuna(90,9);
		tab.setTamColuna(200,10);
		tab.setTamColuna(80,11);
        tab.setTamColuna(100,12);
		
		
		btBusca.addActionListener(this);
		
		btPrevimp.addActionListener(this);
		
		
		tab.addMouseListener(
		  new MouseAdapter() {
		  	public void mouseClicked(MouseEvent mevt) {
				if (mevt.getSource()==tab && mevt.getClickCount()==2)
				  abreOrc();
		  	}
		  }
		);
	}
	/**
	 * 
	 * Carrega os valores para a tabela de consulta.
	 * Este m�todo � executado ap�s carregar o ListaCampos da tabela.
	 *
	 */ 
	private void carregaTabela() {
		String sWhere = "";
		String sStatusOrc = "";
		boolean bConv = (txtCodConv.getVlrInteger().intValue() > 0);
		boolean bCli = (txtCodCli.getVlrInteger().intValue() > 0);
		boolean bEnc = (txtCodEnc.getVlrInteger().intValue()> 0); 
		
        if (cbVencidas.getVlrString().equals("S")){	
        	sWhere = " AND IT.VENCAUTORIZORC < CAST ('today' AS DATE) "; 
        }
        
        if (cbCompleto.getVlrString().equals("S")) 
        	sStatusOrc = "'OC'";
		if (cbLiberado.getVlrString().equals("S"))
			sStatusOrc += (!sStatusOrc.equals("")?",":"")+"'OL'";
		if (cbFaturado.getVlrString().equals("S"))
			sStatusOrc += (!sStatusOrc.equals("")?",":"")+"'OV'";
		if (!sStatusOrc.equals(""))
		    sWhere += " AND O.STATUSORC IN ("+sStatusOrc+") ";                  
		if (gbVenc.getVlrString().equals("V"))
		  sWhere += " AND IT.VENCAUTORIZORC BETWEEN ? AND ?";
		else
		  sWhere += " AND O.DTORC BETWEEN ? AND ?";
		  
		if (bConv)
		  sWhere += " AND O.CODCONV=? AND O.CODEMPCV=O.CODEMP AND O.CODFILIALCV=O.CODFILIAL ";
		  
		if (bCli)  
		  sWhere += " AND O.CODCLI=? AND O.CODEMPCV=O.CODEMP AND CODFILIALCV=O.CODFILIAL ";	
		
		if (bEnc)
			  sWhere += " AND O.CODCONV=C.CODCONV AND O.CODEMPCV=C.CODEMP AND O.CODFILIALCV=C.CODFILIAL " +
				  		"AND C.CODENC="+txtCodEnc.getVlrString()+" AND C.CODEMPEC=O.CODEMP AND C.CODFILIALEC="+lcEnc.getCodFilial()+"";
	    
		
		if (!txtCid.getVlrString().equals("")) {
			sWhere += " AND C.CIDCONV  = '"+txtCid.getVlrString()+"'";
			
		}
		
		
		String sSQL="SELECT  O.CODORC,O.DTORC,O.DTVENCORC,"+
       				"O.CODCONV,C.NOMECONV,C.FONECONV , IT.VENCAUTORIZORC,IT.NUMAUTORIZORC,"+
					"IT.CODPROD, P.CODBARPROD,P.DESCPROD,C.CIDCONV, "+
				    "(SELECT EC.NOMEENC FROM ATENCAMINHADOR EC WHERE EC.CODENC=C.CODENC AND "+
                    "EC.CODEMP=C.CODEMPEC AND EC.CODFILIAL=C.CODFILIALEC) "+
					"FROM VDORCAMENTO O,VDCLIENTE CL,"+
					"ATCONVENIADO C, EQPRODUTO P, VDITORCAMENTO IT WHERE O.CODEMP=? "+
					"AND O.CODFILIAL=? AND O.TIPOORC='O' "+
					"AND IT.CODORC=O.CODORC AND IT.CODEMP=O.CODEMP AND IT.CODFILIAL=O.CODFILIAL " +
					"AND IT.TIPOORC=O.TIPOORC "+ 
					"AND CL.CODEMP=O.CODEMPCL AND CL.CODFILIAL=O.CODFILIALCL "+
					"AND CL.CODCLI=O.CODCLI  AND C.CODEMP=O.CODEMPCV AND C.CODFILIAL=O.CODFILIALCV "+
		            "AND O.CODCONV=C.CODCONV "+					
					"AND P.CODPROD=IT.CODPROD AND P.CODEMP=IT.CODEMPPD AND " +
 					"P.CODFILIAL=IT.CODFILIALPD "+sWhere;

		try {
			//System.out.println(sSQL);
            //System.exit(0);
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("VDORCAMENTO"));
		    ps.setDate(3,Funcoes.dateToSQLDate(txtDtIni.getVlrDate()));
			ps.setDate(4,Funcoes.dateToSQLDate(txtDtFim.getVlrDate()));
			
            int iParam = 5;
			if (bConv) {
		        ps.setInt(iParam,txtCodConv.getVlrInteger().intValue());
		        iParam ++;
			}
			if (bCli) {
			    ps.setInt(iParam,txtCodCli.getVlrInteger().intValue());
			}
			
			ResultSet rs = ps.executeQuery();
			int iLin = 0;
            tab.limpa();
			while (rs.next()) {
				tab.adicLinha();
				tab.setValor(rs.getInt(1)+"",iLin,0);
				tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate(2)),iLin,1);
				tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate(3)),iLin,2);
				tab.setValor(rs.getInt(4)+"",iLin,3);
				tab.setValor(rs.getString(5)!= null ? rs.getString(5) : "",iLin,4);
				tab.setValor(rs.getString(6)!= null ? rs.getString(6) : "",iLin,5);
				tab.setValor(Funcoes.sqlDateToStrDate(rs.getDate(7)),iLin,6);
				tab.setValor(rs.getString(8)!= null ? rs.getString(8) : "",iLin,7);
				tab.setValor(rs.getInt(9)+"",iLin,8);
				tab.setValor(rs.getString(10) != null ? rs.getString(10) : "",iLin,9);
				tab.setValor(rs.getString(11) != null ? rs.getString(11) : "",iLin,10);
				tab.setValor(rs.getString(12)+"",iLin,11);
				tab.setValor(rs.getString(13) != null ? rs.getString(13) : "",iLin,12);
				
				
		
	         	iLin++;
			}
//			rs.close();
//			ps.close();
			if (!con.getAutoCommit())
                con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar a tabela VDOR�AMENTO!\n"+err.getMessage());
			err.printStackTrace();
		}
	}
	
	
	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		imp.montaCab();
		imp.setTitulo("Relat�rio de Or�amentos");
						
		try {
			imp.limpaPags();
			for (int iLin=0;iLin<tab.getNumLinhas();iLin++) {
				if (imp.pRow()==0) {
					imp.impCab(136);
					imp.say(imp.pRow()+0,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,1,"| N.ORC.");
					imp.say(imp.pRow()+0,14," | Emiss�o");
					imp.say(imp.pRow()+0,27,"  | Validade.");
					imp.say(imp.pRow()+0,40," | Autoriz.");
				    imp.say(imp.pRow()+0,54,"  | Nome");
				    imp.say(imp.pRow()+0,86," |Encaminhador");
					imp.say(imp.pRow()+0,101," | Cidade");
					imp.say(imp.pRow()+0,120,"  | Telefone     |");
					
					
					
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
				}
				imp.say(imp.pRow()+1,0,"|"+imp.comprimido());
				imp.say(imp.pRow()+0,2,""+tab.getValor(iLin,0));
				imp.say(imp.pRow()+0,15,"|"+tab.getValor(iLin,1));
				imp.say(imp.pRow()+0,29,"|"+tab.getValor(iLin,6));
				imp.say(imp.pRow()+0,41,"|"+Funcoes.copy(tab.getValor(iLin,7)+"",13));
				
				imp.say(imp.pRow()+0,56,"|"+Funcoes.copy(tab.getValor(iLin,4)+"",25));
				imp.say(imp.pRow()+0,87,"|"+Funcoes.copy(tab.getValor(iLin,12)+"",15));
				
				imp.say(imp.pRow()+0,103,"|"+Funcoes.copy(tab.getValor(iLin,11)+"",17));
				imp.say(imp.pRow()+0,122,"|"+Funcoes.copy(tab.getValor(iLin,5)+"",12)+"  |");
				
				
				if (imp.pRow()>=linPag) {
					imp.incPags();
					imp.eject();
				}
			}
			
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,Funcoes.replicate("=",136));
			imp.eject();
			
			imp.fechaGravacao();
			
//      rs.close();
//      ps.close();
			con.commit();
			
		}  
		catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de or�amentos!"+err.getMessage());      
		}
		
		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
	}
	
	
	
	private void abreOrc() {
		int iCodOrc = ((Integer)tab.getValor(tab.getLinhaSel(),1)).intValue();
		if (fPrim.temTela("Orcamento")==false) {
		  FOrcamento tela = new FOrcamento();
		  fPrim.criatela("Orcamento",tela,con);
		  tela.exec(iCodOrc);
	    } 
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btBusca) {
			if (txtDtIni.getVlrString().length() < 10)
			  Funcoes.mensagemInforma(this,"Digite a data inicial!");
			else if (txtDtFim.getVlrString().length() < 10)
			  Funcoes.mensagemInforma(this,"Digite a data final!");
			else
			  carregaTabela();
		}
		if (evt.getSource()==btPrevimp){
			imprimir(true);
		}
	}
	public void setTelaPrim(FPrincipal fP) {
		fPrim = fP;
	}
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcConv.setConexao(con);
		lcCli.setConexao(con);
		lcEnc.setConexao(con);
	}
}
