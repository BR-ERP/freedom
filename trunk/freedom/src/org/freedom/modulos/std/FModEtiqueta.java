/**
 * @version 04/02/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FModEtiqueta.java <BR>
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
 * Monta o layout para etiquetas.
 * 
 */


package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.acao.JComboBoxEvent;               
import org.freedom.acao.JComboBoxListener;           
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FDados;
//import org.freedom.funcoes.Funcoes; // TESTE
import org.freedom.bmps.Icone;


public class FModEtiqueta extends FDados implements ActionListener, JComboBoxListener {       
	
    private Painel pinCab = new Painel(0,150);
	private JTextFieldPad txtCodModEtiq = new JTextFieldPad();
	private JTextFieldPad txtDescModEtiq = new JTextFieldPad();
	private JTextFieldPad txtNColModEtiq = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtCodpapel = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0); 
	private JTextFieldFK txtDescpapel = new JTextFieldFK();
	private JTextAreaPad txaEtiqueta = new JTextAreaPad(500); 
	private JScrollPane spnCli = new JScrollPane(txaEtiqueta); 
	private JButton btAdic = new JButton(Icone.novo("btOk.gif"));
    private JComboBoxPad cbCampos = null;
    private ListaCampos lcPapel = new ListaCampos(this,"PL");
    
	public FModEtiqueta() {
    	setTitulo("Modelo de etiqueta");
    	setAtribos(20,100,750,400);
    	
    	pnCliente.add(pinCab,BorderLayout.NORTH);
    	pnCliente.add(spnCli);
    	
    	setPainel(pinCab);

    	lcPapel.add(new GuardaCampo( txtCodpapel, 7, 60, 80, 20,  "Codpapel", "Cod.Papel", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodpapel");
    	lcPapel.add(new GuardaCampo( txtDescpapel, 80, 60, 297, 20, "Descpapel", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescpapel"); 
    	lcPapel.montaSql(false, "PAPEL", "SG");
     	lcPapel.setQueryCommit(false);
     	lcPapel.setReadOnly(true);
     	txtCodpapel.setTabelaExterna(lcPapel);

    	txaEtiqueta.setFont(new Font("Courier",Font.PLAIN,11));

    	adicCampo(txtCodModEtiq, 7, 20, 50, 20,"CodModEtiq","C�digo",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
    	adicCampo(txtDescModEtiq, 60, 20, 197, 20,"DescModEtiq","Descri��o",JTextFieldPad.TP_STRING,30,0,false,false,null,true);
    	adicCampo(txtNColModEtiq, 260, 20, 60, 20,"NColModEtiq","Colunas",JTextFieldPad.TP_INTEGER,5,0,false,false,null,true);
    	
    	adicDBLiv(txaEtiqueta,"TxaModEtiq", "Corpo",JTextFieldPad.TP_STRING, true);
    	
     	adicCampo(txtCodpapel, 7, 60, 70, 20,"Codpapel","C�d.Papel",JTextFieldPad.TP_STRING,10,0,false,true,txtDescpapel,true); 
   	    adicDescFK(txtDescpapel, 80, 60, 297, 20,"Descpapel","Descri��o",JTextFieldPad.TP_STRING,40,0); 

    	setListaCampos( false, "MODETIQUETA", "SG");
   	    
    	Vector vLabs = new Vector();
    	vLabs.addElement("C�digo do cliente");
    	vLabs.addElement("Raz�o do cliente");
    	vLabs.addElement("Nome do cliente");
    	vLabs.addElement("CPF ou CNPJ do cliente");
    	vLabs.addElement("Endere�o do cliente");
    	vLabs.addElement("N�mero");
    	vLabs.addElement("Complemento");
    	vLabs.addElement("CEP");
    	vLabs.addElement("Bairro do cliente");
    	vLabs.addElement("Cidade do cliente");
    	vLabs.addElement("UF do cliente");
    	
    	Vector vVals = new Vector();
    	vVals.addElement("[CODCLI]"); //larg: 8
    	vVals.addElement("[_____________RAZAO____DO____CLIENTE_____________]"); //larg: 50
    	vVals.addElement("[_____________NOME_____DO____CLIENTE_____________]"); //larg: 50
    	vVals.addElement("[CPF/CNPJ_ CLIENT]"); //larg: 18
    	vVals.addElement("[____________ENDERECO____DO____CLIENTE___________]"); //larg: 50
    	vVals.addElement("[NUMERO]"); //larg: 8
    	vVals.addElement("[____COMPLEMENTO___]"); //larg: 20
    	vVals.addElement("[__CEP__]"); //larg: 9
    	vVals.addElement("[___________BAIRRO___________]"); //larg: 30
    	vVals.addElement("[___________CIDADE___________]"); //larg: 30
    	vVals.addElement("[UF]"); //larg: 2
    	
    	cbCampos = new JComboBoxPad(vLabs,vVals);
  
    	adic(new JLabel("Campos din�micos"), 7, 80, 220, 20); 
       	adic(cbCampos, 7, 100, 220, 20); 
       	adic(btAdic, 230, 100, 30, 30); 
        
    	txaEtiqueta.setTabSize(0);
 
    	btImp.addActionListener(this);
    	btPrevimp.addActionListener(this);
    	
    	cbCampos.addComboBoxListener(this); 
     	btAdic.addActionListener(this);
    	    	
    }
	
    public void execShow(Connection cn) {
	  	super.execShow(cn);
	  	lcPapel.setConexao(cn);
    }
	
	public void valorAlterado(JComboBoxEvent evt) { 
		if (evt.getComboBoxPad() == cbCampos) { 
			txaEtiqueta.insert(cbCampos.getVlrString(),txaEtiqueta.getCaretPosition()); 
		} 
	} 
	
	public void actionPerformed(ActionEvent evt) {
		 String Aux, Aux1;// teste
		 int Tam = 0;
		 if (evt.getSource() == btAdic) {
		 	 // Aux = String.valueOf(cbCampos.getVlrString());// TESTE
		 	 // if (Aux.length()> 33){// TESTE
		 	 //	Tam = (Aux.length()- 33); // TESTE
		 	 //	Tam =(Aux.length()- Tam);
		 	 //	 Tiracp(Aux,Tam);// TESTE
		 	 //cbCampos.addItem(Aux);// TESTE
		 	 // }
	       txaEtiqueta.insert(cbCampos.getVlrString(),txaEtiqueta.getCaretPosition());
	    }
		 else if (evt.getSource() == btImp)
		 	 imprimir(false);
		 else if (evt.getSource() == btPrevimp)
		 	 imprimir(true);
		 super.actionPerformed(evt);
	}
	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		imp.verifLinPag();
		imp.setTitulo("Teste de etiqueta");
	    imp.limpaPags();
	    String[] sLinhas = txaEtiqueta.getText().split("\n");
	    for(int i=0;i<sLinhas.length;i++) {
			imp.say(imp.pRow()+1,0,sLinhas[i]);
	    }
	    imp.eject();
	    imp.fechaGravacao();
		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
	}

}
