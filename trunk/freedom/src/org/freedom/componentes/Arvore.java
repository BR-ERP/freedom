/**
 * @version 12/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)Arvore.java <BR>
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
 * Coment�rios da classe.....
 */
 
package org.freedom.componentes;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.freedom.acao.ArvoreFace;
public class Arvore extends JTree implements ArvoreFace {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  private ArvoreFace face = this;
  public Arvore() {
  	setCellRenderer(new NohImg(this));
  }
  public void setImgTrat(ArvoreFace imgT) {
    face = imgT;
  }
  public ImageIcon getImagem(int iLinha, boolean bNoh, Object src) {
  	return null;
  }
  public ImageIcon getImg(int iLinha, boolean bNoh, Object src) {
  	return face.getImagem(iLinha,bNoh,src);
  }
}
class NohImg extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Arvore pai = null;
    public NohImg(Arvore p) {
      pai = p;
    }

    public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {
      ImageIcon img = pai.getImg(row,leaf,value);
      super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);
      if (img != null)
        setIcon(img);
      return this;
    }

}
