/**
 * @version 29/10/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FItCLFiscal.java <BR>
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
 * Detalhe para classifica��o fiscal.
 * 
 */

package org.freedom.modulos.std;
import java.sql.Connection;
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FDetalhe;

public class FItCLFiscal extends FDetalhe {
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private JTextFieldPad txtCodFisc = new JTextFieldPad(13);
  private JTextFieldPad txtDescFisc = new JTextFieldPad(40);
  private JTextFieldPad txtCodTipoFisc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFiscCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodMens = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtMens = new JTextFieldFK(JTextFieldPad.TP_STRING,10000,0);
  private JTextFieldPad txtCodItClFiscal = new JTextFieldPad(5);
  private JTextFieldPad txtCodTratTrib = new JTextFieldPad(5);
  private JTextFieldFK txtDescTratTrib = new JTextFieldFK();
  private JTextFieldPad txtRedFisc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtAliqFisc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JCheckBoxPad cbNoUF = new JCheckBoxPad("Sim","S","N");
  private JComboBoxPad cbOrig = null;
  private Vector vValsOrig = new Vector();
  private Vector vLabsOrig = new Vector();
  private JRadioGroup rgTipoFisc = null;
  private Vector vTipoVals = new Vector();
  private Vector vTipoLabs = new Vector();
  private ListaCampos lcTratTrib = new ListaCampos(this,"TT");
  private ListaCampos lcTipoFiscCli = new ListaCampos(this,"FC");
  private ListaCampos lcMens = new ListaCampos(this,"ME");
  public FItCLFiscal () {
    setTitulo("Cadastro de varia��es de ICMS");
    setAtribos( 50, 50, 450, 470);

    txtCodFisc.setAtivo(false);
    txtDescFisc.setAtivo(false);

    setListaCampos(lcCampos);
    pnCab.remove(1); //Remove o navegador do cabe�alho.
    setAltCab(60);
    setPainel( pinCab, pnCliCab);
    adicCampo(txtCodFisc, 7, 20, 100, 20,"CodFisc","C�digo",JTextFieldPad.TP_STRING,13,0,true,false,null,true);
    adicCampo(txtDescFisc, 110, 20, 260, 20,"DescFisc","Descri��o",JTextFieldPad.TP_STRING,50,0,false,false,null,true);
    setListaCampos( true, "CLFISCAL", "LF");
    lcCampos.setReadOnly(true);    


    lcTratTrib.add(new GuardaCampo( txtCodTratTrib, 7, 100, 80, 20, "CodTratTrib", "C�digo", true, false, null, JTextFieldPad.TP_STRING,true),"txtCodRegrax");
    lcTratTrib.add(new GuardaCampo( txtDescTratTrib, 90, 100, 200, 20, "DescTratTrib", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescRegrax");
    lcTratTrib.montaSql(false, "TRATTRIB", "LF");
    lcTratTrib.setQueryCommit(false);
    lcTratTrib.setReadOnly(true);
    txtCodTratTrib.setTabelaExterna(lcTratTrib);

	lcTipoFiscCli.add(new GuardaCampo( txtCodTipoFisc, 7, 100, 80, 20, "CodFiscCli", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodRegrax");
	lcTipoFiscCli.add(new GuardaCampo( txtDescFiscCli, 90, 100, 200, 20, "DescFiscCli", "Descri��o", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescRegrax");
	lcTipoFiscCli.montaSql(false, "TIPOFISCCLI", "LF");
	lcTipoFiscCli.setQueryCommit(false);
	lcTipoFiscCli.setReadOnly(true);
	txtCodTipoFisc.setTabelaExterna(lcTipoFiscCli);

	lcMens.add(new GuardaCampo( txtCodMens, 7, 100, 80, 20, "CodMens", "C�digo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodRegrax");
	lcMens.add(new GuardaCampo( txtMens, 90, 100, 200, 20, "Mens", "Mensagem", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescRegrax");
	lcMens.montaSql(false, "MENSAGEM", "LF");
	lcMens.setQueryCommit(false);
	lcMens.setReadOnly(true);
	txtCodMens.setTabelaExterna(lcMens);

	vTipoLabs.addElement("Isens�o (n�o tributada)");
	vTipoLabs.addElement("Substitui��o Tribut�ria");
	vTipoLabs.addElement("N�o inside (outras)");
	vTipoLabs.addElement("Tributa��o Integral");
	vTipoVals.addElement("II");
	vTipoVals.addElement("FF");
	vTipoVals.addElement("NN");
	vTipoVals.addElement("TT");
	rgTipoFisc = new JRadioGroup( 2, 2, vTipoLabs, vTipoVals);

    vLabsOrig.addElement("Nacional");
    vLabsOrig.addElement("Estrangeira - Importa��o direta");
    vLabsOrig.addElement("Estrangeira - Adquirida no mercado interno");
    vValsOrig.addElement("0");
    vValsOrig.addElement("1");
    vValsOrig.addElement("2");
    cbOrig = new JComboBoxPad(vLabsOrig,vValsOrig);

    setAltDet(260);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtCodItClFiscal, 7, 20, 60, 20,"CodItFisc","Item",JTextFieldPad.TP_INTEGER,5,0,true,false,null,true);
    adicCampo(txtCodTratTrib, 70, 20, 77, 20, "CodTratTrib", "C�digo", JTextFieldPad.TP_STRING, 2, 0, false, true, txtDescTratTrib, true);
    adicDescFK(txtDescTratTrib, 150, 20, 220, 20, "DescTratTrib", "e descri��o da tratamento tribut�rio", JTextFieldPad.TP_STRING, 60, 0);
	adicCampo(txtRedFisc, 7, 60, 85, 20, "RedFisc", "Redu��o", JTextFieldPad.TP_DECIMAL, 9, 2, false, false, null, false);
    adicDB(cbOrig, 95, 60, 187, 25, "OrigFisc", "Origem",JTextFieldPad.TP_STRING,true);
	adicDB(cbNoUF, 285, 60, 77, 20, "NOUFITFISC", "No Estado",JTextFieldPad.TP_STRING,true);
	adicCampo(txtAliqFisc, 7, 105, 85, 20, "AliqFisc", "Al�quota", JTextFieldPad.TP_DECIMAL, 9, 2, false, false, null, false);
	adicCampo(txtCodTipoFisc, 95, 105, 77, 20, "CodFiscCli", "C�digo", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescFiscCli, false);
	adicDescFK(txtDescFiscCli, 175, 105, 195, 20, "DescTratTrib", "e descri��o do tipo fiscal de cliente", JTextFieldPad.TP_STRING, 40, 0);
	adicDB(rgTipoFisc, 7, 150, 363, 50, "TipoFisc", "Situa��o do ICMS:",JTextFieldPad.TP_STRING,true);
	adicCampo(txtCodMens, 7, 220, 80, 20, "CodMens", "C�digo", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtMens, false);
	adicDescFK(txtMens, 90, 220, 280, 20, "Mens", "e mensagem", JTextFieldPad.TP_STRING, 10000, 0);
    setListaCampos( true, "ITCLFISCAL", "LF");
    lcDet.setQueryInsert(false);

    montaTab();
    
    tab.setTamColuna(190,2);
    
  }
  public void exec(String sCodFisc) {
	txtCodFisc.setVlrString(sCodFisc);
	lcCampos.carregaDados();
  }
  public void execShow(Connection cn) {
	lcMens.setConexao(cn);      
	lcTratTrib.setConexao(cn);      
	lcTipoFiscCli.setConexao(cn);      
	super.execShow(cn);
  }
}
