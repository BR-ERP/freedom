/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FConta.java <BR>
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
import java.sql.Connection;
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;
public class FConta extends FDados {
  private JTextFieldPad txtAgConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0);
  private JTextFieldPad txtNumConta = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtDescConta = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtCodBanco = new JTextFieldPad(JTextFieldPad.TP_STRING,3,0);
  private JTextFieldPad txtDataConta = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
  private JTextFieldPad txtCodMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtCodPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldFK txtDescBanco = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldFK txtDescMoeda = new JTextFieldFK(JTextFieldPad.TP_STRING, 50,0);
  private JTextFieldFK txtDescPlan = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private Vector vValsTipo = new Vector();
  private Vector vLabsTipo = new Vector();
  private JRadioGroup rgTipo = null;
  private ListaCampos lcBanco = new ListaCampos(this,"BO");
  private ListaCampos lcMoeda = new ListaCampos(this,"MA");
  private ListaCampos lcPlan = new ListaCampos(this,"PN");
  public FConta() {
    setTitulo("Cadastro de Contas");
    setAtribos(50,50,415,290);

    lcBanco.add(new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, false));
    lcBanco.add(new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false));
    lcBanco.montaSql(false, "BANCO", "FN");    
    lcBanco.setQueryCommit(false);
    lcBanco.setReadOnly(true);
    txtCodBanco.setTabelaExterna(lcBanco);

    lcMoeda.add(new GuardaCampo( txtCodMoeda, "CodMoeda", "C�d.mda.",ListaCampos.DB_PK ,true));
    lcMoeda.add(new GuardaCampo( txtDescMoeda, "SingMoeda", "Descri��o da moeda", ListaCampos.DB_SI, false));
    lcMoeda.montaSql(false, "MOEDA", "FN");    
    lcMoeda.setQueryCommit(false);
    lcMoeda.setReadOnly(true);
    txtCodMoeda.setTabelaExterna(lcMoeda);
    
    lcPlan.add(new GuardaCampo( txtCodPlan, "CodPlan", "C�d.tp.lan�.", ListaCampos.DB_PK, true));
    lcPlan.add(new GuardaCampo( txtDescPlan, "DescPlan", "Descri��o do tipo de lan�amento", ListaCampos.DB_SI, false));
    lcPlan.montaSql(false, "PLANEJAMENTO", "FN");    
    lcPlan.setQueryCommit(false);
    lcPlan.setReadOnly(true);
    txtCodPlan.setTabelaExterna(lcPlan);
    
    vValsTipo.addElement("B");
    vValsTipo.addElement("C");
    vLabsTipo.addElement("Bancos");
    vLabsTipo.addElement("Caixa");
    rgTipo = new JRadioGroup(1,2,vLabsTipo,vValsTipo);

    adicCampo(txtNumConta, 7, 20, 110, 20, "NumConta", "N� da conta", ListaCampos.DB_PK, true);
    adicCampo(txtDescConta, 120, 20, 270, 20, "DescConta", "Descri��o da conta", ListaCampos.DB_SI, true);
    adicCampo(txtAgConta, 7, 60, 60, 20, "AgenciaConta", "Ag�ncia", ListaCampos.DB_SI, false);
    adicCampo(txtCodBanco, 70, 60, 80, 20, "CodBanco", "C�d.banco", ListaCampos.DB_FK, false);
    adicDescFK(txtDescBanco, 153, 60, 237, 20, "NomeBanco", "Descri��o do banco");
    adicCampo(txtDataConta, 7, 100, 80, 20, "DataConta", "Data", ListaCampos.DB_SI, true);
    adicCampo(txtCodMoeda, 90, 100, 60, 20, "CodMoeda", "C�d.mda.", ListaCampos.DB_FK, true);
    adicDescFK(txtDescMoeda, 153, 100, 237, 20, "SingMoeda", "Descri��o da moeda");
    adicCampo(txtCodPlan, 7, 140, 140, 20, "CodPlan", "C�d.tp.lan�.", ListaCampos.DB_FK, true);
    adicDescFK(txtDescPlan, 150, 140, 240, 20, "DescPlan", "Descri��o do tipo de lan�amento");
    adicDB(rgTipo, 7, 180, 383, 30, "TipoConta", "Tipo", true);
    setListaCampos(false,"CONTA", "FN");
    lcCampos.setQueryInsert(false);    
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcBanco.setConexao(cn);
    lcMoeda.setConexao(cn);
    lcPlan.setConexao(cn);
  }
}

