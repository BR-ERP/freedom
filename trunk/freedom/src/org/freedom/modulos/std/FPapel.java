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
import org.freedom.telas.FDados;
import org.freedom.componentes.JTextFieldPad;
public class FPapel extends FDados {
  private JTextFieldPad txtCodPapel = new JTextFieldPad(20);
  private JTextFieldPad txtDescPapel = new JTextFieldPad(40);
  private JTextFieldPad txtLinPapel = new JTextFieldPad(8);
  private JTextFieldPad txtAltPapel = new JTextFieldPad(8);
  private JTextFieldPad txtLargPapel = new JTextFieldPad(8);
  private JTextFieldPad txtColPapel = new JTextFieldPad(8);
  private JTextFieldPad txtClassNotaPapel = new JTextFieldPad();
  public FPapel() {
//Remove o painel de impress�o da classe FDados:
    pnRodape.remove(2);
//Monta a tela:
    setTitulo("Cadastro de tipos de papeis");
    setAtribos(60,60,450,195);    
    
    adicCampo(txtCodPapel, 7, 20, 80, 20, "CodPapel", "C�digo", JTextFieldPad.TP_STRING, 20, 0, true, false, null, true);
    adicCampo(txtDescPapel, 90, 20, 200, 20, "DescPapel", "Descri��o", JTextFieldPad.TP_STRING, 40, 0, false, false, null, true);
    adicCampo(txtLinPapel, 7, 60, 95, 20, "LinPapel", "Num. Linhas", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null, true);
    adicCampo(txtColPapel, 105, 60, 87, 20, "Colpapel", "Num. Colunas", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null, true); // LOM
    adicCampo(txtAltPapel, 194, 60, 92, 20, "AltPapel", "Altura", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null, true);
    adicCampo(txtLargPapel, 289, 60, 87, 20, "LargPapel", "Largura", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null, true);
    adicCampo(txtClassNotaPapel, 7, 100, 120, 20, "ClassNotaPapel", "Arquivo de Layout de Nota Fiscal", JTextFieldPad.TP_STRING, 20, 0, false, false, null, false);
    
    setListaCampos(false,"PAPEL", "SG");
    lcCampos.setQueryInsert(false);    
  }
}

