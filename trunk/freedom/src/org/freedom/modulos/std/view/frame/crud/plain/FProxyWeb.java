/**
 * @version 07/03/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FEstacao.java <BR>
 * 
 *                   Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                   modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                   na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                   Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                   sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                   Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                   Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                   Coment�rios sobre a classe...
 */

package org.freedom.modulos.std.view.frame.crud.plain;

import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FProxyWeb extends FDados implements CheckBoxListener, InsertListener {

	private static final long serialVersionUID = 1L;
	
	
	private JTextFieldPad txtCodProxy = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8 ,0 );

	private JTextFieldPad txtDescProxy = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );
	
	private JTextFieldPad txtHostProxy = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtPortaProxy = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtAutProxy = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldPad txtUsuProxy = new JTextFieldPad( JTextFieldPad.TP_STRING, 128, 0 );
	
	private JTextFieldPad txtSenhaProxy = new JTextFieldPad( JTextFieldPad.TP_STRING, 128, 0 );

	private JCheckBoxPad cbAutProxy = new JCheckBoxPad( "Autenticado?", "S", "N" );
	

	public FProxyWeb() {

		setTitulo( "Cadastro do Proxy" );
		setAtribos( 50, 10, 520, 250 );

		montaTela();
		
		cbAutProxy.addCheckBoxListener( this );
		lcCampos.addInsertListener( this );
	}
	
	/*
	 *  CODFILIAL SMALLINT NOT NULL,
        CODPROXY INTEGER NOT NULL,
        DESCPROXY VARCHAR(60) NOT NULL,
        HOSTPROXY VARCHAR(100) NOT NULL,
        PORTAPROXY INTEGER NOT NULL,
        AUTPROXY CHAR(1) DEFAULT 'N' NOT NULL,
        USUPROXY VARCHAR(128),
        SENHAPROXY VARCHAR(128),
        DTINS DATE DEFAULT 'now' NOT NULL,
        HINS TIME DEFAULT 'now' NOT NULL,
        IDUSUINS VARCHAR(128) DEFAULT USER NOT NULL,
        DTALT DATE DEFAULT 'now' NOT NULL,
        HALT TIME DEFAULT 'now' NOT NULL,
        IDUSUALT VARCHAR(128) DEFAULT USER NOT NULL,
	 */


	private void montaTela() {
		
		adicCampo( txtCodProxy, 7, 20, 80, 20, "CODPROXY", "C�d.proxy", ListaCampos.DB_PK, true );
		adicCampo( txtDescProxy, 90, 20, 250, 20, "DESCPROXY", "Descri��o do proxy", ListaCampos.DB_SI, true );
		adicCampo( txtHostProxy, 7, 60, 250, 20, "HOSTPROXY", "Host do proxy", ListaCampos.DB_SI, true );
		adicCampo( txtPortaProxy, 260, 60, 80, 20, "PORTAPROXY", "Porta proxy", ListaCampos.DB_SI, true );
		adicDB( cbAutProxy, 343, 60, 200, 20, "AUTPROXY","", true );
		adicCampo( txtUsuProxy, 7, 100, 165, 20, "USUPROXY", "Usu�rio", ListaCampos.DB_SI, false );
		adicCampo( txtSenhaProxy, 175, 100, 165, 20, "SENHAPROXY", "Senha", ListaCampos.DB_SI, false );
		
		
		setListaCampos( true, "PROXYWEB", "SG" );

	}
	
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );


	}

	public void valorAlterado( CheckBoxEvent evt ) {
		
		if(evt.getCheckBox() == cbAutProxy){
			if("S".equals( cbAutProxy.getVlrString() ))	{
				txtUsuProxy.setEnabled( true );
				txtSenhaProxy.setEnabled( true );
			} else {
				txtUsuProxy.setEnabled( false );
				txtSenhaProxy.setEnabled( false );
			}
			
			
		}
		
	}

	public void beforeInsert( InsertEvent ievt ) {

		// TODO Auto-generated method stub
		
	}

	public void afterInsert( InsertEvent ievt ) {
		if(ievt.getListaCampos() == lcCampos){
			cbAutProxy.setVlrString( "N" );
		}
	}
	
	/*public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btDirCacerts) {
			Thread th = new Thread(new Runnable() {
				public void run() {
					getDiretorio();
				}
			});
			th.start();
		}
	}
	
	private void getDiretorio() {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				txtPathCacerts.setVlrString(fileChooser.getSelectedFile().getPath());
		}
	}*/
}
