/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JMenuItemPad.java <BR>
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

package org.freedom.library;
import java.awt.Cursor;

import javax.swing.JMenuItem;

import org.freedom.telas.IFilho;

public class JMenuItemPad extends JMenuItem {

	private static final long serialVersionUID = 1L;
	private int iCodSys = 0;
	private int iCodMod = 0;
    private int iCodIt = 0;
    private int iCodNiv = 0;
    private Class<? extends IFilho> tela = null;
    private String titulo = "";
  
	public String getTitulo() {
		return titulo;
	}
	public int getICodIt() {
		return iCodIt;
	}
	public int getICodMod() {
		return iCodMod;
	}
	public int getICodNiv() {
		return iCodNiv;
	}
	public int getICodSys() {
		return iCodSys;
	}
	public Class<? extends IFilho> getTela() {
		return tela;
	}
	/**
	*  Construtor da classe JMenuItem(). <BR>
	*
	*/
  
	public JMenuItemPad () {
		this(0,0,0,0, null, "");
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	public JMenuItemPad(String s, char c) {
		super(s,c);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	*  Construtor da classe JMenuItem(). <BR>
	*
	*/
  
	public JMenuItemPad (String menu) {
		super(menu);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	/**
	*  Construtor da classe JMenu(). <BR>
	*  Construtor que ja ajusta os paramatros basicos do JMenuPad.
	 * @param tela 
	 * @param titulo 
	*
	*/
  
	public JMenuItemPad (int iCodSistema, int iCodModulo, int iCodItem, int iCodNivel, Class<? extends IFilho> tela, String titulo) {
		iCodSys = iCodSistema;
		iCodMod = iCodModulo;
		iCodIt = iCodItem;
		iCodNiv = iCodNivel;
		this.tela = tela;
		this.titulo = titulo;
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	


	/**
	*  Ajusta o c�digo do sistema. <BR>
	*  @param iCod - C�digo do sistema.
	*  @see #getCodSistema
	*
	*/

	public void setCodSistema(int iCod) {
		iCodSys = iCod;
	}

	/**
	*  Retorna o c�digo do sistema. <BR>
	*  @return C�digo do sistema ou zero se o c�digo n�o foi definido.
	*  @see #setCodSistema
	*
	*/


	public int getCodSistema() {
		return iCodSys;
	}

	/**
	*  Ajusta o c�digo do m�dulo do sistema. <BR>
	*  @param iCod - C�digo do m�dulo.
	*  @see #getCodModulo
	*
	*/

	public void setCodModulo(int iCod) {
		iCodMod = iCod;
	}

	/**
	*  Retorna o c�digo do m�dulo do sistema. <BR>
	*  @return C�digo do m�dulo ou zero se o c�digo n�o foi definido.
	*  @see #setCodModulo
	*
	*/

	public int getCodModulo() {
		return iCodMod;
	}

	/**
	*  Ajusta o c�digo do item. <BR>
	*  O c�digo do item � composto da seguinte forma: <BR>
	*  8 casas decimais (caso casas da direita nao usadas colocar 0)<BR>
	*  e mais uma unidade para detalhamentos longos.
	*  agrupadas de dois em dois, ex: 19|26|03|17|8. <BR>
	*  <BR>
	*  No exemplo o c�digo do menu principal �: 19<BR>
	*  submenu1: 26<BR>
	*  submenu2: 03<BR>
	*  submenu3: 17<BR>
	*  item: 8<BR>
	*  
	*  @param iItem - C�digo do item.
	*  @see #getCodItem
	*
	*/

	public void setCodItem(int iCod) {
		iCodIt = iCod;
	}

	/**
	*  Retorna o c�digo do item. 
	*  @return C�digo do item ou zero se o c�digo n�o foi definido.
	*  @see #setCodItem
	*
	*/

	public int getCodItem() {
		return iCodIt;
	}

	/**
	*  Ajusta o n�vel do item. <BR>
	*  O n�vel especifica que n�vel de detalhe que o<BR>
	*  menu se encontra.
	* 
	*  @param iNivel - N�vel do menu pode ser: 1,2,3,4.
	* 
	*  @see #getCodNivel
	*
	*/

	public void setNivel(int iNivel) {
		iCodNiv = iNivel;
	}

	/**
	*  Retorna o n�vel do item. <BR>
	*  @return N�vel.
	*  @see #setNivel
	*
	*/

	public int getCodNivel() {
		return iCodNiv;
	}
}
