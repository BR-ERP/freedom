/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FModGrade.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.JPanelPad;
import org.freedom.library.JTextFieldFK;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;

import org.freedom.telas.FDetalhe;
public class FModGrade extends FDetalhe {
	private static final long serialVersionUID = 1L;

  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodModG = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescModG = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescProdModG = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldPad txtRefModG = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtCodFabModG = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtCodBarModG = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtCodItModG = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescItModG = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldPad txtCodVarG = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtRefItModG = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldPad txtCodFabItModG = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtCodBarItModG = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldFK txtDescVarG = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  private ListaCampos lcVarG = new ListaCampos(this,"VG");
  public FModGrade() {
    setTitulo("Cadastro de Modelos da Grade");
    setAtribos( 50, 20, 620, 400);
    setAltCab(120);
    pinCab = new JPanelPad(590,110);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    
    lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, true));
    lcProd.add(new GuardaCampo( txtRefProd, "RefProd", "Refer�ncia do produto", ListaCampos.DB_SI, false));
    lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false));
    lcProd.setWhereAdic("ATIVOPROD='S'");
    lcProd.montaSql(false, "PRODUTO", "EQ");
    lcProd.setQueryCommit(false);
    lcProd.setReadOnly(true);
    txtCodProd.setTabelaExterna(lcProd);
    
    adicCampo(txtCodModG, 7, 20, 70, 20,"CodModG","C�d.mod.g.", ListaCampos.DB_PK, true);
    adicCampo(txtDescModG, 80, 20, 197, 20,"DescModG","Descri��o do modelo de grade", ListaCampos.DB_SI, true);
    adicCampo(txtCodProd, 280, 20, 77, 20,"CodProd","C�d.prod.", ListaCampos.DB_FK, true);
    adicDescFK(txtDescProd, 360, 20, 200, 20, "DescProd", "Descri��o do produto");
    adicCampo(txtDescProdModG, 7, 60, 270, 20,"DescProdModG","Descri��o inicial", ListaCampos.DB_SI, true);
    adicCampo(txtRefModG, 280, 60, 87, 20,"RefModG","Ref.inic.", ListaCampos.DB_SI, true);
    adicCampo(txtCodFabModG, 370, 60, 87, 20,"CodFabModG","C�d.fab.inic.", ListaCampos.DB_SI, true);
    adicCampo(txtCodBarModG, 460, 60, 100, 20,"CodBarModG","C�d.bar.inic.", ListaCampos.DB_SI, true);
    setListaCampos( true, "MODGRADE", "EQ");
    setAltDet(120);
    pinDet = new JPanelPad(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);

    lcVarG.add(new GuardaCampo( txtCodVarG, "CodVarG", "C�d.var.g.", ListaCampos.DB_PK, true));
    lcVarG.add(new GuardaCampo( txtDescVarG, "DescVarG", "Descri��o da variante", ListaCampos.DB_SI, false));
    lcVarG.montaSql(false, "VARGRADE", "EQ");
    lcVarG.setQueryCommit(false);
    lcVarG.setReadOnly(true);
    txtCodVarG.setTabelaExterna(lcVarG);
    
    adicCampo(txtCodItModG, 7, 20, 70, 20,"CodItModG","Item", ListaCampos.DB_PK, true);
    adicCampo(txtCodVarG, 80, 20, 77, 20,"CodVarG","C�d.var.g.", ListaCampos.DB_FK, true);
    adicDescFK(txtDescVarG, 160, 20, 197, 20, "DescVarG", "Descri��o da variante");
    adicCampo(txtDescItModG, 360, 20, 200, 20,"DescItModG","Descri��o", ListaCampos.DB_SI, true);
    adicCampo(txtRefItModG, 7, 60, 87, 20,"RefItModG","Ref.inicial", ListaCampos.DB_SI, true);
    adicCampo(txtCodFabItModG, 100, 60, 87, 20,"CodFabItModG","C�d.fab.inic.", ListaCampos.DB_SI, true);
    adicCampo(txtCodBarItModG, 190, 60, 100, 20,"CodBarItModG","C�d.bar.inic.", ListaCampos.DB_SI, true);
    setListaCampos( true, "ITMODGRADE", "EQ");
    montaTab();
  }
  public void setConexao(DbConnection cn) {
    super.setConexao(cn);
    lcProd.setConexao(cn);
    lcVarG.setConexao(cn);
    txtCodProd.setBuscaAdic(new DLBuscaProd(con,"CODPROD",lcProd.getWhereAdic()));
  }        
}
