/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FPapel.java <BR>
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

package org.freedom.modulos.std;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FPapel extends FDados {
  private JTextFieldPad txtCodPapel = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtDescPapel = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtLinPapel = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtAltPapel = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtLargPapel = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtColPapel = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtClassNotaPapel = new JTextFieldPad();
  public FPapel() {
//Remove o painel de impress�o da classe FDados:
    pnRodape.remove(2);
//Monta a tela:
    setTitulo("Cadastro de tipos de papeis");
    setAtribos(60,60,400,195);    
    
    adicCampo(txtCodPapel, 7, 20, 80, 20, "CodPapel", "C�d.papel", ListaCampos.DB_PK, true);
    adicCampo(txtDescPapel, 90, 20, 287, 20, "DescPapel", "Descri��o do papel",ListaCampos.DB_SI, true);
    adicCampo(txtLinPapel, 7, 60, 95, 20, "LinPapel", "Num. linhas", ListaCampos.DB_SI, true);
    adicCampo(txtColPapel, 105, 60, 87, 20, "Colpapel", "Num. colunas", ListaCampos.DB_SI, true); // LOM
    adicCampo(txtAltPapel, 194, 60, 92, 20, "AltPapel", "Altura", ListaCampos.DB_SI, true);
    adicCampo(txtLargPapel, 289, 60, 87, 20, "LargPapel", "Largura", ListaCampos.DB_SI, true);
    adicCampo(txtClassNotaPapel, 7, 100, 370, 20, "ClassNotaPapel", "Classe de layout de NF", ListaCampos.DB_SI, false);
    
    setListaCampos(false,"PAPEL", "SG");
    lcCampos.setQueryInsert(false);    
  }
}

