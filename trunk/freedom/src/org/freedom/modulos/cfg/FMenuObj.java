/**
 * @version 22/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FMenuObj.java <BR>
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

package org.freedom.modulos.cfg;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FDetalhe;

public class FMenuObj extends FDetalhe {
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private JTextFieldPad txtCodMenu = new JTextFieldPad(JTextFieldPad.TP_INTEGER,9,0);
  private JTextFieldFK txtDescMenu = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodModu = new JTextFieldPad(JTextFieldPad.TP_INTEGER,9,0);
  private JTextFieldFK txtDescModu = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodSis = new JTextFieldPad(JTextFieldPad.TP_INTEGER,9,0);
  private JTextFieldFK txtDescSis = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtIDObj = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JTextFieldFK txtDescObj = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcMenu = new ListaCampos(this);
  private ListaCampos lcModu = new ListaCampos(this);
  private ListaCampos lcSis = new ListaCampos(this);
  private ListaCampos lcObj = new ListaCampos(this,"");
  public FMenuObj () {
    setTitulo("Cadastro de Menu x Objeto Sistema");
    setAtribos( 50, 50, 367, 360);
    
    lcCampos.setUsaME(false);
    lcDet.setUsaME(false);
    lcMenu.setUsaME(false);
    lcModu.setUsaME(false);
    lcSis.setUsaME(false);
    lcObj.setUsaFI(false);
        
    lcMenu.add(new GuardaCampo( txtCodMenu, 7, 100, 80, 20, "CODMENU", "C�digo", true, false, txtDescMenu, JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
    lcMenu.add(new GuardaCampo( txtDescMenu, 7, 100, 180, 20, "DESCMENU", "Descri�ao", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcMenu.montaSql(false, "MENU", "SG");    
    lcMenu.setQueryCommit(false);
    lcMenu.setReadOnly(true);
    txtCodMenu.setTabelaExterna(lcMenu);
    
    lcModu.add(new GuardaCampo( txtCodModu, 7, 100, 80, 20, "CODMODU", "C�digo", true, false, txtDescModu, JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
    lcModu.add(new GuardaCampo( txtDescModu, 7, 100, 180, 20, "DESCMODU", "Descri�ao", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcModu.montaSql(false, "MODULO", "SG");    
    lcModu.setQueryCommit(false);
    lcModu.setReadOnly(true);
    txtCodModu.setTabelaExterna(lcModu);
    
    lcSis.add(new GuardaCampo( txtCodSis, 7, 100, 80, 20, "CODSIS", "C�digo", true, false, txtDescSis, JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
    lcSis.add(new GuardaCampo( txtDescSis, 7, 100, 180, 20, "DESCSIS", "Descri�ao", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcSis.montaSql(false, "SISTEMA", "SG");    
    lcSis.setQueryCommit(false);
    lcSis.setReadOnly(true);
    txtCodSis.setTabelaExterna(lcSis);
    
    lcObj.add(new GuardaCampo( txtIDObj, "IDOBJ", "Id.obj.", ListaCampos.DB_PK,false));
    lcObj.add(new GuardaCampo( txtDescObj,"DESCOBJ", "Descri�ao", ListaCampos.DB_SI,false));
    lcObj.montaSql(false, "OBJETO", "SG");    
    lcObj.setQueryCommit(false);
    lcObj.setReadOnly(true);
    txtIDObj.setTabelaExterna(lcObj);
    
    pinCab = new Painel(350,165);
    setAltCab(165);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    adicCampo(txtCodSis, 7, 20, 100, 20,"CODSIS","C�d.Sis.",JTextFieldPad.TP_INTEGER,5,0,true,true,null,true);
    adicDescFK(txtDescSis, 110, 20, 197, 20, "DESCSIS", "e descri��o do sistema", JTextFieldPad.TP_STRING, 50, 0);
	adicCampo(txtCodModu, 7, 60, 100, 20,"CODMODU","C�d.Modu",JTextFieldPad.TP_INTEGER,5,0,true,true,null,true);
	adicDescFK(txtDescModu, 110, 60, 197, 20, "DESCMODU", "e descri��o do modulo", JTextFieldPad.TP_STRING, 50, 0);
	adicCampo(txtCodMenu, 7, 100, 100, 20,"CODMENU","C�d.Menu",JTextFieldPad.TP_INTEGER,9,0,true,true,null,true);
	adicDescFK(txtDescMenu, 110, 100, 197, 20, "DESCMENU", "e descri��o do menu", JTextFieldPad.TP_STRING, 50, 0);
	lcCampos.setReadOnly(true);
    setListaCampos( false, "MENU", "SG");

    setAltDet(60);
    pinDet = new Painel(350,90);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtIDObj, 7, 20, 60, 20,"IDOBJ","Id.obj",ListaCampos.DB_PK,true);
    adicDescFK(txtDescObj, 70, 20, 200, 20, "DESCOBJ", "Descri��o do objeto", JTextFieldPad.TP_STRING, 50, 0);
    setListaCampos( false, "MENUOBJ", "SG");
    montaTab();
    
    tab.setTamColuna(200,1);
  }
  public void execShow(Connection cn) {
    lcMenu.setConexao(cn);
    lcModu.setConexao(cn);
    lcSis.setConexao(cn);
    lcObj.setConexao(cn);
    super.execShow(cn);
  }
}