package org.freedom.modulos.fnc;

import java.awt.Component;
import java.awt.event.ActionListener;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;


public class DLIdentCli extends FFDialogo {
   private static final long serialVersionUID = 1L;

   private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 9, 0);
   
   private JTextFieldPad txtRazCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
   
   private JTextFieldPad txtAgenciaCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 9, 0);
   
   private JTextFieldPad txtIdentCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 14, 0);
   
   private static boolean MRET = false;
   
   public DLIdentCli(Component cOrig) {
	  	super(cOrig);
	    setTitulo("Identifica��o do cliente");
	    setAtribos(420, 200);
	    txtCodCli.setAtivo( false );
	    txtRazCli.setAtivo( false );
	    txtAgenciaCli.setRequerido( true );
	    txtIdentCli.setRequerido( true );
	    adic(new JLabelPad("C�d.cli."),7, 5, 80, 20);
		adic(txtCodCli, 7, 25, 80, 20);
		adic(new JLabelPad("Raz�o social do cliente"), 90, 5, 280, 20);
		adic(txtRazCli, 90, 25, 280, 20);
	    
	    adic(new JLabelPad("Ag�ncia"), 7, 50, 80, 20);
		adic(txtAgenciaCli, 7, 70, 80, 20);
		adic(new JLabelPad("Identifica��o"), 90, 50, 100, 20);
		adic(txtIdentCli, 90, 70, 100, 20);
   }
   
   public static Object[] execIdentCli(Component cOrig, Integer codCli, String razCli, String agenciaCli, String identCli) {
	   Object[] retorno = {new Boolean(false), "", ""}; 
	   DLIdentCli dl = new DLIdentCli(cOrig);
	   dl.txtCodCli.setVlrInteger( codCli );
	   dl.txtRazCli.setVlrString( razCli );
	   dl.txtAgenciaCli.setVlrString( agenciaCli );
	   dl.txtIdentCli.setVlrString( identCli );
	   dl.execShow();
	   if (MRET) {
	      retorno[0] = new Boolean(MRET);
	      retorno[1] = dl.txtAgenciaCli.getVlrString();
	      retorno[2] = dl.txtIdentCli.getVlrString();
	   }
	   return retorno;
   }
   

   @ Override
   public void ok() {
 	  if (txtAgenciaCli.getVlrString().trim().equals( "" )) {
		  Funcoes.mensagemInforma( this, "Campo ag�ncia � obrigat�rio!" );
		  txtAgenciaCli.requestFocus();
		  return;
		  
	  } else if (txtIdentCli.getVlrString().trim().equals( "" )) {
		  Funcoes.mensagemInforma( this, "Campo identifica��o � obrigat�rio!" );
		  txtIdentCli.requestFocus();
		  return;
	  }
 	  MRET = true;
	  super.ok();
   }
  
}
