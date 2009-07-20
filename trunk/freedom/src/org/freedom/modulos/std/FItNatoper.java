/**
 * @version 29/10/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FItNatOper.java <BR>
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
 * Detalhe para natureza da opera��o...
 * 
 */

package org.freedom.modulos.std;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FDetalhe;


public class FItNatoper extends FDetalhe {

	private static final long serialVersionUID = 1L;

  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodNat = new JTextFieldPad(JTextFieldPad.TP_STRING,5,0);
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
  public void setConexao(DbConnection cn) {
	lcTabICMS.setConexao(cn);      
	super.setConexao(cn);
  }
}
