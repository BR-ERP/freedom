/**
 * @version 07/03/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FEstacao.java <BR>
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
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FEstacao extends FDetalhe implements PostListener, ActionListener{
    private Painel pinCab = new Painel();
    private Painel pinDet = new Painel();
	private Painel pinEst = new Painel(0,80);
	private ListaCampos lcImp = new ListaCampos(this,"IP");
	private JTextFieldPad txtCodEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtDescEst = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtNroImp = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtCodImp = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtPortaWin = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtPortaLin = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldFK txtDescImp = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JCheckBoxPad cbImpPad = new JCheckBoxPad("Impressora padr�o?","S","N");
	private Vector vValTipoUsoImp = new Vector();
	private Vector vLabTipoUsoImp = new Vector();
	private JRadioGroup rgTipoUsoImp = null;
	
	private Tabela tabEst = new Tabela();

	public FEstacao() {
		setTitulo("Cadastro de esta��es de trabalho"); 
		setAtribos(50, 10, 550, 460);
	    pinCab = new Painel(530, 50);
	}
    private void montaTela() {
	  	//adicTab("Esta��o", pinEst); 
	
        lcImp.add(new GuardaCampo(txtCodImp, "CodImp", "C�d.imp.", ListaCampos.DB_PK,  false));
        lcImp.add(new GuardaCampo(txtDescImp, "DescImp", "Descri��o da impressora", ListaCampos.DB_SI, false));
        lcImp.montaSql(false, "IMPRESSORA", "SG");
        lcImp.setQueryCommit(false);
        lcImp.setReadOnly(true);
        txtCodImp.setTabelaExterna(lcImp);
    	
	  	lcCampos.addPostListener(this);
	    pinCab = new Painel(740, 100);
	    setListaCampos(lcCampos);
	    setAltCab(100);
	    setPainel(pinCab, pnCliCab);
  	
	  	adicCampo(txtCodEst, 7, 20, 80, 20, "Codest", "N� esta��o", ListaCampos.DB_PK, true);
	  	adicCampo(txtDescEst, 90, 20, 307, 20, "Descest", "Descri��o da esta��o de trabalho",ListaCampos.DB_SI,true);
	    setListaCampos(true,"ESTACAO", "SG");
	    lcCampos.setQueryInsert(false);
	    
	    setAltDet(190);
	    pinDet = new Painel(740, 190);
	    setPainel(pinDet, pnDet);
	    setListaCampos(lcDet);
	    setNavegador(navRod);
	    vValTipoUsoImp.addElement("NF");
	    vValTipoUsoImp.addElement("NS");
	    vValTipoUsoImp.addElement("PD");
	    vValTipoUsoImp.addElement("RS");
	    vValTipoUsoImp.addElement("RG");
	    vValTipoUsoImp.addElement("TO");
	    vLabTipoUsoImp.addElement("Nota fiscal");
	    vLabTipoUsoImp.addElement("Nota fiscal - servi�o");
	    vLabTipoUsoImp.addElement("Pedido");
	    vLabTipoUsoImp.addElement("Relat�rio simples");
	    vLabTipoUsoImp.addElement("Relat�rio gr�fico");
	    vLabTipoUsoImp.addElement("Todos menos NF");
	    rgTipoUsoImp = new JRadioGroup(2,3,vLabTipoUsoImp,vValTipoUsoImp);
	    adicCampo(txtNroImp,7,20,80,20,"NroImp","N� imp.",ListaCampos.DB_PK,true);
	    adicCampo(txtCodImp,90,20,80,20,"CodImp","C�d.imp.",ListaCampos.DB_FK,true);
	    adicDescFK(txtDescImp,173,20,300,20,"DescImp","Descri��o da impressora");
	    adicCampo(txtPortaWin,7,60,100,20,"PortaWin","Porta Windows",ListaCampos.DB_SI,true);
	    adicCampo(txtPortaLin,110,60,100,20,"PortaLin","Porta Linux",ListaCampos.DB_SI,true);
	    adicDB(cbImpPad, 213,60,200,20, "ImpPad", "Padr�o", true);
	    adicDB(rgTipoUsoImp,7,100,500,50,"TipoUsoImp","Tipo uso", true);
	    
	    lcDet.addPostListener(this);
	    setListaCampos(true, "ESTACAOIMP", "SG");
	    lcDet.setQueryInsert(false);
	    montaTab();

	    tab.setTamColuna(30, 0);
	    tab.setTamColuna(70, 1);
	    tab.setTamColuna(230, 2);
	    
    }
    public void afterPost(PostEvent pevt) {
    	PreparedStatement ps = null;
    	String sSQL = null;
    	int iNroImp = 0;
    	if ((pevt.getListaCampos()==lcDet) && (cbImpPad.getVlrString().equals("S"))) {
	    	try {
	    		iNroImp = txtNroImp.getVlrInteger().intValue();
	    		sSQL = "EXECUTE PROCEDURE SGESTACAOIMPSP01(?,?,?,?,?)";
	    		ps = con.prepareStatement(sSQL);
	    		ps.setInt(1,Aplicativo.iCodEmp);
	    		ps.setInt(2,ListaCampos.getMasterFilial("SGESTACAOIMP"));
	    		ps.setInt(3,txtCodEst.getVlrInteger().intValue());
	    		ps.setInt(4,txtNroImp.getVlrInteger().intValue());
	    		ps.setString(5,cbImpPad.getVlrString());
	    		ps.execute();
	    		ps.close();
	    		if (!con.getAutoCommit())
	    			con.commit();
	    		lcDet.carregaItens();
	    		txtNroImp.setVlrInteger(new Integer(iNroImp));
	    		lcDet.carregaDados();
	    	}
	    	catch (SQLException e) {
	    		Funcoes.mensagemErro(this,"Erro ajustando impressora padr�o!\n"+e.getMessage());
	    	}
	    	finally {
	        	ps = null;
	        	sSQL = null;
	        	iNroImp = 0;
	    	}
    	}
    }
    
    public void execShow(Connection cn) {
    	con = cn;
    	lcImp.setConexao(cn);
    	montaTela();	
    	super.execShow(cn);
    }
  }
