/**
 * @version 12/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)Arvore.java <BR>
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
