/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FPrincipal.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios para a classe...
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.border.Border;

import org.freedom.bmps.Icone;
import org.freedom.bmps.Imagem;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JMenuPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.StatusBar;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.atd.FAgenda;

public abstract class FPrincipal extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	protected static Connection con = null;

	public JMenuBar bar = new JMenuBar();

	private JToolBar tBar = new JToolBar();

	protected JMenuItem sairMI = new JMenuItem();

	private Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

	private JButton btCalc = new JButton( Icone.novo( "btCalc.gif" ) );

	private JButton btAgenda = new JButton( Icone.novo( "btAgenda.gif" ) );

	public JPanelPad pinBotoesDir = new JPanelPad();

	public Container c = getContentPane();

	public JDesktopPane dpArea = new JDesktopPane();

	public StatusBar statusBar = new StatusBar();

	private JLabelPad lbFreedom = null;

	private JLabelPad lbStpinf = null;

	private ImageIcon icFundo = null;

	private JLabelPad lbFundo = null;

	private int iWidthImgFundo = 0;

	private int iHeightImgFundo = 0;

	private String sURLStpinf = "http://www.stpinf.com";

	private String sURLFreedom = "http://www.freedom.org.br";

	private Border borderStpinf = null;

	private Border borderFreedom = null;

//	public Color padrao = new Color( 69, 62, 113 );

	public Color padrao = new Color( 145, 167, 208 );

	public String sImgFundo = null;

	private JTabbedPanePad tpnAgd = new JTabbedPanePad();

	private JPanelPad pnAgd = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private static Tabela tabAgd = new Tabela();

	private JScrollPane spnAgd = new JScrollPane( tabAgd );

	private JSplitPane splitPane = null;

	private String imgLogoSis = "lgFreedom3.jpg";

	private String imgLogoEmp = "lgSTP3.jpg";

	public FPrincipal( String sDirImagem, String sImgFundo ) {

		this( sDirImagem, sImgFundo, null, null );
	}

	public FPrincipal( String sDirImagem, String sImgFundo, String sImgLogoSis, String sImgLogoEmp ) {

		if ( sDirImagem != null ) {
			Imagem.dirImages = sDirImagem;
			Icone.dirImages = sDirImagem;
		}
		if ( sImgLogoSis != null ) {
			imgLogoSis = sImgLogoSis;
		}
		if ( sImgLogoEmp != null ) {
			imgLogoEmp = sImgLogoEmp;
		}
		lbFreedom = new JLabelPad( Icone.novo( imgLogoSis ) );
		lbStpinf = new JLabelPad( Icone.novo( imgLogoEmp ) );
		this.sImgFundo = sImgFundo;
		c.setLayout( new BorderLayout() );
		// JPanelPad pn = new JPanelPad(JPanelPad.TP_JPANEL);
		// pn.setLayout(new GridLayout(1, 1));

		setJMenuBar( bar );

		sairMI.setText( "Sair" );
		sairMI.setMnemonic( 'r' );

		splitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
		splitPane.setContinuousLayout( true );
		splitPane.setOneTouchExpandable( true );

		splitPane.setTopComponent( dpArea );

		splitPane.setDividerSize( 1 );
		splitPane.setDividerLocation( (int) tela.getHeight() );

		montaStatus();

		// c.add(dpArea, BorderLayout.CENTER);
		c.add( splitPane, BorderLayout.CENTER );

		setExtendedState( MAXIMIZED_BOTH );

		inicializaTela();

		sairMI.addActionListener( new ActionListener() {

			public void actionPerformed( ActionEvent e ) {

				fecharJanela();
			}
		} );
		addWindowListener( new WindowAdapter() {

			public void windowClosing( WindowEvent e ) {

				fecharJanela();
			}
		} );

	}

	public abstract void remConFilial();

	public abstract void fecharJanela();

	public abstract void inicializaTela();

	public void adicAgenda() {

		tabAgd.adicColuna( "Ind." );
		tabAgd.adicColuna( "Sit." );
		tabAgd.adicColuna( "Prioridade" );
		tabAgd.adicColuna( "Assunto" );
		tabAgd.adicColuna( "Data ini." );
		tabAgd.adicColuna( "Hora ini." );
		tabAgd.adicColuna( "Data fim." );
		tabAgd.adicColuna( "Hora fim." );

		tabAgd.setTamColuna( 50, 0 );
		tabAgd.setTamColuna( 50, 1 );
		tabAgd.setTamColuna( 100, 2 );
		tabAgd.setTamColuna( 250, 3 );
		tabAgd.setTamColuna( 100, 4 );
		tabAgd.setTamColuna( 120, 5 );
		tabAgd.setTamColuna( 100, 6 );
		tabAgd.setTamColuna( 120, 7 );

		splitPane.setBottomComponent( tpnAgd );

		pnAgd.add( spnAgd, BorderLayout.CENTER );
		tpnAgd.add( "Agenda do usu�rio", pnAgd );

		splitPane.setDividerSize( 10 );
		splitPane.setDividerLocation( ( (int) tela.getHeight() - 300 ) );

	}

	public static void carregaAgenda() {

		System.out.println( "Vai carregar agenda" );

		int iCodAge = 0;
		String sTipoAge = null;

		try {

			String sSQL = "SELECT U.CODAGE,U.TIPOAGE,U.CODFILIALAE FROM SGUSUARIO U WHERE CODEMP=? AND CODFILIAL=? AND IDUSU=?";
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setString( 3, Aplicativo.strUsuario );
			ResultSet rs = ps.executeQuery();

			while ( rs.next() ) {
				iCodAge = rs.getInt( 1 );
				sTipoAge = rs.getString( 2 );
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		FAgenda.carregaTabAgd( iCodAge, sTipoAge, new Object[] { new Date() }, tabAgd, true, con, null, null );

	}

	private void setBordaURL( JComponent comp ) {

		comp.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createMatteBorder( 0, 0, 0, 0, Color.BLUE ), BorderFactory.createEtchedBorder() ) );
	}

	public void mouseClicked( MouseEvent arg0 ) {

		if ( ( arg0.getSource() == lbStpinf ) && ( arg0.getClickCount() >= 2 ) ) {
			Funcoes.executeURL( Aplicativo.strOS, Aplicativo.strBrowser, sURLStpinf );
		}
		else if ( ( arg0.getSource() == lbFreedom ) && ( arg0.getClickCount() >= 2 ) ) {
			Funcoes.executeURL( Aplicativo.strOS, Aplicativo.strBrowser, sURLFreedom );
		}
	}

	public void mouseEntered( MouseEvent arg0 ) {

		if ( arg0.getSource() == lbStpinf ) {
			setBordaURL( lbStpinf );
		}
		else if ( arg0.getSource() == lbFreedom ) {
			setBordaURL( lbFreedom );
		}
	}

	public void mouseExited( MouseEvent arg0 ) {

		if ( arg0.getSource() == lbStpinf ) {
			lbStpinf.setBorder( borderStpinf );

		}
		else if ( arg0.getSource() == lbFreedom ) {
			lbFreedom.setBorder( borderFreedom );
		}
	}

	public void mousePressed( MouseEvent arg0 ) {

	}

	public void mouseReleased( MouseEvent arg0 ) {

	}

	public void addKeyListerExterno( KeyListener arg0 ) {

		this.addKeyListener( arg0 );
		btCalc.addKeyListener( arg0 );
		bar.addKeyListener( arg0 );
		tBar.addKeyListener( arg0 );
		btAgenda.addKeyListener( arg0 );
	}

	public void montaStatus() {

		c.add( statusBar, BorderLayout.SOUTH );
	}

	public void setConexao( Connection conGeral ) {

		con = conGeral;
	}

	public void adicFilha( Container filha ) {

		dpArea.add( filha );
	}

	public void adicMenu( JMenuPad menu ) {

		bar.add( menu );
	}

	/*
	 * public void adicItemArq(JMenuItemPad menu) { arquivoMenu.add(menu); }
	 * 
	 * public void setMenu() { arquivoMenu.addSeparator(); arquivoMenu.add(sairMI); } public void tiraEmp() { arquivoMenu.remove(0); }
	 */
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btCalc ) {
			Calc calc = null;
			if (!this.temTela( "org.freedom.telas.Calc" ) ) {
				calc = new Calc();
				dpArea.add( calc.getClass().getName(), calc );
				calc.show();
			} else {
				calc = (Calc) getTela("org.freedom.telas.Calc");
				if (calc!=null) {
					calc.show();
				}
			}
			
		}
		else if ( evt.getSource() == btAgenda ) {
			FAgenda agenda = null;
			if ( this.temTela( "org.freedom.modulos.atd.FAgenda" ) ) {
				agenda = (FAgenda) this.getTela( "org.freedom.modulos.atd.FAgenda" );
				if (agenda!=null) {
					agenda.show();
					carregaAgenda();
				}
			} else {
				agenda = new FAgenda();
				this.criatela( "Agenda", agenda, con );
				carregaAgenda();
			}
		}
	}

	public boolean temTela( String nome ) {

		boolean retorno = false;
		int i = 0;

		JInternalFrame[] telas = dpArea.getAllFrames();
		JInternalFrame tela = null;

		while ( true ) {

			try {
				tela = telas[ i ];
			} catch ( java.lang.Exception e ) {
				break;
			}

			if ( tela == null ) {
				break;
			}
			else if ( tela.getName() == null ) {
				i++;
				continue;
			}
			else if ( tela.getName().equals( nome ) ) {
				try {
					tela.setSelected( true );
				} catch ( Exception e ) {
				}
				retorno = true;
				break;
			}

			i++;
		}

		return retorno;

	}

	public JInternalFrame getTela( String nome ) {

		JInternalFrame retorno = null;
		JInternalFrame[] telas = dpArea.getAllFrames();
		JInternalFrame tela = null;

		for ( int i = 0; i < telas.length; i++ ) {
			try {
				tela = telas[ i ];
			} catch ( java.lang.Exception e ) {
				break;
			}

			if ( tela == null ) {
				break;
			}
			else if ( tela.getName() == null ) {
				break;
			}
			else if ( tela.getName().equals( nome ) ) {
				try {
					retorno = tela;
					break;
				} catch ( Exception e ) {
					retorno = null;
					break;
				}
			}
		}

		return retorno;

	}

	public void criatela( String titulo, FFDialogo comp, Connection cn ) {

		//comp.setName( nome );
		String name = comp.getClass().getName();
		comp.setTitulo( titulo, name  );
		comp.setConexao( cn );
		comp.execShow();
	}

	public void criatela( String titulo, FFilho comp, Connection cn ) {

	//	comp.setName( nome );
		String name = comp.getClass().getName();
		comp.setTitulo( titulo, name  );
		dpArea.add( name, comp );
		comp.setConexao( cn );
		comp.execShow();
		try {
			comp.setSelected( true );
		} catch ( Exception e ) {
		}
	}

	public void criatela( String titulo, FDialogo comp, Connection cn ) {

		String name = comp.getClass().getName();
		//comp.setName( name  );
		comp.setTitulo( titulo, name );
		comp.setConexao( cn );
		comp.setVisible( true );
	}

	/**
	 * Ajusta a identifica��o do sistema. <BR>
	 * 
	 * @param sDesc -
	 *            Descri��o do sistema.
	 * @param iCod -
	 *            C�digo do sistema.
	 * @param iMod -
	 *            C�digo do m�dulo.
	 */
	public void setIdent( String sDesc ) {

		setTitle( sDesc );
	}

	/**
	 * Adiciona um componente na barra de ferramentas. <BR>
	 * 
	 * @param comp -
	 *            Componente a ser adicionado.
	 */
	public void adicCompInBar( Component comp, String sAling ) {

		tBar.add( comp, sAling );
	}

	public void setBgColor( final Color cor ) {

		dpArea.setBackground( cor );
	}

	public void addLinks( final ImageIcon icStpinf, final ImageIcon icFreedom ) {

		lbStpinf = new JLabelPad( icStpinf );
		lbFreedom = new JLabelPad( icFreedom );
		

		final int iWidthImgStpinf = icStpinf.getIconWidth();
		final int iHeightImgStpinf = icStpinf.getIconHeight();
		final int iWidthImgFreedom = icFreedom.getIconWidth();
		final int iHeightImgFreedom = icFreedom.getIconHeight();

		lbStpinf.setBounds( 20, (int) tela.getHeight() - 265, iWidthImgStpinf, iHeightImgStpinf );
		lbFreedom.setBounds( (int) tela.getWidth() - 155, (int) tela.getHeight() - 265, iWidthImgFreedom, iHeightImgFreedom );
		lbStpinf.setToolTipText( sURLStpinf );
		lbFreedom.setToolTipText( sURLFreedom );

		borderStpinf = lbStpinf.getBorder();
		borderFreedom = lbFreedom.getBorder();

		dpArea.add( lbStpinf );
		dpArea.add( lbFreedom );

		lbStpinf.addMouseListener( this );
		lbFreedom.addMouseListener( this );
		
	}
	
	public void addFundo() {
		
		addFundo( null );
	}

	public void addFundo( JComponent comp ) {

		final int iWidthArea = (int) tela.getWidth();
		final int iHeightArea = (int) tela.getHeight();
		setSize( iWidthArea, iHeightArea - 50 );
		 
		if ( comp == null ) {

			icFundo = Icone.novo( sImgFundo );
			lbFundo = new JLabelPad( icFundo );

			iWidthImgFundo = icFundo.getIconWidth();
			iHeightImgFundo = icFundo.getIconHeight();
			lbFundo.setSize( iWidthImgFundo, iHeightImgFundo );
						
			comp = lbFundo;
		}	
		
		comp.setBounds( ( iWidthArea / 2 ) - ( comp.getWidth() / 2 ), ( ( iHeightArea - 200 ) / 2 ) - ( comp.getHeight() / 2 ), comp.getWidth(), comp.getHeight() );
		
		dpArea.add( comp );
	}

	public void adicBotoes() {

		preparaBarra();
		adicBtAgenda();
		adicBtCalc();
	}

	public void preparaBarra() {

		pinBotoesDir.setBorder( null );
		c.add( tBar, BorderLayout.NORTH );
		tBar.setLayout( new BorderLayout() );
		pinBotoesDir.setPreferredSize( new Dimension( 102, 34 ) );
		tBar.add( pinBotoesDir, BorderLayout.EAST );
	}

	public void adicBtAgenda() {

		btAgenda.setPreferredSize( new Dimension( 34, 34 ) );
		btAgenda.setToolTipText( "Agenda" );
		btAgenda.addActionListener( this );
		pinBotoesDir.add( btAgenda );
	}

	public void adicBtCalc() {

		btCalc.setPreferredSize( new Dimension( 34, 34 ) );
		btCalc.setToolTipText( "Calculadora" );
		btCalc.addActionListener( this );
		pinBotoesDir.add( btCalc );
	}

}
