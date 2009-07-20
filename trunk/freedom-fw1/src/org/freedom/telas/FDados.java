/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FDados.java <BR>
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

package org.freedom.telas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.PainelImagem;

public class FDados extends FFilho implements ActionListener, KeyListener, InternalFrameListener, PostListener{//, FocusListener {
	private static final long serialVersionUID = 1L;

   public PreparedStatement atualiza = null;
   public PreparedStatement insere = null;
   public PreparedStatement deleta = null;
   public ListaCampos lcCampos = new ListaCampos(this); 
   public ListaCampos lcSeq = null;
   private GridLayout glImp = new GridLayout( 1, 2); 
   private FlowLayout flImp = new FlowLayout(FlowLayout.CENTER, 0, 0);
   public Navegador nav = new Navegador(false); 
   public Navegador navSeq = new Navegador(false); 
   public JPanelPad pnImp = new JPanelPad(JPanelPad.TP_JPANEL);
   public JPanelPad pnGImp = new JPanelPad(JPanelPad.TP_JPANEL);
   public JPanelPad pinDados = new JPanelPad();
   public JButton btSair = new JButton("Sair", Icone.novo("btSair.gif"));
   public JButton btImp = new JButton( Icone.novo("btImprime.gif"));
   public JButton btPrevimp = new JButton( Icone.novo("btPrevimp.gif"));
   public JComponent primeiroCompo = null;
   boolean Shift = false;
   boolean Ctrl = false;
   boolean setArea = true;
   boolean bMostrar = false;
   public FDados() {
   	 this(true);
   }
   public FDados(boolean comScroll) {
   	 super(comScroll);
   	 btImp.setVisible(false);
   	 btPrevimp.setVisible(false);
     //setTitulo("Formul�rio de dados", this.getClass().getName());

     lcSeq = lcCampos;
     navSeq = nav;

     btSair.setToolTipText("Fecha a Tela (Shift + F4)");
     btImp.setToolTipText("Imprimir (Ctrl+P)");
     btPrevimp.setToolTipText("Visualizar Impress�o (Ctrl+R)");

     pnImp.setLayout(flImp);
     pnGImp.setLayout(glImp);
     pnGImp.setPreferredSize(new Dimension( 80, 26));
     pnGImp.add(btImp);
     pnGImp.add(btPrevimp);
     pnImp.add(pnGImp);

     btSair.addActionListener(this);

     btSair.addKeyListener(this);
     btImp.addKeyListener(this);
     btPrevimp.addKeyListener(this);

     addKeyListener(this);
     addInternalFrameListener(this);
     
     lcCampos.addPostListener(this);

     pnRodape.add( btSair, BorderLayout.EAST);      
     pnRodape.add( nav, BorderLayout.WEST);
     pnRodape.add( pnImp, BorderLayout.CENTER);
     c.add(pnBordRod, BorderLayout.SOUTH);
     
  }  

  public void setImprimir(boolean bImp){
  	btImp.setVisible(bImp);
  	btPrevimp.setVisible(bImp);
  }
  
  public void setPKFoco() {
    for (int i=0; i<lcCampos.getComponentCount(); i++) {
      if (((GuardaCampo)lcCampos.getComponent(i)).ehPK())
        ((GuardaCampo)lcCampos.getComponent(i)).getComponente().requestFocus();
    }
  }

  public void execConsultaPrep() {
/*     try {
        resultado = consultaPrep.executeQuery();
     }
     catch (SQLException e) {
        Funcoes.mensagemErro(null,e.getMessage());
     }*/
  }

  public void actionPerformed(ActionEvent evt) {
    if (evt.getActionCommand() == "Sair") 
      dispose();
  }
  public void keyPressed(KeyEvent kevt) {
    if(kevt.getKeyCode() == KeyEvent.VK_SHIFT) Shift = true;
    if(kevt.getKeyCode() == KeyEvent.VK_CONTROL) Ctrl = true;
    if(Shift) {
      if(kevt.getKeyCode() == KeyEvent.VK_F4) btSair.doClick();
    }
    if(Ctrl) {
      if (kevt.getKeyCode() == KeyEvent.VK_R) btPrevimp.doClick();
        else if (kevt.getKeyCode() == KeyEvent.VK_P) btImp.doClick();
    }
  }
  public void keyReleased(KeyEvent kevt) {
//    Funcoes.mensagemErro( null, "keyRelease");
    if(kevt.getKeyCode() == KeyEvent.VK_SHIFT) Shift = false;
    else if(kevt.getKeyCode() == KeyEvent.VK_CONTROL) Ctrl = false;
  }
  public void keyTyped(KeyEvent kevt) {  }

  public void setPainel(JPanelPad pin, Container pn) {
    pinDados = pin;
    pn.add(pinDados);
    setArea = false;
  }
  public void setPainel(JPanelPad pin) {
    pinDados = pin;
    setArea = false;
  }
  public Dimension getAreaComp() {
    int iLarg = (int)getSize().getWidth();
    int iAlt = (int)getSize().getHeight();
    iLarg -= 10;
    iAlt -= 35;
    Dimension dm = new Dimension(iLarg,iAlt);
    return dm;
  }
  public void setAreaComp() {
    pinDados = new JPanelPad((int)getSize().getWidth()-10,
      (int)getSize().getHeight()-65);  
    pnCliente.add(pinDados, BorderLayout.CENTER);
    setArea = false;
  }
  public void adic(Component comp, int X, int Y, int Larg, int Alt) {
    if (setArea)
      setAreaComp();
    comp.addKeyListener(this);
    if (navSeq!=null) {
       comp.addKeyListener(navSeq);
    }
    try {
      JTextFieldPad txt = (JTextFieldPad) comp; 
      txt.addFocusListener(txt);
      comp = txt;
    }
    catch (Exception e) { }
    pinDados.adic(comp,X,Y,Larg,Alt); 
  }

  public JLabelPad adicCampo(JTextFieldPad comp, int X, int Y, int Larg, int Alt, String nome, 
      String label, byte key, JTextFieldFK txtDescFK, boolean req) {
      comp.setName("txt"+nome);
      comp.setNomeCampo(nome);
      comp.setListaCampos(lcSeq);
      comp.setChave(key);
      if (txtDescFK == null)
        lcSeq.add(new GuardaCampo( comp, nome, label, key, req));
      else 	
     	lcSeq.add(new GuardaCampo( comp, nome, label, key, txtDescFK, req));
      if ( navSeq!=null ) {
         navSeq.setListaCampos(lcSeq);
         lcSeq.setNavegador(navSeq);
      }
      lcSeq.setState(ListaCampos.LCS_NONE);
      JLabelPad lbTmp = new JLabelPad(label);
      adic(lbTmp, X, Y-20, Larg, 20);
      adic(comp, X, Y, Larg, Alt);
      return lbTmp;
  }
  public JLabelPad adicCampo(JTextFieldPad comp, int X, int Y, int Larg, int Alt, String nome, 
      String label, byte key, boolean req) {
    return adicCampo(comp, X, Y, Larg, Alt, nome, label, key, null, req);
  }
  
  public void adicCampo(JPasswordFieldPad comp, int X, int Y, int Larg, int Alt, String nome, 
      String label, byte key, boolean req) {
	comp.setName("txp"+nome);
	comp.setListaCampos(lcSeq);
	lcSeq.add(new GuardaCampo( comp, nome, label, key, req),"txt"+nome);
	if (navSeq!=null) {
	   navSeq.setListaCampos(lcSeq);
	   lcSeq.setNavegador(navSeq);
	}
	lcSeq.setState(ListaCampos.LCS_NONE);
	adic(new JLabelPad(label), X, Y-20, Larg, 20);
	adic(comp, X, Y, Larg, Alt);
  }

  public void adicCampoInvisivel(JTextFieldPad comp, String nome, String label, 
	byte key, JTextFieldFK txtDescFK, boolean req) {
	comp.addKeyListener(this);
	if (navSeq!=null) {
	   comp.addKeyListener(navSeq);
	}
	comp.setName("txt"+nome);
	comp.setNomeCampo(nome);
	comp.setListaCampos(lcSeq);
	lcSeq.add(new GuardaCampo( comp, nome, label, key, txtDescFK, req,false));
	if (navSeq!=null) {
	    navSeq.setListaCampos(lcSeq);
	    lcSeq.setNavegador(navSeq);
	}
	lcSeq.setState(ListaCampos.LCS_NONE);
  }   
 
  public void adicCampoInvisivel(JTextFieldPad comp, String nome, String label, 
   		byte key,  boolean req) {
  	 adicCampoInvisivel(comp, nome, label, key, null, req);
  }
  public JLabelPad adicDescFK(JTextFieldFK comp, int X, int Y, int Larg, int Alt, String nome, String label) {
    comp.setNomeCampo(nome);
    comp.addKeyListener(this);
    if (navSeq!=null) {
       comp.addKeyListener(navSeq);
    }
    comp.setLabel(label);
    JLabelPad lbTmp = new JLabelPad(label);
    adic(lbTmp, X, Y-20, Larg, 20);
    adic(comp, X, Y, Larg, Alt);
    return lbTmp;
  }
  public JLabelPad adicDescFKInvisivel(JTextFieldFK comp, String nome, String label) {
    comp.setNomeCampo(nome);
    comp.addKeyListener(this);
    if (navSeq!=null) {
	comp.addKeyListener(navSeq);
    }
    comp.setLabel(label);
    JLabelPad lbTmp = new JLabelPad(label);
    return lbTmp;
  }

  public void adicDBLiv( Component comp, String nome, String label, boolean req) { 
    comp.setName(nome);
    comp.addKeyListener(this);
    if (navSeq!=null) {
	comp.addKeyListener(navSeq);
    }
    if (comp instanceof JRadioGroup)
      ((JRadioGroup<?, ?>) comp).setListaCampos(lcSeq);
    else if (comp instanceof JCheckBoxPad)
      ((JCheckBoxPad) comp).setListaCampos(lcSeq);
    else if (comp instanceof JTextAreaPad)
      ((JTextAreaPad) comp).setListaCampos(lcSeq);
    lcSeq.add(new GuardaCampo( comp, nome, label, ListaCampos.DB_SI, req));
    
  }
  public void adicDBLiv(Component comp,int X,int Y,int Larg,int Alt, String nome, String label, boolean req) { 
    comp.setName(nome);
    comp.addKeyListener(this);
    if (navSeq!=null) {
       comp.addKeyListener(navSeq);
    }
    if (comp instanceof JRadioGroup)
      ((JRadioGroup<?, ?>) comp).setListaCampos(lcSeq);
    else if (comp instanceof JCheckBoxPad)
      ((JCheckBoxPad) comp).setListaCampos(lcSeq);
    else if (comp instanceof JTextAreaPad) {
      ((JTextAreaPad) comp).setListaCampos(lcSeq);
      comp.setName("txp"+nome);
      adic(new JLabelPad(label), X, Y-20, Larg, 20);
      adic(new JScrollPane(comp), X, Y, Larg, Alt);
    }
    lcSeq.add(new GuardaCampo( comp, nome, label, ListaCampos.DB_SI, req));
    
        
  }

  public JLabelPad adicDB( Component comp, int X, int Y, int Larg, int Alt, String nome, String label, boolean req) {
  	boolean bScroll = false;
  	JLabelPad lbTmp = null;
    comp.setName(nome);
    if (comp instanceof JRadioGroup)
      ((JRadioGroup<?, ?>) comp).setListaCampos(lcSeq);
    else if (comp instanceof JCheckBoxPad)
      ((JCheckBoxPad) comp).setListaCampos(lcSeq);
	else if (comp instanceof JComboBoxPad)
	  ((JComboBoxPad) comp).setListaCampos(lcSeq);
    else if (comp instanceof PainelImagem)
      ((PainelImagem) comp).setListaCampos(lcSeq);
	else if (comp instanceof JTextAreaPad) {
	  ((JTextAreaPad) comp).setListaCampos(lcSeq);
	  bScroll = true;
	}
    lcSeq.add(new GuardaCampo( comp, nome, label, ListaCampos.DB_SI, req));
    lbTmp = new JLabelPad(label);
    adic(lbTmp, X, Y-20, Larg, 20);
    if (bScroll)
  	  adic(new JScrollPane(comp),X, Y, Larg, Alt);
    else 
	  adic(comp, X, Y, Larg, Alt);
    
    return lbTmp;
  }
  
  public JLabelPad adic( Component comp, int X, int Y, int Larg, int Alt, String label) {
	  	boolean bScroll = false;
	  	JLabelPad lbTmp = null;
	    
	      	    
	  	bScroll = true;


	    lbTmp = new JLabelPad(label);
	    adic(lbTmp, X, Y-20, Larg, 20);
	    if (bScroll)
	  	  adic(new JScrollPane(comp),X, Y, Larg, Alt);
	    else 
		  adic(comp, X, Y, Larg, Alt);
	    
	    return lbTmp;
  }
  
  public JLabelPad adicDBInvisivel( Component comp, String nome, String label, boolean req) {
	  	boolean bScroll = false;
	  	JLabelPad lbTmp = null;
	    comp.setName(nome);
	    if (comp instanceof JRadioGroup)
	      ((JRadioGroup<?, ?>) comp).setListaCampos(lcSeq);
	    else if (comp instanceof JCheckBoxPad)
	      ((JCheckBoxPad) comp).setListaCampos(lcSeq);
		else if (comp instanceof JComboBoxPad)
		  ((JComboBoxPad) comp).setListaCampos(lcSeq);
	    else if (comp instanceof PainelImagem)
	      ((PainelImagem) comp).setListaCampos(lcSeq);
		else if (comp instanceof JTextAreaPad) {
		  ((JTextAreaPad) comp).setListaCampos(lcSeq);
		  bScroll = true;
		}
	    lcSeq.add(new GuardaCampo( comp, nome, label, ListaCampos.DB_SI, req));
	    lbTmp = new JLabelPad(label);	    
	    return lbTmp;
  }
  
  public void setNavegador(Navegador nv) {
    navSeq = nv;
  }
  public void setListaCampos(ListaCampos lc) {
    lcSeq = lc;
  }
  public void setListaCampos(boolean bAuto, String sTab, String sSigla) {
      lcSeq.montaSql(bAuto, sTab, sSigla);
  }           
  public void setBordaReq(JComponent comp) {
    comp.setBorder( 
      BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red),
        BorderFactory.createEtchedBorder()
      )
    );
  }
  public void setBordaPad(JComponent comp) {
    comp.setBorder( BorderFactory.createEtchedBorder());
  }
  public synchronized void setConexao(DbConnection cn) {
  	super.setConexao(cn);
    lcCampos.setConexao(con);
    lcSeq.setConexao(con);
    setPKFoco();
  }

  public void beforePost(PostEvent pevt) {
    setPKFoco();
  }
  public void afterPost(PostEvent pevt) { }
  public void internalFrameDeactivated(InternalFrameEvent ifevt) { 
    super.internalFrameDeactivated(ifevt);
  }
  public void internalFrameDeiconified(InternalFrameEvent ifevt) { 
    super.internalFrameDeiconified(ifevt);
  }
  public void internalFrameIconified(InternalFrameEvent ifevt) { 
    super.internalFrameIconified(ifevt);
  }
  public void internalFrameOpened(InternalFrameEvent ifevt) { 
    super.internalFrameOpened(ifevt);
  }
}        
        
        
        
        
        
        

