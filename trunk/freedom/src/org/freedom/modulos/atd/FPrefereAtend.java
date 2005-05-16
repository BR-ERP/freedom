/**
 * @version 25/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FPrefereAtend.java <BR>
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
 * 
 */

package org.freedom.modulos.atd;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FTabDados;

public class FPrefereAtend extends FTabDados {
	private JPanelPad pinGeral = null;
	private JPanelPad pinTipo = null;
	private JPanelPad pinSetor = null;
	private JPanelPad pinConv = null;
	private JPanelPad pinOrc = null;

	private JTextFieldPad txtClassMedida = new JTextFieldPad(JTextFieldPad.TP_STRING, 20 , 0);
	private JTextFieldPad txtCodTpAtend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTpAtend = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcTpAtend = new ListaCampos(this,"TO");
	private JTextFieldPad txtCodTpAtend2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTpAtend2 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcTpAtend2 = new ListaCampos(this,"T2");
	private JTextFieldPad txtCodTpAtend3 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTpAtend3 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcTpAtend3 = new ListaCampos(this,"T3");
	private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcSetor = new ListaCampos(this,"TO");
	private JTextFieldPad txtCodSetor2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescSetor2 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcSetor2 = new ListaCampos(this,"T2");
	private JTextFieldPad txtCodAtend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8 , 0);
	private JTextFieldFK txtNomeAtend = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0 );
	private ListaCampos lcAtend = new ListaCampos(this,"AE");
	private JTextFieldPad txtCodTipoCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK  txtDescTipoCli = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcTipoCli = new ListaCampos(this,"TI");
	private JTextFieldPad txtCodClas = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK  txtDescClas = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private ListaCampos lcClas = new ListaCampos(this,"CI");
	private JTextFieldPad txtCodTBA = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTBA = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private ListaCampos lcTabAC = new ListaCampos(this,"TA");
	private JTextFieldPad txtCodITTBA = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescITTBA = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private ListaCampos lcTabITAC = new ListaCampos(this,"TA");
	private JTextFieldPad txtCodTBV = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTBV = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private ListaCampos lcTabAV = new ListaCampos(this,"TA");
	private JTextFieldPad txtCodITTBV = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescITTBV = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	
	private ListaCampos lcTabITAV = new ListaCampos(this,"TA");
	private ListaCampos lcVend = new ListaCampos(this,"VD");
	
	public FPrefereAtend() {
		setTitulo("Prefer�ncias do Atendimento");
		setAtribos(50, 50, 355, 375);
		
		lcCampos.setMensInserir(false);
		
//Tipo:
          
        lcAtend.add(new GuardaCampo(txtCodAtend, "CodAtend","C�d.atend.", ListaCampos.DB_PK ,true));
        lcAtend.add(new GuardaCampo(txtNomeAtend, "NomeAtend","Nome atendente" ,ListaCampos.DB_SI, false));
        lcAtend.montaSql(false,"ATENDENTE", "AT");
        lcAtend.setQueryCommit(false);
        lcAtend.setReadOnly(true);
        txtCodAtend.setTabelaExterna(lcAtend);
        
        lcTipoCli.add(new GuardaCampo(txtCodTipoCli, "CodTipoCli","C�d.tp.cli.", ListaCampos.DB_PK, false));
        lcTipoCli.add(new GuardaCampo(txtDescTipoCli, "DescTipoCli","Descri��o do tipo de cliente", ListaCampos.DB_SI, false));
        lcTipoCli.montaSql(false, "TIPOCLI", "VD");
        lcTipoCli.setQueryCommit(false);
        lcTipoCli.setReadOnly(true);
        txtCodTipoCli.setTabelaExterna(lcTipoCli);

		lcClas.add(new GuardaCampo(txtCodClas, "CodClasCli","C�d.c.cli.", ListaCampos.DB_PK, false));
		lcClas.add(new GuardaCampo(txtDescClas, "DescClasCli","Descri��o da calssifica��o do cliente", ListaCampos.DB_SI, false));
		lcClas.montaSql(false, "CLASCLI", "VD");
		lcClas.setQueryCommit(false);
		lcClas.setReadOnly(true);
		txtCodClas.setTabelaExterna(lcClas);
		
		lcTabAC.add(new GuardaCampo(txtCodTBA, "CodTB","C�d.tab.", ListaCampos.DB_PK, false));
		lcTabAC.add(new GuardaCampo(txtDescTBA, "DescTB","Descri��o da tabela padr�o para aceite", ListaCampos.DB_SI, false));
		lcTabAC.montaSql(false, "TABELA", "SG");
		lcTabAC.setQueryCommit(false);
		lcTabAC.setReadOnly(true);
		txtCodTBA.setTabelaExterna(lcTabAC);

		lcTabITAC.add(new GuardaCampo(txtCodITTBA, "CodITTB","C�d.it.tab.", ListaCampos.DB_PK, false));
		lcTabITAC.add(new GuardaCampo(txtDescITTBA, "DescITTB","Descri��o da situa��o para aceite", ListaCampos.DB_SI, false));
		lcTabITAC.setDinWhereAdic("CODTB = #N",txtCodTBA);
		lcTabITAC.montaSql(false, "ITTABELA", "SG");
		lcTabITAC.setQueryCommit(false);
		lcTabITAC.setReadOnly(true);
		txtCodITTBA.setTabelaExterna(lcTabITAC);

		lcTabAV.add(new GuardaCampo(txtCodTBV, "CodTB","C�d.tab.", ListaCampos.DB_PK , false));
		lcTabAV.add(new GuardaCampo(txtDescTBV, "DescTB","Descri��o da tabela padr�o para aceite", ListaCampos.DB_SI, false));
		lcTabAV.montaSql(false, "TABELA", "SG");
		lcTabAV.setQueryCommit(false);
		lcTabAV.setReadOnly(true);
		txtCodTBV.setTabelaExterna(lcTabAV);

		lcTabITAV.add(new GuardaCampo(txtCodITTBV, "CodITTB","C�d.it.tab.",ListaCampos.DB_PK, false));
		lcTabITAV.add(new GuardaCampo(txtDescITTBV, "DescITTB","Descri��o da situa��o para aceite", ListaCampos.DB_SI, false));
		lcTabITAV.setDinWhereAdic("CODTB = #N",txtCodTBV);
		lcTabITAV.montaSql(false, "ITTABELA", "SG");
		lcTabITAV.setQueryCommit(false);
		lcTabITAV.setReadOnly(true);
		txtCodITTBV.setTabelaExterna(lcTabITAV);
        
		lcVend.add(new GuardaCampo(txtCodVend, "CodVend","C�d.comiss.", ListaCampos.DB_PK, false));
		lcVend.add(new GuardaCampo(txtNomeVend, "NomeVend","Nome do comissionado", ListaCampos.DB_SI, false));
		lcVend.montaSql(false, "VENDEDOR", "VD");
		lcVend.setQueryCommit(false);
		lcVend.setReadOnly(true);
		txtCodVend.setTabelaExterna(lcVend);
           
		lcTpAtend.add(new GuardaCampo(txtCodTpAtend, "CodTpAtendo","C�d.tp.atend.", ListaCampos.DB_PK, true));
		lcTpAtend.add(new GuardaCampo(txtDescTpAtend, "DescTpAtendo","Descri��o do tipo de atendimento", ListaCampos.DB_SI ,false));
		lcTpAtend.montaSql(false, "TIPOATENDO", "AT");
		lcTpAtend.setQueryCommit(false);
		lcTpAtend.setReadOnly(true);
		txtCodTpAtend.setTabelaExterna(lcTpAtend);

		lcTpAtend2.add(new GuardaCampo(txtCodTpAtend2, "CodTpAtendo","C�d.tp.atend.", ListaCampos.DB_PK, true));
		lcTpAtend2.add(new GuardaCampo(txtDescTpAtend2, "DescTpAtendo","Descri��o do tipo de atendimento", ListaCampos.DB_SI ,false));
		lcTpAtend2.montaSql(false, "TIPOATENDO", "AT");
		lcTpAtend2.setQueryCommit(false);
		lcTpAtend2.setReadOnly(true);
		txtCodTpAtend2.setTabelaExterna(lcTpAtend2);

		lcTpAtend3.add(new GuardaCampo(txtCodTpAtend3, "CodTpAtendo","C�dtp.atend.",ListaCampos.DB_PK, true));
		lcTpAtend3.add(new GuardaCampo(txtDescTpAtend3, "DescTpAtendo","Descri��o do tipo de atendimento", ListaCampos.DB_SI, false));
		lcTpAtend3.montaSql(false, "TIPOATENDO", "AT");
		lcTpAtend3.setQueryCommit(false);
		lcTpAtend3.setReadOnly(true);
		txtCodTpAtend3.setTabelaExterna(lcTpAtend3);
		
		pinGeral = new JPanelPad(330,350);
		setPainel(pinGeral);
		adicTab("Geral", pinGeral);
		adicCampo(txtCodAtend,7,25,80,20,"CodAtend","C�d.atend.",ListaCampos.DB_FK,txtNomeAtend,true);
		adicDescFK(txtNomeAtend,90,25,230,20,"NameAtend","Nome do atendente respons�vel.");
		adicCampo(txtClassMedida,7,65,200,20,"ClassMedida","Classe p/ ficha de medida",ListaCampos.DB_SI,false);
		
		pinTipo = new JPanelPad(330, 350);
		setPainel(pinTipo);
		adicTab("Tipos de atendimento", pinTipo);
		adicCampo(txtCodTpAtend,7,25,80,20,"CodTpAtendo","C�d.tp.atend.",ListaCampos.DB_FK,txtDescTpAtend,true);
		adicDescFK(txtDescTpAtend,90,25,230,20,"DescTpAtendo","Descri��o do tipo de levantamento.");
		adicCampo(txtCodTpAtend2,7,65,80,20,"CodTpAtendo2","C�d.tp.atend.",ListaCampos.DB_FK,txtDescTpAtend2,true);
		adicDescFK(txtDescTpAtend2,90,65,230,20,"DescTpAtendo","Descri��o do tipo de cadastro.");
		adicCampo(txtCodTpAtend3,7,105,80,20,"CodTpAtendo3","C�d.tp.atend.",ListaCampos.DB_FK,txtDescTpAtend3,true);
		adicDescFK(txtDescTpAtend3,90,105,230,20,"DescTpAtendo","Descri��o do tipo de or�amento.");
		
		txtCodTpAtend2.setNomeCampo("CodTpAtendo");
		txtCodTpAtend3.setNomeCampo("CodTpAtendo");

//Setor:
		
		lcSetor.add(new GuardaCampo(txtCodSetor, "CodSetAt","C�d.setor", ListaCampos.DB_PK, true));
		lcSetor.add(new GuardaCampo(txtDescSetor, "DescSetAt","Descri��o do setor", ListaCampos.DB_SI, false));
		lcSetor.montaSql(false, "SETOR", "AT");
		lcSetor.setQueryCommit(false);
		lcSetor.setReadOnly(true);
		txtCodSetor.setTabelaExterna(lcSetor);

		lcSetor2.add(new GuardaCampo(txtCodSetor2, "CodSetAt","C�d.setor", ListaCampos.DB_PK, true));
		lcSetor2.add(new GuardaCampo(txtDescSetor2, "DescSetAt","Descri��o do setor", ListaCampos.DB_SI, false));
		lcSetor2.montaSql(false, "SETOR", "AT");
		lcSetor2.setQueryCommit(false);
		lcSetor2.setReadOnly(true);
		txtCodSetor2.setTabelaExterna(lcSetor2);

		pinSetor = new JPanelPad(330, 350);
		setPainel(pinSetor);
		adicTab("Setor de atendimento", pinSetor);
		adicCampo(txtCodSetor,7,25,80,20,"CodSetAt","C�d.setor",ListaCampos.DB_FK,txtDescSetor,true);
		adicDescFK(txtDescSetor,90,25,230,20,"DescSetAt","Setor de cadastro.");
		adicCampo(txtCodSetor2,7,65,80,20,"CodSetAt2","C�d.setor",ListaCampos.DB_FK,txtDescSetor2,true);
		adicDescFK(txtDescSetor2,90,65,230,20,"DescSetAt","Setor de or�amento.");
		
		pinConv = new JPanelPad(330, 350);
		setPainel(pinConv);
		adicTab("Conveniado", pinConv);
		adicCampo(txtCodTipoCli,7,25,80,20,"CodTipoCli","C�d.tp.cli.",ListaCampos.DB_FK,txtDescTipoCli,false);
		adicDescFK(txtDescTipoCli,90,25,230,20,"DescTipoCli","Descri��o do tipo de cliente.");
		adicCampo(txtCodClas,7,65,80,20,"CodClasCli","C�d.c.cli.",ListaCampos.DB_FK,txtDescClas,false);
		adicDescFK(txtDescClas,90,65,230,20,"DescClasCli","Descri��o da classifica��o do cliente.");
		
		pinOrc = new JPanelPad(330, 350);
		setPainel(pinOrc);
		adicTab("Or�amento", pinOrc);
		adicCampo(txtCodTBA,7,25,80,20,"CodTBA","C�d.tab.",ListaCampos.DB_FK,txtDescTBA,false);
		adicDescFK(txtDescTBA,90,25,230,20,"DescTB","Descri��o da tabela padr�o para aceite.");
		adicCampo(txtCodITTBA,7,65,80,20,"CodITTBA","C�d.tab.",ListaCampos.DB_FK,txtDescITTBA,false);
		adicDescFK(txtDescITTBA,90,65,230,20,"DescITTB","Descri��o da situa��o para aceite.");
		adicCampo(txtCodTBV,7,105,80,20,"CodTBV","C�d.tab.",ListaCampos.DB_FK,txtDescTBV,false);
		adicDescFK(txtDescTBV,90,105,230,20,"DescTB","Descri��o da tabela padr�o para aceite.");
		adicCampo(txtCodITTBV,7,150,80,20,"CodITTBV","C�d.tab.",ListaCampos.DB_FK,txtDescITTBV,false);
		adicDescFK(txtDescITTBV,90,150,230,20,"DescITTB","Descri��o da situa��o para aceite.");
		adicCampo(txtCodVend,7,195,80,20,"CodVend","C�d.comiss.",ListaCampos.DB_FK,txtNomeVend,true);
		adicDescFK(txtNomeVend,90,195,230,20,"NomeVend","Nome do comissionado padr�o");

		setListaCampos(false, "PREFERE2", "SG");
		
		txtCodSetor2.setNomeCampo("CodSetAt");
		txtCodTBA.setNomeCampo("CodTB");
		txtCodTBV.setNomeCampo("CodTB");
		txtCodITTBA.setNomeCampo("CodITTB");
		txtCodITTBV.setNomeCampo("CodITTB");
		txtCodVend.setNomeCampo("CodVend");

		nav.setAtivo(0,false);
		nav.setAtivo(1,false);
	}
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcTpAtend.setConexao(cn);
		lcTpAtend2.setConexao(cn);
		lcTpAtend3.setConexao(cn);
		lcSetor.setConexao(cn);
		lcSetor2.setConexao(cn);
		lcAtend.setConexao(cn);
		lcTipoCli.setConexao(cn);
		lcClas.setConexao(cn);
		lcTabAC.setConexao(cn);
		lcTabITAC.setConexao(cn);
		lcTabAV.setConexao(cn);
		lcTabITAV.setConexao(cn);
		lcVend.setConexao(cn);
		lcCampos.carregaDados();
	}
}