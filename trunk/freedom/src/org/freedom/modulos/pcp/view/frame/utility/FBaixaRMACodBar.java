/**
 * @version 20/06/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)FreedomPCP.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para
 * Programas de Computador), <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela de baixa de RMA por C�digo de barras.
 *  
 */
package org.freedom.modulos.pcp.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.gms.view.frame.crud.detail.FRma;

public class FBaixaRMACodBar extends FFilho implements ActionListener,CarregaListener,FocusListener{
	private static final long serialVersionUID = 1L;
	
  private JPanelPad pinCab = new JPanelPad(0, 190);
  private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JTablePad tab = new JTablePad();
  private JScrollPane spnTab = new JScrollPane(tab);	
  private JButtonPad btSair = new JButtonPad("Sair", Icone.novo("btSair.gif"));
  private JButtonPad btExpedir = new JButtonPad("Expedir", Icone.novo("btMedida.gif"));
  private JTextFieldPad txtEntrada = new JTextFieldPad(JTextFieldPad.TP_STRING,100,0);
  private JTextFieldPad txtSeqOf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtSeqOp = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodOp = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodLote = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldPad txtQtdEntrada = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,5);
  private ImageIcon imgAprovada = Icone.novo("clPagoParcial.gif");
  private JPanelPad pinEntrada = new JPanelPad(300,150);
  private JPanelPad pinLbEntrada = new JPanelPad(150,15);
  private JLabelPad lbEntrada = new JLabelPad(" Entrada via c�digo de barras");  
  private JPanelPad pinCampos = new JPanelPad(300,150);
  private JPanelPad pinLbCampos = new JPanelPad(150,15);
  private JLabelPad lbCampos = new JLabelPad(" Informa��es decodificadas");
    
  public FBaixaRMACodBar () {
  	super(false);
    setAtribos( 50, 50, 600, 450);
	Container c = getTela();
	c.add(pnRod, BorderLayout.SOUTH);
	c.add(pnCli, BorderLayout.CENTER);
	pnCli.add(pinCab, BorderLayout.NORTH);
	pnCli.add(spnTab, BorderLayout.CENTER);
	
	pinLbEntrada.adic(lbEntrada,0,0,180,15);
	pinLbEntrada.tiraBorda();
	    
	pinCab.adic(pinLbEntrada,10,7,180,15);
	pinCab.adic(pinEntrada,7,15,330,50);
 
    pinCab.adic(btExpedir,400,15,110,31);
    
	pinLbCampos.adic(lbCampos,0,0,180,15);
	pinLbCampos.tiraBorda();
	
	pinCab.adic(pinLbCampos,10,72,150,15);
	pinCab.adic(pinCampos,7,80,330,100);
	
	btSair.setPreferredSize(new Dimension(100, 30)); 

	pnRod.add(btSair, BorderLayout.EAST);
    
    pinEntrada.adic(txtEntrada,7,15,306,20);
    
    pinCampos.adic(new JLabelPad("C�d.OP"),7,5,100,20);
    pinCampos.adic(txtCodOp,7,25,100,20);
    
    pinCampos.adic(new JLabelPad("Seq.OP"),110,5,100,20);
    pinCampos.adic(txtSeqOf,110,25,100,20);
    
    pinCampos.adic(new JLabelPad("C�d.Prod."),213,5,100,20);
    pinCampos.adic(txtCodProd,213,25,100,20);
    
    pinCampos.adic(new JLabelPad("C�d.Lote"),7,45,203,20);
    pinCampos.adic(txtCodLote,7,65,203,20);
    
    pinCampos.adic(new JLabelPad("Qtd."),213,45,100,20);
    pinCampos.adic(txtQtdEntrada,213,65,100,20);
    

    
	tab.adicColuna("");//0
	tab.adicColuna("Rma.");//1
	tab.adicColuna("Item");//2
	tab.adicColuna("Prod.");//3
	tab.adicColuna("Descri��o do produto");//4
	tab.adicColuna("Lote");//5
	tab.adicColuna("OP");//6
	tab.adicColuna("Seq");//7	
	tab.adicColuna("Qtd.req.");//8
	tab.adicColuna("Qtd.aprov.");//9
	tab.adicColuna("Qtd. exp.");//10
	tab.adicColuna("Saldo");//11
	
	tab.setTamColuna(12, 0);
	tab.setTamColuna(40, 1);
	tab.setTamColuna(40, 2);
	tab.setTamColuna(50, 3);
	tab.setTamColuna(180,4);
	tab.setTamColuna(90, 5);
	tab.setTamColuna(40, 6);
	tab.setTamColuna(40, 7);
	tab.setTamColuna(70, 8);
	tab.setTamColuna(70, 9);
	tab.setTamColuna(90, 10);
	tab.setTamColuna(100,11);

    txtEntrada.addFocusListener(this);
    btSair.addActionListener(this);
    btExpedir.addActionListener(this);
	tab.addMouseListener(new MouseAdapter() {

		public void mouseClicked(MouseEvent mevt) {
			if (mevt.getSource() == tab && mevt.getClickCount() == 2)
				abreRma();
		}
	});
        
  }
  private void expedeItens(){
  	if(Funcoes.mensagemConfirma(this,"Confirma a expedi��o dos �tens selecionados?")==JOptionPane.YES_OPTION){
  		PreparedStatement ps = null;
  		String sSQL = "UPDATE EQITRMA SET QTDEXPITRMA=? WHERE CODEMP=? AND CODFILIAL=? AND CODRMA=? AND CODITRMA=?";
  		try{
	  		ps = con.prepareStatement(sSQL);
	  		try{	  			
	  		  	for(int iLin=0;tab.getNumLinhas()>iLin;iLin++){
	  		  		ps.setDouble(1,Funcoes.strCurrencyToDouble(tab.getValor(iLin,10).toString()));
	  		  		ps.setInt(2,Aplicativo.iCodEmp);
	  	  		    ps.setInt(3,ListaCampos.getMasterFilial("EQRMA"));
	  	  		    ps.setInt(4,Integer.parseInt(tab.getValor(iLin,1).toString()));
	  	  		    ps.setInt(5,Integer.parseInt(tab.getValor(iLin,2).toString()));
	  	  		    ps.executeUpdate();
	  		  	}
	  		  	
	  		  	tab.limpa();
	  		  	
  		  		con.commit();
	  		}
	  		catch(Exception err){
	  			Funcoes.mensagemErro(this,"N�o foi poss�vel efetuar a expedi��o do �tem!",true,con,err);
	  			err.printStackTrace();
	  		}
 		}
  		catch(Exception err){
  			Funcoes.mensagemErro(this,"N�o foi poss�vel efetuar a expedi��o do �tem!",true,con,err);
  			err.printStackTrace();
  		}
  		finally{
  			sSQL = null;
  			ps = null;
  		}
  	}
  }
  private void abreRma() {
	int iRma = ((Integer) tab.getValor(tab.getLinhaSel(), 1)).intValue();
	if (fPrim.temTela("Requisi��o de material") == false) {
		FRma tela = new FRma();
		fPrim.criatela("Requisi��o de material", tela, con);
		tela.exec(iRma);
	}
  }
  private void adicionaItem(ResultSet rs){
	
	int iLin = tab.getNumLinhas();
	tab.adicLinha();
	try {
		tab.setValor(imgAprovada, iLin, 0);//SitItRma
		tab.setValor(new Integer(rs.getInt("CODRMA")), iLin, 1);//CodRma
		tab.setValor(new Integer(rs.getInt("CODITRMA")), iLin, 2);//CodRma
		tab.setValor(rs.getString("CODPROD") == null ? "" : rs.getString("CODPROD") + "",iLin, 3);//CodProd 
		tab.setValor(rs.getString("DESCPROD") == null ? "" : rs.getString("DESCPROD") + "",iLin, 4);//DescProd
		tab.setValor(rs.getString("CODLOTE") == null ? "" : rs.getString("CODLOTE") + "",iLin, 5);//C�d OP
		tab.setValor(rs.getString("CODOP") == null ? "" : rs.getString("CODOP") + "",iLin, 6);//C�d OP
		tab.setValor(rs.getString("SEQOF") == null ? "" : rs.getString("SEQOF") + "",iLin, 7);//Seq OP
		tab.setValor(rs.getString(11) == null ? "" : rs.getString(11) + "",iLin, 8);//Qtd Req
		tab.setValor(rs.getString(12) == null ? "" : rs.getString(12) + "",iLin, 9);//Qtd Aprov
		tab.setValor(txtQtdEntrada.getVlrString(),iLin,10);//Qdt Exp
		tab.setValor(rs.getString(14) == null ? "" : rs.getString(14) + "",iLin, 11);//Saldo Prod
					
		iLin++;
	}
	catch(Exception err){
		err.printStackTrace();
	}
  }
    
  public void buscaItemPrincipal(){
	PreparedStatement ps = null;
	ResultSet rs = null;
    String sChaveRs = null;
    String sChaveTb = "";
	String sSQL = "SELECT R.CODRMA, IT.CODPROD,IT.REFPROD,PD.DESCPROD,IT.SITITRMA,"
					+ "IT.SITAPROVITRMA,IT.SITEXPITRMA,IT.DTINS,IT.DTAPROVITRMA,IT.DTAEXPITRMA,"
					+ "IT.QTDITRMA,IT.QTDAPROVITRMA,IT.QTDEXPITRMA,PD.SLDPROD,IT.CODLOTE,R.CODOP,R.SEQOF,IT.CODITRMA "
					+ "FROM EQRMA R, EQITRMA IT, EQPRODUTO PD "
					+ "WHERE R.CODEMP=IT.CODEMP AND R.CODFILIAL=IT.CODFILIAL AND R.CODRMA=IT.CODRMA "
					+ "AND PD.CODEMP=IT.CODEMP AND PD.CODFILIAL=IT.CODFILIAL AND PD.CODPROD=IT.CODPROD "
					+ "AND IT.CODEMPPD=? AND IT.CODFILIALPD=? AND IT.CODPROD=? "
					+ (txtCodLote.getVlrString().trim().length()>0?"AND IT.CODEMPLE=IT.CODEMPPD AND IT.CODFILIALLE=IT.CODFILIALPD AND IT.CODLOTE=? ":"")
					+ "AND R.CODEMPOF=IT.CODEMP AND R.CODFILIALOF=? AND R.CODOP=? AND R.SEQOP=? AND R.SEQOF=?";
		
	try {
		ps = con.prepareStatement(sSQL);
		int param = 1;
		ps.setInt(param++, Aplicativo.iCodEmp);
		ps.setInt(param++, ListaCampos.getMasterFilial("EQPRODUTO"));
		ps.setInt(param++, txtCodProd.getVlrInteger().intValue());
		if(txtCodLote.getVlrString().trim().length()>0)
		    ps.setString(param++, txtCodLote.getVlrString());
		ps.setInt(param++,ListaCampos.getMasterFilial("PPOP"));
		ps.setInt(param++,txtCodOp.getVlrInteger().intValue());
		ps.setInt(param++,txtSeqOp.getVlrInteger().intValue());
		ps.setInt(param++,txtSeqOf.getVlrInteger().intValue());

		rs = ps.executeQuery();
		
		if(rs.next()) {
			sChaveRs = rs.getString("CODRMA").trim()+"#"+rs.getString("CODITRMA").trim();
			for(int i =0;tab.getNumLinhas()>i;i++){
				sChaveTb = tab.getValor(i,1).toString().trim()+"#"+
						   tab.getValor(i,2).toString().trim();
				if(sChaveTb.equals(sChaveRs)){
					Funcoes.mensagemTemp(Aplicativo.telaPrincipal,"O �tem j� foi adicionado","Busca de �tens de RMA",1);
					return;
				}
			}
			
			if(rs.getString("SITEXPITRMA").equals("PE")){
				if(!rs.getString("SITAPROVITRMA").equals("PE")){
					
					if(new Double(rs.getDouble("QTDAPROVITRMA")).compareTo(new Double(Funcoes.strCurrencyToDouble(txtQtdEntrada.getVlrString())))==0){
						adicionaItem(rs);
					}
					else if (new Double(rs.getDouble("QTDAPROVITRMA")).compareTo(new Double(Funcoes.strCurrencyToDouble(txtQtdEntrada.getVlrString())))<0){
						if(Funcoes.mensagemConfirma(this,"Quantidade aprovada ("+rs.getString("QTDAPROVITRMA").trim()+") � inferior � quantidade � expedir ("+txtQtdEntrada.getVlrString().trim()+") !\nDeseja expedir a quantidade aprovada?")==JOptionPane.YES_OPTION){
							txtQtdEntrada.setVlrDouble(new Double(rs.getDouble("QTDAPROVITRMA")));
							adicionaItem(rs);
						}
					}	
				
				} else
					Funcoes.mensagemTemp(Aplicativo.telaPrincipal,"�tem n�o foi aprovado!","Busca de �tens de RMA",1);
			} else
				Funcoes.mensagemTemp(Aplicativo.telaPrincipal,"�tem j� foi expedido!","Busca de �tens de RMA",1);				
		} else{
			Funcoes.mensagemTemp(Aplicativo.telaPrincipal,"�tem n�o encontrado","Busca de �tens de RMA",1);
			return;
		}
	
		con.commit();
	} catch (SQLException err) {
		Funcoes.mensagemErro(this, "Erro ao carregar a tabela EQRMA!\n"
				+ err.getMessage(),true,con,err);
		err.printStackTrace();
	}
	finally{
		ps = null;
		rs = null;
	    sChaveRs = null;
	    sChaveTb = null;
		sSQL = null;
	}
	  	
  }
  
  public void buscaItemDistribuido(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		int param = 1;
	    String sChaveRs = null;
	    String sChaveTb = "";
		String sSQL = null;
			
		try {
			sSQL = "SELECT R.CODRMA,IT.CODPROD,IT.REFPROD,PD.DESCPROD,IT.SITITRMA,"
				+ "IT.SITAPROVITRMA,IT.SITEXPITRMA,IT.DTINS,IT.DTAPROVITRMA,IT.DTAEXPITRMA,"
				+ "IT.QTDITRMA,IT.QTDAPROVITRMA,IT.QTDEXPITRMA,PD.SLDPROD,IT.CODLOTE,R.CODOP,R.SEQOF,IT.CODITRMA "
				+ "FROM EQRMA R, EQITRMA IT, EQPRODUTO PD "
				+ "WHERE R.CODEMP=IT.CODEMP AND R.CODFILIAL=IT.CODFILIAL AND R.CODRMA=IT.CODRMA "
				+ "AND PD.CODEMP=IT.CODEMP AND PD.CODFILIAL=IT.CODFILIAL "
				+ "AND IT.CODPROD=PD.CODPROD AND IT.CODEMPPD=PD.CODEMP AND IT.CODFILIALPD=PD.CODFILIAL "
				+ "AND R.CODEMPOF=IT.CODEMP AND R.CODFILIALOF=? "
				+ "AND R.CODOP=? AND R.SEQOP=? ";
			ps = con.prepareStatement(sSQL);
			ps.setInt(param++,ListaCampos.getMasterFilial("PPOPFASE"));
			ps.setInt(param++,txtCodOp.getVlrInteger().intValue());
			ps.setInt(param++,txtSeqOp.getVlrInteger().intValue());

			rs = ps.executeQuery();
						
			
			while(rs.next()) {
				txtQtdEntrada.setVlrString(rs.getString("QTDITRMA"));
				sChaveRs = rs.getString("CODRMA").trim()+"#"+rs.getString("CODITRMA").trim();
				for(int i =0;tab.getNumLinhas()>i;i++){
					sChaveTb = tab.getValor(i,1).toString().trim()+"#"+
							   tab.getValor(i,2).toString().trim();
					if(sChaveTb.equals(sChaveRs)){
						Funcoes.mensagemTemp(Aplicativo.telaPrincipal,"O �tem j� foi adicionado","Busca de �tens de RMA",1);
						return;
					}
				}
				
				if(rs.getString("SITEXPITRMA").equals("PE")){
					if(!rs.getString("SITAPROVITRMA").equals("PE")){
						
						if(new Double(rs.getDouble("QTDAPROVITRMA")).compareTo(new Double(Funcoes.strCurrencyToDouble(txtQtdEntrada.getVlrString())))==0){
							adicionaItem(rs);
						} else if (new Double(rs.getDouble("QTDAPROVITRMA")).compareTo(new Double(Funcoes.strCurrencyToDouble(txtQtdEntrada.getVlrString())))<0){
							if(Funcoes.mensagemConfirma(this,"Quantidade aprovada ("+rs.getString("QTDAPROVITRMA").trim()+") � inferior � quantidade � expedir ("+txtQtdEntrada.getVlrString().trim()+") !\nDeseja expedir a quantidade aprovada?")==JOptionPane.YES_OPTION){
								txtQtdEntrada.setVlrDouble(new Double(rs.getDouble("QTDAPROVITRMA")));
								adicionaItem(rs);
							}
						}
					} else
						Funcoes.mensagemTemp(Aplicativo.telaPrincipal,"�tem n�o foi aprovado!","Busca de �tens de RMA",1);
				} else
					Funcoes.mensagemTemp(Aplicativo.telaPrincipal,"�tem j� foi expedido!","Busca de �tens de RMA",1);					
			}
		
			con.commit();
			
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela EQRMA!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		}
		finally{
			ps = null;
			rs = null;
		    sChaveRs = null;
		    sChaveTb = null;
			sSQL = null;
		}		  	
  }
    
  public void beforeCarrega(CarregaEvent cevt){  }
  public void afterCarrega(CarregaEvent cevt){  }
  public void focusGained(FocusEvent e) {
  	if(e.getSource()==txtEntrada)
  		txtEntrada.setVlrString("");
  }
  public void focusLost(FocusEvent e) { 
  	if(e.getSource()==txtEntrada){
  		if(txtEntrada.getVlrString().length()>1)
  			decodeEntrada();
  	}
  }
	
  public void actionPerformed(ActionEvent evt) {
	if (evt.getSource() == btSair) {
		dispose();
	}
	else if(evt.getSource() == btExpedir){
		expedeItens();
	}
  }
  private void limpaCampos(){
  	txtCodLote.setVlrString("");
  	txtCodOp.setVlrString("");
  	txtCodProd.setVlrString("");
  	txtEntrada.setVlrString("");
  	txtSeqOf.setVlrString("");
  }
  private void decodeEntrada(){
  	String sTexto = null;
  	String sResto = null;
  	String sOpcao = null;
  	String sTemp = null;
  	Vector<JTextFieldPad> vCampos = null;
  	JTextFieldPad jtCampo = null;
  	try{
  		sTexto = txtEntrada.getVlrString();
  		limpaCampos();
  		if(sTexto!=null){
  			if (sTexto.length()>0){
  				int iCampos = Funcoes.contaChar(sTexto,'#'); 
  				if(iCampos==6) {
  					vCampos = new Vector<JTextFieldPad>();
  					vCampos.addElement(txtSeqOf);
  					vCampos.addElement(txtCodOp);
  					vCampos.addElement(txtSeqOp);
  					vCampos.addElement(txtCodProd);
  					vCampos.addElement(txtCodLote); 
  					vCampos.addElement(txtQtdEntrada);

  					sResto = sTexto.replace('_','/');
  					
  					sOpcao = sResto.substring(0,sResto.indexOf("#")>-1?sResto.indexOf("#"):sResto.length());
					sResto = sResto.substring(sResto.indexOf("#")+1);
					
					if(sOpcao.equals("P")){
						for(int i=0;vCampos.size()>i;i++){		
	  						jtCampo = (vCampos.elementAt(i)); 
	  						jtCampo.setVlrString(sResto.substring(0,sResto.indexOf("#")>-1?sResto.indexOf("#"):sResto.length()));
	  						sResto = sResto.substring(sResto.indexOf("#")+1);
	  					}
	  					buscaItemPrincipal();
					}
					else if(sOpcao.equals("D")){
						for(int i=0;vCampos.size()>i;i++){		
	  						jtCampo = (vCampos.elementAt(i));
	  						sTemp = sResto.substring(0,sResto.indexOf("#")>-1?sResto.indexOf("#"):sResto.length());
	  						if(jtCampo==txtCodOp || jtCampo==txtSeqOp)
	  							jtCampo.setVlrString(sTemp);
	  						else
	  							jtCampo.setVlrString("");
	  						sResto = sResto.substring(sResto.indexOf("#")+1);
	  					}
	  					buscaItemDistribuido();
					}
					
  					txtEntrada.requestFocus();
  					
  				} else
  					Funcoes.mensagemInforma(this,"Entrada inv�lida!\nN�mero de campos incoerente."+Funcoes.contaChar(sTexto,'#'));
  			} else
  				Funcoes.mensagemInforma(this,"Entrada inv�lida!\nTexto em branco.");
  	  	} else
  	  		Funcoes.mensagemInforma(this,"Entrada inv�lida!\nTexto nulo.");
  	}
  	catch (Exception e){
  		Funcoes.mensagemErro(this,"Erro ao decodificar c�digo de barras!",true,con,e);
  		e.printStackTrace();
  	}
  	finally{
  		sTexto = null;
  	  	sResto = null;
  	  	sOpcao = null;
  	  	sTemp = null;
  	  	vCampos = null;
  	  	jtCampo = null;
  	}
  }	
}
