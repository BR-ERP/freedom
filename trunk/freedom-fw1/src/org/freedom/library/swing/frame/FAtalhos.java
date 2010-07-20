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

package org.freedom.library.swing.frame;

import java.awt.BorderLayout;

import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.util.SwingParams;

public class FAtalhos extends FFDialogo {
	private static final long serialVersionUID = 1L;

	private JPanelPad pnAtalhos = new JPanelPad(JPanelPad.TP_JPANEL, new BorderLayout());

	public FAtalhos() {

		super(Aplicativo.telaPrincipal);
		setTitulo("Atalhos");
		setAtribos(380, 550);

		setToFrameLayout();

		c.add(pnAtalhos);

		JLabelPad lbAtalhos = new JLabelPad();
		StringBuilder atalhos = new StringBuilder();

		atalhos.append("<HTML><BODY>");
		atalhos.append("<UL>");
		atalhos.append("<LI><STRONG>CTRL + N</STRONG> - <EM>Novo Registro</EM>");
		atalhos.append("<LI><STRONG>CTRL + S</STRONG> - <EM>Gravar Altera��es</EM>");
		atalhos.append("<LI><STRONG>CTRL + D</STRONG> - <EM>Apagar Registro</EM>");
		atalhos.append("<LI><STRONG>CTRL + E</STRONG> - <EM>Editar</EM>");
		atalhos.append("<LI><STRONG>CTRL + W</STRONG> - <EM>Cancelar Altera��es</EM>");
		atalhos.append("<LI><STRONG>CTRL + P</STRONG> - <EM>Imprimir Registro</EM>");
		atalhos.append("<LI><STRONG>CTRL + R</STRONG> - <EM>Visualizar Impress�o</EM>");
		atalhos.append("<LI><STRONG>CTRL + O</STRONG> - <EM>Observa��es</EM>");
		atalhos.append("<LI><STRONG>CTRL + I</STRONG> - <EM>Imprimir</EM>");
		atalhos.append("<LI><STRONG>CTRL + P</STRONG> - <EM>Previs�o de Impress�o</EM>");
		atalhos.append("<LI><STRONG>TAB</STRONG> - <EM>Vai para o pr�ximo campo</EM>");
		atalhos.append("<LI><STRONG>SHIFT + TAB</STRONG> - <EM>Volta para o campo anterior</EM>");
		atalhos.append("<LI><STRONG>SHIFT + F4</STRONG> - <EM>Fecha a Tela</EM>");
		atalhos.append("<LI><STRONG>BARRA DE ESPA�OS</STRONG> - <EM>Aperta um bot�o</EM>");
		atalhos.append("<LI><STRONG>CTRL + PAGE UP</STRONG> - <EM>Vai para o Primeiro Registro</EM>");
		atalhos.append("<LI><STRONG>PAGE UP</STRONG> - <EM>Vai para o Registro Anterior</EM>");
		atalhos.append("<LI><STRONG>PAGE DOWN</STRONG> - <EM>Vai para o Pr�ximo Registro</EM>");
		atalhos.append("<LI><STRONG>CTRL + PAGE DOWN</STRONG> - <EM>vai para o �ltimo Registro</EM>");
		atalhos.append("<LI><STRONG>F1</STRONG> - <EM>Atalhos</EM>");
		atalhos.append("<LI><STRONG>F2</STRONG> - <EM>Procurar</EM>");
		atalhos.append("<LI><STRONG>F3</STRONG> - <EM>Procurar similar</EM>");
		atalhos.append("<LI><STRONG>F4</STRONG> - <EM>Completar o Or�amento</EM>");
		atalhos.append("<LI><STRONG>F4</STRONG> - <EM>Fechar a Compra</EM>");
		atalhos.append("<LI><STRONG>F4</STRONG> - <EM>Fechar a Venda</EM>");
		atalhos.append("<LI><STRONG>F5</STRONG> - <EM>Consulta pagamentos</EM>");
		atalhos.append("<LI><STRONG>F6</STRONG> - <EM>Abre tela de cadastro</EM>");
		atalhos.append("<LI><STRONG>ESC</STRONG> - <EM>Sair da Tela</EM>");
		atalhos.append("</UL>");
		atalhos.append("</BODY>");
		atalhos.append("</HTML>");

		lbAtalhos.setText(atalhos.toString());
		lbAtalhos.setFont(SwingParams.getFontbold());

		pnAtalhos.add(lbAtalhos);

	}
}
