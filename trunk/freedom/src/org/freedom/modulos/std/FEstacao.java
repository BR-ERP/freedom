/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Leandro Oliveira Mesquita <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FEstacao.java <BR>
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
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Vector;
//import javax.swing.JButton;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
//import org.freedom.acao.RadioGroupEvent;
//import org.freedom.acao.RadioGroupListener;
//import org.freedom.compo.GuardaCampo;
//import org.freedom.compo.ImprimeOS;
//import org.freedom.compo.JCheckBoxPad;
//import org.freedom.compo.JRadioGroup;
//import org.freedom.compo.JTextAreaPad;
//import org.freedom.compo.JTextFieldFK;
//import org.freedom.compo.ListaCampos;
//import org.freedom.compo.Navegador;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
//import org.freedom.funcoes.Funcoes;
//import org.freedom.telas.Aplicativo;
//import org.freedom.telas.FAndamento;
//import org.freedom.bmps.Icone;
import org.freedom.telas.FTabDados;

public class FEstacao extends FTabDados implements PostListener, ActionListener{
private Painel pinEstc = new Painel();
private JPanel pnEst = new JPanel(new BorderLayout());
private Painel pinEst = new Painel(0,80);
private JTextFieldPad txtCodEmp = new JTextFieldPad(8);
private JTextFieldPad txtNomeCli = new JTextFieldPad(40);
private JTextFieldPad txtRazCli = new JTextFieldPad(50);
private JTextFieldPad txtCodEst = new JTextFieldPad(8);
private JTextFieldPad txtDescEst = new JTextFieldPad(40);
private Tabela tabEst = new Tabela();

public FEstacao() {
	setTitulo("Cadastro de Esta��o"); 
	setAtribos(50, 10, 530, 470);

    pinEst = new Painel(500,330);
    setPainel(pinEst);
	}
    private void montaTela() {
  	adicTab("Esta��o", pinEst); 

  	lcCampos.addPostListener(this);
  	txtCodEst.setTipo(JTextFieldPad.TP_INTEGER,8,0);
  	txtDescEst.setTipo(JTextFieldPad.TP_STRING,50,0); 
  	adicCampo(txtCodEst, 7, 20, 80, 20, "Codest", "Esta��o", JTextFieldPad.TP_INTEGER, 8, 0, true, false, null,true);
  	adicCampo(txtRazCli, 90, 20, 307, 20, "Descest", "Descri��o", JTextFieldPad.TP_STRING, 50, 0, false, false, null,true);
  
    setListaCampos(true,"ESTACAO", "SG");
    lcCampos.setQueryInsert(false); 
  
    }
    
    public void setConexao(Connection cn) {
    	super.setConexao(cn);
        
    }

    public void execShow(Connection cn) {
    	con = cn;
      montaTela();	
     super.execShow(cn);
    }
  }
