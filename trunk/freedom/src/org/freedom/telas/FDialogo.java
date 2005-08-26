/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FDialogo.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para
 * Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios da classe.....
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.Border;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;

public class FDialogo extends JDialog implements ActionListener, KeyListener,
		WindowListener {
	private static final long serialVersionUID = 1L;

	public JButton btCancel = new JButton("Cancelar", Icone
			.novo("btCancelar.gif"));

	public JButton btOK = new JButton("OK", Icone.novo("btOk.gif"));

	public JPanelPad pnRodape = new JPanelPad(JPanelPad.TP_JPANEL,
			new BorderLayout());

	private JPanelPad pnGrid = new JPanelPad(JPanelPad.TP_JPANEL,
			new GridLayout(1, 2));

	private JPanelPad pnBordRodape = new JPanelPad(JPanelPad.TP_JPANEL,
			new GridLayout(1, 1));

	private JPanelPad pin = new JPanelPad();

	public Container c = getContentPane();

	private Border br = BorderFactory.createEtchedBorder();

	boolean setArea = true;

	boolean bUltimo = false;

	private boolean initFirstFocus = true;

	private Component firstFocus = null;

	public boolean OK = false;

	public Connection con = null;

	public FDialogo() {
		super(Aplicativo.telaPrincipal, "Dialogo", true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		setTitle("Dialogo");
		setModal(true);
		setSize(250, 100);
		setLocationRelativeTo(this);

		c.setLayout(new BorderLayout());

		pnGrid.setPreferredSize(new Dimension(220, 30));
		pnGrid.add(btOK);
		pnGrid.add(btCancel);

		pnRodape.add(pnGrid, BorderLayout.EAST);

		pnBordRodape.setPreferredSize(new Dimension(250, 30));
		pnBordRodape.setBorder(br);
		pnBordRodape.add(pnRodape);

		c.add(pnBordRodape, BorderLayout.SOUTH);

		btCancel.addActionListener(this);
		btOK.addActionListener(this);
		addKeyListener(this);

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	public void setConexao(Connection cn) {
		con = cn;
	}

	public void setPainel(JPanelPad p) {
		pin = p;
		setArea = false;
	}

	public void setPanel(JPanelPad p) {
		c.add(p, BorderLayout.CENTER);
		setArea = false;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			OK = true;
			setVisible(false);
		} else if (evt.getSource() == btCancel) {
			OK = false;
			setVisible(false);
		}
	}

	public void setTitulo(String tit) {
		setTitle(tit);
	}

	public void setAtribos(int X, int Y, int Larg, int Alt) {
		setBounds(X, Y, Larg, Alt);
	}

	public void setAtribos(int Larg, int Alt) {
		setSize(Larg, Alt);
		setLocationRelativeTo(this);
	}
    public void setPrimeiroFoco(final JComponent comp) {
    	addWindowListener(
	    	new WindowAdapter() {
	    		public void windowActivated(WindowEvent wevt) {
	    			comp.requestFocusInWindow();
	    		}
	    	}
   		);
    }
	public void eUltimo() {
		bUltimo = true;
	}

	public void setToFrameLayout() {
		pnRodape.remove(0);
		pnGrid = new JPanelPad(JPanelPad.TP_JPANEL, new GridLayout(1, 1));
		pnGrid.setPreferredSize(new Dimension(100, 30));
		JButton btSair = new JButton("Sair", Icone.novo("btSair.gif"));
		btSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		});
		pnGrid.add(btSair);
		pnRodape.add(pnGrid, BorderLayout.EAST);
	}

	public void setAreaComp() {
		pin = new JPanelPad((int) getSize().getWidth(), (int) getSize()
				.getHeight());
		c.add(pin, BorderLayout.CENTER);
		setArea = false;
	}

	public void adic(Component comp, int X, int Y, int Larg, int Alt) {
		if (setArea)
			setAreaComp();
		comp.addKeyListener(this);
		pin.adic(comp, X, Y, Larg, Alt);
	}

	public void adicInvisivel(Component comp) {
		comp.addKeyListener(this);
	}

	public void keyPressed(KeyEvent kevt) {
		if ((bUltimo) & (kevt.getKeyCode() == KeyEvent.VK_ENTER))
			btOK.doClick();
		else if (kevt.getKeyCode() == KeyEvent.VK_ESCAPE)
			btCancel.doClick();
	}

	public void keyTyped(KeyEvent kevt) {
	}

	public void keyReleased(KeyEvent kevt) {
	}

	public void setFirstFocus(Component firstFocus) {
		this.firstFocus = firstFocus;
	}

	public void setInitFirstFocus(boolean initFirstFocus) {
		this.initFirstFocus = initFirstFocus;
	}

	public boolean getInitFirstFocus() {
		return initFirstFocus;
	}

	public void firstFocus() {
		if ((firstFocus != null) && (firstFocus.hasFocus()) && (initFirstFocus))
			firstFocus.requestFocus();

		/*
		 * if (firstFocus!=null) { if (firstFocus.hasFocus())
		 * firstFocus.requestFocus(); else loadFirstFocus(); } else
		 * loadFirstFocus();
		 */
	}

	public void windowActivated(WindowEvent arg0) {
		firstFocus();
	}

	public void windowClosed(WindowEvent arg0) {
	}

	public void windowClosing(WindowEvent arg0) {
	}

	public void windowDeactivated(WindowEvent arg0) {
	}

	public void windowDeiconified(WindowEvent arg0) {
	}

	public void windowIconified(WindowEvent arg0) {
	}

	public void windowOpened(WindowEvent arg0) {
		firstFocus();

	}
}