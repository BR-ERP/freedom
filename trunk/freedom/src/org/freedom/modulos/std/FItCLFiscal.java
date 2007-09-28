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

import org.freedom.acao.CarregaEvent;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDetalhe;


public class FItCLFiscal extends FDetalhe {

	private static final long serialVersionUID = 1L;

  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodFisc = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtDescFisc = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodTipoFisc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFiscCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodMens = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtMens = new JTextFieldFK(JTextFieldPad.TP_STRING,10000,0);
  private JTextFieldPad txtCodItClFiscal = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtCodTratTrib = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private JTextFieldFK txtDescTratTrib = new JTextFieldFK(JTextFieldPad.TP_STRING, 60, 0);
  private JTextFieldPad txtRedFisc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,9,2);
  private JTextFieldPad txtAliqICMS = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,9,2);
  private JTextFieldPad txtAliqIPI = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,9,2);
  private JCheckBoxPad cbNoUF = new JCheckBoxPad("Sim","S","N");
  private JComboBoxPad cbOrig = null;
  private JRadioGroup rgTpRedIcmsFisc = null;
  private Vector<String> vTpRedIcmsFiscLabs = new Vector<String>();
  private Vector<String> vTpRedIcmsFiscVals = new Vector<String>();  
  private Vector<String> vValsOrig = new Vector<String>();
  private Vector<String> vLabsOrig = new Vector<String>();
  private JRadioGroup rgTipoFisc = null;
  private Vector<String> vTipoVals = new Vector<String>();
  private Vector<String> vTipoLabs = new Vector<String>();
  private ListaCampos lcTratTrib = new ListaCampos(this,"TT");
  private ListaCampos lcTipoFiscCli = new ListaCampos(this,"FC");
  private ListaCampos lcMens = new ListaCampos(this,"ME");
  public FItCLFiscal () {
    setTitulo("Cadastro de varia��es de ICMS");
    setAtribos( 50, 50, 510, 520);

    txtCodFisc.setAtivo(false);
    txtDescFisc.setAtivo(false);

    setListaCampos(lcCampos);
    pnCab.remove(1); //Remove o navegador do cabe�alho.
    setAltCab(60);
    setPainel( pinCab, pnCliCab);
    adicCampo(txtCodFisc, 7, 20, 100, 20,"CodFisc","C�d.c.fisc.", ListaCampos.DB_PK, true);
    adicCampo(txtDescFisc, 110, 20, 340, 20,"DescFisc","Descri��o do classifica��o fiscal", ListaCampos.DB_SI, true);
    setListaCampos( true, "CLFISCAL", "LF");
    lcCampos.setReadOnly(true);    


    lcTratTrib.add(new GuardaCampo( txtCodTratTrib, "CodTratTrib", "C�d.tr.trib.", ListaCampos.DB_PK, true));
    lcTratTrib.add(new GuardaCampo( txtDescTratTrib, "DescTratTrib", "Descri��o do tratamento tribut�rio", ListaCampos.DB_SI, false));
    lcTratTrib.montaSql(false, "TRATTRIB", "LF");
    lcTratTrib.setQueryCommit(false);
    lcTratTrib.setReadOnly(true);
    txtCodTratTrib.setTabelaExterna(lcTratTrib);

	lcTipoFiscCli.add(new GuardaCampo( txtCodTipoFisc, "CodFiscCli", "C�d.c.fisc.", ListaCampos.DB_PK, false));
	lcTipoFiscCli.add(new GuardaCampo( txtDescFiscCli, "DescFiscCli", "Descri��o da classifica��o fiscal", ListaCampos.DB_SI, false));
	lcTipoFiscCli.montaSql(false, "TIPOFISCCLI", "LF");
	lcTipoFiscCli.setQueryCommit(false);
	lcTipoFiscCli.setReadOnly(true);
	txtCodTipoFisc.setTabelaExterna(lcTipoFiscCli);

	lcMens.add(new GuardaCampo( txtCodMens, "CodMens", "C�d.mems.", ListaCampos.DB_PK, false));
	lcMens.add(new GuardaCampo( txtMens, "Mens", "Mensagem", ListaCampos.DB_SI, false));
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
	rgTipoFisc = new JRadioGroup<String, String>( 2, 2, vTipoLabs, vTipoVals);

	vLabsOrig.addElement("<--Selecione-->");
    vLabsOrig.addElement("Nacional");
    vLabsOrig.addElement("Estrangeira - Importa��o direta");
    vLabsOrig.addElement("Estrangeira - Adquirida no mercado interno");
    vValsOrig.addElement("");
    vValsOrig.addElement("0");
    vValsOrig.addElement("1");
    vValsOrig.addElement("2");
    cbOrig = new JComboBoxPad(vLabsOrig,vValsOrig, JComboBoxPad.TP_STRING,1,0);

	vTpRedIcmsFiscLabs.addElement( "Base ICMS" );
	vTpRedIcmsFiscLabs.addElement( "Valor ICMS" );
	vTpRedIcmsFiscVals.addElement( "B" );
	vTpRedIcmsFiscVals.addElement( "V" );
	rgTpRedIcmsFisc = new JRadioGroup<String, String>( 2, 1, vTpRedIcmsFiscLabs, vTpRedIcmsFiscVals );    
    rgTpRedIcmsFisc.setVlrString( "B" );
    
    setAltDet(310);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtCodItClFiscal, 7, 20, 60, 20,"CodItFisc","Item", ListaCampos.DB_PK, true);
    adicCampo(txtCodTratTrib, 70, 20, 77, 20, "CodTratTrib", "C�d.tr.tab.", ListaCampos.DB_FK, txtDescTratTrib, true);
    adicDescFK(txtDescTratTrib, 150, 20, 300, 20, "DescTratTrib", "Descri��o da tratamento tribut�rio");
    adicDB(cbOrig, 7, 60, 355, 25, "OrigFisc", "Origem",true);
	adicDB(cbNoUF, 380, 60, 80, 20, "NOUFITFISC", "No Estado",true);
	adicCampo(txtAliqIPI, 7, 105, 95, 20, "AliqIPIFisc", "% Al�q.IPI", ListaCampos.DB_SI, false);
	adicCampo(txtAliqICMS, 110, 105, 95, 20, "AliqFisc", "% Al�q.ICMS", ListaCampos.DB_SI, false);
	adicCampo(txtRedFisc, 213, 105, 95, 20, "RedFisc", "% Redu��o ICMS", ListaCampos.DB_SI, false);
	adicDB( rgTpRedIcmsFisc, 320, 105, 140, 60, "tpredicmsfisc", "Tipo de Redu��o", false );
	adicCampo(txtCodTipoFisc, 7, 145, 80, 20, "CodFiscCli", "C�d.fisc.cli.", ListaCampos.DB_FK, txtDescFiscCli, false);
	adicDescFK(txtDescFiscCli, 90, 145, 223, 20, "DescTratTrib", "Descri��o do tipo fiscal de cliente");
	adicDB(rgTipoFisc, 7, 185, 450, 53, "TipoFisc", "Situa��o do ICMS:",true);
	adicCampo(txtCodMens, 7, 260, 80, 20, "CodMens", "C�d.mens.", ListaCampos.DB_FK, txtMens, false);
	adicDescFK(txtMens, 90, 260, 360, 20, "Mens", "Mensagem");
    setListaCampos( true, "ITCLFISCAL", "LF");
    lcDet.setQueryInsert(false);


    montaTab();
    
    tab.setTamColuna(190,2);
    
  }
  public void afterCarrega(CarregaEvent cevt) {
	if (cevt.getListaCampos() == lcCampos) {}
  }
  public void exec(String sCodFisc) {
	txtCodFisc.setVlrString(sCodFisc);
	lcCampos.carregaDados();
  }
  public void setConexao(Connection cn) {
	super.setConexao(cn);
	lcMens.setConexao(cn);      
	lcTratTrib.setConexao(cn);      
	lcTipoFiscCli.setConexao(cn);      
  }
}
