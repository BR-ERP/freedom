/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FAtendente.java <BR>
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
import org.freedom.telas.FDados;

public class FAtendente extends FDados { 
  private JTextFieldPad txtCodAtend = new JTextFieldPad(5);
  private JTextFieldPad txtNomeAtend = new JTextFieldPad(40);
  private JTextFieldPad txtRgAtend = new JTextFieldPad(12);
  private JTextFieldPad txtCpfAtend = new JTextFieldPad(11);
  private JTextFieldPad txtIdentificAtend = new JTextFieldPad(18);
  private JTextFieldPad txtEndAtend = new JTextFieldPad(50);
  private JTextFieldPad txtNumAtend = new JTextFieldPad(8);
  private JTextFieldPad txtBairAtend = new JTextFieldPad(30);
  private JTextFieldPad txtCidAtend = new JTextFieldPad(20);
  private JTextFieldPad txtCepAtend = new JTextFieldPad(8);
  private JTextFieldPad txtFoneAtend = new JTextFieldPad(12);
  private JTextFieldPad txtCelAtend = new JTextFieldPad(12);
  private JTextFieldPad txtFaxAtend = new JTextFieldPad(8);
  private JTextFieldPad txtUFAtend = new JTextFieldPad(2);
  private JTextFieldPad txtEmailAtend = new JTextFieldPad(50);
  private JTextFieldPad txtCodTipoAtend = new JTextFieldPad();
  private JTextFieldFK txtDescTipoAtend = new JTextFieldFK();
  private JTextFieldPad txtCodUsu = new JTextFieldPad();
  private JTextFieldFK txtDescUsu = new JTextFieldFK();
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcTipoAtend = new ListaCampos(this,"TA");
  private ListaCampos lcUsu = new ListaCampos(this,"US");
  private ListaCampos lcVend = new ListaCampos(this,"VE");
  public FAtendente () {
    setTitulo("Cadastro de Atendentes");
    setAtribos( 50, 20, 500, 440);

    lcTipoAtend.add(new GuardaCampo( txtCodTipoAtend, "CodTpAtend", "C�d.tp.atend.", ListaCampos.DB_PK, false),"txtCodVendx");
    lcTipoAtend.add(new GuardaCampo( txtDescTipoAtend, "DescTpAtend", "Descri�ao do tipo de atendente", ListaCampos.DB_SI,false),"txtCodVendx");
    lcTipoAtend.montaSql(false, "TIPOATEND", "AT");    
    lcTipoAtend.setQueryCommit(false);
    lcTipoAtend.setReadOnly(true);
    txtCodTipoAtend.setTabelaExterna(lcTipoAtend);
    
    lcVend.add(new GuardaCampo( txtCodVend, "CodVend", "C�d.comis.",ListaCampos.DB_PK, false));
    lcVend.add(new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado",ListaCampos.DB_SI, false));
    lcVend.montaSql(false, "VENDEDOR", "VD");    
    lcVend.setQueryCommit(false);
    lcVend.setReadOnly(true);
    txtCodVend.setTabelaExterna(lcVend);
    
    lcUsu.add(new GuardaCampo( txtCodUsu, "IdUsu", "ID", ListaCampos.DB_PK, false),"txtCodVendx");
	lcUsu.add(new GuardaCampo( txtDescUsu, "NomeUsu", "Nome do usu�rio", ListaCampos.DB_SI,false),"txtCodVendx");
	lcUsu.montaSql(false, "USUARIO", "SG");    
	lcUsu.setQueryCommit(false);
	lcUsu.setReadOnly(true);
	txtCodUsu.setTabelaExterna(lcUsu);

    adicCampo(txtCodAtend, 7, 20, 80, 20, "CodAtend", "C�d.atend.", JTextFieldPad.TP_INTEGER, 5, 0, true, false, null, true);
    adicCampo(txtNomeAtend, 90, 20, 370, 20, "NomeAtend", "Nome do atendente", JTextFieldPad.TP_STRING, 40, 0, false, false, null, true);
	adicCampo(txtCpfAtend, 7, 60, 150, 20, "CpfAtend", "CPF", JTextFieldPad.TP_STRING, 11, 0, false, false, null, false);
    adicCampo(txtIdentificAtend, 160, 60, 150, 20, "IdentificAtend", "Identifica��o", JTextFieldPad.TP_STRING, 18, 0, false, false, null, false);
    adicCampo(txtRgAtend, 313, 60, 149, 20, "RgAtend", "RG", JTextFieldPad.TP_STRING, 8, 0, false, false, null, false);
    adicCampo(txtEndAtend, 7, 100, 360, 20, "EndAtend", "Endere�o", JTextFieldPad.TP_STRING, 50, 0, false, false, null, false);
    adicCampo(txtNumAtend, 370, 100, 92, 20, "NumAtend", "N�mero", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null, false);
    adicCampo(txtBairAtend, 7, 140, 165, 20, "BairAtend", "Bairro", JTextFieldPad.TP_STRING, 30, 0, false, false, null, false);
    adicCampo(txtCidAtend, 175, 140, 165, 20, "CidAtend", "Cidade", JTextFieldPad.TP_STRING, 30, 0, false, false, null, false);
    adicCampo(txtCepAtend, 343, 140, 80, 20, "CepAtend", "Cep", JTextFieldPad.TP_STRING, 8, 0, false, false, null, false);
    adicCampo(txtUFAtend, 426, 140, 36, 20, "UFAtend", "UF", JTextFieldPad.TP_STRING, 2, 0, false, false, null, false);
    adicCampo(txtFoneAtend, 7, 180, 150, 20, "FoneAtend", "Telefone", JTextFieldPad.TP_STRING, 12, 0, false, false, null, false);
    adicCampo(txtFaxAtend, 160, 180, 150, 20, "FaxAtend", "Fax", JTextFieldPad.TP_STRING, 8, 0, false, false, null, false);
    adicCampo(txtCelAtend, 313, 180, 149, 20, "CelAtend", "Cel", JTextFieldPad.TP_STRING, 12, 0, false, false, null, false);
	adicCampo(txtCodTipoAtend, 7, 220, 100, 20, "CodTpAtend", "C�d.tp.atend.", JTextFieldPad.TP_STRING, 13, 0, false, true, txtDescTipoAtend,true);
	adicDescFK(txtDescTipoAtend, 110, 220, 352, 20, "DescTpAtend", "Descri��o do tipo de atendente", JTextFieldPad.TP_STRING, 50, 0);
	adicCampo(txtCodUsu, 7, 260, 100, 20, "IdUsu", "ID", JTextFieldPad.TP_STRING, 8, 0, false, true, txtDescUsu,false);
	adicDescFK(txtDescUsu, 110, 260, 352, 20, "NomeUsu", "Nome do usu�rio", JTextFieldPad.TP_STRING, 50, 0);
	adicCampo(txtCodVend, 7, 300, 100, 20, "CodVend", "C�d.comis.", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescVend,false);
	adicDescFK(txtDescVend, 110, 300, 352, 20, "NomeVend", "Nome do comissionado", JTextFieldPad.TP_STRING, 50, 0);
	adicCampo(txtEmailAtend, 7, 340, 340, 20, "EmailAtend", "E-Mail", JTextFieldPad.TP_STRING, 50, 0, false, false, null, false);
    txtRgAtend.setMascara(JTextFieldPad.MC_RG);
    txtCepAtend.setMascara(JTextFieldPad.MC_CEP);
    txtFoneAtend.setMascara(JTextFieldPad.MC_FONEDDD);
    txtCelAtend.setMascara(JTextFieldPad.MC_FONE);
    txtFaxAtend.setMascara(JTextFieldPad.MC_FONE);
    setListaCampos( true, "ATENDENTE", "AT");
    lcCampos.setQueryInsert(false);    
  }
  public void execShow(Connection cn) {
    lcTipoAtend.setConexao(cn);
    lcUsu.setConexao(cn);
    lcVend.setConexao(cn);
    super.execShow(cn);
  }
}
