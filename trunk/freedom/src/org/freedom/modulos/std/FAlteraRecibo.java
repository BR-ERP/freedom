/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCancVenda.java <BR>
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
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FAlteraRecibo extends FFilho implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JPanelPad pinCli = new JPanelPad(350,100);
	
	private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	
	private JTextFieldPad txtCodVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	
	private JTextFieldFK txtSerie = new JTextFieldFK(JTextFieldPad.TP_STRING,4,0);
	
	private JTextFieldFK txtVlrLiqVenda = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,15,2);
	
	private JTextFieldFK txtStatusVenda = new JTextFieldFK(JTextFieldPad.TP_STRING,2,0);
	
	private JTextFieldPad txtCodRec = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
	
	private JTextFieldFK txtNovo = new JTextFieldFK(JTextFieldPad.TP_INTEGER,8,0);
	
	private JButton btTrocaDoc = new JButton(Icone.novo("btTrocaNumero.gif"));
	
	private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
	
	private ListaCampos lcVenda = new ListaCampos(this);
	
	public FAlteraRecibo() {
		super(false);
		setTitulo("Troca de documento");
		setAtribos(50,50,350,170);

		txtCodVenda.setRequerido(true);

		lcVenda.add(new GuardaCampo( txtCodVenda, "CodVenda", "C�d.venda", ListaCampos.DB_PK, false));
		lcVenda.add(new GuardaCampo( txtNovo, "DocVenda", "Documento", ListaCampos.DB_SI, false));
		lcVenda.add(new GuardaCampo( txtCodRec, "DocVenda", "Documento", ListaCampos.DB_SI, false));
		lcVenda.add(new GuardaCampo( txtSerie, "Serie", "S�rie", ListaCampos.DB_SI, false));
		lcVenda.add(new GuardaCampo( txtVlrLiqVenda, "VlrLiqVenda", "V. liq.", ListaCampos.DB_SI, false));
		lcVenda.add(new GuardaCampo( txtStatusVenda, "StatusVenda", "Status", ListaCampos.DB_SI, false));
		lcVenda.add( new GuardaCampo( txtStatusVenda, "StatusVenda", "Status", ListaCampos.DB_SI, false));
		lcVenda.montaSql(false, "VENDA", "VD");
		lcVenda.setReadOnly(true);
		txtCodVenda.setTabelaExterna(lcVenda);
		txtCodVenda.setFK(true);
		txtCodVenda.setNomeCampo("CodVenda");

		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		btSair.setPreferredSize(new Dimension(100,30));

		pnRod.setPreferredSize(new Dimension(350,30));
		pnRod.add(btSair,BorderLayout.EAST);

		c.add(pnRod,BorderLayout.SOUTH);
		c.add(pinCli,BorderLayout.CENTER);

		btTrocaDoc.setToolTipText("Alterar");

		pinCli.adic( new JLabelPad( "Venda" ), 7, 0, 80, 20 );
		pinCli.adic( txtCodVenda, 7, 20, 100, 20 );
		pinCli.adic( new JLabelPad( "Valor" ), 120, 0, 100, 20 );
		pinCli.adic( txtVlrLiqVenda, 120, 20, 100, 20 );
		pinCli.adic( btTrocaDoc, 187, 50, 30, 30 );
		pinCli.adic( new JLabelPad( "Novo Recibo" ), 7, 40, 73, 20);
		pinCli.adic( txtCodRec , 7, 60, 100, 20 );
		
		btSair.addActionListener(this);
		btTrocaDoc.addActionListener(this);
	}
	private void trocar() {
		
		if (txtStatusVenda.getVlrString().equals("")) {
			Funcoes.mensagemInforma(this,"Nenhuma venda foi selecionada!");
			txtCodVenda.requestFocus();
			return;
		}
		String sImpNota = "";
	    String sSQL1 = "SELECT DOCVENDA FROM VDVENDA WHERE CODVENDA=? AND CODEMP=? AND CODFILIAL=?";
	    String sSQL2 = "UPDATE VDVENDA SET DOCVENDA=? WHERE CODVENDA=? AND CODEMP=? AND CODFILIAL=? ";
	    try {
//	1a. query:
	   	  PreparedStatement ps = con.prepareStatement(sSQL1);
	      ps.setInt(1,txtCodRec.getVlrInteger().intValue());
	      ps.setInt(2,Aplicativo.iCodEmp);
	      ps.setInt(3,ListaCampos.getMasterFilial("VDVENDA"));
	      ResultSet rs = ps.executeQuery();
	      if (rs.next()) {
	        sImpNota = rs.getString("DocVenda");
	      }
	      rs.close();
	      ps.close();
//	2a. query:
	      ps = con.prepareStatement(sSQL2);
	      ps.setInt(1,txtCodRec.getVlrInteger().intValue());
	      ps.setInt(2,txtCodVenda.getVlrInteger().intValue());
	      ps.setInt(3,Aplicativo.iCodEmp);
	      ps.setInt(4,ListaCampos.getMasterFilial("VDVENDA"));
	      ps.executeUpdate();
	      ps.close();
				
	      con.commit();
	      Funcoes.mensagemInforma( this, "Numero do recibo alterado com Sucesso!" );
	      txtCodRec.setVlrString( "" );
			
		}
		catch(SQLException err) {
			
			Funcoes.mensagemErro(this,"Erro ao alterar a venda!\n"+err.getMessage(),true,con,err);
			err.printStackTrace();

		}

	}
	public void actionPerformed(ActionEvent evt) { 
		if (evt.getSource() == btSair)
			dispose();

		else if (evt.getSource() == btTrocaDoc)
			trocar();


	}
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcVenda.setConexao(cn);
	}
}

