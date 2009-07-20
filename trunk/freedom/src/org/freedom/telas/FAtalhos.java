/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FLogin.java <BR>
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
 * Coment�rios para a classe...
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;

public class FAtalhos extends FFDialogo {
	private static final long serialVersionUID = 1L;

  private JPanelPad pnEquipe = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  public FAtalhos () {
  	super(Aplicativo.telaPrincipal);
    setTitulo("Atalhos");
	setAtribos(380,580);
	
	setToFrameLayout();

	c.add(pnEquipe);
	pnEquipe.add(new JLabelPad ("<HTML><BODY>" +
			"<UL>"+
        "<LI><STRONG>CTRL + N</STRONG> - <EM>Novo Registro</EM>"+
        "<LI><STRONG>CTRL + S</STRONG> - <EM>Gravar Altera��es</EM>"+
        "<LI><STRONG>CTRL + D</STRONG> - <EM>Apagar Registro</EM>"+
        "<LI><STRONG>CTRL + E</STRONG> - <EM>Editar</EM>"+
        "<LI><STRONG>CTRL + W</STRONG> - <EM>Cancelar Altera��es</EM>"+
        "<LI><STRONG>CTRL + P</STRONG> - <EM>Imprimir Registro</EM>"+
        "<LI><STRONG>CTRL + R</STRONG> - <EM>Visualizar Impress�o</EM>"+
        "<LI><STRONG>CTRL + O</STRONG> - <EM>Observa��es</EM>" +
        "<LI><STRONG>CTRL + I</STRONG> - <EM>Imprimir</EM>" +
        "<LI><STRONG>CTRL + P</STRONG> - <EM>Previs�o de Impress�o</EM>" +
        "<LI><STRONG>TAB</STRONG> - <EM>Vai para o pr�ximo campo</EM>"+
        "<LI><STRONG>SHIFT + TAB</STRONG> - <EM>Volta para o campo anterior</EM>"+
        "<LI><STRONG>SHIFT + F4</STRONG> - <EM>Fecha a Tela</EM>"+
        "<LI><STRONG>BARRA DE ESPA�OS</STRONG> - <EM>Aperta um bot�o</EM>"+
        "<LI><STRONG>CTRL + PAGE UP</STRONG> - <EM>Vai para o Primeiro Registro</EM>" +
        "<LI><STRONG>PAGE UP</STRONG> - <EM>Vai para o Registro Anterior</EM>" +
        "<LI><STRONG>PAGE DOWN</STRONG> - <EM>Vai para o Pr�ximo Registro</EM>" +
        "<LI><STRONG>CTRL + PAGE DOWN</STRONG> - <EM>vai para o �ltimo Registro</EM>" +
        "<LI><STRONG>F1</STRONG> - <EM>Atalhos</EM>" +
        "<LI><STRONG>F2</STRONG> - <EM>Procurar</EM>" +
        "<LI><STRONG>F3</STRONG> - <EM>Procurar similar</EM>" +
        "<LI><STRONG>F4</STRONG> - <EM>Completar o Or�amento</EM>" +
        "<LI><STRONG>F4</STRONG> - <EM>Fechar a Compra</EM>" +
		"<LI><STRONG>F4</STRONG> - <EM>Fechar a Venda</EM>" +
		"<LI><STRONG>F5</STRONG> - <EM>Consulta pagamentos</EM>" +
        "<LI><STRONG>ESC</STRONG> - <EM>Sair da Tela</EM>" +
        "</UL>" +
        "</BODY>" +
        "</HTML>"));
  }
}    
