/**
 * @version 29/10/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FItNatOper.java <BR>
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
 * Detalhe para natureza da opera��o...
 * 
 */

package org.freedom.modulos.std;
import java.sql.Connection;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FDetalhe;


public class FItNatoper extends FDetalhe {
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private JTextFieldPad txtCodNat = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtDescNat = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodItNatoper = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtUFTabICMS = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
  private JTextFieldFK txtAliqTabICMS = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,9,2);
  private ListaCampos lcTabICMS = new ListaCampos(this,"TI");
  public FItNatoper () {
    setTitulo("Cadastro de al�quotas por estado");
    setAtribos( 50, 50, 430, 300);

    txtCodNat.setAtivo(false);
    txtDescNat.setAtivo(false);

    setListaCampos(lcCampos);
    setAltCab(85);
    setPainel( pinCab, pnCliCab);
    adicCampo(txtCodNat, 7, 20, 80, 20,"CodNat","C�d.nat.op.",ListaCampos.DB_PK, true);
    adicCampo(txtDescNat, 90, 20, 220, 20,"DescNat","Descri��o da natureza da opera��o",ListaCampos.DB_SI, true);
    setListaCampos( true, "NATOPER", "LF");
    lcCampos.setReadOnly(true);    
    txtCodNat.setStrMascara("#.###");


    lcTabICMS.add(new GuardaCampo( txtUFTabICMS, "UFTI", "C�d.alq.", ListaCampos.DB_PK, true));
    lcTabICMS.add(new GuardaCampo( txtAliqTabICMS, "ALIQTI", "Al�quota", ListaCampos.DB_SI, false));
    lcTabICMS.montaSql(false, "TABICMS", "LF");
    lcTabICMS.setQueryCommit(false);
    lcTabICMS.setReadOnly(true);
    txtUFTabICMS.setTabelaExterna(lcTabICMS);


    setAltDet(60);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtCodItNatoper, 7, 20, 60, 20,"CodItNatoper","Item", ListaCampos.DB_PK, true);
    adicCampo(txtUFTabICMS, 70, 20, 77, 20, "UFTI", "UF", ListaCampos.DB_FK, txtAliqTabICMS, true);
    adicDescFK(txtAliqTabICMS, 150, 20, 150, 20, "ALIQTI", "Al�quota");
    setListaCampos( true, "ITNATOPER", "LF");
    lcDet.setQueryInsert(false);

    montaTab();
    
  }
  public void exec(String sCodFisc) {
	txtCodNat.setVlrString(sCodFisc);
	lcCampos.carregaDados();
  }
  public void setConexao(Connection cn) {
	lcTabICMS.setConexao(cn);      
	super.setConexao(cn);
  }
}
