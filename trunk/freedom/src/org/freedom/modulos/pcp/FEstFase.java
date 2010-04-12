/**
 * @version 23/04/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FEstFase.java <BR>
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

package org.freedom.modulos.pcp;
import org.freedom.infra.model.jdbc.DbConnection;

import javax.swing.JScrollPane;

import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.std.view.dialog.comum.DLBuscaProd;

public class FEstFase extends FDetalhe {
	private static final long serialVersionUID = 1L;
	
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtDescEst = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtQtdEst = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
  private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFase = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtNumSeqEf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodTpRec = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescTpRec = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtTempoEf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtSeqEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  private ListaCampos lcFase = new ListaCampos(this,"FS");
  private ListaCampos lcTipoRec = new ListaCampos(this,"TR");
  private JCheckBoxPad cbFinaliza = new JCheckBoxPad("Sim","S","N");
  private JTextAreaPad txaModoPreparo = new JTextAreaPad();
  private JScrollPane spnModoPreparo = new JScrollPane(txaModoPreparo);
  private int iCodProd;
  private int iSeqEst;
  
  public FEstFase() {
  	this(0,0);
  }
  
  public FEstFase(int iCodProd,int iSeqEst) {
    setTitulo("Fases da estrutura");
    setAtribos( 70, 40, 550, 550);
    setAltCab(130);
    
    this.iCodProd = iCodProd;
    this.iSeqEst = iSeqEst;
    
    txtCodProd.setAtivo(false);
    txtDescEst.setAtivo(false);
    txtQtdEst.setAtivo(false);
    txtSeqEst.setAtivo(false);
    
    pinCab = new JPanelPad(500,90);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    
    lcProd.setUsaME(false);
    lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, true));
    lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false));
    lcProd.setWhereAdic("TIPOPROD='F'");
    lcProd.montaSql(false, "PRODUTO", "EQ");
    lcProd.setQueryCommit(false);
    lcProd.setReadOnly(true);
    txtCodProd.setTabelaExterna(lcProd);
    
    adicCampo(txtCodProd, 7, 20, 80, 20,"CodProd","C�d.prod.", ListaCampos.DB_PF, txtDescProd, true);
    adicDescFK(txtDescProd, 90, 20, 247, 20, "DescProd", "Descri��o do produto");
    adicCampo(txtSeqEst, 340, 20, 80, 20,"SeqEst","Seq.Est.", ListaCampos.DB_PK, true);
    adicCampo(txtQtdEst, 423, 20, 80, 20,"QtdEst","Quantidade", ListaCampos.DB_SI, true);
    adicCampo(txtDescEst, 7, 60, 250, 20,"DescEst","Descri��o da estrutura", ListaCampos.DB_SI,true);
    setListaCampos( false, "ESTRUTURA", "PP");
    lcCampos.setQueryInsert(false);
        
    lcFase.add(new GuardaCampo( txtCodFase,"CodFase", "C�d.fase", ListaCampos.DB_PK, true));
    lcFase.add(new GuardaCampo( txtDescFase, "DescFase", "Descri��o da fase", ListaCampos.DB_SI, false));
    lcFase.montaSql(false, "FASE", "PP");
    lcFase.setQueryCommit(false);
    lcFase.setReadOnly(true);
    txtCodFase.setTabelaExterna(lcFase);
    
    lcTipoRec.add(new GuardaCampo(txtCodTpRec,"CodTpRec", "C�d.tp.rec.", ListaCampos.DB_PK, true));
    lcTipoRec.add(new GuardaCampo(txtDescTpRec, "DescTpRec", "Descri��o do tipo de recurso", ListaCampos.DB_SI, false));
    lcTipoRec.montaSql(false, "TIPOREC", "PP");
    lcTipoRec.setQueryCommit(false);
    lcTipoRec.setReadOnly(true);
    txtCodTpRec.setTabelaExterna(lcTipoRec);

    setAltDet(220);
    pinDet = new JPanelPad(590,110);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtNumSeqEf, 7, 20, 40, 20,"SeqEf","Item", ListaCampos.DB_PK, true);
    adicCampo(txtCodFase, 50, 20, 77, 20,"CodFase","C�d.fase", ListaCampos.DB_FK, txtDescFase, true);
    adicDescFK(txtDescFase, 130, 20, 277, 20, "DescFase", "Descri��o da fase");
    adicCampo(txtTempoEf, 410, 20, 100, 20,"TempoEf","Tempo (Seg.)",ListaCampos.DB_SI,true);
    adicCampo(txtCodTpRec, 7, 60, 80, 20,"CodTpRec","C�d.tp.rec.", ListaCampos.DB_FK, txtDescTpRec, true);
    adicDescFK(txtDescTpRec, 90, 60, 350, 20, "DescTpRec", "Descri��o do tipo de recurso");
    adicDB(cbFinaliza,445,60,80,20,"FINALIZAOP","Finaliza O.P",true);

    adicDBLiv(txaModoPreparo, "Instrucoes", "Instru��es", false);
	adic(new JLabelPad("Instru��es"), 7, 80, 100, 20);
	adic(spnModoPreparo, 7, 100, 510, 100);
    
    setListaCampos( true, "ESTRUFASE", "PP");
    lcDet.setQueryInsert(false);
    montaTab();
    
    lcCampos.setReadOnly(true);

    tab.setTamColuna(50,0);
    tab.setTamColuna(150,2);
    tab.setTamColuna(120,3);
    tab.setTamColuna(170,5);
    
    
  }

  public void setConexao(DbConnection cn) {
    super.setConexao(cn);
    lcProd.setConexao(cn);
    lcFase.setConexao(cn);
    lcTipoRec.setConexao(cn);
    txtCodProd.setVlrInteger(new Integer(iCodProd));
    txtSeqEst.setVlrInteger(new Integer(iSeqEst));
    lcCampos.carregaDados();
    txtCodProd.setBuscaAdic(new DLBuscaProd(con,"CODPROD",lcProd.getWhereAdic()));
   }        
}
