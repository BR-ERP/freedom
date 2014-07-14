/**
 * @version 29/10/2013 <BR>
 * @author Setpoint Tecnologia em Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.library.swing.frame <BR>
 * Classe: @(#)FListHistorico.java <BR>
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
 * Classe para listagem de logs 
 */
package org.freedom.library.swing.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.freedom.library.swing.component.Historico;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.frame.FFilho;

public class FListHistorico extends FFilho implements MouseListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelListMensagens = new JPanelPad(new BorderLayout());

	private JPanelPad panelListHistorico = new JPanelPad(new BorderLayout());

	private JPanelPad panelHistorico = new JPanelPad(new BorderLayout());

	private JPanelPad panelMensagens = new JPanelPad(new BorderLayout());

	private JLabelPad grupo = new JLabelPad(
			" -- Inconsist�ncias -- ");

	private JTablePad tabHistorico = new JTablePad();

	private JTablePad tabMensagens = new JTablePad();

	private JPanelPad panelgrids = new JPanelPad(new GridLayout(3, 1));

	private JPanelPad panelTextos = new JPanelPad(new GridLayout(2, 1));

	private JTextAreaPad descricao = new JTextAreaPad();

	private JTextAreaPad acaoCorretiva = new JTextAreaPad();

	private enum enum_grid_historico {
		DATA, TIPO, HISTORICO, HISTORICO_ID
	}

	private enum enum_grid_mensagens {
		CODIGO, MENSAGEM, ACAO
	}

	private Vector<Historico> list;

	public FListHistorico(Vector<Historico> list) {
		super(true);
		this.list = list;
		setTitulo("", this.getClass().getName());
		setSize(700, 500);

		c.setLayout(new BorderLayout());

		c.add(panelgrids, BorderLayout.CENTER);
		c.add(adicBotaoSair(), BorderLayout.SOUTH);

		panelgrids.add(panelHistorico);
		panelgrids.add(panelMensagens);
		panelgrids.add(panelTextos);

		panelHistorico.add(panelListHistorico, BorderLayout.CENTER);

		panelMensagens.add(panelListMensagens, BorderLayout.CENTER);

		grupo.setPreferredSize(new Dimension(300, 30));
		grupo.setForeground(Color.BLUE);
		panelListMensagens.add(grupo, BorderLayout.NORTH);

		tabHistorico.adicColuna("Data");
		tabHistorico.adicColuna("Tipo");
		tabHistorico.adicColuna("Historico");
		tabHistorico.adicColuna("ID");

		tabHistorico.setTamColuna(70, enum_grid_historico.DATA.ordinal());
		tabHistorico.setTamColuna(70, enum_grid_historico.TIPO.ordinal());
		tabHistorico.setTamColuna(530, enum_grid_historico.HISTORICO.ordinal());
		tabHistorico.setColunaInvisivel(enum_grid_historico.HISTORICO_ID
				.ordinal());

		tabMensagens.adicColuna("C�digo");
		tabMensagens.adicColuna("inconsist�ncia / mensagem");
		tabMensagens.adicColuna("Acao");

		tabMensagens.setTamColuna(50, enum_grid_mensagens.CODIGO.ordinal());
		tabMensagens.setTamColuna(620, enum_grid_mensagens.MENSAGEM.ordinal());
		tabMensagens.setColunaInvisivel(enum_grid_mensagens.ACAO.ordinal());

		panelListMensagens.add(new JScrollPane(tabMensagens),
				BorderLayout.CENTER);

		panelListHistorico.add(new JScrollPane(tabHistorico),
				BorderLayout.CENTER);

		descricao.setAtivo(false);
		descricao.setBorder(BorderFactory.createTitledBorder("Descri��o : "));
		panelTextos.add(descricao);

		acaoCorretiva.setAtivo(false);
		acaoCorretiva.setBorder(BorderFactory
				.createTitledBorder("A��o corretiva / Observa��es : "));
		panelTextos.add(acaoCorretiva);

		panelTextos.setPreferredSize(new Dimension(700, 200));

		tabMensagens.addMouseListener(this);
		tabHistorico.addMouseListener(this);

		// ns = ( (AplicationSPED)Aplicativo.getInstance()
		// ).getNota_eletronica();

		listHistorico();
	}

	private void listHistorico() {

		int lin = 0;
		tabHistorico.limpa();

		for (Historico h : list) {

			tabHistorico.adicLinha();

			tabHistorico.setValor(h.getDataOperacao(), lin,
					enum_grid_historico.DATA.ordinal());
			tabHistorico.setValor(h.getTipoOperacao(), lin,
					enum_grid_historico.TIPO.ordinal());
			tabHistorico.setValor(h.getHistorico(), lin,
					enum_grid_historico.HISTORICO.ordinal());
			tabHistorico.setValor(h.getId(), lin,
					enum_grid_historico.HISTORICO_ID.ordinal());

			lin++;

		}

	}

	private void selecao() {

		descricao.setVlrString((String) tabMensagens.getValor(
				tabMensagens.getSelectedRow(),
				enum_grid_mensagens.MENSAGEM.ordinal()));
		acaoCorretiva.setVlrString((String) tabMensagens.getValor(
				tabMensagens.getSelectedRow(),
				enum_grid_mensagens.ACAO.ordinal()));
	}

	private void selecaoHistorico() {

		int lin = 0;

		tabMensagens.limpa();

		int lin_sel = tabHistorico.getLinhaSel();
		// int id = (Integer) tabHistorico.getValor(lin_sel,
		// enum_grid_historico.HISTORICO_ID.ordinal());
		String codigo = "";
		String historico = (String) tabHistorico.getValor(lin_sel,
				enum_grid_historico.HISTORICO.ordinal());
		String acao = "";

		tabMensagens.adicLinha();

		tabMensagens
				.setValor(codigo, lin, enum_grid_mensagens.CODIGO.ordinal());
		tabMensagens.setValor(historico, lin,
				enum_grid_mensagens.MENSAGEM.ordinal());
		tabMensagens.setValor(acao, lin, enum_grid_mensagens.ACAO.ordinal());
	}

	public void mouseClicked(MouseEvent e) {

		if (tabMensagens.equals(e.getSource())
				&& tabMensagens.getLinhaSel() >= 0) {
			selecao();
		}

		if (tabHistorico.equals(e.getSource())
				&& tabHistorico.getLinhaSel() >= 0) {
			selecaoHistorico();
		}
	}

	public void mouseEntered(MouseEvent e) {

		if (tabMensagens.equals(e.getSource())
				&& tabMensagens.getLinhaSel() >= 0) {
			selecao();
		}
	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}
}
