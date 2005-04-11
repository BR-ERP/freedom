/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FLogin.java <BR>
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
 * Coment�rios para a classe...
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;

public class FAtalhos extends FFDialogo {
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
        "<LI><STRONG>TAB</STRONG> - <EM>Vai pata o pr�ximo campo</EM>"+
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
